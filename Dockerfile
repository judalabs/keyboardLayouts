# Etapa 1: Build com imagem Gradle mínima
FROM gradle:8-jdk21-alpine AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas arquivos de dependências para o cache inicial
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Faz o download das dependências sem build
RUN ./gradlew dependencies --no-daemon

# Copia o restante do código fonte e realiza o build
COPY src ./src
RUN ./gradlew build -x test --no-daemon

# Etapa 2: Runtime otimizado
FROM eclipse-temurin:21-jre-alpine AS runtime

# Reduz o tamanho da imagem removendo arquivos desnecessários
RUN addgroup -S appgroup && adduser -S appuser -G appgroup \
    && mkdir /app && chown appuser:appgroup /app

# Define o usuário não-root
USER appuser

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o artefato gerado na etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Define variáveis de ambiente JVM (opcional para tuning)
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
