package net.kkennib.house.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.*;

@Table(name = "rssmedia")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RssMedia {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;

    @Column
    private String name;

    @Column
    private String rssUrl;

    @Column
    private String idx;

//    public RssMedia(String name, String rss_url, String idx) {
//        this.name = name;
//        this.rss_url = rss_url;
//        this.idx = idx;
//    }
}
