
<%@ page import="com.grails.practical.Product" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<g:javascript library="jquery"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: '_grails.css')}" type="text/css"/>
	</head>
	<body>
		<a href="#show-product" class="skip align-content" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
	
	<div id="show-product" class="content scaffold-show" role="main" >
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message"  role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list product" style="padding-bottom: 0%;">
			
				<g:if test="${productInstance?.barcode}">
				<li class="fieldcontain">
					<span id="barcode-label" class="property-label"><g:message code="product.barcode.label" default="Barcode:" /></span>
					
						<span class="property-value" aria-labelledby="barcode-label"><g:fieldValue bean="${productInstance}" field="barcode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.productName}">
				<li class="fieldcontain">
					<span id="productName-label" class="property-label"><g:message code="product.productName.label" default="Product Name:" /></span>					
						<span class="property-value" aria-labelledby="productName-label"><g:fieldValue bean="${productInstance}" field="productName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.productDescription}">
				<li class="fieldcontain">
					<span id="productDescription-label" class="property-label"><g:message code="product.productDescription.label" default="Product Description:" /></span>
						<span class="property-value max-lines" aria-labelledby="productDescription-label"><g:fieldValue bean="${productInstance}" field="productDescription"/></span>
					<g:hiddenField name="productInstance" value="${productInstance}" />
				</li>
				</g:if>
	
			<g:if test="${priceCount}">
				<li class="fieldcontain">
				<g:each in="${defaultStrategyMap}" var="var">
						<span class="property-label"><g:message code="price.label"	default="${var.key}:" /></span>
						<span class="property-value" aria-labelledby="stratergy-label">	<g:message code="default.amount.currency" args="${var.value}" /></span>
						<br />
				</g:each>
				 </li>
			</g:if>
		</ol>
		
   
	<g:if test="${priceCount}">
		<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'product', 'error')} required" style="margin-top: 0px;margin-left: 30px;">
			<g:form controller="product">
				<span id="strategylist" class="property-label" ><g:message code="price.calulationstrategy.label" default="Calculate Price:" /></span>
				<span class="property-value" aria-labelledby="strategy-label">
				<g:select  name="strategy" from="${standardStrategySet}"
						onchange="${remoteFunction(action:'calculateForStandardStrategy',update:'subContainer', id:productInstance?.barcode, params:'\'strategy=\' + jQuery(this).val()')}"  noSelection="${['null':'-Select Strategy-']}"/></span><br />
				<div id="subContainer" style="height:20px;margin-top: -40px;padding-left: 350px;"><g:message/></div>
			</g:form>	
		 </div>
	</g:if>
	
	<g:if test="${priceCount}">
		 <div class="fieldcontain ${hasErrors(bean: productInstance, field: 'product', 'error')} required" style="margin-top: 20px;margin-left: 30px;">
			<g:form controller="product">
				<div id="pricecount" >
				<span id="count" class="property-label" ><g:message code="price.pricecount.label" default="Price Count:" /></span>
				<span class="property-value" aria-labelledby="strategy-label">${priceCount}</span></div>
			</g:form>	
		 </div>
		 </g:if>
		 </div>
		 <div style="margin-left: 150px;margin-top: 25px;">
		 	<g:link controller="price" action="create">Add Prices</g:link>
		 </div>
	</body>
	
</html>
