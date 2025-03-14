package com.freshfood.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.freshfood.dto.response.ProvinceResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceService {

    private final String URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";

    @Value("${spring.ghn.token}")
    private  String token;

    public List<ProvinceResponseDTO> getProvinces() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(URL, HttpMethod.GET, entity, JsonNode.class);

        List<ProvinceResponseDTO> result = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonNode data = response.getBody().get("data");
            if (data != null && data.isArray()) {
                for (JsonNode item : data) {
                    int provinceId = item.get("ProvinceID").asInt();
                    String provinceName = item.get("ProvinceName").asText();
                    result.add(new ProvinceResponseDTO(provinceId, provinceName));
                }
            }
        }
        return result;
    }

}
