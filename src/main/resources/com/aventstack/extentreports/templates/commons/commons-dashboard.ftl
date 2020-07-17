<div class="content-main" id="content-main">
	<div class="padding">
		<div class="row">
			<div class="col-6 col-lg-3">
				<div class="box p-3">
					<div class="d-flex">
						<span class="">${parentHeading}</span>
					</div>
					<div class="py-3 text-center text-md text-success">
						${report.reportStatusStats.parentCount}
					</div>
					<div class="d-flex">
						<span class="flex text-muted">Out of which ${report.reportStatusStats.parentCountPass} passed</span>
						<span><i class="fa fa-circle-o text-success"></i> ${report.reportStatusStats.parentPercentagePass?string("#.00")}%</span>
					</div>
				</div>
			</div>
			<div class="col-6 col-lg-3">
				<div class="box p-3">
					<div class="d-flex">
						<span class="">${childHeading}</span>
					</div>
					<div class="py-3 text-center text-md text-primary">
						${report.reportStatusStats.childCount}
					</div>
					<div class="d-flex">
						<span class="flex text-muted">Out of which ${report.reportStatusStats.childCountPass} passed</span>
						<span><i class="fa fa-circle-o text-primary"></i> ${report.reportStatusStats.childPercentagePass?string("#.00")}%</span>
					</div>
				</div>
			</div>
			<div class="col-6 col-lg-3">
				<div class="box p-3">
					<div class="d-flex">
						<span class="">Start</span>
					</div>
					<div class="py-3 text-center text-md">
						${report.startTime?datetime?string["${timeStampFormat}"]}
					</div>
					<div class="d-flex">
						<span class="flex"></span>
						<span><i class="fa fa-circle text-primary"></i></span>
					</div>
				</div>
			</div>
			<div class="col-6 col-lg-3">
				<div class="box p-3">
					<div class="d-flex">
						<span class="">Time Taken</span>
					</div>
					<div class="py-3 text-center text-md">
						${report.longRunDuration}
					</div>
					<div class="d-flex">
						<span class="flex"></span>
						<span><i class="fa fa-circle text-danger"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-${12/size}">
				<div class="box">
					<div class="box-header">
						<h3>${parentHeading}</h3>
					</div>
					<div class="box-body pb-4">
						<div class="row">
							<div class="col-lg-7">
								<canvas id="pc" width="100" height="80">
								</canvas>
							</div>
							<div class="col-lg-5">
								<div class="list">
									<#if report.reportStatusStats.parentCountPass!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-info"></i> &nbsp;Pass ${report.reportStatusStats.parentPercentagePass?string("#.00")}%</div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountFail!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-danger"></i> &nbsp;Fail ${report.reportStatusStats.parentPercentageFail?string("#.00")}%</div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountSkip!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-warning"></i> &nbsp;Skip ${report.reportStatusStats.parentPercentageSkip?string("#.00")}%</div>
									</div>
									</#if>
								</div>
							</div>
						</div>
					</div>
					<div class="box-footer">
						<small class="text-muted">${report.reportStatusStats.parentCountPass} passed, ${report.reportStatusStats.parentCountFail} failed and ${report.reportStatusStats.parentCountSkip} skipped</small>
					</div>
				</div>
			</div>
			<#if report.reportStatusStats.childCount!=0>
			<div class="col-md-${12/size}">
				<div class="box">
					<div class="box-header">
						<h3>${childHeading}</h3>
					</div>
					<div class="box-body pb-4">
						<div class="row">
							<div class="col-lg-7">
								<canvas id="cc" width="100" height="80">
								</canvas>
							</div>
							<div class="col-lg-5">
								<div class="list">
									<#if report.reportStatusStats.parentCountPass!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-info"></i> &nbsp;Pass ${report.reportStatusStats.childPercentagePass?string("#.00")}%</div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountFail!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-danger"></i> &nbsp;Fail ${report.reportStatusStats.childPercentageFail?string("#.00")}%</div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountSkip!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-warning"></i> &nbsp;Skip ${report.reportStatusStats.childPercentageSkip?string("#.00")}%</div>
									</div>
									</#if>
								</div>
							</div>
						</div>
					</div>
					<div class="box-footer">
						<small class="text-muted">${report.reportStatusStats.childCountPass} passed, ${report.reportStatusStats.childCountFail} failed and ${report.reportStatusStats.childCountSkip} skipped</small>
					</div>
				</div>
			</div>
			</#if>
			<#if report.reportStatusStats.grandChildCount!=0>
			<div class="col-md-${12/size}">
				<div class="box">
					<div class="box-header">
						<h3>${grandChildHeading}</h3>
					</div>
					<div class="box-body pb-4">
						<div class="row">
							<div class="col-lg-7">
								<canvas id="gcc" width="100" height="80">
								</canvas>
							</div>
							<div class="col-lg-5">
								<div class="list">
									<#if report.reportStatusStats.parentCountPass!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-info"></i> ${report.reportStatusStats.childPercentagePass}%</div>
										<div class="list-body"><span class="text-muted">Passed</span></div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountFail!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-danger"></i> ${report.reportStatusStats.childPercentageFail}%</div>
										<div class="list-body"><span class="text-muted">Failed</span></div>
									</div>
									</#if>
									<#if report.reportStatusStats.parentCountSkip!=0>
									<div class="list-item">
										<div><i class="badge badge-xs badge-o b-warning"></i> ${report.reportStatusStats.childPercentageSkip}%</div>
										<div class="list-body"><span class="text-muted">Skipped</span></div>
									</div>
									</#if>
								</div>
							</div>
						</div>
					</div>
					<div class="box-footer">
						<small class="text-muted">${report.reportStatusStats.grandChildCountPass} passed, ${report.reportStatusStats.grandChildCountFail} failed and ${report.reportStatusStats.grandChildCountSkip} skipped</small>
					</div>
				</div>
			</div>
			</#if>
		</div>
		<#if config.getConfig("enableTimeline")=='true'>
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header">
						<h3>Timeline</h3>
					</div>
					<div class="box-body">
						<canvas id="timeline"></canvas>
					</div>
				</div>
			</div>
		</div>
		</#if>
		<div class="row">
			<#if systemAttributeContext?size != 0>
			<div class="col-sm-6">
				<div class="box">
					<div class="box-header">
						<h3>System <i class="fa pull-right text-muted text-sm fa-desktop"></i></h3>
					</div>
					<table class="table">
						<tr>
							<th>Name</th>
							<th>Value</th>
						</tr>
						<#list systemAttributeContext as sa>
						<#if sa?? && sa.name?? && sa.value??>
						<tr>
							<td>${ sa.name }</td>
							<td>${ sa.value }</td>
						</tr>
						</#if>
						</#list>
					</table>
				</div>
			</div>
			</#if>
			<#if categoryContext?? && categoryContext?size != 0>
			<div class="col-sm-6">
				<div class="box">
					<div class="box-header">
						<h3>Tags <i class="fa pull-right text-muted text-sm fa-tag"></i></h3>
					</div>
					<table class="table">
						<tr>
							<th></th>
							<th>Passed</th>
							<th>Failed</th>
							<th>Others</th>
						</tr>
						<#list categoryContext as category>
						<tr>
							<td>${category.name}</td>
							<td>${category.passed}</td>
							<td>${category.failed}</td>
							<td>${category.others}</td>
						</tr>
						</#list>
					</table>
				</div>
			</div>
			</#if>
		</div>
	</div>
</div>