package com.example.mattermostbridge.converter;

import com.example.mattermostbridge.model.bitbucket.*;
import com.example.mattermostbridge.model.mattermost.MattermostAttachment;
import com.example.mattermostbridge.model.mattermost.MattermostMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BitbucketMessageConverter implements Converter<BitbucketMessage, MattermostMessage> {
    private static final String COLOR = "#FFFFFF";
    private static final String AUTHOR_NAME_TEMPLATE = "%s (%s)";
    private static final String AUTHOR_LINK_TEMPLATE = "%s/users/%s";
    private static final String AUTHOR_ICON_TEMPLATE = "%s/users/%s/avatar.png";
    private static final String TEXT_TEMPLATE = "%s pull request %s";
    private static final String REPOSITORY_URL_TEMPLATE = "%s/projects/%s/repos/%s";
    private static final String PULL_REQUEST_URL_TEMPLATE = "%s/pull-requests/%s/overview";
    private static final String PULL_REQUEST_LINK_TEMPLATE = "[%s](%s)";
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

    @Override
    public MattermostMessage convert(BitbucketMessage data) {
        BitbucketPullRequest pullRequest = data.getPullRequest();
        BitbucketPullRequestSource toRef = pullRequest.getToRef();
        BitbucketRepository repository = toRef.getRepository();
        BitbucketProject project = repository.getProject();
        BitbucketActor actor = data.getActor();
        BitbucketEventType eventKey = data.getEventKey();

        String actorName = actor.getName();
        String repositoryUrl = String.format(REPOSITORY_URL_TEMPLATE, bitbucketUrl, project.getKey(), repository.getName());
        String pullRequestUrl = String.format(PULL_REQUEST_URL_TEMPLATE, repositoryUrl, pullRequest.getId());
        String pullRequestLink = String.format(PULL_REQUEST_LINK_TEMPLATE, pullRequest.getTitle(), pullRequestUrl);

        MattermostAttachment attachment = MattermostAttachment.builder()
                .color(COLOR)
                .authorName(String.format(AUTHOR_NAME_TEMPLATE, actor.getDisplayName(), actorName))
                .authorLink(String.format(AUTHOR_LINK_TEMPLATE, bitbucketUrl, actorName))
                .authorIcon(String.format(AUTHOR_ICON_TEMPLATE, bitbucketUrl, actorName))
                .text(String.format(TEXT_TEMPLATE, eventKey.getValue(), pullRequestLink))
                .build();

        return MattermostMessage.builder()
                .username(username)
                .iconUrl(iconUrl)
                .attachments(Collections.singletonList(attachment))
                .build();

    }

}
