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

package gcom.atendimentopublico;

import gcom.atendimentopublico.bean.*;
import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.ObterValorDebitoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.EnvioEmail;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.*;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaClienteBean;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaHelper;
import gcom.relatorio.atendimentopublico.RelatorioCertidaoNegativaItemBean;
import gcom.relatorio.atendimentopublico.RelatorioContratoPrestacaoServicoJuridicoBean;
import gcom.relatorio.cadastro.imovel.RelatorioMateriaisAplicadosHelper;
import gcom.seguranca.ControladorPermissaoEspecialLocal;
import gcom.seguranca.ControladorPermissaoEspecialLocalHome;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.util.*;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.atendimentopublico.ExecutorParametrosAtendimentoPublico;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Definição da lógica de negócio do Session Bean de ControladorCliente
 * 
 * @author Leandro Cavalcanti
 * @created 12 de junho de 2006
 */
public class ControladorAtendimentoPublicoSEJB
				implements SessionBean, Parametrizacao {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private IRepositorioMicromedicao repositorioMicromedicao;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();
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

		// pega a instáncia do ServiceLocator.

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

	private ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a interface remota de ControladorParametro
	 * 
	 * @return A interface remota do controlador de parâmetro
	 */
	private ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLocalidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);
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
	 * Retorna o valor de controladorFaturamento
	 * 
	 * @return O valor de controladorFaturamento
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instáncia do ServiceLocator.

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
	 * Retorna o valor do ControladorMicromedicao
	 * 
	 * @author Leonardo Regis
	 * @date 20/07/2006
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.
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
	 * Retorna o valor do ControladorOrdemServico
	 * 
	 * @author Leonardo Regis
	 * @date 23/09/2006
	 * @return O valor de ControladorOrdemServico
	 */
	private ControladorOrdemServicoLocal getControladorOrdemServico(){

		ControladorOrdemServicoLocalHome localHome = null;
		ControladorOrdemServicoLocal local = null;
		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorOrdemServicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ORDEM_SERVICO_SEJB);
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
	 * Retorna o valor do ControladorLigacaoAgua
	 * 
	 * @author Flávio Cordeiro
	 * @date 28/09/2006
	 * @return O valor de controladorLigacaoAgua
	 */
	private ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

		ControladorLigacaoAguaLocalHome localHome = null;
		ControladorLigacaoAguaLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLigacaoAguaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_AGUA_SEJB);
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
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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
	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instáncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
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

	private ControladorTransacaoLocal getControladorTransacao(){

		ControladorTransacaoLocalHome localHome = null;
		ControladorTransacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorTransacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_TRANSACAO_SEJB);
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
	 * [UC0353] Efetuar Ligação de Água.
	 * Permite efetuar Ligação de Água ou pelo menu ou pela funcionalidade
	 * encerrar a Execução da ordem de serviço.
	 * [FS0003] Validar Consumo Mínimo. [SB0001] Gerar Ligação de Água [SB0002]
	 * Atualizar Imóvel Situação do Imovel.
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 03/08/2006
	 * @author eduardo henrique
	 * @date 18/03/2009
	 *       Alteração no [SB0001] para determinação automática do Consumo mínimo da Ligação,
	 *       caso não tenha sido informado hidrômetro.
	 * @param ligacaoAgua
	 * @param imovel
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();
		Usuario usuarioLogado = integracaoComercialHelper.getUsuarioLogado();

		if(ligacaoAgua != null && imovel != null){

			// [SB0001] Gerar Ligação de Água

			// LAGU_ID
			ligacaoAgua.setId(imovel.getId());
			FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
			filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovel.getId()));
			Collection colecaoLigacaoAguaBase = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

			// LAGU_DTIMPLANTACAO
			ligacaoAgua.setDataImplantacao(new Date());

			// LAGU_TMULTIMAALTERACAO
			Date dataCorrente = new Date();
			ligacaoAgua.setUltimaAlteracao(dataCorrente);

			if(!colecaoLigacaoAguaBase.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.imovel_ja_existente", null, "" + ligacaoAgua.getId());
			}

			// determinação do consumo mínimo
			this.determinarConsumoMinimoNovaLigacaoAgua(ligacaoAgua);

			getControladorUtil().inserir(ligacaoAgua);

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LIGACAO_AGUA_EFETUAR, ligacaoAgua.getId(),
							ligacaoAgua.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			registradorOperacao.registrarOperacao(ligacaoAgua);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

			getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

			if(imovel.getLigacaoEsgotoSituacao() != null
							&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
				ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

				imovel.setUltimaAlteracao(new Date());
				// [SB0002] Atualizar Imóvel
				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
			}
		}

		if(!integracaoComercialHelper.isVeioEncerrarOS()){

			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(qtdParcelas));
		}

		/**
		 * 8. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo na
		 * tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * 8.1. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE) obtendo
			 * os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os
			 * campos exceto o CPRO_ID e campos de quantidade/valores) de linha na tabela. Caso
			 * exista, acumular
			 * na existente as colunas de quantidade e valor, caso contrário, inserir nova linha.
			 */

			/*
			 * foi comentando por Isilva na data 10/05/2010 por não existir referência no caso de
			 * uso
			 */
			// getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico,
			// false, false,
			// CobrancaDebitoSituacao.EXECUTADO);
		}

	}

	/**
	 * [UC0353] Efetuar Ligação de Água.
	 * Permite efetuar Ligação de Água ou pelo menu ou pela funcionalidade
	 * encerrar a Execução da ordem de serviço.
	 * [SB0001] Gerar Ligação de Água
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 *       Alteração no [SB0001] para determinação automática do Consumo mínimo da Ligação,
	 *       caso não tenha sido informado hidrômetro.
	 * @param ligacaoAgua
	 *            Instancia da nova Ligacao de Agua que será adicionada. (Deve vir com a instancia
	 *            de Imovel populada e HidromInstHistorico, caso exista)
	 * @throws ControladorException
	 */
	public void determinarConsumoMinimoNovaLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException{

		if(ligacaoAgua == null){
			throw new ControladorException("erro.ligacao_agua_instancia_nao_valida");
		}

		// Parâmetro que indica se vai ser adotado ou nao algum criterio cadastral para estabelecer
		// consumo minimo da ligacao.
		String parametroCalculoConsumoMinimoNaoMedidos = (String) ParametroAtendimentoPublico.P_ESTABELECER_CONSUMO_POR_CRITERIO.executar(
						this, 0);

		// Condição para cálculo do Consumo Mínimo
		if(parametroCalculoConsumoMinimoNaoMedidos.equals(ConstantesSistema.SIM.toString())
						&& ligacaoAgua.getHidrometroInstalacaoHistorico() == null
						&&
						ligacaoAgua.getImovel() != null && ligacaoAgua.getImovel().getNumeroPontosUtilizacao() != null){

			Integer volumeFixoFaixaImovel = getControladorMicromedicao().obterVolumeConsumoFixoFaixaImovel(
							Integer.valueOf(ligacaoAgua.getImovel().getNumeroPontosUtilizacao().shortValue()));
			if(volumeFixoFaixaImovel != null){
				ligacaoAgua.setNumeroConsumoMinimoAgua(volumeFixoFaixaImovel);
			}
		}
	}

	/**
	 * [UC0354] Efetuar Ligação de Água.
	 * Permite validar Ligação de Água Efetuar ou pelo menu ou pela
	 * funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaEfetuar(Imovel imovel, LigacaoAgua ligacaoAgua) throws ControladorException{

		// [FS0003] Validar Consumo Minimo
		if(ligacaoAgua != null){
			this.validarConsumoMinimoLigacaoAguaImovel(imovel, ligacaoAgua.getNumeroConsumoMinimoAgua().toString());
		}

	}

	/**
	 * [UC0353] Efetuar Ligação de Água.
	 * Permite validar Ligação de Água Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a Execução da ordem de serviço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_LIGACAO_AGUA_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_ligacao_agua_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);

		Imovel imovel = null;
		if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				int idImovel = ordem.getRegistroAtendimento().getImovel().getId();
				imovel = Fachada.getInstancia().consultarImovel(idImovel);
			}

			// Caso 4
			if(imovel == null){
				throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, String.valueOf(ordem
								.getRegistroAtendimento().getId()));
			}

			// [FS0002] Validar Situação de Água do Imóvel.
			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.POTENCIAL.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.FACTIVEL.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_FISCALIZACAO.intValue()){

				throw new ControladorException("atencao.situacao_validar_ligacao_agua_invalida_exibir", null, imovel
								.getLigacaoAguaSituacao().getDescricao());
			}

			// [FS0003] Verificar Situação Rede de Água na Quadra
			if((imovel.getQuadra().getIndicadorRedeAgua()).equals(Quadra.SEM_REDE)){
				throw new ControladorException("atencao.seituacao_rede_agua_quadra", null, String.valueOf(imovel.getId()));
			}

			// [FS0006] Verificar Situação do Imovel
			if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
				throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, String.valueOf(imovel.getId()));
			}

			/*
			 * ===================================================================================
			 */

		}

	}

	/**
	 * [UC0354] Efetuar Ligação de Esgoto.
	 * Permite validar Ligação de esgoto Exibir ou pelo menu ou pela
	 * funcionalidade encerrar a Execução da ordem de serviço.
	 * [FS0008] Verificar Situação Rede de Esgoto na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Esgoto do Imóvel
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoEsgotoExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_LIGACAO_ESGOTO_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_ligacao_esgoto_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);
		// Caso 4
		if(ordem.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" + ordem.getRegistroAtendimento().getId());
		}

		int idImovel = ordem.getRegistroAtendimento().getImovel().getId();
		Imovel imovel = Fachada.getInstancia().consultarImovel(idImovel);

		// [FS0002]– Validar Situação da Ligação de Esgoto do Imóvel
		if(imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.POTENCIAL.intValue()
						&& imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.FACTIVEL.intValue()
						&& imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.EM_FISCALIZACAO.intValue()){

			throw new ControladorException("atencao.situacao_validar_ligacao_esgoto_invalida_exibir", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		// [FS0007] - Verificar situação do imóvel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("situacao_imovel_indicador_exclusao_esgoto", null, imovel.getId() + "");
		}

		// [FS0008] - Verificar situação rede de esgoto da quadra
		// Caso o parâmetro P_CRITICAR_ESGOTO_QUADRA tenha valor 1.
		if(ParametroAtendimentoPublico.P_CRITICAR_ESGOTO_QUADRA.executar().equals(ConstantesSistema.SIM.toString())){
			if((imovel.getQuadra().getIndicadorRedeEsgoto()).equals(Quadra.SEM_REDE)){
				throw new ControladorException("atencao.percentual_rede_esgoto_quadra", null, imovel.getId() + "");
			}
		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * Ligação de esgoto do Imóvel no momento da atualização. // [UC0105] -
	 * Obter Consumo Mínimo da Ligação //[FS0003] Validar Consumo Minimo
	 * 
	 * @author Leandro Cavalcanti
	 * @date 20/07/2006
	 * @param volumeMinimoFixado
	 * @param ligacaoEsgoto
	 */
	public Integer validarConsumoMinimoLigacaoAguaImovel(Imovel imovel, String volumeMinimoFixado) throws ControladorException{

		// [FS0003] Validar Consumo Minimo
		/*
		 * atencao.situacao_volume_minimo_fixado_nao_multiplo= Valor do volume
		 * Mínimo Fixado deve ser alterado para {0} valor multiplo de quantidade
		 * de economias {1}.
		 */

		// [UC0105] - Obter Consumo Mínimo da Ligação
		int consumoMinimoObtido = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);
		Integer consumoMinimoObtido1 = new Integer(consumoMinimoObtido);

		// Verificar se o volume Mínimo informado seja menor que o valor
		// Mínimo obtido para Imóvel.
		if(volumeMinimoFixado != null && !volumeMinimoFixado.trim().equals("")){
			if(!volumeMinimoFixado.trim().equalsIgnoreCase(ConstantesSistema.SET_ZERO.toString())){
				Integer consumoInformado = Integer.parseInt(volumeMinimoFixado);
				if(consumoInformado.intValue() < consumoMinimoObtido1.intValue()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.situacao_volume_minimo_fixado_menor_consumo_calculado", null,
									consumoMinimoObtido + "");
				}
			}
		}else{
			throw new ControladorException("atencao.requerid", null, "Situação da Ligação de Esgoto");
		}

		return new Integer(volumeMinimoFixado);
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * Ligação de agua do Imóvel no momento da exibição.
	 * [FS0001] Verificar existência da matrícula do Imóvel. [FS0002] Verificar
	 * Situação do Imovel. [FS0003] Validar Situação de Esgoto do Imóvel.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param Imovel
	 */
	public void validarExibirLigacaoAguaImovel(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] Validar Ordem de Servico
		// Caso 2
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_ATUALIZAR_LIGACAO_AGUA);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_atualizar_ligacao_agua_invalida");
		}

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);

		// Caso 4
		if(ordemServico.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		// [FS0004] Validar Data do Encerramento da Ordem de Servico
		if(ServicoTipo.LIGACAO_AGUA.contains(ordemServico.getServicoTipo().getId())
						|| ServicoTipo.CORTE_LIGACAO_AGUA.contains(ordemServico.getServicoTipo().getId())
						|| ServicoTipo.SUPRESSAO_LIGACAO_AGUA.contains(ordemServico.getServicoTipo().getId())){

			Date dataExecucao = ordemServico.getDataExecucao();

			if(dataExecucao != null){

				int qtdDias = Util.obterQuantidadeDiasEntreDuasDatas(dataExecucao, new Date());

				FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

				Collection colecao = getControladorUtil().pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());

				if(colecao != null && !colecao.isEmpty()){

					SistemaParametro sistemaParametro = (SistemaParametro) Util.retonarObjetoDeColecao(colecao);

					int qtdMaximo = sistemaParametro.getDiasMaximoAlterarOS().intValue();

					if(qtdDias > qtdMaximo){

						String[] msg = new String[2];

						msg[0] = "" + ordemServico.getId();
						msg[1] = "" + qtdMaximo;

						throw new ControladorException("atencao.ligacao_agua_data_encerramento_os_invalida", null, msg);
					}
				}
			}
		}

		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligacao de Água");
		}

		// [FS003] Validar Situação de Água do Imóvel.
		if(imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.POTENCIAL.intValue()
						|| imovel.getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.FACTIVEL.intValue()){

			throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_invalida", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * retirada de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRetiradaHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_RETIRADA_HIDROMETRO_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_retirada_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		if(ordemServico.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		// // [FS0003] - Verificar Situação de Agua ou Esgoto.
		// // [FS0004] - Verificar a existência de hidrômetro no Imóvel/Ligação
		// de
		// // Água
		//
		// // Caso 1
		// if (servicoTipo == ServicoTipo.TIPO_RETIRADA_HIDROMETRO_POCO) {
		//
		// LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel
		// .getLigacaoEsgotoSituacao();
		//
		// if (ligacaoEsgotoSituacao.getId().intValue() !=
		// LigacaoEsgotoSituacao.LIGADO
		// && ligacaoEsgotoSituacao.getId().intValue() !=
		// LigacaoEsgotoSituacao.LIG_FORA_DE_USO
		// && ligacaoEsgotoSituacao.getId().intValue() !=
		// LigacaoEsgotoSituacao.TAMPONADO) {
		//
		// throw new ControladorException(
		// "atencao.situacao_retirada_hidrometro_poco_invalida",
		// null, ligacaoEsgotoSituacao.getDescricao());
		// }
		//
		// if (imovel.getHidrometroInstalacaoHistorico() == null) {
		// throw new ControladorException(
		// "atencao.imovel_poco_sem_hidrometro", null, ""
		// + imovel.getId());
		// }
		//
		// // Caso 2
		// } else if (servicoTipo == ServicoTipo.TIPO_RETIRADA_HIDROMETRO) {
		//
		// LigacaoAguaSituacao ligacaoAguaSituacao = imovel
		// .getLigacaoAguaSituacao();
		//
		// if (ligacaoAguaSituacao.getId().intValue() !=
		// LigacaoAguaSituacao.LIGADO
		// && ligacaoAguaSituacao.getId().intValue() !=
		// LigacaoAguaSituacao.CORTADO) {
		//
		// throw new ControladorException(
		// "atencao.situacao_retirada_hidrometro_poco_invalida",
		// null, ligacaoAguaSituacao.getDescricao());
		// }
		//
		// if (imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() ==
		// null) {
		// throw new ControladorException(
		// "atencao.imovel_ligacao_agua_sem_hidrometro", null, ""
		// + imovel.getId());
		// }
		// }

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * remanejamento de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirRemanejmentoHidrometroAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_REMANEJAMENTO_HIDROMETRO_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_remanejamento_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		if(ordemServico.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
		}

		// [FS0003] - Verificar a existência de hidrômetro no Imóvel

		// Caso 1
		/*
		 * if (servicoTipo == ServicoTipo.TIPO_REMANEJAMENTO_HIDROMETRO_POCO) {
		 * if (imovel.getHidrometroInstalacaoHistorico() == null) { throw new
		 * ControladorException( "atencao.imovel_poco_sem_hidrometro", null, "" +
		 * imovel.getId()); } // Caso 2 } else if (servicoTipo ==
		 * ServicoTipo.TIPO_REMANEJAMENTO_HIDROMETRO_LIGACAO_AGUA) { if
		 * (imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null) {
		 * throw new ControladorException(
		 * "atencao.imovel_ligacao_agua_sem_hidrometro", null, "" +
		 * imovel.getId()); } }
		 */

		if(ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
						MedicaoTipo.LIGACAO_AGUA.shortValue())){

			if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_ligacao_agua_sem_hidrometro", null, "" + imovel.getId());
			}
		}else{

			if(imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_poco_sem_hidrometro", null, "" + imovel.getId());
			}
		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * substituição de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @date 31/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSubstituicaoHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_SUBSTITUICAO_HIDROMETRO_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_substituicao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		/*
		 * Autor: Vivianne Sousa
		 * Data: 11/12/2007
		 * Analista Responsavel: Denys
		 */
		Imovel imovel = null;
		if((ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getImovel() == null)
						&& ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_imovel_invalida");
		}

		if(ordemServico.getImovel() != null){
			imovel = ordemServico.getImovel();
		}else{
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}

		// [FS0007] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		// [FS0008] - Verificar existência de hidrômetro no tipo de medição

		// Caso 1
		if(ordemServico.getRegistroAtendimento() == null
						|| ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
										MedicaoTipo.LIGACAO_AGUA.shortValue())){
			if(imovel.getLigacaoAgua() == null){
				throw new ControladorException("atencao.imovel_nao_tem_ligacao_agua", null, "" + imovel.getId());
			}else if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_ligacao_agua_sem_hidrometro", null, "" + imovel.getId());
			}
		}else{
			if(imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_poco_sem_hidrometro", null, "" + imovel.getId());
			}
		}

		// [FS0002] Verificar Situação do hidrometro.
		/*
		 * Hidrometro hidrometro =
		 * hidrometroInstalacaoHistorico.getHidrometro();
		 * if (hidrometro.getHidrometroSituacao().getId().intValue() !=
		 * HidrometroSituacao.DISPONIVEL .intValue()) { throw new
		 * ControladorException( "atencao.hidrometro_situacao_indisponivel",
		 * null, hidrometro.getHidrometroSituacao().getDescricao()); }
		 */

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * restabelecimento Ligação de agua
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirRestabelecimentoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_RESTABELECIMENTO_LIGACAO_AGUA_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_restabelecimento_ligacao_agua_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */
		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);

		Imovel imovel = null;

		if(ordemServico.getRegistroAtendimento() != null){

			// Caso 4
			if(ordemServico.getRegistroAtendimento().getImovel() == null){
				throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
								+ ordemServico.getRegistroAtendimento().getId());
			}
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}else{

			FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ORDEMS_SERVICO_ID, ordemServico.getId()
							.toString()));
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_AGUA_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_ESGOTO_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LOCALIDADE);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_SETOR_COMERCIAL);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_QUADRA);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL);

			CobrancaDocumento cobrancaDocumentoRetorno = (CobrancaDocumento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroCobrancaDocumento, CobrancaDocumento.class.getName()));

			if(cobrancaDocumentoRetorno == null || cobrancaDocumentoRetorno.getImovel() == null){
				throw new ControladorException("atencao.ordem_servico_doc_cobranca_invalida");
			}
			ordemServico.setCobrancaDocumento(cobrancaDocumentoRetorno);
			imovel = cobrancaDocumentoRetorno.getImovel();
		}

		if(imovel == null){
			throw new ControladorException("atencao.registro.atendimento.ou.doc.cobranca.obrigatorio");
		}

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
		}

		// [FS0003] Verificar a situação de Água

		if(LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue() != ConstantesSistema.INVALIDO_ID){

			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_invalida", null, "" + imovel.getId(),
								"Suprimido ou Suprimido Parcial");
			}

		}else{

			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_invalida", null, "" + imovel.getId(),
								"Suprimido ou Suprimido Parcial");
			}

		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades de
	 * corte adimistrativo de Ligação de Água
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirCorteAdministrativoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_CORTE_ADMINISTRATIVO_LIGACAO_AGUA_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_corte_administrativo_ligacao_agua_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4

		// Comentado por Raphael Rossiter em 26/02/2007
		/*
		 * if (ordemServico.getRegistroAtendimento().getImovel() == null) {
		 * throw new ControladorException(
		 * "atencao.ordem_servico_ra_imovel_invalida", null, "" +
		 * ordemServico.getRegistroAtendimento().getId()); }
		 */

		if(ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		}

		// Comentado por Raphael Rossiter em 28/02/2007
		// Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		Imovel imovel = ordemServico.getImovel();

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
		}

		// [FS0003] Verificar a situação de Água
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){

			throw new ControladorException("atencao.situacao_ligacao_agua_invalida", null, "" + imovel.getId(), "Ligado");

		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades de
	 * reLigação de Água
	 * 
	 * @author Rafael Pinto
	 * @date 29/07/2006
	 * @param ordemServico
	 *            ,veioEncerrarOS
	 */
	public void validarExibirReligacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] Validar Ordem de Servico
		ServicoTipo servicoTipo = ordemServico.getServicoTipo();
		Integer idServicoTipo = servicoTipo.getId();

		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(idServicoTipo,
						Operacao.OPERACAO_RELIGACAO_AGUA_EFETUAR_INT);

		// Caso 2
		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_religacao_agua_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4

		Imovel imovel = null;

		if(ordemServico.getRegistroAtendimento() != null){

			if(ordemServico.getRegistroAtendimento().getImovel() == null){
				throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
								+ ordemServico.getRegistroAtendimento().getId());
			}
			imovel = ordemServico.getRegistroAtendimento().getImovel();

		}else{

			FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
			filtroCobrancaDocumento
							.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ORDEMS_SERVICO_ID, ordemServico.getId()));
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_AGUA_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_ESGOTO_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LOCALIDADE);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_SETOR_COMERCIAL);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_QUADRA);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL);

			CobrancaDocumento cobrancaDocumentoRetorno = (CobrancaDocumento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroCobrancaDocumento, CobrancaDocumento.class.getName()));

			if(cobrancaDocumentoRetorno != null && cobrancaDocumentoRetorno.getImovel() != null){
				imovel = cobrancaDocumentoRetorno.getImovel();
			}
		}

		if(imovel == null){
			imovel = this.getControladorImovel().pesquisarImovel(ordemServico.getImovel().getId());
		}

		if(imovel == null){
			throw new ControladorException("atencao.registro.atendimento.ou.doc.cobranca.obrigatorio");
		}

		// [FS0014] Verificar tipo de registro de corte da ligação de água

		if(ServicoTipo.RELIGACAO_REGISTRO_MAGNETICO.contains(idServicoTipo) || ServicoTipo.RELIGACAO_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.LIQ_NORMAL)){
					throw new ControladorException("atencao.imovel_sem_registro_magnetico_e_sem_tubere_religacao");
				}
			}
		}

		if(ServicoTipo.RELIGACAO_AGUA.contains(idServicoTipo) || ServicoTipo.RELIGACAO_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.REG_MAGNET)){
					throw new ControladorException("atencao.imovel_com_registro_magnetico_incorreto_religacao");
				}
			}
		}

		if(ServicoTipo.RELIGACAO_AGUA.contains(idServicoTipo) || ServicoTipo.RELIGACAO_REGISTRO_MAGNETICO.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.TUB_MAGNET)){
					throw new ControladorException("atencao.imovel_com_tubete_magnetico_incorreto_religacao");
				}
			}
		}

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
		}

		// [FS0003] Verificar a situação de Água
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO_PEDIDO.intValue()){

			throw new ControladorException("atencao.situacao_ligacao_agua_invalida", null, "" + imovel.getId(),
							"Cortado ou Cortado a pedido");

		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades do
	 * supressao Ligação de agua
	 * 
	 * @author Rafael Pinto
	 * @date 28/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirSupressaoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_SUPRESSAO_LIGACAO_AGUA_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_supressa_ligacao_agua_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);

		// Caso 4
		// Comentado por Raphael Rossiter em 28/02/2007
		/*
		 * if (ordemServico.getRegistroAtendimento().getImovel() == null) {
		 * throw new ControladorException(
		 * "atencao.ordem_servico_ra_imovel_invalida", null, "" +
		 * ordemServico.getRegistroAtendimento().getId()); }
		 */

		if(ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		// Comentado por Raphael Rossiter em 28/02/2007
		// Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		Imovel imovel = ordemServico.getImovel();

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
		}

		// [FS0003] Verificar a situação de Água

		if(LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue() != ConstantesSistema.INVALIDO_ID){

			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_FISCALIZACAO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_CANCELAMENTO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_supressao_invalida", null, imovel.getLigacaoAguaSituacao()
								.getDescricao() + "");
			}
		}else{

			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_FISCALIZACAO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_CANCELAMENTO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_supressao_invalida", null, imovel.getLigacaoAguaSituacao()
								.getDescricao() + "");
			}

		}


		/*
		 * ===================================================================================
		 */
	}

	/**
	 * [UC0367]Atualizar Ligação de Agua no sistema.
	 * [SB002] Atualiza Ligação de agua.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param ligacaoAgua
	 * @throws ControladorException
	 */
	public void atualizarLigacaoAgua(LigacaoAgua ligacaoAgua) throws ControladorException{

		if(ligacaoAgua != null){

			// item [FS0001] Verificar existência da matrícula do Imóvel.
			if(ligacaoAgua.getImovel() != null){

				// item [FS0002] Verificar Situação do Imovel
				if(ligacaoAgua.getImovel().getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, ligacaoAgua.getImovel().getId() + "");
				}

				// item [FS0003] Validar Situação de Agua do Imovel
				if(ligacaoAgua.getImovel().getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.POTENCIAL.intValue()
								|| ligacaoAgua.getImovel().getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.FACTIVEL
												.intValue()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_invalida", null, ligacaoAgua.getImovel()
									.getId()
									+ "");

				}

				if(ligacaoAgua.getImovel().getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.CORTADO.intValue()){

					// item [FS0005] Validar Tipo Corte
					if(ligacaoAgua.getCorteTipo() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_tipo_corte", null, ligacaoAgua.getImovel()
										.getId()
										+ "");

						// item [FS0006] Validar Motivo Corte
					}else if(ligacaoAgua.getMotivoCorte() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_motivo_corte", null, ligacaoAgua
										.getImovel().getId()
										+ "");

						// item [FS0010] Validar Numero Selo Corte
					}else if(ligacaoAgua.getNumeroSeloCorte() == null
									|| ligacaoAgua.getNumeroSeloCorte().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_selo_corte", null, ligacaoAgua.getImovel()
										.getId()
										+ "");

					}

				}

				if(ligacaoAgua.getImovel().getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.SUPRIMIDO.intValue()
								|| ligacaoAgua.getImovel().getLigacaoAguaSituacao().getId().intValue() == LigacaoAguaSituacao.SUPR_PARC_PEDIDO
												.intValue()){

					// item [FS0007] Validar Motivo Supressao
					if(ligacaoAgua.getSupressaoMotivo() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_motivo_supressao", null, ligacaoAgua
										.getImovel().getId()
										+ "");

						// item [FS0008] Validar Tipo Supressao
					}else if(ligacaoAgua.getSupressaoTipo() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_tipo_supressao", null, ligacaoAgua
										.getImovel().getId()
										+ "");

						// item [FS0011] Validar Numero Selo Supressao
					}else if(ligacaoAgua.getNumeroSeloSupressao() == null
									|| ligacaoAgua.getNumeroSeloSupressao().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.atualizar_ligacao_agua_situacao_selo_supressao", null, ligacaoAgua
										.getImovel().getId()
										+ "");

					}

				}

			}else{
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.situacao_imovel_indicador_exclusao_esgoto", null, ligacaoAgua.getImovel().getId()
								+ "");
			}

			this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

			// Efetuando uma Ligação de Agua
			getControladorUtil().atualizar(ligacaoAgua);

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = ligacaoAgua.getHidrometroInstalacaoHistorico();

			if(hidrometroInstalacaoHistorico != null){
				getControladorUtil().atualizar(hidrometroInstalacaoHistorico);
			}

		}
	}

	/**
	 * Faz o controle de concorrencia de ligacao Agua
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarLigacaoAguaControleConcorrencia(LigacaoAgua ligacaoAgua) throws ControladorException{

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ligacaoAgua.getId()));

		Collection colecaoLigacao = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		if(colecaoLigacao == null || colecaoLigacao.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		LigacaoAgua ligacaoAguaAtual = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacao);

		if(ligacaoAguaAtual.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * Faz o controle de concorrencia de hidrometro instalacao historico
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarHidrometroInstalacaoHistoricoControleConcorrencia(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico)
					throws ControladorException{

		FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();

		filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.ID,
						hidrometroInstalacaoHistorico.getId()));

		Collection colecaoHidrometroInstalacaoHistorico = getControladorUtil().pesquisar(filtroHidrometroInstalacaoHistorico,
						HidrometroInstalacaoHistorico.class.getName());

		if(colecaoHidrometroInstalacaoHistorico == null || colecaoHidrometroInstalacaoHistorico.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAtual = (HidrometroInstalacaoHistorico) Util
						.retonarObjetoDeColecao(colecaoHidrometroInstalacaoHistorico);

		// Verificar se categoria já foi atualizada por outro usuário durante
		// esta atualização
		if(hidrometroInstalacaoHistoricoAtual.getUltimaAlteracao().after(hidrometroInstalacaoHistorico.getUltimaAlteracao())){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

	}

	/**
	 * Faz o controle de concorrencia de hidrometro
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarHidrometroControleConcorrencia(Hidrometro hidrometro) throws ControladorException{

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

		filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.ID, hidrometro.getId()));

		Collection colecaoHidrometro = getControladorUtil().pesquisar(filtroHidrometro, Hidrometro.class.getName());

		if(colecaoHidrometro == null || colecaoHidrometro.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		Hidrometro hidrometroAtual = (Hidrometro) Util.retonarObjetoDeColecao(colecaoHidrometro);

		// Verificar se categoria já foi atualizada por outro usuário durante
		// esta atualização
		if(hidrometroAtual.getUltimaAlteracao().after(hidrometro.getUltimaAlteracao())){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

	}

	/**
	 * [UC0354] Efetuar Corte de Ligação de Água.
	 * Permite efetuar Ligação de Esgoto ou pelo menu ou pela funcionalidade
	 * encerrar a Execução da ordem de serviço.
	 * 
	 * @author Leandro Cavalcanti.
	 * @date 12/07/2006
	 * @param ligacaoEsgoto
	 * @param imovel
	 * @throws ControladorException
	 */
	public void inserirLigacaoEsgoto(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		LigacaoEsgoto ligacaoEsgoto = integracaoComercialHelper.getLigacaoEsgoto();
		Imovel imovel = integracaoComercialHelper.getImovel();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		ligacaoEsgoto.setId(imovel.getId());
		FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();
		filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID, imovel.getId()));
		Collection colecaoLigacaoEsgotoBase = getControladorUtil().pesquisar(filtroLigacaoEsgoto, LigacaoEsgoto.class.getName());

		if(!colecaoLigacaoEsgotoBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.imovel_ja_existente", null, "" + ligacaoEsgoto.getId());
		}
		getControladorUtil().inserir(ligacaoEsgoto);

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

		getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);

		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(qtdParcelas));
		}

	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserção da especificacao situacao criterio imovel.
	 * [FS0001] Validar especificação da situaçãoo já existente [FS0002] Validar
	 * existência de hidrômetro na Ligação Água [FS0003] Validar existência de
	 * hidrômetro no Poço
	 * 
	 * @author Rafael Pinto
	 * @date 04/08/2006
	 * @param equipeComponentes
	 */
	public void validarExibirInsercaoEspecificacaoImovSitCriterio(Collection colecaoEspecificacaoImovSitCriterio,
					EspecificacaoImovSitCriterio especImovSitCriterio) throws ControladorException{

		// Verificar objeto a ser inserido na base.
		if(especImovSitCriterio != null){

			// [FS0002] Validar existência de hidrômetro na Ligação Água

			// Caso 1
			if(especImovSitCriterio.getLigacaoAguaSituacao() != null){

				if(especImovSitCriterio.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
								&& especImovSitCriterio.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO
												.intValue()){

					if(especImovSitCriterio.getIndicadorHidrometroLigacaoAgua() == null
									|| especImovSitCriterio.getIndicadorHidrometroLigacaoAgua().shortValue() == ConstantesSistema.SIM
													.shortValue()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_ligacao_agua", null, "");

					}

				}

				// Caso 2
			}else{
				if(especImovSitCriterio.getIndicadorHidrometroLigacaoAgua() != null){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_ligacao_agua", null, "");
				}
			}

			// [FS0003] Validar existência de hidrômetro no Poço

			// Caso 1
			if(especImovSitCriterio.getLigacaoEsgotoSituacao() != null){

				if(especImovSitCriterio.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.TAMPONADO.intValue()
								&& especImovSitCriterio.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.LIGADO
												.intValue()){

					if(especImovSitCriterio.getIndicadorHidrometroPoco() == null
									|| especImovSitCriterio.getIndicadorHidrometroPoco().shortValue() == ConstantesSistema.SIM.shortValue()){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_ligacao_esgoto", null, "");

					}

				}

				// Caso 2
			}else{
				if(especImovSitCriterio.getIndicadorHidrometroPoco() != null){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_ligacao_esgoto", null, "");
				}
			}

			// Testar se nova especificacao pode ser inserido na coleção
			if(colecaoEspecificacaoImovSitCriterio != null && !colecaoEspecificacaoImovSitCriterio.isEmpty()){

				// Varre coleção de especificacao da grid (ainda não inseridos
				// na base)
				for(Iterator iter = colecaoEspecificacaoImovSitCriterio.iterator(); iter.hasNext();){

					EspecificacaoImovSitCriterio element = (EspecificacaoImovSitCriterio) iter.next();

					// [FS0001] Validar especificação da situação já existente
					if(element.equals(especImovSitCriterio)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_criterio_ja_informado", null, "");
					}

				}// fim do for
			}
		}else{
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.inserir_especificacao_situacao_imovel_invalida", null, "");
		}
	}

	/**
	 * [UC0365] Efetuar Remanejamento de hidrômetro [SB0001] Atualizar Histórico
	 * de instalação do hidrômetro
	 * 
	 * @author Rômulo Aurélio
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */

	public void efetuarRemanejamentoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		this.verificarHidrometroInstalacaoHistoricoControleConcorrencia(hidrometroInstalacaoHistorico);

		getControladorUtil().atualizar(hidrometroInstalacaoHistorico);

		ordemServico.setUltimaAlteracao(new Date());

		// [SB006]Atualizar Ordem de Serviço
		if(!integracaoComercialHelper.isVeioEncerrarOS()){

			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);

			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(qtdParcelas).intValue());
		}
	}

	/**
	 * [UC0357] Efetuar ReLigação de Água
	 * Permite efetuar reLigação da Ligação de Água ou pelo menu ou pela
	 * funcionalidade encerrar a Execução da ordem de Serviço.
	 * [SB0001] Atualizar Imóvel/Ligação de Água/Ligação de Esgoto
	 * 
	 * @author Rômulo Aurélio
	 * @date 07/07/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();

		// [SB0001] - Atualizar Imóvel/Ligação de Água/Ligação de Esgoto

		// Caso 1
		Imovel imovel = null;

		if(ordemServico.getRegistroAtendimento() != null){
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}else if(ordemServico.getCobrancaDocumento() != null){
			imovel = ordemServico.getCobrancaDocumento().getImovel();
		}else{
			imovel = ordemServico.getImovel();
		}

		LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();
		ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);
		ligacaoAguaSituacao.setUltimaAlteracao(new Date());

		imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
		imovel.setUltimaAlteracao(new Date());

		this.getControladorImovel().verificarImovelControleConcorrencia(imovel);
		// this.getControladorUtil().atualizar(imovel);

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();
		if(ligacaoEsgotoSituacao != null && ligacaoEsgotoSituacao.getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

			ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);
			ligacaoEsgotoSituacao.setUltimaAlteracao(new Date());

			// Colocado por Raphael Rossiter em 09/05/2007
			imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);

			// Comentado por Raphael Rossiter em 09/05/2007
			// this.getControladorUtil().atualizar(ligacaoEsgotoSituacao);
		}

		// Caso 2
		ligacaoAgua.setUltimaAlteracao(new Date());

		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

		getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);
		getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);

		this.getControladorLigacaoAgua().atualizarLigacaoAguaReligacao(ligacaoAgua);

		// Inserido por Isilva
		/**
		 * 8. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo na
		 * tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * 8.1. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE) obtendo
			 * os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os
			 * campos exceto o CPRO_ID e campos de quantidade/valores) de linha na tabela. Caso
			 * exista, acumular
			 * na existente as colunas de quantidade e valor, caso contrário, inserir nova linha.
			 */
			getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}

		ordemServico.setUltimaAlteracao(new Date());

		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);

			getControladorOrdemServico().atualizaOSGeral(ordemServico, true, false);
		}

		/*
		 * [OC790655][UC0357][SB0003]: Atualizar Dados Execução no Histórico de
		 * Manutenção da Ligação do Imóvel ao EfetuarReligacaoAgua
		 */
		if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
			getControladorLigacaoAgua()
							.atualizarHistoricoManutencaoLigacao(ordemServico, HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA);
		}

		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(qtdParcelas));
		}

	}

	/**
	 * [UC0363] Efetuar Retirada de hidrômetro [SB0001] Atualizar Histórico de
	 * instalação do hidrômetro
	 * 
	 * @author Thiago Tenório
	 * @date 30/06/2006
	 * @param hidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */

	public void efetuarRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

		if(integracaoComercialHelper.getHidrometroInstalacaoHistorico() != null){
			HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
			hidrometroSituacao.setId(HidrometroSituacao.DISPONIVEL);
			integracaoComercialHelper.getHidrometroInstalacaoHistorico().getHidrometro().setHidrometroSituacao(hidrometroSituacao);
		}
		hidrometroInstalacaoHistorico.setDataRetirada(new Date());
		this.verificarHidrometroInstalacaoHistoricoControleConcorrencia(hidrometroInstalacaoHistorico);

		getControladorUtil().atualizar(hidrometroInstalacaoHistorico);

		this.verificarHidrometroControleConcorrencia(hidrometroInstalacaoHistorico.getHidrometro());

		getControladorUtil().atualizar(hidrometroInstalacaoHistorico.getHidrometro());

		try{
			// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
			// colunas da tabela LIGACAO_AGUA
			// Integer id = hidrometroInstalacaoHistorico.getId();
			if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){

				repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
								.getLigacaoAgua().getId(), null);

				// Caso o tipo de medição seja igual a Poço, atualiza as colunas
				// da tabela POCO
			}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){

				repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(
								hidrometroInstalacaoHistorico.getImovel().getId(), null);
			}

			OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
			if(ordemServico != null){
				// [SB006]Atualizar Ordem de Serviço
				if(!integracaoComercialHelper.isVeioEncerrarOS()){
					getControladorOrdemServico().atualizaOSGeral(integracaoComercialHelper.getOrdemServico(), false, false);
				}

				if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){



					getControladorOrdemServico().gerarDebitoOrdemServico(
									ordemServico.getId(),
									ordemServico.getServicoTipo().getDebitoTipo().getId(),
									Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico
													.getPercentualCobranca().toString()),
									new Integer(integracaoComercialHelper.getQtdParcelas()));
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * @author anishimura
	 * @author lmedeiros
	 *         Efetua a instalação de hidrometros em lote.
	 */
	public Collection[] efetuarInstalacaoHidrometroEmLote(InputStream arquivoInputStream, Usuario usuario) throws ControladorException{

		Collection[] retorno = validaArquivoInstalacaoHidrometrosLote(arquivoInputStream, usuario);
		if(retorno.length > 0){
			Collection<IntegracaoComercialHelper> instalacoesARealizaar = retorno[0];
			for(IntegracaoComercialHelper helper : instalacoesARealizaar){
				Integer id = null;
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = helper.getHidrometroInstalacaoHistorico();
				validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());
				id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);
				try{
					// Caso o tipo de medição seja igual a Ligação de Água, atualiza as colunas da
					// tabela LIGACAO_AGUA
					if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
						repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
										.getLigacaoAgua().getId(), id);
						// Caso o tipo de medição seja igual a Poço, atualiza as colunas da tabela
						// POCO
					}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
						repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
										.getId(), id);
					}
					// [SB003]Atualizar situação de hidrômetro na tabela HIDROMETRO
					Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
					repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
									situacaoHidrometro);
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
			}
		}
		return retorno;
	}

	private Collection[] validaArquivoInstalacaoHidrometrosLote(InputStream arquivoInputStream, Usuario usuario)
					throws ControladorException{

		Scanner arquivoLote = null;
		File arquivoErro = null;
		BufferedWriter out = null;

		arquivoLote = new Scanner(arquivoInputStream);

		Collection<IntegracaoComercialHelper> colecaoHidrometrosAInstalar = new ArrayList();
		Collection<String> colecaoErros = new ArrayList();
		Collection[] retorno = new Collection[2];
		String descricaoErro = "";

		boolean selecionado = true;

		Integer arquivoLinha = 0;

		// Inicio da leitura do arquivo
		while(arquivoLote.hasNextLine()){
			selecionado = true;
			descricaoErro = "";
			arquivoLinha++;
			String linhaAtual = arquivoLote.nextLine();
			linhaAtual = linhaAtual.replace(";;", "; ;");
			StringTokenizer linha = new StringTokenizer(linhaAtual, ";");

			if(linha.countTokens() < 11){
				descricaoErro = "Linha com menos parâmetros que o necessário, ";
				colecaoErros.add(linhaAtual.concat(";".concat(descricaoErro)));
			}else{
				String matricula = linha.nextToken().trim();
				String hidro = linha.nextToken().trim();
				String dataInstalacao = linha.nextToken().trim();
				String tipoMedicao = linha.nextToken().trim();
				String local = linha.nextToken().trim();
				String protecao = linha.nextToken().trim();
				String trocaProtecao = linha.nextToken().trim();
				String trocaRegistro = linha.nextToken().trim();
				String leituraInst = linha.nextToken().trim();
				String selo = linha.nextToken().trim();
				String cavalete = linha.nextToken().trim();

				// Validaçoes
				// 1. Imovel Existe?
				Imovel imovel = new Imovel();
				HidrometroProtecao hidrometroProtecao = null;
				HidrometroLocalInstalacao localInstalacao = null;

				if(matricula.equals("")){
					descricaoErro = "Matrícula não informada, ";
					selecionado = false;
				}else{
					try{
						imovel = this.getControladorImovel().pesquisarImovel(Integer.valueOf(matricula));
					}catch(NumberFormatException e){
						descricaoErro = "Matrícula com o formato inválido, ";
						selecionado = false;
					}
					if(imovel == null){
						descricaoErro = "Matrícula desconhecida, ";
						selecionado = false;
					}else{
						for(Object object : colecaoHidrometrosAInstalar){
							if(((IntegracaoComercialHelper) object).getHidrometroInstalacaoHistorico().getLigacaoAgua() == null){
								if(((IntegracaoComercialHelper) object).getHidrometroInstalacaoHistorico().getImovel().getId().equals(
												Integer.parseInt(matricula))){
									descricaoErro = descricaoErro + "matricula repetida";
									selecionado = false;
								}
							}else if(((IntegracaoComercialHelper) object).getHidrometroInstalacaoHistorico().getLigacaoAgua().getId()
											.equals(Integer.parseInt(matricula))){
								descricaoErro = descricaoErro + "matricula repetida";
								selecionado = false;
							}
						}

						// existe ligaçao de agua ligada?
						if(tipoMedicao != null && tipoMedicao.equalsIgnoreCase("L")){
							if(verificarExistenciaHidrometroEmLigacaoAgua(imovel.getId())){
								descricaoErro = descricaoErro + " Ligação com hidrometro instalado, ";
								selecionado = false;
							}else{
								LigacaoAguaSituacao ligacaoAguaSituacao = getControladorImovel().pesquisarLigacaoAguaSituacao(
												imovel.getId());
								if(!ligacaoAguaSituacao.getId().equals(LigacaoAguaSituacao.LIGADO)){
									descricaoErro = descricaoErro + " Ligacão de Agua não está ligada, ";
									selecionado = false;
								}
							}
						}else if(tipoMedicao != null && tipoMedicao.equalsIgnoreCase("P")){
							if(verificarExistenciaHidrometroEmImovel(imovel.getId())){
								descricaoErro = descricaoErro + " Poço com hidrometro instalado, ";
								selecionado = false;
							}else{
								LigacaoEsgotoSituacao ligacaoEsgotoSituacao = getControladorImovel().pesquisarLigacaoEsgotoSituacao(
												imovel.getId());
								if(!ligacaoEsgotoSituacao.getId().equals(LigacaoEsgotoSituacao.LIGADO)){
									descricaoErro = descricaoErro + " Ligacão de esgoto não está ligada, ";
									selecionado = false;
								}
							}
						}

						// hidrometro existe?
						if(hidro.equals("")){
							descricaoErro = descricaoErro + " Numero de hidrometro não informado, ";
							selecionado = false;
						}else{
							Hidrometro hidrometro = this.getControladorMicromedicao().pesquisarHidrometroPeloNumero(hidro);
							if(hidrometro == null){
								descricaoErro = descricaoErro + "Hidrometro não cadastrado, ";
								selecionado = false;
							}else if(hidrometro.getHidrometroSituacao().getId().equals(HidrometroSituacao.INSTALADO)){
								descricaoErro = descricaoErro + "Hidrometro ja se encontra instalado, ";
								selecionado = false;
							}else{
								for(Object hidrom : colecaoHidrometrosAInstalar){
									if((((IntegracaoComercialHelper) hidrom).getHidrometroInstalacaoHistorico().getHidrometro().getNumero())
													.equals(hidro)){
										descricaoErro = descricaoErro + "hidrometro repetido";
										selecionado = false;
									}
								}

							}
						}

						// data da instalacao
						if(dataInstalacao.equals("")){
							descricaoErro = descricaoErro.concat("Data não informada, ");
							selecionado = false;
						}else{
							Date data = Util.converteStringParaDate(dataInstalacao);
							if(data == null || data.after(new Date())){
								descricaoErro = descricaoErro + "Data inválida, ";
								selecionado = false;
							}
						}

						// Tipo medicao
						if(tipoMedicao.equalsIgnoreCase("")){
							descricaoErro = descricaoErro + " Tipo medição não informada, ";
							selecionado = false;
						}else if(!tipoMedicao.equalsIgnoreCase("L") && !tipoMedicao.equalsIgnoreCase("P")){
							descricaoErro = descricaoErro + " Tipo medição invalida, ";
							selecionado = false;
						}

						// 4. local de instalaçao
						if(local.equals("")){
							descricaoErro = descricaoErro + "Local de instalação não informado, ";
							selecionado = false;
						}else{

							try{

								localInstalacao = repositorioMicromedicao.pesquisarHidrometroLocalInstalacaoPorDescricaoAbreviada(local);
							}catch(ErroRepositorioException e){

								throw new ControladorException("erro.sistema", e);
							}

							if(localInstalacao == null){
								descricaoErro = descricaoErro + " Local de instalaçao nao cadastrado,";
								selecionado = false;
							}
						}

						// 5. tipo de proteçao
						if(protecao.equals("")){
							descricaoErro = descricaoErro + " proteçao não informada, ";
							selecionado = false;
						}else{
							Collection colecao2 = this.getControladorMicromedicao().pesquisarHidrometroProtecaoPorDescricaoAbreviada(
											protecao);
							hidrometroProtecao = (HidrometroProtecao) Util.retonarObjetoDeColecao(colecao2);
							if(hidrometroProtecao == null){
								descricaoErro = descricaoErro + " protecao não cadastrada, ";
								selecionado = false;
							}
						}
						if(trocaProtecao == null || trocaProtecao.equals("")){
							descricaoErro = descricaoErro + " Opçao de troca de proteçao não informada, ";
							selecionado = false;
						}else if(!(trocaProtecao.equals("S") || trocaProtecao.equals("N"))){
							descricaoErro = descricaoErro + " Opçao de troca de proteçao invalida, ";
							selecionado = false;
						}

						if(trocaRegistro == null || trocaRegistro.equals("")){
							descricaoErro = descricaoErro + " Opçao de troca de registro não informada, ";
							selecionado = false;
						}else if(!(trocaRegistro.equals("S") || trocaRegistro.equals("N"))){
							descricaoErro = descricaoErro + " Opçao de troca de registro invalida, ";
							selecionado = false;
						}
						if(cavalete == null || cavalete.equals("")){
							descricaoErro = descricaoErro + " Opçao de cavalete não informada, ";
							selecionado = false;
						}else if(!(cavalete.equalsIgnoreCase("S") || cavalete.equalsIgnoreCase("N"))){
							descricaoErro = descricaoErro + " Opçao de cavalete invalido, ";
							selecionado = false;
						}
					}
				}
				if(selecionado){
					IntegracaoComercialHelper helper = new IntegracaoComercialHelper();

					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
					Hidrometro hidrometro = this.getControladorMicromedicao().pesquisarHidrometroPeloNumero(hidro);
					hidrometroInstalacaoHistorico.setHidrometro(hidrometro);

					Integer idHidrometro = Integer.valueOf(0);
					if(hidrometro != null){
						idHidrometro = hidrometro.getId();
					}

					RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR,
									idHidrometro, idHidrometro, new UsuarioAcaoUsuarioHelper(usuario,
													UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_LOTE_EFETUAR);

					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);

					hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
					hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

					// Montando o historico
					hidrometroInstalacaoHistorico.setDataInstalacao(Util.converteStringParaDate(dataInstalacao));

					// validar matricula do imovel
					LigacaoAgua ligacaoAgua = new LigacaoAgua();
					ligacaoAgua.setId(Integer.valueOf(matricula));

					if(tipoMedicao != null && tipoMedicao.equalsIgnoreCase("L")){
						hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);
					}else if(tipoMedicao != null && tipoMedicao.equalsIgnoreCase("P")){
						hidrometroInstalacaoHistorico.setImovel(imovel);
					}

					MedicaoTipo medicaoTipo = new MedicaoTipo();
					medicaoTipo.setId(Integer.valueOf(tipoMedicao.equalsIgnoreCase("L") ? MedicaoTipo.LIGACAO_AGUA : MedicaoTipo.POCO));

					hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);
					hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(localInstalacao);
					hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);
					hidrometroInstalacaoHistorico
									.setNumeroLeituraInstalacao((leituraInst != null && !leituraInst.trim().equals("")) ? Integer
													.valueOf(leituraInst) : Integer.valueOf(null));
					hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short
									.valueOf((cavalete.equalsIgnoreCase("S") ? "1" : "2")));
					hidrometroInstalacaoHistorico.setDataRetirada(null);
					hidrometroInstalacaoHistorico.setNumeroLeituraRetirada(null);
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
					hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
					hidrometroInstalacaoHistorico.setNumeroHidrometro(hidro);
					hidrometroInstalacaoHistorico.setNumeroSelo(selo);
					hidrometroInstalacaoHistorico.setRateioTipo(new RateioTipo(RateioTipo.RATEIO_POR_IMOVEL));
					hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());
					hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(Short.valueOf(String
									.valueOf(HidrometroInstalacaoHistorico.INDICADOR_INSTALACAO_HIDROMETRO)));
					hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
					hidrometroInstalacaoHistorico.setIndicadorTrocaProtecao(Short
									.valueOf((trocaProtecao.equalsIgnoreCase("S") ? "1" : "2")));
					hidrometroInstalacaoHistorico.setIndicadorTrocaRegistro(Short
									.valueOf((trocaRegistro.equalsIgnoreCase("S") ? "1" : "2")));
					helper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
					helper.setVeioEncerrarOS(Boolean.FALSE);

					colecaoHidrometrosAInstalar.add(helper);
				}else{
					String linhaErro = linhaAtual.concat(";".concat(descricaoErro));
					colecaoErros.add(linhaErro);
				}
			}
		}
		if(!colecaoErros.isEmpty()){
			try{

				if(arquivoErro == null){
					arquivoErro = File.createTempFile("ArquivoErro", ".csv");
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoErro.getAbsolutePath())));
				}
				for(String linhaErro : colecaoErros){
					out.write(linhaErro);
					out.newLine();
				}
				out.flush();
				out.close();

				EnvioEmail envioEmail = this.getControladorCadastro().pesquisarEnvioEmail(
								EnvioEmail.GERAR_ARQUIVO_ERRO_INSTALACAO_HIDROMETROS_LOTE);
				ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(), envioEmail
								.getCorpoMensagem(), "", arquivoErro);
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		retorno[0] = colecaoHidrometrosAInstalar;
		retorno[1] = colecaoErros;
		return retorno;

	}

	/**
	 * [UC0365] Efetuar Instalação de hidrômetro
	 * [SB0001] Gerar Histórico de instalação do hidrômetro [SB0002] Atualizar
	 * Imóvel/Ligação de Água [SB0003] Atualizar situação de hidrômetro na
	 * tabela HIDROMETRO
	 * 
	 * @author Ana Maria
	 * @date 12/07/2006
	 * @param hidrometroInstalacaoHistorico
	 * @param materialImovel
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public void efetuarInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		Integer id = null;

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());

		id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

		// [SB0002]Atualizar Imóvel/Ligação de Água
		try{
			// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
			// colunas da tabela LIGACAO_AGUA
			if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
								.getLigacaoAgua().getId(), id);
				// Caso o tipo de medição seja igual a Poço, atualiza as colunas
				// da tabela POCO
			}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
				repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(
								hidrometroInstalacaoHistorico.getImovel().getId(), id);
			}

			// [SB003]Atualizar situação de hidrômetro na tabela HIDROMETRO
			Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);

			// [SB006]Atualizar Ordem de Serviço
			if(!integracaoComercialHelper.isVeioEncerrarOS()){
				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), new Integer(qtdParcelas).intValue());
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0362] Efetuar Instalacao de hidrômetro
	 * Validar Instalacao de hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param tipoMedicao
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoInstalacaoHidrometro(String numeroHidrometro) throws ControladorException{

		Hidrometro hidrometro = getControladorMicromedicao().pesquisarHidrometroPeloNumero(numeroHidrometro);

		// FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
		//
		// filtroHidrometro
		// .adicionarCaminhoParaCarregamentoEntidade("hidrometroSituacao");
		// filtroHidrometro.adicionarParametro(new ParametroSimples(
		// FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
		//
		// Collection colecaoHidrometro = null;
		//
		// colecaoHidrometro = getControladorUtil().pesquisar(filtroHidrometro,
		// Hidrometro.class.getName());
		//
		// // [FS002]Caso o hidrômetro informado esteja com a situação diferente
		// de
		// // DISPONíVEL
		// Iterator iteratorHidrometro = colecaoHidrometro.iterator();
		// while (iteratorHidrometro.hasNext()) {
		// Hidrometro hidrometro = (Hidrometro) iteratorHidrometro.next();
		Integer idSituacaoHidrometro = hidrometro.getHidrometroSituacao().getId();
		if(!(idSituacaoHidrometro.equals(HidrometroSituacao.DISPONIVEL))){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.hidrometro_situacao_indisponivel", null, hidrometro.getHidrometroSituacao()
							.getDescricao());
		}
		// }
	}

	/**
	 * [UC0362] Efetuar Instalacao de hidrômetro
	 * Validar Instalacao de hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param tipoMedicao
	 *            return void
	 * @throws ControladorException
	 */
	public void validarExibirInstalacaoHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_instalacao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		/*
		 * Autor: Vivianne Sousa
		 * Data: 11/12/2007
		 * Analista Responsavel: Denys
		 */
		Imovel imovel = null;
		if((ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getImovel() == null)
						&& ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_imovel_invalida");
		}

		if(ordemServico.getImovel() != null){
			imovel = ordemServico.getImovel();
		}else{
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}

		// [FS0002] Verificar Situação do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		// [FS0003] - Verificar Situação de Agua ou Esgoto.
		// [FS0004] - Verificar a existência de hidrômetro no Imóvel/Ligação de
		// Água

		/* HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null; */

		// Caso 1
		if(ordemServico.getRegistroAtendimento() == null
						|| ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
										MedicaoTipo.LIGACAO_AGUA.shortValue())){
			LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

			if(ligacaoAguaSituacao.getId().intValue() != LigacaoAguaSituacao.LIGADO
							&& ligacaoAguaSituacao.getId().intValue() != LigacaoAguaSituacao.LIGADO_EM_ANALISE){

				throw new ControladorException("atencao.instalacao_hidrometro_situacao_ligacao_agua_invalida", null, ligacaoAguaSituacao
								.getDescricao());
			}

			if(imovel.getLigacaoAgua() == null){
				throw new ControladorException("atencao.naocadastrado", null, "Ligação de Água");
			}

			if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
				throw new ControladorException("atencao.hidrometro_instalado_ligacao_agua", null, "" + imovel.getId());
			}
		}else{

			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

			if(ligacaoEsgotoSituacao.getId().intValue() != LigacaoEsgotoSituacao.LIGADO){

				throw new ControladorException("atencao.situacao_instalacao_hidrometro_poco_invalida", null, ligacaoEsgotoSituacao
								.getDescricao());
			}

			if(imovel.getHidrometroInstalacaoHistorico() != null){
				throw new ControladorException("atencao.hidrometro_instalado_poco", null, "" + imovel.getId());
			}
		}

		// [FS0002] Verificar Situação do hidrometro.
		/*
		 * Hidrometro hidrometro =
		 * hidrometroInstalacaoHistorico.getHidrometro();
		 * if (hidrometro.getHidrometroSituacao().getId().intValue() !=
		 * HidrometroSituacao.DISPONIVEL .intValue()) { throw new
		 * ControladorException( "atencao.hidrometro_situacao_indisponivel",
		 * null, hidrometro.getHidrometroSituacao().getDescricao()); }
		 */

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Permite Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto .
	 * [FS0001]- Validar Ordem de Serviço. [FS0002] Verificar Situação do
	 * Imovel. [FS0003]- Validar Situação da Ligação de Esgoto do Imóvel.
	 * [FS0007]- Validar Situação da Ligação de Água do Imóvel. [FS0004 -
	 * Validar Volume Mínimo Fixado.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param ordemServicoId
	 * @param imovel
	 * @param dataMudanca
	 * @param volumeMinimoFixado
	 * @param novaSituacaoEsgoto
	 * @throws ControladorException
	 */
	public String validarMudancaSituacaoFaturamentoLigacaoesgotoExibir(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException{

		String retorno = null;

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// [FS0001]- Validar Ordem de Serviço
		if(ordemServico == null){
			throw new ControladorException("atencao.ordem_nao_existente", null);
		}

		ServicoTipo servicoTipo = ordemServico.getServicoTipo();

		if(servicoTipo != null){
			Integer idServicoTipo = servicoTipo.getId();

			Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(idServicoTipo,
							Operacao.OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO);

			if(idOperacao == null){
				throw new ControladorException("atencao.servico_associado_atualizar_faturamento_ligacao_esgoto_invalida");
			}
		}

		// [FS0001] Continuação
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);

		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		// Caso 4
		if(ordemServico.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		// [FS0002] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao.imovel.invalida", null, imovel.getId() + "");
		}

		// Validar Tipo de Serviço.
		if(ordemServico != null){
			if(servicoTipo.getId() != null){
				// Tipo de Serviço.
				Integer tipoServico = servicoTipo.getId();
				if(ServicoTipo.TAMPONAMENTO_LIGACAO_ESGOTO.contains(tipoServico)){
					// [FS0007]- Validar Situação da Ligação de Água do Imóvel
					this.validarSituacaoAguaImovel(imovel, tipoServico);
					// [FS0003]- Validar Situação da Ligação de Esgoto do Imóvel
					this.validarSituacaoEsgotoImovel(imovel, tipoServico);

					String parametroLigacaoEsgoto = ParametroMicromedicao.P_SITUACAO_LIGACAO_ESGOTO_SERVICO_TAMPONAMENTO.executar();

					if(parametroLigacaoEsgoto == null){
						throw new ControladorException("erro.parametro.sistema.inexistente");
					}

					if(parametroLigacaoEsgoto.trim().equals("1")){
						return new String("TAMPONADO");
					}else if(parametroLigacaoEsgoto.trim().equals("2")){
						return new String("CORTADO");
					}

				}else if(ServicoTipo.DESATIVACAO_LIGACAO_ESGOTO.contains(tipoServico)){
					// [FS0007]- Validar Situação da Ligação de Água do Imóvel
					this.validarSituacaoAguaImovel(imovel, tipoServico);
					// [FS0003]- Validar Situação da Ligação de Esgoto do Imóvel
					this.validarSituacaoEsgotoImovel(imovel, tipoServico);
					return new String("LIGADO FORA DE USO");
				}else if(ServicoTipo.RESTABELECIMENTO_LIGACAO_ESGOTO.contains(tipoServico)
								|| ServicoTipo.REATIVACAO_LIGACAO_ESGOTO.contains(tipoServico)){
					// [FS0004 - Validar Volume Mínimo Fixado]
					this.validarSituacaoEsgotoImovel(imovel, tipoServico);

					return new String("LIGADO");
				}else{
					return new String("LIGADO");
				}
			}
		}
		return retorno;
	}

	/**
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Permite Atualizar Ligação de Esgoto do Imóvel.
	 * [FS0006]-Atualizar Ligação de Esgoto do Imóvel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param ordemServico
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	/*
	 * private void validarMudancaSituacaoFaturamentoLigacaoesgotoEfetuar(
	 * OrdemServico ordemServico) throws ControladorException {
	 * Imovel imovel = ordemServico.getRegistroAtendimento().getImovel(); //
	 * Validar Tipo de Serviço. if (ordemServico != null) { if
	 * (ordemServico.getServicoTipo().getId() != null) { // Tipo de Serviço.
	 * Integer tipoServico = ordemServico.getServicoTipo().getId(); if
	 * (tipoServico.intValue() == ServicoTipo.TIPO_TAMPONAMENTO_LIGACAO_ESGOTO) {
	 * LigacaoEsgotoSituacao ligacaoEsgotoimovel = new LigacaoEsgotoSituacao();
	 * ligacaoEsgotoimovel.setId(LigacaoEsgotoSituacao.TAMPONADO);
	 * imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoimovel);
	 * imovel.setUltimaAlteracao(new Date()); // 4.2.1.3 Caso usuário confirme a
	 * efetivação getControladorImovel()
	 * .atualizarImovelExecucaoOrdemServicoLigacaoEsgoto( imovel,
	 * ligacaoEsgotoimovel); } else if (tipoServico.intValue() ==
	 * ServicoTipo.TIPO_DESATIVACAO_LIGACAO_ESGOTO) {
	 * LigacaoEsgotoSituacao ligacaoEsgotoimovel = new LigacaoEsgotoSituacao();
	 * ligacaoEsgotoimovel .setId(LigacaoEsgotoSituacao.LIG_FORA_DE_USO);
	 * imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoimovel);
	 * imovel.setUltimaAlteracao(new Date()); // 4.2.1.3 Caso usuário confirme a
	 * efetivação getControladorImovel()
	 * .atualizarImovelExecucaoOrdemServicoLigacaoEsgoto( imovel,
	 * ligacaoEsgotoimovel); } else if (tipoServico.intValue() ==
	 * ServicoTipo.TIPO_RESTABELECIMENTO_LIGACAO_ESGOTO) { // [FS0006]-Atualizar
	 * Ligação de Esgoto do Imóvel LigacaoEsgotoSituacao ligacaoEsgotoimovel =
	 * new LigacaoEsgotoSituacao();
	 * ligacaoEsgotoimovel.setId(LigacaoEsgotoSituacao.LIGADO);
	 * imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoimovel);
	 * imovel.setUltimaAlteracao(new Date()); // 4.2.1.3 Caso usuário confirme a
	 * efetivação getControladorImovel()
	 * .atualizarImovelExecucaoOrdemServicoLigacaoEsgoto( imovel,
	 * ligacaoEsgotoimovel); } else if (tipoServico.intValue() ==
	 * ServicoTipo.TIPO_REATIVACAO_LIGACAO_ESGOTO) { // [FS0006]-Atualizar
	 * Ligação de Esgoto do Imóvel LigacaoEsgotoSituacao ligacaoEsgotoimovel =
	 * new LigacaoEsgotoSituacao();
	 * ligacaoEsgotoimovel.setId(LigacaoEsgotoSituacao.LIGADO);
	 * imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoimovel);
	 * imovel.setUltimaAlteracao(new Date()); // 4.2.1.3 Caso usuário confirme a
	 * efetivação getControladorImovel()
	 * .atualizarImovelExecucaoOrdemServicoLigacaoEsgoto( imovel,
	 * ligacaoEsgotoimovel); } } } }
	 */

	/**
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Permite Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto .
	 * [FS0001]- Validar Ordem de Serviço [FS0002] Verificar Situação do Imovel
	 * [FS0002] Verificar Situação do Imovel [FS0003]- Validar Situação da
	 * Ligação de Esgoto do Imóvel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param ordemServicoId
	 * @param imovel
	 * @param dataMudanca
	 * @param volumeMinimoFixado
	 * @param novaSituacaoEsgoto
	 * @throws ControladorException
	 */
	public void efetuarMudancaSituacaoFaturamentoLiagacaoEsgoto(IntegracaoComercialHelper integracaoComercialHelper)
					throws ControladorException{

		// item 4.2.1 nova Situação do Esgoto igual Tamponamento da Ligação de
		// Esgoto
		/* Sistema deve atualizar a situação de esgoto do Imóvel para tamponado */

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		LigacaoEsgoto ligacaoEsgoto = integracaoComercialHelper.getLigacaoEsgoto();

		// Controle de concorrencia
		FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();
		filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID, ligacaoEsgoto.getId()));
		Collection colecaoEsgotoBase = getControladorUtil().pesquisar(filtroLigacaoEsgoto, LigacaoEsgoto.class.getName());

		if(!colecaoEsgotoBase.isEmpty()){
			LigacaoEsgoto ligacaoEsgotoBase = (LigacaoEsgoto) Util.retonarObjetoDeColecao(colecaoEsgotoBase);

			if(ligacaoEsgotoBase.getUltimaAlteracao().after(ligacaoEsgoto.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ordemServico.getRegistroAtendimento().getImovel().getId()));
		Collection colecaoImovelBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		if(!colecaoImovelBase.isEmpty()){
			Imovel imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelBase);

			if(imovelBase.getUltimaAlteracao().after(ordemServico.getRegistroAtendimento().getImovel().getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

		ServicoTipo servicoTipo = ordemServico.getServicoTipo();
		Integer idServicoTipo = servicoTipo.getId();

		Integer idLigacaoEsgotoSituacao = null;

		if(ServicoTipo.TAMPONAMENTO_LIGACAO_ESGOTO.contains(idServicoTipo)){

			idLigacaoEsgotoSituacao = LigacaoEsgotoSituacao.TAMPONADO;
		}else if(ServicoTipo.DESATIVACAO_LIGACAO_ESGOTO.contains(idServicoTipo)){

			idLigacaoEsgotoSituacao = LigacaoEsgotoSituacao.LIG_FORA_DE_USO;
		}else if(ServicoTipo.RESTABELECIMENTO_LIGACAO_ESGOTO.contains(idServicoTipo)){

			idLigacaoEsgotoSituacao = LigacaoEsgotoSituacao.LIGADO;
		}else if(ServicoTipo.REATIVACAO_LIGACAO_ESGOTO.contains(idServicoTipo)){

			idLigacaoEsgotoSituacao = LigacaoEsgotoSituacao.LIGADO;
		}else{

			idLigacaoEsgotoSituacao = LigacaoEsgotoSituacao.LIGADO;
		}

		ligacaoEsgotoSituacao.setId(idLigacaoEsgotoSituacao);

		getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(ordemServico.getRegistroAtendimento().getImovel(),
						ligacaoEsgotoSituacao);

		ordemServico.setUltimaAlteracao(new Date());

		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);

			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		if(servicoTipo.getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							servicoTipo.getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(integracaoComercialHelper.getQtdParcelas()).intValue());
		}

	}

	/**
	 * [UC0356]- Efetuar mudança de Faturamento na Ligação de Água
	 * [FS0006]-Atualizar Ligação de Esgoto
	 * Permite atualizar a Tabele de Ligação Esdoto . Update LIGACAO_ESGOTO
	 * LESG_NNCONSUMOMINIMOESGOTO (volume Mínimo fixado) LESG_TMULTIMAALTERADAO
	 * (data e hora correntes) Where LESG_ID=IMOV_ID da tabela IMOVEL
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public void atualizarLigacaoEsgoto(Imovel imovel, String volumeMinimoFixado) throws ControladorException{

		// Ligação de Esgoto

		String idImovel = imovel.getId().toString();

		FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();

		filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID, idImovel));

		filtroLigacaoEsgoto.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");

		/*
		 * Collection colecaoLigacaoAgua = getControladorUtil().pesquisar(
		 * filtroLigacaoEsgoto, LigacaoEsgoto.class.getName());
		 */
		Collection colecaoLigacaoEsgoto = null;
		LigacaoEsgoto ligacaoEsgoto = (LigacaoEsgoto) colecaoLigacaoEsgoto.iterator().next();

		if(volumeMinimoFixado != null && !volumeMinimoFixado.trim().equals("")){
			Integer volumeMinimoFixadoInformado = new Integer(volumeMinimoFixado);
			// Atualizando campos da tabela LigacaoEsgoto
			ligacaoEsgoto.setConsumoMinimo(volumeMinimoFixadoInformado);
			ligacaoEsgoto.setUltimaAlteracao(new Date());
			// Atualiza tabela LigacaoAgua
			getControladorUtil().atualizar(ligacaoEsgoto);
		}else{
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.requerid", null, "Volume Mínimo Fixado");
		}
	}

	/**
	 * [UC0368] Atualizar Instalação do hidrômetro
	 * [FS0001] - Verificar a existência da matrícula do Imóvel [FS0002] -
	 * Verificar a situação do Imóvel [FS0003] - Validar existência do
	 * hidrômetro [FS0004] - Validar leitura instalação hidrômetro [FS0005] -
	 * Validar leitura retirada hidrômetro [FS0006] - Validar leitura retirada
	 * corte [FS0007] - Validar Leitura Supressão [FS0009] - Verificar sucesso
	 * da transação
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public void atualizarInstalacaoHidrometro(Imovel imovel, Integer medicaoTipo) throws ControladorException{

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;

		if(MedicaoTipo.LIGACAO_AGUA.equals(medicaoTipo)){
			hidrometroInstalacaoHistorico = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico();
		}else if(MedicaoTipo.POCO.equals(medicaoTipo)){
			hidrometroInstalacaoHistorico = imovel.getHidrometroInstalacaoHistorico();
		}

		// [FS0001] - Verificar a existência da matrícula do Imóvel
		// [FS0002] - Verificar a situação do Imóvel
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
		getControladorImovel().pesquisarImovelSituacaoAtiva(filtroImovel);

		// [FS0003] - Validar existência do hidrômetro
		getControladorImovel().validarExistenciaHidrometro(imovel, medicaoTipo);

		// [FS0004] - Validar leitura instalação hidrômetro

		/*
		 * Caso a leitura da instalção do hidrômetro informada seja igual a zero ou valores
		 * negativos, exibir a mensagem: "Leitura instalação deve
		 * somente conter números positivos"
		 */
		if(!Util.validarNumeroMaiorQueZERO(hidrometroInstalacaoHistorico.getNumeroLeituraInstalacao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.long", null, "Leitura de Instalação");
		}



		// [FS0009] - Verificar sucesso da transação
		getControladorUtil().atualizar(hidrometroInstalacaoHistorico);

	}

	public void atualizarInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		this.atualizarInstalacaoHidrometro(integracaoComercialHelper.getImovel(), integracaoComercialHelper.getIdMedicaoTipo());
	}

	/**
	 * [UC0356]- Efetuar mudança de Faturamento na Ligação de Água
	 * [FS0007]- Validar Situação da Ligação de Água do Imóvel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public String validarSituacaoAguaImovel(Imovel imovel, Integer tipoServico) throws ControladorException{

		String retorno = null;

		String pValidarSituacaoSituacaoLigacaoAgua = (String) ParametroAtendimentoPublico.P_VALIDAR_SITUACAO_LIGACAO_AGUA.executar(this, 0);

		if(pValidarSituacaoSituacaoLigacaoAgua != null && pValidarSituacaoSituacaoLigacaoAgua.equals(ConstantesSistema.SIM.toString())){
			LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();
			Integer idLigacaoAguaSituacao = ligacaoAguaSituacao.getId();

			if(ServicoTipo.TAMPONAMENTO_LIGACAO_ESGOTO.contains(tipoServico)){
				String pValidarSituacaoCortado = (String) ParametroAtendimentoPublico.P_VALIDAR_SITUACAO_LIGACAO_AGUA_CORTADA_NO_TAMPONAMENTO_LIGACAO_ESGOTO
								.executar(this, 0);

				if(idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.LIGADO)
								|| (pValidarSituacaoCortado.equals(ConstantesSistema.SIM.toString()) && idLigacaoAguaSituacao
												.equals(LigacaoAguaSituacao.CORTADO))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.situacao.ligacaoagua.imovel.tamponamento.invalida", null, imovel.getId()
									.toString());
				}

				retorno = new String("TAMPONADO");

			}else if(ServicoTipo.DESATIVACAO_LIGACAO_ESGOTO.contains(tipoServico)){
				if(idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.LIGADO) || idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.CORTADO)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.situacao.ligacaoagua.imovel.desativacao.invalida", null, imovel.getId()
									.toString());
				}

				retorno = new String("LIGADO FORA DE USO");
			}
		}

	

		

		return retorno;
	}

	/**
	 * [UC0356]- Efetuar mudança de Faturamento na Ligação de Água
	 * [FS0003]- Validar Situação da Ligação de Esgoto do Imóvel
	 * 
	 * @author Leandro Cavalcanti
	 * @date 18/07/2006
	 * @param imovel
	 * @param volumeMinimoFixado
	 * @throws ControladorException
	 */
	public void validarSituacaoEsgotoImovel(Imovel imovel, Integer tipoServico) throws ControladorException{

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

		if(ligacaoEsgotoSituacao != null){
			Integer idLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getId();

			if(ServicoTipo.TAMPONAMENTO_LIGACAO_ESGOTO.contains(tipoServico)
							&& (idLigacaoEsgotoSituacao.intValue() != LigacaoEsgotoSituacao.LIGADO.intValue())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.situacao_tamponamento_ligacao_esgoto_imovel_invalida", null, tipoServico.toString());
			}else if(ServicoTipo.DESATIVACAO_LIGACAO_ESGOTO.contains(tipoServico)
							&& (idLigacaoEsgotoSituacao.intValue() != LigacaoEsgotoSituacao.LIGADO.intValue())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.situacao_desativacao_ligacao_esgoto_imovel_invalida", null, tipoServico.toString());
			}else if(ServicoTipo.RESTABELECIMENTO_LIGACAO_ESGOTO.contains(tipoServico)
							&& (idLigacaoEsgotoSituacao.intValue() != LigacaoEsgotoSituacao.TAMPONADO.intValue())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.situacao_restabelecimento_ligacao_esgoto_imovel_invalida", null,
								tipoServico.toString());
			}else if(ServicoTipo.REATIVACAO_LIGACAO_ESGOTO.contains(tipoServico)
							&& (idLigacaoEsgotoSituacao.intValue() != LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue())){

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.situacao_reativacaoo_ligacao_esgoto_imovel_invalida", null, tipoServico.toString());
			}

		}
	}

	/**
	 * [UC0364] Efetuar Substituição de hidrômetro
	 * [SB0001] Atualizar o histórico da instalação do hidrômetro substituido
	 * [SB0002] Gerar Histórico de instalação do hidrômetro [SB0003] Atualizar
	 * Imóvel/Ligação de Água [SB0004] Atualizar situação de hidrômetro na
	 * tabela HIDROMETRO [SB0005] Atualizar situação do hidrômetro substituido
	 * na tabela HIDROMETRO
	 * 
	 * @author Ana Maria
	 * @date 24/07/2006
	 * @param hidrometroInstalacaoHistorico
	 * @param materialImovel
	 * @param hidrometroSubstituicaoHistorico
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void efetuarSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
		String matriculaImovel = integracaoComercialHelper.getMatriculaImovel();
		HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = integracaoComercialHelper.getHidrometroSubstituicaoHistorico();
		String situacaoHidrometroSubstituido = integracaoComercialHelper.getSituacaoHidrometroSubstituido();
		Integer localArmazenagemHidrometro = integracaoComercialHelper.getLocalArmazenagemHidrometro();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

		Integer id = null;

		validacaoSubstituicaoHidrometro(matriculaImovel, hidrometroInstalacaoHistorico.getHidrometro().getNumero(),
						situacaoHidrometroSubstituido);

		try{
			// [SB0001] Atualizar o histórico da instalação do hidrômetro
			// substituido
			repositorioAtendimentoPublico.atualizarHidrometroInstalacoHistorico(hidrometroSubstituicaoHistorico);

			// [SB0002] Gerar Histórico de instalação do hidrômetro
			hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("2"));
			id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

			// [SB0003]Atualizar Imóvel/Ligação de Água

			// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
			// colunas da tabela LIGACAO_AGUA
			if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
								.getLigacaoAgua().getId(), id);
				// Caso o tipo de medição seja igual a Poço, atualiza as colunas
				// da tabela POCO
			}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
				repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(
								hidrometroInstalacaoHistorico.getImovel().getId(), id);
			}

			// [SB004]Atualizar situação de hidrômetro na tabela HIDROMETRO
			Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);

			// [SB005]Atualizar situação do hidrômetro substituido na tabela
			// HIDROMETRO
			situacaoHidrometro = new Integer(situacaoHidrometroSubstituido);
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);

			repositorioAtendimentoPublico.atualizarLocalArmazanagemHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
							localArmazenagemHidrometro);

			// [SB006]Atualizar Ordem de Serviço
			if(!integracaoComercialHelper.isVeioEncerrarOS()){
				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), new Integer(integracaoComercialHelper.getQtdParcelas()));
			}

			// [SB0006] - Verifica Credito de Consumo
			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

			// Zerar crédito de consumo do cliente
			short indicadorZeraCreditoClienteSubsHid = sistemaParametro.getIndicadorZeraCreditoClienteSubsHid();

			// Zerar crédito de consumo da concessionária
			short indicadorZeraCreditoEmpresaSubsHid = sistemaParametro.getIndicadorZeraCreditoEmpresaSubsHid();

			if(indicadorZeraCreditoClienteSubsHid == ConstantesSistema.SIM || indicadorZeraCreditoEmpresaSubsHid == ConstantesSistema.SIM){
				Integer idImovel = Util.converterStringParaInteger(matriculaImovel);
				Integer idHidrometro = hidrometroSubstituicaoHistorico.getHidrometro().getId();

				MedicaoHistorico medicaoHistorico = getControladorMicromedicao().consultarUltimaMedicaoHistoricoDoImovel(idImovel,
								idHidrometro);

				if(medicaoHistorico != null){
					Integer consumoCreditoAnterior = medicaoHistorico.getConsumoCreditoAnterior();

					if(consumoCreditoAnterior != null
									&& ((indicadorZeraCreditoClienteSubsHid == ConstantesSistema.SIM && consumoCreditoAnterior < 0) || (indicadorZeraCreditoEmpresaSubsHid == ConstantesSistema.SIM && consumoCreditoAnterior > 0))){

						// Zerar crédito de consumo
						medicaoHistorico.setConsumoCreditoAnterior(0);

						getControladorUtil().atualizar(medicaoHistorico);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0364] Efetuar Substituição de hidrômetro
	 * Validar Substituição de hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/07/2006
	 * @param matriculaImovel
	 *            ,
	 * @param numeroHidrometro
	 *            ,
	 * @param situacaoHidrometroSubstituido
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoSubstituicaoHidrometro(String matriculaImovel, String numeroHidrometro, String situacaoHidrometroSubstituido)
					throws ControladorException{

		// Caso o hidrômetro substituido esteja com situacao igual a DISPONÍVEL
		if(situacaoHidrometroSubstituido.equals("-1") || situacaoHidrometroSubstituido.equals(HidrometroSituacao.INSTALADO.toString())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.hidrometro_situacao_disponivel");
		}

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade("hidrometroSituacao");
		filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));

		Collection colecaoHidrometro = null;

		colecaoHidrometro = getControladorUtil().pesquisar(filtroHidrometro, Hidrometro.class.getName());

		// [FS002]Caso o hidrômetro informado esteja com a situação diferente de DISPONÍVEL
		Iterator iteratorHidrometro = colecaoHidrometro.iterator();
		while(iteratorHidrometro.hasNext()){
			Hidrometro hidrometro = (Hidrometro) iteratorHidrometro.next();
			Integer idSituacaoHidrometro = hidrometro.getHidrometroSituacao().getId();
			if(!(idSituacaoHidrometro.equals(HidrometroSituacao.DISPONIVEL))){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_situacao_indisponivel", null, hidrometro.getHidrometroSituacao()
								.getDescricao());
			}
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, matriculaImovel));

		Collection colecaoImoveis = null;

		colecaoImoveis = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
		Iterator iteratorImovel = colecaoImoveis.iterator();
		Imovel imovel = (Imovel) iteratorImovel.next();

		// [FS008]Caso situção do Imóvel não seja ativo(IMOV_ICEXCLUSAO da tabela IMOVEL
		// correspondete a "não")
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId().toString());
		}
	}

	/**
	 * [UC0360]- Efetuar Supressão da Ligação de Água
	 * [SB0001]- Atualizar Ligação de Água [SB0002]- Atualizar Imóvel [SB0004]- Atualizar Histótico
	 * de Instalação de hidrômetro
	 * 
	 * @author Rômulo Aurélio
	 * @date 28/07/2006
	 * @param imovel
	 * @throws ControladorException
	 */

	@SuppressWarnings("unchecked")
	public void efetuarSupressaoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper, boolean retirarHidrometro)
					throws ControladorException{

		Imovel imovel = integracaoComercialHelper.getImovel();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Boolean gerouDebito = false;
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

		// [SB0001] Atualizar Ligação de Água
		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);
		getControladorUtil().atualizar(ligacaoAgua);

		// [SB0002] Atualizar Imovel

			try{
				this.getControladorImovel().verificarImovelControleConcorrencia(imovel);
				getControladorUtil().atualizar(imovel);
			}catch(Exception e){
				throw new ControladorException("atencao.tipo_supressao_nao_permitido");
				 
			}

		if(hidrometroInstalacaoHistorico != null){

			if(retirarHidrometro){
				gerouDebito = true;
				this.efetuarRetiradaHidrometro(integracaoComercialHelper);
			}

			getControladorUtil().atualizar(hidrometroInstalacaoHistorico);
		}
		// Atualiza Ordem de Servico
		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServico.getId()));
		Collection colecaoOrdemServico = getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());
		if(!colecaoOrdemServico.isEmpty()){
			OrdemServico ordemServicoBase = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOrdemServico);

			if(ordemServicoBase.getUltimaAlteracao().after(ordemServico.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}
		// [SB006]Atualizar Ordem de Serviço
		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, true);
		}

		/*
		 * [OC790655][UC0360][SB0006]: Atualizar Dados Execução no Histórico de
		 * Manutenção da Ligação do Imóvel ao EfetuarSupressaoLigacaoAgua
		 */
		if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
			getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
							HistoricoManutencaoLigacao.EFETUAR_SUPRESSAO_LIGACAO_AGUA);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			if(!gerouDebito){
				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), Integer.valueOf(integracaoComercialHelper.getQtdParcelas()));

			}
		}

		/**
		 * 8. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo na
		 * tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null){

			/**
			 * 8.1. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE) obtendo
			 * os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os
			 * campos exceto o CPRO_ID e campos de quantidade/valores) de linha na tabela. Caso
			 * exista, acumular
			 * na existente as colunas de quantidade e valor, caso contrário, inserir nova linha.
			 */
			getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}
	}

	// atencao.situacao_volume_minimo_fixado_nao_multiplo= Valor do volume
	// Mínimo Fixado deve ser alterado para {0} valor multiplo de quantidade de
	// economias {1}.
	public Integer validarVolumeMinimoFixadoEsgoto(Imovel imovel, String volumeMinimoFixado) throws ControladorException{

		// [UC0105] - Obter Consumo Mínimo da Ligação
		int consumoMinimoObtido = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);
		Integer consumoMinimoObtido1 = new Integer(consumoMinimoObtido);

		// Verificar se o volume Mínimo informado seja menor que o valor
		// Mínimo obtido para Imóvel.
		if(volumeMinimoFixado != null && !volumeMinimoFixado.trim().equals("")){
			if(!volumeMinimoFixado.trim().equalsIgnoreCase(ConstantesSistema.SET_ZERO.toString())){
				Integer consumoInformado = Integer.parseInt(volumeMinimoFixado);
				if(consumoInformado.intValue() < consumoMinimoObtido1.intValue()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.situacao_volume_minimo_fixado_menor_consumo_calculado", null,
									consumoMinimoObtido + "");
				}
			}
		}else{
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.requerid", null, "Situação da Ligação de Esgoto");
		}

		return new Integer(volumeMinimoFixado);
	}

	/**
	 * [UC0359] Efetuar Restabelecimento Ligação de Água
	 * Permite efetuar restabelecimento da Ligação de Água ou pelo menu ou pela
	 * funcionalidade encerrar a Execução da ordem de Serviço.
	 * [SB0001] Atualizar Imóvel/Ligação de Água/Ligação de Esgoto
	 * 
	 * @author Rômulo Aurélio
	 * @date 12/07/2006
	 * @param idImovel
	 *            ,idOrdemServico
	 * @throws ControladorException
	 */

	public void efetuarRestabelecimentoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		// [SB0001] - Atualizar Imóvel/Ligação de Água

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

		// Caso 1

		Imovel imovel = null;

		if(ordemServico.getRegistroAtendimento() != null){
			if(ordemServico.getRegistroAtendimento().getImovel() == null){
				throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
								+ ordemServico.getRegistroAtendimento().getId());
			}
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}else{

			FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ORDEMS_SERVICO_ID, ordemServico.getId()
							.toString()));
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_AGUA_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LIGACAO_ESGOTO_SITUACAO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_LOCALIDADE);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_SETOR_COMERCIAL);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL_QUADRA);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.IMOVEL);

			CobrancaDocumento cobrancaDocumentoRetorno = (CobrancaDocumento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroCobrancaDocumento, CobrancaDocumento.class.getName()));

			if(cobrancaDocumentoRetorno == null || cobrancaDocumentoRetorno.getImovel() == null){
				throw new ControladorException("atencao.ordem_servico_doc_cobranca_invalida");
			}
			ordemServico.setCobrancaDocumento(cobrancaDocumentoRetorno);
			imovel = cobrancaDocumentoRetorno.getImovel();
		}

		if(imovel == null){
			throw new ControladorException("atencao.registro.atendimento.ou.doc.cobranca.obrigatorio");
		}

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
		Collection colecaoImovelBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		Imovel imovelBase = null;

		if(!colecaoImovelBase.isEmpty()){
			imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelBase);

			if(ordemServico.getRegistroAtendimento() != null){

				if(imovelBase.getUltimaAlteracao().after(ordemServico.getRegistroAtendimento().getImovel().getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

			}else{

				if(imovelBase.getUltimaAlteracao().after(ordemServico.getCobrancaDocumento().getImovel().getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
			}

		}

		LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();
		ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);
		ligacaoAguaSituacao.setUltimaAlteracao(new Date());

		imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);
		imovel.setUltimaAlteracao(new Date());

		this.getControladorImovel().verificarImovelControleConcorrencia(imovel);
		// [SB0002] Atualizar Imóvel
		getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();
		if(ligacaoEsgotoSituacao != null && ligacaoEsgotoSituacao.getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

			ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);
			ligacaoEsgotoSituacao.setUltimaAlteracao(new Date());

			getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
		}

		// Caso 2
		LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();
		ligacaoAgua.setDataRestabelecimentoAgua(ordemServico.getDataExecucao());

		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);
		this.getControladorLigacaoAgua().atualizarLigacaoAguaRestabelecimento(ligacaoAgua);

		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);

			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, true);
		}

		/*
		 * [OC790655][UC0359][SB0003]: Atualizar Dados Execução no Histórico de
		 * Manutenção da Ligação do Imóvel ao EfetuarRestabelecimentoAgua
		 */
		if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
			getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
							HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico().gerarDebitoOrdemServico(
							ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(),
							Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
											.toString()), new Integer(integracaoComercialHelper.getQtdParcelas()));
		}

		/**
		 * 8. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo na
		 * tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * 8.1. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE) obtendo
			 * os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os
			 * campos exceto o CPRO_ID e campos de quantidade/valores) de linha na tabela. Caso
			 * exista, acumular
			 * na existente as colunas de quantidade e valor, caso contrário, inserir nova linha.
			 */
			getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}
	}

	/**
	 * [UC0396] Inserir Tipo de Retorno da OS Referida
	 * [FS0003] - Validar atendimento do motivo de encerramento.
	 * 
	 * @author lms
	 * @created 28/07/2006
	 * @throws ControladorException
	 */
	public void validarAtendimentoMotivoEncerramento(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException{

		// [FS0003] - Validar atendimento do motivo de encerramento.

		/*
		 * Caso o indicador de deferimento esteja preenchido com não, o
		 * indicador de Execução do motivo de encerramento informado deverá
		 * estar com não, caso contrário, exibir a mensagem: "Motivo de
		 * encerramento do atendimento incompatível com o indicador de
		 * deferimento informado"
		 */
		if(osReferidaRetornoTipo.getAtendimentoMotivoEncerramento() != null){
			if(ConstantesSistema.NAO.equals(osReferidaRetornoTipo.getIndicadorDeferimento())
							&& ConstantesSistema.NAO
											.equals(osReferidaRetornoTipo.getAtendimentoMotivoEncerramento().getIndicadorExecucao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atendimento_motivo_encerramento.incompativel");
			}
		}

	}

	/**
	 * [UC0396] Inserir Tipo de Retorno da OS Referida
	 * [FS0002] - Solicitar o indicador de troca de Serviço, situação e motivo
	 * de encerramento [FS0003] - Validar atendimento do motivo de encerramento
	 * [FS0005] - Validar indicador de deferimento [FS0006] - Validar indicador
	 * de deferimento x indicador de troca de Serviço [FS0007] - Verificar
	 * sucesso da transação
	 * 
	 * @author lms
	 * @created 21/07/2006
	 * @throws ControladorException
	 */
	public Integer inserirOSReferidaRetornoTipo(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException{

		// [FS0002] - Solicitar o indicador de troca de Serviço, situação e
		// motivo de encerramento

		/*
		 * Caso a Referência do tipo Serviço escolhido possuir o indicador de
		 * existência da Referência preenchido com não, as seguintes informações
		 * deverão estar desabilitadas para preenchimento e os seus conteúdos
		 * deverão estar preenchidos com:
		 * ORRT_ICTROCASERVICO = 2 ORRT_CDSITUACAOOSREFERENCIA = null AMEN_ID =
		 * null
		 */
		if(ConstantesSistema.NAO.equals(osReferidaRetornoTipo.getServicoTipoReferencia().getIndicadorExistenciaOsReferencia())){
			osReferidaRetornoTipo.setIndicadorTrocaServico(ConstantesSistema.NAO);
			osReferidaRetornoTipo.setSituacaoOsReferencia(null);
			osReferidaRetornoTipo.setAtendimentoMotivoEncerramento(null);
		}

		// [FS0003] - Validar atendimento do motivo de encerramento
		this.validarAtendimentoMotivoEncerramento(osReferidaRetornoTipo);

		// [FS0005] - Validar indicador de deferimento

		/*
		 * Apenas uma das descrições dos tipos de retorno da OS referida, por
		 * Referência do tipo de Serviço cujo identificador de uso esteja ATIVO,
		 * pode ter este indicador com o valor UM, o resto deverá possuir o
		 * valor igual a DOIS, caso contrário, exibir a mensagem: "Existe mais
		 * de um indicador de deferimento com situação de deferido para a mesma
		 * Referência do tipo Serviço informado."
		 */
		try{
			if(ConstantesSistema.SIM.equals(osReferidaRetornoTipo.getIndicadorDeferimento())){
				int total = repositorioAtendimentoPublico
								.consultarTotalIndicadorDeferimentoAtivoPorServicoTipoReferencia(osReferidaRetornoTipo);
				if(total > 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.deferimento.incompativel");
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0006] - Validar indicador de deferimento x indicador de troca de
		// Serviço

		/*
		 * Caso o indicador de deferimento estiver marcando deferido, o
		 * indicador da troca de Serviço deve estar marcado como não, caso
		 * contrário, exibir a mensagem: "Indicador de troca de Serviço
		 * incompatível com o indicador de deferimento informado."
		 */
		if(ConstantesSistema.SIM.equals(osReferidaRetornoTipo.getIndicadorDeferimento())
						&& ConstantesSistema.SIM.equals(osReferidaRetornoTipo.getIndicadorTrocaServico())){
			throw new ControladorException("atencao.troca_servico.incompativel");
		}

		osReferidaRetornoTipo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		osReferidaRetornoTipo.setUltimaAlteracao(new Date());

		// [FS0007] - Verificar sucesso da transação
		return (Integer) getControladorUtil().inserir(osReferidaRetornoTipo);

	}

	/**
	 * [UC0381] Inserir Material com Unidade
	 * Permite a inclusao de um novo material
	 * [SB0001] Gerar Material com Unidade
	 * 1.1Inclui o material na tabela Material
	 * 
	 * @author Rômulo Aurélio
	 * @date 31/07/2006
	 * @param descricao
	 * @param descricaoAbreviada
	 * @param unidadeMaterial
	 * @throws ControladorException
	 */

	public Integer inserirMaterial(String descricao, String descricaoAbreviada, String unidadeMaterial, Usuario usuarioLogado)
					throws ControladorException{

		Material material = new Material();

		material.setDescricao(descricao);

		material.setDescricaoAbreviada(descricaoAbreviada);

		String idUnidadeMaterial = unidadeMaterial;

		if(idUnidadeMaterial != null && !idUnidadeMaterial.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			MaterialUnidade materialUnidade = new MaterialUnidade();

			materialUnidade.setId(new Integer(idUnidadeMaterial));

			material.setMaterialUnidade(materialUnidade);

		}

		String indicadorUso = "1";

		material.setIndicadorUso(new Short(indicadorUso));

		// [FS0001] Verificar existencia da Descrição

		FiltroMaterial filtroMaterial = new FiltroMaterial();

		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.DESCRICAO, material.getDescricao()));

		Collection colecaoMaterial = getControladorUtil().pesquisar(filtroMaterial, Material.class.getName());

		if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){
			throw new ControladorException("atencao.descricao_ja_existente_material", null, "" + material.getDescricao() + "");
		}

		if(material.getDescricaoAbreviada() != null && !material.getDescricaoAbreviada().equals("")){

			filtroMaterial.limparListaParametros();

			// [FS0002]- Verificar existência da Descrição abreviada
			filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.DESCRICAO_ABREVIADA, material.getDescricaoAbreviada()));

			colecaoMaterial = getControladorUtil().pesquisar(filtroMaterial, Material.class.getName());

			if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){
				throw new ControladorException("atencao.descricao_abreviada_ja_existente_material", null, "" + material.getDescricao() + "");
			}

		}

		material.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSação----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_MATERIAL_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MATERIAL_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		material.setOperacaoEfetuada(operacaoEfetuada);
		material.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(material);

		// --------FIM---- REGISTRAR TRANSação----------------------------
		Integer idMaterial = (Integer) getControladorUtil().inserir(material);

		return idMaterial;

	}

	/**
	 * [UC0385] Inserir Tipo Perfil Serviço
	 * 
	 * @author Ana Maria
	 * @date 01/08/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public Integer inserirServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo) throws ControladorException{

		// [FS0004] Verificar existencia da Descrição
		FiltroServicoPerfilTipo filtroServicoPerfilTipo = new FiltroServicoPerfilTipo();

		filtroServicoPerfilTipo
						.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.DESCRICAO, servicoPerfilTipo.getDescricao()));

		filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoServicoPerfilTipo = getControladorUtil().pesquisar(filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());

		if(colecaoServicoPerfilTipo != null && !colecaoServicoPerfilTipo.isEmpty()){
			throw new ControladorException("atencao.descricao_ja_existente_tipo_perfil_servico", null, ""
							+ servicoPerfilTipo.getDescricao() + "");
		}

		getControladorUtil().inserir(servicoPerfilTipo);

		// ------------ REGISTRAR TRANSação----------------------------
		/*
		 * RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		 * Operacao.OPERACAO_SERVICO_TIPO_INSERIR, new
		 * UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE,
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		 * Operacao operacao = new Operacao();
		 * operacao.setId(Operacao.OPERACAO_SERVICO_TIPO_PERFIL_INSERIR);
		 * OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		 * operacaoEfetuada.setOperacao(operacao);
		 * servicoPerfilTipo.setOperacaoEfetuada(operacaoEfetuada);
		 * servicoPerfilTipo.adicionarUsuario(Usuario.USUARIO_TESTE,
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		 * registradorOperacao.registrarOperacao(servicoPerfilTipo);
		 */

		// ------------ REGISTRAR TRANSação----------------------------
		return servicoPerfilTipo.getId();
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0004] - Validar Perfil do Serviço
	 * 
	 * @author lms
	 * @date 01/08/2006
	 */
	public ServicoPerfilTipo pesquisarServicoPerfilTipo(Integer idServicoPerfilTipo) throws ControladorException{

		FiltroServicoPerfilTipo filtro = new FiltroServicoPerfilTipo();
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.ID, idServicoPerfilTipo));
		return pesquisar(filtro, ServicoPerfilTipo.class, "Tipo do Perfil");
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0005] - Validar Tipo de Serviço de Referência
	 * 
	 * @author lms
	 * @date 02/08/2006
	 */
	public ServicoTipoReferencia pesquisarServicoTipoReferencia(Integer idServicoTipoReferencia) throws ControladorException{

		FiltroServicoTipoReferencia filtro = new FiltroServicoTipoReferencia();
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.ID, idServicoTipoReferencia));
		return pesquisar(filtro, ServicoTipoReferencia.class, "Tipo do Serviço de Referência");
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0009] - Validar Atividade
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public Atividade pesquisarAtividade(Integer idAtividade, String atividadeUnica) throws ControladorException{

		FiltroAtividade filtro = new FiltroAtividade();
		filtro.adicionarParametro(new ParametroSimples(FiltroAtividade.ID, idAtividade));
		if(atividadeUnica != null && !atividadeUnica.trim().equals("")){
			filtro.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA,
							ConstantesSistema.INDICADOR_USO_DESATIVO));
		}
		return pesquisar(filtro, Atividade.class, "Atividade");
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0010] - Validar Material
	 * 
	 * @author lms
	 * @date 08/08/2006
	 */
	public Material pesquisarMaterial(Integer idMaterial) throws ControladorException{

		FiltroMaterial filtro = new FiltroMaterial();
		filtro.adicionarParametro(new ParametroSimples(FiltroMaterial.ID, idMaterial));
		return pesquisar(filtro, Material.class, "Material");
	}

	private <T> T pesquisar(Filtro filtro, Class clazz, String label) throws ControladorException{

		T objeto = null;
		Collection colecao = getControladorUtil().pesquisar(filtro, clazz.getName());
		if(colecao == null || colecao.isEmpty()){
			throw new ControladorException("atencao.pesquisa_inexistente", null, label);
		}
		objeto = (T) colecao.iterator().next();
		return objeto;
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * [FS0006] - Validar Ordem de Execução
	 * 
	 * @author lms
	 * @date 05/08/2006
	 */
	public void validarOrdemExecucao(Collection colecaoServicoTipoAtividade, Short ordemExecucao) throws ControladorException{

		Set ordensExecucao = new TreeSet();
		if(colecaoServicoTipoAtividade != null && !colecaoServicoTipoAtividade.isEmpty()){
			for(Iterator iter = colecaoServicoTipoAtividade.iterator(); iter.hasNext();){
				ordensExecucao.add(((ServicoTipoAtividade) iter.next()).getNumeroExecucao());
			}
		}
		if(ordensExecucao.contains(ordemExecucao)){
			throw new ControladorException("atencao.ordem_execucao.ja_existente", null, ordemExecucao + "");
		}
		ordensExecucao.add(ordemExecucao);
		Short pred = 0;
		Short succ;
		for(Iterator iter = ordensExecucao.iterator(); iter.hasNext();){
			succ = (Short) iter.next();
			if(succ - pred > 1){
				throw new ControladorException("atencao.ordem_execucao.invalida", null, ordemExecucao + "");
			}
			pred = succ;
		}
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public Integer inserirServicoTipo(ServicoTipo servicoTipo, Usuario usuarioLogado) throws ControladorException{

		// [FS0001] - Verificar existência da Descrição
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.DESCRICAO, servicoTipo.getDescricao()));
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		ServicoTipo st = null;

		try{
			st = pesquisar(filtroServicoTipo, ServicoTipo.class, "");
		}catch(ControladorException e){
		}

		if(st != null){
			throw new ControladorException("atencao.servico_tipo.descricao_ja_existente");
		}

		// ------------ REGISTRAR TRANSação----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_TIPO_SERVICO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_TIPO_SERVICO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		servicoTipo.setOperacaoEfetuada(operacaoEfetuada);
		servicoTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoTipo);

		// ------------ REGISTRAR TRANSação----------------------------

		// [FS0002] - Verificar existência da Descrição abreviada
		if(servicoTipo.getDescricaoAbreviada() != null){

			filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.DESCRICAO_ABREVIADA, servicoTipo
							.getDescricaoAbreviada()));
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			try{
				st = pesquisar(filtroServicoTipo, ServicoTipo.class, "");
			}catch(ControladorException e){
			}

			if(st != null){
				throw new ControladorException("atencao.servico_tipo.descricao_abreviada_ja_existente");
			}

		}

		// [FS0003] - Validar Tipo de Débito
		if(servicoTipo.getDebitoTipo() != null){
			getControladorFaturamento().pesquisarDebitoTipo(servicoTipo.getDebitoTipo().getId());
		}

		// [FS0004] - Validar Perfil do Serviço
		if(servicoTipo.getServicoPerfilTipo() != null){
			pesquisarServicoPerfilTipo(servicoTipo.getServicoPerfilTipo().getId());
		}

		// [FS0006] - Validar Ordem de Execução

		// [FS0009] - Validar Atividade

		// [FS0010] - Validar Material

		Date dataUltimaAlteracao = new Date();

		servicoTipo.setUltimaAlteracao(dataUltimaAlteracao);
		servicoTipo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		Collection<ServicoTipoAtividade> colecaoServicoTipoAtividades = servicoTipo.getServicoTipoAtividades();
		Collection<ServicoTipoMaterial> colecaoServicoTipoMateriais = servicoTipo.getServicoTipoMateriais();
		Collection<ServicoAssociado> colecaoServicosAssociados = servicoTipo.getServicosAssociados();
		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = servicoTipo.getServicosTipoTramite();

		Integer id = null;

		// inserir tipo servico referencia
		try{
			if(servicoTipo.getServicoTipoReferencia() != null && servicoTipo.getServicoTipoReferencia().getId() == null){
				ServicoTipoReferencia servicoTipoReferencia = servicoTipo.getServicoTipoReferencia();
				servicoTipoReferencia.setUltimaAlteracao(new Date());
				Integer referencia = (Integer) getControladorUtil().inserir(servicoTipoReferencia);

				servicoTipo.getServicoTipoReferencia().setId(referencia);
			}else if(servicoTipo.getServicoTipoReferencia() != null && servicoTipo.getServicoTipoReferencia().getId() != null){
				// [FS0005] - Validar Tipo de Serviço Referência
				if(servicoTipo.getServicoTipoReferencia() != null){
					servicoTipo.getServicoTipoReferencia().setUltimaAlteracao(new Date());
					pesquisarServicoTipoReferencia(servicoTipo.getServicoTipoReferencia().getId());
				}
			}

			// [FS0008] - Verificar Sucesso da Operação
			id = inserirServicoTipoSemColecoes(servicoTipo);

		}catch(ControladorException e){
			servicoTipo.getServicoTipoReferencia().setId(null);
			sessionContext.setRollbackOnly();
		}

		// Serviço Tipo Atividade (1..n)
		for(Iterator<ServicoTipoAtividade> iter = colecaoServicoTipoAtividades.iterator(); iter.hasNext();){

			ServicoTipoAtividade sta = iter.next();
			sta.setServicoTipo(servicoTipo);
			sta.setAtividade(sta.getAtividade());
			sta.setUltimaAlteracao(dataUltimaAlteracao);
			sta.setComp_id(new ServicoTipoAtividadePK(id, sta.getAtividade().getId()));

			getControladorUtil().inserir(sta);
		}

		// Serviço Tipo Material (1..n)
		for(Iterator<ServicoTipoMaterial> iter = colecaoServicoTipoMateriais.iterator(); iter.hasNext();){

			ServicoTipoMaterial stm = iter.next();
			stm.setServicoTipo(servicoTipo);
			stm.setMaterial(stm.getMaterial());
			stm.setUltimaAlteracao(dataUltimaAlteracao);
			stm.setComp_id(new ServicoTipoMaterialPK(id, stm.getMaterial().getId()));
			getControladorUtil().inserir(stm);
		}

		// Inserir serviços associados
		if(colecaoServicosAssociados != null && !colecaoServicosAssociados.isEmpty()){
			this.validarSequencialServicosAssociados(colecaoServicosAssociados);
			this.validarDuplicidadeTipoServicoAssociado(colecaoServicosAssociados);

			for(ServicoAssociado servicoAssociado : colecaoServicosAssociados){
				servicoAssociado.setServicoTipo(servicoTipo); // obs, o servico associado ja vem
				// setado (confirmar)
				servicoAssociado.setUltimaAlteracao(dataUltimaAlteracao);
				servicoAssociado.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				servicoAssociado.setComp_id(new ServicoAssociadoPK(id, servicoAssociado.getServicoTipoAssociado().getId()));
				getControladorUtil().inserir(servicoAssociado);
			}
		}

		// Inserir serviços tipo trâmite
		if(!Util.isVazioOrNulo(colecaoServicoTipoTramite)){
			for(ServicoTipoTramite servicoTipoTramite : colecaoServicoTipoTramite){
				servicoTipoTramite.setServicoTipo(servicoTipo);
				servicoTipoTramite.setUltimaAlteracao(dataUltimaAlteracao);
				servicoTipoTramite.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

				this.getControladorUtil().inserir(servicoTipoTramite);
			}
		}

		return id;

	}

	/**
	 * [UC0410] - Atualizar Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 * @author Virgínia Melo
	 * @date 10/12/2008, 13/05/2009
	 *       Atualizar serviços associados.
	 * @author Saulo Lima
	 * @date 31/07/2009
	 *       Alteração ao inserir os 'ServicoTipoAtividades'
	 */
	public Integer atualizarServicoTipo(ServicoTipo servicoTipo) throws ControladorException{

		// [FS0001] - Verificar existência da Descrição
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.DESCRICAO, servicoTipo.getDescricao()));
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		ServicoTipo st = null;

		try{
			st = pesquisar(filtroServicoTipo, ServicoTipo.class, "");
		}catch(ControladorException e){
		}

		/*
		 * if (st != null) { throw new ControladorException(
		 * "atencao.servico_tipo.descricao_ja_existente"); }
		 */

		// [FS0002] - Verificar existência da Descrição abreviada
		if(servicoTipo.getDescricaoAbreviada() != null){

			filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.DESCRICAO_ABREVIADA, servicoTipo
							.getDescricaoAbreviada()));

			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			try{
				st = pesquisar(filtroServicoTipo, ServicoTipo.class, "");
			}catch(ControladorException e){
			}

			/*
			 * if (st != null) { throw new ControladorException(
			 * "atencao.servico_tipo.descricao_abreviada_ja_existente"); }
			 */

		}

		// [FS0003] - Validar Tipo de Débito
		if(servicoTipo.getDebitoTipo() != null){
			getControladorFaturamento().pesquisarDebitoTipo(servicoTipo.getDebitoTipo().getId());
		}

		// [FS0004] - Validar Perfil do Serviço
		if(servicoTipo.getServicoPerfilTipo() != null){
			pesquisarServicoPerfilTipo(servicoTipo.getServicoPerfilTipo().getId());
		}

		// [FS0006] - Validar Ordem de Execução

		// [FS0009] - Validar Atividade

		// [FS0010] - Validar Material

		Date dataUltimaAlteracao = new Date();

		servicoTipo.setUltimaAlteracao(dataUltimaAlteracao);
		servicoTipo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		Collection<ServicoTipoAtividade> colecaoServicoTipoAtividades = servicoTipo.getServicoTipoAtividades();
		Collection<ServicoTipoMaterial> colecaoServicoTipoMateriais = servicoTipo.getServicoTipoMateriais();
		Collection<ServicoAssociado> colecaoServicosAssociados = servicoTipo.getServicosAssociados();
		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = servicoTipo.getServicosTipoTramite();

		Integer id = servicoTipo.getId();

		// inserir tipo servico referencia
		try{
			if(servicoTipo.getServicoTipoReferencia() != null && servicoTipo.getServicoTipoReferencia().getId() == null){
				ServicoTipoReferencia servicoTipoReferencia = servicoTipo.getServicoTipoReferencia();
				servicoTipoReferencia.setUltimaAlteracao(new Date());
				Integer referencia = (Integer) getControladorUtil().inserir(servicoTipoReferencia);

				servicoTipo.getServicoTipoReferencia().setId(referencia);

			}else if(servicoTipo.getServicoTipoReferencia() != null && servicoTipo.getServicoTipoReferencia().getId() != null){

				// [FS0005] - Validar Tipo de Serviço Referência
				if(servicoTipo.getServicoTipoReferencia() != null){
					servicoTipo.getServicoTipoReferencia().setUltimaAlteracao(new Date());
					pesquisarServicoTipoReferencia(servicoTipo.getServicoTipoReferencia().getId());
				}
			}

			// [FS0008] - Verificar Sucesso da Operação
			atualizarServicoTipoSemColecoes(servicoTipo);

		}catch(ControladorException e){
			servicoTipo.getServicoTipoReferencia().setId(null);
			sessionContext.setRollbackOnly();
		}

		FiltroServicoTipoAtividade filtroServicoTipoAtividade = new FiltroServicoTipoAtividade();
		filtroServicoTipoAtividade
						.adicionarParametro(new ParametroSimples(FiltroServicoTipoAtividade.SERVICO_TIPO_ID, servicoTipo.getId()));
		filtroServicoTipoAtividade.adicionarCaminhoParaCarregamentoEntidade("atividade");

		FiltroServicoTipoMaterial filtroServicoTipoMaterial = new FiltroServicoTipoMaterial();
		filtroServicoTipoMaterial.adicionarParametro(new ParametroSimples(FiltroServicoTipoMaterial.ID_SERVICO_TIPO, servicoTipo.getId()));
		filtroServicoTipoMaterial.adicionarCaminhoParaCarregamentoEntidade("material");

		FiltroServicoAssociado filtroServicoAssociado = new FiltroServicoAssociado();
		filtroServicoAssociado.adicionarParametro(new ParametroSimples(FiltroServicoAssociado.ID_SERVICO_TIPO, servicoTipo.getId()));

		FiltroServicoTipoTramite filtroServicoTipoTramite = new FiltroServicoTipoTramite();
		filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.SERVICO_TIPO_ID, servicoTipo.getId()));

		Collection<ServicoTipoMaterial> colecaoServicoTipoMaterialBASE = this.getControladorUtil().pesquisar(filtroServicoTipoMaterial,
						ServicoTipoMaterial.class.getName());
		Collection<ServicoTipoAtividade> colecaoServicoTipoAtividadeBASE = this.getControladorUtil().pesquisar(filtroServicoTipoAtividade,
						ServicoTipoAtividade.class.getName());
		Collection<ServicoAssociado> colecaoServicosAssociadosBASE = this.getControladorUtil().pesquisar(filtroServicoAssociado,
						ServicoAssociado.class.getName());
		Collection<ServicoTipoTramite> colecaoServicoTipoTramiteBASE = this.getControladorUtil().pesquisar(filtroServicoTipoTramite,
						ServicoTipoTramite.class.getName());

		// Remove ServicoTipoAtividade
		for(Iterator iter = colecaoServicoTipoAtividadeBASE.iterator(); iter.hasNext();){
			ServicoTipoAtividade sta = (ServicoTipoAtividade) iter.next();
			this.getControladorUtil().remover(sta);
		}

		// Remove ServicoTipoMaterial
		for(Iterator iter = colecaoServicoTipoMaterialBASE.iterator(); iter.hasNext();){
			ServicoTipoMaterial sta = (ServicoTipoMaterial) iter.next();
			this.getControladorUtil().remover(sta);
		}

		// Remove ServicoAssociado
		for(ServicoAssociado servicoAssociadoBase : colecaoServicosAssociadosBASE){
			this.getControladorUtil().remover(servicoAssociadoBase);
		}

		// Remove Serviço Tipo Trâmite
		if(!Util.isVazioOrNulo(colecaoServicoTipoTramiteBASE)){
			for(ServicoTipoTramite servicoTipoTramite : colecaoServicoTipoTramiteBASE){
				this.getControladorUtil().remover(servicoTipoTramite);
			}
		}

		// Servico Tipo Atividades
		if(colecaoServicoTipoAtividades != null && !colecaoServicoTipoAtividades.isEmpty()){
			for(Iterator<ServicoTipoAtividade> iter = colecaoServicoTipoAtividades.iterator(); iter.hasNext();){

				ServicoTipoAtividade sta = iter.next();
				sta.setServicoTipo(servicoTipo);
				sta.setAtividade(sta.getAtividade());
				sta.setUltimaAlteracao(dataUltimaAlteracao);
				sta.setComp_id(new ServicoTipoAtividadePK(id, sta.getAtividade().getId()));

				this.getControladorUtil().inserir(sta);
			}
		}else{

			FiltroAtividade filtroAtividade = new FiltroAtividade();
			filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA,
							Atividade.INDICADOR_ATIVIDADE_UNICA_SIM));
			Collection<Atividade> colecaoAtividade = this.getControladorUtil().pesquisar(filtroAtividade, Atividade.class.getName());

			if(colecaoAtividade.isEmpty()){
				throw new ControladorException("atencao.naocadastrado", null, "Atividade");
			}

			Atividade atividadeUnica = (Atividade) Util.retonarObjetoDeColecao(colecaoAtividade);
			ServicoTipoAtividade sta = new ServicoTipoAtividade();
			sta.setServicoTipo(servicoTipo);
			sta.setAtividade(atividadeUnica);
			sta.setUltimaAlteracao(dataUltimaAlteracao);
			sta.setComp_id(new ServicoTipoAtividadePK(id, atividadeUnica.getId()));
			sta.setNumeroExecucao(Short.valueOf((short) 1));

			this.getControladorUtil().inserir(sta);
		}

		// Serviço Tipo Material (1..n)
		if(colecaoServicoTipoMateriais != null){
			for(Iterator<ServicoTipoMaterial> iter = colecaoServicoTipoMateriais.iterator(); iter.hasNext();){
				ServicoTipoMaterial stm = iter.next();
				stm.setServicoTipo(servicoTipo);
				stm.setMaterial(stm.getMaterial());
				stm.setUltimaAlteracao(dataUltimaAlteracao);
				stm.setComp_id(new ServicoTipoMaterialPK(id, stm.getMaterial().getId()));
				this.getControladorUtil().inserir(stm);
			}
		}

		// Serviços Associados
		if(colecaoServicosAssociados != null && !colecaoServicosAssociados.isEmpty()){
			this.validarSequencialServicosAssociados(colecaoServicosAssociados);
			this.validarDuplicidadeTipoServicoAssociado(colecaoServicosAssociados);
			this.validarAssociacaoEleMesmo(servicoTipo.getId(), colecaoServicosAssociados);

			for(ServicoAssociado servicoAssociado : colecaoServicosAssociados){
				servicoAssociado.setServicoTipo(servicoTipo); // obs, o servico associado ja vem
				// setado (confirmar)
				servicoAssociado.setUltimaAlteracao(dataUltimaAlteracao);
				servicoAssociado.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				servicoAssociado.setComp_id(new ServicoAssociadoPK(id, servicoAssociado.getServicoTipoAssociado().getId()));
				this.getControladorUtil().inserir(servicoAssociado);
			}
		}

		// Inserir Serviço Tipo Trâmite
		if(!Util.isVazioOrNulo(colecaoServicoTipoTramite)){
			for(ServicoTipoTramite servicoTipoTramite : colecaoServicoTipoTramite){
				servicoTipoTramite.setServicoTipo(servicoTipo);
				servicoTipoTramite.setUltimaAlteracao(dataUltimaAlteracao);
				servicoTipoTramite.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

				this.getControladorUtil().inserir(servicoTipoTramite);
			}
		}

		return id;

	}

	private Integer inserirServicoTipoSemColecoes(ServicoTipo servicoTipo) throws ControladorException{

		servicoTipo.setServicoTipoAtividades(null);
		servicoTipo.setServicoTipoMateriais(null);
		servicoTipo.setServicosAssociados(null);
		servicoTipo.setServicosTipoTramite(null);

		return (Integer) getControladorUtil().inserir(servicoTipo);
	}

	private void atualizarServicoTipoSemColecoes(ServicoTipo servicoTipo) throws ControladorException{

		servicoTipo.setServicoTipoAtividades(null);
		servicoTipo.setServicoTipoMateriais(null);
		servicoTipo.setServicosAssociados(null);
		servicoTipo.setServicosTipoTramite(null);

		getControladorUtil().atualizar(servicoTipo);
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoSubgrupo pesquisarServicoTipoSubgrupo(Integer idServicoTipoSubgrupo) throws ControladorException{

		FiltroServicoTipoSubgrupo filtro = new FiltroServicoTipoSubgrupo();
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.ID, idServicoTipoSubgrupo));
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		return pesquisar(filtro, ServicoTipoSubgrupo.class, "Serviço Tipo Subgrupo");
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ControladorException{

		FiltroServicoTipoPrioridade filtro = new FiltroServicoTipoPrioridade();
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipoPrioridade.ID, idServicoTipoPrioridade));
		filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipoPrioridade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		return pesquisar(filtro, ServicoTipoPrioridade.class, "Serviço Tipo Prioridade");
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarAtividade(Collection colecaoServicoTipoAtividade, Integer idAtividade) throws ControladorException{

		if(colecaoServicoTipoAtividade != null && !colecaoServicoTipoAtividade.isEmpty()){
			for(Iterator iter = colecaoServicoTipoAtividade.iterator(); iter.hasNext();){
				ServicoTipoAtividade element = (ServicoTipoAtividade) iter.next();
				if(idAtividade.equals(element.getAtividade().getId())){
					throw new ControladorException("atencao.atividade.ja_existente");
				}
			}
		}
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public void validarAdicionarMaterial(Collection colecaoServicoTipoMaterial, Integer idMaterial) throws ControladorException{

		if(colecaoServicoTipoMaterial != null && !colecaoServicoTipoMaterial.isEmpty()){
			for(Iterator iter = colecaoServicoTipoMaterial.iterator(); iter.hasNext();){
				ServicoTipoMaterial element = (ServicoTipoMaterial) iter.next();
				if(idMaterial.equals(element.getMaterial().getId())){
					throw new ControladorException("atencao.material.ja_existente");
				}
			}
		}
	}

	/**
	 * [UC0410] - Inserir Tipo de Serviço - Validar Serviço Associado
	 * 
	 * @author Virgínia Melo
	 * @date 14/05/2009
	 */
	public void validarAdicionarServicoAssociado(Collection colecaoServicoAssociado, Integer idServico) throws ControladorException{

		if(colecaoServicoAssociado != null && !colecaoServicoAssociado.isEmpty()){
			for(Iterator<ServicoAssociado> iter = colecaoServicoAssociado.iterator(); iter.hasNext();){
				ServicoAssociado element = iter.next();
				if(idServico.equals(element.getServicoTipoAssociado().getId())){
					throw new ControladorException("atencao.tipo_servico.ja_existente");
				}
			}
		}
	}

	/**
	 * Verifica se o sequencial da geração é válido (Ex.: 1, 2, 3 e 4)
	 * [UC0410] - Inserir Tipo de Serviço - Validar Serviço Associado
	 * 
	 * @author Saulo Lima
	 * @date 16/05/2009
	 */
	public void validarSequencialServicosAssociados(Collection<ServicoAssociado> colecaoServicoAssociado) throws ControladorException{

		int quantidadeAssociados = colecaoServicoAssociado.size();
		Map<Integer, ServicoAssociado> servicosAssociadosMap = new HashMap<Integer, ServicoAssociado>();

		if(colecaoServicoAssociado != null && !colecaoServicoAssociado.isEmpty()){
			try{
				for(Iterator<ServicoAssociado> iter = colecaoServicoAssociado.iterator(); iter.hasNext();){
					ServicoAssociado element = iter.next();
					servicosAssociadosMap.put(element.getSequencial(), element);
				}
			}catch(Exception ex){
				throw new ControladorException("atencao.ordem_geracao_servico.fora_sequencia", null);
			}
		}

		for(int i = 1; i <= quantidadeAssociados; i++){
			if(!servicosAssociadosMap.containsKey(Integer.valueOf(i))){
				throw new ControladorException("atencao.ordem_geracao_servico.fora_sequencia", null);
			}
		}
	}

	/**
	 * Verifica se existe Tipo de Serviço duplicado na coleção de associados.
	 * [UC0410] - Inserir Tipo de Serviço - Validar Serviço Associado
	 * 
	 * @author Saulo Lima
	 * @date 16/05/2009
	 */
	public void validarDuplicidadeTipoServicoAssociado(Collection<ServicoAssociado> colecaoServicoAssociado) throws ControladorException{

		Map<Integer, ServicoAssociado> servicosAssociadosMap = new HashMap<Integer, ServicoAssociado>();

		if(colecaoServicoAssociado != null && !colecaoServicoAssociado.isEmpty()){
			for(Iterator<ServicoAssociado> iter = colecaoServicoAssociado.iterator(); iter.hasNext();){
				ServicoAssociado element = iter.next();
				if(servicosAssociadosMap.containsKey(element.getServicoTipoAssociado().getId())){
					throw new ControladorException("atencao.tipo_servico.duplicado", null);
				}
				servicosAssociadosMap.put(element.getServicoTipoAssociado().getId(), element);
			}
		}
	}

	/**
	 * Verifica se o Tipo de Serviço está associado a ele mesmo;
	 * [UC0410] - Inserir Tipo de Serviço - Validar Serviço Associado
	 * 
	 * @author Saulo Lima
	 * @date 15/06/2009
	 */
	public void validarAssociacaoEleMesmo(Integer idServicoTipo, Collection<ServicoAssociado> colecaoServicoAssociado)
					throws ControladorException{

		if(colecaoServicoAssociado != null && !colecaoServicoAssociado.isEmpty()){
			for(Iterator<ServicoAssociado> iter = colecaoServicoAssociado.iterator(); iter.hasNext();){
				ServicoAssociado element = iter.next();
				if(idServicoTipo.equals(element.getServicoTipoAssociado().getId())){
					throw new ControladorException("atencao.tipo_servico.ele_mesmo", null);
				}
			}
		}
	}

	/**
	 * [UC0436] Inserir Tipo de Serviço de Referência.
	 * Permite a inclusão de um tipo de Serviço de Referência.
	 * [FS0001] Verificar existencia da Descrição [FS0002]- Verificar existência da Descrição
	 * abreviada [FS0003] Validar indicador de existencia x
	 * Situação da Os de referencia
	 * 
	 * @author Rômulo Aurélio.
	 * @date 05/08/2006
	 * @param servicoTipoReferencia
	 * @throws ControladorException
	 */

	public Integer inserirTipoServicoReferencia(ServicoTipoReferencia servicoTipoReferencia, Usuario usuarioLogado)
					throws ControladorException{

		this.validarTipoServicoReferenciaParaInsercao(servicoTipoReferencia);

		// ------------------- Final [FS0003]--------------------------

		servicoTipoReferencia.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSação----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SERVICO_TIPO_REFERENCIA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SERVICO_TIPO_REFERENCIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		servicoTipoReferencia.setOperacaoEfetuada(operacaoEfetuada);
		servicoTipoReferencia.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoTipoReferencia);

		// --------FIM---- REGISTRAR TRANSação----------------------------
		Integer idTipoServicoReferencia = (Integer) getControladorUtil().inserir(servicoTipoReferencia);

		return idTipoServicoReferencia;

	}

	/**
	 * Este método valida os dados que são necessarios para a inserção do
	 * Serviço tipo referencia.
	 * 
	 * @author Flávio Leonardo
	 * @date 31/10/2006
	 * @param servicoTipoReferencia
	 * @return
	 * @throws ControladorException
	 */
	public void validarTipoServicoReferenciaParaInsercao(ServicoTipoReferencia servicoTipoReferencia) throws ControladorException{

		// [FS0001] Verificar existencia da Descrição

		FiltroServicoTipoReferencia filtroServicoTipoReferencia = new FiltroServicoTipoReferencia();

		filtroServicoTipoReferencia.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.DESCRICAO, servicoTipoReferencia
						.getDescricao()));

		Collection colecaoServicoTipoReferencia = getControladorUtil().pesquisar(filtroServicoTipoReferencia,
						ServicoTipoReferencia.class.getName());

		if(colecaoServicoTipoReferencia != null && !colecaoServicoTipoReferencia.isEmpty()){
			throw new ControladorException("atencao.descricao_ja_existente_servico_referencia", null, ""
							+ servicoTipoReferencia.getDescricao() + "");
		}

		if(servicoTipoReferencia.getDescricaoAbreviada() != null && !servicoTipoReferencia.getDescricaoAbreviada().equals("")){

			filtroServicoTipoReferencia.limparListaParametros();

			// [FS0002]- Verificar existência da Descrição abreviada
			filtroServicoTipoReferencia.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.DESCRICAO_ABREVIADA,
							servicoTipoReferencia.getDescricaoAbreviada()));

			colecaoServicoTipoReferencia = getControladorUtil().pesquisar(filtroServicoTipoReferencia,
							ServicoTipoReferencia.class.getName());

			if(colecaoServicoTipoReferencia != null && !colecaoServicoTipoReferencia.isEmpty()){
				throw new ControladorException("atencao.atencao.descricao_abreviada_ja_existente_servico_referencia", null, ""
								+ servicoTipoReferencia.getDescricaoAbreviada() + "");
			}

		}

		// [FS0003]- Validar indicador de existência x Situação da Os de
		// Referência

		Short indicadorExistenciaOS = servicoTipoReferencia.getIndicadorExistenciaOsReferencia();

		if(indicadorExistenciaOS.equals(ConstantesSistema.INDICADOR_USO_ATIVO)){

			if(!(servicoTipoReferencia.getSituacaoOsReferenciaAntes().equals(new Integer("1")))
							&& (servicoTipoReferencia.getSituacaoOsReferenciaAntes().intValue() != 2)){
				throw new ControladorException("atencao.indicador_os_incompativel_os_antes", null);

			}

			if((servicoTipoReferencia.getSituacaoOsReferenciaApos().intValue() != 2)
							&& (servicoTipoReferencia.getSituacaoOsReferenciaApos().intValue() != 4)){
				throw new ControladorException("atencao.indicador_ordem_servico_incompativel", null);

			}

		}

		if(indicadorExistenciaOS.equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
			if(servicoTipoReferencia.getSituacaoOsReferenciaAntes() != null){
				throw new ControladorException("atencao.indicador_os_nao_permitido", null);

			}

			if(servicoTipoReferencia.getSituacaoOsReferenciaApos() != null){
				throw new ControladorException("atencao.indicador_os_nao_permitido", null);

			}

		}
	}

	/**
	 * [UC0449] Inserir Prioridade do Tipo de Serviço
	 * Permite a inclusão de uma prioridade do tipo de Serviço.
	 * [FS0001] Verificar existencia da Descrição [FS0003]- Verificar existência
	 * da Descrição abreviada [FS0002] Validar quantidade de horas início e
	 * quantidade de horas fim
	 * 
	 * @author Rômulo Aurélio.
	 * @date 11/08/2006
	 * @param servicoTipoPrioridade
	 * @throws ControladorException
	 */

	public Integer inserirPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Usuario usuarioLogado)
					throws ControladorException{

		// [FS0001] Verificar existencia da Descrição

		FiltroServicoTipoPrioridade filtroServicoTipoPrioridade = new FiltroServicoTipoPrioridade();

		filtroServicoTipoPrioridade.adicionarParametro(new ParametroSimples(FiltroServicoTipoPrioridade.DESCRICAO, servicoTipoPrioridade
						.getDescricao()));

		Collection colecaoServicoTipoPrioridade = getControladorUtil().pesquisar(filtroServicoTipoPrioridade,
						ServicoTipoPrioridade.class.getName());

		// ajeitar no apliccation a msg

		if(colecaoServicoTipoPrioridade != null && !colecaoServicoTipoPrioridade.isEmpty()){
			throw new ControladorException("atencao.descricao_ja_existente_servico_referencia", null, ""
							+ servicoTipoPrioridade.getDescricao() + "");
		}

		if(servicoTipoPrioridade.getDescricaoAbreviada() != null && !servicoTipoPrioridade.getDescricaoAbreviada().equals("")){

			filtroServicoTipoPrioridade.limparListaParametros();

			// [FS0002]- Verificar existência da Descrição abreviada
			filtroServicoTipoPrioridade.adicionarParametro(new ParametroSimples(FiltroServicoTipoPrioridade.DESCRICAO_ABREVIADA,
							servicoTipoPrioridade.getDescricaoAbreviada()));

			colecaoServicoTipoPrioridade = getControladorUtil().pesquisar(filtroServicoTipoPrioridade,
							ServicoTipoPrioridade.class.getName());

			if(colecaoServicoTipoPrioridade != null && !colecaoServicoTipoPrioridade.isEmpty()){
				throw new ControladorException("atencao.descricao_abreviada_ja_existente_servico_referencia", null, ""
								+ servicoTipoPrioridade.getDescricaoAbreviada() + "");
			}

		}

		// [FS0002] Validar quantidade de horas início e quantidade de horas fim

		if(servicoTipoPrioridade.getPrazoExecucaoInicio() != null){

			if((servicoTipoPrioridade.getPrazoExecucaoInicio() < 0) || (servicoTipoPrioridade.getPrazoExecucaoInicio() == 0)){
				throw new ControladorException("atencao.quantidade_hora_invalida", null, "início de ");
			}

		}

		if(servicoTipoPrioridade.getPrazoExecucaoFim() != null){

			if((servicoTipoPrioridade.getPrazoExecucaoFim() < 0) || (servicoTipoPrioridade.getPrazoExecucaoFim() == 0)){
				throw new ControladorException("atencao.quantidade_hora_invalida", null, "finalizar a ");
			}

		}

		servicoTipoPrioridade.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		servicoTipoPrioridade.setTmCadastramento(new Date());

		servicoTipoPrioridade.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSação----------------------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SERVICO_TIPO_PRIORIDADE_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SERVICO_TIPO_PRIORIDADE_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		servicoTipoPrioridade.setOperacaoEfetuada(operacaoEfetuada);
		servicoTipoPrioridade.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoTipoPrioridade);

		// --------FIM---- REGISTRAR TRANSação----------------------------
		Integer idTPrioridadeipoServico = (Integer) getControladorUtil().inserir(servicoTipoPrioridade);

		return idTPrioridadeipoServico;

	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * atualização da instalação de hidrômetro do Imóvel no momento da exibição.
	 * 
	 * @author Rafael Pinto
	 * @date 20/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirAtualizarInstalacaoHidrometro(OrdemServico ordemServico, boolean menu) throws ControladorException{

		// [FS0001] Validar Ordem de Servico
		this.getControladorOrdemServico().validaOrdemServicoAtualizacao(ordemServico, menu);

		if(ordemServico.getRegistroAtendimento() == null || ordemServico.getRegistroAtendimento().getImovel() == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ordemServico.getId() + "");
		}
		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

		if(imovel == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ordemServico.getRegistroAtendimento().getId()
							.toString());
		}

		Integer idServicoTipo = ordemServico.getServicoTipo().getId();

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(idServicoTipo,
						Operacao.OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR);

		if(idOperacao == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.servico_associado_atualizar_instalacao_hidrometro_invalida");
		}

		// [FS0002] Verificar Situação do Imovel.

		if(ConstantesSistema.INDICADOR_IMOVEL_EXCLUIDO == imovel.getIndicadorExclusao()){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		// [FS0004] Validar Data do Encerramento da Ordem de Servico
		if(ServicoTipo.INSTALACAO_HIDROMETRO.contains(idServicoTipo) || ServicoTipo.SUBSTITUICAO_HIDROMETRO.contains(idServicoTipo)){

			Date dataExecucao = ordemServico.getDataExecucao();
			if(dataExecucao != null){

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dataExecucao);

				int qtdDias = Util.obterQuantidadeDiasEntreDuasDatas(calendar.getTime(), new Date());
				FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

				Collection colecao = getControladorUtil().pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());

				if(colecao != null && !colecao.isEmpty()){

					SistemaParametro sistemaParametro = (SistemaParametro) Util.retonarObjetoDeColecao(colecao);
					int qtdMaximo = sistemaParametro.getDiasMaximoAlterarOS().intValue();
					if(qtdDias > qtdMaximo){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.instalacao_hidrometro_data_encerramento_os_invalida", null,
										new String[] {ordemServico.getId().toString(), qtdMaximo + ""});
					}
				}
			}
		}
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @return valor do Débito
	 * @throws ControladorException
	 */
	public BigDecimal obterValorDebito(Integer servicoTipoId, Integer imovelId, Short tipoMedicao) throws ControladorException{

		BigDecimal valorDebito = null;
		try{
			ObterValorDebitoHelper obterValorDebitoHelper = new ObterValorDebitoHelper();
			ServicoTipo servicoTipo = new ServicoTipo();
			servicoTipo.setId(servicoTipoId);
			obterValorDebitoHelper.setServicoTipo(servicoTipo);
			ImovelPerfil imovelPerfil = this.getControladorImovel().obterImovelPerfil(imovelId);
			obterValorDebitoHelper.setImovelPerfil(imovelPerfil);

			if(tipoMedicao.intValue() == MedicaoTipo.LIGACAO_AGUA.intValue()){
				if(repositorioAtendimentoPublico.verificarExistenciaHidrometroEmLigacaoAgua(imovelId)){
					obterValorDebitoHelper.setSituacaoMedicao(ServicoCobrancaValor.INDICADOR_MEDICAO_SIM);
					HidrometroCapacidade hidrometroCapacidade = repositorioAtendimentoPublico
									.obterHidrometroCapacidadeEmLigacaoAgua(imovelId);
					obterValorDebitoHelper.setHidrometroCapacidade(hidrometroCapacidade);
				}else{
					obterValorDebitoHelper.setSituacaoMedicao(ServicoCobrancaValor.INDICADOR_MEDICAO_NAO);
				}
			}else if(tipoMedicao.intValue() == MedicaoTipo.POCO.intValue()){
				if(repositorioAtendimentoPublico.verificarExistenciaHidrometroEmImovel(imovelId)){
					obterValorDebitoHelper.setSituacaoMedicao(ServicoCobrancaValor.INDICADOR_MEDICAO_SIM);
					HidrometroCapacidade hidrometroCapacidade = repositorioAtendimentoPublico.obterHidrometroCapacidadeEmImovel(imovelId);
					obterValorDebitoHelper.setHidrometroCapacidade(hidrometroCapacidade);
				}else{
					obterValorDebitoHelper.setSituacaoMedicao(ServicoCobrancaValor.INDICADOR_MEDICAO_NAO);
				}
			}
			valorDebito = repositorioAtendimentoPublico.obterValorDebito(obterValorDebitoHelper);

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, servicoTipoId));
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.SIM));

			Collection colecaoServicoTipo = (Collection) getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
			ServicoTipo servicoTipoNaBase = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			if(Util.isNaoNuloBrancoZero(imovelId) && servicoTipoNaBase != null){
				BigDecimal valorServicoLocalidade = this.verificarServicoTipoValorLocalidade(imovelId, servicoTipoNaBase.getId());

				if(valorServicoLocalidade != null){
					valorDebito = valorServicoLocalidade;
				}
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return valorDebito;
	}

	/**
	 * método que retorna o número do hidrômetro da Ligação de Água
	 * 
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.pesquisarNumeroHidrometroLigacaoAgua(idLigacaoAgua);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * método que retorna o tipo da Ligação de Água e a data do corte da Ligação
	 * de Água
	 * 
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.pesquisarDadosLigacaoAgua(idLigacaoAgua);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Consulta os dados das ordens de Serviço para a geração do relatório
	 * 
	 * @author Rafael Corrêa
	 * @created 07/10/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ControladorException{

		try{
			Collection colecaoDadosOrdemServicoProgramacao = repositorioAtendimentoPublico.pesquisarOrdemServicoProgramacaoRelatorio(
							idEquipe, dataRoteiro);

			Collection colecaoOrdensServicoProgramacao = new ArrayList();
			Iterator colecaoDadosOrdemServicoProgramacaoIterator = colecaoDadosOrdemServicoProgramacao.iterator();
			while(colecaoDadosOrdemServicoProgramacaoIterator.hasNext()){

				OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();

				// Obtém os dados do crédito realizado
				Object[] dadosArray = (Object[]) colecaoDadosOrdemServicoProgramacaoIterator.next();

				// Sequencial
				if(dadosArray[0] != null){
					ordemServicoProgramacao.setNnSequencialProgramacao((Short) dadosArray[0]);
				}

				RegistroAtendimento registroAtendimento = new RegistroAtendimento();

				// número do RA
				if(dadosArray[1] != null){
					registroAtendimento.setId((Integer) dadosArray[1]);
				}

				OrdemServico ordemServico = new OrdemServico();

				// número da OS
				if(dadosArray[2] != null){
					ordemServico.setId((Integer) dadosArray[2]);
				}

				// Tipo de Serviço
				if(dadosArray[3] != null){
					ServicoTipo servicoTipo = new ServicoTipo();
					servicoTipo.setId((Integer) dadosArray[3]);
					ordemServico.setServicoTipo(servicoTipo);
				}

				// Observação
				if(dadosArray[4] != null){
					ordemServico.setObservacao((String) dadosArray[4]);
				}else{
					ordemServico.setObservacao("");
				}

				ordemServico.setRegistroAtendimento(registroAtendimento);
				ordemServicoProgramacao.setOrdemServico(ordemServico);

				colecaoOrdensServicoProgramacao.add(ordemServicoProgramacao);
			}

			return colecaoOrdensServicoProgramacao;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.verificarExistenciaHidrometroEmLigacaoAgua(imovelId);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmImovel(Integer imovelId) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.verificarExistenciaHidrometroEmImovel(imovelId);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0387] Manter Tipo Perfil Serviço [SB0002] Remover Tipo Perfil Serviço
	 * 
	 * @author Kassia Albuquerque
	 * @date 08/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void removerServicoTipoPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSação ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_TIPO_PERFIL_SERVICO_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSação ----------------

		this.getControladorUtil().remover(ids, ServicoPerfilTipo.class.getName(), operacaoEfetuada, colecaoUsuarios);

	}

	/**
	 * [UC0387] Manter Tipo Perfil Serviço [SB0001] Atualizar Tipo Perfil
	 * Serviço
	 * 
	 * @author Kassia Albuquerque
	 * @date 01/11/2006
	 * @pparam servicoPerfilTipo
	 * @throws ControladorException
	 */
	public void atualizarServicoTipoPerfil(ServicoPerfilTipo servicoPerfilTipo, Usuario usuarioLogado) throws ControladorException{

		// [UC0107] - Registrar Transação
		// ------------ REGISTRAR TRANSação----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_TIPO_PERFIL_SERVICO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_TIPO_PERFIL_SERVICO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		servicoPerfilTipo.setOperacaoEfetuada(operacaoEfetuada);
		servicoPerfilTipo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoPerfilTipo);
		// ------------ REGISTRAR TRANSação----------------------------

		// [FS0003] - Atualização realizada por outro usuário
		FiltroServicoPerfilTipo filtroServicoPerfilTipo = new FiltroServicoPerfilTipo();
		// Seta o filtro para buscar o servicoPerfilTipo na base
		filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.ID, servicoPerfilTipo.getId()));

		// Procura servicoPerfilTipo na base
		Collection servicoPerfilTipoAtualizadas = getControladorUtil()
						.pesquisar(filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());

		ServicoPerfilTipo servicoPerfilTipoNaBase = (ServicoPerfilTipo) Util.retonarObjetoDeColecao(servicoPerfilTipoAtualizadas);

		if(servicoPerfilTipoNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o servicoPerfilTipo já foi atualizado por outro usuário
		// durante esta atualização

		if(servicoPerfilTipoNaBase.getUltimaAlteracao().after(servicoPerfilTipo.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		servicoPerfilTipo.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(servicoPerfilTipo);

	}

	/**
	 * Faz o controle de concorrencia de ligacao Agua
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarEspecificacaoImovelSituacaoControleConcorrencia(EspecificacaoImovelSituacao especificacaoImovelSituacao)
					throws ControladorException{

		FiltroEspecificacaoImovelSituacao filtro = new FiltroEspecificacaoImovelSituacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroEspecificacaoImovelSituacao.ID, especificacaoImovelSituacao.getId()));

		Collection colecao = getControladorUtil().pesquisar(filtro, EspecificacaoImovelSituacao.class.getName());

		if(colecao == null || colecao.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		EspecificacaoImovelSituacao especificacaoImovelSituacaoAtual = (EspecificacaoImovelSituacao) Util.retonarObjetoDeColecao(colecao);

		if(especificacaoImovelSituacaoAtual.getUltimaAlteracao().after(especificacaoImovelSituacao.getUltimaAlteracao())){

			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * [UC0404] Manter Especificação da Situação do Imovel
	 * Este caso de uso remove a especificação e os critério
	 * [SB0002] Remover Especificação da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovel(String[] idsEspecificacaoSituacaoImovel, Usuario usuario, Date ultimaAlteracao)
					throws ControladorException{

		try{

			for(int i = 0; i < idsEspecificacaoSituacaoImovel.length; i++){
				String id = idsEspecificacaoSituacaoImovel[i];

				EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();

				especificacaoImovelSituacao.setId(new Integer(id));
				especificacaoImovelSituacao.setUltimaAlteracao(ultimaAlteracao);

				this.verificarEspecificacaoImovelSituacaoControleConcorrencia(especificacaoImovelSituacao);
			}

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_ESPECIFICACAO_SITUACAO_IMOVEL_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			repositorioAtendimentoPublico.removerEspecificacaoSituacaoImovelCriterio(idsEspecificacaoSituacaoImovel);

			UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario,
							UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
			colecaoUsuarios.add(usuarioAcaoUsuarioHelper);

			getControladorUtil().remover(idsEspecificacaoSituacaoImovel, EspecificacaoImovelSituacao.class.getName(), operacaoEfetuada,
							colecaoUsuarios);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0383] Manter Material [SB0003] Remover Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 16/11/2006
	 * @pparam material
	 * @throws ControladorException
	 */
	public void removerMaterial(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSação ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MATERIAL_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSação ----------------

		this.getControladorUtil().remover(ids, Material.class.getName(), operacaoEfetuada, colecaoUsuarios);

	}

	/**
	 * [UC0383] Manter Material [SB0001] Atualizar Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/11/2006
	 * @pparam material
	 * @throws ControladorException
	 */
	public void atualizarMaterial(Material material, Usuario usuarioLogado) throws ControladorException{

		getControladorOrdemServico().verificarExistenciaDescricaoMaterial(material);

		// ------------ INICIO REGISTRAR TRANSação----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_MATERIAL_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_MATERIAL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		material.setOperacaoEfetuada(operacaoEfetuada);
		material.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(material);
		// ------------ FIM REGISTRAR TRANSação----------------------------

		// [FS0005] - Atualização realizada por outro usuário
		FiltroMaterial filtroMaterial = new FiltroMaterial();
		// Seta o filtro para buscar o material na base
		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.ID, material.getId()));

		// Procura material na base
		Collection materialAtualizadas = getControladorUtil().pesquisar(filtroMaterial, Material.class.getName());

		Material materialNaBase = (Material) Util.retonarObjetoDeColecao(materialAtualizadas);

		if(materialNaBase == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		// Verificar se o material já foi atualizado por outro usuário
		// durante esta atualização

		if(materialNaBase.getUltimaAlteracao().after(material.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		material.setUltimaAlteracao(new Date());

		// Atualiza o objeto na base
		getControladorUtil().atualizar(material);
	}

	/**
	 * [UC0498] Efetuar Ligação de Água com Instalação de hidrômetro.
	 * Permite validar o efetuar Ligação de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 27/11/2006
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_ligacao_agua_instalacao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);
		// Caso 4
		if(ordem.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" + ordem.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordem.getRegistroAtendimento().getImovel();

		// [FS0002] Validar Situação de Água do Imóvel.
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.POTENCIAL.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.FACTIVEL.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.EM_FISCALIZACAO.intValue()){

			throw new ControladorException("atencao.situacao_validar_ligacao_agua_invalida_exibir", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		// [FS0007] Verificar Situação Rede de Água na Quadra
		if((imovel.getQuadra().getIndicadorRedeAgua()).equals(Quadra.SEM_REDE)){
			throw new ControladorException("atencao.seituacao_rede_agua_quadra", null, imovel.getId() + "");
		}

		// [FS0006] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}

		// [FS0014] - Verificar existência de hidrômetro na Ligação de Água
		if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
			throw new ControladorException("atencao.hidrometro_instalado_ligacao_agua", null, "" + imovel.getId());
		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * [UC0540] Efetuar Restabelecimento da Ligação de Água com Instalação de
	 * hidrômetro.
	 * Permite validar o Efetuar Restabelecimento Ligação de Água com Instalação
	 * de hidrômetro Exibir ou pelo menu ou pela funcionalidade encerrar a
	 * Execução da ordem de serviço.
	 * [FS0008] Verificar Situação Rede de Água na Quadra. [FS0007] Verificar
	 * Situação do Imovel. [FS0002] Validar Situação de Água do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 18/04/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_DE_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_restabelecimento_ligacao_agua_instalacao_hidrometro_invalida");
		}

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);
		// Caso 4
		if(ordem.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" + ordem.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordem.getRegistroAtendimento().getImovel();

		// [FS0002] Validar Situação de Água do Imóvel.
		// Parametro adicionado a pedido da DESO.(OC0920434)

		if(ParametroAtendimentoPublico.P_TRATAR_CORTADO_A_PEDIDO_COMO_SUPRIMIDO.executar().equals(ConstantesSistema.SIM.toString())){
			
			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO_PEDIDO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_invalida_restabelecimento", null, imovel
								.getLigacaoAguaSituacao().getDescricao());
			}
		}else{

			if(LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue() != ConstantesSistema.INVALIDO_ID){

				if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
								&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()){

					throw new ControladorException("atencao.situacao_ligacao_agua_invalida_restabelecimento", null, imovel
									.getLigacaoAguaSituacao().getDescricao());
				}
			}else{

				if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
								&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC.intValue()){

					throw new ControladorException("atencao.situacao_ligacao_agua_invalida_restabelecimento", null, imovel
									.getLigacaoAguaSituacao().getDescricao());
				}

			}
		}

		// [FS0007] Verificar Situação Rede de Água na Quadra
		if((imovel.getQuadra().getIndicadorRedeAgua()).equals(Quadra.SEM_REDE)){
			throw new ControladorException("atencao.seituacao_rede_agua_quadra", null, imovel.getId() + "");
		}

		// [FS0006] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}

		// [FS0014] - Verificar existência de hidrômetro na Ligação de Água
		if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
			throw new ControladorException("atencao.hidrometro_instalado_ligacao_agua", null, "" + imovel.getId());
		}
	}

	/**
	 * [UC0294] Manter Prioridade Tipo Servico [] Atualizar Prioridade do Tipo
	 * Servico Metodo que atualiza a Prioridade do Tipo de Servico
	 * 
	 * @author Thiago Tenório
	 * @date 25/05/2006
	 * @param Prioridade
	 *            Tipo Servico
	 * @throws ControladorException
	 */

	public void atualizarPrioridadeTipoServico(ServicoTipoPrioridade servicoTipoPrioridade, Collection colecaoServicoTipoPrioridade)
					throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((servicoTipoPrioridade.getDescricao() == null || servicoTipoPrioridade.getDescricao().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (servicoTipoPrioridade.getDescricaoAbreviada() == null || servicoTipoPrioridade.getDescricaoAbreviada().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (servicoTipoPrioridade.getId() == null || servicoTipoPrioridade.getId().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (servicoTipoPrioridade.getPrazoExecucaoInicio() == null || servicoTipoPrioridade.getPrazoExecucaoInicio()
										.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (servicoTipoPrioridade.getPrazoExecucaoFim() == null || servicoTipoPrioridade.getPrazoExecucaoFim().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descrição da Prioridade foi preenchido

		if(servicoTipoPrioridade.getDescricao() == null
						|| servicoTipoPrioridade.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descrição da Prioridade");
		}

		// Verifica se o campo Abreviatura da Prioridade foi preenchido

		if(servicoTipoPrioridade.getDescricaoAbreviada() == null
						|| servicoTipoPrioridade.getDescricaoAbreviada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Abreviatura da Prioridade");
		}

		// [FS0003] - Atualização realizada por outro usuário
		FiltroServicoTipoPrioridade filtroServicoTipoPrioridade = new FiltroServicoTipoPrioridade();
		filtroServicoTipoPrioridade.adicionarParametro(new ParametroSimples(FiltroServicoTipoPrioridade.ID, servicoTipoPrioridade.getId()));

		Collection colecaoServicoTipoPrioridadeBase = getControladorUtil().pesquisar(filtroServicoTipoPrioridade,
						ServicoTipoPrioridade.class.getName());

		if(colecaoServicoTipoPrioridadeBase == null || colecaoServicoTipoPrioridadeBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ServicoTipoPrioridade servicoTipoBase = (ServicoTipoPrioridade) colecaoServicoTipoPrioridadeBase.iterator().next();

		if(servicoTipoBase.getUltimaAlteracao().after(servicoTipoBase.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		servicoTipoPrioridade.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(servicoTipoPrioridade);

	}

	/**
	 * [UC0498] Efetuar Ligação de Água com Instalação de hidrômetro.
	 * Permite efetuar Ligação de Água com Instalação de Hidrômetro ou pelo menu
	 * ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Rafael Corrêa
	 * @date 29/11/2006
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		ligacaoAgua.setId(imovel.getId());

		if(ligacaoAgua != null && imovel != null){

			// [SB0001] Gerar Ligação de Água

			// LAGU_DTIMPLANTACAO
			ligacaoAgua.setDataImplantacao(new Date());

			// LAGU_TMULTIMAALTERACAO
			Date dataCorrente = new Date();
			ligacaoAgua.setUltimaAlteracao(dataCorrente);

			// FiltroLigacaoAgua filtroLigacaoAgua = new
			// FiltroLigacaoAgua();
			// filtroLigacaoAgua.adicionarParametro(new ParametroSimples(
			// FiltroLigacaoAgua.ID, imovel.getId()));
			// Collection colecaoLigacaoAguaBase = getControladorUtil()
			// .pesquisar(filtroLigacaoAgua,
			// LigacaoAgua.class.getName());
			//
			// if (!colecaoLigacaoAguaBase.isEmpty()) {
			// sessionContext.setRollbackOnly();
			// throw new ControladorException(
			// "atencao.imovel_ja_existente", null, ""
			// + ligacaoAgua.getId());
			// }

			// regitrando operacao
			ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
			ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ligacaoAgua);

			getControladorUtil().inserir(ligacaoAgua);
		}

		Integer id = null;

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

		validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());

		hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

		id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

		try{

			// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
			// colunas da tabela LIGACAO_AGUA
			// if
			// (hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(
			// MedicaoTipo.LIGACAO_AGUA)) {
			//
			// // Caso o tipo de medição seja igual a Poço, atualiza as colunas
			// // da tabela POCO
			// } else if (hidrometroInstalacaoHistorico.getMedicaoTipo().getId()
			// .equals(MedicaoTipo.POCO)) {
			// repositorioAtendimentoPublico
			// .atualizarHidrometroIntalacaoHistoricoImovel(
			// hidrometroInstalacaoHistorico.getImovel()
			// .getId(), id);
			// }

			// [SB003]Atualizar situação de hidrômetro na tabela HIDROMETRO
			Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);

			hidrometroInstalacaoHistorico.setId(id);
			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

			ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

			if(ligacaoAgua != null && imovel != null){

				// // [SB0001] Gerar Ligação de Água
				//
				// // LAGU_DTIMPLANTACAO
				// ligacaoAgua.setDataImplantacao(new Date());
				//
				// // LAGU_TMULTIMAALTERACAO
				// Date dataCorrente = new Date();
				// ligacaoAgua.setUltimaAlteracao(dataCorrente);
				//
				// // FiltroLigacaoAgua filtroLigacaoAgua = new
				// // FiltroLigacaoAgua();
				// // filtroLigacaoAgua.adicionarParametro(new ParametroSimples(
				// // FiltroLigacaoAgua.ID, imovel.getId()));
				// // Collection colecaoLigacaoAguaBase = getControladorUtil()
				// // .pesquisar(filtroLigacaoAgua,
				// // LigacaoAgua.class.getName());
				// //
				// // if (!colecaoLigacaoAguaBase.isEmpty()) {
				// // sessionContext.setRollbackOnly();
				// // throw new ControladorException(
				// // "atencao.imovel_ja_existente", null, ""
				// // + ligacaoAgua.getId());
				// // }
				//
				// // regitrando operacao
				// ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				// ligacaoAgua.adicionarUsuario(usuario,
				// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				// registradorOperacao.registrarOperacao(ligacaoAgua);
				//
				// getControladorUtil().inserir(ligacaoAgua);

				repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
								.getLigacaoAgua().getId(), id);

				LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
				ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

				if(imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

					imovel.setUltimaAlteracao(new Date());
					// [SB0002] Atualizar Imóvel
					getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
				}
			}

			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), new Integer(qtdParcelas));
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Permite efetuar a Ligação de Água com Instalação de Hidrometro sem RA
	 * 
	 * @author Saulo Lima
	 * @since 12/02/2009
	 * @param LigacaoAgua
	 * @param HidrometroInstalacaoHistorico
	 * @throws ControladorException
	 */
	public void efetuarLigacaoAguaComInstalacaoHidrometroSemRA(LigacaoAgua ligacaoAgua,
					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico) throws ControladorException{

		try{

			if(hidrometroInstalacaoHistorico != null){

				this.getControladorUtil().inserir(ligacaoAgua);
				this.atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(ligacaoAgua.getImovel().getId(), null);
				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);
				Integer idHidrometroInstalacaoHistorico = (Integer) this.getControladorUtil().inserir(hidrometroInstalacaoHistorico);
				hidrometroInstalacaoHistorico.setId(idHidrometroInstalacaoHistorico);
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				this.getControladorUtil().atualizar(ligacaoAgua);
				this.atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(null, hidrometroInstalacaoHistorico.getHidrometro().getId());

			}else{
				this.getControladorUtil().inserir(ligacaoAgua);
				this.atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(ligacaoAgua.getImovel().getId(), null);
			}

		}catch(ControladorException e1){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e1);
		}catch(Exception e2){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e2);
		}
	}

	/**
	 * [UC0540] Efetuar Restabelecimento da Ligação de Água com Instalação de
	 * hidrômetro.
	 * Permite efetuar o Restabelecimento Ligação de Água com Instalação de
	 * Hidrômetro ou pelo menu ou pela funcionalidade encerrar a Execução da
	 * ordem de serviço.
	 * 
	 * @author Rafael Corrêa
	 * @date 19/04/2007
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper,
					Usuario usuario) throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_DE_HIDROMETRO, new UsuarioAcaoUsuarioHelper(
										usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_DE_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}else if(imovel.getIndicadorExclusao() == null || imovel.getIndicadorExclusao().intValue() == Short.valueOf("1").intValue()){
			throw new ControladorException("atencao.imovel.associado.ordem.servico.nao.esta.ativo", null, imovel.getId().toString());
		}

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		if(ligacaoAgua != null && imovel != null){

			// [SB0001] Gerar Ligação de Água

			// LAGU_TMULTIMAALTERACAO
			Date dataCorrente = new Date();
			ligacaoAgua.setUltimaAlteracao(dataCorrente);

			// regitrando operacao
			ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
			ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ligacaoAgua);

			getControladorUtil().atualizar(ligacaoAgua);
		}

		Integer id = null;

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

		validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());

		hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
		hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

		id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

		try{

			// [SB003]Atualizar situação de hidrômetro na tabela HIDROMETRO
			Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);

			hidrometroInstalacaoHistorico.setId(id);
			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

			ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

			if(ligacaoAgua != null && imovel != null){

				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					// 4. Caso o tipo de medição seja igual a “LIGAÇÃO DE ÁGUA”
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					// Caso o tipo de medição seja igual a Poço, atualiza as colunas
					// da tabela POCO
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
				ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

				if(imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

					imovel.setUltimaAlteracao(new Date());
					// [SB0002] Atualizar Imóvel
					getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
				}
			}

			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
			}

			/*
			 * [OC790655][UC0540][SB0005]: Atualizar Dados Execução no Histórico de
			 * Manutenção da Ligação do Imóvel
			 */
			if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
								HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA_COM_INSTALACAO_HIDROMETRO);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), new Integer(qtdParcelas));
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * 
	 * @author Rafael Pinto
	 * @date 22/02/2007
	 * @param servicoTipoId
	 * @param imovelId
	 * @param tipoMedicao
	 * @param idHidrometroCapacidade
	 * @return valor do Débito
	 * @throws ControladorException
	 */
	public BigDecimal obterValorDebito(Integer servicoTipoId, Integer imovelId, HidrometroCapacidade hidrometroCapacidade)
					throws ControladorException{

		BigDecimal valorDebito = null;

		try{

			ObterValorDebitoHelper obterValorDebitoHelper = new ObterValorDebitoHelper();

			ServicoTipo servicoTipo = new ServicoTipo();
			servicoTipo.setId(servicoTipoId);
			obterValorDebitoHelper.setServicoTipo(servicoTipo);

			ImovelPerfil imovelPerfil = this.getControladorImovel().obterImovelPerfil(imovelId);
			obterValorDebitoHelper.setImovelPerfil(imovelPerfil);

			obterValorDebitoHelper.setSituacaoMedicao(ServicoCobrancaValor.INDICADOR_MEDICAO_SIM);
			obterValorDebitoHelper.setHidrometroCapacidade(hidrometroCapacidade);

			valorDebito = repositorioAtendimentoPublico.obterValorDebito(obterValorDebitoHelper);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return valorDebito;
	}

	/**
	 * Este método se destina a validar a ordem de servico do [UC0555]-Alterar Situacao Ligacao
	 * 
	 * @author Romulo Aurelio
	 * @date 27/03/2006
	 * @author Virgínia Melo
	 * @date 18/02/2009
	 *       Alteração para validar OS pelo id da operação e não pelo tipo de serviço.
	 * @param OrdemServico
	 */
	public void validarOrdemServicoAlterarSituacaoLigacao(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_SITUACAO_LIGACAO_ALTERAR);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_alteracao_situacao_ligacao_invalida");
		}

		if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO){
			throw new ControladorException("atencao.ordem_servico_nao_executada", null, ordemServico.getDescricaoSituacao());
		}

		if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO
 && ordemServico.getAtendimentoMotivoEncerramento() != null
						&& ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){
			throw new ControladorException("atencao.ordem_servico_encerrada_nao_executada");
		}
	}

	/**
	 * [UC0555] Alterar Situacao da Ligacao
	 * 
	 * @author Romulo Aurelio
	 * @date 27/03/2007
	 * @author Saulo Lima
	 * @date 20/05/2009
	 *       Novo parametro 'mapServicosAutorizados'
	 * @param imovel
	 * @param indicadorTipoLigacao
	 * @param idSituacaoLigacaoAguaNova
	 * @param idSituacaoLigacaoEsgotoNova
	 * @param idOrdemServico
	 * @param usuarioLogado
	 * @param usuarioLogado
	 * @return idImovel
	 * @throws ControladorException
	 */
	public Integer alterarSituacaoLigacao(Imovel imovel, String indicadorTipoLigacao, String idSituacaoLigacaoAguaNova,
					String idSituacaoLigacaoEsgotoNova, String idOrdemServico, Usuario usuarioLogado,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados) throws ControladorException{

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_SITUACAO_LIGACAO_ALTERAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_IMOVEL_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transação

		Integer idAnteriorSituacaoLigacaoAgua = null;

		if(imovel.getLigacaoAguaSituacao() != null){
			idAnteriorSituacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getId();
		}

		Integer idAnteriorSituacaoLigacaoEsgoto = null;

		if(imovel.getLigacaoEsgotoSituacao() != null){
			idAnteriorSituacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getId();
		}

		// [INICIO] ALTERACAO NA AGUA
		if((indicadorTipoLigacao.equalsIgnoreCase("" + LigacaoTipo.LIGACAO_AGUA) || (indicadorTipoLigacao.equalsIgnoreCase("3") && !indicadorTipoLigacao
						.equalsIgnoreCase("-1")))
						&& idSituacaoLigacaoAguaNova != null){

			String parametroReligarImovelFactivel = ParametroAtendimentoPublico.P_PERMITE_RELIGAR_IMOVEL_FACTIVEL.executar();

			// [INICIO][FS0006] - Verificar existencia de hidrometro
			LigacaoAgua ligacaoAgua = new LigacaoAgua();
			FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
			filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, imovel.getId()));
			filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");
			
			Collection<LigacaoAgua> colecaoLigacaoAgua = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());
			if(!Util.isVazioOrNulo(colecaoLigacaoAgua)){
				ligacaoAgua = colecaoLigacaoAgua.iterator().next();
			}

			if(idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + LigacaoAguaSituacao.POTENCIAL) && Util.isNaoNuloBrancoZero(ligacaoAgua)
							&& ligacaoAgua.getHidrometroInstalacaoHistorico() != null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_ja_instalado_ligacao_agua", null, imovel.getId().toString());
			}

			if(idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + LigacaoAguaSituacao.FACTIVEL) && Util.isNaoNuloBrancoZero(ligacaoAgua)
							&& ligacaoAgua.getHidrometroInstalacaoHistorico() != null && parametroReligarImovelFactivel != null
							&& parametroReligarImovelFactivel.equals("" + ConstantesSistema.NAO)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_ja_instalado_ligacao_agua", null, imovel.getId().toString());
			}
			// [FIM][FS0006] - Verificar existencia de hidrometro


			// [INICIO][SB0001]- Deletar Dados da Ligacao
			if((idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + LigacaoAguaSituacao.POTENCIAL) || idSituacaoLigacaoAguaNova
							.equalsIgnoreCase("" + LigacaoAguaSituacao.FACTIVEL))
							&& parametroReligarImovelFactivel != null
							&& parametroReligarImovelFactivel.equals("" + ConstantesSistema.NAO)){

					// // Registrar Transacao
					// ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
					// ligacaoAgua.adicionarUsuario(usuarioLogado,
					// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					// registradorOperacao.registrarOperacao(ligacaoAgua);
					// getControladorTransacao().registrarTransacao(ligacaoAgua);

					// Registrar Transacao

					/*
					 * Pesquisa o grupo na base de dados e verifica se o registro não foi atualizado
					 * por outro usuário durante essa transação
					 */
					getControladorUtil().remover(ligacaoAgua);
			}
			// [FIM][SB0001]- Deletar Dados da Ligacao

			// [INICIO][SB0002]- Atualizar Situacao do Imovel
			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(new Integer(idSituacaoLigacaoAguaNova));
			imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);

			if(idSituacaoLigacaoAguaNova != null && idSituacaoLigacaoAguaNova.equals("" + LigacaoAguaSituacao.LIGADO)){
				ImovelPerfil imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId(ImovelPerfil.NORMAL);
				imovel.setImovelPerfil(imovelPerfil);
			}

			if(idSituacaoLigacaoAguaNova != null && idSituacaoLigacaoAguaNova.equals("" + LigacaoAguaSituacao.FACTIVEL)
							&& idAnteriorSituacaoLigacaoAgua != null && idAnteriorSituacaoLigacaoAgua.equals(LigacaoAguaSituacao.LIGADO)
							&& parametroReligarImovelFactivel != null && parametroReligarImovelFactivel.equals("" + ConstantesSistema.SIM)){
				ImovelPerfil imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId(ImovelPerfil.FACTIVEL_EM_ESPERA);
				imovel.setImovelPerfil(imovelPerfil);
			}
			// [FIM][SB0002]- Atualizar Situacao do Imovel

		}
		// [FIM] ALTERACAO NA AGUA

		// [INICIO] ALTERACAO NO ESGOTO
		if((indicadorTipoLigacao.equalsIgnoreCase("" + LigacaoTipo.LIGACAO_ESGOTO) || indicadorTipoLigacao.equalsIgnoreCase("3"))
						&& idSituacaoLigacaoEsgotoNova != null){

			String parametroReligarImovelFactivelFaturavel = ParametroAtendimentoPublico.P_PERMITE_RELIGAR_IMOVEL_FACTIVEL_FATURAVEL
							.executar();
			
			// [FS0006] - Verificar existencia de hidrometro
			LigacaoEsgoto ligacaoEsgoto = null;
			FiltroLigacaoEsgoto filtroLigacaoEsgoto = new FiltroLigacaoEsgoto();
			filtroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID, imovel.getId()));

			Collection<LigacaoEsgoto> colecaoLigacaoEsgoto = getControladorUtil().pesquisar(filtroLigacaoEsgoto,
							LigacaoEsgoto.class.getName());

			if(!Util.isVazioOrNulo(colecaoLigacaoEsgoto)){
				ligacaoEsgoto = colecaoLigacaoEsgoto.iterator().next();
			}

			if((idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + LigacaoEsgotoSituacao.FACTIVEL) || idSituacaoLigacaoEsgotoNova
							.equalsIgnoreCase("" + LigacaoEsgotoSituacao.POTENCIAL)) && imovel.getHidrometroInstalacaoHistorico() != null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.hidrometro_ja_instalado_ligacao_esgoto", null, imovel.getId().toString());

			}

			// [SB0001]- Deletar Dados da Ligacao
			if((idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + LigacaoEsgotoSituacao.POTENCIAL) || idSituacaoLigacaoEsgotoNova
							.equalsIgnoreCase("" + LigacaoEsgotoSituacao.FACTIVEL))
							&& parametroReligarImovelFactivelFaturavel != null
							&& parametroReligarImovelFactivelFaturavel.equals("" + ConstantesSistema.NAO)){

				if(ligacaoEsgoto != null){
					//
					// // Registrar Transacao
					// ligacaoEsgoto.setOperacaoEfetuada(operacaoEfetuada);
					// ligacaoEsgoto.adicionarUsuario(usuarioLogado,
					// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					// registradorOperacao.registrarOperacao(ligacaoEsgoto);
					// getControladorTransacao().registrarTransacao(ligacaoEsgoto);
					// Registrar Transacao

					getControladorUtil().remover(ligacaoEsgoto);
				}
			}

			// [SB0002]- Atualizar Situacao do Imovel
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
			ligacaoEsgotoSituacao.setId(new Integer(idSituacaoLigacaoEsgotoNova));
			imovel.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);

			if(idSituacaoLigacaoEsgotoNova != null && idSituacaoLigacaoEsgotoNova.equals("" + LigacaoEsgotoSituacao.LIGADO)){
				ImovelPerfil imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId(ImovelPerfil.NORMAL);
				imovel.setImovelPerfil(imovelPerfil);
			}

			if(idSituacaoLigacaoEsgotoNova != null && idSituacaoLigacaoEsgotoNova.equals("" + LigacaoEsgotoSituacao.FACTIVEL_FATURADA)
							&& idAnteriorSituacaoLigacaoEsgoto != null
							&& idAnteriorSituacaoLigacaoEsgoto.equals(LigacaoEsgotoSituacao.LIGADO)
							&& parametroReligarImovelFactivelFaturavel != null
							&& parametroReligarImovelFactivelFaturavel.equals("" + ConstantesSistema.SIM)){
				ImovelPerfil imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId(ImovelPerfil.FACTIVEL_FATURADO);
				imovel.setImovelPerfil(imovelPerfil);
			}
		}
		// [FIM] ALTERACAO NO ESGOTO

		imovel.setUltimaAlteracao(new Date());

		// [FS0005]- Atualizacao Realizada por outro Usuario
		FiltroImovel filtroImovelBase = new FiltroImovel();
		filtroImovelBase.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
		Collection<Imovel> colecaoImovelBase = getControladorUtil().pesquisar(filtroImovelBase, Imovel.class.getName());

		if(colecaoImovelBase != null && !colecaoImovelBase.isEmpty()){

			// Recupera o grupo na base de dados
			Imovel imovelBase = colecaoImovelBase.iterator().next();

			// [FS0004] - Atualização realizada por outro usuário
			if(imovelBase.getUltimaAlteracao().after(imovel.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}

		// Registrar Transacao
		imovel.setOperacaoEfetuada(operacaoEfetuada);
		imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovel);
		getControladorTransacao().registrarTransacao(imovel);
		// Registrar Transacao

		// [SB0002]- Atualizar Situacao do Imovel
		getControladorUtil().atualizar(imovel);

		// [SB0003]- Atualizar Ordem de Servico
		// String idMotivoEncerramento = AtendimentoMotivoEncerramento.CONCLUSAO_SERVICO.toString();
		//
		// OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
		// osEncerramentoHelper.setNumeroOS(new Integer(idOrdemServico));
		// osEncerramentoHelper.setDataExecucao(new Date());
		// osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
		// osEncerramentoHelper.setIdMotivoEncerramento(idMotivoEncerramento);
		// osEncerramentoHelper.setUltimaAlteracao(new Date());

		OrdemServico ordemServico = new OrdemServico();
		ordemServico = getControladorOrdemServico().consultarDadosOrdemServico(Util.converterStringParaInteger(idOrdemServico));

		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		ordemServico.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(ordemServico);

		// Chamada ao [UC0457]Encerrar Ordem de Servico
		// getControladorOrdemServico().encerrarOSComExecucaoSemReferencia(osEncerramentoHelper,
		// mapServicosAutorizados,
		// OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);

		return imovel.getId();
	}

	/**
	 * Pesquisa todos os ids das situações de ligação de água.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ControladorException{

		try{
			return repositorioAtendimentoPublico.pesquisarTodosIdsSituacaoLigacaoAgua();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa todos os ids das situações de ligação de esgoto.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ControladorException{

		try{
			return repositorioAtendimentoPublico.pesquisarTodosIdsSituacaoLigacaoEsgoto();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Este cso de uso permite efetuar a ligação de água e eventualmente a
	 * instalação de hidrômetro, sem informação de RA sendo chamado direto pelo
	 * menu.
	 * [UC0579] - Efetuar Ligação de Água com Intalação de Hidrômetro
	 * 
	 * @author Flávio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel)
					throws ControladorException{

		EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper retorno = null;
		try{
			Collection objetos = repositorioAtendimentoPublico.pesquisarEfetuarLigacaoAguaHidrometroSemRA(idImovel);
			short indicadorExclusao = 0;
			short indicadorRedeAgua = 0;
			if(!objetos.isEmpty()){
				Iterator iterator = objetos.iterator();
				while(iterator.hasNext()){
					retorno = new EfetuarLigacaoAguaComInstalacaoHidrometroSemRAHelper();
					Object[] objeto = (Object[]) iterator.next();

					// idImovel
					if(objeto[0] != null){
						retorno.setMatriculaImovel(((Integer) objeto[0]) + "");
					}
					// nomeCliente
					if(objeto[1] != null){
						retorno.setClienteUsuario((String) objeto[1]);
					}
					// cpf
					if(objeto[2] != null){
						retorno.setCpfCnpjCliente((String) objeto[2]);
					}else // cnpj
					if(objeto[3] != null){
						retorno.setCpfCnpjCliente((String) objeto[3]);
					}
					// situacaoLigacaoAgua
					if(objeto[4] != null){
						retorno.setSituacaoLigacaoAgua((String) objeto[4]);
					}
					// situacaoLigacaoEsgoto
					if(objeto[5] != null){
						retorno.setSituacaoLigacaoEsgoto((String) objeto[5]);
					}
					// indicadorExclusaoImovel
					if(objeto[6] != null){
						indicadorExclusao = ((Short) objeto[6]);
					}
					// indicadorExclusaoImovel
					if(objeto[7] != null){
						indicadorRedeAgua = ((Short) objeto[7]);
					}
					// idSituacaoLigacaoAgua
					if(objeto[8] != null){
						retorno.setIdSituacaoLigacaoAgua((Integer) objeto[8]);
					}
					// idSituacaoLigacaoEsgoto
					if(objeto[9] != null){
						retorno.setIdSituacaoLigacaoEsgoto((Integer) objeto[9]);
					}

				}

				// [FS0006] Verifica Situação do Imovel
				if(indicadorExclusao == ConstantesSistema.SIM){
					throw new ControladorException("atencao.atualizar_situacao_imovel_indicador_exclusao_esgoto", null, idImovel.toString());
				}
				// [FS0007] Verifica Situação rede de água da quadra
				if(indicadorRedeAgua == ConstantesSistema.SIM){
					throw new ControladorException("atencao.seituacao_rede_agua_quadra", null, idImovel.toString());
				}
				// [FS0002] Validar Situação de Água do Imóvel
				if(!retorno.getIdSituacaoLigacaoAgua().equals(LigacaoAguaSituacao.POTENCIAL)
								&& !retorno.getIdSituacaoLigacaoAgua().equals(LigacaoAguaSituacao.FACTIVEL)
								&& !retorno.getIdSituacaoLigacaoAgua().equals(LigacaoAguaSituacao.EM_FISCALIZACAO)){
					throw new ControladorException("atencao.situacao_ligacao_agua_invaliga", null, idImovel.toString());
				}
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Gerar Contrato de Prestação de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 03/05/2007
	 * @throws ControladorException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ControladorException{

		try{
			Collection colecaoDadosRelatorio = repositorioAtendimentoPublico.obterDadosContratoPrestacaoServico(idImovel);

			Collection colecaoContratoPrestacaoServicoHelper = new ArrayList();
			Iterator colecaoDadosRelatorioIterator = colecaoDadosRelatorio.iterator();
			while(colecaoDadosRelatorioIterator.hasNext()){

				ContratoPrestacaoServicoHelper contratoPrestacaoServicoHelper = new ContratoPrestacaoServicoHelper();

				// Obtém os dados do crédito realizado
				Object[] dadosRelatorio = (Object[]) colecaoDadosRelatorioIterator.next();

				Imovel imovel = null;
				Cliente cliente = null;
				Cliente clienteResponsavel = null;
				
				// Imovel
				if(idImovel != null){
					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

					imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));
				}

				// Cliente
				if(dadosRelatorio[1] != null){
					FiltroCliente filtroCliente = new FiltroCliente();
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.NACIONALIDADE);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ESTADO_CIVIL);
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, (Integer) dadosRelatorio[1]));
					
					cliente = (Cliente) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroCliente, Cliente.class.getName()));
				}

				// Responsavel
				if(dadosRelatorio[2] != null){
					clienteResponsavel = (Cliente) getControladorUtil().pesquisar((Integer) dadosRelatorio[2], Cliente.class, false);
				}

				// Nome Unidade Negocio
				if(dadosRelatorio[3] != null){
					contratoPrestacaoServicoHelper.setNomeUnidadeNegocio((String) dadosRelatorio[3]);
				}

				// Consumo Minimo
				if(dadosRelatorio[4] != null){
					contratoPrestacaoServicoHelper.setConsumoMinimo((Integer) dadosRelatorio[4]);
				}
				
				// Nome Municipio
				if(dadosRelatorio[5] != null){
					contratoPrestacaoServicoHelper.setNomeMunicipio((String) dadosRelatorio[5]);
				}

				contratoPrestacaoServicoHelper.setCliente(cliente);
				contratoPrestacaoServicoHelper.setClienteResponsavel(clienteResponsavel);
				contratoPrestacaoServicoHelper.setImovel(imovel);

				colecaoContratoPrestacaoServicoHelper.add(contratoPrestacaoServicoHelper);
			}

			return colecaoContratoPrestacaoServicoHelper;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro){

		try{
			repositorioAtendimentoPublico.atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(idImovel, idHidrometro);
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obtém os dados necessário da ligação de água, de esgoto e do hidrômetro
	 * instalado na ligação de água
	 * 
	 * @author Rafael Corrêa
	 * @date 17/05/2007
	 * @throws ControladorException
	 */
	public DadosLigacoesBoletimCadastroHelper obterDadosLigacaoAguaEsgoto(Integer idImovel) throws ControladorException{

		DadosLigacoesBoletimCadastroHelper dadosLigacoesBoletimCadastroHelper = new DadosLigacoesBoletimCadastroHelper();
		Object[] dadosLigacos = null;

		try{
			dadosLigacos = repositorioAtendimentoPublico.obterDadosLigacaoAguaEsgoto(idImovel);

			if(dadosLigacos != null){

				LigacaoAgua ligacaoAgua = new LigacaoAgua();
				LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;
				Hidrometro hidrometro = null;
				HidrometroProtecao hidrometroProtecao = null;

				// Diâmetro da Ligação de Água
				if(dadosLigacos[0] != null){
					LigacaoAguaDiametro ligacaoAguaDiametro = new LigacaoAguaDiametro();
					ligacaoAguaDiametro.setId((Integer) dadosLigacos[0]);
					ligacaoAgua.setLigacaoAguaDiametro(ligacaoAguaDiametro);
				}

				// Material da Ligação de Água
				if(dadosLigacos[1] != null){
					LigacaoAguaMaterial ligacaoAguaMaterial = new LigacaoAguaMaterial();
					ligacaoAguaMaterial.setId((Integer) dadosLigacos[1]);
					ligacaoAgua.setLigacaoAguaMaterial(ligacaoAguaMaterial);
				}

				// Diâmetro da Ligação de Esgoto
				if(dadosLigacos[2] != null){
					LigacaoEsgotoDiametro ligacaoEsgotoDiametro = new LigacaoEsgotoDiametro();
					ligacaoEsgotoDiametro.setId((Integer) dadosLigacos[2]);
					ligacaoEsgoto.setLigacaoEsgotoDiametro(ligacaoEsgotoDiametro);
				}

				// Material da Ligação de Esgoto
				if(dadosLigacos[3] != null){
					LigacaoEsgotoMaterial ligacaoEsgotoMaterial = new LigacaoEsgotoMaterial();
					ligacaoEsgotoMaterial.setId((Integer) dadosLigacos[3]);
					ligacaoEsgoto.setLigacaoEsgotoMaterial(ligacaoEsgotoMaterial);
				}

				// Leitura Inicial
				if(dadosLigacos[4] != null){
					hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
					hidrometro = new Hidrometro();
					hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao((Integer) dadosLigacos[4]);
				}

				// Capacidade do Hidrômetro
				if(dadosLigacos[5] != null){
					HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
					hidrometroCapacidade.setId((Integer) dadosLigacos[5]);
					hidrometro.setHidrometroCapacidade(hidrometroCapacidade);
				}

				// Marca do Hidrômetro
				if(dadosLigacos[6] != null){
					HidrometroMarca hidrometroMarca = new HidrometroMarca();
					hidrometroMarca.setId((Integer) dadosLigacos[6]);
					hidrometro.setHidrometroMarca(hidrometroMarca);
				}

				// Local de Instalação do Hidrômetro
				if(dadosLigacos[7] != null){
					HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
					hidrometroLocalInstalacao.setId((Integer) dadosLigacos[7]);
					hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
				}

				// Proteção do Hidrômetro
				if(dadosLigacos[8] != null){
					hidrometroProtecao = new HidrometroProtecao();
					hidrometroProtecao.setId((Integer) dadosLigacos[8]);
				}

				// Indicador Cavalete
				if(dadosLigacos[9] != null){
					hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete((Short) dadosLigacos[9]);
				}

				// Data da Ligação da Água
				if(dadosLigacos[10] != null){
					ligacaoAgua.setDataLigacao((Date) dadosLigacos[10]);
				}

				// Data do Corte da Água
				if(dadosLigacos[11] != null){
					ligacaoAgua.setDataCorte((Date) dadosLigacos[11]);
				}

				// Data da Supressão da Água
				if(dadosLigacos[12] != null){
					ligacaoAgua.setDataSupressao((Date) dadosLigacos[12]);
				}

				// Data da Ligação do Esgoto
				if(dadosLigacos[13] != null){
					ligacaoEsgoto.setDataLigacao((Date) dadosLigacos[13]);
				}

				// Data da Religação de Água
				if(dadosLigacos[14] != null){
					ligacaoAgua.setDataReligacao((Date) dadosLigacos[14]);
				}

				// Data da Restabelecimento de Água
				if(dadosLigacos[15] != null){
					ligacaoAgua.setDataRestabelecimentoAgua((Date) dadosLigacos[15]);
				}

				// Data da Instalação do Hidrômetro
				if(dadosLigacos[16] != null){
					hidrometroInstalacaoHistorico.setDataInstalacao((Date) dadosLigacos[16]);
				}

				// Número do Hidrômetro
				if(dadosLigacos[17] != null){
					hidrometro.setNumero((String) dadosLigacos[17]);
				}

				// Indicador de Cavalete
				if(dadosLigacos[18] != null){
					hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete((Short) dadosLigacos[18]);
				}

				// Descrição da Proteção do Hidrômetro
				if(dadosLigacos[19] != null){
					hidrometroProtecao.setDescricao((String) dadosLigacos[19]);
				}

				if(hidrometroInstalacaoHistorico != null){
					hidrometroInstalacaoHistorico.setHidrometro(hidrometro);
					hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);
				}
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				dadosLigacoesBoletimCadastroHelper.setLigacaoAgua(ligacaoAgua);
				dadosLigacoesBoletimCadastroHelper.setLigacaoEsgoto(ligacaoEsgoto);

			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return dadosLigacoesBoletimCadastroHelper;

	}

	public RelatorioContratoPrestacaoServicoJuridicoBean gerarContratoJuridica(Integer idImovel, Integer idClienteEmpresa)
					throws ControladorException{

		RelatorioContratoPrestacaoServicoJuridicoBean relatorioBean = null;

		ClienteImovel clienteImovel;
		try{
			clienteImovel = repositorioAtendimentoPublico.pesquisarDadosContratoJuridica(idImovel);

			if(clienteImovel != null){
				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
				// 3.4.1 Diretor Presidente
				Cliente clientePresidente = repositorioAtendimentoPublico.pesquisaClienteContrato(sistemaParametro
								.getClientePresidenteCompesa().getId());
				// 3.4.2 Diretor Comercial
				Cliente clienteDiretor = repositorioAtendimentoPublico.pesquisaClienteContrato(sistemaParametro
								.getClienteDiretorComercialCompesa().getId());
				// 3.4.3 Cliente Usuario
				Cliente clienteUsuario = clienteImovel.getCliente();
				// 3.4.4 Endereco Imovel
				String endereco = getControladorEndereco().pesquisarEnderecoFormatado(idImovel);
				// 3.4.6 Cliente Representante Empresa
				Cliente clienteEmpresa = repositorioAtendimentoPublico.pesquisaClienteContrato(idClienteEmpresa);
				// 3.5 Comarca(municipio do imovel)
				String municipio = repositorioAtendimentoPublico.pesquisarMunicipio(idImovel);

				relatorioBean = new RelatorioContratoPrestacaoServicoJuridicoBean(clientePresidente.getNome() != null ? clientePresidente
								.getNome() : "", clientePresidente.getCpfFormatado() != null ? clientePresidente.getCpfFormatado() : "",
								clientePresidente.getRg() != null ? clientePresidente.getRg() : "", clientePresidente.getProfissao()
												.getDescricao() != null ? clientePresidente.getProfissao().getDescricao() : "",
								clienteDiretor.getNome() != null ? clienteDiretor.getNome() : "",
								clienteDiretor.getCpfFormatado() != null ? clienteDiretor.getCpfFormatado() : "",
								clienteDiretor.getRg() != null ? clienteDiretor.getRg() : "",
								clienteDiretor.getProfissao().getDescricao() != null ? clienteDiretor.getProfissao().getDescricao() : "",
								clienteUsuario.getNome() != null ? clienteUsuario.getNome() : "",
								clienteUsuario.getCnpjFormatado() != null ? clienteUsuario.getCnpjFormatado() : "",
								endereco != null ? endereco : "", idImovel.toString() != null ? idImovel.toString() : "", clienteEmpresa
												.getNome() != null ? clienteEmpresa.getNome() : "",
								clienteEmpresa.getCpfFormatado() != null ? clienteEmpresa.getCpfFormatado() : "",
								clienteEmpresa.getRg() != null ? clienteEmpresa.getRg() : "", municipio != null ? municipio : "", Util
												.formatarData(new Date()));
			}
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relatorioBean;
	}

	/**
	 * [UC0608] Efetuar Ligação de Esgoto sem RA.
	 * [FS0001] Verificar existência da matrícula do Imovel.
	 * [FS0007] Verificar situação do imóvel.
	 * [FS0008] Verificar Situação Rede de Esgoto da Quadra.
	 * 
	 * @author Sávio Luiz.
	 * @date 10/09/2007
	 * @param imovel
	 * @throws ControladorException
	 */
	public String validarMatriculaImovel(Integer idImovel) throws ControladorException{

		String mensagem = null;

		int quantidadeImoveis = getControladorImovel().verificarExistenciaImovel(idImovel);
		if(quantidadeImoveis == 0){
			boolean imovelExcluido = getControladorImovel().confirmarImovelExcluido(idImovel);
			// [FS0007] Verificar situação do imóvel.
			if(imovelExcluido){
				throw new ControladorException("atencao.atualizar_situacao_imovel_indicador_exclusao_esgoto", null, "" + idImovel);
			}
			// [FS0001] Verificar existência da matrícula do Imovel.
			mensagem = "Matrícula do imóvel inexistente.";
		}

		Short indicadorEsgoto = getControladorLocalidade().pesquisarIndicadorRedeEsgotoQuadra(idImovel);

		// [FS0008] - Verificar situação rede de esgoto da quadra
		// Caso o parâmetro P_CRITICAR_ESGOTO_QUADRA tenha valor 1.
		if(ParametroAtendimentoPublico.P_CRITICAR_ESGOTO_QUADRA.executar().equals(ConstantesSistema.SIM.toString())){
			// Teste para saber se o indicador de rede de esgoto da tabela quadra
			// esta como: 1 - Sem rede ; 2 - com rede ; 3 - rede parcial
			// Flávio Leonardo alterado dia 30/11/2007
			if(indicadorEsgoto != null && indicadorEsgoto.equals(ConstantesSistema.INDICADOR_USO_ATIVO)){
				// indicador de uso ativo = 1; indicador de rede = 1 igual a sem rede.
				throw new ControladorException("atencao.percentual_rede_esgoto_quadra", null, "" + idImovel);
			}
		}

		return mensagem;
	}

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 * obter numero do hidrômetro na ligação de água.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.obterNumeroHidrometroEmLigacaoAgua(imovelId);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pela Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ControladorException{

		try{
			return repositorioAtendimentoPublico.obterHidrometroCapacidadeEmLigacaoAgua(imovelId);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Collection<RelatorioImoveisSituacaoLigacaoAguaHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaHelper> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ControladorException{

		Collection<RelatorioCertidaoNegativaHelper> retorno = new ArrayList<RelatorioCertidaoNegativaHelper>();

		Collection<Object[]> colecaoPesquisa = null;

		try{

			colecaoPesquisa = repositorioAtendimentoPublico.pesquisarRelatorioCertidaoNegativa(imo);

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		RelatorioCertidaoNegativaHelper helper = null;

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){

			Iterator itera = colecaoPesquisa.iterator();

			if(itera.hasNext()){
				Object[] objeto = (Object[]) itera.next();

				helper = new RelatorioCertidaoNegativaHelper();

				// Nome do usuário
				helper.setNomeClienteUsuario((String) objeto[0]);

				// Matricula do imovel
				helper.setMatriculaImovel((Integer) objeto[1]);

				// Carregamos os dados no imovel para selecionarmos sua matrícula formatada
				// Id do imovel
				Imovel imovel = new Imovel();
				imovel.setId((Integer) objeto[1]);

				// Id da localidade
				Localidade local = new Localidade();
				local.setId((Integer) objeto[2]);
				imovel.setLocalidade(local);

				// Código do Setor Comercial
				SetorComercial setorComercial = new SetorComercial();
				setorComercial.setCodigo((Integer) objeto[3]);
				imovel.setSetorComercial(setorComercial);

				// Numero da quadra
				Quadra quadra = new Quadra();
				quadra.setNumeroQuadra((Integer) objeto[4]);
				imovel.setQuadra(quadra);

				// Lote e sublote
				imovel.setLote((Short) objeto[5]);
				imovel.setSubLote((Short) objeto[6]);

				// Inscrição formatada
				helper.setInscricaoImovel(imovel.getInscricaoFormatada());

				// Nome do Usuário
				Cliente clienteUsuario = getControladorImovel().pesquisarClienteUsuarioImovel(imovel.getId());
				helper.setNomeUsuario(clienteUsuario.getNome());

				// Selecionamos os dados do Endereço
				String[] dadosEndereco = this.getControladorEndereco().pesquisarEnderecoFormatadoDividido((Integer) objeto[1]);
				helper.setEndereco((String) dadosEndereco[0]);
				helper.setMunicipio((String) dadosEndereco[1]);
				helper.setBairro((String) dadosEndereco[3]);
				helper.setCEP(Cep.formatarCep((String) dadosEndereco[4]));
				helper.setLocalidade((String) objeto[15]);

				// Obtemos a principal categoria do imovel
				Categoria categoria = this.getControladorImovel().obterPrincipalCategoriaImovel((Integer) objeto[1]);
				helper.setCategoria(categoria.getDescricao());

				// Obtemos a principal subcategoria do imovel
				ImovelSubcategoria subCategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
								(Integer) objeto[1]);
				FiltroImovelSubCategoria filtro = new FiltroImovelSubCategoria();
				filtro.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria");
				filtro.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.SUBCATEGORIA_ID, subCategoria.getComp_id()
								.getSubcategoria().getId()));
				filtro.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imo.getId()));
				Collection colImovelSubCategoria = this.getControladorUtil().pesquisar(filtro, ImovelSubcategoria.class.getName());

				subCategoria = (ImovelSubcategoria) Util.retonarObjetoDeColecao(colImovelSubCategoria);

				helper.setSubcategoria(subCategoria.getComp_id().getSubcategoria().getDescricao());

				// Obtemos a quantidade de economias do imovel
				helper.setEconomias((Short) objeto[7]);

				// Obtemos o perfil do imovel
				helper.setPerfilImovel((String) objeto[8]);

				// Obtemos a situação da ligacao de agua
				helper.setLigacaoAguaSituacao((String) objeto[9]);

				// Obtemos a situação da ligacao de esgoto
				helper.setLigacaoEsgotoSituacao((String) objeto[10]);

				// Obtemos o tipo do poco
				helper.setSituacaoPoco((String) objeto[11]);

				// Obtemos o nome abreviado da empresa
				helper.setDescricaoAbreviadaEmpresa((String) objeto[12]);

				// Obtemos o nome da empresa
				helper.setDescricaoEmpresa((String) objeto[13]);

				// Obtemos o CNPJ da empresa
				helper.setCNPJEmpresa((String) objeto[14]);

				// Obtemos o logradouro da empresa
				helper.setEnderecoEmpresa((objeto[16] != null ? ((String) objeto[16]).trim() : "")
								+ (objeto[19] != null ? ", " + ((String) objeto[19]).trim() + " " : "")
								+ (objeto[17] != null ? ((String) objeto[17]).trim() : "")
								+ (objeto[18] != null ? ", " + ((String) objeto[18]).trim() : "")
								+ (objeto[20] != null ? " - " + ((String) objeto[20]).trim() : "")
								+ (objeto[21] != null ? " - CEP " + Cep.formatarCep((Integer) objeto[21]) : ""));

				// Obtemos o site
				helper.setSiteEmpresa((String) objeto[22]);

				// Obtemos O 0800
				helper.setZeroOitossentosEmpresa((String) objeto[23]);

				// Obtemos a inscrição estadual
				helper.setInscricaoEstadualEmpresa((String) objeto[24]);

				String area = "";

				if(objeto[25] != null){
					area = Util.formatarMoedaReal((BigDecimal) objeto[25]);
				}else{
					AreaConstruidaFaixa areaConstruidaFaixa = (AreaConstruidaFaixa) objeto[26];
					if(areaConstruidaFaixa != null){
						area = areaConstruidaFaixa.getFaixaCompleta();
					}else{
						area = "0";
					}
				}

				helper.setArea(area);

				if(objeto[27] != null){
					helper.setNumeroHidrometro((String) objeto[27]);
				}else{
					helper.setNumeroHidrometro("");
				}
			}
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		Calendar dataFimVencimentoDebito = new GregorianCalendar();
		dataFimVencimentoDebito.add(Calendar.DATE, -sistemaParametro.getNumeroDiasVencimentoDebitoGeracaoCertidaoNegativaDebitos());

		// Pesquisamos se o imovel possue debitos ou não
		ObterDebitoImovelOuClienteHelper debitoGeral = this.getControladorCobranca().obterDebitoImovelOuCliente(1, imo.getId() + "", null,
						null, "190001", "299901", Util.criarData(1, 1, 1900), dataFimVencimentoDebito.getTime(), 1, 1, 1, 1, 1, 1, 1, true,
						sistemaParametro, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

		if(sistemaParametro.getIndicadorCertidaoNegativaEfeitoPositivo() == ConstantesSistema.NAO.shortValue()){

			if(debitoGeral != null){
				if((debitoGeral.getColecaoContasValores() != null && !debitoGeral.getColecaoContasValores().isEmpty())
								|| (debitoGeral.getColecaoGuiasPagamentoValores() != null && !debitoGeral.getColecaoGuiasPagamentoValores()
												.isEmpty())){
					throw new ControladorException("atencao.imovel_com_debitos_certidao");
				}
				if(sistemaParametro.getIndicadorDebitoACobrarValidoCertidaoNegativa().equals(ConstantesSistema.SIM)
								&& (debitoGeral.getColecaoDebitoACobrar() != null && !debitoGeral.getColecaoDebitoACobrar().isEmpty())){
					throw new ControladorException("atencao.imovel_com_debitos_certidao");
				}
			}
		}

		// Verificamos se existe débito para o imovel selecionado
		if((debitoGeral.getColecaoContasValores() != null && debitoGeral.getColecaoContasValores().size() > 0)
						|| (debitoGeral.getColecaoCreditoARealizar() != null && debitoGeral.getColecaoCreditoARealizar().size() > 0)
						|| (debitoGeral.getColecaoDebitoACobrar() != null && debitoGeral.getColecaoDebitoACobrar().size() > 0)
						|| (debitoGeral.getColecaoDebitoCreditoParcelamentoHelper() != null && debitoGeral
										.getColecaoDebitoCreditoParcelamentoHelper().size() > 0)
						|| (debitoGeral.getColecaoGuiasPagamentoValores() != null && debitoGeral.getColecaoGuiasPagamentoValores().size() > 0)){

			Collection<RelatorioCertidaoNegativaItemBean> colItens = new ArrayList<RelatorioCertidaoNegativaItemBean>();

			if(debitoGeral.getColecaoContasValores() != null && debitoGeral.getColecaoContasValores().size() > 0){
				Iterator iteContas = debitoGeral.getColecaoContasValores().iterator();

				// Total das contas
				BigDecimal totalContas = new BigDecimal(0);

				// Faturas em aberto
				while(iteContas.hasNext()){
					ContaValoresHelper contaHelper = (ContaValoresHelper) iteContas.next();

					RelatorioCertidaoNegativaItemBean fatura = new RelatorioCertidaoNegativaItemBean("Faturas", Util
									.formatarAnoMesParaMesAno(contaHelper.getConta().getReferencia())
									+ "-" + contaHelper.getConta().getDigitoVerificadorConta(), Util.formatarAnoMesParaMesAno(contaHelper
									.getConta().getReferencia()), Util.formatarData(contaHelper.getConta().getDataVencimentoConta()), Util
									.formataBigDecimal(contaHelper.getValorTotalConta(), 2, true), Util.formatarData(contaHelper.getConta()
									.getDataValidadeConta()));

					totalContas = totalContas.add(contaHelper.getValorTotalConta());

					colItens.add(fatura);
				}

				// Incluimos o total
				RelatorioCertidaoNegativaItemBean fatura = new RelatorioCertidaoNegativaItemBean("TOTAL:" + "", "", "", "", Util
								.formataBigDecimal(totalContas, 2, true), "");

				colItens.add(fatura);
			}

			// Total dos servicos
			BigDecimal totalServicos = new BigDecimal(0);

			if(debitoGeral.getColecaoGuiasPagamentoValores() != null && debitoGeral.getColecaoGuiasPagamentoValores().size() > 0){
				Iterator iteGuias = debitoGeral.getColecaoGuiasPagamentoValores().iterator();

				// Guias de pagamento
				while(iteGuias.hasNext()){
					GuiaPagamentoValoresHelper guiaHelper = (GuiaPagamentoValoresHelper) iteGuias.next();

					/*
					 * 9 de março de 2010
					 * Comentado por Andre Nishimura
					 * TODO: tratar efeito colateral do merge com as alteraçoes nas guias de
					 * pagamento
					 * RelatorioCertidaoNegativaItemBean guia = new
					 * RelatorioCertidaoNegativaItemBean("Guias de Pagamento", guiaHelper
					 * .getGuiaPagamento().getDebitoTipo().getDescricao(),
					 * Util.formatarAnoMesParaMesAno(guiaHelper.getGuiaPagamento()
					 * .getAnoMesReferenciaContabil()),
					 * Util.formatarData(guiaHelper.getGuiaPagamento().getDataVencimento()), Util
					 * .formataBigDecimal(guiaHelper.getGuiaPagamento().getValorDebito(), 2, true),
					 * (guiaHelper.getGuiaPagamento()
					 * .getNumeroPrestacaoTotal() -
					 * guiaHelper.getGuiaPagamento().getNumeroPrestacaoDebito())
					 * + "");
					 * totalServicos =
					 * totalServicos.add(guiaHelper.getGuiaPagamento().getValorDebito());
					 * colItens.add(guia);
					 */
				}
			}

			if(debitoGeral.getColecaoDebitoACobrar() != null && debitoGeral.getColecaoDebitoACobrar().size() > 0){
				Iterator iteDebitos = debitoGeral.getColecaoDebitoACobrar().iterator();

				// Debitos
				while(iteDebitos.hasNext()){
					DebitoACobrar debitoHelper = (DebitoACobrar) iteDebitos.next();

					// alterado por Vivianne Sousa data:11/04/2008
					// analista : Aryed

					RelatorioCertidaoNegativaItemBean debito = new RelatorioCertidaoNegativaItemBean("Debitos a cobrar", debitoHelper
									.getDebitoTipo().getDescricao(), Util.formatarAnoMesParaMesAno(debitoHelper
									.getAnoMesReferenciaContabil()), debitoHelper.getNumeroPrestacaoCobradas() + "/"
									+ debitoHelper.getNumeroPrestacaoDebitoMenosBonus(), Util.formataBigDecimal(debitoHelper
									.getValorTotalComBonus(), 2, true), (debitoHelper.getNumeroPrestacaoDebitoMenosBonus() - debitoHelper
									.getNumeroPrestacaoCobradas())
									+ "");

					// RelatorioCertidaoNegativaItemBean debito = new
					// RelatorioCertidaoNegativaItemBean(
					// "Debitos a cobrar",
					// debitoHelper.getDebitoTipo().getDescricao(),
					// Util.formatarAnoMesParaMesAno( debitoHelper.getAnoMesReferenciaContabil() ),
					// debitoHelper.getNumeroPrestacaoCobradas() + "/" +
					// debitoHelper.getNumeroPrestacaoDebito(),
					// Util.formataBigDecimal( debitoHelper.getValorRestanteCobrado() , 2, true ),
					// ( debitoHelper.getNumeroPrestacaoDebito() -
					// debitoHelper.getNumeroPrestacaoCobradas() ) + "" );

					totalServicos = totalServicos.add(debitoHelper.getValorTotalComBonus());

					colItens.add(debito);
				}
			}

			if(debitoGeral.getColecaoCreditoARealizar() != null && debitoGeral.getColecaoCreditoARealizar().size() > 0){
				Iterator iteCredito = debitoGeral.getColecaoCreditoARealizar().iterator();

				// Debitos
				while(iteCredito.hasNext()){
					CreditoARealizar creditoHelper = (CreditoARealizar) iteCredito.next();

					// alterado por Vivianne Sousa data:11/04/2008
					// analista responsavel: Adriano

					RelatorioCertidaoNegativaItemBean credito = new RelatorioCertidaoNegativaItemBean("Crédito a Realizar", creditoHelper
									.getCreditoTipo().getDescricao(), Util.formatarAnoMesParaMesAno(creditoHelper
									.getAnoMesReferenciaContabil()), creditoHelper.getNumeroPrestacaoRealizada() + "/"
									+ creditoHelper.getNumeroPrestacaoCreditoMenosBonus(), Util.formataBigDecimal(creditoHelper
									.getValorTotalComBonus(), 2, true),
									(creditoHelper.getNumeroPrestacaoCreditoMenosBonus() - creditoHelper.getNumeroPrestacaoRealizada())
													+ "");

					// RelatorioCertidaoNegativaItemBean credito = new
					// RelatorioCertidaoNegativaItemBean(
					// "Crédito a Realizar",
					// creditoHelper.getCreditoTipo().getDescricao(),
					// Util.formatarAnoMesParaMesAno( creditoHelper.getAnoMesReferenciaContabil() ),
					// creditoHelper.getNumeroPrestacaoRealizada() + "/" +
					// creditoHelper.getNumeroPrestacaoCredito(),
					// Util.formataBigDecimal( creditoHelper.getValorTotal() , 2, true ),
					// ( creditoHelper.getNumeroPrestacaoCredito() -
					// creditoHelper.getNumeroPrestacaoRealizada() ) + "" );

					totalServicos = totalServicos.add(creditoHelper.getValorTotalComBonus());

					colItens.add(credito);
				}
			}

			// Incluimos o total
			RelatorioCertidaoNegativaItemBean servicos = new RelatorioCertidaoNegativaItemBean("TOTAL:" + "", "", "", "", Util
							.formataBigDecimal(totalServicos, 2, true), "");

			colItens.add(servicos);

			helper.setItens(colItens);

		}

		// verificar se tem parcelamento no imovel
		try{

			Collection colecaoParcelamentoDoImovel = this.getControladorCobranca().pesquisarParcelamentosSituacaoNormal(imo.getId());

			if(colecaoParcelamentoDoImovel.size() > 0 && !colecaoParcelamentoDoImovel.equals("")){
				helper.setImovelComParcelamento(true);
			}else{
				helper.setImovelComParcelamento(false);
			}

		}catch(ControladorException e){
			throw new ControladorException("erro.sistema", e);
		}
		retorno.add(helper);

		return retorno;
	}

	/**
	 * Pesquisa os dados necessários para a geração do relatório
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioCertidaoNegativaClienteBean> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes,
					Cliente clienteInformado) throws ControladorException{

		Collection<RelatorioCertidaoNegativaClienteBean> retorno = new ArrayList<RelatorioCertidaoNegativaClienteBean>();

		try{
			Collection<Object[]> colecaoDadosRelatorioCertidaoNegativaCliente = repositorioAtendimentoPublico
							.pesquisarRelatorioCertidaoNegativaCliente(idsClientes);

			for(Object[] dadosRelatorio : colecaoDadosRelatorioCertidaoNegativaCliente){

				RelatorioCertidaoNegativaClienteBean relatorioCertidaoNegativaClienteBean = new RelatorioCertidaoNegativaClienteBean();

				String cliente = clienteInformado.getId() + " - " + clienteInformado.getNome();
				relatorioCertidaoNegativaClienteBean.setCliente(cliente);

				// Responsável
				if(dadosRelatorio[0] != null){
					relatorioCertidaoNegativaClienteBean.setResponsavel(((Integer) dadosRelatorio[0]).toString());
				}

				// Id do Imóvel e Endereço
				if(dadosRelatorio[1] != null){
					Integer idImovel = (Integer) dadosRelatorio[1];
					relatorioCertidaoNegativaClienteBean.setIdImovel(Util.retornaMatriculaImovelFormatada(idImovel));

					// Nome do Usuário
					Cliente clienteUsuario = getControladorImovel().pesquisarClienteUsuarioImovel(idImovel);
					relatorioCertidaoNegativaClienteBean.setNomeUsuario(clienteUsuario.getNome());

					// Endereço
					String endereco = getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
					relatorioCertidaoNegativaClienteBean.setEndereco(endereco);

				}

				// Situação da Ligação de Água
				if(dadosRelatorio[2] != null){
					relatorioCertidaoNegativaClienteBean.setSituacaoLigacaoAgua((String) dadosRelatorio[2]);
				}

				retorno.add(relatorioCertidaoNegativaClienteBean);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [] Efetuar Religação de Água com Substituição de Hidrômetro.
	 * Permite validar o efetuar religação de Água com Substituição de Hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução
	 * da ordem de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author Luiz César
	 * @date 09/06/2010
	 * @param ordem
	 *            ,veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_religacao_agua_substituicao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael Rossiter Data: 26/04/2007
		 */
		// Caso 3.3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);

		// Caso 4
		// if (ordem.getRegistroAtendimento() == null && ordem.getImovel() == null){
		// throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		// }
		// if (ordem.getRegistroAtendimento() != null && ordem.getRegistroAtendimento().getImovel()
		// == null){
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }

		Imovel imovel = null;

		if(ordem.getImovel() != null){
			imovel = ordem.getImovel();
		}else if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				imovel = ordem.getRegistroAtendimento().getImovel();
			}else{
				throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
			}
		}else{
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
		}

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}

		// Caso 3.4 - Verificar se o registro de atendimento associado a Ordem de Serviço possui
		// imóvel informado
		// if (ordem.getRegistroAtendimento().getImovel() == null) {
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }

		// Imovel imovel = ordem.getRegistroAtendimento().getImovel();

		// Caso 5.1 - Verificar se a situação do imóvel está como ativa
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}

		// Caso 6.1 - Validar Situação de Água do Imóvel - se a situação da ligação de água do
		// imóvel está diferente de CORTADO
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()){
			throw new ControladorException("atencao.situacao_validar_religacao_agua_invalida_substituicao_hidrometro_exibir", null, imovel
							.getLigacaoAguaSituacao().getDescricao());
		}

		// [FS0008] - Verificar existência de hidrômetro no tipo de medição
		if(ordem.getRegistroAtendimento() == null
						|| ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
										MedicaoTipo.LIGACAO_AGUA.shortValue())){
			if(imovel.getLigacaoAgua() == null || imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_ligacao_agua_sem_hidrometro", null, "" + imovel.getId());
			}
		}else{
			if(ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
							MedicaoTipo.POCO.shortValue())
							&& imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_poco_sem_hidrometro", null, "" + imovel.getId());
			}
		}

	}

	/**
	 * [UC0498] Efetuar Religação de Água com Instalação de hidrômetro.
	 * Permite efetuar religação de Água com Instalação de Hidrômetro ou pelo
	 * menu ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Sávio Luiz
	 * @date 29/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		if(ligacaoAgua != null && imovel != null){

			Integer id = null;

			// [SB0003] Gerar Histórico de Instalação do Hidrometro

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

			if(hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ControladorException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
								hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getDescricao());
			}

			validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());

			hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

			hidrometroInstalacaoHistorico.setIndicadorTrocaProtecao((short) 2);
			hidrometroInstalacaoHistorico.setIndicadorTrocaRegistro((short) 2);
			id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

			try{

				// [SB004] Atualizar Hidrômetro
				Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;

				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				hidrometroInstalacaoHistorico.setId(id);
				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				// [SB0001] Atualizar Imóvel/Ligação de Água

				Date dataCorrente = new Date();
				ligacaoAgua.setUltimaAlteracao(dataCorrente);

				// [FS0007] - Atualização realizada por outrio usuário
				FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
				filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ligacaoAgua.getId()));
				Collection colecaoLigacaoAguaBase = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

				if(colecaoLigacaoAguaBase == null || colecaoLigacaoAguaBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				LigacaoAgua ligacaoAguaBase = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);
				if(ligacaoAguaBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ligacaoAgua.getId()));
				Collection colecaoImovelBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoImovelBase == null || colecaoImovelBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				Imovel imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelBase);
				if(imovelBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				getControladorUtil().atualizar(ligacaoAgua);

			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

			getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

			if(imovel.getLigacaoEsgotoSituacao() != null
							&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
				ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

				imovel.setUltimaAlteracao(new Date());

				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
			}
		}

		// [SB0002] Atualizar Ordem de Serviço
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(ordemServico);

		if(!integracaoComercialHelper.isVeioEncerrarOS()){

			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
			getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		/*
		 * [OC790655][UC0747][SB0005]: Dados Execução no Histórico de Manutenção da
		 * Ligação do Imóvel
		 */
		if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
			getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
							HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			getControladorOrdemServico()
							.gerarDebitoOrdemServico(ordemServico.getId(), ordemServico.getServicoTipo().getDebitoTipo().getId(),
											ordemServico.getValorAtual(), new Integer(qtdParcelas)/*
																								 * ,
																								 * ordemServico
																								 * .
																								 * getPercentualCobranca
																								 * (
																								 * )
																								 * .
																								 * toString
																								 * (
																								 * )
																								 * ,
																								 * integracaoComercialHelper
																								 * .
																								 * getUsuarioLogado
																								 * (
																								 * )
																								 */);
		}

		/**
		 * 9. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo
		 * na tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE)
			 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de linha na
			 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
			 * contrário, inserir nova linha.
			 */
			getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}

	}

	private BigDecimal somarDocumentoCobrancaItem(Set<CobrancaDocumentoItem> cobrancaDocumentoItems){

		BigDecimal somaDocumento = new BigDecimal(0);

		if(cobrancaDocumentoItems == null || cobrancaDocumentoItems.size() == 0){
			return BigDecimal.ZERO;
		}else{

			for(CobrancaDocumentoItem cobrancaDocumentoItem : cobrancaDocumentoItems){

				somaDocumento.add(cobrancaDocumentoItem.getValorItemCobrado());
			}
		}

		return somaDocumento;
	}

	/**
	 * Efetuar Religação de Água com Substituição de hidrômetro.
	 * Permite efetuar religação de Água com Substituição de Hidrômetro ou pelo
	 * menu ou pela funcionalidade encerrar a Execução da ordem de serviço.
	 * 
	 * @author Luiz César
	 * @date 22/01/2008
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void efetuarReligacaoAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();
		// String matriculaImovel = integracaoComercialHelper.getMatriculaImovel();
		String situacaoHidrometroSubstituido = integracaoComercialHelper.getSituacaoHidrometroSubstituido();
		Integer localArmazenagemHidrometro = integracaoComercialHelper.getLocalArmazenagemHidrometro();

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();

		if(ligacaoAgua != null && imovel != null){

			OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
			HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = integracaoComercialHelper.getHidrometroSubstituicaoHistorico();

			if(hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ControladorException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
								hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getDescricao());
			}

			hidrometroSubstituicaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroSubstituicaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			Integer id = null;

			// validacaoSubstituicaoHidrometro(matriculaImovel,
			// hidrometroInstalacaoHistorico.getHidrometro().getNumero(),
			// situacaoHidrometroSubstituido);

			try{
				// [SB0001] Atualizar o histórico da instalação do hidrômetro
				// substituido
				repositorioAtendimentoPublico.atualizarHidrometroInstalacoHistorico(hidrometroSubstituicaoHistorico);

				// [SB0002] Gerar Histórico de instalação do hidrômetro
				hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("2"));
				id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

				// [SB0003]Atualizar Imóvel/Ligação de Água

				// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
				// colunas da tabela LIGACAO_AGUA
				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
					// Caso o tipo de medição seja igual a Poço, atualiza as colunas
					// da tabela POCO
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				// [SB004]Atualizar situação de hidrômetro na tabela HIDROMETRO
				Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				// [SB005]Atualizar situação do hidrômetro substituido na tabela
				// HIDROMETRO
				situacaoHidrometro = new Integer(situacaoHidrometroSubstituido);
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				repositorioAtendimentoPublico.atualizarLocalArmazanagemHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								localArmazenagemHidrometro);

				// [SB0005] Atualizar Ordem de Serviço
				ordemServico.setOperacaoEfetuada(operacaoEfetuada);
				ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ordemServico);

				// [SB006]Atualizar Ordem de Serviço
				if(!integracaoComercialHelper.isVeioEncerrarOS()){
					this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
					getControladorOrdemServico().atualizaOSGeral(ordemServico, true, false);
				}

				/*
				 * [OC790655][UC0945][SB0006]: Atualizar Dados Execução no Histórico
				 * de Manutenção da Ligação do Imóvel
				 */
				if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
					getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
									HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO);
				}

				// if (ordemServico.getServicoTipo().getDebitoTipo() != null &&
				// ordemServico.getServicoNaoCobrancaMotivo() == null) {
				// getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
				// ordemServico.getServicoTipo().getDebitoTipo().getId(),
				// Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(),
				// ordemServico.getPercentualCobranca().toString()),
				// new Integer(integracaoComercialHelper.getQtdParcelas()));
				// }
				if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

					getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
									ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(),
									Integer.valueOf(qtdParcelas));
					/*
					 * , ordemServico.getPercentualCobranca().toString(),
					 * integracaoComercialHelper.getUsuarioLogado()
					 */
				}

				/**
				 * 9. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID
				 * diferente de nulo
				 * na tabela ORDEM_SERVIÇO) para a ordem em questão:
				 */
				if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

					/**
					 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
					 * COBRANCA_PRODUTIVIDADE)
					 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
					 * chave
					 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
					 * linha na
					 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor,
					 * caso
					 * contrário, inserir nova linha.
					 */
					getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
									CobrancaDebitoSituacao.EXECUTADO);
				}

				// ##############################################################################

				// Caso a Ordem de Servico nao esteja Associada a Documento de
				// Cobranca
				// nem a Registro de Atendimento
				Short indicadorTrocaProtecao = hidrometroSubstituicaoHistorico.getIndicadorTrocaProtecao();
				hidrometroSubstituicaoHistorico.setIndicadorTrocaProtecao(indicadorTrocaProtecao);

				Short indicadorTrocaRegistro = hidrometroSubstituicaoHistorico.getIndicadorTrocaRegistro();
				hidrometroSubstituicaoHistorico.setIndicadorTrocaRegistro(indicadorTrocaRegistro);

				boolean osAssociadaDOC = getControladorOrdemServico().verificarOSAssociadaDocumentoCobranca(ordemServico.getId());
				boolean osAssociadaRA = getControladorOrdemServico().verificarOSAssociadaRA(ordemServico.getId());

				if(!osAssociadaDOC && !osAssociadaRA){
					// Recupera a data de Encerramento da OS
					FiltroOrdemServico filtroOs = new FiltroOrdemServico();
					filtroOs.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServico.getId()));

					Collection colecaoDados = getControladorUtil().pesquisar(filtroOs, OrdemServico.class.getName());
					Iterator iColecaoDados = colecaoDados.iterator();
					OrdemServico os = (OrdemServico) iColecaoDados.next();

					Date dataExecucaoOs = os.getDataExecucao();

					// **************************************************************
					// Alterado por: Ivan Sergio
					// Data: 12/02/2009
					// CRC1222 - Seta a data de encerramento com o valor do Helper
					// de
					// integracao.
					// **************************************************************
					if(dataExecucaoOs == null) dataExecucaoOs = ordemServico.getDataExecucao();
					// **************************************************************

					// BoletimOsConcluida boletim = new BoletimOsConcluida();
					// boletim.setId(ordemServico.getId());
					// boletim.setOrdemServico(ordemServico);
					// boletim.setLocalidade(ordemServico.getImovel().getLocalidade());
					// boletim.setAnoMesReferenciaBoletim(Util.getAnoMesComoInt(dataEncerramentoOs));
					// boletim.setCodigoFiscalizacao(new Short("0"));
					// boletim.setUsuario(null);
					// boletim.setDataFiscalizacao(null);
					// boletim.setDataEncerramentoBoletim(null);
					// boletim.setIndicadorTrocaProtecaoHidrometro(indicadorTrocaProtecao);
					// boletim.setIndicadorTrocaRegistroHidrometro(indicadorTrocaRegistro);
					// boletim.setHidrometroLocalInstalacao(hidrometroSubstituicaoHistorico.getHidrometroLocalInstalacao());
					// boletim.setUltimaAlteracao(new Date());
					// getControladorUtil().inserir(boletim);

				}

				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				Date dataCorrente = new Date();
				ligacaoAgua.setUltimaAlteracao(dataCorrente);

				// [FS0007] - Atualização realizada por outrio usuário
				FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
				filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ligacaoAgua.getId()));
				Collection colecaoLigacaoAguaBase = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

				if(colecaoLigacaoAguaBase == null || colecaoLigacaoAguaBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				LigacaoAgua ligacaoAguaBase = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);
				if(ligacaoAguaBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ligacaoAgua.getId()));
				Collection colecaoImovelBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoImovelBase == null || colecaoImovelBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				Imovel imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelBase);
				if(imovelBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				getControladorUtil().atualizar(ligacaoAgua);

				LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
				ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

				if(imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

					imovel.setUltimaAlteracao(new Date());

					getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
				}
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

		}
	}

	/**
	 * Validar se o local de armazenagem é OFICINA (HILA_ICOFICINA = 1) e se a situação atual do
	 * hidrômetro for diferente de "EM MANUTENÇÃO"
	 * 
	 * @param idLocalArmazenagem
	 * @param idSituacaoHidrometro
	 * @throws ControladorException
	 */
	public void validarLocalArmazenagemSituacaoHidrometro(String idLocalArmazenagem, String idSituacaoHidrometro)
					throws ControladorException{

		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();
		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID, idLocalArmazenagem));

		Collection<HidrometroLocalArmazenagem> colecaoHidrometroLocalArmazenagem = this.getControladorUtil().pesquisar(
						filtroHidrometroLocalArmazenagem, HidrometroLocalArmazenagem.class.getName());

		if(Util.isVazioOrNulo(colecaoHidrometroLocalArmazenagem)){

			HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
							.retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);
			if(hidrometroLocalArmazenagem.getIndicadorOficina() != null
							&& (hidrometroLocalArmazenagem.getIndicadorOficina().shortValue() == 1 && idSituacaoHidrometro.equals("2"))){

				throw new ControladorException("atencao.local_armazenagem_oficina_situacao_hidrometro_diferente_manutencao");
			}

		}
	}

	/**
	 * [UC0747] Efetuar Religação de Água com Instalação de hidrômetro.
	 * Permite validar o efetuar religação de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author Sávio Luiz
	 * @date 29/01/2008
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarReligacaoAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_religacao_agua_instalacao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael
		 * Rossiter Data: 26/04/2007
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);

		// Caso 4
		// if (ordem.getRegistroAtendimento() == null && ordem.getImovel() == null){
		// throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		// }
		// if (ordem.getRegistroAtendimento() != null && ordem.getRegistroAtendimento().getImovel()
		// == null){
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }
		// Imovel imovel;
		// if (ordem.getImovel() == null){
		// imovel = ordem.getRegistroAtendimento().getImovel();
		// }else{
		// imovel = ordem.getImovel();
		// }

		Imovel imovel = null;

		if(ordem.getImovel() != null){
			imovel = ordem.getImovel();
		}else if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				imovel = ordem.getRegistroAtendimento().getImovel();
			}else{
				throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
			}
		}else{
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
		}

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}

		// [FS0003] Validar Situação de Água do Imóvel.
		// . Caso a situação da ligação de água do imóvel esteja diferente de CORTADO,
		// CORTADO(pedido) e CORTADO(inativo)
		// (LAST_ID da tabela IMOVEL diferente do LAST_ID da tabela de LIGACAO_AGUA_SITUACAO
		// diferente de CORTADO CORTADO(pedido)
		// e CORTADO(inativo)),
		// exibir a mensagem “Situação da Ligação de Água do Imóvel <<LAST_DSLIGACAOAGUASITUACAO com
		// LAST_ID = LAST_ID da tabela IMOVEL>>
		// está inválida para efetuar a Religação de Água com instalação de hidrômetro, a situação
		// de Água deve ser Cortado” e retornar para o passo correspondente no fluxo principal.

		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO_PEDIDO.intValue()
						&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORT_MED_INDIVIDUAL.intValue()){

			throw new ControladorException("atencao.situacao_validar_religacao_agua_invalida_exibir", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
			throw new ControladorException("atencao.imovel_ja_possui_hidrometro_ligacao_agua", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		// [FS0002] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}
	}

	/**
	 * [UC0747] Efetuar Corte de Água com Instalação de hidrômetro.
	 * Permite validar o efetuar corte de Água com Instalação de hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução da ordem
	 * de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param ordem
	 * @param veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarCorteAguaComInstalacaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_INSTALACAO_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_corte_agua_instalacao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Isilva
		 * Rossiter Data: 16/12/2010
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);

		// Caso 4
		// if (ordem.getRegistroAtendimento() == null && ordem.getImovel() == null){
		// throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		// }
		// if (ordem.getRegistroAtendimento() != null && ordem.getRegistroAtendimento().getImovel()
		// == null){
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }
		// Imovel imovel;
		// if (ordem.getImovel() == null){
		// imovel = ordem.getRegistroAtendimento().getImovel();
		// }else{
		// imovel = ordem.getImovel();
		// }

		Imovel imovel = null;

		if(ordem.getImovel() != null){
			imovel = ordem.getImovel();
		}else if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				imovel = ordem.getRegistroAtendimento().getImovel();
			}else{
				throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
			}
		}else{
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
		}

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}

		// [FS0003] Validar Situação de Água do Imóvel.
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){
			throw new ControladorException("atencao.situacao_validar_corte_agua_invalida_exibir", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		if(imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
			throw new ControladorException("atencao.imovel_ja_possui_hidrometro_corte_agua", null, imovel.getLigacaoAguaSituacao()
							.getDescricao());
		}

		// [FS0002] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}
	}

	/**
	 * @author isilva
	 * @param idHidrometro
	 * @param situacaoHidrometro
	 * @throws ControladorException
	 */
	public void atualizarSituacaoHidrometro(Integer idHidrometro, Integer situacaoHidrometro) throws ControladorException{

		try{
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(idHidrometro, situacaoHidrometro);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [] Efetuar Corte de Água com Substituição de Hidrômetro.
	 * Permite validar o efetuar corte de Água com Substituição de Hidrômetro
	 * Exibir ou pelo menu ou pela funcionalidade encerrar a Execução
	 * da ordem de serviço.
	 * [FS0002] Verificar Situação do Imovel. [FS0003] Validar Situação de Água
	 * 
	 * @author isilva
	 * @date 20/12/2010
	 * @param ordem
	 * @param veioEncerrarOS
	 * @throws ControladorException
	 */
	public void validarCorteAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_SUBSTITUICAO_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_corte_agua_substituicao_hidrometro_invalida");
		}

		/*
		 * Validações já contidas no método anteriormente Autor: Raphael Rossiter Data: 26/04/2007
		 */
		// Caso 3.3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);

		// Caso 4
		// if (ordem.getRegistroAtendimento() == null && ordem.getImovel() == null){
		// throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		// }
		// if (ordem.getRegistroAtendimento() != null && ordem.getRegistroAtendimento().getImovel()
		// == null){
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }

		Imovel imovel = null;

		if(ordem.getImovel() != null){
			imovel = ordem.getImovel();
		}else if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				imovel = ordem.getRegistroAtendimento().getImovel();
			}else{
				throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
			}
		}else{
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
		}

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}

		// Caso 3.4 - Verificar se o registro de atendimento associado a Ordem de Serviço possui
		// imóvel informado
		// if (ordem.getRegistroAtendimento().getImovel() == null) {
		// throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" +
		// ordem.getRegistroAtendimento().getId());
		// }

		// Imovel imovel = ordem.getRegistroAtendimento().getImovel();

		// Caso 5.1 - Verificar se a situação do imóvel está como ativa
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}

		// Caso 6.1 - Validar Situação de Água do Imóvel - se a situação da ligação de água do
		// imóvel está diferente de CORTADO
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){
			throw new ControladorException("atencao.situacao_validar_corte_agua_invalida_substituicao_hidrometro_exibir", null, imovel
							.getLigacaoAguaSituacao().getDescricao());
		}

		// [FS0008] - Verificar existência de hidrômetro no tipo de medição
		if(ordem.getRegistroAtendimento() == null
						|| ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
										MedicaoTipo.LIGACAO_AGUA.shortValue())){
			if(imovel.getLigacaoAgua() == null || imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_ligacao_agua_sem_hidrometro", null, "" + imovel.getId());
			}
		}else{
			if(ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
							MedicaoTipo.POCO.shortValue())
							&& imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_poco_sem_hidrometro", null, "" + imovel.getId());
			}
		}

	}

	/**
	 * Insere o HidrometroInstalacaoHistorico com HidrometroSituacao INSTALADO
	 * OBS: Não Atualiza a OS, Efetua os Calculos,
	 * ném Gera/acumula dados relativos aos documentos gerados
	 * 
	 * @author isilva
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 */
	public IntegracaoComercialHelper atualizarHidrometroInstalacaoHistoricoSemAtualizarOSEEfetuarCalculos(
					IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		Integer id = null;

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

		validacaoInstalacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getNumero());

		id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

		// [SB0002]Atualizar Imóvel/Ligação de Água
		try{
			// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
			// colunas da tabela LIGACAO_AGUA
			if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
								.getLigacaoAgua().getId(), id);
				// Caso o tipo de medição seja igual a Poço, atualiza as colunas
				// da tabela POCO
			}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
				repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(
								hidrometroInstalacaoHistorico.getImovel().getId(), id);
			}

			// [SB003]Atualizar situação de hidrômetro na tabela HIDROMETRO
			Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
			repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
							situacaoHidrometro);
			integracaoComercialHelper.getHidrometroInstalacaoHistorico().setId(id);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return integracaoComercialHelper;
	}

	/**
	 * [UC] Efetuar Restabelecimento da Ligação de Água com Substituição de
	 * hidrômetro.
	 * Permite validar o Efetuar Restabelecimento Ligação de Água com Substituição
	 * de hidrômetro Exibir ou pelo menu ou pela funcionalidade encerrar a
	 * Execução da ordem de serviço.
	 * [FS] Verificar Situação Rede de Água na Quadra. [FS] Verificar
	 * Situação do Imovel. [FS] Validar Situação de Água do Imóvel
	 * 
	 * @author isilva
	 * @date 22/12/2010
	 * @param imovel
	 * @throws ControladorException
	 */
	public void validarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException{

		// [FS0001] - Validar Ordem de Serviço
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_restabelecimento_ligacao_agua_substituicao_hidrometro_invalida");
		}

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordem, veioEncerrarOS);
		// Caso 4
		if(ordem.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, "" + ordem.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordem.getRegistroAtendimento().getImovel();

		// [FS0002] Validar Situação de Água do Imóvel.
		// Parametro adicionado a pedido da DESO.(OC0923666)
		if(ParametroAtendimentoPublico.P_TRATAR_CORTADO_A_PEDIDO_COMO_SUPRIMIDO.executar().equals(ConstantesSistema.SIM.toString())){
			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.CORTADO_PEDIDO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_invalida_restabelecimento", null, imovel
								.getLigacaoAguaSituacao().getDescricao());
			}
		}else{
			if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPRIMIDO.intValue()
							&& imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.SUPR_PARC_PEDIDO.intValue()){

				throw new ControladorException("atencao.situacao_ligacao_agua_invalida_restabelecimento", null, imovel
								.getLigacaoAguaSituacao().getDescricao());
			}
		}

		// [FS0007] Verificar Situação Rede de Água na Quadra
		if((imovel.getQuadra().getIndicadorRedeAgua()).equals(Quadra.SEM_REDE)){
			throw new ControladorException("atencao.seituacao_rede_agua_quadra", null, imovel.getId() + "");
		}

		// [FS0006] Verificar Situação do Imovel
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao", null, imovel.getId() + "");
		}

		// [FS0008] - Verificar existência de hidrômetro no tipo de medição
		if(ordem.getRegistroAtendimento() == null
						|| ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
										MedicaoTipo.LIGACAO_AGUA.shortValue())){
			if(imovel.getLigacaoAgua() == null || imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_ligacao_agua_sem_hidrometro", null, "" + imovel.getId());
			}
		}else{
			if(ordem.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua().equals(
							MedicaoTipo.POCO.shortValue())
							&& imovel.getHidrometroInstalacaoHistorico() == null){
				throw new ControladorException("atencao.imovel_poco_sem_hidrometro", null, "" + imovel.getId());
			}
		}
	}

	/**
	 * @author isilva
	 * @param integracaoComercialHelper
	 * @param usuario
	 * @throws ControladorException
	 */
	public void efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper,
					Usuario usuario) throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();
		String situacaoHidrometroSubstituido = integracaoComercialHelper.getSituacaoHidrometroSubstituido();
		Integer localArmazenagemHidrometro = integracaoComercialHelper.getLocalArmazenagemHidrometro();

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		Imovel imovel = integracaoComercialHelper.getImovel();

		if(imovel == null){
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}else if(imovel.getIndicadorExclusao() == null || imovel.getIndicadorExclusao().intValue() == Short.valueOf("1").intValue()){
			throw new ControladorException("atencao.imovel.associado.ordem.servico.nao.esta.ativo", null, imovel.getId().toString());
		}

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		try{
			if(ligacaoAgua != null && imovel != null){

				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
				HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = integracaoComercialHelper
								.getHidrometroSubstituicaoHistorico();

				if(hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
					throw new ControladorException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
									hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getDescricao());
				}

				hidrometroSubstituicaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
				hidrometroSubstituicaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				Integer id = null;

				// [SB] Atualizar o histórico da instalação do hidrômetro
				// substituido
				repositorioAtendimentoPublico.atualizarHidrometroInstalacoHistorico(hidrometroSubstituicaoHistorico);

				// [SB] Gerar Histórico de instalação do hidrômetro
				hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("2"));
				id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);

				// [SB]Atualizar Imóvel/Ligação de Água

				// Caso o tipo de medição seja igual a Ligação de Água, atualiza as
				// colunas da tabela LIGACAO_AGUA
				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
					// Caso o tipo de medição seja igual a Poço, atualiza as colunas
					// da tabela POCO
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				// [SB]Atualizar situação de hidrômetro na tabela HIDROMETRO
				Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				// [SB]Atualizar situação do hidrômetro substituido na tabela
				// HIDROMETRO
				situacaoHidrometro = new Integer(situacaoHidrometroSubstituido);
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);
				repositorioAtendimentoPublico.atualizarLocalArmazanagemHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								localArmazenagemHidrometro);

				Short indicadorTrocaProtecao = hidrometroSubstituicaoHistorico.getIndicadorTrocaProtecao();
				hidrometroSubstituicaoHistorico.setIndicadorTrocaProtecao(indicadorTrocaProtecao);

				Short indicadorTrocaRegistro = hidrometroSubstituicaoHistorico.getIndicadorTrocaRegistro();
				hidrometroSubstituicaoHistorico.setIndicadorTrocaRegistro(indicadorTrocaRegistro);

				hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

				// ******************** [SB] Gerar Ligação de Água ******************
				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				Date dataCorrente = new Date();
				ligacaoAgua.setUltimaAlteracao(dataCorrente);

				// [FS0007] - Atualização realizada por outrio usuário
				FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
				filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ligacaoAgua.getId()));
				Collection colecaoLigacaoAguaBase = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

				if(colecaoLigacaoAguaBase == null || colecaoLigacaoAguaBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				LigacaoAgua ligacaoAguaBase = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);
				if(ligacaoAguaBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ligacaoAgua.getId()));
				Collection colecaoImovelBase = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoImovelBase == null || colecaoImovelBase.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
				Imovel imovelBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovelBase);
				if(imovelBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				getControladorUtil().atualizar(ligacaoAgua);

				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					// 4. Caso o tipo de medição seja igual a “LIGAÇÃO DE ÁGUA”
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					// Caso o tipo de medição seja igual a Poço, atualiza as colunas
					// da tabela POCO
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
				ligacaoAguaSituacao.setId(LigacaoAguaSituacao.LIGADO);

				getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoAgua(imovel, ligacaoAguaSituacao);

				if(imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(LigacaoEsgotoSituacao.LIGADO);

					imovel.setUltimaAlteracao(new Date());
					// [SB0002] Atualizar Imóvel
					getControladorImovel().atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(imovel, ligacaoEsgotoSituacao);
				}
			}

			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, false, true);

			}

			/*
			 * [OC790655][UC0946][SB0006]: Atualizar Dados Execução no Histórico de
			 * Manutenção da Ligação do Imóvel
			 */
			if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(ordemServico,
								HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA_COM_SUBSTITUICAO_HIDROMETRO);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(
								ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(ordemServico.getValorAtual(), ordemServico.getPercentualCobranca()
												.toString()), new Integer(qtdParcelas));
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	public Atividade verificarExistenciaAtividadeUnica(String atividadeUnica) throws ControladorException{

		FiltroAtividade filtro = new FiltroAtividade();
		filtro.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA, ConstantesSistema.INDICADOR_USO_DESATIVO));

		return pesquisar(filtro, Atividade.class, "Atividade");
	}

	/**
	 * Efetuar Instalacao/Substituicao de Registro Magnetico
	 * [FS0001] - Validar Ordem de Serviço
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void validarInstalacaoSubstituicaoRegistroMagneticoExibir(OrdemServico ordem, boolean veioEncerrarOS)
					throws ControladorException{

		if(ordem == null){
			throw new ControladorException("atencao.ordem.servico.inesxistente_invalida");
		}

		// Valida Serviço associado a OS
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_instalacao_substituicao_registro_magnetico_invalida");
		}

		// Valida Situacao Ordem Serviço
		this.getControladorOrdemServico().validarOrdemServicoRegistroMagnetico(ordem, veioEncerrarOS);

		Imovel imovel = null;

		if(ordem.getImovel() != null){
			imovel = ordem.getImovel();
		}else if(ordem.getRegistroAtendimento() != null){
			if(ordem.getRegistroAtendimento().getImovel() != null){
				imovel = ordem.getRegistroAtendimento().getImovel();
			}else{
				throw new ControladorException("atencao.ordem_servico_nao_associada_imovel_instalacao_substituicao_registro_magnetico",
								null, ordem.getRegistroAtendimento().getId().toString());
			}
		}

		// Verificar se a situação do imóvel está como ativa
		if(imovel.getIndicadorExclusao() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao_instalacao_substituicao_registro_magnetico", null,
							imovel.getId() + "");
		}

		// Validar Situação de Água do Imóvel - se a situação da ligação de água do
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){
			throw new ControladorException("atencao.situacao_validar_instalacao_substituicao_registro_magnetico", null, imovel
							.getLigacaoAguaSituacao().getDescricao());
		}

		LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());

		if(ligacaoAgua != null){
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = ligacaoAgua.getHidrometroInstalacaoHistorico();

			if(hidrometroInstalacaoHistorico == null){
				throw new ControladorException("atencao.imovel_sem_hidrometro_instalado_instalacao_substituicao_registro_magnetico");

			}

		}
	}

	/**
	 * [UC3064] Efetuar Instalação/Substituição de Tubete Magnético
	 * [FS0001] - Validar Ordem de Serviço
	 * 
	 * @author Leonardo Angelim
	 *         28/08/2012
	 */
	public void validarOSInstalacaoSubstituicaoTubeteMagnetico(OrdemServico ordem, boolean veioEncerrarOS) throws ControladorException{

		// . Caso não exista a Ordem de Serviço (não existe ODSE_ID=Id da Ordem de Serviço informado
		// na tabela ORDEM_SERVICO),
		// exibir a mensagem “Ordem de Serviço inexistente” e retornar para o passo correspondente
		// no fluxo principal.
		if(ordem == null){
			throw new ControladorException("atencao.ordem.servico.inesxistente_invalida");
		}

		// . Caso o serviço associado à Ordem de Serviço não corresponda a um dos serviços tratados
		// pela operação solicitada
		// (SVTP_ID da tabela ORDEM_SERVICO não contido em SVTP_ID da tabela SERVICO_TIPO_OPERACAO
		// com OPER_ID=Id da operação solicitada),
		// exibir a mensagem “Serviço associado à ordem de serviço informada não permite a
		// instalação/substituição de tubete magnético.” e retornar para o passo correspondente no
		// fluxo principal.

		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordem.getServicoTipo().getId(),
						Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_instalacao_substituicao_tubete_magnetico_invalida");
		}

		// . Caso a Ordem de Serviço tenha sido informada via Menu e a situação da Ordem de Serviço
		// não esteja encerrada por execução
		// (ORSE_CDSITUAÇÃO na tabela ORDEM_SERVICO com o valor diferente de “Encerrada” (2)),
		// exibir a mensagem “Esta Ordem de Serviço está <<descrição da situação >>. Não é possível
		// efetuar a instalação/substituição de tubete magnético.” e retornar para o passo
		// correspondente no fluxo principal.

		this.getControladorOrdemServico().validarOrdemServicoTubeteMagnetico(ordem, veioEncerrarOS);

		// . Caso o Registro de Atendimento da Ordem de Serviço não tenha um imóvel informado
		// (IMOV_ID com o valor nulo na tabela REGISTRO_ATENDIMENTO com RGAT_ID=RGAT_ID da tabela
		// ORDEM_SERVICO),
		// exibir a mensagem “O Registro de Atendimento <<RGAT_ID>> da ordem de serviço informada
		// não tem imóvel associado. Não é possível efetuar a instalação/substituição de tubete
		// magnético.” e retornar para o passo correspondente no fluxo principal.

		if(ordem.getRegistroAtendimento() != null && ordem.getRegistroAtendimento().getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_nao_associada_imovel_instalacao_substituicao_tubete_magnetico", null,
							ordem.getRegistroAtendimento().getId().toString());

		}

		Imovel imovel = ordem.getImovel();

		// [FS0002] - Verificar situação do imóvel
		if(imovel.getIndicadorExclusao() == ConstantesSistema.INDICADOR_IMOVEL_EXCLUIDO){
			throw new ControladorException("atencao.situacao_imovel_indicador_exclusao_instalacao_substituicao_tubete_magnetico", null,
							imovel.getId() + "");
		}

		// [FS0003] - Verificar situação da ligação de água
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){
			throw new ControladorException("atencao.situacao_validar_religacao_agua_invalida_instalacao_substituicao_tubete_magnetico",
							null, imovel.getLigacaoAguaSituacao().getDescricao());
		}

		LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().pesquisarLigacaoAgua(imovel.getId());
		if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() == null){
			throw new ControladorException("atencao.imovel_sem_hidrometro_instalado_instalacao_substituicao_tubete_magnetico", null, imovel
							.getLigacaoAguaSituacao().getDescricao());
		}

	}

	/**
	 * [UC3063] Efetuar Instalacao/Substituicao de Instalacao de Registro Magnetico
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */
	public void efetuarInstalacaoSubstituicaoRegistroMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException{

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();

		if(ligacaoAgua != null){
			Date dataCorrente = new Date();

			// [SB0001] – Atualizar Instalação/Substituição de Registro Magnético

			CorteRegistroTipo corteRegistroTipo = (CorteRegistroTipo) this.getControladorUtil().pesquisar(CorteRegistroTipo.REG_MAGNET,
							CorteRegistroTipo.class, true);

			FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
			filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ligacaoAgua.getId()));
			Collection colecaoLigacaoAguaBase = getControladorUtil().pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

			if(Util.isVazioOrNulo(colecaoLigacaoAguaBase)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			LigacaoAgua ligacaoAguaBase = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);

			if(ligacaoAguaBase.getUltimaAlteracao().after(ligacaoAgua.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			ligacaoAgua.setCorteRegistroTipo(corteRegistroTipo);
			ligacaoAgua.setUltimaAlteracao(dataCorrente);

			ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
			ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ligacaoAgua);

			this.getControladorUtil().atualizar(ligacaoAgua);

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper
							.getRegistroMagneticoInstalacaoHistorico();

			FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
			filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.ID,
							hidrometroInstalacaoHistorico.getId()));

			Collection colecaoHidrometroInstalacaoHistoricoBase = getControladorUtil().pesquisar(filtroHidrometroInstalacaoHistorico,
							HidrometroInstalacaoHistorico.class.getName());

			if(Util.isVazioOrNulo(colecaoHidrometroInstalacaoHistoricoBase)){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoBase = (HidrometroInstalacaoHistorico) Util
							.retonarObjetoDeColecao(colecaoHidrometroInstalacaoHistoricoBase);

			if(hidrometroInstalacaoHistoricoBase.getUltimaAlteracao().after(hidrometroInstalacaoHistorico.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.registro_remocao_nao_existente");
			}

			hidrometroInstalacaoHistorico.setCorteRegistroTipo(corteRegistroTipo);
			hidrometroInstalacaoHistorico.setUltimaAlteracao(dataCorrente);

			hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
			hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

			this.getControladorUtil().atualizar(hidrometroInstalacaoHistorico);
		}

		// [SB0002] Atualizar Ordem de Serviço

		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

		if(!integracaoComercialHelper.isVeioEncerrarOS()){
			this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
			this.getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);
		}

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

			this.getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
							ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(), new Integer(qtdParcelas));
		}

		/**
		 * 9. Caso a ordem de serviço esteja associada a documento de cobrança(CBDO_ID diferente de
		 * nulo
		 * na tabela ORDEM_SERVIÇO) para a ordem em questão:
		 */
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE)
			 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de linha na
			 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
			 * contrário, inserir nova linha.
			 */
			this.getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}
	}

	/**
	 * Efetuar Instalacao/Substituicao de Tubete Magnetico
	 * 
	 * @author Leonardo Angelim
	 * @date 28/08/2012
	 */

	public void efetuarInstalacaoSubstituicaoTubeteMagnetico(IntegracaoComercialHelper integracaoComercialHelper, Usuario usuario)
					throws ControladorException{

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getRegistroMagneticoInstalacaoHistorico();
		String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

		// [SB0001 – Atualizar Instalação/Substituição de Tubete Magnético]
		getControladorUtil().atualizar(ligacaoAgua);
		getControladorUtil().atualizar(hidrometroInstalacaoHistorico);

		// [SB0002 – Atualizar Ordem de Serviço],
		getControladorOrdemServico().atualizaOSGeral(ordemServico, false, false);

		// 7.1. Caso o Motivo da Não Cobrança do débito não esteja selecionado, o sistema gera o
		// débito da ordem de serviço - <<Inclui>> [UC0479 – Gerar Débito Ordem de Serviço]
		if(ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getDebitoTipo() != null){
			if(ordemServico.getServicoNaoCobrancaMotivo() == null){



				getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(),
								new Integer(qtdParcelas));
			}
		}

		// 8. Caso a ordem de serviço esteja associada a documento de cobrança (CBDO_ID com o valor
		// diferente de nulo na tabela ORDEM_SERVIÇO)
		if(ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null){

			/**
			 * . Gerar/acumular dados relativos aos documentos gerados(tabela
			 * COBRANCA_PRODUTIVIDADE)
			 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela chave
			 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de linha na
			 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
			 * contrário, inserir nova linha.
			 */
			getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(ordemServico, false, false,
							CobrancaDebitoSituacao.EXECUTADO);
		}

	}

	public Collection<PercentualCobrancaHelper> obterPercentuaisCobranca() throws ControladorException{

		String[] percentuais = getControladorUtil().pesquisarParametrosDoSistema().getPercentualCobranca().split(";");
		Collection<PercentualCobrancaHelper> colecaoPercentual = new ArrayList<PercentualCobrancaHelper>();

		for(int i = 0; i < percentuais.length; i++){

			PercentualCobrancaHelper percentual = new PercentualCobrancaHelper();
			percentual.setValor(percentuais[i]);
			percentual.setDescricao(percentuais[i] + "%");
			colecaoPercentual.add(percentual);
		}

		return colecaoPercentual;
	}

	/**
	 * Retorna o valor de controladorPermissaoEspecial
	 * 
	 * @return O valor de controladorPermissaoEspecial
	 */

	protected ControladorPermissaoEspecialLocal getControladorPermissaoEspecial(){

		ControladorPermissaoEspecialLocalHome localHome = null;
		ControladorPermissaoEspecialLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorPermissaoEspecialLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_PERMISSAO_ESPECIAL_SEJB);
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

	public void verificarQuantidadeParcelas(Usuario usuario, Short qtdParcelas) throws ControladorException{

		boolean temPermissaoInformarQuantidadeExcedente = getControladorPermissaoEspecial()
						.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuario);

		// [FS00XX] – Validar Quantidade de Parcelas
		Short quantidadeParcelasMaxima = Util.obterShort(ParametroAtendimentoPublico.P_QTD_MAXIMA_PARCELAS_DEBITO_SERVICO.executar());

		if(qtdParcelas > quantidadeParcelasMaxima && !temPermissaoInformarQuantidadeExcedente){

			throw new ControladorException("atencao.quantidade_parcelas_superior", null, quantidadeParcelasMaxima.toString());
		}
	}

	public Short obterQuantidadeParcelasMaxima() throws ControladorException{

		Short quantidadeParcelasMaxima = Util.obterShort(ParametroAtendimentoPublico.P_QTD_MAXIMA_PARCELAS_DEBITO_SERVICO.executar());

		if(quantidadeParcelasMaxima == null){

			quantidadeParcelasMaxima = 0;
		}

		return quantidadeParcelasMaxima;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.util.parametrizacao.Parametrizacao#getExecutorParametro()
	 */
	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosAtendimentoPublico.getInstancia();
	}
	
	/**
	 * [UC3102] - Atualizar Perfil Ligação Esgoto
	 * 
	 * @author Ítalo Almeida
	 *         30/07/2013
	 */
	public void validarExibirAtualizarPerfilLigacaoEsgoto(OrdemServico ordemServico, Boolean veioEncerrarOS) throws ControladorException{

		// [FS0001]
		if(ordemServico == null){
			throw new ControladorException("atencao.ordem_servico_inexistente");
		}

		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_ATUALIZAR_PERFIL_LIGACAO_ESGOTO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_atualizar_perfil_ligacao_esgoto_invalida");
		}

		if(!veioEncerrarOS && ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO){
			throw new ControladorException("atencao.ordem_servico_nao_executada", null, ordemServico.getDescricaoSituacao());
		}

		// [FS0002]
		if(ordemServico.getImovel() != null){

			if(ordemServico.getImovel().getIndicadorExclusao() == ConstantesSistema.NAO){
				throw new ControladorException("atencao.situacao.imovel.invalida", null, ordemServico.getImovel().getId().toString());
			}

		}else{
			throw new ControladorException("atencao.ordem.servico.nao.vinculada.imovel");
		}

		// [FS0004]
		LigacaoEsgotoSituacao les = (LigacaoEsgotoSituacao) getControladorUtil().pesquisar(
						ordemServico.getImovel().getLigacaoEsgotoSituacao().getId(), LigacaoEsgotoSituacao.class, false);

		if(les != null && !les.getId().equals(LigacaoEsgotoSituacao.LIGADO)){
			throw new ControladorException("atencao.atualizar_situacao_ligacao_esgoto_invalida", null, les.getDescricao());
		}
		

	}
	
	/**
	 * [UC3135] Gerar Relatório de Materiais Aplicados
	 * 
	 * @author Felipe Rosacruz
	 * @date 03/02/2014
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioMateriaisAplicadosHelper> obterDadosRelatorioMateriaisAplicados(Integer idLocalidade,
					Integer cdSetorComercial, Date dataExecucaoInicial, Date dataExecucaoFinal, Collection<Integer> colecaoIdServicoTipo,
					Collection<Integer> colecaoIdMaterial, Collection<Integer> colecaoIdEquipe) throws ControladorException{

		Collection<RelatorioMateriaisAplicadosHelper> retorno = new ArrayList<RelatorioMateriaisAplicadosHelper>();

		try{
			Collection<Object[]> dadosRelatorio = repositorioAtendimentoPublico.obterDadosRelatorioMateriaisAplicados(idLocalidade,
							cdSetorComercial, dataExecucaoInicial, dataExecucaoFinal, colecaoIdServicoTipo, colecaoIdMaterial,
							colecaoIdEquipe);

			for(Object[] object : dadosRelatorio){
				RelatorioMateriaisAplicadosHelper relatorioMateriaisAplicadosHelper = new RelatorioMateriaisAplicadosHelper();
				if(object[0] != null){
					relatorioMateriaisAplicadosHelper.setIdLocalidade(((Integer) object[0]));
				}
				if(object[1] != null){
					relatorioMateriaisAplicadosHelper.setCdSetorComercial(((Integer) object[1]));
				}
				if(object[2] != null){
					relatorioMateriaisAplicadosHelper.setIdServicoTipo(((Integer) object[2]));
				}
				if(object[4] != null){
					relatorioMateriaisAplicadosHelper.setIdEquipe(((Integer) object[4]));
				}else if(object[3] != null){
					relatorioMateriaisAplicadosHelper.setIdEquipe(((Integer) object[3]));
				}
				if(object[5] != null){
					relatorioMateriaisAplicadosHelper.setTmExecucao(Util.formatarDataAAAAMMDD(((Date) object[5])));
				}
				if(object[6] != null){
					relatorioMateriaisAplicadosHelper.setIdOrdemServico(((Integer) object[6]));
				}
				if(object[7] != null){
					relatorioMateriaisAplicadosHelper.setDsMaterial(((String) object[7]));
				}
				if(object[8] != null){
					relatorioMateriaisAplicadosHelper.setQtMaterial(((Integer) object[8]).toString());
				}
				if(object[9] != null){
					relatorioMateriaisAplicadosHelper.setNmLocalidade((String) object[9]);
				}
				if(object[10] != null){
					relatorioMateriaisAplicadosHelper.setNmSetorComercial((String) object[10]);
				}
				if(object[11] != null){
					relatorioMateriaisAplicadosHelper.setDsServicoTipo((String) object[11]);
				}
				if(object[13] != null){
					relatorioMateriaisAplicadosHelper.setNmEquipe((String) object[13]);
				}else if(object[12] != null){
					relatorioMateriaisAplicadosHelper.setNmEquipe((String) object[12]);
				}
				if(object[14] != null){
					relatorioMateriaisAplicadosHelper.setIdMaterial(((Integer) object[14]));
				}
				retorno.add(relatorioMateriaisAplicadosHelper);
			}
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * OC1213341 - Verificar se existe valor do Serviço Tipo para a Localidade informada
	 * 
	 * @author Ado Rocha
	 * @date 23/04/2014
	 **/
	public BigDecimal verificarServicoTipoValorLocalidade(Integer idImovel, Integer idDebitoTipo){

		BigDecimal valorServicoLocalidade = null;

		try{

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			Collection colecaoImovel = (Collection) getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());
			Imovel imovelNaBase = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.DEBITOTIPO, idDebitoTipo));
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.SIM));
			Collection colecaoServicoTipo = (Collection) getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
			ServicoTipo servicoTipoBase = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			if(imovelNaBase != null && servicoTipoBase != null){
				FiltroServicoTipoValorLocalidade filtroValorLocalidade = new FiltroServicoTipoValorLocalidade();
				filtroValorLocalidade.adicionarParametro(new ParametroSimples(FiltroServicoTipoValorLocalidade.LOCALIDADE_ID, imovelNaBase
								.getLocalidade().getId()));
				filtroValorLocalidade.adicionarParametro(new ParametroSimples(FiltroServicoTipoValorLocalidade.SERVICO_TIPO_ID,
								servicoTipoBase.getId()));
				filtroValorLocalidade.adicionarParametro(new ParametroSimples(FiltroServicoTipoValorLocalidade.INDICADOR_USO,
								ConstantesSistema.SIM));

				Collection colecaoServicoTipoValorLocalidade = (Collection) getControladorUtil().pesquisar(filtroValorLocalidade,
								ServicoTipoValorLocalidade.class.getName());
				ServicoTipoValorLocalidade valorLocalidadeNaBase = (ServicoTipoValorLocalidade) Util
								.retonarObjetoDeColecao(colecaoServicoTipoValorLocalidade);

				if(valorLocalidadeNaBase != null){
					valorServicoLocalidade = valorLocalidadeNaBase.getValorServico();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return valorServicoLocalidade;
	}
}

