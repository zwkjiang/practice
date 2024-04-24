package com.example.bean;

public class EventBean {

    private String type;

    private Object object;

    public String getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public EventBean(String type, Object object) {
        this.type = type;
        this.object = object;
    }
}
