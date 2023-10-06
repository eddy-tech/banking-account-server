FROM openjdk:17-jdk
COPY  target/banking-account-0.0.1-SNAPSHOT.jar /app/banking-account.jar
EXPOSE 8080
CMD ["java", "-jar", "/banking-account.jar"]