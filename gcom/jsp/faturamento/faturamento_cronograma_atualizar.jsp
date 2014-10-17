<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FaturamentoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
function validaDataCompleta(data, event){
		if(mascaraData(data, event)){
			return false;
		}
}
</script>

<script language="JavaScript">
function validaData(form){
  var form = document.FaturamentoActionForm;
  var mesAno = form.mesAno.value;
  if(mascaraAnoMes(mesAno)){
     return false;
  }
}

function validarForm(){
	var form = document.FaturamentoActionForm;
	if(validateFaturamentoActionForm(form) && validarCamposDinamicos(form)){
		submeterFormPadrao(form);
	}
}

function validarCamposDinamicos(form){
 
 	var camposValidos = true;
 
 	
 	if(!verificaAnoMes(form.mesAno)){
		camposValidos = false;
	}
 	if(camposValidos == true){
	 	for (i=0; i < form.elements.length; i++) {
	    	
	    	if (form.elements[i].type == "text" && form.elements[i].id.length > 1){
	    		if(form.elements[i].value != ""){	
					if(form.elements[i].id == "data"){
						if(!verificaData(form.elements[i])){
							camposValidos = false;
							break;
						}
					}
				}else{
					camposValidos = true;
				}
			}
		}
	}
		
	return camposValidos;
}

function habilitaCampo(form){
	form.mesAno.disabled = "false";
	return true;
}

