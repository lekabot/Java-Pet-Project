package edu.school21.app.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.auth.MegaAuthCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public MegaSession megaSession() {
        return Mega.login(MegaAuthCredentials.createFromEnvVariables());
    }

    @Bean
    public AmazonS3 s3Client() {
        String accessKey = Dotenv.load().get("YANDEX_CLOUD_ACCESS_KEY");
        String secretKey = Dotenv.load().get("YANDEX_CLOUD_SECRET_KEY");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "https://storage.yandexcloud.net", null // Установите регион в null
                ))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withPathStyleAccessEnabled(true) // Включите, если необходимо
                .build();
    }
}
