package com.example.mattermostbridge.controller;

import com.example.mattermostbridge.model.bitbucket.BitbucketMessage;
import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import com.example.mattermostbridge.model.mattermost.MattermostRequest;
import com.example.mattermostbridge.service.MattermostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hooks")
public class BitbucketMessageController {
    private final ConversionService conversionService;
    private final MattermostService mattermostService;

    @Autowired
    public BitbucketMessageController(ConversionService conversionService,
                                      MattermostService mattermostService) {
        this.conversionService = conversionService;
        this.mattermostService = mattermostService;
    }

    @PostMapping(value = "/{hook}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void hook(@RequestBody BitbucketMessage bitbucketMessage, @PathVariable("hook") String hook) {
        MattermostMessage mattermostMessage = conversionService.convert(bitbucketMessage, MattermostMessage.class);
        MattermostRequest request = MattermostRequest.builder()
                .message(mattermostMessage)
                .uri(hook)
                .build();
        mattermostService.sendMessage(request);
    }
}
