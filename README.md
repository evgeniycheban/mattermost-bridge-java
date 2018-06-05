# Mattermost bridge Java

## Config
* application.properties
```properties
webhook.mattermost.url=<mattermost_url>
webhook.bitbucket.url=<bitbucket_url>
webhook.username=<message_username>
webhook.icon.url=<message_icon_url>
```

## Build
`mvn package`

## Run
`java -jar ./target/mattermost-bridge-0.0.1-SNAPSHOT.jar`

## TODO
* tests
