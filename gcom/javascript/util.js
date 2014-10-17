/*
Fun??o que envia dados para a p?gina que abriu o popup -- A p?gina que usa a
pesquisa do popup deve ter a fun??o 'recuperarDadosPopup' para setar os
dados no respectivo form.

codigoRegistro - c?digo do registro que foi selecionado no popup
descricaoRegistro - descri??o do registro que foi selecionado no popup
tipoConsulta - descri??o de que tipo de registro o usu?rio pretende pesquisar

*/
function enviarDados(codigoRegistro, descricaoRegistro, tipoConsulta) {
   opener.recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta);
   self.close();
}


/*
Fun??o que envia dados para a p?gina que abriu o popup -- A p?gina que usa a
pesquisa do popup deve ter a fun??o 'recuperarDadosPopup' para setar os
dados no respectivo form.

codigoRegistro - c?digo do registro que foi selecionado no popup
descricaoRegistro - descri??o do registro que foi selecionado no popup
tipoRegistro - tipo do registro que foi selecionado no popup
auxRegistro - registro aux que foi selecionado no popup
tipoConsulta - descri??o de que tipo de registro o usu?rio pretende pesquisar

*/
function enviarDadosInserirColecao(codigoRegistro, descricaoRegistro,tipoRegistro,auxRegistro,tipoConsulta) {
   opener.recuperarDadosPopupInserirColecao(codigoRegistro, descricaoRegistro,tipoRegistro,auxRegistro, tipoConsulta);
   self.close();
}


function enviarDadosParametros(nomeActionExibirPagina, idCampo, descricaoCampo, tipoConsulta) {

	window.location.href =
	'/gsan/' + nomeActionExibirPagina + '.do?idCampoEnviarDados=' + idCampo +
	'&descricaoCampoEnviarDados=' + descricaoCampo + '&tipoConsulta=' + tipoConsulta;

}

function enviarDadosParametrosFecharPopup(nomeActionExibirPagina, idCampo, descricaoCampo, tipoConsulta) {
	opener.window.location.href =
	'/gsan/' + nomeActionExibirPagina + '.do?idCampoEnviarDados=' + idCampo +
	'&descricaoCampoEnviarDados=' + descricaoCampo + '&tipoConsulta=' + tipoConsulta;
	self.close();

}


//=====================================================================================================================

function enviarDadosMunicipioDdd(codigoRegistro,descricaoRegistro,codigoDdd,tipoConsulta) {
  opener.recuperarDadosMunicipioDdd(codigoRegistro,descricaoRegistro,codigoDdd,tipoConsulta);
  self.close();
}


function enviarDadosCodigoEnderecamentoPostal(codigoRegistro, descricaoRegistro, codigoLogradouro, tipoConsulta){
   opener.recuperarDadosCodigoEnderecamentoPostal(codigoRegistro, descricaoRegistro, codigoLogradouro,tipoConsulta);
   self.close();
}

//Enviar Dados com 4 parametros
function enviarDadosQuatroParametros(codigoRegistro, descricaoRegistro, codigoAuxiliar, tipoConsulta){
   opener.recuperarDadosQuatroParametros(codigoRegistro, descricaoRegistro, codigoAuxiliar,tipoConsulta);
   self.close();
}


//Enviar Dados com 5 parametros
function enviarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta){
   opener.recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta);
   self.close();
}

//Enviar Dados com 6 parametros
function enviarDadosSeisParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, descricaoRegistro4, tipoConsulta){
    opener.recuperarDadosSeisParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, descricaoRegistro4, tipoConsulta);
   self.close();
}

//=====================================================================================================================


function enviarDadosCincoParametrosCaminhoRetorno(nomeActionExibirPagina, codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) {
	window.location.href =
	'/gsan/' + nomeActionExibirPagina + '.do?idCampoEnviarDados=' + codigoRegistro +
	'&descricaoCampoEnviarDados1=' + descricaoRegistro1 + '&descricaoCampoEnviarDados2=' + descricaoRegistro2 + 
	'&descricaoCampoEnviarDados3=' + descricaoRegistro3 + '&tipoConsulta=' + tipoConsulta;

}

function enviarDadosSeisParametrosCaminhoRetorno(nomeActionExibirPagina, codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, descricaoRegistro4, tipoConsulta) {
	window.location.href =
	'/gsan/' + nomeActionExibirPagina + '.do?idCampoEnviarDados=' + codigoRegistro +
	'&descricaoCampoEnviarDados1=' + descricaoRegistro1 + '&descricaoCampoEnviarDados2=' + descricaoRegistro2 + 
	'&descricaoCampoEnviarDados3=' + descricaoRegistro3 + '&descricaoCampoEnviarDados4=' + descricaoRegistro4 + '&tipoConsulta=' + tipoConsulta;

}

/* Fun??o que abre um popup com o caminho informado
   EX: abrirPopup(String , int, int)
   OBS - O popup ser? aberto no centro da tela
*/
function abrirPopup(caminho, altura, largura) {
	abrirPopupDeNome(caminho, altura, largura, 'Pesquisar', 'yes');
}

/* Fun??o que abre um popup com o caminho informado
   EX: abrirPopup(String , int, int)
   OBS - O popup ser? aberto no centro da tela
*/
function abrirPopupDeNome(caminho, altura, largura, nome, status) {

    var x, y, width, height;
    var features = "modal=yes,toolbar=no,resizable=no";
    features += ",scrollbars=yes,location=no,status=" + status;

    //Para abrir o popup centralizado ======

    // Netscape
    if (window.screenX) {
    	width = window.outerWidth;
    	height = window.outerHeight;
        x = window.screenX + (width - largura) / 2;
        y = window.screenY + (height - altura) / 2;
        features += ",screenX=" + x + ",screenY=" + y;
    }
    // IE
    else {
    	width = window.screen.availWidth;
    	height = window.screen.availHeight;
        x = (width - largura) / 2;
        y = (height - altura) / 2;
        features += ",left=" + x + ",top=" + y;
    }
    features += ",width=" + largura + ",height=" + altura;
	
    return window.open(caminho, nome, features);
}


function abrirPopupHelp(caminho, altura, largura) {

	abrirPopupDeNome(caminho, altura, largura, '_help', 'yes');
}


function abrirPopupComSubmit(caminho, altura, largura, nomeJanela) {

	abrirPopupDeNome('about:blank', altura, largura, nomeJanela, 'yes');

	var form = document.forms[0];
	form.action = caminho;
	form.target = nomeJanela;
	
	submeterFormPadrao(form);
	   
	form.target = "_top";
}


/* Fun??o que abre um popup com o caminho informado (Ir? abrir sempre de acordo com a resolu??o do monitor)
   EX: abrirPopupRelatorio(String)
*/
function abrirPopupRelatorio(caminho) {

   //Carrega a resolu??o do monitor =======
	
    // Netscape
    if (window.screenX) {
    	largura = window.outerWidth;
    	altura = window.outerHeight;
    }
    // IE
    else {
    	largura = window.screen.availWidth;
    	altura = window.screen.availHeight;
    }
	
   //======================================
   window.open(caminho,'Relatorio','top=0,left=0,location=no,screenY=0,screenX=0,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizable=yes,width=' + largura + ',height=' + altura);
}


/*
Fun??o que adiciona campos din?micos em p?ginas html.
As p?ginas devem ter <div id="newElements"></div> no trecho onde o conte?do din?mico
deve aparecer

	nomeForm - Nome do html form que ser? modificado
	nomeCampoQuantidadeElementos - Nome do campo que guarda quantos elementos din?micos ser?o
criados
	nomeLabelCampo - Label do campo din?mico

*/
function addElement(nomeForm, nomeCampoQuantidadeElementos, nomeLabelCampo)
{
   var newElement = '';

   for (i=1; i<=document.forms[nomeForm].elements[nomeCampoQuantidadeElementos].value; i++) {
   	newElement += nomeLabelCampo + ' ' + i + ': <input type="text" name=campoDinamico'+i+'><br>';
   }

   document.getElementById('newElements').innerHTML += newElement;

}



/*
Fun??o que adiciona campos din?micos em p?ginas html seguidos por campo select.
As p?ginas devem ter <div id="newElements"></div> no trecho onde o conte?do din?mico
deve aparecer

	nomeForm - Nome do html form que ser? modificado
	nomeCampoQuantidadeElementos - Nome do campo que guarda quantos elementos din?micos ser?o
criados
	nomeLabelCampo - Label do campo din?mico
	stringSelect - trecho de c?digo html que representa o select desejado

*/
function addElementSelect(nomeForm, nomeCampoQuantidadeElementos, nomeLabelCampo, stringSelect) {

   var newElement = '';


   for (i=1; i<=document.forms[nomeForm].elements[nomeCampoQuantidadeElementos].value; i++) {
        campoStringSelect = '<select name=selectDinamico'+i+'>' + stringSelect
   	newElement += nomeLabelCampo + ' ' + i + ': <input type="text" name=campoDinamico'+i+'>' + campoStringSelect + '<br>';
   }

   document.getElementById('newElements').innerHTML += newElement;
}


//Mesma coisa do addElementSelect com adapta??o para as p?ginas que usar?o telefone
function addCamposTelefone(nomeForm, nomeCampoQuantidadeElementos, nomeLabelCampo, stringSelect) {

   var newElement = '';


   for (i=1; i<=document.forms[nomeForm].elements[nomeCampoQuantidadeElementos].value; i++) {
        campoStringSelect = '<select name=codigoTipoTelefone'+i+'>' + stringSelect
   		newElement += 'DDD: ' + '<input type="text" size="2" maxlength="2" name=ddd'+i+'> ' +
		nomeLabelCampo + ' ' + i + ': <input type="text" size="8" maxlength="8" name=numeroTelefone'+i+'>' +
		campoStringSelect + '<br>';
   }

   document.getElementById('newElements').innerHTML += newElement;
}



//Fun??o que limpa os campos din?micos de uma p?gina
function removeElements() {
	document.getElementById('newElements').innerHTML = '';
}



//Fun??o que manipula select m?ltiplo movendo os dados de um select para outro
//nomeForm - Nome do form a ser manipulado
//nomeCampoMenu1 - Nome do campo do select 1 que cont?m os dados dispon?veis
//nomeCampoMenu2 - Nome do campo do select 2 que cont?m os dados cadastrados

function MoverDadosSelectMenu1PARAMenu2(nomeForm, nomeCampoMenu1, nomeCampoMenu2) {

    var m1 = document.forms[nomeForm].elements[nomeCampoMenu1];
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];

    m1len = m1.length ;
    for ( i=0; i<m1len ; i++){
        if (m1.options[i].selected == true ) {
            m2len = m2.length;
            m2.options[m2len]= new Option(m1.options[i].text, m1.options[i].value);
        }
    }

    for ( i = (m1len -1); i>=0; i--){
        if (m1.options[i].selected == true ) {
            m1.options[i] = null;
        }
    }
}



//Fun??o que manipula select m?ltiplo movendo os dados de um select para outro
//nomeForm - Nome do form a ser manipulado
//nomeCampoMenu1 - Nome do campo do select 1 que cont?m os dados dispon?veis
//nomeCampoMenu2 - Nome do campo do select 2 que cont?m os dados cadastrados

function MoverTodosDadosSelectMenu2PARAMenu1(nomeForm, nomeCampoMenu1, nomeCampoMenu2) {

    var m1 = document.forms[nomeForm].elements[nomeCampoMenu1];
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];
    
    //Seleciona todos os itens do menu2 para enviar para o menu1
    enviarSelectMultiplo(nomeForm, nomeCampoMenu2);

       m2len = m2.length;
        for ( i=0; i<m2len ; i++){
            if (m2.options[i].selected == true && m2.options[i].value != '-1') {
                m1len = m1.length;
                m1.options[m1len]= new Option(m2.options[i].text, m2.options[i].value);
            }
        }
        for ( i=(m2len-1); i>=0; i--) {
            if (m2.options[i].selected == true ) {
                m2.options[i] = null;
            }
        }
}



//Fun??o que manipula select m?ltiplo movendo os dados de um select para outro
//nomeForm - Nome do form a ser manipulado
//nomeCampoMenu1 - Nome do campo do select 1 que cont?m os dados dispon?veis
//nomeCampoMenu2 - Nome do campo do select 2 que cont?m os dados cadastrados

function MoverTodosDadosSelectMenu1PARAMenu2(nomeForm, nomeCampoMenu1, nomeCampoMenu2) {

    var m1 = document.forms[nomeForm].elements[nomeCampoMenu1];
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];
    
    //Seleciona todos os itens do menu1 para enviar para o menu2
    enviarSelectMultiplo(nomeForm, nomeCampoMenu1);

    m1len = m1.length ;
    for ( i=0; i<m1len ; i++){
       	if (m1.options[i].selected == true && m1.options[i].value != '-1') {    
            m2len = m2.length;
            m2.options[m2len]= new Option(m1.options[i].text, m1.options[i].value);
        }
    }

    for ( i = (m1len -1); i>=0; i--){
        if (m1.options[i].selected == true ) {
            m1.options[i] = null;
        }
    }
}



//Fun??o que manipula select m?ltiplo movendo os dados de um select para outro
//nomeForm - Nome do form a ser manipulado
//nomeCampoMenu1 - Nome do campo do select 1 que cont?m os dados dispon?veis
//nomeCampoMenu2 - Nome do campo do select 2 que cont?m os dados cadastrados

function MoverDadosSelectMenu2PARAMenu1(nomeForm, nomeCampoMenu1, nomeCampoMenu2) {

    var m1 = document.forms[nomeForm].elements[nomeCampoMenu1];
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];

    m2len = m2.length;
        for ( i=0; i<m2len ; i++){
            if (m2.options[i].selected == true ) {
            	if (m2.options[i].value != '-1') {
               		m1len = m1.length;
                	m1.options[m1len]= new Option(m2.options[i].text, m2.options[i].value);
                }
            }
        }
        for ( i=(m2len-1); i>=0; i--) {
            if (m2.options[i].selected == true ) {
            	if (m2.options[i].value != '-1') {
                	m2.options[i] = null;
                }
            }
        }
}

//Fun??o que seleciona todos os options do select m?ltiplo para serem enviados
//para processamento
function enviarSelectMultiplo(nomeForm, nomeCampoMenu2) {
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];

	if (m2.length == 0) {
		m2.options[0] = new Option('', '-1');	
	} else {
		if (m2.options[0].value == '-1') {
			m2.options[0] = null;
		}
	}


	for (i=0; i<m2.length; i++) {
		if (m2.options[i] != null) {		
			m2.options[i].selected = true;
		}
	}
	

}



//fun??o que limita o n? de caracteres num campo do tipo "textarea" ou "text"
function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}

//Autor: Raphael Rossiter
//limitField = Objeto textarea que ira ser limitado
//limitNum = quantidade m?xima de caracteres
// "utilizado" = Local onde ser? apresentado a quantidade de caracteres que foi utilizada
// "limite" = Local onde ser? apresentado a quantidade de caracteres que falta para bater o limite
// EX: <span id="utilizado">0</span>/<span id="limite">10</span> limitNum = 10
function limitTextArea(limitField, limitNum, utilizado, limite) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		utilizado.innerHTML = limitField.value.length;
		limite.innerHTML = limitNum - limitField.value.length;
	}
}




// Limpa um campo do formul?rio informado como par?metro
function limparCampoPesquisa(nomeForm, nomeCampo) {
  var campo = document.forms[nomeForm].elements[nomeCampo];
  campo.value = "";
}


