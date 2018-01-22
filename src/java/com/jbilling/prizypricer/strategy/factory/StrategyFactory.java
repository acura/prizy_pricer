package com.grails.pricecalculation.strategy.factory;

import java.math.BigDecimal;
import java.util.List;

import com.grails.strategy.AveragePricingStrategy;
import com.grails.strategy.HighestPricingStrategy;
import com.grails.strategy.IdealPricingStrategy;
import com.grails.strategy.LowestPricingStrategy;
import com.grails.strategy.PriceCalculationStrategy;
import com.grails.strategy.RetailPricingStrategy;
import com.grails.strategy.SimplePricingStrategy;

public class StrategyFactory {

	public PriceCalculationStrategy getInstance(String strategy) {
		PriceCalculationStrategy calculationStrategyInstance;

		if (null == strategy)
			return null;
		else if ("Lowest".equals(strategy))
			calculationStrategyInstance = LowestPricingStrategy.LOWEST;
		else if ("Average".equals(strategy))
			calculationStrategyInstance = AveragePricingStrategy.AVERAGE;
		else if ("Highest".equals(strategy))
			calculationStrategyInstance = HighestPricingStrategy.HIGHEST;
		else if ("Ideal".equals(strategy))
			calculationStrategyInstance = IdealPricingStrategy.IDEAL;
		else if ("Simple".equals(strategy))
			calculationStrategyInstance = SimplePricingStrategy.SIMPLE;
		else
			calculationStrategyInstance = RetailPricingStrategy.RETAIL;
		return calculationStrategyInstance;
	}

	public BigDecimal calculatePrice(List<BigDecimal> priceList, String strategy) {
		if (!priceList.isEmpty() && strategy != null)
			return getInstance(strategy).calculatePrice(priceList);
		return BigDecimal.ZERO;
	}
}
