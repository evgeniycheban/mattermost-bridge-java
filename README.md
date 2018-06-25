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
`java -jar ./target/mattermost-bridge.jar`

## Docker
* `docker build -t mattermost-bridge .`
* `docker run -p 8080:8080 mattermost-bridge`

## TODO
* tests
