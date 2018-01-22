
package com.grails.practical.multithreading.test

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.test.spock.IntegrationSpec
import grails.transaction.Rollback

import com.grails.practical.service.PriceService
import com.grails.practical.service.ProductService
import com.jbilling.prizypricer.domain.Price
import com.jbilling.prizypricer.domain.Product

@Integration
@Rollback
@TestFor(ProductService)
@Mock([PriceService,Price,Product])
class MultithreadingTest extends IntegrationSpec {
	def price
	def product
	def product1
	def price1
	def setup() {
		product = new Product("MTG20160417AND14777","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =100 ;index < 5000;index++){
			price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
		
		product1 = new Product("MTG20160417AND11111","Moto G3","Octa Core, 1.6 GHz Processor 4 GB RAM, 64 GB inbuilt 3000 mAh Battery")
		product1.save flush:true
		for(int index =6000 ;index < 9000;index++){
			price1 = new Price(price:new BigDecimal(index),product:product1)
			price1.save flush:true
		}
	}


	void "Test the price calculation is correct when multiple thread access the same product and price"(){
		expect:
		product.validate()
		price.validate()
		
		final def idealPrice1,idealPrice2,retailPrice1,retailPrice2,simplePrice1,simplePrice2
		
		when:

		Thread t1 = new Thread(){
					@Override
					public void run() {
						idealPrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
					};
				}.start().sleep(1000);

		Thread t2 = new Thread(){
					@Override
					void run() {
						retailPrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
					};
				}.start().sleep(1000);
		Thread t3 = new Thread(){
					@Override
					void run() {
						simplePrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t4 = new Thread(){
					@Override
					void run() {
						retailPrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
					};
				}.start().sleep(1000);
		Thread t5 = new Thread(){
					@Override
					void run() {
						simplePrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t6 = new Thread(){
					@Override
					void run() {
						idealPrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
					};
				}.start().sleep(1000);


		then:
		idealPrice1.compareTo(idealPrice2) == 0
		retailPrice1.compareTo(retailPrice2) == 0
		simplePrice1.compareTo(simplePrice2) == 0
		idealPrice1.compareTo(retailPrice1) == -1 || idealPrice1.compareTo(retailPrice1) == 1
		idealPrice1.compareTo(simplePrice1) == -1 || idealPrice1.compareTo(simplePrice1) == 1
		retailPrice1.compareTo(simplePrice1) == -1 || retailPrice1.compareTo(simplePrice1) == 1
	}
	
	void "Test the price calculation is correct when multiple thread access the different product and price"(){
		expect:
		product.validate()
		price.validate()
		
		final def idealPrice1,idealPrice2,retailPrice1,retailPrice2,simplePrice1,simplePrice2
		
		when:

		Thread t1 = new Thread(){
					@Override
					public void run() {
						idealPrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
					};
				}.start().sleep(1000);

		Thread t2 = new Thread(){
					@Override
					void run() {
						retailPrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
					};
				}.start().sleep(1000);
		Thread t3 = new Thread(){
					@Override
					void run() {
						simplePrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t4 = new Thread(){
					@Override
					void run() {
						retailPrice2 = service.calculateForStandardStrategy(product1.getBarcode(),"Retail")
					};
				}.start().sleep(1000);
		Thread t5 = new Thread(){
					@Override
					void run() {
						simplePrice2 = service.calculateForStandardStrategy(product1.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t6 = new Thread(){
					@Override
					void run() {
						idealPrice2 = service.calculateForStandardStrategy(product1.getBarcode(),"Ideal")
					};
				}.start().sleep(1000);


		then:
		idealPrice1.compareTo(idealPrice2) == -1 || idealPrice1.compareTo(idealPrice2) == 1
		simplePrice1.compareTo(simplePrice2) == -1 || simplePrice1.compareTo(simplePrice2) == 1
		retailPrice1.compareTo(retailPrice2) == -1 || retailPrice1.compareTo(retailPrice2) == 1
	}
	
}
