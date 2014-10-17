/**
 * 
 */
/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.cadastro.sistemaparametro;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.banco.FiltroContaBancaria;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.endereco.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 04/01/2007
 */
public class ExibirInformarParametrosSistemaDadosGeraisEmpresaAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @author eduardo henrique
	 * @date 06/06/2008
	 *       Adição de Parâmetros Obrigatórios, porém ainda não utilizados Roteiro Padrão e
	 *       Indicador de Faturamento Antecipado
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirInformarParametrosSistemaDadosGeraisEmpresa");

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		Collection colecaoLogradouroBairro;

		Collection colecaoLogradouroCep;

		Boolean existente = false;

		String idLogradouro = null;

		// Método que foi refatorado para que mesmo que ele não venha do menu ele será carregado
		// para mostrar no Combo.
		popularComboImagensFilesPatch(httpServletRequest, sessao, sistemaParametro);

		// Seta os dados no form
		if(httpServletRequest.getParameter("menu") != null){

			form.setNomeEstado("" + sistemaParametro.getNomeEstado());

			form.setNomeEmpresa("" + sistemaParametro.getNomeEmpresa());

			form.setAbreviaturaEmpresa("" + sistemaParametro.getNomeAbreviadoEmpresa());

			form.setCnpj("" + sistemaParametro.getCnpjEmpresa());

			if(sistemaParametro.getLogradouro() != null){
				form.setLogradouro("" + sistemaParametro.getLogradouro().getId());

				form.setNomeLogradouro("" + sistemaParametro.getLogradouro().getNome());
				idLogradouro = sistemaParametro.getLogradouro().getId().toString();

			}

			if(sistemaParametro.getNumeroImovel() != null) form.setNumero("" + sistemaParametro.getNumeroImovel());
			else form.setNumero("");

			if(sistemaParametro.getComplementoEndereco() != null) form.setComplemento("" + sistemaParametro.getComplementoEndereco());
			else form.setComplemento("");

			if(sistemaParametro.getBairro() != null) form.setBairro("" + sistemaParametro.getBairro().getId());

			if(sistemaParametro.getCep() != null) form.setCep("" + sistemaParametro.getCep().getCepId());

			if(sistemaParametro.getEnderecoReferencia() != null) form.setEnderecoReferencia(""
							+ sistemaParametro.getEnderecoReferencia().getId());

			if(sistemaParametro.getNumeroTelefone() != null) form.setNumeroTelefone("" + sistemaParametro.getNumeroTelefone());
			else form.setNumeroTelefone("");

			if(sistemaParametro.getNumeroRamal() == null) form.setRamal("");
			else form.setRamal("" + sistemaParametro.getNumeroRamal());

			if(sistemaParametro.getNumeroFax() == null)

			form.setFax("");
			else form.setFax("" + sistemaParametro.getNumeroFax());

			if(sistemaParametro.getDescricaoEmail() == null)

			form.setEmail("");
			else form.setEmail("" + sistemaParametro.getDescricaoEmail());

			form.setIndicadorRoteiroEmpresa(sistemaParametro.getIndicadorRoteiroEmpresa().toString());
			form.setIndicadorFaturamentoAntecipado(sistemaParametro.getIndicadorFaturamentoAntecipado().toString());

			if(sistemaParametro.getImagemLogomarca() != null) form.setImagemLogomarca("" + sistemaParametro.getImagemLogomarca());
			else form.setImagemLogomarca("");

			if(sistemaParametro.getImagemRelatorio() != null){
				String imagemRelatorio = sistemaParametro.getImagemRelatorio();
				form.setImagemRelatorioPatch("" + imagemRelatorio);
				imagemRelatorio = imagemRelatorio.substring(imagemRelatorio.indexOf("/") + 1);
				imagemRelatorio = imagemRelatorio.substring(imagemRelatorio.indexOf("/") + 1);
				imagemRelatorio = imagemRelatorio.substring(imagemRelatorio.indexOf("/") + 1);
				form.setImagemRelatorio("" + imagemRelatorio);
			}else{
				form.setImagemRelatorio("");
				form.setImagemRelatorioPatch("");
			}
			if(sistemaParametro.getImagemConta() != null){
				String imagemConta = sistemaParametro.getImagemConta();
				form.setImagemContaPatch("" + imagemConta);
				imagemConta = imagemConta.substring(imagemConta.indexOf("/") + 1);
				imagemConta = imagemConta.substring(imagemConta.indexOf("/") + 1);
				imagemConta = imagemConta.substring(imagemConta.indexOf("/") + 1);
				form.setImagemConta("" + imagemConta);
			}else{
				form.setImagemConta("");
				form.setImagemContaPatch("");
			}

			if(sistemaParametro.getImagemCabecalho() != null) form.setImagemCabecalho("" + sistemaParametro.getImagemCabecalho());
			else form.setImagemCabecalho("");

			if(sistemaParametro.getImagemPrincipal() != null) form.setImagemPrincipal("" + sistemaParametro.getImagemPrincipal());
			else form.setImagemPrincipal("");

			if(sistemaParametro.getImagemSecundaria() != null) form.setImagemSecundaria("" + sistemaParametro.getImagemSecundaria());
			else form.setImagemSecundaria("");

		}

		if(httpServletRequest.getParameter("logradouro") != null) idLogradouro = (String) httpServletRequest.getParameter("logradouro");

		if(form.getLogradouro() != null)

		idLogradouro = form.getLogradouro();
		// Verifica Logradouro Enter

		if(idLogradouro != null){

			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, idLogradouro));

			Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

			if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

				Logradouro logradouro = (Logradouro) colecaoLogradouro.iterator().next();

				form.setLogradouro(logradouro.getId().toString());

				form.setNomeLogradouro(logradouro.getNome());

				existente = true;
			}else{

				httpServletRequest.setAttribute("LogradouroInexistente", true);

				form.setNomeLogradouro("LOGRADOURO INEXISTENTE");

			}

		}

		// Pesquisa dos combos da página

		// EnderecoReferencia
		FiltroEnderecoReferencia filtroEnderecoReferencia = new FiltroEnderecoReferencia();

		filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroEnderecoReferencia.setCampoOrderBy(FiltroEnderecoReferencia.DESCRICAO);

		if(Fachada.getInstancia().pesquisar(filtroEnderecoReferencia, EnderecoReferencia.class.getName()) == null
						|| Fachada.getInstancia().pesquisar(filtroEnderecoReferencia, EnderecoReferencia.class.getName()).isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Endereço Referência");
		}else{

			httpServletRequest.setAttribute("colecaoEnderecoReferencia", Fachada.getInstancia().pesquisar(filtroEnderecoReferencia,
							EnderecoReferencia.class.getName()));
		}

		if(idLogradouro == null || existente == false){

			colecaoLogradouroBairro = new ArrayList();

			colecaoLogradouroCep = new ArrayList();

			httpServletRequest.setAttribute("colecaoLogradouroBairro", colecaoLogradouroBairro);

			httpServletRequest.setAttribute("colecaoLogradouroCep", colecaoLogradouroCep);
		}
		if(existente == true){

			// Logradouro Bairro
			FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();

			filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade("bairro");

			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_LOGRADOURO, idLogradouro));

			filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.INDICADORUSO_BAIRRO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoLogradouroBairro = fachada.pesquisar(filtroLogradouroBairro, LogradouroBairro.class.getName());

			httpServletRequest.setAttribute("colecaoLogradouroBairro", colecaoLogradouroBairro);

			// Logradouro CEP

			FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

			filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("cep");
			filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_LOGRADOURO, idLogradouro));

			filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoLogradouroCep = fachada.pesquisar(filtroLogradouroCep, LogradouroCep.class.getName());

			httpServletRequest.setAttribute("colecaoLogradouroCep", colecaoLogradouroCep);
		}

		// Dados da 2 aba

		Integer cont;

		if(sessao.getAttribute("FaturamentoTarifaSocial") == null){
			cont = 1;
			sessao.setAttribute("FaturamentoTarifaSocial", cont);

			String anoMesFaturamento = Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento().toString());
			form.setMesAnoReferencia("" + anoMesFaturamento);

			form.setIndicadorClienteAtualFatura("" + sistemaParametro.getIndicadorClienteAtualFatura());

			form.setMenorConsumo("" + sistemaParametro.getMenorConsumoGrandeUsuario());

			if(sistemaParametro.getValorMinimoEmissaoConta() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getValorMinimoEmissaoConta());

				form.setMenorValor("" + valorAux);
			}

			form.setQtdeEconomias("" + sistemaParametro.getMenorEconomiasGrandeUsuario());

			form.setMesesCalculoMedio("" + sistemaParametro.getMesesMediaConsumo());

			form.setDiasVencimentoCobranca("" + sistemaParametro.getNumeroMinimoDiasEmissaoVencimento());

			form.setDiasMinimoVencimento("" + sistemaParametro.getNumeroMinimoDiasEmissaoVencimento());

			form.setDiasMinimoVencimentoCorreio("" + sistemaParametro.getNumeroDiasAdicionaisCorreios());

			form.setNumeroMesesValidadeConta("" + sistemaParametro.getNumeroMesesValidadeConta());

			form.setNumeroMesesAlteracaoVencimento("" + sistemaParametro.getNumeroMesesMinimoAlteracaoVencimento());

			if(sistemaParametro.getValorSalarioMinimo() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo());

				form.setSalarioMinimo("" + valorAux);

			}

			form.setAreaMaxima("" + sistemaParametro.getAreaMaximaTarifaSocial());

			form.setConsumoMaximo("" + sistemaParametro.getConsumoEnergiaMaximoTarifaSocial());

			if(sistemaParametro.getTituloPagina() == null){
				form.setTitulosRelatorio("");
			}else{
				form.setTitulosRelatorio("" + sistemaParametro.getTituloPagina());
			}

			if(sistemaParametro.getNumeroMaximoTiposDebitoEmissaoDocumento() != null){
				form.setNumeroMaximoTiposDebitoEmissaoDocumento("" + sistemaParametro.getNumeroMaximoTiposDebitoEmissaoDocumento());
			}

			if(sistemaParametro.getNumeroDiasEsperaExtratoDebito() != null){
				form.setNumeroDiasEsperaExtratoDebito("" + sistemaParametro.getNumeroDiasEsperaExtratoDebito());
			}else{
				form.setNumeroDiasEsperaExtratoDebito("");
			}

			if(sistemaParametro.getAnoReferenciaDebitoConta() != null){
				form.setAnoReferenciaDebitoConta("" + sistemaParametro.getAnoReferenciaDebitoConta());
			}else{
				form.setAnoReferenciaDebitoConta("");
			}

			String numeroDiasEsperaMenor = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.menor");
			String numeroDiasEsperaMaior = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.maior");
			form.setNumeroDiasEsperaMenor(numeroDiasEsperaMenor);
			form.setNumeroDiasEsperaMaior(numeroDiasEsperaMaior);
		}

		// Dados da 3 aba

		// Verifica se os dados foram informados da tabela existem e joga
		// numa
		// colecao

		FiltroContaBancaria filtroContaBancaria = new FiltroContaBancaria();

		filtroContaBancaria.setCampoOrderBy(FiltroContaBancaria.ID);

		Collection<ContaBancaria> colecaoContaBancaria = fachada.pesquisar(filtroContaBancaria, ContaBancaria.class.getName());

		if(colecaoContaBancaria == null || colecaoContaBancaria.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Conta Bancaria");
		}

		httpServletRequest.setAttribute("colecaoContaBancaria", colecaoContaBancaria);

		// Verifica se ja entrou nesse action, caso nao coloca no form os dados
		// do objeto sistemaParametro

		Integer cont1;

		if(sessao.getAttribute("ArrecadacaoFinanceiro") == null){
			cont1 = 1;
			sessao.setAttribute("ArrecadacaoFinanceiro", cont1);

			String anoMesArrecadacao = Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesArrecadacao().toString());
			form.setMesAnoReferenciaArrecadacao("" + anoMesArrecadacao);

			// form.setMesAnoReferenciaArrecadacao(""
			// + sistemaParametro.getMenorConsumoGrandeUsuario());

			// Setando no form

			if(sistemaParametro.getCodigoEmpresaFebraban() != null){

				form.setCodigoEmpresaFebraban("" + sistemaParametro.getCodigoEmpresaFebraban());
			}

			if(sistemaParametro.getNumeroLayoutFebraban() != null){
				form.setNumeroLayOut("" + sistemaParametro.getNumeroLayoutFebraban());
			}
			// Conta Bancaria

			if(sistemaParametro.getContaBancaria() != null){
				form.setIndentificadorContaDevolucao("" + sistemaParametro.getContaBancaria().getId());

			}

			if(sistemaParametro.getPercentualFinanciamentoEntradaMinima() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualFinanciamentoEntradaMinima());

				form.setPercentualEntradaMinima("" + valorAux);
			}

			if(sistemaParametro.getNumeroMaximoParcelasFinanciamento() != null){

				form.setMaximoParcelas("" + sistemaParametro.getNumeroMaximoParcelasFinanciamento());
			}

			if(sistemaParametro.getPercentualMaximoAbatimento() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualMaximoAbatimento());

				form.setPercentualMaximoAbatimento("" + valorAux);
			}

			if(sistemaParametro.getPercentualTaxaJurosFinanciamento() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualTaxaJurosFinanciamento());

				form.setPercentualTaxaFinanciamento("" + valorAux);
			}

			if(sistemaParametro.getNumeroMaximoParcelaCredito() != null){

				form.setNumeroMaximoParcelaCredito("" + sistemaParametro.getNumeroMaximoParcelaCredito());
			}

			if(sistemaParametro.getPercentualMediaIndice() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualMediaIndice());

				form.setPercentualCalculoIndice("" + valorAux);
			}
		}

		// Dados da 4 Aba

		Collection colecaoHidrometroCapacidade = null;

		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

		filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.ID);

		// filtroHidrometroCapacidade
		// .adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());

		httpServletRequest.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);

		// Verifica se ja entrou nesse action, caso nao coloca no form os dados
		// do objeto sistemaParametro

		Integer cont2;

		if(sessao.getAttribute("MicromedicaoCobranca") == null){
			cont2 = 1;
			sessao.setAttribute("MicromedicaoCobranca", cont2);

			if(sistemaParametro.getHidrometroCapacidade() != null){

				form.setCodigoMenorCapacidade("" + sistemaParametro.getHidrometroCapacidade().getId());

			}

			if(sistemaParametro.getIndicadorFaixaFalsa() != null){

				form.setIndicadorGeracaoFaixaFalsa("" + sistemaParametro.getIndicadorFaixaFalsa());
			}

			if(sistemaParametro.getIndicadorUsoFaixaFalsa() != null){
				form.setIndicadorPercentualGeracaoFaixaFalsa("" + sistemaParametro.getIndicadorUsoFaixaFalsa());
			}

			if(sistemaParametro.getPercentualFaixaFalsa() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualFaixaFalsa());

				form.setPercentualGeracaoFaixaFalsa("" + valorAux);
			}

			if(sistemaParametro.getIndicadorPercentualFiscalizacaoLeitura() != null){
				form.setIndicadorPercentualGeracaoFiscalizacaoLeitura("" + sistemaParametro.getIndicadorPercentualFiscalizacaoLeitura());
			}

			if(sistemaParametro.getIndicadorUsoFiscalizadorLeitura() != null){
				form.setIndicadorGeracaoFiscalizacaoLeitura("" + sistemaParametro.getIndicadorUsoFiscalizadorLeitura());
			}

			if(sistemaParametro.getPercentualFiscalizacaoLeitura() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualFiscalizacaoLeitura());

				form.setPercentualGeracaoFiscalizacaoLeitura("" + valorAux);
			}

			if(sistemaParametro.getIncrementoMaximoConsumoRateio() != null){

				form.setIncrementoMaximoConsumo("" + sistemaParametro.getIncrementoMaximoConsumoRateio());
			}

			if(sistemaParametro.getDecrementoMaximoConsumoRateio() != null){
				form.setDecrementoMaximoConsumo("" + sistemaParametro.getDecrementoMaximoConsumoRateio());
			}

			if(sistemaParametro.getPercentualToleranciaRateio() != null){

				String valorAux = Util.formataBigDecimal(sistemaParametro.getPercentualToleranciaRateio(), 1, false);

				form.setPercentualToleranciaRateioConsumo("" + valorAux);
			}

			if(sistemaParametro.getNumeroDiasVencimentoCobranca() != null){

				form.setDiasVencimentoCobranca("" + sistemaParametro.getNumeroDiasVencimentoCobranca());

			}

			if(sistemaParametro.getIndicadorLayoutArquivoLeituraPadrao() != null){
				form.setIndicadorLayoutArquivoLeituraPadrao(sistemaParametro.getIndicadorLayoutArquivoLeituraPadrao().toString());
			}

			if(sistemaParametro.getCodigoLimiteAceitavelAnormalidades() != null){
				form.setCodigoLimiteAceitavelAnormalidades(sistemaParametro.getCodigoLimiteAceitavelAnormalidades().toString());
			}

			if(sistemaParametro.getPercentualAnormalidadeAceitavel() != null){
				String valorAux = Util.formataBigDecimal(sistemaParametro.getPercentualAnormalidadeAceitavel(), 2, false);

				form.setPercentualAnormalidadeAceitavel("" + valorAux);
			}
		}

		// Dados da 5 Aba

		if(sistemaParametro.getIndicadorSugestaoTramite() != null){

			form.setIndicadorSugestaoTramite("" + sistemaParametro.getIndicadorSugestaoTramite());
		}

		if(sistemaParametro.getDiasReativacao() != null){
			form.setDiasMaximoReativarRA("" + sistemaParametro.getDiasReativacao());
		}

		if(sistemaParametro.getDiasMaximoAlterarOS() != null){
			form.setDiasMaximoAlterarOS("" + sistemaParametro.getDiasMaximoAlterarOS());
		}

		if(sistemaParametro.getUltimoRAManual() != null){
			form.setUltimoIDGeracaoRA("" + sistemaParametro.getUltimoRAManual());
		}

		if(sistemaParametro.getUnidadeOrganizacionalIdPresidencia() != null){
			form.setIdUnidadeOrganizacionalPresidencia(sistemaParametro.getUnidadeOrganizacionalIdPresidencia().getId().toString());
		}

		if(sistemaParametro.getNumeroDiasExpiracaoAcesso() != null){
			form.setDiasMaximoExpirarAcesso("" + sistemaParametro.getNumeroDiasExpiracaoAcesso());
		}

		if(sistemaParametro.getNumeroDiasMensagemExpiracao() != null){
			form.setDiasMensagemExpiracaoSenha("" + sistemaParametro.getNumeroDiasMensagemExpiracao());
		}

		if(sistemaParametro.getNumeroMaximoLoginFalho() != null){
			form.setNumeroMaximoTentativasAcesso("" + sistemaParametro.getNumeroMaximoLoginFalho());
		}

		if(sistemaParametro.getNumeroMaximoFavorito() != null){
			form.setNumeroMaximoFavoritosMenu("" + sistemaParametro.getNumeroMaximoFavorito());
		}

		if(sistemaParametro.getIpServidorSmtp() != null){

			form.setIpServidorSmtp("" + sistemaParametro.getIpServidorSmtp());
		}

		if(sistemaParametro.getDsEmailResponsavel() != null){

			form.setEmailResponsavel("" + sistemaParametro.getDsEmailResponsavel());
		}

		/*
		 * ATRIBUTOS DO FORM
		 * private String tipoSolicitacao;
		 * private String especificacao;
		 * private String tipoSolicitacaoReiteracao;
		 * private String especificacaoReiteracao;
		 * ATRIBUTOS DO JAVABEAN
		 * ParametroSistema.solicitacaoTipoEspecificacaoDefault -> form.especificacao
		 * ParametroSistema.solicitacaoTipoEspecificacaoReiteracao -> form.especificacaoReiteracao
		 */

		// Especificação Padrão
		// private String especificacao;
		if(sistemaParametro.getSolicitacaoTipoEspecificacaoDefault() != null){
			form.setEspecificacao(sistemaParametro.getSolicitacaoTipoEspecificacaoDefault().toString());
		}

		// Tipo de Solicitação Padrão
		// private String tipoSolicitacao;
		// ** * * * * BUSCAR DO BANCO
		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, sistemaParametro
						.getSolicitacaoTipoEspecificacaoDefault()));
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection solicitacoesTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class
						.getName());

		if(solicitacoesTipoEspecificacao != null && !solicitacoesTipoEspecificacao.isEmpty()){
			// se a coleção retornou uma solicitacaoTipoEspecificaco
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			for(Iterator iterator = solicitacoesTipoEspecificacao.iterator(); iterator.hasNext();){
				solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) iterator.next();
			}
			if(solicitacaoTipoEspecificacao != null && solicitacaoTipoEspecificacao.getSolicitacaoTipo() != null){
				form.setTipoSolicitacao(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId().toString());
			}

		}


		// Tipo de Solicitação Reiteração
		// private String tipoSolicitacaoReiteracao;
		// ** * * * * BUSCAR DO BANCO
		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoReiteracao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, sistemaParametro
						.getSolicitacaoTipoEspecificacaoReiteracao()));
		filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection solicitacoesTipoReiteracao = fachada.pesquisar(filtroSolicitacaoTipoReiteracao, SolicitacaoTipoEspecificacao.class
						.getName());
		SolicitacaoTipoEspecificacao solicitacaoTipoReiteracao = new SolicitacaoTipoEspecificacao();
		for(Iterator iterator = solicitacoesTipoReiteracao.iterator(); iterator.hasNext();){
			solicitacaoTipoReiteracao = (SolicitacaoTipoEspecificacao) iterator.next();
		}

		if(solicitacaoTipoReiteracao.getSolicitacaoTipo() != null){
			form.setTipoSolicitacaoReiteracao(solicitacaoTipoReiteracao.getSolicitacaoTipo().getId().toString());
		}

		// Especificação Reiteração
		// private String especificacaoReiteracao;
		if(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao() != null){
			form.setEspecificacaoReiteracao(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao().toString());
		}

		return retorno;
	}

	private void popularComboImagensFilesPatch(HttpServletRequest httpServletRequest, HttpSession sessao, SistemaParametro sistemaParametro){

		String imagensPath = "imagens/" + sistemaParametro.getNomeAbreviadoEmpresa() + "/";
		String realPath = sessao.getServletContext().getRealPath(imagensPath);
		File file = new File(realPath);

		if(file.exists()){

			String[] nomesImagens = file.list(new FilenameFilter() {

				public boolean accept(File dir, String name){

					return name.endsWith(".jpg") || name.endsWith(".gif");
				}
			});

			httpServletRequest.setAttribute("nomesImagens", nomesImagens);
		}
	}
}
