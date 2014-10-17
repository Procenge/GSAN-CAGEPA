<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<%--  <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarOperacaoActionForm" dynamicJavascript="false" /> --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" >

function facilitador(objeto){
	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}


 function excluir() {

	if (CheckboxNaoVazio(document.forms[0].ids)) {
		if  (confirm('Confirma remoção ?')){
			document.forms[0].submit();
		}
	}
 
 }
 
</script>
</head>

<body >

<html:form action="/removerGrupoAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post"
	onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirmar exclusao?')">

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
    <td width="625" valign="top" class="centercoltext">
       <table>
         <tr>
           <td></td>
         </tr>
       </table>
       <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
         <tr>
           <td width="11"><img src="imagens/parahead_left.gif" border="0"></td>
           <td class="parabg"> Manter Grupo</td>
           <td valign="top" width="11"><img src="imagens/parahead_right.gif" border="0"></td>
         </tr>
       </table>
       
       <!--Fim Tabela Reference a P&aacute;gina&ccedil;&atilde;o da Tela de Processo-->
       
       <table border="0" width="100%">
         <tr>
           <td colspan="4" height="23"><font color="#000000" style="font-size: 10px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Grupos encontradas:</strong></font></td>
         </tr>
         <tr>
            <td bgcolor="#000000" height="2"></td>
        </tr>
         <tr> 
           <td colspan="2">
             <table bgcolor="#99CCFF" width="100%">
               <tr bgcolor="#99CCFF"> 
                 <td><div align="center"><strong><a href="javascript:facilitador(this);">Todos</a></strong></div></td>
                 <td><div align="center"><strong>Descrição</strong></div></td>
                 <td><div align="center"><strong>Descrição Abreviada </strong></div></td>
                 <td><div align="center"><strong>Indicador de Uso</strong></div></td>
               </tr>

			    <%--Esquema de paginação--%>
				<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset"
				maxPageItems="10" items="${sessionScope.totalRegistros}">
				<pg:param name="pg" />
				<pg:param name="q" />
                                          
               <%int cont=0;%>
			   <logic:present name="grupos" >
			   <logic:iterate name="grupos" id="grupo">
			     <pg:item>
				   <%
                     cont = cont+1;
                     if (cont%2==0){%>
                       <tr bgcolor="#cbe5fe">
                   <%}else{ %>
                       <tr bgcolor="#FFFFFF">
                   <%}%>
                   <td>
                     <div align="center">
                       <strong> 
                         <input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="grupo" property="id"/>"/>
                       </strong>
                     </div>
                   </td>
                   <td>
                     <div align="left">
					   <html:link page="/exibirAtualizarGrupoAction.do"
                                  paramName="grupo"
                                  paramProperty="id"
                                  paramId="idRegistroAtualizacao">
							<bean:write name="grupo" property="descricao"/>
					   </html:link>
					 </div>
				   </td>
                   <td>
                     <div align="left">
                       <bean:write name="grupo" property="descricaoAbreviada"/>
                     </div>
                   </td>
                   <td>
                     <div align="left">
	                   <logic:equal name="grupo" property="indicadorUso" value="1">
		                 ATIVO
	                   </logic:equal >
	                   <logic:notEqual name="grupo" property="indicadorUso" value="1">
		                 INATIVO
	                   </logic:notEqual>
                     </div>
                   </td>
                   </tr>
                 </pg:item>
               </logic:iterate>
               </logic:present>
               
      
			
             </table>
           </td>
         </tr>
         

         <tr bordercolor="#90c7fc">
           <td>
             <table width="100%">
		         <tr bordercolor="#90c7fc">
		           <td> 
		             <%-- <input name="Button22" class="bottonRightCol" value="Remover" type="button" onclick="javascript:excluir();"> --%>
		             <gcom:controleAcessoBotao name="Button" value="Remover" onclick="javascript:excluir();" url="removerGrupoAction.do"/>
		           </td>		           
		         </tr>
	         </table>
	       </td>
         </tr>
                  
       </table>
       
        <table width="100%" border="0">
			<tr>
				<td>
				<div align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
				</td>
				</pg:pager>
				<%-- Fim do esquema de paginação --%>
			</tr>
		</table>
    
    </td>
  </tr>
</table>		
<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
</html:html>
