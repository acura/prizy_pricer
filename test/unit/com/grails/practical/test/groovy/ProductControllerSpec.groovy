



package com.grails.practical.test.groovy

import grails.test.mixin.TestFor;
import groovy.transform.builder.Builder;

import com.grails.practical.Price;
import com.grails.practical.Product;
import com.grails.practical.ProductController;
import com.grails.practical.service.PriceService;
import com.grails.practical.service.ProductService;

import spock.lang.Specification
import spock.lang.Unroll;

@TestFor(ProductController)
@grails.test.mixin.Mock([Product,ProductService,Price,PriceService])
class ProductControllerSpec extends Specification{

	Product product

	static doWithSpring = {
		productService ProductService
		priceService PriceService
	}

	def populateValidParams(params) {
		assert params != null
	}

	def setup(){
		controller.metaClass.message = { it }
		product = new Product("MTG20160417AND14","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		new Product("XXX20160417AND155","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery").save flush:true
		for(int index =0 ;index < 7;index++){
			def price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
	}

	void "Test the index action returns the correct model"() {

		given:
		params["max"] = 10

		when:"The index action is executed for empty product list"
		controller.index()

		then:"The model is correct"
		model.productInstanceCount1 == 2
	}

	void "Test the create action returns the correct model"() {

		when:"The create action is executed"
		controller.create()

		then:"The model is correctly created"
		model.productInstance!= null
	}

	void "Test the filter action searches the correct product"() {
		
		when:
		params["id"] ="MTG"
		params["value"] ="MTG"
		controller.filter(10)

		then:"The model is correctly created"
		response.text.trim().contains("MTG20160417AND14")
		
		when:
		params["id"] ="x"
		params["value"] ="x"
		controller.filter(10)

		then:"The model is correctly created"
		response.text.trim().contains("XXX20160417AND155")
	}

	@Unroll
	void "Test the price calculation is correct for standard strategies"() {
		given:
		params.id = id_value
		params.strategy = strategy

		when:

		controller.calculateForStandardStrategy()

		then:
		response.text.trim().contains(amount)

		where:
		id_value        	|  strategy  |   	amount
		'MTG20160417AND14'	|  'Ideal'	 |      "3.60"
		'MTG20160417AND14'	|  'Retail'	 |      "5.00"
		'MTG20160417AND14'	|  'Simple'	 |      "4.20"
	}


	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is less than 5"() {
		given:
		product = new Product("MTG20160417AND147","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =0 ;index < 4;index++){
			def price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
		params.id = id_value
		params.strategy = strategy

		when:
		controller.calculateForStandardStrategy()

		then:
		response.text.trim().contains(amount)

		where:
		id_value        	|  strategy  |   	amount
		'MTG20160417AND147'	|  'Ideal'	 |      "custom.ideal.strategy.min.price.count"
		'MTG20160417AND147'	|  'Retail'	 |      "3.00"
		'MTG20160417AND147'	|  'Simple'	 |      "2.10"
	}

	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is 5"() {
		given:
		product = new Product("MTG20160417AND148","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =0 ;index < 5;index++){
			def price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
		params.id = id_value
		params.strategy = strategy

		when:
		controller.calculateForStandardStrategy()

		then:
		response.text.trim().contains(amount)

		where:
		id_value        	|  strategy  |   	amount
		'MTG20160417AND148'	|  'Ideal'	 |      "2.40"
		'MTG20160417AND148'	|  'Retail'	 |      "4.00"
		'MTG20160417AND148'	|  'Simple'	 |      "2.80"
	}

	@Unroll
	void "Test the price calculation is correct for standard strategies when price count is 0"() {
		given:
		product = new Product("MTG20160417AND149","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		params.id = id_value
		params.strategy = strategy

		when:
		controller.calculateForStandardStrategy()

		then:
		response.text.trim().contains(amount)

		where:
		id_value        	|  strategy  |   	amount
		'MTG20160417AND149'	|  'Ideal'	 |      "custom.ideal.strategy.min.price.count"
	}

	@Unroll
	void "Test the price calculation is correct for default strategies"() {
		given:
		params.id = id_value

		when:
		def map = controller.calculateForDefaultStrategies()

		then:
		map.get(strategy).toString() == amount

		where:
		id_value        	|  strategy  |      amount
		'MTG20160417AND14'	|  'Lowest'	 |      "0.00"
		'MTG20160417AND14'	|  'Average' |      "3.00"
		'MTG20160417AND14'	|  'Highest' |      "6.00"
	}
	
	@Unroll
	void "Test the price calculation is correct for default strategies when price count is 0"() {
		given:
		product = new Product("MTG20160417AND141","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		params.id = id_value
		
		when:
		def map = controller.calculateForDefaultStrategies()

		then:
		map.get(strategy).compareTo(new BigDecimal(amount)) == 0

		where:
		id_value        	|  strategy  |      amount
		'MTG20160417AND141'	|  'Lowest'	 |      "0.00"
		'MTG20160417AND141'	|  'Average' |      "0.00"
		'MTG20160417AND141'	|  'Highest' |      "0.00"
	}

	void "Test the save action correctly persists an instance"() {

		when:"The save action is executed with an invalid instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'POST'
		def product = new Product()
		product.validate()
		controller.save(product)

		then:"The create view is rendered again with the correct model"
		model.productInstance!= null
		view == 'create'

		when:"The save action is executed with a valid instance"
		response.reset()
		controller.save(this.product)

		then:"A redirect is issued to the show action"
		response.redirectedUrl == '/product/show/MTG20160417AND14'
		controller.flash.message != null
		Product.count() == 2
	}

	void "Test that the show action returns the correct model"() {
		when:"The show action is executed with a null domain"
		controller.show(null)

		then:"A 404 error is returned"
		response.status == 404

		when:"A domain instance is passed to the show action"
		controller.show(product)

		then:"A model is populated containing the domain instance"
		model.productInstance == product
	}


	void "Test that the delete action deletes an instance if it exists"() {
		given:
		def product1 = new Product("MTG20160417AND146","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")

		when:"The delete action is called for a null instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'DELETE'
		params['barcode'] = null
		controller.delete(null)

		then:"A 404 is returned"
		response.redirectedUrl == '/product/index'
		flash.message != null

		when:"A domain instance is created"
		response.reset()
		product1.save(flush: true)

		then:"It exists"
		Product.count() == 3

		when:"The domain instance is passed to the delete action"
		params['barcode'] = product1.getBarcode()
		controller.delete(product1)

		then:"The instance is deleted"
		Product.count() == 2
		response.redirectedUrl == '/product/index'
		flash.message != null
	}
}
