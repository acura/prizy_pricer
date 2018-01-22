package com.grails.practical.service

import grails.transaction.Transactional

import com.grails.pricecalculation.strategy.factory.StrategyFactory
import com.jbilling.prizy.practical.Price

@Transactional
class PriceService{

	private StrategyFactory strategyMapInstance = new StrategyFactory()
	def Price priceInstance;
	
	def getPriceList(String barcode){
		List<BigDecimal>priceListInBigDecimal = new ArrayList<BigDecimal>();
		List<Price>priceList = Price.createCriteria().list {
			product{
				eq("barcode",barcode as String)
			}
		}
		for(Price p : priceList){
			priceListInBigDecimal.add(p.price)
		}
		return priceListInBigDecimal;
	}
	
	def getPriceCount(String barcode){
		List<BigDecimal> priceList = getPriceList(barcode)
		return priceList.size()
	}
	
	def getPriceListTodisplay(String barcode){
		List<BigDecimal> priceList = getPriceList(barcode)
		Collections.sort(priceList)
		if(priceList.size() > 10)
			return priceList.subList(priceList.size()-10, priceList.size()).unique()
		return priceList
	}

	def checkDuplicatePriceForSameProduct(Price priceInstance,String barcode){
		List<Price>priceList = Price.createCriteria().list {
			product{
				eq("barcode",barcode as String)
			}
		}
		if(priceList.contains(priceInstance))
			return true
		return false
	}

	}
