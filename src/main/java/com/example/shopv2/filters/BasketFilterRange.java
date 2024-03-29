package com.example.shopv2.filters;

import com.example.shopv2.model.Basket;
import com.example.shopv2.repository.BasketRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class BasketFilterRange extends FilterRangeAbstract{

    private final BasketRepository basketRepository;

    public BasketFilterRange(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    protected String getFilterName() {
        return "BasketFilter";
    }

    @Override
    protected List<Basket> getAllEntityBetweenDate(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return basketRepository.findAllByBetweenDate(fromDate, toDate);
    }
}
