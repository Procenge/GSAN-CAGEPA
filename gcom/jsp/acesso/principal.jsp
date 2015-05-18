<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/menus-taglib.tld" prefix="menu" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
  <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
  <%--Este script exibe várias imagens na página--%>

  <script language="JavaScript">
  <!-- Begin
  var interval = 18; // delay between rotating images (in seconds)
  var random_display = 1; // 0 = sequential, 1 = random
  interval *= 1000;


  var image_index = 0;
  image_list = new Array();
  image_list[image_index++] = new imageItem("<bean:message key="caminho.imagens"/>backgroundPrincipal1.jpg");
  image_list[image_index++] = new imageItem("<bean:message key="caminho.imagens"/>backgroundPrincipal2.jpg");
  image_list[image_index++] = new imageItem("<bean:message key="caminho.imagens"/>backgroundPrincipal3.jpg");
  //image_list[image_index++] = new imageItem("04.jpg");
  var number_of_image = image_list.length;
  function imageItem(image_location) {
  this.image_item = new Image();
  this.image_item.src = image_location;
  }
  function get_ImageItemLocation(imageObj) {
  return(imageObj.image_item.src)
  }
  function generate(x, y) {
  var range = y - x + 1;
  return Math.floor(Math.random() * range) + x;
  }
  function getNextImage() {
  if (random_display) {
  image_index = generate(0, number_of_image-1);
  }
  else {
  image_index = (image_index+1) % number_of_image;
  }
  var new_image = get_ImageItemLocation(image_list[image_index]);
  return(new_image);
  }
  function rotateImage(place) {
  var new_image = getNextImage();
  document[place].src = new_image;
  var recur_call = "rotateImage('"+place+"')";
  setTimeout(recur_call, interval);
  }
  //  End -->
  </script>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
</head>

<!-- <body leftmargin="5" topmargin="5" onLoad="rotateImage('rImage')"> -->

<body leftmargin="5" topmargin="5">

<%@ include file="/jsp/util/cabecalho.jsp" %>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
    
    <td width="149" valign="top" class="leftcoltext">
      <%@ include file="/jsp/util/informacoes_usuario.jsp" %>      
    </td>
    
    <td width="625" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" border="0" /></td>
          <td class="parabg">GSAN - Sistema Integrado de Gest&atilde;o de Servi&ccedil;os de Saneamento</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" border="0" /></td>
        </tr>
      </table>
      <!-- <p>&nbsp;</p> -->
      <!--  <p><div align="center"><img name="rImage" width="510" height="294" /></div> -->
         
	
	  <table width="95%"  border = "0">
	      <tr>
      
    		  <td align="center"> 
    		    <br><br><br> 
    		 	<!-- <img src="<bean:message key="caminho.imagens"/>logo_aguas_f_azul.gif" border="0" /> -->
    		 	<img src="<bean:message key="caminho.imagens"/>${applicationScope.imagemPrincipal}"  align="center" >
      			<br><br><br>
      			<a href="http://www.procenge.com.br/" target="_blank">
      				<!-- <img src="<bean:message key="caminho.imagens"/>procenge_inicial.gif" border="0" /> -->
      				<img src="<bean:message key="caminho.imagens"/>${applicationScope.imagemSecundaria}"   align="center">
      			</a>
      
      			<br>     
      			<span style="font-size: 11px; font-family: verdana">			
					<br><p align="center"><a href="http://www.procenge.com.br/" target="_blank">Tecnologia para Decisão</a></p>
				<br>				 
				</span>
      	
      		
      		  </td>
      		</tr>
      </table>
      
      <table width="95%">
      <tr>
      
      <td width="50%">
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp; &nbsp; &nbsp;</span></strong>
      
      <br>
      
      <!-- <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		&nbsp;&nbsp;<br>
      	
      	</span>
      	
      <!-- </p> -->
      
      <br>
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;</span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<BR>
      	&nbsp;&nbsp;<BR>
      	&nbsp;&nbsp;<br>
      	
      	</span>
      	
      <!-- </p> -->
      
      
      <br>
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;&nbsp;</span></strong>
      
      <br>
      
      <!-- <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		&nbsp;&nbsp;<br>

      	
      	</span>
      	
      <!--  </p> -->
      
      <br>
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;&nbsp;</span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		
      	</span>
      	
      <!--  </p> -->
      
      </td>
      
      <td width="50%" valign="top">
      
      <strong><span style="font-size: 11px; font-family: verdana"></span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		
      	</span>
      	
      <!--  </p> -->
      
      
      <br>      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;&nbsp;</span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
      	&nbsp;&nbsp;<br>
		
      	</span>
      	
      <!--  </p> -->
      
      
      <br>
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;&nbsp;</span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		
      	</span>
      	
      <!--  </p> -->
      
      
      <br>
      
      <strong><span style="font-size: 11px; font-family: verdana">&nbsp;&nbsp;</span></strong>
      
      <br>
      
      <!--  <p align="justify"> -->
      
      	<span style="font-size: 11px; font-family: verdana">
      	&nbsp;&nbsp;<br>
		
      	</span>
      	
      <!--  </p> -->
      
      </td>
      
      </tr>
      
      </table>
      
      <br>
      
     <table width="95%">
      <tr>
      
      <td width="50%">
      
      	<span style="font-size: 11px; font-family: verdana">
      	<strong><br>
		</strong>
      	
      	</span>
      	
      <!-- </p> -->
      
      </td>
      </tr>
      </table>
		  
	  

	
	</td>
  </tr>
</table>
<%@ include file="/jsp/util/rodape.jsp" %>

</body>
</html:html>
