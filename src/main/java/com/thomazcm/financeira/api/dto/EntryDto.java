package com.thomazcm.financeira.api.dto;

import com.thomazcm.financeira.model.Entry;

public class EntryDto {

    private Long id;
    private String name;
    private Double value;
    private int year;
    private int month;
    private int day;


    public EntryDto(Entry entry) {
        this.id = entry.getId();
        this.name = entry.getName();
        this.value = entry.getValue().doubleValue();
        this.day = entry.getDate().getDayOfMonth();
        this.month = entry.getDate().getMonthValue();
        this.year = entry.getDate().getYear();
    }
    
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getValue() {
        return this.value;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

}
