<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util, gcom.cobranca.prescricao.PrescricaoComando"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">
<!-- Begin -->
	
</script>

</head>

<body leftmargin="5" topmargin="5">

<form method="post">

<table width="764" border="0" cellpadding="0" cellspacing="5">
	<tr>
		<td width="754" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img src="imagens/parahead_left.gif"
					
					border="0" /></td>
				<td class="parabg">Consulta<strong> </strong><font size="-1">Par&acirc;metros
				do Comando de Prescrição</font></td>
				<td width="11"><img src="imagens/parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0">
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td>
					<strong>Título: 
						<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;"> 
							<bean:write name="prescricaoComando" property="titulo" />
						</font>
					</strong>
				</td>
			</tr>
			
			<tr>
				<td>
					<strong>Solicitacao:
						<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;"> 
							<bean:write name="prescricaoComando" property="descricaoSolicitacao" />
						</font>
					</strong>
				</td>
			</tr>
			
			<tr>
				<td>
					<strong>Simulação:
						<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
							<bean:write name="prescricaoComando" property="descricaoIndicadorSimulacao" /> 
						</font>
					</strong>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td width="50%">
					<strong>Dados de Localização Geográfica do Imóvel:</strong>
					<table align="left">
						<tr>
							<td>
								<strong>Ger&ecirc;ncia Regional:</strong>
							</td>
			
							<td>
								<logic:notEmpty name="prescricaoComando" property="gerenciaRegional">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="gerenciaRegional.nome" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>		
			
						<tr>
							<td>
								<strong>Unidade de Neg&oacute;cio:</strong>
							</td>
			
							<td>
								<logic:notEmpty name="prescricaoComando" property="unidadeNegocio">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="unidadeNegocio.nome" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Elo:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="elo">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="elo.descricao" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>		
			
						<tr>
							<td><strong>Localidade Inicial:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="localidadeInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="localidadeInicial.descricaoParaRegistroTransacao" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Setor Comercial Inicial:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="codigoSetorComercialInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="codigoSetorComercialInicial" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Quadra Inicial:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="numeroQuadraInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="numeroQuadraInicial" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Localidade Final:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="localidadeFinal">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="localidadeFinal.descricaoParaRegistroTransacao" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Setor Comercial Final:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="codigoSetorComercialFinal">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="codigoSetorComercialFinal" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td><strong>Quadra Final:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="numeroQuadraFinal">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="numeroQuadraFinal" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td width="50%">
					<strong>Dados do Cliente:</strong>
					<table align="left">
						<tr>
							<td><strong>Cliente:</strong></td>
							
							<td>
								<logic:notEmpty name="prescricaoComando" property="cliente">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="cliente.descricaoParaRegistroTransacao" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Tipo da Relação:</strong>
							</td>
			
							<td>
								<logic:notEmpty name="prescricaoComando" property="clienteRelacaoTipo">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write name="prescricaoComando" property="clienteRelacaoTipo.descricao" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Per&iacute;odo da Relação:</strong>
							</td>
						
							<td>
								<logic:notEmpty name="prescricaoComando" property="dataRelacaoClienteInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
										<bean:write
											name="prescricaoComando"
											format="dd/MM/yyyy HH:mm:ss" property="dataRelacaoClienteInicial" /> 
										</font>
										 &nbsp;&nbsp;a&nbsp;&nbsp;
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
										<bean:write
											name="prescricaoComando"
											format="dd/MM/yyyy HH:mm:ss" property="dataRelacaoClienteFinal" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td>
					<strong>Arquivo de Imóveis :</strong>
					
					<logic:present name="matriculasImoveisComando">
						<input type="text" name="matriculas" value="<bean:write name="matriculasImoveisComando" scope="session" />" 
								size="115" disabled="disabled" style="text-align: left;"/>
					</logic:present>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td width="30%">
					<strong>Características dos Imóveis:</strong>
					<table align="left">
						<tr>
							<td>
								<strong>Categoria:</strong>
							</td>
			
							<td>
								<strong>
								<select style="width: 230px;" multiple="multiple" style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
									<logic:present name="colecaoCategoriaComando" scope="session">
										<logic:iterate name="colecaoCategoriaComando" id="categoria">
											<option><bean:write name="categoria" property="descricao" /></option>
										</logic:iterate>
									</logic:present>
								</select> 													
								</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Subcategoria:</strong>
							</td>
			
							<td>
								<strong>
								<select style="width: 230px;" multiple="multiple" style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
									<logic:present name="colecaoSubcategoriaComando" scope="session">
										<logic:iterate name="colecaoSubcategoriaComando" id="subcategoria">
											<option><bean:write name="subcategoria" property="descricao" /></option>
										</logic:iterate>
									</logic:present>
								</select> 													
								</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Situação da Ligação de Água:</strong>
							</td>
			
							<td>
								<strong> 
								<select style="width: 230px;" multiple="multiple" style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
							
									<logic:present name="colecaoLigacaoAguaSituacaoComando" scope="request">
										<logic:iterate name="colecaoLigacaoAguaSituacaoComando" id="ligacaoAguaSituacao">
											<option><bean:write name="ligacaoAguaSituacao" property="descricao" /></option>
										</logic:iterate>
									</logic:present>
								</select>														
								</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Situação da Ligação de Esgoto:</strong>
							</td>
			
							<td>
								<strong> 
								<select style="width: 230px;" multiple="multiple" style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
							
									<logic:present name="colecaoLigacaoEsgotoSituacaoComando" scope="request">
										<logic:iterate name="colecaoLigacaoEsgotoSituacaoComando" id="ligacaoEsgotoSituacao">
											<option><bean:write name="ligacaoEsgotoSituacao" property="descricao" /></option>
										</logic:iterate>
									</logic:present>
								</select>													
								</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Situações de Cobrança que Impedem a Prescrição:</strong>
							</td>
			
							<td>
								<strong> 
								<select style="width: 230px;" multiple="multiple" style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
							
									<logic:present name="colecaoCobrancaSituacaoComando" scope="request">
										<logic:iterate name="colecaoCobrancaSituacaoComando" id="cobrancaSituacao">
											<option><bean:write name="cobrancaSituacao" property="descricao" /></option>
										</logic:iterate>
									</logic:present>
								</select>												
								</strong>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td>
					<strong>Dados do Débito:</strong>
					<table align="left">
					
						<tr>
							<td>
								<strong>Per&iacute;odo de Referência do Débito:</strong>
							</td>
						
							<td>
								<logic:notEmpty name="prescricaoComando" property="anoMesReferenciaDebitoInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write
												name="prescricaoComando"
												property="anoMesReferenciaDebitoInicialFormatado" /> 
										</font>
										 &nbsp;&nbsp;a&nbsp;&nbsp;
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
											<bean:write
												name="prescricaoComando"
												property="anoMesReferenciaDebitoFinalFormatado" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Per&iacute;odo de Vencimento do Débito:</strong>
							</td>
						
							<td>
								<logic:notEmpty name="prescricaoComando" property="dataVencimentoDebitoInicial">
									<Strong>
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
										<bean:write
											name="prescricaoComando"
											format="dd/MM/yyyy" property="dataVencimentoDebitoInicial" /> 
										</font>
										 &nbsp;&nbsp;a&nbsp;&nbsp;
										<font style="background-color:#EFEFEF; border:0; color: #000000; font-weight: lighter;">
										<bean:write
											name="prescricaoComando"
											format="dd/MM/yyyy" property="dataVencimentoDebitoFinal" />
										</font>
									</Strong>
								</logic:notEmpty>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			
			<tr>
				<td height="17">
				<div align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Voltar"
					onClick="javascript:history.back();"></div>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<p>&nbsp;</p>
		</td>
	</tr>
</table>
</form>
</body>
</html:html>
