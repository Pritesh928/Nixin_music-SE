FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .

RUN apt-get update && apt-get install -y maven python3 python3-pip

RUN pip3 install yt-dlp --break-system-packages

RUN mvn clean package -DskipTests

CMD ["java","-jar","target/nixin_music-0.0.1-SNAPSHOT.jar"]