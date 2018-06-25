package com.example.mattermostbridge.service;

import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import com.example.mattermostbridge.model.mattermost.MattermostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MattermostServiceImpl implements MattermostService {
    private static final String MATTERMOST_URL_TEMPLATE = "%s/%s";
    private final RestTemplate restTemplate;
    private final String mattermostUrl;

    @Autowired
    public MattermostServiceImpl(RestTemplate restTemplate,
                                 @Value("${webhook.mattermost.url}") String mattermostUrl) {
        this.restTemplate = restTemplate;
        this.mattermostUrl = mattermostUrl;
    }

    @Override
    public void sendMessage(MattermostRequest request) {
        String url = String.format(MATTERMOST_URL_TEMPLATE, mattermostUrl, request.getUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MattermostMessage> entity = new HttpEntity<>(request.getMessage(), headers);

        restTemplate.postForObject(url, entity, String.class);
    }
}
