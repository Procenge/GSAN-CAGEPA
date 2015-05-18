<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.Util"%>

<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="LeituraConsumoActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<style>
.styleFontePequena{font-size:9px;
                   color: #000000;
				   font:Verdana, Arial, Helvetica, sans-serif}
.styleFontePeqNegrito{font-size:11px;
                   color: #000000;
				   font-weight: bold}
.styleFonteTabelaPrincipal{font-size:12px;
                   color: #FFFFFF;
				   background-color: #5782E6;
				   font-weight: bold}
.styleFonteMenorNegrito{font-size:10px;
                   color: #000000;
				   font-weight: bold}
.buttonAbaRodaPe {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	width:100px;
	background-color: #55A8FB;
	border-top: 1px outset #FFFFFF;
	border-right: 1px outset #000099;
	border-bottom: 1px outset #000099;
	border-left: 1px outset #FFFFFF;
	text-transform: none;
</style>
<script language="JavaScript">
function limparPesquisaAnormalidade(){
	var form = document.forms[0];
	
	form.idAnormalidade.value = "";
	form.descricaoAnormalidade.value = "";
}
</script>
<script language="JavaScript"><!--
var bCancel = false;

    function validateLeituraConsumoActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateCaracterEspecial(form) && validateLong(form);
   }
