<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gsanLib.tld" prefix="gsan" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ExcluirNegativacaoOnLineActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>	
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
	
	
	
<script language="JavaScript">
<!-- Begin

	function habilitarDesabilitarDivBotaoExcluir(){	
		var botaoExcluir = document.getElementById("Atualizar");
					
		if(document.forms[0].habilitaBotaoExcluir.value == 'true'){			
			botaoExcluir.disabled = 0; //para ativar	
		}else{			
			botaoExcluir.disabled = 1; //para desativar
		}

		habilitarDesabilitarImovelArquivo();
	}
	
	function habilitarDesabilitarImovelArquivo(){									
		if((document.forms[0].arquivoMatricula.value == null 
				|| document.forms[0].arquivoMatricula.value == '')
				&& document.forms[0].idImovel.value != null 
				&& document.forms[0].idImovel.value != ''){
								
			document.forms[0].arquivoMatricula.disabled = 1; // para desativado
			
		}else if(document.forms[0].ehArquivo.value == 'true'){	
									
			document.forms[0].idImovel.value = '';
			document.forms[0].inscricaoImovel.value = '';			
			document.forms[0].idImovel.disabled = 1; // para desativado
													
		}else{
			
			document.forms[0].arquivoMatricula.disabled = 0; // para ativado
			document.forms[0].idImovel.disabled = 0; // para ativado
						
		}			
		
	}

	function trocarNegativador(){
		if (document.forms[0].idImovel.value != '') {
	        document.forms[0].action = 'exibirExcluirNegativacaoOnLineAction.do';
	        document.forms[0].submit();
	    }	    
	}

	

    function required () {
	 this.aa = new Array("negativador","Informe o negativador.", new Function ("varName", " return this[varName];"));     
     this.ab = new Array("idImovel", "Informe o imóvel.", new Function ("varName", " return this[varName];"));         
    } 
	
	
    function carregarArquivo(){
    	var form = document.forms[0];

         document.forms[0].action = 'exibirExcluirNegativacaoOnLineAction.do?download=true';
	     document.forms[0].submit();  
    }

  function validateExcluirNegativacaoOnLineActionForm() {
	 var form = document.forms[0];    
	    
	 if(document.forms[0].idImovel.value != ''){     	               
         if(validateRequired(form)){
        	submeterFormPadrao(form);
    	 }
    	 
     }else if(document.forms[0].ehArquivo.value != ''){ 
          document.forms[0].action = 'excluirNegativacaoOnLineAction.do';
 	      document.forms[0].submit();  
       	
     }
       
   } 



	function limparForm(tipo){
      var form = document.forms[0];
      	  botaoExcluir = document.getElementById("Atualizar");
	    if(tipo == 'imovel')
	    {		    
	        form.idImovel.value = "";
	        form.inscricaoImovel.value = "";
	        botaoExcluir.disabled = 1;
		}
	}	

	//Recupera Dados Popup
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	     if(tipoConsulta == 'imovel'){
	        form.idImovel.value = codigoRegistro;
	        form.inscricaoImovel.value = descricaoRegistro;
	        form.action = 'exibirExcluirNegativacaoOnLineAction.do?objetoConsulta=2';
	        form.submit();
	 	}
	}	
	
	// Chama Popup
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
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaMunicipio=" + tipo, altura, largura);
				}
			}
		}
	}			

-->
</script>
</head>

<body leftmargin="5" topmargin="5" onload="habilitarDesabilitarDivBotaoExcluir()">
<html:form action="/excluirNegativacaoOnLineAction.do" 
	name="ExcluirNegativacaoOnLineActionForm"
	type="gcom.gui.cobranca.ExcluirNegativacaoOnLineActionForm"
	method="post" enctype="multipart/form-data">

	<html:hidden name="ExcluirNegativacaoOnLineActionForm" property="idImovelAnterior"/>
	<html:hidden name="ExcluirNegativacaoOnLineActionForm" property="negativadorAnterior"/>
	<html:hidden name="ExcluirNegativacaoOnLineActionForm" property="habilitaBotaoExcluir"/>
	<html:hidden name="ExcluirNegativacaoOnLineActionForm" property="ehArquivo"/>		

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


			<td valign="top" class="centercoltext">
