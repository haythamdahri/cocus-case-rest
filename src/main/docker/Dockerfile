FROM adoptopenjdk/openjdk11
# Set Working Directory
WORKDIR /app
# Set Jar File Then Copy It
COPY *.jar app.jar
# Set Container Entrypoint
ENTRYPOINT ["java","-jar", "app.jar"]
# Expose 8080 as default port
EXPOSE 8080