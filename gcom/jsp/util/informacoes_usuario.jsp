<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<script language="JavaScript">
<!--

var habilitarAutoComplete = true;
/*
if (navigator.appName.indexOf('Microsoft') != -1){
	habilitarAutoComplete = false;
}
*/

function abrirUltimoAcesso(form){
  var ultimoAcesso = document.getElementById('ultimosacessos');
  if (ultimoAcesso.value != -1){
	  if (ultimoAcesso.value.indexOf('?') != -1) {
    	window.location.href = ultimoAcesso.value + "&menu=sim";
	  } else {
	    window.location.href = ultimoAcesso.value + "?menu=sim";
	  }
  }
}

function pressionarEnter(e, descricao) {
  if (e.keyCode && e.keyCode  == 13 || e.which == 13) {
    pesquisarFuncionalidade(descricao)
	return false;
  } 
}	  

function pesquisarFuncionalidade(descricao) {
  var url = '/gsan/filtrarFuncionalidadeAction.do?apenasFuncionalidade=true&menu=sim&descricao=' + descricao
  document.location = url
}

function localizarMenu(nome){
	
    if (nome.length < 4){
	    alert('Favor informar pelo menos 4 caracteres na pesquisa!');
	    return;
    }
  	d.closeAll();

    //array pra armazenar os indices das pastas abertas
    var indexAbertos = new Array();
    
    //percorre os itens do menu
    for (i = 0; i < d.arrNodes.length; i++){
	     //verifica se o nome esta no item do menu
	     if (d.arrNodes[i].name.toUpperCase().indexOf(nome.toUpperCase()) != -1){
		     if (indexAbertos.length == 0) d.o(1);
		     //armeza o indice da pasta;
		     index = d.arrNodes[i].id;
		     //abre todas as pastas pai, ate chegar no valor 1 (raiz) 
		     while (index != 1){
		    	  //verifica se o item eh uma pasta
		    	  if (d.arrNodes[index].url == null || d.arrNodes[index].url == '#'){ //eh uma pasta
			    	  var jahAberto = false;
		    	      //verifica se ja foi aberto
			    	  for(k = 0; k < indexAbertos.length; k++){
				    	   if (indexAbertos[k] == index) jahAberto = true;
			    	  }
			    	  if (!jahAberto){
			    		  d.o(index);
			    		  indexAbertos[indexAbertos.length] = index;
			    	  }	  
		    	  }
				  index = d.arrNodes[d.arrNodes[index].id].pid;
		     }
	     }
    }
}

//-->
</script>
    
<table width="100%" border="0" class="tableinlayerultimosacessos">
  <tr>
    <td>
	<%@ include file="/jsp/util/menu_gsan.jsp" %>
    </td>
  </tr>
  <tr>
    <td height="45">&nbsp;</td>
  </tr>
  <tr>
    <td>
      <input type="text" name="itemPesquisar" onkeypress="javascript:return pressionarEnter(event, document.getElementById('itemPesquisar').value);" id="itemPesquisar" value="Pesquisar no Menu" onclick="if (this.value == 'Pesquisar no Menu') this.value = ''" class="inputPesquisa">
      <a href="javascript:pesquisarFuncionalidade(document.getElementById('itemPesquisar').value);">
		<img border="0" src="<bean:message key="caminho.imagens"/>javax.help.SearchView.gif" title="Busca Rápida no Menu" />
	  </a>
    </td>
  </tr>
  <tr>
    <td>
      <bean:write name="ultimosAcessos" scope="session" filter="false"/>
    </td>
  </tr>
  <tr>
    <td>
		<table width="100%" border="0" class="tableinlayerusuario">
		  <tr>
		    <td><strong><a href="/gsan/exibirInformarMelhoriasGcomAction.do">Entre em Contato</a></strong><br></td>
		  </tr>
		  <tr>
		    <td><strong><a href="/gsan/exibirEfetuarAlteracaoSenhaSimplificadaAction.do">Alterar Senha</a></strong><br></td>
		  </tr>
		  <tr>
		    <td><strong>Data Atual:</strong><br>
		      <bean:write name="dataAtual" scope="session"/>
		    </td>
		  </tr>
		  <tr>
		    <td><strong>Usu&aacute;rio:</strong><br>
		      <bean:write name="usuarioLogado" scope="session" property="login"/>
		    </td>
		  </tr>
		  <tr>
		    <td><strong>Grupo:</strong><br>
		      <logic:notEmpty name="colecaoGruposUsuario" scope="session">
			    <logic:iterate name="colecaoGruposUsuario" id="grupo" scope="session">
			      <bean:write name="grupo" property="descricao"/><br>
			    </logic:iterate>
		      </logic:notEmpty>
		    </td>
		  </tr>
		  <tr>
		    <td><strong>Nº Acesso:</strong><br>
		      <bean:write name="usuarioLogado" scope="session" property="numeroAcessos"/>
		    </td>
		  </tr>
		  <tr>
		    <td><strong>Data Ult. Acesso:</strong><br>
		      <bean:write name="dataUltimoAcesso" scope="session"/>
		    </td>
		  </tr>
		  <tr>
		    <td><bean:write name="mensagemExpiracao" scope="session"/></td>
		  </tr>
		  <tr>
		    <td><strong><a href="/gsan/efetuarLogoffAction.do">Sair</a></strong><br></td>
		  </tr>
		</table>
    </td>
  </tr>
</table>
<div id="funcionalidades_div" class="autocomplete"></div>

<script type="text/javascript">
<!--
function autoCompleteMenu(){
	if (habilitarAutoComplete){
		new Ajax.Autocompleter("itemPesquisar", "funcionalidades_div", "/gsan/filtrarFuncionalidadeAction.do", {
		  afterUpdateElement: getSelectionId,
		  minChars: 4
		});
	}	
}

if (habilitarAutoComplete){
	document.getElementById('funcionalidades_div').style.visibility = 'visible';
	autoCompleteMenu();	
}

function getSelectionId(text, li) {
	var url = li.id;
	if (url.indexOf('?') == -1) url += '?manter=sim&menu=sim';
    else url += '&manter=sim&menu=sim';
	document.location = '/gsan/' + url;
} 
//-->
</script>