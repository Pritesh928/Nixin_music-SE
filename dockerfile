FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .

RUN apt-get update && apt-get install -y maven python3 python3-pip curl unzip

RUN curl -fsSL https://deno.land/install.sh | sh
ENV DENO_INSTALL="/root/.deno"
ENV PATH="$DENO_INSTALL/bin:$PATH"

RUN ln -s /root/.deno/bin/deno /usr/local/bin/deno

RUN pip3 install -U yt-dlp --break-system-packages

RUN yt-dlp --update-to nightly 2>/dev/null || true

RUN mvn clean package -DskipTests

CMD ["java","-jar","target/nixin_music-0.0.1-SNAPSHOT.jar"]