<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.cadastro.cliente.Cliente"%>
<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<html:javascript staticJavascript="false" formName="GerarFaturaClienteResponsavelActionForm" />


<script language="JavaScript">

    function validarForm(){
       var form = document.GerarFaturaClienteResponsavelActionForm;
       
       if (validarMesAno(form.periodoReferenciaContasInicial) 
    		   && validarMesAno(form.periodoReferenciaContasFinal) 
    		   && validarDatasVencimentoContas(form)
    		   && validarPeriodos(form) 
    		   && validarIndicadorContasRevisao(form)
    		   && validarCliente(form.clientesSelecionados)) {
    	   
		       		enviarSelectMultiplo('GerarFaturaClienteResponsavelActionForm','clientesSelecionados');
            		submeterFormPadrao(form);
		}
    }

	function validarMesAno(mesAno){
		if(mesAno.value != ""){
			return verificaAnoMes(mesAno);
		} else {
			alert('Informe Mês/Ano.');
			mesAno.focus();
			return false;
		}
	}
	
	function validarPeriodos(form) {

		if (comparaMesAno(form.periodoReferenciaContasInicial.value, '>', form.periodoReferenciaContasFinal.value)){

			alert('Mês/Ano Final do Período de Referência das Contas é anterior ao Mês/Ano Inicial');
			return false;
		} else if (comparaData(form.periodoVencimentoContasInicial.value, '>', form.periodoVencimentoContasFinal.value)){

			alert('Data Final do Período de Vencimento das Contas é anterior à Data Inicial');
			return false;
		}
		
		return true;
    }

	function validarIndicadorContasRevisao(form) {
		if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked && !form.indicadorContasRevisao[2].checked){
			alert('\'Selecionar contas?\' deve ser selecionado.');
			return false;
		} else {
			return true;
		}
	}
	
	function validarCliente(clientesSelecionados){
		if(clientesSelecionados.length > 0){
			return true;
		} else {
			alert('Selecione os Clientes desejados.');
			clientesSelecionados.focus();
			return false;
		}
	}
	
	
	function validarDatasVencimentoContas(form){
		var resultado = true;
		if(form.periodoVencimentoContasInicial.value != ""){
			resultado = verificaData(form.periodoVencimentoContasInicial);
			
			if(form.periodoVencimentoContasFinal.value == "") {
				resultado = false;
				alert('Informe a Data Final do Período de Vencimento das Contas.');
			}
		} 
		if(resultado && form.periodoVencimentoContasFinal.value != ""){
			resultado = verificaData(form.periodoVencimentoContasFinal);
		} 
		
		return resultado;
	}
	

	  function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		
	    var form = document.forms[0];

	    if (tipoConsulta == 'cliente') {
	      form.idClienteFiltro.value = codigoRegistro;
	      form.nomeClienteFiltro.value = descricaoRegistro;
	      form.action = '/gsan/exibirGerarFaturaClienteResponsavelAction.do?add=Ok';
	      form.submit();
	    }
	    
	  }
	  
		function remover(cliente) {
			var form = document.forms[0];
			form.action = '/gsan/exibirGerarFaturaClienteResponsavelAction.do?remover='+ cliente;
		    form.submit();
		}
	
		
		function funcaoAdicionarCliente(){
			var form = document.forms[0];
			 
			 if (form.idClienteFiltro.value != null) {
				form.action = '/gsan/exibirGerarFaturaClienteResponsavelAction.do?add=Ok';
			    form.submit();
			 } else {
				 abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1&limparForm=OK', 570, 700);
			 }
			
		}
</script>

