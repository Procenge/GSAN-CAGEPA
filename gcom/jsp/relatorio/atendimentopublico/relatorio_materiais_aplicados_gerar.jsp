<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioMateriaisAplicadosActionForm" />

<script language="JavaScript">

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.GerarRelatorioMateriaisAplicadosActionForm;

    if (tipoConsulta == 'setorComercial') {
      	form.cdSetorComercial.value = codigoRegistro;
    	form.nomeSetorComercial.value = descricaoRegistro;
		form.nomeSetorComercial.style.color = "#000000";
    }
    
    if (tipoConsulta == 'localidade') {
      	form.idLocalidade.value = codigoRegistro;
    	form.nomeLocalidade.value = descricaoRegistro;
		form.nomeLocalidade.style.color = "#000000";
    }
  }

function validarForm(){
	var form = document.forms[0];
	
	if (form.dataExecucaoInicial.value == '') {
		alert('Informe a Data da Execução Inicial.');
		return;
	}
	if (form.dataExecucaoFinal.value == '') {
		alert('Informe a Data da Execução Final.');
		return;
	}
	if (comparaData(form.dataExecucaoInicial.value, ">", form.dataExecucaoFinal.value)) {
		alert('Data de Fim do Contrato tem que ser superior a Data de Início');
		return;
	}
	enviarSelectMultiplo('GerarRelatorioMateriaisAplicadosActionForm','tipoServicoSelecionados');
	
	enviarSelectMultiplo('GerarRelatorioMateriaisAplicadosActionForm','equipeSelecionados');
	enviarSelectMultiplo('GerarRelatorioMateriaisAplicadosActionForm','materialSelecionados');
	form.submit();
}

function limparLocalidade(){
	var form = document.forms[0];
	form.idLocalidade.value = '';
	form.nomeLocalidade.value = '';
	
	limparSetor();
}

function limparSetor(){
	var form = document.forms[0];
	form.cdSetorComercial.value = '';
	form.nomeSetorComercial.value = '';
}

function MoveSelectedDataFromMenu1TO2(form, object, selectedObject){
	var form = document.forms[0];
	
	if (form.tipoServico.value != '-1') {
		MoverDadosSelectMenu1PARAMenu2(form, object, selectedObject);
	}
}

function MoveSelectedDataFromMenu2TO1(form, object, selectedObject){
	var form = document.forms[0];
	
	if (form.tipoServico.value != '-1') {
		MoverDadosSelectMenu2PARAMenu1(form, object, selectedObject);
	}
}

function duplicaValoresDatas(){
	var form = document.forms[0];
	form.dataExecucaoFinal.value = form.dataExecucaoInicial.value;
}
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioMateriaisAplicadosAction.do" name="GerarRelatorioMateriaisAplicadosActionForm"
	type="gcom.gui.relatorio.atendimentopublico.GerarRelatorioMateriaisAplicadosActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/mensagens.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
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
					<td class="parabg">Gerar Relatório de Materiais Aplicados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para gerar o relatório, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>Localidade:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="idLocalidade" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioMateriaisAplicadosAction.do?objetoConsulta=1','idLocalidade','Localidade');"/>
							
						<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=localidade&indicadorUsoTodos=1', 400, 800);limparLocalidade();">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="cdSetorComercial" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioMateriaisAplicadosAction.do?objetoConsulta=2','cdSetorComercial','Setor Comercial');"/>
							
						<a href="javascript:limparSetor();abrirPopup('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=setorComercial', 400, 800);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialEncontrado" scope="request">
							<html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialEncontrado" scope="request">
							<html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparSetor();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
					<tr>
					<td width="120"><strong>Tipo de Servi&ccedil;o:</strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="175">

							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="tipoServico" size="6" multiple="true"
								style="width:190px">

								<html:options collection="colecaoTipoServico"
									labelProperty="descricao" property="id" />
							</html:select></td>

							<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &gt;&gt; "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&gt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&lt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &lt;&lt; "></td>
								</tr>
							</table>
							</td>

							<td>
							<div align="left"><strong>Selecionados</strong></div>

							<html:select property="tipoServicoSelecionados" size="6"
								multiple="true" style="width:190px">

							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td width="120"><strong>Material:</strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="175">

							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="material" size="6" multiple="true"
								style="width:190px">

								<html:options collection="colecaoMaterial"
									labelProperty="descricao" property="id" />
							</html:select></td>

							<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'material', 'materialSelecionados');"
										value=" &gt;&gt; "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'material', 'materialSelecionados');"
										value=" &nbsp;&gt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'material', 'materialSelecionados');"
										value=" &nbsp;&lt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'material', 'materialSelecionados');"
										value=" &lt;&lt; "></td>
								</tr>
							</table>
							</td>

							<td>
							<div align="left"><strong>Selecionados</strong></div>

							<html:select property="materialSelecionados" size="6"
								multiple="true" style="width:190px">

							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td width="120"><strong>Equipe:</strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="175">

							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="equipe" size="6" multiple="true"
								style="width:190px">

								<html:options collection="colecaoEquipe"
									labelProperty="nomeComId" property="id" />
							</html:select></td>

							<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'equipe', 'equipeSelecionados');"
										value=" &gt;&gt; "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioMateriaisAplicadosActionForm', 'equipe', 'equipeSelecionados');"
										value=" &nbsp;&gt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'equipe', 'equipeSelecionados');"
										value=" &nbsp;&lt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioMateriaisAplicadosActionForm', 'equipe', 'equipeSelecionados');"
										value=" &lt;&lt; "></td>
								</tr>
							</table>
							</td>

							<td>
							<div align="left"><strong>Selecionados</strong></div>

							<html:select property="equipeSelecionados" size="6"
								multiple="true" style="width:190px">

							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr> 
					<td><strong>Período de Execução:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"><strong> 
						<html:text property="dataExecucaoInicial" size="11" maxlength="10" onkeyup="mascara_data(this, event);duplicaValoresDatas();" onblur="verificaDataMensagemPersonalizada(this,'Data Execução Inicial Inválida.');duplicaValoresDatas();"/>
						<a href="javascript:abrirCalendarioReplicando('GerarRelatorioMateriaisAplicadosActionForm', 'dataExecucaoInicial','dataExecucaoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="dataExecucaoFinal" size="11" maxlength="10" onkeyup="mascara_data(this, event);" onblur="verificaDataMensagemPersonalizada(this,'Data Execução Inicial Inválida.');"/>
						<a href="javascript:abrirCalendario('GerarRelatorioMateriaisAplicadosActionForm', 'dataExecucaoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</strong>(dd/mm/aaaa)<strong> 
					</strong></span></td>
				</tr>
				
				<tr><td><p>&nbsp;</p></td></tr>
				<tr>
					<td colspan="2">
						<input type="button" name="Button"
							class="bottonRightCol" value="Desfazer" tabindex="7"
							onClick="javascript:window.location.href='/gsan/exibirGerarRelatorioMateriaisAplicadosAction.do?menu=sim'"
							style="width: 80px" />&nbsp; 
						<input type="button" name="Button"
							class="bottonRightCol" value="Cancelar" tabindex="8"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
							style="width: 80px" />
					</td>
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm()" />
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

