package com.grails.practical

import grails.validation.Validateable;

@Validateable
class Product implements Serializable{
	String barcode
	String productName
	String productDescription
	static hasMany = [prices:Price]

	public Product(String barcode, String productName, String productDescription) {
		this.barcode = barcode;
		this.productName = productName;
		this.productDescription = productDescription;
	}

	static constraints = {
		barcode blank:false ,nullable:false, unique:true,size:8..20,matches: '^[a-zA-Z0-9]{4,50}$'
		productName blank: false,nullable:false,size:2..100
		productDescription widget:'textarea', blank: false,nullable:false,size:2..1000
	}

	static mapping = {
		table 'product'
		version:false
		id generator: 'assigned', name: "barcode",type:'String'
		prices cascade: 'all-delete-orphan'
		productDescription sqlType: 'longText'
	}

	@Override
	public String toString() {
		return "Product [barcode=" + barcode + ", productName=" + productName+ ", productDescription=" + productDescription + "]";
	}

}