// Fun??o utilizada no mecanismode processo quando o usu?rio clica no bot?o avan?ar
function botaoAvancar(uri){

	//alert(uri);
 //alert(uri.match('inserirUsuarioWizardAction.do'));
 //alert(uri.value.indexOf('inserirUsuarioWizard.do'));
 
 
 
  document.forms[0].action=uri;
  var cancelarValidacao = false;
  
  //Parte que verifica se a valida??o foi desabilitada
  for (i=0; i < document.forms[0].elements.length; i++) {
    if (document.forms[0].elements[i].name == 'cancelarValidacao') {
//    	alert(document.forms[0].elements[i].value);
    	 if (document.forms[0].elements[i].value == 'true' || document.forms[0].elements[i].value == 'TRUE') {
	cancelarValidacao = true;
	break;
      }
    }
  }

  if (cancelarValidacao || (eval('validate' + document.forms[0].name + '(document.forms[0]);'))) {
    for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].disabled ) {
        document.forms[0].elements[i].disabled = false;
      }
    }
    
    //Alterado por pedro alexandre dia 11/08/2006
    //Trecho adicionado para os processo de usu?rio
    //porque os campos login e email n?o podem ser colocados para caixa alta
    var colocarUpperCase = true;
    
    if(uri.match('inserirUsuarioWizardAction.do') != null || uri.match('atualizarUsuarioWizardAction.do') != null){
	  colocarUpperCase = false;
	}

    if(colocarUpperCase){
	  toUpperCase(document.forms[0]);
	}
    
    document.forms[0].submit();
  }
}

// Fun??o utilizada no mecanismode processo quando o usu?rio clica no bot?o avan?ar
function botaoAvancarRA(uri){

  window.location.href = uri;
}


function botaoDesabilita(form){
  if (eval('validate' + form.name + '(form);')) {
    for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].disabled ) {
        document.forms[0].elements[i].disabled = false;
      }
    }
	toUpperCase(document.forms[0]);
    document.forms[0].submit();
  }
}

function botaoDesabilitaSemValidacao(form){
  for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].disabled ) {
        document.forms[0].elements[i].disabled = false;
      }
    }
}


// Fun??o utilizada no mecanismode processo quando o usu?rio clica no bot?o avan?ar
function botaoAvancarTelaEspera(uri){

	//alert(uri);
 //alert(uri.match('inserirUsuarioWizardAction.do'));
 //alert(uri.value.indexOf('inserirUsuarioWizard.do'));
 
 
 
  document.forms[0].action=uri;
  var cancelarValidacao = false;

  //Parte que verifica se a valida??o foi desabilitada
  for (i=0; i < document.forms[0].elements.length; i++) {
    if (document.forms[0].elements[i].name == 'cancelarValidacao') {
      if (document.forms[0].elements[i].value == 'true') {
	cancelarValidacao = true;
	break;
      }
    }
  }

  if (cancelarValidacao || (eval('validate' + document.forms[0].name + '(document.forms[0]);'))) {
    for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].disabled ) {
        document.forms[0].elements[i].disabled = false;
      }
    }
    
    //Alterado por pedro alexandre dia 11/08/2006
    //Trecho adicionado para os processo de usu?rio
    //porque os campos login e email n?o podem ser colocados para caixa alta
    var colocarUpperCase = true;
    
    if(uri.match('inserirUsuarioWizardAction.do') != null || uri.match('atualizarUsuarioWizardAction.do') != null){
	  colocarUpperCase = false;
	}

    if(colocarUpperCase){
	  toUpperCase(document.forms[0]);
	}
	
	eval('submitForm(document.forms[0]);')
    
  }
}




// Fun??o utilizada no mecanismo de processo quando o usu?rio clica no bot?o voltar
function botaoVoltar(uri){
  document.forms[0].action = uri;
  toUpperCase(document.forms[0]);
  document.forms[0].submit();
}


function validarHora(horaMinuto) {

  if (horaMinuto != "") {
    var horaMinuto = horaMinuto.split(':');
    if (horaMinuto.length == 2) {
      if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
         horaMinuto[1] <= 59 && horaMinuto[1] >= 0){
          return true;
      }
	  else {
		return false;
      }
	}
	else {
      return false;
    }
  }
  else {
	return true;
  }

}


// Fun??o utilizada quando o usu?rio deseja desabilitar o campo na hora de submeter a pagina
function desabilitaCampos(){
    for (i=0; i < document.forms[0].elements.length; i++) {
      if (document.forms[0].elements[i].disabled ) {
        document.forms[0].elements[i].disabled = false;
      }
    }
}



/* Utilizado nos formul?rios que realizam pesquisas atrav?s do bot?o Enter
   EX: pesquisaEnter(event, String)
   event - Captura qual foi o evento realizado pelo usu?rio (Padr?o)
*/
function pesquisaEnter(tecla, caminhoActionReload, objetoPesquisa) {

	var form = document.forms[0];

	if (document.all) {
        var codigo = event.keyCode;
	}
	else{
        var codigo = tecla.which;
    }
    if (codigo == 13) {
    
    	objetoPesquisa.value = (objetoPesquisa.value * 1);
    	 
		desabilitaCampos();
        form.action = caminhoActionReload;
		toUpperCase(form);
        form.submit();
    }
	else{
        return true;
    }
}

/* Utilizado nos formularios que realizam pesquisas atraves do botao Enter
   Sem colocar os campos para uppercase
   EX: pesquisaEnterSemUpperCase(event, String)
   event - Captura qual foi o evento realizado pelo usuario (Padrao)
*/
function pesquisaEnterSemUpperCase(tecla, caminhoActionReload, objetoPesquisa) {

	var form = document.forms[0];

	if (document.all) {
        var codigo = event.keyCode;
	}
	else{
        var codigo = tecla.which;
    }

    if (codigo == 13) {
    
    	objetoPesquisa.value = (objetoPesquisa.value * 1);
    	 
		desabilitaCampos();
        form.action = caminhoActionReload;
        form.submit();
    }
	else{
        return true;
    }
}


/* Utilizado nos formul?rios que n?o realizam pesquisas atrav?s do bot?o ENTER
   EX: pesquisaEnterSemEvento(String)
*/
function pesquisaEnterSemEvento(caminhoActionReload) {

	var form = document.forms[0];
	
	desabilitaCampos();
    form.action = caminhoActionReload;
	toUpperCase(form);
    form.submit();
    
}


// Redireciona o submit do formul?rio para a url passada como par?metro
function redirecionarSubmit(caminhoAction) {

   var form = document.forms[0];
   form.action = caminhoAction;
   toUpperCase(form);
   form.submit();

   return true;

 }

//Redireciona o submit do formulario para a url passada como parametro
//sem colocar os campos do form para caixa alta
function redirecionarSubmitSemUpperCase(caminhoAction) {
   var form = document.forms[0];
   form.action = caminhoAction;
   form.submit();
   return true;
 }


/* Redireciona o submit do formul?rio para a url passada como par?metro.
   Realiza a valida??o definida no validate para o formul?rio que est? sendo utilizado */
function redirecionarSubmitComValidacao(caminhoAction) {

   if (eval('validate' + document.forms[0].name + '(document.forms[0]);')) {
	  var form = document.forms[0];
      form.action = caminhoAction;
	  toUpperCase(form);
      form.submit();
	}

   return true;
}




/* Utilizado nos formul?rios que realizam pesquisas atrav?s do bot?o Enter
   EX: validaEnter(event, String, String)
   event - Captura qual foi o evento realizado pelo usu?rio (Padr?o)
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Nome do campo que possui o c?digo para ser consultado (Recebe uma string).
   OBS - Realiza uma filtragem no que foi digitado pelo usu?rio (s? aceita inteiros maiores que zero)
*/
function validaEnter(tecla, caminhoActionReload, nomeCampo) {

	var form = document.forms[0];
	
	var objetoCampo = eval("form." + nomeCampo);
	var valorCampo = eval("form." + nomeCampo + ".value");

	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }

	if (codigo == 13) {
		if (!isNaN(valorCampo) && valorCampo.length > 0 && valorCampo.indexOf(',') == -1 &&
			valorCampo.indexOf('.') == -1 && ((valorCampo * 1) > 0)){
			
			// Retira os espa?os em branco da string passada.
			objetoCampo.value = trim(valorCampo);
			if (objetoCampo.value.length > 0){
				return pesquisaEnter(tecla, caminhoActionReload, objetoCampo);
			}
		}
	}
	else{
		return true;
	}
}

/*
*
* Valida??o do enter aceitando o valor zero como par?metro
* Data: 15/05/2007
* Autor: Raphael Rossiter
*/
function validaEnterAceitaZERO(tecla, caminhoActionReload, nomeCampo) {

	var form = document.forms[0];

	
	var objetoCampo = eval("form." + nomeCampo);
	var valorCampo = eval("form." + nomeCampo + ".value");

	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }

	if (codigo == 13) {
		if (!isNaN(valorCampo) && valorCampo.length > 0 && valorCampo.indexOf(',') == -1 &&
			valorCampo.indexOf('.') == -1){
			
			// Retira os espa?os em branco da string passada.
			objetoCampo.value = trim(valorCampo);
			if (objetoCampo.value.length > 0){
				return pesquisaEnter(tecla, caminhoActionReload, objetoCampo);
			}
		}
	}
	else{
		return true;
	}
}


/* Utilizado nos formul?rios que realizam pesquisas atrav?s do bot?o Enter
   EX: validaEnter(event, String, String)
   event - Captura qual foi o evento realizado pelo usu?rio (Padr?o)
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Nome do campo que possui o c?digo para ser consultado (Recebe uma string).
   OBS - Realiza uma filtragem no que foi digitado pelo usu?rio (s? aceita inteiros maiores que zero)
   
   Author: Raphael Rossiter, Fernanda Paiva
*/
function validaEnterComMensagem(tecla, caminhoActionReload, nomeCampoForm, nomeCampoMSG) {
	
	var form = document.forms[0];
	
	var objetoCampo = eval("form." + nomeCampoForm);
	var valorCampo = trim(eval("form." + nomeCampoForm + ".value"));
	var indesejaveis = "~{}^%$[]@|`\\<?\#?!;*>\"\'";
	var teste = true;
	
	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }

	if (codigo == 13) {
	    if (valorCampo.length == 0){
	     alert("Informe " + nomeCampoMSG + ".");
	     return false;
	    }
	
		if (!testarCampoValorZero(objetoCampo, nomeCampoMSG)){
			return false;
		}
		
		// alterado para verificar se foi digitado algum caracter especial - Fernanda Paiva - 11/07/2006
		for (var i=0; i<indesejaveis.length; i++) {
			if ((valorCampo.indexOf(indesejaveis.charAt(i))) != -1 ){
				teste = false;
			}
      	}
      	if(teste == false)
      	{
	      	alert(nomeCampoMSG + " possui caracteres especiais.");
			return false;
		}
		else if (valorCampo.length > 0 && (isNaN(valorCampo) || valorCampo.indexOf(',') != -1 ||
			valorCampo.indexOf('.') != -1)){

			alert(nomeCampoMSG + " deve somente conter números positivos.");
			return false;
		}
		else{
			validaEnter(tecla, caminhoActionReload, nomeCampoForm);
		}
	}
	else{
		return false;
	}
}


/*
*
* Valida??o do enter aceitando o valor zero como par?metro
* Data: 18/05/2007
* Autor: Raphael Rossiter
*/
function validaEnterComMensagemAceitaZERO(tecla, caminhoActionReload, nomeCampoForm, nomeCampoMSG) {

	var form = document.forms[0];

	var objetoCampo = eval("form." + nomeCampoForm);
	var valorCampo = trim(eval("form." + nomeCampoForm + ".value"));
	var indesejaveis = "~{}^%$[]@|`\\<?\#?!;*>\"\'";
	var teste = true;
	
	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }

	if (codigo == 13) {
	    if (valorCampo.length == 0){
	     alert("Informe " + nomeCampoMSG + ".");
	     return false;
	    }
	
		// alterado para verificar se foi digitado algum caracter especial - Fernanda Paiva - 11/07/2006
		for (var i=0; i<indesejaveis.length; i++) {
			if ((valorCampo.indexOf(indesejaveis.charAt(i))) != -1 ){
				teste = false;
			}
      	}
      	if(teste == false)
      	{
	      	alert(nomeCampoMSG + " possui caracteres especiais.");
			return false;
		}
		else if (valorCampo.length > 0 && (isNaN(valorCampo) || valorCampo.indexOf(',') != -1 ||
			valorCampo.indexOf('.') != -1)){

			alert(nomeCampoMSG + " deve somente conter n?meros positivos.");
			return false;
		}
		else{
			validaEnterAceitaZERO(tecla, caminhoActionReload, nomeCampoForm);
		}
	}
	else{
		return false;
	}
}

/* Utilizado nos formul?rios que realizam pesquisas atrav?s do bot?o Enter,
   difere da fun??o de validaEnter,por que pode ser passado uma string
   EX: validaEnterString(event, String, String)
   event - Captura qual foi o evento realizado pelo usu?rio (Padr?o)
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Nome do campo que possui a string para ser consultado (Recebe uma string).
   OBS - Realiza uma filtragem no que foi digitado pelo usu?rio 
*/
function validaEnterString(tecla, caminhoActionReload, nomeCampo) {

	var form = document.forms[0];

	var objetoCampo = eval("form." + nomeCampo);
	var valorCampo = eval("form." + nomeCampo + ".value");

	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }

	if (codigo == 13) {
		if (valorCampo.length > 0){

			// Retira os espa?os em branco da string passada.
			objetoCampo.value = trim(valorCampo);
			if (objetoCampo.value.length > 0){
				return pesquisaEnter(tecla, caminhoActionReload, form);
			}
		}
	}
	else{
		return true;
	}
}



/* Utilizado nos formul?rios que realizam pesquisas que n?o ir?o utilizar o bot?o ENTER
   EX: validaEnter(String, String)
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Nome do campo que possui o c?digo para ser consultado (Recebe uma string).
   OBS - Realiza uma filtragem no que foi digitado pelo usu?rio (s? aceita inteiros maiores que zero)
*/
function validaEnterSemEvento(caminhoActionReload, nomeCampo) {

	var form = document.forms[0];

	var objetoCampo = eval("form." + nomeCampo);
	var valorCampo = eval("form." + nomeCampo + ".value");

	
	if (!isNaN(valorCampo) && valorCampo.length > 0 && valorCampo.indexOf(',') == -1 &&
		valorCampo.indexOf('.') == -1 && ((valorCampo * 1) > 0)){

		// Retira os espa?os em branco da string passada.
		objetoCampo.value = trim(valorCampo);
		if (objetoCampo.value.length > 0){
			return pesquisaEnterSemEvento(caminhoActionReload);
		}
	}
	else{
		return true;
	}
}


/* Realiza uma consulta de acordo com a depend?ncia passada como par?metro
   tecla - A??o realizada pelo usu?rio (valor padr?o = event).
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Objeto HTML que ser? consultado de acordo com a depend?ncia passada (? para passar o objeto HTML e n?o uma string).
   idDependencia - C?digo do objeto que ser? utilizado como depend?ncia (? para passar um valor do tipo inteiro).
   nomeDependencia - Nome que ser? utilizado na mensagem de retorno para o usu?rio, caso ocorra alguma exce??o (? para passar uma string).
*/
function validaEnterDependencia(tecla, caminhoActionReload, nomeCampo, idDependencia, nomeDependencia){
      var form = document.forms[0];

      if (document.all) {
		var codigo = event.keyCode;
      }
	  else {
        var codigo = tecla.which;
      }
      if (codigo == 13) {

		  if( idDependencia.length < 1 || isNaN(idDependencia) || ((idDependencia * 1) == 0) ||
			  idDependencia.indexOf(',') != -1 || idDependencia.indexOf('.') != -1 || ((idDependencia * 1) == 0)){
			nomeCampo.value = "";
            alert('Informe ' + nomeDependencia);
            return false;
          }
		  else{
            validaEnter(tecla,caminhoActionReload, nomeCampo.name);
          }
      }
	  else {
		return true;
      }
}