function desabilitaCampo(form){
	form.mesAno.disabled = "true";
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="desabilitaCampo(document.forms[0]);">
<html:form action="/atualizarFaturamentoCronogramaAction"
	name="FaturamentoActionForm"
	type="gcom.gui.faturamento.FaturamentoActionForm" method="post"
	onsubmit="return habilitaCampo(this) && validarCamposDinamicos(this) && validateFaturamentoActionForm(this);">

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


			<td width="600" valign="top" class="centercoltext"><!--In&iacute;cio Tabela Reference a P&aacute;gina&ccedil;&atilde;o da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
			       <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                   <td class="parabg">Atualizar Cronograma de Faturamento</td>
                   <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
                </tr>
			</table>
			<!--Fim Tabela Reference a P&aacute;gina&ccedil;&atilde;o da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para atualizar o(s) cronograma(s) de faturamento,
					informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
				</table>
				<table width="100%" border="0">
				<tr>
					<td><strong>Grupo:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idGrupoFaturamento" disabled="true">
						<html:options collection="faturamentoGrupos"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Mês/Ano:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="7" property="mesAno" size="7"/> &nbsp; mm/aaaa
						</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><font color="#FF0000">*</font> Campo
					Obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
					<td></td>
				</tr>

			</table>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td height="0">
					<table width="100%" bgcolor="#99CCFF">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF">
							<td width="8%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									Comandar
								</strong>
								</font>
							</div>
							</td>
							<td width="23%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									Atividade
								</strong>
								</font>
							</div>
							</td>
							<td width="22%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									Predecessora
								</strong>
								</font>
							</div>
							</td>
							<td width="8%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Obrigat&oacute;ria</strong>
								</font>		
							</div>
							</td>
							<td width="20%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									Data Prevista
								</strong>
								</font>
							</div>
							</td>
							<td width="20%" bgcolor="#90c7fc" align="center">
							<div align="center">
								<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									Data Realização
								</strong>
								</font>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<logic:present name="colecaoFaturamentoAtividadeCronograma">
					<tr>
						<td height="126">
						<div style="width: 100%; height: 100%; overflow: auto;">
						<table width="100%" align="center" bgcolor="#99CCFF">
							<!--corpo da segunda tabela-->
							<%int cont = 0;%>
							<logic:notEmpty name="colecaoFaturamentoAtividadeCronograma">
								<logic:iterate name="colecaoFaturamentoAtividadeCronograma"
									id="faturamentoAtividadeCronograma">
									<%cont = cont + 1;
			if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">									
										<%} else {

			%>
									<tr bgcolor="#FFFFFF">

										<%}%>
										
										<td width="9%">
											<logic:empty name="faturamentoAtividadeCronograma" property="dataRealizacao">
												<div align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<logic:present name="faturamentoAtividadeCronograma" property="faturamentoAtividade.indicadorPossibilidadeComandoAtividade">
						                          	  		<logic:equal name="faturamentoAtividadeCronograma" property="faturamentoAtividade.indicadorPossibilidadeComandoAtividade" value="1">
						                                   		<logic:present name="faturamentoAtividadeCronograma" property="comando">
																	<input type="checkbox" name="c${faturamentoAtividadeCronograma.faturamentoAtividade.id}" value="1" checked="checked"/>
																</logic:present>
																<logic:notPresent name="faturamentoAtividadeCronograma" property="comando">
																	<input type="checkbox" name="c${faturamentoAtividadeCronograma.faturamentoAtividade.id}" value="1"/>
																</logic:notPresent>
		            					                	</logic:equal>
	                            						</logic:present>
													</font>
												</div>
											</logic:empty>
											<logic:notEmpty name="faturamentoAtividadeCronograma" property="dataRealizacao">
												<div align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<logic:present name="faturamentoAtividadeCronograma" property="faturamentoAtividade.indicadorPossibilidadeComandoAtividade">
						                          	  		<logic:equal name="faturamentoAtividadeCronograma" property="faturamentoAtividade.indicadorPossibilidadeComandoAtividade" value="1">
						                                   		<logic:present name="faturamentoAtividadeCronograma" property="comando">
																	<input type="checkbox" name="c${faturamentoAtividadeCronograma.faturamentoAtividade.id}" value="1" checked="checked"/>
																</logic:present>
																<logic:notPresent name="faturamentoAtividadeCronograma" property="comando">
																	<input type="checkbox" name="c${faturamentoAtividadeCronograma.faturamentoAtividade.id}" value="1"/>
																</logic:notPresent>
		            					                	</logic:equal>
	                            						</logic:present>
													</font>
												</div>
											</logic:notEmpty>
										</td>
										
										<td width="23%">
										<div align="left">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												${faturamentoAtividadeCronograma.faturamentoAtividade.descricao}&nbsp;
											</font>
										</div>
										</td>
										<td width="23%">
										<div align="left"><logic:present name="faturamentoAtividadeCronograma"
											property="faturamentoAtividade.faturamentoAtividadePrecedente">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="faturamentoAtividadeCronograma"
													property="faturamentoAtividade.faturamentoAtividadePrecedente.descricao">
												<bean:write name="faturamentoAtividadeCronograma"
													property="faturamentoAtividade.faturamentoAtividadePrecedente.descricao" />
											</logic:present>
											</font>
										</logic:present> <logic:notPresent name="faturamentoAtividade"
											property="faturamentoAtividade.faturamentoAtividadePrecedente">
                                   &nbsp;
                                  </logic:notPresent></div>
										</td>
										<td width="8%">
										<div align="center"><logic:equal name="faturamentoAtividadeCronograma"
											property="faturamentoAtividade.indicadorObrigatoriedadeAtividade"
											value="1">
										<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
                                      SIM
                                      	</font>
                                    </logic:equal> <logic:notEqual
											name="faturamentoAtividadeCronograma"
											property="faturamentoAtividade.indicadorObrigatoriedadeAtividade"
											value="1">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
                                      NÃO
                                      		</font>
                                    </logic:notEqual></div>
										</td>
										<td width="20%">
										<div align="center">
										<logic:empty name="faturamentoAtividadeCronograma"
											property="dataRealizacao">
											<input type="text" maxlength="10"
											name="n<bean:write name="faturamentoAtividadeCronograma" property="faturamentoAtividade.id"/>"
											size="10"
											value="<bean:write name="faturamentoAtividadeCronograma" property="dataPrevista" formatKey="date.format"/>" 
											onkeypress="javascript:validaDataCompleta(this, event)"
											id="data"/>
										
											<a
												href="javascript:abrirCalendario('FaturamentoActionForm', 'n<bean:write name="faturamentoAtividadeCronograma" property="faturamentoAtividade.id"/>')">
											<img border="0"
												src="<bean:message key="caminho.imagens"/>calendario.gif"
												width="20" border="0" align="absmiddle"
												title="Exibir Calendário" /> </a>
										</logic:empty>
										<logic:notEmpty name="faturamentoAtividadeCronograma" property="dataRealizacao">
											<input type="text" maxlength="10"
											name="n<bean:write name="faturamentoAtividadeCronograma" property="faturamentoAtividade.id"/>"
											size="8"
											style="background-color:#EFEFEF; border:0" readonly="true"
											value="<bean:write name="faturamentoAtividadeCronograma" property="dataPrevista" formatKey="date.format"/>" 
											onkeypress="javascript:validaDataCompleta(this, event)"
											id="data"/>
										</logic:notEmpty>
										</div>
										</td>
										<td width="18%" align="center">
										   <logic:present name="faturamentoAtividadeCronograma" property="dataRealizacao">
											<logic:notEmpty name="faturamentoAtividadeCronograma" property="dataRealizacao">
												<bean:write name="faturamentoAtividadeCronograma" property="dataRealizacao" formatKey="date.format"/>
											</logic:notEmpty>
											<logic:empty name="faturamentoAtividadeCronograma" property="dataRealizacao">
												&nbsp;
											</logic:empty>
										   </logic:present>
										   <logic:notPresent name="faturamentoAtividadeCronograma" property="dataRealizacao">
												&nbsp;
										   </logic:notPresent>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						</div>
						</td>
					</tr>
				</logic:present>
			</table>
			<p>
			<table width="100%">
				<tr>
					<td>
						<logic:present name="voltar">
							<logic:equal name="voltar" value="filtrar">
								<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirFiltrarFaturamentoCronogramaAction.do"/>'" align="left" style="width: 80px;">
							</logic:equal>
							<logic:equal name="voltar" value="manter">
								<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirManterFaturamentoCronogramaAction.do"/>'" align="left" style="width: 80px;">
							</logic:equal>
						</logic:present>
						<logic:notPresent name="voltar">
							<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirManterFaturamentoCronogramaAction.do"/>'" align="left" style="width: 80px;">
						</logic:notPresent>
						<input name="button" type="button" class="bottonRightCol" value="Desfazer" onclick="document.forms[0].reset();" align="left" style="width: 80px;">
					</td>
					<td width="30%" align="right">
					<gcom:controleAcessoBotao name="Button" value="Atualizar"
							  onclick="javascript:validarForm();" url="atualizarFaturamentoCronogramaAction.do"/>
                   	<%--
					<html:submit
						styleClass="bottonRightCol" value="Atualizar" property="adicionar" />--%>
						</td>
				</tr>
			</table>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
