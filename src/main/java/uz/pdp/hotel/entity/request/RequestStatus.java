package uz.pdp.hotel.entity.request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "request_status")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestStatus {
    @Id
    @Column(unique = true)
    private String status;
}
