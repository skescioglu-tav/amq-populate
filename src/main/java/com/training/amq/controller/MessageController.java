package com.training.amq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.amq.data.FlightInfo;
import com.training.amq.service.MessageSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class MessageController {

    private final MessageSender messageSender;
    private final ObjectMapper objectMapper;

    public MessageController(MessageSender messageSender, ObjectMapper objectMapper) {
        this.messageSender = messageSender;
        this.objectMapper = objectMapper;
    }


    @PostMapping("/create")
    public void sendMessage(@RequestBody FlightInfo flightInfo) throws JsonProcessingException {
        messageSender.send("flight-queue", objectMapper.writeValueAsString(flightInfo));
    }

    @PostMapping("/create-bulk")
    public void sendBulkMessage() throws JsonProcessingException {
        for (int i=0; i<10000; i++) {
            FlightInfo flightInfo = new FlightInfo();
            flightInfo.setFlightNumber("Flight-"+i);
            flightInfo.setAirline("Airline-"+i);
            flightInfo.setAirportCode("Airport-"+i);
            messageSender.send("flight-queue", objectMapper.writeValueAsString(flightInfo));
        }
    }
}