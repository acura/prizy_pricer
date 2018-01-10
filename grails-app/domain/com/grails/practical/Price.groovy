package com.grails.practical;

import grails.validation.Validateable;
import groovy.transform.EqualsAndHashCode;
@Validateable
@EqualsAndHashCode
class Price implements Serializable {

	BigDecimal price;
	static belongsTo = [product:Product]
	
	static mapping = {
		table 'price'
		version:false
		autowire: true
	}
	static constraints = {
		price nullable:false,scale:2
		price(min:BigDecimal.ZERO,max:new BigDecimal("99999999.99"))
	}


	public Price(BigDecimal price) {
		this.price = price;
	}
	
	public Price(BigDecimal price,Product product) {
		this.price = price;
		this.product = product;
	}
	
	
	@Override
	public String toString() {
		return price;
	}
}
