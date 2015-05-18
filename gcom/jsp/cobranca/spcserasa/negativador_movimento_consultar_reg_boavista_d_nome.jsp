<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gsanLib.tld" prefix="gsan"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.util.ConstantesSistema"%>


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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="CriterioCobrancaActionForm" dynamicJavascript="false" />

<script language="JavaScript">
 
   function validarAtualizarForm(){   
   	  var form = document.forms[0];    	  
	  redirecionarSubmit('atualizarDadosRegistroAction.do');	  
	}
 
   function fecharAtualizarForm(){   
	   	  var form = document.forms[0];    	  
		  chamarReload('exibirConsultarNegativadorMovimentoDadosAction.do');
		  window.close();
		}      

 function verificarIndicadoCorrecao(){
	var form = document.forms[0];
    if(form.indicadorCorrecao.value != "-1" ){
        form.indicadorCorrecao.disabled = true;
        document.getElementById("buttonAtualizar").disabled = true;  
    }else{
        form.indicadorCorrecao.disabled = false;  
        document.getElementById("buttonAtualizar").disabled = false;   
    }
 }
 
 

</script>

</head>

<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(720, 560);setarFoco('${requestScope.nomeCampo}');verificarIndicadoCorrecao();">


<html:form action="/exibirConsultarDadosRegistroSERASAAction"
	name="ConsultarDadosRegistroActionForm"
	type="gcom.gui.cobranca.spcserasa.ConsultarDadosRegistroActionForm"
	method="post">

