package com.prizypricer.domain;

import grails.validation.Validateable;
import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
@Validateable
@EqualsAndHashCode
@ToString
class Price implements Serializable {

	BigDecimal price
	String storeName
	static belongsTo = [product:Product]
	String notes

	static mapping = {
		table 'price'
		version : false
		autowire : true
	}
	static constraints = {
		price nullable : false,scale : 2
		price(min : BigDecimal.ZERO,max : new BigDecimal("99999999.99"))
		storeName blank : false ,nullable : false, unique : true,size : 0..20
		notes widget : 'textarea',size : 0..1000
	}


	public Price(BigDecimal price) {
		this.price = price
	}

	public Price(BigDecimal price,Product product) {
		this.price = price
		this.product = product
	}

}
