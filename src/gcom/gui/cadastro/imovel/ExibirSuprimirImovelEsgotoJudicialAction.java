
package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteEndereco;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
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

/**
 * UC - Suprimir ImovelEsgotoJudicial
 * 
 * @author isilva
 * @date 08/02/2011
 */
public class ExibirSuprimirImovelEsgotoJudicialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("suprimirImovelEsgotoJudicial");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		SuprimirImovelEsgotoJudicialActionForm suprimirImovelEsgotoJudicialActionForm = (SuprimirImovelEsgotoJudicialActionForm) actionForm;

		String idImovel = (String) httpServletRequest.getParameter("idImovel");

		if(Util.isVazioOuBranco(idImovel)){
			idImovel = suprimirImovelEsgotoJudicialActionForm.getIdImovel();
		}

		if(!Util.isVazioOuBranco(idImovel)){
			// Recebe o valor do campo Sistema de Esgoto do formulário.
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(this.getFachada().pesquisar(filtroImovel, Imovel.class.getName()));

			if(imovel == null){
				httpServletRequest.setAttribute("corInscricao", "exception");
				suprimirImovelEsgotoJudicialActionForm.setNomeClienteProprietario("");
				suprimirImovelEsgotoJudicialActionForm.setIdImovel("");
				suprimirImovelEsgotoJudicialActionForm.setInscricaoImovel("Matrícula Inexistente");
				suprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario("");
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
				Boolean existeSuprimido = Fachada.getInstancia().verificarImovelSuprimidoJudicial(clienteImovel.getImovel().getId());

				if(existeSuprimido){
					throw new ActionServletException("atencao.imovel.ja.suprimido.judicialmente");
				}

				suprimirImovelEsgotoJudicialActionForm.setNomeClienteProprietario(clienteImovel.getCliente().getNome());
				suprimirImovelEsgotoJudicialActionForm.setIdImovel(String.valueOf(clienteImovel.getImovel().getId()));
				suprimirImovelEsgotoJudicialActionForm.setInscricaoImovel(clienteImovel.getImovel().getInscricaoFormatada());

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
					suprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario(enderecoCorrespondencia.getEnderecoFormatado());
				}else{
					suprimirImovelEsgotoJudicialActionForm.setEnderecoClienteProprietario("");
				}
			}
		}

		return retorno;
	}

}
