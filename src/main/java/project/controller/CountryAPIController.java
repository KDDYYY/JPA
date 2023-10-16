package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class CountryAPIController {

    private final RestTemplate restTemplate;
    @GetMapping("api/country")
    public ResponseEntity<String> country() {
        String apiKey = "w/onpWcWwpLmwUkq6/yqyulugmKO3k7KnTMRfy/hQJs/s7GM9dU474qebwH3AoXxdJl3eE7Ar0CmKOiKaZHdcw==";

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
        String apiKey = "w/onpWcWwpLmwUkq6/yqyulugmKO3k7KnTMRfy/hQJs/s7GM9dU474qebwH3AoXxdJl3eE7Ar0CmKOiKaZHdcw==";

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

