FROM maven:3.9.2-eclipse-temurin-17-alpine as build

WORKDIR /app

COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src

RUN mvn -e -B -DskipTests=true package


FROM eclipse-temurin:17-jre-alpine

COPY --from=build /app/target/search-engine-1.jar .

EXPOSE 8080

CMD ["java", "-jar", "search-engine-1.jar"]