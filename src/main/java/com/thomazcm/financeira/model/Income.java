package com.thomazcm.financeira.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Entity;

@Entity
public class Income extends Entry{

    public Income() {}
    public Income(String name, BigDecimal value, LocalDate date) {
        super(name, value, date);
    }


}
