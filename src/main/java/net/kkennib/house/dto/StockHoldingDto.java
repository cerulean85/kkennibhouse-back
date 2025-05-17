package net.kkennib.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockHoldingDto {
    private String name;
    private String ticker;
    private String updateDate; // 문자열로 변환
    private Float purchasePrice;
    private int quantity;
}