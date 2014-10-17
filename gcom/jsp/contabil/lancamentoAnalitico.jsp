<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<html:javascript staticJavascript="false"  formName="ConsultarLancamentoSinteticoActionForm" dynamicJavascript="false"/>
<script language="JavaScript">

function voltar(){			
	var form = document.forms[0];
    form.action = "/gsan/exibirConsultarLancamentoSinteticoAction.do?menu=sim";
    form.submit();
}

</script>
</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/consultarLancamentoAnaliticoAction" method="post"
	onsubmit="return validateConsultarLancamentoSinteticoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center">
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>
			<td width="620" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Filtrar Lançamentos</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>			
	        
	        <table bordercolor="#000000" width="100%" cellspacing="0">
	        	<tr>
					<td><strong>Data Contábil:</strong></td>
					<td>${dataContabilFormatada}</td>
					
					<td><strong>Evento Comercial:</strong></td>
					<td>${descricaoEventoComercial}</td>					
				</tr>
				
				<tr>
					<td><strong>Conta Debito:</strong></td>
					<td>${contaDebito}</td>
					
					<td><strong>Categoria:</strong></td>
					<td>${descricaoCategoria}</td>					
				</tr>
				
				<tr>
					<td><strong>Conta Crédito:</strong></td>
					<td>${contaCredito}</td>
					
					<td><strong>Complemento:</strong></td>
					<td>${complemento}</td>					
				</tr>
				<tr>
					<td><strong>Módulo:</strong></td>
					<td>${descricaoModulo}</td>
					
					<td><strong>Valor:</strong></td>
					<td>${valorString}</td>					
				</tr>
			</table>
			<table>	
																																		
				<tr>
				<td colspan="3">

				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4" bgcolor="#000000" height="2"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" bgcolor="#99CCFF">
							<!--header da tabela interna -->
							<tr bgcolor="#99CCFF">
								<td width="250" align="center">
									<strong>Matricula Imovel</strong>
								</td>
								<td width="100" align="center">
									<strong>Objeto</strong>
								</td>
								<td width="100" align="center">
									<strong>Complemento</strong>
								</td>								
								<td width="100" align="center">
									<strong>Valor</strong>
								</td>								
							</tr>
						</table>
					</td>
				</tr>
				<br />
				<!-- --------------------------------------------------------------------------------- -->
				<tr>
					<td>
					
						<table width="100%" bgcolor="#99CCFF">										
							<logic:present name="lancamentos">
								<%int cont = 0;%>
								<logic:iterate name="lancamentos" id="lancamento">									
									<%cont = cont + 1;
									if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
									<%} else {%>
									<tr bgcolor="#FFFFFF">
									<%}%>	
										<td width="250" align="center">
											<bean:write name="lancamento" property="idImovel" />
											<bean:write name="lancamento" property="idImovelHistorico" />
										</td>																			
										<td width="100" align="center">
											<bean:write name="lancamento" property="descricaoObjeto" />
										</td>
										<td width="100" align="center">
											<bean:write name="lancamento" property="complemento" />
										</td>
										<td width="100" align="center">
											<bean:write name="lancamento" property="valorString" />																					
										</td>																																									
									</tr>									
								</logic:iterate>
							</logic:present>						
						</table>												
					</td>
				</tr>
				
				<!-- ---------------------------------------------------------------------------------- -->
				  
				
			</table>
			

			</td>
			</tr>
			
			<tr>
				<td colspan="3">
					<table width="100%" border="0">
						<tr>								
							<td align="right">
								<input type="button" name="ButtonVoltar" class="bottonRightCol"
									value="Voltar" onclick="javascript:history.back();">
							</td>
						</tr>	
					</table>
				</td>
			</tr>	
			</table>
			
			<p>&nbsp;</p>
		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>