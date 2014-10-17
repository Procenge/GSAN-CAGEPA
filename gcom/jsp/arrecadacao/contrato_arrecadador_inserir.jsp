<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<html:javascript staticJavascript="false" formName="InserirContratoArrecadadorActionForm" />
													
<script language="JavaScript">
   	var tipoContaBancaria;
   	
	function validarForm(form){
		if(validateInserirContratoArrecadadorActionForm(form) && validaTodosPeriodos(form)){
			
			if (form.idConvenioParcelamentoResponsavel.value != '') {
			
				if (form.cpfPrimeiroResponsavel.value != '' && form.nomePrimeiroResponsavel.value == '') {
					alert('Informe o nome do primeiro responsável');
					return;
				}
				if (form.cpfPrimeiroResponsavel.value == '' && form.nomePrimeiroResponsavel.value != '') {
					alert('Informe o cpf do primeiro responsável');
					return;
				}
				if (form.cpfSegundoResponsavel.value != '' && form.nomeSegundoResponsavel.value == '') {
					alert('Informe o nome do segundo responsável');
					return;
				}
				if (form.cpfSegundoResponsavel.value == '' && form.nomeSegundoResponsavel.value != '') {
					alert('Informe o cpf do segundo responsável');
					return;
				}
				if (form.cpfPrimeiroResponsavel.value != '' && !validacaoCpf(form.cpfPrimeiroResponsavel.value)) {
					alert('CPF Inválido');
					return;
				}
				if (form.cpfSegundoResponsavel.value != '' && !validacaoCpf(form.cpfSegundoResponsavel.value)) {
					alert('CPF Inválido');
					return;
				}

				if (form.cpfPrimeiroResponsavel.value != '' && form.cpfSegundoResponsavel.value != '' && form.cpfPrimeiroResponsavel.value == form.cpfSegundoResponsavel.value) {
					alert('Primeiro e Segundo Responsável pelo Convênio do Parcelamento por Responsável não podem ser a mesma pessoa.');
					return;
				}
			} else {
				if (form.cpfPrimeiroResponsavel.value != '' || form.nomePrimeiroResponsavel.value != '' || form.cpfSegundoResponsavel.value != '' || form.nomeSegundoResponsavel.value != '') {
					alert('Não é possível informar dados do responsável sem informar o Convênio-Parcelamento por Responsável');
					return;
				}
			}
			
			if (form.dtInicioContrato.value == '') {
				alert('Informe a data de início do contrato');
				return;
			}
			
			if (form.idConvenioBoletoBancario.value != '' && (form.idDepositoArrecadacaoBoleto.value == '' || form.idDepositoTarifaBoleto.value == '')) {
				alert('Conta Depósito Tarifa Boleto Bancário e Conta Depósito Arrecadação Boleto Bancário é obrigatório');
				return;
			} 
			
			
			if (form.idConvenioBoletoBancario.value == '' && (form.idDepositoArrecadacaoBoleto.value != '' || form.idDepositoTarifaBoleto.value != '')) {
				alert('Informe o Convênio-Boleto Bancário');
				return;
			} else if(validarConcessionaria(form)){
				// faz nada!
			} else {
				// Vai passar caso não tiver nenhum erro.
		    	submeterFormPadrao(document.forms[0]);
			}
			
		}
	}

	function validaTodosPeriodos(form) {

    	if (comparaData(form.dtInicioContrato.value, '>', form.dtFimContrato.value)){

			alert('Data Início do Contrato deve ser anterior ou igual à Data Fim do Contrato.');
			return false;

		}

		return true;
	}
   	
	function limparContaBancaria(form, contaBancaria) {
		if(contaBancaria == 'arrecadacao'){
			form.idContaBancariaArrecadador.value = "";
	    	form.bcoArrecadadorConta.value = "";
	    	form.agArrecadadorConta.value = "";
	    	form.numeroArrecadadorConta.value = "";
		}else if(contaBancaria == 'tarifa'){
			form.idContaBancariaTarifa.value = "";
	    	form.bcoTarifaConta.value = "";
	    	form.agTarifaConta.value = "";
	    	form.numeroTarifaConta.value = "";
		}else if(contaBancaria == 'arrecadacaoBoleto'){
			form.idDepositoArrecadacaoBoleto.value = "";
	    	form.bcoArrecadadorContaBoletoBancario.value = "";
	    	form.agArrecadadorContaBoletoBancario.value = "";
	    	form.numeroArrecadadorContaBoletoBancario.value = "";
		}else if(contaBancaria == 'tarifaBoleto'){
			form.idDepositoTarifaBoleto.value = "";
	    	form.bcoTarifaContaBoletoBancario.value = "";
	    	form.agTarifaContaBoletoBancario.value = "";
	    	form.numeroTarifaContaBoletoBancario.value = "";
		}
	}

    function recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) {
    	var form = document.forms[0];
	    if (tipoContaBancaria == 'arrecadacao') {
	    	form.idContaBancariaArrecadador.value = codigoRegistro;
	        form.bcoArrecadadorConta.value = descricaoRegistro1;
	        form.agArrecadadorConta.value = descricaoRegistro2;
	        form.numeroArrecadadorConta.value = descricaoRegistro3;
	      }else if (tipoContaBancaria == 'tarifa') {
	    	form.idContaBancariaTarifa.value = codigoRegistro;
	        form.bcoTarifaConta.value = descricaoRegistro1;
	        form.agTarifaConta.value = descricaoRegistro2;
	        form.numeroTarifaConta.value = descricaoRegistro3;
         	abrirPopup('contaBancariaPesquisarAction.do?tipo=contaBancaria');
	      }else if (tipoContaBancaria == 'arrecadacaoBoleto') {
	    	form.idDepositoArrecadacaoBoleto.value = codigoRegistro;
	        form.bcoArrecadadorContaBoletoBancario.value = descricaoRegistro1;
	        form.agArrecadadorContaBoletoBancario.value = descricaoRegistro2;
	        form.numeroArrecadadorContaBoletoBancario.value = descricaoRegistro3;
	      }else if (tipoContaBancaria == 'tarifaBoleto') {
	    	form.idDepositoTarifaBoleto.value = codigoRegistro;
	        form.bcoTarifaContaBoletoBancario.value = descricaoRegistro1;
	        form.agTarifaContaBoletoBancario.value = descricaoRegistro2;
	        form.numeroTarifaContaBoletoBancario.value = descricaoRegistro3;
	      } 
    }
	
    function pesquisaTipoConta(tipo) {
    	tipoContaBancaria = tipo;
    	abrirPopup('contaBancariaPesquisarAction.do?tipo=contaBancaria');
    }  
    
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		if (tipoConsulta == 'cliente') {
	        form.idCliente.value = codigoRegistro;
	        form.nomeCliente.value = descricaoRegistro;
	        redirecionarSubmit('exibirInserirContratoArrecadadorAction.do?objetoConsulta=1');
	     }
	}
	
	function habilitarPesquisaCliente(form) {
		chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',form.idCliente.value);
	}
	
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
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}

	function limparForm(tipo){
	     var form = document.forms[0];
		
	     if(tipo == 'cliente') { 
	        form.idCliente.value = "";
	        form.nomeCliente.value = "";
		 }
		
		if(tipo == ''){
			form.idCliente.value = "";
	        form.nomeCliente.value = "";
	        limparContaBancaria(form,'arrecadacao'); 
	        limparContaBancaria(form,'tarifa'); 
	        limparContaBancaria(form,'arrecadacaoBoleto'); 
	        limparContaBancaria(form,'tarifaBoleto'); 
	        limparTarifa();
	        form.idClienteCombo.value = "";
	        form.concessionariaId.value = -1;
	        form.numeroContrato.value = "";
	        form.emailCliente.value = "";
	        form.idConvenio.value = "";	  
	        form.idConvenioDebitoAutomatico.value = "";
	        form.idConvenioFichaCompensacao.value = "";
	        form.idConvenioBoletoBancario.value = "";
	        form.idConvenioParcelamentoResponsavel.value = "";
	        form.nomePrimeiroResponsavel.value = "";
	        form.cpfPrimeiroResponsavel.value = "";
	        form.nomeSegundoResponsavel.value = "";
	        form.cpfSegundoResponsavel.value = "";
	        form.indicadorCobranca.value = "1";
	        form.dtInicioContrato.value = "";
	        form.dtFimContrato.value = "";
		}
	}

	function validaFormaArrecadacao() {
		var form = document.forms[0];
		if (form.idFormaArrecadacao.value == '-1') {
			alert('Selecione a Forma de Arrecadação');
			return;
		}

		if (form.valorTarifa.value == '' && form.percentualTarifa.value == '') {
			alert('Informar o Valor da Tarifa ou o Percentual da Tarifa');
			return;
		} else if (form.valorTarifa.value != '' && form.percentualTarifa.value != '') {
			alert('Informar apenas o Valor da Tarifa ou o Percentual da Tarifa');
			return;
		} 

		if (form.qtdDiasFloat.value == '') {
			alert('Informar a Quantidade de Dias de FLOAT');
			return;
		}

		if (form.qtdDiasFloat.value == '0') {
			alert('Quantidade de Dias de FLOAT inválido.');
			return;
		}

		form.action = 'exibirInserirContratoArrecadadorAction.do?adicionarFormaArrecadacao=1&idFormaArrecadacao='+form.idFormaArrecadacao.value+'&valorTarifa='+form.valorTarifa.value+'&percentualTarifa='+form.percentualTarifa.value+'&qtdDiasFloat='+form.qtdDiasFloat.value;
		form.submit();
	}

	function remover(objeto){
    	if (confirm ("Confirma remoção?")) {
    		redirecionarSubmit('exibirInserirContratoArrecadadorAction.do?removerArrecadacaoForma=1');
    	}
    }

  	function limparTarifa() {
		var form = document.forms[0];
		form.idFormaArrecadacao.value = '-1';
		form.valorTarifa.value = '';
		form.percentualTarifa.value = '';
		form.qtdDiasFloat.value = '';
  	}

	function limparNomeCliente() {
		pCampo = document.forms[0].idCliente.value;	
		if(pCampo == "" || pCampo == "undefined"){
			limparForm('cliente');
		}		
  	}

