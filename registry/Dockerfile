
# Use an official Maven image to build the project
FROM maven AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies (this step is cached by Docker)
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean install -DskipTests

# Use an official OpenJDK runtime image
FROM azul/zulu-openjdk:17.0.6-17.40.19

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build stage to container
COPY --from=build /app/target/registry-0.0.1-SNAPSHOT.jar registry-0.0.1-SNAPSHOT.jar

# Expose the application port
# EXPOSE 5054

# Run the application
ENTRYPOINT ["java", "-jar", "registry-0.0.1-SNAPSHOT.jar"]