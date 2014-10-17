
package gcom.gui.atendimentopublico.registromagnetico;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3063] Efetuar Instalação/Substituição de Registro Magnético
 * 
 * @author Leonardo José De S. C. Angelim
 * @date 14/08/2012
 */
public class EfetuarInstalacaoSubstituicaoRegistroMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm = (EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm) actionForm;

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		OrdemServico ordemServico = null;

		/*
		 * [UC0107] Registrar Transação
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
						Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO, new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getVeioEncerrarOS() != null && efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm
										.getVeioEncerrarOS().equalsIgnoreCase("true"))){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			veioEncerrarOS = Boolean.FALSE;
		}

		if(sessao.getAttribute("ordemServico") == null){
			String idOrdemServico = null;

			if(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getIdOrdemServico() != null){
				idOrdemServico = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getIdOrdemServico();
			}else{
				idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

				sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest
								.getAttribute("caminhoRetornoIntegracaoComercial"));
			}

			if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

				ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

				if(ordemServico != null){

					fachada.validarInstalacaoSubstituicaoRegistroMagneticoExibir(ordemServico, veioEncerrarOS);
				}else{
					throw new ActionServletException("atencao.ordem_servico.inexistente");
				}
			}
		}else{

			ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
		}

		String idServicoMotivoNaoCobranca = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getMotivoNaoCobranca();
		String valorPercentual = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getPercentualCobranca();
		String qtdParcelas = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getQuantidadeParcelas();

		if(ordemServico != null && efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getIdTipoDebito() != null){

			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);

			BigDecimal valorAtual = new BigDecimal(0);
			if(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getValorDebito() != null){
				String valorDebito = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getValorDebito().toString().replace(".", "");

				valorDebito = valorDebito.replace(",", ".");

				valorAtual = new BigDecimal(valorDebito);

				ordemServico.setValorAtual(valorAtual);
			}

			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;

			if(idServicoMotivoNaoCobranca != null
							&& !idServicoMotivoNaoCobranca.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(new Integer(idServicoMotivoNaoCobranca));
			}

			if(idServicoMotivoNaoCobranca == null){
				ordemServico.setServicoNaoCobrancaMotivo(null);
			}else{
				ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);
			}

			if(valorPercentual != null){
				ordemServico.setPercentualCobranca(new BigDecimal(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm
								.getPercentualCobranca()));
			}else{
				ordemServico.setPercentualCobranca(null);
			}
		}

		Imovel imovel = null;

		if(ordemServico.getRegistroAtendimento() != null){
			imovel = ordemServico.getRegistroAtendimento().getImovel();
		}else{
			imovel = ordemServico.getCobrancaDocumento().getImovel();
		}

		LigacaoAgua ligacaoAgua = fachada.pesquisarLigacaoAgua(imovel.getId());

		if(ligacaoAgua == null){
			ligacaoAgua = new LigacaoAgua();
		}

		// regitrando operacao
		ordemServico.setOperacaoEfetuada(operacaoEfetuada);
		ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(ordemServico);

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setImovel(imovel);
		integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
		integracaoComercialHelper.setRegistroMagneticoInstalacaoHistorico(ligacaoAgua.getHidrometroInstalacaoHistorico());
		integracaoComercialHelper.setOrdemServico(ordemServico);
		integracaoComercialHelper.setQtdParcelas(qtdParcelas);

		if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){

			try{

				fachada.verificarQuantidadeParcelas(usuario, Util.obterShort(qtdParcelas));
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoSubstituicaoRegistroMagneticoAction.do");
				throw ex;
			}
		}

		if(!veioEncerrarOS){
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			try{
				fachada.efetuarInstalacaoSubstituicaoRegistroMagnetico(integracaoComercialHelper, usuario);
		
			}catch(FachadaException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEfetuarInstalacaoSubstituicaoRegistroMagneticoAction.do");
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
			montarPaginaSucesso(httpServletRequest, "Instalação/Substituição de Registro Magnético para o imóvel " + imovel.getId()
							+ " efetuada com sucesso", "Efetuar outra Instalação/Substituição de Registro Magnético",
							"exibirEfetuarInstalacaoSubstituicaoRegistroMagneticoAction.do?menu=sim");
		}

		return retorno;

	}

}
