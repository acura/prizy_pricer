package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public enum IdealPricingStrategy implements PriceCalculationStrategy {
	IDEAL;

	private static final BigDecimal PROFIT_PERCENTAGE = new BigDecimal("0.20");

	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {
		
		if (priceList.isEmpty() || priceList.size() < 5)
			return BigDecimal.ZERO;

		BigDecimal average = AveragePricingStrategy.AVERAGE
				.calculatePrice(priceList.stream()
										 .sorted()
										 .collect(Collectors.toList())
										 .subList(2, priceList.size() - 2));
		
		return average.add(average.multiply(PROFIT_PERCENTAGE))
					  .setScale(2,RoundingMode.HALF_UP);

	}
}
