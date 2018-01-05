
package com.grails.practical.test

import java.security.DomainCombiner;

import com.grails.practical.Price;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Price)
class PriceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test price not null"() {
		when:
		domain.price = null
	
		then:
		!domain.validate(['price'])
    }
	
	
	
	
	void "test price is not less than 0.0"(){
		when:
		domain.price < new BigDecimal(0.0)
		
		then:
		!domain.validate(['price'])
		
	}
	
	void "test price is not greater 99999999.99"(){
		when:
		domain.price > new BigDecimal(99999999.99)
		
		then:
		!domain.validate(['price'])
		
	}
}
