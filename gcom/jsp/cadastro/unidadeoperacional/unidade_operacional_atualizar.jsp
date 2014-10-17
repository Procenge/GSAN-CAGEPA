<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.unidadeoperacional.UnidadeOperacional" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.cadastro.geografico.Municipio"%><html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">

function validarForm() {
    var form = document.forms[0];
	  if(validateAtualizarUnidadeOperacionalActionForm(form)){	     
		  if (form.ramal.value != '' && form.telefone.value == '' ){
		  	alert('Informe o Número do telefone');
		  }else{
		   	submeterFormPadrao(form); 
		  }
   	    }  		  
	  }

    function removerEndereco(url){

  		if(confirm('Confirma remoção ?')){
  	       var form = document.forms[0];
  	    	form.action = url;
  		    form.submit()	
  		}
  		

  	}

    function limparPesquisaDescricaoLocalidade() {
	    var form = document.forms[0];
	    form.descricaoLocalidade.value = "";
	 }

	function limparPesquisaLocalidade() {
	  var form = document.forms[0];
      form.localidade.value = "";
      form.descricaoLocalidade.value = "";
	}

	//Recebe os dados do(s) popup(s)
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	  var form = document.forms[0];

	  if (tipoConsulta == 'localidade') {
	    limparPesquisaLocalidade();
	    form.localidade.value = codigoRegistro;
	    form.descricaoLocalidade.value = descricaoRegistro;
	    form.descricaoLocalidade.style.color = "#000000";
	  }
	}

