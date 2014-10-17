<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
<!--

var bCancel = false; 

function limpaImovel () {
	window.location.href='/gsan/exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?menu=sim';
}

function controlarCamposCadastroOcorrencia(){
    var form = document.forms[0];
    
    if (form.cadastroOcorrencia.value == ""){
		form.dataOcorrenciaCadastro.value = "";
		form.uploadPictureCadastro.value = "";
		form.idFuncionarioOcorrencia.value = "";
		form.dataOcorrenciaCadastro.disabled = true;
		form.uploadPictureCadastro.disabled = true;
		form.idFuncionarioOcorrencia.disabled = true;
		
		if (form.descricaoFuncionarioOcorrencia != null){
		  form.descricaoFuncionarioOcorrencia.value = "";
		}
    } else {
    	form.dataOcorrenciaCadastro.disabled = false;
		form.uploadPictureCadastro.disabled = false;
		form.idFuncionarioOcorrencia.disabled = false;
		
		form.dataOcorrenciaCadastro.focus();
    }
}

var bCancel = false;

function validateDate(form) {
       var bValid = true;
       var focusField = null;
       var i = 0;
       var fields = new Array();
       oDate = new DateValidations();
       for (x in oDate) {
           var value = form[oDate[x][0]].value;
           var datePattern = oDate[x][2]("datePattern");
           if ((form[oDate[x][0]].type == 'text' ||
                form[oDate[x][0]].type == 'textarea') &&
               (value.length > 0) &&
               (datePattern.length > 0)) {
             var MONTH = "MM";
             var DAY = "dd";
             var YEAR = "yyyy";
             var orderMonth = datePattern.indexOf(MONTH);
             var orderDay = datePattern.indexOf(DAY);
             var orderYear = datePattern.indexOf(YEAR);
             if ((orderDay < orderYear && orderDay > orderMonth)) {
                 var iDelim1 = orderMonth + MONTH.length;
                 var iDelim2 = orderDay + DAY.length;
                 var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                 var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                 if (iDelim1 == orderDay && iDelim2 == orderYear) {
                    dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
                 } else if (iDelim1 == orderDay) {
                    dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
                 } else if (iDelim2 == orderYear) {
                    dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
                 } else {
                    dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
                 }
                 var matched = dateRegexp.exec(value);
                 if(matched != null) {
                    if (!isValidDate(matched[2], matched[1], matched[3])) {
                       if (i == 0) {
                           focusField = form[oDate[x][0]];
                       }
                       fields[i++] = oDate[x][1];
                       bValid =  false;
                    }
                 } else {
                    if (i == 0) {
                        focusField = form[oDate[x][0]];
                    }
                    fields[i++] = oDate[x][1];
                    bValid =  false;
                 }
             } else if ((orderMonth < orderYear && orderMonth > orderDay)) {
                 var iDelim1 = orderDay + DAY.length;
                 var iDelim2 = orderMonth + MONTH.length;
                 var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                 var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                 if (iDelim1 == orderMonth && iDelim2 == orderYear) {
                     dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
                 } else if (iDelim1 == orderMonth) {
                     dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
                 } else if (iDelim2 == orderYear) {
                     dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
                 } else {
                     dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
                 }
                 var matched = dateRegexp.exec(value);
                 if(matched != null) {
                     if (!isValidDate(matched[1], matched[2], matched[3])) {
                         if (i == 0) {
                             focusField = form[oDate[x][0]];
                         }
                         fields[i++] = oDate[x][1];
                         bValid =  false;
                      }
                 } else {
                     if (i == 0) {
                         focusField = form[oDate[x][0]];
                     }
                     fields[i++] = oDate[x][1];
                     bValid =  false;
                 }
             } else if ((orderMonth > orderYear && orderMonth < orderDay)) {
                 var iDelim1 = orderYear + YEAR.length;
                 var iDelim2 = orderMonth + MONTH.length;
                 var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                 var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                 if (iDelim1 == orderMonth && iDelim2 == orderDay) {
                     dateRegexp = new RegExp("^(\\d{4})(\\d{2})(\\d{2})$");
                 } else if (iDelim1 == orderMonth) {
                     dateRegexp = new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$");
                 } else if (iDelim2 == orderDay) {
                     dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$");
                 } else {
                     dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$");
                 }
                 var matched = dateRegexp.exec(value);
                 if(matched != null) {
                     if (!isValidDate(matched[3], matched[2], matched[1])) {
                         if (i == 0) {
                             focusField = form[oDate[x][0]];
                          }
                          fields[i++] = oDate[x][1];
                          bValid =  false;
                      }
                  } else {
                      if (i == 0) {
                          focusField = form[oDate[x][0]];
                      }
                      fields[i++] = oDate[x][1];
                      bValid =  false;
                  }
             } else {
                 if (i == 0) {
                     focusField = form[oDate[x][0]];
                 }
                 fields[i++] = oDate[x][1];
                 bValid =  false;
             }
          }
       }
       if (fields.length > 0) {
          focusField.focus();
          alert(fields.join('\n'));
       }
       return bValid;
    }
    
     function isValidDate(day, month, year) {
	        if (month < 1 || month > 12) {
                    return false;
                }
                if (day < 1 || day > 31) {
                    return false;
                }
                if ((month == 4 || month == 6 || month == 9 || month == 11) &&
                    (day == 31)) {
                    return false;
                }
                if (month == 2) {
                    var leap = (year % 4 == 0 &&
                               (year % 100 != 0 || year % 400 == 0));
                    if (day>29 || (day == 29 && !leap)) {
                        return false;
                    }
                }
                return true;
            }


