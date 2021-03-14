FROM openjdk:11-slim as builder

WORKDIR /

COPY ["build.gradle", "gradlew", "grpc.gradle", "settings.gradle", "./"]
COPY gradle gradle
RUN chmod +x gradlew
RUN ./gradlew downloadRepos

COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM openjdk:11-slim

WORKDIR /
COPY --from=builder build/libs/*.jar backend.jar

EXPOSE 9000
CMD ["java", "-jar", "backend.jar"]