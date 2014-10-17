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

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTipoValidacao;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.micromedicao.Rota;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SubBacia;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ExibirAtualizarImovelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirAtualizarImovelAction");

		// obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		String idImovel = null;

		StatusWizard statusWizard = null;
		if(httpServletRequest.getParameter("desfazer") == null){

			if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
				idImovel = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
			}else if(httpServletRequest.getAttribute("idRegistroAtualizacao") != null){
				idImovel = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
			}

			// verifica se chegou no atualizar imovel atraves da tela de filtrar devido a um unico
			// registro
			// ou atraves da lista de imoveis no manter imovel
			if(httpServletRequest.getAttribute("atualizar") != null){
				statusWizard = new StatusWizard("atualizarImovelWizardAction", "atualizarImovelAction", "cancelarAtualizarImovelAction",
								"exibirFiltrarImovelAction", "exibirAtualizarImovelAction.do", idImovel);
			}else if(httpServletRequest.getParameter("sucesso") != null){
				statusWizard = new StatusWizard("atualizarImovelWizardAction", "atualizarImovelAction", "cancelarAtualizarImovelAction",
								"exibirFiltrarImovelAction", "exibirAtualizarImovelAction.do", idImovel);
			}else{
				statusWizard = new StatusWizard("atualizarImovelWizardAction", "atualizarImovelAction", "cancelarAtualizarImovelAction",
								"exibirManterImovelAction", "exibirAtualizarImovelAction.do", idImovel);
			}

			if(!fachada.verificarPermissaoFuncionalidadeUsuario(usuario.getId(), "atualizarImovelLocalidadeAction.do",
							"exibirFiltrarImovelAction.do")){

				sessao.setAttribute("ExibirAbaLocalidade", "N");

				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(1, "LocalidadeD.gif", "LocalidadeD.gif",
								"exibirAtualizarImovelLocalidadeAction", "atualizarImovelLocalidadeEndereco"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(2, "EnderecoA.gif", "EnderecoD.gif",
								"exibirAtualizarImovelEnderecoAction", "atualizarImovelEnderecoAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(3, "ClienteA.gif", "ClienteD.gif",
								"exibirAtualizarImovelClienteAction", "atualizarImovelClienteAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(4, "SubcategoriaA.gif", "SubcategoriaD.gif",
								"exibirAtualizarImovelSubCategoriaAction", "atualizarImovelSubCategoriaAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(5, "CaracteristicaA.gif", "CaracteristicaD.gif",
								"exibirAtualizarImovelCaracteristicasAction", "atualizarImovelCaracteristicasAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(6, "ConclusaoA.gif", "ConclusaoD.gif",
								"exibirAtualizarImovelConclusaoAction", "atualizarImovelConclusaoAction"));
			}else{
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(1, "LocalidadeA.gif", "LocalidadeD.gif",
								"exibirAtualizarImovelLocalidadeAction", "atualizarImovelLocalidadeAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(2, "EnderecoA.gif", "EnderecoD.gif",
								"exibirAtualizarImovelEnderecoAction", "atualizarImovelEnderecoAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(3, "ClienteA.gif", "ClienteD.gif",
								"exibirAtualizarImovelClienteAction", "atualizarImovelClienteAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(4, "SubcategoriaA.gif", "SubcategoriaD.gif",
								"exibirAtualizarImovelSubCategoriaAction", "atualizarImovelSubCategoriaAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(5, "CaracteristicaA.gif", "CaracteristicaD.gif",
								"exibirAtualizarImovelCaracteristicasAction", "atualizarImovelCaracteristicasAction"));
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(6, "ConclusaoA.gif", "ConclusaoD.gif",
								"exibirAtualizarImovelConclusaoAction", "atualizarImovelConclusaoAction"));
			}



			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(1,
			// "LocalidadeA.gif", "LocalidadeD.gif",
			// "exibirAtualizarImovelLocalidadeAction", "atualizarImovelLocalidadeAction"));
			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(2,
			// "EnderecoA.gif", "EnderecoD.gif",
			// "exibirAtualizarImovelEnderecoAction", "atualizarImovelEnderecoAction"));
			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(3,
			// "ClienteA.gif", "ClienteD.gif",
			// "exibirAtualizarImovelClienteAction", "atualizarImovelClienteAction"));
			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(4,
			// "SubcategoriaA.gif", "SubcategoriaD.gif",
			// "exibirAtualizarImovelSubCategoriaAction", "atualizarImovelSubCategoriaAction"));
			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(5,
			// "CaracteristicaA.gif", "CaracteristicaD.gif",
			// "exibirAtualizarImovelCaracteristicasAction",
			// "atualizarImovelCaracteristicasAction"));
			// statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(6,
			// "ConclusaoA.gif", "ConclusaoD.gif",
			// "exibirAtualizarImovelConclusaoAction", "atualizarImovelConclusaoAction"));

			// manda o statusWizard para a sessao
			sessao.setAttribute(ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL, statusWizard);

		}else{
			statusWizard = (StatusWizard) sessao.getAttribute(ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
			idImovel = statusWizard.getId();
		}

		// limpa a sessão
		sessao.removeAttribute("imoveisPrincipal");
		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("imovelAtualizacao");
		sessao.removeAttribute("colecaoImovelSubCategorias");
		sessao.removeAttribute("colecaoImovelSubcategoriasRemoviadas");

		// Seta um atributo na sessao para ser utilizado nas abas
		sessao.setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		DynaValidatorForm atualizarImovelActionForm = (DynaValidatorForm) actionForm;

		atualizarImovelActionForm.reset(actionMapping, httpServletRequest);



		// FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();
		// filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioGrupo.GRUPO);
		// filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID,
		// usuario.getId()));
		//
		// Collection<UsuarioGrupo> colecaoUsuarioGrupo = fachada.pesquisar(filtroUsuarioGrupo,
		// UsuarioGrupo.class.getName());
		//
		// // Restringe o acesso à aba de Localidade para os grupos definidos no array
		// for(int i = 0; i < gruposRestricaoAbaLocalidade.length; i++){
		// if(!colecaoUsuarioGrupo.contains(gruposRestricaoAbaLocalidade[i])){
		// retorno = actionMapping.findForward("exibirAtualizarImovelComRestricaoAction");
		// break;
		// }
		// }

		boolean temPermissao = fachada.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_IMOVEL, usuario);

		if(!temPermissao){
			fachada.verificarExistenciaRegistroAtendimento(Integer.valueOf(idImovel), "atencao.imovel_existencia_registro_atendimento",
							EspecificacaoTipoValidacao.ALTERACAO_CADASTRAL);
		}

		Imovel imovel = fachada.consultarImovel(Integer.valueOf(idImovel));

		sessao.setAttribute("localidade", imovel.getLocalidade());
		sessao.setAttribute("setorComercial", imovel.getSetorComercial());
		sessao.setAttribute("quadra", imovel.getQuadra());
		sessao.setAttribute("rota", imovel.getRota());

		if(fachada.existeAlteracaoInscricaoPendente(imovel.getId())){
			atualizarImovelActionForm.set("msgAlteracaoInscrPendente", ConstantesAplicacao.get("atencao.alteracao.inscricao.pendente"));
		}else{
			atualizarImovelActionForm.set("msgAlteracaoInscrPendente", "");
		}

		// hint do imovel
		statusWizard.adicionarItemHint("Matrícula:", imovel.getId().toString());
		statusWizard.adicionarItemHint("Inscrição:", imovel.getInscricaoFormatada());
		statusWizard.adicionarItemHint("Endereço:", imovel.getEnderecoFormatado());

		// ------------------------------------------- Seta o form dinamico
		// ------------------------ Localidade

		atualizarImovelActionForm.set("matricula", imovel.getId().toString());
		atualizarImovelActionForm.set("idLocalidade", ""
						+ Util.adicionarZerosEsquedaNumero(3, formatarResultado(imovel.getLocalidade()).toString()));

		atualizarImovelActionForm.set("idSetorComercial", ""
						+ Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(imovel.getSetorComercial().getCodigo()).toString()));


		atualizarImovelActionForm.set("idQuadra", imovel.getQuadra().getNumeroQuadra() + "");

		Rota rota = imovel.getRota();
		if(rota != null){
			Integer idRota = rota.getId();
			atualizarImovelActionForm.set("idRota", Integer.toString(idRota));
			atualizarImovelActionForm.set("cdRota", rota.getCodigo().toString());
		}
		if(imovel.getNumeroSegmento() != null){
			atualizarImovelActionForm.set("nnSegmento", imovel.getNumeroSegmento().toString());
		}

		DistritoOperacional distritoOperacional = imovel.getDistritoOperacional();
		if(distritoOperacional != null){
			Integer idDistritoOperacional = distritoOperacional.getId();
			atualizarImovelActionForm.set("idDistritoOperacional", Integer.toString(idDistritoOperacional));
		}

		atualizarImovelActionForm.set("lote", "" + Util.adicionarZerosEsquedaNumero(4, Integer.valueOf(imovel.getLote()).toString()));

		atualizarImovelActionForm.set("subLote", ""
						+ Util.adicionarZerosEsquedaNumero(3, Short.valueOf(formatarResultado("" + imovel.getSubLote())).toString()));

		String testadaLote = null;
		if(imovel.getTestadaLote() != null){
			testadaLote = "" + imovel.getTestadaLote();
		}else{
			testadaLote = "";
		}
		atualizarImovelActionForm.set("testadaLote", testadaLote);
		// ------------------------ Endereço
		if(imovel.getLogradouroCep().getLogradouro() != null
						&& !imovel.getLogradouroCep().getLogradouro().getId().toString().trim().equalsIgnoreCase("")){
			atualizarImovelActionForm.set("logradouro", "" + formatarResultado(imovel.getLogradouroCep().getLogradouro()));
		}
		atualizarImovelActionForm.set("cep", "" + formatarResultado(imovel.getLogradouroCep().getCep()));

		// verifica o valor da area construida se nulo
		String valorAreaConstruida = null;
		if(imovel.getAreaConstruida() != null){
			valorAreaConstruida = formatarResultado(imovel.getAreaConstruida().toString()).replace('.', ',');
		}else{
			valorAreaConstruida = "";
		}
		atualizarImovelActionForm.set("areaConstruida", valorAreaConstruida);

		if(imovel.getAreaConstruidaFaixa() != null && !imovel.getAreaConstruidaFaixa().getId().equals("")){
			atualizarImovelActionForm.set("faixaAreaConstruida", "" + formatarResultado(imovel.getAreaConstruidaFaixa()));
		}else{
			atualizarImovelActionForm.set("faixaAreaConstruida", "" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		}

		// verifica o valor do volume reservatorio inferior se nulo
		String volumeReservatorioInferior = null;
		if(imovel.getVolumeReservatorioInferior() != null){
			volumeReservatorioInferior = formatarResultado(imovel.getVolumeReservatorioInferior().toString()).replace('.', ',');
		}else{
			volumeReservatorioInferior = "";
		}
		atualizarImovelActionForm.set("reservatorioInferior", volumeReservatorioInferior);

		// verifica se o valor da faixa do volume reservatorio é nulo
		String volumeFaixaReservatorioInferior = null;
		if(imovel.getReservatorioVolumeFaixaInferior() != null && !imovel.getReservatorioVolumeFaixaInferior().getId().equals("")){
			volumeFaixaReservatorioInferior = formatarResultado(imovel.getReservatorioVolumeFaixaInferior().getId().toString().replace('.',
							','));
		}else{
			volumeFaixaReservatorioInferior = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("faixaReservatorioInferior", volumeFaixaReservatorioInferior);

		// verifica o valor do volume reservatorio superior se nulo
		String volumeReservatorioSuperior = null;
		if(imovel.getVolumeReservatorioSuperior() != null){
			volumeReservatorioSuperior = formatarResultado(imovel.getVolumeReservatorioSuperior().toString()).replace('.', ',');
		}else{
			volumeReservatorioSuperior = "";
		}
		atualizarImovelActionForm.set("reservatorioSuperior", volumeReservatorioSuperior);

		// verifica se o valor da faixa do volume reservatorio superior eh nulo
		String volumeFaixaReservatorioSuperior = null;
		if(imovel.getReservatorioVolumeFaixaSuperior() != null && !imovel.getReservatorioVolumeFaixaSuperior().getId().equals("")){
			volumeFaixaReservatorioSuperior = formatarResultado(imovel.getReservatorioVolumeFaixaSuperior().getId().toString().replace('.',
							','));
		}else{
			volumeFaixaReservatorioSuperior = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("faixaResevatorioSuperior", volumeFaixaReservatorioSuperior);

		// verifica o valor da piscina se nula
		String voPiscina = null;
		if(imovel.getVolumePiscina() != null){
			voPiscina = formatarResultado(imovel.getVolumePiscina().toString()).replace('.', ',');
		}else{
			voPiscina = "";
		}
		atualizarImovelActionForm.set("piscina", voPiscina);

		// verifica se o valor da faixa do volume da piscina eh nulo.
		String volumeFaixaPiscina = null;
		if(imovel.getPiscinaVolumeFaixa() != null && !imovel.getPiscinaVolumeFaixa().getId().equals("")){
			volumeFaixaPiscina = formatarResultado(imovel.getPiscinaVolumeFaixa().getId().toString().replace('.', ','));
		}else{
			volumeFaixaPiscina = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("faixaPiscina", volumeFaixaPiscina);

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			if(imovel.getIndicadorJardim() != null){
				atualizarImovelActionForm.set("jardim", imovel.getIndicadorJardim().toString());
			}else{
				atualizarImovelActionForm.set("jardim", "2");
			}
			// ********** Companhia - ADA ******************************
		}

		// Sequencial Rota
		if(imovel.getNumeroSequencialRota() != null){
			atualizarImovelActionForm.set("sequencialRota", imovel.getNumeroSequencialRota().toString());
		}else{
			atualizarImovelActionForm.set("sequencialRota", "");
		}

		if(imovel.getFuncionario() != null){
			atualizarImovelActionForm.set("idFuncionario", imovel.getFuncionario().getId().toString());
			atualizarImovelActionForm.set("nomeFuncionario", imovel.getFuncionario().getNome());
		}else{
			atualizarImovelActionForm.set("idFuncionario", null);
			atualizarImovelActionForm.set("nomeFuncionario", null);
		}

		// paviemtnoCalcada
		String pavimentoCalcada = null;
		if(imovel.getPavimentoCalcada() != null && !imovel.getPavimentoCalcada().equals("")){
			pavimentoCalcada = formatarResultado(imovel.getPavimentoCalcada().getId().toString().replace('.', ','));
		}else{
			pavimentoCalcada = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("pavimentoCalcada", pavimentoCalcada);

		// pavimentoRua
		String pavimentoRua = null;
		if(imovel.getPavimentoRua() != null && !imovel.getPavimentoRua().equals("")){
			pavimentoRua = "" + formatarResultado(imovel.getPavimentoRua());
		}else{
			pavimentoRua = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("pavimentoRua", pavimentoRua);

		//
		String fonteAbastecimento = null;
		if(imovel.getFonteAbastecimento() != null && !imovel.getFonteAbastecimento().equals("")){
			fonteAbastecimento = "" + formatarResultado(imovel.getFonteAbastecimento());
		}else{
			fonteAbastecimento = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("fonteAbastecimento", fonteAbastecimento);

		//
		String situacaoLigacaoAgua = null;
		if(imovel.getLigacaoAguaSituacao() != null && !imovel.getLigacaoAguaSituacao().equals("")){
			situacaoLigacaoAgua = formatarResultado(imovel.getLigacaoAguaSituacao().getId().toString().replace('.', ','));
		}else{
			situacaoLigacaoAgua = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("situacaoLigacaoAgua", situacaoLigacaoAgua);

		// atualizarImovelActionForm.set("situacaoLigacaoAgua", ""+
		// formatarResultado(imovel.getLigacaoAguaSituacao()));
		String situacaoLigacaoEsgoto = null;
		if(imovel.getLigacaoEsgotoSituacao() != null && !imovel.getLigacaoEsgotoSituacao().equals("")){
			situacaoLigacaoEsgoto = formatarResultado(imovel.getLigacaoEsgotoSituacao().getId().toString().replace('.', ','));
		}else{
			situacaoLigacaoEsgoto = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("situacaoLigacaoEsgoto", situacaoLigacaoEsgoto);

		//
		String perfilImovel = null;
		if(imovel.getImovelPerfil() != null && !imovel.getImovelPerfil().equals("")){
			perfilImovel = "" + formatarResultado(imovel.getImovelPerfil());
		}else{
			perfilImovel = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("perfilImovel", perfilImovel);

		//
		String poco = null;
		if(imovel.getPocoTipo() != null && !imovel.getPocoTipo().equals("")){
			poco = "" + formatarResultado(imovel.getPocoTipo());
		}else{
			poco = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("poco", poco);

		//
		String tipoDespejo = null;
		if(imovel.getDespejo() != null && !imovel.getDespejo().equals("")){
			tipoDespejo = formatarResultado(imovel.getDespejo().getId().toString().replace('.', ','));
		}else{
			tipoDespejo = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("tipoDespejo", tipoDespejo);

		// Setor de Abastecimento
		SetorAbastecimento setorAbastecimento = imovel.getSetorAbastecimento();
		String idSetorAbastecimentoStr = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;

		if(!Util.isVazioOuBranco(setorAbastecimento)){
			Integer idSetorAbastecimento = setorAbastecimento.getId();
			idSetorAbastecimentoStr = Integer.toString(idSetorAbastecimento);
		}

		atualizarImovelActionForm.set("setorAbastecimento", idSetorAbastecimentoStr);

		// Sub-bacia
		SubBacia subBacia = imovel.getSubBacia();
		String idSubBaciaStr = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;

		if(!Util.isVazioOuBranco(subBacia)){
			Integer idSubBacia = subBacia.getId();
			idSubBaciaStr = Integer.toString(idSubBacia);
		}

		atualizarImovelActionForm.set("subBacia", idSubBaciaStr);

		//
		String padraoConstrucao = null;
		if(imovel.getPadraoConstrucao() != null && !imovel.getPadraoConstrucao().equals("")){
			padraoConstrucao = formatarResultado(imovel.getPadraoConstrucao().getId().toString().replace('.', ','));
		}else{
			padraoConstrucao = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("padraoConstrucao", padraoConstrucao);

		//
		String esgotamento = null;
		if(imovel.getEsgotamento() != null && !imovel.getEsgotamento().equals("")){
			esgotamento = formatarResultado(imovel.getEsgotamento().getId().toString().replace('.', ','));
		}else{
			esgotamento = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("esgotamento", esgotamento);

		String faixaRendaFamiliar = null;
		if(imovel.getRendaFamiliarFaixa() != null && !imovel.getRendaFamiliarFaixa().equals("")){
			faixaRendaFamiliar = formatarResultado(imovel.getRendaFamiliarFaixa().getId().toString().replace('.', ','));
		}else{
			faixaRendaFamiliar = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
		atualizarImovelActionForm.set("faixaRendaFamiliar", faixaRendaFamiliar);

		// ------------------------ Cliente

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			Boolean indicadorContratoConsumo = Boolean.FALSE;

			if(ConstantesSistema.SIM.equals(imovel.getIndicadorContratoConsumo())){
				indicadorContratoConsumo = Boolean.TRUE;
			}

			atualizarImovelActionForm.set("indicadorContratoConsumo", indicadorContratoConsumo);
		}

		// ------------------------ Conclusão
		String numeroPontos = null;
		// verifica o numero de pontos de utilização
		if(imovel.getNumeroPontosUtilizacao() != null){
			numeroPontos = "" + imovel.getNumeroPontosUtilizacao();
		}else{
			numeroPontos = "";
		}
		atualizarImovelActionForm.set("numeroPontos", numeroPontos);

		String numeroQuarto = null;
		// verifica o numero de quartos
		if(imovel.getNumeroQuarto() != null){
			numeroQuarto = "" + imovel.getNumeroQuarto();
		}else{
			numeroQuarto = "";
		}
		atualizarImovelActionForm.set("numeroQuarto", numeroQuarto);

		String numeroBanheiro = null;
		// verifica o numero de banheiros
		if(imovel.getNumeroBanheiro() != null){
			numeroBanheiro = "" + imovel.getNumeroBanheiro();
		}else{
			numeroBanheiro = "";
		}
		atualizarImovelActionForm.set("numeroBanheiro", numeroBanheiro);

		String numeroAdulto = null;
		// verifica o numero de adultos
		if(imovel.getNumeroAdulto() != null){
			numeroAdulto = "" + imovel.getNumeroAdulto();
		}else{
			numeroAdulto = "";
		}
		atualizarImovelActionForm.set("numeroAdulto", numeroAdulto);

		String numeroCrianca = null;
		// verifica o numero de criancas
		if(imovel.getNumeroCrianca() != null){
			numeroCrianca = "" + imovel.getNumeroCrianca();
		}else{
			numeroCrianca = "";
		}
		atualizarImovelActionForm.set("numeroCrianca", numeroCrianca);

		String numeroMoradorTrabalhador = null;
		// verifica o numero de morador trabalhador
		if(imovel.getNumeroMoradorTrabalhador() != null){
			numeroMoradorTrabalhador = "" + imovel.getNumeroMoradorTrabalhador();
		}else{
			numeroMoradorTrabalhador = "";
		}
		atualizarImovelActionForm.set("numeroMoradorTrabalhador", numeroMoradorTrabalhador);

		// verifica o numero de moradores
		String numeroMoradores = null;
		if(imovel.getNumeroMorador() != null){
			numeroMoradores = "" + imovel.getNumeroMorador();
		}else{
			numeroMoradores = "";
		}
		atualizarImovelActionForm.set("numeroMoradores", numeroMoradores);
		// verifica se o numero do iptu é nulo
		String numeroIptu = null;
		if(imovel.getNumeroIptu() != null){
			numeroIptu = formatarResultado(imovel.getNumeroIptu().toString());
		}else{
			numeroIptu = "";
		}
		atualizarImovelActionForm.set("numeroIptu", numeroIptu);
		// verifica se o numero do contrato da celpe é nulo
		String numeroContratoCelpe = null;
		if(imovel.getNumeroCelpe() != null){
			numeroContratoCelpe = formatarResultado(imovel.getNumeroCelpe().toString());
		}else{
			numeroContratoCelpe = "";
		}
		atualizarImovelActionForm.set("numeroContratoCelpe", numeroContratoCelpe);
		if(imovel.getImovelContaEnvio() != null){
			atualizarImovelActionForm.set("imovelContaEnvio", "" + formatarResultado(imovel.getImovelContaEnvio()));
		}else{
			atualizarImovelActionForm.set("imovelContaEnvio", ConstantesSistema.NUMERO_NAO_INFORMADO);
		}

		// atualizarImovelActionForm.set("tipoOcupacao", ""
		// + formatarResultado(imovel.getNomeConta()));
		// verifica se a coordena X é nula
		String coordenadaUtmX = null;
		if(imovel.getCoordenadaX() != null){
			coordenadaUtmX = imovel.getCoordenadaX().toString();
		}else{
			coordenadaUtmX = "";
		}
		atualizarImovelActionForm.set("cordenadasUtmX", coordenadaUtmX);
		// verifica se a coordena Y é nula
		String coordenadaUtmY = null;
		if(imovel.getCoordenadaY() != null){
			coordenadaUtmY = imovel.getCoordenadaY().toString();
		}else{
			coordenadaUtmY = "";
		}
		atualizarImovelActionForm.set("cordenadasUtmY", coordenadaUtmY);

		/*
		 * Alterado por Raphael Rossiter em 10/09/2007 (Analista: Aryed Lins)
		 * OBJ: Marcar o indicador de emissão de extrato de faturamento, na aba de conclusão,
		 * de acordo com o campo IndicadorEmissaoExtratoFaturamento da tabela de Imovel
		 */
		if(imovel.getIndicadorEmissaoExtratoFaturamento() != null){
			atualizarImovelActionForm.set("extratoResponsavel", imovel.getIndicadorEmissaoExtratoFaturamento().toString());
		}else{
			atualizarImovelActionForm.set("extratoResponsavel", ConstantesSistema.NAO.toString());
		}

		// --------------------------------------------fim form dinamico

		// ------------------------ Coleçao endereço imovel
		Imovel imovel_endereco = new Imovel();
		LogradouroCep logradouroCep = new LogradouroCep();
		LogradouroBairro logradouroBairro = new LogradouroBairro();

		if(imovel.getLogradouroCep() != null && imovel.getLogradouroCep().getCep() != null
						&& !imovel.getLogradouroCep().getCep().getCepId().toString().trim().equalsIgnoreCase("")){

			logradouroCep.setCep(imovel.getLogradouroCep().getCep());
		}

		if(imovel.getLogradouroCep() != null && imovel.getLogradouroCep().getLogradouro() != null
						&& !imovel.getLogradouroCep().getLogradouro().getId().toString().trim().equalsIgnoreCase("")){

			logradouroCep.setLogradouro(imovel.getLogradouroCep().getLogradouro());
		}

		if(logradouroCep.getCep() != null && logradouroCep.getLogradouro() != null){

			logradouroCep = fachada.pesquisarAssociacaoLogradouroCep(logradouroCep.getCep().getCepId(), logradouroCep.getLogradouro()
							.getId());

			imovel_endereco.setLogradouroCep(logradouroCep);
		}

		if(imovel.getComplementoEndereco() != null && !imovel.getComplementoEndereco().trim().equalsIgnoreCase("")){

			imovel_endereco.setComplementoEndereco(imovel.getComplementoEndereco());
		}

		if(imovel.getNumeroImovel() != null && !imovel.getNumeroImovel().trim().equalsIgnoreCase("")){

			imovel_endereco.setNumeroImovel(imovel.getNumeroImovel());
		}
		if(imovel.getLogradouroBairro() != null && imovel.getLogradouroBairro().getBairro() != null
						&& !imovel.getLogradouroBairro().getBairro().getId().toString().trim().equalsIgnoreCase("")){

			logradouroBairro = fachada.pesquisarAssociacaoLogradouroBairro(imovel.getLogradouroBairro().getBairro().getId(), logradouroCep
							.getLogradouro().getId());

		}
		if(imovel.getEnderecoReferencia() != null){
			imovel_endereco.setEnderecoReferencia(imovel.getEnderecoReferencia());
		}

		if(logradouroBairro != null && logradouroBairro.getBairro() != null && logradouroBairro.getLogradouro() != null){

			logradouroBairro = fachada.pesquisarAssociacaoLogradouroBairro(logradouroBairro.getBairro().getId(), logradouroBairro
							.getLogradouro().getId());

			imovel_endereco.setLogradouroBairro(logradouroBairro);
		}

		Collection imoveisPrincipais = new HashSet();
		if(imovel.getImovelPrincipal() != null){
			imoveisPrincipais.add(imovel.getImovelPrincipal());
			atualizarImovelActionForm.set("idImovel", "" + formatarResultado(imovel.getImovelPrincipal()));
		}else{
			atualizarImovelActionForm.set("idImovel", "");

		}
		sessao.setAttribute("imoveisPrincipal", imoveisPrincipais);

		// -------------- Imovel Endereço
		Collection enderecos = new HashSet();

		// A data de última alteração será utilizada para identificar o endereço do imóvel.
		imovel_endereco.setUltimaAlteracao(new Date());

		enderecos.add(imovel_endereco);
		sessao.setAttribute("colecaoEnderecos", enderecos);

		// -------------- Imovel Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		// Objetos que serão retornados pelo Hibernate
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, Integer.valueOf(idImovel)));

		Collection imoveisCliente = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
		sessao.setAttribute("imovelClientesNovos", imoveisCliente);

		if(imoveisCliente != null && !imoveisCliente.isEmpty()){
			Iterator iteratorClientes = imoveisCliente.iterator();

			while(iteratorClientes.hasNext()){
				ClienteImovel clienteImovel = (ClienteImovel) iteratorClientes.next();
				if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.USUARIO.intValue()){
					sessao.setAttribute("idClienteImovelUsuario", clienteImovel.getCliente().getId());
					atualizarImovelActionForm.set("idClienteImovelUsuario", clienteImovel.getCliente().getId().toString());
				}else if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.RESPONSAVEL.intValue()){
					sessao.setAttribute("idClienteImovelResponsavel", clienteImovel.getCliente().getId());
				}
				if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
					if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.PROPRIETARIO.intValue()){
						sessao.setAttribute("idClienteImovelProprietario", clienteImovel.getCliente().getId());
						atualizarImovelActionForm.set("idClienteImovelProprietario", clienteImovel.getCliente().getId().toString());
					}
				}

			}
		}

		// -------------- SubCategoria
		FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();

		// Objetos que serão retornados pelo Hibernate
		filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.subcategoria.categoria");
		filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.imovel");

		filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, Integer.valueOf(idImovel)));
		Collection subCategorias = fachada.pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());
		sessao.setAttribute("colecaoImovelSubCategorias", subCategorias);
		Collection colecao = new ArrayList();
		colecao.addAll(subCategorias);
		sessao.setAttribute("colecaoImovelSubCategoriasOriginal", colecao);

		if(!subCategorias.isEmpty()){
			ImovelSubcategoria imovelSubCategoria = (ImovelSubcategoria) subCategorias.iterator().next();
			String idSubCategoriaImovel = null;
			if(imovelSubCategoria.getComp_id() != null && imovelSubCategoria.getComp_id().getSubcategoria() != null){
				idSubCategoriaImovel = formatarResultado(imovelSubCategoria.getComp_id().getSubcategoria().getId().toString().replace('.',
								','));
			}else{
				idSubCategoriaImovel = "" + ConstantesSistema.NUMERO_NAO_INFORMADO;
			}
			atualizarImovelActionForm.set("idSubCategoriaImovel", idSubCategoriaImovel);
		}

		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
			FiltroImovelConsumoFaixaAreaCategoria filtroImovelConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria();
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL);
			filtroImovelConsumoFaixaAreaCategoria
							.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA);
			filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID,
							Integer.valueOf(idImovel)));
			filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(
							FiltroImovelConsumoFaixaAreaCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection imovelConsumoFaixaAreaCategorias = fachada.pesquisar(filtroImovelConsumoFaixaAreaCategoria,
							ImovelConsumoFaixaAreaCategoria.class.getName());

			sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", imovelConsumoFaixaAreaCategorias);
		}

		// Parâmetro para ativar ou não a obrigatoriedade de alguns campos
		try{
			if(ParametroCadastro.P_CAMPOS_OBRIGATORIOS_IMOVEL.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorCamposObrigatorios", true);
			}else{
				sessao.removeAttribute("indicadorCamposObrigatorios");
			}
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		sessao.setAttribute("imovelAtualizacao", imovel);

		sessao.setAttribute("statusWizard", statusWizard);
		return retorno;
	}

	private Integer formatarResultado(Object parametro){

		if(parametro != null){
			try{
				Class[] clasz = null;
				Object[] obj = null;
				return (Integer) parametro.getClass().getMethod("getId", clasz).invoke(parametro, obj);
			}catch(SecurityException ex){
				return null;
			}catch(NoSuchMethodException ex){
				return null;
			}catch(InvocationTargetException ex){
				return null;
			}catch(IllegalArgumentException ex){
				return null;
			}catch(IllegalAccessException ex){
				return null;
			}
		}else{
			return Integer.valueOf(0);
		}
	}

	// ------------------------------------------- Funcoes

	private String formatarResultado(String parametro){

		if(parametro != null){
			return parametro.trim();
		}else{
			return "";
		}
	}

}