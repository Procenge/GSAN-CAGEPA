<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>

<head>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="PesquisarComandoAcaoCobrancaActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">
<!-- Begin -->
	function pesquisar(){
        var form = document.forms[0];

        if(form.periodoReferenciaCobrancaInicial.value != '' ){
			if(!validaAnoMesSemAlert(form.periodoReferenciaCobrancaInicial)){
				alert('Informe o Período de Referência da Cobrança Inicial no formato correto mm/aaaa');
				return;
			}
        }
        if(form.periodoReferenciaCobrancaFinal.value != '' ){
			if(!validaAnoMesSemAlert(form.periodoReferenciaCobrancaFinal)){
				alert('Informe o Período de Referência da Cobrança Final no formato correto mm/aaaa');
				return;
			}
        }        
        if(validateCaracterEspecial(form)){
      		form.action = 'pesquisarCronogramaAcaoCobrancaAction.do';
        	form.submit();		
		}
	}
	
    function caracteresespeciais () {
	     this.aa = new Array("idUsuario", "Usuário que Gerou o Comando deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
    	 this.ab = new Array("idUsuario", "Usuário que Gerou o Comando deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }	   
    
	function duplicaPeriodoReferenciaCobranca(form){
		form.periodoReferenciaCobrancaFinal.value = form.periodoReferenciaCobrancaInicial.value;
	}

	function verificarPreenchimentoDosCampos(){
		var preenchido = false;
   	 	var formulario = document.forms[0];

		preenchido = true;

		if (preenchido){
			pesquisar();
		}else{
			formulario.elements[0].focus();
			alert("Informe pelo menos uma opção de seleção");
		}
	}

	function limparForm(){
		var form = document.forms[0];
		form.cobrancaAcao.value = "-1";
		form.cobrancaAtividade.value = "-1";
   		form.usuario.value = "";
    	form.idUsuario.value = "";
		form.periodoGeracaoComandoInicial.value = "";
		form.periodoGeracaoComandoFinal.value = "";
		form.periodoExecucaoComandoInicial.value = "";
		form.periodoExecucaoComandoFinal.value = "";
	}


</script>

</head>
<html:form action="/pesquisarCronogramaAcaoCobrancaAction.do" name="PesquisarCronogramaAcaoCobrancaActionForm" type="gcom.gui.cobranca.PesquisarCronogramaAcaoCobrancaActionForm"
	method="post">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
	<table width="717" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="707" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img src="imagens/parahead_left.gif"
						editor="Webstyle4"
						moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws"
						border="0" /></td>
					<td class="parabg">Pesquisar Comandos de A&ccedil;&atilde;o de
					Cobran&ccedil;a - Comandos do Cronograma</td>
					<td width="11"><img src="imagens/parahead_right.gif"
						editor="Webstyle4"
						moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
			<tr>
				<td width="29%"><strong>Per&iacute;odo de Refer&ecirc;ncia da
				Cobran&ccedil;a:</strong></td>
				<td colspan="4"><strong><strong> <html:text maxlength="7" value=""
					name="PesquisarCronogramaAcaoCobrancaActionForm"
					property="periodoReferenciaCobrancaInicial" size="7"
					onkeyup="javascript:mascaraAnoMes(this, event);duplicaPeriodoReferenciaCobranca(document.forms[0]);" />
				</strong> </strong>(mm/aaaa)<strong><strong><strong> a </strong> <html:text
					maxlength="7" value=""
					name="PesquisarCronogramaAcaoCobrancaActionForm"
					property="periodoReferenciaCobrancaFinal" size="7"
					onkeyup="javascript:mascaraAnoMes(this, event);" /> </strong> </strong>(mm/aaaa)<strong>
				</strong></td>
			</tr>
			<tr>
				<td><strong>Grupo de Cobran&ccedil;a:</strong></td>
				<td colspan="4"><html:select property="grupoCobranca"
					name="PesquisarCronogramaAcaoCobrancaActionForm"
					style="width: 230px;" multiple="mutiple" size="4">
					<logic:present name="colecaoGrupoCobranca">
						<html:option value="" />
						<html:options collection="colecaoGrupoCobranca"
							labelProperty="descricao" property="id" />
					</logic:present>
				</html:select></td>
			</tr>
			<tr>
				<td><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:</strong></td>
				<td colspan="4"><html:select property="acaoCobranca"
					name="PesquisarCronogramaAcaoCobrancaActionForm"
					style="width: 230px;" multiple="mutiple" size="4">
					<logic:present name="colecaoAcaoCobranca">
						<html:option value="" />
						<html:options collection="colecaoAcaoCobranca"
							labelProperty="descricaoCobrancaAcao" property="id" />
					</logic:present>
				</html:select></td>
			</tr>
			<tr>
				<td><strong>Atividade de Cobran&ccedil;a:</strong></td>
				<td colspan="4"><html:select property="atividadeCobranca"
					name="PesquisarCronogramaAcaoCobrancaActionForm"
					style="width: 230px;" multiple="mutiple" size="4">
					<logic:present name="colecaoAtividadeCobranca">
						<html:option value="" />
						<html:options collection="colecaoAtividadeCobranca"
							labelProperty="descricaoCobrancaAtividade" property="id" />
					</logic:present>
				</html:select></td>
			</tr>
		<tr>
			<td colspan="3" align="left">
				<table border="0" align="left">
					<tr>
						<td align="left">
							<input name="Button32222" type="button" class="bottonRightCol" value="Limpar" onClick="javascript:limpar()" /></td>
						<td align="left">&nbsp;</td>							
					</tr>
				</table>
			</td>
			<td>
				<table border="0" align="right">
					<tr>
						<td align="right">
						  <input name="Button32222" type="button" class="bottonRightCol" value="Voltar" onClick="javascript:voltar()" />
						</td>

						<td align="right">
						  <INPUT TYPE="button" name="pesquisar" class="bottonRightCol" value="Pesquisar" onClick="javascript:verificarPreenchimentoDosCampos()"/>
						  <%--<gcom:controleAcessoBotao name="concluir" value="Pesquisar" url="pesquisarCronogramaAcaoCobrancaAction.do"/>
						   <input name="concluir" type="button" class="bottonRightCol" onClick="javascript:filtrar();" value="Filtrar" />--%>
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
	</body>
</html:form>
</html:html>
