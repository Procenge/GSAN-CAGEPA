<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.fachada.Fachada" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--

    var bCancel = false; 
    
    function validateSituacaoEspecialFaturamentoActionForm(form) {                                                                   
        if (bCancel) 
      		return true; 
        else 
       		return validateRequired(form) && validateCaracterEspecial(form) && validateMesAno(form);       	
    } 
    
	function validarForm(form){
		if (form.idFaturamentoSituacaoTipo.value != '-1' && form.idFaturamentoSituacaoTipo.value != ''){
		    if(confirm("Confirma inserção/atualização da situação especial de faturamento para os imóveis listados?")){
		    	submeterFormPadrao(form);
			}		
		} else {
			alert('Para concluir é necessário inserir/manter imóveis na situação.');
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    if (tipoConsulta == 'imoveis') {
			form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?consultarImoveis=S';
			form.submit();
	    }
	    if (tipoConsulta == 'imovel'){
	    	form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?consultarImoveis='+codigoRegistro;
			form.submit();
	    }
	}

	function MesAnoValidations() {
     	this.aa = new Array("mesAnoReferenciaFaturamentoInicial", "Mês e Ano de Referência do Faturamento Inicial inválido.", new Function ("varName", " return this[varName];"));
    	this.ab = new Array("mesAnoReferenciaFaturamentoFinal", "Mês e Ano de Referência do Faturamento Final inválido.", new Function ("varName", " return this[varName];"));
    }

    function caracteresespeciais () { 
     	this.aa = new Array("mesAnoReferenciaFaturamentoInicial", "Mês e Ano de Referência do Faturamento Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     	this.ab = new Array("mesAnoReferenciaFaturamentoFinal", "Mês e Ano de Referência do Faturamento Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 
    
    function required () { 
     	this.aa = new Array("idFaturamentoSituacaoTipo", "Informe Tipo da Situação Especial de Faturamento.", new Function ("varName", " return this[varName];"));
     	this.ab = new Array("idFaturamentoSituacaoMotivo", "Informe Motivo da Situação Especial de Faturamento.", new Function ("varName", " return this[varName];"));
     	this.ac = new Array("mesAnoReferenciaFaturamentoInicial", "Informe Mês e Ano de Referência do Faturamento Inicial.", new Function ("varName", " return this[varName];"));
     	this.ad = new Array("mesAnoReferenciaFaturamentoFinal", "Informe Mês e Ano de Referência do Faturamento Final.", new Function ("varName", " return this[varName];"));
    } 
    
    function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		if(!campo.disabled){
		  if (objeto == null || codigoObjeto == null){
		     if(tipo == "" ){
		     	abrirPopup(url,altura, largura);
		     }else{
			  	abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			 }
		 }else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}else{ 
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		 }
      }
   }
   
   function manterInformarSituacao(){
   		var form = document.forms[0];

   		if (validateSituacaoEspecialFaturamentoActionForm(form)){
   			form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?manterImoveis=manterImoveis';
			form.submit();
   		}	

   		/*
   		if(form.idFaturamentoSituacaoTipo.value != '-1' && form.idFaturamentoSituacaoTipo.value != ''){
   			if(form.idFaturamentoSituacaoMotivo.value == '-1' || form.idFaturamentoSituacaoMotivo.value == ''){
   				alert('Selecione um Motivo de Situação Especial para manter.');
				formulario.idFaturamentoSituacaoMotivo.focus;
   			} else {
				form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?manterImoveis=manterImoveis';
				form.submit();
			}	
   		} else{ 
			alert('Selecione um Tipo de Situação Especial para manter.');
			formulario.idFaturamentoSituacaoTipo.focus;
		}*/
   }
   
   	/* Remove Componente da grid */	
	function remover(){
	    var form = document.forms[0];
		if (confirm ("Confirma remoção?")) {
	    	form.action='exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?removerImovel=';
	    	form.submit();
	    }
	}
	
	function limparImoveis(){
		var form = document.forms[0];
		form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?limparImoveis=limparImoveis';
		form.submit();
	}
	
	function validarInserir(botaoOrigem){
		var form = document.forms[0];
		if (validateSituacaoEspecialFaturamentoActionForm(form)){
			abrirPopupComSubmit(form, 600, 600, botaoOrigem);
		}
	}
	
	function abrirPopupComSubmit(form,altura, largura, botaoOrigem){
		var height = window.screen.height - 160;
	 	var width = window.screen.width;
		var top = (height - altura)/2;
		var left = (width - largura)/2;
		window.open('', 'Pesquisar','top=' + top + ',left='+ left +',location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=' + largura + ',height=' + altura);
		form.target='Pesquisar';
		form.action = 'recuperarDadosInserirImoveisSituacaoEspecialAction.do?retornarTela=exibirSituacaoEspecialFaturamentoInformarAction.do&abrirPopup=S&botaoOrigem=' + botaoOrigem;
		submeterFormPadrao(form);
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
	
    
-->    
</script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="SituacaoEspecialFaturamentoActionForm"
	dynamicJavascript="false" />

</head>

<body leftmargin="5" topmargin="5">

<html:form
	action="/validarSituacaoEspecialFaturamentoInserirAtualizarAction"
	type="gcom.gui.faturamento.SituacaoEspecialFaturamentoActionForm"
	name="SituacaoEspecialFaturamentoActionForm" method="post">

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

			<td width="615" valign="top" class="centercoltext">

				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
						<td class="parabg">Inserir Situação Especial de Faturamento</td>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
					</tr>
				</table>
				<table width="100%" border="0">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="30%"><strong>Tipo da
						Situa&ccedil;&atilde;o Especial de Faturamento:<font
							color="#FF0000">*</font></strong></td>
						<td><html:select property="idFaturamentoSituacaoTipo"
							onchange="javascript:limparImoveis();">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="collectionFaturamentoSituacaoTipo"
								labelProperty="descricao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td width="30%"><strong>Motivo da
						Situa&ccedil;&atilde;o Especial de Faturamento:<font
							color="#FF0000">*</font></strong></td>
						<td><html:select property="idFaturamentoSituacaoMotivo">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="collectionFaturamentoSituacaoMotivo"
								labelProperty="descricao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td width="30%"><strong>M&ecirc;s e Ano de
						Refer&ecirc;ncia do Faturamento Inicial</strong><strong>:<font
							color="#FF0000">*</font></strong></td>
						<td><html:text property="mesAnoReferenciaFaturamentoInicial"
							size="7" maxlength="7" onkeypress="mascaraAnoMes(this, event);" />&nbsp;mm/aaaa
						</td>
					</tr>
					<tr>
						<td width="30%"><strong>M&ecirc;s e Ano de
						Refer&ecirc;ncia do Faturamento Final</strong><strong>:<font
							color="#FF0000">*</font></strong></td>
						<td><html:text property="mesAnoReferenciaFaturamentoFinal"
							size="7" maxlength="7" onkeypress="mascaraAnoMes(this, event);" />&nbsp;mm/aaaa
						</td>
					</tr>
					<tr>
						<td width="30%"><strong>Volume:</strong></td>
						<td><html:text property="volume" size="6" maxlength="6" /></td>
					</tr>
					<tr>
						<td width="30%"><strong>Percentual de Esgoto:</strong></td>
						<td><html:text property="percentualEsgoto" size="6"
							maxlength="6" onkeyup="formataValorMonetario(this, 5)" /></td>
					</tr>
					<tr>
						<td colspan="2" align="right">
							<table>
								<tr>
									<td width="50%"><input type="button" name="Inserir"
										class="bottonRightCol" value="Inserir"
										onClick="javascript:validarInserir('Inserir');" tabindex="7"></td>
									<td align="right" width="50%"><input type="button"
										name="Submit24" class="bottonRightCol" value="Manter"
										onClick="javascript:validarInserir('Manter');" tabindex="7">
										<!-- onClick="javascript:manterInformarSituacao();" tabindex="7"> -->
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" colspan="2">
						<hr>
						</td>
					</tr>
					<tr>
						<td colspan="2"><strong>Imóveis Selecionados<font
							color="#FF0000">*</font></strong></td>
					</tr>
	
					<tr>
						<td colspan="2">
						<table width="100%" border="0" bgcolor="#90c7fc">
							<tr bordercolor="#000000">
								<td width="2%" align="center">&nbsp;</td>
								<td width="2%" align="center"><strong><a href="javascript:facilitador(this);" id="0">Todos</a></strong></td>
								<td width="10%" align="center"><strong>Matrícula</strong></td>
								<td width="10%" align="center"><strong>Tipo</strong></td>
								<td width="17%" align="center"><strong>Motivo</strong></td>
								<td width="10%" align="center"><strong>Ref.Inicial</strong></td>
								<td width="10%" align="center"><strong>Ref.Final</strong></td>
								<td width="10%" align="center"><strong>m³</strong></td>
								<td width="9%" align="center"><strong>%</strong></td>
							</tr>
						</table><div style="width: 100%; height: 100; overflow: auto;"><table>
							<c:set var="count" value="0" />
	
							<logic:iterate id="situacaoEspecialFaturamentoHelper"
								name="SituacaoEspecialFaturamentoActionForm"
								property="colecaoSituacaoEspecialFaturamentoHelper"
								type="gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper"
								scope="session">
	
								<tr>
	
									<c:set var="count" value="${count+1}" />
	
									<c:choose>
										<c:when test="${count%2 == '1'}">
											<tr bgcolor="#FFFFFF">
										</c:when>
										<c:otherwise>
											<tr bgcolor="#cbe5fe">
										</c:otherwise>
									</c:choose>
	
									<td bordercolor="#90c7fc" width="1%">
										<div align="right"><font style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif"> ${count}</font></div>
									</td>
	
									<td bordercolor="#90c7fc" width="1%">
										<div align="center">
											<html:multibox property="itensSelecionados" value="<%=situacaoEspecialFaturamentoHelper.getIdImovel()%>">
											   
											</html:multibox> 
											   
											<%-- <INPUT TYPE="checkbox" NAME="situacaoEspecialFaturamento"  value="<%=situacaoEspecialFaturamentoHelper.getIdFaturamentoSituacaoTipo()%>"/> --%>
										</div>
									</td>
	
									<td bordercolor="#90c7fc" width="10%">
										<div align="center"><font style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<bean:write name="situacaoEspecialFaturamentoHelper" property="idImovel" />
										</font></div>
									</td>
	
									<td bordercolor="#90c7fc" width="10%">
									<div align="center"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.tipoSituacaoEspecialFaturamento != null}">
										<bean:write name="situacaoEspecialFaturamentoHelper"
											property="tipoSituacaoEspecialFaturamento" />
									</c:if></font></div>
									</td>
	
									<td bordercolor="#90c7fc" width="16%">
									<div align="center"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.motivoSituacaoEspecialFaturamento != null}">
										<bean:write name="situacaoEspecialFaturamentoHelper"
											property="motivoSituacaoEspecialFaturamento" />
									</c:if></font></div>
									</td>
	
									<td width="10%"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoInicial != null}">
										<% 
										  if(situacaoEspecialFaturamentoHelper.getMesAnoReferenciaMenorIgualInicial()){ 
											%>
										<input
											name="mesAnoInicial${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="5" maxlength="7"
											value="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoInicial}"
											id="mesAnoInicial" readonly="readonly">
										<%} else { %>
										<input
											name="mesAnoInicial${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="5" maxlength="7"
											value="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoInicial}"
											id="mesAnoInicial" onkeypress="mascaraAnoMes(this, event);"  readonly="readonly">
										<%} %>
									</c:if></font></td>
	
									<td width="10%"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoFinal != null}">
										<% 
											if(situacaoEspecialFaturamentoHelper.getMesAnoReferenciaMenorIgualFinal()){ 
										%>
										<input
											name="mesAnoFinal${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="5" maxlength="7"
											value="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoFinal}"
											readonly="readonly" />
										<%} else { %>
										<input
											name="mesAnoFinal${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="5" maxlength="7"
											value="${situacaoEspecialFaturamentoHelper.mesAnoReferenciaFaturamentoFinal}"
											onkeypress="mascaraAnoMes(this, event);" readonly="readonly" />
										<%} %>
									</c:if></font></td>
	
									<td width="10%"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.volume != null}">
										<input
											name="volume${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="3" maxlength="6"
											value="${situacaoEspecialFaturamentoHelper.volume}"  readonly="readonly"/>
									</c:if> <c:if
										test="${situacaoEspecialFaturamentoHelper.volume == null}">
										<input
											name="volume${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="3" maxlength="6" readonly="readonly" />
									</c:if></font></td>
	
									<td width="9%"><font style="font-size: 9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <c:if
										test="${situacaoEspecialFaturamentoHelper.percentualEsgoto != null}">
										<input
											name="percentualEsgoto${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="3" maxlength="6"
											value="${situacaoEspecialFaturamentoHelper.percentualEsgoto}"
											onkeyup="formataValorMonetario(this, 5)"  readonly="readonly" />
									</c:if> <c:if
										test="${situacaoEspecialFaturamentoHelper.percentualEsgoto == null}">
										<input
											name="percentualEsgoto${situacaoEspecialFaturamentoHelper.idImovel}"
											type="text" size="3" maxlength="6"
											onkeyup="formataValorMonetario(this, 5)"  readonly="readonly" />
									</c:if></font></td>
	
								</tr>
							</logic:iterate>
				
						</table>
						</div>
						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td align="right"><input type="button" class="bottonRightCol"
							value="Remover" onclick="javascript:remover()" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">
						<div align="left"><strong><font color="#FF0000">*</font></strong>
						Campos obrigat&oacute;rios</div>
						</td>
					</tr>
					<tr>
						<td align="left"><input type="button" class="bottonRightCol"
							value="Voltar" onclick="javascript:history.back();" /></td>
	
						<td align="right"><input type="button" class="bottonRightCol"
							value="Concluir" onclick="validarForm(form);" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
