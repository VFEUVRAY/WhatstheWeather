package com.example.myapplication.Meteo;

import java.util.List;
import java.util.Map;

public class Forecast {
    private String cod;
    private Double message;
    private Integer cnt;
    private List<Object> list;
    private Map<String, Object> city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Map<String, Object> getCity() {
        return city;
    }

    public void setCity(Map<String, Object> city) {
        this.city = city;
    }
}
