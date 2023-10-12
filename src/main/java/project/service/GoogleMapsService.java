package project.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//구글 맵 쳐리 프론트에서 모두 처리중 아직 사용 x
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleMapsService {
    @Value("${google.maps.api.key}")
    private String apiKey;

    public GeocodingResult[] geocodeAddress(String address) throws Exception { //주소 좌표로
        GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results;
    }


}
