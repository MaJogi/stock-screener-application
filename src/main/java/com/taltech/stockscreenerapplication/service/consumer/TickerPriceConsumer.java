package com.taltech.stockscreenerapplication.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.repository.FinancialsDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class TickerPriceConsumer {

    @Autowired
    /* default */ FinancialsDailyRepository financialsDailyRepository;

    public void updatePrices() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("screener-price-queue", false, false, false, null);
        channel.basicConsume("screener-price-queue", true, (consumerTag, message) -> {
            String receivedMessage = new String(message.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            FinancialsDaily financialsDaily = objectMapper.readValue(receivedMessage, FinancialsDaily.class);
            financialsDailyRepository.updatePrice(financialsDaily.getPrice(), financialsDaily.getTicker_id());
        }, consumerTag -> { });
    }
}
