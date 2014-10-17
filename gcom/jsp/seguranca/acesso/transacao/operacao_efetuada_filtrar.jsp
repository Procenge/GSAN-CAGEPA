<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<html:javascript staticJavascript="false"  formName="FiltrarOperacaoEfetuadaActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script>

 function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
 	if ('usuario' == tipoConsulta) {
	 	document.forms[0].idUsuario.value = codigoRegistro;
	 	document.forms[0].nomeUsuario.value = descricaoRegistro;
 	} else  if (tipoConsulta == 'imovel') {
 		document.forms[0].imovelArgumento.value = codigoRegistro;
 		document.forms[0].imovelNomeArgumento.value = descricaoRegistro;
    } else  if (tipoConsulta == 'cliente') {
 		document.forms[0].clienteArgumento.value = codigoRegistro;
 		document.forms[0].clienteNomeArgumento.value = descricaoRegistro;
    } else  if (tipoConsulta == 'localidade') {
 		document.forms[0].localidadeArgumento.value = codigoRegistro;
 		document.forms[0].localidadeNomeArgumento.value = descricaoRegistro;
    } else  if (tipoConsulta == 'setorComercial') {
 		document.forms[0].setorComercialArgumento.value = codigoRegistro;
 		document.forms[0].setorComercialNomeArgumento.value = descricaoRegistro;
    } else if (tipoConsulta == 'hidrometro') {
 		document.forms[0].numeroHidrometroArgumento.value = codigoRegistro;
 		//document.forms[0].hidrometroNomeArgumento.value = descricaoRegistro;
	} else if (tipoConsulta == 'ordemServico') {
 		document.forms[0].ordemServicoArgumento.value = codigoRegistro;
 		document.forms[0].ordemServicoNomeArgumento.value = descricaoRegistro;
	} else if (tipoConsulta == 'municipio') {
 		document.forms[0].municipioArgumento.value = codigoRegistro;
 		document.forms[0].municipioNomeArgumento.value = descricaoRegistro;
	} else if (tipoConsulta == 'registroAtendimento') {
 		document.forms[0].registroAtendimentoArgumento.value = codigoRegistro;
 		document.forms[0].registroAtendimentoNomeArgumento.value = descricaoRegistro;
	} 
 	
 }

 function recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) { 
		var form = document.forms[0];
		
	 	if (tipoConsulta == 'avisoBancario') {
			document.forms[0].codigoAgenteArrecadador.value = descricaoRegistro1;
			document.forms[0].dataLancamentoAviso.value = descricaoRegistro2;
			document.forms[0].numeroSequencialAviso.value = descricaoRegistro3;
			document.forms[0].idAvisoBancario.value = codigoRegistro;
		}
	}

 function chamarFiltrar() {
 
	if (document.forms[0].dataInicial.value == '' && document.forms[0].horaInicial.value != '') {
		alert('Para informar a Hora Inicial é necessário informar a Data Inicial');
		return;
	} else if (document.forms[0].dataFinal.value == '' && document.forms[0].horaFinal.value != '') {
		alert('Para informar a Hora Final é necessário informar a Data Final');
		return;
	}
	
	if ( !(testarCampoValorZero(document.forms[0].idFuncionalidade, 'Funcionalidade') 
				&& testarCampoValorZero(document.forms[0].idUsuario, 'Usuário') 
				&& validateCaracterEspecial(document.forms[0]) 
				&& validateLong(document.forms[0]) 
				&& validateDate(document.forms[0]))){
		
	         return;
    }
	
	if (document.forms[0].idFuncionalidade.value != '' && !verificarOperacoes()){
		return;
	}
	document.forms[0].submit();
 }
 
 function caracteresespeciais () {
	//this.aa = new Array("idFuncionalidade", "Funcionalidade deve somente conter numeros positivos.", new Function ("varName", " return this[varName];"));
	this.aa = new Array("idUsuario", "Usuário deve somente conter caracteres válidos", new Function ("varName", " return this[varName];"));
 }
 
 function IntegerValidations () {
	//this.aa = new Array("idFuncionalidade", "Funcionalidade deve somente conter numeros positivos.", new Function ("varName", " return this[varName];"));
	this.aa = new Array("idUsuario", "Usuário deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
 }

 function DateValidations () {
	 this.aa = new Array("dataInicial", "Data Inicial inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	 this.ab = new Array("dataFinal", "Data Final inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
 }

 
 function limparFuncionalidade() {
    var form = document.forms[0]; 
 	form.idFuncionalidade.value = '-1';
 	if (form.operacoes != null) {
	 	form.action = 'exibirFiltrarOperacaoEfetuadaAction.do';
	 	form.submit();
	}
	form.operacoes = null;
 }
 
 
 function localidadePreenchida(event){
	 alert('Informe Localidade');
	 return false;
 }
 
 function limparUsuario() {
 	document.forms[0].nomeUsuario.value = '';
 	document.forms[0].idUsuario.value = '';
 	document.forms[0].usuarioLimpo.value='1';                  
 }

 
 function limparImovel() {
 	document.forms[0].imovelArgumento.value = '';
 	document.forms[0].imovelNomeArgumento.value = ''; 
 }

 function limparCliente() {
 	document.forms[0].clienteArgumento.value = '';
 	document.forms[0].clienteNomeArgumento.value = ''; 
 }

 function limparLocalidade() {
 	document.forms[0].localidadeArgumento.value = '';
 	document.forms[0].localidadeNomeArgumento.value = ''; 
 }

 function limparSetorComercial() {
 	document.forms[0].setorComercialArgumento.value = '';
 	document.forms[0].setorComercialNomeArgumento.value = ''; 
 }

 function limparAvisoBancario() {
 	var form = document.forms[0];
	form.codigoAgenteArrecadador.value = "";
	form.dataLancamentoAviso.value = "";
	form.numeroSequencialAviso.value = "";
	form.idAvisoBancario.value = "";
 }
 
 function limparOrdemServico() {
 	document.forms[0].ordemServicoArgumento.value = '';
 	document.forms[0].ordemServicoNomeArgumento.value = ''; 
 }
 
 function limparHidrometro() {
 	document.forms[0].numeroHidrometroArgumento.value = '';
 	document.forms[0].idHidrometroArgumento.value = '';
 	//document.forms[0].hidrometroNomeArgumento.value = ''; 
 }

 function limparMunicipio() {
 	document.forms[0].municipioArgumento.value = '';
 	document.forms[0].municipioNomeArgumento.value = ''; 
 }
 
 function limparRegistroAtendimento() {
	 	document.forms[0].registroAtendimentoArgumento.value = '';
	 	document.forms[0].registroAtendimentoNomeArgumento.value = ''; 
}
 
 function limparResolucaoDiretoria() {
	 	document.forms[0].resolucaoDiretoria.value = '-1';
	 	document.forms[0].imovelSituacaoTipo.value = '-1';
}

 function dataEstahLimpa(){
 	var dataInicial = document.forms[0].dataInicial.value;
 	var dataFinal = document.forms[0].dataFinal.value;
 	if (dataInicial == '' || dataFinal == ''){
 	    document.forms[0].dataLimpa.value = '1';
 	} else {
    	document.forms[0].dataLimpa.value = '0';
 	}
 }
 
 function horaEstahLimpa(){
 	var horaInicial = document.forms[0].horaInicial.value;
 	var horaFinal = document.forms[0].horaFinal.value;
 	if (horaInicial == '' || horaFinal == ''){
 	    document.forms[0].horaLimpa.value = '1';
 	} else {
    	document.forms[0].horaLimpa.value = '0';
 	}
 }

 function verificarOperacoes(){
   var form = document.forms[0];
   if (form.operacoes == null) {
       alert('Falta carregar operações');
       return false;	   
   }
   if (form.operacoes.value == '' && form.operacoes.options != null){
       for(i = 0; i < form.operacoes.options.length; i++){
         form.operacoes.options[i].selected = true;
       }
   }
   return true;   
 }

 function carregarOperacao(idFuncionalidadeSelecionada) {
	 
	limparResolucaoDiretoria();
	
	if(idFuncionalidadeSelecionada == "-1"){
		eval(''+'ResolucaoDiretoria').style.visibility = 'hidden';
		eval(''+'ImovelSituacaoTipo').style.visibility = 'hidden';
	}
	
	var form = document.forms[0];
 	form.idFuncionalidade.value = idFuncionalidadeSelecionada;

  	var campoOperacoes = document.getElementById("operacoes");
  	campoOperacoes.length=0;
	
  	if (idFuncionalidadeSelecionada != "-1") {
  		
  		esconderTodosArgumentos();
  		
		  AjaxService.carregarOperacao( idFuncionalidadeSelecionada, function(operacoes2) {
				  for (key in operacoes2){
					  var novaOpcao = new Option(operacoes2[key], key);
					  campoOperacoes.options[campoOperacoes.length] = novaOpcao;
					  
		  		   }
				  });
		  	 
			  AjaxService.carregarArgumentos( idFuncionalidadeSelecionada, function(argumentos) {
				 for (key in argumentos) {
				  var item = argumentos[key];
				  
				  if(item == 'PerfilParcelamento'){
					  item = 'ResolucaoDiretoria';
					  eval(''+'ImovelSituacaoTipo').style.visibility = 'visible';
				  }
				  
				  if(item == 'Imovel'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'Cliente'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'OrdemServico'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'Hidrometro'){
					  eval(''+item).style.display = '';
				  }
				// if(item == 'CobrancaAcao'){
				//	  eval(''+item).style.display = 'block';
				//  }
				  if(item == 'Localidade'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'Municipio'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'SetorComercial'){
					  eval(''+item).style.display = '';
				  }
			//	  if(item == 'UnidadeOperacional'){
			//		  eval(''+item).style.display = 'block';
			//	  }
				  if(item == 'AvisoBancario'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'RegistroAtendimento'){
					  eval(''+item).style.display = '';
				  }
				  if(item == 'ResolucaoDiretoria'){
					  eval(''+item).style.visibility = 'visible';
				  }
				}
			  });
		  	 
		  
  	} else {
  		var novaOpcao = new Option(" ","-1");
  		campoOperacoes.options[campoOperacoes.length] = novaOpcao;
  		exibirTodosArgumentos();
  	}
 }

 
 function esconderTodosArgumentos(){
	 // COLOCAR TODOS OS ARGUMENTOS PARA "NONE" e depois executar AJAX. para exibir. XXX
	 	 eval('Imovel').style.display = 'none';
	 	 eval('Cliente').style.display = 'none';
	 	 eval('OrdemServico').style.display = 'none';
	 	 eval('Hidrometro').style.display = 'none';
	 	// eval('CobrancaAcao').style.display = 'none';
	 	 eval('Localidade').style.display = 'none';
	 	 eval('Municipio').style.display = 'none';
	 	 eval('SetorComercial').style.display = 'none';
	 	// eval('UnidadeOperacional').style.display = 'none';
	 	 eval('AvisoBancario').style.display = 'none';
	 	eval('RegistroAtendimento').style.display = 'none';
	 	eval('ResolucaoDiretoria').style.visibility = 'hidden';
	 	eval('ImovelSituacaoTipo').style.visibility = 'hidden';
 }

 function exibirTodosArgumentos(){
	 // COLOCAR TODOS OS ARGUMENTOS PARA "NONE" e depois executar AJAX. para exibir. XXX
	 	 eval('Imovel').style.display = '';
	 	 eval('Cliente').style.display = '';
	 	 eval('OrdemServico').style.display = '';
	 	 eval('Hidrometro').style.display = '';
	 	// eval('CobrancaAcao').style.display = 'block';
	 	 eval('Localidade').style.display = '';
	 	 eval('Municipio').style.display = '';
	 	 eval('SetorComercial').style.display = '';
	 	// eval('UnidadeOperacional').style.display = 'block';
	 	 eval('AvisoBancario').style.display = '';
	 	eval('RegistroAtendimento').style.display = '';
	 	eval('ResolucaoDiretoria').style.display = '';
 }
 
 
  function limparFiltrar(){
    limparUsuario();
 	document.forms[0].dataInicial.value = '';
 	document.forms[0].dataFinal.value = '';
 	document.forms[0].horaInicial.value = '';
 	document.forms[0].horaFinal.value = '';
 	document.forms[0].dataLimpa.value = '1';
 	document.forms[0].horaLimpa.value = '1';
 	limparImovel();
 	limparCliente();
 	limparRegistroAtendimento();
 	limparResolucaoDiretoria();
 	limparLocalidade();
 	limparSetorComercial();
 	limparAvisoBancario();
 	limparOrdemServico();
 	limparHidrometro();
 	limparMunicipio();
    limparFuncionalidade();
    
    
  }

  function salvarNomeOperacaoUnica(){
    
    var oper = document.forms[0].operacoes;
    if (oper != null && oper.selectedIndex != -1){
      document.forms[0].nomeOperacaoSelecionada.value = oper.options[oper.selectedIndex].text;
    }
  
  }
  
  function mascaraHoras(mydata, tecla){ 
		 
		 var valido = false;
		 
		 if (document.all) {
	        var codigo = event.keyCode;
		 } else {
	        var codigo = tecla.which;
		 }
		 
		 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
	         mydata.value = mydata.value + ':'; 
	     }  
	     
	     if (mydata.value.length == 5){ 
			//valido = false;
	    	var horaMinuto = mydata.value.split(':');
	    	if (horaMinuto.length == 2) {    
	    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
	    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0){
	    			valido = true;
	    		}
	    	}
	    	
	    	if (!valido)  {
				mydata.value =  '';
				mydata.focus();
	    	}
	    }
	    
	    return valido;	
	}
	
  
