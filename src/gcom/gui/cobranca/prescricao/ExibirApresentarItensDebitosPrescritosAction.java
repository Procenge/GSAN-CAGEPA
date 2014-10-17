
package gcom.gui.cobranca.prescricao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.cobranca.bean.PrescricaoContaHelper;
import gcom.cobranca.bean.PrescricaoGuiaPrestacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * [SB0001] - Apresentar Itens de Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 04/04/2014
 */
public class ExibirApresentarItensDebitosPrescritosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("apresentarItensDebitosPrescritos");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		ApresentarItensDebitosPrescritosActionForm form = (ApresentarItensDebitosPrescritosActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String idImovelComDebitoPrescritoStr = httpServletRequest.getParameter("idImovelComDebitoPrescrito");

		if(Util.isVazioOuBranco(idImovelComDebitoPrescritoStr)){

			ActionServletException ex = new ActionServletException("atencao.naoinformado", null,
							"Matricula do Imóvel com Débitos Prescritos");
			ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
			throw ex;
		}

		Integer idImovelComDebitoPrescrito = Integer.valueOf(idImovelComDebitoPrescritoStr);

		form.limpar();

		sessao.removeAttribute("colecaoImovelSubcategorias");
		sessao.removeAttribute("colecaoClientesImovel");
		sessao.removeAttribute("colecaoPrescricaoContaHelper");
		sessao.removeAttribute("valorTotalAguaContas");
		sessao.removeAttribute("valorTotalEsgotoContas");
		sessao.removeAttribute("valorTotalDebitoContas");
		sessao.removeAttribute("valorTotalCreditoContas");
		sessao.removeAttribute("valorTotalImpostoContas");
		sessao.removeAttribute("valorTotalContas");
		sessao.removeAttribute("colecaoPrescricaoGuiaPrestacaoHelper");
		sessao.removeAttribute("valorTotalDebitoGuia");

		Imovel imovel = fachada.pesquisarImovel(idImovelComDebitoPrescrito);

		if(imovel == null){
			
			ActionServletException ex = new ActionServletException("atencao.atualizacao.timestamp");
			ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
			throw ex;
		}

		Integer idImovel = null;

		if(imovel != null){

			// 1.1. Imóvel
			idImovel = imovel.getId();
			form.setIdImovel(idImovel.toString());

			// 1.2. Dados do Imóvel
			// 1.2.1. Inscrição
			String inscricaoFormatadaImovel = imovel.getInscricaoFormatada();
			form.setInscricaoFormatadaImovel(inscricaoFormatadaImovel);

			// 1.2.2. Situação de Água
			LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

			if(ligacaoAguaSituacao != null){
				String descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
				form.setDescricaoLigacaoAguaSituacao(descricaoLigacaoAguaSituacao);
			}

			// 1.2.3. Situação de Esgoto
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

			if(ligacaoEsgotoSituacao != null){
				String descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
				form.setDescricaoLigacaoEsgotoSituacao(descricaoLigacaoEsgotoSituacao);
			}

			// 1.2.4. Perfil do Imóvel
			ImovelPerfil imovelPerfil = imovel.getImovelPerfil();

			if(imovelPerfil != null){
				String descricaoImovelPerfil = imovelPerfil.getDescricao();
				form.setDescricaoImovelPerfil(descricaoImovelPerfil);
			}

			// 1.2.5. Endereço do Imóvel
			String enderecoImovel = fachada.pesquisarEndereco(idImovel);
			form.setEnderecoImovel(enderecoImovel);

			// 1.2.6. Lista das subcategorias e quantidades de economias do imóvel
			Collection colecaoImovelSubcategorias = fachada.pesquisarCategoriasImovel(idImovel);
			sessao.setAttribute("colecaoImovelSubcategorias", colecaoImovelSubcategorias);

			// 1.2.7. Lista dos clientes associados ao imóvel
			Collection colecaoClientesImovel = fachada.pesquisarClientesImovel(idImovel);
			sessao.setAttribute("colecaoClientesImovel", colecaoClientesImovel);
		}

		// Dados dos Itens do Débito do Imóvel
		BigDecimal valorTotalDebitosPrescritosImovel = BigDecimal.ZERO;

		// Contas
		FiltroImoveisComDebitosPrescritosHelper filtroImoveisComDebitosPrescritosHelper = (FiltroImoveisComDebitosPrescritosHelper) sessao
						.getAttribute("filtroImoveisComDebitosPrescritosHelper");

		Collection<PrescricaoContaHelper> colecaoPrescricaoContaHelper = fachada.pesquisarContasPrescritas(idImovel,
						filtroImoveisComDebitosPrescritosHelper);

		if(!Util.isVazioOrNulo(colecaoPrescricaoContaHelper)){

			sessao.setAttribute("colecaoPrescricaoContaHelper", colecaoPrescricaoContaHelper);

			// Totalizador
			BigDecimal valorTotalAguaContas = BigDecimal.ZERO;
			BigDecimal valorTotalEsgotoContas = BigDecimal.ZERO;
			BigDecimal valorTotalDebitoContas = BigDecimal.ZERO;
			BigDecimal valorTotalCreditoContas = BigDecimal.ZERO;
			BigDecimal valorTotalImpostoContas = BigDecimal.ZERO;
			BigDecimal valorTotalContas = BigDecimal.ZERO;

			for(PrescricaoContaHelper prescricaoContaHelper : colecaoPrescricaoContaHelper){

				valorTotalAguaContas = valorTotalAguaContas.add(prescricaoContaHelper.getValorAgua());
				valorTotalEsgotoContas = valorTotalEsgotoContas.add(prescricaoContaHelper.getValorEsgoto());
				valorTotalDebitoContas = valorTotalDebitoContas.add(prescricaoContaHelper.getValorDebitos());
				valorTotalCreditoContas = valorTotalCreditoContas.add(prescricaoContaHelper.getValorCreditos());
				valorTotalImpostoContas = valorTotalImpostoContas.add(prescricaoContaHelper.getValorImpostos());
				valorTotalContas = valorTotalContas.add(prescricaoContaHelper.getValorConta());
			}

			sessao.setAttribute("valorTotalAguaContas", Util.formatarMoedaReal(valorTotalAguaContas));
			sessao.setAttribute("valorTotalEsgotoContas", Util.formatarMoedaReal(valorTotalEsgotoContas));
			sessao.setAttribute("valorTotalDebitoContas", Util.formatarMoedaReal(valorTotalDebitoContas));
			sessao.setAttribute("valorTotalCreditoContas", Util.formatarMoedaReal(valorTotalCreditoContas));
			sessao.setAttribute("valorTotalImpostoContas", Util.formatarMoedaReal(valorTotalImpostoContas));
			sessao.setAttribute("valorTotalContas", Util.formatarMoedaReal(valorTotalContas));
			valorTotalDebitosPrescritosImovel = valorTotalDebitosPrescritosImovel.add(valorTotalContas);
		}

		// Guia de Pagamento
		Collection<PrescricaoGuiaPrestacaoHelper> colecaoPrescricaoGuiaPrestacaoHelper = fachada.pesquisarGuiasPrestacaoPrescritas(
						idImovel, filtroImoveisComDebitosPrescritosHelper);

		if(!Util.isVazioOrNulo(colecaoPrescricaoGuiaPrestacaoHelper)){

			sessao.setAttribute("colecaoPrescricaoGuiaPrestacaoHelper", colecaoPrescricaoGuiaPrestacaoHelper);

			// Totalizador
			BigDecimal valorTotalDebitoGuia = BigDecimal.ZERO;
			for(PrescricaoGuiaPrestacaoHelper prescricaoGuiaPrestacaoHelper : colecaoPrescricaoGuiaPrestacaoHelper){

				valorTotalDebitoGuia = valorTotalDebitoGuia.add(prescricaoGuiaPrestacaoHelper.getValorPrestacao());
			}

			sessao.setAttribute("valorTotalDebitoGuia", Util.formatarMoedaReal(valorTotalDebitoGuia));

			valorTotalDebitosPrescritosImovel = valorTotalDebitosPrescritosImovel.add(valorTotalDebitoGuia);
		}

		// O sistema exibe ao final da tela o valor total dos débitos prescritos = valor total das
		// contas + valor total das prestações
		sessao.setAttribute("valorTotalDebitosPrescritosImovel", Util.formatarMoedaReal(valorTotalDebitosPrescritosImovel));

		return retorno;
	}
}
