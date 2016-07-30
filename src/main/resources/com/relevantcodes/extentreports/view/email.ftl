<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Extent</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600' rel='stylesheet' type='text/css'>
        
        <style type="text/css">
            html {
                width: 100%; 
            }
            body {
                width: 100%;  
                margin:0; 
                padding:0; 
                -webkit-font-smoothing: antialiased; 
                mso-padding-alt: 0px 0px 0px 0px;
                background: #ffffff;
            }
            p,h1,h2,h3,h4 {
                margin-top:0;
                margin-bottom:0;
                padding-top:0;
                padding-bottom:0;
            }
            table {
                font-size: 14px;
                border: 0;
            }
            img {
                border: none!important;
            }
        </style>

    </head>

    <body style="margin: 0; padding: 0;">
		
		<!-- spacer -->
        <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#f9f9f9" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
            <tbody>
                <tr>
                    <td width="100%" height="5" style="background:#1565c0;"></td>
                </tr>
            </tbody>
        </table>
        <!-- spacer -->
        
        <!-- heading -->
        <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#f9f9f9" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
            <tbody>
                <tr>
                    <td>
                        <table width="600" cellspacing="0" cellpadding="0" border="0" align="center" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
                            <tbody>
                                <tr>
                                    <td width="100%" height="50"></td>
                                </tr>
                                <tr>
                                    <!--  testimonial  -->
                                    <td align="center" style="color: #8a8a8a; font-family: 'Source Sans Pro', arial; font-size: 18px; line-height:28px;">
                                        Automated Test Execution Results
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" style="color: #8a8a8a; font-family: 'Source Sans Pro', arial; font-size: 12px; line-height:28px;">
                                        Jun 18, 2016 9:19:08 PM
                                    </td>
                                </tr>
                                <tr>
                                    <td width="100%" height="50"></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- heading -->

        <!-- test list -->
        <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#fff" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
            <tbody>
                <tr>
                    <td>
                        <table width="600" cellspacing="0" cellpadding="0" border="0" align="center" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
                            <tbody>
                                <tr>
                                    <td width="100%" style="font-family:'Source Sans Pro'">
                                    	<#list report.testList as test>
                                    	<table width="600" height="40" cellspacing="0" cellpadding="10" border="0" align="center" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;border:1px solid #ccc;padding:0 10px;">
                                    		<tbody>
                                    			<tr>
                                    				<td width="80%">
                                    					<span style="font-size: 15px;">
															${ test.name }
														</span>
                                    				</td>
                                    				<#if !test.hasChildren()>
				                                    <td width="20%">
				                                    	<#assign bgcolor = '#00c853'>
				                                    	<#if test.status == 'fail'>
				                                    		<#assign bgcolor = '#ef5350'>
				                                    	</#if>
				                                        <span padding="2" style="border:none;padding:4px 7px;border-radius:4px;background-color:${ bgcolor };color:#fff;font-family:'Source Sans Pro';font-size:11px;font-weight:600;">
				                                            ${ test.status?upper_case }
				                                        </span>
				                                    </td>
				                                    <#else>
				                                    <td></td>
				                                    </#if>
                                    			</tr>
                                    			<#if test.nodeContext?? && test.nodeContext.all?size != 0>
				                                <@recurse_nodes nodeList=test.nodeContext.all depth=1 />
												<#macro recurse_nodes nodeList depth>
												<#list nodeList as node>
												<tr>
													<td width="80%" height="0">
														<span style="font-size: 15px;">
															<#list 1..(node.level) as x>
															&nbsp;&nbsp;&nbsp;&nbsp;
															</#list>
															-&nbsp;&nbsp;
															${ node.name }
														</span>
													</td>
													<#if !node.hasChildren()>
													<td width="20%" height="0">
														<#assign bgcolor = '#00c853'>
														<#if node.status == 'fail'>
															<#assign bgcolor = '#ef5350'>
														</#if>
														<span padding="2" style="border:none;padding:4px 7px;border-radius:4px;background-color:${ bgcolor };color:#fff;font-family:'Source Sans Pro';font-size:11px;font-weight:600;">
															${ node.status?upper_case }
														</span>
													</td>
													<#else>
				                                    <td></td>
													</#if>
												</tr>
												<@recurse_nodes nodeList=node.nodeContext.all depth=depth+1 />
												</#list>
												</#macro>
											</#if>
                                    		</tbody>
                                      	</table>
                                      	&nbsp;
                                      	</#list>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- test list -->

        <!-- footer -->
        <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#f9f9f9" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
            <tbody>
                <tr>
                    <td>
                        <table width="600" cellspacing="0" cellpadding="0" border="0" align="center" style="border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
                            <tbody>
                                <tr>
                                    <td width="100%" height="50"></td>
                                </tr>
                                <tr>
                                    <td align="center" style="color: #8a8a8a; font-family: 'Source Sans Pro', arial; font-size: 12px; line-height:28px;">
                                        Built with love
                                    </td>
                                </tr>
                                <tr>
                                    <td width="100%" height="50"></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- footer -->

    </body>

</html>