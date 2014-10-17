<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@ include file="/jsp/util/titulo.jsp"%>
<script type="text/javascript">
var isCheckboxesChecked = false;
$(document).ready(function(){
	   $("dd").hide();
	   $("dt div").click(function(){
	   $("dd:visible").slideUp();
	   $(this).parent().next().slideDown();
	   //somaTotalOs();
	   desabilitaCheckBoxes();
	   document.forms[0].acao.value='exibirEmissaoOSCobranca'
	   return false;
	  });
 });
</script>
 <style>
	 body { font-family: Arial; font-size: 16px; }
 	dl { width: 60% }
 	dl,dd { margin: 0; }
	dt { background: #90c7fc; font-size: 14px; width: 60%}
 	dt a { color: #FFF; }
 	dd a { color: #000; }
 	ul { list-style: none; padding: 5px; }
 	ul li div strong {width:100%; list-style: none; background: white;}
 	#divWait {display:none; left: 0%; top: 0%; position:absolute; background-color:#9AC0CD; width:100%; height: 100%;
	opacity:0.60; Z-index: 3;   filter:alpha(opacity=60); 
	}
	
	#modalWait{display:none;
	 		   left:50%;
	            top:50%;
	          z-index:4;
	       position:absolute;
   background-color:white;}
   
	#modalPanel{
	 	     display:none;				 
		     z-index:4;
		    position:absolute;
    background-color:white;
               width:300px;
              height:120px; 
        border-style:solid;
        border-color:black;
        vertical-align: 50%;
        text-align: center;}
    			     
 </style>

</head>
<body>
    <div id="divWait"></div>
 <div id="modalWait">
	<img id="imagemModal" border="0"	src="<bean:message key="caminho.imagens"/>aguarde.gif"/>
 </div>
 <div id="modalPanel">
     <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
       <tr height="2%">
         <td id="topoModalPanel" style="color:white; background-color:#1E90FF;" colspan="3">
           <p><b>&nbsp;Atenção</b></p>
         </td>
       </tr>
       <tr height="90%" align="center">
         <td>&nbsp;</td>
         <td width="30">
         	<img border="0"	src="<bean:message key="caminho.imagens"/>tick.gif" width="25" height="25"/>
         	<br/>
         </td>
         <td align="left">
            <span id="messageModal">Ordens de Serviço emitidas com sucesso!</span>
         <br/>
         </td>
       </tr>
       <tr align="center" height="2%">
          <td style="top:0%;" colspan="3">
             <input type="button" value="    Ok    " onclick="$('#divWait').hide();$('#modalPanel').hide();" class="bottonRightCol"/>
          </td>
       </tr>
       <tr>
        <td colspan="3">
          <br/>
        </td>
       </tr>
     </table>
 </div>
 <html:form action="/emitirOSCobrancaAction.do">
   <html:hidden property="acao" value="associarOSCobranca" />
   <table>
   	<tr><input type="button" value="Voltar" onclick="javascript:history.back();" class="bottonRightCol"/></tr>
    <tr>
		<td><strong>Empresa<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<html:select  property="agenteSelecionado">
				    	<html:option value="">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoEmpresa">
							<html:options name="session" collection="colecaoEmpresa" labelProperty="descricao" property="id"/>
				     	</logic:present>			
					</html:select>
		</td>
		<td>
		        <input type="button" value="Associar" onclick="checkAction('associarOSCobranca');" class="bottonRightCol"/>
		        <input type="button" value="Emitir" onclick="checkAction('emitirOS');" class="bottonRightCol"/>
   				<!--<html:submit value="Associar" onclick="checkAction('associarOSCobranca');" styleClass="bottonRightCol"/>-->
				<html:submit value="Desfazer" onclick="return checkAction('desfazerAssociacoes');" styleClass="bottonRightCol"/>
				<!--<html:submit value="Emitir" onclick="checkAction('emitirOS');" styleClass="bottonRightCol"/>-->
  			</td>
	</tr>
   </table>
   <table width="60%" border="0" bgcolor="#79bbfd">
			<tr  align="left">
				<td width="10%">
			     <div align="center"><strong>Localidade</strong></div>
				</td>
				<td width="13%">
				 <div align="center"><strong>Setor</strong></div>
				</td>
				<td width="13%">
				 <div align="center"><strong></strong></div>
				</td>
 			</tr>
	</table>
	
	
	<logic:present name="listaOSFiltrada">
	  <%int cont = 0; %>
	  <logic:iterate id="item" name="listaOSFiltrada" indexId="indice">
	     <dt>
	       <div>
	       <%cont = cont + 1;
			if (cont % 2 == 0) {%>
	         <table width="100%" border="0" bgcolor="#90c7fc" cellpadding="0" cellspacing="0" bordercolor="#79bbfd">
	         <%} else { %>
	         <table width="100%" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#79bbfd">
	         <%} %>
	           <tr style="cursor:hand;cursor:pointer;" onMouseOver="javascript:this.style.backgroundColor='#BC8F8F';" onMouseOut="javascript:this.style.backgroundColor=''">
	             <td width="12%" align="center">
			 		<bean:write name="item" property="localidade"/>
			 	 </td>
			 	 <td width="15%" align="center">
			 		<bean:write name="item" property="setor"/>
			 	 </td>
			 	 <td width="13%">
				 	<div align="center"><strong></strong></div>
				</td>	
			 	</tr>
			 </table>
			</div> 
		  </dt>
	     <dd>
	      <ul>
	        <div>
	      	<table width="80%" bgcolor="#90c7fc">
				<tr>
				  <td width="10%" align="center"><strong><a id="seletorTodosCheckBoxes" onclick="selecionarTodos();" href="#">&nbsp;&nbsp;&nbsp;Todos&nbsp;&nbsp;</a></strong> </td>
		     	  <td width="20%" align="center"><strong>Quadra</strong> </td>
		     	  <td width="30%" align="center"><strong>Quantidade de OS</strong></td>
		     	  <td width="40%" align="center"><strong>Agente Associado</strong></td>
		     	</tr>
		    </table>
		    </div> 	
		  <li>
	         <div>
	         <table width="80%">
	          <logic:iterate id="quadra" name="item" property="quadras">
	          <tr>
	            <td width="10%" align="center">
 			    <html:multibox property="OSSelecionadas">
				   <bean:write name="quadra" property="locSetorQuadra"/>
 				</html:multibox>
 				</td>
 				<td width="20%" align="center"> <bean:write name="quadra" property="numeroQuadra"/></td>
 			    <td width="30%" align="center"> <bean:write name="quadra" property="quantidadeOS"/></td>
 			    <td width="41%" align="center"> <bean:write name="quadra" property="nomeAgente"/></td>
		       </tr>
     		    </logic:iterate>
     		  </table>  
     		 </div>
     		 <div>
     		   <table width="80%" bgcolor="#90c7fc">
	       	    <tr><td></td>
	              <!-- <td width="30%" align="center"></td>
 				  <td width="21%" align="right"><b>Total OS :</b></td>
 			      <td width="48%" align="center"><span id="totalOS"></span></td>
 			       -->
 		        </tr>
     		  </table>  
     		 </div>
 			</li>
 		   </ul>
 		</dd>
 		</logic:iterate>
 	  </logic:present>
 </html:form>	
