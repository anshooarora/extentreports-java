<#if categoryContext?? && categoryContext?size != 0>
<div id='category-view' class='view hide'>

	<section id='controls'>
		<div class='controls grey lighten-4'>
			<!-- search -->
			<div class='chip transparent' alt='Search Tests' title='Search Tests'>
				<a href="#" class='search-div'>
					<i class='material-icons'>search</i> Search
				</a>
				
				<div class='input-field left hide'>
					<input id='search-tests' type='text' class='validate browser-default' placeholder='Search Tests...'>
				</div>
				
			</div>
			<!-- search -->
		</div>
	</section>

	<div class='subview-left left'>
		
		<div class='view-summary'>
			<h5>Categories</h3>
			<ul id='category-collection' class='category-collection'>
				
				<#list categoryContext as category>
				<li class='category displayed active'>
					<div class='category-heading'>
						<span class='category-name'>${ category.name }</span>
						<span class='category-status right'>
							<#if category.passed != 0>
							<span class='label pass'>${ category.passed }</span>
							</#if>
							<#if category.failed != 0>
							<span class='label fail'>${ category.failed }</span>
							</#if>
							<#if category.others != 0>
							<span class='label others'>${ category.others }</span>
							</#if>
						</span>
					</div>
					<div class='category-content hide'>
						<div class='category-status-counts'>
							<#if category.passed != 0><span class='label green accent-4 white-text'>Passed: ${ category.passed }</span></#if>
							<#if category.failed != 0><span class='label red lighten-1 white-text'>Failed: ${ category.failed }</span></#if>
							<#if category.others != 0><span class='label yellow darken-2 white-text'>Others: ${ category.others }</span></#if>
						</div>
					
						<div class='category-tests'>
							<table class='bordered table-results'>
								<thead>
									<tr>
										<th>Timestamp</th>
										<th>TestName</th>
										<th>Description</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<#list category.getTestList() as test>
									<tr>
										<td>${ test.startTime?datetime?string }</td>
										<td class='linked' test-id='${ test.getID() }'>${ test.hierarchicalName }</td>
										<td>${ test.description?html}</td>
										<td><span class='test-status ${ test.status }'>${ test.status }</span></td>
									</tr>
									</#list>
								</tbody>
							</table>
						</div>
					</div>
				</li>
				</#list>

			</ul>
		</div>
	</div>

	<div class='subview-right left'>
		<div class='view-summary'>
			<h5 class='category-name'></h5>
		</div>
	</div>
</div>
</#if>
<!-- category view -->