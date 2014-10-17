<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">



<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="InserirTipoDebitoActionForm" />
	

<script language="JavaScript">

function validaIndicadores(){
	
			var form = document.InserirTipoDebitoActionForm;
    			
    			if(form.indicadorGeracaoDebitoAutomatica[0].checked == false &&	form.indicadorGeracaoDebitoAutomatica[1].checked == false){
    			alert('Indicador de Geração do Débito Automática');
    			}
    			if(form.indicadorGeracaoDebitoConta[0].checked == false &&	form.indicadorGeracaoDebitoConta[1].checked == false){
    			alert('Indicador de Geração do Débito em Conta');
    			}
	}
	
/*function validaIndicadorGeracaoDebitoAutomatica(){
	
			var form = document.InserirTipoDebitoActionForm;
    			if(form.indicadorGeracaoDebitoConta[0].checked == false &&	form.indicadorGeracaoDebitoConta[1].checked == false){
    			alert('Indicador de Geração do Débito em Conta');
    			}
	}
*/

function inserirDebitoTipoValorLocalidade() { 		

  	chamarPopup('exibirPesquisarDebitoTipoValorLocalidadeAction.do?limparCampos=ok&', 'debitoTipoValorLocalidade', null, null, 300, 620, '','');	   	
}

function recuperarDadosQuatroParametros(codigoRegistro, descricaoRegistro, codigoAuxiliar, tipoConsulta) {
	if (tipoConsulta == 'debitoValorLocalidade'){
		insertRowTableDebitoTipoValorLocalidade(codigoRegistro, descricaoRegistro, codigoAuxiliar);
	}
}




function validarForm(form){

	/*if(//testarCampoValorZero(document.InserirTipoDebitoActionForm.descricao, 'Descrição do Tipo de Débito') && 
	//testarCampoValorZero(document.InserirTipoDebitoActionForm.descricaoAbreviada, 'Descrição do Tipo de Débito Abreviada') && 
	//testarCampoValorZero(document.InserirTipoDebitoActionForm.lancamentoItemContabil, 'Tipo do Lançamento do Item Contábil') && 
	//testarCampoValorZero(document.InserirTipoDebitoActionForm.financiamentoTipo, 'Tipo de Financiamento') && 
	//testarCampoValorZero(document.InserirTipoDebitoActionForm.indicadorGeracaoDebitoAutomatica, 'Indicador de Geração do Débito Automática') && 
	//testarCampoValorZero(document.InserirTipoDebitoActionForm.indicadorGeracaoDebitoConta, 'Indicador de Geração do Débito em Conta') && 
	testarCampoValorZero(document.InserirTipoDebitoActionForm.valorLimiteDebito, 'Valor Limite do Débito')) {*/

		if(validateInserirTipoDebitoActionForm(form)){
			if(form.indicadorGeracaoDebitoAutomatica[0].checked == false &&	form.indicadorGeracaoDebitoAutomatica[1].checked == false){
    			alert('Informe Indicador de Geração do Débito Automática .');
    			return false;
    		}
    		if(form.indicadorGeracaoDebitoConta[0].checked == false &&	form.indicadorGeracaoDebitoConta[1].checked == false){
    			alert('Informe Indicador de Geração do Débito em Conta .');
    			return false;
    		}
    		if(form.indicadorIncidenciaMulta[0].checked == false &&	form.indicadorIncidenciaMulta[1].checked == false){
    			alert('Informe Indicador de Incidência de Multa .');
    			return false;
    		}
    		if(form.indicadorIncidenciaJurosMora[0].checked == false &&	form.indicadorIncidenciaJurosMora[1].checked == false){
    			alert('Informe Indicador de Geração do Débito em Conta .');
    			return false;
    		}

    		if(form.indicadorValorCalcular[0].checked == false &&	form.indicadorValorCalcular[1].checked == false){
    			alert('Informe Indicador de Valor a Calcular.');
    			return false;
    		}
    		
    		submeterFormPadrao(form);
    	}

	//}
}

function reload() {
	var form = document.forms[0];
	form.action = "/gsan/exibirInserirTipoDebitoAction.do";
	form.submit();
}

function insertRowTableDebitoTipoValorLocalidade(codigoRegistro, descricaoRegistro, codigoAuxiliar) {
	
	var form = document.forms[0];	
	form.valorLocalidadeId.value = codigoRegistro;
	form.debitoTipoValorLocalidadeValor.value = codigoAuxiliar;
	form.debitoTipoValorLocalidadeDescricao.value = descricaoRegistro;
	form.method.value = "addDebitoTipoValorLocalidade";
	
	
	reload();
}	

function removeRowTableDebitoTipoValorLocalidade(id) {
	var form = document.forms[0];	
	form.valorLocalidadeId.value = id;
	form.method.value = "removeDebitoTipoValorLocalidade";
	reload();
}

