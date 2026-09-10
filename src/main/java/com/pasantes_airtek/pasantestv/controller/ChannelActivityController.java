package com.pasantes_airtek.pasantestv.controller;

import com.pasantes_airtek.pasantestv.dto.ChannelActiveUsersDTO;
import com.pasantes_airtek.pasantestv.service.ChannelActivityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "*")
public class ChannelActivityController {

    private final ChannelActivityService channelActivityService;

    public ChannelActivityController(ChannelActivityService channelActivityService) {
        this.channelActivityService = channelActivityService;
    }

    @GetMapping("/active-users")
    public Map<String, Integer> getActiveUsers() {
        return Map.of("activeUsers", channelActivityService.getActiveUsersCount());
    }

    @GetMapping("/active-users/by-channel")
    public List<ChannelActiveUsersDTO> getActiveUsersByChannel() {
        return channelActivityService.getActiveUsersByChannel();
    }

    @GetMapping("/active-users/by-channel/{channelId}")
    public ChannelActiveUsersDTO getActiveUsersByChannelId(@PathVariable Long channelId) {
        return channelActivityService.getActiveUsersCountByChannel(channelId);
    }
}