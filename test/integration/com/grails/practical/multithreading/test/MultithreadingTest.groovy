
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
	static def resultingAmount1,resultingAmount2,resultingAmount3,resultingAmount4,resultingAmount5,resultingAmount6

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
						resultingAmount1 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
						println "resultingAmount1 Ideal" + resultingAmount1
					};
				}.start().sleep(1000);

		Thread t2 = new Thread(){
					@Override
					void run() {
						resultingAmount2 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
						println "resultingAmount2 Retail" + resultingAmount2
					};
				}.start().sleep(1000);
		Thread t3 = new Thread(){
					void run() {
						resultingAmount3 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
						println "resultingAmount3 Simple" + resultingAmount3
					};
				}.start().sleep(1000);
		Thread t4 = new Thread(){
					void run() {
						resultingAmount4 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
						println "resultingAmount4 Retail" + resultingAmount4
					};
				}.start().sleep(1000);
		Thread t5 = new Thread(){
					void run() {
						resultingAmount5 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
						println "resultingAmount5 Simple" + resultingAmount5
					};
				}.start().sleep(1000);
		Thread t6 = new Thread(){
					void run() {
						resultingAmount6 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
						println "resultingAmount6 Ideal" + resultingAmount6
					};
				}.start().sleep(1000);


		expect:
		resultingAmount1.compareTo(resultingAmount6) == 0
		resultingAmount2.compareTo(resultingAmount4) == 0
		resultingAmount3.compareTo(resultingAmount5) == 0
		resultingAmount1.compareTo(resultingAmount2) == -1 || resultingAmount1.compareTo(resultingAmount6) == 1
		resultingAmount1.compareTo(resultingAmount3) == -1 || resultingAmount1.compareTo(resultingAmount3) == 1
		resultingAmount2.compareTo(resultingAmount3) == -1 || resultingAmount2.compareTo(resultingAmount3) == 1
	}
	
}
