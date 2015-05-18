<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarRegistroAtendimentoActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
	function reload() {
		var form = document.forms[0];
		
		if(form.tipoSolicitacao.selectedIndex == 0) {
			limparEspecificacao();
		}
		
		form.action = '/gsan/exibirFiltrarRegistroAtendimentoAction.do';
		form.submit();
	}

	function habilitaEspecificacao() {
		var form = document.forms[0];
		if (form.selectedTipoSolicitacaoSize.value == '1') {
			form.especificacao.disabled = false;	
		} else {
			form.especificacao.disabled = true;	
		}
	}
	/* Chama Popup */ 
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
	var unidade = 0;
	
	function setUnidade(tipo) {
		unidade = tipo;
	}
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
		//form.action='exibirFiltrarRegistroAtendimentoAction.do';
	    if (tipoConsulta == 'imovel') {
	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
		  form.inscricaoImovel.style.color = '#000000';	      
	    } else if (tipoConsulta == 'unidadeOrganizacional') {
	      if (unidade == 1) {
		      form.unidadeAtendimentoId.value = codigoRegistro;
		      form.unidadeAtendimentoDescricao.value = descricaoRegistro;
			  form.unidadeAtendimentoDescricao.style.color = '#000000';
	      } else if (unidade == 2) {
		      form.unidadeAtualId.value = codigoRegistro;
		      form.unidadeAtualDescricao.value = descricaoRegistro;
			  form.unidadeAtualDescricao.style.color = '#000000';		      
	      }
	      unidade = 0;
	    } else if (tipoConsulta == 'unidadeSuperior') {
		      form.unidadeSuperiorId.value = codigoRegistro;
		      form.unidadeSuperiorDescricao.value = descricaoRegistro;
		      form.action = 'exibirFiltrarRegistroAtendimentoAction.do?validaUnidadeOrganizacional=true';
		      form.submit();
	    } else if (tipoConsulta == 'municipio') {
		    form.municipioId.value = codigoRegistro;
		    form.municipioDescricao.value = descricaoRegistro;
		    form.municipioDescricao.style.color = '#000000';
	    } else if (tipoConsulta == 'bairro') {
	    	form.bairroId.value = "";
		    form.bairroCodigo.value = codigoRegistro;
		    form.bairroDescricao.value = descricaoRegistro;
		    form.bairroDescricao.style.color = '#000000';
		    form.action = 'exibirFiltrarRegistroAtendimentoAction.do?validaBairro=true';
		    form.submit();
	    } else if (tipoConsulta == 'logradouro') {
  	      	form.logradouroId.value = codigoRegistro;
	      	form.logradouroDescricao.value = descricaoRegistro;
	      	form.logradouroDescricao.style.color = '#000000';
	    } else if (tipoConsulta == 'usuario') {
	    	if(form.campoUsuario.value == '1'){
  	      	form.loginUsuario.value = codigoRegistro;
	      	form.nomeUsuario.value = descricaoRegistro;
	      	form.nomeUsuario.style.color = '#000000';
	    	}else if(form.campoUsuario.value == '2'){
	    		form.matriculaAtendente.value = codigoRegistro;
		    	form.nomeAtendente.value = descricaoRegistro;
		      	form.nomeAtendente.style.color = "#000000";
	    	}
	    } else if (tipoConsulta == 'funcionario') {
	    	form.matriculaAtendente.value = codigoRegistro;
	    	form.nomeAtendente.value = descricaoRegistro;
	      	form.nomeAtendente.style.color = "#000000";
	    }
	     
	}
	
    function limparPeriodoAtendimento() {
        var form = document.forms[0];
        
        if (form.periodoAtendimentoInicial.value != ''){
			form.periodoAtendimentoInicial.value="";
		}
		
		if (form.periodoAtendimentoFinal.value != ''){
			form.periodoAtendimentoFinal.value="";
		}			
  	}
	
	/* Limpar Form */
	function limparForm(){
		var form = document.forms[0];
		
		form.numeroRA.value = "";
		form.numeroRAManual.value = "";
		limparFormImovel();
		limparFormCliente();
		limparUsuario();
		form.situacao[0].checked = true;
		form.tipoSolicitacao.selectedIndex = 0;	
		form.especificacao.selectedIndex = 0;		
		limparEspecificacao();
		form.periodoAtendimentoInicial.value = "";
		form.periodoAtendimentoFinal.value = "";
		form.periodoEncerramentoInicial.value = "";
		form.periodoEncerramentoFinal.value = "";		
		limparUnidadeAtendimento();
		limparUnidadeAtual();
		//limparUnidadeSuperior();
		limparMunicipio();
		limparBairro();
		form.areaBairroId.selectedIndex = 0;
		limparAreaBairro();
		limparLogradouro();
		form.cpf.value = "";
		form.cnpj.value = "";
		form.solicitante.value = "";
		
		window.location.href = '/gsan/exibirFiltrarRegistroAtendimentoAction.do?menu=sim';
	}
	/* Clear Especificação */
	function limparEspecificacao() {
		var form = document.forms[0];

		for(i=form.especificacao.length-1; i>0; i--) {
			form.especificacao.options[i] = null;
		}
	}
	
	/* Limpar Imóvel */
	function limparFormImovel() {
		var form = document.forms[0];
		
      	form.matriculaImovel.value = "";
	    form.inscricaoImovel.value = "";
	    
	    form.matriculaImovel.focus();
	}
	/* Limpar Cliente */
	function limparFormCliente() {
		var form = document.forms[0];
		
      	form.codigoCliente.value = "";
	    form.nomeCliente.value = "";
	    
	    form.codigoCliente.focus();
	}
	/* Limpar Unidade Atendimento */
	function limparUnidadeAtendimento() {
		var form = document.forms[0];
		
      	form.unidadeAtendimentoId.value = '';
	    form.unidadeAtendimentoDescricao.value = '';
	
		form.unidadeAtendimentoId.focus();
	}
	/* Limpar Usuário */
	function limparUsuario() {
		var form = document.forms[0];
		
      	form.loginUsuario.value = "";
	    form.nomeUsuario.value = "";
	
		form.loginUsuario.focus();
	}	
	/* Limpar Unidade Atual */
	function limparUnidadeAtual() {
		var form = document.forms[0];
		
      	form.unidadeAtualId.value = '';
	    form.unidadeAtualDescricao.value = '';
	
		form.unidadeAtualId.focus();
	}
	/* Limpar Unidade Superior */
	function limparUnidadeSuperior() {
		var form = document.forms[0];
		
      	form.unidadeSuperiorId.value = '';
	    form.unidadeSuperiorDescricao.value = '';
	
		form.unidadeSuperiorId.focus();
	}
	/* Limpar Município */
	function limparMunicipio() {
		var form = document.forms[0];
		
      	form.municipioId.value = '';
	    form.municipioDescricao.value = '';
	    limparBairro();
	
		form.municipioId.focus();
	}
	/* Limpar Município */
	function limparMunicipioDigitacao() {
		var form = document.forms[0];
		
      	form.municipioDescricao.value = '';
	    limparBairro();
	}
	/* Limpar Bairro */
	function limparBairro() {
		var form = document.forms[0];
		
      	form.bairroId.value = '';
	    form.bairroCodigo.value = '';
	    form.bairroDescricao.value = '';
	    limparAreaBairro();
	    
	    form.bairroCodigo.focus();
	}
	/* Limpar Bairro */
	function limparBairroDigitacao() {
		var form = document.forms[0];
		
      	form.bairroId.value = '';
	    form.bairroDescricao.value = '';
	    limparAreaBairro();
	}
	/* Limpar Área Bairro */
	function limparAreaBairro() {
		var form = document.forms[0];
		for(i=form.areaBairroId.length-1; i>0; i--) {
			form.areaBairroId.options[i] = null;
		}
	}
	/* Limpar Logradouro */
	function limparLogradouro() {
		var form = document.forms[0];
		
      	form.logradouroId.value = '';
	    form.logradouroDescricao.value = '';
	
		form.logradouroId.focus();
	}
	/* Replica Data de Atendimento */
	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value
	}
	/* Replica Data de Encerramento */	
	function replicaDataEncerramento() {
		var form = document.forms[0];
		form.periodoEncerramentoFinal.value = form.periodoEncerramentoInicial.value
	}
	/* Replica Data de Trâmite */	
	function replicaDataTramite() {
		var form = document.forms[0];
		form.periodoTramiteFinal.value = form.periodoTramiteInicial.value
	}
	/* Replica Data Prevista de Atendimento */
	function replicaDataPrevistaAtendimento() {
		var form = document.forms[0];
		form.periodoPrevistoAtendimentoFinal.value = form.periodoPrevistoAtendimentoInicial.value
	}
	
	function habilitaCampoIndicadorGeradaPelaUnidadeAtual(){

		var form = document.forms[0];

		for(var i=0;i<form.indicadorOrdemServicoGerada.length;i++) {

			if(form.indicadorOrdemServicoGerada[i].checked) {

				if(form.indicadorOrdemServicoGerada[i].value == 1){
					document.forms[0].indicadorGeradaPelaUnidadeAtual[0].disabled=false;
					document.forms[0].indicadorGeradaPelaUnidadeAtual[1].disabled=false;
					document.forms[0].indicadorGeradaPelaUnidadeAtual[2].disabled=false;
					
				} else {
					document.getElementById('UndAtual').innerHTML = 'Unidade Atual: ';
					document.forms[0].indicadorGeradaPelaUnidadeAtual[2].checked=true;
					document.forms[0].indicadorGeradaPelaUnidadeAtual[0].disabled=true;
					document.forms[0].indicadorGeradaPelaUnidadeAtual[1].disabled=true;
					document.forms[0].indicadorGeradaPelaUnidadeAtual[2].disabled=true;
					
				}
			}
		}
		
	}

	function alteraNomeCampoUndAtual(){

		var form = document.forms[0];

		for(var i=0;i<form.indicadorGeradaPelaUnidadeAtual.length;i++) {

			if(form.indicadorGeradaPelaUnidadeAtual[i].checked) {

				if(form.indicadorGeradaPelaUnidadeAtual[i].value != 3){
					document.getElementById('UndAtual').innerHTML = 'Unidade Atual da OS: ';
				} else {
					document.getElementById('UndAtual').innerHTML = 'Unidade Atual: ';
				}
			}
		}
	
	}
	
	function validaForm(){
		var form = document.forms[0];
		/*if(validateFiltrarRegistroAtendimentoActionForm(form) && validarFormatacaoNumeracaoRAManual(form.numeroRAManual, "Número Manual")){
			submeterFormPadrao(form);
		}*/
		
		if(validateFiltrarRegistroAtendimentoActionForm(form)){
			//submeterFormPadrao(form);
				form.submit();
		}
	}

	function limparAtendente() {
    	var form = document.forms[0];

    	form.matriculaAtendente.value = "";
    	form.nomeAtendente.value = "";
  	}

	function setaUsuario(usuario){

		document.forms[0].campoUsuario.value = usuario;
	}

	function verificarExibeCampos(){
		var form = document.forms[0];
		var campoChecked = form.situacao[5].checked;
		if(campoChecked){
			document.getElementById('exibeMotivoReativacao').style.display = 'block';
			document.getElementById('exibeMatriculaAtendente').style.display = 'block';
		}else{
			document.getElementById('exibeMotivoReativacao').style.display = 'none';
			document.getElementById('exibeMatriculaAtendente').style.display = 'none';
			form.motivoReativacao.value = "-1";
			limparAtendente();
		}	
	}
	
