package com.prizypricer.controller



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import com.prizypricer.domain.Product

@Transactional
class ProductController {
	def productService;
	def priceService
	static allowedMethods = [save: "POST", delete: "DELETE"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Product.list(params),model:[productInstanceCount: Product.count()]
	}

	def show(Product productInstance) {
		def priceCount = getPriceCount()
		def priceMap = calculateIdealPrice()
		respond productInstance, model:[priceMap:priceMap,priceCount:priceCount]
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

	def calculateIdealPrice(){
		def price = productService.calculateIdealPrice(params.id)
	}

	def getPriceCount(){
		def count =  priceService.getPriceCount(params.id)
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

	def search(Integer max){
		params.max = Math.min(max ?: 10, 100)
		def productList
		def productCount
		def searchText = params.value
		
		println "in search"

		if(null !=searchText){
			productList=Product.findAllByBarcodeLike(searchText +'%',params)
			productCount = getProductCount(searchText)
		}else{
			productList = Product.list(params)
			productCount = Product.count()
		}
		
		render(template: "/template/list", model: [productInstanceList: productList,productInstanceCount: productCount,value:searchText])
	}
	
	def getProductCount(String searchText) {
		def productList = Product.createCriteria().list(){
			like('barcode', "%"+searchText.trim()+"%")
			projections { count() }
		}
		return productList[0]
	}
}