<table width="678" border="0" cellpadding="0" cellspacing="5">

  <tr> 
    <td width="668" valign="top" class="centercoltext"> <table height="100%">
        <tr> 
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Consultar<strong> </strong>Registros do Movimento do Negativador </td>

          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <table width="100%" border="0">
        <tr> 
          <td height="364"> 
            <table width="100%" border="0" dwcopytype="CopyTableRow">
              <tr> 
                <td colspan="3">&nbsp;</td>
              </tr>

              <tr>
                <td><strong>Negativador:</strong></td>
                <td colspan="2"><strong><b><span class="style4">
                  <html:text property="negativador" disabled="true" style="background-color:#EFEFEF; border:0" size="50" maxlength="50"/>
                </span></b></strong></td>
              </tr>
              <tr>
                <td><strong>Tipo do Movimento:</strong></td>

                <td colspan="2"><strong><b><span class="style4">
                  <html:text property="tipoMovimento" disabled="true" style="background-color:#EFEFEF; border:0" size="10" maxlength="10"/>
                </span></b></strong></td>
              </tr>
              <tr>
                <td colspan="3"><hr></td>
              </tr>
              <tr>
                <td colspan="3"><div align="center"><strong>Conte&uacute;do do Registro</strong></div></td>

              </tr>
              <tr>
                <td><strong>Tipo do Registro:</strong></td>
                <td colspan="2">
	                <html:text property="tipoRegistroCodigo" disabled="true" style="background-color:#EFEFEF; border:0" size="2" maxlength="2"/>
	                <html:text property="tipoRegistroDescricao" disabled="true" style="background-color:#EFEFEF; border:0" size="60" maxlength="60"/>
                </td>
              </tr>
              <tr>
                <td><strong>Código do Associado da institui&ccedil;&atilde;o informante:</strong></td>
                <td colspan="2">
                <html:text property="cnpj" disabled="true" style="background-color:#EFEFEF; border:0" size="6" maxlength="6"/>
                </td>
              </tr>
              
              <tr> 
                <td><strong>Sequencial do Registro:</strong></td>
                <td colspan="2">
                	<html:text property="sequencialRegistro" disabled="true" style="background-color:#EFEFEF; border:0" size="7" maxlength="7"/>
                </td>
              </tr>     
              
              <tr>
                <td><strong>Código do Sistema:</strong></td>
                <td colspan="2">
					<html:text property="codigoSistema" disabled="true" style="background-color:#EFEFEF; border:0" size="2" maxlength="2"/>
					<html:text property="descricaoCodigoSistema" disabled="true" style="background-color:#EFEFEF; border:0" size="10" maxlength="10"/>
				</td>
              </tr>              
              
              <tr>
                <td><strong>Documento Principal:</strong></td>
                <td colspan="2">
					<html:text property="documentoPrincipal" disabled="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"/>
				</td>
              </tr>                
              
              <tr>
                <td><strong>Documento Sencundário:</strong></td>
                <td colspan="2">
					<html:text property="documentoSecundario" disabled="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"/>
				</td>
              </tr>                         
              
              <tr>
                <td><strong>Documento Terciário:</strong></td>
                <td colspan="2">
					<html:text property="documentoTerciario" disabled="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"/>
				</td>
              </tr>       
              
              <tr>
                <td><strong>Nome do Devedor: </strong></td>
                <td colspan="2">
                	<html:text property="nomeDevedor" disabled="true" style="background-color:#EFEFEF; border:0" size="60" maxlength="60"/>
                </td>
              </tr>                       

              <tr>
                <td><strong>Data da Nascimento:</strong></td>
                <td colspan="2">
                	<html:text property="dataNascimento" disabled="true" style="background-color:#EFEFEF; border:0" size="10" maxlength="10"/>
                  (AAAAMMDD) </td>
              </tr>
              
              <tr>
                <td><strong>Nome do Cônjuge: </strong></td>
                <td colspan="2">
                	<html:text property="nomeConjuge" disabled="true" style="background-color:#EFEFEF; border:0" size="60" maxlength="60"/>
                </td>
              </tr>                 
              
              <tr>
                <td><strong>Naturalidade:</strong></td>
                <td colspan="2">
                	<html:text property="naturalidade" disabled="true" style="background-color:#EFEFEF; border:0" size="2" maxlength="2"/>
                </td>
              </tr>
              
              <tr>
                <td><strong>Unidade Federação: </strong></td>
                <td colspan="2">
                	<html:text property="unidadeFederacao" disabled="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"/>
                </td>

              </tr>

              <tr>
                <td><strong>C&oacute;digo de Retorno:</strong></td>
                <td colspan="2">
                	<html:text property="codigoRetorno" disabled="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"/>
                </td>
              </tr>


              <tr>
                <td colspan="3"><hr></td>
              </tr>
              <tr>
                <td colspan="3"><div align="center"><strong>Dados do Retorno  do Registro</strong></div></td>

              </tr>
              <tr>
                <td><strong>Indicador de Aceita&ccedil;&atilde;o:</strong></td>
                <td colspan="2">
                	<html:text property="indicadorAceitacao" disabled="true" style="background-color:#EFEFEF; border:0" size="15" maxlength="15"/>
                </td>
              </tr>
              <tr> 
                <td colspan="3"><table width="100%" border="0">
                  <tr bordercolor="#90c7fc">
                    <td colspan="6" bgcolor="#90c7fc"><strong>Ocorr&ecirc;ncias do Retorno</strong></td>
                  </tr>
                  <tr bordercolor="#000000">
                    <td width="23%" bgcolor="#90c7fc"><div align="center"><strong>C&oacute;digo</strong></div></td>
                    <td bgcolor="#90c7fc"><div align="center"><strong>Descri&ccedil;&atilde;o do Motivo de Retorno</strong> </div></td>
                    </tr>
                 
                 
               

							<logic:present name="collNegativadorMovimentoRegRetMot">
								<%int cont = 1;%>
								<logic:iterate name="collNegativadorMovimentoRegRetMot" id="negativadorMovimentoRegRetMot">
								
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>

																						
											<td >
												<div align="center">
												<logic:notPresent name="acao" scope="session">
													
														<bean:write name="negativadorMovimentoRegRetMot" property="negativadorRetornoMotivo.codigoRetornoMotivo"/>														
													   &nbsp;		
																				
												</logic:notPresent></div>
											</td>					
											
											
											<td align="center">	
													<bean:write name="negativadorMovimentoRegRetMot" property="negativadorRetornoMotivo.descricaoRetornocodigo"/>				
											</td>
											</tr>
								
								</logic:iterate>
							</logic:present>
                 
                 

                </table>
                
                <table width="100%" border="0">

			
			</table>
                
                
                </td>
              </tr>
               <tr>
                <td colspan="3"><hr></td>
              </tr>
              
			  <tr>
                <td><strong>Indicador de Correção:</strong></td>
                <td colspan="2">                 
                  <html:select property="indicadorCorrecao" tabindex="7" >
                        <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	 
						<html:option value="<%=""+ConstantesSistema.CORRIGIDO%>">CORRIGIDO</html:option>						
						<html:option value="<%=""+ConstantesSistema.NAO_CORRIGIDO%>">NÃO CORRIGIDO</html:option>						
					</html:select>			 
				</td>
              </tr> 
                
               <tr>
                <td colspan="3"><hr></td>
              </tr>
				
              
   
             <table width="100%">
				<tr>
					<td align="right"><strong><font color="#FF0000"> 					 
					     <input id ="buttonAtualizar" name="buttonAtualizar" type="button" class="bottonRightCol" value="Atualizar" onclick="javascript:validarAtualizarForm();" align="left">
					     <input  name="button" type="button" class="bottonRightCol" value="Fechar" onclick="javascript:fecharAtualizarForm();" align="left">
						     
					 </td>
				</tr>
			</table>
              
              <!-- <tr>
          <td height="15">&nbsp;</td>
          <td>&nbsp;</td>
        </tr> -->
            </table>
          </td>

        </tr>
      </table>
      <p>&nbsp;</p></td>
  </tr>
</table>
</body>

</html:form>
</html:html>
