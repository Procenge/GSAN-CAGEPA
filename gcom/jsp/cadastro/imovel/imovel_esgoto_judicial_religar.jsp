<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="ReligarSuprimirImovelEsgotoJudicialActionForm" />


 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

	function validarForm(form) {
		if(confirm("Confirma Religação Supressão Judicial do  Imovel?")){
	  		if(validateReligarSuprimirImovelEsgotoJudicialActionForm(form)) {
				submeterFormPadrao(form);
			}
	  	}
	}

	//Funcao que recupera os dados do popup
	 function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];       
		form.action='exibirReligarSuprimidoImovelEsgotoJudicialAction.do?idImovel=' + codigoRegistro;
		submeterFormPadrao(form);
	 }

	//Funcao que serve para limpar a pesquisa do imovel  
	 function limparPesquisaImovel() {
	    var form = document.forms[0];
	    form.idImovel.value = '';
	    form.inscricaoImovel.value = '';
	    form.nomeClienteProprietario.value ='';
	    form.enderecoClienteProprietario.value ='';
	  }
	  
	  function limpaDesabilita(){
	    var form = document.forms[0];
	    if(form.idImovel.value == ''){
	     limparPesquisaImovel();
	    }
	  }
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/efetuarReligarSuprimidoImovelEsgotoJudicialAction.do" name="ReligarSuprimirImovelEsgotoJudicialActionForm" type="gcom.gui.cadastro.imovel.ReligarSuprimirImovelEsgotoJudicialActionForm" enctype="multipart/form-data" method="post" focus="idImovel">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="150" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Religar Esgoto Suprimido Judicialmente</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para religar esgoto suprimido judicialmente do im&oacute;vel, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td width="207"><strong>Matr&iacute;cula do Im&oacute;vel:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="9" property="idImovel" size="10" tabindex="1"
						onkeypress="validaEnterComMensagem(event, 'exibirReligarSuprimidoImovelEsgotoJudicialAction.do', 'idImovel', 'Matrícula do Imóvel');"
						onkeyup="limpaDesabilita();" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Imóvel" /></a> 
					<logic:present name="corInscricao">
						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="corInscricao">
						<logic:empty name="ReligarSuprimirImovelEsgotoJudicialActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="ReligarSuprimirImovelEsgotoJudicialActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent>
					<a
						href="javascript:limparPesquisaImovel();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Imóvel" /></a></td>
				</tr>
				
				<tr>
					<td><strong>Nome do Cliente Propriet&aacute;rio:</strong></td>
					<td><html:textarea rows="2" property="nomeClienteProprietario" cols="43" readonly="true" style="background-color:#EFEFEF; border:0" /></td>
				</tr>
				<tr>
					<td><strong>Endereço do Cliente Propriet&aacute;rio:</strong></td>
					<td><html:textarea rows="2" property="enderecoClienteProprietario" cols="43" readonly="true" style="background-color:#EFEFEF; border:0" /></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
						<div align="left">
							<strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<strong>
							<font color="#FF0000">
								<input name="btLimpar" class="bottonRightCol" value="Limpar" type="button" onclick="window.location.href='/gsan/exibirReligarSuprimidoImovelEsgotoJudicialAction.do?menu=sim';">
								<input type="button" name="Button" class="bottonRightCol" value="Cancelar" tabindex="6" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'" style="width: 80px" />
							</font>
						</strong>
					</td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Religar" tabindex="7" onclick="javascript:validarForm(document.forms[0]);" url="efetuarReligarSuprimidoImovelEsgotoJudicialAction.do" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>