/*
*
* Valida??o do enter aceitando o valor zero como par?metro
* Data: 15/05/2007
* Autor: Raphael Rossiter
*/
function validaEnterDependenciaAceitaZERO(tecla, caminhoActionReload, nomeCampo, idDependencia, nomeDependencia){
      var form = document.forms[0];

      if (document.all) {
		var codigo = event.keyCode;
      }
	  else {
        var codigo = tecla.which;
      }
      if (codigo == 13) {

		  if( idDependencia.length < 1 || isNaN(idDependencia) || ((idDependencia * 1) == 0) ||
			  idDependencia.indexOf(',') != -1 || idDependencia.indexOf('.') != -1 || ((idDependencia * 1) == 0)){
			nomeCampo.value = "";
            alert('Informe ' + nomeDependencia);
            return false;
          }
		  else{
            validaEnterAceitaZERO(tecla,caminhoActionReload, nomeCampo.name);
          }
      }
	  else {
		return true;
      }
}


/* Realiza uma consulta de acordo com a depend?ncia passada como par?metro
   tecla - A??o realizada pelo usu?rio (valor padr?o = event).
   caminhoActionReload - Action respons?vel por realizar a consulta.
   nomeCampo - Objeto HTML que ser? consultado de acordo com a depend?ncia passada (? para passar o objeto HTML e n?o uma string).
   idDependencia - C?digo do objeto que ser? utilizado como depend?ncia (? para passar um valor do tipo inteiro).
   nomeDependencia - Nome que ser? utilizado na mensagem de retorno para o usu?rio, caso ocorra alguma exce??o (? para passar uma string).
*/
function validaEnterDependenciaComMensagem(tecla, caminhoActionReload, nomeCampo, idDependencia, nomeDependencia, nomeCampoMSG){
      var form = document.forms[0];
	  var indesejaveis = "~{}^%$[]@|`\\<?\#?!;*>\"\'";
	  var teste = true;
	
      if (document.all) {
		var codigo = event.keyCode;
      }
	  else {
        var codigo = tecla.which;
      }
      if (codigo == 13) {

		  if (nomeCampo.value.length == 0){
	       	alert("Informe " + nomeCampoMSG + ".");
	     	return false;
	      }
	      else if (!testarCampoValorZero(nomeCampo, nomeCampoMSG)){
			return false;
		  }
		  else if ( idDependencia.length < 1 || isNaN(idDependencia) || ((idDependencia * 1) == 0) ||
			  idDependencia.indexOf(',') != -1 || idDependencia.indexOf('.') != -1 || ((idDependencia * 1) == 0)){
			
			nomeCampo.value = "";
            alert('Informe ' + nomeDependencia);
            return false;
            
          }
          
        // alterado para verificar se foi digitado algum caracter especial - Vivianne Sousa - 27/07/2006
		for (var i=0; i<indesejaveis.length; i++) {
			if ((nomeCampo.value.indexOf(indesejaveis.charAt(i))) != -1 ){
				teste = false;
			}
      	}
      	if(teste == false)
      	{
	      	alert(nomeCampoMSG + " possui caracteres especiais.");
			return false;
		}
          
          
		  else if (nomeCampo.value.length > 0 && (isNaN(nomeCampo.value) || nomeCampo.value.indexOf(',') != -1 ||
					nomeCampo.value.indexOf('.') != -1)){

			alert(nomeCampoMSG + " deve somente conter números positivos.");
			return false;
		  
		  }else{
            
            validaEnter(tecla,caminhoActionReload, nomeCampo.name);
          }
      }
	  else {
		return true;
      }
}

/*
* Aceitar zero como par?metro ZERO
* Autor: Raphael Rossiter
* Data: 17/05/2007
*/
function validaEnterDependenciaComMensagemAceitaZERO(tecla, caminhoActionReload, nomeCampo, idDependencia, nomeDependencia, nomeCampoMSG){
      var form = document.forms[0];
	  var indesejaveis = "~{}^%$[]@|`\\<?\#?!;*>\"\'";
	  var teste = true;
	
      if (document.all) {
		var codigo = event.keyCode;
      }
	  else {
        var codigo = tecla.which;
      }
      if (codigo == 13) {

		  if (nomeCampo.value.length == 0){
	       	alert("Informe " + nomeCampoMSG + ".");
	     	return false;
	      }
	      else if ( idDependencia.length < 1 || isNaN(idDependencia) || ((idDependencia * 1) == 0) ||
			  idDependencia.indexOf(',') != -1 || idDependencia.indexOf('.') != -1 || ((idDependencia * 1) == 0)){
			
			nomeCampo.value = "";
            alert('Informe ' + nomeDependencia);
            return false;
            
          }
          
        // alterado para verificar se foi digitado algum caracter especial - Vivianne Sousa - 27/07/2006
		for (var i=0; i<indesejaveis.length; i++) {
			if ((nomeCampo.value.indexOf(indesejaveis.charAt(i))) != -1 ){
				teste = false;
			}
      	}
      	if(teste == false)
      	{
	      	alert(nomeCampoMSG + " possui caracteres especiais.");
			return false;
		}
          
          
		  else if (nomeCampo.value.length > 0 && (isNaN(nomeCampo.value) || nomeCampo.value.indexOf(',') != -1 ||
					nomeCampo.value.indexOf('.') != -1)){

			alert(nomeCampoMSG + " deve somente conter n?meros positivos.");
			return false;
		  
		  }else{
            
            validaEnterAceitaZERO(tecla,caminhoActionReload, nomeCampo.name);
          }
      }
	  else {
		return true;
      }
}



function pesquisaColecaoReload(caminhoActionReload, nomeCampo) {

	var form = document.forms[0];

	var valorCampo = eval("form." + nomeCampo + ".value");
    form.action = caminhoActionReload;
	toUpperCase(form);
    form.submit();
}




// A duas fun??es abaixo tem por finalidade redimensionar um popup
/* Basta chamar a fun??o resizePage(url, largura, altura) passando como
   par?metro respectivamente o link, a nova largura e a nova altura do popup */
// EX: resizePageComLink("teste.html", 350, 230);
//=================================================================
  function resizePageComLink(url, altura, largura){
       window.location.href = url;

	   //Para abrir o popup centralizado
		var height = window.screen.height - 25;
		var width = window.screen.width;
		var top = (height - altura)/2;
		var left = (width - largura)/2;

		resizeNow(largura, altura, top, left);
  }

  function resizePageSemLink(largura, altura){

	   //Para abrir o popup centralizado
		var height = window.screen.height - 25;
		var width = window.screen.width;
		var top = (height - altura)/2;
		var left = (width - largura)/2;

		resizeNow(largura, altura, top, left);
  }

  function resizeNow(largura, altura, top, left){
	  if (top < 0) top = 0;
      window.resizeTo(largura, altura);
      window.moveTo(left , top);
  }
//=================================================================




// Realiza um reload na pagina inicial de acordo com a url passada
function chamarReload(pagina){
	opener.window.location.href=pagina
}

//Realiza um reload na pagina inicial de acordo com a url passada
function chamarReloadSubmetForm(pagina){
	opener.document.forms[0].action = pagina;
	opener.document.forms[0].submit();
}

// Realiza um reload na pagina inicial
function chamarReloadSemParametro(){
	opener.window.location.href = opener.window.location.href;
//	opener.window.location.reload();
}


// Realiza um submit na pagina inicial
function chamarSubmit(){
	toUpperCase(opener.window.document.forms[0]);
	opener.window.document.forms[0].submit();
}


// Realiza um submit na pagina inicial de acordo com a url passada
function chamarSubmitComUrl(pagina){
	toUpperCase(opener.window.document.forms[0]);
	opener.window.document.forms[0].action = pagina; 
	opener.window.document.forms[0].submit();
}


// Realiza um submit na pagina inicial com passagem de par?metro (Utilizado na localidade)
function chamarSubmitEspecial(operacao){

	if (operacao == "1" || operacao == "2"){
		opener.window.document.getElementById("limparCampos").value = "1";
	}

	toUpperCase(opener.window.document.forms[0]);
	opener.window.document.forms[0].submit();
}



/* Retorna um objeto do formul?rio de acordo com os par?metros passados
   form - Formul?rio que est? sendo utilizado (? para passar o objeto HTML e n?o uma string).
   nome - Nome do objeto HTML que ser? retornado (? para passar uma string).
*/
function returnObject(form, nome){
	var retorno;
	for(indice = 0; indice < form.elements.length; indice++){
		campo = form.elements[indice];
		if (campo.name == nome){
			retorno = campo;
			break;
		}
	}
	return retorno;
}




//Ativa todos os elementos do tipo checkbox do formul?rio existente
function marcarTodos(){

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox"){
			elemento.checked = true;
		}
	}
}


function checkRadioPeloMenosUmRadioChecked(nomeCampo) {
    var inputs    = document.getElementsByTagName( 'input' );
    for ( var i = 0; i < inputs.length; i++ ) {
   	
        if ( inputs[i].type == "radio" ) {
            if ( inputs[i].checked == true  && inputs[i].name == nomeCampo) {
            	return false;
            };
        }
    }
    return true;
}

//Desativa todos os elementos do tipo checkbox do formul?rio existente
function desmarcarTodos() {

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox"){
			elemento.checked = false;
		}
	}
}


//Ativa todos os elementos do tipo checkbox do formul?rio existente
function marcarDesmarcarTodos(identificador){

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		
		if (elemento.type == "checkbox" && elemento.id == identificador){
			if(elemento.checked) {
				elemento.checked = false;
			} else {
				elemento.checked = true;
			}
		}
	}
}

//Testa se o campo foi digitado somente com zeros
function testarCampoValorZero(valor, nomeCampo) {

	var retorno = true;
	if(valor != null && valor.value != null && trim(valor.value).length > 0){
	   var conteudo = valor.value.replace(",","");
   	   var conteudo = conteudo.replace(".","");
	
		if (isNaN(valor.value)) {
           
			for (x= 0; x < conteudo.length; x++){
				
				if (conteudo.substr(x, 1) != "0"){
					retorno = true;
					break;
				}
				else{
					retorno = false;	
				}
			}
			
			if (!retorno){
				alert( nomeCampo + ' deve somente conter números positivos.');
			}
		}
		else {
		
			var intValorCampo = valor.value * 1;
	
			if (intValorCampo == 0) {
				alert( nomeCampo + ' deve somente conter números positivos.');
        		retorno =  false;
			}
		}
	
	}
	
	return retorno;
}

function testarCampoValorZeroDecimal(valor, nomeCampo) {

	var retorno = true;
	
	var conteudo = valor.value.replace(",","");
	var conteudo = conteudo.replace(".","");
	
	if (trim(valor.value).length > 0){
	
		if (isNaN(valor.value)) {
           
			for (x= 0; x < conteudo.length; x++){
				
				if (conteudo.substr(x, 1) != "0"){
					retorno = true;
					break;
				}
				else{
					retorno = false;	
				}
			}
			
			if (!retorno){
				alert( nomeCampo + ' deve somente conter números decimais positivos.');
			}
		}
		else {
		
			var intValorCampo = valor.value * 1;
	
			if (intValorCampo == 0) {
				alert( nomeCampo + ' deve somente conter números  decimais positivos.');
        		retorno =  false;
			}
		}
	}
	
	return retorno;
}

//Testa se o campo foi digitado somente com zeros
function testarCampoValorInteiroComZero(valor, nomeCampo) {

	var retorno = true;
	if(valor != null){
	var conteudo = valor.value.replace(",","");
	var conteudo = conteudo.replace(".","");
	
	if (trim(valor.value).length > 0){
	    if (isNaN(valor.value)) {
           	 alert( nomeCampo + ' deve somente conter números positivos.');
        	 retorno =  false;
			}
		}
	}
	return retorno;
}

function testarCampoValorDecimalComZero(valor, nomeCampo) {

	var retorno = true;
	
	var conteudo = valor.value.replace(",","");
	var conteudo = conteudo.replace(".","");
	
	 var iValue = parseFloat(valor.value);
	
	if (trim(valor.value).length > 0){
	    if (isNaN(iValue)) {
           	alert( nomeCampo + ' deve somente conter números decimais positivos.');
			 retorno =  false;
			}
	}
	
	return retorno;
}



/**
* Testa se em campos Decimal tem o valor 0
* Author: Rafael Santos
* Data: 28/06/2006
*/
function testarCampoDecimalValorZero(valor, nomeCampo) {

	var retorno = true;
	
	var conteudo = valor.value.replace(",","");
	var conteudo = conteudo.replace(".","");
	
	if (trim(valor.value).length > 0){
	
		if (isNaN(valor.value)) {
           
			for (x= 0; x < conteudo.length; x++){
				
				if (conteudo.substr(x, 1) != "0"){
					retorno = true;
					break;
				}
				else{
					retorno = false;	
				}
			}
			
			if (!retorno){
				alert( nomeCampo + ' deve somente conter n?meros decimais positivos.');
			}
		}
		else {
		
			var intValorCampo = valor.value * 1;
	
			if (intValorCampo == 0) {
				alert( nomeCampo + ' deve somente conter n?meros decimais positivos.');
        		retorno =  false;
			}
		}
	}
	
	return retorno;
}




function abrirPopupDependencia(url, idDependencia, nomeMSG, altura, largura){

	if (idDependencia.length < 1 || isNaN(idDependencia) || ((idDependencia * 1) == 0)){
		alert("Informe " + nomeMSG);
	}
	else{
		abrirPopup(url , altura, largura);
	}
}





function redirecionarSubmitDependencia(caminhoAction,idDependencia,nomeMSG) {

	 if (idDependencia.length < 1 || isNaN(idDependencia) || parseInt(idDependencia) == 0){
        alert("Informe " + nomeMSG);
     }
     else{
		var form = document.forms[0];
		form.action = caminhoAction;
		toUpperCase(form);
		form.submit();
     }
}


/* Coloca toda a cadeia de caracteres em caixa alta para os campos do tipo "text" e "hidden".
   Par?metro: O formul?rio que est? sendo utilizado. */
function toUpperCase(form){

	for (var i=0;i < form.elements.length;i++){
		var elemento = form.elements[i];
		if (elemento.type == "text"){
			
			//Retira os espa?os em branco da string passada
			elemento.value = trim(elemento.value);
			
			if(elemento.className != "input"){
				elemento.value = elemento.value.toUpperCase();
			}
		}
		else if (elemento.type == "hidden"){

			//Retira os espa?os em branco da string passada
			elemento.value = trim(elemento.value);

			if(elemento.className != "input"){
				elemento.value = elemento.value.toUpperCase();
			}
		}
		
	}
}


function submeterFormPadrao(form){
     toUpperCase(form)
	 form.submit();
}




/** Formata os caracteres digitados pelo usu?rio no formato DD/MM/AAAA **/
//	Recebe como parametro o evento realizado pelo usu?rio (comando event) e o indice do campo que ser? formatado
//	Funciona em ambos os browsers (Firefox e IE).

function formataData(e, campo){

	if (blockNumbers(e)){
		campo.value = filtraCampo(campo);
		vr = campo.value;
		tam = vr.length;

		if ( tam > 2 && tam < 5 )
			campo.value = vr.substr( 0, tam - 2  ) + '/' + vr.substr( tam - 2, tam );
		if ( tam >= 5 && tam <= 8 ) 
			campo.value = vr.substr( 0, 2 ) + '/' + vr.substr( 2, 2 ) + '/' + vr.substr( 4, 4 );
	}
	else{
		if(window.event) {
			// for IE, e.keyCode or window.event.keyCode can be used
			if(e.keyCode != 13 && e.keyCode != 8 && e.keyCode != 9 && e.keyCode != 46){
				e.keyCode = 0;
			}
		}
		else{
			// netscape
			if(e.keyCode != 13 && e.keyCode != 8 && e.keyCode != 9 && e.keyCode != 46){
				e.preventDefault();
			}
		}
	}
}



/** Tratamento para os campos que ser?o formatados **/
//	Recebe como parametro o campo que ser? formatado
//	Funciona em ambos os browsers (Firefox e IE)

