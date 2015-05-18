<%@page import="java.util.Map"%>
<%@page import="gcom.micromedicao.medicao.MedicaoTipo"%>
<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.micromedicao.MovimentoRoteiroEmpresa"%>
<%@page import="gcom.micromedicao.hidrometro.Hidrometro"%>
<%@page import="gcom.atendimentopublico.ligacaoagua.LigacaoAgua"%><html:html>
<head>

	<%@ include file="/jsp/util/titulo.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

	<script type="text/javascript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	<html:javascript staticJavascript="false"  formName="InformarDadosLeituraAnormalidadeActionForm" />

	<script type="text/javascript" src="<bean:message key="caminho.js"/>util.js"></script>
	<script type="text/javascript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	<script type="text/javascript">

		function fazerPaginacao(page, pageAtual){
			var form = document.forms[0];
			var msg = '';
			if(form.idEmpresa.value == -1){
				msg = 'Informe Empresa. \n';
			}
			if(form.codigoLeiturista.value == -1){
				msg += 'Informe Leiturista. \n';
			}
			if(form.idRota.value == ''){
				msg += 'Informe Rota.';
			}
			if(msg != ''){
				alert(msg);
			}else{
				if (confirm("Deseja Atualizar os dados informados?")) {
					form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?pesquisaImovelRota=1&atualizarDigitacao=1&page.offset="+page + "&page.offsetAtual="+pageAtual;
				} else {
					form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?pesquisaImovelRota=1&page.offset="+page + "&page.offsetAtual="+pageAtual;
				}
				form.submit();
			}
		}

		function validaDataCamposDigitacao(data, event, anoMesMovimento, dataLeituraAnterior){
			if(verificaDataMensagemPersonalizada(data,'Data do Pagamento Inválida') && data.value != ""){
				if(compararDataComDataAtual(data.value) == false ){
					alert("Data de Leitura não pode ser maior que a data atual.");
					fazerPaginacao();
					data.value ="";
					return;
				}else{
					anoDigitado = data.value.substring(6,10);
					mesDigitado = data.value.substring(3,5);
					var anoMesDigitado = anoDigitado + mesDigitado;
					if((anoMesDigitado != anoMesMovimento)&&(anoMesDigitado != anoMesMovimento-1)){
						alert("Data de leitura incompatível como mês/ano de faturamento.")
						data.value = "";
						return;
					}
				}
			}
		}

		function mudaFoco(registroAtual){
			var idProximoRegistro = "registro" + registroAtual;
			document.getElementById(idProximoRegistro).focus();
		}

		function atualizarForm() {
			var form = document.forms[0];
			if (form.dataLeitura.value == '') {
				alert('Informe a data da leitura');
				return;
			}
			if (form.leitura.value == '') {
				alert('Informe a leitura');
				return;
			}
			
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?atualizar=1";
			form.submit();
		}
	
		function atualizarFormDigitacao(pageAtual) {
			var form = document.forms[0];
			
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?finalizar=1&atualizarDigitacao=1&page.offsetAtual="+pageAtual;
			form.submit();
		}

		function marcarRadio(valor) {
			var i = 0;
			var form = document.forms[0];
			if (form.idRegistros != undefined) {
				for (i=0; i<form.idRegistros.length;i++) {
					if (form.idRegistros[i].value == valor) {
						form.idRegistros[i].checked = true;
					}
				}
			}
		}
	
		function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	   		var form = document.forms[0];
	   		if(tipoConsulta == 'rotaLeiturista'){
				form.idRota.value = codigoRegistro;
	   	   	}else{
		   	   	form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?retornarImovelRota=1";
				form.submit();
	   	   	}
	  	}

		function limparLeiturista(form) {
	    	form.codigoLeiturista.value = "";
	    	form.nomeLeiturista.value = "";
		}

		function buscaLeiturista() {
			var form = document.forms[0];
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?pesquisarLeiturista=1";
			form.submit();
		}

		function pesquisaRota() {
			var form = document.forms[0];
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?pesquisaRota=1";
			form.submit();
		}

		function buscaImoveis() {
			var form = document.forms[0];
			var msg = '';
			if(form.idEmpresa.value == -1){
				msg = 'Informe Empresa. \n';
			}
			if(form.codigoLeiturista.value == -1){
				msg += 'Informe Leiturista. \n';
			}
			if(form.idRota.value == ''){
				msg += 'Informe Rota.';
			}
			if(msg != ''){
				alert(msg);
			}else{
				form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?pesquisaImovelRota=1&page.offset=1";
				form.submit();
			}
		}
		
		function proximoImovel(valor) {
			var form = document.forms[0];
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?proximoImovelRota="+valor;
			form.submit();
		}

		function anteriorImovel(valor) {
			var form = document.forms[0];
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?anteriorImovelRota="+valor;
			form.submit();
		}

		function abrirPopupPesquisaRota(){
			var form = document.forms[0];
			var msg = '';
			if(form.idEmpresa.value == -1){
				msg = 'Informe Empresa. \n';
			}
			if(form.codigoLeiturista.value == -1){
				msg += 'Informe Leiturista.';
			}
			if(msg != ''){
				alert(msg);
			}else{
				abrirPopup('exibirPesquisarRotaLeituristaAction.do?chamarPopup=true&empresa=' + form.idEmpresa.value + '&leiturista=' + form.codigoLeiturista.value, 250, 470);
			}			
		}
	
		function validarFormaEntradaDados(valor){
			window.location.href='/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?manter=sim&menu=sim&indicadorEntradaDados=' + valor;
		}

		function verificarFormaEntradaDados(){
			var form = document.forms[0];
			var radioIndicadorEntradaDados = form.indicadorEntradaDados;
		 	var codigoIndicadorEntradaDados;
		 	for(var i = 0; i < radioIndicadorEntradaDados.length; i++){
				if(radioIndicadorEntradaDados[i].checked){
					codigoIndicadorEntradaDados = radioIndicadorEntradaDados[i].value;
				 	break;
				}	
			}
			if(codigoIndicadorEntradaDados == '1'){
				document.getElementById('manual').style.display="none";
				document.getElementById('arquivo').style.display="inline";
				if(document.getElementById('dadosLeituraImovel') != null){
					document.getElementById('dadosLeituraImovel').style.display="none";
				}
				if(document.getElementById('dadosLeituraImovelBotoes') != null){
					document.getElementById('dadosLeituraImovelBotoes').style.display="none";
				}
				if(document.getElementById('dadosLeituraImovelCampos') != null){
					document.getElementById('dadosLeituraImovelCampos').style.display="none";
				}
				if(document.getElementById('dadosLeituraImovelCamposDigitacao') != null){
					document.getElementById('dadosLeituraImovelCamposDigitacao').style.display="none";
				}
			}else{
				document.getElementById('manual').style.display="inline";
				document.getElementById('arquivo').style.display="none";
				
				if(codigoIndicadorEntradaDados == '3'){
					if(document.getElementById('dadosLeituraImovel') != null){
						document.getElementById('dadosLeituraImovel').style.display="none";
					}
					
					if(document.getElementById('dadosLeituraImovelCampos') != null){
						document.getElementById('dadosLeituraImovelCampos').style.display="none";
					}
					if(document.getElementById('dadosLeituraImovelBotoes') != null){
						document.getElementById('dadosLeituraImovelBotoes').style.display="none";
					}
					if(document.getElementById('dadosLeituraImovelCamposDigitacao') != null){
						document.getElementById('dadosLeituraImovelCamposDigitacao').style.display="inline";
					}
				}else{
					if(document.getElementById('dadosLeituraImovel') != null){
						document.getElementById('dadosLeituraImovel').style.display="inline";
					}
					
					if(document.getElementById('dadosLeituraImovelCampos') != null){
						document.getElementById('dadosLeituraImovelCampos').style.display="inline";
					}
					if(document.getElementById('dadosLeituraImovelBotoes') != null){
						document.getElementById('dadosLeituraImovelBotoes').style.display="inline";
					}
					if(document.getElementById('dadosLeituraImovelCamposDigitacao') != null){
						document.getElementById('dadosLeituraImovelCamposDigitacao').style.display="none";
					}
				}
			}
			//form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?indicadorEntradaDados=" + form.indicadorEntradaDados.value;
			//form.submit();
		}

		function processarArquivo() {
			var form = document.forms[0];
			if (form.idGrupoFaturamento.value == '-1') {
				alert('Informe o Grupo de Faturamento');
				return;
			}
			
			form.action = "exibirInformarDadosLeituraAnormalidadeAction.do?processarArquivo=1";
			form.submit();
		}
	
	</script>
