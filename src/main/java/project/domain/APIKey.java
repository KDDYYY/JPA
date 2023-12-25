package project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class APIKey {
    @Id @GeneratedValue
    @Column(name = "API_KEY_ID")
    private Long id;

    private String endPoint;

    private String deCodeKey;

    public APIKey(String endPoint, String deCodeKey) {
        this.endPoint = endPoint;
        this.deCodeKey = deCodeKey;
    }

}
