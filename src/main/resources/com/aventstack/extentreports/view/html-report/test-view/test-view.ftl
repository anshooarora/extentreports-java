<div id='test-view' class='view'>
				
	<section id='controls'>
		<div class='controls grey lighten-4'>
			<!-- test toggle -->
			<div class='chip transparent'>
				<a class='dropdown-button tests-toggle' data-activates='tests-toggle' data-constrainwidth='true' data-beloworigin='true' data-hover='true' href='#'>
					<i class='material-icons'>warning</i> Status
				</a>
				<ul id='tests-toggle' class='dropdown-content'>
					<#if report.containsStatus(Status.PASS)>
						<li status='pass'><a href='#!'>Pass <i class='material-icons green-text'>check_circle</i></a></li>
					</#if>
					<#if report.containsStatus(Status.FATAL)>
						<li status='fatal'><a href='#!'>Fatal <i class='material-icons red-text darken-3'>cancel</i></a></li>
					</#if>
					<#if report.containsStatus(Status.FAIL)>
						<li status='fail'><a href='#!'>Fail <i class='material-icons red-text'>cancel</i></a></li>
					</#if>
					<#if report.containsStatus(Status.ERROR)>
						<li status='error'><a href='#!'>Error <i class='material-icons red-text lighten-2'>error</i></a></li>
					</#if>
					<#if report.containsStatus(Status.WARNING)>
						<li status='warning'><a href='#!'>Warning <i class='material-icons orange-text'>warning</i></a></li>
					</#if>
					<#if report.containsStatus(Status.SKIP)>
						<li status='skip'><a href='#!'>Skip <i class='material-icons cyan-text'>redo</i></a></li>
					</#if>
					<li class='divider'></li>
					<li status='clear' clear='true'><a href='#!'>Clear Filters <i class='material-icons'>clear</i></a></li>
				</ul>
			</div>
			<!-- test toggle -->

			<!-- category toggle -->
			<#if categoryContext?? && categoryContext?size != 0>
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

	<#if testViewChartLocation=='top'>
		<#include 'test-view-charts.ftl'>
	</#if>

	<div class='subview-left left'>
		
		<div class='view-summary'>
			<h5>Tests</h3>
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
							<#include 'bdd.ftl'>							
						<#else>
							<#include 'standard.ftl'>
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