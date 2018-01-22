package com.prizypricer.service

import grails.transaction.Transactional

import com.prizypricer.domain.Price

@Transactional
class PriceService{

	def getPriceList(String barcode){
		def productList = Price.executeQuery("select price from Price where product_id ='"+ barcode +"'")
		return productList;
	}

	def getPriceCount(String barcode){
		return getPriceList(barcode).size()
	}
}
