FROM openjdk:21
COPY target/moex-iss-1.0-SNAPSHOT.jar /usr/local/service/
ENTRYPOINT ["java", "-jar", "-Dliquibase.hub.mode=off", "/usr/local/service/moex-iss-1.0-SNAPSHOT.jar"]
#dev2
