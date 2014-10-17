<%@page import="br.com.procenge.parametrosistema.impl.ParametroSistemaValor"%>
<%@page import="br.com.procenge.parametrosistema.api.ParametroSistema"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="display" uri="displaytag"%>
<%@ page
	import="br.com.procenge.parametrosistema.api.TipoParametroSistema"%>

<script type="text/javascript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script type="text/javascript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script type="text/javascript">
	
	function selectRadio(val) {
		var radios = document.getElementsByTagName("input");
		var valorAtual = val;
		for ( var i = 0; i < radios.length; i++) {
			if (radios[i].type == "radio" && radios[i].value == valorAtual) {
				radios[i].checked = true;
			}
		}
	}

	function toggleBox(szDivID, iState) // 1 visible, 0 hidden
	{
		if (document.layers) //NN4+
		{
			document.layers[szDivID].visibility = iState ? "show" : "hide";
		} else if (document.getElementById) //gecko(NN6) + IE 5+
		{
			var obj = document.getElementById(szDivID);
			obj.style.visibility = iState ? "visible" : "hidden";
		} else if (document.all) // IE 4
		{
			document.all[szDivID].style.visibility = iState ? "visible"
					: "hidden";
		}
	}

	function selecionarValor(form) {
		toggleBox('addValor', 0);
		toggleBox('selValor', 1);
		$('#alterar').removeAttr('disabled');
		$('#cancelar').removeAttr('disabled');
	}

	function adicionarValor(form) {
		toggleBox('selValor', 0);
		toggleBox('addValor', 1);
		$('#alterar').attr('disabled', 'disabled');
		$('#cancelar').attr('disabled', 'disabled');
	}

	function excluir(elemento, form) {
		var valor = elemento.value;
		form.deletaValor.value = valor;
		if (confirm("Remover valor?")) {
// 			location.href = "deletar_categoria.jsp?acao=deletar";
			form.action = "removerParametroSistemaValor.do?acao=excluirParametroSistemaValor";
			form.submit();
		} else {
			form.action = "exibirFormAlteracaoParametroSistema.do?acao=exibirFormAlteracaoParametroSistema";
		}
	}

	function abrirPopupValor(element, alt, lar) {
		var chavePrim = document.getElementById("chavePrimaria").value;
		var val = element.title;
		abrirPopup(
				"alterarParametroSistema.do?acao=exibirParametroSistemaValor&valor="
						+ val + "&chavePrimaria=" + chavePrim + "", alt, lar);
	}
</script>

<style>
.divParametro {
	width: 415px;
	margin-top: 10px;
}

.textAreaParametro {
	resize: none;
	padding-left: 2px;
	width: 440px;
}

.inputCodigo {
	width: 440px;
}

.btnLeft {
	float: left;
}

.btnRight {
	float: right;
}

.voltar {
	padding-right: 3px;
}

-moz-document url-prefix    () { .voltar {
	padding-right: 1px;
}

}
@media screen and (-webkit-min-device-pixel-ratio:0) {
	.voltar {
		padding-right: 1px;
	}
}

span.link:hover,.link span.link:hover {
	color: #304860;
}

.link {
	cursor: pointer;
	text-decoration: underline;
}
</style>

