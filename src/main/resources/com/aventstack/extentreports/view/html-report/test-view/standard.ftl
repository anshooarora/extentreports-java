<div class='test-time-info'>
	<span class='label start-time'>${ test.startTime?datetime?string["${timeStampFormat}"] }</span>
	<span class='label end-time'>${ test.endTime?datetime?string["${timeStampFormat}"] }</span>
	<span class='label time-taken grey lighten-1 white-text'>${ test.getRunDuration()?string }</span>
</div>
<#if test.description?? && test.description?has_content>
	<div class='test-desc'>${ test.description} </div>
</#if>
<#if test.hasAuthor() || test.hasCategory()>
	<div class='test-attributes'>
		<#if test.hasCategory()>
			<div class='category-list'>
				<#list test.categoryContext.all as category>
				<span class='category label white-text'>${ category.name }</span>
				</#list>
			</div>
		</#if>
		<#if test.hasAuthor()>
			<div class='author-list'>
				<#list test.authorContext.all as author>
				<span class='author label white-text'>${ author.name }</span>
				</#list>
			</div>
		</#if>
	</div>
</#if>
<#if test.hasLog()>
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
		<#macro recurse_nodes nodeList>
			<#list nodeList as node>
				<#assign leaf=(node.hasChildren())?then('','leaf')>
				<li class='node level-${ node.level } ${ leaf } ${ node.status }' status='${ node.status }' test-id='${ node.getID() }'>
					<div class='collapsible-header'>
						<div class='node-name'>${ node.name }</div>						
						<span class='node-time'>${ node.startTime?datetime?string["${timeStampFormat}"] }</span>
						<span class='node-duration'>${ node.runDuration }</span>
						<span class='test-status right ${ node.status }'>${ node.status }</span>
					</div>
					<#if node.hasLog()>
						<div class='collapsible-body'>
							<#if node.hasLog()>
								<#if node.hasCategory()>
									<div class='category-list right'>
										<#list node.categoryContext.all as category>
											<span class='category label white-text'>${ category.name }</span>
										</#list>
									</div>
								</#if>
								<#if node.description?? && node.description?has_content>
									<div class='node-desc'>${ node.description}</div>
								</#if>
								<#if node.hasAuthor()>
									<div class='author-list right'>
										<#list node.authorContext.all as author>
											<span class='author label white-text'>${ author.name }</span>
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
					<#if node.hasChildren()>
						<ul class='collapsible node-list' data-collapsible='accordion'>
							<@recurse_nodes nodeList=node.nodeContext.all />
						</ul>
					</#if>
				</li>
			</#list>
		</#macro>
		
		<@recurse_nodes nodeList=test.nodeContext.all />
	</ul>
</#if>
