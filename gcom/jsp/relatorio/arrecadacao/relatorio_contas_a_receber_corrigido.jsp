<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.localidade.GerenciaRegional" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="FiltrarRelatorioContasAReceberCorrigidoActionForm"/>

<script language="JavaScript">
<!-- Begin

	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    	var form = document.forms[0];

    	if (tipoConsulta == 'unidadeNegocio') {
        	limparUnidadeNegocio();
          	form.unidadeNegocio.value = codigoRegistro;
          	form.descricaoUnidadeNegocio.value = descricaoRegistro;
          	form.descricaoUnidadeNegocio.style.color = "#000000";
        
        	if (form.localidade.disabled){
    	  		setarFoco('perfilImovel');
    	  	}
    	  	else{
    	  		form.localidade.focus();
    	  	}
        }
        
        if (tipoConsulta == 'localidade') {
        	limparLocalidade();
          	form.localidade.value = codigoRegistro;
          	form.descricaoLocalidade.value = descricaoRegistro;
          	form.descricaoLocalidade.style.color = "#000000";
        
        	if (form.setorComercial.disabled){
    	  		setarFoco('perfilImovel');
    	  	}
    	  	else{
    	  		form.setorComercial.focus();
    	  	}
        }
      	
        gerenciadorHabilitacaoCampos(form.opcaoTotalizacao.value);
     }

	function validarForm(form){
				
		var flag = true;
		
		if (form.gerencialRegional.disabled == false && form.gerencialRegional.value.length < 1){
				alert("Informe Gerência Regional");
				form.gerencialRegional.focus();
				flag = false;
				
		} else if (form.unidadeNegocio.disabled == false && form.unidadeNegocio.value.length < 1){
			alert("Informe Unidade Negócio");
			form.unidadeNegocio.focus();
			flag = false;
			
		} else if (form.localidade.disabled == false && form.localidade.value.length < 1){
			alert("Informe Localidade");
			form.localidade.focus();
			flag = false;
		}
	
		if(flag){
			submeterFormPadrao(form);
		}
	}

	function gerenciadorHabilitacaoImagemPesquisa(opcaoSelected, tipoPesquisa){

		switch (tipoPesquisa) { 
		    case "UnidadeNegocio":
		    
		    	if (opcaoSelected == "21" || opcaoSelected == "22" || opcaoSelected == "23"){
					abrirPopup('exibirPesquisarUnidadeNegocioAction.do', 320, 810);
				} 
		        
		       break; 
		    case "Localidade": 
		        
		        if (opcaoSelected == "13" || opcaoSelected == "14" || opcaoSelected == "15" ||
		        	opcaoSelected == "16" || opcaoSelected == "17" || opcaoSelected == "18"){
		        	
					abrirPopup('exibirPesquisarLocalidadeAction.do', 320, 810);
					limparSetorComercial();
				}
				
		       break; 
		    default:
	    
	    }
	}

	function limparUnidadeNegocio(){
		var form = document.forms[0];
		
		form.unidadeNegocio.disabled = false;
		
		form.unidadeNegocio.value = "";
		form.descricaoUnidadeNegocio.value = "";
	}

	function limparLocalidade(){
		var form = document.forms[0];
		
		form.localidade.disabled = false;
		
		form.localidade.value = "";
		form.descricaoLocalidade.value = "";
		
	}


	function gerenciadorHabilitacaoCampos(opcaoSelected){

		var form = document.forms[0];
		<!-- 1, 3, 24, 5 = ESTADO = ELSE-->
		<!-- 7, 25, 9 = Gerencia Regional-->
		<!-- 21, 23 = Unidade Negócio -->
		<!-- 13 = Localidade-->
		
		if (opcaoSelected == "7" || opcaoSelected == "25" || opcaoSelected == "9"){
		
	       limparUnidadeNegocio();
	       limparLocalidade();
	       
	       form.gerencialRegional.disabled = false;
	       
	       form.unidadeNegocio.disabled = true;
	       form.localidade.disabled = true;
	       
	    }
		else if (opcaoSelected == "21" ||  opcaoSelected == "23"){
			
		       form.gerencialRegional.value = "";
		       limparLocalidade();
		       
		       form.unidadeNegocio.disabled = false;
		       
		       form.gerencialRegional.disabled = true;
		       form.localidade.disabled = true;
		 
		} else if (opcaoSelected == "13"){
		
	       form.gerencialRegional.value = "";
	       limparUnidadeNegocio();

	       form.localidade.disabled = false;
	       
	       form.gerencialRegional.disabled = true;
	       form.unidadeNegocio.disabled = true;
	       
	    }
		else{
			
	       	form.gerencialRegional.value = "";
	       	limparUnidadeNegocio();
	       	limparLocalidade();
	       	
	        form.gerencialRegional.disabled = true;
	        form.unidadeNegocio.disabled = true;
	        form.localidade.disabled = true;
	        
		}
	}


	
