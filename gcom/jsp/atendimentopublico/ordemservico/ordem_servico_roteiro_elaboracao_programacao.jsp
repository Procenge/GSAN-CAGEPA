<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="java.math.BigDecimal"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>


<style type="text/css">
.barraRolagem {
	width: 100%;
	overflow: auto;
}

.acrescentarColunaEmTabela {
	width: 15px;
	background-color:#CBE5FE;
}
</style>

<script language="JavaScript">

$(document).ready(function(){
	ajustaHeightDiv('divSelecao', 670);
});

function ajustaHeightDiv(id, maxTamanho){
	var heightDiv = $("#"+ id).height();
	if (heightDiv > maxTamanho) {
		$("#"+ id).css({'height':maxTamanho});
	}
}

 
	function programar(){
   		var form = document.forms[0];

		if(CheckboxNaoVazioMensagemGenerico("Informe pelo menos uma Ordem de Serviço",form.osSelecionada)){
			if(validaForm()){

				var principal = selecionaPrincipal();

				if(principal != false){

					enviarSelectMultiplo('ElaborarOrdemServicoRoteiroCriteriosActionForm','equipesSelecionadas');
	   			
	   				form.action = "programarOrdemServicoRoteiroCriteriosAction.do?tipoAcao=P&principal="+principal;
	   				form.submit();
				}				
			}
		}
    }
	
	function validaForm(){
		
		var form = document.forms[0];
		var retorno = true;
		
		for(indice = 0; indice < form.elements.length; indice++){

			if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {

				var campo = eval('form.dataFinalProgramacaoSelecionadas'+form.elements[indice].value);
				
				if (campo == null || campo.value == ''){

					campo.focus();
					alert("Data Final de Programação deve ser informada");
					retorno = false;
					break;

				}else if( !verificaDataMensagem(campo,"Data Final de Programação inválida") ){

					retorno = false;
					break;

				}else if ( comparaData(campo.value, '<', form.dataRoteiro.value) ){

					alert('Data Final de Programação deve ser igual ou superior a '+form.dataRoteiro.value);
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}
	
	function selecionaPrincipal(){
		var form = document.forms[0];
	    var selecionados = form.elements['equipesSelecionadas'];
	    var retorno;
		var jaSelecionado = false;

		if(selecionados.length == 1){
			retorno = selecionados.options[0].value;
		}else {
			for (i=0; i< selecionados.length; i++) {
				if(selecionados.options[i].selected){
					if(jaSelecionado == false){
						retorno = selecionados.options[i].value;
						jaSelecionado = true;
					}else{
						alert('Selecione somente uma equipe como principal');
						return false;
						break;
					}
				}
			}
		}
		if(jaSelecionado == false && selecionados.length != 1){
			alert('Selecione uma equipe como principal');
			return false;
		}
		return retorno;
	}

	function abrirAlerta(chaveEquipe,idOs){
   		var form = document.forms[0];

		abrirPopup("programarOrdemServicoRoteiroCriteriosAction.do?tipoAcao=A&chaveEquipe="+chaveEquipe+"&idOs="+idOs);
    }

	function remover(idOs){
   		var form = document.forms[0];

   		form.action = "programarOrdemServicoRoteiroCriteriosAction.do?tipoAcao=R&idOs="+idOs;
   		form.submit();
		
    }

	function extendeTabela(tabela,display){
		var form = document.forms[0];

		eval('layerHide'+tabela).style.display = (display == true ? 'none' : 'block');
		eval('layerShow'+tabela).style.display = (display == true ? 'block' : 'none');


		var tamanhoMaximo = 670;
		
		ajustaHeightDiv('divSelecionados'+tabela, tamanhoMaximo);

		var heightDiv = $("#"+ 'divSelecionados'+tabela).height();
		if (heightDiv == tamanhoMaximo) {
			acrescentarColunaEmTabela('tabelaSelecionada'+tabela);
		}
	}

	function acrescentarColunaEmTabela(idTabela) {
		var tabela = document.getElementById(idTabela);
		var linhas = tabela.rows[0];
		var novaColuna = document.createElement('td');
		novaColuna.className = 'acrescentarColunaEmTabela';
		linhas.appendChild(novaColuna);
	}
	
	function consultarOs(idOs){
		var url = 'exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+idOs;
		abrirPopup(url);
	}

	function consultarRa(idRa){
		abrirPopup("exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA="+idRa);
	}
	
	function habilitaBotaoConcluir(){
		var form = document.forms[0];
		if(form.programadas.value != '' && form.programadas.value > 0){
			form.ButtonConcluir.disabled = false;
		}else{
			form.ButtonConcluir.disabled = true;
		}
	}
	
	function concluir(){
   		var form = document.forms[0];

   		form.action = "concluirElaborarOrdemServicoRoteiroCriteriosAction.do";
   		form.submit();
		
    }

	<%--
	function voltar(){
   		var form = document.forms[0];

   		form.action = "exibirElaborarOrdemServicoRoteiroCriteriosAction.do?menu=sim";
   		form.submit();
		
    }
	--%>
	
	function desfazer(){
   		var form = document.forms[0];

   		form.action = "programarOrdemServicoRoteiroCriteriosAction.do?tipoAcao=R";
   		form.submit();
		
    }


	function facilitador(objeto){

		if (objeto.value == "0"){

			objeto.value = "1";
			checkedTodos(false);

		}else{
			objeto.value = "0";
			checkedTodos(true);	
		}
	}
	
	function checkedTodos(checked){

		var form = document.forms[0];
	    var selecionados = form.elements['osSelecionada'];

		if(selecionados.length == null){
			selecionados.checked = checked;
		}else{
			for (i=0; i< selecionados.length; i++) {

				var valor = selecionados[i].value;				
				selecionados[i].checked = checked;
				
			}
		}
	}


	

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:habilitaBotaoConcluir();">
	
<html:form action="/elaborarOrdemServicoRoteiroCriteriosAction.do"
	name="ElaborarOrdemServicoRoteiroCriteriosActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ElaborarOrdemServicoRoteiroCriteriosActionForm"
	method="post">

	<html:hidden property="dataRoteiro" />

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
						<img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Elabora&ccedil;&atilde;o de Roteiro - Programa&ccedil;&atilde;o 
                  	das Ordens de Servi&ccedil;os</td>
					<td width="11">
						<img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para programar as ordens de servi&ccedil;o do roteiro do dia 
                        <strong>
							<bean:write name="ElaborarOrdemServicoRoteiroCriteriosActionForm" property="dataRoteiro"/>
						</strong>,selecione as equipes e as ordens de servi&ccedil;o:
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">

						<tr>
							<td height="4">
							<table width="100%" border="0">
								<tr>
									<td width="200">
									
										<div align="left"><strong>Disponíveis</strong></div>
		
										<html:select property="equipe" size="6" multiple="true" style="width:230px">
											<html:options collection="colecaoEquipes" 
												labelProperty="nome" 
												property="id" />
										</html:select>
									</td>
		
									<td width="5" valign="center"><br>
										<table width="50" align="center">
											<tr>
												<td align="center"><input type="button" class="bottonRightCol"
													onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('ElaborarOrdemServicoRoteiroCriteriosActionForm', 'equipe', 'equipesSelecionadas');"
													value=" &gt;&gt; "></td>
											</tr>
			
											<tr>
												<td align="center"><input type="button" class="bottonRightCol"
													onclick="javascript:MoverDadosSelectMenu1PARAMenu2('ElaborarOrdemServicoRoteiroCriteriosActionForm', 'equipe', 'equipesSelecionadas');"
													value=" &nbsp;&gt;  "></td>
											</tr>
			
											<tr>
												<td align="center"><input type="button" class="bottonRightCol"
													onclick="javascript:MoverDadosSelectMenu2PARAMenu1('ElaborarOrdemServicoRoteiroCriteriosActionForm', 'equipe', 'equipesSelecionadas');"
													value=" &nbsp;&lt;  "></td>
											</tr>
			
											<tr>
												<td align="center"><input type="button" class="bottonRightCol"
													onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('ElaborarOrdemServicoRoteiroCriteriosActionForm', 'equipe', 'equipesSelecionadas');"
													value=" &lt;&lt; "></td>
											</tr>
										</table>
									</td>
		
									<td>
										<div align="left"><strong>Selecionados</strong></div>
										<html:select property="equipesSelecionadas" size="6" multiple="true" style="width:200px">
										</html:select>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					
                    	<tr bgcolor="#99CCFF"> 
                        	<td height="18" colspan="2">
	                        	<div align="center">
	                        	<strong>Ordens de Servi&ccedil;o Selecionadas</strong>
	                        	</div>
                        	</td>
                     	</tr>
                     	
						<tr bgcolor="#cbe5fe"> 
                        	<td colspan="2"> 
                        	<table border="0" width="100%" >
                            	<tr  bgcolor="#99CCFF"> 
                                	<td width="35px" height="10" rowspan="2">
			                              		<div align="center">
												<strong><a href="javascript:facilitador(this)">Todos</a></strong>
			                              		</div>
			                         </td>
			                        <td width="70px" rowspan="2"><strong>Localidade</strong></td> 
			                        <td width="102px" rowspan="2"><strong>Setor<br>Comercial</strong></td>
                                  	<td width="60px" colspan="2"><strong>Dias Atr.</strong></td>
                                  	<td width="17px" rowspan="2"><strong>Pri.</strong></td>
                                  	<!-- <td rowspan="2"></td> -->
                                  	<td width="77px" rowspan="2"><strong>No.<br>do RA</strong></td>
                                  	<td width="28px" rowspan="2"><strong>Serv.</strong></td>
                                  	<td rowspan="2" width="105px"><strong>Endere&ccedil;o</strong></td>
                                  	<td width="125px" rowspan="2"><strong>Dt. Final<br>Program.</strong></td>
                                </tr>

                                <tr  bgcolor="#99CCFF"> 
                                  	<td width="23px"><strong>Clte</strong></td>
                                  	<td width="38px"><strong>Ag.Reg</strong></td>
                                </tr>
							</table>
							<div class="barraRolagem" id="divSelecao">
							<table border="0" width="100%" >	
								<c:set var="countOs" value="0"/>

       							<logic:iterate name="colecaoOSFiltroHelper" id="osHelper">
	                     		  		<c:set var="countOs" value="${countOs+1}"/>
	
		                        		<c:choose>
	                        				<c:when test="${countOs%2 == '1'}">
		                        				<tr bgcolor="#FFFFFF">
		                        			</c:when>
		                        			<c:otherwise>
		                        				<tr bgcolor="#cbe5fe">
		                        			</c:otherwise>
	                        			</c:choose>

                                  		<td width="40px" height="10" align="center"> 
											<input type="checkbox"
												name="osSelecionada" 
												value="<bean:write name="osHelper" property="ordemServico.id" />" />
                                  		</td>
                                  		
                                  		<td width="70px">
                                  			<logic:notEmpty name="osHelper" property="ordemServico.imovel">
												<logic:notEmpty name="osHelper" property="ordemServico.imovel.localidade">
													<div align="center">
														<bean:write name="osHelper" 
															property="ordemServico.imovel.localidade.descricao"/>
													</div>
												</logic:notEmpty>
											</logic:notEmpty>
                                  		</td>
                                  		
                                  		<td width="90px">
                                  			<logic:notEmpty name="osHelper" property="ordemServico.imovel">
												<logic:notEmpty name="osHelper" property="ordemServico.imovel.setorComercial">
													<div align="center" width="50px">
														<bean:write name="osHelper" 
															property="ordemServico.imovel.setorComercial.descricao"/>
													</div>
												</logic:notEmpty>
											</logic:notEmpty>
                                  		</td>

                                  		<td width="27px">
                                  			<div align="center">
                                  				<bean:write name="osHelper" property="diasAtrasoCliente" />
                                  			</div>
                                  		</td>

                                  		<td width="45px">
                                  			<div align="center">
                                  				<bean:write name="osHelper" property="diasAtrasoAgencia" />
                                  			</div>
                                  		</td>

                                  		<td width="21px">
											<logic:notEmpty name="osHelper" property="ordemServico.servicoTipoPrioridadeAtual">
												<div align="center">
													<bean:write name="osHelper" 
														property="ordemServico.servicoTipoPrioridadeAtual.id"/>
												</div>
											</logic:notEmpty>
                                  		</td>
                                  		<!-- <td><div align="center">&nbsp;</div></td> -->
                                  		
                                  		<td width="58px">
											<div align="center">
											   <logic:notEmpty name="osHelper" property="ordemServico.registroAtendimento">
		                        				<a  title="Consultar Dados do RA - Registro de Atendimento" 
		                        					href="javascript:consultarRa('<bean:write name="osHelper" property="ordemServico.registroAtendimento.id" />');">
                            					
                            						<bean:write name="osHelper" property="ordemServico.registroAtendimento.id" />
												</a>
												</logic:notEmpty>												
											</div>

                                  		</td>
                                  		
                                  		<td width="28px">
											<logic:notEmpty name="osHelper" property="ordemServico.servicoTipo">
												<div align="center">
			                        			<a  title="Ordem de Serviço: <bean:write name="osHelper" property="ordemServico.id"/>; Serviço: <bean:write name="osHelper" property="ordemServico.servicoTipo.descricao"/>"
			                        				href="javascript:consultarOs('<bean:write name="osHelper" property="ordemServico.id"/>');">
													
													<bean:write name="osHelper" property="ordemServico.servicoTipo.id"/>
												</a>
												</div>
											</logic:notEmpty>
                                  		</td>

                                  		<td width="87px">
											<logic:notEmpty name="osHelper" property="endereco">
												<bean:write name="osHelper" property="endereco"/>
											</logic:notEmpty>
                                  		</td>

                                  		<td width="83px">
											<input type="text"
												id="data"
												name="dataFinalProgramacaoSelecionadas<bean:write name="osHelper" property="ordemServico.id" />"
												size="8" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);"
												value="<bean:write name="osHelper" property="dataPrevisaoAtual" />"/>
											
											<a href="javascript:abrirCalendario('ElaborarOrdemServicoRoteiroCriteriosActionForm','dataFinalProgramacaoSelecionadas<bean:write name="osHelper" property="ordemServico.id" />');">
												<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" 
													height="15" 
													border="0" 
													alt="Exibir Calendário" 
													tabindex="4"/>
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
                
                <tr> 
                	<td>
                		<div align="left">
	                		<strong>Selecionadas:</strong>
	                    	<strong>
								<html:text property="selecionadas" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="3"
									maxlength="3" />
	                       	</strong>
						</div>
                	</td>
                	<td>
                		<div align="left">
	                		<strong>Programadas:</strong>
	                    	<strong>
								<html:text property="programadas" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="3"
									maxlength="3" />

	                       	</strong>
						</div>
                	</td>
                	
              	</tr>
				
				
				<tr>
					<td height="24">
					</td>
					
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol"
							value="Programar" 
							onClick="javascript:programar();" />
					</td>
					
					<td>&nbsp;</td>
				</tr>

				<!--<tr>
					<td colspan="3">
                  	<table width="100%" border="0" >
                    	<tr bgcolor="#cbe5fe"> 
                      		<td width="27%" rowspan="2" bgcolor="#99CCFF">
                      			<div align="center"><strong>Equipe</strong></div>
                      		</td>
                      		
                      		<td colspan="3" bgcolor="#99CCFF"> 
                      			<div align="center"></div>
		                        <div align="center"></div>
		                        <div align="center"></div>
		                        <div align="center"></div>
                        		<div align="center"><strong>Carga de Tabalho</strong></div>
                        	</td>
                    	</tr>
                    	
                    	<tr bgcolor="#cbe5fe"> 
                      		<td colspan="2" bgcolor="#99CCFF">
                      			<div align="center">
                      			<strong>Prevista</strong>
                      			</div>
                      		</td>
                      		<td width="38%" bgcolor="#99CCFF">
                      			<div align="center">
									<strong>Realizada</strong>
								</div>
							</td>
                    	</tr>
                  	</table>
                  	</td>
                </tr> -->

				<tr bgcolor="#cbe5fe"> 
                  	<td colspan="5">
					
					<logic:iterate name="mapOsProgramacaoHelper" id="osProgramacaoHelper" >

						<bean:define id="chaveEquipe" 
							name="osProgramacaoHelper" 
							property="key" 
							type="String"/>
						
						<bean:define id="idEquipe" 
							name="osProgramacaoHelper" 
							property="key" 
							type="String"/>

						<% chaveEquipe = chaveEquipe.replace("-",""); %>
						<% chaveEquipe = chaveEquipe.replace(" ",""); %>
						<% //chaveEquipe = chaveEquipe.replace("/",""); %>

						<bean:define id="percentualTrabalhoPrevista" 
							name="<%="percentualTrabalhoPrevista"+chaveEquipe%>"
							type="BigDecimal"
							scope="session"/>

						<bean:define id="percentualTrabalhoRealizada" 
							name="<%="percentualTrabalhoRealizada"+chaveEquipe%>"
							type="BigDecimal"
							scope="session"/>
						<logic:present name="<%="qtidadeOS"+chaveEquipe%>" scope="session">
							<bean:define id="qtidadeOs" 
								name="<%="qtidadeOS"+chaveEquipe%>"
								type="Integer"
								scope="session"/>
						</logic:present>	
						<logic:notPresent name="<%="qtidadeOS"+chaveEquipe%>" scope="session">
							<bean:define id="qtidadeOs" 
								value="0"/>
						</logic:notPresent>	
						<logic:present name="<%="cargaTrabalhoPrevistaHoras"+chaveEquipe%>" scope="session">
							<bean:define id="cargaTrabalhoPrevistaHoras" 
								name="<%="cargaTrabalhoPrevistaHoras"+chaveEquipe%>"
								type="String"
								scope="session"/>
						</logic:present>	
						<logic:notPresent name="<%="cargaTrabalhoPrevistaHoras"+chaveEquipe%>" scope="session">
							<bean:define id="cargaTrabalhoPrevistaHoras" 
								value="00:00"
								type="String"/>
						</logic:notPresent>	
							
						

       					
       					<div id="layerHide<%=chaveEquipe%>" class="barraRolagem" style="display:block">

                  			<table border="0" width="100%">
                    			
		                       	<tr  bgcolor="#99CCFF">
	                            	<td width="27%" height="18">
	                            		<div align="center" onMouseOver="javascript:this.style.color='#3366CC';" onMouseOut="javascript:this.style.color=''" style="cursor:hand;cursor:pointer;text-decoration: underline;"
	                            		 onclick="javascript:extendeTabela('<%=chaveEquipe%>',true);">

                     						<b><label><bean:write name="osProgramacaoHelper" property="key"/></label></b>
              
	                            		</div>
	                            	</td>
	                            	
	                            	<td width="10%" height="18">
	                            		<div align="center">

                     						<b><label><bean:write name="qtidadeOs"/></label></b>
              
	                            		</div>
	                            	</td>
	                            	
	                            	<td width="10%" height="18">
	                            		<div align="center">

                     						<b><label><bean:write name="cargaTrabalhoPrevistaHoras"/></label></b>
              
	                            		</div>
	                            	</td>
	                            	
	                            	<td width="30%">
	                            		<div align="center">
											
			                        		<c:choose>
		                        				<c:when test="${percentualTrabalhoPrevista gt 100.00}">
				                               		<input name="cargaTrabalhoPrevista" 
				                               			type="text" 
				                               			disabled 
				                               			style="background-color:red; border:0"
				                               			size="15" 
				                               			maxlength="15">
			                        			</c:when>
			                        			<c:otherwise>
					                        		<c:choose>			                        			
				                        				<c:when test="${percentualTrabalhoPrevista gt 90.00}">
						                               		<input name="cargaTrabalhoPrevista" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:yellow; border:0" 
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:when>
					                        			<c:otherwise>
						                               		<input name="cargaTrabalhoPrevista" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:green; border:0"
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:otherwise>
				                        			</c:choose>
			                        			</c:otherwise>
		                        			</c:choose>
		                               		
		                               		<input name="percentualTrabalhoPrevista"
		                               			type="text" 
		                               			disabled 
		                               			style="background-color:#EFEFEF; border:0" 
		                               			size="6" 
		                               			maxlength="6"
		                               			value="<%=percentualTrabalhoPrevista.toString()%>%">
	                            		</div>
									</td>
	
	                            	<td width="33%">
	                            		<div align="center">
			                        		<c:choose>
		                        				<c:when test="${percentualTrabalhoRealizada gt 100.00}">
				                               		<input name="cargaTrabalhoRealizada" 
				                               			type="text" 
				                               			disabled 
				                               			style="background-color:red; border:0" 
				                               			size="15" 
				                               			maxlength="15">
			                        			</c:when>
			                        			<c:otherwise>
					                        		<c:choose>			                        			
				                        				<c:when test="${percentualTrabalhoRealizada gt 90.00}">
						                               		<input name="cargaTrabalhoRealizada" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:yellow; border:0" 
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:when>
					                        			<c:otherwise>
						                               		<input name="cargaTrabalhoRealizada" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:green; border:0"
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:otherwise>
				                        			</c:choose>				                        			
			                        			</c:otherwise>
		                        			</c:choose>
	                            		
		                               		<input name="percentualTrabalhoRealizada" 
		                               			type="text" 
		                               			disabled 
		                               			style="background-color:#EFEFEF; border:0" 
		                               			size="6" 
		                               			maxlength="6"
		                               			value="<%=percentualTrabalhoRealizada.toString()%>%">
	                            		</div>
									</td>
		                     	</tr>
                    			
                   			</table>
           				</div>
					
                  		<div id="layerShow<%=chaveEquipe%>" class="barraRolagem" style="display:none">

	                  		<table border="0" width="100%">
	                  	
		                       	<tr  bgcolor="#99CCFF">
	                            	<td width="27%" height="18">
	                            		<div align="center" onMouseOver="javascript:this.style.color='#3366CC';" onMouseOut="javascript:this.style.color=''" style="cursor:hand;cursor:pointer;text-decoration: underline;"
	                            		 onclick="javascript:extendeTabela('<%=chaveEquipe%>',false);">

	                   					
	                   						<b><bean:write name="osProgramacaoHelper" property="key"/></b>
	                   			
	                            		</div>
	                            	</td>
	                            	
	                            	<td width="10%" height="18">
	                            		<div align="center">

                     						<b><label><bean:write name="qtidadeOs"/></label></b>
              
	                            		</div>
	                            	</td>
	                            	
	                            	<td width="10%" height="18">
	                            		<div align="center">

                     						<b><label><bean:write name="cargaTrabalhoPrevistaHoras"/></label></b>
              
	                            		</div>
	                            	</td>
	                            	<td width="30%">
	                            		<div align="center">
											
			                        		<c:choose>
		                        				<c:when test="${percentualTrabalhoPrevista gt 100.00}">
				                               		<input name="cargaTrabalhoPrevista" 
				                               			type="text" 
				                               			disabled 
				                               			style="background-color:red; border:0" 
				                               			size="15" 
				                               			maxlength="15">
			                        			</c:when>
			                        			<c:otherwise>
					                        		<c:choose>			                        			
				                        				<c:when test="${percentualTrabalhoPrevista gt 90.00}">
						                               		<input name="cargaTrabalhoPrevista" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:yellow; border:0" 
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:when>
					                        			<c:otherwise>
						                               		<input name="cargaTrabalhoPrevista" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:green; border:0"
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:otherwise>
				                        			</c:choose>				                        			
			                        			</c:otherwise>
		                        			</c:choose>
		                               		
		                               		<input name="percentualTrabalhoPrevista"
		                               			type="text" 
		                               			disabled 
		                               			style="background-color:#EFEFEF; border:0" 
		                               			size="6"
		                               			maxlength="6"
		                               			value="<%=percentualTrabalhoPrevista.toString()%>%">
	                            		</div>
									</td>
	
	                            	<td width="33%">
	                            		<div align="center">
			                        		<c:choose>
		                        				<c:when test="${percentualTrabalhoRealizada gt 100.00}">
				                               		<input name="cargaTrabalhoRealizada" 
				                               			type="text" 
				                               			disabled 
				                               			style="background-color:red; border:0" 
				                               			size="15" 
				                               			maxlength="15">
			                        			</c:when>
			                        			<c:otherwise>
					                        		<c:choose>			                        			
				                        				<c:when test="${percentualTrabalhoRealizada gt 90.00}">
						                               		<input name="cargaTrabalhoRealizada" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:yellow; border:0" 
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:when>
					                        			<c:otherwise>
						                               		<input name="cargaTrabalhoRealizada" 
						                               			type="text" 
						                               			disabled 
						                               			style="background-color:green; border:0"
						                               			size="15" 
						                               			maxlength="15">
					                        			</c:otherwise>
				                        			</c:choose>				                        			
			                        			</c:otherwise>
		                        			</c:choose>
	                            		
		                               		<input name="percentualTrabalhoRealizada" 
		                               			type="text" 
		                               			disabled 
		                               			style="background-color:#EFEFEF; border:0" 
		                               			size="6" 
		                               			maxlength="6"
		                               			value="<%=percentualTrabalhoRealizada.toString()%>%">
	                            		</div>
									</td>
	                            	
		                     	</tr>
								
								<tr bgcolor="#cbe5fe"> 
		                        	<td colspan="6"> 
		                        	<table border="0" width="100%" id="tabelaSelecionada<%=chaveEquipe%>">
		                            	<tr  bgcolor="#99CCFF"> 
			                              
			                            	<td rowspan="2" width="10px">
			                              		<div align="center"><strong>&nbsp;</strong></div>
			                              	</td>
			                              
			                              	<td width="30px" rowspan="2">
			                              		<div align="center"><strong>Seq.</strong></div>
			                              	</td>
			                              	
			                              	<td width="60px" rowspan="2">
			                              		<div align="center"><strong>Local.</strong></div>
			                              	</td>
			                              	
			                              	<td width="80px" rowspan="2">
			                              		<div align="center"><strong>Setor<br>Com.</strong></div>
			                              	</td>
			                              
			                              	<td colspan="2">
			                              		<div align="center"><strong>Dias Atr.</strong></div>
			                              	</td>
	
			                              	<td width="25px" rowspan="2">
			                              		<div align="center"><strong>Pri.</strong></div>
			                              	</td>
	
			                              	<td width="33px" rowspan="2">
			                              		<div align="center"><strong>Sit.</strong></div>
			                              	</td>
	
			                              	<td width="79px" rowspan="2"><div align="center"><strong>No. 
			                                  do RA</strong></div>
			                                </td>
	
			                              	<td width="38px" rowspan="2">
			                              		<strong>Serv.</strong>
			                              	</td>
			                              	<td width="160px" rowspan="2">
			                              		<div align="center"><strong>Endere&ccedil;o</strong></div>
			                              	</td>
			                              	
			                              	<td width="68px" rowspan="2">
			                              		<div align="center"><strong>Dt.Final<br>Program. </strong></div>
			                              	</td>
	
			                              	<td width="20px" rowspan="2">
			                              		<div align="center"><strong>Alta</strong></div>
			                              	</td>
		                                </tr>
		                                
		                                <tr  bgcolor="#99CCFF"> 
		                                  	<td width="25px"><strong>Clte</strong></td>
		                                  	<td width="40px"><strong>Ag.Reg</strong></td>
		                                </tr>
									</table>
									<div class="barraRolagem" id="divSelecionados<%=chaveEquipe%>">
									<table border="0" width="100%" >
										<c:set var="count" value="0"/>
	
	              						<logic:iterate name="osProgramacaoHelper" property="value" id="osProgramacao" >
											
											<bean:define id="idOs" name="osProgramacao" property="ordemServicoProgramacao.ordemServico.id" type="Integer"/>
		                     		  		<c:set var="count" value="${count+1}"/>
		
			                        		<c:choose>
		                        				<c:when test="${count%2 == '1'}">
			                        				<tr bgcolor="#FFFFFF">
			                        			</c:when>
			                        			<c:otherwise>
			                        				<tr bgcolor="#cbe5fe">
			                        			</c:otherwise>
		                        			</c:choose>
	
	                              			<td width="15px" bordercolor="#90c7fc">
	                              				<div align="center">

												<logic:equal name="osProgramacao" property="podeRemover" value="true" >
												<c:choose>
													<c:when test="${osProgramacao.ordemServicoProgramacao.indicadorEquipePrincipal == '1'}">
														<a href="javascript:remover('<%=idOs.toString()%>');">
			                       						<img src="imagens/Error.gif"
			                       							border="0"
			                       							width="14"
			                       							height="14"></a>
													</c:when>

													<c:otherwise>
			                       						<img src="imagens/Error.gif" 
			                       							width="14" 
			                       							height="14">

													</c:otherwise>
												</c:choose>
                           						</logic:equal>
	                           						
	                                  			</div>
	                                  		</td>
	                                  		
	                              			<td width="30px" height="25" bordercolor="#90c7fc">
	                              				<div align="center">
	                                  				<bean:write name="osProgramacao" property="ordemServicoProgramacao.nnSequencialProgramacao" />
	                                  			</div>
	                              			</td>
			                              	
			                              	<td width="50px" height="25" bordercolor="#90c7fc">
	                              				<div align="center">
	                                  				<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.imovel">
														<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.imovel.localidade">
															<div align="center">
																<bean:write name="osProgramacao" 
																	property="ordemServicoProgramacao.ordemServico.imovel.localidade.descricao"/>
															</div>
														</logic:notEmpty>
													</logic:notEmpty>
	                                  			</div>
	                              			</td>
	                              			
	                              			<td width="50px" height="25" bordercolor="#90c7fc">
	                              				<div align="center">
	                                  				<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.imovel">
														<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.imovel.setorComercial">
															<div align="center">
																<bean:write name="osProgramacao" 
																	property="ordemServicoProgramacao.ordemServico.imovel.setorComercial.descricao"/>
															</div>
														</logic:notEmpty>
													</logic:notEmpty>
	                                  			</div>
	                              			</td>
			                              	
	                                  		<td width="25px" bordercolor="#90c7fc">
	                                  			<div align="center">
	                                  				<bean:write name="osProgramacao" property="diasAtrasoCliente" />
	                                  			</div>
	                                  		</td>
	
	                                  		<td width="40px" bordercolor="#90c7fc">
	                                  			<div align="center">
	                                  				<bean:write name="osProgramacao" property="diasAtrasoAgencia" />
	                                  			</div>
	                                  		</td>
	
	                                  		<td width="25px" bordercolor="#90c7fc">
												<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.servicoTipoPrioridadeAtual">
													<div align="center">													
														
													<bean:write name="osProgramacao" 
															property="ordemServicoProgramacao.ordemServico.servicoTipoPrioridadeAtual.id"/>
													</div>
												</logic:notEmpty>
	                                  		</td>
	                                  		
	                                  		<td width="33px" bordercolor="#90c7fc">
	                                  			<div align="center">
	                                  				<bean:write name="osProgramacao" property="situacaoDescricaoAbreviada" />
	                                  			</div>
	                               			</td>
	                                  		
	                                  		<td width="59px">
	                                  			<div align="center">
	                                  				<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.registroAtendimento">
			                        					<a  title="Consultar Dados do RA - Registro de Atendimento" href="javascript:consultarRa('<bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.registroAtendimento.id" />');">
			                                  				<bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.registroAtendimento.id" />
	        		                          			</a>
	                                  				</logic:notEmpty>
                                  				</div>
	                                  		</td>
	                                  		
	                                  		<td width="38px">
												<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.ordemServico.servicoTipo">
													
													<div align="center">
				                        			<a  title="Ordem de Serviço: <bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.id"/>; Serviço: <bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.servicoTipo.descricao"/>"
				                        				href="javascript:consultarOs('<bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.id"/>');">
													
														<bean:write name="osProgramacao" property="ordemServicoProgramacao.ordemServico.servicoTipo.id"/>
													</a>
													</div>
												</logic:notEmpty>
	                                  		</td>
	
	                                  		<td width="140px">
												<logic:notEmpty name="osProgramacao" property="endereco">
													<bean:write name="osProgramacao" property="endereco"/>
												</logic:notEmpty>
	                                  		</td>
			                              	
			                              	<td width="68px" bordercolor="#90c7fc">
												<logic:notEmpty name="osProgramacao" property="ordemServicoProgramacao.programacaoRoteiro.dataRoteiro">
													<bean:write name="osProgramacao" property="ordemServicoProgramacao.programacaoRoteiro.dataRoteiro" format="dd/MM/yyyy"/>
												</logic:notEmpty>
			                              	</td>
			                              	
			                              	<td bordercolor="#90c7fc" width="20px">
			                              		<div align="center"> 
													<logic:equal name="osProgramacao" property="temAlerta" value="true" >

														<a href="javascript:abrirAlerta('<%=idEquipe%>', '<%=idOs.toString()%>');">
				                       						<img src="imagens/alerta.gif" 
				                       							width="14" 
				                       							border="0"
				                       							height="14"></a>

	                           						</logic:equal>
			                              		</div>
			                              	</td>
	
	              						</logic:iterate>
		                                
				                  	</table>
				                  	</div>
				                  	</td>
	                			</tr>
	                  		</table>
	                  	</div>
                    </logic:iterate>
                  	</td>
                  	
                  	
                </tr>
				<tr>
					<td align="right" colspan="2">
						<input type="button" 
							name="Button" 
							class="bottonRightCol"
							value="Voltar" 
							onClick="javascript:history.back();" />
					</td>
				</tr>

				<tr>
					<td height="24">

						<input type="button" 
							name="ButtonDesfazer" 
							class="bottonRightCol" 
							value="Desfazer" 
							onClick="javascript:desfazer();">
					
					
						<input type="button" 
							name="ButtonCancelar" 
							class="bottonRightCol" 
							value="Cancelar" 
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					
					<td align="right">
						<input 
							type="button" 
							name="ButtonConcluir" 
							class="bottonRightCol"
							value="Concluir" 
							onClick="javascript:concluir();" />
					</td>
					
					<td>&nbsp;</td>
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
