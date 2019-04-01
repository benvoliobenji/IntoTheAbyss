FROM gradle:jdk8-alpine AS deploy

EXPOSE 44444/tcp
EXPOSE 44445/udp

ADD build/libs/intoTheAbyss-0.0.1.jar /app/intoTheAbyss.jar

ENTRYPOINT ["java", "-jar", "/app/intoTheAbyss.jar"]