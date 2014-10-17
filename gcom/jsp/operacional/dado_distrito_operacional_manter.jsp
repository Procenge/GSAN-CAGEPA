<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<%@page import="gcom.operacional.DistritoOperacional"%>
<%@page import="gcom.operacional.DadoDistritoOperacional"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ManterDadoDistritoOperacionalActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js">
</script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
</head>

<script language="JavaScript">
  
    function validarForm() {
    var form = document.forms[0];
	  if(validateManterDadoDistritoOperacionalActionForm(form)){	     
			if(validarFormObrigatorio(form)){
				submeterFormPadrao(form);	
			}
       }
    }

    function atualizarForm() {
        var form = document.forms[0];
        form.validar.value = '1';
        form.action = '/gsan/atualizarDadoDistritoOperacionalAction.do';
    	  if(validateManterDadoDistritoOperacionalActionForm(form)){	     
    			if(validarFormObrigatorio(form)){
    				submeterFormPadrao(form);	
    			}
           }
        }

    function removerDado(url){

  		if(confirm('Confirma remoção ?')){
  	       var form = document.forms[0];
  	    	form.action = url;
  		    form.submit()	
  		}

  	}
  	
    function validarFormObrigatorio(form) {
    	if(form.validar.value == '1'){
        	var alerta = '';
    		if(form.unidadeCapacidade.value == ''){
        		alerta = 'Informe Unidade(m3/h) do Título Capacidade. \n';
    		}

    		if(form.aducaoCapacidade.value == ''){
        		alerta = alerta + 'Informe Adução(m3/h) do Título Capacidade. \n';
    		}

    		if(form.volumeCapacidade.value == ''){
        		alerta = alerta + 'Informe Volume(m3) do Título Capacidade. \n';
    		}

    		if(form.extensaoAdutora.value == ''){
        		alerta = alerta + 'Informe Extensão(m) do Título Adutora. \n';
    		}

    		if(form.diametroAdutora.value == ''){
        		alerta = alerta + 'Informe Diâmetro(ø) do Título Adutora. \n';
    		}
    		
    		if(form.materialAdutora.value == '-1'){
        		alerta = alerta + 'Informe Material Título Adutora. \n';
    		}

    		if(form.demandaEnergia.value == ''){
        		alerta = alerta + 'Informe Demanda Energia(KW). \n';
    		}
    		
    		if(form.qtidadeSulfatoAluminio.value == ''){
        		alerta = alerta + 'Informe Quantidade Sulfato Alumínio(kg). \n';
    		}

    		if(form.qtidadeCloroGasoso.value == ''){
        		alerta = alerta + 'Informe Quantidade Cloro Gasoso(kg). \n';
    		}

    		if(form.qtidadeHipocloritoSodio.value == ''){
        		alerta = alerta + 'Informe Quantidade Hipoclorito de Sódio(kg). \n';
    		}

    		if(form.qtidadeFluor.value == ''){
        		alerta = alerta + 'Informe Quantidade Fluor(kg). \n';
    		}

    		if(form.qtidadeHorasParalisadas.value == ''){
        		alerta = alerta + 'Informe Horas Paralisadas. \n';
    		}
			if(alerta != ''){
				alert(alerta);
				return false;
			}else{
				return true;
			}
    	} else{
			return true;
        }   
    }

    function limparForm(){
    	var form = document.forms[0];
    	form.anoMesReferencia.value = '';
    	form.unidadeCapacidade.value = '';
		form.aducaoCapacidade.value = '';
		form.volumeCapacidade.value = '';
		form.extensaoAdutora.value = '';
		form.diametroAdutora.value = '';
		form.materialAdutora.value = '-1';
		form.demandaEnergia.value = '';
		form.qtidadeSulfatoAluminio.value = '';
		form.qtidadeCloroGasoso.value = '';
		form.qtidadeHipocloritoSodio.value = '';
		form.qtidadeFluor.value = '';
		form.qtidadeHorasParalisadas.value = '';
    }
       
	 
</script>


<body leftmargin="5" topmargin="5" onload="setarFoco('descricao');">

