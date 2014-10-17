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
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AcaoCobrancaAtualizarActionForm" dynamicJavascript="false" />

<script language="JavaScript">

var bCancel = false;

function validateAcaoCobrancaAtualizarActionForm(form) {
     if (bCancel)
   		return true;
     else
	   return validateRequired(form) && validateCaracterEspecial(form) && validateLong(form) 
				&& validaTodosRadioButton() && validarNumeroDiasEntreAcoes(form) && validarCartaParcelamento(form) && validarQtdDiasRealizacao(); 
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
	this.ad = new Array("numeroDiasVencimento", "Número de Dias de Vencimento deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("idServicoTipo", "Tipo de Serviço da Ordem de Serviço a ser Gerada deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.af = new Array("idCobrancaCriterio", "Critério de Cobrança deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function required () {
	this.aa = new Array("descricaoAcao", "Informe Descrição da Ação de Cobrança.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("numeroDiasValidade", "Informe Número de Dias de Validade da Ação.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("idTipoDocumentoGerado", "Informe Tipo de Documento a ser Gerado.", new Function ("varName", " return this[varName];"));
	this.af = new Array("idCobrancaCriterio", "Informe Critério de Cobrança.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("acaoCobrancaEfeito", "Informe Efeito da Ação.", new Function ("varName", " return this[varName];"));
}


function validaRadioButton(nomeCampo,mensagem){
	var alerta = "";
	var int = 0;
	if(!nomeCampo[0].checked && !nomeCampo[1].checked){
		alerta =  "Informe " + mensagem +".";
	}
	return alerta;
}

function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(validaRadioButton(form.icCompoeCronograma,"Compõe o Cronograma") != ""){
			mensagem = mensagem + validaRadioButton(form.icCompoeCronograma,"Compõe o Cronograma")+"\n";
	}
	if(validaRadioButton(form.icAcaoObrigatoria,"Ação Obrigatória") != ""){
		mensagem = mensagem + validaRadioButton(form.icAcaoObrigatoria,"Ação Obrigatória")+"\n";
	}
	if(validaRadioButton(form.icClienteUsuarioSemCPFCNPJ,"Considera Cliente Usuário Sem CPF/CNPJ") != ""){
		mensagem = mensagem + validaRadioButton(form.icClienteUsuarioSemCPFCNPJ,"Considera Cliente Usuário Sem CPF/CNPJ")+"\n";
	}
	if(validaRadioButton(form.icEnderecoSemCEP,"Considera Endereço Sem CEP") != ""){
		mensagem = mensagem + validaRadioButton(form.icEnderecoSemCEP,"Considera Endereço Sem CEP")+"\n";
	}
	if(validaRadioButton(form.icRepetidaCiclo,"Pode ser Repetida no Ciclo") != ""){
		mensagem = mensagem + validaRadioButton(form.icRepetidaCiclo,"Pode ser Repetida no Ciclo")+"\n";
	}
	if(validaRadioButton(form.icSuspensaoAbastecimento,"Provoca Suspensão de Abastecimento") != ""){
		mensagem = mensagem + validaRadioButton(form.icSuspensaoAbastecimento,"Provoca Suspensão de Abastecimento")+"\n";
	}
	if(validaRadioButton(form.icDebitosACobrar,"Considera Débitos a Cobrar") != ""){
		mensagem = mensagem + validaRadioButton(form.icDebitosACobrar,"Considera Débitos a Cobrar")+"\n";
	}
	if(validaRadioButton(form.icAcrescimosImpontualidade,"Considera Acréscimos por Impontualidade") != ""){
		mensagem = mensagem + validaRadioButton(form.icAcrescimosImpontualidade,"Considera Acréscimos por Impontualidade")+"\n";
	}
	if(validaRadioButton(form.icGeraTaxa,"Gera Taxa") != ""){
		mensagem = mensagem + validaRadioButton(form.icGeraTaxa,"Gera Taxa")+"\n";
	}
	if(validaRadioButton(form.icEmitirBoletimCadastro,"Pode Emitir Boletins de Cadastro") != ""){
		mensagem = mensagem + validaRadioButton(form.icEmitirBoletimCadastro,"Pode Emitir Boletins de Cadastro")+"\n";
	}
	if(validaRadioButton(form.icImoveisSemDebitos,"Pode ser Executada para Imóveis sem Débito") != ""){
		mensagem = mensagem + validaRadioButton(form.icImoveisSemDebitos,"Pode ser Executada para Imóveis sem Débito")+"\n";
	}
 
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

    

 
function validarForm(form){

	if(validateAcaoCobrancaAtualizarActionForm(form)){
	    submeterFormPadrao(form);
	}
}


function desfazer(){
	var form = document.forms[0];
	form.action = '\exibirAtualizarAcaoCobrancaAction.do?limpar=1';
	form.submit();
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

function habilitaNumeroDiasAcaoPredecessora(){
	var form = document.forms[0];
	if(form.idAcaoPredecessora.value != ""){
	  	form.numeroDiasEntreAcoes.disabled = false;
	}else{
		form.numeroDiasEntreAcoes.value ='';
		form.numeroDiasEntreAcoes.disabled = true;
	}
}

function validarNumeroDiasEntreAcoes(form){
   var retorno = true;
	if(form.idAcaoPredecessora.value != ''){
		 if(form.numeroDiasEntreAcoes.value == ''){
		 	alert('Informe Número de Dias entre a Ação e sua Predecessora');
		  	retorno = false;
		 }
	}
	return retorno;
}

function validarQtdDiasRealizacao(){
	var retorno = true;
	var form = document.forms[0];
	if(form.qtdDiasRealizacao.value != ''){
		 if(parseInt(form.qtdDiasRealizacao.value) > parseInt(form.numeroDiasValidade.value)){
		  alert('O Número de Dias para Realização da Ação não pode ser superior ao Número de Dias de Validade da Ação');
		  form.qtdDiasRealizacao.focus();
		  retorno = false;
		 }
	}
	return retorno;
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

function validarCartaParcelamento(form){
   var retorno = true;

	if(form.idTipoDocumentoGerado.value == '37'){
		 if(form.idPrimeiraResolucaoDiretoria.value == ''){
			alert('Informe Número da 1ª R.D.');
			retorno = false;
		 }
		
		 if(form.idSegundaResolucaoDiretoria.value == ''){
			alert('Informe Número da 2ª R.D.');
			retorno = false;
		 }
		
		 if(form.idTerceiraResolucaoDiretoria.value == ''){
		 	alert('Informe Número da 3ª R.D.');
		 	retorno = false;
		 }
		
		 if(validaRadioButton(form.indicadorConsideraCreditoRealizar,"Considera Crédito a Realizar") != ""){
		 	alert('Informe Considera Crédito a Realizar.');
		  	retorno = false;
		 }
		
		 if(validaRadioButton(form.indicadorConsideraGuiaPagamento,"Considera Guia de Pagamento") != ""){
		 	alert('Informe Considera Guia de Pagamento.');
		  	retorno = false;
		 }
	}
	
	return retorno;
}

function verificarExibirRD(){

	var form = document.forms[0];
	form.action = 'exibirAtualizarAcaoCobrancaAction.do?mudouTipoDocumento=true';
	form.submit();
}

</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');habilitaNumeroDiasAcaoPredecessora();">

<html:form action="/atualizarAcaoCobrancaAction"
	name="AcaoCobrancaAtualizarActionForm"
	type="gcom.gui.cobranca.AcaoCobrancaAtualizarActionForm"
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
					<td class="parabg">Atualizar Ação de Cobrança</td>

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
					<p>Para atualizar a ação de cobrança, informe os dados abaixo:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="25%"><strong>Descrição da Ação de Cobrança:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="descricaoAcao" size="40" maxlength="40" tabindex="1"/></td>
				</tr>
				<tr> 
                    <td><strong>Número de Dias de Validade da Ação:<font color="#FF0000">*</font></strong></td>
				     <td width="25%"><html:text maxlength="4" property="numeroDiasValidade" size="4" tabindex="2"/>
				     </td>
              </tr>
              <tr> 
	                  <td><strong>Número de Dias para a Realização da Ação:</strong></td>
					 <td>
	                  		<html:text property="qtdDiasRealizacao" size="4"
							maxlength="4" tabindex="4" onblur="javascript:validarQtdDiasRealizacao();"/>
				  	  </td>
             </tr>
              <tr> 
                <td><strong>Ação Predecessora</strong></td>
                <td width="25%">
                    <html:select property="idAcaoPredecessora" tabindex="3" onchange="habilitaNumeroDiasAcaoPredecessora();">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoAcaoPredecessora" labelProperty="descricaoCobrancaAcao" property="id" />
					</html:select>
                </td>
              </tr>
              
               <tr> 
	                  <td><strong>Número de Dias entre a Ação e sua Predecessora:</strong></td>
					 <td>
	                  		<html:text property="numeroDiasEntreAcoes" size="4"
							maxlength="" tabindex="4"/>
				  	  </td>
             </tr>
             <tr> 
	                  <td><strong>Número de Dias de Vencimento:</strong></td>
					 <td>
	                  		<html:text property="numeroDiasVencimento" size="4"
							maxlength="8" tabindex="5"/>
				  	  </td>
             </tr>
             
             <tr> 
                <td><strong>Tipo de Documento a ser Gerado:<font color="#FF0000">*</font></strong></td>
                <td width="25%">
                    <html:select property="idTipoDocumentoGerado" tabindex="6" onchange="verificarExibirRD()">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoDocumentoTipo" labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select>
                </td>
              </tr>
              
              <tr> 
                <td><strong>Situação de Cobrança</strong></td>
                <td width="25%">
                    <html:select property="idSituacaoCobranca" tabindex="7">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoCobrancaSituacao">
							<html:options collection="colecaoCobrancaSituacao" labelProperty="descricao" property="id" />
							</logic:present>
					</html:select>
                </td>
              </tr>
              
              <tr> 
                <td><strong>Situação da Ligação de Água dos Imóveis</strong></td>
                <td width="25%">
                    <html:select property="idSituacaoLigacaoAgua" tabindex="7">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoLigacaoAguaSituacao" labelProperty="descricao" property="id" />
					</html:select>
                </td>
              </tr>
              <tr> 
                <td><strong>Situação da Ligação de Esgoto dos Imóveis</strong></td>
                <td width="25%">
                    <html:select property="idSituacaoLigacaoEsgoto" tabindex="8">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoLigacaoEsgotoSituacao" labelProperty="descricao" property="id" />
					</html:select>
                </td>
              </tr>  
              <tr> 
	            <td><strong>Critério de Cobrança:<font color="#FF0000">*</font></strong></td>
	            <td width="40%">
	               <html:text maxlength="6" 
	               		property="idCobrancaCriterio" 
	               		size="6" 
	               		tabindex="9"
					 	onkeypress="javascript:validaEnterComMensagem(event, 'exibirAtualizarAcaoCobrancaAction.do?objetoConsulta=1', 'idCobrancaCriterio', 'Critério de Cobrança');document.forms[0].descricaoCobrancaCriterio.value = '';" />
						
						<a href="javascript:abrirPopup('exibirPesquisarCriterioCobrancaAction.do',400,600);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message 
								key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Critério de Cobrança" />
						</a>
						<logic:notEmpty name="AcaoCobrancaAtualizarActionForm" property="idCobrancaCriterio">
							<html:text property="descricaoCobrancaCriterio" 
								size="38" 
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<logic:empty name="AcaoCobrancaAtualizarActionForm" property="idCobrancaCriterio">
						 <html:text property="descricaoCobrancaCriterio" 
					 		size="38" 
					 		maxlength="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty> 
						<a href="javascript:limparCriterio();">
							<img src="<bean:message 
								key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
				</td>
              </tr>
              <tr> 
	            <td><strong>Tipo de Serviço da Ordem de Serviço a ser Gerada:</strong></td>
	            <td>
	               <html:text maxlength="4" 
	               		property="idServicoTipo" 
	               		size="4" 
	               		tabindex="10"
					 	onkeypress="javascript:validaEnterComMensagem(event, 'exibirAtualizarAcaoCobrancaAction.do?objetoConsulta=1', 'idServicoTipo', 'Tipo de Serviço');document.forms[0].descricaoServicoTipo.value = '';" />
						<a href="javascript:abrirPopup('exibirPesquisarTipoServicoAction.do',400,600);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message 
									key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Tipo de Serviço" />
						</a>
						<logic:notEmpty name="AcaoCobrancaAtualizarActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" 
								size="38" 
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<logic:empty name="AcaoCobrancaAtualizarActionForm" property="idServicoTipo">
						 <html:text property="descricaoServicoTipo" 
						 	size="38" 
						 	maxlength="30" 
						 	readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty> 
						<a href="javascript:limparServicoTipo();">
							<img 
								src="<bean:message 
									key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
				</td>
              </tr>
              <tr> 
	               <td><strong>Ordem no Cronograma:</strong></td>
				   <td>
	                	<html:text property="ordemCronograma" size="4"
							maxlength="4" tabindex="11"/>
				  	  </td>
             </tr>
             
             <logic:equal name="AcaoCobrancaAtualizarActionForm" property="idTipoDocumentoGerado" value="37">
             <tr> 
	                <td><strong>Número da 1ª R.D.:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="idPrimeiraResolucaoDiretoria" tabindex="12">
								<html:option value="">&nbsp;</html:option>
								<logic:present name="colecaoResolucaoDiretoriaAtualizar">
								<html:options collection="colecaoResolucaoDiretoriaAtualizar" labelProperty="numeroResolucaoDiretoria" property="id" />
								</logic:present>
						</html:select>
	                </td>
              </tr>
              <tr> 
	                <td><strong>Número da 2ª R.D.:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="idSegundaResolucaoDiretoria" tabindex="13">
								<html:option value="">&nbsp;</html:option>
								<logic:present name="colecaoResolucaoDiretoriaAtualizar">
								<html:options collection="colecaoResolucaoDiretoriaAtualizar" labelProperty="numeroResolucaoDiretoria" property="id" />
								</logic:present>
						</html:select>
	                </td>
              </tr>
              <tr> 
	                <td><strong>Número da 3ª R.D.:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="idTerceiraResolucaoDiretoria" tabindex="14">
								<html:option value="">&nbsp;</html:option>
								<logic:present name="colecaoResolucaoDiretoriaAtualizar">
								<html:options collection="colecaoResolucaoDiretoriaAtualizar" labelProperty="numeroResolucaoDiretoria" property="id" />
								</logic:present>
						</html:select>
	                </td>
              </tr>
              </logic:equal>
              
              <logic:notEqual name="AcaoCobrancaAtualizarActionForm" property="idTipoDocumentoGerado" value="37">
              <tr> 
	                <td><strong>Número da 1ª R.D.:</strong></td>
	                <td width="25%">
	                    <html:select property="idPrimeiraResolucaoDiretoria" disabled="disabled" tabindex="12">
								<html:option value="">&nbsp;</html:option>
						</html:select>
	                </td>
              </tr>
              <tr> 
	                <td><strong>Número da 2ª R.D.:</strong></td>
	                <td width="25%">
	                    <html:select property="idSegundaResolucaoDiretoria" disabled="disabled" tabindex="13">
								<html:option value="">&nbsp;</html:option>
						</html:select>
	                </td>
              </tr>
              <tr> 
	               <td><strong>Número da 3ª R.D.:</strong></td>
	               <td width="25%">
	                   <html:select property="idTerceiraResolucaoDiretoria" disabled="disabled" tabindex="14">
							<html:option value="">&nbsp;</html:option>
					</html:select>
	               </td>
              </tr>
              </logic:notEqual>
              <tr> 
	               <td><strong>Efeito da Ação:<font color="#FF0000">*</font></strong></td>
				   <td>
	                	<html:select property="acaoCobrancaEfeito"  tabindex="15">
	                		<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoAcaoCobrancaEfeito" labelProperty="descricao" property="id" />
						</html:select>
			  	   </td>
             </tr>
             
              <tr> 
	               <td><strong>Negativador:</strong></td>
				   <td>
	                	<html:select property="negativador"  tabindex="16">
	                		<html:option value=""/>
	                		<logic:present name="colecaoNegativador">
								<html:options collection="colecaoNegativador" labelProperty="cliente.nome" property="id" />
							</logic:present>
						</html:select>
			  	   </td>
             </tr>
            
               <tr> 
                <td height="24" colspan="2"></td>
              </tr>
           
              <tr> 
                <td colspan="2">
                <table width="100%">
                
                 <logic:equal name="AcaoCobrancaAtualizarActionForm" property="idTipoDocumentoGerado" value="37">
	            <tr>
	                 <td width="45%"><strong>Considera Crédito a Realizar:<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio property="indicadorConsideraCreditoRealizar" value="1" tabindex="16"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="indicadorConsideraCreditoRealizar" value="2" tabindex="17"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             <tr>
	                 <td width="45%"><strong>Considera Guia de Pagamento:<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio property="indicadorConsideraGuiaPagamento" value="1" tabindex="18"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="indicadorConsideraGuiaPagamento" value="2" tabindex="19"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             </logic:equal>
	             <logic:notEqual name="AcaoCobrancaAtualizarActionForm" property="idTipoDocumentoGerado" value="37">
	             <tr>
	                 <td width="45%"><strong>Considera Crédito a Realizar:<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio disabled="true" property="indicadorConsideraCreditoRealizar" value="1" tabindex="16"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio disabled="true" property="indicadorConsideraCreditoRealizar" value="2" tabindex="17"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             <tr>
	                 <td width="45%"><strong>Considera Guia de Pagamento:<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio disabled="true" property="indicadorConsideraGuiaPagamento" value="1" tabindex="18"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio disabled="true" property="indicadorConsideraGuiaPagamento" value="2" tabindex="19"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             </logic:notEqual>
	              <tr>
	                 <td width="45%"><strong>Considera Cliente Usuário Sem CPF/CNPJ?<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio property="icClienteUsuarioSemCPFCNPJ" value="1" tabindex="16"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="icClienteUsuarioSemCPFCNPJ" value="2" tabindex="17"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
	             <tr>
	                 <td width="45%"><strong>Considera Endereço Sem CEP?<font color="#FF0000">*</font></strong></td>
	                 <td><html:radio property="icEnderecoSemCEP" value="1" tabindex="16"/> 
	                   <strong>Sim</strong></td>
	                 <td><html:radio property="icEnderecoSemCEP" value="2" tabindex="17"/> 
	                     <strong>N&atilde;o</strong></td>
	             </tr>
                 <tr>
                  <td width="45%"><strong>Compõe o Cronograma:<font color="#FF0000">*</font></strong></td>
                  <td><html:radio property="icCompoeCronograma" value="1" tabindex="20"/> 
                    <strong>Sim</strong></td>
                  <td><html:radio property="icCompoeCronograma" value="2" tabindex="21"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Ação Obrigatória:<font color="#FF0000"></font><font color="#FF0000">*</font></strong></td>
                  <td><html:radio property="icAcaoObrigatoria" value="1" tabindex="22"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icAcaoObrigatoria" value="2" tabindex="23"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Pode ser Repetida no Ciclo:<font color="#FF0000"></font><font color="#FF0000">*</font></strong></td>
                  <td><html:radio property="icRepetidaCiclo" value="1" tabindex="24"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icRepetidaCiclo" value="2" tabindex="25"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Provoca Suspensão de Abastecimento:<font color="#FF0000"></font><font color="#FF0000">*</font></strong></td>
                  <td><html:radio property="icSuspensaoAbastecimento" value="1" tabindex="26"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icSuspensaoAbastecimento" value="2" tabindex="27"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Considera Débitos a Cobrar:<font color="#FF0000">*</font></strong></td>
                  <td><html:radio property="icDebitosACobrar" value="1" tabindex="28"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icDebitosACobrar" value="2" tabindex="29"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Considera Acréscimos por Impontualidade:<font color="#FF0000">*</font></strong></td>

                  <td><html:radio property="icAcrescimosImpontualidade" value="1" tabindex="30"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icAcrescimosImpontualidade" value="2" tabindex="31"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Gera Taxa:<font color="#FF0000">*</font></strong></td>

                  <td><html:radio property="icGeraTaxa" value="1" tabindex="32"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icGeraTaxa" value="2" tabindex="33"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Pode Emitir Boletins de Cadastro:<font color="#FF0000">*</font></strong></td>

                  <td><html:radio property="icEmitirBoletimCadastro" value="1" tabindex="34"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icEmitirBoletimCadastro" value="2" tabindex="35"/> 
                      <strong>N&atilde;o</strong></td>
                 </tr>
                 <tr> 
                  <td width="45%"><strong>Pode ser Executada para Imóveis sem Débito:<font color="#FF0000">*</font></strong></td>

                  <td><html:radio property="icImoveisSemDebitos" value="1" tabindex="36"/> 
                      <strong>Sim</strong></td>
                  <td><html:radio property="icImoveisSemDebitos" value="2" tabindex="37"/> 
                      <strong>N&atilde;o</strong></td>
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
							<td valign="top">
							<logic:present name="voltar">
								<logic:equal name="voltar" value="filtrar">
									<input name="Button" type="button" class="bottonRightCol"
									value="Voltar" align="left" tabindex="39"
									onclick="window.location.href='<html:rewrite page="/exibirFiltrarAcaoCobrancaAction.do"/>'">
								</logic:equal>
								<logic:equal name="voltar" value="manter">
									<input name="Button" type="button" class="bottonRightCol"
									value="Voltar" align="left" tabindex="39"
									onclick=";window.location.href='<html:rewrite page="/exibirManterAcaoCobrancaAction.do"/>'">
								</logic:equal>
							</logic:present>
							<logic:notPresent name="voltar">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left" tabindex="39"
								onclick="window.location.href='<html:rewrite page="/exibirManterAcaoCobrancaAction.do"/>'">
							</logic:notPresent>
							&nbsp;
							<input name="button" type="button"
								class="bottonRightCol" value="Desfazer" tabindex="40" onclick="desfazer();">&nbsp;<input type="button"
								name="ButtonCancelar" class="bottonRightCol" value="Cancelar" tabindex="41"
								onClick="window.location.href='/gsan/telaPrincipal.do'"></td>
							<td valign="top">
							  <div align="right">
							    <gcom:controleAcessoBotao name="botaoAtualizar" value="Atualizar" onclick="validarForm(document.forms[0]);" url="atualizarAcaoCobrancaAction.do" tabindex="38"/>
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

