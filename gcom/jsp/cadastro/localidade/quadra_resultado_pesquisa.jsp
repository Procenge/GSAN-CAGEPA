<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<html:html>

<%@ page import="java.util.Collection,gcom.util.ConstantesSistema" %>

<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript">
function enviarIdParaInserir(idQuadra) {
	opener.redirecionarSubmitAtualizar(idQuadra);
	self.close();
}

function enviarDadosSeteParametros(codigoRegistro, descricaoRegistro, tipoConsulta, rotaID, localidadeID, setorComercialCD, rotaCD) {
	opener.recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta, rotaID, localidadeID, setorComercialCD, rotaCD);
	self.close();
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(660, 430);">
<table width="630" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="630" valign="top" class="centercoltext"> <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Pesquisa de Quadra</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0"  bgcolor="#90c7fc">
        <tr align="left">
          <td width="10%" align="center"><strong>N° Quadra</strong> </td>
          <td width="30%" align="center"><strong>Localidade</strong></td>
          <td width="10%" align="center"><strong>C&oacute;digo do Setor</strong></td>
          <td width="40%" align="center"><strong>Setor Comercial</strong></td>
          <td width="10%" align="center"><strong>Bairro</strong></td>
        </tr>
	<%--Esquema de paginação--%>
		<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
			export="currentPageNumber=pageNumber;pageOffset"
			maxPageItems="10" items="${sessionScope.totalRegistros}">
			<pg:param name="q" />

					<%int cont = 0;%>


         <logic:iterate name="quadras" id="quadra">
          <pg:item>
			<%cont = cont + 1;
			if (cont % 2 == 0) {%>
				<tr bgcolor="#cbe5fe">				
			<%} else {	%>
				<tr bgcolor="#FFFFFF">
			<%}%>
			
         
          <td align="center">
           <logic:equal name="tipoPesquisa" value="quadraOrigem">
               <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
				  <logic:present name="consulta">
						<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
							name="quadra" property="numeroQuadra" /> 
						</a>
				  </logic:present>
				  
				  <logic:notPresent name="consulta">
				  		<logic:notEmpty name="quadra" property="bairro">
							<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '<bean:write name="quadra" property="bairro.nome"/>', '<bean:write name="quadra" property="numeroQuadra"/>','quadraOrigem');">
		         	        <bean:write name="quadra" property="numeroQuadra"/>
	    	     	       </a>
	    	            </logic:notEmpty>
	    	            <logic:empty name="quadra" property="bairro">
	    	            	<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '', '<bean:write name="quadra" property="numeroQuadra"/>','quadraOrigem');">
		         	        <bean:write name="quadra" property="numeroQuadra"/>
	    	     	       </a>
	    	            </logic:empty>
				  </logic:notPresent>
	            </logic:notEqual>
	            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
				  <logic:present name="consulta">
						<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
							name="quadra" property="numeroQuadra" /> 
						</a>
				  </logic:present>
				  
				  <logic:notPresent name="consulta">
				  		<logic:notEmpty name="quadra" property="bairro">
						<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '<bean:write name="quadra" property="bairro.nome"/>', '<bean:write name="quadra" property="numeroQuadra"/>','quadraOrigem');">
							<font color="#CC0000"> 
	    		               <bean:write name="quadra" property="numeroQuadra"/>
	    		            </font>
		        	    </a>
		        	    </logic:notEmpty>
		        	    <logic:empty name="quadra" property="bairro">
		        	    	<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '', '<bean:write name="quadra" property="numeroQuadra"/>','quadraOrigem');">
							<font color="#CC0000"> 
	    		               <bean:write name="quadra" property="numeroQuadra"/>
	    		            </font>
		        	    </a>
		        	    </logic:empty>
				  </logic:notPresent>
	            </logic:equal>
           </logic:equal>
           <logic:equal name="tipoPesquisa" value="quadraDestino">
                <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
				<logic:present name="consulta">
						<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
							name="quadra" property="numeroQuadra" /> 
						</a>
				</logic:present>
				  
				<logic:notPresent name="consulta">
						<logic:notEmpty name="quadra" property="bairro">
							<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '<bean:write name="quadra" property="bairro.nome"/>', '<bean:write name="quadra" property="numeroQuadra"/>','quadraDestino');">
								<font color="#CC0000"> 
		    		               <bean:write name="quadra" property="numeroQuadra"/>
	    			            </font>
		    	    	    </a>
		    	    	</logic:notEmpty>
		    	    	<logic:empty name="quadra" property="bairro">
		    	    		<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '', '<bean:write name="quadra" property="numeroQuadra"/>','quadraDestino');">
								<font color="#CC0000"> 
		    		               <bean:write name="quadra" property="numeroQuadra"/>
	    			            </font>
		    	    	    </a>
		    	    	</logic:empty>    
				</logic:notPresent>
								
				
	            </logic:notEqual>
	            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
		            <logic:present name="consulta">
							<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
								name="quadra" property="numeroQuadra" /> 
							</a>
					</logic:present>
					  
					<logic:notPresent name="consulta">
							<logic:notEmpty name="quadra" property="bairro">
								<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '<bean:write name="quadra" property="bairro.nome"/>', '<bean:write name="quadra" property="numeroQuadra"/>','quadraDestino');">
									<font color="#CC0000"> 
		    		 	              <bean:write name="quadra" property="numeroQuadra"/>
		    			            </font>
			     		   	    </a>
			     		   	</logic:notEmpty>
			     		   	<logic:empty name="quadra" property="bairro">
			     		   		<a href="javascript:enviarDadosQuatroParametros('<bean:write name="quadra" property="id"/>', '', '<bean:write name="quadra" property="numeroQuadra"/>','quadraDestino');">
									<font color="#CC0000"> 
		    		 	              <bean:write name="quadra" property="numeroQuadra"/>
		    			            </font>
			     		   	    </a>			     		   	
			     		   	</logic:empty>    
					</logic:notPresent>
	            </logic:equal>
           </logic:equal>
           <logic:notEqual name="tipoPesquisa" value="quadraOrigem">
             <logic:notEqual name="tipoPesquisa" value="quadraDestino">
                <logic:notEqual name="tipoPesquisa" value="setorComercialDestino">
                  <logic:notEmpty name="caminhoRetornoTelaPesquisaQuadra">
                  
                  <!-- Início popip pesquisar Rota -->
                  <logic:equal name="caminhoRetornoTelaPesquisaQuadra" value="exibirPesquisarRotaAction">
                        <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
							<logic:present name="consulta">
									<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
										name="quadra" property="numeroQuadra" /> 
									</a>
							</logic:present>
							  	<logic:empty name="quadra" property="bairro">
							<logic:notPresent name="consulta">
									<a href="javascript:enviarDadosParametros('exibirPesquisarRotaAction', '<bean:write name="quadra" property="numeroQuadra"/>', '', 'quadra');">
					                 <bean:write name="quadra" property="numeroQuadra"/>
				    	           </a>
							</logic:notPresent>
							</logic:empty>
							
								<logic:notEmpty name="quadra" property="bairro">
								<logic:notPresent name="consulta">
									<a href="javascript:enviarDadosParametros('exibirPesquisarRotaAction', '<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
					                 <bean:write name="quadra" property="numeroQuadra"/>
				    	           </a>
							</logic:notPresent>
								</logic:notEmpty>
			            </logic:notEqual>
			            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
							<logic:present name="consulta">
									<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
										name="quadra" property="numeroQuadra" /> 
									</a>
							</logic:present>
							  
							<logic:notPresent name="consulta">
								<logic:notEmpty name="quadra" property="bairro">
									<a href="javascript:enviarDadosParametros('exibirPesquisarRotaAction', '<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
										<font color="#CC0000"> 
				    		               <bean:write name="quadra" property="numeroQuadra"/>
				    		            </font>
					        	    </a>
					        	    </logic:notEmpty>
					        	    <logic:empty name="quadra" property="bairro">
					        	    <a href="javascript:enviarDadosParametros('exibirPesquisarRotaAction', '<bean:write name="quadra" property="numeroQuadra"/>', '', 'quadra');">
										<font color="#CC0000"> 
				    		               <bean:write name="quadra" property="numeroQuadra"/>
				    		            </font>
					        	    </a>
					        	    </logic:empty>
							</logic:notPresent>							
			            </logic:equal>
                    </logic:equal>
                  <!--  Fim popup pesquisar Rota-->
                   <logic:equal name="caminhoRetornoTelaPesquisaQuadra" value="exibirPesquisarImovelAction">
                        <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
							<logic:present name="consulta">
									<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
										name="quadra" property="numeroQuadra" /> 
									</a>
							</logic:present>
							  
							<logic:notPresent name="consulta">
									<a href="javascript:enviarDadosParametros('exibirPesquisarImovelAction', '<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
					                 <bean:write name="quadra" property="numeroQuadra"/>
				    	           </a>
							</logic:notPresent>
			            </logic:notEqual>
			            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
							<logic:present name="consulta">
									<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
										name="quadra" property="numeroQuadra" /> 
									</a>
							</logic:present>
							  
							<logic:notPresent name="consulta">
									<a href="javascript:enviarDadosParametros('exibirPesquisarImovelAction', '<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
										<font color="#CC0000"> 
				    		               <bean:write name="quadra" property="numeroQuadra"/>
				    		            </font>
					        	    </a>
							</logic:notPresent>							
			            </logic:equal>
                    </logic:equal>
                 </logic:notEmpty>
                <logic:empty name="caminhoRetornoTelaPesquisaQuadra">
                  	<logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
						<logic:present name="consulta">
								<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
									name="quadra" property="numeroQuadra" /> 
								</a>
						</logic:present>
						  
						<logic:notPresent name="consulta">
							<logic:notPresent name="retornarSeteParametros">
								<logic:notEmpty name="quadra" property="bairro">
									<a href="javascript:enviarDados('<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
					                 <bean:write name="quadra" property="numeroQuadra"/>
				    	           </a>
				    	        </logic:notEmpty>
				    	        <logic:empty name="quadra" property="bairro">
									<a href="javascript:enviarDadosSeteParametros('<bean:write name="quadra" property="numeroQuadra"/>', '', 'quadra', '<bean:write name="quadra" property="rota.id"/>', '<bean:write name="quadra" property="setorComercial.localidade.id"/>', '<bean:write name="quadra" property="setorComercial.codigo"/>', '<bean:write name="quadra" property="rota.codigo"/>');">
					                 <bean:write name="quadra" property="numeroQuadra"/>
				    	           </a>
				    	        </logic:empty>
			    	        </logic:notPresent>

							<logic:present name="retornarSeteParametros">
								<a href="javascript:enviarDadosSeteParametros('<bean:write name="quadra" property="numeroQuadra"/>', '', 'quadra', '<bean:write name="quadra" property="rota.id"/>', '<bean:write name="quadra" property="setorComercial.localidade.id"/>', '<bean:write name="quadra" property="setorComercial.codigo"/>', '<bean:write name="quadra" property="rota.codigo"/>');">
									<bean:write name="quadra" property="numeroQuadra"/>
								</a>
			    	        </logic:present>
						</logic:notPresent>						
		            </logic:notEqual>
		            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
						<logic:present name="consulta">
								<a href="javascript:enviarIdParaInserir(${quadra.id})"> <bean:write
									name="quadra" property="numeroQuadra" /> 
								</a>
						</logic:present>
						  
						<logic:notPresent name="consulta">
								<a href="javascript:enviarDados('<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra', enviarDadosQuadraComRota('<bean:write name="quadra" property="numeroQuadra"/>', '<bean:write name="quadra" property="bairro.nome"/>', 'quadra');">
									<font color="#CC0000"> 
			    		               <bean:write name="quadra" property="numeroQuadra"/>
			    		            </font>
				        	    </a>
						</logic:notPresent>						
		            </logic:equal>
                </logic:empty>
             </logic:notEqual>
            </logic:notEqual>
           </logic:notEqual>
          </td>
