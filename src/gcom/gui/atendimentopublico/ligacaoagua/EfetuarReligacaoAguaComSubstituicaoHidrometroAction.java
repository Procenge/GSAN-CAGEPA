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
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
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
 * Action responsável pela realização de efetuar religação de água com substituição de hidrômetro
 * 
 * @author isilva
 * @created 20/12/2010
 */
public class EfetuarReligacaoAguaComSubstituicaoHidrometroAction
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

		EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm efetuarReligacaoAguaComSubstituicaoHidrometroActionForm = (EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Valida os campos
		this.validarCamposSubstituicao(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm, sessao);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		if(Util.verificarIdNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem())
						&& Util.verificarIdNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoHidrometro())){

			try{

				fachada.validarLocalArmazenagemSituacaoHidrometro(
								efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem(),
								efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoHidrometro());

			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarReligacaoAguaComSubstituicaoHidrometroAction.do");
				throw ex;

			}

		}

		String ordemServicoId = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdOrdemServico();
		LigacaoAgua ligacaoAgua = this.setDadosLigacaoAgua(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm, fachada);

		Imovel imovel = null;

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			String matriculaImovel = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getMatriculaImovel();
			String localArmazenagemHidrometro = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalArmazenagem();
			String numeroLeituraRetiradaHidrometro = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada();

			// Novos Historico e Hidrometro
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVIÇO INEXISTENTE");
			}

			// Guarda o usuário logado, que realizou a operação
			ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());
				ligacaoAgua.setImovel(imovel);
				ligacaoAgua.setId(imovel.getId());
			}

			ordemServico = this.setDadosOrdemServico(httpServletRequest, ordemServico,
							efetuarReligacaoAguaComSubstituicaoHidrometroActionForm);

			String numeroHidrometroNovo = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo();

			// Pesquisa o Novo Hidrômetro
			Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroNovo);

			if(hidrometroNovo == null){
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			if(hidrometroNovo.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
								hidrometroNovo.getHidrometroSituacao().getDescricao());
			}

			hidrometroInstalacaoHistorico.setHidrometro(hidrometroNovo);

			// Atualiza a entidade com os valores do formulário
			efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.setFormValues(hidrometroInstalacaoHistorico);

			HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = (HidrometroInstalacaoHistorico) sessao
							.getAttribute("religacaoAguaSubstituicaoHidrometroHistorico");

			Date dataRetirada = Util.converteStringParaDate(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada());

			hidrometroSubstituicaoHistorico.setDataRetirada(dataRetirada);

			if(numeroLeituraRetiradaHidrometro != null && !numeroLeituraRetiradaHidrometro.equalsIgnoreCase("")){
				hidrometroSubstituicaoHistorico.setNumeroLeituraRetirada(Integer.valueOf(numeroLeituraRetiradaHidrometro));
			}

			hidrometroSubstituicaoHistorico.setUltimaAlteracao(new Date());

			String qtdParcelas = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getQuantidadeParcelas();

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

			integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
			integracaoComercialHelper.setImovel(imovel);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);
			integracaoComercialHelper.setLocalArmazenagemHidrometro(Integer.valueOf(localArmazenagemHidrometro));
			integracaoComercialHelper.setHidrometroSubstituicaoHistorico(hidrometroSubstituicaoHistorico);
			integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			integracaoComercialHelper.setMatriculaImovel(matriculaImovel);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				try{

					fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(integracaoComercialHelper.getQtdParcelas()));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarReligacaoAguaComSubstituicaoHidrometroAction.do");
					throw ex;
				}
			}

			if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);
				try{

					fachada.efetuarReligacaoAguaComSubstituicaoHidrometro(integracaoComercialHelper, usuario);

				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarReligacaoAguaComSubstituicaoHidrometroAction.do");
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
				montarPaginaSucesso(httpServletRequest, "Religação de Água com Substituição de Hidrômetro efetuada com Sucesso",
								"Efetuar outra Religação de Água com Substituição de Hidrômetro",
								"exibirEfetuarReligacaoAguaComSubstituicaoHidrometroAction.do?menu=sim");
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
					EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm efetuarReligacaoAguaComSubstituicaoHidrometroActionForm,
					Fachada fachada){

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID,
						efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getMatriculaImovel()));
		Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataReligacao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataReligacao().equals("")){
			Date data = Util.converteStringParaDate(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataReligacao());
			ligacaoAgua.setDataReligacao(data);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, " Data da Religação");
		}

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario().equals("")){
			Funcionario func = new Funcionario();
			func.setId(new Integer(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario()));
			ligacaoAgua.setFuncionarioReligacaoAgua(func);
		}

		if(ligacaoAgua.getNumeroReligacao() != null){
			ligacaoAgua.setNumeroReligacao(ligacaoAgua.getNumeroReligacao() + 1);
		}else{
			ligacaoAgua.setNumeroReligacao(1);
		}

		return ligacaoAgua;
	}

	// [SB0003] - Atualizar Ordem de Serviço
	//
	// Método responsável por setar os dados da ordem de serviço
	// de acordo com as exigências do caso de uso
	public OrdemServico setDadosOrdemServico(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm efetuarReligacaoAguaComSubstituicaoHidrometroActionForm){

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoDebito() != null){

			String idServicoMotivoNaoCobranca = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getMotivoNaoCobranca();
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}else{
				// Calcula valores do Quadro: Dados da Geração do Débito
				BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
								efetuarReligacaoAguaComSubstituicaoHidrometroActionForm);

				if(Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getPercentualCobranca())
								&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getPercentualCobranca().equalsIgnoreCase("-1")){
					ordemServico.setPercentualCobranca(new BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
									.getPercentualCobranca()));
				}

				// if
				// (Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getUnidadeMedida())
				// &&
				// Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getQuantidade()))
				// {

				if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico() != null
								&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().equalsIgnoreCase("")){
					// ordemServico.setValorOriginal(new
					// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
					// .replace(".", "").replace(",", ".")));
					ordemServico.setValorOriginal(Util
									.formatarMoedaRealparaBigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
													.getValorServico().toString()));
				}

				ordemServico.setValorAtual(valorAtual);

				// if (efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito() !=
				// null &&
				// !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito().equalsIgnoreCase(""))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorDebito().toString()
				// .replace(".", "").replace(",", ".")));
				// }
				// } else if
				// (Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico()))
				// {
				// ordemServico.setValorAtual(new
				// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
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
					EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm form){

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
					EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm efetuarReligacaoAguaComSubstituicaoHidrometroActionForm,
					HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometroAtual = null;

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro().equalsIgnoreCase("")){
			numeroHidrometroAtual = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro();
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
							.getAttribute("religacaoAguaSubstituicaoHidrometroHistorico")).getId());
		}

		String numeroHidrometroNovo = null;

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo().equalsIgnoreCase("")){
			numeroHidrometroNovo = efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo();
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
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao().equals("")){

			hidrometroSubstituicaoHistorico.setDataInstalacao(Util
							.converteStringParaDate(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao()));

		}

		// Medição tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		// medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao().equalsIgnoreCase("")){
			medicaoTipo.setId(Integer.valueOf(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Medição");
		}
		hidrometroSubstituicaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidrômetro local instalação
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao()));
			hidrometroSubstituicaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instalação");
		}

		// proteção
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			hidrometroProtecao.setId(Integer.parseInt(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Proteção");
		}
		hidrometroSubstituicaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instalação
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroSubstituicaoHistorico.setNumeroLeituraInstalacao(Integer
							.parseInt(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao()));
		}else{
			hidrometroSubstituicaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorExistenciaCavalete(Short
							.parseShort(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Situação do Cavalete");
		}

		/*
		 * Campos opcionais
		 */

		// data da retirada
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setDataRetirada(Util
							.converteStringParaDate(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Data da Retirada");
		}
		// leitura retirada
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setNumeroLeituraRetirada(Integer
							.valueOf(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número da Leitura");
		}
		// leitura corte
		hidrometroSubstituicaoHistorico.setNumeroLeituraCorte(null);
		// leitura supressão
		hidrometroSubstituicaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroSubstituicaoHistorico.setNumeroSelo(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroSelo());
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
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorTrocaProtecao(Short.valueOf(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
							.getIndicadorTrocaProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Proteção");
		}
		// Troca de Registro
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro() != null
						&& !efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro().equalsIgnoreCase("")){
			hidrometroSubstituicaoHistorico.setIndicadorTrocaRegistro(Short.valueOf(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
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
					EfetuarReligacaoAguaComSubstituicaoHidrometroActionForm efetuarReligacaoAguaComSubstituicaoHidrometroActionForm,
					HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometro().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		// Pesquisa o Hidrômetro atual
		Hidrometro hidrometroAtual = fachada.pesquisarHidrometroPeloNumero(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
						.getNumeroHidrometro());

		if(hidrometroAtual == null){
			throw new ActionServletException("atencao.hidrometro_inexistente");
		}

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getNumeroHidrometroNovo().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número do Hidrômetro Atual");
		}

		// Pesquisa o Novo Hidrômetro
		Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm
						.getNumeroHidrometroNovo());

		if(hidrometroNovo == null){
			throw new ActionServletException("atencao.hidrometro_inexistente");
		}

		// Data instalação
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataInstalacao().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data da Instalação");
		}

		// Medição tipo
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIdTipoMedicao().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Medição");
		}

		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instalação");
		}

		// proteção
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getProtecao().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.informe_campo", null, "Proteção");
		}

		// leitura instalação
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Leitura de Instalação");
		}

		// cavalete
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Situação do Cavalete");
		}

		/*
		 * Campos opcionais
		 */
		// data da retirada
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getDataRetirada().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Data da Retirada");
		}
		// leitura retirada
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Número da Leitura");
		}

		// Troca de Proteção
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Proteção");
		}

		// Troca de Registro
		if(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro() == null
						|| efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro().equalsIgnoreCase("")){
			throw new ActionServletException("atencao.informe_campo", null, "Troca de Registro");
		}
	}
}
