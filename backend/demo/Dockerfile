FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.9.1 \
    /lambda-adapter /opt/extensions/lambda-adapter

ENV PORT=8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]