
package com.grails.practical.multithreading.test

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import spock.lang.Specification;

import com.grails.practical.Price;
import com.grails.practical.Product;
import com.grails.practical.service.PriceService;
import com.grails.practical.service.ProductService;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import grails.test.mixin.Mock;
import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin;
import grails.test.mixin.integration.Integration;
import grails.test.mixin.support.GrailsUnitTestMixin;
import grails.test.spock.IntegrationSpec;
import groovy.mock.interceptor.MockFor;


@TestFor(ProductService)
@Mock([PriceService,Price,Product])
@TestMixin(GrailsUnitTestMixin)
class MultithreadingTest extends IntegrationSpec {
	def price
	def product
	static def idealPrice1,idealPrice2,retailPrice1,retailPrice2,simplePrice1,simplePrice2

	def setup() {
		product = new Product("MTG20160417AND14777","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =100 ;index < 1000;index++){
			price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
	}


	void "Test the price calculation is correct when multiple thread access the same product and price"(){

		given:

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
					void run() {
						simplePrice1 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t4 = new Thread(){
					void run() {
						retailPrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
					};
				}.start().sleep(1000);
		Thread t5 = new Thread(){
					void run() {
						simplePrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
					};
				}.start().sleep(1000);
		Thread t6 = new Thread(){
					void run() {
						idealPrice2 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
					};
				}.start().sleep(1000);


		expect:
		idealPrice1.compareTo(idealPrice2) == 0
		retailPrice1.compareTo(retailPrice2) == 0
		simplePrice1.compareTo(simplePrice2) == 0
		idealPrice1.compareTo(retailPrice1) == -1 || idealPrice1.compareTo(retailPrice1) == 1
		idealPrice1.compareTo(simplePrice1) == -1 || idealPrice1.compareTo(simplePrice1) == 1
		retailPrice1.compareTo(simplePrice1) == -1 || retailPrice1.compareTo(simplePrice1) == 1
	}
	
}
