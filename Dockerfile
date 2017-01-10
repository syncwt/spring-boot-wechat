FROM openjdk:8-jre
WORKDIR /usr/src/
COPY Columbia/target/Columbia-0.0.1-SNAPSHOT.jar /usr/src/
CMD ["java", "-jar", "Columbia-0.0.1-SNAPSHOT.jar", "--spring.cloud.config.profile=dev",">>", "/usr/src/columbia.log"]
RUN ln -sf /usr/data /etc/localtime
EXPOSE 9001