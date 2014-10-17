<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirRoteiroEmpresaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validarForm(form){
		if (validateInserirRoteiroEmpresaActionForm(form)){
			var str = msgQuadrasSelecionadas();
			if (str == '') {
			  alert('Informe pelo menos uma Quadra para o Roteiro da Empresa.');
			  return false;
			}else{
				var mensagem = 'Confirma inclusão de Roteiro Empresa com quadra(s) ' + str + '?';			
				if (confirm(mensagem) ) {
					submeterFormPadrao(form);
				}
			}			
		}	
	}

	function limparEmpresa(form) {
    	form.empresa.value = "";
	}
	
	function limparLocalidade(form) {
    	form.idLocalidade.value = "";
    	form.descricaoLocalidade.value = "";
	}
	
	function limparLeiturista(form) {
    	form.idLeiturista.value = "";
    	form.nomeLeiturista.value = "";
	}

 	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];

    	if (tipoConsulta == 'localidade') {
      		limparLocalidade(form);
	      	form.idLocalidade.value = codigoRegistro;
    	  	form.descricaoLocalidade.value = descricaoRegistro;
      		form.descricaoLocalidade.style.color = "#000000";
	       	form.idLeiturista.focus();
			form.action = 'exibirInserirRoteiroEmpresaAction.do?localidadeAlterada=Sim';
			submeterFormPadrao(form);       	
    	} else 
	    if (tipoConsulta == 'setorComercial') {
	      	limparSetorComercial(form);
	      	form.codigoSetorComercial.value = codigoRegistro;
	      	form.setorComercialDescricao.value = descricaoRegistro;
	    	form.setorComercialDescricao.style.color = "#000000";

			form.action = 'exibirInserirRoteiroEmpresaAction.do';
			submeterFormPadrao(form);
    	} else if (tipoConsulta == 'leiturista') {
	    	form.idLeiturista.value = codigoRegistro;
	    	form.nomeLeiturista.value = descricaoRegistro;
	    	form.nomeLeiturista.style.color = "#000000";
	    } else if (tipoConsulta == 'grupoFaturamento'){
	         var grupo = form.faturamentoGrupo[form.faturamentoGrupo.selectedIndex].text;
	         var localidade = form.idLocalidade.value;
	        if (grupo != '' && localidade != ''){ 
	        	form.action = 'exibirInserirRoteiroEmpresaAction.do?grupoAlterado=Sim';
				submeterFormPadrao(form);
			}	
	    }
   }
   
	function limparForm(form) {
		form.idEmpresa.value = "";
		form.idLocalidade.value = "";
		form.descricaoLocalidade.value = "";
		form.idLeiturista.value = "";
		form.nomeLeiturista.value = "";
		MoverTodosDadosSelectMenu2PARAMenu1('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 'idSetorComercialSelecionados');		
	}   
	
	function carregarQuadras(){
	    var form = document.forms[0];
		form.action = 'exibirInserirRoteiroEmpresaAction.do?carregarQuadras=Sim';
		var optsSetores = form.idSetorComercialSelecionados.options;
		for (i = 0; i < optsSetores.length; i++) {
		  optsSetores[i].selected = true;
		}
		submeterFormPadrao(form);	
	}
	
	function carregarSetoresSelecionados(){
		var form = document.forms[0];
		var optsSetor = form.idSetorComercial.options;
		for (i = 0; i < optsSetor.length; i++) {
		    	optsSetor[i].selected = true;
		}
		MoverDadosSelectMenu1PARAMenu2('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 
			'idSetorComercialSelecionados');
	}
	
	function removerSetorSelecionado(){
		var form = document.forms[0];
		if (form.existeQuadrasCarregadas.value == 'S'){
			form.action = 'exibirInserirRoteiroEmpresaAction.do?removerSetor=Sim';				
			submeterFormPadrao(form);		
		}
	}
	
	function habilitarCarregarQuadras(){
		var form = document.forms[0];
		if (form.idSetorComercialSelecionados.options.length > 0){
			form.btCarregarQuadras.disabled = false;
		}
	}	
	
  function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
  }
  
  function msgQuadrasSelecionadas(){
  	var form = document.forms[0];  	
  	var str = '';
  	var idQuadras = form.idQuadraAdicionar;
  	
  	if (idQuadras != null){
  		if (idQuadras.type == 'checkbox'){ // apenas 1 elemento selecionado
	  		if (idQuadras.checked){
  				str += idQuadras.title + ", ";
  			}	
  		}else{
	  		for(i = 0; i < idQuadras.length; i++) {	   	   
				if (idQuadras[i].checked) {
	 				str += idQuadras[i].title + ", ";
  				}
			}
  		}
  		
		str = str.substr(0, str.length - 2);
	}	  	
  	return str;
  }
    
	
