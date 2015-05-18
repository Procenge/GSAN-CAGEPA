<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<script src="<bean:message key="caminho.js"/>jquery-1.4.2.min.js"></script>
<script src="<bean:message key="caminho.js"/>jquery.maskedinput-1.2.2.js"></script>

<script src="<bean:message key="caminho.js"/>html2canvas.js"></script>
<script type="text/javascript">
$(window).load(function(){
	$('#btn_printscreen').click(function(){
		var height = $('.centercoltext').height() + 10;
		var width  = $('.centercoltext').width() + 45;
		html2canvas($('.centercoltext'), {
			onrendered: function (canvas) {
				var img = canvas.toDataURL('image/png', 1.0);
			    printscreenWindow = window.open('', '', 'width=' + width + ',height=' + height + ',scrollbars=1,directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no');
				printscreenWindow.document.title = document.title;
				printscreenWindow.document.write("<img src='" + img + "' />");
			    printscreenWindow.focus();
			    printscreenWindow.print();
			}
		});
	});
});
</script>

<script src="<bean:message key="caminho.js"/>scriptaculous/prototype.js"></script>
<script src="<bean:message key="caminho.js"/>scriptaculous/scriptaculous.js"></script>
<script src="<bean:message key="caminho.js"/>scriptaculous/scriptaculous.js?load=controls,effects"></script>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="description" />
<meta http-equiv="expires" content="-1" />	
	
<title>
	<logic:present scope="application" name="tituloPagina">
		${applicationScope.tituloPagina}
	</logic:present>
	<logic:notPresent scope="application" name="tituloPagina">
		GSAN - Sistema Integrado de Gest&atilde;o de Servi&ccedil;os de Saneamento
	</logic:notPresent>
</title>
