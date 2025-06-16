package net.kkennib.house.controller;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.dto.ServiceResponse;
import net.kkennib.house.dto.StockHoldingDto;
import net.kkennib.house.service.StockHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stock-holding")
public class StockHoldingController {
    @Autowired
    private StockHoldingService stockHoldingService;

    @GetMapping("/all")
    public Mono<ServiceResponse<List<StockHoldingDto>>> getStockHoldingAll() {
        return stockHoldingService.getStockHoldingAll();
    }
}