</script>



<logic:present name="colecaoConcessionaria">
<SCRIPT LANGUAGE="JavaScript">
	function validarConcessionaria(formulario){
		var objConcessionariaID = returnObject(formulario, "concessionariaId");

		if (objConcessionariaID.value == -1) {
			alert('Informe Concessionária.');
			objConcessionariaID.focus();
			return true;
		
		}else {
			return false;
		}
	}
</SCRIPT>
</logic:present>
<logic:notPresent name="colecaoConcessionaria">
<SCRIPT LANGUAGE="JavaScript">
	function validarConcessionaria(formulario){
		// Faz nada!
		return false;
	}
</SCRIPT>
</logic:notPresent>

</head>



<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');limparTarifa();">

<html:form action="/inserirContratoArrecadadorAction.do"
	name="InserirContratoArrecadadorActionForm"
	type="gcom.gui.arrecadacao.InserirContratoArrecadadorActionForm"
	method="post" onsubmit="return validateInserirContratoArrecadadorActionForm(this);">

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
					<td class="parabg">Inserir Contrato de Arrecadador</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o contrato de arrecadador, informe
					os dados abaixo:</td>
				</tr>

				<tr>
					<td width="32%" class="style3"><strong>Arrecadador:<font color="#FF0000">*</font></strong></td>
					<td width="68%" colspan="2">
						<html:select property="idClienteCombo" tabindex="1" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoClienteArrecadador" property="id" labelProperty="nome" />
				  		</html:select>
				  	</td>
				</tr>

			  <logic:present name="colecaoConcessionaria">
				  <tr>
			          <td><strong>Concessionária:<font color="#FF0000">*</font></strong></td>
			          <td>
			              <html:select property="concessionariaId" style="width: 200px;" tabindex="3">
			                  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
		                      <html:options collection="colecaoConcessionaria" labelProperty="nome" property="id"/>
			              </html:select>
			          </td>
				  </tr>
			  </logic:present>
		

				<tr>
					<td width="32%" class="style3"><strong>Número Contrato:<font color="#FF0000">*</font></strong></td>
					<td width="68%" colspan="2"><strong><b><span class="style2"> 
					  <html:text
						property="numeroContrato" size="10" maxlength="10" tabindex="2"
						onkeyup="javascript:verificaNumeroInteiro(this);"/></span></b></strong>
				  </td>
				</tr>
				<tr>
					<td width="32%"><strong>Conta Depósito Arrecadação:<font color="#FF0000">*</font></strong></td>
					<html:hidden property="idContaBancariaArrecadador"/>
					<td width="68%"><html:text maxlength="4" property="bcoArrecadadorConta" size="4"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="9" property="agArrecadadorConta" size="9"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="20" property="numeroArrecadadorConta" size="20"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<a href="javascript:pesquisaTipoConta('arrecadacao');">
					<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Conta Bancária" /></a>
					<a href="javascript:limparContaBancaria(document.forms[0],'arrecadacao');">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Limpar Conta Bancária"/></a>
				  </td>
				</tr>

				<tr>
					<td width="32%"><strong>Conta Depósito Tarifa:<font color="#FF0000">*</font></strong></td>
					<html:hidden property="idContaBancariaTarifa"/>
					<td width="68%"><html:text maxlength="4" property="bcoTarifaConta" size="4"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="9" property="agTarifaConta" size="9"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="20" property="numeroTarifaConta" size="20"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<a href="javascript:pesquisaTipoConta('tarifa');" >
					<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Conta Bancária" /></a>
					<a href="javascript:limparContaBancaria(document.forms[0],'tarifa');">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Limpar Conta Bancária"/></a>
				  </td>
				</tr>

				<tr>
					<td><strong>Cliente:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:text property="idCliente" size="10" maxlength="10" tabindex="3" onkeyup="javascript:verificaNumeroInteiro(this);limparNomeCliente();"
						onkeypress="javascript:return validaEnter(event, 'exibirInserirContratoArrecadadorAction.do?objetoConsulta=1', 'idCliente');" />
					</strong> <a href="javascript:habilitarPesquisaCliente(document.forms[0]);"	alt="Pesquisar Cliente"> 
					            <img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
					          </a>
					<logic:present name="existeCliente">
						<logic:equal name="existeCliente" value="exception">
							<html:text property="nomeCliente" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="existeCliente" value="exception">
							<html:text property="nomeCliente" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="existeCliente">
						<logic:empty name="InserirContratoArrecadadorActionForm" property="idCliente">
							<html:text property="nomeCliente" size="35" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:empty>
						<logic:notEmpty name="InserirContratoArrecadadorActionForm"
							property="idCliente">
							<html:text property="nomeCliente" size="35" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> 
					<a href="javascript:limparForm('cliente');"> 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>
					</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>E-mail:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="emailCliente" size="40" maxlength="40" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>

				<tr>
					<td width="32%" class="style3"><strong>Convênio-Código de Barras:</strong>
					</td>
					<td width="68%" colspan="2"><strong><b><span class="style2"> 
					  <html:text
						property="idConvenio" size="20" maxlength="20" tabindex="4" />
				  </span></b></strong></td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>Convênio-Débito Automático:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="idConvenioDebitoAutomatico" size="20" maxlength="20" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>Convênio-Ficha de Compensação:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="idConvenioFichaCompensacao" size="20" maxlength="20" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				<tr>
					<td width="32%" class="style3">
						<strong>Convênio-Boleto Bancário:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="idConvenioBoletoBancario" size="20" maxlength="20" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%"><strong>Conta Depósito Arrecadação Boleto Bancário:</strong></td>
					<html:hidden property="idDepositoArrecadacaoBoleto"/>
					<td width="68%"><html:text maxlength="4" property="bcoArrecadadorContaBoletoBancario" size="4"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="9" property="agArrecadadorContaBoletoBancario" size="9"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="20" property="numeroArrecadadorContaBoletoBancario" size="20"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<a href="javascript:pesquisaTipoConta('arrecadacaoBoleto');">
					<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Conta Bancária" /></a>
					<a href="javascript:limparContaBancaria(document.forms[0],'arrecadacaoBoleto');">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Limpar Conta Bancária"/></a>
				  </td>
				</tr>

				<tr>
					<td width="32%"><strong>Conta Depósito Tarifa Boleto Bancário:</strong></td>
					<html:hidden property="idDepositoTarifaBoleto"/>
					<td width="68%"><html:text maxlength="4" property="bcoTarifaContaBoletoBancario" size="4"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="9" property="agTarifaContaBoletoBancario" size="9"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<html:text maxlength="20" property="numeroTarifaContaBoletoBancario" size="20"
						readonly="true"
						style="background-color:#EFEFEF; border:0; font-color: #000000" />
					<a href="javascript:pesquisaTipoConta('tarifaBoleto');" >
					<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Conta Bancária" /></a>
					<a href="javascript:limparContaBancaria(document.forms[0],'tarifaBoleto');">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Limpar Conta Bancária"/></a>
				  </td>
				</tr>
				
				<tr>
					<td colspan="2">
						<hr>
					</td>
				</tr>
				<tr>
					<td width="32%" class="style3">
						<strong>Convênio-Parcelamento por Responsável:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="idConvenioParcelamentoResponsavel" size="20" maxlength="20" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>Nome do 1º Responsável:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="nomePrimeiroResponsavel" size="50" maxlength="50" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>CPF do 1º Responsável:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="cpfPrimeiroResponsavel" size="11" maxlength="11" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>Nome do 2º Responsável:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="nomeSegundoResponsavel" size="50" maxlength="50" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3">
						<strong>CPF do 2º Responsável:</strong>
					</td>
					<td width="68%" colspan="2">
						<strong>
							<b>
								<span class="style2"> 
					  				<html:text property="cpfSegundoResponsavel" size="11" maxlength="11" tabindex="4" />
				  				</span>
				  			</b>
				  		</strong>
				  	</td>
				</tr>
				<tr>
					<td colspan="2">
						<hr>
					</td>
				</tr>
				<tr>
					<td width="32%"><strong>Indicador de Cobrança de ISS:</strong></td>
					<td width="68%"><strong> <span class="style1"><strong> 
					<html:radio property="indicadorCobranca" value="1" tabindex="5" /> <strong>Cobra ISS 
					<html:radio property="indicadorCobranca" value="2" tabindex="6" />Não Cobra ISS
				  </strong></strong></span></strong></td>
				</tr>

				<tr>
					<td><strong>Data Início do contrato:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="dtInicioContrato" size="10"
						maxlength="10" tabindex="7" onkeyup="mascaraData(this, event);"/>
					<a
						href="javascript:abrirCalendario('InserirContratoArrecadadorActionForm', 'dtInicioContrato');">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>

				<tr>
					<td><strong>Data Fim do Contrato:</strong></td>
					<td><html:text property="dtFimContrato" size="10" maxlength="10"
						tabindex="8" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('InserirContratoArrecadadorActionForm', 'dtFimContrato')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
				<tr>
					<td colspan="2">
						<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<strong>Tarifas do Contrato por Forma de Arrecadação:<font color="#FF0000">*</font></strong>
					</td>
				</tr>
					
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td colspan="2">
									<strong>Forma de Arrecadação:</strong>
								</td>
								<td colspan="5">
									<html:select property="idFormaArrecadacao" tabindex="1" style="width:100%;" >
										<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>"> &nbsp; </html:option>
										<logic:present name="colecaoFormaArrecadacao">
											<html:options collection="colecaoFormaArrecadacao" property="id" labelProperty="descricao"/>
										</logic:present>
						  			</html:select>
						  		</td>
						  	</tr>
							<tr>	
								<td>
							  		<strong>Valor Tarifa:</strong>
							  	</td>
								<td>
									<strong>
										<b>
											<span class="style2"> 
								  				<html:text property="valorTarifa" size="9" maxlength="9" tabindex="4" onkeyup="javaScript:formataValorDecimalPermiteNegativo(this,2);"/>
							  				</span>
							  			</b>
							  		</strong>
							  	</td>
							  	<td>
									<strong>% da Tarifa:</strong>
								</td>
								<td>
									<strong>
										<b>
											<span class="style2"> 
								  				<html:text property="percentualTarifa" size="9" maxlength="9" tabindex="4" onkeyup="javaScript:formataValorDecimalPermiteNegativo(this,2);"/>
							  				</span>
							  			</b>
							  		</strong>
							  	</td>
								<td>
									<strong>Dias de FLOAT:</strong>
								</td>
								<td>
									<strong>
										<b>
											<span class="style2"> 
								  				<html:text property="qtdDiasFloat" size="9" maxlength="9" tabindex="4" onkeyup="javascript:verificaNumeroInteiro(this);" onblur="javascript:verificaNumeroInteiro(this);"/> 
							  				</span>
							  			</b>
							  		</strong>
							  	</td>
							  	<td>
									<input type="button" tabindex="6"
											class="bottonRightCol" value="Adicionar" name="botaoAdicionar"
											onclick="javascript:validaFormaArrecadacao();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td height="0">
										<table width="100%" bgcolor="#99CCFF">
											<!--header da tabela interna -->
											<tr bgcolor="#90c7fc">
												<td width="10%" align="center">
													<strong>Remover</strong>
												</td>
												<td width="50%" align="center">
													<strong>Forma de Arrecadação</strong>
												</td>
												<td width="10%" align="center">
													<strong>Valor Monetário</strong>
												</td>
												<td width="10%" align="center">
													<strong>Percentual</strong>
												</td>
												<td width="10%" align="center">
													<strong>Dias de FLOAT</strong>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="100">
										<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%" align="center" bgcolor="#99CCFF">
											<logic:present name="colecaoArrecadadorContratoTarifa">
												<%int cont = 0;%>
												<%--Campo que vai guardar o valor do telefone a ser removido--%>
												<input type="hidden" name="idRegistrosRemocao" value="" />
												<logic:iterate name="colecaoArrecadadorContratoTarifa" id="arrecadadorContratoTarifa" scope="session">
													<%cont = cont + 1;
													if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
													<%} else {%>
													<tr bgcolor="#FFFFFF">
													<%}%>
														<td width="10%" align="center">
															<strong> 
															<img src="<bean:message key='caminho.imagens'/>Error.gif"
																width="14" height="14" style="cursor:hand;" alt="Remover"
																onclick="javascript:remover(document.forms[0].idRegistrosRemocao.value='${arrecadadorContratoTarifa.comp_id.arrecadacaoFormaId}');">
															</strong>
														</td>
														<td width="50%" align="center">
															<strong>
																<bean:write	name="arrecadadorContratoTarifa" property="arrecadacaoForma.descricao" />
															</strong>
														</td>
														<td width="10%" align="center">
															<strong>
																<bean:write	name="arrecadadorContratoTarifa" property="valorTarifa" />
															</strong>
														</td>
														<td width="10%" align="center">
															<strong>
																<bean:write	name="arrecadadorContratoTarifa" property="percentualTarifa" />
															</strong>
														</td>
														<td width="10%" align="center">
															<strong>
																<bean:write	name="arrecadadorContratoTarifa" property="numeroDiaFloat" />
															</strong>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
										</table>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<hr>
					</td>
				</tr>
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
			
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>

				<tr>
					<td width="33%" align="left"><input type="button"
						name="ButtonReset" class="bottonRightCol" value="Desfazer"
						onClick="limparForm('');"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>

					<td align="right">
						<input type="button" name="Button" class="bottonRightCol" value="Inserir" onClick="validarForm(document.forms[0]);" tabindex="11" />
					</td>
				</tr>

			</table>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
