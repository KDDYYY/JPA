package project.controller.country;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class CountryAPIController {

    @Value("${country.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    @GetMapping("api/country")
    public ResponseEntity<String> country() {
        String apiUrl = "http://apis.data.go.kr/1262000/TravelAlarmService0404/getTravelAlarm0404List?" +
                "serviceKey=" + apiKey +
                "&page=1" +
                "&perPage=10" +
                "&returnType=JSON" +
                "&cond[country_nm::EQ]=러시아" +
                "&cond[country_iso_alp2::EQ]=RU";

        return restTemplate.getForEntity(apiUrl, String.class);
    }
    @PostMapping("api/country")
    public ResponseEntity<String> country(@RequestBody RequestCountry requestCountry) {
        String apiUrl = "http://apis.data.go.kr/1262000/TravelAlarmService0404/getTravelAlarm0404List?" +
                "serviceKey=" + apiKey +
                "&page=" + requestCountry.getPage() +
                "&perPage=" + requestCountry.getPerPage() +
                "&returnType=JSON" +
                "&cond[country_nm::EQ]=" + requestCountry.getCountryName() +
                "&cond[country_iso_alp2::EQ]=" + requestCountry.getCountryIso();

        return restTemplate.exchange(apiUrl, HttpMethod.POST, null, String.class);
    }
}

