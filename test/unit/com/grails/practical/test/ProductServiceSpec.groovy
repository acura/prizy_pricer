

package com.grails.practical.test

import com.grails.practical.Price
import com.grails.practical.Product;
import com.grails.practical.service.PriceService;
import com.grails.practical.service.ProductService;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll;

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(ProductService)
@grails.test.mixin.Mock([Product,Price,PriceService])
class ProductServiceSpec extends Specification {

	Product product

	def setup() {
		product = new Product("MTG20160417AND14","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =0 ;index < 7;index++){
			def price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
	}

	def cleanup() {
	}

	@Unroll
	void "Test the getStandardStrategySet return correct strategy set"() {

		when:
		def set = new HashSet<>()
		set.add("Ideal")
		set.add("Retail")
		set.add("Simple")

		then:
		service.getStandardStrategySet().containsAll(set)
	}

	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is greater than 5"() {
		when:
		def resultingAmount = service.calculateForStandardStrategy(product.getBarcode(),strategy)

		then:
		resultingAmount.compareTo(new BigDecimal(amount))  == 0

		where:
		  strategy   |   	amount
		  'Ideal'	 |      "3.60"
		  'Retail'	 |      "5.00"
		  'Simple'	 |      "4.20"
	}
	
	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is zero"() {
		given:
		Product product1 = new Product("MTG20160417AND141","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product1.save flush:true
		
		when:
		def resultingAmount = service.calculateForStandardStrategy(product1.getBarcode(),strategy)

		then:
		resultingAmount.compareTo(new BigDecimal(amount))  == 0

		where:
		  strategy   |   	amount
		  'Ideal'	 |      "0.00"
		  'Retail'	 |      "0.00"
		  'Simple'	 |      "0.00"
	}
	
	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is greater than zero and less than 5"() {
		given:
		Product product1 = new Product("XXX20160417AND141","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product1.save flush:true
		for(int index =0 ;index < 4;index++){
			def price = new Price(price:new BigDecimal(index),product:product1)
			price.save flush:true
		}
		
		when:
		def resultingAmount = service.calculateForStandardStrategy(product1.getBarcode(),strategy)

		then:
		resultingAmount.compareTo(new BigDecimal(amount))  == 0

		where:
		  strategy   |   	amount
		  'Ideal'	 |      "0.00"
		  'Retail'	 |      "3.00"
		  'Simple'	 |      "2.10"
	}
	
	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is 5"() {
		given:
		Product product1 = new Product("XXX20160417AND141","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product1.save flush:true
		for(int index =0 ;index < 5;index++){
			def price = new Price(price:new BigDecimal(index),product:product1)
			price.save flush:true
		}
		
		when:
		def resultingAmount = service.calculateForStandardStrategy(product1.getBarcode(),strategy)

		then:
		resultingAmount.compareTo(new BigDecimal(amount))  == 0

		where:
		  strategy   |   	amount
		  'Ideal'	 |      "2.40"
		  'Retail'	 |      "4.00"
		  'Simple'	 |      "2.80"
	}
	
	@Unroll
	void "Test the price calculation is correct for default strategies when price count is greater than 0"() {
		
		when:
		def map = service.calculateForDefaultStrategies(product.getBarcode())

		then:
		map.get(strategy).compareTo(new BigDecimal(amount)) == 0

		where:
		  strategy  |      amount
	      'Lowest'	|      "0.00"
		  'Average' |      "3.00"
		  'Highest' |      "6.00"
	}
	
	@Unroll
	void "Test the price calculation is correct for default strategies when price count is 0"() {
		given:
		Product product1 = new Product("MTG20160417AND141","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product1.save flush:true 
		
		when:
		def map = service.calculateForDefaultStrategies(product1.getBarcode())

		then:
		map.get(strategy).compareTo(new BigDecimal(amount)) == 0

		where:
		  strategy  |      amount
		  'Lowest'	|      "0.00"
		  'Average' |      "0.00"
		  'Highest' |      "0.00"
	}
	
	void "Test the product count is correct for filter action"() {
		
		when:
		def searchText = "MTG" 

		then:
		service.getProductCountForSearch(searchText) == 1
	}
	
}
