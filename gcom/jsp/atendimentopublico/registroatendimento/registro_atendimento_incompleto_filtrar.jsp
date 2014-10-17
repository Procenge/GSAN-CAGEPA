<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarRegistroAtendimentoIncompletoActionForm"/>

<SCRIPT LANGUAGE="JavaScript">

	function reload() {
		var form = document.forms[0];
		
		if(form.tipoSolicitacao.selectedIndex == 0) {
			limparEspecificacao();
		}
		
		form.action = '/gsan/exibirFiltrarRegistroAtendimentoIncompletoAction.do';
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
	var usuario = 0;
	function setUnidade(tipo) {
		unidade = tipo;
	}
	
	function setUsuario(tipo) {
		usuario = tipo;
	}
	
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
	    if (tipoConsulta == 'imovel') {
	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
		  form.inscricaoImovel.style.color = '#000000';	      
	    } else if (tipoConsulta == 'unidadeOrganizacional') {
		    if (unidade == 1 ) {
				  form.unidadeAtendimento.value = codigoRegistro;
				  form.unidadeAtendimentoDescricao.value = descricaoRegistro;
				  form.unidadeAtendimentoDescricao.style.color = '#000000';
		      } else if (unidade == 2) {
			      form.unidadeRetornoChamada.value = codigoRegistro;
				  form.unidadeRetornoChamadaDescricao.value = descricaoRegistro;
				  form.unidadeRetornoChamadaDescricao.style.color = '#000000';
		      }
		      unidade = 0;
	    } else if (tipoConsulta == 'usuario') {
	    	if (usuario == 1) {
	  	      	form.usuarioAtendimento.value = codigoRegistro;
		      	form.usuarioAtendimentoDescricao.value = descricaoRegistro;
		      	form.usuarioAtendimentoDescricao.style.color = '#000000';
		    } else if (usuario == 2) {
		   		form.usuarioRetornoChamada.value = codigoRegistro;
		      	form.usuarioRetornoChamadaDescricao.value = descricaoRegistro;
		      	form.usuarioRetornoChamadaDescricao.style.color = '#000000';
		    }
	    } else  if (tipoConsulta == 'cliente') {
      		limparPesquisaCliente();
      		form.cliente.value = codigoRegistro;
      		form.clientedescricao.value = descricaoRegistro;
      		form.clientedescricao.style.color = "#000000";
      		form.matriculaImovel.focus();
    	} else if (tipoConsulta == 'unidadeRetornoChamada') {
    		form.unidadeRetornoChamada.value = codigoRegistro;
    		form.unidadeRetornoChamadaDescricao.value = descricaoRegistro;
    		form.unidadeRetornoChamadaDescricao.style.color = "#000000";
    	} else if (tipoConsulta == 'usuarioRetornoChamada') {
    		form.usuarioRetornoChamada.value = codigoRegistro;
    		form.usuarioRetornoChamadaDescricao.value = descricaoRegistro;
    		form.usuarioRetornoChamadaDescricao.style.color = "#000000";
    	} else if (tipoConsulta == 'registroAtendimento') {
    		form.RADefinitivo.value = codigoRegistro;
    		form.descricaoRA.value = descricaoRegistro;
    		form.descricaoRA.style.color = "#000000";
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
	
	function limparPesquisaCliente() {
    	var form = document.forms[0];

      	form.cliente.value = "";
      	form.clientedescricao.value = "";
  	}
  	
	/* Limpar Form */
	function limparForm(){
		var form = document.forms[0];
		
		form.numeroRA.value = "";
		limparFormImovel();
		limparUsuario();
		form.indicadorRetornoChamada[0].checked = true;
		form.tipoSolicitacao.selectedIndex = 0;	
		form.especificacao.selectedIndex = 0;		
		limparEspecificacao();
		form.periodoAtendimentoInicial.value = "";
		form.periodoAtendimentoFinal.value = "";		
		limparUnidadeAtendimento();
		limparUnidadeRetornoChamada();
		limparPesquisaRA();
		
		window.location.href = '/gsan/exibirFiltrarRegistroAtendimentoIncompletoAction.do?menu=sim';
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
	
	/* Limpar Unidade Atendimento */
	function limparUnidadeAtendimento() {
		var form = document.forms[0];
		
      	form.unidadeAtendimento.value = '';
	    form.unidadeAtendimentoDescricao.value = '';
	
		form.unidadeAtendimento.focus();
	}
	
	/* Limpar Usuário */
	function limparUsuario() {
		var form = document.forms[0];
		
      	form.usuarioAtendimento.value = "";
	    form.usuarioAtendimentoDescricao.value = "";
	
		form.usuarioAtendimento.focus();
	}	

	/* Limpar Unidade de Retorno da Chamada */
	function limparUnidadeRetornoChamada() {
		var form = document.forms[0];
		form.unidadeRetornoChamada.value = '';
		form.unidadeRetornoChamadaDescricao.value = '';
	}
	
	/* Limpar Usuario de Retorno da Chamada */
	function limparUsuarioRetornoChamada() {
		var form = document.forms[0];
		form.usuarioRetornoChamada.value = '';
		form.usuarioRetornoChamadaDescricao.value = '';
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
	
	function validaForm(){
		var form = document.forms[0];
		
		if(validateFiltrarRegistroAtendimentoIncompletoActionForm(form)){
			form.submit();
		}
	}
	
	function limparPesquisaRA(){
		var form = document.forms[0];
		form.RADefinitivo.value = "";
		form.descricaoRA.value = "";
	}
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarRegistroAtendimentoIncompletoAction" 
		   name="FiltrarRegistroAtendimentoIncompletoActionForm" 
		   type="gcom.gui.atendimentopublico.registroatendimento.FiltrarRegistroAtendimentoIncompletoActionForm"
		   method="post">
		   
<html:hidden property="selectedTipoSolicitacaoSize" />
<%@ include file="/jsp/util/cabecalho.jsp"%>
<logic:notPresent parameter="apareceMenu" scope="request">
<%@ include file="/jsp/util/menu.jsp" %>
</logic:notPresent>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
  <logic:notPresent parameter="apareceMenu" scope="request">
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
      </logic:notPresent>
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
          <td class="parabg">Filtrar RA - Registro de Atendimento Incompleto</td>
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
                  <html:text property="numeroRA" size="9" maxlength="9" tabindex="1" />
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>DDD:</strong></td>
                <td colspan="6"><span class="style2"><strong>
                  <html:text property="codigoDDD" size="7" maxlength="3" tabindex="2" />
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Telefone:</strong></td>
                <td colspan="6"><span class="style2"><strong>
                  <html:text property="foneChamada" size="10" maxlength="10"  tabindex="3" />
                  </strong></span></td>
              </tr>
				<tr>
					<td height="30">
						<strong>Nome:</strong>
					</td>
					<td><html:text name="FiltrarRegistroAtendimentoIncompletoActionForm" property="nomeContato" size="50" maxlength="50" tabindex="4"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="top">
						<html:radio property="tipoPesquisa"
							value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" tabindex="5" />
						Iniciando pelo texto
						<html:radio property="tipoPesquisa"
							value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" tabindex="6" />
						Contendo o texto
					</td>
				</tr>
              <tr> 
                <td><strong>Chamada já retornada:</strong></td>
                <td align="top">
						<html:radio property="indicadorRetornoChamada"
							value="<%=ConstantesSistema.SIM.toString()%>" tabindex="7" />
						Sim
						<html:radio property="indicadorRetornoChamada"
							value="<%=ConstantesSistema.NAO.toString()%>" tabindex="8" />
						Nao
						<html:radio property="indicadorRetornoChamada"
							value="<%=ConstantesSistema.TODOS.toString()%>" tabindex="9" />
						Ambos
					</td>
              </tr>
              <tr>
      				<td HEIGHT="30"><strong>Motivo Atendimento Incompleto:</strong></td>
        			<td>
						<html:select property="motivoAtendimentoIncompleto" style="width: 350px; height:50px;" tabindex="12" multiple="true"> 
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoMotivoAtendimentoIncompleto" labelProperty="descricao" property="id"/>
							
						</html:select>
					</td>
      		  </tr>
              <tr> 
                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:select property="tipoSolicitacao" style="width: 350px; height:100px;" multiple="true" onchange="javascript:reload();habilitaEspecificacao();" tabindex="9">
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
					<html:select property="especificacao" style="width: 350px;" multiple="true" tabindex="10">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoEspecificacao" scope="session">
							<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              <tr>
					<td><strong>Cliente:</strong></td>
					<td colspan="6" height="24" width="78%">
					<html:text maxlength="9"
						property="cliente" tabindex="10" size="9"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do', 'cliente', 'cliente');" />
						
					<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do?','cliente',null,null, 400, 800,'',document.forms[0].cliente);">
					<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Cliente" /></a> 
					<logic:present name="idClienteNaoEncontrado">
						<logic:equal name="idClienteNaoEncontrado" value="true">
							<html:text property="clientedescricao" size="50" maxlength="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idClienteNaoEncontrado" value="true">
							<html:text property="clientedescricao" size="50" maxlength="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idClienteNaoEncontrado">
						<logic:empty name="FiltrarRegistroAtendimentoIncompletoActionForm" property="cliente">
							<html:text property="clientedescricao" value="" size="50" maxlength="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarRegistroAtendimentoIncompletoActionForm" property="cliente">
							<html:text property="clientedescricao" size="50" maxlength="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparPesquisaCliente();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
              <tr>
                <td><strong><span class="style2">Matr&iacute;cula do Im&oacute;vel</span>:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="matriculaImovel" size="9" maxlength="9" 
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do?validaImovel=true', 'matriculaImovel','Matrícula do Imovel');"
							   onkeyup="limparPeriodoAtendimento();" tabindex="11" />
					
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
                <td><strong>RA Definitivo:</strong></td>
                <td>
						
						<html:text maxlength="9" 
							tabindex="12"
							property="RADefinitivo" 
							size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do?validaRADefinitiva=1','RADefinitivo','Numero RA');"
							/>
							
							<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].RADefinitivo);">
								
								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar RA" /></a> 

							<logic:present name="numeroRAEncontrada" scope="request">
								
								<html:text property="descricaoRA" 
									size="45"
									maxlength="45" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present> 

							<logic:notPresent name="numeroRAEncontrada" scope="request">
								
								<html:text property="descricaoRA" 
									size="45"
									maxlength="45" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />
									
							</logic:notPresent>

							<a href="javascript:limparPesquisaRA();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
						</td>
              </tr>
              <tr> 
                <td><strong>Unidade Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="unidadeAtendimento" size="10" maxlength="8"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do', 'unidadeAtendimento','Unidade Atendimento');" tabindex="13"/>
					
					<a href="javascript:setUnidade(1);chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeAtendimento);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="unidadeAtendimentoEncontrada" scope="request">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeAtendimentoEncontrada" scope="request">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUnidadeAtendimento();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Usuário Atendimento:</strong></td>
                <td>
				    <input type="hidden" name="usuarioLimpo" value="0">
					<html:text maxlength="10"  name="FiltrarRegistroAtendimentoIncompletoActionForm" property="usuarioAtendimento" style="text-transform: none;" size="10" 
					onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do', 'usuarioAtendimento', 'Usuário Atendimento');" tabindex="14"/>
                    <a href="javascript:setUsuario(1);chamarPopup('exibirUsuarioPesquisar.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].usuarioAtendimento);">
                    <img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
   		      		
   		      		<logic:present name="usuarioAtendimentoDescricao" scope="request">
						<html:text property="usuarioAtendimentoDescricao" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
                    </logic:present>
   		      		<logic:notPresent name="usuarioAtendimentoDescricao" scope="request">
						<html:text property="usuarioAtendimentoDescricao" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
                    </logic:notPresent>
                     
                    <a href="javascript:limparUsuario();">
                    	<img  border="0" src="imagens/limparcampo.gif" height="21" width="23"> 
                    </a>
                    </td>
              </tr>
              <tr> 
                <td><strong>Unidade Retorno Chamada:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="unidadeRetornoChamada" size="10" maxlength="8"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do?validaUnidadeRetornoChamada=true', 'unidadeRetornoChamada','Unidade Retorno Chamada');" 
							   tabindex="15"/>
					
					<a href="javascript:setUnidade(2);chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeRetornoChamada', null, null, 275, 480, '',document.forms[0].unidadeRetornoChamada);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="unidadeRetornoChamadaDescricao" scope="request">
						<html:text property="unidadeRetornoChamadaDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeRetornoChamadaDescricao" scope="request">
						<html:text property="unidadeRetornoChamadaDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUnidadeRetornoChamada();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td><strong>Usuário Retorno Chamada:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="usuarioRetornoChamada" size="10" maxlength="10"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRegistroAtendimentoIncompletoAction.do?validaUsuarioRetornoChamada=true', 'usuarioRetornoChamada','Usuário Retorno Chamada');" 
							   tabindex="16"/>
					
					<a href="javascript:setUsuario(2);chamarPopup('exibirUsuarioPesquisar.do', 'usuarioRetornoChamada', null, null, 275, 480, '',document.forms[0].usuarioRetornoChamada);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="usuarioRetornoChamadaDescricao" scope="request">
						<html:text property="usuarioRetornoChamadaDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="usuarioRetornoChamadaDescricao" scope="request">
						<html:text property="usuarioRetornoChamadaDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUsuarioRetornoChamada();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
  
              <tr> 
                <td><strong>Per&iacute;odo de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="periodoAtendimentoInicial" size="11" maxlength="10" tabindex="17" onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
					<a href="javascript:abrirCalendarioReplicando('FiltrarRegistroAtendimentoIncompletoActionForm', 'periodoAtendimentoInicial','periodoAtendimentoFinal' );">
					<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					a <html:text property="periodoAtendimentoFinal" size="11" maxlength="10" tabindex="18" onkeyup="mascaraData(this, event)"/>
					<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoIncompletoActionForm', 'periodoAtendimentoFinal');">
					<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</strong>(dd/mm/aaaa)<strong> 
                  	</strong></span>
                 </td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
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
                <td><input type="button" name="adicionar2" class="bottonRightCol" value="Limpar" onclick="javascript:limparForm();" tabindex="20"></td>
                <td colspan="6"> <div align="right"> 
                    <input type="button" name="Submit" class="bottonRightCol" value="Filtrar" onclick="validaForm();" tabindex="19">
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