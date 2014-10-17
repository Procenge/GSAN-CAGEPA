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

package gcom.gerencial.cobranca;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaSituacaoMotivo;
import gcom.cobranca.CobrancaSituacaoTipo;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.ResumoCobrancaAcao;
import gcom.cobranca.ResumoCobrancaSituacaoEspecial;
import gcom.cobranca.ResumoPendencia;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.gerencial.bean.CobrancaAcaoHelper;
import gcom.gerencial.bean.CobrancaAcaoPerfilHelper;
import gcom.gerencial.bean.CobrancaAcaoSituacaoHelper;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.cadastro.IRepositorioGerencialCadastro;
import gcom.gerencial.cadastro.RepositorioGerencialCadastroHBM;
import gcom.gerencial.cobranca.bean.ResumoCobrancaSituacaoEspecialHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaAcumuladoHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaContasGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaContasRegiaoHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaCreditoARealizarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaDebitosACobrarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaGuiasPagamentoGerenciaHelper;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ErroRepositorioException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.hql.ast.QueryTranslatorImpl;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public class ControladorGerencialCobrancaSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioGerencialCobranca repositorioGerencialCobranca = null;

	private IRepositorioGerencialCadastro repositorioGerencialCadastro = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioGerencialCobranca = RepositorioGerencialCobrancaHBM.getInstancia();
		repositorioGerencialCadastro = RepositorioGerencialCadastroHBM.getInstancia();
	}

	/**
	 * Retorna a interface remota de ControladorImovel
	 * 
	 * @return A interface remota do controlador de parâmetro
	 */
	private ControladorImovelLocal getControladorImovel(){

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

	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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

	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
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
	 * Retorna a interface remota de ControladorCadastro
	 * 
	 * @return A interface remota do controlador de parâmetro
	 */
	private ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
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
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
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
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Método que gera o resumo Resumo Situacao Especial Faturamento
	 * [UC0341]
	 * 
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 */
	public void gerarResumoSituacaoEspecialCobranca(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, (idLocalidade));

		try{

			this.repositorioGerencialCobranca.excluirTodosResumoCobrancaSituacaoEspecial(idLocalidade);

			List<ResumoCobrancaSituacaoEspecialHelper> listaSimplificada = new ArrayList();
			List<ResumoCobrancaSituacaoEspecial> listaResumoLigacoesEconomia = new ArrayList();

			List imoveisResumoLigacaoEconomias = this.repositorioGerencialCobranca.getResumoSituacaoEspecialCobrancaHelper(idLocalidade);
			// pra cada objeto obter a categoria e o indicador de existência de
			// hidrômetro
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes

			for(int i = 0; i < imoveisResumoLigacaoEconomias.size(); i++){
				Object obj = imoveisResumoLigacaoEconomias.get(i);

				// if (imoveisResumoLigacaoEconomias != null &&
				// imoveisResumoLigacaoEconomias.get(0) != null) {
				// Object obj = imoveisResumoLigacaoEconomias.get(0);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					ResumoCobrancaSituacaoEspecialHelper helper = new ResumoCobrancaSituacaoEspecialHelper((Integer) retorno[0],
									(Integer) retorno[1], (Integer) retorno[2], (Integer) retorno[3], (Integer) retorno[4],
									(Integer) retorno[5], (Integer) retorno[6], (Integer) retorno[7], (Integer) retorno[8],
									(Integer) retorno[9], (Integer) retorno[10], null, (Integer) retorno[11], (Integer) retorno[12],
									(Integer) retorno[13], (Integer) retorno[14], (Integer) retorno[15], (Integer) retorno[16]);

					// if (obj instanceof ResumoCobrancaSituacaoEspecialHelper)
					// {
					// ResumoCobrancaSituacaoEspecialHelper irleh =
					// (ResumoCobrancaSituacaoEspecialHelper) obj;

					Integer idImovel = helper.getIdImovel();

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

					if(categoria != null) helper.setIdCategoria(categoria.getId());

					// se ja existe um objeto igual a ele entao soma as ligacoes
					// e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao idImovel, quantidadeEconomia,
					// quantidadeLigacoes)
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoCobrancaSituacaoEspecialHelper jaCadastrado = (ResumoCobrancaSituacaoEspecialHelper) listaSimplificada
										.get(posicao);
						jaCadastrado.setQuantidadeImovel(jaCadastrado.getQuantidadeImovel() + 1);
					}else{
						listaSimplificada.add(helper);
					}
				}
			}

			/**
			 * para todoas as ImovelResumoLigacaoEconomiaHelper cria
			 * ResumoLigacoesEconomia
			 */
			for(int i = 0; i < listaSimplificada.size(); i++){
				ResumoCobrancaSituacaoEspecialHelper helper = (ResumoCobrancaSituacaoEspecialHelper) listaSimplificada.get(i);

				// Integer anoMesReferencia = Util
				// .getAnoMesComoInteger(new Date());

				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				CobrancaSituacaoTipo cobrancaSituacaoTipo = null;
				if(helper.getIdEspecialCobranca() != null){
					cobrancaSituacaoTipo = new CobrancaSituacaoTipo();
					cobrancaSituacaoTipo.setId(helper.getIdEspecialCobranca());
				}

				CobrancaSituacaoMotivo cobrancaSituacaoMotivo = null;
				if(helper.getIdMotivoSituacaoEspecialCobranca() != null){
					cobrancaSituacaoMotivo = new CobrancaSituacaoMotivo();
					cobrancaSituacaoMotivo.setId(helper.getIdMotivoSituacaoEspecialCobranca());
				}

				Integer anoMesInicioSituacaoEspecial = null;
				if(helper.getAnoMesInicioSituacaoEspecial() != null){
					anoMesInicioSituacaoEspecial = (helper.getAnoMesInicioSituacaoEspecial());
				}

				Integer anoMesFinalSituacaoEspecial = null;
				if(helper.getAnoMesFinalSituacaoEspecial() != null){
					anoMesFinalSituacaoEspecial = (helper.getAnoMesFinalSituacaoEspecial());
				}

				int quantidadeImovel = (helper.getQuantidadeImovel());

				GerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				Localidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new Localidade();
					localidade.setId(helper.getIdLocalidade());
				}

				SetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new SetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				Rota rota = null;
				if(helper.getIdRota() != null){
					rota = new Rota();
					rota.setId(helper.getIdRota());
				}

				Quadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new Quadra();
					quadra.setId(helper.getIdQuadra());
				}

				ImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new ImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				LigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new LigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				Categoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new Categoria();
					categoria.setId(helper.getIdCategoria());
				}

				EsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new EsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				UnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				ResumoCobrancaSituacaoEspecial resumo = new ResumoCobrancaSituacaoEspecial(codigoSetorComercial, numeroQuadra,
								anoMesInicioSituacaoEspecial, anoMesFinalSituacaoEspecial, quantidadeImovel, new Date(), gerenciaRegional,
								localidade, setorComercial, rota, quadra, imovelPerfil, ligacaoAguaSituacao, ligacaoEsgotoSituacao,
								categoria, esferaPoder, cobrancaSituacaoTipo, cobrancaSituacaoMotivo, unidadeNegocio);

				// ResumoCobrancaSituacaoEspecialHelper resumoLigacoesEconomia =
				// new ResumoCobrancaSituacaoEspecialHelper(anoMesReferencia,
				// codigoSetorComercial, numeroQuadra, indicadorHidrometro,
				// quantidadeLigacoes,
				// quantidadeEconomias, gerenciaRegional, localidade,
				// setorComercial, rota, quadra, imovelPerfil,
				// ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria,
				// esferaPoder);

				listaResumoLigacoesEconomia.add(resumo);
			}

			this.repositorioGerencialCobranca.inserirResumoSituacaoEspecialCobranca(listaResumoLigacoesEconomia);

			// --------------------------------------------------------
			//
			// Registrar o fim da execução da Unidade de Processamento
			//
			// --------------------------------------------------------
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			e.printStackTrace();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			sessionContext.setRollbackOnly();

			throw new EJBException(e);
		}
	}

	/**
	 * Método que gera o resumo da pendencia
	 * [UC0335] - Gerar Resumo do Parcelamento
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */

	public void gerarResumoPendencia(int idSetor, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		try{

			// Altera o nível de Log da classes QueryTranslatorImpl para ERROR. Desta forma,
			// evitamos WARNs desta classe devido ao tamanho das queries utilizadas neste processo
			Logger log = Logger.getLogger(QueryTranslatorImpl.class);
			Level originallevel = log.getLevel();
			log.setLevel(Level.ERROR);

			this.gerarResumoPendenciaContaGerencia(idSetor);
			this.gerarResumoPendenciaGuiasPagamentoGerencia(idSetor);
			this.gerarResumoPendenciaDebitosACobrarGerencia(idSetor);
			this.gerarResumoPendenciaCreditosARealizarGerencia(idSetor);

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

			// Retorna o nível de Log para o original
			log.setLevel(originallevel);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			System.out.println(" ERRO NO SETOR " + idSetor);

			ex.printStackTrace();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * Gera a primeira parte do resumo de pendencias
	 * [SB0001A]
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */
	private void gerarResumoPendenciaContaGerencia(int idSetor) throws ControladorException, ErroRepositorioException{

		// System.out.println( " ***RESUMO DE PENDENCIAS DAS CONTAS POR GERENCIA - LOCALIDADE " +
		// idLocalidade + "***");

		/*
		 * O sistema seleciona as contas pendentes ( a partir
		 * da tabela CONTA com CNTA_AMREFERENCIACONTA <
		 * PARM_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS
		 * e ( DCST_IDATUAL = 0 ou (DCST_IDATUAL = (1,2) e
		 * CNTA_AMREFERENCIACONTABIL < PARM_AMREFENRECIAFATURAMENTO
		 * ou (DCST_IDATUAL = (3,4,5) e CNTA_AMREFERENCIACONTABIL >
		 * PARM_AMREFERENCIAFATURAMENTO
		 */

		// final int RANGE = 100000;
		// Object[] retorno = this.repositorioGerencialCobranca.getFaixaContasPendentes(
		// idLocalidade );
		// int inicio = (Integer) ( retorno[0] != null ? retorno[0] : 0 );
		// int fim = (Integer) ( retorno[1] != null ? retorno[1] : 0 );
		//
		//
		// int contador = inicio;
		// while ( contador <= fim ){
		//
		// List contasPendentes = this.repositorioGerencialCobranca
		// .getContasPendentes(idLocalidade, contador, contador+RANGE);
		//
		// if ( contasPendentes.size() > 0 ){
		// System.out.println( " ***Achou contas entre " + contador + " e " + ( contador + RANGE ) +
		// " da localidade " + idLocalidade );
		// }

		List<ResumoPendenciaContasGerenciaHelper> quebra = new ArrayList<ResumoPendenciaContasGerenciaHelper>();

		List contasPendentes = this.repositorioGerencialCobranca.getContasPendentes(idSetor);

		for(int i = 0; i < contasPendentes.size(); i++){
			Object obj = contasPendentes.get(i);

			if(obj instanceof Object[]){
				Object[] linha = (Object[]) obj;

				ResumoPendenciaContasGerenciaHelper helper = new ResumoPendenciaContasGerenciaHelper((Integer) linha[0], // Gerencia
								// Regional
								(Integer) linha[1], // Unidade Negocio
								(Integer) linha[2], // Elo
								(Integer) linha[3], // Localidade
								(Integer) linha[4], // Setor Comercial
								(Integer) linha[5], // Rota
								(Integer) linha[6], // Quadra
								(Integer) linha[7], // Codigo Setor Comercial
								(Integer) linha[8], // Numero da quadra
								(Integer) linha[9], // Perfil do imovel
								(Integer) linha[10], // Situacao Ligacao Agua
								(Integer) linha[11], // Situacao Ligacao Esgoto
								(Integer) linha[12], // Perfil ligação Agua
								(Integer) linha[13], // Perfil ligação Esgoto
								(Integer) linha[14], // Volume Fixado Agua
								(Integer) linha[15], // Volume Fixado Agua
								(Integer) linha[16], // Volume Fixado Esgoto
								DocumentoTipo.CONTA, // Tipo do documento
								(Integer) linha[17], // Ano mes de referencia
								// documento
								(Integer) linha[18], // Referencia do vencimento
								// da conta
								(BigDecimal) linha[20], // Valor da agua
								(BigDecimal) linha[21], // Valor do Esgoto
								(BigDecimal) linha[22], // Valor dos debitos
								(BigDecimal) linha[23], // Valor dos créditos
								(BigDecimal) linha[24], // Valor do imposto
								(Integer) linha[26], // Tarifa de Consumo
								(Integer) linha[27]); // Quantidade de Ligações

				Integer idImovel = (Integer) linha[19]; // Codigo do imovel
				// que esta sendo
				// processado

				// Pesquisamos a esfera de poder do cliente responsavel
				helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
				// Pesquisamos o tipo de cliente responsavel do imovel
				helper
								.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
												.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

				// pesquisando a categoria
				// [UC0306] - Obtter principal categoria do imóvel
				Categoria categoria = null;
				categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
				if(categoria != null){
					helper.setIdPrincipalCategoriaImovel(categoria.getId());

					// Pesquisando a principal subcategoria
					ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

					if(subcategoria != null){
						helper.setIdPrincipalSubCategoriaImovel(subcategoria.getComp_id().getSubcategoria().getId());
					}
				}

				// Verificamos se a esfera de poder foi encontrada
				// [FS0002] Verificar existencia de cliente responsavel
				if(helper.getIdEsferaPoder().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)
									&& (clienteTemp.getClienteTipo().getEsferaPoder() != null)){
						helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
					}
				}

				if(helper.getIdTipoClienteResponsavel().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)){
						helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
					}
				}

				// Verificamos se o objeto ja possue uma quebra cadastrada
				acumularConta(quebra, helper);
			}
			obj = null;
		}
		// contasPendentes = null;
		//
		// if ( contador == inicio ){
		// ++contador;
		// }
		//
		// contador += RANGE;
		// }

		// Devemos gravar como anomes de arrecadacao -1
		SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
		Integer anoMesArrecadacaoMenosUm = Util.subtrairMesDoAnoMes(sistema.getAnoMesArrecadacao(), 1);

		// Inserirmos manualmente, por questão de performace
		for(int j = 0; j < quebra.size(); j++){
			this.repositorioGerencialCobranca.inserirPendenciaContasGerencia(anoMesArrecadacaoMenosUm,
							(ResumoPendenciaContasGerenciaHelper) quebra.get(j));
		}
	}

	/**
	 * Gera a segunda parte do resumo de pendencias
	 * ATENCAO - ESSA IMPLEMENTACAO NAO SERA UTILIZADA AGORA
	 * [SB0001B]
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */
	private void gerarResumoPendenciaContaRegiao(int idSetor) throws ControladorException, ErroRepositorioException{

		// System.out.println( " ***RESUMO DE PENDENCIAS DAS CONTAS POR REGIAO - LOCALIDADE " +
		// idLocalidade + "***");

		/*
		 * O sistema seleciona as contas pendentes (a partir
		 * da tabela CONTA com CNTA_AMREFERENCIACONTA < PARM_AMREFERENCIAFATURAMENTO
		 * da tabela SISTEMA_PARAMENTOS e DCST_IDATUAL com valor correspondente a normal
		 * ou incluida ou retificada
		 */
		List contasPendentesRegiao = this.repositorioGerencialCobranca.getContasPendentesPorRegiao(idSetor);

		List<ResumoPendenciaContasRegiaoHelper> quebra = new ArrayList();

		for(int i = 0; i < contasPendentesRegiao.size(); i++){
			// System.out.println( " ***Agrupando objeto " + i + " de " +
			// contasPendentesRegiao.size() + " da localidade " + idLocalidade + "***");
			Object obj = contasPendentesRegiao.get(i);

			if(obj instanceof Object[]){
				Object[] linha = (Object[]) obj;

				ResumoPendenciaContasRegiaoHelper helper = new ResumoPendenciaContasRegiaoHelper((Integer) linha[0], // Regiao
								(Integer) linha[1], // MicroRegiao
								(Integer) linha[2], // Municipio
								(Integer) linha[3], // Bairro
								(Integer) linha[4], // Perfil de Imovel
								(Integer) linha[5], // Situacao de Agua
								(Integer) linha[6], // Situacao de Esgoto
								(Integer) linha[7], // Possue Hidrometro
								(Integer) linha[8], // Volume fixo Agua
								(Integer) linha[9], // Volume fixo Esgoto
								new Integer(1), // Tipo de documento
								(Integer) linha[10], // Referencia do Documento
								(Integer) linha[11], // Referencia do vencimento da conta
								(BigDecimal) linha[12], // Valor da agua
								(BigDecimal) linha[13], // Valor do Esgoto
								(BigDecimal) linha[14], // Valor dos debitos
								(BigDecimal) linha[15], // Valor dos créditos
								(BigDecimal) linha[16]); // Valor do imposto

				Integer idImovel = (Integer) linha[17]; // Codigo do imovel que esta sendo
				// processado

				// Pesquisamos a esfera de poder do cliente responsavel
				helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
				// Pesquisamos o tipo de cliente responsavel do imovel
				helper
								.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
												.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

				// pesquisando a categoria
				// [UC0306] - Obtter principal categoria do imóvel
				Categoria categoria = null;
				categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
				if(categoria != null){
					helper.setIdPrincipalCategoria(categoria.getId());

					// Pesquisando a principal subcategoria
					ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

					if(subcategoria != null){
						helper.setIdPrincipalSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
					}
				}

				// Verificamos se a esfera de poder foi encontrada
				// [FS0002] Verificar existencia de cliente responsavel
				if(helper.getIdEsferaPoder().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if(clienteTemp != null){
						helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
					}

				}

				if(helper.getIdTipoClienteResponsavel().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if(clienteTemp != null){
						helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
					}

				}

				// Verificamos se o objeto ja possue uma quebra cadastrada
				if(quebra.contains(helper)){
					int posicao = quebra.indexOf(helper);

					ResumoPendenciaContasRegiaoHelper jaCadastrado = (ResumoPendenciaContasRegiaoHelper) quebra.get(posicao);

					// Incrementamos as quantidades e somamos os valores
					jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + 1);
					jaCadastrado.setQuantidadeDocumentos(jaCadastrado.getQuantidadeDocumentos() + 1);
					jaCadastrado.setValorPendenteAgua(jaCadastrado.getValorPendenteAgua().add(helper.getValorPendenteAgua()));
					jaCadastrado.setValorPendenteEsgoto(jaCadastrado.getValorPendenteEsgoto().add(helper.getValorPendenteEsgoto()));
					jaCadastrado.setValorPendenteCredito(jaCadastrado.getValorPendenteCredito().add(helper.getValorPendenteCredito()));
					jaCadastrado.setValorPendenteDebito(jaCadastrado.getValorPendenteDebito().add(helper.getValorPendenteDebito()));
					jaCadastrado.setValorPendenteImposto(jaCadastrado.getValorPendenteImposto().add(helper.getValorPendenteImposto()));
				}else{
					quebra.add(helper);
				}
			}
		}

		// Devemos gravar como anomes de arrecadacao -1
		SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
		Integer anoMesArrecadacaoMenosUm = Util.subtrairMesDoAnoMes(sistema.getAnoMesArrecadacao(), 1);

		// Inserirmos manualmente, por questão de performace
		for(int j = 0; j < quebra.size(); j++){
			// this.repositorioGerencialCobranca.inserirPendenciaContasGerencia(
			// anoMesArrecadacaoMenosUm, (ResumoPendenciaContasRegiaoHelper) quebra.get(j) );
		}
	}

	/**
	 * Gera a terceira parte do resumo de pendencias
	 * [SB0002A]
	 * 
	 * @author Bruno Barros
	 * @date 01/08/2007
	 */
	private void gerarResumoPendenciaGuiasPagamentoGerencia(int idSetor) throws ControladorException, ErroRepositorioException{

		// System.out.println( " ***RESUMO DE PENDENCIAS DAS CONTAS POR GERENCIA - LOCALIDADE " +
		// idLocalidade + "***");

		List contasPendentes = this.repositorioGerencialCobranca.getGuiasPagamentoGerencia(idSetor);

		List<ResumoPendenciaGuiasPagamentoGerenciaHelper> quebra = new ArrayList();

		for(int i = 0; i < contasPendentes.size(); i++){
			// System.out.println( " ***Agrupando objeto " + i + " de " + contasPendentes.size() +
			// " da localidade " + idLocalidade + "***");
			Object obj = contasPendentes.get(i);

			if(obj instanceof Object[]){
				Object[] linha = (Object[]) obj;

				ResumoPendenciaGuiasPagamentoGerenciaHelper helper = new ResumoPendenciaGuiasPagamentoGerenciaHelper((Integer) linha[0], // Gerencia
								// Regional
								(Integer) linha[1], // Unidade Negocio
								(Integer) linha[2], // Elo
								(Integer) linha[3], // Localidade
								(Integer) linha[4], // Setor Comercial
								(Integer) linha[5], // Rota
								(Integer) linha[6], // Quadra
								(Integer) linha[7], // Codigo Setor Comercial
								(Integer) linha[8], // Numero da quadra
								(Integer) linha[9], // Perfil do imovel
								(Integer) linha[10], // Situacao Ligacao Agua
								(Integer) linha[11], // Situacao Ligacao Esgoto
								(Integer) linha[12], // Perfil ligação Agua
								(Integer) linha[13], // Perfil ligação Esgoto
								(Integer) linha[14], // Indicador de Existência de Hidrômetro
								(Integer) linha[15], // Volume Fixado Agua
								(Integer) linha[16], // Volume Fixado Esgoto
								DocumentoTipo.GUIA_PAGAMENTO, // Tipo do documento
								(BigDecimal) linha[17],// Valor dos debitos
								(Integer) linha[19]); // Consumo Tarifa

				Integer idImovel = (Integer) linha[18]; // Codigo do imovel que esta sendo
				Short numeroPrestacaoTotal = (Short) linha[20]; // Número total de prestações da
				// Guia de Pagamento
				Integer idGuiaPagamento = (Integer) linha[21]; // id da Guia de Pagamento
				// processado

				// Pesquisamos a esfera de poder do cliente responsavel
				helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
				// Pesquisamos o tipo de cliente responsavel do imovel
				helper
								.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
												.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

				// pesquisando a categoria
				// [UC0306] - Obtter principal categoria do imóvel
				Categoria categoria = null;
				categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
				if(categoria != null){
					helper.setIdPrincipalCategoriaImovel(categoria.getId());

					// Pesquisando a principal subcategoria
					ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

					if(subcategoria != null){
						helper.setIdPrincipalSubCategoriaImovel(subcategoria.getComp_id().getSubcategoria().getId());
					}
				}

				// Verificamos se a esfera de poder foi encontrada
				// [FS0002] Verificar existencia de cliente responsavel
				if(helper.getIdEsferaPoder().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)
									&& (clienteTemp.getClienteTipo().getEsferaPoder() != null)){
						helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
					}

				}

				if(helper.getIdTipoClienteResponsavel().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)){
						helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
					}
				}

				// Recupera as informações das Prestações da Guia de Pagamento
				List<Object[]> prestacoes = this.repositorioGerencialCobranca.getPrestacoesDeGuiaPagamento(idGuiaPagamento);

				SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
				Integer anoMesReferenciaArrecadacao = sistema.getAnoMesArrecadacao();

				helper.setIdReferenciaVencimentoConta(Integer.valueOf(2));

				// Se existir prestações da guia de pagamento
				if((prestacoes != null) && (!prestacoes.isEmpty())){

					// Captura o ano mês de referência do documento e o tipo de financiamento da
					// primeira prestação retornada
					Object[] primeiraPrestacao = prestacoes.get(0);

					helper.setAnoMesReferenciaDocumento((Integer) primeiraPrestacao[0]);
					helper.setIdTipoFinanciamento((Integer) primeiraPrestacao[1]);

					// Verifica em cada prestação se a referência de vencimento é menor que a
					// referência de arrecadação. Caso exista alguma prestação nestas consições, o
					// indicador de referência de vencimento da conta será 1. Caso contrário, será 2
					// (setado previamente)
					for(Object[] prestacao : (List<Object[]>) prestacoes){
						if(Integer.valueOf((String) prestacao[2]) < anoMesReferenciaArrecadacao){
							helper.setIdReferenciaVencimentoConta(Integer.valueOf(1));
							break;
						}
					}
				}

				// [UC0335] - [SB007] - Obter Valores da Guia de Curto e Longo Prazo
				BigDecimal[] valoresCurtoLongoPrazo = extrairValoresCurtoLongoPrazoGuiaPagamento(numeroPrestacaoTotal, idGuiaPagamento);

				helper.setIdValorCurtoPrazo(ResumoPendenciaGerenciaHelper.VALOR_CURTO_PRAZO);

				// Seta o valor de curto prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[0] != null)){
					helper.setValorPendenteDebito(valoresCurtoLongoPrazo[0]);
				}else{
					helper.setValorPendenteDebito(new BigDecimal(0));
				}

				// Realiza a quebra e acumula a guia de pagamento
				acumularGuiaPagamento(quebra, helper);

				// Seta o valor de longo prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[1] != null)
								&& (!valoresCurtoLongoPrazo[1].equals(BigDecimal.ZERO))){

					try{
						// Cria um novo objeto Helper idêntico ao primeiro, mas com o indicador e o
						// valor de longo prazo
						ResumoPendenciaGuiasPagamentoGerenciaHelper helperLongoPrazo = (ResumoPendenciaGuiasPagamentoGerenciaHelper) helper
										.getCloneLongoPrazo(valoresCurtoLongoPrazo[1]);

						acumularGuiaPagamento(quebra, helperLongoPrazo);

					}catch(CloneNotSupportedException e){
						// TODO Auto-generated catch block
						throw new ControladorException("erro.sistema", e);
					}
				}
			}
		}

		// Devemos gravar como anomes de arrecadacao -1
		SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
		Integer anoMesArrecadacaoMenosUm = Util.subtrairMesDoAnoMes(sistema.getAnoMesArrecadacao(), 1);

		// Inserirmos manualmente, por questão de performace
		for(int j = 0; j < quebra.size(); j++){
			this.repositorioGerencialCobranca.inserirGuiasPagamentoGerencia(anoMesArrecadacaoMenosUm,
							(ResumoPendenciaGuiasPagamentoGerenciaHelper) quebra.get(j));
		}
	}

	/**
	 * Recupera os valores de curto e longo prazo para uma guia de pagamento
	 * 
	 * @return Valores de curto e longo prazo
	 */
	private BigDecimal[] extrairValoresCurtoLongoPrazoGuiaPagamento(Short numeroPrestacaoTotal, Integer idGuiaPagamento)
					throws ErroRepositorioException, ControladorException{

		short qtdPrestacoesPendentes = 0;
		BigDecimal valorPrestacoesPendentes = new BigDecimal(0);
		short numeroPrestacoesCobradas = 0;

		// Consulta os valores de prestações pendentes
		List<BigDecimal> prestacoesPendentes = this.repositorioGerencialCobranca.obterValoresPrestacoesPendentes(idGuiaPagamento);

		// obtem a quantidade e soma os valores das prestações pendentes
		if(prestacoesPendentes != null){
			qtdPrestacoesPendentes = (short) prestacoesPendentes.size();

			for(BigDecimal valorPrestacao : prestacoesPendentes){
				valorPrestacoesPendentes = valorPrestacoesPendentes.add(valorPrestacao);
			}
		}

		// calcula o número de prestações sobradas (total - prestações pendentes)
		numeroPrestacoesCobradas = (short) (numeroPrestacaoTotal.shortValue() - qtdPrestacoesPendentes);

		// Recupera os valores de curto e longo prazo
		BigDecimal[] valoresCurtoLongoPrazo = ServiceLocator.getInstancia().getControladorFaturamento()
						.obterValorACobrarDeCurtoELongoPrazo(qtdPrestacoesPendentes, numeroPrestacoesCobradas, valorPrestacoesPendentes);

		return valoresCurtoLongoPrazo;
	}

	/**
	 * Acumular valores de Conta
	 * 
	 * @param quebra
	 *            Lista que acumula as contas a serem inseridas em Resumo de Pendências.
	 *            Implementa a quebra das informações
	 * @param helper
	 *            Elemento de Conta a ser acumulado
	 */
	private void acumularConta(List<ResumoPendenciaContasGerenciaHelper> quebra, ResumoPendenciaContasGerenciaHelper helper){

		if(quebra.contains(helper)){
			int posicao = quebra.indexOf(helper);

			ResumoPendenciaContasGerenciaHelper jaCadastrado = (ResumoPendenciaContasGerenciaHelper) quebra.get(posicao);

			// Incrementamos as quantidades e somamos os valores
			jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + helper.getQuantidadeLigacoes());
			jaCadastrado.setQuantidadeDocumentos(jaCadastrado.getQuantidadeDocumentos() + 1);
			jaCadastrado.setValorPendenteAgua(jaCadastrado.getValorPendenteAgua().add(helper.getValorPendenteAgua()));
			jaCadastrado.setValorPendenteEsgoto(jaCadastrado.getValorPendenteEsgoto().add(helper.getValorPendenteEsgoto()));
			jaCadastrado.setValorPendenteCredito(jaCadastrado.getValorPendenteCredito().add(helper.getValorPendenteCredito()));
			jaCadastrado.setValorPendenteDebito(jaCadastrado.getValorPendenteDebito().add(helper.getValorPendenteDebito()));
			jaCadastrado.setValorPendenteImposto(jaCadastrado.getValorPendenteImposto().add(helper.getValorPendenteImposto()));
		}else{
			quebra.add(helper);
		}
	}

	/**
	 * Acumular valores de Guia de Pagamento
	 * 
	 * @param quebra
	 *            Lista com acumula as guias de pagamento a serem inseridas em Resumo de Pendências.
	 *            Implementa a quebra das informações
	 * @param helper
	 *            Elemento de Guia de Pagamento a ser acumulado
	 */
	private void acumularGuiaPagamento(List<ResumoPendenciaGuiasPagamentoGerenciaHelper> quebra,
					ResumoPendenciaGuiasPagamentoGerenciaHelper helper){

		// Verificamos se o objeto ja possue uma quebra cadastrada
		if(quebra.contains(helper)){
			int posicao = quebra.indexOf(helper);

			ResumoPendenciaGuiasPagamentoGerenciaHelper jaCadastrado = (ResumoPendenciaGuiasPagamentoGerenciaHelper) quebra.get(posicao);

			// Incrementamos as quantidades e somamos os valores
			jaCadastrado.setValorPendenteDebito(jaCadastrado.getValorPendenteDebito().add(helper.getValorPendenteDebito()));
		}else{
			quebra.add(helper);
		}
	}

	/**
	 * Acumular valores de Débito a Cobrar
	 * 
	 * @param quebra
	 *            Lista com acumula os débitos a cobrar a serem inseridos em Resumo de Pendências.
	 *            Implementa a quebra das informações
	 * @param helper
	 *            Elemento de Débito a Cobrar a ser acumulado
	 */
	private void acumularDebitoACobrar(List<ResumoPendenciaDebitosACobrarGerenciaHelper> quebra,
					ResumoPendenciaDebitosACobrarGerenciaHelper helper){

		// Verificamos se o objeto ja possue uma quebra cadastrada
		if(quebra.contains(helper)){
			int posicao = quebra.indexOf(helper);

			ResumoPendenciaDebitosACobrarGerenciaHelper jaCadastrado = (ResumoPendenciaDebitosACobrarGerenciaHelper) quebra.get(posicao);

			// Incrementamos as quantidades e somamos os valores
			jaCadastrado.setValorPendenteDebito(jaCadastrado.getValorPendenteDebito().add(helper.getValorPendenteDebito()));
		}else{
			quebra.add(helper);
		}
	}

	/**
	 * Acumular valores de Crédito a Realizar
	 * 
	 * @param quebra
	 *            Lista com acumula os créditos a realizar a serem inseridos em Resumo de
	 *            Pendências.
	 *            Implementa a quebra das informações
	 * @param helper
	 *            Elemento de Crédito a Realizar a ser acumulado
	 */
	private void acumularCreditoARealizar(List<ResumoPendenciaCreditoARealizarGerenciaHelper> quebra,
					ResumoPendenciaCreditoARealizarGerenciaHelper helper){

		// Verificamos se o objeto ja possue uma quebra cadastrada
		if(quebra.contains(helper)){
			int posicao = quebra.indexOf(helper);

			ResumoPendenciaCreditoARealizarGerenciaHelper jaCadastrado = (ResumoPendenciaCreditoARealizarGerenciaHelper) quebra
							.get(posicao);

			// Incrementamos as quantidades e somamos os valores
			jaCadastrado.setValorPendenteCredito(jaCadastrado.getValorPendenteCredito().add(helper.getValorPendenteCredito()));
		}else{
			quebra.add(helper);
		}
	}

	/**
	 * Gera a terceira parte do resumo de pendencias
	 * [SB0003A]
	 * 
	 * @author Bruno Barros
	 * @date 06/08/2007
	 */
	private void gerarResumoPendenciaDebitosACobrarGerencia(int idSetor) throws ControladorException, ErroRepositorioException{

		// System.out.println(
		// " ***RESUMO DE PENDENCIAS DOS DEBITOS A COBRAR POR GERENCIA - LOCALIDADE " + idLocalidade
		// + "***");

		List debitosACobrar = this.repositorioGerencialCobranca.getDebitosACobrarGerencia(idSetor);

		List<ResumoPendenciaDebitosACobrarGerenciaHelper> quebra = new ArrayList();

		for(int i = 0; i < debitosACobrar.size(); i++){
			// System.out.println( " ***Agrupando objeto " + i + " de " + debitosACobrar.size() +
			// " da localidade " + idLocalidade + "***");
			Object obj = debitosACobrar.get(i);

			if(obj instanceof Object[]){
				Object[] linha = (Object[]) obj;

				ResumoPendenciaDebitosACobrarGerenciaHelper helper = new ResumoPendenciaDebitosACobrarGerenciaHelper((Integer) linha[0], // Gerencia
								// Regional
								(Integer) linha[1], // Unidade Negocio
								(Integer) linha[2], // Elo
								(Integer) linha[3], // Localidade
								(Integer) linha[4], // Setor Comercial
								(Integer) linha[5], // Rota
								(Integer) linha[6], // Quadra
								(Integer) linha[7], // Codigo Setor Comercial
								(Integer) linha[8], // Numero da quadra
								(Integer) linha[9], // Perfil do imovel
								(Integer) linha[10], // Situacao Ligacao Agua
								(Integer) linha[11], // Situacao Ligacao Esgoto
								(Integer) linha[12], // Perfil ligação Agua
								(Integer) linha[13], // Perfil ligação Esgoto
								(Integer) linha[14], // Indicador de Hidrometro
								(Integer) linha[15], // Volume Fixado Agua
								(Integer) linha[16], // Volume Fixado Esgoto
								DocumentoTipo.DEBITO_A_COBRAR, // Tipo do documento
								(Integer) linha[17], // Referencia do vencimento da conta
								(Integer) linha[18], // Tipo de Financiamento
								(BigDecimal) linha[19], // Valor dos debitos
								(Integer) linha[21]); // Tipo de Tarifa de Consumo

				Integer idImovel = (Integer) linha[20]; // Codigo do imovel que esta sendo
				Short numeroPrestacoes = (Short) linha[22]; // Número de prestações do débito
				Short numeroPrestacoesCobradas = (Short) linha[23]; // Número de prestações
				// cobradas

				// processado

				// Pesquisamos a esfera de poder do cliente responsavel
				helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
				// Pesquisamos o tipo de cliente responsavel do imovel
				helper
								.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
												.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

				// pesquisando a categoria
				// [UC0306] - Obtter principal categoria do imóvel
				Categoria categoria = null;
				categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
				if(categoria != null){
					helper.setIdPrincipalCategoriaImovel(categoria.getId());

					// Pesquisando a principal subcategoria
					ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

					if(subcategoria != null){
						helper.setIdPrincipalSubCategoriaImovel(subcategoria.getComp_id().getSubcategoria().getId());
					}
				}

				// Verificamos se a esfera de poder foi encontrada
				// [FS0002] Verificar existencia de cliente responsavel
				if(helper.getIdEsferaPoder().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)
									&& (clienteTemp.getClienteTipo().getEsferaPoder() != null)){
						helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
					}

				}

				if(helper.getIdTipoClienteResponsavel().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)){
						helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
					}

				}

				// [UC0335] - [SB005] - Obter Valores da Guia de Curto e Longo Prazo
				// Recupera os valores de curto e longo prazo
				BigDecimal[] valoresCurtoLongoPrazo = ServiceLocator.getInstancia().getControladorFaturamento()
								.obterValorACobrarDeCurtoELongoPrazo(numeroPrestacoes.shortValue(), numeroPrestacoesCobradas.shortValue(),
												helper.getValorPendenteDebito());

				helper.setIdValorCurtoPrazo(ResumoPendenciaGerenciaHelper.VALOR_CURTO_PRAZO);

				// Seta o valor de curto prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[0] != null)){
					helper.setValorPendenteDebito(valoresCurtoLongoPrazo[0]);
				}else{
					helper.setValorPendenteDebito(new BigDecimal(0));
				}

				// Realiza a quebra e acumula o débito a cobrar
				acumularDebitoACobrar(quebra, helper);

				// Seta o valor de longo prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[1] != null)
								&& (!valoresCurtoLongoPrazo[1].equals(BigDecimal.ZERO))){

					try{
						// Cria um novo objeto Helper idêntico ao primeiro, mas com o indicador e o
						// valor de longo prazo
						ResumoPendenciaDebitosACobrarGerenciaHelper helperLongoPrazo = (ResumoPendenciaDebitosACobrarGerenciaHelper) helper
										.getCloneLongoPrazo(valoresCurtoLongoPrazo[1]);

						acumularDebitoACobrar(quebra, helperLongoPrazo);

					}catch(CloneNotSupportedException e){
						throw new ControladorException("erro.sistema", e);
					}
				}
			}
		}

		// Devemos gravar como anomes de arrecadacao -1
		SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
		Integer anoMesArrecadacaoMenosUm = Util.subtrairMesDoAnoMes(sistema.getAnoMesArrecadacao(), 1);

		// Inserirmos manualmente, por questão de performace
		for(int j = 0; j < quebra.size(); j++){
			this.repositorioGerencialCobranca.inserirPendendiciaDebitosACobrarGerencia(anoMesArrecadacaoMenosUm,
							(ResumoPendenciaDebitosACobrarGerenciaHelper) quebra.get(j));
		}
	}

	/**
	 * Gera a ultima parte do resumo de pendencias
	 * [SB0004A]
	 * 
	 * @author Bruno Barros
	 * @date 06/08/2007
	 */
	private void gerarResumoPendenciaCreditosARealizarGerencia(int idSetor) throws ControladorException, ErroRepositorioException{

		// System.out.println(
		// " ***RESUMO DE PENDENCIAS DOS CREDITOS A REALIZAR POR GERENCIA - LOCALIDADE " +
		// idLocalidade + "***");

		List creditosARealizar = this.repositorioGerencialCobranca.getCreditoARealizarGerencia(idSetor);

		List<ResumoPendenciaCreditoARealizarGerenciaHelper> quebra = new ArrayList<ResumoPendenciaCreditoARealizarGerenciaHelper>();

		for(int i = 0; i < creditosARealizar.size(); i++){
			// System.out.println( " ***Agrupando objeto " + i + " de " + creditosARealizar.size() +
			// " da localidade " + idLocalidade + "***");
			Object obj = creditosARealizar.get(i);

			if(obj instanceof Object[]){
				Object[] linha = (Object[]) obj;

				ResumoPendenciaCreditoARealizarGerenciaHelper helper = new ResumoPendenciaCreditoARealizarGerenciaHelper(
								(Integer) linha[0], // Gerencia Regional
								(Integer) linha[1], // Unidade Negocio
								(Integer) linha[2], // Elo
								(Integer) linha[3], // Localidade
								(Integer) linha[4], // Setor Comercial
								(Integer) linha[5], // Rota
								(Integer) linha[6], // Quadra
								(Integer) linha[7], // Codigo Setor Comercial
								(Integer) linha[8], // Numero da quadra
								(Integer) linha[9], // Perfil do imovel
								(Integer) linha[10], // Situacao Ligacao Agua
								(Integer) linha[11], // Situacao Ligacao Esgoto
								(Integer) linha[12], // Perfil ligação Agua
								(Integer) linha[13], // Perfil ligação Esgoto
								(Integer) linha[14], // Indicador de Hidrometro
								(Integer) linha[15], // Volume Fixado Agua
								(Integer) linha[16], // Volume Fixado Esgoto
								DocumentoTipo.CREDITO_A_REALIZAR, // Tipo do documento
								(Integer) linha[17], // Ano mes de referencia documento
								(BigDecimal) linha[18], // Valor dos Creditos
								(Integer) linha[20]); // Tipo de Tarifa de consumo

				Integer idImovel = (Integer) linha[19]; // Codigo do imovel que esta sendo
				Short numeroPrestacoes = (Short) linha[21]; // Número de prestações do crédito
				Short numeroPrestacoesRealizadas = (Short) linha[22]; // Número de prestações
				// realizadas

				// Pesquisamos a esfera de poder do cliente responsavel
				helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
				// Pesquisamos o tipo de cliente responsavel do imovel
				helper
								.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
												.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

				// pesquisando a categoria
				// [UC0306] - Obtter principal categoria do imóvel
				Categoria categoria = null;
				categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
				if(categoria != null){
					helper.setIdPrincipalCategoriaImovel(categoria.getId());

					// Pesquisando a principal subcategoria
					ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

					if(subcategoria != null){
						helper.setIdPrincipalSubCategoriaImovel(subcategoria.getComp_id().getSubcategoria().getId());
					}
				}

				// Verificamos se a esfera de poder foi encontrada
				// [FS0002] Verificar existencia de cliente responsavel
				if(helper.getIdEsferaPoder().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)
									&& (clienteTemp.getClienteTipo().getEsferaPoder() != null)){
						helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
					}
				}

				if(helper.getIdTipoClienteResponsavel().equals(0)){

					Imovel imovel = new Imovel();
					imovel.setId(idImovel);
					Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

					if((clienteTemp != null) && (clienteTemp.getClienteTipo() != null)){
						helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
					}
				}

				// [UC0335] - [SB006] - Obter Valores da Guia de Curto e Longo Prazo
				// Recupera os valores de curto e longo prazo
				BigDecimal[] valoresCurtoLongoPrazo = ServiceLocator.getInstancia().getControladorFaturamento()
								.obterValorACobrarDeCurtoELongoPrazo(numeroPrestacoes.shortValue(),
												numeroPrestacoesRealizadas.shortValue(), helper.getValorPendenteCredito());

				helper.setIdValorCurtoPrazo(ResumoPendenciaGerenciaHelper.VALOR_CURTO_PRAZO);

				// Seta o valor de curto prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[0] != null)){
					helper.setValorPendenteCredito(valoresCurtoLongoPrazo[0]);
				}else{
					helper.setValorPendenteCredito(new BigDecimal(0));
				}

				// Realiza a quebra e acumula o débito a cobrar
				acumularCreditoARealizar(quebra, helper);

				// Seta o valor de longo prazo
				if((valoresCurtoLongoPrazo != null) && (valoresCurtoLongoPrazo[1] != null)
								&& (!valoresCurtoLongoPrazo[1].equals(BigDecimal.ZERO))){

					try{
						// Cria um novo objeto Helper idêntico ao primeiro, mas com o indicador e o
						// valor de longo prazo
						ResumoPendenciaCreditoARealizarGerenciaHelper helperLongoPrazo = (ResumoPendenciaCreditoARealizarGerenciaHelper) helper
										.getCloneLongoPrazo(valoresCurtoLongoPrazo[1]);

						acumularCreditoARealizar(quebra, helperLongoPrazo);

					}catch(CloneNotSupportedException e){
						throw new ControladorException("erro.sistema", e);
					}
				}
			}
		}

		// Devemos gravar como anomes de arrecadacao -1
		SistemaParametro sistema = getControladorUtil().pesquisarParametrosDoSistema();
		Integer anoMesArrecadacaoMenosUm = Util.subtrairMesDoAnoMes(sistema.getAnoMesArrecadacao(), 1);

		// Inserirmos manualmente, por questão de performace
		for(int j = 0; j < quebra.size(); j++){
			this.repositorioGerencialCobranca.inserirPendendiciaCreditosARealizerGerencia(anoMesArrecadacaoMenosUm,
							(ResumoPendenciaCreditoARealizarGerenciaHelper) quebra.get(j));
		}
	}

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de
	 * impressão da consulta. Dependendo da opção de totalização sempre é gerado
	 * o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Gera a lista de pendências das Contas e Guias de Pagamento
	 * consultarResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		try{

			// [FS0001] Verificar existência de dados para o ano/mês de
			// referência retornado
			String resumo = ResumoPendencia.class.getName();
			Integer countResumoPendencia = this.repositorioGerencialCobranca.verificarExistenciaAnoMesReferenciaResumo(
							informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia(), resumo);
			if(countResumoPendencia == null || countResumoPendencia == 0){
				throw new ControladorException("atencao.nao_existe_resumo_pendencia", null, Util
								.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));
			}

			List retorno = this.repositorioGerencialCobranca.consultarResumoPendencia(informarDadosGeracaoRelatorioConsultaHelper);

			// [FS0007] Nenhum registro encontrado
			if(retorno == null || retorno.equals("")){
				throw new ControladorException("atencao.pesquisa.nenhumresultado");
			}

			return retorno;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * CASO DE USO: CONSULTAR RESUMO DE SITUACAO ESPECIAL DE COBRANCA.
	 * 
	 * @author TIAGO MORENO RODRIGUES
	 * @date 26/05/2006
	 * @param idSituacaoTipo
	 * @param idSituacaoMotivo
	 * @return ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
	 * @throws ControladorException
	 */
	public Collection<ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper> recuperaResumoSituacaoEspecialCobranca(
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ControladorException{

		Collection<ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper> resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = null;
		// ResumoCobrancaSituacaoEspecialConsultaFinalHelper RCSEFinal = null;

		try{
			if(idSituacaoTipo != null){
				if(idSituacaoTipo.length == 1 && idSituacaoTipo[0] == 0){
					idSituacaoTipo = null;
				}
			}

			if(idSituacaoMotivo != null){
				if(idSituacaoMotivo.length == 1 && idSituacaoMotivo[0] == 0){
					idSituacaoMotivo = null;
				}
			}

			resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = this.repositorioGerencialCobranca
							.pesquisarResumoCobrancaSituacaoEspecialConsultaGerenciaRegionalHelper(idSituacaoTipo, idSituacaoMotivo);

			// Para cada "Resumo Cobranca Situacao Especial Consulta Gerencia Reg Helper" consulta
			// uma localidade
			for(ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper resumoCobrancaGerenciaRegHelper : resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper){

				Collection<ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper> resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper //
				= this.repositorioGerencialCobranca.pesquisarResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper(
								resumoCobrancaGerenciaRegHelper.getIdGerenciaRegional(), idSituacaoTipo, idSituacaoMotivo);

				resumoCobrancaGerenciaRegHelper
								.setResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper); //
				BigDecimal totalPercentualGerencia = new BigDecimal("0.00");
				BigDecimal totalFatEstimadoGerencia = new BigDecimal("0.00");
				Integer totalQtLigacoesGerencia = new Integer("0");

				// Para cada "Resumo Cobranca Situacao Especial Consulta Localidade Helper" consulta
				// tipo
				for(ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper resumoCobrancaUnidadeNegocioHelper : resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper){

					Collection<ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper> resumoCobrancaSituacaoEspecialConsultaUnidadeHelper = this.repositorioGerencialCobranca
									.pesquisarResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(resumoCobrancaUnidadeNegocioHelper
													.getIdUnidadeNegocio(), idSituacaoTipo, idSituacaoMotivo);

					resumoCobrancaUnidadeNegocioHelper
									.setResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(resumoCobrancaSituacaoEspecialConsultaUnidadeHelper); //
					BigDecimal totalPercentualUnidadeNegocio = new BigDecimal("0.00");
					BigDecimal totalFatEstimadoUnidadeNegocio = new BigDecimal("0.00");
					Integer totalQtLigacoesUnidadeNegocio = new Integer("0");

					// Para cada "Resumo Cobranca Situacao Especial Consulta Localidade Helper"
					// consulta tipo
					for(ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper resumoCobrancaLocalidadeHelper : resumoCobrancaSituacaoEspecialConsultaUnidadeHelper){

						Collection<ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper> resumoCobrancaSituacaoEspecialConsultaSitTipoHelper = this.repositorioGerencialCobranca
										.pesquisarResumoCobrancaSituacaoEspecialConsultaSitTipoHelper(resumoCobrancaGerenciaRegHelper
														.getIdGerenciaRegional(), resumoCobrancaLocalidadeHelper.getIdLocalidade(),
														idSituacaoTipo, idSituacaoMotivo);

						resumoCobrancaLocalidadeHelper
										.setResumoCobrancaSituacaoEspecialConsultaSitTipoHelper(resumoCobrancaSituacaoEspecialConsultaSitTipoHelper);
						BigDecimal totalPercentualLocalidade = new BigDecimal("0.00");
						BigDecimal totalFatEstimadoLocalidade = new BigDecimal("0.00");
						Integer totalQtLigacoesLocalidade = new Integer("0");

						// Para cada "Resumo Cobranca Situacao Especial Consulta Sit Tipo Helper"
						// consulta o motivo.
						for(ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper resumoCobrancaSitTipoHelper : resumoCobrancaSituacaoEspecialConsultaSitTipoHelper){

							Collection<ResumoCobrancaSituacaoEspecialConsultaMotivoHelper> resumoCobrancaSituacaoEspecialConsultaMotivoHelper = this.repositorioGerencialCobranca
											.pesquisarResumoCobrancaSituacaoEspecialConsultaMotivoHelper(resumoCobrancaGerenciaRegHelper
															.getIdGerenciaRegional(), resumoCobrancaLocalidadeHelper.getIdLocalidade(),
															resumoCobrancaSitTipoHelper.getIdSituacaoTipo(), idSituacaoTipo,
															idSituacaoMotivo);

							resumoCobrancaSitTipoHelper
											.setResumoCobrancaSituacaoEspecialConsultaMotivoHelper(resumoCobrancaSituacaoEspecialConsultaMotivoHelper);
							BigDecimal totalPercentualSitTipo = new BigDecimal("0.00");
							BigDecimal totalFatEstimadoSitTipo = new BigDecimal("0.00");
							Integer totalQtLigacoesSitTipo = new Integer("0");

							// Resumo Cobranca Situacao Especial Consulta Motivo Helper.
							for(ResumoCobrancaSituacaoEspecialConsultaMotivoHelper resumoCobrancaMotivoHelper : resumoCobrancaSituacaoEspecialConsultaMotivoHelper){

								// Calculando o Faturamento Estimado por Motivo

								Integer anoMesInicio = resumoCobrancaMotivoHelper.getAnoMesInicio() - 1;
								Integer localidade = resumoCobrancaLocalidadeHelper.getIdLocalidade();
								Collection<ResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper> resumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper = this.repositorioGerencialCobranca
												.pesquisarResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper(anoMesInicio, localidade);
								BigDecimal fatEstimado = (BigDecimal) resumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper.iterator()
												.next().getFaturamentoEstimado().setScale(2, RoundingMode.HALF_UP);

								String fatEstimadoFormatado = Util.formatarMoedaReal(fatEstimado);
								resumoCobrancaMotivoHelper.setCobrancaEstimado(fatEstimado);
								resumoCobrancaMotivoHelper.setValorCobrancaEstimadoFormatado(fatEstimadoFormatado);
								if(fatEstimado != null){
									totalFatEstimadoSitTipo = totalFatEstimadoSitTipo.add(fatEstimado).setScale(2);
								}
								// Calculando a Qt de Ligacoes por Motivo
								Integer anoMesInicioReal = resumoCobrancaMotivoHelper.getAnoMesInicio();
								Collection<ResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper> resumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper = this.repositorioGerencialCobranca
												.pesquisarResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper(anoMesInicioReal,
																localidade);

								Integer qtLigacoes = (Integer) resumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper.iterator().next()
												.getQtLigacoes();

								resumoCobrancaMotivoHelper.setQtLigacoes(qtLigacoes);
								if(qtLigacoes != null){
									totalQtLigacoesSitTipo = totalQtLigacoesSitTipo + qtLigacoes;
								}

								BigDecimal qtParalizada = new BigDecimal(resumoCobrancaMotivoHelper.getQtParalisada());
								// calculando o percentual
								BigDecimal i = new BigDecimal("100");
								BigDecimal percentual = new BigDecimal("0.00");
								if(qtParalizada != null && qtLigacoes != null && qtLigacoes != 0){
									BigDecimal qtLigacoesBigDecimal = new BigDecimal(qtLigacoes);
									percentual = (qtParalizada.multiply(i));
									percentual = percentual.divide(qtLigacoesBigDecimal, 2, RoundingMode.HALF_UP);

								}

								resumoCobrancaMotivoHelper.setPercentual(percentual);
								// if (percentual != null) {
								// totalPercentualSitTipo = totalPercentualSitTipo
								// .add(percentual).setScale(2);
								// }
							}
							// total fat estimado Situacao Tipo
							resumoCobrancaSitTipoHelper.setTotalFatEstimadoSitTipo(totalFatEstimadoSitTipo);
							totalFatEstimadoLocalidade = totalFatEstimadoLocalidade.add(totalFatEstimadoSitTipo).setScale(2,
											RoundingMode.HALF_UP);

							// total Qt ligacoes
							resumoCobrancaSitTipoHelper.setTotalQtLigacoesSitTipo(totalQtLigacoesSitTipo);
							totalQtLigacoesLocalidade = totalQtLigacoesLocalidade + totalQtLigacoesSitTipo;

							// total percentual Situacao Tipo
							BigDecimal qtParalizadaSitTipo = new BigDecimal(resumoCobrancaSitTipoHelper.getTotalSituacaoTipo());
							BigDecimal i = new BigDecimal("100");
							if(qtParalizadaSitTipo != null && totalQtLigacoesSitTipo != null && totalQtLigacoesSitTipo != 0){
								totalPercentualSitTipo = (qtParalizadaSitTipo.multiply(i));
								totalPercentualSitTipo = totalPercentualSitTipo.divide(new BigDecimal(totalQtLigacoesSitTipo), 2,
												RoundingMode.HALF_UP);

							}

							resumoCobrancaSitTipoHelper.setTotalPercentualSitTipo(totalPercentualSitTipo);
							// totalPercentualLocalidade = totalPercentualLocalidade
							// .add(totalPercentualSitTipo).setScale(2,
							// RoundingMode.HALF_UP);
						}
						// total fat estimado Localidade
						resumoCobrancaLocalidadeHelper.setTotalFatEstimadoLocalidade(totalFatEstimadoLocalidade);
						totalFatEstimadoUnidadeNegocio = totalFatEstimadoUnidadeNegocio.add(totalFatEstimadoLocalidade).setScale(2,
										RoundingMode.HALF_UP);

						// total ligacoes Localidade
						resumoCobrancaLocalidadeHelper.setTotalQtLigacoesLocalidade(totalQtLigacoesLocalidade);
						totalQtLigacoesUnidadeNegocio = totalQtLigacoesUnidadeNegocio + totalQtLigacoesLocalidade;

						// total percentual Localidade
						BigDecimal qtParalizadaLocalidade = new BigDecimal(resumoCobrancaLocalidadeHelper.getTotalLocalidade());
						BigDecimal i = new BigDecimal("100");
						if(qtParalizadaLocalidade != null && totalQtLigacoesLocalidade != null && totalQtLigacoesLocalidade != 0){
							totalPercentualLocalidade = (qtParalizadaLocalidade.multiply(i));
							totalPercentualLocalidade = totalPercentualLocalidade.divide(new BigDecimal(totalQtLigacoesLocalidade), 2,
											RoundingMode.HALF_UP);

						}

						resumoCobrancaLocalidadeHelper.setTotalPercentualLocalidade(totalPercentualLocalidade);
						// totalPercentualGerencia = totalPercentualGerencia.add(
						// totalPercentualLocalidade).setScale(2,
						// RoundingMode.HALF_UP);
					}
					// Total fat estimado Unidade Negócio
					resumoCobrancaUnidadeNegocioHelper.setTotalFatEstimadoUnidadeNegocio(totalFatEstimadoUnidadeNegocio);
					totalFatEstimadoGerencia = totalFatEstimadoGerencia.add(totalFatEstimadoUnidadeNegocio).setScale(2,
									RoundingMode.HALF_UP);

					// Total ligacoes Unidade Negócio
					resumoCobrancaUnidadeNegocioHelper.setTotalQtLigacoesUnidadeNegocio(totalQtLigacoesUnidadeNegocio);
					totalQtLigacoesGerencia = totalQtLigacoesGerencia + totalQtLigacoesUnidadeNegocio;

					// Total percentual Unidade Negócio
					BigDecimal qtParalizadaLocalidade = new BigDecimal(resumoCobrancaUnidadeNegocioHelper.getTotalUnidadeNegocio());
					BigDecimal i = new BigDecimal("100");
					if(qtParalizadaLocalidade != null && totalQtLigacoesUnidadeNegocio != null && totalQtLigacoesUnidadeNegocio != 0){
						totalPercentualUnidadeNegocio = (qtParalizadaLocalidade.multiply(i));
						totalPercentualUnidadeNegocio = totalPercentualUnidadeNegocio.divide(new BigDecimal(totalQtLigacoesUnidadeNegocio),
										2, RoundingMode.HALF_UP);

					}

					resumoCobrancaUnidadeNegocioHelper.setTotalPercentualUnidadeNegocio(totalPercentualUnidadeNegocio);
					// totalPercentualGerencia = totalPercentualGerencia.add(
					// totalPercentualLocalidade).setScale(2,
					// RoundingMode.HALF_UP);
				}
				// total percentual Gerencia

				resumoCobrancaGerenciaRegHelper.setTotalFatEstimadoGerencia(totalFatEstimadoGerencia);

				// total das ligacoes Gerencia
				resumoCobrancaGerenciaRegHelper.setTotalQtLigacoesGerencia(totalQtLigacoesGerencia);

				// total percentual Gerencia
				BigDecimal qtParalizadaGerencia = new BigDecimal(resumoCobrancaGerenciaRegHelper.getTotalGerenciaRegional());
				BigDecimal i = new BigDecimal("100");
				if(qtParalizadaGerencia != null && totalQtLigacoesGerencia != null && totalQtLigacoesGerencia != 0){
					totalPercentualGerencia = (qtParalizadaGerencia.multiply(i));
					totalPercentualGerencia = totalPercentualGerencia.divide(new BigDecimal(totalQtLigacoesGerencia), 2,
									RoundingMode.HALF_UP);

				}

				resumoCobrancaGerenciaRegHelper.setTotalPercentualGerencia(totalPercentualGerencia);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper;
	}

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de
	 * impressão da consulta. Dependendo da opção de totalização sempre é gerado
	 * o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Retorna os registro de resumo pendência dividindo em coleções de
	 * categoria RESIDENCIAL, COMERCIAL, INDUSTRIAL e PUBLICA
	 * retornaConsultaResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResumoPendenciaAcumuladoHelper> retornaConsultaResumoPendencia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException{

		// Pega a lista de Resumo de pendências da base de dados
		List retornoConsulta = this.consultarResumoPendencia(informarDadosGeracaoRelatorioConsultaHelper);

		Collection<ResumoPendenciaAcumuladoHelper> resumoPendenciaAcumulado = new ArrayList();
		ResumoPendenciaAcumuladoHelper resumoPendenciaAcumuladoHelper = new ResumoPendenciaAcumuladoHelper();

		if(retornoConsulta != null && !retornoConsulta.equals("") && retornoConsulta.size() != 0){
			for(int i = 0; i < retornoConsulta.size(); i++){
				Object obj = retornoConsulta.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					resumoPendenciaAcumuladoHelper = new ResumoPendenciaAcumuladoHelper((String) retorno[0], (String) retorno[1],
									(String) retorno[2], (String) retorno[3], (Integer) retorno[4], (Integer) retorno[5],
									(Integer) retorno[6], (BigDecimal) retorno[7]);
					resumoPendenciaAcumulado.add(resumoPendenciaAcumuladoHelper);
				}
			}
		}

		return resumoPendenciaAcumulado;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcao(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ControladorException{

		Collection cobrancaAcaoHelper = null;
		try{

			// [FS0001] Verificar existência de dados para o ano/mês de
			// referência retornado
			String resumo = ResumoCobrancaAcao.class.getName();
			Integer countResumoPendencia = this.repositorioGerencialCobranca.verificarExistenciaAnoMesReferenciaResumo(
							informarDadosGeracaoResumoAcaoConsultaHelper != null ? informarDadosGeracaoResumoAcaoConsultaHelper
											.getAnoMesReferencia() : null, resumo);
			if(countResumoPendencia == null || countResumoPendencia == 0){
				throw new ControladorException(
								"atencao.nao.existem.acoes.cobranca.resumo",
								null,
								Util
												.formatarAnoMesParaMesAno(informarDadosGeracaoResumoAcaoConsultaHelper != null ? informarDadosGeracaoResumoAcaoConsultaHelper
																.getAnoMesReferencia()
																: 0));
			}

			// Pega a lista de Resumo das Ações de Cobrança da base de dados
			cobrancaAcaoHelper = repositorioGerencialCobranca.consultarCobrancaAcao(informarDadosGeracaoResumoAcaoConsultaHelper);

			if(cobrancaAcaoHelper != null && !cobrancaAcaoHelper.isEmpty()){
				Iterator cobrancaAcaoIterator = cobrancaAcaoHelper.iterator();
				while(cobrancaAcaoIterator.hasNext()){
					CobrancaAcaoHelper resumoCobrancaAcao = (CobrancaAcaoHelper) cobrancaAcaoIterator.next();
					Integer idCobrancaAcao = resumoCobrancaAcao.getId();

					Integer quantidadeCobrancaDocumentos = repositorioGerencialCobranca.consultarCobrancaAcaoQuantidadeDocumentos(
									informarDadosGeracaoResumoAcaoConsultaHelper, idCobrancaAcao);
					if(quantidadeCobrancaDocumentos != null
									&& quantidadeCobrancaDocumentos.equals(resumoCobrancaAcao.getSomatorioQuantidadesDocumentos())){
						resumoCobrancaAcao.setIndicadorDefinitivo("DEFINITIVO");
					}else{
						resumoCobrancaAcao.setIndicadorDefinitivo("PROVISÓRIO");
					}

					Collection cobrancaAcaoSituacaoHelper = repositorioGerencialCobranca.consultarCobrancaAcaoSituacao(
									informarDadosGeracaoResumoAcaoConsultaHelper, idCobrancaAcao);
					if(cobrancaAcaoSituacaoHelper != null){
						// resumoCobrancaAcao
						// .setColecaoCobrancaAcaoSituacao(cobrancaAcaoSituacaoHelper);
						Iterator cobrancaAcaoSituacaoIterator = cobrancaAcaoSituacaoHelper.iterator();
						BigDecimal percentualQuantidade = null;
						BigDecimal percentualValor = null;
						Collection colecaoCobrancaAcaoSituacao = new ArrayList();
						while(cobrancaAcaoSituacaoIterator.hasNext()){
							CobrancaAcaoSituacaoHelper resumoCobrancaAcaoSituacao = (CobrancaAcaoSituacaoHelper) cobrancaAcaoSituacaoIterator
											.next();

							percentualQuantidade = Util.calcularPercentualBigDecimal(""
											+ resumoCobrancaAcaoSituacao.getQuantidadeDocumento(), ""
											+ resumoCobrancaAcao.getSomatorioQuantidadesDocumentos());

							percentualValor = Util.calcularPercentualBigDecimal("" + resumoCobrancaAcaoSituacao.getValorDoumento(), ""
											+ resumoCobrancaAcao.getSomatorioValoresDocumentos());
							resumoCobrancaAcaoSituacao.setPorcentagemQuantidade(percentualQuantidade);
							resumoCobrancaAcaoSituacao.setPorcentagemValor(percentualValor);
							Integer idCobrancaAcaoSituacao = resumoCobrancaAcaoSituacao.getId();
							Collection cobrancaAcaoDebitoHelper = repositorioGerencialCobranca.consultarCobrancaAcaoDebito(
											informarDadosGeracaoResumoAcaoConsultaHelper, idCobrancaAcao, idCobrancaAcaoSituacao);
							if(cobrancaAcaoDebitoHelper != null){
								resumoCobrancaAcaoSituacao.setColecaoCobrancaAcaoDebito(cobrancaAcaoDebitoHelper);
							}else{
								throw new ControladorException("atencao.pesquisa.nenhumresultado");
							}
							colecaoCobrancaAcaoSituacao.add(resumoCobrancaAcaoSituacao);
						}
						resumoCobrancaAcao.setColecaoCobrancaAcaoSituacao(colecaoCobrancaAcaoSituacao);
					}else{
						throw new ControladorException("atencao.pesquisa.nenhumresultado");
					}
				}
			}else{
				throw new ControladorException("atencao.pesquisa.nenhumresultado");
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoHelper;

	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as situações de débito da situação da ação de acordo com o
	 * indicador antesApos
	 * 
	 * @author Sávio Luiz
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito) throws ControladorException{

		Collection cobrancaAcaoDebitoHelperParaPago = new ArrayList();
		try{
			Collection cobrancaAcaoDebitoHelperAntes = repositorioGerencialCobranca.consultarCobrancaAcaoDebitoComIndicador(
							informarDadosGeracaoResumoAcaoConsultaHelper, idCobrancaAcao, idCobrancaAcaoSituacao,
							ResumoCobrancaAcao.INDICADOR_ANTES, idCobrancaAcaoDebito);
			cobrancaAcaoDebitoHelperParaPago.addAll(cobrancaAcaoDebitoHelperAntes);
			Collection cobrancaAcaoDebitoHelperApos = repositorioGerencialCobranca.consultarCobrancaAcaoDebitoComIndicador(
							informarDadosGeracaoResumoAcaoConsultaHelper, idCobrancaAcao, idCobrancaAcaoSituacao,
							ResumoCobrancaAcao.INDICADOR_APOS, idCobrancaAcaoDebito);
			cobrancaAcaoDebitoHelperParaPago.addAll(cobrancaAcaoDebitoHelperApos);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoDebitoHelperParaPago;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoPerfil(int anoMesReferencia, Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper) throws ControladorException{

		Collection cobrancaAcaoPerfil = null;
		try{
			if(idCobrancaAcaoDebito == null || idCobrancaAcaoDebito.equals("")){
				cobrancaAcaoPerfil = repositorioGerencialCobranca.consultarCobrancaAcaoSituacaoPerfilImovel(anoMesReferencia,
								idCobrancaAcao, idCobrancaAcaoSituacao, informarDadosGeracaoResumoAcaoConsultaHelper);
				if(cobrancaAcaoPerfil != null && !cobrancaAcaoPerfil.isEmpty()){
					Iterator cobrancaAcaoPerfilIterator = cobrancaAcaoPerfil.iterator();
					while(cobrancaAcaoPerfilIterator.hasNext()){
						CobrancaAcaoPerfilHelper resumoCobrancaAcaoPerfil = (CobrancaAcaoPerfilHelper) cobrancaAcaoPerfilIterator.next();
						Integer idPerfil = resumoCobrancaAcaoPerfil.getId();
						Collection cobrancaAcaoPerfilIndicador = repositorioGerencialCobranca
										.consultarCobrancaAcaoSituacaoPerfilImovelIndicador(anoMesReferencia, idCobrancaAcao,
														idCobrancaAcaoSituacao, idPerfil, informarDadosGeracaoResumoAcaoConsultaHelper);
						if(cobrancaAcaoPerfilIndicador != null){
							resumoCobrancaAcaoPerfil.setColecaoCobrancaAcaoPerfilIndicador(cobrancaAcaoPerfilIndicador);
						}
					}
				}
			}else{
				cobrancaAcaoPerfil = repositorioGerencialCobranca.consultarCobrancaAcaoDebitoPerfilImovel(anoMesReferencia, idCobrancaAcao,
								idCobrancaAcaoSituacao, idCobrancaAcaoDebito, idIndicador, informarDadosGeracaoResumoAcaoConsultaHelper);
				if(cobrancaAcaoPerfil != null && !cobrancaAcaoPerfil.isEmpty()){
					Iterator cobrancaAcaoPerfilIterator = cobrancaAcaoPerfil.iterator();
					while(cobrancaAcaoPerfilIterator.hasNext()){
						CobrancaAcaoPerfilHelper resumoCobrancaAcaoPerfil = (CobrancaAcaoPerfilHelper) cobrancaAcaoPerfilIterator.next();
						Integer idPerfil = resumoCobrancaAcaoPerfil.getId();
						Collection cobrancaAcaoPerfilIndicador = repositorioGerencialCobranca
										.consultarCobrancaAcaoDebitoPerfilImovelIndicador(anoMesReferencia, idCobrancaAcao,
														idCobrancaAcaoSituacao, idCobrancaAcaoDebito, idPerfil, idIndicador,
														informarDadosGeracaoResumoAcaoConsultaHelper);
						if(cobrancaAcaoPerfilIndicador != null){
							resumoCobrancaAcaoPerfil.setColecaoCobrancaAcaoPerfilIndicador(cobrancaAcaoPerfilIndicador);
						}
					}
				}
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoPerfil;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException{

		Collection cobrancaAcaoHelper = null;
		try{

			// [FS0001] Verificar existência de dados para o ano/mês de
			// referência retornado
			Integer countResumoPendencia = this.repositorioGerencialCobranca
							.verificarExistenciaResumoEventual(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(countResumoPendencia == null || countResumoPendencia == 0){
				throw new ControladorException("atencao.nao.existem.acoes.cobranca.resumo.eventual");
			}

			// Pega a lista de Resumo das Ações de Cobrança da base de dados
			cobrancaAcaoHelper = repositorioGerencialCobranca
							.consultarCobrancaAcaoEventual(informarDadosGeracaoResumoAcaoConsultaEventualHelper);

			if(cobrancaAcaoHelper != null && !cobrancaAcaoHelper.isEmpty()){
				Iterator cobrancaAcaoIterator = cobrancaAcaoHelper.iterator();
				while(cobrancaAcaoIterator.hasNext()){
					CobrancaAcaoHelper resumoCobrancaAcao = (CobrancaAcaoHelper) cobrancaAcaoIterator.next();
					Integer idCobrancaAcao = resumoCobrancaAcao.getId();

					FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
					filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, idCobrancaAcao));
					filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade("cobrancaCriterio");

					CobrancaAcao cobrancaAcao = null;
					Collection colecaoCobrancaAcao = getControladorUtil().pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
					cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcao);
					if(ConstantesSistema.SIM.equals(cobrancaAcao.getIndicadorEntregaDocumento())){
						resumoCobrancaAcao.setIndicadorEntregaDocumento(ConstantesSistema.SIM);
					}else{
						resumoCobrancaAcao.setIndicadorEntregaDocumento(ConstantesSistema.NAO);
					}
					System.out.println("IndicadorEntregaDocumento= " + resumoCobrancaAcao.getIndicadorEntregaDocumento());

					Integer quantidadeCobrancaDocumentos = repositorioGerencialCobranca.consultarCobrancaAcaoEventualQuantidadeDocumentos(
									informarDadosGeracaoResumoAcaoConsultaEventualHelper, idCobrancaAcao);
					if(quantidadeCobrancaDocumentos != null
									&& quantidadeCobrancaDocumentos.equals(resumoCobrancaAcao.getSomatorioQuantidadesDocumentos())){
						resumoCobrancaAcao.setIndicadorDefinitivo("DEFINITIVO");
					}else{
						resumoCobrancaAcao.setIndicadorDefinitivo("PROVISÓRIO");
					}

					Collection cobrancaAcaoSituacaoHelper = repositorioGerencialCobranca.consultarCobrancaAcaoSituacaoEventual(
									informarDadosGeracaoResumoAcaoConsultaEventualHelper, idCobrancaAcao);
					if(cobrancaAcaoSituacaoHelper != null){

						Iterator cobrancaAcaoSituacaoIterator = cobrancaAcaoSituacaoHelper.iterator();
						BigDecimal percentualQuantidade = null;
						BigDecimal percentualValor = null;
						Collection colecaoCobrancaAcaoSituacao = new ArrayList();
						while(cobrancaAcaoSituacaoIterator.hasNext()){
							CobrancaAcaoSituacaoHelper resumoCobrancaAcaoSituacao = (CobrancaAcaoSituacaoHelper) cobrancaAcaoSituacaoIterator
											.next();

							percentualQuantidade = Util.calcularPercentualBigDecimal(""
											+ resumoCobrancaAcaoSituacao.getQuantidadeDocumento(), ""
											+ resumoCobrancaAcao.getSomatorioQuantidadesDocumentos());

							percentualValor = Util.calcularPercentualBigDecimal("" + resumoCobrancaAcaoSituacao.getValorDoumento(), ""
											+ resumoCobrancaAcao.getSomatorioValoresDocumentos());
							resumoCobrancaAcaoSituacao.setPorcentagemQuantidade(percentualQuantidade);
							resumoCobrancaAcaoSituacao.setPorcentagemValor(percentualValor);
							Integer idCobrancaAcaoSituacao = resumoCobrancaAcaoSituacao.getId();
							Collection cobrancaAcaoDebitoHelper = repositorioGerencialCobranca.consultarCobrancaAcaoDebitoEventual(
											informarDadosGeracaoResumoAcaoConsultaEventualHelper, idCobrancaAcao, idCobrancaAcaoSituacao);
							if(cobrancaAcaoDebitoHelper != null){
								resumoCobrancaAcaoSituacao.setColecaoCobrancaAcaoDebito(cobrancaAcaoDebitoHelper);
							}else{
								throw new ControladorException("atencao.pesquisa.nenhumresultado");
							}
							colecaoCobrancaAcaoSituacao.add(resumoCobrancaAcaoSituacao);
						}
						resumoCobrancaAcao.setColecaoCobrancaAcaoSituacao(colecaoCobrancaAcaoSituacao);
					}else{
						throw new ControladorException("atencao.pesquisa.nenhumresultado");
					}
				}
			}else{
				throw new ControladorException("atencao.pesquisa.nenhumresultado");
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoHelper;

	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventualPerfil(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException{

		Collection cobrancaAcaoPerfil = null;
		try{
			if(idCobrancaAcaoDebito == null || idCobrancaAcaoDebito.equals("")){
				cobrancaAcaoPerfil = repositorioGerencialCobranca.consultarCobrancaAcaoEventualSituacaoPerfilImovel(idCobrancaAcao,
								idCobrancaAcaoSituacao, informarDadosGeracaoResumoAcaoConsultaEventualHelper);
				if(cobrancaAcaoPerfil != null && !cobrancaAcaoPerfil.isEmpty()){
					Iterator cobrancaAcaoPerfilIterator = cobrancaAcaoPerfil.iterator();
					while(cobrancaAcaoPerfilIterator.hasNext()){
						CobrancaAcaoPerfilHelper resumoCobrancaAcaoPerfil = (CobrancaAcaoPerfilHelper) cobrancaAcaoPerfilIterator.next();
						Integer idPerfil = resumoCobrancaAcaoPerfil.getId();
						Collection cobrancaAcaoPerfilIndicador = repositorioGerencialCobranca
										.consultarCobrancaAcaoEventualSituacaoPerfilImovelIndicador(idCobrancaAcao, idCobrancaAcaoSituacao,
														idPerfil, informarDadosGeracaoResumoAcaoConsultaEventualHelper);
						if(cobrancaAcaoPerfilIndicador != null){
							resumoCobrancaAcaoPerfil.setColecaoCobrancaAcaoPerfilIndicador(cobrancaAcaoPerfilIndicador);
						}
					}
				}
			}else{
				cobrancaAcaoPerfil = repositorioGerencialCobranca.consultarCobrancaAcaoEventualDebitoPerfilImovel(idCobrancaAcao,
								idCobrancaAcaoSituacao, idCobrancaAcaoDebito, idIndicador,
								informarDadosGeracaoResumoAcaoConsultaEventualHelper);
				if(cobrancaAcaoPerfil != null && !cobrancaAcaoPerfil.isEmpty()){
					Iterator cobrancaAcaoPerfilIterator = cobrancaAcaoPerfil.iterator();
					while(cobrancaAcaoPerfilIterator.hasNext()){
						CobrancaAcaoPerfilHelper resumoCobrancaAcaoPerfil = (CobrancaAcaoPerfilHelper) cobrancaAcaoPerfilIterator.next();
						Integer idPerfil = resumoCobrancaAcaoPerfil.getId();
						Collection cobrancaAcaoPerfilIndicador = repositorioGerencialCobranca
										.consultarCobrancaAcaoEventualDebitoPerfilImovelIndicador(idCobrancaAcao, idCobrancaAcaoSituacao,
														idCobrancaAcaoDebito, idPerfil, idIndicador,
														informarDadosGeracaoResumoAcaoConsultaEventualHelper);
						if(cobrancaAcaoPerfilIndicador != null){
							resumoCobrancaAcaoPerfil.setColecaoCobrancaAcaoPerfilIndicador(cobrancaAcaoPerfilIndicador);
						}
					}
				}
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoPerfil;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito) throws ControladorException{

		Collection cobrancaAcaoDebitoHelperParaPago = new ArrayList();
		try{
			Collection cobrancaAcaoDebitoHelperAntes = repositorioGerencialCobranca.consultarCobrancaAcaoEventualDebitoComIndicador(
							informarDadosGeracaoResumoAcaoConsultaEventualHelper, idCobrancaAcao, idCobrancaAcaoSituacao,
							ResumoCobrancaAcao.INDICADOR_ANTES, idCobrancaAcaoDebito);
			cobrancaAcaoDebitoHelperParaPago.addAll(cobrancaAcaoDebitoHelperAntes);
			Collection cobrancaAcaoDebitoHelperApos = repositorioGerencialCobranca.consultarCobrancaAcaoEventualDebitoComIndicador(
							informarDadosGeracaoResumoAcaoConsultaEventualHelper, idCobrancaAcao, idCobrancaAcaoSituacao,
							ResumoCobrancaAcao.INDICADOR_APOS, idCobrancaAcaoDebito);
			cobrancaAcaoDebitoHelperParaPago.addAll(cobrancaAcaoDebitoHelperApos);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return cobrancaAcaoDebitoHelperParaPago;
	}

	public Collection consultarResumoCobrancaAcaoEventual(Integer idCobrancaAcao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException{

		try{
			return repositorioGerencialCobranca.consultarResumoCobrancaAcaoEventual(idCobrancaAcao,
							informarDadosGeracaoResumoAcaoConsultaEventualHelper);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

}
