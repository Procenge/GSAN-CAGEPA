<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.util.ConstantesSistema"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirDadosEncerramentoOrdemServicoActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"> </script>	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript">
  
function validarForm() {
 	var form = document.forms[0];
 
	if(validaInserirDados(form)){	     
		submeterFormPadrao(form);   		  
    }  		  
}

function validaInserirDados(form){
	if(form.nnDoctoInfracao.value != '' ||
	   form.nnAnoDoctoInfracao.value != '' ||
	   form.infracaoTipo.value != '' ||
	   (form.infracaoImovelSituacao.value != '' && form.infracaoImovelSituacao.value != <%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>)||
	   (form.infracaoLigacaoSituacao.value != '' && form.infracaoLigacaoSituacao.value != <%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>)||
	   form.funcionario.value != ''){
		return validateRequired(form) &&
			   validateCaracterEspecial(form) &&
			   validateInteger(form);
	}else{
		return true;
	}
}

function required () {
    this.aa = new Array("nnDoctoInfracao", "Informe o Número da Infração.", new Function ("varName", " return this[varName];"));
    this.ab = new Array("nnAnoDoctoInfracao", "Informe o Ano da Infração.", new Function ("varName", " return this[varName];"));
    this.ac = new Array("infracaoTipo", "Informe Tipo de Infração.", new Function ("varName", " return this[varName];"));
    this.ad = new Array("infracaoImovelSituacao", "Informe Situação do Imóvel.", new Function ("varName", " return this[varName];"));
    this.ae = new Array("infracaoLigacaoSituacao", "Informe Situação da Ligação.", new Function ("varName", " return this[varName];"));
    this.af = new Array("funcionario", "Informe Funcionário Executor.", new Function ("varName", " return this[varName];"));
}

