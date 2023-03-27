package com.cummins.sqs.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);


	@SqsListener(value = "${cloud.aws.end-point.uri}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void receive(String message, Acknowledgment acknowledgment) {


		logger.info("Received message: {}", message);

		try {
			if (!message.contains("fail")){
			logger.info("Processing complete.");
			acknowledgment.acknowledge();}

		} catch (Exception e) {
			logger.error("An error occurred while processing the message: {}", e.getMessage(), e);
			throw e;
		}
	}
	@SqsListener(value = "${cloud.aws.end-point.dlq.uri}")
	public void receiveDlq(String message) throws IOException {
		logger.info(message+"received at DLQ");
	}
}