--></script>   
    
    <logic:notPresent name="naoValidarLeitura" scope="session">    

		<script language="JavaScript"><!--
		    function caracteresespeciais () {
			     this.aa = new Array("dataLeituraAnteriorFaturamento", "Data Anterior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ab = new Array("leituraAnteriorFaturamento", "Leitura Anterior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ac = new Array("dataLeituraAtualInformada", "Data Atual possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ad = new Array("leituraAtualInformada", "Leitura Atual possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ae = new Array("consumoInformado", "Consumo Inf. possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.af = new Array("codigoImovel", "Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ag = new Array("idAnormalidade", "Anorm. possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     
		    }
		
	    	function IntegerValidations () {
	        	this.aa = new Array("leituraAnteriorFaturamento", "Leitura Anterior deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.ab = new Array("leituraAtualInformada", "Leitura Atual deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.aa = new Array("consumoInformado", "Consumo Inf deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.ab = new Array("codigoImovel", "Imóvel deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.ac = new Array("idAnormalidade", "Anorm. deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	       }	
	    	
	    	function submeterForm(){
	    		var form = document.forms[0];
	    		
	    		if(validateLeituraConsumoActionForm(form)){
	    			
	    			if(!verificaDataMensagemSemApagar(form.dataLeituraAnteriorFaturamento,'Data Anterior Inválida')){
	    				
	    			}else if(!verificaDataMensagemSemApagar(form.dataLeituraAtualInformada, 'Data Atual Inválida')){
	    				
	    			}else
	    			if (form.dataLeituraAtualInformada.value == '' && form.leituraAtualInformada.value !=''){
	    				alert('Informe Data Atual');
	    			}else{
	    				if(form.dataLeituraAnteriorFaturamento.value != '' && form.leituraAnteriorFaturamento.value ==''){
	    					alert('Informe Leitura Anterior');
	    				}else
	    				if (form.dataLeituraAnteriorFaturamento.value == '' && form.leituraAnteriorFaturamento.value !=''){
	    					alert('Informe Data Anterior');
	    				}else{
	    					submeterFormPadrao(document.forms[0]);
	    				}
	    			}
	    		}
	    	}
	    	
		--></script>   

	</logic:notPresent>
	
   <logic:present name="naoValidarLeitura" scope="session">    

		<script language="JavaScript"><!--
		    function caracteresespeciais () {
			     this.ae = new Array("consumoInformado", "Consumo Inf. possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.af = new Array("codigoImovel", "Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     this.ag = new Array("idAnormalidade", "Anorm. possui caracteres especiais.", new Function ("varName", " return this[varName];"));
			     
		    }
		
	    	function IntegerValidations () {
	        	this.aa = new Array("consumoInformado", "Consumo Inf deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.ab = new Array("codigoImovel", "Imóvel deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	        	this.ac = new Array("idAnormalidade", "Anorm. deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	       }
	    	
	    	function submeterForm(){
	    		var form = document.forms[0];
	    		
	    		if(validateLeituraConsumoActionForm(form)){
	    			submeterFormPadrao(document.forms[0]);
	    		}
	    	}	    	
		--></script>   

	</logic:present>	


  <script language="JavaScript"><!--  
   // function InteiroZeroPositivoValidations () { 
   //  this.aa = new Array("leituraAnteriorFaturamento", "Leitura Anterior deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
   //  this.ab = new Array("leituraAtualInformada", "Leitura Atual deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
	//}
    
    
    function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
	if(!campo.disabled){
	  if (objeto == null || codigoObjeto == null){
	     if(tipo == "" ){
	      abrirPopup(url,altura, largura);
	     }else{
		  abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		 }
	 }else{
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			alert(msg);
		}
		else{
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaAnormalidadeLeitura=" + tipo, altura, largura);
		}
	}
  }
}
--></script>
<script language="JavaScript">




function imovelAnterior(){
 var form = document.forms[0];
 form.action = "exibirDadosAnaliseMedicaoConsumoResumoAction.do?imovelAnterior=1";
 submeterFormPadrao(form);
}
function proximoImovel(){
 var form = document.forms[0];
 form.action = "exibirDadosAnaliseMedicaoConsumoResumoAction.do?proximoImovel=1";
 submeterFormPadrao(form);
}

function pesquisaImovelNaColecao(imovel, tipoMedicao){
	var form = document.forms[0];
	if(validaCampoInteiro(form.codigoImovel)){
		form.action = "exibirDadosAnaliseMedicaoConsumoResumoAction.do?idRegistroAtualizacao="+imovel+"&medicaoTipo="+tipoMedicao+"&consultaImovelLista=Sim";
		submeterFormPadrao(form);
	}else{
		alert("Imóvel deve somente conter números positivos.");
	}
}

//Recebe os dados do(s) popup(s)
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

      var form = document.LeituraConsumoActionForm;

       if (tipoConsulta == 'leituraAnormalidade') {
        form.idAnormalidade.value = codigoRegistro;
        form.descricaoAnormalidade.value = descricaoRegistro;
        form.descricaoAnormalidade.style.color = "#000000";
      }

    }
    
    function validaDataCompleta(data, event){
		if(mascaraData(data, event)){
			return false;
		}
}

  
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/atualizarConsumoResumoAction.do"
	name="LeituraConsumoActionForm"
	type="gcom.gui.micromedicao.LeituraConsumoActionForm" method="post">

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


			<td valign="top" class="centercoltext">

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
					<td class="parabg">Resumo da An&aacute;lise da
					Medi&ccedil;&atilde;o e Consumo do M&ecirc;s</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0">
				<tr>
					<table width="100%" border="0">
						<tr>
							<td colspan="6">
							<table cellspacing="0" cellpadding="0" border="0" width="100%">
								<tr>
									<logic:present name="sucesso">
										<tr>
											<td colspan="7"><img height="20" width="21"
												src="<bean:message key="caminho.imagens"/>sucesso.gif"><strong>Leituras
											e Consumo atualizados com sucesso</strong></td>
										</tr>
									</logic:present>
								<tr>
									<td align="right">
									<table width="100%" bgcolor="#99CCFF">
										<tr class="styleFonteTabelaPrincipal">
											<td colspan="7" class="styleFonteTabelaPrincipal"
												align="center" bgcolor="#79bbfd">Dados do Imóvel</td>
										</tr>
										<!--header da tabela interna -->
										<tr bgcolor="#99CCFF" class="styleFontePeqNegrito">
											<td width="24%" align="center">Inscri&ccedil;&atilde;o</td>
											<td width="13%" align="center">Matr&iacute;cula Im&oacute;vel</td>
											<td width="13%" align="center">Im&oacute;vel Condom&iacute;no</td>
											<td width="14%" align="center">Hidr&ocirc;metro</td>
											<td width="13%" align="center">Hidr&ocirc;metro
											Instala&ccedil;&atilde;o</td>
											<td width="12%" align="center">Indicador Po&ccedil;o</td>
											<td width="11%" align="center">Capacidade</td>
										</tr>
										<%if (session.getAttribute("imovelMicromedicaoDadosResumo") != null) {

						%>
										<tr bgcolor="#FFFFFF" class="styleFontePequena">
											<td align="center"><a
												href="javascript:abrirPopup('exibirAnaliseExcecaoConsumoResumoPopupAction.do?codigoImovel=${idImovel}', 300, 700);">
											${inscricaoFormatada}</a> &nbsp;</td>
											<logic:present name="imovelMicromedicaoDadosResumo"
												property="imovel">
												<logic:equal name="imovelMicromedicaoDadosResumo"
													property="imovel.indicadorImovelCondominio" value="2">
													<td align="center">${idImovel}</td>
													<td align="center">Não</td>
												</logic:equal>
												<logic:equal name="imovelMicromedicaoDadosResumo"
													property="imovel.indicadorImovelCondominio" value="1">
													<td align="center"><a
														href="javascript:abrirPopup('ligacoesMedicaoIndividualizadasAction.do?codigoImovel=${idImovel}', 300, 800);"">
													${idImovel}</a></td>
													<td align="center">Sim</td>
												</logic:equal>
											</logic:present>
											<logic:notPresent name="imovelMicromedicaoDadosResumo"
												property="imovel">
												<td align="center">${idImovel}</td>
												<td align="center">Não</td>
											</logic:notPresent>
											<td align="center">${imovelMicromedicaoDadosResumo.imovel.hidrometroInstalacaoHistorico.hidrometro.numero}</td>
											<td align="center"><bean:write
												name="LeituraConsumoActionForm"
												property="instalacaoHidrometro" /></td>
											<logic:present name="poco">
												<td align="center"><bean:write name="pocoDescricao"/></td>
											</logic:present>
											<logic:notPresent name="poco">
												<td align="center">Não</td>
											</logic:notPresent>
											<td align="center">${imovelMicromedicaoDadosResumo.imovel.hidrometroInstalacaoHistorico.hidrometro.hidrometroCapacidade.descricao}</td>
										</tr>
										<tr>
											<td height="38" colspan="7">
											<table width="100%" bgcolor="#99CCFF" cellpadding="0"
												border="0">
												<!--header da tabela interna -->
												<tr bgcolor="#99CCFF" class="styleFontePeqNegrito">
													<td width="17%" bgcolor="#99CCFF" align="center">
													Situa&ccedil;&atilde;o de &Aacute;gua</td>
													<td width="17%" align="center">Situa&ccedil;&atilde;o de
													Esgoto</td>
													<td width="14%" align="center">Categoria</td>
													<td width="9%" align="center">Econ.</td>
													<td width="12%" align="center">Rateio</td>
													<td width="21%" align="center">Tipo de Cons.</td>
													<td width="22%" align="center">Rota</td>
													<td width="21%" align="center">Seq.Rota</td>
												</tr>
												<tr bgcolor="#FFFFFF" class="styleFontePequena">
													<td align="center">${imovelMicromedicaoDadosResumo.imovel.ligacaoAguaSituacao.descricao}</td>
													<td align="center">${imovelMicromedicaoDadosResumo.imovel.ligacaoEsgotoSituacao.descricao}</td>
													<td align="center">${categoria.descricaoAbreviada}</td>
													<td align="center">${imovelMicromedicaoDadosResumo.imovel.quantidadeEconomias}</td>
													<td align="center">${LeituraConsumoActionForm.rateio}</td>
													<td align="center">${imovelMicromedicaoDadosResumo.imovel.imovelPerfil.descricao}</td>
													<td align="center">${sessionScope.leituraConsumoActionForm.rota}</td>
													<td align="center">${sessionScope.leituraConsumoActionForm.seqRota}</td>
												</tr>
											</table>
											</td>
										</tr>
										<%}

					%>
									</table>
									</td>
								</tr>
								<tr>
									<td height="5">
									
									<div align="left">
										<a href="javascript:abrirPopup('exibirConsultarImovelAction.do?idImovel=${idImovel}',400,800);">
											<strong>Consultar Imóvel</strong>
						 				</a>
									</div> 	
									
									</td>
								</tr>
								<tr>
									<td align="right">
									<table width="100%" bgcolor="#99CCFF">
										<tr bordercolor="#79bbfd" class="styleFontePeqNegrito">
											<td width="526" height="20" colspan="2" bgcolor="#79bbfd"
												class="styleFonteTabelaPrincipal">
											<div align="center"><strong>Dados do Faturamento</strong></div>
											</td>
										</tr>
										<tr bordercolor="#99CCFF" class="styleFontePequena"
											bgcolor="#cbe5fe">
											<td height="140">
											<table width="100%" align="center" bgcolor="#cbe5fe"
												class="styleFontePequena">
												<!--corpo da segunda tabela-->
												<tr bgcolor="#cbe5fe">
													<td width="69" height="24"></td>
													<td width="80" class="styleFontePeqNegrito"><strong> Data</strong></td>
													<td width="50" class="styleFontePeqNegrito"><strong>Leitura
													</strong></td>
													<td width="94" class="styleFontePeqNegrito"><strong>Cons.
													Fat. : </strong></td>
													<td width="83"><html:text property="consumo"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" /> </td>
													<td colspan="2" class="styleFontePeqNegrito">
														Cons.Medido:&nbsp;&nbsp;&nbsp;&nbsp;
														<html:text property="medido"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="4" />
													 	<html:checkbox property="confirmacao"
														value="1" /><strong>
														Conf.</strong>
													</td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td width="67" height="22" class="styleFontePeqNegrito"><strong>Anterior
													:</strong></td>
													<td width="80"><html:text
														property="dataLeituraAnteriorFaturamento" size="10"
														maxlength="10"
														onkeypress="validaDataCompleta(this, event)"
														style="font-size:xx-small" /></td>

													<td width="50"><html:text
														property="leituraAnteriorFaturamento"
														style="font-size:xx-small" size="5" /></td>
													<td width="83" class="styleFontePeqNegrito"><strong>&nbsp;M&eacute;dia
													: </strong></td>
													<td width="83"><html:text property="consumoMedioImovel"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" /></td>
													<td width="83" class="styleFontePeqNegrito"><strong>Cons.
													Inf. : </strong></td>
													<td width="83"><html:text property="consumoInformado"
														style="font-size:xx-small" size="5" /></td>

												</tr>
												<tr bgcolor="#cbe5fe">
													<td width="67" height="22" class="styleFontePeqNegrito"><strong>Atual:</strong></td>

													<td width="80"><html:text
														property="dataLeituraAtualInformada" maxlength="10"
														onkeypress="validaDataCompleta(this, event)"
														style="font-size:xx-small" size="10" /></td>

													<td width="50"><html:text property="leituraAtualInformada"
														style="font-size:xx-small" size="5" /></td>
													<td width="94" class="styleFontePeqNegrito"><strong>% Var.
													Cons :</strong></td>
													<td width="83"><html:text property="varConsumo"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" /></td>
													<td width="118" class="styleFontePeqNegrito"><strong>Med.
													Hidrom. :</strong></td>
													<td width="94"><html:text property="consumoMedioHidrometro"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" /></td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td height="22" class="styleFontePeqNegrito">
													<div align="left"><strong>Ajustada</strong>&nbsp;:</div>
													</td>

													<td><html:text property="dataLeituraAtualFaturamento"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="10" /></td>

													<td><html:text property="leituraAtualFaturada"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" readonly="true" /></td>
													<td class="styleFontePeqNegrito">
													<div align="left"><strong>Dias Cons. </strong>:</div>
													</td>
													<td>
													<div align="left"><html:text property="diasConsumo"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" /></div>
													</td>
													<td width="118" class="styleFontePeqNegrito"><strong>Tipo
													Consumo&nbsp;:</strong></td>
													<td width="94">
														<logic:present name="imovelMicromedicaoConsumo">
														<bean:define name="imovelMicromedicaoConsumo" property="consumoHistorico.consumoTipo.descricao" id="consumoTipoDescricao" />
														<html:text property="tipoConsumo" title="${imovelMicromedicaoConsumo.consumoHistorico.consumoTipo.descricao}"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														value="${imovelMicromedicaoConsumo.consumoHistorico.consumoTipo.descricaoAbreviada}"
														size="5" />
													
														<img width="25" height="25" border="0" onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('Descrição:  <%=consumoTipoDescricao %>')" src="/gsan/imagens/informacao.gif"/>
								
														</logic:present>
														
													</td>
												</tr>
												<tr>
													<td width="53" class="styleFontePeqNegrito">Créd. Gerado :</td>
													<td><html:text property="creditoGerado"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" />
													</td>
													
													<td>&nbsp;</td>
													<td width="73" class="styleFontePeqNegrito">Créd. Faturado :</td>
													<td><html:text property="creditoFaturado"
														readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" />
													</td>
													<td class="styleFontePeqNegrito">
													<div align="left">&nbsp;<strong>Anor. Cons. : </strong></div>
													</td>
													<td>
													
													<html:text
														property="consumoAnormalidadeAbreviada"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="5" />
														
														<img width="25" height="25" border="0" onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('Descrição:  <bean:write name="LeituraConsumoActionForm" property="consumoAnormalidadeDescricao" />')" src="/gsan/imagens/informacao.gif"/>
													</td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td height="28" class="styleFontePeqNegrito">Anorm. :</td>
													<td colspan="6"><html:text property="idAnormalidade"
														style="font-size:xx-small" size="8"
														onkeypress="javascript:validaEnter(event, 'exibirDadosAnaliseMedicaoConsumoResumoAction.do?pesquisarAnormalidade=sim', 'idAnormalidade')" />
													<a
												href="javascript:chamarPopup('exibirPesquisarLeituraAnormalidadeAction.do', 'leituraAnormalidade', null, null, 600, 500, '',document.forms[0].idAnormalidade);">
													<img width="23" height="21" border="0"
														src="<bean:message key='caminho.imagens'/>pesquisa.gif"
														title="Pesquisar Anormalidade" /></a> <html:text
														property="descricaoAnormalidade" readonly="true"
														style="background-color:#EFEFEF; border:0;font-size:xx-small"
														size="60" /> <a
														href="javascript:limparPesquisaAnormalidade();"> <img
														src="<bean:message key="caminho.imagens"/>limparcampo.gif"
														border="0" title="Apagar" /></a>
													<div align="left"></div>
													</td>
													
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td height="5"></td>
								</tr>
								<tr>
									<td align="right">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="9" class="styleFonteTabelaPrincipal"
												align="center" bgcolor="#79bbfd">Hist&oacute;rico de
											Medi&ccedil;&atilde;o</td>
										</tr>
										<tr bgcolor="#90c7fc" class="styleFontePeqNegrito">
											<td width="8%" align="center">M&ecirc;s e Ano</td>
											<td width="10%" align="center">Dt. Leit. Inform.</td>
											<td width="6%" align="center">Leit. Inform.</td>
											<td width="12%" align="center">Dt. Leit. Faturada</td>
											<td width="8%" align="center">Leit. Faturada</td>
											<td width="9%" align="center">Cons.</td>
											<td width="12%" align="center">Anorm. Consumo</td>
											<td width="11%" align="center">Anorm. Leitura</td>
											<td width="17%" align="center">Sit. Leit. Atual</td>
										</tr>
										<logic:present name="imoveisMicromedicao">
											<tr bordercolor="#90c7fc">
												<td colspan="9" height="100">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%" align="left" bgcolor="#90c7fc">
													<%int cont = 0;%>
													<logic:iterate name="imoveisMicromedicao"
														id="imovelMicromedicaoIterator">
														<!--corpo da segunda tabela-->
														<%cont = cont + 1;
							if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe" class="styleFontePequena">
															<%} else {

							%>
														<tr bgcolor="#FFFFFF" class="styleFontePequena">
															<%}%>
															<td width="10%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.medicaoHistorico.mesAno}</font></td>
															<td width="11%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <%=Util
													.formatarData(((gcom.cadastro.imovel.bean.ImovelMicromedicao) imovelMicromedicaoIterator)
															.getMedicaoHistorico()
															.getDataLeituraAtualInformada())%> </font></td>
															<td width="7%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.medicaoHistorico.leituraAtualInformada}</font></td>
															<td width="13%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <%=Util
													.formatarData(((gcom.cadastro.imovel.bean.ImovelMicromedicao) imovelMicromedicaoIterator)
															.getMedicaoHistorico()
															.getDataLeituraAtualFaturamento())%> </font></td>
															<td width="10%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.medicaoHistorico.leituraAtualFaturamento}</font></td>
															<td width="9%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.consumoHistorico.numeroConsumoFaturadoMes}</font></td>
															<td width="12%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.consumoHistorico.consumoAnormalidade.descricaoAbreviada}</font></td>
															<td width="10%" align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">										
															${imovelMicromedicaoIterator.medicaoHistorico.leituraAnormalidadeFaturamento.descricaoAbreviada}
															</font></td>
															<td width="18%"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif">
															${imovelMicromedicaoIterator.medicaoHistorico.leituraSituacaoAtual.descricao}</font>
															</td>
														</tr>
													</logic:iterate>
												</table>
												</div>
												</td>
											</tr>
										</logic:present>
									</table>
								</td>
								</tr>
								<tr>
									<td align="right">
									<table width="100%" border="0" cellspacing="0">
										<tr>
											<td width="15%"><input type="button" name="Button3"
												class="bottonRightCol" value="Voltar"
												onclick="javascript:redirecionarSubmit('/gsan/filtrarExcecoesLeiturasConsumosWizardAction.do?concluir=true&action=validarExcecoesLeiturasConsumosLocalizacao')"></td>
											<td width="70%" align="center"><strong><font
												style="styleFontePeqNegrito"> Im&oacute;vel:</font></strong>
											<INPUT name="codigoImovel" TYPE="text"
												style="font-size:xx-small" size="9" maxlength="9"> <input
												type="button" name="pesquisaImovel" class="bottonRightCol"
												value="Pesquisar"
												onclick="pesquisaImovelNaColecao(document.forms[0].codigoImovel.value, '${tipoMedicao}');">

											&nbsp;&nbsp;&nbsp;&nbsp;<strong><font
												style="styleFontePeqNegrito">Posição: ${indiceImovel} /
											${totalRegistros}</font></strong></td>
											<td width="15%">
											<div align="right"><input style="width: 70px" type="button"
												name="Button2" class="bottonRightCol" value="Atualizar"
												onclick="submeterForm();"></div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="6">
							<table cellspacing="0" cellpadding="0" width="100%" border="0">
								<tr>
									<td width="50%">&nbsp;
									<html:checkbox property="relatorio" onchange="pesquisaImovelNaColecao('${idImovel}', '${tipoMedicao}');"/><strong><font style="styleFontePeqNegrito"> Adicionar para impressão</font></strong>
									</td>
									<td>&nbsp;</td>
									<td width="50%">&nbsp;
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td colspan="6">
											<table cellspacing="0" cellpadding="0" width="100%"
												border="0">
												<tr>
													<td width="50%">&nbsp; <logic:notPresent
														name="desabilitaBotaoAnterior">
														<table cellspacing="0" cellpadding="0" width="100%"
															border="0">
															<tr>
																<td align="right" width="83%"><img
																	src="<bean:message key="caminho.imagens"/>voltar.gif"
																	onclick="imovelAnterior();"></td>
																<td align="left" width="15%"><input type="button"
																	name="Button" class="buttonAbaRodaPe"
																	value="Imóvel Anterior" onclick="imovelAnterior();" /></td>
															</tr>
														</table>

													</logic:notPresent></td>
													<td width="100%">&nbsp; <logic:notPresent
														name="desabilitaBotaoProximo">
														<table cellspacing="0" cellpadding="0" width="100%"
															border="0">
															<tr>
																<td align="left" width="15%"><input type="button"
																	name="Button" class="buttonAbaRodaPe"
																	value="Próximo Imóvel" onclick="proximoImovel();" /></td>
																<td align="left" width="83%"><img
																	src="<bean:message key="caminho.imagens"/>avancar.gif"
																	onclick="proximoImovel()"></td>
															</tr>
														</table>
													</logic:notPresent></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</tr>
			</table>
			</td>
		</tr>
	</table>


	<%@ include file="/jsp/util/rodape.jsp"%>
	<%@ include file="/jsp/util/tooltip.jsp"%>


</html:form>
</body>
</html:html>
