/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.FiltroCreditoTipo;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por exibir a tela de geração da ordem de serviço
 * 
 * @author lms
 * @date 14/08/2006
 * @author Virgínia Melo
 * @date 27/03/2009
 *       Alteração para não listar os tipos de serviço que estão inativos.
 */
public class ExibirGerarOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		String forward = null;
		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		GerarOrdemServicoActionForm form = (GerarOrdemServicoActionForm) actionForm;
		Boolean primeiraVez = false;

		if(sessao.getAttribute("GerarOrdemServicoActionForm") != null){
			form = (GerarOrdemServicoActionForm) sessao.getAttribute("GerarOrdemServicoActionForm");
			sessao.removeAttribute("GerarOrdemServicoActionForm");
		}

		Collection<Integer> colecaoCompleta = new ArrayList<Integer>();
		if(Util.isVazioOuBranco(form.getColecaoCompleta())){
			primeiraVez = true;

		}else{
			colecaoCompleta = form.getColecaoCompleta();
		}

		Integer idRA = null;

		if(httpServletRequest.getParameter("numeroRA") != null){
			idRA = Integer.valueOf(httpServletRequest.getParameter("numeroRA"));
		}

		// Caso a solicitação venha da tela de Manter RA
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("telaManterRA"))){

			// Vários registros podem ser selecionados
			String[] idRegistrosRAGerarOrdemServico = null;

			if(!Util.isVazioOuBranco(form.getIdRegistrosRAGerarOrdemServico())){
				idRegistrosRAGerarOrdemServico = form.getIdRegistrosRAGerarOrdemServico();
			}

			if(!Util.isVazioOuBranco(form.getIdRegistrosRAGerarOrdemServico())){
				sessao.setAttribute("idRegistrosRAGerarOrdemServico", idRegistrosRAGerarOrdemServico);
			}else{
				idRegistrosRAGerarOrdemServico = (String[]) sessao.getAttribute("idRegistrosRAGerarOrdemServico");
			}

			if(idRegistrosRAGerarOrdemServico == null){
				idRegistrosRAGerarOrdemServico = new String[1];
				idRegistrosRAGerarOrdemServico[0] = idRA.toString();
			}

			Integer index = null;

			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("voltar"))){
				index = this.obterIndexRAColecao(colecaoCompleta, idRA);
				if(index < colecaoCompleta.size()){
					index = index + 1;
				}
			}else{
				if(primeiraVez){
					int primeiraRA = 0;

					// Recupera os ids das RA's selecionadas
					for(String idRAStr : idRegistrosRAGerarOrdemServico){
						if(primeiraRA == 0){
							idRA = Integer.valueOf(idRAStr);
							primeiraRA += 1;
						}
						colecaoCompleta.add(Integer.valueOf(idRAStr));
					}

					primeiraVez = false;
					sessao.setAttribute("primeiraVez", primeiraVez);
					form.setColecaoCompleta(colecaoCompleta);
				}
				index = this.obterIndexRAColecao(colecaoCompleta, idRA);
			}

			if(!Util.isVazioOuBranco(colecaoCompleta)){
				colecaoCompleta = form.getColecaoCompleta();
			}

			/*
			 * Verifica existência da utilização dos botões de navegação
			 */
			if(httpServletRequest.getParameter("raAnterior") != null || httpServletRequest.getParameter("proximoRA") != null){

				// Navegando na coleção
				if(httpServletRequest.getParameter("raAnterior") != null){

					index = index - 1;

				}

				if(httpServletRequest.getParameter("proximoRA") != null){

					index = index + 1;

				}
			}
			if(index >= colecaoCompleta.size()){
				index = colecaoCompleta.size() - 1;
			}

			try{
				idRA = ((Integer) ((List) colecaoCompleta).get(index));
			}catch(IndexOutOfBoundsException e){
				ActionForward afRetorno = actionMapping.findForward("exibirFiltrarRegistroAtendimento");
				return afRetorno;
			}

			if(index == 0){
				sessao.setAttribute("desabilitaBotaoAnterior", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoAnterior");
			}

			if((index + 1) >= colecaoCompleta.size()){
				sessao.setAttribute("desabilitaBotaoProximo", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoProximo");
			}

		}else{
			if(idRA == null){
				if(httpServletRequest.getParameter("idRegistroAtendimento") != null){
					idRA = Integer.valueOf(httpServletRequest.getParameter("idRegistroAtendimento"));
				}else{
					idRA = Integer.valueOf(form.getIdRegistroAtendimento());
				}
			}

		}

		if(httpServletRequest.getParameter("forward") != null){
			forward = getRealForward(httpServletRequest.getParameter("forward"));
			retorno = actionMapping.findForward(forward);
		}else if(httpServletRequest.getParameter("tipoConsulta") != null){
			if(httpServletRequest.getParameter("tipoConsulta").equals("tipoServico")){
				form.setIdServicoTipo(httpServletRequest.getParameter("idCampoEnviarDados"));
				form.setDescricaoServicoTipo(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
				retorno = actionMapping.findForward("exibirGerarOrdemServicoPopup");
			}
		}

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("forward") != null){
			form.setObservacao(null);
		}

		if(httpServletRequest.getParameter("caminhoRetornoGerarOs") != null){
			sessao.setAttribute("caminhoRetornoGerarOs", httpServletRequest.getParameter("caminhoRetornoGerarOs"));
		}
		if(httpServletRequest.getParameter("veioAcompanhamento") != null){
			sessao.setAttribute("veioAcompanhamento", httpServletRequest.getParameter("veioAcompanhamento"));
		}

		// Registro de Atendimento
		ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(idRA);
		RegistroAtendimento ra = fachada.validarRegistroAtendimento(idRA);

		// [SF0004] - Verificar unidade do usuário
		fachada.verificarUnidadeUsuario(ra, usuarioLogado);
		form.getOrdemServico().setRegistroAtendimento(ra);

		if(!Util.isVazioOuBranco(registroAtendimentoHelper) && !Util.isVazioOuBranco(registroAtendimentoHelper.getRegistroAtendimento())){
			if(!Util.isVazioOuBranco(registroAtendimentoHelper.getRegistroAtendimento().getImovel())){
				form.getOrdemServico().setImovel(registroAtendimentoHelper.getRegistroAtendimento().getImovel());
			}
		}

		// Serviço Tipo
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

				if(registroAtendimentoHelper.getRegistroAtendimento().getImovel() != null){
					Integer idImovelInformado = registroAtendimentoHelper.getRegistroAtendimento().getImovel().getId();

					if(Util.isNaoNuloBrancoZero(idImovelInformado) && servicoTipo.getDebitoTipo() != null){

						BigDecimal valorServicoLocalidade = fachada.verificarServicoTipoValorLocalidade(new Integer(idImovelInformado),
										new Integer(servicoTipo.getDebitoTipo().getId()));

						if(valorServicoLocalidade != null){
							valorServicoOriginal = valorServicoLocalidade.toString().replace('.', ',');
						}
					}

				}
				if(servicoTipo.getServicoTipoPrioridade() != null){
					idServicoTipoPrioridadeOriginal = servicoTipo.getServicoTipoPrioridade().getId();
					descricaoServicoTipoPrioridadeOriginal = servicoTipo.getServicoTipoPrioridade().getDescricao();
				}
				httpServletRequest.setAttribute("idServicoTipoEncontrada", "true");
			}else{
				form.setIdServicoTipo("");
				descricaoServicoTipo = "Tipo de Serviço inexistente";
			}
			form.getOrdemServico().setServicoTipo(servicoTipo);
		}

		form.setDescricaoServicoTipo(descricaoServicoTipo);
		form.setValorServicoOriginal(valorServicoOriginal);
		form.setIdPrioridadeServicoOriginal(String.valueOf(idServicoTipoPrioridadeOriginal));
		form.setDescricaoPrioridadeServicoOriginal(descricaoServicoTipoPrioridadeOriginal);

		// Ordem de Serviço Referência

		Integer idOrdemServicoReferencia = Util.converterStringParaInteger(form.getIdOrdemServicoReferencia());

		if(Util.validarNumeroMaiorQueZERO(idOrdemServicoReferencia)){

			OrdemServico os = fachada.pesquisarOrdemServico(idOrdemServicoReferencia);

			if(os == null){
				form.setIdOrdemServicoReferencia(null);
				form.setDescricaoOrdemServicoReferencia("Ordem de Serviço inexistente");
			}else{

				form.setIdOrdemServicoReferencia(os.getId().toString());
				form.setDescricaoOrdemServicoReferencia(os.getServicoTipo().getDescricao());
				form.getOrdemServico().setOsReferencia(os);

				httpServletRequest.setAttribute("osReferenciaEncontrada", "true");
			}
		}

		// Serviço Tipo Referência
		Integer idServicoTipoReferencia = Util.converterStringParaInteger(form.getIdServicoTipoReferencia());

		if(Util.validarNumeroMaiorQueZERO(idServicoTipoReferencia)){

			ServicoTipo st = fachada.pesquisarSevicoTipo(idServicoTipoReferencia);

			if(st != null){
				form.setIdServicoTipoReferencia(st.getId().toString());
				form.setDescricaoServicoTipoReferencia(st.getDescricao());
				form.getOrdemServico().setServicoTipoReferencia(st);
				httpServletRequest.setAttribute("servicoTipoReferenciaEncontrada", "true");
			}else{
				form.setIdServicoTipoReferencia("");
				form.setDescricaoServicoTipoReferencia("Tipo de Serviço referência inexistente");
			}
		}

		// Coleção de Serviços Tipo
		Collection colecaoServicosTipo = new ArrayList();
		for(Iterator iter = ra.getSolicitacaoTipoEspecificacao().getEspecificacaoServicoTipos().iterator(); iter.hasNext();){
			EspecificacaoServicoTipo est = (EspecificacaoServicoTipo) iter.next();
			if(est.getServicoTipo() != null && est.getServicoTipo().getIndicadorUso() == ConstantesSistema.INDICADOR_USO_ATIVO){
				colecaoServicosTipo.add(est.getServicoTipo());
			}
		}

		sessao.setAttribute("colecaoServicosTipo", colecaoServicosTipo);

		UnidadeOrganizacional unidadeAtualRA = fachada.obterUnidadeAtualRA(idRA);

		FiltroServicoTipoPrioridade filtroServicoTipoPrioridade = new FiltroServicoTipoPrioridade();
		filtroServicoTipoPrioridade.setCampoOrderBy(FiltroCreditoTipo.DESCRICAO);
		filtroServicoTipoPrioridade.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoServicoTipoPrioridade = fachada.pesquisar(filtroServicoTipoPrioridade, ServicoTipoPrioridade.class.getName());

		sessao.setAttribute("colecaoServicoTipoPrioridade", colecaoServicoTipoPrioridade);

		httpServletRequest.setAttribute("servicoTipo", servicoTipo);
		httpServletRequest.setAttribute("registroAtendimento", ra);
		httpServletRequest.setAttribute("registroAtendimentoHelper", registroAtendimentoHelper);
		httpServletRequest.setAttribute("unidadeAtualRA", unidadeAtualRA);

		sessao.setAttribute("ordemServico", form.getOrdemServico());
		sessao.setAttribute("GerarOrdemServicoActionForm", form);

		return retorno;
	}

	private String getRealForward(String upper){

		String forward = "";
		if("exibirGerarOrdemServico".toUpperCase().equals(upper.toUpperCase())){
			forward = "exibirGerarOrdemServico";
		}else if("exibirGerarOrdemServicoPopup".toUpperCase().equals(upper.toUpperCase())){
			forward = "exibirGerarOrdemServicoPopup";
		}else{
			throw new IllegalArgumentException();
		}
		return forward;
	}

	private Integer obterIndexRAColecao(Collection colecaoRAHelper, Integer idRA){

		int i = 0;
		Iterator iterator = colecaoRAHelper.iterator();
		Integer registroAtendimento = null;
		while(iterator.hasNext()){

			registroAtendimento = (Integer) iterator.next();

			if(!registroAtendimento.equals(Integer.valueOf(idRA))){
				i = i + 1;
			}else{
				break;
			}
		}

		return i;
	}

}