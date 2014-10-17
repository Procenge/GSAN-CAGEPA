<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirConsumoTarifaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

function abrirCategoria(){
  	var form = document.InserirConsumoTarifaActionForm;
  	if ((form.descTarifa.value == '') && (form.slcDescTarifa[form.slcDescTarifa.selectedIndex].value == '-1')){
  		if (form.dataVigencia.value =='') {
	  		alert ('Informe Tarifa de Consumo.\nInforme Data de Vigência.');
	  		form.descTarifa.focus();
  		} else{
	  		alert ('Informe Tarifa de Consumo.');
	 		form.descTarifa.focus();
  		}
  	} else {
  		if (form.dataVigencia.value =='') {
	  		alert ('Informe Data de Vigência.');
	  		form.dataVigencia.focus();
  		} else {
  			if (validateCaracterEspecial(form) && validateDate(form)){
  				var valorInTarifaEsgotoPropria;
  				
  				if(form.inTarifaEsgotoPropria[0].checked){
  					valorInTarifaEsgotoPropria = 1
  				} else {
  					valorInTarifaEsgotoPropria = 2
  				}
  				window.open('exibirInserirCategoriaConsumoTarifaAction.do?limpa=1&parametroVigencia='+form.dataVigencia.value+'&parametroDescricao='+form.descTarifa.value+'&indicadorTarifaEsgotoPropria='+valorInTarifaEsgotoPropria+'&parametroSelect='+form.slcDescTarifa.value, 'pesquisar','location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=630,height=400');
  			}
  		}
  	}

 }

