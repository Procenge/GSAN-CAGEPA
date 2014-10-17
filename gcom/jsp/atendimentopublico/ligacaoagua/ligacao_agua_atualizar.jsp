<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.atendimentopublico.ordemservico.ServicoTipo"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="AtualizarLigacaoAguaActionForm" /> 

<script language="JavaScript">

	//Valida o form no validator.xml
	function validaForm() {
		var form = document.AtualizarLigacaoAguaActionForm;
	   	if (validateAtualizarLigacaoAguaActionForm(form)){
			submeterFormPadrao(form);
		}
	}

	function limpar(){

		var form = document.forms[0];
		
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
				   
		//Coloca o foco no objeto selecionado
		form.idFuncionario.focus();
	}

	function limparDescricao(){
		
		var form = document.forms[0];
		form.descricaoFuncionario.value = "";
	}
	
	//Limpa Form Exibir
	function limparForm() {
		var form = document.AtualizarLigacaoAguaActionForm;
		form.idOrdemServico.value = "";
		form.numeroLacre.value = "";
		
		limparFormAtualizar();	
		//form.action = 'exibirAtualizarLigacaoAguaAction.do';
		//form.submit();

	}
	
	//Limpa Form Atualizar
	function limparFormAtualizar() {

		var form = document.AtualizarLigacaoAguaActionForm;

		habilitaLigacao(false);
		habilitaCorte(false);
		habilitaSupressao(false);

		form.nomeOrdemServico.value = "";
		
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";

		limparLigacao();
		limparCorte();
		limparSupressao();

		form.habilitaCorte.value = "true";
		form.habilitaSupressao.value = "true";
		form.dataLigacao.value = "";

	}

	//Chama Popup
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
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaOrdemServico=" + tipo, altura, largura);
				}
			}
		}
	}

	//Recupera Dados Popup
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];
	    if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirAtualizarLigacaoAguaAction.do';
	      	form.submit();
	    } else {
	    	if (tipoConsulta=='funcionario'){
	  			form.idFuncionario.value = codigoRegistro;
	  			form.descricaoFuncionario.value = descricaoRegistro;
	  			form.descricaoFuncionario.style.color = "#000000";
	      	}else{
	      		form.idCliente.value = codigoRegistro;  
	      		form.descricaoCliente.value = descricaoRegistro;
	      		form.descricaoCliente.style.color = "#000000";
	      	}	
	    }
	}
	function limparLigacao(){
		var form = document.forms[0];

		form.diametroLigacao.selectedIndex = 0;
		form.materialLigacao.selectedIndex = 0;
		form.perfilLigacao.selectedIndex = 0;
		form.ramalLocalInstalacao.selectedIndex = 0;
	}

	function limparCorte(){
		var form = document.forms[0];

		form.motivoCorte.selectedIndex = 0;
		form.tipoCorte.selectedIndex = 0;
		form.numSeloCorte.value = "";
		form.leituraCorte.value = "";
	}

	function limparSupressao(){
		var form = document.forms[0];
		
		form.motivoSupressao.selectedIndex = 0;
		form.tipoSupressao.selectedIndex = 0;
		form.numSeloSupressao.value = "";
		form.leituraSupressao.value = "";
	}
	
	function habilitaCorte(desabilita){
		var form = document.forms[0];
				
		form.motivoCorte.disabled = desabilita;
		form.tipoCorte.disabled = desabilita;
		form.numSeloCorte.disabled = desabilita;
		form.leituraCorte.disabled = desabilita;
	}

	function habilitaSupressao(desabilita){
		var form = document.forms[0];

		form.motivoSupressao.disabled = desabilita;
		form.tipoSupressao.disabled = desabilita;
		form.numSeloSupressao.disabled = desabilita;
		form.leituraSupressao.disabled = desabilita;
	}

	function habilitaLigacao(desabilita){
		var form = document.forms[0];	

		form.diametroLigacao.disabled = desabilita;
		form.materialLigacao.disabled = desabilita;
		form.perfilLigacao.disabled = desabilita;
		form.ramalLocalInstalacao.disabled = desabilita;
	}

	//verifica se existe alguma restrição 
	//para exibição alguma campo no formulario
    function verificaForm() {

       	var form = document.forms[0];

		if(form.veioEncerrarOS.value == 'true'){
			form.idOrdemServico.disabled=true;
		}else{
			form.idOrdemServico.disabled=false;
		}
		
		var condicaoServicoTipo = document.getElementById("SERVICO_TIPO_CONFIRMAR_DADOS_LIGACAO_AGUA").value;
		var idsServicoTipo = condicaoServicoTipo.split(",");
		
		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i].replace("[", "").replace("]", "") == form.servicoTipo.value){

				 habilitaCorte(true);
		         habilitaSupressao(true);
		         habilitaLigacao(false);
	             return;
			}
		}

		condicaoServicoTipo = document.getElementById("SERVICO_TIPO_LIGACAO_AGUA").value;
		idsServicoTipo = condicaoServicoTipo.split(",");

		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i].replace("[", "").replace("]", "") == form.servicoTipo.value){

				habilitaCorte(true);
	            habilitaSupressao(true);
	            habilitaLigacao(false);
	            return;
			}
		}
		
		condicaoServicoTipo = document.getElementById("SERVICO_TIPO_CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA").value;
		idsServicoTipo = condicaoServicoTipo.split(",");

		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i] == form.servicoTipo.value){

				habilitaLigacao(true);
	            habilitaSupressao(true);
	            habilitaCorte(false);
	            return;
			}
		}

		condicaoServicoTipo = document.getElementById("SERVICO_TIPO_CORTE_LIGACAO_AGUA").value;

		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i] == form.servicoTipo.value){

				 habilitaLigacao(true);
		         habilitaSupressao(true);
		         habilitaCorte(false);
	            return;
			}
		}

		condicaoServicoTipo = document.getElementById("SERVICO_TIPO_CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA").value;

		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i] == form.servicoTipo.value){

				habilitaLigacao(true);
	            habilitaCorte(true);
	            habilitaSupressao(false);
	            return;
			}
		}

		condicaoServicoTipo = document.getElementById("SERVICO_TIPO_SUPRESSAO_LIGACAO_AGUA").value;

		for ( i = 0; i < idsServicoTipo.length ; i++){

			if (idsServicoTipo[i] == form.servicoTipo.value){

				habilitaLigacao(true);
	            habilitaCorte(true);
	            habilitaSupressao(false);
	            return;
			}
		}

       habilitaLigacao(false);
       habilitaCorte(false);
       habilitaSupressao(false);
        
	}

	function desabilitaCamposNumeroLacreOnClick(){
		var form = document.forms[0];
	  if(form.aceitaLacre[0].checked){
		form.numeroLacre.disabled = false;
	  }else{
		form.numeroLacre.value = "";
	    form.numeroLacre.disabled = true;
	    form.numeroLacre.style.color = "#000000";
	  }
	}	

