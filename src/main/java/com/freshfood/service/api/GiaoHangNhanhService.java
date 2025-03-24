package com.freshfood.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.freshfood.dto.response.DeliveryFeeResponseDTO;
import com.freshfood.dto.response.DistrictResponseDTO;
import com.freshfood.dto.response.ProvinceResponseDTO;
import com.freshfood.dto.response.WardResponseDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiaoHangNhanhService {

    private final String URL_PROVINCE = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private final String URL_DISTRICT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private final String URL_WARD = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    private final String API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";

    @Value("${spring.ghn.token}")
    private  String token;

    @Value("${spring.ghn.shop-id}")
    private String shopId;

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

    public  DeliveryFeeResponseDTO getData(String addressDetail, String wardId, String districtId) {
        String jsonData = String.format("""
                {
                    "payment_type_id": 2,
                    "note": "Tintest 123",
                    "required_note": "KHONGCHOXEMHANG",
                    "from_name": "TinTest124",
                    "from_phone": "0345778312",
                    "from_address": "Khu Phố 6, Phường Linh Trung, Quận Thủ Đưc, Hồ Chí Minh, Vietnam",
                    "from_ward_name": "Phường Linh Trung",
                    "from_district_name": "Quận Thủ Đức",
                    "from_province_name": "HCM",
                    "return_phone": "0332190444",
                    "return_address": "39 NTT",
                    "return_district_id": null,
                    "return_ward_code": "",
                    "client_order_code": "",
                    "to_name": "TinTest124",
                    "to_phone": "0987654321",
                    "to_address": "%s",
                    "to_ward_code": "%s",
                    "to_district_id": %s,
                    "cod_amount": 500000,
                    "content": "Theo New York Times",
                    "weight": 5000,
                    "length": 1,
                    "width": 19,
                    "height": 10,
                    "pick_station_id": 1444,
                    "deliver_station_id": null,
                    "insurance_value": 1000000,
                    "service_id": 0,
                    "service_type_id": 2,
                    "coupon": null,
                    "pick_shift": [2],
                    "items": [
                        {
                            "name": "Áo Polo",
                            "code": "Polo123",
                            "quantity": 5,
                            "price": 200000,
                            "length": 12,
                            "width": 12,
                            "height": 12,
                            "weight": 3200,
                            "category": {
                                "level1": "Áo"
                            }
                        }
                    ]
                }
                """, addressDetail, wardId, districtId);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("ShopId", shopId);
            connection.setRequestProperty("Token", token);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonData.getBytes("utf-8"));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to call API, response code: " + responseCode);
                return null;
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String response = in.lines().reduce("", (acc, line) -> acc + line);
                JsonObject data = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("data");
                return DeliveryFeeResponseDTO.builder()
                        .deliveryFee(data.get("total_fee").getAsString())
                        .deliveryDate(data.get("expected_delivery_time").getAsString())
                        .build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DeliveryFeeResponseDTO getData2(String addressDetail, String wardId, String districtId) {
        DeliveryFeeResponseDTO  dataRespone = new DeliveryFeeResponseDTO();
        String apiUrl = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
        String token = "80117275-f321-11ee-8bfa-8a2dda8ec551";
        String shopId = "0345778312-191687";

        // JSON data to send in the POST request
        String jsonData = "{\n" +
                "    \"payment_type_id\": 2,\n" +
                "    \"note\": \"Tintest 123\",\n" +
                "    \"required_note\": \"KHONGCHOXEMHANG\",\n" +
                "    \"from_name\": \"TinTest124\",\n" +
                "    \"from_phone\": \"0345778312\",\n" +
                "    \"from_address\": \"Khu Phố 6, Phường Linh Trung, Quận Thủ Đưc, Hồ Chí Minh, Vietnam\",\n" +
                "    \"from_ward_name\": \"Phường Linh Trung\",\n" +
                "    \"from_district_name\": \"Quận Thủ Đức\",\n" +
                "    \"from_province_name\": \"HCM\",\n" +
                "    \"return_phone\": \"0332190444\",\n" +
                "    \"return_address\": \"39 NTT\",\n" +
                "    \"return_district_id\": null,\n" +
                "    \"return_ward_code\": \"\",\n" +
                "    \"client_order_code\": \"\",\n" +
                "    \"to_name\": \"TinTest124\",\n" +
                "    \"to_phone\": \"0987654321\",\n" +
                "    \"to_address\": \"" + addressDetail + "\",\n" +
                "    \"to_ward_code\": \"" + wardId + "\",\n" +
                "    \"to_district_id\": " + districtId + ",\n" +
                "    \"cod_amount\": 500000,\n" +
                "    \"content\": \"Theo New York Times\",\n" +
                "    \"weight\": 5000,\n" +
                "    \"length\": 1,\n" +
                "    \"width\": 19,\n" +
                "    \"height\": 10,\n" +
                "    \"pick_station_id\": 1444,\n" +
                "    \"deliver_station_id\": null,\n" +
                "    \"insurance_value\": 1000000,\n" +
                "    \"service_id\": 0,\n" +
                "    \"service_type_id\": 2,\n" +
                "    \"coupon\": null,\n" +
                "    \"pick_shift\": [2],\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"name\": \"Áo Polo\",\n" +
                "            \"code\": \"Polo123\",\n" +
                "            \"quantity\": 5,\n" +
                "            \"price\": 200000,\n" +
                "            \"length\": 12,\n" +
                "            \"width\": 12,\n" +
                "            \"height\": 12,\n" +
                "            \"weight\": 3200,\n" +
                "            \"category\": {\n" +
                "                \"level1\": \"Áo\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("ShopId", shopId);
            connection.setRequestProperty("Token", token);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject data = jsonResponse.getAsJsonObject("data");
            String totalFee = data.get("total_fee").getAsString();
            String deliveryDate = data.get("expected_delivery_time").getAsString();
            dataRespone.setDeliveryFee(totalFee);
            dataRespone.setDeliveryDate(deliveryDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataRespone;
    }

    public static void main(String[] args) {
        GiaoHangNhanhService service = new GiaoHangNhanhService();
        System.out.println(service.getData2("Thu duc hcm city", "20308", "1444").toString());
        System.out.println("OKOK");
    }

}
