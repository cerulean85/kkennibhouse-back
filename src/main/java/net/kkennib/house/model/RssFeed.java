package net.kkennib.house.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "rssfeed")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String title;

    @Column
    private String link;

    @Column
    private String mediaIdx;

    @Column
    private String updatedAt;


}
