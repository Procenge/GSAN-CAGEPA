package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3102] - Atualizar Perfil Ligação Esgoto
 * 
 * @author Ítalo Almeida
 * @created 31/07/2013
 */
public class ExibirAtualizarPerfilLigacaoEsgotoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAtualizarPerfilLigacaoEsgotoAction");
		HttpSession sessao = httpServletRequest.getSession(false);
		AtualizarPerfilLigacaoEsgotoActionForm form = (AtualizarPerfilLigacaoEsgotoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// verifica se veio do menu
		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(form.getVeioEncerrarOS() != null && !form.getVeioEncerrarOS().equals("")){
				if(form.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		form.setVeioEncerrarOS("" + veioEncerrarOS);

		String idOrdemServico = null;

		if(form.getNumeroOS() != null){
			idOrdemServico = form.getNumeroOS();
		}else{
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));
		}

		// ----- preenche o combo com os perfis de ligação de esgoto
		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoligacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

		httpServletRequest.setAttribute("colecaoligacaoEsgotoPerfil", colecaoligacaoEsgotoPerfil);
		// --------------------------------------------------------------

		if(idOrdemServico != null && !idOrdemServico.equals("")){

			OrdemServico ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null && !ordemServico.equals("")){
				// Validações
				fachada.validarExibirAtualizarPerfilLigacaoEsgoto(ordemServico, veioEncerrarOS);
				form.setNumeroOS(idOrdemServico);
				// Preenchar dados da ordem de servico
				form.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());
				// preenche o actionForm
				this.preencherDadosFormulario(form, ordemServico, httpServletRequest);
				sessao.setAttribute("osEncontrada", "true");
			}else{
				sessao.removeAttribute("osEncontrada");
				form.setNomeOrdemServico("Ordem de Serviço inexistente");
			}
		}else{
			httpServletRequest.setAttribute("nomeCampo", "numeroOS");
			form.setDataConcorrencia(new Date());

		}

		return retorno;
	}

	private void preencherDadosFormulario(AtualizarPerfilLigacaoEsgotoActionForm form, OrdemServico ordemServico,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		Imovel imovel = ordemServico.getImovel();
		Cliente cliente = fachada.pesquisarClienteUsuarioImovel(imovel.getId());
		Categoria categoria = fachada.obterDescricoesCategoriaImovel(imovel);
		Integer quantidadeEconomia = (Integer) fachada.obterQuantidadeEconomias(imovel);
		
		imovel = fachada.pesquisarImovel(imovel.getId());

		form.setNumeroOS(ordemServico.getId().toString());
		form.setMatriculaImovel(imovel.getId().toString());
		form.setInscricaoImovel(imovel.getInscricaoFormatada());
		form.setnomeClienteUsuario(cliente.getNome());
		if(cliente.getCpf() != null){
			form.setcpfCnpjClienteUsuario(cliente.getCpfFormatado());
		}else{
			form.setcpfCnpjClienteUsuario(cliente.getCnpjFormatado());
		}
		form.setSituacaoLigacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
		form.setCategoriaImovel(categoria.getDescricao());
		form.setQuantidadeEconomias(quantidadeEconomia.toString());
		form.setSituacaoLigacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
		form.setPerfilLigacaoEsgoto(imovel.getLigacaoEsgoto().getLigacaoEsgotoPerfil().getId().toString());

	}

}
