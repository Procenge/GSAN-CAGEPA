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

<html:javascript staticJavascript="false" formName="PesquisarServicoTipoTramiteActionForm"/>

<script>
	function verificarInclusaoConfirmada() {
		var inclusaoConfirmada = "" + "${inclusaoConfirmada}";
		if (inclusaoConfirmada != "" && inclusaoConfirmada == "true") {
			opener.reload();
			self.close();
		}
	}
	
	function validarForm() {
		var form = document.forms[0];
		if (validatePesquisarServicoTipoTramiteActionForm(form)) {
			var idServicoTipoTramiteEditar = "" + "${idServicoTipoTramiteEditar}";
			if (idServicoTipoTramiteEditar != "") {
    			form.action = 'pesquisarServicoTipoTramiteAction.do?idServicoTipoTramiteEditar=' + idServicoTipoTramiteEditar;
    			form.submit();
    		} else {
    		    form.submit();
    		}
		}
	}

	function limparPesquisaLocalidade() {
		var form = document.forms[0];
    	form.idLocalidade.value = "";
	    form.descricaoLocalidade.value = "";

	    limparPesquisaSetorComercial();
	}

	function limparPesquisaSetorComercial() {
		var form = document.forms[0];
    	form.codigoSetorComercial.value = "";
	    form.descricaoSetorComercial.value = "";
	}

	function limparPesquisaUnidadeOrigem() {
		var form = document.forms[0];
    	form.idUnidadeOrganizacionalOrigem.value = "";
	    form.descricaoUnidadeOrganizacionalOrigem.value = "";
	}

	function limparPesquisaUnidadeDestino() {
		var form = document.forms[0];
    	form.idUnidadeOrganizacionalDestino.value = "";
	    form.descricaoUnidadeOrganizacionalDestino.value = "";
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

<body onload="resizePageSemLink(635,370);verificarInclusaoConfirmada();">

<html:form action="/pesquisarServicoTipoTramiteAction.do" method="post">

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
          			<td class="parabg">Adicionar Servi&ccedil;os Trâmites</td>
          			<td width="14"><img src="imagens/parahead_right.gif" border="0" /></td>
        		</tr>
      		</table>
      		<p>&nbsp;</p>
      		<table width="100%" border="0">
        		<tr>
          			<td colspan="3">Preencha os campos para inserir um servi&ccedil;o trâmite:</td>
        		</tr>

		       	<html:hidden property="id"/>

		       	<tr>
			       	<td width="25%" height="24">
			       		<strong>Localidade:<span class="style3"></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="idLocalidade" size="7" maxlength="3" tabindex="1"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoTramiteAction.do?objetoConsulta=1','idLocalidade');" /> 
						<a href="javascript:redirecionarSubmit('exibirPesquisarLocalidadeAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarServicoTipoTramiteAction');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Localidade" />
						</a>
						<logic:empty name="PesquisarServicoTipoTramiteActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoTramiteActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>

		       	<tr>
			       	<td width="25%" height="24">
			       		<strong>Setor Comercial:<span class="style3"></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="codigoSetorComercial" size="7" maxlength="3" tabindex="2"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoTramiteAction.do?objetoConsulta=2','codigoSetorComercial');" /> 
						<a href="javascript:redirecionarSubmitDependencia('exibirPesquisarSetorComercialAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarServicoTipoTramiteAction&idLocalidade='+document.forms[0].idLocalidade.value,document.forms[0].idLocalidade.value,'Localidade');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Setor Comercial" />
						</a>
						<logic:empty name="PesquisarServicoTipoTramiteActionForm" property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoTramiteActionForm" property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaSetorComercial();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>

        		<tr>
			       	<td width="25%" height="24">
			       		<strong>Unidade de Origem:<span class="style3"></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="idUnidadeOrganizacionalOrigem" size="7" maxlength="8" tabindex="3"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoTramiteAction.do?objetoConsulta=3','idUnidadeOrganizacionalOrigem');" /> 
						<a href="javascript:redirecionarSubmit('exibirPesquisarUnidadeOrganizacionalAction.do?caminhoRetornoTelaPesquisaUnidadeOrganizacional=exibirPesquisarServicoTipoTramiteAction&tipoUnidade=unidadeAtendimento&campoUnidade=origem');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Unidade de Origem" />
						</a>
						<logic:empty name="PesquisarServicoTipoTramiteActionForm" property="idUnidadeOrganizacionalOrigem">
							<html:text property="descricaoUnidadeOrganizacionalOrigem" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoTramiteActionForm" property="idUnidadeOrganizacionalOrigem">
							<html:text property="descricaoUnidadeOrganizacionalOrigem" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaUnidadeOrigem();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>

        		<tr>
			       	<td width="25%" height="24">
			       		<strong>Unidade de Destino:<span class="style3"><font color="#FF0000">*</font></span></strong>
			       	</td>
					<td colspan="2">
						<html:text property="idUnidadeOrganizacionalDestino" size="7" maxlength="8" tabindex="4"
							onkeypress="validaEnter(event, 'exibirPesquisarServicoTipoTramiteAction.do?objetoConsulta=4','idUnidadeOrganizacionalDestino');" /> 
						<a href="javascript:redirecionarSubmit('exibirPesquisarUnidadeOrganizacionalAction.do?caminhoRetornoTelaPesquisaUnidadeOrganizacional=exibirPesquisarServicoTipoTramiteAction&tipoUnidade=unidadeAtendimento&campoUnidade=destino');">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Unidade de Destino" />
						</a>
						<logic:empty name="PesquisarServicoTipoTramiteActionForm" property="idUnidadeOrganizacionalDestino">
							<html:text property="descricaoUnidadeOrganizacionalDestino" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoTramiteActionForm" property="idUnidadeOrganizacionalDestino">
							<html:text property="descricaoUnidadeOrganizacionalDestino" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparPesquisaUnidadeDestino();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
						</a>
					</td>
		       	</tr>

        		<tr>
          			<td height="24"><strong></strong></td>
          			<td colspan="2"><strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
        		</tr>
        		<tr>
          			<td height="27" colspan="2">
            		</td>
          			<td width="60%" height="27">
          				<logic:empty name="idServicoTipoTramiteEditar">
	          				<div align="right">
	            				<input name="Button3" type="button" class="bottonRightCol" value="Inserir" tabindex="10" onclick="javascript:validarForm();">
	          				</div>
          				</logic:empty>
          				<logic:notEmpty name="idServicoTipoTramiteEditar">
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