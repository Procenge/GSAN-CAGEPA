<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

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
function validarForm(form){
	if (validateSituacaoEspecialCobrancaActionForm(form)){
	     if(verificaAnoMesMensagemPersonalizada(form.mesAnoReferenciaCobrancaInicial,"M�s Ano de Refer�ncia de Cobran�a Inicial inv�lido.") 
	     && verificaAnoMesMensagemPersonalizada(form.mesAnoReferenciaCobrancaFinal,"M�s Ano de Refer�ncia de Cobran�a Final inv�lido.")){
	    	if(confirm("Confirma inser��o da situa��o especial de cobran�a para " + form.quantidadeImoveisSEMSituacaoEspecialCobranca.value + " im�vel(eis) ?")){
	    	submeterFormPadrao(form);
			}
		}	
	}

}

//-->
</SCRIPT>
<script>
<!-- Begin 

     var bCancel = false; 

    function validateSituacaoEspecialCobrancaActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateRequired(form) && validateCaracterEspecial(form); 
   } 

    function caracteresespeciais () { 
     this.aa = new Array("mesAnoReferenciaCobrancaInicial", "M�s Ano de Refer�ncia de Cobran�a Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("mesAnoReferenciaCobrancaFinal", "M�s Ano de Refer�ncia de Cobran�a Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     
    } 
    
    function required () { 
     this.aa = new Array("idCobrancaSituacaoTipo", "Informe Tipo da Situa��o Especial de Cobran�a.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("idCobrancaSituacaoMotivo", "Informe Motivo da Situa��o Especial de Cobran�a.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("mesAnoReferenciaCobrancaInicial", "Informe M�s e Ano de Refer�ncia de Cobran�a Inicial.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("mesAnoReferenciaCobrancaFinal", "Informe M�s e Ano de Refer�ncia de Cobran�a Final.", new Function ("varName", " return this[varName];"));
    } 
    
     
    
-->    
</script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="SituacaoEspecialCobrancaActionForm"
	dynamicJavascript="false" />

</head>

<body leftmargin="5" topmargin="5">


<html:form action="/validarSituacaoEspecialCobrancaInserirAction"
	type="gcom.gui.cobranca.SituacaoEspecialCobrancaActionForm"
	name="SituacaoEspecialCobrancaActionForm" method="post">

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
					<td class="parabg">Inserir Situa��o Especial de Cobranca</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="6">Par&acirc;metros Informados:</td>
				</tr>
				<tr>
					<td><strong>Matr&iacute;cula:</strong></td>
					<td colspan="5"><html:text property="idImovel" size="8"
						maxlength="8" readonly="true" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td width="10%"><strong>Localidade </strong></td>
					<td width="6%"><strong>Setor</strong></td>
					<td width="7%"><strong>Quadra</strong></td>
					<td width="4%"><strong>Lote</strong></td>
					<td width="20%"><strong>Sublote</strong></td>
				</tr>
				<tr>
					<td><strong>Inscri&ccedil;&atilde;o Incial:</strong></td>
					<td><html:text property="localidadeOrigemID" size="3" maxlength="3"
						readonly="true" /></td>
					<td><html:text property="setorComercialOrigemCD" size="3"
						maxlength="3" readonly="true" /> <html:hidden
						property="setorComercialOrigemID" /></td>
					<td><html:text property="quadraOrigemNM" size="4" maxlength="5"
						readonly="true" /> <html:hidden property="quadraOrigemID" /></td>
					<td><html:text property="loteOrigem" size="3" maxlength="3"
						readonly="true" /></td>
					<td><html:text property="subloteOrigem" size="3" maxlength="3"
						readonly="true" /></td>
				</tr>
				<tr>
					<td><strong>Inscri&ccedil;&atilde;o Final:</strong></td>
					<td><html:text property="localidadeDestinoID" size="3"
						maxlength="3" readonly="true" /></td>
					<td><html:text property="setorComercialDestinoCD" size="3"
						maxlength="3" readonly="true" /> <html:hidden
						property="setorComercialDestinoID" /></td>
					<td><html:text property="quadraDestinoNM" size="4" maxlength="5"
						readonly="true" /> <html:hidden property="quadraDestinoID" /></td>
					<td><html:text property="loteDestino" size="3" maxlength="3"
						readonly="true" /></td>
					<td><html:text property="subloteDestino" size="3" maxlength="3"
						readonly="true" /></td>
				</tr>
				
				<tr>
					<td colspan="6" HEIGHT="10"></td>
				</tr>
				
				<!-- ROTA INICIAL -->
				<tr>
					<td>&nbsp;</td>
					<td width="10%"><strong>C�digo </strong></td>
					<td width="6%"><strong>Sequencial </strong></td>
					<td width="7%">&nbsp;</td>
					<td width="4%">&nbsp;</td>
					<td width="20%">&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Rota Inicial:</strong></td>
					<td><html:text property="cdRotaInicial" size="7"
						maxlength="4" readonly="true" /></td>
					<td><html:text property="sequencialRotaInicial" size="7"
						maxlength="6" readonly="true" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<!-- FIM ROTA INICIAL -->
				
				<!-- ROTA FINAL -->
				<tr>
					<td><strong>Rota Final:</strong></td>
					<td><html:text property="cdRotaFinal" size="7"
						maxlength="4" readonly="true" /></td>
					<td><html:text property="sequencialRotaFinal" size="7"
						maxlength="6" readonly="true" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<!-- FIM ROTA FINAL -->
				
				
				<tr>
					<td colspan="6">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Quantidade de im&oacute;veis que ser&atilde;o
					atualizados:</strong></td>
					<td colspan="5" align="right">
					<div align="left"><html:text property="quantidadeImoveisSEMSituacaoEspecialCobranca"
						size="8" maxlength="8" readonly="true" /></div>
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<table>
						<tr>
							<td colspan="2">Para inserir/atualizar a situa&ccedil;&atilde;o
							especial de cobran�a do(s) im&oacute;vel(is), informe os dados
							abaixo:</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="30%"><strong>Tipo da Situa&ccedil;&atilde;o Especial
							de Cobran�a:<font color="#FF0000">*</font></strong></td>
							<td><html:select property="idCobrancaSituacaoTipo">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="collectionCobrancaSituacaoTipo"
									labelProperty="descricao" property="id" />
							</html:select></td>
						</tr>
						<tr>
							<td width="30%"><strong>Motivo da Situa&ccedil;&atilde;o Especial
							de Cobran�a:<font color="#FF0000">*</font></strong></td>
							<td><html:select property="idCobrancaSituacaoMotivo">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="collectionCobrancaSituacaoMotivo"
									labelProperty="descricao" property="id" />
							</html:select></td>
						</tr>
						<tr>
							<td width="30%"><strong>M&ecirc;s e Ano de Refer&ecirc;ncia de
							Cobran�a Inicial</strong><strong>:<font color="#FF0000">*</font></strong></td>
							<td><html:text property="mesAnoReferenciaCobrancaInicial"
								size="7" maxlength="7" onkeypress="mascaraAnoMes(this, event);" /> mm/aaaa
							</td>
						</tr>
						<tr>
							<td width="30%"><strong>M&ecirc;s e Ano de Refer&ecirc;ncia de
							Cobran�a Final</strong><strong>:<font color="#FF0000">*</font></strong></td>
							<td><html:text property="mesAnoReferenciaCobrancaFinal" size="7"
								maxlength="7" onkeypress="mascaraAnoMes(this, event);" /> mm/aaaa
							</td>
						</tr>
						<tr>
							<td><strong>Numero do processo juridico:</strong> </td>
							<td><html:text property="numeroProcesso" /></td>
						</tr>
						<tr>
							<td><strong>Org�o:</strong></td>
							<td><html:select property="orgao" >
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoOrgaoExterno" labelProperty="descricao" property="id" />
								</html:select></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="right">
							<div align="left"><strong><font color="#FF0000">*</font></strong>
							Campos obrigat&oacute;rios</div>
							</td>
						</tr>
						
						<tr>
							<td colspan="1" align="left"><input type="button"
								class="bottonRightCol" value="Voltar"
								onclick="javascript:history.back();" /></td>
							
							<td colspan="5" align="right">
							  <gcom:controleAcessoBotao name="Button" value="Concluir" onclick="validarForm(form);" url="exibirSituacaoEspecialCobrancaInserirAction.do"/>
							  <%-- <input type="button" class="bottonRightCol" value="Concluir" onclick="validarForm(form);" />--%>
							</td>
						</tr>

						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>

					</td>
				</tr>
			</table>

			<p>&nbsp;</p>
			</td>
		</tr>


	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
</html:html>
