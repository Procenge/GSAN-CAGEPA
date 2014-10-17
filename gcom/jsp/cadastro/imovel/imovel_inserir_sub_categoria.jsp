<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%><html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirImovelActionForm" dynamicJavascript="false" page="3"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<script language="JavaScript">
	function verificarAdicionar() {
		var form = document.InserirImovelActionForm;

		if(validateLong(form) && validateCaracterEspecial(form)) {
			if(form.idCategoria.value == "") {
				alert("Informe Categoria.");	          
			} else if(form.idSubCategoria.value == "-1") {
				alert("Informe Subcategoria.");	          
			} else if (form.quantidadeEconomia.value == '') {
				alert("Informe Quantidade de Economias.");		      
			} else if(form.quantidadeEconomia.value == '0') {
				alert("Quantidade de Economias deve somente conter números positivos.");
			} else if(form.quantidadeEconomia.value < '0') {
				alert("Quantidade de Economias deve somente conter números positivos.");
			} else {
				form.action='inserirImovelWizardAction.do?action=exibirInserirImovelSubCategoriaAction&botaoAdicionar=1';
				form.submit();
   }
    }
    }
</script>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script language="JavaScript">
    function verificarAdicionar() {

      var form = document.InserirImovelActionForm;

        if(validateLong(form) && validateCaracterEspecial(form)){
			if (form.idCategoria.value == "-1" && !form.indicadorContratoConsumo[0].checked) {
                alert("Informe Categoria.");	          
			} else if(form.idSubCategoria.value == "-1" && !form.indicadorContratoConsumo[0].checked) {
                alert("Informe Subcategoria.");	          
		      }else if (form.quantidadeEconomia.value == '') {
		         alert("Informe Quantidade de Economias.");		      
              }else if(form.quantidadeEconomia.value == '0'){
                alert("Quantidade de Economias deve somente conter números positivos.");
              }else if(form.quantidadeEconomia.value < '0'){
                alert("Quantidade de Economias deve somente conter números positivos.");
			} else if (form.idCategoria.value == "-1" &&
					form.idSubCategoria.value == "-1" &&
					form.indicadorContratoConsumo[0].checked) {
				alert("Contrato de consumo já informado.");
              }else{
                form.action='inserirImovelWizardAction.do?action=exibirInserirImovelSubCategoriaAction&botaoAdicionar=1';
                form.submit();
              }
        }
   }
    
	function submitContratoConsumo() {
		var form = document.InserirImovelActionForm;
		form.action='inserirImovelWizardAction.do?action=exibirInserirImovelSubCategoriaAction&atualizarIndicador=S';
		form.submit();
	}

	function checkContratoConsumo() {
		var form = document.forms[0];

		if (form.indicadorContratoConsumo[0].checked) {
			form.idCategoria.disabled = 1;
			form.idSubCategoria.disabled = 1;
		} else {
			form.idCategoria.disabled = 0;
			form.idSubCategoria.disabled = 0;
		}
	}
</script>
</logic:equal>

