FROM adoptopenjdk:11-jdk-hotspot
COPY ./build/libs/*.war /usr/local/tomcat/webapps/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/tomcat/webapps/noname.war"]
