<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="displaytag" %>
<link rel="stylesheet" href="<bean:message key="caminho.css"/>displaytag.css" type="text/css">	

<%@ page import="gcom.atendimentopublico.ordemservico.Atividade"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="ManterDadosAtividadesOrdemServicoActionForm"/>

<script>

function limpar(){

	var form = document.forms[0];
	
	form.idAtividade.value = "";
	form.descricaoAtividade.value = "";
			   
	//Coloca o foco no objeto selecionado
	form.idAtividade.focus();
}

function limparDescricao(){
	
	var form = document.forms[0];
	form.descricaoAtividade.value = "";
}

function remover(idAtividade){
 	var form = document.forms[0];
 	
	form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do?removerAtividade=" + idAtividade;
	if (confirm("Confirma remoção?")){
		form.submit();
	}
}

function removerInterrupcao(posicaoInterrupcao) {
 	var form = document.forms[0];
 	
	form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do?posicaoInterrupcao=" + posicaoInterrupcao;
	if (confirm("Confirma remoção?")) {
		form.submit();
	}
}

function removerVala(posicaoVala) {
 	var form = document.forms[0];
 	
 	form.posicaoVala.value = posicaoVala;
	form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do";
	
	if (confirm("Confirma remoção?")) {
		form.submit();
	}
}

function adicionar(){
	var form = document.forms[0];
	
	//if (validateManterDadosAtividadesOrdemServicoActionForm(form)){
		form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do?adicionarAtividade=OK";
		form.submit();
	//}
}

function adicionarInterrupcao() {

	var form = document.forms[0];

	if (form.kmInicial.value != ""
		&& form.kmFinal.value != ""
		&& form.dataInicioDeslocamento.value != ""
		&& form.dataFimDeslocamento.value != ""
		&& form.horaInicioDeslocamento.value != ""
		&& form.horaFimDeslocamento.value != ""
		&& form.dataInicioInterrupcao.value != ""
		&& form.horaInicioInterrupcao.value != ""
		&& form.dataFimInterrupcao.value != ""
		&& form.horaFimInterrupcao.value != ""
		&& form.kmInterrupcao.value != ""
		&& form.idMotivoInterrupcao.value != "-1") {
		 form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do?adicionarInterrupcao=OK";
		 form.submit();
	} else {
		alert("Para adicionar uma interrupção é preciso informar todos os dados do deslocamento.");
	}
}

function adicionarVala() {

	var form = document.forms[0];

	form.action = "/gsan/exibirManterDadosAtividadesOrdemServicoAction.do?adicionarVala=OK";
	form.submit();
}

function apropriarHoras(idAtividade, descricaoAtividade){

	var form = document.forms[0];
	
	numeroOS = form.numeroOSForm.value;
	roteiro = form.dataRoteiroForm.value;
	encerramento = form.dataEncerramentoForm.value;
	
	caminhoAction = "/gsan/exibirManterHorasExecucaoEquipeOSAction.do?caminhoRetorno=/gsan/exibirManterDadosAtividadesOrdemServicoAction.do&ordemServico=" + 
	numeroOS + "&idAtividade=" + idAtividade + "&descricaoAtividade=" + descricaoAtividade;
	
	if (roteiro.length > 0){
		caminhoAction = caminhoAction + "&dataRoteiro=" + roteiro;
	}
	
	if (encerramento.length > 0){
		caminhoAction = caminhoAction + "&dataEncerramento=" + encerramento;
	}
	
	redirecionarSubmit(caminhoAction);
}


function apropriarMaterial(idAtividade, descricaoAtividade){

	var form = document.forms[0];
	
	numeroOS = form.numeroOSForm.value;
	
	caminhoAction = "/gsan/exibirManterMaterialExecucaoOSAction.do?caminhoRetorno=/gsan/exibirManterDadosAtividadesOrdemServicoAction.do&ordemServico=" + 
	numeroOS + "&idAtividade=" + idAtividade + "&descricaoAtividade=" + descricaoAtividade;
	
	redirecionarSubmit(caminhoAction);
}
 
