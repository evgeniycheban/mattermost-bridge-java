package com.example.mattermostbridge.model.bitbucket;

import lombok.Data;

@Data
public class BitbucketPullRequest {
    private Long id;

    private String title;

    private BitbucketPullRequestSource fromRef;
    private BitbucketPullRequestSource toRef;
}
