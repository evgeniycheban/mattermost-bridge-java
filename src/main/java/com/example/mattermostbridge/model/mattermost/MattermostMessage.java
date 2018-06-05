package com.example.mattermostbridge.model.mattermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MattermostMessage {
    private String username;

    @JsonProperty("icon_url")
    private String iconUrl;

    private List<MattermostAttachment> attachments;
}
