package net.kkennib.house.service;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.dto.ServiceResponse;
import net.kkennib.house.dto.StockHoldingDto;
import net.kkennib.house.model.StockHolding;
import net.kkennib.house.repository.StockHoldingRepository;
import net.kkennib.house.util.ResponseFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockHoldingService {

    private final StockHoldingRepository stockHoldingRepository;

    public Mono<ServiceResponse<List<StockHoldingDto>>> getStockHoldingAll() {
        return Mono.fromCallable(() -> stockHoldingRepository.findAll(Sort.by(Sort.Direction.ASC, "name")))
                .map(this::convertToDtoList)
                .map(ResponseFactory::createSuccessResponse)
                .onErrorResume(e -> Mono.just(ResponseFactory.createErrorResponse("Failed to fetch stock holdings: " + e.getMessage())));
    }

    private List<StockHoldingDto> convertToDtoList(List<StockHolding> stockHoldings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Asia/Seoul"));

        return stockHoldings.stream()
                .map(sh -> new StockHoldingDto(
                        sh.getName(),
                        sh.getTicker(),
                        sh.getUpdateDate() != null ? formatter.format(sh.getUpdateDate().toInstant()) : "N/A",
                        sh.getPurchasePrice(),
                        sh.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}