function filtraCampo(campo){
	var s = "";
	var cp = "";
	vr = campo.value;
	tam = vr.length;
	for (i = 0; i < tam ; i++) {  
		if (vr.substring(i,i + 1) != "/" && vr.substring(i,i + 1) != "-" && vr.substring(i,i + 1) != "."  && vr.substring(i,i + 1) != "," ){
		 	s = s + vr.substring(i,i + 1);}
	}
	campo.value = s;
	return cp = campo.value
}




/** Desabilita a digita??o de caracteres que n?o forem n?meros **/
//	Recebe como parametro o evento realizado pelo usu?rio (comando event)
//	Funciona em ambos os browsers (Firefox e IE)
	
function blockNumbers(e)
{
    var key;
    var keychar;
    var reg;
    
    if(window.event) {
        // for IE
        key = e.keyCode; 
    }
    else{
        // netscape
        key = e.which; 
    }
    

    keychar = String.fromCharCode(key);
    reg = /\d/;

    return reg.test(keychar);
    
}



/** Formata os caracteres digitados pelo usu?rio no formato HH:MM **/
//	Recebe como parametro o evento realizado pelo usu?rio (comando event) e o campo que ser? formatado
//	Funciona em ambos os browsers (Firefox e IE).

function formataHora(e, campo){

	if (blockNumbers(e)){
		campo.value = filtraCampo(campo);
		vr = campo.value;
		tam = vr.length;

		if ( tam > 2 && tam < 5 )
			campo.value = vr.substr( 0, tam - 1  ) + ':' + vr.substr( tam - 1, tam );
	}
	else{
		if(window.event) {
			// for IE
			if(e.keyCode != 13 && e.keyCode != 8 && e.keyCode != 9 && e.keyCode != 46){
				e.keyCode = 0;
			}
		}
		else{
			// netscape
			if(e.keyCode != 13 && e.keyCode != 8 && e.keyCode != 9 && e.keyCode != 46){
				e.preventDefault();
			}
		}
	}
}


/** Valida hora (HH:MM) **/
//	Recebe como parametro o campo que ser? validado
//	Funciona em ambos os browsers (Firefox e IE).

function validaHoraMinuto(timeStr) {

	var timePat = /^(\d{2}):(\d{2})(:(\d{2}))?(\s?(AM|am|PM|pm))?$/;

	var matchArray = timeStr.match(timePat);

	if (matchArray == null) {
		alert("Hora inválida.");
		return false;
	}

	hour = matchArray[1];
	minute = matchArray[2];


	if (hour < 0  || hour > 23) {
		alert("Hora deve estar entre 0 e 23.");
		return false;
	}

	if (minute < 0 || minute > 59) {
		alert ("Minuto deve estar entre 0 e 59.");
		return false;
	}

	return true;
}

// Valida??o com o nome do campo na mensagem
// Roberta Costa 03/08/06
function validaHoraMinutoMensagem(timeStr, campo) {

	var timePat = /^(\d{2}):(\d{2})(:(\d{2}))?(\s?(AM|am|PM|pm))?$/;

	var matchArray = timeStr.match(timePat);

	if (matchArray == null) {
		alert(campo + " inválida.");
		return false;
	}

	hour = matchArray[1];
	minute = matchArray[2];


	if (hour < 0  || hour > 23) {
		alert("Hora deve estar entre 0 e 23.");
		return false;
	}

	if (minute < 0 || minute > 59) {
		alert ("Minuto deve estar entre 0 e 59.");
		return false;
	}

	return true;
}

function validarHoraCompleta(horaMinutoSegundo) {
	if (horaMinutoSegundo != "") {
    	var horaMinutoSegundo = horaMinutoSegundo.split(':');
	    if (horaMinutoSegundo.length == 3) {
    		if(horaMinutoSegundo[0] != '' && horaMinutoSegundo[1] != '' && horaMinutoSegundo[2] != '' && 
    				horaMinutoSegundo[0] <= 23 && horaMinutoSegundo[0] >= 0 &&
    				horaMinutoSegundo[1] <= 59 && horaMinutoSegundo[1] >= 0 &&
    				horaMinutoSegundo[2] <= 59 && horaMinutoSegundo[2] >= 0){
          	   return true;
      	    }
   			else {
   				alert ("Hora inválida.");
  				return false;
      		}
 		}
 		else {
 			alert ("Hora inválida.");
      		return false;
    	}
	}
  	else {
 		return true;
  	}
}

// Valida??o com o nome do campo na mensagem
// Roberta Costa 03/08/06
function validarHoraCompletaMensagem(horaMinutoSegundo, campo) {
	if (horaMinutoSegundo != "") {
    	var horaMinutoSegundo = horaMinutoSegundo.split(':');
	    if (horaMinutoSegundo.length == 3) {
    		if(horaMinutoSegundo[0] <= 23 && horaMinutoSegundo[0] >= 0 &&
		       horaMinutoSegundo[1] <= 59 && horaMinutoSegundo[1] >= 0 &&
   			   horaMinutoSegundo[2] <= 59 && horaMinutoSegundo[2] >= 0){
          	   return true;
      	    }
   			else {
   				alert (campo + " inválida.");
  				return false;
      		}
 		}
 		else {
 			alert (campo + " inválida.");
      		return false;
    	}
	}
  	else {
  		alert (campo + " inválida.");
 		return true;
  	}
}

/** Utilizada para o tratamento de campos que possuam casas decimais,
	converte o caracter "," pelo caracter "." com o objetivo de evitar exceptions nos Actions. **/
//	Recebe o campo que sofrer? a modifica??o.
function converteVirgula(objText){
	if (objText.value.length > 0){
		objText.value = objText.value.replace(",", "."); 
	}

}


function convertePonto(objText){
	if (objText.value.length > 0){
		objText.value = objText.value.replace(".", ","); 
	}

}


//Compara duas datas no formato de strings
function comparaData(data1, operador, data2 ){

        sDia1 = data1.substr(0,2);
        sMes1 = data1.substr(3,2);
        sAno1 = data1.substr(6,4);
        sdata1 = new Date(sMes1 +"/"+ sDia1 +"/"+ sAno1);

        sDia2 = data2.substr(0,2);
        sMes2 = data2.substr(3,2);
        sAno2 = data2.substr(6,4);
        sdata2 = new Date(sMes2 +"/"+ sDia2 +"/"+ sAno2);

        if (operador == '<'){
                if ( sdata1 < sdata2 ){
                        return true;
                } else {
                        return false;
                }
        }

        if (operador ==  '>'){
                if ( sdata1 > sdata2 ){
                        return true;
                } else {
                        return false;
                }
        }
        
        if (operador ==  '='){
        		if ( trim(data1) == trim(data2) ){
                        return true;
                } else {
                		return false;
                }
        }
}

// data no formato dd/mm/yyyy - Retorna true se for menor q a data Atual
function compararDataComDataAtual(data){
	
	    sDia1 = data.substr(0,2);
        sMes1 = data.substr(3,2);
        sAno1 = data.substr(6,4);
        sdata1 = new Date(sMes1 +"/"+ sDia1 +"/"+ sAno1);
        
        sdata2 = new Date();
        
        if (sdata1 < sdata2){
           return true;
        }else{
           return false;
        }
}

function compararDatas(data1, data2){
	// data no formato yyyy-mm-dd
    sDia1 = data1.substr(8,2);
    sMes1 = data1.substr(5,2);
    sAno1 = data1.substr(0,4);
    sdata1 = new Date(sMes1 +"/"+ sDia1 +"/"+ sAno1);
    
    sDia2 = data2.substr(8,2);
    sMes2 = data2.substr(5,2);
    sAno2 = data2.substr(0,4);
    sdata2 = new Date(sMes2 +"/"+ sDia2 +"/"+ sAno2);
    
    if (sdata1 < sdata2){
       return -1;
    }else if(sdata1 > sdata2){
       return 1;
    } else{
    	return 0;
    }
}



//Compara dois per?dos (mesAno) no formato de strings
function comparaMesAno(mesAno1, operador, mesAno2 ){
		
        sMes1 = mesAno1.substr(0,2);
        sAno1 = mesAno1.substr(3,7);
        smesAno1 = sAno1+sMes1 ;

        sMes2 = mesAno2.substr(0,2);
        sAno2 = mesAno2.substr(3,7);
        smesAno2 = sAno2+sMes2;

        if (operador == '<'){
                if ( smesAno1 < smesAno2 ){
                        return true;
                } else {
                        return false;
                }
        }

        if (operador ==  '>'){
                if ( smesAno1 > smesAno2 ){
                        return true;
                } else {
                        return false;
                }
        }
                
        if (operador ==  '='){
        		if ( trim(smesAno1) == trim(smesAno2) ){
                        return true;
                } else {
                		return false;
                }
        }
        
        if (operador ==  '>='){
    		if ( trim(smesAno1) >= trim(smesAno2) ){
                return true;
            } else {
            	return false;
            }
        }
}

/**
*
* Fun??o similar ao trim() do asp que tir? os espa?os em branco de antes e depois do texto. 
*
**/
function trim(str){

	while (str.charAt(0) == " "){
		str = str.substr(1,str.length -1);
	}
	
	while (str.charAt(str.length-1) == " "){
		str = str.substr(0,str.length-1);
	}
	
	return str;
} 



// Formata??o: MM/AAAA 
//EX: onkeyup="mascaraAnoMes(objeto, event);" 
function mascaraAnoMes(mydata, tecla){ 
     
	 if (document.all) {
        var codigo = event.keyCode;
	 }
	 else{
        var codigo = tecla.which;
	 }

	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + '/'; 
     } 

     //if (mydata.value.length == 7){ 
        // verificaAnoMes(mydata); 
     //}
} 

//Valida um campo do tipo Ano M?s           
function verificaAnoMes(mydata) {
	
	var situacao = true;
	
	if (mydata.value.length == 7) {
	
		dia = 1;
    	mes = mydata.value.substring(0,2); 
    	ano = mydata.value.substring(3,7); 

    	if ((!isNaN(mes) || !isNaN(ano)) && (mes.indexOf('.') == -1 && mes.indexOf(',') == -1 && mes.indexOf('/') == -1) &&
			(ano.indexOf('.') == -1 && ano.indexOf(',') == -1 && ano.indexOf('/') == -1)) {
    	
    		// verifica se o mes e valido 
	    	if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
	        
	        	// verifica se o ano e valido
	        	if ((ano * 1) != 0 && (ano * 1) >= 1980) {
	        	
	        		// verifica se e ano bissexto 
	    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
	    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
	       				situacao = false; 
	    			}
	        	}
	        	else{
	        		situacao = false;
	        	}
	    	}
	    	else{
	    		situacao = false;
	    	} 
		}
		else{
			situacao = false;
		}
    }
    else if (mydata.value.length > 0){
    	situacao = false;
    }
    
    if (!situacao) { 
	   alert("Mês/Ano inválidos.");
	   mydata.value = "";
	   mydata.focus(); 
	}
	
	return situacao;
}


function verificaAnoMesEspecial(mydata) {
	
	var situacao = true;
	
	if (mydata.value.length == 7) {
	
		dia = 1;
    	mes = mydata.value.substring(0,2); 
    	ano = mydata.value.substring(3,7); 

    	if ((!isNaN(mes) || !isNaN(ano)) && (mes.indexOf('.') == -1 && mes.indexOf(',') == -1 && mes.indexOf('/') == -1) &&
			(ano.indexOf('.') == -1 && ano.indexOf(',') == -1 && ano.indexOf('/') == -1)) {
    	
    		// verifica se o mes e valido 
	    	if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
	        
	        	// verifica se o ano e valido
	        	if ((ano * 1) != 0 && (ano * 1) >= 1980) {
	        	
	        		// verifica se e ano bissexto 
	    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
	    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
	       				situacao = false;
	       				alert("Mês e/ou Ano inválidos!");
	   					mydata.value = "";
	   					mydata.focus(); 
	    			}
	        	}
	        	else{
	        		situacao = false;
	        		alert("Mês e/ou Ano inválidos!");
	   				mydata.value = "";
	   				mydata.focus();
	        	} 
	    	}
	    	else{
	    		situacao = false;
	    		alert("Mês e/ou Ano inválidos!");
	   			mydata.value = "";
	   			mydata.focus();
	    	} 
		}
		else{
			situacao = false;
			alert("Mês e/ou Ano inválidos!");
	   		mydata.value = "";
	   		mydata.focus();
		}
    }
    
    
    return situacao;
}

// valida AnoMes sem retornar um alert
function validaAnoMesSemAlert(mydata) {
	
	var situacao = true;
	
	if (mydata.value.length == 7) {
	
		dia = 1;
    	mes = mydata.value.substring(0,2); 
    	ano = mydata.value.substring(3,7); 

    	if ((!isNaN(mes) || !isNaN(ano)) && (mes.indexOf('.') == -1 && mes.indexOf(',') == -1 && mes.indexOf('/') == -1) &&
			(ano.indexOf('.') == -1 && ano.indexOf(',') == -1 && ano.indexOf('/') == -1)) {
    	
    		// verifica se o mes e valido 
	    	if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
	        
	        	// verifica se o ano e valido
	        	if ((ano * 1) != 0 && (ano * 1) >= 1980) {
	        	
	        		// verifica se e ano bissexto 
	    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
	    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
	       				situacao = false;
//	       				mydata.value = "";
	   					mydata.focus(); 
	    			}
	        	}
	        	else{
	        		situacao = false;
//	        		mydata.value = "";
	   				mydata.focus();
	        	} 
	    	}
	    	else{
	    		situacao = false;
//	    		mydata.value = "";
	   			mydata.focus();
	    	} 
		}
		else{
			situacao = false;
//	   		mydata.value = "";
	   		mydata.focus();
		}
    } else {
    	situacao = false;
    }
    return situacao;
}

//Author:Rafael Santos Data:30/03/2006 - Verifica o Ano Mes com a mensagem do alert personalida ao campo
function verificaAnoMesMensagemPersonalizada(mydata,mensagem, isLimitado) {
	
	if(isLimitado == null){
		isLimitado = true;
	}

	var limitado=isLimitado;
	
	var situacao = true;
	if (mydata.value.length > 0) {
	
		if (mydata.value.length == 7) {
	
			dia = 1;
    		mes = mydata.value.substring(0,2); 
	    	ano = mydata.value.substring(3,7); 

    		if ((!isNaN(mes) || !isNaN(ano)) && (mes.indexOf('.') == -1 && mes.indexOf(',') == -1 && mes.indexOf('/') == -1) &&
				(ano.indexOf('.') == -1 && ano.indexOf(',') == -1 && ano.indexOf('/') == -1)) {
    	
    			// verifica se o mes e valido 
	    		if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
	        
		        	// verifica se o ano e valido
	    			if(limitado==true){
		        		if ((ano * 1) != 0 && (ano * 1) >= 1980) {
		    	        	
				        	// verifica se e ano bissexto 
				    		if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
				    			|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
				       			situacao = false;
				       			alert(mensagem);
				       			mydata.value = "";
				   				mydata.focus(); 
				    		}
				        }
				        else{
				        	situacao = false;
				        	alert(mensagem);
				        	mydata.value = "";
				   			mydata.focus();
				        }
	    			}else{

	    				if ((ano * 1) != 0) {
		    	        	
				        	// verifica se e ano bissexto 
				    		if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
				    			|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
				       			situacao = false;
				       			alert(mensagem);
				       			mydata.value = "";
				   				mydata.focus(); 
				    		}
				        }
				        else{
				        	situacao = false;
				        	alert(mensagem);
				        	mydata.value = "";
				   			mydata.focus();
				        }
	    			}
			    }
		    	else{
	    			situacao = false;
	    			alert(mensagem);
	   				mydata.value = "";
		   			mydata.focus();
		    	} 
			}
			else{
				situacao = false;
				alert(mensagem);
		   		mydata.value = "";
		   		mydata.focus();
			}
	    }else{
			situacao = false;
			alert(mensagem);
	   		mydata.value = "";
	   		mydata.focus();
		}
	}
    
    
    return situacao;
}

