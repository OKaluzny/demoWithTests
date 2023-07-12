FROM openjdk:11
ADD /target/demo-app.jar demo-app.jar
ENTRYPOINT ["java", "-jar", "demo-app.jar"]