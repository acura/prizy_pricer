<%@ page import="com.prizypricer.domain.Price"%>

<div
	class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
	<label for="price"> <g:message code="price.price.label"
			default="Price" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="price" maxlength="10" required=""
		value="${fieldValue(bean: priceInstance, field: 'price')}" />

</div>

<div
	class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
	<label for="product"> <g:message code="price.product.label"
			default="Product" /> <span class="required-indicator">*</span>
	</label>
	<g:select id="barcode" name="product.id"
		from="${productList }}" optionKey="barcode"
		optionValue="productName" value="${priceInstance?.product?.barcode}"
		class="many-to-one" />
</div>

<div
	class="fieldcontain ${hasErrors(bean: priceInstance, field: 'storeName', 'error')} required">
	<label for="storeName"> <g:message code="price.storeName.label"
			default="Store Name" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="storeName" minlength="0" maxlength="50" required=""
		value="${priceInstance?.storeName}" />

</div>
<div
	class="fieldcontain ${hasErrors(bean: priceInstance, field: 'notes', 'error')} required">
	<label for="notes"> <g:message code="price.notes.label"
			default="Notes" /> <span class="required-indicator">*</span>
	</label>
	<g:textArea class="areaSize" name="notes" minlength="0"
		maxlength="1000" required="" value="${priceInstance?.notes}" />
</div>



