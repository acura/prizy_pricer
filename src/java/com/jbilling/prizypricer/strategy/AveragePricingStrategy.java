package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public enum AveragePricingStrategy implements PriceCalculationStrategy {
	AVERAGE;

	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {
		
		if (priceList.isEmpty())
			return BigDecimal.ZERO;

		return priceList
				.stream()
				.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2))
				.divide(new BigDecimal(priceList.stream().count()), 2,RoundingMode.HALF_UP)
				.setScale(2, RoundingMode.HALF_UP);
	}
}
