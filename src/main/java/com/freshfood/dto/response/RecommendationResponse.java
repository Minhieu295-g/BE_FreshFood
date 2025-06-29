package com.freshfood.dto.response;

import java.util.List;

public class RecommendationResponse {
    private int user_id;
    private List<List<Object>> recommendations;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<List<Object>> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<List<Object>> recommendations) {
        this.recommendations = recommendations;
    }
}
