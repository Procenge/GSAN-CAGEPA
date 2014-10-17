<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.faturamento.debito.DebitoACobrar"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper"%>
<%@ page import="gcom.cobranca.bean.ContaValoresHelper"
	isELIgnored="false"%>
<%@ page import="gcom.cadastro.cliente.bean.ClienteImovelRelacaoHelper"
	isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@ page import="gcom.faturamento.conta.Conta"%>
<%@ page import="java.util.Locale"%>

<% session.setAttribute("currentLocale", new Locale("pt", "BR")); %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	
<html:javascript staticJavascript="false"
	formName="ConsultarDebitoClienteActionForm" dynamicJavascript="true" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">
<!--
function voltar(){
    var form = document.forms[0];
  	form.action = 'exibirConsultarDebitoAction.do?voltar=sim';
    form.submit();		
}

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
	}
}
 
 function imprimirExtratoDebitosContas(){
	 
	var form = document.forms[0];
	
	var radios = form.indicadorIncluirAcrescimosImpontualidade;
	var valor = 0;
	for (var i = 0; i < radios.length; i++) {       
        if (radios[i].checked) {
        	valor = radios[i].value;
            break;
        }
    }
	
	window.location.href='/gsan/gerarRelatorioExtratoDebitoClienteAction.do?tipo=conta&acresImp='+valor;
			
 }
		
		
//-->
</script>
</head>

<logic:present name="ehPopup">
	<body leftmargin="5" topmargin="5"
		onload="resizePageSemLink(630, 500);">
</logic:present>

<logic:notPresent name="ehPopup">
	<body leftmargin="5" topmargin="5">
</logic:notPresent>


