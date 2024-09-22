package mk.ukim.finki.diansvinarii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Entity
@Table(name = "[Winery]")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Winery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String phone;
    String website;
    Double longitude;
    Double latitude;
    String openHours;
    String closeHours;

    public Winery(String name, String phone, String website, Double lon, Double lat, String openHours, String closeHours) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.longitude = lon;
        this.latitude = lat;
        this.openHours = openHours;
        this.closeHours = closeHours;
    }


    public static Winery csvItemToWinery(String line){
        String[] items = line.split(",");
        return new Winery(items[2], items[3], items[4], Double.parseDouble(items[0]), Double.parseDouble(items[1]), items[5], items[6]);
    }
}
