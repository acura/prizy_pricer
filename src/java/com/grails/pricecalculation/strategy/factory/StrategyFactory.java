package com.grails.pricecalculation.strategy.factory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grails.strategy.AveragePricingStrategy;
import com.grails.strategy.HighestPricingStrategy;
import com.grails.strategy.IdealPricingStrategy;
import com.grails.strategy.LowestPricingStrategy;
import com.grails.strategy.PriceCalculationStrategy;
import com.grails.strategy.RetailPricingStrategy;
import com.grails.strategy.SimplePricingStrategy;

public class StrategyFactory{
	
	@Autowired
	private PriceCalculationStrategy calculationStrategy;

	public PriceCalculationStrategy getInstance(String strategy) {
		if (null == strategy)
			return null;
		else if ("Lowest".equals(strategy))
			calculationStrategy = LowestPricingStrategy.INSTANCE;
		else if ("Average".equals(strategy))
			calculationStrategy = AveragePricingStrategy.INSTANCE;
		else if ("Highest".equals(strategy))
			calculationStrategy = HighestPricingStrategy.INSTANCE;
		else if ("Ideal".equals(strategy))
			calculationStrategy = IdealPricingStrategy.INSTANCE;
		else if ("Simple".equals(strategy))
			calculationStrategy = SimplePricingStrategy.INSTANCE;
		else
			calculationStrategy = RetailPricingStrategy.INSTANCE;
		return calculationStrategy;
	}

	public BigDecimal calculatePrice(List<BigDecimal> priceList, String strategy) {
		if(!priceList.isEmpty() && strategy != null)
			return getInstance(strategy).calculatePrice(priceList);	
		return BigDecimal.ZERO;
	}
}
