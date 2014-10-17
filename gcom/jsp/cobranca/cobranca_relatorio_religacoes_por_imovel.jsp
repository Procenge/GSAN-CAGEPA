<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="FiltroRelatorioReligacoesPorImovelActionForm"  dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validaForm(form){

		var campo = form.comando;
		var aux;
		for(var i = 0; i < campo.length; i++){
			if(campo[i].checked){
				aux = campo[i].value;
				}
			}
		
		if (aux == undefined){
			
			if (validarDatas()){
				submeterFormPadrao(form);
			}
			
		} else {
			if (aux == "3") {

				if (validarDatas()){
					submeterFormPadrao(form);
				}
				
			} else {
				submeterFormPadrao(form);
			}
		}
	}

	function validarDatas(){

		var form = document.forms[0];
		
		var periodoInicio = form.periodoInicial.value;
		var periodoFim = form.periodoFim.value;

		if (isBrancoOuNulo(periodoInicio) && isBrancoOuNulo(periodoFim)){
			alert("Informe:\no Período");
			return false;
		} else if (isBrancoOuNulo(periodoInicio)){
			alert("Informe:\nPeríodo Inicial");
			return false;
		} else if (isBrancoOuNulo(periodoFim)){
			alert("Informe:\nPeríodo Final");
			return false;
		}

		return true;

	}

	function validarIntervalos(){

		var form = document.forms[0];
		
		var periodoInicio = form.periodoInicio.value;
		var periodoFim = form.periodoFim.value;

		// Verificamos se a data final informada é menor que a data inicial
	    if (comparaData(periodoFim, '<', periodoInicio)){
			  alert("A data final deve ser maior que a data inicial");
			  return false;
	    }
	    return true;
	}

	function limparForm(form) {

		var form = document.forms[0];
		
		form.acao.value = "-1";
		form.periodoInicial.value = "";
		form.periodoFim.value = "";
		form.idGrupo.value = "-1";
		form.setorComercial.value = "-1";
		form.bairro.value = "-1";
		form.categoria.value = "-1";		
		form.servicoTipo.value = "-1";
		redirecionarSubmit('exibirFiltrarRelatorioReligacoesPorImovelAction.do?menu=sim&limpar=true');
	}

	function limparTitulo(){
		var form = document.forms[0];
		form.idCobrancaAcaoAtividadeComando.value = '';
		form.tituloCobrancaAcaoAtividadeComando.value = '';
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
	      form.idCobrancaAcaoAtividadeComando.value = codigoRegistro;
	      form.tituloCobrancaAcaoAtividadeComando.value = descricaoRegistro;
	    }
	    if (tipoConsulta == 'cobrancaAcaoAtividadeCronograma'){
			form.idCobrancaAcaoAtividadeCronograma.value = codigoRegistro;
			form.descricaoGrupo.value = descricaoRegistro;
	    }
	}

	function recarregaForm(){
		redirecionarSubmit('exibirFiltrarRelatorioReligacoesPorImovelAction.do?menu=sim');
	}

	function verificaTipoComando(){

		var form = document.forms[0];
		
		var campo = form.comando;
		var aux;
		for(var i = 0; i < campo.length; i++){
			if(campo[i].checked){
				aux = campo[i].value;
				}
			}
		
		if (aux == undefined){
			habilitarCampos();
		} else {
			if (aux == "3") {
				habilitarCampos();
			} else {
				desabilitarCampos();
			}
		}
	}

	function desabilitarCampos(){

		var form = document.forms[0];
		
		form.acao.disabled = true;
		//$("select[name='acao']").attr('disabled', true);
	}

	function habilitarCampos(){

		var form = document.forms[0];
		
		form.acao.disabled = false;
		//$("select[name='acao']").attr('disabled', false);
	}

	function init(){

		var comando = document.forms[0].comando;
		var comandoChecked = false;
		
		for(i = 0; comando.length > i; i++){
			if(comando[i].checked){
				comandoChecked = true;
				break;
			}
		}

		if(!comandoChecked){
			comando[2].checked = true;
		}

	}
	
