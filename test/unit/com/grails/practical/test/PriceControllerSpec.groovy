
package com.grails.practical.test
import grails.test.mixin.TestFor;

import com.grails.practical.Price;
import com.grails.practical.PriceController;

import spock.lang.*;

@TestFor(PriceController)
@grails.test.mixin.Mock(Price)
class PriceControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		//params[price:4, product.id:xxxxxxxxxxx, product:[id:xxxxxxxxxxx], create:Create, controller:price, format:null, action:save]
		params["price"] = new BigDecimal("1111.11")
		params["product.id"] = "xxxxxxxxxxx"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.priceInstanceList
            model.priceInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.priceInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def price = new Price()
            price.validate()
            controller.save(price)

        then:"The create view is rendered again with the correct model"
            model.priceInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            price = new Price(params)

            controller.save(price)

        then:"A redirect is issued to the show action"
			def p = 
            response.redirectedUrl == '/price/show/1'
            controller.flash.message != null
            Price.count() == 1
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
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/price/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def price = new Price(params).save(flush: true)

        then:"It exists"
            Price.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(price)

        then:"The instance is deleted"
            Price.count() == 0
            response.redirectedUrl == '/price/index'
            flash.message != null
    }
}
