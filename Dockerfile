# Use the mdsol/java21-jdk image as the base image
FROM mdsol/java21-jdk

# Set metadata for the image
LABEL authors="haris"

# Set an argument for the JAR file
ARG JAR_FILE=target/*.jar

# Copy the executable JAR file from the host to the container
COPY ${JAR_FILE} app.jar

# Specify the command to run the executable JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]