</script>


</head>

<body leftmargin="5" topmargin="5" onload="init();verificaTipoComando();">
<html:form action="/gerarRelatorioReligacoesPorImovelAction"
	name="FiltroRelatorioReligacoesPorImovelActionForm"
	type="gcom.gui.cobranca.FiltroRelatorioReligacoesPorImovelActionForm"
	method="post">

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
			
			<td width="615" valign="top" class="centercoltext">
				
				<table height="100%">
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
						<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
						<td class="parabg">Filtrar Relatorio Religações por Imóvel</td>
						<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
					</tr>
	
				</table>
				
				<!--Fim Tabela Reference a Páginação da Tela de Processo-->
				<p>&nbsp;</p>
				<table width="100%" border="0">
					<tr>
						<td width="100%" colspan=2>
							<table width="100%" border="0">
								<tr>
									<td>Para filtrar os dados do Relat&oacute;rio de Religações por Imóvel, informe os dados abaixo:</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td><strong>Tipo de Comando: <font color="#FF0000">*</font></strong></td>
						<td>
							<span class="style2"> 					
								<html:radio property="comando" tabindex="2" value="1" onclick="recarregaForm();"/><strong>Cronograma</strong>
								<html:radio property="comando" tabindex="3" value="2" onclick="recarregaForm();"/><strong>Eventual</strong>
								<html:radio property="comando" tabindex="3" value="3" onclick="recarregaForm();"/><strong>Ambos</strong>
	 						</span>
	 					</td>
					</tr>
					<tr>
           					<logic:equal name="FiltroRelatorioReligacoesPorImovelActionForm" property="comando" value="2">
						<td HEIGHT="30"><strong>Título do Comando:<font color="red">  </font></strong></td>
        				<td>
           					<html:hidden property="idCobrancaAcaoAtividadeComando"/>
        					<html:text property="tituloCobrancaAcaoAtividadeComando" size="52" maxlength="52" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"/>
							<a href="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" title="Pesquisar" id="linkTituloComando" >
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparTitulo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>

					    </td>
					    </logic:equal>
					</tr>
					<tr><logic:equal name="FiltroRelatorioReligacoesPorImovelActionForm" property="comando" value="1">
						<td><Strong>Comando:<font color="red">  </font></Strong></td>
						<td>
							<html:hidden property="idCobrancaAcaoAtividadeCronograma"/>
							<html:text property="descricaoGrupo" size="20" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;
							<a href="javascript:abrirPopup('exibirPesquisarCronogramaAcaoCobrancaAction.do', 400, 750);" title="Pesquisar" id="linkComnando">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparGrupo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
						
						</td>
						</logic:equal>
					</tr>
					<tr>
						<td><strong>Ação:</strong></td>
						<td align="left">
							<html:select styleId="acao" property="acao" multiple="true" size="6">
								<html:option value="">&nbsp;</html:option>
								
								<logic:present name="colecaoCobrancaAcao" scope="request">
									<logic:notEmpty name="colecaoCobrancaAcao" scope="request">
										<html:options collection="colecaoCobrancaAcao" labelProperty="descricaoCobrancaAcao" property="id" />
									</logic:notEmpty>
								</logic:present>
							</html:select>
						</td>
					</tr>
					
					<tr>
						<td><strong>Período Inicial:<font color="#FF0000">*</font></strong></td>
						<td>
							<strong> 
								<html:text maxlength="10" property="periodoInicial" size="10" tabindex="7" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this, event);replicarCampo(document.forms[0].periodoFim,this);" /> 
								 <a id="linkPeriodoInicio" href="javascript:abrirCalendario('FiltroRelatorioReligacoesPorImovelActionForm', 'periodoInicial')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>
				   			&nbsp;(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					<tr>
						<td><strong>Período Final:<font color="#FF0000">*</font></strong></td>
						<td>
							<strong> 
								<html:text maxlength="10" property="periodoFim" size="10" tabindex="8" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this, event);" /> 
								  <a id="linkPeriodoFim" href="javascript:abrirCalendario('FiltroRelatorioReligacoesPorImovelActionForm', 'periodoFim')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>
				   			(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					<tr>
						<td><strong>Grupo:</strong></td>
						<td>
							<html:select styleId="idGrupo" property="idGrupo" name="FiltroRelatorioReligacoesPorImovelActionForm" style="width: 230px;" >
								<logic:present name="colecaoCobrancaGrupo" scope="request">
									<html:option value="" />
									<logic:notEmpty name="colecaoCobrancaGrupo" scope="request">
										<html:options collection="colecaoCobrancaGrupo" labelProperty="descricao" property="id" />
									</logic:notEmpty>
								</logic:present>
							</html:select>
						</td>
					</tr>
					<tr>
						<td><strong>Setor (Zona):</strong></td>
						<td>
							<html:select styleId="setorComercial" property="setorComercial" name="FiltroRelatorioReligacoesPorImovelActionForm" style="width: 230px;" multiple="mutiple" size="6">
								<logic:present name="colecaoSetorComercial" scope="request">
									<logic:notEmpty name="colecaoSetorComercial" scope="request">
										<html:option value="" />
										<html:options collection="colecaoSetorComercial" labelProperty="descricao" property="id" />
									</logic:notEmpty>
								</logic:present>
							</html:select>
						</td>
					</tr>
					<tr>
						<td><strong>Bairro:</strong></td>
						<td>
							<html:select styleId="bairro" property="bairro" name="FiltroRelatorioReligacoesPorImovelActionForm" style="width: 230px;" multiple="mutiple" size="6">
								<logic:present name="colecaoBairro" scope="request">
									<logic:notEmpty name="colecaoBairro" scope="request">
										<html:option value="" />
										<html:options collection="colecaoBairro" labelProperty="nome" property="id" />
									</logic:notEmpty>
								</logic:present>
							</html:select>
						</td>
					</tr>
                <tr>
					<td><strong>Categoria:</strong></td>
					<td>
						<html:select styleId="categoria" property="categoria" name="FiltroRelatorioReligacoesPorImovelActionForm" style="width: 230px;" multiple="mutiple" size="4">
							<logic:present name="colecaoCategoria" scope="request">
								<logic:notEmpty name="colecaoCategoria" scope="request">
									<html:option value="" />
									<html:options collection="colecaoCategoria" labelProperty="descricao" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Tipo de Serviço:</strong></td>
					<td>
						<html:select styleId="servicoTipo" property="servicoTipo" name="FiltroRelatorioReligacoesPorImovelActionForm" style="width: 360px; h" multiple="mutiple" size="6">
							<logic:present name="colecaoServicoTipo" scope="request">
								<logic:notEmpty name="colecaoServicoTipo" scope="request">
									<html:option value="" />
									<html:options collection="colecaoServicoTipo" labelProperty="descricao" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
                <tr>
					<td>
						<strong> 
							<font color="#FF0000"> 
								<input type="button" name="Submit22" class="bottonRightCol" value="Desfazer" onClick="javascript:limparForm(document.forms[0]);">
								<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font> 
						</strong>
					</td>
					<td align="right">
						<input type="button" name="Submit2" class="bottonRightCol" value="Gerar Relatorio" onclick="validaForm(document.forms[0]);">
					</td>
				</tr>
				</table>
				<p>&nbsp;</p>
			</tr>
			<!-- Rodapé -->
			<%@ include file="/jsp/util/rodape.jsp"%>
		</table>
		<p>&nbsp;</p>
	<tr>
	</tr>

</html:form>
</body>
</html:html>