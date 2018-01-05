package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public enum HighestPricingStrategy implements PriceCalculationStrategy{
	INSTANCE;
	
	@Override
	public BigDecimal calculatePrice(List<BigDecimal> priceList) {
		Optional<BigDecimal> optional = priceList.stream().sorted().max((p1,p2) -> p1.compareTo(p2));
		if(optional.isPresent())
			return optional.get().setScale(2, RoundingMode.HALF_UP);
		return BigDecimal.ZERO;
	}
}