// valida se o campo contém números positivos e inteiros
function verificaNumero(valor) {
	if(valor.value != "") {
		if(!validaCampoInteiroPositivo(valor.value)) {
			alert('Este campo deve conter apenas números positivos.');
			valor.value = "";
			valor.focus();
			return false;
		}
	}	 
}


// valida se ano mes eh num?rico - Fernanda Karla
function validaAnoMesNumerico(mydata) {
	var situacao = true;
	if (mydata != "") {
		mes = mydata.value.substring(0,2); 
    	ano = mydata.value.substring(3,7); 
    	if (isNaN(mes) || isNaN(ano)){
			situacao = false;
	   		mydata.value = "";
	  		mydata.focus();
		}
	}
	return situacao;
}

// Formata??o de Data: DD/MM/AAAA 
//EX: onkeyup="mascara_data(this, event);" 
function mascaraData(mydata, tecla){ 
     
	 
	 
	 if (document.all) {
        var codigo = event.keyCode;
	 }
	 else{
        var codigo = tecla.which;
	 }

	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + '/'; 
     } 

     /*if (mydata.value.length == 10){ 
         verificaData(mydata); 
     }*/
     
	 if (mydata.value.length == 5 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + '/'; 
     } 
	 
} 

// Formatação de Hora: HH:MM
//EX: onkeyup="mascaraHora(this, event);" 
function mascaraHora(mydata, tecla){ 
	 
	 var valido = false;
	 
	 if (document.all) {
        var codigo = event.keyCode;
	 } else {
        var codigo = tecla.which;
	 }
	 
	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }  
     
     if (mydata.value.length == 5){ 
		//valido = false;
    	var horaMinuto = mydata.value.split(':');
    	if (horaMinuto.length == 2) {    
    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0){
    			valido = true;
    		}
    	}
    	
    	if (!valido)  {
			alert('Hora inválida.');
			mydata.value =  '';
			mydata.focus();
    	}
    }
    
    return valido;	
}


// Formata??o de Hora: HH:MM
//EX: onkeyup="mascaraHora(this, event);" 
function mascaraHoraSemMensagem(mydata, tecla){ 
	 
	 var valido = false;
	 
	 if (document.all) {
        var codigo = event.keyCode;
	 } else {
        var codigo = tecla.which;
	 }
	 
	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }  
     
     if (mydata.value.length == 5){ 
		
    	var horaMinuto = mydata.value.split(':');
    	if (horaMinuto.length == 2) {    
    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0){
    			valido = true;
    		}
    	}
    }
    
    return valido;	
}

// Formata Hora: HH:MM e exibe a mensagem de erro com o nome do campo
// EX: onkeyup="mascaraHormascaraHoraMensagema(this, event, 'Nome Campo');" 
// Roberta Costa 01/08/2006
function mascaraHoraMensagem(mydata, tecla, campo){ 
	 if (document.all) {
        var codigo = event.keyCode;
	 } else {
        var codigo = tecla.which;
	 }
	 
	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }  
     
     if (mydata.value.length == 5){ 
		var valido = false;
    	var horaMinuto = mydata.value.split(':');
    	if (horaMinuto.length == 2) {    
    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0){
    			valido = true;
    		}
    	}
    	
    	if (!valido)  {
			alert(campo+' inválida.'  );
			mydata.value =  '';
			mydata.focus();
    	}
    }	
}

// Formata??o e Valida??o Hora: HH:MM:SS
//EX: onkeyup="mascaraHoraMinutoSegundo(this, event);" 
function mascaraHoraMinutoSegundo(mydata, tecla){ 
	 if (document.all) {
        var codigo = event.keyCode;
	 } else {
        var codigo = tecla.which;
	 }
	 
	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }
	 
	 if (mydata.value.length == 5 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }
     
     if (mydata.value.length == 8){ 
		var valido = false;
    	var horaMinuto = mydata.value.split(':');
    	if (horaMinuto.length == 3) {    
    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0 &&
				horaMinuto[2] <= 59 && horaMinuto[2] >= 0){
    			valido = true;
    		}
    	}
    	
    	if (!valido)  {
			alert('Hora inválida.');
			mydata.value =  '';
			mydata.focus();
    	}
    }	
}


// Formata Hora: HH:MM:SS e exibe a mensagem de erro com o nome do campo
// EX: onkeyup="mascaraHoraMinutoSegundo(this, event, 'Nome Campo');" 
// Roberta Costa 01/08/2006
function mascaraHoraMinutoSegundoMensagem(mydata, tecla, campo){ 
	 if (document.all) {
        var codigo = event.keyCode;
	 } else {
        var codigo = tecla.which;
	 }
	 
	 if (mydata.value.length == 2 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }
	 
	 if (mydata.value.length == 5 && (codigo != 8 && codigo != 46)){ 
         mydata.value = mydata.value + ':'; 
     }
     
     if (mydata.value.length == 8){ 
		var valido = false;
    	var horaMinuto = mydata.value.split(':');
    	if (horaMinuto.length == 3) {    
    		if(horaMinuto[0] <= 23 && horaMinuto[0] >= 0 &&
    			horaMinuto[1] <= 59 && horaMinuto[1] >= 0 &&
				horaMinuto[2] <= 59 && horaMinuto[2] >= 0){
    			valido = true;
    		}
    	}
    	
    	if (!valido)  {
			alert(campo+' inválida.');
			mydata.value =  '';
			mydata.focus();
    	}
    }	
}

//Author: Rafael Santos Data: 30/03/2006 Valida a se a Data ? uma data v?lida e critica com a mensagem personalizada enviada          
function verificaDataMensagemPersonalizada(mydata,mensagem) {
	
	
  situacao = true;      
  		
			
   if (mydata.value.length > 0) {
		
	 	if (mydata.value.length == 10) {
	
			
			dia = mydata.value.substring(0,2);
            mes = mydata.value.substring(3,5); 
            ano = mydata.value.substring(6,10); 

            if(dia != '' && !validaCampoInteiroPositivo(dia)) {
    			alert('Este campo deve conter apenas números positivos.');
    			mydata.value = "";
    			mydata.focus();
    			return false;
    		}
            
            if(mes != '' && !validaCampoInteiroPositivo(mes)) {
    			alert('Este campo deve conter apenas números positivos.');
    			mydata.value = "";
    			mydata.focus();
    			return false;
    		}
            
            if(ano != '' && !validaCampoInteiroPositivo(ano)) {
    			alert('Este campo deve conter apenas números positivos.');
    			mydata.value = "";
    			mydata.focus();
    			return false;
    		}
            
            
            if(!isNaN(dia) && !isNaN(mes) && !isNaN(ano)){
	            
              
		            // verifica se o mes e valido 
		            if ((mes * 1) >= 1 && (mes * 1) <= 12 ) {
		            
		            	// verifica se o ano e valido
			        	if ((ano * 1) != 0 && (ano * 1) >= 1900) {
			        	
			        		// verifica se e ano bissexto 
			    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
			    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
			       				situacao = false; 
			    			}
			    			// verifica o dia valido para cada mes
			    			else if (((dia * 1) < 1 || (dia * 1) > 30) && ((mes * 1) == 4 || (mes * 1) == 6 || (mes * 1) == 9 || (mes * 1) == 11 ) || (dia * 1) > 31){
			    				situacao = false;
			    			}
			        	}
			        	else{
			        		situacao = false;
			        	} 
			        }
			        else{	            
		                situacao = false; 
		            }
	        }
            else{
              situacao = false; 
            } 
    
            if (situacao == false) { 
                alert(mensagem);
                
                mydata.value='';
                mydata.focus(); 
            } 

			}else{
				situacao = false;
	    	   alert(mensagem);
                
		         mydata.value='';
		         mydata.focus(); 
  			
				
			}
				
	}
				
	return situacao;
            
} 

//Verifica o tamanho da hora - Ricardo Rodrigues.
function verifica_tamanho_hora(hora){
	
	if (hora.value != "" && hora.value.length < 8){    	
    	alert('Hora Inválida.');
    	hora.value = "";		
    	hora.focus();
    	return false;    	
    }
}

// Gera uma máscara de data e verifica se é válida. - Ricardo Rodrigues.
function mascara_data(data){
    var mydata = ''; 
    mydata = mydata + data.value;
    
    if (mydata.length == 2){ 
        mydata = mydata + '/'; 
        data.value = mydata; 
    } 
    if (mydata.length == 5){ 
        mydata = mydata + '/'; 
        data.value = mydata; 
    } 
    if (mydata.length == 10){    	
        verifica_data(data);
    } 
} 

// Verifica o tamanho da data - Ricardo Rodrigues.
function verifica_tamanho_data(data){
	
	if (data.value != "" && data.value.length < 10){    	
    	alert('Data Inválida.');
    	data.value = "";		
    	data.focus();
    	return false;    	
    }
}
 
// Verifica se é uma data válida. - Ricardo Rodrigues.
function verifica_data(data) { 

  var dia = (data.value.substring(0,2)); 
  var mes = (data.value.substring(3,5)); 
  var ano = (data.value.substring(6,10)); 
  var situacao = false;
  
  if (data.value != "") { 
	  
	  situacao = true;
	  
	  if(dia != "") {
		if(!validaCampoInteiroPositivo(dia)) {
			alert('Este campo deve conter apenas números positivos.');
			dia = "";
			data.value = "";		
			data.focus();
			return false;
		}
	  }
	  
	  if(mes != "") {
		if(!validaCampoInteiroPositivo(mes)) {
			alert('Este campo deve conter apenas números positivos.');
			mes = "";
			data.value = "";
			data.focus();
			return false;
		}
	  }
	  
	  if(ano != "") {
		if(!validaCampoInteiroPositivo(ano)) {
			alert('Este campo deve conter apenas números positivos.');
			ano = "";
			data.value = "";
			data.focus();
			return false;
		}
	  }
	  
	   
	  // verifica o dia valido para cada mes 
	  if ((dia < 01)||(dia < 01 || dia > 30) && (  mes == 04 || mes == 06 || mes == 09 || mes == 11 ) || dia > 31) {
	      situacao = false;
	  } 
	
	  // verifica se o mes e valido 
	  if (mes < 01 || mes > 12 ) { 
	      situacao = false; 
	  } 
	
	  // verifica se e ano bissexto 
	  if (mes == 2 && ( dia < 01 || dia > 29 || ( dia > 28 && (parseInt(ano / 4) != ano / 4)))) { 
	      situacao = false; 
	  }
 
  }

  if (!situacao) { 
      alert("Data inválida!");
      data.value = "";
      data.focus(); 
  } 
}

//Compara duas datas no formato de strings com maior ou igual
function comparaDatas(data1, operador, data2 ){

        sDia1 = data1.substr(0,2);
        sMes1 = data1.substr(3,2);
        sAno1 = data1.substr(6,4);
        sdata1 = new Date(sMes1 +"/"+ sDia1 +"/"+ sAno1);

        sDia2 = data2.substr(0,2);
        sMes2 = data2.substr(3,2);
        sAno2 = data2.substr(6,4);
        sdata2 = new Date(sMes2 +"/"+ sDia2 +"/"+ sAno2);

        if (operador == '<'){
                if ( sdata1 <= sdata2 ){
                        return true;
                } else {
                        return false;
                }
        }

        if (operador ==  '>'){
                if ( sdata1 >= sdata2 ){
                        return true;
                } else {
                        return false;
                }
        }
        
        if (operador ==  '='){
        		if ( trim(data1) == trim(data2) ){
                        return true;
                } else {
                		return false;
                }
        }
                
}

//Valida a se a Data ? uma data v?lida           
function verificaData (mydata) {
			dia = mydata.value.substring(0,2);
            mes = mydata.value.substring(3,5); 
            ano = mydata.value.substring(6,10); 

            situacao = true;      
                
            if(!isNaN(dia) && !isNaN(mes) && !isNaN(ano)){
	            
              
		            // verifica se o mes e valido 
		            if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
			             // verifica se o ano e valido
			        	if ((ano * 1) != 0 && (ano * 1) >= 1900) {
			        	
			        		// verifica se e ano bissexto 
			    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
			    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
			       				situacao = false; 
			    			}
			    			// verifica o dia valido para cada mes
			    			else if (((dia * 1) < 1 || (dia * 1) > 30) && ((mes * 1) == 4 || (mes * 1) == 6 || (mes * 1) == 9 || (mes * 1) == 11 ) || (dia * 1) > 31){
			    				situacao = false;
			    			}
			        	}
			        	else{
			        		situacao = false;
			        	} 
			        }
			        else{	            
		                situacao = false; 
		            }
	        }
            else{
              situacao = false; 
            } 
    
            if (situacao == false) { 
                alert("Data inválida.");
                
                mydata.value='';
                mydata.focus(); 
            } 
            
            return situacao;
}

//Valida a se a Data ? uma data v?lida Mensagem
function verificaDataMensagem(mydata, mensagem) {
			dia = mydata.value.substring(0,2);
            mes = mydata.value.substring(3,5); 
            ano = mydata.value.substring(6,10); 

            situacao = true;      
                
            if(!isNaN(dia) && !isNaN(mes) && !isNaN(ano)){
	            
              
		            // verifica se o mes e valido 
		            if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
			             // verifica se o ano e valido
			        	if ((ano * 1) != 0 && (ano * 1) >= 1900) {
			        	
			        		// verifica se e ano bissexto 
			    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
			    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
			       				situacao = false; 
			    			}
			    			// verifica o dia valido para cada mes
			    			else if (((dia * 1) < 1 || (dia * 1) > 30) && ((mes * 1) == 4 || (mes * 1) == 6 || (mes * 1) == 9 || (mes * 1) == 11 ) || (dia * 1) > 31){
			    				situacao = false;
			    			}
			        	}
			        	else{
			        		situacao = false;
			        	} 
			        }
			        else{	            
		                situacao = false; 
		            }
	        }
            else{
              situacao = false; 
            } 
    
            if (situacao == false) { 
                alert(mensagem);
                
                mydata.value='';
                mydata.focus(); 
            } 
            
            return situacao;
}     


//Valida a se a Data ? uma data v?lida Mensagem e naum apaga a data do campo
function verificaDataMensagemSemApagar(mydata, mensagem) {
			dia = mydata.value.substring(0,2);
            mes = mydata.value.substring(3,5); 
            ano = mydata.value.substring(6,10); 

            situacao = true;      
                
            if(!isNaN(dia) && !isNaN(mes) && !isNaN(ano)){
	            
              
		            // verifica se o mes e valido 
		            if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
			             // verifica se o ano e valido
			        	if ((ano * 1) != 0 && (ano * 1) >= 1900) {
			        	
			        		// verifica se e ano bissexto 
			    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
			    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
			       				situacao = false; 
			    			}
			    			// verifica o dia valido para cada mes
			    			else if (((dia * 1) < 1 || (dia * 1) > 30) && ((mes * 1) == 4 || (mes * 1) == 6 || (mes * 1) == 9 || (mes * 1) == 11 ) || (dia * 1) > 31){
			    				situacao = false;
			    			}
			        	}
			        	else{
			        		situacao = false;
			        	} 
			        }
			        else{	            
		                situacao = false; 
		            }
	        }
            else{
              situacao = false; 
            } 
    
            if (situacao == false) { 
                alert(mensagem);
                
                mydata.focus(); 
            } 
            
            return situacao;
}    


// Retorna a quantidade de algarismos que comp?em a parte inteira de um n?mero decimal
// EX: obterQuantidadeInteiro(10,33) - Retorno = 2
function obterQuantidadeInteiro(valor){

	var contadorInteiro = 0;
	
	if (valor.length > 0 && parseFloat(valor)){
		
		var valorReplace = valor.replace(",",".");
			
			
		for (x = 0; x < valorReplace.length; x++){
				
			if (valorReplace.substr(x, 1) != "."){
				contadorInteiro++;
			}
			else{
				break;
			}
		}
	}

	return contadorInteiro;
}


