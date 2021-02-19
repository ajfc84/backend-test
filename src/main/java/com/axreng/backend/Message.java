package com.axreng.backend;

import java.util.LinkedList;
import java.util.List;

class GetResponse {
    private String id;
    private String status;
    private List<String> urls;

    public GetResponse() {}

    public GetResponse(String id) {
        this.id = id;
        this.status = "active";
        this.urls = new LinkedList<>();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}

class PostRequest {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }
}

class PostResponse {
    private String id;

    public PostResponse() {}

    public PostResponse(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

