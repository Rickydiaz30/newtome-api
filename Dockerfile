# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first for better Docker layer caching
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -q -DskipTests package

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Spring Boot default port
EXPOSE 8080

# Optional: set a default profile (change if you use profiles later)
# ENV SPRING_PROFILES_ACTIVE=dev

# Run the app
ENTRYPOINT ["java","-jar","/app/app.jar"]
