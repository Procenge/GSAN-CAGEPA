<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.cadastro.imovel.ImovelContaEnvio"%>
<%@ page import="gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirImovelActionForm"
	dynamicJavascript="false" staticJavascript="false" page="3" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">
<!-- Begin

     var bCancel = false;

    function validateInserirImovelActionForm(form) {
        if (bCancel)
      		return true;
        else
       		return testarCampoValorZero(document.InserirImovelActionForm.idCliente, 'Código') && validateCaracterEspecial(form) && validateLong(form)  && validateRequired(form) ;
   }
    
    function caracteresespeciais () {

     this.ad = new Array("idCliente", "Código deve somente conter números positivos.", new Function ("varName", " return this[varName];"));

    }

    function IntegerValidations () {
     this.ac = new Array("idCliente", "Código deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function required () {
     this.ad = new Array("idClienteImovelProprietario", "Informe um cliente do tipo proprietário.", new Function ("varName", " return this[varName];"));
    }


    function verificarAdicionar() {   	
  
      var form = document.forms[0];
	  if(form.tipoClienteImovel.value == "-1"){
			alert("Informe Tipo do Cliente.");
	  }else if (form.idCliente.value != '') {
        if(form.idCliente.value == '0'){
      	  alert("Código deve somente conter números positivos.");
		}else if(form.idCliente.value < '0'){
		  alert("Código deve somente conter números positivos.");
		}else{
		  if(!validateCaracterEspecial(form)){
    	        alert("Código deve somente conter números positivos.");
        	 }else if(!validateLong(form)){

		  }else{
			if (validateDate(form)){ 
    	    	form.action='inserirImovelWizardAction.do?action=adicionarInserirImovelClienteAction&indicadorNomeConta=SIM';
	    		//form.submit(); Mudado para a tela de espera
	    		submitForm(form);
			}
		  }
	    }
      } else {
		 alert("Informe Código.");
      }
  }

//Recebe os dados do(s) popup(s)

  function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

     if (tipoConsulta == 'cliente') {
      form.idCliente.value = codigoRegistro;
      form.nomeCliente.value = descricaoRegistro;
      form.nomeCliente.style.color = "#000000";
      form.action='inserirImovelWizardAction.do?action=exibirInserirImovelClienteAction';
      submeterFormPadrao(form);
    }
  }

function limparClienteImovel() {
    var form = document.forms[0];

    form.idCliente.value = "";
    form.nomeCliente.value = "";


  }

function validarNomeConta(valor){

	   var formulario = document.forms[0]; 
 	   formulario.nomeContaSelecionado.value = valor;
       formulario.action='inserirImovelWizardAction.do?action=exibirInserirImovelClienteAction&indicadorNomeConta=SIM';
  	   formulario.submit(); 				

}

function verficarSelecao(objeto, tipoObjeto){
     
       var indice;
       var array = new Array(objeto.length);
	   var formulario = document.forms[0]; 
	   var cont = 0;
		
	   for(indice = 0; indice < formulario.elements.length; indice++){

		 if (formulario.elements[indice].type == tipoObjeto && formulario.elements[indice].checked == true) {

			cont = cont +1;	
		 }	
	   }
	   
        if (cont < 1) {

	       var array = new Array(objeto.length);
		   var formulario = document.forms[0]; 
	
		   for(indice = 0; indice < formulario.elements.length; indice++){
			  
		     var valor = formulario.elements[indice].value;
		     
			 if (formulario.elements[indice].type == tipoObjeto && formulario.elements[indice].checked == false  && formulario.elements[indice].value.substring(0,7) == "USUARIO") {
				 
				
				 formulario.elements[indice].checked = true;
 				 indice = formulario.elements.length;
 				 
 				 formulario.nomeContaSelecionado.value = valor;
 				 
    	         formulario.action='inserirImovelWizardAction.do?action=exibirInserirImovelClienteAction&indicadorNomeConta=SIM';
	    	     formulario.submit(); 				
			 }
		   }
       
       }
   }


function removerCliente(url){

	if(confirm('Confirma remoção ?')){
       var form = document.forms[0];
    	form.action = url;
	   // form.submit()
	   submitForm(form);	
	}
	

}

