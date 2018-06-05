package com.example.mattermostbridge.controller;

import com.example.mattermostbridge.converter.BitbucketMessageConverter;
import com.example.mattermostbridge.model.bitbucket.BitbucketMessage;
import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hooks")
@Slf4j
public class BitbucketWebhookController {
    private final RestTemplate restTemplate;
    private final BitbucketMessageConverter converter;
    private final String mattermostUrl;

    @Autowired
    public BitbucketWebhookController(RestTemplate restTemplate,
                                      BitbucketMessageConverter converter,
                                      @Value("${webhook.mattermost.url}") String mattermostUrl) {
        this.restTemplate = restTemplate;
        this.converter = converter;
        this.mattermostUrl = mattermostUrl;
    }

    @PostMapping(value = "/{hook}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void hook(@RequestBody BitbucketMessage bitbucketMessage,
                     @PathVariable("hook") String hook) {

        MattermostMessage mattermostMessage = converter.convertToMuttermostMessage(bitbucketMessage);

        String url = String.format("%s/%s", mattermostUrl, hook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MattermostMessage> entity = new HttpEntity<>(mattermostMessage, headers);

        String res = restTemplate.postForObject(url, entity, String.class);
        log.info("Response {}", res);
    }
}
