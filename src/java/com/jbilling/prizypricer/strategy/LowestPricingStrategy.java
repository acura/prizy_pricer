package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public enum LowestPricingStrategy implements PriceCalculationStrategy{
	LOWEST;
	
	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {
		
		if (priceList.isEmpty())
			return BigDecimal.ZERO;

		return priceList
				.stream()
				.sorted()
				.min((p1,p2) -> p1.compareTo(p2))
				.get()
				.setScale(2, RoundingMode.HALF_UP);
	}
}
