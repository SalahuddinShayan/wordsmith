FROM openjdk:17
EXPOSE 8081
ADD target/wordsmith-0.0.1-SNAPSHOT.war wordsmith-0.0.1-SNAPSHOT.war
ENTRYPOINT [ "java", "-Dspring.profiles.active=prod","-jar","/wordsmith-0.0.1-SNAPSHOT.war" ]