-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/gerarRelatorioContasAReceberCorrigidoAction"
	name="FiltrarRelatorioContasAReceberCorrigidoActionForm"
	type="gcom.gui.relatorio.arrecadacao.FiltrarRelatorioContasAReceberCorrigidoActionForm"
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
			<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Gerar Relatório Contas A Receber Corrigido</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Paginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para gerar o relatório, informe os dados abaixo:</td>
				</tr>
				
<!-- 1, 3, 24, 5 = ESTADO-->
<!-- 7, 25, 9 = Gerencia Regional-->
<!-- 21, 23 = Unidade Negócio -->
<!-- 13 = Localidade-->
				<tr>
					<td><strong>Opção de Totalização:</strong></td>
					<td>
						<html:select property="opcaoTotalizacao" tabindex="3" style="width:350" onchange="gerenciadorHabilitacaoCampos(this.value);">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>"> &nbsp; </html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_ESTADO%>"><%=ConstantesSistema.ESTADO%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL%>"><%=ConstantesSistema.ESTADO_GERENCIA_REGIONAL%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO%>"><%=ConstantesSistema.ESTADO_UNIDADE_NEGOCIO%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_ESTADO_LOCALIDADE%>"><%=ConstantesSistema.ESTADO_LOCALIDADE%></html:option>
					
						<html:option value="<%="" + ConstantesSistema.CODIGO_GERENCIA_REGIONAL%>"><%=ConstantesSistema.GERENCIA_REGIONAL%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_GERENCIA_REGIONAL_UNIDADE_NEGOCIO%>"><%=ConstantesSistema.GERENCIA_REGIONAL_UNIDADE_NEGOCIO%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE%>"><%=ConstantesSistema.GERENCIA_REGIONAL_LOCALIDADE%></html:option>
						
						<html:option value="<%="" + ConstantesSistema.CODIGO_UNIDADE_NEGOCIO%>"><%=ConstantesSistema.UNIDADE_NEGOCIO%></html:option>
						<html:option value="<%="" + ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE%>"><%=ConstantesSistema.UNIDADE_NEGOCIO_LOCALIDADE%></html:option>
						
						<html:option value="<%="" + ConstantesSistema.CODIGO_LOCALIDADE%>"><%=ConstantesSistema.LOCALIDADE%></html:option>
						</html:select>
					</td>
				</tr>
				
      
			   <tr>
		      	<td><strong>Gerência Regional:</strong></td>
		        <td>
					<html:select property="gerencialRegional"  tabindex="4" style="width:350">
						<html:option value="">&nbsp;</html:option>
						<logic:iterate name="colecaoGerenciaRegional" id="gerenciaRegional" type="GerenciaRegional">
							<html:option value="<%=""+ gerenciaRegional.getId()%>">
							<%= gerenciaRegional.getNomeAbreviado() + " - " + gerenciaRegional.getNome()%></html:option>
						</logic:iterate>
					</html:select>
				</td>
		      </tr>
      
			<!-- Unidade Negócio -->
		      <tr>
		      	<td width="183" HEIGHT="30"><strong>Unidade Negócio:</strong></td>
		        <td width="432">
		        	
		        	<html:text property="unidadeNegocio" size="4" maxlength="4" tabindex="5" onkeypress="validaEnterComMensagem(event, 'exibirRelatorioContasAReceberCorrigidoAction.do?pesquisarUnidadeNegocio=OK', 'unidadeNegocio', 'Unidade Negócio');"/>
					<a href="javascript:gerenciadorHabilitacaoImagemPesquisa(document.forms[0].opcaoTotalizacao.value, 'UnidadeNegocio')" title="Pesquisar"><img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
		
					<logic:present name="corUnidadeNegocio">
		
						<logic:equal name="corUnidadeNegocio" value="exception">
							<html:text property="descricaoUnidadeNegocio" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="corUnidadeNegocio" value="exception">
							<html:text property="descricaoUnidadeNegocio" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="corUnidadeNegocio">
		
						<logic:empty name="FiltrarRelatorioContasAReceberCorrigidoActionForm" property="unidadeNegocio">
							<html:text property="descricaoUnidadeNegocio" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="FiltrarRelatorioContasAReceberCorrigidoActionForm" property="unidadeNegocio">
							<html:text property="descricaoUnidadeNegocio" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
						
		
					</logic:notPresent>
		        	
		        	<a href="javascript:gerenciadorHabilitacaoImagemLimpar(document.forms[0].opcaoTotalizacao.value, 'UnidadeNegocio')" title="Apagar"><img src="<bean:message key='caminho.imagens'/>limparcampo.gif" border="0"></a>
				</td>
		      </tr>
		      
		      <!-- Fim Unidade Negócio -->
				
		 <tr>
      		<td width="183" HEIGHT="30">
      			<strong>Localidade:</strong>
      		</td>
        	
        	
        	<td width="432">
        	
        	<html:text property="localidade" size="4" maxlength="3" tabindex="6" onkeypress="validaEnterComMensagem(event, 'exibirRelatorioContasAReceberCorrigidoAction.do?pesquisarLocalidade=OK', 'localidade', 'Localidade');"/>
			<a href="javascript:gerenciadorHabilitacaoImagemPesquisa(document.forms[0].opcaoTotalizacao.value, 'Localidade')" title="Pesquisar"><img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>

			<logic:present name="corLocalidade">

				<logic:equal name="corLocalidade" value="exception">
					<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corLocalidade" value="exception">
					<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corLocalidade">

				<logic:empty name="FiltrarRelatorioContasAReceberCorrigidoActionForm" property="localidade">
					<html:text property="descricaoLocalidade" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="FiltrarRelatorioContasAReceberCorrigidoActionForm" property="localidade">
					<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>
        	
        	<a href="javascript:gerenciadorHabilitacaoImagemLimpar(document.forms[0].opcaoTotalizacao.value, 'Localidade')" title="Apagar"><img src="<bean:message key='caminho.imagens'/>limparcampo.gif" border="0"></a>
		</td>
      </tr>

			<!-- ESPAÇO	 -->
			<tr>
				<td>
					<p>&nbsp;</p>
				</td>
				<td>
				<p>&nbsp;</p>
				</td>
			</tr>

			<!-- BOTÕES -->				
			<tr>
				<td>
					<input name="Submit22" class="bottonRightCol" value="Limpar" type="button" onclick="window.location.href='/gsan/exibirRelatorioContasAReceberCorrigidoAction.do?menu=sim';">
				</td>
				<td align="right">
					<INPUT type="button" onclick="validarForm(document.forms[0]);" name="botaoGerar" class="bottonRightCol" value="Gerar Relatório" tabindex="16">
				</td>
				
			</tr>
		</table>
	</td>
	</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
<SCRIPT LANGUAGE="JavaScript"> gerenciadorHabilitacaoCampos(document.forms[0].opcaoTotalizacao.value);  </SCRIPT>
</body>
<!-- relatorio_contas_a_receber_corrigido.jsp -->
</html:html>
