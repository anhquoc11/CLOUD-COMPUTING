package com.nguyentrananhquoc.library_backend.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Value("${aws.secretsmanager.secret-name:capstone/library/rds}")
    private String secretName;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        logger.info(">>> [AWS Secrets Manager] Dang ket noi toi AWS de lay credentials tu Secret: {}", secretName);

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(request);
            String secretString = response.secretString();

            // Parse JSON tu AWS Secrets Manager
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(secretString);

            String username = rootNode.get("username").asText();
            String password = rootNode.get("password").asText();

            logger.info(">>> [AWS Secrets Manager] LAY CREDENTIALS THANH CONG! Username: {}", username);

            return DataSourceBuilder.create()
                    .url(dbUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .build();

        } catch (Exception e) {
            logger.error(">>> [AWS Secrets Manager] LOI lay credentials tu AWS: {}", e.getMessage(), e);
            throw new RuntimeException("Khong the khoi tao DataSource tu AWS Secrets Manager: " + e.getMessage(), e);
        }
    }
}