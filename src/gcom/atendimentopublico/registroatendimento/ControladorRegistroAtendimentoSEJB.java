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
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 */

package gcom.atendimentopublico.registroatendimento;

import gcom.acquagis.atendimento.RegistroAtendimentoJSONHelper;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.*;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.*;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.*;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.IRepositorioFaturamento;
import gcom.faturamento.RepositorioFaturamentoHBM;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.atendimentopublico.registroatendimento.GMeioSolicitacao;
import gcom.gerencial.atendimentopublico.registroatendimento.GSolicitacaoTipo;
import gcom.gerencial.atendimentopublico.registroatendimento.GSolicitacaoTipoEspecificacao;
import gcom.gerencial.atendimentopublico.registroatendimento.UnResumoRegistroAtendimento;
import gcom.gerencial.cadastro.IRepositorioGerencialCadastro;
import gcom.gerencial.cadastro.RepositorioGerencialCadastroHBM;
import gcom.gerencial.cadastro.cliente.GClienteTipo;
import gcom.gerencial.cadastro.cliente.GEsferaPoder;
import gcom.gerencial.cadastro.imovel.GCategoria;
import gcom.gerencial.cadastro.imovel.GImovelPerfil;
import gcom.gerencial.cadastro.imovel.GSubcategoria;
import gcom.gerencial.cadastro.localidade.*;
import gcom.gerencial.micromedicao.GRota;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.integracao.acquagis.DadosAcquaGis;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.Rota;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.operacional.abastecimento.AbastecimentoProgramacao;
import gcom.operacional.abastecimento.FiltroAbastecimentoProgramacao;
import gcom.operacional.abastecimento.FiltroManutencaoProgramacao;
import gcom.operacional.abastecimento.ManutencaoProgramacao;
import gcom.relatorio.atendimentopublico.*;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.collection.PersistentSet;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

public class ControladorRegistroAtendimentoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioRegistroAtendimento repositorioRegistroAtendimento = null;

	private IRepositorioGerencialCadastro repositorioGerencialCadastro = null;

	private IRepositorioCobranca repositorioCobranca = null;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	private IRepositorioFaturamento repositorioFaturamento = null;

	SessionContext sessionContext;

	public void ejbCreate() throws CreateException{

		repositorioRegistroAtendimento = RepositorioRegistroAtendimentoHBM.getInstancia();
		repositorioGerencialCadastro = RepositorioGerencialCadastroHBM.getInstancia();
		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
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
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */
	private ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
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
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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
	 * Retorna o valor de controladorUnidade
	 * 
	 * @return O valor de controladorUnidade
	 */
	private ControladorUnidadeLocal getControladorUnidade(){

		ControladorUnidadeLocalHome localHome = null;
		ControladorUnidadeLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUnidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UNIDADE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorOrdemServico
	 * 
	 * @return O valor de controladorOrdemServico
	 */
	private ControladorOrdemServicoLocal getControladorOrdemServico(){

		ControladorOrdemServicoLocalHome localHome = null;
		ControladorOrdemServicoLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorOrdemServicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ORDEM_SERVICO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorFaturamento
	 * 
	 * @return O valor de controladorFaturamento
	 */
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

	private SolicitacaoTipoEspecificacao buscarSolicitacaoTipoEspecificacao(Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException{

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

		// filtroSolicitacaoTipoEspecificacao
		// .adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						idSolicitacaoTipoEspecificacao));

		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);
		}

		return solicitacaoTipoEspecificacao;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Data Prevista = Data válida obtida a partir da Data do Atendimento +
	 * número de dias previstos para a especificação do tipo de solicitação
	 * (STEP_NNDIAPRAZO da tabela SOLICITACAO_TIPO_ESPECIFICACAO).
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS)
	 * Caso a Especificação esteja associada a uma unidade (UNID_ID da tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO com o valor diferente de nulo), definir a
	 * unidade destino a partir da Especificação (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com SETP_ID=Id da Especificação
	 * selecionada).
	 * [SB0003] Define Data Prevista e Unidade Destino da Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper definirDataPrevistaUnidadeDestinoEspecificacao(Date dataAtendimento,
					Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper retorno = new DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper();
		Object[] parmsEspecificacao = null;
		try{
			parmsEspecificacao = repositorioRegistroAtendimento.pesquisarIndicadorFaltaAguaRA(idSolicitacaoTipoEspecificacao);
			if(parmsEspecificacao != null){

				if(parmsEspecificacao[0] != null){
					retorno.setIndFaltaAgua(((Short) parmsEspecificacao[0]).toString());
				}
				if(parmsEspecificacao[1] != null){
					retorno.setIndMatricula(((Integer) parmsEspecificacao[1]).toString());
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = buscarSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);

		if(solicitacaoTipoEspecificacao != null){

			// Exige matrícula do Imóvel
			boolean imovelObrigatorio = this.especificacaoExigeMatriculaImovel(solicitacaoTipoEspecificacao);

			if(imovelObrigatorio){
				retorno.setImovelObrigatorio("SIM");
			}else{
				retorno.setImovelObrigatorio("NAO");
			}

			// Exige pavimento rua
			boolean pavimentoRuaObrigatorio = this.especificacaoExigePavimentoRua(solicitacaoTipoEspecificacao);

			if(pavimentoRuaObrigatorio){
				retorno.setPavimentoRuaObrigatorio("SIM");
			}else{
				retorno.setPavimentoRuaObrigatorio("NAO");
			}

			// Exige pavimento calçada
			boolean pavimentoCalcadaObrigatorio = this.especificacaoExigePavimentoCalcada(solicitacaoTipoEspecificacao);

			if(pavimentoCalcadaObrigatorio){
				retorno.setPavimentoCalcadaObrigatorio("SIM");
			}else{
				retorno.setPavimentoCalcadaObrigatorio("NAO");
			}

			retorno.setDataPrevista(this.obterDataHoraPrevistaAtendimento(dataAtendimento, solicitacaoTipoEspecificacao));

			// SistemaParametro sistemaParametro = this.getControladorUtil()
			// .pesquisarParametrosDoSistema();
			//
			// if (sistemaParametro.getIndicadorSugestaoTramite() != null
			// && sistemaParametro.getIndicadorSugestaoTramite().equals(
			// ConstantesSistema.INDICADOR_USO_ATIVO)
			// && solicitacaoTipoEspecificacao.getUnidadeOrganizacional() != null
			// && solicitacaoTipoEspecificacao.getUnidadeOrganizacional()
			// .getIndicadorTramite() == ConstantesSistema.INDICADOR_USO_ATIVO
			// .shortValue()) {
			//
			// /*
			// * [UC0366] Inserir Registro de Atendimento [FS0018] Verificar
			// * existência de unidade centralizadora
			// */
			// UnidadeOrganizacional unidadeDestino = this
			// .getControladorUnidade()
			// .verificarExistenciaUnidadeCentralizadora(
			// solicitacaoTipoEspecificacao
			// .getUnidadeOrganizacional());
			//
			// retorno.setUnidadeOrganizacional(unidadeDestino);
			// }
		}

		return retorno;
	}

	private Date obterDataHoraPrevistaAtendimento(Date dataAtendimento, SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao)
					throws NumberFormatException, ControladorException{

		Date dataRetorno = null;
		Short pCalcularApenasDiasUteis = Short.valueOf((String) ParametroAtendimentoPublico.P_CALCULAR_APENAS_DIAS_UTEIS.executar());
		Collection<NacionalFeriado> colecaoferiadoNacional = null;

		if(solicitacaoTipoEspecificacao.getHorasPrazo() != null
						&& solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento() != null){

			Date dataCalculada = Util.adicionarNumeroHorasAUmaData(dataAtendimento, solicitacaoTipoEspecificacao.getHorasPrazo());

			try{

				colecaoferiadoNacional = repositorioCobranca.pesquisarFeriadoNacional(dataCalculada);

			}catch(Exception e){

				throw new ControladorException("erro.sistema", e);

			}

			if(pCalcularApenasDiasUteis.equals(ConstantesSistema.SIM)){

				int qtdDiasUteis = Util.obterDiferencaEntreDatasEmDias(dataAtendimento, dataCalculada);

				if(qtdDiasUteis > 0){

					for(int i = 1; i <= qtdDiasUteis; i++){

						dataAtendimento = Util.adicionarNumeroDiasDeUmaData(dataAtendimento, 1);

						while(!Util.ehDiaUtil(dataAtendimento, colecaoferiadoNacional, null)){

							dataAtendimento = Util.adicionarNumeroDiasDeUmaData(dataAtendimento, 1);

						}

					}

					dataRetorno = dataAtendimento;

				}else{

					dataRetorno = dataCalculada;

				}

			}else{

				dataRetorno = dataCalculada;

			}

			if(solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento().equals(ConstantesSistema.SIM)){

				while(!Util.ehDiaUtil(dataRetorno, colecaoferiadoNacional, null)){

					dataRetorno = Util.adicionarNumeroDiasDeUmaData(dataRetorno, 1);

				}

			}

		}else{

			dataRetorno = dataAtendimento;

		}

		return dataRetorno;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Valida os dados gerais do atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 27/07/2006
	 * @param dataAtendimento
	 *            ,
	 *            horaAtendimento, tempoEsperaInicial, tempoEsperaFinal,
	 *            idUnidadeOrganizacional
	 * @return void
	 * @throws ControladorException
	 */
	public void validarInserirRegistroAtendimentoDadosGeraisIncompleto(String dataAtendimento, String horaAtendimento,
					String tempoEsperaInicial, String tempoEsperaFinal, String idUnidadeOrganizacional, String numeroRAManual,
					String especificacao, String idRAReiteracao) throws ControladorException{

		// [FS0001] - Verificar data do atendimento
		Date dataCorrente = new Date();
		Date dataAtendimentoObjeto = Util.converteStringParaDate(dataAtendimento);

		if(dataAtendimentoObjeto.after(dataCorrente)){
			throw new ControladorException("atencao.data_atendimento_posterior_data_corrente", null, Util.formatarData(dataCorrente));
		}

		// [FS0002] - Verificar hora do atendimento
		if(Util.datasIguais(dataAtendimentoObjeto, dataCorrente)
						&& Util.compararHoraMinuto(Util.formatarHoraSemSegundos(horaAtendimento),
										Util.formatarHoraSemSegundos(dataCorrente), ">")){
			throw new ControladorException("atencao.hora_atendimento_posterior_hora_corrente", null,
							Util.formatarHoraSemSegundos(dataCorrente));
		}

		// [FS0014] - Verificar tempo de espera inicial para atendimento
		if(Util.datasIguais(dataAtendimentoObjeto, dataCorrente)
						&& tempoEsperaInicial != null
						&& tempoEsperaInicial.length() > 0
						&& Util.compararHoraMinuto(Util.formatarHoraSemSegundos(tempoEsperaInicial),
										Util.formatarHoraSemSegundos(dataCorrente), ">")){
			throw new ControladorException("atencao.tempo_espera_atendimento_posterior_hora_corrente", null, "Tempo de Espera Inicial",
							Util.formatarHoraSemSegundos(dataCorrente));
		}

		// [FS0015] - Verificar tempo de espera final para atendimento
		if(Util.datasIguais(dataAtendimentoObjeto, dataCorrente)
						&& tempoEsperaFinal != null
						&& tempoEsperaFinal.length() > 0
						&& Util.compararHoraMinuto(Util.formatarHoraSemSegundos(tempoEsperaFinal),
										Util.formatarHoraSemSegundos(dataCorrente), ">")){
			throw new ControladorException("atencao.tempo_espera_atendimento_posterior_hora_corrente", null, "Tempo de Espera Final ",
							Util.formatarHoraSemSegundos(dataCorrente));
		}

		// [FS0016] - Verificar tempo de espera final para atendimento menor que o inicial
		if(tempoEsperaInicial != null
						&& tempoEsperaFinal != null
						&& tempoEsperaInicial.length() > 0
						&& tempoEsperaFinal.length() > 0
						&& Util.compararHoraMinuto(Util.formatarHoraSemSegundos(tempoEsperaInicial),
										Util.formatarHoraSemSegundos(tempoEsperaFinal), ">")){

			throw new ControladorException("atencao.tempo_espera_final_anterior_tempo_espera_final_registro_atendimento");
		}

		// [FS0033] Verificar autorização da unidade de atendimento para abertura de registro de
		// atendimento
		this.getControladorUnidade().verificarAutorizacaoUnidadeAberturaRA(Integer.valueOf(idUnidadeOrganizacional), true);

		// Caso o número informado seja maior que o número atualizado como
		// ltimo emitido
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		// [FS0042] Verificar número Informado
		if(numeroRAManual != null && !numeroRAManual.equalsIgnoreCase("")){

			/*
			 * Busca o '-' que determina o digito verificador para determinar a posicao da numeração
			 * da RA
			 */
			String[] arrayNumeroRAManual;
			if(numeroRAManual.indexOf('-') > -1){
				arrayNumeroRAManual = numeroRAManual.split("-");
			}else{
				arrayNumeroRAManual = new String[2];
				arrayNumeroRAManual[0] = numeroRAManual.substring(0, numeroRAManual.length() - 1);
				arrayNumeroRAManual[1] = numeroRAManual.substring(numeroRAManual.length() - 1, numeroRAManual.length());
			}

			Integer numeracao = Integer.valueOf(arrayNumeroRAManual[0]);
			Integer digitoModulo11 = Integer.valueOf(arrayNumeroRAManual[1]);
			Integer numeracaoComDigito = Integer.valueOf(arrayNumeroRAManual[0] + arrayNumeroRAManual[1]);

			Integer ultimaNumeracao = sistemaParametro.getUltimoRAManual().intValue();

			if(numeracao.intValue() > ultimaNumeracao){
				throw new ControladorException("atencao.numeracao_ra_manual_superior");
			}

			// Caso o dígito verificador do número informado não bata com o
			// dígito calculado com o módulo 11
			if(!digitoModulo11.equals(Util.obterDigitoVerificadorModulo11(Long.parseLong(numeracao.toString())))){
				throw new ControladorException("atencao.numeracao_ra_manual_digito_invalido");
			}

			// Caso o número informado já esteja informado em outro registro
			// atendimento
			Integer quantidadeRA = null;

			try{
				quantidadeRA = this.repositorioRegistroAtendimento.verificaNumeracaoRAManualInformada(numeracaoComDigito);
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(quantidadeRA != null){
				throw new ControladorException("atencao.numeracao_ra_manual_ja_informado", null, quantidadeRA.toString());
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Valida os dados gerais do atendimento
	 * 
	 * @author anishimura
	 * @date 03/03/2010
	 * @param dataAtendimento
	 * @param horaAtendimento
	 * @param tempoEsperaInicial
	 * @param tempoEsperaFinal
	 * @param idUnidadeOrganizacional
	 * @param numeroRAManual
	 * @param especificacao
	 * @param idRAReiteracao
	 * @return void
	 * @throws ControladorException
	 */
	public void validarInserirRegistroAtendimentoDadosGerais(String dataAtendimento, String horaAtendimento, String tempoEsperaInicial,
					String tempoEsperaFinal, String idUnidadeOrganizacional, String numeroRAManual, String especificacao,
					String idRAReiteracao) throws ControladorException{

		this.validarInserirRegistroAtendimentoDadosGeraisIncompleto(dataAtendimento, horaAtendimento, tempoEsperaInicial, tempoEsperaFinal,
						idUnidadeOrganizacional, numeroRAManual, especificacao, idRAReiteracao);

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		if(especificacao != null && sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao() != null
						&& especificacao.equals(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao().toString())){
			if(idRAReiteracao == null || idRAReiteracao.equals("")){
				throw new ControladorException("atencao.especificacao_reiteracao_obrigatoria");
			}
			RegistroAtendimento ra = this.pesquisarRegistroAtendimento(Integer.valueOf(idRAReiteracao));
			if(ra == null || ra.getCodigoSituacao() == RegistroAtendimento.SITUACAO_ENCERRADO){
				throw new ControladorException("atencao.ra_reiteracao_nao_existe_ou_encerrado");
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação exija a matrícula do Imóvel (STEP_ICMATRICULA com o valor correspondente
	 * a um na tabela SOLICITACAO_TIPO_ESPECIFICACAO),
	 * obrigatório; caso contrário, opcional
	 * 
	 * @author Raphael Rossiter
	 * @date 28/07/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarExigenciaImovelPelaEspecificacao(Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		boolean retorno = false;

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						idSolicitacaoTipoEspecificacao));

		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
							.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);

			if(solicitacaoTipoEspecificacao.getIndicadorMatricula() != null
							&& solicitacaoTipoEspecificacao.getIndicadorMatricula()
											.equals(ConstantesSistema.INDICADOR_USO_ATIVO.intValue())){

				retorno = true;
			}
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a especificação informada para o RA tem indicativo que é para
	 * verificar débito (STEP_ICVERIFICADEBITO da tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1)), o sistema
	 * deverá verificar se o imóvel informado tenha débito <<incluir>> [UC0067]
	 * Obter Débito do Imóvel ou Cliente (passando o imóvel). [FS0043] –
	 * Verifica imóvel com débito.
	 * [SB0032] – Verifica se o imóvel informado tem débito.
	 * [FS0043] – Verifica imóvel com débito
	 * 
	 * @author Raphael Rossiter
	 * @date 19/02/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public void verificarImovelComDebitos(Integer idSolicitacaoTipoEspecificacao, Integer idImovel) throws ControladorException{

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						idSolicitacaoTipoEspecificacao));
		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
							.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);
			if(solicitacaoTipoEspecificacao.getIndicadorVerificarDebito() != null
							&& solicitacaoTipoEspecificacao.getIndicadorVerificarDebito().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO
											.intValue()){
				Date dataVencimentoInicial = Util.criarData(1, 1, 0001);
				// ate dataVencimentoFinal = Util.adicionarNumeroDiasDeUmaData(new Date(), -7);
				Date dataVencimentoFinal = new Date();
				// [UC0067] Obter Débito do Imóvel ou Cliente
				ObterDebitoImovelOuClienteHelper imovelDebitos = this.getControladorCobranca().obterDebitoImovelOuCliente(1,
								idImovel.toString(), null, null, "000101", "999912", dataVencimentoInicial, dataVencimentoFinal, 1, 1, 1,
								1, 1, 1, 1, true, null, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
								ConstantesSistema.SIM, 2, null);

				// [FS0043] – Verifica imóvel com débito
				if(/*
					 * (imovelDebitos.getColecaoDebitoACobrar() != null &&
					 * !imovelDebitos.getColecaoDebitoACobrar().isEmpty())
					 * ||
					 */(imovelDebitos.getColecaoContasValores() != null && !imovelDebitos.getColecaoContasValores().isEmpty())
								|| (imovelDebitos.getColecaoContasValoresImovel() != null && !imovelDebitos.getColecaoContasValoresImovel()
												.isEmpty())
								|| (imovelDebitos.getColecaoGuiasPagamentoValores() != null && !imovelDebitos
												.getColecaoGuiasPagamentoValores().isEmpty())){

					throw new ControladorException("atencao.imovel_com_debitos", null, idImovel.toString(),
									solicitacaoTipoEspecificacao.getDescricao());
				}
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Obter e habilitar/desabilitar os Dados da Identificação do Local da
	 * ocorrência de acordo com as situações abaixo descritas no caso de uso
	 * [SB0004] Obtém e Habilita/Desabilita Dados da Identificação do Local da
	 * ocorrência e Dados do Solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 28/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao, idSolicitacaoTipo,
	 *            levantarExceptionImovelInexistente
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrencia(Integer idImovel,
					Integer idSolicitacaoTipoEspecificacao, Integer idSolicitacaoTipo, boolean levantarExceptionImovelInexistente,
					Usuario usuario) throws ControladorException{

		ObterDadosIdentificacaoLocalOcorrenciaHelper retorno = new ObterDadosIdentificacaoLocalOcorrenciaHelper();

		Imovel imovel = this.getControladorImovel().pesquisarImovelRegistroAtendimento(idImovel);

		if(imovel != null){

			// [SB0040] - Validar Autorização de Abertura de RA pelos Usuários das Empresas de
			// Cobrança Administrativa
			if(this.getControladorImovel()
							.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
											ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA)
							.equals(ConstantesSistema.NAO)){
				throw new ControladorException("atencao.usuario_sem_acesso_inserir_ra_imovel_cobranca_administrativa", null,
								usuario.getLogin(), Integer.toString(idImovel));
			}

			// [FS0020] - Verificar existência de registro de atendimento para o
			// Imóvel com a mesma especificação
			this.verificarExistenciaRAImovelMesmaEspecificacao(imovel.getId(), idSolicitacaoTipoEspecificacao);

			this.verificarExistenciaOSImovelMesmaEspecificacao(imovel.getId(), idSolicitacaoTipoEspecificacao);

			// [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
			// religação ou restabelecimento
			this.verificarExistenciaServicoReligacaoRestabelecimento(idImovel, idSolicitacaoTipo);

			// [FS0055 - Verificar abertura de RA de corte]
			this.verificarAberturaRACorte(idImovel, idSolicitacaoTipo, idSolicitacaoTipoEspecificacao);

			retorno.setImovel(imovel);

			if(this.verificarExigenciaImovelPelaEspecificacao(idSolicitacaoTipoEspecificacao)){

				retorno.setHabilitarAlteracaoEndereco(false);
				retorno.setInformarEndereco(false);

				/*
				 * [FS0019] - Verificar endereço do Imóvel
				 * Caso a matrícula do Imóvel seja obrigatória (STEP_ICMATRICULA
				 * com o valor correspondente a um na tabela
				 * SOLICITACAO_TIPO_ESPECIFICACAO) e o endereço do Imóvel seja
				 * descritivo (LGBR_ID com o valor nulo na tabela IMOVEL para
				 * IMOV_ID=matrícula do Imóvel)
				 */
				if(imovel.getLogradouroBairro() == null){

					String enderecoDescritivo = this.getControladorEndereco().pesquisarEnderecoFormatado(imovel.getId());

					retorno.setEnderecoDescritivo(enderecoDescritivo);
				}

			}else if(imovel.getLogradouroBairro() != null){
				retorno.setHabilitarAlteracaoEndereco(true);
				retorno.setInformarEndereco(false);
			}else{
				retorno.setHabilitarAlteracaoEndereco(true);
				retorno.setInformarEndereco(true);
			}

		}else if(levantarExceptionImovelInexistente){

			// [FS0005] Verificar existência da matrícula do Imóvel
			throw new ControladorException("atencao.label_inexistente", null, "Imóvel");
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Obter e habilitar/desabilitar os Dados da Identificação do Local da
	 * ocorrência de acordo com as situações abaixo descritas no caso de uso
	 * [SB0004] Obtém e Habilita/Desabilita Dados da Identificação do Local da
	 * ocorrência e Dados do Solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 28/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao, idSolicitacaoTipo,
	 *            levantarExceptionImovelInexistente
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrenciaAtualizar(Integer idImovel,
					Integer idSolicitacaoTipoEspecificacao, Integer idSolicitacaoTipo, Integer idRegistroAtendimento,
					boolean levantarExceptionImovelInexistente, Usuario usuario) throws ControladorException{

		ObterDadosIdentificacaoLocalOcorrenciaHelper retorno = new ObterDadosIdentificacaoLocalOcorrenciaHelper();

		Imovel imovel = this.getControladorImovel().pesquisarImovelRegistroAtendimento(idImovel);

		if(imovel != null){

			// [SB0030] - Validar Autorização de Abertura de RA pelos Usuários das Empresas de
			// Cobrança Administrativa
			if(this.getControladorImovel()
							.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
											ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA)
							.equals(ConstantesSistema.NAO)){
				throw new ControladorException("atencao.usuario_sem_acesso_atualizar_ra_imovel_cobranca_administrativa", null,
								usuario.getLogin(), Integer.toString(idRegistroAtendimento), Integer.toString(idImovel));
			}

			// [FS0020] - Verificar existência de registro de atendimento para o
			// Imóvel com a mesma especificação
			this.verificarExistenciaRAImovelMesmaEspecificacaoRAAtualizar(imovel.getId(), idRegistroAtendimento,
							idSolicitacaoTipoEspecificacao);

			// [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
			// religação ou restabelecimento
			this.verificarExistenciaServicoReligacaoRestabelecimento(idImovel, idSolicitacaoTipo);

			// [FS0055 - Verificar abertura de RA de corte]
			// [FS0051 - Verificar abertura de RA de corte],
			this.verificarAberturaRACorte(idImovel, idSolicitacaoTipo, idSolicitacaoTipoEspecificacao);

			retorno.setImovel(imovel);

			if(this.verificarExigenciaImovelPelaEspecificacao(idSolicitacaoTipoEspecificacao)){

				retorno.setHabilitarAlteracaoEndereco(false);
				retorno.setInformarEndereco(false);

				/*
				 * [FS0019] - Verificar endereço do Imóvel
				 * Caso a matrícula do Imóvel seja obrigatória (STEP_ICMATRICULA
				 * com o valor correspondente a um na tabela
				 * SOLICITACAO_TIPO_ESPECIFICACAO) e o endereço do Imóvel seja
				 * descritivo (LGBR_ID com o valor nulo na tabela IMOVEL para
				 * IMOV_ID=matrícula do Imóvel)
				 */
				if(imovel.getLogradouroBairro() == null){

					String enderecoDescritivo = this.getControladorEndereco().pesquisarEnderecoFormatado(imovel.getId());

					retorno.setEnderecoDescritivo(enderecoDescritivo);
				}

			}else if(imovel.getLogradouroBairro() != null){
				retorno.setHabilitarAlteracaoEndereco(true);
				retorno.setInformarEndereco(false);
			}else{
				retorno.setHabilitarAlteracaoEndereco(true);
				retorno.setInformarEndereco(true);
			}

		}else if(levantarExceptionImovelInexistente){

			// [FS0005] Verificar existência da matrícula do Imóvel
			throw new ControladorException("atencao.label_inexistente", null, "Imóvel");
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0002] Habilita/Desabilita Município, Bairro, Área do Bairro e Divisão
	 * de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 02/08/2006
	 * @param idSolicitacaoTipo
	 * @return ObterDadosIdentificacaoLocalOcorrenciaHelper
	 * @throws ControladorException
	 */
	public ObterDadosIdentificacaoLocalOcorrenciaHelper habilitarGeograficoDivisaoEsgoto(Integer idSolicitacaoTipo)
					throws ControladorException{

		ObterDadosIdentificacaoLocalOcorrenciaHelper retorno = null;

		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

		filtroSolicitacaoTipo.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoGrupo");

		filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, idSolicitacaoTipo));

		Collection colecaoSolicitacaoTipo = this.getControladorUtil().pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			retorno = new ObterDadosIdentificacaoLocalOcorrenciaHelper();

			SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) Util.retonarObjetoDeColecao(colecaoSolicitacaoTipo);

			if(solicitacaoTipo.getIndicadorFaltaAgua() == ConstantesSistema.SIM.shortValue()){
				retorno.setSolicitacaoTipoRelativoFaltaAgua(true);
			}else{
				retorno.setSolicitacaoTipoRelativoFaltaAgua(false);
			}

			if(solicitacaoTipo.getSolicitacaoTipoGrupo() != null
							&& solicitacaoTipo.getSolicitacaoTipoGrupo().getIndicadorEsgoto() == ConstantesSistema.SIM.shortValue()){
				retorno.setSolicitacaoTipoRelativoAreaEsgoto(true);
			}else{
				retorno.setSolicitacaoTipoRelativoAreaEsgoto(false);
			}
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o Imóvel com a mesma
	 * especificação (existe ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=matrícula do Imóvel e STEP_ID=Id da Especificação selecionada e
	 * RGAT_CDSITUACAO=1).
	 * [FS0020] - Verificar existência de registro de atendimento para o Imóvel
	 * com a mesma especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarExistenciaRAImovelMesmaEspecificacao(Integer idImovel, Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException{

		RegistroAtendimento registroAtendimento = null;

		try{

			registroAtendimento = repositorioRegistroAtendimento.verificarExistenciaRAImovelMesmaEspecificacao(idImovel,
							idSolicitacaoTipoEspecificacao);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(registroAtendimento != null){
			throw new ControladorException("atencao.imovel_ja_possui_ra_com_mesma_especificacao", null, registroAtendimento
							.getSolicitacaoTipoEspecificacao().getDescricao(), registroAtendimento.getId().toString(), registroAtendimento
							.getImovel().getId().toString());
		}
	}

	/**
	 * Caso exista ordem de serviço pendente para o Imóvel com a mesma
	 * especificação (existe ocorrência na tabela ORDEM_SERVICO com
	 * IMOV_ID=matrícula do Imóvel e SVTP_ID contido entre os possíveis
	 * resulados da tabela ESPECIFICACAO_SERVICO_TIPO STEP_ID=Id da Especificação selecionada e
	 * ORSE_CDSITUACAO=1).
	 * 
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarExistenciaOSImovelMesmaEspecificacao(Integer idImovel, Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException{

		FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();

		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
						FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(FiltroEspecificacaoServicoTipo.INDICADOR_GERACAO_AUTOMATICA,
						ConstantesSistema.SIM));

		Collection tipos = Fachada.getInstancia().pesquisar(filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class.getName());

		for(Object tipo : tipos){

			EspecificacaoServicoTipo tipoEspecificacao = (EspecificacaoServicoTipo) tipo;

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();

			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID_IMOVEL, idImovel));
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.SERVICO_TIPO_ID, tipoEspecificacao
							.getServicoTipo().getId()));
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.SITUACAO, OrdemServico.SITUACAO_PENDENTE));

			Collection<Object> ordensServico = Fachada.getInstancia().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

			if(!ordensServico.isEmpty()){

				Iterator<Object> iterator = ordensServico.iterator();

				while(iterator.hasNext()){

					OrdemServico os = (OrdemServico) iterator.next();
					throw new ControladorException("atencao.imovel_ja_possui_os_com_mesma_especificacao", null, os.getId().toString(), os
									.getImovel().getId().toString());

				}
			}
		}
	}

	/**
	 * [UC0366] Atualizar Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o Imóvel com a mesma
	 * especificação (existe ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=matrícula do Imóvel e STEP_ID=Id da Especificação selecionada e
	 * RGAT_CDSITUACAO=1).
	 * [FS0020] - Verificar existência de registro de atendimento para o Imóvel
	 * com a mesma especificação
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarExistenciaRAImovelMesmaEspecificacaoRAAtualizar(Integer idImovel, Integer idRA,
					Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		RegistroAtendimento registroAtendimento = null;

		try{

			registroAtendimento = repositorioRegistroAtendimento.verificarExistenciaRAAtualizarImovelMesmaEspecificacao(idImovel, idRA,
							idSolicitacaoTipoEspecificacao);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(registroAtendimento != null){
			throw new ControladorException("atencao.imovel_ja_possui_ra_com_mesma_especificacao", null, registroAtendimento
							.getSolicitacaoTipoEspecificacao().getDescricao(), registroAtendimento.getSolicitacaoTipoEspecificacao()
							.getId().toString(), registroAtendimento.getImovel().getId().toString());
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o Imóvel (existe
	 * ocorrência na tabela REGISTRO_ATENDIMENTO com IMOV_ID=matrícula do Imóvel
	 * e RGAT_CDSITUACAO=1)
	 * [SB0021]Verifica existência de Registro de Atendimento Pendente para o
	 * Imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAPendenteImovel(Integer idImovel) throws ControladorException{

		boolean retorno = false;
		Integer quantidadeRA = null;

		try{

			quantidadeRA = repositorioRegistroAtendimento.verificaExistenciaRAPendenteImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(quantidadeRA != null && quantidadeRA > 0){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificação
	 * [SB0001] - Gerar Tipo Solicitação com Especificações
	 * 
	 * @author Sávio Luiz
	 * @date 01/08/2006
	 * @param solicitacaoTipo
	 *            ,
	 *            colecaoSolicitacaoTipoEspecificacao, usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirTipoSolicitacaoEspecificacao(SolicitacaoTipo solicitacaoTipo, Collection colecaoSolicitacaoTipoEspecificacao,
					Usuario usuarioLogado) throws ControladorException{

		Integer idTipoSolicitacao = null;

		// [SF0001] - Verificar existencia da descrição
		try{

			Integer idSolicitacaoTipoNaBase = repositorioRegistroAtendimento.verificarExistenciaDescricaoTipoSolicitacao(solicitacaoTipo
							.getDescricao());
			if(idSolicitacaoTipoNaBase != null && !idSolicitacaoTipoNaBase.equals("")){
				throw new ControladorException("atencao.tipo.solicitacao.ja.existe");
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		// inseri tipo solicitacao especificacao
		idTipoSolicitacao = (Integer) getControladorUtil().inserir(solicitacaoTipo);
		solicitacaoTipo.setId(idTipoSolicitacao);

		Iterator iteratorSolicitacaoTipoEspecificacao = colecaoSolicitacaoTipoEspecificacao.iterator();
		while(iteratorSolicitacaoTipoEspecificacao.hasNext()){
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) iteratorSolicitacaoTipoEspecificacao
							.next();
			// recupera a coleção de especificação Serviço tipo
			Collection colecaoEspecificacaoServicoTipo = solicitacaoTipoEspecificacao.getEspecificacaoServicoTipos();
			// limpa a coleção de especificação Serviço tipo no objeto
			// solicitação tipo especificação para não dar erro
			solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(null);
			// inseri o tipo de solicitação no tipo de solicitação especificação
			solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
			// inseri tipo solicitacao especificacao
			Integer idSolicitacaoTipoEspecificacao = (Integer) getControladorUtil().inserir(solicitacaoTipoEspecificacao);
			// caso a coleção de especificação Serviço tipo seja diferente de
			// nula
			if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
				Iterator iteratorSolicitacaoTipoServico = colecaoEspecificacaoServicoTipo.iterator();
				while(iteratorSolicitacaoTipoServico.hasNext()){
					EspecificacaoServicoTipo especificacaoServicoTipo = (EspecificacaoServicoTipo) iteratorSolicitacaoTipoServico.next();
					EspecificacaoServicoTipoPK especificacaoServicoTipoPK = especificacaoServicoTipo.getComp_id();
					especificacaoServicoTipoPK.setIdSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);
					especificacaoServicoTipo.setComp_id(especificacaoServicoTipoPK);
					// inseri especificacao Serviço tipo
					getControladorUtil().inserir(especificacaoServicoTipo);
				}
			}

		}

		return idTipoSolicitacao;
	}

	/**
	 * [UC3096] AcquaGIS
	 * 
	 * @return Objeto JSON
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoJSONHelper> filtrarRegistroAtendimentoWebService(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException{

		Collection<RegistroAtendimentoJSONHelper> toReturn = new ArrayList<RegistroAtendimentoJSONHelper>();
		try{

			Collection<Object[]> objects = repositorioRegistroAtendimento.filtrarRAWebService(filtroRA);

			for(Object[] item : objects){
				RegistroAtendimentoJSONHelper novo = new RegistroAtendimentoJSONHelper();

				Imovel imovel = new Imovel();

				Integer numeroRa = (Integer) item[0];
				novo.setNumeroRa(numeroRa);
				novo.setCodigoEspecificacao((Integer) item[1]);
				novo.setCodigoSituacaoRa((Integer) item[2]);
				novo.setCoordenadaLeste(String.valueOf((BigDecimal) item[3]));
				novo.setCoordenadaNorte(String.valueOf((BigDecimal) item[4]));

				UnidadeOrganizacional unidadeAtualRA = obterUnidadeAtualRA(numeroRa);

				novo.setCodigoUnidadeNegocio(unidadeAtualRA.getId());
				novo.setCodigoUnidadeExecutora(unidadeAtualRA.getId());

				ObterDescricaoSituacaoRAHelper descricaoSituacaoRA = obterDescricaoSituacaoRA(numeroRa);
				novo.setDescricaoSituacaoRa(descricaoSituacaoRA.getDescricaoSituacao());

				Integer idLocalidade = (Integer) item[5];
				Integer codigoSetorComercial = (Integer) item[6];
				Integer numeroQuadra = (Integer) item[7];
				Short lote = (Short) item[8];
				Short subLote = (Short) item[9];

				if(idLocalidade != null && codigoSetorComercial != null && numeroQuadra != null && lote != null && subLote != null){
					imovel.setInscricaoFormatada(idLocalidade, codigoSetorComercial, numeroQuadra, lote, subLote);
					novo.setInscricao(imovel.getInscricaoFormatada());
				}

				Integer idSolicitacaoTipo = (Integer) item[10];
				novo.setCodigoSolicitacaoTipo(idSolicitacaoTipo);

				String descricaoSolicitacaoTipo = (String) item[11];
				novo.setDescricaoSolicitacaoTipo(descricaoSolicitacaoTipo);

				String descricaoSolicitacaoTipoEspecifi = (String) item[12];
				novo.setDescricaoEspecificacao(descricaoSolicitacaoTipoEspecifi);

				Integer matricula = (Integer) item[13];
				if(matricula != null){
					novo.setMatricula(matricula.toString());
				}

				FiltroPesquisaSatisfacao filtroPesquisaSatisfacao = new FiltroPesquisaSatisfacao();
				filtroPesquisaSatisfacao
								.adicionarParametro(new ParametroSimples(FiltroPesquisaSatisfacao.REGISTRO_ATENDIMENTO_ID, numeroRa));
				if(Fachada.getInstancia().pesquisar(filtroPesquisaSatisfacao, PesquisaSatisfacao.class.getName()).size() != 0){
					novo.setIndicadorExistePesquisaSatisfacao(ConstantesSistema.SIM.intValue());
				}else{
					novo.setIndicadorExistePesquisaSatisfacao(ConstantesSistema.NAO.intValue());
				}

				toReturn.add(novo);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return toReturn;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param filtroRA
	 * @return void
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> filtrarRegistroAtendimento(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException{

		Collection<RegistroAtendimento> toReturn = new ArrayList();
		Collection colecaoRA = new ArrayList();

		try{

			colecaoRA = repositorioRegistroAtendimento.filtrarRA(filtroRA);

			if(colecaoRA == null || colecaoRA.isEmpty()){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.filtrar_ra_consulta_vazia");
			}
			toReturn = colecaoRA;

		}catch(ErroRepositorioException ex){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ilex){

			}
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return toReturn;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param filtroRA
	 * @return integer - tamanho
	 * @throws ControladorException
	 */
	public Integer filtrarRegistroAtendimentoTamanho(FiltrarRegistroAtendimentoHelper filtroRA) throws ControladorException{

		try{

			return repositorioRegistroAtendimento.filtrarRATamanho(filtroRA);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento que estáo na unidade atual informada
	 * (existe ocorrência na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da
	 * Unidade Atual e maior TRAM_TMTRAMITE)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<RegistroAtendimento>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaRAPorUnidadeAtual(UnidadeOrganizacional unidadeOrganizacional) throws ControladorException{

		try{

			return repositorioRegistroAtendimento.recuperaRAPorUnidadeAtual(unidadeOrganizacional);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * [SB003] Seleciona Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 09/08/2006
	 * @param colecaoUnidadeSuperior
	 * @throws ControladorException
	 */
	/*
	 * private void recuperarListaRAPorUnidadeSubordinada( Collection<UnidadeOrganizacional>
	 * colecaoUnidadeSuperior) throws ControladorException { Collection<Integer>
	 * colecaoIdRA = null; Collection<UnidadeOrganizacional>
	 * colecaoUnidadesSubordinadas = null; for (Iterator iter =
	 * colecaoUnidadeSuperior.iterator(); iter.hasNext();) {
	 * UnidadeOrganizacional unidadeAtual = (UnidadeOrganizacional) iter
	 * .next(); try { colecaoIdRA = repositorioRegistroAtendimento
	 * .recuperaRAPorUnidadeAtual(unidadeAtual); if (colecaoIdRA != null &&
	 * !colecaoIdRA.isEmpty()) {
	 * colecaoRAPorUnidadeSuperior.addAll(colecaoIdRA); } } catch
	 * (ErroRepositorioException ex) { sessionContext.setRollbackOnly();
	 * ex.printStackTrace(); throw new ControladorException("erro.sistema", ex); }
	 * colecaoUnidadesSubordinadas = getControladorUnidade()
	 * .recuperarUnidadesSubordinadasPorUnidadeSuperior( unidadeAtual); if
	 * (colecaoUnidadesSubordinadas != null &&
	 * !colecaoUnidadesSubordinadas.isEmpty()) {
	 * recuperarListaRAPorUnidadeSubordinada(colecaoUnidadesSubordinadas); } } }
	 */

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS), definir a Unidade Destino da Localidade de
	 * acordo com as regras abaixo. Caso a Especificação não esteja associada a
	 * uma unidade (UNID_ID da tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor
	 * nulo): Caso o Tipo de Solicitação não seja relativoÁrea de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a dois para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicitação selecionado), definir a unidade destino
	 * a partir da localidade informada/selecionada (UNID_ID e UNID_DSUNIDADE da
	 * tabela UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da
	 * tabela LOCALIDADE_SOLIC_TIPO_GRUPO com LOCA_ID=Id da Localidade e
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicitação selecionado) [FS0018Verificar existência de unidade
	 * centralizadora].
	 * [SB0005] Define Unidade Destino da Localidade
	 * 
	 * @author Raphael Rossiter
	 * @date 04/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idSolicitacaoTipo, idSolicitacaoTipoEspecificacao
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestinoLocalidade(
	// Integer idSolicitacaoTipoEspecificacao, Integer idLocalidade,
	// Integer idSolicitacaoTipo, boolean solicitacaoTipoRelativoAreaEsgoto)
	// throws ControladorException {
	//
	// UnidadeOrganizacional retorno = null;
	//
	// FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new
	// FiltroSolicitacaoTipoEspecificacao();
	//
	// filtroSolicitacaoTipoEspecificacao
	// .adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
	//
	// filtroSolicitacaoTipoEspecificacao
	// .adicionarParametro(new ParametroSimples(
	// FiltroSolicitacaoTipoEspecificacao.ID,
	// idSolicitacaoTipoEspecificacao));
	//
	// Collection colecaoSolicitacaoTipoEspecificacao = this
	// .getControladorUtil().pesquisar(
	// filtroSolicitacaoTipoEspecificacao,
	// SolicitacaoTipoEspecificacao.class.getName());
	//
	// if (colecaoSolicitacaoTipoEspecificacao != null
	// && !colecaoSolicitacaoTipoEspecificacao.isEmpty()) {
	//
	// SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao)
	// Util
	// .retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);
	//
	// SistemaParametro sistemaParametro = this.getControladorUtil()
	// .pesquisarParametrosDoSistema();
	//
	// if (sistemaParametro.getIndicadorSugestaoTramite() != null
	// && sistemaParametro.getIndicadorSugestaoTramite().equals(
	// ConstantesSistema.INDICADOR_USO_ATIVO)
	// && solicitacaoTipoEspecificacao.getUnidadeOrganizacional() == null
	// && !solicitacaoTipoRelativoAreaEsgoto) {
	//
	// try {
	//
	// retorno = repositorioRegistroAtendimento
	// .definirUnidadeDestinoLocalidade(idLocalidade,
	// idSolicitacaoTipo);
	//
	// } catch (ErroRepositorioException ex) {
	// ex.printStackTrace();
	// throw new ControladorException("erro.sistema", ex);
	// }
	//
	// if (retorno != null) {
	//
	// /*
	// * [UC0366] Inserir Registro de Atendimento [FS0018]
	// * Verificar existência de unidade centralizadora
	// */
	// retorno = this.getControladorUnidade()
	// .verificarExistenciaUnidadeCentralizadora(retorno);
	// }
	//
	// }
	// }
	//
	// return retorno;
	// }

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o Tipo de Solicitação seja relativo Área de esgoto (SOTG_ICESGOTO da
	 * tabela SOLICITACAO_TIPO_GRUPO com o valor correspondente a um para
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicitação selecionado). Caso a quadra esteja preenchida, obter a
	 * divisão de esgoto da quadra (DVES_ID e DVES_DSDIVISAOESGOTO da tabela
	 * DIVISAO_ESGOTO com DVES_ID=DVES_ID da tabela SISTEMA_ESGOTO com
	 * SESG_ID=SESG_ID da tabela BACIA com BACI_ID=BACI_ID da tabela QUADRA com
	 * QDRA_ID=Id da quadra informada/selecionada).
	 * [SB0006] Obtém divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/08/2006
	 * @param solicitacaoTipoRelativoAreaEsgoto
	 *            ,
	 *            idQuadra
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public DivisaoEsgoto obterDivisaoEsgoto(Integer idQuadra, boolean solicitacaoTipoRelativoAreaEsgoto) throws ControladorException{

		DivisaoEsgoto retorno = null;

		if(solicitacaoTipoRelativoAreaEsgoto){

			try{

				retorno = repositorioRegistroAtendimento.obterDivisaoEsgoto(idQuadra);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso informe a divisão de esgoto: Caso tenha informado a quadra e a mesma
	 * não pertença a divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de DVES_ID da tabela QUADRA com QDRA_ID=Id da quadra
	 * informada).
	 * Caso tenha informado o setor comercial sem a quadra e o setor comercial
	 * não pertença divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=Id do setor
	 * comercial informado).
	 * Caso tenha informado a localidade sem o setor comercial e a localidade
	 * não pertença divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=STCM_ID da
	 * tabela SETOR_COMERCIAL com LOCA_ID=Id da localidade informada).
	 * [FS0013] Verificar compatibilidade entre divisão de esgoto e
	 * localidade/setor/quadra
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idSetorComercial, idQuadra, idDivisaoEsgoto
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, int idDivisaoEsgoto) throws ControladorException{

		DivisaoEsgoto divisaoEsgoto = null;

		if(idQuadra != null){

			try{

				divisaoEsgoto = repositorioRegistroAtendimento.verificarCompatibilidadeDivisaoEsgotoQuadra(idQuadra, idDivisaoEsgoto);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(divisaoEsgoto == null){
				throw new ControladorException("atencao.divisao_esgoto_nao_compativel_com_localidade_setor_quadra", null, "Quadra");
			}

		}else if(idSetorComercial != null){

			try{

				divisaoEsgoto = repositorioRegistroAtendimento
								.verificarCompatibilidadeDivisaoEsgotoSetor(idSetorComercial, idDivisaoEsgoto);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(divisaoEsgoto == null){
				throw new ControladorException("atencao.divisao_esgoto_nao_compativel_com_localidade_setor_quadra", null, "Setor Comercial");
			}
		}else if(idLocalidade != null){

			try{

				divisaoEsgoto = repositorioRegistroAtendimento.verificarCompatibilidadeDivisaoEsgotoLocalidade(idLocalidade,
								idDivisaoEsgoto);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(divisaoEsgoto == null){
				throw new ControladorException("atencao.divisao_esgoto_nao_compativel_com_localidade_setor_quadra", null, "Localidade");
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o sistema deva sugerir a unidade destino para o primeiro
	 * encaminhamento do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na
	 * tabela SISTEMA_PARAMETROS).
	 * Caso a Especificação não esteja associada a uma unidade (UNID_ID da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor nulo):
	 * Caso o Tipo de Solicitação não seja relativo Área de esgoto
	 * (SOTG_ICESGOTO da tabela SOLICITACAO_TIPO_GRUPO com o valor
	 * correspondente a um para SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com
	 * SOTP_ID=Id do Tipo de Solicitação selecionado).
	 * Definir a unidade destino a partir da divisão de esgoto
	 * informada/selecionada (UNID_ID e UNID_DSUNIDADE da tabela
	 * UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da tabela
	 * DIVISAO_ESGOTO com DVES_ID=Id da divisão selecionada) [FS0018 Verificar
	 * existência de unidade centralizadora].
	 * [SB0007] Define Unidade Destino da divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idDivisaoEsgoto
	 *            ,
	 *            idSolicitacaoTipoEspecificacao,
	 *            solicitacaoTipoRelativoAreaEsgoto
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestinoDivisaoEsgoto(
	// Integer idSolicitacaoTipoEspecificacao, Integer idDivisaoEsgoto,
	// boolean solicitacaoTipoRelativoAreaEsgoto)
	// throws ControladorException {
	//
	// UnidadeOrganizacional retorno = null;
	//
	// FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new
	// FiltroSolicitacaoTipoEspecificacao();
	//
	// filtroSolicitacaoTipoEspecificacao
	// .adicionarParametro(new ParametroSimples(
	// FiltroSolicitacaoTipoEspecificacao.ID,
	// idSolicitacaoTipoEspecificacao));
	//
	// Collection colecaoSolicitacaoTipoEspecificacao = this
	// .getControladorUtil().pesquisar(
	// filtroSolicitacaoTipoEspecificacao,
	// SolicitacaoTipoEspecificacao.class.getName());
	//
	// if (colecaoSolicitacaoTipoEspecificacao != null
	// && !colecaoSolicitacaoTipoEspecificacao.isEmpty()) {
	//
	// SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao)
	// Util
	// .retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);
	//
	// SistemaParametro sistemaParametro = this.getControladorUtil()
	// .pesquisarParametrosDoSistema();
	//
	// if (sistemaParametro.getIndicadorSugestaoTramite() != null
	// && sistemaParametro.getIndicadorSugestaoTramite().equals(
	// ConstantesSistema.INDICADOR_USO_ATIVO)
	// && solicitacaoTipoEspecificacao.getUnidadeOrganizacional() == null
	// && solicitacaoTipoRelativoAreaEsgoto) {
	//
	// try {
	//
	// retorno = repositorioRegistroAtendimento
	// .definirUnidadeDestinoDivisaoEsgoto(idDivisaoEsgoto);
	//
	// } catch (ErroRepositorioException ex) {
	// ex.printStackTrace();
	// throw new ControladorException("erro.sistema", ex);
	// }
	//
	// if (retorno != null) {
	//
	// /*
	// * [UC0366] Inserir Registro de Atendimento [FS0018]
	// * Verificar existência de unidade centralizadora
	// */
	// retorno = this.getControladorUnidade()
	// .verificarExistenciaUnidadeCentralizadora(retorno);
	// }
	//
	// }
	// }
	//
	// return retorno;
	// }

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a unidade destino informada não possa receber registros de
	 * atendimento (UNID_ICTRAMITE=2 na tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=Id da unidade destino informada).
	 * [FS0021] - Verificar possibilidade de encaminhamento para a unidade
	 * destino
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param UnidadeOrganizacional
	 * @return void
	 * @throws ControladorException
	 */
	public void verificaPossibilidadeEncaminhamentoUnidadeDestino(UnidadeOrganizacional unidadeDestino) throws ControladorException{

		if(unidadeDestino != null && unidadeDestino.getIndicadorTramite() == ConstantesSistema.INDICADOR_USO_DESATIVO.shortValue()){

			throw new ControladorException("atencao.unidade_destino_nao_possivel_encaminhamento");
		}
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se o Serviço tipo tem como Serviço automatico geração
	 * automática.
	 * [SF0003] Validar Tipo de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void verificarServicoTipoReferencia(Integer idServicoTipo) throws ControladorException{

		try{
			Integer idServicoTipoNaBase = repositorioRegistroAtendimento.verificarServicoTipoReferencia(idServicoTipo);
			if(idServicoTipoNaBase != null && !idServicoTipoNaBase.equals("")){
				throw new ControladorException("atencao.tipo.servico.nao.geracao.automatica");
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se na coleção existe algum ordem de execução .
	 * [SF0004] Validar valor ordem execução parte
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaOrdemExecucao(Collection colecaoEspecificacaoServicoTipo, Short ordemExecucao)
					throws ControladorException{

		if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
			Iterator iteratorEspecificacaoServicoTipo = colecaoEspecificacaoServicoTipo.iterator();
			while(iteratorEspecificacaoServicoTipo.hasNext()){
				EspecificacaoServicoTipo especificacaoServicoTipo = (EspecificacaoServicoTipo) iteratorEspecificacaoServicoTipo.next();
				if(especificacaoServicoTipo.getOrdemExecucao() != null && !especificacaoServicoTipo.equals("")){

					if(especificacaoServicoTipo.getOrdemExecucao().equals(ordemExecucao)){
						throw new ControladorException("atencao.valor.ordem.execucao.ja.existe", null, "" + ordemExecucao);
					}
				}
			}
		}
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificações
	 * Verifica se na coleção existe algum ordem de execução fora da
	 * ordem(1,2,3,4,5,6).Ex.:não exista numero 2.
	 * [SF0004] Validar valor ordem execução 2 parte
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void verificarOrdemExecucaoForaOrdem(Collection colecaoEspecificacaoServicoTipo) throws ControladorException{

		if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
			// ordena a coleção pela ordem de execução
			Collections.sort((List) colecaoEspecificacaoServicoTipo, new Comparator() {

				public int compare(Object a, Object b){

					Short codigo1 = ((EspecificacaoServicoTipo) a).getOrdemExecucao();
					Short codigo2 = ((EspecificacaoServicoTipo) b).getOrdemExecucao();
					if(Util.isVazioOuBranco(codigo1)){
						return -1;
					}
					if(Util.isVazioOuBranco(codigo2)){
						return 1;
					}
					return codigo1.compareTo(codigo2);
				}
			});

			boolean primeiraVez = true;
			Integer ordemExecucaoProximo = null;

			Iterator iteratorEspecificacaoServicoTipo = colecaoEspecificacaoServicoTipo.iterator();
			while(iteratorEspecificacaoServicoTipo.hasNext()){
				EspecificacaoServicoTipo especificacaoServicoTipo = (EspecificacaoServicoTipo) iteratorEspecificacaoServicoTipo.next();
				// caso a ordem de execução seja diferente de nulo
				if(especificacaoServicoTipo.getOrdemExecucao() != null && !especificacaoServicoTipo.getOrdemExecucao().equals("")){
					Integer ordemExecucao = Integer.valueOf(especificacaoServicoTipo.getOrdemExecucao());
					if(primeiraVez){
						ordemExecucaoProximo = ordemExecucao;
						primeiraVez = false;
					}

					if(ordemExecucao.equals(ordemExecucaoProximo)){
						ordemExecucaoProximo = ordemExecucaoProximo + 1;

					}else{
						throw new ControladorException("atencao.valor.ordem.execucao.fora.sequencia", null, "" + ordemExecucao);
					}
				}

			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0020] Verifica Situação do Imóvel e Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 10/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarSituacaoImovelEspecificacao(Imovel imovel, Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		Collection colecaoEspecificacaoImovSitCriterio = null;

		try{

			colecaoEspecificacaoImovSitCriterio = this.repositorioRegistroAtendimento
							.verificarSituacaoImovelEspecificacao(idSolicitacaoTipoEspecificacao);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoEspecificacaoImovSitCriterio != null && !colecaoEspecificacaoImovSitCriterio.isEmpty()){

			// [UC0409 Obter Indicador de existência de hidrômetro na Ligação
			// de Água e poço
			ObterIndicadorExistenciaHidrometroHelper indicadorHidrometroAguaPoco = this.obterIndicadorExistenciaHidrometroLigacaoAguaPoco(
							imovel.getId(), true);

			Short hidrometroLigacaoAguaImovel = indicadorHidrometroAguaPoco.getIndicadorLigacaoAgua();
			Short hidrometroLigacaoPocoImovel = indicadorHidrometroAguaPoco.getIndicadorPoco();

			String comSemHidrometroLigacaoAgua = "SEM";
			String comSemHidrometroLigacaoPoco = "SEM";

			if(hidrometroLigacaoAguaImovel != null && hidrometroLigacaoAguaImovel.equals(ConstantesSistema.SIM)){
				comSemHidrometroLigacaoAgua = "COM";
			}

			if(hidrometroLigacaoPocoImovel != null && hidrometroLigacaoPocoImovel.equals(ConstantesSistema.SIM)){
				comSemHidrometroLigacaoPoco = "COM";
			}

			Iterator especificacaoImovSitCriterioIterator = colecaoEspecificacaoImovSitCriterio.iterator();

			Object[] arrayEspecificacaoImovSitCriterio = null;

			Integer idSituacaoAguaCriterio = null;
			Integer idSituacaoEsgotoCriterio = null;
			Short hidrometroLigacaoAguaCriterio = null;
			Short hidrometroLigacaoPocoCriterio = null;
			String descricaoEspecificacao = null;

			/*
			 * Unão dos dois ïúltimos parâmetros (O Arquivo erro.jsp só
			 * aceita um total de quatro parâmetros)
			 */
			String concatenacao = comSemHidrometroLigacaoAgua + " hidrômetro na ligação de Água. Imóvel " + comSemHidrometroLigacaoPoco
							+ " hidrômetro no poço.";

			boolean correspondencia1 = false;
			boolean correspondencia2 = false;
			boolean correspondencia3 = false;
			boolean correspondencia4 = false;
			boolean atendeCriterio = false;

			while(especificacaoImovSitCriterioIterator.hasNext()){

				arrayEspecificacaoImovSitCriterio = (Object[]) especificacaoImovSitCriterioIterator.next();

				idSituacaoAguaCriterio = (Integer) arrayEspecificacaoImovSitCriterio[0];
				idSituacaoEsgotoCriterio = (Integer) arrayEspecificacaoImovSitCriterio[1];
				hidrometroLigacaoAguaCriterio = (Short) arrayEspecificacaoImovSitCriterio[2];
				hidrometroLigacaoPocoCriterio = (Short) arrayEspecificacaoImovSitCriterio[3];
				descricaoEspecificacao = (String) arrayEspecificacaoImovSitCriterio[4];

				correspondencia1 = false;
				correspondencia2 = false;
				correspondencia3 = false;
				correspondencia4 = false;

				// LigacaoAguaSituacao
				if((idSituacaoAguaCriterio != null && imovel.getLigacaoAguaSituacao().getId().equals(idSituacaoAguaCriterio))
								|| idSituacaoAguaCriterio == null){
					correspondencia1 = true;

				}

				// LigacaoEsgotoSituacao
				if((idSituacaoEsgotoCriterio != null && imovel.getLigacaoEsgotoSituacao().getId().equals(idSituacaoEsgotoCriterio))
								|| idSituacaoEsgotoCriterio == null){

					correspondencia2 = true;

				}

				// hidrometroLigacaoAguaCriterio
				if((hidrometroLigacaoAguaCriterio != null && hidrometroLigacaoAguaImovel.equals(hidrometroLigacaoAguaCriterio))
								|| hidrometroLigacaoAguaCriterio == null){

					correspondencia3 = true;

				}

				// hidrometroLigacaoPocoCriterio
				if((hidrometroLigacaoPocoCriterio != null && hidrometroLigacaoPocoImovel.equals(hidrometroLigacaoPocoCriterio))
								|| hidrometroLigacaoPocoCriterio == null){

					correspondencia4 = true;

				}

				if(correspondencia1 & correspondencia2 & correspondencia3 & correspondencia4){
					atendeCriterio = true;
					break;
				}
			}

			// [FS0034] - Verificar correspondência entre os dados do imóvel para a especificação
			if(!atendeCriterio){

				throw new ControladorException("atencao.imovel_especificacao_correspondencia_invalida", null, imovel.getId().toString(),
								descricaoEspecificacao, imovel.getLigacaoAguaSituacao().getDescricao(), imovel.getLigacaoEsgotoSituacao()
												.getDescricao(), concatenacao);
			}

		}

	}

	/**
	 * [UC0418] Obter Unidade Atual do RA
	 * Este caso de uso permite obter a unidade atual de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 03/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtualRA(Integer idRegistroAtendimento) throws ControladorException{

		UnidadeOrganizacional retorno = null;
		try{

			// verifica a unidade atual(unid_iddestino) do registro de
			// atendimento pelo último Trâmite efetuado
			retorno = repositorioRegistroAtendimento.verificaUnidadeAtualRA(idRegistroAtendimento);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0419] Obter Indicador de Autorização para Manutenção do RA
	 * Este caso de uso Obtém o indicador de autorização para manutenção do RA,
	 * ou seja, se o usuário tem autorização para efetuar a manutenção do RA
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idUnidadeOrganizacional
	 * @param idUusuario
	 * @throws ControladorException
	 */
	public Short obterIndicadorAutorizacaoManutencaoRA(Integer idUnidadeOrganizacional, Integer idUsuario) throws ControladorException{

		// Atribui o valor 2-não ao indicador
		Short retorno = ConstantesSistema.NAO;

		try{

			Object[] dadosUnidade = null;

			if(idUsuario != null){

				// Pesquisar a unidade organizacional do usúario, o indicador da
				// unidade de central de atendimento
				// e a unidade superior
				dadosUnidade = repositorioRegistroAtendimento.pesquisarUnidadeOrganizacionalUsuario(idUsuario);

				Integer idUnidadeSuperior = repositorioRegistroAtendimento.verificaUnidadeSuperiorUnidade(idUnidadeOrganizacional);

				if(dadosUnidade != null){

					Integer idUnidadeOrganizacionalUsuario = (Integer) dadosUnidade[0];
					Short indicadorCentralAtendimento = (Short) dadosUnidade[1];

					/*
					 * Caso a unidade do RA corresponda unidade de lotação do
					 * usuário, atribuir o valor 1-sim
					 */
					if(idUnidadeOrganizacional != null && idUnidadeOrganizacional.equals(idUnidadeOrganizacionalUsuario)){
						retorno = ConstantesSistema.SIM;
						return retorno;
						/*
						 * Caso a unidade de lotação do usuário corresponda
						 * unidade de central de atendimento ao cliente(com
						 * valor 1), atribuir o valor 1-sim
						 */
					}else if(indicadorCentralAtendimento != null
									&& indicadorCentralAtendimento.equals(ConstantesSistema.INDICADOR_USO_ATIVO)){
						retorno = ConstantesSistema.SIM;
						return retorno;
					}else if(idUnidadeSuperior != null){
						/*
						 * Caso a unidade de lotação do usuário corresponda
						 * unidade superior, atribuir o valor 1-sim ao indicador
						 * de unidade subordinada
						 */
						while(idUnidadeSuperior != null){
							if(idUnidadeOrganizacionalUsuario != null && idUnidadeOrganizacionalUsuario.equals(idUnidadeSuperior)){
								retorno = ConstantesSistema.SIM;
								break;
							}
							Integer idUnidadePesquisada = repositorioRegistroAtendimento.verificaUnidadeSuperiorUnidade(idUnidadeSuperior);
							if(idUnidadeSuperior.equals(idUnidadePesquisada)){ // chegou no nível
								// máximo da
								// hierarquia de
								// unidades
								break;
							}
							idUnidadeSuperior = idUnidadePesquisada; // Atribui a nova unidade para
							// verificação
						}
					}
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0420] Obter Descrição da situação da RA
	 * Este caso de uso permite obter a descrição de um registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRA(Integer idRegistroAtendimento) throws ControladorException{

		ObterDescricaoSituacaoRAHelper retorno = new ObterDescricaoSituacaoRAHelper();
		try{

			// verifica o Código da situação(RGAT_CDSITUACAO) do registro de
			// atendimento
			Short situacao = repositorioRegistroAtendimento.verificaSituacaoRA(idRegistroAtendimento);

			if(situacao != null){
				// caso o valor igual a 1
				if(situacao.equals(RegistroAtendimento.SITUACAO_PENDENTE)){
					retorno.setCodigoSituacao(RegistroAtendimento.SITUACAO_PENDENTE);
					retorno.setDescricaoSituacao(RegistroAtendimento.SITUACAO_DESCRICAO_PENDENTE);
					retorno.setDescricaoAbreviadaSituacao(RegistroAtendimento.SITUACAO_DESC_ABREV_PENDENTE);
					// caso o valor igual a 2
				}else if(situacao.equals(RegistroAtendimento.SITUACAO_ENCERRADO)){
					retorno.setCodigoSituacao(RegistroAtendimento.SITUACAO_ENCERRADO);
					retorno.setDescricaoSituacao(RegistroAtendimento.SITUACAO_DESCRICAO_ENCERRADO);
					retorno.setDescricaoAbreviadaSituacao(RegistroAtendimento.SITUACAO_DESC_ABREV_ENCERRADO);
					// caso o valor igual a 3
				}else if(situacao.equals(RegistroAtendimento.SITUACAO_BLOQUEADO)){
					retorno.setCodigoSituacao(RegistroAtendimento.SITUACAO_BLOQUEADO);
					retorno.setDescricaoSituacao(RegistroAtendimento.SITUACAO_DESCRICAO_BLOQUEADO);
					retorno.setDescricaoAbreviadaSituacao(RegistroAtendimento.SITUACAO_DESC_ABREV_BLOQUEADO);
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0421] Obter Unidade de Atendimento do RA
	 * Este caso de uso permite obter a unidade de atendimento de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtendimentoRA(Integer idRegistroAtendimento) throws ControladorException{

		UnidadeOrganizacional retorno = null;
		try{

			// verificar unidade de atendimento do registro de atendimento
			retorno = repositorioRegistroAtendimento.verificaUnidadeAtendimentoRA(idRegistroAtendimento,
							AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0422] Obter endereço da ocorrência do RA
	 * Este caso de uso permite obter o endereço da ocorrência de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String obterEnderecoOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException{

		String enderecoOcorrencia = null;

		String enderecoDescritivo = this.obterEnderecoDescritivoRA(idRegistroAtendimento);
		if(enderecoDescritivo != null && !enderecoDescritivo.equals("")){
			enderecoOcorrencia = enderecoDescritivo;
		}else{
			try{

				Object[] dadosRegistroAtendimento = null;

				// verifica o Código do logradourobairro e o Código do Imóvel no
				// registro atendimento
				dadosRegistroAtendimento = repositorioRegistroAtendimento.verificaEnderecoOcorrenciaRA(idRegistroAtendimento);

				if(dadosRegistroAtendimento != null){

					Integer idLogradouroBairro = (Integer) dadosRegistroAtendimento[0];
					Integer idImovel = (Integer) dadosRegistroAtendimento[1];

					/*
					 * Caso o registro de atendimento esteja associado a um
					 * logradouro, obter o endereço da ocorrência a partir do
					 * registro de atendimento
					 */
					if(idLogradouroBairro != null && !idLogradouroBairro.equals("")){
						enderecoOcorrencia = this.getControladorEndereco().pesquisarEnderecoRegistroAtendimentoFormatado(
										idRegistroAtendimento);
					}
					// Caso contrário, obter a endereço da ocorrência a
					// partir
					// do Imóvel associado ao RA
					else if(idImovel != null && !idImovel.equals("")){
						enderecoOcorrencia = this.getControladorEndereco().pesquisarEnderecoFormatado(idImovel);
					}

				}
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}
		return enderecoOcorrencia;
	}

	/**
	 * [UC0423] Obter endereço do Solicitante do RA
	 * Este caso de uso permite obter o endereço do solicitante de um registro
	 * de atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String obterEnderecoSolicitanteRA(Integer idRegistroAtendimentoSolicitante) throws ControladorException{

		String enderecoSolicitante = null;

		String enderecoDescritivo = this.obterEnderecoDescritivoRA(idRegistroAtendimentoSolicitante);
		if(enderecoDescritivo != null && !enderecoDescritivo.equals("")){
			enderecoSolicitante = enderecoDescritivo;
		}else{
			try{

				Object[] dadosRegistroAtendimentoSolicitante = null;

				// verifica o Código do logradourobairro e o Código do cliente
				// no
				// registro atendimento solicitante
				dadosRegistroAtendimentoSolicitante = repositorioRegistroAtendimento
								.verificaEnderecoRASolicitante(idRegistroAtendimentoSolicitante);

				if(dadosRegistroAtendimentoSolicitante != null){

					Integer idLogradouroBairro = (Integer) dadosRegistroAtendimentoSolicitante[0];
					Integer idCliente = (Integer) dadosRegistroAtendimentoSolicitante[1];
					/*
					 * Caso o solicitante do registro de atendimento esteja
					 * associado a um logradouro, obter o endereço do
					 * solicitante a partir do solicitante do registro de
					 * atendimento
					 */
					if(idLogradouroBairro != null && !idLogradouroBairro.equals("")){
						enderecoSolicitante = this.getControladorEndereco().pesquisarEnderecoRegistroAtendimentoSolicitanteFormatado(
										idRegistroAtendimentoSolicitante);
						// Caso contrário, obter a endereço da ocorrência a
						// partir
						// do cliente associado ao solicitante do RA
					}else{
						if(idCliente != null && !idCliente.equals("")){
							enderecoSolicitante = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(idCliente);
						}
					}
				}

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}
		return enderecoSolicitante;
	}

	/**
	 * [UC0433] Obter Registro de Atendimento Associado
	 * Este caso de uso permite obter o registro de atendimento associado a
	 * outro
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterRAAssociadoHelper obterRAAssociado(Integer idRegistroAtendimento) throws ControladorException{

		ObterRAAssociadoHelper retorno = new ObterRAAssociadoHelper();

		// Atribui o valor 0-SEM RA ASSOCIADO ao Código de existência de RA
		// associado
		retorno.setCodigoExistenciaRAAssociado(RegistroAtendimento.CODIGO_ASSOCIADO_SEM_RA);

		// Atribui o valor nulo ao registro de atendimento associado
		retorno.setRegistroAtendimentoAssociado(null);
		try{

			RegistroAtendimento registroAtendimentoDuplicidade = repositorioRegistroAtendimento
							.verificaDuplicidadeRegistroAtendimento(idRegistroAtendimento);

			RegistroAtendimento registroAtendimentoReativado = repositorioRegistroAtendimento
							.verificaRegistroAtendimentoReativado(idRegistroAtendimento);

			RegistroAtendimento registroAtendimentoReativacao = repositorioRegistroAtendimento
							.verificaRegistroAtendimentoReativacao(idRegistroAtendimento);

			/*
			 * Caso o registro de atendimento esteja associado a outro em razão
			 * de duplicidade, obter o registro de atendimento associado a
			 * partir do registro de atendimento que representa a duplicidade e
			 * atribuir o valor 1-COM RA DE REFERÊNCIA ao ao Código de
			 * existência
			 */
			if(registroAtendimentoDuplicidade != null){
				retorno.setCodigoExistenciaRAAssociado(RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA);

				retorno.setRegistroAtendimentoAssociado(registroAtendimentoDuplicidade);

			}else if(registroAtendimentoReativado != null){

				/*
				 * Caso o registro atendimento tenha sido reativado, obter o
				 * registro de atendimento associado a partir do registro de
				 * atendimento que representa a última reativação realizada e
				 * atribuir o valor 2-COM RA ATUAL ao Código de existência de RA
				 * associado
				 */
				if(registroAtendimentoReativado != null){
					retorno.setCodigoExistenciaRAAssociado(RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL);
					retorno.setRegistroAtendimentoAssociado(registroAtendimentoReativado);

					registroAtendimentoReativado = repositorioRegistroAtendimento
									.verificaRegistroAtendimentoReativado(registroAtendimentoReativado.getId());

				}

				/*
				 * Caso o registro de atendimento seja reativação de outro,
				 * obter o registro de atendimento associado a partir do
				 * registro de atendimento que foi reativado e atribuir o valor
				 * 3-COM RA ANTERIOR ao Código de existência de RA associado
				 */
			}else if(registroAtendimentoReativacao != null){
				retorno.setCodigoExistenciaRAAssociado(RegistroAtendimento.CODIGO_ASSOCIADO_RA_ANTERIOR);
				retorno.setRegistroAtendimentoAssociado(registroAtendimentoReativacao);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0434] Obter Unidade de Encerramento do RA
	 * Este caso de uso permite obter a unidade de encerramento de um registro
	 * de atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeEncerramentoRA(Integer idRegistroAtendimento) throws ControladorException{

		UnidadeOrganizacional retorno = null;
		try{

			// verificar unidade de atendimento do registro de atendimento
			retorno = repositorioRegistroAtendimento.verificaUnidadeAtendimentoRA(idRegistroAtendimento, AtendimentoRelacaoTipo.ENCERRAR);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0445] Obter Nome do Solicitante do RA
	 * Este caso de uso permite obter o nome do solicitante de um registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 09/08/2006
	 * @param idRegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public String obterNomeSolicitanteRA(Integer idRegistroAtendimentoSolicitante) throws ControladorException{

		String retorno = null;
		try{
			Object[] dadosRegistroAtendimento = null;

			if(idRegistroAtendimentoSolicitante != null){

				// Pesquisar a unidade organizacional do usuário e o indicador
				// da unidade de central de atendimento
				dadosRegistroAtendimento = repositorioRegistroAtendimento
								.pesquisarRegistroAtendimentoSolicitante(idRegistroAtendimentoSolicitante);

				if(dadosRegistroAtendimento != null){
					String nomeSolicitante = (String) dadosRegistroAtendimento[0];
					Integer idCliente = (Integer) dadosRegistroAtendimento[1];
					String nomeCliente = (String) dadosRegistroAtendimento[2];
					Integer idUnidade = (Integer) dadosRegistroAtendimento[3];
					String nomeFuncionario = (String) dadosRegistroAtendimento[4];

					/*
					 * Caso o solicitante do registro de atendimento seja um
					 * cliente, obter o nome do solicitante a partir do nome do
					 * cliente
					 */
					if(idCliente != null){
						retorno = nomeCliente;
						/*
						 * Caso o solicitante do registro de atendimento seja um
						 * unidade, obter o nome do solicitante a partir do nome
						 * do Funcionário Responsável
						 */
					}else if(idUnidade != null){
						retorno = nomeFuncionario;
						// Caso contrário obter o nome do solicitante a partir
						// dos dados do solicitante
					}else{
						retorno = nomeSolicitante;
					}
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0424] Consultar Registro Atendimento
	 * Retorno o Tramite mais atual a partir
	 * 
	 * @author Rafael Pinto
	 * @date 10/08/2006
	 * @param idRA
	 * @return Tramite
	 * @throws ErroRepositorioException
	 */
	public Tramite recuperarTramiteMaisAtualPorRA(Integer idRA) throws ControladorException{

		Tramite tramite = null;
		try{
			tramite = repositorioRegistroAtendimento.recuperarTramiteMaisAtualPorRA(idRA);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return tramite;
	}

	/**
	 * [UC0425] - Reiterar Registro de Atendimento
	 * 
	 * @author lms
	 * @date 10/08/2006
	 */
	public Integer reiterarRegistroAtendimento(RegistroAtendimento registroAtendimento, Usuario usuario, RegistroAtendimento raNovo)
					throws ControladorException{

		try{

			Date dataAtual = new Date();

			// [FS0001] - Verificar se o RA está cancelado ou bloqueado

			/*
			 * Caso o usuário esteja tentando reiterar o registro de atendimento
			 * e o mesmo esteja com a situação de cancelado ou bloqueado, o
			 * sistema deverá exibir a mensagem: "Este R.A. já está encerrado ou
			 * bloqueado. não possível reiterá-lo."
			 */
			if(RegistroAtendimento.SITUACAO_ENCERRADO.equals(registroAtendimento.getCodigoSituacao())
							|| RegistroAtendimento.SITUACAO_BLOQUEADO.equals(registroAtendimento.getCodigoSituacao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_atendimento.cancelado_bloqueado");
			}

			// [FS0002] - Verificar se o RA está no prazo de atendimento

			/*
			 * Caso o usuário esteja tentando reiterar o registro de atendimento
			 * e o mesmo ainda esteja no prazo de atendimento, o sistema deverá
			 * exibir a mensagem: "Prazo previsto para o atendimento ainda não
			 * expirou. não possível reiterá-lo."
			 */
			if(Util.compararDataTime(registroAtendimento.getDataPrevistaAtual(), dataAtual) > 0){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_atendimento.prazo_nao_expirado");
			}

			// [FS0003] - Verificar se o RA já foi realizado no mesmo dia

			/*
			 * Caso o usuário esteja tentando reiterar o registro de atendimento
			 * e o mesmo já foi reiterado no mesmo dia, o sistema deverá exibir
			 * a mensagem: "Este R.A. já foi reiterado hoje. não possível
			 * reiterá-lo."
			 */
			// if (registroAtendimento.getUltimaReiteracao() != null) {
			// if (Util.compararDiaMesAno(registroAtendimento
			// .getUltimaReiteracao(), dataAtual) == 0) {
			// sessionContext.setRollbackOnly();
			// throw new ControladorException(
			// "atencao.registro_atendimento.ja_reiterado_hoje");
			// }
			// }

			/*
			 * [FS0004] - Verificar se a unidade organizacional pode reiterar o RA
			 */
			UnidadeOrganizacional unidadeAtualRA = obterUnidadeAtualRA(registroAtendimento.getId());

			Short indicadorAutorizacaoManutencaoRA = obterIndicadorAutorizacaoManutencaoRA(unidadeAtualRA.getId(), usuario.getId());

			/*
			 * Caso a reiteração de RA não esteja liberada para qualquer usuário da empresa
			 * (PASI_VLPARAMETRO com o valor 2 (NÃO) na tabela PARAMETRO_SISTEMA com
			 * PASI_CDPARAMETRO=”PARM_ICLIBERARREITERACAORA”):
			 */
			if(ParametroAtendimentoPublico.P_INDICADOR_LIBERAR_REITERACAO_RA.executar().equals(ConstantesSistema.NAO.toString())){
				/*
				 * Caso o indicador de autorização de manutenção do R.A. esteja com
				 * não, o sistema deverá exibir a mensagem: "Este R.A. foi
				 * registrado por outra Unidade Organizacional, reiteração
				 * impedida."
				 */
				if(ConstantesSistema.NAO.equals(indicadorAutorizacaoManutencaoRA)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.registro_atendimento.manutencao_nao_autorizada");
				}
			}

			// [FS0005] - Atualização realizada por outro usuário

			/*
			 * Caso o usuário esteja tentando alterar o registro de atendimento
			 * e a mesma já tenha sido atualizada durante a manutenção corrente,
			 * o sistema deverá exibir a mensagem: "Este R.A. foi atualizado por
			 * outro usuário. não foi possível efetuar a reiteração."
			 */
			FiltroRegistroAtendimento filtro = new FiltroRegistroAtendimento();
			filtro.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, registroAtendimento.getId()));
			Collection collection = getControladorUtil().pesquisar(filtro, RegistroAtendimento.class.getName());
			Date timestamp = ((RegistroAtendimento) collection.iterator().next()).getUltimaAlteracao();

			if(timestamp.after(registroAtendimento.getUltimaAlteracao())){
				throw new ControladorException("atencao.registro_atendimento.timestamp");
			}

			// atualiza o registro de atendimento
			short qtd = 1;
			if(registroAtendimento.getQuantidadeReiteracao() != null){
				qtd = (short) (registroAtendimento.getQuantidadeReiteracao() + 1);
			}

			registroAtendimento.setQuantidadeReiteracao(qtd);
			registroAtendimento.setUltimaReiteracao(dataAtual);
			registroAtendimento.setUltimaAlteracao(dataAtual);

			// [FS0006] - Verificar sucesso da transação
			getControladorUtil().atualizar(registroAtendimento);

			// teste por alteraçoes na lei do call center
			// que ao inserir uma Ra pode ter atualizado uma outra RA.
			if(raNovo != null){
				getControladorUtil().inserir(raNovo);

				// Registro Atendimento Solicitante
				FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
				filtroRegistroAtendimentoSolicitante
								.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoSolicitante.SOLICITANTE_FONES);
				filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoSolicitante.CLIENTE);
				filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
								FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, registroAtendimento.getId().intValue()));

				Collection<RegistroAtendimentoSolicitante> colecaoRegistroAtendimentoSolicitante = getControladorUtil().pesquisar(
								filtroRegistroAtendimentoSolicitante, RegistroAtendimentoSolicitante.class.getName());
				for(RegistroAtendimentoSolicitante rasoTmp : colecaoRegistroAtendimentoSolicitante){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = new RegistroAtendimentoSolicitante();
					try{
						PropertyUtils.copyProperties(registroAtendimentoSolicitante, rasoTmp);
					}catch(IllegalAccessException e){
						throw new ControladorException("erro.sistema", e);
					}catch(InvocationTargetException e){
						throw new ControladorException("erro.sistema", e);
					}catch(NoSuchMethodException e){
						throw new ControladorException("erro.sistema", e);
					}

					registroAtendimentoSolicitante.setID(null);
					registroAtendimentoSolicitante.setRegistroAtendimento(raNovo);
					registroAtendimentoSolicitante.setUltimaAlteracao(new Date());

					Object idRASolicitante = getControladorUtil().inserir(registroAtendimentoSolicitante);

					// Solicitante Fone
					Iterator<SolicitanteFone> itSolicitanteFone = rasoTmp.getSolicitanteFones().iterator();
					while(itSolicitanteFone.hasNext()){
						SolicitanteFone solicitanteFoneTMP = itSolicitanteFone.next();
						SolicitanteFone solicitanteFone = new SolicitanteFone();
						try{
							PropertyUtils.copyProperties(solicitanteFone, solicitanteFoneTMP);
						}catch(IllegalAccessException e){
							throw new ControladorException("erro.sistema", e);
						}catch(InvocationTargetException e){
							throw new ControladorException("erro.sistema", e);
						}catch(NoSuchMethodException e){
							throw new ControladorException("erro.sistema", e);
						}
						solicitanteFone.setId(null);
						solicitanteFone.setUltimaAlteracao(new Date());
						registroAtendimentoSolicitante.setID((Integer) idRASolicitante);
						solicitanteFone.setRegistroAtendimentoSolicitante(registroAtendimentoSolicitante);

						getControladorUtil().inserir(solicitanteFone);
					}
				}

				// Registro Atendimento Unidade
				RegistroAtendimentoUnidade registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
				registroAtendimentoUnidade.setUnidadeOrganizacional(usuario.getUnidadeOrganizacional());
				registroAtendimentoUnidade.setRegistroAtendimento(raNovo);
				registroAtendimentoUnidade.setUsuario(usuario);

				AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
				atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
				registroAtendimentoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
				registroAtendimentoUnidade.setUltimaAlteracao(new Date());
				registroAtendimentoUnidade.setRegistroAtendimento(raNovo);

				getControladorUtil().inserir(registroAtendimentoUnidade);

				// Tramite
				Tramite tramite = new Tramite();
				tramite.setRegistroAtendimento(raNovo);
				tramite.setParecerTramite(Tramite.TRAMITE_GERADO_PELO_SISTEMA_ABERTURA_RA);
				tramite.setDataTramite(new Date());
				tramite.setUltimaAlteracao(new Date());

				UnidadeOrganizacional unidadeOrigem = new UnidadeOrganizacional();
				unidadeOrigem.setId(usuario.getUnidadeOrganizacional().getId());
				UnidadeOrganizacional unidadeDestino = new UnidadeOrganizacional();
				unidadeDestino.setId(usuario.getUnidadeOrganizacional().getId());
				tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);
				tramite.setUnidadeOrganizacionalDestino(unidadeDestino);

				tramite.setUsuarioRegistro(usuario);
				tramite.setUsuarioResponsavel(usuario);

				getControladorUtil().inserir(tramite);
			}

			return registroAtendimento.getId();

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;

		}

	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar os parametros para atualizar o registro atendimento escolhido
	 * pelo usuário
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsRegistroAtendimento(Integer idRegistroAtendimento) throws ControladorException{

		try{
			return repositorioRegistroAtendimento.pesquisarParmsRegistroAtendimento(idRegistroAtendimento);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Verificar existencia ordem de Serviço para o registro atendimento
	 * pesquisado
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOrdemServicoParaRA(Integer idRegistroAtendimento) throws ControladorException{

		try{
			return repositorioRegistroAtendimento.verificarOrdemServicoParaRA(idRegistroAtendimento);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public RegistroAtendimento validarRegistroAtendimento(Integer idRA) throws ControladorException{

		RegistroAtendimento registroAtendimento = null;

		try{
			registroAtendimento = repositorioRegistroAtendimento.pesquisarRegistroAtendimento(idRA);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0001] - Validar Registro de Atendimento

		/*
		 * Caso o registro de atendimento seja recebido e a especificação associada ao mesmo não
		 * permite geração de ordem de Serviço, exibir a
		 * mensagem:
		 * "Especificação da Solicitação do Registro de Atendimento <<id_Ra>> não permite geração de ordem de Serviço"
		 * e retornar para o
		 * passo correspondente no fluxo principal.
		 */
		if(!Util.isVazioOuBranco(registroAtendimento)
						&& !Util.isVazioOuBranco(registroAtendimento.getSolicitacaoTipoEspecificacao())
						&& ConstantesSistema.NAO.equals(registroAtendimento.getSolicitacaoTipoEspecificacao()
										.getIndicadorGeracaoOrdemServico())){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.registro_atendimento.nao_permite_geracao_os", null, "" + registroAtendimento.getId());
		}

		/*
		 * Caso o registro de atendimento seja informado e a situação do mesmo esteja encerrado,
		 * exibir a mensagem: "Situação do Registro de
		 * Atendimento <<id_RA>> <<descricao_situação>> e não permite geração de ordem de Serviço."
		 * e retornar para o passo correspondente no fluxo
		 * principal.
		 */

		if(!Util.isVazioOuBranco(registroAtendimento)
						&& !RegistroAtendimento.SITUACAO_PENDENTE.equals(registroAtendimento.getCodigoSituacao())){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.registro_atendimento.diferente_pendente", null, new String[] {""
							+ registroAtendimento.getId(), "" + registroAtendimento.getDescricaoSituacao()});
		}

		return registroAtendimento;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 11/08/2006
	 */
	public void verificarUnidadeUsuario(RegistroAtendimento registroAtendimento, Usuario usuario) throws ControladorException{

		// [FS0004] - Verificar Unidade do usuário

		/*
		 * Caso o indicador de autorização para manutenção do RA retornado pelo
		 * UC0419 esteja com valor correspondente a 2-não, exibir a mensagem:
		 * "Este RA foi aberto pela unidade <<descricao_unidade>>. não possível
		 * gerar ordem de Serviço." E retornar para o passo correspondente do
		 * fluxo principal.
		 */
		if(!Util.isVazioOuBranco(registroAtendimento)){
			UnidadeOrganizacional unidadeOrganizacional = obterUnidadeAtualRA(registroAtendimento.getId());

			Short indicadorAutorizacaoManutencaoRA = obterIndicadorAutorizacaoManutencaoRA(unidadeOrganizacional.getId(), usuario.getId());

			if(ConstantesSistema.NAO.equals(indicadorAutorizacaoManutencaoRA)){

				throw new ControladorException("atencao.unidade_atendimento.nao_permite_geracao_os", null, ""
								+ unidadeOrganizacional.getDescricao());
			}
		}
	}

	/**
	 * [UC0446] Consultar Trâmites
	 * Retorna a Coleção de Tramites do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<Tramite>
	 * @throws ControladorException
	 */
	public Collection<Tramite> obterTramitesRA(Integer idRA) throws ControladorException{

		Collection<Tramite> colecaoTramite = new ArrayList();
		try{
			colecaoTramite = repositorioRegistroAtendimento.obterTramitesRA(idRA);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoTramite;
	}

	/**
	 * [UC0447] Consultar RA Solicitantes
	 * Retorna a Coleção de registro de atendimento solicitantes
	 * 
	 * @author Rafael Pinto
	 * @date 14/08/2006
	 * @param idRA
	 * @return Collection<RegistroAtendimentoSolicitante>
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoSolicitante> obterRASolicitante(Integer idRA) throws ControladorException{

		Collection<RegistroAtendimentoSolicitante> colecaoRASolicitante = new ArrayList();
		try{
			colecaoRASolicitante = repositorioRegistroAtendimento.obterRASolicitante(idRA);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoRASolicitante;
	}

	/**
	 * [UC0431] Consultar Ordens de Serviço do Registro Atendimento
	 * Retorna a Coleção de OS's do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> obterOSRA(Integer idRA) throws ControladorException{

		Collection<OrdemServico> colecaoOS = new ArrayList();
		try{
			colecaoOS = repositorioRegistroAtendimento.obterOSRA(idRA);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return colecaoOS;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [FS0012] - Verificar possibilidade de atualização do registro de
	 * atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 14/08/2006
	 * @param idRA
	 *            ,idUsuarioLogado
	 * @throws ControladorException
	 */

	public UnidadeOrganizacional verificarPossibilidadeAtualizacaoRA(Integer idRA, Integer idUsuarioLogado) throws ControladorException{

		// [UC0418] - Obter Unidade Atual do RA
		UnidadeOrganizacional unidadeOrganizacional = obterUnidadeAtualRA(idRA);
		// [UC0419] - Obter Indicador de Autorização para Manutenção do RA
		Short indicadorAutorizacaoRA = obterIndicadorAutorizacaoManutencaoRA(unidadeOrganizacional.getId(), idUsuarioLogado);
		// [UC0420] - Obter Descrição da situação do RA
		ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = obterDescricaoSituacaoRA(idRA);

		// caso o indicador de atualização seja igual a 2-não
		if(indicadorAutorizacaoRA != null && indicadorAutorizacaoRA.equals(Short.valueOf("2"))){
			throw new ControladorException("atencao.unidade_organizacional_nao_pode_ser_alterada", null,
							unidadeOrganizacional.getDescricao());
		}

		Object[] parmsRA = null;
		Integer idOrdemServProg = null;
		try{
			parmsRA = repositorioRegistroAtendimento.verificarParmsRA(idRA);

			idOrdemServProg = repositorioRegistroAtendimento.verificarExistenciaOrdemServicoProgramacaoRA(idRA);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(parmsRA != null){
			// caso o registro atendimento duplicidade esteja diferente de nulo
			if(parmsRA[0] != null){
				Integer idDuplicidade = (Integer) parmsRA[0];
				throw new ControladorException("atencao.registro_atendimento_duplicidade", null, "" + idRA, "" + idDuplicidade);
			}
			// caso a situação de registro atendimento seja igual a encerrado
			if(parmsRA[1] != null){
				Short situacaoRA = (Short) parmsRA[1];
				if(situacaoRA.equals(RegistroAtendimento.SITUACAO_ENCERRADO)){
					throw new ControladorException("atencao.registro_atendimento_situacao", null, "" + idRA,
									obterDescricaoSituacaoRAHelper.getDescricaoSituacao());
				}
			}
		}
		// caso exista ordem de Serviço não encerrada e programada para o
		// registro de atendimento
		if(idOrdemServProg != null){
			throw new ControladorException("atencao.ordem_servico_nao_encerrada_programada_ra", null, "" + idRA);
		}

		return unidadeOrganizacional;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação exija a matrícula do Imóvel (STEP_ICMATRICULA com o
	 * valor correspondente a um na tabela SOLICITACAO_TIPO_ESPECIFICACAO.
	 * 
	 * @author Raphael Rossiter
	 * @date 14/08/2006
	 * @param SolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean especificacaoExigeMatriculaImovel(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao) throws ControladorException{

		boolean retorno = false;

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						solicitacaoTipoEspecificacao.getId()));

		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

			SolicitacaoTipoEspecificacao especificacaoBase = (SolicitacaoTipoEspecificacao) Util
							.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);

			if(especificacaoBase.getIndicadorMatricula() != null
							&& especificacaoBase.getIndicadorMatricula().equals(ConstantesSistema.INDICADOR_USO_ATIVO.intValue())){

				retorno = true;
			}
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a especificação exija o pavimento da rua (STEP_ICPAVIMENTORUA com o
	 * valor correspondente a um na tabela SOLICITACAO_TIPO_ESPECIFICACAO.
	 * 
	 * @author Raphael Rossiter
	 * @date 14/08/2006
	 * @param SolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean especificacaoExigePavimentoRua(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		boolean retorno = false;

		if(solicitacaoTipoEspecificacao.getIndicadorPavimentoRua() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a especificação exija o pavimento da calçada
	 * (STEP_ICPAVIMENTOCALCADA com o valor correspondente a um na tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO.
	 * 
	 * @author Raphael Rossiter
	 * @date 14/08/2006
	 * @param SolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean especificacaoExigePavimentoCalcada(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		boolean retorno = false;

		if(solicitacaoTipoEspecificacao.getIndicadorPavimentoCalcada() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0409] Obter Indicador de existência de hidrômetro na Ligação de Água e
	 * no poço
	 * Este caso de uso Obtém o indicador de existência de hidrômetro na ligação
	 * de Água e no poço
	 * 
	 * @author Ana Maria
	 * @date 14/08/2006
	 * @param idImovel
	 * @throws ControladorException
	 */
	public ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Integer idImovel,
					boolean considerarSituacaoLigacao) throws ControladorException{

		ObterIndicadorExistenciaHidrometroHelper retorno = new ObterIndicadorExistenciaHidrometroHelper();

		retorno.setIndicadorLigacaoAgua(ConstantesSistema.NAO);
		retorno.setIndicadorPoco(ConstantesSistema.NAO);

		try{
			Object[] dadosImovel = null;

			if(idImovel != null){

				// Pesquisar a situação e o indicador de existência de
				// hidrômetro na ligação de Água e no poço
				dadosImovel = repositorioRegistroAtendimento.pesquisarHidrometroImovel(idImovel);

				if(dadosImovel != null){
					Integer idSituacaoLigacaoAgua = (Integer) dadosImovel[0];
					Integer idSituacaoLigacaoEsgoto = (Integer) dadosImovel[1];
					Integer idHidrometroPoco = (Integer) dadosImovel[2];
					Integer idHidrometroLigacaoAgua = (Integer) dadosImovel[3];

					/*
					 * Caso a situação da ligação de Água do Imóvel(last_id)
					 * corresponda a ligado ou cortado e exista hidrômetro
					 * instalado na ligação de Água (hidi_id com valor diferente
					 * de nulo na tabela LIGACAO_AGUA), atribui o valor 1-SIM ao
					 * indicador de existência de hidrômetro na ligação de Água
					 */
					if(considerarSituacaoLigacao){
						if(idSituacaoLigacaoAgua.equals(LigacaoAguaSituacao.LIGADO)
										|| idSituacaoLigacaoAgua.equals(LigacaoAguaSituacao.CORTADO)){
							if(idHidrometroLigacaoAgua != null && !idHidrometroLigacaoAgua.equals("")){
								retorno.setIndicadorLigacaoAgua(ConstantesSistema.SIM);
							}
						}
					}else{
						if(idHidrometroLigacaoAgua != null && !idHidrometroLigacaoAgua.equals("")){
							retorno.setIndicadorLigacaoAgua(ConstantesSistema.SIM);
						}
					}
					/*
					 * Caso a situação da ligação do esgoto do Imóvel(lest_id)
					 * corresponda a ligado ou tamponado e exista hidrômetro
					 * instalado no poço (hidi_id com valor diferente de nulo na
					 * tabela IMOVEL), atribui o valor 1-SIM ao indicador de
					 * existência de hidrômetro no poço
					 */
					if(considerarSituacaoLigacao){
						if(idSituacaoLigacaoEsgoto.equals(LigacaoEsgotoSituacao.LIGADO)
										|| idSituacaoLigacaoEsgoto.equals(LigacaoEsgotoSituacao.TAMPONADO)){
							if(idHidrometroPoco != null && !idHidrometroPoco.equals("")){
								retorno.setIndicadorPoco(ConstantesSistema.SIM);
							}
						}
					}else{
						if(idHidrometroPoco != null && !idHidrometroPoco.equals("")){
							retorno.setIndicadorPoco(ConstantesSistema.SIM);
						}
					}
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0024] - Verificar registro de Atendimento Sem Identificação do Local
	 * de ocorrência
	 * 
	 * @author Sávio Luiz
	 * @date 15/08/2006
	 * @param idRA
	 *            ,idUsuarioLogado
	 * @throws ControladorException
	 */

	public int verificarRASemIdentificacaoLO(Integer idImovel, Integer idRA) throws ControladorException{

		// Indicador de validação de matricula do Imóvel com valor = 2(não)
		int indicadorValidacaoImovel = 2;

		Short codigoSituacao = null;
		Integer idLogradouroBairro = null;
		try{
			codigoSituacao = repositorioRegistroAtendimento.pesquisarCdSituacaoRegistroAtendimento(idRA);
			if(idImovel != null){
				idLogradouroBairro = repositorioRegistroAtendimento.pesquisarImovelDescritivo(idImovel);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		// caso o registro de atendimento esteja sem a identificação do local de
		// ocorrência
		if(codigoSituacao != null && codigoSituacao.equals(RegistroAtendimento.SITUACAO_BLOQUEADO)){
			// caso o registro de atendimento esteja acsociado a um Imóvel
			if(idImovel != null){
				// caso o endereço do imovel não seja descritivo
				if(idLogradouroBairro != null){
					// atribuir o valor 1(Sim) ao indicador de validação de
					// matrícula do Imóvel
					indicadorValidacaoImovel = 1;
				}
			}
		}
		return indicadorValidacaoImovel;

	}

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * Este caso de uso permite obter dados de um registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 14/08/2006
	 * @author Saulo Lima
	 * @date 07/01/2009
	 *       Adicionados os campos de Ligação de Água ao imóvel
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimento(Integer idRegistroAtendimento) throws ControladorException{

		ObterDadosRegistroAtendimentoHelper retorno = null;

		if(idRegistroAtendimento != null){

			retorno = new ObterDadosRegistroAtendimentoHelper();

			Object[] dadosRegistroAtendimento = null;
			try{

				// Pesquisa os dados do registro de atendimento
				dadosRegistroAtendimento = repositorioRegistroAtendimento.pesquisarDadosRegistroAtendimento(idRegistroAtendimento);

				if(dadosRegistroAtendimento != null){

					RegistroAtendimento ra = new RegistroAtendimento();

					// número do RA
					ra.setId((Integer) dadosRegistroAtendimento[0]);

					// número Manual
					if(dadosRegistroAtendimento[65] != null){
						ra.setManual((Integer) dadosRegistroAtendimento[65]);
					}

					ra.setCodigoSituacao((Short) dadosRegistroAtendimento[1]);
					ra.setUltimaAlteracao((Date) dadosRegistroAtendimento[30]);
					ra.setQuantidadeReiteracao((Short) dadosRegistroAtendimento[56]);
					ra.setUltimaReiteracao((Date) dadosRegistroAtendimento[57]);
					ra.setParecerEncerramento((String) dadosRegistroAtendimento[58]);

					// Situação do RA
					ObterDescricaoSituacaoRAHelper SituacaoRA = obterDescricaoSituacaoRA(idRegistroAtendimento);
					retorno.setDescricaoSituacaoRA(SituacaoRA.getDescricaoSituacao());

					// Dados do RA Associado
					setDadosRAAssociado(idRegistroAtendimento, retorno);

					// Especificação e Tipo de solicitação
					if((Integer) dadosRegistroAtendimento[2] != null){
						SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
						solicitacaoTipoEspecificacao.setId((Integer) dadosRegistroAtendimento[2]);
						solicitacaoTipoEspecificacao.setDescricao((String) dadosRegistroAtendimento[3]);

						// Colocado por Raphael Rossiter em 24/08/2006
						if(dadosRegistroAtendimento[31] != null){
							solicitacaoTipoEspecificacao.setIndicadorCliente((Short) dadosRegistroAtendimento[31]);
						}

						// Colocado por Leonardo Regis em 28/08/2006
						if(dadosRegistroAtendimento[32] != null){
							solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento((Integer) dadosRegistroAtendimento[32]);
						}

						if(dadosRegistroAtendimento[63] != null){
							solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito((Integer) dadosRegistroAtendimento[63]);
						}

						if(dadosRegistroAtendimento[64] != null){
							solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito((Integer) dadosRegistroAtendimento[64]);
						}

						if((Integer) dadosRegistroAtendimento[4] != null){
							SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
							solicitacaoTipo.setId((Integer) dadosRegistroAtendimento[4]);
							solicitacaoTipo.setDescricao((String) dadosRegistroAtendimento[5]);
							solicitacaoTipo.setIndicadorTarifaSocial((Short) dadosRegistroAtendimento[29]);
							solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
						}
						ra.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
					}
					// Meio de solicitação
					if((Integer) dadosRegistroAtendimento[6] != null){
						MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
						meioSolicitacao.setId((Integer) dadosRegistroAtendimento[6]);
						meioSolicitacao.setDescricao((String) dadosRegistroAtendimento[7]);
						ra.setMeioSolicitacao(meioSolicitacao);
					}
					// Dados do Imóvel
					if((Integer) dadosRegistroAtendimento[8] != null){
						Imovel imovel = new Imovel();
						imovel.setId((Integer) dadosRegistroAtendimento[8]);
						if((Integer) dadosRegistroAtendimento[9] != null){
							Localidade localidade = new Localidade();
							localidade.setId((Integer) dadosRegistroAtendimento[9]);

							imovel.setLocalidade(localidade);
						}
						if((Integer) dadosRegistroAtendimento[10] != null){
							SetorComercial setorComercial = new SetorComercial();
							setorComercial.setCodigo((Integer) dadosRegistroAtendimento[10]);
							imovel.setSetorComercial(setorComercial);
						}
						if((Integer) dadosRegistroAtendimento[11] != null){
							Quadra quadra = new Quadra();
							quadra.setNumeroQuadra((Integer) dadosRegistroAtendimento[11]);
							imovel.setQuadra(quadra);
						}
						if(dadosRegistroAtendimento[67] != null){
							retorno.setCodigoRota((Short) dadosRegistroAtendimento[67]);
						}
						if(dadosRegistroAtendimento[68] != null){
							retorno.setSequencialRota((Integer) dadosRegistroAtendimento[68]);
						}
						if(dadosRegistroAtendimento[69] != null){
							imovel.setNumeroImovel((String) dadosRegistroAtendimento[69]);
							ra.setNumeroImovel((String) dadosRegistroAtendimento[69]);
						}
						if(dadosRegistroAtendimento[75] != null){
							Integer idRota = (Integer) dadosRegistroAtendimento[75];

							Rota rota = new Rota();
							rota.setId(idRota);

							imovel.setRota(rota);
						}
						if(dadosRegistroAtendimento[76] != null){
							imovel.setNumeroSegmento((Short) dadosRegistroAtendimento[76]);
						}
						if((Integer) dadosRegistroAtendimento[59] != null){
							LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
							ligacaoAguaSituacao.setId((Integer) dadosRegistroAtendimento[59]);
							ligacaoAguaSituacao.setDescricao((String) dadosRegistroAtendimento[60]);
							imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
						}
						if((Integer) dadosRegistroAtendimento[61] != null){
							LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
							ligacaoEsgotoSituacao.setId((Integer) dadosRegistroAtendimento[61]);
							ligacaoEsgotoSituacao.setDescricao((String) dadosRegistroAtendimento[62]);
							imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
						}
						imovel.setLote((Short) dadosRegistroAtendimento[12]);
						imovel.setSubLote((Short) dadosRegistroAtendimento[13]);

						if((Integer) dadosRegistroAtendimento[71] != null){
							LigacaoAgua ligacaoAgua = new LigacaoAgua();
							ligacaoAgua.setId((Integer) dadosRegistroAtendimento[71]);

							if((Integer) dadosRegistroAtendimento[72] != null){
								CorteTipo corteTipo = new CorteTipo();
								corteTipo.setId((Integer) dadosRegistroAtendimento[72]);
								ligacaoAgua.setCorteTipo(corteTipo);
							}
							if((Integer) dadosRegistroAtendimento[73] != null){
								SupressaoTipo supressaoTipo = new SupressaoTipo();
								supressaoTipo.setId((Integer) dadosRegistroAtendimento[73]);
								ligacaoAgua.setSupressaoTipo(supressaoTipo);
							}
							imovel.setLigacaoAgua(ligacaoAgua);
						}

						ra.setImovel(imovel);
					}

					// Data e Hora do Atendimento
					ra.setRegistroAtendimento((Date) dadosRegistroAtendimento[14]);

					// Data Prevista
					ra.setDataPrevistaAtual((Date) dadosRegistroAtendimento[15]);

					ra.setDataPrevistaOriginal((Date) dadosRegistroAtendimento[46]);
					ra.setObservacao((String) dadosRegistroAtendimento[47]);
					ra.setIndicadorAtendimentoOnline((Short) dadosRegistroAtendimento[48]);
					if(dadosRegistroAtendimento[49] != null){
						ra.setDataInicioEspera((Date) dadosRegistroAtendimento[49]);
					}

					if(dadosRegistroAtendimento[50] != null){
						ra.setDataFimEspera((Date) dadosRegistroAtendimento[50]);
					}

					// Senha de Atendimento
					if(dadosRegistroAtendimento[70] != null){
						ra.setSenhaAtendimento((Integer) dadosRegistroAtendimento[70]);
					}

					// Data do Encerramento
					ra.setDataEncerramento((Date) dadosRegistroAtendimento[16]);

					// Motivo do Encerramento
					if((Integer) dadosRegistroAtendimento[17] != null){
						AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
						atendimentoMotivoEncerramento.setId((Integer) dadosRegistroAtendimento[17]);
						atendimentoMotivoEncerramento.setDescricao((String) dadosRegistroAtendimento[18]);
						atendimentoMotivoEncerramento.setIndicadorExecucao((Short) dadosRegistroAtendimento[66]);
						ra.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
					}

					// Dados do Solicitante do RA
					setSolicitanteRA(idRegistroAtendimento, retorno);

					// Dados do endereço de ocorrência do RA
					setEnderecoOcorrenciaRA(idRegistroAtendimento, retorno);

					// Ponto de Referência
					ra.setPontoReferencia((String) dadosRegistroAtendimento[19]);
					// Dados Área do Bairro
					if((Integer) dadosRegistroAtendimento[20] != null){
						BairroArea bairroArea = new BairroArea();
						bairroArea.setId((Integer) dadosRegistroAtendimento[20]);
						bairroArea.setNome((String) dadosRegistroAtendimento[21]);
						if((Integer) dadosRegistroAtendimento[22] != null){

							Bairro bairro = new Bairro();
							bairro.setId((Integer) dadosRegistroAtendimento[22]);
							bairro.setNome((String) dadosRegistroAtendimento[23]);

							if(dadosRegistroAtendimento[42] != null){
								Municipio municipio = new Municipio();
								municipio.setId((Integer) dadosRegistroAtendimento[42]);
								municipio.setNome((String) dadosRegistroAtendimento[43]);

								bairro.setMunicipio(municipio);
							}
							bairroArea.setBairro(bairro);
						}
						ra.setBairroArea(bairroArea);
					}

					// Local/Setor/Quadra
					if((Integer) dadosRegistroAtendimento[26] != null){
						Localidade localidadeRA = new Localidade();
						localidadeRA.setId((Integer) dadosRegistroAtendimento[26]);
						localidadeRA.setDescricao((String) dadosRegistroAtendimento[51]);
						ra.setLocalidade(localidadeRA);
					}
					if((Integer) dadosRegistroAtendimento[27] != null){
						SetorComercial setorRA = new SetorComercial();
						setorRA.setId((Integer) dadosRegistroAtendimento[40]);
						setorRA.setCodigo((Integer) dadosRegistroAtendimento[27]);
						setorRA.setDescricao((String) dadosRegistroAtendimento[55]);
						ra.setSetorComercial(setorRA);
					}
					if((Integer) dadosRegistroAtendimento[28] != null){
						Quadra quadraRA = new Quadra();
						quadraRA.setId((Integer) dadosRegistroAtendimento[41]);
						quadraRA.setNumeroQuadra((Integer) dadosRegistroAtendimento[28]);
						ra.setQuadra(quadraRA);
					}
					// divisão de esgoto
					if((Integer) dadosRegistroAtendimento[24] != null){
						DivisaoEsgoto divisaoEsgoto = new DivisaoEsgoto();
						divisaoEsgoto.setId((Integer) dadosRegistroAtendimento[24]);
						divisaoEsgoto.setDescricao((String) dadosRegistroAtendimento[25]);
						ra.setDivisaoEsgoto(divisaoEsgoto);
					}
					// Colocado por Ana Maria em 29/08/2006
					// Logradouro Bairro
					if((Integer) dadosRegistroAtendimento[33] != null){
						LogradouroBairro logradouroBairro = new LogradouroBairro();
						logradouroBairro.setId((Integer) dadosRegistroAtendimento[33]);
						ra.setLogradouroBairro(logradouroBairro);
					}
					// Logradouro cep
					if((Integer) dadosRegistroAtendimento[34] != null){
						LogradouroCep logradouroCep = new LogradouroCep();
						logradouroCep.setId((Integer) dadosRegistroAtendimento[34]);
						ra.setLogradouroCep(logradouroCep);
					}

					ra.setComplementoEndereco((String) dadosRegistroAtendimento[35]);

					// Local ocorrência
					if((Integer) dadosRegistroAtendimento[36] != null){
						LocalOcorrencia localOcorrencia = new LocalOcorrencia();
						localOcorrencia.setId((Integer) dadosRegistroAtendimento[36]);
						localOcorrencia.setDescricao((String) dadosRegistroAtendimento[52]);
						ra.setLocalOcorrencia(localOcorrencia);
					}
					// Pavimento Rua
					if((Integer) dadosRegistroAtendimento[37] != null){
						PavimentoRua pavimentoRua = new PavimentoRua();
						pavimentoRua.setId((Integer) dadosRegistroAtendimento[37]);
						pavimentoRua.setDescricao((String) dadosRegistroAtendimento[53]);
						ra.setPavimentoRua(pavimentoRua);
					}
					// Pavimento calçada
					if((Integer) dadosRegistroAtendimento[38] != null){
						PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
						pavimentoCalcada.setId((Integer) dadosRegistroAtendimento[38]);
						pavimentoCalcada.setDescricao((String) dadosRegistroAtendimento[54]);
						ra.setPavimentoCalcada(pavimentoCalcada);
					}

					// Descrição Local ocorrência
					ra.setDescricaoLocalOcorrencia((String) dadosRegistroAtendimento[39]);

					// Ra Motivo Reativacao
					if(dadosRegistroAtendimento[44] != null){
						RaMotivoReativacao raMotivoReativacao = new RaMotivoReativacao();

						raMotivoReativacao.setId((Integer) dadosRegistroAtendimento[44]);
						raMotivoReativacao.setDescricao((String) dadosRegistroAtendimento[45]);

						ra.setRaMotivoReativacao(raMotivoReativacao);
					}

					// Dados da Unidade de Atendimento e a Unidade Atual
					setUnidadesRA(idRegistroAtendimento, retorno);

					// Ra ID Reiteração
					if(dadosRegistroAtendimento[74] != null){
						ra.setReiteracao((Integer) dadosRegistroAtendimento[74]);
					}

					// Ra Indicador Processo Adm Jud
					if(dadosRegistroAtendimento[77] != null){
						ra.setIndicadorProcessoAdmJud((Short) dadosRegistroAtendimento[77]);
					}

					// Ra Número do Processo na Agência
					if(dadosRegistroAtendimento[78] != null){
						ra.setNumeroProcessoAgencia((String) dadosRegistroAtendimento[78]);
					}

					retorno.setRegistroAtendimento(ra);
				}else{
					throw new ControladorException("atencao.registro_atendimento.inexistente");
				}

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}
		return retorno;
	}

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @param retorno
	 */
	private void setEnderecoOcorrenciaRA(Integer idRegistroAtendimento, ObterDadosRegistroAtendimentoHelper retorno)
					throws ControladorException{

		// endereço da ocorrência
		String enderecoOcorrencia = obterEnderecoOcorrenciaRA(idRegistroAtendimento);
		if(enderecoOcorrencia != null){
			retorno.setEnderecoOcorrencia(enderecoOcorrencia);
		}
	}

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @param retorno
	 */
	private void setUnidadesRA(Integer idRegistroAtendimento, ObterDadosRegistroAtendimentoHelper retorno) throws ControladorException{

		// Unidade de Atendimento
		UnidadeOrganizacional unidadeAtendimento = obterUnidadeAtendimentoRA(idRegistroAtendimento);
		if(unidadeAtendimento != null){
			retorno.setUnidadeAtendimento(unidadeAtendimento);
		}
		// Unidade Atual
		UnidadeOrganizacional unidadeAtual = obterUnidadeAtualRA(idRegistroAtendimento);
		if(unidadeAtual != null){
			retorno.setUnidadeAtual(unidadeAtual);
		}
	}

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 15/08/2006
	 * @param idRegistroAtendimento
	 * @param retorno
	 */
	private void setDadosRAAssociado(Integer idRegistroAtendimento, ObterDadosRegistroAtendimentoHelper retorno)
					throws ControladorException{

		// Dados do RA Associado
		ObterRAAssociadoHelper dadosRAAssociado = obterRAAssociado(idRegistroAtendimento);
		if(dadosRAAssociado.getCodigoExistenciaRAAssociado() != RegistroAtendimento.CODIGO_ASSOCIADO_SEM_RA){
			Short codigoExistenciaRAassociado = dadosRAAssociado.getCodigoExistenciaRAAssociado();
			// Código de existência de RA Associado
			retorno.setCodigoExistenciaRAAssociado(codigoExistenciaRAassociado);
			// Registro Atendimento Associado
			retorno.setRAAssociado(dadosRAAssociado.getRegistroAtendimentoAssociado());
			// Situação do RA Associado
			ObterDescricaoSituacaoRAHelper situacaoRAAssociado = obterDescricaoSituacaoRA(dadosRAAssociado
							.getRegistroAtendimentoAssociado().getId());
			retorno.setDescricaoSituacaoRAAssociado(situacaoRAAssociado.getDescricaoSituacao());

			// Títulos do número e da situação do RA Associado
			if(codigoExistenciaRAassociado.equals(RegistroAtendimento.CODIGO_ASSOCIADO_SEM_RA)){
				retorno.setTituloNumeroRAAssociado(null);
				retorno.setTituloSituacaoRAAssociado(null);
			}else if(codigoExistenciaRAassociado.equals(RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA)){
				retorno.setTituloNumeroRAAssociado(RegistroAtendimento.NUMERO_RA_REFERENCIA);
				retorno.setTituloSituacaoRAAssociado(RegistroAtendimento.SITUACAO_RA_REFERENCIA);
			}else if(codigoExistenciaRAassociado.equals(RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL)){
				retorno.setTituloNumeroRAAssociado(RegistroAtendimento.NUMERO_RA_ATUAL);
				retorno.setTituloSituacaoRAAssociado(RegistroAtendimento.SITUACAO_RA_ATUAL);
			}else if(codigoExistenciaRAassociado.equals(RegistroAtendimento.CODIGO_ASSOCIADO_RA_ANTERIOR)){
				retorno.setTituloNumeroRAAssociado(RegistroAtendimento.NUMERO_RA_ANTERIOR);
				retorno.setTituloSituacaoRAAssociado(RegistroAtendimento.SITUACAO_RA_ANTERIOR);
			}
		}
	}

	/**
	 * [UC0452] Obter Dados do Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 15/08/2006
	 * @param idRegistroAtendimento
	 * @param retorno
	 */
	private void setSolicitanteRA(Integer idRegistroAtendimento, ObterDadosRegistroAtendimentoHelper retorno)
					throws ErroRepositorioException{

		// Pesquisar dados do Solicitante do RA
		Object[] dadosRegistroAtendimentoSolicitante = repositorioRegistroAtendimento.pesquisarDadosRASolicitante(idRegistroAtendimento);

		if(dadosRegistroAtendimentoSolicitante != null){
			RegistroAtendimentoSolicitante raSolicitante = new RegistroAtendimentoSolicitante();
			raSolicitante.setID((Integer) dadosRegistroAtendimentoSolicitante[5]);
			/*
			 * Caso o principal solicitante do registro de atendimento seja um
			 * cliente, obter os dados do cliente
			 */
			if((Integer) dadosRegistroAtendimentoSolicitante[0] != null){
				Cliente cliente = new Cliente();
				cliente.setId((Integer) dadosRegistroAtendimentoSolicitante[0]);
				cliente.setNome((String) dadosRegistroAtendimentoSolicitante[1]);
				raSolicitante.setCliente(cliente);
				/*
				 * Caso o principal solicitante do registro de atendimento seja
				 * uma unidade, obter dados da unidade
				 */
			}else if((Integer) dadosRegistroAtendimentoSolicitante[2] != null){
				UnidadeOrganizacional unidade = new UnidadeOrganizacional();
				unidade.setId((Integer) dadosRegistroAtendimentoSolicitante[2]);
				unidade.setDescricao((String) dadosRegistroAtendimentoSolicitante[3]);
				raSolicitante.setUnidadeOrganizacional(unidade);
				// Caso contrário obter o nome do solicitante a partir dos dados
				// do solicitante
			}else{
				raSolicitante.setSolicitante((String) dadosRegistroAtendimentoSolicitante[4]);
			}

			if(dadosRegistroAtendimentoSolicitante[6] != null){
				raSolicitante.setClienteTipo((Short) dadosRegistroAtendimentoSolicitante[6]);
				if(raSolicitante.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.shortValue()){
					raSolicitante.setNumeroCpf((String) dadosRegistroAtendimentoSolicitante[7]);
					raSolicitante.setNumeroRG((String) dadosRegistroAtendimentoSolicitante[8]);
					raSolicitante.setOrgaoExpedidorRg((OrgaoExpedidorRg) dadosRegistroAtendimentoSolicitante[9]);
					raSolicitante.setUnidadeFederacaoRG((UnidadeFederacao) dadosRegistroAtendimentoSolicitante[10]);
				}else if(raSolicitante.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.shortValue()){
					raSolicitante.setNumeroCnpj((String) dadosRegistroAtendimentoSolicitante[11]);
				}
			}
			retorno.setSolicitante(raSolicitante);
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do endereço da ocorrência e LGCP_ID=LGCP_ID do endereço
	 * da ocorrência e STEP_ID=Id da Especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * [SB0008] Verifica existência de Registro de Atendimento Pendente para o
	 * Local da ocorrência
	 * 
	 * @author Raphael Rossiter
	 * @date 15/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroBairro, idLogradouroCep
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento verificaExistenciaRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao, Integer idLogradouroCep,
					Integer idLogradouroBairro) throws ControladorException{

		RegistroAtendimento ra = null;
		Collection colecaoRegistroAtendimento = null;

		try{
			colecaoRegistroAtendimento = repositorioRegistroAtendimento.verificaExistenciaRAPendenteLocalOcorrencia(
							idSolicitacaoTipoEspecificacao, idLogradouroBairro, idLogradouroCep);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

			ra = new RegistroAtendimento();

			Iterator raIterator = colecaoRegistroAtendimento.iterator();

			Object[] arrayRA = (Object[]) raIterator.next();

			if(arrayRA[20] != null && arrayRA[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayRA[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayRA[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayRA[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayRA[19]);
						logradouro.setNome("" + arrayRA[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição logradouro tipo
					if(arrayRA[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricaoAbreviada("" + arrayRA[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição logradouro titulo
					if(arrayRA[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricaoAbreviada("" + arrayRA[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayRA[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayRA[10]);
						cep.setCodigo((Integer) arrayRA[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				ra.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayRA[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayRA[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayRA[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayRA[3]);
						bairro.setCodigo((Integer) arrayRA[17]);
						bairro.setNome("" + arrayRA[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayRA[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayRA[5]);
						municipio.setNome("" + arrayRA[6]);

						// id da unidade federação
						if(arrayRA[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayRA[7]);
							unidadeFederacao.setSigla("" + arrayRA[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				ra.setLogradouroBairro(logradouroBairro);

				// complemento endereço
				if(arrayRA[18] != null){
					ra.setComplementoEndereco("" + arrayRA[18]);
				}
			}

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setId((Integer) arrayRA[24]);
			solicitacaoTipoEspecificacao.setDescricao((String) arrayRA[9]);
			ra.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		}

		return ra;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do endereço da ocorrência e LGCP_ID=LGCP_ID do endereço
	 * da ocorrência e STEP_ID=Id da Especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * [SB0025] Verifica Registro de Atendimento de Falta de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroCep, idLogradouroBairro
	 * @return RegistroAtendimentoPendenteLocalOcorrenciaHelper
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimentoPendenteLocalOcorrenciaHelper pesquisarRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao,
					Integer idLogradouroCep, Integer idLogradouroBairro) throws ControladorException{

		RegistroAtendimentoPendenteLocalOcorrenciaHelper retorno = null;

		Collection colecaoRegistroAtendimento = null;

		try{
			colecaoRegistroAtendimento = repositorioRegistroAtendimento.pesquisarRAPendenteLocalOcorrencia(idSolicitacaoTipoEspecificacao,
							idLogradouroCep, idLogradouroBairro);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

			retorno = new RegistroAtendimentoPendenteLocalOcorrenciaHelper();

			Collection colecaoRegistroAtendimentoRetorno = new ArrayList();

			RegistroAtendimento ra = null;

			Iterator raIterator = colecaoRegistroAtendimento.iterator();

			Object[] arrayRA = null;

			boolean primeiroRegistro = true;

			while(raIterator.hasNext()){

				ra = new RegistroAtendimento();

				arrayRA = (Object[]) raIterator.next();

				// endereço e SolicitacaoTipoEspecificacao
				if(primeiroRegistro){

					if(arrayRA[20] != null && arrayRA[21] != null){

						LogradouroCep logradouroCep = null;
						if(arrayRA[20] != null){

							logradouroCep = new LogradouroCep();
							logradouroCep.setId((Integer) arrayRA[20]);

							// id do Logradouro
							Logradouro logradouro = null;
							if(arrayRA[19] != null){
								logradouro = new Logradouro();
								logradouro.setId((Integer) arrayRA[19]);
								logradouro.setNome("" + arrayRA[0]);
							}
							LogradouroTipo logradouroTipo = null;
							// Descrição logradouro tipo
							if(arrayRA[22] != null){
								logradouroTipo = new LogradouroTipo();
								logradouroTipo.setDescricaoAbreviada("" + arrayRA[22]);
								logradouro.setLogradouroTipo(logradouroTipo);
							}
							LogradouroTitulo logradouroTitulo = null;
							// Descrição logradouro titulo
							if(arrayRA[23] != null){
								logradouroTitulo = new LogradouroTitulo();
								logradouroTitulo.setDescricaoAbreviada("" + arrayRA[23]);
								logradouro.setLogradouroTitulo(logradouroTitulo);
							}
							// id do CEP
							Cep cep = null;
							if(arrayRA[10] != null){
								cep = new Cep();
								cep.setCepId((Integer) arrayRA[10]);
								cep.setCodigo((Integer) arrayRA[16]);
							}

							logradouroCep.setLogradouro(logradouro);
							logradouroCep.setCep(cep);
						}

						ra.setLogradouroCep(logradouroCep);

						LogradouroBairro logradouroBairro = null;
						if(arrayRA[21] != null){

							logradouroBairro = new LogradouroBairro();
							logradouroBairro.setId((Integer) arrayRA[21]);

							Bairro bairro = null;
							// nome bairro
							if(arrayRA[3] != null){
								bairro = new Bairro();
								bairro.setId((Integer) arrayRA[3]);
								bairro.setCodigo((Integer) arrayRA[17]);
								bairro.setNome("" + arrayRA[4]);
							}

							Municipio municipio = null;
							// nome municipio
							if(arrayRA[5] != null){
								municipio = new Municipio();
								municipio.setId((Integer) arrayRA[5]);
								municipio.setNome("" + arrayRA[6]);

								// id da unidade federação
								if(arrayRA[7] != null){
									UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
									unidadeFederacao.setId((Integer) arrayRA[7]);
									unidadeFederacao.setSigla("" + arrayRA[8]);
									municipio.setUnidadeFederacao(unidadeFederacao);
								}

								bairro.setMunicipio(municipio);
							}

							logradouroBairro.setBairro(bairro);
						}

						ra.setLogradouroBairro(logradouroBairro);

						retorno.setEnderecoOcorrencia(ra.getEnderecoFormatadoAbreviado());
					}

					SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
					solicitacaoTipo.setId((Integer) arrayRA[25]);
					solicitacaoTipo.setDescricao("" + arrayRA[26]);

					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
					solicitacaoTipoEspecificacao.setId((Integer) arrayRA[24]);
					solicitacaoTipoEspecificacao.setDescricao((String) arrayRA[9]);

					solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

					// Carregando o endereço da ocorrência e a Especificação no
					// objeto de retorno
					retorno.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
					retorno.setEnderecoOcorrencia(ra.getEnderecoFormatadoAbreviado());

					primeiroRegistro = false;
				}

				// Id
				ra.setId((Integer) arrayRA[27]);

				// complemento endereço
				if(arrayRA[18] != null){
					ra.setComplementoEndereco("" + arrayRA[18]);
				}

				// pontoReferencia
				if(arrayRA[28] != null){
					ra.setPontoReferencia("" + arrayRA[28]);
				}

				// Data do RegistroAtendimento
				if(arrayRA[29] != null){
					ra.setRegistroAtendimento((Date) arrayRA[29]);
				}

				// Carregando todos os RAs pendentes para um mesmo local de
				// ocorrência
				colecaoRegistroAtendimentoRetorno.add(ra);
			}

			retorno.setColecaoRegistroAtendimento(colecaoRegistroAtendimentoRetorno);
		}

		return retorno;
	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * select a.svtp_id from ATENDIMENTOPUBLICO.SERVICO_TIPO A,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_ATIVIDADE B,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_MATERIAL C WHERE A.SVTP_DSSERVICOTIPO
	 * LIKE '%DESC%' AND A.SVTP_DSABREVIADO LIKE '%DESC%' AND (A.SVTP_VLSERVICO >=
	 * 000000 AND A.SVTP_VLSERVICO <= 99999) AND A.SVTP_ICPAVIMENTO = 1 OU 2 and
	 * A.SVTP_ICATUALIZACOMERCIAL = 1 OU 2 AND A.SVTP_ICTERCEIRIZADO = 1 OU 2
	 * AND A.SVTP_CDSERVICOTIPO = ("O" OR "C") AND (A.SVTP_NNTEMPOMEDIOEXECUCAO >=
	 * 0000 AND A.SVTP_NNTEMPOMEDIOEXECUCAO <= 9999) AND DBTP_ID = ID INFORMADO
	 * AND AND CRTP_ID = ID INFORMADO AND STSG_ID = ID INFORMADO AND STRF_ID =
	 * ID INFORMADO AND STPR_ID = ID INFORMADO AND A.SVTP_ID = B.SVTP_ID AND
	 * B.ATIV_ID IN (ID's INFORMADOS) AND A.SVTP_ID = C.SVTP_ID AND C.MATE_ID IN
	 * (ID's INFORMADOS)
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Collection<ServicoTipo> filtrarST(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal)
					throws ControladorException{

		Collection servicotipos = null;
		return servicotipos;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0017] - Verificar registro de Atendimento de falta de Água
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public VerificarRAFaltaAguaHelper verificarRegistroAtendimentoFaltaAgua(Integer idRegistroAtendimento, Date dataAtendimento,
					Integer idBairroArea, Integer idBairro, Integer idEspecificacao, Short indFaltaAgua, Integer indMatricula,
					String continua) throws ControladorException{

		VerificarRAFaltaAguaHelper verificarRAFaltaAguaHelper = new VerificarRAFaltaAguaHelper();

		String mensagem = null;

		try{

			// caso esteja atualizado um registro de atendimento de falta de
			// agua
			if(indFaltaAgua != null && indFaltaAgua.equals(Short.valueOf("1"))){
				// caso seja a primeira vez então continua está vazio e
				// pesquisado o programa de abastecimento e manutenção,caso não
				// seja a primeira vez então não necessario pesquisar o
				// programa de manutenção e então segue o fluxo
				if(continua == null || continua.equals("")){
					mensagem = verificarProgramacaoAbastecimentoManutencao(dataAtendimento, idBairroArea, idBairro);
				}
				// caso o programa de abastecimento e ou manutenção esteja vazio
				// então continua o fluxo, senão retorna para quem chamou
				if(mensagem == null){
					// caso esteja atualizando um registro de atendimento de
					// falta
					// de agua generalizada
					if(indMatricula != null && indMatricula.equals(SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_NAO_OBRIGATORIO)){
						Collection colecaoExibirRAFaltaAguaImovelHelper = null;
						if(idRegistroAtendimento != null && idBairroArea != null && idEspecificacao != null){
							colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairro(
											idRegistroAtendimento, idBairroArea, idEspecificacao);
						}
						// se existe registro de atendimento de falta de Água
						// generalizada em abreto para a mesma area de bairro
						if(colecaoExibirRAFaltaAguaImovelHelper != null && !colecaoExibirRAFaltaAguaImovelHelper.isEmpty()){
							String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
							Integer idMotivoEncerramento = repositorioRegistroAtendimento.pesquisarIdAtendimentoEncerramentoMotivo();

							// Alterado por Raphael Rossiter em 09/02/2007
							String desSolTipoEspecificacao = repositorioRegistroAtendimento
											.pesquisarDescricaoSolicitacaoTipoEspecificacao(idEspecificacao);

							mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao + " em aberto para a Área de bairro "
											+ nomeBairroArea + ".";
							// seta os parametros que serão recuperados pelo
							// action que chamou esse método
							verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.ENCERRAR_REGISTRO_ATENDIMENTO);
							verificarRAFaltaAguaHelper.setMensagem(mensagem);
							ExibirRAFaltaAguaImovelHelper exibirRAFaltaAguaImovelHelper = (ExibirRAFaltaAguaImovelHelper) Util
											.retonarObjetoDeColecao(colecaoExibirRAFaltaAguaImovelHelper);
							verificarRAFaltaAguaHelper.setIdRAReferencia(exibirRAFaltaAguaImovelHelper.getIdRA());
							verificarRAFaltaAguaHelper.setIdMotivoEncerramento(idMotivoEncerramento);
						}
					}
					// caso esteja atualizando um registro de atendimento de
					// falta
					// de agua no Imóvel
					if(indMatricula != null && indMatricula.equals(SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_OBRIGATORIO)){

						Integer idRAReferencia = null;
						if(idBairroArea != null){
							idRAReferencia = repositorioRegistroAtendimento.pesquisarRAAreaBairroFaltaAguaImovel(idBairroArea);
						}
						// se existe registro de atendimento de falta de Água
						// generalizada em aberto para a mesma area de bairro
						if(idRAReferencia != null){
							String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
							Integer idMotivoEncerramento = repositorioRegistroAtendimento.pesquisarIdAtendimentoEncerramentoMotivo();
							String desSolTipoEspecificacao = repositorioRegistroAtendimento.descricaoSolTipoEspecFaltaAguaGeneralizada();
							mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao + " em aberto para a Área de bairro "
											+ nomeBairroArea + ".";
							// seta os parametros que serão recuperados pelo
							// action que chamou esse método
							verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.ENCERRAR_REGISTRO_ATENDIMENTO);
							verificarRAFaltaAguaHelper.setMensagem(mensagem);
							verificarRAFaltaAguaHelper.setIdRAReferencia(idRAReferencia);
							verificarRAFaltaAguaHelper.setIdMotivoEncerramento(idMotivoEncerramento);
						}else{
							Collection colecaoExibirRAFaltaAguaImovelHelper = null;
							if(idRegistroAtendimento != null && idBairroArea != null && idEspecificacao != null){
								colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairro(
												idRegistroAtendimento, idBairroArea, idEspecificacao);
							}
							// caso exista registro de atendimento de falta de
							// Água no Imóvel em aberto para o mesmo bairro Área
							if(colecaoExibirRAFaltaAguaImovelHelper != null && !colecaoExibirRAFaltaAguaImovelHelper.isEmpty()){
								String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
								String desSolTipoEspecificacao = repositorioRegistroAtendimento
												.descricaoSolTipoEspecAguaGeneralizada(idEspecificacao);
								mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao
												+ " em aberto para a Área de bairro " + nomeBairroArea + ".";
								// seta os parametros que serão recuperados pelo
								// action que chamou esse método
								verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.EXIBIR_RA_FALTA_AGUA_IMOVEL);
								verificarRAFaltaAguaHelper.setMensagem(mensagem);

								// Abrir registro de atendimento de falta de
								// Água generalizada passando a solicitação e a
								// especificação pré determinadas
								// seta os parametros que serão recuperados pelo
								// action que chamou esse método
								verificarRAFaltaAguaHelper.setCasoUso2(VerificarRAFaltaAguaHelper.INSERIR_REGISTRO_ATENDIMENTO);

							}
						}
					}
				}else{
					// seta os parametros que serão recuperados pelo
					// action que chamou esse método
					verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.CONSULTAR_PROGRAMACAO);
					verificarRAFaltaAguaHelper.setMensagem(mensagem);
				}
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return verificarRAFaltaAguaHelper;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0017] - Verificar registro de Atendimento de falta de Água
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @throws ControladorException
	 */
	public VerificarRAFaltaAguaHelper verificarRegistroAtendimentoFaltaAguaInserir(Date dataAtendimento, Integer idBairroArea,
					Integer idBairro, Integer idEspecificacao, Short indFaltaAgua, Integer indMatricula, String continua)
					throws ControladorException{

		VerificarRAFaltaAguaHelper verificarRAFaltaAguaHelper = new VerificarRAFaltaAguaHelper();

		String mensagem = null;

		try{

			// caso esteja atualizado um registro de atendimento de falta de
			// agua
			if(indFaltaAgua != null && indFaltaAgua.equals(Short.valueOf("1"))){
				// caso seja a primeira vez então continua está vazio e é
				// pesquisado o programa de abastecimento e manutenção,caso não
				// seja a primeira vez então não necessario pesquisar o
				// programa de manutenção e então segue o fluxo
				if(continua == null || continua.equals("")){
					mensagem = verificarProgramacaoAbastecimentoManutencao(dataAtendimento, idBairroArea, idBairro);
				}
				// caso o programa de abastecimento e ou manutenção esteja vazio
				// então continua o fluxo, senão retorna para quem chamou
				if(mensagem == null){
					// caso esteja atualizando um registro de atendimento de
					// falta
					// de agua generalizada
					if(indMatricula != null && indMatricula.equals(SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_NAO_OBRIGATORIO)){
						Collection colecaoExibirRAFaltaAguaImovelHelper = null;
						if(idBairroArea != null && idEspecificacao != null){
							colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairroInserir(
											idBairroArea, idEspecificacao);
						}
						// se existe registro de atendimento de falta de Água
						// generalizada em abreto para a mesma area de bairro
						if(colecaoExibirRAFaltaAguaImovelHelper != null && !colecaoExibirRAFaltaAguaImovelHelper.isEmpty()){
							String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);

							// Alterado por Raphael Rossiter em 09/02/2007
							String desSolTipoEspecificacao = repositorioRegistroAtendimento
											.pesquisarDescricaoSolicitacaoTipoEspecificacao(idEspecificacao);

							mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao + " em aberto para a Área de bairro "
											+ nomeBairroArea + ".";
							// seta os parametros que serão recuperados pelo
							// action que chamou esse método
							verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.EXIBIR_ADICIONAR_SOLICITANTE);
							verificarRAFaltaAguaHelper.setMensagem(mensagem);

							ExibirRAFaltaAguaImovelHelper exibirRAFaltaAguaImovelHelper = (ExibirRAFaltaAguaImovelHelper) Util
											.retonarObjetoDeColecao(colecaoExibirRAFaltaAguaImovelHelper);

							verificarRAFaltaAguaHelper.setIdRAReferencia(exibirRAFaltaAguaImovelHelper.getIdRA());
						}
					}
					// caso esteja atualizando um registro de atendimento de
					// falta
					// de agua no Imóvel
					if(indMatricula != null && indMatricula.equals(SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_OBRIGATORIO)){

						Integer idRAReferencia = null;
						if(idBairroArea != null){
							idRAReferencia = repositorioRegistroAtendimento.pesquisarRAAreaBairroFaltaAguaImovel(idBairroArea);
						}
						// se existe registro de atendimento de falta de Água
						// generalizada em aberto para a mesma area de bairro
						if(idRAReferencia != null){
							String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
							String desSolTipoEspecificacao = repositorioRegistroAtendimento.descricaoSolTipoEspecFaltaAguaGeneralizada();
							mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao + " em aberto para a Área de bairro "
											+ nomeBairroArea + ".";
							// seta os parametros que serão recuperados pelo
							// action que chamou esse método
							verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.EXIBIR_ADICIONAR_SOLICITANTE);
							verificarRAFaltaAguaHelper.setMensagem(mensagem);

							verificarRAFaltaAguaHelper.setIdRAReferencia(idRAReferencia);

						}else{
							Collection colecaoExibirRAFaltaAguaImovelHelper = null;
							if(idBairroArea != null && idEspecificacao != null){
								colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairroInserir(
												idBairroArea, idEspecificacao);
							}
							// caso exista registro de atendimento de falta de
							// Água no Imóvel em aberto para o mesmo bairro Área
							if(colecaoExibirRAFaltaAguaImovelHelper != null && !colecaoExibirRAFaltaAguaImovelHelper.isEmpty()){
								String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
								String desSolTipoEspecificacao = repositorioRegistroAtendimento
												.descricaoSolTipoEspecAguaGeneralizada(idEspecificacao);
								mensagem = "Existe Registro de Atendimento de " + desSolTipoEspecificacao
												+ " em aberto para a Área de bairro " + nomeBairroArea + ".";
								// seta os parametros que serão recuperados pelo
								// action que chamou esse método
								verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.EXIBIR_RA_FALTA_AGUA_IMOVEL);
								verificarRAFaltaAguaHelper.setMensagem(mensagem);

								// Abrir registro de atendimento de falta de
								// Água generalizada passando a solicitação e a
								// especificação pré determinadas
								// seta os parametros que serão recuperados pelo
								// action que chamou esse método
								verificarRAFaltaAguaHelper.setCasoUso2(VerificarRAFaltaAguaHelper.INSERIR_REGISTRO_ATENDIMENTO);

							}
						}
					}
				}else{
					// seta os parametros que serão recuperados pelo
					// action que chamou esse método
					verificarRAFaltaAguaHelper.setCasoUso1(VerificarRAFaltaAguaHelper.CONSULTAR_PROGRAMACAO);
					verificarRAFaltaAguaHelper.setMensagem(mensagem);
				}
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return verificarRAFaltaAguaHelper;

	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0018] - Verificar Programação de Abastecimento e/ou de Manutenção
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public String verificarProgramacaoAbastecimentoManutencao(Date dataAtendimento, Integer idBairroArea, Integer idBairro)
					throws ControladorException{

		String mensagemRetorno = null;

		try{
			Integer idAbastecimentoProgramacao = null;
			Integer idManutencaoProgramacao = null;
			if(dataAtendimento != null && idBairroArea != null && idBairro != null){
				idAbastecimentoProgramacao = repositorioRegistroAtendimento.verificarOcorrenciaAbastecimentoProgramacao(dataAtendimento,
								idBairroArea, idBairro);
				idManutencaoProgramacao = repositorioRegistroAtendimento.verificarOcorrenciaManutencaoProgramacao(dataAtendimento,
								idBairroArea, idBairro);
			}

			// caso exista ocorrência na tabela de manutencaoProgramacao e não exista
			// ocorrência na tabela abastecimentoProgramacao
			if(idAbastecimentoProgramacao == null && idManutencaoProgramacao != null){
				String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
				String dataAtendimentoString = Util.formatarData(dataAtendimento);
				mensagemRetorno = "Existe manutenção prevista no dia " + dataAtendimentoString + " para a Área de bairro " + nomeBairroArea
								+ ".";
			}

			// caso não exista ocorrência na tabela de manutencaoProgramacao e exista
			// ocorrência na tabela abastecimentoProgramacao
			if(idAbastecimentoProgramacao != null && idManutencaoProgramacao == null && mensagemRetorno == null){
				String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
				String dataAtendimentoString = Util.formatarData(dataAtendimento);
				mensagemRetorno = "Existe programação de abastecimento no dia " + dataAtendimentoString + " para a Área de bairro "
								+ nomeBairroArea + ".";
			}

			// caso exista ocorrência na tabela de abastecimentoProgramacao e exista
			// ocorrência na tabela manutencaoProgramacao
			if(idAbastecimentoProgramacao != null && idManutencaoProgramacao != null && mensagemRetorno == null){
				String nomeBairroArea = repositorioRegistroAtendimento.pesquisarNomeBairroArea(idBairroArea);
				String dataAtendimentoString = Util.formatarData(dataAtendimento);
				mensagemRetorno = "Existe programação de abastecimento no dia " + dataAtendimentoString + " para a Área de bairro "
								+ nomeBairroArea + "." + " Há manutenção prevista neste dia para essa Área de bairro.";
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
		return mensagemRetorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0019] - Exibe Registros de Atendimentos de falta de Água no Imóvel
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idRA
	 * @throws ControladorException
	 */
	public RAFaltaAguaPendenteHelper carregarObjetoRAFaltaAguaPendente(Integer idRegistroAtendimento, Integer idBairroArea,
					Integer idEspecificacao) throws ControladorException{

		RAFaltaAguaPendenteHelper rAFaltaAguaPendenteHelper = new RAFaltaAguaPendenteHelper();

		try{

			Collection colecaoExibirRAFaltaAguaImovelHelper = null;

			// Atualizar RA
			if(idRegistroAtendimento != null){

				colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairro(idRegistroAtendimento,
								idBairroArea, idEspecificacao);

				// [SB0019] - Exibir Registros de Atendimento de
				// Falta de Água no Imóvel da Área do Bairro
				Object[] parmsFaltaAguaAreaBairro = repositorioRegistroAtendimento.pesquisarParmsRAFaltaAguaImovel(idRegistroAtendimento);
				if(parmsFaltaAguaAreaBairro != null){
					// id da solicitação tipo
					if(parmsFaltaAguaAreaBairro[0] != null){
						rAFaltaAguaPendenteHelper.setIdSolicitacaoTipo((Integer) parmsFaltaAguaAreaBairro[0]);
					}
					// descrição da solicitação tipo
					if(parmsFaltaAguaAreaBairro[1] != null){
						rAFaltaAguaPendenteHelper.setDescricaoSolicitacaoTipo((String) parmsFaltaAguaAreaBairro[1]);
					}
					// id da solicitação tipo especificação
					if(parmsFaltaAguaAreaBairro[2] != null){
						rAFaltaAguaPendenteHelper.setIdSolicitacaoTipoEspecificacao((Integer) parmsFaltaAguaAreaBairro[2]);
					}
					// descrição da solicitação tipo
					// especificação
					if(parmsFaltaAguaAreaBairro[3] != null){
						rAFaltaAguaPendenteHelper.setDescricaoSolicitacaoTipoEspecificacao((String) parmsFaltaAguaAreaBairro[3]);
					}
					// codigo do bairro
					if(parmsFaltaAguaAreaBairro[4] != null){
						rAFaltaAguaPendenteHelper.setCodigoBairro((Integer) parmsFaltaAguaAreaBairro[4]);
					}
					// nome bairro
					if(parmsFaltaAguaAreaBairro[5] != null){
						rAFaltaAguaPendenteHelper.setNomeBairro((String) parmsFaltaAguaAreaBairro[5]);
					}
					// id do bairro da Área
					if(parmsFaltaAguaAreaBairro[6] != null){
						rAFaltaAguaPendenteHelper.setIdBairroArea((Integer) parmsFaltaAguaAreaBairro[6]);
					}
					// nome bairro da Área
					if(parmsFaltaAguaAreaBairro[7] != null){
						rAFaltaAguaPendenteHelper.setNomeBairroArea((String) parmsFaltaAguaAreaBairro[7]);
					}
				}
			}
			// Inserir RA
			else{

				colecaoExibirRAFaltaAguaImovelHelper = repositorioRegistroAtendimento.pesquisarRAAreaBairroInserir(idBairroArea,
								idEspecificacao);
			}

			// seta a coleção de falta de agua para a mesma
			// Área de bairro
			rAFaltaAguaPendenteHelper.setColecaoExibirRAFaltaAguaHelper(colecaoExibirRAFaltaAguaImovelHelper);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
		return rAFaltaAguaPendenteHelper;
	}

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Validar Tramitação
	 * [FS0001] Verificar se o RA está cancelado ou bloqueado. [FS0002]
	 * Verificar situações das OS(ordem de Serviço) associadas ao RA [FS0003]
	 * Verificar se o tipo de solicitação Tarifa Social [FS0008] Validar Unidade
	 * de Destino
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param tramite
	 * @throws ControladorException
	 */
	public void validarTramitacao(Tramite tramite, Usuario usuario) throws ControladorException{

		if(tramite != null){

			boolean possuiOSTerceirizado = false;

			// [FS0001]
			if(Short.valueOf(tramite.getRegistroAtendimento().getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO
							.intValue()
							|| Short.valueOf(tramite.getRegistroAtendimento().getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_BLOQUEADO
											.intValue()){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_ra_situacao_bloqueado_encerrado");
			}

			// [FS0002]
			Collection<OrdemServico> colecaoOrdemServico = obterOSRA(tramite.getRegistroAtendimento().getId());

			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

				for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

					OrdemServico element = (OrdemServico) iter.next();

					if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO.intValue()){

						// Caso 1
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_os_em_andamento");

					}else if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO.intValue()){

						// Caso 2
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_os_referencia", null, element.getOsReferencia().getId() + "");

					}else if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_PENDENTE.intValue()){

						// Caso 3
						if(getControladorOrdemServico().verificarExistenciaOSProgramada(element.getId())){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.tramitar_ra_os_programada");
						}

						// [FS0008 Caso 4]
						if((new Short(element.getServicoTipo().getIndicadorTerceirizado())).intValue() == 1){
							possuiOSTerceirizado = true;
						}
					}
				}
			}

			// [UC0418] - Obter Unidade Atual do RA
			UnidadeOrganizacional unidadeAtual = obterUnidadeAtualRA(tramite.getRegistroAtendimento().getId());

			// [UC0419] - Obter Indicador de Autorização para Manutenção do RA
			Short indicadorAutorizacao = this.obterIndicadorAutorizacaoManutencaoRA(unidadeAtual.getId(), usuario.getId());

			if(ConstantesSistema.NAO.equals(indicadorAutorizacao)){

				// [FS0003]Verificar se o tipo de solicitação tarifa
				// social
				String indicadorLiberarTramitacaoRA = ParametroAtendimentoPublico.P_INDICADOR_LIBERAR_TRAMITACAO_RA.executar();
				/*
				 * Caso o trâmite de RA não esteja liberada para qualquer usuário da empresa
				 * (PASI_VLPARAMETRO com o valor 2 (NÃO) na tabela PARAMETRO_SISTEMA com
				 * PASI_CDPARAMETRO="P_INDICADOR_LIBERAR_TRAMITACAO_RA"):
				 */
				if(!Util.isVazioOuBranco(indicadorLiberarTramitacaoRA)
								&& indicadorLiberarTramitacaoRA.equals(ConstantesSistema.NAO.toString())){
					// Caso o RA não seja de Tarifa Social OU a unidade do usuário não seja da área
					// da
					// tarifa social
					Boolean raTarifaSocial = tramite.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getSolicitacaoTipo()
									.getIndicadorTarifaSocial() == ConstantesSistema.SIM.shortValue();

					Boolean unidadeAreaTarifaSocial = tramite.getUsuarioRegistro().getUnidadeOrganizacional().getIndicadorTarifaSocial() == ConstantesSistema.SIM
									.shortValue();

					if(!raTarifaSocial || !unidadeAreaTarifaSocial){

						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_tarifa_social");

					}
				}
			}

			// [FS0006] Validar Data
			// [FS0007] Validar Hora
			Date dataCorrente = new Date();
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(tramite.getDataTramite(), dataCorrente);

			if(qtdeDias < 0){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_ra_data_maior_que_atual", null, Util.formatarData(dataCorrente));

			}else if(Util.datasIguais(tramite.getDataTramite(), dataCorrente)){
				if(Util.compararHoraMinuto(Util.formatarHoraSemData(tramite.getDataTramite()), Util.formatarHoraSemData(dataCorrente), ">")){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.tramitar_ra_hora_maior_que_atual", null, Util.formatarHoraSemData(dataCorrente));
				}
			}

			// [FS0008]
			// Caso 1
			if(tramite.getUnidadeOrganizacionalOrigem().getId().intValue() == tramite.getUnidadeOrganizacionalDestino().getId().intValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_ra_unidade_origem_destino_iguais");
			}

			// Caso 2
			if((new Short(tramite.getUnidadeOrganizacionalDestino().getIndicadorTramite())).intValue() == 2){
				if((new Short(tramite.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getSolicitacaoTipo()
								.getIndicadorTarifaSocial())).intValue() == 2){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.tramitar_ra_unidade_destino_tramite");
				}
			}

			if(tramite.getUnidadeOrganizacionalDestino().getUnidadeTipo() != null
							&& tramite.getUnidadeOrganizacionalDestino().getUnidadeTipo().getCodigoTipo() != null){

				if(tramite.getUnidadeOrganizacionalDestino().getUnidadeTipo().getCodigoTipo().equals(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)){

					// Caso 3
					if(tramite.getUnidadeOrganizacionalOrigem().getUnidadeTipo() == null
									|| tramite.getUnidadeOrganizacionalOrigem().getUnidadeTipo().getCodigoTipo() == null
									|| !tramite.getUnidadeOrganizacionalOrigem().getUnidadeTipo().getCodigoTipo()
													.equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA)){

						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_unidade_centralizadora");
					}
					// Caso 4
					if(!possuiOSTerceirizado){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_os_possui_terceiros");
					}
				}
				// Caso 5
				if(tramite.getUnidadeOrganizacionalDestino().getUnidadeTipo().getCodigoTipo()
								.equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA)
								&& (tramite.getUnidadeOrganizacionalOrigem().getUnidadeTipo() != null && !tramite
												.getUnidadeOrganizacionalOrigem().getUnidadeTipo().getCodigoTipo()
												.equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA))){
					Integer unidadeCentralizadora = 0;
					try{
						unidadeCentralizadora = repositorioRegistroAtendimento.pesquisarUnidadeCentralizadoraRa(tramite
										.getRegistroAtendimento().getId());
					}catch(ErroRepositorioException ex){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ilex){

						}
						ex.printStackTrace();
						throw new ControladorException("erro.sistema", ex);
					}
					if(unidadeCentralizadora == null
									|| unidadeCentralizadora != tramite.getUnidadeOrganizacionalDestino().getId().intValue()){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.tramitar_ra_unidade_origem_central");
					}
				}
			}
		}
	}

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Tramitar
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param tramite
	 * @param dataConcorrente
	 * @throws ControladorException
	 */
	public void tramitar(Tramite tramite, Date dataConcorrente) throws ControladorException{

		Tramite ultimoTramitebase = this.pesquisarUltimaDataTramite(tramite.getRegistroAtendimento().getId());

		java.sql.Timestamp dataTramiteTramite = new java.sql.Timestamp(tramite.getDataTramite().getTime());

		if(ultimoTramitebase != null && ultimoTramitebase.getDataTramite().compareTo(dataTramiteTramite) == 0){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tramite_existente_dia_hora_informada");
		}

		try{
			Date dataAtual = repositorioRegistroAtendimento.verificarConcorrenciaRA(tramite.getRegistroAtendimento().getId());

			if(dataConcorrente != null){
				if((dataAtual.after(dataConcorrente))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.tramitar_ra_usuario_concorrente");
				}
			}

			getControladorUtil().inserir(tramite);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a matrícula do Imóvel da Aba Dados do Local da ocorrência tenha sido
	 * informada e o Cliente informado não seja um cliente do Imóvel (inexiste
	 * ocorrência na tabela CLIENTE_IMOVEL com CLIE_ID=Id do cliente e
	 * IMOV_ID=matrícula do Imóvel e CLIM_DTRELACAOFIM com o valor nulo).
	 * [FS0027] Verificar informação do Imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 *            ,
	 *            idImovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente verificarInformacaoImovel(Integer idCliente, Integer idImovel, boolean levantarException) throws ControladorException{

		Cliente retorno = null;

		if(idImovel != null){
			ClienteImovel clienteImovel = this.getControladorCliente().pesquisarClienteImovel(idCliente, idImovel);

			if(clienteImovel != null){
				retorno = clienteImovel.getCliente();
			}else{
				throw new ControladorException("atencao.cliente_informado_nao_corresponde_imovel", null, idImovel.toString());
			}
		}else{
			retorno = this.getControladorCliente().pesquisarCliente(idCliente);

			if(levantarException){
				throw new ControladorException("atencao.cliente.inexistente", null, idImovel.toString());
			}
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado).
	 * [FS0012] Verificar existência do cliente solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaClienteSolicitante(Integer idRegistroAtendimento, Integer idCliente) throws ControladorException{

		Integer qtdRegistros = null;

		try{
			qtdRegistros = this.repositorioRegistroAtendimento.verificarExistenciaClienteSolicitante(idRegistroAtendimento, idCliente);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(qtdRegistros != null && qtdRegistros > 0){
			throw new ControladorException("atencao.cliente_solicitante_ja_informado_registro_atendimento", null, idCliente.toString(),
							idRegistroAtendimento.toString());
		}

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada).
	 * [FS0026] Verificar existência da unidade solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadeSolicitante(Integer idRegistroAtendimento, Integer idUnidade) throws ControladorException{

		Integer qtdRegistros = null;

		try{
			qtdRegistros = this.repositorioRegistroAtendimento.verificarExistenciaUnidadeSolicitante(idRegistroAtendimento, idUnidade);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(qtdRegistros != null && qtdRegistros > 0){
			throw new ControladorException("atencao.unidade_solicitante_ja_informado_registro_atendimento", null, idUnidade.toString(),
							idRegistroAtendimento.toString());
		}

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento e [UC0408] Atualizar Registro de
	 * Atendimento
	 * [FS0040] Verificar preenchimento campos 2 ABA
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void validarCamposObrigatoriosRA_2ABA(String idImovel, String pontoReferencia, String idMunicipio, String descricaoMunicipio,
					String cdBairro, String descricaoBairro, String idAreaBairro, String idlocalidade, String descricaoLocalidade,
					String cdSetorComercial, String descricaoSetorComercial, String numeroQuadra, String idDivisaoEsgoto, String idUnidade,
					String descricaoUnidade, String idLocalOcorrencia, String idPavimentoRua, String idPavimentoCalcada,
					String descricaoLocalOcorrencia, String imovelObrigatorio, String pavimentoRuaObrigatorio,
					String pavimentoCalcadaObrigatorio, String solicitacaoTipoRelativoFaltaAgua, String solicitacaoTipoRelativoAreaEsgoto,
					String desabilitarMunicipioBairro, String indRuaLocalOcorrencia, String indCalcadaLocalOcorrencia,
					Integer idEspecificacao, Integer idRAAtualizacao, Collection colecaoEndereco, String idCliente, String idUnidadeDestino)
					throws ControladorException{

		// Valida Se a Unidade de destino é obrigatoria
		// Verificação de Unidade Destino obrigatório
		String parametro = ParametroAtendimentoPublico.P_EXIBIR_UNIDADE_ATENDIMENTO.executar();

		if(parametro != null && parametro.equalsIgnoreCase(ConstantesSistema.NAO.toString())){
			if(idUnidadeDestino == null || idUnidadeDestino.equalsIgnoreCase("")){
				throw new ControladorException("atencao.required", null, "Unidade Destino");
			}
		}

		boolean camposLO = this.validarCamposHabilitadosLocalOcorrencia(idImovel, pontoReferencia, idMunicipio, cdBairro, idAreaBairro,
						idlocalidade, cdSetorComercial, numeroQuadra, idDivisaoEsgoto, idUnidade, idLocalOcorrencia, idPavimentoRua,
						idPavimentoCalcada);

		SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.intValue() == sistemaParametros.getParmId().intValue()){
			if(!Util.isVazioOuBranco(idlocalidade) && !Util.isVazioOuBranco(idMunicipio)){
				if(!this.existeVinculoLocalidadeMunicipio(Integer.valueOf(idlocalidade), Integer.valueOf(idMunicipio))){
					throw new ControladorException("atencao.localidade.nao.esta.municipio.informado");
				}
			}
		}

		if(camposLO || (descricaoLocalOcorrencia != null && !descricaoLocalOcorrencia.equals(""))){
			if(camposLO){
				// se o indicador do Imóvel for diferente de nulo e o Imóvel estiver nulo
				if(imovelObrigatorio != null && imovelObrigatorio.equals("SIM")){
					if(idImovel == null || idImovel.equals("")){
						throw new ControladorException("atencao.required", null, "Imóvel");
					}
				}

				// Verificação de endereço obrigatório
				if((idImovel == null || idImovel.equalsIgnoreCase(""))
								&& (solicitacaoTipoRelativoFaltaAgua != null && solicitacaoTipoRelativoFaltaAgua.equals("NAO"))
								&& (colecaoEndereco == null || colecaoEndereco.isEmpty())){
					throw new ControladorException("atencao.required", null, "Endereço");
				}

				if(solicitacaoTipoRelativoFaltaAgua != null && solicitacaoTipoRelativoFaltaAgua.equals("SIM")){
					if(desabilitarMunicipioBairro == null){
						if(idMunicipio == null || idMunicipio.equals("")){
							throw new ControladorException("atencao.required", null, "Município");
						}
						if(cdBairro == null || cdBairro.equals("")){
							throw new ControladorException("atencao.required", null, "Município");
						}
					}

					if(idAreaBairro == null || idAreaBairro.equals("")){
						throw new ControladorException("atencao.required", null, "Área do Bairro");
					}
				}
				// valida a localidade o setor comercial e a divisão de esgoto
				if(idImovel == null || idImovel.equals("")){
					if(idlocalidade == null || idlocalidade.equals("")){
						throw new ControladorException("atencao.required", null, "Localidade");
					}
					// valida o setor comercial
					if(cdSetorComercial == null || cdSetorComercial.equals("")){
						if(numeroQuadra != null && !numeroQuadra.equals("")){
							throw new ControladorException("atencao.required", null, "Setor Comercial");
						}
					}
					if(solicitacaoTipoRelativoAreaEsgoto != null && solicitacaoTipoRelativoAreaEsgoto.equals("SIM")){
						if(idDivisaoEsgoto == null || idDivisaoEsgoto.equals("")){
							throw new ControladorException("atencao.required", null, "Divisão de Esgoto");
						}
					}
				}
				if((pavimentoRuaObrigatorio != null && pavimentoRuaObrigatorio.equals("SIM"))
								|| (indRuaLocalOcorrencia != null && indRuaLocalOcorrencia.equals("1"))){
					if(idPavimentoRua == null || idPavimentoRua.equals("")
									|| idPavimentoRua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
						throw new ControladorException("atencao.required", null, "Pavimento da Rua");
					}
				}
				if((pavimentoCalcadaObrigatorio != null && pavimentoCalcadaObrigatorio.equals("SIM"))
								|| (indCalcadaLocalOcorrencia != null && indCalcadaLocalOcorrencia.equals("1"))){
					if(idPavimentoCalcada == null || idPavimentoCalcada.equals("")
									|| idPavimentoCalcada.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
						throw new ControladorException("atencao.required", null, "Pavimento da Calçada");
					}
				}
			}
		}else{
			throw new ControladorException("atencao.informar_descricao_ou_dados_local_ocorrencia");
		}

		/*
		 * Validações para o imóvel - Colocado por Raphael Rossiter em 20/02/2007. Caso a
		 * especificação venha com valor nulo, não será necessário
		 * realizar o [SB0020] – Verifica Situação do Imóvel e Especificação (O método foi invocado
		 * pelo Manter RA)
		 */
		if(idImovel != null && !idImovel.equals("")){

			Imovel imovelSoId = this.getControladorImovel().pesquisarImovelRegistroAtendimento(Integer.valueOf(idImovel));

			// Inserir RA
			if(idRAAtualizacao == null){

				// [SB0020] – Verifica Situação do Imóvel e Especificação
				this.verificarSituacaoImovelEspecificacao(imovelSoId, idEspecificacao);

				// [SB0032] – Verifica se o imóvel informado tem débito
				// this.verificarImovelComDebitos(idEspecificacao, imovelSoId.getId());

				// Integer clienteID = null;
				//
				// if(!Util.isVazioOuBranco(idCliente)){
				// clienteID = Integer.valueOf(idCliente);
				// }

				this.verificarDebitosImovelCliente(idEspecificacao, imovelSoId.getId(), null);
			}
			// Manter RA
			else{

				/*
				 * Caso o usuário tenha modificado a matrícula do imóvel, será necessário invocar o
				 * [SB0032] – Verifica se o imóvel informado tem
				 * débito
				 */
				if(idEspecificacao != null){

					/*
					 * parametrosRA[7] = idSolicitacaoTipoEspecificacao parametrosRA[10] = idImovel
					 */
					Object[] parametrosRA = this.pesquisarParmsRegistroAtendimento(idRAAtualizacao);

					if(parametrosRA != null && parametrosRA[10] != null && parametrosRA[10] != imovelSoId.getId()){

						// [SB0032] – Verifica se o imóvel informado tem débito
						// this.verificarImovelComDebitos(idEspecificacao, imovelSoId.getId());

						// Integer clienteID = null;
						//
						// if(!Util.isVazioOuBranco(idCliente)){
						// clienteID = Integer.valueOf(idCliente);
						// }

						this.verificarDebitosImovelCliente(idEspecificacao, imovelSoId.getId(), null);
					}
				}
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de identificação do solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public boolean validarCamposHabilitadosLocalOcorrencia(String idImovel, String pontoReferencia, String idMunicipio, String cdBairro,
					String idAreaBairro, String idlocalidade, String cdSetorComercial, String numeroQuadra, String idDivisaoEsgoto,
					String idUnidade, String idLocalOcorrencia, String idPavimentoRua, String idPavimentoCalcada){

		boolean camposLO = false;

		String[] dadoslocalOcorrencia = {idImovel, pontoReferencia, idMunicipio, cdBairro, idAreaBairro, idlocalidade, cdSetorComercial, numeroQuadra, idDivisaoEsgoto, idUnidade, idLocalOcorrencia, idPavimentoRua, idPavimentoCalcada};

		for(int i = 0; i < dadoslocalOcorrencia.length; i++){
			if(dadoslocalOcorrencia[i] != null && !dadoslocalOcorrencia[i].equals("")
							&& !dadoslocalOcorrencia[i].equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				camposLO = true;
				break;
			}
		}

		return camposLO;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de identificação do
	 * solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void verificaDadosSolicitante(Integer idCliente, Integer idUnidadeSolicitante, Integer idFuncionario, String nomeSolicitante,
					Collection colecaoEndereco, Collection colecaoFone, Short indicadorClienteEspecificacao, Integer idImovel,
					Integer idRegistroAtendimento, Integer idEspecificacao) throws ControladorException{

		// Validação de campos requeridos
		if(idCliente == null
						&& idUnidadeSolicitante == null
						&& idFuncionario == null
						&& nomeSolicitante == null
						&& (idEspecificacao == null || (idEspecificacao != null && this
										.clienteObrigatorioInserirRegistroAtendimento(idEspecificacao)))){
			throw new ControladorException("atencao.dados_solicitante_nao_informado");
		}

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		if(idEspecificacao != null){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
							idEspecificacao));

			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));
		}else{
			if(idRegistroAtendimento != null){
				FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
				filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idRegistroAtendimento));
				filtroRegistroAtendimento
								.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);

				RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
								filtroRegistroAtendimento, RegistroAtendimento.class.getName()));

				// [FS0027] Verificar informação do Imóvel
				if(registroAtendimento != null){
					solicitacaoTipoEspecificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();
				}
			}
		}

		if(indicadorClienteEspecificacao != null && indicadorClienteEspecificacao.equals(ConstantesSistema.INDICADOR_USO_ATIVO)
						&& idCliente == null){
			throw new ControladorException("atencao.required", null, "Cliente");
		}

		if(idFuncionario != null && idUnidadeSolicitante == null){
			throw new ControladorException("atencao.required", null, "Unidade Solicitante");
		}

		if(idFuncionario == null && idUnidadeSolicitante != null){
			throw new ControladorException("atencao.required", null, "Funcionário Responsável");
		}

		if(idCliente != null && (colecaoEndereco == null || colecaoEndereco.isEmpty())){
			throw new ControladorException("atencao.required", null, "endereço");
		}

		if(idCliente != null){

			if(idRegistroAtendimento != null){
				// [FS0012] Verificar existência do cliente solicitante
				this.verificarExistenciaClienteSolicitante(idRegistroAtendimento, idCliente);
			}

			// [FS0027] Verificar informação do Imóvel
			if((idImovel != null)
							&& (solicitacaoTipoEspecificacao == null || solicitacaoTipoEspecificacao.getIndicadorCliente().equals(
											ConstantesSistema.SIM))){
				this.verificarInformacaoImovel(idCliente, idImovel, true);
			}else{
				Cliente cliente = null;

				if(idCliente != null){
					cliente = getControladorCliente().pesquisarClienteDigitado(idCliente);
				}

				if(cliente == null){
					throw new ActionServletException("atencao.cliente.inexistente", null, idImovel.toString());
				}
			}
		}

		if(idUnidadeSolicitante != null){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeSolicitante));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){

				throw new ControladorException("atencao.label_inexistente", null, "Unidade Solicitante");

			}else if(idRegistroAtendimento != null){

				// [FS0026] Verificar existência da unidade solicitante
				this.verificarExistenciaUnidadeSolicitante(idRegistroAtendimento, idUnidadeSolicitante);
			}
		}

		if(idFuncionario != null){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, idUnidadeSolicitante));

			Collection colecaoFuncionario = this.getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				throw new ControladorException("atencao.label_inexistente", null, "Funcionário Responsável");

			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0027] Inclui Solicitante do Registro de Atendimento
	 * (REGISTRO_ATENDIMENTO_SOLICITANTE)
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public boolean clienteObrigatorioInserirRegistroAtendimento(Integer idEspecificacao) throws ControladorException{

		boolean retorno = true;

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, idEspecificacao));

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
						FiltroSolicitacaoTipoEspecificacao.INDICADOR_SOLICITANTE, ConstantesSistema.NAO));

		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

			retorno = false;
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0027] Inclui Solicitante do Registro de Atendimento
	 * (REGISTRO_ATENDIMENTO_SOLICITANTE)
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void inserirRegistroAtendimentoSolicitante(Integer idRegistroAtendimento, Integer idCliente, Collection colecaoEndereco,
					String pontoReferencia, String nomeSolicitante, boolean novoSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, String tipoCliente, String numeroCpf, String numeroRg,
					String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException{

		RegistroAtendimento ra = new RegistroAtendimento();
		RegistroAtendimentoSolicitante solicitanteInserir = new RegistroAtendimentoSolicitante();

		// Registro Atendimento
		ra.setId(idRegistroAtendimento);
		solicitanteInserir.setRegistroAtendimento(ra);

		// Cliente
		if(idCliente != null){
			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			solicitanteInserir.setCliente(cliente);
		}

		// número do Imóvel, Complemento do endereço, LogradouroCep e
		// LogradouroBairro
		if(idCliente == null){
			ClienteEndereco endereco = (ClienteEndereco) Util.retonarObjetoDeColecao(colecaoEndereco);

			if(endereco != null){
				solicitanteInserir.setNumeroImovel(endereco.getNumero());

				if(endereco.getComplemento() != null){
					solicitanteInserir.setComplementoEndereco(endereco.getComplemento());
				}

				solicitanteInserir.setLogradouroCep(endereco.getLogradouroCep());
				solicitanteInserir.setLogradouroBairro(endereco.getLogradouroBairro());

			}

		}

		// Ponto de Referência
		if(pontoReferencia != null && !pontoReferencia.equalsIgnoreCase("")){
			solicitanteInserir.setPontoReferencia(pontoReferencia);
		}

		// Nome do Solicitante
		if(nomeSolicitante != null && !nomeSolicitante.equalsIgnoreCase("")){
			solicitanteInserir.setSolicitante(nomeSolicitante);
		}

		// Indicador Solicitante Principal
		if(novoSolicitante){
			solicitanteInserir.setIndicadorSolicitantePrincipal(ConstantesSistema.INDICADOR_NOVO_SOLICITANTE);
		}else{
			solicitanteInserir.setIndicadorSolicitantePrincipal(ConstantesSistema.INDICADOR_INSERIR_SOLICITANTE_RA);
		}

		// Ultima alteração
		solicitanteInserir.setUltimaAlteracao(new Date());

		// Unidade Solicitante
		if(idUnidadeSolicitante != null){
			UnidadeOrganizacional unidadeSolicitante = new UnidadeOrganizacional();
			unidadeSolicitante.setId(idUnidadeSolicitante);
			solicitanteInserir.setUnidadeOrganizacional(unidadeSolicitante);
		}

		if(idFuncionario != null){
			Funcionario funcionario = new Funcionario();
			funcionario.setId(idFuncionario);
			solicitanteInserir.setFuncionario(funcionario);
		}

		if(tipoCliente != null){
			solicitanteInserir.setClienteTipo(Short.valueOf(tipoCliente));
			if(solicitanteInserir.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.shortValue()){
				solicitanteInserir.setNumeroCpf(numeroCpf);
				solicitanteInserir.setNumeroRG(numeroRg);
				if(!orgaoExpedidorRg.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					OrgaoExpedidorRg orgaoExpedidorRgInserir = new OrgaoExpedidorRg();
					orgaoExpedidorRgInserir.setId(Integer.valueOf(orgaoExpedidorRg));
					solicitanteInserir.setOrgaoExpedidorRg(orgaoExpedidorRgInserir);
				}
				if(!unidadeFederacaoRG.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setId(Integer.valueOf(unidadeFederacaoRG));
					solicitanteInserir.setUnidadeFederacaoRG(unidadeFederacao);
				}
			}else if(solicitanteInserir.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.shortValue()){
				solicitanteInserir.setNumeroCnpj(numeroCnpj);
			}
		}
		Integer idRASolicitante = (Integer) this.getControladorUtil().inserir(solicitanteInserir);
		solicitanteInserir.setID(idRASolicitante);

		if(colecaoFone != null && !colecaoFone.isEmpty() && idCliente == null){
			this.inserirRegistroAtendimentoSolicitanteFone(solicitanteInserir, colecaoFone);
		}
	}

	/**
	 * passa os parametros do registro atendimento solicitante e a coleção de
	 * fones do solicitante e retorna um objeto de Registro Atendimento
	 * Solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 02/09/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoSolicitante inserirDadosNoRegistroAtendimentoSolicitante(Integer idRegistroAtendimento, Integer idCliente,
					Collection colecaoEndereco, String pontoReferencia, String nomeSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, String fonePadrao, String tipoCliente, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException{

		RegistroAtendimento ra = new RegistroAtendimento();
		RegistroAtendimentoSolicitante solicitanteInserir = new RegistroAtendimentoSolicitante();

		// Registro Atendimento
		ra.setId(idRegistroAtendimento);
		solicitanteInserir.setRegistroAtendimento(ra);

		// Cliente
		if(idCliente != null){
			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			solicitanteInserir.setCliente(cliente);
		}

		// número do Imóvel, Complemento do endereço, LogradouroCep e
		// LogradouroBairro
		if(idCliente == null){
			ClienteEndereco endereco = (ClienteEndereco) Util.retonarObjetoDeColecao(colecaoEndereco);
			solicitanteInserir.setNumeroImovel(endereco.getNumero());

			if(endereco.getComplemento() != null){
				solicitanteInserir.setComplementoEndereco(endereco.getComplemento());
			}

			solicitanteInserir.setLogradouroCep(endereco.getLogradouroCep());
			solicitanteInserir.setLogradouroBairro(endereco.getLogradouroBairro());
		}

		// Ponto de Referência
		if(pontoReferencia != null && !pontoReferencia.equalsIgnoreCase("")){
			solicitanteInserir.setPontoReferencia(pontoReferencia);
		}

		// Nome do Solicitante
		if(nomeSolicitante != null && !nomeSolicitante.equalsIgnoreCase("")){
			solicitanteInserir.setSolicitante(nomeSolicitante);
		}

		// Unidade Solicitante
		if(idUnidadeSolicitante != null){
			UnidadeOrganizacional unidadeSolicitante = new UnidadeOrganizacional();
			unidadeSolicitante.setId(idUnidadeSolicitante);
			solicitanteInserir.setUnidadeOrganizacional(unidadeSolicitante);
		}

		if(idCliente == null){
			if(fonePadrao != null && !fonePadrao.equals("")){
				// Responsável pera troca do solicitante principal
				// caso tenha sido trocado então sai da coleção
				boolean trocaPrincipal = false;

				if(colecaoFone != null && !colecaoFone.isEmpty()){
					Iterator iteratorFone = colecaoFone.iterator();
					while(iteratorFone.hasNext()){
						ClienteFone clienteFone = (ClienteFone) iteratorFone.next();
						// caso a colecao só tenha um solicitante então o
						// solicitante será o principal
						if(colecaoFone.size() == 1){
							clienteFone.setIndicadorTelefonePadrao(new Short("1"));
						}else{
							// senão se o id socilitante seja igual ao o id
							// do solicitante que foi escolhido como
							// principal
							if(clienteFone.getUltimaAlteracao().getTime() == Long.parseLong(fonePadrao)){
								// se for diferente de 1, ou seja se o
								// solicitante não era o principal
								if(clienteFone.getIndicadorTelefonePadrao() != 1){
									// seta o valor 1 ao indicador principal do
									// solicitante
									clienteFone.setIndicadorTelefonePadrao(new Short("1"));
									// verifica se o indicador principal do
									// solicitante que era 1 anteriormente já
									// foi mudado para 2(nesse caso o boolean
									// trocaPrincipal está com o valor true).
									if(trocaPrincipal){
										break;
									}
									trocaPrincipal = true;

									// caso o solicitante principal não tenha
									// mudado então sai do loop
								}else{
									break;
								}

							}else{
								// parte que muda o indicador principal do
								// solicitante(que não mais principal)
								// para 2
								if(clienteFone.getIndicadorTelefonePadrao() == 1){
									clienteFone.setIndicadorTelefonePadrao(new Short("2"));
									if(trocaPrincipal){
										break;
									}
									trocaPrincipal = true;
								}
							}
						}
					}
				}
			}
		}
		if(idFuncionario != null){
			Funcionario funcionario = new Funcionario();
			funcionario.setId(idFuncionario);
			solicitanteInserir.setFuncionario(funcionario);
		}

		if(colecaoFone != null && !colecaoFone.isEmpty()){
			solicitanteInserir.setSolicitanteFones(new HashSet(colecaoFone));
		}

		if(tipoCliente != null){
			solicitanteInserir.setClienteTipo(Short.valueOf(tipoCliente));
			if(solicitanteInserir.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.shortValue()){
				solicitanteInserir.setNumeroCpf(numeroCpf);
				solicitanteInserir.setNumeroRG(numeroRg);
				if(!orgaoExpedidorRg.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					OrgaoExpedidorRg orgaoExpedidorRgInserir = new OrgaoExpedidorRg();
					orgaoExpedidorRgInserir.setId(Integer.valueOf(orgaoExpedidorRg));
					solicitanteInserir.setOrgaoExpedidorRg(orgaoExpedidorRgInserir);
				}else{
					solicitanteInserir.setOrgaoExpedidorRg(null);
				}
				if(!unidadeFederacaoRG.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setId(Integer.valueOf(unidadeFederacaoRG));
					solicitanteInserir.setUnidadeFederacaoRG(unidadeFederacao);
				}else{
					solicitanteInserir.setUnidadeFederacaoRG(null);
				}
				solicitanteInserir.setNumeroCnpj(null);
			}else if(solicitanteInserir.getClienteTipo().shortValue() == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.shortValue()){
				solicitanteInserir.setNumeroCnpj(numeroCnpj);
				solicitanteInserir.setNumeroCpf(null);
				solicitanteInserir.setNumeroRG(null);
				solicitanteInserir.setOrgaoExpedidorRg(null);
				solicitanteInserir.setUnidadeFederacaoRG(null);
			}
		}
		return solicitanteInserir;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0027] Inclui Solicitante do Registro de Atendimento (SOLICITANTE_FONE)
	 * 
	 * @author Raphael Rossiter
	 * @date 24/08/2006
	 * @throws ControladorException
	 */
	public void inserirRegistroAtendimentoSolicitanteFone(RegistroAtendimentoSolicitante solicitante, Collection colecaoFone)
					throws ControladorException{

		Iterator itaratorClienteFone = colecaoFone.iterator();
		ClienteFone clienteFone = null;
		SolicitanteFone solicitanteFone = null;

		while(itaratorClienteFone.hasNext()){
			clienteFone = (ClienteFone) itaratorClienteFone.next();

			solicitanteFone = new SolicitanteFone();

			// Registro Atendimento Solicitante
			solicitanteFone.setRegistroAtendimentoSolicitante(solicitante);

			// Tipo do Telefone
			solicitanteFone.setFoneTipo(clienteFone.getFoneTipo());

			// DDD
			solicitanteFone.setDdd(Short.valueOf(clienteFone.getDdd()));

			// Telefone
			solicitanteFone.setFone(clienteFone.getTelefone());

			// Ramal
			if(clienteFone.getRamal() != null && !clienteFone.getRamal().equalsIgnoreCase("")){
				solicitanteFone.setRamal(clienteFone.getRamal());
			}

			// Indicador Fone Padrao
			if(clienteFone.getIndicadorTelefonePadrao() != null){
				solicitanteFone.setIndicadorPadrao(clienteFone.getIndicadorTelefonePadrao());
			}else{
				solicitanteFone.setIndicadorPadrao(ConstantesSistema.INDICADOR_NAO_TELEFONE_PRINCIPAL);
			}

			// Ultima alteração
			solicitanteFone.setUltimaAlteracao(new Date());

			this.getControladorUtil().inserir(solicitanteFone);
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0028] Inclui Registro de Atendimento (REGISTRO_ATENDIMENTO)
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public Integer[] inserirRegistroAtendimento(short indicadorAtendimentoOnLine, String dataAtendimento, String horaAtendimento,
					String tempoEsperaInicial, String tempoEsperaFinal, Integer idMeioSolicitacao, Integer senhaAtendimento,
					Integer idSolicitacaoTipoEspecificacao, String dataPrevista, String observacao, Integer idImovel,
					String descricaoLocalOcorrencia, Integer idSolicitacaoTipo, Collection colecaoEndereco,
					String pontoReferenciaLocalOcorrencia, Integer idBairroArea, Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, Integer idDivisaoEsgoto, Integer idLocalOcorrencia, Integer idPavimentoRua,
					Integer idPavimentoCalcada, Integer idUnidadeAtendimento, Integer idUsuarioLogado, Integer idCliente,
					String pontoReferenciaSolicitante, String nomeSolicitante, boolean novoSolicitante, Integer idUnidadeSolicitante,
					Integer idFuncionario, Collection colecaoFone, Collection colecaoEnderecoSolicitante, Integer idUnidadeDestino,
					String parecerUnidadeDestino, Collection<Integer> colecaoIdServicoTipo, String numeroRAManual, Integer idRAJAGerado,
					BigDecimal coordenadaNorte, BigDecimal coordenadaLeste, Integer sequenceRA, Integer idRaReiterada, String tipoCliente,
					String numeroCpf, String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj,
					Collection<Conta> colecaoContas, String identificadores, ContaMotivoRevisao contaMotivoRevisao,
					String indicadorProcessoAdmJud, String numeroProcessoAgencia, Short quantidadePrestacoesGuiaPagamento)
					throws ControladorException{

		this.verificarDebitosImovelCliente(idSolicitacaoTipoEspecificacao, idImovel, idCliente);

		Integer[] retorno = null;

		/*
		 * Verifica se e necessário geração automática de uma ordem de Serviço
		 */
		Collection<OrdemServico> colecaoOsGeradaAutomatica = null;

		if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao)
						&& (!Util.isVazioOrNulo(colecaoIdServicoTipo) && idSolicitacaoTipo != null)){

			retorno = new Integer[2];

			colecaoOsGeradaAutomatica = this.gerarColecaoOrdemServicoAutomatica(colecaoIdServicoTipo, idSolicitacaoTipo);
		}else{

			retorno = new Integer[1];
		}

		RegistroAtendimento ra = new RegistroAtendimento();

		ra.setIndicadorAtendimentoOnline(indicadorAtendimentoOnLine);

		// id da RA reiterada
		ra.setReiteracao(idRaReiterada);

		// Numeração manual
		/*
		 * if (numeroRAManual != null && !numeroRAManual.equalsIgnoreCase("")) {
		 * ra.setManual(new Integer(arrayNumeroRAManual[0])); }
		 */

		if(numeroRAManual != null && !numeroRAManual.equalsIgnoreCase("")){
			String[] arrayNumeroRAManual = numeroRAManual.split("-");
			ra.setManual(new Integer(arrayNumeroRAManual[0]));
		}

		// Data e Hora do atendimento
		String dataHoraAtendimento = dataAtendimento + " " + Util.formatarHoraSemSegundos(horaAtendimento) + ":00";
		Date dataHoraAtendimentoObjetoDate = Util.converteStringParaDateHora(dataHoraAtendimento);

		ra.setRegistroAtendimento(dataHoraAtendimentoObjetoDate);

		// Tempo de espera inicial
		if(tempoEsperaInicial != null && !tempoEsperaInicial.equalsIgnoreCase("")){
			Date dataEsperaInicial = Util.converteStringParaDateHora(dataAtendimento + " "
							+ Util.formatarHoraSemSegundos(tempoEsperaInicial) + ":00");
			ra.setDataInicioEspera(dataEsperaInicial);
			Date dataEsperaFinal = Util.converteStringParaDateHora(dataAtendimento + " " + Util.formatarHoraSemSegundos(tempoEsperaFinal)
							+ ":00");
			ra.setDataFimEspera(dataEsperaFinal);
		}

		// Meio de Solicitação
		MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
		meioSolicitacao.setId(idMeioSolicitacao);

		ra.setMeioSolicitacao(meioSolicitacao);
		ra.setSenhaAtendimento(senhaAtendimento);

		// Especificação
		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
						.valueOf(idSolicitacaoTipoEspecificacao)));

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
						.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
										SolicitacaoTipoEspecificacao.class.getName()));

		if(solicitacaoTipoEspecificacao == null){
			throw new ControladorException("atencao.pesquisa_inexistente", null, "Solicitação Tipo Especificação");
		}

		ra.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

		// Data Original e Data Prevista
		Date dataPrevistaObjetoDate = Util.converteStringParaDateHora(dataPrevista + ":00");

		ra.setDataPrevistaOriginal(dataPrevistaObjetoDate);
		ra.setDataPrevistaAtual(dataPrevistaObjetoDate);

		// Observação
		if(observacao != null && !observacao.equalsIgnoreCase("")){
			ra.setObservacao(observacao);
		}

		// Imóvel
		if(idImovel != null){
			Imovel imovel = null;

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA);
			Collection<Imovel> colecaoImovel = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel != null && !colecaoImovel.isEmpty()){
				imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
			}

			if(imovel != null){
				ra.setImovel(imovel);
			}else{
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Imovel " + idImovel);
			}
		}

		// Indicador Processo Administrativo Judiciario
		if(!Util.isVazioOuBranco(indicadorProcessoAdmJud)){
			ra.setIndicadorProcessoAdmJud(Short.valueOf(indicadorProcessoAdmJud));
		}else{
			ra.setIndicadorProcessoAdmJud(ConstantesSistema.NAO);
		}

		// Numero Processo na Agência
		ra.setNumeroProcessoAgencia(numeroProcessoAgencia);

		/*
		 * Dados da Identificação do Local da ocorrência (caso a Descrição do
		 * Local da ocorrência esteja preenchida, todos os Dados da
		 * Identificação do Local da ocorrência devem ter o valor nulo; caso
		 * contrário, preencher de acordo com as regras abaixo)
		 */
		if(descricaoLocalOcorrencia != null && !descricaoLocalOcorrencia.equalsIgnoreCase("")){
			ra.setDescricaoLocalOcorrencia(descricaoLocalOcorrencia);

			// Código da Situação
			ra.setCodigoSituacao(RegistroAtendimento.SITUACAO_BLOQUEADO);
		}else{

			/*
			 * Caso a matrícula do Imóvel seja obrigatória (STEP_ICMATRICULA com
			 * o valor correspondente a um na tabela
			 * SOLICITACAO_TIPO_ESPECIFICACAO), nulo.
			 */

			boolean especificao = this.especificacaoExigeMatriculaImovel(solicitacaoTipoEspecificacao);

			if(especificao || (colecaoEndereco != null && !colecaoEndereco.isEmpty())){

				Imovel imovelEndereco = (Imovel) Util.retonarObjetoDeColecao(colecaoEndereco);
				if(imovelEndereco == null){
					throw new ControladorException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
									"atencao.erro.solicitacaoTipoEspecificacao_requer_matricula"));
				}
				// LogradouroBairro e LogradouroCEP
				if(imovelEndereco.getId() != null || especificao){

					ra.setLogradouroBairro(null);
					ra.setLogradouroCep(null);
				}else{

					ra.setLogradouroBairro(imovelEndereco.getLogradouroBairro());
					ra.setLogradouroCep(imovelEndereco.getLogradouroCep());
				}

				// Complemento endereço
				if(imovelEndereco.getComplementoEndereco() != null){
					ra.setComplementoEndereco(imovelEndereco.getComplementoEndereco());
				}

				// número do Imóvel
				ra.setNumeroImovel(imovelEndereco.getNumeroImovel());
			}

			// Ponto de Referência
			if(pontoReferenciaLocalOcorrencia != null && !pontoReferenciaLocalOcorrencia.equalsIgnoreCase("")){
				ra.setPontoReferencia(pontoReferenciaLocalOcorrencia);
			}

			// Área do Bairro
			if(idBairroArea != null && !idBairroArea.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				BairroArea bairroArea = new BairroArea();
				bairroArea.setId(idBairroArea);
				ra.setBairroArea(bairroArea);
			}

			// Localidade
			if(idLocalidade != null){
				Localidade localidade = new Localidade();
				localidade.setId(idLocalidade);
				ra.setLocalidade(localidade);
			}

			// Setor Comercial
			if(idSetorComercial != null){
				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(idSetorComercial);
				ra.setSetorComercial(setorComercial);
			}

			// Quadra
			if(idQuadra != null){
				Quadra quadra = new Quadra();
				quadra.setId(idQuadra);
				ra.setQuadra(quadra);
			}

			// divisão de Esgoto
			if(idDivisaoEsgoto != null && !idDivisaoEsgoto.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				DivisaoEsgoto divisaoEsgoto = new DivisaoEsgoto();
				divisaoEsgoto.setId(idDivisaoEsgoto);
				ra.setDivisaoEsgoto(divisaoEsgoto);
			}

			// Local ocorrência
			if(idLocalOcorrencia != null && !idLocalOcorrencia.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				LocalOcorrencia localOcorrencia = new LocalOcorrencia();
				localOcorrencia.setId(idLocalOcorrencia);
				ra.setLocalOcorrencia(localOcorrencia);
			}

			// Pavimento Rua
			if(idPavimentoRua != null && !idPavimentoRua.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				PavimentoRua pavimentoRua = new PavimentoRua();
				pavimentoRua.setId(idPavimentoRua);
				ra.setPavimentoRua(pavimentoRua);
			}

			// Pavimento Calçada
			if(idPavimentoCalcada != null && !idPavimentoCalcada.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
				pavimentoCalcada.setId(idPavimentoCalcada);
				ra.setPavimentoCalcada(pavimentoCalcada);
			}

			// Código da Situação
			ra.setCodigoSituacao(RegistroAtendimento.SITUACAO_PENDENTE);
		}

		/*
		 * Raphael Rossiter em 29/05/2007
		 * Caso o usuário tente inserir o mesmo RA por mais de uma vez
		 */
		if(idRAJAGerado != null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_atendimento_insercao_ja_realizada", null, idRAJAGerado.toString());
		}

		// Coordenada norte
		ra.setCoordenadaNorte(coordenadaNorte);

		// Coordenada leste
		ra.setCoordenadaLeste(coordenadaLeste);

		// última alteração
		ra.setUltimaAlteracao(new Date());

		// setando o id da RA
		ra.setId(sequenceRA);

		// usuário Logado = Registro e Responsável
		Usuario usuario = new Usuario();
		usuario.setId(idUsuarioLogado);

		// // ------------ REGISTRAR TRANSAÇÃO ------------
		//
		// Operacao operacao = new Operacao();
		// operacao.setId(Operacao.OPERACAO_INSERIR_REGISTRO_ATENDIMENTO);
		//
		// RegistradorOperacao registradorOperacao = new RegistradorOperacao(operacao.getId(), new
		// UsuarioAcaoUsuarioHelper(usuario,
		// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		//
		// OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		// operacaoEfetuada.setOperacao(operacao);
		//
		// ra.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		//
		// registradorOperacao.registrarOperacao(ra);
		//
		// // ------------ REGISTRAR TRANSAÇÃO ------------

		Integer idRAGerado = (Integer) this.getControladorUtil().inserir(ra);
		ra.setId(idRAGerado);

		retorno[0] = idRAGerado;

		// [SB0028] Inclui Registro de Atendimento
		// (REGISTRO_ATENDIMENTO_UNIDADE)
		this.inserirRegistroAtendimentoUnidade(ra, idUnidadeAtendimento, idUsuarioLogado);

		/*
		 * [UC0366] Inserir Registro de Atendimento [SB0027] Inclui Solicitante
		 * do Registro de Atendimento
		 */
		this.inserirRegistroAtendimentoSolicitante(idRAGerado, idCliente, colecaoEnderecoSolicitante, pontoReferenciaSolicitante,
						nomeSolicitante, novoSolicitante, idUnidadeSolicitante, idFuncionario, colecaoFone, tipoCliente, numeroCpf,
						numeroRg, orgaoExpedidorRg, unidadeFederacaoRG, numeroCnpj);

		// [SB0027] Gerar Trâmite
		Tramite tramite = new Tramite();
		tramite.setUsuarioRegistro(usuario);
		tramite.setUsuarioResponsavel(usuario);

		// Registro Atendimento
		tramite.setRegistroAtendimento(ra);

		// Unidade Origem = Unidade Atendimento
		UnidadeOrganizacional unidadeOrigem = new UnidadeOrganizacional();
		unidadeOrigem.setId(idUnidadeAtendimento);

		tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);

		/*
		 * Caso a Unidade Destino esteja preenchida, Id da Unidade Destino; caso
		 * contrário, Id da Unidade de Atendimento
		 */
		if(idUnidadeDestino != null){
			UnidadeOrganizacional unidadeDestino = new UnidadeOrganizacional();
			unidadeDestino.setId(idUnidadeDestino);

			tramite.setUnidadeOrganizacionalDestino(unidadeDestino);
		}else{
			tramite.setUnidadeOrganizacionalDestino(unidadeOrigem);
		}

		/*
		 * Caso o Parecer para a Unidade Destino esteja preenchido, Parecer para
		 * a Unidade Destino; caso contrário, tramite gerado pelo sistema na
		 * abertura do registro de atendimento.
		 */
		if(parecerUnidadeDestino != null && !parecerUnidadeDestino.equalsIgnoreCase("")){
			tramite.setParecerTramite(parecerUnidadeDestino);
		}else{
			tramite.setParecerTramite(Tramite.TRAMITE_GERADO_PELO_SISTEMA_ABERTURA_RA);
		}

		tramite.setDataTramite(new Date());
		tramite.setUltimaAlteracao(new Date());

		// [UC0427] Tramitar Registro de Atendimento
		this.tramitar(tramite, null);

		// [UC0430] - Gerar Ordem de Serviço
		if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao) && !Util.isVazioOrNulo(colecaoOsGeradaAutomatica)){
			Imovel imovel = null;
			Integer idOrdemServico = null;

			for(OrdemServico osGeradaAutomatica : colecaoOsGeradaAutomatica){
				// O Imóvel da OS será o mesmo do RA
				imovel = ra.getImovel();

				if(imovel != null){
					osGeradaAutomatica.setImovel(imovel);
				}

				osGeradaAutomatica.setRegistroAtendimento(ra);

				Object[] dadosDaRA = this.pesquisarDadosLocalizacaoRegistroAtendimento(ra.getId());
				Integer idBairro = null;
				if(dadosDaRA != null){
					if(idLocalidade == null){
						idLocalidade = (Integer) dadosDaRA[0];
					}
					if(idSetorComercial == null){
						idSetorComercial = (Integer) dadosDaRA[1];
					}
					idBairro = (Integer) dadosDaRA[2];
				}

				idOrdemServico = this.getControladorOrdemServico().gerarOrdemServico(osGeradaAutomatica, usuario, null, idLocalidade,
								idSetorComercial, idBairro, idUnidadeAtendimento, idUnidadeDestino, parecerUnidadeDestino, null, false,
								quantidadePrestacoesGuiaPagamento);

				// this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial,
				// idUnidadeAtendimento, idUnidadeDestino, parecerUnidadeDestino, usuario,
				// osGeradaAutomatica);

				// Definido pelo Analista que não tem problema retornar apenas um dos Ids
				retorno[1] = idOrdemServico;
			}
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.intValue() == sistemaParametro.getParmId().intValue()){
			// 1. Caso a especificação informada para o RA tenha indicativo que é para colocar
			// contas em revisão (STEP_ICCOLOCACONTASEMREVISAO da tabela
			// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1))
			if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())
							&& solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao().intValue() == ConstantesSistema.SIM.intValue()){

				if(colecaoContas != null && !colecaoContas.isEmpty()){
					if(!Util.isVazioOuBranco(identificadores)){
						if(contaMotivoRevisao == null){
							throw new ControladorException("atencao.informe_campo", null, "Motivo da Revisão");
						}
						getControladorFaturamento().colocarRevisaoConta(colecaoContas, identificadores, contaMotivoRevisao, usuario);
					}
				}
			}
		}

		return retorno;

	}

	/**
	 * Retorna todos os Registros de Atendimento feito por os coleitores, que
	 * ainda não foram inportado para o Gsan.
	 * 
	 * @author isilva
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarRegistroAtendimentoColetor() throws ControladorException{

		FiltroRegistroAtendimentoColetor filtroRegistroAtendimentoColetor = new FiltroRegistroAtendimentoColetor();
		filtroRegistroAtendimentoColetor.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.LEITURISTA);
		filtroRegistroAtendimentoColetor.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.LEITURISTA_FUNCIONARIO);
		filtroRegistroAtendimentoColetor.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.IMOVEL);
		filtroRegistroAtendimentoColetor
						.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroRegistroAtendimentoColetor
						.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimentoColetor.SOLICITACAO_TIPO_ESPECIFICACAO_UNIDADE_ORGANIZACIONAL);
		filtroRegistroAtendimentoColetor.adicionarParametro(new ParametroNulo(
						FiltroRegistroAtendimentoColetor.DATA_GERACAO_REGISTRO_ATENDIMENTO));

		return this.getControladorUtil().pesquisar(filtroRegistroAtendimentoColetor, RegistroAtendimentoColetor.class.getName());
	}

	/**
	 * Gera todas os Registros de Atendimento vindo dos coletores
	 * 
	 * @author isilva
	 * @throws ControladorException
	 */
	public void gerarRAColetor() throws ControladorException{

		Collection<RegistroAtendimentoColetor> registrosAtendimentoColetor = this.pesquisarRegistroAtendimentoColetor();

		if(registrosAtendimentoColetor != null && !registrosAtendimentoColetor.isEmpty()){
			configurarInserirRegistroAtendimentoColetor(registrosAtendimentoColetor);
		}

	}

	private void configurarInserirRegistroAtendimentoColetor(Collection<RegistroAtendimentoColetor> colecaoRegistroAtendimentoColetor)
					throws NumberFormatException, ControladorException{

		Iterator iteratorRegistroAtendimentoColetor = colecaoRegistroAtendimentoColetor.iterator();

		while(iteratorRegistroAtendimentoColetor.hasNext()){
			RegistroAtendimentoColetor registroAtendimentoColetor = (RegistroAtendimentoColetor) iteratorRegistroAtendimentoColetor.next();

			DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper dataPrevistaUnidadeDestino = definirDataPrevistaUnidadeDestinoEspecificacao(registroAtendimentoColetor);
			String dataPrevista = null;
			if(dataPrevistaUnidadeDestino.getDataPrevista() != null){
				Date dataPrev = dataPrevistaUnidadeDestino.getDataPrevista();
				dataPrevista = Util.formatarData(dataPrev) + " " + Util.formatarHoraSemSegundos(dataPrev);
			}

			Integer unidadeOrganizacional = null;

			if(dataPrevistaUnidadeDestino.getUnidadeOrganizacional() != null){
				unidadeOrganizacional = dataPrevistaUnidadeDestino.getUnidadeOrganizacional().getId();

				FiltroDivisaoEsgoto filtroDivisaoEsgoto = new FiltroDivisaoEsgoto();
				filtroDivisaoEsgoto.adicionarCaminhoParaCarregamentoEntidade(FiltroDivisaoEsgoto.UNIDADE_ORGANIZACIONAL);
				filtroDivisaoEsgoto.adicionarParametro(new ParametroSimples(FiltroDivisaoEsgoto.UNIDADE_ORGANIZACIONAL_ID,
								unidadeOrganizacional.toString()));

			}

			Cliente clienteUsuarioImovel = getControladorImovel().pesquisarClienteUsuarioImovel(
							registroAtendimentoColetor.getImovel().getId());
			Integer idCliente = null;
			Collection colecaoFones = null;

			if(clienteUsuarioImovel != null){
				idCliente = clienteUsuarioImovel.getId();
				colecaoFones = getControladorCliente().pesquisarClienteFone(idCliente);
			}

			FiltroUsuario filtroUsuario = new FiltroUsuario();
			Integer idUnidadeOrganizacional = null;

			if(registroAtendimentoColetor.getLeiturista().getFuncionario() != null){
				filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.FUNCIONARIO_ID, registroAtendimentoColetor
								.getLeiturista().getFuncionario().getId()));
				if(registroAtendimentoColetor.getLeiturista().getFuncionario().getUnidadeOrganizacional() != null){
					idUnidadeOrganizacional = registroAtendimentoColetor.getLeiturista().getFuncionario().getUnidadeOrganizacional()
									.getId();
				}
			}else{

				filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, Usuario.getIdUsuarioBatchParametro() + ""));
			}

			Usuario usuarioLogado = (Usuario) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroUsuario,
							Usuario.class.getName()));

			if(usuarioLogado == null){
				filtroUsuario.limparListaParametros();
				filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, Usuario.getIdUsuarioBatchParametro() + ""));
				usuarioLogado = (Usuario) Util.retonarObjetoDeColecao(getControladorUtil()
								.pesquisar(filtroUsuario, Usuario.class.getName()));
			}

			if(idUnidadeOrganizacional == null){
				idUnidadeOrganizacional = UnidadeOrganizacional.ID_UNIDADE_ORGANIZACIONAL_DEFAULT_LEITURISTA;
			}

			Collection colecaoEnderecoImovel = buscarEnderecosImovel(registroAtendimentoColetor.getImovel().getId());

			Integer idBairroArea = null;
			Integer idLocalidade = null;
			Integer idSetorComercial = null;
			Integer idQuadra = null;

			if(colecaoEnderecoImovel != null && !colecaoEnderecoImovel.isEmpty()){
				Imovel imovel = ((Imovel) colecaoEnderecoImovel.iterator().next());

				idLocalidade = imovel.getLocalidade().getId();
				idSetorComercial = imovel.getSetorComercial().getId();
				idQuadra = imovel.getQuadra().getId();

				FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
				filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID, imovel.getLogradouroBairro().getBairro()
								.getId().toString()));
				Collection colecaoBairroArea = this.getControladorUtil().pesquisar(filtroBairroArea, BairroArea.class.getName());

				if(colecaoBairroArea != null && !colecaoBairroArea.isEmpty()){
					idBairroArea = ((BairroArea) colecaoBairroArea.iterator().next()).getId();
				}
			}

			Integer idServicoTipo = null;
			if(registroAtendimentoColetor.getSolicitacaoTipoEspecificacao().getServicoTipo() != null){
				idServicoTipo = registroAtendimentoColetor.getSolicitacaoTipoEspecificacao().getServicoTipo().getId();
			}

			/**
			 * short indicadorAtendimentoOnLine,
			 * String dataAtendimento,
			 * String horaAtendimento,
			 * String tempoEsperaInicial,
			 * String tempoEsperaFinal,
			 * Integer idMeioSolicitacao,
			 * Integer senhaAtendimento,
			 * Integer idSolicitacaoTipoEspecificacao,
			 * String dataPrevista,
			 * String observacao,
			 * Integer idImovel,
			 * String descricaoLocalOcorrencia,
			 * Integer idSolicitacaoTipo,
			 * Collection colecaoEndereco,
			 * String pontoReferenciaLocalOcorrencia,
			 * Integer idBairroArea,
			 * Integer idLocalidade,
			 * Integer idSetorComercial,
			 * Integer idQuadra,
			 * Integer idDivisaoEsgoto,
			 * Integer idLocalOcorrencia,
			 * Integer idPavimentoRua,
			 * Integer idPavimentoCalcada,
			 * Integer idUnidadeAtendimento,
			 * Integer idUsuarioLogado,
			 * Integer idCliente,
			 * String pontoReferenciaSolicitante,
			 * String nomeSolicitante,
			 * boolean novoSolicitante,
			 * Integer idUnidadeSolicitante,
			 * Integer idFuncionario,
			 * Collection colecaoFone,
			 * Collection colecaoEnderecoSolicitante,
			 * Integer idUnidadeDestino,
			 * String parecerUnidadeDestino,
			 * Integer idServicoTipo,
			 * String numeroRAManual,
			 * Integer idRAJAGerado,
			 * BigDecimal coordenadaNorte,
			 * BigDecimal coordenadaLeste,
			 * Collection<Conta> colecaoContas,
			 * String identificadores,
			 * ContaMotivoRevisao contaMotivoRevisao
			 */

			// Como esse método de gerarRAColetor() não está sendo utilizado, vamos continuar o
			// código como estava
			Collection<Integer> colecaoIdServicoTipo = new ArrayList<Integer>();
			colecaoIdServicoTipo.add(idServicoTipo);

			// Campos inseridos através da tela na funcionalidade Inserir RA
			String indicadorProcessoAdmJud = ConstantesSistema.NAO.toString();
			String numeroProcessoAgencia = null;

			/*
			 * Lembrar que deve-se passar o id do RA no metodo.
			 * registroAtendimentoColetor.getId()
			 */
			this.inserirRegistroAtendimento(Short.parseShort("1"), Util.formatarData(registroAtendimentoColetor
							.getDataRegistroAtendimento()), Util.formatarHoraSemSegundos(registroAtendimentoColetor
							.getDataRegistroAtendimento()), null, null, MeioSolicitacao.COLETOR_AGENTE_COMERCIAL, null,
							registroAtendimentoColetor.getSolicitacaoTipoEspecificacao().getId(), dataPrevista, registroAtendimentoColetor
											.getObservacao(), registroAtendimentoColetor.getImovel().getId(), null,
							registroAtendimentoColetor.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId(),
							colecaoEnderecoImovel, null, idBairroArea, idLocalidade, idSetorComercial, idQuadra, null, null, null, null,
							idUnidadeOrganizacional, usuarioLogado.getId(), idCliente, null, null, false, null, null, colecaoFones, null,
							unidadeOrganizacional, null, colecaoIdServicoTipo, null, null, registroAtendimentoColetor.getCoordenadaNorte(),
							registroAtendimentoColetor.getCoordenadaLeste(), registroAtendimentoColetor.getId(), null, null, null, null,
							null, null, null, null, null, null, indicadorProcessoAdmJud, numeroProcessoAgencia, null);

			Date data = new Date();
			registroAtendimentoColetor.setDataGeracaoRegistroAtendimento(data);
			registroAtendimentoColetor.setUltimaAlteracao(data);
			this.alterarRegistroAtendimentoColetor(registroAtendimentoColetor);
		}

	}

	private Collection buscarEnderecosImovel(Integer matricula) throws ControladorException{

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, matricula.toString()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		Collection colecaoEnderecoImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		return colecaoEnderecoImovel;
	}

	private void alterarRegistroAtendimentoColetor(RegistroAtendimentoColetor registroAtendimentoColetor) throws ControladorException{

		getControladorUtil().atualizar(registroAtendimentoColetor);
	}

	private DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper definirDataPrevistaUnidadeDestinoEspecificacao(
					RegistroAtendimentoColetor registroAtendimentoColetor) throws ControladorException{

		Date dataAtendimento = Util.converteStringParaDateHora(Util.formatarData(registroAtendimentoColetor.getDataRegistroAtendimento())
						+ " " + Util.formatarHoraSemSegundos(registroAtendimentoColetor.getDataRegistroAtendimento()) + ":00");

		if(dataAtendimento == null){
			throw new ControladorException("atencao.data.hora.invalido");
		}

		return this.definirDataPrevistaUnidadeDestinoEspecificacao(dataAtendimento, registroAtendimentoColetor
						.getSolicitacaoTipoEspecificacao().getId());

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0028] Inclui Registro de Atendimento (REGISTRO_ATENDIMENTO_UNIDADE)
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public void inserirRegistroAtendimentoUnidade(RegistroAtendimento ra, Integer idUnidadeAtendimento, Integer idUsuarioLogado)
					throws ControladorException{

		RegistroAtendimentoUnidade raUnidade = new RegistroAtendimentoUnidade();

		// Registro Atendimento
		raUnidade.setRegistroAtendimento(ra);

		// Unidade Atendimento
		UnidadeOrganizacional unidadeAtendimento = new UnidadeOrganizacional();
		unidadeAtendimento.setId(idUnidadeAtendimento);

		raUnidade.setUnidadeOrganizacional(unidadeAtendimento);

		// usuário Logado
		Usuario usuario = new Usuario();
		usuario.setId(idUsuarioLogado);

		raUnidade.setUsuario(usuario);

		// Atendimento Relação Tipo
		AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
		atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

		raUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);

		// última Alteração
		raUnidade.setUltimaAlteracao(new Date());

		this.getControladorUtil().inserir(raUnidade);
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Pré-Encerramento
	 * [FS0001] Verificar possibilidade de encerramento do registro de
	 * atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 25/08/2006
	 * @param registroAtendimento
	 * @param usuarioLogado
	 * @param indicadorAutorizacaoManutencaoRA
	 * @throws ControladorException
	 */
	public void validarPreEncerramentoRA(RegistroAtendimento registroAtendimento, Usuario usuarioLogado,
					Short indicadorAutorizacaoManutencaoRA) throws ControladorException{

		if(registroAtendimento != null){
			// Caso indicador de autorização de manutenção seja 2-não
			if(indicadorAutorizacaoManutencaoRA.intValue() == ConstantesSistema.NAO.intValue()){
				// Caso tipo de solicitação do RA seja da Área de Tarifa Social
				if(new Short(registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getIndicadorTarifaSocial())
								.intValue() == 1
								&& new Short(usuarioLogado.getUnidadeOrganizacional().getIndicadorTarifaSocial()).intValue() != 1){

					UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualRA(registroAtendimento.getId());

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.encerrar_ra_nao_tarifa_social", null, unidadeAtual.getDescricao());
				}
			}
			if(new Short(registroAtendimento.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO.intValue()){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.encerrar_ra_ja_encerrado", null, registroAtendimento.getId() + "");
			}

			// Caso exista ordem de Serviço não encerrada e programada para
			// o registro de atendimento
			Collection<OrdemServico> colecaoOrdemServico = obterOSRA(registroAtendimento.getId());
			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
				for(OrdemServico ordemServico : colecaoOrdemServico){
					if((new Short(ordemServico.getSituacao())).intValue() != OrdemServico.SITUACAO_ENCERRADO.intValue()
									&& getControladorOrdemServico().verificarExistenciaOSProgramada(ordemServico.getId())){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.encerrar_ra_existe_os_programada", null, registroAtendimento.getId() + "");
					}
				}
			}
		}
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Pré-Encerramento
	 * [FS0001] Verificar possibilidade de encerramento do registro de
	 * atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2007
	 * @param registroAtendimento
	 * @param usuarioLogado
	 * @param indicadorAutorizacaoManutencaoRA
	 * @throws ControladorException
	 */
	public void validarPreEncerramentoRASemTarifaSocial(RegistroAtendimento registroAtendimento, Usuario usuarioLogado,
					Short indicadorAutorizacaoManutencaoRA) throws ControladorException{

		if(registroAtendimento != null){
			// Caso indicador de autorização de manutenção seja 2-não
			if(indicadorAutorizacaoManutencaoRA.intValue() == ConstantesSistema.NAO.intValue()){

				UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualRA(registroAtendimento.getId());

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException e){
				}
				throw new ControladorException("atencao.encerrar_ra_nao_tarifa_social", null, unidadeAtual.getDescricao());

			}
			if(new Short(registroAtendimento.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO.intValue()){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException e){
				}

				throw new ControladorException("atencao.encerrar_ra_ja_encerrado", null, registroAtendimento.getId() + "");
			}

			// Caso exista ordem de Serviço não encerrada e programada para
			// o registro de atendimento
			Collection<OrdemServico> colecaoOrdemServico = obterOSRA(registroAtendimento.getId());
			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
				for(OrdemServico ordemServico : colecaoOrdemServico){
					if(new Short(ordemServico.getSituacao()).intValue() != OrdemServico.SITUACAO_ENCERRADO.intValue()){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.encerrar_ra_existe_os_associada", null, registroAtendimento.getId() + "");
					}
				}
			}
		}
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * [FS003] Validar RA de Referência
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 */
	public RegistroAtendimento validarRAReferencia(Integer idRA, Integer idRAReferencia) throws ControladorException{

		// Caso o número do RA Referência seja igual ao número do ra que está
		// sendo encerrado.
		if(idRA.intValue() == idRAReferencia.intValue()){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.encerrar_ra_referencia_igual_ra");
		}
		RegistroAtendimento registroAtendimento = null;
		try{
			registroAtendimento = repositorioRegistroAtendimento.pesquisarRegistroAtendimento(idRAReferencia);
		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}
		// Caso o número do RA de Referência não exista.
		if(registroAtendimento == null){
			// sessionContext.setRollbackOnly();
			// throw new ControladorException(
			// "atencao.encerrar_ra_referencia_inexistente");
		}else{
			// Caso o número do RA de Referência esteja encerrado.
			if(new Short(registroAtendimento.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO.intValue()){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.encerrar_ra_referencia_ja_encerrado");
			}
		}
		return registroAtendimento;
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Validar Encerramento
	 * [FS0004] Verificar data do encerramento [FS0005] Verificar hora do
	 * encerramento
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param registroAtendimento
	 * @throws ControladorException
	 */
	public void validarEncerramentoRA(RegistroAtendimento registroAtendimento) throws ControladorException{

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		Date dataInicio = new Date();

		if(registroAtendimento != null){
			Date dataCorrente = new Date();
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(registroAtendimento.getDataEncerramento(), dataCorrente);
			if(qtdeDias < 0){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.encerrar_ra_data_encerramento_anterior_data_corrente", null,
								Util.formatarData(dataCorrente));
			}else if(Util.datasIguais(registroAtendimento.getDataEncerramento(), dataCorrente)){
				if(Util.compararHoraMinuto(Util.formatarHoraSemData(registroAtendimento.getDataEncerramento()),
								Util.formatarHoraSemData(dataCorrente), ">")){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.encerrar_ra_hora_encerramento_anterior_hora_corrente", null,
									Util.formatarHoraSemSegundos(dataCorrente));
				}
			}
			Date dataRA = registroAtendimento.getRegistroAtendimento();
			int qtdeDiasRA = Util.obterQuantidadeDiasEntreDuasDatas(dataRA, registroAtendimento.getDataEncerramento());
			if(qtdeDiasRA < 0){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.encerrar_ra_data_encerramento_posterior_data_atendimento", null,
								Util.formatarData(dataRA));
			}else if(qtdeDiasRA == 0){
				if(Util.compararHoraMinuto(Util.formatarHoraSemData(dataRA),
								Util.formatarHoraSemData(registroAtendimento.getDataEncerramento()), ">")){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.encerrar_ra_hora_encerramento_posterior_hora_atendimento", null,
									Util.formatarHoraSemSegundos(dataRA));
				}
			}
			try{
				Date maiorData = this.repositorioRegistroAtendimento.obterMaiorDataEncerramentoOSRegistroAtendimento(registroAtendimento
								.getId());
				if(maiorData != null){
					maiorData = Util.formatarDataSemHora(maiorData);
					int qtdeDiasOS = Util.obterQuantidadeDiasEntreDuasDatas(maiorData, registroAtendimento.getDataEncerramento());
					if(qtdeDiasOS < 0){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.encerrar_ra_data_encerramento_posterior_maior_data_encerramento_os", null,
										Util.formatarData(maiorData));
					}else if(Util.datasIguais(maiorData, registroAtendimento.getDataEncerramento())){
						// Caso o ra tenha ordens de Serviço já encerradas e a
						// data do encerramento seja igual maior data
						// do encerramento das ordens de Serviço e a hora do
						// encerramento seja anterior hora da maior
						// data do encerramento das ordens de Serviço.
						if(Util.compararHoraMinuto(Util.formatarHoraSemData(maiorData),
										Util.formatarHoraSemData(registroAtendimento.getDataEncerramento()), ">")){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException(
											"atencao.encerrar_ra_hora_encerramento_posterior_hora_maior_data_encerramento_os", null,
											Util.formatarHoraSemSegundos(maiorData));
						}
					}
				}
			}catch(ErroRepositorioException ex){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ilex){

				}
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		System.out.println("testeLentidaoRA ## " + " ## ControladorRegistroAtendimentoSEJB -> Tempo do método validarEncerramentoRA: "
						+ Util.diferencaSegundos(dataInicio, new Date()) + " s ");
		System.out.println("testeLentidaoRA Número RA: " + registroAtendimento.getId());
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param registroAtendimento
	 * @param registroAtendimentoUnidade
	 * @param usuarioLogado
	 */
	public void encerrarRegistroAtendimento(RegistroAtendimento registroAtendimento, RegistroAtendimentoUnidade registroAtendimentoUnidade,
					Usuario usuarioLogado) throws ControladorException{

		try{

			this.verificarRegistroAtendimentoControleConcorrencia(registroAtendimento);

			// Inserir Registro Atendimento Unidade
			this.getControladorUtil().inserir(registroAtendimentoUnidade);

			AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = registroAtendimento.getAtendimentoMotivoEncerramento();

			// Caso exista ordem de Serviço não encerrada e programada para o registro de
			// atendimento
			// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
			Date dataInicial = new Date();
			Collection<OrdemServico> colecaoOrdemServico = obterOSRA(registroAtendimento.getId());
			// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
			System.out.println("testeLentidaoRA ## "
							+ " ## ControladorRegistroAtendimentoSEJB -> encerrarRegistroAtendimento -> Tempo obterOSRA: "
							+ Util.diferencaSegundos(dataInicial, new Date()) + " s ");
			System.out.println("testeLentidaoRA Número RA: " + registroAtendimento.getId());

			boolean osPendente = false;

			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
				for(OrdemServico ordemServico : colecaoOrdemServico){
					if((new Short(ordemServico.getSituacao())).intValue() == OrdemServico.SITUACAO_PENDENTE.intValue()){
						osPendente = true;
						break;
					}
				}
			}

			if(osPendente){

				if(atendimentoMotivoEncerramento != null
								&& atendimentoMotivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO){

					String idMotivoEncerramento = "" + atendimentoMotivoEncerramento.getId();
					String parecerEncerramento = registroAtendimento.getParecerEncerramento();
					Date dataEncerramento = registroAtendimento.getDataEncerramento();
					Date dataUltimaAlteracao = registroAtendimento.getUltimaAlteracao();

					// Encerrar OS que ainda não estáo encerradas
					Collection<OrdemServico> colecaoOS = this.obterOSRA(registroAtendimento.getId());

					if(colecaoOS != null && !colecaoOS.isEmpty()){

						for(OrdemServico servico : colecaoOS){
							if(new Short(servico.getSituacao()).intValue() != OrdemServico.SITUACAO_ENCERRADO.intValue()){

								OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
								osEncerramentoHelper.setNumeroOS(servico.getId());
								osEncerramentoHelper.setDataExecucao(dataEncerramento);
								osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
								osEncerramentoHelper.setIdMotivoEncerramento(idMotivoEncerramento);
								osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
								osEncerramentoHelper.setParecerEncerramento(parecerEncerramento);
								osEncerramentoHelper.setOsFiscalizacao(null);
								osEncerramentoHelper.setIndicadorVistoriaServicoTipo(null);
								osEncerramentoHelper.setCodigoRetornoVistoriaOs(null);

								// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
								dataInicial = new Date();
								this.getControladorOrdemServico().encerrarOSSemExecucao(osEncerramentoHelper, null,
												OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);

								// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
								System.out.println("testeLentidaoRA ## "
												+ " ## ControladorRegistroAtendimentoSEJB -> encerrarRegistroAtendimento - > "
												+ "tempo encerrarOSSemExecucao: " + Util.diferencaSegundos(dataInicial, new Date()) + " s ");
								System.out.println("testeLentidaoRA Número RA: " + registroAtendimento.getId());
								System.out.println("testeLentidaoRA Número OS: " + osEncerramentoHelper.getNumeroOS());
							}
						}
					}

				}else{
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.indicador_execucao_nao");
				}

			}

			// Início - Retirar Contas de Revisão
			if(registroAtendimento != null){
				SolicitacaoTipoEspecificacao especificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();

				if(especificacao == null){
					FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
					filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(filtroRegistroAtendimento.ID, registroAtendimento
									.getId()));

					Collection<RegistroAtendimento> registros = this.getControladorUtil().pesquisar(filtroRegistroAtendimento,
									RegistroAtendimento.class.getName());

					if(registros != null && !registros.isEmpty()){
						especificacao = ((RegistroAtendimento) registros.iterator().next()).getSolicitacaoTipoEspecificacao();
					}
				}

				if(especificacao != null){
					FiltroEspecificacaoTipoValidacao filtroEspecificacaoTipoValidacao = new FiltroEspecificacaoTipoValidacao();
					filtroEspecificacaoTipoValidacao.adicionarParametro(new ParametroSimples(
									filtroEspecificacaoTipoValidacao.ID_SOLICITACAO_TIPO_ESPECIFICACAO_ID, especificacao.getId()));

					Collection<EspecificacaoTipoValidacao> especificacoesTipoValidacao = this.getControladorUtil().pesquisar(
									filtroEspecificacaoTipoValidacao, EspecificacaoTipoValidacao.class.getName());

					if(especificacoesTipoValidacao != null && !especificacoesTipoValidacao.isEmpty()){
						Iterator<EspecificacaoTipoValidacao> especificacoesIterator = especificacoesTipoValidacao.iterator();
						while(especificacoesIterator.hasNext()){
							EspecificacaoTipoValidacao especificacaoTipoValidacao = especificacoesIterator.next();
							if(especificacaoTipoValidacao.getCodigoConstante() == EspecificacaoTipoValidacao.ALTERACAO_CONTA){
								FiltroConta filtroConta = new FiltroConta();
								filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REGISTRO_ATENDIMENTO, registroAtendimento
												.getId()));
								filtroConta.adicionarParametro(new ParametroNaoNulo(FiltroConta.DATA_REVISAO));
								filtroConta.adicionarParametro(new ParametroNaoNulo(FiltroConta.CONTA_MOTIVO_REVISAO));
								Collection<Conta> contas = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
								if(contas != null && !contas.isEmpty()){
									String identificadores = "";
									Iterator<Conta> contasIterator = contas.iterator();
									while(contasIterator.hasNext()){
										if(!identificadores.equals("")){
											identificadores = identificadores + ",";
										}
										Conta conta = contasIterator.next();
										identificadores = identificadores + ("" + conta.getId());
									}
									this.getControladorFaturamento().retirarRevisaoConta(contas, identificadores, usuarioLogado);
								}
							}
						}
					}
				}
			}
			// Fim - Retirar Contas de Revisão

			// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
			dataInicial = new Date();
			// Atualizar Tabela RA com dados do Encerramento
			repositorioRegistroAtendimento.encerrarRegistroAtendimento(registroAtendimento);
			// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
			System.out.println("testeLentidaoRA ## " + " ## ControladorRegistroAtendimentoSEJB -> encerrarRegistroAtendimento ->"
							+ " Tempo do encerrarRegistroAtendimento: " + Util.diferencaSegundos(dataInicial, new Date()) + " s ");
			System.out.println("testeLentidaoRA Número RA: " + registroAtendimento.getId());

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param registroAtendimento
	 * @param registroAtendimentoUnidade
	 * @param usuarioLogado
	 */
	public void encerrarRegistroAtendimento(Collection<RegistroAtendimento> colecaoRegistroAtendimento,
					Collection<RegistroAtendimentoUnidade> colecaoRegistroAtendimentoUnidade, Usuario usuarioLogado)
					throws ControladorException{

		try{

			Iterator iteratorRegistroAtendimento = colecaoRegistroAtendimento.iterator();
			Iterator iteratorRegistroAtendimentoUnidade = colecaoRegistroAtendimentoUnidade.iterator();
			while(iteratorRegistroAtendimento.hasNext()){
				RegistroAtendimento registroAtendimento = (RegistroAtendimento) iteratorRegistroAtendimento.next();
				RegistroAtendimentoUnidade registroAtendimentoUnidade = (RegistroAtendimentoUnidade) iteratorRegistroAtendimentoUnidade
								.next();

				this.encerrarRegistroAtendimento(registroAtendimento, registroAtendimentoUnidade, usuarioLogado);

				// Cancelar Guia
				// Cancelar as outras prestacoes da Guia
				FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
				filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.REGISTRO_ATENDIMENTO_ID,
								registroAtendimento.getId()));

				Collection<GuiaPagamento> colecaoGuiaPagamento = getControladorUtil().pesquisar(filtroGuiaPagamento,
								GuiaPagamento.class.getName());

				for(GuiaPagamento guiaPagamento : colecaoGuiaPagamento){
					Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = getControladorFaturamento()
									.pesquisarGuiasPagamentoPrestacaoFiltrar(guiaPagamento.getId());

					if(colecaoGuiasPrestacoes != null && colecaoGuiasPrestacoes.size() > 0){
						Collection<String> colecaoGuiasPrestacoesRemovidas = new ArrayList<String>();

						for(GuiaPagamentoPrestacaoHelper guiaPagamentoHelper : colecaoGuiasPrestacoes){
							FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, guiaPagamentoHelper.getIdGuiaPagamento()));
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, guiaPagamentoHelper.getNumeroPrestacao()));

							GuiaPagamentoPrestacao guiaPagamentoPrestacaoPesquisada = (GuiaPagamentoPrestacao) Util
											.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroGuiaPagamentoPrestacao,
															GuiaPagamentoPrestacao.class.getName()));

							if(guiaPagamentoPrestacaoPesquisada != null){
								colecaoGuiasPrestacoesRemovidas.add(guiaPagamentoHelper.getIdGuiaPagamento().toString()
												+ guiaPagamentoHelper.getNumeroPrestacao().toString());

							}
						}

						String[] registrosRemocao = new String[colecaoGuiasPrestacoesRemovidas.size()];
						int contLinha = 0;
						for(String guiaPagamentoRemovida : colecaoGuiasPrestacoesRemovidas){
							registrosRemocao[contLinha] = guiaPagamentoRemovida;

							contLinha = contLinha + 1;
						}

						if(contLinha > 0){
							getControladorFaturamento()
											.cancelarGuiaPagamento(colecaoGuiasPrestacoes, registrosRemocao, true, usuarioLogado);
						}
					}
				}

			}

		}catch(ControladorException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0440] Consultar Programação de Abastecimento
	 * Caso exista Programação de Abastecimento de uma determinada Área de Bairro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/08/2006
	 * @param idMunicipio
	 * @param idBairro
	 * @param areaBairro
	 * @param mesAnoReferencia
	 * @return Collection<AbastecimentoProgramacao>
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Collection consultarProgramacaoAbastecimento(String idMunicipio, String codigoBairro, String areaBairro, String mesAnoReferencia)
					throws ControladorException{

		String ano = null;

		String mes = null;

		String anoMesReferencia = null;

		if(mesAnoReferencia == null || mesAnoReferencia.equals("")){

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);
			String anoMesCorrente = ano + mes;
			anoMesReferencia = anoMesCorrente;

		}else{

			boolean mesAnoValido = Util.validarMesAno(mesAnoReferencia);

			if(mesAnoValido == false){
				throw new ControladorException("atencao.adicionar_debito_ano_mes_referencia_invalido", null, mesAnoReferencia);
			}

			mes = mesAnoReferencia.substring(0, 2);
			ano = mesAnoReferencia.substring(3, 7);

			anoMesReferencia = ano + mes;
		}

		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));

		Collection colecaBairro = getControladorUtil().pesquisar(filtroBairro, Bairro.class.getName());

		Bairro bairro = (Bairro) colecaBairro.iterator().next();

		int idBairro = bairro.getId();

		// Seleciona todas as programações de abastecimento de acordo da Área de
		// bairro e o mês e o ano de referencia informado. Ordenando de forma
		// crescente por Data de Inicio de Abastecimento e Hora de Inicio de
		// abastecimento

		FiltroAbastecimentoProgramacao filtroAbastecimentoProgramacao = new FiltroAbastecimentoProgramacao();

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("bairroArea");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("municipio");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("sistemaAbastecimento");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("setorAbastecimento");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("distritoOperacional");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("zonaAbastecimento");

		filtroAbastecimentoProgramacao.adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroAbastecimentoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.ANOMESREFERENCIA,
						anoMesReferencia));
		filtroAbastecimentoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.MUNICIPIO, idMunicipio));

		filtroAbastecimentoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.BAIRRO, idBairro));

		filtroAbastecimentoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.BAIRRO_AREA, areaBairro));

		filtroAbastecimentoProgramacao.setCampoOrderBy(FiltroAbastecimentoProgramacao.DATA_INICIO,
						FiltroAbastecimentoProgramacao.HORA_INICIO);

		Collection colecaoAbastecimentoProgramacao = getControladorUtil().pesquisar(filtroAbastecimentoProgramacao,
						AbastecimentoProgramacao.class.getName());

		return colecaoAbastecimentoProgramacao;

	}

	/**
	 * [UC0440] Consultar Programação de Manutenção
	 * Caso exista Programação de Manutenção de uma determinada Área de Bairro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/08/2006
	 * @param idMunicipio
	 * @param idBairro
	 * @param areaBairro
	 * @param mesAnoReferencia
	 * @return Collection<ManutencaoProgramacao>
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Collection consultarProgramacaoManutencao(String idMunicipio, String codigoBairro, String areaBairro, String mesAnoReferencia)
					throws ControladorException{

		String ano = null;

		String mes = null;

		String anoMesReferencia = null;

		if(mesAnoReferencia == null || mesAnoReferencia.equals("")){

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);
			String anoMesCorrente = ano + mes;
			anoMesReferencia = anoMesCorrente;

		}else{

			boolean mesAnoValido = Util.validarMesAno(mesAnoReferencia);
			if(mesAnoValido == false){
				throw new ControladorException("atencao.ano_mes_referencia.invalida");
			}

			mes = mesAnoReferencia.substring(0, 2);
			ano = mesAnoReferencia.substring(3, 7);

			anoMesReferencia = ano + mes;
		}

		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairro));

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipio));

		Collection colecaBairro = getControladorUtil().pesquisar(filtroBairro, Bairro.class.getName());

		Bairro bairro = (Bairro) colecaBairro.iterator().next();

		int idBairro = bairro.getId();

		// Seleciona todas as programações de abastecimento de acordo da Área de
		// bairro e o mês e o ano de referencia informado. Ordenando de forma
		// crescente por Data de Inicio de Abastecimento e Hora de Inicio de
		// abastecimento

		FiltroManutencaoProgramacao filtroManutencaoProgramacao = new FiltroManutencaoProgramacao();

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("bairroArea");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("municipio");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("sistemaAbastecimento");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("setorAbastecimento");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("distritoOperacional");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("zonaAbastecimento");

		filtroManutencaoProgramacao.adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroManutencaoProgramacao
						.adicionarParametro(new ParametroSimples(FiltroManutencaoProgramacao.ANOMESREFERENCIA, anoMesReferencia));

		filtroManutencaoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.MUNICIPIO, idMunicipio));

		filtroManutencaoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.BAIRRO, idBairro));

		filtroManutencaoProgramacao.adicionarParametro(new ParametroSimples(FiltroAbastecimentoProgramacao.BAIRRO_AREA, areaBairro));

		filtroManutencaoProgramacao.setCampoOrderBy(FiltroAbastecimentoProgramacao.DATA_INICIO, FiltroAbastecimentoProgramacao.HORA_INICIO);

		Collection colecaoManutencaoProgramacao = getControladorUtil().pesquisar(filtroManutencaoProgramacao,
						ManutencaoProgramacao.class.getName());

		return colecaoManutencaoProgramacao;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação tenha o indicadorGeracaoOrdemServico igual a 1 (SIM) e servicoTipo
	 * diferente de NULO, retorna TRUE
	 * [SB0030] Gerar Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @author Saulo Lima
	 * @date 12/02/2009
	 *       Mudança no comportamento do método, buscando pelo indicador.
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean gerarOrdemServicoAutomatica(Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		boolean retorno = false;

		FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(FiltroEspecificacaoServicoTipo.INDICADOR_GERACAO_AUTOMATICA,
						ConstantesSistema.SIM));
		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
						FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(FiltroEspecificacaoServicoTipo.SERVICO_TIPO_INDICADOR_USO,
						ConstantesSistema.SIM));

		Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = this.getControladorUtil().pesquisar(
						filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class.getName());

		if(!Util.isVazioOrNulo(colecaoEspecificacaoServicoTipo)){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * método Responsável pela elaboração de um objeto do tipo OrdemServico que será inserido junto
	 * com um RA
	 * [SB0030] Gerar Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 28/11/2006
	 * @param idServicoTipo
	 *            ,
	 *            idSolicitacaoTipo
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico gerarOrdemServicoAutomatica(Integer idServicoTipo, Integer idSolicitacaoTipo) throws ControladorException{

		OrdemServico osGeradaAutomatica = new OrdemServico();

		// Carregando o servicoTipo informado
		// ===================================================================
		FiltroServicoTipo filtroServicoTipoInformado = new FiltroServicoTipo();
		filtroServicoTipoInformado.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia.servicoTipo");

		filtroServicoTipoInformado.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer.valueOf(idServicoTipo)));

		filtroServicoTipoInformado.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoServicoTipoInformado = getControladorUtil().pesquisar(filtroServicoTipoInformado, ServicoTipo.class.getName());

		ServicoTipo servicoTipoInformado = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipoInformado);
		// ===================================================================

		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

		filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, Integer.valueOf(idSolicitacaoTipo)));

		filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.SOLICITACAO_TIPO_GRUPO_ID,
						SolicitacaoTipoGrupo.ID_OPERACIONAL_AGUA_COM_DIAGNOSTICO));

		Collection colecaoSolicitacaoTipo = getControladorUtil().pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		/*
		 * Caso o grupo da solicitação do registro de atendimento esteja associado ao grupo
		 * "Operacional - Água"
		 */
		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia.servicoTipo");
			filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoPrioridade");

			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_DIAGNOSTICO_SERVICO_TIPO_REF,
							ServicoTipoReferencia.INDICADOR_DIAGNOSTICO_ATIVO));

			Collection colecaoServicoTipo = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){

				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

				if(servicoTipo.getServicoTipoPrioridade() != null){

					osGeradaAutomatica.setServicoTipoPrioridadeOriginal(servicoTipo.getServicoTipoPrioridade());
					osGeradaAutomatica.setServicoTipoPrioridadeAtual(servicoTipo.getServicoTipoPrioridade());

				}

				osGeradaAutomatica.setServicoTipo(servicoTipo);
				osGeradaAutomatica.setServicoTipoReferencia(servicoTipoInformado);

			}else{

				if(servicoTipoInformado.getServicoTipoPrioridade() != null){

					osGeradaAutomatica.setServicoTipoPrioridadeOriginal(servicoTipoInformado.getServicoTipoPrioridade());
					osGeradaAutomatica.setServicoTipoPrioridadeAtual(servicoTipoInformado.getServicoTipoPrioridade());

				}

				osGeradaAutomatica.setServicoTipo(servicoTipoInformado);

				if(servicoTipoInformado.getServicoTipoReferencia() != null
								&& servicoTipoInformado.getServicoTipoReferencia().getServicoTipo() != null){

					osGeradaAutomatica.setServicoTipoReferencia(servicoTipoInformado.getServicoTipoReferencia().getServicoTipo());

				}

			}

		}else{

			if(servicoTipoInformado.getServicoTipoPrioridade() != null){

				osGeradaAutomatica.setServicoTipoPrioridadeOriginal(servicoTipoInformado.getServicoTipoPrioridade());
				osGeradaAutomatica.setServicoTipoPrioridadeAtual(servicoTipoInformado.getServicoTipoPrioridade());

			}

			osGeradaAutomatica.setServicoTipo(servicoTipoInformado);

			if(servicoTipoInformado.getServicoTipoReferencia() != null
							&& servicoTipoInformado.getServicoTipoReferencia().getServicoTipo() != null){

				osGeradaAutomatica.setServicoTipoReferencia(servicoTipoInformado.getServicoTipoReferencia().getServicoTipo());

			}

		}

		return osGeradaAutomatica;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso a Especificação possa gerar alguma ordem de Serviço (STEP_ICGERACAOORDEMSERVICO da
	 * tabela SOLICITACAO_TIPO_ESPECIFICACAO com o valor
	 * correspondente a um). (OPCIONAL) [SB0030] Gerar Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean gerarOrdemServicoOpcional(Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		boolean retorno = false;

		FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
		filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
						FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));

		Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = this.getControladorUtil().pesquisar(
						filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class.getName());

		if(!Util.isVazioOrNulo(colecaoEspecificacaoServicoTipo)){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0028] Atualizar Registro de Atendimento (REGISTRO_ATENDIMENTO)
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @throws ControladorException
	 *             [CR21946] Correçao de falha Parametro de filtro de imoveis incorreto, gerando
	 *             erro
	 *             ao executar hql.
	 * @author Andre Nishimura
	 * @date 22/05/2009
	 */
	public Integer[] atualizarRegistroAtendimento(Integer idRA, short indicadorAtendimentoOnLine, String dataAtendimento,
					String horaAtendimento, String tempoEsperaInicial, String tempoEsperaFinal, Integer idMeioSolicitacao,
					Integer senhaAtendimento, Integer idSolicitacaoTipoEspecificacao, String dataPrevista, String observacao,
					Integer idImovel, String descricaoLocalOcorrencia, Integer idSolicitacaoTipo, Collection colecaoEndereco,
					String pontoReferenciaLocalOcorrencia, Integer idBairroArea, Integer idLocalidade, Integer idSetorComercial,
					Integer idQuadra, Integer idDivisaoEsgoto, Integer idLocalOcorrencia, Integer idPavimentoRua,
					Integer idPavimentoCalcada, Integer idUnidadeAtendimento, Usuario usuarioLogado, Integer imovelObrigatorio,
					Date ultimaAlteracao, Collection colecaoRASolicitante, Collection colecaoRASolicitanteRemovida,
					Collection<Integer> colecaoIdServicoTipo, Integer especificacaoNaBase, Collection<Conta> colecaoContas,
					String identificadores, ContaMotivoRevisao contaMotivoRevisao, String indicadorProcessoAdmJud,
					String numeroProcessoAgencia) throws ControladorException{

		// ******* Valida se o Imóvel ou Cliente têm Débitos ********************
		if(!Util.isVazioOrNulo(colecaoRASolicitante)){
			Iterator iteratorRASolicitante = colecaoRASolicitante.iterator();
			while(iteratorRASolicitante.hasNext()){
				RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteratorRASolicitante
								.next();

				this.verificarDebitosImovelCliente(idSolicitacaoTipoEspecificacao, idImovel, registroAtendimentoSolicitante.getCliente()
								.getId());
			}
		}
		// **********************************************************************

		RegistroAtendimento ra = new RegistroAtendimento();

		if(idImovel != null && !idImovel.equals("")){
			Integer idRAMesma = null;
			try{
				idRAMesma = repositorioRegistroAtendimento.verificarMesmaRA(idImovel, idRA);

			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(idRAMesma == null){
				getControladorOrdemServico().atualizarOsDaRA(idRA, idImovel);
			}
		}

		// controle de transação
		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();

		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idRA));

		Collection colecaoRA = getControladorUtil().pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

		if(colecaoRA == null || colecaoRA.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		RegistroAtendimento raNaBase = (RegistroAtendimento) colecaoRA.iterator().next();

		if(raNaBase.getUltimaAlteracao().after(ultimaAlteracao)){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		if(raNaBase.getNumeroImovel() != null) ra.setNumeroImovel(raNaBase.getNumeroImovel());

		if(raNaBase.getRegistroAtendimentoReativacao() != null) ra.setRegistroAtendimentoReativacao(raNaBase
						.getRegistroAtendimentoReativacao());

		Integer[] retorno = null;

		/*
		 * Verifica se e necessário geração automática de uma ordem de Serviço
		 */
		Collection<OrdemServico> colecaoOsGeradaAutomatica = null;
		if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao) && !idSolicitacaoTipoEspecificacao.equals(especificacaoNaBase)){

			retorno = new Integer[2];

			colecaoOsGeradaAutomatica = this.gerarColecaoOrdemServicoAutomatica(colecaoIdServicoTipo, idSolicitacaoTipo);
		}else{

			retorno = new Integer[1];
		}

		// fim controle transação

		// seta o id do registro atendimento
		ra.setId(idRA);

		// joga id do registro de atendimento no array de retorno
		retorno[0] = ra.getId();

		// última alteração
		ra.setUltimaAlteracao(new Date());

		ra.setIndicadorAtendimentoOnline(indicadorAtendimentoOnLine);

		// Data e Hora do atendimento
		String dataHoraAtendimento = dataAtendimento + " " + horaAtendimento + ":00";
		Date dataHoraAtendimentoObjetoDate = Util.converteStringParaDateHora(dataHoraAtendimento);

		ra.setRegistroAtendimento(dataHoraAtendimentoObjetoDate);

		// Tempo de espera inicial
		if(tempoEsperaInicial != null && !tempoEsperaInicial.equals("")){
			tempoEsperaInicial = tempoEsperaInicial + ":00";
			Date dataEsperaInicial = Util.converteStringParaDateHora(dataAtendimento + " " + tempoEsperaInicial);
			ra.setDataInicioEspera(dataEsperaInicial);
			tempoEsperaFinal = tempoEsperaFinal + ":00";
			Date dataEsperaFinal = Util.converteStringParaDateHora(dataAtendimento + " " + tempoEsperaFinal);
			ra.setDataFimEspera(dataEsperaFinal);
		}else{
			ra.setDataInicioEspera(null);
			ra.setDataFimEspera(null);
		}

		// Meio de Solicitação
		MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
		meioSolicitacao.setId(idMeioSolicitacao);

		ra.setMeioSolicitacao(meioSolicitacao);

		// Senha Atendimento
		ra.setSenhaAtendimento(senhaAtendimento);

		// Especificação
		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
						.valueOf(idSolicitacaoTipoEspecificacao)));

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
						.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
										SolicitacaoTipoEspecificacao.class.getName()));

		if(solicitacaoTipoEspecificacao == null){
			throw new ControladorException("atencao.pesquisa_inexistente", null, "Solicitação Tipo Especificação");
		}

		ra.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

		// Data Original e Data Prevista
		Date dataPrevistaObjetoDate = Util.converteStringParaDateHora(dataPrevista + ":00");

		ra.setDataPrevistaOriginal(raNaBase.getDataPrevistaOriginal());
		ra.setDataPrevistaAtual(dataPrevistaObjetoDate);

		// Observação
		if(observacao != null && !observacao.equals("")){
			ra.setObservacao(observacao);
		}else{
			ra.setObservacao(null);
		}

		// Imóvel
		if(idImovel != null && !idImovel.equals("")){
			Imovel imovel = null;

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA);
			Collection<Imovel> colecaoImovel = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel != null && !colecaoImovel.isEmpty()){
				imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
			}

			if(imovel != null){
				ra.setImovel(imovel);
			}else{
				throw new ControladorException("atencao.pesquisa_inexistente", null, "Imovel " + idImovel);
			}
		}else{
			ra.setImovel(null);
		}

		/*
		 * Dados da Identificação do Local da ocorrência (caso a Descrição do
		 * Local da ocorrência esteja preenchida, todos os Dados da
		 * Identificação do Local da ocorrência devem ter o valor nulo; caso
		 * contrário, preencher de acordo com as regras abaixo)
		 */
		if(descricaoLocalOcorrencia != null && !descricaoLocalOcorrencia.equals("")){
			ra.setDescricaoLocalOcorrencia(descricaoLocalOcorrencia);

			// Código da Situação bloqueado
			ra.setCodigoSituacao(RegistroAtendimento.SITUACAO_BLOQUEADO);
			// seta os campos para null
			ra.setLogradouroBairro(null);
			ra.setLogradouroCep(null);
			ra.setComplementoEndereco(null);
			ra.setPontoReferencia(null);
			ra.setBairroArea(null);
			ra.setLocalidade(null);
			ra.setSetorComercial(null);
			ra.setQuadra(null);
			ra.setDivisaoEsgoto(null);
			ra.setLocalOcorrencia(null);
			ra.setPavimentoRua(null);
			ra.setPavimentoCalcada(null);
		}else{

			/*
			 * Caso a matrícula do Imóvel seja obrigatória (STEP_ICMATRICULA com
			 * o valor correspondente a um na tabela
			 * SOLICITACAO_TIPO_ESPECIFICACAO), nulo.
			 */
			boolean especificao = this.especificacaoExigeMatriculaImovel(solicitacaoTipoEspecificacao);

			if(especificao || (colecaoEndereco != null && !colecaoEndereco.isEmpty())){

				Imovel imovelEndereco = (Imovel) Util.retonarObjetoDeColecao(colecaoEndereco);

				// LogradouroBairro e LogradouroCep
				if(especificao || imovelEndereco.getId() != null){

					ra.setLogradouroBairro(null);
					ra.setLogradouroCep(null);

				}else{

					ra.setLogradouroBairro(imovelEndereco.getLogradouroBairro());
					ra.setLogradouroCep(imovelEndereco.getLogradouroCep());

				}

				// Complemento endereço
				if(imovelEndereco.getComplementoEndereco() != null && !imovelEndereco.getComplementoEndereco().equals("")){
					ra.setComplementoEndereco(imovelEndereco.getComplementoEndereco());
				}else{
					ra.setComplementoEndereco(null);
				}
			}else{
				// LogradouroBairro
				ra.setLogradouroBairro(null);
				// LogradouroCep
				ra.setLogradouroCep(null);
				ra.setComplementoEndereco(null);
			}

			// Ponto de Referência
			if(pontoReferenciaLocalOcorrencia != null && !pontoReferenciaLocalOcorrencia.equals("")){
				ra.setPontoReferencia(pontoReferenciaLocalOcorrencia);
			}else{
				ra.setPontoReferencia(null);
			}

			// Área do Bairro
			if(idBairroArea != null && !idBairroArea.equals("")){
				BairroArea bairroArea = new BairroArea();
				bairroArea.setId(idBairroArea);
				ra.setBairroArea(bairroArea);
			}else{
				ra.setBairroArea(null);
			}

			// Localidade
			if(idLocalidade != null && !idLocalidade.equals("")){
				Localidade localidade = new Localidade();
				localidade.setId(idLocalidade);
				ra.setLocalidade(localidade);
			}else{
				ra.setLocalidade(null);
			}

			// Setor Comercial
			if(idSetorComercial != null && !idSetorComercial.equals("")){
				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setId(idSetorComercial);
				ra.setSetorComercial(setorComercial);
			}else{
				ra.setSetorComercial(null);
			}

			// Quadra
			if(idQuadra != null && !idQuadra.equals("")){
				Quadra quadra = new Quadra();
				quadra.setId(idQuadra);
				ra.setQuadra(quadra);
			}else{
				ra.setQuadra(null);
			}

			// divisão de Esgoto
			if(idDivisaoEsgoto != null && !idDivisaoEsgoto.equals("")){
				DivisaoEsgoto divisaoEsgoto = new DivisaoEsgoto();
				divisaoEsgoto.setId(idDivisaoEsgoto);
				ra.setDivisaoEsgoto(divisaoEsgoto);
			}else{
				ra.setDivisaoEsgoto(null);
			}

			// Local ocorrência
			if(idLocalOcorrencia != null && !idLocalOcorrencia.equals("")){
				LocalOcorrencia localOcorrencia = new LocalOcorrencia();
				localOcorrencia.setId(idLocalOcorrencia);
				ra.setLocalOcorrencia(localOcorrencia);
			}else{
				ra.setLocalOcorrencia(null);
			}

			// Pavimento Rua
			if(idPavimentoRua != null && !idPavimentoRua.equals("")){
				PavimentoRua pavimentoRua = new PavimentoRua();
				pavimentoRua.setId(idPavimentoRua);
				ra.setPavimentoRua(pavimentoRua);
			}else{
				ra.setPavimentoRua(null);
			}

			// Pavimento Calçada
			if(idPavimentoCalcada != null && !idPavimentoCalcada.equals("")){
				PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
				pavimentoCalcada.setId(idPavimentoCalcada);
				ra.setPavimentoCalcada(pavimentoCalcada);
			}else{
				ra.setPavimentoCalcada(null);
			}

			// Código da Situação
			ra.setCodigoSituacao(RegistroAtendimento.SITUACAO_PENDENTE);
		}

		// Indicador Processo Adm Jud
		if(!Util.isVazioOuBranco(indicadorProcessoAdmJud)){
			ra.setIndicadorProcessoAdmJud(Short.parseShort(indicadorProcessoAdmJud));
		}else{
			ra.setIndicadorProcessoAdmJud(ConstantesSistema.NAO);
		}

		// Número do Processo na Agência
		if(!Util.isVazioOuBranco(numeroProcessoAgencia)){
			ra.setNumeroProcessoAgencia(numeroProcessoAgencia);
		}else{
			ra.setNumeroProcessoAgencia(null);
		}

		// ------------ REGISTRAR TRANSAÇÃO ------------

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_REGISTRO_ATENDIMENTO);

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(operacao.getId(), ra.getId(), new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setArgumentoValor(ra.getId());

		ra.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		ra.setOperacaoEfetuada(operacaoEfetuada);

		registradorOperacao.registrarOperacao(ra);

		// ------------ REGISTRAR TRANSAÇÃO ------------

		// atualizar o registro de atendimento
		this.getControladorUtil().atualizar(ra);

		// [SB0028] Atualizar Registro de Atendimento
		// (REGISTRO_ATENDIMENTO_UNIDADE)
		this.atualizarRegistroAtendimentoUnidade(ra.getId(), idUnidadeAtendimento, usuarioLogado.getId());

		/*
		 * [UC0408] Atualizar Registro de Atendimento [SB0027] Atualizar
		 * Solicitante do Registro de Atendimento
		 */
		this.atualizarRegistroAtendimentoSolicitante(colecaoRASolicitante, colecaoRASolicitanteRemovida);

		// [UC0430] - Gerar Ordem de Serviço
		if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao) && !Util.isVazioOrNulo(colecaoOsGeradaAutomatica)
						&& !idSolicitacaoTipoEspecificacao.equals(especificacaoNaBase)){
			Imovel imovel = null;
			Integer idOrdemServico = null;

			for(OrdemServico osGeradaAutomatica : colecaoOsGeradaAutomatica){
				// O Imóvel da OS será o mesmo do RA
				imovel = ra.getImovel();

				if(imovel != null){
					osGeradaAutomatica.setImovel(imovel);
				}

				osGeradaAutomatica.setRegistroAtendimento(ra);

				Object[] dadosDaRA = this.pesquisarDadosLocalizacaoRegistroAtendimento(ra.getId());
				Integer idBairro = null;
				if(dadosDaRA != null){
					if(idLocalidade == null){
						idLocalidade = (Integer) dadosDaRA[0];
					}
					if(idSetorComercial == null){
						idSetorComercial = (Integer) dadosDaRA[1];
					}
					idBairro = (Integer) dadosDaRA[2];
				}

				idOrdemServico = this.getControladorOrdemServico().gerarOrdemServico(osGeradaAutomatica, usuarioLogado, null, idLocalidade,
								idSetorComercial, idBairro, idUnidadeAtendimento, null, null, null, false, null);

				// Definido pelo Analista que não tem problema retornar apenas um dos Ids
				retorno[1] = idOrdemServico;

				// [SB0011] – Gerar Trâmite de O.S.
				/*
				 * this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial,
				 * idUnidadeAtendimento, null, null, usuarioLogado,
				 * osGeradaAutomatica);
				 */
			}
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.intValue() == sistemaParametro.getParmId().intValue()){
			// 1. Caso a especificação informada para o RA tenha indicativo que é para colocar
			// contas em revisão (STEP_ICCOLOCACONTASEMREVISAO da tabela
			// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1))
			if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())
							&& solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao().intValue() == ConstantesSistema.SIM.intValue()){

				if(colecaoContas != null && !colecaoContas.isEmpty()){
					if(!Util.isVazioOuBranco(identificadores)){
						if(contaMotivoRevisao == null){
							throw new ControladorException("atencao.informe_campo", null, "Motivo da Revisão");
						}

						getControladorFaturamento().colocarRevisaoConta(colecaoContas, identificadores, contaMotivoRevisao, usuarioLogado);
					}
				}
			}
		}

		return retorno;

	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0028] Atualiza Registro de Atendimento (REGISTRO_ATENDIMENTO_UNIDADE)
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @throws ControladorException
	 */
	public void atualizarRegistroAtendimentoUnidade(Integer idRa, Integer idUnidadeAtendimento, Integer idUsuarioLogado)
					throws ControladorException{

		RegistroAtendimentoUnidade raUnidade = null;
		try{
			raUnidade = repositorioRegistroAtendimento.pesquisarRAUnidade(idRa);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		if(raUnidade.getUnidadeOrganizacional() != null && !raUnidade.getUnidadeOrganizacional().equals(idUnidadeAtendimento)){

			UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
			unidadeOrganizacional.setId(idUnidadeAtendimento);
			raUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
			// última Alteração
			raUnidade.setUltimaAlteracao(new Date());
			this.getControladorUtil().atualizar(raUnidade);
		}

	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [SB0027] Atualizar Solicitante do Registro de Atendimento
	 * (REGISTRO_ATENDIMENTO_SOLICITANTE)
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @throws ControladorException
	 */
	public void atualizarRegistroAtendimentoSolicitante(Collection colecaoRASolicitante, Collection colecaoRASolicitanteRemovida)
					throws ControladorException{

		Collection colecaoSolicitacaoFone = null;

		if(colecaoRASolicitante != null && !colecaoRASolicitante.isEmpty()){
			Iterator itRASolicitante = colecaoRASolicitante.iterator();
			while(itRASolicitante.hasNext()){
				RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) itRASolicitante.next();
				if(!(registroAtendimentoSolicitante.getSolicitanteFones() instanceof PersistentSet)){
					colecaoSolicitacaoFone = registroAtendimentoSolicitante.getSolicitanteFones();
				}
				// limpa o campo de fone no registro atendimento solicitante
				registroAtendimentoSolicitante.setSolicitanteFones(new HashSet());
				Integer idRASolicitante = registroAtendimentoSolicitante.getID();
				if(idRASolicitante != null){
					FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
					filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimentoSolicitante.ID,
									idRASolicitante));
					Collection colecaoRASolicitanteNaBase = getControladorUtil().pesquisar(filtroRegistroAtendimentoSolicitante,
									RegistroAtendimentoSolicitante.class.getName());

					if(colecaoRASolicitanteNaBase == null || colecaoRASolicitanteNaBase.isEmpty()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizacao.timestamp");
					}

					RegistroAtendimentoSolicitante raSolicitanteNaBase = (RegistroAtendimentoSolicitante) colecaoRASolicitanteNaBase
									.iterator().next();

					if(raSolicitanteNaBase.getUltimaAlteracao().after(registroAtendimentoSolicitante.getUltimaAlteracao())){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizacao.timestamp");
					}

					registroAtendimentoSolicitante.setUltimaAlteracao(new Date());

					getControladorUtil().atualizar(registroAtendimentoSolicitante);

				}else{
					registroAtendimentoSolicitante.setUltimaAlteracao(new Date());

					idRASolicitante = (Integer) getControladorUtil().inserir(registroAtendimentoSolicitante);
				}

				try{
					repositorioRegistroAtendimento.removerSolicitanteFone(registroAtendimentoSolicitante.getID());
				}catch(ErroRepositorioException ex){
					sessionContext.setRollbackOnly();
					ex.printStackTrace();
					throw new ControladorException("erro.sistema", ex);
				}
				if(colecaoSolicitacaoFone != null && !colecaoSolicitacaoFone.isEmpty()){
					Iterator iteSolicitacaoFone = colecaoSolicitacaoFone.iterator();
					while(iteSolicitacaoFone.hasNext()){
						ClienteFone clienteFone = (ClienteFone) iteSolicitacaoFone.next();

						if(clienteFone.getId() == null){
							SolicitanteFone solicitanteFone = new SolicitanteFone();
							solicitanteFone.setId(clienteFone.getId());
							solicitanteFone.setDdd(new Short(clienteFone.getDdd()));
							solicitanteFone.setFone(clienteFone.getTelefone());
							solicitanteFone.setRamal(clienteFone.getRamal());
							solicitanteFone.setIndicadorPadrao(clienteFone.getIndicadorTelefonePadrao());
							solicitanteFone.setFoneTipo(clienteFone.getFoneTipo());
							solicitanteFone.setUltimaAlteracao(clienteFone.getUltimaAlteracao());
							RegistroAtendimentoSolicitante registroAtendimentoSolicitanteNovo = new RegistroAtendimentoSolicitante();
							registroAtendimentoSolicitanteNovo.setID(idRASolicitante);
							solicitanteFone.setRegistroAtendimentoSolicitante(registroAtendimentoSolicitanteNovo);
							this.getControladorUtil().inserir(solicitanteFone);
						}
					}
				}
			}
		}

		if(colecaoRASolicitanteRemovida != null && !colecaoRASolicitanteRemovida.isEmpty()){
			Iterator itRASolicitanteRemovida = colecaoRASolicitanteRemovida.iterator();
			while(itRASolicitanteRemovida.hasNext()){
				RegistroAtendimentoSolicitante registroAtendimentoSolicitanteRemovida = (RegistroAtendimentoSolicitante) itRASolicitanteRemovida
								.next();
				registroAtendimentoSolicitanteRemovida.setSolicitanteFones(new HashSet());
				try{
					repositorioRegistroAtendimento.removerSolicitanteFone(registroAtendimentoSolicitanteRemovida.getID());
				}catch(ErroRepositorioException ex){
					sessionContext.setRollbackOnly();
					ex.printStackTrace();
					throw new ControladorException("erro.sistema", ex);
				}
				this.getControladorUtil().remover(registroAtendimentoSolicitanteRemovida);
			}
		}

	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0001] Define Data Prevista
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	public Date definirDataPrevistaRA(Date dataAtendimento, Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		Date retorno = null;

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();

		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						idSolicitacaoTipoEspecificacao));

		Collection colecaoSolicitacaoTipoEspecificacao = this.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
							.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);

			if(solicitacaoTipoEspecificacao.getHorasPrazo() != null){

				/*
				 * 1.1 Caso STEP_ICCALCDATAPREVISTAATEND da Tabela SOLICITACAO_TIPO_ESPECIFICACAO
				 * = 1(Dias úteis)
				 * 1.1.1. Data/Hora Prevista = Data/Hora válida obtida a partir da Data/Hora do
				 * Atendimento + número de horas previstos para a especificação do tipo de
				 * solicitação. (STEP_NNHORASPRAZO da tabela SOLICITACAO_TIPO_ESPECIFICACAO).
				 * Caso a Data calculada seja feriado nacional, estadual ou fim de semana (sábado ou
				 * domingo), obter próximo dia útil.
				 */
				if(solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento() != null
								&& solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento().intValue() == ConstantesSistema.SIM
												.intValue()){

					Date dataPrevistaUtil = Util
									.adicionarNumeroHorasAUmaData(dataAtendimento, solicitacaoTipoEspecificacao.getHorasPrazo());

					try{

						Collection<NacionalFeriado> colecaoferiadoNacional = repositorioCobranca.pesquisarFeriadoNacional(dataPrevistaUtil);
						boolean adiarData = true;

						while(adiarData){

							if(Util.ehDiaUtil(dataPrevistaUtil, colecaoferiadoNacional, null)){

								adiarData = false;

							}else{

								dataPrevistaUtil = Util.adicionarNumeroDiasDeUmaData(dataPrevistaUtil, 1);

							}

						}

						retorno = dataPrevistaUtil;

					}catch(ErroRepositorioException ex){
						ex.printStackTrace();
						throw new ControladorException("erro.sistema", ex);
					}

				}else{

					retorno = (Util.adicionarNumeroHorasAUmaData(dataAtendimento, solicitacaoTipoEspecificacao.getHorasPrazo()));
				}

			}else{

				retorno = (dataAtendimento);

			}

		}

		return retorno;

	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0001],[SB0002],[SB0003] Define Unidade Destino
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional definirUnidadeDestino(
	// Integer idSolicitacaoTipoEspecificacao, Integer idLocalidade,
	// Integer idSolicitacaoTipo,
	// boolean solicitacaoTipoRelativoAreaEsgoto, Integer idDivisaoEsgoto)
	// throws ControladorException {
	//
	// UnidadeOrganizacional retorno = null;
	//
	// SistemaParametro sistemaParametro = this.getControladorUtil()
	// .pesquisarParametrosDoSistema();
	//
	// try {
	// if (sistemaParametro.getIndicadorSugestaoTramite() != null
	// && sistemaParametro.getIndicadorSugestaoTramite().equals(
	// ConstantesSistema.INDICADOR_USO_ATIVO)) {
	//
	// UnidadeOrganizacional unidadeOrganizacionalEspecificacao = repositorioRegistroAtendimento
	// .obterUnidadeDestinoEspecificacao(idSolicitacaoTipoEspecificacao);
	// UnidadeOrganizacional unidadeOrganizacionalLocalidade = null;
	// UnidadeOrganizacional unidadeOrganizacionalDivisaoEsgoto = null;
	// if (unidadeOrganizacionalEspecificacao == null
	// && idLocalidade != null
	// && !solicitacaoTipoRelativoAreaEsgoto) {
	// unidadeOrganizacionalLocalidade = repositorioRegistroAtendimento
	// .definirUnidadeDestinoLocalidade(idLocalidade,
	// idSolicitacaoTipo);
	// }
	// if ((unidadeOrganizacionalLocalidade == null || solicitacaoTipoRelativoAreaEsgoto)
	// && idDivisaoEsgoto != null) {
	// unidadeOrganizacionalDivisaoEsgoto = repositorioRegistroAtendimento
	// .definirUnidadeDestinoDivisaoEsgoto(idDivisaoEsgoto);
	// }
	// if (unidadeOrganizacionalEspecificacao != null) {
	// retorno = unidadeOrganizacionalEspecificacao;
	// } else if (unidadeOrganizacionalLocalidade != null
	// && !solicitacaoTipoRelativoAreaEsgoto) {
	// retorno = unidadeOrganizacionalLocalidade;
	// } else if (unidadeOrganizacionalDivisaoEsgoto != null
	// && solicitacaoTipoRelativoAreaEsgoto) {
	// retorno = unidadeOrganizacionalDivisaoEsgoto;
	// }
	// }
	//
	// /*
	// * [FS0018] Verificarexistência de unidade centralizadora
	// */
	// if (retorno != null) {
	// UnidadeOrganizacional unidadeDestino = this
	// .getControladorUnidade()
	// .verificarExistenciaUnidadeCentralizadora(retorno);
	//
	// retorno = unidadeDestino;
	// }
	// } catch (ErroRepositorioException ex) {
	// ex.printStackTrace();
	// throw new ControladorException("erro.sistema", ex);
	// }
	//
	// return retorno;
	// }

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [SB0006]Incluir Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @throws ControladorException
	 */
	public Integer[] reativarRegistroAtendimento(RegistroAtendimento ra, Integer idUnidadeAtendimento, Integer idUsuarioLogado,
					Integer idCliente, Integer idRaSolicitante, Integer idUnidadeDestino, String parecerUnidadeDestino,
					Integer idSolicitacaoTipo) throws ControladorException{

		Integer[] retorno = null;

		Collection<OrdemServico> colecaoOsGeradaAutomatica = null;

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = ra.getSolicitacaoTipoEspecificacao();
		Integer idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();

		if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao)){
			retorno = new Integer[2];

			Collection<Integer> colecaoIdServicoTipo = this.getControladorOrdemServico()
							.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(idSolicitacaoTipoEspecificacao);

			colecaoOsGeradaAutomatica = this.gerarColecaoOrdemServicoAutomatica(colecaoIdServicoTipo, idSolicitacaoTipo);
		}else{

			retorno = new Integer[1];
		}

		ra.setId(this.obterSequenceRA());

		Integer idRAGerado = (Integer) this.getControladorUtil().inserir(ra);
		ra.setId(idRAGerado);

		retorno[0] = idRAGerado;

		// [SB0006] Inclui Registro de Atendimento
		// (REGISTRO_ATENDIMENTO_UNIDADE)
		this.inserirRegistroAtendimentoUnidade(ra, idUnidadeAtendimento, idUsuarioLogado);

		Collection colecaoEnderecoSolicitante = null;
		Collection colecaoFone = null;

		try{
			RegistroAtendimentoSolicitante raSolicitante = repositorioRegistroAtendimento
							.obterRegistroAtendimentoSolicitante(idRaSolicitante);

			if(raSolicitante.getCliente() == null){
				ClienteEndereco clienteEndereco = new ClienteEndereco();
				clienteEndereco.setNumero(raSolicitante.getNumeroImovel());
				clienteEndereco.setLogradouroBairro(raSolicitante.getLogradouroBairro());
				clienteEndereco.setLogradouroCep(raSolicitante.getLogradouroCep());

				colecaoEnderecoSolicitante = new ArrayList();
				colecaoEnderecoSolicitante.add(clienteEndereco);

				colecaoFone = setColecaoFone(idRaSolicitante, colecaoFone);
			}

			String pontoReferenciaSolicitante = raSolicitante.getPontoReferencia();

			String nomeSolicitante = raSolicitante.getSolicitante();

			boolean novoSolicitante = false;

			Integer idUnidadeSolicitante = null;
			if(raSolicitante.getUnidadeOrganizacional() != null){
				idUnidadeSolicitante = raSolicitante.getUnidadeOrganizacional().getId();
			}
			Integer idFuncionario = null;
			if(raSolicitante.getFuncionario() != null){
				idFuncionario = raSolicitante.getFuncionario().getId();
			}

			/*
			 * [UC0366] Inserir Registro de Atendimento [SB0007] Inclui
			 * Solicitante do Registro de Atendimento
			 */
			this.inserirRegistroAtendimentoSolicitante(idRAGerado, idCliente, colecaoEnderecoSolicitante, pontoReferenciaSolicitante,
							nomeSolicitante, novoSolicitante, idUnidadeSolicitante, idFuncionario, colecaoFone, null, null, null, null,
							null, null);

			// [SB0008] Gerar Trâmite
			Tramite tramite = new Tramite();

			// Registro Atendimento
			tramite.setRegistroAtendimento(ra);

			// Unidade Origem = Unidade Atendimento
			UnidadeOrganizacional unidadeOrigem = new UnidadeOrganizacional();
			unidadeOrigem.setId(idUnidadeAtendimento);

			tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);

			/*
			 * Caso a Unidade Destino esteja preenchida, Id da Unidade Destino;
			 * caso contrário, Id da Unidade de Atendimento
			 */
			if(idUnidadeDestino != null){
				UnidadeOrganizacional unidadeDestino = new UnidadeOrganizacional();
				unidadeDestino.setId(idUnidadeDestino);

				tramite.setUnidadeOrganizacionalDestino(unidadeDestino);
			}else{
				tramite.setUnidadeOrganizacionalDestino(unidadeOrigem);
			}

			// usuário Logado = Registro e Responsável
			Usuario usuario = new Usuario();
			usuario.setId(idUsuarioLogado);

			tramite.setUsuarioRegistro(usuario);
			tramite.setUsuarioResponsavel(usuario);

			/*
			 * Caso o Parecer para a Unidade Destino esteja preenchido, Parecer
			 * para a Unidade Destino; caso contrário, tramite gerado pelo
			 * sistema na abertura do registro de atendimento.
			 */
			if(!Util.isVazioOuBranco(parecerUnidadeDestino)){
				tramite.setParecerTramite(parecerUnidadeDestino);
			}else{
				tramite.setParecerTramite(Tramite.TRAMITE_GERADO_PELO_SISTEMA_ABERTURA_RA);
			}

			tramite.setDataTramite(new Date());
			tramite.setUltimaAlteracao(new Date());

			// [UC0427] Tramitar Registro de Atendimento
			this.tramitar(tramite, null);

			// [UC0430] - Gerar Ordem de Serviço
			if(this.gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao) && !Util.isVazioOrNulo(colecaoOsGeradaAutomatica)){
				Imovel imovel = null;
				Integer idOrdemServico = null;

				Integer idLocalidade = null;
				Localidade localidade = ra.getLocalidade();

				if(localidade != null){
					idLocalidade = localidade.getId();
				}

				Integer idSetorComercial = null;
				SetorComercial setorComercial = ra.getSetorComercial();

				if(setorComercial != null){
					idSetorComercial = setorComercial.getId();
				}

				for(OrdemServico osGeradaAutomatica : colecaoOsGeradaAutomatica){
					// O Imóvel da OS será o mesmo do RA
					imovel = ra.getImovel();

					if(imovel != null){
						osGeradaAutomatica.setImovel(imovel);
					}

					osGeradaAutomatica.setRegistroAtendimento(ra);

					Object[] dadosDaRA = this.pesquisarDadosLocalizacaoRegistroAtendimento(ra.getId());
					Integer idBairro = null;
					if(dadosDaRA != null){
						if(idLocalidade == null){
							idLocalidade = (Integer) dadosDaRA[0];
						}
						if(idSetorComercial == null){
							idSetorComercial = (Integer) dadosDaRA[1];
						}
						idBairro = (Integer) dadosDaRA[2];
					}

					idOrdemServico = this.getControladorOrdemServico().gerarOrdemServico(osGeradaAutomatica, usuario, null, idLocalidade,
									idSetorComercial, idBairro, idUnidadeAtendimento, null, null, null, false, null);

					// Definido pelo Analista que não tem problema retornar apenas um dos Ids
					retorno[1] = idOrdemServico;

					// [SB0011] – Gerar Trâmite de O.S.
					/*
					 * this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial,
					 * idUnidadeAtendimento, idUnidadeDestino,
					 * parecerUnidadeDestino, usuario, osGeradaAutomatica);
					 */
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @throws ControladorException
	 */
	private Collection setColecaoFone(Integer idRaSolicitante, Collection colecaoFone) throws ErroRepositorioException{

		Collection colecaoSolicitanteFone = repositorioRegistroAtendimento.pesquisarFoneSolicitante(idRaSolicitante);

		Iterator itaratorSolicitanteFone = colecaoSolicitanteFone.iterator();
		ClienteFone clienteFone = null;
		SolicitanteFone solicitanteFone = null;

		while(itaratorSolicitanteFone.hasNext()){
			solicitanteFone = (SolicitanteFone) itaratorSolicitanteFone.next();

			clienteFone = new ClienteFone();

			// Tipo do Telefone
			clienteFone.setFoneTipo(solicitanteFone.getFoneTipo());

			// DDD
			clienteFone.setDdd(solicitanteFone.getDdd().toString());

			// Telefone
			clienteFone.setTelefone(solicitanteFone.getFone());

			// Ramal
			if(solicitanteFone.getRamal() != null){
				clienteFone.setRamal(solicitanteFone.getRamal());
			}

			// Indicador Fone Padrao
			clienteFone.setIndicadorTelefonePadrao(solicitanteFone.getIndicadorPadrao());

			// Ultima alteração
			clienteFone.setUltimaAlteracao(new Date());

			colecaoFone = new ArrayList();
			colecaoFone.add(clienteFone);
		}
		return colecaoFone;
	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * [FS0001]Valida possibilidade de reativação
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public void validaPossibilidadeReativacaoRA(Integer idRegistroAtendimento, Integer idUsuario) throws ControladorException{

		// [UC0421] Obter Unidade de Atendimento do RA
		/*
		 * UnidadeOrganizacional unidadeAtendimento = this
		 * .obterUnidadeAtendimentoRA(idRegistroAtendimento);
		 */

		// Colocado por Raphael Rossiter em 12/03/2007
		// [UC0418] - Obter Unidade Atual do RA
		UnidadeOrganizacional unidadeOrganizacional = obterUnidadeAtualRA(idRegistroAtendimento);

		if(unidadeOrganizacional == null){
			throw new ControladorException("atencao.unidade_nao_encontrada");
		}

		// [UC0419] - Obter Indicador de Autorização para Manutenção do RA
		Short indicadorAutorizacao = this.obterIndicadorAutorizacaoManutencaoRA(unidadeOrganizacional.getId(), idUsuario);

		/*
		 * Caso a reativação de RA não esteja liberada para qualquer usuário
		 * da empresa (PASI_VLPARAMETRO com o valor 2 (NÃO) na tabela PARAMETRO_SISTEMA
		 * com PASI_CDPARAMETRO=”PARM_ICLIBERARREATIVACAORA”)
		 */
		if(ParametroAtendimentoPublico.P_INDICADOR_LIBERAR_REATIVACAO_RA.executar().equals(ConstantesSistema.NAO.toString())){
			/*
			 * Caso o indicador de autorização para manutenção do RA retornado pelo
			 * UC0419 esteja com valor correspondente a 2-não, exibir a mensagem:
			 * "Este RA foi aberto pela unidade <<unid_dsunidade>>. não possível
			 * reati-lo."
			 */
			if(indicadorAutorizacao == null || indicadorAutorizacao.equals(ConstantesSistema.NAO)){
				throw new ControladorException("atencao.unidade_nao_autorizada", null, unidadeOrganizacional.getDescricao());
			}
		}

		// [UC0420] Obter Descrição da situação da RA
		ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = this.obterDescricaoSituacaoRA(idRegistroAtendimento);
		/*
		 * Caso o RA não esteja encerrado, exibir a mensagem "O Registro de
		 * Atendimento<<rgat_id>> está <<Descrição da situação do RA>>. não
		 * possível reativá-lo." (ra não encerrado - rgat_cdsituacao com valor
		 * correspondente diferente de dois)
		 */
		if(obterDescricaoSituacaoRAHelper.getCodigoSituacao() == null
						|| !obterDescricaoSituacaoRAHelper.getCodigoSituacao().equals(RegistroAtendimento.SITUACAO_ENCERRADO)){
			throw new ControladorException("atencao.ra_nao_encerrado", null,
							new String[] {"" + idRegistroAtendimento, obterDescricaoSituacaoRAHelper.getDescricaoSituacao()});
		}
		// [UC0433] Obter Registro de Atendimento Associado
		ObterRAAssociadoHelper obterRAAssociadoHelper = this.obterRAAssociado(idRegistroAtendimento);
		/*
		 * Caso o RA seja duplicidade de outro registro de atendimento, exibir a
		 * mensagem "O Registro de Atendimento <<rgat_id>> duplicidade do
		 * Registro de Atendimento <<id do ra associado>>. não é possível
		 * reativá-lo." (ra duplicidade de outro registro de atendimento- Código
		 * de existência de RA Associado com o valor correspondente a um)
		 */
		if(obterRAAssociadoHelper == null
						|| obterRAAssociadoHelper.getCodigoExistenciaRAAssociado().equals(
										RegistroAtendimento.CODIGO_ASSOCIADO_RA_REFERENCIA)){
			throw new ControladorException("atencao.ra_duplicidade", null, new String[] {"" + idRegistroAtendimento, obterRAAssociadoHelper
							.getRegistroAtendimentoAssociado().getId().toString()});
		}
		/*
		 * Caso o RA já tenha sido reativado, exibir a mensagem "O Registro de
		 * Atendimento <<rgat_id>> foi reativado. O Registro de Atendimento
		 * Atual o de número <<id de ra associado>>. não possível reativá-lo
		 * novamente." (ra reativado - Código de existência de RA Associado com
		 * o valor correspondente a dois)
		 */
		if(obterRAAssociadoHelper == null
						|| obterRAAssociadoHelper.getCodigoExistenciaRAAssociado().equals(RegistroAtendimento.CODIGO_ASSOCIADO_RA_ATUAL)){
			throw new ControladorException("atencao.ra_reativado", null, new String[] {"" + idRegistroAtendimento, obterRAAssociadoHelper
							.getRegistroAtendimentoAssociado().getId().toString()});
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * SOTP_ICFALTAAGUA da tabela SOLICITACAO_TIPO com o valor correspondente a
	 * um e STEP_ICMATRICULA com o valor correspondente a dois na tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO).
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @return SolicitacaoTipoEspecificacao
	 * @throws ControladorException
	 */
	public SolicitacaoTipoEspecificacao pesquisarTipoEspecificacaoFaltaAguaGeneralizada() throws ControladorException{

		SolicitacaoTipoEspecificacao retorno = null;
		SolicitacaoTipo solicitacaoTipo = null;

		Collection colecaoPesquisa = null;

		try{
			colecaoPesquisa = repositorioRegistroAtendimento.pesquisarTipoEspecificacaoFaltaAguaGeneralizada();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator raIterator = colecaoPesquisa.iterator();

			Object[] arrayPesquisa = (Object[]) raIterator.next();

			solicitacaoTipo = new SolicitacaoTipo();
			solicitacaoTipo.setId((Integer) arrayPesquisa[0]);

			retorno = new SolicitacaoTipoEspecificacao();
			retorno.setId((Integer) arrayPesquisa[1]);

			retorno.setSolicitacaoTipo(solicitacaoTipo);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 06/09/2006
	 */
	public Tramite recuperarTramiteMaisAtualPorUnidadeDestino(Integer idUnidade) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.recuperarTramiteMaisAtualPorUnidadeDestino(idUnidade);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisa os fones do regsitro atendimento solicitante e joga na coleção
	 * de ClientesFones
	 * 
	 * @author Sávio Luiz
	 * @date 05/09/2006
	 * @return idRASolicitante
	 * @throws ControladorException
	 */
	public Collection pesquisarParmsFoneRegistroAtendimentoSolicitante(Integer idRASolicitante) throws ControladorException{

		Collection colecaoParmsSolicitanteFone = null;
		try{
			colecaoParmsSolicitanteFone = repositorioRegistroAtendimento.pesquisarParmsFoneRegistroAtendimentoSolicitante(idRASolicitante);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		Collection colecaoClientesFone = null;

		if(colecaoParmsSolicitanteFone != null && !colecaoParmsSolicitanteFone.isEmpty()){
			colecaoClientesFone = new ArrayList();
			Iterator iteColecaoParmsRASolicitante = colecaoParmsSolicitanteFone.iterator();
			while(iteColecaoParmsRASolicitante.hasNext()){
				Object[] parsmRASolicitante = (Object[]) iteColecaoParmsRASolicitante.next();
				ClienteFone clienteFone = new ClienteFone();

				if(parsmRASolicitante[0] != null){
					// seta o DDD
					clienteFone.setDdd("" + (Short) parsmRASolicitante[0]);
				}
				if(parsmRASolicitante[1] != null){
					// seta o número do telefone
					clienteFone.setTelefone((String) parsmRASolicitante[1]);
				}
				if(parsmRASolicitante[2] != null){
					// seta o ramal do telefone
					clienteFone.setRamal((String) parsmRASolicitante[2]);
				}
				if(parsmRASolicitante[3] != null){
					// indicador padrão
					clienteFone.setIndicadorTelefonePadrao((Short) parsmRASolicitante[3]);
				}
				if(parsmRASolicitante[4] != null){
					// id fone tipo
					FoneTipo foneTipo = new FoneTipo();
					foneTipo.setId((Integer) parsmRASolicitante[4]);
					foneTipo.setDescricao((String) parsmRASolicitante[5]);
					clienteFone.setFoneTipo(foneTipo);
				}
				if(parsmRASolicitante[6] != null){
					// ultima alteração
					clienteFone.setUltimaAlteracao((Date) parsmRASolicitante[6]);
				}

				colecaoClientesFone.add(clienteFone);
			}
		}
		return colecaoClientesFone;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * [FS0030] Verificar preenchimento dos dados de identificação do
	 * solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 07/09/2006
	 * @throws ControladorException
	 */
	public void verificaDadosSolicitanteAtualizar(Integer idCliente, Integer idUnidadeSolicitante, Integer idFuncionario,
					String nomeSolicitante, Collection colecaoEndereco, Collection colecaoFone, Short indicadorClienteEspecificacao,
					Integer idImovel, Integer idRegistroAtendimento, Integer idRASolicitante) throws ControladorException{

		// Validação de campos requeridos
		if(idCliente == null && idUnidadeSolicitante == null && idFuncionario == null && nomeSolicitante == null){
			throw new ControladorException("atencao.dados_solicitante_nao_informado");
		}

		if(indicadorClienteEspecificacao != null && indicadorClienteEspecificacao.equals(ConstantesSistema.INDICADOR_USO_ATIVO)
						&& idCliente == null){
			throw new ControladorException("atencao.required", null, "Cliente");
		}

		if(idFuncionario != null && idUnidadeSolicitante == null){
			throw new ControladorException("atencao.required", null, "Unidade Solicitante");
		}

		if(idFuncionario == null && idUnidadeSolicitante != null){
			throw new ControladorException("atencao.required", null, "Funcionário Responsável");
		}

		if(idCliente == null && (colecaoEndereco == null || colecaoEndereco.isEmpty())){
			throw new ControladorException("atencao.required", null, "endereço");
		}

		if(idCliente != null){

			if(idRegistroAtendimento != null){
				// [FS0012] Verificar existência do cliente solicitante
				this.verificarExistenciaClienteSolicitanteAtualizar(idRegistroAtendimento, idCliente, idRASolicitante);
			}

			FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
			filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idRegistroAtendimento));
			filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);

			RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroRegistroAtendimento, RegistroAtendimento.class.getName()));

			// [FS0027] Verificar informação do Imóvel
			if((registroAtendimento == null) || (registroAtendimento.getSolicitacaoTipoEspecificacao() == null)
							|| (registroAtendimento.getSolicitacaoTipoEspecificacao().getIndicadorCliente().equals(ConstantesSistema.SIM))){
				this.verificarInformacaoImovel(idCliente, idImovel, true);
			}else{
				Cliente cliente = null;
				if(idCliente != null){
					cliente = getControladorCliente().pesquisarClienteDigitado(idCliente);
				}

				if(cliente == null){
					throw new ActionServletException("atencao.cliente.inexistente", null, idImovel.toString());
				}
			}
		}

		if(idUnidadeSolicitante != null){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeSolicitante));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){

				throw new ControladorException("atencao.label_inexistente", null, "Unidade Solicitante");

			}else if(idRegistroAtendimento != null){

				// [FS0018] Verificar existência da unidade solicitante
				this.verificarExistenciaUnidadeSolicitanteAtualizar(idRegistroAtendimento, idUnidadeSolicitante, idRASolicitante);
			}
		}

		if(idFuncionario != null){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, idUnidadeSolicitante));

			Collection colecaoFuncionario = this.getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				throw new ControladorException("atencao.label_inexistente", null, "Funcionário Responsável");

			}
		}
	}

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado e o registro
	 * atendimento solicitante for diferente do que está sendo atualizado).
	 * [FS0027] Verificar existência do cliente solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public void verificarExistenciaClienteSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idCliente, Integer idRASolicitante)
					throws ControladorException{

		Integer qtdRegistros = null;

		try{
			qtdRegistros = this.repositorioRegistroAtendimento.verificarExistenciaClienteSolicitanteAtualizar(idRegistroAtendimento,
							idCliente, idRASolicitante);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(qtdRegistros != null && qtdRegistros > 0){
			throw new ControladorException("atencao.cliente_solicitante_ja_informado_registro_atendimento", null, idCliente.toString(),
							idRegistroAtendimento.toString());
		}

	}

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada e RASO_ID<>id
	 * do Registro atendimento solicitante).
	 * [FS0018] Verificar existência da unidade solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 07/09/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public void verificarExistenciaUnidadeSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idUnidade, Integer idRASolicitante)
					throws ControladorException{

		Integer qtdRegistros = null;

		try{
			qtdRegistros = this.repositorioRegistroAtendimento.verificarExistenciaUnidadeSolicitanteAtualizar(idRegistroAtendimento,
							idUnidade, idRASolicitante);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(qtdRegistros != null && qtdRegistros > 0){
			throw new ControladorException("atencao.unidade_solicitante_ja_informado_registro_atendimento", null, idUnidade.toString(),
							idRegistroAtendimento.toString());
		}

	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 13/09/2006
	 * @param idRa
	 * @return dataPrevisaoAtual
	 * @throws ControladorException
	 */
	public Date obterDataAgenciaReguladoraPrevisaoAtual(Integer idRa) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.obterDataAgenciaReguladoraPrevisaoAtual(idRa);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Consultar os registros de atendimento do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 25/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarRegistroAtendimentoImovel(Integer idImovel, String situacao) throws ControladorException{

		Collection colecaoRegistroAtendimento = null;
		try{
			colecaoRegistroAtendimento = repositorioRegistroAtendimento.consultarRegistroAtendimentoImovel(idImovel, situacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		Collection registrosAtendimentos = null;

		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

			registrosAtendimentos = new ArrayList();

			Iterator iteColecaoRegistroAtendimento = colecaoRegistroAtendimento.iterator();

			RegistroAtendimento registroAtendimento = null;
			while(iteColecaoRegistroAtendimento.hasNext()){
				Object[] array = (Object[]) iteColecaoRegistroAtendimento.next();

				registroAtendimento = new RegistroAtendimento();
				if(array[0] != null){
					// seta o id
					registroAtendimento.setId((Integer) array[0]);
				}

				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
				if(array[1] != null){
					solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
					SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
					solicitacaoTipo.setDescricao((String) array[1]);

					solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
					// seta a descricao da solicitacao Tipo
					registroAtendimento.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
				}
				if(array[2] != null){
					if(solicitacaoTipoEspecificacao == null){
						solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
					}
					solicitacaoTipoEspecificacao.setDescricao((String) array[2]);

					// seta a descricao da solicitacao Tipo Especificacao
					registroAtendimento.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
				}
				if(array[3] != null){
					// tm registro atendimento
					registroAtendimento.setRegistroAtendimento((Date) array[3]);
				}
				registrosAtendimentos.add(registroAtendimento);
			}
		}
		return registrosAtendimentos;

	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0025] Verifica Registro de Atendimento de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoFaltaAguaGeneralizadaHelper verificarRegistroAtendimentoFaltaAguaGeneralizafa(Integer idEspecificacao,
					Integer idBairroArea) throws ControladorException{

		RegistroAtendimentoFaltaAguaGeneralizadaHelper retorno = null;

		// Caso esteja abrindo um registro de atendimento de falta de Água
		// generalizada
		SolicitacaoTipoEspecificacao especificacaoFaltaAguaGeneralizada = this.pesquisarTipoEspecificacaoFaltaAguaGeneralizada();

		if(especificacaoFaltaAguaGeneralizada != null && especificacaoFaltaAguaGeneralizada.getId().equals(idEspecificacao)
						&& idBairroArea != null){

			Collection colecaoRAFaltaAgua = null;

			try{
				colecaoRAFaltaAgua = repositorioRegistroAtendimento.pesquisarRAFaltaAguaGeneralizada(idBairroArea);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(colecaoRAFaltaAgua != null && !colecaoRAFaltaAgua.isEmpty()){

				retorno = new RegistroAtendimentoFaltaAguaGeneralizadaHelper();

				Collection colecaoRegistroAtendimentoRetorno = new ArrayList();

				RegistroAtendimento ra = null;

				Iterator raIterator = colecaoRAFaltaAgua.iterator();

				Object[] arrayRA = null;

				boolean primeiroRegistro = true;

				while(raIterator.hasNext()){

					ra = new RegistroAtendimento();

					arrayRA = (Object[]) raIterator.next();

					// endereço e SolicitacaoTipoEspecificacao
					if(primeiroRegistro){

						SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
						solicitacaoTipo.setId((Integer) arrayRA[25]);
						solicitacaoTipo.setDescricao("" + arrayRA[26]);

						SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
						solicitacaoTipoEspecificacao.setId((Integer) arrayRA[24]);
						solicitacaoTipoEspecificacao.setDescricao((String) arrayRA[9]);

						solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

						Bairro bairro = new Bairro();
						bairro.setCodigo(((Integer) arrayRA[32]).intValue());
						bairro.setNome((String) arrayRA[33]);

						BairroArea bairroArea = new BairroArea();
						bairroArea.setId((Integer) arrayRA[30]);
						bairroArea.setNome((String) arrayRA[31]);
						bairroArea.setBairro(bairro);

						// Carregando o Área do bairro e a Especificação no
						// objeto de retorno
						retorno.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
						retorno.setBairroArea(bairroArea);

						primeiroRegistro = false;
					}

					if(arrayRA[20] != null && arrayRA[21] != null){

						LogradouroCep logradouroCep = null;
						if(arrayRA[20] != null){

							logradouroCep = new LogradouroCep();
							logradouroCep.setId((Integer) arrayRA[20]);

							// id do Logradouro
							Logradouro logradouro = null;
							if(arrayRA[19] != null){
								logradouro = new Logradouro();
								logradouro.setId((Integer) arrayRA[19]);
								logradouro.setNome("" + arrayRA[0]);
							}
							LogradouroTipo logradouroTipo = null;
							// Descrição logradouro tipo
							if(arrayRA[22] != null){
								logradouroTipo = new LogradouroTipo();
								logradouroTipo.setDescricaoAbreviada("" + arrayRA[22]);
								logradouro.setLogradouroTipo(logradouroTipo);
							}
							LogradouroTitulo logradouroTitulo = null;
							// Descrição logradouro titulo
							if(arrayRA[23] != null){
								logradouroTitulo = new LogradouroTitulo();
								logradouroTitulo.setDescricaoAbreviada("" + arrayRA[23]);
								logradouro.setLogradouroTitulo(logradouroTitulo);
							}
							// id do CEP
							Cep cep = null;
							if(arrayRA[10] != null){
								cep = new Cep();
								cep.setCepId((Integer) arrayRA[10]);
								cep.setCodigo((Integer) arrayRA[16]);
							}

							logradouroCep.setLogradouro(logradouro);
							logradouroCep.setCep(cep);
						}

						ra.setLogradouroCep(logradouroCep);

						LogradouroBairro logradouroBairro = null;
						if(arrayRA[21] != null){

							logradouroBairro = new LogradouroBairro();
							logradouroBairro.setId((Integer) arrayRA[21]);

							Bairro bairro = null;
							// nome bairro
							if(arrayRA[3] != null){
								bairro = new Bairro();
								bairro.setId((Integer) arrayRA[3]);
								bairro.setCodigo((Integer) arrayRA[17]);
								bairro.setNome("" + arrayRA[4]);
							}

							Municipio municipio = null;
							// nome municipio
							if(arrayRA[5] != null){
								municipio = new Municipio();
								municipio.setId((Integer) arrayRA[5]);
								municipio.setNome("" + arrayRA[6]);

								// id da unidade federação
								if(arrayRA[7] != null){
									UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
									unidadeFederacao.setId((Integer) arrayRA[7]);
									unidadeFederacao.setSigla("" + arrayRA[8]);
									municipio.setUnidadeFederacao(unidadeFederacao);
								}

								bairro.setMunicipio(municipio);
							}

							logradouroBairro.setBairro(bairro);
						}

						ra.setLogradouroBairro(logradouroBairro);
					}

					// Id
					ra.setId((Integer) arrayRA[27]);

					// complemento endereço
					if(arrayRA[18] != null){
						ra.setComplementoEndereco("" + arrayRA[18]);
					}

					// pontoReferencia
					if(arrayRA[28] != null){
						ra.setPontoReferencia("" + arrayRA[28]);
					}

					// Data do RegistroAtendimento
					if(arrayRA[29] != null){
						ra.setRegistroAtendimento((Date) arrayRA[29]);
					}

					// id do Imóvel
					if(arrayRA[34] != null){
						Imovel imovel = this.getControladorEndereco().pesquisarImovelParaEndereco((Integer) arrayRA[34]);
						ra.setImovel(imovel);
					}

					colecaoRegistroAtendimentoRetorno.add(ra);
				}

				retorno.setColecaoRegistroAtendimento(colecaoRegistroAtendimentoRetorno);
			}
		}

		return retorno;
	}

	/**
	 * Faz o controle de concorrencia de registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @throws ControladorException
	 */
	private void verificarRegistroAtendimentoControleConcorrencia(RegistroAtendimento registroAtendimento) throws ControladorException{

		FiltroRegistroAtendimento filtroRA = new FiltroRegistroAtendimento();
		filtroRA.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, registroAtendimento.getId()));

		Collection colecaoRA = getControladorUtil().pesquisar(filtroRA, RegistroAtendimento.class.getName());

		if(colecaoRA == null || colecaoRA.isEmpty()){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		RegistroAtendimento raAtual = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecaoRA);

		if(raAtual.getUltimaAlteracao().after(registroAtendimento.getUltimaAlteracao())){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0015] Verifica Registro de Atendimento Encerrado para o Local da
	 * ocorrência
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2006
	 * @throws ControladorException
	 */
	public RegistroAtendimentoEncerradoLocalOcorrenciaHelper verificarRegistroAtendimentoEncerradoLocalOcorrencia(Integer idImovel,
					Integer idEspecificacao, Integer idLogradouroBairro, Integer idLogradouroCep) throws ControladorException{

		RegistroAtendimentoEncerradoLocalOcorrenciaHelper retorno = null;

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		Date dataReativacao = new Date();
		dataReativacao = Util.subtrairNumeroDiasDeUmaData(dataReativacao, sistemaParametro.getDiasReativacao().intValue());

		Collection colecaoRAEncerrado = null;

		if(idImovel != null){

			try{
				colecaoRAEncerrado = repositorioRegistroAtendimento.pesquisarRAEncerradoImovel(idImovel, idEspecificacao, dataReativacao);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(colecaoRAEncerrado != null && !colecaoRAEncerrado.isEmpty()){

				retorno = new RegistroAtendimentoEncerradoLocalOcorrenciaHelper();

				Collection colecaoRegistroAtendimentoRetorno = new ArrayList();

				RegistroAtendimento ra = null;
				Imovel objetoImovel = null;

				Iterator raIterator = colecaoRAEncerrado.iterator();

				Object[] arrayRA = null;

				boolean primeiroRegistro = true;

				while(raIterator.hasNext()){

					ra = new RegistroAtendimento();
					objetoImovel = new Imovel();

					arrayRA = (Object[]) raIterator.next();

					// Imovel
					objetoImovel.setId((Integer) arrayRA[30]);

					// SolicitacaoTipoEspecificacao
					if(primeiroRegistro){

						SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
						solicitacaoTipo.setId((Integer) arrayRA[25]);
						solicitacaoTipo.setDescricao("" + arrayRA[26]);

						SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
						solicitacaoTipoEspecificacao.setId((Integer) arrayRA[24]);
						solicitacaoTipoEspecificacao.setDescricao((String) arrayRA[9]);

						solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

						// Especificação
						retorno.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

						if(arrayRA[20] != null && arrayRA[21] != null){

							LogradouroCep logradouroCep = null;
							if(arrayRA[20] != null){

								logradouroCep = new LogradouroCep();
								logradouroCep.setId((Integer) arrayRA[20]);

								// id do Logradouro
								Logradouro logradouro = null;
								if(arrayRA[19] != null){
									logradouro = new Logradouro();
									logradouro.setId((Integer) arrayRA[19]);
									logradouro.setNome("" + arrayRA[0]);
								}
								LogradouroTipo logradouroTipo = null;
								// Descrição logradouro tipo
								if(arrayRA[22] != null){
									logradouroTipo = new LogradouroTipo();
									logradouroTipo.setDescricaoAbreviada("" + arrayRA[22]);
									logradouro.setLogradouroTipo(logradouroTipo);
								}
								LogradouroTitulo logradouroTitulo = null;
								// Descrição logradouro titulo
								if(arrayRA[23] != null){
									logradouroTitulo = new LogradouroTitulo();
									logradouroTitulo.setDescricaoAbreviada("" + arrayRA[23]);
									logradouro.setLogradouroTitulo(logradouroTitulo);
								}
								// id do CEP
								Cep cep = null;
								if(arrayRA[10] != null){
									cep = new Cep();
									cep.setCepId((Integer) arrayRA[10]);
									cep.setCodigo((Integer) arrayRA[16]);
								}

								logradouroCep.setLogradouro(logradouro);
								logradouroCep.setCep(cep);
							}

							objetoImovel.setLogradouroCep(logradouroCep);

							LogradouroBairro logradouroBairro = null;
							if(arrayRA[21] != null){

								logradouroBairro = new LogradouroBairro();
								logradouroBairro.setId((Integer) arrayRA[21]);

								Bairro bairro = null;
								// nome bairro
								if(arrayRA[3] != null){
									bairro = new Bairro();
									bairro.setId((Integer) arrayRA[3]);
									bairro.setCodigo((Integer) arrayRA[17]);
									bairro.setNome("" + arrayRA[4]);
								}

								Municipio municipio = null;
								// nome municipio
								if(arrayRA[5] != null){
									municipio = new Municipio();
									municipio.setId((Integer) arrayRA[5]);
									municipio.setNome("" + arrayRA[6]);

									// id da unidade federação
									if(arrayRA[7] != null){
										UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
										unidadeFederacao.setId((Integer) arrayRA[7]);
										unidadeFederacao.setSigla("" + arrayRA[8]);
										municipio.setUnidadeFederacao(unidadeFederacao);
									}

									bairro.setMunicipio(municipio);
								}

								logradouroBairro.setBairro(bairro);
							}

							objetoImovel.setLogradouroBairro(logradouroBairro);
						}

						Localidade localidade = new Localidade();
						SetorComercial setorComercial = new SetorComercial();
						Quadra quadra = new Quadra();

						localidade.setId((Integer) arrayRA[31]);
						setorComercial.setCodigo(((Integer) arrayRA[32]).intValue());
						quadra.setNumeroQuadra(((Integer) arrayRA[33]).intValue());

						objetoImovel.setLocalidade(localidade);
						objetoImovel.setSetorComercial(setorComercial);
						objetoImovel.setQuadra(quadra);
						objetoImovel.setLote(((Short) arrayRA[34]).shortValue());
						objetoImovel.setSubLote(((Short) arrayRA[35]).shortValue());

						objetoImovel.setNumeroImovel((String) arrayRA[39]);

						if(arrayRA[40] != null){
							objetoImovel.setComplementoEndereco((String) arrayRA[40]);
						}

						retorno.setImovel(objetoImovel);

						primeiroRegistro = false;
					}

					// Id
					ra.setId((Integer) arrayRA[27]);

					// complemento endereço
					if(arrayRA[18] != null){
						ra.setComplementoEndereco("" + arrayRA[18]);
					}

					// pontoReferencia
					if(arrayRA[28] != null){
						ra.setPontoReferencia("" + arrayRA[28]);
					}

					// Data do RegistroAtendimento
					if(arrayRA[29] != null){
						ra.setRegistroAtendimento((Date) arrayRA[29]);
					}

					// Data do Encerramento
					if(arrayRA[36] != null){
						ra.setDataEncerramento((Date) arrayRA[36]);
					}

					// Atendimento Motivo Encerramento
					if(arrayRA[37] != null){
						AtendimentoMotivoEncerramento motivoEncerramento = new AtendimentoMotivoEncerramento();
						motivoEncerramento.setId((Integer) arrayRA[37]);
						motivoEncerramento.setDescricao((String) arrayRA[38]);
						ra.setAtendimentoMotivoEncerramento(motivoEncerramento);
					}

					colecaoRegistroAtendimentoRetorno.add(ra);
				}

				retorno.setColecaoRegistroAtendimento(colecaoRegistroAtendimentoRetorno);
			}
		}else if(idImovel == null && idLogradouroBairro != null && idLogradouroCep != null){

			try{
				colecaoRAEncerrado = repositorioRegistroAtendimento.pesquisarRAEncerradoLocalOcorrencia(idLogradouroBairro,
								idLogradouroCep, idEspecificacao, dataReativacao);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(colecaoRAEncerrado != null && !colecaoRAEncerrado.isEmpty()){

				retorno = new RegistroAtendimentoEncerradoLocalOcorrenciaHelper();

				Collection colecaoRegistroAtendimentoRetorno = new ArrayList();

				RegistroAtendimento ra = null;

				Iterator raIterator = colecaoRAEncerrado.iterator();

				Object[] arrayRA = null;

				boolean primeiroRegistro = true;

				while(raIterator.hasNext()){

					ra = new RegistroAtendimento();

					arrayRA = (Object[]) raIterator.next();

					// complemento endereço
					if(arrayRA[18] != null){
						ra.setComplementoEndereco("" + arrayRA[18]);
					}

					// SolicitacaoTipoEspecificacao
					if(primeiroRegistro){

						SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
						solicitacaoTipo.setId((Integer) arrayRA[25]);
						solicitacaoTipo.setDescricao("" + arrayRA[26]);

						SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
						solicitacaoTipoEspecificacao.setId((Integer) arrayRA[24]);
						solicitacaoTipoEspecificacao.setDescricao((String) arrayRA[9]);

						solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

						// Especificação
						retorno.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);

						if(arrayRA[20] != null && arrayRA[21] != null){

							LogradouroCep logradouroCep = null;
							if(arrayRA[20] != null){

								logradouroCep = new LogradouroCep();
								logradouroCep.setId((Integer) arrayRA[20]);

								// id do Logradouro
								Logradouro logradouro = null;
								if(arrayRA[19] != null){
									logradouro = new Logradouro();
									logradouro.setId((Integer) arrayRA[19]);
									logradouro.setNome("" + arrayRA[0]);
								}
								LogradouroTipo logradouroTipo = null;
								// Descrição logradouro tipo
								if(arrayRA[22] != null){
									logradouroTipo = new LogradouroTipo();
									logradouroTipo.setDescricaoAbreviada("" + arrayRA[22]);
									logradouro.setLogradouroTipo(logradouroTipo);
								}
								LogradouroTitulo logradouroTitulo = null;
								// Descrição logradouro titulo
								if(arrayRA[23] != null){
									logradouroTitulo = new LogradouroTitulo();
									logradouroTitulo.setDescricaoAbreviada("" + arrayRA[23]);
									logradouro.setLogradouroTitulo(logradouroTitulo);
								}
								// id do CEP
								Cep cep = null;
								if(arrayRA[10] != null){
									cep = new Cep();
									cep.setCepId((Integer) arrayRA[10]);
									cep.setCodigo((Integer) arrayRA[16]);
								}

								logradouroCep.setLogradouro(logradouro);
								logradouroCep.setCep(cep);
							}

							ra.setLogradouroCep(logradouroCep);

							LogradouroBairro logradouroBairro = null;
							if(arrayRA[21] != null){

								logradouroBairro = new LogradouroBairro();
								logradouroBairro.setId((Integer) arrayRA[21]);

								Bairro bairro = null;
								// nome bairro
								if(arrayRA[3] != null){
									bairro = new Bairro();
									bairro.setId((Integer) arrayRA[3]);
									bairro.setCodigo((Integer) arrayRA[17]);
									bairro.setNome("" + arrayRA[4]);
								}

								Municipio municipio = null;
								// nome municipio
								if(arrayRA[5] != null){
									municipio = new Municipio();
									municipio.setId((Integer) arrayRA[5]);
									municipio.setNome("" + arrayRA[6]);

									// id da unidade federação
									if(arrayRA[7] != null){
										UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
										unidadeFederacao.setId((Integer) arrayRA[7]);
										unidadeFederacao.setSigla("" + arrayRA[8]);
										municipio.setUnidadeFederacao(unidadeFederacao);
									}

									bairro.setMunicipio(municipio);
								}

								logradouroBairro.setBairro(bairro);
							}

							ra.setLogradouroBairro(logradouroBairro);
						}

						retorno.setEnderecoLocalOcorrencia(ra.getEnderecoFormatadoAbreviado());

						primeiroRegistro = false;
					}

					// Id
					ra.setId((Integer) arrayRA[27]);

					// pontoReferencia
					if(arrayRA[28] != null){
						ra.setPontoReferencia("" + arrayRA[28]);
					}

					// Data do RegistroAtendimento
					if(arrayRA[29] != null){
						ra.setRegistroAtendimento((Date) arrayRA[29]);
					}

					// Data do Encerramento
					if(arrayRA[30] != null){
						ra.setDataEncerramento((Date) arrayRA[30]);
					}

					// Atendimento Motivo Encerramento
					if(arrayRA[31] != null){
						AtendimentoMotivoEncerramento motivoEncerramento = new AtendimentoMotivoEncerramento();
						motivoEncerramento.setId((Integer) arrayRA[31]);
						motivoEncerramento.setDescricao((String) arrayRA[32]);
						ra.setAtendimentoMotivoEncerramento(motivoEncerramento);
					}

					colecaoRegistroAtendimentoRetorno.add(ra);
				}

				retorno.setColecaoRegistroAtendimento(colecaoRegistroAtendimentoRetorno);

			}
		}

		return retorno;

	}

	/**
	 * Pesquisa os dados do Registro Atendimento Solicitante para o Relatório de
	 * OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(Integer idRegistroAtendimento) throws ControladorException{

		Object[] dadosSolicitante = null;

		try{

			dadosSolicitante = this.repositorioRegistroAtendimento
							.pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(idRegistroAtendimento);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return dadosSolicitante;

	}

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa o telefone principal do Registro Atendimento Solicitante para o
	 * Relatório de OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String pesquisarSolicitanteFonePrincipal(Integer idRegistroAtendimento) throws ControladorException{

		Object[] dadosSolicitanteFone = null;
		String telefoneFormatado = "";

		try{

			dadosSolicitanteFone = this.repositorioRegistroAtendimento.pesquisarSolicitanteFonePrincipal(idRegistroAtendimento);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(dadosSolicitanteFone != null){

			// DDD
			if(dadosSolicitanteFone[0] != null){
				telefoneFormatado = telefoneFormatado + "(" + dadosSolicitanteFone[0].toString() + ")";
			}

			// número do Telefone
			if(dadosSolicitanteFone[1] != null){
				telefoneFormatado = telefoneFormatado + dadosSolicitanteFone[1];
			}

			// Ramal
			if(dadosSolicitanteFone[2] != null){
				telefoneFormatado = telefoneFormatado + "/" + dadosSolicitanteFone[2];
			}

		}

		return telefoneFormatado;

	}

	/**
	 * Pesquisar o endereço descritivo do RA a partir do seu id
	 * [UC0482] - Obter endereço Abreviado da ocorrência do RA
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idRA
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoDescritivoRA(Integer idRA) throws ControladorException{

		Object[] dadosEndereco = null;
		try{
			dadosEndereco = repositorioRegistroAtendimento.obterEnderecoDescritivoRA(idRA);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = "";

		if(dadosEndereco != null){

			// Decrição endereço
			if(dadosEndereco[0] != null){
				endereco = endereco + dadosEndereco[0].toString().trim();
			}

			// Nome do Bairro
			if(dadosEndereco[1] != null){
				endereco = endereco + " - " + dadosEndereco[1].toString().trim();
			}

		}

		return endereco;
	}

	/**
	 * [UC0482] - Obter endereço Abreviado da ocorrência do RA
	 * Pesquisa o endereço abreviado da ocorrência do RA
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoOcorrenciaRA(Integer idRA) throws ControladorException{

		String endereco = "";
		Object[] dadosRA = null;

		String enderecoDescritivo = this.obterEnderecoDescritivoRA(idRA);
		if(enderecoDescritivo != null && !enderecoDescritivo.equals("")){
			endereco = enderecoDescritivo;
		}else{

			try{
				dadosRA = this.repositorioRegistroAtendimento.obterLogradouroBairroImovelRegistroAtendimento(idRA);
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(dadosRA != null){

				// Id do Logradouro Bairro
				// Caso o Logradouro Bairro seja diferente de nulo pesquisa o
				// endereco pelo RA
				if(dadosRA[0] != null){
					endereco = getControladorEndereco().obterEnderecoAbreviadoRA(idRA);
				}
				// Id do Imóvel
				// Caso o Logradouro Bairro for nulo e o Imóvel não for pesquisa
				// o
				// endereço do Imóvel
				else if(dadosRA[1] != null){
					Integer idImovel = (Integer) dadosRA[1];
					endereco = getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
				}

			}

		}

		return endereco;

	}

	/**
	 * Caso não exista para o Imóvel RA encerrada por execução com especificação
	 * da solicitação
	 * 
	 * @author Rafael Santos,Rafael Santos
	 * @since 26/10/2006,20/11/2006
	 */
	public void verificarExistenciaRegistroAtendimento(Integer idImovel, String mensagemErro, char codigoConstante)
					throws ControladorException{

		// filtro Registro Atendimento
		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.IMOVEL_ID, idImovel));
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
		// Alterado no dia 20/11/2006, pode determinação de Aryed e Newton
		filtroRegistroAtendimento.adicionarParametro(new ParametroNulo(FiltroRegistroAtendimento.ID_ATENDIMENTO_MOTIVO_ENCERRAMENTO));

		filtroRegistroAtendimento.adicionarParametro(new ParametroNaoNulo(FiltroRegistroAtendimento.ID_ESPECIFIACAO_TIPO_VALIDADCAO));

		Collection colecaoRegistroAtendimento = getControladorUtil().pesquisar(filtroRegistroAtendimento,
						RegistroAtendimento.class.getName());

		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){

			Iterator iteratorColecaoRegistroAtendimento = colecaoRegistroAtendimento.iterator();

			RegistroAtendimento registroAtendimento = null;

			boolean existeEspecificacaoTipoValidacao = false;

			while(iteratorColecaoRegistroAtendimento.hasNext()){

				registroAtendimento = (RegistroAtendimento) iteratorColecaoRegistroAtendimento.next();

				// Alterado no dia 20/11/2006, pode determinação de Aryed e
				// Newton
				/*
				 * if (registroAtendimento.getAtendimentoMotivoEncerramento() !=
				 * null) { sessionContext.setRollbackOnly(); throw new
				 * ControladorException(mensagemErro, null, idImovel
				 * .toString()); }
				 */

				// Alterado no dia 20/11/2006, pode determinação de Aryed e
				// Newton
				// verifica se existe atendimento motivo para o encerramento
				/*
				 * if (registroAtendimento.getAtendimentoMotivoEncerramento()
				 * .getIndicadorExecucao() != 1) {
				 * sessionContext.setRollbackOnly(); throw new
				 * ControladorException(mensagemErro, null, idImovel
				 * .toString()); }
				 */

				FiltroEspecificacaoTipoValidacao filtroEspecificacaoTipoValidacao = new FiltroEspecificacaoTipoValidacao();
				filtroEspecificacaoTipoValidacao.adicionarParametro(new ParametroSimples(
								FiltroEspecificacaoTipoValidacao.ID_SOLICITACAO_TIPO_ESPECIFICACAO_ID, registroAtendimento
												.getSolicitacaoTipoEspecificacao().getId()));
				filtroEspecificacaoTipoValidacao.adicionarParametro(new ParametroSimples(FiltroEspecificacaoTipoValidacao.CODIGO_CONSTANTE,
								codigoConstante));

				Collection colecaoEspecificacaoTipoValidacao = getControladorUtil().pesquisar(filtroEspecificacaoTipoValidacao,
								EspecificacaoTipoValidacao.class.getName());

				if(colecaoEspecificacaoTipoValidacao != null && !colecaoEspecificacaoTipoValidacao.isEmpty()){
					existeEspecificacaoTipoValidacao = true;
				}
			}

			if(!(existeEspecificacaoTipoValidacao)){
				throw new ControladorException(mensagemErro, null, idImovel.toString());
			}

		}else{
			throw new ControladorException(mensagemErro, null, idImovel.toString());
		}
	}

	/**
	 * Caso não exista para o Imóvel RA encerrada por execução com especificação
	 * da solicitação No caso de Tarifa Social
	 * 
	 * @author Rafael Santos
	 * @since 26/10/2006
	 */
	public void verificarExistenciaRegistroAtendimentoTarifaSocial(Integer idImovel, String mensagemErro) throws ControladorException{

		// filtro Registro Atendimento
		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.IMOVEL_ID, idImovel));
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ID_ESPECIFIACAO_TIPO_VALIDADCAO);

		Collection colecaoRegistroAtendimento = getControladorUtil().pesquisar(filtroRegistroAtendimento,
						RegistroAtendimento.class.getName());

		// validar para saber se existe RA para o imvel
		RegistroAtendimento registroAtendimento = null;
		if(colecaoRegistroAtendimento != null && !colecaoRegistroAtendimento.isEmpty()){
			registroAtendimento = (RegistroAtendimento) colecaoRegistroAtendimento.iterator().next();
		}else{
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException(mensagemErro, null, idImovel.toString());

		}

		// verifica se existe atendimento motivo para o encerramento
		if(registroAtendimento.getAtendimentoMotivoEncerramento() == null){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException(mensagemErro, null, idImovel.toString());
		}

		// verifica se existe atendimento motivo para o encerramento
		if(registroAtendimento.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != 1){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException(mensagemErro, null, idImovel.toString());
		}

		if(registroAtendimento.getSolicitacaoTipoEspecificacao() != null){

			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
							registroAtendimento.getSolicitacaoTipoEspecificacao().getId()));
			filtroSolicitacaoTipoEspecificacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

			Collection colecaoSolicitacaoTipoEspecificacao = getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
							SolicitacaoTipoEspecificacao.class.getName());

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
			if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){
				solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) colecaoSolicitacaoTipoEspecificacao.iterator().next();
				if(solicitacaoTipoEspecificacao.getSolicitacaoTipo() == null
								|| solicitacaoTipoEspecificacao.getSolicitacaoTipo().getIndicadorTarifaSocial() != 1){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException(mensagemErro, null, idImovel.toString());
				}
			}else{
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException(mensagemErro, null, idImovel.toString());
			}
		}else{
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException(mensagemErro, null, idImovel.toString());
		}
	}

	/**
	 * [UC0494] Gerar Numeração de RA Manual
	 * 
	 * @author Raphael Rossiter
	 * @date 06/11/2006
	 * @throws ControladorException
	 */
	public GerarNumeracaoRAManualHelper gerarNumeracaoRAManual(Integer quantidade, Integer idUnidadeOrganizacional)
					throws ControladorException{

		GerarNumeracaoRAManualHelper retorno = new GerarNumeracaoRAManualHelper();

		// [FS001] - Validar Quantidade Informada
		if(quantidade > ConstantesSistema.LIMITE_QUANTIDADE_GERACAO_MANUAL){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_atendimento_geracao_manual_limite_quantidade_superior", null,
							ConstantesSistema.LIMITE_QUANTIDADE_GERACAO_MANUAL.toString());
		}

		// [FS002] - Verificar existência da Unidade Organizacional
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeOrganizacional));

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoUnidade = this.getControladorUtil().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		if(colecaoUnidade == null || colecaoUnidade.isEmpty()){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.pesquisa_inexistente", null, "Unidade Organizacional");

		}

		UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

		retorno.setIdUnidadeOrganizacional(unidadeOrganizacional.getId());
		retorno.setDescricaoUnidadeOrganizacional(unidadeOrganizacional.getDescricao());

		// Numeração
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		int sequencialInteiro = sistemaParametro.getUltimoRAManual().intValue() + 1;
		String sequencialString = "";

		Collection<String> colecaoNumeracao = new ArrayList();
		String ultimaNumeracaoGerada = null;

		for(int x = 0; x < quantidade; x++){

			sequencialString = String.valueOf(sequencialInteiro);
			int digitoModulo11 = Util.obterDigitoVerificadorModulo11(Long.parseLong(sequencialString));

			if(sequencialString.length() < 9){

				int complementoZeros = 9 - sequencialString.length();
				String sequencialStringFinal = sequencialString;

				for(int y = 0; y < complementoZeros; y++){
					sequencialStringFinal = "0" + sequencialStringFinal;
				}

				colecaoNumeracao.add(sequencialStringFinal.trim() + "-" + digitoModulo11);
				ultimaNumeracaoGerada = sequencialStringFinal.trim();
			}else{

				colecaoNumeracao.add(sequencialString.trim() + "-" + digitoModulo11);
				ultimaNumeracaoGerada = sequencialString.trim();
			}

			sequencialInteiro++;
		}

		retorno.setColecaoNumeracao(colecaoNumeracao);

		// Atualizando o último número gerado
		sistemaParametro.setUltimoRAManual(Short.valueOf(ultimaNumeracaoGerada));

		this.getControladorUtil().atualizarSistemaParametro(sistemaParametro);

		return retorno;

	}

	/**
	 * [UC0404] - Manter Especificação da Situação do Imóvel
	 * [SB0001] Atualizar Critério de Cobrança
	 * 
	 * @author Rafael Pinto
	 * @created 09/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarEspecificacaoSituacaoImovel(EspecificacaoImovelSituacao especificacaoImovelSituacao,
					Collection colecaoEspecificacaoCriterios, Collection colecaoEspecificacaoCriteriosRemovidas, Usuario usuario)
					throws ControladorException{

		EspecificacaoImovelSituacao especificacaoImovelSituacaoBase = null;

		FiltroEspecificacaoImovelSituacao filtro = new FiltroEspecificacaoImovelSituacao();

		filtro.adicionarParametro(new ParametroSimples(FiltroEspecificacaoImovelSituacao.ID, especificacaoImovelSituacao.getId()));

		Collection<CobrancaCriterio> collectionEspecificacao = this.getControladorUtil().pesquisar(filtro,
						EspecificacaoImovelSituacao.class.getName());

		if(collectionEspecificacao != null && !collectionEspecificacao.isEmpty()){

			especificacaoImovelSituacaoBase = (EspecificacaoImovelSituacao) Util.retonarObjetoDeColecao(collectionEspecificacao);
		}

		// Verificar se o logradouro já foi atualizado por outro usuário
		// durante
		// esta atualização
		if(especificacaoImovelSituacao.getUltimaAlteracao() != null){
			if(especificacaoImovelSituacaoBase == null
							|| especificacaoImovelSituacaoBase.getUltimaAlteracao().after(especificacaoImovelSituacao.getUltimaAlteracao())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}

		// [UC0107] - Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ESPECIFICACAO_SITUACAO_IMOVEL_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ESPECIFICACAO_SITUACAO_IMOVEL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		especificacaoImovelSituacao.setOperacaoEfetuada(operacaoEfetuada);
		especificacaoImovelSituacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(especificacaoImovelSituacao);

		especificacaoImovelSituacao.setUltimaAlteracao(new Date());

		this.getControladorUtil().atualizar(especificacaoImovelSituacao);

		Iterator itera = colecaoEspecificacaoCriterios.iterator();

		while(itera.hasNext()){

			EspecificacaoImovSitCriterio especificacaoImovSitCriterio = (EspecificacaoImovSitCriterio) itera.next();

			especificacaoImovSitCriterio.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);

			// [UC0107] - Registrar Transação
			especificacaoImovSitCriterio.setOperacaoEfetuada(operacaoEfetuada);
			especificacaoImovSitCriterio.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(especificacaoImovSitCriterio);

			especificacaoImovSitCriterio.setUltimaAlteracao(new Date());

			this.getControladorUtil().inserirOuAtualizar(especificacaoImovSitCriterio);
		}

		if(colecaoEspecificacaoCriteriosRemovidas != null && !colecaoEspecificacaoCriteriosRemovidas.isEmpty()){

			Iterator iter = colecaoEspecificacaoCriteriosRemovidas.iterator();

			while(iter.hasNext()){

				EspecificacaoImovSitCriterio especificacaoImovSitCriterio = (EspecificacaoImovSitCriterio) iter.next();

				this.getControladorUtil().remover(especificacaoImovSitCriterio);
			}
		}
	}

	/**
	 * [UC00054] Inserir Tarifa Social
	 * [FS00001] Verificar Registro Atendimento
	 * 
	 * @author Rafael Santos
	 * @date 10/11/2006
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificarRegistroAtendimentoTarifaSocial(String idRegistroAtendimento) throws ControladorException{

		RegistroAtendimento registroAtendimento = null;

		try{

			registroAtendimento = repositorioRegistroAtendimento.pesquisarRegistroAtendimentoTarifaSocial(Integer
							.valueOf(idRegistroAtendimento));

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(registroAtendimento != null){

			// Registro de Atendimento não está associado a um Imóvel
			if(registroAtendimento.getImovel() == null){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.nao.associado.imovel");
			}

			// Registro de Atendimento está encerrado
			if(registroAtendimento.getAtendimentoMotivoEncerramento() != null){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.esta.encerrado");
			}

			// Tipo de Solicitação do registro de atendimento não permite a
			// inclusão na tarifa social
			if(registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getIndicadorTarifaSocial() == 2){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.nao.permite.inclusao.tarifa_social");
			}
		}

		return registroAtendimento;
	}

	/**
	 * [UC00069] Manter Dados Tarifa Social
	 * [FS00001] Verificar Registro Atendimento
	 * 
	 * @author Rafael Corrêa
	 * @date 05/02/2007
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificarRegistroAtendimentoManterTarifaSocial(String idRegistroAtendimento) throws ControladorException{

		RegistroAtendimento registroAtendimento = null;

		try{

			registroAtendimento = repositorioRegistroAtendimento.pesquisarRegistroAtendimentoTarifaSocial(Integer
							.valueOf(idRegistroAtendimento));

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(registroAtendimento != null){

			// Registro de Atendimento não está associado a um Imóvel
			if(registroAtendimento.getImovel() == null){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.nao.associado.imovel");
			}

			// Registro de Atendimento está encerrado
			if(registroAtendimento.getAtendimentoMotivoEncerramento() != null){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.esta.encerrado");
			}

			// Tipo de Solicitação do registro de atendimento não permite a
			// inclusão na tarifa social
			if(registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getIndicadorTarifaSocial() == 2){
				// FS0001 - Validar Registro de Atendimento
				throw new ControladorException("atencao.registro_atendimento.nao.permite.manutencao.tarifa_social");
			}
		}

		return registroAtendimento;
	}

	/**
	 * [UC0401] Atualizar Tipo de Solicitação com Especificação
	 * [SB0001] - Atualizar Tipo Solicitação com Especificações
	 * 
	 * @author Rômulo Aurélio
	 * @date 01/08/2006
	 * @param solicitacaoTipo
	 *            ,
	 *            colecaoSolicitacaoTipoEspecificacao, usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer atualizarTipoSolicitacaoEspecificacao(SolicitacaoTipo solicitacaoTipo, Collection colecaoSolicitacaoTipoEspecificacao,
					Usuario usuarioLogado, Collection colecaoSolicitacaoTipoEspecificacaoRemovidos) throws ControladorException{

		Integer idTipoSolicitacao = null;

		// [SF0001] - Verificar existencia da descrição
		try{

			Integer idSolicitacaoTipoNaBase = repositorioRegistroAtendimento.verificarExistenciaDescricaoTipoSolicitacao(solicitacaoTipo
							.getDescricao());

			if(idSolicitacaoTipoNaBase != null && idSolicitacaoTipoNaBase.intValue() != solicitacaoTipo.getId().intValue()){
				if(idSolicitacaoTipoNaBase != null && !idSolicitacaoTipoNaBase.equals("")){
					throw new ControladorException("atencao.tipo.solicitacao.ja.existe");

				}

			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		/*
		 * Controle de concorrencia
		 */
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

		filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, solicitacaoTipo.getId().toString()));
		Collection colecaoSolicitacaoTipoBase = getControladorUtil().pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());
		SolicitacaoTipo solicitacaoTipoBase = null;
		if(!colecaoSolicitacaoTipoBase.isEmpty()){
			solicitacaoTipoBase = (SolicitacaoTipo) Util.retonarObjetoDeColecao(colecaoSolicitacaoTipoBase);

			if(solicitacaoTipoBase.getUltimaAlteracao().after(solicitacaoTipo.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}
		// Fim controle de concorrencia

		// atualiza tipo solicitacao especificacao

		getControladorUtil().atualizar(solicitacaoTipo);
		// solicitacaoTipo.setId(idTipoSolicitacao);

		Iterator iteratorSolicitacaoTipoEspecificacao = colecaoSolicitacaoTipoEspecificacao.iterator();
		while(iteratorSolicitacaoTipoEspecificacao.hasNext()){
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) iteratorSolicitacaoTipoEspecificacao
							.next();
			// recupera a coleção de especificação Serviço tipo
			Collection colecaoEspecificacaoServicoTipo = solicitacaoTipoEspecificacao.getEspecificacaoServicoTipos();
			// limpa a coleção de especificação Serviço tipo no objeto
			// solicitação tipo especificação para não dar erro
			solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(null);
			// inseri o tipo de solicitação no tipo de solicitação especificação
			solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

			/**
			 * [UC0401] Manter Tipo de Solicitação com Especificações
			 * 3.1.2. Caso o usuário solicite remover um tipo de solicitação ou torná-lo INATIVO,
			 * deverá atualizar na tabela SOLICITACAO_TIPO o campo
			 * SOTP_ICUSO = 2 cujo STOP_ID seja igual ao solicitado e também todos as suas
			 * especificações associadas (STEP_ICUSO = 2 da tabela
			 * SOLICITACAO_TIPO_ESPECIFICACAO cujo STOP_ID = STOP_ID da tabela SOLICITCAO_TIPO). Não
			 * esquecer de solicitar a mensagem de confirmação
			 * de remoção.
			 */
			// if (solicitacaoTipo.getIndicadorUso() ==
			// ConstantesSistema.INDICADOR_USO_DESATIVO.shortValue()) {
			solicitacaoTipoEspecificacao.setIndicadorUso(solicitacaoTipo.getIndicadorUso());
			// }

			if(solicitacaoTipoEspecificacao.getId() == null){

				// inseri tipo solicitacao especificacao
				Integer idSolicitacaoTipoEspecificacao = (Integer) getControladorUtil().inserir(solicitacaoTipoEspecificacao);

				// caso a coleção de especificação Serviço tipo seja diferente
				// de
				// nula
				if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
					Iterator iteratorSolicitacaoTipoServico = colecaoEspecificacaoServicoTipo.iterator();
					while(iteratorSolicitacaoTipoServico.hasNext()){
						EspecificacaoServicoTipo especificacaoServicoTipo = (EspecificacaoServicoTipo) iteratorSolicitacaoTipoServico
										.next();
						EspecificacaoServicoTipoPK especificacaoServicoTipoPK = especificacaoServicoTipo.getComp_id();
						if(solicitacaoTipoEspecificacao.getId() != null){
							especificacaoServicoTipoPK.setIdSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);
						}
						especificacaoServicoTipo.setComp_id(especificacaoServicoTipoPK);
						// atualizar especificacao Serviço tipo
						getControladorUtil().inserir(especificacaoServicoTipo);
					}
				}

			}else{
				// atualiza tipo solicitacao especificacao
				getControladorUtil().atualizar(solicitacaoTipoEspecificacao);

				// caso a coleção de especificação Serviço tipo seja diferente
				// de
				// nula

				FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
				filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
								FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, solicitacaoTipoEspecificacao.getId()));

				Collection colecaoEspecificacaoServicoTipoBase = getControladorUtil().pesquisar(filtroEspecificacaoServicoTipo,
								EspecificacaoServicoTipo.class.getName());

				if(colecaoEspecificacaoServicoTipoBase != null && !colecaoEspecificacaoServicoTipoBase.isEmpty()){
					Iterator colecaoEspecificacaoServicoTipoBaseIterator = colecaoEspecificacaoServicoTipoBase.iterator();

					while(colecaoEspecificacaoServicoTipoBaseIterator.hasNext()){
						EspecificacaoServicoTipo especificacaoServicoTipoBase = (EspecificacaoServicoTipo) colecaoEspecificacaoServicoTipoBaseIterator
										.next();
						getControladorUtil().remover(especificacaoServicoTipoBase);
					}
				}

				if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
					Iterator iteratorSolicitacaoTipoServico = colecaoEspecificacaoServicoTipo.iterator();
					while(iteratorSolicitacaoTipoServico.hasNext()){

						EspecificacaoServicoTipo especificacaoServicoTipo = (EspecificacaoServicoTipo) iteratorSolicitacaoTipoServico
										.next();
						EspecificacaoServicoTipoPK especificacaoServicoTipoPK = especificacaoServicoTipo.getComp_id();
						especificacaoServicoTipoPK.setIdSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao.getId());
						especificacaoServicoTipo.setComp_id(especificacaoServicoTipoPK);
						// inseri especificacao Serviço tipo
						getControladorUtil().inserir(especificacaoServicoTipo);
					}

				}
			}

			if(colecaoSolicitacaoTipoEspecificacaoRemovidos != null && !colecaoSolicitacaoTipoEspecificacaoRemovidos.isEmpty()){

				Iterator iteratorSolicitacaoTipoEspecificacaoRemovidos = colecaoSolicitacaoTipoEspecificacaoRemovidos.iterator();

				while(iteratorSolicitacaoTipoEspecificacaoRemovidos.hasNext()){
					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoRemovido = (SolicitacaoTipoEspecificacao) iteratorSolicitacaoTipoEspecificacaoRemovidos
									.next();

					if(solicitacaoTipoEspecificacaoRemovido.getId() != null){

						getControladorUtil().remover(solicitacaoTipoEspecificacaoRemovido);

					}
				}

			}

			colecaoSolicitacaoTipoEspecificacaoRemovidos = null;
		}

		return idTipoSolicitacao;
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void tramitarConjuntoRA(Collection tramites) throws ControladorException{

		if(tramites != null && !tramites.isEmpty()){
			Iterator iteratorTramite = tramites.iterator();
			while(iteratorTramite.hasNext()){
				Tramite tramite = (Tramite) iteratorTramite.next();
				tramite.setUltimaAlteracao(new Date());
				// tramite.setDataTramite(new Date());
				getControladorUtil().inserir(tramite);
			}
		}
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [FS0006] Valida Data [FS0007] Valida Hora [FS0008] Valida Unidade Destino
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void validarConjuntoTramitacao(String[] ids, Date dataHoraTramitacao, Integer idUnidadeDestino) throws ControladorException{

		// [FS0006] Validar Data
		// [FS0007] Validar Hora
		Date dataCorrente = new Date();
		int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataHoraTramitacao, dataCorrente);

		if(qtdeDias < 0){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.tramitar_ra_data_maior_que_atual", null, Util.formatarData(dataCorrente));

		}else if(Util.datasIguais(dataHoraTramitacao, dataCorrente)){
			if(Util.compararHoraMinuto(Util.formatarHoraSemData(dataHoraTramitacao), Util.formatarHoraSemData(dataCorrente), ">")){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_ra_hora_maior_que_atual", null, Util.formatarHoraSemData(dataCorrente));
			}
		}

		UnidadeOrganizacional unidadeDestino = this.getControladorUnidade().pesquisarUnidadeOrganizacional(idUnidadeDestino);

		if(unidadeDestino == null){
			throw new ControladorException("atencao.unidadeOrganizacional.inexistente");
		}

		if(ids != null && ids.length != 0){
			for(int i = 0; i < ids.length; i++){
				String[] idsSelecionados = ids[i].split(";");
				Integer idRa = Integer.parseInt(idsSelecionados[0]);
				Integer idUnidadeOrigem = Integer.parseInt(idsSelecionados[1]);

				UnidadeOrganizacional unidadeOrigem = this.getControladorUnidade().pesquisarUnidadeOrganizacional(idUnidadeOrigem);

				boolean possuiOSTerceirizado = false;

				Collection<OrdemServico> colecaoOrdemServico = obterOSRA(idRa);

				if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

					for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

						OrdemServico element = (OrdemServico) iter.next();

						if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_PENDENTE.intValue()){

							// [FS0008 Caso 4]
							if((new Short(element.getServicoTipo().getIndicadorTerceirizado())).intValue() == 1){
								possuiOSTerceirizado = true;
							}
						}
					}
				}

				// [FS0008] Validar Unidade Destino
				// Caso 1
				if(idUnidadeOrigem.intValue() == idUnidadeDestino.intValue()){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.tramitar_ra_unidade_origem_destino_iguais");
				}

				try{
					Short idTarifaSocialRa = repositorioRegistroAtendimento.verificarIndicadorTarifaSocialRA(idRa);

					// Caso 2
					if((new Short(unidadeDestino.getIndicadorTramite())).intValue() == 2){
						if(new Short(idTarifaSocialRa).intValue() == 2){

							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.tramitar_ra_unidade_destino_tramite");
						}
					}
				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					throw new ControladorException("erro.sistema", ex);
				}

				if(unidadeDestino.getUnidadeTipo() != null && unidadeDestino.getUnidadeTipo().getCodigoTipo() != null){

					if(unidadeDestino.getUnidadeTipo().getCodigoTipo().equals(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)){

						// Caso 3
						if(unidadeOrigem.getUnidadeTipo() == null || unidadeOrigem.getUnidadeTipo().getCodigoTipo() == null
										|| !unidadeOrigem.getUnidadeTipo().getCodigoTipo().equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA)){

							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.tramitar_ra_unidade_centralizadora");
						}
						// Caso 4
						if(!possuiOSTerceirizado){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.tramitar_ra_os_possui_terceiros");
						}
					}
					// Caso 5
					if(unidadeDestino.getUnidadeTipo().getCodigoTipo().equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA)
									&& (unidadeOrigem.getUnidadeTipo().getCodigoTipo() == null || !unidadeOrigem.getUnidadeTipo()
													.getCodigoTipo().equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA))){
						Integer unidadeCentralizadora = 0;
						try{
							unidadeCentralizadora = repositorioRegistroAtendimento.pesquisarUnidadeCentralizadoraRa(idRa);
						}catch(ErroRepositorioException ex){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ilex){

							}
							ex.printStackTrace();
							throw new ControladorException("erro.sistema", ex);
						}
						if(unidadeCentralizadora == null || (unidadeCentralizadora != unidadeDestino.getId().intValue())){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.tramitar_ra_unidade_origem_central");
						}
					}
				}
			}
		}
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * [FS0002] Verificar as situações das OS associadas ao RA [FS0003]
	 * Verificar se o tipo de solicitação Tarifa Social
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public void validarRATramitacao(String[] ids, Integer idUsuario) throws ControladorException{

		try{

			Short idTarifaSocialUnidadeUsuario = repositorioRegistroAtendimento.verificarIndicadorTarifaSocialUsuario(idUsuario);

			if(ids != null && ids.length != 0){
				for(int i = 0; i < ids.length; i++){

					String[] idsSelecionados = ids[i].split(";");
					Integer idRa = Integer.parseInt(idsSelecionados[0]);
					// [FS0002]Verificar as situações das OS associadas ao RA
					Collection<OrdemServico> colecaoOrdemServico = obterOSRA(idRa);

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

						for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

							OrdemServico element = (OrdemServico) iter.next();

							if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO.intValue()){

								// Caso 1
								try{
									sessionContext.setRollbackOnly();
								}catch(IllegalStateException ex){

								}
								throw new ControladorException("atencao.tramitar_ra_os_em_andamento");

							}else if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO.intValue()){

								// Caso 2
								try{
									sessionContext.setRollbackOnly();
								}catch(IllegalStateException ex){

								}
								throw new ControladorException("atencao.tramitar_ra_os_referencia", null, element.getOsReferencia().getId()
												+ "");

							}else if((new Short(element.getSituacao())).intValue() == OrdemServico.SITUACAO_PENDENTE.intValue()){

								// Caso 3
								if(getControladorOrdemServico().verificarExistenciaOSProgramada(element.getId())){
									try{
										sessionContext.setRollbackOnly();
									}catch(IllegalStateException ex){

									}
									throw new ControladorException("atencao.tramitar_ra_os_programada");
								}
							}
						}
					}

					// [UC0418] - Obter Unidade Atual do RA
					UnidadeOrganizacional unidadeAtual = obterUnidadeAtualRA(idRa);

					// [UC0419] - Obter Indicador de Autorização para Manutenção do RA
					Short indicadorAutorizacao = this.obterIndicadorAutorizacaoManutencaoRA(unidadeAtual.getId(), idUsuario);

					if(ConstantesSistema.NAO.equals(indicadorAutorizacao)){
						// [FS0003]Verificar se o tipo de solicitação tarifa
						// social
						Short idTarifaSocialRa = repositorioRegistroAtendimento.verificarIndicadorTarifaSocialRA(idRa);

						/*
						 * Caso o trâmite de RA não esteja liberada para qualquer usuário da empresa
						 * (PASI_VLPARAMETRO com o valor 2 (NÃO) na tabela PARAMETRO_SISTEMA com
						 * PASI_CDPARAMETRO="P_INDICADOR_LIBERAR_TRAMITACAO_RA"):
						 */
						String indicadorLiberarTramitacaoRA = ParametroAtendimentoPublico.P_INDICADOR_LIBERAR_TRAMITACAO_RA.executar();

						if(!Util.isVazioOuBranco(indicadorLiberarTramitacaoRA)
										&& indicadorLiberarTramitacaoRA.equals(ConstantesSistema.NAO.toString())){
							/*
							 * Caso o tipo de solicitação do RA seja “Tarifa Social” e a unidade do
							 * Login do usuário seja na unidade autorizada a tramitar Tarifa Social,
							 * o
							 * sistema deverá liberar a tramitação, caso contrário o sistema deverá
							 * exibir mensagem
							 */
							Boolean raTarifaSocial = idTarifaSocialRa.shortValue() == ConstantesSistema.SIM.shortValue();

							Boolean unidadeAreaTarifaSocial = idTarifaSocialUnidadeUsuario.shortValue() == ConstantesSistema.SIM
											.shortValue();

							// Alterada lógica para que a mesma fique conforme especificado e
							// trate a validação da mesma forma da funcionalidade Manter RA
							// a alteração foi realizada para que uma RA SEM ser do tipo tarifa
							// social
							// não poder ser tramitada.
							if(!raTarifaSocial || !unidadeAreaTarifaSocial){
								try{
									sessionContext.setRollbackOnly();
								}catch(IllegalStateException ex){

								}
								throw new ControladorException("atencao.tramitar_ra_tarifa_social");
							}
						}
					}
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException{

		try{

			this.repositorioRegistroAtendimento.atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException{

		try{

			this.repositorioRegistroAtendimento.atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCepSolicitante(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ControladorException{

		try{

			this.repositorioRegistroAtendimento.atualizarLogradouroCepSolicitante(logradouroCepAntigo, logradouroCepNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairroSolicitante(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException{

		try{

			this.repositorioRegistroAtendimento.atualizarLogradouroBairroSolicitante(logradouroBairroAntigo, logradouroBairroNovo);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * UC?? - ????????
	 * 
	 * @author Rômulo Aurélio Filho
	 * @date 20/03/2007
	 * @descricao O método retorna um objeto com a maior data de Tramite
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Tramite pesquisarUltimaDataTramite(Integer idRA) throws ControladorException{

		try{
			return repositorioRegistroAtendimento.pesquisarUltimaDataTramite(idRA);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Consultar Observacao Registro Atendimento Solicitacao da CAER
	 * 
	 * @author Rafael Pinto
	 * @date 14/03/2007
	 */
	public Collection<RegistroAtendimento> pesquisarObservacaoRegistroAtendimento(Integer matriculaImovel, Date dataInicialAtendimento,
					Date dataFinalAtendimento) throws ControladorException{

		Collection<RegistroAtendimento> retorno = new ArrayList();

		Collection colecao = new ArrayList();

		try{

			colecao = repositorioRegistroAtendimento.pesquisarObservacaoRegistroAtendimento(matriculaImovel, dataInicialAtendimento,
							dataFinalAtendimento);

			if(colecao != null && !colecao.isEmpty()){

				Iterator itera = colecao.iterator();

				while(itera.hasNext()){
					Object[] retornoColecao = (Object[]) itera.next();

					RegistroAtendimento ra = new RegistroAtendimento();

					ra.setId((Integer) retornoColecao[0]);
					ra.setRegistroAtendimento((Date) retornoColecao[1]);
					ra.setObservacao((String) retornoColecao[2]);

					retorno.add(ra);
				}

			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	/**
	 * Método que gera o resumo dos registros de atendimentos
	 * [UC0567] - Gerar Resumo dos Registro de Atendimentos
	 * 
	 * @author Thiago Tenório
	 * @date 24/04/2007
	 */
	public void gerarResumoRegistroAtendimento(int idLocalidade, int idFuncionalidadeIniciada, Integer anoMesReferencia)
					throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idLocalidade);

		try{
			Integer dtAtual = Integer.decode(Util.recuperaDataInvertida(new Date()));

			Collection imoveisRegistroAtendimento = this.repositorioRegistroAtendimento.getImoveisResumoRegistroAtendimento(idLocalidade,
							anoMesReferencia, dtAtual);

			List<ResumoRegistroAtendimentoHelper> listaSimplificada = new ArrayList();
			List<UnResumoRegistroAtendimento> listaResumoRegistroAtendimento = new ArrayList();

			Iterator imoveisRegistroAtendimentoIterator = imoveisRegistroAtendimento.iterator();

			Integer idUnidadeSolicitacao;
			Integer idUnidadeEncerramento;

			// pra cada objeto obter a categoria
			// caso ja tenha um igual soma a quantidade de registro de atendimentos
			while(imoveisRegistroAtendimentoIterator.hasNext()){
				Object obj = imoveisRegistroAtendimentoIterator.next();

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					// Montamos um objeto de resumo, com as informacoes do
					// retorno

					ResumoRegistroAtendimentoHelper helper = new ResumoRegistroAtendimentoHelper((Integer) retorno[1], // idGerenciaRegional
									(Integer) retorno[2], // idUnidadeNegocio
									(Integer) retorno[3], // idLocalidade
									(Integer) retorno[4], // idElo
									(Integer) retorno[5], // idSetorcomercial
									(Integer) retorno[6], // idRota
									(Integer) retorno[7], // idQuadra
									(Integer) retorno[8], // codigoSetorComercial
									(Integer) retorno[9], // numeroQuadra
									(Integer) retorno[10],// perfilImovel
									(Integer) retorno[11],// idSituacaoLigacaoAgua
									(Integer) retorno[12],// idSituacaoLigacaoEsgoto
									(Integer) retorno[13],// idPerfuilLigacaiAgua
									(Integer) retorno[14],// idPerfilLigacaoEsgoto
									(Short) retorno[15],// indicadorAtendimentoOnline
									(Integer) retorno[16],// idTipoSolicitacao
									(Integer) retorno[17],// idTipoEspecificacao
									(Integer) retorno[18],// idMeioSolicitacao
									(Integer) retorno[19],// quantidadeGerada
									(Integer) retorno[20],// quantidadePendentesNoPrazo
									(Integer) retorno[21],// quantidadePendentesForaPrazo
									(Integer) retorno[22],// quantidadeEncerradasNoPrazo
									(Integer) retorno[23],// quantidadeEncerradasForaPrazo
									(Integer) retorno[24]);// quantidadeBloqueadas

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					Integer idImovel = (Integer) retorno[0];
					Integer idRegistroAtendimento = (Integer) retorno[25];

					if(idImovel != null){
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
					}

					// Pesquisa a Unidade de Solicitacao
					idUnidadeSolicitacao = pesquisaUnidadeSolicitacaoRa(idRegistroAtendimento);
					helper.setIdUnidadeSolicitacao(idUnidadeSolicitacao);

					// Pesquisa a Unidade de Encerramento
					idUnidadeEncerramento = pesquisaUnidadeEncerramentoRa(idRegistroAtendimento);
					if(idUnidadeEncerramento != null){
						helper.setIdUnidadeEncerramento(idUnidadeEncerramento);
					}

					// Caso loca_cdelo = 0 significa que
					// loca_cdelo = loca_id
					if(helper.getIdElo().equals(0)){
						helper.setIdElo(helper.getIdLocalidade());
					}

					if(helper.getQuantidadePendentesNoPrazo() == null){
						helper.setQuantidadePendentesNoPrazo(0);
					}
					if(helper.getQuantidadePendentesForaPrazo() == null){
						helper.setQuantidadePendentesForaPrazo(0);
					}

					if(helper.getQuantidadeEncerradasNoPrazo() == null){
						helper.setQuantidadeEncerradasNoPrazo(0);
					}
					if(helper.getQuantidadeEncerradasForaPrazo() == null){
						helper.setQuantidadeEncerradasForaPrazo(0);
					}

					// se ja existe um objeto igual a ele entao soma as
					// ligacoes e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao
					// quantidadeEconomia, quantidadeLigacoes )
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoRegistroAtendimentoHelper jaCadastrado = (ResumoRegistroAtendimentoHelper) listaSimplificada.get(posicao);

						jaCadastrado.setQuantidadeGerada(jaCadastrado.getQuantidadeGerada() + helper.getQuantidadeGerada());

						jaCadastrado.setQuantidadeBloqueada(jaCadastrado.getQuantidadeBloqueada() + helper.getQuantidadeBloqueada());

						jaCadastrado.setQuantidadeEncerradasNoPrazo(jaCadastrado.getQuantidadeEncerradasNoPrazo()
										+ helper.getQuantidadeEncerradasNoPrazo());

						jaCadastrado.setQuantidadeEncerradasForaPrazo(jaCadastrado.getQuantidadeEncerradasForaPrazo()
										+ helper.getQuantidadeEncerradasForaPrazo());

						jaCadastrado.setQuantidadePendentesNoPrazo(jaCadastrado.getQuantidadePendentesNoPrazo()
										+ helper.getQuantidadePendentesNoPrazo());

						jaCadastrado.setQuantidadePendentesForaPrazo(jaCadastrado.getQuantidadePendentesForaPrazo()
										+ helper.getQuantidadePendentesForaPrazo());

					}else{
						listaSimplificada.add(helper);
					}
				}
			}
			/**
			 * para todas os ImovelResumoRegistroAtendimentoHelper cria
			 * ResumoRegistroAtendimento
			 */

			for(int count = 0; count < listaSimplificada.size(); count++){
				ResumoRegistroAtendimentoHelper helper = (ResumoRegistroAtendimentoHelper) listaSimplificada.get(count);

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

				// indicadorAtendimentoOnline
				Short indicadorAtendimentoOnline = null;
				if(helper.getIndicadorAtendimentoOnline() != null){
					indicadorAtendimentoOnline = (helper.getIndicadorAtendimentoOnline());
				}

				// Solicitação Tipo
				GSolicitacaoTipo solicitacaoTipo = null;
				if(helper.getIdSolicitacaoTipo() != null){
					solicitacaoTipo = new GSolicitacaoTipo();
					solicitacaoTipo.setId(helper.getIdSolicitacaoTipo());
				}

				// Solicitação Tipo Especificações
				GSolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
				if(helper.getIdsolicitacaoTipoEspecificacao() != null){
					solicitacaoTipoEspecificacao = new GSolicitacaoTipoEspecificacao();
					solicitacaoTipoEspecificacao.setId(helper.getIdsolicitacaoTipoEspecificacao());
				}

				// Meio de Solicitação
				GMeioSolicitacao meioSolicitacao = null;
				if(helper.getIdMeioSolicitacao() != null){
					meioSolicitacao = new GMeioSolicitacao();
					meioSolicitacao.setId(helper.getIdMeioSolicitacao());
				}

				Integer unidadeSolicitacao = helper.getIdUnidadeSolicitacao();
				Integer unidadeEncerramento = helper.getIdUnidadeEncerramento();

				int countQtdPedentesNoPrazo = helper.getQuantidadePendentesNoPrazo();
				int countQtdPedentesForaPrazo = helper.getQuantidadePendentesForaPrazo();
				int countQtdEncerradasNoPrazo = helper.getQuantidadeEncerradasNoPrazo();
				int countQtdEncerradasForaPrazo = helper.getQuantidadeEncerradasForaPrazo();
				int countQtdBloqueadas = helper.getQuantidadeBloqueada();
				int countQtdTotal = helper.getQuantidadeGerada();

				// Criamos um resumo de ligacao economia (***UFA***)
				UnResumoRegistroAtendimento resumoRegistroAtendimento = new UnResumoRegistroAtendimento(null, anoMesReferencia.intValue(),
								new Date(), countQtdTotal, codigoSetorComercial, countQtdPedentesNoPrazo, countQtdPedentesForaPrazo,
								numeroQuadra, countQtdBloqueadas, countQtdEncerradasNoPrazo, countQtdEncerradasForaPrazo,
								indicadorAtendimentoOnline, meioSolicitacao, subcategoria, clienteTipo, ligacaoAguaSituacao,
								unidadeNegocio, localidade, elo, solicitacaoTipo, quadra, ligacaoEsgotoSituacao, perfilLigacaoEsgoto,
								solicitacaoTipoEspecificacao, gerenciaRegional, setorComercial, perfilLigacaoAgua, esferaPoder, categoria,
								imovelPerfil, rota);

				resumoRegistroAtendimento.setUnidadeSolicitacao(unidadeSolicitacao);
				resumoRegistroAtendimento.setUnidadeEncerramento(unidadeEncerramento);

				// Adicionamos a lista que deve ser inserida
				listaResumoRegistroAtendimento.add(resumoRegistroAtendimento);
				gerenciaRegional = null;
				unidadeNegocio = null;
				localidade = null;
				elo = null;
				setorComercial = null;
				rota = null;
				quadra = null;
				imovelPerfil = null;
				esferaPoder = null;
				clienteTipo = null;
				ligacaoAguaSituacao = null;
				ligacaoEsgotoSituacao = null;
				categoria = null;
				subcategoria = null;
				perfilLigacaoAgua = null;
				perfilLigacaoEsgoto = null;
				indicadorAtendimentoOnline = null;
				solicitacaoTipo = null;
				solicitacaoTipoEspecificacao = null;
				meioSolicitacao = null;
				unidadeSolicitacao = null;
				unidadeEncerramento = null;
			}
			listaSimplificada.clear();
			listaSimplificada = null;

			imoveisRegistroAtendimento.clear();
			imoveisRegistroAtendimento = null;

			getControladorBatch().inserirColecaoObjetoParaBatchGerencial((Collection) listaResumoRegistroAtendimento);

			listaResumoRegistroAtendimento.clear();
			listaResumoRegistroAtendimento = null;

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

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author Kássia Albuquerque
	 * @date 09/04/2007
	 */

	public Integer informarDadosAgenciaReguladora(RaDadosAgenciaReguladora raDadosAgenciaReguladora,
					Collection collectionRaDadosAgenciaReguladoraFone, Usuario usuarioLogado) throws ControladorException{

		// Verificando restricao da tabela Ra_Dados_Agencia_Reguladora
		// xak1_ra_dados_arpe_fone = UNIQUE INDEX (RGAT_ID,RAAR_NNAGENCIAREGULADORA)
		FiltroRaDadosAgenciaReguladora filtroRaDadosAgenciaReguladora = new FiltroRaDadosAgenciaReguladora();

		filtroRaDadosAgenciaReguladora.adicionarParametro(new ParametroSimples(FiltroRaDadosAgenciaReguladora.REGISTRO_ATENDIMENTO_ID,
						raDadosAgenciaReguladora.getRegistroAtendimento().getId()));

		filtroRaDadosAgenciaReguladora.adicionarParametro(new ParametroSimples(FiltroRaDadosAgenciaReguladora.AGENCIA_REGULADORA,
						raDadosAgenciaReguladora.getAgenciaReguladora()));

		Collection collectionUniqueIndexRepetido = getControladorUtil().pesquisar(filtroRaDadosAgenciaReguladora,
						RaDadosAgenciaReguladora.class.getName());

		if(collectionUniqueIndexRepetido != null && !collectionUniqueIndexRepetido.isEmpty()){
			throw new ControladorException("atencao.ra_existente_numero_agencia_reguladora");
		}

		raDadosAgenciaReguladora.setDataReclamacao(new Date());

		raDadosAgenciaReguladora.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO DADOS AGENCIA REGULADORA----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_RA_DADOS_AGENCIA_REGULADORA_INFORMAR,
						new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_RA_DADOS_AGENCIA_REGULADORA_INFORMAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		raDadosAgenciaReguladora.setOperacaoEfetuada(operacaoEfetuada);
		raDadosAgenciaReguladora.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(raDadosAgenciaReguladora);
		// ------------ REGISTRAR TRANSAÇÃO DADOS AGENCIA REGULADORA----------------------------

		Integer idRaDadosAgenciaReguladora = (Integer) this.getControladorUtil().inserir(raDadosAgenciaReguladora);

		// -- codigo para inserir dados agencia reguladora fone --
		raDadosAgenciaReguladora.setId(idRaDadosAgenciaReguladora);

		if(collectionRaDadosAgenciaReguladoraFone != null && !collectionRaDadosAgenciaReguladoraFone.isEmpty()){

			Iterator iterator = collectionRaDadosAgenciaReguladoraFone.iterator();

			while(iterator.hasNext()){

				RaDadosAgenciaReguladoraFone raDadosAgenciaReguladoraFone = (RaDadosAgenciaReguladoraFone) iterator.next();
				raDadosAgenciaReguladoraFone.setRaDadosAgenciaReguladora(raDadosAgenciaReguladora);

				raDadosAgenciaReguladoraFone.setUltimaAlteracao(new Date());
				// ------------ REGISTRAR TRANSAÇÃO DADOS AGENCIA REGULADORA FONE----------------
				raDadosAgenciaReguladoraFone.setOperacaoEfetuada(operacaoEfetuada);
				raDadosAgenciaReguladoraFone.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(raDadosAgenciaReguladoraFone);
				// ------------ REGISTRAR TRANSAÇÃO DADOS AGENCIA REGULADORA FONE----------------

				this.getControladorUtil().inserir(raDadosAgenciaReguladoraFone);

			}
		}
		// -- fim de codigo para inserir dados agencia reguladora fone --
		return idRaDadosAgenciaReguladora;
	}

	/**
	 * Procura a quantidade de dias de prazo
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author Kássia Albuquerque
	 * @date 19/04/2007
	 */

	public Integer procurarDiasPazo(Integer raId) throws ControladorException{

		try{

			return this.repositorioRegistroAtendimento.procurarDiasPazo(raId);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Pesquisa a Unidade Solicitante de acordo com a RA
	 * 
	 * @author Ivan Sérgio
	 * @date 17/08/2007
	 */
	private Integer pesquisaUnidadeSolicitacaoRa(Integer idRa) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisaUnidadeSolicitacaoRa(idRa);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa a Unidade Encerramento de acordo com a RA
	 * 
	 * @author Ivan Sérgio
	 * @date 17/08/2007
	 */
	private Integer pesquisaUnidadeEncerramentoRa(Integer idRa) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisaUnidadeEncerradaRa(idRa);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa um Registro de Atendimento
	 * 
	 * @author eduardo henrique
	 * @date 20/05/2009
	 */
	public RegistroAtendimento pesquisarRegistroAtendimento(Integer idRa) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisarRegistroAtendimento(idRa);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa um Registro de Atendimento Solicitante
	 * 
	 * @author Virgínia Melo
	 * @date 04/06/2009
	 */
	public RegistroAtendimentoSolicitante obterRegistroAtendimentoSolicitanteRelatorioOS(Integer idRa) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.obterRegistroAtendimentoSolicitanteRelatorioOS(idRa);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * obtêm a ultima sequence
	 * 
	 * @return sequence
	 * @throws ControladorException
	 */
	public Integer obterSequenceRA() throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.obterSequenceRA();
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection listarMotivoAtendimentoIncompleto() throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.listarMotivoAtendimentoIncompleto();
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Lista RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarRAsReiteradas(RegistroAtendimento registroAtendimento) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.listarRAsReiteradas(registroAtendimento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<AtendimentoIncompleto> filtrarRegistroAtendimentoIncompleto(FiltrarRegistroAtendimentoIncompletoHelper ra)
					throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.filtrarRegistroAtendimentoIncompleto(ra);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public AtendimentoIncompleto pesquisarRAIncompleta(Integer idRAi) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisarRAIncompleta(idRAi);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Permite obter o Tipo de Solicitação de um registro de atendimento
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2011
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer obterTipoSolicitacaoRA(Integer idRegistroAtendimento) throws ControladorException{

		Integer tipoSolicitacao = Integer.valueOf("0");
		try{
			// verifica o Tipo da Solicitação do registro de atendimento
			tipoSolicitacao = repositorioRegistroAtendimento.obterTipoSolicitacaoRA(idRegistroAtendimento);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return tipoSolicitacao;
	}

	/**
	 * [UC3002] Inserir Mensagem Tipo Solicitação Especificação
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 * @param solicitacaoTipoEspecificacaoMensagem
	 *            , usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirMensagemTipoSolicitacaoEspecificacao(SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem,
					Usuario usuarioLogado) throws ControladorException{

		Integer idMensagemTipoSolicitacao = null;

		// [SF0001] - Verificar existencia da descrição
		try{

			Integer idMensagemSolicitacaoTipoNaBase = repositorioRegistroAtendimento
							.verificarExistenciaDescricaoMensagemTipoSolicitacao(solicitacaoTipoEspecificacaoMensagem.getDescricao());
			if(idMensagemSolicitacaoTipoNaBase != null && !idMensagemSolicitacaoTipoNaBase.equals("")){
				throw new ControladorException("atencao.mensagem.tipo.especificacao.solicitacao.ja.existe");
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		solicitacaoTipoEspecificacaoMensagem.setUltimaAlteracao(new Date());

		// inseri tipo solicitacao especificacao
		idMensagemTipoSolicitacao = (Integer) getControladorUtil().inserir(solicitacaoTipoEspecificacaoMensagem);
		solicitacaoTipoEspecificacaoMensagem.setId(idMensagemTipoSolicitacao);

		return idMensagemTipoSolicitacao;
	}

	/**
	 * [UC3003] Manter Mensagem Tipo Solicitação Especificacao
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 * @param solicitacaoTipo
	 *            , usuarioLogado
	 * @return void
	 * @throws ControladorException
	 */
	public void atualizarMensagemTipoSolicitacaoEspecificacao(SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem,
					Usuario usuarioLogado) throws ControladorException{

		Integer idTipoSolicitacao = null;

		// [SF0001] - Verificar existencia da descrição
		try{

			Integer idMensagemSolicitacaoTipoNaBase = repositorioRegistroAtendimento
							.verificarExistenciaDescricaoMensagemTipoSolicitacao(solicitacaoTipoEspecificacaoMensagem.getDescricao());

			if(idMensagemSolicitacaoTipoNaBase != null
							&& idMensagemSolicitacaoTipoNaBase.intValue() != solicitacaoTipoEspecificacaoMensagem.getId().intValue()){
				if(idMensagemSolicitacaoTipoNaBase != null && !idMensagemSolicitacaoTipoNaBase.equals("")){
					throw new ControladorException("atencao.mensagem.tipo.especificacao.solicitacao.ja.existe");
				}
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		/*
		 * Controle de concorrencia
		 */
		FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();

		filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacaoMensagem.ID,
						solicitacaoTipoEspecificacaoMensagem.getId().toString()));
		Collection colecaoMensagemSolicitacaoTipoBase = getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
						SolicitacaoTipoEspecificacaoMensagem.class.getName());
		SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagemBase = null;
		if(!colecaoMensagemSolicitacaoTipoBase.isEmpty()){
			solicitacaoTipoEspecificacaoMensagemBase = (SolicitacaoTipoEspecificacaoMensagem) Util
							.retonarObjetoDeColecao(colecaoMensagemSolicitacaoTipoBase);

			if(solicitacaoTipoEspecificacaoMensagemBase.getUltimaAlteracao().after(
							solicitacaoTipoEspecificacaoMensagem.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}
		// Fim controle de concorrencia

		solicitacaoTipoEspecificacaoMensagem.setUltimaAlteracao(new Date());

		// atualiza tipo solicitacao especificacao
		getControladorUtil().atualizar(solicitacaoTipoEspecificacaoMensagem);

	}

	/**
	 * [UC3003] Manter Mensagem Tipo Solicitação Especificacao
	 * [SB0002]Excluir Mensagem Automática
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 */
	public void removerMensagemTipoSolicitacaoEspecificacao(String[] ids, Usuario usuarioLogado) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			FiltroSolicitacaoTipoEspecificacaoMensagem filtro = new FiltroSolicitacaoTipoEspecificacaoMensagem();
			filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer.valueOf(ids[i])));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, SolicitacaoTipoEspecificacaoMensagem.class.getName());

			SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
							.retonarObjetoDeColecao(colecaoRetorno);

			// [FS0004]Sistema de Esgoto possui vinculos no sistema
			this.getControladorUtil().remover(solicitacaoTipoEspecificacaoMensagem);
		}
	}

	/**
	 * Verificar se existe o vínculo entre a localidade e o munícipio
	 * 
	 * @author isilva
	 * @date 23/02/2011
	 * @param idLocalidade
	 * @param idMunicipio
	 * @return
	 * @throws ControladorException
	 */
	public boolean existeVinculoLocalidadeMunicipio(Integer idLocalidade, Integer idMunicipio) throws ControladorException{

		try{
			return repositorioRegistroAtendimento.existeVinculoLocalidadeMunicipio(idLocalidade, idMunicipio);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Lista as duas ultimas RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarDuasUltimasRAsReiteradas(RegistroAtendimento registroAtendimento)
					throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.listarDuasUltimasRAsReiteradas(registroAtendimento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Verifica os Dados Solicitantes de Rg, Cpf e Cnpj
	 * 
	 * @author Péricles Tavares
	 * @created 17/03/2011
	 * @param tipoCliente
	 * @param solicitacaoTipoEspecificacaoId
	 * @param numeroCpf
	 * @param numeroRg
	 * @param orgaoExpedidorRg
	 * @param unidadeFederacaoRG
	 * @param numeroCnpj
	 * @throws ControladorException
	 */
	public void verificarDadosSolicitanteRgCpfCnpj(String tipoCliente, String solicitacaoTipoEspecificacaoId, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj) throws ControladorException{

		this.verificarDadosSolicitanteRgCpfCnpj(tipoCliente, solicitacaoTipoEspecificacaoId, numeroCpf, numeroRg, orgaoExpedidorRg,
						unidadeFederacaoRG, numeroCnpj, null);
	}

	public void verificarDadosSolicitanteRgCpfCnpj(String tipoCliente, String solicitacaoTipoEspecificacaoId, String numeroCpf,
					String numeroRg, String orgaoExpedidorRg, String unidadeFederacaoRG, String numeroCnpj, String idCliente)
					throws ControladorException{

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		if(solicitacaoTipoEspecificacaoId != null){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(filtroSolicitacaoTipoEspecificacao.ID,
							solicitacaoTipoEspecificacaoId));
			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));
		}

		if(Integer.valueOf(tipoCliente).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO
						&& !Util.isVazioOuBranco(solicitacaoTipoEspecificacao)
						&& ((solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA() != null && solicitacaoTipoEspecificacao
										.getIndicadorObrigatoriedadeCpfCnpjRA().intValue() == ConstantesSistema.SIM.intValue()) || (solicitacaoTipoEspecificacao
										.getIndicadorObrigatoriedadeRgRA() != null && solicitacaoTipoEspecificacao
										.getIndicadorObrigatoriedadeRgRA().intValue() == ConstantesSistema.SIM.intValue()))){
			if(idCliente == null || idCliente.equals("-1")){
				throw new ControladorException("atencao.especificacao_cliente_obrigatorio");
			}
			throw new ControladorException("atencao.informe_campo", null, "Tipo de Pessoa");
		}

		if(solicitacaoTipoEspecificacao != null){
			if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA() != null
							&& solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA().intValue() == ConstantesSistema.SIM
											.intValue()){

				if(Integer.valueOf(tipoCliente).intValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.intValue()
								&& Util.isVazioOuBranco(numeroCpf)){
					if(idCliente != null){
						throw new ControladorException("atencao.cliente_sem_cpf_cnpj", null, idCliente);
					}

					throw new ControladorException("atencao.informe_campo", null, "Cpf");

				}else if(Integer.valueOf(tipoCliente).intValue() == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.intValue()
								&& Util.isVazioOuBranco(numeroCnpj)){
					if(idCliente != null){
						throw new ControladorException("atencao.cliente_sem_cpf_cnpj", null, idCliente);
					}
					throw new ControladorException("atencao.informe_campo", null, "Cnpj");
				}

				if(Integer.valueOf(tipoCliente).intValue() == ConstantesSistema.INDICADOR_PESSOA_FISICA.intValue()
								&& solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA() != null
								&& solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA().intValue() == ConstantesSistema.SIM
												.intValue()){
					if(Util.isVazioOuBranco(numeroRg)){
						throw new ControladorException("atencao.informe_campo", null, "Rg");
					}
					if(Integer.valueOf(orgaoExpedidorRg).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
						throw new ControladorException("atencao.informe_campo", null, "Órgão Expedidor");
					}
					if(Integer.valueOf(unidadeFederacaoRG).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
						throw new ControladorException("atencao.informe_campo", null, "Estado");
					}
					FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
					filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(filtroOrgaoExpedidorRg.ID, orgaoExpedidorRg));
					Collection<OrgaoExpedidorRg> colecaoOrgaoExpedidorRgs = getControladorUtil().pesquisar(filtroOrgaoExpedidorRg,
									OrgaoExpedidorRg.class.getName());
					if(colecaoOrgaoExpedidorRgs == null || colecaoOrgaoExpedidorRgs.isEmpty()){
						throw new ControladorException("atencao.rg_campos_relacionados.nao_preenchidos");
					}
					FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
					filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(filtroUnidadeFederacao.ID, unidadeFederacaoRG));
					Collection<UnidadeFederacao> colecaoUnidadeFederacao = getControladorUtil().pesquisar(filtroUnidadeFederacao,
									UnidadeFederacao.class.getName());
					if(colecaoUnidadeFederacao == null || colecaoUnidadeFederacao.isEmpty()){
						throw new ControladorException("atencao.rg_campos_relacionados.nao_preenchidos");
					}
				}
			}
			// Faz a verificação do grupo do RG, onde se um campo for preenchido, todos terão
			// que ser preenchidos
			if(Util.isVazioOuBranco(numeroRg) && Integer.valueOf(orgaoExpedidorRg).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO
							&& Integer.valueOf(unidadeFederacaoRG).intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
			}else{
				if(numeroRg != null && Integer.valueOf(orgaoExpedidorRg).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Integer.valueOf(unidadeFederacaoRG).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				}else{
					throw new ControladorException("atencao.rg_campos_relacionados.nao_preenchidos");
				}
			}

			if(idCliente == null || idCliente.equals("-1")){
				if(solicitacaoTipoEspecificacao.getIndicadorCliente() != null
								&& solicitacaoTipoEspecificacao.getIndicadorCliente() == ConstantesSistema.SIM){
					throw new ControladorException("atencao.especificacao_cliente_obrigatorio");
				}
			}
		}

	}

	/**
	 * [UC0XXX] Relatório Resumo de Registro de Atendimento
	 * Obter dados do Relatório Resumo de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @date 15/03/2011
	 */
	public Collection pesquisaRelatorioResumoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoAgrupamento) throws ControladorException{

		Collection retorno = null;

		try{

			retorno = repositorioRegistroAtendimento.pesquisaRelatorioResumoRA(filtroRA, tipoAgrupamento);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Estatistica Atendimento por Atendente
	 * Obter dados do Relatório Estatistica Atendimento por Atendente pelos parametros informados no
	 * filtro
	 * [0] - idUnidade;
	 * [1] - descricaoUnidade;
	 * [2] - idUsuario;
	 * [3] - nomeUsuario;
	 * [4] - dataRegistroAtendimento;
	 * [5] - ultimaAlteracaoUnidade
	 * 
	 * @author isilva
	 * @date 24/03/2011
	 */
	public Collection pesquisaRelatorioEstatisticaAtendimentoPorAtendente(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ControladorException{

		Collection retorno = null;

		try{

			retorno = repositorioRegistroAtendimento.pesquisaRelatorioEstatisticaAtendimentoPorAtendente(filtroRA);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	/**
	 * [UC0XXX] Relatório Gestão de Registro de Atendimento
	 * Obter dados do Relatório de Gestão de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @param tipoRelatorio
	 *            (0 == Sintético e 1 == Analítico),
	 *            FiltrarRegistroAtendimentoHelper filtroRA
	 * @date 28/03/2011
	 */
	public Collection pesquisaRelatorioGestaoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoRelatorio) throws ControladorException{

		Collection retorno = null;

		try{

			retorno = repositorioRegistroAtendimento.pesquisaRelatorioGestaoRA(filtroRA, tipoRelatorio);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UCXXXX] Inserir Trâmite por Especificação
	 * 
	 * @author Ailton Sousa
	 * @date 04/04/2011
	 * @param colecaoTramiteEspecificacao
	 *            , usuarioLogado
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirTramiteEspecificacao(Collection colecaoTramiteEspecificacao, Usuario usuarioLogado) throws ControladorException{

		Integer quatidadeInserida = Integer.valueOf("0");
		String tamanhoColecao = "";

		if(colecaoTramiteEspecificacao.size() == 1){
			tamanhoColecao = "1";
		}else{
			tamanhoColecao = "2";
		}

		Iterator iteratorTramiteEspecificacao = colecaoTramiteEspecificacao.iterator();

		while(iteratorTramiteEspecificacao.hasNext()){
			EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) iteratorTramiteEspecificacao.next();

			// Verifica duplicidade dos dados
			Collection<Integer> ids = null;

			try{
				ids = repositorioRegistroAtendimento.pesquisarTramiteEspecificacao(especificacaoTramite);
			}catch(ErroRepositorioException ex){
				throw new ControladorException("erro.sistema", ex);
			}

			quatidadeInserida = quatidadeInserida + 1;

			if(ids != null && !ids.isEmpty()){
				sessionContext.setRollbackOnly();
				if(tamanhoColecao.equals("1")){
					throw new ControladorException("atencao.especificacao_tramite_ja_existente");
				}else{
					throw new ControladorException("atencao.especificacao_tramite_colecao_ja_existente", null, quatidadeInserida.toString());
				}
			}

			// Inseri Trâmite por Especificacao
			getControladorUtil().inserir(especificacaoTramite);
		}

		return quatidadeInserida;
	}

	/**
	 * Remover Trâmite Especificação
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2011
	 */
	public void removerTramiteEspecificacao(String[] ids) throws ControladorException{

		for(int i = 0; i < ids.length; i++){
			Integer id = Integer.parseInt(ids[i]);

			FiltroTramiteEspecificacao filtro = new FiltroTramiteEspecificacao();
			filtro.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.ID, id));

			Collection colecaoRetorno = getControladorUtil().pesquisar(filtro, EspecificacaoTramite.class.getName());

			EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) Util.retonarObjetoDeColecao(colecaoRetorno);

			this.getControladorUtil().remover(especificacaoTramite);
		}
	}

	/**
	 * Atualizar Trâmite Especificação
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2011
	 */
	public void atualizarTramiteEspecificacao(EspecificacaoTramite especificacaoTramite) throws ControladorException{

		// Validação de campos obrigatórios
		Integer id = especificacaoTramite.getId();
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = especificacaoTramite.getSolicitacaoTipoEspecificacao();
		UnidadeOrganizacional unidadeOrganizacionalDestino = especificacaoTramite.getUnidadeOrganizacionalDestino();
		Short indicadorUso = especificacaoTramite.getIndicadorUso();

		if(id == null){
			throw new ControladorException("atencao.required", null, "Id");
		}

		if(solicitacaoTipoEspecificacao == null || solicitacaoTipoEspecificacao.getId() == null){
			throw new ControladorException("atencao.required", null, "Especificação");
		}

		if(unidadeOrganizacionalDestino == null || unidadeOrganizacionalDestino.getId() == null){
			throw new ControladorException("atencao.required", null, "Unidade Destino");
		}

		if(indicadorUso == null){
			throw new ControladorException("atencao.required", null, "Indicador de Uso");
		}

		// Verifica duplicidade dos dados
		Collection<Integer> ids = null;

		try{
			ids = repositorioRegistroAtendimento.pesquisarTramiteEspecificacao(especificacaoTramite);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		if(ids != null && !ids.isEmpty()){
			throw new ControladorException("atencao.especificacao_tramite_ja_existente");
		}

		// Verificar se a especificação trâmite já foi atualizada por outro usuário durante esta
		// atualização
		FiltroTramiteEspecificacao filtroTramiteEspecificacao = new FiltroTramiteEspecificacao();
		filtroTramiteEspecificacao.adicionarParametro(new ParametroSimples(FiltroTramiteEspecificacao.ID, id));

		Collection<EspecificacaoTramite> colecaoEspecificacaoTramite = getControladorUtil().pesquisar(filtroTramiteEspecificacao,
						EspecificacaoTramite.class.getName());
		EspecificacaoTramite especificacaoTramiteNaBase = (EspecificacaoTramite) Util.retonarObjetoDeColecao(colecaoEspecificacaoTramite);

		if(especificacaoTramiteNaBase == null){
			// Verifica se existe na base
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}else if(especificacaoTramiteNaBase.getUltimaAlteracao().after(especificacaoTramite.getUltimaAlteracao())){
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		especificacaoTramite.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(especificacaoTramite);
	}

	/**
	 * Pesquisar Unidades de Destino por EspecificacaoTramite
	 * 
	 * @author isilva
	 * @date 13/04/2011
	 * @param especificacaoTramite
	 * @return
	 * @throws ControladorException
	 */
	public Collection<UnidadeOrganizacional> obterUnidadeDestinoPorEspecificacao(EspecificacaoTramite especificacaoTramite,
					boolean checarIndicadorPrimeiroTramite) throws ControladorException{

		Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = null;

		try{
			colecaoUnidadeOrganizacional = repositorioRegistroAtendimento.pesquisarUnidadeDestinoPorEspecificacao(especificacaoTramite,
							checarIndicadorPrimeiroTramite);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoUnidadeOrganizacional;
	}

	/**
	 * @author isilva
	 * @date 04/05/2011
	 * @param idsRegistrosRemocao
	 * @throws ControladorException
	 */
	public void removerSolicitacaoTipoEDependencias(String[] idsRegistrosRemocao) throws ControladorException{

		try{
			if(idsRegistrosRemocao != null && idsRegistrosRemocao.length != 0){

				for(String idSolicitacaoTipo : idsRegistrosRemocao){
					// Para cada SolicitacaoTipo
					Collection<Object[]> retornoConsulta = (ArrayList<Object[]>) repositorioRegistroAtendimento
									.pesquisarDependenciasSolicitacaoTipo(Integer.valueOf(idSolicitacaoTipo));

					if(retornoConsulta == null || retornoConsulta.isEmpty()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.registro_remocao_nao_existente");
						// erro de restrição na base
					}

					Map<Integer, SolicitacaoTipoEspecificacao> mapSolicitacaoTipoEspecificacaoRemover = new TreeMap<Integer, SolicitacaoTipoEspecificacao>();

					for(Object[] retorno : retornoConsulta){
						// Remove Todas as EspecificacaoServicoTipo
						if(retorno[1] != null){
							this.getControladorUtil().remover(retorno[1]);
						}

						// Guarda identificador da SolicitacaoTipoEspecificacao para ser Removida
						if(retorno[0] != null){
							if(!mapSolicitacaoTipoEspecificacaoRemover.containsKey(((SolicitacaoTipoEspecificacao) (retorno[0])).getId())){
								mapSolicitacaoTipoEspecificacaoRemover.put(((SolicitacaoTipoEspecificacao) (retorno[0])).getId(),
												(SolicitacaoTipoEspecificacao) retorno[0]);
							}
						}
					}

					if(mapSolicitacaoTipoEspecificacaoRemover != null && !mapSolicitacaoTipoEspecificacaoRemover.isEmpty()){
						// Remove Todas as SolicitacaoTipoEspecificacao
						Set set = mapSolicitacaoTipoEspecificacaoRemover.keySet();
						Iterator iterMap = set.iterator();
						while(iterMap.hasNext()){
							Integer key = (Integer) iterMap.next();
							this.getControladorUtil().remover(mapSolicitacaoTipoEspecificacaoRemover.get(key));
						}
					}

					// ********** Remove Todas as SolicitacaoTipo **********
					this.getControladorUtil().removerUm(Integer.valueOf(idSolicitacaoTipo), SolicitacaoTipo.class.getName(), null, null);
				}

			}
		}catch(NumberFormatException e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Verifica se o imovel e/ou o cliente possuem débitos
	 * 
	 * @author Isaac Silva
	 * @date 20/06/2011
	 * @param idSolicitacaoTipoEspecificacao
	 * @param idImovel
	 * @throws ControladorException
	 */
	public void verificarDebitosImovelCliente(Integer idSolicitacaoTipoEspecificacao, Integer idImovel, Integer idCliente)
					throws ControladorException{

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						idSolicitacaoTipoEspecificacao));

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(this
						.getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));

		if(solicitacaoTipoEspecificacao != null){

			if(solicitacaoTipoEspecificacao.getIndicadorVerificarDebito() != null
							&& solicitacaoTipoEspecificacao.getIndicadorVerificarDebito().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO
											.intValue()){

				Date dataVencimentoInicial = Util.criarData(1, 1, 0001);

				Integer numeroDiasSubtrairData = Util.obterInteger(ParametroAtendimentoPublico.P_NUMERO_DIAS_DATA_VENCIMENTO.executar());

				Date dataVencimentoFinal = Util.subtrairNumeroDiasDeUmaData(new Date(), numeroDiasSubtrairData);

				if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito() != null){
					if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().intValue() == SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS
									.intValue()){

						if(idImovel != null){

							// [UC0067] Obter Débito do Imóvel ou Cliente
							ObterDebitoImovelOuClienteHelper imovelDebitos = this.getControladorCobranca().obterDebitoImovelOuCliente(1,
											idImovel.toString(), null, null, "000101", "999912", dataVencimentoInicial,
											dataVencimentoFinal, 2, 2, 2, 2, 2, 1, 1, true, null, null, null, null, ConstantesSistema.SIM,
											ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

							Collection<ContaValoresHelper> colecaoContasValores = imovelDebitos.getColecaoContasValores();
							Collection<DebitoACobrar> colecaoDebitoACobrar = imovelDebitos.getColecaoDebitoACobrar();
							Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = imovelDebitos
											.getColecaoGuiasPagamentoValores();
							Collection<CreditoARealizar> colecaoCreditoARealizar = null;

							// [SB0041 - Verificar Titularidade do Débito]
							this.verificarTitularidadeDebito(idImovel,
											solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual(),
											solicitacaoTipoEspecificacao.getClienteRelacaoTipo(), imovelDebitos, colecaoContasValores,
											colecaoDebitoACobrar, colecaoGuiasPagamentoValores, colecaoCreditoARealizar);

							/*
							 * [FS0043] – Verifica imóvel com débito
							 * Caso a lista de contas retornada pelo [UC0067] não esteja
							 * vazia,retirar as contas não vencidas
							 */
							removerContasNaoVencidasVerificacaoDebitos(imovelDebitos);

							if(!Util.isVazioOrNulo(imovelDebitos.getColecaoContasValores())
											|| !Util.isVazioOrNulo(imovelDebitos.getColecaoContasValoresImovel())
											|| !Util.isVazioOrNulo(imovelDebitos.getColecaoGuiasPagamentoValores())){

								throw new ControladorException("atencao.imovel_com_debitos", null, idImovel.toString(),
												solicitacaoTipoEspecificacao.getDescricao());
							}
						}

					}else if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().intValue() == SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_CLIENTES
									.intValue()){

						if(idCliente != null){

							ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca()
											.obterDebitoImovelOuCliente(2, null, idCliente.toString(), null, "000101", "999912",
															dataVencimentoInicial, dataVencimentoFinal, 2, 2, 2, 2, 2, 1, 1, null, null,
															null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
															ConstantesSistema.SIM, 2, null);

							if(obterDebitoImovelOuClienteHelper != null){

								/*
								 * [FS0043] – Verifica imóvel com débito
								 * Caso a lista de contas retornada pelo [UC0067] não esteja
								 * vazia,retirar as contas não vencidas
								 */
								removerContasNaoVencidasVerificacaoDebitos(obterDebitoImovelOuClienteHelper);

								if(!Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoContasValores())
												|| !Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel())
												|| !Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores())){
									throw new ControladorException("atencao.cliente_com_debitos", null, idCliente.toString(),
													solicitacaoTipoEspecificacao.getDescricao());
								}
							}
						}

					}else if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().intValue() == SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS
									.intValue()){

						boolean existeDebitoImovel = false;
						boolean existeDebitoCliente = false;

						if(idImovel != null){

							// [UC0067] Obter Débito do Imóvel ou Cliente
							ObterDebitoImovelOuClienteHelper imovelDebitos = this.getControladorCobranca().obterDebitoImovelOuCliente(1,
											idImovel.toString(), null, null, "000101", "999912", dataVencimentoInicial,
											dataVencimentoFinal, 2, 2, 2, 2, 2, 1, 1, true, null, null, null, null, ConstantesSistema.SIM,
											ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

							Collection<ContaValoresHelper> colecaoContasValores = imovelDebitos.getColecaoContasValores();
							Collection<DebitoACobrar> colecaoDebitoACobrar = imovelDebitos.getColecaoDebitoACobrar();
							Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = imovelDebitos
											.getColecaoGuiasPagamentoValores();
							Collection<CreditoARealizar> colecaoCreditoARealizar = null;

							// [SB0041 - Verificar Titularidade do Débito]
							this.verificarTitularidadeDebito(idImovel,
											solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual(),
											solicitacaoTipoEspecificacao.getClienteRelacaoTipo(), imovelDebitos, colecaoContasValores,
											colecaoDebitoACobrar, colecaoGuiasPagamentoValores, colecaoCreditoARealizar);

							/*
							 * [FS0043] – Verifica imóvel com débito
							 * Caso a lista de contas retornada pelo [UC0067] não esteja
							 * vazia,retirar as contas não vencidas
							 */
							removerContasNaoVencidasVerificacaoDebitos(imovelDebitos);

							if(!Util.isVazioOrNulo(imovelDebitos.getColecaoContasValores())
											|| !Util.isVazioOrNulo(imovelDebitos.getColecaoContasValoresImovel())
											|| !Util.isVazioOrNulo(imovelDebitos.getColecaoGuiasPagamentoValores())){

								existeDebitoImovel = true;
							}
						}

						// ********************** Verifica Débitos do Cliente *********************

						if(idCliente != null){
							ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca()
											.obterDebitoImovelOuCliente(2, null, idCliente.toString(), null, "000101", "999912",
															dataVencimentoInicial, dataVencimentoFinal, 2, 2, 2, 2, 2, 1, 1, null, null,
															null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
															ConstantesSistema.SIM, 2, null);

							if(obterDebitoImovelOuClienteHelper != null){

								/*
								 * [FS0043] – Verifica imóvel com débito
								 * Caso a lista de contas retornada pelo [UC0067] não esteja
								 * vazia,retirar as contas não vencidas
								 */
								removerContasNaoVencidasVerificacaoDebitos(obterDebitoImovelOuClienteHelper);

								if(!Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoContasValores())
												|| !Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel())
												|| !Util.isVazioOrNulo(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores())){
									existeDebitoCliente = true;
								}
							}
						}

						if(existeDebitoImovel == true && existeDebitoCliente == true){
							throw new ControladorException("atencao.cliente_imovel_com_debitos", null, idCliente.toString(),
											idImovel.toString(), solicitacaoTipoEspecificacao.getDescricao());

						}else if(existeDebitoImovel){
							throw new ControladorException("atencao.imovel_com_debitos", null, idImovel.toString(),
											solicitacaoTipoEspecificacao.getDescricao());
						}else if(existeDebitoCliente){
							throw new ControladorException("atencao.cliente_com_debitos", null, idCliente.toString(),
											solicitacaoTipoEspecificacao.getDescricao());
						}

					}
				}
			}
		}
	}

	/**
	 * [UC0366] - Inserir Registro de Atendimento
	 * [SB0041] - Verificar Titularidade do Débito]
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * [UC3153] - Verificar Titularidade Débito/Crédito
	 * 
	 * @author Anderson Italo
	 * @created 16/01/2014
	 */
	public void verificarTitularidadeDebito(Integer idImovel, Short indicadorConsiderarApenasDebitoTitularAtual,
					ClienteRelacaoTipo clienteRelacaoTipo, ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper,
					Collection<ContaValoresHelper> colecaoContasValores, Collection<DebitoACobrar> colecaoDebitoACobrar,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores,
					Collection<CreditoARealizar> colecaoCreditoARealizar) throws ControladorException{

		// Caso esteja indicado para considerar apenas o débito do titular atual do imóvel
		if(indicadorConsiderarApenasDebitoTitularAtual.equals(ConstantesSistema.SIM)){

			// Caso o tipo do titular atual do débito do imóvel seja o cliente indicado para
			// emissão na conta
			if(clienteRelacaoTipo == null){

				try{

					Collection<ContaValoresHelper> colecaoContasValoresHelperRemover = new ArrayList();
					Collection<ClienteConta> colecaoClienteConta = null;
					Cliente clienteComNomeConta = repositorioClienteImovel.pesquisarClienteImovelNomeConta(idImovel);

					if(!Util.isVazioOrNulo(colecaoContasValores)){

						// Retirar da lista de contas retornada pelo [UC0067] as contas emitidas
						// para cliente diverso do cliente atual indicado para emissão na conta
						HashMap<Integer, ClienteConta> mapClienteContaComNomeConta = new HashMap<Integer, ClienteConta>();

						for(ContaValoresHelper contaValoresHelper : colecaoContasValores){

							colecaoClienteConta = repositorioFaturamento.pesquisarClienteConta(contaValoresHelper.getConta().getId());

							for(ClienteConta clienteConta : colecaoClienteConta){

								if(clienteConta.getIndicadorNomeConta().equals(ConstantesSistema.SIM)){

									mapClienteContaComNomeConta.put(contaValoresHelper.getConta().getId(), clienteConta);
									break;
								}
							}
						}

						if(mapClienteContaComNomeConta.isEmpty() || clienteComNomeConta == null){

							colecaoContasValoresHelperRemover.addAll(colecaoContasValores);
						}else{

							for(ContaValoresHelper contaValoresHelper : colecaoContasValores){

								ClienteConta clienteConta = mapClienteContaComNomeConta.get(contaValoresHelper.getConta().getId());

								if(clienteConta == null || !clienteConta.getCliente().getId().equals(clienteComNomeConta.getId())){

									colecaoContasValoresHelperRemover.add(contaValoresHelper);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoContasValoresHelperRemover)){

							colecaoContasValores.removeAll(colecaoContasValoresHelperRemover);
						}
					}

					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperRemover = new ArrayList();
					Integer idClienteGuiaPagamento = null;

					if(!Util.isVazioOrNulo(colecaoGuiasPagamentoValores)){

						// Retirar da lista de guias retornada pelo [UC0067] as guias de pagamento
						// com cliente diverso do cliente atual indicado para emissão na conta
						if(clienteComNomeConta == null){

							colecaoGuiaPagamentoValoresHelperRemover.addAll(colecaoGuiasPagamentoValores);
						}else{

							for(GuiaPagamentoValoresHelper guiaValoresHelper : colecaoGuiasPagamentoValores){

								idClienteGuiaPagamento = repositorioCobranca.pesquisarIdClienteGuiaPagamentoComNomeConta(guiaValoresHelper
												.getIdGuiaPagamento());

								if(idClienteGuiaPagamento == null || !idClienteGuiaPagamento.equals(clienteComNomeConta.getId())){

									colecaoGuiaPagamentoValoresHelperRemover.add(guiaValoresHelper);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValoresHelperRemover)){

							colecaoGuiasPagamentoValores.removeAll(colecaoGuiaPagamentoValoresHelperRemover);
						}
					}

					Collection<DebitoACobrar> colecaoDebitoACobrarRemover = new ArrayList();
					Integer idClienteDebitoACobrar = null;

					if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

						// Retirar da lista de débitos a cobrar retornada pelo [UC0067] os débitos a
						// cobrar com cliente diverso do cliente atual indicado para emissão na
						// conta
						if(clienteComNomeConta == null){

							colecaoDebitoACobrarRemover.addAll(colecaoDebitoACobrar);
						}else{
							for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

								idClienteDebitoACobrar = repositorioCobranca.pesquisarIdClienteDebitoACobrarComNomeConta(debitoACobrar
												.getId());

								if(idClienteDebitoACobrar == null || !idClienteDebitoACobrar.equals(clienteComNomeConta.getId())){

									colecaoDebitoACobrarRemover.add(debitoACobrar);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoDebitoACobrarRemover)){

							colecaoDebitoACobrar.removeAll(colecaoDebitoACobrarRemover);
						}
					}

					Collection<CreditoARealizar> colecaoCreditoARealizarRemover = new ArrayList();
					Integer idClienteCreditoARealizar = null;

					if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){

						// Retirar da lista de creditos a realizar retornada pelo [UC0067] os
						// débitos a
						// cobrar com cliente diverso do cliente atual indicado para emissão na
						// conta
						if(clienteComNomeConta == null){

							colecaoCreditoARealizarRemover.addAll(colecaoCreditoARealizar);
						}else{
							for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){

								idClienteCreditoARealizar = repositorioCobranca
												.pesquisarIdClienteCreditoARealizarComNomeConta(creditoARealizar.getId());

								if(idClienteCreditoARealizar == null || !idClienteCreditoARealizar.equals(clienteComNomeConta.getId())){

									colecaoCreditoARealizarRemover.add(creditoARealizar);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoCreditoARealizarRemover)){

							colecaoCreditoARealizar.removeAll(colecaoCreditoARealizarRemover);
						}
					}

				}catch(ErroRepositorioException e){

					e.printStackTrace();
					throw new ControladorException("erro.sistema", e);
				}
			}else{

				// Caso contrário, ou seja, o tipo do titular atual do débito do imóvel não seja
				// o cliente indicado para emissão na conta
				try{

					Collection<ContaValoresHelper> colecaoContasValoresHelperRemover = new ArrayList();
					Collection<ClienteConta> colecaoClienteConta = null;
					Integer idClienteTitularIndicado = repositorioCobranca.pesquisarIdClienteImovelPorTipoRelacao(idImovel,
									clienteRelacaoTipo.getId());

					if(!Util.isVazioOrNulo(colecaoContasValores)){

						// Retirar da lista de contas retornada pelo [UC0067] as contas com cliente
						// diverso do cliente indicado como atual titular do débito do imóvel
						HashMap<Integer, ClienteConta> mapClienteContaComTipoRelacaoIndicado = new HashMap<Integer, ClienteConta>();

						for(ContaValoresHelper contaValoresHelper : colecaoContasValores){

							colecaoClienteConta = repositorioFaturamento.pesquisarClienteConta(contaValoresHelper.getConta().getId());

							for(ClienteConta clienteConta : colecaoClienteConta){

								if(clienteConta.getClienteRelacaoTipo().getId().equals(clienteRelacaoTipo.getId())){

									mapClienteContaComTipoRelacaoIndicado.put(contaValoresHelper.getConta().getId(), clienteConta);
									break;
								}
							}
						}

						if(mapClienteContaComTipoRelacaoIndicado.isEmpty() || idClienteTitularIndicado == null){

							colecaoContasValoresHelperRemover.addAll(colecaoContasValores);
						}else{

							for(ContaValoresHelper contaValoresHelper : colecaoContasValores){

								ClienteConta clienteConta = mapClienteContaComTipoRelacaoIndicado
												.get(contaValoresHelper.getConta().getId());

								if(clienteConta == null || !clienteConta.getCliente().getId().equals(idClienteTitularIndicado)){

									colecaoContasValoresHelperRemover.add(contaValoresHelper);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoContasValoresHelperRemover)){

							colecaoContasValores.removeAll(colecaoContasValoresHelperRemover);
						}
					}

					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperRemover = new ArrayList();
					Integer idClienteGuiaPagamento = null;

					if(!Util.isVazioOrNulo(colecaoGuiasPagamentoValores)){

						// Retirar da lista de guias retornada pelo [UC0067] as guias de pagamento
						// com cliente diverso do cliente indicado como atual titular do débito do
						// imóvel
						if(idClienteTitularIndicado == null){

							colecaoGuiaPagamentoValoresHelperRemover.addAll(colecaoGuiasPagamentoValores);
						}else{

							for(GuiaPagamentoValoresHelper guiaValoresHelper : colecaoGuiasPagamentoValores){

								idClienteGuiaPagamento = repositorioCobranca.pesquisarIdClienteGuiaPagamentoTitularRelacao(
												guiaValoresHelper.getIdGuiaPagamento(), clienteRelacaoTipo.getId());

								if(idClienteGuiaPagamento == null || !idClienteGuiaPagamento.equals(idClienteTitularIndicado)){

									colecaoGuiaPagamentoValoresHelperRemover.add(guiaValoresHelper);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValoresHelperRemover)){

							colecaoGuiasPagamentoValores.removeAll(colecaoGuiaPagamentoValoresHelperRemover);
						}
					}

					Collection<DebitoACobrar> colecaoDebitoACobrarRemover = new ArrayList();
					Integer idClienteDebitoACobrar = null;

					if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

						// Retirar da lista de débitos a cobrar retornada pelo [UC0067] os débitos a
						// cobrar com cliente diverso do cliente indicado como atual titular do
						// débito do imóvel
						if(idClienteTitularIndicado == null){

							colecaoDebitoACobrarRemover.addAll(colecaoDebitoACobrar);
						}else{
							for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

								idClienteDebitoACobrar = repositorioCobranca.pesquisarIdClienteDebitoACobrarTitularRelacao(
												debitoACobrar.getId(), clienteRelacaoTipo.getId());

								if(idClienteDebitoACobrar == null || !idClienteDebitoACobrar.equals(idClienteTitularIndicado)){

									colecaoDebitoACobrarRemover.add(debitoACobrar);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoDebitoACobrarRemover)){

							colecaoDebitoACobrar.removeAll(colecaoDebitoACobrarRemover);
						}
					}

					Collection<CreditoARealizar> colecaoCreditoARealizarRemover = new ArrayList();
					Integer idClienteCreditoARealizar = null;

					if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){

						// Retirar da lista de créditos a realizar retornada pelo [UC0067] os
						// débitos a
						// cobrar com cliente diverso do cliente indicado como atual titular do
						// débito do imóvel
						if(idClienteTitularIndicado == null){

							colecaoCreditoARealizarRemover.addAll(colecaoCreditoARealizar);
						}else{
							for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){

								idClienteCreditoARealizar = repositorioCobranca.pesquisarIdClienteCreditoARealizarTitularRelacao(
												creditoARealizar.getId(), clienteRelacaoTipo.getId());

								if(idClienteCreditoARealizar == null || !idClienteCreditoARealizar.equals(idClienteTitularIndicado)){

									colecaoCreditoARealizarRemover.add(creditoARealizar);
								}
							}
						}

						if(!Util.isVazioOrNulo(colecaoCreditoARealizarRemover)){

							colecaoCreditoARealizar.removeAll(colecaoCreditoARealizarRemover);
						}
					}

				}catch(ErroRepositorioException e){

					e.printStackTrace();
					throw new ControladorException("erro.sistema", e);
				}
			}

			if(obterDebitoImovelOuClienteHelper != null){
				obterDebitoImovelOuClienteHelper.setColecaoContasValores(colecaoContasValores);
				obterDebitoImovelOuClienteHelper.setColecaoDebitoACobrar(colecaoDebitoACobrar);
				obterDebitoImovelOuClienteHelper.setColecaoGuiasPagamentoValores(colecaoGuiasPagamentoValores);
				obterDebitoImovelOuClienteHelper.setColecaoCreditoARealizar(colecaoCreditoARealizar);
			}

		}
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento e Ordem de servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */

	public void tramitarRAOS(Tramite tramite, Date dataConcorrente, Usuario usuario) throws ControladorException{

		this.tramitar(tramite, dataConcorrente);

		this.getControladorOrdemServico().tramitarOSRA(tramite, usuario);

	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento e Ordem de servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */

	public void tramitarColecaoRAOS(Collection tramites, Usuario usuario) throws ControladorException{

		Collection tramitesRAOS = new ArrayList();

		// Extrai uma sublista contendo os registros que precisam ser tramitados com suas
		// respectivas
		// OS
		for(Object object : tramites){
			Tramite tramite = (Tramite) object;
			if(!Util.isVazioOuBranco(tramite.getIndicadorTramiteApenasRA())
							&& tramite.getIndicadorTramiteApenasRA().equals(ConstantesSistema.NAO.toString())){
				tramitesRAOS.add(tramite);
			}
		}

		// Tramita todos os registros para RA
		this.tramitarConjuntoRA(tramites);

		// Tramita os registros para RA e OS
		if(!Util.isVazioOrNulo(tramitesRAOS)){
			this.getControladorOrdemServico().tramitarConjuntoOSRA(tramitesRAOS, usuario);
		}

	}

	/**
	 * Gerar Coleção de Ordem de Serviço Automâtica
	 * 
	 * @author Hebert Falcão
	 * @date 17/12/2012
	 */
	public Collection<OrdemServico> gerarColecaoOrdemServicoAutomatica(Collection<Integer> idsServicoTipo, Integer idSolicitacaoTipo)
					throws ControladorException{

		OrdemServico ordemServico = null;
		Collection<OrdemServico> colecaoOrdemServico = null;

		for(Integer idServicoTipo : idsServicoTipo){
			if(colecaoOrdemServico == null){
				colecaoOrdemServico = new ArrayList<OrdemServico>();
			}

			ordemServico = this.gerarOrdemServicoAutomatica(idServicoTipo, idSolicitacaoTipo);
			colecaoOrdemServico.add(ordemServico);
		}

		return colecaoOrdemServico;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Verifica se o meio da solicitação permite liberação do preenchimento do campo de documento da
	 * aba solicitante do caso de uso
	 * 
	 * @author Hugo Lima
	 * @date 24/04/2012
	 * @param idMeioSolicitacao
	 * @return
	 * @throws ControladorException
	 */
	public boolean isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(Integer idMeioSolicitacao) throws ControladorException{

		MeioSolicitacao meioSolicitacao = null;

		try{
			// Consulta o meio de solicitação passado no parametro
			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.ID, idMeioSolicitacao));

			Collection<MeioSolicitacao> retornoConsulta = Fachada.getInstancia().pesquisar(filtroMeioSolicitacao,
							MeioSolicitacao.class.getName());

			if(!Util.isVazioOrNulo(retornoConsulta)){
				meioSolicitacao = (MeioSolicitacao) Util.retonarObjetoDeColecao(retornoConsulta);

				// Verifica se o meio de solicitação permite a liberação do preenchimento do campo
				// de documento de identificação através de seu indicador de liberação
				if(meioSolicitacao.getIndicadorLiberacaoDocIdent().equals(ConstantesSistema.SIM)){
					return true;
				}else{
					return false;
				}

			}else{
				return false;
			}

		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [FS0043] – Verifica imóvel com débito
	 * 
	 * @author Anderson Italo
	 * @date 08/05/2012
	 */
	private void removerContasNaoVencidasVerificacaoDebitos(ObterDebitoImovelOuClienteHelper imovelDebitos){

		Collection<ContaValoresHelper> colecaoContasRemover = new ArrayList<ContaValoresHelper>();

		if(!Util.isVazioOrNulo(imovelDebitos.getColecaoContasValores())){

			for(ContaValoresHelper contaHelper : imovelDebitos.getColecaoContasValores()){

				if(contaHelper.getConta().getDataVencimentoConta().compareTo(new Date()) == 0
								|| contaHelper.getConta().getDataVencimentoConta().compareTo(new Date()) > 0){

					colecaoContasRemover.add(contaHelper);
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoContasRemover)){

			imovelDebitos.getColecaoContasValores().removeAll(colecaoContasRemover);
		}

		colecaoContasRemover.clear();

		if(!Util.isVazioOrNulo(imovelDebitos.getColecaoContasValoresImovel())){

			for(ContaValoresHelper contaHelper : imovelDebitos.getColecaoContasValoresImovel()){

				if(contaHelper.getConta().getDataVencimentoConta().compareTo(new Date()) == 0
								|| contaHelper.getConta().getDataVencimentoConta().compareTo(new Date()) > 0){

					colecaoContasRemover.add(contaHelper);
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoContasRemover)){

			imovelDebitos.getColecaoContasValoresImovel().removeAll(colecaoContasRemover);
		}
	}

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idRA
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosLocalizacaoRegistroAtendimento(Integer idRA) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisarDadosLocalizacaoRegistroAtendimento(idRA);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Registra um atendimento do tipo Online.
	 * 
	 * @param matricula
	 * @param cpfcnpj
	 * @param pontoReferencia
	 * @param nomeSolicitante
	 * @param foneSolicitante
	 * @param emailSolicitante
	 * @param idSolicitacaoTipoEspecificacao
	 * @param idMunicio
	 * @param idBairro
	 * @param idLogradouro
	 * @param numero
	 * @param descricao
	 * @param tipoPavimentoRua
	 * @return Numero do Registro de Atendimento
	 * @throws ControladorException
	 * @throws NegocioException
	 */
	public Integer inserirRegistroAtendimento(Integer matricula, String cpfcnpj, String pontoReferencia, String nomeSolicitante,
					String foneSolicitante, String emailSolicitante, Integer idSolicitacaoTipoEspecificacao, Integer idMunicio,
					Integer idBairro, Integer idLogradouro, Integer numero, String descricao, Integer tipoPavimentoRua)
					throws ControladorException, NegocioException{

		Imovel imovel = Util.isVazioOuBranco(matricula) ? null : ServiceLocator.getInstancia().getControladorImovel()
						.pesquisarImovel(matricula);
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = buscarSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);
		if(solicitacaoTipoEspecificacao == null){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.naocadastrado", "Especificação do Tipo de Solicitação");
		}

		// VALIDAR VINCULO DE IMOVEL E CPF / CNPJ (SE INFORMADOS) e Indicador de Cliente ser
		// Obrigatório
		if((!Util.isVazioOuBranco(matricula) || !Util.isVazioOuBranco(cpfcnpj))
						&& solicitacaoTipoEspecificacao.getIndicadorCliente().equals(ConstantesSistema.SIM)){
			ServiceLocator.getInstancia().getControladorImovel().validarPermissaoClienteImovel(cpfcnpj, String.valueOf(matricula));
		}

		Date dataAtendimento = Calendar.getInstance().getTime();
		Collection colecaoEnderecoImovel = Util.isVazioOuBranco(matricula) ? null : buscarEnderecosImovel(matricula);
		Cliente cliente = Util.isVazioOuBranco(matricula) || Util.isVazioOuBranco(cpfcnpj) ? null : obterClienteImovel(matricula, cpfcnpj);

		Integer idMeioSolicitacao = Util.obterInteger(ParametrosAgenciaVirtual.P_AV_MEIO_SOLICITACAO.executar());
		Date dataPrevista = definirDataPrevistaRA(dataAtendimento, idSolicitacaoTipoEspecificacao);
		String dataPrevistaFormatada = Util.formatarData(dataPrevista) + " " + Util.formatarHoraSemSegundos(dataPrevista);
		Integer idSolicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId();
		Integer idLocalidade = imovel == null ? null : imovel.getLocalidade().getId();
		Integer idSetorComercial = imovel == null ? null : imovel.getSetorComercial().getId();
		Integer idQuadra = imovel == null ? null : imovel.getQuadra().getId();
		Integer unidadeOrganizacionalAbertura = Util.obterInteger(ParametrosAgenciaVirtual.P_AV_UNIDADE_ORGANIZACIONAL_ABERTURA.executar());
		Integer usuarioAbertura = Usuario.USUARIO_AGENCIA_VIRTUAL.getId();
		Integer idCliente = cliente == null ? null : cliente.getId();
		Collection colecaoFones = Util.isVazioOuBranco(foneSolicitante) ? null : obterTelefoneCliente(foneSolicitante, cliente);
		Integer unidadeOrganizacionalDestino = Util.obterInteger(ParametrosAgenciaVirtual.P_AV_UNIDADE_ORGANIZACIONAL_DESTINO.executar());
		String cpf = cliente == null ? null : cliente.getCpf();
		String rg = cliente == null ? null : cliente.getRg();
		String orgExpRg = cliente == null || rg == null || cliente.getOrgaoExpedidorRg() == null ? null : cliente.getOrgaoExpedidorRg()
						.getDescricaoAbreviada();
		String ufRg = cliente == null || rg == null || cliente.getUnidadeFederacao() == null ? null : cliente.getUnidadeFederacao()
						.getSigla();
		String cnpj = cliente == null ? null : cliente.getCnpj();

		// Campos inseridos através da funcionalidade Inserir RA
		String indicadorProcessoAdmJud = ConstantesSistema.NAO.toString();
		String numeroProcessoAgencia = null;

		Integer[] retorno = inserirRegistroAtendimento(//
						Short.valueOf("1"), // indicadorAtendimentoOnLine,
						Util.formatarData(dataAtendimento), // dataAtendimento,
						Util.formatarHoraSemSegundos(dataAtendimento), // horaAtendimento,
						null,// tempoEsperaInicial,
						null, // tempoEsperaFinal,
						idMeioSolicitacao, // idMeioSolicitacao,
						null, // senhaAtendimento,
						idSolicitacaoTipoEspecificacao, // idSolicitacaoTipoEspecificacao,
						dataPrevistaFormatada, // dataPrevista,
						descricao, // observacao,
						matricula, // idImovel,
						null, // descricaoLocalOcorrencia,
						idSolicitacaoTipo, // idSolicitacaoTipo,
						colecaoEnderecoImovel, // colecaoEndereco,
						null, // pontoReferenciaLocalOcorrencia,
						idBairro, // idBairroArea,
						idLocalidade, // idLocalidade,
						idSetorComercial, // idSetorComercial,
						idQuadra, // idQuadra,
						null, // idDivisaoEsgoto,
						null, // idLocalOcorrencia,
						tipoPavimentoRua, // idPavimentoRua,
						null, // idPavimentoCalcada,
						unidadeOrganizacionalAbertura, // idUnidadeAtendimento,
						usuarioAbertura, // idUsuarioLogado, TODO DEFINIR Ususario de WS.
						idCliente, // idCliente,
						pontoReferencia, // pontoReferenciaSolicitante,
						nomeSolicitante, // nomeSolicitante,
						false, // novoSolicitante,
						null, // idUnidadeSolicitante,
						null, // idFuncionario,
						colecaoFones, // colecaoFone,
						null, // colecaoEnderecoSolicitante,
						unidadeOrganizacionalDestino, // idUnidadeDestino,
						null, // parecerUnidadeDestino,
						null, // colecaoIdServicoTipo,
						null, // numeroRAManual,
						null, // idRAJAGerado,
						null, // coordenadaNorte,
						null, // coordenadaLeste,
						obterSequenceRA(), // sequenceRA,
						null, // idRaReiterada,
						null, // tipoCliente,
						cpf, // numeroCpf,
						rg, // numeroRg,
						orgExpRg, // orgaoExpedidorRg,
						ufRg, // unidadeFederacaoRG,
						cnpj, // numeroCnpj,
						null, // colecaoContas,
						null, // identificadores,
						null, // contaMotivoRevisao
						indicadorProcessoAdmJud, // indicadorProcessoAdmJud
						numeroProcessoAgencia, // numeroProcessoAgencia
						null // numeroPrestacoesGuiaPagamento
		);
		return retorno[0];
	}

	/**
	 * @param matricula
	 * @param cpfcnpj
	 * @param pontoReferencia
	 * @param nomeSolicitante
	 * @param foneSolicitante
	 * @param emailSolicitante
	 * @param idSolicitacaoTipoEspecificacao
	 * @param idMunicio
	 * @param idBairro
	 * @param idLogradouro
	 * @param numero
	 * @param descricao
	 * @param tipoPavimentoRua
	 * @return
	 * @throws ControladorException
	 * @throws NegocioException
	 */
	public Integer inserirRegistroAtendimentoContaBraille(Cliente cliente, Integer idSolicitacaoTipoEspecificacao, Usuario usuarioLogado,
					Collection<ClienteFone> telefonesCliente) throws ControladorException{

		Date dataAtendimento = Calendar.getInstance().getTime();

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = buscarSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);

		Integer idMeioSolicitacao = MeioSolicitacao.INTERNO;
		Date dataPrevista = definirDataPrevistaRA(dataAtendimento, idSolicitacaoTipoEspecificacao);
		String dataPrevistaFormatada = Util.formatarData(dataPrevista) + " " + Util.formatarHoraSemSegundos(dataPrevista);
		Integer idSolicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId();
		Integer unidadeOrganizacionalAbertura = usuarioLogado.getUnidadeOrganizacional().getId();
		Integer usuarioAbertura = usuarioLogado.getId();
		Integer idCliente = cliente.getId();
		Collection colecaoFones = telefonesCliente;
		String cpf = cliente.getCpf();
		String rg = cliente.getRg();
		String orgExpRg = (cliente.getOrgaoExpedidorRg() == null ? ConstantesSistema.NUMERO_NAO_INFORMADO + "" : cliente
						.getOrgaoExpedidorRg().getId().toString());
		String ufRg = (cliente.getUnidadeFederacao() == null ? ConstantesSistema.NUMERO_NAO_INFORMADO + "" : cliente.getUnidadeFederacao()
						.getId().toString());
		String cnpj = cliente.getCnpj();
		String nomeSolicitante = cliente.getNome();

		Integer[] retorno = inserirRegistroAtendimento(//
						Short.valueOf("1"), // indicadorAtendimentoOnLine,
						Util.formatarData(dataAtendimento), // dataAtendimento,
						Util.formatarHoraSemSegundos(dataAtendimento), // horaAtendimento,
						null,// tempoEsperaInicial,
						null, // tempoEsperaFinal,
						idMeioSolicitacao, // idMeioSolicitacao,
						null, // senhaAtendimento,
						idSolicitacaoTipoEspecificacao, // idSolicitacaoTipoEspecificacao,
						dataPrevistaFormatada, // dataPrevista,
						"SOLICITAÇÃO CONTA BRAILLE", // observacao,
						null, // idImovel,
						null, // descricaoLocalOcorrencia,
						idSolicitacaoTipo, // idSolicitacaoTipo,
						null, // colecaoEndereco,
						null, // pontoReferenciaLocalOcorrencia,
						null, // idBairroArea,
						null, // idLocalidade,
						null, // idSetorComercial,
						null, // idQuadra,
						null, // idDivisaoEsgoto,
						null, // idLocalOcorrencia,
						null, // idPavimentoRua,
						null, // idPavimentoCalcada,
						unidadeOrganizacionalAbertura, // idUnidadeAtendimento,
						usuarioAbertura, // idUsuarioLogado, TODO DEFINIR Ususario de WS.
						idCliente, // idCliente,
						null, // pontoReferenciaSolicitante,
						nomeSolicitante, // nomeSolicitante,
						false, // novoSolicitante,
						null, // idUnidadeSolicitante,
						null, // idFuncionario,
						colecaoFones, // colecaoFone,
						null, // colecaoEnderecoSolicitante,
						null, // idUnidadeDestino,
						null, // parecerUnidadeDestino,
						null, // colecaoIdServicoTipo,
						null, // numeroRAManual,
						null, // idRAJAGerado,
						null, // coordenadaNorte,
						null, // coordenadaLeste,
						obterSequenceRA(), // sequenceRA,
						null, // idRaReiterada,
						cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().toString(), // tipoCliente,
						cpf, // numeroCpf,
						rg, // numeroRg,
						orgExpRg, // orgaoExpedidorRg,
						ufRg, // unidadeFederacaoRG,
						cnpj, // numeroCnpj,
						null, // colecaoContas,
						null, // identificadores,
						null, // contaMotivoRevisao
						ConstantesSistema.NAO.toString(), // indicadorProcessoAdmJud
						null, // numeroProcessoAgencia
						null // numeroPrestacoesGuiaPagamento
		);
		return retorno[0];
	}

	private Collection obterTelefoneCliente(String telefone, Cliente cliente) throws NegocioException{

		// (99)9999-9999#9999
		if(!telefone.matches("^\\(\\d{2}\\)\\d{4}-\\d{4}(#\\d{1,4}){0,1}")){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.erro.campo_telefone_invalido");
		}

		ClienteFone clienteFone = new ClienteFone();
		// seta o id e a descrição do fone tipo
		FoneTipo foneTipo = new FoneTipo();
		foneTipo.setId(FoneTipo.OUTRO);
		foneTipo.setDescricao("Fone AV");
		clienteFone.setFoneTipo(foneTipo);

		String ddd = telefone.substring(1, 3);
		String numeroTelefone = telefone.substring(4, 13);
		String ramal = telefone.substring(14);

		// seta o DDD
		clienteFone.setDdd(ddd);

		// seta o número do telefone
		clienteFone.setTelefone(numeroTelefone);
		// seta o ramal do telefone
		clienteFone.setRamal(ramal);

		clienteFone.setIndicadorTelefonePadrao(Short.valueOf("1"));

		clienteFone.setCliente(cliente);

		// UltimaAlteracao para facilitar a identificacao do objeto
		clienteFone.setUltimaAlteracao(new Date());
		return Arrays.asList(clienteFone);
	}

	private Cliente obterClienteImovel(Integer matricula, String cpfcnpj) throws ControladorException{

		Collection<ClienteImovel> clientes = ServiceLocator.getInstancia().getControladorImovel().pesquisarClientesImovel(matricula);

		Cliente clienteSelecionado = null;
		for(ClienteImovel clienteImovel : clientes){
			clienteSelecionado = clienteImovel.getCliente();
			if((!Util.isVazioOuBranco(clienteSelecionado.getCpf()) && clienteSelecionado.getCpf().equals(cpfcnpj))
							|| (!Util.isVazioOuBranco(clienteSelecionado.getCnpj()) && clienteSelecionado.getCnpj().equals(cpfcnpj))){
				break;
			}
		}
		return ServiceLocator.getInstancia().getControladorCliente().pesquisarCliente(clienteSelecionado.getId());
	}

	/**
	 * Cosulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * @return
	 */
	public int consultarQuantidadeRA(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper) throws ControladorException,
					NegocioException{

		try{
			validarCamposObrigatoriosHalperRA(registroAtendimentoHelper);

			return repositorioRegistroAtendimento.constularQuantidadeRA(registroAtendimentoHelper, Boolean.FALSE);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Valida o {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param registroAtendimentoHelper
	 * @throws NegocioException
	 */
	public void validarCamposObrigatoriosHalperRA(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException{

		if(registroAtendimentoHelper == null){
			throw new IllegalStateException("Filtro do Relatório estatistico de RA não pode ser NULL.");
		}

		if(registroAtendimentoHelper.getPeriodoAtendimentoInicial() == null
						|| registroAtendimentoHelper.getPeriodoAtendimentoFinal() == null){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.required", new String[] {"Periodo Atendimento"});
		}

		if(registroAtendimentoHelper.getPeriodoAtendimentoInicial().compareTo(registroAtendimentoHelper.getPeriodoAtendimentoFinal()) > 0){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.data_fim_menor_inicio");
		}

		if(registroAtendimentoHelper.getEspecificacoes() == null && //
						registroAtendimentoHelper.getTiposSolicitacao() == null && //
						registroAtendimentoHelper.getUnidadeAtendimento() == null && //
						registroAtendimentoHelper.getUnidadeAtual() == null && //
						registroAtendimentoHelper.getUnidadeSuperior() == null && //
						registroAtendimentoHelper.getUsuario() == null){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.solicitar_parametros_filtro");
		}
	}

	/**
	 * Consulta os dados estatisticos de RA filtrados pelo
	 * {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param relatorioEstatisticoRegistroAtendimentoHelper
	 * @return {@link List} de {@link RelatorioEstatisticoRegistroAtendimentoBean}
	 */
	public List<RelatorioEstatisticoRegistroAtendimentoBean> consultarDadosEstatisticosRegistroAtendimento(
					RelatorioEstatisticoRegistroAtendimentoHelper relatorioEstatisticoRegistroAtendimentoHelper)
					throws ControladorException, NegocioException{

		validarCamposObrigatoriosHalperRA(relatorioEstatisticoRegistroAtendimentoHelper);
		int qtdTotalRA = consultarQuantidadeRA(relatorioEstatisticoRegistroAtendimentoHelper);

		List<RelatorioEstatisticoRegistroAtendimentoBean> dadosConsolidados = new ArrayList<RelatorioEstatisticoRegistroAtendimentoBean>();

		// consulta os dados agrupados por unidade superior e unidade de atendimento
		dadosConsolidados.addAll(consultaDadosEstatisticosConsolidadosRA(qtdTotalRA, relatorioEstatisticoRegistroAtendimentoHelper,
						Boolean.FALSE));

		// consulta os dados agrupados apenas por unidade superior
		if(relatorioEstatisticoRegistroAtendimentoHelper.getUnidadeSuperior() != null){
			dadosConsolidados.addAll(consultaDadosEstatisticosConsolidadosRA(qtdTotalRA, relatorioEstatisticoRegistroAtendimentoHelper,
							Boolean.TRUE));
		}

		return dadosConsolidados;
	}

	/**
	 * Consulta os dados estatísticos consolidados de RA agrupados por unidade superior e de
	 * atendimento ou apenas por unidade superior
	 * 
	 * @param qtdTotalRA
	 * @param relatorioEstatisticoRegistroAtendimentoHelper
	 * @param apenasUnidadeSuperior
	 * @return
	 * @throws ControladorException
	 */
	private List<RelatorioEstatisticoRegistroAtendimentoBean> consultaDadosEstatisticosConsolidadosRA(int qtdTotalRA,
					RelatorioEstatisticoRegistroAtendimentoHelper relatorioEstatisticoRegistroAtendimentoHelper,
					boolean apenasUnidadeSuperior) throws ControladorException{

		Collection<Object[]> dadosEstatisticos;
		try{
			dadosEstatisticos = repositorioRegistroAtendimento.consultarDadosEstatisticosRegistroAtendimento(
							relatorioEstatisticoRegistroAtendimentoHelper, apenasUnidadeSuperior);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		List<RelatorioEstatisticoRegistroAtendimentoBean> dadosConsolidados = new ArrayList<RelatorioEstatisticoRegistroAtendimentoBean>();

		for(Object[] objects : dadosEstatisticos){
			Integer idUnidadeSuperior = (Integer) objects[0];
			String unidadeSuperior = (String) objects[1];
			Integer idUnidadeAtendimento = (Integer) objects[2];
			String unidadeAtendimento = (String) objects[3];
			Integer idSolicitacao = (Integer) objects[4];
			String solicitacao = (String) objects[5];
			Integer idEspecificacao = (Integer) objects[6];
			String especificacao = (String) objects[7];
			Integer qtdRA = (Integer) objects[8];
			BigDecimal pct = Util.calcularPercentualBigDecimal(String.valueOf(qtdRA), String.valueOf(qtdTotalRA));
			RelatorioEstatisticoRegistroAtendimentoBean registroConsolidado = new RelatorioEstatisticoRegistroAtendimentoBean(
							idUnidadeSuperior, //
							unidadeSuperior, //
							idUnidadeAtendimento, //
							unidadeAtendimento, //
							idSolicitacao, //
							solicitacao, //
							idEspecificacao, //
							especificacao, //
							qtdRA, pct);

			// Se foram consultadas apenas unidades superiores, significa que o registro é uma
			// unidade superior
			registroConsolidado.setEhUnidadeSuperior(apenasUnidadeSuperior);

			dadosConsolidados.add(registroConsolidado);
		}

		return dadosConsolidados;
	}

	/**
	 * Metodo que consulta o detalhe do registro de atendimento.
	 * 
	 * @param numeroRA
	 * @return
	 */
	public RegistroAtendimentoJSONHelper buscarDetalheRAWebService(Integer numeroRA) throws ControladorException{

		RegistroAtendimentoJSONHelper retorno = null;
		try{
			Object[] array = repositorioRegistroAtendimento.buscarDetalheRAWebService(numeroRA);

			if(!Util.isVazioOrNulo(array)){
				retorno = new RegistroAtendimentoJSONHelper();

				Integer idRA = (Integer) array[0];
				retorno.setNumeroRa(idRA);
				retorno.setProtocolo(idRA);
				retorno.setObservacao((String) array[1]);
				retorno.setPontoReferencia((String) array[2]);
				retorno.setNomeBairro((String) array[3]);

				retorno.setCodigoEspecificacao((Integer) array[4]);

				retorno.setCoordenadaLeste(String.valueOf((BigDecimal) array[5]));
				retorno.setCoordenadaNorte(String.valueOf((BigDecimal) array[6]));

				retorno.setDataAtendimento((Date) array[7]);
				retorno.setDescricaoEspecificacao((String) array[8]);

				StringBuilder telefoneContato = new StringBuilder("");
				Short ddd = (Short) array[9];
				if(Util.isNaoNuloBrancoZero(ddd)){
					telefoneContato.append("(" + ddd + ") ");
				}
				String numeroFone = (String) array[10];
				if(Util.isNaoNuloBrancoZero(numeroFone)){
					telefoneContato.append(numeroFone);
				}
				retorno.setTelefoneContato(telefoneContato.toString());

				retorno.setNomeLocalidade((String) array[11]);
				Integer matricula = (Integer) array[12];
				if(Util.isNaoNuloBrancoZero(matricula)){
					retorno.setMatricula(String.valueOf(matricula));
				}
				retorno.setMunicipio((String) array[13]);

				Integer idLocalidade = (Integer) array[14];
				Integer codigoSetorComercial = (Integer) array[15];
				Integer numeroQuadra = (Integer) array[16];
				Short lote = (Short) array[17];
				Short subLote = (Short) array[18];
				Imovel imovel = new Imovel();
				if(idLocalidade != null && codigoSetorComercial != null && numeroQuadra != null && lote != null && subLote != null){
					imovel.setInscricaoFormatada(idLocalidade, codigoSetorComercial, numeroQuadra, lote, subLote);
					retorno.setInscricao(imovel.getInscricaoFormatada());
				}

				retorno.setIdCliente((Integer) array[19]);
				retorno.setNomeCliente((String) array[20]);

				ObterDescricaoSituacaoRAHelper descricaoSituacaoRA = obterDescricaoSituacaoRA(numeroRA);
				retorno.setDescricaoSituacaoRa(descricaoSituacaoRA.getDescricaoSituacao());

				UnidadeOrganizacional unidadeAtualRA = obterUnidadeAtualRA(numeroRA);
				retorno.setCodigoUnidadeNegocio(unidadeAtualRA.getId());

				String[] logradouro = obterEnderecoeNumeroOcorrenciaRA(numeroRA);
				retorno.setLogradouro(logradouro[0]);
				retorno.setNumeroLogradouro(logradouro[1]);

				retorno.setCodigoSolicitacaoTipo((Integer) array[21]);
				retorno.setDescricaoSolicitacaoTipo((String) array[22]);

				FiltroPesquisaSatisfacao filtroPesquisaSatisfacao = new FiltroPesquisaSatisfacao();
				filtroPesquisaSatisfacao.adicionarParametro(new ParametroSimples(FiltroPesquisaSatisfacao.REGISTRO_ATENDIMENTO_ID, idRA));
				if(Fachada.getInstancia().pesquisar(filtroPesquisaSatisfacao, PesquisaSatisfacao.class.getName()).size() != 0){
					retorno.setIndicadorExistePesquisaSatisfacao(ConstantesSistema.SIM.intValue());
				}else{
					retorno.setIndicadorExistePesquisaSatisfacao(ConstantesSistema.NAO.intValue());
				}
			}

			return retorno;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0422] Obter endereço da ocorrência do RA Este caso de uso permite obter o endereço da
	 * ocorrência de um registro de
	 * atendimento
	 * 
	 * @author André Lopes
	 * @date 17/06/2013
	 * @param idRegistroAtendimento
	 * @return [0] Endereço Formatado (Caso possível sem o número do imóvel)
	 *         [1] Número do imóvel.
	 * @throws ControladorException
	 */
	public String[] obterEnderecoeNumeroOcorrenciaRA(Integer idRegistroAtendimento) throws ControladorException{

		String[] enderecoOcorrencia = new String[2];

		String enderecoDescritivo = this.obterEnderecoDescritivoRA(idRegistroAtendimento);
		if(enderecoDescritivo != null && !enderecoDescritivo.equals("")){
			enderecoOcorrencia[0] = enderecoDescritivo;
			enderecoOcorrencia[1] = ""; // Não tem número nesta descrição
		}else{
			try{

				Object[] dadosRegistroAtendimento = null;

				// verifica o Código do logradourobairro e o Código do Imóvel no
				// registro atendimento
				dadosRegistroAtendimento = repositorioRegistroAtendimento.verificaEnderecoOcorrenciaRA(idRegistroAtendimento);

				if(dadosRegistroAtendimento != null){

					Integer idLogradouroBairro = (Integer) dadosRegistroAtendimento[0];
					Integer idImovel = (Integer) dadosRegistroAtendimento[1];

					/*
					 * Caso o registro de atendimento esteja associado a um logradouro, obter o
					 * endereço da ocorrência
					 * a partir do registro de atendimento
					 */
					if(idLogradouroBairro != null && !idLogradouroBairro.equals("")){
						RegistroAtendimento ra = this.getControladorEndereco().pesquisarRegistroAtendimentoDadosEnderecoFormatado(
										idRegistroAtendimento);
						enderecoOcorrencia[1] = ra.getNumeroImovel();
						ra.setNumeroImovel(null);
						enderecoOcorrencia[0] = ra.getEnderecoFormatado();
					}
					// Caso contrário, obter a endereço da ocorrência a partir do Imóvel associado
					// ao RA
					else if(idImovel != null && !idImovel.equals("")){
						Object[] itens = this.getControladorEndereco().pesquisarEnderecoFormatadoLista(idImovel);
						enderecoOcorrencia[0] = (String) itens[0];
						enderecoOcorrencia[1] = (String) itens[2];
					}

				}
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}
		return enderecoOcorrencia;
	}

	/**
	 * [UC0145] Inserir Conta
	 * [SB0008] - Verificar Exigência RA de Inclusão de Conta Dados Cadastrais Divergentes do Imóvel
	 * 
	 * @author Ado Rocha
	 * @date 31/10/2013
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAInclusaoContaDadosCadastraisDivergentesImovel(Integer idImovel) throws ControladorException{

		boolean retorno = false;

		try{

			retorno = repositorioRegistroAtendimento.verificaExistenciaRAInclusaoContaDadosCadastraisDivergentesImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro Atendimento
	 * [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo religação
	 * ou restabelecimento
	 * 
	 * @author Ítalo Almeida
	 * @date 19/11/2013
	 * @param idImovel
	 * @return Boolean
	 */
	public void verificarExistenciaServicoReligacaoRestabelecimento(Integer idImovel, Integer idSolicitacaoTipo)
					throws ControladorException{

		Collection colecaoOs = null;

		Integer parametroTipoSolicitacaoReligacao = null;
		Integer parametroTipoSolicitacaoRestabelecimento = null;
		Integer parametroVerificarOsAndamentoAberturaRa = null;

		try{

			parametroVerificarOsAndamentoAberturaRa = Integer.valueOf(ParametroAtendimentoPublico.P_VERIFICAR_OS_ANDAMENTO_ABERTURA_RA
							.executar());

			parametroTipoSolicitacaoReligacao = Integer.valueOf(ParametroAtendimentoPublico.P_TIPO_SOLICITACAO_RELIGACAO.executar());

			parametroTipoSolicitacaoRestabelecimento = Integer.valueOf(ParametroAtendimentoPublico.P_TIPO_SOLICITACAO_RESTABELECIMENTO
							.executar());

			colecaoOs = repositorioRegistroAtendimento.retornarColecaoOrdemServicoImovel(idImovel);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if((idSolicitacaoTipo.equals(parametroTipoSolicitacaoReligacao) || idSolicitacaoTipo
						.equals(parametroTipoSolicitacaoRestabelecimento)) && colecaoOs != null && !colecaoOs.isEmpty()){
			throw new ControladorException("atencao.imovel_possui_os_religacao_restabelecimento_em_andamento");
		}

	}

	/**
	 * [UC0366] Inserir Registro Atendimento
	 * [FS0055] Verificar abertura de RA de corte
	 * 
	 * @author Gicevalter Couto
	 * @date 23/04/2015
	 */
	private void verificarAberturaRACorte(Integer idImovel, Integer idSolicitacaoTipo, Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException{

		Integer pIdTipoSolicitacao = null;
		try{
			pIdTipoSolicitacao = Integer.valueOf(ParametroAtendimentoPublico.P_SOLICITACAO_TIPO_GRUPO_CORTE.executar());

		}catch(ControladorException e1){
			throw new ControladorException("erro.sistema", e1);
		}

		if(pIdTipoSolicitacao != null && !pIdTipoSolicitacao.equals(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, idSolicitacaoTipo));

			SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroSolicitacaoTipo, SolicitacaoTipo.class.getName()));

			if(pIdTipoSolicitacao.equals(solicitacaoTipo.getSolicitacaoTipoGrupo().getId())){
				FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
				filtroCobrancaAcao.adicionarParametro(new ParametroSimples(filtroCobrancaAcao.ID, CobrancaAcao.AVISO_CORTE));
				filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.DOCUMENTO_TIPO);

				CobrancaAcao cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroCobrancaAcao,
								CobrancaAcao.class.getName()));

				FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.IMOVEL_ID, idImovel));
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.DOCUMENTO_TIPO_ID, cobrancaAcao
								.getDocumentoTipo().getId()));
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCobrancaDocumento.COBRANCA_SITUACAO_ID,
								CobrancaAcaoSituacao.CANCELADA_PRAZO));
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCobrancaDocumento.COBRANCA_SITUACAO_ID,
								CobrancaAcaoSituacao.CANCELADA));
				filtroCobrancaDocumento.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCobrancaDocumento.COBRANCA_SITUACAO_ID,
								CobrancaAcaoSituacao.NAO_ENTREGUE));

				Collection<CobrancaDocumento> colecaoCobrancaDocumento = (Collection<CobrancaDocumento>) getControladorUtil().pesquisar(
								filtroCobrancaDocumento, CobrancaDocumento.class.getName());

				if(cobrancaAcao.getNumeroDiasValidade() != null){
					for(CobrancaDocumento cobrancaDocumento : colecaoCobrancaDocumento){
						if(cobrancaDocumento.getEmissao() != null){
							Calendar dataEmissaoDocumento = Calendar.getInstance();
							dataEmissaoDocumento.setTime(cobrancaDocumento.getEmissao());
							dataEmissaoDocumento.add(Calendar.DAY_OF_YEAR, cobrancaAcao.getNumeroDiasValidade().intValue());

							if(!(dataEmissaoDocumento.getTime().before(Calendar.getInstance().getTime()))){
								String descricaoMsgErro = "";
								if(solicitacaoTipo.getDescricao() != null){
									descricaoMsgErro = solicitacaoTipo.getDescricao();
								}

								FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
								filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
												FiltroSolicitacaoTipoEspecificacao.ID, idSolicitacaoTipoEspecificacao));

								SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
												.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroSolicitacaoTipoEspecificacao,
																SolicitacaoTipoEspecificacao.class.getName()));
								if(solicitacaoTipoEspecificacao.getDescricao() != null){
									if(!descricaoMsgErro.equals("")){
										descricaoMsgErro = descricaoMsgErro + "/" + solicitacaoTipoEspecificacao.getDescricao();
									}else{
										descricaoMsgErro = solicitacaoTipoEspecificacao.getDescricao();
									}
								}

								throw new ControladorException("atencao.imovel_possui_aviso_corte_valido", null, descricaoMsgErro,
												idImovel.toString());
							}
						}

					}
				}

			}
		}

	}

	/**
	 * @param dadosAcquaGis
	 * @throws ControladorException
	 */
	public void atualizarCoordenadasGis(DadosAcquaGis dadosAcquaGis) throws ControladorException{

		try{

			repositorioRegistroAtendimento.atualizarCoordenadasGis(dadosAcquaGis);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Valida o {@link RelatorioRAComProcessoAdmJudHelper}
	 * 
	 * @param registroAtendimentoHelper
	 * @throws NegocioException
	 */
	public void validarCamposObrigatoriosHelperRA(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException{

		if(registroAtendimentoHelper == null){
			throw new IllegalStateException("Filtro do Relatório de RA com Processo Adm Jud não pode ser NULL.");
		}

		if(registroAtendimentoHelper.getPeriodoAtendimentoInicial() == null
						|| registroAtendimentoHelper.getPeriodoAtendimentoFinal() == null){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.required", new String[] {"Periodo Atendimento"});
		}

		if(registroAtendimentoHelper.getPeriodoAtendimentoInicial().compareTo(registroAtendimentoHelper.getPeriodoAtendimentoFinal()) > 0){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.data_fim_menor_inicio");
		}

		if(registroAtendimentoHelper.getEspecificacoes() == null && //
						registroAtendimentoHelper.getTiposSolicitacao() == null && //
						registroAtendimentoHelper.getUnidadeAtendimento() == null && //
						registroAtendimentoHelper.getUnidadeAtual() == null && //
						registroAtendimentoHelper.getUnidadeSuperior() == null && //
						registroAtendimentoHelper.getUsuario() == null){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.solicitar_parametros_filtro");
		}
	}

	/**
	 * Cosulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioRAComProcessoAdmJudHelper}
	 * @return
	 */
	public int consultarQuantidadeRAComProcessoAdmJud(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper)
					throws ControladorException, NegocioException{

		try{
			validarCamposObrigatoriosHelperRA(registroAtendimentoHelper);

			return repositorioRegistroAtendimento.consultarQuantidadeRAComProcessoAdmJud(registroAtendimentoHelper, Boolean.FALSE);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Consulta os dados de RA com Processo Adm Jud filtrados pelo
	 * {@link RelatorioRAComProcessoAdmJudHelper}
	 * 
	 * @param relatorioRAComProcessoAdmJudHelper
	 * @return {@link List} de {@link RelatorioRAComProcessoAdmJudBean}
	 */
	public List<RelatorioRAComProcessoAdmJudBean> consultarDadosRAComProcessoAdmJud(
					RelatorioRAComProcessoAdmJudHelper relatorioRAComProcessoAdmJudHelper) throws ControladorException, NegocioException{

		validarCamposObrigatoriosHelperRA(relatorioRAComProcessoAdmJudHelper);
		int qtdTotalRA = consultarQuantidadeRAComProcessoAdmJud(relatorioRAComProcessoAdmJudHelper);

		List<RelatorioRAComProcessoAdmJudBean> dadosConsolidados = new ArrayList<RelatorioRAComProcessoAdmJudBean>();

		// consulta os dados agrupados por unidade superior e unidade de atendimento
		dadosConsolidados
						.addAll(consultaDadosRAComProcessoAdmJudConsolidados(qtdTotalRA, relatorioRAComProcessoAdmJudHelper, Boolean.FALSE));

		// consulta os dados agrupados apenas por unidade superior
		if(relatorioRAComProcessoAdmJudHelper.getUnidadeSuperior() != null){
			dadosConsolidados.addAll(consultaDadosRAComProcessoAdmJudConsolidados(qtdTotalRA, relatorioRAComProcessoAdmJudHelper,
							Boolean.TRUE));
		}

		return dadosConsolidados;
	}

	/**
	 * Consulta os dados de RA com processo adm jud consolidados agrupados por unidade superior e de
	 * atendimento ou apenas por unidade superior
	 * 
	 * @param qtdTotalRA
	 * @param relatorioRAComProcessoAdmJudHelper
	 * @param apenasUnidadeSuperior
	 * @return
	 * @throws ControladorException
	 */
	private List<RelatorioRAComProcessoAdmJudBean> consultaDadosRAComProcessoAdmJudConsolidados(int qtdTotalRA,
					RelatorioRAComProcessoAdmJudHelper relatorioRAComProcessoAdmJudHelper, boolean apenasUnidadeSuperior)
					throws ControladorException{

		Collection<Object[]> dados;
		try{
			dados = repositorioRegistroAtendimento.consultarDadosRAComProcessoAdmJud(relatorioRAComProcessoAdmJudHelper,
							apenasUnidadeSuperior);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		List<RelatorioRAComProcessoAdmJudBean> dadosConsolidados = new ArrayList<RelatorioRAComProcessoAdmJudBean>();

		for(Object[] objects : dados){
			Integer idUnidadeSuperior = (Integer) objects[0];
			String unidadeSuperior = (String) objects[1];
			Integer idUnidadeAtendimento = (Integer) objects[2];
			String unidadeAtendimento = (String) objects[3];
			Integer idSolicitacao = (Integer) objects[4];
			String solicitacao = (String) objects[5];
			Integer idEspecificacao = (Integer) objects[6];
			String especificacao = (String) objects[7];
			String numeroProcessoAgencia = (String) objects[8];
			Integer numeroRABase = (Integer) objects[9];
			String numeroRA = numeroRABase.toString();
			Integer situacaoRABase = (Integer) objects[10];
			Short situacaoRA = situacaoRABase.shortValue();
			String descricaoSituacaoRA = "";
			Integer idUnidadeAtual = null;
			String descricaoUnidadeAtual = "";

			if(situacaoRA != null && situacaoRA.equals(RegistroAtendimento.SITUACAO_PENDENTE)){
				descricaoSituacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_PENDENTE;
			}else if(situacaoRA != null && situacaoRA.equals(RegistroAtendimento.SITUACAO_ENCERRADO)){
				descricaoSituacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_ENCERRADO;
			}else if(situacaoRA != null && situacaoRA.equals(RegistroAtendimento.SITUACAO_BLOQUEADO)){
				descricaoSituacaoRA = RegistroAtendimento.SITUACAO_DESCRICAO_BLOQUEADO;
			}

			UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualRA(numeroRABase);

			idUnidadeAtual = unidadeAtual.getId();
			descricaoUnidadeAtual = unidadeAtual.getDescricaoComId();

			RelatorioRAComProcessoAdmJudBean registroConsolidado = new RelatorioRAComProcessoAdmJudBean(Boolean.FALSE, //
							idUnidadeSuperior, //
							unidadeSuperior, //
							idUnidadeAtendimento, //
							unidadeAtendimento, //
							idSolicitacao, //
							solicitacao, //
							idEspecificacao, //
							especificacao, //
							numeroProcessoAgencia, //
							numeroRA, //
							descricaoSituacaoRA, //
							idUnidadeAtual, //
							descricaoUnidadeAtual);

			// Se foram consultadas apenas unidades superiores, significa que o registro é uma
			// unidade superior
			registroConsolidado.setEhUnidadeSuperior(apenasUnidadeSuperior);

			dadosConsolidados.add(registroConsolidado);
		}

		return dadosConsolidados;
	}

	/**
	 * @author Hiroshi Gonçalves
	 * @date 18/02/2014
	 * @throws ControladorException
	 */
	public List<RelatorioEstatisticoAtendimentoPorRacaCorBean> pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(
					GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(form);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @author Yara Souza
	 * @date 18/02/2014
	 * @throws ControladorException
	 */
	public List<ServicoAssociadoValorHelper> pesquisarServicoAssociado(Integer idSolicitacaoTipoEspecificacao) throws ControladorException{

		try{
			return this.repositorioRegistroAtendimento.pesquisarServicoAssociado(idSolicitacaoTipoEspecificacao);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @param idSolicitacaoTipoEspecificacao
	 * @param usuarioLogado
	 * @param cliente
	 * @return
	 * @throws NumberFormatException
	 * @throws ControladorException
	 */
	public Integer inserirRAPorSolicitacaoTipoEspecificacao(Integer idSolicitacaoTipoEspecificacao, Usuario usuarioLogado, Cliente cliente,
					Imovel imovel) throws NumberFormatException, ControladorException{

		Date dataAtendimento = Calendar.getInstance().getTime();

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = buscarSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacao);

		Integer idMeioSolicitacao = MeioSolicitacao.INTERNO;
		Date dataPrevista = definirDataPrevistaRA(dataAtendimento, idSolicitacaoTipoEspecificacao);
		String dataPrevistaFormatada = Util.formatarData(dataPrevista) + " " + Util.formatarHoraSemSegundos(dataPrevista);
		Integer idSolicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId();
		Integer unidadeOrganizacionalAbertura = usuarioLogado.getUnidadeOrganizacional().getId();
		Integer usuarioAbertura = usuarioLogado.getId();
		Integer idCliente = cliente.getId();
		Collection colecaoFones = null;
		String cpf = cliente.getCpf();
		String rg = cliente.getRg();
		String orgExpRg = (cliente.getOrgaoExpedidorRg() == null ? ConstantesSistema.NUMERO_NAO_INFORMADO + "" : cliente
						.getOrgaoExpedidorRg().getId().toString());
		String ufRg = (cliente.getUnidadeFederacao() == null ? ConstantesSistema.NUMERO_NAO_INFORMADO + "" : cliente.getUnidadeFederacao()
						.getId().toString());
		String cnpj = cliente.getCnpj();
		String nomeSolicitante = cliente.getNome();
		Collection colecaoEnderecoImovel = Util.isVazioOuBranco(imovel.getId()) ? null : buscarEnderecosImovel(imovel.getId());

		Integer[] retorno = inserirRegistroAtendimento(//
						Short.valueOf("1"), // indicadorAtendimentoOnLine,
						Util.formatarData(dataAtendimento), // dataAtendimento,
						Util.formatarHoraSemSegundos(dataAtendimento), // horaAtendimento,
						null,// tempoEsperaInicial,
						null, // tempoEsperaFinal,
						idMeioSolicitacao, // idMeioSolicitacao,
						null, // senhaAtendimento,
						idSolicitacaoTipoEspecificacao, // idSolicitacaoTipoEspecificacao,
						dataPrevistaFormatada, // dataPrevista,
						"SOLICITAÇÃO CONTA BRAILLE", // observacao,
						imovel.getId(), // idImovel,
						null, // descricaoLocalOcorrencia,
						idSolicitacaoTipo, // idSolicitacaoTipo,
						colecaoEnderecoImovel, // colecaoEndereco,
						null, // pontoReferenciaLocalOcorrencia,
						null, // idBairroArea,
						null, // idLocalidade,
						null, // idSetorComercial,
						null, // idQuadra,
						null, // idDivisaoEsgoto,
						null, // idLocalOcorrencia,
						null, // idPavimentoRua,
						null, // idPavimentoCalcada,
						unidadeOrganizacionalAbertura, // idUnidadeAtendimento,
						usuarioAbertura, // idUsuarioLogado, TODO DEFINIR Ususario de WS.
						idCliente, // idCliente,
						null, // pontoReferenciaSolicitante,
						nomeSolicitante, // nomeSolicitante,
						false, // novoSolicitante,
						null, // idUnidadeSolicitante,
						null, // idFuncionario,
						colecaoFones, // colecaoFone,
						null, // colecaoEnderecoSolicitante,
						null, // idUnidadeDestino,
						null, // parecerUnidadeDestino,
						null, // colecaoIdServicoTipo,
						null, // numeroRAManual,
						null, // idRAJAGerado,
						null, // coordenadaNorte,
						null, // coordenadaLeste,
						obterSequenceRA(), // sequenceRA,
						null, // idRaReiterada,
						cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().toString(), // tipoCliente,
						cpf, // numeroCpf,
						rg, // numeroRg,
						orgExpRg, // orgaoExpedidorRg,
						ufRg, // unidadeFederacaoRG,
						cnpj, // numeroCnpj,
						null, // colecaoContas,
						null, // identificadores,
						null, // contaMotivoRevisao
						ConstantesSistema.NAO.toString(), // indicadorProcessoAdmJud
						null, // numeroProcessoAgencia
						null // quantidadePrestacoesGuiaPagamento
		);

		return retorno[0];
	}
}