function reload() {
	alert("reload");
   	redirecionarSubmit("exibirInserirConsumoTarifaAction.do");
      
 }

  function desabilitaCampoTarifa(){
    var form = document.InserirConsumoTarifaActionForm;
    if (form.descTarifa.value != ''){
      form.slcDescTarifa.value = -1;	
      form.slcDescTarifa.disabled = true;
    }else{
      form.slcDescTarifa.disabled = false;
    }
  }


  
  function verficaConsumoExistente(){
  	var form = document.InserirConsumoTarifaActionForm;
  	colecaoConsumoTarifa = form.slcDescTarifa;
  	cont = 0;
  	
  	while (colecaoConsumoTarifa.options.length > cont){
  		if (trim(colecaoConsumoTarifa.options[cont].innerHTML) == trim(form.descTarifa.value.toUpperCase())){
  			alert ('Tarifa de Consumo já existe. Selecionar a descrição da lista ao lado.');
  			form.descTarifa.value = "";
  			colecaoConsumoTarifa.disabled = false;
  			colecaoConsumoTarifa.focus();		
  			break;
  		}
  		cont++;
  	}
  }
  
 function verificaDescricao(){
 	var form = document.InserirConsumoTarifaActionForm;
 	
 	if (form.descTarifa.value != ""){
 		form.slcDescTarifa.disabled = true;
 	} else {
 		form.slcDescTarifa.disabled = false;
 	}
 }
  
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.descTarifa}');">
<html:form action="/inserirConsumoTarifaAction"
	name="InserirConsumoTarifaActionForm"
	onsubmit="return validateInserirConsumoTarifaActionForm(this);"
	type="gcom.gui.faturamento.consumotarifa.InserirConsumoTarifaActionForm"
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
					<td class="parabg">Inserir Tarifa de Consumo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%">
				<tr>
					<td colspan="3">Para inserir a tarifa de consumo, informe os dados
					abaixo:</td>
					<td align="right"></td>
				</tr>
				</table>
			<table width="100%">
				<tr>
					<td width="184" class="style1"><strong>Descri&ccedil;&atilde;o da Tarifa:<font
						color="#FF0000">*</font></strong></td>
					<td width="210" class="style1"><html:text maxlength="30"
						property="descTarifa" size="30" value="${sessionScope.descricao}"
						onkeyup="javaScript:desabilitaCampoTarifa();"
						onblur="verficaConsumoExistente();" /></td>
					<td width="212" class="style1"><html:select
						property="slcDescTarifa" style="width: 230px;" value="${sessionScope.select}">
						<logic:present name="colecaoConsumoTarifa">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>" >&nbsp; </html:option>
							<html:options collection="colecaoConsumoTarifa" 
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td class="style1"><strong>Data de Vig&ecirc;ncia:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2" class="style1"><html:text maxlength="10"
						property="dataVigencia" size="10" value="${sessionScope.vigencia}" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('InserirConsumoTarifaActionForm', 'dataVigencia')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <font size="1">(dd/mm/aaaa)</font></td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Tarifa de Esgoto Própria:<font color="#FF0000">*</font></strong></td>
					<td width="70%"><span class="style1">
					<input type="radio" name="inTarifaEsgotoPropria" value="1"><strong>SIM</strong>
					<input type="radio" name="inTarifaEsgotoPropria" value="2" checked><strong>NÃO</strong>
					</span></td>
				</tr>
				
				<tr>
					<td width="27%" height="24"><strong>Tipo de Cálculo:<font color="#FF0000">*</font></strong></td>
					<td colspan="3"><html:select property="slcCalculoTipo"
						value="${sessionScope.calculoTipoSelected}" style="width: 230px;">
							<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
			    		<html:options collection="colecaoCalculoTipo" labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td class="style1" colspan="2"><strong>Categorias e Economias:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2" class="style1">
					<div align="right"><input type="button" name="adicionar2"
						class="bottonRightCol" value="Adicionar"
						onClick="abrirCategoria();">
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="5">
					<table width="100%" bgcolor="#99CCFF">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">
							<td width="9%">
							<div align="center" class="style9"><strong>Remover</strong></div>
							</td>
							<td width="34%">
							<div align="center" class="style9"><strong>Categoria</strong></div>
							</td>
							<td width="19%">
							<div align="center" class="style9"><strong>Consumo M&iacute;nimo
							</strong></div>
							</td>
							<td width="23%">
							<div align="center"><strong>Tarifa M&iacute;nima</strong></div>
							</td>
							<td width="15%">
							<div align="center"><strong>Qtd. Faixas</strong></div>
							</td>
						</tr>
						<%String cor = "#FFFFFF";%>
						<logic:present name="colecaoConsumoTarifaCategoria"
							scope="session">
							<logic:iterate indexId="posicao"
								name="colecaoConsumoTarifaCategoria" id="helper" >

								<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#FFFFFF";%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td>
									<div align="center" class="style9"><img
										src="<bean:message key='caminho.imagens'/>Error.gif"
										width="14" height="14" style="cursor:hand;"
										onclick="redirecionarSubmit('excluirCategoriaConsumoTarifaAction.do?posicao=<bean:write name="helper" property="consumoTarifaCategoria.ultimaAlteracao.time" />');"></div>
									</td>
									<td>
									<div align="center" class="style9"><bean:write
										name="helper" property="consumoTarifaCategoria.categoria.descricao" /></div>
									</td>

									<td>
									<div align="center" class="style9"><INPUT type="text"
										maxlength="9" size="9" disabled="disabled" 
										name="ValorConMinimo.<bean:write
										name="helper" property="consumoTarifaCategoria.categoria.descricao" />"
										value="<bean:write
										name="helper" property="consumoTarifaCategoria.numeroConsumoMinimo" />"></div>
									</td>
									<td>
									<div align="center" class="style9"><INPUT type="text" style="text-align:right;" size="18"
										disabled="disabled"
										maxlength="18"
										name="ValorTarMin.<bean:write
										name="helper" property="consumoTarifaCategoria.categoria.descricao" />"
										value="<bean:write
										name="helper" property="consumoTarifaCategoria.valorTarifaMinima" formatKey="money.quatrodecimais.format"/>"></div>
									</td>
									<td>
									<div align="center" class="style9"><bean:write
										name="helper" property="quantidadesFaixa" /></div>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" class="style1">
					
					</td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="3"><strong> <font color="#ff0000"> <input name="Submit22"
						class="bottonRightCol" value="Desfazer" type="button"
						onclick="window.location.href='/gsan/exibirInserirConsumoTarifaAction.do?menu=sim';">

					<input name="Submit23" class="bottonRightCol" value="Cancelar"
						type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"> </font></strong></td>
					
					<td class="style1" align="right">
					<input type="button" class="bottonRightCol" value="Inserir"
						onclick="javascript:if(validateInserirConsumoTarifaActionForm(document.forms[0])){submeterFormPadrao(document.forms[0]);}">
					</td>
				</tr>
			</table>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	</html:form>
</body>

<script language="JavaScript">
<!-- Begin
	verificaDescricao();
-->
</script>

</html:html>
