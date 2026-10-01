FROM registry.access.redhat.com/ubi9/openjdk-21:latest as builder

COPY --chown=default . .

# RUN whoami && pwd &&  ls -lart .

RUN mvn clean package
  
# RUN ls -lart ./target


FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:latest

# RUN whoami && pwd && ls -lart .

COPY --from=builder /home/default/target/quarkus-app ./quarkus-app

WORKDIR ./quarkus-app

CMD ["java", "-jar", "quarkus-run.jar"]