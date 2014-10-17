<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarServicoTipoValorLocalidadeActionForm"/>

<script>

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
  		var form = document.forms[0];
    	
    	if (tipoConsulta == 'localidade') {
	 	  	form.idLocalidadeFiltro.value = codigoRegistro;
	 	  	form.localidadeDescricaoFiltro.value = descricaoRegistro; 
 		}
  	}

	function validarForm() {
		var form = document.forms[0];
		var concluir = true;
		
		if (form.valor.value == ''){
			alert("Informe valor");
			concluir = false;
		} 
		
		if (form.idLocalidadeFiltro.value == ''){
			alert("Informe localidade");
			concluir = false;
		} 
		
		if(concluir){
			form.method.value = "inserir";
    		form.submit();
		}
			

	}

	/* Limpa Form */	 
	function limpar() {
		var form = document.forms[0];
    	form.idLocalidadeFiltro.value = "";
    	form.localidadeDescricaoFiltro.value = "";
    	form.valor.value = "";
	}  
	
	function whenOnload() {
		if (${requestScope.close != null}) {
			var form = document.forms[0];
			var a = form.idLocalidadeFiltro.value;
			var b = form.localidadeDescricaoFiltro.value;
			var c = form.valor.value;
			enviarDadosQuatroParametros(a, b, c, 'servicoValorLocalidade'); 
		}
	}


</script>
</head>

<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(620, 300);setarFoco('${requestScope.nomeCampo}');whenOnload();">
<html:form
  action="/exibirPesquisarServicoTipoValorLocalidadeAction"
  name="PesquisarServicoTipoValorLocalidadeActionForm"
  type="gcom.gui.atendimentopublico.ordemservico.PesquisarServicoTipoValorLocalidadeActionForm"
  method="post"
>
<input type="hidden" name="method" value="${"PesquisarServicoTipoValorLocalidadeActionForm.method"}" />
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
          			<td class="parabg">Adicionar Atividades do Tipo de Servi&ccedil;o Valor Localidade</td>
          			<td width="14"><img src="imagens/parahead_right.gif" border="0" /></td>
        		</tr>
      		</table>
      		<p>&nbsp;</p>
      		<table width="100%" border="0">
        		<tr> 
          			<td colspan="3">Preencha os campos para inserir um atividades do tipo de servi&ccedil;o Valor Localidade:</td>
        		</tr>
       			<tr>
					<td width="19%"><strong>Localidade:</strong></td>
					<td colspan="2" width="81%" height="24"><html:text maxlength="3"
						tabindex="1" property="idLocalidadeFiltro" size="3"
						onkeypress="javascript:validaEnter(event, 'exibirPesquisarServicoTipoValorLocalidadeAction.do?objetoConsulta=1', 'idLocalidadeFiltro');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
					<logic:present name="localidadeEncontrado">
						<logic:equal name="localidadeEncontrado" value="exception">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="localidadeEncontrado" value="exception">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="localidadeEncontrado">
						<logic:empty name="PesquisarServicoTipoValorLocalidadeActionForm"
							property="idLocalidadeFiltro">
							<html:text property="localidadeDescricaoFiltro" value=""
								size="40" maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarServicoTipoValorLocalidadeActionForm"
							property="idLocalidadeFiltro">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a
						href="javascript:limpar();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
        		
        		<tr>
          			<td height="24"><span class="style3"><strong>Valor:<font color="#FF0000">*</font></strong></span></td>
          			<td height="24" colspan="2">
          				<strong><span class="style2">
            			<html:text property="valor" onkeyup="javaScript:formataValorMonetario(this, 8);" size="10" maxlength="22" />
            			</span>
          				</strong>
          			</td>
        		</tr>
        		<tr> 
          			<td height="24"><strong> </strong></td>
          			<td colspan="2"><strong><font color="#FF0000">*</font></strong> Campos 
            		obrigat&oacute;rios</td>
        		</tr>
        		<tr> 
          			<td height="27" colspan="2"> 
            		</td>
          			<td width="60%" height="27">
          				<div align="right">
            			<input name="Button3" 
            				   type="button" 
            				   class="bottonRightCol" 
            				   value="Inserir"
            				   onclick="javascript:validarForm();">
          				</div>
          			</td>
        		</tr>
      		</table>
      		<p>&nbsp;</p>
      	</td>
  	</tr>
</table>
</html:form>
</body>
</html:html>
