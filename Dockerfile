FROM maven:3.9.2-eclipse-temurin-17-alpine

WORKDIR /app

COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src

RUN mvn -e -B -DskipTests=true package

EXPOSE 8080

CMD ["java", "-jar", "target/search-engine-1.jar"]