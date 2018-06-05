package com.example.mattermostbridge.model.bitbucket;

import lombok.Data;

@Data
public class BitbucketRepository {
    private String name;
    private BitbucketProject project;
}
