<%@page import="gcom.cobranca.campanhapremiacao.CampanhaCadastro"%>
<%@page import="gcom.gui.GcomAction"%>
<%@page import="gcom.cadastro.cliente.ClienteRelacaoTipo"%>
<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="EfetuarInscricaoCampanhaPremiacaoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function remover(index){
		document.forms[0].indexTelefone.value = index;
 		document.forms[0].botaoClicado.value = 'REMOVER';
		redirecionarSubmit('/gsan/exibirEfetuarInscricaoCampanhaPremiacaoAction.do');

	}

	function validarForm(){
		
		var indicadorResidencial =  "<%=request.getSession().getAttribute("indicadorResidencial")%>"; 

		var form = document.forms[0];
		var msg = '';		
		
		if(form.nomeCliente.value == ''){
			msg = msg + 'Infome Nome do Cliente.\n';
		}

		if(form.tipoRelacao[0].checked == false
				&& form.tipoRelacao[1].checked == false
				&& form.tipoRelacao[2].checked == false){
			
			msg = msg + 'Infome Tipo da Relação.\n';
		}
		if(indicadorResidencial == 1){
		if(form.nuCPF.value == ''){
			msg = msg + 'Infome o CPF.\n';
		}else{
			if(!validarCpf()){
				return false;
			}
		}
		
		if(form.nuRG.value == ''){
			msg = msg + 'Infome o RG.\n';
		}
		if(form.dtEmissaoRG.value == ''){
			msg = msg + 'Infome a data de Emissão do RG.\n';
		}else{	
			if(!compararDataComDataAtual(form.dtEmissaoRG.value)){
				alert('Data não pode ser superior a atual.');
				return false; 
			}
		}
		if(form.orgaoExpedidorRG.value == '-1'){
			msg = msg + 'Infome o Orgão Expedidor do RG.\n';
		}
		
		if(form.estado.value == -1){
			msg = msg + 'Infome o estado.\n';
		}		
		if(form.dtNascimento.value == ''){
			msg = msg + 'Infome sua data de Nascimento.\n';
		}else{	
			if(!compararDataComDataAtual(form.dtNascimento.value)){
				alert('Data não pode ser superior a atual.');
				return false; 
			}
		}
		if(form.nomeMae.value == ''){
			msg = msg + 'Infome o nome da Mãe.\n';
		}
	}else{
		if(form.nuCNPJ.value == ''){
			msg = msg + 'Infome o CNPJ.\n';
		}else{
			if(!isCpfCnpj(form.nuCNPJ.value)){
				alert('CNPJ Inválido');
				return false;
			}
		}
	}
		if(form.email.value != ''){
			if(!checkMail(form.email)){
				alert('E-mail inválido.');
				return false;
			}
		}
		
		if( msg != '' ){
			alert(msg);
			return false;
		}
		
		if(form.dtEmissaoRG != null){
			if(!verificarIdade()){
				return false;
			}
		}
		
		if(form.dtNascimento != null){
			if(!verificarDataMaiorAtual(form.dtNascimento)){
				return false;
			}
		}
		
		if(form.concordaRegulamentoCampanha.checked == true){
			form.submit();
		}else{
			alert('Para efetuar a inscrição, é necessário concordar com o regulamento.');
		}
	}
	
	function validarCpf() {
		var form = document.forms[0];
		if (form.nuCPF != null &&
		form.nuCPF.value == '' ) {
			alert('Informe CPF.');
			return false;
		} else {
			if ( !isCpf(form.nuCPF.value) ) {
				alert('CPF Inválido.');
				return false;
			} else {
				return true;
			}
		}
	} 

	function DateValidations () {
		this.aa = new Array("dataEmissao", "Data de Emissão inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	}
	
	function isCpf(cpf) {
		
		var soma;
		var resto;
		var i;
		 
		if ( (cpf.length != 11) ||
			 (cpf == "00000000000") || (cpf == "11111111111") ||
			 (cpf == "22222222222") || (cpf == "33333333333") ||
			 (cpf == "44444444444") || (cpf == "55555555555") ||
			 (cpf == "66666666666") || (cpf == "77777777777") ||
			 (cpf == "88888888888") || (cpf == "99999999999") ) {
		 
			return false;
		}
		 
		soma = 0;
		 
		for (i = 1; i <= 9; i++) {
		
			soma += Math.floor(cpf.charAt(i-1)) * (11 - i);
		
		}
		 
		resto = 11 - (soma - (Math.floor(soma / 11) * 11));
		 
		if ( (resto == 10) || (resto == 11) ) {
			resto = 0;
		}
		 
		if ( resto != Math.floor(cpf.charAt(9)) ) {
			return false;
		}
		 
		soma = 0;
		 
		for (i = 1; i<=10; i++) {
			soma += cpf.charAt(i-1) * (12 - i);
		}
		 
		resto = 11 - (soma - (Math.floor(soma / 11) * 11));
		 
		if ( (resto == 10) || (resto == 11) ) {
			resto = 0;
		}
		 
		if (resto != Math.floor(cpf.charAt(10)) ) {
			return false;
		}
		 
		 return true;
	}
	

	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "&" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "&" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
function validaEnterImovel(tecla, caminhoActionReload, nomeCampo) {
	var form = document.EfetuarInscricaoCampanhaPremiacaoActionForm;
	
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Matrícula do Imóvel");
	
	if(form.idImovel.value.length > 0) {
		form.codigoCliente.readOnly = true;
		form.tipoRelacao.disabled = true;
		form.codigoCliente.value = "";	
		form.codigoClienteClone.value = "";	
		form.nomeCliente.value = "";
		form.tipoRelacao.value = <%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>;
		form.codigoClienteSuperior.readOnly = true;
		form.codigoClienteSuperior.value = "";	
		form.codigoClienteSuperiorClone.value = "";	
		form.nomeClienteSuperior.value = "";
	} else {
		form.codigoCliente.readOnly = false;
		form.tipoRelacao.disabled = false;
		form.idImovel.value = "";
		form.codigoImovelClone.value = "";
        form.nomeCliente.value = "";
        form.codigoClienteSuperior.readOnly = false;
	}
}

function validaMudancaCampoMatricula(){
	var form = document.forms[0];	
	
	if(form.idImovel.value != form.idImovelAux.value){
		form.idImovelAux.value = form.idImovel.value;
			
	    if(form.idImovel.value != ""){
			form.action='exibirEfetuarInscricaoCampanhaPremiacaoAction.do';
		    form.submit();
	    }
	}
}

function habilitarPesquisaImovel(form) {
	if (form.idImovel.readOnly == false) {
		abrirPopup('exibirPesquisarImovelAction.do', 400, 800);
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    form.idImovel.value = codigoRegistro;
      
	form.action='exibirEfetuarInscricaoCampanhaPremiacaoAction.do?';
    form.submit();

}

function exibirOcultarDIV(camada, display){
	var form = document.forms[0];
	
	if (camada == 'camadaDadosCliente'){
		if(eval('camadaDadosCliente').style.display == 'block'){
			eval('camadaDadosCliente').style.display = 'none'
		} else {
			eval('camadaDadosCliente').style.display = 'block'
		}
	} else if (camada == 'camadaEnderecoImovel'){
		if(eval('camadaEnderecoImovel').style.display == 'block'){
			eval('camadaEnderecoImovel').style.display = 'none'
		} else {
			eval('camadaEnderecoImovel').style.display = 'block'
		}
	}else if (camada == 'camadaFonesCliente'){
		if(eval('camadaFonesCliente').style.display == 'block'){
			eval('camadaFonesCliente').style.display = 'none'
		} else {
			eval('camadaFonesCliente').style.display = 'block'
		}
	}
}

function validaTelefone() {
	var form = document.forms[0];
	var msg = '';
	if( form.idTipoTelefone.value  == -1 ) {
		msg = 'Informe Tipo Telefone.\n';
	}
	if( form.ddd.value == '-1' ) {
		msg = msg + 'Informe DDD.\n';
	}
	if( form.telefone.value == '' ) {
		msg = msg + 'Informe Número do Telefone.';
	}
	if( msg != '' ){
		alert(msg);
		return false;
	}else{
		var tamanho = document.forms[0].idTipoTelefone[document.forms[0].idTipoTelefone.selectedIndex].text.indexOf('-');
		var descricao = document.forms[0].idTipoTelefone[document.forms[0].idTipoTelefone.selectedIndex].text;
		document.forms[0].textoSelecionado.value = descricao.substring(tamanho+2);
		document.forms[0].botaoClicado.value = 'Adicionar';
		return redirecionarSubmit('/gsan/exibirEfetuarInscricaoCampanhaPremiacaoAction.do');
		return true;
	}
}

function verificarDocumentoImpedido(campo){
	var form = document.forms[0];

	if(campo.value != ''){
		if(campo.name == 'nuCPF'){ 
			
			if(form.nuCPF.value != form.nuCPFAux.value){
				form.nuCPFAux.value = form.nuCPF.value;
			
				if(validarCpf()){
					return redirecionarSubmit('/gsan/exibirEfetuarInscricaoCampanhaPremiacaoAction.do?indicadorVerificarDocumentoImpedido=1');
				}
			}
		} else {
			if(form.nuCNPJ.value != form.nuCNPJAux.value){
				form.nuCNPJAux.value = form.nuCNPJ.value;
			
				if(isCpfCnpj(form.nuCNPJ.value)){
					return redirecionarSubmit('/gsan/exibirEfetuarInscricaoCampanhaPremiacaoAction.do?indicadorVerificarDocumentoImpedido=1');
				}
			}
			
		}
	}
}

function verificarIdade() {
	var form = document.forms[0];
	
	var data1 = form.dtNascimento.value;
	
	if(data1 == ''){
		return false;
	}

   data1 = data1.substr(6,4) + "-" + data1.substr(3,2) + "-" + data1.substr(0,2);
    
   data2 = new Date();
   var dia =  data2.getDate();
   var mes = data2.getMonth() + 1 + "";
   var ano = data2.getFullYear() - 18;

   if(mes.length == 1){
	   mes = "0" + mes;
   }
   
    var data2 = ano + "-" + mes + "-" + dia;
    
   if(compararDatas(data1, data2) == 1){

	   var mensagem = "A campanha " + form.tituloCampanha.value + " é destinada para clientes maiores de 18 anos. Não é possível prosseguir com a inscrição para o cliente " + form.nomeCliente.value + ".";
	   alert(mensagem);
	   return false;
   } else {
	   return true;
   }



}

function verificarData(campo){
	var form = document.forms[0];
	
	if(campo.value == ''){
		return false;
	}
	
	if(campo.name == "dtNascimento"){
	
		if(form.dtNascimento.value != form.dtNascimentoAux.value){
			
			form.dtNascimentoAux.value = form.dtNascimento.value;
			
			verificarIdade();
		}
	} else if(campo.name == "dtEmissaoRG"){
		
		if(form.dtEmissaoRG.value != form.dtEmissaoRGAux.value){
			
			form.dtEmissaoRGAux.value = form.dtEmissaoRG.value;
			
			verificarDataMaiorAtual(campo);
		}
		
	}

}

function dispararValidacaoData(campo){
	if(campo.value.length == 10){
		if(campo.name == "dtEmissaoRG"){
			
			setarFoco("orgaoExpedidorRG");
			
		} else if(campo.name == "dtNascimento"){
			
			setarFoco("nomeMae");
		}		

	}
}

function verificarDataMaiorAtual(campo){
	dataHoje = new Date();

	var dia =  dataHoje.getDate() + "";
	var mes = dataHoje.getMonth() + 1 + "";
	var ano = dataHoje.getFullYear();

	if(dia.length == 1){
		dia = "0" + dia;
	}
	
	if(mes.length == 1){
		   mes = "0" + mes;
	}

	var dataHoje = dia + "/" + mes + "/" + ano;
	
	if(comparaData(campo.value, ">", dataHoje)){
		alert("A data não pode ser posterior a hoje.");
		return false;
	}
	
	return true;
}

function setarFocoDtNascimento(campo){
	var form = document.forms[0];
	
	if(campo == "dtNascimento"){
		
		form.dtNascimento.focus();
	} else if(campo == "dtEmissaoRG"){
		form.dtEmissaoRG.focus();
	}
}

</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}')">
<html:form action="/efetuarInscricaoCampanhaPremiacaoAction.do" method="post"
	name="EfetuarInscricaoCampanhaPremiacaoActionForm"
	type="gcom.gui.cobranca.campanhapremiacao.EfetuarInscricaoCampanhaPremiacaoActionForm"
	onsubmit="">
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" id="dtEmissaoRGAux" >
	<input type="hidden" id="dtNascimentoAux" >
	<input type="hidden" id="idImovelAux" value="<%=request.getAttribute("idImovel") %>">
	<input type="hidden" id="nuCPFAux" value="<%=request.getAttribute("nuCPF") %>" >
	<input type="hidden" id="nuCNPJAux" value="<%=request.getAttribute("nuCNPJ") %>" >

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
			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Efetuar Inscrição Campanha de Premiação</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
      		  <tr>
      			<td align="left"><strong><bean:write name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="tituloCampanha"/></strong></td>
      			<html:hidden property="tituloCampanha" />
    		  </tr>
    		  <tr>
   			   <td align="right"><strong>Inscrições:</strong><bean:write name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="inscricoesRegistradas"/>/<bean:write name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="inscricoesBloqueadas"/></td>
   			   <html:hidden property="inscricoesRegistradas" />
   			   <html:hidden property="inscricoesBloqueadas" />																	        	
    		  </tr>
    	 	 </table>
    	 	 <p>&nbsp;</p>
			<table width="100%" border="0" colspan="4">
			
				<logic:present name="indicadorExibirInscricao">
					<tr>
						<td ><strong>Número da Inscrição:</strong>					
							<bean:write name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="inscricaoCampanha"/>
					</tr>
				</logic:present>
				
				<tr>
					<td ><strong>Matrícula do Imóvel:<font color="#FF0000">*</font></strong>
					
					<html:text property="idImovel" maxlength="9" size="9" 
					onkeyup="validaEnterImovel(event, 'exibirEfetuarInscricaoCampanhaPremiacaoAction.do', 'idImovel');" 
					onblur="validaMudancaCampoMatricula();"/>
					<a href="javascript:habilitarPesquisaImovel(document.forms[0]);" alt="Pesquisar Imóvel">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<a href="exibirEfetuarInscricaoCampanhaPremiacaoAction.do?menu=sim"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>	
					</td>
					
				</tr>
				
				<tr>
					<td colspan="3" align="center" bgcolor="#99CCFF"><strong>Endereço do Imóvel</strong></td>
				</tr>
				<logic:present name="indicadorResidencial">
					<tr>
						<td colspan="3"  bgcolor="#FFFFFF" align="center">
							<bean:write name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="endereco"/>
							<html:hidden property="endereco" />
						</td>
							
					</tr>
				</logic:present>
				<logic:present name="indicadorConsulta">
				<logic:equal name="indicadorConsulta" value="<%=ConstantesSistema.INATIVO%>">
				<tr bgcolor="#99CCFF">
					<td colspan="3" align="center" bgcolor="#99CCFF"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaDadosCliente',true);" />
						<b>Dados do Cliente</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaDadosCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
							
								<table width="100%">
								
									<tr>
									
										<td><strong>Nome do Cliente:<font color="#FF0000">*</font></strong></td>
										
										<td><strong> <html:text property="nomeCliente" size="50" maxlength= "50" /> </strong></td>
									</tr>
									<tr>
						                <td><strong>Tipo de Relação:<font color="#FF0000">*</font></strong></td>
						                <td align="right">
						       			    <div align="left">
						                        <html:radio property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO).toString()%>"/><strong>Usuário
						                        <html:radio property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL).toString()%>"/>Responsável
						                        <html:radio property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL).toString()%>"/>Usuário e Responsável</strong>
											</div>
										</td>
						            </tr>
						          <logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.ATIVO%>">
						            <tr>
										<td width="162"><strong>CPF:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text property="nuCPF" size="11" maxlength= "11" onblur="verificarDocumentoImpedido(this)" /> </strong></td>
									</tr>
									<tr>
										<td width="162"><strong>RG:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>
													<td style="padding-left: 0"><strong> <html:text property="nuRG" size="11" maxlength= "11" style="margin-left:0px;" /> </strong></td>
													<td align="right"><strong>Data de Emissão:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													onkeyup="javascript:mascara_data(this), dispararValidacaoData(this);"
													onblur="javascript:verifica_tamanho_data(this), verificarData(this)"
													property="dtEmissaoRG" size="10" maxlength="10" /> <a
													href="javascript:abrirCalendario('EfetuarInscricaoCampanhaPremiacaoActionForm', 'dtEmissaoRG')">
													<img border="0" width="16" height="15" onclick="setarFoco('dtEmissaoRG')"
													src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
													</td>
												</tr>
											</table>
										</td>
										
										
									</tr>
									<tr>
										<td width="162"><strong>Órgão Expedidor:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>

													<td >
														<html:select property="orgaoExpedidorRG" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colOrgaoExpedidorRg" labelProperty="descricao" property="id" />
														</html:select>
													</td>
													<td align="right"><strong>Estado:<font color="#FF0000">*</font></strong></td>
													<td >
														<html:select property="estado" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colEstados" labelProperty="sigla" property="id" />
														</html:select>
													</td>
												</tr>
											</table>
										</td>
									
									</tr>
									<tr>
										<td align="left"><strong>Data de Nascimento:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													onkeyup="javascript:mascara_data(this), dispararValidacaoData(this);"
													onblur="verifica_tamanho_data(this), verificarData(this);"
													property="dtNascimento" size="10" maxlength="10" /> <a
													href="javascript:abrirCalendario('EfetuarInscricaoCampanhaPremiacaoActionForm', 'dtNascimento')">
													<img border="0" width="16" onclick="setarFoco('dtNascimento')" height="15"
													src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
													</td>
													
									
									</tr>
									<tr>
										<td><strong>Nome da Mãe:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text property="nomeMae" size="50" maxlength= "50" /> </strong></td>
									</tr>
								</logic:equal>
								<logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.INATIVO %>">
									<tr>
										<td width="162"><strong>CNPJ:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text property="nuCNPJ" size="14" maxlength= "14" onblur="verificarDocumentoImpedido(this)"/> </strong></td>
									</tr>
								</logic:equal>
									<tr>
										<td width="162"><strong>E-Mail:</strong></td>
										<td><strong> <html:text property="email" size="40" maxlength= "40" /> </strong></td>
									</tr>
								
								</table>
								
							</td>
						</tr>
						
					</table>
					</div>								
					</td>
				</tr>	
				</logic:present>
				
				
				
				<tr bgcolor="#99CCFF" style="padding-top: 300px;">
					<td colspan="3" align="center"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaFonesCliente',true);" />
						<b>Fones do Cliente</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaFonesCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
								<table width="100%" border="0">
					
					<tr>
						<td width="25%">
							<strong>Tipo Telefone:</strong>
						</td>
						<td >
							<html:select property="idTipoTelefone" tabindex="1">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="foneTipos" labelProperty="descricaoComId" property="id" />
							</html:select>
							<html:hidden property="textoSelecionado" />
						</td>
					</tr>
					<tr>
						<td height="24">
							<strong>DDD:</strong>
						</td>
						<td >
							<html:select property="ddd" tabindex="1">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colDdds" labelProperty="dddComMunicipio" property="ddd" />
							</html:select>
							
						</td>
						
					</tr>
					<tr>
						<td height="24">
							<strong>Número do Telefone:</strong>
						</td>
						<td>
							<html:text maxlength="9" property="telefone" size="9" tabindex="4"/>
						</td>
					</tr>
					<tr>
						<td height="24">
							<strong>Ramal:</strong>
						</td>
						<td>
							<html:text maxlength="4" property="ramal" size="4" tabindex="5"/>
						</td>
					</tr>
					<tr>
						<td colspan="3">
						<table width="100%" border="0">
							<tr>
								<td width="183">
									<strong>Telefone(s) do Cliente</strong>
								</td>
								<td width="432" align="right">
									<input type="button" class="bottonRightCol" value="Adicionar" name="botaoAdicionar" tabindex="6"
										onclick="javascript:validaTelefone();" />
									<input type="hidden" name="botaoClicado" value=""/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td height="0">
												<table width="100%" bgcolor="#90c7fc">
													<!--header da tabela interna -->
													<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
														<td width="10%" align="center">
															<strong>Remover</strong>
														</td>
														<td width="50%" align="center">
															<strong>Telefone</strong>
														</td>
														<td width="30%" align="center">
															<strong>Tipo</strong>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="100">
												<html:hidden property="indexTelefone" />
												<div style="width: 100%; height: 100%; overflow: auto;">
												<input type="hidden" name="idRegistrosRemocao" value="" />
												<table width="100%" align="center" bgcolor="#99CCFF">
												  <logic:present name="colecaoClienteFone">
													<%int cont = 0;%>
													<%--Campo que vai guardar o valor do telefone a ser removido--%>
													<logic:iterate name="colecaoClienteFone" id="clienteFone" scope="session">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else { %>
														<tr bgcolor="#FFFFFF">
														<%}%>
															<td width="10%" align="center">
																<strong> 
																<img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" style="cursor:hand;" alt="Remover"
																	onclick="javascript:remover(<%=cont%>);">
																</strong>
															</td>
															<td width="50%" align="center">
																<strong>
																<bean:write name="clienteFone" property="dddTelefone"/> 
																<logic:notEmpty name="clienteFone" property="ramal">
																	&nbsp;Ramal&nbsp;<bean:write name="clienteFone" property="ramal" />
																</logic:notEmpty> 
																</strong>
															</td>
															<td width="30%" align="center">
																<strong><bean:write name="clienteFone" property="foneTipo.descricao" /></strong>
															</td>
														</tr>
													</logic:iterate>
													</logic:present>
												</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<p>&nbsp;</p>
						</td>
					</tr>
				</table>
							</td>
						</tr>
						
					</table>
					</div>								
					</td>
				</tr>		
				</logic:present>
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td width="10%"></td>
								<td align="left">
									<strong>Regulamento da Campanha</strong>
									<html:textarea property="txRegulamentoCampanha" cols="60" rows="10" readonly="true" style="resize: none;"></html:textarea>
								</td>	
								<td  width="10%"></td>
							</tr>
							<tr>
								<td></td>
								<td><html:checkbox property="concordaRegulamentoCampanha">Concordo com o Regulamento da Campanha</html:checkbox></td>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirEfetuarInscricaoCampanhaPremiacaoAction.do?menu=sim"/>'">
					<input name="Button" type="button" class="bottonRightCol"
						value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inscrever" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
				
				</logic:equal>
				<logic:equal name="indicadorConsulta" value="<%=ConstantesSistema.ATIVO%>">
				
				<tr bgcolor="#99CCFF">
					<td colspan="3" align="center" bgcolor="#99CCFF"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaDadosCliente',true);" />
						<b>Dados do Cliente</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaDadosCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
							
								<table width="100%">
								
									<tr>
									
										<td><strong>Nome do Cliente:<font color="#FF0000">*</font></strong></td>
										
										<td><strong> <html:text readonly="true" property="nomeCliente" size="50" maxlength= "50" /> </strong></td>
									</tr>
									<tr>
						                <td><strong>Tipo de Relação:<font color="#FF0000">*</font></strong></td>
						                <td align="right">
						       			    <div align="left">
						                        <html:radio disabled="true" property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO).toString()%>"/><strong>Usuário
						                        <html:radio disabled="true" property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL).toString()%>"/>Responsável
						                        <html:radio disabled="true" property="tipoRelacao" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL).toString()%>"/>Usuário e Responsável</strong>
											</div>
										</td>
						            </tr>
						          <logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.ATIVO%>">
						            <tr>
										<td width="162"><strong>CPF:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" property="nuCPF" size="11" maxlength= "11" /> </strong></td>
									</tr>
									<tr>
										<td width="162"><strong>RG:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>
													<td style="padding-left: 0"><strong> <html:text readonly="true" property="nuRG" size="11" maxlength= "11" style="margin-left:0px;" /> </strong></td>
													<td align="right"><strong>Data de Emissão:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													readonly="true"
													onkeyup="javascript:mascara_data(this);"
													onblur="javascript:verifica_tamanho_data(this);"
													property="dtEmissaoRG" size="10" maxlength="10" />
													</td>
												</tr>
											</table>
										</td>
										
										
									</tr>
									<tr>
										<td width="162"><strong>Órgão Expedidor:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>

													<td >
														<html:select disabled="true" property="orgaoExpedidorRG" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colOrgaoExpedidorRg" labelProperty="descricao" property="id" />
														</html:select>
													</td>
													<td align="right"><strong>Estado:<font color="#FF0000">*</font></strong></td>
													<td >
														<html:select disabled="true" property="estado" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colEstados" labelProperty="sigla" property="id" />
														</html:select>
													</td>
												</tr>
											</table>
										</td>
									
									</tr>
									<tr>
										<td align="left"><strong>Data de Nascimento:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													readonly="true"
													onkeyup="javascript:mascara_data(this);"
													onblur="javascript:verifica_tamanho_data(this);"
													property="dtNascimento" size="10" maxlength="10" />
													</td>
									
									</tr>
									<tr>
										<td><strong>Nome da Mãe:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" property="nomeMae" size="50" maxlength= "50" /> </strong></td>
									</tr>
								</logic:equal>
								<logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.INATIVO %>">
									<tr>
										<td width="162"><strong>CNPJ:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" property="nuCNPJ" size="14" maxlength= "14" /> </strong></td>
									</tr>
								</logic:equal>
									<tr>
										<td width="162"><strong>E-Mail:</strong></td>
										<td><strong> <html:text readonly="true" property="email" size="40" maxlength= "40" /> </strong></td>
									</tr>
								
								</table>
								
							</td>
						</tr>
						
					</table>
					</div>								
					</td>
				</tr>	
				</logic:present>
				
				
				
				<tr bgcolor="#99CCFF" style="padding-top: 300px;">
					<td colspan="3" align="center"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaFonesCliente',true);" />
						<b>Fones do Cliente</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaFonesCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
								<table width="100%" border="0">
					
				
					
					
					
					<tr>
						<td colspan="3">
						<table width="100%" border="0">
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td height="0">
												<table width="100%" bgcolor="#90c7fc">
													<!--header da tabela interna -->
													<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
														<td width="50%" align="center">
															<strong>Telefone</strong>
														</td>
														<td width="30%" align="center">
															<strong>Tipo</strong>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="100">
												<html:hidden property="indexTelefone" />
												<div style="width: 100%; height: 100%; overflow: auto;">
												<input type="hidden" name="idRegistrosRemocao" value="" />
												<table width="100%" align="center" bgcolor="#99CCFF">
												  <logic:present name="colecaoClienteFone">
													<%int cont = 0;%>
													<%--Campo que vai guardar o valor do telefone a ser removido--%>
													<logic:iterate name="colecaoClienteFone" id="clienteFone" scope="session">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else { %>
														<tr bgcolor="#FFFFFF">
														<%}%>															

															<td width="50%" align="center">
																<strong>
																<bean:write name="clienteFone" property="dddTelefone"/> 
																<logic:notEmpty name="clienteFone" property="ramal">
																	&nbsp;Ramal&nbsp;<bean:write name="clienteFone" property="ramal" />
																</logic:notEmpty> 
																</strong>
															</td>
															<td width="30%" align="center">
																<strong><bean:write name="clienteFone" property="foneTipo.descricao" /></strong>
															</td>
														</tr>
													</logic:iterate>
													</logic:present>
												</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<p>&nbsp;</p>
						</td>
					</tr>
				</table>
							</td>
						</tr>
						
					</table>
					</div>								
					</td>
				</tr>		
				</logic:present>		
			
				<tr>
					<td width="50%">
					<input name="Button" type="button" class="bottonRightCol"
						value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
						<logic:equal name="indicadorExibirBotaoEmitirComprovante" value="<%=ConstantesSistema.ATIVO %>">
						<td align="right"><input name="Button" type="button"
							class="bottonRightCol" value="Emitir Comprovante de Inscrição" align="right"
							onclick="window.location.href='/gsan/emitirComprovanteInscricaoCampanhaPremiacaoAction.do?indicadorEnvioComprovanteEmail=2'"></td>
						</logic:equal>
				</tr>
				<tr>
				<logic:equal name="indicadorExibirBotaoEmitirComprovante" value="<%=ConstantesSistema.ATIVO %>">
				<logic:present name="EfetuarInscricaoCampanhaPremiacaoActionForm" property="email">
					<td colspan="2" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Enviar Comprovante de Inscrição para E-mail" align="right"
						onclick="window.location.href='/gsan/emitirComprovanteInscricaoCampanhaPremiacaoAction.do?indicadorEnvioComprovanteEmail=1'"></td>
						</logic:present>
						</logic:equal>
						</tr>
				
				</logic:equal>
				</logic:present>
				
			</table>
			<p>&nbsp;</p>
		</tr>
		
	</table>	
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