<html:form action="/exibirConsultarDebitoClienteAction"
	name="ConsultarDebitoClienteActionForm" method="post"
	type="gcom.gui.cobranca.ConsultarDebitoClienteActionForm"
	method="post">

	<logic:notPresent name="ehPopup">
	
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
					<td class="parabg">Consultar Débitos do Cliente</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			</logic:notPresent> 
			<logic:present name="ehPopup">
					<table width="600" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="600" valign="top" class="centercoltext">

							<table height="100%">
								<tr>
									<td></td>
								</tr>
							</table>

							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
									<td class="parabg">Consultar D&eacute;bitos do Cliente</td>
									<td width="11" valign="top"><img border="0"
										src="imagens/parahead_right.gif" /></td>
								</tr>
							</table>

							</logic:present> <!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr>
							<td colspan="2" align="center"><strong>Dados do Cliente</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td align="left">
							<table border="0">
								<tr>
									<td width="50%" align="right">
									<div align="left" class="style9"><strong>C&oacute;digo do
									Cliente:</strong></div>
									</td>
									<td width="50%" align="left">
									<logic:present name="ConsultarDebitoClienteActionForm" property="codigoCliente">
									<html:text
										name="ConsultarDebitoClienteActionForm"
										property="codigoCliente" size="9" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:present>
									<logic:notPresent name="ConsultarDebitoClienteActionForm" property="codigoCliente">
										<html:text
										name="ConsultarDebitoClienteActionForm"
										property="codigoClienteSuperior" size="9" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:notPresent>
									
										</td>
										
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Tipo de Cliente:</strong>
									</div>
									</td>
									<td align="left" width="50%"><html:text
										name="ConsultarDebitoClienteActionForm" property="tipoRelacao"
										size="30" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" /></td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Per&iacute;odo de
									Referência do D&eacute;bito:</strong></div>
									</td>
									<td align="left" width="50%">
									<div align="left"><html:text
										name="ConsultarDebitoClienteActionForm"
										property="referenciaInicial" size="7" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									<strong> a</strong> <html:text
										name="ConsultarDebitoClienteActionForm"
										property="referenciaFinal" size="7" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</div>
									</td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Per&iacute;odo de
									Vencimento do D&eacute;bito:</strong></div>
									</td>
									<td align="left" width="50%">
									<div align="left"><html:text
										name="ConsultarDebitoClienteActionForm"
										property="dataVencimentoInicial" size="10" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									<strong> a</strong> <html:text
										name="ConsultarDebitoClienteActionForm"
										property="dataVencimentoFinal" size="10" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</div>
									</td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Nome do Cliente:</strong>
									</div>
									</td>
									<td align="left" width="50%">
									<logic:present name="ConsultarDebitoClienteActionForm" property="nomeCliente">
									<html:text
										name="ConsultarDebitoClienteActionForm" property="nomeCliente"
										size="50" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:present>
									<logic:notPresent name="ConsultarDebitoClienteActionForm" property="nomeCliente">
										<html:text
										name="ConsultarDebitoClienteActionForm" property="nomeClienteSuperior"
										size="50" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:notPresent>
									</td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>CPF/CNPJ:</strong></div>
									</td>
									<td align="left" width="50%"><logic:notEqual
										name="ConsultarDebitoClienteActionForm" property="cpfCnpj"
										value="null">
										<html:text name="ConsultarDebitoClienteActionForm"
											property="cpfCnpj" size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:notEqual> <logic:equal
										name="ConsultarDebitoClienteActionForm" property="cpfCnpj"
										value="null">
										<input name="ConsultarDebitoClienteActionForm" value=""
											size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />
									</logic:equal></td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Profiss&atilde;o:</strong>
									</div>
									</td>
									<td align="left" width="50%"><logic:present
										name="ConsultarDebitoClienteActionForm" property="profissao">
										<html:text name="ConsultarDebitoClienteActionForm"
											property="profissao" size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />&nbsp;
												</logic:present> <logic:notPresent
										name="ConsultarDebitoClienteActionForm" property="profissao">
										<input name="ConsultarDebitoClienteActionForm" value=""
											size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />&nbsp;
												</logic:notPresent></td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Ramo de Atividade:</strong>
									</div>
									</td>
									<td align="left" width="50%"><logic:present
										name="ConsultarDebitoClienteActionForm"
										property="ramoAtividade">
										<html:text name="ConsultarDebitoClienteActionForm"
											property="ramoAtividade" size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />&nbsp;
												</logic:present> <logic:notPresent
										name="ConsultarDebitoClienteActionForm"
										property="ramoAtividade">
										<input name="ConsultarDebitoClienteActionForm" value=""
											size="25" readonly="true"
											style="background-color:#EFEFEF; border:0; font-size:9px" />&nbsp;
												</logic:notPresent></td>
								</tr>
								<tr>
									<td width="50%">
									<div align="left" class="style9"><strong>Telefone para Contato:</strong>
									</div>
									</td>
									<td width="50%"><html:text
										name="ConsultarDebitoClienteActionForm" property="clienteFone"
										size="15" readonly="true"
										style="background-color:#EFEFEF; border:0; font-size:9px" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr>
							<td align="center">
							<div align="center" class="style9"><strong>Endere&ccedil;o </strong>
							</div>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td align="center">
							<div align="center" class="style9"><bean:write
								name="ConsultarDebitoClienteActionForm"
								property="enderecoCliente" /> &nbsp;</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<!-- Relacionamento do Cliente com os Imóveis -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosRelacionamentoClienteImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosRelacionamentoClienteImovel',true);" />
								<b>Relacionamento do Cliente com os Imóveis</b></a></span>
							</td>
						</tr>
					</table>
					</div>
					<div id="layerShowDadosRelacionamentoClienteImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosRelacionamentoClienteImovel',false);" />
								<b>Relacionamento do Cliente com os Imóveis</b></a></span>
							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="90c7fc">
									<td bgcolor="90c7fc" align="center"><strong>Imóvel</strong></td>
									<td align="center" bgcolor="90c7fc"><strong>Data de Relação Inicial</strong></td>
									<td align="center" bgcolor="90c7fc"><strong>Data de Relação Final</strong></td>
									<td align="center" bgcolor="90c7fc"><strong>Tipo de Relação</strong></td>
									<td align="center" bgcolor="90c7fc"><strong>Motivo do Término da Relação</strong></td>
								</tr>
								<%String cor1 = "#cbe5fe";%>	
								<logic:present name="colecaoClienteImovelRelacaoHelper">
									<logic:iterate name="colecaoClienteImovelRelacaoHelper" id="clienteImovelRelacaoHelper" type="ClienteImovelRelacaoHelper">
								
									<%if (cor1.equalsIgnoreCase("#cbe5fe")) {
											cor1 = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
											<%} else {
											cor1 = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<div align="center">
										<td align="left"><bean:write name="clienteImovelRelacaoHelper" property="idImovel"/></td>
										</div>
										<td align="center"><bean:write name="clienteImovelRelacaoHelper" property="dataInicioRelacao"/></td>
										<td align="center"><bean:write name="clienteImovelRelacaoHelper" property="dataFimRelacao"/></td>
										<td align="left"><bean:write name="clienteImovelRelacaoHelper" property="descricaoClienteRelacaoTipo"/></td>
										<td align="left"><bean:write name="clienteImovelRelacaoHelper" property="descricaoClienteImovelFimRelacaoMotivo"/></td>
									</tr>
									</logic:iterate>
								</logic:present>
							</table>

							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="5">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<%String cor = "#cbe5fe";%>
						<tr bordercolor="#90c7fc">
							<td colspan="10" bgcolor="#79bbfd" align="center"><strong>Contas</strong></td>
						</tr>
						<logic:notEmpty name="colecaoContaValores">
							<%if (((Collection) session.getAttribute("colecaoContaValores"))
					.size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS_DEBITO) {%>
							<tr bordercolor="#000000">
								<td width="10%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Matrícula</strong>
								</font></div>
								</td>
								<td width="15%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano</strong>
								</font></div>
								</td>
								<td width="12%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
								</font></div>
								</td>
								<td width="10%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
								&Aacute;gua </strong> </font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
								Esgoto</strong> </font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								D&eacute;bitos</strong> </font></div>
								</td>
								<td width="5%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								Creditos</strong> </font></div>
								</td>

								<td width="5%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								Impostos</strong> </font></div>
								</td>



								<td width="10%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da
								Conta</strong> </font></div>
								</td>
								<td width="9%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Acr&eacute;sc.
								Impont.</strong><strong></strong> </font></div>
								</td>
								<td width="10%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Sit.</strong>
								</font></div>
								</td>
							</tr>
							<logic:present name="colecaoContaValores">
								<logic:iterate name="colecaoContaValores"
									id="contavaloreshelper">
									<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
										<%} else {
					cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
										<%}%>
										<td>
										<div class="style9" align="center"><bean:define
											name="contavaloreshelper" property="conta" id="conta" /> <font
											color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
											name="contavaloreshelper" property="conta">
											<logic:equal name="conta" property="contaMotivoRevisao"
												value="">
												<bean:write name="conta" property="imovel.id" />
											</logic:equal>
											<logic:notEqual name="conta" property="contaMotivoRevisao"
												value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="imovel.id" /> </font>
											</logic:notEqual>
										</logic:notEmpty> </font></div>
										</td>
										<td align="left"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div class="style9" align="center"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <a
												href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
											<bean:define name="contavaloreshelper" property="conta"
												id="conta" /> <logic:equal name="conta"
												property="contaMotivoRevisao" value="">
												<bean:write name="conta" property="formatarAnoMesParaMesAno" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="formatarAnoMesParaMesAno" /> </font>
											</logic:notEqual> </a> </font></div>
										</logic:notEmpty></td>
										<td align="left"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="center"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:define
												name="contavaloreshelper" property="conta" id="conta" /> <logic:equal
												name="conta" property="contaMotivoRevisao" value="">
												<bean:write name="conta" property="dataVencimentoConta"
													formatKey="date.format" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="dataVencimentoConta" formatKey="date.format" />
												</font>
											</logic:notEqual> </font></div>
										</logic:notEmpty></td>
										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:define
												name="contavaloreshelper" property="conta" id="conta" /> <logic:equal
												name="conta" property="contaMotivoRevisao" value="">
												<bean:write name="conta" property="valorAgua"
													formatKey="money.format" locale="currentLocale" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="valorAgua" formatKey="money.format" locale="currentLocale" /> </font>
											</logic:notEqual> </font></div>
										</logic:notEmpty></td>
										<td align="rigth">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
											name="contavaloreshelper" property="conta">
											<bean:define name="contavaloreshelper" property="conta"
												id="conta" />
											<logic:equal name="conta" property="contaMotivoRevisao"
												value="">
												<bean:write name="conta" property="valorEsgoto"
													formatKey="money.format" locale="currentLocale" />
											</logic:equal>
											<logic:notEqual name="conta" property="contaMotivoRevisao"
												value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="valorEsgoto" formatKey="money.format" locale="currentLocale" /> </font>
											</logic:notEqual>
										</logic:notEmpty> </font></div>
										</td>
										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEqual
												name="contavaloreshelper" property="conta.debitos" value="0">
												<a
													href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="conta.debitos" formatKey="money.format" locale="currentLocale" />
												</logic:equal> <logic:notEqual name="conta"
													property="contaMotivoRevisao" value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper" property="conta.debitos"
														formatKey="money.format" locale="currentLocale" /> </font>
												</logic:notEqual> </a>
											</logic:notEqual> <logic:equal name="contavaloreshelper"
												property="conta.debitos" value="0">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="conta.debitos" formatKey="money.format" locale="currentLocale" />
												</logic:equal>
												<logic:notEqual name="conta" property="contaMotivoRevisao"
													value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper" property="conta.debitos"
														formatKey="money.format" locale="currentLocale" /> </font>
												</logic:notEqual>
											</logic:equal> </font></div>
										</logic:notEmpty></td>
										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEqual
												name="contavaloreshelper" property="conta.valorCreditos"
												value="0">
												<a
													href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="conta.valorCreditos" formatKey="money.format" locale="currentLocale" />
												</logic:equal> <logic:notEqual name="conta"
													property="contaMotivoRevisao" value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper" property="conta.valorCreditos"
														formatKey="money.format" locale="currentLocale" /> </font>
												</logic:notEqual> </a>
											</logic:notEqual> <logic:equal name="contavaloreshelper"
												property="conta.valorCreditos" value="0">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="conta.valorCreditos" formatKey="money.format" locale="currentLocale" />
												</logic:equal>
												<logic:notEqual name="conta" property="contaMotivoRevisao"
													value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper" property="conta.valorCreditos"
														formatKey="money.format" locale="currentLocale" /> </font>
												</logic:notEqual>
											</logic:equal> </font></div>
										</logic:notEmpty></td>




										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:define
												name="contavaloreshelper" property="conta" id="conta" /> <logic:equal
												name="conta" property="contaMotivoRevisao" value="">
												<bean:write name="conta" property="valorImposto"
													formatKey="money.format" locale="currentLocale" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="valorImposto" formatKey="money.format" locale="currentLocale" /> </font>
											</logic:notEqual> </font></div>
										</logic:notEmpty></td>





										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:define
												name="contavaloreshelper" property="conta" id="conta" /> <logic:equal
												name="conta" property="contaMotivoRevisao" value="">
												<bean:write name="conta" property="valorTotal"
													formatKey="money.format" locale="currentLocale" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write name="conta"
													property="valorTotal" formatKey="money.format" locale="currentLocale" /> </font>
											</logic:notEqual> </font></div>
										</logic:notEmpty></td>
										<td align="right"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="right" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEqual
												name="contavaloreshelper" property="valorTotalContaValores"
												value="0">
												<a
													href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?multa=<bean:write name="contavaloreshelper" property="valorMulta" />&juros=<bean:write name="contavaloreshelper" property="valorJurosMora" />&atualizacao=<bean:write name="contavaloreshelper" property="valorAtualizacaoMonetaria" />', 300, 650);">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="valorTotalContaValores" formatKey="money.format" locale="currentLocale" />
												</logic:equal> <logic:notEqual name="conta"
													property="contaMotivoRevisao" value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper"
														property="valorTotalContaValores" formatKey="money.format" locale="currentLocale" />
													</font>
												</logic:notEqual> </a>
											</logic:notEqual> <logic:equal name="contavaloreshelper"
												property="valorTotalContaValores" value="0">
												<logic:equal name="conta" property="contaMotivoRevisao"
													value="">
													<bean:write name="contavaloreshelper"
														property="valorTotalContaValores" formatKey="money.format" locale="currentLocale" />
												</logic:equal>
												<logic:notEqual name="conta" property="contaMotivoRevisao"
													value="">
													<font color="#CC0000"> <bean:write
														name="contavaloreshelper"
														property="valorTotalContaValores" formatKey="money.format" locale="currentLocale" />
													</font>
												</logic:notEqual>
											</logic:equal> </font></div>
										</logic:notEmpty></td>
										<td align="left"><logic:notEmpty name="contavaloreshelper"
											property="conta">
											<div align="left"><font color="#000000" style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:define
												name="contavaloreshelper" property="conta" id="conta" /> <bean:define
												name="conta" property="debitoCreditoSituacaoAtual"
												id="debitoCreditoSituacaoAtual" /> <logic:equal
												name="conta" property="contaMotivoRevisao" value="">
												<bean:write name="debitoCreditoSituacaoAtual"
													property="descricaoAbreviada" />
											</logic:equal> <logic:notEqual name="conta"
												property="contaMotivoRevisao" value="">
												<font color="#CC0000"> <bean:write
													name="debitoCreditoSituacaoAtual"
													property="descricaoAbreviada" /> </font>
											</logic:notEqual> </font></div>
										</logic:notEmpty></td>
									</tr>
								</logic:iterate>
								<logic:notEmpty name="colecaoContaValores">
									<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
										<%} else {
					cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
										<%}%>
										<td bgcolor="#90c7fc" align="left">
										<div class="style9" align="center"><font color="#000000"
											style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
										</font></div>
										</td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;</td>
										<td align="right">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorAgua")%>
										</font></div>
										</td>
										<td align="rigth">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorEsgoto")%>
										</font></div>
										</td>
										<td align="right">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorDebito")%>
										</font></div>
										</td>
										<td align="right">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorCredito")%>
										</font></div>
										</td>

										<td align="right">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorImposto")%>
										</font></div>
										</td>

										<td align="right">
										<div align="right"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorConta")%>
										</font></div>
										</td>
										<td align="right">
										<div align="right" class="style9"><font color="#000000"
											style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorAcrescimo")%>
										</font></div>
										</td>
										<td align="left">
										<div align="left"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
										</td>
									</tr>
								</logic:notEmpty>
							</logic:present>
							<%} else {%>
							<tr bordercolor="#000000">
								<td width="10%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Matrícula</strong>
								</font></div>
								</td>
								<td width="9%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano</strong>
								</font></div>
								</td>
								<td width="12%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
								</font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
								&Aacute;gua </strong> </font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
								Esgoto</strong> </font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								<br>
								D&eacute;bitos</strong> </font></div>
								</td>
								<td width="10%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								Creditos</strong> </font></div>
								</td>


								<td width="10%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
								Impostos</strong> </font></div>
								</td>



								<td width="8%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da
								Conta</strong> </font></div>
								</td>
								<td width="9%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Acr&eacute;sc.
								Impont.</strong><strong></strong> </font></div>
								</td>
								<td width="8%" bgcolor="#90c7fc">
								<div align="center" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Sit.</strong>
								</font></div>
								</td>
							</tr>
							<tr>
								<td height="100" colspan="11">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:present name="colecaoContaValores">
										<logic:iterate name="colecaoContaValores"
											id="contavaloreshelper">
											<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
												<%} else {
					cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
												<%}%>
												<td width="10%">
												<div align="left" class="style9"><bean:define
													name="contavaloreshelper" property="conta" id="conta" /> <font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="contavaloreshelper"
															property="conta.imovel.id" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write
															name="contavaloreshelper" property="conta.imovel.id" />
														</font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="9%" align="left">
												<div align="left" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<a
															href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);"><bean:write
															name="conta" property="formatarAnoMesParaMesAno" /></a>
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <a
															href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);"><bean:write
															name="conta" property="formatarAnoMesParaMesAno" /></a>
														</font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="12%" align="left">
												<div align="left" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="conta" property="dataVencimentoConta"
															formatKey="date.format" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write name="conta"
															property="dataVencimentoConta" formatKey="date.format" />
														</font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="8%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="conta" property="valorAgua"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write name="conta"
															property="valorAgua" formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="8%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="conta" property="valorEsgoto"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write name="conta"
															property="valorEsgoto" formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="8%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<logic:notEqual name="contavaloreshelper"
														property="conta.debitos" value="0">
														<a
															href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
														<logic:equal name="conta" property="contaMotivoRevisao"
															value="">
															<bean:write name="contavaloreshelper"
																property="conta.debitos" formatKey="money.format" locale="currentLocale" />
														</logic:equal> <logic:notEqual name="conta"
															property="contaMotivoRevisao" value="">
															<font color="#CC0000"> <bean:write
																name="contavaloreshelper" property="conta.debitos"
																formatKey="money.format" locale="currentLocale" /> </font>
														</logic:notEqual> </a>
													</logic:notEqual>
													<logic:equal name="contavaloreshelper"
														property="conta.debitos" value="0">
														<logic:equal name="conta" property="contaMotivoRevisao"
															value="">
															<bean:write name="contavaloreshelper"
																property="conta.debitos" formatKey="money.format" locale="currentLocale" />
														</logic:equal>
														<logic:notEqual name="conta" property="contaMotivoRevisao"
															value="">
															<font color="#CC0000"> <bean:write
																name="contavaloreshelper" property="conta.debitos"
																formatKey="money.format" locale="currentLocale" /> </font>
														</logic:notEqual>
													</logic:equal>
												</logic:notEmpty> </font></div>
												</td>
												<td width="10%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<logic:notEqual name="contavaloreshelper"
														property="conta.valorCreditos" value="0">
														<a
															href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
														<logic:equal name="conta" property="contaMotivoRevisao"
															value="">
															<bean:write name="contavaloreshelper"
																property="conta.valorCreditos" formatKey="money.format" locale="currentLocale" />
														</logic:equal> <logic:notEqual name="conta"
															property="contaMotivoRevisao" value="">
															<font color="#CC0000"> <bean:write
																name="contavaloreshelper" property="conta.valorCreditos"
																formatKey="money.format" locale="currentLocale" /> </font>
														</logic:notEqual> </a>
													</logic:notEqual>
													<logic:equal name="contavaloreshelper"
														property="conta.valorCreditos" value="0">
														<logic:equal name="conta" property="contaMotivoRevisao"
															value="">
															<bean:write name="contavaloreshelper"
																property="conta.valorCreditos" formatKey="money.format" locale="currentLocale" />
														</logic:equal>
														<logic:notEqual name="conta" property="contaMotivoRevisao"
															value="">
															<font color="#CC0000"> <bean:write
																name="contavaloreshelper" property="conta.valorCreditos"
																formatKey="money.format" locale="currentLocale" /> </font>
														</logic:notEqual>
													</logic:equal>
												</logic:notEmpty> </font></div>
												</td>


												<td width="10%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="conta" property="valorImposto"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write name="conta"
															property="valorImposto" formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>



												<td width="8%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="conta" property="valorTotal"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write name="conta"
															property="valorTotal" formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
												<td width="9%" align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEqual
													name="contavaloreshelper" property="valorTotalContaValores"
													value="0">
													<a
														href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?multa=<bean:write name="contavaloreshelper" property="valorMulta" />&juros=<bean:write name="contavaloreshelper" property="valorJurosMora" />&atualizacao=<bean:write name="contavaloreshelper" property="valorAtualizacaoMonetaria" />', 300, 650);">
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="contavaloreshelper"
															property="valorTotalContaValores"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal> <logic:notEqual name="conta"
														property="contaMotivoRevisao" value="">
														<font color="#CC0000"> <bean:write
															name="contavaloreshelper"
															property="valorTotalContaValores"
															formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual> </a>
												</logic:notEqual> <logic:equal name="contavaloreshelper"
													property="valorTotalContaValores" value="0">
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="contavaloreshelper"
															property="valorTotalContaValores"
															formatKey="money.format" locale="currentLocale" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write
															name="contavaloreshelper"
															property="valorTotalContaValores"
															formatKey="money.format" locale="currentLocale" /> </font>
													</logic:notEqual>
												</logic:equal> </font></div>
												</td>
												<td width="8%" align="left">
												<div align="left" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="contavaloreshelper" property="conta">
													<bean:define name="contavaloreshelper" property="conta"
														id="conta" />
													<bean:define name="conta"
														property="debitoCreditoSituacaoAtual"
														id="debitoCreditoSituacaoAtual" />
													<logic:equal name="conta" property="contaMotivoRevisao"
														value="">
														<bean:write name="debitoCreditoSituacaoAtual"
															property="descricaoAbreviada" />
													</logic:equal>
													<logic:notEqual name="conta" property="contaMotivoRevisao"
														value="">
														<font color="#CC0000"> <bean:write
															name="debitoCreditoSituacaoAtual"
															property="descricaoAbreviada" /> </font>
													</logic:notEqual>
												</logic:notEmpty> </font></div>
												</td>
											</tr>
										</logic:iterate>
										<logic:notEmpty name="colecaoContaValores">
											<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
												<%} else {
					cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
												<%}%>
												<td bgcolor="#90c7fc" align="center">
												<div class="style9" align="center"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
												</font></div>
												</td>
												<td align="left">&nbsp;</td>
												<td align="left">&nbsp;</td>
												<td align="right">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorAgua")%>
												</font></div>
												</td>
												<td align="rigth">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorEsgoto")%>
												</font></div>
												</td>
												<td align="right">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorDebito")%>
												</font></div>
												</td>
												<td align="right">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorCredito")%>
												</font></div>
												</td>



												<td align="right">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorImposto")%>
												</font></div>
												</td>





												<td align="right">
												<div align="right"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorConta")%>
												</font></div>
												</td>
												<td align="right">
												<div align="right" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorAcrescimo")%>
												</font></div>
												</td>
												<td align="left">
												<div align="left"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
												</td>
											</tr>
										</logic:notEmpty>
									</logic:present>
								</table>
								</div>
								</td>
							</tr>
							<%}

			%>
						</logic:notEmpty>
					</table>
					</td>
				</tr>
				
				<tr>
					<td align="center">
					<strong>Acréscimos Impontualidade:</strong>
					
					
					       <logic:equal name="ConsultarDebitoClienteActionForm" property="indicadorIncluirAcrescimosImpontualidade" value="2">
								<input type="radio"	name="indicadorIncluirAcrescimosImpontualidade" id="indicadorIncluirAcrescimosImpontualidade"  value="1" disabled="disabled" checked="checked" /> <strong>Incluir</strong>
								<input type="radio"	name="indicadorIncluirAcrescimosImpontualidade" id="indicadorIncluirAcrescimosImpontualidade"  value="2" disabled="disabled" /> <strong>Não Incluir</strong>
							</logic:equal>
							
							<logic:notEqual name="ConsultarDebitoClienteActionForm" property="indicadorIncluirAcrescimosImpontualidade" value="2">
								<input type="radio"	name="indicadorIncluirAcrescimosImpontualidade" id="indicadorIncluirAcrescimosImpontualidade" value="1" checked="checked" /> <strong>Incluir</strong>
								<input type="radio"	name="indicadorIncluirAcrescimosImpontualidade" id="indicadorIncluirAcrescimosImpontualidade"  value="2" /> <strong>Não Incluir</strong>
							</logic:notEqual>	
						
					</td>
				</tr>
					
				<tr>
					<logic:empty name="colecaoContaValores">
						<td colspan="4" align="right"><input type="button" name=""
							value="Imprimir Extrato de Débito Conta(s)"
							class="bottonRightCol" disabled="true" /></td>
					</logic:empty>
					<logic:notEmpty name="colecaoContaValores">
						<td colspan="4" align="right">
							<input type="button" name="" value="Imprimir Extrato de Débito Conta(s)"
								class="bottonRightCol"
								onclick="javaScript:imprimirExtratoDebitosContas()" />
					
						</td>
					</logic:notEmpty>
				</tr>
				
				
				
				
				
				
				
				
				
				<tr>
					<td colspan="6">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#000000">
							<td colspan="6" align="center" bgcolor="#79bbfd"><strong>D&eacute;bitos
							A Cobrar</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Matrícula</strong>
							</font></div>
							</td>
							<td width="43%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do
							D&eacute;bito</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Refer&ecirc;ncia</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Cobran&ccedil;a</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Parcelas a
							cobrar</strong> </font></div>
							</td>
							<td width="17%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor a
							cobrar</strong> </font></div>
							</td>
						</tr>
						<%cor = "#cbe5fe";%>
						<tr>
							<td height="100" colspan="10">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%">
								<logic:present name="colecaoDebitoACobrar">
									<logic:iterate name="colecaoDebitoACobrar" id="debitoacobrar">
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
											<%} else {
				cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="10%">
											<div align="left" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="debitoacobrar" property="imovel.id" /> </font></div>
											</td>
											<td width="43%">
											<div align="left" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
												name="debitoacobrar" property="imovel">
												<a
													href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:define name="debitoacobrar" property="imovel" id="imovel" /><bean:write name="imovel" property="id" />&debitoID=<bean:write name="debitoacobrar" property="id" />&debitosAgrupados=<c:out value="${debitosAgrupados}"/>', 570, 720);">
												<bean:define name="debitoacobrar" property="debitoTipo"
													id="debitoTipo" /> <logic:notEmpty name="debitoTipo"
													property="descricao">
													<bean:write name="debitoTipo" property="descricao" />
												</logic:notEmpty> </a>
											</logic:notEmpty> </font></div>
											</td>
											<td width="10%">
											<div align="center" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
												name="debitoacobrar" property="anoMesReferenciaDebito">
												<%=Util
									.formatarAnoMesParaMesAno(((DebitoACobrar) debitoacobrar)
											.getAnoMesReferenciaDebito()
											.toString())%>
											</logic:notEmpty> </font></div>
											&nbsp;</td>
											<td width="10%">
											<div align="center" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
												name="debitoacobrar" property="anoMesCobrancaDebito">
												<logic:notEqual name="debitoacobrar"
													property="anoMesCobrancaDebito" value="0">
													<bean:write name="debitoacobrar"
														property="formatarAnoMesCobrancaDebito" />
												</logic:notEqual>
											</logic:present>&nbsp; </font></div>
											</td>
											<td width="10%">
											<div align="center" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
												name="debitoacobrar" property="parcelasRestante">
												<bean:write name="debitoacobrar" property="parcelasRestante" />
											</logic:notEmpty> </font></div>
											</td>
											<td width="17%">
											<div align="right" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
												name="debitoacobrar" property="valorTotal">
												<bean:write name="debitoacobrar" property="valorTotal"
													formatKey="money.format" locale="currentLocale" />
											</logic:notEmpty> </font></div>
											</td>
										</tr>
									</logic:iterate>
									<logic:notEmpty name="colecaoDebitoACobrar">
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
											<%} else {
				cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td bgcolor="#90c7fc">
											<div align="center" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
											</font></div>
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>
											<div align="right" class="style9"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorDebitoACobrar")%>
											</font></div>
											</td>
										</tr>
									</logic:notEmpty>
								</logic:present>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="6" bgcolor="#79bbfd" align="center"><strong>Cr&eacute;ditos
							A Realizar</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="10%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Matrícula</strong>
							</font></div>
							</td>
							<td width="43%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do
							Cr&eacute;dito</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Refer&ecirc;ncia</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Cobran&ccedil;a</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Parcelas a
							creditar</strong> </font></div>
							</td>
							<td width="17%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor a
							creditar</strong> </font></div>
							</td>
						</tr>
						<%cor = "#cbe5fe";%>
						<logic:present name="colecaoCreditoARealizar">
							<logic:iterate name="colecaoCreditoARealizar"
								id="creditoarealizar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td>
									<div align="left" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
										name="creditoarealizar" property="imovel.id" /> </font></div>
									</td>
									<td><logic:notEmpty name="creditoarealizar"
										property="creditoTipo">
										<div align="left" class="style9"><font color="#000000"
											style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <a
											href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<bean:define name="creditoarealizar" property="imovel" id="imovel" /><bean:write name="imovel" property="id" />&creditoID=<bean:write name="creditoarealizar" property="id" />', 570, 720);">
										<bean:define name="creditoarealizar" property="creditoTipo"
											id="creditoTipo" /> <logic:notEmpty name="creditoTipo"
											property="descricao">
											<bean:write name="creditoTipo" property="descricao" />
										</logic:notEmpty> </a> </font></div>
									</logic:notEmpty></td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="anoMesReferenciaCredito">
										<bean:write name="creditoarealizar"
											property="formatarAnoMesReferenciaCredito" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="anoMesCobrancaCredito">
										<bean:write name="creditoarealizar"
											property="formatarAnoMesCobrancaCredito" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="parcelasRestante">
										<bean:write name="creditoarealizar"
											property="parcelasRestante" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="right" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="valorTotal">
										<bean:write name="creditoarealizar" property="valorTotal"
											formatKey="money.format" locale="currentLocale" />
									</logic:notEmpty> </font></div>
									</td>
								</tr>
							</logic:iterate>
							<logic:notEmpty name="colecaoCreditoARealizar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
									</font></div>
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>
									<div align="right" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorCreditoARealizar")%>
									</font></div>
									</td>
								</tr>
							</logic:notEmpty>
						</logic:present>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc"
								border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="5" bgcolor="#79bbfd" align="center"><strong>Guias de Pagamento</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="15%" bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número da Guia</strong> </font></div>
									</td>
									<td width="15%" bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número Parcela</strong> </font></div>
									</td>
									<td width="22%" bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data de Emiss&atilde;o</strong> </font></div>
									</td>
									<td width="22%" bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data de Vencimento</strong> </font></div>
									</td>
									<td width="26%" bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da Parcela</strong> </font></div>
									</td>
								</tr>
								<%cor = "#cbe5fe";%>
								<logic:present name="colecaoGuiaPagamentoValores">
									<logic:iterate name="colecaoGuiaPagamentoValores"
										id="guiapagamentohelper">
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
											cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
										<%} else {
											cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
										<%}%>
											<td>
												<div align="left" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<logic:notPresent name="ehPopup">
															 <a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<bean:write name="guiapagamentohelper" property="idGuiaPagamento" />', 600, 900);">
														</logic:notPresent>
														<logic:present name="ehPopup">
															<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?caminhoRetornoTelaConsultaGuiaPagamento=exibirConsultarContaAction&guiaPagamentoId=<bean:write name="guiapagamentohelper" property="idGuiaPagamento" />', 600, 900);">
														</logic:present>
															<bean:write name="guiapagamentohelper" property="idGuiaPagamento" />																											
												 	</font>
												</div>
											</td>
											<td>
												<div align="center" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="guiapagamentohelper" property="numeroPrestacao" />
													</font>
												</div>
											</td>
											<td>
												<div align="center" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="guiapagamentohelper" property="dataEmissao" formatKey="date.format" />  
													</font>
												</div>
											</td>
											<td>
												<div align="center" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="guiapagamentohelper" property="dataVencimento" formatKey="date.format" />  
													</font>
												</div>
											</td>
											<td>
												<div align="right" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="guiapagamentohelper" property="valorTotalPrestacao" formatKey="money.format" locale="currentLocale" />
													</font>
												</div>
											</td>
										</tr>
									</logic:iterate>
									<logic:notEmpty name="colecaoGuiaPagamentoValores">
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
											cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
										<%} else {
											cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
										<%}%>
											<td bgcolor="#90c7fc">
												<div align="center" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
														<strong>Total</strong>
													</font>
												</div>
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>
												<div align="right" class="style9">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
														<%=session.getAttribute("valorGuiaPagamento")%>
													</font>
												</div>
											</td>
										</tr>
									</logic:notEmpty>
								</logic:present>
							</table>
						</td>
					</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#000000">
							<td width="50%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor
							Total dos Débitos</strong> </font></div>
							</td>
							<td width="50%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor
							Total dos Débitos Atualizado</strong> </font></div>
							</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td>
							<div align="right" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorTotalSemAcrescimo")%>
							</font></div>
							</td>
							<td>
							<div align="right" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorTotalComAcrescimo")%>
							</font></div>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td align="right"><select name="opcaoRelatorio">
						<option
							value="gerarRelatorioDebitosClienteConsultarAction.do?pesquisaCliente=sim">Relatório
						de Débitos</option>
						<option
							value="gerarRelatorioDebitosClienteConsultarAction.do?pesquisaCliente=sim&relatorioEndereco=sim">Relatório
						de Débitos com Endereço</option>
						<option
							value="gerarRelatorioDebitosResumidoClienteConsultarAction.do">Relatório
						de Débitos Resumido</option>
					</select>&nbsp;</td>
					<td align="right">
					<div align="right"><a
						href="javascript:toggleBoxCaminho('demodiv',1, document.forms[0].opcaoRelatorio.value);">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>print.gif"
						title="Imprimir Débitos" /> </a></div>
					</td>
				</tr>
				<tr>
					<logic:empty name="colecaoContaValores">
						<td colspan="4" align="right"><input type="button" name=""
							value="Imprimir Extrato de Débito Total" class="bottonRightCol"
							disabled="true" /></td>
					</logic:empty>
					<logic:notEmpty name="colecaoContaValores">
						<td colspan="4" align="right"><input type="button" name=""
							value="Imprimir Extrato de Débito Total" class="bottonRightCol"
							onclick="window.location.href='<html:rewrite page="/gerarRelatorioExtratoDebitoClienteAction.do?tipo=total"/>'" />
						</td>
					</logic:notEmpty>
				</tr>

				<tr>
					<td height="17" colspan="4">&nbsp;</td>
				</tr>
				<logic:notPresent name="ehPopup">
					<tr>
						<td align="left" colspan="4"><input name="Button" type="button"
							class="bottonRightCol" value="Voltar"
							onClick="javascript:voltar();">&nbsp; <input name="adicionar"
							class="bottonRightCol" value="Cancelar" type="button"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
						</td>
					</tr>
				</logic:notPresent>
				
				<logic:present name="ehPopup">
					<tr height="24">
						<td align="left" colspan="4">
							<logic:present name="caminhoRetornoTelaConsultaDebito"	scope="request">
								<input type="button" class="bottonRightCol" value="Voltar" style="width: 70px;" onclick="javascript:history.back();" />&nbsp;&nbsp;
							</logic:present>
							<input name="Button" type="button" class="bottonRightCol" value="Fechar" onClick="javascript:window.close();">
						</td>
					</tr>
				</logic:present>
				
				
				<tr>
					<td height="17" colspan="4">&nbsp;</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	

	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=" />
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
