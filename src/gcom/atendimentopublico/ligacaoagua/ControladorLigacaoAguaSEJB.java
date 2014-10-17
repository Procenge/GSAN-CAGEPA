/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ControladorAtendimentoPublicoLocal;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocalHome;
import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.*;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * Defini��o da l�gica de neg�cio do Session Bean de ControladorLigacaoAgua
 * 
 * @author Leonardo Regis
 * @date 09/09/2006
 */
public class ControladorLigacaoAguaSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioLigacaoAgua repositorioLigacaoAgua = null;

	private IRepositorioAtendimentoPublico repositorioAtendimentoPublico = null;

	private static final Logger LOGGER = Logger.getLogger(ControladorLigacaoAguaSEJB.class);

	/**
	 * @exception CreateException
	 */
	public void ejbCreate() throws CreateException{

		repositorioLigacaoAgua = RepositorioLigacaoAguaHBM.getInstancia();
		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
	}

	/**
	 */
	public void ejbRemove(){

	}

	/**
	 */
	public void ejbActivate(){

	}

	/**
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
		// pega a inst�ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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

		// pega a inst�ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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
		// pega a inst�ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorOrdemServicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ORDEM_SERVICO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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
	 * Retorna o valor do ControladorImovel
	 * 
	 * @author Leonardo Regis
	 * @date 20/07/2006
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;
		// pega a inst�ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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
	 * Retorna o valor do ControladorAtendimentoPublico
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @return O valor de controladorAtendimentoPublico
	 */
	private ControladorAtendimentoPublicoLocal getControladorAtendimentoPublico(){

		ControladorAtendimentoPublicoLocalHome localHome = null;
		ControladorAtendimentoPublicoLocal local = null;
		// pega a inst�ncia do ServiceLocator.
		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorAtendimentoPublicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
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
	 * [UC0463] Atualizar Consumo M�nimo de Liga��o �gua
	 * Este m�todo se destina a validar todas as situa��es e particularidades da
	 * atualiza��o do consumo m�nimo da liga��o de agua
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param OrdemServico
	 * @param veioEncerrarOS
	 */
	public void validarExibirAtualizarConsumoMinimoLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException{

		// [FS0001] - Validar Ordem de Servi�o
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(ordemServico.getServicoTipo().getId(),
						Operacao.OPERACAO_CONSUMO_MINIMO_LIGACAO_AGUA_ATUALIZAR_INT);

		if(idOperacao == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizar_consumo_minimo_ligacao_agua_servico");
		}

		/*
		 * Valida��es j� contidas no m�todo anteriormente
		 * Autor: Raphael Rossiter
		 * Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		if(ordemServico.getRegistroAtendimento().getImovel() == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.ordem_servico_ra_imovel_invalida", null, ""
							+ ordemServico.getRegistroAtendimento().getId());
		}

		Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		// [FS0002] Verificar Situa��o do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.naocadastrado", null, "Liga��o de �gua");
		}

		/*
		 * ===================================================================================
		 */
	}

	/**
	 * [UC0463] Atualizar Consumo M�nimo de Liga��o de �gua
	 * [FS004] Validar Consumo M�nimo
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param imovel
	 */
	public void validarConsumoMinimoLigacaoAgua(Imovel imovel) throws ControladorException{

		// Valida��es da liga��o de �gua
		if(imovel.getLigacaoAgua() != null){
			// [UC105] Obter Consumo M�nimo da Liga��o
			int volumeObtido = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);
			if(imovel.getLigacaoAgua().getNumeroConsumoMinimoAgua().intValue() < volumeObtido){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizar_situacao_consumo_minimo_fixado_menor_minimo", null, volumeObtido + "");
			}
		}else{
			// Se entrar aqui significa que a base est� inconsistente.
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizar_consumo_minimo_ligacao_agua_invalida", null, imovel.getId().toString());
		}
	}

	/**
	 * [UC0463] Atualizar Consumo M�nimo da Liga��o de �gua
	 * [SB0001] Atualizar Liga��o de �gua.
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @throws ControladorException
	 */
	public void atualizarConsumoMinimoLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();
		ligacaoAgua.setUltimaAlteracao(new Date());
		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);
		try{

			// Inicio Registrar Transa��o
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CONSUMO_MINIMO_LIGACAO_AGUA_ATUALIZAR,
							ligacaoAgua.getId(), ligacaoAgua.getId(), new UsuarioAcaoUsuarioHelper(integracaoComercialHelper
											.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			registradorOperacao.registrarOperacao(ligacaoAgua);
			getControladorTransacao().registrarTransacao(ligacaoAgua);
			// Fim Registrar Transa��o

			// Atualizar Tabela Liga��o de �gua com novo valor de consumo m�nimo
			repositorioLigacaoAgua.atualizarConsumoMinimoLigacaoAgua(ligacaoAgua);

			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().atualizaOSGeral(integracaoComercialHelper.getOrdemServico(), false, false);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades do
	 * corte liga��o de agua
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirCorteLigacaoAgua(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		ServicoTipo servicoTipo = ordemServico.getServicoTipo();
		Integer idServicoTipo = servicoTipo.getId();

		// [FS0001] - Validar Ordem de Servi�o
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(idServicoTipo,
						Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR_INT);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_corte_ligacao_agua_invalida");
		}

		/*
		 * Valida��es j� contidas no m�todo anteriormente
		 * Autor: Raphael Rossiter
		 * Data: 26/04/2007
		 * ===============================================================================
		 */

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);
		// Caso 4
		// Comentado por Raphael Rossiter em 26/02/2007
		/*
		 * if (ordemServico.getRegistroAtendimento().getImovel() == null) {
		 * throw new ControladorException(
		 * "atencao.ordem_servico_ra_imovel_invalida", null, ""
		 * + ordemServico.getRegistroAtendimento().getId());
		 * }
		 */

		if(ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		}

		// Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		Imovel imovel = ordemServico.getImovel();

		// [FS0002] Verificar Situa��o do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Liga��o de �gua");
		}

		// [FS0003] Verificar a situa��o de �gua
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){

			throw new ControladorException("atencao.situacao_agua_imovel_invaliga", null, imovel.getLigacaoAguaSituacao().getDescricao()
							+ "");
		}

		/*
		 * ===================================================================================
		 */

		// [FS0014] Verificar tipo de registro de corte da liga��o de �gua

		if(ServicoTipo.CORTE_REGISTRO_MAGNETICO.contains(idServicoTipo) || ServicoTipo.CORTE_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.LIQ_NORMAL)){
					throw new ControladorException("atencao.imovel_sem_registro_magnetico_e_sem_tubere_corte_ligacao");
				}
			}
		}

		if(ServicoTipo.CORTE.contains(idServicoTipo) || ServicoTipo.CORTE_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.REG_MAGNET)){
					throw new ControladorException("atencao.imovel_com_registro_magnetico_incorreto_corte_ligacao");
				}
			}
		}

		if(ServicoTipo.CORTE.contains(idServicoTipo) || ServicoTipo.CORTE_REGISTRO_MAGNETICO.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.TUB_MAGNET)){
					throw new ControladorException("atencao.imovel_com_tubete_magnetico_incorreto_corte_ligacao");
				}
			}
		}
	}

	/**
	 * [UC0355] Efetuar Corte de Liga��o de �gua.
	 * 
	 * @author Leonardo Regis.
	 * @date 25/09/2006
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteLigacaoAgua(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		DadosEfetuacaoCorteLigacaoAguaHelper helper = integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper();

		this.getControladorImovel().verificarImovelControleConcorrencia(helper.getImovel());
		LigacaoAgua ligacaoAgua = helper.getImovel().getLigacaoAgua();
		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

		Usuario usuario = integracaoComercialHelper.getUsuarioLogado();
		helper.getOrdemServico().adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR,
						ligacaoAgua.getId(), ligacaoAgua.getId(), new UsuarioAcaoUsuarioHelper(
										integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		registradorOperacao.registrarOperacao(ligacaoAgua);
		getControladorTransacao().registrarTransacao(ligacaoAgua);
		try{
			// Efetuar Corte Liga��o de �gua
			repositorioLigacaoAgua.efetuarCorteLigacaoAgua(helper);
			if(!helper.isVeioEncerrarOS()){
				this.getControladorOrdemServico().atualizaOSGeral(helper.getOrdemServico(), true, false);
			}

			/*
			 * [OC790655][UC0355][SB0003]: Atualizar Dados Execu��o no Hist�rico de
			 * Manuten��o da Liga��o do Im�vel ao EfetuarCorteLigacaoAgua
			 */
			atualizarHistoricoManutencaoLigacao(helper.getOrdemServico(), HistoricoManutencaoLigacao.EFETUAR_CORTE_LIGACAO_AGUA);

			if(helper.getOrdemServico().getServicoTipo().getDebitoTipo() != null
							&& (helper.getOrdemServico().getServicoNaoCobrancaMotivo() == null || helper.getOrdemServico()
											.getServicoNaoCobrancaMotivo().getId().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Gerar D�bito OS
				this.getControladorOrdemServico().gerarDebitoOrdemServico(
								helper.getOrdemServico().getId(),
								helper.getOrdemServico().getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(helper.getOrdemServico().getValorAtual(), helper.getOrdemServico()
												.getPercentualCobranca().toString()), helper.getQtdeParcelas());
			}

			/**
			 * 8. Caso a ordem de servi�o esteja associada a documento de cobran�a(CBDO_ID diferente
			 * de nulo
			 * na tabela ORDEM_SERVI�O) para a ordem em quest�o:
			 */
			if(helper.getOrdemServico().getCobrancaDocumento() != null && helper.getOrdemServico().getCobrancaDocumento().getId() != null){

				/**
				 * 9. Gerar/acumular dados relativos aos documentos gerados(tabela
				 * COBRANCA_PRODUTIVIDADE)
				 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
				 * chave
				 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
				 * linha na
				 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
				 * contr�rio, inserir nova linha.
				 */
				getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(helper.getOrdemServico(), false, false,
								CobrancaDebitoSituacao.EXECUTADO);
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0369] Efetuar Corte Administrativo Liga��o de Agua
	 * 
	 * @author Thiago Ten�rio
	 * @date 30/06/2006
	 * @param helper
	 * @throws ControladorException
	 */

	public void efetuarCorteAdministrativoLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper, Usuario usuario)
					throws ControladorException{

		this.verificarLigacaoAguaControleConcorrencia(helper.getLigacaoAgua());

		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CORTE_ADMINISTRATIVO_LIGACAO_AGUA_EFETUAR,
						helper.getLigacaoAgua().getId(), helper.getLigacaoAgua().getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		registradorOperacao.registrarOperacao(helper.getLigacaoAgua());
		getControladorTransacao().registrarTransacao(helper.getLigacaoAgua());
		// [UC0107] -Fim- Registrar Transa��o

		try{
			// Efetuar Corte Liga��o de �gua
			repositorioLigacaoAgua.efetuarCorteAdministrativoLigacaoAgua(helper);
			if(!helper.isVeioEncerrarOS()){
				this.getControladorOrdemServico().atualizaOSGeral(helper.getOrdemServico(), false, false);
			}

			/*
			 * [OC790655][UC0369][SB0003]: Atualizar Dados Execu��o no Hist�rico de
			 * Manuten��o da Liga��o do Im�vel ao EfetuarCorteAdministrativoLigacaoAgua
			 */
			atualizarHistoricoManutencaoLigacao(helper.getOrdemServico(),
							HistoricoManutencaoLigacao.EFETUAR_CORTE_ADMINISTRATIVO_LIGACAO_AGUA);

			if(helper.getOrdemServico().getServicoTipo().getDebitoTipo() != null
							&& (helper.getOrdemServico().getServicoNaoCobrancaMotivo() == null || helper.getOrdemServico()
											.getServicoNaoCobrancaMotivo().getId().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Gerar D�bito OS
				this.getControladorOrdemServico().gerarDebitoOrdemServico(helper.getOrdemServico().getId(),
								helper.getOrdemServico().getServicoTipo().getDebitoTipo().getId(),
								helper.getOrdemServico().getValorAtual(), helper.getQtdeParcelas());
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0463] Efetuar Restabelecimento da Liga��o de �gua
	 * [SB0001] Atualizar Im�vel/Liga��o �gua
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaRestabelecimento(LigacaoAgua ligacaoAgua) throws ControladorException{

		try{
			repositorioLigacaoAgua.atualizarLigacaoAguaRestabelecimento(ligacaoAgua);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0357] Efetuar Religa��o de �gua
	 * [SB0001] Atualizar Im�vel/Liga��o �gua
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaReligacao(LigacaoAgua ligacaoAgua) throws ControladorException{

		try{
			repositorioLigacaoAgua.atualizarLigacaoAguaReligacao(ligacaoAgua);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * Recupera os par�metros necess�rios da Ligacao de �gua
	 * (id,dataCorte,dataSupressao)
	 * 
	 * @author S�vio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public LigacaoAgua recuperaParametrosLigacaoAgua(Integer idImovel) throws ControladorException{

		LigacaoAgua ligacaoAgua = null;
		if(idImovel != null){
			try{
				Object[] parmsLigacaoAgua = repositorioLigacaoAgua.pesquisarParmsLigacaoAgua(idImovel);
				if(parmsLigacaoAgua != null){
					ligacaoAgua = new LigacaoAgua();
					// id liga��o de agua
					if(parmsLigacaoAgua[0] != null){
						ligacaoAgua.setId((Integer) parmsLigacaoAgua[0]);
					}
					// data corte
					if(parmsLigacaoAgua[1] != null){
						ligacaoAgua.setDataCorte((Date) parmsLigacaoAgua[1]);
					}
					// data supressao
					if(parmsLigacaoAgua[2] != null){
						ligacaoAgua.setDataSupressao((Date) parmsLigacaoAgua[2]);
					}
				}

			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}
		return ligacaoAgua;
	}

	/**
	 * Pesquisa o id do hidrometro
	 * 
	 * @author S�vio Luiz
	 * @date 19/02/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdHidrometroInstalacaoHistorico(Integer idImovel) throws ControladorException{

		try{
			return repositorioLigacaoAgua.pesquisarIdHidrometroInstalacaoHistorico(idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0054] - Inserir Dados da Tarifa Social
	 * Recupera o consumo m�nimo fixado do Im�vel
	 * 
	 * @author Rafael Corr�a
	 * @date 04/0/2006
	 * @param idImovel
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoMinimoFixado(Integer idImovel) throws ControladorException{

		try{
			return repositorioLigacaoAgua.pesquisarConsumoMinimoFixado(idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection verificaExistenciaLigacaoAgua(Integer idImovel){

		Collection retorno = null;

		Integer idLigacaoAgua;
		try{
			retorno = repositorioLigacaoAgua.verificaExistenciaLigacaoAgua(idImovel);

		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}

	private ControladorTransacaoLocal getControladorTransacao(){

		ControladorTransacaoLocalHome localHome = null;
		ControladorTransacaoLocal local = null;
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorTransacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_TRANSACAO_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 19/05/2009
	 *       M�todo retorna uma LigacaoAgua, buscado por um Id
	 * @param id
	 *            - id da Liga��o [obrigat�rio]
	 * @return
	 * @throws ControladorException
	 */
	public LigacaoAgua pesquisarLigacaoAgua(Integer id) throws ControladorException{

		try{
			return repositorioLigacaoAgua.pesquisarLigacaoAgua(id);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0944] Efetuar Corte de Liga��o de �gua com Instala��o de Hidr�metro.
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteAguaComInstalacaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		DadosEfetuacaoCorteLigacaoAguaHelper helper = integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper();
		this.getControladorImovel().verificarImovelControleConcorrencia(helper.getImovel());

		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_INSTALACAO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(integracaoComercialHelper.getUsuarioLogado(),
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Usuario usuario = integracaoComercialHelper.getUsuarioLogado();

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_INSTALACAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		try{

			Imovel imovel = integracaoComercialHelper.getImovel();
			OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();
			String qtdParcelas = integracaoComercialHelper.getQtdParcelas();

			// LigacaoAgua ligacaoAgua = helper.getLigacaoAgua();
			LigacaoAgua ligacaoAgua = helper.getImovel().getLigacaoAgua();

			if(ligacaoAgua != null && imovel != null){

				// Instala o HidrometroInstalacaoHistorico
				// [SB0003] Gerar Hist�rico de Instala��o do Hidrometro
				IntegracaoComercialHelper integracaoComercialHelperAtualizado = getControladorAtendimentoPublico()
								.atualizarHidrometroInstalacaoHistoricoSemAtualizarOSEEfetuarCalculos(integracaoComercialHelper);

				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelperAtualizado
								.getHidrometroInstalacaoHistorico();

				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				helper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				// [SB0001] Atualizar Im�vel/Liga��o de �gua
				ligacaoAgua.setUltimaAlteracao(new Date());

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				helper.setLigacaoAgua(ligacaoAgua);

				this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

				helper.getImovel().setLigacaoAgua(ligacaoAgua);

				// Efetuar Corte Liga��o de �gua
				repositorioLigacaoAgua.efetuarCorteLigacaoAgua(helper);
			}

			// [SB0002] Atualizar Ordem de Servi�o
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			// if(!integracaoComercialHelper.isVeioEncerrarOS() &&
			// ordemServico.getServicoTipo().getDebitoTipo() != null){
			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, true, false);
			}

			/*
			 * [OC790655][UC0944][SB0005]: Atualizar Dados Execu��o no Hist�rico de
			 * Manuten��o da Liga��o do Im�vel
			 */
			if(ConstantesSistema.SIM.equals(ordemServico.getServicoTipo().getIndicadorGerarHistoricoImovel())){
				atualizarHistoricoManutencaoLigacao(ordemServico, HistoricoManutencaoLigacao.EFETUAR_CORTE_COM_INSTALACAO_HIDROMETRO);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(),
								new Integer(qtdParcelas)/*
														 * ,
														 * 
														 * ordemServico.getPercentualCobranca().toString
														 * (),
														 * 
														 * integracaoComercialHelper.getUsuarioLogado
														 * ()
														 */);
			}

			/**
			 * 9. Caso a ordem de servi�o esteja associada a documento de cobran�a(CBDO_ID diferente
			 * de nulo
			 * na tabela ORDEM_SERVI�O) para a ordem em quest�o:
			 */
			if(helper.getOrdemServico().getCobrancaDocumento() != null && helper.getOrdemServico().getCobrancaDocumento().getId() != null){

				/**
				 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
				 * COBRANCA_PRODUTIVIDADE)
				 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
				 * chave
				 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
				 * linha na
				 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
				 * contr�rio, inserir nova linha.
				 */
				getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(helper.getOrdemServico(), false, false,
								CobrancaDebitoSituacao.EXECUTADO);
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author jns
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void efetuarCorteAguaComSubstituicaoHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		DadosEfetuacaoCorteLigacaoAguaHelper helper = integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper();
		this.getControladorImovel().verificarImovelControleConcorrencia(helper.getImovel());

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_SUBSTITUICAO_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(integracaoComercialHelper.getUsuarioLogado(),
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_SUBSTITUICAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Imovel imovel = integracaoComercialHelper.getImovel();
		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();

		try{

			if(ligacaoAgua != null && imovel != null){

				// Efetuar substitui��o de hidr�metro
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();
				String matriculaImovel = integracaoComercialHelper.getMatriculaImovel();
				HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = integracaoComercialHelper
								.getHidrometroSubstituicaoHistorico();
				String situacaoHidrometroSubstituido = integracaoComercialHelper.getSituacaoHidrometroSubstituido();
				Integer localArmazenagemHidrometro = integracaoComercialHelper.getLocalArmazenagemHidrometro();

				// getControladorAtendimentoPublico().validacaoSubstituicaoHidrometro(matriculaImovel,hidrometroInstalacaoHistorico.getHidrometro().getNumero(),situacaoHidrometroSubstituido);

				// [SB0001] Atualizar o hist�rico da instala��o do hidr�metro
				// substituido
				repositorioAtendimentoPublico.atualizarHidrometroInstalacoHistorico(hidrometroSubstituicaoHistorico);

				// [SB0002] Gerar Hist�rico de instala��o do hidr�metro
				hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("2"));
				Integer id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);
				hidrometroInstalacaoHistorico.setId(id);

				// [SB0003]Atualizar Im�vel/Liga��o de �gua

				// Caso o tipo de medi��o seja igual a Liga��o de �gua, atualiza as
				// colunas da tabela LIGACAO_AGUA
				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
					// Caso o tipo de medi��o seja igual a Po�o, atualiza as colunas
					// da tabela POCO
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				// [SB004]Atualizar situa��o de hidr�metro na tabela HIDROMETRO
				Integer situacaoHidrometro = HidrometroSituacao.INSTALADO;
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				// [SB005]Atualizar situa��o do hidr�metro substituido na tabela
				// HIDROMETRO
				situacaoHidrometro = new Integer(situacaoHidrometroSubstituido);
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				repositorioAtendimentoPublico.atualizarLocalArmazanagemHidrometro(hidrometroSubstituicaoHistorico.getHidrometro().getId(),
								localArmazenagemHidrometro);

				// *******************************************************************
				// Realiza��o do corte de �gua

				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				helper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				// [SB0001] Atualizar Im�vel/Liga��o de �gua
				ligacaoAgua.setUltimaAlteracao(new Date());

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				helper.setLigacaoAgua(ligacaoAgua);

				this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

				helper.getImovel().setLigacaoAgua(ligacaoAgua);

				// Efetuar Corte Liga��o de �gua
				repositorioLigacaoAgua.efetuarCorteLigacaoAgua(helper);
				// *******************************************************************
			}

			OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

			// [SB0002] Atualizar Ordem de Servi�o
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			// if(!integracaoComercialHelper.isVeioEncerrarOS() &&
			// ordemServico.getServicoTipo().getDebitoTipo() != null){
			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, true, false);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){
				getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(),
								new Integer(integracaoComercialHelper.getQtdParcelas())/*
																						 * ,
																						 * ordemServico
																						 * .
																						 * getPercentualCobranca
																						 * (
																						 * 
																						 * ).toString
																						 * (),
																						 * integracaoComercialHelper
																						 * .
																						 * getUsuarioLogado
																						 * ()
																						 */);
			}

			/**
			 * 9. Caso a ordem de servi�o esteja associada a documento de cobran�a(CBDO_ID diferente
			 * de nulo
			 * na tabela ORDEM_SERVI�O) para a ordem em quest�o:
			 */
			if(helper.getOrdemServico().getCobrancaDocumento() != null && helper.getOrdemServico().getCobrancaDocumento().getId() != null){

				/**
				 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
				 * COBRANCA_PRODUTIVIDADE)
				 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
				 * chave
				 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
				 * linha na
				 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
				 * contr�rio, inserir nova linha.
				 */
				getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(helper.getOrdemServico(), false, false,
								CobrancaDebitoSituacao.EXECUTADO);
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @author Eduardo Oliveira
	 * @param integracaoComercialHelper
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void efetuarCorteAguaComRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper) throws ControladorException{

		DadosEfetuacaoCorteLigacaoAguaHelper helper = integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper();
		this.getControladorImovel().verificarImovelControleConcorrencia(helper.getImovel());

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_RETIRADA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(integracaoComercialHelper.getUsuarioLogado(),
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_SUBSTITUICAO_HIDROMETRO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Imovel imovel = integracaoComercialHelper.getImovel();
		LigacaoAgua ligacaoAgua = integracaoComercialHelper.getLigacaoAgua();

		try{

			if(ligacaoAgua != null && imovel != null){

				// Efetuar substitui��o de hidr�metro
				HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

				HidrometroInstalacaoHistorico hidrometroRetiradoHistorico = integracaoComercialHelper
								.getHidrometroSubstituicaoHistorico();
				String situacaoHidrometroSubstituido = integracaoComercialHelper.getSituacaoHidrometroSubstituido();
				Integer localArmazenagemHidrometro = integracaoComercialHelper.getLocalArmazenagemHidrometro();

				// [SB0001] Atualizar Im�vel/Liga��o de �gua/Hist�rico de Instala��o de Hidr�metro
				repositorioAtendimentoPublico.atualizarHidrometroInstalacoHistorico(hidrometroRetiradoHistorico);

				// [SB0002] Gerar Hist�rico de instala��o do hidr�metro
				hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("2"));
				Integer id = (Integer) getControladorUtil().inserir(hidrometroInstalacaoHistorico);
				hidrometroInstalacaoHistorico.setId(id);

				// [SB0003]Atualizar Im�vel/Liga��o de �gua

				// Caso o tipo de medi��o seja igual a Liga��o de �gua, atualiza as
				// colunas da tabela LIGACAO_AGUA
				if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
					repositorioAtendimentoPublico.atualizarHidrometroInstalacaoHistoricoLigacaoAgua(hidrometroInstalacaoHistorico
									.getLigacaoAgua().getId(), id);
					// Caso o tipo de medi��o seja igual a Po�o, atualiza as colunas
					// da tabela POCO
				}else if(hidrometroInstalacaoHistorico.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){
					repositorioAtendimentoPublico.atualizarHidrometroIntalacaoHistoricoImovel(hidrometroInstalacaoHistorico.getImovel()
									.getId(), id);
				}

				// [SB004]Atualizar situa��o de hidr�metro na tabela HIDROMETRO
				Integer situacaoHidrometro = HidrometroSituacao.DISPONIVEL;
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroInstalacaoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				// [SB005]Atualizar situa��o do hidr�metro substituido na tabela
				// HIDROMETRO
				situacaoHidrometro = new Integer(situacaoHidrometroSubstituido);
				repositorioAtendimentoPublico.atualizarSituacaoHidrometro(hidrometroRetiradoHistorico.getHidrometro().getId(),
								situacaoHidrometro);

				repositorioAtendimentoPublico.atualizarLocalArmazanagemHidrometro(hidrometroRetiradoHistorico.getHidrometro().getId(),
								localArmazenagemHidrometro);

				// *******************************************************************
				// Realiza��o do corte de �gua

				// Atualiza o hidrometro
				ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				helper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

				// [SB0001] Atualizar Im�vel/Liga��o de �gua
				ligacaoAgua.setUltimaAlteracao(new Date());

				// regitrando operacao
				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				helper.setLigacaoAgua(ligacaoAgua);

				this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

				helper.getImovel().setLigacaoAgua(ligacaoAgua);

				// Efetuar Corte Liga��o de �gua
				repositorioLigacaoAgua.efetuarCorteLigacaoAgua(helper);
				// *******************************************************************
			}

			OrdemServico ordemServico = integracaoComercialHelper.getOrdemServico();

			// [SB0002] Atualizar Ordem de Servi�o
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.adicionarUsuario(integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ordemServico);

			if(!integracaoComercialHelper.isVeioEncerrarOS()){

				this.getControladorOrdemServico().verificarOrdemServicoControleConcorrencia(ordemServico);
				getControladorOrdemServico().atualizaOSGeral(ordemServico, true, false);
			}

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){
				getControladorOrdemServico().gerarDebitoOrdemServico(ordemServico.getId(),
								ordemServico.getServicoTipo().getDebitoTipo().getId(), ordemServico.getValorAtual(),
								new Integer(integracaoComercialHelper.getQtdParcelas()));
			}

			/**
			 * 9. Caso a ordem de servi�o esteja associada a documento de cobran�a(CBDO_ID diferente
			 * de nulo
			 * na tabela ORDEM_SERVI�O) para a ordem em quest�o:
			 */
			if(helper.getOrdemServico().getCobrancaDocumento() != null && helper.getOrdemServico().getCobrancaDocumento().getId() != null){

				/**
				 * 10. Gerar/acumular dados relativos aos documentos gerados(tabela
				 * COBRANCA_PRODUTIVIDADE)
				 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
				 * chave
				 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
				 * linha na
				 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
				 * contr�rio, inserir nova linha.
				 */
				getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(helper.getOrdemServico(), false, false,
								CobrancaDebitoSituacao.EXECUTADO);
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @author isilva
	 * @date 24/05/2011
	 *       Obtem Dados do �ltimo corte
	 * @param idImovel
	 *            Identificador do Im�vel
	 * @return
	 * @throws ControladorException
	 */
	public DadosUltimoCorteHelper obterDadosUltimoCorte(Integer idImovel) throws ControladorException{

		DadosUltimoCorteHelper retorno = null;
		try{
			retorno = this.repositorioLigacaoAgua.obterDadosUltimoCorte(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * M�todo atualizarHistoricoManutencaoLicacao
	 * <p>
	 * Esse m�todo implementa o processo de atualizacao de historico de manutencao de ligacao de
	 * agua.
	 * </p>
	 * RASTREIO: [OC790655][UC0355][SB0003];
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(OrdemServico ordemServico, short fluxo) throws ControladorException{

		LOGGER.info("Atualizando HistoricoManutencaoLigacao para a OS[" + ordemServico.getId() + "] com ServicoTipo["
						+ ordemServico.getServicoTipo().getIdDescricao() + "] atravez do fluxo[" + fluxo + "].");

		LigacaoAgua ligacaoAgua = null;
		HistoricoManutencaoLigacao historicoManutencaoLigacao = consultarHistoricoManutencaoLigacao(ordemServico);

		if(historicoManutencaoLigacao == null){
			LOGGER.info("Nenum registro para a OS[" + ordemServico.getId() + "] foi encontrado.");
		}else{
			Usuario usr = null;
			switch(fluxo){
				case HistoricoManutencaoLigacao.EFETUAR_CORTE_LIGACAO_AGUA:
					ligacaoAgua = carregarLigacaoAgua(ordemServico);

					if(ligacaoAgua.getHidrometroInstalacaoHistorico() != null){

						historicoManutencaoLigacao.setNumeroLeituraExecucao(ligacaoAgua.getHidrometroInstalacaoHistorico()
										.getNumeroLeituraCorte());
					}

					if(ligacaoAgua.getCorteTipo() != null){
						historicoManutencaoLigacao.setCorteTipo(ligacaoAgua.getCorteTipo());
					}

					usr = recuperarUsuarioAcao(ordemServico, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;

				case HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA:
					usr = recuperarUsuarioAcao(ordemServico, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA:
					usr = recuperarUsuarioAcao(ordemServico, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_SUPRESSAO_LIGACAO_AGUA:
					ligacaoAgua = carregarLigacaoAgua(ordemServico);
					if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null){
						historicoManutencaoLigacao.setNumeroLeituraExecucao(ligacaoAgua.getHidrometroInstalacaoHistorico()
										.getNumeroLeituraSupressao());
					}
					usr = recuperarUsuarioAcao(ordemServico, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_CORTE_ADMINISTRATIVO_LIGACAO_AGUA:
					ligacaoAgua = carregarLigacaoAgua(ordemServico);
					if(ligacaoAgua.getCorteTipo() != null){
						historicoManutencaoLigacao.setCorteTipo(ligacaoAgua.getCorteTipo());
					}

					usr = recuperarUsuarioAcao(ordemServico, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO:
					historicoManutencaoLigacao.setCodigoSituacaoOS(ordemServico.getSituacao());
					historicoManutencaoLigacao.setAtendimentoMotivoEncerramento(ordemServico.getAtendimentoMotivoEncerramento());
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioEncerramento(usr);
					}
					historicoManutencaoLigacao.setDescricaoParecer(ordemServico.getDescricaoParecerEncerramento());
					break;
				case HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO_ATUALIZAR_SITUACAO:
					historicoManutencaoLigacao.setCodigoSituacaoOS(ordemServico.getSituacao());
					break;

				case HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA_COM_INSTALACAO_HIDROMETRO:
					ligacaoAgua = carregarLigacaoAgua(ordemServico);
					historicoManutencaoLigacao.setNumeroLeituraExecucao(ligacaoAgua.getHidrometroInstalacaoHistorico()
									.getNumeroLeituraInstalacao());
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO:
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_CORTE_COM_INSTALACAO_HIDROMETRO:
					ligacaoAgua = carregarLigacaoAgua(ordemServico);
					if(ligacaoAgua.getCorteTipo() != null){
						historicoManutencaoLigacao.setCorteTipo(ligacaoAgua.getCorteTipo());
					}
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_RELIGACAO_AGUA_COM_SUBSTITUICAO_HIDROMETRO:
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
				case HistoricoManutencaoLigacao.EFETUAR_RESTABELECIMENTO_AGUA_COM_SUBSTITUICAO_HIDROMETRO:
					usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ENCERRAR);
					if(usr != null){
						historicoManutencaoLigacao.setUsuarioExecucao(usr);
					}
					break;
			}
			historicoManutencaoLigacao.setUltimaAlteracao(Calendar.getInstance().getTime());
			getControladorUtil().atualizar(historicoManutencaoLigacao);
		}
	}

	private LigacaoAgua carregarLigacaoAgua(OrdemServico ordemServico) throws ControladorException{

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, ordemServico.getImovel().getLigacaoAgua().getId()));
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade(FiltroLigacaoAgua.CORTE_TIPO);

		LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroLigacaoAgua,
						LigacaoAgua.class.getName()));

		// Consulta o objeto HidroometroInstalacaoHistorico
		FiltroHidrometroInstalacaoHistorico filtroHidrometroInst = new FiltroHidrometroInstalacaoHistorico();
		filtroHidrometroInst.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.LIGACAO_AGUA_ID, ligacaoAgua
						.getId()));

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = (HidrometroInstalacaoHistorico) Util
						.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroHidrometroInst,
										HidrometroInstalacaoHistorico.class.getName()));

		ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

		return ligacaoAgua;
	}

	private HistoricoManutencaoLigacao consultarHistoricoManutencaoLigacao(OrdemServico ordemServico) throws ControladorException{

		FiltroHistoricoManutencaoLigacao filtro = new FiltroHistoricoManutencaoLigacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.ORDEM_SERVICO_ID, ordemServico.getId()));
		Collection<Object> historicos = getControladorUtil().pesquisar(filtro, HistoricoManutencaoLigacao.class.getName());
		HistoricoManutencaoLigacao historicoManutencaoLigacao = (HistoricoManutencaoLigacao) Util.retonarObjetoDeColecao(historicos);
		return historicoManutencaoLigacao;
	}

	private Usuario recuperarUsuarioAcao(OrdemServico ordemServico, UsuarioAcao usuarioAcao){

		Usuario usrExec = null;
		for(UsuarioAcaoUsuarioHelper usuarioHelper : (Collection<UsuarioAcaoUsuarioHelper>) ordemServico.getUsuarioAcaoUsuarioHelp()){
			if(usuarioAcao.equals(usuarioHelper.getUsuarioAcao())){
				usrExec = usuarioHelper.getUsuario();
			}
		}
		return usrExec;
	}

	/**
	 * M�todo criarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa processo de criar��o de um {@link HistoricoManutencaoLigacao} tendo
	 * como base em {@link OrdemServico}.
	 * </p>
	 * RASTREIO: [OC790655][UC0430][SB0004.7.1]
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void criarHistoricoManutencaoLigacao(OrdemServico ordemServico) throws ControladorException{

		// [FS0011] - Verificar exist�ncia do Im�vel na Ordem de Servi�o
		if(ordemServico.getImovel() == null){

			throw new ControladorException("atencao.imovel_obigatorio_geracao_ordem_servico", null, ordemServico.getServicoTipo()
							.getDescricao());
		}

		HistoricoManutencaoLigacao historico = new HistoricoManutencaoLigacao();
		Imovel imovel = ordemServico.getImovel();
		imovel = getControladorImovel().consultarImovel(imovel.getId());
		SetorComercial setorComercial = imovel == null ? null : imovel.getSetorComercial();
		Localidade localidade = imovel == null ? null : imovel.getLocalidade();
		ImovelPerfil imovelPerfil = imovel == null ? null : imovel.getImovelPerfil();
		Integer codigo = setorComercial == null ? null : setorComercial.getCodigo();

		historico.setImovel(imovel);
		historico.setDataEmissao(ordemServico.getDataGeracao());
		historico.setLocalidade(localidade);
		historico.setSetorComercial(setorComercial);
		historico.setCodigoSetorComercial(codigo);
		historico.setImovelPerfil(imovelPerfil);
		historico.setDataInclusao(Calendar.getInstance().getTime());
		historico.setUltimaAlteracao(Calendar.getInstance().getTime());
		historico.setOrdemServico(ordemServico);
		historico.setCobrancaDocumentoOrigemServico(ordemServico.getCobrancaDocumento());
		historico.setRegistroAtendimentoOrigemServico(ordemServico.getRegistroAtendimento());
		historico.setServicoTipo(ordemServico.getServicoTipo());
		historico.setCodigoSituacaoOS(ordemServico.getSituacao());

		Usuario usr = recuperarUsuarioUnidadeAtendimento(ordemServico, AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
		historico.setUsuarioGeracao(usr);

		getControladorUtil().inserir(historico);

	}

	private Usuario recuperarUsuarioUnidadeAtendimento(OrdemServico ordemServico, Integer tipoAtendimento) throws ControladorException{

		FiltroOrdemServicoUnidade filtro = new FiltroOrdemServicoUnidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ORDEM_SERVICO_ID, ordemServico.getId()));
		filtro.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ATENDIMENTO_RELACAO_TIPO_ID, tipoAtendimento));
		filtro.adicionarCaminhoParaCarregamentoEntidade("usuario");
		Collection ordensServicoUnidade = getControladorUtil().pesquisar(filtro, OrdemServicoUnidade.class.getName());
		OrdemServicoUnidade ordemServicoUnidade = (OrdemServicoUnidade) Util.retonarObjetoDeColecao(ordensServicoUnidade);
		return ordemServicoUnidade == null ? null : ordemServicoUnidade.getUsuario();
	}

	/**
	 * M�todo atualizarHistoricoManutencaoLicacao
	 * <p>
	 * Esse m�todo implementa o processo de atualizacao de historico de manutencao de ligacao de
	 * agua.
	 * </p>
	 * RASTREIO: [OC790655][UC3044][SB0001.1.3];
	 * 
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(CobrancaDocumento cobrancaDocumento, short fluxo) throws ControladorException{

		LOGGER.info("Atualizando HistoricoManutencaoLigacao para O DocCobran�a [" + cobrancaDocumento.getId() + "] com TipoDoc["
						+ cobrancaDocumento.getDocumentoTipo().getId() + "] atravez do fluxo[" + fluxo + "].");
		Collection<Object> historicos = consultarHistoricoManutencaoLigacao(cobrancaDocumento);
		HistoricoManutencaoLigacao historicoManutencaoLigacao = (HistoricoManutencaoLigacao) Util.retonarObjetoDeColecao(historicos);
		if(historicoManutencaoLigacao == null){
			LOGGER.info("Nenum registro para a DocCobranca[" + cobrancaDocumento.getId() + "] foi encontrado.");
		}else{
			switch(fluxo){
				case HistoricoManutencaoLigacao.INFORMAR_ENTREGA_DEVOLUCAO_DOCUMENTOS:
					historicoManutencaoLigacao.setCobrancaAcaoSituacao(cobrancaDocumento.getCobrancaAcaoSituacao());
					historicoManutencaoLigacao.setDataSituacaoDocumento(cobrancaDocumento.getDataSituacaoAcao());
					historicoManutencaoLigacao.setDescricaoParecer(cobrancaDocumento.getDescricaoParecer());
					historicoManutencaoLigacao.setMotivoNaoEntregaDocumento(cobrancaDocumento.getMotivoNaoEntregaDocumento());
					break;
				case HistoricoManutencaoLigacao.GERAR_RESUMO_ACOES_COBRANCA_EVENTUAIS:
					historicoManutencaoLigacao.setCobrancaAcaoSituacao(cobrancaDocumento.getCobrancaAcaoSituacao());
					historicoManutencaoLigacao.setDataSituacaoDocumento(cobrancaDocumento.getDataSituacaoAcao());
					historicoManutencaoLigacao.setCobrancaDebitoSituacao(cobrancaDocumento.getCobrancaDebitoSituacao());
					historicoManutencaoLigacao.setDataSituacaoDebito(cobrancaDocumento.getDataSituacaoDebito());
					break;
			}
			historicoManutencaoLigacao.setUltimaAlteracao(Calendar.getInstance().getTime());
			getControladorUtil().atualizar(historicoManutencaoLigacao);
		}

	}

	private Collection<Object> consultarHistoricoManutencaoLigacao(CobrancaDocumento cobrancaDocumento) throws ControladorException{

		FiltroHistoricoManutencaoLigacao filtro = new FiltroHistoricoManutencaoLigacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.COBRANCA_DOCUMENTO_ID, cobrancaDocumento.getId()));
		Collection<Object> historicos = getControladorUtil().pesquisar(filtro, HistoricoManutencaoLigacao.class.getName());
		return historicos;
	}

	/**
	 * M�todo consultarHistoricoAcaoCobrancaPredecessora
	 * <p>
	 * Esse m�todo implementa consulta para recuperar o {@link HistoricoManutencaoLigacao} mais
	 * recente do {@link Imovel} que recebeu o {@link CobrancaDocumento} e que tenha o mesmo
	 * {@link DocumentoTipo} da sua {@link CobrancaAcao} Predecessora./p> RASTREIO:
	 * [OC790655][UC3052][SB0001.4.2.1]
	 * 
	 * @param documentoCobranca
	 * @param cobrancaAcaoPredecessora
	 * @return
	 * @throws ControladorException
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public HistoricoManutencaoLigacao consultarHistoricoAcaoCobranca(CobrancaDocumento documentoCobranca,
					CobrancaAcao cobrancaAcaoPredecessora) throws ControladorException{

		LOGGER.info("Consultando HistoricoManutencaoLigacao da CobracaAcao Predecessora[" + cobrancaAcaoPredecessora.getId() + "]");
		FiltroHistoricoManutencaoLigacao filtro = new FiltroHistoricoManutencaoLigacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.IMOVEL_ID, documentoCobranca.getImovel().getId()));

		filtro.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.DOCUMENTO_TIPO_ID, cobrancaAcaoPredecessora
						.getDocumentoTipo().getId()));
		filtro.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.COBRANCA_ACAO_SITUACAO_ID,
						CobrancaAcaoSituacao.ENTREGUE));
		filtro.setCampoOrderBy(FiltroHistoricoManutencaoLigacao.DATA_INCLUSAO);
		Collection<Object> historicos = getControladorUtil().pesquisar(filtro, HistoricoManutencaoLigacao.class.getName());

		return (HistoricoManutencaoLigacao) (Util.isVazioOrNulo(historicos) ? null : (historicos.toArray())[historicos.size() - 1]);
	}

	/**
	 * M�todo atualizarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa a atualizacao do {@link HistoricoManutencaoLigacao} da
	 * {@link CobrancaAcao} predecessora
	 * </p>
	 * RASTREIO: [OC790655][UC3052][SB0001.4.2.1]
	 * 
	 * @param historicoAcaoCobrancaPredecessora
	 * @param ordemServico
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void atualizarHistoricoManutencaoLigacao(HistoricoManutencaoLigacao historicoAcaoCobrancaPredecessora, OrdemServico ordemServico)
					throws ControladorException{

		HistoricoManutencaoLigacao historico = consultarHistoricoManutencaoLigacao(ordemServico);

		historicoAcaoCobrancaPredecessora.setIdAssociado(historico.getId());
		historicoAcaoCobrancaPredecessora.setUltimaAlteracao(Calendar.getInstance().getTime());
		getControladorUtil().atualizar(historicoAcaoCobrancaPredecessora);
	}

	/**
	 * M�todo criarHistoricoManutencaoLigacao
	 * <p>
	 * Esse m�todo implementa fluxo de cria��o de {@link HistoricoManutencaoLigacao} com base no
	 * {@link CobrancaDocumento}
	 * </p>
	 * RASTREIO: [OC790655][UC3052][SB0001.5]
	 * 
	 * @param documentoCobranca
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public void criarHistoricoManutencaoLigacao(CobrancaDocumento documentoCobranca) throws ControladorException{

		Imovel imovel = documentoCobranca.getImovel();
		imovel = getControladorImovel().consultarImovel(imovel.getId());

		HistoricoManutencaoLigacao historico = new HistoricoManutencaoLigacao();
		historico.setImovel(imovel);
		historico.setDataEmissao(documentoCobranca.getEmissao());
		historico.setLocalidade(documentoCobranca.getLocalidade());
		historico.setSetorComercial(imovel.getSetorComercial());
		historico.setCodigoSetorComercial(documentoCobranca.getCodigoSetorComercial());
		historico.setImovelPerfil(documentoCobranca.getImovelPerfil());
		historico.setDataInclusao(Calendar.getInstance().getTime());
		historico.setUltimaAlteracao(Calendar.getInstance().getTime());
		historico.setCobrancaDocumento(documentoCobranca);
		historico.setDocumentoEmissaoForma(documentoCobranca.getDocumentoEmissaoForma());
		historico.setDocumentoTipo(documentoCobranca.getDocumentoTipo());
		historico.setCobrancaDebitoSituacao(documentoCobranca.getCobrancaDebitoSituacao());
		historico.setDataSituacaoDebito(documentoCobranca.getDataSituacaoDebito());
		historico.setCobrancaAcaoSituacao(documentoCobranca.getCobrancaAcaoSituacao());
		historico.setDataSituacaoDocumento(documentoCobranca.getEmissao());
		historico.setValorDebito(documentoCobranca.getValorDocumento());

		getControladorUtil().inserir(historico);
	}

	/**
	 * [UC3076] Consultar o Hist�rico da Manuten��o da Liga��o de �gua
	 * 
	 * @author Luciano Galvao
	 * @date 19/09/2012
	 * @throws ControladorException
	 */
	public List<HistoricoManutencaoLigacaoHelper> consultarHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper,
					Integer numeroPagina) throws ControladorException{

		List<HistoricoManutencaoLigacaoHelper> retorno = new ArrayList<HistoricoManutencaoLigacaoHelper>();
		HistoricoManutencaoLigacaoHelper historicoManutencaoLigacao = null;
		ObterDescricaoSituacaoOSHelper descricaoOSHelper = null;
		Collection<AtendimentoMotivoEncerramento> motivosEmExecucao = null;

		try{
			Collection<Object[]> retornoConsulta = this.repositorioLigacaoAgua.consultarHistoricoManutencaoLigacao(helper, numeroPagina);

			if(!Util.isVazioOrNulo(retornoConsulta)){
				for(Object[] itemRetorno : retornoConsulta){

					Integer imovelId = (Integer) itemRetorno[0]; // IMOV_ID
					Date dataEmissao = (Date) itemRetorno[1]; // HMLI_DTEMISSAO
					String docTipoDescricaoAbreviada = (String) itemRetorno[2]; // DOTP_DSABREVIADO
					String servicoTipoDescricaoAbreviada = (String) itemRetorno[3]; // SVTP_DSABREVIADO
					Integer documentoCobrancaId = (Integer) itemRetorno[4]; // CBDO_ID
					Integer situacaoDocumento = (Integer) itemRetorno[5]; // CAST_ID
					Date dataSituacaoDocumento = (Date) itemRetorno[6]; // HMLI_DTSITUACAODOCUMENTO
					Integer ordemServicoIdAssociado = (Integer) itemRetorno[7]; // ORSE_ID_ASSOCIADO
					Integer ordemServicoId = (Integer) itemRetorno[8]; // ORSE_ID
					Short codigoSituacaoOS = (Short) itemRetorno[9]; // HMLI_CDSITUACAOOS
					String descricaoDocumentoTipo = (String) itemRetorno[10]; // DOTP_DSDOCUMENTOTIPO
					String descricaoSituacaoAcao = (String) itemRetorno[11]; // CAST_DSSITUACAOACAO
					String descricaoServicoTipoAssociado = (String) itemRetorno[12]; // SVTP_DSSVTIPO_ASSOCIADO
					Date dataEmissaoAssociado = (Date) itemRetorno[13]; // HMLI_DTEMISSAO_ASSOCIADO
					String descricaoServicoTipo = (String) itemRetorno[14]; // SVTP_DSSERVICOTIPO
					Integer numeroLeituraExecucao = (Integer) itemRetorno[15]; // HMLI_NNLEITURAEXECUCAO
					String descricaoCorteTipo = (String) itemRetorno[16]; // COTP_DSCORTETIPO
					String loginUsuario = (String) itemRetorno[17]; // USUR_NMLOGIN
					Integer atendimentoMotivoEncerramentoId = (Integer) itemRetorno[18]; // AMEN_ID
					Integer numeroLeituraExecucaoAssociado = (Integer) itemRetorno[19]; // HMLI_NNLEITEXECUCAO_ASSOCIADO
					String descricaoCorteTipoAssociado = (String) itemRetorno[20]; // COTP_DSCORTETIPO_ASSOCIADO
					String loginUsuarioAssociado = (String) itemRetorno[21]; // USUR_NMLOGIN_ASSOC
					Integer atendimentoMotivoEncerramentoIdAssociado = (Integer) itemRetorno[22]; // AMEN_ID_ASSOCIADO

					historicoManutencaoLigacao = new HistoricoManutencaoLigacaoHelper();

					if(imovelId != null){
						historicoManutencaoLigacao.setImovelId(imovelId.toString());
					}

					if(dataEmissao != null){
						historicoManutencaoLigacao.setDataEmissao(Util.formatarData(dataEmissao));
					}

					if(documentoCobrancaId != null){
						historicoManutencaoLigacao.setDocumentoCobranca(documentoCobrancaId.toString());
						historicoManutencaoLigacao.setDocOS(docTipoDescricaoAbreviada);
					}else if(ordemServicoId != null){
						historicoManutencaoLigacao.setOrdemServico(ordemServicoId.toString());
						historicoManutencaoLigacao.setDocOS(servicoTipoDescricaoAbreviada);
					}

					if(situacaoDocumento != null){
						historicoManutencaoLigacao.setSituacaoDocumento(situacaoDocumento.toString());
					}

					if(dataSituacaoDocumento != null){
						historicoManutencaoLigacao.setDataSituacaoDocumento(Util.formatarData(dataSituacaoDocumento));
					}

					if(ordemServicoIdAssociado != null){
						historicoManutencaoLigacao.setServicoAssociado(ordemServicoIdAssociado.toString());
					}

					if(ordemServicoId != null){
						historicoManutencaoLigacao.setOrdemServico(ordemServicoId.toString());
					}

					if(codigoSituacaoOS != null){
						historicoManutencaoLigacao.setSituacaoOS(codigoSituacaoOS.toString());
					}

					if(codigoSituacaoOS != null){
						historicoManutencaoLigacao.setSituacaoOS(codigoSituacaoOS.toString());
					}

					if(descricaoDocumentoTipo != null){
						historicoManutencaoLigacao.setDescricaoDocumentoTipo(descricaoDocumentoTipo);
					}

					if(descricaoSituacaoAcao != null){
						historicoManutencaoLigacao.setDescricaoSituacaoAcao(descricaoSituacaoAcao);
					}

					if(descricaoServicoTipoAssociado != null){
						historicoManutencaoLigacao.setDescricaoServicoTipoItemAssociado(descricaoServicoTipoAssociado);
					}

					if(dataEmissaoAssociado != null){
						historicoManutencaoLigacao.setDataEmissaoItemAssociado(Util.formatarData(dataEmissaoAssociado));
					}

					if(descricaoServicoTipo != null){
						historicoManutencaoLigacao.setDescricaoServicoTipo(descricaoServicoTipo);
					}

					if(ordemServicoId != null){

						descricaoOSHelper = getControladorOrdemServico().obterDescricaoSituacaoOS(ordemServicoId);

						if((descricaoOSHelper != null) && (descricaoOSHelper.getDescricaoSituacao() != null)){
							historicoManutencaoLigacao.setDescricaoSituacaoOS(descricaoOSHelper.getDescricaoSituacao());
						}

						// Se a descri��o da OS � "Encerrada"
						if(historicoManutencaoLigacao.getDescricaoSituacaoOS() != null
										&& historicoManutencaoLigacao.getDescricaoSituacaoOS().equals(
														OrdemServico.SITUACAO_DESCRICAO_ENCERRADO)){

							FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
							filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(
											FiltroAtendimentoMotivoEncerramento.INDICADOR_EXECUCAO, ConstantesSistema.SIM));

							motivosEmExecucao = getControladorUtil().pesquisar(filtroAtendimentoMotivoEncerramento,
											AtendimentoMotivoEncerramento.class.getName());

							if(!Util.isVazioOrNulo(motivosEmExecucao)){
								for(AtendimentoMotivoEncerramento atendimentoMotivoEncerramento : motivosEmExecucao){

									// Se o motivo de encerramento corresponda � conclus�o do
									// servico,
									// exibir descri��o do tipo de corte e login do usu�rio que
									// informou
									// a execu��o da OS
									if(atendimentoMotivoEncerramentoId != null
													&& atendimentoMotivoEncerramentoId.equals(atendimentoMotivoEncerramento.getId())){

										if(numeroLeituraExecucao != null){
											historicoManutencaoLigacao.setNumeroLeituraExecucao(numeroLeituraExecucao.toString());
										}

										if(descricaoCorteTipo != null){
											historicoManutencaoLigacao.setDescricaoCorteTipo(descricaoCorteTipo);
										}

										if(loginUsuario != null){
											historicoManutencaoLigacao.setLoginUsuario(loginUsuario);
										}

										break;
									}
								}
							}
						}
					}

					if(ordemServicoIdAssociado != null){

						descricaoOSHelper = getControladorOrdemServico().obterDescricaoSituacaoOS(ordemServicoIdAssociado);

						if((descricaoOSHelper != null) && (descricaoOSHelper.getDescricaoSituacao() != null)){
							historicoManutencaoLigacao.setDescricaoSituacaoOSAssociado(descricaoOSHelper.getDescricaoSituacao());
						}

						// Se a descri��o da OS � "Encerrada"
						if(historicoManutencaoLigacao.getDescricaoSituacaoOSAssociado() != null
										&& historicoManutencaoLigacao.getDescricaoSituacaoOSAssociado().equals(
														OrdemServico.SITUACAO_DESCRICAO_ENCERRADO)){

							if(motivosEmExecucao == null){
								FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
								filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(
												FiltroAtendimentoMotivoEncerramento.INDICADOR_EXECUCAO, ConstantesSistema.SIM));

								motivosEmExecucao = getControladorUtil().pesquisar(filtroAtendimentoMotivoEncerramento,
												AtendimentoMotivoEncerramento.class.getName());
							}

							if(!Util.isVazioOrNulo(motivosEmExecucao)){
								for(AtendimentoMotivoEncerramento atendimentoMotivoEncerramento : motivosEmExecucao){

									// Se o motivo de encerramento corresponda � conclus�o do
									// servico, exibir descri��o do tipo de corte e login do usu�rio
									// que informou a execu��o da OS
									if(atendimentoMotivoEncerramentoIdAssociado != null
													&& atendimentoMotivoEncerramentoIdAssociado.equals(atendimentoMotivoEncerramento
																	.getId())){

										if(numeroLeituraExecucaoAssociado != null){
											historicoManutencaoLigacao.setNumeroLeituraExecucaoAssociado(numeroLeituraExecucaoAssociado
															.toString());
										}

										if(descricaoCorteTipoAssociado != null){
											historicoManutencaoLigacao.setDescricaoCorteTipoAssociado(descricaoCorteTipoAssociado);
										}

										if(loginUsuarioAssociado != null){
											historicoManutencaoLigacao.setLoginUsuarioAssociado(loginUsuarioAssociado);
										}

										break;
									}
								}
							}
						}
					}

					retorno.add(historicoManutencaoLigacao);

				}
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3076] Retorna a quantidade de registros retornados pela consulta do Hist�rico da
	 * Manuten��o da Liga��o de �gua
	 * 
	 * @author Luciano Galvao
	 * @date 19/09/2012
	 * @throws ControladorException
	 */
	public Integer consultarTotalRegistrosHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper)
					throws ControladorException{

		try{
			return this.repositorioLigacaoAgua.consultarTotalRegistrosHistoricoManutencaoLigacao(helper);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @param idLigacaoAguaSituacao
	 * @return
	 * @throws ControladorException
	 */

	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idLigacaoAguaSituacao) throws ControladorException{

		LigacaoAguaSituacao retorno = null;
		try{
			retorno = this.repositorioLigacaoAgua.pesquisarLigacaoAguaSituacao(idLigacaoAguaSituacao);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3144] Efetuar Corte de Liga��o de �gua com Retirada de Hidr�metro
	 * 
	 * @author Eduardo Oliveira.
	 * @date 17/04/2014
	 * @param helper
	 * @throws ControladorException
	 */
	public void efetuarCorteLigacaoAguaComRetiradaHidrometro(IntegracaoComercialHelper integracaoComercialHelper)
					throws ControladorException{

		DadosEfetuacaoCorteLigacaoAguaHelper helper = integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper();

		this.getControladorImovel().verificarImovelControleConcorrencia(helper.getImovel());
		LigacaoAgua ligacaoAgua = helper.getImovel().getLigacaoAgua();
		this.verificarLigacaoAguaControleConcorrencia(ligacaoAgua);

		Usuario usuario = integracaoComercialHelper.getUsuarioLogado();
		helper.getOrdemServico().adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR,
						ligacaoAgua.getId(), ligacaoAgua.getId(), new UsuarioAcaoUsuarioHelper(
										integracaoComercialHelper.getUsuarioLogado(), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		registradorOperacao.registrarOperacao(ligacaoAgua);
		getControladorTransacao().registrarTransacao(ligacaoAgua);
		try{
			// Efetuar Corte Liga��o de �gua
			repositorioLigacaoAgua.efetuarCorteLigacaoAgua(helper);
			if(!helper.isVeioEncerrarOS()){
				this.getControladorOrdemServico().atualizaOSGeral(helper.getOrdemServico(), true, false);
			}


			atualizarHistoricoManutencaoLigacao(helper.getOrdemServico(), HistoricoManutencaoLigacao.EFETUAR_CORTE_LIGACAO_AGUA);

			if(helper.getOrdemServico().getServicoTipo().getDebitoTipo() != null
							&& (helper.getOrdemServico().getServicoNaoCobrancaMotivo() == null || helper.getOrdemServico()
											.getServicoNaoCobrancaMotivo().getId().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Gerar D�bito OS
				this.getControladorOrdemServico().gerarDebitoOrdemServico(
								helper.getOrdemServico().getId(),
								helper.getOrdemServico().getServicoTipo().getDebitoTipo().getId(),
								Util.calcularValorDebitoComPorcentagem(helper.getOrdemServico().getValorAtual(), helper.getOrdemServico()
												.getPercentualCobranca().toString()), helper.getQtdeParcelas());
			}

			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = integracaoComercialHelper.getHidrometroInstalacaoHistorico();

			if(integracaoComercialHelper.getHidrometroInstalacaoHistorico() != null){
				HidrometroSituacao hidrometroSituacao = new HidrometroSituacao();
				hidrometroSituacao.setId(HidrometroSituacao.DISPONIVEL);
				integracaoComercialHelper.getHidrometroInstalacaoHistorico().getHidrometro().setHidrometroSituacao(hidrometroSituacao);
				integracaoComercialHelper.getHidrometroInstalacaoHistorico().getHidrometro().setUltimaAlteracao(new Date());

			}

			getControladorUtil().atualizar(hidrometroInstalacaoHistorico);

			getControladorUtil().atualizar(hidrometroInstalacaoHistorico.getHidrometro());

			/**
			 * 8. Caso a ordem de servi�o esteja associada a documento de cobran�a(CBDO_ID diferente
			 * de nulo
			 * na tabela ORDEM_SERVI�O) para a ordem em quest�o:
			 */
			if(helper.getOrdemServico().getCobrancaDocumento() != null && helper.getOrdemServico().getCobrancaDocumento().getId() != null){

				/**
				 * 9. Gerar/acumular dados relativos aos documentos gerados(tabela
				 * COBRANCA_PRODUTIVIDADE)
				 * obtendo os dados a partir de COBRANCA_DOCUMENTO - Verificar a existencia pela
				 * chave
				 * composta(todos os campos exceto o CPRO_ID e campos de quantidade/valores) de
				 * linha na
				 * tabela. Caso exista, acumular na existente as colunas de quantidade e valor, caso
				 * contr�rio, inserir nova linha.
				 */
				getControladorCobranca().gerarAcumuladoDadosRelativosDocumentosGerados(helper.getOrdemServico(), false, false,
								CobrancaDebitoSituacao.EXECUTADO);
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Este m�todo se destina a validar todas as situa��es e particularidades do
	 * corte liga��o de agua
	 * 
	 * @author Rafael Pinto
	 * @date 25/07/2006
	 * @param OrdemServico
	 */
	public void validarExibirCorteLigacaoAguaComRetiradaHidrometro(OrdemServico ordemServico, boolean veioEncerrarOS)
					throws ControladorException{

		ServicoTipo servicoTipo = ordemServico.getServicoTipo();
		Integer idServicoTipo = servicoTipo.getId();

		// [FS0001] - Validar Ordem de Servi�o
		Integer idOperacao = this.getControladorOrdemServico().pesquisarServicoTipoOperacao(idServicoTipo,
						Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_RETIRADA_HIDROMETRO);

		if(idOperacao == null){
			throw new ControladorException("atencao.servico_associado_corte_ligacao_agua_invalida");
		}

		// Caso 3
		this.getControladorOrdemServico().validaOrdemServico(ordemServico, veioEncerrarOS);

		if(ordemServico.getImovel() == null){
			throw new ControladorException("atencao.ordem_servico_imovel_invalido");
		}

		// Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
		Imovel imovel = ordemServico.getImovel();

		// [FS0002] Verificar Situa��o do Imovel.
		if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().intValue() != ConstantesSistema.INDICADOR_IMOVEL_ATIVO){

			throw new ControladorException("atencao.atualizar_imovel_situacao_invalida", null, imovel.getId().toString());
		}

		if(imovel.getLigacaoAgua() == null){
			throw new ControladorException("atencao.naocadastrado", null, "Liga��o de �gua");
		}

		// [FS0003] Verificar a situa��o de �gua
		if(imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.LIGADO.intValue()){

			throw new ControladorException("atencao.situacao_agua_imovel_invaliga", null, imovel.getLigacaoAguaSituacao().getDescricao()
							+ "");
		}

		/*
		 * ===================================================================================
		 */

		// [FS0014] Verificar tipo de registro de corte da liga��o de �gua

		if(ServicoTipo.CORTE_REGISTRO_MAGNETICO.contains(idServicoTipo) || ServicoTipo.CORTE_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.LIQ_NORMAL)){
					throw new ControladorException("atencao.imovel_sem_registro_magnetico_e_sem_tubere_corte_ligacao");
				}
			}
		}

		if(ServicoTipo.CORTE.contains(idServicoTipo) || ServicoTipo.CORTE_TUBETE.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.REG_MAGNET)){
					throw new ControladorException("atencao.imovel_com_registro_magnetico_incorreto_corte_ligacao");
				}
			}
		}

		if(ServicoTipo.CORTE.contains(idServicoTipo) || ServicoTipo.CORTE_REGISTRO_MAGNETICO.contains(idServicoTipo)){
			LigacaoAgua ligacaoAgua = this.pesquisarLigacaoAgua(imovel.getId());

			CorteRegistroTipo corteRegistroTipo = ligacaoAgua.getCorteRegistroTipo();

			if(corteRegistroTipo != null){
				Integer idCorteRegistroTipo = corteRegistroTipo.getId();

				if(idCorteRegistroTipo.equals(CorteRegistroTipo.TUB_MAGNET)){
					throw new ControladorException("atencao.imovel_com_tubete_magnetico_incorreto_corte_ligacao");
				}
			}
		}
	}
}