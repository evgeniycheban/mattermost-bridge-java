package com.example.mattermostbridge.model.mattermost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MattermostAttachment {

    @JsonProperty("author_name")
    private String authorName;


    @JsonProperty("author_link")
    private String authorLink;

    @JsonProperty("author_icon")
    private String authorIcon;

    private String color;

    private String text;
}
