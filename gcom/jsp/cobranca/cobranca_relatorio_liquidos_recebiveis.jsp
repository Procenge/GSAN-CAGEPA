<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="FiltroRelatorioLiquidosRecebiveisActionForm"  dynamicJavascript="true" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validaForm(form){

		if(validarDatas() && validarIntervalos()){
			submeterFormPadrao(form);
		}
	}

	function validarDatas(){

		var form = document.forms[0];
		
		var periodoInicio = form.periodoInicial.value;
		var periodoFim = form.periodoFim.value;

		if (isBrancoOuNulo(periodoInicio) && isBrancoOuNulo(periodoFim)){
			alert("Informe:\no Período");
			return false;
		} else if (isBrancoOuNulo(periodoInicio)){
			alert("Informe:\nData do Pagamento Inicial");
			return false;
		} else if (isBrancoOuNulo(periodoFim)){
			alert("Informe:\nData do Pagamento Final");
			return false;
		}

		return true;

	}

	function validarIntervalos(){

		var form = document.forms[0];
		
		var periodoInicio = form.periodoInicial.value;
		var periodoFim = form.periodoFim.value;

		// Verificamos se a data final informada é menor que a data inicial
	    if (comparaData(periodoFim, '<', periodoInicio)){
			  alert("A data pagamento final deve ser maior que a data pagamento inicial");
			  return false;
	    }
	    return true;
	}

	function limparForm(form) {

		var form = document.forms[0];
		form.periodoInicial.value = "";
		form.periodoFim.value = "";
		form.comando.value = "3";
		redirecionarSubmit('exibirFiltrarRelatorioLiquidosRecebiveisAction.do?menu=sim&limpar=true');
	}


	function duplicarData(){
		var formulario = document.forms[0]; 
		formulario.periodoFim.value = formulario.periodoInicial.value;
	}  

	
</script>


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioLiquidosRecebiveisAction" name="FiltroRelatorioLiquidosRecebiveisActionForm" 
		   type="gcom.gui.cobranca.FiltroRelatorioLiquidosRecebiveisActionForm" method="post">

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

				<!--Início Tabela Reference a Páginação da Tela de Processo-->
				
				<table>
					<tr>
						<td></td>
					</tr>
				</table>
				
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
						<td class="parabg">Filtrar Relatório de Líquidos Recebíveis</td>
						<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
					</tr>
	
				</table>
				
				<!--Fim Tabela Reference a Paginação da Tela de Processo-->
				<p>&nbsp;</p>
				<table width="100%" border="0">
					<tr>
						<td width="100%" colspan=2>
							<table width="100%" border="0">
								<tr>
									<td>Para filtrar os dados do Relatório de Líquidos Recebíveis, informe os dados abaixo:</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td><strong>Opção de geração: <font color="#FF0000">*</font></strong></td>
						<td>
							<span class="style2"> 					
								<html:radio property="comando" tabindex="1" value="1" /><strong>Analítico</strong>
								<html:radio property="comando" tabindex="2" value="2" /><strong>Sintético</strong>
								<html:radio property="comando" tabindex="3" value="3" /><strong>Ambos</strong>
	 						</span>
	 					</td>
					</tr>

					<tr>
						<td><strong>Data do Pagamento Inicial:<font color="#FF0000">*</font></strong></td>
						<td>
							<strong> 
								<html:text maxlength="10" property="periodoInicial" size="10" tabindex="7" 
									onkeypress="return isCampoNumerico(event);" 
									onkeyup="javascript:mascaraData(this, event);replicarCampo(document.forms[0].periodoFim,this);"
									onblur="duplicarData();" /> 
								 <a id="linkPeriodoInicio" href="javascript:abrirCalendario('FiltroRelatorioLiquidosRecebiveisActionForm', 'periodoInicial')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>&nbsp;(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					<tr>
						<td><strong>Data do Pagamento Final:<font color="#FF0000">*</font></strong></td>
						<td>
							<strong> 
								<html:text maxlength="10" property="periodoFim" size="10" tabindex="8" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this, event);" /> 
								  <a id="linkPeriodoFim" href="javascript:abrirCalendario('FiltroRelatorioLiquidosRecebiveisActionForm', 'periodoFim')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>
				   			(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					
					
			
                <tr>
					<td>
						<strong> 
							<font color="#FF0000"> 
								<input type="button" name="Submit22" class="bottonRightCol" value="Desfazer" onClick="javascript:limparForm(document.forms[0]);">
								
								<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font> 
						</strong>
					</td>
					<td align="right">
						<input type="button" name="Submit2" class="bottonRightCol" value="Gerar Relatorio" onclick="validaForm(document.forms[0]);">
					</td>
				</tr>
				</table>
				<p>&nbsp;</p>
			</tr>
			<!-- Rodapé -->
			<%@ include file="/jsp/util/rodape.jsp"%>
		</table>
		<p>&nbsp;</p>
	<tr>
	</tr>

</html:form>
</body>
</html:html>