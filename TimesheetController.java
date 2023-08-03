package com.example.KafkaProducer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KafkaProducer.Entity.Timesheet;

@RestController
public class TimesheetController {

    @Autowired
    private KafkaTemplate<String, Timesheet> kafkaTemplate;

    //private static final String TOPIC = "timesheet-topic";
    
    @Value("${spring.kafka.topic.name}")
    private String TOPIC;

    @PostMapping("api/upload")
    public String uploadTimesheet(@RequestBody Timesheet timesheet) {
        // Validate and process the timesheet
        // Send the timesheet to Kafka topic
        kafkaTemplate.send(TOPIC, timesheet);
        return "Timesheet uploaded successfully";
    }
}
