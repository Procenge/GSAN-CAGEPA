<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ConverterContasEmGuiaPorResponsavelActionForm" />

<SCRIPT LANGUAGE="JavaScript">
<!--


function validarForm(formulario){

	submeterFormPadrao(formulario);
}


//Chama Popup
function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaMunicipio=" + tipo, altura, largura);
			}
		}
	}
}

function validaEnterCliente(tecla, caminhoActionReload, nomeCampo) {

	var form = document.ConverterContasEmGuiaPorResponsavelActionForm;
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Código do Cliente");	

}


function validaEnterImovel(tecla, caminhoActionReload, nomeCampo) {

	var form = document.InserirNegativadorActionForm;
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Matrícula do Imóvel");
}



function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.forms[0];
	if (tipoConsulta == 'cliente') {
    	form.codigoCliente.value = codigoRegistro;
       	form.action = 'exibirConverterContasEmGuiaPorResponsavelAction.do';
        form.submit(); 
    }
    if(tipoConsulta == 'imovel'){
    	form.codigoImovel.value = codigoRegistro;
      	form.inscricaoImovel.value = descricaoRegistro;
      	form.action = 'exibirConverterContasEmGuiaPorResponsavelAction.do';
      	form.submit();
    }
     
}

function limparForm(tipo){
    var form = document.ConverterContasEmGuiaPorResponsavelActionForm;
   
    if(tipo == 'imovel')
    {
		var ObjCodigoImovel = returnObject(form,"codigoImovel");
		var ObjInscricaoImovel = returnObject(form,"inscricaoImovel");
		ObjCodigoImovel.value = "";
		ObjInscricaoImovel.value = "";
		
		form.inscricaoImovel.value = "";
	}
	if(tipo == 'cliente')
    {
		var ObjCodigoCliente = returnObject(form,"codigoCliente");
		var ObjNomeCliente = returnObject(form,"nomeCliente");
		ObjCodigoCliente.value = "";
		ObjNomeCliente.value = "";
		
		form.nomeCliente.value = "";
	}
}


function adicionaIntervaloReferencias(){

	var form = document.ConverterContasEmGuiaPorResponsavelActionForm;
	     
	      if  (form.referenciaInicial.value != "" && form.referenciaFinal.value != '') 	{				
			if (comparaMesAno(form.referenciaInicial.value, ">", form.referenciaFinal.value )) 	{
					alert('Mês/Ano Final do Período de Referência é anterior ao Mês/Ano Inicial do Período de Referência.');
		 	}else{
		 			redirecionarSubmit('exibirConverterContasEmGuiaPorResponsavelAction.do?adicionaIntervaloReferencias='+true);	
		 	}
		 		
		}			
}


function removeIntervaloReferencias(pIntervalo){

	document.forms[0].target='';
	if(confirm('Confirma remoção ?')){
		redirecionarSubmit('exibirConverterContasEmGuiaPorResponsavelAction.do?removeIntervaloReferencias='+pIntervalo);
	}		
}



function calcularValorTotalGuia(){

	var form = document.ConverterContasEmGuiaPorResponsavelActionForm;
	
	redirecionarSubmit('exibirConverterContasEmGuiaPorResponsavelAction.do?calcularValorTotalGuia=S');	
	     		
}




//-->
</SCRIPT>

</head>