<!--Início Tabela Reference a Páginação da Tela de Processo-->
            <table>
              <tr> 
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="11"><img border="0" src="imagens/parahead_left.gif"/></td>

                <td class="parabg">Excluir Negativa&ccedil;&atilde;o do Im&oacute;vel</td>
                <td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif"/></td>
              </tr>
            </table>

            <!--Fim Tabela Reference a Páginação da Tela de Processo-->
            <p>&nbsp;</p>
            <table width="100%" border="0" >

              <form>
              
                <tr> 
                  <td colspan="2">Pesquisar um im&oacute;vel ou uma lista de imóveis</td>
                </tr>              
               
                <tr>
                  <td><strong>Negativador:<font color="#FF0000">*</font></strong></td>
                  <td><html:select styleId="comboNegativador" property="negativador" name="ExcluirNegativacaoOnLineActionForm" 
                   onchange="javascript:consultarCombosNegativador(this);">
                  		<logic:present property="collNegativador" name="ExcluirNegativacaoOnLineActionForm">
                  			<bean:define property="collNegativador" id="collNegativador" name="ExcluirNegativacaoOnLineActionForm" />
							<html:options collection="collNegativador" labelProperty="cliente.nome" property="id"/>
				  		</logic:present>
					  </html:select></td>
                </tr>
                 	 			     
                <tr> 
                  <td><strong> <font color="#FF0000"></font></strong></td>

                  <td>&nbsp;</td>
                </tr>



				<tr>
					<td><strong>Imóvel:</strong></td>
					<td><strong><html:text styleId="idImovel" property="idImovel" size="10" maxlength="10" onchange="habilitarDesabilitarImovelArquivo()"
						onkeypress="javascript:return validaEnter(event, 'exibirExcluirNegativacaoOnLineAction.do', 'idImovel');" />
												
					<logic:equal name="ExcluirNegativacaoOnLineActionForm" property="ehArquivo" value="false">
					<a
						href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].idImovel.value);"
						alt="Pesquisar Imóvel" > <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					</logic:equal>
					<logic:notEqual name="ExcluirNegativacaoOnLineActionForm" property="ehArquivo" value="false">
					<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
					</logic:notEqual>					
					</strong>
					
					<logic:present name="existeImovel">
						<logic:equal name="existeImovel" value="exception">
							<html:text property="inscricaoImovel" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="existeImovel" value="exception">
							<html:text property="inscricaoImovel" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="existeImovel">
						<logic:empty name="ExcluirNegativacaoOnLineActionForm"
							property="idImovel">
							<html:text property="inscricaoImovel" size="30" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:empty>
						<logic:notEmpty name="ExcluirNegativacaoOnLineActionForm"
							property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparForm('imovel');"> <img
						border="0"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						style="cursor: hand;" /> </a></td>
				</tr>
				
                 
                <tr> 
                  <td colspan="9"><hr></td>
                </tr>
                 
                 <tr>
					<td width="20%"><strong> Arquivo:</strong>
					</td>
					<td>
					<div id="arquivoMatricula">
						<html:file styleId="arquivoMatricula" property="arquivoMatricula" accept="text" onchange="javascript:carregarArquivo();"></html:file>																								
					</div>			         
				   
				    </td>

				</tr>
				<tr> 
                  <td colspan="9"></td>
                </tr>
				<tr>
					<td colspan="9">					
						<logic:present name="resumoArquivo">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="4" bgcolor="#000000" height="2"></td>
							</tr>
							<tr>
								<td>
									<table width="100%" bgcolor="#99CCFF">
										<!--header da tabela interna -->
										
										<tr bgcolor="#99CCFF">
											<td width="100%" align="center">
												<strong>Resumo Arquivo</strong>
											</td>																
										</tr>										
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" bgcolor="#99CCFF">																				
											<%int cont = 0;%>
											<logic:iterate name="resumoArquivo" id="resumoArquivo">									
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
												<%} else {%>
												<tr bgcolor="#FFFFFF">
												<%}%>																				
													<td width="100%" align="justify">
														<bean:write name="resumoArquivo"/>
													</td>																																										
												</tr>									
											</logic:iterate>										
									</table>
								</td>
							</tr>
						</table>
						</logic:present>
					</td>	
				</tr>								                               					
               
                  </span></b></strong><strong><b><span class="style1"></span></b></strong> 
                    <span class="style1"> </span></td>
           
                
                <tr> 
                  <td colspan="9"><hr></td>
                </tr>
    
                <tr> 
                  <td colspan="7"><p>
                  
                  	<input type="button"
								name="ButtonDesfazer" class="bottonRightCol" value="Desfazer"
								onClick="window.location.href='/gsan/exibirExcluirNegativacaoOnLineAction.do?menu=sim'"> &nbsp;
								                    
                    <input type="button"
								name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
								onClick="window.location.href='/gsan/telaPrincipal.do'">													
								
                  </p></td>
                  <td width="20%" colspan="2" align="right">  
                        						
						<input name="Atualizar" type="button" class="bottonRightCol" disabled="disabled" id="Atualizar" value="Excluir" onclick="javascript:validateExcluirNegativacaoOnLineActionForm();">
						                        
                  </td>
                </tr>
              </form>
            </table>
            <p class="style1">&nbsp;</p></tr>

      </table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
