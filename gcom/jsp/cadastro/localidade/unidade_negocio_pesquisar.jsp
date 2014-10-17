<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarUnidadeNegocioActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">

	function validarForm(){
   		var form = document.forms[0];

    	if(validatePesquisarUnidadeNegocioActionForm(form)){
    		form.submit();
	  	}
    }

	function limparForm(){

		var form = document.forms[0];
		
		form.idUnidade.value = "";
		form.descricao.value = "";
		limparPesquisaLocalidade();
	}

	function limparPesquisaLocalidade() {

    	var form = document.forms[0];

    	form.idLocalidade.value = "";
    	form.descricaoLocalidade.value = "";
  	}

  
    function submitForm() {
    	var form = document.forms[0];
    	
    	form.action = 'exibirPesquisarUnidadeNegocioAction.do'
    	form.submit();
    }

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(640, 340);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/pesquisarUnidadeNegocioAction" method="post">
	
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
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Pesquisar Unidade Negócio:</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Preencha o campo para pesquisar uma unidade de negócio:</td>
					<td align="right"></td>
				</tr>
			</table>
			
			<table width="100%" border="0">
				<tr>
					<td width="14%"><strong>Código:</strong></td>
					<td width="86%" colspan="3"><html:text maxlength="4"
						property="idUnidade" size="4" tabindex="1" /></td>
				</tr>
				<tr>
					<td height="0"><strong>Descrição:</strong></td>
					<td colspan="3"><html:text maxlength="30" property="descricao"
						size="30" tabindex="2" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3"><html:radio property="tipoPesquisa"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto&nbsp;<html:radio
						property="tipoPesquisa" tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>

				<tr>
					<td><strong>Localidade:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="idLocalidade" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirPesquisarUnidadeNegocioAction.do?objetoConsulta=1','idLocalidade','Localidade');"/> 
							
							<a href="javascript:redirecionarSubmit('exibirPesquisarLocalidadeAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarUnidadeNegocioAction');">

								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Localidade" /></a> 

							<logic:present name="idLocalidadeEncontrada" scope="request">
								
								<html:text property="descricaoLocalidade" 
									size="30"
									maxlength="30" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
								
							</logic:present> 

							<logic:notPresent name="idLocalidadeEncontrada" scope="request">
								
								<html:text property="descricaoLocalidade" 
									size="30"
									maxlength="30" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />

							</logic:notPresent>

							
							<a href="javascript:limparPesquisaLocalidade();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
						</td>
				</tr>

				

				<tr>
					<td height="24">
			          	<input type="button" class="bottonRightCol" value="Limpar" onclick="javascript:limparForm();"/>
							&nbsp;&nbsp;
	          			
	          			<logic:present name="caminhoRetornoTelaPesquisaUnidadeNegocio">
			          		<input type="button" 
			          			class="bottonRightCol" 
			          			value="Voltar" 
			          			onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaUnidadeNegocio}.do')"/>
			          	</logic:present>
        		  		
					</td>
					
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol"
							value="Pesquisar" 
							onClick="javascript:validarForm()" />
					</td>
					
					<td>&nbsp;</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
