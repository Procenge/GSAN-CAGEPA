<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FaturamentoActionForm"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript"></script>
<script language="JavaScript">
function validaDataCompleta(data, event){
		if(mascaraData(data, event)){
			return false;
		}
}
</script>
<script language="JavaScript">
function validaData(form){
  var form = document.FaturamentoActionForm;
  var mesAno = form.mesAno.value;
  if(mascaraAnoMes(mesAno)){
     return false;
  }
}

function validarForm(){
	var form = document.FaturamentoActionForm;
	if(validateFaturamentoActionForm(form) && validarCamposDinamicos(form)){
		submeterFormPadrao(form);
	}
}

function validarCamposDinamicos(form){
 
 	var camposValidos = true;
 	
 	if(camposValidos == true){
	 	for (i=0; i < form.elements.length; i++) {
	    	
	    	if (form.elements[i].type == "text" && form.elements[i].id.length > 1){
	    		if(form.elements[i].value != ""){	
					if(form.elements[i].id == "data"){
						if(!verificaData(form.elements[i])){
							camposValidos = false;
							break;
						}
					}
				}else{
					camposValidos = true;
				}
			}
		}
	}
		
	return camposValidos;
}

</script>

</head>

<body leftmargin="5" topmargin="5">
<html:form
  action="/inserirFaturamentoCronogramaAction"
  name="FaturamentoActionForm"
  type="gcom.gui.faturamento.FaturamentoActionForm"
  method="post"
  onsubmit="return validateFaturamentoActionForm(this) && validarCamposDinamicos(this);"
>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
   <td width="135"  valign="top" class="leftcoltext">

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
            <!--In&iacute;cio Tabela Reference a P&aacute;gina&ccedil;&atilde;o da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                <td class="parabg">Inserir Cronograma de Faturamento</td>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>
            <!--Fim Tabela Reference a P&aacute;gina&ccedil;&atilde;o da Tela de Processo-->
            <p>&nbsp;</p>
            <table width="100%" border="0" >

                <tr>
                  	<td>Para adicionar o(s) cronograma(s) de faturamento,
                    informe os dados abaixo:</td>
					<td align="right"></td>												
                </tr>
                </table>
                <table width="100%" border="0" >
                <tr>
                  <td><strong>Grupo:<font color="#FF0000">*</font></strong> </td>
                  <td>
                    <html:select property="idGrupoFaturamento">
                    <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
  		       		<html:options collection="faturamentoGrupos" labelProperty="descricao" property="id"/>
                    </html:select>
		  </td>
                </tr>
                <tr>
                  <td><strong>Mês/Ano:<font color="#FF0000">*</font></strong></td>
                  <td><html:text maxlength="7" property="mesAno" size="7" onkeyup="mascaraAnoMes(this, event);"/>
                    &nbsp; mm/aaaa</td>
                </tr>
                <tr>
                  <td><strong>Quantidade de Cronogramas:<font color="#FF0000">*</font></strong></td>
                  <td><html:text maxlength="8" property="quantidadeCronogramas" size="8"/></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td align="left"><font color="#FF0000">*</font> Campo Obrigat&oacute;rio
                  </td>
                </tr>
                <tr>
                  <td><p>&nbsp;</p></td>
                  <td> </td>
                </tr>

            </table>
            <table width="100%" cellpadding="0" cellspacing="0">
              <tr>
                <td height="0">
                  <table width="100%" bgcolor="#99CCFF">
                    <!--header da tabela interna -->
                    <tr bordercolor="#000000">
                      <td width="10%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Comandar</strong></div></td>
                      <td width="25%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Atividade</strong></div></td>
                      <td width="25%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Predecessora</strong></div></td>
                      <td width="15%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Obrigat&oacute;ria</strong></div></td>
                      <td width="23%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Data Prevista</strong></div></td>
                    </tr>
                  </table></td>
              </tr>
          <logic:present name="faturamentoAtividades">
              <tr>
                <td height="126">
                  <div style="width: 100%; height: 100%; overflow: auto;">
                    <table width="100%" align="center" bgcolor="#99CCFF">
                      <!--corpo da segunda tabela-->
                      <%int cont=0;%>
                      <logic:notEmpty name="faturamentoAtividades">
                        <logic:iterate name="faturamentoAtividades" id="faturamentoAtividade">
                          <%
                               cont = cont+1;
                            if (cont%2==0){%>
                              <tr bgcolor="#cbe5fe">
                            <%}else{ %>
                              <tr bgcolor="#FFFFFF">                              
                          <%}%>
                          	  <td width="11%">
                          	  	<logic:present name="faturamentoAtividade" property="indicadorPossibilidadeComandoAtividade">
                          	  		<logic:equal name="faturamentoAtividade" property="indicadorPossibilidadeComandoAtividade" value="1">
		                                <div align="center">
		                                   <input type="checkbox" name="c${faturamentoAtividade.id}" value="1" checked="checked"/>
		                                </div>
		                            </logic:equal>
	                            </logic:present>
                              </td>
                              <td width="26%">
                                <div align="left">
                                   <bean:write name="faturamentoAtividade" property="descricao"/>
                                </div>
                              </td>
                              <td width="26%">
			        <div align="left">
				  <logic:present name="faturamentoAtividade" property="faturamentoAtividadePrecedente">
                                   <bean:write name="faturamentoAtividade" property="faturamentoAtividadePrecedente.descricao"/>
                                  </logic:present>
				  <logic:notPresent name="faturamentoAtividade" property="faturamentoAtividadePrecedente">
                                   &nbsp;
                                  </logic:notPresent>
				</div>
                              </td>
                              <td width="16%">
                                 <div align="center">
                                    <logic:equal name="faturamentoAtividade" property="indicadorObrigatoriedadeAtividade" value="1">
                                      SIM
                                    </logic:equal>
                                    <logic:notEqual name="faturamentoAtividade" property="indicadorObrigatoriedadeAtividade" value="1">
                                      NÃO
                                    </logic:notEqual>
                                 </div>
                              </td>
                              <td>
				<div align="center">
                                  <input type="text" maxlength="10" name="n<bean:write name="faturamentoAtividade" property="id"/>" size="10" id="data" onkeypress="validaDataCompleta(this, event)" />
                                    <a href="javascript:abrirCalendario('FaturamentoActionForm', 'n<bean:write name="faturamentoAtividade" property="id"/>')">
                                      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" title="Exibir Calendário"/>
                                    </a>
				</div>
                              </td>
                            </tr>
                        </logic:iterate>
                      </logic:notEmpty>
                    </table>
                </div>
    	       </td>
              </tr>
            </logic:present>
            </table>
            <p>
              <table width="100%">
                 <tr>
                   <td width="50%">
                   		<input name="Button" 
                   			type="button" 
                   			class="bottonRightCol" 
                   			value="Desfazer" 
                   			align="left"
							onclick="window.location.href='<html:rewrite page="/exibirInserirFaturamentoCronogramaAction.do?menu=sim"/>'">
						&nbsp;
						<input name="Button" 
							type="button" 
							class="bottonRightCol"
							value="Cancelar" 
							align="left"
							onclick="window.location.href='<html:rewrite page="/telaPrincipal.do"/>'">
				   	</td>
                   <td align="right">
                   	<gcom:controleAcessoBotao name="Button" value="Inserir"
							  onclick="javascript:validarForm();" url="inserirFaturamentoCronogramaAction.do"/>
                   	<%--<html:submit styleClass="bottonRightCol" value="Inserir" property="adicionar"/>--%>
                   	</td>
                 </tr>
              </table>
        </tr>
   </table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
