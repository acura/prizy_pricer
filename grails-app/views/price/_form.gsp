<%@ page import="com.grails.practical.Price" %>

<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="price.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" value="${fieldValue(bean: priceInstance, field: 'price')}" required="" maxlength="10"/>

</div>

<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
	<label for="product">
		<g:message code="price.product.label" default="Product" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="barcode" name="product.id" from="${com.grails.practical.Product.list()}" optionKey="barcode" optionValue="productName" value="${priceInstance?.product?.barcode}" class="many-to-one"/>
</div>



