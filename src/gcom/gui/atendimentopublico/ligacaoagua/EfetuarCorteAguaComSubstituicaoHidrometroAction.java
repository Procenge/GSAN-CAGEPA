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
 * Action responsável pela realização de efetuar corte de água com substituição de hidrômetro
 * 
 * @author isilva
 * @created 20/12/2010
 */
public class EfetuarCorteAguaComSubstituicaoHidrometroAction
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

		EfetuarCorteAguaComSubstituicaoHidrometroActionForm efetuarCorteAguaComSubstituicaoHidrometroActionForm = (EfetuarCorteAguaComSubstituicaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Valida os campos
		this.validarCamposSubstituicao(efetuarCorteAguaComSubstituicaoHidrometroActionForm, sessao);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		if(Util.verificarIdNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem())
						&& Util.verificarIdNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoHidrometro())){

			fachada.validarLocalArmazenagemSituacaoHidrometro(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem(),
							efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoHidrometro());

		}

		String ordemServicoId = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdOrdemServico();
		LigacaoAgua ligacaoAgua = this.setDadosLigacaoAgua(efetuarCorteAguaComSubstituicaoHidrometroActionForm, fachada);

		Imovel imovel = null;

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			String matriculaImovel = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getMatriculaImovel();
			String localArmazenagemHidrometro = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem();
			String numeroLeituraRetiradaHidrometro = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada();

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVIÇO INEXISTENTE");
			}

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());

				ligacaoAgua.setImovel(imovel);
				ligacaoAgua.setId(imovel.getId());
			}

			// Motivo do Corte
			String idMotivoCorteStr = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getMotivoCorte();

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

			if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte() != null
							&& efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte() != ""){
				dataCorte = Util.converteStringParaDate(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte());
			}else{
				throw new ActionServletException("atencao.required", null, " Data do Corte");
			}

			ligacaoAgua.setDataCorte(dataCorte);

			if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumSeloCorte() != null
							&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumSeloCorte().equals("")){
				ligacaoAgua.setNumeroSeloCorte(Integer.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumSeloCorte()));
			}else{
				ligacaoAgua.setNumeroSeloCorte(null);
			}

			CorteTipo corteTipo = new CorteTipo();
			corteTipo.setId(Integer.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getTipoCorte()));
			ligacaoAgua.setCorteTipo(corteTipo);

			ligacaoAgua.setMotivoCorte(motivoCorte);
			ligacaoAgua.setUltimaAlteracao(new Date());

			ordemServico = this.setDadosOrdemServico(httpServletRequest, ordemServico, efetuarCorteAguaComSubstituicaoHidrometroActionForm);
			ordemServico.setCorteTipo(corteTipo);

			String qtdParcelas = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas();

			// *******************************************************************

			String numeroHidrometroNovo = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo();

			// Pesquisa o Novo Hidrômetro
			Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroNovo);

			if(hidrometroNovo == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			if(hidrometroNovo.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_corte_com_substituicao_hidrometro", null,
								hidrometroNovo.getHidrometroSituacao().getDescricao());
			}

			// Novos Historico e Hidrometro
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
			hidrometroInstalacaoHistorico.setHidrometro(hidrometroNovo);

			// Atualiza a entidade com os valores do formulário
			efetuarCorteAguaComSubstituicaoHidrometroActionForm.setFormValues(hidrometroInstalacaoHistorico);

			HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = (HidrometroInstalacaoHistorico) sessao
							.getAttribute("corteAguaSubstituicaoHidrometroHistorico");

			Date dataRetirada = Util.converteStringParaDate(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataRetirada());

			hidrometroSubstituicaoHistorico.setDataRetirada(dataRetirada);

			if(numeroLeituraRetiradaHidrometro != null && !numeroLeituraRetiradaHidrometro.equalsIgnoreCase("")){
				hidrometroSubstituicaoHistorico.setNumeroLeituraRetirada(Integer.valueOf(numeroLeituraRetiradaHidrometro));
			}

			hidrometroSubstituicaoHistorico.setUltimaAlteracao(new Date());

			IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

			if((String) httpServletRequest.getParameter("situacaoHidrometro") != null
							&& !((String) httpServletRequest.getParameter("situacaoHidrometro")).equalsIgnoreCase("")
							&& !((String) httpServletRequest.getParameter("situacaoHidrometro")).equalsIgnoreCase("-1")){
				integracaoComercialHelper.setSituacaoHidrometroSubstituido((String) httpServletRequest.getParameter("situacaoHidrometro"));
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Situação do Hidrômetro");
			}

			if((String) httpServletRequest.getParameter("localArmazenagem") != null
							&& !((String) httpServletRequest.getParameter("localArmazenagem")).equalsIgnoreCase("")
							&& !((String) httpServletRequest.getParameter("localArmazenagem")).equalsIgnoreCase("-1")){
				integracaoComercialHelper.setLocalArmazenagemHidrometro(Integer.valueOf((String) httpServletRequest
								.getParameter("localArmazenagem")));
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Local de Armazenagem");
			}

			// Preenche Helper
			DadosEfetuacaoCorteLigacaoAguaHelper dadosHelper = new DadosEfetuacaoCorteLigacaoAguaHelper();
			dadosHelper.setImovel(imovel);
			dadosHelper.setLigacaoAgua(ligacaoAgua);
			dadosHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			dadosHelper.setOrdemServico(ordemServico);
			if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("true")){
				dadosHelper.setVeioEncerrarOS(true);
			}else{
				dadosHelper.setVeioEncerrarOS(false);
			}
			if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas() != null
							&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas().equals("")){
				dadosHelper.setQtdeParcelas(Integer.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas())
								.intValue());
			}else{
				dadosHelper.setQtdeParcelas(0);
			}

			integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
			integracaoComercialHelper.setImovel(imovel);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);
			integracaoComercialHelper.setLocalArmazenagemHidrometro(Integer.valueOf(localArmazenagemHidrometro));
			integracaoComercialHelper.setHidrometroSubstituicaoHistorico(hidrometroSubstituicaoHistorico);
			integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			integracaoComercialHelper.setMatriculaImovel(matriculaImovel);

			integracaoComercialHelper.setDadosEfetuacaoCorteLigacaoAguaHelper(dadosHelper);
			integracaoComercialHelper.setUsuarioLogado(usuario);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				try{

					fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(String.valueOf(dadosHelper.getQtdeParcelas())));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteAguaComSubstituicaoHidrometroAction.do");
					throw ex;
				}
			}

			if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarCorteAguaComSubstituicaoHidrometro(integracaoComercialHelper);
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteAguaComSubstituicaoHidrometroAction.do");
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
				montarPaginaSucesso(httpServletRequest, "Corte de Água com Substituição de Hidrômetro efetuada com Sucesso",
								"Efetuar outro Corte de Água com Substituição de Hidrômetro",
								"exibirEfetuarCorteAguaComSubstituicaoHidrometroAction.do?menu=sim");
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
					EfetuarCorteAguaComSubstituicaoHidrometroActionForm efetuarCorteAguaComSubstituicaoHidrometroActionForm, Fachada fachada){

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, efetuarCorteAguaComSubstituicaoHidrometroActionForm
						.getMatriculaImovel()));
		Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte().equals("")){
			Date data = Util.converteStringParaDate(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataCorte());
			ligacaoAgua.setDataCorte(data);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, " Data do Corte");
		}

		return ligacaoAgua;
	}

	// [SB0003] - Atualizar Ordem de Serviço
	//
	// Método responsável por setar os dados da ordem de serviço
	// de acordo com as exigências do caso de uso
	public OrdemServico setDadosOrdemServico(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarCorteAguaComSubstituicaoHidrometroActionForm efetuarCorteAguaComSubstituicaoHidrometroActionForm){

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoDebito() != null){

			String idServicoMotivoNaoCobranca = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getMotivoNaoCobranca();
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}else{
				// Calcula valores do Quadro: Dados da Geração do Débito
				BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
								efetuarCorteAguaComSubstituicaoHidrometroActionForm);

				if(Util.verificarNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getPercentualCobranca())
								&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getPercentualCobranca().equalsIgnoreCase("-1")){
					ordemServico.setPercentualCobranca(new BigDecimal(efetuarCorteAguaComSubstituicaoHidrometroActionForm
									.getPercentualCobranca()));
				}

				// if
				// (Util.verificarNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getUnidadeMedida())
				// &&
				// Util.verificarNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getQuantidade()))
				// {

				if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorServico() != null
								&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorServico().equalsIgnoreCase("")){
					// ordemServico.setValorOriginal(new
					// BigDecimal(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
					// .replace(".", "").replace(",", ".")));
					ordemServico.setValorOriginal(Util.formatarMoedaRealparaBigDecimal(efetuarCorteAguaComSubstituicaoHidrometroActionForm
									.getValorServico().toString()));
				}

				ordemServico.setValorAtual(valorAtual);

				// if (efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorDebito() != null
				// &&
				// !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorDebito().equalsIgnoreCase(""))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorDebito().toString()
				// .replace(".", "").replace(",", ".")));
				// }
				// } else if
				// (Util.verificarNaoVazio(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorServico()))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
				// .replace(".", "").replace(",", ".")));
				// }
			}

			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
		}
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
		ordemServico.setUltimaAlteracao(new Date());

		return ordemServico;
	}

	/*
	 * Calcular valor da prestação com juros
	 * return: Retorna o valor total do débito
	 * autor: Raphael Rossiter
	 * data: 04/05/2007
	 */
	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarCorteAguaComSubstituicaoHidrometroActionForm form){

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

	// [SB0004] - Gerar Histórico de Instalação do Hidrômetro
	//
	// Método responsável por setar os dados do hidrômetro instalação histórico
	// de acordo com os dados selecionados pelo usuário e pelas exigências do
	// caso de uso
	public IntegracaoComercialHelper setDadosHidrometroSubstituicaoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico,
					EfetuarCorteAguaComSubstituicaoHidrometroActionForm efetuarCorteAguaComSubstituicaoHidrometroActionForm,
					HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometroAtual = null;

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro().equalsIgnoreCase("")){
			numeroHidrometroAtual = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro();
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		if(numeroHidrometroAtual != null){
			// Pesquisa o Hidrômetro atual
			Hidrometro hidrometroAtual = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroAtual);

			if(hidrometroAtual == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			hidrometroSubstituicaoHistorico.setHidrometro(hidrometroAtual);
			hidrometroSubstituicaoHistorico.setId(((HidrometroInstalacaoHistorico) sessao
							.getAttribute("corteAguaSubstituicaoHidrometroHistorico")).getId());
		}

		String numeroHidrometroNovo = null;

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo().equalsIgnoreCase("")){
			numeroHidrometroNovo = efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo();
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		HidrometroInstalacaoHistorico hidrometroSubstituicaoHistoricoInst = null;
		if(numeroHidrometroNovo != null){
			// Pesquisa o Hidrômetro
			Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroNovo);

			if(hidrometroNovo == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			hidrometroSubstituicaoHistoricoInst = new HidrometroInstalacaoHistorico();
			hidrometroSubstituicaoHistoricoInst.setHidrometro(hidrometroNovo);
		}

		// Data instalação
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataInstalacao().equals("")){

			hidrometroSubstituicaoHistorico.setDataInstalacao(Util
							.converteStringParaDate(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataInstalacao()));

		}

		// Medição tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		// medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao().equalsIgnoreCase("")){
			medicaoTipo.setId(Integer.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Medição");
		}
		hidrometroSubstituicaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidrômetro local instalação
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao()));
			hidrometroSubstituicaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instalação");
		}

		// proteção
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			hidrometroProtecao.setId(Integer.parseInt(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Proteção");
		}
		hidrometroSubstituicaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instalação
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroSubstituicaoHistorico.setNumeroLeituraInstalacao(Integer.parseInt(efetuarCorteAguaComSubstituicaoHidrometroActionForm
							.getLeituraInstalacao()));
		}else{
			hidrometroSubstituicaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorExistenciaCavalete(Short
							.parseShort(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Situação do Cavalete");
		}

		/*
		 * Campos opcionais
		 */

		// data da retirada
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataRetirada() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataRetirada().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setDataRetirada(Util.converteStringParaDate(efetuarCorteAguaComSubstituicaoHidrometroActionForm
							.getDataRetirada()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Data da Retirada");
		}
		// leitura retirada
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setNumeroLeituraRetirada(Integer.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm
							.getLeituraRetirada()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número da Leitura");
		}
		// leitura corte
		hidrometroSubstituicaoHistorico.setNumeroLeituraCorte(null);
		// leitura supressão
		hidrometroSubstituicaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroSubstituicaoHistorico.setNumeroSelo(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroSelo());
		}else{
			hidrometroSubstituicaoHistorico.setNumeroSelo(null);
		}
		// tipo de rateio
		hidrometroSubstituicaoHistorico.setRateioTipo(new RateioTipo(RateioTipo.RATEIO_POR_IMOVEL));
		hidrometroSubstituicaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instalação substituição
		hidrometroSubstituicaoHistorico.setIndicadorInstalcaoSubstituicao(ConstantesSistema.NAO);

		// data última alteração
		hidrometroSubstituicaoHistorico.setUltimaAlteracao(new Date());

		// Troca de Proteção
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorTrocaProtecao(Short.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm
							.getIndicadorTrocaProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Proteção");
		}
		// Troca de Registro
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro() != null
						&& !efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorTrocaRegistro(Short.valueOf(efetuarCorteAguaComSubstituicaoHidrometroActionForm
							.getIndicadorTrocaRegistro()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Registro");
		}
		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();
		integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroSubstituicaoHistoricoInst);
		integracaoComercialHelper.setHidrometroSubstituicaoHistorico(hidrometroSubstituicaoHistorico);

		return integracaoComercialHelper;

	}

	private void validarCamposSubstituicao(
					EfetuarCorteAguaComSubstituicaoHidrometroActionForm efetuarCorteAguaComSubstituicaoHidrometroActionForm,
					HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		// Pesquisa o Hidrômetro atual
		Hidrometro hidrometroAtual = fachada.pesquisarHidrometroPeloNumero(efetuarCorteAguaComSubstituicaoHidrometroActionForm
						.getNumeroHidrometro());

		if(hidrometroAtual == null){
			throw new ActionServletException("atencao.hidrometro_inexistente");
		}

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		// Pesquisa o Novo Hidrômetro
		Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(efetuarCorteAguaComSubstituicaoHidrometroActionForm
						.getNumeroHidrometroNovo());

		if(hidrometroNovo == null){
			throw new ActionServletException("atencao.hidrometro_inexistente");
		}

		// Data instalação
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataInstalacao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataInstalacao().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data da Instalação");
		}

		// Medição tipo
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Medição");
		}

		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instalação");
		}

		// proteção
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.informe_campo", null, "Proteção");
		}

		// leitura instalação
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Leitura de Instalação");
		}

		// cavalete
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Situação do Cavalete");
		}

		/*
		 * Campos opcionais
		 */
		// data da retirada
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataRetirada() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getDataRetirada().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data da Retirada");
		}
		// leitura retirada
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número da Leitura");
		}

		// Troca de Proteção
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Proteção");
		}

		// Troca de Registro
		if(efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro() == null
						|| efetuarCorteAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Registro");
		}
	}
}
