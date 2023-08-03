package com.example.KafkaProducer.Consumer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.KafkaProducer.Entity.Timesheet;

@Component
public class TimesheetConsumer {
	Logger logger = LoggerFactory.logger(TimesheetConsumer.class);
    
    private final Map<String, String> employeeValidationResults = new HashMap<>();

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTimesheet(Timesheet timesheet) {
        logger.info("Received message: " + timesheet);
        processTimesheet(timesheet);
    }

    private void processTimesheet(Timesheet timesheet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Map<String, String> record: timesheet.getRecords()) {
            String employeeName = record.get("name");
            logger.info("Processing employee: " + employeeName);
            
           
                boolean allValid = true;
                for (Map.Entry<String, String> entry : record.entrySet()) {
                	logger.info("inside for");
                    String dateKey = entry.getKey();
                    
                    logger.info("date :"+dateKey);
                    

                    if(!dateKey.equals("name")) {
                    try {
                    	int  hours = Integer.parseInt(entry.getValue());
                    	logger.info("inside try");
                        java.util.Date date = dateFormat.parse(dateKey);
                        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                        String dayOfWeek = dayFormat.format(date);
                        logger.info("day of week:"+dayOfWeek);

                        //if (((dayOfWeek.equalsIgnoreCase("Saturday") || dayOfWeek.equalsIgnoreCase("Sunday"))
                                //&& hours!=0)||(dayOfWeek.equalsIgnoreCase("Monday")||dayOfWeek.equalsIgnoreCase("Tuesday")||dayOfWeek.equalsIgnoreCase("Wednesday"))||dayOfWeek.equalsIgnoreCase("Thursday")||dayOfWeek.equalsIgnoreCase("Friday"))&&hours!=8) {
                        if(((dayOfWeek.equalsIgnoreCase("Saturday") || dayOfWeek.equalsIgnoreCase("Sunday"))
                                && hours!=0)||((dayOfWeek.equalsIgnoreCase("Monday")||dayOfWeek.equalsIgnoreCase("Tuesday")||dayOfWeek.equalsIgnoreCase("Wednesday")||dayOfWeek.equalsIgnoreCase("Thursday")||dayOfWeek.equalsIgnoreCase("Friday"))&&hours!=8)) {
                        
                        logger.info("inside if line 52");
                        	allValid = false;
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                }

                if (allValid) {
                    employeeValidationResults.put(employeeName, "processed");
                } else {
                    employeeValidationResults.put(employeeName, "rejected");
                }
            } 
        

        for (Map.Entry<String, String> entry : employeeValidationResults.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
