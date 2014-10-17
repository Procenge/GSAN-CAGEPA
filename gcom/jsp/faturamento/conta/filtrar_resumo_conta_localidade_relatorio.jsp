<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript">
<!-- Begin

 var bCancel = false;

    function validateFiltrarMapaControleContaRelatorioActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateRequired(form) && validaMesAno(form.mesAno);
   }
   
    function required () {
     this.aa = new Array("idGrupoFaturamento", "Informe Grupo.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("mesAno", "Informe Mês/Ano.", new Function ("varName", " return this[varName];"));
    }

	function validaMesAno(mesAno){
		if(mesAno.value != ""){
			return verificaAnoMes(mesAno);
		}else{
			return true;
		}
	}
	
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro,
			tipoConsulta) {

		var form = document.FiltrarMapaControleContaRelatorioActionForm;

		if (tipoConsulta == 'localidade') {
			form.idLocalidade.value = codigoRegistro;
			form.descricaoLocalidade.value = descricaoRegistro;
			form.descricaoLocalidade.style.color = "#000000";
		}

		if (tipoConsulta == 'setorComercial') {
			form.idSetorComercial.value = codigoRegistro;
			form.descricaoSetorComercial.value = descricaoRegistro;
			form.descricaoSetorComercial.style.color = "#000000";
			form.action = 'exibirMapaControContalaRelatorioAction.do?caminho=ResumoContaLocalidade'
		    	  form.submit();

		}

	}

	function chamarFiltrar() {
		var form = document.forms[0];
		//if(form.idGrupoFaturamento.value == '-1'){
		//alert('Informe Grupo');
		//  }else{
		if (validateFiltrarMapaControleContaRelatorioActionForm(form)) {
			toggleBox('demodiv', 1);
		}
		//}
	}

	function validarLocalidade(){

		var form = document.forms[0];
		
		if(form.idLocalidade.value == ""){
			alert("Informe Localidade");
			form.idLocalidade.focus();
		}else{
			abrirPopup('exibirPesquisarSetorComercialAction.do');
		}
	}
	
	function validarLocalidadeEnter(){

		var form = document.forms[0];
		
		if(form.idLocalidade.value == ""){
			alert("Informe Localidade");			
			form.idLocalidade.focus();
			form.idSetorComercial.value = "";
		}else{
			validaEnterComMensagem(event, 'exibirMapaControContalaRelatorioAction.do?caminho=ResumoContaLocalidade&objetoConsulta=2', 'idSetorComercial', 'Setor Comercial');
		}
	}

	function limparLocalidade() {
		var form = document.FiltrarMapaControleContaRelatorioActionForm;
		form.idLocalidade.value = "";
		form.descricaoLocalidade.value = "";
		form.idSetorComercial.value = "";
		form.descricaoSetorComercial.value = "";
	}

	function limparSetorComercial() {
		var form = document.FiltrarMapaControleContaRelatorioActionForm;
		form.idSetorComercial.value = "";
		form.descricaoSetorComercial.value = "";
	}

	function limpar() {
		var form = document.FiltrarMapaControleContaRelatorioActionForm;
		form.idGrupoFaturamento.value = "";
		form.firma.value = "";
		form.mesAno.value = "";
		form.idSetorComercial.value = "";
		form.descricaoSetorComercial.value = "";
		form.idLocalidade.value = "";
		form.descricaoLocalidade.value = "";
	}

	-->
</script>
</head>

