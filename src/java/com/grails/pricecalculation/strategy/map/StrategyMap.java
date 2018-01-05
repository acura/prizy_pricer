package com.grails.pricecalculation.strategy.map;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.grails.strategy.AveragePricingStrategy;
import com.grails.strategy.HighestPricingStrategy;
import com.grails.strategy.IdealPricingStrategy;
import com.grails.strategy.LowestPricingStrategy;
import com.grails.strategy.PriceCalculationStrategy;
import com.grails.strategy.RetailPricingStrategy;
import com.grails.strategy.SimplePricingStrategy;

public class StrategyMap {

	public enum STRATEGY {
		LOWEST,AVEARAGE,HIGHEST,IDEAL,SIMPLE,RETAIL;
	}
	Map<STRATEGY, BigDecimal>map = new EnumMap<>(STRATEGY.class);
	static final Map<STRATEGY, PriceCalculationStrategy> defaultStrategyMap = new EnumMap<>(STRATEGY.class);
	static final Map<STRATEGY, PriceCalculationStrategy> standardStrategyMap = new EnumMap<>(STRATEGY.class);
	static{
		defaultStrategyMap.put(STRATEGY.LOWEST, LowestPricingStrategy.INSTANCE);
		defaultStrategyMap.put(STRATEGY.AVEARAGE, AveragePricingStrategy.INSTANCE);
		defaultStrategyMap.put(STRATEGY.HIGHEST, HighestPricingStrategy.INSTANCE);
		standardStrategyMap.put(STRATEGY.IDEAL, IdealPricingStrategy.INSTANCE);
		standardStrategyMap.put(STRATEGY.SIMPLE, SimplePricingStrategy.INSTANCE);
		standardStrategyMap.put(STRATEGY.RETAIL, RetailPricingStrategy.INSTANCE);
	}

	public  PriceCalculationStrategy getInstanceForDefaultStrategy(STRATEGY strategy){
		return defaultStrategyMap.get(strategy);
	}

	public  PriceCalculationStrategy getInstanceForStandardStrategy(STRATEGY strategy){
		return standardStrategyMap.get(strategy);
	}

	public   Set<STRATEGY> getDefaultStrategySet(){
		return defaultStrategyMap.keySet();		
	}

	public  List<String> getStandardStrategySet(){
		return standardStrategyMap.entrySet().stream().map(e -> String.valueOf(e.getKey())).collect(Collectors.toList());
	}

	public  BigDecimal calculateForStandardStrategy(List<BigDecimal>priceList,String strategy){	
		return getInstanceForStandardStrategy(STRATEGY.valueOf(strategy)).calculatePrice(priceList);
	}

	public  Map<STRATEGY, BigDecimal> calculateForDefaultStrategies(List<BigDecimal>priceList){
		getDefaultStrategySet().forEach(e -> map.put(e, getInstanceForDefaultStrategy(e).calculatePrice(priceList)));
		return map;
	}
}
