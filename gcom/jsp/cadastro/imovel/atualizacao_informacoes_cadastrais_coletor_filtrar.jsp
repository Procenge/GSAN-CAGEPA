<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="FiltrarAtualizacaoCadastralColetorDadosActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];
		if(campoObrigatorio()){
			document.forms[0].submit();
			document.forms[0].target = "";
		}
	}
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipoConsulta=" + tipo, altura, largura);
			}
			else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}
				else{
					abrirPopup(url + "?" + "tipoConsulta=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	function campoObrigatorio(){       
		var form = document.forms[0];
		var msg = "";
				
		if(form.referenciaInicial.value.length < 1 || form.referenciaFinal.value.length < 1){
			msg = "Informe a Referência";
			form.referenciaInicial.focus();
			alert(msg);
			return false;
		}else{
			if (comparaMesAno(form.referenciaFinal.value,">=",form.referenciaInicial.value) == false){
				alert("Referência final maior que referência inicial.");
				return false;
			}
		}
		
		return true;
	}
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
		if (tipoConsulta == 'imovel') {
      		
      		form.matriculaImovel.value = codigoRegistro;
	  		form.inscricaoImovel.value = descricaoRegistro;
      		
	  		form.inscricaoImovel.style.color = "#000000";
	  		disabilitarHabilitarLocalidade();
		}
		if (tipoConsulta == 'localidade') {
      		
      		form.localidade.value = codigoRegistro;
	  		form.nomeLocalidade.value = descricaoRegistro;
      		
	  		form.nomeLocalidade.style.color = "#000000";
	  		
	  		form.setorComercial.focus();
	  		disabilitarHabilitarMatricula();
		}
		if (tipoConsulta == 'setorComercial') {
      		
      		form.setorComercial.value = codigoRegistro;
	  		form.nomeSetorComercial.value = descricaoRegistro;
      		
	  		form.nomeSetorComercial.style.color = "#000000";
	  		
	  		form.rota.focus();
		}
		if (tipoConsulta == 'rota') {
      		
      		form.rota.value = codigoRegistro;
	  		form.nomeRota.value = descricaoRegistro;
      		
	  		form.nomeRota.style.color = "#000000";
		}
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {
		  	form.setorComercial.value = codigoRegistro;
		  	form.nomeSetorComercial.value = descricaoRegistro;
		  	form.nomeSetorComercial.style.color = "#000000";
		  	
		}
	}

	function troca(str,strsai,strentra) 
    { 
            while(str.indexOf(strsai)>-1) 
            { 
                    str = str.replace(strsai,strentra); 
            } 
            return str; 
    }

	function maskKeyPress(objEvent) 
    { 
            var iKeyCode; 
                                     
            if(window.event) // IE 
            { 
                    iKeyCode = objEvent.keyCode; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
            else if(e.which) // Netscape/Firefox/Opera 
            { 
                    iKeyCode = objEvent.which; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
             
             
    }

	function replicarReferencia(){
		var formulario = document.forms[0]; 
		formulario.referenciaFinal.value = formulario.referenciaInicial.value;
		formulario.referenciaInicial.focus;
	}

	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		form.action="/gsan/exibirFiltrarAtualizacaoCadastralColetorDadosAction.do";

		switch(tipo){
			case 0: //De localidara pra baixo

				form.matriculaImovel.value = "";
			    form.inscricaoImovel.value = "";
			    form.matriculaImovel.focus();
				break;
			case 1: //De localidara pra baixo

				form.localidade.value = "";
				form.nomeLocalidade.value = "";
				form.setorComercial.value = "";
		     	form.nomeSetorComercial.value = "";
		     	form.rota.value="";
		     	form.nomeRota.value = "";
		     	form.localidade.focus();
				break;
			case 2: //De setor para baixo
		     	
		     	form.setorComercial.value = "";
		     	form.nomeSetorComercial.value = "";
		     	form.rota.value="";
		     	form.nomeRota.value = "";
		     	form.setorComercial.focus();
		     	break;
			case 3: //Rota
		     	
				form.rota.value="";
		     	form.nomeRota.value = "";
		     	form.setorComercial.focus();
		     	break;
		}
		form.submit();
	}

	function limpar(){

  		var form = document.forms[0];
  		form.matriculaImovel.value = "";
	    form.inscricaoImovel.value = "";
  		form.localidade.value = "";
  		form.setorComercial.value = "";
  		form.rota.value = "";
  		form.referenciaInicial.value = "";
  		form.referenciaFinal.value = "";
  		
  		form.nomeLocalidade.value = "";
  		form.nomeSetorComercial.value = "";
  		form.nomeRota.value = "";
  		
  	}

	function disabilitarHabilitarMatricula() {
		var form = document.forms[0];
		if((form.localidade.value == "")) {
			form.matriculaImovel.disabled = false;
			form.matriculaImovel.disabled = false;
		} else {
			form.matriculaImovel.value = ""
			form.matriculaImovel.value = ""

			form.matriculaImovel.disabled = true;
			form.matriculaImovel.disabled = true;
		}
	}
	function disabilitarHabilitarLocalidade() {
		var form = document.forms[0];
		if((form.matriculaImovel.value == "")) {
			form.localidade.disabled = false;
			form.localidade.disabled = false;
			
			form.setorComercial.disabled = false;
			form.setorComercial.disabled = false;
			
			form.rota.disabled = false;
			form.rota.disabled = false;
		} else {
			form.localidade.value = ""
			form.localidade.value = ""

			form.localidade.disabled = true;
			form.localidade.disabled = true;
			
			form.setorComercial.value = ""
			form.setorComercial.value = ""

			form.setorComercial.disabled = true;
			form.setorComercial.disabled = true;
			
			form.rota.value = ""
			form.rota.value = ""

			form.rota.disabled = true;
			form.rota.disabled = true;
		}
	}
	
	function submitDynaReset(){
		var form = document.forms[0];
		if(form.matriculaImovel.value == 0){
			form.matriculaImovel.value = "";	
		}
		if(form.localidade.value == 0){
			form.localidade.value = "";	
		}
		if(form.setorComercial.value == 0){
			form.setorComercial.value = "";	
		}
		if(form.rota.value == 0){
			form.rota.value = "";			
		}
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:disabilitarHabilitarMatricula(); disabilitarHabilitarLocalidade(); submitDynaReset();">

<html:form action="/filtrarAtualizacaoCadastralColetorDadosAction.do"
	name="FiltrarAtualizacaoCadastralColetorDadosActionForm"
	type="gcom.gui.cadastro.imovel.FiltrarAtualizacaoCadastralColetorDadosActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>
<!-- 
	<input type="hidden" name="campoOrigem" value=""/>
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioContasEmAtraso.rpt"/>
	 
	
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>
-->
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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Filtrar Atualiza&ccedil;&atilde;o de Informa&ccedil;&otilde;es Cadastrais via Coletor de Dados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para filtrar, informe os dados abaixo:</td>
					<td align="right" colspan="3">
					<input type="checkbox" name="consultar" value="1" />
					<strong>Consultar</strong></td>
				</tr>
				
				
				<!-- Mudança de layout de página. -->
								
				<tr>
					<td><strong>Refer&ecirc;ncia: <font color="#FF0000">*</font></strong></td>					
					<td >						
						<html:text maxlength="7"
							
							property="referenciaInicial" 
							size="7" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:replicarReferencia();verificaAnoMes(this);"
							/>
						<strong>a</strong>
						<html:text maxlength="7"
							
							property="referenciaFinal" 
							size="7"
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:verificaAnoMes(this);"
							/>(mm/aaaa)
					</td>
				</tr>
				<tr> 
		                <td>
		                	<strong>
			             		<span class="style2">Matr&iacute;cula do Im&oacute;vel</span>:
		                	</strong>
		                </td>
		                <td colspan="6"><span class="style2"><strong>
							<html:text property="matriculaImovel" size="9" maxlength="9" 
									   onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarAtualizacaoCadastralColetorDadosAction.do?objetoConsulta=1', 'matriculaImovel','Matrícula do Imovel');"
									   tabindex="3"
									   onkeyup="javascript:disabilitarHabilitarLocalidade()"
									   onblur="javascript:disabilitarHabilitarLocalidade()" />
							
							<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].matriculaImovel);">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
								 
							<logic:present name="inscricaoImovelEncontrada" scope="session">
								<html:text property="inscricaoImovel" readonly="true" 
										   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
							</logic:present> 
		
							<logic:notPresent name="inscricaoImovelEncontrada" scope="session">
								<html:text property="inscricaoImovel" readonly="true" 
										   style="background-color:#EFEFEF; border:0; color: red" size="40" maxlength="40" />
							</logic:notPresent>  						 
								 
							<a href="javascript:limparBorrachaOrigem(0);">
								<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
		                  </strong></span></td>
				</tr>					
				<tr>
					<td><strong>Localidade:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="localidade" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirFiltrarAtualizacaoCadastralColetorDadosAction.do?objetoConsulta=2','localidade','Localidade'); return isCampoNumerico(event);"
							 onkeyup="javascript:disabilitarHabilitarMatricula();"
							 onblur="javascript:disabilitarHabilitarMatricula();"
							 />
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '',document.forms[0].localidade);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeEncontrada" scope="session">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeEncontrada" scope="session">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>				
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="setorComercial" 
							size="3"
							onkeyup="limparOrigem(2);"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarAtualizacaoCadastralColetorDadosAction.do?objetoConsulta=3','setorComercial','Setor Comercial'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercial', 'idLocalidade', document.forms[0].localidade.value , 275, 480, 'Informe Localidade',document.forms[0].setorComercial);
						         limparOrigem(2);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialEncontrado" scope="session">
							<html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialEncontrado" scope="session">
							<html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>				
				<tr>
					<td>
						<strong>Rota:</strong>
					</td>
					<td>
						
						<html:text maxlength="3" 
							
							property="rota" 
							size="3"
							onkeyup="limparOrigem(2);"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarAtualizacaoCadastralColetorDadosAction.do?objetoConsulta=4','rota','Rota'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarRotaAction.do','rota&codigoSetorComercial='+document.forms[0].setorComercial.value,'idLocalidade', document.forms[0].localidade.value, 400, 800, 'Informe Rota', document.forms[0].rota);
						         limparOrigem(2);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Rota" /></a>
								

						<logic:present name="rotaEncontrada" scope="session">
							<html:text property="nomeRota" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="rotaEncontrada" scope="session">
							<html:text property="nomeRota" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(3);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>              	
				<tr>
					<td height="24" colspan="2">
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:window.location.href='exibirFiltrarAtualizacaoCadastralColetorDadosAction.do?menu=sim'"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Filtrar" 
							onClick="javascript:validarForm();" />
							
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

<!-- relatorio_contas_atraso_gerar.jsp -->
</html:html>