<body leftmargin="5" topmargin="5">
<html:form
  action="/filtrarResumoContaLocalidadeRelatorioAction.do"
  method="post"
  name="FiltrarMapaControleContaRelatorioActionForm"
  type="gcom.gui.faturamento.conta.FiltrarMapaControleContaRelatorioActionForm"
  onsubmit="return validateFiltrarMapaControleContaRelatorioActionForm(this);"
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

         <%@ include file="/jsp/util/mensagens.jsp"%>

        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
      </div></td>
    <td width="625" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>

          <td class="parabg">Filtrar Resumo de Contas Emitidas por Localidade no Grupo</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
	  <table width="100%" border="0">
        <tr>
        	<td width="100%" colspan=2>
	        	<table width="100%" border="0">
		          	<tr>
		          		<td>Para filtrar a(s) conta(s) emitida(s), informe os dados abaixo:</td>
		          	</tr>
		          </table>
        	</td>
        </tr>
        <tr>
          <td width="10%"><strong>Grupo:</strong><font color="#FF0000">*</font> </td>
          <td width="90%"><html:select property="idGrupoFaturamento" tabindex="1">
          		<html:option value="-1">&nbsp;</html:option>
              <html:options collection="faturamentoGrupos" labelProperty="descricao" property="id"/>
             </html:select>
          </td>
        </tr>

						<tr>
							<td width="10%"><strong>Mês/Ano:</strong><font
								color="#FF0000">*</font></td>
							<td width="90%"><html:text property="mesAno" size="7"
									tabindex="2" maxlength="7"
									onkeypress="javascript:mascaraAnoMes(this, event);" />
								&nbsp;mm/aaaa</td>
						</tr>
						<tr>
							<td width="40%" class="style3"><strong>Empresa:</strong></td>
							<td width="60%" colspan="2"><html:select property="firma"
									tabindex="3" style="width:200px;">
									<html:option value="-1"> &nbsp; </html:option>
									<html:options collection="colecaoEmpresas" property="id"
										labelProperty="descricao" />
								</html:select></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						<tr>						
						<tr>
							<td width="183"><strong>Localidade:</strong></td>
							<td width="432"><html:text property="idLocalidade" size="3"
									maxlength="3" tabindex="1"
									onkeypress="validaEnterComMensagem(event, 'exibirMapaControContalaRelatorioAction.do?caminho=ResumoContaLocalidade&objetoConsulta=1', 'idLocalidade', 'Localidade');" />
								<a
								href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 250, 495);"><img
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" width="23" height="21" title="Pesquisar Localidade"></a>
								<logic:present name="corLocalidade">
									<logic:equal name="corLocalidade" value="exception">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:equal>

									<logic:notEqual name="corLocalidade" value="exception">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEqual>
									<a href="javascript:limparLocalidade();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" />
									</a>
								</logic:present> <logic:notPresent name="corLocalidade">

									<logic:empty name="FiltrarMapaControleContaRelatorioActionForm"
										property="idLocalidade">
										<html:text property="descricaoLocalidade" value="" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:empty>
									<logic:notEmpty
										name="FiltrarMapaControleContaRelatorioActionForm"
										property="idLocalidade">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEmpty>
									<a href="javascript:limparLocalidade();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" />
									</a>
								</logic:notPresent></td>
						</tr>

						<tr>						
						<tr>
							<td width="183"><strong>Código Setor Comercial:</strong></td>
							<td width="432"><html:text property="idSetorComercial"
									size="3" maxlength="3" tabindex="1"
									onkeyup="validarLocalidadeEnter()" /> <a
								href="javascript:validarLocalidade();"><img
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" width="23" height="21"
									title="Pesquisar Setor Comercial"></a> <logic:present
									name="corLocalidade">
									<logic:equal name="corLocalidade" value="exception">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:equal>

									<logic:notEqual name="corLocalidade" value="exception">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEqual>
									<a href="javascript:limparLocalidade();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" />
									</a>
								</logic:present> <logic:notPresent name="corLocalidade">

									<logic:empty name="FiltrarMapaControleContaRelatorioActionForm"
										property="idLocalidade">
										<html:text property="descricaoSetorComercial" value=""
											size="45" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:empty>
									<logic:notEmpty
										name="FiltrarMapaControleContaRelatorioActionForm"
										property="idLocalidade">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEmpty>
									<a href="javascript:limparSetorComercial();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" />
									</a>
								</logic:notPresent></td>
						</tr>
						<tr>
          <td>
          	<input type="button" name="Button" class="bottonRightCol" value="Limpar" onclick="javascript:limpar();"/>
          </td>
          <td><div align="right">
   	         <gcom:controleAcessoBotao  name="Button" value="Gerar"
							  onclick="javascript:chamarFiltrar();" url="filtrarResumoContaLocalidadeRelatorioAction.do"/>
		                    	<%--
          <input type="button" name="Button" class="bottonRightCol" value="Filtrar" onClick="javascript:chamarFiltrar();"/>--%>
          </div></td>
        </tr>
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>
<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=filtrarResumoContaLocalidadeRelatorioAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>


</html:form>
</body>
</html:html>