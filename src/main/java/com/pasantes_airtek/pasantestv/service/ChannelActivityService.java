package com.pasantes_airtek.pasantestv.service;

import com.pasantes_airtek.pasantestv.dto.ChannelActiveUsersDTO;
import com.pasantes_airtek.pasantestv.model.Channel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChannelActivityService {

    private static final Duration ACTIVE_WINDOW = Duration.ofMinutes(5);
    private final ChannelService channelService;
    private final Map<String, Map<String, Instant>> lastSeenByChannelAndIp = new ConcurrentHashMap<>();

    public ChannelActivityService(ChannelService channelService) {
        this.channelService = channelService;
    }

    public int getActiveUsersCount() {
        Instant now = Instant.now();
        int total = 0;

        lastSeenByChannelAndIp.forEach((channelId, viewers) -> {
            viewers.entrySet().removeIf(entry ->
                    Duration.between(entry.getValue(), now).compareTo(ACTIVE_WINDOW) > 0
            );
        });

        lastSeenByChannelAndIp.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        for (Map<String, Instant> viewers : lastSeenByChannelAndIp.values()) {
            total += viewers.size();
        }

        return total;
    }

    public void registerChannelRequest(HttpServletRequest request, Long channelId) {
        if (channelId == null) {
            return;
        }
        String ip = getClientIp(request);
        if (ip == null || ip.isBlank()) {
            return;
        }
        Instant now = Instant.now();
        String channelKey = String.valueOf(channelId);
        lastSeenByChannelAndIp
                .computeIfAbsent(channelKey, key -> new ConcurrentHashMap<>())
                .put(ip, now);
    }

    public List<ChannelActiveUsersDTO> getActiveUsersByChannel() {
        Instant now = Instant.now();
        List<ChannelActiveUsersDTO> result = new ArrayList<>();

        lastSeenByChannelAndIp.forEach((channelId, viewers) -> {
            viewers.entrySet().removeIf(entry ->
                    Duration.between(entry.getValue(), now).compareTo(ACTIVE_WINDOW) > 0
            );
            if (!viewers.isEmpty()) {
                try {
                    Long id = Long.valueOf(channelId);
                    Channel channel = channelService.findById(id);
                    String channelName = channel != null ? channel.getName() : "Canal " + channelId;
                    result.add(new ChannelActiveUsersDTO(channelName, viewers.size()));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        lastSeenByChannelAndIp.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return result;
    }

    public ChannelActiveUsersDTO getActiveUsersCountByChannel(Long channelId) {
        if (channelId == null) {
            return new ChannelActiveUsersDTO("Canal", 0);
        }
        Instant now = Instant.now();
        Map<String, Instant> viewers = lastSeenByChannelAndIp.get(String.valueOf(channelId));
        Channel channel = channelService.findById(channelId);
        String channelName = channel != null ? channel.getName() : "Canal " + channelId;
        if (viewers == null) {
            return new ChannelActiveUsersDTO(channelName, 0);
        }
        viewers.entrySet().removeIf(entry ->
                Duration.between(entry.getValue(), now).compareTo(ACTIVE_WINDOW) > 0
        );
        if (viewers.isEmpty()) {
            lastSeenByChannelAndIp.remove(String.valueOf(channelId));
            return new ChannelActiveUsersDTO(channelName, 0);
        }
        return new ChannelActiveUsersDTO(channelName, viewers.size());
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}