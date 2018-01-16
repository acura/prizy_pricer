package com.grails.strategy;

import java.math.BigDecimal;
import java.util.List;

public interface PriceCalculationStrategy {
	public BigDecimal calculatePrice(final List<BigDecimal> priceList);
}
