package com.example.mattermostbridge.service;

import com.example.mattermostbridge.model.mattermost.MattermostRequest;

public interface MattermostService {
    void sendMessage(MattermostRequest request);
}
