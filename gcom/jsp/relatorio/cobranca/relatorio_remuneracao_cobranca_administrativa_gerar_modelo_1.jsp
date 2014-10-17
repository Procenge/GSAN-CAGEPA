<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.relatorio.cobranca.FiltroLocalidadeSetorComercialHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
function init(){
    var situacaoRemuneracao = document.forms[0].situacaoRemuneracao;
    var situacaoRemuneracaoChecked = false;
    
    for(i = 0; situacaoRemuneracao.length > i; i++){
        if(situacaoRemuneracao[i].checked){
        	situacaoRemuneracaoChecked = true;
            break;
        }
    }

    if(!situacaoRemuneracaoChecked){
    	situacaoRemuneracao[2].checked = true;
    }

    var indicadorConfirmaPagamento = document.forms[0].indicadorConfirmaPagamento;
    var indicadorConfirmaPagamentoChecked = false;
    
    for(i = 0; indicadorConfirmaPagamento.length > i; i++){
        if(indicadorConfirmaPagamento[i].checked){
        	indicadorConfirmaPagamentoChecked = true;
            break;
        }
    }

    if(!indicadorConfirmaPagamentoChecked){
    	indicadorConfirmaPagamento[1].checked = true;
    }
}

	function reload() {
		var form = document.forms[0];
		
		if(form.localidades.selectedIndex == 0) {
			limparSetoresComerciais();
		}
		
		form.action = '/gsan/exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do';
		form.submit();
	}
	function replicaDados(campoOrigem, campoDestino){
		campoDestino.value = campoOrigem.value;
	}	

	function habilitaSetorComercial() {
		var form = document.forms[0];
		if (form.selectedLocalidadesSize.value == '1') {
			form.setoresComerciais.disabled = false;	
		} else {
			form.setoresComerciais.disabled = true;	
		}
	}
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
    function limparPeriodoPagamento() {
        var form = document.forms[0];
        
        if (form.periodoPagamentoInicial.value != ''){
			form.periodoPagamentoInicial.value="";
		}
		
		if (form.periodoPagamentoFinal.value != ''){
			form.periodoPagamentoFinal.value="";
		}			
  	}
	
	/* Limpar Form */
	function limparForm(){
		var form = document.forms[0];
		
		form.periodoPagamentoInicial.value = "";
		form.periodoPagamentoFinal.value = "";
		form.localidades.selectedIndex = 0;	
		form.setoresComerciais.selectedIndex = 0;		
		limparSetoresComerciais();
		
		window.location.href = '/gsan/exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do?menu=sim';
	}
	/* Limpar setores comerciais */
	function limparSetoresComerciais() {
		var form = document.forms[0];

		for(i=form.setoresComerciais.length-1; i>0; i--) {
			form.setoresComerciais.options[i] = null;
		}
	}
	
	/* Replica Data de Pagamento */
	function replicaDataPagamento() {
		var form = document.forms[0];
		form.periodoPagamentoFinal.value = form.periodoPagamentoInicial.value
	}

	function validaForm(){

		var form = document.forms[0];
		var msg = '';
		var dataValida = true;
	
		if((form.periodoPagamentoInicial.value == "") || (form.periodoPagamentoFinal.value == "")) {		
			msg = "Informe o período de pagamento.";
		} else {
			if(verificaData(form.periodoPagamentoInicial)) {
				if(verificaData(form.periodoPagamentoFinal)) {

					if(!comparaDatas(form.periodoPagamentoInicial.value, '<', form.periodoPagamentoFinal.value)) {
						msg = "Data Final do Período é anterior à Data Inicial do Período";
					}
					if(subtrairDatas(form.periodoPagamentoFinal.value, form.periodoPagamentoInicial.value) > 30) {
						msg = "O período de pagamento não pode ser superior a 30 dias."
					}
	
				} else {
					dataValida = false;
				}
			} else {
				dataValida = false;
			}
		}
		
		if(msg != '') {
			alert(msg);
			return;
		}

		if(dataValida) {
			form.action = '/gsan/exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do';
			toggleBox('demodiv',1);
		}
	}

	function adicionarLocalidadeSetor() {
		var form = document.forms[0];
		form.action = '/gsan/exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do?operacao=Adicionar';
		form.submit();		
	}

</SCRIPT>


</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');init();">

<html:form action="/gerarRelatorioRemuneracaoCobrancaAdministrativaAction.do" 
		   name="GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm" 
		   type="gcom.gui.relatorio.cobranca.GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm"
		   method="post">

