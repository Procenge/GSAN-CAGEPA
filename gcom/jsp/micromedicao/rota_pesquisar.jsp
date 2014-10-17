<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarRotaActionForm"
	staticJavascript="false" />
<script language="JavaScript">



function validarForm(form){
	var form = document.forms[0];

	if(validatePesquisarRotaActionForm(form)){
		submeterFormPadrao(form)
	}
}
function limparPesquisaLocalidade() {
   var form = document.PesquisarRotaActionForm;

   form.idLocalidade.value = "";
   form.descricaoLocalidade.value = "";
   limparPesquisaSetorComercial();
   limparPesquisaQuadra();


 }
function limparPesquisaSetorComercial() {
   var form = document.PesquisarRotaActionForm;

   form.codigoSetorComercial.value = "";
   form.descricaoSetorComercial.value = "";
    


 }
function limparPesquisaQuadra() {
   var form = document.PesquisarRotaActionForm;

   form.numeroQuadra.value = "";
   if (form.descricaoQuadra != null){
   	form.descricaoQuadra.value = "";
   }	
}


function limparForm(){

	var form = document.forms[0];
	
	limparPesquisaQuadra();
	limparPesquisaSetorComercial();
	limparPesquisaLocalidade();
	form.codigoRota.value = "";
	form.faturamentoGrupo.value = -1;
	form.indicadorUso.value = "";
	
}
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if (tipoConsulta == 'quadra') {
      form.numeroQuadra.value = codigoRegistro;
     
    }
}

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="resizePageSemLink(800, 490);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/pesquisarRotaAction" method="post">
	<table width="760" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="760" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Pesquisar Rota</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">Preencha os campos para pesquisar uma Rota:</td>
					<td align="right"></td>
				</tr>
			</table>
				
			<table width="100%" border="0">
				<tr>
					<td width="18%"><strong>Localidade:</strong></td>
					<td width="82%" height="24">
						
						<logic:empty scope="session" name="desabilitarLocalidade">
							<html:text 
								maxlength="3" 
								tabindex="1"
								property="idLocalidade" 
								size="3"
								onkeypress="limparPesquisaQuadra();limparPesquisaSetorComercial();validaEnterComMensagem(event, 'exibirPesquisarRotaAction.do?objetoConsulta=1','idLocalidade');"
							 /> 
							 
							 <a href="javascript:redirecionarSubmit('exibirPesquisarLocalidadeAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarRotaAction');limparPesquisaQuadra();limparPesquisaSetorComercial();">
							 
							 	<img width="23" 
							 		height="21" 
							 		border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Localidade" />
							</a> 
						 </logic:empty>
						 
						 <logic:notEmpty scope="session" name="desabilitarLocalidade">
							
							<html:text 
								maxlength="3" 
								tabindex="1"
								property="idLocalidade" 
								size="3"
								readonly="true"
							 /> 
						 	<img width="23" 
						 		height="21" 
						 		border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" />
								
						 </logic:notEmpty>
					
					<logic:present
						name="idLocalidadeNaoEncontrada">
						<logic:equal name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idLocalidadeNaoEncontrada">
						<logic:empty name="PesquisarRotaActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarRotaActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
						<logic:empty scope="session" name="desabilitarLocalidade">
							<a href="javascript:limparPesquisaLocalidade();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" />
							</a>
						</logic:empty>
						<logic:notEmpty scope="session" name="desabilitarLocalidade">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td>
						
						<logic:empty scope="session" name="desabilitarSetor">
							<html:text maxlength="3" 
								property="codigoSetorComercial"
								tabindex="2" 
								size="3"
								onkeypress="limparPesquisaQuadra();validaEnterDependencia(event, 'exibirPesquisarRotaAction.do?objetoConsulta=2',this,document.forms[0].idLocalidade.value,'Localidade');"
							 /> 
							 
							 <a href="javascript:redirecionarSubmitDependencia('exibirPesquisarSetorComercialAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarRotaAction&idLocalidade='+document.forms[0].idLocalidade.value,document.forms[0].idLocalidade.value,'Localidade');limparPesquisaQuadra();">
																													
								<img width="23" height="21" border="0" 
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Setor Comercial" />
							 </a> 
						 </logic:empty>
						 
						 <logic:notEmpty scope="session" name="desabilitarSetor">
							<html:text maxlength="3" 
								property="codigoSetorComercial"
								tabindex="2" 
								size="3"
								readonly="true"
							 /> 															
							<img width="23" height="21" border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" />
						 </logic:notEmpty>
						 
						<logic:present	name="idSetorComercialNaoEncontrada">
						<logic:equal name="idSetorComercialNaoEncontrada"	value="exception">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idSetorComercialNaoEncontrada"
							value="exception">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="idSetorComercialNaoEncontrada">
						<logic:empty name="PesquisarRotaActionForm"
							property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarRotaActionForm"
							property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent>
						<logic:empty scope="session" name="desabilitarSetor"> 
							<a href="javascript:limparPesquisaSetorComercial();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a>
						</logic:empty>
						<logic:notEmpty scope="session" name="desabilitarSetor"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" />
						</logic:notEmpty>
					</td>
				</tr>
				
				<tr>
					<td><strong>Quadra:</strong></td>
					<td colspan="2" height="24"><html:text maxlength="5"
						property="numeroQuadra" tabindex="3" size="5"
						onkeypress="javascript:return validaEnterDependencia(event, 'exibirPesquisarRotaAction.do?objetoConsulta=3', this, document.forms[0].idLocalidade.value, 'Setor Comercial');" />
					      <a href="javascript:redirecionarSubmitDependencia('exibirPesquisarQuadraAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarRotaAction&codigoSetorComercial='+document.forms[0].codigoSetorComercial.value+'&tipo=Quadra',document.forms[0].codigoSetorComercial.value,'Setor Comercial', 400, 800);">
							
						
				          	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
				          </a>                 
						  
					
					<logic:present name="codigoQuadraNaoEncontrada" scope="request">
						<span style="color:#ff0000" id="msgQuadra"><bean:write
							scope="request" name="msgQuadra" /></span>
					</logic:present> <logic:notPresent name="codigoQuadraNaoEncontrada"
						scope="request">
					</logic:notPresent>
					<a href="javascript:limparPesquisaQuadra();document.forms[0].numeroQuadra.focus();"> <img
							 src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							 border="0" title="Apagar" />
						 </a>
					</td>
				</tr>
				<tr>
         			 <td height="0"><strong>Código da Rota:</strong></td>
         			 <td colspan="3"><html:text maxlength="30" property="codigoRota" size="3" tabindex="2"/></td>
        		</tr>
       			 <tr>
       				 <td><strong>Grupo de Faturamento:</strong></td>
									<td align="left">
										<html:select property="faturamentoGrupo">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoFaturamentoGrupo">
												<html:options collection="colecaoFaturamentoGrupo" property="id" labelProperty="descricao" />
											</logic:present>		
										</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td>
						<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /> Ativo
						<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /> Inativo
						<html:radio property="indicadorUso" value="" /> Todos
					</td>
				</tr>
				<tr>
					<td height="24" colspan="3" width="80%"><INPUT TYPE="button"
						class="bottonRightCol" value="Limpar"
						onclick="javascript:limparForm();" /> &nbsp;&nbsp; <logic:present
						name="caminhoRetornoTelaPesquisaRota">
						<INPUT TYPE="button" class="bottonRightCol" value="Voltar"
							onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaRota}.do')" />
					</logic:present></td>
					<td align="right"><input type="button" name="Button"
						class="bottonRightCol" tabindex="12" value="Pesquisar"
						onClick="javascript:validarForm(document.forms[0])" /></td>
				</tr>
        			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>