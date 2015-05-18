<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="SimularCalculoContaDadosReaisFiltrarActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">
	
	function validarFormulario(){
		
		var form = document.forms[0];
		
		if(validateSimularCalculoContaDadosReaisFiltrarActionForm(form)){
		
			form.action = "/gsan/simularCalculoContaDadosReaisFiltrarAction.do";
			submeterFormPadrao(form);
		}
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){
		
		var form = document.forms[0];
		
		if (objeto == null || codigoObjeto == null){
     		
			if(tipo == "" ){
      			abrirPopup(url,altura, largura);
     		}else{
	  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	 		}
 		}else{
 			
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}else{
				
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
			}
		}
		
	}
	
  	function limpar(){

  		var form = document.forms[0];
  		
  		form.idImovel.value = '';
    	form.inscricaoImovel.value = '';
    	form.periodoReferenciaFaturamentoInicial.value = '';
    	form.periodoReferenciaFaturamentoFinal.value = '';
    	form.idCategoria.value = '';
		form.idCategoria.selectedIndex = 0;
		form.idLigacaoAguaSituacao.value='';
		form.idLigacaoAguaSituacao.selectedIndex = 0;
		form.idLigacaoEsgotoSituacao.value='';
		form.idLigacaoEsgotoSituacao.selectedIndex = 0;
		form.idFaturamentoGrupo.value = '';
		form.idFaturamentoGrupo.selectedIndex = 0;
		form.idConsumoTarifa.value = '';
		form.idConsumoTarifa.selectedIndex = 0;
		
  		form.action='exibirSimularCalculoContaDadosReaisFiltrarAction.do?menu=sim';
	    form.submit();
  	}
	
	function limparImovel(){
		var form = document.forms[0];
		     	
		form.idImovel.value = "";
		form.inscricaoImovel.value = "";
		     	
		form.action = 'exibirSimularCalculoContaDadosReaisFiltrarAction.do?menu=sim';
	  	form.submit();
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
		
		if (tipoConsulta == 'imovel') {

		     form.idImovel.value = codigoRegistro;
		     form.inscricaoImovel.value = descricaoRegistro;
		     form.inscricaoImovel.style.color = "#000000";
		     
		     form.action = 'exibirSimularCalculoContaDadosReaisFiltrarAction.do?objetoConsulta=1';
			 form.submit();
	    }
	}
	
	function replicaDataPeriodoReferenciaFaturamento() {
		
		var form = document.forms[0];
		form.periodoReferenciaFaturamentoFinal.value = form.periodoReferenciaFaturamentoInicial.value;
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirSimularCalculoContaDadosReaisFiltrarAction.do"
	name="SimularCalculoContaDadosReaisFiltrarActionForm"
	type="gcom.gui.faturamento.conta.SimularCalculoContaDadosReaisFiltrarActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td width="149" valign="top" class="leftcoltext">
	      <div align="center">
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>

	      	</div>
	      	</td>
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Simular C�lculo das Contas com Dados Reais</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para simular c�lculo das contas com dados reais, informe os dados abaixo:</td>
				</tr>
				
				<tr>
	                <td>
	                	<strong>Per&iacute;odo de Refer�ncia do Faturamento:<font color="#FF0000">*</font></strong>
	                </td>
                
	                <td width="70%">
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="periodoReferenciaFaturamentoInicial" 
								size="7" 
								maxlength="7" 
								onkeyup="mascaraAnoMes(this, event);replicaDataPeriodoReferenciaFaturamento();"/>
							a 
							<html:text property="periodoReferenciaFaturamentoFinal" 
								size="7" 
								maxlength="7" 
								onkeyup="mascaraAnoMes(this, event)"/>
							
							</strong>(mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>
				
				<tr>
					<td><strong>Im&oacute;vel:</strong></td>
										
					<td>
           		
	  					<html:text maxlength="9" 
							property="idImovel" 
							size="9"
							onkeyup="javascript:verificaNumeroInteiro(this);"
							onkeypress="validaEnterComMensagem(event, 'exibirSimularCalculoContaDadosReaisFiltrarAction.do?objetoConsulta=1','idImovel','Im�vel');"
							/>	
						
						<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Im�vel" /></a> 
						
						<logic:present name="imovelEncontrado" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="imovelEncontrado" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparImovel();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Situa��o da Liga��o de �gua:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idLigacaoAguaSituacao" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoLigacaoAguaSituacao" scope="request">
								<html:options collection="colecaoLigacaoAguaSituacao"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Situa��o da Liga��o de Esgoto:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idLigacaoEsgotoSituacao" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoLigacaoEsgotoSituacao" scope="request">
								<html:options collection="colecaoLigacaoEsgotoSituacao"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Tarifa:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idConsumoTarifa" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoConsumoTarifa" scope="request">
								<html:options collection="colecaoConsumoTarifa"
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Grupo de Faturamento:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idFaturamentoGrupo" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
								<html:options collection="colecaoFaturamentoGrupo"
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Categoria:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idCategoria" 
							style="width: 230px;"
							multiple="true">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoCategoria" scope="request">
								<html:options collection="colecaoCategoria"
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
        
        		<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>

				<tr>
					<td height="24" >
			          	
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"
			          		/>
			          		
					</td>
				
					<td align="right">
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
						<gsan:controleAcessoBotao name="Button" 
							value="Filtrar"
							onclick="javascript:validarFormulario();"
							url="simularCalculoContaDadosReaisFiltrarAction.do" />
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