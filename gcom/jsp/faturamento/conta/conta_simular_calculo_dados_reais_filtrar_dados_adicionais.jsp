<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false"  formName="SimularCalculoContaDadosReaisDadosAdicionaisActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">
	
	function validarFormulario(){
		
		var form = document.forms[0];
		
		if(validateSimularCalculoContaDadosReaisDadosAdicionaisActionForm(form)){
			
			if (form.quantidadeContas.value == '0'){
				
				alert("Quantidades de Contas deve ser maior que 0.");
				return;
			}
			
			if (confirm("Confirma recálculo para " + form.quantidadeContas.value + " Contas?")) {
				
					form.action = "/gsan/simularCalculoContaDadosReaisAction.do";
					submeterFormPadrao(form);
			}
		}
	}
	
	function limpar(){

  		var form = document.forms[0];
  		
		form.idConsumoTarifa.value = '';
		form.idConsumoTarifa.selectedIndex = 0;
		form.idConsumoTarifaVigencia.value = '';
		form.idConsumoTarifaVigencia.selectedIndex = 0;
		obterQuantidadeContasValidasVigenciaSelecionada();
  	}
	
	function carregaConsumoTarifaVigencias() {
    	
		var form = document.forms[0];
    	var campo = form.idConsumoTarifa;
    	var count = 0;
    	var temSelecionado = 0;
    	var idConsumoTarifa;
    	var idConsumoTarifaVigencia = form.idConsumoTarifaVigencia;
    	
    	for(i = 1; i <= campo.length; i++){
			
    		if(campo[i - 1].selected){
				
				count ++;
				idConsumoTarifa = campo[i - 1].value;
				temSelecionado = 1;
			}
		}
    	
    	
    	if (count == 1 && idConsumoTarifa != "-1") {
    		
    		form.idConsumoTarifaVigencia.disabled = false;
    		
    		AjaxService.carregaConsumoTarifaVigencias(idConsumoTarifa, {callback: 
				function(list) {
	    			
    			//Função que remove caso exista os valores da combo.  
                DWRUtil.removeAllOptions(idConsumoTarifaVigencia);  
                
				//Adicionando valores na combo.  
                DWRUtil.addOptions(idConsumoTarifaVigencia, {'-1':' '});
				
    
                DWRUtil.addOptions(idConsumoTarifaVigencia, list);
                
     
    			}
    		});
    	} else {
    		
    		form.idConsumoTarifaVigencia.length = 0;
    		form.idConsumoTarifaVigencia.value = "-1";
    		form.idConsumoTarifaVigencia.disabled = true;
    	}
    	
    	obterQuantidadeContasValidasVigenciaSelecionada();
    }
	
	function controleConsumoTarifaVigencias() {
    	
		var form = document.forms[0];
    	var obj = form.idConsumoTarifa;
    	
    	if (obj.selectedIndex == 0) {
    		form.idConsumoTarifaVigencia.disabled = true;
    		form.idConsumoTarifaVigencia[0].selected = true;
    	}else {
    		if (form.idConsumoTarifa.selectedIndex == 0 || form.idConsumoTarifa.selectedIndex == -1) {
    			form.idConsumoTarifaVigencia.disabled = true;
    			form.idConsumoTarifaVigencia.value = "-1";
    		}else {
    			form.idConsumoTarifaVigencia.disabled = false;
    		}
    	}
    }
	
	
	function obterQuantidadeContasValidasVigenciaSelecionada(){

		var form = document.forms[0];
		var quantidadeContasSelecionada = "";
		var idConsumoTarifaVigencia = form.idConsumoTarifaVigencia.value;
		
		if (form.idConsumoTarifaVigencia.selectedIndex == 0 || form.idConsumoTarifaVigencia.value == '-1') {
			
			AjaxService.obterQuantidadeContasComVigenciaValida('-1', form.quantidadeContas.value, {callback: 
				function(quantidadeContasComVigenciaValida) {
	
				quantidadeContasSelecionada = quantidadeContasComVigenciaValida;	
				
				}, async: false} 
			);
	
			form.quantidadeContas.value = quantidadeContasSelecionada;
		}else{
			
			AjaxService.obterQuantidadeContasComVigenciaValida(idConsumoTarifaVigencia, form.quantidadeContas.value, {callback: 
				function(quantidadeContasComVigenciaValida) {
	
				quantidadeContasSelecionada = quantidadeContasComVigenciaValida;	
				
				}, async: false} 
			);
	
			form.quantidadeContas.value = quantidadeContasSelecionada;
		}
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:controleConsumoTarifaVigencias();">

<html:form action="/exibirSimularCalculoContaDadosReaisDadosAdicionaisAction.do"
	name="SimularCalculoContaDadosReaisDadosAdicionaisActionForm"
	type="gcom.gui.faturamento.conta.SimularCalculoContaDadosReaisDadosAdicionaisActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td width="149" valign="top" class="leftcoltext">
	      <div align="center">
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>

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
					<td class="parabg">Simular Cálculo das Contas com Dados Reais</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para simular cálculo das contas com dados reais, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Quantidades de Contas:</strong></td>
					<td width="70%"><html:text property="quantidadeContas" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="10" /></td>
				</tr>
				
				<tr>
					<td>
						<strong>Tarifa de Consumo para Recálculo:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="idConsumoTarifa" 
							style="width: 230px;"
							onchange="javascript:carregaConsumoTarifaVigencias();">
							
							<html:option
								value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoConsumoTarifa" scope="request">
								<html:options collection="colecaoConsumoTarifa"
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Vigências da Tarifa:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="idConsumoTarifaVigencia" 
							style="width: 230px;"
							onchange="javascript:obterQuantidadeContasValidasVigenciaSelecionada();">
							
							<html:option
								value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoConsumoTarifaVigencia" scope="request">
								<html:options collection="colecaoConsumoTarifaVigencia"
									labelProperty="dataVigenciaFormatada" 
									property="id"/>
							</logic:present>
						</html:select> 														
						</strong>
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
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>

				<tr>
					<td height="24" >
			          	
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"
			          		/>
			          	<input name="button" 
							type="button"
							class="bottonRightCol" 
							value="Voltar Filtro"
							onclick="window.location.href='<html:rewrite page="/exibirSimularCalculoContaDadosReaisFiltrarAction.do"/>'"
							align="left" 
							style="width: 80px;">
			          		
					</td>
					
					<td align="right">
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
						<gsan:controleAcessoBotao name="Button" 
							value="Filtrar"
							onclick="javascript:validarFormulario();"
							url="simularCalculoContaDadosReaisAction.do" />
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
</html:html>