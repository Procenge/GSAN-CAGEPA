
package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCheckListDocumentacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarImovelCheckListDocumentacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		ImovelCheckListDocumentacaoForm form = (ImovelCheckListDocumentacaoForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idCheckList = form.getIdCheckList();
		String idImovel = form.getIdImovel();
		String indicadorContrato = form.getIndicadorContrato();
		String indicadorCpf = form.getIndicadorCpf();
		String indicadorRg = form.getIndicadorRg();
		String indicadorDocImovel = form.getIndicadorDocImovel();
		String indicadorTemoConfissaoDivida = form.getIndicadorTemoConfissaoDivida();
		String indicadorCorrespondencia = form.getIndicadorCorrespondencia();


		if(idImovel != null && !idImovel.equals("")){

			if(indicadorContrato == null){
				throw new ActionServletException("atencao.check_list_contrato_nao_informado");
			}
			if(indicadorCpf == null){
				throw new ActionServletException("atencao.check_list_cpf_nao_informado");
			}
			if(indicadorRg == null){
				throw new ActionServletException("atencao.check_list_rg_nao_informado");
			}
			if(indicadorDocImovel == null){
				throw new ActionServletException("atencao.check_list_documento_imovel_nao_informado");
			}
			if(indicadorTemoConfissaoDivida == null){
				throw new ActionServletException("atencao.check_list_termo_confissao_imovel_nao_informado");
			}
			if(indicadorCorrespondencia == null){
				throw new ActionServletException("atencao.check_list_correspondecia_nao_informado");
			}

			ImovelCheckListDocumentacao imovelCheckListDocumentacao = new ImovelCheckListDocumentacao();

			Imovel imovel = new Imovel();
			imovel.setId(Integer.parseInt(idImovel));

			imovelCheckListDocumentacao.setId(Integer.parseInt(idCheckList));
			imovelCheckListDocumentacao.setImovel(imovel);
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaContrato(Short.parseShort(indicadorContrato));
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaCpf(Short.parseShort(indicadorCpf));
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaRg(Short.parseShort(indicadorRg));
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaDocImovel(Short.parseShort(indicadorDocImovel));
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaTermoConfDivida(Short.parseShort(indicadorTemoConfissaoDivida));
			imovelCheckListDocumentacao.setIndicadorEntregaCopiaCorrespondencia(Short.parseShort(indicadorCorrespondencia));
			imovelCheckListDocumentacao.setUltimaAlteracao(new Date());

			fachada.atualizar(imovelCheckListDocumentacao);

			// Registrar Transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL,
							imovelCheckListDocumentacao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			imovelCheckListDocumentacao.setOperacaoEfetuada(operacaoEfetuada);
			imovelCheckListDocumentacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(imovelCheckListDocumentacao);

			montarPaginaSucesso(httpServletRequest, "Check List para o imóvel " + idImovel + " atualizado com sucesso.",
							"Atualizar outro Check List de Documentos", "exibirConsultarImovelCheckListDocumentacaoAction.do?menu=sim",
							null, null);

		}else{

			throw new ActionServletException("atencao.imovel_nao_selecionado");

		}

		return retorno;
	}

}