// Retorna a quantidade de algarismos que comp?em a fra??o de um n?mero decimal
// EX: obterQuantidadeFracao(10,3345) - Retorno = 4
function obterQuantidadeFracao(valor){

	var contadorFracao = 0;
	var begin = false;
	
	if (valor.length > 0 && parseFloat(valor)){
		
		var valorReplace = valor.replace(",",".");
			
		for (x = 0; x < valorReplace.length; x++){
				
			if (valorReplace.substr(x, 1) == "."){
				begin = true;
			}
			else if (begin){
				contadorFracao++;
			}
		 
		}
	}

	return contadorFracao;
}


// Retorna os algarismos sem virgula e ponto
// EX: obterNumerosSemVirgulaEPonto(10,3345) - Retorno = 103345
function obterNumerosSemVirgulaEPonto(valor){

	var begin = false;
	
	if (valor.length > 0){
		begin = true;
		var valorReplaceVirgula = valor.replace(",","");
		var valorReplacePonto = valorReplaceVirgula.replace(".","");
	}

	return valorReplacePonto;
}


/*
 Fun??o para Auto tabbing sem precisar dar enter
 */
 
 function autotab(original,destination){
  if (original.getAttribute && original.value.length == original.getAttribute("maxlength")){
     destination.focus();
   }
 }
 
 
//Caso o campo n?o seja informado, ser? selecionado para receber o foco o primeiro campo do formul?rio
// que nao seja do tipo "hidden" e n?o esteja desabilitado.
function setarFoco(nomeCampo){
	var form = document.forms[0];

 	if (nomeCampo != undefined && nomeCampo.length > 0){
    	for (var i=0;i < form.elements.length;i++){
    		var elemento = form.elements[i];
   			if (elemento.name == nomeCampo){
    			objetoCampo = elemento;
    			break;
   			}
  		}
  		if (objetoCampo!= null && !objetoCampo.disabled){
   			objetoCampo.focus();
   		}else{
     		var proximo = false;
   			for (var i=0;i < form.elements.length;i++){
  				var elemento = form.elements[i];
    			if (elemento.name == objetoCampo.name){
     				proximo = true;
    			}else if (proximo && (elemento.type != "hidden" && elemento.disabled == false)){
     				elemento.focus();
     				break;
    			}
   			}
  		}
 	}else{
 		for (var i=0;i < form.elements.length;i++){
     		var elemento = form.elements[i];
			if (elemento.type != "hidden" && elemento.disabled == false && elemento.name != "ultimoacesso"){
    			elemento.focus();
    			break;
   			}
  		}
 	}
}


	//Esta fun??o ser? chamada no evento onKeyUp do objeto.
	//EX: <INPUT TYPE="text" NAME="teste" onKeyup="formataValorDecimalUmaCasa(this, 11)">
	function formataValorDecimalUmaCasa(campo,tammax){
		var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		valor = campo.value;
			
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}
 		}

		//verifica tamanho (tamanho m?ximo vindo do m?todo)
		if(tammax > 0 && tammax < valorAuxiliar.length)
			valorAuxiliar = valorAuxiliar.substring(0,tammax);

		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 3 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		digitosNumericos = valor.length;

		//insere pontos decimais
		for(i = 1;i<=(digitosNumericos/3);i++)
			valor = valor.substring(0,digitosNumericos + 2 - 3*i) +
				(i==1?',':'.') +
				valor.substring(digitosNumericos + 2 - 3*i);
					
		campo.value = valor;
	}
	
	
	function formataValorMonetario(campo,tammax){
		var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		valor = campo.value;
			
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}
 		}

		//verifica tamanho (tamanho m?ximo vindo do m?todo)
		if(tammax > 0 && tammax < valorAuxiliar.length)
			valorAuxiliar = valorAuxiliar.substring(0,tammax);

		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 3 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		digitosNumericos = valor.length;

		//insere pontos decimais
		for(i = 1;i<=(digitosNumericos/3);i++)
			valor = valor.substring(0,digitosNumericos + 1 - 3*i) +
				(i==1?',':'.') +
				valor.substring(digitosNumericos + 1 - 3*i);
					
		campo.value = valor;
	}
	
	function formataValorMonetarioQuatroDecimais(campo, tamMax){
		var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		valor = campo.value;
			
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}
 		}

		//verifica tamanho (tamanho maximo vindo do metodo)
		if(tamMax > 0 && tamMax < valorAuxiliar.length)
			valorAuxiliar = valorAuxiliar.substring(0,tamMax);

		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 5 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		digitosNumericos = valor.length;

		//insere pontos decimais, se necessario
		if (digitosNumericos >= 5){		
		  // coloca a vírgula com as 4 casas
		  valor = valor.substring(0, digitosNumericos-4) + ',' + valor.substring(digitosNumericos-4);
		  
		  // verifica o que precisará ser formatado antes da vírgula
		  posVirgula = valor.indexOf(',');
		  formatInteiro = valor.substring(0, posVirgula);
		  if (formatInteiro.length > 3){
		  	valorVirgula = valor.substring(posVirgula);
		  	
		  	digitosNumericos = formatInteiro.length;
		  	for(i = 1;i<(digitosNumericos/3);i++){		  		
					formatInteiro = formatInteiro.substring(0,digitosNumericos - 3*i) +
									'.' + formatInteiro.substring(digitosNumericos - 3*i);
			}
			valor = formatInteiro + valorVirgula;
		  }		  
		}
		campo.value = valor;	
	}
	
	function formataValorDecimalTresCasas(campo,tamMax){
		var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		valor = campo.value;
			
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}
 		}

		//verifica tamanho (tamanho maximo vindo do metodo)
		if(tamMax > 0 && tamMax < valorAuxiliar.length)
			valorAuxiliar = valorAuxiliar.substring(0,tamMax);

		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 4 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		digitosNumericos = valor.length;

		//insere pontos decimais, se necessario
		if (digitosNumericos >= 4){		
		  // coloca a vírgula com as 3 casas
		  valor = valor.substring(0, digitosNumericos-3) + ',' + valor.substring(digitosNumericos-3);
		}
		campo.value = valor;
	}
	
	function formataValorDecimal(campo, casasDecimais, e) {
		// Ignora a telca Tab
		if (e == null || e.keyCode != '9') {
			var digitosValidos = "0123456789,";

			var valor = campo.value;
	   	var valorAuxiliar = "";
		
			var tamanhoMaximo = campo.maxLength;
			var tamanhoInteiro = tamanhoMaximo - casasDecimais - 1;

			// retira digitos invalidos
			var achouVirgula = 'N';
			var posVirgula = '';
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
					if ((valor.charAt(i) == ',' && achouVirgula == 'N' && i != 0) || (valor.charAt(i) != ',')) {
 				valorAuxiliar += valor.charAt(i);

						if (valor.charAt(i) == ',') {
							achouVirgula = 'S';
							posVirgula = valorAuxiliar.length;
						}
					}
				}
			}

			valorAuxiliar = valorAuxiliar.substr(0, tamanhoMaximo);
		
			digitosNumericos = valorAuxiliar.length;

			if (achouVirgula == 'S') {
				var parteInteiraDigitada = valorAuxiliar.substr(0, posVirgula - 1);
				var parteDecimalDigitada = valorAuxiliar.substr(posVirgula, casasDecimais);

				var tamanhoParteInteiraDigitada = parteInteiraDigitada.length;
				var tamanhoParteDecimalDigitada = parteDecimalDigitada.length;
 
				if (tamanhoParteInteiraDigitada > tamanhoInteiro) {
					parteInteiraDigitada = parteInteiraDigitada.substr(0, tamanhoInteiro);
	}
	
				if (parteDecimalDigitada > casasDecimais) {
					parteDecimalDigitada = parteDecimalDigitada.substr(0, casasDecimais);
		}

				if (parteInteiraDigitada != '') {
					valorAuxiliar = parteInteiraDigitada + ',' + parteDecimalDigitada;
	 	} else {
					valorAuxiliar = '';
		}
			} else {
				valorAuxiliar = valorAuxiliar.substr(0, tamanhoInteiro);
	}
	
			campo.value = valorAuxiliar;
		}
	}
	
	function formataValorDecimalPermiteNegativo(campo, casasDecimais) {
		var valorAuxiliar = "";
		var digitosValidos = "-0123456789";
		var valor = campo.value;
		var numeroNegativo = false;
	
		if(valor.charAt(0) == "-"){
			campo.maxLength = 7;
			numeroNegativo = true;
		}else{
			campo.maxLength = 6;
				}
		var tamanhoMaximo = campo.maxLength;
	
		// retira digitos nao numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
				if((valor.charAt(i) == "-" && i == 0) || valor.charAt(i) != "-"){
 				valorAuxiliar += valor.charAt(i);
 			}
 		}
		}
 		
		if (valor.length == tamanhoMaximo) {
			valorAuxiliar = valorAuxiliar.substring(0, tamanhoMaximo - 1);
 	}

		// verifica tamanho (tamanho maximo vindo do metodo)
		if(tamanhoMaximo > 0 && tamanhoMaximo < valorAuxiliar.length) {
			valorAuxiliar = valorAuxiliar.substring(0, tamanhoMaximo);
		}
 	
		// retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > (casasDecimais + 1) && valorAuxiliar.charAt(0) == "0") {
			valorAuxiliar = valorAuxiliar.substring(1);
		}

		valor = valorAuxiliar;
		digitosNumericos = valor.length;
		if(numeroNegativo){
			digitosNumericos = valor.length - 1;
		} else {
			digitosNumericos = valor.length;
    }

		// insere pontos decimais, se necessario
		if (digitosNumericos >= (casasDecimais + 1)) {
			// coloca a vírgula com as X casas
			if(numeroNegativo){
				//alert("digitosNumericos: " + digitosNumericos + "#13" + "casasDecimais: " + casasDecimais + "#13" + "valor: " + valor);
				valor = valor.substring(0, (digitosNumericos - casasDecimais)+1) + ',' + valor.substring((digitosNumericos - casasDecimais)+1);
			} else {
				//alert("digitosNumericos: " + digitosNumericos + "#13" + "casasDecimais: " + casasDecimais + "#13" + "valor: " + valor);
				valor = valor.substring(0, digitosNumericos - casasDecimais) + ',' + valor.substring(digitosNumericos - casasDecimais);
			
			}
		}
		
		campo.value = valor;
	}

	function formataValorQuantidade(campo,tammax){
		var valorAuxiliar = "";
		digitosValidos = "0123456789" ;

		valor = campo.value;

		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
	}
    }
    
		//verifica tamanho (tamanho m?ximo vindo do m?todo)
		if(tammax > 0 && tammax < valorAuxiliar.length)
			valorAuxiliar = valorAuxiliar.substring(0,tammax);
    	 
		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 3 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		digitosNumericos = valor.length;

		//insere pontos decimais
		for(i = 1;i<=(digitosNumericos/3);i++)
			valor = valor.substring(0,digitosNumericos + 1 - 3*i) +
				(i==1?',':'') +
				valor.substring(digitosNumericos + 1 - 3*i);
					
		campo.value = valor;
    }
	
	//Esta fun??o sera ? chamada no evento onKeyUp do objeto.
	//EX: <INPUT TYPE="text" NAME="teste" onKeyup="formataValorMonetario(this, 11)">
	function verificaNumeroInteiro(campo){
	   	var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		valor = campo.value;
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
    }
}


		//retira zeros desnecess?rios ao in?cio do n?mero
		while (valorAuxiliar.length > 1 && valorAuxiliar.charAt(0) == "0")
			valorAuxiliar = valorAuxiliar.substring(1);

		valor = valorAuxiliar;
		campo.value = valor;
		
	}

	function replicarCampo(fim,inicio) {
		fim.value = inicio.value;
			}
			
	function replicarCampoSeVazio(fim,inicio) {
		if (fim.value == "") {
			fim.value = inicio.value;
		}
	}

	function emailValido(campo)  {
		var str= campo;
		var filter=/^.+@.+\..{2,3}$/;
	
	 	if (filter.test(str)) {
	    	testresults=true;
	 	} else {
	    	testresults=false;
		}
	 return (testresults)
		}
	
	//--------------- Calcular Percentual
	// a funcao recebe dois valores, pega o primeiro valor(valor1) multiplica por 100 
	// e divide pelo segundo valor(valor2)
	function calcularPercentual(valor1, valor2){
		var valorTotal = 0;
		
		valorTotal = (valor1 * 100)/valor2;
		
		return valorTotal;
	}
	
	
	//Desmarca o botao de radio
	//Tiago Moreno
	//Ex: <input type="radio" name="radio"	value="1" checked="true" onclick="limpaRadioButton(this);" id = "1">
	function limpaRadioButton(nomeCampo){
		var form = document.forms[0];
	  	if(nomeCampo.checked && nomeCampo.id != "0"){
	   		nomeCampo.checked = false;
			nomeCampo.id = "0";
		}else{
			nomeCampo.id = "1";
			for (i=0; i < form.elements.length; i++){
				if( form.elements[i].type == "radio" && form.elements[i].name == nomeCampo.name 
						&& form.elements[i].checked == false ){
					form.elements[i].id = 0;
				}
			}
		}
	}
	
	//Esta fun??o sera ? chamada para verificar se o campo passado ? um n?mero inteiro
	function validaCampoInteiro(campo){
	   	var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
		
		var retorno = true;
		
		valor = campo.value;
		//retira digitos n?o numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}else{
 				retorno = false;
 				break;
 			}
 		}
 		
 		return retorno;
 	}
	
	
/* Utilizado nos formularios que realizam pesquisas atraves do botao Enter
   Sem colocar os campos para uppercase.	
   EX: validaEnterSemUpperCase(event, String, String)
   event - Captura qual foi o evento realizado pelo usuario (Padrao)
   caminhoActionReload - Action responsavel por realizar a consulta.
   nomeCampo - Nome do campo que possui o codigo para ser consultado (Recebe uma string).
   OBS - Realiza uma filtragem no que foi digitado pelo usuario (so aceita inteiros maiores que zero)
	*/
function validaEnterSemUpperCase(tecla, caminhoActionReload, nomeCampo) {
	
	var form = document.forms[0];
		
			
	var objetoCampo = eval("form." + nomeCampo);
	var valorCampo = eval("form." + nomeCampo + ".value");
				
	if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }
				
	if (codigo == 13) {
		if (!isNaN(valorCampo) && valorCampo.length > 0 && valorCampo.indexOf(',') == -1 &&
			valorCampo.indexOf('.') == -1 && ((valorCampo * 1) > 0)){
					
			// Retira os espacos em branco da string passada.
			objetoCampo.value = trim(valorCampo);
			if (objetoCampo.value.length > 0){
				return pesquisaEnterSemUpperCase(tecla, caminhoActionReload, objetoCampo);
			}
						}
      				}
	else{
		return true;
					}
					}
					
					
						
/* Utilizado nos formularios que realizam pesquisas atraves do botao Enter
   Sem colocar os campos para uppercase
   EX: pesquisaEnterSemUpperCase(event, String)
   event - Captura qual foi o evento realizado pelo usuario (Padrao)
*/
function pesquisaEnterSemUpperCase(tecla, caminhoActionReload, objetoPesquisa) {
						
	var form = document.forms[0];
					
	if (document.all) {
        var codigo = event.keyCode;
								}
	else{
        var codigo = tecla.which;
		      				}
    if (codigo == 13) {
		      	
    	objetoPesquisa.value = (objetoPesquisa.value * 1);
		
		desabilitaCampos();
        form.action = caminhoActionReload;
        form.submit();
							}
	else{
        return true;
						}
					}
					
