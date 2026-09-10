package com.pasantes_airtek.pasantestv.dto;

public class ChannelHeartbeatDTO {
    private Long id;
    private String viewerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViewerId() {
        return viewerId;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }
}
