<head>
	<meta charset='UTF-8' /> 
	<meta name='description' content='' />
	<meta name='robots' content='noodp, noydir' />
	<meta name='viewport' content='width=device-width, initial-scale=1' />
	<meta name='extentx' id='extentx' content='5750e943cca23e0088f56290' />

	<link href='${ config.getValue('protocol') }://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600' rel='stylesheet' type='text/css'>
	<link href="${ config.getValue('protocol') }://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href='${ config.getValue('protocol') }://cdn.rawgit.com/anshooarora/extentreports-java/master/dist/css/extent.css' type='text/css' rel='stylesheet' />

	<title>${ config.getValue('documentTitle') }</title>
	
	<#if config.containsKey('css')>
	<style type='text/css'>
		${ config.getValue('css') }
	</style>
	</#if>
</head>