</script>
  
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>
  
</head>

<body leftmargin="5" topmargin="5" onload="carregarOperacao(document.forms[0].idFuncionalidade.value);">

<html:form action="/filtrarOperacaoEfetuadaAction" method="post" onsubmit="return validateFiltrarOperacaoEfetuadaActionForm(this);" >

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
      </div></td>
    <td width="625" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Filtrar Operação</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
            <table border="0" width="100%">
                <tbody>
                <tr> 
                  <td colspan="2">Para filtrar a(s) operação(ões), informe os dados abaixo:</td>
                </tr>
                <tr > 
                  <td ><strong>Funcionalidade:</strong></td>
                  <td>
                  
                  	<html:select property="idFuncionalidade" onchange="carregarOperacao(this.value);" style="width: 340px;">
						
						<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
						
						<logic:present name="colecaoFuncionalidades">
							<html:options collection="colecaoFuncionalidades" labelProperty="descricao" property="id" />
						</logic:present>
						
					</html:select>

                  </td>
                </tr>
                <tr> 
                  <td width="26%"><strong>Operações:</strong></td>
                  <td width="74%">
                  	<input type="hidden" name="nomeOperacaoSelecionada"
                  			value="<%=session.getAttribute("nomeOperacaoSelecionada") == null ? "" : session.getAttribute("nomeOperacaoSelecionada")%>">
					<html:select property="operacoes" styleId="operacoes"
						style="width:390px;" multiple="" onchange="javascript:salvarNomeOperacaoUnica()">
						<logic:notPresent name="operacoes">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						</logic:notPresent>
					</html:select>
                  </td>
                </tr>
                <tr> 
                  <td><strong>Usuário:</strong></td>
                  <td>
				    <input type="hidden" name="usuarioLimpo" value="0">
					<html:text maxlength="11"  name="FiltrarOperacaoEfetuadaActionForm" property="idUsuario" onblur="javascript:verificaNumeroInteiro(this);" onkeyup="javascript:verificaNumero(this);" style="text-transform: none;" size="11" onkeypress="javascript:return validaEnter(event, 'exibirFiltrarOperacaoEfetuadaAction.do', 'idUsuario');"/>
                    <img onclick="abrirPopup('exibirUsuarioPesquisar.do', 250, 495);" width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
   		      		<logic:equal property="usuarioNaoEncontrada" name="FiltrarOperacaoEfetuadaActionForm" value="true">
						<input type="text" name="nomeUsuario" size="40" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="pesquisa.usuario.inexistente"/>"/>
                    </logic:equal>
   		      		<logic:equal property="usuarioNaoEncontrada" name="FiltrarOperacaoEfetuadaActionForm" value="false">
						<html:text name="FiltrarOperacaoEfetuadaActionForm" property="nomeUsuario" size="40" maxlength="50" readonly="true" style="border: 0pt none ; background-color: rgb(239, 239, 239);" />
                    </logic:equal>
                     
                    <a href="javascript:limparUsuario();">
                    	<img  border="0" src="imagens/limparcampo.gif" height="21" width="23"> 
                    </a>
                    </td>
                </tr>
	                <tr> 
	                  <td><strong>Período de Realização:</strong></td>
                  <td> 
                    <input type="hidden" name="dataLimpa" value="0">
                  	<html:text name="FiltrarOperacaoEfetuadaActionForm" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataFinal,this);"
                  	 onblur="javascript:dataEstahLimpa()"  property="dataInicial" size="10" maxlength="10"/> 
						<a href="javascript:abrirCalendario('FiltrarOperacaoEfetuadaActionForm', 'dataInicial')"><img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
                    <strong>a</strong> 
                    <html:text name="FiltrarOperacaoEfetuadaActionForm" onkeyup="mascaraData(this, event);" property="dataFinal" size="10" maxlength="10" /> 
						<a href="javascript:abrirCalendario('FiltrarOperacaoEfetuadaActionForm', 'dataFinal')"><img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" align="middle" alt="Exibir Calendário" /></a> dd/mm/aaaa</td>

                </tr>
                <tr> 
                  <td><strong>Horário de Realização:</strong></td>
                  <td> 
                    <input type="hidden" name="horaLimpa" value="0">                  
                  <html:text name="FiltrarOperacaoEfetuadaActionForm" property="horaInicial" onkeyup="mascaraHora(this, event);replicarCampo(document.forms[0].horaFinal,this);" 
                    onblur="javascript:horaEstahLimpa();mascaraHoras(this, event);" size="10" maxlength="5"/>
                    <strong> a</strong> <html:text name="FiltrarOperacaoEfetuadaActionForm" onkeyup="mascaraHora(this, event);" property="horaFinal" size="10" maxlength="5"/>
                    hh:mm </td>
                </tr>
                <tr>
                  <td colspan="2"><hr></td>
                </tr>
              
 				<tr id="Imovel">
	            	<td width="26%">
	            		<strong>Imóvel: </strong>
	            	</td>
					<td width="74%">
						<html:text property="imovelArgumento" maxlength="9" size="9" onblur="javascript:verificaNumeroInteiro(this);"
									onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarImovel','imovelArgumento','Im&oacute;vel');"/>
									
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
						</a>
						 
						<logic:present name="idImovelNaoEncontrado" scope="request">
							<html:text property="imovelNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
						 
						<logic:notPresent name="idImovelNaoEncontrado" scope="request">
							<html:text property="imovelNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent> 
						<a href="javascript:limparImovel();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
						
					</td>
               </tr>

	            <tr id="Cliente">
	            
	             <td width="26%">
	             	<strong>Cliente: </strong>
	             </td>
	             
				  <td  width="74%">
				  	  <html:text property="clienteArgumento" size="9" maxlength="9" onblur="javascript:verificaNumeroInteiro(this);"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarCliente','clienteArgumento','Cliente');"/>
										
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do?tipo=cliente', 275, 480);">
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" style="cursor:hand;" alt="Pesquisar" border="0"/>
						</a> 
						
						<logic:present name="idClienteNaoEncontrado" scope="request">
							<html:text property="clienteNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
						
						<logic:notPresent name="idClienteNaoEncontrado" scope="request">
							<html:text property="clienteNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						 
						<a href="javascript:limparCliente();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
						</a>
					</td>
	            
               </tr>               
               
	            <tr id="OrdemServico" >
	            	<td bordercolor="#000000" width="26%">
	             		<strong>Ordem de Servi&ccedil;o: </strong>
	             	</td>
					<td width="74%">
						<html:text property="ordemServicoArgumento" size="9" maxlength="9" onblur="javascript:verificaNumeroInteiro(this);"
								onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarOrdemServico', 'ordemServicoArgumento','Ordem de Serviço');" />
						<a href="javascript:abrirPopup('exibirPesquisarOrdemServicoAction.do', 600, 500);">								 
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar OS" />
						</a> 
						<logic:present name="ordemServicoNaoEncontrado" scope="request">
							<html:text property="ordemServicoNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
						</logic:present> 
						
						<logic:notPresent name="ordemServicoNaoEncontrado" scope="request">
							<html:text property="ordemServicoNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
						</logic:notPresent> 
						<a href="javascript:limparOrdemServico();"> 
							<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
						</a>
					</td>
               </tr>
               
	            <tr id="Hidrometro" >
					 <td width="26%"> 
					 	<strong>Hidrômetro :</strong>
					 </td>

					<td width="74%">
						<html:hidden property="idHidrometroArgumento" />
						<html:text property="numeroHidrometroArgumento" size="9" maxlength="15"
								onkeypress="validaEnterString(event, 'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarHidrometro', 'numeroHidrometroArgumento');" />
						
						<a href="javascript:abrirPopup('exibirPesquisarHidrometroAction.do', 600, 500);">								 
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar OS" />
						</a> 
						
					<!-- <logic:present name="hidrometroInexistente" scope="request">
							<html:text property="hidrometroNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
						</logic:present> 
						
						<logic:notPresent name="hidrometroInexistente" scope="request">
							<html:text property="hidrometroNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
						</logic:notPresent> 
						 -->
						
						<a href="javascript:limparHidrometro();"> 
							<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
						</a>
					</td>
				</tr>
				

	            <!--  <tr id="CobrancaAcao" style="display:block">
					 <td width="26%"> 
					 	<strong>Cobranca Acao :</strong>
					 </td>
	                 <td width="74%">
	 					<html:text maxlength="9" name="FiltrarOperacaoEfetuadaActionForm" property="cobrancaAcaoArgumento" size="9"  />
	                 </td>
               </tr>  -->
               <tr id="Municipio" >
               
		             <td width="26%">
		             	<strong>Munic&iacute;pio:</strong>
		             </td>
               
					<td width="74%">
					
						<html:text maxlength="4" property="municipioArgumento" size="9" onblur="javascript:verificaNumeroInteiro(this);"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=municipio', 'municipioArgumento', 'Município');" />
						
						<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do', 600, 500);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Municipio" />
						</a> 
						
						<logic:present name="municipioNaoEncontrado" scope="request">
							<html:text property="municipioNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
						</logic:present> 
						
						<logic:notPresent name="municipioNaoEncontrado" scope="request">
							<html:text property="municipioNomeArgumento" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
						</logic:notPresent> 			
			
						<a href="javascript:limparMunicipio();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
               </tr>
               
	            <tr id="Localidade" >
					 <td width="26%"> 
					 	<strong>Localidade :</strong>
					 </td>
					 <td width="74%">
						<html:text maxlength="3" property="localidadeArgumento" size="9"
						onkeypress="javascript:validaEnterComMensagem(event,'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=localidade','localidadeArgumento','Localidade'); return isCampoNumerico(event);"/>
							
						<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 275, 480);">	
							<img width="23" height="21" border="0" style="cursor:hand;" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Localidade" />
						</a>
								
						<logic:present name="localidadeEncontrada" scope="request">
							<html:text property="localidadeNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
						
						<logic:notPresent name="localidadeEncontrada" scope="request">
							<html:text property="localidadeNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>								
								
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
               </tr>

	           <tr id="SetorComercial">
	            
	            	<td bordercolor="#000000" width="26%">
	            		<strong>Setor Comercial :</strong>
	            	</td>
	            	
					<td bordercolor="#000000" width="74%">
						<html:text maxlength="3" property="setorComercialArgumento" size="9"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarSetorComercial','setorComercialArgumento','Setor Comercial'); return isCampoNumerico(event); return localidadePreenchida(event);"/>
							
						<a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?caminhoRetornoTelaPesquisa=exibirFiltrarOperacaoEfetuadaAction&idLocalidade='+document.forms[0].localidadeArgumento.value,document.forms[0].localidadeArgumento.value,'Localidade');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Setor Comercial" />
						</a> 
						
