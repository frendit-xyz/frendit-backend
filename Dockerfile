FROM openjdk:17-alpine
ARG PORT
WORKDIR /backend
RUN apk update && apk add dos2unix
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN dos2unix mvnw
RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
COPY . .
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "target/frendit.jar"]
