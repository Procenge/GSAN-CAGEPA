function CalendarDiaVencimento(Month, Year, situacao) {
    var output = '';
	
    var data; 
	var vetorSituacao = situacao.split(",");
	
    output += '<form name="ExibirCalendarioDiaVencimentoActionForm"><body onLoad="window.focus();" background="../../imagens/background.gif">';
    output += '<table bgcolor="#cbe5fe" border="0" align="center"><tr><td>';
    output += '<table bgcolor="#cbe5fe" border="0" cellpadding="0" cellspacing="0" align="center"><tr>';
    output += '<td align="center" width="240" bgcolor="#90c7fc" height="30">';
    output += '&nbsp;&nbsp;&nbsp;<select name="Month"  disabled="true" onChange="changeMonth(ExibirCalendarioDiaVencimentoActionForm.mes.value,ExibirCalendarioDiaVencimentoActionForm.ano.value);"><font size=-1 color="#000000" face="ARIAL">';

    for (month=0; month<12; month++) {
        if (month == Month) output += '<option value="' + month + '" selected>' + names[month] + '<\/option>';
        else                output += '<option value="' + month + '">'          + names[month] + '<\/option>';
    }

    output += '<\/select><select name="Year"  disabled="true" onChange="changeYear();">';

    for (year=1900; year<2101; year++) {
        if (year == Year) output += '<option value="' + year + '" selected>' + year + '<\/option>';
        else              output += '<option value="' + year + '">'          + year + '<\/option>';
    }

    output += '</font><\/select>&nbsp;&nbsp;&nbsp;';
	output += '<\/td><\/tr><\/table><\/td><\/tr><tr><td align="center" colspan="4">';

    firstDay = new Date(Year,Month,1);
    startDay = firstDay.getDay();

    if (((Year % 4 == 0) && (Year % 100 != 0)) || (Year % 400 == 0))
         days[1] = 29;
    else
         days[1] = 28;

    output += '<table border="0" bgcolor="#90c7fc" align="center"><tr>';

    for (i=0; i<7; i++){
        output += '<td width="10%" align="center" valign="middle"><font size=-1 color="#000000" face="ARIAL"><b>' + dow[i] +'<\/b><\/font><\/td>';
    }    

    output += '<\/tr><tr align="center" valign="middle">';

    var column = 0;
    var lastMonth = Month - 1;
    if (lastMonth == -1) lastMonth = 11;

    for (i=0; i<startDay; i++, column++){
        output += '<td width="10%" height="25" bgcolor="#e9e9e9"><font size=-1 color="#7f7f7f" face="ARIAL">' + (days[lastMonth]-startDay+i+1) + '<\/font><\/td>';
   	}     

    
    
    for (i=1; i<=days[Month]; i++, column++) {
    	if( column == 0 ){
	        

	        if (vetorSituacao[i - 1] == "4"){	 	        	
            	output += '<td width="10%" height="25" bgcolor="#ffffff">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="#FF0000">' + i + '<\/font><\/a>' +'<\/td>';
	        }
	        else if (vetorSituacao[i - 1] == "5"){
	    	     output += '<td width="10%" height="25" bgcolor="#ffffff">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="#0000BB">' + i + '<\/font><\/a> <span class="elaboracaoAcompanhamento">R</span>' +'<\/td>';
	        }
	        else  if (vetorSituacao[i - 1] == "6"){
	        	output += '<td width="10%" height="25" bgcolor="#ffffff">' + '<font SIZE=-1 face="ARIAL" color="#0000BB">' + i + '<\/font>' +'<\/td>';
	        }
	        
    	}else{
  
	        if (vetorSituacao[i - 1] == "4"){
	        	output += '<td width="10%" height="25" bgcolor="#cbe5fe">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="#FF0000">' + i + '<\/font><\/a>' +'<\/td>';
	        }
	        else if (vetorSituacao[i - 1] == "5"){
	        	output += '<td width="10%" height="25" bgcolor="#cbe5fe">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="#0000BB">' + i + '<\/font><\/a> <span class="elaboracaoAcompanhamento">R</span>' +'<\/td>';
	        }
	        else if (vetorSituacao[i - 1] == "6"){
	        	output += '<td width="10%" height="25" bgcolor="#cbe5fe">' + '<font SIZE=-1 face="ARIAL" color="#0000BB">' + i + '<\/font>' +'<\/td>';
	        }
	    }    
        if (column == 6) {
            output += '<\/tr><tr align="center" valign="middle">';
            column = -1;
        }
    }

    if (column > 0) {
        for (i=1; column<7; i++, column++)
            output +=  '<td width="10%" height="25" bgcolor="#e9e9e9"><font size=-1 color="#7f7f7f" face="ARIAL">' + i + '<\/font><\/td>';
    }

    output += '<\/tr><\/table><\/td><\/tr><\/table></boby><\/form>';

    return output;
}

