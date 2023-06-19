package com.thomazcm.financeira.api.dto;

import com.thomazcm.financeira.model.Entry;

public class EntryDto {

    private String name;
    private Double value;
    private Integer dia;
    private Integer mes;
    private Integer ano;


    public EntryDto(Entry entry) {
        this.name = entry.getName();
        this.value = entry.getValue().doubleValue();
        this.dia = entry.getDate().getDayOfMonth();
        this.mes = entry.getDate().getMonthValue();
        this.ano = entry.getDate().getDayOfYear();
    }

    public String getName() {
        return this.name;
    }

    public Double getValue() {
        return this.value;
    }

    public Integer getDia() {
        return this.dia;
    }

    public Integer getMes() {
        return this.mes;
    }

    public Integer getAno() {
        return this.ano;
    }



}
