
package com.grails.practical.test

import grails.test.mixin.TestFor;
import groovy.transform.builder.Builder;

import com.grails.practical.Price;
import com.grails.practical.Product;
import com.grails.practical.ProductController;

import prizy_pricer.PriceService
import prizy_pricer.ProductService;
import spock.lang.Specification

@TestFor(ProductController)
@grails.test.mixin.Mock([Product,ProductService,Price,PriceService])
class ProductControllerSpec extends Specification{
	
	static doWithSpring = {
		productService ProductService
		priceService PriceService
	}
	
    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		//params______________[create:Create, barcode:ssssssssssssss, productName:ssssss, productDescription:ssssssssssss, controller:product, format:null, action:save]
		params["barcode"] = "abcdefghijklmn"
		params["id"] = "abcdefghijklmn"
		params["productName"] = "i-phone"
		params["productDescription"] = "32 GB"
		params["strategy"] = "Retail"
		 }

    void "Test the index action returns the correct model"() {
	
        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.productInstanceList
            model.productInstanceCount1 == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.productInstance!= null
    }
	
	void "Test the price calculation is correct for strategy"() {
		when:"The create action is executed"
			controller.calculateForStandardStrategy()

		then:"The model is correctly created"
			model.price!= null
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
            populateValidParams(params)
            product = new Product(params)

            controller.save(product)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/product/show/abcdefghijklmn'
            controller.flash.message != null
            Product.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def product = new Product(params)
            controller.show(product)

        then:"A model is populated containing the domain instance"
            model.productInstance == product
    }

   
    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/product/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def product = new Product(params).save(flush: true)

        then:"It exists"
            Product.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(product)

        then:"The instance is deleted"
            Product.count() == 0
            response.redirectedUrl == '/product/index'
            flash.message != null
    }
}
