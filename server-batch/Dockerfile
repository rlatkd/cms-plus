FROM       gradle:8.7-jdk17-alpine AS build
WORKDIR    /home/gradle/project
COPY       build.gradle settings.gradle ./
RUN        gradle clean build -x test --no-daemon || return 0
COPY       . .
RUN        gradle clean build -x test --no-daemon
FROM       openjdk:17-jdk-slim
WORKDIR    /server
COPY       --from=build /home/gradle/project/build/libs/*.jar app.jar
ENV        TZ=Asia/Seoul
ENV        SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT [ "java", "-jar", "app.jar" ]