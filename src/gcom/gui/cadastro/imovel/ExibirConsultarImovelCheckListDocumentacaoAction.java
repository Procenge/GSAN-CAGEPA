
package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.imovel.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarImovelCheckListDocumentacaoAction
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

		ActionForward retorno = actionMapping.findForward("atualizarImovelCheckListDocumentacaoAction");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelCheckListDocumentacaoForm imovelCheckListDocumentacaoForm = (ImovelCheckListDocumentacaoForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String limparForm = httpServletRequest.getParameter("limparForm");
		String idImovel = imovelCheckListDocumentacaoForm.getIdImovel();
		Imovel imovel = null;

		if(limparForm != null && !limparForm.equals("")){
			this.limparForm(imovelCheckListDocumentacaoForm, httpServletRequest);
		}

		if(imovelCheckListDocumentacaoForm.getIdImovel() != null && !imovelCheckListDocumentacaoForm.getIdImovel().equals("")){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelCheckListDocumentacaoForm.getIdImovel()));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			if(imovel != null){

				imovelCheckListDocumentacaoForm.setIdImovel(idImovel);

				imovelCheckListDocumentacaoForm.setMatriculaImovelDados(fachada.pesquisarInscricaoImovel(Integer.valueOf(idImovel.trim()),
								true));

				imovelCheckListDocumentacaoForm.setNomeLocalidade(imovel.getLocalidade().getDescricao());
				imovelCheckListDocumentacaoForm.setCodigoSituacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
				imovelCheckListDocumentacaoForm.setDescricaoSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
				imovelCheckListDocumentacaoForm.setCodigoSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());
				imovelCheckListDocumentacaoForm.setDescricaoSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
				imovelCheckListDocumentacaoForm.setNumeroRota(imovel.getRota().getCodigo().toString());
				imovelCheckListDocumentacaoForm.setNumeroSegmento(imovel.getNumeroSegmento().toString());

				String endereco = fachada.pesquisarEndereco(imovel.getId());
				imovelCheckListDocumentacaoForm.setEndereco(endereco);

				Collection colecaoClienteImovel = fachada.pesquisarClientesImovel(imovel.getId());

				Iterator iterator = colecaoClienteImovel.iterator();

				while(iterator.hasNext()){

					ClienteImovel clienteImovel = (ClienteImovel) iterator.next();

					if(clienteImovel.getDataFimRelacao() == null
									&& clienteImovel.getClienteRelacaoTipo().getId().equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO)){

						Cliente cliente = clienteImovel.getCliente();

						imovelCheckListDocumentacaoForm.setNomeCliente(cliente.getNome());

						FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
						filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, cliente.getId()));
						filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.FONE_TIPO_ID, ConstantesSistema.SIM));
						Collection colecaoTelefones = fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName());

						if(colecaoTelefones != null && !colecaoTelefones.isEmpty()){

							sessao.setAttribute("colecaoTelefones", colecaoTelefones);
						}
					}
				}

				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.CATEGORIA);
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));
				filtroImovelSubCategoria.setCampoOrderBy(FiltroImovelSubCategoria.CATEGORIA);
				Collection colecaoImovelSubcategoria = fachada.pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());

				FiltroImovelCheckListDocumentacao filtroImovelCheckListDocumentacao = new FiltroImovelCheckListDocumentacao();
				filtroImovelCheckListDocumentacao.adicionarParametro(new ParametroSimples(FiltroImovelCheckListDocumentacao.IMOVEL_ID,
								imovel.getId()));

				Collection colecaoCheckListDocumentosImovel = fachada.pesquisar(filtroImovelCheckListDocumentacao,
								ImovelCheckListDocumentacao.class.getName());

				ImovelCheckListDocumentacao imovelCheckListDocumentacao = (ImovelCheckListDocumentacao) Util
								.retonarObjetoDeColecao(colecaoCheckListDocumentosImovel);

				if(imovelCheckListDocumentacao != null){

					imovelCheckListDocumentacaoForm.setIdCheckList(imovelCheckListDocumentacao.getId().toString());
					imovelCheckListDocumentacaoForm.setIndicadorContrato(imovelCheckListDocumentacao.getIndicadorEntregaCopiaContrato()
									.toString());
					imovelCheckListDocumentacaoForm.setIndicadorCpf(imovelCheckListDocumentacao.getIndicadorEntregaCopiaCpf().toString());
					imovelCheckListDocumentacaoForm.setIndicadorRg(imovelCheckListDocumentacao.getIndicadorEntregaCopiaRg().toString());
					imovelCheckListDocumentacaoForm.setIndicadorDocImovel(imovelCheckListDocumentacao.getIndicadorEntregaCopiaDocImovel()
									.toString());
					imovelCheckListDocumentacaoForm.setIndicadorTemoConfissaoDivida(imovelCheckListDocumentacao
									.getIndicadorEntregaCopiaTermoConfDivida().toString());
					imovelCheckListDocumentacaoForm.setIndicadorCorrespondencia(imovelCheckListDocumentacao
									.getIndicadorEntregaCopiaCorrespondencia().toString());

				}else{

					throw new ActionServletException("atencao.check_list_documentos_imovel_nao_cadastrado", null, imovel.getId().toString());
				}

				sessao.setAttribute("colecaoImovelSubcategoria", colecaoImovelSubcategoria);
			}else{

				httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", "true");
				imovelCheckListDocumentacaoForm.setMatriculaImovelDados("IMÓVEL INEXISTENTE");

			}

		}

		return retorno;

	}

	private void limparForm(ImovelCheckListDocumentacaoForm imovelCheckListDocumentacaoForm, HttpServletRequest httpServletRequest){

		// limpar os dados
		httpServletRequest.setAttribute("idImovelDadosCadastraisNaoEncontrado", null);
		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("colecaoTelefones");
		sessao.removeAttribute("colecaoImovelSubcategoria");

		imovelCheckListDocumentacaoForm.setIdImovel(null);
		imovelCheckListDocumentacaoForm.setMatriculaImovelDados(null);
		imovelCheckListDocumentacaoForm.setNomeLocalidade(null);
		imovelCheckListDocumentacaoForm.setCodigoSituacaoAgua(null);
		imovelCheckListDocumentacaoForm.setDescricaoSituacaoAgua(null);
		imovelCheckListDocumentacaoForm.setCodigoSituacaoEsgoto(null);
		imovelCheckListDocumentacaoForm.setDescricaoSituacaoEsgoto(null);
		imovelCheckListDocumentacaoForm.setNumeroRota(null);
		imovelCheckListDocumentacaoForm.setNumeroSegmento(null);
		imovelCheckListDocumentacaoForm.setNomeCliente(null);
		imovelCheckListDocumentacaoForm.setEndereco(null);
		imovelCheckListDocumentacaoForm.setIndicadorContrato(null);
		imovelCheckListDocumentacaoForm.setIndicadorCpf(null);
		imovelCheckListDocumentacaoForm.setIndicadorRg(null);
		imovelCheckListDocumentacaoForm.setIndicadorDocImovel(null);
		imovelCheckListDocumentacaoForm.setIndicadorCorrespondencia(null);
		imovelCheckListDocumentacaoForm.setIndicadorTemoConfissaoDivida(null);

	}
}