function controlarCamposEloAnormalidade(){
    var form = document.forms[0];
    
    if (form.eloAnormalidade.value == ""){
		form.dataAnormalidadeElo.value = "";
		form.uploadPictureAnormalidade.value = "";
		form.idFuncionarioAnormalidade.value = "";
		form.dataAnormalidadeElo.disabled = true;
		form.uploadPictureAnormalidade.disabled = true;
		form.idFuncionarioAnormalidade.disabled = true;
		
		if (form.descricaoFuncionarioAnormalidade != null){
		  form.descricaoFuncionarioAnormalidade.value = "";
		}
    } else {
    	form.dataAnormalidadeElo.disabled = false;
		form.uploadPictureAnormalidade.disabled = false;
		form.idFuncionarioAnormalidade.disabled = false;
		
		form.dataAnormalidadeElo.focus();
    }
}

function DateValidations () { 
     this.aa = new Array("dataOcorrenciaCadastro", "Data da Ocorrência inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("dataAnormalidadeElo", "Data da Anormalidade inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
} 

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovel.value = codigoRegistro;
	  form.action = 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=1';      
	  form.submit();
    }
    if (tipoConsulta == 'funcionario') {
    	
      if (document.getElementById("tipoPesquisaFuncionario").value == 'ocorrencia'){
      	form.idFuncionarioOcorrencia.value = codigoRegistro;
	  	form.descricaoFuncionarioOcorrencia.value = descricaoRegistro;
	  	form.descricaoFuncionarioOcorrencia.style.color = "#000000";
	  }else{
	  	form.idFuncionarioAnormalidade.value = codigoRegistro;
	  	form.descricaoFuncionarioAnormalidade.value = descricaoRegistro;
	  	form.descricaoFuncionarioAnormalidade.style.color = "#000000"
	  }	
    }
 }

function setarChamadaFuncionario(tipoPesquisa){
	
	if (tipoPesquisa != null){
		document.getElementById("tipoPesquisaFuncionario").value = tipoPesquisa;
	}			
}
 
function limpar(){

	var form = document.forms[0];
	
	form.idFuncionarioOcorrencia.value = "";
	form.descricaoFuncionarioOcorrencia.value = "";
	form.idFuncionarioAnormalidade.value = "";
	form.descricaoFuncionarioAnormalidade.value = "";
			   
} 
 
 function validarForm(form){
 
 	if (form.idImovel.value == ""){
 		alert ("Informe Imóvel.")
 	} else {
 	 	if (form.cadastroOcorrencia.value != '') {
			if (form.dataOcorrenciaCadastro.value == '' && form.idFuncionarioOcorrencia.value == '') {
				alert('Informe a Data e o Funcionário da Ocorrência');
				return;
			} else {
				if (form.dataOcorrenciaCadastro.value == '') {
					alert('Informe a Data da Ocorrência');
					return;
				}
				if (form.idFuncionarioOcorrencia.value == '') {
					alert('Informe o Funcionário da Ocorrência');
					return;
				}
			}
 	 	}
 	 	if (form.eloAnormalidade.value != '') {
 	 		if (form.dataAnormalidadeElo.value == '' && form.idFuncionarioAnormalidade.value == '') {
 	 			alert('Informe a Data e o Funcionário da Anormalidade');
 	 			return;
 	 		} else {
	 	 	 	
				if (form.dataAnormalidadeElo.value == '') {
					alert('Informe a Data da Anormalidade');
					return;
				}
				if (form.idFuncionarioAnormalidade.value == '') {
					alert('Informe o Funcionário da Anormalidade');
					return;
				}
 	 		}
 	 	}
 		if (validateDate(form)){
	 		form.action = 'informarOcorrenciaCadastroAnormalidadeEloAction.do';
	 		form.submit();
 		}
 	}
 }

