package com.jbilling.prizypricer.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SimplePricingStrategy implements PriceCalculationStrategy {

	private static final BigDecimal PROFIT_PERCENTAGE = new BigDecimal("0.40");

	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {

		if (priceList.isEmpty())
			return BigDecimal.ZERO;
		BigDecimal average = priceList
									.stream()
									.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2))
									.divide(new BigDecimal(priceList.stream().count()), 2,RoundingMode.HALF_UP)
									.setScale(2, RoundingMode.HALF_UP);

		return average.add(average.multiply(PROFIT_PERCENTAGE)).setScale(2,	RoundingMode.HALF_UP);
	}

}