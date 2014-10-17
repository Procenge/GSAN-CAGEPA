<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="display" uri="displaytag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function cancelar(form) {

		window.close();
	}
	
	function preencherDadosEncerramentoAutomatica(idServicoTipo) {
		if (popup == 'true') {
			redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?redirecionarPagina=processoAutorizacaoServicosAssociados&idServicoTipo=' + idServicoTipo);
		} else {
			redirecionarSubmit('exibirEncerrarOrdemServicoAction.do?redirecionarPagina=processoAutorizacaoServicosAssociados&idServicoTipo=' + idServicoTipo + '&retornoTela=telaAnterior');
		}
		
	}
	
	function preencherDadosEncerramentoSolicita(idServicoTipo) {
		 var form = document.forms[0];
  	   		
		if (form.popup.value == 'true') {
			  
			redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?redirecionarPagina=processoAutorizacaoServicosAssociados&idServicoTipo=' + idServicoTipo + '&retornoTela=telaAnterior');
		} else {
			
			redirecionarSubmit('exibirEncerrarOrdemServicoAction.do?redirecionarPagina=processoAutorizacaoServicosAssociados&idServicoTipo=' + idServicoTipo + '&retornoTela=telaAnterior');
		}
		
	}
	
	function preencherDadosEncerramentoPosterior() {
		alert('Encerramento não autorizado pra este tipo de serviço.')
	}

</script>

