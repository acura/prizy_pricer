
package com.grails.practical.test;

import java.security.DomainCombiner;

import javax.validation.Constraint;
import javax.validation.constraints.AssertFalse;

import org.junit.Before;

import com.grails.practical.Product;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin
import grails.test.mixin.gorm.Domain;
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Product)
class ProductSpec extends Specification {

	def setup() {
		
	}

	def cleanup() {
	}

	void "test product barcode not null"() {
		when:
		domain.barcode = null
	
		then:
		!domain.validate(['barcode'])
	}
	
	void "test product barcode not blank"(){
		when:
		domain.barcode = ''
		
		then:
		!domain.validate(['barcode'])
		
	}
	
	void "test product barcode is unique"(){
		when:
		def product = new Product("XXXxxx12", "moto-g", "xxxxxxxxxxxx")
		def product1 = new Product("XXXxxx13", "moto-g", "xxxxxxxxxxxx")
		assert product.barcode != product1.barcode
		
		then:
		!domain.validate(['barcode'])
		
	}
	
	void "test product name not null"() {
		when:
		domain.productName = null
	
		then:
		!domain.validate(['productName'])
		
	}
	
	void "test product name not blank"(){
		when:
		domain.productName = ''
		
		then:
		!domain.validate(['productName'])
		
	}
	
	void "test product name is unique"(){
		when:
		def product = new Product("XXXxxx12", "moto-g", "xxxxxxxxxxxx")
		def product1 = new Product("XXXxxx12", "moto-g", "xxxxxxxxxxxx")
		assert product.productName,product1.productName
		
		then:
		!domain.validate(['productName'])
	}
}
