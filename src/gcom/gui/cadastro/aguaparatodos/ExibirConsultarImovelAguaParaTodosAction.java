/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cadastro.aguaparatodos.FiltroImovelAguaParaTodos;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovelSimplificado;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteFone;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ExibirConsultarImovelAguaParaTodosAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 27/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirConsultarImovelAguaParaTodos");

		ConsultarImovelAguaParaTodosActionForm form = (ConsultarImovelAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		form.setFlagOperacao(form.NENHUM);

		Integer idImovel = null;
		if(httpServletRequest.getParameter("matriculaImovel") != null && !httpServletRequest.getParameter("matriculaImovel").equals("")){
			idImovel = Integer.valueOf((String) form.getMatricula());
		}else{
			if(form.getMatricula() != null && !form.getMatricula().equals("")){
				idImovel = Integer.valueOf(form.getMatricula());
			}
		}

		if(form != null && idImovel != null){
			Imovel imovel = null;
			try{
				imovel = fachada.pesquisarImovel(Integer.valueOf((String) form.getMatricula()));
			}catch(NumberFormatException e){
				throw new ActionServletException("atencao.nao.numerico", null, form.getMatricula());
			}

			if(imovel != null){
				form.setFlagOperacao(form.CONSULTAR);
				FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID_IMOVEL, imovel.getId()));
				filtroImovelAguaParaTodos.setCampoOrderBy(FiltroImovelAguaParaTodos.ID_CONTRIBUINTE,
								FiltroImovelAguaParaTodos.DATA_HABILITACAO);
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelAguaParaTodos.CODIGO_SITUACAO,
								ImovelAguaParaTodos.EXCLUIDO));

				Collection colImovelAguaParaTodos = fachada.pesquisar(filtroImovelAguaParaTodos, ImovelAguaParaTodos.class.getName());

				form.setColecaoImovelAguaParaTodos(colImovelAguaParaTodos);

				Iterator it = null;

				boolean existeResidencial = false;

				FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
				filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, imovel.getId()));

				Collection colImovelSubCategoria = fachada.pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());

				it = colImovelSubCategoria.iterator();

				String economias = "";

				while(it.hasNext()){
					ImovelSubcategoria subImovelCategoria = (ImovelSubcategoria) it.next();

					FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, subImovelCategoria.getComp_id()
									.getSubcategoria().getId()));

					Subcategoria subCategoria = (Subcategoria) fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName())
									.iterator().next();

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, subCategoria.getCategoria().getId()));

					Categoria categoria = (Categoria) fachada.pesquisar(filtroCategoria, Categoria.class.getName()).iterator().next();

					if(categoria.getId().equals(Categoria.RESIDENCIAL)){
						existeResidencial = true;
					}

					economias += categoria.getDescricao() + ": " + subImovelCategoria.getQuantidadeEconomias() + " ";
				}

				if(!existeResidencial){
					throw new ActionServletException("atencao.agua.para.todos.nao_imovel_residencial", null, form.getMatricula());
				}

				form.setCategoriasEconomia(economias);

				form.setMatricula(imovel.getId().toString());
				form.setLocalidade(imovel.getLocalidade().getDescricao());
				form.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getId() + " - " + imovel.getLigacaoAguaSituacao().getDescricao());
				form
								.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId() + " - "
												+ imovel.getLigacaoEsgotoSituacao().getDescricao());
				form.setInscricao(imovel.getInscricaoFormatada());

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				Collection colClienteImovel = fachada.pesquisarClienteImovel(filtroClienteImovel);

				it = colClienteImovel.iterator();

				if(it.hasNext()){
					ClienteImovelSimplificado clienteSimples = (ClienteImovelSimplificado) it.next();
					FiltroCliente filtroCliente = new FiltroCliente();
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteSimples.getCliente().getId()));
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteFones");

					Cliente cliente = (Cliente) fachada.pesquisar(filtroCliente, Cliente.class.getName()).iterator().next();

					Collection colFone = cliente.getClienteFones();

					if(colFone != null && !colFone.isEmpty()){
						Iterator itFone = colFone.iterator();

						while(itFone.hasNext()){
							ClienteFone fone = (ClienteFone) itFone.next();
							FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
							filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.ID, fone.getId()));
							filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade("foneTipo");
							fone = (ClienteFone) fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName()).iterator().next();

							if(fone.getFoneTipo().getId().equals(new Integer("1"))){
								String ddd = fone.getDdd() != null ? fone.getDdd() + " - " : "";
								form.setTelefone(ddd + fone.getTelefone());
							}
						}
					}
					form.setNomeCliente(cliente.getNome());
				}

				form.setEndereco(imovel.getEnderecoFormatado());
			}else{
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente", null, form.getMatricula());
			}

		}

		return retorno;
	}
}
