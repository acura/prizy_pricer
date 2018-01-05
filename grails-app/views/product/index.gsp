
<%@ page import="com.grails.practical.Product"%>
<!DOCTYPE html>
<html>
<head>
<g:javascript library="jquery" />
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'product.label', default: 'Product')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: '_grails.css')}" type="text/css" />
</head>
<body>


	<a href="#list-product" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div>
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
	</div>
	<div>
		<g:form controller='product' action='filter' align="right">
			<g:link href="#" onclick="javascript:window.history.back();"
				class="link" style="margin-right:290px;"> 
					<< Previous page</g:link>
		   				Search Barcode:<g:remoteField action="filter"
				controller="Product" update="list-product" name="search"
				placeholder="Serach By Barcode" value="${search}" />
			<g:submitButton name="submit" value="Search" class="submit-button" />
		</g:form>
	</div>

	<div id="list-product" class="content scaffold-list" role="main"
		style="position: sticky;">

		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>


		<div id="list">
			<g:render template="/template/list" />
		</div>
	</div>
</body>

</html>
