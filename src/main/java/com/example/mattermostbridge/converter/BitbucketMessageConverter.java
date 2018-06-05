package com.example.mattermostbridge.converter;

import com.example.mattermostbridge.model.bitbucket.BitbucketMessage;
import com.example.mattermostbridge.model.bitbucket.BitbucketPullRequest;
import com.example.mattermostbridge.model.mattermost.MattermostAttachment;
import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BitbucketMessageConverter {
    private final String bitbucketUrl;
    private final String username;
    private final String iconUrl;

    @Autowired
    public BitbucketMessageConverter(@Value("${webhook.bitbucket.url}") String bitbucketUrl,
                                     @Value("${webhook.username}") String username,
                                     @Value("${webhook.icon.url}") String iconUrl) {
        this.bitbucketUrl = bitbucketUrl;
        this.username = username;
        this.iconUrl = iconUrl;
    }

    public MattermostMessage convertToMuttermostMessage(BitbucketMessage data) {
        BitbucketPullRequest pr = data.getPullRequest();

        String repositoryUrl = String.format("%s/projects/%s/repos/%s", bitbucketUrl,
                pr.getToRef().getRepository().getProject().getKey(), pr.getToRef().getRepository().getName());
        String prUrl = String.format("%s/pull-requests/%s/overview", repositoryUrl, pr.getId());
        String prLink = String.format("[%s](%s)", pr.getTitle(), prUrl);

        MattermostAttachment attachment = MattermostAttachment.builder()
                .color("#FFFFFF")
                .authorName(String.format("%s (%s)", data.getActor().getDisplayName(), data.getActor().getName()))
                .authorLink(String.format("%s/users/%s", bitbucketUrl, data.getActor().getName()))
                .authorIcon(String.format("%s/users/%s/avatar.png", bitbucketUrl, data.getActor().getName()))
                .text(String.format("%s pull request %s", data.getEventKey().getValue(), prLink))
                .build();

        return MattermostMessage.builder()
                .username(username)
                .iconUrl(iconUrl)
                .attachments(Collections.singletonList(attachment))
                .build();

    }

}
