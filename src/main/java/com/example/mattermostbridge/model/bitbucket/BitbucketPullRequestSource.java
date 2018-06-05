package com.example.mattermostbridge.model.bitbucket;

import lombok.Data;

@Data
public class BitbucketPullRequestSource {
    private String id;
    private String displayId;
    private BitbucketRepository repository;
}
