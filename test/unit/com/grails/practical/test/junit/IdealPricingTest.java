package com.grails.practical.test.junit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jbilling.prizypricer.strategy.IdealPricingStrategy;
import com.jbilling.prizypricer.strategy.PriceCalculationStrategy;

public class IdealPricingTest {

	List<BigDecimal>list;
	List<BigDecimal>list2;
	private PriceCalculationStrategy priceCalculation;
	@Before
	public void setup(){
		list = new ArrayList<BigDecimal>();
		list2 = new ArrayList<BigDecimal>();
		list.add(new BigDecimal(10.0));
		list.add(new BigDecimal(20.0));
		list.add(new BigDecimal(30.0));
		list.add(new BigDecimal(40.0));
		list.add(new BigDecimal(50.0));
		list.add(new BigDecimal(60.0));
		list2.add(new BigDecimal(70));
		list2.add(new BigDecimal(80));
		list2.add(new BigDecimal(90));
		list2.add(new BigDecimal(100));
		priceCalculation = IdealPricingStrategy.IDEAL;
	}
	
	@Test
	public void testForInstantiation(){
		org.junit.Assert.assertTrue((priceCalculation.getClass().equals(IdealPricingStrategy.class)) == true);
	}

	@Test
	public void testIdealPriceCalculationStrategy() {
		BigDecimal price1 = new BigDecimal(42.00);
		
		BigDecimal price2 = new BigDecimal("42.00");
		
		BigDecimal price3 = new BigDecimal(42.0);
		
		org.junit.Assert.assertTrue(price1.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(price2.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(price3.compareTo(priceCalculation.calculatePrice(list)) == 0);
		
		org.junit.Assert.assertTrue(list.size() == 6);
		
		org.junit.Assert.assertTrue((list.size() >=5) == true);
		
		org.junit.Assert.assertTrue(BigDecimal.ZERO.compareTo(priceCalculation.calculatePrice(list)) == -1);
		
		org.junit.Assert.assertTrue(list.isEmpty() == false);
		
		org.junit.Assert.assertTrue(list.contains(10) == false);
		
		org.junit.Assert.assertTrue(list.contains(new BigDecimal(10)) == true);
		
		Collections.sort(list);
		
		org.junit.Assert.assertTrue(list.get(0).compareTo(new BigDecimal(10)) == 0);
		
		org.junit.Assert.assertTrue(list.get(5).compareTo(new BigDecimal(60)) == 0);
		
		org.junit.Assert.assertTrue(BigDecimal.ZERO.compareTo(priceCalculation.calculatePrice(new ArrayList<BigDecimal>())) == 0);
		
		org.junit.Assert.assertTrue(list2.size() == 4);
		
		org.junit.Assert.assertTrue((list2.size() < 5) == true);
		
		org.junit.Assert.assertTrue(BigDecimal.ZERO.compareTo(priceCalculation.calculatePrice(list2)) == 0);
		
		org.junit.Assert.assertTrue(list2.isEmpty() == false);
		
		org.junit.Assert.assertTrue(list2.contains(100) == false);
		
		org.junit.Assert.assertTrue(list2.contains(new BigDecimal(100)) == true);
		
		Collections.sort(list2);
		
		org.junit.Assert.assertTrue(list2.get(0).compareTo(new BigDecimal(70)) == 0);
		
		org.junit.Assert.assertTrue(list2.get(3).compareTo(new BigDecimal(100)) == 0);

	}
	
	

}
