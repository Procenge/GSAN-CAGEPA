<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<SCRIPT LANGUAGE="JavaScript">
	function reload() {
		var form = document.forms[0];
		
		form.action = '/gsan/exibirFiltroRelatorioTotalContasEmitidasLocalidade.do';
		form.submit();
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
	
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
		//form.action='exibirFiltrarRegistroAtendimentoAction.do';
	    if (tipoConsulta == 'imovel') {
	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
		  form.inscricaoImovel.style.color = '#000000';	      
	    } 
	     
	}
	
   
	/* Limpar Form */
	function limparForm(){
		var form = document.forms[0];
		
		limparFormImovel();
		window.location.href = '/gsan/exibirFiltroRelatorioTotalContasEmitidasLocalidade.do?menu=sim';
	}

		
	/* Limpar Imóvel */
	function limparFormImovel() {
		var form = document.forms[0];
		
        form.referencia.value="";
	    form.referencia.focus();
	}
	
	
	
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioTotalContasEmitidasLocalidade"
   name="RelatorioTotalContasEmitidasLocalidadeForm"
   type="gcom.gui.relatorio.faturamento.RelatorioTotalContasEmitidasLocalidadeForm"
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
	      	
	    <td  valign="top" class="centercoltext">
		        <table height="100%">
			        <tr>
			          <td></td>
			        </tr>
		      	</table>

	
	      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        <tr>
	        
	          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
	          <td class="parabg">Relat&oacute;rio Total das Contas Emitidas por Localidade</td>
	          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	        </tr>
	      </table>
	       <p>&nbsp;</p>
	      
	      <table  border="0">

				<tr>
		          <td ><strong>Refer&ecirc;ncia:<font color="#FF0000">*</font></strong></td>
		          <td >
		          	<html:text property="referencia" size="7"  maxlength="7" 
		          		onkeyup="javascript:mascaraAnoMes(this, event);"
		          		onblur="javascript:verificaAnoMes(this);"
		          		/>
		          	&nbsp;mm/aaaa
		          	
		          </td>
		          <td>
		            <input name="button" type="submit"
						class="bottonRightCol" value="Gerar"
						>
		          </td>
		        </tr>
				
				
				 				
			</table>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			</td>
			
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>

</html:html>