</head>
<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form
	action="/filtrarContasParaFaturaClienteResponsavelAction"
	method="post"
	name="GerarFaturaClienteResponsavelActionForm"
	type="gcom.gui.faturamento.GerarFaturaClienteResponsavelActionForm">

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


		<td valign="top" class="centercoltext">
		<table>
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Gerar Fatura do Cliente Responsável</td>
				<td width="11" valign="top"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<table width="100%" border="0">

			<tr>
				<td colspan="2">Para gerar fatura do cliente, informe os dados abaixo:</td>
			</tr>

			<!-- Período de Referência das Contas -->
	        <tr>
				<td>
					<strong>Per&iacute;odo de Refer&ecirc;ncia das Contas:<font color="#FF0000">*</font></strong>
				</td>
				<td>
					<span class="style2">
						<html:text maxlength="7"
							property="periodoReferenciaContasInicial" 
							size="7"
							onkeyup="mascaraAnoMes(this, event);replicarCampo(document.forms[0].periodoReferenciaContasFinal, document.forms[0].periodoReferenciaContasInicial);"
							tabindex="7" /> &nbsp; 
						<strong>a</strong> &nbsp; 
						<html:text maxlength="7"
							property="periodoReferenciaContasFinal" 
							size="7"
							onkeyup="mascaraAnoMes(this, event);" 
							tabindex="8" />
						</strong>(mm/aaaa)<strong>
					</span>
				</td>
			</tr>
			
			<!-- Período de Vencimento das Contas -->
			<tr>
                <td>
                	<strong>Per&iacute;odo de Vencimento das Contas:</strong>
                </td>
               
                <td>
                	<span class="style2">
                	
                	<strong> 
						
						<html:text property="periodoVencimentoContasInicial" 
							size="11" 
							maxlength="10" 
							tabindex="9" 
							onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].periodoVencimentoContasFinal, document.forms[0].periodoVencimentoContasInicial);"/>
						
						<a href="javascript:abrirCalendarioReplicando('GerarFaturaClienteResponsavelActionForm', 'periodoVencimentoContasInicial','periodoVencimentoContasFinal');">
						<img border="0" 
							src="<bean:message key='caminho.imagens'/>calendario.gif" 
							width="16" 
							height="15" 
							border="0" 
							alt="Exibir Calendário"/>
						</a>
						&nbsp;a&nbsp;					
						<html:text property="periodoVencimentoContasFinal" 
							size="11" 
							maxlength="10" 
							tabindex="10" 
							onkeyup="mascaraData(this, event)"/>
						
						<a href="javascript:abrirCalendario('GerarFaturaClienteResponsavelActionForm', 'periodoVencimentoContasFinal');">
						<img border="0" 
							src="<bean:message key='caminho.imagens'/>calendario.gif" 
							width="16" 
							height="15" 
							border="0" 
							alt="Exibir Calendário"/>
						</a>
						
					</strong>(dd/mm/aaaa)
                  	</span>
              	</td>
            </tr>
            
            <!-- Selecionar contas? -->
            <tr> 
				<td><strong>Selecionar contas?<font color="#FF0000">*</font></strong></td>
				<td>
					<strong>
						<html:radio property="indicadorContasRevisao" value="<%=""+ConstantesSistema.CONTAS_NORMAIS%>"/>Normal
						<html:radio property="indicadorContasRevisao" value="<%=""+ConstantesSistema.CONTAS_EM_REVISAO%>"/>Em Revisão
						<html:radio property="indicadorContasRevisao" value="<%=""+ConstantesSistema.TODOS%>"/>Ambas
				 	</strong>
				 </td>
			</tr>
            
            <!-- Motivo de Revisão -->
	        <tr>
				<td width="110"><strong>Motivo de Revisão:</strong></td>
				<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
						<tr>
							<td width="175">
								<html:select property="motivosRevisao" 
									size="4" 
									multiple="true" 
									style="width:350px">
								<logic:notEmpty name="colecaoContaMotivoRevisao">	
									<html:options collection="colecaoContaMotivoRevisao" 
										labelProperty="descricaoMotivoRevisaoConta" 
										property="id" />
								</logic:notEmpty>
								</html:select>
							</td>							
						</tr>
					</table>
				</td>
			</tr>
	        
		<tr>
			<td height="24" colspan="2" class="style1">
				<hr>
			</td>
		</tr>
	        
		<tr>
			<td colspan="3">
				<strong>Clientes:<font color="#FF0000">*</font>
			</td>
		</tr>
	        
		<tr>
		
				<td colspan="3">
					<html:text maxlength="9" tabindex="1" property="idClienteFiltro" size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarFaturaClienteResponsavelAction.do?add=Ok','idClienteFiltro','Código Cliente');"/>
											
					<logic:present name="codigoClienteEncontrada" scope="request">
						<html:text property="nomeClienteFiltro" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:present> 
				
				    <logic:notPresent name="codigoClienteEncontrada" scope="request">
						<html:text property="nomeClienteFiltro" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
					</logic:notPresent>
											
					<input type="button" align="left" class="bottonRightCol" value="Adicionar" tabindex="9" id="botaoEndereco"
						onclick="javascript:funcaoAdicionarCliente();">
					
				</td>
		</tr>
		
		<tr>
			<td colspan="2" height="70" valign="top">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
						<tr bgcolor="#90c7fc" height="18">
							<td width="10%" align="center"><strong>Remover</strong></td>
							<td align="center"><strong>Cliente</strong></td>
						</tr>
					</table>
					</td>
				</tr>
				<logic:present name="listaCliente">
					<tr>
						<td>
						<table width="100%" align="center" bgcolor="#99CCFF">
							<!--corpo da segunda tabela-->
							<%String cor = "#cbe5fe";%>
							<logic:iterate name="listaCliente" id="cliente">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF" height="18">
								<%} else {
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe" height="18">
								<%}%>
									<td width="10%" align="center">
										<img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" style="cursor:hand;" alt="Remover" onclick="javascript:if(confirm('Confirma remoção?')){remover(<bean:write name="cliente" property="id" />);}">
									</td>

									<td>
										<a href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="cliente" property="id" />, 500, 800)">
											<bean:write name="cliente" property="nome" />
										</a>
									</td>
									
								</tr>
							</logic:iterate>
						</table>
						</td>
					</tr>
				</logic:present>
			</table>
			</td>
		</tr>
			
		<tr>
			<td align="left" colspan="2"><font color="#FF0000">*</font> Campo Obrigat&oacute;rio</td>
		</tr>
		
		<logic:present name="listaCliente">
			<tr style="display:none">
				<td align="left" colspan="2">
					<html:select property="clientesSelecionados" size="0" multiple="true" style="width:0px" >
						<logic:iterate name="listaCliente" id="cliente" type="Cliente">
								<option value="<%= "" + cliente.getId() %>" selected="selected"><bean:write name="cliente" property="id" />	- <bean:write name="cliente" property="nome" /></option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
		</logic:present>
		
		</table>

		<table width="100%">
			<tr>
				<td align="left">
					<input name="Button" 
						type="button" 
						class="bottonRightCol"
						value="Desfazer" 
						align="left"
						onclick="window.location.href='<html:rewrite page="/exibirGerarFaturaClienteResponsavelAction.do?menu=sim"/>'">
						&nbsp;
						
					<input type="button"
						name="ButtonCancelar" 
						class="bottonRightCol" 
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
				</td>
				
				<td align="right">
					<gcom:controleAcessoBotao name="Button" 
				  		value="Filtrar" 
				  		onclick="javascript:validarForm();" 
				  		url="filtrarContasParaFaturaClienteResponsavelAction.do"/>
				  	
				</td>
			</tr>
		</table>
	</tr>


</table>
<%@ include file="/jsp/util/rodape.jsp"%></html:form>
</body>
</html:html>