<html:form method="post" action="autorizarServicoAssociado.do?acao=autorizarServicoAssociado">
	 
	<INPUT TYPE="hidden" name="popup" value="${popup}" />
	 
	<c:set var="i" value="0" />
	<display:table class="dataTable"  name="servicosParaAutorizacao" sort="list" id="servico"  pagesize="15" excludedParams="" requestURI="mostrarAutorizarServicoAssociado.do?acao=mostrarAutorizarServicoAssociado"> 
       
        <display:column style="text-align: center;" sortable="false" title="Serviço"> 
          <input type="hidden" name="idServicoAssociado" value="${servico.idServicoAssociado}"/> ${servico.descricaoServicoAssociado}
          <input type="hidden" name="descricaoServicoAssociado" value="${servico.descricaoServicoAssociado}"/>
        </display:column>
        
        <display:column style="text-align: center;"  sortable="false" title="Autorizar">

            <c:choose>
        		<c:when test="${servico.formaGeracaoHelper.automatica}">
        		    <input type="checkbox" name="execucaoAutorizada${servico.idServicoAssociado}" checked="checked" disabled="disabled" />
        		</c:when>
        		
        		<c:when test="${servico.formaGeracaoHelper.solicitaAutorizacao}">
        			<c:set var="nomeParametro" value="execucaoAutorizada${servico.idServicoAssociado}" />
        			<input type="checkbox" name="execucaoAutorizada${servico.idServicoAssociado}" value="true" 
        				<c:if test="${param[nomeParametro] == true}"> checked </c:if>
        			 />
        		</c:when>
        		
        		<c:when test="${servico.formaGeracaoHelper.posterior}">
        			<input type="checkbox" name="execucaoAutorizada${servico.idServicoAssociado}" value="false" disabled="disabled" />
        		</c:when>
        		
        	</c:choose>
        </display:column> 
        
        <display:column style="text-align: center;"  sortable="false" title="Equipe" >

        	<c:choose>
        		<c:when test="${servico.formaSelecaoEquipeHelper.atual}">
        			<input type="hidden" name="idEquipeInformada" value="-1" />
        		</c:when>
        		
        		<c:when test="${servico.formaSelecaoEquipeHelper.solicitaAutorizacao}">
        			
        			<select name="idEquipeInformada">
        			    <option value="-1">&nbsp;</option>
        			    <c:forEach items="${servico.equipes}" var="equipe" >
        			    	<option value="${equipe.id}"
        			    	<c:if test="${autorizarServicoForm.map.idEquipeInformada[i] == equipe.id}"> selected </c:if>
        			    	>${equipe.nome}</option>
        			    </c:forEach>
					</select>
        		
        		</c:when>
        		
        		<c:when test="${servico.formaSelecaoEquipeHelper.posterior}">
        			<input type="hidden" name="idEquipeInformada" value="-1" />
        		</c:when>
        	
        	</c:choose>
        </display:column>
        
        
        <display:column style="width:20%; text-align: center;" sortable="false" title="Programação">
             <c:choose>
             	<c:when test="${servico.formaProgramacaoHelper.mesmoDia}">
             		<fmt:formatDate value='${servico.dataProgramacaoInformada}' type='date' pattern='dd/MM/yyyy'/>
             		<input type="hidden" name="dataProgramacaoInformada" id="dataProgramacaoInformada${i}" value="" >
             	</c:when>
             	
             	<c:when test="${servico.formaProgramacaoHelper.solicitaAutorizacao}">
             	
             		<input type="text" name="dataProgramacaoInformada" id="dataProgramacaoInformada${i}" value="${autorizarServicoForm.map.dataProgramacaoInformada[i]}" 
		        		   onkeyup="mascaraData(this,event)" size="10" maxlength="10"  />
		        		   
	        		<a href="javascript:abrirCalendario(document.forms[0].name, 'dataProgramacaoInformada${i}');" >
					   <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
					</a>
						
             	</c:when>
             	
             	<c:when test="${servico.formaProgramacaoHelper.diaPosterior}">
             		<fmt:formatDate value='${servico.dataProgramacaoInformada}' type='date' pattern='dd/MM/yyyy'/>
             		<input type="hidden" name="dataProgramacaoInformada" id="dataProgramacaoInformada${i}" value="" >
             	</c:when>
             	
             	<c:when test="${servico.formaProgramacaoHelper.posterior}">
             		<input type="hidden" name="dataProgramacaoInformada" id="dataProgramacaoInformada${i}" value="" >	
             	</c:when>
             	
        	</c:choose>
        </display:column>
        
        <display:column style="text-align: center;" sortable="false" title="Trâmite" >
        
             <c:choose>
             
             	<c:when test="${servico.formaTramiteHelper.automatica}">
             		<input type="checkbox" name="tramiteAutorizado${servico.idServicoAssociado}" checked="checked" disabled="disabled">
             	</c:when>
             
             	<c:when test="${servico.formaTramiteHelper.solicitaAutorizacao}">
             		<c:set var="nomeParametro" value="tramiteAutorizado${servico.idServicoAssociado}" />
        				<input type="checkbox" name="tramiteAutorizado${servico.idServicoAssociado}" value="true"
    						<c:if test="${param[nomeParametro] == true}"> checked </c:if>
        			 	/>
             	</c:when>
             	
             	<c:when test="${servico.formaTramiteHelper.posterior}">
             		<input type="checkbox" name="tramiteAutorizado${servico.idServicoAssociado}" disabled="disabled" >
             	</c:when>
  
        	</c:choose>
        </display:column>
        
        <display:column style="text-align: center;" sortable="false" title="Encerramento"> 
          <c:set var="nomeAtributo" value="encerrado-${servico.idServicoAssociado}" />   	
        	<c:choose>
        		
        		<c:when test="${servico.formaEncerramentoHelper.solicitaAutorizacao}">
        			<input type="button" class="bottonRightCol" value="Encerrar"  onclick="preencherDadosEncerramentoSolicita(${servico.idServicoAssociado});"/>
        		</c:when>
        		
        		<c:when test="${servico.formaEncerramentoHelper.automatica}">
        			<input type="button" class="bottonRightCol" value="Encerrar" disabled="disabled" onclick="preencherDadosEncerramentoAutomatica(${servico.idServicoAssociado});"/>
        		</c:when>
        		
        		<c:when test="${servico.formaEncerramentoHelper.posterior}">
        			<input type="button" class="bottonRightCol" value="Encerrar" disabled="disabled" />
        		</c:when>
        	

        	</c:choose>
        </display:column>
       
      <c:set var="i" value="${i+1}" />
                
     </display:table>
    <div align="right"> 
     <input type="submit" class="bottonRightCol" name="Submit" value="Confirmar" >
     <input type="button" class="bottonRightCol" value="Cancelar" onClick="window.location.href='/gsan/telaPrincipal.do'"/>
    </div>
</html:form> 