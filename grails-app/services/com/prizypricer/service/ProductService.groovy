package com.grails.practical.service

import grails.transaction.Transactional

import com.grails.pricecalculation.strategy.factory.StrategyFactory
import com.jbilling.prizy.practical.Product

@Transactional
class ProductService {
	private StrategyFactory strategyFactory = new StrategyFactory();
	def priceService

	def getStandardStrategySet(){
		def set = new HashSet<String>()
		set.add("Ideal")
		set.add("Retail")
		set.add("Simple")
		return set
	}

	def getDefaultStrategySet(){
		def set = new HashSet<String>()
		set.add("Lowest")
		set.add("Average")
		set.add("Highest")
		return set
	}

	def calculateForDefaultStrategies(String barcode){
		def strategySet = getDefaultStrategySet()
		def map = new HashMap<String, BigDecimal>()
		strategySet.each { strategy ->
			map.put(strategy, strategyFactory.calculatePrice(priceService.getPriceList(barcode), strategy))
		}
		return map
	}


	def calculateForStandardStrategy(String barcode,String strategy){
		def price = strategyFactory.calculatePrice(priceService.getPriceList(barcode), strategy)
		return price
	}

	synchronized getProductCountForSearch(String text) {
		def criteria = Product.createCriteria()
		if(null == text)
			return 0
		List<Product> result = criteria.list(){
			like('barcode', "%"+text.trim()+"%")
			projections { count() }
		}
		return result[0]
	}
}
