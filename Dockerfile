FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DsipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/OnlineLibraryManagementSystem-0.0.1-SNAPSHOT.jar OnlineLibraryManagementSystem.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "OnlineLibraryManagementSystem.jar"]