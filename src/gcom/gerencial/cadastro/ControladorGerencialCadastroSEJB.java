/*
 * Copyright (C) 2007-2007 the GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
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
 * Foundation, Inc., 59 Temple Place ï¿½ Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araï¿½jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Clï¿½udio de Andrade Lira
 * Denys Guimarï¿½es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabï¿½ola Gomes de Araï¿½jo
 * Flï¿½vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Jï¿½nior
 * Homero Sampaio Cavalcanti
 * Ivan Sï¿½rgio da Silva Jï¿½nior
 * Josï¿½ Edmar de Siqueira
 * Josï¿½ Thiago Tenï¿½rio Lopes
 * Kï¿½ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Mï¿½rcio Roberto Batista da Silva
 * Maria de Fï¿½tima Sampaio Leite
 * Micaela Maria Coelho de Araï¿½jo
 * Nelson Mendonï¿½a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrï¿½a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araï¿½jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sï¿½vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa ï¿½ software livre; vocï¿½ pode redistribuï¿½-lo e/ou
 * modificï¿½-lo sob os termos de Licenï¿½a Pï¿½blica Geral GNU, conforme
 * publicada pela Free Software Foundation; versï¿½o 2 da
 * Licenï¿½a.
 * Este programa ï¿½ distribuï¿½do na expectativa de ser ï¿½til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implï¿½cita de
 * COMERCIALIZAï¿½ï¿½O ou de ADEQUAï¿½ï¿½O A QUALQUER PROPï¿½SITO EM
 * PARTICULAR. Consulte a Licenï¿½a Pï¿½blica Geral GNU para obter mais
 * detalhes.
 * Vocï¿½ deve ter recebido uma cï¿½pia da Licenï¿½a Pï¿½blica Geral GNU
 * junto com este programa; se nï¿½o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.Microrregiao;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.Regiao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.IRepositorioImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.RepositorioImovelHBM;
import gcom.cadastro.imovel.Subcategoria;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.FiltroContaCategoria;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.gerencial.cadastro.bean.HistogramaAguaEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaAguaLigacaoHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoLigacaoHelper;
import gcom.gerencial.cadastro.bean.ResumoColetaEsgotoHelper;
import gcom.gerencial.cadastro.bean.ResumoConsumoAguaHelper;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaConsultarHelper;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaHelper;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaRegiaoHelper;
import gcom.gerencial.cadastro.bean.ResumoMetasHelper;
import gcom.gerencial.cadastro.bean.ResumoParcelamentoHelper;
import gcom.gerencial.cadastro.bean.SumarioLigacaoPorCategoriaGCSHelper;
import gcom.gerencial.cadastro.cliente.GClienteTipo;
import gcom.gerencial.cadastro.cliente.GEsferaPoder;
import gcom.gerencial.cadastro.imovel.GCategoria;
import gcom.gerencial.cadastro.imovel.GImovelPerfil;
import gcom.gerencial.cadastro.imovel.GSubcategoria;
import gcom.gerencial.cadastro.localidade.GGerenciaRegional;
import gcom.gerencial.cadastro.localidade.GLocalidade;
import gcom.gerencial.cadastro.localidade.GQuadra;
import gcom.gerencial.cadastro.localidade.GSetorComercial;
import gcom.gerencial.cadastro.localidade.GUnidadeNegocio;
import gcom.gerencial.cobranca.IRepositorioGerencialCobranca;
import gcom.gerencial.cobranca.RepositorioGerencialCobrancaHBM;
import gcom.gerencial.cobranca.UnResumoParcelamento;
import gcom.gerencial.faturamento.GConsumoTarifa;
import gcom.gerencial.micromedicao.GRota;
import gcom.gerencial.micromedicao.UnResumoColetaEsgoto;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.util.ConstantesJNDI;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public class ControladorGerencialCadastroSEJB
				implements SessionBean {

	private static final Logger LOGGER = Logger.getLogger(ControladorGerencialCadastroSEJB.class);

	private static final long serialVersionUID = 1L;

	private IRepositorioGerencialCadastro repositorioGerencialCadastro = null;

	private IRepositorioGerencialCobranca repositorioGerencialCobranca = null;

	private IRepositorioImovel repositorioImovel = null;

	// private IRepositorioUtil repositorioUtil = null;

	SessionContext sessionContext;

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 * 
	 * @exception CreateException
	 *                Descriï¿½ï¿½o da exceï¿½ï¿½o
	 */
	public void ejbCreate() throws CreateException{

		// repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioGerencialCadastro = RepositorioGerencialCadastroHBM.getInstancia();

		repositorioGerencialCobranca = RepositorioGerencialCobrancaHBM.getInstancia();

		repositorioImovel = RepositorioImovelHBM.getInstancia();
	}

	/**
	 * Retorna a interface remota de ControladorImovel
	 * 
	 * @return A interface remota do controlador de parï¿½metro
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
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
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
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
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
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

	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

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
	 * Mï¿½todo que gera o resumo dos histogramas de ï¿½gua e esgoto
	 * [UC0566] - Gerar Histograma de ï¿½gua e Esgoto
	 * 
	 * @author Bruno Barros
	 * @date 09/05/2007
	 */
	public void gerarResumoHistograma(int anoMesReferencia, int idSetor, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		try{

			List histogramaAguaLigacao = this.repositorioGerencialCadastro.getContasHistograma(idSetor);

			List<HistogramaAguaLigacaoHelper> listaSimplificadaAguaLigacao = new ArrayList<HistogramaAguaLigacaoHelper>();
			List<HistogramaAguaEconomiaHelper> listaSimplificadaAguaEconomia = new ArrayList<HistogramaAguaEconomiaHelper>();
			List<HistogramaEsgotoLigacaoHelper> listaSimplificadaEsgotoLigacao = new ArrayList<HistogramaEsgotoLigacaoHelper>();
			List<HistogramaEsgotoEconomiaHelper> listaSimplificadaEsgotoEconomia = new ArrayList<HistogramaEsgotoEconomiaHelper>();

			for(int i = 0; i < histogramaAguaLigacao.size(); i++){
				Object obj = histogramaAguaLigacao.get(i);

				if(obj instanceof Object[]){

					HistogramaAguaLigacaoHelper histogramaAguaLigacaoHelper;
					HistogramaAguaEconomiaHelper histogramaAguaEconomiaHelper;
					HistogramaEsgotoLigacaoHelper histogramaEsgotoLigacaoHelper;
					HistogramaEsgotoEconomiaHelper histogramaEsgotoEconomiaHelper;

					Object[] retorno = (Object[]) obj;

					Integer idConta = (Integer) retorno[0];
					Integer idImovel = (Integer) retorno[12];

					// Variaveis para preenchimento dos Helper`s
					Integer idGerenciaRegional = (Integer) retorno[1];
					Integer idUnidadeNegocio = (Integer) retorno[2];
					Integer idElo = (Integer) retorno[3];
					Integer idLocalidade = (Integer) retorno[4];
					Integer idSetorCormecial = (Integer) retorno[5];
					Integer codigoSetorComercial = (Integer) retorno[6];
					Integer idQuadra = (Integer) retorno[7];
					Integer numeroQuadra = (Integer) retorno[8];

					// Verificamos se o imovel possue categoria mista
					Integer idLigacaoMista = (this.repositorioGerencialCadastro.pesquisaQuantidadeCategorias(idConta) == 1 ? 2 : 1);
					Integer idConsumoTarifa = (Integer) retorno[9];
					Integer idPerfilImovel = (Integer) retorno[10];

					// Pesquisamos a esfera de poder
					Integer idEsferaPoder = this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel);

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(idEsferaPoder.equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							idEsferaPoder = clienteTemp.getClienteTipo().getEsferaPoder().getId();
						}
					}

					Integer idSituacaoAgua = (Integer) retorno[11];
					Integer idSituacaoEsgoto = (Integer) retorno[13];
					BigDecimal percentualEsgoto = ((BigDecimal) retorno[14]);

					// Verificamos o consumo real
					Integer idConsumoReal = this.repositorioGerencialCadastro.verificarConsumoReal(idImovel);
					// Verificamos a existencia de hidrï¿½metro
					Integer idHidrometro = this.repositorioGerencialCadastro.verificarExistenciaHidrometro(idImovel);
					// Verificamos a existencia de poco no imovel
					Integer idPoco = this.repositorioGerencialCadastro.verificarExistenciaPoco(idImovel);
					// Verificamos a existencia de volume fixo de agua
					Integer idVolumeFixoAgua = this.repositorioGerencialCadastro.verificarExistenciaVolumeFixoAgua(idImovel);
					Integer idVolumeFixoEsgoto = this.repositorioGerencialCadastro.verificarExistenciaVolumeFixoEsgoto(idImovel);
					// Valores a serem calculados
					Integer quantidadeLigacaoAgua = 0;
					Integer quantidadeEconomiaLigacaoAgua = 0;
					Integer quantidadeEconomiaLigacaoEsgoto = 0;
					Integer quantidadeEconomiaAgua = 0;
					Integer quantidadeEconomiaEsgoto = 0;
					BigDecimal valorFaturadoLigacaoAgua = new BigDecimal(0);
					Integer volumeFaturadoLigacaoAgua = 0;
					BigDecimal valorFaturadoEconomiaAgua = new BigDecimal(0);
					Integer volumeFaturadoEconomiaAgua = 0;
					Integer quantidadeLigacaoEsgoto = 0;
					BigDecimal valorFaturadoLigacaoEsgoto = new BigDecimal(0);
					Integer volumeFaturadoLigacaoEsgoto = 0;
					BigDecimal valorFaturadoEconomiaEsgoto = new BigDecimal(0);
					Integer volumeFaturadoEconomiaEsgoto = 0;
					Integer qtdEconomias = 0;
					Integer quantidadeConsumoAguaLigacao = 0;
					Integer quantidadeConsumoAguaEconomia = 0;
					Integer quantidadeConsumoEsgotoLigacao = 0;
					Integer quantidadeConsumoEsgotoEconomia = 0;
					Categoria contaCategoriaPrincipal = null;

					// Criamos um laï¿½o com todas as categorias da conta
					FiltroContaCategoria filtro = new FiltroContaCategoria();
					filtro.adicionarCaminhoParaCarregamentoEntidade("comp_id");
					filtro.adicionarCaminhoParaCarregamentoEntidade("comp_id.categoria");
					filtro.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
					filtro.adicionarCaminhoParaCarregamentoEntidade("comp_id.categoria.categoriaTipo");
					filtro.adicionarParametro(new ParametroSimples(FiltroContaCategoria.CONTA_ID, idConta));

					Collection colContaCategoria = getControladorUtil().pesquisar(filtro, ContaCategoria.class.getName());

					Iterator iteContaCategoria = colContaCategoria.iterator();

					// Identificamos a categoria principal
					contaCategoriaPrincipal = this.repositorioGerencialCadastro.obterPrincipalCategoriaConta(idConta);

					while(iteContaCategoria.hasNext()){
						Integer qtdLigacoesNasEconomias = 0;
						// Verificamos se quantidade de economias dessa conta ï¿½
						// maior do que a anterior
						ContaCategoria contaCategoria = (ContaCategoria) iteContaCategoria.next();

						// DADOS EXCLUSIVOS DAS TABELAS DE AGUA
						/**
						 * **** DADOS PARA A TABELA HISTOGRAMA AGUA LIGACAO
						 * *****
						 */
						quantidadeLigacaoAgua++;

						if(contaCategoria.getConsumoAgua() != null){
							quantidadeConsumoAguaLigacao = contaCategoria.getConsumoAgua();
						}
						quantidadeEconomiaLigacaoAgua += contaCategoria.getQuantidadeEconomia();

						if(contaCategoria.getValorAgua() != null){
							valorFaturadoLigacaoAgua = valorFaturadoLigacaoAgua.add(contaCategoria.getValorAgua());
						}

						// Obtemos o consumo faturado de agua
						// Se o consumo de agua for menor do que o minimo * as
						// economias
						// Devemos pegar o mï¿½nimo
						if(contaCategoria.getConsumoAgua() != null && contaCategoria.getConsumoMinimoAgua() != null){
							if(contaCategoria.getConsumoAgua() < (contaCategoria.getConsumoMinimoAgua())){
								volumeFaturadoLigacaoAgua += contaCategoria.getConsumoMinimoAgua();
							}else{
								volumeFaturadoLigacaoAgua += contaCategoria.getConsumoAgua();
							}
						}
						/**
						 * **** FIM DADOS PARA A TABELA HISTOGRAMA AGUA LIGACAO
						 * *****
						 */

						/**
						 * **** DADOS PARA A TABELA HISTOGRAMA ESGOTO LIGACAO
						 * *****
						 */
						quantidadeLigacaoEsgoto++;

						if(contaCategoria.getConsumoEsgoto() != null){
							quantidadeConsumoEsgotoLigacao += contaCategoria.getConsumoEsgoto();
						}

						quantidadeEconomiaLigacaoEsgoto += contaCategoria.getQuantidadeEconomia();

						if(contaCategoria.getValorEsgoto() != null){
							valorFaturadoLigacaoEsgoto = valorFaturadoLigacaoEsgoto.add(contaCategoria.getValorEsgoto());
						}

						// Obtemos o consumo faturado de agua
						// Se o consumo de agua for menor do que o minimo * as
						// economias
						// Devemos pegar o mï¿½nimo
						if(contaCategoria.getConsumoEsgoto() != null && contaCategoria.getConsumoMinimoEsgoto() != null){
							if(contaCategoria.getConsumoEsgoto() < (contaCategoria.getConsumoMinimoEsgoto())){
								volumeFaturadoLigacaoEsgoto += contaCategoria.getConsumoMinimoEsgoto();
							}else{
								volumeFaturadoLigacaoEsgoto += contaCategoria.getConsumoEsgoto();
							}
						}
						/**
						 * **** FIM DADOS PARA A TABELA HISTOGRAMA ESGOTO
						 * LIGACAO *****
						 */

						// Verificamos a quantidade de economias da conta
						// faturada
						qtdEconomias = this.repositorioGerencialCadastro.pesquisaQuantidadeEconomias(idConta, contaCategoria.getComp_id()
										.getCategoria().getId());
						/**
						 * **** DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA
						 * *****
						 */
						quantidadeEconomiaAgua = qtdEconomias;

						if(contaCategoria.getValorAgua() != null){
							valorFaturadoEconomiaAgua = contaCategoria.getValorAgua();
							// qtdEconomias;
						}

						// Obtemos o percentual de esgoto
						// Alterado Bruno Barros / 23/11/2007
						// percentualEsgoto = this.repositorioGerencialCadastro
						// .percentualColetaEsgoto(idImovel);

						// Obtemos o consumo faturado de agua
						// Se o consumo de agua for menor do que o minimo * as
						// economias
						// Devemos pegar o mï¿½nimo
						if(contaCategoria.getConsumoAgua() != null && contaCategoria.getConsumoMinimoAgua() != null){
							if(contaCategoria.getConsumoAgua() < (contaCategoria.getConsumoMinimoAgua())){
								volumeFaturadoEconomiaAgua = contaCategoria.getConsumoMinimoAgua();
							}else{
								volumeFaturadoEconomiaAgua = contaCategoria.getConsumoAgua();
								// qtdEconomias;
							}
						}

						if(contaCategoria.getConsumoAgua() != null && contaCategoria.getConsumoAgua() != 0){
							quantidadeConsumoAguaEconomia = (contaCategoria.getConsumoAgua() / qtdEconomias);
						}

						if(contaCategoria.getComp_id().getCategoria().getId().equals(contaCategoriaPrincipal.getId())){
							qtdLigacoesNasEconomias = 1;
						}

						// Criamos um helper para os histograma de agua por economia
						histogramaAguaEconomiaHelper = new HistogramaAguaEconomiaHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, contaCategoria.getComp_id().getCategoria().getCategoriaTipo().getId(), contaCategoria
														.getComp_id().getCategoria().getId(), idConsumoTarifa, idPerfilImovel,
										idEsferaPoder, idSituacaoAgua, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null,
										quantidadeConsumoAguaEconomia, qtdLigacoesNasEconomias);

						// Agrupamos o histograma agua por economia
						if(!valorFaturadoEconomiaAgua.equals(0f)){
							if(listaSimplificadaAguaEconomia.contains(histogramaAguaEconomiaHelper)){
								int posicao = listaSimplificadaAguaEconomia.indexOf(histogramaAguaEconomiaHelper);

								HistogramaAguaEconomiaHelper jaCadastrado = (HistogramaAguaEconomiaHelper) listaSimplificadaAguaEconomia
												.get(posicao);

								jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia() + quantidadeEconomiaAgua);

								jaCadastrado.setValorFaturadoEconomia(jaCadastrado.getValorFaturadoEconomia()
												.add(valorFaturadoEconomiaAgua));

								jaCadastrado.setVolumeFaturadoEconomia(jaCadastrado.getVolumeFaturadoEconomia()
												+ volumeFaturadoEconomiaAgua);
								jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + qtdLigacoesNasEconomias);
							}else{
								histogramaAguaEconomiaHelper.setQuantidadeEconomia(quantidadeEconomiaAgua);

								histogramaAguaEconomiaHelper.setValorFaturadoEconomia(valorFaturadoEconomiaAgua);

								histogramaAguaEconomiaHelper.setVolumeFaturadoEconomia(volumeFaturadoEconomiaAgua);

								listaSimplificadaAguaEconomia.add(histogramaAguaEconomiaHelper);
							}
						}
						/**
						 * **** FIM DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA
						 * *****
						 */

						/**
						 * **** DADOS PARA A TABELA HISTOGRAMA ESGOTO ECONOMIA
						 * *****
						 */
						quantidadeEconomiaEsgoto = qtdEconomias;

						if(contaCategoria.getValorEsgoto() != null){
							valorFaturadoEconomiaEsgoto = contaCategoria.getValorEsgoto();
							// qtdEconomias;
						}

						// Obtemos o consumo faturado de agua
						// Se o consumo de agua for menor do que o minimo * as
						// economias
						// Devemos pegar o mï¿½nimo
						if(contaCategoria.getConsumoEsgoto() != null && contaCategoria.getConsumoMinimoEsgoto() != null){
							if(contaCategoria.getConsumoEsgoto() < (contaCategoria.getConsumoMinimoEsgoto())){
								volumeFaturadoEconomiaEsgoto = contaCategoria.getConsumoMinimoEsgoto();
							}else{
								volumeFaturadoEconomiaEsgoto = contaCategoria.getConsumoEsgoto();
								// qtdEconomias;
							}
						}

						if(contaCategoria.getConsumoEsgoto() != null && contaCategoria.getConsumoEsgoto() != 0){
							quantidadeConsumoEsgotoEconomia = ((contaCategoria.getConsumoEsgoto() / qtdEconomias));
						}

						// Criamos um helper para os histograma de Esgoto por
						// Economia
						histogramaEsgotoEconomiaHelper = new HistogramaEsgotoEconomiaHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, contaCategoria.getComp_id().getCategoria().getCategoriaTipo().getId(), contaCategoria
														.getComp_id().getCategoria().getId(), idConsumoTarifa, idPerfilImovel,
										idEsferaPoder, idSituacaoEsgoto, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto.shortValue() : null,
										(percentualEsgoto != null) ? percentualEsgoto.shortValue() : null, quantidadeConsumoEsgotoEconomia,
										qtdLigacoesNasEconomias);

						// Agrupamos o histograma esgoto por economia
						if(!valorFaturadoEconomiaEsgoto.equals(0f)){
							if(listaSimplificadaEsgotoEconomia.contains(histogramaEsgotoEconomiaHelper)){
								int posicao = listaSimplificadaEsgotoEconomia.indexOf(histogramaEsgotoEconomiaHelper);

								HistogramaEsgotoEconomiaHelper jaCadastrado = (HistogramaEsgotoEconomiaHelper) listaSimplificadaEsgotoEconomia
												.get(posicao);

								jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia() + quantidadeEconomiaEsgoto);

								jaCadastrado.setValorFaturadoEconomia(jaCadastrado.getValorFaturadoEconomia().add(
												valorFaturadoEconomiaEsgoto));

								jaCadastrado.setVolumeFaturadoEconomia(jaCadastrado.getVolumeFaturadoEconomia()
												+ volumeFaturadoEconomiaEsgoto);

								jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + qtdLigacoesNasEconomias);

							}else{
								histogramaEsgotoEconomiaHelper.setQuantidadeEconomia(quantidadeEconomiaEsgoto);

								histogramaEsgotoEconomiaHelper.setValorFaturadoEconomia(valorFaturadoEconomiaEsgoto);

								histogramaEsgotoEconomiaHelper.setVolumeFaturadoEconomia(volumeFaturadoEconomiaEsgoto);

								listaSimplificadaEsgotoEconomia.add(histogramaEsgotoEconomiaHelper);
							}
							/**
							 * **** FIM DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA
							 * *****
							 */
						}
					}

					FiltroCategoria filtroCategoria = new FiltroCategoria();

					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, contaCategoriaPrincipal.getId()));
					filtroCategoria.adicionarCaminhoParaCarregamentoEntidade("categoriaTipo");

					Collection colCategoria = getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

					Categoria categoriaExistente = (Categoria) colCategoria.iterator().next();

					// Criamos um helper para os histograma de agua por ligaï¿½ï¿½o
					histogramaAguaLigacaoHelper = new
					// HistogramaAguaLigacaoHelper(anoMesReferencia,
					// idGerenciaRegional, idUnidadeNegocio, idElo,
					// idLocalidade, idSetorCormecial,
					// codigoSetorComercial, idQuadra, numeroQuadra,
					// categoriaExistente.getCategoriaTipo().getId(),
					// contaCategoriaPrincipal.getId(),
					// idLigacaoMista, idConsumoTarifa,
					// idPerfilImovel, idEsferaPoder, idSituacaoAgua,
					// idConsumoReal, idHidrometro, idPoco,
					// idVolumeFixoAgua, quantidadeConsumoAguaLigacao);

					HistogramaAguaLigacaoHelper(anoMesReferencia, idGerenciaRegional, idUnidadeNegocio, idElo, idLocalidade,
									idSetorCormecial, codigoSetorComercial, idQuadra, numeroQuadra, categoriaExistente.getCategoriaTipo()
													.getId(), contaCategoriaPrincipal.getId(), new Short(idLigacaoMista + ""),
									idConsumoTarifa, idPerfilImovel, idEsferaPoder, idSituacaoAgua,
									(idConsumoReal != null) ? idConsumoReal.shortValue() : null,
									(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
													: null, (idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null,
									quantidadeConsumoAguaLigacao);
					// Agrupamos o histograma agua ligacao
					if(!valorFaturadoLigacaoAgua.equals(0f)){
						if(listaSimplificadaAguaLigacao.contains(histogramaAguaLigacaoHelper)){
							int posicao = listaSimplificadaAguaLigacao.indexOf(histogramaAguaLigacaoHelper);

							HistogramaAguaLigacaoHelper jaCadastrado = (HistogramaAguaLigacaoHelper) listaSimplificadaAguaLigacao
											.get(posicao);

							jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao() + quantidadeLigacaoAgua);

							jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
											+ quantidadeEconomiaLigacaoAgua);

							jaCadastrado.setValorFaturadoLigacao(jaCadastrado.getValorFaturadoLigacao().add(valorFaturadoLigacaoAgua));

							jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao() + volumeFaturadoLigacaoAgua);
						}else{
							histogramaAguaLigacaoHelper.setQuantidadeLigacao(quantidadeLigacaoAgua);

							histogramaAguaLigacaoHelper.setQuantidadeEconomiaLigacao(quantidadeEconomiaLigacaoAgua);

							histogramaAguaLigacaoHelper.setValorFaturadoLigacao(valorFaturadoLigacaoAgua);

							histogramaAguaLigacaoHelper.setVolumeFaturadoLigacao(volumeFaturadoLigacaoAgua);

							listaSimplificadaAguaLigacao.add(histogramaAguaLigacaoHelper);
						}
					}

					// Criamos um helper para os histograma de Esgoto por
					// ligaï¿½ï¿½o
					histogramaEsgotoLigacaoHelper = new HistogramaEsgotoLigacaoHelper(anoMesReferencia, idGerenciaRegional,
									idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra, numeroQuadra,
									categoriaExistente.getCategoriaTipo().getId(), contaCategoriaPrincipal.getId(), new Short(
													idLigacaoMista + ""), idConsumoTarifa, idPerfilImovel, idEsferaPoder, idSituacaoEsgoto,
									(idConsumoReal != null) ? idConsumoReal.shortValue() : null,
									(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
													: null, (idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto.shortValue() : null,
									(percentualEsgoto != null) ? percentualEsgoto.shortValue() : null, quantidadeConsumoEsgotoLigacao);

					// Agrupamos o histograma esgoto por ligacao
					if(!valorFaturadoLigacaoEsgoto.equals(0f)){
						if(listaSimplificadaEsgotoLigacao.contains(histogramaEsgotoLigacaoHelper)){
							int posicao = listaSimplificadaEsgotoLigacao.indexOf(histogramaEsgotoLigacaoHelper);

							HistogramaEsgotoLigacaoHelper jaCadastrado = (HistogramaEsgotoLigacaoHelper) listaSimplificadaEsgotoLigacao
											.get(posicao);

							jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao() + quantidadeLigacaoEsgoto);

							jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
											+ quantidadeEconomiaLigacaoEsgoto);

							jaCadastrado.setValorFaturadoLigacao(jaCadastrado.getValorFaturadoLigacao().add(valorFaturadoLigacaoEsgoto));

							jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao() + volumeFaturadoLigacaoEsgoto);
						}else{
							histogramaEsgotoLigacaoHelper.setQuantidadeLigacao(quantidadeLigacaoEsgoto);

							histogramaEsgotoLigacaoHelper.setQuantidadeEconomiaLigacao(quantidadeEconomiaLigacaoEsgoto);

							histogramaEsgotoLigacaoHelper.setValorFaturadoLigacao(valorFaturadoLigacaoEsgoto);

							histogramaEsgotoLigacaoHelper.setVolumeFaturadoLigacao(volumeFaturadoLigacaoEsgoto);

							listaSimplificadaEsgotoLigacao.add(histogramaEsgotoLigacaoHelper);
						}
					}
				}
			}

			for(int j = 0; j < listaSimplificadaAguaLigacao.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaAguaLigacao(anoMesReferencia,
								(HistogramaAguaLigacaoHelper) listaSimplificadaAguaLigacao.get(j));
			}

			for(int j = 0; j < listaSimplificadaAguaEconomia.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaAguaEconomima(anoMesReferencia,
								(HistogramaAguaEconomiaHelper) listaSimplificadaAguaEconomia.get(j));
			}

			for(int j = 0; j < listaSimplificadaEsgotoLigacao.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaEsgotoLigacao(anoMesReferencia,
								(HistogramaEsgotoLigacaoHelper) listaSimplificadaEsgotoLigacao.get(j));
			}

			for(int j = 0; j < listaSimplificadaEsgotoEconomia.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaEsgotoEconomia(anoMesReferencia,
								(HistogramaEsgotoEconomiaHelper) listaSimplificadaEsgotoEconomia.get(j));
			}

			gerarResumoHistogramaImoveisNaoFaturados(anoMesReferencia, idSetor, idFuncionalidadeIniciada);

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NO SETOR COMERCIAL " + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	public void gerarResumoHistogramaImoveisNaoFaturados(int anoMesReferencia, int idSetor, int unidadeIniciada)
					throws ControladorException{

		try{
			// selecionamos o consumo historico
			List consumoHistoricoHistograma = this.repositorioGerencialCadastro.getConsumoHistoricoImoveisNaoFaturados(idSetor);

			List<HistogramaAguaLigacaoHelper> listaSimplificadaAguaLigacao = new ArrayList<HistogramaAguaLigacaoHelper>();
			List<HistogramaAguaEconomiaHelper> listaSimplificadaAguaEconomia = new ArrayList<HistogramaAguaEconomiaHelper>();
			List<HistogramaEsgotoLigacaoHelper> listaSimplificadaEsgotoLigacao = new ArrayList<HistogramaEsgotoLigacaoHelper>();
			List<HistogramaEsgotoEconomiaHelper> listaSimplificadaEsgotoEconomia = new ArrayList<HistogramaEsgotoEconomiaHelper>();

			for(int i = 0; i < consumoHistoricoHistograma.size(); i++){
				Object obj = consumoHistoricoHistograma.get(i);

				if(obj instanceof Object[]){

					HistogramaAguaLigacaoHelper histogramaAguaLigacaoHelper;
					HistogramaAguaEconomiaHelper histogramaAguaEconomiaHelper;
					HistogramaEsgotoLigacaoHelper histogramaEsgotoLigacaoHelper;
					HistogramaEsgotoEconomiaHelper histogramaEsgotoEconomiaHelper;

					Object[] retorno = (Object[]) obj;

					Integer idImovel = (Integer) retorno[11];
					Imovel imovel = new Imovel();
					imovel.setId(idImovel);

					// Selecionamos todas as categorias distintas do imovel
					Collection colCategoriaImovel = this.repositorioGerencialCadastro.getCategoriasImovelDistintas(idImovel);
					Iterator iteCategoriaImovel = colCategoriaImovel.iterator();
					Integer consumoMinimoMes = 0;
					Integer consumoFaturadoMes = 0;
					Integer ligacaoMista = 0;

					// Obtemos o consumo mï¿½nimo de cada categoria do imovel
					while(iteCategoriaImovel.hasNext()){
						Categoria categoria = (Categoria) iteCategoriaImovel.next();
						categoria.setConsumoMinimo(this.obterConsumoMinimoCategoria(imovel, categoria));
						consumoMinimoMes += categoria.getConsumoMinimo();
						ligacaoMista++;
					}

					ligacaoMista = (ligacaoMista == 1 ? 2 : 1);

					// Selecionamos o consumo faturado do mes
					consumoFaturadoMes = (Integer) retorno[15];

					Short quantidadeEconomiasImovel = (Short) retorno[13];
					List<Long> consumosImovelporCategoria = new ArrayList<Long>();
					List<Short> quantidadeEconomiasImovelporCategoria = new ArrayList<Short>();

					Categoria categoriaPrincipal = null;
					Short quantidadeEconomiasAnterior = 0;
					// Obtemos o consumo do imovel para cada categoria e selecionamos
					// a principal categoria do imovel

					iteCategoriaImovel = colCategoriaImovel.iterator();

					while(iteCategoriaImovel.hasNext()){
						Categoria categoria = (Categoria) iteCategoriaImovel.next();
						long fator = (consumoFaturadoMes - consumoMinimoMes) / quantidadeEconomiasImovel;
						Short quantidadeEconomias = this.repositorioImovel.pesquisarObterQuantidadeEconomias(imovel, categoria);
						long consumoImovelCategoria = categoria.getConsumoMinimo() + fator * quantidadeEconomias;
						consumosImovelporCategoria.add(consumoImovelCategoria);
						quantidadeEconomiasImovelporCategoria.add(quantidadeEconomias);

						// Selecionamos a categoria principal
						if(categoriaPrincipal == null || quantidadeEconomias > quantidadeEconomiasAnterior){
							categoriaPrincipal = categoria;
						}

						quantidadeEconomiasAnterior = quantidadeEconomias;
					}

					// Variaveis para preenchimento dos Helper`s
					Integer idGerenciaRegional = (Integer) retorno[0];
					Integer idUnidadeNegocio = (Integer) retorno[1];
					Integer idElo = (Integer) retorno[2];
					Integer idLocalidade = (Integer) retorno[3];
					Integer idSetorCormecial = (Integer) retorno[4];
					Integer codigoSetorComercial = (Integer) retorno[5];
					Integer idQuadra = (Integer) retorno[6];
					Integer numeroQuadra = (Integer) retorno[7];

					// Verificamos se o imovel possue categoria mista
					Integer idConsumoTarifa = (Integer) retorno[8];
					Integer idPerfilImovel = (Integer) retorno[9];
					// Pesquisamos a esfera de poder
					Integer idEsferaPoder = this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel);

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(idEsferaPoder.equals(0)){
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							idEsferaPoder = clienteTemp.getClienteTipo().getEsferaPoder().getId();
						}
					}

					// Obtemos o percentual de esgoto
					BigDecimal percentualEsgoto = ((retorno[18]) == null ? new BigDecimal(0) : ((BigDecimal) retorno[18]));

					Integer idSituacaoAgua = (Integer) retorno[10];
					Integer idSituacaoEsgoto = (Integer) retorno[12];
					Integer idTipoLigacao = (Integer) retorno[14];

					// Verificamos o consumo real
					// Integer idConsumoReal = (((Integer) retorno[17]).equals(1) ? 1 : 2);
					Integer idConsumoReal = this.repositorioGerencialCadastro.verificarConsumoReal(idImovel);

					// Verificamos a existencia de hidrï¿½metro
					Integer idHidrometro = this.repositorioGerencialCadastro.verificarExistenciaHidrometro(idImovel);
					// Verificamos a existencia de poco no imovel
					Integer idPoco = (retorno[16] != null && !((Integer) retorno[16]).equals(0) ? 1 : 2);
					Integer idVolumeFixoAgua = this.repositorioGerencialCadastro.verificarExistenciaVolumeFixoAgua(idImovel);
					Integer idVolumeFixoEsgoto = this.repositorioGerencialCadastro.verificarExistenciaVolumeFixoEsgoto(idImovel);

					// Valores a serem calculados
					Integer quantidadeLigacaoAgua = 0;
					Integer quantidadeEconomiaLigacaoAgua = 0;
					Short quantidadeEconomiaLigacaoEsgoto = 0;
					Integer quantidadeEconomiaAgua = 0;
					Integer quantidadeEconomiaEsgoto = 0;
					Integer volumeFaturadoLigacaoAgua = 0;
					Long volumeFaturadoEconomiaAgua = 0l;
					Integer quantidadeLigacaoEsgoto = 0;
					Integer volumeFaturadoLigacaoEsgoto = 0;
					Long volumeFaturadoEconomiaEsgoto = 0l;
					Integer quantidadeConsumoAgua = 0;
					Integer quantidadeConsumoEsgoto = 0;
					Integer index = 0;

					iteCategoriaImovel = colCategoriaImovel.iterator();

					while(iteCategoriaImovel.hasNext()){
						// Verificamos se quantidade de economias dessa conta ï¿½
						// maior do que a anterior
						Categoria categoria = (Categoria) iteCategoriaImovel.next();

						if(idTipoLigacao.equals(LigacaoTipo.LIGACAO_AGUA)){
							/** ***** FIM DADOS PARA A TABELA HISTOGRAMA AGUA LIGACAO ******* **/
							quantidadeLigacaoAgua++;
							quantidadeEconomiaLigacaoAgua += (Short) quantidadeEconomiasImovelporCategoria.get(index);

							// Obtemos o consumo faturado de agua
							// Se o consumo de agua for menor do que o minimo * as
							// economias
							// Devemos pegar o mï¿½nimo
							if((Long) consumosImovelporCategoria.get(index) < categoria.getConsumoMinimo()){
								volumeFaturadoLigacaoAgua += categoria.getConsumoMinimo();
							}else{
								volumeFaturadoLigacaoAgua += consumosImovelporCategoria.get(index).intValue();
							}

							/** ***** FIM DADOS PARA A TABELA HISTOGRAMA AGUA LIGACAO ******* **/
							/** **** DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA ****** **/
							quantidadeEconomiaAgua += (Short) quantidadeEconomiasImovelporCategoria.get(index);

							// Obtemos o consumo faturado de agua
							// Se o consumo de agua for menor do que o minimo * as
							// economias
							// Devemos pegar o mï¿½nimo
							if((Long) consumosImovelporCategoria.get(index) < categoria.getConsumoMinimo()){
								volumeFaturadoEconomiaAgua += categoria.getConsumoMinimo();
							}else{
								volumeFaturadoEconomiaAgua += consumosImovelporCategoria.get(index); // quantidadeEconomiasImovelporCategoria.get(
								// index
								// );
							}
							/** **** FIM DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA ****** **/
						}

						if(idTipoLigacao.equals(LigacaoTipo.LIGACAO_ESGOTO)){
							/** ***** DADOS PARA A TABELA HISTOGRAMA ESGOTO LIGACAO ****** **/
							quantidadeLigacaoEsgoto++;

							quantidadeEconomiaLigacaoEsgoto = (Short) quantidadeEconomiasImovelporCategoria.get(index);

							// Obtemos o consumo faturado de esgoto
							// Se o consumo de esgoto for menor do que o minimo * as
							// economias
							// Devemos pegar o mï¿½nimo
							if((Long) consumosImovelporCategoria.get(index) < categoria.getConsumoMinimo()){
								volumeFaturadoLigacaoEsgoto += categoria.getConsumoMinimo();
							}else{
								volumeFaturadoLigacaoEsgoto += ((Long) consumosImovelporCategoria.get(index)).intValue();
							}
							/** ***** FIM DADOS PARA A TABELA HISTOGRAMA ESGOTO LIGACAO ****** **/

							/** **** DADOS PARA A TABELA HISTOGRAMA ESGOTO ECONOMIA ****** **/
							quantidadeEconomiaEsgoto += (Short) quantidadeEconomiasImovelporCategoria.get(index);

							// Obtemos o consumo faturado de agua
							// Se o consumo de agua for menor do que o minimo * as
							// economias
							// Devemos pegar o mï¿½nimo
							if((Long) consumosImovelporCategoria.get(index) < categoria.getConsumoMinimo()){
								volumeFaturadoEconomiaEsgoto += categoria.getConsumoMinimo();
							}else{
								volumeFaturadoEconomiaEsgoto += consumosImovelporCategoria.get(index); // quantidadeEconomiasImovelporCategoria.get(
								// index
								// );
							}
							/** **** FIM DADOS PARA A TABELA HISTOGRAMA AGUA ECONOMIA ****** **/

						}
					}

					if(idTipoLigacao.equals(LigacaoTipo.LIGACAO_AGUA)){
						// Criamos um helper para os histograma de agua por ligaï¿½ï¿½o
						histogramaAguaLigacaoHelper = new HistogramaAguaLigacaoHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, categoriaPrincipal.getCategoriaTipo().getId(), categoriaPrincipal.getId(),
										(ligacaoMista != null) ? ligacaoMista.shortValue() : null, idConsumoTarifa, idPerfilImovel,
										idEsferaPoder, idSituacaoAgua, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null,
										quantidadeConsumoAgua);

						if(listaSimplificadaAguaLigacao.contains(histogramaAguaLigacaoHelper)){
							int posicao = listaSimplificadaAguaLigacao.indexOf(histogramaAguaLigacaoHelper);

							HistogramaAguaLigacaoHelper jaCadastrado = (HistogramaAguaLigacaoHelper) listaSimplificadaAguaLigacao
											.get(posicao);

							jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao() + quantidadeLigacaoAgua);

							jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
											+ quantidadeEconomiaLigacaoAgua);

							jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao() + volumeFaturadoLigacaoAgua);
						}else{
							histogramaAguaLigacaoHelper.setQuantidadeLigacao(quantidadeLigacaoAgua);

							histogramaAguaLigacaoHelper.setQuantidadeEconomiaLigacao(quantidadeEconomiaLigacaoAgua);

							histogramaAguaLigacaoHelper.setValorFaturadoLigacao(new BigDecimal(0));

							histogramaAguaLigacaoHelper.setVolumeFaturadoLigacao(volumeFaturadoLigacaoAgua);

							listaSimplificadaAguaLigacao.add(histogramaAguaLigacaoHelper);
						}

						// Criamos um helper para os histograma de agua por economia
						histogramaAguaEconomiaHelper = new HistogramaAguaEconomiaHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, categoriaPrincipal.getCategoriaTipo().getId(), categoriaPrincipal.getId(),
										idConsumoTarifa, idPerfilImovel, idEsferaPoder, idSituacaoAgua,
										(idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoAgua != null) ? idVolumeFixoAgua.shortValue() : null,
										quantidadeConsumoAgua, 1);

						if(listaSimplificadaAguaEconomia.contains(histogramaAguaEconomiaHelper)){
							int posicao = listaSimplificadaAguaEconomia.indexOf(histogramaAguaEconomiaHelper);

							HistogramaAguaEconomiaHelper jaCadastrado = (HistogramaAguaEconomiaHelper) listaSimplificadaAguaEconomia
											.get(posicao);

							jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia() + quantidadeEconomiaAgua);

							jaCadastrado.setVolumeFaturadoEconomia((Integer) jaCadastrado.getVolumeFaturadoEconomia()
											+ volumeFaturadoEconomiaAgua.intValue());
						}else{
							histogramaAguaEconomiaHelper.setQuantidadeEconomia(quantidadeEconomiaAgua);

							histogramaAguaEconomiaHelper.setValorFaturadoEconomia(new BigDecimal(0));

							histogramaAguaEconomiaHelper.setVolumeFaturadoEconomia(volumeFaturadoEconomiaAgua.intValue());

							listaSimplificadaAguaEconomia.add(histogramaAguaEconomiaHelper);
						}
					}

					if(idTipoLigacao.equals(LigacaoTipo.LIGACAO_ESGOTO)){
						// Criamos um helper para os histograma de Esgoto por ligaï¿½ï¿½o
						histogramaEsgotoLigacaoHelper = new HistogramaEsgotoLigacaoHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, categoriaPrincipal.getCategoriaTipo().getId(), categoriaPrincipal.getId(),
										(ligacaoMista != null) ? ligacaoMista.shortValue() : null, idConsumoTarifa, idPerfilImovel,
										idEsferaPoder, idSituacaoEsgoto, (idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto.shortValue() : null,
										(percentualEsgoto != null) ? percentualEsgoto.shortValue() : null, quantidadeConsumoEsgoto);

						if(listaSimplificadaEsgotoLigacao.contains(histogramaEsgotoLigacaoHelper)){
							int posicao = listaSimplificadaEsgotoLigacao.indexOf(histogramaEsgotoLigacaoHelper);

							HistogramaEsgotoLigacaoHelper jaCadastrado = (HistogramaEsgotoLigacaoHelper) listaSimplificadaEsgotoLigacao
											.get(posicao);

							jaCadastrado.setQuantidadeLigacao(jaCadastrado.getQuantidadeLigacao() + quantidadeLigacaoEsgoto);

							jaCadastrado.setQuantidadeEconomiaLigacao(jaCadastrado.getQuantidadeEconomiaLigacao()
											+ quantidadeEconomiaLigacaoEsgoto);

							jaCadastrado.setVolumeFaturadoLigacao(jaCadastrado.getVolumeFaturadoLigacao() + volumeFaturadoLigacaoEsgoto);
						}else{
							histogramaEsgotoLigacaoHelper.setQuantidadeLigacao(quantidadeLigacaoEsgoto);

							histogramaEsgotoLigacaoHelper.setQuantidadeEconomiaLigacao(quantidadeEconomiaLigacaoEsgoto.intValue());

							histogramaEsgotoLigacaoHelper.setValorFaturadoLigacao(new BigDecimal(0));

							histogramaEsgotoLigacaoHelper.setVolumeFaturadoLigacao(volumeFaturadoLigacaoEsgoto);

							listaSimplificadaEsgotoLigacao.add(histogramaEsgotoLigacaoHelper);
						}

						// Criamos um helper para os histograma de Esgoto por Economia
						histogramaEsgotoEconomiaHelper = new HistogramaEsgotoEconomiaHelper(anoMesReferencia, idGerenciaRegional,
										idUnidadeNegocio, idElo, idLocalidade, idSetorCormecial, codigoSetorComercial, idQuadra,
										numeroQuadra, categoriaPrincipal.getCategoriaTipo().getId(), categoriaPrincipal.getId(),
										idConsumoTarifa, idPerfilImovel, idEsferaPoder, idSituacaoEsgoto,
										(idConsumoReal != null) ? idConsumoReal.shortValue() : null,
										(idHidrometro != null) ? idHidrometro.shortValue() : null, (idPoco != null) ? idPoco.shortValue()
														: null, (idVolumeFixoEsgoto != null) ? idVolumeFixoEsgoto.shortValue() : null,
										(percentualEsgoto != null) ? percentualEsgoto.shortValue() : null, quantidadeConsumoEsgoto, 1);

						// Agrupamos o histograma esgoto por economia
						if(listaSimplificadaEsgotoEconomia.contains(histogramaEsgotoEconomiaHelper)){
							int posicao = listaSimplificadaEsgotoEconomia.indexOf(histogramaEsgotoEconomiaHelper);

							HistogramaEsgotoEconomiaHelper jaCadastrado = (HistogramaEsgotoEconomiaHelper) listaSimplificadaEsgotoEconomia
											.get(posicao);

							jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia() + quantidadeEconomiaEsgoto);

							jaCadastrado.setVolumeFaturadoEconomia(((Integer) jaCadastrado.getVolumeFaturadoEconomia() + volumeFaturadoEconomiaEsgoto
											.intValue()));

						}else{
							histogramaEsgotoEconomiaHelper.setQuantidadeEconomia(quantidadeEconomiaEsgoto);

							histogramaEsgotoEconomiaHelper.setValorFaturadoEconomia(new BigDecimal(0));

							histogramaEsgotoEconomiaHelper.setVolumeFaturadoEconomia(volumeFaturadoEconomiaEsgoto.intValue());

							listaSimplificadaEsgotoEconomia.add(histogramaEsgotoEconomiaHelper);
						}
					}
				}
			}

			for(int j = 0; j < listaSimplificadaAguaLigacao.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaAguaLigacao(anoMesReferencia,
								(HistogramaAguaLigacaoHelper) listaSimplificadaAguaLigacao.get(j));
			}

			for(int j = 0; j < listaSimplificadaAguaEconomia.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaAguaEconomima(anoMesReferencia,
								(HistogramaAguaEconomiaHelper) listaSimplificadaAguaEconomia.get(j));
			}

			for(int j = 0; j < listaSimplificadaEsgotoLigacao.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaEsgotoLigacao(anoMesReferencia,
								(HistogramaEsgotoLigacaoHelper) listaSimplificadaEsgotoLigacao.get(j));
			}

			for(int j = 0; j < listaSimplificadaEsgotoEconomia.size(); j++){
				this.repositorioGerencialCadastro.inserirHistogramaEsgotoEconomia(anoMesReferencia,
								(HistogramaEsgotoEconomiaHelper) listaSimplificadaEsgotoEconomia.get(j));
			}

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NO SETOR COMERCIAL " + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			throw new EJBException(ex);
		}
	}

	/**
	 * Mï¿½todo que gera o resumo das ligaï¿½ï¿½es e economias
	 * [UC0275] - Gerar Resumo das Ligaï¿½ï¿½es/Economias
	 * 
	 * @author Thiago Toscano, Bruno Barros
	 * @date 19/04/2006 17/04/2007
	 */
	public void gerarResumoLigacoesEconomias(int idSetor, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		try{

			List imoveisResumoLigacaoEconomias = this.repositorioGerencialCadastro.getImoveisResumoLigacaoEconomias(idSetor);

			List<ResumoLigacaoEconomiaHelper> listaSimplificada = new ArrayList();
			List<UnResumoLigacaoEconomia> listaResumoLigacoesEconomia = new ArrayList();

			Imovel imovelObterQtdEconomia = null;

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoLigacaoEconomias.size(); i++){

				Object obj = imoveisResumoLigacaoEconomias.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia = new Imovel();
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoLigacaoEconomiaHelper helper = new ResumoLigacaoEconomiaHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de negocio
									(Integer) retorno[3], // Localidade
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Id Setor Comercial
									(Integer) retorno[6], // id Rota
									(Integer) retorno[7], // Id Quadra
									(Integer) retorno[8], // Codigo do Setor Comercial
									(Integer) retorno[9], // Numero da quadra
									(Integer) retorno[10], // Perfil do imovel
									(Integer) retorno[11],// Situacao da ligacao da
									// agua
									(Integer) retorno[12],// Situacao da ligacao do
									// esgoto
									(Integer) retorno[13],// Perfil da ligacao de agua
									(Integer) retorno[14],// Perfil da ligacao de
									// esgoto
									(Integer) retorno[15],// Possue hidrometro
									// instalado ?
									(Integer) retorno[16],// Possue hidrometro
									// instalado no poco ?
									(Integer) retorno[17],// Possue volume minimo de
									// agua fixado ?
									(Integer) retorno[18],// Possue volume minimo de
									// esgoto fixado ?
									(Integer) retorno[19],// Possue poco
									(Integer) retorno[22]); // Tipo de Tarifa de Consumo

					Integer idImovel = (Integer) retorno[0]; // Codigo do
					// imovel que
					// esta sendo
					// processado

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsfera(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
									.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel

					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
										idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
						}
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(helper.getIdEsfera().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdEsfera(clienteTemp.getClienteTipo().getEsferaPoder().getId());
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

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoLigacaoEconomiaHelper jaCadastrado = (ResumoLigacaoEconomiaHelper) listaSimplificada.get(posicao);

						// Somamos as economias
						jaCadastrado.setQtdEconomias(jaCadastrado.getQtdEconomias().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						jaCadastrado.setQtdLigacoes(jaCadastrado.getQtdLigacoes() + 1);
					}else{
						// Somamos as economias
						helper.setQtdEconomias(helper.getQtdEconomias().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						helper.setQtdLigacoes(helper.getQtdLigacoes() + 1);

						listaSimplificada.add(helper);
					}
				}
			}

			// /**
			// * para todas as ImovelResumoLigacaoEconomiaHelper cria
			// * ResumoLigacoesEconomia
			// */
			for(int i = 0; i < listaSimplificada.size(); i++){
				ResumoLigacaoEconomiaHelper helper = (ResumoLigacaoEconomiaHelper) listaSimplificada.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(helper.getIdTipoClienteResponsavel());
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Indicador de hidrometro
				Short indicadorHidrometro = null;
				if(helper.getIdHidrometro() != null){
					indicadorHidrometro = helper.getIdHidrometro().shortValue();
				}

				// Indicador de hidrometro no poco
				Short indicadorHidrometroPoco = null;
				if(helper.getIdHidrometroPoco() != null){
					indicadorHidrometroPoco = helper.getIdHidrometroPoco().shortValue();
				}

				// Indicador de volume minimo de agua fixado
				Short indicadorVolumeFixadoAgua = null;
				if(helper.getIdVolFixadoAgua() != null){
					indicadorVolumeFixadoAgua = helper.getIdVolFixadoAgua().shortValue();
				}

				// Indicador de volume minimo de esgoto fixado
				Short indicadorVolumeFixadoEsgoto = null;
				if(helper.getIdVolFixadoEsgoto() != null){
					indicadorVolumeFixadoEsgoto = helper.getIdVolFixadoEsgoto().shortValue();
				}

				// Indicador de poco
				Short indicadorPoco = null;
				if(helper.getIdPoco() != null){
					indicadorPoco = helper.getIdPoco().shortValue();
				}

				// Tipo de Tarifa de Consumo
				GConsumoTarifa consumoTarifa = null;
				if(helper.getIdTipoTarifaConsumo() != null){
					consumoTarifa = new GConsumoTarifa();
					consumoTarifa.setId(helper.getIdTipoTarifaConsumo());
				}

				// Quantidade ligacoes
				Integer qtdLigacoes = helper.getQtdLigacoes();

				// Quantidade economias
				Integer qtdEconomias = helper.getQtdEconomias();

				// Criamos um resumo de ligacao economia (***UFA***)
				UnResumoLigacaoEconomia resumoLigacoesEconomia = new UnResumoLigacaoEconomia(anoMesReferencia, gerenciaRegional,
								unidadeNegocio, localidade, elo, setorComercial, rota, quadra, codigoSetorComercial, numeroQuadra,
								imovelPerfil, esferaPoder, clienteTipo, ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria,
								subcategoria, perfilLigacaoAgua, perfilLigacaoEsgoto, indicadorHidrometro, indicadorHidrometroPoco,
								indicadorVolumeFixadoAgua, indicadorVolumeFixadoEsgoto, indicadorPoco, consumoTarifa, qtdLigacoes,
								qtdEconomias);

				// Adicionamos a lista que deve ser inserida
				listaResumoLigacoesEconomia.add(resumoLigacoesEconomia);
			}

			this.getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoLigacoesEconomia);

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido

			System.out.println(" ERRO NO SETOR" + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * Mï¿½todo que gera o resumo das ligaï¿½ï¿½es e economias
	 * [UC0275] - Gerar Resumo das Ligaï¿½ï¿½es/Economias Regiao
	 * 
	 * @author Ivan Sï¿½rgio
	 * @date 20/04/2007
	 */
	public void gerarResumoLigacaoEconomiaRegiao(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idLocalidade);

		try{
			List imoveisResumoLigacaoEconomiaRegiao = this.repositorioGerencialCadastro.getImoveisResumoLigacaoEconomiaRegiao(idLocalidade);

			List<ResumoLigacaoEconomiaRegiaoHelper> listaSimplificada = new ArrayList();
			List<ResumoLigacaoEconomiaRegiao> listaResumoLigacaoEconomiaRegiao = new ArrayList();

			Imovel imovelObterQtdEconomia = null;

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoLigacaoEconomiaRegiao.size(); i++){
				Object obj = imoveisResumoLigacaoEconomiaRegiao.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoLigacaoEconomiaRegiaoHelper helper = new ResumoLigacaoEconomiaRegiaoHelper((Integer) retorno[1], // Regiao
									(Integer) retorno[2], // Microrregiao
									(Integer) retorno[3], // Municipio
									(Integer) retorno[4], // Bairro
									(Integer) retorno[5], // Perfil Imovel
									(Integer) retorno[6], // Ligacao Agua Situacao
									(Integer) retorno[7], // Ligacao Esgoto Situacao
									(Integer) retorno[8], // Esfera de Poder do
									// Cliente
									(Integer) retorno[9], // Tipo de Cliente do
									// Cliente Responsavel
									(Integer) retorno[10], // Perfil da Ligacao da Agua
									(Integer) retorno[11], // Perfil da Ligacao do
									// Esgoto
									(Integer) retorno[12], // Possue hidrometro
									// instalado ?
									(Integer) retorno[13], // Possue hidrometro
									// instalado no poco ?
									(Integer) retorno[14], // Possue volume minimo de
									// agua fixado ?
									(Integer) retorno[15], // Possue volume minimo de
									// esgoto fixado ?
									(Integer) retorno[16]);// Possue poco ?

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Integer idImovel = (Integer) retorno[0]; // Codigo do
					// imovel que
					// esta sendo
					// processado
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

					if(categoria != null){
						helper.setIdPrincipalCategoriaImovel(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
										idImovel);

						if(subcategoria != null){
							helper.setIdPrincipalSubCategoriaImovel(subcategoria.getComp_id().getSubcategoria().getId());
						}
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(helper.getIdEsferaCliente().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdEsferaCliente(clienteTemp.getClienteTipo().getEsferaPoder().getId());
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

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacoes iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoLigacaoEconomiaRegiaoHelper jaCadastrado = (ResumoLigacaoEconomiaRegiaoHelper) listaSimplificada.get(posicao);

						// Somamos as economias
						jaCadastrado.setQuantidadeEconomia(jaCadastrado.getQuantidadeEconomia().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + 1);

					}else{
						// Somamos as economias
						helper.setQuantidadeEconomia(helper.getQuantidadeEconomia().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						helper.setQuantidadeLigacoes(helper.getQuantidadeLigacoes() + 1);

						listaSimplificada.add(helper);
					}
				}
			}

			/**
			 * para todas as ImovelResumoLigacaoEconomiaRegiaoHelper cria
			 * ResumoLigacoesEconomiaRegiao
			 */
			for(int i = 0; i < listaSimplificada.size(); i++){
				ResumoLigacaoEconomiaRegiaoHelper helper = (ResumoLigacaoEconomiaRegiaoHelper) listaSimplificada.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = Util.getAnoMesComoInteger(new Date());

				// Regiao
				Regiao regiao = null;
				if(helper.getIdRegiao() != null){
					regiao = new Regiao();
					regiao.setId(helper.getIdRegiao());
				}

				// Microrregiao
				Microrregiao microrregiao = null;
				if(helper.getIdMicrorregiao() != null){
					microrregiao = new Microrregiao();
					microrregiao.setId(helper.getIdMicrorregiao());
				}

				// Municipio
				Municipio municipio = null;
				if(helper.getIdMunicipio() != null){
					municipio = new Municipio();
					municipio.setId(helper.getIdMunicipio());
				}

				// Bairro
				Bairro bairro = null;
				if(helper.getIdBairro() != null){
					bairro = new Bairro();
					bairro.setId(helper.getIdBairro());
				}

				// Perfil do imovel
				ImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new ImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Situacao da ligacao de agua
				LigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new LigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Principal Categoria do Imovel
				Categoria categoria = null;
				if(helper.getIdPrincipalCategoriaImovel() != null){
					categoria = new Categoria();
					categoria.setId(helper.getIdPrincipalCategoriaImovel());
				}

				// Principal Sub Categoria do Imovel
				Subcategoria subcategoria = null;
				if(helper.getIdPrincipalSubCategoriaImovel() != null){
					subcategoria = new Subcategoria();
					subcategoria.setId(helper.getIdPrincipalSubCategoriaImovel());
				}

				// Esfera de poder do cliente responsavel
				EsferaPoder esferaPoder = null;
				if(helper.getIdEsferaCliente() != null){
					esferaPoder = new EsferaPoder();
					esferaPoder.setId(helper.getIdEsferaCliente());
				}

				// Tipo do cliente responsavel
				ClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new ClienteTipo();
					clienteTipo.setId(helper.getIdTipoClienteResponsavel());
				}

				// Perfil da ligacao de agua
				LigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new LigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				LigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new LigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Indicador de hidrometro
				Short indicadorHidrometro = null;
				if(helper.getIdIndicadorHidrometro() != null){
					indicadorHidrometro = helper.getIdIndicadorHidrometro().shortValue();
				}

				// Indicador de hidrometro no poco
				Short indicadorHidrometroPoco = null;
				if(helper.getIdIndicadorHidrometroPoco() != null){
					indicadorHidrometroPoco = helper.getIdIndicadorHidrometroPoco().shortValue();
				}

				// Indicador de volume minimo de agua fixado
				Short indicadorVolumeFixadoAgua = null;
				if(helper.getIdIndicadorVolumeMinimoAguaFixado() != null){
					indicadorVolumeFixadoAgua = helper.getIdIndicadorVolumeMinimoAguaFixado().shortValue();
				}

				// Indicador de volume minimo de esgoto fixado
				Short indicadorVolumeFixadoEsgoto = null;
				if(helper.getIdIndicadorVolumeMinimoEsgotoFixado() != null){
					indicadorVolumeFixadoEsgoto = helper.getIdIndicadorVolumeMinimoEsgotoFixado().shortValue();
				}

				// Indicador de poco
				Short indicadorPoco = null;
				if(helper.getIdIndicadorPoco() != null){
					indicadorPoco = helper.getIdIndicadorPoco().shortValue();
				}

				// Quantidade ligacoes
				Integer qtdLigacoes = helper.getQuantidadeLigacoes();

				// Quantidade economias
				Integer qtdEconomias = helper.getQuantidadeEconomia();

				// Criamos um resumo de ligacao economia regiao
				ResumoLigacaoEconomiaRegiao resumoLigacoesEconomiaRegiao = new ResumoLigacaoEconomiaRegiao(anoMesReferencia, regiao,
								microrregiao, municipio, bairro, imovelPerfil, ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria,
								subcategoria, esferaPoder, clienteTipo, perfilLigacaoAgua, perfilLigacaoEsgoto, indicadorHidrometro,
								indicadorHidrometroPoco, indicadorVolumeFixadoAgua, indicadorVolumeFixadoEsgoto, indicadorPoco,
								qtdLigacoes, qtdEconomias);

				// Adicionamos a lista que deve ser inserida
				listaResumoLigacaoEconomiaRegiao.add(resumoLigacoesEconomiaRegiao);
			}

			// this.repositorioGerencialCadastro
			// .inserirResumoLigacaoEconomiaRegiaoBatch(listaResumoLigacaoEconomiaRegiao);

			this.getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoLigacaoEconomiaRegiao);

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}// gerarResumoLigacaoEconomiaRegiao

	/**
	 * Mï¿½todo que gera o resumo do consumo de agua
	 * [UC0570] - Gerar Resumo do consumo de agua
	 * 
	 * @author Bruno Barros
	 * @date 23/04/2007
	 */
	public void gerarResumoConsumoAgua(int idSetor, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		Integer anoMesReferencia = null;

		try{

			List imoveisResumoConsumoAgua = this.repositorioGerencialCadastro.getHistoricosConsumoAgua(idSetor);

			List<ResumoConsumoAguaHelper> listaSimplificada = new ArrayList();
			List<UnResumoConsumoAgua> listaResumoConsumoAgua = new ArrayList();

			Imovel imovelObterQtdEconomia = null;

			// FS0001 - Verificar existencia de dados para o ano/mes referencia informado
			repositorioGerencialCadastro.excluirResumoGerencial(getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento(),
							"micromedicao.un_resumo_consumo_agua", "reca_amreferencia", idSetor);

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoConsumoAgua.size(); i++){
				Object obj = imoveisResumoConsumoAgua.get(i);

				// System.out.println( i + " de " + imoveisResumoConsumoAgua.size() + " do Setor " +
				// idSetor );

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia = new Imovel();
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoConsumoAguaHelper helper = new ResumoConsumoAguaHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de negocio
									(Integer) retorno[3], // Localidade
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Id Setor Comercial
									(Integer) retorno[6], // id Rota
									(Integer) retorno[7], // Id Quadra
									(Integer) retorno[8], // Codigo do Setor Comercial
									(Integer) retorno[9], // Numero da quadra
									(Integer) retorno[10],// Perfil do imovel
									(Integer) retorno[11],// Situacao da ligacao da
									// agua
									(Integer) retorno[12],// Situacao da ligacao do
									// esgoto
									(Integer) retorno[13],// Perfil da ligacao de agua
									(Integer) retorno[14],// Perfil da ligacao de
									// esgoto
									(Integer) retorno[15],// Consumo tipo
									(Integer) retorno[16] // Consumo de Agua
					);

					Integer idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo
					// processado

					// [UC0307] - Obter Indicador de Existï¿½ncia de Hidrï¿½metro
					String indicadorHidrometroString = new Integer(getControladorImovel()
									.obterIndicadorExistenciaHidrometroImovel(idImovel)).toString();
					Integer indicadorHidrometro = new Integer(indicadorHidrometroString);
					// Caso indicador de hidrï¿½metro esteja nulo
					// Seta 2(dois) = Nï¿½O no indicador de
					// hidrï¿½metro
					if(indicadorHidrometro == null){
						indicadorHidrometro = 2;
					}
					helper.setIdHidrometro(indicadorHidrometro);

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdClienteTipo(this.repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
										idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
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
					if(helper.getIdClienteTipo().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdClienteTipo(clienteTemp.getClienteTipo().getId());
						}
					}

					// Calculamos o consumo excedente
					// [UC0105] - Obter consumo minimo ligacao
					// Valor minimo de consumo de agua
					anoMesReferencia = (Integer) retorno[18];
					Imovel imovelTemp = new Imovel();
					imovelTemp.setId(idImovel);
					imovelTemp = this.getControladorImovel().pesquisarImovel(imovelTemp.getId());

					Integer valorMinimoAgua = getControladorMicromedicao().obterConsumoMinimoLigacao(imovelTemp, null);

					// Setamos o valor do excedente
					helper.setQuantidadeConsumoAguaExcedente(helper.getQuantidadeConsumoAgua() - valorMinimoAgua);

					// Caso o valor excedente seja negativo, seramos, pois nao
					// houve excedente.
					if(helper.getQuantidadeConsumoAguaExcedente() <= 0){
						helper.setQuantidadeConsumoAguaExcedente(0);
					}

					// Guardamos o volume faturado, caso este seja maior que o mínimo
					if(helper.getQuantidadeConsumoAgua() > valorMinimoAgua){
						helper.setVolumeFaturado(helper.getQuantidadeConsumoAgua());
					}else{
						helper.setVolumeFaturado(valorMinimoAgua);
					}

					// Marcamos o helper como possuindo ou nï¿½o consumo excedente
					helper.setIdVolumeExcedente(helper.getQuantidadeConsumoAguaExcedente() > 0 ? 1 : 2);

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoConsumoAguaHelper jaCadastrado = (ResumoConsumoAguaHelper) listaSimplificada.get(posicao);

						// Somamos as economias
						jaCadastrado.setQuantidadeEconomias(jaCadastrado.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + 1);

						// Acumulamos o consumo de agua
						jaCadastrado.setQuantidadeConsumoAgua(jaCadastrado.getQuantidadeConsumoAgua().intValue()
										+ helper.getQuantidadeConsumoAgua());

						// Acumulamos o consumo Excedente
						jaCadastrado.setQuantidadeConsumoAguaExcedente(jaCadastrado.getQuantidadeConsumoAguaExcedente()
										+ helper.getQuantidadeConsumoAguaExcedente());
					}else{
						// Somamos as economias
						helper.setQuantidadeEconomias(helper.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						helper.setQuantidadeLigacoes(helper.getQuantidadeLigacoes() + 1);

						listaSimplificada.add(helper);
					}
				}
			}

			for(int i = 0; i < listaSimplificada.size(); i++){
				this.repositorioGerencialCadastro.inserirResumoConsumoAgua(anoMesReferencia,
								(ResumoConsumoAguaHelper) listaSimplificada.get(i));
			}

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NA LOCALIDADE " + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	public Integer consultarQtdRegistrosResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper dadosGeracaoRelatorioConsultaHelper) throws ControladorException{

		return repositorioGerencialCadastro.consultarQtdRegistrosResumoLigacoesEconomias(dadosGeracaoRelatorioConsultaHelper);
	}

	/**
	 * [UC0344] Consultar Resumo Anormalidade
	 * 
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List<ResumoLigacaoEconomiaConsultarHelper> consultarResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException{

		// [FS0001] Verificar existï¿½ncia de dados para o ano/mï¿½s de
		// referï¿½ncia retornado

		// if( countResumoPendencia == null || countResumoPendencia == 0 ){
		// throw new ControladorException(
		// "atencao.nao_existe_resumo_ligacoes_economia", null,
		// Util.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));
		// }
		List<ResumoLigacaoEconomiaConsultarHelper> colecaoLigacaoEconomiaHelper;
		try{
			List retorno = this.repositorioGerencialCadastro.consultarResumoLigacoesEconomias(informarDadosGeracaoRelatorioConsultaHelper);

			// [FS0007] Nenhum registro encontrado
			if(retorno == null || retorno.equals("") || retorno.isEmpty()){
				throw new ControladorException("atencao.pesquisa.nenhumresultado");
			}
			colecaoLigacaoEconomiaHelper = formatarColecaoRetornoResumoLigacoesEconomia(informarDadosGeracaoRelatorioConsultaHelper,
							retorno, true);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoLigacaoEconomiaHelper;

	}

	private List<ResumoLigacaoEconomiaConsultarHelper> formatarColecaoRetornoResumoLigacoesEconomia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper, List retorno,
					boolean formatacaoOn){

		Object[] objetoResumoLigacaoEconomia = null;
		Iterator iteratorResumoLigacaoEconomia = null;
		ResumoLigacaoEconomiaConsultarHelper resumoLigacaoEconomiaConsultarHelper = null;
		ResumoLigacaoEconomiaConsultarHelper resumoLigacaoEconomiaConsultarHelperAnterior = null;
		List<ResumoLigacaoEconomiaConsultarHelper> colecaoLigacaoEconomiaHelper = new ArrayList<ResumoLigacaoEconomiaConsultarHelper>();

		boolean primeiraVez = true;
		// Monta os objetos e carrega uma colecao para mandar para a proxima
		// camada
		iteratorResumoLigacaoEconomia = retorno.iterator();
		while(iteratorResumoLigacaoEconomia.hasNext()){
			objetoResumoLigacaoEconomia = (Object[]) iteratorResumoLigacaoEconomia.next();
			resumoLigacaoEconomiaConsultarHelper = new ResumoLigacaoEconomiaConsultarHelper();

			resumoLigacaoEconomiaConsultarHelper.setAnoMesReferncia(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()
							.toString());
			resumoLigacaoEconomiaConsultarHelper.setDescricaoEstado((String) objetoResumoLigacaoEconomia[0]);
			resumoLigacaoEconomiaConsultarHelper.setIdGerenciaRegional(objetoResumoLigacaoEconomia[1] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[1]);
			resumoLigacaoEconomiaConsultarHelper.setDescricaoGerenciaRegional((String) objetoResumoLigacaoEconomia[2]);
			resumoLigacaoEconomiaConsultarHelper.setIdElo((Integer) objetoResumoLigacaoEconomia[3] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[3]);
			resumoLigacaoEconomiaConsultarHelper.setDescricaoElo((String) objetoResumoLigacaoEconomia[4]);
			resumoLigacaoEconomiaConsultarHelper.setIdLocalidade((Integer) objetoResumoLigacaoEconomia[5] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[5]);
			resumoLigacaoEconomiaConsultarHelper.setDescricaoLocalidade((String) objetoResumoLigacaoEconomia[6]);
			resumoLigacaoEconomiaConsultarHelper.setIdSetorComercial((Integer) objetoResumoLigacaoEconomia[7] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[7]);
			resumoLigacaoEconomiaConsultarHelper.setDescricaoSetorComercial((String) objetoResumoLigacaoEconomia[8]);
			resumoLigacaoEconomiaConsultarHelper.setIdQuadra((Integer) objetoResumoLigacaoEconomia[9] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[9]);
			resumoLigacaoEconomiaConsultarHelper.setNumeroQuadra((String) objetoResumoLigacaoEconomia[10]);

			resumoLigacaoEconomiaConsultarHelper.setIdSituacaoLigacaoAgua((Integer) objetoResumoLigacaoEconomia[11] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[11]);
			resumoLigacaoEconomiaConsultarHelper.setIdSituacaoLigacaoEsgoto((Integer) objetoResumoLigacaoEconomia[13] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[13]);
			resumoLigacaoEconomiaConsultarHelper.setIdCategoria((Integer) objetoResumoLigacaoEconomia[15] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[15]);

			// JCSJ >>> Tirar Comentario - BEGIN<<<

			if(!formatacaoOn || primeiraVez){
				primeiraVez = false;
				resumoLigacaoEconomiaConsultarHelper.setDescricaoSituacaoLigacaoAgua((String) objetoResumoLigacaoEconomia[12]);
				resumoLigacaoEconomiaConsultarHelper.setDescricaoSituacaoLigacaoEsgoto((String) objetoResumoLigacaoEconomia[14]);
				resumoLigacaoEconomiaConsultarHelper.setDescricaoCategoria((String) objetoResumoLigacaoEconomia[16]);

			}else{

				if(!resumoLigacaoEconomiaConsultarHelperAnterior.getIdSituacaoLigacaoAgua().equals(
								resumoLigacaoEconomiaConsultarHelper.getIdSituacaoLigacaoAgua())){
					resumoLigacaoEconomiaConsultarHelper.setDescricaoSituacaoLigacaoAgua((String) objetoResumoLigacaoEconomia[12]);
					resumoLigacaoEconomiaConsultarHelper.setDescricaoSituacaoLigacaoEsgoto((String) objetoResumoLigacaoEconomia[14]);
					resumoLigacaoEconomiaConsultarHelper.setDescricaoCategoria((String) objetoResumoLigacaoEconomia[16]);
				}
				if(!resumoLigacaoEconomiaConsultarHelperAnterior.getIdSituacaoLigacaoEsgoto().equals(
								resumoLigacaoEconomiaConsultarHelper.getIdSituacaoLigacaoEsgoto())){
					resumoLigacaoEconomiaConsultarHelper.setDescricaoSituacaoLigacaoEsgoto((String) objetoResumoLigacaoEconomia[14]);
					resumoLigacaoEconomiaConsultarHelper.setDescricaoCategoria((String) objetoResumoLigacaoEconomia[16]);
				}
				if(!resumoLigacaoEconomiaConsultarHelperAnterior.getIdCategoria().equals(
								resumoLigacaoEconomiaConsultarHelper.getIdCategoria())){
					resumoLigacaoEconomiaConsultarHelper.setDescricaoCategoria((String) objetoResumoLigacaoEconomia[16]);
				}
			}

			// JCSJ >>> Tirar Comentario - END<<<

			resumoLigacaoEconomiaConsultarHelper
							.setQuantidadeLigacoesComHidrometro((Integer) objetoResumoLigacaoEconomia[17] == null ? null
											: (Integer) objetoResumoLigacaoEconomia[17]);
			resumoLigacaoEconomiaConsultarHelper
							.setQuantidadeLigacoesSemHidrometro((Integer) objetoResumoLigacaoEconomia[18] == null ? null
											: (Integer) objetoResumoLigacaoEconomia[18]);
			resumoLigacaoEconomiaConsultarHelper.setTotalLigacoes((Integer) objetoResumoLigacaoEconomia[19] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[19]);
			resumoLigacaoEconomiaConsultarHelper
							.setQuantidadeEconomiaComHidrometro((Integer) objetoResumoLigacaoEconomia[20] == null ? null
											: (Integer) objetoResumoLigacaoEconomia[20]);
			resumoLigacaoEconomiaConsultarHelper
							.setQuantidadeEconomiaSemHidrometro((Integer) objetoResumoLigacaoEconomia[21] == null ? null
											: (Integer) objetoResumoLigacaoEconomia[21]);
			resumoLigacaoEconomiaConsultarHelper.setTotalEconomia((Integer) objetoResumoLigacaoEconomia[22] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[22]);

			resumoLigacaoEconomiaConsultarHelper.setIdUnidadeNegocio((Integer) objetoResumoLigacaoEconomia[23] == null ? null
							: (Integer) objetoResumoLigacaoEconomia[23]);

			resumoLigacaoEconomiaConsultarHelper.setDescricaoUnidadeNegocio((String) objetoResumoLigacaoEconomia[24] == null ? null
							: (String) objetoResumoLigacaoEconomia[24]);

			colecaoLigacaoEconomiaHelper.add(resumoLigacaoEconomiaConsultarHelper);
			resumoLigacaoEconomiaConsultarHelperAnterior = resumoLigacaoEconomiaConsultarHelper;
		}
		return colecaoLigacaoEconomiaHelper;
	}

	/**
	 * Retorna o valor do ControladorMicromedicao
	 * 
	 * @author Bruno Barros
	 * @date 23/04/2007
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
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
	 * Mï¿½todo que gera o resumo do Parcelamento
	 * [UC0565] - Gerar Resumo do Parcelamento
	 * 
	 * @author Marcio Roberto
	 * @date 04/05/2007
	 */
	public void gerarResumoParcelamento(int idLocalidade, int idFuncionalidadeIniciada, int anoMes) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idLocalidade);

		try{

			List imoveisResumoParcelamento = this.repositorioGerencialCadastro.getImoveisResumoParcelamento(idLocalidade, anoMes);

			List<ResumoParcelamentoHelper> listaSimplificadaParcelamento = new ArrayList();
			List<UnResumoParcelamento> listaResumoParcelamento = new ArrayList();

			UnResumoParcelamento resumoParcelamento = null;

			// caso ja tenha um igual soma as quantidades
			for(int i = 0; i < imoveisResumoParcelamento.size(); i++){

				Object obj = imoveisResumoParcelamento.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades do imovel corrente
					resumoParcelamento = new UnResumoParcelamento();
					resumoParcelamento.setId((Integer) retorno[0]);

					System.out.println("processando Localidade :: " + idLocalidade + " :: Imovel >> " + (Integer) retorno[1] + " <<");

					// Contas
					Integer quantidadeContas = this.repositorioGerencialCadastro.pesquisarObterQuantidadeContas((Integer) retorno[0]);
					if(quantidadeContas == null){
						quantidadeContas = 0;
					}

					// Guias
					Integer quantidadeGuias = this.repositorioGerencialCadastro.pesquisarObterQuantidadeGuias((Integer) retorno[0]);
					if(quantidadeGuias == null){
						quantidadeGuias = 0;
					}

					// Quantidade Servicos Indiretos
					Short quantidadeServicosIndiretos = this.repositorioGerencialCadastro
									.pesquisarObterQuantidadeServicosIndiretos((Integer) retorno[0]);
					if(quantidadeServicosIndiretos == null){
						quantidadeServicosIndiretos = 0;
					}

					// Valor das Contas
					BigDecimal valorContas = (BigDecimal) retorno[16];
					if(valorContas == null){
						valorContas = new BigDecimal(0);
					}

					// Valor Guias Pagmto
					BigDecimal valorGuias = (BigDecimal) retorno[17];
					if(valorGuias == null){
						valorGuias = new BigDecimal(0);
					}

					// Valor Servicos Indiretos
					BigDecimal valorServicosIndiretos = this.repositorioGerencialCadastro.pesquisarObterValorServicosIndiretos(
									(Integer) retorno[0], " debitoacobrar.lancamentoItemContabil.id not in (2,3)");
					if(valorServicosIndiretos == null){
						valorServicosIndiretos = new BigDecimal(0);
					}

					// Valor creditos
					BigDecimal valorCreditos = (BigDecimal) retorno[18];
					if(valorCreditos == null){
						valorCreditos = new BigDecimal(0);
					}

					// Valor Servicos Indiretos
					BigDecimal valorAcrescimoImpontualidade = (BigDecimal) retorno[25];
					if(valorAcrescimoImpontualidade == null){
						valorAcrescimoImpontualidade = new BigDecimal(0);
					}

					if(valorAcrescimoImpontualidade == null){
						valorAcrescimoImpontualidade = new BigDecimal(0);
					}

					// Valor Sancoes
					BigDecimal valorSancoes = this.repositorioGerencialCadastro.pesquisarObterValorServicosIndiretos((Integer) retorno[0],
									" debitoacobrar.lancamentoItemContabil.id = 3");
					if(valorSancoes == null){
						valorSancoes = new BigDecimal(0);
					}

					// Valor creditos
					BigDecimal valorDescontoAcrescimo = (BigDecimal) retorno[19];
					if(valorDescontoAcrescimo == null){
						valorDescontoAcrescimo = new BigDecimal(0);
					}

					// Valor Desconto Inatividade
					BigDecimal valorDescontoInatividade = (BigDecimal) retorno[20];
					if(valorDescontoInatividade == null){
						valorDescontoInatividade = new BigDecimal(0);
					}

					// Valor Desconto Antiguidade
					BigDecimal valorDescontoAntiguidade = (BigDecimal) retorno[21];
					if(valorDescontoAntiguidade == null){
						valorDescontoAntiguidade = new BigDecimal(0);
					}

					// Valor total Parcelamento
					BigDecimal valorTotalParcelamento = (BigDecimal) retorno[22];
					if(valorTotalParcelamento == null){
						valorTotalParcelamento = new BigDecimal(0);
					}
					// Ano Mes Referencia
					Integer anoMesRef = (Integer) retorno[23];

					// valor debito a cobrar Total
					BigDecimal valorDebitoACobrarTotal = (BigDecimal) retorno[24];
					if(valorDebitoACobrarTotal == null){
						valorDebitoACobrarTotal = new BigDecimal(0);
					}
					// valor debito a cobrar Acrescimos
					BigDecimal valorDebitoACobrarAcrescimos = this.repositorioGerencialCadastro.pesquisarObterValorServicosIndiretos(
									(Integer) retorno[0], " debitoacobrar.lancamentoItemContabil.id = 2");
					if(valorDebitoACobrarAcrescimos == null){
						valorDebitoACobrarAcrescimos = new BigDecimal(0);
					}

					// valor debito a cobrar Acrescimos
					BigDecimal valorDebitoACobrarReligSancoes = new BigDecimal(0); // (BigDecimal)
					// retorno[0];
					if(valorDebitoACobrarReligSancoes == null){
						valorDebitoACobrarReligSancoes = new BigDecimal(0);
					}
					// valor debitos a cobrar parcelamentos
					BigDecimal valorDebitoACobrarParcelamentos = (BigDecimal) retorno[26];
					if(valorDebitoACobrarParcelamentos == null){
						valorDebitoACobrarParcelamentos = new BigDecimal(0);
					}
					// valor entrada
					BigDecimal valorEntrada = (BigDecimal) retorno[27];
					if(valorEntrada == null){
						valorEntrada = new BigDecimal(0);
					}
					// valor juros parcelamento
					BigDecimal valorJurosParcelamento = (BigDecimal) retorno[28];
					if(valorJurosParcelamento == null){
						valorJurosParcelamento = new BigDecimal(0);
					}
					// quantidade total de parcelas
					Short quantidadeTotalParcelas = (Short) retorno[29];
					if(quantidadeTotalParcelas == null){
						quantidadeTotalParcelas = 0;
					}

					Integer consumoTarifa = (Integer) retorno[30];
					if(consumoTarifa == null){
						consumoTarifa = 0;
					}

					Short numeroParcelamentoConsecutivos = (Short) retorno[31];
					if(numeroParcelamentoConsecutivos == null){
						numeroParcelamentoConsecutivos = new Short("0");
					}

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Integer idImovel = (Integer) retorno[1]; // Codigo do

					// [UC0307] - Obter Indicador de Existï¿½ncia de Hidrï¿½metro
					String indicadorHidrometroString = new Integer(getControladorImovel()
									.obterIndicadorExistenciaHidrometroImovel(idImovel)).toString();
					Short indicadorHidrometro = new Short(indicadorHidrometroString);
					// Caso indicador de hidrï¿½metro esteja nulo
					// Seta 2(dois) = Nï¿½O no indicador de
					// hidrï¿½metro
					if(indicadorHidrometro == null){
						indicadorHidrometro = new Short("2");
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno

					ResumoParcelamentoHelper helper = new ResumoParcelamentoHelper((Integer) retorno[2], // Gerencia
									// Regional
									(Integer) retorno[3], // Unidade de negocio
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Localidade
									(Integer) retorno[6], // Id Setor Comercial
									(Integer) retorno[7], // id Rota
									(Integer) retorno[8], // Id Quadra
									(Integer) retorno[9], // Codigo do Setor Comercial
									(Integer) retorno[10], // Numero da quadra
									(Integer) retorno[11], // Perfil do imovel
									(Integer) retorno[12], // Situacao da ligacao da
									// agua
									(Integer) retorno[13], // Situacao da ligacao do
									// esgoto
									(Integer) retorno[14], // Perfil ligacao agua
									(Integer) retorno[15], // Perfil ligacao esgoto
									(Integer) retorno[30], // Tarifa Consumo
									indicadorHidrometro, // Indicador Hidrometro
									numeroParcelamentoConsecutivos); // Numero Parcelamentos
					// Consecutivos

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsfera(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdTipoClienteResponsavel(this.repositorioGerencialCadastro
									.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
										idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
						}
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(helper.getIdEsfera().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);

						System.out.println("localidade: " + idLocalidade + "/imovel: " + imovel.getId());

						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdEsfera(clienteTemp.getClienteTipo().getEsferaPoder().getId());
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

					// se ja existe um objeto igual a ele entao soma os
					// valores e as quantidades ja existentes.
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals
					if(listaSimplificadaParcelamento.contains(helper)){
						int posicao = listaSimplificadaParcelamento.indexOf(helper);
						ResumoParcelamentoHelper jaCadastrado = (ResumoParcelamentoHelper) listaSimplificadaParcelamento.get(posicao);
						// Somatorios
						// Contas
						jaCadastrado.setQtdContas(jaCadastrado.getQtdContas() + quantidadeContas);
						// Guias
						jaCadastrado.setQtdGuias(jaCadastrado.getQtdGuias() + quantidadeGuias);
						// Servicos Indiretos
						jaCadastrado.setQtdServicosIndiretos((short) (jaCadastrado.getQtdServicosIndiretos() + quantidadeServicosIndiretos));
						// qtdParcelamentos
						Short p = (((Integer) (jaCadastrado.getQtdParcelamento() + 1)).shortValue());
						jaCadastrado.setQtdParcelamento(p);
						// Valor Contas
						jaCadastrado.setVlContas(jaCadastrado.getVlContas().add(valorContas));
						// Valor Guias
						jaCadastrado.setVlGuias(jaCadastrado.getVlGuias().add(valorGuias));
						// Valor Servicos Indiretos
						jaCadastrado.setVlServicosIndiretos(jaCadastrado.getVlServicosIndiretos().add(valorGuias));
						// Valor Creditos
						jaCadastrado.setVlCreditoRealizar(jaCadastrado.getVlCreditoRealizar().add(valorCreditos));
						// Valor Acrescimo Impontualidade
						jaCadastrado.setVlAcrescimoImpontualidade(jaCadastrado.getVlAcrescimoImpontualidade().add(
										valorAcrescimoImpontualidade));
						// Valor Sancoes
						jaCadastrado.setVlSancoes(jaCadastrado.getVlSancoes().add(valorSancoes));
						// Valor Desconto Acrescimo
						jaCadastrado.setVlDescontoAcrescimo(jaCadastrado.getVlDescontoAcrescimo().add(valorDescontoAcrescimo));
						// Valor Desconto Inatividade
						jaCadastrado.setVlDescontoInatividade(jaCadastrado.getVlDescontoInatividade().add(valorDescontoInatividade));
						// Valor Desconto Antiguidade
						jaCadastrado.setVlDescontoAntiguidade(jaCadastrado.getVlDescontoAntiguidade().add(valorDescontoAntiguidade));
						// Valor Total Parcelamento
						jaCadastrado.setVlTotalParcelamento(jaCadastrado.getVlTotalParcelamento().add(valorTotalParcelamento));
						// AnoMesReferencia
						jaCadastrado.setAnoMesReferencia(jaCadastrado.getAnoMesReferencia());

						// valor debitos a cobrar total
						jaCadastrado.setVlDebitosACobrarTotal(jaCadastrado.getVlDebitosACobrarTotal().add(valorDebitoACobrarTotal));

						// valor debitos a cobrar acrescimos
						jaCadastrado.setVlDebitosACobrarAcrescimos(jaCadastrado.getVlDebitosACobrarAcrescimos().add(
										valorDebitoACobrarAcrescimos));

						// valor debitos a cobrar religsancoes
						jaCadastrado.setVlDebitosACobrarReligSancoes(jaCadastrado.getVlDebitosACobrarReligSancoes().add(
										valorDebitoACobrarReligSancoes));

						// valor debitos a cobrar parcelamentos
						jaCadastrado.setVlDebitosACobrarParcelamentos(jaCadastrado.getVlDebitosACobrarParcelamentos().add(
										valorDebitoACobrarParcelamentos));

						// valor entrada
						jaCadastrado.setVlEntrada(jaCadastrado.getVlEntrada().add(valorEntrada));

						// valor juros parcelamento
						jaCadastrado.setVlJurosParcelamento(jaCadastrado.getVlJurosParcelamento().add(valorJurosParcelamento));

						Integer qtdMediaParcelas = jaCadastrado.getQtdMediaParcelas() + quantidadeTotalParcelas;
						// media parcelas
						jaCadastrado.setQtdMediaParcelas(qtdMediaParcelas.shortValue());

						Integer qtdTotalParcelas = jaCadastrado.getQtdTotalParcelas() + quantidadeTotalParcelas;
						// total parcelas
						jaCadastrado.setQtdTotalParcelas(qtdTotalParcelas.shortValue());

					}else{
						// Somatorios
						// Contas
						helper.setQtdContas(helper.getQtdContas().intValue() + quantidadeContas);
						// Guias
						helper.setQtdGuias(helper.getQtdGuias().intValue() + quantidadeGuias);

						Short s = (((Integer) (helper.getQtdServicosIndiretos() + quantidadeServicosIndiretos)).shortValue());
						// Servicos Indiretos
						helper.setQtdServicosIndiretos(s);
						Short p = (((Integer) (helper.getQtdParcelamento() + 1)).shortValue());
						// qtdParcelamentos
						helper.setQtdParcelamento(p);
						// vlContas
						helper.setVlContas(helper.getVlContas().add(valorContas));
						// vlGuias
						helper.setVlGuias(helper.getVlGuias().add(valorGuias));
						// vlServicosPrestados
						helper.setVlServicosIndiretos(helper.getVlServicosIndiretos().add(valorServicosIndiretos));
						// vlCreditos
						helper.setVlCreditoRealizar(helper.getVlCreditoRealizar().add(valorCreditos));
						// vlAcrescimoImpontualidade
						helper.setVlAcrescimoImpontualidade(helper.getVlAcrescimoImpontualidade().add(valorAcrescimoImpontualidade));
						// vlSancoes
						helper.setVlSancoes(helper.getVlSancoes().add(valorSancoes));
						// vlDescontoAcrescimo
						helper.setVlDescontoAcrescimo(helper.getVlDescontoAcrescimo().add(valorDescontoAcrescimo));

						// vlDescontoInatividade
						helper.setVlDescontoInatividade(helper.getVlDescontoInatividade().add(valorDescontoInatividade));
						// vlDescontoAntiguidade
						helper.setVlDescontoAntiguidade(helper.getVlDescontoAntiguidade().add(valorDescontoAntiguidade));
						// vlTotalParcalamento
						helper.setVlTotalParcelamento(helper.getVlTotalParcelamento().add(valorTotalParcelamento));
						listaSimplificadaParcelamento.add(helper);
						// AnoMesReferencia
						helper.setAnoMesReferencia(anoMesRef);

						// valor debitos a cobrar total
						helper.setVlDebitosACobrarTotal(valorDebitoACobrarTotal);

						// valor debitos a cobrar acrescimos
						helper.setVlDebitosACobrarAcrescimos(valorDebitoACobrarAcrescimos);

						// valor debitos a cobrar religsancoes
						helper.setVlDebitosACobrarReligSancoes(valorDebitoACobrarReligSancoes);

						// valor debitos a cobrar parcelamentos
						helper.setVlDebitosACobrarParcelamentos(valorDebitoACobrarParcelamentos);

						// valor entrada
						helper.setVlEntrada(valorEntrada);

						// valor juros parcelamento
						helper.setVlJurosParcelamento(valorJurosParcelamento);

						// media parcelas
						helper.setQtdMediaParcelas(quantidadeTotalParcelas);

						// total parcelas
						helper.setQtdTotalParcelas(quantidadeTotalParcelas);
					}
				}
			}

			/**
			 * para todas as ImovelResumoParcelamentoHelper cria
			 * ResumoParcelamento
			 */
			for(int i = 0; i < listaSimplificadaParcelamento.size(); i++){
				ResumoParcelamentoHelper helper = (ResumoParcelamentoHelper) listaSimplificadaParcelamento.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(helper.getIdTipoClienteResponsavel());
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Quantidade Contas
				Integer qtdContas = helper.getQtdContas();

				// Quantidade Parcelamentos
				Short qtdParcelamentos = helper.getQtdParcelamento();

				// Quantidade Guias
				Integer qtdGuias = helper.getQtdGuias();

				// Valor das Contas
				BigDecimal vlContas = helper.getVlContas();

				// Valor Guias Pagmto
				BigDecimal vlGuias = helper.getVlGuias();

				// Valor creditos
				BigDecimal vlCreditos = helper.getVlCreditoRealizar();

				// Valor Servicos Indiretos
				BigDecimal vlAcrescimoImpontualidade = helper.getVlAcrescimoImpontualidade();

				// Valor Sancoes
				BigDecimal vlSancoes = helper.getVlSancoes();

				// Valor creditos
				BigDecimal vlDescontoAcrescimo = helper.getVlDescontoAcrescimo();

				// Valor Desconto Inatividade
				BigDecimal vlDescontoInatividade = helper.getVlDescontoInatividade();

				// Valor Desconto Antiguidade
				BigDecimal vlDescontoAntiguidade = helper.getVlDescontoAntiguidade();

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// valor debitos a cobrar total
				BigDecimal vlDebitosACobrarTotal = helper.getVlDebitosACobrarTotal();

				// valor debitos a cobrar acrescimos
				BigDecimal vlDebitosACobrarAcrescimos = helper.getVlDebitosACobrarAcrescimos();

				// valor debitos a cobrar religsancoes
				BigDecimal vlDebitosACobrarReligSancoes = helper.getVlDebitosACobrarReligSancoes();

				// valor debitos a cobrar parcelamentos
				BigDecimal vlDebitosACobrarParcelamentos = helper.getVlDebitosACobrarParcelamentos();

				// valor entrada
				BigDecimal vlEntrada = helper.getVlEntrada();

				// valor juros parcelamento
				BigDecimal vlJurosParcelamento = helper.getVlJurosParcelamento();

				// media parcelas
				Integer qtdMediaParcelas = new BigDecimal((helper.getQtdMediaParcelas() / qtdParcelamentos)).intValue();

				// total parcelas
				Integer qtdTotalParcelas = helper.getQtdTotalParcelas().intValue();

				// Perfil da ligacao de agua
				GConsumoTarifa consumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					consumoTarifa = new GConsumoTarifa();
					consumoTarifa.setId(helper.getConsumoTarifa());
				}

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				Short numeroParcelamentoConsecutivosShort = helper.getNumeroParcelamentoConsecutivos();

				Integer numeroParcelamentoConsecutivos = null;
				if(numeroParcelamentoConsecutivosShort != null){
					numeroParcelamentoConsecutivos = numeroParcelamentoConsecutivosShort.intValue();
				}else{
					numeroParcelamentoConsecutivos = 0;
				}

				// Criamos um resumo do parcelamento (***UFA***)
				UnResumoParcelamento resumoParcelamentoGrava = new UnResumoParcelamento(anoMesReferencia, codigoSetorComercial,
								numeroQuadra, qtdContas, vlContas, vlGuias, vlCreditos, vlDescontoAcrescimo, qtdGuias,
								vlDescontoInatividade, vlAcrescimoImpontualidade, ultimaAlteracao, qtdParcelamentos, vlDescontoAntiguidade,
								subcategoria, clienteTipo, ligacaoAguaSituacao, quadra, ligacaoEsgotoSituacao, gerenciaRegional,
								setorComercial, perfilLigacaoAgua, imovelPerfil, unidadeNegocio, elo, localidade, perfilLigacaoEsgoto,
								esferaPoder, categoria, rota, vlJurosParcelamento, vlEntrada, vlDebitosACobrarParcelamentos,
								vlDebitosACobrarTotal, vlDebitosACobrarAcrescimos, vlDebitosACobrarReligSancoes, qtdTotalParcelas,
								qtdMediaParcelas, consumoTarifa, indicadorHidrometro, numeroParcelamentoConsecutivos);

				// Adicionamos a lista que deve ser inserida
				listaResumoParcelamento.add(resumoParcelamentoGrava);
			}

			this.getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoParcelamento);

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido

			System.out.println(" ERRO NA LOCALIDADE " + idLocalidade);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
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
	 * Mï¿½todo que gera o resumo de Metas da CAERN
	 * [UC0623] - Gerar Resumo de Metas
	 * 
	 * @author Sï¿½vio Luiz
	 * @date 20/07/2007
	 */
	public void gerarResumoMetas(int idSetor, Date dataInicial, Date dataFinal, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		Integer anoMesReferencia = Util.recuperaAnoMesDaData(dataInicial);

		try{

			List imoveisResumoConsumoAgua = this.repositorioGerencialCadastro.pesquisarDadosResumoMetas(idSetor, dataInicial, dataFinal);

			List<ResumoMetasHelper> listaSimplificada = new ArrayList();
			Imovel imovelObterQtdEconomia = null;

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoConsumoAgua.size(); i++){
				Object obj = imoveisResumoConsumoAgua.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia = new Imovel();
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoMetasHelper helper = new ResumoMetasHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de negocio
									(Integer) retorno[3], // Localidade
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Id Setor Comercial
									(Integer) retorno[6], // id Rota
									(Integer) retorno[7], // Id Quadra
									(Integer) retorno[8], // Codigo do Setor Comercial
									(Integer) retorno[9], // Numero da quadra
									(Integer) retorno[10],// Perfil do imovel
									(Integer) retorno[11],// Situacao da ligacao da
									// agua
									(Integer) retorno[12],// Situacao da ligacao do
									// esgoto
									(Integer) retorno[13],// Perfil da ligacao de agua
									(Integer) retorno[14],// Perfil da ligacao de
									// esgoto
									(Date) retorno[15],// Data Ligaï¿½ï¿½o
									(Date) retorno[16],// Data Supressï¿½o
									(Date) retorno[17],// Data Corte
									(Date) retorno[18],// Data Religaï¿½ï¿½o
									(Date) retorno[19],// Perfil da ligacao de esgoto
									(Integer) retorno[20]// Hidrometro instalado
					);

					Integer idImovel = (Integer) retorno[0]; // Codigo do
					// imovel que
					// esta sendo
					// processado

					// seta o tipo de data
					helper.setarTipoData(dataInicial, dataFinal);

					// Pesquisamos a esfera de poder do cliente responsavel

					helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));

					// Pesquisamos o tipo de cliente responsavel do imovel

					helper.setIdClienteTipo(this.repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoriaComCodigoGrupo(
										categoria.getId(), idImovel);
						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
							if(subcategoria.getComp_id().getSubcategoria() != null
											&& subcategoria.getComp_id().getSubcategoria().getCodigoGrupoSubcategoria() != null){
								helper.setCodigoGrupoSubcategoria(subcategoria.getComp_id().getSubcategoria().getCodigoGrupoSubcategoria());
							}
						}
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(helper.getIdEsferaPoder().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);

						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

						if(clienteTemp != null && clienteTemp.getClienteTipo() != null
										&& clienteTemp.getClienteTipo().getEsferaPoder() != null){
							helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
						}
					}
					if(helper.getIdClienteTipo().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdClienteTipo(clienteTemp.getClienteTipo().getId());
						}
					}

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){

						int posicao = listaSimplificada.indexOf(helper);
						ResumoMetasHelper jaCadastrado = (ResumoMetasHelper) listaSimplificada.get(posicao);

						// Somamos as ligaï¿½ï¿½es cadastradas
						jaCadastrado.setQuantidadeLigacoesCadastradas(jaCadastrado.getQuantidadeLigacoesCadastradas().intValue() + 1);
						// Somamos as economias
						jaCadastrado.setQuantidadeEconomias(jaCadastrado.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						int[] tiposData = helper.getTipoData();

						for(int tipoData : tiposData){

							if(tipoData != 0){

								switch(tipoData){
									case ResumoMetasHelper.INIDCADOR_DATA_SUPRESSAO:
										// Somamos as ligaï¿½ï¿½es suprimidas
										jaCadastrado.setQuantidadeLigacoesSuprimidas(jaCadastrado.getQuantidadeLigacoesSuprimidas()
														.intValue() + 1);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_CORTE:
										// Somamos as ligaï¿½ï¿½es suprimidas
										jaCadastrado.setQuantidadeLigacoesCortadas(jaCadastrado.getQuantidadeLigacoesCortadas().intValue() + 1);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_LIGACAO:
										jaCadastrado = setarDadosResumoMetasHelper(jaCadastrado, idImovel, anoMesReferencia);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_RELIGACAO:
										jaCadastrado = setarDadosResumoMetasHelper(jaCadastrado, idImovel, anoMesReferencia);
										break;
									case ResumoMetasHelper.INIDCADOR_DATA_RESTABELECIMENTO:
										jaCadastrado = setarDadosResumoMetasHelper(jaCadastrado, idImovel, anoMesReferencia);
										break;

								}
							}
						}

					}else{

						// Somamos as ligaï¿½ï¿½es cadastradas
						helper.setQuantidadeLigacoesCadastradas(helper.getQuantidadeLigacoesCadastradas().intValue() + 1);
						// Somamos as economias
						helper.setQuantidadeEconomias(helper.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						int[] tiposData = helper.getTipoData();

						for(int tipoData : tiposData){

							if(tipoData != 0){

								switch(tipoData){
									case ResumoMetasHelper.INIDCADOR_DATA_SUPRESSAO:
										// Somamos as ligaï¿½ï¿½es suprimidas
										helper.setQuantidadeLigacoesSuprimidas(helper.getQuantidadeLigacoesSuprimidas().intValue() + 1);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_CORTE:
										// Somamos as ligaï¿½ï¿½es suprimidas
										helper.setQuantidadeLigacoesCortadas(helper.getQuantidadeLigacoesCortadas().intValue() + 1);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_LIGACAO:
										helper = setarDadosResumoMetasHelper(helper, idImovel, anoMesReferencia);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_RELIGACAO:
										helper = setarDadosResumoMetasHelper(helper, idImovel, anoMesReferencia);
										break;

									case ResumoMetasHelper.INIDCADOR_DATA_RESTABELECIMENTO:
										helper = setarDadosResumoMetasHelper(helper, idImovel, anoMesReferencia);
										break;

								}
							}
						}

						listaSimplificada.add(helper);

					}
				}
			}

			for(int i = 0; i < listaSimplificada.size(); i++){
				this.repositorioGerencialCadastro.inserirResumoMetas(anoMesReferencia, (ResumoMetasHelper) listaSimplificada.get(i));
			}

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NO SETOR COMERCIAL " + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * Mï¿½todo que gera o resumo de Metas da CAERN
	 * [UC0570] - Gerar Resumo do consumo de agua
	 * 
	 * @author Sï¿½vio Luiz
	 * @date 20/07/2007
	 */
	private ResumoMetasHelper setarDadosResumoMetasHelper(ResumoMetasHelper resumoMetasHelper, Integer idImovel, Integer amArrecadacao){

		try{
			// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas + 1
			resumoMetasHelper.setQuantidadeLigacoesAtivas(resumoMetasHelper.getQuantidadeLigacoesAtivas() + 1);

			boolean existeDebito = getControladorFaturamento().verificarDebitoMais3MesesFaturaEmAberto(amArrecadacao, idImovel);
			if(existeDebito){
				// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas + 1
				resumoMetasHelper.setQuantidadeLigacoesAtivasDebito3M(resumoMetasHelper.getQuantidadeLigacoesAtivasDebito3M() + 1);
			}

			if(resumoMetasHelper.getIdHidrometroInstalado() != null && !resumoMetasHelper.getIdHidrometroInstalado().equals("")){
				// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas + 1
				resumoMetasHelper.setQuantidadeLigacoesConsumoMedido(resumoMetasHelper.getQuantidadeLigacoesConsumoMedido() + 1);

				Integer consumoFaturadoSemLigacao = getControladorMicromedicao().pesquisarConsumoFaturado(idImovel,
								LigacaoTipo.LIGACAO_AGUA, null, null, amArrecadacao);

				if(consumoFaturadoSemLigacao != null && consumoFaturadoSemLigacao >= 0 && consumoFaturadoSemLigacao <= 5){
					// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas + 1
					resumoMetasHelper.setQuantidadeLigacoesConsumoAte5M3(resumoMetasHelper.getQuantidadeLigacoesConsumoAte5M3() + 1);
				}

				Integer consumoFaturadoImovelAtual = getControladorMicromedicao().pesquisarConsumoFaturado(idImovel,
								LigacaoTipo.LIGACAO_AGUA, ConsumoTipo.MEDIA_IMOVEL, ConsumoTipo.MEDIA_HIDROMETRO, amArrecadacao);
				Integer consumoFaturadoImovelMenos1 = getControladorMicromedicao().pesquisarConsumoFaturado(idImovel,
								LigacaoTipo.LIGACAO_AGUA, ConsumoTipo.MEDIA_IMOVEL, ConsumoTipo.MEDIA_HIDROMETRO,
								Util.subtraiAteSeisMesesAnoMesReferencia(amArrecadacao, 1));

				Integer consumoFaturadoImovelMenos2 = getControladorMicromedicao().pesquisarConsumoFaturado(idImovel,
								LigacaoTipo.LIGACAO_AGUA, ConsumoTipo.MEDIA_IMOVEL, ConsumoTipo.MEDIA_HIDROMETRO,
								Util.subtraiAteSeisMesesAnoMesReferencia(amArrecadacao, 2));
				Integer consumoFaturadoImovelMenos3 = getControladorMicromedicao().pesquisarConsumoFaturado(idImovel,
								LigacaoTipo.LIGACAO_AGUA, ConsumoTipo.MEDIA_IMOVEL, ConsumoTipo.MEDIA_HIDROMETRO,
								Util.subtraiAteSeisMesesAnoMesReferencia(amArrecadacao, 3));
				if(consumoFaturadoImovelAtual != null && !consumoFaturadoImovelAtual.equals("") && consumoFaturadoImovelMenos1 != null
								&& !consumoFaturadoImovelMenos1.equals("") && consumoFaturadoImovelMenos2 != null
								&& !consumoFaturadoImovelMenos2.equals("") && consumoFaturadoImovelMenos3 != null
								&& !consumoFaturadoImovelMenos3.equals("")){
					// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas
					// + 1
					resumoMetasHelper.setQuantidadeLigacoesConsumoMedio(resumoMetasHelper.getQuantidadeLigacoesConsumoMedio() + 1);
				}

			}else{
				// Somar ao acumulador de quantidade de ligaï¿½ï¿½es ativas + 1
				resumoMetasHelper.setQuantidadeLigacoesConsumoNaoMedido(resumoMetasHelper.getQuantidadeLigacoesConsumoNaoMedido() + 1);
			}

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO IMOVEL " + idImovel);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			throw new EJBException(ex);
		}

		return resumoMetasHelper;
	}

	/**
	 * Mï¿½todo que gera o resumo da pendencia
	 * [UC0335] - Gerar Resumo do Parcelamento
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */

	// public void gerarResumoPendencia( int idSetor,
	// int idFuncionalidadeIniciada) throws ControladorException {
	//
	// int idUnidadeIniciada = 0;
	//
	// // -------------------------
	// //
	// // Registrar o inï¿½cio do processamento da Unidade de
	// // Processamento
	// // do Batch
	// //
	// // -------------------------
	// idUnidadeIniciada = getControladorBatch()
	// .iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
	// UnidadeProcessamento.LOCALIDADE, idSetor);
	//
	// try {
	//
	// this.gerarResumoPendenciaContaGerencia(idSetor);
	//
	// getControladorBatch().encerrarUnidadeProcessamentoBatch(
	// idUnidadeIniciada, false);
	//
	// } catch (Exception ex) {
	// // Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
	// // batch venha a lanï¿½ar e garantir que a unidade de processamento do
	// // batch serï¿½ atualizada com o erro ocorrido
	//
	// System.out.println( " ERRO NO SETOR " + idSetor);
	//
	// ex.printStackTrace();
	// getControladorBatch().encerrarUnidadeProcessamentoBatch(
	// idUnidadeIniciada, true);
	//
	// throw new EJBException(ex);
	// }
	// }
	/**
	 * Gera a primeira parte do resumo de pendencias [SB0001A]
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 */
	// private void gerarResumoPendenciaContaGerencia( int idSetor ) throws
	// ControladorException, ErroRepositorioException {
	// System.out.println( " ***RESUMO DE PENDENCIAS DAS CONTAS POR GERENCIA -
	// SETOR " + idSetor + "***");
	//
	// /**
	// * O sistema seleciona as contas pendentes ( a partir
	// * da tabela CONTA com CNTA_AMREFERENCIACONTA <
	// * PARM_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS
	// * e ( DCST_IDATUAL = 0 ou (DCST_IDATUAL = (1,2) e
	// * CNTA_AMREFERENCIACONTABIL < PARM_AMREFENRECIAFATURAMENTO
	// * ou (DCST_IDATUAL = (3,4,5) e CNTA_AMREFERENCIACONTABIL >
	// * PARM_AMREFERENCIAFATURAMENTO */
	// List contasPendentes =
	// this.repositorioGerencialCadastro.getContasPendentes( idSetor );
	//
	// // Varremos
	// for ( int i = 0; i < contasPendentes.size(); i++ ){
	// Object obj = contasPendentes.get(i);
	//
	// if (obj instanceof Object[]) {
	// Object[] linha = (Object[]) obj;
	//
	// ResumoPendenciaContasGerenciaHelper helper = new
	// ResumoPendenciaContasGerenciaHelper(
	// (Integer) linha[0], // Gerencia Regional
	// (Integer) linha[1], // Unidade Negocio
	// (Integer) linha[2], // Elo
	// (Integer) linha[3], // Localidade
	// (Integer) linha[4], // Setor Comercial
	// (Integer) linha[5], // Rota
	// (Integer) linha[6], // Quadra
	// (Integer) linha[7], // Codigo Setor Comercial
	// (Integer) linha[8], // Numero da quadra
	// (Integer) linha[9], // Perfil do imovel
	// (Integer) linha[10], // Situacao Ligacao Agua
	// (Integer) linha[11], // Situacao Ligacao Esgoto
	// (Integer) linha[12], // Perfil ligaï¿½ï¿½o Agua
	// (Integer) linha[13], // Perfil ligaï¿½ï¿½o Esgoto
	// (Integer) linha[14], // Volume Fixado Agua
	// (Integer) linha[15], // Volume Fixado Agua
	// (Integer) linha[16], // Volume Fixado Esgoto
	// new Integer(1),
	// (Integer) linha[17], // Ano mes de referencia documento
	// (Integer) linha[18]); // Referencia do vencimento da conta
	//
	// Integer idImovel = ( Integer )linha[19]; // Codigo do imovel que esta
	// sendo processado
	//
	// // Pesquisamos a esfera de poder do cliente responsavel
	// helper.setIdEsferaPoder(
	// this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(
	// idImovel ) );
	// // Pesquisamos o tipo de cliente responsavel do imovel
	// helper.setIdTipoClienteResponsavel(
	// this.repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(
	// idImovel ) );
	//
	// // pesquisando a categoria
	// // [UC0306] - Obtter principal categoria do imï¿½vel
	// Categoria categoria = null;
	// categoria = this.getControladorImovel()
	// .obterPrincipalCategoriaImovel(idImovel);
	// if (categoria != null) {
	// helper.setIdPrincipalCategoriaImovel(categoria.getId());
	//
	// // Pesquisando a principal subcategoria
	// ImovelSubcategoria subcategoria =
	// this.getControladorImovel().obterPrincipalSubcategoria(
	// categoria.getId(), idImovel );
	//
	// if ( subcategoria != null ){
	// helper.setIdPrincipalSubCategoriaImovel(
	// subcategoria.getComp_id().getSubcategoria().getId() );
	// }
	// }
	//
	// // Verificamos se a esfera de poder foi encontrada
	// // [FS0002] Verificar existencia de cliente responsavel
	// if ( helper.getIdEsferaPoder().equals( 0 ) ){
	// Imovel imovel = new Imovel();
	// imovel.setId(idImovel);
	// Cliente clienteTemp =
	// this.getControladorImovel().consultarClienteUsuarioImovel( imovel );
	// if ( clienteTemp != null ){
	// helper.setIdEsferaPoder(
	// clienteTemp.getClienteTipo().getEsferaPoder().getId() );
	// }
	// }
	// if ( helper.getIdTipoClienteResponsavel().equals( 0 ) ){
	// Imovel imovel = new Imovel();
	// imovel.setId(idImovel);
	// Cliente clienteTemp =
	// this.getControladorImovel().consultarClienteUsuarioImovel( imovel );
	// if ( clienteTemp != null ){
	// helper.setIdTipoClienteResponsavel( clienteTemp.getClienteTipo().getId()
	// );
	// }
	// }
	// }
	// }
	// }
	/**
	 * Mï¿½todo que gera o resumo de Metas da CAERN
	 * [UC0623] - Gerar Resumo de Metas
	 * 
	 * @author Sï¿½vio Luiz
	 * @date 20/07/2007
	 */
	public void gerarResumoMetasAcumulado(int idSetor, Integer anoMesArrecadacao, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		try{

			List imoveisResumoConsumoAgua = this.repositorioGerencialCadastro.pesquisarDadosResumoMetasAcumulado(idSetor);

			List<ResumoMetasHelper> listaSimplificada = new ArrayList();

			Imovel imovelObterQtdEconomia = null;

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoConsumoAgua.size(); i++){
				Object obj = imoveisResumoConsumoAgua.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia = new Imovel();
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoMetasHelper helper = new ResumoMetasHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de negocio
									(Integer) retorno[3], // Localidade
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Id Setor Comercial
									(Integer) retorno[6], // id Rota
									(Integer) retorno[7], // Id Quadra
									(Integer) retorno[8], // Codigo do Setor Comercial
									(Integer) retorno[9], // Numero da quadra
									(Integer) retorno[10],// Perfil do imovel
									(Integer) retorno[11],// Situacao da ligacao da
									// agua
									(Integer) retorno[12],// Situacao da ligacao do
									// esgoto
									(Integer) retorno[13],// Perfil da ligacao de agua
									(Integer) retorno[14],// Perfil da ligacao de
									// esgoto
									(Date) retorno[15],// Data Ligaï¿½ï¿½o
									(Date) retorno[16],// Data Supressï¿½o
									(Date) retorno[17],// Data Corte
									(Date) retorno[18],// Data Religaï¿½ï¿½o
									(Date) retorno[19],// Perfil da ligacao de esgoto
									(Integer) retorno[20]// Hidrometro instalado
					);

					Integer idImovel = (Integer) retorno[0]; // Codigo do
					// imovel que
					// esta sendo
					// processado

					// Pesquisamos a esfera de poder do cliente responsavel

					helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));

					// Pesquisamos o tipo de cliente responsavel do imovel

					helper.setIdClienteTipo(this.repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoriaComCodigoGrupo(
										categoria.getId(), idImovel);
						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
							if(subcategoria.getComp_id().getSubcategoria() != null
											&& subcategoria.getComp_id().getSubcategoria().getCodigoGrupoSubcategoria() != null){
								helper.setCodigoGrupoSubcategoria(subcategoria.getComp_id().getSubcategoria().getCodigoGrupoSubcategoria());
							}
						}
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					if(helper.getIdEsferaPoder().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);

						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);

						if(clienteTemp != null && clienteTemp.getClienteTipo() != null
										&& clienteTemp.getClienteTipo().getEsferaPoder() != null){
							helper.setIdEsferaPoder(clienteTemp.getClienteTipo().getEsferaPoder().getId());
						}
					}
					if(helper.getIdClienteTipo().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdClienteTipo(clienteTemp.getClienteTipo().getId());
						}
					}

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){

						int posicao = listaSimplificada.indexOf(helper);
						ResumoMetasHelper jaCadastrado = (ResumoMetasHelper) listaSimplificada.get(posicao);

						// Somamos as ligaï¿½ï¿½es cadastradas
						if((jaCadastrado.getIdLigacaoAguaSituacao() != null && (!jaCadastrado.getIdLigacaoAguaSituacao().equals(
										LigacaoAguaSituacao.POTENCIAL) && !jaCadastrado.getIdLigacaoAguaSituacao().equals(
										LigacaoAguaSituacao.FACTIVEL)))
										|| (jaCadastrado.getIdLigacaoEsgotoSituacao() != null && (!jaCadastrado
														.getIdLigacaoEsgotoSituacao().equals(LigacaoEsgotoSituacao.POTENCIAL) && !jaCadastrado
														.getIdLigacaoEsgotoSituacao().equals(LigacaoEsgotoSituacao.FACTIVEL)))){
							jaCadastrado.setQuantidadeLigacoesCadastradas(jaCadastrado.getQuantidadeLigacoesCadastradas().intValue() + 1);

							// Somamos as economias
							jaCadastrado.setQuantidadeEconomias(jaCadastrado.getQuantidadeEconomias().intValue() + quantidadeEconomia);
						}
						// Somamos as ligaï¿½ï¿½es cadastradas
						if(jaCadastrado.getIdLigacaoAguaSituacao() != null
										&& (jaCadastrado.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.SUPR_PARC)
														|| jaCadastrado.getIdLigacaoAguaSituacao().equals(
																		LigacaoAguaSituacao.SUPR_PARC_PEDIDO) || jaCadastrado
														.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.SUPRIMIDO))){

							// Somamos as ligaï¿½ï¿½es suprimidas
							jaCadastrado.setQuantidadeLigacoesSuprimidas(jaCadastrado.getQuantidadeLigacoesSuprimidas().intValue() + 1);
						}

						// Somamos as ligaï¿½ï¿½es cadastradas
						if(jaCadastrado.getIdLigacaoAguaSituacao() != null
										&& (jaCadastrado.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.CORTADO))){
							// Somamos as ligaï¿½ï¿½es suprimidas
							jaCadastrado.setQuantidadeLigacoesCortadas(jaCadastrado.getQuantidadeLigacoesCortadas().intValue() + 1);
						}
						// Somamos as ligaï¿½ï¿½es cadastradas
						if(jaCadastrado.getIdLigacaoAguaSituacao() != null
										&& (jaCadastrado.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.LIGADO))
										|| (jaCadastrado.getIdLigacaoEsgotoSituacao() != null && (jaCadastrado.getIdLigacaoEsgotoSituacao()
														.equals(LigacaoEsgotoSituacao.LIGADO)))){

							jaCadastrado = setarDadosResumoMetasHelper(jaCadastrado, idImovel, anoMesArrecadacao);
						}

					}else{

						// Somamos as ligaï¿½ï¿½es cadastradas
						if((helper.getIdLigacaoAguaSituacao() != null && (!helper.getIdLigacaoAguaSituacao().equals(
										LigacaoAguaSituacao.POTENCIAL) && !helper.getIdLigacaoAguaSituacao().equals(
										LigacaoAguaSituacao.FACTIVEL)))
										|| (helper.getIdLigacaoEsgotoSituacao() != null && (!helper.getIdLigacaoEsgotoSituacao().equals(
														LigacaoEsgotoSituacao.POTENCIAL) && !helper.getIdLigacaoEsgotoSituacao().equals(
														LigacaoEsgotoSituacao.FACTIVEL)))){
							helper.setQuantidadeLigacoesCadastradas(helper.getQuantidadeLigacoesCadastradas().intValue() + 1);

							// Somamos as economias
							helper.setQuantidadeEconomias(helper.getQuantidadeEconomias().intValue() + quantidadeEconomia);
						}
						// Somamos as ligaï¿½ï¿½es cadastradas
						if(helper.getIdLigacaoAguaSituacao() != null
										&& (helper.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.SUPR_PARC)
														|| helper.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO) || helper
														.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.SUPRIMIDO))){

							// Somamos as ligaï¿½ï¿½es suprimidas
							helper.setQuantidadeLigacoesSuprimidas(helper.getQuantidadeLigacoesSuprimidas().intValue() + 1);
						}

						// Somamos as ligaï¿½ï¿½es cadastradas
						if(helper.getIdLigacaoAguaSituacao() != null
										&& (helper.getIdLigacaoAguaSituacao().equals(LigacaoAguaSituacao.CORTADO))){
							// Somamos as ligaï¿½ï¿½es suprimidas
							helper.setQuantidadeLigacoesCortadas(helper.getQuantidadeLigacoesCortadas().intValue() + 1);
						}
						// Somamos as ligaï¿½ï¿½es cadastradas
						if((helper.getIdLigacaoAguaSituacao() != null && (helper.getIdLigacaoAguaSituacao()
										.equals(LigacaoAguaSituacao.LIGADO)))
										|| (helper.getIdLigacaoEsgotoSituacao() != null && (helper.getIdLigacaoEsgotoSituacao()
														.equals(LigacaoEsgotoSituacao.LIGADO)))){

							helper = setarDadosResumoMetasHelper(helper, idImovel, anoMesArrecadacao);
						}

						listaSimplificada.add(helper);

					}
				}
			}

			for(int i = 0; i < listaSimplificada.size(); i++){
				this.repositorioGerencialCadastro.inserirResumoMetasAcumulado(anoMesArrecadacao,
								(ResumoMetasHelper) listaSimplificada.get(i));
			}

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------n

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o
			// processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento
			// do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NO SETOR COMERCIAL " + idSetor);

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	public Integer obterConsumoMinimoCategoria(Imovel imovel, Categoria categoria) throws ControladorException{

		Integer consumoMinimoCategoria = 0;

		// Selecionamos o consumo mï¿½nimo dessa faixa
		try{
			// [UC0108] - Obter Quantidade de Economias por Categoria
			Short quantidadeEconomias = this.repositorioImovel.pesquisarObterQuantidadeEconomias(imovel, categoria);
			Integer consumoMinimo = this.repositorioGerencialCadastro.getConsumoMinimoImovelCategoria(imovel.getId(), categoria.getId());
			consumoMinimoCategoria = quantidadeEconomias * consumoMinimo;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return consumoMinimoCategoria;
	}

	/**
	 * Mï¿½todo que gera o resumo do coleta esgoto
	 * [UC0573] - Gerar Resumo do coleta esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 18/09/2007
	 */
	public void gerarResumoColetaEsgoto(int idSetor, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o inï¿½cio do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		Integer anoMesReferencia = null;

		try{
			// deleta tabela resumo do mes corrente.
			// this.repositorioGerencialCadastro.deletaResumoColetaEsgoto();

			List imoveisResumoColetaEsgoto = this.repositorioGerencialCadastro.getHistoricosColetaEsgoto(idSetor);

			List<ResumoColetaEsgotoHelper> listaSimplificada = new ArrayList();
			List<UnResumoColetaEsgoto> listaResumoConsumoAgua = new ArrayList();

			Imovel imovelObterQtdEconomia = null;

			// FS0001 - Verificar existencia de dados para o ano/mes referencia informado
			repositorioGerencialCadastro.excluirResumoGerencial(getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento(),
							"micromedicao.un_resumo_coleta_esgoto", "rece_amreferencia", idSetor);

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoColetaEsgoto.size(); i++){
				Object obj = imoveisResumoColetaEsgoto.get(i);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					System.out.println("imovel: " + (Integer) retorno[0] + " Setor: " + idSetor + " Localidade: " + (Integer) retorno[3]);

					// Pesquisamos as quantidades de economia do imovel corrente
					imovelObterQtdEconomia = new Imovel();
					imovelObterQtdEconomia.setId((Integer) retorno[0]);
					Integer quantidadeEconomia = this.getControladorImovel().obterQuantidadeEconomias(imovelObterQtdEconomia);
					if(quantidadeEconomia == null){
						quantidadeEconomia = 0;
					}

					// Montamos um objeto de resumo, com as informacoes do
					// retorno
					ResumoColetaEsgotoHelper helper = new ResumoColetaEsgotoHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de negocio
									(Integer) retorno[3], // Localidade
									(Integer) retorno[4], // Elo
									(Integer) retorno[5], // Id Setor Comercial
									(Integer) retorno[6], // id Rota
									(Integer) retorno[7], // Id Quadra
									(Integer) retorno[8], // Codigo do Setor Comercial
									(Integer) retorno[9], // Numero da quadra
									(Integer) retorno[10],// Perfil do imovel
									(Integer) retorno[11],// Situacao da ligacao da
									// agua
									(Integer) retorno[12],// Situacao da ligacao do esgoto
									(Integer) retorno[13],// Perfil da ligacao de agua
									(Integer) retorno[14],// Perfil da ligacao de esgoto
									(Integer) retorno[15]// Consumo tipo
					);
					// Imovel
					Integer idImovel = (Integer) retorno[0];

					// Consumo de Esgoto
					Integer consumoEsgoto = (Integer) retorno[16];
					if(consumoEsgoto == null){
						consumoEsgoto = 0;
					}

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsferaPoder(this.repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdClienteTipo(this.repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imï¿½vel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
										idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
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
					if(helper.getIdClienteTipo().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovel);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helper.setIdClienteTipo(clienteTemp.getClienteTipo().getId());
						}
					}

					// Ano Mï¿½s Referencia.
					anoMesReferencia = (Integer) retorno[17];

					// [UC0307] - Obter Indicador de Existï¿½ncia de Hidrï¿½metro
					String indicadorHidrometroString = new Integer(getControladorImovel()
									.obterIndicadorExistenciaHidrometroImovel(idImovel)).toString();
					Short indicadorHidrometro = new Short(indicadorHidrometroString);
					// Caso indicador de hidrï¿½metro esteja nulo
					// Seta 2(dois) = Nï¿½O no indicador de
					// hidrï¿½metro
					if(indicadorHidrometro == null){
						indicadorHidrometro = new Short("2");
					}
					helper.setIndicadorHidrometro(indicadorHidrometro);

					// Verificamos a existencia de poco no imovel
					Integer idPoco = this.repositorioGerencialCadastro.verificarExistenciaPoco(idImovel);
					if(idPoco == null){
						idPoco = 0;
					}
					// helper.setPoco(idPoco.shortValue());

					// Existencia de Hidrometro no Poï¿½o.
					Integer hidrometroInstaladoPoco = (Integer) retorno[20];
					// helper.setMedidoFonteAlternativa(hidrometroInstaladoPoco.shortValue());

					Imovel imovelTemp = new Imovel();
					imovelTemp.setId(idImovel);
					imovelTemp = this.getControladorImovel().pesquisarImovel(imovelTemp.getId());

					Integer valorMinimoEsgoto = getControladorMicromedicao().obterConsumoMinimoLigacao(imovelTemp, null);

					Integer voFaturado = valorMinimoEsgoto;
					if(consumoEsgoto > valorMinimoEsgoto){
						voFaturado = consumoEsgoto;
					}
					if(voFaturado == null){
						voFaturado = 0;
					}
					helper.setVoFaturado(voFaturado);

					// set numeroConsumoFaturadoMes
					helper.setQuantidadeColetaEsgoto(consumoEsgoto);

					// Setamos o valor do excedente
					helper.setVolumeExcedente(helper.getQuantidadeColetaEsgoto() - valorMinimoEsgoto);

					// Caso o valor excedente seja negativo, seramos, pois nao
					// houve excedente.
					if(helper.getVolumeExcedente() <= 0){
						helper.setVolumeExcedente(0);
					}

					short icColetaEsgotoExcedente = 2;
					helper.setIndicadorColetaEsgotoExcedente(icColetaEsgotoExcedente);
					if(helper.getVolumeExcedente() > 0){
						icColetaEsgotoExcedente = 1;
						helper.setIndicadorColetaEsgotoExcedente(icColetaEsgotoExcedente);
					}

					// Obtemos o percentual de esgoto
					// System.out.println(retorno[21].getClass().getName());
					float percentualEsgoto = 0f;
					percentualEsgoto = ((Double) retorno[19]).floatValue();
					if(percentualEsgoto == 0){
						percentualEsgoto = 0.0f;
					}
					helper.setPcEsgoto(percentualEsgoto);

					// percentual coleta
					BigDecimal pcColeta = (BigDecimal) retorno[21];
					// pcColeta = ((Double) retorno[21]).floatValue();
					// if(pcColeta == 0){
					// pcColeta = 0.0f;
					// }
					helper.setPcEsgoto(pcColeta.floatValue());

					// Indicador volume minimo esgoto
					Integer icVlMinimo = (Integer) retorno[18];
					// helper.setIcVlMinimoEsgoto(icVlMinimo.shortValue());

					// helper
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoColetaEsgotoHelper jaCadastrado = (ResumoColetaEsgotoHelper) listaSimplificada.get(posicao);

						// Somamos as economias
						jaCadastrado.setQuantidadeEconomias(jaCadastrado.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						// Incrementamos as ligacoes
						jaCadastrado.setQuantidadeLigacoes(jaCadastrado.getQuantidadeLigacoes() + 1);

						// Acumulamos o consumo de agua
						jaCadastrado.setQuantidadeColetaEsgoto(jaCadastrado.getQuantidadeColetaEsgoto().intValue()
										+ helper.getQuantidadeColetaEsgoto());

						// Acumulamos o consumo Excedente
						jaCadastrado.setVolumeExcedente(jaCadastrado.getVolumeExcedente() + helper.getVolumeExcedente());
					}else{
						// Incrementamos as ligacoes
						helper.setQuantidadeLigacoes(helper.getQuantidadeLigacoes() + 1);

						// Somamos as economias
						helper.setQuantidadeEconomias(helper.getQuantidadeEconomias().intValue() + quantidadeEconomia);

						// Ano Mes Referencia.
						helper.setAnoMesReferencia(anoMesReferencia);

						listaSimplificada.add(helper);
					}
				}
			}

			for(int i = 0; i < listaSimplificada.size(); i++){
				this.repositorioGerencialCadastro.inserirResumoColetaEsgoto(anoMesReferencia,
								(ResumoColetaEsgotoHelper) listaSimplificada.get(i));
			}

			// --------------------------------------------------------
			//
			// Registrar o fim da execuï¿½ï¿½o da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceï¿½ï¿½o que o processo
			// batch venha a lanï¿½ar e garantir que a unidade de processamento do
			// batch serï¿½ atualizada com o erro ocorrido
			System.out.println(" ERRO NO SETOR " + idSetor + " COLETA ESGOTO ");

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * Método consultarResumoLigacoesEconomiasGCS
	 * <p>
	 * Esse método implementa consulta dos dados para relatorio de resumo ligacoes economia tal como
	 * no GCS.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 *            dados do formulario de pesquisa,
	 *            {@link InformarDadosGeracaoRelatorioConsultaHelper}.
	 * @return Lista de {@link DetalheLigacaoEconomiaGCSHelper}.
	 * @author Marlos Ribeiro
	 * @since 20/11/2012
	 */
	public List<DetalheLigacaoEconomiaGCSHelper> consultarDetalhesLigacoesEconomiasGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa, Integer paginacao)
					throws ControladorException{

		try{
			return repositorioGerencialCadastro.consultarDetalhesLigacoesEconomiasGCS(parametrosPesquisa, paginacao);
		}catch(ErroRepositorioException e){
			LOGGER.error(e);
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método consultarResumoLigacoesEconomiasGCS
	 * <p>
	 * Esse método implementa consulta dos dados para relatorio de resumo ligacoes economia tal como
	 * no GCS.
	 * </p>
	 * RASTREIO: [OC897714][UC269]
	 * 
	 * @param parametrosPesquisa
	 *            dados do formulario de pesquisa,
	 *            {@link InformarDadosGeracaoRelatorioConsultaHelper}.
	 * @return Lista de {@link DetalheLigacaoEconomiaGCSHelper}.
	 * @author Marlos Ribeiro
	 * @since 20/11/2012
	 */
	public List<SumarioLigacaoPorCategoriaGCSHelper> consultarSumarioLigacoesPorCategoriaGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa) throws ControladorException{

		try{
			return repositorioGerencialCadastro.consultarSumarioLigacoesPorCategoriaGCS(parametrosPesquisa);
		}catch(ErroRepositorioException e){
			LOGGER.error(e);
			throw new ControladorException("erro.sistema", e);
		}
	}

}