<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.cobranca.CobrancaCriterioLinha" %>

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
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="CriterioCobrancaActionForm" dynamicJavascript="false" />

<style type="text/css">

.desabilitar {
 background-color:#EFEFEF;
 border:0;
 color: #000000;
}

</style>

<script language="JavaScript">

 var bCancel = false;

    function validateCriterioCobrancaActionForm(form) {
        if (bCancel)
      return true;
        else
      return validateRequired(form) && validateCaracterEspecial(form) && testarCampoValorZero(form.numeroAnoContaAntiga, 'Número de Anos para Determinar Conta Antiga') && validateLong(form) 
		&& validateDate(form) && validateDecimal(form) && validaTodosRadioButton(); 
   }

    function caracteresespeciais () {
     this.aa = new Array("descricaoCriterio", "Descrição do Critério de Cobrança possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     
    }

    function IntegerValidations () {
     this.ac = new Array("numeroAnoContaAntiga", "Número de Anos para Determinar Conta Antiga deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    function required () { 
	this.aa = new Array("descricaoCriterio", "Informe Descrição do Critério de Cobrança.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("dataInicioVigencia", "Informe Data de Início de Vigência do Critério.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("numeroAnoContaAntiga", "Informe Número de Anos para Determinar Conta Antiga.", new Function ("varName", " return this[varName];"));
	this.af = new Array("valorLimitePrioridade", "Informe Valor Limite para Prioridade.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("percentualValorMinimoPagoParceladoCancelado", "Informe Percentual Valor.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("percentualQuantidadeMinimoPagoParceladoCancelado", "Informe Percentual Quantidade de Itens.", new Function ("varName", " return this[varName];"));
	this.bg = new Array("comCpf", "Informe Com CPF.", new Function ("varName", " return this[varName];"));
	this.bh = new Array("comTelefone", "Informe Com Telefone.", new Function ("varName", " return this[varName];"));

	//this.ba = new Array("idsSituacaoLigacaoAgua", "Informe Situação de Ligação de Água.", new Function ("varName", " return this[varName];"));
	//this.bb = new Array("idsSituacaoLigacaoEsgoto", "Informe Situação de Ligação de Esgoto.", new Function ("varName", " return this[varName];"));
	} 
    function DateValidations () { 
	  this.aa = new Array("dataInicioVigencia", "Data de Início de Vigência do Critério inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 
    
	function FloatValidations () {
	 this.an = new Array("percentualValorMinimoPagoParceladoCancelado", "Percentual Valor deve somente conter números decimais positivos.",new Function ("varName", " return this[varName];"));
	 this.ao = new Array("percentualQuantidadeMinimoPagoParceladoCancelado", "Percentual Quantidade de Itens deve somente conter números decimais positivos.",new Function ("varName", " return this[varName];"));
	
	} 
    
function validaRadioButton(nomeCampo,mensagem){
	var alerta = "";
	var terceiroCampo = nomeCampo[2];
		
	if((!nomeCampo[0].checked && !nomeCampo[1].checked) && (terceiroCampo == null || !terceiroCampo.checked)){
		alerta =  "Informe " + mensagem +".";
	}
	return alerta;
}

function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(validaRadioButton(form.indicadorConsiderarApenasDebitoTitularAtual,"Considerar apenas o Débito do Titular Atual do Imóvel") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorConsiderarApenasDebitoTitularAtual,"Considerar apenas o Débito do Titular Atual do Imóvel")+"\n";
	}
	if(validaRadioButton(form.opcaoAcaoImovelSitEspecial,"Emissão da Ação para Imóvel com Sit. Especial de Cobrança") != ""){
			mensagem = mensagem + validaRadioButton(form.opcaoAcaoImovelSitEspecial,"Emissão da Ação para Imóvel com Situação Especial de Cobrança")+"\n";
	}
	if(validaRadioButton(form.opcaoAcaoImovelSit,"Emissão da Ação para Imóvel com Sit. de Cobrança") != ""){
		mensagem = mensagem + validaRadioButton(form.opcaoAcaoImovelSit,"Emissão da Ação para Imóvel com Situação de Cobrança")+"\n";
	}
	if(validaRadioButton(form.opcaoContasRevisao,"Considerar Contas em Revisão") != ""){
		mensagem = mensagem + validaRadioButton(form.opcaoContasRevisao,"Considerar Contas em Revisão")+"\n";
	}
	if(validaRadioButton(form.opcaoAcaoImovelDebitoMesConta,"Emissão da Ação para Imóvel com Débito só da Conta do Mês") != ""){
		mensagem = mensagem + validaRadioButton(form.opcaoAcaoImovelDebitoMesConta,"Emissão da Ação para Imóvel com Débito só da Conta do Mês")+"\n";
	}
	if(validaRadioButton(form.opcaoAcaoInquilinoDebitoMesConta,"Emissão da Ação para Inquilino Com Débito só da Conta do Mês Independentemente do Valor da Conta") != ""){
		mensagem = mensagem + validaRadioButton(form.opcaoAcaoInquilinoDebitoMesConta,"Emissão da Ação para Inquilino Com Débito só da Conta do Mês Independentemente do Valor da Conta")+"\n";
	}
	if(validaRadioButton(form.opcaoAcaoImovelDebitoContasAntigas,"Emissão da Ação para Imóvel com Débito só de Contas Antigas") != ""){
		mensagem = mensagem + validaRadioButton(form.opcaoAcaoImovelDebitoContasAntigas,"Emissão da Ação para Imóvel com Débito só de Contas Antigas")+"\n";
	}
	if(validaRadioButton(form.comCpf,"Com CPF") != ""){
		mensagem = mensagem + validaRadioButton(form.comCpf,"Com CPF")+"\n";
	}
	if(validaRadioButton(form.comTelefone,"Com Telefone") != ""){
		mensagem = mensagem + validaRadioButton(form.comTelefone,"Com Telefone")+"\n";
	}
 
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}


function validarForm(form){

if(validateCriterioCobrancaActionForm(form)){
    document.forms[0].target='';
    form.action="inserirCriterioCobrancaAction.do";
    submeterFormPadrao(form);
}
}

function abrirPopupComSubmit(form,altura, largura){
 var height = window.screen.height - 160;
 var width = window.screen.width;
 var top = (height - altura)/2;
 var left = (width - largura)/2;
 window.open('', 'Pesquisar','top=' + top + ',left='+ left +',location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=' + largura + ',height=' + altura);
 form.target='Pesquisar';
 form.action = 'exibirAdicionarCriterioCobrancaLinhaAction.do?limparPopup=SIM&retornarTela=exibirInserirCriterioCobrancaAction.do';
 submeterFormPadrao(form);
}

function verificaValorMaior(minimo,maximo){
 if(minimo!= "" && maximo!= ""){
  minimo = minimo * 1;
  maximo = maximo * 1;
  if(minimo > maximo){
   alert('Valor máximo de débito é menor do que o valor mínimo do débito.');
  }
 }
}

function verificaQuantidadeMaior(minimo,maximo){
 if(minimo!= "" && maximo!= ""){
  minimo = minimo * 1;
  maximo = maximo * 1;
  if(minimo > maximo){
   alert('Quantidade máxima de contas é menor do que a quantidade mínima de contas.');
  }
 }
}
function desfazer(){
 form = document.forms[0];
 form.action='exibirInserirCriterioCobrancaAction.do?limpaSessao=1';
 form.submit();
}

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
	}
}

function habilitarSelectComCor(radio, select, posicaoCursor){
	  
   if(radio[0].checked){
	   
     	select.disabled = false;
     	select.className = '';
     
   }else{
   	 	select.disabled = true;
   	 	select.selectedIndex = posicaoCursor;
   		select.className = 'desabilitar';
   }
}


	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0);habilitarSelectComCor(document.forms[0].opcaoAcaoImovelSit, document.forms[0].idsCobrancaSituacao, -1);">

<html:form action="/inserirCriterioCobrancaAction"
	name="CriterioCobrancaActionForm"
	type="gcom.gui.cobranca.CriterioCobrancaActionForm"
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
					<td class="parabg">Inserir Critério de Cobrança</td>

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
					<p>Para adicionar o critério de cobrança, informe os dados abaixo:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>Descrição do Critério de Cobrança:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="descricaoCriterio"	size="30" maxlength="30" tabindex="1"/></td>
				</tr>
				
				<tr>
				<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<tr>
				<td height="1px" bgcolor="#000000" width="100%" colspan="9"></td>
				</tr>
				
				<tr>
				<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				
			   <tr> 
                <td><strong>Linhas do Crit&eacute;rio<font color="#FF0000">*</font></strong></td>
                <td align="right"> <input type="button" name="Submit24" class="bottonRightCol" value="Adicionar" onClick="abrirPopupComSubmit(document.forms[0],'','');" tabindex="2"> 
                </td>
               </tr>

			   <tr>
				<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr bordercolor="#000000"> 
					      <td bgcolor="#90c7fc" align="center" width="15%" height="20"><div align="center"><strong>Remover</strong></div></td>
					      <td bgcolor="#90c7fc" width="40%" height="20"><strong>Perfil do Imóvel</strong></td>
					      <td bgcolor="#90c7fc" width="40%" height="20"><strong>Categoria</strong></td>
					   </tr>
						<logic:present name="colecaoCobrancaCriterioLinha">
							<tr>
								<td colspan="3">

								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" bgcolor="#99CCFF">

									<%int cont = 1;%>
									<logic:iterate name="colecaoCobrancaCriterioLinha"
										id="cobrancaCriterioLinha" type="CobrancaCriterioLinha">
										<%cont = cont + 1;
							if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

							%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="15%">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remoção?')){redirecionarSubmit('removerCriterioCobrancaLinhaAction.do?codigoCobrancaCriterioLinha=<bean:write name="cobrancaCriterioLinha" property="imovelPerfil.id"/>,<bean:write name="cobrancaCriterioLinha" property="categoria.id"/>&tipoRetorno=inserir');}" />
												</font></div>
											</td>
											<td width="40%"><bean:write name="cobrancaCriterioLinha" property="imovelPerfil.descricao"/></td>
											<td width="40%"><a href="javascript:abrirPopup('exibirAtualizarCriterioCobrancaLinhaAction.do?parmsImovelPerfilCobranca=<bean:write name="cobrancaCriterioLinha" property="imovelPerfil.id"/>,<bean:write name="cobrancaCriterioLinha" property="categoria.id"/>&retornarTela=exibirInserirCriterioCobrancaAction.do','','')"><bean:write name="cobrancaCriterioLinha" property="categoria.descricao"/></a></td>
										</tr>										
											
									</logic:iterate>

								</table>
								</div>
								</td>
							</tr>
						</logic:present>
					</table>
					</td>
				</tr>
				
				<tr>
				<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<tr>
				<td height="1px" bgcolor="#000000" width="100%" colspan="9"></td>
				</tr>
				
				<tr>
				<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				
					<tr>
					<td>
						<strong>Situa&ccedil;&atilde;o de Liga&ccedil;&atilde;o de &Aacute;gua:</strong>
					</td>
					<td colspan=3>
                 		<logic:present name="desabilita">
                 			<html:select property="idsSituacaoLigacaoAgua" multiple="multiple" disabled="true" tabindex="3">
              					<html:options collection="colecaoSituacaoLigacaoAgua" labelProperty="descricao" property="id"/>
             				</html:select>
           				</logic:present>
                 		<logic:notPresent name="desabilita">
                 			<html:select property="idsSituacaoLigacaoAgua" multiple="multiple" tabindex="3">
              					<html:options collection="colecaoSituacaoLigacaoAgua" labelProperty="descricao" property="id"/>
             				</html:select>
           				</logic:notPresent>
                 	</td>
                 </tr> 
               
               	<tr>
					<td>
						<strong>Situa&ccedil;&atilde;o de Liga&ccedil;&atilde;o de Esgoto:</strong>
					</td>
					<td colspan=3>
                 		<logic:present name="desabilita">                 	
                 			<html:select property="idsSituacaoLigacaoEsgoto" multiple="multiple" disabled="true" tabindex="4">
              					<html:options collection="colecaoSituacaoLigacaoEsgoto" labelProperty="descricao" property="id"/>
             				</html:select>
             			</logic:present>
                 		<logic:notPresent name="desabilita">                 	
                 			<html:select property="idsSituacaoLigacaoEsgoto" multiple="multiple" tabindex="4">
              					<html:options collection="colecaoSituacaoLigacaoEsgoto" labelProperty="descricao" property="id"/>
             				</html:select>
             			</logic:notPresent>             		
                 	</td>
                 </tr>              
				
			<!--============================ Alteração do caso de uso UC0316 ============================ -->
				<tr>
					<td colspan="4">
					<div id="layerHideLigacaoAgua" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',true);" /> <b>Critérios Adicionais
								</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLigacaoAgua" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',false);" /> <b>Critérios Adicionais
								</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table width="100%" bgcolor="#99CCFF">

								<tr> 
				                    <td><strong>Data de In&iacute;cio de Vig&ecirc;ncia do Crit&eacute;rio:<font color="#FF0000">*</font></strong></td>
								     <td width="40%"><html:text maxlength="10" property="dataInicioVigencia" size="10" onkeyup="javascript:mascaraData(this,event);" tabindex="5"/>
				                      <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('CriterioCobrancaActionForm', 'dataInicioVigencia')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa </td>
				              </tr>
				              <tr> 
				                <td><p><strong>N&uacute;mero de Anos para Determinar Conta Antiga</strong><strong>:<font color="#FF0000">*</font></strong></p></td>
				                <td width="40%"><html:text property="numeroAnoContaAntiga" size="2" maxlength="2" tabindex="6" onkeyup="javascript:verificaNumeroInteiro(this);"/> 
				                </td>
				
				              </tr>
				              
               				<tr> 
			                  <td><strong>Valor limite para prioridade:<font color="#FF0000">*</font></strong></td>
							 <td>
			                  		<html:text property="valorLimitePrioridade" size="14"
									maxlength="14" tabindex="7" 
									onkeyup="formataValorMonetario(this, 13)" 
									style="text-align:right;" />
						  	  </td>
                 			 </tr>
                 
                 
			                 <tr> 
				                  <td><strong>Documento Pago/Parcelado/Cancelado:</strong></td>
				             </tr>     
			                 <tr>
				                 <td colspan="2">
					                 <table width="100%">
					                 
					                 
					          <tr> 
					             <td width="2%">&nbsp;</td>
				                  <td width="48%"><strong>  Percentual Valor:<font color="#FF0000">*</font></strong></td>
								 	<td>
				                  		<html:text property="percentualValorMinimoPagoParceladoCancelado" size="6"
										maxlength="6" tabindex="8" 
										onkeyup="formataValorMonetario(this, 5)" 
										style="text-align:right;" />
							  	    </td>
			                  </tr>
			                 
			                  <tr> 
			                 	 <td width="2%">&nbsp;</td>
				                  <td width="48%"><strong>  Percentual Quantidade de Itens:<font color="#FF0000">*</font></strong></td>
								 <td>
				                  		<html:text property="percentualQuantidadeMinimoPagoParceladoCancelado" size="6"
										maxlength="6" tabindex="9" 
										onkeyup="formataValorMonetario(this, 5)" 
										style="text-align:right;" />
							  	  </td>
			                  </tr>
					                 
					           <tr> 
			                 	 <td width="2%">&nbsp;</td>
				                  <td width="48%"><strong>  Quantidade dias cortado:</strong></td>
								 <td>
				                  		<html:text property="qtdDiasCortado" size="6" maxlength="6" tabindex="10" style="text-align:right;" onkeyup="javascript:verificaNumeroInteiro(this);"/>
							  	  </td>
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
			                 	<td width="65%">
			                 		<strong>Considerar apenas o Débito do Titular Atual do Imóvel:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="indicadorConsiderarApenasDebitoTitularAtual" value="<%=""+ConstantesSistema.SIM%>" onclick="javascript:habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0)"/><strong>Sim</strong>			    
			                  		<html:radio property="indicadorConsiderarApenasDebitoTitularAtual" value="<%=""+ConstantesSistema.NAO%>" onclick="javascript:habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0)"/><strong>N&atilde;o</strong>
			                   	</td>
			                 </tr>
			                  
			                  <tr>
								<td width="65%">
									<strong>Titular Atual do Débito do Imóvel:</strong>
								</td>
								<td>
		                 			<html:select property="idClienteRelacaoTipo">
		              					<html:options collection="colecaoClienteRelacaoTipo" labelProperty="descricao" property="id"/>
		             				</html:select>           		
			                 	</td>
			                 </tr>
			                 
			                 <tr>
			                 	<td width="65%">
			                 		<strong>
			                 			Emiss&atilde;o da A&ccedil;&atilde;o para Im&oacute;vel com Situação Especial de Cobran&ccedil;a:
			                 		<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoAcaoImovelSitEspecial" value="1" tabindex="11"/><strong>Sim</strong>
			                    </td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoAcaoImovelSitEspecial" value="2" tabindex="12"/><strong>N&atilde;o</strong>
			                  	</td>
			                 </tr>
			                 <tr> 
			                 	<td>
			                 		<strong>Emiss&atilde;o da A&ccedil;&atilde;o para Im&oacute;vel com Situação de Cobran&ccedil;a:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoAcaoImovelSit" value="1" tabindex="13" onclick="javascript:habilitarSelectComCor(document.forms[0].opcaoAcaoImovelSit, document.forms[0].idsCobrancaSituacao, -1)"/><strong>Sim</strong>
			                    </td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoAcaoImovelSit" value="2" tabindex="14" onclick="javascript:habilitarSelectComCor(document.forms[0].opcaoAcaoImovelSit, document.forms[0].idsCobrancaSituacao, -1)"/><strong>N&atilde;o</strong>
			                  	</td>
			                 </tr>
			 				 <tr>
								<td>
									<strong>Situa&ccedil;&atilde;o de cobran&ccedil;a:</strong>
								</td>                 
			                 	<td colspan="3">
			                 		<logic:present name="desabilita">
			                 			<html:select property="idsCobrancaSituacao" multiple="multiple" disabled="true" name="CriterioCobrancaActionForm" size="8" tabindex="15">
			              					<html:options name="session" collection="colecaoCobrancaSituacao" labelProperty="descricao" property="id" />
			             				</html:select>
			             			</logic:present>
			                 		<logic:notPresent name="desabilita">
			                 			<html:select property="idsCobrancaSituacao" multiple="multiple"  size="8" tabindex="16">
			              					<html:options collection="colecaoCobrancaSituacao" labelProperty="descricao" property="id"/>
			             				</html:select>
			             			</logic:notPresent>             		
			                 	</td>
			                 </tr>                   
			                 <tr> 
			                 	<td>
			                 		<strong>Considerar Contas em Revis&atilde;o:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoContasRevisao" value="1" tabindex="17"/><strong>Sim</strong>
			                    </td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoContasRevisao" value="2" tabindex="18"/><strong>N&atilde;o</strong>
			                   	</td>
			                 </tr>
			                 <tr> 
			                 	<td>
			                 		<strong>Emiss&atilde;o da A&ccedil;&atilde;o para Im&oacute;vel com D&eacute;bito s&oacute; da Conta do M&ecirc;s:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoAcaoImovelDebitoMesConta" value="1" tabindex="19"/><strong>Sim</strong>
			                  	</td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoAcaoImovelDebitoMesConta" value="2" tabindex="20"/><strong>N&atilde;o</strong>
			                  	</td>
			                 </tr>
			                 <tr> 
			                 	<td>
			                 		<strong>Emiss&atilde;o da A&ccedil;&atilde;o para Inquilino Com D&eacute;bito s&oacute; da Conta do M&ecirc;s Independentemente do Valor da Conta:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoAcaoInquilinoDebitoMesConta" value="1" tabindex="21"/><strong>Sim</strong>
			                  	</td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoAcaoInquilinoDebitoMesConta" value="2" tabindex="22"/><strong>N&atilde;o</strong>
			                  	</td>
			                 </tr>
			                 <tr> 
			                 	<td>
			                 		<strong>Emiss&atilde;o da A&ccedil;&atilde;o para Im&oacute;vel com D&eacute;bito s&oacute; de Contas Antigas:<font color="#FF0000">*</font></strong>
			                 	</td>
			                  	<td>
			                  		<html:radio property="opcaoAcaoImovelDebitoContasAntigas" value="1" tabindex="23"/><strong>Sim</strong>
			                  	</td>
			                  	<td colspan="2">
			                  		<html:radio property="opcaoAcaoImovelDebitoContasAntigas" value="2" tabindex="24"/><strong>N&atilde;o</strong>
			                  	</td>
			                 </tr>
			                 
			                 <tr>
			                 	<td>
			                 		<strong>
			                 			Com CPF:<font color="#FF0000">*</font>
			                 		</strong>
			                 	</td>
			                 	<td>
			                 		<html:radio property="comCpf" value="1" tabindex="25">
			                 			 <strong>
			                 			 	Sim
			                 			 </strong>
			                 		</html:radio>                 	
			                 	</td>
			                 	<td>
			                 		<html:radio property="comCpf" value="2" tabindex="26">
			                 			<strong>
			                 				Não
			                 			</strong>
			                 		</html:radio>
			                 	</td>
			                 	<td>
			                 		<html:radio property="comCpf" value="3" tabindex="27">
			                 			<strong>
			                 				Ambos
			                 			</strong>
			                 		</html:radio>                 	
			                 	</td>
			               	</tr>
			                
			                <tr>
			                 	<td>
			                 		<strong>
			                 			Com Telefone:<font color="#FF0000">*</font>
			                 		</strong>                 	
			                 	</td>
			                 	<td>
			                 		<html:radio property="comTelefone" value="1" tabindex="28">
			                 			<strong>
			                 			 	Sim
			                 			</strong>                 		
			                 		</html:radio>                 	
			                 	</td>
			                 	<td>
			                 		<html:radio property="comTelefone" value="2" tabindex="29">
			                 			<strong>
			                 				Não
			                 			</strong>
			                 		</html:radio>   
			                 	</td>
			                 	<td>
			                 		<html:radio property="comTelefone" value="3" tabindex="30">
			                 			<strong>
			                 				Ambos
			                 			</strong>
			                 		</html:radio>                 	
			                 	</td>
			               	</tr>
					
					

							</table>
							</td>
						</tr>

					</table>
					</div>
					</td>
				</tr>
				
 				<!--============================ Alteração do caso de uso UC0316 ============================ -->
				
				
				
				
                 
 			    
                 
                 </table>
                </td>
               </tr>  
			   <tr> 
                <td height="24" colspan="2"></td>
               </tr>
 
				<tr>
        </tr>
        <tr>
					<td colspan="0">
					</td>
					<td>&nbsp;</td>
					<td ><strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'"></td>
					<td valign="top">
					  <div align="right">
					    <gcom:controleAcessoBotao name="botaoInserir" value="Inserir" onclick="validarForm(document.forms[0]);" url="inserirCriterioCobrancaAction.do" tabindex="23"/>
					    <%-- <input name="botaoInserir" type="button" class="bottonRightCol" value="Inserir" onclick="validarForm(document.forms[0]);" tabindex="17">--%>
					  </div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
