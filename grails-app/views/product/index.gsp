
<%@ page import="com.prizypricer.domain.Product" %>
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
		<g:form align="right">
		   				Search <g:remoteField action="search"
				controller="Product" update="list" name="search"
				placeholder="Serach By Barcode"/>
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
	<g:javascript>
	$(document).ready(function() {
 		 $(window).keydown(function(event){
		    if(event.keyCode == 13) {
		      event.preventDefault();
		      return false;
   		 }
  		});
	});
	</g:javascript>
</body>

</html>
