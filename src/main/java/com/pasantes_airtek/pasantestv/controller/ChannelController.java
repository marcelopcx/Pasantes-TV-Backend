package com.pasantes_airtek.pasantestv.controller;

import com.pasantes_airtek.pasantestv.model.Channel;
import com.pasantes_airtek.pasantestv.service.ChannelService;
import com.pasantes_airtek.pasantestv.service.ChannelActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channels")
@CrossOrigin(origins = "*")
public class ChannelController {
    private final ChannelService service;
    private final ChannelActivityService channelActivityService;

    public ChannelController(ChannelService service, ChannelActivityService channelActivityService) {
        this.service = service;
        this.channelActivityService = channelActivityService;
    }

    @PostMapping
    public Channel save(@RequestBody Channel channel) { return service.save(channel); }
    @DeleteMapping
    public void delete(@RequestBody Channel channel) { service.delete(channel); }
    @GetMapping("/list")
    public List<Channel> getChannelList() { return service.findAll(); }
    @PostMapping("/id")
    public Channel getChannelById(@RequestBody Channel channel) { return service.findById(channel.getId()); }
    @PostMapping("/name")
    public List<Channel> getChannelByName(@RequestBody Channel channel) { return service.findByName(channel.getName()); }
    @PostMapping("/category")
    public List<Channel> getChannelByCategory(@RequestBody Channel channel) { return service.findByCategory(channel.getCategory()); }
    @PostMapping("/heartbeat")
    public void heartbeat(@RequestBody Channel channel, String viewerId) {
        channelActivityService.registerChannelRequest(viewerId, channel.getId());
    }
    @PostMapping("/leave")
    public void leave(@RequestBody Channel channel, String viewerId) {
        channelActivityService.unregisterChannelRequest(viewerId, channel.getId());
    }
}
