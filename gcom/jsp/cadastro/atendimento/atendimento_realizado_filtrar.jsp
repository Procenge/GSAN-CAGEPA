<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarDocumentosCobrancaActionForm" />
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function validarForm(){
	var form = document.forms[0];
	var flagExecutarConsulta = true;
	
	if ( ((form.dataHoraInicioAtendimento.value != null) && (form.dataHoraInicioAtendimento.value != '')) 
			&& ((form.dataHoraFimAtendimento.value == null) || (form.dataHoraFimAtendimento.value == '')) ) {
		alert('Por favor informa a data final do período do atendimento.');
		flagExecutarConsulta = false;
	}

	if (((form.dataHoraFimAtendimento.value != null) && (form.dataHoraFimAtendimento.value != '')) 
			&& ((form.dataHoraInicioAtendimento.value == null) || (form.dataHoraInicioAtendimento.value == '')) ) {
		alert('Por favor informa a data inicial do período do atendimento.');
		flagExecutarConsulta = false;
	}	
	
	if (((form.dataHoraFimAtendimento.value != null) && (form.dataHoraFimAtendimento.value != '')) 
			&& ((form.dataHoraInicioAtendimento.value != null) && (form.dataHoraInicioAtendimento.value != '')) ) {	
		if (comparaData(form.dataHoraInicioAtendimento.value, ">", form.dataHoraFimAtendimento.value )){
	  		alert('Data Final do Período de Atendimento é anterior a Data Inicial do Período de Lançamento do Aviso.');
	  		flagExecutarConsulta = false;
		}	
	}
	
	if (flagExecutarConsulta) {
		submeterFormPadrao(form);
	}
	
}

function carregarSolicitacaoTipoEspecificacao() {
	var form = document.forms[0];
	
	form.action = '/gsan/exibirFiltrarAtendimentosRealizadosAction.do?carregarSolicitacaoTipoEspecificacao=S';
	form.submit();
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		} else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			} else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
	
    if (tipoConsulta == 'funcionalidade') {
    	  form.idFuncionalidade.value = codigoRegistro;
	      form.descricaoFuncionalidade.value = descricaoRegistro;
	      form.action = 'exibirFiltrarAtendimentosRealizadosAction.do?carregarFuncionalidade=S';
	      form.submit();
    }
    
    if (tipoConsulta == 'imovel') {
  	  form.idImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.action = 'exibirFiltrarAtendimentosRealizadosAction.do?carregarImovel=S';
	      form.submit();
  }
  
    
  if (tipoConsulta == 'cliente') {
		form.idCliente.value = codigoRegistro;
		form.nomeCliente.value = descricaoRegistro;
      form.action = 'exibirFiltrarAtendimentosRealizadosAction.do?carregarCliente=S'
	    form.submit();
	}    
}

function limparFuncionalidade(){
 	var form = document.forms[0];
 	
 	form.idFuncionalidade.value = "";
 	form.descricaoFuncionalidade.value = ""; 		
 }

 function limparDescricaoFuncionalidade(){
	 	var form = document.forms[0];
	 	form.descricaoFuncionalidade.value = ""; 		
 }
 
 function limparInscricaoImovel(){
	 	var form = document.forms[0];
	 	form.inscricaoImovel.value = ""; 		
}

function limparImovel(){
	 	var form = document.forms[0];
	 	
	 	form.idImovel.value = ""; 
	 	form.inscricaoImovel.value = ""; 			
}

function limparNomeCliente(){
	 	var form = document.forms[0];
	 	form.nomeCliente.value = ""; 		
}

function limparCliente(){
	 	var form = document.forms[0];

	 	form.idCliente.value = ""; 
	 	form.nomeCliente.value = ""; 		 	
} 
 
</script>
</head>

	<body leftmargin="5" topmargin="5" onload="">

