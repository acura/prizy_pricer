package com.jbilling.prizypricer.strategy.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jbilling.prizypricer.strategy.AveragePricingStrategy;
import com.jbilling.prizypricer.strategy.HighestPricingStrategy;
import com.jbilling.prizypricer.strategy.IdealPricingStrategy;
import com.jbilling.prizypricer.strategy.LowestPricingStrategy;
import com.jbilling.prizypricer.strategy.PriceCalculationStrategy;

public class StrategyFactory{
	
	private StrategyFactory() {}

	@SuppressWarnings("resource")
	public static PriceCalculationStrategy getInstance(String strategy) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StrategyBeanConfig.class);
		if (null == strategy)
			return null;
		else if ("Lowest".equals(strategy))
			return applicationContext.getBean(LowestPricingStrategy.class);
		else if ("Average".equals(strategy))
			return applicationContext.getBean(AveragePricingStrategy.class);
		else if ("Highest".equals(strategy))
			return applicationContext.getBean(HighestPricingStrategy.class);
		else if ("Ideal".equals(strategy))
			return applicationContext.getBean(IdealPricingStrategy.class);
		return null;
	}

}
