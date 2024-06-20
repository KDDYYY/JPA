package project.controller.country;

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
import project.domain.APIKey;
import project.service.APIKeyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryController {
    @Autowired
    private final RestTemplate restTemplate;

    private final APIKeyService apiKeyService;

    @GetMapping("/country")
    public String countryForm() {
        return "practice/countrySearch";
    }

    @PostMapping("/country")
    public String countrySubmit(@RequestParam("countryName") String countryName,
                                @RequestParam("countryIso") String countryIso,
                                Model model) {

        List<APIKey> apiKey = apiKeyService.findAll();

        String apiUrl = apiKey.get(0).getEndPoint() +
                "serviceKey=" + apiKey.get(0).getDeCodeKey() +
                "&page=1"  +
                "&perPage=10" +
                "&returnType=JSON" +
                "&cond[country_nm::EQ]=" + countryName +
                "&cond[country_iso_alp2::EQ]=" + countryIso;

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, null, String.class);

        model.addAttribute("apiResponse", response.getBody());

        return "practice/countrySearch";
    }
}