<html:form action="/filtrarAtendimentosRealizadosAction"
	name="FiltrarAtendimentosRealizadosActionForm"
	type="gcom.gui.cadastro.atendimento.FiltrarAtendimentosRealizadosActionForm"
	onsubmit="return validateFiltrarAtendimentosRealizadosActionForm(this);" method="post">

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
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table>
				<tr>
					<td></td>
				</tr>
			</table>

			<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
				<TBODY>

					<TR>
						<TD width=11><IMG
							src="<bean:message key="caminho.imagens"/>parahead_left.gif"
							border=0></TD>
						<TD class=parabg>Consultar Atendimentos Realizados</TD>
						<TD width=11><IMG
							src="<bean:message key="caminho.imagens"/>parahead_right.gif"
							border=0></TD>
					</TR>
				</TBODY>
			</TABLE>
			
			<DIV align=right>
			<table bordercolor="#000000" width="100%" cellspacing="0" id="table1">

				<tr>
					<td colspan="4">Para filtrar Atendimentos Realizados no
					sistema, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td colspan="4"></td>
				</tr>
				
				<tr>
					<td><strong>Funcionalidade:</strong></td>
					<td colspan="3"><html:text property="idFuncionalidade" size="9"
						maxlength="9" tabindex="2"
						onkeyup="javascript:limparDescricaoFuncionalidade();"
						onkeypress="return validaEnterSemUpperCase(event, 'exibirFiltrarAtendimentosRealizadosAction.do?carregarFuncionalidade=S', 'idFuncionalidade');" />
					<a
						href="javascript:chamarPopup('exibirPesquisarFuncionalidadeAction.do', '', null, null, 600, 500, '','');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Funcionalidade" /> </a>  
					<logic:present name="funcionalidadeNaoEncontrada">
						<logic:equal name="funcionalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoFuncionalidade" size="45"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeNaoEncontrada"
							value="exception">
							<html:text property="descricaoFuncionalidade" size="45"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="funcionalidadeNaoEncontrada">
						<logic:empty name="FiltrarAtendimentosRealizadosActionForm"
							property="idFuncionalidade">
							<html:text property="descricaoFuncionalidade" value="" size="30"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarAtendimentosRealizadosActionForm"
							property="idFuncionalidade">
							<html:text property="descricaoFuncionalidade" size="30"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparFuncionalidade();"> <img
						border="0" title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>
				</tr>	
				
				<tr> 
					<td width="30%">
						<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
					</td>
					<td>
						<html:text property="idImovel" maxlength="9" size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarAtendimentosRealizadosAction.do?carregarImovel=S', 'idImovel','Matrícula do Imóvel');" 
							onkeyup="verificaNumeroInteiro(this);limparInscricaoImovel();" />
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
							<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>	
							<logic:present name="idImovelNaoEncontrado">
			                  <logic:equal name="idImovelNaoEncontrado" value="exception">
				                    <html:text property="inscricaoImovel" size="30"
					                   maxlength="30" readonly="true"
					                   style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:equal>
			                  <logic:notEqual name="idImovelNaoEncontrado" value="exception">
				                   <html:text property="inscricaoImovel" size="30"
					                maxlength="30" readonly="true"
					                style="background-color:#EFEFEF; border:0; color: #000000" />
			                  </logic:notEqual>
		                   </logic:present> 
		                   <logic:notPresent name="idImovelNaoEncontrado">
			                  <logic:empty name="FiltrarAtendimentosRealizadosActionForm" property="inscricaoImovel">
				                    <html:text property="inscricaoImovel" size="30" value = ""
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:empty>
			               <logic:notEmpty name="FiltrarAtendimentosRealizadosActionForm" property="inscricaoImovel">
				                   <html:text property="inscricaoImovel" size="30"
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #000000" />
			               </logic:notEmpty>
			               </logic:notPresent>
							
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" onclick="limparImovel();" style="cursor: hand;" />
					</td>
				</tr>				
				
				<tr>
					<td><strong>Cliente:</strong></td>
					<td>
						<html:text property="idCliente" size="9" maxlength="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarAtendimentosRealizadosAction.do?carregarCliente=S', 'idCliente','Cliente');"
							onkeyup="verificaNumeroInteiro(this);limparNomeCliente();" />
						
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do', 400, 800);" >
							<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
						</a>
						
						<logic:present name="clienteInexistente" scope="request">
							<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present> 
						<logic:notPresent name="clienteInexistente"
							scope="request">
							<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						
						<a href="javascript:limparCliente();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
						</a>
					</td>
				</tr>				
							
	              <tr> 
	                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong>
						<html:select property="idSolicitacaoTipo" 
							style="width: 350px; height:100px;" 
							multiple="true" 
							onchange="javascript:carregarSolicitacaoTipoEspecificacao();" 
							tabindex="7">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoTipoSolicitacao" scope="session">
								<html:options collection="colecaoTipoSolicitacao" labelProperty="descricao" property="id" />
							</logic:present>
						</html:select>                
	                  </strong></span></td>
	              </tr>
	              
	              <tr> 
	                <td><strong>Especifica&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong> 
						<html:select property="idSolicitacaoTipoEspecificacao" 
							style="width: 350px;" 
							multiple="true" 
							tabindex="8">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoEspecificacao" scope="session">
								<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
							</logic:present>
						</html:select>                
	                  </strong></span>
	                 </td>
	              </tr>	
				<tr>
					<td class="style1"><strong>Per&iacute;odo de Atendimento:</strong></td>

					<td colspan="3"> <html:text property="dataHoraInicioAtendimento"
						size="10"
						onkeyup="mascaraData(this, event);"
						maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarAtendimentosRealizadosActionForm', 'dataHoraInicioAtendimento')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
					a  <html:text property="dataHoraFimAtendimento" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('FiltrarAtendimentosRealizadosActionForm', 'dataHoraFimAtendimento')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
				<tr>
					<td>&nbsp;</td>

					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%">
				<tr>
					<td colspan="3" align="left">
						<div align="left">
								<input type="button" 
									value="Limpar"
									name="Submit123" 
									class="bottonRightCol"
									onclick="javascript:window.location.href='/gsan/exibirFiltrarAtendimentosRealizadosAction.do?menu=sim';">
						</div>
					</td>

					<td colspan="3">
					<div align="right">
					  <gcom:controleAcessoBotao name="Submit23" value="Consultar" onclick="validarForm();" url="filtrarAtendimentosRealizadosAction.do"/>
					</div>
					</td>
				</tr>
			</table>
			</DIV>
		</td>
		</tr>
		</table>

		<logic:notPresent name="ehPopup">
			<%@ include file="/jsp/util/rodape.jsp"%>
		</logic:notPresent>


</html:form>

</html:html>
