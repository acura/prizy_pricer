
package com.grails.practical.test.groovy

import com.jbilling.prizypricer.domain.Price;

import grails.test.mixin.TestFor;
import grails.test.mixin.TestMixin;
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll



/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Price)
class PriceSpec extends Specification {
	
	void "price should have min/max/nullable/scale constraints"() {
		expect:
		Price.constraints.price.getAppliedConstraint('min').parameter.compareTo(BigDecimal.ZERO) == 0
		Price.constraints.price.getAppliedConstraint('max').parameter.compareTo(new BigDecimal("99999999.99")) == 0
		! Price.constraints.price.getAppliedConstraint('nullable').nullable
		2 == Price.constraints.price.getAppliedConstraint('scale').parameter
	}

	@Unroll
	void "test all constraints on price" (error, field, val) {
		when:
		mockForConstraintsTests(Price, [new Price()])
		def obj = new Price("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field                       | val
		'nullable'             | 'price'  				     | null
		'min'                  | 'price'  				     | new BigDecimal("-1.00")
		'max'                  | 'price'  				     | new BigDecimal("10000000000")
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
}