<html:hidden property="selectedLocalidadesSize" />
<html:hidden property="modeloRelatorio" value="1"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Gerar Relatório de Remuneração da Cobrança Administrativa</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
			<tr> 
                <td height="24" colspan="4" bordercolor="#000000" class="style1">Para 
                  gerar o Relatório de Remuneração da Cobrança Administrativa, informe os dados abaixo:</td>
              </tr>
              <tr> 
                <td height="24" colspan="4" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>

              <!-- Período de Pagamento -->

              <tr> 
                <td colspan="4"><span class="style2"><strong> 
                	    Per&iacute;odo de Pagamento:&nbsp;<font color="#FF0000">*</font>&nbsp;&nbsp;
						<html:text property="periodoPagamentoInicial" size="11" maxlength="10" tabindex="22" onkeyup="mascaraData(this, event);replicaDataPagamento();"/>
						<a href="javascript:abrirCalendarioReplicando('GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm', 'periodoPagamentoInicial','periodoPagamentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="periodoPagamentoFinal" size="11" maxlength="10" tabindex="23" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm', 'periodoPagamentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>              

              <!-- Localidade -->
			  <tr> 
                <td>&nbsp;</td>
                <td colspan="4">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2"><span class="style2"><strong>
                    Localidade:
					<html:select property="localidades" style="width: 260px; height:100px;" multiple="true" onchange="javascript:reload();habilitaSetorComercial();" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoLocalidades" scope="session">
							<html:options collection="colecaoLocalidades" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>

              <!-- Setor Comercial -->

                <td colspan="2"><span class="style2"><strong> 
                    Setor Comercial:
					<html:select property="setoresComerciais" style="width: 320px; height:100px;" multiple="true" tabindex="13">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSetoresComerciais" scope="session">
							<html:options collection="colecaoSetoresComerciais" labelProperty="descricaoComCodigo" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              <tr> 
                <td colspan="4" align="right">  
                    <input type="button" tabindex="34" name="Adicionar" class="bottonRightCol" value="Adicionar" onclick="adicionarLocalidadeSetor();">
                </td>
              </tr>      
              <tr> 
                <td height="24" colspan="8" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              
              <!-- Lista de Localidades e Setores Comerciais selecionados -->

			   <tr>
				<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr bordercolor="#000000"> 
					      <td bgcolor="#90c7fc" align="center" width="15%" height="20"><div align="center"><strong>Remover</strong></div></td>
					      <td bgcolor="#90c7fc" width="40%" height="20"><strong>Localidade</strong></td>
					      <td bgcolor="#90c7fc" width="40%" height="20"><strong>Setor Comercial</strong></td>
					   </tr>
						<logic:present name="colecaoLocalidadesSetores">
							<tr>
								<td colspan="3">

								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" bgcolor="#99CCFF">

									<%int cont = 1;%>
									<logic:iterate name="colecaoLocalidadesSetores"
										id="filtroLocalidadeSetor" type="FiltroLocalidadeSetorComercialHelper">
										<%cont = cont + 1;
							if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

							%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="15%">
												<div align="center"><font color="#333333"> 
													<img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remoção?')){redirecionarSubmit('exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do?operacao=Remover&idLocalidadeRemocao=<bean:write name="filtroLocalidadeSetor" property="localidade.id"/>&idSetorRemocao=<bean:write name="filtroLocalidadeSetor" property="setorComercialId"/>');}" />
												</font></div>
											</td>
											<td width="40%"><bean:write name="filtroLocalidadeSetor" property="localidade.descricao"/></td>
											<td width="40%"><bean:write name="filtroLocalidadeSetor" property="setorComercialDescricao"/></td>
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
					<td>&nbsp;</td>
					<td colspan="4"> <div align="right"> </div></td>
				</tr>
				<tr> 
					<td>&nbsp;</td>
					<td colspan="4"> <div align="right"> </div></td>
				</tr>

				<tr>
					<td colspan="1"><strong>Situação da Remuneração:<font color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<span class="style2"> 					
							<html:radio property="situacaoRemuneracao" tabindex="5" value="1"/><strong>Paga</strong>
							<html:radio property="situacaoRemuneracao" tabindex="6" value="2"/><strong>Não Paga</strong>
							<html:radio property="situacaoRemuneracao" tabindex="7" value="3"/><strong>Ambos</strong>
 						</span>
 					</td>
				</tr>

				<tr>
					<td colspan="1"><strong>Confirma pagamento da remuneração dos itens emitidos?<font color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<span class="style2"> 					
							<html:radio property="indicadorConfirmaPagamento" tabindex="8" value="1"/><strong>Sim</strong>
							<html:radio property="indicadorConfirmaPagamento" tabindex="9" value="2"/><strong>Não</strong>
 						</span>
 					</td>
				</tr>

              <!-- Botões -->

              <tr> 
                <td>&nbsp;</td>
                <td colspan="4"> <div align="right"> </div></td>
              </tr>
              <tr> 
                <td><input type="button" name="adicionar2" class="bottonRightCol" value="Limpar" tabindex="35" onclick="javascript:limparForm();"></td>
                <td colspan="3"> <div align="right"> 
                    <input type="button" tabindex="34" name="Submit" class="bottonRightCol" value="Gerar" onclick="validaForm();">
                  </div></td>
              </tr>      
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioRemuneracaoCobrancaAdministrativaAction.do&left=500&top=300"/>

<%@ include file="/jsp/util/rodape.jsp"%>

<!-- relatorio_remuneracao_cobranca_administrativa.jsp -->

</html:form>


</body>
</html:html>