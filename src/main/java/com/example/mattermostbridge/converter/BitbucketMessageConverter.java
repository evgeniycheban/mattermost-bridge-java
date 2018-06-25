package com.example.mattermostbridge.converter;

import com.example.mattermostbridge.model.bitbucket.*;
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
        BitbucketPullRequest pullRequest = data.getPullRequest();
        BitbucketPullRequestSource toRef = pullRequest.getToRef();
        BitbucketRepository repository = toRef.getRepository();
        BitbucketProject project = repository.getProject();
        BitbucketActor actor = data.getActor();
        BitbucketEventType eventKey = data.getEventKey();

        String actorName = actor.getName();
        String repositoryUrl = String.format("%s/projects/%s/repos/%s", bitbucketUrl,
                project.getKey(), repository.getName());
        String pullRequestUrl = String.format("%s/pull-requests/%s/overview", repositoryUrl, pullRequest.getId());
        String pullRequestLink = String.format("[%s](%s)", pullRequest.getTitle(), pullRequestUrl);

        MattermostAttachment attachment = MattermostAttachment.builder()
                .color("#FFFFFF")
                .authorName(String.format("%s (%s)", actor.getDisplayName(), actorName))
                .authorLink(String.format("%s/users/%s", bitbucketUrl, actorName))
                .authorIcon(String.format("%s/users/%s/avatar.png", bitbucketUrl, actorName))
                .text(String.format("%s pull request %s", eventKey.getValue(), pullRequestLink))
                .build();

        return MattermostMessage.builder()
                .username(username)
                .iconUrl(iconUrl)
                .attachments(Collections.singletonList(attachment))
                .build();

    }

}
