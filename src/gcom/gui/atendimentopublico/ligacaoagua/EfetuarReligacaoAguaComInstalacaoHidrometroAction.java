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
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
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
 * Action respons�vel pela realiza��o de efetuar religa��o de �gua com instala��o de hidrometro
 * 
 * @author S�vio Luiz
 * @created 29/01/2008
 */
public class EfetuarReligacaoAguaComInstalacaoHidrometroAction
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

		EfetuarReligacaoAguaComInstalacaoHidrometroActionForm efetuarReligacaoAguaComInstalacaoHidrometroActionForm = (EfetuarReligacaoAguaComInstalacaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String ordemServicoId = efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getIdOrdemServico();

		LigacaoAgua ligacaoAgua = this.setDadosLigacaoAgua(efetuarReligacaoAguaComInstalacaoHidrometroActionForm, fachada);
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = new HidrometroInstalacaoHistorico();
		hidrometroInstalacaoHistorico = this.setDadosHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico,
						efetuarReligacaoAguaComInstalacaoHidrometroActionForm);

		Imovel imovel = null;

		if(ordemServicoId != null && !ordemServicoId.equals("")){

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			if(ordemServico == null){
				throw new ActionServletException("atencao.ordem_servico_inexistente", null, "ORDEM DE SERVI�O INEXISTENTE");
			}

			// Guarda a opera��o efetuada
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_EFETUAR_RELIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			ordemServico.setOperacaoEfetuada(operacaoEfetuada);

			if(sessao.getAttribute("imovel") != null){
				imovel = (Imovel) sessao.getAttribute("imovel");
				imovel.setUltimaAlteracao(new Date());
				ligacaoAgua.setImovel(imovel);
				// hidrometroInstalacaoHistorico.setImovel(imovel);

				// ligacaoAgua.setId(imovel.getId());
			}

			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);

			ordemServico = this.setDadosOrdemServico(httpServletRequest, ordemServico,
							efetuarReligacaoAguaComInstalacaoHidrometroActionForm);

			String qtdParcelas = efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getQuantidadeParcelas();

			IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

			integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
			integracaoComercialHelper.setImovel(imovel);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);
			integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

				try{

					fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(qtdParcelas));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarReligacaoAguaComInstalacaoHidrometroAction.do");
					throw ex;
				}
			}

			if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarReligacaoAguaComInstalacaoHidrometro(integracaoComercialHelper, usuario);
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarReligacaoAguaComInstalacaoHidrometroAction.do");
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
				// Monta a p�gina de sucesso
				montarPaginaSucesso(httpServletRequest, "Religa��o de �gua com Instala��o de Hidr�metro efetuada com Sucesso",
								"Efetuar outra Religa��o de �gua com Instala��o de Hidr�metro",
								"exibirEfetuarReligacaoAguaComInstalacaoHidrometroAction.do?menu=sim");
			}

			return retorno;
		}
		throw new ActionServletException("atencao.informe_campo", null, "Ordem de Servi�o");

	}

	// [SB0001] - Gerar Liga��o de �gua
	//
	// M�todo respons�vel por setar os dados da liga��o de �gua
	// de acordo com os dados selecionados pelo usu�rio e pelas exig�ncias do
	// caso de uso
	public LigacaoAgua setDadosLigacaoAgua(
					EfetuarReligacaoAguaComInstalacaoHidrometroActionForm efetuarReligacaoAguaComInstalacaoHidrometroActionForm,
					Fachada fachada){

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID,
						efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getMatriculaImovel()));
		Collection colecaoLigacaoAguaBase = fachada.pesquisar(filtroLigacaoAgua, LigacaoAgua.class.getName());

		LigacaoAgua ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(colecaoLigacaoAguaBase);

		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataReligacao() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataReligacao().equals("")){
			Date data = Util.converteStringParaDate(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataReligacao());
			ligacaoAgua.setDataReligacao(data);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, " Data da Religa��o");
		}

		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getIdFuncionario() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getIdFuncionario().equals("")){
			Funcionario func = new Funcionario();
			func.setId(new Integer(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getIdFuncionario()));
			ligacaoAgua.setFuncionarioReligacaoAgua(func);
		}

		if(ligacaoAgua.getNumeroReligacao() != null){
			ligacaoAgua.setNumeroReligacao(ligacaoAgua.getNumeroReligacao() + 1);
		}else{
			ligacaoAgua.setNumeroReligacao(1);
		}

		return ligacaoAgua;
	}

	// [SB0003] - Atualizar Ordem de Servi�o
	//
	// M�todo respons�vel por setar os dados da ordem de servi�o
	// de acordo com as exig�ncias do caso de uso
	public OrdemServico setDadosOrdemServico(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarReligacaoAguaComInstalacaoHidrometroActionForm efetuarReligacaoAguaComInstalacaoHidrometroActionForm){

		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getIdTipoDebito() != null){

			String idServicoMotivoNaoCobranca = efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getMotivoNaoCobranca();
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null && !idServicoMotivoNaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}else{
				// Calcula valores do Quadro: Dados da Gera��o do D�bito
				BigDecimal valorAtual = this.calcularValores(httpServletRequest, ordemServico,
								efetuarReligacaoAguaComInstalacaoHidrometroActionForm);

				if(Util.verificarNaoVazio(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getPercentualCobranca())
								&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getPercentualCobranca().equalsIgnoreCase("-1")){
					ordemServico.setPercentualCobranca(new BigDecimal(efetuarReligacaoAguaComInstalacaoHidrometroActionForm
									.getPercentualCobranca()));
				}

				// if
				// (Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getUnidadeMedida())
				// &&
				// Util.verificarNaoVazio(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getQuantidade()))
				// {

				if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getValorServico() != null
								&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getValorServico().equalsIgnoreCase("")){
					// ordemServico.setValorOriginal(new
					// BigDecimal(efetuarReligacaoAguaComSubstituicaoHidrometroActionForm.getValorServico().toString()
					// .replace(".", "").replace(",", ".")));
					ordemServico.setValorOriginal(Util
									.formatarMoedaRealparaBigDecimal(efetuarReligacaoAguaComInstalacaoHidrometroActionForm
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

	// [SB0004] - Gerar Hist�rico de Instala��o do Hidr�metro
	//
	// M�todo respons�vel por setar os dados do hidr�metro instala��o hist�rico
	// de acordo com os dados selecionados pelo usu�rio e pelas exig�ncias do
	// caso de uso
	public HidrometroInstalacaoHistorico setDadosHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
					EfetuarReligacaoAguaComInstalacaoHidrometroActionForm efetuarReligacaoAguaComInstalacaoHidrometroActionForm){

		Fachada fachada = Fachada.getInstancia();

		String numeroHidrometro = efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getNumeroHidrometro();

		if(numeroHidrometro != null && !numeroHidrometro.equalsIgnoreCase("")){
			// Pesquisa o Hidr�metro
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(numeroHidrometro);

			// FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			// filtroHidrometro.adicionarParametro(new ParametroSimples(
			// FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
			// // Realiza a pesquisa do Hidr�metro
			// Collection colecaoHidrometro = fachada.pesquisar(filtroHidrometro,
			// Hidrometro.class.getName());
			//
			// // verificar se o n�mero do hidr�metro n�o est� cadastrado
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
			throw new ActionServletException("atencao.informe_campo", null, "N�mero do Hidr�metro");
		}

		if(hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
			throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_religacao_com_instalao_hidrometro", null,
							hidrometroInstalacaoHistorico.getHidrometro().getHidrometroSituacao().getDescricao());
		}

		// Data instala��o
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao().equalsIgnoreCase("")){

			hidrometroInstalacaoHistorico.setDataInstalacao(Util
							.converteStringParaDate(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getDataInstalacao()));

		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Data da Instala��o");
		}

		// Medi��o tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		medicaoTipo.setId(MedicaoTipo.LIGACAO_AGUA);
		hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidr�metro local instala��o
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLocalInstalacao() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("")
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLocalInstalacao().equalsIgnoreCase("-1")){
			hidrometroLocalInstalacao.setId(Integer.parseInt(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLocalInstalacao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Local de Instala��o");
		}
		hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);

		// prote��o
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getProtecao() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getProtecao().equalsIgnoreCase("")
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getProtecao().equalsIgnoreCase("-1")){
			hidrometroProtecao.setId(Integer.parseInt(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getProtecao()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Prote��o");
		}
		hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instala��o
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getLeituraInstalacao().trim().equals("")){
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(Integer.parseInt(efetuarReligacaoAguaComInstalacaoHidrometroActionForm
							.getLeituraInstalacao()));
		}else{
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("")
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete().equalsIgnoreCase("-1")){
			hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short
							.parseShort(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getSituacaoCavalete()));
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
		// leitura supress�o
		hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);
		// numero selo
		if(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo() != null
						&& !efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo().equals("")){
			hidrometroInstalacaoHistorico.setNumeroSelo(efetuarReligacaoAguaComInstalacaoHidrometroActionForm.getNumeroSelo());
		}else{
			hidrometroInstalacaoHistorico.setNumeroSelo(null);
		}
		// tipo de rateio
		hidrometroInstalacaoHistorico.setRateioTipo(new RateioTipo(RateioTipo.RATEIO_POR_IMOVEL));
		hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instala��o substitui��o
		hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("1"));

		// data �ltima altera��o
		hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());

		return hidrometroInstalacaoHistorico;

	}

	/*
	 * Calcular valor da presta��o com juros
	 * return: Retorna o valor total do d�bito
	 * autor: Raphael Rossiter
	 * data: 04/05/2007
	 */
	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarReligacaoAguaComInstalacaoHidrometroActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

		BigDecimal valorDebito = new BigDecimal(0);
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		Integer qtdeParcelas = null;

		if(calculaValores != null && calculaValores.equals("S")){

			// [UC0186] - Calcular Presta��o
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
					throw new ActionServletException("atencao.informe_campo", null, "Percentual de Cobran�a");
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