# FROM openjdk:17-jdk-alpine3.13
# ARG JAR_FILE=./build/libs/testest.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


FROM       gradle:8.7-jdk17-alpine AS build
WORKDIR    /home/gradle/project
COPY       build.gradle settings.gradle ./
RUN        gradle build -x test --no-daemon || return 0
COPY       . .
RUN        gradle build -x test --no-daemon
FROM       openjdk:17-jdk-slim
WORKDIR    /server
COPY       --from=build /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
# VOLUME     /tmp
# EXPOSE     8080