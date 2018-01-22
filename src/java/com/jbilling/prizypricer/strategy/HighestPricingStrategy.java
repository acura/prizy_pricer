package com.jbilling.prizypricer.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class HighestPricingStrategy implements PriceCalculationStrategy {

	@Override
	public BigDecimal calculatePrice(final List<BigDecimal> priceList) {

		if (priceList.isEmpty())
			return BigDecimal.ZERO;
		
		return priceList
					  .stream()
					  .filter(p -> p != null)
					  .max((b1,b2) -> b1.compareTo(b2))
					  .get()
					  .setScale(2, RoundingMode.HALF_UP);
	}
}
