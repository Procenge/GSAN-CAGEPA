<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="ImovelEmissaoOrdensSeletivasActionForm" dynamicJavascript="false" />
<script language="JavaScript">

 var bCancel = false;

function validarForm(form){

  if(validatePesquisarActionForm(form)){
    form.submit();
  }
}

</script>

<script>
<!-- Begin 

     var bCancel = false; 

    function validateImovelEmissaoOrdensSeletivasActionForm(form) {                                                                   
		if (validateInteger(form)) {
			if (parseInt(form.numeroOcorrenciasConsecutivas.value) > 10) {
				alert('O Número de Ocorrências Consecutivas deve ser Menor ou Igual a 10.');
				form.numeroOcorrenciasConsecutivas.focus();
				return false;
			}			

			
			return true;
		}else {
			return false;
		}
	}
	
	function IntegerValidations () { 
		this.aa = new Array("numeroOcorrenciasConsecutivas", "O número de Ocorrências Consecutivas deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	}

    function ControleAnormalidade() {
    	var form = document.forms[0];
		
    	if (form.anormalidadeHidrometro.selectedIndex != 0 && form.anormalidadeHidrometro.selectedIndex != -1) {
    		form.numeroOcorrenciasConsecutivas.disabled = false;
    	}else {
    		form.numeroOcorrenciasConsecutivas.disabled = true;
    		form.numeroOcorrenciasConsecutivas.value = '';
    	}
    }

	function atualizarForm() {
		form = document.forms[0];
		form.action = "/gsan/filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasHidrometro";
		form.submit();
	}

	function removerDadosDoHidrometro(url) {
		if (confirm('Confirma remoção ?')) {
			var form = document.forms[0];
			form.action = url;
			form.submit();
		}
	}

-->    
</script>

</head>

<body leftmargin="5" topmargin="5" onload="ControleAnormalidade();">

<html:form action="/filtrarImovelEmissaoOrdensSeletivasWizardAction"
	name="ImovelEmissaoOrdensSeletivasActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ImovelEmissaoOrdensSeletivasActionForm"
	method="post"
	onsubmit="return validateImovelEmissaoOrdensSeletivasActionForm(this);">

	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=2" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="4" />	
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
			<td width="617" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><html:img src="imagens/parahead_left.gif" border="0" /></td>
					<td class="parabg">Filtrar Imóvel</td>
					<td width="11" valign="top"><html:img
						src="imagens/parahead_right.gif" border="0" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para filtrar o(s) im&oacute;vel(is) pelas
					caracter&iacute;sticas gerais, informe os dados abaixo:</td>
				</tr>

				<tr>
					<td><strong>Marca:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="marca" style="width: 230px;" multiple="true" size="4">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="collectionHidrometroMarca">
												<html:options collection="collectionHidrometroMarca"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
									<html:hidden property="marcaDescricao"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><strong>Classe:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroClasseMetrologica" style="width: 230px;" multiple="true" size="4">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoHidrometroClasseMetrologica">
												<html:options collection="colecaoHidrometroClasseMetrologica"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><strong>Proteção:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroProtecao" style="width: 230px;" multiple="true" size="4">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoHidrometroProtecao">
												<html:options collection="colecaoHidrometroProtecao"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><strong>Local de Instalação:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroLocalInstalacao" style="width: 230px;" multiple="true" size="4">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoHidrometroLocalInstalacao">
												<html:options collection="colecaoHidrometroLocalInstalacao"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><strong>Anormalidade de Hidr&ocirc;metro:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="anormalidadeHidrometro" style="width: 230px;"
											onchange="ControleAnormalidade();" multiple="true" size="4">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="collectionHidrometroAnormalidade">
												<html:options collection="collectionHidrometroAnormalidade"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td width="200"><strong>Num. Ocorr&ecirc;ncias Consecutivas:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td><html:text property="numeroOcorrenciasConsecutivas" size="3" maxlength="2" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table width="100%" border="0">
							<tr>
								<td align="right">
									<html:button styleClass="bottonRightCol" value="Adicionar" property="botaoAdicionar" onclick="javascript:atualizarForm();abrirPopupComSubmit('exibirInserirDadosDoHidrometroPopupAction.do', 270, 630, 'DadosDoHidrometroPopup');"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="3">
									<table width="100%" bgcolor="#90c7fc">
										<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
											<td width="10%"><div align="center"><strong>Remover</strong></div></td>
											<td width="22%" align="center"><strong>Diâmetro</strong></td>
											<td width="22%" align="center"><strong>Capacidade</strong></td>
											<td width="22%" align="center" colspan="2"><strong>Período de Instalação</strong></td>
											<td width="24%" align="center"><strong>Leitura Acumulada</strong></td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td height="83px" colspan="3">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%" align="center" bgcolor="#99CCFF">
											<logic:present property="colecaoDadosDoHidrometro" name="ImovelEmissaoOrdensSeletivasActionForm" scope="session">
												<%int cont=0;%>
												<logic:iterate property="colecaoDadosDoHidrometro" name="ImovelEmissaoOrdensSeletivasActionForm" id="dadosDoHidrometroHelper" type="DadosDoHidrometroHelper" scope="session">
													<%cont = cont+1;
													if (cont%2==0) {%>
													<tr bgcolor="#cbe5fe">
													<%}else{%>
													<tr bgcolor="#FFFFFF">
													<%}%>
														<td width="10%">
															<div align="center">
																<a href="javascript:removerDadosDoHidrometro('removerDadosDoHidrometroAction.do?id=<%=""+dadosDoHidrometroHelper.getId()%>');"><img border="0" src="/gsan/imagens/Error.gif"/></a>
															</div>
														</td>
														<td width="22%">
															<bean:write name="dadosDoHidrometroHelper" property="descricaoHidrometroDiametro"/>
														</td>
														<td width="22%">
															<bean:write name="dadosDoHidrometroHelper" property="descricaoHidrometroCapacidade"/>
														</td>
														<td width="11%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="intervaloInstalacaoInicial"/>
														</td>
														<td width="11%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="intervaloInstalacaoFinal"/>
														</td>
														<td width="24%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="numeroLeituraAcumulada"/>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
										</table>
									</div>
								</td>
							</tr>						
						</table>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<div align="right">
							<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=2" />
						</div>
					</td>
				</tr>
			</table>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	
	<logic:present name="servicoTipo">
		<logic:equal name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = 'none';</script>
		</logic:equal>
		<logic:notEqual name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = '';</script>
		</logic:notEqual>
	</logic:present>
	<logic:notPresent name="servicoTipo">
		<script>document.getElementById('2').style.display = 'none';</script>
	</logic:notPresent>
	
</body>
</html:form>
</html:html>
