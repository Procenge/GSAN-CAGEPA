<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="gcom.atendimentopublico.registroatendimento.EspecificacaoTramite"%>
<%@page import="gcom.util.Pagina,gcom.util.ConstantesSistema"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script>
	function facilitador(objeto) {
		if (objeto.id == "0" || objeto.id == undefined) {
			objeto.id = "1";
			marcarTodos();
		} else {
			objeto.id = "0";
			desmarcarTodos();
		}
	}
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/removerTramiteEspecificacaoAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post"
	onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirmar exclusao?')">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0" >
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif"/></td>
					<td class="parabg">Manter Tr�mite por Especifica��o</td>
					<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif"/></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><strong>Dados tr�mite por especifica��o cadastradas:</strong></font></td>
				</tr>
				<tr>
					<td bgcolor="#000000" height="2" valign="baseline"></td>
				</tr>
				<tr>
					<td style="position: relative">
						<div style="position: absolute; width: 595px; height: 250; overflow: auto;" >
						<table bgcolor="#90c7fc" border="0" style="">
							<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
								<td>
									<div align="center">
										<strong>
											<a href="javascript:facilitador(this);" id="0">Todos</a>
										</strong>
									</div>
								</td>
								<td align="center" width="25%"><font color="#000000"><strong>Especifica��o</strong></font></td>
								<td align="center" width="25%"><font color="#000000"><strong>Localidade</strong></font></td>
								<td align="center" width="25%"><font color="#000000"><strong>Unidade Destino</strong></font></td>
								<td align="center" width="25%"><font color="#000000"><strong>Primeiro Tr�mite?</strong></font></td>
							</tr>
							<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
								export="currentPageNumber=pageNumber;pageOffset"
								maxPageItems="10" items="${sessionScope.totalRegistros}">
								<pg:param name="pg" />
								<pg:param name="q" />
								<%int cont = 0;%>
								<logic:iterate name="colecaoTramiteEspecificacao" id="especificacaoTramite" type="EspecificacaoTramite">
									<pg:item>
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
										<%} else {%>
										<tr bgcolor="#cbe5fe">
										<%}%>
											<td>
												<div align="center">
													<input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="especificacaoTramite" property="id"/>">
												</div>
											</td>
											<td>
												<div align="center">
												<html:link href="/gsan/exibirAtualizarTramiteEspecificacaoAction.do" paramId="idEspecificacaoTramite" paramProperty="id" paramName="especificacaoTramite">
														<bean:write name="especificacaoTramite" property="solicitacaoTipoEspecificacao.descricao" />
													</html:link>
												</div>
											</td>
											<td>
												<div align="center">
													<logic:notEmpty name="especificacaoTramite" property="localidade">
														<bean:write name="especificacaoTramite" property="localidade.descricao" />
													</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
													<logic:notEmpty name="especificacaoTramite" property="unidadeOrganizacionalDestino">
														<bean:write name="especificacaoTramite" property="unidadeOrganizacionalDestino.sigla" />
													</logic:notEmpty>
												</div>
											</td>
											
												<td>
												<div align="center">
													
														
														    <logic:equal name="especificacaoTramite" property="indicadorPrimeiroTramite" value="1">
													 		 SIM
												  			</logic:equal>
												  			<logic:equal name="especificacaoTramite" property="indicadorPrimeiroTramite" value="2">	
												             N�O
												  			</logic:equal>
													
														
														
												
												
												</div>
											</td>
											
											
										</tr>
									</pg:item>
								</logic:iterate>
						</table>
						</div>
						<p style="height: 255">&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="center"><strong><%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong></td>
							</tr>
							</pg:pager>
							<tr>
								<td>
									<font color="#FF0000">
										<html:submit property="buttonRemover" styleClass="bottonRightCol" value="Remover" />
									</font>
									<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro"	onclick="window.location.href='<html:rewrite page="/exibirFiltrarTramiteEspecificacaoAction.do?limpar=N"/>'" align="left" style="width: 80px;">
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