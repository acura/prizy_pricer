package com.grails.practical



import static org.springframework.http.HttpStatus.*

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ProductController {
	def productService;
	def priceService
	static allowedMethods = [save: "POST", delete: "DELETE"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Product.list(params),model:[productInstanceCount1: Product.count()]
	}

	def show(Product productInstance) {
		def defaultStrategyMap = calculateForDefaultStrategies()
		def standardStrategySet = getStandardStrategySet()
		def priceCount = getPriceCount()
		def priceList = getListToDisplay()
		respond productInstance, model:[defaultStrategyMap:defaultStrategyMap,standardStrategySet:standardStrategySet,priceCount:priceCount,priceList:priceList]
	}

	def create() {
		respond new Product(params)
	}


	def save(Product productInstance) {
		if (productInstance == null) {
			notFound()
			return
		}
		if (productInstance.hasErrors()) {
			respond productInstance.errors, view:'create'
			return
		}

		productInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'product.label', default: 'Product'),
					productInstance.barcode
				])
				redirect(action: "show", id: productInstance.barcode, model: [barcode: productInstance.barcode,productName:productInstance.productName,productDescription:productInstance.productDescription])
				return
			}
			'*' { respond productInstance, [status: CREATED] }
		}
	}

	def getStandardStrategySet(){
		def list = productService.getStandardStrategySet();
	}

	def calculateForDefaultStrategies(){
		def map = productService.calculateForDefaultStrategies(params.id)
	}

	def calculateForStandardStrategy(){
		def price = productService.calculateForStandardStrategy(params.id,params.strategy)

		if(BigDecimal.ZERO.compareTo(price) == 0 && "Ideal".equals(params.strategy)) {
			render {
				div(id: "subContainer",style:"margin-top:-40px;",class:"message", "Provide minimum 5 prices to calculate ideal price")
			}
			return
		}

		if(getPriceCount() == 0){
			render {
				div(id: "subContainer",style:"margin-top:-40px;",class:"message", "Add some prices for price calculation")
			}
			return
		}
		render(template: "/template/displayprices", collection:[key:params.strategy,value:price])
	}

	def getPriceCount(){
		def count =  priceService.getPriceCount(params.id)
	}

	def getListToDisplay(){
		def list = priceService.getPriceListTodisplay(params.id)
	}

	def delete(Product product) {
		Product productInstance = Product.get(params.barcode)
		if (productInstance == null) {
			notFound()
			return
		}

		productInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'Product.label', default: 'Product'),
					productInstance.barcode
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}



	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'product.label', default: 'Product'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}

	def filter(Integer max){
		params.max = Math.min(max ?: 10, 100)
		def productList = Product.list()
		int list_count
		println params.id
		println params.value
		if(null !=params.id){
			params.value=params.id
			def searchString = params.value.toUpperCase()
			productList=Product.findAllByBarcodeLike(searchString+'%', params)
			println productList
			list_count = productService.getProductCountForSearch(searchString)
			render(template: "/template/list", model:[productInstanceList: productList,productInstanceCount1: list_count,value:params.value,search:params.id])
		}else if ( "" == params.value || null ==  params.value) {
			productList = Product.list(params)
			render(template: "/template/list", model: [productInstanceList: productList,productInstanceCount1: Product.count()])
		}else if(null != params.value){
			def searchString = params.value.toUpperCase()
			productList=Product.findAllByBarcodeLike(searchString+'%', params)
			list_count = productService.getProductCountForSearch(searchString)
			render(template: "/template/list", model: [productInstanceList: productList,productInstanceCount1: list_count,value:params.value,search:params.id])
		}
	}
}
