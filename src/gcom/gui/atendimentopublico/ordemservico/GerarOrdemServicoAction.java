/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <<Descri��o da Classe>>
 * 
 * @author lms
 * @date 22/08/2006
 */
public class GerarOrdemServicoAction
				extends GcomAction {

	private static final String CAMINHO_RETORNO = "";

	private static final String CAMINHO_GERAR_ORDEM_SERVICO = "gerarOrdemServicoAction";

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		GerarOrdemServicoActionForm form = (GerarOrdemServicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
						.getAttribute("mapServicosAutorizados");

		RegistroAtendimento ra = null;

		if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){

			// Inicio Alterado por S�vio Luiz
			if(form.getValorServicoOriginal() == null || form.getValorServicoOriginal().equals("")){

				Integer idRA = Integer.valueOf(form.getIdRegistroAtendimento());
				ra = fachada.validarRegistroAtendimento(idRA);

				// Servi�o Tipo
				ServicoTipo servicoTipo = null;

				Integer idServicoTipo = Util.converterStringParaInteger(form.getIdServicoTipo());
				String descricaoServicoTipo = null;
				String valorServicoOriginal = null;
				Integer idServicoTipoPrioridadeOriginal = null;
				String descricaoServicoTipoPrioridadeOriginal = null;

				if(Util.validarNumeroMaiorQueZERO(idServicoTipo)){
					servicoTipo = fachada.pesquisarSevicoTipo(idServicoTipo);

					fachada.validarServicoTipo(ra.getId(), idServicoTipo);

					if(servicoTipo != null){
						descricaoServicoTipo = servicoTipo.getDescricao();
						if(servicoTipo.getValor() != null){
							String valorFormatado = servicoTipo.getValor().toString().replace('.', ',');
							valorServicoOriginal = valorFormatado;
						}
						if(servicoTipo.getServicoTipoPrioridade() != null){
							idServicoTipoPrioridadeOriginal = servicoTipo.getServicoTipoPrioridade().getId();
							descricaoServicoTipoPrioridadeOriginal = servicoTipo.getServicoTipoPrioridade().getDescricao();
						}
						httpServletRequest.setAttribute("idServicoTipoEncontrada", "true");
					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Servi�o");
					}
					form.getOrdemServico().setServicoTipo(servicoTipo);
				}

				form.setDescricaoServicoTipo(descricaoServicoTipo);
				form.setValorServicoOriginal(valorServicoOriginal);
				form.setIdPrioridadeServicoOriginal(idServicoTipoPrioridadeOriginal + "");
				form.setDescricaoPrioridadeServicoOriginal(descricaoServicoTipoPrioridadeOriginal);
			}

			// Fim Alterado por S�vio Luiz

			if(ordemServico != null){
				ordemServico = form.setFormValues(ordemServico);
			}else{
				ordemServico = form.setFormValues(form.getOrdemServico());
			}

			// Verificar se existem servicos associados
			/*
			 * List<ServicoAssociadoAutorizacaoHelper> servicosParaAutorizacao =
			 * fachada.verificarServicosAssociadosParaAutorizacao(ordemServico.getServicoTipo(),
			 * EventoGeracaoServico.GERACAO_ORDEM_SERVICO, null);
			 * if (servicosParaAutorizacao != null && !servicosParaAutorizacao.isEmpty()) {
			 * //1
			 * //Parametros obrigatorios para a tela de autoriza��o
			 * autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();
			 * Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
			 * //TODO: Verificar qual ser� o action de autorizacao e setar os parametros necess�rios
			 * no map
			 * autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(
			 * parametrosCaminhoAutorizacao);
			 * autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_GERAR_ORDEM_SERVICO);
			 * Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
			 * //TODO: Verificar qual ser� o action de retorno e setar os parametros necess�rios no
			 * map
			 * 
			 * autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
			 * autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO);
			 * autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicosParaAutorizacao);
			 * sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER,
			 * autorizarServicoAssociadoHelper);
			 * retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");
			 * return retorno;
			 * }
			 */
		}

		Integer idLocalidade = null;
		Integer idSetorComercial = null;
		Integer idBairro = null;
		String parecerUnidadeDestinoString = null;
		Integer idUnidadeAtual = null;
		Object[] dadosDaRA = null;

		if(ra == null){
			ra = ordemServico.getRegistroAtendimento();
		}

		if(ra != null){
			dadosDaRA = fachada.pesquisarDadosLocalizacaoRegistroAtendimento(ra.getId());
		}

		if(dadosDaRA != null){
			idLocalidade = (Integer) dadosDaRA[0];
			idSetorComercial = (Integer) dadosDaRA[1];
			idBairro = (Integer) dadosDaRA[2];
		}

		Integer idServicoTipo = null;
		Integer idRegistroAtendimento = null;

		if(ordemServico != null && ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getId() != null){
			idServicoTipo = ordemServico.getServicoTipo().getId();
		}

		if(ordemServico != null && ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getId() != null){
			idRegistroAtendimento = ordemServico.getRegistroAtendimento().getId();

			UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtendimentoRA(ordemServico.getRegistroAtendimento().getId());
			idUnidadeAtual = unidadeAtual.getId();
		}

		// Verifica se ir� Gerar Guia de Pagamento ou Ordem de Servico
		Boolean flagGerarGuiaPagamento = fachada.validarGerarGuiaPagamentoOS(idServicoTipo, idRegistroAtendimento);

		Short qtdPrestacaoGuiaPagamento = null;
		if(form.getQuantidadePrestacoesGuiaPagamento() != null && !form.getQuantidadePrestacoesGuiaPagamento().equals("")){
			qtdPrestacaoGuiaPagamento = Short.valueOf(form.getQuantidadePrestacoesGuiaPagamento());
		} else {
			if(form.getOrdemServico() != null
							&& form.getOrdemServico().getServicoTipo() != null
							&& form.getOrdemServico().getServicoTipo().getIndicadorPagamentoAntecipado() == ConstantesSistema.SIM
											.intValue()){
				if(form.getQuantidadePrestacoesGuiaPagamento() == null || form.getQuantidadePrestacoesGuiaPagamento().equals("")){
					throw new ActionServletException("atencao.informe_campo", null, "Quantidade de Presta��o da Guia de Pagamento");
				}
			}
		}

		// gera a ordem de servi�o
		Integer idOrdemServico = fachada.gerarOrdemServico(ordemServico, usuario, mapServicosAutorizados, idLocalidade, idSetorComercial,
						idBairro, idUnidadeAtual, null, parecerUnidadeDestinoString, form.getIdOSPrincipal(), qtdPrestacaoGuiaPagamento);


		String veioAcompanhamento = (String) sessao.getAttribute("veioAcompanhamento");

		if(veioAcompanhamento != null){

			retorno = actionMapping.findForward("incluirOrdemServicoAcompanharRoteiroProgramacao");

			httpServletRequest.setAttribute("objetoConsulta", "2");
			httpServletRequest.setAttribute("idOrdemServico", "" + idOrdemServico);

		}else{
			String caminhoRetornoGerarOs = (String) sessao.getAttribute("caminhoRetornoGerarOs");
			String idRa = ordemServico.getRegistroAtendimento().getId().toString();

			if(!Util.isVazioOuBranco(form.getColecaoCompleta())){

				caminhoRetornoGerarOs = "exibirGerarOrdemServicoAction.do?telaManterRA=sim&forward=exibirGerarOrdemServico&caminhoRetornoGerarOs=exibirFiltrarRegistroAtendimentoAction.do&numeroRA="
								+ idRa + "&voltar=sim";

			}
			if(caminhoRetornoGerarOs != null && caminhoRetornoGerarOs.equals("exibirConsultarRegistroAtendimentoAction.do")){

				caminhoRetornoGerarOs = caminhoRetornoGerarOs + "?numeroRA=" + idRa;
			}

			String msg = "";
			if(!flagGerarGuiaPagamento){
				msg = "Gera��o da Ordem de Servi�o " + idOrdemServico + " para o RA - Registro de Atendimento n�mero "
								+ ordemServico.getRegistroAtendimento().getId() + " efetuada com sucesso.";
			}else{
				msg = "Gera��o da Guia de Pagamento " + idOrdemServico + " para o RA - Registro de Atendimento n�mero "
								+ ordemServico.getRegistroAtendimento().getId() + " efetuada com sucesso."
								+ ". Ap�s o pagamento da guia no vencimento, ser� gerado o Ordem de servi�o";
			}
			
			String msgImprimir = "";
			if(!flagGerarGuiaPagamento){
				msgImprimir = "gerarRelatorioOrdemServicoAction.do?idsOS=" + idOrdemServico;
			} else {
				msgImprimir = "exibirAtualizarGuiaPagamentoAction.do?manter=sim&idRegistroAtualizacao=" + idOrdemServico;
			}
			
			String msgTipo = "";
			if(!flagGerarGuiaPagamento){
				msgTipo = "OS";
			} else {
				msgTipo = "Guia de Pagamento";
			}			

			if(caminhoRetornoGerarOs == null){

				montarPaginaSucessoUmRelatorio(httpServletRequest, msg, "", "",
								"exibirGerarOrdemServicoAction.do?forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento=" + idRa,
								"Voltar", "Imprimir " + msgTipo, msgImprimir);

			}else{
				montarPaginaSucessoUmRelatorio(httpServletRequest, msg, "", "", caminhoRetornoGerarOs, "Voltar", "Imprimir " + msgTipo,
								msgImprimir);

			}
		}

		return retorno;
	}

}