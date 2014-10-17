<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AcaoCobrancaFiltrarActionForm" dynamicJavascript="false" />

<script language="JavaScript">

 var bCancel = false;

function validateAcaoCobrancaFiltrarActionForm(form) {
     if (bCancel)
   return true;
     else
   return validateCaracterEspecial(form) && validateLong(form); 
}

function caracteresespeciais () {
  this.aa = new Array("descricaoAcao", "Descrição da Ação de Cobrança possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  this.ab = new Array("idCobrancaCriterio", "Critério de Cobrança possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  this.ac = new Array("idServicoTipo", "Tipo de Serviço da Ordem de Serviço a ser Gerada possui caracteres especiais.", new Function ("varName", " return this[varName];"));
 
}

 function IntegerValidations () {
  this.aa = new Array("numeroDiasValidade", "Número de Dias de Validade da Ação deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
  this.ab = new Array("numeroDiasEntreAcoes", "Número de Dias entre a Ação e sua Predecessora deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
  this.ac = new Array("ordemCronograma", "Ordem no Cronograma deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
  this.ad = new Array("idServicoTipo", "Tipo de Serviço da Ordem de Serviço a ser Gerada deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
  this.ae = new Array("idCobrancaCriterio", "Critério de Cobrança deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}
   
    


    

 
function validarForm(form){

	if(validateAcaoCobrancaFiltrarActionForm(form)){
	    submeterFormPadrao(form);
	}
}


function desfazer(){
	 var form = document.forms[0];
	 
	 form.descricaoAcao.value = '';
	 form.numeroDiasValidade.value = '';
	 form.idAcaoPredecessora.value = '';
	 form.numeroDiasEntreAcoes.value = '';
	 form.qtdDiasRealizacao.value = '';
	 form.idTipoDocumentoGerado.value = '';
	 form.idSituacaoLigacaoAgua.value = '';
	 form.idSituacaoLigacaoEsgoto.value = '';
	 form.idCobrancaCriterio.value = '';
	 form.descricaoCobrancaCriterio.value = '';
	 form.idServicoTipo.value = '';
	 form.descricaoServicoTipo.value = '';
	 form.ordemCronograma.value = '';
	 form.icCompoeCronograma[0].checked = false;
	 form.icCompoeCronograma[1].checked = false;
	 form.icCompoeCronograma.value = '';
	 form.icAcaoObrigatoria[0].checked = false;
	 form.icAcaoObrigatoria[1].checked = false;
	 form.icAcaoObrigatoria.value = '';
	 form.icRepetidaCiclo[0].checked = false;
	 form.icRepetidaCiclo[1].checked = false;
	 form.icRepetidaCiclo.value = '';
	 form.icSuspensaoAbastecimento[0].checked = false;
	 form.icSuspensaoAbastecimento[1].checked = false;
	 form.icSuspensaoAbastecimento.value = '';
	 form.icDebitosACobrar[0].checked = false;
	 form.icDebitosACobrar[1].checked = false;
	 form.icDebitosACobrar.value = '';
	 form.icAcrescimosImpontualidade[0].checked = false;
	 form.icAcrescimosImpontualidade[1].checked = false;
	 form.icAcrescimosImpontualidade.value = '';
	 form.icGeraTaxa[0].checked = false;
	 form.icGeraTaxa[1].checked = false;
	 form.icGeraTaxa.value = '';
	 form.icEmitirBoletimCadastro[0].checked = false;
	 form.icEmitirBoletimCadastro[1].checked = false;
	 form.icEmitirBoletimCadastro.value = '';
	 form.icImoveisSemDebitos[0].checked = false;
	 form.icImoveisSemDebitos[1].checked = false;
	 form.icImoveisSemDebitos.value = '';
	 form.indicadorAtualizar.value = '1';
	 form.icUso[0].checked = false;
	 form.icUso[1].checked = false;
	 form.icUso[2].checked = true;
	 form.icUso.value = '3';
	 form.indicadorConsideraCreditoRealizar[0].checked = false;
	 form.indicadorConsideraCreditoRealizar[1].checked = false;
	 form.indicadorConsideraCreditoRealizar.value = '';
	 form.indicadorConsideraGuiaPagamento[0].checked = false;
	 form.indicadorConsideraGuiaPagamento[1].checked = false;
	 form.indicadorConsideraGuiaPagamento.value = '';
	 form.idPrimeiraResolucaoDiretoria.value = '';
	 form.idSegundaResolucaoDiretoria.value = '';
	 form.idTerceiraResolucaoDiretoria.value = '';
 
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   var form = document.forms[0];

   if (tipoConsulta == 'criterioCobranca') {
      form.idCobrancaCriterio.value = codigoRegistro;
      form.descricaoCobrancaCriterio.value = descricaoRegistro;
       form.descricaoCobrancaCriterio.style.color = "#000000";
   }
   
   if (tipoConsulta == 'tipoServico') {
     	form.idServicoTipo.value = codigoRegistro;
		form.descricaoServicoTipo.value = descricaoRegistro;
       form.descricaoServicoTipo.style.color = "#000000";
   }
   
   
 }

function limparCriterio(){
	var form = document.forms[0];
	form.idCobrancaCriterio.value = '';
	form.descricaoCobrancaCriterio.value = '';
}


function limparServicoTipo(){
	 var form = document.forms[0];
	  form.idServicoTipo.value = '';
	 form.descricaoServicoTipo.value = '';
}


function habilitaNumeroDiasAcaoPredecessora(){
	 var form = document.forms[0];
	 if(form.idAcaoPredecessora.value != ""){
	   form.numeroDiasEntreAcoes.disabled = false;
	 }else{
	  form.numeroDiasEntreAcoes.value ='';
	  form.numeroDiasEntreAcoes.disabled = true;
	 }
}

function verificarChecado(valor){
	form = document.forms[0];
	if(valor == "1"){
	 form.indicadorAtualizar.checked = true;
	 }else{
	 form.indicadorAtualizar.checked = false;
	
	}
}

</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');habilitaNumeroDiasAcaoPredecessora();verificarChecado('${sessionScope.indicadorAtualizar}');"">

<html:form action="/filtrarAcaoCobrancaAction"
	name="AcaoCobrancaFiltrarActionForm"
	type="gcom.gui.cobranca.AcaoCobrancaFiltrarActionForm"
	method="post">

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
					<td class="parabg">Manter Ação de Cobrança</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<p>Para filtrar a ação de cobrança, informe os dados abaixo:</p></td>
					<td align="right"><input type="checkbox"
						name="indicadorAtualizar" value="1" tabindex="42"/><strong>Atualizar</strong>
					<p>&nbsp;</p>
					</td>
				</tr>
			</table>
			
			<table width="100%" border="0">
				<tr>
					<td width="25%"><strong>Descrição da Ação de Cobrança:</strong></td>
					<td><html:text property="descricaoAcao"	size="40" maxlength="40" tabindex="1"/></td>
				</tr>
				<tr> 
                    <td><strong>Núm. Dias de Validade da Ação:</strong></td>
				     <td><html:text maxlength="4" property="numeroDiasValidade" size="4" tabindex="2" onkeypress="return isCampoNumerico(event);"/></td>
              </tr>
              <tr> 
	                  <td><strong>Número de Dias para a Realização da Ação:</strong></td>
					 <td>
	                  		<html:text property="qtdDiasRealizacao" size="4"
							maxlength="4" tabindex="4" onkeypress="return isCampoNumerico(event);"/>
				  	  </td>
             </tr>
              <tr> 
                <td><strong>Ação Predecessora:</strong></td>
                <td>
                    <html:select property="idAcaoPredecessora" tabindex="3" onchange="habilitaNumeroDiasAcaoPredecessora();" style="width: 300px;">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoAcaoPredecessora" labelProperty="descricaoCobrancaAcao" property="id" />
					</html:select>
                </td>
              </tr>
              
               <tr> 
	           	<td><strong>Núm. Dias entre a Ação e Predec.:</strong></td>
				<td><html:text property="numeroDiasEntreAcoes" size="4" maxlength="" tabindex="4" onkeypress="return isCampoNumerico(event);"/></td>
             </tr>
             <tr> 
                <td><strong>Tipo de Doc. a ser Gerado:</strong></td>
                <td>
                    <html:select property="idTipoDocumentoGerado" tabindex="5" style="width: 300px;">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoDocumentoTipo" labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select>
                </td>
              </tr>
              <tr> 
                <td><strong>Sit. da Lig. de Água dos Imóveis:</strong></td>
                <td>
                    <html:select property="idSituacaoLigacaoAgua" tabindex="6" style="width: 200px;">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoLigacaoAguaSituacao" labelProperty="descricao" property="id" />
					</html:select>
                </td>
              </tr>
              <tr> 
                <td><strong>Sit. da Lig. de Esgoto dos Imóveis:</strong></td>
                <td>
                    <html:select property="idSituacaoLigacaoEsgoto" tabindex="7" style="width: 200px;">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoLigacaoEsgotoSituacao" labelProperty="descricao" property="id" />
					</html:select>
                </td>
              </tr>  
              <tr> 
	            <td><strong>Critério de Cobrança:</strong></td>
	            <td width="40%">
	               <html:text 
	               	maxlength="6" 
	               	property="idCobrancaCriterio" 
	               	size="6" 
	               	tabindex="8"
					onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarAcaoCobrancaAction.do', 'idCobrancaCriterio', 'Critério de Cobrança');document.forms[0].descricaoCobrancaCriterio.value = ''; return isCampoNumerico(event);" />
						<a href="javascript:abrirPopup('exibirPesquisarCriterioCobrancaAction.do',400,600);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Critério de Cobrança" /></a>
						<logic:notEmpty name="AcaoCobrancaFiltrarActionForm" property="idCobrancaCriterio">
							<html:text property="descricaoCobrancaCriterio" size="40" maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<logic:empty name="AcaoCobrancaFiltrarActionForm" property="idCobrancaCriterio">
						 <html:text property="descricaoCobrancaCriterio" size="40" maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty> 
						<a href="javascript:limparCriterio();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
				</td>
              </tr>
              <tr> 
	            <td><strong>Tipo de Serviço:</strong></td>
	            <td>
	               <html:text maxlength="4" property="idServicoTipo" size="4" tabindex="9"
					 onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarAcaoCobrancaAction.do', 'idServicoTipo', 'Tipo de Serviço');document.forms[0].descricaoServicoTipo.value = '';" />
						<a href="javascript:abrirPopup('exibirPesquisarTipoServicoAction.do',400,600);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Tipo de Serviço" /></a>
						<logic:notEmpty name="AcaoCobrancaFiltrarActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" size="40" maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<logic:empty name="AcaoCobrancaFiltrarActionForm" property="idServicoTipo">
						 <html:text property="descricaoServicoTipo" size="40" maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty> 
						<a href="javascript:limparServicoTipo();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
				</td>
              </tr>
              <tr> 
	          	<td><strong>Ordem no Cronograma:</strong></td>
				<td><html:text property="ordemCronograma" size="4" maxlength="4" tabindex="10"/></td>
             </tr>
             <tr> 
                <td><strong>Número da 1ª R.D.:</strong></td>
                <td width="25%">
                    <html:select property="idPrimeiraResolucaoDiretoria" tabindex="11">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoResolucaoDiretoria">
							<html:options collection="colecaoResolucaoDiretoria" labelProperty="numeroResolucaoDiretoria" property="id" />
							</logic:present>
					</html:select>
                </td>
              </tr>
              <tr> 
                <td><strong>Número da 2ª R.D.:</strong></td>
                <td width="25%">
                    <html:select property="idSegundaResolucaoDiretoria" tabindex="12">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoResolucaoDiretoria">
							<html:options collection="colecaoResolucaoDiretoria" labelProperty="numeroResolucaoDiretoria" property="id" />
							</logic:present>
					</html:select>
                </td>
              </tr>
              <tr> 
                <td><strong>Número da 3ª R.D.:</strong></td>
                <td width="25%">
                    <html:select property="idTerceiraResolucaoDiretoria" tabindex="13">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoResolucaoDiretoria">
							<html:options collection="colecaoResolucaoDiretoria" labelProperty="numeroResolucaoDiretoria" property="id" />
							</logic:present>
					</html:select>
                </td>
              </tr>
                 
             <tr> 
                <td height="24" colspan="2"></td>
             </tr>
           </table>
			
			<table width="100%" border="0">
              <tr> 
                <td colspan="2">
                
                <table width="100%" border="0">
                  <tr>
	                 <td width="45%"><strong>Considera Crédito a Realizar:</strong></td>
	                 <td><html:radio property="indicadorConsideraCreditoRealizar" value="1" tabindex="14"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="indicadorConsideraCreditoRealizar" value="2" tabindex="15"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             <tr>
	                 <td width="45%"><strong>Considera Guia de Pagamento:</strong></td>
	                 <td><html:radio property="indicadorConsideraGuiaPagamento" value="1" tabindex="16"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="indicadorConsideraGuiaPagamento" value="2" tabindex="17"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
                 <tr>
                  <td width="35%"><strong>Compõe o Cronograma:</strong></td>
                  <td width="15%"><html:radio property="icCompoeCronograma" value="1" tabindex="18"/> 
                    	<strong>Sim</strong></td>
                  <td width="15%"><html:radio property="icCompoeCronograma" value="2" tabindex="19"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Ação Obrigatória:</strong></td>
                  <td><html:radio property="icAcaoObrigatoria" value="1" tabindex="20"/> 
                      	<strong>Sim</strong></td>
                  <td><html:radio property="icAcaoObrigatoria" value="2" tabindex="21"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Pode ser Repetida no Ciclo:</strong></td>
                  <td><html:radio property="icRepetidaCiclo" value="1" tabindex="22"/> 
                      	<strong>Sim</strong></td>
                  <td><html:radio property="icRepetidaCiclo" value="2" tabindex="23"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Provoca Suspen. de Abast.:<font color="#FF0000"></strong></td>
                  <td><html:radio property="icSuspensaoAbastecimento" value="1" tabindex="24"/> 
                      	<strong>Sim</strong></td>
                  <td><html:radio property="icSuspensaoAbastecimento" value="2" tabindex="25"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Considera Débitos a Cobrar:</strong></td>
                  <td><html:radio property="icDebitosACobrar" value="1" tabindex="26"/> 
                      	<strong>Sim</strong></td>
                  <td><html:radio property="icDebitosACobrar" value="2" tabindex="27"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Considera Acrésc. por Impont.:</strong></td>
				  <td><html:radio property="icAcrescimosImpontualidade" value="1" tabindex="28"/> 
                      	<strong>Sim</strong></td>
                  <td><html:radio property="icAcrescimosImpontualidade" value="2" tabindex="29"/> 
                      	<strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Gera Taxa:</strong></td>

                  <td><html:radio property="icGeraTaxa" value="1" tabindex="30"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icGeraTaxa" value="2" tabindex="31"/> 
                      <strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Pode Emitir Boletins de Cadastro:</strong></td>

                  <td><html:radio property="icEmitirBoletimCadastro" value="1" tabindex="32"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icEmitirBoletimCadastro" value="2" tabindex="33"/> 
                      <strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 <tr> 
                  <td><strong>Pode Exec. para Imóv. sem Déb.:</strong></td>

                  <td><html:radio property="icImoveisSemDebitos" value="1" tabindex="34"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icImoveisSemDebitos" value="2" tabindex="35"/> 
                      <strong>N&atilde;o</strong></td>
                  <td></td>
                 </tr>
                 
                 <tr> 
                  <td><strong>Indicador de Uso:</strong></td>

                  <td><html:radio property="icUso" value="1" tabindex="36" /> <strong>Ativo</strong></td>
                  <td><html:radio property="icUso" value="2" tabindex="37" /> <strong>Inativo</strong></td>
                  <td><html:radio property="icUso" value="3" tabindex="38" /> <strong>Todos</strong></td>
                 </tr>
                 
             	</table>
                
                </td>
               </tr>  
			   <tr> 
                <td height="24" colspan="2"></td>
               </tr>
               <tr>
				<td colspan="2">
					<table width="100%" border="0">
						<tr>
							<td valign="top"><input name="button" type="button"
								class="bottonRightCol" value="Desfazer" onclick="desfazer();" tabindex="40">&nbsp;<input type="button"
								name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
								onClick="window.location.href='/gsan/telaPrincipal.do'" tabindex="41"></td>
							<td valign="top">
							  <div align="right">
							    <gcom:controleAcessoBotao name="botaoFiltrar" value="Filtrar" onclick="validarForm(document.forms[0]);" url="inserirAcaoCobrancaAction.do" tabindex="39"/>
							    <%-- <input name="botaoInserir" type="button" class="bottonRightCol" value="Inserir" onclick="validarForm(document.forms[0]);" tabindex="17">--%>
							  </div>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
			</td>
		</tr>			
	</table>
   </td>
  </tr>
 </table>


	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
