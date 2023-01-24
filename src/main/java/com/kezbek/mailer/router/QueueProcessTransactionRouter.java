package com.kezbek.mailer.router;

import com.alibaba.fastjson.JSON;
import com.kezbek.mailer.services.EmailService;
import com.kezbek.mailer.dto.UserTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws2.sqs.Sqs2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueProcessTransactionRouter extends RouteBuilder {
    @Value("${integration.aws-sqs.access-key}")
    String accessKey;

    @Value("${integration.aws-sqs.secret-key}")
    String secretKey;

    @Value("${integration.aws-sqs.queue-name}")
    String queue;

    @Value("${integration.aws-sqs.region}")
    String region;

    @Autowired
    EmailService emailService;

    @Override
    public void configure() {
        String address = "aws2-sqs://".concat(queue).concat("?accessKey=")
                .concat(accessKey).concat("&secretKey=RAW(".concat(secretKey)
                        .concat(")&region=".concat(region)));
        log.info("Transaction - transaction consumer is running [{}]", address);
        from(address)
                .setHeader(Sqs2Constants.SQS_OPERATION, constant("listQueues"))
                .process(exchange -> {
                    UserTransaction userTransactionResponse = JSON.parseObject(String.valueOf(exchange.getMessage().getBody()), UserTransaction.class);
                    log.info("user transaction consumer exchange [{}]", userTransactionResponse);
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setFrom("syams.arie@gmail.com");
                    simpleMailMessage.setSubject("Cashback Top Up Success");
                    simpleMailMessage.setTo(userTransactionResponse.getEmail());
                    emailService.sendEmailCashback(simpleMailMessage, userTransactionResponse);
                });
    }
}
