
<%@ page import="com.prizypricer.domain.Price" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'price.label', default: 'Price')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-price" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<g:if test="${flash.message}">
				<div id="msg" class="message" role="status">${flash.message}</div>
		</g:if>	
		<div id="list-price" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>	
			<table>
			<thead>
					 <tr>
						<th><g:message code="price.product.label" default="Product" /></th>
						<g:sortableColumn property="price" title="${message(code: 'price.price.label', default: 'Price')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${priceInstanceList}" status="i" var="priceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${priceInstance?.product?.productName?.encodeAsHTML()}</td>
						<td><g:link action="show" id="${priceInstance.id}" params="${priceInstance.product.barcode }">${fieldValue(bean: priceInstance, field: "price")}</g:link></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			
		<div class="pagination" style="position: sticky;">
			<g:paginate next="next" total="${priceInstanceCount}" />
		</div>
		
	</div>
	</body>
</html>
