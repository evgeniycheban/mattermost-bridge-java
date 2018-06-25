FROM openjdk:8

MAINTAINER Evgeniy Cheban <mister.cheban@gmail.com>

ADD ./target/mattermost-bridge.jar /app/
CMD ["java", "-Xmx256m", "-XX:PermSize=128m", "-XX:MaxPermSize:128m", "-jar", "/app/mattermost-bridge.jar"]

EXPOSE 8080
