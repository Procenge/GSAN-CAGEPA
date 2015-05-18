
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

<!--

function fechar(){
		window.close();
-->

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
		eval('layerHide'+tabela).style.display = 'none';
		eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
		eval('layerShow'+tabela).style.display = 'none';
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelRegistroAtendimento.value = codigoRegistro;
      form.matriculaImovelRegistroAtendimento.value = descricaoRegistro;
      form.matriculaImovelRegistroAtendimento.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelRegistroAtendimentoAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelRegistroAtendimentoAction&limparForm=OK'
	form.submit();
}
	
function abrirInserirRegistroAtendimento(){
	var form = document.forms[0];
    
    if (form.idImovelRegistroAtendimento.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirInserirRegistroAtendimentoAction.do?menu=nao', 400, 800, 'InserirRegistroAtendimento', 'yes');
	}	
}
function abrirManterRegistroAtendimentoIncompleto(){
    
	abrirPopupDeNome('exibirFiltrarRegistroAtendimentoIncompletoAction.do?menu=nao&apareceMenu=nao', 400, 800, '', 'yes');
}
	
function limparImovelTecla() {

	var form = document.forms[0];
	
	form.matriculaImovelRegistroAtendimento.value = "";

	if (form.digitoVerificadorImovelRegistroAtendimento != undefined) {
		form.digitoVerificadorImovelRegistroAtendimento.value = "";
	}
		
	form.situacaoAguaRegistroAtendimento.value = "";
	form.situacaoEsgotoRegistroAtendimento.value = "";
	form.tipoLigacao.value = "";

}	
	
	
</script>


</head>
<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelRegistroAtendimento')">


