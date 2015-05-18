<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

	<head>
		<%@ include file="/jsp/util/titulo.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<%@ page import="gcom.util.ConstantesSistema"%>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<html:javascript staticJavascript="false" formName="GerarRelatorioQuadroHidrometrosActionForm" dynamicJavascript="true" />
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>		
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>	
		<script language="JavaScript">
			function validarForm(form){
					form.submit();
			}
			
			
		function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
			var form = document.GerarRelatorioQuadroHidrometrosActionForm;
			
			if (tipoConsulta == 'localidade') {
		    	form.idLocalidade.value = codigoRegistro;
		    	form.descricaoLocalidade.value = descricaoRegistro;
		 		form.descricaoLocalidade.style.color = "#000000";    	
			}
			
		}
		 
			
		
	  function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
			case 2: // Localidade
				form.idLocalidade.value = "";
	  			form.descricaoLocalidade.value = "";	 
				break;
			
		}
	}
			
	  

	  function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

			if(objetoRelacionado.disabled != true){
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
			
			
		</script>
	</head>

	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
	
		<html:form action="/gerarRelatorioQuadroHidrometrosAction" name="GerarRelatorioQuadroHidrometrosActionForm" 
					type="gcom.gui.relatorio.micromedicao.GerarRelatorioQuadroHidrometrosActionForm" method="post">
		
			<%@ include file="/jsp/util/cabecalho.jsp"%>
			<%@ include file="/jsp/util/menu.jsp"%>
		
			<table width="770" border="0" cellspacing="4" cellpadding="0">
				<tr>
					<td width="149" valign="top" class="leftcoltext">
					<div align="center">
						<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
						<%@ include file="/jsp/util/mensagens.jsp"%>
					</div>
					</td>
					<td width="625" valign="top" class="centercoltext">
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
							<td class="parabg">Gerar Relatório Quadro de Hidrômetros </td>
							<td width="11" valign="top"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
		
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="3">Para gerar o relatório quadro de hidrômetros, informe os dados abaixo:</td>
						</tr>
						<tr>
							<td>
								<strong>Data para Referência:<font color="#FF0000">*</font></strong>
							</td>
							<td align="left">
								<html:text property="dataReferencia" size="10" maxlength="10" onkeyup="mascaraData(this, event);" />
								<a href="javascript:abrirCalendario('GerarRelatorioQuadroHidrometrosActionForm', 'dataReferencia')">
								<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
								dd/mm/aaaa
							</td>
						</tr>
						
						
				
					<tr>
					<td><strong>Localidade:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="idLocalidade" 
							size="3"		
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioQuadroHidrometrosAction.do?objetoConsulta=2','idLocalidade','Localidade');return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '','idLocalidade');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
                        <logic:empty name="GerarRelatorioQuadroHidrometrosActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioQuadroHidrometrosActionForm" property="idLocalidade">
						    <html:text property="descricaoLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparBorracha(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				
				
				
				
				
						
						
								<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="idGerenciaRegional" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
						
				 <tr>
					<td>
						<strong>Unidade Neg&oacute;cio:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="idUnidadeNegocio" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>	
						
						
						
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<font color="#FF0000">*</font> Campos Obrigat&oacute;rios
							</td>
						</tr>
					</table>		
					<table>
						<tr>
							<td width="550" align="right">&nbsp;</td>
							<td align="right">
								<gcom:controleAcessoBotao name="Button" value="Gerar" onclick="validarForm(document.forms[0]);" url="gerarRelatorioQuadroHidrometrosAction.do" />
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<%@ include file="/jsp/util/rodape.jsp"%>
		</html:form>
	</body>
</html:html>
