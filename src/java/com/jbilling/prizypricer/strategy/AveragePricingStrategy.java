package com.jbilling.prizypricer.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AveragePricingStrategy implements PriceCalculationStrategy {

	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {
		
		if (priceList.isEmpty())
			return BigDecimal.ZERO;

		return priceList
				.stream()
				.filter(p -> p!= null)
				.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2))
				.divide(new BigDecimal(priceList.stream().count()), 2,RoundingMode.HALF_UP)
				.setScale(2, RoundingMode.HALF_UP);
	}
}