<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=10" />
	<logic:present name="montarPopUp">
	 <table width="800" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->		
	
	</logic:present>
	
	<logic:notPresent name="montarPopUp">	
	
		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>	
		
		<table width="800" border="0" cellspacing="5" cellpadding="0">
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
			
			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
	
	</logic:notPresent>
		<p>&nbsp;</p>

		<!-- Início do Corpo - Fernanda Paiva  07/02/2006  -->
		<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelRegistroAtendimento" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelRegistroAtendimentoAction&indicadorNovo=OK&limparForm=S','idImovelRegistroAtendimento','Im&oacute;vel');"
										onkeyup="limparImovelTecla();"/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelRegistroAtendimentoNaoEncontrado" scope="request">
										
										<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
											<html:text property="matriculaImovelRegistroAtendimento" size="40"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:equal>
										
										<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
											<html:text property="digitoVerificadorImovelRegistroAtendimento" size="2"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
												
											<html:text property="matriculaImovelRegistroAtendimento" size="31"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />												
										</logic:equal>										
										
									</logic:present> <logic:notPresent
										name="idImovelRegistroAtendimentoNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelRegistroAtendimento"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
												<html:text property="matriculaImovelRegistroAtendimento"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>
											
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelRegistroAtendimento"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
													
												<html:text property="matriculaImovelRegistroAtendimento"
													size="31" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />													
											</logic:equal>											
											
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelRegistroAtendimento"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">											
												<html:text property="matriculaImovelRegistroAtendimento"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>
											
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelRegistroAtendimento"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
																																			
												<html:text property="matriculaImovelRegistroAtendimento"
													size="31" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>											
												
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td><html:text property="situacaoAguaRegistroAtendimento"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoRegistroAtendimento" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>	
								</tr>
									<tr>
									<td height="10">
										<div class="style9"><strong>Tipo de Ligação:</strong></div>
									</td>
									<td><html:text property="tipoLigacao"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" />
									</td>
									<td width="90"></td>
									<td width="120"></td>
								</tr>								
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>
				
				
				<tr bgcolor="#cbe5fe">
					<td align="center" colspan="2">
						
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="6" align="center">
										 
												<b>Dados Gerais do Registros de Atendimento</b>
											
									</td>
								</tr>
								<tr bgcolor="#cbe5fe" bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Número do RA</strong></div>
									</td>
		
									<td width="20%" bgcolor="#90c7fc">
										<div align="center"><strong>Tipo da Solicitação</strong></div>
									</td>
									<td width="30%" bgcolor="#90c7fc">
										<div align="center"><strong>Especificação</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Data de Atendimento</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Situação</strong></div>							
									</td>
									
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Unidade Atendimento</strong></div>							
									</td>
									
								</tr></table>
								<div style="width: 100%; height: 150; overflow: auto;">
								<table width="100%" border="0" bgcolor="#99CCFF">
								<logic:present name="colecaoConsultarImovelRegistroAtendimentoHelper">
									<%int cont = 0;%>
									<logic:iterate name="colecaoConsultarImovelRegistroAtendimentoHelper" id="consultarImovelRegistroAtendimentoHelper">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else { %>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%" align="center"><a 
											href="javascript:abrirPopup('exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA='+${consultarImovelRegistroAtendimentoHelper.idRegistroAtendimento}, 400, 800);">
											${consultarImovelRegistroAtendimentoHelper.idRegistroAtendimento}</a></td>
											<td width="20%" align="center">${consultarImovelRegistroAtendimentoHelper.tipoSolicitacao}</td>
											<td width="33%" align="left">${consultarImovelRegistroAtendimentoHelper.especificacao}</td>
											<td width="10%" align="center">${consultarImovelRegistroAtendimentoHelper.dataAtendimento}</td>
											<td width="10%" align="center">${consultarImovelRegistroAtendimentoHelper.situacao}</td>	
											<td width="10%" align="center">${consultarImovelRegistroAtendimentoHelper.unidadeAtendimento}</td>								
										</tr>
									</logic:iterate>
								</logic:present>
							</table>
						</div>
					</tr>
					<tr>
						<td>
							 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div id="layerHideOrdensServico" style="display:block">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td height="18" colspan="4" align="center">
											<span class="style2"> 
												<a href="javascript:extendeTabela('OrdensServico',true);" /> 
													<b>Dados das Ordens de Serviço</b>
												</a>
											</span>
										</td>
									</tr>
								</table>
							</div>
							<div id="layerShowOrdensServico" style="display:none">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td height="18" colspan="4" align="center">
											<span class="style2"> 
												<a href="javascript:extendeTabela('OrdensServico',false);" /> 
													<b>Dados das Ordens de Serviço</b>
												</a>
											</span>
										</td>
									</tr>
									<tr bordercolor="#FFFFFF" bgcolor="#79BBFD"> 
		                  				<td width="10%"><div align="center"><strong>N&uacute;mero da Ordem de Servi&ccedil;o</strong></div></td>
		                  				<td width="52%"><div align="center"><strong>Tipo de Servi&ccedil;o</strong></div></td>
		                  				<td width="18%"><div align="center"><strong>Data da Gera&ccedil;&atilde;o</strong></div></td>
		                  				<td width="20%"><div align="center"><strong>Situa&ccedil;&atilde;o</strong></div></td>
		                			</tr>
								</table>
								<div style="width: 100%; height: 100; overflow: auto;">
									<table width="100%" align="center" bgcolor="#99CCFF">
										<c:set var="count" value="0"/>
											<logic:present name="ConsultarImovelActionForm" property="colecaoOS">
												<logic:iterate id="os" name="ConsultarImovelActionForm" property="colecaoOS" type="gcom.atendimentopublico.ordemservico.OrdemServico" scope="session">
					  			                	<c:set var="count" value="${count+1}"/>
									                	<c:choose>
									                    	<c:when test="${count%2 == '1'}">
									                        	<tr bgcolor="#FFFFFF">
									                        </c:when>
									                        <c:otherwise>
									                        	<tr bgcolor="#cbe5fe">
									                        </c:otherwise>
									                	</c:choose>
												        <td bordercolor="#90c7fc" width="10%">
												        	<div align="center">
												            	<bean:write name="os" property="id"/>
												            </div>
												        </td>
												        <td bordercolor="#90c7fc" width="54%">
												        	<div align="left">
										                    	<a href="javascript:abrirPopup('exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+${os.id}, 400, 800);">
											                    	<bean:write name="os" property="servicoTipo.descricao"/>
												                </a>
												           	</div>
												       	</td>
												      	<td bordercolor="#90c7fc" width="18%">
												         	<div align="center">								                        
										                      	<bean:write name="os" property="dataGeracao" format="dd/MM/yyyy"/>
												         	</div>
												       	</td>
												       	<td bordercolor="#90c7fc">
												         	<div align="left">
												              	<c:choose>
												                  	<c:when test="${os.situacao == 1}">
												                    	PENDENTE
												                 	</c:when>
												                   	<c:when test="${os.situacao == 2}">
												                      	ENCERRADO
												                   	</c:when>
												                  	<c:when test="${os.situacao == 3}">
												                     	EXECU&Ccedil;&Atilde;O EM ANDAMENTO 
												                  	</c:when>
												                  	<c:when test="${os.situacao == 4}">
												                       	AGUARDANDO LIBERA&Ccedil;&Atilde;O PARA EXECU&Ccedil;&Atilde;O
												                   	</c:when>
												            	</c:choose>
												          	</div>
												     	</td>
												    </tr>
										      	</logic:iterate>
									      	</logic:present>
							       	</table>
						       	</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							 &nbsp;
						</td>
					</tr>
					<tr>
					<td>
						<div id="layerHideComentarios" style="display:block">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="4" align="center">
										<span class="style2"> 
											<a href="javascript:extendeTabela('Comentarios',true);"> 
												<b>Comentários do Imóvel</b>
											</a>
										</span>
									</td>
								</tr>
							</table>
						</div>
						<div id="layerShowComentarios" style="display:none">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="4" align="center">
										<span class="style2"> 
											<a href="javascript:extendeTabela('Comentarios',false);"> 
												<b>Comentários do Imóvel</b>
											</a>
										</span>
									</td>
								</tr>
								<tr bordercolor="#000000">
									<td width="50%" bgcolor="#90c7fc">
										<div align="center"><strong>Comentário</strong></div>
									</td>
									
									<td width="12%" bgcolor="#90c7fc">
										<div align="center"><strong>Sequencial Inc.</strong></div>
									</td>
		
									<td width="12%" bgcolor="#90c7fc">
										<div align="center"><strong>Data Inclusão</strong></div>
									</td>
								
									<td width="26%" bgcolor="#90c7fc">
										<div align="center"><strong>Usuário</strong></div>
									</td>
								</tr>
								<logic:present name="colecaoImovelComentarioHelper">
								<%int cont = 0;%>
								<logic:iterate name="colecaoImovelComentarioHelper" id="imovelComentarioHelper">
									<%cont = cont + 1;
									if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
										<%} else {
	
									%>
									<tr bgcolor="#FFFFFF">
									<%}%>
										<td width="50%" align="left">${imovelComentarioHelper.descricao}</td>
										<td width="12%" align="center">${imovelComentarioHelper.sequencialInclusao}</td>
										<td width="12%" align="center">${imovelComentarioHelper.dataInclusao}</td>
										<td width="26%" align="center">${imovelComentarioHelper.usuario}</td>		
									</tr>
								</logic:iterate>
								</logic:present>
									
							</table>
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td>
						 &nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<div id="layerHideAtendimentos" style="display:block">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="4" align="center">
										<span class="style2"> 
											<a href="javascript:extendeTabela('Atendimentos',true);" /> 
												<b>Atendimentos</b>
											</a>
										</span>
									</td>
								</tr>
							</table>
						</div>
						<div id="layerShowAtendimentos" style="display:none">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="4" align="center">
										<span class="style2"> 
											<a href="javascript:extendeTabela('Atendimentos',false);" /> 
												<b>Atendimentos</b>
											</a>
										</span>
									</td>
								</tr>
								<tr bordercolor="#FFFFFF" bgcolor="#79BBFD"> 
	                  				<td width="32%"><div align="center"><strong>Procedimento de Atedimento</strong></div></td>
	                  				<td width="32%"><div align="center"><strong>Funcionalidade</strong></div></td>
	                  				<td width="18%"><div align="center"><strong>Usuário</strong></div></td>
	                  				<td width="18%"><div align="center"><strong>Data Atendimento</strong></div></td>
	                			</tr>
							</table>
							<div style="width: 100%; height: 100; overflow: auto;">
								<table width="100%" align="center" bgcolor="#99CCFF">
									<c:set var="count" value="0"/>
										<logic:present name="ConsultarImovelActionForm" property="colecaoAtendimento">
											<logic:iterate id="atendimento" name="ConsultarImovelActionForm" property="colecaoAtendimento" type="gcom.cadastro.atendimento.Atendimento" scope="session">
				  			                	<c:set var="count" value="${count+1}"/>
								                	<c:choose>
								                    	<c:when test="${count%2 == '1'}">
								                        	<tr bgcolor="#FFFFFF">
								                        </c:when>
								                        <c:otherwise>
								                        	<tr bgcolor="#cbe5fe">
								                        </c:otherwise>
								                	</c:choose>
											        <td bordercolor="#90c7fc" width="32%">
											        	<div align="center">
									                    	<a href="javascript:abrirPopup('exibirConsultarDadosAtendimentoPopupAction.do?idAtendimento='+${atendimento.id}, 400, 800);">
										                    	<bean:write name="atendimento" property="atendimentoProcedimento.descricao"/>
											                </a>											        	
											            </div>
											        </td>
											        <td bordercolor="#90c7fc" width="32%">
											        	<div align="center">
											            	<bean:write name="atendimento" property="atendimentoProcedimento.funcionalidade.descricao"/>
											           	</div>
											       	</td>
											      	<td bordercolor="#90c7fc" width="18%">
											         	<div align="center">
											         		<bean:write name="atendimento" property="usuario.login"/>
											          	</div>											      	
											       	</td>
											       	<td bordercolor="#90c7fc" width="18%">
											         	<div align="center">								                        
									                      	<bean:write name="atendimento" property="dataInicioAtendimento" format="dd/MM/yyyy HH:mm"/>
											         	</div>											       	
											     	</td>
											    </tr>
									      	</logic:iterate>
								      	</logic:present>
						       	</table>
					       	</div>
						</div>
					</td>
				</tr>				
				
			</table>
		<p>&nbsp;</p>
		
	  <logic:notPresent name="montarPopUp">
		<table width="100%" border="0">
			<tr>
				<td>
					<div align="left">
						<a href="javascript:abrirInserirRegistroAtendimento();">
								<strong>Inserir RA - Registro de Atendimento</strong>
					 	</a>
					</div> 	
				</td>
			</tr>
		
		
			<tr>
				<td>
					<div align="left">
						<a href="javascript:abrirManterRegistroAtendimentoIncompleto();">
								<strong>Manter RA - Registro de Atendimento Incompleto</strong>
					 	</a>
					</div> 	
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=10" />
					</div>
				</td>
			</tr>
			
			
		</table>
		</logic:notPresent>
		</td>
	</tr>
</table>
<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_registro_atendimento.jsp -->
</html:html>