// Realiza um submit na pagina inicial de acordo com a url passada
//sem colocar oscampos para uppercase
function chamarSubmitComUrlSemUpperCase(pagina){
	//toUpperCase(opener.window.document.forms[0]);
	opener.window.document.forms[0].action = pagina; 
	opener.window.document.forms[0].submit();
		}
		
		
//Calcula o valor da parcela do debito p telas com dados do debito
function calculaValorParcela() {
		var form = document.forms[0];
		var valor1 = obterNumerosSemVirgulaEPonto(form.valorDebito.value);
		var valor2 = form.quantidadeParcelas.value * 100;
	
		var calc = new Number();
		if (form.quantidadeParcelas.value != '') {
	
			if(form.percentualCobranca.value == "70"){
				valor1 = valor1 * 0.7;
			}else if(form.percentualCobranca.value == "50"){
				valor1 = valor1 * 0.5;
			} else if (form.percentualCobranca.value == "-1") {
				alert("Informe o percentual de cobran?a.");
				form.quantidadeParcelas.value = "";
				return false;
			}

			calc = valor1/valor2;
			form.valorParcelas.value = calc.toFixed(2);
			formataValorMonetario(form.valorParcelas, 10);
	    }
	    }
	
 	function limpaValorParcela() {
		var form = document.forms[0];
		form.valorParcelas.value = '';
		if(form.percentualCobranca.value == "-1"){
			form.quantidadeParcelas.value = "";
		}
			} 
	
	//verifica se o motivo da n?o cobra?a naum foi informado
	//libera o campo quantidade de parcelas 
	function habilitarQtdParcela(){
		var form = document.forms[0];
		if(form.motivoNaoCobranca.value == "-1"){	
			form.percentualCobranca.value = "-1"
			form.valorParcelas.value = "";
			form.percentualCobranca.disabled = false;
			form.quantidadeParcelas.disabled = false;
			form.valorParcelas.value = "";
		}else{
			form.percentualCobranca.value = "-1"
			form.percentualCobranca.disabled = true;
			form.quantidadeParcelas.disabled = true;
			form.quantidadeParcelas.value = "";
			form.valorParcelas.value = "";
		}
	}
	
	function validaDebito(){
		var form = document.forms[0];
		if(form.idTipoDebito != null && form.idTipoDebito.value != ""){
			if(form.motivoNaoCobranca != null && form.motivoNaoCobranca.value != "-1"){
				return true;
			}else{
				if(form.percentualCobranca.value == "-1"
					&& form.quantidadeParcelas.value == ""){
					if(form.motivoNaoCobranca != null){
					  alert("Informe Motivo da Não Cobrança \n Informe Percentual de Cobrança \n Informe Quantidade de Parcelas");
					}else{
					  alert("Informe Percentual de Cobrança \n Informe Quantidade de Parcelas");					
					}
					return false;
				}else 
					
				   if(form.percentualCobranca.value != "-1"){
					  
				     if(form.quantidadeParcelas.value == ""){
					   alert("Informe Quantidade de Parcelas");
					   return false;	
					 }else{
						 
						if (!testarCampoValorZero(form.quantidadeParcelas, 'Quantidade de Parcelas')){
							 
							return false;
						}
						 
					    var digitosValidos = "0123456789" ;
						var qtdParcelas = form.quantidadeParcelas.value;
			             //retira digitos não numericos
					 	for (i=0;i<qtdParcelas.length;i++){
					 	 if(digitosValidos.indexOf(qtdParcelas.charAt(i))<0) {
					 		alert("Quantidade de Parcelas deve ser numérico(a)");
					 		return false;	
					 	 }
					 	}
					   }
					   return true;			
				}else if(form.percentualCobranca.value == "-1"
					&& form.quantidadeParcelas.value != ""){
					alert("Informe Percentual de Cobrança");
					return false;	
				}else{
					return true;
				}
			}
		}else{
			return true;
		}
	}
	
	function validarTamanhoMaximoTextArea(campo,tamanhoMaximo){
	  var valorAux = ""
	  var valor = campo.value;
	 if(valor.length > tamanhoMaximo){
	  alert('Tamanho maximo atingido.');
	  valorAux = valor.substring(0,tamanhoMaximo);
	  campo.value = valorAux;
	 }
	}
	
	
	/*
	* Objetivo: Verifica se a formata??o utilizada pelo usu?rio est? correta
	* Autor: Raphael Rossiter
	* Data: 07/11/2006
	 */
	function validarFormatacaoNumeracaoRAManual(campo, nomeCampo){
		var retorno = true;

		if (campo.value.length > 0){
		
			if (campo.value.length == 11){
			
				var arrayCampo = campo.value.split("-");
				
				if (arrayCampo.length == 2 && arrayCampo[0].length == 9 && arrayCampo[1].length == 1){
				
					var indesejaveis = "~{}^%$[]@|`\\<?\#?!;*>\"\'";
					
					//1?
					teste = true;
					for (var i=0; i<indesejaveis.length; i++) {
			
						if ((arrayCampo[0].indexOf(indesejaveis.charAt(i))) != -1 ){
							teste = false;
						}
      				}
      	
      				if(teste == false){
	      				alert("Informe " + nomeCampo + " no formato 000000000-0");
						campo.focus();
						retorno = false;
					}
					else if (arrayCampo[0].length > 0 && (isNaN(arrayCampo[0]) || arrayCampo[0].indexOf(',') != -1 ||
							 arrayCampo[0].indexOf('.') != -1)){

						alert("Informe " + nomeCampo + " no formato 000000000-0");
						campo.focus();
						retorno = false;
					}
					
					if (retorno){
					
						//O valor tem que ser superior ? zero
						if ((arrayCampo[0] * 1) == 0){
						
							alert(nomeCampo + " deve somente conter n?meros positivos");
							campo.focus();
							retorno = false;
						}
						else{
						
							//2?
							teste = true;
							for (var i=0; i<indesejaveis.length; i++) {
					
								if ((arrayCampo[1].indexOf(indesejaveis.charAt(i))) != -1 ){
									teste = false;
								}
		      				}
		      	
		      				if(teste == false){
			      				alert("Informe " + nomeCampo + " no formato 000000000-0");
								campo.focus();
								retorno = false;
							}
							else if (arrayCampo[1].length > 0 && (isNaN(arrayCampo[1]) || arrayCampo[1].indexOf(',') != -1 ||
									 arrayCampo[1].indexOf('.') != -1)){

								alert("Informe " + nomeCampo + " no formato 000000000-0");
								campo.focus();
								retorno = false;
							}
						}
					}
				}
				else{
					
					alert("Informe " + nomeCampo + " no formato 000000000-0");
					campo.focus();
					retorno = false;
				}
			}
			else{
				alert("Informe " + nomeCampo + " no formato 000000000-0");
				campo.focus();
				retorno = false;
			}
		}
		
		
		return retorno;
	}
	
	
	/*
	* Objetivo: Mascara a numera??o informada pelo usu?rio
	* Autor: Raphael Rossiter
	* Data: 14/11/2006
	*/
	function mascaraNumeracaoManual(tecla, campo){

		if (document.all) {
			var codigo = event.keyCode;
	    }
		else {
	    	var codigo = tecla.which;
	    }
	
		//Tecla 08 = backspace 
		if (campo.value.length == 9 && codigo != 8){
			campo.value = campo.value + "-";
		}
		else if (campo.value.length > 9){
			if (campo.value.substring(9,10) != "-"){
				campo.value = campo.value.substring(0,9) + "-" + campo.value.substring(9,10);
			} 
		}
	}
	
	/*
	 * Verifica se os hor?rios de um per?odo s?o v?lidos
	 *
	 * par?metro objHoraInicial  campo que cont?m o hora inicial (hh:mm)
	 * par?metro objHoraFinal  campo que cont?m o hora final (hh:mm)
	 *
	 * Autor: Rafael Pinto
	 *
	 * return: 	<true> Se o per?odo for v?lido
	 *			<false> Caso contr?rio
	 * Data: 29/11/2006
	 */
	function validarIntervaloHora(horaInicio,horaFim){

		var retorno = true;

		var horaInicial = parseInt(horaInicio.substring(0, 2), 10);
		var minutoInicial = parseInt(horaInicio.substring(3, 5), 10);

		var horaFinal = parseInt(horaFim.substring(0, 2), 10);
		var minutoFinal = parseInt(horaFim.substring(3, 5), 10);
		
		if(horaInicial == horaFinal){
			if(minutoInicial > minutoFinal){
				retorno = false;
			}
		} else if(horaInicial > horaFinal){
			retorno =  false;
		}

		return retorno;
	}
	
	
	/**
	 * DependenciaPerformance
	 *
	 * Permite ao programdor gerar depend?ncias entre campos, na valida??o, para 
	 * otimizar o retorno das pesquisas.
	 *
	 * Author: Raphael Rossiter
	 * Data: 31/01/2007
	 *
	 * @param: array bidimensional javaScript, array javaScript, array javaScript, formulario  
	 */
	function dependenciaPerformance(camposDependencia, msgDependecia, camposExclusao, formularioJSP){

		exception = false;
		aprovado = true;
		  
		/**
		 * Campos que necessitam da informa??o de um outro campo que est? realacionado com ele para melhorar 
		 * o tempo de performance de uma determinada pesquisa.
		 */
		if (camposDependencia != null && msgDependecia != null){
	
			/**
			 * Verificando se os arrays que cont?m os campos organizados em duplas de objetos est?o sincronizados
			 * com os que cont?m as mensagens para caso de exce??o.
			 */
			if (camposDependencia.length > 0 && msgDependecia.length > 0 &&
				camposDependencia.length == msgDependecia.length){
			  
				for(x = 0; x < camposDependencia.length; x++){
				
					/**
					 * Caso o objeto que esta no primeiro indice esteja preenchido, o objeto que est? alojado no segundo indice,
					 * obrigatoriamente, ter? que ser informado.
					 */		
					if (campoInformado(camposDependencia[x][0]) && 
						campoInformado(camposDependencia[x][1]) == false){
						
						alert("Informe " + msgDependecia[x]);
						camposDependencia[x][1].focus();
						exception = true;
						aprovado = false;
	
						break;
					}
				}
			}
	
		}
	
		/*
		* Campos que necessitam da informa??o de mais um campo para melhorar 
		* o tempo de performance de uma determinada pesquisa.
		*/
		if (!exception){
	
			if (camposExclusao != null && formularioJSP != null){
	
				outroCampoInformado = false;
				
				for(x = 0; x < formularioJSP.elements.length; x++){
				
					//Verificando os campos que n?o entrar?o no esquema de valida??o
					campoExcluido = false;
	
					for(y = 0; y < camposExclusao.length; y++){
					
						if (formularioJSP.elements[x].name == camposExclusao[y].name){
						
							campoExcluido = true;
							break;
						}
					}
				
	
					//Campo n?o exlu?do... continuando valida??o
					if (!campoExcluido){
					
						if (campoInformado(formularioJSP.elements[x])){
							
							outroCampoInformado = true;
							break;
						}
					}
				}
	
				if (!outroCampoInformado){
					aprovado = false;
					alert("Informe pelo menos uma op??o de sele??o");
				}
			}
		}
		
		return aprovado;
	}
	
	/**
	 * PerformanceJSP
	 *
	 * Trecho de c?digo que ser? necess?rio colocar nos .JSPs que ser?o contemplados 
	 * com a valida??o para melhoria de performance.
	 *
	 * N?O ? PARA REMOVER ESTE TRECHO DE C?DIGO COMENTADO, O MESMO EST? AQUI PARA CONSULTA.
	 *
	 * Author: Raphael Rossiter
	 * Data: 31/01/2007
	 *
	 */
	/*function performanceJSP(){

		form = document.forms[0];*/
	
		/**
		 * Objetos em dupla de acordo com as depend?ncias que vc queira gerar
		 *
		 * EX: var camposDependencia = [[form.campo1, form.campo2],[form.campo1, form.campo3],[form.campo3, form.campo4]];
		 */
		//var camposDependencia = [[]];
		
		/**
		 * Mensagens sincronizadas com as duplas informadas no objeto camposDependencia
		 *
		 * EX: var camposDependencia = [[form.campo1, form.campo2],[form.campo1, form.campo3],[form.campo3, form.campo4]];
		 *     var msgDependecia = ["nomeCampo2", "nomeCampo3", "nomeCampo4"];
		 */
		//var msgDependecia = [];
	
		/**
		 * Objetos que poder?o j? vir informados ou serem informados pelo usu?rio mas que n?o ser?o
		 * considerados como par?metros informados (INDICADOR_USO).
		 *
		 * EX: var camposExclusao = [form.indicadorUso];
		 */
		//var camposExclusao = [];
	
		
		/**
		 * Caso o objeto camposDependencia seja informado, o objeto msgDependecia ser? obrigat?rio e visse-versa.
		 * Caso o objeto camposExclusao seja informado, o formul?rio ser? obrigat?rio e visse-versa.
		 */
		//retorno = dependenciaPerformance(null, null, null, null);
		
		//return retorno;
	//}


	/**
	 * CampoInformado
	 *
	 * Permite ao programdor verificar se o objeto html que foi informado est? carregado com algum valor.
	 *
	 * Author: Raphael Rossiter
	 * Data: 31/01/2007
	 *
	 * @param: objetoHTML  
	 */
	function campoInformado(objeto){
	
		informado = false;
		
		if (objeto != null){
			if (objeto.type == "text" && objeto.value.length > 0){
				informado = true;
			}
			else if (objeto.type == "select-one" && objeto.value.length > 0 && objeto.value > 0){
				informado = true;
			}
			else if (objeto.type == "radio" && objeto.checked == true){
				informado = true;
			}
			else if (objeto.type == "checkbox" && objeto.checked == true){
				informado = true;
			}
			else if (objeto.type == "textarea" && objeto.value.length > 0){
				informado = true;
			}
		}
		return informado;
	}	
	
	/**
	 * Função que cria mais um input de arquivo na tela.
	 *
	 * Author: Lucas Daniel
	 * Date: 23/10/2009
	 */
	function adicionaInput() {
		var ni = document.getElementById('myDiv');
		var numi = document.getElementById('theValue');
		var num = (document.getElementById('theValue').value -1)+ 2;
		numi.value = num;
		var newdiv = document.createElement('input');
		var divIdName = 'my'+num+'Div';
		newdiv.setAttribute('id',divIdName);
		newdiv.setAttribute('type','file');
		newdiv.setAttribute('name','name');
		ni.appendChild(newdiv);
	}

	function setTituloSelect(select){

		var texto = trim(select[select.selectedIndex].text);
		if(texto != ''){
			select.title = texto;
		}else{
			select.title = null;
		}

	}
	
	//Valida a se a Data ? uma data v?lida           
	function verificaData (mydata) {
				dia = mydata.value.substring(0,2);
	            mes = mydata.value.substring(3,5); 
	            ano = mydata.value.substring(6,10); 

	            situacao = true;      
	                
	            if(!isNaN(dia) && !isNaN(mes) && !isNaN(ano)){
		            
	              
			            // verifica se o mes e valido 
			            if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
				             // verifica se o ano e valido
				        	if ((ano * 1) != 0 && (ano * 1) >= 1900) {
				        	
				        		// verifica se e ano bissexto 
				    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
				    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
				       				situacao = false; 
				    			}
				    			// verifica o dia valido para cada mes
				    			else if (((dia * 1) < 1 || (dia * 1) > 30) && ((mes * 1) == 4 || (mes * 1) == 6 || (mes * 1) == 9 || (mes * 1) == 11 ) || (dia * 1) > 31){
				    				situacao = false;
				    			}
				        	}
				        	else{
				        		situacao = false;
				        	} 
				        }
				        else{	            
			                situacao = false; 
			            }
		        }
	            else{
	              situacao = false; 
	            } 
	    
	            if (situacao == false) { 
	                alert("Data inválida.");
	                
	                mydata.value='';
	                mydata.focus(); 
	            } 
		
	            return situacao;
			}
	
	//lembrar de passar o campo.value e não o campo como parametro
	function isBrancoOuNulo(texto){
		if(texto == null || trim(texto) == ""){
			return true;
		}

		return false;
	}
	
	// onkeypress="return isCampoNumerico(event);"
	function isCampoNumerico(evento) {
		  var tecla = null;
	
		  if(window.event){ // Internet Explorer
		  		tecla = evento.keyCode;
		  }else if(evento.which){ // Nestcape ou Mozilla
		    	tecla = evento.which;
		  }

		  if(tecla == null){//tab
			  return true;
		  }
		  
		  if((tecla > 47 && tecla < 58) || (tecla.value == 'undefined')){ // numeros de 0 a 9
		    return true;
		  }
	
	      if (tecla == 8 || tecla == 13){ // backspace ou enter
		        return true;
	      }
			  
	      return false;
	}
				
	function validaCampoInteiroPositivo(campo){
	   	var valorAuxiliar = "";
		digitosValidos = "0123456789" ;
						
		var retorno = true;
	
		var valor = campo;
		//retira digitos não numericos
 		for (i=0;i<valor.length;i++){
 			if(digitosValidos.indexOf(valor.charAt(i))>=0) {
 				valorAuxiliar += valor.charAt(i);
 			}else{
 				retorno = false;
						break;
					}
				}
 		
 		if (valor == 0){
 			retorno = false;
			}
	
 		return retorno;
		}
	
	function verificaAnoMesNovo(mydata) {
	
		var situacao = true;
	
		if (mydata.value != ""){
			if (mydata.value.length == 7) {
				
				dia = 1;
		    	mes = mydata.value.substring(0,2); 
		    	ano = mydata.value.substring(3,7); 
				
		    	if ((!isNaN(mes) || !isNaN(ano)) && (mes.indexOf('.') == -1 && mes.indexOf(',') == -1 && mes.indexOf('/') == -1) &&
					(ano.indexOf('.') == -1 && ano.indexOf(',') == -1 && ano.indexOf('/') == -1)) {
	
		    		// verifica se o mes e valido 
			    	if ((mes * 1) >= 1 && (mes * 1) <= 12 ) { 
					
			        	// verifica se o ano e valido
			        	if ((ano * 1) != 0 && (ano * 1) >= 1980) {
						
			        		// verifica se e ano bissexto 
			    			if ((mes * 1) == 2 && ((dia * 1) < 1 || (dia * 1) > 29 
			    				|| ((dia * 1) > 28 && (((ano * 1) / 4) != (ano * 1) / 4)))) { 
			       				situacao = false;
			       				alert("Mês e/ou Ano inválidos!");
			   					mydata.value = "";
			   					mydata.focus(); 
			    			}
						}
			        	else{
			        		situacao = false;
			        		alert("Mês e/ou Ano inválidos!");
			   				mydata.value = "";
			   				mydata.focus();
					}
						}
			    	else{
			    		situacao = false;
			    		alert("Mês e/ou Ano inválidos!");
			   			mydata.value = "";
			   			mydata.focus();
					}
				}
				else{
					situacao = false;
					alert("Mês e/ou Ano inválidos!");
			   		mydata.value = "";
			   		mydata.focus();
				}
		    }else{
		    	situacao = false;
				alert("Mês e/ou Ano inválidos!");
		   		mydata.value = "";
		   		mydata.focus();
			}
		}
		
	    
	    return situacao;
	}
	
