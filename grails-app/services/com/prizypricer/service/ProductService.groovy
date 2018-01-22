package com.prizypricer.service

import grails.transaction.Transactional

import com.jbilling.prizypricer.strategy.factory.StrategyFactory
import com.prizypricer.domain.Product

@Transactional
class ProductService {
	def priceService
	
	def calculateIdealPrice(String barcode){
		def map = new HashMap<String, BigDecimal>()
		def product = Product.findByBarcode(barcode);
		def priceList = priceService.getPriceList(barcode)//product.prices.each { p -> p.price }
		Collections.sort(priceList)
		map.put("Lowest", StrategyFactory.getInstance("Lowest").calculatePrice(priceList))
		map.put("Average", StrategyFactory.getInstance("Average").calculatePrice(priceList))
		map.put("Highest", StrategyFactory.getInstance("Highest").calculatePrice(priceList))
		map.put("Ideal", StrategyFactory.getInstance("Ideal").calculatePrice(priceList))
		return map
	}
}
