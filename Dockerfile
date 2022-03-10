FROM openjdk:17 as build

WORKDIR /code
COPY . /code/
RUN chmod u+x ./mvnw
RUN ./mvnw package -DskipTests
RUN mv /code/target/*.jar /code/app.jar

FROM openjdk:17-alpine
WORKDIR /code
COPY --from=build /code/app.jar /code/app.jar
CMD ["java", "-jar", "/code/app.jar"]
EXPOSE 8080
