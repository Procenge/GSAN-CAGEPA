<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<%@ page import="gcom.micromedicao.bean.OrdemServicoManutencaoHidrometroHelper"%>
<%@ page import="gcom.micromedicao.hidrometro.HidrometroHistoricoAfericao"%>

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
	
<script language="JavaScript">
	
	function consultarOs(idOs){
		
		var url = 'exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+idOs;
		abrirPopup(url);
	}
</script>

</head>


<body leftmargin="5" topmargin="5">

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


		<td valign="top" class="centercoltext">

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
				<td class="parabg">Consultar Hist&oacute;rico de
				Instala&ccedil;&atilde;o de Hidr&ocirc;metro</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		<table width="100%" border="0">
			<tr>
				<td height="31">
				<table width="100%" bgcolor="#90c7fc">
					<!--header da tabela interna -->
					<tr>
						<td bgcolor="#79bbfd" colspan="8">
						<div align="center" class="style11"><strong>Dados do
						Hidr&ocirc;metro</strong></div>
						</td>
					</tr>
					<tr bgcolor="#90c7fc">
						<td width="13%">
						<div align="center" class="style9"><strong>N&uacute;mero do
						Hidr&ocirc;metro</strong></div>
						</td>
						<td width="12%">
						<div align="center" class="style9"><strong>Data
						Aquisi&ccedil;&atilde;o</strong></div>
						</td>
						<td width="10%">
						<div align="center" class="style9"><strong> Ano Fabr.</strong></div>
						</td>
						<td width="11%">
						<div align="center" class="style9"><strong>Dt.Ult.Afe.</strong></div>
						</td>
						<td width="11%">
						<div align="center"><strong>Data Baixa</strong></div>
						</td>
						<td width="15%">
						<div align="center"><strong>Mot.Baixa</strong></div>
						</td>
						<td width="12%">
						<div align="center" class="style9"><strong>Situa&ccedil;&atilde;o
						Hidr&ocirc;metro</strong></div>
						</td>
						<td width="16%">
						<div align="center" class="style9"><strong>Leitura <br>Acumulada </strong></div>
						</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td height="17">
						<div align="center">${requestScope.hidrometro.numero}&nbsp;<br>
						</div>
						</td>
						<td>
						<div align="center"><bean:write name="hidrometro"
							property="dataAquisicao" formatKey="date.format" /></div>
						</td>
						<td>
						<div align="center">${requestScope.hidrometro.anoFabricacao}&nbsp;</div>
						</td>
						<td>
						<div align="center"><bean:write name="hidrometro"
							property="dataUltimaRevisao" formatKey="date.format" /></div>
						</td>
						<td>
						<div align="center"><bean:write name="hidrometro"
							property="dataBaixa" formatKey="date.format" /></div>
						</td>
						<td>
						<div align="center">${requestScope.hidrometro.hidrometroMotivoBaixa.descricao}&nbsp;</div>
						</td>
						<td>
						<div align="center">${requestScope.hidrometro.hidrometroSituacao.descricao}&nbsp;</div>
						</td>
						<td>
						<div align="center">${requestScope.hidrometro.numeroLeituraAcumulada}&nbsp;</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="22" class="style1">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td height="37">
						<table width="100%" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc">
								<td width="17%">
								<div align="center" class="style9"><strong>Classe Metrol.</strong></div>
								</td>
								<td width="7%">
								<div align="center" class="style9"><strong>Marca</strong></div>
								</td>
								<td width="18%">
								<div align="center" class="style9"><strong> Di&acirc;metro</strong></div>
								</td>
								<td width="16%">
								<div align="center" class="style9"><strong>Num.Dig.Leit.</strong></div>
								</td>
								<td width="21%">
								<div align="center"><strong>Capacidade</strong></div>
								</td>
								<td width="21%">
								<div align="center"><strong>Tipo</strong></div>
								<div align="center" class="style9"></div>
								</td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td height="17">
								<div align="center">${requestScope.hidrometro.hidrometroClasseMetrologica.descricao}&nbsp;<br>
								</div>
								</td>
								<td>
								<div align="center">${requestScope.hidrometro.hidrometroMarca.descricaoAbreviada}&nbsp;</div>
								</td>
								<td>
								<div align="center">${requestScope.hidrometro.hidrometroDiametro.descricao}&nbsp;</div>
								</td>
								<td>
								<div align="center">${requestScope.hidrometro.numeroDigitosLeitura}&nbsp;</div>
								</td>
								<td>
								<div align="center">${requestScope.hidrometro.hidrometroCapacidade.descricao}&nbsp;</div>
								</td>
								<td>
								<div align="center">${requestScope.hidrometro.hidrometroTipo.descricao}&nbsp;</div>
								<div align="center"></div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<div style="width: 100%; height: 100%; overflow: auto;"></div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="22" class="style1">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td height="37">
						<table width="100%" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc">
								<td width="40%">
								<div align="center" class="style9"><strong>Número da Nota Fiscal.</strong></div>
								</td>
								<td width="60%">
								<div align="left" class="style9"><strong>Empresa Responsável pela Última Aferição</strong></div>
								</td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td>
									<div align="center">
										<logic:notEmpty scope="request" name="hidrometro" property="numeroNotaFiscal">
											${requestScope.hidrometro.numeroNotaFiscal}
										</logic:notEmpty>
										&nbsp;
									</div>
								</td>
								<td>
									<div align="left">
										<logic:notEmpty scope="request" name="hidrometro" property="empresaUltimaAfericao">
											${requestScope.hidrometro.empresaUltimaAfericao.descricao}
										</logic:notEmpty>
										&nbsp;
									</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<div style="width: 100%; height: 100%; overflow: auto;"></div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<c:if test="${!empty requestScope.hidrometrosInstalacaoHistorico}">
			<table width="100%" border="0">
				<tr>
					<td>
					<table width="100%" bgcolor="#90c7fc">
						<tr>
							<td bgcolor="#79bbfd" colspan="8">
							<div align="center" ><strong>Hist&oacute;rico de
							Instala&ccedil;&atilde;o de Hidr&ocirc;metro</strong></div>
							</td>
						</tr>
						<tr bgcolor="#90c7fc">
							<td width="13%">
							<div align="center"><strong>Matr&iacute;cula do Im&oacute;vel</strong></div>
							</td>
							<td width="19%">
							<div align="center"><strong>Tipo de Medi&ccedil;&atilde;o</strong></div>
							</td>
							<td width="11%">
							<div align="center"><strong>Data de Instala&ccedil;&atilde;o</strong></div>
							</td>
							<td width="10%">
							<div align="center"><strong>Local de Instala&ccedil;&atilde;o</strong></div>
							</td>
							<td width="12%">
							<div align="center"><strong>Tipo de Prote&ccedil;&atilde;o</strong></div>
							</td>
							<td width="10%">
							<div align="center"><strong>Data de Retirada</strong></div>
							</td>
							<td width="16%">
							<div align="center"><strong>N&uacute;mero do Selo </strong></div>
							</td>
							<td width= bgcolor="#90c7fc">
							<div align="center"><strong>Cavalete</strong></div>
							</td>
						</tr>
						<%String cor = "#cbe5fe";%>
						<c:forEach items="${requestScope.hidrometrosInstalacaoHistorico}"
							var="hidrometroInstalacaoHistorico">

							<%    if (cor.equalsIgnoreCase("#cbe5fe")){   
                                cor = "#FFFFFF";%>
                                <tr bgcolor="#FFFFFF" height="18">   
                            <%} else{   
                                cor = "#cbe5fe";%>
                                <tr bgcolor="#cbe5fe" height="18">       
                            <%}%>
								<td>
								<div align="center"><a
									href="consultarHistoricoInstalacaoHidrometroAction.do?codigoImovel=${hidrometroInstalacaoHistorico.imovel.id}${hidrometroInstalacaoHistorico.ligacaoAgua.id}">
								${hidrometroInstalacaoHistorico.imovel.id}${hidrometroInstalacaoHistorico.ligacaoAgua.id}</a></div>
								</td>
								<td>
								<div align="center">${hidrometroInstalacaoHistorico.medicaoTipo.descricao}&nbsp;</div>
								</td>
								<td>
								<div align="center"><bean:write
									name="hidrometroInstalacaoHistorico" property="dataInstalacao"
									formatKey="date.format" />&nbsp;</div>
								</td>
								<td>
								<div align="center">${hidrometroInstalacaoHistorico.hidrometroLocalInstalacao.descricaoAbreviada}&nbsp;</div>
								</td>
								<td>
								<div align="center">${hidrometroInstalacaoHistorico.hidrometroProtecao.descricao}&nbsp;</div>
								</td>
								<td>
								<div align="center"><bean:write
									name="hidrometroInstalacaoHistorico" property="dataRetirada"
									formatKey="date.format" />&nbsp;</div>
								</td>
								<td>
								<div align="center">${hidrometroInstalacaoHistorico.numeroSelo}&nbsp;</div>
								</td>
								<td>
								<div align="center"><c:if
									test="${hidrometroInstalacaoHistorico.indicadorExistenciaCavalete eq 1}">
						Sim	
					</c:if> <c:if
									test="${hidrometroInstalacaoHistorico.indicadorExistenciaCavalete eq 2}">
						Não
					</c:if></div>
								</td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
			</table>
		</c:if>
		
		<logic:present name="colecaoOrdemServicoManutencaoHidrometroHelper">
			<table width="100%" border="0">
				<tr>
					<td>
						<table width="100%" bgcolor="#90c7fc">
							<tr>
								<td bgcolor="#79bbfd" colspan="4">
								<div align="center" ><strong>Dados das Ordens de Serviço</strong></div>
								</td>
							</tr>
							<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
								<td>
									<div align="center" class="style9"><strong>Ordem de Serviço</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Tipo de Serviço</strong></div>
								</td>
								<td>
									<div align="center" class="style9"><strong>Data da Geração</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Situação</strong></div>
								</td>
							</tr>
							
							<%int cont3 = 0;%>
							<logic:iterate name="colecaoOrdemServicoManutencaoHidrometroHelper" id="ordemServicoManutencaoHidrometroHelper">
			
								<%cont3 = cont3 + 1;
									if (cont3 % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
									<%} else {
			
									%>
								<tr bgcolor="#FFFFFF">
									<%}%>
									
									<td>
										<div align="center">
											<a  title="Consultar Dados da Ordem de Serviço" href="javascript:consultarOs('<bean:write name="ordemServicoManutencaoHidrometroHelper" property="numeroOS"/>');">
												<bean:write name="ordemServicoManutencaoHidrometroHelper" property="numeroOS" />
											</a>
										</div>
									</td>
									
									<td>
										<div align="left">
											<bean:write name="ordemServicoManutencaoHidrometroHelper" property="descricaoTipoServico"/>
										</div>
									</td>
			
									<td>
										<div align="center"><bean:write name="ordemServicoManutencaoHidrometroHelper"
										property="dataGeracaoOS"/></div>
									</td>
									
									<td>
										<div align="left"><bean:write name="ordemServicoManutencaoHidrometroHelper"
										property="descricaoSituacaoOS" /></div>
									</td>
			
								</tr>
								
							</logic:iterate>
							
						</table>
					</td>
				</tr>
			</table>
		</logic:present>
		
		<logic:present name="colecaoHidrometroHistoricoAfericao">
			<table width="100%" border="0">
				<tr>
					<td>
						<table width="100%" bgcolor="#90c7fc">
							<tr>
								<td bgcolor="#79bbfd" colspan="6">
								<div align="center" ><strong>Histórico de Aferições</strong></div>
								</td>
							</tr>
							<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
								<td>
									<div align="center" class="style9"><strong>Ordem de Serviço</strong></div>
								</td>
								<td>
									<div align="center" class="style9"><strong>Data da Aferição</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Condição do Hidrômetro</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Resultado</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Cliente Acompanhou</strong></div>
								</td>
								<td>
									<div align="left" class="style9"><strong>Funcionário</strong></div>
								</td>
							</tr>
							
							<%int cont3 = 0;%>
							<logic:iterate name="colecaoHidrometroHistoricoAfericao" id="hidrometroHistoricoAfericao">
			
								<%cont3 = cont3 + 1;
									if (cont3 % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
									<%} else {
			
									%>
								<tr bgcolor="#FFFFFF">
									<%}%>
									
									<td>
										<div align="center">
											<a  title="Consultar Dados da Ordem de Serviço" href="javascript:consultarOs('<bean:write name="hidrometroHistoricoAfericao" property="ordemServico.id"/>');">
												<bean:write name="hidrometroHistoricoAfericao" property="ordemServico.id" />
											</a>
										</div>
									</td>
									
									<td>
										<div align="center">
											<bean:write name="hidrometroHistoricoAfericao" property="dataAfericao" formatKey="date.format"/>
										</div>
									</td>
			
									<td>
										<div align="left">
											<bean:write name="hidrometroHistoricoAfericao" property="hidrometroCondicao.descricao"/>
										</div>
									</td>
									
									<td>
										<div align="left">
											<bean:write name="hidrometroHistoricoAfericao" property="indicadorResultadoFormatado"/>
										</div>
									</td>
									
									<td>
										<div align="left">
											<bean:write name="hidrometroHistoricoAfericao" property="indicadorClienteAcompanhouFormatado"/>
										</div>
									</td>
									
									<td>
										<div align="left">
											<bean:write name="hidrometroHistoricoAfericao" property="funcionario.nome"/>
										</div>
									</td>
			
								</tr>
								
							</logic:iterate>
							
						</table>
					</td>
				</tr>
			</table>
		</logic:present>

		<table width="100%" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td align="left"><input class="bottonRightCol" type="button"
					name="Voltar" value="Voltar Consulta"
					onclick="javascript:window.location.href = 'exibirConsultarHistoricoInstalacaoHidrometroInformarAction.do?menu=sim'" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
</table>


<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:html>
