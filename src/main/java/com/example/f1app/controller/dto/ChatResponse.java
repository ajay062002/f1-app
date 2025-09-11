package com.example.f1app.controller.dto;

import java.util.List;
import java.util.Map;

public class ChatResponse {
    private String answer;
    private Map<String, Object> meta;
    private List<?> data;

    public ChatResponse() {}
    public ChatResponse(String answer) { this.answer = answer; }
    public ChatResponse(String answer, Map<String, Object> meta, List<?> data) {
        this.answer = answer; this.meta = meta; this.data = data;
    }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Map<String, Object> getMeta() { return meta; }
    public void setMeta(Map<String, Object> meta) { this.meta = meta; }

    public List<?> getData() { return data; }
    public void setData(List<?> data) { this.data = data; }
}
