<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarAtendimentoProcedimentoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){

			submeterFormPadrao(form);

	}

	function carregarSolicitacaoTipoEspecificacao() {
		var form = document.forms[0];
		
		form.action = '/gsan/exibirFiltrarAtendimentoProcedimentoAction.do?carregarSolicitacaoTipoEspecificacao=1';
		form.submit();
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
	    if (tipoConsulta == 'funcionalidade') {
	    	  form.idFuncionalidade.value = codigoRegistro;
		      form.descricaoFuncionalidade.value = descricaoRegistro;
		      form.action = 'exibirFiltrarAtendimentoProcedimentoAction.do';
		      form.submit();
	    }
	}
	
	function limparFuncionalidade(){
	 	var form = document.forms[0];
	 	
	 	form.idFuncionalidade.value = "";
	 	form.descricaoFuncionalidade.value = ""; 		
	 }
	
	 function limparDescricaoFuncionalidade(){
		 	var form = document.forms[0];
		 	form.descricaoFuncionalidade.value = ""; 		
	 }

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

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
			<td width="600" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Procedimento Atendimento</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			
			<html:form action="filtrarAtendimentoProcedimentoAction" method="post"
				name="FiltrarAtendimentoProcedimentoActionForm"
				type="gcom.gui.cadastro.atendimento.FiltrarAtendimentoProcedimentoActionForm"
				onsubmit="return validateFiltrarAtendimentoProcedimentoActionForm(this);">
				
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para manter a(s) Procedimentos(s) de Atendimento(s), informe os
					dados abaixo:</td>
					<td width="80" align="right"><html:checkbox property="atualizar" value="1"/><strong>Atualizar</strong></td>
     			</tr>
				<tr>
					<td width="120"><strong>Código:</strong></td>
					<td colspan="2"><strong> <html:text property="id" size="10" maxlength="9" onkeyup="javascript:verificaNumeroInteiro(this);" />
					</strong>
				</tr>

				
				<tr>
					<td><strong>Funcionalidade:</strong></td>
					<td colspan="3"><html:text property="idFuncionalidade" size="9"
						maxlength="9" tabindex="2"
						onkeyup="javascript:limparDescricaoFuncionalidade();"
						onkeypress="return validaEnterSemUpperCase(event, 'exibirFiltrarAtendimentoProcedimentoAction.do', 'idFuncionalidade');" />
					<a
						href="javascript:chamarPopup('exibirPesquisarFuncionalidadeAction.do', '', null, null, 600, 500, '','');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Funcionalidade" /> </a>  
					<logic:present name="funcionalidadeNaoEncontrada">
						<logic:equal name="funcionalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoFuncionalidade" size="45"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeNaoEncontrada"
							value="exception">
							<html:text property="descricaoFuncionalidade" size="45"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="funcionalidadeNaoEncontrada">
						<logic:empty name="FiltrarAtendimentoProcedimentoActionForm"
							property="idFuncionalidade">
							<html:text property="descricaoFuncionalidade" value="" size="30"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarAtendimentoProcedimentoActionForm"
							property="idFuncionalidade">
							<html:text property="descricaoFuncionalidade" size="30"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparFuncionalidade();"> <img
						border="0" title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>
				</tr>				
				<tr>
					<td width="162"><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td>
					<strong> 
					<html:text property="descricao" size="40" maxlength="60" /> 
					</strong>
					</td>
				</tr>
				
	              <tr> 
	                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong>
						<html:select property="idSolicitacaoTipo" 
							style="width: 350px; height:100px;" 
							multiple="true" 
							onchange="javascript:carregarSolicitacaoTipoEspecificacao();" 
							tabindex="7">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoTipoSolicitacao" scope="session">
								<html:options collection="colecaoTipoSolicitacao" labelProperty="descricao" property="id" />
							</logic:present>
						</html:select>                
	                  </strong></span></td>
	              </tr>
	              
	              <tr> 
	                <td><strong>Especifica&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong> 
						<html:select property="idSolicitacaoTipoEspecificacao" 
							style="width: 350px;" 
							multiple="true" 
							tabindex="8">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoEspecificacao" scope="session">
								<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
							</logic:present>
						</html:select>                
	                  </strong></span>
	                 </td>
	              </tr>	
	              
			  <tr>
				<td><strong>Indicador de Uso:</strong></td>
				<td align="right">
				  <div align="left">
					<html:radio property="indicadorUso" tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /><strong>Ativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /><strong>Inativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="" /><strong>Todos</strong>
				  </div>
				</td>
			  </tr>	              	

				<tr>

				</tr>

				<tr>
					<td colspan="3"><input name="button" type="button" class="bottonRightCol"
						value="Limpar"
						onclick="window.location.href='<html:rewrite page="/exibirFiltrarAtendimentoProcedimentoAction.do?desfazer=S"/>'">&nbsp;
					</td>

					<td align="right"><input name="botaoFiltrar" type="button"
						class="bottonRightCol" align="right" value="Filtrar"
						onclick="validarForm(document.forms[0]);" tabindex="19"
						style="width: 70px;"></td>
				</tr>				
				
				
			</table>
			</html:form>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</body>
</html:html>