var names     = new makeArray0('Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro');
var days      = new makeArray0(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var dow       = new makeArray0('Dom','Seg','Ter','Qua','Qui','Sex','Sab');

function y2k(number)    { return (number < 1000) ? number + 1900 : number; }

var today = new Date();
var day   = today.getDate();
var month = today.getMonth();
var year  = y2k(today.getYear());
var nomeForm;
var nomeCampo;
var nomeCampoReplicar;

function padout(number) { return (number < 10) ? '0' + number : number; }

function changeDay(day) {
    opener.day = day + '';   
    nomeCampo = '' + padout(day);    
    opener.recuperarDadosPopup(nomeCampo, 'novoDiaVencimentoCalendario','calendario');    
    self.close();
}

function changeMonth(Month,Year) {
   
  var mes = document.ExibirCalendarioDiaVencimentoActionForm.Month.options[document.ExibirCalendarioDiaVencimentoActionForm.Month.selectedIndex].value + '';
  var ano = document.ExibirCalendarioDiaVencimentoActionForm.Year.options[document.ExibirCalendarioDiaVencimentoActionForm.Year.selectedIndex].value + '';
  var tipoCalendario = document.forms[0].tipoCalendario.value;
   location.href = '/gsan/exibirCalendarioDiaVencimentoAction.do?mes='+mes+'&ano='+ano+'&'+tipoCalendario+'=true';
}

function changeYear(Year) {
   var mes = document.ExibirCalendarioDiaVencimentoActionForm.Month.options[document.ExibirCalendarioDiaVencimentoActionForm.Month.selectedIndex].value + '';
  var ano = document.ExibirCalendarioDiaVencimentoActionForm.Year.options[document.ExibirCalendarioDiaVencimentoActionForm.Year.selectedIndex].value + ''; 
  var tipoCalendario = document.forms[0].tipoCalendario.value;
  
   location.href = '/gsan/exibirCalendarioDiaVencimentoAction.do?mes='+mes+'&ano='+ano+'&'+tipoCalendario+'=true';
}

function makeArray0() {
    for (i = 0; i<makeArray0.arguments.length; i++)
        this[i] = makeArray0.arguments[i];
}


function  centerpopup(url,nome,altura,largura){
  // esta ser? a largura e a altura mínima evitando uma janela muito pequena
  var minimo = screen.width/4;  
  
  // esta ser? a largura e a altura máxima evitando uma janela muito grande
  var maximo = screen.height - 100; 
  
  var w = ( ( ( (largura>minimo)? largura:minimo )<maximo )?largura:maximo);
  var h = ( ( ( ( altura>minimo )? altura:minimo )<maximo )?altura:maximo);
  var l = (screen.width/2) - w/2;    // valor para a posi??o na horizontal
  var t = (screen.height/2) - h/2;    // valor para a posi??o na vertical
  var argumentos = 'copyhistory=yes,width='+w+',height='+h+',left='+l+',top='+t+',screenX='+l+',screenY='+t;
  var novajan = window.open(url,nome, argumentos);
} 