function consultarPavimentos(obj) {
 	var idLocalOcorrencia = obj.options[obj.selectedIndex].value;
  	var selectPavimentos = document.getElementById("idPavimento");
  
  	selectPavimentos.length=0;
  	if (idLocalOcorrencia != "-1") {
		  AjaxService.consultarPavimentos( idLocalOcorrencia, 
		       function(pavimento) {
				  for (key in pavimento){
					  var novaOpcao = new Option(pavimento[key], key);
					  selectPavimentos.options[selectPavimentos.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		selectPavimentos.options[selectPavimentos.length] = novaOpcao;
  	}	
}

function consultarCombosAgua(obj) {
 	var idRedeRamal = obj.options[obj.selectedIndex].value;
  	var selectDiametroAgua = document.getElementById("idDiametroAgua");
  	var selectMaterialAgua = document.getElementById("idMaterialAgua");
  	
  	var idDiametroAguaSelecionado = "${ManterDadosAtividadesOrdemServicoActionForm.idDiametroAgua}";
  	var idMaterialAguaSelecionado = "${ManterDadosAtividadesOrdemServicoActionForm.idMaterialAgua}";
  
  	selectDiametroAgua.length=0;
  	selectMaterialAgua.length=0;
  	if (idRedeRamal != "-1") {
		  AjaxService.consultarDiametroAgua( idRedeRamal, 
		       function(diametro) {
				  for (key in diametro){
					  var novaOpcao = new Option(diametro[key], key);
					  if (key == idDiametroAguaSelecionado) {
					  	novaOpcao.selected = true;
					  }
					  selectDiametroAgua.options[selectDiametroAgua.length] = novaOpcao;
		  		   }
				  });
		  AjaxService.consultarMaterialAgua( idRedeRamal, 
		       function(material) {
				  for (key in material){
					  var novaOpcao = new Option(material[key], key);
					  if (key == idMaterialAguaSelecionado) {
					  	novaOpcao.selected = true;
					  }
					  selectMaterialAgua.options[selectMaterialAgua.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		selectDiametroAgua.options[selectDiametroAgua.length] = novaOpcao;
		selectMaterialAgua.options[selectMaterialAgua.length] = novaOpcao;
  	}	
}

function consultarCombosEsgoto(obj) {
 	var idRedeRamal = obj.options[obj.selectedIndex].value;
  	var selectDiametroEsgoto = document.getElementById("idDiametroEsgoto");
  	var selectMaterialEsgoto = document.getElementById("idMaterialEsgoto");
  	
  	var idDiametroEsgotoSelecionado = "${ManterDadosAtividadesOrdemServicoActionForm.idDiametroEsgoto}";
  	var idMaterialEsgotoSelecionado = "${ManterDadosAtividadesOrdemServicoActionForm.idMaterialEsgoto}";
    
  	selectDiametroEsgoto.length=0;
  	selectMaterialEsgoto.length=0;
  	if (idRedeRamal != "-1") {
		  AjaxService.consultarDiametroEsgoto( idRedeRamal, 
		       function(diametro) {
				  for (key in diametro){
					  var novaOpcao = new Option(diametro[key], key);

					  if (key == idDiametroEsgotoSelecionado) {
					  	novaOpcao.selected = true;
					  }
					  selectDiametroEsgoto.options[selectDiametroEsgoto.length] = novaOpcao;
		  		   }
				  });
		  AjaxService.consultarMaterialEsgoto( idRedeRamal, 
		       function(material) {
				  for (key in material){
					  var novaOpcao = new Option(material[key], key);
					  if (key == idMaterialEsgotoSelecionado) {
					  	novaOpcao.selected = true;
					  }
					  selectMaterialEsgoto.options[selectMaterialEsgoto.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		selectDiametroEsgoto.options[selectDiametroEsgoto.length] = novaOpcao;
		selectMaterialEsgoto.options[selectMaterialEsgoto.length] = novaOpcao;
  	}	
}

</script>

<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">
	function init() {
	
	<logic:present name="fecharPopup" scope="request">
		<logic:present name="caminhoRetornoDadosAtividadesOS">
			redirecionarSubmit('${sessionScope.caminhoRetornoDadosAtividadesOS}');
		</logic:present>
		<logic:notPresent name="caminhoRetornoDadosAtividadesOS">
			<logic:present name="caminhoRetorno" scope="request">
				chamarReload('${requestScope.caminhoRetorno}');
				window.close();
			</logic:present>
			<logic:notPresent name="caminhoRetorno" scope="request">
				window.close();
			</logic:notPresent>
		</logic:notPresent>
	</logic:present>
	
	<logic:notPresent name="fecharPopup" scope="request">
		window.focus();
		resizePageSemLink(720, 720);
		<c:if test="${not empty requestScope.nomeCampo}">
		setarFoco('${requestScope.nomeCampo}');
		</c:if>
	</logic:notPresent>
	
		  var form = document.forms[0];
		  if (form.idRedeRamalAgua != undefined) {
		  	form.idRedeRamalAgua.onchange();
		  }
		  if (form.idRedeRamalEsgoto != undefined) {
		  	form.idRedeRamalEsgoto.onchange();
		  }
		  if (form.idLocalOcorrencia != undefined) {
		  	 form.idLocalOcorrencia.onchange();
		  }
	}

</script>

</head>

<body leftmargin="5" topmargin="5" onload="init();">

<html:form action="/exibirManterDadosAtividadesOrdemServicoAction?inserir=ok" method="post">
<input type="hidden" name="posicaoVala" value=""/>
<table width="652" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="652" valign="top" class="centercoltext">
    <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Manter Dados das Atividades da Ordem de Serviço</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td colspan="4">
          	Apropriar as horas e o material de execução das atividades da Ordem de Serviço:
          </td>
        </tr>
        <tr>
          <td width="20%"><strong>Número da OS: </strong></td>
          <td width="80%" colspan="3">
          	<html:text property="numeroOSForm" size="4" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
          </td>
        </tr>
        <tr>
          <td width="20%"><strong>Roteiro: </strong></td>
          <td width="80%" colspan="3">
          	<html:text property="dataRoteiroForm" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
          </td>
        </tr>
        <tr>
          <td width="20%"><strong>Encerramento: </strong></td>
          <td width="80%" colspan="3">
          	<html:text property="dataEncerramentoForm" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
          </td>
        </tr>
        <tr>
          <td colspan="4" height="10"></td>
        </tr>
        <tr>
      		<td WIDTH="20%"><strong>Atividade:</strong></td>
        	<td width="80%" colspan="3">
			<html:text property="idAtividade" size="7" maxlength="6" tabindex="1" onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirManterDadosAtividadesOrdemServicoAction.do', 'idAtividade', 'Atividade');"/>
			<a href="javascript:redirecionarSubmit('exibirManterDadosAtividadesOrdemServicoAction.do?pesquisarAtividade=OK');">
			<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" alt="Pesquisar" border="0"></a>

			<logic:present name="corAtividade">

				<logic:equal name="corAtividade" value="exception">
					<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corAtividade" value="exception">
					<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corAtividade">

				<logic:empty name="ManterDadosAtividadesOrdemServicoActionForm" property="idAtividade">
					<html:text property="descricaoAtividade" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="ManterDadosAtividadesOrdemServicoActionForm" property="idAtividade">
					<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>
        	
        	<a href="javascript:limpar();">
        	<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
       	</td>
	  	</tr>
       	<tr>
          <td colspan="4" height="10"></td>
        </tr>
        <tr>
      	<td colspan="4">
      	
      		<table width="98%">
			<tr>
				<td><strong>Execução das Atividades:</strong></td>
				<td align="right"><input type="button" class="bottonRightCol"
				value="Adicionar" tabindex="11" style="width: 80px" onclick="adicionar();"></td>
			</tr>
			</table>
      		<table width="98%" border="0">
	  		<tr> 
          		<td colspan="4">
		  
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr> 
                		<td> 
							
							<table width="100%" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc"> 

								<td align="center" width="10%"><FONT COLOR="#000000"><strong>Remover</strong></FONT></td>
								<td align="center" width="15%"><FONT COLOR="#000000"><strong>Código</strong></FONT></td>
								<td align="center" width="45%"><FONT COLOR="#000000"><strong>Atividade</strong></FONT></td>
								<td align="center" width="15%"><FONT COLOR="#000000"><strong>Horas</strong></FONT></td>
								<td align="center" width="15%"><FONT COLOR="#000000"><strong>Material</strong></FONT></td>
					
							</tr>
                    		</table>
					
						</td>
            		</tr>
            		
            		<tr> 
						<td> 
					
							<div style="width: 100%; height: 100; overflow: auto;">	
							
							<% String cor = "#cbe5fe";%>
							
							<table width="100%" align="center" bgcolor="#90c7fc">

								<logic:iterate name="colecaoAtividade" id="atividade" type="Atividade">
                            
									
									
									<%	if (cor.equalsIgnoreCase("#FFFFFF")){
											cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
									<%} else{
											cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
									<%}%> 
									
									<td align="center" width="10%">
										<a href="javascript:remover(<%="" + atividade.getId().intValue()%>)">
											<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
										</a>
									</td>
									
									<td align="center" width="15%"><bean:write name="atividade" property="id"/></td>
									
									<td width="45%"><bean:write name="atividade" property="descricao"/></td>
									
									<td align="center" valign="middle" width="15%">
										<a href="javascript:apropriarHoras('<%="" + atividade.getId().intValue()%>', '<%="" + atividade.getDescricao()%>');">
											<img src="<bean:message key='caminho.imagens'/>relogioTransparente.gif" border="0">
										</a>
									</td>
									
									<td align="center" valign="middle" width="15%">
										<a href="javascript:apropriarMaterial('<%="" + atividade.getId().intValue()%>', '<%="" + atividade.getDescricao()%>');">
											<img src="<bean:message key='caminho.imagens'/>marteloTransparente3.gif" border="0">
										</a>
									</td>
									
									</tr>

								</logic:iterate>
								</table>
							</div>
						</td>
            		</tr>
					</table>
				</td>
			</tr>
			</table>
      		</td>
      	</tr>
        <tr>
        	<tr>
				<td height="24" colspan="7" bordercolor="#000000" class="style1">
					<hr>
				</td>
			</tr>
			<tr>
        	<td colspan="4">
        		<table width="100%">
        		<c:if test="${ordemServico.servicoTipo.indicadorDeslocamento != 2}"> 
						<tr><td colspan="6"><strong>Dados do Deslocamento:</strong></td></tr>
						<tr>
				          <td><strong>Km inicial:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="kmInicial" size="7" maxlength="7" onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)" /></td>
				          <td><strong>Km final:<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="kmFinal" size="7" maxlength="7" onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)"/>
				          </td>
				        </tr>
				         <tr>
				          <td><strong>Data Inicial:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="dataInicioDeslocamento" size="10" maxlength="10" tabindex="1"onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('ManterDadosAtividadesOrdemServicoActionForm', 'dataInicioDeslocamento');" >
								  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
								</a>
						  </td> 
				          <td><strong>Hora Inicial<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="horaInicioDeslocamento" size="5" maxlength="5" tabindex="1" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
				        </tr>
				        <tr>
				          <td><strong>Data Final:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="dataFimDeslocamento" size="10" maxlength="10" tabindex="1"onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('ManterDadosAtividadesOrdemServicoActionForm', 'dataFimDeslocamento');" >
								  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
								</a>
						  </td> 
				          <td><strong>Hora Final<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="horaFimDeslocamento" size="5" maxlength="5" tabindex="1" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
				        </tr>
				        <tr>
				        	<td>&nbsp;</td>
				        </tr>
				        <tr><td colspan="6"><strong>Dados das Interrupções:</strong></td></tr>
				        <tr>
				          <td><strong>Data Inicial:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="dataInicioInterrupcao" size="10" maxlength="10" tabindex="1"onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('ManterDadosAtividadesOrdemServicoActionForm', 'dataInicioInterrupcao');" >
								  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
								</a>
						  </td> 
				          <td><strong>Hora Inicial<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="horaInicioInterrupcao" size="5" maxlength="5" tabindex="1" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
				        </tr>
				        <tr>
				          <td><strong>Data Final:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="dataFimInterrupcao" size="10" maxlength="10" tabindex="1"onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('ManterDadosAtividadesOrdemServicoActionForm', 'dataFimInterrupcao');" >
								  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
								</a>
						  </td> 
				          <td><strong>Hora Final<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="horaFimInterrupcao" size="5" maxlength="5" tabindex="1" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
				        </tr>
				        <tr>
				          <td><strong>Km Interrupção:<span style="color:#FF0000">*</span></strong></td>
				          <td colspan="2"><html:text property="kmInterrupcao" size="7" onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)" /></td>
				          <td><strong>Motivo<span style="color:#FF0000">*</span></strong></td>
				          <td> 
				          	<select name="idMotivoInterrupcao">
		        			    <option value="-1">&nbsp;</option>
		        			    <c:forEach items="${colecaoMotivoInterrupcao}" var="motivo" >
		        			    	<option value="${motivo.id}">${motivo.descricao}</option>
		        			    </c:forEach>
							</select>
				          </td>
				        </tr>
				        <tr>
				          <td colspan="6" height="10"></td>
				        </tr>
				        <tr>
				        	<td colspan="5"><strong>Horários Interrupção:</strong></td>
							<td align="right">
								<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="adicionarInterrupcao();">
							</td>
				        </tr>
				        <tr>
				        <td colspan="6">
				        	<c:set var="i" value="0" />
				        	<display:table class="dataTable" name="colecaoInterrupcao" sort="list" id="interrupcao"  pagesize="15" excludedParams="" > 
       
						        <display:column style="width:10%; text-align: center;" sortable="false" title="Remover">
					       			<a href="javascript:removerInterrupcao(${i})">
										<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
									</a>
						        </display:column>
						        <display:column property="interrupcaoInicio" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" title="Data/Hora Início"/>
						        <display:column property="interrupcaoFim" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" title="Data/Hora Fim"/>
						        <display:column property="numeroKm" sortable="false" title="Km"/>
						        <display:column property="motivoInterrupcao.descricao" sortable="false" title="Motivo"/> 
						    	<c:set var="i" value="${i+1}" />
						    </display:table>
						    
                    	</td>
                    	<tr>
				          <td colspan="6" height="10"></td>
				        </tr>
				        <tr>
	            		 	<td colspan="6"><hr/>
	            		</tr>
				        
					</c:if>
					<!--  INÍCIO DOS DADOS - SOLICITAÇÃO 315 ADA -->
					<c:if test="${ordemServico.servicoTipo.indicadorRedeRamalAgua != 2}"> 
						
						<tr>
	            		 	<td colspan="6"><strong>Dados Rede/Ramal de Água:</strong></td>
	            		</tr>
            			
	            		  <c:if test="${ordemServico.servicoTipo.indicadorCausaOcorrencia != 2}">
	            		    <tr>
	            			  <td><strong>Causa<span style="color:#FF0000">*</span></strong></td>
					          <td> 
					          	<select name="idCausaAgua">
			        			    <option value="-1">&nbsp;</option>
			        			    <c:forEach items="${colecaoCausa}" var="causa" >
			        			    	<option
			        			    	 <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idCausaAgua == causa.id}">selected</c:if>
			        			    	 value="${causa.id}">${causa.descricao}</option>
			        			    </c:forEach>
								</select>
					          </td>
					        </tr>
	            		  </c:if>
	            		  
	            		<tr>
            			  <td><strong>Rede/Ramal<span style="color:#FF0000">*</span></strong></td>
				          <td> 
				          	<select name="idRedeRamalAgua" onchange="consultarCombosAgua(this);">
		        			    <option value="-1">&nbsp;</option>
		        			    <option 
		        			    <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idRedeRamalAgua == 1}">selected</c:if>
		        			    value="1">Rede</option>
		        			    <option 
		        			    <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idRedeRamalAgua == 2}">selected</c:if>
		        			    value="2">Ramal</option>  	
		        			    
		        			     <option 
		        			    <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idRedeRamalAgua == 3}">selected</c:if>
		        			    value="3">Cavalete</option>  			  
		        			    
							</select>
				          </td>
				          <td><strong>Diâmetro<span style="color:#FF0000">*</span></strong></td>
				          <td>
				          	<select name="idDiametroAgua" id="idDiametroAgua">
				          	<option value="-1">&nbsp;</option>
				          	</select>
						  </td>
						  <td><strong>Material<span style="color:#FF0000">*</span></strong></td>
				          <td>
				          	<select name="idMaterialAgua" id="idMaterialAgua">
				          	<option value="-1">&nbsp;</option>
				          	</select>
						  </td>
				          
            			</tr>
            			<tr>
            			  <td><strong>Profundidade:</strong></td>
				          <td><html:text property="profundidadeAgua" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
				          <td><strong>Pressão:</strong></td>
				          <td><html:text property="pressaoAgua" size="7" maxlength="8" onkeyup="javaScript:formataValorMonetarioQuatroDecimais(this, 8);"/></td>
				          <td><strong>Agente Externo<span style="color:#FF0000">*</span></strong></td>
				          <td> 
				          	<select name="idAgenteExternoAgua">
		        			    <option value="-1">&nbsp;</option>
		        			    <c:forEach items="${colecaoAgenteExterno}" var="agente" >
		        			    	<option 
		        			    	<c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idAgenteExternoAgua == agente.id}">selected</c:if>
		        			    	value="${agente.id}">${agente.descricao}</option>
		        			    </c:forEach>
							</select>
				          </td>
            			</tr>
            			<tr>
				          <td colspan="6"></td>
				        </tr>
				        <tr>
	            		 	<td colspan="6"><hr/></td>
	            		</tr>
	            		
					</c:if>
					
					<c:if test="${ordemServico.servicoTipo.indicadorRedeRamalEsgoto != 2}"> 
						
						<tr>
	            		 	<td colspan="6"><strong>Dados Rede/Ramal de Esgoto:</strong></td>
	            		</tr>
	            		
            			
	            		  <c:if test="${ordemServico.servicoTipo.indicadorCausaOcorrencia != 2}">
	            			<tr>
	            			  <td><strong>Causa<span style="color:#FF0000">*</span></strong></td>
					          <td> 
					          	<select name="idCausaEsgoto">
			        			    <option value="-1">&nbsp;</option>
			        			    <c:forEach items="${colecaoCausa}" var="causa" >
			        			    	<option 
			        			    	<c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idCausaEsgoto == causa.id}">selected</c:if>
			        			    	value="${causa.id}">${causa.descricao}</option>
			        			    </c:forEach>
								</select>
					          </td>
					         </tr>
	            		  </c:if>
	            		<tr>
            			  <td><strong>Rede/Ramal<span style="color:#FF0000">*</span></strong></td>
				          <td> 
				          	<select name="idRedeRamalEsgoto" onchange="consultarCombosEsgoto(this);">
		        			    <option value="-1">&nbsp;</option>
		        			    <option 
		        			    <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idRedeRamalEsgoto == 1}">selected</c:if>
		        			    value="1">Rede</option>
		        			    <option 
		        			    <c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idRedeRamalEsgoto == 2}">selected</c:if>
		        			    value="2">Ramal</option>
							</select>
				          </td>
				          <td><strong>Diâmetro<span style="color:#FF0000">*</span></strong></td>
				          <td>
				          	<select name="idDiametroEsgoto" id="idDiametroEsgoto">
				          	<option value="-1">&nbsp;</option>
				          	</select>
						  </td>
						  <td><strong>Material<span style="color:#FF0000">*</span></strong></td>
				          <td>
				          	<select name="idMaterialEsgoto" id="idMaterialEsgoto">
				          	<option value="-1">&nbsp;</option>
				          	</select>
						  </td>
				          
            			</tr>
            			<tr>
            			  <td><strong>Profundidade:</strong></td>
				          <td><html:text property="profundidadeEsgoto" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
				          <td><strong>Pressão:</strong></td>
				          <td><html:text property="pressaoEsgoto" size="7" maxlength="8" onkeyup="javaScript:formataValorMonetarioQuatroDecimais(this, 8);"/></td>
				          <td><strong>Agente Externo<span style="color:#FF0000">*</span></strong></td>
				          <td> 
				          	<select name="idAgenteExternoEsgoto">
		        			    <option value="-1">&nbsp;</option>
		        			    <c:forEach items="${colecaoAgenteExterno}" var="agente" >
		        			    	<option 
		        			    	<c:if test="${ManterDadosAtividadesOrdemServicoActionForm.idAgenteExternoEsgoto == agente.id}">selected</c:if>
		        			    	value="${agente.id}">${agente.descricao}</option>
		        			    </c:forEach>
							</select>
				          </td>
            			</tr>
            			<tr>
				          <td colspan="6"></td>
				        </tr>
				        <tr>
	            		 	<td colspan="6"><hr/></td>
	            		</tr>
					</c:if>
					
					<c:if test="${ordemServico.servicoTipo.indicadorVala != 2}"> 
						<tr>
	            		 	<td colspan="6"></td>
	            		</tr>
						<tr>
	            		 	<td colspan="6"><strong>Dados da Vala:</strong></td>
	            		</tr>
	            		<tr>
				          <td><strong>Número Vala:<span style="color:#FF0000">*</span></strong></td>
				          <td><html:text property="numeroVala" size="7" maxlength="4"/></td>
				          <td><strong>Local Ocorrência:<span style="color:#FF0000">*</span></strong></td>
				          <td>
				          	<select name="idLocalOcorrencia" onchange="consultarPavimentos(this);">
		        			    <option value="-1">&nbsp;</option>
		        			    <c:forEach items="${colecaoLocalOcorrencia}" var="local" >
		        			    	<option value="${local.id}">${local.descricao}</option>
		        			    </c:forEach>
							</select>
						  </td>
						  <td><strong>Pavimento:<span style="color:#FF0000">*</span></strong></td>
						  <td>
				          	<select name="idPavimento" id="idPavimento">
				          	<option value="-1">&nbsp;</option>
				          	</select>
						  </td>
				        </tr>
				        <tr>
				        	<td><strong>Comprimento:<span style="color:#FF0000">*</span></strong></td>
				          	<td><html:text property="comprimentoVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
				          	<td><strong>Largura:<span style="color:#FF0000">*</span></strong></td>
				          	<td><html:text property="larguraVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
				          	<td><strong>Profundidade:<span style="color:#FF0000">*</span></strong></td>
				          	<td><html:text property="profundidadeVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
				        </tr>
				        <tr>
				        	<td colspan="2"><strong>Indicador aterro ?<span style="color:#FF0000">*</span></strong></td>
				        	<td align="left" width="20%">
				        		<label><html:radio property="indicadorValaAterrada" value="1" /> <strong>Sim</strong></label>
							</td>
							<td align="left">
								<label><html:radio property="indicadorValaAterrada"	value="2" /> <strong>Não</strong></label>
							</td>
				        </tr>
				        
				        <tr>
				        	<td colspan="2"><strong>Indicador entulho?<span style="color:#FF0000">*</span></strong></td>
				        	<td align="left" width="20%">
				        		<label><html:radio property="indicadorEntulho" value="1" /> <strong>Sim</strong></label>
							</td>
							<td align="left">
								<label><html:radio property="indicadorEntulho"	value="2" /> <strong>Não</strong></label>
							</td>
				        </tr>
				        
				        
				        <tr>
				          <td colspan="6" height="10"></td>
				        </tr>
				        <tr>
				        	<td colspan="5"><strong>Valas Informadas:</strong></td>
							<td align="right">
								<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="adicionarVala();">
							</td>
				        </tr>
				        <tr>
				        <td colspan="6">
				        	<c:set var="i" value="0" />
				        	<display:table class="dataTable" name="colecaoVala" sort="list" id="vala"  pagesize="15" excludedParams="" > 
				        		<display:column style="width:10%; text-align: center;" sortable="false" title="Remover">
					       			<a href="javascript:removerVala(${i})">
										<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
									</a>
						        </display:column>
						        <display:column property="numeroVala" sortable="false" title="Número"/>
						        <display:column property="localOcorrencia.descricao" sortable="false" title="Loc. Ocorrência"/>
						        <display:column sortable="false" title="Pavimento">
						        	<c:choose>
						        		<c:when test="${vala.localOcorrencia.indicadorCalcada == 1}">
						        			<c:out value="${vala.pavimentoCalcada.descricao}" />
						        		</c:when>
						        		<c:otherwise>
						        			<c:out value="${vala.pavimentoRua.descricao}" />
						        		</c:otherwise>
						        	</c:choose>
						        </display:column>
						        <display:column property="numeroComprimento" sortable="false" title="Comp."/>
						        <display:column property="numeroLargura" sortable="false" title="Larg."/>
						        <display:column property="numeroProfundidade" sortable="false" title="Prof."/>
						        <display:column sortable="false" title="Aterrada">
						        	<c:choose>
						        		<c:when test="${vala.indicadorAterro eq 1}">
						        			<c:out value="Sim"/>
						        		</c:when>
						        		<c:otherwise>
						        			<c:out value="Não"/>
						        		</c:otherwise>
						        	</c:choose>
						        </display:column>
						        	
						        
						        <display:column sortable="false" title="Entulho">
						        	<c:choose>
						        		<c:when test="${vala.indicadorEntulho eq 1}">
						        			<c:out value="Sim"/>
						        		</c:when>
						        		<c:otherwise>
						        			<c:out value="Não"/>
						        		</c:otherwise>
						        	</c:choose>
						        </display:column>   
						        
						         
						    	<c:set var="i" value="${i+1}" />
				        	</display:table>
				        </td>
				       </tr>
				       <tr>
				          <td colspan="6" height="10"></td>
				        </tr>
				        <tr>
				          <td colspan="6" height="10"></td>
				        </tr>

					</c:if>
        		<!--  FIM DOS DADOS - SOLICITAÇÃO 315 ADA -->
        		</table>
        	</td>
        	
        </tr>
        <tr>
          <td colspan="3" height="20" align="left"><input type="button" class="bottonRightCol" value="Fechar"
			style="width: 80px" onclick="window.close();"></td>
		  <td align="right"><input type="button" class="bottonRightCol" value="Inserir"
			style="width: 80px" onclick="this.form.submit()"></td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html:html>

