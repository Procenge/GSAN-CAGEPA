<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema" %>

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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="GerarRelatorioContasEmitidasDynaForm" />
<script>

function validarForm(){
	
	var form = document.forms[0];
	if(campoObrigatorio()){
		document.forms[0].submit();
	}
}
function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipoConsulta=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipoConsulta=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}
function campoObrigatorio(){       
	var form = document.forms[0];
	var msg = "";
			
	if(form.mesAno.value.length < 1){
		msg = "Informe a Referência.";
		form.mesAno.focus();
		alert(msg);
		return false;
	}
	if(form.localidade.value.length < 1){
		msg = "Informe a Localidade.";
		form.localidade.focus();
		alert(msg);
		return false;
	}
	if(isNaN(form.localidade.value)){
		msg = "Informe valores numéricos para Localidade.";
		form.localidade.focus();
		alert(msg);
		return false;
	}
	if(isNaN(form.qntRegistros.value)){
		msg = "Informe valores numéricos para Quantidade de Registros.";
		form.qntRegistros.focus();
		alert(msg);
		return false;
	}
	return true;
}
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];
	if (tipoConsulta == 'localidade') {
  		
  		form.localidade.value = codigoRegistro;
  		form.nomeLocalidade.value = descricaoRegistro;
  		
  		form.nomeLocalidade.style.color = "#000000";
	}
}
function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		form.action="/gsan/exibirGerarRelatorioMaioresConsumidoresAction.do";

		switch(tipo){
			case 0: //De localidara pra baixo

				form.localidade.value = "";
				form.nomeLocalidade.value = "";
		     	form.localidade.focus();
				break;
		}
		form.submit();
	}
function maskKeyPress(objEvent) 
{ 
        var iKeyCode; 
                                 
        if(window.event) // IE 
        { 
                iKeyCode = objEvent.keyCode; 
                if(iKeyCode>=48 && iKeyCode<=57) return true; 
                return false; 
        } 
        else if(e.which) // Netscape/Firefox/Opera 
        { 
                iKeyCode = objEvent.which; 
                if(iKeyCode>=48 && iKeyCode<=57) return true; 
                return false; 
        } 
         
         
}

function limpar(){

		var form = document.forms[0];
		form.mesAno.value = "";
		form.localidade.value = "";		
		form.nomeLocalidade.value = "";
		form.qntRegistros.value = "";
	}


</script>


</head>

<body leftmargin="5" topmargin="5">
<html:form action="/gerarRelatorioMaioresConsumidoresAction.do"
	name="GerarRelatorioMaioresConsumidoresActionForm"
	type="gcom.gui.relatorio.faturamento.GerarRelatorioMaioresConsumidoresActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio" />


	<table width="770" border="0" cellpadding="0" cellspacing="5">
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
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Relátorio dos Maiores Consumidores</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio dos maiores consumidores, informe os dados abaixo:</td>
				</tr>
				<tr>
         			 <td width="30%"><strong>Ano/Mês Referência:<font color="#FF0000">*</font></strong></td>
          			<td width="70%">
          				<html:text property="mesAno" size="7"  maxlength="7" 
          						   onkeypress="javascript: return isCampoNumerico(event);"
          						   onkeyup="javascript:mascaraAnoMes(this, event);" 
          						   onblur="javascript:return verificaAnoMes(this);"/>
         					&nbsp;mm/aaaa
         					<br/>
          			</td>
        		</tr>
        		<tr>
					<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="localidade" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioMaioresConsumidoresAction.do?objetoConsulta=1','localidade','Localidade'); return isCampoNumerico(event);"
							 />
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '',document.forms[0].localidade);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeEncontrada" scope="session">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeEncontrada" scope="session">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaOrigem(0);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Quantidade máxima de registros:<font color="#FF0000">*</font></strong></td>
					<td>
					<html:text maxlength="5" 
							property="qntRegistros" 
							size="5"
							onkeypress="return isCampoNumerico(event);"
							 />
					</td>
				</tr>		        		
				<tr>
					<td align="right">
					<div align="left"><br><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				
							
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
					<td colspan="2"></td>
				</tr>
			</table>
			<table width="100%">
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
