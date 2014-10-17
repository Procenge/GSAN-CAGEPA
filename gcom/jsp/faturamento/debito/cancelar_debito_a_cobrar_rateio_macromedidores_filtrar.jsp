<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
	<head>
		<%@ include file="/jsp/util/titulo.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<html:javascript staticJavascript="false" formName="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" />
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
		<script language="JavaScript">
			function desfazer(){
				window.location.href='/gsan/exibirFiltrarCancelarDebitoACobrarRateioMacromedidoresAction.do?menu=sim';
			}
			
			function validarForm(){
				var form = document.forms[0];
				if (validarCampos(form)){
					form.submit();
				}
			}
			
			function validarCampos(form){
				if(form.matriculaImovel.value == null || form.matriculaImovel.value == ''){
					alert('Matrícula imóvel é obrigatória.');
					return false;
				}
				if(form.mesAnoReferenciaFaturamento.value == null || form.mesAnoReferenciaFaturamento.value == ''){
					alert('Referência do faturamento é obrigatória.');
					return false;
				}
				if(form.idTipoLigacao.value == null || form.idTipoLigacao.value == ''){
					alert('Ligação tipo é obrigatório.');
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
		<html:form action="/filtrarCancelarDebitoACobrarRateioMacromedidoresAction.do" method="post"
				name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm"
				type="gcom.gui.faturamento.debito.FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm">
			<%@ include file="/jsp/util/cabecalho.jsp"%>
			<%@ include file="/jsp/util/menu.jsp"%>
	
			<table width="770" border="0" cellspacing="4" cellpadding="0">
	
				<tr>
					<td width="149" valign="top" class="leftcoltext">
						<div align="center">
							<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
							<%@ include file="/jsp/util/mensagens.jsp"%>
						</div>
					</td>
					<td width="600" valign="top" class="centercoltext">
						<p>&nbsp;</p>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
								<td class="parabg">Cancelar D&eacute;bito a Cobrar Rateio Macromedidores</td>
								<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
							</tr>
						</table>
						<p>&nbsp;</p>
						
						<table width="100%" border="0">
							<tr>
								<td colspan="4">Para Filtrar o(s) Im&oacute;vel(eis) Condom&iacute;nios, informe os dados abaixo:</td>
								<td align="right"></td>
							</tr>
						</table>
							
						<table width="100%" border="0">

						<tr>
							<td width="200"><strong>Matrícula do Imóvel
									Condomínio:<font color="#FF0000">*</font>
							</strong></td>
							<td colspan="3" align="left"><html:text
									property="matriculaImovel" maxlength="9" size="9"
									onkeypress="validaEnterComMensagem(event, 'exibirFiltrarCancelarDebitoACobrarRateioMacromedidoresAction.do','matriculaImovel','Matrícula do Imóvel Condomínio');" />
								<a
								href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" />
							</a>
						</tr>
						<tr>
								<td  width="200"><strong>Mês/Ano faturamento:<strong><font color="#FF0000">*</font></strong></strong></td>
								<td colspan="3" align="right">
									<div align="left">
										<span class="style1"> 
											<html:text property="mesAnoReferenciaFaturamento" maxlength="7" size="7" onkeyup="mascaraAnoMes(this, event)"/> 
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<td  width="200"><strong>Tipo de Ligação:<strong><font color="#FF0000">*</font></strong></strong></td>
								<td align="right">
									<div align="left">
										<span class="style1">
											<html:select property="idTipoLigacao" style="width: 200px; " tabindex="11" disabled="true"size="1">
												<html:options collection="colecaoLigacaoTipo" labelProperty="descricao" property="id"/>
											</html:select>
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="style1">&nbsp;</td>
								<td colspan="3" class="style1">&nbsp;</td>
							</tr>
							<tr bordercolor="#90c7fc">
								<td colspan="4">
									<table width="100%">
										<tr>
											<td valign="top"><input name="button" type="button"
												class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;<input type="button"
												name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
												onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'"></td>
											<td>
											<div align="right"><input name="cancelar" type="button"
												class="bottonRightCol" value="Filtrar"
												onclick="javascript:validarForm();"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<p>&nbsp;</p>
						<%@ include file="/jsp/util/rodape.jsp"%>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html:html>

