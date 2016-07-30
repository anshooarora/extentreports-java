<div id='test-view' class='view'>
				
	<section id='controls'>
		<div class='controls grey lighten-4'>
			<!-- test toggle -->
			<div class='chip transparent'>
				<a class='dropdown-button tests-toggle' data-activates='tests-toggle' data-constrainwidth='true' data-beloworigin='true' data-hover='true' href='#'>
					<i class='material-icons'>warning</i> Status
				</a>
				<ul id='tests-toggle' class='dropdown-content'>
					<li status='pass'><a href='#!'>Pass <i class='material-icons green-text'>check_circle</i></a></li>
					<li status='fail'><a href='#!'>Fail <i class='material-icons red-text'>cancel</i></a></li>
					<li status='error'><a href='#!'>Error <i class='material-icons red-text lighten-2'>error</i></a></li>
					<li status='warning'><a href='#!'>Warning <i class='material-icons orange-text'>warning</i></a></li>
					<li status='skip'><a href='#!'>Skip <i class='material-icons cyan-text'>redo</i></a></li>
					<li class='divider'></li>
					<li status='clear' clear='true'><a href='#!'>Clear Filters <i class='material-icons'>clear</i></a></li>
				</ul>
			</div>
			<!-- test toggle -->

			<!-- category toggle -->
			<#if categoryContext??>
			<div class='chip transparent'>
				<a class='dropdown-button category-toggle' data-activates='category-toggle' data-constrainwidth='false' data-beloworigin='true' data-hover='true' href='#'>
					<i class='material-icons'>local_offer</i> Category
				</a>
				<ul id='category-toggle' class='dropdown-content'>
					<#list categoryContext as category>
					<li><a href='#'>${ category.getName() }</a></li>
					</#list>
					<li class='divider'></li>
					<li class='clear'><a href='#!' clear='true'>Clear Filters</a></li>
				</ul>
			</div>
			</#if>
			<!-- category toggle -->

			<!-- clear filters -->
			<div class='chip transparent hide'>
				<a class='' id='clear-filters' alt='Clear Filters' title='Clear Filters'>
					<i class='material-icons'>close</i> Clear
				</a>
			</div>
			<!-- clear filters -->

			<!-- enable dashboard -->
			<div id='toggle-test-view-charts' class='chip transparent'>
				<#assign btnEnabledClass=(chartVisibleOnOpen=='true')?then('pink-text','')>
				<a class='${ btnEnabledClass }' id='enable-dashboard' alt='Enable Dashboard' title='Enable Dashboard'>
					<i class='material-icons'>track_changes</i> Dashboard
				</a>
			</div>
			<!-- enable dashboard -->

			<!-- search -->
			<div class='chip transparent' alt='Search Tests' title='Search Tests'>
				<a href="#" class=''>
					<i class='material-icons'>search</i> Search
				</a>

				<div class='input-field left hide'>
					<input id='search-tests' type='text' class='validate' placeholder='Search Tests...'>
				</div>
				
			</div>
			<!-- search -->
		</div>
	</section>

	<#if testViewChartLocation=='top'>
		<#include 'test-view-charts.ftl'>
	</#if>

	<div class='subview-left left'>
		
		<div class='view-summary'>
			<h5>Tests</h5>

			<ul id='test-collection' class='test-collection'>
				<#list report.testList as test>
				
				<#assign isBdd = (test.hasChildren() && test.nodeContext.get(0).isBehaviorDrivenType())>

				<#assign hasChildrenClass = ''>
				<#if test.nodeContext?? && test.nodeContext.all?size != 0>
					<#assign hasChildrenClass = 'has-leaf'>
				</#if>
				
				<li class='test displayed active ${ hasChildrenClass } ${ test.status }' status='${ test.status }' bdd='${ isBdd?string }' test-id='${ test.getID() }'>
					<div class='test-heading'>
						<span class='test-name'>${ test.name }</span>
						<span class='test-time'>${ test.startTime?datetime?string }</span>
						<span class='test-status right ${ test.status }'>${ test.status }</span>
					</div>
					<div class='test-content hide'>
						<#if isBdd>
							<#list test.nodeContext.all as node>
							<div class='${ node.getBehaviorDrivenType().getSimpleName()?lower_case } node' test-id='${ node.getID() }' status='${ node.status }'>
								<#if node.hasCategory()>
								<div class='category-list'>
									<#list node.categoryList as category>
									<span class='category label white-text'>${ category.name }</span>
									</#list>
								</div>
								</#if>
								<span class='${ node.getBehaviorDrivenType().getSimpleName()?lower_case }-duration right label'>${ node.runDuration }</span>
								<div class='${ node.getBehaviorDrivenType().getSimpleName()?lower_case }-desc'>
									<b>${ node.getBehaviorDrivenType().getSimpleName()?capitalize }</b>
									${ node.name }
									<#if node.screenCaptureList?? && node.screenCaptureList?size != 0>
									<ul class='screenshots right'>
										<#list node.screenCaptureList as sc>
										<li><a data-featherlight="image" href="${ sc.path }"><i class='material-icons'>panorama</i></a></li>
										</#list>
									</ul>
									</#if>
									<#if node.description?? && node.description?has_content>
									<div class='pre'>${ node.description }</div>
									</#if>
								</div>
								<#if node.hasChildren()>
								<ul class='steps'>
									<#list node.nodeContext.all as child>
									<li test-id='${ child.getID() }' class='node ${ child.getBehaviorDrivenType().getSimpleName()?lower_case } ${ child.status }' status='${ child.status }'><b>${ child.getBehaviorDrivenType().getSimpleName()?string }</b>
										${ child.name }
										<#if child.screenCaptureList?? && child.screenCaptureList?size != 0>
										<ul class='screenshots right'>
											<#list child.screenCaptureList as sc>
											<li><a data-featherlight="image" href="${ sc.path }"><i class='material-icons'>panorama</i></a></li>
											</#list>
										</ul>
										</#if>
										<#if child.description?? && child.description?has_content>
										<div class='pre'>${ child.description }</div>
										</#if>
										<#list child.logContext.all as log>
											<#if log.status?string?lower_case != 'pass' && log.status?string?lower_case != 'info'>
											<div class='pre'>${ log.details }</div>
											</#if>
										</#list>
									</li>
									</#list>
								</ul>
								</#if>
							</div>
							</#list>
						<#else>
						<div class='test-time-info'>
							<span class='label start-time'>${ test.startTime?datetime?string }</span>
							<span class='label end-time'>${ test.endTime?datetime?string }</span>
							<span class='label time-taken grey lighten-1 white-text'>${ test.getRunDuration()?string }</span>
						</div>
						<#if test.description?? && test.description?has_content>
						<div class='test-desc'>${ test.description} </div>
						</#if>
						<#if test.hasAuthor() || test.hasCategory()>
						<div class='test-attributes'>
							<#if test.hasCategory()>
							<div class='category-list'>
								<#list test.categoryList as category>
								<span class='category label white-text'>${ category.name }</span>
								</#list>
							</div>
							</#if>
							<#if test.hasAuthor()>
							<div class='author-list'>
								<#list test.getAuthorList() as author>
								<span class='author label white-text'>${ author.name }</span>
								</#list>
							</div>
							</#if>
						</div>
						</#if>
						<#if test.hasLogs()>
						<div class='test-steps'>
							<table class='bordered table-results'>
								<thead>
									<tr>
										<th>Status</th>
										<th>Timestamp</th>
										<#if test.getLogContext().get(0).stepName??>
										<th>StepName</th>
										</#if>
										<th>Details</th>
									</tr>
								</thead>
								<tbody>
									<#list test.getLogContext().getAll() as log>
									<tr class='log' status='${ log.status }'>
										<td class='status ${ log.status }' title='${ log.status }' alt='${ log.status }'><i class='material-icons'>${ Icon.getIcon(log.status) }</i></td>
										<td class='timestamp'>${ log.timestamp?time?string }</td>
										<#if log.stepName??>
										<td class='step-name'>${ log.stepName }</td>
										</#if>
										<td class='step-details'>${ log.details }</td>
									</tr>
									</#list>
								</tbody>
							</table>
						</div>
						</#if>
						<#if test.nodeContext?? && test.nodeContext.all?size != 0>
						<ul class='collapsible node-list' data-collapsible='accordion'>
							<@recurse_nodes nodeList=test.nodeContext.all depth=1 />
							<#macro recurse_nodes nodeList depth>
							<#list nodeList as node>
							<#assign leaf=(node.hasChildren())?then('','leaf')>
							<li class='node level-${ node.level } ${ leaf } ${ node.status }' status='${ node.status }' test-id='${ node.getID() }'>
								<div class='collapsible-header'>
									<div class='node-name'>${ node.name }</div>
									<#if node.hasLogs()>
									<span class='node-time'>${ node.startTime?datetime?string }</span>
									<span class='test-status right ${ node.status }'>${ node.status }</span>
									</#if>
								</div>
								<#if node.hasLogs()>
								<div class='collapsible-body'>
									<#if node.hasLogs()>
										<#if node.hasCategory()>
										<div class='category-list right'>
											<#list node.categoryList as category>
											<span class='category label white-text'>${ category.name }</span>
											</#list>
										</div>
										</#if>
										<div class='node-steps'>
											<table class='bordered table-results'>
												<thead>
													<tr>
														<th>Status</th>
														<th>Timestamp</th>
														<#if node.getLogContext().get(0).stepName??>
														<th>StepName</th>
														</#if>
														<th>Details</th>
													</tr>
												</thead>
												<tbody>
													<#list node.getLogContext().getAll() as log>
													<tr class='log' status='${ log.status }'>
														<td class='status ${ log.status }' title='${ log.status }' alt='${ log.status }'><i class='material-icons'>${ Icon.getIcon(log.status) }</i></td>
														<td class='timestamp'>${ log.timestamp?time?string }</td>
														<#if log.stepName??>
														<td class='step-name'>${ log.stepName }</td>
														</#if>
														<td class='step-details'>${ log.details }</td>
													</tr>
													</#list>
												</tbody>
											</table>
											<#if node.screenCaptureList?? && node.screenCaptureList?size != 0>
											<ul class='screenshots'>
												<#list node.screenCaptureList as sc>
												<li>${ sc.source }</li>
												</#list>
											</ul>
											</#if>
										</div>
									</#if>
								</div>
								</#if>
							</li>
							<@recurse_nodes nodeList=node.nodeContext.all depth=depth+1 />
							</#list>
							</#macro>
						</ul>
						</#if>
						</#if>
						<#if test.screenCaptureList?? && test.screenCaptureList?size != 0>
						<ul class='screenshots'>
							<#list test.screenCaptureList as sc>
							<li>${ sc.source }</li>
							</#list>
						</ul>
						</#if>
					</div>
				</li>
				</#list>
			</ul>
		</div>
	</div>
	<!-- subview left -->

	<div class='subview-right left'>
		<div class='view-summary'>
			<h5 class='test-name'></h5>

			<div id='step-filters' class="right">
				<span class="blue-text" status="info" alt="info" title="info"><i class="material-icons">info_outline</i></span>
				<span class="green-text" status="pass" alt="pass" title="pass"><i class="material-icons">check_circle</i></span>
				<span class="red-text" status="fail" alt="fail" title="fail"><i class="material-icons">cancel</i></span>
				<span class="red-text text-darken-4" status="fatal" alt="fatal" title="fatal"><i class="material-icons">cancel</i></span>
				<span class="pink-text text-lighten-1" status="error" alt="error" title="error"><i class="material-icons">error</i></span>
				<span class="orange-text" alt="warning" status="warning" title="warning"><i class="material-icons">warning</i></span>
				<span class="teal-text" status="skip" alt="skip" title="skip"><i class="material-icons">redo</i></span>
				<span status="clear" alt="Clear filters" title="Clear filters"><i class="material-icons">clear</i></span>
			</div>
		</div>
	</div>
	<!-- subview right -->

	<#if testViewChartLocation=='bottom'>
		<#include 'test-view-charts.ftl'>
	</#if>
</div>
<!-- test view -->