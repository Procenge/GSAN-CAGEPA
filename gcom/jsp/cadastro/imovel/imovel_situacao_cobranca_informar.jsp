<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InformarImovelSituacaoCobrancaActionForm" />

<script language="JavaScript">

function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}

function marcarTodos(){

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox" && elemento.name == 'idRemocao'){
			elemento.checked = true;
		}
	}
}

function desmarcarTodos() {

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox" && elemento.name == 'idRemocao'){
			elemento.checked = false;
		}
	}
}

function verificaChecked() {

	 var form = document.forms[0];
	 var i;
	 var checked = false;

	 if (form.idRemocao != undefined) {

		if (form.idRemocao.length != undefined) {

		 	for(i = 0; i < form.idRemocao.length; i++) {
			 	
				if (!form.idRemocao[i].disabled) {

					if (form.idRemocao[i].checked) {

						checked = true;
					}
				}
		 	}
		}else{

			if (!form.idRemocao.disabled) {

				if (form.idRemocao.checked) {

					checked = true;
				}
			}
		}
	 } else {

		alert('Não há Situações de Cobrança a serem retiradas');
	 }
	 
	 if (checked == false) {

		 alert('É necessário selecionar pelo menos uma situação de cobrança para retirada');
		 return false;
	 } 
	 
	 return true;
}

function removeCheckDisabled() {

	var form = document.forms[0];
	var i;

	if (form.idRemocao != undefined) {

		if (form.idRemocao.length != undefined) {

			for(i = 0; i < form.idRemocao.length; i++) {

				if (form.idRemocao[i].disabled) {

					form.idRemocao[i].checked = false;
				}
		 	}
		}
	}	
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovel.value = codigoRegistro;
	  form.action = 'exibirInformarImovelSituacaoCobrancaAction.do?objetoConsulta=1';      
	  form.submit();
    }
 }
 
 function validaForm() {
 	var form = document.forms[0];
 	if (validateInformarImovelSituacaoCobrancaActionForm(form)){
 		form.action = 'exibirInserirImovelSituacaoCobrancaAction.do?idImovel='+form.idImovel.value;
 		form.submit();
 	}
 }
 function retirar() {
 	var form = document.forms[0];
 	if (verificaChecked()) {
	 	if (confirm('Deseja retirar a situação de cobrança do Imóvel?')){
	 		if (validateInformarImovelSituacaoCobrancaActionForm(form)){
	 			form.action = 'removerImovelSituacaoCobrancaAction.do?idImovel='+form.idImovel.value;
	 			form.submit();
	 		}
	 	}
 	}
 }

</script>
</head>
<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('idImovel');removeCheckDisabled();">

