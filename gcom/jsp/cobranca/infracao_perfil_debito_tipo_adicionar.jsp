<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="InfracaoPerfilDebitoTipoActionForm"/>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
<SCRIPT LANGUAGE="JavaScript">


<!--
function adicionarDebitoTipo(){
	redirecionarSubmit('exibirInserirInfracaoPerfilDebitoTipo.do?incluir=ok');
}
function validarForm(form){
	redirecionarSubmit('exibirInserirInfracaoPerfilDebitoTipo.do?concluir=ok');
}

function verificaCampoVazio(objeto1, objeto2) {
	var form = document.forms[0];
	if (objeto1.value != "") {
		objeto2.disabled = true;
	} else {
		objeto2.disabled = false;
	}
}

function limparTipoDebito(){
	var form = document.forms[0];
	form.idTipoDebito.value = "";
	form.descricaoDebitoTipo.value = "";
}

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
    
    if (tipoConsulta == 'tipoDebito') {

	      if(!form.debitoTipo.disabled){
		      form.idTipoDebito.value = codigoRegistro;
		      form.descricaoDebitoTipo.value = descricaoRegistro;
		      form.descricaoDebitoTipo.style.color = "#000000";
		      reload();
	      }
    }
}

function removeRowTableInfracaoPerfilDebitoTipo(id){
	redirecionarSubmit('exibirInserirInfracaoPerfilDebitoTipo.do?remover='+id);
}

function atualizaValores(tipo,valor,id){
	if (tipo == 3)
		AjaxService.atualizaValoresFatorMultiplicador(valor.value,id);
}
//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">

	<logic:equal name="reloadPageURL" value="INSERIRCONTRATO">
	<body leftmargin="0" topmargin="0" onload="window.focus(); chamarSubmitComUrl('exibirInserirContratoCobrancaAction.do?reloadPage=1'); self.close();">
	</logic:equal>
		
	<logic:equal name="reloadPageURL" value="MANTERCONTRATO">
	<body leftmargin="0" topmargin="0" onload="window.focus();  chamarSubmitComUrl('exibirAtualizarContratoCobrancaAction.do?reloadPage=OK'); opener.reloadPagina();limparTipoServico();">
	</logic:equal>
	
</logic:present>

