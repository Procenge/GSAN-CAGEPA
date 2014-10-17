<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="display" uri="displaytag"%>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<!-- <div id="scroll3" -->
<!-- 	style="width: 1000px; max-height: 1000px; overflow: scroll; color: red;"> -->

<script type="text/javascript" src="javascript/jquery.mim.js"></script>
<script type="text/javascript" src="javascript/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="javascript/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		oTable = $('#parametro').dataTable({ 
			"bPaginate" : true,
			"bJQueryUI" : false,
			"sPaginationType" : "full_numbers"

		});
	});
</script>


<display:table class="dataTable" name="parametros" sort="list"
	id="parametro"  excludedParams=""
	requestURI="consultarParametroSistema.do">

	<display:column property="codigo" maxLength="50"
		title="Código" paramProperty="chavePrimaria" paramId="chavePrimaria"
		href="exibirFormAlteracaoParametroSistema.do?acao=exibirFormAlteracaoParametroSistema" /> 
	<display:column property="valor" title="Valor"
		maxLength="10" />
</display:table>

<table>
	<tr>
		<td><input type="button" name="ButtonVoltar"
			class="bottonRightCol" value="Voltar Filtro"
			onClick="javascript:window.location.href='/gsan/consultarParametroSistema.do?acao=consultarParametroSistema'">
		
		<td><input type="button" name="ButtonCancelar"
			class="bottonRightCol" value="Cancelar"
			onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
		</td>
	</tr>
</table>