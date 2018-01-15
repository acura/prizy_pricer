package com.grails.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public enum RetailPricingStrategy implements PriceCalculationStrategy {
	INSTANCE;
	private static final BigDecimal HUNDRED = new BigDecimal("100");
	private static final BigDecimal PROFIT_PERCENTAGE = new BigDecimal("45");

	@Override
	public BigDecimal calculatePrice(List<BigDecimal> priceList) {
		if (!priceList.isEmpty()) {
			return AveragePricingStrategy.INSTANCE
					.calculatePrice(priceList)
					.divide(HUNDRED.subtract(PROFIT_PERCENTAGE), 2,	RoundingMode.HALF_UP)
					.multiply(HUNDRED)
					.setScale(2, RoundingMode.HALF_UP);
		}
		
		return BigDecimal.ZERO;
	}

}