function atualizarColecaoDebitoTipoValorLocalidade(id, valor){

	var form = document.forms[0];
	var valorAtualizado = valor.value;
		form.action = "/gsan/exibirInserirTipoDebitoAction.do?atualizarIdLocalidade="+id+"&debitoValorLocalidade="+valorAtualizado;
	form.submit();

}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado) {
	if(objetoRelacionado.disabled != true) {
		if (objeto == null || codigoObjeto == null) {
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		} else {
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)) {
				alert(msg);
			} else {
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/inserirTipoDebitoAction.do" method="post"
	name="InserirTipoDebitoActionForm"
	type="gcom.gui.faturamento.debito.InserirTipoDebitoActionForm"
	onsubmit="return validateInserirTipoDebitoActionForm(this);">
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
	
		<html:hidden property="valorLocalidadeId" />
		<html:hidden property="debitoTipoValorLocalidadeDescricao" />
		<html:hidden property="debitoTipoValorLocalidadeValor" />

<html:hidden property="method" />
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
					<td class="parabg">Inserir Tipo de Débito</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar um tipo de débito, informe os dados abaixo:</td>
				</tr>
				<tr>
				  <td><strong>Descrição do Tipo de Débito:<font color="#FF0000">*</font></strong></td>
				  <td>
					<strong> 
					  <html:text property="descricao" size="30" maxlength="30" /> 
					</strong>
				  </td>
				</tr>
				<tr>
					<td width="162"><strong>Descrição do Tipo de Débito Abreviada:</strong></td>
					<td>
					<strong> 
					<html:text property="descricaoAbreviada" size="18" maxlength="18" /> 
					</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Tipo do Lançamento do Item Contábil:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="lancamentoItemContabil">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLancamentoItemContabil"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				
				<tr>
					<td><strong>Tipo de Financiamento:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="financiamentoTipo">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoFinanciamentoTipo"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				<tr>
					<td><strong>Indicador de Geração do Débito Automática:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorGeracaoDebitoAutomatica" value="1" />
					<strong>Sim <html:radio property="indicadorGeracaoDebitoAutomatica" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Indicador de Geração do Débito em Conta:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorGeracaoDebitoConta" value="1" />
					<strong>Sim <html:radio property="indicadorGeracaoDebitoConta" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Indicador de Incid&ecirc;ncia de Multa<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorIncidenciaMulta" value="1" />
					<strong>Sim <html:radio property="indicadorIncidenciaMulta" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>	
				<tr>
					<td><strong>Indicador de Incid&ecirc;ncia de Juros Mora<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorIncidenciaJurosMora" value="1" />
					<strong>Sim <html:radio property="indicadorIncidenciaJurosMora" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>				
				<tr>
					<td><strong>Valor Limite do Débito:<font color="#FF0000">*</font></strong></td>
					<td>
					<strong> 
					<html:text property="valorLimiteDebito" size="17" maxlength="17" onkeyup="javascript:formataValorMonetario(this,13);" style="text-transform: none;"/>  
					</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Valor Padrão:</strong></td>
					<td>
					<strong> 
					<html:text property="valorPadrao" size="17" maxlength="17" onkeyup="javascript:formataValorMonetario(this,13);" style="text-transform: none;"/> 
					</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Indicador de Valor a Calcular<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorValorCalcular" value="1" />
					<strong>Sim <html:radio property="indicadorValorCalcular" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<!-- Valores diferenciados por Localidade -->
				<tr>
					<td colspan="3"><strong> <font color="#000000">Valores diferenciados por Localidade </font></strong></td>
					<td align="right">
					<div align="right"><input type="button" name="Submit24"
						class="bottonRightCol" value="Adicionar"
						onclick="inserirDebitoTipoValorLocalidade();"></div>
					</td>
				</tr>
				
				<tr>
					<td colspan="4"><strong> <font color="#FF0000"></font></strong>
					<div align="left">
					<table width="100%" id="tableDebitoTipoValorLocalidade" align="center"
						bgcolor="#99CCFF">
						<!--corpo da segunda tabela-->
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td width="14%">
							<div align="center"><strong>Remover</strong></div>
							</td>
							<td width="55%">
							<div align="center"><strong>Descri&ccedil;&atilde;o das
							Localidades </strong></div>
							</td>
							<td>
							<div align="center"><strong>Valor </strong></div>
							</td>
						</tr>
						<tbody>

							<c:forEach var="debitoTipoValorLocalidade"
								items="${InserirTipoDebitoActionForm.debitoTipoValorLocalidade}"
								varStatus="i">

								<c:if test="${i.count%2 == '1'}">
									<tr bgcolor="#FFFFFF">
								</c:if>
								<c:if test="${i.count%2 == '0'}">
									<tr bgcolor="#cbe5fe">
								</c:if>
								<td>
								<div align="center">
									<a href="javascript:if(confirm('Confirma remoção?')){removeRowTableDebitoTipoValorLocalidade('${debitoTipoValorLocalidade.localidade.id}');}">
										<img src="imagens/Error.gif" width="14" height="14" border="0" title="Remover"> 
									</a>
								</div>
								</td>
								<td>
								<div align="left">${debitoTipoValorLocalidade.localidade.descricao}</div>
								</td>
								<td>
									<span class="style2">  
										<input type="text" id="valorDebitoLocalidadeAtual" 
											value="${debitoTipoValorLocalidade.valorDebitoTipo}" 
											onkeyup="javaScript:formataValorMonetario(this, 8);"
											onblur= "atualizarColecaoDebitoTipoValorLocalidade('${debitoTipoValorLocalidade.localidade.id}', this);"/>
									</span> 									
								<div align="center"></div>
								</td>
							
							</c:forEach>

						</tbody>
					</table>
					</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left" onclick="window.location.href='<html:rewrite page="/exibirInserirTipoDebitoAction.do?menu=sim"/>'" > <input name="Button" type="button"
						class="bottonRightCol" value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

