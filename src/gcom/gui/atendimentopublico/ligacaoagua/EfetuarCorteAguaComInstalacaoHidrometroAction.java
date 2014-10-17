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

package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.RateioTipo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroProtecao;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela realização de efetuar corte de água com instalação de hidrometro
 * 
 * @author isilva
 * @created 15/12/2010
 */
public class EfetuarCorteAguaComInstalacaoHidrometroAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm = (EfetuarCorteAguaComInstalacaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String ordemServicoId = efetuarCorteAguaComInstalacaoHidrometroActionForm.getIdOrdemServico();

		LigacaoAgua ligacaoAgua = this.setDadosLigacaoAgua(efetuarCorteAguaComInstalacaoHidrometroActionForm, fachada);
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
		hidrometroInstalacaoHistorico = this.setDadosHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico,
						efetuarCorteAguaComInstalacaoHidrometroActionForm);

		Imovel imovel = null;

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVIÇO INEXISTENTE");
			}

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());
				ligacaoAgua.setImovel(imovel);
			}

			// Guarda o usuário logado, que realizou a operação
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			// Motivo do Corte
			String idMotivoCorteStr = efetuarCorteAguaComInstalacaoHidrometroActionForm.getMotivoCorte();

			FiltroMotivoCorte filtroMotivoCorte = new FiltroMotivoCorte();
			filtroMotivoCorte.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.ID, idMotivoCorteStr));

			Collection<MotivoCorte> colecaoMotivoCorte = fachada.pesquisar(filtroMotivoCorte, MotivoCorte.class.getName());

			MotivoCorte motivoCorte = (MotivoCorte) Util.retonarObjetoDeColecao(colecaoMotivoCorte);

			Integer indicadorCortePedido = motivoCorte.getIndicadorCortePedido();

			// Situação da Ligação de Água
			Integer idLigacaoAguaSituacao = null;

			if(indicadorCortePedido != null && indicadorCortePedido.equals(Integer.valueOf(ConstantesSistema.SIM))){
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO_PEDIDO;
			}else{
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO;
			}

			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(idLigacaoAguaSituacao);
			imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);

			imovel.setUltimaAlteracao(new Date());

			// Ligação Água
			ligacaoAgua.setId(imovel.getId());

			Date dataCorte = null;

			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte() != null
							&& efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte() != ""){
				dataCorte = Util.converteStringParaDate(efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte());
			}else{
				throw new ActionServletException("atencao.required", null, " Data do Corte");
			}

			ligacaoAgua.setDataCorte(dataCorte);

			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumSeloCorte() != null
							&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumSeloCorte().equals("")){
				ligacaoAgua.setNumeroSeloCorte(Integer.valueOf(efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumSeloCorte()));
			}else{
				ligacaoAgua.setNumeroSeloCorte(null);
			}

			CorteTipo corteTipo = new CorteTipo();
			corteTipo.setId(Integer.valueOf(efetuarCorteAguaComInstalacaoHidrometroActionForm.getTipoCorte()));
			ligacaoAgua.setCorteTipo(corteTipo);

			ligacaoAgua.setMotivoCorte(motivoCorte);
			ligacaoAgua.setUltimaAlteracao(new Date());

			// Hidrometro Instalação Histórico
			if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
				hidrometroInstalacaoHistorico = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico();
				// Validar Número de Leitura do Corte / Número do Selo de Corte
				if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumLeituraCorte() != null
								&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumLeituraCorte().equalsIgnoreCase("")){
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(Integer.valueOf(efetuarCorteAguaComInstalacaoHidrometroActionForm
									.getNumLeituraCorte()));
				}else{
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
				}
				hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
			}

			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

			ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			imovel.setLigacaoAgua(ligacaoAgua);

			ordemServico = this.setDadosOrdemServico(httpServletRequest, ordemServico, efetuarCorteAguaComInstalacaoHidrometroActionForm);
			ordemServico.setCorteTipo(corteTipo);

			String qtdParcelas = efetuarCorteAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas();

			IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

			integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
			integracaoComercialHelper.setImovel(imovel);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);
			integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

			// Preenche Helper
			DadosEfetuacaoCorteLigacaoAguaHelper dadosHelper = new DadosEfetuacaoCorteLigacaoAguaHelper();
			dadosHelper.setImovel(imovel);
			dadosHelper.setLigacaoAgua(ligacaoAgua);
			dadosHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			dadosHelper.setOrdemServico(ordemServico);
			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("true")){
				dadosHelper.setVeioEncerrarOS(true);
			}else{
				dadosHelper.setVeioEncerrarOS(false);
			}
			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas() != null
							&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas().equals("")){
				dadosHelper.setQtdeParcelas(Integer.valueOf(efetuarCorteAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas())
								.intValue());
			}else{
				dadosHelper.setQtdeParcelas(0);
			}

			integracaoComercialHelper.setDadosEfetuacaoCorteLigacaoAguaHelper(dadosHelper);
			integracaoComercialHelper.setUsuarioLogado(usuario);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				try{

					fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(String.valueOf(dadosHelper.getQtdeParcelas())));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteAguaComInstalacaoHidrometroAction.do");
					throw ex;
				}
			}

			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarCorteAguaComInstalacaoHidrometro(integracaoComercialHelper);
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteAguaComInstalacaoHidrometroAction.do");
					throw ex;
				}

			}else{
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
				sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

				if(sessao.getAttribute("semMenu") == null){
					retorno = actionMapping.findForward("encerrarOrdemServicoAction");
				}else{
					retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
				}
				sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
			}
			if(retorno.getName().equalsIgnoreCase("telaSucesso")){
				// Monta a página de sucesso
				montarPaginaSucesso(httpServletRequest, "Corte de Água com Instalação de Hidrômetro efetuado com Sucesso",
								"Efetuar outro Corte de Água com Instalação de Hidrômetro",
								"exibirEfetuarCorteAguaComInstalacaoHidrometroAction.do?menu=sim");
			}

			return retorno;
		}
		throw new ActionServletException("atencao.informe_campo", null, "Ordem de Serviço");

	}

	// [SB0001] - Gerar Ligação de Água
	//
	// Método responsável por setar os dados da ligação de água
	// de acordo com os dados selecionados pelo usuário e pelas exigências do
	// caso de uso
	public LigacaoAgua setDadosLigacaoAgua(
					EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm, Fachada fachada){

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, efetuarCorteAguaComInstalacaoHidrometroActionForm
						.getMatriculaImovel()));
		Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);

		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte().equals("")){
			Date data = Util.converteStringParaDate(efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataCorte());
			ligacaoAgua.setDataCorte(data);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, " Data da Religação");
		}

		return ligacaoAgua;
	}

	// [SB0003] - Atualizar Ordem de Serviço
	//
	// Método responsável por setar os dados da ordem de serviço
	// de acordo com as exigências do caso de uso
	public OrdemServico setDadosOrdemServico(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm){

		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getIdTipoDebito() != null){

			String idServicoMotivoNaoCobranca = efetuarCorteAguaComInstalacaoHidrometroActionForm.getMotivoNaoCobranca();
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}else{
				// Calcula valores do Quadro: Dados da Geração do Débito
				BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
								efetuarCorteAguaComInstalacaoHidrometroActionForm);

				if(Util.verificarNaoVazio(efetuarCorteAguaComInstalacaoHidrometroActionForm.getPercentualCobranca())
								&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getPercentualCobranca().equalsIgnoreCase("-1")){
					ordemServico.setPercentualCobranca(new BigDecimal(efetuarCorteAguaComInstalacaoHidrometroActionForm
									.getPercentualCobranca()));
				}

				// if
				// (Util.verificarNaoVazio(efetuarCorteAguaComInstalacaoHidrometroActionForm.getUnidadeMedida())
				// &&
				// Util.verificarNaoVazio(efetuarCorteAguaComInstalacaoHidrometroActionForm.getQuantidade()))
				// {

				if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorServico() != null
								&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorServico().equalsIgnoreCase("")){
					// ordemServico.setValorOriginal(new
					// BigDecimal(efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorServico().toString()
					// .replace(".", "").replace(",", ".")));
					ordemServico.setValorOriginal(Util.formatarMoedaRealparaBigDecimal(efetuarCorteAguaComInstalacaoHidrometroActionForm
									.getValorServico().toString()));
				}

				ordemServico.setValorAtual(valorAtual);

				// if (efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorDebito() != null &&
				// !efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorDebito().equalsIgnoreCase(""))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorDebito().toString()
				// .replace(".", "").replace(",", ".")));
				// }
				// } else if
				// (Util.verificarNaoVazio(efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorServico()))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarCorteAguaComInstalacaoHidrometroActionForm.getValorServico().toString()
				// .replace(".", "").replace(",", ".")));
				// }
			}

			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
		}
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		ordemServico.setUltimaAlteracao(new Date());

		return ordemServico;
	}

	// [SB0004] - Gerar Histórico de Instalação do Hidrômetro
	//
	// Método responsável por setar os dados do hidrômetro instalação histórico
	// de acordo com os dados selecionados pelo usuário e pelas exigências do
	// caso de uso
	public HidrometroInstalacaoHistorico setDadosHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
					EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometro = efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumeroHidrometro();

		if(numeroHidrometro != null && !numeroHidrometro.equalsIgnoreCase("")){

			// Pesquisa o Hidrômetro
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(numeroHidrometro);

			// FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			// filtroHidrometro.adicionarParametro(new ParametroSimples(
			// FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
			// // Realiza a pesquisa do Hidrômetro
			// Collection colecaoHidrometro = fachada.pesquisar(filtroHidrometro,
			// Hidrometro.class.getName());
			//
			// // verificar se o número do hidrômetro não está cadastrado
			// if (colecaoHidrometro == null || colecaoHidrometro.isEmpty()) {
			// throw new ActionServletException(
			// "atencao.hidrometro_inexistente");
			// }
			// Iterator iteratorHidrometro = colecaoHidrometro.iterator();
			// Hidrometro hidrometro = (Hidrometro) iteratorHidrometro.next();

			if(hidrometro == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}
			hidrometroInstalacaoHistorico.setHidrometro(hidrometro);
			hidrometroInstalacaoHistorico.setNumeroHidrometro(numeroHidrometro);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro");
		}

		if(hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
			throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_corte_com_instalao_hidrometro", null,
							hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getDescricao());
		}

		// Data instalação
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getDataInstalacao().equalsIgnoreCase("")){

			hidrometroInstalacaoHistorico.setDataInstalacao(Util.converteStringParaDate(efetuarCorteAguaComInstalacaoHidrometroActionForm
							.getDataInstalacao()));

		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Data da Instalação");
		}

		// Medição tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidrômetro local instalação
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getLocalInstalacao() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarCorteAguaComInstalacaoHidrometroActionForm.getLocalInstalacao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instalação");
		}
		hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);

		// proteção
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getProtecao() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getProtecao().equalsIgnoreCase("-1")){
			hidrometroProtecao.setId(Integer.parseInt(efetuarCorteAguaComInstalacaoHidrometroActionForm.getProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Proteção");
		}
		hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instalação
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(Integer.parseInt(efetuarCorteAguaComInstalacaoHidrometroActionForm
							.getLeituraInstalacao()));
		}else{
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("-1")){
			hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short.parseShort(efetuarCorteAguaComInstalacaoHidrometroActionForm
							.getSituacaoCavalete()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Cavalete");
		}

		/*
		 * Campos opcionais
		 */

		// data da retirada
		hidrometroInstalacaoHistorico.setDataRetirada(null);
		// leitura retirada
		hidrometroInstalacaoHistorico.setNumeroLeituraRetirada(null);
		// leitura corte
		hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
		// leitura supressão
		hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroInstalacaoHistorico.setNumeroSelo(efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumeroSelo());
		}else{
			hidrometroInstalacaoHistorico.setNumeroSelo(null);
		}
		// tipo de rateio
		hidrometroInstalacaoHistorico.setRateioTipo(new RateioTipo(RateioTipo.RATEIO_POR_IMOVEL));
		hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instalação substituição
		hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("1"));

		// data última alteração
		hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());

		return hidrometroInstalacaoHistorico;

	}

	/*
	 * Calcular valor da prestação com juros
	 * return: Retorna o valor total do débito
	 * autor: Raphael Rossiter
	 * data: 04/05/2007
	 */
	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarCorteAguaComInstalacaoHidrometroActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

		BigDecimal valorDebito = new BigDecimal(0);
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		Integer qtdeParcelas = null;

		if(calculaValores != null && calculaValores.equals("S")){

			// [UC0186] - Calcular Prestação
			BigDecimal taxaJurosFinanciamento = null;
			qtdeParcelas = new Integer(form.getQuantidadeParcelas());

			if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue() && qtdeParcelas.intValue() > 1){

				taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
			}else{
				taxaJurosFinanciamento = new BigDecimal(0);
				qtdeParcelas = 1;
			}

			BigDecimal valorPrestacao = null;
			if(taxaJurosFinanciamento != null){

				// valorDebito = new BigDecimal(form.getValorDebito().replace(",","."));
				valorDebito = Util.formatarMoedaRealparaBigDecimal(form.getValorDebito());

				String percentualCobranca = form.getPercentualCobranca();

				if(percentualCobranca == null || percentualCobranca.equalsIgnoreCase("")
								|| percentualCobranca.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.informe_campo", null, "Percentual de Cobrança");
				}

				valorDebito = valorDebito.multiply(Util.converterEmPercentagem(percentualCobranca, 1));

				valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
								new BigDecimal("0.00"));

				valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			if(valorPrestacao != null){
				String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
				form.setValorParcelas(valorPrestacaoComVirgula);
			}else{
				form.setValorParcelas("0,00");
			}

			// }else if(ordemServico.getRegistroAtendimento() != null){
		}else{

			HttpSession sessao = httpServletRequest.getSession(false);
			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			valorDebito = Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(), imovel.getId(), new Short("3"));

			if(valorDebito != null){
				form.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
			}else{
				form.setValorDebito("0");
			}
		}

		return valorDebito;
	}
}