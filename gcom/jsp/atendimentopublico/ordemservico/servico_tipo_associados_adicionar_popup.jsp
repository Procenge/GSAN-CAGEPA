<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="PesquisarServicoTipoAssociadoActionForm"/>

<script>

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		
		alert('Entrou no recuperarDadosPopup');
  		
  		var form = document.forms[0];
    	if (tipoConsulta == 'servicoTipo') {
	 	  	form.idServicoTipo.value = codigoRegistro;
	 	  	form.descricaoServicoTipo.value = descricaoRegistro;
 		}
 	}

	function verificarInclusaoConfirmada() {
		var inclusaoConfirmada = "" + "${inclusaoConfirmada}";
		if (inclusaoConfirmada != "" && inclusaoConfirmada == "true") {
			opener.reload();
			self.close();
		}
	}
	
	function popupServicoTipo() {
		redirecionarSubmit('exibirPesquisarTipoServicoAction.do?caminhoRetornoTelaPesquisaTipoServico=exibirPesquisarServicoTipoAssociadoAction&tipoConsulta=servicoTipo&forward=exibirServicoTipoAssociadoPopup');
	}

	function validarForm() {
		var form = document.forms[0];
		if (validatePesquisarServicoTipoAssociadoActionForm(form)) {
			var idServicoAssociadoEditar = "" + "${idServicoAssociadoEditar}";
			if (idServicoAssociadoEditar != "") {
    			form.action = 'pesquisarServicoTipoAssociadoAction.do?idServicoAssociadoEditar=' + idServicoAssociadoEditar;
    			form.submit();
    		} else {
    		    form.submit();
    		}
		}
	}

	/* Limpa Form */
	function limparForm() {
		var form = document.forms[0];
    	form.idServicoTipo.value = "";
	    form.descricaoServicoTipo.value = "";
	    form.abreviaturaAtividade.value = "";
	    form.action = 'exibirPesquisarServicoTipoAssociadoAction.do';
		form.submit();
	}
	
	function limparPesquisaServicoTipo() {
		var form = document.forms[0];
    	form.idServicoTipo.value = "";
	    form.descricaoServicoTipo.value = "";
	}
	
	function limparPesquisaUnidadeDestino() {
		var form = document.forms[0];
    	form.idUnidadeDestino.value = "";
	    form.descricaoUnidadeDestino.value = "";
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado) {
		if (objetoRelacionado.disabled != true) {
			if (objeto == null || codigoObjeto == null) {
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else {
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)) {
					alert(msg);
				} else {
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
</script>
</head>

<body onload="resizePageSemLink(630,530);verificarInclusaoConfirmada();">

<html:form action="/pesquisarServicoTipoAssociadoAction.do" method="post">

<table width="586" border="0" cellpadding="0" cellspacing="5">
  	<tr> 
    	<td width="576" valign="top" class="centercoltext">
    		<table height="100%">
        		<tr>
          			<td></td>
        		</tr>
      		</table>
      		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        		<tr>
          			<td width="14"><img src="imagens/parahead_left.gif" border="0" /></td>
          			<td class="parabg">Adicionar Servi&ccedil;os Associados</td>
          			<td width="14"><img src="imagens/parahead_right.gif" border="0" /></td>
        		</tr>
      		</table>
      		<p>&nbsp;</p>
      		<table width="100%" border="0">
        		<tr>
          			<td colspan="3">Preencha os campos para inserir um servi&ccedil;o associado:</td>
        		</tr>
		       	<tr>
			       	<td width="25%" height="24">
			       		<strong>Tipo de serviço:<span class="style3"><font color="#FF0000">*</font></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="idServicoTipo" size="6" maxlength="6" tabindex="1"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoAssociadoAction.do?objetoConsulta=1','idServicoTipo');" /> 
						<a href="javascript:redirecionarSubmit('exibirPesquisarTipoServicoAction.do?caminhoRetornoTelaPesquisaTipoServico=exibirPesquisarServicoTipoAssociadoAction');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Tipo de Serviço" />
						</a>
						<logic:empty name="PesquisarServicoTipoAssociadoActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoAssociadoActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaServicoTipo();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Sequência de Gera&ccedil;ão:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				<strong><span class="style2">
            			<html:text property="sequencial" size="4" maxlength="3" tabindex="2" />
            			</span>
          				</strong>
          			</td>
        		</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Evento Associado:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
	          			<c:if test="${not empty colecaoEventoGeracao}">
		          			<html:select property="idEventoGeracao" tabindex="3">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoEventoGeracao" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoEventoGeracao}">
		          			<html:select property="idEventoGeracao" tabindex="3">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Forma de Gera&ccedil;ão:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				 <c:if test="${not empty colecaoFormaGeracao}">
		          			<html:select property="idFormaGeracao" tabindex="4">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoFormaGeracao" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoFormaGeracao}">
		          			<html:select property="idFormaGeracao" tabindex="4">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Forma de Trâmite:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          			    <c:if test="${not empty colecaoFormaTramite}">
		          			<html:select property="idFormaTramite" tabindex="5">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoFormaTramite" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoFormaTramite}">
		          			<html:select property="idFormaTramite" tabindex="5">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		<tr>
			       	<td width="25%" height="24">
			       		<strong>Unidade de Destino:<span class="style3"></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="idUnidadeDestino" size="6" maxlength="6" tabindex="6"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoAssociadoAction.do','idUnidadeDestino');" /> 
						<a href="javascript:redirecionarSubmit('exibirPesquisarUnidadeOrganizacionalAction.do?caminhoRetornoTelaPesquisaUnidadeOrganizacional=exibirPesquisarServicoTipoAssociadoAction&tipoUnidade=unidadeAtendimento');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Unidade de Destino" />
						</a>
						<logic:empty name="PesquisarServicoTipoAssociadoActionForm" property="idUnidadeDestino">
							<html:text property="descricaoUnidadeDestino" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoAssociadoActionForm" property="idUnidadeDestino">
							<html:text property="descricaoUnidadeDestino" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaUnidadeDestino();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>
        		
        		
        		<tr>
          			<td height="24"><span class="style3"><strong>Forma de Encerramento:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				<c:if test="${not empty colecaoFormaEncerramento}">
		          			<html:select property="idFormaEncerramento" tabindex="7">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoFormaEncerramento" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoFormaEncerramento}">
		          			<html:select property="idFormaEncerramento" tabindex="7">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Programa&ccedil;ão:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				<c:if test="${not empty colecaoFormaProgramacao}">
		          			<html:select property="idFormaProgramacao" tabindex="8">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoFormaProgramacao" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoFormaProgramacao}">
		          			<html:select property="idFormaProgramacao" tabindex="8">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		<tr>
          			<td height="24"><span class="style3"><strong>Sele&ccedil;ão de Equipe:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				<c:if test="${not empty colecaoFormaSelecaoEquipe}">
		          			<html:select property="idFormaSelecaoEquipe" tabindex="9">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoFormaSelecaoEquipe" labelProperty="descricao" property="id" />
							</html:select>
						</c:if>
						<c:if test="${empty colecaoFormaSelecaoEquipe}">
		          			<html:select property="idFormaSelecaoEquipe" tabindex="9">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</c:if>
          			</td>
        		</tr>
        		
        		<tr>
          			<td height="24"><strong> </strong></td>
          			<td colspan="2"><strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
        		</tr>
        		<tr>
          			<td height="27" colspan="2">
            		</td>
          			<td width="60%" height="27">
          				<logic:empty name="idServicoAssociadoEditar">
	          				<div align="right">
	            				<input name="Button3" type="button" class="bottonRightCol" value="Inserir" tabindex="10" onclick="javascript:validarForm();">
	          				</div>
          				</logic:empty>
          				<logic:notEmpty name="idServicoAssociadoEditar">
	          				<div align="right">
	            				<input name="Button3" type="button" class="bottonRightCol" value="Editar" tabindex="10" onclick="javascript:validarForm();">
	          				</div>
          				</logic:notEmpty>
          				
          			</td>
        		</tr>
      		</table>
      		<p>&nbsp;</p>
      	</td>
  	</tr>
</table>
</html:form>

</body>
</html>