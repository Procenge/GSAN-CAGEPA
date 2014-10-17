<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarResolucaoDiretoriaActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin

	function limparForm(form) {

		document.forms[0].target='';
		if(confirm('Deseja realmente limpar os dados do formulário ?')){
			redirecionarSubmit('exibirFiltrarResolucaoDiretoriaAction.do?desfazer='+1);
		}				
	}

	function validarForm(form){
				
		if(testarCampoValorZero(document.FiltrarResolucaoDiretoriaActionForm.numero, 'Número da RD')
		&& testarCampoValorZero(document.FiltrarResolucaoDiretoriaActionForm.assunto, 'Assunto da RD')) { 		
			if(validateFiltrarResolucaoDiretoriaActionForm(form)){			
				submeterFormPadrao(form);			
			}
		}	
	}

	function limparLocalidade() {

	    var form = document.forms[0];

	    form.idLocalidade.value = "";
	    form.nomeLocalidade.value = "";
	    form.nomeLocalidade.style.color = "#000000";
	}

	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

		if(objetoRelacionado.readOnly != true){

			if (objeto == null || codigoObjeto == null){

				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{

				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){

					alert(msg);
				} else{

					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'imovel') {

	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.inscricaoImovel.style.color = "#000000";
	      form.matriculaImovel.disabled = false;
		  form.codigoClienteGuia.value = '';
		  form.nomeClienteGuia.value = '';
		  form.codigoClienteGuia.disabled = true;
		  form.codigoClienteResponsavel.disabled = false;
		  form.idTipoRelacao.disabled = false;
	    
	    } else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteResponsavel.value == 'true') {

		    form.codigoClienteResponsavel.value = codigoRegistro;
		    form.nomeClienteResponsavel.value = descricaoRegistro;
	      	form.nomeClienteResponsavel.style.color = "#000000";
	      	form.indicadorPesquisaClienteResponsavel.value = 'false';
	      	form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
	     
		    }else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteGuia.value == 'true'){

		   	    form.codigoClienteGuia.value = codigoRegistro;
	    	form.nomeClienteGuia.value = descricaoRegistro;
	  		form.nomeClienteGuia.style.color = "#000000";
	  		form.indicadorPesquisaClienteGuia.value = 'false';
	  		form.matriculaImovel.value = '';
			form.inscricaoImovel.value = '';
			form.matriculaImovel.disabled = true;
			form.codigoClienteGuia.disabled = false;
			form.codigoClienteResponsavel.value = '';
			form.nomeClienteResponsavel.value = '';	
			form.codigoClienteResponsavel.disabled = true;
			form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
		    }else if (tipoConsulta == 'localidade'){

		    	form.idLocalidade.value = codigoRegistro;
		        form.nomeLocalidade.value = descricaoRegistro;
		        form.nomeLocalidade.style.color = "#000000";
		    }
	}

