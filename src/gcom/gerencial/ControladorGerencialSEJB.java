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

package gcom.gerencial;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gerencial.bean.FiltrarRelatorioOrcamentoSINPHelper;
import gcom.gerencial.bean.FiltrarRelatorioQuadroMetasAcumuladoHelper;
import gcom.gerencial.bean.FiltroQuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.bean.OrcamentoSINPHelper;
import gcom.gerencial.bean.QuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gerencial.bean.QuadroMetasAcumuladoHelper;
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
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @author Raphael Rossiter
 * @created 20/05/2006
 */
public class ControladorGerencialSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioGerencial repositorioGerencial = null;

	// private IRepositorioUtil repositorioUtil = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		// repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioGerencial = RepositorioGerencialHBM.getInstancia();
	}

	/**
	 * Author: Vivianne Sousa Data: 1804/03/2006
	 * Retorna o valor do Controlador Util
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

	/**
	 * Esta funcionalidade permite informar dados para geração de relatórios ou
	 * consultas
	 * [UC0304] - Informar Dados para Geração de Relatório ou Consulta
	 * 
	 * @author Raphael Rossiter
	 * @date 22/05/2006
	 * @param mesAnoFaturamento
	 * @param opcaoTotalizacao
	 * @param idFauramentoGrupo
	 * @param idGerenciaRegional
	 * @param idEloPolo
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param nmQuadra
	 * @param idsImovelPerfil
	 * @param idsLigacaoAguaSituacao
	 * @param idsLigacaoEsgotoSituacao
	 * @param idsCategoria
	 * @param idsEsferaPoder
	 * @param tipoAnaliseFaturamento
	 * @param tipoRelatorio
	 * @return InformarDadosGeracaoRelatorioConsultaHelper
	 * @throws ControladorException
	 */
	public InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsulta(String mesAnoFaturamento,
					Integer opcaoTotalizacao, Integer idFauramentoGrupo, Integer idCobrancaGrupo, Integer idGerenciaRegional,
					Integer idEloPolo, Integer idLocalidade, Integer idSetorComercial, Integer nmQuadra, String[] idsImovelPerfil,
					String[] idsLigacaoAguaSituacao, String[] idsLigacaoEsgotoSituacao, String[] idsCategoria, String[] idsEsferaPoder,
					Integer tipoAnaliseFaturamento, Integer tipoRelatorio, Integer idUnidadeNegocio) throws ControladorException{

		InformarDadosGeracaoRelatorioConsultaHelper retorno = new InformarDadosGeracaoRelatorioConsultaHelper();

		if(!Util.validarMesAno(mesAnoFaturamento)){
			throw new ControladorException("atencao.ano_mes.invalido");
		}

		Integer anoMesReferencia = new Integer(Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamento));
		// SistemaParametro sistemaParametro =
		// this.getControladorUtil().pesquisarParametrosDoSistema();

		// if
		// (anoMesReferencia.compareTo(sistemaParametro.getAnoMesFaturamento())
		// ==
		// 1){
		// throw new
		// ControladorException("atencao.ano.mes.referencia.posterior.ano.mes.faturamento",
		// null,
		// String.valueOf(sistemaParametro.getAnoMesFaturamento()));
		// }

		retorno.setAnoMesReferencia(anoMesReferencia);
		retorno.setOpcaoTotalizacao(opcaoTotalizacao);
		retorno.setDescricaoOpcaoTotalizacao(this.obterDescricaoOpcaoTotalizacao(opcaoTotalizacao));

		Collection colecaoPesquisa = null;
		if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO) || //
						opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO) || //
						opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE) && idUnidadeNegocio != null){

			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));

			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Unidade Negócio");
			}

			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setUnidadeNegocio(unidadeNegocio);

		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GRUPO_FATURAMENTO) && idFauramentoGrupo != null){

			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, idFauramentoGrupo));

			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Grupo de Faturamento");
			}

			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setFaturamentoGrupo(faturamentoGrupo);

		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GRUPO_COBRANCA) && idCobrancaGrupo != null){
			FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();

			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, idCobrancaGrupo));

			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Grupo de Cobrança");
			}

			CobrancaGrupo cobrancaGrupo = (CobrancaGrupo) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setCobrancaGrupo(cobrancaGrupo);
		}else if((opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL)
						|| opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO) || opcaoTotalizacao
						.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE))
						&& idGerenciaRegional != null){

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, idGerenciaRegional));

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Gerência Regional");
			}

			GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setGerenciaRegional(gerenciaRegional);

		}else if((opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ELO_POLO)
						|| opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE) || opcaoTotalizacao
						.equals(ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL))
						&& idEloPolo != null){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idEloPolo));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Elo Pólo");
			}else{
				Localidade eloPolo = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

				if(eloPolo.getLocalidade() == null){
					throw new ControladorException("atencao.localidade.nao.elo");
				}else if(!eloPolo.getLocalidade().getId().equals(new Integer(eloPolo.getId()))){

					throw new ControladorException("atencao.localidade.nao.elo");

				}

				retorno.setEloPolo(eloPolo);
			}
		}else if((opcaoTotalizacao.equals(ConstantesSistema.CODIGO_LOCALIDADE)
						|| opcaoTotalizacao.equals(ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL) || opcaoTotalizacao
						.equals(ConstantesSistema.CODIGO_LOCALIDADE_QUADRA))
						&& idLocalidade != null){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Localidade");
			}

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setLocalidade(localidade);
		}else if((opcaoTotalizacao.equals(ConstantesSistema.CODIGO_SETOR_COMERCIAL) || opcaoTotalizacao
						.equals(ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA))
						&& idSetorComercial != null){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade("localidade");

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercial));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setLocalidade(setorComercial.getLocalidade());
			retorno.setSetorComercial(setorComercial);

		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_QUADRA) && idSetorComercial != null && nmQuadra != null){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, idSetorComercial));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, nmQuadra));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = this.getControladorUtil().pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Quadra");
			}

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);

			retorno.setLocalidade(quadra.getSetorComercial().getLocalidade());
			retorno.setSetorComercial(quadra.getSetorComercial());
			retorno.setQuadra(quadra);

		}

		if(idsImovelPerfil != null && idsImovelPerfil.length > 0){

			Collection colecaoImovelPerfil = new ArrayList();
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

			ImovelPerfil imovelPerfil = null;

			for(int index = 0; idsImovelPerfil.length > index; index++){

				if(idsImovelPerfil[index] != null && idsImovelPerfil[index].length() > 0){

					filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, idsImovelPerfil[index]));

					filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoPesquisa = this.getControladorUtil().pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.pesquisa_inexistente", null, "Perfil do Imóvel");
					}

					imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoPesquisa);
					filtroImovelPerfil.limparListaParametros();

					colecaoImovelPerfil.add(imovelPerfil);
				}
			}

			if(!colecaoImovelPerfil.isEmpty()){
				retorno.setColecaoImovelPerfil(colecaoImovelPerfil);
			}

		}

		if(idsLigacaoAguaSituacao != null && idsLigacaoAguaSituacao.length > 0){

			Collection colecaoLigacaoAguaSituacao = new ArrayList();
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

			LigacaoAguaSituacao ligacaoAguaSituacao = null;

			for(int index = 0; idsLigacaoAguaSituacao.length > index; index++){

				if(idsLigacaoAguaSituacao[index] != null && idsLigacaoAguaSituacao[index].length() > 0){
					filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
									idsLigacaoAguaSituacao[index]));

					filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoPesquisa = this.getControladorUtil().pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.pesquisa_inexistente", null, "Ligação de Água");
					}

					ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoPesquisa);
					filtroLigacaoAguaSituacao.limparListaParametros();

					colecaoLigacaoAguaSituacao.add(ligacaoAguaSituacao);
				}
			}

			if(!colecaoLigacaoAguaSituacao.isEmpty()){
				retorno.setColecaoLigacaoAguaSituacao(colecaoLigacaoAguaSituacao);
			}

		}

		if(idsLigacaoEsgotoSituacao != null && idsLigacaoEsgotoSituacao.length > 0){

			Collection colecaoLigacaoEsgotoSituacao = new ArrayList();
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

			for(int index = 0; idsLigacaoEsgotoSituacao.length > index; index++){

				if(idsLigacaoEsgotoSituacao[index] != null && idsLigacaoEsgotoSituacao[index].length() > 0){

					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
									idsLigacaoEsgotoSituacao[index]));

					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoPesquisa = this.getControladorUtil().pesquisar(filtroLigacaoEsgotoSituacao,
									LigacaoEsgotoSituacao.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.pesquisa_inexistente", null, "Ligação de Esgoto");
					}

					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util.retonarObjetoDeColecao(colecaoPesquisa);
					filtroLigacaoEsgotoSituacao.limparListaParametros();

					colecaoLigacaoEsgotoSituacao.add(ligacaoEsgotoSituacao);
				}
			}

			if(!colecaoLigacaoEsgotoSituacao.isEmpty()){
				retorno.setColecaoLigacaoEsgotoSituacao(colecaoLigacaoEsgotoSituacao);
			}

		}

		if(idsCategoria != null && idsCategoria.length > 0){

			Collection colecaoCategoria = new ArrayList();
			FiltroCategoria filtroCategoria = new FiltroCategoria();

			Categoria categoria = null;

			for(int index = 0; idsCategoria.length > index; index++){

				if(idsCategoria[index] != null && idsCategoria[index].length() > 0){

					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idsCategoria[index]));

					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoPesquisa = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.pesquisa_inexistente", null, "Categoria");
					}

					categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoPesquisa);
					filtroCategoria.limparListaParametros();

					colecaoCategoria.add(categoria);
				}
			}

			if(!colecaoCategoria.isEmpty()){
				retorno.setColecaoCategoria(colecaoCategoria);
			}
		}

		if(idsEsferaPoder != null && idsEsferaPoder.length > 0){

			Collection colecaoEsferaPoder = new ArrayList();
			FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();

			EsferaPoder esferaPoder = null;

			for(int index = 0; idsEsferaPoder.length > index; index++){

				if(idsEsferaPoder[index] != null && idsEsferaPoder[index].length() > 0){

					filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.ID, idsEsferaPoder[index]));

					filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoPesquisa = this.getControladorUtil().pesquisar(filtroEsferaPoder, EsferaPoder.class.getName());

					if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.pesquisa_inexistente", null, "Esfera de Poder");
					}

					esferaPoder = (EsferaPoder) Util.retonarObjetoDeColecao(colecaoPesquisa);
					filtroEsferaPoder.limparListaParametros();

					colecaoEsferaPoder.add(esferaPoder);
				}
			}

			if(!colecaoEsferaPoder.isEmpty()){
				retorno.setColecaoEsferaPoder(colecaoEsferaPoder);
			}

		}

		if(tipoAnaliseFaturamento != null){
			retorno.setTipoAnaliseFaturamento(tipoAnaliseFaturamento);
		}

		if(tipoRelatorio != null){
			retorno.setGerarRelatorio(true);
			retorno.setTipoRelatorio(tipoRelatorio);
		}

		return retorno;
	}

	/**
	 * Obtém a descrição da opção de totalização que está localizada nas
	 * constantes
	 * [UC0304] - Informar Dados para Geração de Relatório ou Consulta
	 * 
	 * @author Raphael Rossiter
	 * @date 01/06/2006
	 * @param opcaoTotalizacao
	 * @return String
	 */
	public String obterDescricaoOpcaoTotalizacao(Integer opcaoTotalizacao){

		String retorno = null;

		if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO)){
			retorno = ConstantesSistema.ESTADO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_ELO_POLO)){
			retorno = ConstantesSistema.ESTADO_ELO_POLO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL)){
			retorno = ConstantesSistema.ESTADO_GERENCIA_REGIONAL;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO)){
			retorno = ConstantesSistema.ESTADO_GRUPO_FATURAMENTO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA)){
			retorno = ConstantesSistema.ESTADO_GRUPO_COBRANCA;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_LOCALIDADE)){
			retorno = ConstantesSistema.ESTADO_LOCALIDADE;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ELO_POLO)){
			retorno = ConstantesSistema.ELO_POLO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE)){
			retorno = ConstantesSistema.ELO_POLO_LOCALIDADE;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL)){
			retorno = ConstantesSistema.ELO_POLO_SETOR_COMERCIAL;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL)){
			retorno = ConstantesSistema.GERENCIA_REGIONAL;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO)){
			retorno = ConstantesSistema.GERENCIA_REGIONAL_ELO_POLO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE)){
			retorno = ConstantesSistema.GERENCIA_REGIONAL_LOCALIDADE;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GRUPO_FATURAMENTO)){
			retorno = ConstantesSistema.GRUPO_FATURAMENTO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_GRUPO_COBRANCA)){
			retorno = ConstantesSistema.GRUPO_COBRANCA;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_LOCALIDADE)){
			retorno = ConstantesSistema.LOCALIDADE;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_LOCALIDADE_QUADRA)){
			retorno = ConstantesSistema.LOCALIDADE_QUADRA;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL)){
			retorno = ConstantesSistema.LOCALIDADE_SETOR_COMERCIAL;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_SETOR_COMERCIAL)){
			retorno = ConstantesSistema.SETOR_COMERCIAL;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA)){
			retorno = ConstantesSistema.SETOR_COMERCIAL_QUADRA;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_QUADRA)){
			retorno = ConstantesSistema.QUADRA;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO)){
			retorno = ConstantesSistema.ESTADO_UNIDADE_NEGOCIO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO)){
			retorno = ConstantesSistema.UNIDADE_NEGOCIO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO)){
			retorno = ConstantesSistema.UNIDADE_NEGOCIO_ELO_POLO;
		}else if(opcaoTotalizacao.equals(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE)){
			retorno = ConstantesSistema.UNIDADE_NEGOCIO_LOCALIDADE;
		}

		return retorno;
	}

	/**
	 * Método para auxilio de Casos de Uso de resumos
	 */

	public Collection criarColecaoAgrupamentoResumos(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		/**
		 * Este caso de uso serve para montar uma coleção de Object[] para
		 * montagem da parte Tabela Dados da Geração da Consulta q se refere as
		 * opções de agrupamento(no jsp).
		 * No Action q chamar esse método, mandar o retorno deste método para o
		 * jsp atraves do request com o nome de colecaoAgrupamento.
		 * ver Action ExibirResultadoConsultaResumoAnormalidadeAction.java
		 */

		Collection colecaoLocalidade = null;
		Collection colecaoAgrupamento = new ArrayList();
		FiltroLocalidade filtroLocalidade = null;
		Localidade localidade = null;
		Object[] objeto = null;
		switch(informarDadosGeracaoRelatorioConsultaHelper.getOpcaoTotalizacao().intValue()){
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.GRUPO_FATURAMENTO;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId().toString();
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getDescricao();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_GRUPO_COBRANCA:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.GRUPO_COBRANCA;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getCobrancaGrupo().getId().toString();
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getCobrancaGrupo().getDescricao();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.GERENCIA_REGIONAL;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId().toString();
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getNomeAbreviado();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.ELO_POLO;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId().toString();
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getDescricao();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.LOCALIDADE;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId().toString();
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getDescricao();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, informarDadosGeracaoRelatorioConsultaHelper
								.getSetorComercial().getLocalidade().getId()));
				colecaoLocalidade = getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());
				localidade = (Localidade) colecaoLocalidade.iterator().next();

				objeto = new Object[3];
				objeto[0] = ConstantesSistema.LOCALIDADE;
				objeto[1] = localidade.getId().toString();
				objeto[2] = localidade.getDescricao();
				colecaoAgrupamento.add(objeto);

				objeto = new Object[3];
				objeto[0] = ConstantesSistema.SETOR_COMERCIAL;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getCodigo() + "";
				objeto[2] = informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getDescricao();
				colecaoAgrupamento.add(objeto);
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID,
								informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getSetorComercial().getId()));
				Collection colecaoSetorComercial = getControladorUtil().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				SetorComercial setorComercial = (SetorComercial) colecaoSetorComercial.iterator().next();

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, setorComercial.getLocalidade().getId()));
				colecaoLocalidade = getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());
				localidade = (Localidade) colecaoLocalidade.iterator().next();

				objeto = new Object[3];
				objeto[0] = ConstantesSistema.LOCALIDADE;
				objeto[1] = localidade.getId().toString();
				objeto[2] = localidade.getDescricao();
				colecaoAgrupamento.add(objeto);

				objeto = new Object[3];
				objeto[0] = ConstantesSistema.SETOR_COMERCIAL;
				objeto[1] = setorComercial.getCodigo() + "";
				objeto[2] = setorComercial.getDescricao();
				colecaoAgrupamento.add(objeto);

				objeto = new Object[3];
				objeto[0] = ConstantesSistema.QUADRA;
				objeto[1] = informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getNumeroQuadra() + "";
				objeto[2] = "";
				colecaoAgrupamento.add(objeto);
				break;

			case ConstantesSistema.CODIGO_ESTADO:
				objeto = new Object[3];
				objeto[0] = ConstantesSistema.ESTADO;
				objeto[1] = "1";
				objeto[2] = "Estado";
				colecaoAgrupamento.add(objeto);
				break;

		}
		return colecaoAgrupamento;
	}

	/**
	 * Método para auxilio de Casos de Uso de resumos
	 */

	public Collection criarColecaoAgrupamentoResumosCobrancaAcao(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper) throws ControladorException{

		/**
		 * Este caso de uso serve para montar uma coleção de Object[] para
		 * montagem da parte Tabela Dados da Geração da Consulta q se refere as
		 * opções de agrupamento(no jsp).
		 * No Action q chamar esse método, mandar o retorno deste método para o
		 * jsp atraves do request com o nome de colecaoAgrupamento.
		 * ver Action ExibirResultadoConsultaResumoAnormalidadeAction.java
		 */

		Collection colecaoAgrupamento = new ArrayList();
		Object[] objeto = null;
		if(informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo() != null){
			objeto = new Object[3];
			objeto[0] = "Elo Pólo";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo().getId().toString();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade() != null){
			objeto = new Object[3];
			objeto[0] = "Localidade";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade().getId().toString();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial() != null){

			objeto = new Object[3];
			objeto[0] = "Setor Comercial";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial().getCodigo() + "";
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaHelper.getQuadra() != null){

			objeto = new Object[3];
			objeto[0] = "Quadra";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaHelper.getQuadra().getNumeroQuadra() + "";
			objeto[2] = "";
			colecaoAgrupamento.add(objeto);
		}

		return colecaoAgrupamento;
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
	public Collection criarColecaoAgrupamentoResumosCobrancaAcaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException{

		Collection colecaoAgrupamento = new ArrayList();
		Object[] objeto = null;
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
						&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
			objeto = new Object[3];
			objeto[0] = "Período de Emissão";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getFormatarDataEmissaoInicial();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getFormatarDataEmissaoFinal();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getIdCobrancaAcaoAtividadeComando() != null){
			objeto = new Object[3];
			objeto[0] = "Comando";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getTituloCobrancaAcaoAtividadeComando();
			objeto[2] = "";
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo() != null){
			objeto = new Object[3];
			objeto[0] = "Elo Pólo";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo().getId().toString();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade() != null){
			objeto = new Object[3];
			objeto[0] = "Localidade";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade().getId().toString();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial() != null){

			objeto = new Object[3];
			objeto[0] = "Setor Comercial";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial().getCodigo() + "";
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial().getDescricao();
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getQuadra() != null){

			objeto = new Object[3];
			objeto[0] = "Quadra";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getQuadra().getNumeroQuadra() + "";
			objeto[2] = "";
			colecaoAgrupamento.add(objeto);
		}
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio() != null){

			objeto = new Object[3];
			objeto[0] = "Unidade de Negócio";
			objeto[1] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio().getId().toString();
			objeto[2] = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio().getNome();
			colecaoAgrupamento.add(objeto);
		}

		return colecaoAgrupamento;
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * [UC0350 - Consultar Comparativo entre os Resumos do Faturamento,
	 * Arrecadação e da Pendência]
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarComparativoResumosFaturamentoArrecadacaoPendencia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException{

		List retorno = null;
		try{
			retorno = repositorioGerencial
							.consultarComparativoResumosFaturamentoArrecadacaoPendencia(informarDadosGeracaoRelatorioConsultaHelper);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da faturamento
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento,
	 * Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		List retorno = null;
		try{
			retorno = repositorioGerencial.consultarResumoFaturamento(informarDadosGeracaoRelatorioConsultaHelper);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da arrecadação
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento,
	 * Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoArrecadacao(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		List retorno = null;
		try{
			retorno = repositorioGerencial.consultarResumoArrecadacao(informarDadosGeracaoRelatorioConsultaHelper);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da pendência.
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento,
	 * Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoComparativoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		List retorno = null;
		try{
			retorno = repositorioGerencial.consultarComparativoResumoPendencia(informarDadosGeracaoRelatorioConsultaHelper);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_faturamento
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Collection<OrcamentoSINPHelper>
	 * @throws ControladorException
	 */
	public Collection<OrcamentoSINPHelper> pesquisarRelatorioOrcamentoSINP(FiltrarRelatorioOrcamentoSINPHelper filtro)
					throws ControladorException{

		Collection<OrcamentoSINPHelper> colecaoOrcamentoSINP = new ArrayList<OrcamentoSINPHelper>();

		int opcaoTotalizacao = filtro.getOpcaoTotalizacao();

		Collection<Integer> chavesLocalidade = null;
		Collection<Integer> chavesUnidade = null;
		Collection<Integer> chavesGerencia = null;

		// Pega todas os campos
		if(opcaoTotalizacao >= 1 && opcaoTotalizacao <= 5){

			try{

				chavesLocalidade = this.repositorioGerencial.pesquisarLocalidades();
				chavesUnidade = this.repositorioGerencial.pesquisarUnidadesNegocios();
				chavesGerencia = this.repositorioGerencial.pesquisarGerenciasRegionais();

			}catch(ErroRepositorioException ex){
				throw new ControladorException("erro.sistema", ex);
			}

		}else{

			chavesLocalidade = filtro.getChavesLocalidade();
			chavesUnidade = filtro.getChavesUnidade();
			chavesGerencia = filtro.getChavesGerencia();

		}

		switch(opcaoTotalizacao){

			// Estado
			case 1:
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 0, null));
				break;

			// Estado por Gerencia Regional
			case 2:

				// Gerencia Regional
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 3, chavesGerencia));

				// Estado
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 0, null));
				break;

			// Estado por Unidade Negocio
			case 3:

				// Unidade de Negocio
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 2, chavesUnidade));

				// Gerencia Regional
				filtro.setUnidadeNegocio(null);
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 3, chavesGerencia));

				// Estado
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 0, null));
				break;

			// Estado por Localidade
			case 5:

				// Localidade
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 1, chavesLocalidade));

				// Unidade de Negocio
				filtro.setLocalidade(null);
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 2, chavesUnidade));

				// Gerencia Regional
				filtro.setUnidadeNegocio(null);
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 3, chavesGerencia));

				// Estado
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 0, null));

				break;

			// Gerencia Regional
			case 6:
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 3, chavesGerencia));
				break;

			// Unidade de Negocio
			case 10:
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 2, chavesUnidade));
				break;

			// Localidade
			case 16:
				colecaoOrcamentoSINP.addAll(this.montarColecaoOrcamentoSINPHelper(filtro, 1, chavesLocalidade));
				break;

			default:
				break;
		}

		return colecaoOrcamentoSINP;
	}

	/**
	 * Verifica se existe dados nas tabelas de resumo
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 11/01/2007
	 * @param anoMesReferencia
	 * @throws ControladorException
	 */
	public void validarDadosOrcamentoSINP(int anoMesReferencia) throws ControladorException{

		try{
			boolean existeDados = this.repositorioGerencial.existeDadosUnResumoParaOrcamentoSINP(anoMesReferencia);

			if(!existeDados){
				throw new ControladorException("atencao.sem_registros_gerencias");
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_faturamento
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Collection<OrcamentoSINPHelper>
	 * @throws ControladorException
	 */
	private Collection<OrcamentoSINPHelper> montarColecaoOrcamentoSINPHelper(FiltrarRelatorioOrcamentoSINPHelper filtro, int tipoGroupBy,
					Collection<Integer> chaves) throws ControladorException{

		Collection<OrcamentoSINPHelper> colecaoOrcamentoSINP = new ArrayList<OrcamentoSINPHelper>();

		String descricaoTotalizacao = "";

		// Vai montar o relatorio agrupado por Localidade
		String groupBy = "";

		switch(tipoGroupBy){

			// Localidade
			case 1:
				groupBy = ",loca_id";
				break;

			// UnidadeNegocio
			case 2:
				groupBy = ",uneg_id";
				break;

			// Gerencia Regional
			case 3:
				groupBy = ",greg_id";
				break;

		}

		if(chaves != null && !chaves.isEmpty()){

			Collection colecaoFiltro = null;

			Localidade localidade = null;
			GerenciaRegional gerencia = null;
			UnidadeNegocio unidade = null;

			filtro.setGroupBy(groupBy);

			Iterator itera = chaves.iterator();

			while(itera.hasNext()){

				Integer key = (Integer) itera.next();

				switch(tipoGroupBy){

					// Localidade
					case 1:
						filtro.setLocalidade(key);

						FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
						filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, key));

						colecaoFiltro = this.getControladorUtil().pesquisar(filtroLocalidade, Localidade.class.getName());

						localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoFiltro);

						descricaoTotalizacao = localidade.getDescricao().trim();

						break;

					// UnidadeNegocio
					case 2:

						filtro.setUnidadeNegocio(key);

						FiltroUnidadeNegocio filtroUnidade = new FiltroUnidadeNegocio();
						filtroUnidade.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, key));

						colecaoFiltro = this.getControladorUtil().pesquisar(filtroUnidade, UnidadeNegocio.class.getName());

						unidade = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoFiltro);

						descricaoTotalizacao = unidade.getNome().trim();

						break;

					// Gerencia Regional
					case 3:

						filtro.setGerenciaRegional(key);

						FiltroGerenciaRegional filtroGerencia = new FiltroGerenciaRegional();
						filtroGerencia.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, key));

						colecaoFiltro = this.getControladorUtil().pesquisar(filtroGerencia, GerenciaRegional.class.getName());

						gerencia = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoFiltro);

						descricaoTotalizacao = gerencia.getNome().trim();

						break;

				}

				OrcamentoSINPHelper orcamento = this.montarOrcamentoSINPHelper(filtro);

				orcamento.setOpcaoTotalizacao(descricaoTotalizacao);

				colecaoOrcamentoSINP.add(orcamento);
			}
		}else{ // fim do if chave

			// Estado
			filtro.setLocalidade(null);
			filtro.setUnidadeNegocio(null);
			filtro.setGerenciaRegional(null);

			filtro.setGroupBy("");

			descricaoTotalizacao = "TOTAL GERAL PARA ESTADO";

			OrcamentoSINPHelper orcamento = this.montarOrcamentoSINPHelper(filtro);

			orcamento.setOpcaoTotalizacao(descricaoTotalizacao);

			colecaoOrcamentoSINP.add(orcamento);
		}

		return colecaoOrcamentoSINP;

	}

	private OrcamentoSINPHelper montarOrcamentoSINPHelper(FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper)
					throws ControladorException{

		OrcamentoSINPHelper orcamento = new OrcamentoSINPHelper();

		try{
			Object[] resumoLigacaoEconomia = this.repositorioGerencial
							.pesquisarRelatorioOrcamentoSINPResumoLigacaoEconomia(filtrarRelatorioOrcamentoSINPHelper);

			if(resumoLigacaoEconomia != null){

				Object[] totalLigacoes = this.repositorioGerencial
								.pesquisarRelatorioOrcamentoSINPTotalLigacoesResumoLigacaoEconomia(filtrarRelatorioOrcamentoSINPHelper);

				orcamento.setAguaTotalLigacoesCadastradas((Integer) resumoLigacaoEconomia[0]);
				orcamento.setEsgotoTotalLigacoesCadastradas((Integer) resumoLigacaoEconomia[1]);
				orcamento.setEsgotoTotalLigacoesCadastradasConvencional((Integer) resumoLigacaoEconomia[2]);
				orcamento.setAguaTotalLigacoesAtivas((Integer) resumoLigacaoEconomia[3]);
				orcamento.setEsgotoTotalLigacoesCadastradasCondominial((Integer) resumoLigacaoEconomia[4]);
				orcamento.setAguaTotalLigacoesMedidas((Integer) resumoLigacaoEconomia[5]);
				orcamento.setEsgotoTotalLigacoesAtivasConvencional((Integer) resumoLigacaoEconomia[6]);
				orcamento.setAguaTotalLigacoesComHidrometro((Integer) resumoLigacaoEconomia[7]);
				orcamento.setEsgotoTotalLigacoesAtivasCondominial((Integer) resumoLigacaoEconomia[8]);
				orcamento.setAguaTotalLigacoesResidencialCadastradas((Integer) resumoLigacaoEconomia[9]);
				orcamento.setEsgotoTotalLigacoesResidencialCadastradas((Integer) resumoLigacaoEconomia[10]);
				orcamento.setAguaTotalLigacoesDesligadas((Integer) resumoLigacaoEconomia[11]);
				orcamento.setAguaTotalEconomiasCadastradas((Integer) resumoLigacaoEconomia[12]);
				orcamento.setEsgotoTotalEconomiasCadastradasConvencional((Integer) resumoLigacaoEconomia[13]);
				orcamento.setAguaTotalEconomiasAtivas((Integer) resumoLigacaoEconomia[14]);
				orcamento.setEsgotoTotalEconomiasCadastradasCondominial((Integer) resumoLigacaoEconomia[15]);
				orcamento.setAguaTotalEconomiasAtivasMedidas((Integer) resumoLigacaoEconomia[16]);
				orcamento.setEsgotoTotalEconomiasAtivasConvencional((Integer) resumoLigacaoEconomia[17]);
				orcamento.setAguaTotalEconomiasResidencialCadastradas((Integer) resumoLigacaoEconomia[18]);
				orcamento.setEsgotoTotalEconomiasAtivasCondominial((Integer) resumoLigacaoEconomia[19]);
				orcamento.setAguaTotalEconomiasResidencialAtivasMicromedidas((Integer) resumoLigacaoEconomia[20]);
				orcamento.setEsgotoTotalEconomiasResidencialCadastradas((Integer) resumoLigacaoEconomia[21]);
				orcamento.setAguaTotalEconomiasResidencialAtivas((Integer) resumoLigacaoEconomia[22]);
				orcamento.setEsgotoTotalEconomiasResidencialAtivas((Integer) resumoLigacaoEconomia[23]);
				orcamento.setAguaTotalEconomiasComercialAtivas((Integer) resumoLigacaoEconomia[24]);
				orcamento.setEsgotoTotalEconomiasComercialAtivas((Integer) resumoLigacaoEconomia[25]);
				orcamento.setAguaTotalEconomiasIndustrialAtivas((Integer) resumoLigacaoEconomia[26]);
				orcamento.setEsgotoTotalEconomiasIndustrialAtivas((Integer) resumoLigacaoEconomia[27]);
				orcamento.setAguaTotalEconomiasPublicoAtivas((Integer) resumoLigacaoEconomia[28]);
				orcamento.setEsgotoTotalEconomiasPublicoAtivas((Integer) resumoLigacaoEconomia[29]);
				orcamento.setAguaTotalEconomiasRuralAtivas((Integer) resumoLigacaoEconomia[30]);

				orcamento.setAguaTotalLigacoesSuprimidas((Integer) resumoLigacaoEconomia[31]);

				if(totalLigacoes != null){

					Integer valorAguaNova = orcamento.getAguaTotalLigacoesCadastradas().intValue()
									- ((Integer) totalLigacoes[0]).intValue();

					Integer valorEsgotoNova = orcamento.getEsgotoTotalLigacoesCadastradas().intValue()
									- ((Integer) totalLigacoes[1]).intValue();

					orcamento.setAguaTotalLigacoesNovas(valorAguaNova);
					orcamento.setEsgotoTotalLigacoesNovas(valorEsgotoNova);
				}

			}

			Object[] resumoFaturamento = this.repositorioGerencial
							.pesquisarRelatorioOrcamentoSINPResumoFaturamento(filtrarRelatorioOrcamentoSINPHelper);

			if(resumoFaturamento != null){

				orcamento.setAguaTotalVolumeFaturadoMedido((Integer) resumoFaturamento[0]);
				orcamento.setEsgotoTotalVolumeFaturadoResidencial((Integer) resumoFaturamento[1]);
				orcamento.setEsgotoTotalVolumeFaturadoComercial((Integer) resumoFaturamento[2]);
				orcamento.setAguaTotalVolumeFaturadoEstimado((Integer) resumoFaturamento[3]);
				orcamento.setEsgotoTotalVolumeFaturadoIndustrial((Integer) resumoFaturamento[4]);
				orcamento.setEsgotoTotalVolumeFaturadoPublico((Integer) resumoFaturamento[5]);
				orcamento.setAguaTotalVolumeFaturadoResidencial((Integer) resumoFaturamento[6]);
				orcamento.setEsgotoTotalVolumeFaturadoGeral((Integer) resumoFaturamento[7]);
				orcamento.setAguaTotalVolumeFaturadoComercial((Integer) resumoFaturamento[8]);
				orcamento.setAguaTotalVolumeFaturadoIndustrial((Integer) resumoFaturamento[9]);
				orcamento.setAguaTotalVolumeFaturadoPublico((Integer) resumoFaturamento[10]);
				orcamento.setAguaTotalVolumeFaturadoRural((Integer) resumoFaturamento[11]);
				orcamento.setAguaTotalVolumeFaturadoGeral((Integer) resumoFaturamento[12]);

				orcamento.setAguaTotalFaturadoResidencial((BigDecimal) resumoFaturamento[13]);
				orcamento.setEsgotoTotalFaturadoResidencial((BigDecimal) resumoFaturamento[14]);
				orcamento.setAguaTotalFaturadoComercial((BigDecimal) resumoFaturamento[15]);
				orcamento.setEsgotoTotalFaturadoComercial((BigDecimal) resumoFaturamento[16]);
				orcamento.setAguaTotalFaturadoIndustrial((BigDecimal) resumoFaturamento[17]);
				orcamento.setEsgotoTotalFaturadoIndustrial((BigDecimal) resumoFaturamento[18]);
				orcamento.setAguaTotalFaturadoPublico((BigDecimal) resumoFaturamento[19]);
				orcamento.setEsgotoTotalFaturadoPublico((BigDecimal) resumoFaturamento[20]);
				orcamento.setAguaTotalFaturadoDireto((BigDecimal) resumoFaturamento[21]);
				orcamento.setEsgotoTotalFaturadoDireto((BigDecimal) resumoFaturamento[22]);
				orcamento.setAguaTotalFaturadoIndireto((BigDecimal) resumoFaturamento[23]);
				orcamento.setEsgotoTotalFaturadoIndireto((BigDecimal) resumoFaturamento[24]);
				orcamento.setDevolucao((BigDecimal) resumoFaturamento[25]);

			}

			// Consumo de Agua
			Object[] totalConsumoAgua = this.repositorioGerencial
							.pesquisarRelatorioOrcamentoSINPTotalComumoResumoConsumoAgua(filtrarRelatorioOrcamentoSINPHelper);

			if(totalConsumoAgua != null){

				orcamento.setAguaTotalVolumeFaturadoMicroMedido((Integer) totalConsumoAgua[0]);
				orcamento.setAguaTotalVolumeFaturadoEconomiasResidenciasAtivas((Integer) totalConsumoAgua[1]);
			}

			// Arrecadação Atual
			BigDecimal totalArrecadacao = this.repositorioGerencial.pesquisarRelatorioOrcamentoSINPTotalArrecadacaoResumoArrecadacao(
							filtrarRelatorioOrcamentoSINPHelper, false);

			if(totalArrecadacao != null){
				orcamento.setTotalArrecadacaoMesAtual(totalArrecadacao);
			}

			// Arrecadação Anterior
			BigDecimal totalArrecadacaoAnterior = this.repositorioGerencial
							.pesquisarRelatorioOrcamentoSINPTotalArrecadacaoResumoArrecadacao(filtrarRelatorioOrcamentoSINPHelper, true);

			if(totalArrecadacaoAnterior != null){
				orcamento.setTotalArrecadacaoMesAnterior(totalArrecadacaoAnterior);
			}

			// Saldo Contas a Receber
			BigDecimal totalSaldoContasReceber = this.repositorioGerencial
							.pesquisarRelatorioOrcamentoSINPTotalContasResumoPendencia(filtrarRelatorioOrcamentoSINPHelper);

			if(totalSaldoContasReceber != null){
				orcamento.setSaldoContasReceber(totalSaldoContasReceber);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return orcamento;
	}

	/**
	 * [UC0733] Gerar Quadro de metas Acumulado
	 * 
	 * @author Bruno Barros
	 * @param filtrarRelatorioQuadroMetasAcumuladoHelper
	 * @return
	 */
	public Collection<QuadroMetasAcumuladoHelper> pesquisarRelatorioQuadroMetasAcumulado(
					FiltrarRelatorioQuadroMetasAcumuladoHelper filtrarRelatorioQuadroMetasAcumuladoHelper) throws ControladorException{

		Collection<QuadroMetasAcumuladoHelper> retorno = new ArrayList<QuadroMetasAcumuladoHelper>();
		List dadosBrutos = null;

		try{
			dadosBrutos = repositorioGerencial.pesquisarRelatorioQuadroMetasAcumulado(filtrarRelatorioQuadroMetasAcumuladoHelper);

			if(dadosBrutos != null && dadosBrutos.size() > 0){
				Iterator iteDadosBrutos = dadosBrutos.iterator();

				while(iteDadosBrutos.hasNext()){
					// Pegamos linha por linha do retorno
					Object[] linha = (Object[]) iteDadosBrutos.next();

					// Montamos Helper por Helper para a coleção de retorno
					QuadroMetasAcumuladoHelper quadroMetasAcumuladoHelper = new QuadroMetasAcumuladoHelper();

					// Ligacoes Cadastradas
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradasSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCadastradas((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CADASTRADAS]);

					// Ligacoes Cortadas
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadasSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesCortadas((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CORTADAS]);

					// Ligacoes Suprimidas
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidasSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesSuprimidas((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS]);

					// Ligacoes Ativas
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivas((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS]);

					// Ligacoes Ativas com débitos a mais de 3 meses
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3mSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesAtivasDebitos3m((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M]);

					// Ligacoes Consumo Medido
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedidoSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedido((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO]);

					// Ligacoes Consumo 5m3
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3Subcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumo5m3((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3]);

					// Ligacoes Consumo Não Medido
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedidoSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoNaoMedido((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO]);

					// Ligacoes Consumo Media
					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMediaSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper
									.setQuantidadeLigacoesConsumoMedia((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA]);

					// Quantidade de Economias
					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria101((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_101]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria102((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_102]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria103((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_103]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria200((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_200]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria300((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_300]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria400((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_400]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria116((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_116]);

					quadroMetasAcumuladoHelper
									.setQuantidadeEconomiasSubcategoria115((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_115]);

					quadroMetasAcumuladoHelper.setQuantidadeEconomias((Integer) linha[RepositorioGerencialHBM.INDEX_QUANTIDADE_ECONOMIAS]);

					// Verificamos quais os grupos que foram formados
					// Gerencia regional
					if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
									RepositorioGerencialHBM.TOTALIZACAO_GERENCIA_REGIONAL)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_UNIDADE_NEGOCIO)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_LOCALIDADE)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_GERENCIA_REGIONAL)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_UNIDADE_NEGOCIO)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_LOCALIDADE)){

						FiltroGerenciaRegional filtro = new FiltroGerenciaRegional();
						filtro.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID,
										(Integer) linha[RepositorioGerencialHBM.INDEX_ID_GERENCIA]));
						Collection<GerenciaRegional> colGerenciaRegional = this.getControladorUtil().pesquisar(filtro,
										GerenciaRegional.class.getName());

						if(colGerenciaRegional != null && colGerenciaRegional.size() > 0){
							GerenciaRegional gerenciaRegional = (GerenciaRegional) colGerenciaRegional.toArray()[0];
							quadroMetasAcumuladoHelper.setNomeGerenciaRegional(gerenciaRegional.getId() + " - "
											+ gerenciaRegional.getNome());
						}
					}

					if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
									RepositorioGerencialHBM.TOTALIZACAO_UNIDADE_NEGOCIO)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_LOCALIDADE)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_UNIDADE_NEGOCIO)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_LOCALIDADE)){
						FiltroUnidadeNegocio filtro = new FiltroUnidadeNegocio();
						filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID,
										(Integer) linha[RepositorioGerencialHBM.INDEX_ID_UNIDADE_NEGOCIO]));
						Collection<UnidadeNegocio> colUnidadeNegocio = this.getControladorUtil().pesquisar(filtro,
										UnidadeNegocio.class.getName());

						if(colUnidadeNegocio != null && colUnidadeNegocio.size() > 0){
							UnidadeNegocio unidadeNegocio = (UnidadeNegocio) colUnidadeNegocio.toArray()[0];
							quadroMetasAcumuladoHelper.setNomeUnidadeNegocio(unidadeNegocio.getId() + " - " + unidadeNegocio.getNome());
						}
					}

					if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
									RepositorioGerencialHBM.TOTALIZACAO_LOCALIDADE)
									|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao().equals(
													RepositorioGerencialHBM.TOTALIZACAO_ESTADO_LOCALIDADE)){
						FiltroLocalidade filtro = new FiltroLocalidade();
						filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID,
										(Integer) linha[RepositorioGerencialHBM.INDEX_ID_LOCALIDADE]));
						Collection<Localidade> colLocalidade = this.getControladorUtil().pesquisar(filtro, Localidade.class.getName());

						if(colLocalidade != null && colLocalidade.size() > 0){
							Localidade localidade = (Localidade) colLocalidade.toArray()[0];
							quadroMetasAcumuladoHelper.setNomeLocalidade(localidade.getId() + " - " + localidade.getDescricao());
							quadroMetasAcumuladoHelper.setNomeCentroCusto(localidade.getCodigoCentroCusto());
						}
					}
					retorno.add(quadroMetasAcumuladoHelper);
				}
			}else{
				throw new ControladorException("atencao.naocadastrado", null, "Quadro de Metas Acumulado");
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecadação
	 * Consulta o quadro comparativo de faturamento e arrecadação a partir do filtro informado pelo
	 * usuário
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public Collection<QuadroComparativoFaturamentoArrecadacaoHelper> consultarQuadroComparativoFaturamentoArrecadacao(
					FiltroQuadroComparativoFaturamentoArrecadacaoHelper filtro) throws ControladorException{

		Collection<QuadroComparativoFaturamentoArrecadacaoHelper> resultadoConsulta = null;
		List<Object[]> dadosQuadroComparativo = null;

		try{

			if(filtro != null){

				switch(filtro.getOpcaoTotalizacao()){
					case FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_ESTADO:
						dadosQuadroComparativo = this.repositorioGerencial.consultarQuadroComparativoFaturamentoArrecadacaoPorEstado(filtro
										.getAnoMesReferencia());
						break;

					case FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_GERENCIA_REGIONAL:
						dadosQuadroComparativo = this.repositorioGerencial
										.consultarQuadroComparativoFaturamentoArrecadacaoPorGerenciaRegional(filtro.getAnoMesReferencia(),
														filtro.getIdGerenciaRegional());
						break;
					case FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_LOCALIDADE:
						dadosQuadroComparativo = this.repositorioGerencial
										.consultarQuadroComparativoFaturamentoArrecadacaoPorLocalidade(filtro.getAnoMesReferencia(),
														filtro.getIdLocalidade());
						break;

					default:
						break;
				}
			}

			if(!Util.isVazioOrNulo(dadosQuadroComparativo)){

				QuadroComparativoFaturamentoArrecadacaoHelper quadroComparativoTotalizador = new QuadroComparativoFaturamentoArrecadacaoHelper();
				quadroComparativoTotalizador.setDescricaoCategoria("TOTAL");
				quadroComparativoTotalizador.setValorContaFaturada(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorContaIncluida(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorContaCancelada(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorContaCanceladaPorParcelamento(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorContaPaga(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorContaPagaEmDia(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorParcelamentoCobradoContaFaturada(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorArrecadavel(BigDecimal.ZERO);
				quadroComparativoTotalizador.setValorPendencia(BigDecimal.ZERO);
				quadroComparativoTotalizador.setPercentualRecebimentos(BigDecimal.ZERO);
				quadroComparativoTotalizador.setPercentualRecebimentoEmDia(BigDecimal.ZERO);

				resultadoConsulta = new ArrayList<QuadroComparativoFaturamentoArrecadacaoHelper>();
				QuadroComparativoFaturamentoArrecadacaoHelper quadroComparativo = null;

				BigDecimal numeroCem = new BigDecimal("100.00");

				for(Object[] registroQuadroComparativo : dadosQuadroComparativo){

					quadroComparativo = new QuadroComparativoFaturamentoArrecadacaoHelper();
					quadroComparativo.setIdCategoria((Integer) registroQuadroComparativo[0]);
					quadroComparativo.setDescricaoCategoria((String) registroQuadroComparativo[1]);
					quadroComparativo.setValorContaFaturada((BigDecimal) registroQuadroComparativo[2]);
					quadroComparativo.setValorContaIncluida((BigDecimal) registroQuadroComparativo[3]);
					quadroComparativo.setValorContaCancelada((BigDecimal) registroQuadroComparativo[4]);
					quadroComparativo.setValorContaCanceladaPorParcelamento((BigDecimal) registroQuadroComparativo[5]);
					quadroComparativo.setValorContaPaga((BigDecimal) registroQuadroComparativo[6]);
					quadroComparativo.setValorContaPagaEmDia((BigDecimal) registroQuadroComparativo[7]);
					quadroComparativo.setValorParcelamentoCobradoContaFaturada((BigDecimal) registroQuadroComparativo[8]);
					
					// Valores calculados
					quadroComparativo.setValorArrecadavel(quadroComparativo.getValorContaFaturada()
									.add(quadroComparativo.getValorContaIncluida()).subtract(quadroComparativo.getValorContaCancelada())
									.subtract(quadroComparativo.getValorContaCanceladaPorParcelamento()));

					quadroComparativo.setValorPendencia(quadroComparativo.getValorArrecadavel().subtract(
									quadroComparativo.getValorContaPaga()));

					if(quadroComparativo.getValorArrecadavel() != null && !quadroComparativo.getValorArrecadavel().equals(BigDecimal.ZERO)){

						quadroComparativo.setPercentualRecebimentos(quadroComparativo.getValorContaPaga()
										.divide(quadroComparativo.getValorArrecadavel(), 4, RoundingMode.HALF_UP).multiply(numeroCem));

						quadroComparativo.setPercentualRecebimentoEmDia(quadroComparativo.getValorContaPagaEmDia()
										.divide(quadroComparativo.getValorArrecadavel(), 4, RoundingMode.HALF_UP).multiply(numeroCem));
					}else{

						quadroComparativo.setPercentualRecebimentos(BigDecimal.ZERO);
						quadroComparativo.setPercentualRecebimentoEmDia(BigDecimal.ZERO);
					}

					// Totalizando
					quadroComparativoTotalizador.setValorContaFaturada(quadroComparativoTotalizador.getValorContaFaturada().add(
									quadroComparativo.getValorContaFaturada()));
					quadroComparativoTotalizador.setValorContaIncluida(quadroComparativoTotalizador.getValorContaIncluida().add(
									quadroComparativo.getValorContaIncluida()));
					quadroComparativoTotalizador.setValorContaCancelada(quadroComparativoTotalizador.getValorContaCancelada().add(
									quadroComparativo.getValorContaCancelada()));
					quadroComparativoTotalizador
									.setValorContaCanceladaPorParcelamento(quadroComparativoTotalizador
													.getValorContaCanceladaPorParcelamento().add(
																	quadroComparativo.getValorContaCanceladaPorParcelamento()));
					quadroComparativoTotalizador.setValorContaPaga(quadroComparativoTotalizador.getValorContaPaga().add(
									quadroComparativo.getValorContaPaga()));
					quadroComparativoTotalizador.setValorContaPagaEmDia(quadroComparativoTotalizador.getValorContaPagaEmDia().add(
									quadroComparativo.getValorContaPagaEmDia()));
					quadroComparativoTotalizador.setValorParcelamentoCobradoContaFaturada(quadroComparativoTotalizador
									.getValorParcelamentoCobradoContaFaturada().add(
													quadroComparativo.getValorParcelamentoCobradoContaFaturada()));
					quadroComparativoTotalizador.setValorArrecadavel(quadroComparativoTotalizador.getValorArrecadavel().add(
									quadroComparativo.getValorArrecadavel()));
					quadroComparativoTotalizador.setValorPendencia(quadroComparativoTotalizador.getValorPendencia().add(
									quadroComparativo.getValorPendencia()));

					resultadoConsulta.add(quadroComparativo);
				}

				if(quadroComparativoTotalizador.getValorArrecadavel() != null
								&& !quadroComparativoTotalizador.getValorArrecadavel().equals(BigDecimal.ZERO)){

					quadroComparativoTotalizador.setPercentualRecebimentos(quadroComparativoTotalizador.getValorContaPaga()
									.divide(quadroComparativoTotalizador.getValorArrecadavel(), 4, RoundingMode.HALF_UP)
									.multiply(numeroCem));

					quadroComparativoTotalizador.setPercentualRecebimentoEmDia(quadroComparativoTotalizador.getValorContaPagaEmDia()
									.divide(quadroComparativoTotalizador.getValorArrecadavel(), 4, RoundingMode.HALF_UP)
									.multiply(numeroCem));

				}else{

					quadroComparativoTotalizador.setPercentualRecebimentos(BigDecimal.ZERO);
					quadroComparativoTotalizador.setPercentualRecebimentoEmDia(BigDecimal.ZERO);
				}

				resultadoConsulta.add(quadroComparativoTotalizador);

			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return resultadoConsulta;
	}

}
