package com.example.mattermostbridge.model.mattermost;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MattermostRequest {
    private MattermostMessage message;
    private String uri;
}
