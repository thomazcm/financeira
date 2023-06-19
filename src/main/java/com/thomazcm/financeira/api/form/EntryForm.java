package com.thomazcm.financeira.api.form;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EntryForm {

    private String name;
    private Double value;
    private Integer day;
    private Integer month;
    private Integer year;

    public String getName() {
        return this.name;
    }

    public BigDecimal getValue() {
        return new BigDecimal(this.value);
    }

    public Integer getDay() {
        return this.day;
    }

    public Integer getMonth() {
        return this.month;
    }

    public Integer getYear() {
        return this.year;
    }

    public LocalDate getDate() {
        return LocalDate.of(year, month, day);
    }

}
