package com.jbilling.prizypricer.strategy.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jbilling.prizypricer.strategy.AveragePricingStrategy;
import com.jbilling.prizypricer.strategy.HighestPricingStrategy;
import com.jbilling.prizypricer.strategy.IdealPricingStrategy;
import com.jbilling.prizypricer.strategy.LowestPricingStrategy;

@Configuration
public class StrategyBeanConfig {

	@Bean
	public LowestPricingStrategy lowestPricingStrategyBean() {
		return new LowestPricingStrategy();
	}

	@Bean
	public HighestPricingStrategy highestPricingStrategyBean() {
		return new HighestPricingStrategy();
	}

	@Bean
	public AveragePricingStrategy averagePricingStrategyBean() {
		return new AveragePricingStrategy();
	}

	@Bean
	public IdealPricingStrategy idealPricingStrategyBean() {
		return new IdealPricingStrategy();
	}
	
}