-->
</script>

<logic:present name="indicadorDataRelacaoFimInserir" scope="session">

	<script language="JavaScript">
	
	    function DateValidations () {
	        var form = document.forms[0];
			
	    	if (document.InserirImovelActionForm.dataFimClienteImovelRelacao.value != null && document.InserirImovelActionForm.dataFimClienteImovelRelacao.value != "") {
	        	this.fa = new Array("dataFimClienteImovelRelacao", "Data Fim Relação inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	    	}
	      } 
    
		function modificarTipoRelacaoCliente() {
			var form = document.forms[0];
			
			if (form.tipoClienteImovel.value != form.idTipoRelacaoUsuario.value) {
				form.dataFimClienteImovelRelacao.value = "";
				document.InserirImovelActionForm.dataFimClienteImovelRelacao = "";
				form.dataFimClienteImovelRelacao.readOnly = true;	
				form.dataFimClienteImovelRelacao.style.backgroundColor = "#EFEFEF";
				
				form.idMotivoFimClienteImovelRelacao.selectedIndex = -1;
				document.InserirImovelActionForm.idMotivoFimClienteImovelRelacao = "";
				form.idMotivoFimClienteImovelRelacao.disabled = true;	
				form.idMotivoFimClienteImovelRelacao.style.backgroundColor = "#EFEFEF";		
			} else {
				if (form.dataFimClienteImovelRelacao.disabled == true) {
					form.dataFimClienteImovelRelacao.value = "";
					document.InserirImovelActionForm.dataFimClienteImovelRelacao = "";
					
					form.idMotivoFimClienteImovelRelacao.selectedIndex = -1;
					document.InserirImovelActionForm.dataFimClienteImovelRelacao = "";			
				}
				
				form.dataFimClienteImovelRelacao.readOnly = false;
				form.dataFimClienteImovelRelacao.style.backgroundColor = "FFFFFF";
				
				form.idMotivoFimClienteImovelRelacao.disabled = false;
				form.idMotivoFimClienteImovelRelacao.style.backgroundColor = "FFFFFF";
			}
		}
		
		function abrirCalendarioFimRelacao() {
			var form = document.forms[0];
			
			if (form.tipoClienteImovel.value == form.idTipoRelacaoUsuario.value) {
				abrirCalendario('InserirImovelActionForm', 'dataFimClienteImovelRelacao');
			}
		}
	
	</script>
</logic:present>

<logic:notPresent name="indicadorDataRelacaoFimInserir" scope="session">
	<script language="JavaScript">
		function modificarTipoRelacaoCliente() {
		}
		
	    function DateValidations () {
	    } 		
	</script>		
</logic:notPresent>	

</head>


<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('idCliente');modificarTipoRelacaoCliente();">

<div id="formDiv">
<html:form action="/inserirImovelWizardAction" method="post"
	onsubmit="return validateInserirImovelActionForm(this);">


	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=3" />

	<input type="hidden" id="idTipoRelacaoUsuario" name="idTipoRelacaoUsuario"  value="<%=""+ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO%>"/>
	<html:hidden property="url" value="3" />
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="4" />
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
			<td width="635" valign="top" class="centercoltext">
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
					<td class="parabg">Inserir Imóvel</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o(s) cliente(s), informe os dados
					abaixo:</td>
					<td align="right"></td>												
				</tr>
				</table>
				<table width="100%" border="0">
				<tr>
					<td><strong>C&oacute;digo:<font color="#FF0000">*</font></strong></td>
					<td>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><html:text maxlength="9" property="idCliente" size="9"
								onkeypress="javascript:validaEnter(event, 'inserirImovelWizardAction.do?action=exibirInserirImovelClienteAction', 'idCliente');" /></td>
							<td>
							<logic:present name="idClienteImovelUsuario" scope="session">
								<html:hidden property="idClienteImovelUsuario" value="${sessionScope.idClienteImovelUsuario}"/>	
							</logic:present>
							<logic:notPresent name="idClienteImovelUsuario"	scope="session">
								<html:hidden property="idClienteImovelUsuario" value="${sessionScope.idClienteImovelUsuario}"/>	
							</logic:notPresent>
							
							<logic:present name="idClienteImovelResponsavel" scope="session">
								<html:hidden property="idClienteImovelResponsavel" value="${sessionScope.idClienteImovelResponsavel}" />
							</logic:present>
							<logic:notPresent name="idClienteImovelResponsavel" scope="session">
 								<html:hidden property="idClienteImovelResponsavel" value="${sessionScope.idClienteImovelResponsavel}"/>
							</logic:notPresent>
							<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
								<html:hidden property="idClienteImovelProprietario" value="1"/>
							</logic:equal>
							<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
								<logic:present name="idClienteImovelProprietario" scope="session">
									<html:hidden property="idClienteImovelProprietario" value="${sessionScope.idClienteImovelProprietario}" />
								</logic:present>
								<logic:notPresent name="idClienteImovelProprietario" scope="session">
	 								<html:hidden property="idClienteImovelProprietario" value="${sessionScope.idClienteImovelProprietario}"/>
								</logic:notPresent>
							</logic:equal>
								
							</td>
							<td width="442"><a	
								href="javascript:abrirPopup('exibirPesquisarClienteAction.do?limparForm=OK', 400, 800);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Cliente" /></a> <logic:present
								name="codigoClienteNaoEncontrado" scope="request">
								<input type="text" name="nomeCliente" size="40" readonly="readonly"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									value="<bean:message key="atencao.cliente.inexistente"/>" />
							</logic:present> <logic:notPresent
								name="codigoClienteNaoEncontrado" scope="request">
								<html:text property="nomeCliente" size="40" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notPresent> <a
								href="javascript:limparClienteImovel();document.forms[0].idCliente.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td><strong>Tipo do Cliente:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="tipoClienteImovel"
						onchange="javascript:document.InserirImovelActionForm.textoSelecionado.value = this[this.selectedIndex].text.substring(5);modificarTipoRelacaoCliente();">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="tipoClientesImoveis" labelProperty="descricaoComId" property="id" />
					</html:select> <html:hidden property="textoSelecionado" /></td>
				</tr>
				
				<logic:present name="indicadorDataRelacaoFimInserir" scope="session">
					<tr> 
						<td>
							<strong>Data Fim da Relação:</strong>
						</td>
						<td>
							<html:text property="dataFimClienteImovelRelacao" tabindex="10" size="10" maxlength="10"
									onkeypress="mascaraData(this, event); " /> 
							<a href="javascript:abrirCalendarioFimRelacao()">
								<img border="0"	src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="middle" alt="Exibir Calendário" />
							</a>
							<font size="1">(dd/mm/aaaa) </font>
						</td>
					</tr>	
					
					<tr>
	                     <td width="10%"><strong>Motivo</strong></td>
	                     <td width="56%">
	                       <html:select property="idMotivoFimClienteImovelRelacao">
	                       	 <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
	                         <html:options collection="clienteImoveisFimRelacaoMotivo" labelProperty="descricao" property="id"/>
	                       </html:select>
	                      </td>
	                </tr>	
	                						
				</logic:present>
				
				<tr>
					<td>&nbsp;</td>
					<td><font color="#FF0000">*</font> Campo obrigatório.</td>
				</tr>
				<tr>
					<td colspan="2">
					<p>&nbsp;<html:hidden property="nomeContaSelecionado"/></p>
					</td>
				</tr>
				<tr>
					<td width="140"><strong>Cliente(s) Informado(s)</strong></td>
					<td width="450" align="right"><input type="button"
						class="bottonRightCol" style="bottonRightCol" value="Adicionar"
						onclick="javascript:verificarAdicionar();" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
								<table width="587" bgcolor="#90c7fc">
									<!--header da tabela interna -->
									<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
										<td width="24" align="center" ><strong></strong></td>
										<td width="40" align="center" ><strong>Nome Conta</strong></td>
										<td width="40" align="center" ><strong>Código</strong></td>
										
										<logic:present name="indicadorDataRelacaoFimInserir" scope="session">
											<td width="100"><strong>Nome</strong></td>
										</logic:present>
										
										<logic:notPresent name="indicadorDataRelacaoFimInserir" scope="session">
											<td width="170"><strong>Nome</strong></td>	
										</logic:notPresent>											
										
										<td width="95"><strong>Tipo</strong></td>
										<td width="110"><strong>CPF/CNPJ</strong></td>
										<td width="70" align="center"><strong>Data Início Relação</strong></td>
										
										<logic:present name="indicadorDataRelacaoFimInserir" scope="session">										
											<td width="70" align="left"><strong>Data Fim Relação</strong></td>											
										</logic:present>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 587; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>
								<logic:notEmpty name="imovelClientesNovos">

									<logic:iterate name="imovelClientesNovos"
										id="imovelClienteNovo" type="ClienteImovel">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td >
											<div  align="center" style="width: 24"><a
												href="javascript:removerCliente('removerInserirImovelClienteAction.do?idRemocaoClienteImovel=<%=""+imovelClienteNovo.getUltimaAlteracao().getTime()%>');"><img
												border="0" src="/gsan/imagens/Error.gif" /></a></div>
											</td>
											<td >
    											<div  style="width: 40" align="center">
	 											    <html:radio property="idNomeConta" onclick="javascript:validarNomeConta('${imovelClienteNovo.clienteRelacaoTipo.descricao}-${imovelClienteNovo.cliente.id}');"
	 											    value="${imovelClienteNovo.clienteRelacaoTipo.descricao}-${imovelClienteNovo.cliente.id}"/>		
												</div>									
											</td>
											<td >
											<div align="center" style="width: 40"><bean:write name="imovelClienteNovo"
												property="cliente.id" /></div>
											</td>
											
											<logic:present name="indicadorDataRelacaoFimInserir" scope="session">
												<td align="left" width="100">
												<div align="left" style="width: 100"><bean:write name="imovelClienteNovo"
													property="cliente.nome" /></div>
												</td>
											</logic:present>
											
											<logic:notPresent name="indicadorDataRelacaoFimInserir" scope="session">
												<td align="left" width="170">
												<div align="left" style="width: 170"><bean:write name="imovelClienteNovo"
													property="cliente.nome" /></div>
												</td>
											</logic:notPresent>												
											
											<td width="95">
											<div align="left"  style="width: 95"><bean:write name="imovelClienteNovo"
												property="clienteRelacaoTipo.descricao" /></div>
											</td>
												<td width="110" align="right"><logic:notEmpty name="imovelClienteNovo"
												property="cliente.cpf">
												<bean:write name="imovelClienteNovo" property="cliente.cpfFormatado" />
											</logic:notEmpty> <logic:notEmpty name="imovelClienteNovo"
												property="cliente.cnpj">
												<bean:write name="imovelClienteNovo" property="cliente.cnpjFormatado"  />
												</logic:notEmpty></td>
												
											<td align="center" width="65">
											<div align="center" style="width: 65"><bean:write name="imovelClienteNovo"
												property="dataInicioRelacao" formatKey="date.format" /></div>
											</td>											
											
											<logic:present name="indicadorDataRelacaoFimInserir" scope="session">
												<td align="center" width="65">
												<div align="center" style="width: 65"><bean:write name="imovelClienteNovo"
													property="dataPrevistaFimRelacao" formatKey="date.format" /></div>
												</td>
											</logic:present>												
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=3" />
					</div>
					</td>
				</tr>
			</table>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>

</div>
<!--  REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * *  -->
<logic:equal name="sistemaParametro" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<!--  REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * *  -->
	<logic:notEmpty name="responsavelNaoContemDados" scope="request">
		<script>
			window.alert("Cliente responsável sem dados adicionais");
		</script>
		<bean:define id="responsavelNaoContemDados" value="null" scope="request"/>
	</logic:notEmpty>
<!--  REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * *  -->
</logic:equal>
<!--  REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * * REMOVER SE FOR DESO * * *  -->


</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirImovelWizardAction.do?concluir=true&action=inserirImovelClienteAction'); }
</script>


<script>

	if(document.forms[0].idNomeConta != undefined){

      verficarSelecao(document.forms[0].idNomeConta,'radio');
    }

</script>


</html:html>