<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/converterContasEmGuiaPorResponsavelAction" method="post">

	<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
					<td class="parabg">Converter Contas em Guia de Pagamento Por Cliente</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				
				 	<tr>
					<td width="30%"><strong>Cliente : <font color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<html:text property="codigoCliente" maxlength="9" size="9" onkeyup="return validaEnterCliente(event, 'exibirConverterContasEmGuiaPorResponsavelAction.do?alterarCliente=S', 'codigoCliente'); " />
						<a
						href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].codigoCliente.value);"
						alt="Pesquisar Cliente"> <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
						<logic:present name="corCliente">
							<logic:equal name="corCliente" value="exception">
								<html:text property="nomeCliente" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
							<logic:notEqual name="corCliente" value="exception">
								<html:text property="nomeCliente" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present> 
						<logic:notPresent name="corCliente">
							<logic:empty name="ConverterContasEmGuiaPorResponsavelActionForm"	property="codigoCliente">
								<html:text property="nomeCliente" size="38" value="" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="ConverterContasEmGuiaPorResponsavelActionForm" property="codigoCliente">
								<html:text property="nomeCliente" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
						</logic:notPresent>
						<a href="javascript:limparForm('cliente');"> 
							<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
						</a>
					</td>
				</tr>
				
					<tr>
					<td width="30%" height="10"><strong>Imóvel Destino:<font color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<html:text property="codigoImovel" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" onkeyup="validaEnterImovel(event, 'exibirConverterContasEmGuiaPorResponsavelAction.do', 'codigoImovel');" />
					<a
						href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].codigoImovel.value);"
						alt="Pesquisar Imóvel"> <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
				
							
						<logic:present name="corImovel">
							<logic:equal name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
							<logic:notEqual name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present> 
						<logic:notPresent name="corImovel">
							<logic:empty name="ConverterContasEmGuiaPorResponsavelActionForm"	property="codigoImovel">
								<html:text property="inscricaoImovel" size="38" value="" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:empty>
							<logic:notEmpty name="ConverterContasEmGuiaPorResponsavelActionForm" property="codigoImovel">
								<html:text property="inscricaoImovel" size="38"	readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
						</logic:notPresent>
						<a href="javascript:limparForm('imovel');"> 
							<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
						</a>	
					</td>
				</tr>
				
	
				
				<!-- X -->
			
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				 <tr>
                 	<td>
						<strong>Refência Contas:<font color="#FF0000">*</font></strong>
					</td>
					<td>
               	
               			
               			<html:text maxlength="7" property="referenciaInicial"
									size="7" tabindex="8" onkeyup="mascaraAnoMes(this, event);" name = "ConverterContasEmGuiaPorResponsavelActionForm" />
               			
               			 até
               		
               			
               			
               			<html:text maxlength="7" property="referenciaFinal"
									size="7" tabindex="8" onkeyup="mascaraAnoMes(this, event);" name = "ConverterContasEmGuiaPorResponsavelActionForm" />
               			
                 	</td>	
					<td align="left" colspan="3">																	
							<input type="button" name="btAdicionar" class="bottonRightCol" value="Adicionar" onClick="javascript:adicionaIntervaloReferencias();" />						
					</td>	
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>			
			
			<logic:notEmpty name="colecaoIntervaloReferenciaHelper">
				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="10%" align="center" ><strong>Remover</strong></td>
									<td width="30%" align="center" ><strong>Referência Inicial</strong></td>
									<td width="30%" align="center" ><strong>Referência Final</strong></td>
									<td width="30%" align="center" ><strong>Valor Total Contas</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>

									<logic:iterate name="colecaoIntervaloReferenciaHelper"
										id="intervaloReferenciaHelper" >
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
    											<div align="center">
	 											   <font color="#333333"> <img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:removeIntervaloReferencias(<bean:write name="intervaloReferenciaHelper" property="id" />)" />	
												</font></div>
											</td>
											<td width="30%" align="center">
												<bean:write	name="intervaloReferenciaHelper" property="referenciaInicial" />
											</td>
											<td width="30%" align="center">
												<bean:write	name="intervaloReferenciaHelper" property="referenciaFinal" />
											</td>
											
											<td width="30%" align="center">
												<bean:write	name="intervaloReferenciaHelper" property="valorTotalContas" />
											</td>
										</tr>
									</logic:iterate>
									
									<tr>
											<td width="10%" align="center">
											<p>&nbsp;</p>
									        </td>
											
											<td width="30%" align="center">
												<p>&nbsp;</p>
									        </td>
											
											<td width="30%" align="center">
												<b> Total :
									        </td>
									        
									        <td width="30%" align="center">
												<bean:write	name="ConverterContasEmGuiaPorResponsavelActionForm" property="valorTotalListaContas" />
									        </td>
									</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</logic:notEmpty>
				
				<!-- X -->


				<tr>
					<td><strong>Valor Juros Parcelamento:</strong></td>
					<td><html:text property="jurosParcelamento" size="15" name = "ConverterContasEmGuiaPorResponsavelActionForm" onkeyup="formataValorMonetario(this, 15);"  /></td>
				</tr>

				<tr>
				    <td><strong>Valor Total Guia de Pagamento:</strong></td>
					<td><html:text property="valorTotalGuiaPagamento" size="15" name = "ConverterContasEmGuiaPorResponsavelActionForm" disabled="true" /></td>
					<td><input type="button" name="btAdicionar" class="bottonRightCol" value="Calcular Valor Guia de Pagamento" onClick="javascript:calcularValorTotalGuia();" /></td>
					
				</tr>	


				
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
				</tr>

				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>	


				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirConverterContasEmGuiaPorResponsavelAction.do?menu=sim"/>'">
						
					<input type="button" name="Submit23" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					
					<td align="right"> <gcom:controleAcessoBotao name="Botao" value="Converter para Guia de Pagamento"
						onclick="validarForm(document.forms[0]);"
						url="converterContasEmGuiaPorResponsavelAction.do" /></td>
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

