package project.controller.weatehr;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final RestTemplate restTemplate;

    @GetMapping("/weather")
    public String weatherForm() {
        return "practice/country/weatherSearch";
    }

    @PostMapping("/weather")
    public String weatherSubmit(@RequestParam("city") String city, Model model) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=308657c9ee7d6b0b7b8e26ef53c59c8f&lang=kr&units=metric";

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

        // 컨트롤러에서 받은 JSON 데이터 확인
        System.out.println("API Response: " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK) {
            // 서버에서 받은 JSON 데이터를 Thymeleaf에 전달
            model.addAttribute("apiResponse", response.getBody());
        }

        return "practice/country/weatherSearch";
    }

}
