/*
 * Copyright (C) 2007-2007 the GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place ? Suite 330, Boston, MA 02111-1307, USA
 */

package gcom.contabil;

import gcom.arrecadacao.IRepositorioArrecadacao;
import gcom.arrecadacao.RepositorioArrecadacaoHBM;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.bean.OperacaoContabilHelper;
import gcom.arrecadacao.pagamento.*;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.cobranca.parcelamento.ParcelamentoHelper;
import gcom.contabil.bean.LancamentoContabilAnaliticoConsultaHelper;
import gcom.contabil.bean.LancamentoContabilAnaliticoHelper;
import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;
import gcom.contabil.bean.LancamentoContabilSinteticoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.IRepositorioFaturamento;
import gcom.faturamento.RepositorioFaturamentoHBM;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.*;
import gcom.faturamento.debito.*;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.util.*;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.ParametroContabil;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

import br.com.procenge.comum.exception.ConcorrenciaException;
import br.com.procenge.comum.exception.NegocioException;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Genival Barbosa
 * @created 6 de Julho de 2011
 * @version 1.0
 */

public class ControladorContabilSEJB
				implements SessionBean, ExecutorParametro {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ControladorContabilSEJB.class);

	private static final String UNDERLINE = "_";

	private static final String EVENTOCOMERCIAL = "EVENTO_COMERCIAL";

	private static final String DEBITOACOBRAR = "DEBITO_A_COBRAR";

	private static Map<String, String> mapaCodigoEventoComercial;

	SessionContext sessionContext;

	private IRepositorioContabil repositorioContabil = null;

	private IRepositorioFaturamento repositorioFaturamento = null;

	private IRepositorioUtil repositorioUtil = null;

	private IRepositorioCobranca repositorioCobranca = null;

	private IRepositorioArrecadacao repositorioArrecadacao = null;

	private IRepositorioLocalidade repositorioLocalidade = null;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioContabil = RepositorioContabilHBM.getInstancia();
		repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
		repositorioArrecadacao = RepositorioArrecadacaoHBM.getInstancia();
		repositorioLocalidade = RepositorioLocalidadeHBM.getInstancia();
	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
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

	static{
		inicializarPropriedades();
	}

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLocalidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);
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
	 * Retorna o valor de ControladorBatch
	 * 
	 * @return O valor de ControladorBatch
	 */

	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
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
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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
	 * Método responsável por inicializar o mapa de código do arquivo eventoComercial.properties
	 */
	private static void inicializarPropriedades(){

		try{
			FiltroEventoComercial filtroEventoComercial = new FiltroEventoComercial();
			filtroEventoComercial.adicionarParametro(new ParametroNaoNulo(FiltroEventoComercial.ID));

			Collection<EventoComercial> colecaoEventoComercial = Fachada.getInstancia().pesquisar(filtroEventoComercial,
							EventoComercial.class.getName());

			mapaCodigoEventoComercial = new HashMap<String, String>();
			for(EventoComercial eventoComercial : colecaoEventoComercial){
				mapaCodigoEventoComercial.put(eventoComercial.getCodigoConstante().trim(), String.valueOf(eventoComercial.getId()).trim());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public Integer obterChaveEventoComercial(String codigoEventoComercial){

		String chave = mapaCodigoEventoComercial.get(codigoEventoComercial);
		return chave == null ? null : Integer.valueOf(chave);
	}

	public void inserirLancamentoContabilSintetico(LancamentoContabilSinteticoHelper lancamentoContabilSinteticoHelper)
					throws NegocioException, ConcorrenciaException, ControladorException{

		try{
			// O Sistema obtém a data corrente;
			Date dataAtual = Util.zerarHoraMinutoSegundo(Calendar.getInstance().getTime());

			Date dataContabil = null;
			if(lancamentoContabilSinteticoHelper.getDataRealizacaoEvento() == null){
				dataContabil = dataAtual;
			}else{
				dataContabil = lancamentoContabilSinteticoHelper.getDataRealizacaoEvento();
			}

			dataContabil = Util.zerarHoraMinutoSegundo(dataContabil);

			if(lancamentoContabilSinteticoHelper.getValorTotal() != null
							&& lancamentoContabilSinteticoHelper.getValorTotal().compareTo(new BigDecimal(0)) > 0){
				Map<String, Object> mapaParametros = montarFiltroPesquisaAtributosUnicos(lancamentoContabilSinteticoHelper, dataAtual,
								dataContabil);
				int qtdLancamentos = repositorioContabil.consultarQuantidadeLancamentoContabilSinteticoInserir(mapaParametros);
				if(qtdLancamentos > 0){
					// O subfluxo Acumula Contabilização é iniciado
					if(qtdLancamentos == 1){
						synchronized(this){
							Collection<LancamentoContabilSintetico> colecaoLCS = this
											.consultarLancamentoContabilSinteticoInserir(mapaParametros);
							this.acumularContabilizacao((LancamentoContabilSintetico) Util.retonarObjetoDeColecao(colecaoLCS),
											lancamentoContabilSinteticoHelper);
						}
					}else{
						Collection<LancamentoContabilSintetico> colecaoLCS = this
										.consultarLancamentoContabilSinteticoInserir(mapaParametros);
						for(LancamentoContabilSintetico lancamentoContabilSintetico : colecaoLCS){
							if(lancamentoContabilSintetico.getEventoComercial() != null){
								LOG.error("DataAtual= " + dataAtual + " /n DataContabil= " + dataContabil + " /n eventoComer= "
												+ lancamentoContabilSintetico.getEventoComercial().getId());
							}
						}
						throw new NegocioException(
										"Não foi possível inserir o Lançamento Contábil pois existem mais de um Lançamentos com atributos idênticos.");
					}
				}else{

					// O subfluxo inclui contabilização é iniciado
					LancamentoContabilSintetico lancamentoContabilSintetico = this
									.montarLancamentoContabilSintetico(lancamentoContabilSinteticoHelper);
					lancamentoContabilSintetico.setDataGeracao(dataAtual);
					lancamentoContabilSintetico.setDataContabil(dataContabil);

					lancamentoContabilSintetico.setUltimaAlteracao(Calendar.getInstance().getTime());
					lancamentoContabilSintetico.setIndicadorUso(Short.valueOf("1"));

					Long idLancamentoSinteticoInserido = (Long) getControladorUtil().inserirOuAtualizar(lancamentoContabilSintetico);

					if(idLancamentoSinteticoInserido != null){

						lancamentoContabilSintetico.setId(idLancamentoSinteticoInserido);
					}

					Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico = lancamentoContabilSintetico
									.getListaLancamentoContabilAnalitico();

					for(LancamentoContabilAnalitico lancamentoContabilAnalitico : listaLancamentoContabilAnalitico){

						lancamentoContabilAnalitico.setUltimaAlteracao(Calendar.getInstance().getTime());
						lancamentoContabilAnalitico.setIndicadorUso(Short.valueOf("1"));
						lancamentoContabilAnalitico.setLancamentoContabilSintetico(lancamentoContabilSintetico);

						getControladorUtil().inserirOuAtualizar(lancamentoContabilAnalitico);
					}
				}
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método responsável por monta um filtro de pesquisa com o conjunto único de atributos de
	 * LancamentoContabilSintetico
	 * 
	 * @param lancamentoContabilVO
	 *            o VO de lancamento Contabil
	 * @param dataAtual
	 *            a data atual do sistema ou data de geração
	 * @param dataContabil
	 *            a data contábil
	 * @return filtro de pesquisa
	 */
	private Map<String, Object> montarFiltroPesquisaAtributosUnicos(LancamentoContabilSinteticoHelper lancamentoContabilSinteticoHelper,
					Date dataAtual, Date dataContabil){

		Map<String, Object> mapaParametros = new HashMap<String, Object>();

		mapaParametros.put("dataGeracao", dataAtual);

		if(dataContabil != null){
			mapaParametros.put("dataContabil", dataContabil);
		}
		if(lancamentoContabilSinteticoHelper.getIdUnidadeContabilAgrupamento() != null){
			mapaParametros.put("idUnidadeContabilAgrupamento", lancamentoContabilSinteticoHelper.getIdUnidadeContabilAgrupamento());
		}
		if(lancamentoContabilSinteticoHelper.getEventoComercial() != null){
			mapaParametros.put("idEventoComercial", lancamentoContabilSinteticoHelper.getEventoComercial().getId());
		}
		if(lancamentoContabilSinteticoHelper.getCategoria() != null){
			mapaParametros.put("idCategoria", lancamentoContabilSinteticoHelper.getCategoria().getId());
		}
		if(lancamentoContabilSinteticoHelper.getLancamentoItemContabil() != null){
			mapaParametros.put("idLancamentoItemContabil", lancamentoContabilSinteticoHelper.getLancamentoItemContabil().getId());
		}
		if(lancamentoContabilSinteticoHelper.getImpostoTipo() != null){
			mapaParametros.put("idImpostoTipo", lancamentoContabilSinteticoHelper.getImpostoTipo().getId());
		}
		if(lancamentoContabilSinteticoHelper.getCnpj() != null && !lancamentoContabilSinteticoHelper.getCnpj().trim().equals("")){
			mapaParametros.put("cnpj", lancamentoContabilSinteticoHelper.getCnpj());
		}
		if(lancamentoContabilSinteticoHelper.getContaBancaria() != null){
			mapaParametros.put("idContaBancaria", lancamentoContabilSinteticoHelper.getContaBancaria().getId());
		}

		return mapaParametros;
	}

	/**
	 * Método responsável por criar e popular um LancamentoContabilSintetico apartir de um
	 * LancamentoContabilVO
	 * 
	 * @param lancamentoContabilVO
	 *            o VO de LancamentoContabilSintetico
	 * @return o LancamentoContabilSintetico criado
	 */
	private LancamentoContabilSintetico montarLancamentoContabilSintetico(
					LancamentoContabilSinteticoHelper lancamentoContabilSinteticoHelper){

		LancamentoContabilSintetico retorno = null;

		if(lancamentoContabilSinteticoHelper != null){
			retorno = new LancamentoContabilSintetico();

			if(lancamentoContabilSinteticoHelper.getIdUnidadeContabilAgrupamento() != null){
				retorno.setIdUnidadeContabilAgrupamento(lancamentoContabilSinteticoHelper.getIdUnidadeContabilAgrupamento());
			}

			if(lancamentoContabilSinteticoHelper.getEventoComercial() != null){
				retorno.setEventoComercial(lancamentoContabilSinteticoHelper.getEventoComercial());
			}

			if(lancamentoContabilSinteticoHelper.getCategoria() != null){
				retorno.setCategoria(lancamentoContabilSinteticoHelper.getCategoria());
			}

			if(lancamentoContabilSinteticoHelper.getLancamentoItemContabil() != null){
				retorno.setLancamentoItemContabil(lancamentoContabilSinteticoHelper.getLancamentoItemContabil());
			}

			if(lancamentoContabilSinteticoHelper.getImpostoTipo() != null){
				retorno.setImpostoTipo(lancamentoContabilSinteticoHelper.getImpostoTipo());
			}

			if(lancamentoContabilSinteticoHelper.getCnpj() != null && !lancamentoContabilSinteticoHelper.getCnpj().trim().equals("")){
				retorno.setNumeroCNPJ(lancamentoContabilSinteticoHelper.getCnpj());
			}

			if(lancamentoContabilSinteticoHelper.getContaBancaria() != null){
				retorno.setContaBancaria(lancamentoContabilSinteticoHelper.getContaBancaria());
			}

			if(lancamentoContabilSinteticoHelper.getValorTotal() != null){
				retorno.setValor(lancamentoContabilSinteticoHelper.getValorTotal());
			}

			if(lancamentoContabilSinteticoHelper.getListaLancamentosContabeisAnaliticosHelper() != null
							&& lancamentoContabilSinteticoHelper.getListaLancamentosContabeisAnaliticosHelper().size() > 0){

				retorno.setListaLancamentoContabilAnalitico(this.montarListaLancamentoContabilAnalitico(lancamentoContabilSinteticoHelper
								.getListaLancamentosContabeisAnaliticosHelper(), retorno));
			}
		}

		return retorno;
	}

	/**
	 * Método responsável criar e montar uma lista de LancamentoContabilAnalitico apartir de uma
	 * lista de LancamentoContabilAnaliticoVO
	 * 
	 * @param listaLancamentosContabeisAnaliticos
	 *            a lista de LancamentoContabilAnaliticoVO
	 * @param dadosAuditoria
	 *            o dados auditoria
	 * @return o conjunto criado de LancamentoContabilAnalitico
	 */
	private Set<LancamentoContabilAnalitico> montarListaLancamentoContabilAnalitico(
					Collection<LancamentoContabilAnaliticoHelper> listaLancamentosContabeisAnaliticosHelper,
					LancamentoContabilSintetico lancamentoContabilSintetico){

		Set<LancamentoContabilAnalitico> retorno = new HashSet<LancamentoContabilAnalitico>();

		if(listaLancamentosContabeisAnaliticosHelper != null){
			for(LancamentoContabilAnaliticoHelper lancamentoContabilAnaliticoHelper : listaLancamentosContabeisAnaliticosHelper){
				retorno.add(this.montarLancamentoContabilAnalitico(lancamentoContabilAnaliticoHelper, lancamentoContabilSintetico));
			}
		}

		return retorno;
	}

	/**
	 * Método responsável por criar e montar um único LancamentoContabilAnalitico apartir de um
	 * lancamentoContabilAnaliticoVO
	 * 
	 * @param lancamentoContabilAnaliticoVO
	 *            o lancamentoContabilAnaliticoVO
	 * @param dadosAuditoria
	 *            o objeto dadosAuditoria
	 * @return o LancamentoContabilAnalitico criado
	 */
	private LancamentoContabilAnalitico montarLancamentoContabilAnalitico(
					LancamentoContabilAnaliticoHelper lancamentoContabilAnaliticoHelper,
					LancamentoContabilSintetico lancamentoContabilSintetico){

		LancamentoContabilAnalitico lancamentoContabilAnalitico = new LancamentoContabilAnalitico();

		if(lancamentoContabilAnaliticoHelper.getValor() != null){
			lancamentoContabilAnalitico.setValor(lancamentoContabilAnaliticoHelper.getValor());
		}
		if(lancamentoContabilAnaliticoHelper.getCodigoObjeto() != null){
			lancamentoContabilAnalitico.setCodigoObjeto(lancamentoContabilAnaliticoHelper.getCodigoObjeto());
		}

		lancamentoContabilAnalitico.setLancamentoContabilSintetico(lancamentoContabilSintetico);

		return lancamentoContabilAnalitico;
	}

	/**
	 * Método responsável por acumular o valor de um LancamentoContabilSintetico e atualiza-lo
	 * 
	 * @param lcSintetico
	 *            o LancamentoContabilSintetico
	 * @param novoValor
	 *            o valor a ser adicionado
	 * @throws NegocioException
	 *             caso ocorra algum erro
	 * @throws ConcorrenciaException
	 * @throws ErroRepositorioException
	 */
	private void acumularContabilizacao(LancamentoContabilSintetico lcSintetico,
					LancamentoContabilSinteticoHelper lancamentoContabilSintetivoHelper) throws NegocioException, ConcorrenciaException,
					ErroRepositorioException{

		LancamentoContabilSintetico lcsMontar = this.montarLancamentoContabilSintetico(lancamentoContabilSintetivoHelper);
		lcSintetico.setListaLancamentoContabilAnalitico(lcsMontar.getListaLancamentoContabilAnalitico());

		// Ao valor recuperado da contabilização é somado o valor informado como parâmetro de
		// entrada;
		lcSintetico.setValor(lcSintetico.getValor().add(lancamentoContabilSintetivoHelper.getValorTotal()));
		lcSintetico.setUltimaAlteracao(new Date());

		// O Sistema atualiza o valor acumulado.
		repositorioContabil.atualizarLancamentoContabilSintetico(lcSintetico);
	}

	/**
	 * Prepara os dados necessários e chama a rotina de registro dos lançamentos contábeis
	 * sintéticos e analíticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	public void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil) throws ControladorException{

		this.registrarLancamentoContabil(objeto, operacaoContabil, null);
	}

	/**
	 * Prepara os dados necessários e chama a rotina de registro dos lançamentos contábeis
	 * sintéticos e analíticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	public void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil,
					Collection<GuiaPagamentoCategoria> colGuiaPagamentoCategoria)
					throws ControladorException{

		// Recupera o parâmetro indicador de geração de lançamento contábil.
		String habilitarContabilizacao = ParametroContabil.P_HABILITAR_CONTABILIZACAO.executar();

		if(habilitarContabilizacao.equals(ConstantesSistema.SIM.toString())){
			if(objeto instanceof Conta){

				registrarLancamentoContabilConta((Conta) objeto, operacaoContabil, null, null);

			}else if(objeto instanceof DebitoACobrar){

				registrarLancamentoContabilDebitoACobrar((DebitoACobrar) objeto, operacaoContabil, null, null);

			}else if(objeto instanceof CreditoARealizar){

				registrarLancamentoContabilCreditoARealizar((CreditoARealizar) objeto, operacaoContabil);

			}else if(objeto instanceof Pagamento){

				registrarLancamentoContabilPagamento(objeto, operacaoContabil);

			}else if(objeto instanceof PagamentoHistorico){

				registrarLancamentoContabilPagamento(objeto, operacaoContabil);

			}else if(objeto instanceof GuiaPagamento){

				registrarLancamentoContabilGuiaPagamento((GuiaPagamento) objeto, operacaoContabil, null, colGuiaPagamentoCategoria);

			}else if(objeto instanceof ParcelamentoHelper){

				registrarLancamentoContabilParcelamento((ParcelamentoHelper) objeto, operacaoContabil);

			}else if(objeto instanceof DebitoACobrarCategoria){

				registrarLancamentoContabilTransferenciaDebitoLongoParaCurtoPrazo((DebitoACobrarCategoria) objeto, operacaoContabil);

			}else if(objeto instanceof CreditoARealizarCategoria){

				registrarLancamentoContabilTransferenciaCreditoLongoParaCurtoPrazo((CreditoARealizarCategoria) objeto, operacaoContabil);

			}else if(objeto instanceof ProvisaoDevedoresDuvidosos){

				registrarLancamentoContabilProvisaoDevedoresDuvidosos((ProvisaoDevedoresDuvidosos) objeto, operacaoContabil);

			}else{
				// System.out.println("O objeto " + objeto.getClass().getName() +
				// " não faz parte da contabilidade.");
				throw new ControladorException("O objeto " + objeto.getClass().getName() + " não faz parte da contabilidade.");
			}
		}
	}

	private void registrarLancamentoContabilProvisaoDevedoresDuvidosos(ProvisaoDevedoresDuvidosos pddv, OperacaoContabil operacaoContabil)
					throws ControladorException{

		try{
			LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
			lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

			Conta conta = Fachada.getInstancia().pesquisarContaPeloID(pddv.getContaGeral().getId());
			if(conta != null){
				Localidade localidade = this.repositorioLocalidade.obterLocalidade(conta.getLocalidade().getId());

				lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(localidade, conta.getCodigoSetorComercial()));
			}else{
				ContaHistorico contaHistorico = this.repositorioFaturamento.obterImovelLocalidadeContaHistorico(pddv.getContaGeral()
								.getId());

				Localidade localidade = this.repositorioLocalidade.obterLocalidade(contaHistorico.getLocalidade().getId());

				lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(localidade, contaHistorico.getSetorComercial()));
			}

			if(operacaoContabil.equals(OperacaoContabil.BAIXA_PROVISAO_DEVEDORES_DUVIDOSOS)){
				lcsh.setEventoComercial(pddv.getEventoComercialBaixaContaPDD());
			}else{
				lcsh.setEventoComercial(pddv.getEventoComercial());
			}

			lcsh.setCategoria(pddv.getCategoria());

			BigDecimal valorTotal = BigDecimal.ZERO;
			valorTotal = valorTotal.add(pddv.getValorAgua());
			valorTotal = valorTotal.add(pddv.getValorEsgoto());
			valorTotal = valorTotal.add(pddv.getValorDebitos());
			valorTotal = valorTotal.subtract(pddv.getValorCreditos());

			LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(valorTotal, Long.parseLong(pddv.getContaGeral()
							.getId().toString()));
			lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

			inserirLancamentoContabilSintetico(lcsh);
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}

	}

	public void transferirDebitosParaCurtoPrazo(Collection<DebitoACobrar> debitosACobrar) throws ControladorException{

		try{
			String chaveEvento = EVENTOCOMERCIAL + UNDERLINE + OperacaoContabil.TRANSFERENCIA_DEBITO_LONGO_PARA_CURTO_PRAZO;

			if(debitosACobrar != null && !debitosACobrar.isEmpty()){
				for(DebitoACobrar debitoACobrar : debitosACobrar){

					int prestacoesRestantes = debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas();
					if(prestacoesRestantes > 12){

						// Obtém as categorias considenadas nesse débito.
						List<Categoria> colecaoCategoriasObterValor = new ArrayList();

						for(DebitoACobrarCategoria debitoACobrarCategoria : (Set<DebitoACobrarCategoria>) debitoACobrar
										.getDebitoACobrarCategorias()){
							Categoria categoria = new Categoria();
							categoria.setId(debitoACobrarCategoria.getComp_id().getCategoria().getId());
							categoria.setQuantidadeEconomiasCategoria(debitoACobrarCategoria.getQuantidadeEconomia());
							colecaoCategoriasObterValor.add(categoria);
						}

						// Monta o mapa de valores de parcela para cada id de categoria.
						Map<Integer, BigDecimal[]> mapaIdsCategoriaValores = montarMapaIdsCategoriaValor(debitoACobrar.getValorDebito(),
										debitoACobrar.getNumeroPrestacaoDebito(), colecaoCategoriasObterValor);

						int numeroTransferencias = prestacoesRestantes - 12;
						for(DebitoACobrarCategoria debitoCategoria : (Set<DebitoACobrarCategoria>) debitoACobrar
										.getDebitoACobrarCategorias()){

							// Registro de transferência de parcelas normais restantes.
							while(numeroTransferencias > 1){

								LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(debitoACobrar
												.getLocalidade(), debitoACobrar.getCodigoSetorComercial(), debitoACobrar
												.getLancamentoItemContabil(), debitoACobrar.getId(), chaveEvento, mapaIdsCategoriaValores
												.get(debitoCategoria.getCategoria().getId())[0], debitoCategoria.getCategoria());

								inserirLancamentoContabilSintetico(lancamento);
								numeroTransferencias--;
							}

							// Registro de transferência da última parcela.
							LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(debitoACobrar
											.getLocalidade(), debitoACobrar.getCodigoSetorComercial(), debitoACobrar
											.getLancamentoItemContabil(), debitoACobrar.getId(), chaveEvento, mapaIdsCategoriaValores
											.get(debitoCategoria.getCategoria().getId())[1], debitoCategoria.getCategoria());

							inserirLancamentoContabilSintetico(lancamento);
						}
					}
				}
			}

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	private void registrarLancamentoContabilTransferenciaCreditoLongoParaCurtoPrazo(CreditoARealizarCategoria ctarc,
					OperacaoContabil operacaoContabil) throws ControladorException{

		try{
			String chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;

			LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(ctarc.getCreditoARealizar()
							.getLocalidade(), ctarc.getCreditoARealizar().getCodigoSetorComercial(), ctarc.getCreditoARealizar()
							.getLancamentoItemContabil(), ctarc.getCreditoARealizar().getId(), chaveEvento, ctarc.getValorCategoria(),
							ctarc.getCategoria());

			inserirLancamentoContabilSintetico(lancamento);

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	private void registrarLancamentoContabilTransferenciaDebitoLongoParaCurtoPrazo(DebitoACobrarCategoria dbacc,
					OperacaoContabil operacaoContabil) throws ControladorException{

		try{
			String chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;

			LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(
							dbacc.getDebitoACobrar().getLocalidade(), dbacc.getDebitoACobrar().getCodigoSetorComercial(), dbacc
											.getDebitoACobrar().getLancamentoItemContabil(), dbacc.getDebitoACobrar().getId(), chaveEvento,
							dbacc.getValorCategoria(), dbacc.getCategoria());

			inserirLancamentoContabilSintetico(lancamento);

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	private void registrarLancamentoContabilPagamento(Object objeto, OperacaoContabil operacaoContabil) throws ControladorException{

		try{
			if(objeto instanceof Pagamento){

				Pagamento pagamento = (Pagamento) objeto;
				if(pagamento != null){
					repositorioContabil.carregarAtributosPagamento(pagamento);
				}

				if(pagamento.getImovel() != null){
					LOG.info("### id Imovel  pagamento 1: " + pagamento.getImovel().getId());
				}

				// pagamento não classificado
				if(operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO_NAO_IDENTIFICADO)
								|| operacaoContabil.equals(OperacaoContabil.ESTORNAR_PAGAMENTO_NAO_IDENTIFICADO)){

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.CONTA.intValue() || pagamento.getConta() != null){

						// TODO verifica como pegar o setor comercial de uma conta que não existe no
						// pagamento
						lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento.getLocalidade(), null));

					}else if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.GUIA_PAGAMENTO.intValue()
									|| pagamento.getGuiaPagamentoGeral() != null){

						if(pagamento.getGuiaPagamentoGeral() != null){

							repositorioContabil.carregarAtributosGuiaPagamentoGeral(pagamento.getGuiaPagamentoGeral());

							if(pagamento.getGuiaPagamentoGeral().getIndicadorHistorico() == ConstantesSistema.SIM){

								lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento
												.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getLocalidade(), null));

							}else{

								lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento
												.getGuiaPagamentoGeral().getGuiaPagamento().getLocalidade(), null));

							}
						}else{
							if(pagamento != null && pagamento.getId() != null){

								if(pagamento.getImovel() != null){
									LOG.info("### id Imovel  pagamento 2: " + pagamento.getImovel().getId());
								}

								if(pagamento.getLocalidade() != null){
									lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento.getLocalidade(),
													null));
								}
							}
						}

					}else if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.DEBITO_A_COBRAR.intValue()
									|| pagamento.getDebitoACobrar() != null){

						if(pagamento.getDebitoACobrar() != null){

							FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
							filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, pagamento
											.getDebitoACobrar().getId()));

							Collection<DebitoACobrar> colecaoDebitoACobrar = this.getControladorUtil().pesquisar(filtroDebitoACobrar,
											DebitoACobrar.class.getName());

							if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
								repositorioContabil.carregarAtributosDebitoACobrar(pagamento.getDebitoACobrar());
								lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento.getDebitoACobrar()
												.getLocalidade(), null));
							}else{
								FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
								filtroDebitoACobrarHistorico
												.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.LOCALIDADE);
								filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
												pagamento.getDebitoACobrar().getId()));

								Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = this.getControladorUtil().pesquisar(
												filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

								if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){

									DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
													.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);

									repositorioContabil.carregarAtributosDebitoACobrarHistorico(debitoACobrarHistorico);
									lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(debitoACobrarHistorico
													.getLocalidade(), null));
								}
							}

						}else{
							if(pagamento != null && pagamento.getId() != null){

								if(pagamento.getImovel() != null){
									LOG.info("### id Imovel  pagamento 3: " + pagamento.getImovel().getId());
								}

								if(pagamento.getLocalidade() != null){
									lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamento.getLocalidade(),
													null));
								}
							}
						}

					}

					if(pagamento.getAvisoBancario() != null && pagamento.getAvisoBancario().getContaBancaria() != null){
						lcsh.setContaBancaria(pagamento.getAvisoBancario().getContaBancaria());
					}

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil)));

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(pagamento.getValorPagamento(), Long
									.parseLong(pagamento.getId().toString()));

					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(pagamento.getDataPagamento(), lcsh);

					inserirLancamentoContabilSintetico(lcsh);
				}else{
					ContaBancaria contaBancaria = null;

					if(pagamento != null && pagamento.getAvisoBancario() != null && pagamento.getAvisoBancario().getContaBancaria() != null){
						contaBancaria = pagamento.getAvisoBancario().getContaBancaria();
					}else{

						if(pagamento.getImovel() != null){
							LOG.info("### id Imovel  pagamento  (conta bancaria nula) 4: " + pagamento.getImovel().getId());
						}
					}

					if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.CONTA.intValue() || pagamento.getConta() != null){

						if(pagamento.getConta() != null && contaBancaria != null){
							registrarLancamentoContabilConta(pagamento.getConta(), operacaoContabil, contaBancaria, pagamento
											.getDataPagamento());
						}else{

							if(pagamento.getImovel() != null){
								LOG.info("### id Imovel  pagamento  (conta bancaria nula : conta) 5: " + pagamento.getImovel().getId());
							}

						}

					}else if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.GUIA_PAGAMENTO.intValue()
									|| pagamento.getGuiaPagamentoGeral() != null){

						if(pagamento.getGuiaPagamentoGeral() != null){
							repositorioContabil.carregarAtributosGuiaPagamentoGeral(pagamento.getGuiaPagamentoGeral());
							registrarLancamentoContabilGuiaPagamento(pagamento.getGuiaPagamentoGeral().getGuiaPagamento().getId(),
											pagamento.getNumeroPrestacao(), operacaoContabil, contaBancaria, pagamento
															.getGuiaPagamentoGeral().getGuiaPagamento().getLocalidade(), pagamento
															.getGuiaPagamentoGeral().getGuiaPagamento().getSetorComercial(), pagamento
															.getDataPagamento(), null);
						}else{

							if(pagamento.getImovel() != null){
								LOG.info("### id Imovel  pagamento  (guia pagamento) 6: " + pagamento.getImovel().getId());

							}

						}

					}else if(pagamento.getDocumentoTipo().getId().intValue() == DocumentoTipo.DEBITO_A_COBRAR.intValue()
									|| pagamento.getDebitoACobrar() != null){

						if(pagamento.getDebitoACobrar() != null){
							repositorioContabil.carregarAtributosDebitoACobrar(pagamento.getDebitoACobrar());
							registrarLancamentoContabilDebitoACobrar(pagamento.getDebitoACobrar(), false, operacaoContabil, null,
											contaBancaria, pagamento.getDataPagamento());
						}else{

							if(pagamento.getImovel() != null){
								LOG.info("### id Imovel  pagamento (debito a cobrar) 7: " + pagamento.getImovel().getId());
							}

						}

					}

				}

			}else if(objeto instanceof PagamentoHistorico){
				// pagamento classificado
				PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) objeto;

				if(pagamentoHistorico != null){
					repositorioContabil.carregarAtributosPagamentoHistorico(pagamentoHistorico);
				}

				if(pagamentoHistorico.getImovel() != null){
					LOG.info("### id Imovel  pagamento historico (debito a cobrar) 8: " + pagamentoHistorico.getImovel().getId());
				}

				ContaBancaria contaBancaria = null;
				if(pagamentoHistorico.getAvisoBancario() != null && pagamentoHistorico.getAvisoBancario().getContaBancaria() != null){
					contaBancaria = pagamentoHistorico.getAvisoBancario().getContaBancaria();
				}else{

					if(pagamentoHistorico.getImovel() != null){
						LOG.info("### id Imovel  pagamento historico (conta bancaria) 9: " + pagamentoHistorico.getImovel().getId());
					}
				}

				if(pagamentoHistorico.getDocumentoTipo().getId().intValue() == DocumentoTipo.CONTA.intValue()
								|| pagamentoHistorico.getConta() != null){

					if(pagamentoHistorico.getConta() != null){
						registrarLancamentoContabilContaHistorico(pagamentoHistorico.getConta(), operacaoContabil, contaBancaria,
										pagamentoHistorico.getDataPagamento());

						// Verifica se conta é provisão de devedores duvidosos
						// Caso seja a operação contábil seja de inclusão ou inclusao posterior
						if(this.obterIdentificadorContaPDD(pagamentoHistorico.getConta()).equals(ConstantesSistema.SIM)
										&& (operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO) || operacaoContabil
														.equals(OperacaoContabil.INCLUIR_PAGAMENTO_POSTERIOR))){
							this.atualizarProvisaoDevedoresDuvidososLancamentoContabil(pagamentoHistorico.getConta(), null,
											ProvisaoDevedoresDuvidososMotivoBaixa.RECEBIMENTO);
						}
					}else{

						if(pagamentoHistorico.getImovel() != null){

							if(pagamentoHistorico.getImovel() != null){
								LOG.info("### id Imovel  pagamento historico 10: " + pagamentoHistorico.getImovel().getId());
							}
						}

						if(operacaoContabil.equals(OperacaoContabil.ESTORNAR_PAGAMENTO_NAO_IDENTIFICADO)){
							LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
							lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(pagamentoHistorico.getLocalidade(),
											null));
							lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL
											+ UNDERLINE + operacaoContabil)));
							lcsh.setContaBancaria(contaBancaria);

							LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(pagamentoHistorico
											.getValorPagamento(), Long.parseLong(pagamentoHistorico.getId().toString()));
							lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

							// Definição da Data de Realização do Evento ocorre de acordo com a
							// regra abaixo
							// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme
							// definido na
							// OC1069615 por Luís Eduardo
							definirDataRealizacaoEventoParaArrecadacao(pagamentoHistorico.getDataPagamento(), lcsh);
							

							inserirLancamentoContabilSintetico(lcsh);

						}
					}

				}else if(pagamentoHistorico.getDocumentoTipo().getId().intValue() == DocumentoTipo.GUIA_PAGAMENTO.intValue()
								|| pagamentoHistorico.getGuiaPagamentoGeral() != null){

					if(pagamentoHistorico.getGuiaPagamentoGeral() != null){
						repositorioContabil.carregarAtributosGuiaPagamentoGeral(pagamentoHistorico.getGuiaPagamentoGeral());

						if(pagamentoHistorico.getGuiaPagamentoGeral().getIndicadorHistorico() == ConstantesSistema.SIM){

							registrarLancamentoContabilGuiaPagamentoHistorico(pagamentoHistorico.getGuiaPagamentoGeral()
											.getGuiaPagamentoHistorico().getId(), pagamentoHistorico.getNumeroPrestacao(),
											operacaoContabil, contaBancaria, pagamentoHistorico.getGuiaPagamentoGeral()
															.getGuiaPagamentoHistorico().getLocalidade(), pagamentoHistorico
															.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getSetorComercial(),
											pagamentoHistorico.getDataPagamento());

						}else{

							registrarLancamentoContabilGuiaPagamento(pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().getId(),
											pagamentoHistorico.getNumeroPrestacao(), operacaoContabil, contaBancaria, pagamentoHistorico
															.getGuiaPagamentoGeral().getGuiaPagamento().getLocalidade(), pagamentoHistorico
															.getGuiaPagamentoGeral().getGuiaPagamento().getSetorComercial(),
											pagamentoHistorico.getDataPagamento(), null);

						}
					}else{
						if(pagamentoHistorico != null && pagamentoHistorico.getId() != null){

							if(pagamentoHistorico.getImovel() != null){
								LOG.info("### id Imovel  pagamento historico 11: " + pagamentoHistorico.getImovel().getId());
							}

						}
					}

				}else if(pagamentoHistorico.getDocumentoTipo().getId().intValue() == DocumentoTipo.DEBITO_A_COBRAR.intValue()
								|| pagamentoHistorico.getDebitoACobrar() != null){

					if(pagamentoHistorico.getDebitoACobrar() != null){
						FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
						filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
										pagamentoHistorico.getDebitoACobrar().getId()));

						Collection<DebitoACobrarHistorico> listaDebitoACobrarHistorico = repositorioUtil.pesquisar(
										filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

						if(listaDebitoACobrarHistorico != null && listaDebitoACobrarHistorico.size() > 0){

							DebitoACobrarHistorico debitoACobrarHistorico = listaDebitoACobrarHistorico.iterator().next();
							if(debitoACobrarHistorico != null){
								repositorioContabil.carregarAtributosDebitoACobrarHistorico(debitoACobrarHistorico);
								registrarLancamentoContabilDebitoACobrarHistorico(debitoACobrarHistorico, operacaoContabil, contaBancaria,
												pagamentoHistorico.getDataPagamento());
							}else{

								if(pagamentoHistorico.getImovel() != null){
									LOG.info("### id Imovel  pagamento historico 12: " + pagamentoHistorico.getImovel().getId());
								}
							}

						}

					}else{
						if(pagamentoHistorico != null && pagamentoHistorico.getId() != null){

							if(pagamentoHistorico.getImovel() != null){
								LOG.info("### id Imovel  pagamento historico 13: " + pagamentoHistorico.getImovel().getId());
							}

						}

					}

				}
			}
		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Define a Data de Realização do Evento para o módulo de Arrecadação (Pagamento/Pagamento
	 * Histórico), conforme definido na OC1069615 por Luís Eduardo
	 * 
	 * @author Luciano Galvao
	 * @date 27/05/2013
	 */
	private void definirDataRealizacaoEventoParaArrecadacao(Date dataPagamento, LancamentoContabilSinteticoHelper lcsh)
					throws NumberFormatException, ControladorException{

		if(dataPagamento != null){
			Integer referenciaDataPagamento = Util.recuperaAnoMesDaData(dataPagamento);
			Integer referenciaContabil = Integer.valueOf(ParametroContabil.P_REFERENCIA_CONTABIL.executar());

			// Se a data da realização (data pagamento) do evento for igual ao mesmo mês
			// e Ano da Referência Contábil então a data contábil do evento será a
			// própria data da realização do evento (data pagamento)
			if(referenciaDataPagamento.compareTo(referenciaContabil) >= 0){
				lcsh.setDataRealizacaoEvento(dataPagamento);

				// Se a data da realização (data pagamento) do evento for menor que o
				// mês e Ano da Referência Contábil, então a data contábil do evento
				// será a data corrente;
			}else{
				lcsh.setDataRealizacaoEvento(new Date());
			}
		}
	}

	private void registrarLancamentoContabilConta(Conta conta, OperacaoContabil operacaoContabil, ContaBancaria contaBancaria,
					Date dataPagamento)
					throws ControladorException{

		// OperacaoContabil operacaoContabilEditado = operacaoContabil;
		// if(operacaoContabil.equals(OperacaoContabil.INCLUIR_CONTA_BATCH)){
		// operacaoContabilEditado = OperacaoContabil.INCLUIR_CONTA;
		// }
		Short indicadorPDDPersistencia = new Short(conta.getIndicadorPDD().shortValue());

		repositorioContabil.carregarAtributosConta((Conta) conta);

		conta.setIndicadorPDD(indicadorPDDPersistencia);

		try{
			for(Object objeto : conta.getContaCategorias().toArray()){
				ContaCategoria contaCategoria = (ContaCategoria) objeto;

				// AGUA
				if(contaCategoria.getValorAgua() != null && contaCategoria.getValorAgua().compareTo(BigDecimal.ZERO) > 0){

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getCodigoSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.AGUA)));

					lcsh.setCategoria(contaCategoria.getComp_id().getCategoria());

					lcsh.setLancamentoItemContabil(new LancamentoItemContabil(LancamentoItemContabil.TARIFA_DE_AGUA));

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaCategoria.getValorAgua(), Long
									.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					if(dataPagamento != null){
						// Definição da Data de Realização do Evento ocorre de acordo com a regra
						// abaixo
						// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido
						// na
						// OC1069615 por Luís Eduardo
						definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);
					}

					inserirLancamentoContabilSintetico(lcsh);

				}

				// ESGOTO
				if(contaCategoria.getValorEsgoto() != null && contaCategoria.getValorEsgoto().compareTo(BigDecimal.ZERO) > 0){

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getCodigoSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.ESGOTO)));

					lcsh.setCategoria(contaCategoria.getComp_id().getCategoria());

					lcsh.setLancamentoItemContabil(new LancamentoItemContabil(LancamentoItemContabil.TARIFA_DE_ESGOTO));

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaCategoria.getValorEsgoto(), Long
									.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);

				}

			}

			for(Object objeto : conta.getDebitoCobrados()){
				DebitoCobrado debitoCobrado = (DebitoCobrado) objeto;

				for(Object objetoDebitoCobradoCategoria : debitoCobrado.getDebitoCobradoCategorias()){
					DebitoCobradoCategoria debitoCobradoCategoria = (DebitoCobradoCategoria) objetoDebitoCobradoCategoria;

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getCodigoSetorComercial()));

					if(debitoCobrado.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){
						lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL
										+ UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.DEBITO)));
					}else{
						lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL
										+ UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.PARCELAMENTO)));
					}

					lcsh.setCategoria(debitoCobradoCategoria.getCategoria());

					lcsh.setLancamentoItemContabil(debitoCobrado.getLancamentoItemContabil());

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(debitoCobradoCategoria
									.getValorCategoria(), Long.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);

					// if(operacaoContabil.equals(OperacaoContabil.INCLUIR_CONTA)){
					// LancamentoContabilSinteticoHelper lcshDebitoACobrar = new
					// LancamentoContabilSinteticoHelper();
					// lcshDebitoACobrar.setListaLancamentosContabeisAnaliticosHelper(new
					// ArrayList<LancamentoContabilAnaliticoHelper>());
					// lcshDebitoACobrar.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(),
					// conta.getCodigoSetorComercial()));
					// lcshDebitoACobrar.setEventoComercial(repositorioContabil
					// .obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
					// + OperacaoContabil.INCLUIR_DEBITO_A_COBRAR + UNDERLINE +
					// Prazo.CURTO_PRAZO)));
					// lcshDebitoACobrar.setCategoria(debitoCobradoCategoria.getCategoria());
					// lcshDebitoACobrar.setLancamentoItemContabil(debitoCobrado.getLancamentoItemContabil());
					// lcshDebitoACobrar.setContaBancaria(contaBancaria);
					// LancamentoContabilAnaliticoHelper lcahDebitoACobrar = new
					// LancamentoContabilAnaliticoHelper(debitoCobradoCategoria
					// .getValorCategoria(), Long.parseLong(conta.getId().toString()));
					// lcshDebitoACobrar.getListaLancamentosContabeisAnaliticosHelper().add(lcahDebitoACobrar);
					//
					// inserirLancamentoContabilSintetico(lcshDebitoACobrar);
					//
					// }

				}
			}

			for(Object objeto : conta.getCreditoRealizados()){
				CreditoRealizado creditoRealizado = (CreditoRealizado) objeto;

				for(Object objetoCreditoRealizadoCategoria : creditoRealizado.getCreditoRealizadoCategorias()){
					CreditoRealizadoCategoria creditoRealizadoCategoria = (CreditoRealizadoCategoria) objetoCreditoRealizadoCategoria;

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getCodigoSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.CREDITO)));

					lcsh.setCategoria(creditoRealizadoCategoria.getCategoria());

					lcsh.setLancamentoItemContabil(creditoRealizado.getLancamentoItemContabil());

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(creditoRealizadoCategoria
									.getValorCategoria(), Long.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);
				}
			}

			for(Object objeto : conta.getContaImpostosDeduzidos()){
				ContaImpostosDeduzidos contaImpostoDeduzidos = (ContaImpostosDeduzidos) objeto;

				LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
				lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

				lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
								.getCodigoSetorComercial()));

				lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
								+ operacaoContabil + UNDERLINE + ObjetoContabil.IMPOSTO)));

				lcsh.setImpostoTipo(contaImpostoDeduzidos.getImpostoTipo());

				lcsh.setContaBancaria(contaBancaria);

				LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaImpostoDeduzidos.getValorImposto(),
								Long.parseLong(conta.getId().toString()));
				lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

				// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
				// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
				// OC1069615 por Luís Eduardo
				definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

				inserirLancamentoContabilSintetico(lcsh);

			}

			// Verifica se conta é provisão de devedores duvidosos
			// Caso seja a operação contábil seja de inclusão ou inclusao posterior
			if(this.obterIdentificadorContaPDD(conta).equals(ConstantesSistema.SIM)
							&& (operacaoContabil.equals(OperacaoContabil.CANCELAR_CONTA)
											|| operacaoContabil.equals(OperacaoContabil.CANCELAR_CONTA_APT)
											|| operacaoContabil.equals(OperacaoContabil.CANCELAR_CONTA_PREFEITURA) || operacaoContabil
											.equals(OperacaoContabil.CANCELAR_CONTA_PRESCRICAO))){
				this.atualizarProvisaoDevedoresDuvidososLancamentoContabil(conta, null, ProvisaoDevedoresDuvidososMotivoBaixa.CANCELAMENTO);
			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}

	}

	private void registrarLancamentoContabilContaHistorico(ContaHistorico conta, OperacaoContabil operacaoContabil,
					ContaBancaria contaBancaria, Date dataPagamento) throws ControladorException{

		repositorioContabil.carregarAtributosContaHistorico((ContaHistorico) conta);

		try{
			for(Object objeto : conta.getContaCategoriaHistoricos().toArray()){
				ContaCategoriaHistorico contaCategoria = (ContaCategoriaHistorico) objeto;

				// AGUA
				if(contaCategoria.getValorAgua() != null && contaCategoria.getValorAgua().compareTo(BigDecimal.ZERO) > 0){

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.AGUA)));

					lcsh.setCategoria(contaCategoria.getComp_id().getCategoria());

					lcsh.setLancamentoItemContabil(new LancamentoItemContabil(LancamentoItemContabil.TARIFA_DE_AGUA));

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaCategoria.getValorAgua(), Long
									.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);

				}

				// ESGOTO
				if(contaCategoria.getValorEsgoto() != null && contaCategoria.getValorEsgoto().compareTo(BigDecimal.ZERO) > 0){

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.ESGOTO)));

					lcsh.setCategoria(contaCategoria.getComp_id().getCategoria());

					lcsh.setLancamentoItemContabil(new LancamentoItemContabil(LancamentoItemContabil.TARIFA_DE_ESGOTO));

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaCategoria.getValorEsgoto(), Long
									.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);

				}

			}

			Collection debitoCobradoCategoriaHistorico = repositorioFaturamento.buscarDebitoCobradoCategoriaHistoricoPorIdConta(conta
							.getId());

			// for(Object objeto : conta.getDebitoCobradoHistoricos()){
			// for(Object objeto : debitoCobradoCategoriaHistorico){
			// DebitoCobradoHistorico debitoCobrado = (DebitoCobradoHistorico) objeto;

			for(Object objetoDebitoCobradoCategoria : debitoCobradoCategoriaHistorico){
				DebitoCobradoCategoriaHistorico debitoCobradoCategoria = (DebitoCobradoCategoriaHistorico) objetoDebitoCobradoCategoria;

				LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
				lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

				lcsh
								.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
												.getSetorComercial()));

				if(debitoCobradoCategoria.getDebitoCobradoHistorico().getFinanciamentoTipo().getId().equals(
								FinanciamentoTipo.SERVICO_NORMAL)){
					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.DEBITO)));
				}else{
					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.PARCELAMENTO)));
				}

				lcsh.setCategoria(debitoCobradoCategoria.getCategoria());

				lcsh.setLancamentoItemContabil(debitoCobradoCategoria.getDebitoCobradoHistorico().getLancamentoItemContabil());

				lcsh.setContaBancaria(contaBancaria);

				LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(debitoCobradoCategoria.getValorCategoria(),
								Long.parseLong(conta.getId().toString()));
				lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

				// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
				// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
				// OC1069615 por Luís Eduardo
				definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

				inserirLancamentoContabilSintetico(lcsh);
			}
			// }

			for(Object objeto : conta.getCreditoRealizadoHistoricos()){
				CreditoRealizadoHistorico creditoRealizado = (CreditoRealizadoHistorico) objeto;

				for(Object objetoCreditoRealizadoCategoria : creditoRealizado.getCreditoRealizadoCategoriasHistorico()){
					CreditoRealizadoCategoriaHistorico creditoRealizadoCategoria = (CreditoRealizadoCategoriaHistorico) objetoCreditoRealizadoCategoria;

					LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
					lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

					lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
									.getSetorComercial()));

					lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
									+ operacaoContabil + UNDERLINE + ObjetoContabil.CREDITO)));

					lcsh.setCategoria(creditoRealizadoCategoria.getCategoria());

					lcsh.setLancamentoItemContabil(creditoRealizado.getLancamentoItemContabil());

					lcsh.setContaBancaria(contaBancaria);

					LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(creditoRealizadoCategoria
									.getValorCategoria(), Long.parseLong(conta.getId().toString()));
					lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

					inserirLancamentoContabilSintetico(lcsh);
				}
			}

			for(Object objeto : conta.getContaImpostosDeduzidosHistoricos()){
				ContaImpostosDeduzidosHistorico contaImpostoDeduzidos = (ContaImpostosDeduzidosHistorico) objeto;

				LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
				lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

				lcsh
								.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(conta.getLocalidade(), conta
												.getSetorComercial()));

				lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
								+ operacaoContabil + UNDERLINE + ObjetoContabil.IMPOSTO)));

				lcsh.setImpostoTipo(contaImpostoDeduzidos.getImpostoTipo());

				lcsh.setContaBancaria(contaBancaria);

				LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(contaImpostoDeduzidos.getValorImposto(),
								Long.parseLong(conta.getId().toString()));
				lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

				// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
				// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
				// OC1069615 por Luís Eduardo
				definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lcsh);

				inserirLancamentoContabilSintetico(lcsh);

			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}

	}

	public Collection<LancamentoContabilSintetico> consultarLancamentoContabilSinteticoInserir(Map<String, Object> mapaParametros)
					throws ControladorException{

		Collection<LancamentoContabilSintetico> colecaoLancamentoContabilSintetico = new ArrayList<LancamentoContabilSintetico>();
		try{

			Collection colecaoConsulta = repositorioContabil.consultarLancamentoContabilSinteticoInserir(mapaParametros);

			Iterator iterator = colecaoConsulta.iterator();

			while(iterator.hasNext()){
				Object[] element = null;
				LancamentoContabilSintetico lancamentoContabilSintetico = null;
				String descricaoUnidadeAgrupamentoContabil = null;

				element = (Object[]) iterator.next();

				lancamentoContabilSintetico = (LancamentoContabilSintetico) element[0];
				descricaoUnidadeAgrupamentoContabil = (String) element[1];

				lancamentoContabilSintetico.setUnidadeContabilAgrupamento(new UnidadeContabilAgrupamento());
				lancamentoContabilSintetico.getUnidadeContabilAgrupamento().setId(
								lancamentoContabilSintetico.getIdUnidadeContabilAgrupamento());
				lancamentoContabilSintetico.getUnidadeContabilAgrupamento().setDescricao(descricaoUnidadeAgrupamentoContabil);

				colecaoLancamentoContabilSintetico.add(lancamentoContabilSintetico);

			}
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoLancamentoContabilSintetico;
	}

	public Integer recuperarIdUnidadeContabilAgrupamento(Localidade localidade, Integer codigoSetorComercial) throws ControladorException{

		Integer retorno = 0;
		String unidadeContabilAgrupamento = ParametroContabil.UNIDADE_CONTABIL_AGRUPAMENTO.executar().toString();

		if(unidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.LOCALIDADE)){

			retorno = localidade.getId();

		}else if(unidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.GERENCIA_REGIONAL)){

			retorno = localidade.getGerenciaRegional().getId();

		}else if(unidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.SETOR_COMERCIAL)){

			retorno = codigoSetorComercial;

		}else if(unidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.UNIDADE_NEGOCIO)){

			retorno = localidade.getUnidadeNegocio().getId();

		}

		return retorno;
	}

	public EventoComercial obterEventoComercial(Integer id) throws ControladorException{

		try{
			return repositorioContabil.obterEventoComercial(id);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<LancamentoContabilSinteticoConsultaHelper> consultarLancamentoContabilSintetico(Map<String, Object> mapaParametros)
					throws ControladorException{

		try{

			return repositorioContabil.consultarLancamentoContabilSintetico(mapaParametros);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * @param debitoACobrar
	 * @param operacaoContabil
	 * @throws ControladorException
	 */
	private void registrarLancamentoContabilDebitoACobrar(DebitoACobrar debitoACobrar, OperacaoContabil operacaoContabil,
					ContaBancaria contaBancaria, Date dataPagamento) throws ControladorException{

		registrarLancamentoContabilDebitoACobrar(debitoACobrar, true, operacaoContabil, null, null, contaBancaria, dataPagamento);
	}

	/**
	 * @param debitoACobrar
	 * @param operacaoContabil
	 * @param objetoContabil
	 * @throws ControladorException
	 */
	private void registrarLancamentoContabilDebitoACobrar(DebitoACobrar debitoACobrar, boolean considerarCurtoLongoPrazo,
					OperacaoContabil operacaoContabil, ObjetoContabil objetoContabil, ContaBancaria contaBancaria, Date dataPagamento)
					throws ControladorException{

		registrarLancamentoContabilDebitoACobrar(debitoACobrar, considerarCurtoLongoPrazo, operacaoContabil, objetoContabil, null,
						contaBancaria, dataPagamento);
	}

	private void registrarLancamentoContabilDebitoACobrar(DebitoACobrar debitoACobrar, boolean considerarCurtoLongoPrazo,
					OperacaoContabil operacaoContabil, ObjetoContabil objetoContabil, Prazo prazo, ContaBancaria contaBancaria,
					Date dataPagamento)
					throws ControladorException{

		try{
			// Obtém as categorias.
			List<Categoria> colecaoCategoriasObterValor = new ArrayList();

			for(DebitoACobrarCategoria debitoACobrarCategoria : (Set<DebitoACobrarCategoria>) debitoACobrar.getDebitoACobrarCategorias()){
				Categoria categoria = new Categoria();
				categoria.setId(debitoACobrarCategoria.getComp_id().getCategoria().getId());
				categoria.setQuantidadeEconomiasCategoria(debitoACobrarCategoria.getQuantidadeEconomia());
				colecaoCategoriasObterValor.add(categoria);
			}

			// Calculando a contribuição de cada categoria nas parcelas normais e na última parcela
			// do débito.
			Map<Integer, BigDecimal[]> mapaIdsCategoriaValores = montarMapaIdsCategoriaValor(debitoACobrar.getValorDebito(), debitoACobrar
							.getNumeroPrestacaoDebito(), colecaoCategoriasObterValor);

			// Formação inicial da chave do evento comercial pela operação, objeto e prazo
			// informados.
			String chaveBase = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil + (objetoContabil != null ? UNDERLINE + objetoContabil : "")
							+ (prazo != null ? UNDERLINE + prazo : "");

			// Registrando lançamentos contábeis por categoria.
			for(Object objeto : debitoACobrar.getDebitoACobrarCategorias()){
				DebitoACobrarCategoria debitoCategoria = (DebitoACobrarCategoria) objeto;
				String chaveEvento = chaveBase;

				if(operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO)
								|| operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO_POSTERIOR)){
					chaveEvento += UNDERLINE + DEBITOACOBRAR;
				}else if(operacaoContabil.equals(OperacaoContabil.CANCELAR_DEBITO_A_COBRAR)
								&& !debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){

					// Ajusta o evento comercial em caso de cancelamento de saldo de parcelamento.
					chaveEvento = EVENTOCOMERCIAL + UNDERLINE + OperacaoContabil.CANCELAR_PARCELAMENTO_SALDO;
				}

				// Caso seja um débito originado de um parcelamento usa o id do parcelamento
				// caso contrário usa o id do próprio débito a cobrar.
				Integer idComplemento = debitoACobrar.getId();
				if(operacaoContabil.equals(OperacaoContabil.PARCELAMENTO)){
					idComplemento = debitoACobrar.getParcelamento().getId();
				}

				if(considerarCurtoLongoPrazo){
					// Monta helper's de lancamentos sintéticos divididos em curto e longo prazo
					// caso haja necessidade.
					Collection<LancamentoContabilSinteticoHelper> lancamentos = montarLancamentoContabilSinteticoHelper(new Integer(
									debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas()), debitoACobrar
									.getLocalidade(), debitoACobrar.getCodigoSetorComercial(), debitoACobrar.getLancamentoItemContabil(),
									idComplemento, chaveEvento, mapaIdsCategoriaValores, debitoCategoria.getComp_id().getCategoria());

					for(LancamentoContabilSinteticoHelper lancamento : lancamentos){
						lancamento.setContaBancaria(contaBancaria);

						if(dataPagamento != null){
							// Definição da Data de Realização do Evento ocorre de acordo com a
							// regra
							// abaixo
							// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme
							// definido
							// na
							// OC1069615 por Luís Eduardo
							definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lancamento);
						}

						inserirLancamentoContabilSintetico(lancamento);
					}
				}else{
					// Monta o helper de lancamento sintético relativo ao débito categoria atual.
					BigDecimal valorCategoria = debitoCategoria.getValorCategoria();

					LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(debitoACobrar.getLocalidade(),
									debitoACobrar.getCodigoSetorComercial(), debitoACobrar.getLancamentoItemContabil(), idComplemento,
									chaveEvento, valorCategoria, debitoCategoria.getComp_id().getCategoria());

					lancamento.setContaBancaria(contaBancaria);

					if(dataPagamento != null){
						// Definição da Data de Realização do Evento ocorre de acordo com a regra
						// abaixo
						// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido
						// na
						// OC1069615 por Luís Eduardo
						definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lancamento);
					}

					inserirLancamentoContabilSintetico(lancamento);
				}
			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * Registra os lançametos de debito a cobrar originados pelo parcelamento de contas.
	 * 
	 * @param debitoACobrar
	 * @param operacaoContabil
	 * @param mapaCategoriaItensContabeisValor
	 * @param colecaoCategoria
	 * @throws ControladorException
	 */
	private void registrarLancamentoContabilDebitoACobrarParcelamentoConta(DebitoACobrar debitoACobrar, OperacaoContabil operacaoContabil,
					ParcelamentoHelper parcelamentoHelper) throws ControladorException{

		try{
			// Formação inicial da chave do evento comercial pela operação, objeto e prazo
			// informados.
			String chaveBase = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;
			Categoria filtro = new Categoria();

			// Registrando lançamentos contábeis por categoria.
			for(Object objeto : debitoACobrar.getDebitoACobrarCategorias()){
				DebitoACobrarCategoria debitoCategoria = (DebitoACobrarCategoria) objeto;

				// Mapa de valores por lançamento item contábil para o debito categoria informado.
				filtro.setId(debitoCategoria.getComp_id().getCategoria().getId());
				Map<Integer, BigDecimal> mapaItensContabeisValor = parcelamentoHelper.getMapaCategoriaItensContabeisValor().get(filtro);

				// Para cada item contábil diferente.
				for(Integer idItemContabil : mapaItensContabeisValor.keySet()){
					String chaveEvento = chaveBase;

					// Complementa a chave do evento comercial.
					if(LancamentoItemContabil.TARIFA_DE_AGUA.equals(idItemContabil)){
						chaveEvento += UNDERLINE + ObjetoContabil.AGUA;
					}else if(LancamentoItemContabil.TARIFA_DE_ESGOTO.equals(idItemContabil)){
						chaveEvento += UNDERLINE + ObjetoContabil.ESGOTO;
					}else{
						chaveEvento += UNDERLINE + ObjetoContabil.FINANCIAMENTO;
					}

					LancamentoItemContabil itemContabil = new LancamentoItemContabil(idItemContabil);

					Map<Integer, BigDecimal[]> mapaIdsCategoriaValores = new HashMap<Integer, BigDecimal[]>();

					BigDecimal numeroTotalPrestacoes = new BigDecimal(debitoACobrar.getNumeroPrestacaoDebito());
					BigDecimal valorParcela = mapaItensContabeisValor.get(idItemContabil).divide(numeroTotalPrestacoes, 2,
									BigDecimal.ROUND_DOWN);
					BigDecimal valorDebitoCalculado = valorParcela.multiply(numeroTotalPrestacoes).setScale(2);
					BigDecimal valorUltimaParcela = valorParcela.add(mapaItensContabeisValor.get(idItemContabil).subtract(
									valorDebitoCalculado).setScale(2));
					mapaIdsCategoriaValores.put(debitoCategoria.getComp_id().getCategoria().getId(),
									new BigDecimal[] {valorParcela, valorUltimaParcela});

					// Monta helper's de lancamentos sintéticos divididos em curto e longo prazo
					// caso haja necessidade.
					Collection<LancamentoContabilSinteticoHelper> lancamentos = montarLancamentoContabilSinteticoHelper(new Integer(
									debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas()), debitoACobrar
									.getLocalidade(), debitoACobrar.getCodigoSetorComercial(), itemContabil, parcelamentoHelper
									.getParcelamento().getId(), chaveEvento, mapaIdsCategoriaValores, debitoCategoria.getComp_id()
									.getCategoria());

					for(LancamentoContabilSinteticoHelper lancamento : lancamentos){
						inserirLancamentoContabilSintetico(lancamento);
					}
				}
			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * @param debitoACobrar
	 * @param operacaoContabil
	 * @throws ControladorException
	 */
	private void registrarLancamentoContabilDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrar, OperacaoContabil operacaoContabil,
					ContaBancaria contaBancaria, Date dataPagamento)
					throws ControladorException{

		try{

			// Registrando lançamentos contábeis por categoria.
			for(Object objeto : debitoACobrar.getDebitoACobrarCategoriasHistorico()){
				DebitoACobrarCategoriaHistorico debitoCategoria = (DebitoACobrarCategoriaHistorico) objeto;
				String chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;

				if(operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO)
								|| operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO_POSTERIOR)
								|| operacaoContabil.equals(OperacaoContabil.ESTORNAR_PAGAMENTO)){
					chaveEvento = chaveEvento + UNDERLINE + DEBITOACOBRAR;
				}

				BigDecimal valorCategoria = debitoCategoria.getValorCategoria();

				LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(debitoACobrar.getLocalidade(),
								debitoACobrar.getCodigoSetorComercial(), debitoACobrar.getLancamentoItemContabil(), debitoACobrar.getId(),
								chaveEvento, valorCategoria, debitoCategoria.getComp_id().getCategoria());

				lancamento.setContaBancaria(contaBancaria);

				// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
				// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
				// OC1069615 por Luís Eduardo
				definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lancamento);

				inserirLancamentoContabilSintetico(lancamento);
			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * @param valor
	 * @param numeroPrestacoes
	 * @param colecaoCategorias
	 * @return
	 */
	private Map<Integer, BigDecimal[]> montarMapaIdsCategoriaValor(BigDecimal valor, short numeroPrestacoes,
					List<Categoria> colecaoCategorias){

		Map<Integer, BigDecimal[]> mapaIdsCategoriaValor = new HashMap<Integer, BigDecimal[]>();

		// Calculando saldo restante total.
		BigDecimal numeroTotalPrestacoes = new BigDecimal(numeroPrestacoes);

		BigDecimal valorParcela = valor.divide(numeroTotalPrestacoes, 2, BigDecimal.ROUND_DOWN);

		BigDecimal valorDebitoCalculado = valorParcela.multiply(numeroTotalPrestacoes).setScale(2);
		BigDecimal valorUltimaParcela = valorParcela.add(valor.subtract(valorDebitoCalculado).setScale(2, BigDecimal.ROUND_DOWN));

		// Ordena as categorias por id.
		Collections.sort(colecaoCategorias);

		// Obter os valores para cada categoria ordenados pelo id da categoria.
		Collection<BigDecimal> colecaoCategoriasValorParcela = getControladorImovel().obterValorPorCategoria(colecaoCategorias,
						valorParcela);
		Collection<BigDecimal> colecaoCategoriasValorUltimaParcela = getControladorImovel().obterValorPorCategoria(colecaoCategorias,
						valorUltimaParcela);

		// Monta mapa com os identificadores das categorias e sua respectiva participação
		// nas parcelas normais e na última parcela do débito.
		Iterator<BigDecimal> iteratorValorParcela = colecaoCategoriasValorParcela.iterator();
		Iterator<BigDecimal> iteratorValorUltimaParcela = colecaoCategoriasValorUltimaParcela.iterator();

		for(Categoria categoria : colecaoCategorias){
			mapaIdsCategoriaValor.put(categoria.getId(), new BigDecimal[] {iteratorValorParcela.next(), iteratorValorUltimaParcela.next()});
		}

		return mapaIdsCategoriaValor;
	}

	/**
	 * Monta a partir dos dados informados os devidos helpers de lançamento sintético e analítico
	 * verificando a necessidade de avaliar curto e longo prazo.
	 * 
	 * @param numeroPrestacoes
	 * @param localidade
	 * @param codigoSetorComercial
	 * @param lancamentoItemContabil
	 * @param codigoObjeto
	 * @param chaveEventoComercial
	 * @param mapaIdsCategoriaValores
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	private Collection<LancamentoContabilSinteticoHelper> montarLancamentoContabilSinteticoHelper(Integer numeroPrestacoes,
					Localidade localidade, Integer codigoSetorComercial, LancamentoItemContabil lancamentoItemContabil,
					Integer codigoObjeto, String chaveEventoComercial, Map<Integer, BigDecimal[]> mapaIdsCategoriaValores,
					Categoria categoria) throws ErroRepositorioException, ControladorException{

		Collection<LancamentoContabilSinteticoHelper> retorno = new ArrayList<LancamentoContabilSinteticoHelper>();

		if(mapaIdsCategoriaValores != null && mapaIdsCategoriaValores.containsKey(categoria.getId())){
			BigDecimal valor = mapaIdsCategoriaValores.get(categoria.getId())[0].multiply(new BigDecimal(numeroPrestacoes - 1)).add(
							mapaIdsCategoriaValores.get(categoria.getId())[1]);

			BigDecimal valorCurtoPrazo = valor;
			BigDecimal valorLongoPrazo = BigDecimal.ZERO.setScale(2);
			String chaveEventoAtualizada = chaveEventoComercial;

			// Caso se é uma operação com prestações e haja mais de 12, calcular valores de curto e
			// longo prazo e complementar chave do evento comercial.
			if(numeroPrestacoes != null && numeroPrestacoes > 0){
				chaveEventoAtualizada += UNDERLINE + Prazo.CURTO_PRAZO;

				if(numeroPrestacoes > 12){
					valorCurtoPrazo = mapaIdsCategoriaValores.get(categoria.getId())[0].multiply(new BigDecimal(12)).setScale(2,
									BigDecimal.ROUND_DOWN);
					valorLongoPrazo = valor.subtract(valorCurtoPrazo);
				}
			}

			// Criação de lancamento contábil padrão ou de curto prazo.
			retorno.add(montarLancamentoContabilSinteticoHelper(localidade, codigoSetorComercial, lancamentoItemContabil, codigoObjeto,
							chaveEventoAtualizada, valorCurtoPrazo, categoria));

			// Criacao de lancamento contábil de longo prazo, caso haja.
			if(numeroPrestacoes != null && numeroPrestacoes > 12){
				retorno.add(montarLancamentoContabilSinteticoHelper(localidade, codigoSetorComercial, lancamentoItemContabil, codigoObjeto,
								chaveEventoComercial + UNDERLINE + Prazo.LONGO_PRAZO, valorLongoPrazo, categoria));
			}
		}

		return retorno;
	}

	/**
	 * Monta a partir dos dados informados os devidos helpers de lançamento sintético e analítico.
	 * 
	 * @param localidade
	 * @param codigoSetorComercial
	 * @param lancamentoItemContabil
	 * @param codigoObjeto
	 * @param chaveEventoComercial
	 * @param valor
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	private LancamentoContabilSinteticoHelper montarLancamentoContabilSinteticoHelper(Localidade localidade, Integer codigoSetorComercial,
					LancamentoItemContabil lancamentoItemContabil, Integer codigoObjeto, String chaveEventoComercial, BigDecimal valor,
					Categoria categoria) throws ErroRepositorioException, ControladorException{

		LancamentoContabilSinteticoHelper lcsh = new LancamentoContabilSinteticoHelper();
		lcsh.setListaLancamentosContabeisAnaliticosHelper(new ArrayList<LancamentoContabilAnaliticoHelper>());

		lcsh.setEventoComercial(repositorioContabil.obterEventoComercial(obterChaveEventoComercial(chaveEventoComercial)));

		lcsh.setIdUnidadeContabilAgrupamento(recuperarIdUnidadeContabilAgrupamento(localidade, codigoSetorComercial));

		lcsh.setCategoria(categoria);

		lcsh.setLancamentoItemContabil(lancamentoItemContabil);

		LancamentoContabilAnaliticoHelper lcah = new LancamentoContabilAnaliticoHelper(valor, codigoObjeto.longValue());

		lcsh.getListaLancamentosContabeisAnaliticosHelper().add(lcah);

		return lcsh;
	}

	private void registrarLancamentoContabilCreditoARealizar(CreditoARealizar creditoARealizar, OperacaoContabil operacaoContabil)
					throws ControladorException{

		try{
			// Obtém as categorias.
			List<Categoria> colecaoCategoriasObterValor = new ArrayList();

			for(Iterator<CreditoARealizarCategoria> i = creditoARealizar.getCreditoARealizarCategoria().iterator(); i.hasNext();){
				CreditoARealizarCategoria creditoARealizarCategoria = (CreditoARealizarCategoria) i.next();
				Categoria categoria = new Categoria();
				categoria.setId(creditoARealizarCategoria.getComp_id().getCategoria().getId());
				categoria.setQuantidadeEconomiasCategoria(creditoARealizarCategoria.getQuantidadeEconomia());
				colecaoCategoriasObterValor.add(categoria);
			}

			// Calculando saldo devedor por categoria.
			Map<Integer, BigDecimal[]> mapaIdsCategoriaValores = montarMapaIdsCategoriaValor(creditoARealizar.getValorCredito(),
							creditoARealizar.getNumeroPrestacaoCredito(), colecaoCategoriasObterValor);

			// Registrando lançamentos contábeis por categoria.
			for(Object objeto : creditoARealizar.getCreditoARealizarCategoria()){
				CreditoARealizarCategoria creditoCategoria = (CreditoARealizarCategoria) objeto;
				String chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;

				Collection<LancamentoContabilSinteticoHelper> lancamentos = montarLancamentoContabilSinteticoHelper(new Integer(
								creditoARealizar.getNumeroPrestacaoCredito() - creditoARealizar.getNumeroPrestacaoRealizada()),
								creditoARealizar.getLocalidade(), creditoARealizar.getCodigoSetorComercial(), creditoARealizar
												.getLancamentoItemContabil(), creditoARealizar.getId(), chaveEvento,
								mapaIdsCategoriaValores, creditoCategoria.getComp_id().getCategoria());

				for(LancamentoContabilSinteticoHelper lancamento : lancamentos){
					inserirLancamentoContabilSintetico(lancamento);
				}
			}

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * Método responsável por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
	 * 
	 * @param lancamentoContabilSintetico
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilAnaliticoConsultaHelper
	 * @throws ErroRepositorioException
	 */
	public Collection<LancamentoContabilAnaliticoConsultaHelper> consultarLancamentoContabilAnaliticoPorSintetico(
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException{

		try{
			return repositorioContabil.consultarLancamentoContabilAnaliticoPorSintetico(lancamentoContabilSintetico);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private void registrarLancamentoContabilGuiaPagamento(Integer idGuiaPagamento, Integer numeroPrestacao,
					OperacaoContabil operacaoContabil, ContaBancaria contaBancaria, Localidade localidade, SetorComercial setorComercial,
					Date dataPagamento, Collection<GuiaPagamentoCategoria> colecaoGuiaPagamentoCategoria)
					throws ControladorException{

		try{
			// Agrupando valores de parcelas da guia de pagamento por categoria e lançamento item
			// contábil.

			// Mapa de totais por categoira e lançamento item contábil.
			Map<String, BigDecimal> mapaIdCategoriaIdItemContabilValorAcumulado = new HashMap<String, BigDecimal>();

			if(colecaoGuiaPagamentoCategoria == null){
				// Pesquisa os registro de GuiaPagamentoCategoria
				FiltroGuiaPagamentoCategoria filtroGuiaPagamentoCategoria = new FiltroGuiaPagamentoCategoria();
				filtroGuiaPagamentoCategoria.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoCategoria.GUIA_PAGAMENTO_ID,
								idGuiaPagamento));
				filtroGuiaPagamentoCategoria.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoCategoria.NUMERO_PRESTACAO,
								numeroPrestacao));

				colecaoGuiaPagamentoCategoria = getControladorUtil().pesquisar(filtroGuiaPagamentoCategoria,
								GuiaPagamentoCategoria.class.getName());
			}

			if(!Util.isVazioOrNulo(colecaoGuiaPagamentoCategoria)){
				GuiaPagamentoCategoriaPK guiaPagamentoCategoriaPK = null;
				Integer categoriaId = null;
				Integer lancamentoItemContabilId = null;
				String chave = null;
				BigDecimal valorCategoria = null;
				BigDecimal valorAcumulado = null;

				for(GuiaPagamentoCategoria guiaPagamentoCategoria : colecaoGuiaPagamentoCategoria){
					guiaPagamentoCategoriaPK = guiaPagamentoCategoria.getComp_id();
					categoriaId = guiaPagamentoCategoriaPK.getCategoriaId();
					lancamentoItemContabilId = guiaPagamentoCategoriaPK.getLancamentoItemContabilId();
					valorCategoria = guiaPagamentoCategoria.getValorCategoria();

					// Montagem de chave baseada no id da categoria e no id do lançamento contábil.
					chave = categoriaId + UNDERLINE + lancamentoItemContabilId;

					if(!mapaIdCategoriaIdItemContabilValorAcumulado.containsKey(chave)){
						mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorCategoria);
					}else{
						valorAcumulado = mapaIdCategoriaIdItemContabilValorAcumulado.get(chave).add(valorCategoria);
						mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorAcumulado);
					}
				}
			}else{
				// Pesquisa os registro de GuiaPagamentoCategoriaHistorico
				FiltroGuiaPagamentoCategoriaHistorico filtroGuiaPagamentoCategoriaHistorico = new FiltroGuiaPagamentoCategoriaHistorico();
				filtroGuiaPagamentoCategoriaHistorico.adicionarParametro(new ParametroSimples(
								FiltroGuiaPagamentoCategoriaHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
				filtroGuiaPagamentoCategoriaHistorico.adicionarParametro(new ParametroSimples(
								FiltroGuiaPagamentoCategoriaHistorico.NUMERO_PRESTACAO, numeroPrestacao));

				Collection<GuiaPagamentoCategoriaHistorico> colecaoGuiaPagamentoCategoriaHistorico = getControladorUtil().pesquisar(
								filtroGuiaPagamentoCategoriaHistorico, GuiaPagamentoCategoriaHistorico.class.getName());

				if(!Util.isVazioOrNulo(colecaoGuiaPagamentoCategoriaHistorico)){
					GuiaPagamentoCategoriaHistoricoPK guiaPagamentoCategoriaHistoricoPK = null;
					Integer categoriaId = null;
					Integer lancamentoItemContabilId = null;
					String chave = null;
					BigDecimal valorCategoria = null;
					BigDecimal valorAcumulado = null;

					for(GuiaPagamentoCategoriaHistorico guiaPagamentoCategoriaHistorico : colecaoGuiaPagamentoCategoriaHistorico){
						guiaPagamentoCategoriaHistoricoPK = guiaPagamentoCategoriaHistorico.getComp_id();
						categoriaId = guiaPagamentoCategoriaHistoricoPK.getCategoriaId();
						lancamentoItemContabilId = guiaPagamentoCategoriaHistoricoPK.getLancamentoItemContabilId();
						valorCategoria = guiaPagamentoCategoriaHistorico.getValorCategoria();

						// Montagem de chave baseada no id da categoria e no id do lançamento
						// contábil.
						chave = categoriaId + UNDERLINE + lancamentoItemContabilId;

						if(!mapaIdCategoriaIdItemContabilValorAcumulado.containsKey(chave)){
							mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorCategoria);
						}else{
							valorAcumulado = mapaIdCategoriaIdItemContabilValorAcumulado.get(chave).add(valorCategoria);
							mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorAcumulado);
						}
					}
				}
			}

			Integer idSetorComercial = null;

			if(setorComercial != null){
				idSetorComercial = setorComercial.getId();
			}

			GuiaPagamento guiaPagamento = (GuiaPagamento) getControladorUtil().pesquisar(idGuiaPagamento, GuiaPagamento.class, true);

			String chaveEvento = "";
			if(operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO)
							|| operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO_POSTERIOR)){

				if(guiaPagamento.getParcelamento() != null && guiaPagamento.getParcelamento().getId() != null){
					chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.PARCELAMENTO;
				}else{
					chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.GUIA_PAGAMENTO;
				}

			}else{
				chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;
			}

			// Para cada item do mapa gera um helper de lançamento analítico e um de sintético
			// correspondente e depois os inserer fazendo acumulações caso necessário.
			for(String idCategoriaIdItemContabil : mapaIdCategoriaIdItemContabilValorAcumulado.keySet()){
				String[] chaveIdCategoriaIdItemContabil = idCategoriaIdItemContabil.split(UNDERLINE);

				Categoria categoria = new Categoria(Integer.parseInt(chaveIdCategoriaIdItemContabil[0]));
				LancamentoItemContabil itemContabil = new LancamentoItemContabil(Integer.parseInt(chaveIdCategoriaIdItemContabil[1]));

				LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(localidade, idSetorComercial,
								itemContabil, idGuiaPagamento, chaveEvento, mapaIdCategoriaIdItemContabilValorAcumulado
												.get(idCategoriaIdItemContabil), categoria);
				lancamento.setContaBancaria(contaBancaria);

				if(dataPagamento != null){
					// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
					// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
					// OC1069615 por Luís Eduardo
					definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lancamento);
				}

				inserirLancamentoContabilSintetico(lancamento);
			}

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	private void registrarLancamentoContabilGuiaPagamentoHistorico(Integer idGuiaPagamento, Integer numeroPrestacao,
					OperacaoContabil operacaoContabil, ContaBancaria contaBancaria, Localidade localidade, SetorComercial setorComercial,
					Date dataPagamento)
					throws ControladorException{

		try{
			// Agrupando valores de parcelas da guia de pagamento por categoria e lançamento item
			// contábil.

			// Mapa de totais por categoira e lançamento item contábil.
			Map<String, BigDecimal> mapaIdCategoriaIdItemContabilValorAcumulado = new HashMap<String, BigDecimal>();

			// Pesquisa os registro de GuiaPagamentoCategoria
			FiltroGuiaPagamentoCategoria filtroGuiaPagamentoCategoria = new FiltroGuiaPagamentoCategoria();
			filtroGuiaPagamentoCategoria.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoCategoria.GUIA_PAGAMENTO_ID,
							idGuiaPagamento));
			filtroGuiaPagamentoCategoria.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoCategoria.NUMERO_PRESTACAO,
							numeroPrestacao));

			Collection<GuiaPagamentoCategoria> colecaoGuiaPagamentoCategoria = getControladorUtil().pesquisar(filtroGuiaPagamentoCategoria,
							GuiaPagamentoCategoria.class.getName());

			if(!Util.isVazioOrNulo(colecaoGuiaPagamentoCategoria)){
				GuiaPagamentoCategoriaPK guiaPagamentoCategoriaPK = null;
				Integer categoriaId = null;
				Integer lancamentoItemContabilId = null;
				String chave = null;
				BigDecimal valorCategoria = null;
				BigDecimal valorAcumulado = null;

				for(GuiaPagamentoCategoria guiaPagamentoCategoria : colecaoGuiaPagamentoCategoria){
					guiaPagamentoCategoriaPK = guiaPagamentoCategoria.getComp_id();
					categoriaId = guiaPagamentoCategoriaPK.getCategoriaId();
					lancamentoItemContabilId = guiaPagamentoCategoriaPK.getLancamentoItemContabilId();
					valorCategoria = guiaPagamentoCategoria.getValorCategoria();

					// Montagem de chave baseada no id da categoria e no id do lançamento contábil.
					chave = categoriaId + UNDERLINE + lancamentoItemContabilId;

					if(!mapaIdCategoriaIdItemContabilValorAcumulado.containsKey(chave)){
						mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorCategoria);
					}else{
						valorAcumulado = mapaIdCategoriaIdItemContabilValorAcumulado.get(chave).add(valorCategoria);
						mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorAcumulado);
					}
				}
			}else{
				// Pesquisa os registro de GuiaPagamentoCategoriaHistorico
				FiltroGuiaPagamentoCategoriaHistorico filtroGuiaPagamentoCategoriaHistorico = new FiltroGuiaPagamentoCategoriaHistorico();
				filtroGuiaPagamentoCategoriaHistorico.adicionarParametro(new ParametroSimples(
								FiltroGuiaPagamentoCategoriaHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
				filtroGuiaPagamentoCategoriaHistorico.adicionarParametro(new ParametroSimples(
								FiltroGuiaPagamentoCategoriaHistorico.NUMERO_PRESTACAO, numeroPrestacao));

				Collection<GuiaPagamentoCategoriaHistorico> colecaoGuiaPagamentoCategoriaHistorico = getControladorUtil().pesquisar(
								filtroGuiaPagamentoCategoriaHistorico, GuiaPagamentoCategoriaHistorico.class.getName());

				if(!Util.isVazioOrNulo(colecaoGuiaPagamentoCategoriaHistorico)){
					GuiaPagamentoCategoriaHistoricoPK guiaPagamentoCategoriaHistoricoPK = null;
					Integer categoriaId = null;
					Integer lancamentoItemContabilId = null;
					String chave = null;
					BigDecimal valorCategoria = null;
					BigDecimal valorAcumulado = null;

					for(GuiaPagamentoCategoriaHistorico guiaPagamentoCategoriaHistorico : colecaoGuiaPagamentoCategoriaHistorico){
						guiaPagamentoCategoriaHistoricoPK = guiaPagamentoCategoriaHistorico.getComp_id();
						categoriaId = guiaPagamentoCategoriaHistoricoPK.getCategoriaId();
						lancamentoItemContabilId = guiaPagamentoCategoriaHistoricoPK.getLancamentoItemContabilId();
						valorCategoria = guiaPagamentoCategoriaHistorico.getValorCategoria();

						// Montagem de chave baseada no id da categoria e no id do lançamento
						// contábil.
						chave = categoriaId + UNDERLINE + lancamentoItemContabilId;

						if(!mapaIdCategoriaIdItemContabilValorAcumulado.containsKey(chave)){
							mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorCategoria);
						}else{
							valorAcumulado = mapaIdCategoriaIdItemContabilValorAcumulado.get(chave).add(valorCategoria);
							mapaIdCategoriaIdItemContabilValorAcumulado.put(chave, valorAcumulado);
						}
					}
				}
			}

			Integer idSetorComercial = null;

			if(setorComercial != null){
				idSetorComercial = setorComercial.getId();
			}

			GuiaPagamentoHistorico guiaPagamentoHistorico = (GuiaPagamentoHistorico) getControladorUtil().pesquisar(idGuiaPagamento,
							GuiaPagamentoHistorico.class, true);

			// Para cada item do mapa gera um helper de lançamento analítico e um de sintético
			// correspondente e depois os inserer fazendo acumulações caso necessário.
			for(String idCategoriaIdItemContabil : mapaIdCategoriaIdItemContabilValorAcumulado.keySet()){
				String[] chaveIdCategoriaIdItemContabil = idCategoriaIdItemContabil.split(UNDERLINE);
				String chaveEvento = "";
				if(operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO)
								|| operacaoContabil.equals(OperacaoContabil.INCLUIR_PAGAMENTO_POSTERIOR)
								|| operacaoContabil.equals(OperacaoContabil.ESTORNAR_PAGAMENTO)){

					if(guiaPagamentoHistorico.getParcelamento() != null && guiaPagamentoHistorico.getParcelamento().getId() != null){
						chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.PARCELAMENTO;
					}else{
						chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil + UNDERLINE + ObjetoContabil.GUIA_PAGAMENTO;
					}
				}else{
					chaveEvento = EVENTOCOMERCIAL + UNDERLINE + operacaoContabil;
				}

				Categoria categoria = new Categoria(Integer.parseInt(chaveIdCategoriaIdItemContabil[0]));
				LancamentoItemContabil itemContabil = new LancamentoItemContabil(Integer.parseInt(chaveIdCategoriaIdItemContabil[1]));

				LancamentoContabilSinteticoHelper lancamento = montarLancamentoContabilSinteticoHelper(localidade, idSetorComercial,
								itemContabil, idGuiaPagamento, chaveEvento, mapaIdCategoriaIdItemContabilValorAcumulado
												.get(idCategoriaIdItemContabil), categoria);
				lancamento.setContaBancaria(contaBancaria);

				// Definição da Data de Realização do Evento ocorre de acordo com a regra abaixo
				// no caso de Arrecadação (Pagamento/Pagamento Histórico), conforme definido na
				// OC1069615 por Luís Eduardo
				definirDataRealizacaoEventoParaArrecadacao(dataPagamento, lancamento);

				inserirLancamentoContabilSintetico(lancamento);
			}

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	private void registrarLancamentoContabilGuiaPagamento(GuiaPagamento guiaPagamento, OperacaoContabil operacaoContabil,
					ContaBancaria contaBancaria, Collection<GuiaPagamentoCategoria> colGuiaPagamentoCategoria) throws ControladorException{

		Collection<Short> prestacoesAProcessar = new ArrayList<Short>();

		GuiaPagamentoPrestacaoPK guiaPagamentoPrestacaoPK = null;
		Short numeroPrestacao = null;

		for(GuiaPagamentoPrestacao prestacao : (Set<GuiaPagamentoPrestacao>) guiaPagamento.getGuiasPagamentoPrestacao()){
			guiaPagamentoPrestacaoPK = prestacao.getComp_id();
			numeroPrestacao = guiaPagamentoPrestacaoPK.getNumeroPrestacao();

			if(!prestacoesAProcessar.contains(numeroPrestacao)){
				prestacoesAProcessar.add(numeroPrestacao);
			}
		}

		Integer idGuiaPagamento = guiaPagamento.getId();
		Localidade localidade = guiaPagamento.getLocalidade();
		SetorComercial setorComercial = guiaPagamento.getSetorComercial();

		for(Short numeroPrestacaoAux : prestacoesAProcessar){
			this.registrarLancamentoContabilGuiaPagamento(idGuiaPagamento, numeroPrestacaoAux.intValue(), operacaoContabil, contaBancaria,
							localidade, setorComercial, null, colGuiaPagamentoCategoria);
		}

	}

	private void registrarLancamentoContabilParcelamento(ParcelamentoHelper parcelamentoHelper, OperacaoContabil operacaoContabil)
					throws ControladorException{

		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.PARCELAMENTO_ID, parcelamentoHelper
						.getParcelamento().getId()));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
						DebitoCreditoSituacao.NORMAL));

		Collection<DebitoACobrar> colecaoDebitoACobrarParcelamento = getControladorUtil().pesquisar(filtroDebitoACobrar,
						DebitoACobrar.class.getName());

		try{
			DebitoACobrar parcelamentoDebitos = null;
			DebitoACobrar reparcelamento = null;

			for(DebitoACobrar debitoACobrar : colecaoDebitoACobrarParcelamento){
				repositorioContabil.carregarAtributosDebitoACobrar(debitoACobrar);

				if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE)
								|| debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

					// O registro de juros e acréscimos não é dividido em curto e longo prazo
					// (considerarCurtoLongoPrazo = false).
					registrarLancamentoContabilDebitoACobrar(debitoACobrar, true, operacaoContabil, ObjetoContabil.JUROS_CORRECAO, null,
									null);

				}

				else if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.PARCELAMENTO_CONTAS)){

					registrarLancamentoContabilDebitoACobrarParcelamentoConta(debitoACobrar, operacaoContabil, parcelamentoHelper);

				}

				else if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.PARCELAMENTO_GUIAS_PAGAMENTO)){

					registrarLancamentoContabilDebitoACobrar(debitoACobrar, true, operacaoContabil, ObjetoContabil.FINANCIAMENTO, null,
									null);

				}

				else if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.PARCELAMENTO_DEBITO_A_COBRAR_CURTO_PRAZO)
								|| debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.PARCELAMENTO_DEBITO_A_COBRAR_LONGO_PRAZO)){

					// Totaliza valores de curto e longo prazo dos débitos originais.
					if(parcelamentoDebitos == null){
						parcelamentoDebitos = debitoACobrar;
					}else{
						parcelamentoDebitos.setValorDebito(parcelamentoDebitos.getValorDebito().add(debitoACobrar.getValorDebito()));
					}

				}

				else if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.REPARCELAMENTOS_CURTO_PRAZO)
								|| debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.REPARCELAMENTOS_LONGO_PRAZO)){

					// Totaliza valores de curto e longo prazo dos reparcelamentos originais.
					if(reparcelamento == null){
						reparcelamento = debitoACobrar;
					}else{
						reparcelamento.setValorDebito(reparcelamento.getValorDebito().add(debitoACobrar.getValorDebito()));
					}

				}
			}

			if(parcelamentoDebitos != null){
				registrarLancamentoContabilDebitoACobrar(parcelamentoDebitos, true, operacaoContabil, ObjetoContabil.FINANCIAMENTO, null,
								null);
			}

			if(reparcelamento != null){
				registrarLancamentoContabilDebitoACobrar(reparcelamento, true, operacaoContabil, ObjetoContabil.PARCELAMENTO, null, null);
			}

		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * [UCXXXX] Provisão de devedores duvidosos
	 * 
	 * @autor Genival Barbosa
	 * @date 06/12/2011
	 * @param colecao
	 * @throws ControladorException
	 */
	public void provisaoDevedoresDuvidosos(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			Collection<Conta> colecaoContas = repositorioFaturamento.consultarContasProvisaoDevedoresDuvidosos();

			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
			String anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao() + "";

			Map<Integer, EventoComercial> mapaEventoComercial = carregarMapaEventoComercialPDD();

			for(Conta conta : colecaoContas){

				Collection<ContaCategoria> colecaoContaCategoria = repositorioFaturamento.pesquisarContaCategoria(conta.getId());
				if(colecaoContaCategoria.size() > 0){
					String contaMista = "";
					if(colecaoContaCategoria.size() > 1){
						contaMista = "_MISTO";
					}

					String pddvFaixa = "";

					pddvFaixa = this.obterFaixaPDD(conta, null);

					if(!pddvFaixa.equals("")){
						ContaGeral contaGeral = new ContaGeral();
						contaGeral.setId(conta.getId());

						EventoComercial eventoComercial = mapaEventoComercial.get(obterChaveEventoComercial(EVENTOCOMERCIAL + UNDERLINE
										+ pddvFaixa + contaMista));

						ProvisaoDevedoresDuvidosos pddv = new ProvisaoDevedoresDuvidosos();
						if(contaMista.equals("")) pddv.setCategoria(colecaoContaCategoria.iterator().next().getComp_id().getCategoria());

						pddv.setEventoComercial(eventoComercial);
						pddv.setContaGeral(contaGeral);
						pddv.setReferenciaContabil(Integer.valueOf(anoMesArrecadacao));

						// Deve retornar ao modelo anterior e pegar a data do anoMesArrecadação em
						// Sistema parâmetro fim emergêncial para geração do relatório
						// pddv.setReferenciaContabil(201211);

						if(conta.getValorAgua() == null){
							pddv.setValorAgua(BigDecimal.ZERO);
						}else{
							pddv.setValorAgua(conta.getValorAgua());
						}
						if(conta.getValorEsgoto() == null){
							pddv.setValorEsgoto(BigDecimal.ZERO);
						}else{
							pddv.setValorEsgoto(conta.getValorEsgoto());
						}

						pddv.setValorDebitos(conta.getDebitos());
						pddv.setValorCreditos(conta.getValorCreditos());

						// -------- Calcular e setar Valores Multa, Juros e Correcao ----------
						BigDecimal valorMultasCobradas = null;
						valorMultasCobradas = repositorioFaturamento.pesquisarValorMultasCobradas(conta.getId());

						Collection dadosPagamento = null;
						if(conta.getIndicadorPagamento() != null && conta.getIndicadorPagamento() == (short) 1){

							dadosPagamento = repositorioCobranca.pesquisarValorTotalPagamentoMenorDataPagamento(conta.getId());

						}
						Date menorDataPagamento = null;
						if(dadosPagamento != null && !dadosPagamento.isEmpty()){

							Object[] dadosPagamentoArray = (Object[]) dadosPagamento.iterator().next();

							if(dadosPagamentoArray[1] != null){
								menorDataPagamento = (Date) dadosPagamentoArray[1];
							}
						}

						CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidade = null;
						calcularAcrescimoPorImpontualidade = getControladorCobranca().calcularAcrescimoPorImpontualidadeBancoDeDados(
										conta.getReferencia(), conta.getDataVencimentoConta(), menorDataPagamento, conta.getValorTotal(),
										valorMultasCobradas, conta.getIndicadorCobrancaMulta(), anoMesArrecadacao, conta.getId(), null,
										null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

						if(calcularAcrescimoPorImpontualidade != null){

							pddv.setValorMulta(calcularAcrescimoPorImpontualidade.getValorMulta());
							pddv.setValorjuros(calcularAcrescimoPorImpontualidade.getValorJurosMora());
							pddv.setValorCorrecao(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria());

						}
						// -------------------------------------------------------------

						pddv.setIndicadorUso(ConstantesSistema.SIM);
						pddv.setUltimaAlteracao(new Date());

						getControladorUtil().inserir(pddv);

						// Atualiza o indicador de PDD da conta
						conta.setIndicadorPDD(ConstantesSistema.SIM);

						this.getControladorUtil().atualizar(conta);

						this.registrarLancamentoContabil(pddv, OperacaoContabil.PROVISAO_DEVEDORES_DUVIDOSOS, null);

					}

				}

			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

		}catch(Exception ex){
			getControladorBatch().encerrarUnidadeProcessamentoBatch(ex, idUnidadeIniciada, true);
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private String obterFaixaPDD(Object objetoConta, Integer referencia){

		Date dataMin6meses = Util.adicionarNumeroMesDeUmaData(new Date(), -6);
		Date dataMin1ano = Util.adicionarNumeroMesDeUmaData(new Date(), -12);
		if(referencia != null){
			dataMin6meses = Util.adicionarNumeroMesDeUmaData(Util.gerarDataApartirAnoMesRefencia(referencia), -6);
			dataMin1ano = Util.adicionarNumeroMesDeUmaData(Util.gerarDataApartirAnoMesRefencia(referencia), -12);
		}

		String retorno = "";

		if(objetoConta instanceof Conta){
			Conta conta = (Conta) objetoConta;

			if(conta.getValorTotal().compareTo(new BigDecimal(5000)) <= 0 && conta.getDataVencimentoConta().compareTo(dataMin6meses) < 0){
				// Faixa1 (ate R$5.000,00 vencidos a mais de 6 meses)
				retorno = "PDD_FAIXA1";

			}else if(conta.getValorTotal().compareTo(new BigDecimal(5000)) > 0
							&& conta.getValorTotal().compareTo(new BigDecimal(30000)) <= 0
							&& conta.getDataVencimentoConta().compareTo(dataMin1ano) < 0){
				// Faixa2 (acima de R$5.000,00 ate R$30.000,00 e vencidos a mais de 1 ano)
				retorno = "PDD_FAIXA2";

			}else if(conta.getValorTotal().compareTo(new BigDecimal(30000)) > 0
							&& conta.getDataVencimentoConta().compareTo(dataMin1ano) < 0){
				// Faixa3 (acima de R$30.000,00 e vencidos a mais de 1 ano)
				retorno = "PDD_FAIXA3";

			}
		}else{
			ContaHistorico conta = (ContaHistorico) objetoConta;

			if(conta.getValorTotal().compareTo(new BigDecimal(5000)) <= 0 && conta.getDataVencimentoConta().compareTo(dataMin6meses) < 0){
				// Faixa1 (ate R$5.000,00 vencidos a mais de 6 meses)
				retorno = "PDD_FAIXA1";

			}else if(conta.getValorTotal().compareTo(new BigDecimal(5000)) > 0
							&& conta.getValorTotal().compareTo(new BigDecimal(30000)) <= 0
							&& conta.getDataVencimentoConta().compareTo(dataMin1ano) < 0){
				// Faixa2 (acima de R$5.000,00 ate R$30.000,00 e vencidos a mais de 1 ano)
				retorno = "PDD_FAIXA2";

			}else if(conta.getValorTotal().compareTo(new BigDecimal(30000)) > 0
							&& conta.getDataVencimentoConta().compareTo(dataMin1ano) < 0){
				// Faixa3 (acima de R$30.000,00 e vencidos a mais de 1 ano)
				retorno = "PDD_FAIXA3";

			}
		}

		return retorno;
	}

	public Map<Integer, EventoComercial> carregarMapaEventoComercialPDD() throws ErroRepositorioException{

		Map<Integer, EventoComercial> retorno = new HashMap<Integer, EventoComercial>();

		for(int i = 1; i <= repositorioContabil.obterTotalEventoComercial(); i++){
			EventoComercial eventoComercial = repositorioContabil.obterEventoComercial(i);
			retorno.put(eventoComercial.getId(), eventoComercial);
		}

		return retorno;
	}

	/**
	 * @param collOperacaoContabilHelper
	 * @throws ControladorException
	 */

	public void registrarLancamentoContabil(Collection collOperacaoContabilHelper) throws ControladorException{

		Iterator it = collOperacaoContabilHelper.iterator();

		while(it.hasNext()){
			OperacaoContabilHelper operacaoContabilHelper = (OperacaoContabilHelper) it.next();

			try{

				System.out.println(operacaoContabilHelper.getOperacaoContabil().name());

				registrarLancamentoContabil(operacaoContabilHelper.getObjetoOrigem(), operacaoContabilHelper.getOperacaoContabil(), null);
			}catch(ControladorException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

		}

	}

	/**
	 * Atualizar registro de provisão de devedores duvidosos e realizar o lançamento contábil do
	 * mesmo
	 * 
	 * @author Hugo Lima
	 * @date 27/06/2012
	 * @param conta
	 * @param sistemaParametro
	 * @param idProvisaoDevedoresDuvidososMotivoBaixa
	 * @throws ControladorException
	 */
	public void atualizarProvisaoDevedoresDuvidososLancamentoContabil(Object objetoConta, SistemaParametro sistemaParametro,
					Integer idProvisaoDevedoresDuvidososMotivoBaixa) throws ControladorException{

		Integer idConta = null;
		String pddvFaixa = "";
		Map<Integer, EventoComercial> mapaEventoComercial = null;

		try{

			if(objetoConta instanceof Conta){
				idConta = ((Conta) objetoConta).getId();
			}else{
				idConta = ((ContaHistorico) objetoConta).getId();
			}

			ProvisaoDevedoresDuvidosos provisaoDevedoresDuvidosos = this.repositorioContabil
							.obterProvisaoDevedoresDuvidososMaisRecente(idConta);

			if(provisaoDevedoresDuvidosos != null){
				pddvFaixa = this.obterFaixaPDD(objetoConta, provisaoDevedoresDuvidosos.getReferenciaContabil());
			}

			if(!pddvFaixa.equals("") && provisaoDevedoresDuvidosos != null){

				mapaEventoComercial = carregarMapaEventoComercialPDD();

				EventoComercial eventoComercialBaixaContaPDD = mapaEventoComercial.get(obterChaveEventoComercial(EVENTOCOMERCIAL
								+ UNDERLINE + pddvFaixa + UNDERLINE + ConstantesSistema.TOKEN_RETORNO_EVENTO_COMERCIAL_BAIXA));

				if(sistemaParametro == null){
					sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
				}

				// Atualizar os campos de PDD
				provisaoDevedoresDuvidosos.setProvisaoDevedoresDuvidososMotivoBaixa(new ProvisaoDevedoresDuvidososMotivoBaixa(
								idProvisaoDevedoresDuvidososMotivoBaixa));
				provisaoDevedoresDuvidosos.setDataBaixa(new Date());
				provisaoDevedoresDuvidosos.setReferenciaBaixa(sistemaParametro.getAnoMesArrecadacao());
				provisaoDevedoresDuvidosos.setEventoComercialBaixaContaPDD(eventoComercialBaixaContaPDD);
				provisaoDevedoresDuvidosos.setUltimaAlteracao(new Date());

				this.getControladorUtil().atualizar(provisaoDevedoresDuvidosos);

				// Realizar o lançamento contábil
				this.registrarLancamentoContabil(provisaoDevedoresDuvidosos, OperacaoContabil.BAIXA_PROVISAO_DEVEDORES_DUVIDOSOS, null);

				// Atualizar o indicador de PDD da Conta (com persistencia na base)
				if(objetoConta instanceof Conta){
					((Conta) objetoConta).setIndicadorPDD(ConstantesSistema.NAO);
					this.repositorioFaturamento.atualizarIndicadorPDDDeConta(((Conta) objetoConta).getId(), ((Conta) objetoConta)
									.getIndicadorPDD(), false);
				}else{
					this.repositorioFaturamento.atualizarIndicadorPDDDeConta(((ContaHistorico) objetoConta).getId(),
									((ContaHistorico) objetoConta).getIndicadorPDD(), true);
				}

			}

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @param conta
	 * @return
	 * @throws ErroRepositorioException
	 */
	private Short obterIdentificadorContaPDD(Object objetoConta) throws ErroRepositorioException{

		Short indicadorContaPDD = null;

		if(objetoConta instanceof Conta){
			Conta conta = (Conta) objetoConta;

			if(conta.getIndicadorPDD() != null){
				indicadorContaPDD = conta.getIndicadorPDD();
			}else{
				Conta contaTemp = this.repositorioArrecadacao.pesquisarConta(conta.getId());
				indicadorContaPDD = contaTemp.getIndicadorPDD();
			}

		}else{
			ContaHistorico conta = (ContaHistorico) objetoConta;

			if(conta.getIndicadorPDD() != null){
				indicadorContaPDD = conta.getIndicadorPDD();
			}else{
				ContaHistorico contaTemp = this.repositorioArrecadacao.pesquisarContaHistorico(conta.getId());
				indicadorContaPDD = contaTemp.getIndicadorPDD();
			}
		}

		return indicadorContaPDD;
	}

	public void ajustarContabilidadeArrecadacaoDeso(Integer limite) throws ControladorException{

		try{
			AjusteContabilidadeDeso ajusteContabilidadeDeso = new AjusteContabilidadeDeso();
			ajusteContabilidadeDeso.executarAjustarContabilidadeArrecadacao(limite);
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(e);
		}
	}

	public void ajustarRegistrosContaEGuiaDeso() throws ControladorException{

		try{
			AjusteContabilidadeDeso ajusteContabilidadeDeso = new AjusteContabilidadeDeso();
			ajusteContabilidadeDeso.executarAjustarRegistrosContaEGuiaDeso();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(e);
		}
	}

	public void desfazerPreFaturamentoPorGrupoERef() throws ControladorException{

		try{
			AjusteContabilidadeDeso ajusteContabilidadeDeso = new AjusteContabilidadeDeso();
			ajusteContabilidadeDeso.desfazerPreFaturamentoPorGrupoERef();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(e);
		}
	}

	// public String obterCodigoContaAuxiliarOrigemCredito(LancamentoContabilSintetico
	// lancamentoContabilSintetico)
	// throws ControladorException{
	//
	// // Caso o grupo de conta auxiliar da conta contábil for 003 (CNCT_NNGRUPOCONTAAUXILIAR da
	// // tabela CONTA_CONTABIL com CNCT_ID = CNCT_ID_CREDITO da tabela EVENTO_COMERCIAL_LANCAMENTO
	// // com (EVCO_ID = EVCO_ID, CATG_ID = CATG_ID, LICT_ID = LICT_ID , IMTP_ID = IMTP_ID) da
	// // tabela LANCAMENTO_CONTABIL_SINTETICO)
	//
	// // então atribuir o código do banco (BNCO_ID da tabela
	// // AGENCIA com AGEN_ID = AGEN_ID da tabela CONTA_BANCARIA com CTCB_ID = CTBC_ID da tabela
	// // LANCAMENTO_CONTABIL_SINTETICO) senão atribuir a unidade de contabilização (UNCO_ID da
	// // tabela LANCAMENTO_CONTABIL_SINTETICO)
	//
	// String retorno = null;
	//
	// try{
	//
	// String numGrupoContaAuxiliar =
	// repositorioContabil.obterNumeroContaAuiliar(ObjetoContabil.CREDITO,
	// lancamentoContabilSintetico);
	//
	// if(numGrupoContaAuxiliar != null){
	//
	// if(numGrupoContaAuxiliar.equals("003")){
	//
	// retorno = numGrupoContaAuxiliar;
	//
	// }else{
	//
	// retorno = this.obterCodigoBanco(lancamentoContabilSintetico);
	//
	// }
	//
	// }else{
	//
	// retorno = this.obterCodigoBanco(lancamentoContabilSintetico);
	//
	// }
	//
	// }catch(ErroRepositorioException ex){
	//
	// throw new ControladorException("erro.sistema", ex);
	//
	// }
	//
	// return retorno;
	//
	// }

	// /**
	// * Método usado para obter O Evento Comercial Lancamento
	// *
	// * @param lancamentoContabilSintetico
	// * @return
	// * @throws ControladorException
	// */
	// public String obterCodigoContaAuxiliarOrigemDebito(LancamentoContabilSintetico
	// lancamentoContabilSintetico)
	// throws ControladorException{
	//
	// // COD_CNTAUX_ORIGEM
	//
	// // Caso o grupo de conta auxiliar da conta contábil for 003 (CNCT_NNGRUPOCONTAAUXILIAR da
	// // tabela CONTA_CONTABIL com CNCT_ID = CNCT_ID_DEBITO da tabela EVENTO_COMERCIAL_LANCAMENTO
	// // com (EVCO_ID = EVCO_ID, CATG_ID = CATG_ID, LICT_ID = LICT_ID , IMTP_ID = IMTP_ID) da
	// // tabela LANCAMENTO_CONTABIL_SINTETICO)
	//
	// // então atribuir o código do banco (BNCO_ID da tabela
	// // AGENCIA com AGEN_ID = AGEN_ID da tabela CONTA_BANCARIA com CTCB_ID = CTBC_ID da tabela
	// // LANCAMENTO_CONTABIL_SINTETICO) senão atribuir a unidade de contabilização (UNCO_ID da
	// // tabela LANCAMENTO_CONTABIL_SINTETICO)
	//
	// String retorno = null;
	//
	// try{
	//
	// String numGrupoContaAuxiliar =
	// repositorioContabil.obterNumeroContaAuiliar(ObjetoContabil.DEBITO,
	// lancamentoContabilSintetico);
	//
	// if(numGrupoContaAuxiliar != null){
	//
	// if(numGrupoContaAuxiliar.equals("003")){
	//
	// retorno = numGrupoContaAuxiliar;
	//
	// }else{
	//
	// retorno = this.obterCodigoBanco(lancamentoContabilSintetico);
	//
	// }
	//
	// }else{
	//
	// retorno = this.obterCodigoBanco(lancamentoContabilSintetico);
	//
	// }
	//
	//
	// }catch(ErroRepositorioException ex){
	//
	// throw new ControladorException("erro.sistema", ex);
	//
	// }
	//
	// return retorno;
	//
	// }

	// private String obterCodigoBanco(LancamentoContabilSintetico lancamentoContabilSintetico)
	// throws ControladorException{
	//
	// String retorno = null;
	//
	// try{
	//
	// ContaBancaria contaBancaria =
	// repositorioContabil.obterContaBancaria(lancamentoContabilSintetico);
	//
	// if(contaBancaria != null){
	//
	// retorno =
	// Util.completarStringZeroEsquerda(String.valueOf(contaBancaria.getAgencia().getBanco().getId()),
	// 3);
	//
	// }else{
	//
	// retorno =
	// Util.completarStringZeroEsquerda(String.valueOf(lancamentoContabilSintetico.getIdUnidadeContabilAgrupamento()),
	// 3);
	//
	// }
	//
	// }catch(ErroRepositorioException ex){
	//
	// throw new ControladorException("erro.sistema", ex);
	//
	// }
	//
	// return retorno;
	//
	// }

	public String obterCodigoGrupoContaAuxiliarOrigemCredito(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException{

		// Caso o grupo de conta auxiliar da conta contábil for 003 (CNCT_NNGRUPOCONTAAUXILIAR da
		// tabela CONTA_CONTABIL com CNCT_ID = CNCT_ID_CREDITO da tabela EVENTO_COMERCIAL_LANCAMENTO
		// com (EVCO_ID = EVCO_ID, CATG_ID = CATG_ID, LICT_ID = LICT_ID , IMTP_ID = IMTP_ID) da
		// tabela LANCAMENTO_CONTABIL_SINTETICO
		// então atribuir a constante 003 senão atribuir a
		// constante 004

		String numGrupoContaAuxiliar = null;

		try{

			numGrupoContaAuxiliar = repositorioContabil.obterNumeroContaAuiliar(ObjetoContabil.CREDITO,
							lancamentoContabilSintetico);


		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);

		}

		if(numGrupoContaAuxiliar != null){
			return numGrupoContaAuxiliar.trim();
		}else{
			return numGrupoContaAuxiliar;
		}



	}

	/**
	 * Método usado para obter O Evento Comercial Lancamento
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ControladorException
	 */
	public String obterCodigoGrupoContaAuxiliarOrigemDebito(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException{

		// COD_GRP_CNTAUX_ORI

		// Caso o grupo de conta auxiliar da conta contábil for 003 (CNCT_NNGRUPOCONTAAUXILIAR da
		// tabela CONTA_CONTABIL com CNCT_ID = CNCT_ID_DEBITO da tabela EVENTO_COMERCIAL_LANCAMENTO
		// com (EVCO_ID = EVCO_ID, CATG_ID = CATG_ID, LICT_ID = LICT_ID , IMTP_ID = IMTP_ID) da
		// tabela LANCAMENTO_CONTABIL_SINTETICO)
		// então Atribuir a constante 003 senão atribuir a
		// constante 004

		String numGrupoContaAuxiliar = null;

		try{

			numGrupoContaAuxiliar = repositorioContabil.obterNumeroContaAuiliar(ObjetoContabil.DEBITO,
							lancamentoContabilSintetico);



		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);

		}

		return numGrupoContaAuxiliar;

	}

}
