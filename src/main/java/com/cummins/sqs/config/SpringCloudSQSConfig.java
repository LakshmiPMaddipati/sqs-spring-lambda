package com.cummins.sqs.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
@EnableSqs
public class SpringCloudSQSConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {

        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setWaitTimeOut(20); //Long polling, the maximum value of 20 seconds
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(10); //The maximum number of messages you want to read in a single poll
        return factory;
    }
    // Mapping Converter for serializing / deserializing the messages produced / consumed
    @Bean
    public MappingJackson2MessageConverter createMappingJackson2MessageConverter() {

        ObjectMapper objectMapper = new ObjectMapper();

        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();

        messageConverter.setSerializedPayloadClass(String.class);

        messageConverter.setObjectMapper(objectMapper);

        return messageConverter;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }


    private AmazonSQSAsync amazonSQSAsync() {

        AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder =
                AmazonSQSAsyncClientBuilder.standard();

        amazonSQSAsyncClientBuilder.withRegion(region);

        BasicAWSCredentials basicAWSCredentials =
                new BasicAWSCredentials(accessKey, secretKey);

        amazonSQSAsyncClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));

        return amazonSQSAsyncClientBuilder.build();
    }
}