<script language="JavaScript">
<!-- Begin
	var bCancel = false;

    function validateInserirImovelActionForm(form) {
    	if (bCancel)
    		return true;
    	else
    		return validateCaracterEspecial(form) && validateLong(form)  && validateRequired(form) ;
    }

    function caracteresespeciais() {
    	this.ab = new Array("quantidadeEconomia","Quantidade de Economias deve somente conter números positivos." , new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations() {
    	this.ab = new Array("quantidadeEconomia","Quantidade de Economias deve somente conter números positivos." , new Function ("varName", " return this[varName];"));
    }

    function required() {
    	this.ab = new Array("idSubCategoriaImovel", "Informe uma Subcategoria.", new Function ("varName", " return this[varName];"));
    }
    
function removerSubcategoria(url){

	if(confirm('Confirma remoção ?')){
       var form = document.forms[0];
    	form.action = url;
	    	form.submit();
	}
	

}
     
-->
</script>


</head>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<body leftmargin="5" topmargin="5">
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	<body leftmargin="5" topmargin="5" onload="javascript:checkContratoConsumo();">
</logic:equal>

<div id="formDiv">
<html:form action="/inserirImovelWizardAction" method="post" onsubmit="return validateInserirImovelActionForm(this);">

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=4"/>



<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
<input type="hidden" name="numeroPagina" value="5"/>
<html:hidden property="url" value="4" />
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
          <td class="parabg">Inserir Imóvel</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
	  <table width="100%" border="0" >
              <tr>
                <td ><p>Selecione a categoria e sua subcategoria:</p>
                </td>
                <td align="right"></td>																	  
                  
              </tr>
              </table>
              <table width="100%" border="0" >

              <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
              <tr>
					<td width="30%"><strong>Contrato de Consumo:</strong></td>
                	<td width="70%" colspan="2"> 
                		<html:checkbox property="indicadorContratoConsumo" onclick="javascript:submitContratoConsumo();" value="true"/>
                	</td>
              	</tr>
				<input type="hidden" name="indicadorContratoConsumo" value="false">
              </logic:equal>

              <tr>
                <td width="30%"><strong>Categoria:<font	color="#FF0000">*</font></strong></td>
                <td width="70%" colspan="2">
                <font color="#FF0000">
                <html:select property="idCategoria" onchange="javascript:document.InserirImovelActionForm.textoSelecionadoCategoria.value = this[this.selectedIndex].text.substring(5);pesquisaColecaoReload('inserirImovelWizardAction.do?action=exibirInserirImovelSubCategoriaAction&subCategoriaEscolhida=2','idCategoria');">
                  <html:option value="-1">&nbsp;</html:option>
                  <html:options collection="categorias" labelProperty="descricaoComId" property="id"/>
                </html:select>
                <html:hidden property="textoSelecionadoCategoria"/>
                <html:hidden property="idSubCategoriaImovel"/>
                  </font>
                </td>
                
              </tr>
              <tr>
                <td width="30%"><strong>Subcategoria:<font	color="#FF0000">*</font></strong></td>
                <td width="70%" colspan="2"><font color="#FF0000">
                <html:select property="idSubCategoria" onchange="javascript:document.InserirImovelActionForm.textoSelecionadoSubCategoria.value = this[this.selectedIndex].text;pesquisaColecaoReload('inserirImovelWizardAction.do?action=exibirInserirImovelSubCategoriaAction&subCategoriaEscolhida=1','idSubCategoria')">
                  <html:option value="-1">&nbsp;</html:option>
                  <html:options collection="subCategorias" labelProperty="descricaoComId" property="id"/>
                </html:select>
                <html:hidden property="textoSelecionadoSubCategoria"/>
                  </font></td>
              </tr>

              <tr>
                <td width="30%"><strong>Quantidade de Economias:<font color="#FF0000">*</font></strong></td>
                <td width="70%" colspan="2"> <html:text maxlength="4" property="quantidadeEconomia" size="4"/>

               </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
		<td colspan="2"><font color="#FF0000">*</font> Campo obrigatório.</td>
              </tr>
             <tr><td colspan="3">&nbsp;</td></tr>
              <tr>
                <td colspan="2"><strong>Subcategorias Informadas</strong></td>
                <td align="right">
                  <html:button  styleClass="bottonRightCol" value="Adicionar" property="botaoAdicionar" onclick="javascript:verificarAdicionar();"/>
                  	<logic:present name="indicadorImovelConsumoFaixaAreaCatg">
                  		 <logic:equal name="indicadorImovelConsumoFaixaAreaCatg" value="1" scope="session">
                  		<html:button  styleClass="bottonRightCol" value="Consumo Por Faixa de Área e Categoria" property="botaoAdicionar" onclick="javascript:abrirPopupComSubmit('exibirImovelConsumoFaixaAreaCategoriaPopupAction.do?indicadorContratoConsumo=<%= indicadorContratoConsumo %>', 310, 750, 'ImovelConsumoFaixaAreaCategoriaPopup');"/>
                  	 </logic:equal>
                  	</logic:present>
                </td>
              </tr>

              <tr>
                <td colspan="3">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>

                      <table width="100%" bgcolor="#90c7fc">
                          <!--header da tabela interna -->
                          <tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
                            <td  width="10%"><div align="center"><strong>Remover</strong> </div></td>
                            <td width="32%"><strong>Categoria</strong></td>
                            <td width="32%"><strong>Subcategoria</strong></td>
                            <td width="25%"><strong>Quantidade Economias</strong></td>
                          </tr>
                        </table>

                      </td>
                    </tr>
                    <tr>
                      <td height="83px">
                        <div style="width: 100%; height: 100%; overflow: auto;">
                          <table width="100%" align="center" bgcolor="#99CCFF">
                            <!--corpo da segunda tabela-->
                            <%int cont=0;%>
                            <logic:iterate name="colecaoImovelSubCategorias" id="imovelSubCategoria" type="ImovelSubcategoria">

     				<%
                                   cont = cont+1;
                                   if (cont%2==0){%>
                                     <tr bgcolor="#cbe5fe">
                                <% }else{ %>
                                     <tr bgcolor="#FFFFFF">
                                <% }%>
					<td  width="10%">
					  <div align="center">
                                            <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
                                            <a href="javascript:removerSubcategoria('removerImovelSubCategoriaAction.do?removerImovelSubCategoria=<%=""+imovelSubCategoria.getUltimaAlteracao().getTime()%>');"><img border="0" src="/gsan/imagens/Error.gif"/></a>
                                            </logic:equal>

                                            <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
                                              <a href="javascript:removerSubcategoria('removerImovelSubCategoriaAction.do?indicadorContratoConsumo='+document.forms[0].indicadorContratoConsumo[0].checked+'&removerImovelSubCategoria=<%=""+imovelSubCategoria.getUltimaAlteracao().getTime()%>');"><img border="0" src="/gsan/imagens/Error.gif"/></a>
                                            </logic:equal>
                                          </div>
					</td>
                                        <td width="32%">
					  <div>
                                            <bean:write name="imovelSubCategoria" property="comp_id.subcategoria.categoria.descricao"/>
                                          </div>
					</td>
                                        <td width="32%">
					  <div>
                                            <bean:write name="imovelSubCategoria" property="comp_id.subcategoria.descricao"/>
                                          </div>
					</td>
                                        <td width="25%">
 					  <div align="center">
                                            <bean:write name="imovelSubCategoria" property="quantidadeEconomias"/>
                                          </div>
					</td>

                                 </tr>

                            </logic:iterate>
                          </table>
                        </div></td>
                    </tr>
                  </table></td>
              </tr>
               <tr>
                <td colspan="3">
					<div align="right">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=4"/>
					</div>
				</td>
               </tr>
            </table>
    </table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>



<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirImovelWizardAction.do?concluir=true&action=inserirImovelSubCategoriaAction'); }
</script>



</html:html>
