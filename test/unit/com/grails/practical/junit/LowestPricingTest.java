package com.grails.practical.junit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.grails.strategy.LowestPricingStrategy;
import com.grails.strategy.PriceCalculationStrategy;

public class LowestPricingTest {
	List<BigDecimal>list;
	private PriceCalculationStrategy priceCalculation;
	@Before
	public void setup(){
		list = new ArrayList<BigDecimal>();
		list.add(new BigDecimal(10.0));
		list.add(new BigDecimal(20.0));
		list.add(new BigDecimal(30.0));
		list.add(new BigDecimal(40.0));
		list.add(new BigDecimal(50.0));
		list.add(new BigDecimal(60.0));
		priceCalculation = LowestPricingStrategy.INSTANCE;
	}
	
	@Test
	public void testForInstantiation(){
		org.junit.Assert.assertTrue((priceCalculation.getClass().equals(LowestPricingStrategy.class)) == true);
	}

	@Test
	public void testLowestPriceCalculationStrategy() {
		BigDecimal price1 = new BigDecimal(10.00);
		
		BigDecimal price2 = new BigDecimal("10.00");
		
		BigDecimal price3 = new BigDecimal(10.0);
		
		org.junit.Assert.assertTrue(price1.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(price2.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(price3.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(list.size() == 6);
		
		org.junit.Assert.assertTrue(BigDecimal.ZERO.compareTo(priceCalculation.calculatePrice(list)) == -1);
		
		org.junit.Assert.assertTrue(list.isEmpty() == false);
		
		org.junit.Assert.assertTrue(list.contains(10) == false);
		
		org.junit.Assert.assertTrue(list.contains(new BigDecimal(10)) == true);
		
		Collections.sort(list);
		
		org.junit.Assert.assertTrue(list.get(0).compareTo(new BigDecimal(10)) == 0);
		
		org.junit.Assert.assertTrue(list.get(5).compareTo(new BigDecimal(60)) == 0);
		
		org.junit.Assert.assertTrue(BigDecimal.ZERO.compareTo(priceCalculation.calculatePrice(new ArrayList<BigDecimal>())) == 0);
	}

}