<logic:present name="fechar">
		<body leftmargin="0" topmargin="0" onload="window.close();">
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="0" topmargin="0" onload="window.focus();">
</logic:notPresent>


	<html:form action="/exibirInserirInfracaoPerfilDebitoTipo.do?concluir=ok" method="post" onsubmit="return validarForm(this);">
		<table width="602" border="0" cellpadding="0" cellspacing="5">
  			<tr>
    			<td width="602" valign="top" class="centercoltext"> 
				 	<table height="100%">
				  		<tr>
				  			<td></td>
				 		</tr>
				 	</table>
				 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				    	<tr>
					        <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
					        <td class="parabg">Adicionar Tipo de Débito</td>
					        <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
				    	</tr>
				    </table>
      				<p>&nbsp;</p>
		
			      	<table width="100%" border="0">
				        <tr>
				          <td colspan="4">Preencha os campos abaixo para inserir um ou mais Tipo de Débito ao Perfil de Infração:</td>
				        </tr>
			        </table>
					<html:hidden property="idPerfil"/>			        
					<table width="100%" border="0">
						<tr>
							<td><strong>Tipo Débito:<font color="#FF0000">*</font></strong></td>
							<td colspan="3">
								<html:text property="idTipoDebito" maxlength="4" size="4"
									onkeypress="validaEnterComMensagem(event, 'exibirInserirInfracaoPerfilDebitoTipo.do','idTipoDebito','Tipo de Débito');"
									onkeyup="" /> 
								<a href="javascript:redirecionarSubmit('exibirPesquisarTipoDebitoAction.do?caminhoRetornoTelaPesquisaTipoDebito=exibirInserirInfracaoPerfilDebitoTipo', 400, 800);"
									alt="Pesquisar Tipo de Débito"> 
									<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
								<logic:present name="debitoTipoEncontrado" scope="request">
									<html:text property="descricaoDebitoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> 
								<logic:notPresent name="debitoTipoEncontrado" scope="request">
									<html:text property="descricaoDebitoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
								</logic:notPresent> 
								<a href="javascript:limparTipoDebito();"> 
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
								</a></td>
						</tr>
						<logic:present name="atualizar" >
						<tr>
							<td><strong>Lançamento Ativo:</strong></td>
							<td colspan="3">
								<html:radio property="indicadorLancamentoAtivo" style="text-align: right;" value="1" /><strong>Sim</strong>
							  	<html:radio property="indicadorLancamentoAtivo" style="text-align: right;" value="2" /><strong>Não</strong> </td>
						</tr>
						</logic:present>
						<tr>
							<td><strong>Porcentagem Desconto:</strong></td>
							<td><html:text property="pcDesconto" size="15" maxlength="5" style="text-align: right;" onkeypress="formataValorMonetario(this, 11);" /></td>
						</tr>
						<tr>
							<td><strong>Fator Multiplicador:</strong></td>
							<td><html:text property="fatorMultiplicador" size="15" maxlength="5" style="text-align: right;" onkeypress="formataValorMonetario(this, 11);" /></td>
						</tr>
						<tr>
				<td >
					
				</td>
				<td align="right">
					<input type="button"
							   name="ButtonCancelar" 
							   class="bottonRightCol" 
							   value="Adicionar"
							   onClick="javascript:adicionarDebitoTipo();">
				</td>
			</tr>
			
						<tr>
						<td colspan="4"><strong> <font color="#FF0000"></font></strong>
						<div align="left">
							Clique no link para informar o(s) tipo(s) de débito de cada perfil de infração.
							<table width="100%" id="tableAtividades" align="center"
								bgcolor="#99CCFF">
								<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
									<td width="14%">
										<div align="center"><strong>Remover</strong></div>
									</td>
									<td>
										<div align="center"><strong>Tipo Debito</strong></div>
									</td>
									<logic:present name="manter" scope="session">
									<td>
										<div align="center"><strong>Lançamento Ativo</strong></div>
									</td>
									</logic:present>
									<td>
										<div align="center"><strong>Pc. Desconto</strong></div>
									</td>
									<td>
										<div align="center"><strong>Fator Multip.</strong></div>
									</td>
								</tr>
								<tbody>
									
									<c:forEach var="debitoTipo" items="${sessionScope.colecaoDebitoTipo}" varStatus="i">
								
										<c:if test="${i.count%2 == '1'}">
											<tr bgcolor="#FFFFFF">
										</c:if>
										<c:if test="${i.count%2 == '0'}">
											<tr bgcolor="#cbe5fe">
										</c:if>
										<logic:present name="manter" scope="session">
											<td><div align="center"><a href="javascript:if(confirm('Confirma remoção?')){removeRowTableInfracaoPerfilDebitoTipo('${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}');}">
												 	<img src="imagens/Error.gif" width="14" height="14" border="0" title="Remover"></a></div></td>
											<td> <div align="center">${debitoTipo.tipoDebitoDescricao}</div> </td>
											<td> 
											<c:if test="${debitoTipo.lancamentoAtivo == 'SIM'}">
											<div align="center"><select style="text-align: right;font-size: xx-small;" 
													name="lancamentoAtivo${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}" >
													<option value="SIM" selected="selected">SIM </option>
													<option value="NAO">NAO </option>
													</select></div>
											</c:if>
											<c:if test="${debitoTipo.lancamentoAtivo == 'NAO'}">
											<div align="center"><select style="text-align: right;font-size: xx-small;" 
													name="lancamentoAtivo${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}" >
													<option value="SIM">SIM </option>
													<option value="NAO" selected="selected">NAO </option>
													</select></div>
											</c:if>
													 </td>
											<td> <div align="center">												
												<input type="text" style="text-align: right;font-size: xx-small;" 
													align="middle" name="pcDesconto${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}"
													value="${debitoTipo.porcentagemDesconto}"  size="5" maxlength="5" onkeypress="formataValorMonetario(this, 11);"/></div></td>
											</div></td>
											<td> <div align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													align="middle" name="fatorMultiplicador${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}"
													value="${debitoTipo.fatorMultiplicador}"  size="5" maxlength="5" onkeypress="formataValorMonetario(this, 11);"/></div></td>
											</logic:present>
										<logic:notPresent name="manter" scope="session">
										<td><div align="center"><a href="javascript:if(confirm('Confirma remoção?')){removeRowTableInfracaoPerfilDebitoTipo('${debitoTipo.idInfracaoPerfil}${debitoTipo.idDebitoTipo}');}">
												 	<img src="imagens/Error.gif" width="14" height="14" border="0" title="Remover"></a></div></td>
											<td> <div align="center">${debitoTipo.tipoDebitoDescricao}</div> </td>
											<td> <div align="center">${debitoTipo.porcentagemDesconto}</div></td>
											<td> <div align="center">${debitoTipo.fatorMultiplicador}</div></td>
										</logic:notPresent>
									</c:forEach>
								
								</tbody>
							</table>
						</div>
					</td>
						</tr>
						<tr>
							<td height="30" colspan="4">
								<div align="right">
									<input type="button" onclick="validarForm(document.forms[0]);" class="bottonRightCol" value="Concluir" style="width: 70px;">&nbsp; 
									<input type="button" onclick="window.close();" class="bottonRightCol" value="Fechar" style="width: 70px;"></div>
							</td>
						</tr>
					</table>
					<p>&nbsp;</p>
      			</td>
  			</tr>
		</table>

	</html:form>

</body>
</html:html>