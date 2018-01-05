package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public enum SimplePricingStrategy implements PriceCalculationStrategy{
	INSTANCE;
	
	private static final BigDecimal PROFIT_PERCENTAGE = new BigDecimal("0.20");

	@Override
	public BigDecimal calculatePrice(List<BigDecimal> priceList) {
		if (priceList.isEmpty())
			return BigDecimal.ZERO;
		BigDecimal average = AveragePricingStrategy.INSTANCE.calculatePrice(priceList);
		return average.add(average.multiply(PROFIT_PERCENTAGE)).setScale(2,
				RoundingMode.HALF_UP);
	}

}
