<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="RotaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){
		if(validateRotaActionForm(form)){
			submeterFormPadrao(form);
		}
	}

	function limparLocalidade(form) {
    	form.idLocalidade.value = "";
    	form.localidadeDescricao.value = "";
	}
	
	function limparSetorComercial(form) {
    	form.codigoSetorComercial.value = "";
    	form.setorComercialDescricao.value = "";
	}

	function limparDescLocalidade(form) {
    	form.localidadeDescricao.value = "";
    	form.codigoSetorComercial.value = "";
	}
	
	function limparDescSetorComercial(form) {
    	form.setorComercialDescricao.value = "";
	}
 	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'localidade') {
      	limparLocalidade(form);
      	form.idLocalidade.value = codigoRegistro;
      	form.localidadeDescricao.value = descricaoRegistro;
      	form.localidadeDescricao.style.color = "#000000";
      	form.codigoSetorComercial.focus();
    }

    if (tipoConsulta == 'setorComercial') {
      limparSetorComercial(form);
      form.codigoSetorComercial.value = codigoRegistro;
      form.setorComercialDescricao.value = descricaoRegistro;
      form.setorComercialDescricao.style.color = "#000000"
    }
    
    if (tipoConsulta == 'leiturista') {
       limparLeiturista(form);
	   form.codigoLeiturista.value = codigoRegistro;
	   form.nomeLeiturista.value = descricaoRegistro;
	   form.nomeLeiturista.style.color = "#000000";
	} 

   }

	function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	}
	
	function limparLeiturista(form) {
    	form.codigoLeiturista.value = "";
    	form.nomeLeiturista.value = "";
	}

	function limparForm(form){
		limparLocalidade(form);
		limparSetorComercial(form);
		limparDescLocalidade(form);
		limparDescSetorComercial(form);
		limparLeiturista(form);

	}

</script>
</head>
<body leftmargin="5" topmargin="5"
onload="setarFoco('${requestScope.nomeCampo}');verificarChecado('${sessionScope.indicadorAtualizar}');">
<html:form action="/filtrarRotaAction"
	name="RotaActionForm"
	type="gcom.gui.micromedicao.RotaActionForm"
	method="post"
	onsubmit="return validateRotaActionForm(this);">


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
                <td class="parabg">Filtrar Rota</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>


			<p>&nbsp;</p>
			<table width="100%" border="0">


				 <tr>
			          <td width="100%" colspan=2>
				          <table width="100%" border="0">
				          	<tr>
				          		<td>Para filtrar uma rota no sistema, informe os dados abaixo:</td>
				          		<td align="right"><input type="checkbox" name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
									
								</td>
								<td align="right"></td>												
				          	</tr>
				          </table>
			          </td>
			     </tr>
				
				
             	<tr>
					<td><strong>Localidade:</strong></td>
					<td><html:text maxlength="3" tabindex="1"
						property="idLocalidade" size="3"
						onkeypress="javascript:limparDescLocalidade(document.RotaActionForm);
						limparDescSetorComercial(document.RotaActionForm);
						validaEnterComMensagem(event, 'exibirFiltrarRotaAction.do?objetoConsulta=1', 'idLocalidade','Localidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
						
						<logic:present
							name="idLocalidadeNaoEncontrada">
						<logic:equal name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="localidadeDescricao" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						
						<logic:notEqual name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="localidadeDescricao" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						</logic:present> 
						
						<logic:notPresent name="idLocalidadeNaoEncontrada">
							<logic:empty name="RotaActionForm" property="idLocalidade">
								<html:text property="localidadeDescricao" value="" size="40"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="RotaActionForm" property="idLocalidade">
								<html:text property="localidadeDescricao" size="40"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>

					</logic:notPresent> 					<a
						href="javascript:limparLocalidade(document.RotaActionForm);
						limparSetorComercial(document.RotaActionForm);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a></td>
				</tr>
				

				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td height="24"><html:text maxlength="3"
						property="codigoSetorComercial" tabindex="2" size="3"
						onkeypress="javascript:limparDescSetorComercial(document.RotaActionForm);
						return validaEnterDependenciaComMensagem(event, 'exibirFiltrarRotaAction.do?objetoConsulta=1', this, document.forms[0].idLocalidade.value, 'Localidade','Setor Comercial');" />
						
						<a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);">
						
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> <logic:present
						name="idSetorComercialNaoEncontrada">
						<logic:equal name="idSetorComercialNaoEncontrada"
							value="exception">
							<html:text property="setorComercialDescricao" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idSetorComercialNaoEncontrada"
							value="exception">
							<html:text property="setorComercialDescricao" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="idSetorComercialNaoEncontrada">
						<logic:empty name="RotaActionForm"
							property="codigoSetorComercial">
							<html:text property="setorComercialDescricao" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="RotaActionForm"
							property="codigoSetorComercial">
							<html:text property="setorComercialDescricao" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
						<a
						href="javascript:limparSetorComercial(document.RotaActionForm);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a>
				</td>
				</tr>
             	<tr>
					<td><strong>Agente Comercial:</strong></td>
					<td><html:text maxlength="3" tabindex="3"
						property="codigoLeiturista" size="3"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarRotaAction.do', 'codigoLeiturista', 'Leiturista');" />
					<a href="javascript:abrirPopup('exibirPesquisarLeituristaAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Agente Comercial" />
					</a> 
						
						<logic:present name="idLeituristaEncontrado" scope="request">
						<html:text maxlength="30" property="nomeLeiturista"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:present> 
					<logic:notPresent name="idLeituristaEncontrado" scope="request">
						<html:text maxlength="30" property="nomeLeiturista"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:notPresent> 
					<a href="javascript:limparLeiturista(document.RotaActionForm);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a></td>
				</tr>
             	
             	<tr> 
                	<td><strong> Código da Rota:</strong></td>
                	<td>
                		<html:text property="codigoRota" size="4" maxlength="4" tabindex="4" />
                  	</td>
              	</tr>	
         	
				
							
				<tr> 
                	<td><strong>Grupo de Faturamento:</strong></td>
                	<td><html:select property="faturamentoGrupo" tabindex="5">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="collectionFaturamentoGrupo"
							labelProperty="descricao" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
 
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td><strong> <span class="style1"><strong> 
					<html:radio property="indicadorUso" value="1" tabindex="6"/> <strong>Ativo 
					<html:radio property="indicadorUso" value="2"/> Inativo
					<html:radio property="indicadorUso" value="3"/> Todos
					</strong></strong></span></strong></td>
				</tr>				
		  			

				<tr>
					<td>
						<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left" onclick="javaScript:limparForm(document.RotaActionForm);" >
                    	</td>
                    	<td  align="right">
						<gcom:controleAcessoBotao name="Button" value="Filtrar"
							  onclick="javascript:validarForm(document.RotaActionForm);" url="filtrarRotaAction.do"/>
<!--
						<input name="Button" type="button" class="bottonRightCol" value="Filtrar" align="right" onClick="validarForm(document.RotaActionForm)">-->
					</td>
				</tr>


			</table>
			<p>&nbsp;</p>
			</td>

		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
