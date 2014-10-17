<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
	redirecionarSubmit('exibirInserirInfracaoPerfilAction.do?pesquisaSubCat=1');
}

function pesquisaSubcategoria(idValue){
	//redirecionarSubmit('exibirInserirInfracaoPerfilAction.do?pesquisaSubCat=1');
	AjaxService.carregaSubcategorias(idValue,{  
        callback:function(list){  
            //Função que remove caso exista os valores da combo.  
            DWRUtil.removeAllOptions("subcategoria");  
            //Adicionando valores na combo.  
            DWRUtil.addOptions("subcategoria",{'-1':' '});
            DWRUtil.addOptions("subcategoria", list);  
    	}
	});
}

function adicionarInfracaoPerfil(){
	var form = document.forms[0];
	form.action = 'adicionarInfracaoPerfilAction.do'; 
	form.submit();
}

function adicionarDebitoTipo(id){
	abrirPopup('exibirInserirInfracaoPerfilDebitoTipo.do?idPerfil='+id,400,700);
}

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
    
    if (tipoConsulta == 'tipoInfracao') {

	      if(!form.idInfracaoTipo.disabled){
		      form.idInfracaoTipo.value = codigoRegistro;
		      form.descricaoInfracaoTipo.value = descricaoRegistro;
		      form.descricaoInfracaoTipo.style.color = "#000000";
	      }
    }
}

function removeRowTableAtividades(id) {
	redirecionarSubmit('exibirInserirInfracaoPerfilAction.do?remover='+id);
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
	form.idCategoria.value = '-1';
	form.idSubcategoria.value = '-1';
	form.idImovelPerfil.value = '-1';
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
	<html:form action="/inserirInfracaoPerfilAction.do"
	   name="InfracaoPerfilActionForm"
	   type="gcom.gui.cobranca.InfracaoPerfilActionForm" method="post" >
	  
		<table width="100%" border="0">
	   		<tr>
				<td height="10" colspan="3"> Para inserir um Perfil de Infração, informe os dados abaixo:</td>
			</tr>
			
			<tr>
				<td><strong>Tipo Infração:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2"> 
						<html:text property="idInfracaoTipo" maxlength="4" size="4" 
									onkeypress="validaEnter(event, 'exibirInserirInfracaoPerfilAction.do?pesquisarInfracaoTipo=OK','idInfracaoTipo');return isCampoNumerico(event);"/> 
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
					</span>
				</td>
			</tr>

			<tr>
				<td><strong>Categoria:</strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idCategoria"  onchange="javascript:pesquisaSubcategoria(this.value);">
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoCategoria"/>
						</html:select>
					</span></td>
			</tr>
			 
			<tr>
				<td><strong>Subcategoria:</strong></td>
				<td colspan="2">
					<span class="style2">
						<logic:present name="colecaoSubcategoria" scope="session">
							<html:select property="idSubcategoria" styleId="subcategoria">
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
				<td><strong>Perfil do Imóvel:</strong></td>
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
				</td>
				<td align="right">
					<!--<gcom:controleAcessoBotao name="Button" value="Adicionar" onclick="javascript:validaForm(document.forms[0]);" url="adicionarInfracaoPerfilAction.do"/>-->
					<input type="button"
							   name="ButtonCancelar" 
							   class="bottonRightCol" 
							   value="Adicionar"
							   onClick="javascript:adicionarInfracaoPerfil();">
				</td>
			</tr>
			
			<tr>
					<td colspan="4"><strong> <font color="#FF0000"></font></strong>
						<div align="left">
							Clique no link para informar o(s) tipo(s) de débito de cada perfil de infração.
							<table width="100%" id="tableAtividades" align="center"
								bgcolor="#99CCFF">
								<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
									<td width="14%">
										<div align="center"><strong>Remover</strong></div>
									</td>
									<td>
										<div align="center"><strong>Tipo Infr. </strong></div>
									</td>
									<td>
										<div align="center"><strong>Categoria</strong></div>
									</td>
									<td>
										<div align="center"><strong>Subcategoria</strong></div>
									</td>
									<td>
										<div align="center"><strong>Imov. Perf.</strong></div>
									</td>
								</tr>
								<tbody>
								
									<c:forEach var="infracaoPerfil" items="${sessionScope.colecaoInfracaoPerfil}" varStatus="i">
								
										<c:if test="${i.count%2 == '1'}">
											<tr bgcolor="#FFFFFF">
										</c:if>
										<c:if test="${i.count%2 == '0'}">
											<tr bgcolor="#cbe5fe">
										</c:if>
											<td><div align="center"><a href="javascript:if(confirm('Confirma remoção?')){removeRowTableAtividades('${infracaoPerfil.id}');}">
												 	<img src="imagens/Error.gif" width="14" height="14" border="0" title="Remover"></a></div></td>
											<td> <div align="center"><a href="javascript:adicionarDebitoTipo(${infracaoPerfil.id});">${infracaoPerfil.infracaoTipo.descricao}</a></div> </td>
											<td> <div align="center">${infracaoPerfil.categoria.descricao}</div> </td>
											<td> <div align="center">${infracaoPerfil.subcategoria.descricao}</div></td>
											<td> <div align="center">${infracaoPerfil.imovelPerfil.descricao}</div></td>
									</c:forEach>
								
								</tbody>
							</table>
						</div>
					</td>
				</tr>
				<tr><td></td></tr>
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
					<gcom:controleAcessoBotao name="Button" value="Concluir" onclick="javascript:validaForm(document.forms[0]);" url="inserirInfracaoPerfilAction.do"/>
				</td>
			</tr>
	 	</table>
	</html:form>
</body>
</html>