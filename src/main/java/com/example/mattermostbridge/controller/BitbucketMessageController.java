package com.example.mattermostbridge.controller;

import com.example.mattermostbridge.model.bitbucket.BitbucketMessage;
import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hooks")
@Slf4j
public class BitbucketMessageController {
    private static final String MATTERMOST_URL_TEMPLATE = "%s/%s";
    private final RestTemplate restTemplate;
    private final ConversionService conversionService;
    private final String mattermostUrl;

    @Autowired
    public BitbucketMessageController(RestTemplate restTemplate,
                                      ConversionService conversionService,
                                      @Value("${webhook.mattermost.url}") String mattermostUrl) {
        this.restTemplate = restTemplate;
        this.conversionService = conversionService;
        this.mattermostUrl = mattermostUrl;
    }

    @PostMapping(value = "/{hook}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void hook(@RequestBody BitbucketMessage bitbucketMessage, @PathVariable("hook") String hook) {
        MattermostMessage mattermostMessage = conversionService.convert(bitbucketMessage, MattermostMessage.class);

        String url = String.format(MATTERMOST_URL_TEMPLATE, mattermostUrl, hook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MattermostMessage> entity = new HttpEntity<>(mattermostMessage, headers);

        String response = restTemplate.postForObject(url, entity, String.class);
        log.info("Mattermost response {}", response);
    }
}