<html:form action="/inserirDadoDistritoOperacionalAction.do"
	name="ManterDadoDistritoOperacionalActionForm"
	type="gcom.gui.operacional.ManterDadoDistritoOperacionalActionForm"
	method="post"
	onsubmit="return validateManterDadoDistritoOperacionalActionForm(this);">
	<logic:present name="dadosDistritoOperacional">
		<logic:empty name="dadosDistritoOperacional">
			<input type="hidden" name="validar" value="1" />
		</logic:empty>
		<logic:notEmpty name="dadosDistritoOperacional">
			<input type="hidden" name="validar" value="2" />
		</logic:notEmpty>
	</logic:present>


	<table width="700" height="100%" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Manter Dados Distrito Operacional</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<logic:present name="ManterDadoDistritoOperacionalActionForm"
						property="atualizar">
						<td colspan="2">Para atualizar o Dado para o Distrito
						Operacional, informe os dados abaixo:</td>
					</logic:present>
					<logic:notPresent name="ManterDadoDistritoOperacionalActionForm"
						property="atualizar">
						<td colspan="2">Para adicionar o um Dado para o Distrito
						Operacional, informe os dados abaixo:</td>
					</logic:notPresent>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Mês e Ano de
					Referência:<font color="#FF0000">*</font></strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2">
					 <logic:present	name="ManterDadoDistritoOperacionalActionForm" property="atualizar">
						<html:text property="anoMesReferencia" size="7" maxlength="7"
							tabindex="1" onkeyup="mascaraAnoMes(this, event)" disabled="true" />
						mm/aaaa
				</logic:present> <logic:notPresent name="ManterDadoDistritoOperacionalActionForm"
						property="atualizar">
						<html:text property="anoMesReferencia" size="7" maxlength="7"
							tabindex="1" onkeyup="mascaraAnoMes(this, event)" />
						mm/aaaa
					</logic:notPresent> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3">Título
					Capacidade</td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Unidade(m3/h):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="unidadeCapacidade"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="2" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Adução(m3/h):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="aducaoCapacidade"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="3" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Volume(m3):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="volumeCapacidade"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="4" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3">Título
					Adutora</td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Extensão(m):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="extensaoAdutora"
						size="10" maxlength="10"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="5" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Diâmetro(ø):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="diametroAdutora"
						size="7" maxlength="7"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="6" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Material:</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:select property="materialAdutora"
						tabindex="7" style="width:200px;">
						<html:option value="-1"> &nbsp; </html:option>
						<html:options collection="colecaoMaterialRedeAgua" property="id"
							labelProperty="descricaoComId" />
					</html:select> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Demanda
					Energia(KW):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="demandaEnergia"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="8" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Quantidade
					Sulfato Alumínio(kg):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text
						property="qtidadeSulfatoAluminio" size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="9" /> </span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Quantidade
					Cloro Gasoso(kg):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="qtidadeCloroGasoso"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="10" />
					</span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Quantidade
					Hipoclorito de Sódio(kg):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text
						property="qtidadeHipocloritoSodio" size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="11" />
					</span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Quantidade
					Fluor(kg):</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text property="qtidadeFluor"
						size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event)" onblur="javascript:formataValorDecimal(this, 4, null)" tabindex="12" />
					</span></b></strong></td>
				</tr>
				<tr>
					<td width="40%" class="style3"><strong>Horas
					Paralisadas:</strong></td>
					<td width="60%" colspan="2"><strong><b><span
						class="style2"> <html:text
						property="qtidadeHorasParalisadas" size="6" maxlength="6"
						onkeyup="javascript:formataValorDecimal(this, 2, event)" onblur="javascript:formataValorDecimal(this, 2, null)" tabindex="13" />
					</span></b></strong></td>
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>

					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
				
					<td align="right">
						
						<logic:present	name="ManterDadoDistritoOperacionalActionForm" property="atualizar">
							<input type="button" name="ButtonReset"
							class="bottonRightCol" value="Desfazer" onClick="javascript: window.location.href = 'exibirAtualizarDadoDistritoOperacionalAction.do?idDadoDistritoOperacional=<bean:write name="idDadoDistritoOperacional" scope="request" />'">
							<input type="button" name="Button" class="bottonRightCol"
							value="Atualizar" onclick="javascript:atualizarForm();" tabindex="15" />
						</logic:present> 
						<logic:notPresent name="ManterDadoDistritoOperacionalActionForm"
						property="atualizar">
							<input type="button" name="ButtonReset"
						class="bottonRightCol" value="Limpar" onClick="limparForm();">
							<input type="button" name="Button" class="bottonRightCol"
							value="Inserir" onclick="javascript:validarForm();" tabindex="15" />
					</logic:notPresent></td>
				</tr>
			</table>
			<table width="700" border="0" cellspacing="5" cellpadding="0">
				<tr>
					<td valign="top" class="centercoltext">
					<table>
						<tr>
							<td></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%">
					<tr>
							<td colspan="2">Dados Inseridos para o Distrito Operacional</td>
						</tr>
						<tr>
						<td>
					<table width="100%" align="center" bgcolor="#99CCFF">
									<tr>
										<td width="50" align="center"><strong>Remover</strong></td>
										<td align="center"><strong>Referência</strong></td>
									</tr>
						</table>
						</td>
						</tr>
						<tr>
								<td height="95" colspan="2">
								<div style="width: 100%; height: 100%; overflow: auto;">
						<table width="100%" border="0">
								<logic:present name="dadosDistritoOperacional">
		
											<!--corpo da segunda tabela-->
		
											<%
												String cor = "#cbe5fe";
											%>
		
											<logic:iterate name="dadosDistritoOperacional"
												id="dadoDistritoOperacional" type="DadoDistritoOperacional">
												
		
												<%
													if(cor.equalsIgnoreCase("#cbe5fe")){
																		cor = "#cbe5fe";
												%>
												<tr bgcolor="#FFFFFF">
													<%
														}else{
																			cor = "#FFFFFF";
													%>
													<tr bgcolor="#cbe5fe">
														<%
															}
														%>
		
														<td width="50" align="center"><a
															href="javascript:removerDado('/gsan/removerDadoDistritoOperacionalAction.do?dadoRemoverSelecao=<%="" + dadoDistritoOperacional.getAnoMesReferencia()%>');"><img
															border="0"
															src="<bean:message key="caminho.imagens"/>Error.gif" /></a></td>
														<td><a
															href="exibirAtualizarDadoDistritoOperacionalAction.do?idDadoDistritoOperacional=<%="" + dadoDistritoOperacional.getAnoMesReferencia()%>"><bean:write
															name="dadoDistritoOperacional" property="mesReferenciaFormatado" /></a>
														</td>
													</tr>
											</logic:iterate>
										</table>
										</div>
										</td>
									</tr>
								</logic:present>
		
							</table>
					<table width="100%">
						<td align="right"><input type="button" name="Button"
							class="bottonRightCol" value="Concluir"
							onclick="javascript:window.close();" tabindex="16" /></td>
						</tr>
					</table>
			</table>
			</html:form> </html:html>