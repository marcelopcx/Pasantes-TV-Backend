package com.pasantes_airtek.pasantestv.service;

import com.pasantes_airtek.pasantestv.model.Channel;
import com.pasantes_airtek.pasantestv.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository repository;

    public ChannelService(ChannelRepository repository) {
        this.repository = repository;
    }

    public Channel save(Channel channel) { return repository.save(channel); }
    public void delete(Channel channel) { repository.deleteById(channel.getId()); }
    public List<Channel> findAll() { return repository.findAll(); }
    public Channel findById(Long id) { return repository.findById(id).orElse(null); }
    public List<Channel> findByName(String name) { return repository.findByName(name); }
}