</script>
</head>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarUnidadeOperacionalActionForm" />

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/atualizarUnidadeOperacionalAction.do"
	name="AtualizarUnidadeOperacionalActionForm"
	type="gcom.gui.cadastro.unidadeoperacional.AtualizarUnidadeOperacionalActionForm"
	method="post"
	onsubmit="return validateAtualizarUnidadeOperacionalActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">
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
					<td class="parabg">Atualizar Distrito Operacional</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				
			<tr>
			  <td  width="40%"class="style3"><strong>Sistema de Abastecimento:<font color="#FF0000">*</font></strong></td>
			  <td  width="60%" colspan="2">
			  			<html:select property="sistemaAbastecimento" disabled="true" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoSistemaAbastecimento" property="id" labelProperty="descricaoComId"/>
						</html:select></td>
			</tr>
				
			<tr>
			  <td  width="40%" class="style3"><strong>Código do Distrito Operacional:<font color="#FF0000">*</font></strong></td>
			  <td  width="60%" colspan="2"><strong><b><span class="style2"> <html:text
				  property="codigoUnidadeOperacional" size="3" maxlength="3" disabled="true"/> </span></b></strong></td>
			</tr>	
								
			
			<tr>
			  <td  width="40%" class="style3"><strong>Descrição do Distrito Operacional:<font color="#FF0000">*</font></strong></td>
			  <td  width="60%" colspan="2"><strong><b><span class="style2"> <html:text
				  property="descricao" size="40" maxlength="30" tabindex="1"/> </span></b></strong></td>
			</tr>
			
			
			<tr>
			  <td  width="40%" class="style3"><strong>Descrição Abreviada do Distrito Operacional:</strong></td>
			  <td  width="60%" colspan="2"><strong><b><span class="style2"> <html:text
				  property="descricaoAbreviada" size="10" maxlength="6" tabindex="2"/> </span></b></strong></td>
			</tr>
			
			<tr>
		   			<td width="30%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
                    <td width="70%" height="24" colspan="2">
                   		<html:text maxlength="3" property="localidade" size="3" tabindex="3" onkeypress="javascript:limparPesquisaDescricaoLocalidade(); return validaEnter(event, 'exibirAtualizarUnidadeOperacionalAction.do?recarregar=true', 'localidade');"/>
                      	<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 400, 800);">
                        	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
                        </a>
   		      			<logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
							<input type="text" name="descricaoLocalidade" size="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.localidade.inexistente"/>"/>
                   		</logic:present>
						<logic:notPresent name="codigoLocalidadeNaoEncontrada" scope="request">
							<html:text property="descricaoLocalidade" size="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						<a href="javascript:limparPesquisaLocalidade();document.forms[0].idLocalidade.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
                 </tr>
			<tr>
					<td width="183"><strong>Endereço do Distrito Operacional </strong></td>
					<td width="432" align="right">
					
					<logic:present name="colecaoEnderecos">
						
						<logic:empty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar"
								id="botaoEndereco"
								onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=unidadeOperacional&operacao=2', 570, 700);" tabindex="2">
							<INPUT TYPE="hidden" id="validarEndereco" value="0">
						</logic:empty>
						
						<logic:notEmpty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar"
								id="botaoEndereco"
								onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=unidadeOperacional&operacao=2', 570, 700);"
								disabled="disabled" tabindex="4" >
							<INPUT TYPE="hidden" id="validarEndereco" value="1">
						</logic:notEmpty>
					
					</logic:present> 
					
					<logic:notPresent name="colecaoEnderecos">
					
						<input type="button" class="bottonRightCol" value="Adicionar"
							id="botaoEndereco"
							onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=unidadeOperacional&operacao=2', 570, 700);">
					
						<INPUT TYPE="hidden" id="validarEndereco" value="0">
					</logic:notPresent>
					
					</td>
				</tr>
			<tr>
				<td colspan="2">
					<table width="100%" bgcolor="#90c7fc">
						<!--header da tabela interna -->
						<tr>
							<td width="50" align="center"><strong>Remover</strong></td>
							<td align="center"><strong>Endere&ccedil;o</strong></td>
						</tr>
					</table>
				</td>
			</tr>
			<logic:present name="colecaoEnderecos">

							<tr>
								<td height="40" colspan="2">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" align="center" bgcolor="#99CCFF">
									<!--corpo da segunda tabela-->

									<%String cor = "#cbe5fe";%>

									<logic:iterate name="colecaoEnderecos" id="endereco" type="UnidadeOperacional">

										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">
											<%} else {
				cor = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>

											<td width="50" align="center"><a
												href="javascript:removerEndereco('removerAlterarUnidadeOperacionalColecaoEnderecoAction.do?enderecoRemoverSelecao=<%=""+endereco.getUltimaAlteracao().getTime()%>');"><img
												border="0"
												src="<bean:message key="caminho.imagens"/>Error.gif" /></a>
											</td>
											<td>
											
												<a href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=unidadeOperacional&operacao=2', 570, 700)"><bean:write name="endereco" property="enderecoFormatado"/></a>
											
											</td>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>

						</logic:present>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Número do telefone:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="telefone" size="12" maxlength="9" tabindex="5"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Número do ramal:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="ramal" size="6" maxlength="4" tabindex="6"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Número do fax:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="fax" size="12" maxlength="9" tabindex="7"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>E-mail:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="email" size="50" maxlength="40" tabindex="8"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Descrição do tipo de Instalação:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="descricaoTipoInstalacao" size="40" maxlength="30" tabindex="9"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Número da Cota:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="numeroCota" size="12" maxlength="9" tabindex="10"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Latitude do Lugar:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="latitude" size="12" maxlength="9" tabindex="11"/>
			  				 </span></b></strong></td>
			</tr>
			
			<tr>
					    <td  width="40%"class="style3"><strong>Longitude do Lugar:</strong></td>
			  			<td  width="60%" colspan="2"><strong><b><span class="style2"> 
			  				<html:text property="longitude" size="12" maxlength="9" tabindex="12"/>
			  				 </span></b></strong></td>
			</tr>
			<tr>
					<td><strong>Indicador de uso:<font color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorUso" value="1" tabindex="13"/><strong>Ativo
					<html:radio property="indicadorUso" value="2" tabindex="14"/>Inativo</strong>
					</td>
			</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
					<td width="500" colspan="2">
					<div align="center" ><font color="#FF0000">*</font> Campos
					obrigatórios</div>
					</td>
				</tr>
			
			</table>
			<table width="100%">
				<tr>
					<td width="40%" align="left">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar"
						onClick="javascript:window.location.href='/gsan/exibirManterUnidadeOperacionalAction.do';">
						<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
						onclick="window.location.href='/gsan/exibirAtualizarUnidadeOperacionalAction.do?desfazer=S&reloadPage=1&idUnidadeOperacional=<bean:write name="AtualizarUnidadeOperacionalActionForm" property="id" />';">
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right">
						<input type="button" name="Button" class="bottonRightCol"
						value="Atualizar" onclick="validarForm();" tabindex="15" />
					</td>
				</tr>
			</table>
		</tr>


	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