</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirRoteiroEmpresaAction" name="InserirRoteiroEmpresaActionForm"
	type="gcom.gui.micromedicao.InserirRoteiroEmpresaActionForm" method="post"
	onsubmit="return validateInserirRoteiroEmpresaActionForm(this);"  >

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="quadrasSelecionadas">

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
					<td class="parabg">Inserir Roteiro Empresa</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
<p>&nbsp;</p>
   			<table width="100%" border="0">

				<tr>
					<td>Para adicionar o roteiro empresa, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
              <tr> 
				<td width="163"><strong>Empresa:</strong><font color="#FF0000">*</font></td>
				<td align="right"><div align="left"> <span class="style2">
				  <html:select property="empresa" tabindex="1" >
					<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>				  
					<html:options collection="colecaoEmpresa"
						labelProperty="descricao" property="id" />
				  </html:select>
				  </span></div>
				</td>
              </tr>
              <tr>
					<td width="130"><strong>Agente Comercial Responsável:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="9" property="idLeiturista"
						tabindex="2" size="5"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirRoteiroEmpresaAction.do', 'idLeiturista', 'Leiturista');" />
					<a href="javascript:abrirPopup('exibirPesquisarLeituristaAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Agente Comercial" />
					</a> 
					<logic:present name="idLeituristaEncontrado" scope="request">
						<html:text maxlength="30" property="nomeLeiturista"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:present> 
					<logic:notPresent name="idLeituristaEncontrado" scope="request">
						<html:text maxlength="30" property="nomeLeiturista"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:notPresent> 
					<a href="javascript:limparLeiturista(document.InserirRoteiroEmpresaActionForm);"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
			 <tr> 
				<td width="163"><strong>Grupo de Faturamento:</strong><font color="#FF0000">*</font></td>
				<td align="right"><div align="left"> <span class="style2">
				  <html:select property="faturamentoGrupo" tabindex="3" onchange="javascript:recuperarDadosPopup('', '', 'grupoFaturamento');" >
				    <html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
					<html:options collection="colecaoFaturamentoGrupo"
						labelProperty="descricao" property="id" />
				  </html:select>
				  </span></div>
				</td>
              </tr>
				<tr>
					<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="3" tabindex="4" property="idLocalidade"
						size="5"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirRoteiroEmpresaAction.do?localidadeAlterada=Sim', 'idLocalidade', 'Localidade');" />
					<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> <logic:present
						name="idLocalidadeNaoEncontrada">
						<logic:equal name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="idLocalidadeNaoEncontrada">
						<logic:empty name="InserirRoteiroEmpresaActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"  />
						</logic:empty>

						<logic:notEmpty name="InserirRoteiroEmpresaActionForm"
							property="idLocalidade">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
					<a href="javascript:limparLocalidade(document.InserirRoteiroEmpresaActionForm);
						limparSetorComercial(document.InserirRoteiroEmpresaActionForm);">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /> </a>										
					</td>
				</tr>
              				
				<tr>
					<td width="120"><strong>Setor Comercial:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="200">
							<div align="left"><strong>Disponíveis</strong></div>
							<html:select property="idSetorComercial" size="6" multiple="true"
								style="width:200px" tabindex="5" >
								<html:options collection="colecaoSetorComercial"
									labelProperty="codigo" property="id" />
							</html:select></td>
							<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 'idSetorComercialSelecionados');habilitarCarregarQuadras();"
										value=" &gt;&gt; "></td>
								</tr>
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 'idSetorComercialSelecionados');habilitarCarregarQuadras();"
										value=" &nbsp;&gt;  "></td>
								</tr>
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:removerSetorSelecionado();MoverDadosSelectMenu2PARAMenu1('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 'idSetorComercialSelecionados');habilitarCarregarQuadras();"
										value=" &nbsp;&lt;  "></td>
								</tr>
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:removerSetorSelecionado();MoverTodosDadosSelectMenu2PARAMenu1('InserirRoteiroEmpresaActionForm', 'idSetorComercial', 'idSetorComercialSelecionados');habilitarCarregarQuadras();"
										value=" &lt;&lt; "></td>
								</tr>
							</table>
							</td>
							<td>
							<div align="left"><strong>Selecionados</strong></div>
							<html:select property="idSetorComercialSelecionados" size="6"
								multiple="true" style="width:200px" tabindex="6">
								<html:options collection="colecaoSetoresSelecionados"
									labelProperty="codigo" property="id" />								
							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>              
			  <tr> 			  	  
				  <td height="24" colspan="5" align="right">
					<input type=button class="bottonRightCol" name="btCarregarQuadras" disabled="disabled" value="Carregar Quadras" onclick="javascript:carregarQuadras()"> 
                </td>
              </tr>
              
			  <tr> 
                <td height="24" colspan="5"><hr></td>
              </tr>
              <tr>
              	<td colspan="5"> 
              		<div>
                    	 <%@ include file="/jsp/util/mensagens.jsp" %>
	            	</div>
	            </td>
              </tr>
              <tr> 
                <td colspan="5"><strong> <font color="#000000">Quadras:</font><font color="#FF0000">*</font></strong>
                 &nbsp;&nbsp;<a href="javascript:facilitador(this);" id="0">Todas</a>
                </td>
              </tr>
              <tr>				
                <td colspan="6" height="300">
                    <logic:equal name="colecaoQuadras" property="empty" value="true">
                    	<input type="hidden" name="existeQuadrasCarregadas" value="N"/>
                    </logic:equal>
                    <logic:equal name="colecaoQuadras" property="empty" value="false">
                    	<input type="hidden" name="existeQuadrasCarregadas" value="S"/>
                    </logic:equal>             
                    <div style="width: 100%; height: 100%; overflow: auto;">
                    <table width="90%" align="center" bgcolor="#99CCFF">
                      <!--corpo da segunda tabela-->
                      <tbody>
                      <tr bordercolor="#FFFFFF" bgcolor="#79BBFD"> 
                        <td width="8%"><div align="center"><strong>Adicionar</strong></div></td>
                        <td width="18%"><div align="center"><strong>Setor Comercial</strong></div></td>
                        <td width="24%"><div align="center"><strong>N&uacute;mero 
                            da Quadra</strong></div></td>
                        <td width="8%"><div align="center"><strong>Adicionar</strong></div></td>
                        <td width="18%"><div align="center"><strong>Setor Comercial</strong></div></td>
                        <td width="24%"><div align="center"><strong>N&uacute;mero 
                            da Quadra</strong></div></td>                            
                      </tr>
                    <%
                    int indexQuadra = 0;
                    %>
                      <logic:present name="colecaoQuadras">
	                      <logic:iterate name="colecaoQuadras" id="quadra">
	                         <% if (indexQuadra++ % 2 == 0) { %>
			                  <tr bgcolor="#cbe5fe"> 
			                  <% } %>
		                        <td bordercolor="#90c7fc"><div align="center">
		                        	<html:checkbox title="${quadra.numeroQuadra}" property="idQuadraAdicionar" value="${quadra.id}" disabled="<%=((gcom.cadastro.localidade.Quadra) quadra).getRoteiroEmpresa() != null ? true : false %>">
		                        	</html:checkbox>
		                        </td>
		                        <td bordercolor="#90c7fc"><div align="center" >${quadra.setorComercial.codigo}</div></td>
		                        <td bordercolor="#90c7fc">
		                        	<div align="center">${quadra.numeroQuadra}</div></td>
	                         <% if (indexQuadra % 2 == 0) { %>		                        
		                       </tr> 
		                     <% } %>
	                      </logic:iterate>                     
                      </logic:present>
                      </tbody>                      
                      </table>
                      </div>
                      </td>
                      </tr>
              <tr> 
                <td colspan="6"><strong> <font color="#FF0000"></font></strong> 
                  <div align="left"> 
                    <hr>
                  </div></td>
              </tr>
              <tr> 
                <td><strong> <font color="#FF0000"></font></strong></td>
                <td colspan="5" align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong> 
                    Campos obrigat&oacute;rios
                    </div>
                    </td>
              </tr>
              <tr> 
                <td colspan="2"><strong> <font color="#FF0000"> 
                  <input type="button" name="Submit22" class="bottonRightCol" value="Desfazer"
                      onclick="window.location.href='<html:rewrite page="/exibirInserirRoteiroEmpresaAction.do?desfazer=S"/>'">
                  <input type="button" name="Submit23" class="bottonRightCol" value="Cancelar"
                      onclick="window.location.href='/gsan/telaPrincipal.do'">
                  </font></strong>
                  </td>
                  <td colspan="5" align="right">
				  <gcom:controleAcessoBotao name="Button" value="Inserir"
							  onclick="javascript:validarForm(document.InserirRoteiroEmpresaActionForm);" url="inserirRoteiroEmpresaAction.do"/>
                </td>
              </tr>
            </table>
 
		<tr>
		  <td colspan="3">

		</td>
		</tr>
	  </table>
 
   
	<script>
		habilitarCarregarQuadras();
	</script>
  
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
