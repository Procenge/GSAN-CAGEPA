<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="InserirSetorAbastecimentoActionForm" dynamicJavascript="true" />

<SCRIPT LANGUAGE="JavaScript">

	function validarForm(form) {
     	if (validateInserirSetorAbastecimentoActionForm(form)){
			submeterFormPadrao(form);
		}
	}
  	
  	function limparForm() {
		var form = document.forms[0];
		form.codigo.value = "";
		form.descricao.value = "";
		form.descricaoAbreviada.value = "";
		form.unidadeOperacional.value = "-1";
		form.sistemaAbastecimento.value = "-1";
		form.zonaAbastecimento.value = "-1";
		form.extensaoRede.value="";
		form.pressaoMaxima.value="";
		form.pressaoMedia.value="";
		form.pressaoMinima.value="";
		form.monitoramentoFinal.value="";
		form.monitoramentoInicial.value="";
	}	

  	
 	/* Replica Data de Pagamento */
	function replicaData() {
		var form = document.forms[0];
		form.monitoramentoInicial.value = form.monitoramentoFinal.value
	}
  	
	function maskIt(w,e,m,r,a){
		// Cancela se o evento for Backspace
		if (!e) var e = window.event
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		// Variáveis da função
		var txt  = (!r) ? w.value.replace(/[^\d]+/gi,'') : w.value.replace(/[^\d]+/gi,'').reverse();
		var mask = (!r) ? m : m.reverse();
		var pre  = (a ) ? a.pre : "";
		var pos  = (a ) ? a.pos : "";
		var ret  = "";
		if(code == 9 || code == 8 || txt.length == mask.replace(/[^#]+/g,'').length) return false;
		// Loop na máscara para aplicar os caracteres
		for(var x=0,y=0, z=mask.length;x<z && y<txt.length;){
		if(mask.charAt(x)!='#'){
		ret += mask.charAt(x); x++; } 
		else {
		ret += txt.charAt(y); y++; x++; } }
		// Retorno da função
		ret = (!r) ? ret : ret.reverse()	
		w.value = pre+ret+pos; }
		// Novo método para o objeto 'String'
		String.prototype.reverse = function(){
		return this.split('').reverse().join(''); };

  	
	function number_format( number, decimals, dec_point, thousands_sep ) {
		var n = number, c = isNaN(decimals = Math.abs(decimals)) ? 2 : decimals;
		var d = dec_point == undefined ? "," : dec_point;
		var t = thousands_sep == undefined ? "." : thousands_sep, s = n < 0 ? "-" : "";
		var i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
		}

		function calcula(operacion){ 
		var operando1 = parseFloat( document.calc.operando1.value.replace(/\./g, "").replace(",", ".") );
		var operando2 = parseFloat( document.calc.operando2.value.replace(/\./g, "").replace(",", ".") );
		var result = eval(operando1 + operacion + operando2);
		document.calc.resultado.value = number_format(result,2, ',', '.');
		} 

	
	
	
</SCRIPT>



</head>

<body leftmargin="5" topmargin="5">

<html:form action="/inserirSetorAbastecimentoAction" method="post">


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
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
					<td class="parabg">Inserir Setor de Abastecimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o setor de abastecimento, informe os dados
					abaixo:</td>				
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
    		
    			<tr>
					<td><strong>Código do Setor de Abastecimento:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="4"
							property="codigo" 
							size="4" 
							tabindex="1" 
							onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'Código');"/></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Descrição do Setor de Abastecimento:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="30"
							property="descricao" 
							size="52" 
							tabindex="2" /></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Descrição Abreviada:</strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="6"
							property="descricaoAbreviada" 
							size="6" 
							tabindex="3" /></div>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Sistema de Abastecimento: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="sistemaAbastecimento" tabindex="4" style="width:200px;" onchange="javascript:pesquisaColecaoReload('exibirInserirSetorAbastecimentoAction.do?consultaSistema=1','sistemaAbastecimento');">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoSistemaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Distrito Operacional: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="distritoOperacional" tabindex="4" style="width:200px;" onchange="javascript:pesquisaColecaoReload('exibirInserirSetorAbastecimentoAction.do?consultaSistema=1','distritoOperacional');">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDistritoOperacional" property="id" labelProperty="descricaoComId"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Zona de Abastecimento: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="zonaAbastecimento" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoZonaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
					</td>
				</tr>
			
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Extensão de Rede: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:text maxlength="6"
							property="extensaoRede" 
							size="6" 
							tabindex="3" />(m)
					</td>
				</tr>
				
			<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Pressão Maxima: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:text maxlength="6"
							property="pressaoMaxima" 
							size="6" 
							tabindex="3" onkeyup="maskIt(this,event,'######,#',true)"/>
					</td>
				</tr>
			<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Pressão Média: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:text maxlength="6"
							property="pressaoMedia" 
							size="6" 
							tabindex="3" onkeyup="maskIt(this,event,'######,#',true)"/>
					</td>
				</tr>
			<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Pressão Minima: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:text maxlength="6"
							property="pressaoMinima" 
							size="6" 
							tabindex="3" onkeyup="maskIt(this,event,'######,#',true)"/>
					</td>
				</tr>
			
			
			<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Periodo de monitoramento: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  		
							<html:text maxlength="10" property="monitoramentoInicial" size="10" 
							onkeypress="javascript:return isCampoNumerico(event); " 
							onkeyup="mascaraData(this, event);replicaPeriodo();" />
                   			<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" 
                   			onclick="abrirCalendarioReplicando('InserirSetorAbastecimentoActionForm', 'monitoramentoInicial','monitoramentoFinal')" 
                   			width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> à
							
							<html:text maxlength="10" property="monitoramentoFinal" size="10" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="mascaraData(this, event)" />
                      		<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" 
                      		onclick="abrirCalendario('InserirSetorAbastecimentoActionForm', 'monitoramentoFinal')" 
                      		width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> 
						



					</strong>(dd/mm/aaaa)</strong>
					</td>
				</tr>
			<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Diâmetro: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="idDiametro" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDiamentroRedeAgua" property="id" labelProperty="descricao"/>
						</html:select>
					</td>
				</tr>
			
			
				<tr>
					<td>&nbsp;</td>
					<td align="right">
						<div align="left">
							<strong><font color="#FF0000">*</font></strong>
							Campos obrigat&oacute;rios</div>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<input name="Button" 
						type="button"
						class="bottonRightCol" 
						value="Limpar" 
						align="left"
						tabindex="4"
						onclick="javascript:limparForm();">&nbsp; 
					&nbsp;
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								tabindex="5"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td align="right">
						<gsan:controleAcessoBotao name="Button" 
							value="Inserir"
							tabindex="6"
							onclick="javascript:validarForm(document.forms[0]);"
							url="inserirSetorAbastecimentoAction.do" />
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
