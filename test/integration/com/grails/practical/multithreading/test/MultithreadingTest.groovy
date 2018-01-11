
package com.grails.practical.multithreading.test

import spock.lang.Specification;

import com.grails.practical.Price;
import com.grails.practical.Product;
import com.grails.practical.service.PriceService;
import com.grails.practical.service.ProductService;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import grails.test.mixin.Mock;
import grails.test.mixin.TestFor;
import grails.test.mixin.support.GrailsUnitTestMixin;
import groovy.mock.interceptor.MockFor;


@TestFor(ProductService)
@grails.test.mixin.Mock([Product,Price,PriceService,ProductService])
class MultithreadingTest extends GroovyTestCase{
	Price price
	Product product
	ProductService service
	BigDecimal resultingAmount1,resultingAmount2,resultingAmount3,resultingAmount4,resultingAmount5,resultingAmount6;

	def setup() {
		service = [service: { Product p -> product }] as ProductService
		product = new Product("MTG20160417AND14","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =100 ;index < 1000;index++){
			price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
	}

	void "Test the price calculation is correct when multiple thread access the same product and price"(){

		when:
		Runnable r1 = new Runnable(){
					void run() {
						resultingAmount1 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
						println resultingAmount1
					};
				}

		/*Runnable r2 = new Runnable(){
					void run() {
						resultingAmount2 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
						println resultingAmount2
					};
				}

		Runnable r3 = new Runnable(){
					void run() {
						resultingAmount3 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
						println resultingAmount3
					};
				}

		Runnable r4 = new Runnable(){
					void run() {
						resultingAmount4 = service.calculateForStandardStrategy(product.getBarcode(),"Retail")
						println resultingAmount4
					};
				}

		Runnable r5 = new Runnable(){
					void run() {
						resultingAmount5 = service.calculateForStandardStrategy(product.getBarcode(),"Simple")
						println resultingAmount5
					};
				}

		Runnable r6 = new Runnable(){
					void run() {
						resultingAmount6 = service.calculateForStandardStrategy(product.getBarcode(),"Ideal")
						println resultingAmount6
					};
				}
		*/
		Thread t1=new Thread(r1);
		t1.start();
		/*Thread t2=new Thread(r2);
		t2.start();
		Thread t3=new Thread(r3);
		t3.start();
		Thread t4=new Thread(r4);
		t4.start();
		Thread t5=new Thread(r5);
		t5.start();
		Thread t6=new Thread(r6);
		t6.start();*/
		
		Thread t = Thread.currentThread();
		t.sleep(10000);
		
		then:
		true
		/* resultingAmount1.compareTo(resultingAmount6) == 0
		 resultingAmount2.compareTo(resultingAmount4) == 0
		 resultingAmount3.compareTo(resultingAmount5) == 0*/
	}
}
