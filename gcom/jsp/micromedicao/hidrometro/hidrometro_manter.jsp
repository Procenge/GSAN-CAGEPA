<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema,java.util.Collection" %>
<%@ page import="gcom.micromedicao.hidrometro.Hidrometro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<script language="JavaScript">

<!--
function facilitador(objeto){
	if (objeto.value == "0" || objeto.value == undefined){
		objeto.value = "1";
		marcarTodos();
	}
	else{
		objeto.value = "0";
		desmarcarTodos();
	}
}
function atualizarConjuntoHidrometro(){
 var form = document.forms[0];
 form.action='exibirAtualizarConjuntoHidrometroAction.do';
 form.submit();
}

// Verifica se há item selecionado
function verficarSelecao(objeto){

	if (CheckboxNaoVazio(objeto)){
		if (confirm ("Confirma remoção?")) {
			document.forms[0].action = "/gsan/removerHidrometroAction.do"
			document.forms[0].submit();
		 }
	}
 }
-->
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form
  action="/removerHidrometroAction"
  name="ManutencaoRegistroActionForm"
  type="gcom.gui.ManutencaoRegistroActionForm"
  method="post"
  onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirma Remoção?')"
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
    <td valign="top" bgcolor="#003399" class="centercoltext">
      <table height="100%">

        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Manter Hidr&ocirc;metro </td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
        <tr>
         <td height="5" colspan="3"></td>
	</tr>
      </table>

      <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23"><font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Hidrometros
              Encontrados:</strong></font></td>
			<td align="right"></td>					    
        </tr>
        
      <table width="100%" cellpadding="0" cellspacing="0">        
         <tr>
             <!--<td colspan="4" bgcolor="#3399FF"> -->
             <td colspan="8" bgcolor="#000000" height="2"></td>
         </tr>
         <tr  bordercolor="#000000">
           <td bgcolor="#90c7fc" width="7%" align="center"><strong><a href="javascript:facilitador(this);">Todos</a></strong></td>
           <td bgcolor="#90c7fc" width="10%" align="center"><strong>Formato</strong></td>
           <td bgcolor="#90c7fc" width="16%" align="center"><strong>N&uacute;mero</strong></td>
           <td bgcolor="#90c7fc" width="13%" align="center"><strong>Data de Aquisi&ccedil;&atilde;o</strong></td>
           <td bgcolor="#90c7fc" width="13%" align="center"><strong>Ano de Fabrica&ccedil;&atilde;o</strong></td>
           <td bgcolor="#90c7fc" width="13%" align="center"><strong>Marca</strong></td>
           <td bgcolor="#90c7fc" width="13%" align="center"><strong>Capacidade</strong></td>
           <td bgcolor="#90c7fc" width="15%" align="center"><strong>Situa&ccedil;&atilde;o</strong></td>
        </tr>
        <tr bordercolor="#90c7fc">
           <td colspan="8">
             <table width="100%" bgcolor="#99CCFF">
                <%--Esquema de paginação--%>
                  <pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">
                    <pg:param name="pg"/>
                    <pg:param name="q"/>
                    <logic:present name="hidrometros">
                      <%int cont = 0;%>
                    <logic:iterate name="hidrometros" id="hidrometro">
                       <pg:item>
                       <%
                        cont = cont+1;
                        if (cont%2==0){%>
                          <tr bgcolor="#cbe5fe">
                        <%}else{ %>
                           <tr bgcolor="#FFFFFF">
                        <%}%>
                            <td width="7%">
                              <div align="center">
                                <input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="hidrometro" property="id"/>"/>
                              </div>
                            </td>

                            <td width="10%" align="center">
                            	<logic:equal name="hidrometro" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
                            		4x6
                            	</logic:equal>
                            	<logic:equal name="hidrometro" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
                            		5x5
                            	</logic:equal>
                            	<logic:equal name="hidrometro" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_LIVRE.toString()%>">
                            		Livre
                            	</logic:equal>
                            </td>

                            <td width="16%" align="center">
                               <html:link page="/exibirAtualizarHidrometroAction.do"
                                           paramName="hidrometro"
                                           paramProperty="id"
                                           paramId="idRegistroAtualizacao">
                                   <bean:write name="hidrometro" property="numero"/>
                                </html:link>
                            </td>

                            <td width="13%" align="center">
                               <bean:write name="hidrometro" property="dataAquisicao" formatKey="date.format"/>
                            </td>

                            <td width="13%" align="center">
                               <bean:write name="hidrometro" property="anoFabricacao"/>
                            </td>

                            <td width="13%" align="center">
                               <bean:write name="hidrometro" property="hidrometroMarca.descricaoAbreviada"/>
                            </td>

                            <td width="13%" align="center">
                               <bean:write name="hidrometro" property="hidrometroCapacidade.descricaoAbreviada"/>
                            </td>

                            <td width="15%" align="left">
                               <logic:notEmpty name="hidrometro" property="hidrometroSituacao">
                               <bean:write name="hidrometro" property="hidrometroSituacao.descricao"/>
                               </logic:notEmpty>
                            </td>

                        </tr>
                    </pg:item>
                    </logic:iterate>

                   </logic:present>

             </table>
             <table width="100%" border="0">
                 <tr>
                  <td valign="top">
                  <gcom:controleAcessoBotao name="Button" value="Remover"
							  onclick="javascript:verficarSelecao(idRegistrosRemocao);" url="removerHidrometroAction.do"/>
					<!--
					<input type="button" class="bottonRightCol" value="Remover" name="Button" onclick="verficarSelecao(idRegistrosRemocao)"/>
					--><input name="button" type="button" class="bottonRightCol" value="Voltar Filtro" onclick="window.location.href='<html:rewrite page="/exibirManterHidrometroAction.do?voltarFiltro=1"/>'" align="left" style="width: 80px;">
				  </td>
                  <td valign="top">
                    <div align="right"><a href="javascript:toggleBox('demodiv',1);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>print.gif"
								title="Imprimir Hidrômetros" /> </a></div></td>
                 </tr>
            </table>

           </td>
        </tr>
        <tr bordercolor="#90c7fc">
          <td colspan="7">
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
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td>Clique nesse botão para atualizar todos os hidrômetros filtrados.</td>
        </tr>
        <tr>
          <td>
            <div align="left">
              <logic:present name="conjuntoHidrometro">
               <input name="button" type="button" class="bottonRightCol" value="Atualizar Conjunto" onclick="atualizarConjuntoHidrometro();">
              </logic:present>
              <logic:notPresent name="conjuntoHidrometro">
               <input name="button" type="button" class="bottonRightCol" value="Atualizar Conjunto" disabled />
              </logic:notPresent>

              </div></td>
        </tr>
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioHidrometroManterAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>

</html:html>