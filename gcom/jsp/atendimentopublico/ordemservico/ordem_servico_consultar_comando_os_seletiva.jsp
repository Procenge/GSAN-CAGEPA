<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper"%>
<%@ page import="gcom.gui.atendimentopublico.ordemservico.DadosOrdemServicoHelper"%>
<%@page import="gcom.cadastro.localidade.SetorComercial"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" 	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	
<html:javascript staticJavascript="false"  formName="ConsultarComandoOsSeletivaActionForm"/>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">

function extendeTabela(tabela,display){

		if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
		}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
		}
}

function expandeTelaDados(){
	
		var tabela='Dados';
		var display=false;
		
		eval('layerHide'+tabela).style.display = 'none';
		eval('layerShow'+tabela).style.display = 'block';
}

function emitirRelatorioDadosComando(){
	
	document.forms[0].action = "/gsan/gerarRelatorioDadosComandoOsSeletivaAction.do";
	document.forms[0].submit();
}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');expandeTelaDados();">
<html:form action="/exibirConsultarComandoOsSeletivaoAction" method="post" name="ConsultarComandoOsSeletivaActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ConsultarComandoOsSeletivaActionForm">
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
			
			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Comando OS Seletiva</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			
		<p>&nbsp;</p>
			<table width="100%" height="100%" border="0">
				<%String cor = "#FFFFFF";%>
				<tr>
				<td colspan="1">
					<td align="left"></td>
					<table width="100%" align="center"  border="0">
					<tr>
				<tr>
				<td colspan="4">
					<div id="layerHideDados" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Dados',true);" /><b>Dados do Filtro para Geração do Comando
							</b></a></span></td>
						</tr>
					</table>
					</div>
					<div id="layerShowDados" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Dados',false);" /><b>Dados do Filtro para Geração do Comando
							</b></a></span></td>
						</tr>
							<tr bgcolor="#cbe5fe">
								<td>
							  	   <table width="100%" align="center">
							  	<tr>
							  		<tr>
							<td><strong>Título:&nbsp;</font></strong></td>
								<td align="left">
								<html:textarea property="titulo" cols="53" rows="1" disabled="true"
								 onkeyup="limitTextArea(document.forms[0].titulo, 100, document.getElementById('utilizado'), document.getElementById('limite'));"/>
								 <br><strong><span id="utilizado">0</span>/<span id="limite">100</span></strong>
								</td>					
						</tr>
						<tr>
							<td><strong>Empresa:</strong></td>
								<td align="left">
									<html:text property="idFirma" style="width:60px; height:25px" disabled="true"></html:text>
									<html:text property="descricaoFirma" style="width:385px; height:25px" disabled="true"></html:text>
						    	</td>
							</tr>  
						<tr>
							<td><strong>Serviço Tipo:</strong></td>
								<td align="left">
									<html:text property="idServicoTipo" style="width:60px; height:25px" disabled="true"></html:text>
						    		<html:text property="descricaoServicoTipo" style="width:385px; height:25px" disabled="true"></html:text>
						    	</td>
							</tr>
							
							<tr>
							<td><strong>Quantidade Máxima:</strong></td>
								<td align="left">
									<html:text property="quantidadeMaximaOrdens" style="width:60px; height:25px" disabled="true"></html:text>
						    	</td>
							</tr>		
					<tr>
						<td><strong>Imóveis:</strong></td>
								<td align="left">
										<html:select property="imovel" multiple="true"
												style="width: 220px; height: 100px" disabled="true" >
													<logic:present name="colecaoImovel">
															<html:options  name="colecaoImovel" />
													</logic:present>
										</html:select>
									</td>
								</tr>		
					<tr>
						<td><strong>Elo:</strong></td>
								<td align="left">
									<html:text property="idElo" style="width:60px; height:25px" disabled="true"></html:text>
									<html:text property="descricaoElo" style="width:380px; height:25px" disabled="true"></html:text>
						    	</td>
						</tr>
						<tr>
						<td><strong>Grupo Faturamento:</strong></td>
								<td align="left">
									<html:text property="idFaturamentoGrupo" style="width:60px; height:25px" disabled="true"></html:text>
									<html:text property="descricaoFaturamentoGrupo" style="width:380px; height:25px" disabled="true"></html:text>	
						    	</td>
						</tr>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<tr>
		<td colspan="2">
				<div id="layerHideLocalizacao" style="display: block">
				<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Localizacao',true);" /><b>Dados de Localização Geográfica
							</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLocalizacao" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Localizacao',false);" /><b>Dados de Localização Geográfica
							</b></a></span></td>
						</tr>
							<tr bgcolor="#cbe5fe">
								<td>
							  	   <table width="100%" align="center">
							  	<tr>
								<td><strong>Gerencia Regional:</strong></td>
								<td align="left">
									<html:text property="nomeGerenciaRegional" style="width:220px; height:25px" disabled="true"></html:text>	
						    	</td>
								</tr>
								
								<tr>
								<td><strong>Localidade:</strong></td>
								<td align="left">
									<html:text property="nomeLocalidade" style="width:220px; height:25px" disabled="true"></html:text>	
						    	</td>
								</tr>
								<tr>
								<td><strong>Setor:</strong></td>
									<td align="left">
											<html:select property="setor" multiple="true"
												style="width: 220px; height: 100px" disabled="true"  >
												<logic:present name="relacaoSetorComercial">
														<html:options collection="relacaoSetorComercial" property="id" labelProperty="descricaoComCodigo" />
												</logic:present>
											</html:select>
						    		</td>
						    		<td><strong>Quadra:</strong></td>
									<td align="left">
										<html:select property="quadra" multiple="true"
												style="width: 220px; height: 100px" disabled="true">
												<logic:present name="relacaoQuadra">		
													<html:options collection="relacaoQuadra" property="id" labelProperty="numeroQuadra" />
												</logic:present>
										</html:select>
										
						    		</td>
								</tr>
								<tr>
								<td><strong>Rota:</strong></td>
									<td align="left">
										
											<html:select property="rota" multiple="true"
												style="width: 220px; height: 100px" disabled="true" >
													<logic:present name="relacaoRota">
													<html:options collection="relacaoRota" property="id" labelProperty="codigo" />
													</logic:present>
											</html:select>			
										
						    		</td>
						    		<td><strong>Lote:</strong></td>
						    		<td align="left">
										<html:select property="lote" multiple="true"
												style="width: 220px; height: 100px" disabled="true" >
													<logic:present name="relacaoLote">
															<html:options  name="relacaoLote" />
													</logic:present>
										</html:select>
						    		</td>
								</tr>
								<tr>
								<td><strong>Bairro:</strong></td>
									<td align="left">
										<html:select property="bairro" multiple="true"
												style="width: 220px; height: 100px" disabled="true"  >
													<logic:present name="relacaoBairro">				
													<html:options collection="relacaoBairro" property="id"	labelProperty="nome" />
													</logic:present>
										</html:select>
										
						    		</td>
						    		<td><strong>Logradouro:</strong></td>
									<td align="left">
										<html:select property="logradouro" multiple="true"
												style="width: 220px; height: 100px" disabled="true" >
													<logic:present name="relacaoLogradouro">
														<html:options collection="relacaoLogradouro" property="id"	labelProperty="nome" />
													</logic:present>
										</html:select>
										
						    		</td>
								</tr>
						     </table>
							</td>
						</tr>	
						</tr>
					</table>
					</div>	
				</td>
			</tr>
		    <tr>
			<td colspan="4">
					<div id="layerHideImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Imovel',true);" /><b>Características do Imóvel
							</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Imovel',false);" /><b>Características do Imóvel
							</b></a></span></td>
						</tr>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td>
							  	   <table width="100%" align="center">
							  	<tr>
				<td>
				<table cellpadding="0" cellspacing="2">
				<tr>
					<td>
					<strong>Pefil Imóvel:</strong>
					</td>
					<td>
					<strong>Categoria:</strong>
					</td>
					<td>
					<strong>Subcategoria:</strong>
					</td>
				</tr>
				<tr>
					<td align="left">
								<logic:present name="relacaoPerfilImovel">
								<html:select property="perfilImovel" multiple="true"
									style="width: 190px; height: 100px"  disabled="true"  >
									<html:options collection="relacaoPerfilImovel" property="id" labelProperty="descricao"/>
								 </html:select>
								 </logic:present>
					</td>
					<td align="left">
						<logic:present name="relacaoCategoria">
								<html:select property="categoria" multiple="true"
									style="width: 190px; height: 100px"  disabled="true">
									<html:options collection="relacaoCategoria" property="id" labelProperty="descricao"/>
								 </html:select>
								  </logic:present>
					</td>
					<td align="left">
						<logic:present name="relacaoSubCategoria">
								<html:select property="subCategoria" multiple="true"
									style="width: 190px; height: 100px"  disabled="true">
									<html:options collection="relacaoSubCategoria" property="id" labelProperty="descricao"/>
								 </html:select>
							</logic:present>
					</td>
				</tr>
				</table>
			</td>	<tr><td colspan="2"><hr></td></tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td ><strong>Situação da Ligação de Água:</strong></td>
								<td align="left">
								<logic:present name="relacaoLigacaoAguaSituacao">
									<html:select property="ligacaoAguaSituacao" multiple="true"
										style="width: 190px;"  disabled="true">
										<html:options collection="relacaoLigacaoAguaSituacao" property="id" labelProperty="descricao"/>
								 	</html:select>
									</logic:present>
								</td>

								<td>&nbsp;</td>

								<td><strong>Situação da Ligação de Esgoto:</strong></td>
								<td align="left">
								<logic:present name="relacaoLigacaoEsgotoSituacao">
									<html:select property="ligacaoEsgotoSituacao" multiple="true"
										style="width: 190px;"  disabled="true" >
										<html:options collection="relacaoLigacaoEsgotoSituacao" property="id" labelProperty="descricao"/>
								 	</html:select>
								 </logic:present>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Intervalo de Quantidade de Economias:</strong></td>
								<td align="left">
								<html:text property="intervaloQuantidadeEconomiasInicial"
									size="7" maxlength="4" disabled="true"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeEconomiasFinal" size="7" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Intervalo de Quantidade de Documentos:</strong></td>
								<td align="left"><html:text property="intervaloQuantidadeDocumentosInicial"
									size="7" maxlength="4" disabled="true"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeDocumentosFinal" size="7" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Intervalo de N&uacute;mero de Moradores:</strong></td>
								<td align="left"><html:text property="intervaloNumeroMoradoresInicial"
									size="7" maxlength="4" disabled="true" ></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroMoradoresFinal" size="7" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Intervalo de Pontos de Utilização:</strong></td>
								<td align="left"><html:text property="intervaloNumeroPontosUtilizacaoInicial"
									size="7" maxlength="4" disabled="true"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroPontosUtilizacaoFinal" size="7" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Intervalo de &Aacute;rea Construida:</strong></td>
								<td align="left">
									<html:text property="intervaloAreaConstruidaInicial" size="7"
										maxlength="10" disabled="true"
										style="text-align:right;" disabled="true">
									</html:text>
								</td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left">
									<html:text property="intervaloAreaConstruidaFinal" size="7"
										maxlength="10" disabled="true"
										style="text-align:right;">
									</html:text>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>&Aacute;rea Construida:</strong></td>
								<td align="left">
									<html:text property="areaContruidaInicial" size="7"
										maxlength="10" disabled="true"
										style="text-align:right;">
									</html:text>
								</td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left">
									<html:text property="areaConstruidadeFinal" size="7"
										maxlength="10" disabled="true"
										style="text-align:right;">
									</html:text>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				
				<tr>
					<td align="left">
						<table border="0">
							<tr>
							<td width="150"><strong>Im&oacute;vel Condom&iacute;nio:</strong></td>
								<td ><html:radio value="1" property="imovelCondominio" disabled="true"></html:radio>&nbsp;Sim</td>
								<td align="left"><html:radio value="2" property="imovelCondominio" disabled="true"></html:radio>&nbsp;N&atilde;o</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr><td colspan="2"><hr></td></tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Consumo por Economia:</strong></td>
								<td align="left">
										<html:text property="consumoPorEconomia" size="8" maxlength="6" disabled="true"></html:text>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Faixa de Consumo:</strong></td>
								<td align="left"><html:text property="intervaloNumeroConsumoMesInicial"
									size="5" maxlength="4" disabled="true"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroConsumoMesFinal" size="5" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td colspan="2"><strong>Consumo Médio do Imóvel:</strong></td>
								<td align="left">
											<html:select property="intervaloNumeroConsumoMedio" multiple="true"
												style="width: 190px;"  disabled="true">
											<logic:present name="relacaoConsumoMedio">
												<html:options collection="relacaoConsumoMedio" property="id" labelProperty="descricaoConsumoFaixa"/>
								 			</logic:present>
								 		</html:select>									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td ><strong>Intervalo de Qtd de Débito:</strong></td>
								<td align="left"><html:text property="intervaloQuantidadeContasVencidasInicial"
									size="5" maxlength="4" disabled="true"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeContasVencidasFinal" size="5" maxlength="4" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="150"><strong>Valor do Débito:</strong></td>
								<td align="left"><html:text property="valorTotalDebitoVencido" disabled="true"
									size="14" maxlength="14" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
					  </tr>
					</table>
					</div>
				</tr>
				</table>
			</tr>
			<tr>
			<td colspan="4">
					<div id="layerHideHidrometro" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Hidrometro',true);" /><b>Hidrômetro
							</b></a></span></td>
						</tr>
					</table>
					</div>
					<div id="layerShowHidrometro" style="display: none">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('Hidrometro',false);" /><b>Hidrômetro
								</b></a></span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
									<td>
							  	  	 <table width="100%" align="center">
							  			<tr>
											
											<tr>
				<tr>
					<td><strong>Marca:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="marca" style="width: 230px;" multiple="true" disabled="true" size="4">
											<logic:present name="relacaoHidrometroMarca">
												<html:options collection="relacaoHidrometroMarca" labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
									<html:hidden property="marcaDescricao"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>					
					<tr>
						<td><strong>Classe:</strong></td>
						<td align="left">
							<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroClasseMetrologica" style="width: 230px;" disabled="true" multiple="true" size="4">
											<logic:present name="colecaoHidrometroClasseMetrologica">
												<html:options collection="colecaoHidrometroClasseMetrologica"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
					</tr>
					
					<tr>
					<td><strong>Proteção:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroProtecao" style="width: 230px;" disabled="true" multiple="true" size="4">
											<logic:present name="colecaoHidrometroProtecao">
												<html:options collection="colecaoHidrometroProtecao"
													labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td><strong>Local de Instalação:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="hidrometroLocalInstalacao" style="width: 230px;" disabled="true" multiple="true" size="4">
											<logic:present name="colecaoHidrometroLocalInstalacao">
												<html:options collection="colecaoHidrometroLocalInstalacao" labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td><strong>Anormalidade de Hidr&ocirc;metro:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="anormalidadeHidrometro" disabled="true" style="width: 230px;"
											onchange="ControleAnormalidade();" multiple="true" size="4">
											<logic:present name="relacaoHidrometroAnormalidade">
												<html:options collection="relacaoHidrometroAnormalidade" labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td width="200"><strong>Num. Ocorr&ecirc;ncias Consecutivas:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td><html:text property="numeroOcorrenciasConsecutivas" size="3" maxlength="2" disabled="true"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
		<tr>
			<td colspan="4">
					<div id="layerHideDadosHidrometro" style="display: block">
					<table align="center" width="95%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosHidrometro',true);" /><b>Diâmetro/Capacidade/Período de Instalação/Leitura Acumulada
							</b></a></span></td>
						</tr>
					</table>
					</div>
					<div id="layerShowDadosHidrometro" style="display: none">
						<table align="center" width="95%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('DadosHidrometro',false);" /><b>Diâmetro/Capacidade/Período de Instalação/Leitura Acumulada
								</b></a></span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
									<td>
							  	  	 <table width="100%" align="center">
							  			<tr>
							  			
							  			<tr>
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="3">
									<table width="100%" bgcolor="#90c7fc">
										<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
											<td width="22%" align="center"><strong>Diâmetro</strong></td>
											<td width="22%" align="center"><strong>Capacidade</strong></td>
											<td width="22%" align="center" colspan="2"><strong>Período de Instalação</strong></td>
											<td width="24%" align="center"><strong>Leitura Acumulada</strong></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="83px" colspan="3">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%" align="center" bgcolor="#99CCFF">
											<logic:present property="colecaoDadosDoHidrometro" name="ConsultarComandoOsSeletivaActionForm" scope="session">
												<%int cont=0;%>
												<logic:iterate property="colecaoDadosDoHidrometro" name="ConsultarComandoOsSeletivaActionForm" id="dadosDoHidrometroHelper" type="DadosDoHidrometroHelper" scope="session">
													<%cont = cont+1;
													if (cont%2==0) {%>
													<tr bgcolor="#cbe5fe">
													<%}else{%>
													<tr bgcolor="#FFFFFF">
													<%}%>
														<td width="22%">
															<bean:write name="dadosDoHidrometroHelper" property="descricaoHidrometroDiametro"/>
														</td>
														<td width="22%">
															<bean:write name="dadosDoHidrometroHelper" property="descricaoHidrometroCapacidade"/>
														</td>
														<td width="11%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="intervaloInstalacaoInicial"/>
														</td>
														<td width="11%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="intervaloInstalacaoFinal"/>
														</td>
														<td width="24%" align="center">
															<bean:write name="dadosDoHidrometroHelper" property="numeroLeituraAcumulada"/>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
										</table>
									</div>
								</td>
							</tr>						
						</table>
					</td>
				</tr>
			</tr>
		</table>
		</td>
		</tr>
		</table>
		</div>
		</td>
		</tr>		
		</table>
		</td>
		</tr>
		</table>
		</div>
		</td>
		</tr>

		<tr>
			<td colspan="4">
					<div id="layerHideOrdem" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Ordem',true);" /><b> Dados das Ordens de Serviço do Comando
							</b></a></span></td>
						</tr>
					</table>
					</div>
					<div id="layerShowOrdem" style="display: none">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('Ordem',false);" /><b>Dados das Ordens de Serviço do Comando
								</b></a></span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
									<td>
							  	  	 <table width="100%" align="center">
							  			<tr>
										<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="3">
									<table width="100%" bgcolor="#90c7fc">
										<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
											<td width="10%" align="center"><strong>OS</strong></td>
											<td width="10%" align="center" colspan="2"><strong>Tipo Serv.</strong></td>
											<td width="8%" align="center"><strong>Imov</strong></td>
											<td width="3%" align="center"><strong>Loca.</strong></td>
											<td width="3%" align="center"><strong>Set.</strong></td>
											<td width="5%" align="center"><strong>Quad.</strong></td>
											<td width="10%" align="center"><strong>Sit.</strong></td>
											<td width="13%" align="center" colspan="2"><strong>Data Gera.</strong></td>
											<td width="13%" align="center"><strong>Uni. Gera.</strong></td>
											<td width="10%" align="center" colspan="2"><strong>Data Encer.</strong></td>
											<td width="10%" align="center" colspan="2"><strong>Uni. Encer.</strong></td>
											<td width="10%" align="center" colspan="2"><strong>Mot. Encer.</strong></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="83px" colspan="3">
									<div style="width: 100%; height: 600; overflow: auto;">
										<table width="100%" height="100%" align="center" bgcolor="#99CCFF">
											<logic:present property="colecaoDadosOrdemServico" name="ConsultarComandoOsSeletivaActionForm" scope="session">
												<%int cont=0;%>
												<logic:iterate property="colecaoDadosOrdemServico" name="ConsultarComandoOsSeletivaActionForm"
													 id="dadosOrdemServicoHelper" type="DadosOrdemServicoHelper" scope="session">
													<%cont = cont+1;
													if (cont%2==0) {%>
													<tr bgcolor="#cbe5fe">
													<%}else{%>
													<tr bgcolor="#FFFFFF">
													<%}%>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idOrdemServico"/>
														</td>
														<td width="8%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idServicoTipo"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idImovel"/>
														</td>
														<td width="5%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idLocalidade"/>
														</td>
														<td width="5%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="codigoSetor"/>
														</td>
														<td width="5%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="numeroQuadra"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="descricaoAbreviada"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="dataGeracao"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idUnidadeGeracao"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="dataEncerramento"/>
														</td>
														<td width="10%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idUnidadeEncerramento"/>
														</td>
														<td width="8%" align="center">
															<bean:write name="dadosOrdemServicoHelper" property="idMotivoEncerramento"/>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
										</table>
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
			</div>
			</td>
			</tr>
			</table>
						
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
					<tr>
					<td>
						<div id="botoes">
							
							<table>
								<tr>
									<td height="24" align="left" width="100%">
							          	<input type="button" 
							          		class="bottonRightCol" 
							          		value="Voltar" 
							          		onclick="history.back()"/>
									</td>
									
									<td height="24" align="right" width="100%">
							          	<input type="button" 
							          		class="bottonRightCol" 
							          		value="Emitir Relatório Dados Comando" 
							          		onclick="javascript:emitirRelatorioDadosComando();"/>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

