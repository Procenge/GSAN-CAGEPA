/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
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
package gcom.cobranca;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.PagamentoTipo;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cobranca.bean.CobrancaDocumentoItemHelper;
import gcom.cobranca.bean.EmitirArquivoPdfAvisoCorteHelper;
import gcom.cobranca.bean.EmitirDocumentoOrdemCorteModelo4e5Helper;
import gcom.cobranca.bean.IEmitirArquivoAvisoOrdemCorteHelper;
import gcom.relatorio.cobranca.RelatorioAvisoCorteModelo4;
import gcom.relatorio.cobranca.RelatorioAvisoCorteModelo5;
import gcom.relatorio.cobranca.RelatorioOrdemCorteModelo4;
import gcom.relatorio.cobranca.RelatorioOrdemCorteModelo5;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;

/**
 * Title: GSAN -
 * Description: Sistema de Gestão Comercial
 * Copyright: Copyright (c) 2004
 * Company: PROCENGE
 * 
 * @author André Lopes
 * @version 2.11
 */
public class ControladorCobrancaOrdemCorte
				implements SessionBean, IControladorCobrancaOrdemCorte {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(ControladorCobrancaOrdemCorte.class);

	SessionContext sessionContext;

	protected IRepositorioCobranca repositorioCobranca = null;

	/**
	 * Criação dos Ejbs
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove(){

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate(){

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor do ControladorBatch
	 * 
	 * @return O valor de ControladorBatch
	 */
	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}


	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Este caso de uso gera os avisos de corte.
	 * [UC0476] [SB0008] - Gerar Arquivo PDF de Ordem de Corte - Modelo 4 (CAGEPA)
	 * 
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarOrdemCorteArquivoPDFModelo4(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException{

		LOGGER.info("Iniciando Gerar Ordem Corte Arquivo PDF modelo 4. ");

		Collection<CobrancaDocumento> colecaoCobrancaDocumento = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirDocumentoOrdemCorteModelo5Helper = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoHelperOrdenada = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{

			colecaoCobrancaDocumento = repositorioCobranca.pesquisarCobrancaDocumentoArquivoPdfModelo4e5(idCronogramaAtividadeAcaoCobranca,
							idComandoAtividadeAcaoCobranca);

			if(!Util.isVazioOrNulo(colecaoCobrancaDocumento)){

				colecaoEmitirDocumentoOrdemCorteModelo5Helper = this
								.formatarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoCobrancaDocumento);

				// Completa os campos dos helpers que não foram retornados na pesquisa realizada
				// pelo método de formatação
				this.completarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoEmitirDocumentoOrdemCorteModelo5Helper, 15);

				// Ordena e metade de um lado e outra metade de outro lado.
				colecaoHelperOrdenada = this
								.ordenarColecaoEmitirArquivoAvisoOrdemCorteHelperModelo4e5(colecaoEmitirDocumentoOrdemCorteModelo5Helper);

				RelatorioOrdemCorteModelo4 relatorio = new RelatorioOrdemCorteModelo4(usuario);
				relatorio.addParametro("colecaoHelperOrdenada", colecaoHelperOrdenada);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

			}
			LOGGER.info("Finalizando Gerar Ordem Corte Arquivo PDF modelo 4. ");
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Este caso de uso gera os avisos de corte.
	 * [UC0476] [SB0008] - Gerar Arquivo PDF de Ordem de Corte - Modelo 5 (CAERD)
	 * 
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarOrdemCorteArquivoPDFModelo5(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException{

		LOGGER.info("Iniciando Gerar Ordem Corte Arquivo PDF modelo 5. ");

		Collection<CobrancaDocumento> colecaoCobrancaDocumento = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirDocumentoOrdemCorteModelo5Helper = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoHelperOrdenada = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{

			colecaoCobrancaDocumento = repositorioCobranca.pesquisarCobrancaDocumentoArquivoPdfModelo4e5(idCronogramaAtividadeAcaoCobranca,
							idComandoAtividadeAcaoCobranca);

			if(!Util.isVazioOrNulo(colecaoCobrancaDocumento)){

				colecaoEmitirDocumentoOrdemCorteModelo5Helper = this
								.formatarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoCobrancaDocumento);

				// Completa os campos dos helpers que não foram retornados na pesquisa realizada
				// pelo método de formatação
				this.completarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoEmitirDocumentoOrdemCorteModelo5Helper, 13);

				// Ordena e metade de um lado e outra metade de outro lado.
				colecaoHelperOrdenada = this
								.ordenarColecaoEmitirArquivoAvisoOrdemCorteHelperModelo4e5(colecaoEmitirDocumentoOrdemCorteModelo5Helper);
				
				
				RelatorioOrdemCorteModelo5 relatorio = new RelatorioOrdemCorteModelo5(usuario);
				relatorio.addParametro("colecaoHelperOrdenada", colecaoHelperOrdenada);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

			}
			LOGGER.info("Finalizando Gerar Ordem Corte Arquivo PDF modelo 5. ");
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private void completarEmitirDocumentoOrdemCorteModelo4e5Helper(
					Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirDocumentoOrdemCorteModelo5Helper, int limiteGrid)
					throws ControladorException{

		try{

			EmitirDocumentoOrdemCorteModelo4e5Helper emitirDocumentoOrdemCorteModelo5Helper = null;
			EmitirArquivoPdfAvisoCorteHelper emitirArquivoPdfAvisoCorteHelper = null;
			String enderecoFormatado = null;
			for(IEmitirArquivoAvisoOrdemCorteHelper helper : colecaoEmitirDocumentoOrdemCorteModelo5Helper){

				enderecoFormatado = getControladorEndereco().pesquisarEnderecoFormatado(helper.getIdImovel());

				if(helper instanceof EmitirDocumentoOrdemCorteModelo4e5Helper){

					emitirDocumentoOrdemCorteModelo5Helper = (EmitirDocumentoOrdemCorteModelo4e5Helper) helper;

					emitirDocumentoOrdemCorteModelo5Helper.setColecaoItemConta(preencherCampoCobrancaDocumentoItem(
									emitirDocumentoOrdemCorteModelo5Helper.getIdCobrancaDocumento(), limiteGrid));

					Date emissao = this.repositorioCobranca.obterDataEmissaoApresentacaoOrdemCorte(emitirDocumentoOrdemCorteModelo5Helper
									.getIdImovel());
					Date entrega = this.repositorioCobranca.obterDataApresentacaoAvisoCorte(emitirDocumentoOrdemCorteModelo5Helper
									.getIdImovel());

					emitirDocumentoOrdemCorteModelo5Helper.setDataEmissao(emissao);
					emitirDocumentoOrdemCorteModelo5Helper.setDataEntrega(entrega);

				}else if(helper instanceof EmitirArquivoPdfAvisoCorteHelper){

					emitirArquivoPdfAvisoCorteHelper = (EmitirArquivoPdfAvisoCorteHelper) helper;

					emitirArquivoPdfAvisoCorteHelper.setColecaoItemConta(preencherCampoCobrancaDocumentoItem(
									emitirArquivoPdfAvisoCorteHelper.getIdCobrancaDocumento(), limiteGrid));
				}

				helper.setEnderecoFormatado(enderecoFormatado);
				enderecoFormatado = null;
			}

		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	private Collection<CobrancaDocumentoItemHelper> preencherCampoCobrancaDocumentoItem(Integer idCobrancaDocumento, int limiteGrid)
					throws ErroRepositorioException{

		Collection<CobrancaDocumentoItemHelper> colecaoHelper = new ArrayList<CobrancaDocumentoItemHelper>();
		CobrancaDocumento cobrancaDocumento = new CobrancaDocumento();
		cobrancaDocumento.setId(idCobrancaDocumento);

		Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = this.repositorioCobranca
						.selecionarCobrancaDocumentoItemReferenteConta(cobrancaDocumento);

		int contador = 1;
		boolean maisDeTreze = false;
		CobrancaDocumentoItemHelper docItemHelper = null;
		CobrancaDocumentoItemHelper docItemHelperMaisDeTreze = new CobrancaDocumentoItemHelper();
		docItemHelperMaisDeTreze.setAnoMesReferenciaConta("Outras");
		docItemHelperMaisDeTreze.setValorAcrescimos(BigDecimal.ZERO);
		docItemHelperMaisDeTreze.setValorItemCobrado(BigDecimal.ZERO);

		for(CobrancaDocumentoItem item : colecaoCobrancaDocumentoItem){

			docItemHelper = new CobrancaDocumentoItemHelper();

			if(contador > limiteGrid){
				maisDeTreze = true;
				if(item.getValorAcrescimos() == null){
					item.setValorAcrescimos(BigDecimal.ZERO);
				}

				if(item.getValorItemCobrado() == null){
					item.setValorItemCobrado(BigDecimal.ZERO);
				}

				docItemHelperMaisDeTreze.setValorAcrescimos(item.getValorAcrescimos().add(docItemHelperMaisDeTreze.getValorAcrescimos()));
				docItemHelperMaisDeTreze
								.setValorItemCobrado(item.getValorItemCobrado().add(docItemHelperMaisDeTreze.getValorItemCobrado()));

			}else{
				if(Util.isNaoNuloBrancoZero(item.getContaGeral()) && Util.isNaoNuloBrancoZero(item.getContaGeral().getConta())){
					docItemHelper.setAnoMesReferenciaConta(String.valueOf(Util.formatarAnoMesParaMesAno(item.getContaGeral().getConta()
									.getReferencia())));
					docItemHelper.setDataVencimento(item.getContaGeral().getConta().getDataVencimentoConta());
				}

				docItemHelper.setValorAcrescimos(item.getValorAcrescimos());
				docItemHelper.setValorItemCobrado(item.getValorItemCobrado());
				colecaoHelper.add(docItemHelper);
			}
			contador++;
		}

		if(maisDeTreze){
			colecaoHelper.add(docItemHelperMaisDeTreze);
		}

		return colecaoHelper;
	}

	private Collection<IEmitirArquivoAvisoOrdemCorteHelper> formatarEmitirDocumentoOrdemCorteModelo4e5Helper(
					Collection<CobrancaDocumento> colecaoCobrancaDocumento){

		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoDocumentoOrdemCorteModelo5Helper = new ArrayList<IEmitirArquivoAvisoOrdemCorteHelper>();

		if(colecaoCobrancaDocumento != null){

			Iterator dadosColecaoDocumentoCobranca = colecaoCobrancaDocumento.iterator();
			while(dadosColecaoDocumentoCobranca.hasNext()){
				Object[] parametros = (Object[]) dadosColecaoDocumentoCobranca.next();

				EmitirDocumentoOrdemCorteModelo4e5Helper helper = new EmitirDocumentoOrdemCorteModelo4e5Helper();

				// Id do documento de cobrança
				if(parametros[0] != null){
					helper.setIdCobrancaDocumento((Integer) parametros[0]);
				}

				// Id do imóvel
				if(parametros[1] != null){
					helper.setIdImovel((Integer) parametros[1]);
				}

				// Data apresentação corte
				if(parametros[2] != null){
					helper.setDataApresentacaoCorte((Date) parametros[2]);
				}

				// Localidade Id
				if(parametros[3] != null){
					helper.setIdLocalidade((Integer) parametros[3]);
				}

				// Localidade Id
				if(parametros[3] != null){
					helper.setIdLocalidade((Integer) parametros[3]);
				}

				// código setor comercial
				if(parametros[4] != null){
					helper.setCodigoSetorComercial((Integer) parametros[4]);
				}

				// número quadra
				if(parametros[5] != null){
					helper.setNumeroQuadra((Integer) parametros[5]);
				}

				// número quadra
				if(parametros[6] != null){
					helper.setNumeroLote((Short) parametros[6]);
				}

				// Sub Lote
				if(parametros[7] != null){
					helper.setNumeroSubLote((Short) parametros[7]);
				}

				// Nome Cliente
				if(parametros[8] != null){
					helper.setNomeClienteUsuario((String) parametros[8]);
				}

				// Número Hidrômetro
				if(parametros[9] != null){
					helper.setNumeroHidrometro((String) parametros[9]);
				}

				// Local Instalação Hidrômetro
				if(parametros[10] != null){
					helper.setLocalHistalacaoHidrometro((String) parametros[10]);
				}

				// Id Ordem Serviço - Número da OSP
				if(parametros[11] != null){
					helper.setIdOrdemServico((Integer) parametros[11]);
				}

				// Local Instalação Hidrômetro
				if(parametros[12] != null){
					helper.setDiametroHidrometro((String) parametros[12]);
				}

				// Valor Documento
				if(parametros[13] != null){
					helper.setValorDocumento((BigDecimal) parametros[13]);
				}

				// Nome Localidade
				if(parametros[14] != null){
					helper.setNomeLocalidade((String) parametros[14]);
				}

				// Nome Município / Cidade
				if(parametros[15] != null){
					helper.setNomeMunicipio((String) parametros[15]);
				}

				// Servico Tipo
				if(parametros[16] != null){
					Integer idServicoTipo = (Integer) parametros[16];

					helper.setIdTipoServico(idServicoTipo.toString());
				}

				colecaoDocumentoOrdemCorteModelo5Helper.add(helper);
				helper = null;
			}
		}

		return colecaoDocumentoOrdemCorteModelo5Helper;
	}


	//
	// ------------------------------------------------------------FIM-ANDRÉ-------------------------------------------------------------------

	//
	// ------------------------------------------------------------JOSENILDO-------------------------------------------------------------------
	//
	//
	//
	//

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.cobranca.IControladorCobrancaOrdemCorte#gerarArquivoPdfAvisoCorteModelo5(gcom.cobranca
	 * .CobrancaAcaoAtividadeCronograma, gcom.cobranca.CobrancaAcaoAtividadeComando,
	 * gcom.seguranca.acesso.usuario.Usuario)
	 */
	public void gerarArquivoPdfAvisoCorteModelo5(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException{

		LOGGER.info("INICIO: GERANDO ARQUIVO PDF AVISO DE CORTE MODELO 5.");

		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirArquivoPdfAvisoCorteHelper = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{

			// 1. Lista de documentos de cobrança ordenada por: localidade (LOCA_ID), setor
			// (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote (IMOV_NNLOTE da tabela IMOVEL
			// com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO) e sublote (IMOV_NNSUBLOTE da tabela
			// IMOVEL com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO).
			LOGGER.info("PESQUISA: COLEÇÃO DOCUMENTOS DE COBRANÇA.");
			colecaoEmitirArquivoPdfAvisoCorteHelper = repositorioCobranca.pesquisarCobrancaDocumentoArquivoPDFAvisoCorteModelo4e5(
							idCronogramaAtividadeAcaoCobranca,
							idComandoAtividadeAcaoCobranca);

			// 3. Coleção ordenada e com os campos necessários para gerar o PDF já prenchidos e
			// formatados.
			if(!Util.isVazioOrNulo(colecaoEmitirArquivoPdfAvisoCorteHelper)){

				// Completa os campos dos helpers que não foram retornados na pesquisa realizada
				// pelo método de formatação
				LOGGER.info("COMPLETA: CAMPOS DO HELPER PARA MONTAR O RELATÓRIO.");
				this.completarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoEmitirArquivoPdfAvisoCorteHelper, 13);

				// Ordena e metade de um lado e outra metade de outro lado.
				colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada = this
								.ordenarColecaoEmitirArquivoAvisoOrdemCorteHelperModelo4e5(colecaoEmitirArquivoPdfAvisoCorteHelper);

				List<EmitirArquivoPdfAvisoCorteHelper> colecaoHelperOrdenada = (ArrayList) colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada;

				List<EmitirArquivoPdfAvisoCorteHelper> colecaoHelperOrdenadaRelatorio = new ArrayList<EmitirArquivoPdfAvisoCorteHelper>();

				// Bloco responsável por gerar o código de barras para o relatório.
				for(Integer contador = 0; contador < colecaoHelperOrdenada.size(); contador++){

					EmitirArquivoPdfAvisoCorteHelper helper = colecaoHelperOrdenada.get(contador);

					String representacaoNumericaCodBarra = "";

					// [UC0229] Obtém a representação numérica do código de barra
					representacaoNumericaCodBarra = this.getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
									PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL,
									helper.getValorDocumento(), helper.getIdLocalidade(), helper.getIdImovel(), null, null, null, null,
									helper.getNumeroSequenciaDocumento().toString(), helper.getDocumentoTipoId(), null, null, null, null,
									null, null);

					// Formata a representação númerica do código de barras
					String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
									+ representacaoNumericaCodBarra.substring(11, 12) + " "
									+ representacaoNumericaCodBarra.substring(12, 23) + "-"
									+ representacaoNumericaCodBarra.substring(23, 24) + " "
									+ representacaoNumericaCodBarra.substring(24, 35) + "-"
									+ representacaoNumericaCodBarra.substring(35, 36) + " "
									+ representacaoNumericaCodBarra.substring(36, 47) + "-"
									+ representacaoNumericaCodBarra.substring(47, 48);

					helper.setRepresentacaoNumericaCodBarra(representacaoNumericaCodBarraFormatada);

					String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
									+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
									+ representacaoNumericaCodBarra.substring(36, 47);

					helper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

					colecaoHelperOrdenadaRelatorio.add(helper);

				}

				LOGGER.info("RELATÓRIO: ENVIANDO PARA RELATÓRIO.");
				RelatorioAvisoCorteModelo5 relatorio = new RelatorioAvisoCorteModelo5(usuario);
				relatorio.addParametro("colecaoHelperOrdenada", colecaoHelperOrdenadaRelatorio);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

			}

			LOGGER.info("FIM: GERANDO ARQUIVO PDF AVISO DE CORTE MODELO 5.");

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}
	
	private Collection<IEmitirArquivoAvisoOrdemCorteHelper> ordenarColecaoEmitirArquivoAvisoOrdemCorteHelperModelo4e5(
					Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirArquivoPdfAvisoCorteHelper)
					throws ControladorException{

		LOGGER.info("INICIO: ORDENANDO O HELPER E DIVIDINDO EM DUAS PARTES (METADE1 E METADE2).");
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoRetorno = new ArrayList<IEmitirArquivoAvisoOrdemCorteHelper>();
		Map<Integer, IEmitirArquivoAvisoOrdemCorteHelper> colecaoHashMap = new HashMap<Integer, IEmitirArquivoAvisoOrdemCorteHelper>();

		try{
			// Ordenar por mais de um campo
			List camposOrdenados = new ArrayList();
			camposOrdenados.add(new BeanComparator("idLocalidade"));
			camposOrdenados.add(new BeanComparator("codigoSetorComercial"));
			camposOrdenados.add(new BeanComparator("numeroQuadra"));
			camposOrdenados.add(new BeanComparator("numeroLote"));
			camposOrdenados.add(new BeanComparator("numeroSubLote"));
			

			ComparatorChain esquemaOrdenacao = new ComparatorChain(camposOrdenados);
			Collections.sort((List) colecaoEmitirArquivoPdfAvisoCorteHelper, esquemaOrdenacao);

			// Gerar sequencial das contas antes da ordenação
			int totalContas = colecaoEmitirArquivoPdfAvisoCorteHelper.size();
			int sequencialAtual = 1;

			for(IEmitirArquivoAvisoOrdemCorteHelper helper : colecaoEmitirArquivoPdfAvisoCorteHelper){

				helper.setSequencialImpressao(sequencialAtual);
				helper.setTotalContasImpressao(totalContas);
				sequencialAtual++;
			}
			// Fim Gerar Sequencial

			Integer contador = Integer.valueOf(1);
			for(IEmitirArquivoAvisoOrdemCorteHelper helper : colecaoEmitirArquivoPdfAvisoCorteHelper){
				colecaoHashMap.put(contador, helper);
				contador++;
			}

			Integer metade = null;
			int resto = colecaoEmitirArquivoPdfAvisoCorteHelper.size() % 2;
			if(resto == 1){
				metade = (colecaoEmitirArquivoPdfAvisoCorteHelper.size() / 2) + 1;
			}else{
				metade = colecaoEmitirArquivoPdfAvisoCorteHelper.size() / 2;
			}

			for(int i = 1; i <= metade; i++){
				colecaoRetorno.add(colecaoHashMap.get(Integer.valueOf(i)));
				if(colecaoHashMap.get(Integer.valueOf(i) + metade) != null){
					colecaoRetorno.add(colecaoHashMap.get(Integer.valueOf(i) + metade));
				}
			}

		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
		LOGGER.info("FIM: ORDENANDO O HELPER E DIVIDINDO EM DUAS PARTES (METADE1 E METADE2).");
		return colecaoRetorno;
	}



	//
	//
	//
	// ---------------------------------------------------------FIM-JOSENILDO-------------------------------------------------------------------
	//

	//
	//
	//
	// ---------------------------------------------------------Inicio-Vicente----------------------------------------------------------------------
	//
	public void gerarArquivoPdfAvisoCorteModelo4(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException{

		LOGGER.info("INICIO: GERANDO ARQUIVO PDF AVISO DE CORTE MODELO 4.");

		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirArquivoPdfAvisoCorteHelper = null;
		Collection<IEmitirArquivoAvisoOrdemCorteHelper> colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{

			// 1. Lista de documentos de cobrança ordenada por: localidade (LOCA_ID), setor
			// (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote (IMOV_NNLOTE da tabela IMOVEL
			// com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO) e sublote (IMOV_NNSUBLOTE da tabela
			// IMOVEL com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO).
			// Gera o código de Barras
			LOGGER.info("PESQUISA: COLEÇÃO DOCUMENTOS DE COBRANÇA.");
			colecaoEmitirArquivoPdfAvisoCorteHelper = repositorioCobranca.pesquisarCobrancaDocumentoArquivoPDFAvisoCorteModelo4e5(
							idCronogramaAtividadeAcaoCobranca, idComandoAtividadeAcaoCobranca);

			// 3. Coleção ordenada e com os campos necessários para gerar o PDF já prenchidos e
			// formatados.
			if(!Util.isVazioOrNulo(colecaoEmitirArquivoPdfAvisoCorteHelper)){

				// Completa os campos dos helpers que não foram retornados na pesquisa realizada
				// pelo método de formatação
				LOGGER.info("COMPLETA: CAMPOS DO HELPER PARA MONTAR O RELATÓRIO.");
				this.completarEmitirDocumentoOrdemCorteModelo4e5Helper(colecaoEmitirArquivoPdfAvisoCorteHelper, 13);


				// Ordena e metade de um lado e outra metade de outro lado.
				colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada = this
								.ordenarColecaoEmitirArquivoAvisoOrdemCorteHelperModelo4e5(colecaoEmitirArquivoPdfAvisoCorteHelper);

				List<EmitirArquivoPdfAvisoCorteHelper> colecaoHelperOrdenada = (ArrayList) colecaoEmitirArquivoPdfAvisoCorteHelperOrdenada;

				List<EmitirArquivoPdfAvisoCorteHelper> colecaoHelperOrdenadaRelatorio = new ArrayList<EmitirArquivoPdfAvisoCorteHelper>();

				// Bloco responsável por gerar o código de barras para o relatório.
				for(Integer contador = 0; contador < colecaoHelperOrdenada.size(); contador++){

					EmitirArquivoPdfAvisoCorteHelper helper = colecaoHelperOrdenada.get(contador);

					String representacaoNumericaCodBarra = "";

					// [UC0229] Obtém a representação numérica do código de barra
					representacaoNumericaCodBarra = this.getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(5,
									helper.getValorDocumento(), helper.getIdLocalidade(), helper.getIdImovel(), null, null, null, null,
									helper.getNumeroSequenciaDocumento().toString(), helper.getDocumentoTipoId(), null, null, null, null,
									null, null);

					// Formata a representação númerica do código de barras
					String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
									+ representacaoNumericaCodBarra.substring(11, 12) + " "
									+ representacaoNumericaCodBarra.substring(12, 23) + "-"
									+ representacaoNumericaCodBarra.substring(23, 24) + " "
									+ representacaoNumericaCodBarra.substring(24, 35) + "-"
									+ representacaoNumericaCodBarra.substring(35, 36) + " "
									+ representacaoNumericaCodBarra.substring(36, 47) + "-"
									+ representacaoNumericaCodBarra.substring(47, 48);

					helper.setRepresentacaoNumericaCodBarra(representacaoNumericaCodBarraFormatada);

					String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
									+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
									+ representacaoNumericaCodBarra.substring(36, 47);

					helper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

					colecaoHelperOrdenadaRelatorio.add(helper);

				}

				LOGGER.info("RELATÓRIO: ENVIANDO PARA RELATÓRIO.");
				RelatorioAvisoCorteModelo4 relatorio = new RelatorioAvisoCorteModelo4(usuario);
				relatorio.addParametro("colecaoHelperOrdenada", colecaoHelperOrdenadaRelatorio);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

			}

			LOGGER.info("FIM: GERANDO ARQUIVO PDF AVISO DE CORTE MODELO 4.");

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}
}