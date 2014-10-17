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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gerencial.arrecadacao;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.gerencial.arrecadacao.bean.ResumoArrecadacaoAguaEsgotoHelper;
import gcom.gerencial.arrecadacao.bean.ResumoArrecadacaoCreditoHelper;
import gcom.gerencial.arrecadacao.bean.ResumoArrecadacaoOutrosHelper;
import gcom.gerencial.arrecadacao.pagamento.GEpocaPagamento;
import gcom.gerencial.arrecadacao.pagamento.GPagamentoSituacao;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.cadastro.IRepositorioGerencialCadastro;
import gcom.gerencial.cadastro.RepositorioGerencialCadastroHBM;
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
import gcom.gerencial.cobranca.GDocumentoTipo;
import gcom.gerencial.micromedicao.GRota;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @author Ivan Sérgio
 * @created 11/05/2007
 */
public class ControladorGerencialArrecadacaoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioGerencialArrecadacao repositorioGerencialArrecadacao = null;

	private IRepositorioGerencialCadastro repositorioGerencialCadastro = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioGerencialArrecadacao = RepositorioGerencialArrecadacaoHBM.getInstancia();
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
	 * Método que gera o resumo da Arrecadacao
	 * [UC0551] - Gerar Resumo da Arrecadacao Agua/Esgoto
	 * - Gerar Resumo da Arrecadacao Outros
	 * - Gerar Resumo da Arrecadacao Credito
	 * 
	 * @author Ivan Sérgio
	 * @date 17/05/2007, 29/06/2007, 17/10/2007, 12/11/2007
	 */
	public void gerarResumoArrecadacao(int idSetorComercial, int anoMesReferenciaArrecadacao, int idFuncionalidadeIniciada)
					throws ControladorException{

		/**********************************************************
		 * Inicio do Resumo Arrecadacao Agua Esgoto
		 *********************************************************/
		int idUnidadeIniciada = 0;

		// -------------------------
		// Registrar o início do processamento da Unidade de
		// Processamento do Batch
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetorComercial);

		try{
			System.out.print("=====> SETOR : " + idSetorComercial);

			List imoveisResumoArrecadacaoAguaEsgoto = this.repositorioGerencialArrecadacao.getImoveisResumoArrecadacaoAguaEsgoto(
							idSetorComercial, anoMesReferenciaArrecadacao);

			List<ResumoArrecadacaoAguaEsgotoHelper> listaSimplificada = new ArrayList();

			// Declaracao dos Objetos Usados
			Object obj = null;
			Object[] retorno = null;
			ResumoArrecadacaoAguaEsgotoHelper helper = null;
			// Cliente clienteTemp = null;
			Categoria categoria = null;
			ImovelSubcategoria subcategoria = null;
			BigDecimal valorAgua = null;
			BigDecimal valorEsgoto = null;
			BigDecimal valorNaoIdentificado = null;
			BigDecimal valorImpostos = null;
			short indicadorHidrometro;
			Integer anoMesReferenciaDocumento = null;
			int posicao = 0;
			ResumoArrecadacaoAguaEsgotoHelper jaCadastrado = null;

			System.out.print("=====> TOTAL : " + imoveisResumoArrecadacaoAguaEsgoto.size());

			// pra cada objeto obter a categoria
			for(int i = 0; i < imoveisResumoArrecadacaoAguaEsgoto.size(); i++){
				obj = imoveisResumoArrecadacaoAguaEsgoto.get(i);

				if(obj instanceof Object[]){
					retorno = (Object[]) obj;

					// Montamos um objeto de resumo, com as informacoes do retorno
					helper = new ResumoArrecadacaoAguaEsgotoHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de Negocio
									(Integer) retorno[3], // Codigo do Elo
									(Integer) retorno[4], // Localidade
									(Integer) retorno[5], // Setor Comercial
									(Integer) retorno[6], // Rota
									(Integer) retorno[7], // Quadra
									(Integer) retorno[8], // Perfil Imovel
									(Integer) retorno[9], // Situação Ligação Água
									(Integer) retorno[10], // Situação Ligação Esgoto
									(Integer) retorno[11], // Perfil da Ligação da Água
									(Integer) retorno[12], // Perfil da Ligação do Esgoto
									(Integer) retorno[13], // Tipo Documento
									(Integer) retorno[14], // Situação do Pagamento
									(Integer) retorno[15], // Indicador de Contas Recebidas
									(Integer) retorno[16], // Época do Pagamento
									(Integer) retorno[17], // Codigo do Setor Comercial
									(Integer) retorno[18], // Número da Quadra
									(Integer) retorno[22], // Forma de Arrecadacao
									(Integer) retorno[23]);// Agenete Arrecadador

					Integer idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo
					// processado

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsferaPoder(repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdTipoCliente(repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
						}
					}

					// Verifica Indicador de Hidrometro para o imovel
					// [UC0307] - Obter Indicador de Existência de Hidrômetro
					String indicadorHidrometroString = new Integer(this.getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
									idImovel)).toString();
					indicadorHidrometro = new Short(indicadorHidrometroString);
					helper.setIndicadorHidrometro(indicadorHidrometro);

					valorAgua = new BigDecimal(0);
					if(retorno[19] != null){
						valorAgua = (BigDecimal) retorno[19];
					}

					valorEsgoto = new BigDecimal(0);
					if(retorno[20] != null){
						valorEsgoto = (BigDecimal) retorno[20];
					}

					valorNaoIdentificado = new BigDecimal(0);
					if(retorno[21] != null){
						valorNaoIdentificado = (BigDecimal) retorno[21];
					}

					valorImpostos = new BigDecimal(0);
					if(retorno[24] != null){
						valorImpostos = (BigDecimal) retorno[24];
					}

					if(retorno[25] != null){
						anoMesReferenciaDocumento = (Integer) retorno[25];
						helper.setAnoMesReferenciaDocumento(anoMesReferenciaDocumento);
					}

					// Verifica Epoca de Pagamento
					Integer epocaPagamento = null;
					String dataPagamento = retorno[26].toString();
					String dataVencimentoConta = "";
					if(retorno[27] != null){
						dataVencimentoConta = retorno[27].toString();
					}
					String dataVencimentoGuia = "";
					if(retorno[28] != null){
						dataVencimentoGuia = retorno[28].toString();
					}

					if(helper.getIdEpocaPagamento() == 99){
						// Verifica Epoca de Pagamento para CONTA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoConta);
						helper.setIdEpocaPagamento(epocaPagamento);
					}else if(helper.getIdEpocaPagamento() == 98){
						// Verifica Epoca de Pagamento para GUIA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoGuia);
						helper.setIdEpocaPagamento(epocaPagamento);
					}

					// Se ja existe um objeto igual a ele entao soma os
					// valores Agua, Esgoto e Não Identificado.
					// Um objeto eh igual ao outro se ele tem todos as
					// informacoes de quebra iguais.
					if(listaSimplificada.contains(helper)){
						posicao = listaSimplificada.indexOf(helper);
						jaCadastrado = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(posicao);

						// Somatorio de Valor Agua
						jaCadastrado.setValorAgua(jaCadastrado.getValorAgua().add(valorAgua));

						// Somatorio de Valor Esgoto
						jaCadastrado.setValorEsgoto(jaCadastrado.getValorEsgoto().add(valorEsgoto));

						// Somatorio de Valor Nao Identificado
						jaCadastrado.setValorNaoIdentificado(jaCadastrado.getValorNaoIdentificado().add(valorNaoIdentificado));

						// Somatorio de Valor Impostos
						jaCadastrado.setValorImpostos(jaCadastrado.getValorImpostos().add(valorImpostos));

						// Incrementamos a Quantidade de Contas
						jaCadastrado.setQuantidadeContas(jaCadastrado.getQuantidadeContas().intValue() + 1);

					}else{
						// Incluimos o Valor Agua
						helper.setValorAgua(helper.getValorAgua().add(valorAgua));

						// Incluimos o Valor Esgoto
						helper.setValorEsgoto(helper.getValorEsgoto().add(valorEsgoto));

						// Incluimos o Valor Nao Identificado
						helper.setValorNaoIdentificado(helper.getValorNaoIdentificado().add(valorNaoIdentificado));

						// Incluimos o Valor Impostos
						helper.setValorImpostos(helper.getValorImpostos().add(valorImpostos));

						// Incrementamos a Quantidade de Contas
						helper.setQuantidadeContas(helper.getQuantidadeContas().intValue() + 1);

						listaSimplificada.add(helper);
					}
				}
			}

			imoveisResumoArrecadacaoAguaEsgoto.clear();
			imoveisResumoArrecadacaoAguaEsgoto = null;
			/**
			 * para todas as ResumoArrecadacaoAguaEsgotoHelper cria
			 * UnResumoArrecadacao
			 */
			// Declaracao dos Objetos Usados
			int anoMesReferencia = 0;
			int codigoSetorComercial = 0;
			int numeroQuadra = 0;
			int quantidadeContas = 0;
			short indicadorRecebidasNomes = 0;
			BigDecimal volumeAgua = null;
			BigDecimal volumeEsgoto = null;
			Date ultimaAlteracao = null;
			BigDecimal volumeNaoIdentificado = null;

			GSubcategoria subCategoria = null;
			GClienteTipo clienteTipo = null;
			GLigacaoAguaSituacao ligacaoAguaSituacao = null;
			GUnidadeNegocio unidadeNegocio = null;
			GLocalidade localidade = null;
			GLocalidade elo = null;
			GQuadra quadra = null;
			GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			GLigacaoEsgotoPerfil ligacaoEsgotoPerfil = null;
			GGerenciaRegional gerenciaRegional = null;
			GSetorComercial setorComercial = null;
			GDocumentoTipo documentoTipo = null;
			GPagamentoSituacao pagamentoSituacao = null;
			GLigacaoAguaPerfil ligacaoAguaPerfil = null;
			GEpocaPagamento epocaPagamento = null;
			GEsferaPoder esferaPoder = null;
			GCategoria Gcategoria = null;
			GImovelPerfil imovelPerfil = null;
			GRota rota = null;
			GArrecadacaoForma formaArrecadacao = null;
			GArrecadador agenteArrecadador = null;
			UnResumoArrecadacao resumoArrecadacaoAguaEsgoto = null;

			// Preenche os Objetos com valor 0 que serão usados em Credito e Outros
			Integer creditoOrigem = null;
			Integer itemContabelOutros = null;
			Integer itemContabelCredito = null;
			Integer financiamentoTipo = null;

			for(int i = 0; i < listaSimplificada.size(); i++){
				helper = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(i);

				// Montamos todo o agrupamento necessario

				// Mes ano de referencia
				anoMesReferencia = anoMesReferenciaArrecadacao;

				// Codigo Setor Comercial
				if(helper.getIdSetorComercial() != null){
					codigoSetorComercial = (helper.getIdCodigoSetorComercial());
				}

				// Numero da quadra
				if(helper.getIdNumeroQuadra() != null){
					numeroQuadra = (helper.getIdNumeroQuadra());
				}

				// Quantidades de Conta
				if(helper.getQuantidadeContas() != null){
					quantidadeContas = (helper.getQuantidadeContas());
				}

				// Indicador de Recebidas no Mes
				if(helper.getIdIndicadorContasRecebidas() != null){
					indicadorRecebidasNomes = (helper.getIdIndicadorContasRecebidas().shortValue());
				}

				// Volume Agua
				if(helper.getValorAgua() != null){
					volumeAgua = (helper.getValorAgua());
				}

				// Volume Esgoto
				if(helper.getValorAgua() != null){
					volumeEsgoto = (helper.getValorEsgoto());
				}

				// Ultima Alteracao
				ultimaAlteracao = new Date();

				// Volume Nao Identificado
				if(helper.getValorNaoIdentificado() != null){
					volumeNaoIdentificado = (helper.getValorNaoIdentificado());
				}

				// Principal SubCategoria da Principal Categoria do Imovel
				if(helper.getIdSubCategoria() != null){
					subCategoria = new GSubcategoria();
					subCategoria.setId(helper.getIdSubCategoria());
				}

				// Tipo do Cliente Responsável
				if(helper.getIdTipoCliente() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(helper.getIdTipoCliente());
				}

				// Situacao da Ligação da Água
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Unidade de Negocio
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				if(helper.getIdCodigoElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdCodigoElo());
				}

				// Quadra
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Situação da Ligação do Esgoto
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Perfil da Ligação do Esgoto
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					ligacaoEsgotoPerfil = new GLigacaoEsgotoPerfil();
					ligacaoEsgotoPerfil.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Gerencia Regional
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Setor comercial
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Tipo do Documento
				if(helper.getIdTipoDocumento() != null){
					documentoTipo = new GDocumentoTipo();
					documentoTipo.setId(helper.getIdTipoDocumento());
				}

				// Situacao do Pagamento
				if(helper.getIdSituacaoPagamento() != null){
					pagamentoSituacao = new GPagamentoSituacao();
					pagamentoSituacao.setId(helper.getIdSituacaoPagamento());
				}

				// Perfil da Ligação da Água
				if(helper.getIdPerfilLigacaoAgua() != null){
					ligacaoAguaPerfil = new GLigacaoAguaPerfil();
					ligacaoAguaPerfil.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Epoca do Pagamento
				if(helper.getIdEpocaPagamento() != null){
					epocaPagamento = new GEpocaPagamento();
					epocaPagamento.setId(helper.getIdEpocaPagamento());
				}

				// Esfera de Poder
				if(helper.getIdEsferaPoder() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsferaPoder());
				}

				// Principal Categoria do Imovel
				if(helper.getIdCategoria() != null){
					Gcategoria = new GCategoria();
					Gcategoria.setId(helper.getIdCategoria());
				}

				// Perfil do Imovel
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Rota
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Forma Arrecadacao
				if(helper.getIdFormaArrecadacao() != null){
					formaArrecadacao = new GArrecadacaoForma();
					formaArrecadacao.setId(helper.getIdFormaArrecadacao());
				}

				// Agente Arrecadador
				if(helper.getIdAgenteArrecadador() != null){
					agenteArrecadador = new GArrecadador();
					agenteArrecadador.setId(helper.getIdAgenteArrecadador());
				}

				// Valor Impostos
				valorImpostos = new BigDecimal(0);
				if(helper.getValorImpostos() != null){
					valorImpostos = helper.getValorImpostos();
				}

				// Indicador Hidrometro
				indicadorHidrometro = helper.getIndicadorHidrometro();

				// Ano/Mes Referencia Documento
				anoMesReferenciaDocumento = null;
				if(helper.getAnoMesReferenciaDocumento() != null){
					anoMesReferenciaDocumento = helper.getAnoMesReferenciaDocumento();
				}

				// Criamos um resumo de Arrecadacao Agua/Esgoto
				resumoArrecadacaoAguaEsgoto = new UnResumoArrecadacao(anoMesReferencia, codigoSetorComercial, numeroQuadra,
								quantidadeContas, indicadorRecebidasNomes, volumeAgua, volumeEsgoto, ultimaAlteracao,
								volumeNaoIdentificado, subCategoria, clienteTipo, ligacaoAguaSituacao, unidadeNegocio, localidade, elo,
								quadra, ligacaoEsgotoSituacao, ligacaoEsgotoPerfil, gerenciaRegional, setorComercial, documentoTipo,
								pagamentoSituacao, ligacaoAguaPerfil, epocaPagamento, esferaPoder, Gcategoria, imovelPerfil, rota,
								valorImpostos, indicadorHidrometro, anoMesReferenciaDocumento);

				resumoArrecadacaoAguaEsgoto.setGerArrecadacaoForma(formaArrecadacao);
				resumoArrecadacaoAguaEsgoto.setGerArrecadador(agenteArrecadador);

				// Preenche os Objetos com valor 0 que serão usados em Credito e Outros
				creditoOrigem = new Integer(0);
				itemContabelOutros = new Integer(0);
				itemContabelCredito = new Integer(0);
				financiamentoTipo = new Integer(0);

				resumoArrecadacaoAguaEsgoto.setCreditoOrigemIdCredito(creditoOrigem);
				resumoArrecadacaoAguaEsgoto.setLancamentoItemIdCredito(itemContabelCredito);
				resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosCredito(new BigDecimal(0));

				resumoArrecadacaoAguaEsgoto.setLancamentoItemIdOutros(itemContabelOutros);
				resumoArrecadacaoAguaEsgoto.setFinanciamentoTipoIdOutros(financiamentoTipo);
				resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosOutros(new BigDecimal(0));

				// Insere em UN_RESUMO_ARRECADACAO_AGUA_ESGOTO
				System.out.print("=====> Inserindo Resumo Arrecadacao <=====");
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoArrecadacaoAguaEsgoto);

				helper = null;
				resumoArrecadacaoAguaEsgoto = null;
			}
			System.out.print("=================> FIM do Resumo Arrecadacao SETOR: " + idSetorComercial
							+ " <=======================================");

			/**********************************************************************************
			 * INICIO DO RESUMO ARRECADACAO OUTROS
			 **********************************************************************************/
			this.gerarResumoArrecadacaoOutros(idSetorComercial, anoMesReferenciaArrecadacao);
			/**********************************************************************************/

			/**********************************************************************************
			 * INICIO DO RESUMO ARRECADACAO CREDITOS
			 **********************************************************************************/
			this.gerarResumoArrecadacaoCreditos(idSetorComercial, anoMesReferenciaArrecadacao);
			/**********************************************************************************/

			// --------------------------------------------------------
			//
			// Registrar o fim da execução da Unidade de Processamento
			//
			// --------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido
			System.out.println("ERROR NO SETOR -> " + idSetorComercial);
			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}// gerarResumoArrecadacao

	/**
	 * @author Ivan Sérgio
	 * @date 09/01/2008
	 * @param idSetorComercial
	 * @param anoMesReferenciaArrecadacao
	 * @throws ControladorException
	 */
	public void gerarResumoArrecadacaoOutros(int idSetorComercial, int anoMesReferenciaArrecadacao) throws ControladorException{

		try{
			List imoveisResumoArrecadacaoOutros = this.repositorioGerencialArrecadacao.getImoveisResumoArrecadacaoOutros(idSetorComercial,
							anoMesReferenciaArrecadacao);

			List<ResumoArrecadacaoAguaEsgotoHelper> listaSimplificada = new ArrayList();

			// Declaracao dos Objetos Usados
			Object obj = null;
			Object[] retorno = null;
			ResumoArrecadacaoAguaEsgotoHelper helper = null;
			// Cliente clienteTemp = null;
			Categoria categoria = null;
			ImovelSubcategoria subcategoria = null;
			BigDecimal valorImpostos = null;
			short indicadorHidrometro;
			Integer anoMesReferenciaDocumento = null;
			int posicao = 0;
			ResumoArrecadacaoAguaEsgotoHelper jaCadastrado = null;

			for(int i = 0; i < imoveisResumoArrecadacaoOutros.size(); i++){
				obj = imoveisResumoArrecadacaoOutros.get(i);

				if(obj instanceof Object[]){
					retorno = (Object[]) obj;

					// Se o Tipo de Financiamento for igual a NULL, despresar o Pagamento
					if(retorno[25] != null){

						// Montamos um objeto de resumo, com as informacoes do retorno
						helper = new ResumoArrecadacaoAguaEsgotoHelper((Integer) retorno[1], // Gerencia
										// Regional
										(Integer) retorno[2], // Unidade de Negocio
										(Integer) retorno[3], // Codigo do Elo
										(Integer) retorno[4], // Localidade
										(Integer) retorno[5], // Setor Comercial
										(Integer) retorno[6], // Rota
										(Integer) retorno[7], // Quadra
										(Integer) retorno[8], // Perfil Imovel
										(Integer) retorno[9], // Situação Ligação Água
										(Integer) retorno[10], // Situação Ligação Esgoto
										(Integer) retorno[11], // Perfil da Ligação da Água
										(Integer) retorno[12], // Perfil da Ligação do Esgoto
										(Integer) retorno[13], // Tipo Documento
										(Integer) retorno[14], // Situação do Pagamento
										(Integer) retorno[15], // Indicador de Contas Recebidas
										(Integer) retorno[16], // Época do Pagamento
										(Integer) retorno[17], // Codigo do Setor Comercial
										(Integer) retorno[18], // Número da Quadra
										(Integer) retorno[19], // Forma de Arrecadacao
										(Integer) retorno[20]);// Agente Arrecadador

						Integer idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo
						// processado

						// Pesquisamos a esfera de poder do cliente responsavel
						helper.setIdEsferaPoder(repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
						// Pesquisamos o tipo de cliente responsavel do imovel
						helper.setIdTipoCliente(repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

						// pesquisando a categoria
						// [UC0306] - Obtter principal categoria do imóvel
						categoria = null;
						categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

						if(categoria != null){
							helper.setIdCategoria(categoria.getId());

							// Pesquisando a principal subcategoria
							subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

							if(subcategoria != null){
								helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
							}
						}

						// Verifica Indicador de Hidrometro para o imovel
						// [UC0307] - Obter Indicador de Existência de Hidrômetro
						String indicadorHidrometroString = new Integer(this.getControladorImovel()
										.obterIndicadorExistenciaHidrometroImovel(idImovel)).toString();
						indicadorHidrometro = new Short(indicadorHidrometroString);
						helper.setIndicadorHidrometro(indicadorHidrometro);

						if(retorno[21] != null){
							anoMesReferenciaDocumento = (Integer) retorno[21];
							helper.setAnoMesReferenciaDocumento(anoMesReferenciaDocumento);
						}

						// Verifica Epoca de Pagamento
						Integer epocaPagamento = null;
						String dataPagamento = retorno[22].toString();
						String dataVencimentoConta = "";
						if(retorno[23] != null){
							dataVencimentoConta = retorno[23].toString();
						}
						String dataVencimentoGuia = "";
						if(retorno[24] != null){
							dataVencimentoGuia = retorno[24].toString();
						}

						/*********************************************************************
						 * Dados da Arrecadacao OUTROS
						 *********************************************************************/
						Integer tipoFinanciamento = null;
						if(retorno[25] != null){
							tipoFinanciamento = (Integer) retorno[25];
							helper.setIdTipoFinanciamento(tipoFinanciamento);
						}

						Integer lancamentoItemContabilOutros = null;
						if(retorno[26] != null){
							lancamentoItemContabilOutros = (Integer) retorno[26];
							helper.setIdLancamentoItemContabilOutros(lancamentoItemContabilOutros);
						}

						BigDecimal valorDebitos = new BigDecimal(0);
						if(retorno[27] != null){
							valorDebitos = (BigDecimal) retorno[27];
						}

						/*********************************************************************/

						if(helper.getIdEpocaPagamento() == 99){
							// Verifica Epoca de Pagamento para CONTA
							epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoConta);
							helper.setIdEpocaPagamento(epocaPagamento);
						}else if(helper.getIdEpocaPagamento() == 98){
							// Verifica Epoca de Pagamento para GUIA
							epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoGuia);
							helper.setIdEpocaPagamento(epocaPagamento);
						}

						// Se ja existe um objeto igual a ele entao soma os
						// valores Agua, Esgoto e Não Identificado.
						// Um objeto eh igual ao outro se ele tem todos as
						// informacoes de quebra iguais.
						if(listaSimplificada.contains(helper)){
							posicao = listaSimplificada.indexOf(helper);
							jaCadastrado = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(posicao);

							// Somatorio de Valor Debitos
							jaCadastrado.setValorDebitos(jaCadastrado.getValorDebitos().add(valorDebitos));
						}else{
							// Incluimos o Valor Debitos
							helper.setValorDebitos(valorDebitos);

							listaSimplificada.add(helper);
						}
					}
				}
			}

			imoveisResumoArrecadacaoOutros.clear();
			imoveisResumoArrecadacaoOutros = null;

			/**
			 * para todas as ResumoArrecadacaoAguaEsgotoHelper cria
			 * UnResumoArrecadacao
			 */
			// Declaracao dos Objetos Usados
			int anoMesReferencia = 0;
			int codigoSetorComercial = 0;
			int numeroQuadra = 0;
			int quantidadeContas = 0;
			short indicadorRecebidasNomes = 0;
			BigDecimal volumeAgua = new BigDecimal(0);
			BigDecimal volumeEsgoto = new BigDecimal(0);
			BigDecimal volumeNaoIdentificado = new BigDecimal(0);
			valorImpostos = new BigDecimal(0);
			Date ultimaAlteracao = null;

			GSubcategoria subCategoria = null;
			GClienteTipo clienteTipo = null;
			GLigacaoAguaSituacao ligacaoAguaSituacao = null;
			GUnidadeNegocio unidadeNegocio = null;
			GLocalidade localidade = null;
			GLocalidade elo = null;
			GQuadra quadra = null;
			GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			GLigacaoEsgotoPerfil ligacaoEsgotoPerfil = null;
			GGerenciaRegional gerenciaRegional = null;
			GSetorComercial setorComercial = null;
			GDocumentoTipo documentoTipo = null;
			GPagamentoSituacao pagamentoSituacao = null;
			GLigacaoAguaPerfil ligacaoAguaPerfil = null;
			GEpocaPagamento epocaPagamento = null;
			GEsferaPoder esferaPoder = null;
			GCategoria Gcategoria = null;
			GImovelPerfil imovelPerfil = null;
			GRota rota = null;
			GArrecadacaoForma formaArrecadacao = null;
			GArrecadador agenteArrecadador = null;
			UnResumoArrecadacao resumoArrecadacaoAguaEsgoto = null;

			BigDecimal valorDebito = new BigDecimal(0);

			// Preenche os Objetos com valor 0 que serão usados em Credito e Outros
			Integer creditoOrigem = null;
			Integer itemContabelOutros = null;
			Integer itemContabelCredito = null;
			Integer financiamentoTipoOutros = null;

			for(int i = 0; i < listaSimplificada.size(); i++){
				helper = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(i);

				// Mes ano de referencia
				anoMesReferencia = anoMesReferenciaArrecadacao;

				// Codigo Setor Comercial
				if(helper.getIdSetorComercial() != null){
					codigoSetorComercial = (helper.getIdCodigoSetorComercial());
				}

				// Numero da quadra
				if(helper.getIdNumeroQuadra() != null){
					numeroQuadra = (helper.getIdNumeroQuadra());
				}

				// Quantidades de Conta
				// if (helper.getQuantidadeContas() != null) {
				// quantidadeContas = (helper.getQuantidadeContas());
				// }

				// Indicador de Recebidas no Mes
				if(helper.getIdIndicadorContasRecebidas() != null){
					indicadorRecebidasNomes = (helper.getIdIndicadorContasRecebidas().shortValue());
				}

				// Volume Agua
				// if (helper.getValorAgua() != null) {
				// volumeAgua = (helper.getValorAgua());
				// }

				// Volume Esgoto
				// if (helper.getValorAgua() != null) {
				// volumeEsgoto = (helper.getValorEsgoto());
				// }

				// Volume Nao Identificado
				// if (helper.getValorNaoIdentificado() != null) {
				// volumeNaoIdentificado = (helper.getValorNaoIdentificado());
				// }

				// Valor Impostos
				// valorImpostos = new BigDecimal(0);
				// if (helper.getValorImpostos() != null) {
				// valorImpostos = helper.getValorImpostos();
				// }

				// Ultima Alteracao
				ultimaAlteracao = new Date();

				// Principal SubCategoria da Principal Categoria do Imovel
				if(helper.getIdSubCategoria() != null){
					subCategoria = new GSubcategoria();
					subCategoria.setId(helper.getIdSubCategoria());
				}

				// Tipo do Cliente Responsável
				if(helper.getIdTipoCliente() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(helper.getIdTipoCliente());
				}

				// Situacao da Ligação da Água
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Unidade de Negocio
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				if(helper.getIdCodigoElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdCodigoElo());
				}

				// Quadra
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Situação da Ligação do Esgoto
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Perfil da Ligação do Esgoto
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					ligacaoEsgotoPerfil = new GLigacaoEsgotoPerfil();
					ligacaoEsgotoPerfil.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Gerencia Regional
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Setor comercial
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Tipo do Documento
				if(helper.getIdTipoDocumento() != null){
					documentoTipo = new GDocumentoTipo();
					documentoTipo.setId(helper.getIdTipoDocumento());
				}

				// Situacao do Pagamento
				if(helper.getIdSituacaoPagamento() != null){
					pagamentoSituacao = new GPagamentoSituacao();
					pagamentoSituacao.setId(helper.getIdSituacaoPagamento());
				}

				// Perfil da Ligação da Água
				if(helper.getIdPerfilLigacaoAgua() != null){
					ligacaoAguaPerfil = new GLigacaoAguaPerfil();
					ligacaoAguaPerfil.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Epoca do Pagamento
				if(helper.getIdEpocaPagamento() != null){
					epocaPagamento = new GEpocaPagamento();
					epocaPagamento.setId(helper.getIdEpocaPagamento());
				}

				// Esfera de Poder
				if(helper.getIdEsferaPoder() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsferaPoder());
				}

				// Principal Categoria do Imovel
				if(helper.getIdCategoria() != null){
					Gcategoria = new GCategoria();
					Gcategoria.setId(helper.getIdCategoria());
				}

				// Perfil do Imovel
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Rota
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Forma Arrecadacao
				if(helper.getIdFormaArrecadacao() != null){
					formaArrecadacao = new GArrecadacaoForma();
					formaArrecadacao.setId(helper.getIdFormaArrecadacao());
				}

				// Agente Arrecadador
				if(helper.getIdAgenteArrecadador() != null){
					agenteArrecadador = new GArrecadador();
					agenteArrecadador.setId(helper.getIdAgenteArrecadador());
				}

				// Indicador Hidrometro
				indicadorHidrometro = helper.getIndicadorHidrometro();

				// Ano/Mes Referencia Documento
				anoMesReferenciaDocumento = null;
				if(helper.getAnoMesReferenciaDocumento() != null){
					anoMesReferenciaDocumento = helper.getAnoMesReferenciaDocumento();
				}

				// Criamos um resumo de Arrecadacao Agua/Esgoto
				resumoArrecadacaoAguaEsgoto = new UnResumoArrecadacao(anoMesReferencia, codigoSetorComercial, numeroQuadra,
								quantidadeContas, indicadorRecebidasNomes, volumeAgua, volumeEsgoto, ultimaAlteracao,
								volumeNaoIdentificado, subCategoria, clienteTipo, ligacaoAguaSituacao, unidadeNegocio, localidade, elo,
								quadra, ligacaoEsgotoSituacao, ligacaoEsgotoPerfil, gerenciaRegional, setorComercial, documentoTipo,
								pagamentoSituacao, ligacaoAguaPerfil, epocaPagamento, esferaPoder, Gcategoria, imovelPerfil, rota,
								valorImpostos, indicadorHidrometro, anoMesReferenciaDocumento);

				resumoArrecadacaoAguaEsgoto.setGerArrecadacaoForma(formaArrecadacao);
				resumoArrecadacaoAguaEsgoto.setGerArrecadador(agenteArrecadador);

				// Preenche os Objetos com valor 0 que serão usados em CREDITO
				creditoOrigem = new Integer(0);
				itemContabelCredito = new Integer(0);

				resumoArrecadacaoAguaEsgoto.setCreditoOrigemIdCredito(creditoOrigem);
				resumoArrecadacaoAguaEsgoto.setLancamentoItemIdCredito(itemContabelCredito);
				resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosCredito(new BigDecimal(0));

				/*********************************************************************************
				 * Arrecadacao OUTROS
				 *********************************************************************************/
				// Valor Debitos
				if(helper.getValorDebitos() != null){
					valorDebito = (helper.getValorDebitos());
				}

				// Lancamento Item Contabel
				if(helper.getIdLancamentoItemContabilOutros() != null){
					itemContabelOutros = helper.getIdLancamentoItemContabilOutros();
				}

				// Financiamento Tipo
				financiamentoTipoOutros = null;
				if(helper.getIdTipoFinanciamento() != null){
					financiamentoTipoOutros = helper.getIdTipoFinanciamento();
				}

				// Se o Tipo de Financiamento for igual a NULL, despresar o Pagamento
				if(financiamentoTipoOutros != null){
					resumoArrecadacaoAguaEsgoto.setLancamentoItemIdOutros(itemContabelOutros);
					resumoArrecadacaoAguaEsgoto.setFinanciamentoTipoIdOutros(financiamentoTipoOutros);
					resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosOutros(valorDebito);

					// Inicio do Resumo Arrecadacao Outros
					System.out.print("=====> Inserindo Resumo Arrecadacao OUTROS <=====");
					this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoArrecadacaoAguaEsgoto);
				}

				helper = null;
				resumoArrecadacaoAguaEsgoto = null;
			}
			System.out.print("=================> FIM do Resumo Arrecadacao OUTROS SETOR: " + idSetorComercial + " <=================");

		}catch(Exception ex){
			System.out.println("RESUMO ARRECADACAO OUTROS ERRO NO SETOR -> " + idSetorComercial);
			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}// gerarResumoArrecadacaoOUTROS

	/**
	 * @author Ivan Sérgio
	 * @date 10/01/2008
	 * @param idSetorComercial
	 * @param anoMesReferenciaArrecadacao
	 * @throws ControladorException
	 */
	public void gerarResumoArrecadacaoCreditos(int idSetorComercial, int anoMesReferenciaArrecadacao) throws ControladorException{

		try{
			List imoveisResumoArrecadacaoOutros = this.repositorioGerencialArrecadacao.getImoveisResumoArrecadacaoCreditos(
							idSetorComercial, anoMesReferenciaArrecadacao);

			List<ResumoArrecadacaoAguaEsgotoHelper> listaSimplificada = new ArrayList();

			// Declaracao dos Objetos Usados
			Object obj = null;
			Object[] retorno = null;
			ResumoArrecadacaoAguaEsgotoHelper helper = null;
			// Cliente clienteTemp = null;
			Categoria categoria = null;
			ImovelSubcategoria subcategoria = null;
			BigDecimal valorImpostos = null;
			short indicadorHidrometro;
			Integer anoMesReferenciaDocumento = null;
			int posicao = 0;
			ResumoArrecadacaoAguaEsgotoHelper jaCadastrado = null;

			for(int i = 0; i < imoveisResumoArrecadacaoOutros.size(); i++){
				obj = imoveisResumoArrecadacaoOutros.get(i);

				if(obj instanceof Object[]){
					retorno = (Object[]) obj;

					// Montamos um objeto de resumo, com as informacoes do retorno
					helper = new ResumoArrecadacaoAguaEsgotoHelper((Integer) retorno[1], // Gerencia
									// Regional
									(Integer) retorno[2], // Unidade de Negocio
									(Integer) retorno[3], // Codigo do Elo
									(Integer) retorno[4], // Localidade
									(Integer) retorno[5], // Setor Comercial
									(Integer) retorno[6], // Rota
									(Integer) retorno[7], // Quadra
									(Integer) retorno[8], // Perfil Imovel
									(Integer) retorno[9], // Situação Ligação Água
									(Integer) retorno[10], // Situação Ligação Esgoto
									(Integer) retorno[11], // Perfil da Ligação da Água
									(Integer) retorno[12], // Perfil da Ligação do Esgoto
									(Integer) retorno[13], // Tipo Documento
									(Integer) retorno[14], // Situação do Pagamento
									(Integer) retorno[15], // Indicador de Contas Recebidas
									(Integer) retorno[16], // Época do Pagamento
									(Integer) retorno[17], // Codigo do Setor Comercial
									(Integer) retorno[18], // Número da Quadra
									(Integer) retorno[19], // Forma de Arrecadacao
									(Integer) retorno[20]);// Agente Arrecadador

					Integer idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo
					// processado

					// Pesquisamos a esfera de poder do cliente responsavel
					helper.setIdEsferaPoder(repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel));
					// Pesquisamos o tipo de cliente responsavel do imovel
					helper.setIdTipoCliente(repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel));

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

					if(categoria != null){
						helper.setIdCategoria(categoria.getId());

						// Pesquisando a principal subcategoria
						subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

						if(subcategoria != null){
							helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
						}
					}

					// Verifica Indicador de Hidrometro para o imovel
					// [UC0307] - Obter Indicador de Existência de Hidrômetro
					String indicadorHidrometroString = new Integer(this.getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
									idImovel)).toString();
					indicadorHidrometro = new Short(indicadorHidrometroString);
					helper.setIndicadorHidrometro(indicadorHidrometro);

					if(retorno[21] != null){
						anoMesReferenciaDocumento = (Integer) retorno[21];
						helper.setAnoMesReferenciaDocumento(anoMesReferenciaDocumento);
					}

					// Verifica Epoca de Pagamento
					Integer epocaPagamento = null;
					String dataPagamento = retorno[22].toString();
					String dataVencimentoConta = "";
					if(retorno[23] != null){
						dataVencimentoConta = retorno[23].toString();
					}
					String dataVencimentoGuia = "";
					if(retorno[24] != null){
						dataVencimentoGuia = retorno[24].toString();
					}

					/*********************************************************************
					 * Dados da Arrecadacao CREDITOS
					 *********************************************************************/
					// Credito Origem
					Integer creditoOrigem = null;
					if(retorno[25] != null){
						creditoOrigem = (Integer) retorno[25];
						helper.setIdCreditoOrigem(creditoOrigem);
					}

					// Lancamento Item Contabel
					Integer itemContabelCredito = null;
					if(retorno[26] != null){
						itemContabelCredito = (Integer) retorno[26];
						helper.setIdLancamentoItemContabilCredito(itemContabelCredito);
					}

					// Valor Credito
					BigDecimal valorCredito = new BigDecimal(0);
					if(retorno[27] != null){
						valorCredito = (BigDecimal) retorno[27];
					}

					/*********************************************************************/

					if(helper.getIdEpocaPagamento() == 99){
						// Verifica Epoca de Pagamento para CONTA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoConta);
						helper.setIdEpocaPagamento(epocaPagamento);
					}else if(helper.getIdEpocaPagamento() == 98){
						// Verifica Epoca de Pagamento para GUIA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoGuia);
						helper.setIdEpocaPagamento(epocaPagamento);
					}

					// Se ja existe um objeto igual a ele entao soma os
					// valores Agua, Esgoto e Não Identificado.
					// Um objeto eh igual ao outro se ele tem todos as
					// informacoes de quebra iguais.
					if(listaSimplificada.contains(helper)){
						posicao = listaSimplificada.indexOf(helper);
						jaCadastrado = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(posicao);

						// Somatorio de Valor Debitos
						jaCadastrado.setValorCredito(jaCadastrado.getValorCredito().add(valorCredito));
					}else{
						// Incluimos o Valor Debitos
						helper.setValorCredito(valorCredito);

						listaSimplificada.add(helper);
					}
				}
			}

			imoveisResumoArrecadacaoOutros.clear();
			imoveisResumoArrecadacaoOutros = null;

			/**
			 * para todas as ResumoArrecadacaoAguaEsgotoHelper cria
			 * UnResumoArrecadacao
			 */
			// Declaracao dos Objetos Usados
			int anoMesReferencia = 0;
			int codigoSetorComercial = 0;
			int numeroQuadra = 0;
			int quantidadeContas = 0;
			short indicadorRecebidasNomes = 0;
			BigDecimal volumeAgua = new BigDecimal(0);
			BigDecimal volumeEsgoto = new BigDecimal(0);
			BigDecimal volumeNaoIdentificado = new BigDecimal(0);
			valorImpostos = new BigDecimal(0);
			Date ultimaAlteracao = null;

			GSubcategoria subCategoria = null;
			GClienteTipo clienteTipo = null;
			GLigacaoAguaSituacao ligacaoAguaSituacao = null;
			GUnidadeNegocio unidadeNegocio = null;
			GLocalidade localidade = null;
			GLocalidade elo = null;
			GQuadra quadra = null;
			GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
			GLigacaoEsgotoPerfil ligacaoEsgotoPerfil = null;
			GGerenciaRegional gerenciaRegional = null;
			GSetorComercial setorComercial = null;
			GDocumentoTipo documentoTipo = null;
			GPagamentoSituacao pagamentoSituacao = null;
			GLigacaoAguaPerfil ligacaoAguaPerfil = null;
			GEpocaPagamento epocaPagamento = null;
			GEsferaPoder esferaPoder = null;
			GCategoria Gcategoria = null;
			GImovelPerfil imovelPerfil = null;
			GRota rota = null;
			GArrecadacaoForma formaArrecadacao = null;
			GArrecadador agenteArrecadador = null;
			UnResumoArrecadacao resumoArrecadacaoAguaEsgoto = null;
			BigDecimal valorCredito = new BigDecimal(0);

			// Preenche os Objetos com valor 0 que serão usados em Credito e Outros
			Integer creditoOrigem = null;
			Integer itemContabelCredito = null;

			for(int i = 0; i < listaSimplificada.size(); i++){
				helper = (ResumoArrecadacaoAguaEsgotoHelper) listaSimplificada.get(i);

				// Mes ano de referencia
				anoMesReferencia = anoMesReferenciaArrecadacao;

				// Codigo Setor Comercial
				if(helper.getIdSetorComercial() != null){
					codigoSetorComercial = (helper.getIdCodigoSetorComercial());
				}

				// Numero da quadra
				if(helper.getIdNumeroQuadra() != null){
					numeroQuadra = (helper.getIdNumeroQuadra());
				}

				// Indicador de Recebidas no Mes
				if(helper.getIdIndicadorContasRecebidas() != null){
					indicadorRecebidasNomes = (helper.getIdIndicadorContasRecebidas().shortValue());
				}

				// Ultima Alteracao
				ultimaAlteracao = new Date();

				// Principal SubCategoria da Principal Categoria do Imovel
				if(helper.getIdSubCategoria() != null){
					subCategoria = new GSubcategoria();
					subCategoria.setId(helper.getIdSubCategoria());
				}

				// Tipo do Cliente Responsável
				if(helper.getIdTipoCliente() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(helper.getIdTipoCliente());
				}

				// Situacao da Ligação da Água
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Unidade de Negocio
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				if(helper.getIdCodigoElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdCodigoElo());
				}

				// Quadra
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Situação da Ligação do Esgoto
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Perfil da Ligação do Esgoto
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					ligacaoEsgotoPerfil = new GLigacaoEsgotoPerfil();
					ligacaoEsgotoPerfil.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Gerencia Regional
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Setor comercial
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Tipo do Documento
				if(helper.getIdTipoDocumento() != null){
					documentoTipo = new GDocumentoTipo();
					documentoTipo.setId(helper.getIdTipoDocumento());
				}

				// Situacao do Pagamento
				if(helper.getIdSituacaoPagamento() != null){
					pagamentoSituacao = new GPagamentoSituacao();
					pagamentoSituacao.setId(helper.getIdSituacaoPagamento());
				}

				// Perfil da Ligação da Água
				if(helper.getIdPerfilLigacaoAgua() != null){
					ligacaoAguaPerfil = new GLigacaoAguaPerfil();
					ligacaoAguaPerfil.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Epoca do Pagamento
				if(helper.getIdEpocaPagamento() != null){
					epocaPagamento = new GEpocaPagamento();
					epocaPagamento.setId(helper.getIdEpocaPagamento());
				}

				// Esfera de Poder
				if(helper.getIdEsferaPoder() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsferaPoder());
				}

				// Principal Categoria do Imovel
				if(helper.getIdCategoria() != null){
					Gcategoria = new GCategoria();
					Gcategoria.setId(helper.getIdCategoria());
				}

				// Perfil do Imovel
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Rota
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Forma Arrecadacao
				if(helper.getIdFormaArrecadacao() != null){
					formaArrecadacao = new GArrecadacaoForma();
					formaArrecadacao.setId(helper.getIdFormaArrecadacao());
				}

				// Agente Arrecadador
				if(helper.getIdAgenteArrecadador() != null){
					agenteArrecadador = new GArrecadador();
					agenteArrecadador.setId(helper.getIdAgenteArrecadador());
				}

				// Indicador Hidrometro
				indicadorHidrometro = helper.getIndicadorHidrometro();

				// Ano/Mes Referencia Documento
				anoMesReferenciaDocumento = null;
				if(helper.getAnoMesReferenciaDocumento() != null){
					anoMesReferenciaDocumento = helper.getAnoMesReferenciaDocumento();
				}

				// Criamos um resumo de Arrecadacao Agua/Esgoto
				resumoArrecadacaoAguaEsgoto = new UnResumoArrecadacao(anoMesReferencia, codigoSetorComercial, numeroQuadra,
								quantidadeContas, indicadorRecebidasNomes, volumeAgua, volumeEsgoto, ultimaAlteracao,
								volumeNaoIdentificado, subCategoria, clienteTipo, ligacaoAguaSituacao, unidadeNegocio, localidade, elo,
								quadra, ligacaoEsgotoSituacao, ligacaoEsgotoPerfil, gerenciaRegional, setorComercial, documentoTipo,
								pagamentoSituacao, ligacaoAguaPerfil, epocaPagamento, esferaPoder, Gcategoria, imovelPerfil, rota,
								valorImpostos, indicadorHidrometro, anoMesReferenciaDocumento);

				resumoArrecadacaoAguaEsgoto.setGerArrecadacaoForma(formaArrecadacao);
				resumoArrecadacaoAguaEsgoto.setGerArrecadador(agenteArrecadador);

				creditoOrigem = new Integer(0);
				itemContabelCredito = new Integer(0);

				// Limpa os valores da Arrecadacao OUTROS
				resumoArrecadacaoAguaEsgoto.setLancamentoItemIdOutros(new Integer(0));
				resumoArrecadacaoAguaEsgoto.setFinanciamentoTipoIdOutros(new Integer(0));
				resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosOutros(new BigDecimal(0));

				/*********************************************************************************
				 * Arrecadacao CREDITOS
				 *********************************************************************************/
				// Valor Creditos
				if(helper.getValorCredito() != null){
					valorCredito = (helper.getValorCredito());
				}

				// Lancamento Item Contabel
				if(helper.getIdLancamentoItemContabilCredito() != null){
					itemContabelCredito = helper.getIdLancamentoItemContabilCredito();
				}

				// Credito Origem
				if(helper.getIdCreditoOrigem() != null){
					creditoOrigem = helper.getIdCreditoOrigem();
				}

				resumoArrecadacaoAguaEsgoto.setValorDocumentosRecebidosCredito(valorCredito);
				resumoArrecadacaoAguaEsgoto.setLancamentoItemIdCredito(itemContabelCredito);
				resumoArrecadacaoAguaEsgoto.setCreditoOrigemIdCredito(creditoOrigem);

				// Inicio do Resumo Arrecadacao Creditos
				System.out.print("=====> Inserindo Resumo Arrecadacao CREDITOS <=====");
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoArrecadacaoAguaEsgoto);

				helper = null;
				resumoArrecadacaoAguaEsgoto = null;
			}
			System.out.print("=================> FIM do Resumo Arrecadacao CREDITOS SETOR: " + idSetorComercial + " <=================");

		}catch(Exception ex){
			System.out.println("RESUMO ARRECADACAO CREDITOS ERRO NO SETOR -> " + idSetorComercial);
			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}// gerarResumoArrecadacaoCREDITOS

	/**
	 * Método que gera o resumo da Arrecadacao
	 * [UC0551] - Gerar Resumo da Arrecadacao Outros
	 * 
	 * @author Ivan Sérgio
	 * @date 22/05/2007
	 */
	@Deprecated
	public void gerarResumoArrecadacaoOutros(int anoMesReferenciaArrecadacao, UnResumoArrecadacao resumoArrecadacaoAguaEsgoto)
					throws ControladorException{

		// Declaracao dos Objetos Usados
		Object obj = null;
		Object[] retorno = null;
		ResumoArrecadacaoOutrosHelper helper = null;
		Integer idImovel = null;
		BigDecimal valorDebito = null;
		Integer idEsferaPoder = null;
		Integer idTipoCliente = null;
		Categoria categoria = null;
		Integer idSubcategoria = 0;
		ImovelSubcategoria subcategoria = null;
		Imovel imovel = null;
		// Cliente clienteTemp = null;
		int posicao = 0;
		ResumoArrecadacaoOutrosHelper jaCadastrado = null;
		Integer epocaPagamento = null;

		try{
			List listResumoArrecadacaoOutro = this.repositorioGerencialArrecadacao.getResumoArrecadacaoOutros(anoMesReferenciaArrecadacao,
							resumoArrecadacaoAguaEsgoto);

			List<ResumoArrecadacaoOutrosHelper> listaSimplificada = new ArrayList();
			List<UnResumoArrecadacao> listaResumoArrecadacaoAguaEsgoto_Outro = new ArrayList();

			for(int i = 0; i < listResumoArrecadacaoOutro.size(); i++){
				obj = listResumoArrecadacaoOutro.get(i);

				if(obj instanceof Object[]){
					retorno = (Object[]) obj;

					// Se o Tipo de Financiamento for igual a NULL, despresar o Pagamento
					if(retorno[1] != null){
						// Montamos um objeto de resumo, com as informacoes do retorno
						helper = new ResumoArrecadacaoOutrosHelper((Integer) retorno[1], // Tipo
										// Financiamento
										(Integer) retorno[2]); // Lancamento Item Contabil

						idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo
						// processado

						valorDebito = new BigDecimal(0);
						if(retorno[3] != null){
							valorDebito = (BigDecimal) retorno[3];
						}

						// Pesquisamos a esfera de poder do cliente responsavel
						idEsferaPoder = repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel);
						// Pesquisamos o tipo de cliente responsavel do imovel
						idTipoCliente = repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel);

						// pesquisando a categoria
						// [UC0306] - Obtter principal categoria do imóvel
						categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
						if(categoria != null){
							// Pesquisando a principal subcategoria
							subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

							if(subcategoria != null){
								idSubcategoria = subcategoria.getComp_id().getSubcategoria().getId();
							}
						}

						// Verifica Epoca de Pagamento
						epocaPagamento = (Integer) retorno[4];
						String dataPagamento = retorno[5].toString();
						String dataVencimentoConta = "";
						if(retorno[6] != null){
							dataVencimentoConta = retorno[6].toString();
						}
						String dataVencimentoGuia = "";
						if(retorno[7] != null){
							dataVencimentoGuia = retorno[7].toString();
						}

						if(epocaPagamento == 99){
							// Verifica Epoca de Pagamento para CONTA
							epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoConta);
						}else if(epocaPagamento == 98){
							// Verifica Epoca de Pagamento para GUIA
							epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoGuia);
						}

						// Verificamos se a esfera de poder foi encontrada
						// [FS0002] Verificar existencia de cliente responsavel
						/*
						 * if ( idEsferaPoder == 0 ){
						 * imovel = new Imovel();
						 * imovel.setId(idImovel);
						 * clienteTemp =
						 * this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						 * if (clienteTemp != null){
						 * idEsferaPoder = clienteTemp.getClienteTipo().getEsferaPoder().getId();
						 * }
						 * }
						 * if ( idTipoCliente == 0 ){
						 * imovel = new Imovel();
						 * imovel.setId(idImovel);
						 * clienteTemp =
						 * this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						 * if (clienteTemp != null){
						 * idTipoCliente = clienteTemp.getClienteTipo().getId();
						 * }
						 * }
						 */

						// Tratamos as informações para refinar os dados com os valores de
						// Esfera de Poder, Tipo do Cliente, Categoria e Sub Categoria de
						// acordo com a quebra de Resumo Arrecadacao Agua/Esgoto
						if((idEsferaPoder.equals(resumoArrecadacaoAguaEsgoto.getGerEsferaPoder().getId().intValue()))
										& (idTipoCliente.equals(resumoArrecadacaoAguaEsgoto.getGerClienteTipo().getId()))
										& (categoria.getId().equals(resumoArrecadacaoAguaEsgoto.getGerCategoria().getId()))
										& (idSubcategoria.equals(resumoArrecadacaoAguaEsgoto.getGerSubcategoria().getId()))
										& (epocaPagamento.equals(resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento().getId()))){

							// Se ja existe um objeto igual a ele entao acumulamos os
							// valores Outros.
							// Um objeto eh igual ao outro se ele tem todos as
							// informacoes de quebra iguais.
							if(listaSimplificada.contains(helper)){
								posicao = listaSimplificada.indexOf(helper);
								jaCadastrado = (ResumoArrecadacaoOutrosHelper) listaSimplificada.get(posicao);

								// Somatorio de Valor Outros : "Valor Débitos segundo UC0553"
								jaCadastrado.setValorDebitos(jaCadastrado.getValorDebitos().add(valorDebito));

							}else{
								// Incluimos o Valor Outros : "Valor Débitos segundo UC0553"
								helper.setValorDebitos(helper.getValorDebitos().add(valorDebito));

								listaSimplificada.add(helper);
							}
						}

						helper = null;

					}
				}

				obj = null;
			}

			listResumoArrecadacaoOutro.clear();
			listResumoArrecadacaoOutro = null;

			/**
			 * para todas as ResumoArrecadacaoOutrosHelper cria
			 * UnResumoArrecadacaoOutro
			 */
			// BigDecimal valorDebito = null;
			Integer itemContabel = null;
			Integer financiamentoTipo = null;
			UnResumoArrecadacao resumoArrecadacaoAguaEsgoto_Outro = null;

			// Usados apenas para cadastrar em Credito
			Integer creditoOrigem = null;
			Integer itemContabelCredito = null;

			for(int i = 0; i < listaSimplificada.size(); i++){
				helper = (ResumoArrecadacaoOutrosHelper) listaSimplificada.get(i);

				// Montamos todo o agrupamento necessario
				// Valor Debito
				if(helper.getValorDebitos() != null){
					valorDebito = (helper.getValorDebitos());
				}

				// Lancamento Item Contabel
				if(helper.getIdLancamentoItemContabil() != null){
					itemContabel = helper.getIdLancamentoItemContabil();
				}

				// Financiamento Tipo
				if(helper.getIdTipoFinanciamento() != null){
					financiamentoTipo = helper.getIdTipoFinanciamento();
				}

				resumoArrecadacaoAguaEsgoto_Outro = new UnResumoArrecadacao(resumoArrecadacaoAguaEsgoto.getAnoMesReferencia(),
								resumoArrecadacaoAguaEsgoto.getCodigoSetorComercial(), resumoArrecadacaoAguaEsgoto.getNumeroQuadra(),
								resumoArrecadacaoAguaEsgoto.getQuantidadeContas(),
								resumoArrecadacaoAguaEsgoto.getIndicadorRecebidasNomes(), resumoArrecadacaoAguaEsgoto.getValorAgua(),
								resumoArrecadacaoAguaEsgoto.getValorEsgoto(), resumoArrecadacaoAguaEsgoto.getUltimaAlteracao(),
								resumoArrecadacaoAguaEsgoto.getValorNaoIdentificado(), resumoArrecadacaoAguaEsgoto.getGerSubcategoria(),
								resumoArrecadacaoAguaEsgoto.getGerClienteTipo(), resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaSituacao(),
								resumoArrecadacaoAguaEsgoto.getGerUnidadeNegocio(), resumoArrecadacaoAguaEsgoto.getGerLocalidade(),
								resumoArrecadacaoAguaEsgoto.getGerLocalidadeElo(), resumoArrecadacaoAguaEsgoto.getGerQuadra(),
								resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoSituacao(), resumoArrecadacaoAguaEsgoto
												.getGerLigacaoEsgotoPerfil(), resumoArrecadacaoAguaEsgoto.getGerGerenciaRegional(),
								resumoArrecadacaoAguaEsgoto.getGerSetorComercial(), resumoArrecadacaoAguaEsgoto.getGerDocumentoTipo(),
								resumoArrecadacaoAguaEsgoto.getGerPagamentoSituacao(), resumoArrecadacaoAguaEsgoto
												.getGerLigacaoAguaPerfil(), resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento(),
								resumoArrecadacaoAguaEsgoto.getGerEsferaPoder(), resumoArrecadacaoAguaEsgoto.getGerCategoria(),
								resumoArrecadacaoAguaEsgoto.getGerImovelPerfil(), resumoArrecadacaoAguaEsgoto.getGerRota(),
								resumoArrecadacaoAguaEsgoto.getValorImpostos(), resumoArrecadacaoAguaEsgoto.getIndicadorHidrometro(),
								resumoArrecadacaoAguaEsgoto.getAnoMesReferenciaDocumento());

				resumoArrecadacaoAguaEsgoto_Outro.setGerArrecadacaoForma(resumoArrecadacaoAguaEsgoto.getGerArrecadacaoForma());
				resumoArrecadacaoAguaEsgoto_Outro.setGerArrecadador(resumoArrecadacaoAguaEsgoto.getGerArrecadador());

				// Insere os valores para Credito com 0(zero)
				creditoOrigem = new Integer(0);
				itemContabelCredito = new Integer(0);

				resumoArrecadacaoAguaEsgoto_Outro.setCreditoOrigemIdCredito(creditoOrigem);
				resumoArrecadacaoAguaEsgoto_Outro.setLancamentoItemIdCredito(itemContabelCredito);
				resumoArrecadacaoAguaEsgoto_Outro.setValorDocumentosRecebidosCredito(new BigDecimal(0));

				// Insere os Valores para Outros
				resumoArrecadacaoAguaEsgoto_Outro.setValorDocumentosRecebidosOutros(valorDebito);
				resumoArrecadacaoAguaEsgoto_Outro.setLancamentoItemIdOutros(itemContabel);
				resumoArrecadacaoAguaEsgoto_Outro.setFinanciamentoTipoIdOutros(financiamentoTipo);

				// Adicionamos a lista que deve ser inserida
				listaResumoArrecadacaoAguaEsgoto_Outro.add(resumoArrecadacaoAguaEsgoto_Outro);

				helper = null;
			}

			// Insere o resumo na UnResumoArrecadacaoOutro
			if(listaResumoArrecadacaoAguaEsgoto_Outro.size() > 0){
				System.out.print("=====> Início do Inserir Resumo Arrecadacao Outro <=====");
				this.getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoArrecadacaoAguaEsgoto_Outro);
				System.out.print("=====>      FIM do Resumo Arrecadacao Outro       <=====");
			}

			listaSimplificada.clear();
			listaSimplificada = null;

			listaResumoArrecadacaoAguaEsgoto_Outro.clear();
			listaResumoArrecadacaoAguaEsgoto_Outro = null;

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			System.out.print("ERROR =============================================");
			System.out.println("IMOVEL => " + idImovel);
			System.out.println("Esfera => " + idEsferaPoder);
			System.out.println("Esfera => " + resumoArrecadacaoAguaEsgoto.getGerEsferaPoder().getId().intValue());
			System.out.println("Tipo Cliente => " + idTipoCliente);
			System.out.println("Tipo Cliente => " + resumoArrecadacaoAguaEsgoto.getGerClienteTipo().getId());
			System.out.println("Categoria => " + categoria.getId());
			System.out.println("Categoria => " + resumoArrecadacaoAguaEsgoto.getGerCategoria().getId());
			System.out.println("SubCategoria => " + idSubcategoria);
			System.out.println("SubCategoria => " + resumoArrecadacaoAguaEsgoto.getGerSubcategoria().getId());
			System.out.println("Epoca => " + epocaPagamento);
			System.out.println("Epoca => " + resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento().getId());

			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}// gerarResumoArrecadacaoOutro

	/**
	 * Método que gera o resumo da Arrecadacao
	 * [UC0551] - Gerar Resumo da Arrecadacao Credito
	 * 
	 * @author Ivan Sérgio
	 * @date 29/05/2007
	 */
	@Deprecated
	public void gerarResumoArrecadacaoCredito(int anoMesReferenciaArrecadacao, UnResumoArrecadacao resumoArrecadacaoAguaEsgoto)
					throws ControladorException{

		try{
			List listResumoArrecadacaoCredito = this.repositorioGerencialArrecadacao.getResumoArrecadacaoCredito(
							anoMesReferenciaArrecadacao, resumoArrecadacaoAguaEsgoto);

			List<ResumoArrecadacaoCreditoHelper> listaSimplificada = new ArrayList();
			List<UnResumoArrecadacao> listaResumoArrecadacaoAguaEsgoto_Credito = new ArrayList();

			Object obj = null;
			Object[] retorno = null;
			ResumoArrecadacaoCreditoHelper helper = null;
			Integer idImovel = null;
			BigDecimal valorCredito = null;
			Integer idEsferaPoder = null;
			Integer idTipoCliente = null;
			Categoria categoria = null;
			Integer idSubcategoria = 0;
			ImovelSubcategoria subcategoria = null;
			Imovel imovel = null;
			// Cliente clienteTemp = null;
			int posicao = 0;
			ResumoArrecadacaoCreditoHelper jaCadastrado = null;

			for(int i = 0; i < listResumoArrecadacaoCredito.size(); i++){
				obj = listResumoArrecadacaoCredito.get(i);

				if(obj instanceof Object[]){
					retorno = (Object[]) obj;

					// Se o Tipo de Financiamento for igual a NULL, despresar o Pagamento
					// if (retorno[1] != null) {
					// Montamos um objeto de resumo, com as informacoes do retorno
					helper = new ResumoArrecadacaoCreditoHelper((Integer) retorno[1], // Credito
									// Origem
									(Integer) retorno[2]); // Lancamento Item Contabil

					idImovel = (Integer) retorno[0]; // Codigo do imovel que esta sendo processado

					// Valor Credito
					valorCredito = new BigDecimal(0);
					if(retorno[3] != null){
						valorCredito = (BigDecimal) retorno[3];
					}

					// Pesquisamos a esfera de poder do cliente responsavel
					idEsferaPoder = repositorioGerencialCadastro.pesquisarEsferaPoderClienteResponsavelImovel(idImovel);
					// Pesquisamos o tipo de cliente responsavel do imovel
					idTipoCliente = repositorioGerencialCadastro.pesquisarTipoClienteClienteResponsavelImovel(idImovel);

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
					idSubcategoria = 0;
					if(categoria != null){
						// Pesquisando a principal subcategoria
						subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(), idImovel);

						if(subcategoria != null){
							idSubcategoria = subcategoria.getComp_id().getSubcategoria().getId();
						}
					}

					// Verifica Epoca de Pagamento
					Integer epocaPagamento = (Integer) retorno[4];
					String dataPagamento = retorno[5].toString();
					String dataVencimentoConta = "";
					if(retorno[6] != null){
						dataVencimentoConta = retorno[6].toString();
					}
					String dataVencimentoGuia = "";
					if(retorno[7] != null){
						dataVencimentoGuia = retorno[7].toString();
					}

					if(epocaPagamento == 99){
						// Verifica Epoca de Pagamento para CONTA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoConta);
					}else if(epocaPagamento == 98){
						// Verifica Epoca de Pagamento para GUIA
						epocaPagamento = retornaValorEpocaPagamento(dataPagamento, dataVencimentoGuia);
					}

					// Verificamos se a esfera de poder foi encontrada
					// [FS0002] Verificar existencia de cliente responsavel
					/*
					 * if ( idEsferaPoder == 0 ){
					 * imovel = new Imovel();
					 * imovel.setId(idImovel);
					 * clienteTemp =
					 * this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
					 * if (clienteTemp != null){
					 * idEsferaPoder = clienteTemp.getClienteTipo().getEsferaPoder().getId();
					 * }
					 * }
					 * if ( idTipoCliente == 0 ){
					 * imovel = new Imovel();
					 * imovel.setId(idImovel);
					 * clienteTemp =
					 * this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
					 * if (clienteTemp != null){
					 * idTipoCliente = clienteTemp.getClienteTipo().getId();
					 * }
					 * }
					 */

					// Tratamos as informações para refinar os dados com os valores de
					// Esfera de Poder, Tipo do Cliente, Categoria e Sub Categoria de
					// acordo com a quebra de Resumo Arrecadacao Agua/Esgoto
					if((idEsferaPoder.equals(resumoArrecadacaoAguaEsgoto.getGerEsferaPoder().getId().intValue()))
									& (idTipoCliente.equals(resumoArrecadacaoAguaEsgoto.getGerClienteTipo().getId()))
									& (categoria.getId().equals(resumoArrecadacaoAguaEsgoto.getGerCategoria().getId()))
									& (idSubcategoria.equals(resumoArrecadacaoAguaEsgoto.getGerSubcategoria().getId()))
									& (epocaPagamento.equals(resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento().getId()))){

						// Se ja existe um objeto igual a ele entao acumulamos os
						// valores Credito.
						if(listaSimplificada.contains(helper)){
							posicao = listaSimplificada.indexOf(helper);
							jaCadastrado = (ResumoArrecadacaoCreditoHelper) listaSimplificada.get(posicao);

							// Somatorio de Valor Credito
							jaCadastrado.setValorCredito(jaCadastrado.getValorCredito().add(valorCredito));

						}else{
							// Incluimos o Valor Credito
							helper.setValorCredito(helper.getValorCredito().add(valorCredito));
							listaSimplificada.add(helper);
						}

					}
					helper = null;

					// }
				}
				obj = null;
			}

			listResumoArrecadacaoCredito.clear();
			listResumoArrecadacaoCredito = null;

			/**
			 * para todas as ResumoArrecadacaoCreditoHelper cria
			 * UnResumoArrecadacaoCredito
			 */
			Integer itemContabel = null;
			Integer creditoOrigem = null;
			UnResumoArrecadacao resumoArrecadacaoAguaEsgoto_Credito = null;

			// Usados apenas para adicionar os valores de Outros
			Integer itemContabelOutros = null;
			Integer financiamentoTipoOutros = null;

			for(int i = 0; i < listaSimplificada.size(); i++){
				helper = (ResumoArrecadacaoCreditoHelper) listaSimplificada.get(i);

				// Valor Credito
				if(helper.getValorCredito() != null){
					valorCredito = (helper.getValorCredito());
				}

				// Lancamento Item Contabel
				itemContabel = new Integer(0);
				if(helper.getidLancamentoItemContabil() != null){
					itemContabel = helper.getidLancamentoItemContabil();
				}

				// Credito Origem
				creditoOrigem = new Integer(0);
				if(helper.getIdCreditoOrigem() != null){
					creditoOrigem = helper.getIdCreditoOrigem();
				}

				resumoArrecadacaoAguaEsgoto_Credito = new UnResumoArrecadacao(resumoArrecadacaoAguaEsgoto.getAnoMesReferencia(),
								resumoArrecadacaoAguaEsgoto.getCodigoSetorComercial(), resumoArrecadacaoAguaEsgoto.getNumeroQuadra(),
								resumoArrecadacaoAguaEsgoto.getQuantidadeContas(),
								resumoArrecadacaoAguaEsgoto.getIndicadorRecebidasNomes(), resumoArrecadacaoAguaEsgoto.getValorAgua(),
								resumoArrecadacaoAguaEsgoto.getValorEsgoto(), resumoArrecadacaoAguaEsgoto.getUltimaAlteracao(),
								resumoArrecadacaoAguaEsgoto.getValorNaoIdentificado(), resumoArrecadacaoAguaEsgoto.getGerSubcategoria(),
								resumoArrecadacaoAguaEsgoto.getGerClienteTipo(), resumoArrecadacaoAguaEsgoto.getGerLigacaoAguaSituacao(),
								resumoArrecadacaoAguaEsgoto.getGerUnidadeNegocio(), resumoArrecadacaoAguaEsgoto.getGerLocalidade(),
								resumoArrecadacaoAguaEsgoto.getGerLocalidadeElo(), resumoArrecadacaoAguaEsgoto.getGerQuadra(),
								resumoArrecadacaoAguaEsgoto.getGerLigacaoEsgotoSituacao(), resumoArrecadacaoAguaEsgoto
												.getGerLigacaoEsgotoPerfil(), resumoArrecadacaoAguaEsgoto.getGerGerenciaRegional(),
								resumoArrecadacaoAguaEsgoto.getGerSetorComercial(), resumoArrecadacaoAguaEsgoto.getGerDocumentoTipo(),
								resumoArrecadacaoAguaEsgoto.getGerPagamentoSituacao(), resumoArrecadacaoAguaEsgoto
												.getGerLigacaoAguaPerfil(), resumoArrecadacaoAguaEsgoto.getGerEpocaPagamento(),
								resumoArrecadacaoAguaEsgoto.getGerEsferaPoder(), resumoArrecadacaoAguaEsgoto.getGerCategoria(),
								resumoArrecadacaoAguaEsgoto.getGerImovelPerfil(), resumoArrecadacaoAguaEsgoto.getGerRota(),
								resumoArrecadacaoAguaEsgoto.getValorImpostos(), resumoArrecadacaoAguaEsgoto.getIndicadorHidrometro(),
								resumoArrecadacaoAguaEsgoto.getAnoMesReferenciaDocumento());

				resumoArrecadacaoAguaEsgoto_Credito.setGerArrecadacaoForma(resumoArrecadacaoAguaEsgoto.getGerArrecadacaoForma());
				resumoArrecadacaoAguaEsgoto_Credito.setGerArrecadador(resumoArrecadacaoAguaEsgoto.getGerArrecadador());

				// Preenche os Objetos com valor 0 que serão usados em Credito e Outros
				itemContabelOutros = new Integer(0);
				financiamentoTipoOutros = new Integer(0);

				resumoArrecadacaoAguaEsgoto_Credito.setLancamentoItemIdOutros(itemContabelOutros);
				resumoArrecadacaoAguaEsgoto_Credito.setFinanciamentoTipoIdOutros(financiamentoTipoOutros);
				resumoArrecadacaoAguaEsgoto_Credito.setValorDocumentosRecebidosOutros(new BigDecimal(0));

				// Adicionar os valores de Credito
				resumoArrecadacaoAguaEsgoto_Credito.setValorDocumentosRecebidosCredito(valorCredito);
				resumoArrecadacaoAguaEsgoto_Credito.setLancamentoItemIdCredito(itemContabel);
				resumoArrecadacaoAguaEsgoto_Credito.setCreditoOrigemIdCredito(creditoOrigem);

				// Adicionamos a lista que deve ser inserida
				listaResumoArrecadacaoAguaEsgoto_Credito.add(resumoArrecadacaoAguaEsgoto_Credito);

				helper = null;
			}

			// Insere o resumo na UnResumoArrecadacaoCredito
			if(listaResumoArrecadacaoAguaEsgoto_Credito.size() > 0){
				System.out.print("=====> Início do Inserir Resumo Arrecadacao Credito <=====");
				this.getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoArrecadacaoAguaEsgoto_Credito);
				System.out.print("=====>  FIM do Inserir Resumo Arrecadacao Credito   <=====");
			}

			listaSimplificada.clear();
			listaSimplificada = null;

			listaResumoArrecadacaoAguaEsgoto_Credito.clear();
			listaResumoArrecadacaoAguaEsgoto_Credito = null;

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}// gerarResumoArrecadacaoCredito

	/**
	 * Retorna o Valor para Epoca de Pagamento
	 * 
	 * @param dataPagamento
	 * @param dataVencimento
	 * @return
	 */
	private Integer retornaValorEpocaPagamento(String dataPagamento, String dataVencimento){

		Integer retorno = null;

		Date data1 = Util.converteStringParaDate(formataData(dataPagamento));
		dataPagamento = "" + Util.getAno(data1);

		// Adiciona 0 ao mes
		if(Util.getMes(data1) < 10){
			dataPagamento += "0" + Util.getMes(data1);
		}else{
			dataPagamento += "" + Util.getMes(data1);
		}

		Integer iDataPagamento = Util.converterStringParaInteger(dataPagamento);

		if(iDataPagamento.equals(incrementaData(dataVencimento, 1))){
			retorno = 2;
		}

		if(iDataPagamento.equals(incrementaData(dataVencimento, 2))){
			retorno = 3;
		}

		if(iDataPagamento.equals(incrementaData(dataVencimento, 3))){
			retorno = 4;
		}

		if(iDataPagamento.compareTo(incrementaData(dataVencimento, 3)) > 0){
			retorno = 5;
		}

		return retorno;
	}

	/**
	 * Formata uma data no formato yyy-mm-dd para dd/mm/aaaa
	 * 
	 * @param data
	 * @return
	 */
	private String formataData(String data){

		String retorno = "";
		String dia = "";
		String mes = "";
		String ano = "";

		dia = data.substring(8, 10);
		mes = data.substring(5, 7);
		ano = data.substring(0, 4);

		retorno = dia + "/" + mes + "/" + ano;

		return retorno;
	}

	/**
	 * @param data
	 * @param qtdMes
	 * @return
	 */
	private Integer incrementaData(String data, int qtdMes){

		Date data1 = Util.converteStringParaDate(formataData(data));
		data1 = Util.adcionarOuSubtrairMesesAData(data1, qtdMes, 0);
		String sData = "" + Util.getAno(data1);

		// Adiciona 0 ao mes
		if(Util.getMes(data1) < 10){
			sData += "0" + Util.getMes(data1);
		}else{
			sData += "" + Util.getMes(data1);
		}

		Integer iData = Util.converterStringParaInteger(sData);

		return iData;
	}
}