// Necessita de JQUERY, na Página
// Deve-se inserir na função ready a seguinte linha simple_tooltip("identificacaoElemento","tooltip");
function simple_tooltip(target_items, name, op, fi, fo){
 $(target_items).each(function(i){
		$("body").append("<div class='"+name+"' id='"+name+i+"'><p>"+$(this).attr('title')+"</p></div>");
		var my_tooltip = $("#"+name+i);

		$(this).removeAttr("title").mouseover(function(){
				my_tooltip.css({opacity:op, display:"none"}).fadeIn(fi);
		}).mousemove(function(kmouse){
				my_tooltip.css({left:kmouse.pageX+15, top:kmouse.pageY+15});
		}).mouseout(function(){
				my_tooltip.fadeOut(fo);				  
		});
	});
}
		
function carregarComboGenerico(obj, nomeClasse, attPesquisa, objRet, textoCombo, nomeColecao) {
	var id = obj.options[obj.selectedIndex].value;
	
	obj.value = id;
	objRet.length = 0;
	
	var novaOpcao = new Option(" ", "-1");
	objRet.options[objRet.length] = novaOpcao;
	
	AjaxService.carregar(id, nomeClasse, attPesquisa, textoCombo, nomeColecao, function(retPesquisa) {
		
		for (key in retPesquisa){
			  objRet.options[objRet.length] = new Option(retPesquisa[key], key);
		}
	});
}

function limparComboGenerico(obj) {
	var novaOpcao = new Option(" ","-1");
	obj.length=0;
	obj.options[obj.length] = novaOpcao;
}
	
// onkeypress="return isCampoNumericoDecimal(event);"
function isCampoNumericoDecimal(evento) {
	  var tecla = null;
		
	  if(window.event){ // Internet Explorer
	  		tecla = evento.keyCode;
	  }else if(evento.which){ // Nestcape ou Mozilla
	    	tecla = evento.which;
			}
	  
	  if(tecla == null){//tab
		  return true;
			}
	  
	  if(tecla == 44 || tecla == 46){ // vírgulas e pontos
	    return true;
			}
	  
	  if((tecla > 47 && tecla < 58) || (tecla.value == 'undefined')){ // numeros de 0 a 9
	    return true;
			}
	  
      if (tecla == 8 || tecla == 13){ // backspace ou enter
	        return true;
			}
	  
      return false;
		}
	
function validarNumeroDecimal(campo, nomeCampo, msgCustomizada, precisao, escala, required) {
	
	var valorCampo = campo.value;
	
	if (required != null && required == true) {
		if (valorCampo == null || valorCampo == '') {
			alert('Informe ' + nomeCampo + '.');
			return false;
		}
	}

	// Pt
	//var reDecimalPt = /^[+-]?((\d+|\d{1,3}(\.\d{3})+)(\,\d*)?|\,\d+)$/;
	// En
	//var reDecimalEn = /^[+-]?((\d+|\d{1,3}(\,\d{3})+)(\.\d*)?|\.\d+)$/;
	
	var reDecimalPt = /^[+-]?((\d+|\d{1,3}(\.\d{3})+)(\,\d*)?|\,\d+)$/;
	

	charDec = ",";
	
	if (reDecimalPt.test(valorCampo)) {
		pos = valorCampo.indexOf(charDec);
		decs = pos == -1? 0: valorCampo.length - pos - 1;
		
		// Testa Decimal
		if (pos != -1) {
			if (decs == 0) {

				if (!isBrancoOuNull(msgCustomizada)) {
					alert(msgCustomizada);
		}else{
					alert(nomeCampo + ' é invalido.');
		}

				return false;
	}
	
			if (escala != null) {
				if (decs > escala) {
					if (!isBrancoOuNull(msgCustomizada)) {
						alert(msgCustomizada);
					} else {
						alert(nomeCampo + ' é invalido.');
					}
					return false;
				}
			}
		}

		if (pos == 0) {
			if (!isBrancoOuNull(msgCustomizada)) {
				alert(msgCustomizada);
			} else {
				alert(nomeCampo + ' é invalido.');
			}
			return false;
		} else {
	                
			var arrayValorAux = valorCampo.split(charDec);
			var parteInteiraValorAux = arrayValorAux[0];
			var parteInteiraSemSeparadorMilhar = replaceAll(parteInteiraValorAux, '.', '');
				        	
			if (precisao != null) {
				if (parteInteiraSemSeparadorMilhar.length > (precisao - escala)) {
					if (!isBrancoOuNull(msgCustomizada)) {
						alert(msgCustomizada);
					} else {
						alert(nomeCampo + ' é invalido.');
				    			}
					return false;
				    			}
				        	}
				        	} 
		
		return true;
	} else if (valorCampo != null && valorCampo != "") {
		if (!isBrancoOuNull(msgCustomizada)) {
			alert(msgCustomizada);
		} else {
			alert(nomeCampo + ' é invalido.');
				        }
		return false;
			            }
		        }

function replaceAll(string, token, newtoken) {
	var valor = string;
	while (valor.indexOf(token) != -1) {
		valor = valor.replace(token, newtoken);
	}
	return valor;
	            } 
	    
//recebe o objeto
function isCampoBrancoOuNull(obj){
	if(obj == null || obj == 'null' || obj == undefined || obj == 'undefined' || trim(obj.value) == trim('')){
		return true;
	}else{
		return false;
	}
}
	                
// recebe o value do objeto
function isBrancoOuNull(obj){
	if(obj == null || obj == 'null' || obj == undefined || obj == 'undefined' || trim(obj) == ''){
		return true;
	}else{
		return false;
	            } 
	}

	//lembrar de passar o campo.value e não o campo como parametro
	function isBrancoOuNulo(texto){
		if(texto == null || trim(texto) == ""){
			return true;
		}
		
		return false;
	}
	
	// onkeypress="return isCampoNumerico(event);"
	function isCampoNumerico(evento) {
		  var tecla = null;
			
		  if(window.event){ // Internet Explorer
		  		tecla = evento.keyCode;
		  }else if(evento.which){ // Nestcape ou Mozilla
		    	tecla = evento.which;
		  }
		  
		  if(tecla == null){//tab
			  return true;
		  }
		  
		  if((tecla > 47 && tecla < 58) || (tecla.value == 'undefined')){ // numeros de 0 a 9
		    return true;
		  }
		  
	      if (tecla == 8 || tecla == 13){ // backspace ou enter
		        return true;
	      }
		  
	      return false;
	}
	
	/**
	 * M?todo que valida um email. O validator est? aceitando YARA@YAHOO/COM.BR (furo) 
	 *
	 * Author: Yara, Thiago Toscano
	 * Data: 20/02/2007
	 *
	 * @param: objetoHTML  
	 */
	function checkMail(email){
		var retorno = false;
		var x = email.value;
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (filter.test(x))  {
			retorno = true;
		}	
		
		return retorno;	
	}

	function marcarTodosExceto(valorIgnorado){
		for (var i=0;i < document.forms[0].elements.length;i++){
			var elemento = document.forms[0].elements[i];
			if (elemento.type == "checkbox" && elemento.value != valorIgnorado){
				elemento.checked = true;
			}
		}
	}

	function exibirAlertValidandoAnoMes(data) {
		if(validaAnoMesSemAlert(data) == false) {
			alert('Mês/Ano inválidos.');
			data.focus();
			return false;
		}		
	}
	
	function validarInicioFim(inicio, fim){
		
		if (parseInt(fim.value) < parseInt(inicio.value)){
			alert("Valor final menor que inicial.");
			fim.focus();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Realiza a seguinte subtração: data1 - data2
	 *
	 * @author Luciano Galvão
	 * @created 03/09/2012
	 * @param data1
	 * @param data2
	 * @returns
	 */
	function subtrairDatas(data1, data2) {
	    var d1=new Date(data1.substr(6,4), data1.substr(3,2)-1, data1.substr(0,2));
	    var d2=new Date(data2.substr(6,4), data2.substr(3,2)-1, data2.substr(0,2));	    
	    return Math.ceil((d1.getTime()-d2.getTime())/1000/60/60/24);
	}
	
		function cpfCnpj(v){
		if (v.length <= 14) { //CPF
		//Remove tudo o que não é dígito
		v=v.replace(/\D/g,"")
		//Coloca um ponto entre o terceiro e o quarto dígitos
		v=v.replace(/(\d{3})(\d)/,"$1.$2")
		//Coloca um ponto entre o terceiro e o quarto dígitos
		//de novo (para o segundo bloco de números)
		v=v.replace(/(\d{3})(\d)/,"$1.$2")
		//Coloca um hífen entre o terceiro e o quarto dígitos
		v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")
		} else { //CNPJ
		//Remove tudo o que não é dígito
		v=v.replace(/\D/g,"")
		//Coloca ponto entre o segundo e o terceiro dígitos
		v=v.replace(/^(\d{2})(\d)/,"$1.$2")
		//Coloca ponto entre o quinto e o sexto dígitos
		v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")
		//Coloca uma barra entre o oitavo e o nono dígitos
		v=v.replace(/\.(\d{3})(\d)/,".$1/$2")
		//Coloca um hífen depois do bloco de quatro dígitos
		v=v.replace(/(\d{4})(\d)/,"$1-$2")
		}
		return v
	}
	
	function isCpf(cpf) {
		
		var soma;
		var resto;
		var i;
		 
		if ( (cpf.length != 11) ||
			 (cpf == "00000000000") || (cpf == "11111111111") ||
			 (cpf == "22222222222") || (cpf == "33333333333") ||
			 (cpf == "44444444444") || (cpf == "55555555555") ||
			 (cpf == "66666666666") || (cpf == "77777777777") ||
			 (cpf == "88888888888") || (cpf == "99999999999") ) {
		 
			return false;
		}
		 
		soma = 0;
		 
		for (i = 1; i <= 9; i++) {
		
			soma += Math.floor(cpf.charAt(i-1)) * (11 - i);
		
		}
		 
		resto = 11 - (soma - (Math.floor(soma / 11) * 11));
		 
		if ( (resto == 10) || (resto == 11) ) {
			resto = 0;
		}
		 
		if ( resto != Math.floor(cpf.charAt(9)) ) {
			return false;
		}
		 
		soma = 0;
		 
		for (i = 1; i<=10; i++) {
			soma += cpf.charAt(i-1) * (12 - i);
		}
		 
		resto = 11 - (soma - (Math.floor(soma / 11) * 11));
		 
		if ( (resto == 10) || (resto == 11) ) {
			resto = 0;
		}
		 
		if (resto != Math.floor(cpf.charAt(10)) ) {
			return false;
		}
		 
		 return true;
	}
		 
	function isCnpj(s){
	
		var i;
		var c = s.substr(0,12);
		var dv = s.substr(12,2);
		var d1 = 0;
		 
		for (i = 0; i < 12; i++){
			d1 += c.charAt(11-i)*(2+(i % 8));
		}
		 
		if (d1 == 0) return false;
				
		d1 = 11 - (d1 % 11);
		 
		if (d1 > 9) d1 = 0;
		
		if (dv.charAt(0) != d1){
			return false;
		 
		}
		 
		d1 *= 2;
				
		for (i = 0; i < 12; i++){
		 
			d1 += c.charAt(11-i)*(2+((i+1) % 8));
		
		}
				 
		d1 = 11 - (d1 % 11);
				
		if (d1 > 9) d1 = 0;
		
		if (dv.charAt(1) != d1){
		
			return false;
		 
		}
		 
		 return true;
		 
	}
		 
	function isCpfCnpj(valor) {
		
		valor = replaceAll(valor, ".", "");
		valor = replaceAll(valor, "-", "");
		valor = replaceAll(valor, "/", "");
		 
		var retorno = false;
		var numero  = valor;
		 		 
		if (numero.length > 11){
		 
			if (isCnpj(numero)) {
		 
				retorno = true;
		 
			}
		 
		} else {
		 
			if (isCpf(numero)) {
		 
				retorno = true;
		 
			}
		 
		}
		 
		 return retorno;
		
	}
	
	function enviarDadosQuatroParametrosCaminhoRetorno(nomeActionExibirPagina, idCampo, descricaoCampo, idCampo2, descricaoCampo2, tipoConsulta) {
		window.location.href =
		'/gsan/' + nomeActionExibirPagina + '.do?idCampoEnviarDados=' + idCampo +
		'&descricaoCampoEnviarDados=' + descricaoCampo + '&idCampo2=' + idCampo2 + '&descricaoCampoEnviarDado2s=' + descricaoCampo2 + '&tipoConsulta=' + tipoConsulta;

	}
	
