package com.example.mattermostbridge.model.bitbucket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = BitbucketEventType.EventTypeDeserializer.class)
public enum BitbucketEventType {
    PR_COMMENT_ADDED("pr:comment:added", "Comment added"),
    PR_REVIEWER_UNAPPROVED("pr:reviewer:unapproved", "Unapproved"),
    PR_REVIEWER_APPROVED("pr:reviewer:approved", "Approved"),
    PR_REVIEWER_NEEDS_WORK("pr:reviewer:needs_work", "Needs work"),
    PR_MERGED("pr:merged", "Merged"),
    PR_DECLINED("pr:declined", "Declined"),
    PR_DELETED("pr:deleted", "Deleted"),
    PR_OPENED("pr:opened", "Opened");

    private final String key;
    private final String value;

    BitbucketEventType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static class EventTypeDeserializer extends StdDeserializer<BitbucketEventType> {
        protected EventTypeDeserializer() {
            super(EventTypeDeserializer.class);
        }

        @Override
        public BitbucketEventType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String key = jsonParser.readValueAs(String.class);

            return Arrays.stream(values())
                    .filter(element -> element.getKey().equals(key))
                    .findAny()
                    .orElseThrow(() -> deserializationContext.weirdStringException(key, BitbucketEventType.class, "No such enum constant"));
        }
    }
}
