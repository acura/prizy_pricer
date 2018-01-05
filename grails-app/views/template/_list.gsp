<div>
	<table style="width: 100%">
		<thead>
			<tr style="width: 100%">

				<util:remoteSortableColumn controller="Product" action="filter"
					update="list" property="barcode" id="${value}"
					title="${message(code: 'product.barcode.label', default: 'Barcode')}" />

				<util:remoteSortableColumn controller="Product" action="filter"
					update="list" property="productName" id="${value}"
					title="${message(code: 'product.productname.label', default: 'Product Name')}" />

			</tr>
		</thead>
		<tbody>
			<g:each in="${productInstanceList}" status="i" var="productInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

					<td style="width: 50%"><g:link action="show"
							update="list-product" id="${productInstance.barcode}">
							${fieldValue(bean: productInstance, field: "barcode")}
						</g:link></td>

					<td style="width: 50%">
						${fieldValue(bean: productInstance, field: "productName")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
</div>
<div class="pagination">
	<util:remotePaginate controller="Product" action="filter"
		total="${productInstanceCount1 ?: 0}" id="${value}" update="list"></util:remotePaginate>
</div>