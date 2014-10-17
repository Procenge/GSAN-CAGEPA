
package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteEndereco;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelEsgotoJudicial;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelEsgotoJudicial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC - Religar ImovelEsgotoJudicial
 * 
 * @author isilva
 * @date 08/02/2011
 */
public class ExibirReligarSuprimirImovelEsgotoJudicialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("religarSuprimirImovelEsgotoJudicial");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		ReligarSuprimirImovelEsgotoJudicialActionForm religarSuprimirImovelEsgotoJudicialActionForm = (ReligarSuprimirImovelEsgotoJudicialActionForm) actionForm;

		String idImovel = (String) httpServletRequest.getParameter("idImovel");

		if(Util.isVazioOuBranco(idImovel)){
			idImovel = religarSuprimirImovelEsgotoJudicialActionForm.getIdImovel();
		}

		if(!Util.isVazioOuBranco(idImovel)){
			// Recebe o valor do campo Sistema de Esgoto do formulário.
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(this.getFachada().pesquisar(filtroImovel, Imovel.class.getName()));

			if(imovel == null){
				httpServletRequest.setAttribute("corInscricao", "exception");
				religarSuprimirImovelEsgotoJudicialActionForm.setNomeClienteProprietario("");
				religarSuprimirImovelEsgotoJudicialActionForm.setIdImovel("");
				religarSuprimirImovelEsgotoJudicialActionForm.setInscricaoImovel("Matrícula Inexistente");
				religarSuprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario("");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}else{

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota.faturamentoGrupo");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.PROPRIETARIO));

				Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

				if(clienteImovel == null){
					throw new ActionServletException("atencao.cliente.imovel.usuario.com.relacao.nao.existe", null, "Proprietário");
				}

				// [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
				FiltroImovelEsgotoJudicial filtroImovelEsgotoJudicial = new FiltroImovelEsgotoJudicial();
				filtroImovelEsgotoJudicial.adicionarParametro(new ParametroSimples(FiltroImovelEsgotoJudicial.IMOVEL_ID, Integer
								.valueOf(idImovel)));
				filtroImovelEsgotoJudicial.adicionarParametro(new ParametroSimples(FiltroImovelEsgotoJudicial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroImovelEsgotoJudicial.adicionarParametro(new ParametroNulo(FiltroImovelEsgotoJudicial.DATAEXCLUSAO));

				Collection colecaoImovelEsgotoJudicial = Fachada.getInstancia().pesquisar(filtroImovelEsgotoJudicial,
								ImovelEsgotoJudicial.class.getName());

				if(colecaoImovelEsgotoJudicial == null || colecaoImovelEsgotoJudicial.isEmpty()){
					throw new ActionServletException("atencao.imovel.nao.suprimido.judicialmente");
				}

				religarSuprimirImovelEsgotoJudicialActionForm.setNomeClienteProprietario(clienteImovel.getCliente().getNome());
				religarSuprimirImovelEsgotoJudicialActionForm.setIdImovel(String.valueOf(clienteImovel.getImovel().getId()));
				religarSuprimirImovelEsgotoJudicialActionForm.setInscricaoImovel(clienteImovel.getImovel().getInscricaoFormatada());

				FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
				filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, clienteImovel.getCliente()
								.getId()));
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
				filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("cliente");

				Collection colecaoClienteEnderecos = Fachada.getInstancia().pesquisar(filtroClienteEndereco,
								ClienteEndereco.class.getName());

				ClienteEndereco enderecoCorrespondencia = null;

				if(colecaoClienteEnderecos != null && !colecaoClienteEnderecos.isEmpty()){
					Iterator colecaoClienteEnderecosIterator = colecaoClienteEnderecos.iterator();
					while(colecaoClienteEnderecosIterator.hasNext()){
						enderecoCorrespondencia = (ClienteEndereco) colecaoClienteEnderecosIterator.next();
						if(enderecoCorrespondencia.getIndicadorEnderecoCorrespondencia().equals(
										ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){
							break;
						}
					}
				}

				if(enderecoCorrespondencia != null){
					religarSuprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario(enderecoCorrespondencia
									.getEnderecoFormatado());
				}else{
					religarSuprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario("");
				}
			}
		}

		return retorno;
	}

}
