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
		<html:javascript staticJavascript="false" formName="GerarRelatorioQuadroHidrometrosSituacaoActionForm" dynamicJavascript="true" />
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>		
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>	
		<script language="JavaScript">
		
		function validarForm(form){
			form.submit();
		}
			
	
		function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
			var form = document.GerarRelatorioQuadroHidrometrosSituacaoActionForm;
			
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
		
	  	function reloadForm(){
	  		var form = document.forms[0];
	  	
	  		form.action='gerarRelatorioQuadroHidrometrosSituacaoAction.do';
		    form.submit();
	  	}
	  	
	  
			
		</script>
	</head>

	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
	
		<html:form action="/gerarRelatorioQuadroHidrometrosSituacaoAction" name="GerarRelatorioQuadroHidrometrosSituacaoActionForm" 
					type="gcom.gui.relatorio.micromedicao.GerarRelatorioQuadroHidrometrosSituacaoActionForm" method="post">
		
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
							<td class="parabg">Gerar Relatório Quadro Situação de Hidrômetros </td>
							<td width="11" valign="top"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
		
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="3">Para gerar o relatório Quadro Situação de Hidrômetros, informe os dados abaixo:</td>
						</tr>
						<tr>
							<td width="26%"><strong>Mês/Ano do Faturamento:<font
								color="#FF0000">*</font></strong></td>
							<td colspan="2"><html:text property="mesAno" size="7" maxlength="7" onkeyup="mascaraAnoMes(this, event);"/>
							<strong>&nbsp; </strong>mm/aaaa</td>
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
					<td>
						<strong>Unidade Federa&ccedil;&atilde;o:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="idUnidadeFederacao" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeFederacao" scope="request">
								<html:options collection="colecaoUnidadeFederacao"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>		
						
				
				
					<tr>
					<td><strong>Localidade:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="idLocalidade" 
							size="3"		
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioQuadroHidrometrosSituacaoAction.do?objetoConsulta=2','idLocalidade','Localidade');return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '','idLocalidade');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
                        <logic:empty name="GerarRelatorioQuadroHidrometrosSituacaoActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioQuadroHidrometrosSituacaoActionForm" property="idLocalidade">
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
					<td><strong>Capacidade:</strong></td>
					<td><html:select property="idHidrometroCapacidade" tabindex="9">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
						
						
						
					<tr>
					<td><strong>Diâmetro:</strong></td>
					<td><html:select property="idHidrometroDiametro" tabindex="8">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroDiametro"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Marca:</strong></td>
					<td><html:select property="idHidrometroMarca" tabindex="7">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroMarca"
							labelProperty="descricao" property="id" />
					</html:select></td>
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
								<td height="24"  align="left" >
			          	         <input type="button" 
			          		     class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar(0);"/>
					           </td>
							<td align="right">
								<gcom:controleAcessoBotao name="Button" value="Gerar" onclick="validarForm(document.forms[0]);" url="gerarRelatorioQuadroHidrometrosSituacaoAction.do" />
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
