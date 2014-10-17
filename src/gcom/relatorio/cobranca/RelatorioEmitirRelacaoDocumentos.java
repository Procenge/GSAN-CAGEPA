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

package gcom.relatorio.cobranca;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaDocumentoItem;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Cinthya Cavalcanti
 * @date 22/12/2011
 */

public class RelatorioEmitirRelacaoDocumentos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioEmitirRelacaoDocumentos
	 */
	public RelatorioEmitirRelacaoDocumentos(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA);
	}

	@Deprecated
	public RelatorioEmitirRelacaoDocumentos() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		try{
			/*
			 * Se relatorio solicitado por processo de ação comandada de geracao de aviso de
			 * corte.
			 */
			Boolean porFluxoFaturamento = (Boolean) getParametro("gerarRelatorioAvisoCortePorFluxoFaturamento");
			if(Boolean.TRUE.equals(porFluxoFaturamento)){
				System.out.println("Criando o  relatório de  relacao de documentos de cobranca pelo FATURAMENTO");
				// Colecao de IDs de Documentos Cobrnaca gerados no processo de faturamento
				List<Integer> listaIdDocumentosCobranca = (List<Integer>) getParametro("listaIdsDocumentosCobranca");
				retorno = gerarRelatorioDocumentosGeradosAvisoCortePorFaturamento(listaIdDocumentosCobranca);
			}else{
				retorno = gerarRelatorioDocumentosgeradosAvisoCortePorComando();
			}

		}catch(ControladorException e1){
			e1.printStackTrace();
		}
		return retorno;
	}

	private byte[] gerarRelatorioDocumentosGeradosAvisoCortePorFaturamento(List<Integer> listaIdDocumentosCobranca)
					throws ControladorException{

		byte[] retorno;
		List<RelatorioEmitirRelacaoDocumentosBean> collEmitirRelacaoDocumentosBean = new ArrayList<RelatorioEmitirRelacaoDocumentosBean>();
		CobrancaDocumento cobrancaDocumento;
		// ----------------------------------
		CobrancaAcaoAtividadeComando comandoAtividadeAcaoComando = (CobrancaAcaoAtividadeComando) getParametro("comandoAtividadeAcaoComando");
		Integer idCobrancaAcao = comandoAtividadeAcaoComando.getCobrancaAcao().getId();
		// ----------------------------------
		for(Integer integer : listaIdDocumentosCobranca){
			cobrancaDocumento = ServiceLocator.getInstancia().getControladorCobranca().pesquisarCobrancaDocumento(integer);
			preencherRelatorio(collEmitirRelacaoDocumentosBean, cobrancaDocumento, (String) getParametro("descricaoCobrancaAcao"),
							idCobrancaAcao);
		}
		retorno = gerarEGravarRelatorio(Util.formatarDataComHora(new Date()), collEmitirRelacaoDocumentosBean);
		return retorno;
	}

	/**
	 * @return
	 * @throws ControladorException
	 */
	private byte[] gerarRelatorioDocumentosgeradosAvisoCortePorComando() throws ControladorException{

		byte[] retorno = null;
		List<RelatorioEmitirRelacaoDocumentosBean> collEmitirRelacaoDocumentosBean = new ArrayList<RelatorioEmitirRelacaoDocumentosBean>();

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		CobrancaAcaoAtividadeComando comandoAtividadeAcaoComando = (CobrancaAcaoAtividadeComando) getParametro("comandoAtividadeAcaoComando");
		String descricaoCobrancaAcao = comandoAtividadeAcaoComando.getCobrancaAcao().getDescricaoCobrancaAcao();
		Integer idCobrancaAcao = comandoAtividadeAcaoComando.getCobrancaAcao().getId();
		Collection<CobrancaDocumento> collCobrancaDocumento = null;

		collCobrancaDocumento = fachada.pesquisarCobrancaDocumentoRelatorioEmitirRelacaoDocumentos(comandoAtividadeAcaoComando
						.getId());

		// faz uma busca pelo CobrancaAcaoAtividadeComando
		// para obter o valor da data de realização atualizada
		FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
		filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID,
						comandoAtividadeAcaoComando.getId()));
		Collection<CobrancaAcaoAtividadeComando> colecaoCobrancaAcaoAtividadeComando = fachada.pesquisar(
						filtroCobrancaAcaoAtividadeComando, CobrancaAcaoAtividadeComando.class.getName());

		Date dataRealizacao = null;

		if(colecaoCobrancaAcaoAtividadeComando != null && !colecaoCobrancaAcaoAtividadeComando.isEmpty()){

			dataRealizacao = colecaoCobrancaAcaoAtividadeComando.iterator().next().getRealizacao();

			// caso não tenha a data de realização não gera o relatório
			if(dataRealizacao != null && !dataRealizacao.equals("")){
				String data = Util.formatarDataComHora(dataRealizacao);

				if(collCobrancaDocumento != null && !collCobrancaDocumento.isEmpty()){

					for(CobrancaDocumento cobrancaDocumento : collCobrancaDocumento){
						cobrancaDocumento.setCobrancaAcaoAtividadeComando(comandoAtividadeAcaoComando);
						preencherRelatorio(collEmitirRelacaoDocumentosBean, cobrancaDocumento, descricaoCobrancaAcao, idCobrancaAcao);
					}

					retorno = gerarEGravarRelatorio(data, collEmitirRelacaoDocumentosBean);
				}
			}
		}
		return retorno;
	}

	/**
	 * Grava o relatorio no disco.
	 * 
	 * @param data
	 * @param collEmitirRelacaoDocumentosBean
	 * @return
	 */
	private byte[] gerarEGravarRelatorio(String data, List<RelatorioEmitirRelacaoDocumentosBean> collEmitirRelacaoDocumentosBean){

		byte[] retorno;
		// preenche os parâmetros no relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("dataHoraEmissao", data);

		RelatorioDataSource ds = new RelatorioDataSource(collEmitirRelacaoDocumentosBean);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA, parametros, ds,
						(Integer) getParametro("tipoFormatoRelatorio"));

		// Grava o relatório no sistema
		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA,
							this.getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		return retorno;
	}

	/**
	 * Preenche o collEmitirRelacaoDocumentosBean que será usado na geracao do relatorio.
	 * 
	 * @param collEmitirRelacaoDocumentosBean
	 * @param cobrancaDocumento
	 * @param descricaoCobrancaAcao
	 */
	private void preencherRelatorio(List<RelatorioEmitirRelacaoDocumentosBean> collEmitirRelacaoDocumentosBean,
					CobrancaDocumento cobrancaDocumento, String descricaoCobrancaAcao, Integer idCobrancaAcao){

		Fachada fachada = Fachada.getInstancia();
		RelatorioEmitirRelacaoDocumentosBean relatorioEmitirRelacaoDocumentosBean = new RelatorioEmitirRelacaoDocumentosBean();

		relatorioEmitirRelacaoDocumentosBean.setDescricaoAcaoCobranca(descricaoCobrancaAcao);
		relatorioEmitirRelacaoDocumentosBean.setIdLocalidade(String.valueOf(cobrancaDocumento.getLocalidade().getId()));
		relatorioEmitirRelacaoDocumentosBean.setNomeLocalidade(cobrancaDocumento.getLocalidade().getDescricao());

		// pesquisa a inscricaoFormatada
		String inscricaoFormatada = fachada.pesquisarInscricaoImovel(cobrancaDocumento.getImovel().getId(), Boolean.TRUE);
		relatorioEmitirRelacaoDocumentosBean.setInscricaoFormatada(inscricaoFormatada);

		// numero sequencia documento
		relatorioEmitirRelacaoDocumentosBean.setNumeroSequenciaDocumento(String.valueOf(cobrancaDocumento.getNumeroSequenciaDocumento()));

		// categoria abreviada do imovel
		Collection collCategoria = fachada.pesquisarCategoriasImovel(cobrancaDocumento.getImovel());

		Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(collCategoria);

		relatorioEmitirRelacaoDocumentosBean.setCategoriaImovel(categoria.getDescricaoAbreviada());

		// quantidade de economias imovel
		Collection collCategoriaImovel = fachada.pesquisarCategoriasImovel(cobrancaDocumento.getImovel().getId());
		ImovelSubcategoria subcategoriaImovel = (ImovelSubcategoria) Util.retonarObjetoDeColecao(collCategoriaImovel);

		Categoria categoriaImovel = subcategoriaImovel.getComp_id().getSubcategoria().getCategoria();
		relatorioEmitirRelacaoDocumentosBean.setQtdEconomias(String.valueOf(categoriaImovel.getQuantidadeEconomiasCategoria()));

		// situacao agua
		relatorioEmitirRelacaoDocumentosBean
						.setSituacaoAgua(String.valueOf(cobrancaDocumento.getImovel().getLigacaoAguaSituacao().getId()));

		// situacao esgoto
		relatorioEmitirRelacaoDocumentosBean.setSituacaoEsgoto(String.valueOf(cobrancaDocumento.getImovel().getLigacaoEsgotoSituacao()
						.getId()));

		// setor comercial
		Object[] setorComercial = fachada.pesquisarSetorComercialPorCobrancaDocumento(cobrancaDocumento.getId());
		cobrancaDocumento.setCodigoSetorComercial((Integer) setorComercial[0]);
		relatorioEmitirRelacaoDocumentosBean.setCdSetorComercial(String.valueOf(setorComercial[0]));
		relatorioEmitirRelacaoDocumentosBean.setNomeSetorComercial(String.valueOf(setorComercial[1]));

		// numeor da o.s.
		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.COBRANCA_DOCUMENTO_ID, cobrancaDocumento.getId()));

		Collection collOrdemServico = fachada.pesquisar(filtroOrdemServico, OrdemServico.class.getName());
		if(!Util.isVazioOrNulo(collOrdemServico)){
			OrdemServico ordemServico = (OrdemServico) Util.retonarObjetoDeColecao(collOrdemServico);
			relatorioEmitirRelacaoDocumentosBean.setNumeroOrdemServico(String.valueOf(ordemServico.getId()));
		}

		// total descições setor

		String totalDocumentosCobrancaSetor = String.valueOf(fachada
						.pesquisarTotalDescricoesCobrancaAcaoPorSetor((Integer) setorComercial[0]));

		relatorioEmitirRelacaoDocumentosBean.setTotalDocumentosCobrancaPorSetor(totalDocumentosCobrancaSetor);

		// total descições localidade
		String totalDocumentosCobrancaLocalidade = String.valueOf(fachada
						.pesquisarTotalDescricoesCobrancaAcaoPorLocalidade(cobrancaDocumento.getLocalidade().getId()));
		relatorioEmitirRelacaoDocumentosBean.setTotalDocumentosCobrancaPorLocalidade(totalDocumentosCobrancaLocalidade);

		// captura os Ids do faturamento e do comando de cobrança, se existir
		Integer faturamentoGrupoCronogramaMensalId = null;
		if((cobrancaDocumento.getFaturamentoGrupoCronogramaMensal() != null)
						&& (cobrancaDocumento.getFaturamentoGrupoCronogramaMensal().getId() != null)){
			faturamentoGrupoCronogramaMensalId = cobrancaDocumento.getFaturamentoGrupoCronogramaMensal().getId();
		}

		Integer cobrancaAcaoAtividadeComandoId = null;
		if((cobrancaDocumento.getCobrancaAcaoAtividadeComando() != null)
						&& (cobrancaDocumento.getCobrancaAcaoAtividadeComando().getId() != null)){
			cobrancaAcaoAtividadeComandoId = cobrancaDocumento.getCobrancaAcaoAtividadeComando().getId();
		}

		// total ocorrencias setor
		String totalOcorrenciasPorSetor = String.valueOf(fachada.pesquisarTotalOcorrenciasCobrancaAcaoPorSetor(
						cobrancaDocumento.getCodigoSetorComercial(), cobrancaDocumento.getLocalidade().getId(),
						faturamentoGrupoCronogramaMensalId, cobrancaAcaoAtividadeComandoId, idCobrancaAcao));
		relatorioEmitirRelacaoDocumentosBean.setTotalOcorrenciasPorSetor(totalOcorrenciasPorSetor);

		// total ocorrencias localidade
		String totalOcorrenciasPorLocalidade = String.valueOf(fachada.pesquisarTotalOcorrenciasCobrancaAcaoPorLocalidade(cobrancaDocumento
						.getLocalidade().getId(), faturamentoGrupoCronogramaMensalId, cobrancaAcaoAtividadeComandoId, idCobrancaAcao));
		relatorioEmitirRelacaoDocumentosBean.setTotalOcorrenciasPorLocalidade(totalOcorrenciasPorLocalidade);

		// pesquisa o enderecoFormatado
		String enderecoFormatado = fachada.pesquisarEndereco(cobrancaDocumento.getImovel().getId());
		relatorioEmitirRelacaoDocumentosBean.setEnderecoImovel(enderecoFormatado);

		relatorioEmitirRelacaoDocumentosBean.setMatriculaImovel(String.valueOf(cobrancaDocumento.getImovel().getId()));

		// pesquisa cliente imovel
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, cobrancaDocumento.getImovel().getId()));
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
		Collection<ClienteImovel> colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){
			ClienteImovel clienteImovel = colecaoClienteImovel.iterator().next();
			if(clienteImovel.getClienteRelacaoTipo() != null
							&& clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.USUARIO)
							&& clienteImovel.getDataFimRelacao() == null){
				relatorioEmitirRelacaoDocumentosBean.setNomeCliente(clienteImovel.getCliente().getNome());
			}
		}

		relatorioEmitirRelacaoDocumentosBean.setValorDebito(Util.formatarMoedaReal(cobrancaDocumento.getValorDocumento()));

		// pesquisa CobrancaDocumentoItem
		FiltroCobrancaDocumentoItem filtroCobrancaDocumentoItem = new FiltroCobrancaDocumentoItem();
		filtroCobrancaDocumentoItem.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumentoItem.COBRANCA_DOCUMENTO_ID,
						cobrancaDocumento.getId()));
		Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = fachada.pesquisar(filtroCobrancaDocumentoItem,
						CobrancaDocumentoItem.class.getName());

		relatorioEmitirRelacaoDocumentosBean.setQuantidadeItens(String.valueOf(colecaoCobrancaDocumentoItem.size()));

		collEmitirRelacaoDocumentosBean.add(relatorioEmitirRelacaoDocumentosBean);
	}

	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirRelacaoDocumentos", this);
	}
}
