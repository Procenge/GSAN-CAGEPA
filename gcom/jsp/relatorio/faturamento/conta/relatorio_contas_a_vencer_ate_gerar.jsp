<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioContasAVencerAteDynaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
		var form = document.forms[0];
		var data = new Date();
		var mes = data.getMonth()+1;
        var ano = data.getFullYear();
        var mesAnoAtual = mes +"/"+ ano;
        if (mes <10){
        var mesAnoAtual = "0"+mes +"/"+ ano;
        }
        var mesAnoCampo = document.forms[0].p_anoreferencia.value;


		if(comparaMesAno(mesAnoCampo, ">", mesAnoAtual)){
			alert("Mês/Ano do Faturamento deve ser inferior ou igual ao Mês/Ano corrente");
		} else if(campoObrigatorio()){
				if(document.forms[0].ehRelatorioBatch.value == 2) {
				    document.forms[0].target = "_blank";
				}
				document.forms[0].submit();
				document.forms[0].target = "";
		}
		
	}

	
	function campoObrigatorio(){       
		var form = document.forms[0];
		var msg = "";

		if(form.p_anoreferencia.value.length < 1){
			msg = "Informe Mês/ano do faturamento";
			form.p_anoreferencia.focus();
			alert(msg);
			return false;
		}


		
		return true;
	}


	function troca(str,strsai,strentra) 
    { 
            while(str.indexOf(strsai)>-1) 
            { 
                    str = str.replace(strsai,strentra); 
            } 
            return str; 
    }

	function FormataMoeda(campo,tammax,teclapres,caracter) 
    { 
            if(teclapres == null || teclapres == "undefined") 
            { 
                    var tecla = -1; 
            } 
            else 
            { 
                    var tecla = teclapres.keyCode; 
            } 

            if(caracter == null || caracter == "undefined") 
            { 
                    caracter = "."; 
            } 

            vr = campo.value; 
            if(caracter != "") 
            { 
                    vr = troca(vr,caracter,""); 
            } 
            vr = troca(vr,"/",""); 
            vr = troca(vr,",",""); 
            vr = troca(vr,".",""); 

            tam = vr.length; 
            if(tecla > 0) 
            { 
                    if(tam < tammax && tecla != 8) 
                    { 
                            tam = vr.length + 1; 
                    } 

                    if(tecla == 8) 
                    { 
                            tam = tam - 1; 
                    } 
            } 
            if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) 
            { 
                    if(tam <= 2) 
                    { 
                            campo.value = vr; 
                    } 
                    if((tam > 2) && (tam <= 5)) 
                    { 
                            campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 6) && (tam <= 8)) 
                    { 
                            campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 9) && (tam <= 11)) 
                    { 
                            campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 12) && (tam <= 14)) 
                    { 
                            campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 15) && (tam <= 17)) 
                    { 
                            campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
            } 
    }

	function maskKeyPress(objEvent) 
    { 
            var iKeyCode; 
                                     
            if(window.event) // IE 
            { 
                    iKeyCode = objEvent.keyCode; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
            else if(e.which) // Netscape/Firefox/Opera 
            { 
                    iKeyCode = objEvent.which; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
             
             
    }

	

	

	function limpar(){

  		var form = document.forms[0];
  		
  		form.p_anoreferencia.value = '';
  		form.p_nValor.value = '';
  		
  	}
	
	  	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('p_anoreferencia')">

<html:form action="/gerarRelatorioContasAVencerAteAction.do"
	name="GerarRelatorioContasAVencerAteDynaActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioContasAVencer.rpt"/>
	
	
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td width="149" valign="top" class="leftcoltext">
	      <div align="center">
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>
	
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	        <%--<p align="left">&nbsp;</p>--%>
	      	</div>
	      	</td>
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Gerar Relat&oacute;rio de Contas a Vencer Até</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>
				              	
              	<tr>
					<td><strong>Refer&ecirc;ncia do Faturamento: <font color="#FF0000">*</font></strong></td>					
					<td >						
						<html:text maxlength="7"
							
							property="p_anoreferencia" 
							size="7" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:return verificaAnoMes(this);"
							/>
					</td>
				</tr>
				
				<tr>
					<td><strong>Valor limite mínimo:</strong></td>						
					<td>						
						<html:text maxlength="13"
							
							property="p_nValor" 
							size="13" 
							onkeydown="FormataMoeda(this,10,event)" 
							onkeypress="return maskKeyPress(event)" 
							/>
						
					</td>
				</tr>
				
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar()"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm()" />
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
