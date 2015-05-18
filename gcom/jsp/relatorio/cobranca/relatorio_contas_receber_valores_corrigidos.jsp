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

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<SCRIPT LANGUAGE="JavaScript">
    function reload() {
        var form = document.forms[0];
        
        form.action = '/gsan/exibirFiltrorRelContasReceberValoCorrigido.do';
        form.submit();
    }

    /* Chama Popup */ 
    function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
        if(objetoRelacionado.disabled != true){
            if (objeto == null || codigoObjeto == null){
                abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
            } else{
                if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
                    alert(msg);
                } else{
                    abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
                }
            }
        }
    }
    
    /* Recuperar Popup */
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
        var form = document.forms[0];
        
        //form.action='exibirFiltrarRegistroAtendimentoAction.do';
        if (tipoConsulta == 'imovel') {
          form.matriculaImovel.value = codigoRegistro;
          form.inscricaoImovel.value = descricaoRegistro;
          form.inscricaoImovel.style.color = '#000000';       
        } 
         
    }
    
   
    /* Limpar Form */
    function limparForm(){
        var form = document.forms[0];
        
        limparFormImovel();
        window.location.href = '/gsan/exibirFiltrorRelContasReceberValoCorrigido.do?menu=sim';
    }

        
    /* Limpar Imóvel */
    function limparFormImovel() {
        var form = document.forms[0];
        
        //form.matriculaImovel.value = "";
        //form.inscricaoImovel.value = "";
        
        //form.matriculaImovel.focus();
        form.referencia.value="";
        form.referencia.focus();
    }
    
    
    
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioContasReceberValoresCorrigidos"
   name="RelatorioContasReceberValoresCorrigidosForm"
   type="gcom.gui.relatorio.cobranca.RelatorioContasReceberValoresCorrigidosForm"
    method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
    <tr>
        <td width="149" valign="top" class="leftcoltext">
           <div align="center">
               <%@ include file="/jsp/util/informacoes_usuario.jsp"%>
                <%@ include file="/jsp/util/mensagens.jsp"%>
           </div>
        </td>
            
        <td  valign="top" class="centercoltext">
                <table height="100%">
                    <tr>
                      <td></td>
                    </tr>
                </table>

    
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
              <td class="parabg">Contas Receber Valores Corrigidos</td>
              <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
            </tr>
          </table>
           <p>&nbsp;</p>
          
          <table  border="0">
                <tr>
                  <td width="30%"><strong>Refer&ecirc;ncia do D&eacute;bito:<font color="#FF0000">*</font></strong></td>
                  <td width="70%">
                    <html:text property="referencia" size="7"  maxlength="7" 
                        onkeyup="javascript:mascaraAnoMes(this, event);"
                        onblur="javascript:verificaAnoMes(this);"
                        />
                    &nbsp;mm/aaaa
                    <input name="button" type="submit"
                        class="bottonRightCol" value="Gerar"
                        >
                  </td>
                </tr>
                <tr>
                    <td><strong>Formato do Relatório:<font color="#FF0000">*</font></strong></td>
                    
                    <td>
                        <html:radio property="tipoRelatorio" value="1" tabindex="5">
                            <strong> PDF </strong> 
                        </html:radio>
                        <html:radio property="tipoRelatorio" value="3" tabindex="5">
                            <strong> XLS </strong> 
                        </html:radio>
                    </td>
                </tr>
                
                
                                
            </table>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            </td>
            
        </tr>
    </table>
<%@ include file="/jsp/util/rodape.jsp"%>   
</html:form>
</body>

</html:html>