<html:form action="/exibirInserirImovelSituacaoCobrancaAction.do"
	name="InformarImovelSituacaoCobrancaActionForm"
	type="gcom.gui.cadastro.imovel.InformarImovelSituacaoCobrancaActionForm"
	method="post"
	onsubmit="return validateInformarImovelSituacaoCobrancaActionForm(this);">

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
			<td width="615" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
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
					<td class="parabg">Informar Situação de Cobrança</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table border="0" width="100%">
				<tr>
					<td colspan="5">
					<table align="center" bgcolor="#99ccff" border="0" width="100%">
						<tr>
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td align="center" width="100%">
							<table border="0" width="100%">
								<tr>
									<td bordercolor="#000000" width="160"><strong>Matrícula do Imóvel:<font
										color="#ff0000">*</font></strong></td>
									<td colspan="3" bordercolor="#000000" width="414"><html:text
										property="idImovel" maxlength="9" size="10" tabindex="1"
										onkeypress="javascript:validaEnterComMensagem(event, 'exibirInformarImovelSituacaoCobrancaAction.do?objetoConsulta=1', 'idImovel','Imóvel');" />
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do');">
									<img src="imagens/pesquisa.gif" title="Pesquisar Imovél"
										border="0" height="21" width="23"></a> <logic:present
										name="inexistente" scope="request">
										<html:text property="descricaoImovel" maxlength="25" size="25"
											value="IMÓVEL INEXISTENTE" readonly="readonly"
											style="background-color:#EFEFEF; border:0; color: #ff0000;" />
									</logic:present> <logic:notPresent name="inexistente"
										scope="request">
										<html:text property="descricaoImovel" maxlength="25" size="25"
											value="${requestScope.matriculaImovel}" readonly="readonly"
											style="border: 0pt none ; background-color: rgb(239, 239, 239); color: rgb(0, 0, 0);" />
									</logic:notPresent> <a
										href="exibirInformarImovelSituacaoCobrancaAction.do?menu=sim"><img
										src="imagens/limparcampo.gif" border="0" height="21"
										width="23"></a></td>
								</tr>
								<tr>
								<td height="10">
								<div class="style9"><strong>Situação de Água:</strong></div>
								</td>
								<td><input name="ligacaoAgua" type="text" value="${requestScope.imovel.ligacaoAguaSituacao.descricao}"
									style="background-color:#EFEFEF; border:0; font-size:9px"
									value="" size="15" maxlength="15" readonly="readonly"></td>

								<td width="146"><strong>Situação de Esgoto:</strong></td>
								<td width="123"><input name="ligacaoEsgoto" type="text" value="${requestScope.imovel.ligacaoEsgotoSituacao.descricao}"
									style="background-color:#EFEFEF; border:0; font-size:9px"
									value="" size="30" maxlength="30" readonly="readonly"></td>
							</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="5">
					<table align="center" bgcolor="#99ccff" border="0" width="100%">
						<tr>
							<td align="center">
							<div class="style9"><strong>Endereço </strong></div>

							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<logic:present name="endereco" scope="request">
								<td align="center" bgcolor="#ffffff">${requestScope.endereco}</td>
							</logic:present>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="5">
					<table border="0" width="100%">
						<tr>
							<td colspan="2">
							<table align="center" bgcolor="#90c7fc" border="0" width="100%">
								<tr bordercolor="#79bbfd">
									<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Situações
									de Cobrança do Imóvel </strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td align="center" bgcolor="#90c7fc" width="4%">
									<div class="style9"><strong><font style="font-size: 9px;"
										color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
									<a onclick="this.focus();" id="0" href="javascript:facilitador(this);">Todos</a></font></strong></div>
									</td>
									<td align="center" bgcolor="#90c7fc" width="32%">
									<div class="style9"><font style="font-size: 9px;"
										color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Situação</strong></font></div>
									</td>
									<td align="center" bgcolor="#90c7fc" width="17%">
									<div class="style9"><font style="font-size: 9px;"
										color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Data Implantação</strong> </font></div>
									</td>
									<td align="center" bgcolor="#90c7fc" width="13%">
									<div class="style9"><font style="font-size: 9px;"
										color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Data Retirada </strong></font></div>
									</td>
									<td align="center" bgcolor="#90c7fc">
									<div class="style9"><font style="font-size: 9px;"
										color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Cliente Alvo </strong></font></div>
									</td>
								</tr>

								<%String cor = "#FFFFFF";%>
								<logic:present name="situacoes" scope="request">
									<logic:iterate name="situacoes" id="sit">
										<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">

											<%} else {
				cor = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td bordercolor="#90c7fc" align="left">
												<logic:notPresent name="sit" property="dataRetiradaCobranca">
													<logic:equal name="sit" property="cobrancaSituacao.indicadorBloqueioRetirada" value="1">
														<html:checkbox property="idRemocaoNaoHabilitado" value="0" disabled="true"/>
													</logic:equal>
													<logic:equal name="sit" property="cobrancaSituacao.indicadorBloqueioRetirada" value="2">
														<html:checkbox property="idRemocao" value="${sit.id}"/>
													</logic:equal>													
												</logic:notPresent>
											</td>
											<td bordercolor="#90c7fc" align="left">
											${sit.cobrancaSituacao.descricao}</td>
											<td align="center"><bean:write name="sit"
												property="dataImplantacaoCobranca" formatKey="date.format" />
											</td>
											<td align="center"><bean:write name="sit"
												property="dataRetiradaCobranca" formatKey="date.format" /></td>
											<td align="left">${sit.cliente.nome}</td>
										</tr>
									</logic:iterate>
								</logic:present>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td align="left" width="51%">&nbsp;
								<input name="Submit22" class="bottonRightCol" value="Desfazer" type="button" onclick="window.location.href='/gsan/exibirInformarImovelSituacaoCobrancaAction.do?menu=sim';">
								<input name="Submit23" class="bottonRightCol" value="Cancelar" type="button" onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="left" width="49%">
								<div align="right">
								
									<input name="adicionar" class="bottonRightCol" value="Inserir" onclick="javascript:validaForm();" type="button">
									
									<logic:present name="escondeRetirar" scope="request">
										<input name="adicionar" class="bottonRightCol" value="Retirar" onclick="retirar();" disabled="disabled" type="button">
									</logic:present>
									<logic:notPresent name="escondeRetirar" scope="request">
										<input name="adicionar" class="bottonRightCol" value="Retirar" onclick="retirar();" type="button">
									</logic:notPresent>
								</div>
							</td>
						</tr>
					</table>
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
