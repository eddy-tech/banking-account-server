FROM eclipse-temurin:17-jdk-jammy AS base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base AS test
CMD ["./mvnw", "test"]

FROM base AS development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base AS build
RUN ./mvnw package



FROM eclipse-temurin:17-jre-jammy AS production
WORKDIR /app
COPY --from=build /target/banking-account-0.0.1-SNAPSHOT.jar /app/banking-account.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom" , "-jar", "/banking-account.jar"]