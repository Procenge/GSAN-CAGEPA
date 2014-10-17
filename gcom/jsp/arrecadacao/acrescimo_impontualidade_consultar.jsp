<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.arrecadacao.AcrescimoImpontualidadeHelper"%><html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">


<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ConsultarAcrescimoImpontualidadeActionForm" />


<SCRIPT LANGUAGE="JavaScript">
<!--

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovel.value = codigoRegistro;
      form.submit();
    }
 }

function limparForm(){
	var form = document.forms[0];
	window.location.search='?peloMenu=true&menu=sim';
	window.location.replace;
}
 
//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarAcrescimoImpontualidadeAction" method="post">

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

			<td width="625" valign="top" class="centercoltext">

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consultar Acrescimo por Impontualidade</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para consultar o(s) acréscimo(s) por impontualidade, informe os dados abaixo:</td>
					<td align="right"></td>					
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td height="10" width="160"><strong>Matrícula do Imóvel:<font
						color="#FF0000">*</font></strong></td>
					<td width="403"><html:text property="idImovel" maxlength="9"
						size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirConsultarAcrescimoImpontualidadeAction.do', 'idImovel', 'Matrícula do Imóvel')" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<logic:present name="corInscricao">
	
						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="corInscricao">
		
						<logic:empty name="ConsultarAcrescimoImpontualidadeActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="ConsultarAcrescimoImpontualidadeActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
						
		
					</logic:notPresent>
					
					<a href="javascript:limparForm();" tabindex="1"> 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>
					<td width="43">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">

					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr>
							<td><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
								<tr>
									<td height="10"><strong>Situação de Água:</strong></td>
									<td><html:text property="situacaoAguaImovel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Situação de Esgoto:</strong></td>
									<td><html:text property="situacaoEsgotoImovel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
							</table>

							</td>
						</tr>
					</table>

					</td>
				</tr>
				<tr>
				<td colspan="3">
				<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
									<table width="100%" border="0" bgcolor="#90c7fc">
										<tr>
											<td bgcolor="#79bbfd" colspan="9" height="20">
												<strong>Acréscimos Impontualidade</strong>
											</td>
										</tr>
										<tr bgcolor="#90c7fc">

											<td width="10%">
											<div align="center"><strong>Refe.</strong></div>
											</td>
											<td width="13%">
											<div align="center"><strong>Venc.</strong></div>
											</td>
											<td width="10%">
											<div align="center"><strong>Valor</strong></div>
											</td>
											<td width="10%">
											<div align="center"><strong>Valor Base</strong></div>
											</td>
											<td width="10%">
											<div align="center"><strong>Multa</strong></div>
											</td>
											<td width="10%">
											<div align="center"><strong>Qtd. Dias Cobr.</strong></div>
											</td>
											<td width="13%">
											<div align="center"><strong>Data Pagto</strong></div>
											</td>
											<td width="13%">
											<div align="center"><strong>Refe. Cobr.</strong></div>
											</td>
										</tr>
									</table>

									</td>
								</tr>

								<logic:present name="colecaAcrescimoImpontualidade">

									<tr>
										<td>
										
										<% String cor = "#cbe5fe";%>

										<div style="width: 100%; height: 100; overflow: auto;">
										
										<table width="100%" align="center" bgcolor="#90c7fc">
										
										<logic:iterate name="colecaAcrescimoImpontualidade" id="acrescImpont" type="AcrescimoImpontualidadeHelper">

								
												<%	if (cor.equalsIgnoreCase("#cbe5fe")){
													cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF">
												<%} else{
													cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe">
												<%}%>
													<td width="10%" align="center">
													
													<bean:write name="acrescImpont" property="referencia" />
													</td>
													<td width="13%" align="center">
													<bean:write name="acrescImpont" property="dataVencimento" />
													</td>
													<td width="10%" align="center">
													<bean:write name="acrescImpont" property="valorConta" />
													</td>
													<td width="10%" align="center">
													<bean:write name="acrescImpont" property="valorBase" />
													</td>
													<td width="10%" align="center">
													<bean:write name="acrescImpont" property="multa" />
													</td>
													<td width="10%" align="center">
													<bean:write name="acrescImpont" property="qtdDiasCobrado" />
													</td>
													<td width="13%" align="center">
													<bean:write name="acrescImpont" property="dataPagamento" />
													</td>
													<td width="13%" align="center">
													<bean:write name="acrescImpont" property="referenciaCobranca" />
													</td>
												</tr>
											

										</logic:iterate>
																				
										</table>
										
										</div>
										</td>
									</tr>

								</logic:present>
								<logic:present name="consultaImpontualidade" scope="request">
									<span style="color:#ff0000" id="msgConsultaAcrescimo">
									<bean:write scope="request" name="msgConsultaAcrescimo" /></span>
								</logic:present>
								
							</table>
							</td>
						</tr>

					</table>
					</td>
				</tr>
				
				<tr>
					<td valign="top">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
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
