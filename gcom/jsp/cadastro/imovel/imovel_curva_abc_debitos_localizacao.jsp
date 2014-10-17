<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ImovelCurvaAbcDebitosActionForm" dynamicJavascript="false" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>cadastro/imovel/imovel_curva_abc_debitos.js"></script>

<script>
	var bCancel = false;
	var estadoCampo = ''; 

    function validateImovelCurvaAbcDebitosActionForm(form) {
        if (bCancel) 
      		return true; 
        else 
			return validateCaracterEspecial(form) && validateLong(form)
		       && testarCampoValorZero(document.ImovelCurvaAbcDebitosActionForm.idLocalidadeInicial, 'Localidade Inicial')
		       && testarCampoValorZero(document.ImovelCurvaAbcDebitosActionForm.codigoSetorComercialInicial, 'Setor Comercial Inicial')
		       && testarCampoValorZero(document.ImovelCurvaAbcDebitosActionForm.idLocalidadeFinal, 'Localidade Final')
		       && testarCampoValorZero(document.ImovelCurvaAbcDebitosActionForm.codigoSetorComercialFinal, 'Setor Comercial Final')
			   && validarLocalidadeDiferentes()
			   && validarSetoresComerciaisDiferentes()
			   && verificarSetoresComerciaisMenores()
			   && verificarLocalidadeMenores();
   	} 

    function caracteresespeciais () { 
	     this.aa = new Array("idLocalidadeInicial", "Localidade Inicial deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ab = new Array("codigoSetorComercialInicial", "Setor Comercial  Inicial deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ac = new Array("idLocalidadeFinal", "Localidade Final deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ad = new Array("codigoSetorComercialFinal", "Setor Comercial Final deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
    }
    
    function IntegerValidations () { 
	     this.aa = new Array("idLocalidadeInicial", "Localidade Inicial deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ab = new Array("codigoSetorComercialInicial", "Setor Comercial Inicial deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ac = new Array("idLocalidadeFinal", "Localidade Final deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     this.ad = new Array("codigoSetorComercialFinal", "Setor Comercial Final deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
    } 
    
	function limparForm(){
		var form = document.forms[0];
	}

</script>

</head>
<body leftmargin="5" topmargin="5" onload="controleClassificacao(); //setarFoco('${requestScope.nomeCampo}');">


<html:form action="/filtrarImovelCurvaAbcDebitosWizardAction"
	type="gcom.gui.cadastro.imovel.ImovelCurvaAbcDebitosActionForm"
	onsubmit="return validateImovelCurvaAbcDebitosActionForm(this);"
	name="ImovelCurvaAbcDebitosActionForm" method="post">
	
	<input type="hidden" name="classificacao" value="${requestScope.classificacao}"/>
	
	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=2" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="2" />
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

				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
						<td class="parabg">Filtrar Im�vel</td>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
					</tr>
				</table>
				<p>&nbsp;</p>
			
				<table width="100%" border="0">
					<tr>
						<td colspan="2">Para filtrar o(s) im&oacute;vel(is) pela localiza&ccedil;&atilde;o, informe os dados abaixo:</td>
					</tr>

					<tr>
						<td width="160" valign="top"><strong>Ger&ecirc;ncia Regional:</strong></td>
						<td align="left">
							<html:select property="idGerenciaRegional" tabindex="3" onchange="controleClassificacao();">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoGerenciasRegionais"
									labelProperty="nome" property="id" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td width="160" valign="top"><strong>Unidade de Neg&oacute;cio:</strong></td>
						<td align="left">
							<html:select property="idUnidadeNegocio" tabindex="3" onchange="controleClassificacao();">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoUnidadesNegocios"
									labelProperty="nome" property="id" />
							</html:select>
						</td>
					</tr>
					
					<tr><td colspan="2"><hr></td></tr>

					<tr>
						<td width="160" valign="top" colspan="2"><strong>Intervalo Inicial</strong></td>
					</tr>
					<tr>
						<td><strong>Localidade:</strong></td>
						<td>
							<html:text tabindex="2" maxlength="3" property="idLocalidadeInicial" size="5"
								onkeypress="form.target=''; limparDestino(1); validaEnter(event, 'filtrarImovelCurvaAbcDebitosWizardAction.do?action=exibirFiltrarImovelCurvaAbcDebitosLocalizacao&objetoConsulta=1&inscricaoTipo=origem', 'idLocalidadeInicial');"
								onclick="javascript:validarLocalidade();" onblur="javascript:duplicarLocalidade();" />
							<a href="javascript:chamarPopup1('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, ''); limparOrigem(1);" id="btPesqLocalidadeInicial">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar" /></a>
							<logic:present name="corLocalidadeOrigem">
								<logic:equal name="corLocalidadeOrigem" value="exception">
									<html:text property="nomeLocalidadeInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corLocalidadeOrigem" value="exception">
									<html:text property="nomeLocalidadeInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present>
							
							<logic:notPresent name="corLocalidadeOrigem">
								<logic:empty name="ImovelCurvaAbcDebitosActionForm"
									property="idLocalidadeInicial">
									<html:text property="nomeLocalidadeInicial" value="" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>

								<logic:notEmpty name="ImovelCurvaAbcDebitosActionForm"
									property="idLocalidadeInicial">
									<html:text property="nomeLocalidadeInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: 	#000000" />
								</logic:notEmpty>
							</logic:notPresent>
							
							<a href="javascript:limparBorrachaOrigem(1);">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a>
						</td>
					</tr>
					<tr>
						<td><strong>Setor Comercial:</strong></td>
						<td>
							<html:text tabindex="3" maxlength="3"
							property="codigoSetorComercialInicial" size="5"
							onkeyup="limparOrigem(2);"
							onkeypress="form.target=''; limparDestino(2); validaEnterDependencia(event, 'filtrarImovelCurvaAbcDebitosWizardAction.do?action=exibirFiltrarImovelCurvaAbcDebitosLocalizacao&objetoConsulta=2&inscricaoTipo=origem', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial.');"
							onblur="javascript:duplicarSetorComercial();" />
							<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.ImovelCurvaAbcDebitosActionForm.idLocalidadeInicial.value, 275, 480, 'Informe Localidade Inicial.'); limparOrigem(2);" id="btPesqSetorComercialInicial">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar" />
							</a>
							
							<logic:present name="corSetorComercialOrigem">
								<logic:equal name="corSetorComercialOrigem" value="exception">
									<html:text property="nomeSetorComercialInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
	
								<logic:notEqual name="corSetorComercialOrigem" value="exception">
									<html:text property="nomeSetorComercialInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
	
							</logic:present>
							
							<logic:notPresent name="corSetorComercialOrigem">
								<logic:empty name="ImovelCurvaAbcDebitosActionForm" property="codigoSetorComercialInicial">
									<html:text property="nomeSetorComercialInicial" value="" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="ImovelCurvaAbcDebitosActionForm" property="codigoSetorComercialInicial">
									<html:text property="nomeSetorComercialInicial" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent>
							<a href="javascript:limparBorrachaOrigem(2);">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
							</a>
							<html:hidden property="idSetorComercialInicial"/>
						</td>
					</tr>
				
					<tr><td colspan="2"><hr></td></tr>
					<tr>
						<td width="160" valign="top" colspan="2"><strong>Intervalo Final</strong></td>
					</tr>
					<tr>
						<td><strong>Localidade:</strong></td>
						<td>
							<html:text maxlength="3" property="idLocalidadeFinal" size="5"
								onkeyup="desabilitaIntervaloDiferente(1);" onkeypress="limparDestino(1); validaEnter(event,'filtrarImovelCurvaAbcDebitosWizardAction.do?action=exibirFiltrarImovelCurvaAbcDebitosLocalizacao&objetoConsulta=1&inscricaoTipo=destino', 'idLocalidadeFinal');"
								tabindex="6" />
							<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, ''); limparDestino(1);" id="btPesqLocalidadeFinal">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar" />
							</a>
							<logic:present name="corLocalidadeDestino">
								<logic:equal name="corLocalidadeDestino" value="exception">
									<html:text property="nomeLocalidadeFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corLocalidadeDestino" value="exception">
									<html:text property="nomeLocalidadeFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present>
							
							<logic:notPresent name="corLocalidadeDestino">
								<logic:empty name="ImovelCurvaAbcDebitosActionForm" property="idLocalidadeFinal">
									<html:text property="nomeLocalidadeFinal" value="" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="ImovelCurvaAbcDebitosActionForm" property="idLocalidadeFinal">
									<html:text property="nomeLocalidadeFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: 	#000000" />
								</logic:notEmpty>
							</logic:notPresent>
							
							<a href="javascript:limparBorrachaDestino(1);">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
							</a>
						</td>
					</tr>
					<tr>
						<td><strong>Setor Comercial :</strong></td>
						<td>
							<html:text maxlength="3" property="codigoSetorComercialFinal" size="5"
								onkeyup="limparDestino(2); //desabilitaIntervaloDiferente(2);"
								onkeypress="form.target=''; validaEnterDependencia(event, 'filtrarImovelCurvaAbcDebitosWizardAction.do?action=exibirFiltrarImovelCurvaAbcDebitosLocalizacao&objetoConsulta=2&inscricaoTipo=destino', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final.');"
								tabindex="7" />
							<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.ImovelCurvaAbcDebitosActionForm.idLocalidadeFinal.value, 275, 480, 'Informe Localidade Final.'); limparDestino(2);" id="btPesqSetorComercialFinal">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar" />
							</a>
							
							<logic:present name="corSetorComercialDestino">
								<logic:equal name="corSetorComercialDestino" value="exception">
									<html:text property="nomeSetorComercialFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>

								<logic:notEqual name="corSetorComercialDestino" value="exception">
									<html:text property="nomeSetorComercialFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present>
							
							<logic:notPresent name="corSetorComercialDestino">
								<logic:empty name="ImovelCurvaAbcDebitosActionForm" property="codigoSetorComercialFinal">
									<html:text property="nomeSetorComercialFinal" value="" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="ImovelCurvaAbcDebitosActionForm" property="codigoSetorComercialFinal">
									<html:text property="nomeSetorComercialFinal" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent>
							
							<a href="javascript:limparBorrachaDestino(2); //limparPesquisaQuadraFinal();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a>
							<html:hidden property="idSetorComercialFinal" />
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<div align="right">
								<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=2" />
							</div>
						</td>
					</tr>

				</table>
				<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
<logic:present name="classificacao">
	<logic:equal name="classificacao" value="REGIONAL">
		<script>
			var form = document.ImovelCurvaAbcDebitosActionForm;
			
			form.idLocalidadeInicial.disabled = true;
			form.idLocalidadeInicial.value = '';
			form.nomeLocalidadeInicial.value = '';
			document.getElementById('btPesqLocalidadeInicial').style.display = 'none';
			
			form.idLocalidadeFinal.disabled = true;
			form.idLocalidadeFinal.value = '';
			form.nomeLocalidadeFinal.value = '';
			document.getElementById('btPesqLocalidadeFinal').style.display = 'none';
			
			form.codigoSetorComercialInicial.disabled = true;
			form.codigoSetorComercialInicial.value = '';
			form.nomeSetorComercialInicial.value = '';
			document.getElementById('btPesqSetorComercialInicial').style.display = 'none';
			
			form.codigoSetorComercialFinal.disabled = true;
			form.codigoSetorComercialFinal.value = '';
			form.nomeSetorComercialFinal.value = '';
			document.getElementById('btPesqSetorComercialFinal').style.display = 'none';
		</script>
	</logic:equal>
	<!-- 
	<logic:equal name="classificacao" value="LOCAL">
		<script>
			var form = document.ImovelCurvaAbcDebitosActionForm;
			
			form.gerenciaRegional.value = "-1";
			form.gerenciaRegional.disabled = true;
			//form.gerenciaRegional[form.gerenciaRegional.selectedIndex].selected = false;
		</script>
	</logic:equal>
	
	<logic:equal name="classificacao" value="SETORCOMERCIAL">
		<script>
			var form = document.ImovelCurvaAbcDebitosActionForm;
			
			form.gerenciaRegional.value = "-1";
			form.gerenciaRegional.disabled = true;
			//form.gerenciaRegional[form.gerenciaRegional.selectedIndex].selected = false;
		</script>
	</logic:equal>
	-->
</logic:present>
</body>
</html:html>