-->
</script>

</head>
<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<form name="informar" 
	method="post" enctype="multipart/form-data"><%@ include
	file="/jsp/util/cabecalho.jsp"%> <%@ include file="/jsp/util/menu.jsp"%>

<input type="hidden" id="tipoPesquisaFuncionario" name="tipoPesquisaFuncionario" value="ocorrencia" />	

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
		<!--Início Tabela Reference a Páginação da Tela de Processo-->
		<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>

				<td class="parabg">Informar Ocorr&ecirc;ncia/Anormalidade do Imóvel</td>

				<td width="11" valign="top"><img border="0"
					src="imagens/parahead_right.gif" /></td>

			</tr>
		</table>
		<!--Fim Tabela Reference a Páginação da Tela de Processo-->
		<p>&nbsp;</p>
		<table width="100%" border="0">
			<tr>
				<td colspan="4">
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">

					<tr>
						<td align="center"><strong>Dados do Imóvel</strong></td>
					</tr>
					<tr bgcolor="#cbe5fe">
						<td width="100%" align="center">
						<table width="100%" border="0">
							<tr>
								<td bordercolor="#000000"><strong>Im&oacute;vel:<font
									color="#FF0000">*</font></strong></td>
								
								<td colspan="3" bordercolor="#000000">
								<logic:present name="inexistente" scope="request">
								<input type="text"
									name="idImovel" maxlength="9" size="10" tabindex="1" value=""
									onkeypress="javascript:validaEnterComMensagem(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=1', 'idImovel','Imovél');">
								</logic:present>
								<logic:notPresent name="inexistente" scope="request">
									<input type="text"
									name="idImovel" value="${requestScope.imovel.idImovel}" maxlength="10" size="10" tabindex="1" value=""
									onkeypress="javascript:validaEnterComMensagem(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=1', 'idImovel','Imovél');">
								</logic:notPresent>
								<a
									href="javascript:abrirPopup('exibirPesquisarImovelAction.do');">
								<img width="23" height="21" border="0"
									src="imagens/pesquisa.gif" title="Pesquisar Imovél" /></a> 
									<logic:present name="inexistente" scope="request">
									<input
									type="text" name="matriculaImovel" value="IMÓVEL INEXISTENTE" maxlength="25" size="25"
									value="" readonly="readonly"
 									style="background-color:#EFEFEF; border:0; color: #ff0000">
									</logic:present>
									<logic:notPresent name="inexistente" scope="request">
									<input
									type="text" name="matriculaImovel" value="${requestScope.imovel.matriculaImovel}" maxlength="25" size="25"
									value="" readonly="readonly"
 									style="background-color:#EFEFEF; border:0; color: #000000">
									</logic:notPresent>
									<a
									href="javascript:limpaImovel();"><img
									src="imagens/limparcampo.gif" border="0" height="21" width="23"></a>
								</td>
							</tr>
							<tr>
								<td height="10">
								<div class="style9"><strong>Situação de Água:</strong></div>
								</td>
								<td><input name="ligacaoAgua" type="text" value="${requestScope.imovel.situacaoAgua}"
									style="background-color:#EFEFEF; border:0; font-size:9px"
									value="" size="15" maxlength="15" readonly="readonly"></td>

								<td width="146"><strong>Situação de Esgoto:</strong></td>
								<td width="123"><input name="ligacaoEsgoto" type="text" value="${requestScope.imovel.situacaoEsgoto}"
									style="background-color:#EFEFEF; border:0; font-size:9px"
									value="" size="15" maxlength="15" readonly="readonly"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>

			</tr>

			<tr>
				<td colspan="4">
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
					<tr>
						<td align="center">
						<div class="style9"><strong>Endere&ccedil;o </strong></div>

						</td>
					</tr>
					<tr bgcolor="#cbe5fe">
						<logic:present name="imovel" scope="request">
						<td align="center" bgcolor="#FFFFFF">
						<div class="style9">${requestScope.imovel.enderecoImovel}</div>
						</td>
						</logic:present>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="100%" border="0">

					<tr>
						<td colspan="4">
						<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bordercolor="#79bbfd">
								<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Ocorr&ecirc;ncias
								de Cadastro</strong></td>
							</tr>
							<tr bordercolor="#000000">
								<td width="48%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Ocorr&ecirc;ncia</strong></font>
								</div>
								</td>

								<td width="17%" align="center" bgcolor="#90c7fc">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data </strong></font></div>
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
								</td>
								<td width="17%" align="center" bgcolor="#90c7fc">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Funcionário </strong></font></div>
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
								</td>								
								<td width="18%" align="center" bgcolor="#90c7fc"><span
									class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"><strong>Foto</strong></font></span></td>
							</tr>
							<%String cor = "#FFFFFF";%>
							<logic:present name="cadastroOcorrencia" scope="request">
								<logic:iterate name="cadastroOcorrencia" id="cad1">
										<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">
										
											<%} else {
				cor = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
								<td bordercolor="#90c7fc" align="left">
								<bean:write name="cad1" property="cadastroOcorrencia.descricao"/>
								</td>
								<td align="center">
								<bean:write name="cad1" property="dataOcorrencia" formatKey="date.format"/>
								</td>
								<td align="center">
								  &nbsp;
								 <logic:notEmpty name="cad1" property="funcionario">
									<bean:write name="cad1" property="funcionario.nome" />
								</logic:notEmpty>									
								</td>
								<td align="center">
								<logic:notEmpty name="cad1" property="fotoOcorrencia">
								<a href="javascript:abrirPopup('exibirFotoOcorrenciaCadastroAction.do?id=<bean:write name="cad1" property="id"/>',  600, 800)">
								<input name="imageField" type="image"
									src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled"> </a>
								</logic:notEmpty>
								</td>
							</tr>
							</logic:iterate>
							</logic:present>
						</table>
						</td>

					</tr>
					<tr>
						<td colspan="4">
						<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bordercolor="#79bbfd">
								<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Anormalidades
								de Elo </strong></td>
							</tr>
							<tr bordercolor="#000000">
								<td width="48%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Anormalidade</strong></font></div>
								</td>

								<td width="17%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Data </strong>
								</font></div>
								</td>
								<td width="17%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Funcionário </strong>
								</font></div>
								</td>
								<td width="18%" bgcolor="#90c7fc" align="center"><span
									class="style9"> <font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Foto </strong>
								</font> </span></td>
							</tr>
							<%String cor2 = "#FFFFFF";%>
							<logic:present name="eloAnormalidade" scope="request">
								<logic:iterate name="eloAnormalidade" id="elo1">
										<%if (cor2.equalsIgnoreCase("#FFFFFF")) {
				cor2 = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">
										
											<%} else {
				cor2 = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
								<td align="left"><bean:write name="elo1" property="eloAnormalidade.descricao"/></td>
								<td align="center">
								<bean:write name="elo1" property="dataAnormalidade" formatKey="date.format"/>
								</td>
								<td align="center">
								 &nbsp;
								 <logic:notEmpty name="elo1" property="funcionario">
									<bean:write name="elo1" property="funcionario.nome" />
								</logic:notEmpty>
								</td>
								<td align="center">
								<logic:notEmpty name="elo1" property="fotoAnormalidade">
								<a href="javascript:abrirPopup('exibirFotoAnormalidadeEloAction.do?id=<bean:write name="elo1" property="id"/>', 600, 800)">
								<input name="imageField" type="image"
									src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled"></a>
								</logic:notEmpty>
								</td>
							</tr>
							</logic:iterate>
							</logic:present>
						</table>
						</td>
					</tr>
					<tr>
						<td height="10" colspan="4" align="left">&nbsp;</td>
					</tr>
					<tr>
						<td height="10" colspan="4" align="left">
							Para informar uma Ocorr&ecirc;ncia de Imóvel
						preencha os dados abaixo:
						</td>
					</tr>
					<tr>
						<td width="27%" align="left"><strong> Ocorr&ecirc;ncia: </strong>
						</td>
						<td colspan="3" align="left">
						<select name="cadastroOcorrencia"
						style="width: 250px;" onchange="javascript:controlarCamposCadastroOcorrencia();" tabindex="2">
						<logic:present name="cadastro">
							<option value="">&nbsp;</option>
							
							<logic:present name="idOcorrenciaCadastro">
								<bean:define name="idOcorrenciaCadastro" id="idOcorrencia" />
								<%String ocorrencia = (String) idOcorrencia; %>
								
								<logic:iterate name="cadastro" scope="session" id="cad">;
									<bean:define name="cad" property="id" id="idOptionOcorrencia"/>
									<% Integer option = (Integer) idOptionOcorrencia;
			
									if (ocorrencia.equals(option.toString())){
									%>
									<option value="${cad.id}" selected="selected">${cad.descricaoComId}</option>
									<%}else{ %>
									<option value="${cad.id}">${cad.descricaoComId}</option>
									<%} %>	
								</logic:iterate>
							</logic:present>
							
							<logic:notPresent name="idOcorrenciaCadastro">
							<logic:iterate name="cadastro" scope="session" id="cad">;
								<option value="${cad.id}">${cad.descricaoComId}</option>
							</logic:iterate>
							</logic:notPresent>
						</logic:present>
					</select></td>
					</tr>
					<tr>
						<td height="10">
						<strong> Data da Ocorr&ecirc;ncia: </strong>
						</td>
						<td colspan="3">
							<logic:notPresent name="dataOcorrenciaCadastro">
								<input name="dataOcorrenciaCadastro" maxlength="10" type="text" size="10" onkeyup="mascaraData(this, event);" tabindex="3"/>
							</logic:notPresent>	 
							
							<logic:present name="dataOcorrenciaCadastro">
								<input name="dataOcorrenciaCadastro" maxlength="10" type="text" size="10" onkeyup="mascaraData(this, event);" tabindex="3" value='<bean:write name="dataOcorrenciaCadastro" />'/>
							</logic:present>
							
							<a href="javascript:abrirCalendario('informar', 'dataOcorrenciaCadastro')">
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> dd/mm/aaaa
						</td>
					</tr>
					<tr>
						<td height="10">
							<strong>Funcionário:</strong>
						</td>
						<td colspan="3">
							<logic:notPresent name="idFuncionarioOcorrencia">
								<input name="idFuncionarioOcorrencia" size="10" maxlength="9" tabindex="4" 
						onkeypress="validaEnterString(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=2', 'idFuncionarioOcorrencia');" />
							</logic:notPresent>
							<logic:present name="idFuncionarioOcorrencia">
								<input name="idFuncionarioOcorrencia" size="10" maxlength="9" tabindex="4" value='<bean:write name="idFuncionarioOcorrencia" />' 
						onkeypress="validaEnterString(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=2', 'idFuncionarioOcorrencia');" />
							</logic:present>
							
						
						<a 	href="javascript:abrirPopup('exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?pesquisarFuncionario=OK',400,400);setarChamadaFuncionario('ocorrencia');">
						<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
							border="0" height="21" width="23"></a> 
							<logic:present name="descricaoFuncionarioOcorrencia">		
							
								<logic:present name="exception">
									<input name="descricaoFuncionarioOcorrencia" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #FF0000" value='<bean:write name="descricaoFuncionarioOcorrencia" />' />								
								</logic:present>	
								<logic:notPresent name="exception">
									<input name="descricaoFuncionarioOcorrencia" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" value='<bean:write name="descricaoFuncionarioOcorrencia" />' />
								</logic:notPresent>				
															
							</logic:present> 
						<logic:notPresent name="descricaoFuncionarioOcorrencia">
							
							<input name="descricaoFuncionarioOcorrencia" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" value="" />
														
							</logic:notPresent> 							
						</td>
				</tr>
					<tr>
						<td height="10" align="left">
						<strong> Foto da Ocorr&ecirc;ncia: </strong>
						</td>
						<td colspan="3" align="left">							
								<input type="file" style="textbox" name="uploadPictureCadastro" size="50" tabindex="5" value="teste"/>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="left">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="left">Para informar
						uma Anormalidade de Imóvel preencha os dados abaixo:</td>
					</tr>
					<tr>
						<td align="left"><strong> Anormalidade: </strong></td>
						<td colspan="3" align="left">
						<select name="eloAnormalidade"
						style="width: 250px;" onchange="javascript:controlarCamposEloAnormalidade();" tabindex="6">
						<logic:present name="eloAnormalidade">							
							<option value="">&nbsp;</option>
							
							<logic:present name="idAnormalidadeElo">
								<bean:define name="idAnormalidadeElo" id="idAnormalidade" />
								<%String anormalidade = (String) idAnormalidade; %>
								<logic:iterate name="eloAnormalidade" scope="session" id="elo">;
									<bean:define name="elo" property="id" id="idOption"/>
									<% Integer option = (Integer) idOption;
									
										if (anormalidade.equals(option.toString())){
									%>
										<option value="${elo.id}" selected="selected">${elo.descricaoComId}</option>
									<%}else{ %>
										<option value="${elo.id}">${elo.descricaoComId}</option>
									<%} %>	
								</logic:iterate>
							</logic:present>
							
							<logic:notPresent name="idAnormalidadeElo">
								<logic:iterate name="eloAnormalidade" scope="session" id="elo">;
									<option value="${elo.id}">${elo.descricaoComId}</option>
								</logic:iterate>	
							</logic:notPresent>						
						</logic:present>
					</select>
						</td>
					</tr>
					<tr>
						<td align="left"><strong> Data da Anormalidade: </strong></td>
						<td colspan="3" align="left">
							 							
							<logic:notPresent name="dataAnormalidadeElo">
								<input name="dataAnormalidadeElo" maxlength="10" type="text" size="10" onkeyup="mascaraData(this, event);" tabindex="7"/>
							</logic:notPresent>	 
							
							<logic:present name="dataAnormalidadeElo">
								<input name="dataAnormalidadeElo" maxlength="10" type="text" size="10" onkeyup="mascaraData(this, event);" tabindex="7" value='<bean:write name="dataAnormalidadeElo" />'/>
							</logic:present>
							
							
							<a	href="javascript:abrirCalendario('informar', 'dataAnormalidadeElo')">
								<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> dd/mm/aaaa
						</td>
					</tr>
					<tr>
						<td height="10">
							<strong>Funcionário:</strong>
						</td>
						<td colspan="3">
							<logic:notPresent name="idFuncionarioAnormalidade">
								<input name="idFuncionarioAnormalidade" size="10" maxlength="9" tabindex="8"
						onkeypress="validaEnter(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=3', 'idFuncionarioAnormalidade', 'Funcionário');" />
							</logic:notPresent>
							<logic:present name="idFuncionarioAnormalidade">
								<input name="idFuncionarioAnormalidade" size="10" maxlength="9" tabindex="8" value='<bean:write name="idFuncionarioAnormalidade" />' 
						onkeypress="validaEnter(event, 'exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?objetoConsulta=3', 'idFuncionarioAnormalidade', 'Funcionário');" />
							</logic:present>
						<a 
							href="javascript:abrirPopup('exibirInformarOcorrenciaCadastroAnormalidadeEloAction.do?pesquisarFuncionario=OK',400,400);setarChamadaFuncionario('anormalidade');">
						<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
							border="0" height="21" width="23"></a> 
							<logic:present name="descricaoFuncionarioAnormalidade">							
								
								<logic:present name="exception">
									<input name="descricaoFuncionarioAnormalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #FF0000" value='<bean:write name="descricaoFuncionarioAnormalidade" />' />
								</logic:present>
								<logic:notPresent name="exception">
									<input name="descricaoFuncionarioAnormalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" value='<bean:write name="descricaoFuncionarioAnormalidade" />' />
								</logic:notPresent>
	
						</logic:present> 
						<logic:notPresent name="descricaoFuncionarioAnormalidade">
							
							<input name="descricaoFuncionarioAnormalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" value="" />

						</logic:notPresent> 						
						</td>
					</tr>
					<tr>
						<td align="left"><strong> Foto da Anormalidade: </strong></td>
						<td colspan="3" align="left">
								<input type="file" style="textbox" name="uploadPictureAnormalidade" size="50" tabindex="9"/>													
						</td>
					</tr>
					<tr>
						<td align="left" colspan="4">&nbsp;</td>
					</tr>

					<tr>
						<td align="left" colspan="3">&nbsp; <input name="adicionar"
							class="bottonRightCol" value="Cancelar" type="button"
							onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
						<td width="53%" align="right">
						<input name="adicionar" class="bottonRightCol"
							value="Concluir" type="button"
							onclick="validarForm(document.forms[0]);">
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%@ include file="/jsp/util/rodape.jsp"%></form>
</body>
<script language="JavaScript">
<!-- Begin
	controlarCamposCadastroOcorrencia();
	controlarCamposEloAnormalidade();
-->
</script>
</html:html>
