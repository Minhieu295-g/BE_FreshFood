package com.freshfood.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.freshfood.dto.response.DistrictResponseDTO;
import com.freshfood.dto.response.ProvinceResponseDTO;
import com.freshfood.dto.response.WardResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiaoHangNhanhService {

    private final String URL_PROVINCE = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private final String URL_DISTRICT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private final String URL_WARD = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    @Value("${spring.ghn.token}")
    private  String token;

    public List<ProvinceResponseDTO> getProvinces() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(URL_PROVINCE, HttpMethod.GET, entity, JsonNode.class);

        List<ProvinceResponseDTO> result = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonNode data = response.getBody().get("data");
            if (data != null && data.isArray()) {
                for (JsonNode item : data) {
                    int provinceId = item.get("ProvinceID").asInt();
                    if(provinceId ==286) continue;
                    String provinceName = item.get("ProvinceName").asText();
                    result.add(new ProvinceResponseDTO(provinceId, provinceName));
                }
            }
        }
        return result;
    }

    public List<DistrictResponseDTO> getDistricts(int provinceId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String urlWithParams = URL_DISTRICT + "?province_id=" + provinceId;

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        List<DistrictResponseDTO> result = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonNode data = response.getBody().get("data");
            if (data != null && data.isArray()) {
                for (JsonNode item : data) {
                    int districtId = item.get("DistrictID").asInt();
                    String districtName = item.get("DistrictName").asText();
                    result.add(new DistrictResponseDTO(districtId, districtName));
                }
            }
        }
        return result;
    }

    public List<WardResponseDTO> getWards(int districtId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String urlWithParams = URL_WARD + "?district_id=" + districtId;

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        List<WardResponseDTO> result = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonNode data = response.getBody().get("data");
            if (data != null && data.isArray()) {
                for (JsonNode item : data) {
                    int wardId = item.get("WardCode").asInt();
                    String wardName = item.get("WardName").asText();
                    result.add(new WardResponseDTO(wardId, wardName));
                }
            }
        }
        return result;
    }



}
