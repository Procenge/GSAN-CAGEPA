<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gsanLib.tld" prefix="gsan" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<html:javascript staticJavascript="false"  formName="FiltrarNegativadorMovimentoActionForm" />	
	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarPerfilParcelamentoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">


	function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	
		
	}
	
	
	function limpar(){
		form = document.forms[0];
		
		form.idNegativador.value = "-1";
		form.codigoRegistro.value = "";
		form.descricaoRegistroTipo.value = "";	
		form.tipoMovimento="3";	
		
	}
	
	
		
	function DateValidations () { 
     this.aa = new Array("dataProcessamentoInicial", "Data de Processamento Inicial não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("dataProcessamentoFinal", "Data de Processamento Final não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 
	
	 function validateFiltrarNegativadorMovimentoActionForm() {                                                                   
     var form = document.forms[0];       

       	if(validateDate(form)){
    		submeterFormPadrao(form);
		}
       
   } 
	

	function verificarMovimentoRegistroChecado(valor){
		form = document.forms[0];
		
		if( valor==1){	
		 form.movimentoCorrigido[0].disabled = true;
         form.movimentoCorrigido[1].disabled = true;
     	}else{
     	 form.movimentoCorrigido[0].disabled = false;
         form.movimentoCorrigido[1].disabled = false;
     	}
		
	}
	
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){


	if(objetoRelacionado.readOnly != true){
		if (objeto == null || codigoObjeto == null){
		
			
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
  }
	
	
function habilitarPesquisaImovel(form) {
	if (form.idImovel.readOnly == false) {
		chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',form.idImovel.value);
	}	
}


function validaEnterImovel(event, caminho, campo) {		
	retorno = validaEnter(event, caminho, campo);			
}

	function limparImovel() {
		var form = document.forms[0];

		if (form.nomeImovel != null){
			form.nomeImovel.value = '';
		}

		form.idImovel.value = ''; 	
	}	

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		
	    var form = document.forms[0];

	    if (tipoConsulta == 'imovel') {
	      limparImovel();
	      form.idImovel.value = codigoRegistro;
	      form.nomeImovel.value = descricaoRegistro;
	      form.nomeImovel.style.color = "#000000";
	      form.idImovel.focus();
	    }
	    
	  }

</script>
</head>
<body leftmargin="5" topmargin="5"
onload="setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarNegativadorMovimentoAction"
	name="FiltrarNegativadorMovimentoActionForm"
	type="gcom.gui.cobranca.spcserasa.FiltrarNegativadorMovimentoActionForm"
	method="post">


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
			</div>
		</td>
		<td valign="top" class="centercoltext">
            <table>
              <tr><td></td></tr>
              
            </table>
            
            
             <!--Início Tabela Reference a Páginação da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="imagens/parahead_left.gif"/></td>
                <td class="parabg">Consultar Movimento de Negativador</td>
                <td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif"/></td>
              </tr>
            </table>
            <!--Fim Tabela Reference a Páginação da Tela de Processo-->
<p>&nbsp;</p>
            <table width="100%" border="0" >
              <tr> 
                <td colspan="4">Para consultar movimento de negativador, informe 
                  os dados abaixo:</td>
              </tr>
              <tr> 
                <td width="274"><strong>Negativador:<strong><font color="#FF0000"></font></strong></strong></td>
                <td width="337" colspan="3">
                <logic:present name="colecaoNegativador">  
            	 <html:select property="idNegativador" tabindex="7">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				   <logic:present name="colecaoNegativador">
					 <html:options collection="colecaoNegativador" labelProperty="cliente.nome" property="id"/>
				    </logic:present>
				</html:select>
         		 </logic:present>    
         		 </td>
              </tr>
              <tr> 
                <td width="274"><strong>Tipo do Movimento:</strong></td>
                <td colspan="3"><strong><strong>
                  <html:radio property="tipoMovimento" value="1"/>    
                  <strong>Inclus&atilde;o
                   <html:radio property="tipoMovimento" value="2"/> 
					Exclus&atilde;o<strong><strong>
				   <html:radio property="tipoMovimento" value="3" /> 
					Todos</strong></strong></strong></strong></strong></td>
              </tr>
              
              
             
             	<tr>
						<td width="25%"><strong>Matrícula do Imóvel:</strong></td>
						<td width="75%"><html:text maxlength="9"
							name="FiltrarNegativadorMovimentoActionForm"
							property="idImovel" size="9"
							onkeypress="javascript:return validaEnterImovel(event, 'exibirFiltrarNegativadorMovimentoAction.do', 'idImovel');" />
						<a
							href="javascript:habilitarPesquisaImovel(document.forms[0]);">
						<img width="23" height="21"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							border="0" /></a> 
						
						<logic:equal property="imovelNaoEncontrado"
							name="FiltrarNegativadorMovimentoActionForm" value="true">
							<input type="text" name="nomeImovel" size="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								value="<bean:message key="atencao.imovel.inexistente"/>" />
						</logic:equal> 
						<logic:notEqual property="imovelNaoEncontrado"
							name="FiltrarNegativadorMovimentoActionForm" value="true">
							<html:text size="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								name="FiltrarNegativadorMovimentoActionForm"
								property="nomeImovel" />						
						</logic:notEqual>
						
						<a href="javascript:limparImovel();"> <img
							border="0" src="imagens/limparcampo.gif" height="21" width="23">
						</a></td>
					</tr>
             
             
             
              
              
              <tr> 
                <td><strong>N&uacute;mero Sequencial do Arquivo (NSA):</strong></td>
                <td colspan="3"><strong> 
                  <html:text property="numeroSequencialArquivo" size="9" maxlength="9" onkeypress="return isCampoNumerico(event);"/>    
                  </strong></td>
              </tr>
              
              
              <tr> 
                <td height="25"><strong>Per&iacute;odo de Processamento do Movimento:</strong></td>
                <td colspan="3"><strong> 
                   <html:text property="dataProcessamentoInicial" size="10" maxlength="10"
						tabindex="8" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendarioReplicando('FiltrarNegativadorMovimentoActionForm', 'dataProcessamentoInicial','dataProcessamentoFinal')">
					    <img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a</strong> 
                <html:text property="dataProcessamentoFinal" size="10" maxlength="10"
						tabindex="8" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('FiltrarNegativadorMovimentoActionForm', 'dataProcessamentoFinal')">
					    <img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)  </td>
              </tr>
              <tr> 
                <td><strong> Movimentos:</strong></td>
                <td colspan="3"><strong><span class="style1"><strong> <span class="style1"><strong><strong><strong>
                    <html:radio property="movimento" value="1"/> 
                  <strong>Com Retorno 
                    <html:radio property="movimento" value="2"/> 
                  <strong><strong><strong><strong><strong>Sem Retorno</strong></strong></strong></strong></strong><strong><strong>
				    <html:radio property="movimento" value="3"/> 
					Todos </strong></strong></strong></strong> </strong></strong></span></strong></span></strong></td>
              </tr>
              <tr> 
                <td><strong>Movimentos com Registros:</strong></td>
                <td colspan="3"><strong><span class="style1"><strong> <span class="style1"><strong><span class="style1"><strong><span class="style1"><strong><strong> 
                  </strong></strong><strong><strong><strong><strong><strong><strong>
                   <html:radio property="movimentoRegistro" value="1" onclick="javascript:verificarMovimentoRegistroChecado(1)"/> 
                  <strong> Aceitos
                    <html:radio property="movimentoRegistro" value="2" onclick="javascript:verificarMovimentoRegistroChecado(2)"/> 
                  <strong><strong><strong><strong><strong>N&atilde;o Aceitos</strong></strong></strong></strong></strong><strong><strong>
                   <html:radio property="movimentoRegistro" value="3" onclick="javascript:verificarMovimentoRegistroChecado(3)"/> 
					Todos</strong></strong></strong></strong></strong></strong></strong> </strong></strong></span></strong></span></strong></span></strong></span></strong></td>
              </tr>
              
               <tr> 
                <td><strong>Movimentos com Registros:</strong></td>
                <td colspan="3"><strong><span class="style1"><strong> <span class="style1"><strong><span class="style1"><strong><span class="style1"><strong><strong> 
                  </strong></strong><strong><strong><strong><strong><strong><strong>
                   <html:radio property="movimentoCorrigido" value="1" /> 
                  <strong> Corrigidos
                    <html:radio property="movimentoCorrigido" value="2"/> 
                  <strong><strong><strong><strong><strong>N&atilde;o Corrigidos</strong></strong></strong></strong></strong><strong><strong>
                   <html:radio property="movimentoCorrigido" value="3"/> 
					Todos</strong></strong></strong></strong></strong></strong></strong> </strong></strong></span></strong></span></strong></span></strong></span></strong></td>
              </tr>
              
              
              
              
              <tr> 
                <td colspan="4">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="4"><div align="right"> 
                    <input type="button" class="bottonRightCol" value="Filtrar" onclick="javascript:validateFiltrarNegativadorMovimentoActionForm();">
                  </div></td>
              </tr>
            </table>
          <p>&nbsp;</p></tr>
		
	  </table>
 
         
                 
			<p>&nbsp;</p>
			</td>

		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
