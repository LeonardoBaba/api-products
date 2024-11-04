FROM eclipse-temurin:22-jdk as build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:22-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 64064

CMD ["java", "-jar", "app.jar"]