</SCRIPT>


</head>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');verificarExibeCampos();habilitaCampoIndicadorGeradaPelaUnidadeAtual();alteraNomeCampoUndAtual();">
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
</logic:equal>

<html:form action="/filtrarRegistroAtendimentoAction.do" 
		   name="FiltrarRegistroAtendimentoActionForm" 
		   type="gcom.gui.atendimentopublico.registroatendimento.FiltrarRegistroAtendimentoActionForm"
		   method="post">

<html:hidden property="selectedTipoSolicitacaoSize" />
<html:hidden property="situacaoId"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<INPUT TYPE="hidden" name="campoUsuario">

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

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Filtrar RA - Registro de Atendimento</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
			<tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1">Para 
                  filtrar o RA - Registro de Atendimento, informe os dados abaixo:</td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              <tr> 
                <td><strong>N&uacute;mero do RA:</strong></td>
                <td colspan="6"><span class="style2"><strong>
                  <html:text property="numeroRA" size="9" maxlength="9" onkeyup="limparPeriodoAtendimento();" tabindex="1" />
                  </strong></span></td>
              </tr>
              <tr>
      			<td><strong>Número Manual:</strong></td>
        		<td colspan="6">
					<%-- <html:text property="numeroRAManual" size="12" maxlength="11" onkeyup="mascaraNumeracaoManual(event, this);limparPeriodoAtendimento();"/><strong>&nbsp;(000000000-0)</strong> --%>
					<html:text property="numeroRAManual" size="9" maxlength="9" onkeyup="limparPeriodoAtendimento();" tabindex="2"/>
				</td>
      		  </tr>
              <tr> 
                <td><strong><span class="style2">Matr&iacute;cula do Im&oacute;vel</span>:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="matriculaImovel" size="9" maxlength="9" 
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaImovel=true', 'matriculaImovel','Matrícula do Imovel');"
							   onkeyup="limparPeriodoAtendimento();" tabindex="3" />
					
					<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].matriculaImovel);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="inscricaoImovelEncontrada" scope="session">
						<html:text property="inscricaoImovel" readonly="true" 
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="inscricaoImovelEncontrada" scope="session">
						<html:text property="inscricaoImovel" readonly="true" 
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>  						 
						 
					<a href="javascript:limparFormImovel();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              
              
              <tr> 
                <td><strong><span class="style2">Matr&iacute;cula do Cliente</span>:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="codigoCliente" size="9" maxlength="9" 
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaCliente=true', 'codigoCliente','Codigo do Cliente');"
							   onkeyup="limparPeriodoAtendimento();" tabindex="3" />
					
					<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].codigoCliente);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="clienteEncontrado" scope="session">
						<html:text property="nomeCliente" readonly="true" 
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="clienteEncontrado" scope="session">
						<html:text property="nomeCliente" readonly="true" 
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>  						 
						 
					<a href="javascript:limparFormCliente();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              
              
              <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
              	<html:hidden property="cpf"/>
              	<html:hidden property="cnpj"/>
              	<html:hidden property="solicitante"/>
              </logic:equal>
              <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
              <tr> 
	                <td><strong>CPF: </strong></td>
	                <td colspan="6">
	                	<span class="style2">
	                		<strong>
	                			<html:text property="cpf" size="14" maxlength="11" tabindex="4"/>
	                 		</strong>
	                 	</span>
	                 </td>
	              </tr>
	              <tr> 
	                <td><strong>CNPJ: </strong></td>
	                <td colspan="6">
	                	<span class="style2">
	                		<strong>
	                			<html:text property="cnpj" size="17" maxlength="14" tabindex="5"/>
	                 		</strong>
	                 	</span>
	                 </td>
	              </tr>
	               <tr> 
	                <td><strong>Nome do Solicitante:</strong></td>
	                <td colspan="6">
	                	<span class="style2">
	                		<strong>
	                			<html:text property="solicitante" size="65" maxlength="60" tabindex="6"/>
	                 		</strong>
	                 	</span>
	                 </td>
	                </tr>
	                <tr>
	                <td>&nbsp;</td> 
	                <td colspan="6"><span class="style2"><strong> 
	                  <label> 
					  	<html:radio property="tipoPesquisa" value="0"/>
	 				  	Iniciando pelo texto
	 				  </label>
	                  <label> 
					  	<html:radio property="tipoPesquisa" value="1"/>
	 				  	Contendo o texto
	 				  </label>
					  </strong></span></td>
	              </tr>
              </logic:equal>
              <tr> 
                <td><strong>Login do usuário:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="loginUsuario" 
						size="12" 
						maxlength="11"
						style="text-transform: none;"
						onkeypress="javascript:pesquisaEnterSemUpperCase(event, 'exibirFiltrarRegistroAtendimentoAction.do', 'loginUsuario');" tabindex="7"/>
					
					<a href="javascript:chamarPopup('exibirUsuarioPesquisar.do?mostrarLogin=S&limpaForm=sim', 'usuario', null, null, 370, 600, '',document.forms[0].loginUsuario);setaUsuario(1);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="usuarioEncontrado" scope="session">
						<html:text property="nomeUsuario" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" 
							size="40" 
							maxlength="40" />
							
					</logic:present> 

					<logic:notPresent name="usuarioEncontrado" scope="session">
						<html:text property="nomeUsuario" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" 
							size="40" 
							maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUsuario();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
					</a>
                  </strong></span></td>
              </tr>
              
              <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
              <tr> 
                <td><strong>Situa&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
                  <label> </label>
				  <html:radio property="situacao" value="0" onclick="javascript: reload();" tabindex="8"/>
 				  Todos
                  <label> 
				  <html:radio property="situacao" value="1" onclick="javascript: reload();" tabindex="9"/>
 				  Pendentes</label>
                  <label> 
				  <html:radio property="situacao" value="2" onclick="javascript: reload();" tabindex="10"/>
				  Encerrados</label>
				  <html:radio property="situacao" value="3" onclick="javascript: reload();" tabindex="11"/>
				  Sem Local de Ocorr&ecirc;ncia</strong></span></td>
              </tr>
              </logic:equal>
              <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
	              <tr> 
	                <td><strong>Situa&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong> 
	                  <label> </label>
					  <html:radio property="situacao" value="0" onclick="javascript: reload();verificarExibeCampos();" tabindex="8"/>
	 				  Todos
	                  <label> &nbsp;&nbsp;
					  <html:radio property="situacao" value="1" onclick="javascript: reload();verificarExibeCampos();" tabindex="9"/>
	 				  Pendentes</label>
	                  <label> &nbsp;&nbsp;
					  <html:radio property="situacao" value="2" onclick="javascript: reload();verificarExibeCampos();" tabindex="10"/>
					  Encerrados</label>
					  <label> &nbsp;&nbsp;
					  <html:radio property="situacao" value="501" onclick="javascript: reload();verificarExibeCampos();" tabindex="10"/>
					  Reiterados</label></strong></span></td>
	              </tr>
              	  <tr> 
		            <td>&nbsp;</td>
		            <td colspan="6"><span class="style2"><strong> 
	                  <label> 
					  <html:radio property="situacao" value="3" onclick="javascript: reload();verificarExibeCampos();" tabindex="11"/>
					  Sem Local de Ocorr&ecirc;ncia</label>
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <html:radio property="situacao" value="500" onclick="javascript:verificarExibeCampos();" tabindex="11"/>
					  Reativados</strong></span></td>
				  </tr>
				  <tr id="exibeMotivoReativacao" style="dysplay: none;">
					<td><strong>Motivo da Reativação:<font color="#FF0000"></font></strong></td>
					<td><html:select property="motivoReativacao" tabindex="11">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoMotivoReativacao"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				  </tr>
				  <tr id="exibeMatriculaAtendente" style="dysplay: none;">
					<td><strong>Matr&iacute;cula Atendente :</strong></td>
					<td>
						<html:text maxlength="9" 
							tabindex="12"
							property="matriculaAtendente" 
							size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaAtendente=true','matriculaAtendente','Matrícula Atendente');"/>
							
							<a href="javascript:abrirPopup('exibirUsuarioPesquisar.do?limpaForm=sim', 570, 700);setaUsuario(2);">
							
								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Atendente" /></a> 
			
							<logic:present name="atendenteEncontrado" scope="session">
								<html:text property="nomeAtendente" 
									size="35"
									maxlength="35" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present> 
			
							<logic:notPresent name="atendenteEncontrado" scope="session">
								<html:text property="nomeAtendente" 
									size="35"
									maxlength="35" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:notPresent>
			
							<a href="javascript:limparAtendente();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" />
							</a>					
					</td>
				  </tr>
			  </logic:equal> 
    	   	 <tr> 
	              <td><strong>Ordem de Servi&ccedil;o Gerada:</strong></td>
	              <td colspan="6">
		              <span class="style2"><strong> 
			              <label>
			              <html:radio property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.SIM.toString()%>" onclick="javascript:habilitaCampoIndicadorGeradaPelaUnidadeAtual()"/>
			              Sim
			              </label>
						  <label> 
						  &nbsp;&nbsp; <html:radio property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>" onclick="javascript:habilitaCampoIndicadorGeradaPelaUnidadeAtual()"/>
						  Não
						  </label>
			              <label> 
			              &nbsp;&nbsp; <html:radio property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.TODOS.toString()%>" onclick="javascript:habilitaCampoIndicadorGeradaPelaUnidadeAtual()"/>
			              Ambas
			              </label>
		              </strong></span>
	              </td>
	          </tr>
	          <tr> 
	              <td><strong>Gerada pela Unidade Atual:</strong></td>
	              <td colspan="6">
		              <span class="style2"><strong> 
			              <label>
			              <html:radio property="indicadorGeradaPelaUnidadeAtual" value="<%=ConstantesSistema.SIM.toString()%>" onclick="javascript:alteraNomeCampoUndAtual();"/>
			              Sim
			              </label>
						  <label> 
						  &nbsp;&nbsp; <html:radio property="indicadorGeradaPelaUnidadeAtual" value="<%=ConstantesSistema.NAO.toString()%>" onclick="javascript:alteraNomeCampoUndAtual();"/>
						  Não
						  </label>
			              <label> 
			              &nbsp;&nbsp; <html:radio property="indicadorGeradaPelaUnidadeAtual" value="<%=ConstantesSistema.TODOS.toString()%>" onclick="javascript:alteraNomeCampoUndAtual();"/>
			              Ambas
			              </label>
		              </strong></span>
	              </td>
	          </tr>
		   	  <tr> 
		        <td><strong>Processo Adm./Jud. ?</strong></td>
		           <td colspan="6">
		            <span class="style2"><strong> 
		             <label>
		             <html:radio property="indicadorProcessoAdmJud" value="<%=ConstantesSistema.SIM.toString()%>"/> Sim
		             </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorProcessoAdmJud" value="<%=ConstantesSistema.NAO.toString()%>"/> Não
				  	 </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorProcessoAdmJud" value="<%=ConstantesSistema.TODOS.toString()%>"/> Ambas
				  	 </label>
		            </strong></span>
		           </td>
		      </tr>
		      
		      
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>		 
              <tr>
              	<td colspan="7"> 
              		<strong>
              			Situação da Guia de Pagamento para Geração do Serviço:
              		</strong>
                </td>
              </tr>
              
              <tr>              
				<td colspan="7">                
                	&nbsp 
                </td>
              </tr>                   
		   	  <tr> 
		        <td><strong>Vencida?</strong></td>
		           <td colspan="6">
		            <span class="style2"><strong> 
		             <label>
		             <html:radio property="indicadorRaVencidas" value="<%=ConstantesSistema.SIM.toString()%>"/> Sim
		             </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaVencidas" value="<%=ConstantesSistema.NAO.toString()%>"/> Não
				  	 </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaVencidas" value="<%=ConstantesSistema.TODOS.toString()%>"/> Ambas
				  	 </label>
		            </strong></span>
		           </td>
		      </tr>		
		      
		   	  <tr> 
		        <td><strong>Com Pagamento?</strong></td>
		           <td colspan="6">
		            <span class="style2"><strong> 
		             <label>
		             <html:radio property="indicadorRaPagamento" value="<%=ConstantesSistema.SIM.toString()%>"/> Sim
		             </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaPagamento" value="<%=ConstantesSistema.NAO.toString()%>"/> Não
				  	 </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaPagamento" value="<%=ConstantesSistema.TODOS.toString()%>"/> Ambos
				  	 </label>
		            </strong></span>
		           </td>
		      </tr>		
		      
		   	  <tr> 
		        <td><strong>Com Devolução do Valor?</strong></td>
		           <td colspan="6">
		            <span class="style2"><strong> 
		             <label>
		             <html:radio property="indicadorRaDevolucao" value="<%=ConstantesSistema.SIM.toString()%>"/> Sim
		             </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaDevolucao" value="<%=ConstantesSistema.NAO.toString()%>"/> Não
				  	 </label>
				  	 <label> 
				 		 &nbsp;&nbsp; 
				 		 <html:radio property="indicadorRaDevolucao" value="<%=ConstantesSistema.TODOS.toString()%>"/> Ambos
				  	 </label>
		            </strong></span>
		           </td>
		      </tr>			      	            
		      
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              <tr> 
                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:select property="tipoSolicitacao" style="width: 350px; height:100px;" multiple="true" onchange="javascript:reload();habilitaEspecificacao();" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoTipoSolicitacao" scope="session">
							<html:options collection="colecaoTipoSolicitacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Especifica&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:select property="especificacao" style="width: 350px;" multiple="true" tabindex="13">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoEspecificacao" scope="session">
							<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              <tr> 
                <td><strong>Per&iacute;odo de Previsão Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 			
						<html:text property="periodoPrevistoAtendimentoInicial" size="11" maxlength="10" tabindex="14" onkeyup="mascaraData(this, event);replicaDataPrevistaAtendimento();"/>
							<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoPrevistoAtendimentoInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/></a>
						a <html:text property="periodoPrevistoAtendimentoFinal" size="11" maxlength="10" tabindex="16" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoPrevistoAtendimentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/></a>					
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span>
                 </td>
              </tr>
              <tr> 
                <td><strong>Per&iacute;odo de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<logic:equal name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="2">
						<html:text property="periodoAtendimentoInicial" size="11" maxlength="10" tabindex="18" onkeyup="mascaraData(this, event)" readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/>
						a <html:text property="periodoAtendimentoFinal" size="11" maxlength="10" tabindex="20" onkeyup="mascaraData(this, event)"  readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/>
					</logic:equal>
					
					<logic:notEqual name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="2">
						<html:text property="periodoAtendimentoInicial" size="11" maxlength="10" tabindex="22" onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoAtendimentoInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="periodoAtendimentoFinal" size="11" maxlength="10" tabindex="23" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoAtendimentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</logic:notEqual>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Per&iacute;odo de Encerramento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<logic:equal name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="1">
						<html:text property="periodoEncerramentoInicial" size="11" maxlength="10" tabindex="24" onkeyup="mascaraData(this, event)"  readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" />
						a <html:text property="periodoEncerramentoFinal" size="11" maxlength="10" tabindex="25" onkeyup="mascaraData(this, event)"  readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" />
					</logic:equal>
					
					<logic:notEqual name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="1">
						<html:text property="periodoEncerramentoInicial" size="11" maxlength="10" tabindex="26" onkeyup="mascaraData(this, event);replicaDataEncerramento();"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoEncerramentoInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="periodoEncerramentoFinal" size="11" maxlength="10" tabindex="27" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoEncerramentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</logic:notEqual>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
              
              <tr> 
                <td><strong>Per&iacute;odo de Trâmite:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<logic:equal name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="2">
						<html:text property="periodoTramiteInicial" size="11" maxlength="10" tabindex="24" onkeyup="mascaraData(this, event)"  readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" />
						a <html:text property="periodoTramiteFinal" size="11" maxlength="10" tabindex="25" onkeyup="mascaraData(this, event)"  readonly="true" value=""/>
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" />
					</logic:equal>
					
					<logic:notEqual name="FiltrarRegistroAtendimentoActionForm" property="situacao" value="2">
						<html:text property="periodoTramiteInicial" size="11" maxlength="10" tabindex="26" onkeyup="mascaraData(this, event);replicaDataTramite();"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoTramiteInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="periodoTramiteFinal" size="11" maxlength="10" tabindex="27" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'periodoTramiteFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</logic:notEqual>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
               
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              <tr> 
                <td><strong>Unidade de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="unidadeAtendimentoId" size="6" maxlength="8"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaUnidadeAtendimento=true', 'unidadeAtendimentoId','Unidade Atendimento');" tabindex="28"/>
					
					<a href="javascript:setUnidade(1); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeAtendimentoId);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="unidadeAtendimentoEncontrada" scope="session">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeAtendimentoEncontrada" scope="session">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUnidadeAtendimento();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong id="UndAtual"> Unidade Atual:</strong></td>
                <td colspan="6" align="right"><div align="left"><span class="style2"><strong> 
					<html:text property="unidadeAtualId" size="6" maxlength="8"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaUnidadeAtual=true', 'unidadeAtualId','Unidade Atual');" tabindex="29"/>
					
					<a href="javascript:setUnidade(2); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeAtualId);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="unidadeAtualEncontrada" scope="session">
						<html:text property="unidadeAtualDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeAtualEncontrada" scope="session">
						<html:text property="unidadeAtualDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparUnidadeAtual();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                    </strong></span></div></td>
              </tr>
             <%-- <tr> 
                <td><strong> Unidade Superior:</strong></td>
                <td colspan="6" align="right"><div align="left"><span class="style2"><strong> 
					<html:text property="unidadeSuperiorId" size="4" maxlength="4"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaUnidadeSuperior=true', 'unidadeSuperiorId','Unidade Superior');"/>
					
					<a href="javascript:setUnidade(3); chamarPopup('exibirPesquisarUnidadeSuperiorAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeSuperiorId);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="unidadeSuperiorEncontrada" scope="session">
						<html:text property="unidadeSuperiorDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeSuperiorEncontrada" scope="session">
						<html:text property="unidadeSuperiorDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparUnidadeSuperior();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                    </strong></span></div></td>
              </tr> --%>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              <tr> 
                <td><strong>Munic&iacute;pio:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:text property="municipioId" size="4" maxlength="4"
							   onkeypress="javascript:limparBairroDigitacao();validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaMunicipio=true', 'municipioId','Município');" tabindex="30"/>
					
					<a href="javascript:chamarPopup('exibirPesquisarMunicipioAction.do', 'municipio', null, null, 275, 480, '',document.forms[0].municipioId);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="municipioEncontrada" scope="session">
						<html:text property="municipioDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="municipioEncontrada" scope="session">
						<html:text property="municipioDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparMunicipio();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Bairro:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:text property="bairroCodigo" size="4" maxlength="4"
							   onkeypress="javascript:limparBairroDigitacao();validaEnterDependencia(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaBairro=true', this, document.forms[0].municipioId.value, 'Município');" tabindex="31"/>
					
					<a href="javascript:abrirPopupDependencia('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].municipioId.value+'&indicadorUsoTodos=1', document.forms[0].municipioId.value, 'Município', 275, 480);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="bairroEncontrada" scope="session">
						<html:text property="bairroDescricao" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="bairroEncontrada" scope="session">
						<html:text property="bairroDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<html:hidden property="bairroId"/>
					
					<a href="javascript:limparBairro();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>&Aacute;rea do Bairro:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:select property="areaBairroId" style="width: 230px;" tabindex="32">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoBairroArea" scope="session">
							<html:options collection="colecaoBairroArea" labelProperty="nome" property="id" />
						</logic:present>
					</html:select>
                  </strong><strong> </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Logradouro:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:text property="logradouroId" size="9" maxlength="9"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoAction.do?validaLogradouro=true', 'logradouroId','Logradouro');" tabindex="33"/>
					
					<a href="javascript:abrirPopup('exibirPesquisarLogradouroAction.do?codigoMunicipio='+document.forms[0].municipioId.value+'&codigoBairro='+document.forms[0].bairroCodigo.value+'&indicadorUsoTodos=1&primeriaVez=1', 275, 480);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="logradouroEncontrada" scope="session">
						<html:text property="logradouroDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="logradouroEncontrada" scope="session">
						<html:text property="logradouroDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparLogradouro();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td colspan="6" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td colspan="6"> <div align="right"> </div></td>
              </tr>
              <tr> 
                <td><input type="button" name="adicionar2" class="bottonRightCol" value="Limpar" tabindex="35" onclick="javascript:limparForm();"></td>
                <td colspan="6"> <div align="right"> 
                    <input type="button" tabindex="34" name="Submit" class="bottonRightCol" value="Filtrar" onclick="validaForm();">
                  </div></td>
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