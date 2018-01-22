
package com.grails.practical.test.groovy
import grails.test.mixin.TestFor;
import spock.lang.*

import com.grails.practical.PriceController
import com.jbilling.prizypricer.domain.Price
import com.jbilling.prizypricer.domain.Product

@TestFor(PriceController)
@grails.test.mixin.Mock([Price,Product])
class PriceControllerSpec extends Specification {

	Product product
	Price price
	
	def setup(){
		product = new Product("MTG20160417AND14","Moto G2","Octa Core, 1.6 GHz Processor 4 GB RAM, 32 GB inbuilt 3000 mAh Battery")
		product.save flush:true
		for(int index =0 ;index < 7;index++){
			price = new Price(price:new BigDecimal(index),product:product)
			price.save flush:true
		}
	}
    def populateValidParams(params) {
        assert params != null
		//params[price:4, product.id:xxxxxxxxxxx, product:[id:xxxxxxxxxxx], create:Create, controller:price, format:null, action:save]
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            model.priceInstanceList
            model.priceInstanceCount == 7
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.priceInstance!= null
    }

    void "Test the save action correctly persists an instance"() {
		given:
		Price price1 = new Price(product:product)

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            price1.validate()
            controller.save(price1)

        then:"The create view is rendered again with the correct model"
           model.priceInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
			price1 = new Price(price:new BigDecimal("90.00"),product:product)
            controller.save(price1)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/price/index'
            controller.flash.message != null
            Price.count() == 8
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def price = new Price(params)
            controller.show(price)

        then:"A model is populated containing the domain instance"
            model.priceInstance == price
    }

    

    void "Test that the delete action deletes an instance if it exists"() {
		given:
		Price price1 = new Price(price:new BigDecimal("90.00"),product:product)
		
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/price/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
             price1.save(flush: true)

        then:"It exists"
            Price.count() == 8

        when:"The domain instance is passed to the delete action"
            controller.delete(price1)

        then:"The instance is deleted"
            Price.count() == 7
            response.redirectedUrl == '/price/index'
            flash.message != null
    }
}
