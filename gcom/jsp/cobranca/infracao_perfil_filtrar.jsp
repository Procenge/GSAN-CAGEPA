<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1;">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
	
<script language="JavaScript">
function validaForm(){	
	var form = document.forms[0]; 
	form.submit();
}

function pesquisaSubcategoria(){
	redirecionarSubmit('exibirFiltrarInfracaoPerfilAction.do?pesquisaSubCat=1');
}

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
    
    if (tipoConsulta == 'tipoInfracao') {

	      if(!form.idInfracaoTipo.disabled) {
		      form.idInfracaoTipo.value = codigoRegistro;
		      form.descricaoInfracaoTipo.value = descricaoRegistro;
		      form.descricaoInfracaoTipo.style.color = "#000000";
	      }
    }
}

function pesquisaSubcategoria(idValue){
	//redirecionarSubmit('exibirFiltrarInfracaoPerfilAction.do?pesquisaSubCat=1');

	AjaxService.carregaSubcategorias(idValue,{  
        callback:function(list){  
            //Função que remove caso exista os valores da combo.  
            DWRUtil.removeAllOptions("subcategoria");  
            //Adicionando valores na combo.  
            DWRUtil.addOptions("subcategoria", {'-1':' '});
            DWRUtil.addOptions("subcategoria", list);  
    	}
	});
}

function limparTipoInfracao(){
	var form = document.forms[0];
	form.descricaoInfracaoTipo.value = "";
	form.idInfracaoTipo.value = "";
}

function limparForm(){
	var form = document.forms[0];
	form.descricaoInfracaoTipo.value = "";
	form.idInfracaoTipo.value = "";
	form.idCategoria.value = "-1";
	form.idSubcategoria.value = "-1";
	form.idImovelPerfil.value = "-1";
	pesquisaSubcategoria('-1');
}

function validaEnterPesquisa(idValue){
	if (event.keyCode == 13){
		AjaxService.pesquisaInfracao(idValue,{  
	        callback:function(retorno){  
	        	DWRUtil.setValue("descricaoInfracaoTipo","");
	            DWRUtil.setValue("descricaoInfracaoTipo", retorno);  
	    	}
		});
	}
}
</script>

<body leftmargin="5" topmargin="5">
	<html:form action="/filtrarInfracaoPerfilAction.do"
	   name="InfracaoPerfilActionForm"
	   type="gcom.gui.cobranca.InfracaoPerfilActionForm"
	   method="post" >
	  
		<table width="100%" border="0">
	   		<tr>
				<td height="10" colspan="3"> Para filtrar um Perfil de Infração, informe os dados abaixo:</td>
				<td align="right"></td>
				<td align="right">
			</tr>
			
			<tr>
				<td><strong>Tipo Infração:<font color="#FF0000"></font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:text property="idInfracaoTipo" maxlength="4" size="4"
									onkeypress="validaEnter(event, 'exibirFiltrarInfracaoPerfilAction.do?pesquisarInfracaoTipo=OK','idInfracaoTipo');return isCampoNumerico(event);" /> 
								<a href="javascript:abrirPopup('exibirPesquisarInfracaoTipoAction.do', 340, 450);"
									alt="Pesquisar Tipo de Infração"> 
									<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
						<logic:present name="infracaoTipoEncontrado" scope="request">
								<html:text property="descricaoInfracaoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
						<logic:notPresent name="infracaoTipoEncontrado" scope="request">
								<html:text property="descricaoInfracaoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent> 
						<a href="javascript:limparTipoInfracao();"> 
							<img src="imagens/limparcampo.gif" width="23" height="21" border="0" title="Apagar">
						</a> 
					</span></td>
			</tr>

			<tr>
				<td><strong>Categoria:<font color="#FF0000"></font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idCategoria"  onchange="javascript:pesquisaSubcategoria(this.value);">
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoCategoria"/>
						</html:select>
					</span></td>
			</tr>
			 
			<tr>
				<td><strong>Subcategoria:<font color="#FF0000"></font></strong></td>
				<td colspan="2">
					<span class="style2">
						<logic:present name="colecaoSubcategoria" scope="session">
							<<html:select property="idSubcategoria"  styleId="subcategoria">
								<html:option value="-1">&nbsp;</html:option>
								<html:options property="id" labelProperty="descricao"  collection="colecaoSubcategoria"/>
							</html:select>										
						</logic:present>
						<logic:notPresent name="colecaoSubcategoria" scope="session">
							<html:select property="idSubcategoria" styleId="subcategoria">
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</logic:notPresent>
					</span></td>
			</tr>
			
			<tr>
				<td><strong>Perfil do Imóvel:<font color="#FF0000"></font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idImovelPerfil" >
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoImovelPerfil"/>
						</html:select>
					</span></td>
			</tr>
			
			<tr>
				<td></td>
			</tr>				
			
			<tr>
				<td></td>
				<td align="right">
					<div align="left">
						<strong><font color="#FF0000">*</font></strong>Campos obrigatórios</div></td></tr>
			<tr>
				<td colspan="2">
					<font color="#FF0000"> 
						
						<input type="button" 
							   name="ButtonReset"
							   class="bottonRightCol" 
							   value="Desfazer"
							   onClick="limparForm();">  
						
						<input type="button"
							   name="ButtonCancelar" 
							   class="bottonRightCol" 
							   value="Cancelar"
							   onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font>
				</td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validaForm(document.forms[0]);" url="filtrarInfracaoPerfilAction.do"/>
				</td>
			</tr>
	 	</table>
	</html:form>
</body>
</html>