</head>

<body leftmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}'); marcarRadio(<logic:present scope="session" name="pesquisaImovelRota"> <bean:write scope="session" name="pesquisaImovelRota" /></logic:present><logic:notPresent scope="session" name="pesquisaImovelRota">''</logic:notPresent>);verificarFormaEntradaDados();">
	<html:form action="/informarLeituraDadosAnormalidadeAction.do" 
			   name="InformarDadosLeituraAnormalidadeActionForm"
			   type="gcom.gui.micromedicao.leitura.InformarDadosLeituraAnormalidadeActionForm"
			   method="post" 
			   enctype="multipart/form-data" >
	
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
				<td width="600" valign="top" class="centercoltext">
					<table>
						<tr>
							<td></td>
						</tr>
					</table>

					<!--Início Tabela Reference a Páginação da Tela de Processo-->
					<table>
						<tr>
							<td></td>
						</tr>
					</table>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
							<td class="parabg">Informar Dados de Leitura e Anormalidade:</td>
							<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
					<!--Fim Tabela Reference a Páginação da Tela de Processo-->
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td height="10" colspan="2" width="100%">Para informar dados de leitura e anormalidade, informe os dados abaixo:</td>
						</tr>
					</table>
					<table>
						<tr>
							<td><strong>Selecione a forma de entrada dos dados:</strong></td>
							<td>
								<strong> 
									<logic:equal name="indicadoPermitirInformarDadosLeituraLote" value="<%=ConstantesSistema.INATIVO %>">
										<html:radio property="indicadorEntradaDados" value="2" onclick="javascript:validarFormaEntradaDados(2);" /> Manual
									</logic:equal>
								 	<html:radio property="indicadorEntradaDados" value="1" onclick="javascript:validarFormaEntradaDados(1);"  /> Via Arquivo 
									<logic:equal name="indicadoPermitirInformarDadosLeituraLote" value="<%=ConstantesSistema.ATIVO %>">
										<html:radio property="indicadorEntradaDados" value="3" onclick="javascript:validarFormaEntradaDados(3);"  /> Manual
									</logic:equal>
						 		</strong>
						 	</td>
						</table>
						<table id="arquivo">
							<tr>
								<td>
									<strong>Grupo de Faturamento:</strong> 
									<span class="style2"> <strong>
										<font color="#FF0000">*</font> </strong> 
									</span>
								</td>
								<td>
									<strong> 
										<html:select property="idGrupoFaturamento" style="width: 230px;">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoFaturamentoGrupo" scope="session">
												<html:options collection="colecaoFaturamentoGrupo" labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td height="10">
									<strong>
										Arquivo de leituras e anormalidades:<font color="#FF0000">*</font>
									</strong>
								</td>
								<td>
									<html:file property="arquivoLeitura" size="40"/>
								</td>
							</tr>
							<tr>
								<td>
									<input type="button" class="buttonAbaRodape" value="Processar Arquivo" name="processaArquivo" onclick="javascript:processarArquivo();" />
								</td>
							</tr>
						</table>
						<table id="manual">
							<tr>
								<td>
									<strong>Referência do Movimento:</strong>
								</td>
								<td>
									<html:text property="referenciaMovimento" maxlength="7" size="7" onkeyup="mascaraAnoMes(this, event);"/>
								</td>
							</tr>
							<tr>
								<td><strong>Empresa:</strong> <span class="style2"> <strong><font color="#FF0000">*</font> </strong> </span></td>
								<td>
									<strong> 
										<html:select property="idEmpresa" style="width: 230px;" onchange="javascript:buscaLeiturista();">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoEmpresa" scope="session">
												<html:options collection="colecaoEmpresa" labelProperty="descricaoAbreviada" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Agente Comercial:</strong> <span class="style2"> <strong><font color="#FF0000">*</font> </strong> </span></td>
								<td>
									<strong>
										<html:select property="codigoLeiturista" style="width: 230px;" onchange="javascript:pesquisaRota();">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoLeiturista" scope="session">
												<html:options collection="colecaoLeiturista" labelProperty="nomeLeiturista" property="id" />
											</logic:present>
										</html:select> 
									</strong>
								</td>	
							</tr>
							<tr>
		               			<td height="24"><strong>Rota:<font color="#FF0000">*</font></strong></td>
		               			<td colspan="2">
			               			<html:text maxlength="5" property="idRota" size="5"  onkeypress="return isCampoNumerico(event);"/>
			               			<a href="javascript:abrirPopupPesquisaRota();">
	                         			<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
	                         		</a>
	                   			</td>
		             		</tr>
		        			<tr>
								<td colspan="2">
									<input type="button" name="Button" class="bottonRightCol" value="Pesquisar" onClick="javascript:buscaImoveis();" style="width: 80px" />
								</td>
							</tr>
						</table>
						<br>
						<logic:present name="listaImoveisRota">
							<hr align="center" />
							<logic:notEmpty name="listaImoveisRota">
								<logic:present name="indicadorEntradaDadosDigitacao">
									<logic:equal name="indicadorEntradaDadosDigitacao" value="<%=ConstantesSistema.INATIVO%>">
										<strong id="dadosLeituraImovel">Dados de Leitura do Imóvel: <bean:write scope="session" name="posicaoRota" /> / <bean:write scope="session" name="qtdImoveisRota" /></strong>
										<br><br>
										<table id="dadosLeituraImovelCampos">
											<tr>
												<td>
													<strong>Referência do Movimento:</strong>
												</td>
												<td>
													<bean:write name="InformarDadosLeituraAnormalidadeActionForm" property="referenciaMovimento"/>
												</td>
											</tr>
											<tr>
												<td>
													<strong>Situação da Leitura:</strong>
												</td>
												<td>
													<logic:equal scope="session" name="imoveisRota" property="indicadorFase" value="<%=MovimentoRoteiroEmpresa.FASE_GERADO.toString() %>">
														GERADA
													</logic:equal>
													<logic:equal scope="session" name="imoveisRota" property="indicadorFase" value="<%=MovimentoRoteiroEmpresa.FASE_LEITURA_RETORNADA.toString() %>">
														LIDA
													</logic:equal>
													<logic:equal scope="session" name="imoveisRota" property="indicadorFase" value="<%=MovimentoRoteiroEmpresa.FASE_PROCESSADO.toString() %>">
														PROCESSADA
													</logic:equal>
												</td>
											</tr>
											<tr>
												<td>
													<strong>Tipo de Medição:</strong>
												</td>
												<td>
													<logic:notEmpty name="imoveisRota.medicaoTipo">
														<bean:write scope="session" name="imoveisRota" property="medicaoTipo.id" /> - <bean:write scope="session" name="imoveisRota" property="medicaoTipo.descricao" />
													</logic:notEmpty >
												</td>
											</tr>
											<tr>
												<td>
													<strong>Data da Leitura:</strong><span class="style2"> <strong>
													<font color="#FF0000">*</font> </strong> </span>
												</td>
												<td>
													<logic:notPresent scope="session" name="verificaFaturamentoRota">
														<html:text property="dataLeitura" size="11" maxlength="10" tabindex="14" onkeyup="mascaraData(this, event)" />
														<a href="javascript:abrirCalendario('FiltrarRegistroAtendimentoActionForm', 'dataLeitura');">
															<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/>
														</a>					
														</strong>(dd/mm/aaaa)<strong> 
													</logic:notPresent>
										
													<logic:present scope="session" name="verificaFaturamentoRota">
														<html:text property="dataLeitura" size="11" maxlength="10" tabindex="14" onkeyup="mascaraData(this, event)" disabled="true" />
													</logic:present>
												</td>
											</tr>
											<tr>
												<td>
													<strong>Endereço:</strong>
												</td>
												<td>
													<bean:write scope="session" name="imoveisRota" property="enderecoImovel" />
												</td>
											</tr>
											<tr>
												<td>
													<strong>Matrícula do Imóvel:</strong>
												</td>
												<td>
													<bean:write scope="session" name="imoveisRota" property="imovel.id" />
												</td>
											</tr>
											<tr>
												<td>
													<strong>Inscrição do Imóvel:</strong>
												</td>
												<td>
													<bean:write scope="session" name="imoveisRota" property="numeroInscricao" />
												</td>
											</tr>
											<tr>
												<td>
													<strong>Seguencial do Imóvel na Rota:</strong>
												</td>
												<td>
													<bean:write scope="session" name="imoveisRota" property="imovel.numeroSequencialRota" />
												</td>
											</tr>
											<tr>
												<td>
													<strong>Hidrômetro:</strong>
												</td>
												<td>
													<bean:write scope="session" name="imoveisRota" property="numeroHidrometro" />
												</td>
											</tr>
											<tr>
												<td>
													<strong>Leitura:</strong><span class="style2"> <strong>
													<font color="#FF0000">*</font> </strong> </span>
												</td>
												<td>
												<logic:notPresent scope="session" name="verificaFaturamentoRota">
													<html:text property="leitura" onkeypress="return isCampoNumerico(event);" />
												</logic:notPresent>
												<logic:present scope="session" name="verificaFaturamentoRota">
													<html:text property="leitura" onkeypress="return isCampoNumerico(event);" disabled="true"/>
												</logic:present>
												</td>
											</tr>
											<tr>
												<td>
													<strong>Ocorrência:</strong> 
												</td>
												<logic:notPresent scope="session" name="verificaFaturamentoRota">
													<td>
														<strong>
															<html:select property="idOcorrencia" style="width: 230px;">
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<logic:present name="colecaoOcorrencia" scope="session">
																	<html:options collection="colecaoOcorrencia" labelProperty="descricao" property="id" />
																</logic:present>
												    		</html:select> 
											    		</strong>
										   			</td>
									   			</logic:notPresent>
									   			<logic:present scope="session" name="verificaFaturamentoRota">
													<td>
														<strong> 
															<html:select property="idOcorrencia" style="width: 230px;" disabled="true">
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<logic:present name="colecaoOcorrencia" scope="session">
																	<html:options collection="colecaoOcorrencia" labelProperty="descricao" property="id" />
																</logic:present>
											    			</html:select> 
											    		</strong>
										   			</td>
									   			</logic:present>
											</tr>
									</logic:equal>
									<logic:equal name="indicadorEntradaDadosDigitacao" value="<%=ConstantesSistema.ATIVO %>">
										<table width="100%" height="100%" align="center" id="dadosLeituraImovelCamposDigitacao">
											<tr bgcolor="#99CCFF" >
												<td width="25%" bgcolor="#90c7fc" height="20">
													<strong>Inscrição do Imóvel</strong>
												</td>
												<td width="25%"><strong>Data da Leitura</strong></td>
												<td width="25%"><strong>Leitura</strong></td>
												<td width="25%"><strong>Ocorrência</strong></td>
											</tr>
											
											<!-- magno 1 -->
											<tr>
												<td colspan="4">
													<div style="overflow: auto; height: 200px; "> 
														<table width="100%" height="100%" align="center">
															<%--Esquema de paginação--%>
															<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="200" items="${sessionScope.totalRegistros}">
																<% int cont = 0; %>
																<pg:param name="pg" />
																<pg:param name="q" />
																<logic:iterate id="movimentoRoteiroEmpresa" name="listaImoveisRota" type="MovimentoRoteiroEmpresa">
																	<pg:item>
																		<% cont = cont + 1; %>
																		<tr>
																			<td width="26%"> 
																				<bean:write name="movimentoRoteiroEmpresa" property="numeroInscricao" /> 
																			</td>
																			<%Map<Integer, LigacaoAgua> mapLigacaoAgua = (Map)session.getAttribute("mapLigacaoAgua"); %>
																			<%Map<Integer, Hidrometro> mapHidrometro = (Map)session.getAttribute("mapHidrometro"); %>
																			<%int idMovimentoRoteiroEmpresa = movimentoRoteiroEmpresa.getId(); %>
																			<%if(MovimentoRoteiroEmpresa.FASE_PROCESSADO.equals(movimentoRoteiroEmpresa.getIndicadorFase()) ||
																			
																				(movimentoRoteiroEmpresa.getMedicaoTipo() != null
																				&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA) 
																				&& mapLigacaoAgua.get(idMovimentoRoteiroEmpresa).getHidrometroInstalacaoHistorico() == null) ||
																				
																				(movimentoRoteiroEmpresa.getMedicaoTipo() != null
																				&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.POCO) 
																				&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null) ||
																				
																				(Util.isVazioOuBrancoOuZero(movimentoRoteiroEmpresa.getMedicaoTipo()) 
																				&& mapLigacaoAgua != null 
																				&& mapLigacaoAgua.get(idMovimentoRoteiroEmpresa) != null
																				&& mapLigacaoAgua.get(idMovimentoRoteiroEmpresa).getHidrometroInstalacaoHistorico() == null
																				&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() == null)
																				){ %>
																					<td width="26%">
																						<input style="background-color: #E5E5E5" name="dataLeitura${movimentoRoteiroEmpresa.id}" id="registro<%=cont%>" type="text" size="10" maxlength="10"
																							   value="${movimentoRoteiroEmpresa.dataLeituraFormatada}" readonly="readonly" onkeyup="mascaraData(this, event);"
																							   onfocus="javascript:mudaFoco(<%=cont +1%>);"> 
																					</td>
																					<td width="26%">
																						<input style="background-color: #E5E5E5" name="leitura${movimentoRoteiroEmpresa.id}" type="text" 
																							   value="${movimentoRoteiroEmpresa.numeroLeitura}" readonly="readonly" onfocus="javascript:mudaFoco(<%=cont +1%>);"> 
																					</td>
																					<td width="22%"> 
																						<input style="background-color: #E5E5E5" name="ocorrencia${movimentoRoteiroEmpresa.id}" type="text" size="8" maxlength="4"
																							   value="${movimentoRoteiroEmpresa.leituraAnormalidade.id}" readonly="readonly" onfocus="javascript:mudaFoco(<%=cont +1%>);"> 
																					</td>
																			<%}else{ %>
																				<%int tamanhoMaximo=200; %>
																				<%if(movimentoRoteiroEmpresa.getMedicaoTipo() != null 
																					&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)
																					&& mapLigacaoAgua.get(idMovimentoRoteiroEmpresa).getHidrometroInstalacaoHistorico().getHidrometro() != null
																					&& mapLigacaoAgua.get(idMovimentoRoteiroEmpresa).getHidrometroInstalacaoHistorico() != null){ %>
																				
																					<%tamanhoMaximo = mapHidrometro.get(idMovimentoRoteiroEmpresa).getNumeroDigitosLeitura().intValue(); %>
																				
																				<%} else if(movimentoRoteiroEmpresa.getMedicaoTipo() != null
																						&& movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)
																						&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() != null
																						&& movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico().getHidrometro() != null){%>
																				
																					<%tamanhoMaximo = movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico().getHidrometro().getNumeroDigitosLeitura().intValue(); %>
																				<%} %>
																					
																				<td width="26%">
																					<input name="dataLeitura${movimentoRoteiroEmpresa.id}" id="registro<%=cont%>" type="text" size="10" maxlength="10"
																						   value="${movimentoRoteiroEmpresa.dataLeituraFormatada}" onkeyup="mascaraData(this, event);" 
																						   onchange="javascript:validaDataCamposDigitacao(this, event, ${movimentoRoteiroEmpresa.anoMesMovimento},<%=movimentoRoteiroEmpresa.getDataLeituraAnteriorFormatada().toString()%>);"> 
																				</td>
																				<td width="26%"> 
																					<input name="leitura${movimentoRoteiroEmpresa.id}" type="text" maxlength="<%=tamanhoMaximo%>"
																						   value="${movimentoRoteiroEmpresa.numeroLeitura}" onkeypress="return isCampoNumerico(event);"
																						   onchange="javascript:validaDataCamposDigitacao(document.forms[0].dataLeitura${movimentoRoteiroEmpresa.id}, event, ${movimentoRoteiroEmpresa.anoMesMovimento}, <%=movimentoRoteiroEmpresa.getDataLeituraAnteriorFormatada().toString()%>);"> 
																				</td>
																				<td width="22%"> 
																					<input  name="ocorrencia${movimentoRoteiroEmpresa.id}" type="text" size="8" maxlength="4"
																							value="${movimentoRoteiroEmpresa.leituraAnormalidade.id}" onkeypress="return isCampoNumerico(event);"
																							onchange="javascript:validaDataCamposDigitacao(document.forms[0].dataLeitura${movimentoRoteiroEmpresa.id}, event, ${movimentoRoteiroEmpresa.anoMesMovimento}, <%=movimentoRoteiroEmpresa.getDataLeituraAnteriorFormatada().toString()%>);"> 
																				</td>
																			<%} %>
																		</tr>
																	</pg:item>
																</logic:iterate>
															</pg:pager>
														</table>
													</div>
												</td>
											</tr>
											<!-- /magno 1 -->
											
											<tr>
												<td>
													<logic:present name="indicadorDesativaVoltar">
														<logic:equal name="indicadorDesativaVoltar" value="<%=ConstantesSistema.ATIVO %>">
															<input type="button" class="buttonAbaRodape" value="Voltar" name="voltar" onclick="javascript:fazerPaginacao(<%=((Integer)request.getAttribute("page.offset")) - 1%>, <%=((Integer)request.getAttribute("page.offset"))%>)"/>
														</logic:equal>
														<logic:equal name="indicadorDesativaVoltar" value="<%=ConstantesSistema.INATIVO %>">
															<input type="button" class="buttonAbaRodape" value="Voltar" name="voltar" onclick="javascript:fazerPaginacao(<%=((Integer)request.getAttribute("page.offset")) - 1%>, <%=((Integer)request.getAttribute("page.offset"))%>)" disabled="disabled"/>
														</logic:equal>
														<logic:equal name="indicadorDesativaAvancar"  value="<%=ConstantesSistema.ATIVO %>">
															<input type="button" class="buttonAbaRodape" value="Avançar" name="avancar" onclick="javascript:fazerPaginacao(<%=((Integer)request.getAttribute("page.offset")) + 1%>, <%=((Integer)request.getAttribute("page.offset"))%>)"/>
														</logic:equal>
														<logic:equal name="indicadorDesativaAvancar" value="<%=ConstantesSistema.INATIVO %>">
															<input type="button" class="buttonAbaRodape" value="Avançar" name="avancar" onclick="javascript:fazerPaginacao(<%=((Integer)request.getAttribute("page.offset")) + 1%>, <%=((Integer)request.getAttribute("page.offset"))%>)" disabled="disabled"/>
														</logic:equal>
													</logic:present>
												</td>
												<td colspan="3" align="right">
													<input type="button" class="buttonAbaRodape" value="Atualizar" name="atualizar" onclick="javascript:atualizarFormDigitacao(<%=((Integer)request.getAttribute("page.offset"))%>);" />
												</td>
											</tr>
										</table>
									</logic:equal>
								
									<logic:equal name="indicadorEntradaDadosDigitacao" value="<%=ConstantesSistema.INATIVO %>">
										<table width="100%" height="100%" align="center" id="dadosLeituraImovelBotoes">
											<tr>
												<%
												Integer anterior = (Integer) session.getAttribute("anterior");
												Integer proximo = (Integer) session.getAttribute("proximo");
												Integer qtd = (Integer) session.getAttribute("qtdImoveisRota");
												
												if (anterior < 0) { %>
													<td align="right" width="25%"><img src="imagens/voltar.gif" border="0" /></td>
													<td>
														<input type="button" disabled="disabled" class="buttonAbaRodape" value="Anterior" name="anterior" onclick="javascript:anteriorImovel(<bean:write scope="session" name="anterior"/>);" />
													</td>
												<%} else {%>									
													<td align="right" width="25%">
														<a href="javascript:anteriorImovel(<bean:write scope="session" name="anterior"/>);"><img src="imagens/voltar.gif" border="0" /></a>
													</td>
													<td>
														<input type="button" class="buttonAbaRodape" value="Anterior" name="anterior" onclick="javascript:anteriorImovel(<bean:write scope="session" name="anterior"/>);" />
													</td>
								    			<%} 
								    
								    			if (proximo == qtd) { %>
													<td>
														<input type="button" disabled="disabled" class="buttonAbaRodape" value="Proximo" name="proximo" onclick="javascript:proximoImovel(<bean:write scope="session" name="proximo"/>);" />
													</td>
													<td align="left" width="25%">
														<img src="imagens/avancar.gif" border="0" />
													</td>
												<%} else { %>	
													<td>
														<input type="button" class="buttonAbaRodape" value="Proximo" name="proximo" onclick="javascript:proximoImovel(<bean:write scope="session" name="proximo"/>);" />
													</td>
													<td align="left" width="25%">
														<a href="javascript:proximoImovel(<bean:write scope="session" name="proximo"/>);"><img src="imagens/avancar.gif" border="0" /></a>
													</td>
												<%} %>
												<td>
													<input type="button" class="buttonAbaRodape" value="Localizar" name="localizar" onclick="javascript:abrirPopup('exibirInformarDadosLeituraAnormalidadeAction.do?pesquisarImovelRotaPopup=1',230,600);" />
												</td>
												<td>
													<input type="button" class="buttonAbaRodape" value="Atualizar" name="atualizar" onclick="javascript:atualizarForm();" />
												</td>
											</tr>
										</table>
									</logic:equal>
									<hr />
									<table>
										<tr>
			       							<td>&nbsp;</td>
			       							<td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
			   							</tr>
	
										<tr>
											<td colspan="2">
												<input type="button" name="Button"
												class="bottonRightCol" value="Desfazer" tabindex="23"
												onClick="javascript:window.location.href='/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?menu=sim'"
												style="width: 80px" />&nbsp; 
												<input type="button" name="Button"
												class="bottonRightCol" value="Cancelar" tabindex="24"
												onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
												style="width: 80px" />
											</td>
										</tr>
   									</table>
								</table>
							</logic:present>
						</logic:notEmpty>
					</logic:present>
					<p>&nbsp;</p>
					<logic:notPresent name="listaImoveisRota">
						<logic:empty name="listaImoveisRota">
							<table>
			   					<tr>
			       					<td></td>
			       					<td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
			   					</tr>
								<tr>
									<td colspan="2">
										<input type="button" name="Button" class="bottonRightCol" value="Desfazer" tabindex="23"
											   onClick="javascript:window.location.href='/gsan/exibirInformarDadosLeituraAnormalidadeAction.do?menu=sim'"
											   style="width: 80px" />&nbsp; 
										<input type="button" name="Button" class="bottonRightCol" value="Cancelar" tabindex="24"
											   onClick="javascript:window.location.href='/gsan/telaPrincipal.do'" style="width: 80px" />
									</td>
								</tr>	   
   							</table>
						</logic:empty>
					</logic:notPresent>
				</tr>		
			</table>
			<tr>
				<td colspan="4"><%@ include file="/jsp/util/rodape.jsp"%>
			</tr>
		</html:form>
	</body>
</html:html>

