<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirLocalidadeDadoOperacionalActionForm" />


 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

	function validaForm() {
	  	var form = document.forms[0];
	 	if(validateInserirLocalidadeDadoOperacionalActionForm(form) && validarCampos()){
   			submeterFormPadrao(form);
	 	}
	}

	function limparPesquisaDescricaoLocalidade() {
	    var form = document.forms[0];
	    form.descricaoLocalidade.value = "";
	 }

	function limparPesquisaLocalidade() {
	  var form = document.forms[0];
      form.idLocalidade.value = "";
      form.descricaoLocalidade.value = "";
	}

	//Recebe os dados do(s) popup(s)
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	  var form = document.forms[0];

	  if (tipoConsulta == 'localidade') {
	    limparPesquisaLocalidade();
	    form.idLocalidade.value = codigoRegistro;
	    form.descricaoLocalidade.value = descricaoRegistro;
	    form.descricaoLocalidade.style.color = "#000000";
	  }
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];

		var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
		var REFERENCIA_I = form.mesAnoReferencia.value.substring(3,7) + form.mesAnoReferencia.value.substring(0,2);
		var REFERENCIA_A = DATA_ATUAL.substring(6,10) + DATA_ATUAL.substring(3,5);
		var MES_ANO_C = DATA_ATUAL.substring(3,5) + DATA_ATUAL.substring(5,6) + DATA_ATUAL.substring(6,10);

		if (REFERENCIA_A < REFERENCIA_I){
		    alert("Referência informada posterior ao Mês e Ano corrente " + MES_ANO_C + ".");
		    return false;
	    }
		
		return retorno;
	}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/inserirLocalidadeDadoOperacionalAction" enctype="multipart/form-data" method="post" focus="idLocalidade">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="150" valign="top" class="leftcoltext">
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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Dados Operacionais da Localidade</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para adicionar um  dados operacionais da localidade, informe os dados
					abaixo:</td>
				</tr>
				<tr>
					<td><strong>Refer&ecirc;ncia: <font	color="#FF0000">*</font></strong></td>
					<td><INPUT TYPE="hidden" ID="DATA_ATUAL"	value="${requestScope.dataAtual}" />
						<html:text property="mesAnoReferencia" size="8" maxlength="7" onblur="verificaAnoMesNovo(this);" onkeyup="mascaraAnoMes(this, event);" onkeypress="return isCampoNumerico(event);"/>&nbsp;mm/aaaa</td>
				</tr>
				<tr>
		   			<td width="30%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
                    <td width="70%" height="24" colspan="2">
                   		<html:text maxlength="3" property="idLocalidade" size="3" onkeypress="javascript:limparPesquisaDescricaoLocalidade(); return validaEnter(event, 'exibirInserirLocalidadeDadoOperacionalAction.do', 'idLocalidade');"/>
                      	<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 400, 800);">
                        	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
                        </a>
   		      			<logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
							<input type="text" name="descricaoLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.localidade.inexistente"/>"/>
                   		</logic:present>
						<logic:notPresent name="codigoLocalidadeNaoEncontrada" scope="request">
							<html:text property="descricaoLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						<a href="javascript:limparPesquisaLocalidade();document.forms[0].idLocalidade.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
                 </tr>
				
				<tr>
					<td><strong>Volume Produzido:</strong></td>
					<td><html:text property="volumeProduzido" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;m3</td>
				</tr>
				<tr>
					<td><strong>Extensão da Rede de &Aacute;gua:</strong></td>
					<td><html:text property="extensaoRedeAgua" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;Km</td>
				</tr>
				<tr>
					<td><strong>Extensão da Rede de Esgoto:</strong></td>
					<td><html:text property="extensaoRedeEsgoto" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;Km</td>
				</tr>
				<tr>
					<td><strong>Num. Funcion&aacute;rios da Adm.:</strong></td>
					<td><html:text property="qtdFuncionariosAdministracao" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
				</tr>
				<tr>
					<td><strong>Num. Funcion&aacute;rios da Adm. Terceiros:</strong></td>
					<td><html:text property="qtdFuncionariosAdministracaoTercerizados" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
				</tr>
				<tr>
					<td><strong>Num. Funcion&aacute;rios da Oper.:</strong></td>
					<td><html:text property="qtdFuncionariosOperacao" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
				</tr>
				<tr>
					<td><strong>Num. Funcion&aacute;rios da Oper. Terceiros:</strong></td>
					<td><html:text property="qtdFuncionariosOperacaoTercerizados" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
				</tr>
				<tr>
					<td><strong>Qtd. Sulfato de Aluminio:</strong></td>
					<td><html:text property="qtdSulfatoAluminio" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
				</tr>
				<tr>
					<td><strong>Qtd. Cloro Gasoso:</strong></td>
					<td><html:text property="qtdCloroGasoso" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
				</tr>
				<tr>
					<td><strong>Qtd. Hipocl. de Sodio:</strong></td>
					<td><html:text property="qtdHipocloritoSodio" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
				</tr>
				<tr>
					<td><strong>Qtd. Fluor:</strong></td>
					<td><html:text property="quantidadeFluor" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
				</tr>
				<tr>
					<td><strong>Consumo de Energia:</strong></td>
					<td><html:text property="qtdConsumoEnergia" maxlength="12" size="12" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"/>&nbsp;Kwh</td>
				</tr>
				<tr>
					<td><strong>Qtd. Horas Paralisadas:</strong></td>
					<td><html:text property="qtdHorasParalisadas" maxlength="12" size="12" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"/></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"> <input
						name="btLimpar" class="bottonRightCol" value="Limpar"
						type="button"
						onclick="window.location.href='/gsan/exibirInserirLocalidadeDadoOperacionalAction.do?menu=sim';">
					<input type="button" name="Button" class="bottonRightCol"
						value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /> </font></strong></td>
					<td align="right"><input type="button" onclick="javascript:validaForm();" name="botaoInserir"
						class="bottonRightCol" value="Inserir"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>