<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function confirmar(){
		var form = document.forms[0];

		var dsParecerParte1 = form.dsCampoParecer.value.substring(0,50);
		var dsParecerParte2 = form.dsCampoParecer.value.substring(50,100);
		var dsParecerParte3 = form.dsCampoParecer.value.substring(100,150);
		var dsParecerParte4 = form.dsCampoParecer.value.substring(150,200);
		var dsCampoParecer = dsParecerParte1 + "\n" + dsParecerParte2 + "\n" + dsParecerParte3 + "\n" + dsParecerParte4;
			
		enviarDados(form.idCampoParecer.value, dsCampoParecer, 'parecer');
		window.close();
	}

  	function textAreaMaxLength(maxlength){
		var form = document.forms[0];
		if(form.dsCampoParecer.value.length >= maxlength){
			window.event.keyCode = '';
		}
	}
  
	function recuperaParecer(idCampoParecer, dsCampoParecer){
		var form = document.forms[0];

		form.idCampoParecer.value = idCampoParecer;
		form.dsCampoParecer.value = dsCampoParecer;
	}
	
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(630, 500);javascript:recuperaParecer('${requestScope.idCampo}', '${requestScope.dsCampo}');">

<html:form action="/exibirInformarDescricaoParecerPopupAction" 
	name="InformarDescricaoParecerPopupActionForm"
	type="gcom.gui.cobranca.InformarDescricaoParecerPopupActionForm"
	method="post">

	<html:hidden property="idCampoParecer"/>
	
	<table width="600" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="615" valign="top" class="centercoltext">

				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>
	
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
						</td>
						<td class="parabg">Informar Descrição do Parecer</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>

				<table width="100%" border="0">
				<tr>
					<td align="right"></td>
				</tr>
				</table>

				<!--Inicio da Tabela Dados Gerais do RA - Registro de Atendimento -->
            	<table width="100%" border="0">

            	<tr>
                	<td height="31">
                    <table width="100%" border="0" align="center">

                        <tr bgcolor="#cbe5fe">

                      		<td align="center">
                      		<table width="100%" border="0" bgcolor="#99CCFF">

					    		<tr bgcolor="#99CCFF">
                         			<td height="18" colspan="2">
                         				<div align="center">
                         					<span class="style2"><b>Informe a descrição do Parecer </b></span>
                         				</div>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
										<table border="0" width="100%">
		                              		<tr> 
		                                		<td class="style3"><strong>Descri&ccedil;&atilde;o 
		                                  		do Parecer:</strong></td>
		
												<td colspan="3">
													<html:textarea
														property="dsCampoParecer" cols="50" rows="4"
														onkeypress="javascript:textAreaMaxLength(200);" 
														tabindex="6"/>
												</td>
	  									</table>
										<table border="0" width="100%"> 			
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td colspan="3" align="right"><input name="Button" type="button"
													class="bottonRightCol" value="Confirmar"
													onClick="javascript:confirmar();"></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</td>
               </tr>
			</table>
			</td>
 	  	</tr>
 	</table>
</html:form>
</body>
</html:html>