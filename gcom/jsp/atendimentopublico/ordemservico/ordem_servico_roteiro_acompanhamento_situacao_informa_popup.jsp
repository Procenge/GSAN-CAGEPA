<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="java.util.Date"%>
<%@ page import="gcom.util.Util"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.atendimentopublico.ordemservico.OrdemServico"%>

<%-- Carrega javascripts da biblioteca --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<%-- Novos Javascripts --%>

<script language="JavaScript">
   	function limpar() {

		var form = document.forms[0];
		form.motivoNaoEncerramento.disabled = false;
		
		form.situacaoOrdemServico.selectedIndex = 0;
		form.motivoNaoEncerramento.selectedIndex = 0;

   	}
   	
   	function concluir() {
		var form = document.forms[0];

		if(validaSelect() != false){
	    	
			if(form.situacaoOrdemServico.value == 2){
				var lista = new Array();
		    	lista[0] = form.idOrdemServico.value;
		    	lista[1] = form.dataRoteiro.value;

				if (form.submitAutomatico.value == 'ok') {
			    	form.action = 'exibirEncerrarOrdemServicoAction.do?numeroOS='+ 	form.idOrdemServico.value + '&dataRoteiro=' + form.dataRoteiro.value + '&retornoTela=exibirAcompanharRoteiroProgramacaoOrdemServicoAction.do';
				    form.submit();
				}else{
					window.opener.recuperarDadosPopup('', lista, 'encerrarOrdemServico');
					window.close();
				}

			}else{
				var lista = new Array();
		    	lista[0] = form.situacaoOrdemServico.value;
		    	lista[1] = form.motivoNaoEncerramento.value;
		    	lista[2] = form.idOrdemServico.value;
		    	lista[3] = form.chaveEquipe.value;
		    	
		    	alert(form.dataVisita);
		    	
		    	if (form.dataVisita != undefined) {
		    		lista[4] = form.dataVisita.value;
		    		lista[5] = form.horaVisita.value;
		    	} else {
		    		lista[4] = '';
		    		lista[5] = '';		    		
		    	}
		    	
				window.opener.recuperarDadosPopup('', lista, 'situacaoOs');
				window.close();
		    	
			}
		}
   	}
   	
	function validaForm(){
		var form = document.forms[0];
		
		if(	form.situacaoOrdemServico.value == '1'){

			form.motivoNaoEncerramento.disabled = false;
		}else{
			form.motivoNaoEncerramento.disabled = true;
		}

	}

	function enviarFotos() {
		redirecionarSubmit('acompanharRoteiroProgramacaoOrdemServicoSalvarFotosAction.do');
	}
    
	function validaSelect() {
		var form = document.forms[0];
		
    	if (form.situacaoOrdemServico.value == '-1') {
			alert('Informe a Nova Situação da OS');
			form.situacaoOrdemServico.focus();
			return false;
    	}
    	
    	if(form.motivoNaoEncerramento.disabled == false && 
    		form.motivoNaoEncerramento.value == '-1'){

			alert('Informe o Motivo do Não Encerramento da OS');
			form.motivoNaoEncerramento.focus();
			return false;
    	
    	}
    	
    	if (form.motivoVisitaImprodutiva.value == '1' && form.situacaoOrdemServico.value == <%=""+OrdemServico.SITUACAO_PENDENTE%>) {
			if (!validateRequired(form) || !validateCaracterEspecial(form) || !validateDate(form)) {
				return false;
			} else {
				if (form.horaVisita.value.length != 5) {
					alert("Hora da visita inválida.")
					return false;
				}
			}	
    	}
    	
    	return true;
	}

   function validarSubmitAutomatico(form) {
   		if (form.submitAutomatico.value == 'ok') {
   			validaForm();
   			concluir();
      	}
   }
   
   function modificarMotivoEncerramento() {
		 var form = document.forms[0];	
		 
		form.action = 'exibirAcompanharRoteiroProgramacaoOrdemServicoInformarSituacaoAction.do?modificarMotivoEncerramento=S'
		form.submit();	

	} 
   
   function required () { 
	     this.aa = new Array("dataVisita", "Informe Data da Visita.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	     this.ab = new Array("horaVisita", "Informe Hora da Visita.", new Function ("varName", " return this[varName];"));
   } 

    function caracteresespeciais () { 
     this.aa = new Array("dataVisita", "Data da Visita possui caracteres especiais.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("horaVisita", "Hora da Visita possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 
	
    function DateValidations () { 
     this.aa = new Array("dataVisita", "Data da Visita inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 
	    
</script>
</head>

<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(600, 400);">	

<html:form action="/exibirAcompanharRoteiroProgramacaoOrdemServicoAction.do"
	enctype="multipart/form-data"
	name="AcompanharRoteiroProgramacaoOrdemServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.AcompanharRoteiroProgramacaoOrdemServicoActionForm"
	method="post">
	
	<html:hidden property="dataRoteiro"/>
	<html:hidden property="chaveEquipe"/>
	
	<html:hidden property="motivoVisitaImprodutiva"/>
	<html:hidden property="motivoCobrarVisitaImprodutiva"/>
		
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr> 
	    	<td width="560" valign="top" class="centercoltext">
	    		<table height="100%">
	        		<tr> 
	          			<td></td>
	        		</tr>
	      		</table>
	      		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        		<tr> 
	          			<td width="11">
	          				<img border="0" 
	          					src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
	          			</td>
	          			<td class="parabg">Informar Situa&ccedil;&atilde;o da Ordem de Servi&ccedil;o</td>
	          			<td width="11">
	          				<img border="0" 
	          					src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
	          			</td>
	        		</tr>
	      		</table>
	      		<p>&nbsp;</p>
	      		<table width="100%" border="0">

			        <tr> 
			          <td colspan="3">Selecione a nova situa&ccedil;&atilde;o da ordem de servi&ccedil;o:</td>
			        </tr>

					<tr>
						<td>
							<strong> Ordem de Servi&ccedil;o:</strong>
						</td>

						<td colspan="2">
							<strong> 
								<html:text property="idOrdemServico" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="9"
									maxlength="9" />

								<html:text property="descricaoOrdemServico" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="45"
									maxlength="45" />

							</strong>
						</td>
					</tr>

			        <tr>
			        	<td>
			        		<strong>Situa&ccedil;&atilde;o Atual:</strong>
			        	</td>
			          	
			          	<td colspan="2">
			          		<strong> 
								<html:text property="situacaoAtual" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="50"
									maxlength="50" />

							</strong>			          		
						</td>
			        </tr>
					
			        <tr>
			        	<td height="24" colspan="3">
			        		<hr>
			        	</td>
			        </tr>

			        <tr> 
			        	<td height="24">
			        		<strong>Nova Situa&ccedil;&atilde;o da OS:</strong>
			        	</td>

						<td>
							<strong> 

							<html:select property="situacaoOrdemServico"
								style="width: 300px;"
								onchange="javascript:validaForm();">
	
								<html:option
									value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
								</html:option>
	
								<html:option
									value="<%=""+OrdemServico.SITUACAO_PENDENTE%>">PENDENTES
								</html:option>
	
								<html:option
									value="<%=""+OrdemServico.SITUACAO_ENCERRADO%>">ENCERRADOS
								</html:option>
	
								<html:option
									value="<%=""+OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO%>">EXECU&Ccedil;&Atilde;O EM ANDAMENTO
								</html:option>
							
							</html:select> 														
							</strong>
						</td>
					</tr>

			        <tr> 
			        	<td height="24">
			        		<strong>Motivo do N&atilde;o Encerramento da OS:</strong>
			        	</td>
						<td>
							<strong> 
							<html:select property="motivoNaoEncerramento" style="width: 230px;" onchange="javascript:modificarMotivoEncerramento();">
								<html:option
									value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
								</html:option>
						
								<logic:present name="colecaoMotivoNaoEncerramento"	scope="request">
									<html:options collection="colecaoMotivoNaoEncerramento"
										labelProperty="descricao" 
										property="id" />
								</logic:present>
							</html:select> 														
							</strong>
						</td>
        			</tr>
        			
        		<logic:equal name="AcompanharRoteiroProgramacaoOrdemServicoActionForm"  property="motivoVisitaImprodutiva" value="1">
					<tr>
						<td HEIGHT="30"><strong>Data da Visita:<font color="#FF0000">*</font></strong></td>
						<td>
							<html:text property="dataVisita" size="11" maxlength="10"
								tabindex="3" onkeyup="mascaraData(this, event)" />
							<a
								href="javascript:abrirCalendario('AcompanharRoteiroProgramacaoOrdemServicoActionForm', 'dataVisita');">
							<img border="0"
								src="<bean:message key='caminho.imagens'/>calendario.gif"
								width="20" border="0" alt="Exibir Calendário" tabindex="4" /></a>
						<strong>&nbsp;(dd/mm/aaaa)</strong></td>
					</tr>
					<tr>
						<td HEIGHT="30"><strong>Hora da Visita:<font color="#FF0000">*</font></strong></td>
						<td>
							<html:text property="horaVisita" size="10" maxlength="5" tabindex="5"
								onkeyup="mascaraHora(this, event)" />
						<strong>&nbsp;(hh:mm)</strong></td>
					</tr>   
				</logic:equal>     			
        			

					<tr>
						<logic:lessEqual name="AcompanharRoteiroProgramacaoOrdemServicoActionForm" property="qtdFotos" value="2">
							<td><strong>Fotos:</strong></td>
						</logic:lessEqual>
					</tr>
						<logic:lessEqual name="AcompanharRoteiroProgramacaoOrdemServicoActionForm" property="qtdFotos" value="2">
							<tr><td colspan="2"><input name="fotos1" type="file" id="fotos1" size="68" onchange="lblfotos1.value = this.value"/></tr>
						</logic:lessEqual>
						
						<logic:lessEqual name="AcompanharRoteiroProgramacaoOrdemServicoActionForm" property="qtdFotos" value="1">
							<tr><td colspan="2"><input name="fotos2" type="file" id="fotos2" size="68" onchange="lblfotos2.value = this.value"/></tr>
						</logic:lessEqual>
						
						<logic:equal name="AcompanharRoteiroProgramacaoOrdemServicoActionForm" property="qtdFotos" value="0">
							<tr><td colspan="2"><input name="fotos3" type="file" id="fotos3" size="68" onchange="lblfotos3.value = this.value"/></tr>
						</logic:equal>
						
						<tr><td colspan="2" align="right">
							<a href="javascript:abrirPopup('exibirFotoOSAction.do?idOrdemServico=${AcompanharRoteiroProgramacaoOrdemServicoActionForm.idOrdemServico}&idOrdemServicoProgramacao=${AcompanharRoteiroProgramacaoOrdemServicoActionForm.idOrdemServicoProgramacao}&remover=true', 600, 800)" style="text-decoration: none">											
								<img src="imagens/imgfolder.gif" width="18" height="18" border="0">
							</a>
						</td></tr>
						
						<logic:lessEqual name="AcompanharRoteiroProgramacaoOrdemServicoActionForm" property="qtdFotos" value="2">
							<tr><td colspan="2" align="right">
							<input name="botaoSalvarFotos" type="button" class="bottonRightCol" value="Salvar Fotos" onclick="enviarFotos();" style="text-decoration: none"><td></tr>
						</logic:lessEqual>

			        <tr> 
			        	<td height="27" colspan="2"> 
			        		<div align="left">

	                        	<input name="ButtonFechar" 
	                        		type="button" 
	                        		class="bottonRightCol" 
	                        		value="Fechar"
	                        		onClick="javascript:window.close();">
			        		
	              				<input name="Button2" 
	              					type="button" 
	              					class="bottonRightCol" 
	              					value="Limpar" 
	              					onClick="javascript:limpar();">
			            	</div>
			            </td>
			          	
			          	<td height="27" colspan="5">
	          				<div align="right"> 
	              				<input name="Button2" 
	              					type="button" 
	              					class="bottonRightCol" 
	              					value="Concluir" 
	              					onClick="javascript:concluir();">

			            	</div>
			            </td>
			        </tr>

				   <!-- Utilizado pelo setor de Mobilidade para encerramento automático de OS através de dispositivo móvel -->
				   <input type="hidden" name="submitAutomatico"	value="${requestScope.submitAutomatico}" />
				   <script language="JavaScript">
				   		validarSubmitAutomatico(document.forms[0]);
				   </script>

	      		</table>
	      		<p>&nbsp;</p>
	      	</td>
	  	</tr>
	</table>
</html:form>
</body>
</html:html>