<!-- ****************************************************************** -->
  <td >
             <logic:notEmpty name="quadra" property="setorComercial">
                 <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
						<bean:write name="quadra" property="setorComercial.localidade.descricao"/>
	            </logic:notEqual>
	            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
					<font color="#CC0000"> 
    		            <bean:write name="quadra" property="setorComercial.localidade.descricao"/>
   		            </font>
	            </logic:equal>
             </logic:notEmpty>
             <logic:empty name="quadra" property="setorComercial">
				&nbsp;
             </logic:empty>
	  </td>

           <td align="center">
             <logic:notEmpty name="quadra" property="setorComercial">
                 <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
						<bean:write name="quadra" property="setorComercial.codigo"/>
	            </logic:notEqual>
	            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
					<font color="#CC0000"> 
    		            <bean:write name="quadra" property="setorComercial.codigo"/>
   		            </font>
	            </logic:equal>
             </logic:notEmpty>
             <logic:empty name="quadra" property="setorComercial">
				&nbsp;
             </logic:empty>
	  </td>

          <td >
             <logic:notEmpty name="quadra" property="setorComercial">
                 <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
						<bean:write name="quadra" property="setorComercial.descricao"/>
	            </logic:notEqual>
	            <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
					<font color="#CC0000"> 
    		            <bean:write name="quadra" property="setorComercial.descricao"/>
   		            </font>
	            </logic:equal>
             </logic:notEmpty>
             <logic:empty name="quadra" property="setorComercial">
				&nbsp;
             </logic:empty>
	  </td>
	  
	  
	 <td >
           <logic:notEmpty name="quadra" property="bairro">
               <logic:notEqual name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
				<bean:write name="quadra" property="bairro.nome"/>
           </logic:notEqual>
           <logic:equal name="quadra" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">            
			<font color="#CC0000"> 
  		            <bean:write name="quadra" property="bairro.nome"/>
 		            </font>
           </logic:equal>
           </logic:notEmpty>
           <logic:empty name="quadra" property="bairro">
		&nbsp;
           </logic:empty>
	  </td>
	  
        </tr>
	</pg:item>
      </logic:iterate>
					</tr>
		</table>

		<table width="100%" border="0">
			<tr>
				<td>
				<div align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
				</td>
				</pg:pager>
				<%-- Fim do esquema de paginação --%>
			</tr>
		</table>
      <table width="100%" border="0">
         <tr>
          <td height="24"><input type="button" class="bottonRightCol" value="Fechar" onclick="javascript:window.close();"/></td>
        </tr>
      </table>
      </td>
  </tr>
  </table>
</table>
</body>
</html:html>
