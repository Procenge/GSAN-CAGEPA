<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">

function validaCampanha(){
	var form = document.forms[0];
	form.action='ExibirFiltrarCampanhaPremiacaoAction.do?menu=sim';
    form.submit();
}

function validaMudancaDataInicialSorteio(){
	var form = document.forms[0];
	var textoDataInicial = form.dataInicialSorteio.value;
	form.dataFinalSorteio.value = form.dataInicialSorteio.value;
	
// 	verifica_tamanho_data(form.dataInicialSorteio);
// 	verifica_tamanho_data(form.dataFinalSorteio);
	mascara_data(form.dataInicialSorteio);
	mascara_data(form.dataFinalSorteio);

}

function validaMudancaPermiacao(){
	var form = document.forms[0];
	form.action='ExibirFiltrarCampanhaPremiacaoAction.do';
    form.submit();
} 

function validaForm(){
	
	var form = document.forms[0];
	var peloMenosUm = false;
	if(form.idCampanha.value != -1){
		peloMenosUm = true;
	}
	if(form.dataInicialSorteio.value != ''){
		peloMenosUm = true;
		
	}
	if(form.dataFinalSorteio.value != ''){
		peloMenosUm = true;
		
	}
	if(form.idPremio.value != ''){
		peloMenosUm = true;
	}
	
	if(!peloMenosUm){
		alert('Informe pelo menos uma opção de seleção.');
		
		return false;
	}
	
	if(comparaData(form.dataInicialSorteio.value, '>', form.dataFinalSorteio.value)){
		alert('Data Final do Período é anterior à Data Inicial do Período');
		
		return false
	}
	
	form.submit();
	
}

</script>
</head>
<body leftmargin="5" topmargin="5" onload="limpar();">
	<html:form action="/FiltrarCampanhaPremiacaoAction.do?indicadorAtualizar=1"
	   name="FiltrarCampanhaPremiacaoActionForm"
	   type="gcom.gui.cobranca.campanhapremiacao.FiltrarCampanhaPremiacaoActionForm"
	   method="post" >
	  
		<table width="100%" border="0">
			<tr>
				<td>Para filtrar uma Premiação da Campanha, informe os dados abaixo:</td>
					
			</tr>
		</table>
		<br/>
		<table width="100%" border="0">
				
				<tr>
					<td><strong>Campanha:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select onchange="validaCampanha();" property="idCampanha" tabindex="1">
							<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoCampanhas" labelProperty="dsTituloCampanha" property="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Periodo do Sorteio:</strong></td>
					<td >
						
						<html:text 
							onkeyup="validaMudancaDataInicialSorteio();"
							onblur="validaMudancaDataInicialSorteio();"
							property="dataInicialSorteio" size="10" maxlength="10" /> 
							<a href="javascript:abrirCalendarioReplicando('FiltrarCampanhaPremiacaoActionForm', 'dataInicialSorteio', 'dataFinalSorteio')">
							<img border="0" width="16" height="15"
							src="<bean:message key="caminho.imagens"/>calendario.gif"
							width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
							</a>
					
					
						&nbsp;<strong> a </strong>&nbsp;
						<html:text 
						onkeyup="mascara_data(form.dataFinalSorteio);"
						onblur="javascript:verifica_tamanho_data(this);"
						property="dataFinalSorteio" size="10" maxlength="10" />
						<a href="javascript:abrirCalendario('FiltrarCampanhaPremiacaoActionForm', 'dataFinalSorteio')">
							<img border="0" width="16" height="15"
							src="<bean:message key="caminho.imagens"/>calendario.gif"
							width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					</td>
				</tr>
				<tr>
					<td valign="top"><strong>Prêmio:</strong></td>
					<td>
					<html:select property="idPremio" style="width: 350px;"
						multiple="true" tabindex="13">
						<logic:present name="colecaoPremios" scope="session">
						<html:option 
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> 
						
							<html:options collection="colecaoPremios"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>
				</td>
				</tr>
				<logic:present name="indicadorTemPremiacaoPorUnidade">
					<logic:equal name="indicadorTemPremiacaoPorUnidade" value="<%=ConstantesSistema.ATIVO%>">
						<tr>
							<td><strong>Premiação:<font color="#FF0000">*</font></strong></td>
							<td >
		 						 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_DA_UNIDADE%>" /><strong>Da Unidade
								 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_GLOBAL%>" />Global
								 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_AMBAS%>" />Ambas</strong>
		 								
							</td>
						</tr>
					</logic:equal>
				</logic:present>
				
				<logic:present name="indicadorUnidadePremiacao">
					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_GERENCIA_REGIONAL%>">
						<tr>
							<td><strong>Gerência Regional:<font color="#FF0000">*</font></strong></td>
							<td>
								<html:select property="idGerenciaRegional" style="width: 350px;"
									multiple="true" tabindex="13">
									<html:option 
										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoGerenciaRegional" scope="session">
										<html:options collection="colecaoGerenciaRegional"
											labelProperty="nome" property="id" />
									</logic:present>
								</html:select>
							</td>
						</tr>
					</logic:equal>
				
					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_UNIDADE_NEGOCIO%>">
						<tr>
							<td><strong>Unidade de Negócio:<font color="#FF0000">*</font></strong></td>
							<td>
								<html:select property="idUnidadeNegocio" style="width: 350px;"
									multiple="true" tabindex="13">
									<html:option 
										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoUnidadeNegocio" scope="session">
										<html:options collection="colecaoUnidadeNegocio"
											labelProperty="nome" property="id" />
									</logic:present>
								</html:select>
							</td>
						</tr>
					</logic:equal>
				
					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_ELO%>">
						<tr>
							<td><strong>Elo:<font color="#FF0000">*</font></strong></td>
							<td>
								<html:select property="idElo" style="width: 350px;"
									multiple="true" tabindex="13">
									<html:option 
										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoElo" scope="session">
										<html:options collection="colecaoElo"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
							</td>
						</tr>
					</logic:equal>
				
					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_LOCALIDADE%>">
							<tr>
								<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
								<td>
									<html:select property="idLocalidade" style="width: 350px;"
										multiple="true" tabindex="13">
										<html:option 
											value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoLocalidade" scope="session">
											<html:options collection="colecaoLocalidade"
												labelProperty="descricao" property="id" />
										</logic:present>
									</html:select>
								</td>
							</tr>
						</logic:equal>
					</logic:present>
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Limpar" 
										type="button"
										onclick="window.location.href='/gsan/ExibirFiltrarCampanhaPremiacaoAction.do?menu=sim';">
								<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validaForm();" url="FiltrarCampanhaPremiacaoAction.do?indicadorAtualizar=1"/>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>