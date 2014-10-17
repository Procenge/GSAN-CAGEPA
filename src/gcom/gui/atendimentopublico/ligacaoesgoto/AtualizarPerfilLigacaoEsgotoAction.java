package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3102] - Atualizar Perfil Liga��o Esgoto
 * 
 * @author �talo Almeida
 * @created 31/07/2013
 */
public class AtualizarPerfilLigacaoEsgotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarPerfilLigacaoEsgotoActionForm form = (AtualizarPerfilLigacaoEsgotoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_PERFIL_LIGACAO_ESGOTO,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ATUALIZAR_PERFIL_LIGACAO_ESGOTO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transa��o

		// Imovel
		Imovel imovel = new Imovel();
		imovel.setId(new Integer(form.getMatriculaImovel()));

		// Liga��o Esgoto Perfil
		LigacaoEsgotoPerfil ligacaoEsgotoPerfil = new LigacaoEsgotoPerfil();
		ligacaoEsgotoPerfil.setId(Integer.valueOf(form.getPerfilLigacaoEsgoto()));


		// Liga��o Esgoto Percentual
		Integer idLigacaoEsgoto = Integer.valueOf(form.getPerfilLigacaoEsgoto());
		BigDecimal valorPercentual = fachada.recuperarPercentualEsgotoPerfil(idLigacaoEsgoto);

		// Liga��o Esgoto
		LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();
		ligacaoEsgoto.setId(imovel.getId());
		ligacaoEsgoto.setLigacaoEsgotoPerfil(ligacaoEsgotoPerfil);
		ligacaoEsgoto.setPercentual(valorPercentual);
		ligacaoEsgoto.setUltimaAlteracao(form.getDataConcorrencia());
		imovel.setLigacaoEsgoto(ligacaoEsgoto);

		// incluido OS para "transportar" opera�ao a ser executada
		OrdemServico ordemServico = new OrdemServico();
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();
		integracaoComercialHelper.setOrdemServico(ordemServico);
		integracaoComercialHelper.setLigacaoEsgoto(ligacaoEsgoto);

		if(form.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			// Efetuando Atualiza��o Perfil da Liga��o de Esgoto
			fachada.atualizarPerfilLigacaoEsgoto(integracaoComercialHelper);
			// Setando Opera��o
			ligacaoEsgoto.setOperacaoEfetuada(operacaoEfetuada);
			ligacaoEsgoto.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(ligacaoEsgoto);
		}else{

			integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);

			sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

			// Efetuando Atualiza��o Perfil da Liga��o de Esgoto
			fachada.atualizarPerfilLigacaoEsgoto(integracaoComercialHelper);

			retorno = actionMapping.findForward("encerrarOrdemServicoAction");

			sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
		}
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Atualiza��o do Perfil da Liga��o de Esgoto " + ligacaoEsgoto.getId()
							+ " efetuada com Sucesso", "Atualizar o Perfil de outra Liga��o de Esgoto",
							"exibirAtualizarPerfilLigacaoEsgotoAction.do?menu=sim", "exibirAtualizarPerfilLigacaoEsgotoAction.do?numeroOS="
											+ form.getNumeroOS(), "Atualizar o Perfil da Liga��o de Esgoto alterada");
		}

		return retorno;
	}

}
