package com.sertis.miniblog.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardRequest {

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("content")
    private String content;

    @JsonProperty("category_id")
    private Integer categoryId;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CardRequest{" +
                "topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
