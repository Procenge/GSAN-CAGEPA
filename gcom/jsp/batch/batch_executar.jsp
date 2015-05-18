<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ExecutarBatchDynaForm" />

</head>

<body leftmargin="5" topmargin="5">
	<html:form action="/executarBatch" method="post" name="ExecutarBatchDynaForm" type="org.apache.struts.action.DynaActionForm">
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>

			<td width="625" valign="top" class="centercoltext">

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
					<td class="parabg">Executar Batch</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			
			
			
			
				  <table width="100%" border="0">
	 
		
		<tr> 
         
          <td>
            <html:radio property="tipoProcesso" value="1">	</html:radio>           
          </td>
           <td width="40%"><strong> Processar Rotina Ajuste Registros Conta e Guia:</strong></td>
        </tr>
        
         <tr> 
         
          <td>
           <html:radio property="tipoProcesso" value="2">	</html:radio><br/>           
          </td>
           <td width="40%"><strong>Processar Rotina Ajuste Contabilidade Arrecadação:</strong></td>
        </tr>

		<tr> 
         
          <td>
           <html:radio property="tipoProcesso" value="3"></html:radio>
          </td>
           <td width="40%"><strong>Cancelar Débitos:</strong></td>
        </tr>
        
        <tr> 
        
          <td>
       		<html:radio property="tipoProcesso" value="5"></html:radio>
          </td>
            <td width="40%"><strong>Gerar Resumo Faturamento Simulação Grupo 18:</strong></td>
        </tr>
        <tr> 
          
          <td>
       			<html:radio property="tipoProcesso" value="6">	</html:radio>
          </td>
          <td width="40%"><strong> Gerar Resumo Faturamento Simulação Grupo 19:</strong></td>
        </tr>
        
         <tr> 
         
          <td>
       				<html:radio property="tipoProcesso" value="7"></html:radio>
          </td>
           <td width="40%"><strong> Gerar Resumo Faturamento Simulação Grupo 46:</strong></td>
        </tr>
        
        
         <tr> 
        
          <td>
       		<html:radio property="tipoProcesso" value="8">	</html:radio>
          </td>
            <td width="40%"><strong> Gerar Resumo Faturamento Simulação Grupo 104:</strong></td>
        </tr>
         <tr> 
     
          <td>
       		<html:radio property="tipoProcesso" value="9">	</html:radio>
          </td>
               <td width="40%"><strong> Desfazer Pré - Faturamento grupo 100 ref 201304:</strong></td>
        </tr>
        
         <tr> 
         
          <td>
       		<html:radio property="tipoProcesso" value="10">	</html:radio>
          </td>   
           <td width="40%"><strong>Remuneração do Legado Cobrança Administrativa - CASAL:</strong></td>       
         </tr>
        
         <tr> 
       
          <td>
       		<html:radio property="tipoProcesso" value="11"> </html:radio>
          </td>
             <td width="40%"><strong>Cancelar Débito a Cobrar - 9992:</strong></td>
        </tr>
        
        
        <tr> 
        
          <td>
       		<html:radio property="tipoProcesso" value="12"> </html:radio>
          </td>
            <td width="40%"><strong>CASO 1 [CASAL] - Ajuste Valor DebitoCobrado MENOR QUE VALOR CONTA:</strong></td>
        </tr>
        
         <tr> 
       
          <td>
       		<html:radio property="tipoProcesso" value="13"> </html:radio>
          </td>
             <td width="40%"><strong>CASO 2 [CASAL] - Ajuste Valor DebitoCobradoHistorico MENOR QUE VALOR CONTA_HISTORICO :</strong></td>
        </tr>
        
         <tr> 
        
          <td>
       			<html:radio property="tipoProcesso" value="14" > </html:radio>
          </td>
            <td width="40%"><strong>CASO 3 [CASAL] - Ajuste Valor DebitoCobradoHistorico MAIOR QUE VALOR CONTA_HISTORICO :</strong></td>
        </tr>
        
        <tr> 
        
          <td>
       			<html:radio property="tipoProcesso" value="16" ></html:radio>
          </td>
            <td width="40%"><strong> CASO 16 -  [DESO] - Cancelar pagamentos duplicados do BANESE (OC1180769) :</strong></td>
    	    <td colspan="3"> CORRETO <html:text property="idAvisoCorreto" maxlength="20" size="20" />   </td>
   		    <td colspan="3"> DUPLICADO <html:text property="idMovimentoDuplicado" maxlength="20" size="20" />  </td>
   			
        </tr>
        
        <tr> 
          <td>
       		<html:radio property="tipoProcesso" value="17"></html:radio>
          </td>
            <td width="40%"><strong> Classificar pagamentos (fixos):</strong></td>
        </tr>
        
        <tr> 
         
          <td>
       				<html:radio property="tipoProcesso" value="18"></html:radio>
          </td>
           <td width="40%"><strong> [CAERD] - Transferir Contas Histórico para Conta (Débito Prescrito) :</strong></td>
        </tr>
        
         <tr> 
         
          <td>
       				<html:radio property="tipoProcesso" value="19"></html:radio>
          </td>
           <td width="40%"><strong> [CAERD] - Transferir Contas Histórico para Conta (Indicador de Emissão Campo 3) :</strong></td>
        </tr>
        
         <tr> 
        
          <td>
       				<html:radio property="tipoProcesso" value="20"></html:radio>
          </td>
            <td width="40%"><strong> [CAERD] - Popula a tabela de REGISTRO DE ATENDIMENTO COM AS COORDENADAS DO IMÓVEL INFORMADAS PELO GIS :</strong></td>
        </tr>
        
        <tr> 
         
          <td>
       				<html:radio property="tipoProcesso" value="21"></html:radio>
          </td>
           <td width="40%"><strong> [DESO] - Ajustar Médias Geradas Erradas Devido a Erro Percentual de Coleta da Ligação de Esgoto :</strong></td>
        </tr>
        
         <tr> 
        
          <td>
       				<html:radio property="tipoProcesso" value="22"> </html:radio>
          </td>
            <td width="40%"><strong> [DESO] - Regeração Histograma :</strong></td>
         </tr> 
          
          
          <tr> 
          
        <td><strong>Ano Mês Referencia:</strong> </td>
          <td> <html:text property="anoMesRefInicial" maxlength="20" size="20" /> / <html:text property="anoMesRefFinal" maxlength="20" size="20" />
   						 &nbsp;aaaamm
    					
          </td>
          
          
        </tr>
        
         <tr> 
     
          <td>
       				<html:radio property="tipoProcesso" value="23" ></html:radio>
          </td>
               <td width="40%"><strong> [CAERD] - Gerar Debito A Cobrar Conta Com Valor A Menor :</strong></td>
          
          </tr>
          
          <tr> 
          
          <td><strong>Ano Mês Referencia:</strong></td>
	    	       			<!--  <td colspan="3"> <html:text property="anoMesReferencia" maxlength="20" size="20" /></td>-->
        </tr>
        
        
          <tr> 
          <td><strong>ids Grupos:</strong></td>
           <!-- <td colspan="3"> <html:text property="listaGrupos" maxlength="70" size="70" />-->
   						 &nbsp;
   			</td>
          </tr>
        
        
            <tr> 
     
          <td>
       				<html:radio property="tipoProcesso" value="26" ></html:radio>
          </td>
               <td width="40%"><strong> [CAERD] - Ajuste Resumo Ação de Cobrança :</strong></td>
          
          </tr>
          
          <tr> 
     
         	 <td>
       			<html:radio property="tipoProcesso" value="27" ></html:radio>
          	</td>
            <td width="40%">
            	<strong> CASO 27: Ajuste para testar batimento de valores de conta após alteração no UC00120 calcular valores água e esgoto cascata:</strong>
            </td>
          
          </tr>
          
           <tr> 
     
         	 <td>
       			<html:radio property="tipoProcesso" value="28" ></html:radio>
          	</td>
            <td width="40%">
            	<strong> CASO 28: Ajuste gerar registros em CLIENTE_DEBITO_A_COBRAR:</strong>
            </td>
          
          </tr>
        
             
		  <tr> 
				<td>
			    	<html:radio property="tipoProcesso" value="29"></html:radio>
			    </td>
			    <td width="40%">
			        <strong>CASO 29: Refaturar todas as contas da localidade 062 com referência de 10/2009 a 08/2014:</strong>
			    </td>
			</tr>
			<tr> 
	    		<td><strong>Ano Mês Referencia:</strong> </td>
				<td> <html:text property="anoMesReferencia28" maxlength="20" size="20" />&nbsp;aaaamm
				</td>
	     </tr>
	     
	     <tr> 
							<td>
								<html:radio property="tipoProcesso" value="31" ></html:radio>
								<html:radio property="tipoProcesso" value="7"></html:radio>
						 	</td>
						   	<td width="40%"><strong> Gerar Resumo Faturamento Simulação Grupo 46:</strong></td>
						</tr>
						<tr> 
						 	<td>
								<html:radio property="tipoProcesso" value="8">	</html:radio>
						  	</td>
						    <td width="40%"><strong> Gerar Resumo Faturamento Simulação Grupo 104:</strong></td>
						</tr>
						<tr> 
							<td>
								<html:radio property="tipoProcesso" value="9">	</html:radio>
						  	</td>
							<td width="40%"><strong> Desfazer Pré - Faturamento grupo 19 ref 201503:</strong></td>
						</tr>
						<tr> 
          					<td>
       							<html:radio property="tipoProcesso" value="10">	</html:radio>
          					</td>   
           					<td width="40%"><strong>Remuneração do Legado Cobrança Administrativa - CASAL:</strong></td>       
         				</tr>
         				<tr> 
							<td>
				       			<html:radio property="tipoProcesso" value="11"> </html:radio>
				          	</td>
				            <td width="40%"><strong>Cancelar Débito a Cobrar - 9992:</strong></td>
				        </tr>
				        <tr> 
				        	<td>
				       			<html:radio property="tipoProcesso" value="12"> </html:radio>
				          	</td>
				            <td width="40%"><strong>CASO 1 [CASAL] - Ajuste Valor DebitoCobrado MENOR QUE VALOR CONTA:</strong></td>
				        </tr>
				        <tr> 
				        	<td>
				       			<html:radio property="tipoProcesso" value="13"> </html:radio>
				          	</td>
				            <td width="40%"><strong>CASO 2 [CASAL] - Ajuste Valor DebitoCobradoHistorico MENOR QUE VALOR CONTA_HISTORICO :</strong></td>
				        </tr>
         				<tr> 
				          <td>
				       			<html:radio property="tipoProcesso" value="14" > </html:radio>
				          </td>
				            <td width="40%"><strong>CASO 3 [CASAL] - Ajuste Valor DebitoCobradoHistorico MAIOR QUE VALOR CONTA_HISTORICO :</strong></td>
				        </tr>
				        <tr> 
				        	<td>
				       			<html:radio property="tipoProcesso" value="16" ></html:radio>
				          	</td>
				            <td width="40%"><strong> CASO 16 -  [DESO] - Cancelar pagamentos duplicados do BANESE (OC1180769) :</strong></td>
				    	    <td colspan="3"> CORRETO <html:text property="idAvisoCorreto" maxlength="20" size="20" />   </td>
				   		    <td colspan="3"> DUPLICADO <html:text property="idMovimentoDuplicado" maxlength="20" size="20" />  </td>
				        </tr>
				        <tr> 
				        	<td>
				       			<html:radio property="tipoProcesso" value="16"></html:radio>
				          	</td>
				            <td width="40%"><strong> [CASAL] - Classificar pagamentos (fixos) com acréscimo de Multa :</strong></td>
				        </tr>
				        <tr> 
				        	<td>
       							<html:radio property="tipoProcesso" value="18"></html:radio>
          					</td>
           					<td width="40%"><strong> [CAERD] - Transferir Contas Histórico para Conta (Débito Prescrito) :</strong></td>
        				</tr>
				        <tr> 
          					<td>
       							<html:radio property="tipoProcesso" value="19"></html:radio>
          					</td>
           					<td width="40%"><strong> [CAERD] - Transferir Contas Histórico para Conta (Indicador de Emissão Campo 3) :</strong></td>
        				</tr>
						<tr> 
							<td>
								<html:radio property="tipoProcesso" value="20"></html:radio>
							</td>
						    <td width="40%"><strong> [CAERD] - Gerar Credito a Realizar :</strong></td>
						</tr>
						<tr> 
							<td><strong>Ano Mês Referencia:</strong></td>
	    	       			<!--  <td colspan="3"> <html:text property="anoMesReferencia" maxlength="20" size="20" /></td>-->
						</tr>
						<tr> 
							<td><strong>ids Grupos:</strong></td>
							<!-- <td colspan="3"> <html:text property="listaGrupos" maxlength="70" size="70" />-->
	   						 &nbsp;
	   						</td>
	          			</tr>	
	     
	     <tr> 
     
         	 <td>
       			<html:radio property="tipoProcesso" value="30" ></html:radio>
          	</td>
            <td width="40%">
            	<strong> CASO 30: Ajuste Conversão Coluna LOG tabela acordo_tac SOROCABA:</strong>
            </td>
          
         </tr>
          
	  </table>
			
			
			
			

			<table width="100%" border="0">

				
       				
       

				<tr>
					<td colspan="3">Clique no botão para executar o batch:</td>				
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
 

				<tr>
					<td colspan="2">
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td align="right">
						<gsan:controleAcessoBotao name="Button" 
							value="Executar"
							tabindex="6"
							onclick="javascript:submeterFormPadrao(document.forms[0]);"
							url="executarBatch.do" />
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
