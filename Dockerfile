FROM eclipse-temurin:17-jdk
EXPOSE 8081
ADD target/wordsmith-0.0.1-SNAPSHOT.war wordsmith-0.0.1-SNAPSHOT.war
ENTRYPOINT [ "java", "-Dspring.profiles.active=prod","-jar","/wordsmith-0.0.1-SNAPSHOT.war" ]