<!--					<a href="javascript:abrirPopup('exibirPesquisarSetorComercialAction.do', 275, 480);">							
							<img width="23" height="21" border="0" style="cursor:hand;" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Setor Comercial" />
						</a>
 -->
						<logic:present name="setorComercialNaoEncontrado" scope="request">
							<html:text property="setorComercialNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
						
						<logic:notPresent name="setorComercialNaoEncontrado" scope="request">
							<html:text property="setorComercialNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						
						<a href="javascript:limparSetorComercial();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
               </tr>
               
	         <!--   <tr id="UnidadeOperacional" style="display:block">
					 <td width="26%"> 
					 	<strong>Unidade Operacional :</strong>
					 </td>
	                 <td width="74%">
	 					<html:text maxlength="9" name="FiltrarOperacaoEfetuadaActionForm" property="unidadeOperacionalArgumento" size="9"  />
	                 </td>
               </tr> --> 
	            <tr id="RegistroAtendimento" >
	            
	             <td  width="26%">
	             	<strong>RA - Registro de Atendimento: </strong>
	             </td>
	             
				  <td  width="74%">
				  	  <html:text property="registroAtendimentoArgumento" size="9" maxlength="9" onblur="javascript:verificaNumeroInteiro(this);"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirFiltrarOperacaoEfetuadaAction.do?acaoEnterDoPopUp=consultarRegistroAtendimento','registroAtendimentoArgumento','RegistroAtendimento');"/>
										
						<a href="javascript:abrirPopup('exibirPesquisarRegistroAtendimentoAction.do', 275, 500);">
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" style="cursor:hand;" alt="Pesquisar" border="0"/>
						</a> 
						
						<logic:present name="idRegistroAtendimentoNaoEncontrado" scope="request">
							<html:text property="registroAtendimentoNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
						
						<logic:notPresent name="idRegistroAtendimentoNaoEncontrado" scope="request">
							<html:text property="registroAtendimentoNomeArgumento" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						 
						<a href="javascript:limparRegistroAtendimento();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
						</a>
					</td>
	            
               </tr>
               <tr id="AvisoBancario" >
	            
		              <td width="26%">
			            <strong>Aviso Bancário:</strong>
		    	      </td>
				     <td width="74%" colspan="3">
				         <strong> 
							  <html:text property="codigoAgenteArrecadador" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
							  <html:text property="dataLancamentoAviso" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
							  <html:text property="numeroSequencialAviso" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
							  
							  <a href="javascript:abrirPopup('exibirPesquisarAvisoBancarioAction.do?limparForm=1', 475, 765);">
									<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Aviso Bancário" />
							  </a>
							  
							 <a href="javascript:limparAvisoBancario();"> 
							 	<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /> 
							 </a>
						</strong>
						<html:hidden property="idAvisoBancario"/>
					</td>
               </tr>
               	<tr>
		 		 	<td colspan="2">&nbsp;</td>
				</tr>
				<tr id="ResolucaoDiretoria" style="visibility:hidden">
					<td><strong>Número da RD:</strong></td>
					<td><html:select property="resolucaoDiretoria" tabindex="3">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="collectionResolucaoDiretoria"
							labelProperty="numeroResolucaoDiretoria" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				<tr id="ImovelSituacaoTipo" style="visibility:hidden">
					<td><strong>Tipo da Situação do Imóvel:</strong></td>
					<td><html:select property="imovelSituacaoTipo" tabindex="2">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="collectionImovelSituacaoTipo"
							labelProperty="descricaoImovelSituacaoTipo" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>					
				<tr>
				  <td colspan="2">&nbsp;</td>
				</tr>
                <tr> 
                  <td class="style1"><input type="button" class="bottonRightCol" value="Limpar" onclick="javascript:limparFiltrar();"/>
                  <p>&nbsp;</p></td>
                  <td class="style1"> <table>
                      <tbody><tr> 
                        
                        <td align="right" width="500"><input type="button" class="bottonRightCol" value="Filtrar" onclick="javascript:chamarFiltrar();"/></td>
                        <td>&nbsp;</td>
                      </tr>
                    </tbody></table></td>
                </tr>
            </tbody>
     </table>
      <p>&nbsp;</p>
    </td>
  </tr>
</table>


<%@ include file="/jsp/util/rodape.jsp"%>


</html:form>
</body>
	<!-- operacao_efetuada_filtrar.jsp -->
</html:html>
