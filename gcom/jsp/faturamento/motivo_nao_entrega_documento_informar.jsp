<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InformarNaoEntregaDocumentosActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<SCRIPT LANGUAGE="JavaScript">
 
	function limparDescricaoResponsavelEntrega(){
		var form = document.forms[0];
		
		form.idResponsavelEntrega.value = "";
		form.descricaoResponsavelEntrega.value = "";
	}
	
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    	
    	var form= document.forms[0];
    	//var form= document.InserirHistoricoAlteracaoSistemaActionForm;
    	
      	form.idResponsavelEntrega.value = codigoRegistro;  
      	form.descricaoResponsavelEntrega.value = descricaoRegistro;
      	form.descricaoResponsavelEntrega.style.color = "#000000";
    }	

	function validarForm(){
		var form = document.InformarNaoEntregaDocumentosActionForm;
						
		if(validateInformarNaoEntregaDocumentosActionForm(form)){
			// valida se a Data de Devolu&ccedil;&atilde;o é menor que o Mês/Ano do Documento
			var dataDevolucao = form.dataDevolucao.value;
			var mesAnoDocumento = form.mesAnoConta.value;


			/*
			if (!comparaMesAno(dataDevolucao.substr(3), '>', mesAnoDocumento)){
				alert('Mês/Ano da Data de Devolução deve ser posterior ou igual ao Mês/Ano do Documento');
				return false;
			}*/
		
			// valida se a data atual é maior q a corrente
			if (!compararDataComDataAtual(dataDevolucao)){		  
		  		alert('Data de Devolução deve ser anterior ou igual à Data Corrente.');
		  		return false;
			}
			
			if (!validaNumeroDocumento()){
			   alert(' Matrícula ou Nr. de Documento não tiveram Motivo ou Quantidade de Tentativas informadas.');
			   return false;
			}
			
	       	submeterFormPadrao(form);
	    }
	}
	
  	function validaNumeroDocumento() {
		var form = this.document.forms[0];		
		
		if ((form.numeroDocumento1 != null && form.numeroDocumento1.value != '') || 
		     (form.matricula1 != null && form.matricula1.value != '')){
		  if (form.idCodigo1[form.idCodigo1.selectedIndex] == null || form.idCodigo1[form.idCodigo1.selectedIndex].value == '-1' ||
		  	  form.qtTentativas1.value == '' || form.qtTentativas1.value == '0'){		  	   
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento2 != null && form.numeroDocumento2.value != '') || 
		     (form.matricula2 != null && form.matricula2.value != '')){
		  if (form.idCodigo2[form.idCodigo2.selectedIndex] == null || form.idCodigo2[form.idCodigo2.selectedIndex].value == '-1' ||
		  	  form.qtTentativas2.value == '' || form.qtTentativas2.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento3 != null && form.numeroDocumento3.value != '') || 
		     (form.matricula3 != null && form.matricula3.value != '')){
		  if (form.idCodigo3[form.idCodigo3.selectedIndex] == null || form.idCodigo3[form.idCodigo3.selectedIndex].value == '-1' ||
		  	  form.qtTentativas3.value == '' || form.qtTentativas3.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento4 != null && form.numeroDocumento4.value != '') || 
		     (form.matricula4 != null && form.matricula4.value != '')){
		  if (form.idCodigo4[form.idCodigo4.selectedIndex] == null || form.idCodigo4[form.idCodigo4.selectedIndex].value == '-1' ||
		  	  form.qtTentativas4.value == '' || form.qtTentativas4.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento5 != null && form.numeroDocumento5.value != '') || 
		     (form.matricula5 != null && form.matricula5.value != '')){
		  if (form.idCodigo5[form.idCodigo5.selectedIndex] == null || form.idCodigo5[form.idCodigo5.selectedIndex].value == '-1' ||
		  	  form.qtTentativas5.value == '' || form.qtTentativas5.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento6 != null && form.numeroDocumento6.value != '') || 
		     (form.matricula6 != null && form.matricula6.value != '')){
		  if (form.idCodigo6[form.idCodigo6.selectedIndex] == null || form.idCodigo6[form.idCodigo6.selectedIndex].value == '-1' ||
		  	  form.qtTentativas6.value == '' || form.qtTentativas6.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento7 != null && form.numeroDocumento7.value != '') || 
		     (form.matricula7 != null && form.matricula7.value != '')){
		  if (form.idCodigo7[form.idCodigo7.selectedIndex] == null || form.idCodigo7[form.idCodigo7.selectedIndex].value == '-1' ||
		  	  form.qtTentativas7.value == '' || form.qtTentativas7.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento8 != null && form.numeroDocumento8.value != '') || 
		     (form.matricula8 != null && form.matricula8.value != '')){
		  if (form.idCodigo8[form.idCodigo8.selectedIndex] == null || form.idCodigo8[form.idCodigo8.selectedIndex].value == '-1' ||
		  	  form.qtTentativas8.value == '' || form.qtTentativas8.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento9 != null && form.numeroDocumento9.value != '') || 
		     (form.matricula9 != null && form.matricula9.value != '')){
		  if (form.idCodigo9[form.idCodigo9.selectedIndex] == null || form.idCodigo9[form.idCodigo9.selectedIndex].value == '-1' ||
		  	  form.qtTentativas9.value == '' || form.qtTentativas9.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento10 != null && form.numeroDocumento10.value != '') || 
		     (form.matricula10 != null && form.matricula10.value != '')){
		  if (form.idCodigo10[form.idCodigo10.selectedIndex] == null || form.idCodigo10[form.idCodigo10.selectedIndex].value == '-1' ||
		  	  form.qtTentativas10.value == '' || form.qtTentativas10.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento11 != null && form.numeroDocumento11.value != '') || 
		     (form.matricula11 != null && form.matricula11.value != '')){
		  if (form.idCodigo11[form.idCodigo11.selectedIndex] == null || form.idCodigo11[form.idCodigo11.selectedIndex].value == '-1' ||
		  	  form.qtTentativas11.value == '' || form.qtTentativas11.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento12 != null && form.numeroDocumento12.value != '') || 
		     (form.matricula12 != null && form.matricula12.value != '')){
		  if (form.idCodigo12[form.idCodigo12.selectedIndex] == null || form.idCodigo12[form.idCodigo12.selectedIndex].value == '-1' ||
		  	  form.qtTentativas12.value == '' || form.qtTentativas12.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento13 != null && form.numeroDocumento13.value != '') || 
		     (form.matricula13 != null && form.matricula13.value != '')){
		  if (form.idCodigo13[form.idCodigo13.selectedIndex] == null || form.idCodigo13[form.idCodigo13.selectedIndex].value == '-1' ||
		  	  form.qtTentativas13.value == '' || form.qtTentativas13.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento14 != null && form.numeroDocumento14.value != '') || 
		     (form.matricula14 != null && form.matricula14.value != '')){
		  if (form.idCodigo14[form.idCodigo14.selectedIndex] == null || form.idCodigo14[form.idCodigo14.selectedIndex].value == '-1' ||
		  	  form.qtTentativas14.value == '' || form.qtTentativas14.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento15 != null && form.numeroDocumento15.value != '') || 
		     (form.matricula15 != null && form.matricula15.value != '')){
		  if (form.idCodigo15[form.idCodigo15.selectedIndex] == null || form.idCodigo15[form.idCodigo15.selectedIndex].value == '-1' ||
		  	  form.qtTentativas15.value == '' || form.qtTentativas15.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento16 != null && form.numeroDocumento16.value != '') || 
		     (form.matricula16 != null && form.matricula16.value != '')){
		  if (form.idCodigo16[form.idCodigo16.selectedIndex] == null || form.idCodigo16[form.idCodigo16.selectedIndex].value == '-1' ||
		  	  form.qtTentativas16.value == '' || form.qtTentativas16.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento17 != null && form.numeroDocumento17.value != '') || 
		     (form.matricula17 != null && form.matricula17.value != '')){
		  if (form.idCodigo17[form.idCodigo17.selectedIndex] == null || form.idCodigo17[form.idCodigo17.selectedIndex].value == '-1' ||
		  	  form.qtTentativas17.value == '' || form.qtTentativas17.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento18 != null && form.numeroDocumento18.value != '') || 
		     (form.matricula18 != null && form.matricula18.value != '')){
		  if (form.idCodigo18[form.idCodigo18.selectedIndex] == null || form.idCodigo18[form.idCodigo18.selectedIndex].value == '-1' ||
		  	  form.qtTentativas18.value == '' || form.qtTentativas18.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento19 != null && form.numeroDocumento19.value != '') || 
		     (form.matricula19 != null && form.matricula19.value != '')){
		  if (form.idCodigo19[form.idCodigo19.selectedIndex] == null || form.idCodigo19[form.idCodigo19.selectedIndex].value == '-1' ||
		  	  form.qtTentativas19.value == '' || form.qtTentativas19.value == '0'){
		  	    return false;
		  	  }		  
		}
		
		if ((form.numeroDocumento20 != null && form.numeroDocumento20.value != '') || 
		     (form.matricula20 != null && form.matricula20.value != '')){
		  if (form.idCodigo20[form.idCodigo20.selectedIndex] == null || form.idCodigo20[form.idCodigo20.selectedIndex].value == '-1' ||
		  	  form.qtTentativas20.value == '' || form.qtTentativas20.value == '0'){
		  	    return false;
		  	  }		  
		}
		return true;
   	}   	   	   	
	
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/informarNaoEntregaDocumentosAction"
	name="InformarNaoEntregaDocumentosActionForm"
	type="gcom.gui.faturamento.InformarNaoEntregaDocumentosActionForm"
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
					<td class="parabg">Informar Documentos Não Entregues (Devolvidos)</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para informar os documentos não entregues
					(devolvidos), informe os dados abaixo:</td>
				</tr>

				<tr>
					<td height="25"><strong>Data da Devolução:<font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><html:text property="dataDevolucao" size="10"
						maxlength="10" tabindex="1" onkeyup="mascaraData(this, event);" />
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InformarNaoEntregaDocumentosActionForm', 'dataDevolucao')" />
					dd/mm/aaaa</div>
					</td>
				</tr>

				<tr>
					<td><strong>Responsável pela Entrega:</strong></td>
					<td colspan="2"><html:text property="idResponsavelEntrega" size="8"
						maxlength="8" tabindex="2"
						onkeypress="validaEnterComMensagem(event, 'exibirInformarNaoEntregaDocumentosAction.do', 'idResponsavelEntrega','Cliente');" />

					<a
						href="javascript:abrirPopup('exibirPesquisarClienteAction.do?menu=sim');">
					<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Cliente"
						border="0" height="21" width="23"></a> <logic:present
						name="funcionalidadeEncontrada">

						<logic:equal name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoResponsavelEntrega" size="40"
								maxlength="50" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoResponsavelEntrega" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="funcionalidadeEncontrada">
						<logic:empty name="InformarNaoEntregaDocumentosActionForm"
							property="idResponsavelEntrega">
							<html:text property="descricaoResponsavelEntrega" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>

						<logic:notEmpty name="InformarNaoEntregaDocumentosActionForm"
							property="idResponsavelEntrega">
							<html:text property="descricaoResponsavelEntrega" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparDescricaoResponsavelEntrega()"> <img
						border="0" title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>
					</td>
				</tr>

				<tr>
					<td><strong>Tipo do Documento:<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select property="tipoDocumento"
						tabindex="3"
						onchange="redirecionarSubmit('exibirInformarNaoEntregaDocumentosAction.do');">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoTipoDocumento"
							labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select></td>
				</tr>

				<tr>

					<td height="10" width="145"><strong>Mês e Ano :<font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="mesAnoConta" size="8" maxlength="7"
						tabindex="4" onkeyup="mascaraAnoMes(this, event);" /></td>

				</tr>
				<tr>

					<td><strong></strong></td>


					<td colspan="3" align="left"><label><span class="style2"> </span> </label></td>
				</tr>

				<tr>
					<td colspan="4">
					<table bgcolor="#99ccff" width="100%">

						<tbody>
							<tr bordercolor="#FFFFFF" bgcolor="#79bbfd">
								<logic:present name="exibirTabela" scope="request">
									<td height="32" width="11%">
									<div align="center">Matrícula</div>
									</td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td height="32" width="11%">
									<div align="center">Nº Documento</div>
									</td>
								</logic:notPresent>
								<td width="32%">
								<div align="center">Motivo</div>
								</td>
								<td width="6%">
								<div align="center">Tent.</div>
								</td>

								<logic:present name="exibirTabela" scope="request">
										<td height="32" width="11%">
											<div align="center">Matrícula</div>
										</td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td height="32" width="11%">
									<div align="center">Nº Documento</div>
									</td>
								</logic:notPresent>
								
								<td width="32%">
								<div align="center">Motivo</div>
								</td>
								<td width="8%">
								<div align="center">Tent.</div>
								</td>
							</tr>

							<%-- PRIMEIRA LINHA DA TABELA --%>

							<logic:present name="exibirTabela" scope="request">
								<td width="12%" align="center"><html:text property="matricula1"
									size="9" maxlength="9" tabindex="5" /></td>
							</logic:present>
							<logic:notPresent name="exibirTabela" scope="request">
								<td width="12%" align="center"><html:text
									property="numeroDocumento1" size="9" maxlength="9" tabindex="5" /></td>
							</logic:notPresent>

							<td width="28%" align="center">
								<html:select property="idCodigo1" tabindex="6">
									<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
									<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
								</html:select>
							</td>

							<td width="8%" align="center"><html:text property="qtTentativas1"
								size="1" maxlength="1" tabindex="7" /></td>

							<!-- OUTRO LADO DA TABELA -->

							<logic:present name="exibirTabela" scope="request">
								<td width="12%" align="center"><html:text property="matricula2"
									size="9" maxlength="9" tabindex="8" /></td>
							</logic:present>
							<logic:notPresent name="exibirTabela" scope="request">
								<td width="12%" align="center"><html:text
									property="numeroDocumento2" size="9" maxlength="9" tabindex="8" /></td>
							</logic:notPresent>

							<td width="28%" align="center">
								<html:select property="idCodigo2" tabindex="9">
									<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
									<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
								</html:select>
							</td>

							<td width="10%" align="center"><html:text
								property="qtTentativas2" size="1" maxlength="1" tabindex="10" /></td>
							<tr>

								<%-- FIM DA PRIMEIRA LINHA DA TABELA --%>

								<%-- INICIO DA SEGUNDA LINHA DA TABELA--%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula3"
										size="9" maxlength="9" tabindex="11" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento3" size="9" maxlength="9"
										tabindex="11" /></td>
								</logic:notPresent>


								<td width="28%" align="center">
									<html:select property="idCodigo3" tabindex="12">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas3" size="1" maxlength="1" tabindex="13" /></td>

								<!-- OUTRA PARTE DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula4"
										size="9" maxlength="9" tabindex="14" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento4" size="9" maxlength="9"
										tabindex="14" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo4" tabindex="15">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas4" size="1" maxlength="1" tabindex="16" /></td>
							</tr>
							<tr>

								<%-- FIM DA SEGUNDA LINHA DA TABELA --%>

								<%-- INICIO DA TERCEIRA LINHA DA TABELA--%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula5"
										size="9" maxlength="9" tabindex="17" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento5" size="9" maxlength="9"
										tabindex="17" /></td>
								</logic:notPresent>



								<td width="28%" align="center">
									<html:select property="idCodigo5" tabindex="18">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas5" size="1" maxlength="1" tabindex="19" /></td>

								<!-- OUTRA PARTE DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula6"
										size="9" maxlength="9" tabindex="20" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento6" size="9" maxlength="9"
										tabindex="20" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo6" tabindex="21">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas6" size="1" maxlength="1" tabindex="22" /></td>
							</tr>
							<tr>
								<%-- FIM TERCEIRA LINHA DA TABELA --%>


								<%-- INICIO QUARTA LINHA DA TABELA --%>


								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula7"
										size="9" maxlength="9" tabindex="23" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento7" size="9" maxlength="9"
										tabindex="23" /></td>
								</logic:notPresent>



								<td width="28%" align="center">
									<html:select property="idCodigo7" tabindex="24">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas7" size="1" maxlength="1" tabindex="25" /></td>

								<!-- OUTRA PARTE DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula8"
										size="9" maxlength="9" tabindex="26" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento8" size="9" maxlength="9"
										tabindex="26" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo8" tabindex="27">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas8" size="1" maxlength="1" tabindex="28" /></td>
							</tr>
							<tr>

								<%--FIM QUARTA LINHA --%>

								<%--INICIO QUINTA LINHA --%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text property="matricula9"
										size="9" maxlength="9" tabindex="29" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento9" size="9" maxlength="9"
										tabindex="29" /></td>
								</logic:notPresent>



								<td width="28%" align="center">
									<html:select property="idCodigo9" tabindex="30">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas9" size="1" maxlength="1" tabindex="31" /></td>

								<!-- OUTRA PARTE DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula10" size="9" maxlength="9" tabindex="32" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento10" size="9" maxlength="9"
										tabindex="32" /></td>
								</logic:notPresent>
								<td width="28%" align="center">
									<html:select property="idCodigo10" tabindex="33">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas10" size="1" maxlength="1" tabindex="34" /></td>
							</tr>
							<tr>

								<%-- FIM QUINTA LINHA --%>

								<%-- INICIO SEXTA LINHA --%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula11" size="9" maxlength="9" tabindex="35" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento11" size="9" maxlength="9"
										tabindex="35" /></td>
								</logic:notPresent>


								<td width="28%" align="center">
									<html:select property="idCodigo11" tabindex="36">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas11" size="1" maxlength="1" tabindex="37" /></td>

								<!-- OUTRA PARTE DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula12" size="9" maxlength="9" tabindex="38" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento12" size="9" maxlength="9"
										tabindex="38" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo12" tabindex="39">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
								</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas12" size="1" maxlength="1" tabindex="40" /></td>
							</tr>
							<tr>

								<%-- FIM SEXTA LINHA --%>

								<%-- INICIO SETIMA LINHA --%>


								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula13" size="9" maxlength="9" tabindex="41" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento13" size="9" maxlength="9"
										tabindex="41" /></td>
								</logic:notPresent>


								<td width="28%" align="center">
									<html:select property="idCodigo13" tabindex="42">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
								</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas13" size="1" maxlength="1" tabindex="43" /></td>

								<!-- OUTRO LADO DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula14" size="9" maxlength="9" tabindex="44" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento14" size="9" maxlength="9"
										tabindex="44" /></td>
								</logic:notPresent>
								<td width="28%" align="center">
									<html:select property="idCodigo14" tabindex="45">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas14" size="1" maxlength="1" tabindex="46" /></td>
							</tr>
							<tr>
								<%-- FIM SETIMA LINHA--%>

								<%--INICIO OITAVA LINHA --%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula15" size="9" maxlength="9" tabindex="47" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento15" size="9" maxlength="9"
										tabindex="47" /></td>
								</logic:notPresent>



								<td width="28%" align="center">
									<html:select property="idCodigo15" tabindex="48">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas15" size="1" maxlength="1" tabindex="49" /></td>

								<!-- OUTRO LADO DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula16" size="9" maxlength="9" tabindex="50" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento16" size="9" maxlength="9"
										tabindex="50" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo16" tabindex="51">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas16" size="1" maxlength="1" tabindex="52" /></td>
							</tr>
							<tr>
								<%-- FIM OITAVA LINHA--%>

								<%-- INICIO NONA LINHA--%>
								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula17" size="9" maxlength="9" tabindex="53" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento17" size="9" maxlength="9"
										tabindex="53" /></td>
								</logic:notPresent>


								<td width="28%" align="center">
									<html:select property="idCodigo17" tabindex="54">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas17" size="1" maxlength="1" tabindex="55" /></td>

								<!-- OUTRO LADO DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula18" size="9" maxlength="9" tabindex="56" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento18" size="9" maxlength="9"
										tabindex="56" /></td>
								</logic:notPresent>
								<td width="28%" align="center">
									<html:select property="idCodigo18" tabindex="57">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas18" size="1" maxlength="1" tabindex="58" /></td>
							</tr>
							<tr>

								<%-- FIM NONA LINHA--%>

								<%-- INICIO DECIMA LINHA--%>

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula19" size="9" maxlength="9" tabindex="59" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento19" size="9" maxlength="9"
										tabindex="59" /></td>
								</logic:notPresent>


								<td width="28%" align="center">
									<html:select property="idCodigo19" tabindex="60">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="8%" align="center"><html:text
									property="qtTentativas19" size="1" maxlength="1" tabindex="61" /></td>

								<!-- OUTRO LADO DA TABELA -->

								<logic:present name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="matricula20" size="9" maxlength="9" tabindex="62" /></td>
								</logic:present>
								<logic:notPresent name="exibirTabela" scope="request">
									<td width="12%" align="center"><html:text
										property="numeroDocumento20" size="9" maxlength="9"
										tabindex="62" /></td>
								</logic:notPresent>

								<td width="28%" align="center">
									<html:select property="idCodigo20" tabindex="63">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>		
										<html:options collection="colecaoMotivosNaoEntrega" labelProperty="descricao" property="id" />
									</html:select>
								</td>

								<td width="10%" align="center"><html:text
									property="qtTentativas20" size="1" maxlength="1" tabindex="64" /></td>
							</tr>

						</tbody>
					</table>
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
					<td align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirInformarNaoEntregaDocumentosAction.do?menu=sim"/>'">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						tabindex="65" value="Informar"
						onclick="javascript:validarForm(document.forms[0]);"
						url="informarNaoEntregaDocumentosAction.do" /> <%-- <input type="button" name="Button" class="bottonRightCol" value="Inserir" onClick="javascript:validarForm(document.forms[0]);" /> --%>
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
