
package gcom.gui.atendimentopublico.tubetemagnetico;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.CorteRegistroTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3064] Efetuar Instalação/Substituição de Tubete Magnético
 * 
 * @author Leonardo José De S. C. Angelim
 * @date 14/08/2012
 */

public class EfetuarInstalacaoSubstituicaoTubeteMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm = (EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm) actionForm;

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		OrdemServico ordemServico = null;

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getVeioEncerrarOS() != null && efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
										.getVeioEncerrarOS().equalsIgnoreCase("true"))){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			veioEncerrarOS = Boolean.FALSE;
		}

		if(sessao.getAttribute("ordemServico") == null){
			String idOrdemServico = null;

			if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getIdOrdemServico() != null){
				idOrdemServico = efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getIdOrdemServico();
			}else{
				idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			}

			if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

				ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));
			}
		}else{

			ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
		}

		// 6. Caso o usuário confirme, o sistema efetiva a instalação/substituição de tubete
		// magnético

		LigacaoAgua ligacaoAgua = null;
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;
		Imovel imovel = ordemServico.getImovel();
		String qtdParcelas = "0";

		if(ordemServico != null){

			CorteRegistroTipo corteRegistroTipo = new CorteRegistroTipo();
			corteRegistroTipo.setId(CorteRegistroTipo.TUB_MAGNET);

			// [SB0001 – Atualizar Instalação/Substituição de Tubete Magnético]
			ligacaoAgua = fachada.pesquisarLigacaoAgua(imovel.getId());
			if(ligacaoAgua != null){
				ligacaoAgua.setCorteRegistroTipo(corteRegistroTipo);
				ligacaoAgua.setUltimaAlteracao(new Date());
			}

			hidrometroInstalacaoHistorico = ligacaoAgua.getHidrometroInstalacaoHistorico();
			if(hidrometroInstalacaoHistorico != null){
				hidrometroInstalacaoHistorico.setCorteRegistroTipo(corteRegistroTipo);
				hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
			}

			// [SB0002 – Atualizar Ordem de Serviço]
			// 1. Caso a funcionalidade tenha sido chamada diretamente pelo Menu:

			if(ordemServico != null && efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getIdTipoDebito() != null){

				if(!"".equals(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getQuantidadeParcelas())
								&& efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getQuantidadeParcelas() != null){
					qtdParcelas = efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getQuantidadeParcelas();
				}

				ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);

				if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getValorDebito() != null){
					String valorDebito = efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getValorDebito();
					ordemServico.setValorAtual(Util.formatarMoedaRealparaBigDecimal(valorDebito));
				}

				if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getMotivoNaoCobranca() != null
								&& !efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getMotivoNaoCobranca().equals("-1")){

					ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
					servicoNaoCobrancaMotivo.setId(Util.converterStringParaInteger(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
									.getMotivoNaoCobranca()));
					ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
				}

				if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getPercentualCobranca() != null
								&& !efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getPercentualCobranca().equals("-1")){
					ordemServico.setPercentualCobranca(Util
									.formatarMoedaRealparaBigDecimal(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
													.getPercentualCobranca()));
				}

				ordemServico.setUltimaAlteracao(new Date());

			}
		}



		// regitrando operacao
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(ordemServico);

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
		integracaoComercialHelper.setRegistroMagneticoInstalacaoHistorico(hidrometroInstalacaoHistorico);
		integracaoComercialHelper.setOrdemServico(ordemServico);
		integracaoComercialHelper.setQtdParcelas(qtdParcelas);

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			try{
				fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(qtdParcelas));
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoSubstituicaoTubeteMagneticoAction.do");
				throw ex;
			}
		}

		if(!veioEncerrarOS){
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			try{

				fachada.efetuarInstalacaoSubstituicaoTubeteMagnetico(integracaoComercialHelper, usuario);
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoSubstituicaoTubeteMagneticoAction.do");
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
			montarPaginaSucesso(httpServletRequest, "Instalação/Substituição Tubete Magnético para o imóvel " + imovel.getId()
							+ " efetuada com sucesso.", "Efetuar outra Instalação de Hidrômetro",
							"exibirEfetuarInstalacaoHidrometroAction.do");
		}

		sessao.removeAttribute("efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm");

		return retorno;

	}
}
