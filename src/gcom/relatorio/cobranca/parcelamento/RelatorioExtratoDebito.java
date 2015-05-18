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
 */

package gcom.relatorio.cobranca.parcelamento;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.bean.CalcularValorDataVencimentoAnteriorHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * [UC0444] Gerar e Emitir Extrato de Débito
 * 
 * @author Vivianne Sousa
 * @date 07/09/2006
 */

public class RelatorioExtratoDebito
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7034984685957706140L;

	/**
	 * numero máximo de Contas q poderá ser impressa no rel
	 */
	private static final int QTDE_CONTAS_REL = 46;

	public RelatorioExtratoDebito(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EXTRATO_DEBITO);
	}

	@Deprecated
	public RelatorioExtratoDebito() {

		super(null, "");
	}

	private Collection<RelatorioExtratoDebitoBean> inicializarBeanRelatorio(ExtratoDebitoRelatorioHelper extratoDebitoRelatorioHelper){

		Collection<RelatorioExtratoDebitoBean> retorno = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		/*
		 * Selecionar os itens do documento de cobrança
		 * correspondentes a conta e ordenar por ano/mês de referência da conta
		 */

		if(extratoDebitoRelatorioHelper.getColecaoCobrancaDocumentoItemContas() != null
						&& !extratoDebitoRelatorioHelper.getColecaoCobrancaDocumentoItemContas().isEmpty()){

			// Iterator iteratorColecaoCobrancaDocumentoItem = null;
			// int contRegistros = 0;
			int contRegistrosTotal = 0;
			CobrancaDocumentoItem cobrancaDocumentoItem = null;
			Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItemContas = extratoDebitoRelatorioHelper
							.getColecaoCobrancaDocumentoItemContas();

			// Ordena a coleção de CobrancaDocumentoItem por ano/mês de referencia da conta
			Collections.sort((List) colecaoCobrancaDocumentoItemContas, new Comparator() {

				public int compare(Object a, Object b){

					Integer anoMesReferencia1 = new Integer(((CobrancaDocumentoItem) a).getContaGeral().getConta().getReferencia());
					Integer anoMesReferencia2 = new Integer(((CobrancaDocumentoItem) b).getContaGeral().getConta().getReferencia());

					return anoMesReferencia1.compareTo(anoMesReferencia2);

				}
			});

			String faturaAtrasada1 = "";
			String vencimentoFatura1 = "";
			String valorFatura1 = "";
			String faturaAtrasada2 = "";
			String vencimentoFatura2 = "";
			String valorFatura2 = "";

			/*
			 * Caso a quantidade de itens selecionados seja superior a QTDE_CONTAS_REL
			 * [SB0001 - Calcular Valor e Data de Vencimento Anterior]
			 * Caso contrário: Dados do primeiro e segundo itens
			 * selecionados
			 */
			Object[] cobrancaDocumentoItemContasArray = colecaoCobrancaDocumentoItemContas.toArray();

			if(colecaoCobrancaDocumentoItemContas.size() > QTDE_CONTAS_REL){
				int i = colecaoCobrancaDocumentoItemContas.size() - (QTDE_CONTAS_REL);

				CalcularValorDataVencimentoAnteriorHelper calcularValorDataVencimentoAnteriorHelper = fachada
								.calcularValorDataVencimentoAnterior(colecaoCobrancaDocumentoItemContas, QTDE_CONTAS_REL);

				faturaAtrasada1 = "DEB.ATE";
				vencimentoFatura1 = "" + Util.formatarData(calcularValorDataVencimentoAnteriorHelper.getDataVencimentoAnterior());
				valorFatura1 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(calcularValorDataVencimentoAnteriorHelper
								.getValorAnterior()), 16);
				i++;

				// Dados da primeira linha que não foi considerado anterior:

				// teste = colecaoCobrancaDocumentoItemContas.size() - (QTDE_CONTAS_REL);

				cobrancaDocumentoItem = new CobrancaDocumentoItem();

				cobrancaDocumentoItem = (CobrancaDocumentoItem) cobrancaDocumentoItemContasArray[i];

				// Mês/Ano de referência da conta
				faturaAtrasada2 = Util.completaString(Util.formatarAnoMesParaMesAno(cobrancaDocumentoItem.getContaGeral().getConta()
								.getReferencia()), 9);

				// Data de vencimento da conta
				vencimentoFatura2 = Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta());

				// Valor do item
				valorFatura2 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(cobrancaDocumentoItem.getValorItemCobrado()),
								16);
				i++;

				RelatorioExtratoDebitoBean bean = new RelatorioExtratoDebitoBean(faturaAtrasada1, vencimentoFatura1, valorFatura1,
								faturaAtrasada2, vencimentoFatura2, valorFatura2);

				retorno.add(bean);
				contRegistrosTotal++;

				for(int j = i; j < cobrancaDocumentoItemContasArray.length; j = j + 1){

					faturaAtrasada1 = "";
					vencimentoFatura1 = "";
					valorFatura1 = "";
					faturaAtrasada2 = "";
					vencimentoFatura2 = "";
					valorFatura2 = "";

					if(j < cobrancaDocumentoItemContasArray.length){
						cobrancaDocumentoItem = new CobrancaDocumentoItem();
						cobrancaDocumentoItem = (CobrancaDocumentoItem) cobrancaDocumentoItemContasArray[j];

						// Mês/Ano de referência da conta
						faturaAtrasada1 = Util.completaString(Util.formatarAnoMesParaMesAno(cobrancaDocumentoItem.getContaGeral()
										.getConta().getReferencia()), 9);

						// Data de vencimento da conta
						vencimentoFatura1 = Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta());

						// Valor do item
						valorFatura1 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(cobrancaDocumentoItem
										.getValorItemCobrado()), 16);

						j++;
					}

					if(j < cobrancaDocumentoItemContasArray.length){
						cobrancaDocumentoItem = new CobrancaDocumentoItem();
						cobrancaDocumentoItem = (CobrancaDocumentoItem) cobrancaDocumentoItemContasArray[j];

						// Mês/Ano de referência da conta
						faturaAtrasada2 = Util.completaString(Util.formatarAnoMesParaMesAno(cobrancaDocumentoItem.getContaGeral()
										.getConta().getReferencia()), 9);

						// Data de vencimento da conta
						vencimentoFatura2 = Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta());

						// Valor do item
						valorFatura2 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(cobrancaDocumentoItem
										.getValorItemCobrado()), 16);

					}

					bean = new RelatorioExtratoDebitoBean(faturaAtrasada1, vencimentoFatura1, valorFatura1, faturaAtrasada2,
									vencimentoFatura2, valorFatura2);

					retorno.add(bean);
					contRegistrosTotal++;

				}

			}else{

				for(int j = 0; j < cobrancaDocumentoItemContasArray.length; j = j + 1){

					faturaAtrasada1 = "";
					vencimentoFatura1 = "";
					valorFatura1 = "";
					faturaAtrasada2 = "";
					vencimentoFatura2 = "";
					valorFatura2 = "";

					if(j < cobrancaDocumentoItemContasArray.length){
						cobrancaDocumentoItem = new CobrancaDocumentoItem();
						cobrancaDocumentoItem = (CobrancaDocumentoItem) cobrancaDocumentoItemContasArray[j];

						// Mês/Ano de referência da conta
						faturaAtrasada1 = Util.completaString(Util.formatarAnoMesParaMesAno(cobrancaDocumentoItem.getContaGeral()
										.getConta().getReferencia()), 9);

						// Data de vencimento da conta
						vencimentoFatura1 = Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta());

						// Valor do item
						valorFatura1 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(cobrancaDocumentoItem
										.getValorItemCobrado()), 16);

						j++;
					}

					if(j < cobrancaDocumentoItemContasArray.length){
						cobrancaDocumentoItem = new CobrancaDocumentoItem();
						cobrancaDocumentoItem = (CobrancaDocumentoItem) cobrancaDocumentoItemContasArray[j];

						// Mês/Ano de referência da conta
						faturaAtrasada2 = Util.completaString(Util.formatarAnoMesParaMesAno(cobrancaDocumentoItem.getContaGeral()
										.getConta().getReferencia()), 9);

						// Data de vencimento da conta
						vencimentoFatura2 = Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta());

						// Valor do item
						valorFatura2 = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(cobrancaDocumentoItem
										.getValorItemCobrado()), 16);

					}

					RelatorioExtratoDebitoBean bean = new RelatorioExtratoDebitoBean(faturaAtrasada1, vencimentoFatura1, valorFatura1,
									faturaAtrasada2, vencimentoFatura2, valorFatura2);

					retorno.add(bean);
					contRegistrosTotal++;

				}
			}

			while(contRegistrosTotal < (QTDE_CONTAS_REL / 2)){
				RelatorioExtratoDebitoBean beanVazio = new RelatorioExtratoDebitoBean("", "", "", "", "", "");

				retorno.add(beanVazio);
				contRegistrosTotal++;
			}
		}else{
			int contRegistrosTotal = 0;
			while(contRegistrosTotal < (QTDE_CONTAS_REL / 2)){
				RelatorioExtratoDebitoBean beanVazio = new RelatorioExtratoDebitoBean("", "", "", "", "", "");

				retorno.add(beanVazio);
				contRegistrosTotal++;
			}
		}

		return retorno;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		String nomeLocalidade = (String) getParametro("nomeLocalidade");
		String inscricao = (String) getParametro("inscricao");
		String nomeUsuario = (String) getParametro("nomeUsuario");
		String matricula = (String) getParametro("matricula");
		String enderecoImovel = (String) getParametro("enderecoImovel");
		String seqDocCobranca = (String) getParametro("seqDocCobranca");
		String situacaoAgua = (String) getParametro("situacaoAgua");
		String situacaoEsgoto = (String) getParametro("situacaoEsgoto");
		String qtdResidencial = (String) getParametro("qtdResidencial");
		String qtdComercial = (String) getParametro("qtdComercial");
		String qtdIndustrial = (String) getParametro("qtdIndustrial");
		String qtdPublico = (String) getParametro("qtdPublico");
		String descPerfilImovel = (String) getParametro("descPerfilImovel");
		String dataEmissao = (String) getParametro("dataEmissao");
		String dataValidade = (String) getParametro("dataValidade");
		String representacaoNumericaCodBarra = (String) getParametro("representacaoNumericaCodBarra");
		String representacaoNumericaCodBarraSemDigito = (String) getParametro("representacaoNumericaCodBarraSemDigito");

		String valorTotalContas = (String) getParametro("valorTotalContas");
		String valorTotalSucumbencia = (String) getParametro("valorTotalSucumbencia");
		String valorServicosAtualizacoes = (String) getParametro("valorServicosAtualizacoes");
		String valorDesconto = (String) getParametro("valorDesconto");
		String valorTotalComDesconto = (String) getParametro("valorTotalComDesconto");
		String codigoRotaESequencialRota = (String) getParametro("codigoRotaESequencialRota");
		String quantidadeParcelas = (String) getParametro("quantidadeParcelas");
		String quantidadeParcelasDebitos = (String) getParametro("quantidadeParcelasDebitos");
		Integer quantidadeDebitoACobrar = (Integer) getParametro("quantidadeDebitoACobrar");
		Integer quantidadeParcelamento = (Integer) getParametro("quantidadeParcelamento");
		String descricaoSucumbencia = (String) getParametro("descricaoSucumbencia");

		String mensagemPagamentoAVista = (String) getParametro("mensagemPagamentoAVista");

		String mensagem1 = "";
		String mensagem2 = "";
		String mensagem3 = "";

		if(!Util.isVazioOuBranco(mensagemPagamentoAVista)){
			String[] mensagens = mensagemPagamentoAVista.split(";");

			if(mensagens.length == 1){
				mensagem1 = mensagens[0];
			}else if(mensagens.length == 2){
				mensagem1 = mensagens[0];
				mensagem2 = mensagens[1];
			}else if(mensagens.length == 3){
				mensagem1 = mensagens[0];
				mensagem2 = mensagens[1];
				mensagem3 = mensagens[2];
			}
		}else{
			mensagem1 = "Juros e Multas eventuais pelo não pagamento na data de emissão serão acrescidos na próxima fatura.";
		}

		ExtratoDebitoRelatorioHelper extratoDebitoRelatorioHelper = (ExtratoDebitoRelatorioHelper) getParametro("extratoDebitoRelatorioHelper");
		Integer debitoTipo = (Integer) getParametro("cobrarTaxaDebitoTipo");
		Boolean gerarDebitoACobrarTaxa = (Boolean) getParametro("gerarDebitoACobrarTaxa");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		ExtratoDebitoRelatorioHelper dadosRelatorio = extratoDebitoRelatorioHelper;

		Collection<RelatorioExtratoDebitoBean> colecaoBean = this.inicializarBeanRelatorio(dadosRelatorio);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		if(gerarDebitoACobrarTaxa != null && gerarDebitoACobrarTaxa && debitoTipo != null){
			SistemaParametro sistema = null;
			try{
				sistema = getControladorUtil().pesquisarParametrosDoSistema();
			}catch(ControladorException e2){
				e2.printStackTrace();
			}
			fachada.gerarDebitoACobrar(Integer.valueOf(matricula), sistema.getAnoMesFaturamento(), debitoTipo, null);
		}

		// Residêncial
		if(!qtdResidencial.equals("")){
			qtdResidencial = Util.adicionarZerosEsquedaNumero(3, qtdResidencial);
		}
		// Comercial
		if(!qtdComercial.equals("")){
			qtdComercial = Util.adicionarZerosEsquedaNumero(3, qtdComercial);
		}
		// Industrial
		if(!qtdIndustrial.equals("")){
			qtdIndustrial = Util.adicionarZerosEsquedaNumero(3, qtdIndustrial);
		}
		// Público
		if(!qtdPublico.equals("")){
			qtdPublico = Util.completaStringComEspacoAEsquerda(qtdPublico, 3);
		}

		// Linha 1
		parametros.put("nomeLocalidade", nomeLocalidade);

		// Linha 2
		parametros.put("inscricao", inscricao);
		parametros.put("nomeUsuario", nomeUsuario);
		
		// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
		// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
		String matriculaImovel = Util.retornaMatriculaImovelParametrizada(Integer.valueOf(Imovel
						.getMatriculaComDigitoVerificador(matricula)));

		parametros.put("matricula", matriculaImovel);

		// Linha 3
		parametros.put("enderecoImovel", enderecoImovel);
		parametros.put("seqDocCobranca", seqDocCobranca);

		// Linha 4
		parametros.put("situacaoAgua", situacaoAgua);
		parametros.put("situacaoEsgoto", situacaoEsgoto);
		parametros.put("qtdResidencial", qtdResidencial);
		parametros.put("qtdComercial", qtdComercial);
		parametros.put("qtdIndustrial", qtdIndustrial);
		parametros.put("qtdPublico", qtdPublico);
		parametros.put("descPerfilImovel", descPerfilImovel);
		parametros.put("dataEmissao", dataEmissao);
		parametros.put("dataValidade", dataValidade);

		if(Util.isNaoNuloBrancoZero(quantidadeParcelamento) && quantidadeParcelamento.equals(ConstantesSistema.SIM.intValue())){
			parametros.put("quantidadeParcelas", quantidadeParcelas);
			parametros.put("quantidadeParcelamento", quantidadeParcelamento.toString());
		}else{
			parametros.put("quantidadeParcelas", null);
			parametros.put("quantidadeParcelamento", "");
		}

		if(Util.isNaoNuloBrancoZero(quantidadeDebitoACobrar) && quantidadeDebitoACobrar.equals(ConstantesSistema.SIM.intValue())){
			parametros.put("quantidadeDebitoACobrar", quantidadeDebitoACobrar.toString());
			parametros.put("quantidadeParcelasDebitos", quantidadeParcelasDebitos);
		}else{
			parametros.put("quantidadeDebitoACobrar", null);
			parametros.put("quantidadeParcelasDebitos", "");
		}

		// Linha 7
		parametros.put("descricaoSucumbencia", descricaoSucumbencia);
		parametros.put("valorTotalSucumbencia", valorTotalSucumbencia);
		parametros.put("valorTotalContas", valorTotalContas);

		// Linha 8
		parametros.put("valorServicosAtualizacoes", valorServicosAtualizacoes);

		// Linha 9
		parametros.put("valorDesconto", valorDesconto);

		// Linha 10
		parametros.put("valorTotalComDesconto", valorTotalComDesconto);

		// Linha 11
		parametros.put("representacaoNumericaCodBarra", representacaoNumericaCodBarra);
		parametros.put("representacaoNumericaCodBarraSemDigito", representacaoNumericaCodBarraSemDigito);

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagemConta", sistemaParametro.getImagemConta());

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

		if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){
			codigoRotaESequencialRota = null;
		}

		parametros.put("codigoRotaESequencialRota", codigoRotaESequencialRota);

		parametros.put("mensagem1", mensagem1);
		parametros.put("mensagem2", mensagem2);
		parametros.put("mensagem3", mensagem3);

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());

		String pLabelMatriculaDocumentosPagaveis = null;

		try{

			pLabelMatriculaDocumentosPagaveis = (String) ParametroFaturamento.P_LABEL_MATRICULA_DOCUMENTOS_PAGAVEIS.executar();
		}catch(ControladorException e){

			throw new TarefaException(e.getMessage(), e);
		}

		parametros.put("P_LABEL_MATRICULA_DOCUMENTOS_PAGAVEIS", pLabelMatriculaDocumentosPagaveis.toUpperCase());

		if(pLabelMatriculaDocumentosPagaveis.toUpperCase().equals("CDC-DV")){
			parametros.put("matriculaFormatada", Imovel.getMatriculaComDigitoVerificadorFormatada(Integer.valueOf(matricula).toString()));
		}else{
			parametros.put("matriculaFormatada", matriculaImovel);
		}

		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", Util.formatarFone(sistemaParametro.getNumeroTelefone()));
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_EXTRATO_DEBITO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.EXTRATO_DEBITO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioExtratoDebito", this);
	}
}
