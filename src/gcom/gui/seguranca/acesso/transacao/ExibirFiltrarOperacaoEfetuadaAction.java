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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.seguranca.acesso.transacao;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.FiltroImovelSituacaoTipo;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.FiltroUsuarioAcao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.email.ErroEmailException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarOperacaoEfetuadaAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author eduardo Henrique
	 * @date 21/05/2008
	 *       Incluído filtro de múltipla escolha de operações para visualização de Transações
	 *       efetuadas.
	 *       Filtro Argumento de Pesquisa foi retirado por enquanto, até revisão desta
	 *       funcionalidade.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		ActionForward retorno = actionMapping.findForward("filtrar");

		Fachada fachada = Fachada.getInstancia();

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(form.getAcao() == null){
			form.setAcao(httpServletRequest.getParameter("acao"));
		}
		sessao.setAttribute("acao", form.getAcao());
		sessao.setAttribute("usuarioAlteracao", null);
		// sessao.setAttribute("mostrarLogin", true);

		FiltroUsuarioAcao filtroUsuarioAcao = new FiltroUsuarioAcao();
		filtroUsuarioAcao.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroUsuarioAcao.INDICADO_USO, ConstantesSistema.NAO));

		Collection coll = Fachada.getInstancia().pesquisar(filtroUsuarioAcao, UsuarioAcao.class.getName());
		form.setCollUsuarioAcao(coll);
		if(form.getCollUsuarioAcao() == null || form.getCollUsuarioAcao().isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, " A Tabela Usuario Ação está ");
		}

		Collection<Funcionalidade> colecaoFuncionalidades = null;
		if(Util.isVazioOrNulo((Collection) sessao.getAttribute("colecaoFuncionalidades"))){
			colecaoFuncionalidades = this.getFachada().pesquisarFuncionalidadesComOperacaoAuditavel();

			if(Util.isVazioOrNulo(colecaoFuncionalidades)){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Funcionalidade com Operacao auditável");
			}else{
				sessao.setAttribute("colecaoFuncionalidades", colecaoFuncionalidades);
			}
		}

		Object valorSessao = sessao.getAttribute("idFuncionalidade");

		if(!equalValorSessaoToForm(form.getIdFuncionalidade(), valorSessao)
						|| (httpServletRequest.getParameter("funcionalidadeLimpa") != null && httpServletRequest.getParameter(
										"funcionalidadeLimpa").equals("1"))){
			sessao.removeAttribute("idFuncionalidade");
			sessao.removeAttribute("argumentos");
			sessao.removeAttribute("nomeOperacaoSelecionada");
		}
		valorSessao = sessao.getAttribute("idUsuario");
		if(!equalValorSessaoToForm(form.getIdUsuario(), valorSessao)
						|| (httpServletRequest.getParameter("usuarioLimpo") != null && httpServletRequest.getParameter("usuarioLimpo")
										.equals("1"))){
			sessao.removeAttribute("idUsuario");
			sessao.removeAttribute("nomeUsuario");
		}

		valorSessao = sessao.getAttribute("dataInicial");
		if(!equalValorSessaoToForm(form.getDataInicial(), valorSessao)
						|| !equalValorSessaoToForm(form.getDataFinal(), sessao.getAttribute("dataFinal"))
						|| (httpServletRequest.getParameter("dataLimpa") != null && httpServletRequest.getParameter("dataLimpa")
										.equals("1"))){
			sessao.removeAttribute("dataInicial");
			sessao.removeAttribute("dataFinal");
		}

		valorSessao = sessao.getAttribute("horaInicial");
		if(!equalValorSessaoToForm(form.getHoraInicial(), valorSessao)
						|| !equalValorSessaoToForm(form.getHoraFinal(), sessao.getAttribute("horaFinal"))
						|| (httpServletRequest.getParameter("horaLimpa") != null && httpServletRequest.getParameter("horaLimpa")
										.equals("1"))){
			sessao.removeAttribute("horaInicial");
			sessao.removeAttribute("horaFinal");
		}
		if(httpServletRequest.getParameter("argumentoLimpo") != null && httpServletRequest.getParameter("argumentoLimpo").equals("1")){
			sessao.removeAttribute("argumentos");
		}

		if(sessao.getAttribute("idFuncionalidade") != null){
			form.setIdFuncionalidade(String.valueOf((Integer) sessao.getAttribute("idFuncionalidade")));
		}
		if(sessao.getAttribute("idUsuario") != null){
			form.setIdUsuario((String) sessao.getAttribute("idUsuario"));
		}

		if(sessao.getAttribute("dataInicial") != null){
			String data = Util.formatarData((Date) sessao.getAttribute("dataInicial"));
			form.setDataInicial(data);
		}

		if(sessao.getAttribute("dataFinal") != null){
			String data = Util.formatarData((Date) sessao.getAttribute("dataFinal"));
			form.setDataFinal(data);
		}

		if(sessao.getAttribute("horaInicial") != null){
			String hora = Util.formatarHoraSemSegundos((Date) sessao.getAttribute("horaInicial"));
			form.setHoraInicial(hora);
		}

		if(sessao.getAttribute("horaFinal") != null){
			String hora = Util.formatarHoraSemSegundos((Date) sessao.getAttribute("horaFinal"));
			form.setHoraFinal(hora);
		}

		if(!"".equals(form.getIdUsuario()) && form.getIdUsuario() != null){
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// coloca parametro no filtro (virá o login, após pesquisa seta o idDoUsuário)
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, form.getIdUsuario()));

			// pesquisa
			coll = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());
			if(coll != null && !coll.isEmpty()){
				// O cliente foi encontrado
				// inserirImovelFiltrarActionForm.set("idCliente", ((Cliente)
				// ((List) clientes).get(0)).getId().toString());
				form.setNomeUsuario(((Usuario) ((List) coll).get(0)).getNomeUsuario());
				form.setIdUsuario(((Usuario) ((List) coll).get(0)).getId().toString());
				sessao.setAttribute("nomeUsuario", form.getNomeUsuario());
				sessao.setAttribute("idUsuario", form.getIdUsuario());

				form.setUsuarioNaoEncontrada("false");
			}else{
				form.setUsuarioNaoEncontrada("true");
				form.setNomeUsuario("");
			}
		}else{
			form.setNomeUsuario("");
		}

		// Pesquisando Resolucao Diretoria
		FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
		filtroResolucaoDiretoria.setCampoOrderBy(FiltroResolucaoDiretoria.NUMERO);
		Collection<ResolucaoDiretoria> collectionResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria,
						ResolucaoDiretoria.class.getName());
		sessao.setAttribute("collectionResolucaoDiretoria", collectionResolucaoDiretoria);
		// Fim de pesquisando Resolucao Diretoria

		// Pesquisando Tipo da Situacao do Imóvel
		FiltroImovelSituacaoTipo filtroImovelSituacaoTipo = new FiltroImovelSituacaoTipo();
		filtroImovelSituacaoTipo.setCampoOrderBy(FiltroImovelSituacaoTipo.DESCRICAO_IMOVEL_SITUACAO_TIPO);
		Collection<ImovelSituacaoTipo> collectionImovelSituacaoTipo = fachada.pesquisar(filtroImovelSituacaoTipo,
						ImovelSituacaoTipo.class.getName());
		sessao.setAttribute("collectionImovelSituacaoTipo", collectionImovelSituacaoTipo);
		// Fim de pesquisando Tipo da Situacao do Imóvel

		// AÇÕES DE POP-UP

		String acaoEnterDoPopUp = httpServletRequest.getParameter("acaoEnterDoPopUp");

		if("consultarImovel".equals(acaoEnterDoPopUp)){

			consultarImovel(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("consultarCliente".equals(acaoEnterDoPopUp)){

			consultarCliente(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("municipio".equals(acaoEnterDoPopUp)){

			consultarMunicipio(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("localidade".equals(acaoEnterDoPopUp)){

			consultarLocalidade(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("consultarSetorComercial".equals(acaoEnterDoPopUp)){

			consultarSetorComercial(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("consultarHidrometro".equals(acaoEnterDoPopUp)){

			consultarHidrometro(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("consultarOrdemServico".equals(acaoEnterDoPopUp)){

			consultarOrdemServico(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else if("consultarRegistroAtendimento".equals(acaoEnterDoPopUp)){

			consultarRegistroAtendimento(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}


		return retorno;
	}

	// ######################################

	public void consultarImovel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();
		Imovel imovel = fachada.consultarImovel(Integer.valueOf(form.getImovelArgumento().trim()));

		if(imovel != null){
			form.setImovelArgumento(form.getImovelArgumento().trim());
			form.setImovelNomeArgumento(imovel.getInscricaoFormatada());

		}else{
			httpServletRequest.setAttribute("idImovelNaoEncontrado", "true");
			form.setImovelArgumento(null);
			form.setImovelNomeArgumento("IMÓVEL INEXISTENTE");
		}

	}

	public void consultarCliente(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();
		Cliente cliente = fachada.consultarCliente(Integer.valueOf(form.getClienteArgumento().trim()));

		if(cliente != null){
			form.setClienteArgumento(form.getClienteArgumento().trim());
			form.setClienteNomeArgumento(cliente.getNome());

		}else{
			httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");
			form.setClienteArgumento(null);
			form.setClienteNomeArgumento("CLIENTE INEXISTENTE");
		}
	}

	public void consultarOrdemServico(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		OrdemServico ordemServico = fachada.recuperaOSPorId(Integer.valueOf(form.getOrdemServicoArgumento().trim()));

		if(ordemServico != null){
			form.setOrdemServicoArgumento(form.getOrdemServicoArgumento().trim());
			form.setOrdemServicoNomeArgumento(ordemServico.getServicoTipo().getDescricao());

		}else{
			httpServletRequest.setAttribute("ordemServicoNaoEncontrado", "true");
			form.setOrdemServicoArgumento(null);
			form.setOrdemServicoNomeArgumento("ORDEM SERVIÇO INEXISTENTE");
		}
	}

	public void consultarHidrometro(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(form.getNumeroHidrometroArgumento().trim());

		if(hidrometro != null){
			form.setIdHidrometroArgumento(hidrometro.getId());
			form.setNumeroHidrometroArgumento(form.getNumeroHidrometroArgumento().trim());
			form.setHidrometroNomeArgumento(hidrometro.getHidrometroSituacao().getDescricao());

		}else{
			httpServletRequest.setAttribute("hidrometroInexistente", "true");
			form.setIdHidrometroArgumento(null);
			form.setNumeroHidrometroArgumento(null);
			form.setHidrometroNomeArgumento("HIDRÔMETRO INEXISTENTE");
		}
	}

	public void consultarCobrancaAcao(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

	}

	public void consultarLocalidade(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;
		Integer idLocalidade = Integer.valueOf(form.getLocalidadeArgumento());
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			form.setLocalidadeArgumento("" + localidade.getId());
			form.setLocalidadeNomeArgumento(localidade.getDescricao());

		}else{
			httpServletRequest.setAttribute("localidadeEncontrada", "true");
			form.setLocalidadeArgumento(null);
			form.setLocalidadeNomeArgumento("Localidade inexistente");
		}
	}

	public void consultarMunicipio(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		Integer idMunicipio = Integer.valueOf(form.getMunicipioArgumento());
		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));

		Collection colecaoMunicipio = this.getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());

		if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){

			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
			form.setMunicipioArgumento("" + municipio.getId());
			form.setMunicipioNomeArgumento(municipio.getNome());

		}else{
			httpServletRequest.setAttribute("municipioNaoEncontrado", "true");
			form.setMunicipioArgumento(null);
			form.setMunicipioNomeArgumento("Município inexistente");
		}
	}

	public void consultarSetorComercial(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		Integer idSetorComercial = Integer.valueOf(form.getSetorComercialArgumento());

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, idSetorComercial));

		if(!Util.isVazioOuBrancoOuZero(form.getLocalidadeArgumento())){
			Integer idLocalidade = Integer.valueOf(form.getLocalidadeArgumento());
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, idLocalidade));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Localidade");
		}

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
			form.setSetorComercialArgumento("" + setorComercial.getCodigo());
			form.setSetorComercialNomeArgumento(setorComercial.getDescricao());

		}else{
			httpServletRequest.setAttribute("setorComercialNaoEncontrado", "true");
			form.setSetorComercialArgumento(null);
			form.setSetorComercialNomeArgumento("Setor Comercial inexistente");
		}
	}

	public void consultarUnidadeOperacional(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

	}

	public void consultarAvisoBancario(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

	}

	private boolean equalValorSessaoToForm(String valorForm, Object valorSessao){

		if(valorForm == null && valorSessao == null){
			return true;
		}
		if(valorForm != null){
			if(valorSessao == null){
				// form not null && sessao null
				return false;

			}
			String valorSessaoStr = valorSessao.toString();
			return valorForm.equals(valorSessaoStr);
		}
		// form == null && sessao not null
		return false;

	}

	public void consultarRegistroAtendimento(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ErroEmailException{

		FiltrarOperacaoEfetuadaActionForm form = (FiltrarOperacaoEfetuadaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, form
						.getRegistroAtendimentoArgumento()));

		Collection colecaoRegistroAtendimento = (Collection) fachada.pesquisar(filtroRegistroAtendimento,
						RegistroAtendimento.class.getName());

		RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecaoRegistroAtendimento);

		if(registroAtendimento != null){
			form.setRegistroAtendimentoArgumento(registroAtendimento.getId().toString());
			form.setRegistroAtendimentoNomeArgumento(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());
		}else{
			httpServletRequest.setAttribute("idRegistroAtendimentoNaoEncontrado", "true");
			form.setRegistroAtendimentoArgumento(null);
			form.setRegistroAtendimentoNomeArgumento("RA inexistente");
		}

	}

}
