<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

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
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioFaturamentoCobradoEmContaForm" />

<script language="JavaScript">

function validarForm(form){
	
	if(form.P_MES_ANO_REFERENCIA.value == '') {
		alert('Informe o Mês e Ano');
		form.P_MES_ANO_REFERENCIA.focus();
		return false;
	} else {
		if(validaMesAno(form.P_MES_ANO_REFERENCIA) == true) {
			if(form.ehRelatorioBatch.value == 2) {
				form.target = "_blank";				
			}
			form.submit();
			form.target = "";
		}		
	}
}
function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
	
	document.forms[0].campoOrigem.value = campo.name;
	
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
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
			}
		}
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'localidadeOrigem') {
  		
  		form.LOCA_I.value = codigoRegistro;
  		form.nomeLocalidadeInicial.value = descricaoRegistro;
  		
  		form.LOCA_F.value = codigoRegistro;
  		form.nomeLocalidadeFinal.value = descricaoRegistro;
  		
  		form.nomeLocalidadeInicial.style.color = "#000000";
  		form.nomeLocalidadeFinal.style.color = "#000000";
  		
	}

	if (tipoConsulta == 'localidadeDestino') {
	
  		form.LOCA_F.value = codigoRegistro;
  		form.nomeLocalidadeFinal.value = descricaoRegistro;
  		form.nomeLocalidadeFinal.style.color = "#000000";

	}
}

function replicarLocalidade(){
	var formulario = document.forms[0]; 
	
	formulario.LOCA_F.value = formulario.LOCA_I.value;
}


function limparOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1: //De localidade pra baixo
			
			form.nomeLocalidadeInicial.value = "";
			form.LOCA_F.value = "";
			form.nomeLocalidadeFinal.value = "";
}
}

function limparBorrachaOrigem(tipo){
	var form = document.forms[0];
			
	switch(tipo){
		case 1: //De localidara pra baixo

			form.LOCA_I.value = "";
			form.nomeLocalidadeInicial.value = "";
			form.LOCA_F.value = "";
			form.nomeLocalidadeFinal.value = "";
				break;
		}
}
function validaMesAno(mesAno){
	if(mesAno.value != ""){
		return verificaAnoMes(mesAno);
	}else{
		return false;
	}
}

function submitDynaReset(){
	var form = document.forms[0];
	
	if(form.LOCA_I.value == 0){
		form.LOCA_I.value = '';	
	}
	if(form.LOCA_F.value == 0){
  		form.LOCA_F.value = '';
	}
}


</script>

</head>

<body leftmargin="5" topmargin="5" onload="submitDynaReset();">

<html:form action="/gerarRelatorioFaturamentoCobradoEmContaAction.do"
 name="GerarRelatorioFaturamentoCobradoEmContaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="campoOrigem" value="" />
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioFaturamentoCobradoEmConta.rpt"/>
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                        --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online   --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>
	
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
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Gerar Relatório Faturamento Cobrado em Conta</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para gerar o relatório,
					informe os dados abaixo:</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				
				<tr>
		          <td width="30%"><strong>Mês/Ano de Faturamento:<font color="#FF0000">*</font></strong></td>
		          <td width="70%">
		          	<html:text property="P_MES_ANO_REFERENCIA" size="7"  maxlength="7" 
		          		onkeyup="javascript:mascaraAnoMes(this, event);"
		          		onblur="javascript:verificaAnoMes(this);"
		          		/>
		          	&nbsp;mm/aaaa
		          </td>
		        </tr>
		        	
				<tr>
					<td><strong>Localidade Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="LOCA_I" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeyup="javascript:limparOrigem(1);"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioFaturamentoCobradoEmContaAction.do?objetoConsulta=1','LOCA_I','Localidade Inicial'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].LOCA_I);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="LOCA_F" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioFaturamentoCobradoEmContaAction.do?objetoConsulta=3','LOCA_F','Localidade Final'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].LOCA_F);limparDestino(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 

						<logic:present name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaDestino(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
					
				<tr>
					<td>
						<strong>Grupo de Faturamento Inicial:</strong>
					</td>
					
					<td><strong> 
						<html:select property="GRUPO_I"
								onblur="replicarCampo(GRUPO_F,GRUPO_I);">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
							<html:options collection="colecaoFaturamentoGrupo" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Grupo de Faturamento Final:</strong>
					</td>
					
					<td><strong> 
						<html:select property="GRUPO_F">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
							<html:options collection="colecaoFaturamentoGrupo" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
				</tr>				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left" colspan="2"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar" tabindex="4"
						onclick="window.location.href='/gsan/exibirGerarRelatorioFaturamentoCobradoEmContaAction.do?menu=sim'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Gerar"
						onclick="validarForm(document.forms[0]);" tabindex="5"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
