<#assign timeStampFormat = config.getValue('timeStampFormat')>
<#assign cdn = config.getValue('cdn')>

<head>
	<meta charset='${ config.getValue('encoding') }' /> 
	<meta name='description' content='' />
	<meta name='robots' content='noodp, noydir' />
	<meta name='viewport' content='width=device-width, initial-scale=1' />
	<meta id="timeStampFormat">${timeStampFormat}</meta>
	
	<link href='${ config.getValue('protocol') }://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600' rel='stylesheet' type='text/css'>
	<link href="${ config.getValue('protocol') }://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

	<#if cdn == 'github'>
		<link href='${ config.getValue('protocol') }://cdn.rawgit.com/anshooarora/extentreports-java/9fa70d0ed9c34a8ed445ceee3494d3d7de7f8918/dist/css/extent.css' type='text/css' rel='stylesheet' />
	<#elseif cdn == 'extentreports'>
		<link href='http://extentreports.com/resx/dist/css/extent.css' type='text/css' rel='stylesheet' />
	<#else>
		<link href='${ config.getValue('protocol') }://cdn.rawgit.com/anshooarora/extentreports-java/9fa70d0ed9c34a8ed445ceee3494d3d7de7f8918/dist/css/extent.css' type='text/css' rel='stylesheet' />
	</#if>
	
	<title>${ config.getValue('documentTitle') }</title>

	<#if config.containsKey('css')>
	<style type='text/css'>
		${ config.getValue('css') }
	</style>
	</#if>
</head>