</body>
<script>
function checkAction(acao){
	document.forms[0].acao.value=acao;
	
	if(acao == "associarOSCobranca"){
	  if(validaEntradaAssociacao()){
		  document.forms[0].submit();
	  }
	}else if (acao == "desfazerAssociacoes"){
			  if (confirm("Atenção! Todas as associações serão desfeitas. Deseja prosseguir?")){
				  document.forms[0].submit();
	    	  }else{
				  return false;			  
			  }
	 }else{
		 document.forms[0].submit();
	 }

}

function desmarcarTodos(){
	$("dd:visible input:enabled[type='checkbox']").attr("checked","");
	isCheckboxesChecked = false;
}

function selecionarTodos(){
	if(isCheckboxesChecked == false){
		isCheckboxesChecked = true;
		$("dd:visible input[type='checkbox']").attr("checked","checked");
	}else{
		desmarcarTodos()
	}	
}

function validaEntradaAssociacao(){
	var valor = $("select[name='agenteSelecionado'] :selected").val();
	var checked = $("input:enabled:checked");
	if(valor == ""){
		alert("Selecione uma empresa");
		return false;
	}else if(checked.length == 0){
		alert("Você deve marcar pelo menos uma quadra para fazer associação.");
		return false;
	}
	return true;
}

function ajaxEmitirOS(){
	$.ajax({
		 type: "POST", 
		 url: $("form[name='emissaoOrdemServicoForm']").attr("action")+"?acao=emitirOS"+"&random="+Math.random(), 
		 context: document.body,
		 success: function(msg){
			$("#modalWait").hide();
			createModalPanel(msg.split(",")[0],msg.split(",")[1]);
     },
     beforeSend: function(){
    	 showDivWait();
     },
     error:function (msg){
    	 $("#modalWait").hide();
    	 createModalPanel("error",msg);
     }    
     });
}

function showDivWait(){
	$("#divWait").css("width",$(window).width());
	$("#divWait").css("height",$(document).height());
	$("#divWait").show();
	
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	//armazena a largura e a altura da janela
	var winH = $(window).height();
	var winW = $(window).width();
	//centraliza na tela a janela popup
	$("#modalWait").css('top',  winH/2-$("#modalWait").height()/2);
	$("#modalWait").css('left', winW/2-$("#modalWait").width()/2);
	$("#modalWait").show();
}

function showMessageModal(msg){

	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	//armazena a largura e a altura da janela
	var winH = $(window).height();
	var winW = $(window).width();
	//centraliza na tela a janela popup
	$("#modalPanel").css('top',  winH/2-$("#modalPanel").height()/2);
	$("#modalPanel").css('left', winW/2-$("#modalPanel").width()/2);
	$("#messageModal").text(msg);
	$("#modalPanel").show();
	
}

function createModalPanel(type,msg){
		
	if(type == "success"){
		$("#modalPanel img[src$='tick.gif']").attr("src","/gsan/imagens/tick.gif");
	}else if(type == "alert"){
		$("#modalPanel img[src$='tick.gif']").attr("src","/gsan/imagens/alert_icon.gif");
	}else{
		$("#modalPanel img[src$='tick.gif']").attr("src","/gsan/imagens/icon-error.gif");
		msg = "Erro interno!"
	}
	showMessageModal(msg);
}

function somaTotalOs(){
	var array = $.makeArray($("dd:visible td[width=49%]"))
	var total = 0;
	$.each(array, function(){
	  total = total + parseInt($(this).text());
	})
	$("#totalOS").text(total);
	//alert(total);
}

function desabilitaCheckBoxes(){
	var array = $.makeArray($("dd:visible td[width=41%]"))
	var arrayCheck = $.makeArray($("dd:visible input[type='checkbox']"));
	$.each(array, function(index){
	  if($.trim($(this).text()) != ""){
	     arrayCheck[index].checked = "checked";
	     arrayCheck[index].disabled = "disabled";
	  }
	})
}

</script>
</html>