<body onload="selectRadio('${parametroSistemaForm.map.valor}')">

	<html:form method="post" action="alterarParametroSistema.do">

		<input name="chavePrimaria" type="hidden" id="chavePrimaria"
			value="<c:out value='${parametroSistemaForm.map.chavePrimaria}'/>" />
		<input name="versao" type="hidden" id="versao"
			value="<c:out value='${parametroSistemaForm.map.versao}'/>" />
		<input name="acao" type="hidden" id="acao"
			value="alterarParametroSistema">
		<input name="postBack" type="hidden" id="postBack" value="true">

		<div id="selValor">

			<table border="0" align="center" cellpadding="0" cellspacing="0">
				
				<thead>
					<p>Para atualizar um parametro, informe os dados abaixo:</p>
				</thead>
				<tr>
					<td valign="top" align="left"><br> <b>Código: </b><br></td>
					<td><br> <input name="codigo" type="hidden" id="codigo"
						value="<c:out value='${parametroSistemaForm.map.codigo}'/>">
						<p>
						<input class="inputCodigo" name="codigo2" type="text" id="codigo2" disabled value="<c:out value='${parametroSistemaForm.map.codigo}' />" maxlength="68">
						</p> <br></td>
				</tr>

				<tr>
					<td valign="top" align="left"><b>Descrição:<span class="campoObrigatorio">*</span></b></td>
					<td><textarea class="textAreaParametro" name="descricao" rows="4"
							maxlength="255" ><c:out value='${parametroSistemaForm.map.descricao}' /></textarea></td>
				</tr>
				<tr>
					<td align="left"></td>
					<td>
						<div class="divParametro">
							<div style="width: 150px; float: left;">
								<b>Tipo:<span class="campoObrigatorio">*</span></b>
								<html:select property="tipo"
									value="${parametroSistemaForm.map.tipo}"
									style="position: relative;">
									<html:option
										value="<%=String
								.valueOf(TipoParametroSistema.TIPO_ESTATICO)%>"><%=TipoParametroSistema.DESCRICAO_TIPO_ESTATICO%></html:option>
									<html:option
										value="<%=String
								.valueOf(TipoParametroSistema.TIPO_DINAMICO)%>"><%=TipoParametroSistema.DESCRICAO_TIPO_DINAMICO%></html:option>
								</html:select>
							</div>
							<div style="float: right; text-align: right; display: block;">
								<b>Habilitado:</b> <input type="checkbox" name="uso" id="uso"
									value="1"
									<c:if test="${parametroSistemaForm.map.uso == 1}">checked</c:if>>

							</div>

						</div>

					</td>
				</tr>
				<tr>
				
				
					<td align="left" valign="top"> </td>
					
							<td>  
								<div  id="divCadParametro" style="width: 440px; padding-top: 5px;">	<hr>
								<div style="display: block;float: left;"><b>Valor:<span class="campoObrigatorio">*</span></b>
								<input name="novoValor" title="Valor" type="text"
													id="valor" class="input" size="10" maxlength="255"/>
								</div>
								<div style="display: block;float: right; padding-left: 7px;" ><b>Descrição:</b>
								<input name="descricaoValor"
														title="Descrição do Novo Valor" class="input" size="32" maxlength="255">
								</div>						
								<button style="float: right; margin-top: 5px; margin-bottom: 10px;" class="bottonRightCol" type="submit"
														Valor" onclick="this.form.action='inserirParametroSistemaValor.do?acao=inserirParametroSistemaValor'">Adicionar
														Valor</button>
								
								</div>
							<div id="divParametro" style="margin-bottom: 10px;">
								<input name="deletaValor" type="hidden" id="deletaValor" value="">
								<display:table class="dataTable parametroSistema" name="paramValores" sort="external" style=""
									id="paramValores" excludedParams="" defaultsort="1">
									<display:column style="text-align: center; cursor:text;" title="Delete" sortable="false" property="valor" format='<input type="image" value="{0}" src="imagens/Error.gif" border="none" onclick="excluir(this, this.form)"/>'>
									</display:column>
									<display:column style="text-align: center;" title="Lista" sortable="false" property="valor" format='<input type="radio" name="valor" value="{0}" >'>
									</display:column>
									<display:column sortable="false" title="Valores Possíveis" 
										property="valor" format='<span class="link"  onclick="javascript:abrirPopupValor(this, 400, 800);" title="{0}">{0}</span>'>
									</display:column>
								</display:table>
								</div>
							</td>
				</tr>
				<tr>
					<td align="right">
					
						
			</td>
					<td>
					<div class="btnLeft voltar">
							<input id="cancelar" name="button" class="bottonRightCol"
								type="button" id="button" value="Voltar"
								onClick="javascript:window.location.href='/gsan/consultarParametroSistema.do?acao=filtrarParametroSistema'">
						</div>


						<div class="btnLeft">
							<input type="button" name="ButtonCancelar" class="bottonRightCol"
								value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
						</div>
						<div class="btnRight">
							<input id="alterar" type="submit" class="bottonRightCol"
								name="Submit" value="Atualizar"
								onclick="this.form.action='alterarParametroSistema.do?acao=alterarParametroSistema'">
						</div>
					</td>
				</tr>
			</table>
		</div>
	</html:form>
</body>
