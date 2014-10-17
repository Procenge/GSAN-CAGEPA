<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm"
	dynamicJavascript="false" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="Javascript">
<!--

     var bCancel = false;

    function validateFiltrarQualidadeAguaActionForm(form) {
        if (bCancel)
      return true;
        else
       return testarCampoValorZero(document.forms[0].idLocalidade, 'Localidade') 
       && testarCampoValorZero(document.forms[0].codigoSetor, 'Setor Comercial')
       && validateCaracterEspecial(form) && validateLong(form);
   }

    function caracteresespeciais  () {

     this.aa = new Array("idLocalidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
 	 this.ab = new Array("codigoSetor", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("fonteCaptacao", "Fonte de Captação possui caracteres especiais.", new Function ("varName", " return this[varName];"));


    }

    function IntegerValidations  () {
     this.ab = new Array("idLocalidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("codigoSetor", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));

    }




    //Recebe os dados do(s) popup(s)
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

      var form = document.forms[0];

       if (tipoConsulta == 'localidade') {
        form.idLocalidade.value = codigoRegistro;
        form.nomeLocalidade.value = descricaoRegistro;
        form.nomeLocalidade.style.color = "#000000";
      }else if (tipoConsulta == 'setorComercial') {
        form.codigoSetor.value = codigoRegistro;
        form.nomeSetor.value = descricaoRegistro;
        form.nomeSetor.style.color = "#000000";
      }



    }


    function limparPesquisa() {
        var form = document.forms[0];

        form.idLocalidade.value = "";
        form.nomeLocalidade.value = "";
    }
    
    function limpaLocalidade(){
		form = document.forms[0];
	
		form.nomeLocalidade.value = "";
		form.idLocalidade.value = "";
		form.idLocalidade.focus();
    }
    
    function limpaSetor(){
		form = document.forms[0];
		
		form.nomeSetor.value = "";
		form.codigoSetor.value = "";
		form.codigoSetor.focus();
    }

function limpaNomeLocalidade(){
	var form = document.forms[0];

	form.nomeLocalidade.value = "";
}

function limpaNomeSetor(){
	var form = document.forms[0];

	form.nomeSetor.value = "";
}
    
    

function validarForm(){
	var form = document.forms[0];
	if(validateFiltrarQualidadeAguaActionForm(form)){
		if(form.referencia.value.length > 6){
			var referenciaMes = form.referencia.value.substring(0,2);
			

			if(referenciaMes < 13 && referenciaMes>0){
				form.action = "filtrarQualidadeAguaAction.do";
        		form.submit();
        		}else{
        			alert("Mês de Referência inválido")
            	}
		}else{
			if(form.referencia.value.length > 0){
				alert("Período de Referência inválido");
			} else {
				form.action = "filtrarQualidadeAguaAction.do";
        		form.submit();
			}
		}
	}
}

-->
</script>

</head>


<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarQualidadeAguaAction" method="post"
	onsubmit="return validateFiltrarQualidadeAguaActionForm(this);">

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
					<td class="parabg">Filtrar Qualidade da Água</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0">
				<tr>
					<td>
					Para manter a(s) qualidade(s) da água, informe os dados abaixo:
					</td>
					<td width="84"><input name="atualizar" type="checkbox"
						checked="checked" value="1"> <strong>Atualizar</strong></td>
					<td align="right"></td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
   			
   				<tr>
					<td><strong>Referência:</strong></td>
					<td><html:text property="referencia" size="8" tabindex="1"
						maxlength="7" onkeyup="mascaraAnoMes(this, event);"/></td>
				</tr>
   				
				<tr>
					<td width="130"><strong>Localidade:</strong></td>
					<td width="480"><html:text maxlength="4" property="idLocalidade"
						tabindex="2" size="4" onkeyup="javascript:limpaNomeLocalidade();"
						onkeypress="javascript:return validaEnter(event, 'exibirFiltrarQualidadeAguaAction.do', 'idLocalidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> <logic:present
						name="idLocalidadeNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeLocalidade" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent name="idLocalidadeNaoEncontrado"
						scope="request">
						<html:text maxlength="30" property="nomeLocalidade" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limpaLocalidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>



				</tr>
				
				<tr>
					<td width="130"><strong>Setor Comercial:</strong></td>
					<td width="480"><html:text maxlength="4" property="codigoSetor"
						tabindex="3" size="4" onkeyup="javascript:limpaNomeSetor();"
						onkeypress="javascript:return validaEnter(event, 'exibirFiltrarQualidadeAguaAction.do', 'codigoSetor');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarSetorComercialAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> <logic:present
						name="codigoSetorNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeSetor" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent name="codigoSetorNaoEncontrado"
						scope="request">
						<html:text maxlength="30" property="nomeSetor" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limpaSetor();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>



				</tr>
				<tr>
					<td><strong> Fonte de Captação:</strong></td>
					<td><html:text property="fonteCaptacao" size="30" tabindex="4"
						maxlength="30" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto<html:radio property="tipoPesquisa"
						tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong> <font color="#ff0000"> <input name="Submit22"
						class="bottonRightCol" value="Limpar" type="button"
						onclick="window.location.href='/gsan/exibirFiltrarQualidadeAguaAction.do?menu=sim';">
					</font></strong></td>
					<td align="right"></td>
					<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
							  onclick="javascript:validarForm();" url="/filtrarQualidadeAguaAction.do"/>
			</table>

			<p>&nbsp;</p>

			</td>
		</tr>

	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