-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarResolucaoDiretoriaAction"
	name="FiltrarResolucaoDiretoriaActionForm"
	type="gcom.gui.cobranca.FiltrarResolucaoDiretoriaActionForm"
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
			<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Resolu&ccedil;&atilde;o de Diretoria</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para manter a(s)
					resolu&ccedil;&atilde;o(&otilde;es) de diretoria, informe os dados
					abaixo:</td>
					<td><html:checkbox property="atualizar" value="1" /><strong>Atualizar</strong></td>
				</tr>
				<tr>
					<td><strong>N&uacute;mero RD:</strong></td>
					<td><html:text property="numero" size="15" maxlength="15" /></td>
				</tr>
				<tr>
					<td><strong>Assunto RD:</strong></td>
					<td><html:text property="assunto" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<html:radio property="tipoPesquisa"	value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto
						<html:radio property="tipoPesquisa"	tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto
					</td>
				</tr>
				<tr>
					<td><strong>Data In&iacute;cio Vig&ecirc;ncia RD<font
						color="#000000">:</font></strong></td>
					<td height="25"><html:text property="dataInicio" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('FiltrarResolucaoDiretoriaActionForm', 'dataInicio')" />
					dd/mm/aaaa</td>
				</tr>
				<tr>
					<td height="25"><strong>Data T&eacute;rmino Vig&ecirc;ncia RD:</strong></td>
					<td height="25"><html:text property="dataFim" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('FiltrarResolucaoDiretoriaActionForm', 'dataFim')" />
					dd/mm/aaaa</td>
				</tr>
				<tr>
					<td width="26%"><strong>Termo de Confissão de Divida:</strong></td>
					<td width="74%"><html:select property="resolucaoDiretoriaLayout">
						<option value="">&nbsp;</option>
						<html:options name="request" collection="colecaoResolucaoDiretoriaLayout" labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="50%"><strong>Uso da RD é único por parcelamento/imóvel?</strong></td>
					<td><html:radio property="indicadorUsoRDParcImovel" tabindex="6" value="1"/>Sim
						<html:radio	property="indicadorUsoRDParcImovel" tabindex="7" value="2"/>Não
						<html:radio	property="indicadorUsoRDParcImovel" tabindex="8" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>Uso da RD é permitido para todos os usuários?</strong></td>
					<td><html:radio property="indicadorUsoRDUsuarios" tabindex="9" value="1"/>Sim
						<html:radio	property="indicadorUsoRDUsuarios" tabindex="10" value="2"/>Não
						<html:radio	property="indicadorUsoRDUsuarios" tabindex="11" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>RD permite desconto nos débitos a cobrar referente a sanções e consumo fraudado? </strong></td>
					<td><html:radio property="indicadorUsoRDDebitoCobrar" tabindex="12" value="1"/>Sim
						<html:radio	property="indicadorUsoRDDebitoCobrar" tabindex="13" value="2"/>Não
						<html:radio	property="indicadorUsoRDDebitoCobrar" tabindex="14" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>RD emite o assunto da resolução de diretoria nas contas?</strong></td>
					<td><html:radio property="indicadorEmissaoAssuntoConta" tabindex="15" value="1"/>Sim
						<html:radio	property="indicadorEmissaoAssuntoConta" tabindex="16" value="2"/>Não
						<html:radio	property="indicadorEmissaoAssuntoConta" tabindex="17" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>A média da atualização monetária dos últimos 12 (doze) meses deve ser acrescida à taxa de juros do parcelamento?</strong></td>
					<td><html:radio property="indicadorTrataMediaAtualizacaoMonetaria" tabindex="18" value="1"/>Sim
						<html:radio	property="indicadorTrataMediaAtualizacaoMonetaria" tabindex="19" value="2"/>Não
						<html:radio	property="indicadorTrataMediaAtualizacaoMonetaria" tabindex="20" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>RD permite cobrança de descontos concedidos no parcelamento?</strong></td>
					<td><html:radio property="indicadorCobrarDescontosArrasto" tabindex="21" value="1"/>Sim
						<html:radio	property="indicadorCobrarDescontosArrasto" tabindex="22" value="2"/>Não
						<html:radio	property="indicadorCobrarDescontosArrasto" tabindex="23" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>Realiza arrasto?</strong></td>
					<td><html:radio property="indicadorArrasto" tabindex="24" value="1"/>Sim
						<html:radio	property="indicadorArrasto" tabindex="25" value="2"/>Não
						<html:radio	property="indicadorArrasto" tabindex="26" value=""/>Todas
					</td>
				</tr>
				<tr>
					<td width="26%"><strong>Grupo de Acesso:</strong></td>
					<td width="74%"><html:select property="grupo">
						<option value="">&nbsp;</option>
						<html:options name="request" collection="colecaoGrupo" labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="26%"><strong>Localidade:</strong></td>
					
					<td colspan="2" width="74%">
						
						<html:text maxlength="3" 
							tabindex="6"
							property="idLocalidade" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarResolucaoDiretoriaAction.do?objetoConsulta=1','idLocalidade','Localidade');"/>
							
						<a href="javascript: chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 250, 495, '', document.forms[0].idLocalidade);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Localidade" /></a> 			
	
						<logic:present name="idLocalidadeEncontrado">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
	
						<logic:notPresent name="idLocalidadeEncontrado">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>			
					</td>
				</tr>
				<tr>
					<td>
						<strong>Período de Referência do Débito:</strong>
					</td>
					<td>
						<span class="style2">
							<html:text maxlength="7"
								property="anoMesReferenciaDebitoInicio" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);replicarCampo(document.forms[0].anoMesReferenciaDebitoFim, document.forms[0].anoMesReferenciaDebitoInicio);"
								tabindex="7" /> &nbsp; 
							<strong>a</strong> &nbsp; 
							<html:text maxlength="7"
								property="anoMesReferenciaDebitoFim" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);" 
								tabindex="8" />
							</strong>(mm/aaaa)<strong>
						</span>
					</td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td><input type="button" name="ButtonReset" class="bottonRightCol"
						value="Limpar" onclick="javascript:limparForm(document.forms[0]);">
						
					<td colspan="2" align="right">
 					  <gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validarForm(document.forms[0]);" url="filtrarResolucaoDiretoriaAction.do"/>
					  <%-- <input type="button" name="Button" class="bottonRightCol" value="Filtrar" onClick="javascript:validarForm(document.forms[0]);" /> --%>
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
