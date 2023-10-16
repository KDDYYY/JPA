package project.controller;

import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.service.GoogleMapsService;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final GoogleMapsService mapsService;

//    @GetMapping("/map")
//    public String showMap() {
//        return "practice/map";
//    }


//    @PostMapping("/geocode")
//    public String geocodeAddress(@RequestParam("address") String address, Model model) {
//        try {
//            GeocodingResult[] results = mapsService.geocodeAddress(address);
//            model.addAttribute("results", results);
//        } catch (Exception e) {
//            model.addAttribute("error", "Geocoding failed: " + e.getMessage());
//        }
//        return "practice/map";
//    }

}
