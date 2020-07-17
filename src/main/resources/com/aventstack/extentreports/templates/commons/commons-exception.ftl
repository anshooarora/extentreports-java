<#include "commons-macros.ftl">

<div class="fade aside aside-sm b-r" id="content-aside">
	<div class="modal-dialog d-flex flex-column h-100">
		<div class="navbar box-shadow">
			<div class="input-group flex mr-1">
              	<input id="qt" type="text" class="form-control px-0 no-bg no-border no-shadow search" placeholder="Search..." required="">
              	<span class="input-group-btn">
                	<button class="btn no-bg no-border no-shadow" type="button"><i class="fa fa-search text-muted text-xs"></i></button>
              	</span>
            </div>
		</div>
		<div class="scrollable hover">
			<div class="list inset">
				<div class="p-2 px-3 text-muted text-sm">Tags</div>
				<#list exceptionContext as exception>
					<div class="list-item attr">
						<div class="list-body">
							<a href="#" class="item-title _500">${exception.exceptionInfo.exceptionName}</a>
							<div class="item-except text-sm text-muted h-1x">
								${exception.tests?size} tests
							</div>
							<div class="tc d-none">
								<h5 class="mb-4">${exception.exceptionInfo.exceptionName}</h5>
								<table class="table">
									<thead><tr>
										<th id="status-col"></th>
										<th>Test</th>
										<th>Duration</th>
										<th>Attributes</th>
										<th>Media</th>
										<th>Source</th>
									</tr></thead>
									<tbody>
										<#list exception.tests as test>
										<@row test=test level=test.level />
										</#list>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</#list>
			</div>
		</div>
	</div>
</div>