
package com.grails.practical.test.groovy

import javax.validation.Constraint;

import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.junit.Before;

import spock.lang.Specification
import spock.lang.Unroll;

import com.grails.practical.Product;
import com.grails.practical.ProductController;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin
import grails.test.mixin.gorm.Domain;
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Holders;


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Product)
class ProductSpec extends Specification{
	
	void "barcode should have unique/blank/nullable/size/matches constraints"() {
		expect:
		Product.constraints.barcode.getAppliedConstraint('unique').parameter
		! Product.constraints.barcode.getAppliedConstraint('blank').blank
		! Product.constraints.barcode.getAppliedConstraint('nullable').nullable
		8..20 == Product.constraints.barcode.getAppliedConstraint('size').parameter
		'^[a-zA-Z0-9]{4,50}$' == Product.constraints.barcode.getAppliedConstraint('matches').parameter
	}
	
	void "product name should have blank/nullable/size constraints"() {
		expect:
		! Product.constraints.productName.getAppliedConstraint('blank').blank
		! Product.constraints.productName.getAppliedConstraint('nullable').nullable
		2..100 == Product.constraints.productName.getAppliedConstraint('size').parameter
	}
	
	void "product description should have blank/nullable/size constraints"() {
		expect:
		! Product.constraints.productDescription.getAppliedConstraint('blank').blank
		! Product.constraints.productDescription.getAppliedConstraint('nullable').nullable
		2..1000 == Product.constraints.productDescription.getAppliedConstraint('size').parameter
	}

	@Unroll
	void "test all constraints on product" (error, field, val) {
		when:
		mockForConstraintsTests(Product, [ new Product(barcode:"I8350OIUNLB121")])
		def obj = new Product("$field": val)
		

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field                       | val
		'blank'            	   | 'barcode'  				 | ''.value
		'matches'              | 'barcode'  			     | 'I8350O@IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O.IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O&IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O$IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O#IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O*IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O^IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O%IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O!IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O(IUNLB121'
		'matches'              | 'barcode'  			     | 'I8350O)IUNLB121'
		'size'                 | 'barcode'                   | getLongString(21)
		'size'                 | 'barcode'                   | getLongString(7)
		'nullable'             | 'barcode'  				 | null
		'blank'                | 'productName'  			 | ''.value
		'nullable'             | 'productName'		         | null
		'size'                 | 'productName'               | getLongString(101)
		'size'                 | 'productName'               | getLongString(1)
		'blank'                | 'productDescription'  	     | ''.value
		'nullable'             | 'productDescription'   	 | null
		'size'                 | 'productDescription'        | getLongString(1001)
		'size'                 | 'productDescription'        | getLongString(1)
		'unique'               | 'barcode'  				 | "I8350OIUNLB121"
	}

	private void validateConstraints(obj, field, error) {
		def validated = obj.validate()
		if (error && error != 'valid') {
			assert !validated
			assert obj.errors[field]
			assert error == obj.errors[field]
		} else {
			assert !obj.errors[field]
		}
	}
	
	String getLongString(Integer length) {
		'a' * length
	}
	
}
