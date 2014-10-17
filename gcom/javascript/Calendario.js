function Calendar(Month,Year) {
    var output = '';

    output += '<form name="Cal"><body onLoad="window.focus();" background="../../imagens/background.gif">';
    output += '<table bgcolor="#cbe5fe" border="0" align="center"><tr><td>';
    output += '<table bgcolor="#cbe5fe" border="0" cellpadding="0" cellspacing="0" align="center"><tr>';
    output += '<td align="center" width="240" bgcolor="#90c7fc" height="30">';
    output += '&nbsp;&nbsp;&nbsp;<select name="Month" onChange="changeMonth();"><font size=-1 color="#000000" face="ARIAL">';

    for (xmonth=0; xmonth<12; xmonth++) {
        if (xmonth == Month) output += '<option value="' + xmonth + '" selected>' + names[xmonth] + '<\/option>';
        else                output += '<option value="' + xmonth + '">'          + names[xmonth] + '<\/option>';
    }

    output += '<\/select><select name="Year" onChange="changeYear();">';

    for (xyear=1900; xyear<2101; xyear++) {
        if (xyear == Year) output += '<option value="' + xyear + '" selected>' + xyear + '<\/option>';
        else              output += '<option value="' + xyear + '">'          + xyear + '<\/option>';
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
	        output += '<td width="10%" height="25" bgcolor="' + (i == day ? (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'red' : (year == opener.year && month == opener.month ? 'black' : '#ffffff')) : (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'red' : '#ffffff')) + '">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="' + (i == day ? (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'yellow' : (year == opener.year && month == opener.month ? '#ffffff' : '#0000BB')) : (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'yellow' : '#0000BB')) + '">' + i + '<\/font><\/a>' +'<\/td>';
    	}else{    
	        output += '<td width="10%" height="25" bgcolor="' + (i == day ? (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'red' : (year == opener.year && month == opener.month ? 'black' : '#cbe5fe')) : (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'red' : '#cbe5fe')) + '">' + '<a href="javascript:changeDay(' + i + ')"><font SIZE=-1 face="ARIAL" color="' + (i == day ? (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'yellow' : (year == opener.year && month == opener.month ? '#ffffff' : '#0000BB')) : (i == opener.selDay && opener.year == opener.selYear && opener.month == opener.selMonth ? 'yellow' : '#0000BB')) + '">' + i + '<\/font><\/a>' +'<\/td>';
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
var selDay   = null;
var selMonth = null;
var selYear  = null;
var nomeForm;
var nomeCampo;
var nomeCampoReplicar;
var nomeCampoReplicarVarios;

function padout(number) { return (number < 10) ? '0' + number : number; }

function restart() {
    var obj = document.getElementById(nomeCampo);
	if (obj  != undefined) {
		obj.value = '' + padout(day) + '/' + padout(month - 0 + 1) + '/' + year;
	} else {
    	document.forms[nomeForm].elements[nomeCampo].value = '' + padout(day) + '/' + padout(month - 0 + 1) + '/' + year;
    }
    if(nomeCampoReplicar != null){
      document.forms[nomeForm].elements[nomeCampoReplicar].value = '' + padout(day) + '/' + padout(month - 0 + 1) + '/' + year;
    }
    
    if (nomeCampoReplicarVarios != null){
    	for(i = 0; i < document.forms[nomeForm].elements.length; i++){	           
	           if (document.forms[nomeForm].elements[i].name.indexOf(nomeCampoReplicarVarios) != -1){
                   nomeCampoReplicarAux = document.forms[nomeForm].elements[i].name;
	        	   document.forms[nomeForm].elements[nomeCampoReplicarAux].value = '' + padout(day) + '/' + padout(month - 0 + 1) + '/' + year;                
	           }
	   }
    }
    	
}

function splitDate(nomeForm, nomeCampo) {
    var today = new Date();
    day   = today.getDate();
    month = today.getMonth();
    year  = y2k(today.getYear());
    var obj = document.getElementById(nomeCampo);
	selDay = selMonth = selYear  = null;
	if (obj  != undefined) {
		dayMonthYear = obj.value.split('/');
	} else {
		dayMonthYear = document.forms[nomeForm].elements[nomeCampo].value.split('/');
    }
    if (dayMonthYear.length == 3 && !isNaN(dayMonthYear[0]) 
    		&& !isNaN(dayMonthYear[1]) && !isNaN(dayMonthYear[2])) {
    	selDay = parseInt(dayMonthYear[0], 10);
    	selMonth = parseInt(dayMonthYear[1], 10) - 1;
    	selYear = parseInt(dayMonthYear[2], 10);
    	var selDate = new Date(selYear, selMonth, selDay, 0, 0, 0, 0);
    	if (!isNaN(selDate)) {
    		selDay   = selDate.getDate();
    		selMonth = selDate.getMonth();
    		selYear  = y2k(selDate.getYear());
    		month = selMonth;
    		year = selYear;
    		day = selDay;
    	}
    }
}

function abrirCalendario(formName, fieldName) {
    nomeForm = formName;
    nomeCampo = fieldName;
    splitDate(formName, fieldName);
	centerpopup('./jsp/util/calendario.jsp','calendario',225,268);
}

function abrirCalendarioEvento(formName, fieldName, url) {
    nomeForm = formName;
    nomeCampo = fieldName;
    splitDate(formName, fieldName);
	centerpopup('./jsp/util/calendario.jsp','calendario',225,268);
	
	alert(document.getElementById(nomeCampo).value);
	
	var dataEntregaDocumento = document.getElementById(nomeCampo).value;
	
	var url = url+dataEntregaDocumento+'&id='+document.getElementById(nomeCampo).id;
	
	alert(url);
	
	nomeForm.action =  url;
	nomeForm.submit();		
}

function changeDay(day) {
    opener.day = day + '';
    opener.restart();
    self.close();
}

function changeMonth() {
    opener.month = document.Cal.Month.options[document.Cal.Month.selectedIndex].value + '';
    location.href = 'calendario.jsp';
}

function changeYear() {
    opener.year = document.Cal.Year.options[document.Cal.Year.selectedIndex].value + '';
    location.href = 'calendario.jsp';
}

function makeArray0() {
    for (i = 0; i<makeArray0.arguments.length; i++)
        this[i] = makeArray0.arguments[i];
}

function abrirCalendarioReplicando(formName, fieldName, fieldNameReplicar) {
    nomeForm = formName;
    nomeCampo = fieldName;
	nomeCampoReplicar = fieldNameReplicar;
    splitDate(formName, fieldName);
    centerpopup('./jsp/util/calendario.jsp','calendario',225,268);
}

function abrirCalendarioReplicandoVarios(formName, fieldName, fieldNamePrefixo) {
	   nomeForm = formName;
	   nomeCampo = fieldName;
	   nomeCampoReplicarVarios = fieldNamePrefixo;
	   splitDate(formName, fieldName);
	   centerpopup('./jsp/util/calendario.jsp','calendario',225,268);
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