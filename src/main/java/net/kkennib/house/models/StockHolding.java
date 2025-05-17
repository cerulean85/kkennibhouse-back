package net.kkennib.house.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Table(name="stock_holdings")
@Entity
@Data@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false)
    private int userId = 0;

    @Column
    private String name;

    @Column
    private String ticker;

    @Column(name="update_date")
    private Timestamp updateDate;

    @Column(name="purchase_price")
    private Float purchasePrice;

    @Column
    private int quantity;
}