function caracteresespeciais () {
    this.ag = new Array("nnDoctoInfracao", "Número da Infração deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ah = new Array("nnAnoDoctoInfracao", "Ano da Infração deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ai = new Array("funcionario", "Funcionário Executor deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations () {
     this.aj = new Array("nnDoctoInfracao", "Número da Infração deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.al = new Array("nnAnoDoctoInfracao", "Ano da Infração deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.am = new Array("funcionario", "Funcionário Executor deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function limparForm() {
	var form = document.forms[0];
	form.data.value = "";
	form.descricao.value = "";
}

function limparFuncionario() {

	var form = document.forms[0];

	form.funcionario.value = "";
	form.nomeFuncionario.value = "";
}

function atualizarTelaInicial() {
	form = document.forms[0];
	form.action = '/gsan/exibirInserirDadosEncerramentoOrdemServicoAction.do?numeroOS=' + form.numeroOrdemServico.value + '&retornoTela=exibirEncerrarOrdemServicoAction.do?numeroOS=' + form.numeroOrdemServico.value;
	form.submit();
}

function encerrarPopup() {
	form = document.forms[0];
	form.action = '/gsan/encerrarOrdemServicoPopupAction.do';
	form.submit();
}

</script>  

</head>


<logic:present name="closePage" scope="session">
	<logic:equal name="closePage" value="S" scope="session">
		<body leftmargin="5" topmargin="5" onload="chamarReload('encerrarOrdemServicoAction.do');window.close();">
	</logic:equal>
	<logic:equal name="closePage" value="N" scope="session">
		<body leftmargin="5" topmargin="5" onload="encerrarPopup();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage" scope="session">
	<body leftmargin="5" topmargin="5">
</logic:notPresent>

<html:form action="/inserirDadosEncerramentoOrdemServicoAction.do" 
	name="InserirDadosEncerramentoOrdemServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.InserirDadosEncerramentoOrdemServicoActionForm"
	method="post">
	
	<html:hidden property="numeroOrdemServico"/>
	
	<table width="100%" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="100%" valign="top" class="centercoltext">

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
						<td class="parabg">Inserir Dados para Encerramento da Ordem de Serviço</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>

				<table width="100%" border="0" align="center" >
				
				<tr>Informe os dados abaixo:</tr>
				
				<tr>
					<td><strong>N&uacute;mero da Infra&ccedil;&atilde;o:</strong><font color="#FF0000"></font></td>
					<td>
						<html:text property="nnDoctoInfracao" size="8" maxlength="9" tabindex="1" /> 
		            </td>
				</tr>
				<tr>
				  <td><strong>Ano da Infra&ccedil;&atilde;o:<font color="#FF0000"></font></strong></td>
				  <td>
				  		<html:text property="nnAnoDoctoInfracao" size="4" maxlength="4" tabindex="2"/> 
				  </td>
				</tr>
				<tr>
				  <td><strong>Tipo de Infração:<font color="#FF0000"></font></strong></td>
				  <td>
					<html:select property="infracaoTipo" 
						size="6" 
						multiple="true" 
						style="width:190px">
						
						<logic:present name="colecaoInfracaoTipo" scope="request">
							<html:options collection="colecaoInfracaoTipo" 
								labelProperty="descricao" 
								property="id" />
						</logic:present>
					</html:select>
				  </td>
				</tr>
				<tr>
				  <td><strong>Situa&ccedil;&atilde;o do Im&oacute;vel:<font color="#FF0000"></font></strong></td>
				  <td>
					<strong> 
					<html:select property="infracaoImovelSituacao" style="width: 220px;">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
						</html:option>
				
						<logic:present name="colecaoInfracaoImovelSituacao" scope="request">
							<html:options collection="colecaoInfracaoImovelSituacao"
								labelProperty="descricao" 
								property="id" />
						</logic:present>
					</html:select> 														
					</strong>
				  </td>
				</tr>
				<tr>
				  <td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o:<font color="#FF0000"></font></strong></td>
				  <td>
					<strong> 
					<html:select property="infracaoLigacaoSituacao" style="width: 220px;">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
						</html:option>
				
						<logic:present name="colecaoInfracaoLigacaoSituacao" scope="request">
							<html:options collection="colecaoInfracaoLigacaoSituacao"
								labelProperty="descricao" 
								property="id" />
						</logic:present>
					</html:select> 														
					</strong>
				  </td>
				</tr>	
				<tr>
				  <td><strong>Funcion&aacute;rio Executor:<font color="#FF0000"></font></strong></td>
				  <td>
					<html:text maxlength="9" 
					tabindex="6"
					property="funcionario" 
					size="9"
					onkeypress="validaEnterComMensagem(event, 'exibirInserirDadosEncerramentoOrdemServicoAction.do?objetoConsulta=1&limparCampos=1','funcionario','Funcionário Executor');"/>
					
					<a href="javascript:atualizarTelaInicial();abrirPopupComSubmit('exibirPesquisarFuncionarioAction.do?limpaForm=sim&caminhoRetornoTelaPesquisaFuncionario=exibirInserirDadosEncerramentoOrdemServicoAction', 570, 700, 'Pesquisar')">
					
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Funcionario" /></a> 
	
					<logic:present name="funcionarioEncontrado" scope="request">
						<html:text property="nomeFuncionario" 
							size="35"
							maxlength="35" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:present> 
	
					<logic:notPresent name="funcionarioEncontrado" scope="request">
						<html:text property="nomeFuncionario" 
							size="35"
							maxlength="35" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />
					</logic:notPresent>
	
					
					<a href="javascript:limparFuncionario();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
					</a>	
				  </td>
				</tr>				
				
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					
					<td align="right"><div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%">
			              	<tr>
			            		<td >
		              				<div align="left">
	                  					<input name="ButtonFechar" type="button" class="bottonRightCol" 
	                  					value="Fechar" onclick="javascript:window.close();">
		              				</div>
			              		</td>
			              		
			              		<td align="right">
				              			<input type="button" name="Button" class="bottonRightCol"	
				              			value="Inserir" onclick="validarForm();" />
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