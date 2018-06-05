package com.example.mattermostbridge.model.bitbucket;

import lombok.Data;

@Data
public class BitbucketMessage {
    private BitbucketEventType eventKey;
    private BitbucketActor actor;
    private BitbucketPullRequest pullRequest;
}
