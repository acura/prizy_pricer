package com.jbilling.prizypricer.strategy.factory;

import org.springframework.stereotype.Component;

import com.jbilling.prizypricer.strategy.PriceCalculationStrategy;
@Component
public interface Factory {
	public  PriceCalculationStrategy getInstance(String strategy);
}
