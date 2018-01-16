

package com.grails.practical.test.groovy

import java.math.RoundingMode;

import com.grails.practical.Price;
import com.grails.practical.Product;
import com.grails.practical.service.PriceService;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(PriceService)
@grails.test.mixin.Mock([Price,Product])
class PriceServiceSpec extends Specification {

	Price price 
	Product product
	
    def setup() {
		product = new Product("MTG20160417AND14","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =0 ;index < 7;index++){
			 price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
    }

    def cleanup() {
    }

    void "Test the price list for the barcode is correct"() {
		
		when:
		List<Price>priceList = new ArrayList<>()
		for(int index =0 ;index < 7;index++){
			priceList.add(new BigDecimal(index).setScale(2, RoundingMode.HALF_UP)) 
	   }
		
		then:
		service.getPriceList(product.getBarcode()).containsAll(priceList)
    }
	
	void "Test the price count for the barcode is correct"() {
		
		when:
		def priceCount = service.getPriceCount(product.getBarcode())
		
		then:
		priceCount == 7
	}
}
