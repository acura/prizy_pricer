package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public enum IdealPricingStrategy implements PriceCalculationStrategy {
	INSTANCE;

	private static final BigDecimal PROFIT_PERCENTAGE = new BigDecimal("0.20");

	@Override
	public BigDecimal calculatePrice(List<BigDecimal> priceList) {
		if (priceList.size() < 5)
			return BigDecimal.ZERO;

		Collections.sort(priceList);
		BigDecimal average = AveragePricingStrategy.INSTANCE
							.calculatePrice(priceList.subList(2, priceList.size() - 2));
		return average.add(average.multiply(PROFIT_PERCENTAGE))
					  .setScale(2,RoundingMode.HALF_UP);

	}
}
