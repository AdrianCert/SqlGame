FROM maven:3.8.1-openjdk-15 AS build

COPY src /proj/src
COPY pom.xml /proj
RUN mvn -f /proj/pom.xml clean package

FROM openjdk:15
COPY --from=build /proj/target/quert-handler-1.0.jar /proj/server.jar
COPY --from=build /proj/target/lib /proj/lib
# COPY --from=build /proj/lib /proj/lib

EXPOSE 2021
ENTRYPOINT [ "java", "-jar", "/proj/server.jar" ]
