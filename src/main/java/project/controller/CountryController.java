package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class CountryController {
    @Autowired
    private final RestTemplate restTemplate;

    @GetMapping("country")
    public String countryForm() {
        return "practice/countrySearch";
    }

    @PostMapping("country")
    public String countrySubmit(@RequestParam("page") String page,
                                @RequestParam("perPage") String perPage,
                                @RequestParam("countryName") String countryName,
                                @RequestParam("countryIso") String countryIso,
                                Model model) {
        // 이 부분에서 API 호출 및 결과를 model에 추가하여 HTML에 표시합니다.
        String apiKey = "w/onpWcWwpLmwUkq6/yqyulugmKO3k7KnTMRfy/hQJs/s7GM9dU474qebwH3AoXxdJl3eE7Ar0CmKOiKaZHdcw==";

        String apiUrl = "http://apis.data.go.kr/1262000/TravelAlarmService0404/getTravelAlarm0404List?" +
                "serviceKey=" + apiKey +
                "&page=" + page +
                "&perPage=" + perPage +
                "&returnType=JSON" +
                "&cond[country_nm::EQ]=" + countryName +
                "&cond[country_iso_alp2::EQ]=" + countryIso;

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, null, String.class);

        model.addAttribute("apiResponse", response.getBody());

        return "practice/countrySearch";
    }
}
