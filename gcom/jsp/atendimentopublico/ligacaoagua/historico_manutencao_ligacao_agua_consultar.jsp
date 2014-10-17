<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>


<script language="JavaScript">

function voltar(){
	
	var form = document.forms[0];
	form.action='exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do';
	form.submit();

}

function consultarOs(idOs){
	var url = 'exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+idOs;
	abrirPopup(url);
}

function consultarDocCobranca(idDocCobranca){
	var url = 'exibirDocumentoCobrancaItemAction.do?cobrancaDocumentosID='+idDocCobranca;
	abrirPopup(url, 400, 800);
}

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/consultarHistoricoManutencaoLigacaoAguaAction.do"
	name="FiltrarHistoricoManutencaoLigacaoAguaActionForm"
	type="gcom.gui.atendimentopublico.ligacaoagua.FiltrarHistoricoManutencaoLigacaoAguaActionForm"
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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Hist&oacute;rico da Manuten&ccedil;&atilde;o da Liga&ccedil;&atilde;o de &Aacute;gua</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<!-- Registros de Manutenção da Ligação da Água -->			
			<%@ include file="/jsp/atendimentopublico/ligacaoagua/historico_manutencao_ligacao_agua_registros.jsp" %>																			
			
			<table>
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Voltar" 
			          		onclick="javascript:voltar();"/>
					</td>
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