</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');verificaForm();desabilitaCamposNumeroLacreOnClick();">

<html:form action="/atualizarLigacaoAguaAction.do"
	name="AtualizarLigacaoAguaActionForm"
	type="gcom.gui.atendimentopublico.AtualizarLigacaoAguaActionForm"
	method="post">

	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="servicoTipo" />
	<html:hidden property="habilitaCorte"/>
	<html:hidden property="habilitaSupressao" />
	
	<input type="hidden" id="SERVICO_TIPO_CONFIRMAR_DADOS_LIGACAO_AGUA" value="<%=ServicoTipo.CONFIRMAR_DADOS_LIGACAO_AGUA.toString()%>"/>
    <input type="hidden" id="SERVICO_TIPO_LIGACAO_AGUA" value="<%=ServicoTipo.LIGACAO_AGUA.toString()%>"/>
    <input type ="hidden" id="SERVICO_TIPO_CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA" value="<%=ServicoTipo.CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA.toString()%>"/>
    <input type ="hidden" id="SERVICO_TIPO_CORTE_LIGACAO_AGUA" value="<%=ServicoTipo.CORTE_LIGACAO_AGUA.toString()%>"/>
    <input type ="hidden" id="SERVICO_TIPO_CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA" value="<%=ServicoTipo.CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA.toString()%>"/>
    <input type ="hidden" id="SERVICO_TIPO_SUPRESSAO_LIGACAO_AGUA" value="<%=ServicoTipo.SUPRESSAO_LIGACAO_AGUA.toString()%>"/>
	
	
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

			<td width="615" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Atualizar Ligação de Água</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			
			<p>&nbsp;</p>


			<!--Inicio da Tabela Ligação Água -->
            <table width="100%" border="0">
            	<tr>
                	<td height="31">
                    <table width="100%" border="0" align="center">
                   		<tr>
                       		<td height="10" colspan="2">
                       		Para atualizar a  liga&ccedil;&atilde;o de &aacute;gua, 
                       		informe os dados abaixo: </td>
                   		</tr>
                   		
                   		<tr>
                        	<td height="10" colspan="2"><hr></td>
                   		</tr>
                   		
						<tr>
							<td height="10">
								<strong>Ordem de Servi&ccedil;o:<span class="style2">
								<strong><font color="#FF0000">*</font></strong></span>
								</strong>
							</td>
									
							<td>
								<span class="style2">
	
									<html:text
										property="idOrdemServico" 
										size="8" 
										maxlength="9" 
										onkeypress="javascript:limparFormAtualizar();validaEnterComMensagem(event, 'exibirAtualizarLigacaoAguaAction.do', 'idOrdemServico','Ordem de Serviço');" />
	
									<a href="javascript:chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 600, 500, '',document.forms[0].idOrdemServico);">
		
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar OS" /></a>
		
									<logic:present name="numeroOsEncontrada" scope="request">
								
										<html:text property="nomeOrdemServico" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" 
											size="37"
											maxlength="40" /> 
								
									</logic:present> 

									<logic:notPresent name="numeroOsEncontrada" scope="request">
								
										<html:text property="nomeOrdemServico" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />

									</logic:notPresent>

										
									<a href="javascript:limparForm();"> 
										<img border="0" 
											title="Apagar"
											src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
									</a>
								
								</span>
							</td>
						</tr>


                        <tr bgcolor="#cbe5fe">

                      		<td align="center" colspan="2">
                      		<table width="100%" border="0" bgcolor="#99CCFF">

					    		<tr bgcolor="#99CCFF">
                         			<td height="18" colspan="2">
                         				<div align="center">
                         					<span class="style2"> Dados do Imóvel </span>
                         				</div>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

										<tr>
											<td width="37%" height="10">
												<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
											</td>

											<td width="58%">
												<html:text property="matriculaImovel"
													readonly="true" 
													style="background-color:#EFEFEF; border:0;"
													size="15" 
													maxlength="20" /> 
												
												<html:text
													property="inscricaoImovel" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="21"
													maxlength="20" />
											</td>
										</tr>

										<tr>
											<td><strong> Cliente Usu&aacute;rio:</strong></td>
											<td>
												<html:text property="clienteUsuario" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"
													maxlength="40" />
											</td>
										</tr>
										
										<tr>
											<td><strong>CPF ou CNPJ:</strong></td>
											<td>
												<html:text property="cpfCnpjCliente" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"
													maxlength="40" />
											</td>
										</tr>
										
										<tr>
											<td><strong>
												Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de &Aacute;gua:
											</strong></td>
											
											<td>
												<html:text property="situacaoLigacaoAgua" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"
													maxlength="40" />
											</td>
										</tr>

										<tr>
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
											de Esgoto:</strong>
											</td>

											<td>
												<html:text property="situacaoLigacaoEsgoto"
													readonly="true" 
													style="background-color:#EFEFEF; border:0;"
													size="40" 
													maxlength="40" />
											</td>
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
				
				<tr>
			    	<td height="31">
			    	<table width="100%" border="0" align="center">
                    	<tr bgcolor="#cbe5fe">
                      		<td align="center" colspan="2">
                        	<table width="100%" border="0" bgcolor="#99CCFF">
                          		<tr bgcolor="#99CCFF">
                            		<td height="18" colspan="2">
                            			<div align="center">
                            				<span class="style2"> Dados da Liga&ccedil;&atilde;o de &Aacute;gua </span>
                            			</div>
                            		</td>
                          		</tr>
                          		
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										
										<tr>
											<td>
												<strong> Data da Liga&ccedil;&atilde;o:
												<strong><font color="#FF0000">*</font></strong>
												</strong>
											</td>

											<td colspan="2">
												<strong> 
												<html:text property="dataLigacao" 
													size="11" 
													maxlength="10" 
													tabindex="3" 
													onkeyup="mascaraData(this, event)"/>
													
												<a href="javascript:abrirCalendario('AtualizarLigacaoAguaActionForm', 'dataLigacao');">
													<img border="0" 
														src="<bean:message key='caminho.imagens'/>calendario.gif" 
														width="16" 
														height="15" 
														border="0" 
														alt="Exibir Calendário" 
														tabindex="4"/></a> (dd/mm/aaaa)

												</strong>
											</td>
										</tr>

										<tr>
											<td>
												<strong> Diâmetro da Liga&ccedil;&atilde;o:</strong>
											</td>

											<td colspan="2">
												<strong> 
												<html:select property="diametroLigacao" style="width: 230px;">
													<html:option
														value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
													</html:option>
											
													<logic:present name="colecaoDiametroLigacaoAgua"
														scope="session">
														<html:options collection="colecaoDiametroLigacaoAgua"
														labelProperty="descricao" property="id" />
													</logic:present>
												</html:select> 														
												</strong>
											</td>
										</tr>

										<tr>
											<td class="style3">
												<strong>Material da Liga&ccedil;&atilde;o:</strong>
											</td>

											<td colspan="2">
												<strong> 
												
												<html:select property="materialLigacao" style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
												</html:option>

												<logic:present name="colecaoMaterialLigacao"
													scope="session">
													<html:options collection="colecaoMaterialLigacao"
														labelProperty="descricao" property="id" />
												</logic:present>
												</html:select> 
												</strong>
											</td>
										</tr>

										<tr>
											<td class="style3">
												<strong>Perfil da Liga&ccedil;&atilde;o:</strong>
											</td>

											<td colspan="2">
												<strong> 
												<html:select property="perfilLigacao" style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
												</html:option>

												<logic:present name="colecaoPerfilLigacao"
													scope="session">
													<html:options collection="colecaoPerfilLigacao"
														labelProperty="descricao" property="id" />
												</logic:present>
												</html:select> 
												</strong>
											</td>
											
										</tr>
										
										<tr>
											<td class="style3">
												<strong>Local de Instala&ccedil;&atilde;o do Ramal :</strong>
											</td>

											<td colspan="2">
												<strong> 
												<html:select property="ramalLocalInstalacao" style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
												</html:option>

												<logic:present name="colecaoRamalLocalInstalacao" scope="session">
													<html:options collection="colecaoRamalLocalInstalacao"
														labelProperty="descricao" property="id" />
												</logic:present>
												</html:select> 
												</strong>
											</td>
											
										</tr>										
										
										
										<tr>
											<td class="style3"><strong>Motivo do Corte:
												<span class="style2"><strong><strong><strong>
													<font color="#FF0000"></font></strong></strong></strong>
												</span>
											</strong></td>
											


												<td colspan="2">
													<strong>

													<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="true">
														<html:select property="motivoCorte" style="width: 230px;">
														
															<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
															</html:option>

															<logic:present name="colecaoMotivoCorte" scope="session">
																<html:options collection="colecaoMotivoCorte" labelProperty="descricao" property="id" />
															</logic:present>
														
														</html:select> 
													</logic:equal>
													
													<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="false">
														<html:select property="motivoCorte" style="width: 230px;" disabled="true">
														
															<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
															</html:option>

															<logic:present name="colecaoMotivoCorte" scope="session">
																<html:options collection="colecaoMotivoCorte" labelProperty="descricao" property="id" />
															</logic:present>
														</html:select> 
													</logic:equal>

													</strong>
												</td>


										</tr>
		                                
										<tr>
											<td class="style3"><strong>Tipo do Corte:
												<span class="style2"><strong><strong><strong>
													<font color="#FF0000"></font></strong></strong></strong>
												</span>
											</strong></td>

											<td colspan="2">
												<strong> 
												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="true">
													<html:select property="tipoCorte" style="width: 230px;">

														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
														
														<logic:present name="colecaoTipoCorte" scope="session">
														<html:options collection="colecaoTipoCorte" labelProperty="descricao" property="id" />
														</logic:present>

													</html:select> 
												</logic:equal>

												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="false">
													<html:select property="tipoCorte" style="width: 230px;" disabled="true">

														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
														
														<logic:present name="colecaoTipoCorte" scope="session">
														<html:options collection="colecaoTipoCorte" labelProperty="descricao" property="id" />
														</logic:present>

													</html:select> 
												</logic:equal>

												</strong>
											</td>											
										</tr>
		                                
										<tr>
											<td class="style3"><strong>N&uacute;mero do Selo do Corte:
												<span class="style2"><strong><strong><strong>
													<font color="#FF0000"></font></strong></strong></strong>
												</span></strong>
											</td>
											
											<td colspan="2">
												<strong> 
												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="true">
													<html:text property="numSeloCorte" 
														size="12" 
														maxlength="12"/> 
												</logic:equal>

												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaCorte" value="false">
													<html:text property="numSeloCorte" 
														size="12" 
														maxlength="12"
														disabled="true"/> 
												</logic:equal>

												</strong>												
											</td>
										</tr>
							
                                		<!-- Leitura Corte -->
                                		
		                                <tr>
		                                  	<td class="style3"><strong>Leitura Corte:</strong></td>					                                  	
		                                  	<td>
		                                  		<strong><b><span class="style2">
												<c:choose>
													<c:when test="${hidrometroInstalacaoHistorico.dataRetirada != null}">
														<html:text property="leituraCorte" 
															size="10" 
															maxlength="8" 
															disabled="false" />
													</c:when>
													<c:otherwise>
														<html:text property="leituraCorte" 
															size="10" 
															maxlength="8" 
															disabled="true" />
													</c:otherwise>
												</c:choose>
		                                  		</span></b></strong>
		                                  	</td>
		                                </tr>
										
										<tr>
											<td class="style3"><strong>Motivo da Supress&atilde;o:
												<span class="style2"><strong><strong><strong>
													<font color="#FF0000"></font></strong></strong></strong>
												</span></strong>
											</td>

											<td colspan="2">
												<strong>
												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="true">
													<html:select property="motivoSupressao" style="width: 230px;">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
		
														<logic:present name="colecaoSupressaoMotivo" scope="session">
															<html:options collection="colecaoSupressaoMotivo" labelProperty="descricao" property="id" />
														</logic:present>
													</html:select> 
												</logic:equal>

												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="false">
													<html:select property="motivoSupressao" style="width: 230px;" disabled="true">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
		
														<logic:present name="colecaoSupressaoMotivo" scope="session">
															<html:options collection="colecaoSupressaoMotivo" labelProperty="descricao" property="id" />
														</logic:present>
													</html:select> 
												</logic:equal>

												</strong>
											</td>											

										</tr>


										<tr>
											<td class="style3"><strong>Tipo da Supress&atilde;o:
												<span class="style2">
													<strong><strong><strong>
													<font color="#FF0000"></font></strong></strong></strong>
												</span>
											</strong></td>

											<td colspan="2">
												<strong> 
												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="true">
													<html:select property="tipoSupressao" style="width: 230px;">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
		
														<logic:present name="colecaoSupressaoTipo" scope="session">
															<html:options collection="colecaoSupressaoTipo" labelProperty="descricao" property="id" />
														</logic:present>
													</html:select> 
												</logic:equal>

												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="false">
													<html:select property="tipoSupressao" style="width: 230px;" disabled="true">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>
		
														<logic:present name="colecaoSupressaoTipo" scope="session">
															<html:options collection="colecaoSupressaoTipo" labelProperty="descricao" property="id" />
														</logic:present>
													</html:select> 
												</logic:equal>

												</strong>
											</td>											

										</tr>

										<tr>
											<td class="style3"><strong>N&uacute;mero do Selo Supress&atilde;o:
												<span class="style2"><strong>
													<font color="#FF0000"></font></strong>
												</span>
											</strong></td>

											<td colspan="2">
												<strong>
												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="true">
													<html:text property="numSeloSupressao" 
														size="12" 
														maxlength="12"/> 
												</logic:equal>

												<logic:equal name="AtualizarLigacaoAguaActionForm" property="habilitaSupressao" value="false">
													<html:text property="numSeloSupressao" 
														size="12" 
														maxlength="12"
														disabled="true"/> 

												</logic:equal>

												</strong>
											</td>
										</tr>

		                                <!-- Leitura Supressão -->
		                                
		                                <tr>
		                                  	<td class="style3"><strong>Leitura Supress&atilde;o:</strong></td>
		                                  	<td>
		                                  		<strong><b><span class="style2">
												<c:choose>
													<c:when test="${hidrometroInstalacaoHistorico.dataRetirada != null}">
		                                    			<html:text property="leituraSupressao" size="10" maxlength="8" disabled="false" />
													</c:when>
													<c:otherwise>
														<html:text property="leituraSupressao" size="10" maxlength="8" disabled="true" />
													</c:otherwise>
												</c:choose>
		                                  		</span></b></strong>
		                                  	</td>
		                                </tr>
		                                
										<tr>
											<td><strong>Existe Lacre?</strong></td>
											<td width="66%" align="right">
												<div align="left">
													<html:radio property="aceitaLacre" 
														tabindex="4" 
														onclick="desabilitaCamposNumeroLacreOnClick();"
														value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
													<strong>Sim</strong> 
													<html:radio property="aceitaLacre" 
														tabindex="5" 
														onclick="desabilitaCamposNumeroLacreOnClick();"
														value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
													<strong>Não</strong>
												</div>
											</td>
										</tr>
										
										<tr>
											<td><strong> Número do Lacre:</strong></td>
											<td>
												<html:text property="numeroLacre" 
													size="9"
													maxlength="9" />
											</td>
										</tr>			                                
		                                <tr>
											<td WIDTH="20%"><strong>Matrícula Funcionário:</strong></td>
											<td width="80%" colspan="3"><html:text property="idFuncionario"
												size="8" maxlength="8" tabindex="1"
												onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirAtualizarLigacaoAguaAction.do', 'idFuncionario', 'Funcionário');" />
											<a 
												href="javascript:abrirPopup('exibirAtualizarLigacaoAguaAction.do?pesquisarFuncionario=OK',400,400);">
											<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
												border="0" height="21" width="23"></a> <logic:present
												name="corFuncionario">
						
												<logic:equal name="corFuncionario" value="exception">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
						
												<logic:notEqual name="corFuncionario" value="exception">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
						
											</logic:present> <logic:notPresent name="corFuncionario">
						
												<logic:empty name="AtualizarLigacaoAguaActionForm"
													property="idFuncionario">
													<html:text property="descricaoFuncionario" value="" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty name="AtualizarLigacaoAguaActionForm"
													property="idFuncionario">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
						
						
											</logic:notPresent> <a href="javascript:limpar();"> <img
												src="<bean:message key='caminho.imagens'/>limparcampo.gif"
												alt="Apagar" border="0"></a></td>
										</tr>
		                                
									</table>
									</td>
								</tr>                            
                            </table></td>
                          </tr>
							<tr>
								<td colspan="2">
								<table width="100%">
			
									<tr>
										<td width="40%" align="left">

											<logic:equal parameter="veioEncerrarOS" value="TRUE" >
												<input type="button"
													name="ButtonCancelar" 
													class="bottonRightCol" 
													value="Voltar"
													onClick="javascript:history.back();'">
											</logic:equal>								
										
											<input type="button" 
												name="ButtonReset" 
												class="bottonRightCol"
												value="Desfazer" 
												onClick="limparForm();"> 
											
											<input type="button" 
												name="ButtonCancelar" 
												class="bottonRightCol" 
												value="Cancelar" 
												onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
										</td>

										<td align="right">
											<input name="Button" 
												type="button"
												class="bottonRightCol" 
												value="Atualizar" 
												onclick="validaForm();">
										</td>

										</tr>
								</table>
								</td>
			
							</tr>

                      </table></td>
                    </tr>

                </table>

					</td>
				</tr>

			</table>
	<!-- Fim do Corpo - Rafael Pinto -->


	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>

</html:html>