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
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioResumoProvisaoParaPerdasRecebimentoDynaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];
		if(form.psNumeroAno.value != null && form.psNumeroAno.value!=''){
			if(form.psNumeroAno.value.length !=4){
				alert("O campo ANO deve conter 4 digitos!");
				form.ANO.focus();
				return false;
			}

		}
		if(form.psNumeroMes.value !=null && form.psNumeroMes.value !='' ){
			if(form.psNumeroMes.value.length !=2){
				alert("O campo MÊS deve conter 2 digitos!");
				form.MES.focus();
				return false;
			}
		}
		
		
		
			document.forms[0].submit();
			document.forms[0].target = "";
		
	}


	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		
		document.forms[0].campoOrigem.value = campo.name;
		
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
				}else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
				}
			}
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'localidadeOrigem') {
      		
      		form.psCodLocalidade.value = codigoRegistro;
	  		form.nomeLocalidade.value = descricaoRegistro;
	  		
	  	      		
	  		form.nomeLocalidade.style.color = "#000000";
	  	
	  		
	  		
		}

	
		
	}

	

	function troca(str,strsai,strentra) 
    { 
            while(str.indexOf(strsai)>-1) 
            { 
                    str = str.replace(strsai,strentra); 
            } 
            return str; 
    }

	function FormataMoeda(campo,tammax,teclapres,caracter) 
    { 
            if(teclapres == null || teclapres == "undefined") 
            { 
                    var tecla = -1; 
            } 
            else 
            { 
                    var tecla = teclapres.keyCode; 
            } 

            if(caracter == null || caracter == "undefined") 
            { 
                    caracter = "."; 
            } 

            vr = campo.value; 
            if(caracter != "") 
            { 
                    vr = troca(vr,caracter,""); 
            } 
            vr = troca(vr,"/",""); 
            vr = troca(vr,",",""); 
            vr = troca(vr,".",""); 

            tam = vr.length; 
            if(tecla > 0) 
            { 
                    if(tam < tammax && tecla != 8) 
                    { 
                            tam = vr.length + 1; 
                    } 

                    if(tecla == 8) 
                    { 
                            tam = tam - 1; 
                    } 
            } 
            if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) 
            { 
                    if(tam <= 2) 
                    { 
                            campo.value = vr; 
                    } 
                    if((tam > 2) && (tam <= 5)) 
                    { 
                            campo.value = vr.substr(0, tam - 2) + '.' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 6) && (tam <= 8)) 
                    { 
                            campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + '.' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 9) && (tam <= 11)) 
                    { 
                            campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + '.' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 12) && (tam <= 14)) 
                    { 
                            campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + '.' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 15) && (tam <= 17)) 
                    { 
                            campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + '.' + vr.substr(tam - 2, tam); 
                    } 
            } 
    }

	function maskKeyPress(objEvent) 
    { 
            var iKeyCode; 
                                     
            if(window.event) // IE 
            { 
                    iKeyCode = objEvent.keyCode; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
            else if(e.which) // Netscape/Firefox/Opera 
            { 
                    iKeyCode = objEvent.which; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
             
             
    }


	

	

	function limpar(){

  		var form = document.forms[0];
  		
  		form.psNumeroAno.value = '';
  		form.psNumeroMes.value = '';
  		form.psCodLocalidade.value = "-1";
  		form.psCodMunicipio.value = '';
  		form.psCodRegional.value = "-1";
  			
  	}
	
	
	function submitDynaReset(){
		var form = document.forms[0];
		
		
		if(form.psCodLocalidade.value == 0){
			form.psCodLocalidade.value = '';	
		}
		if(form.psCodCategoria.value == 0){
	  		form.psCodCategoria.value = '';
		}
		if(form.psCodMunicipio.value == 0){
			form.psCodMunicipio.value = '';			
		}
		
		
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:disabilitarHabilitarUnidadeNegocio(); disabilitarHabilitarGerenciaRegional(); submitDynaReset();">

<html:form action="/gerarRelatorioResumoProvisaoParaPerdasRecebimentoAction.do"
	name="GerarRelatorioResumoProvisaoParaPerdasRecebimentoDynaActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

	<input type="hidden" name="campoOrigem" value=""/>
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="ResumoProvisoesParaPerdaRecebimentos.rpt"/>
	
	
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>

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
					<td class="parabg">Gerar Relat&oacute;rio de Resumo de Provisões para Perdas de Recebimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>
				
				
				
				<tr>
					<td><strong>Ano: </strong></td>					
					<td >						
						<html:text maxlength="4" property="psNumeroAno" size="6" onkeypress="javascript:return isCampoNumerico(event); "/>
					</td>
				</tr>
				<tr>
					<td><strong>Mês: </strong></td>					
					<td >						
						<html:text maxlength="2" property="psNumeroMes" size="4" onkeypress="javascript:return isCampoNumerico(event); "/>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="psCodLocalidade" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeyup="javascript:limparOrigem(1);"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioResumoAgAction.do?objetoConsulta=1','psCodLocalidade','Localidade'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].psCodLocalidade);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Munic&iacute;pio:</strong></td>
					<td>
						<html:text maxlength="4" 
							tabindex="1"
							property="psCodMunicipio" 
							size="4"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioResumoAgAction.do?objetoConsulta=8','psCodMunicipio','Município');"/> 
							
						<a href="javascript:chamarPopup('exibirPesquisarMunicipioAction.do', 'municipio', null, null, 275, 480, '', document.forms[0].municipio);">

							<img width="23" 
								height="21" 
								border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Município" /></a>
									

						<logic:present name="municipioEncontrada" scope="request">
							<html:text property="descricaoMunicipio" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="municipioEncontrada" scope="request">
							<html:text property="descricaoMunicipio" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						<a href="javascript:limparMunicipio();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" /></a>
					
					</td>
				</tr>
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional :</strong>
					</td>

					<td>
						<strong> 
						<html:select property="psCodRegional" style="width: 230px;" >
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nome" property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Tipo de Relatório :</strong>
					</td>

					<td>
						<strong> 
						<html:select property="psTipoRelatorio" style="width: 230px;" >
							
							<html:option
								value="A">1 - Analítico
							</html:option>
							<html:option
								value="S">2 - Sintético
							</html:option>
							<html:option
								value="T">3 - Somente Totais
							</html:option>
							
						</html:select> 														
						</strong>
					</td>
				</tr>             	
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar()"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm();" />
							
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

<!-- relatorio_contas_atraso_gerar.jsp -->
</html:html>
