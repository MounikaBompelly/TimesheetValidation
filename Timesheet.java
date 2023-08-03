package com.example.KafkaProducer.Entity;

import java.util.List;
import java.util.Map;

public class Timesheet {
	private List<Map<String,String>> records;

    public List<Map<String,String>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String,String>> records) {
        this.records = records;
    }

    /*public static class Record {
        private String name;
        private Map<String, String> entries = new HashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getEntries() {
            return entries;
        }

        public void setEntries(Map<String, String> entries) {
            this.entries = entries;
        }
    }*/
}


