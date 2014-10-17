<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript">

			function carregarArquivo(){

				if(validarExtensaoArquivoAnexo()){
					if(confirm('Confirma a operação?')){
						document.forms[0].action = 'baixaOrdemServicoCobranca.do?action=baixaOrdemServicoCobranca';
						document.forms[0].submit();	
					}	
				}

			}

			function desfazerTudo(){
				window.location.href='/gsan/exibirTelaBaixaOrdemServicoCobranca.do?action=exibirTelaBaixaOrdemServicoCobranca';
			}
			
			function validarExtensaoArquivoAnexo(){
				var nomeArquivo = document.forms[0].arquivo.value;
				var retorno = false;
				
				if((nomeArquivo != null) && (nomeArquivo.length != 0)){
					var extensaoArquivo = obterExtensaoArquivoUpload(nomeArquivo).toLowerCase();

					if(extensaoArquivo != 'csv'){
						alert('O arquivo anexado não possui uma extensão válida');
					}else{
						retorno = true;
					}
				}else{
					alert('Informe o caminho do arquivo');
				}

				return retorno;
			}

			function obterExtensaoArquivoUpload(nomeArquivo){
				var extensaoArquivo = '';
				var indexExtensao = nomeArquivo.lastIndexOf(".");

				extensaoArquivo = nomeArquivo.substring(indexExtensao + 1);

				return extensaoArquivo;
			} 
				
		</script>
	</head>

	<body leftmargin="5" topmargin="5" >
		<html:form action="/exibirTelaBaixaOrdemServicoCobranca.do" enctype="multipart/form-data">
			<table border="0" width="100%">
				<tr>
					<td height="10" colspan="2">
						Para efetuar a baixa das ordens de serviço de cobrança, informe o caminho do arquivo:
					</td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td colspan="2">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td height="18" colspan="2" align="center">
									Dados do Arquivo 
								</td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td height="10">
									<strong>
										Arquivo de Ordem de Serviço (.csv):<font color="#FF0000">*</font>
									</strong>
								</td>
								<td>
									<html:file property="arquivo" size="40"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%">
							<tr>
								<td width="70px">
									<html:button property="desfazer" styleClass="bottonRightCol" value="Desfazer" onclick="desfazerTudo();"/>
								</td>
								<td>
									<html:button property="cancelar" styleClass="bottonRightCol" value="Cancelar" onclick="window.location.href='/gsan/telaPrincipal.do'"/>								
								</td>
								<td align="right">
									<html:button property="cancelar" styleClass="bottonRightCol" value="Criticar Arquivo e Efetuar Baixa" onclick="carregarArquivo();"/>								
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
