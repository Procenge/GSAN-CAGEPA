<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirRotaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
	
	function validarForm(form){

			if (form.faturamentoGrupo.value == "-1") {
				alert('Informe o Grupo de Faturamento');
				return;
			}

			if (form.leituraTipo.value == "-1") {
				alert('Informe o Tipo de Leitura');
				return;
			}
			
			if (form.empresaLeituristica.value == "-1") {
				alert('Informe o Empresa');
				return;
			}
		
			if (!validarIndicadorGerarFalsaFaixa()) {
				return;
			}
			if (!validarIndicadorGerarFiscalizacao()) {
				return;
			}
			
			if(testarCampoDecimalValorZero(document.InserirRotaActionForm.percentualGeracaoFaixaFalsa, 'Percentual de Faixa Falsa')
			&& testarCampoDecimalValorZero(document.InserirRotaActionForm.percentualGeracaoFiscalizacao, 'Percentual de Fiscalizacao de Leitura'))
			{
				submeterFormPadrao(form);
			}
	}
	function limparLocalidade(form) {
    	form.idLocalidade.value = "";
    	form.localidadeDescricao.value = "";
	}
	
	function limparSetorComercial(form) {
    	form.codigoSetorComercial.value = "";
    	form.setorComercialDescricao.value = "";
	}

	function limparDescLocalidade(form) {
    	form.localidadeDescricao.value = "";
    	form.codigoSetorComercial.value = "";
	}
	
	function limparDescSetorComercial(form) {
    	form.setorComercialDescricao.value = "";
	}
	
	function limparLeiturista(form) {
    	form.codigoLeiturista.value = "";
    	form.nomeLeiturista.value = "";
	}

	function habilitacaoPercentualGeracaoFaixaFalsa(indicadorGerarFalsaFaixa){

		var form = document.forms[0];
			if (indicadorGerarFalsaFaixa[1].checked){
				form.percentualGeracaoFaixaFalsa.disabled = true;
				form.percentualGeracaoFaixaFalsa.value = ""; 
			}else{
				form.percentualGeracaoFaixaFalsa.disabled = false;
			}

	   }
	   
	   function habilitacaoPercentualGeracaoFiscalizacao(indicadorGerarFiscalizacao){

		var form = document.forms[0];
			if (indicadorGerarFiscalizacao[1].checked){
				form.percentualGeracaoFiscalizacao.disabled = true;
				form.percentualGeracaoFiscalizacao.value = ""; 
			}else{
				form.percentualGeracaoFiscalizacao.disabled = false;
			}

	   }
	
 	
   function validarIndicadorGerarFalsaFaixa(){

	    var form = document.forms[0];
	    
	    var indice;
	    var array = new Array(form.indicadorGerarFalsaFaixa.length);
	    var selecionado = "";
	    var formulario = document.forms[0]; 
	    for(indice = 0; indice < form.elements.length; indice++){
		   if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && formulario.elements[indice].name == "indicadorGerarFalsaFaixa") {
		      selecionado = form.elements[indice].value;
		      indice = form.elements.length;
		   }
	    }    
		if(selecionado == ''){
			alert('Informe Gera Faixa Falsa.');
			return false;
		}	
		return true;
	}		   

   function validarIndicadorGerarFiscalizacao(){

	    var form = document.forms[0];
	    
	    var indice;
	    var array = new Array(form.indicadorGerarFiscalizacao.length);
	    var selecionado = "";
	    var formulario = document.forms[0]; 
	    for(indice = 0; indice < form.elements.length; indice++){
		   if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && formulario.elements[indice].name == "indicadorGerarFiscalizacao") {
		      selecionado = form.elements[indice].value;
		      indice = form.elements.length;
		   }
	    }    
		if(selecionado == ''){
			alert('Informe Gera Fiscalização de Leitura.');
			return false;
		}
		return true;	
	}	

	function fecharJanela(obj) {
		if (obj == '1') {
			window.close();
		}
	}

</script>
</head>
<body leftmargin="5" topmargin="5"
onload="fecharJanela('${requestScope.fecharJanela}');setarFoco('${requestScope.nomeCampo}');">
<html:form action="/exibirAtualizarRotaSetorComercialAction.do?atualizar=1" name="InserirRotaActionForm"
	type="gcom.gui.micromedicao.InserirRotaActionForm" method="post">

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>

			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Rotas</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>


			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para atualizar a rota, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
</table>
<table width="100%" border="0">
				<tr>
					<td><strong>Grupo de Faturamento:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="faturamentoGrupo" tabindex="4">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="collectionFaturamentoGrupo"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>

				<tr>
					<td><strong>Tipo de Leitura:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="leituraTipo" tabindex="6">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="collectionLeituraTipo"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>


				<tr>
					<td><strong>Empresa de Leitura:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="empresaLeituristica" tabindex="7">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="collectionEmpresa"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				<tr>
					<td><strong>Gera Faixa Falsa:<font color="#FF0000">*</font></strong></td>
					<td><strong> <span class="style1"> 
						<html:radio property="indicadorGerarFalsaFaixa" value="1" onclick= "habilitacaoPercentualGeracaoFaixaFalsa(document.forms[0].indicadorGerarFalsaFaixa)" tabindex="13"/> Sim 
						<html:radio property="indicadorGerarFalsaFaixa" value="2" onclick= "habilitacaoPercentualGeracaoFaixaFalsa(document.forms[0].indicadorGerarFalsaFaixa)"  /> Não													
					</span></strong></td>
				</tr>
				
				
				<tr> 
                	<td><strong> Percentual de Faixa Falsa:</strong></td>
                	<td>                	
                	<html:text property="percentualGeracaoFaixaFalsa" size="6" maxlength="6" 
                		tabindex="14" 
                		onkeyup="formataValorMonetario(this, 6)"
                		style="text-align: right;" />
                  	</td>
              	</tr>				
								
				
				<tr>
					<td><strong>Gera Fiscaliza&ccedil;&atilde;o de Leitura:<font color="#FF0000">*</font></strong></td>
					<td><strong> <span class="style1"> 
						<html:radio property="indicadorGerarFiscalizacao" value="1" onclick= "habilitacaoPercentualGeracaoFiscalizacao(document.forms[0].indicadorGerarFiscalizacao)" tabindex="15"/> Sim 
						<html:radio property="indicadorGerarFiscalizacao" value="2" onclick= "habilitacaoPercentualGeracaoFiscalizacao(document.forms[0].indicadorGerarFiscalizacao)" /> Não													
					</span></strong></td>
				</tr>				
				
				
				<tr> 
                	<td><strong> Percentual de Fiscaliza&ccedil;&atilde;o de Leitura:</strong></td>
                	<td>
                	<html:text property="percentualGeracaoFiscalizacao" size="6" maxlength="6" 
                		tabindex="16"
                		onkeyup="formataValorMonetario(this, 6)"
                		style="text-align: right;" />

                  	</td>
              	</tr>
				
				
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td>
						<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onclick="window.close();">
					</td>
					<td align="right">
						<input name="Button1" type="button" value="Atualizar" class="bottonRightCol"
							 	onclick="javascript:validarForm(document.InserirRotaActionForm);"/>
					</td>
				</tr>



			</table>
			<p>&nbsp;</p>
			</td>

		</tr>
	</table>
	
</html:form>

	<script language="JavaScript">
	<!--
	habilitacaoPercentualGeracaoFaixaFalsa(document.forms[0].indicadorGerarFalsaFaixa);
	habilitacaoPercentualGeracaoFiscalizacao(document.forms[0].indicadorGerarFiscalizacao);
	//-->
	</script>
</body>
</html:html>
