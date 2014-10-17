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
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="RelatorioPagamentosNaoClassificadosForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript">
	
	function replicarBanco(){

		var formulario = document.forms[0];
		
		formulario.P_BC_DP_ARRECADACAO_FIN.value = formulario.P_BC_DP_ARRECADACAO_INI.value;
		
		formulario.P_BC_DP_ARRECADACAO_FIN.focus();
	}

	function replicarAgencia(){

		var formulario = document.forms[0];
		
		formulario.P_AG_DP_ARRECADACAO_FIN.value = formulario.P_AG_DP_ARRECADACAO_INI.value;
		
		formulario.P_AG_DP_ARRECADACAO_FIN.focus();
	}

	function validarForm(){
		
		var form = document.forms[0];
		if(campoObrigatorio()){
			document.forms[0].submit();
		}
	}

	function limpar(){
		var formulario = document.forms[0];
		formulario.P_AM_ARRECADACAO.value = '';
		formulario.P_BC_DP_ARRECADACAO_INI.value = '';
		formulario.P_BC_DP_ARRECADACAO_FIN.value = '';
		formulario.P_AG_DP_ARRECADACAO_INI.value = '';
		formulario.P_AG_DP_ARRECADACAO_FIN.value = '';
		formulario.P_ST_PG_INI.value = "-1";
		formulario.P_ST_PG_FIN.value = "-1";
		
	}

	function campoObrigatorio(){       
		var form = document.forms[0];
		var msg = "";
		var retorno = true;


		if(form.P_AM_ARRECADACAO.value.length < 1){
			msg = "Informe a Referência da Arrecadação";
			form.P_AM_ARRECADACAO.focus();
			alert(msg);
			return false;
		}

		if(form.P_BC_DP_ARRECADACAO_INI.value.length < 1 || form.P_BC_DP_ARRECADACAO_FIN.value.length < 1){
			msg = "Informe o Código do Banco";
			form.P_BC_DP_ARRECADACAO_INI.focus();
			alert(msg);
			return false;
		} else{
			retorno = validarInicioFim(form.P_BC_DP_ARRECADACAO_INI,form.P_BC_DP_ARRECADACAO_FIN);
			if(!retorno){
				return retorno;
			}
		}

		if(form.P_AG_DP_ARRECADACAO_INI.value.length < 1 || form.P_AG_DP_ARRECADACAO_FIN.value.length < 1){
			msg = "Informe o Código da Agência";
			form.P_AG_DP_ARRECADACAO_INI.focus();
			alert(msg);
			return false;
		} else{
			retorno = validarInicioFim(form.P_AG_DP_ARRECADACAO_INI,form.P_AG_DP_ARRECADACAO_FIN);
			if(!retorno){
				return retorno;
			}
		}

		if(form.P_ST_PG_INI.value == -1 || form.P_ST_PG_FIN.value == -1){
			msg = "Informe Situação do Pagamento";
			form.P_ST_PG_INI.focus();
			alert(msg);
			return false;
		}else{
			retorno = validarInicioFim(form.P_ST_PG_INI,form.P_ST_PG_FIN);
			if(!retorno){
				return retorno;
			}
		}

		return retorno;
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5" >

<html:form action="/gerarRelatorioPagamentosNaoClassificadosAction"
	name="RelatorioPagamentosNaoClassificadosForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<input type="hidden" name="acao" value="gerarRelatorio" />
<input type="hidden" name="relatorio" value="RelatorioPagamentosNaoClassificados.rpt" />

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Gerar Relat&oacute;rio  de Pagamentos Não Classificados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>
				
				<tr>
         			 <td width="30%"><strong>Referência da Arrecadação:<font
						color="#FF0000">*</font></strong></td>
          			<td width="70%">
          				<html:text property="P_AM_ARRECADACAO" size="7"  maxlength="7" 
          						   onkeypress="javascript: return isCampoNumerico(event);"
          						   onkeyup="javascript:mascaraAnoMes(this, event);" 
          						   onblur="javascript:return verificaAnoMes(this);"/>
         					&nbsp;mm/aaaa
          			</td>
        		</tr>
				
				<tr>
					<td><strong>C&oacute;digo do Banco Inicial:<font
						color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="P_BC_DP_ARRECADACAO_INI" 
							size="3"
							onkeypress="javascript:return isCampoNumerico(event);"
							onblur="javascript:replicarBanco();"
							/>
							
					</td>
				</tr>
				
				<tr>
					<td><strong>C&oacute;digo do Banco Final:<font
						color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="2"
							property="P_BC_DP_ARRECADACAO_FIN" 
							size="3"
							onkeypress="javascript:return isCampoNumerico(event);"/>
							
					</td>
				</tr>
				
				<tr>
					<td><strong>C&oacute;digo agência para depósito da arrecadação Inicial:<font
						color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							tabindex="2"
							property="P_AG_DP_ARRECADACAO_INI" 
							size="5"
							onkeypress="javascript:return isCampoNumerico(event);"
							onblur="javascript:replicarAgencia();"
							/>
							
					</td>
				</tr>
				
				<tr>
					<td><strong>C&oacute;digo agência para depósito da arrecadação Final:<font
						color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							tabindex="2"
							property="P_AG_DP_ARRECADACAO_FIN" 
							size="5"
							onkeypress="javascript:return isCampoNumerico(event);"/>
							
					</td>
				</tr>
							<tr>
								<td width="30%"><strong>Situação do Pagamento Inicial:<font
						color="#FF0000">*</font></strong></td>
								<td>
									<strong> 
									<html:select 
										property="P_ST_PG_INI" 
										style="width: 230px;"
										>
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoPagamentoSituacao" scope="request">
										<html:options collection="colecaoPagamentoSituacao"
											labelProperty="descricao" property="id" />
									</logic:present>
									</html:select>
									</strong>
								</td>
							</tr>
							<tr>
								<td width="30%"><strong>Situação do Pagamento Final:<font
						color="#FF0000">*</font></strong></td>
								<td>
									<strong> 
									<html:select 
										property="P_ST_PG_FIN" 
										style="width: 230px;"
										>
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoPagamentoSituacao"  scope="request">
										<html:options collection="colecaoPagamentoSituacao"
											labelProperty="descricao" property="id" />
									</logic:present>
									</html:select>
									</strong>
								</td>
							</tr>
				
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar()"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm()" />
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
