
function CheckboxNaoVazio(campo)
{
    form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}

	if (!retorno){
		alert('Nenhum registro selecionado para remoção.'); 
	}

	return retorno;
}

function CheckboxNaoVazioMensagem(mensagem, campo)
{
    
	form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}

	if (!retorno){
		alert(mensagem + ' para exclusão.');
	}

	return retorno;
}

function CheckboxNaoVazioMensagemGenerico(mensagem, campo)
{
    form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}
	if (!retorno){
		alert(mensagem);
	}
	return retorno;
}

function obterValorCheckboxMarcado(){
	form = document.forms[0];
	retorno = "";

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			retorno = retorno + form.elements[indice].value + ",";
		}
	}
	retorno = retorno.substring(0, retorno.length - 1);
	return retorno;
}

function obterValorCheckboxMarcadoPorNome(nomeCampo){
	form = document.forms[0];
	retorno = "";
	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].name == nomeCampo &&
			form.elements[indice].checked == true) {
			retorno = retorno + form.elements[indice].value + ",";
		}
	}
	retorno = retorno.substring(0, retorno.length - 1);
	return retorno;
}

function obterValorComboboxMarcadoPorNome(nomeCampo){
	form = document.forms[0];
	retorno = "";

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "select-one" &&
			form.elements[indice].name == nomeCampo &&
			form.elements[indice].value != -1) {
			var todos = form.elements[indice].value.split("_");
			if (todos[1] == "0") {
				if (retorno == '') {
					retorno = todos[0] + "_0";
				} else {
					retorno = retorno + "," + todos[0] + "_0";
				}	
			}else {
				if (retorno == '') {
					retorno = form.elements[indice].value;
				} else {
					retorno = retorno + "," + form.elements[indice].value ;
				}				
				
			}
		}
	}
	return retorno;
}

function RadioNaoVazioMensagem(mensagem, campo)
{
    
	form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "radio" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}

	if (!retorno){
		alert("Selecione um(a) " + mensagem + ".");
	}

	return retorno;
}


function obterValorRadioMarcado(){
	var form = document.forms[0];
	retorno = "";

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "radio" && form.elements[indice].checked == true) {
			retorno = form.elements[indice].value;
			break;
		}
	}

	return retorno;
}
	
function limparSelectsMultiplo() {
	var form = document.forms[0];
	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "select-multiple" && form.elements[indice].value != -1) {
			form.elements[indice].value = -1;
			form.elements[indice].selectedIndex = "";			
		}
	}
}

function obterQuantidadeCheckboxMarcado(mensagem, campo)
{
    form = document.forms[0];
	quantidadeCheckboxMarcado = 0;
	retorno = true;
	
	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			quantidadeCheckboxMarcado = quantidadeCheckboxMarcado + 1;
		}
	}
	
	if (quantidadeCheckboxMarcado > 1){
		alert(mensagem);
		retorno = false;
	}
	
	return retorno;
}

function obterQuantidadeCheckboxMarcado(mensagem, campo, limiteSuperior)
{
    form = document.forms[0];
	quantidadeCheckboxMarcado = 0;
	retorno = true;
	
	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			quantidadeCheckboxMarcado = quantidadeCheckboxMarcado + 1;
		}
	}
	
	if (quantidadeCheckboxMarcado > limiteSuperior){
		alert(mensagem);
		retorno = false;
	}
	
	return retorno;
}
