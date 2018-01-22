package com.prizypricer.controller

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional

import com.prizypricer.domain.Price
import com.prizypricer.domain.Product;

@Transactional
class PriceController {
	def priceServiceImpl;
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Price.list(params), model:[priceInstanceCount: Price.count()]
    }

    def show(Price priceInstance) {
        respond priceInstance
    }

    def create() {
        respond new Price(params),model:[productList: Product.list()]
    }

   
    def save(Price priceInstance) {
        if (priceInstance == null) {
            notFound()
            return
        }

        if (priceInstance.hasErrors()) {
            respond priceInstance.errors, view:'create'
            return
        }
	
        priceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'price.label', default: 'Price'), priceInstance.id])
				redirect(action:"index",id:params.barcode)
            }
            '*' { respond priceInstance, [status: CREATED] }
        }
    }
	
	def search(Integer max){
		params.max = Math.min(max ?: 10, 100)
		def productList
		def productCount
		def searchText = params.value
		
		println "in search"

		if(null !=searchText){
			productList=Product.findAllByBarcodeLike(searchText +'%',params)
		}else{
			productList = Product.list(params)
		}
		
		return productList as JSON
	}

    def delete(Price priceInstance) {
	
		if (priceInstance == null) {
			notFound()
			return
		}
		priceInstance.delete flush:true
		
		def _price = priceInstance.price
		def _productName = priceInstance.product.productName

		flash.message = _price + "of product "+_productName+" is deleted";
		redirect(action:"index",id:params.barcode)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
