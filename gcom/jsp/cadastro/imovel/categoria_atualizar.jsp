<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema,java.util.Collection"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="CategoriaActionForm"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<script language="JavaScript">
<!--
// Verifica se o n�mero do campo do formul�rio � negativo
function numeroNegativo(valor,campo,descricao){
	if(valor < 0){ 
		alert('Informe '+descricao+' com um n�mero positivo.');
		campo.focus();
		return true;
	}
}

// Faz as valida��es do formul�rio
function validarForm(form){
	// Verifica se os campos s�o vazios 
	if(validateCategoriaActionForm(form)){
		var retorno = true;
		var objConsumoMinimo = returnObject(form, "consumoMinimo");
		var objConsumoEstouro = returnObject(form, "consumoEstouro");
		var objVezesMediaEstouro = returnObject(form, "vezesMediaEstouro");
		var objMediaBaixoConsumo = returnObject(form, "mediaBaixoConsumo");		
		var objPorcentagemMediaBaixoConsumo = returnObject(form, "porcentagemMediaBaixoConsumo");		
		var objConsumoAlto = returnObject(form, "consumoAlto");		
		var objVezesMediaAltoConsumo = returnObject(form, "vezesMediaAltoConsumo");		
		
		// Verifica se os campos s�o negativos
		if(numeroNegativo(objConsumoMinimo.value,objConsumoMinimo,'Consumo M�nimo da Categoria')){
			retorno = false;
		}else if(numeroNegativo(objConsumoEstouro.value,objConsumoEstouro,'Consumo de Refer�ncia do Estouro de Consumo')){
			retorno = false;
		}else if(numeroNegativo(objVezesMediaEstouro.value,objVezesMediaEstouro,'Fator de Multiplica��o do Consumo M�dio do Estouro do Consumo')){
			retorno = false;
		}else if(numeroNegativo(objMediaBaixoConsumo.value,objMediaBaixoConsumo,'Consumo M�dio de Refer�ncia do Baixo Consumo')){
			retorno = false;
		}else if(numeroNegativo(objPorcentagemMediaBaixoConsumo.value,objPorcentagemMediaBaixoConsumo,'Percentual do Consumo M�dio do Baixo Consumo')){
			retorno = false;
		}else if(numeroNegativo(objConsumoAlto.value,objConsumoAlto,'Consumo de Refer�ncia do Alto Consumo')){
			retorno = false;
		}else if(numeroNegativo(objVezesMediaAltoConsumo.value,objVezesMediaAltoConsumo,'Fator de Multiplica��o do Consumo M�dio do Alto Consumo')){
			retorno = false;
		}
		// Valida��es do Caso de Uso
		if(parseInt(objConsumoEstouro.value) < parseInt(objConsumoMinimo.value)){
		   	retorno = false;
		   	alert('Consumo de Refer�ncia do Estouro do Consumo deve ser superior ao Consumo M�nimo da Categoria.');
		   	objConsumoEstouro.focus();
		}else if(parseInt(objVezesMediaEstouro.value) < 1){
		   	retorno = false;
		   	alert('Fator de Multiplica��o da M�dia do Estouro do Consumo deve ser maior ou igual a um.');
		   	objVezesMediaEstouro.focus();
		}else if(parseInt(objMediaBaixoConsumo.value) < parseInt(objConsumoMinimo.value)){
		   	retorno = false;
		   	alert('Consumo M�dio de Refer�ncia do Baixo Consumo deve ser superior ao Consumo M�nimo da Categoria.');
		   	objMediaBaixoConsumo.focus();
		}else if(parseInt(objPorcentagemMediaBaixoConsumo.value) < 1){
		   	retorno = false;
		   	alert('Percentual do Consumo M�dio do Baixo Consumo deve ser maior ou igual a um.');
		   	objPorcentagemMediaBaixoConsumo.focus();
		}else if(parseInt(objConsumoAlto.value) < parseInt(objConsumoMinimo.value)){
		   	retorno = false;
		   	alert('Consumo de Refer�ncia do Alto Consumo deve ser superior ao Consumo M�nimo da Categoria.');
		   	objConsumoAlto.focus();
		}else if(parseInt(objConsumoAlto.value) > parseInt(objConsumoEstouro.value)){
		   	retorno = false;
		   	alert('Consumo de Refer�ncia do Alto Consumo deve ser inferior ao Consumo de Refer�ncia do Estouro do Consumo.');
		   	objConsumoAlto.focus();
		}else if(parseInt(objVezesMediaAltoConsumo.value) < 1){
		   	retorno = false;
		   	alert('Fator de Multiplica��o do Consumo M�dio do Alto Consumo deve ser maior ou igual a um.');
		   	objVezesMediaAltoConsumo.focus();
		}else if(parseFloat(objVezesMediaAltoConsumo.value.replace(",", ".")) > parseFloat(objVezesMediaEstouro.value.replace(",", "."))){
		   	retorno = false;
		   	alert('Fator de Multiplica��o do Consumo M�dio do Alto Consumo deve ser inferior ao Fator de Multiplica��o do Consumo M�dio do Estouro do Consumo.');
		   	objVezesMediaAltoConsumo.focus();
		}
		if( retorno == true ){
			submeterFormPadrao(form);
		}	
		
	}	
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5">
<html:form
  action="/atualizarCategoriaAction.do"
  name="CategoriaActionForm"
  type="gcom.gui.cadastro.imovel.CategoriaActionForm"
  method="post"
  onsubmit="return validateCategoriaActionForm(this);"
>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
            <table>
              <tr><td></td></tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                <td class="parabg">Atualizar Categoria</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>
	        <!-- In�cio do Corpo - Roberta Costa -->
	        <p>&nbsp;</p>

			<table bordercolor="#000000" width="100%" cellspacing="0">
                <tr>
					<td colspan="2">
						<p>Para atualizar uma categoria, informe os dados abaixo:</p><p>&nbsp;</p>
					</td>
					<td align="right"></td>
				</tr>
				</table>
				<table bordercolor="#000000" width="100%" cellspacing="0">
                <tr>
	                <td width="183" height="18">
	                	<strong>C&oacute;digo da Categoria:<font color="#FF0000">*</font></strong>
	                </td>
					<td>
						<html:text property="idCategoria" size="2" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"/>
					</td>
              	</tr>
				<tr>
					<td width="50%">
						<strong>Descri&ccedil;&atilde;o da Categoria:<font color="#FF0000">*</font></strong>
					</td>
					<td align="right">
						<div align="left"><html:text property="descricao" size="15" maxlength="15"/></div>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Descri&ccedil;&atilde;o Abreviada da Categoria: <font color="#FF0000">*</font></strong>
					</td>
					<td align="right">
						<div align="left"><html:text property="descricaoAbreviada" size="3" maxlength="3"/></div>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Tipo da Categoria:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:select property="tipoCategoria" tabindex="3">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoTipoCategoria" labelProperty="descricao" property="id" />
	                   	</html:select>
	                </td>
				</tr>
				<tr>
					<td>
						<strong>Consumo M&iacute;nimo da Categoria: <font color="#FF0000">*</font></strong>
					</td>
					<td align="right">
						<div align="left"><html:text property="consumoMinimo" size="3" maxlength="3"/></div>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Consumo M&eacute;dio por Economia x M&ecirc;s:<font color="#FF0000">*</font></strong>
					</td>
					<td align="right">
						<div align="left"><html:text property="consumoMedioEconomiaMes" size="6" maxlength="7"/></div>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Quantidade M�xima de Economias:<font color="#FF0000">*</font></strong>
					</td>
					<td align="right">
						<div align="left"><html:text property="quantidadeMaximoEconomiasValidacao" size="6" maxlength="7"/></div>
					</td>
				</tr>
								
				<tr>
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center"><strong>Dados para Determina��o do Estouro do Consumo</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
										<tr> 
											<td width="50%">
												<strong>Consumo de Refer&ecirc;ncia:<font color="#FF0000">*</font></strong>
											</td>
											<td>
												<html:text property="consumoEstouro" size="4" maxlength="4"/>&nbsp;m<sup>3</sup>
											</td>
										</tr>
										<tr> 
											<td><strong>Fator de Multiplica&ccedil;&atilde;o do Consumo M&eacute;dio:<font color="#FF0000">*</font></strong></td>
											<td>
												<input type="text" name="vezesMediaEstouro" value="<bean:write name="CategoriaActionForm" property="vezesMediaEstouro"/>" 
													size="4" maxlength="4" onKeyup="formataValorMonetario(this, 3);" style="text-align: right;"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center"><strong>Dados para Determina&ccedil;&atilde;o do Baixo Consumo</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
										<tr> 
											<td width="50%">
												<strong>Consumo M&eacute;dio de Refer&ecirc;ncia:<font color="#FF0000">*</font></strong>
											</td>
											<td>
												<html:text property="mediaBaixoConsumo" size="4" maxlength="4"/>&nbsp;m<sup>3</sup>
											</td>
										</tr>
										<tr> 
											<td><strong>Percentual do Consumo M&eacute;dio:<font color="#FF0000">*</font></strong></td>
											<td>
												<input name="porcentagemMediaBaixoConsumo" value="<bean:write name="CategoriaActionForm" property="porcentagemMediaBaixoConsumo"/>" 
													type="text" size="6" maxlength="6" onKeyup="formataValorMonetario(this, 5);" style="text-align: right;">&nbsp;%
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center"><strong>Dados para Determina&ccedil;&atilde;o do Alto Consumo</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
										<tr> 
											<td width="50%">
												<strong>Consumo de Refer&ecirc;ncia:<font color="#FF0000">*</font></strong>
											</td>
											<td>
												<html:text property="consumoAlto" size="4" maxlength="4"/>&nbsp;m<sup>3</sup>
											</td>
										</tr>
										<tr> 
											<td><strong>Fator de Multiplica&ccedil;&atilde;o do Consumo M&eacute;dio:<font color="#FF0000">*</font></strong></td>
											<td>
												<input type="text" name="vezesMediaAltoConsumo" value="<bean:write name="CategoriaActionForm" property="vezesMediaAltoConsumo"/>" 
													size="4" maxlength="4" onKeyup="formataValorMonetario(this, 3);" style="text-align: right;"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center"><strong>Dados para Determina&ccedil;&atilde;o da Virada do Hidrometro</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
										<tr> 
											<td>
												<strong>Consumo de Virada do Hidrometro:</strong>
											</td>
											<td>
												<input name="consumoViradaHidrometro" type="text" size="4" maxlength="9"  value="<bean:write name="CategoriaActionForm" property="consumoViradaHidrometro"/>" >&nbsp;m<sup>3</sup>
											</td>
										</tr>
										<tr> 
											<td><strong>Numero de Vezes Media de Virada do Hidrometro:</strong></td>
											<td>
												<input name="numeroVezesMediaViradaHidrometro" type="text" size="4" maxlength="3" value="<bean:write name="CategoriaActionForm" property="numeroVezesMediaViradaHidrometro"/>" >>&nbsp;m<sup>3</sup>
											</td>
										</tr>
										<tr> 
											<td><strong>Indicador Validar a VIrada do Hidrometro:</strong></td>
											<td>
												<input name="indicadorValidarViradaHidrometro" type="text" size="4" maxlength="1" value="<bean:write name="CategoriaActionForm" property="indicadorValidarViradaHidrometro"/>">&nbsp;m<sup>3</sup>
											</td>
										</tr>
									</table>
								</td>
							</tr>
				
				
						</table>
					</td>
				</tr>
				
				
				
			
                <tr>
                	<td><br><strong>Indicador de Uso:</strong></td>
                 	<td align="right"><br>
						<div align="left">
							<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"/>
							<strong>Ativo</strong>
							<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"/>
							<strong>Inativo</strong>
						</div>
					</td>
				</tr>
              	<tr>
                <td>&nbsp;</td>
                <td align="right">
                	<div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div>
                </td>
             </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
				<td>
					<logic:present scope="session" name="manter">
						<input class="bottonRightCol" value="Voltar" type="button"
							onclick="javascript:history.back();">
					</logic:present>
					<logic:notPresent scope="session" name="manter">
						<input class="bottonRightCol" value="Voltar" type="button"
							onclick="window.location.href='/gsan/exibirFiltrarCategoriaAction.do';">
					</logic:notPresent>
					<input class="bottonRightCol" value="Desfazer" type="button"
						onclick="window.location.href='/gsan/exibirAtualizarCategoriaAction.do?idRegistroAtualizacao=<bean:write name='CategoriaActionForm' property='idCategoria' />';">	
					<input class="bottonRightCol" value="Cancelar" type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"> 
				</td>
				<td align="right">
					<%-- <input type="button" name="Button" class="bottonRightCol" value="Atualizar" onClick="javascript:validarForm(document.forms[0])" /> --%>
					<gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validarForm(document.forms[0])" url="atualizarCategoriaAction.do" />
				</td>
                <td>&nbsp;</td>
              </tr>
            </table>

          	<p>&nbsp;</p>
			<!-- Fim do Corpo - Roberta Costa -->          	
		</td>
	</tr>
</table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>