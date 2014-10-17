<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="java.util.HashMap"%>
<%@page import="gcom.cadastro.imovel.Imovel"%><html:html>

<head>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="EstabelecerVinculoPopupActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">
<!-- Begin -->
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    	var form = document.forms[0];
	    if (tipoConsulta == 'imovel') {
    	  	form.co11digoImovel.value = codigoRegistro;
 	    	form.matriculaImovel.value = descricaoRegistro;
            form.matriculaImovel.style.color = "#000000";
 	    	  	      		//form.action = 'exibirManterDebitoACobrarAction.do'
			//form.submit();
	    }
	 }

	function estabelecerVinculo(){
        var form = document.forms[0];
      	form.action = 'estabelecerVinculoPopupAction.do';
        form.submit();		
	}
	
	function facilitador(objeto){
	
		if (objeto.id == "0" || objeto.id == undefined){
			objeto.id = "1";
			marcarTodos();
	}
		else{
			objeto.id = "0";
			desmarcarTodos();
	}
	}
	
</script>
</head>

<body leftmargin="5" topmargin="5">
<html:form action="/estabelecerVinculoPopupAction.do"
	name="EstabelecerVinculoPopupActionForm"
	type="gcom.gui.micromedicao.EstabelecerVinculoPopupActionForm"
	method="post">

	<table width="650" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="650" valign="top" class="centercoltext">
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
					<td class="parabg">Estabelecer V&iacute;nculo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Preencha os campos para estabelecer v&iacute;nculo
					para rateio de consumo:</td>
					<td align="right"></td>								
				</tr>
				</table>
				<table width="100%" border="0">
				<logic:present name="EstabelecerVinculoPopupActionForm"
					property="rateioTipoAgua">

					<tr>
						<td width="35%" height="27"><strong>Tipo de Rateio da
						Liga&ccedil;&atilde;o de &Aacute;gua:<font color="#FF0000">*</font><strong></strong></strong></td>
						<td width="65%" colspan="3">
						<div align="left"><strong> <html:select
							name="EstabelecerVinculoPopupActionForm"
							property="rateioTipoAgua">
							<html:options name="request" collection="colecaoRateioTipo"
								labelProperty="descricao" property="id" />
						</html:select></strong></div>
						</td>
					</tr>
				</logic:present>
				<logic:present name="EstabelecerVinculoPopupActionForm"
					property="rateioTipoPoco">
					<tr>
						<td height="35"><strong>Tipo de Rateio do Po&ccedil;o:<strong></strong></strong></td>
						<td width="65%" colspan="3">
						<div align="left"><strong> <html:select
							name="EstabelecerVinculoPopupActionForm"
							property="rateioTipoPoco">
							<html:options name="request" collection="colecaoRateioTipo"
								labelProperty="descricao" property="id" />
						</html:select></strong></div>
						</td>
					</tr>
				</logic:present>
				<tr>
					<td height="24" colspan="4">
					<div style="width: 100%; height: 300; overflow: auto;">
					<table width="100%" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td>
								<a onclick="this.focus();" id="0" href="javascript:facilitador(this);">
									<div align="center" class="style9"><strong>Todos</strong></div>
								</a>
							</td>
							<td>
								<div align="center" class="style9"><strong>Matrícula</strong></div>
							</td>
							<td>
								<div align="center" class="style9"><strong>Inscrição</strong></div>
							</td>
							<td>
								<div align="center" class="style9"><strong>Endereço</strong></div>
							</td>
						</tr>
						<%	String cor = "#cbe5fe";
							HashMap mapRestrincoes = (HashMap) session.getAttribute("mapRestrincoes");
						%>
						<logic:iterate name="colecaoImoveisASerVinculados" id="imovel">
							<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
								<%} else {
				cor = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
								<%}%>
								<td width="5%">
									<div align="center" class="style9">
										<%
											if (!mapRestrincoes.containsKey(((Imovel)imovel).getId())) {
										%>
											<input type="checkbox" name="imoveisSelecionados"  value="<bean:write name="imovel" property="id" />">
										<% 
											} else {
												String mensagem = (String)mapRestrincoes.get(((Imovel)imovel).getId());
										%>
											<img align="center" border="0" src="<bean:message key='caminho.imagens'/>alerta.gif" title="<%=mensagem%>" width="14" border="0" height="14"/>
										<% 
											}
										%>
									</div>
								</td>
								<td width="10%">
									<div align="center" class="style9"><bean:write name="imovel"
										property="id" />
								</div>
								</td>
								<td width="20%">
								<div align="center" class="style9"><bean:write name="imovel"
										property="inscricaoFormatada" />
									</div>
								</td>
								<td width="65%">
									<div align="left" class="style9"><bean:write name="imovel"
										property="enderecoFormatado" />
									</div>
								</td>
							</tr>
						</logic:iterate>
					</table>
					</div>
					</td>
				</tr>
				<tr>
					<td height="27" colspan="4">
					<div align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Estabelecer V&iacute;nculo"
						onClick="javascript:estabelecerVinculo();"> <input name="Button2"
						type="button" class="bottonRightCol" value="Fechar"
						onClick="javascript:window.close();"></div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
<body>
</html:html>
