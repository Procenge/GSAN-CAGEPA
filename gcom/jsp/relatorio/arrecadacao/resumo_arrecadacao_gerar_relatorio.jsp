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
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript staticJavascript="false"  formName="GerarRelatorioResumoArrecadacaoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script>

	function validarForm(){
		
		var form = document.forms[0];
		if(validateGerarRelatorioResumoArrecadacaoActionForm(form)
				&& validarOpcoesTotalizacao()){
			
			toggleBox('demodiv',1);
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
	    var form = document.GerarRelatorioResumoArrecadacaoActionForm;
	
	    if (tipoConsulta == 'localidade') {
	      form.codigoLocalidade.value = codigoRegistro;
	      form.descricaoLocalidade.value = descricaoRegistro;
	      form.descricaoLocalidade.style.color = "#000000";
	    } else if (tipoConsulta == 'setorComercial') {
	      form.codigoSetorComercial.value = codigoRegistro;
	      form.descricaoSetorComercial.value = descricaoRegistro;
	      form.descricaoSetorComercial.style.color = "#000000";        
	    }
	  }
	
	
	function limparLocalidade() {
	    var form = document.GerarRelatorioResumoArrecadacaoActionForm;
	    
	      form.codigoLocalidade.value = '';
	      form.descricaoLocalidade.value = '';
	
	      limparSetorComercial();
	}
	
	function limparSetorComercial() {
		var form = document.GerarRelatorioResumoArrecadacaoActionForm;
	
		form.codigoSetorComercial.value = '';
		form.descricaoSetorComercial.value = '';
	}
	
	function validaMesAno(form){
	  var form = document.GerarRelatorioResumoArrecadacaoActionForm;
	  var mesAno = form.mesAno.value;
	  if(mesAno.length < 7 || mesAno.substring(2,3) != "/" ||
	     mesAno.substring(0,2) < "01" || mesAno.substring(0,2) > "12"){
	     alert("M�s/Ano inv�lido.");
	     return false;
	  }
	  return true;
	}
	
	function validarOpcoesTotalizacao(){
	
		var form = document.forms[0];
		var retorno = true;
		var opcao = '';
		
		for(i=0; i < form.opcaoTotalizacao.length; i++) {
		
			if (form.opcaoTotalizacao[i].checked) {
		
				opcao = form.opcaoTotalizacao[i].value;
			}
		}
		 
 		if (opcao == 'localidade') {

 			if (form.codigoLocalidade.value == '') {

 				alert('Informe Localidade.');
 				retorno = false;
 			}
 		} else if (opcao == 'gerenciaRegional') {

 			if (form.gerenciaRegionalId.value == null
 	 			 || form.gerenciaRegionalId.value == '-1') {

 				alert('Informe Ger�ncia Regional.');
 				retorno = false;
 			}
 		} else if (opcao == 'gerenciaRegionalLocalidade') {

			if (form.gerenciaRegionalporLocalidadeId.value == null
	 			 || form.gerenciaRegionalporLocalidadeId.value == '-1') {

				alert('Informe Ger�ncia Regional.');
				retorno = false;
			}
 		} else if (opcao == 'unidadeNegocio') {

			if (form.unidadeNegocioId.value == null
	 			 || form.unidadeNegocioId.value == '-1') {

				alert('Informe Unidade de Neg�cio.');
				retorno = false;
			}
 		} else if (opcao == '') {

 			alert('Informe Op��o de Totaliza��o.');
 			retorno = false;
 		}
	
		return retorno;	
	}
	
	function limparCampos(opcaoTotalizacao){
		var form = document.forms[0];
		
		if (opcaoTotalizacao.value != 'localidade'){
			limparLocalidade() ;
			limparSetorComercial();
		}
		
		if (opcaoTotalizacao.value != 'gerenciaRegional'){
			form.gerenciaRegionalId.value = "-1";
		}
		
		if (opcaoTotalizacao.value != 'gerenciaRegionalLocalidade'){
			form.gerenciaRegionalporLocalidadeId.value = "-1" ;
		}
		
		if (opcaoTotalizacao.value != 'unidadeNegocio'){
			form.unidadeNegocioId.value = "-1" ;
		}
	}

</script>


</head>

<body leftmargin="5" topmargin="5">
<html:form action="/gerarRelatorioResumoArrecadacaoAction.do"
	name="GerarRelatorioResumoArrecadacaoActionForm"
	type="gcom.gui.relatorio.arrecadacao.GerarRelatorioResumoArrecadacaoActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			
				<div align="center">
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
				<%@ include file="/jsp/util/mensagens.jsp"%>
				</div>

			</td>
			
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Relat�rio Resumo da Arrecada��o</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para gerar o relat�rio resumo da arrecada��o, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td width="26%"><strong>M�s/Ano da Arrecada��o:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text property="mesAno" size="7" maxlength="7"
					onkeyup="mascaraAnoMes(this, event);"
					 />
					<strong>&nbsp; </strong>mm/aaaa</td>
				</tr>
				<tr>
					<td><strong>Op&ccedil;&atilde;o de Totaliza&ccedil;&atilde;o:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:radio
						property="opcaoTotalizacao" value="estado" 
						onclick = "limparCampos(this);"/> <strong>Estado </strong></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td colspan="2" align="left"><strong> <html:radio
						property="opcaoTotalizacao" value="estadoGerencia" 
						onclick = "limparCampos(this);"/> Estado por
					Ger&ecirc;ncia Regional </strong></td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td colspan="2" align="left"><strong> 
						<html:radio property="opcaoTotalizacao" value="estadoUnidadeNegocio" 
						onclick = "limparCampos(this);"/>
					<strong>Estado por Unidade de Neg�cio</strong></strong></td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td colspan="2" align="left"><strong> <html:radio
						property="opcaoTotalizacao" value="estadoLocalidade" 
						onclick = "limparCampos(this);"/> <strong>Estado</strong>
					por Localidade</strong></td>
				</tr>
				
				
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td width="36%" align="left"><strong> 
					<html:radio property="opcaoTotalizacao" value="gerenciaRegional" 
					onclick = "limparCampos(this);"/>
					<strong>Ger&ecirc;ncia Regional </strong></strong></td>
					<td width="38%" align="left"><strong><strong> <html:select
						property="gerenciaRegionalId">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoGerenciaRegional"
							labelProperty="nome" property="id" />
					</html:select> </strong></strong></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="left"><strong> <html:radio property="opcaoTotalizacao"
						value="gerenciaRegionalLocalidade" 
						onclick = "limparCampos(this);"/> <strong>Ger&ecirc;ncia
					Regional</strong> por Localidade</strong></td>
					<td align="left"><strong><strong> <strong> <html:select
						property="gerenciaRegionalporLocalidadeId">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoGerenciaRegional"
							labelProperty="nome" property="id" />
					</html:select> </strong> </strong></strong></td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td width="36%" align="left"><strong> 
						<html:radio property="opcaoTotalizacao" value="unidadeNegocio" 
						onclick = "limparCampos(this);"/>
					<strong>Unidade de Neg�cio </strong></strong></td>
					<td width="38%" align="left"><strong><strong> 
						<html:select property="unidadeNegocioId">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoUnidadeNegocio" labelProperty="nome" property="id" />
					</html:select> </strong></strong></td>
				 </tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="left"><strong> <html:radio property="opcaoTotalizacao"
						value="localidade"
						onclick = "limparCampos(this);" /> Localidade</strong></td>
					<td align="left"><html:text
						value="${requestScope.codigoLocalidade}"
						property="codigoLocalidade" size="3" maxlength="3"
						onkeypress="validaEnter(event, 'exibirGerarRelatorioResumoArrecadacaoAction.do', 'codigoLocalidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
					<a
						href="javascript:limparLocalidade();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a>
					</td>
				</tr>

				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="left"><strong> </strong></td>
					<td align="left"><strong> 
					
						<input type="text" name="descricaoLocalidade" readonly
							style="background-color:#EFEFEF; border:0" size="30"
							maxlength="30" value="${requestScope.descricaoLocalidade}" />
							
					<c:if test="${empty requestScope.codigoLocalidade}" var="testeCor">
						<script>
							document.GerarRelatorioResumoArrecadacaoActionForm.descricaoLocalidade.style.color = '#FF0000';
						</script>
					</c:if>
					
					<c:if test="${!testeCor}">
						<script>
							document.GerarRelatorioResumoArrecadacaoActionForm.descricaoLocalidade.style.color = '#000000';
						</script>
					</c:if>
							

					 </strong></td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="left"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Setor Comercial</strong></td>
					<td align="left"><html:text
						value="${requestScope.codigoSetorComercial}"
						property="codigoSetorComercial" size="3" maxlength="3"
						onkeypress="validaEnterDependencia(event,'exibirGerarRelatorioResumoArrecadacaoAction.do', this, document.forms[0].codigoLocalidade.value, 'Localidade');"/>
					<a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].codigoLocalidade.value+'&tipo=SetorComercial',document.forms[0].codigoLocalidade.value,'Localidade', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> 
					<a
						href="javascript:limparSetorComercial();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a>
					</td>
				</tr>			

				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="left"><strong> </strong></td>
					<td align="left">
						<strong> 
							<input type="text" name="descricaoSetorComercial" readonly
							style="background-color:#EFEFEF; border:0" size="30"
							maxlength="30" value="${requestScope.descricaoSetorComercial}" />

							<c:if test="${empty requestScope.codigoSetorComercial}" var="testeCor">
								<script>
									document.GerarRelatorioResumoArrecadacaoActionForm.descricaoSetorComercial.style.color = '#FF0000';
								</script>
							</c:if>

							<c:if test="${!testeCor}">
								<script>
									document.GerarRelatorioResumoArrecadacaoActionForm.descricaoSetorComercial.style.color = '#000000';
								</script>
							</c:if>
						</strong>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td colspan="2" align="left"><font color="#FF0000">*</font> Campo
					Obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
					<td colspan="2"></td>
				</tr>
			</table>
			<table>
				<tr>
					<td width="100%">
					<table width="30%" border="0" align="right" cellpadding="0"
						cellspacing="2">
						<tr>
							<td width="61">&nbsp;</td>
							<td width="416">&nbsp;</td>
							<td width="12"></td>
							<td width="61">		
							
								<input type="button" class="bottonRightCol"
									value="Gerar Relat&oacute;rio"
									onclick="javascript:validarForm();">				
								
							</td>
							<td width="82">&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioResumoArrecadacaoAction.do"/> 
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
