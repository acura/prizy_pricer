<%@ page import="com.prizypricer.domain.Product" %>
<html>
<head>
<link rel="stylesheet" href="${resource(dir: 'css', file: '_grails.css')}" type="text/css"/>
</head>
<body>
	<div
		class="fieldcontain ${hasErrors(bean: productInstance, field: 'barcode', 'error')} required">
		<label for="barcode"> <g:message code="product.barcode.label"
				default="Bar Code" /> <span class="required-indicator">*</span>
		</label>
		<g:textField name="barcode" required="" minlength="8" maxlength="20" value="${productInstance?.barcode}" />
	</div>

	<div
		class="fieldcontain ${hasErrors(bean: productInstance, field: 'productName', 'error')} required">
		<label for="productName"> <g:message
				code="product.productName.label" default="Product Name" /> <span
			class="required-indicator">*</span>
		</label>
		<g:textField name="productName" minlength="2" maxlength="50" required=""
			value="${productInstance?.productName}" />

	</div>
	<div
		class="fieldcontain ${hasErrors(bean: productInstance, field: 'productDescription', 'error')} required">
		<label for="productDescription"> <g:message
				code="product.productDescription.label"
				default="Product Description" /> <span class="required-indicator">*</span>
		</label>
		<g:textArea class="areaSize" name="productDescription" minlength="2" maxlength="1000" required=""
			value="${productInstance?.productDescription}" />
	</div>
</body>
</html>
