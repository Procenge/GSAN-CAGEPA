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

package gcom.gui.arrecadacao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarDadosDiariosArrecadacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o formul�rio
		FiltrarDadosDiariosArrecadacaoActionForm filtrarDadosDiariosArrecadacaoActionForm = (FiltrarDadosDiariosArrecadacaoActionForm) actionForm;

		Collection colecaoArrecadacaoDadosDiarios = null;

		// Recupera os par�metros do form
		String periodoArrecadacaoInicial = (String) filtrarDadosDiariosArrecadacaoActionForm.getPeriodoArrecadacaoInicio();
		String periodoArrecadacaoFinal = (String) filtrarDadosDiariosArrecadacaoActionForm.getPeriodoArrecadacaoFim();
		String localidade = (String) filtrarDadosDiariosArrecadacaoActionForm.getLocalidade();
		String idConcessionaria = (String) filtrarDadosDiariosArrecadacaoActionForm.getIdConcessionaria();
		String setorComercial = (String) filtrarDadosDiariosArrecadacaoActionForm.getIdSetorComercialFiltro();
		String idArrecadador = (String) filtrarDadosDiariosArrecadacaoActionForm.getIdArrecadador();
		String idGerenciaRegional = (String) filtrarDadosDiariosArrecadacaoActionForm.getIdGerenciaRegional();
		String unidadeNegocioId = (String) filtrarDadosDiariosArrecadacaoActionForm.getunidadeNegocioId();
		String idElo = (String) filtrarDadosDiariosArrecadacaoActionForm.getIdElo();
		String[] idsImovelPerfil = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getImovelPerfil();
		String[] idsLigacaoAgua = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getLigacaoAgua();
		String[] idsLigacaoEsgoto = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getLigacaoEsgoto();
		String[] idsCategoria = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getCategoria();
		String[] idsEsferaPoder = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getEsferaPoder();
		String[] idsDocumentosTipos = (String[]) filtrarDadosDiariosArrecadacaoActionForm.getDocumentoTipo();

		// Utilizado para atribuir o n�mero da p�gina(aba) dinamicamente pelo fato da aba
		// Concession�ria ter a possibilidade de n�o ser exibida,
		// pois como ela est� entre outras abas, caso a numera��o fosse fixa e ela n�o fosse
		// exibida, as abas n�o iriam ficar com a numera��o em sequencia, ocasionando um erro nos
		// bot�es Voltar e Avan�ar.
		int nuPaginaInicial = 1;
		int nuPaginaParametro = nuPaginaInicial;
		int nuPaginaGerencia = ++nuPaginaInicial;
		int nuPaginaConcessionaria = 0;
		if(sessao.getAttribute("indEmpresaTrabalhaConcessionaria") != null
						&& ((String) sessao.getAttribute("indEmpresaTrabalhaConcessionaria")).equals(ConstantesSistema.SIM.toString())){
			nuPaginaConcessionaria = ++nuPaginaInicial;
			sessao.setAttribute("nuPaginaConcessionaria", String.valueOf(nuPaginaConcessionaria));
		}
		int nuPaginaArrecadador = ++nuPaginaInicial;
		int nuPaginaCategoria = ++nuPaginaInicial;
		int nuPaginaPerfil = ++nuPaginaInicial;
		int nuPaginaDocumento = ++nuPaginaInicial;


		retorno = actionMapping.findForward("consultarDadosDiariosParametros");

		// Monta o Status do Wizard
		StatusWizard statusWizard = new StatusWizard("consultarDadosDiariosArrecadacaoWizardAction",
						"exibirFiltrarDadosDiariosArrecadacaoAction", "cancelarConsultarDadosDiariosArrecadacaoAction",
						"exibirFiltrarDadosDiariosArrecadacaoAction", "filtrarDadosDiariosArrecadacaoAction.do");

		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaParametro, "ParametrosPrimeiraAbaA.gif",
						"ParametrosPrimeiraAbaD.gif", "exibirConsultarDadosDiariosParametrosAction", ""));

		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaGerencia, "UnidadeNegocioA.gif",
						"UnidadeNegocioD.gif",
						"exibirConsultarDadosDiariosGerenciaAction", ""));


		// Caso a empresa n�o trabalhe com concession�ria:
		// N�o exibir a aba �Concession�ria�.
		if(sessao.getAttribute("indEmpresaTrabalhaConcessionaria") != null
						&& ((String) sessao.getAttribute("indEmpresaTrabalhaConcessionaria")).equals(ConstantesSistema.SIM.toString())){

			statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaConcessionaria,
							"ConcessionariaAbaA.gif",
							"ConcessionariaAbaD.gif", "exibirConsultarDadosDiariosConcessionariaAction", ""));


		}

		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaArrecadador, "ArrecadadorIntervaloAbaA.gif",
						"ArrecadadorIntervaloAbaD.gif", "exibirConsultarDadosDiariosArrecadadorAction", ""));


		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaCategoria, "CategoriaIntervaloAbaA.gif",
						"CategoriaIntervaloAbaD.gif", "exibirConsultarDadosDiariosCategoriaAction", ""));


		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaPerfil, "PerfilIntervaloAbaA.gif",
						"PerfilIntervaloAbaD.gif",
						"exibirConsultarDadosDiariosPerfilAction", ""));


		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(nuPaginaDocumento, "DocumentoUltimaAbaA.gif",
						"DocumentoUltimaAbaD.gif",
						"exibirConsultarDadosDiariosDocumentoAction", ""));


		colecaoArrecadacaoDadosDiarios = fachada.filtrarDadosDiariosArrecadacao(Util
						.formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoInicial), Util
						.formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoFinal), localidade, idGerenciaRegional, unidadeNegocioId,
						idArrecadador, idElo, idsImovelPerfil, idsLigacaoAgua, idsLigacaoEsgoto, idsDocumentosTipos, idsCategoria,
						idsEsferaPoder, setorComercial, idConcessionaria);

		// Pesquisa os dados diarios
		/*
		 * FiltroArrecadacaoDadosDiarios filtroArrecadacaoDadosDiarios = new
		 * FiltroArrecadacaoDadosDiarios();
		 * filtroArrecadacaoDadosDiarios.setConsultaSemLimites(true);
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("localidade");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("arrecadador");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("categoria");
		 * filtroArrecadacaoDadosDiarios
		 * .adicionarCaminhoParaCarregamentoEntidade("esferaPoder");
		 */
		boolean peloMenosUmParametroInformado = false;

		// Per�odo Arrecada��o
		if(periodoArrecadacaoInicial != null && !periodoArrecadacaoInicial.equals("")){
			peloMenosUmParametroInformado = true;

			/*
			 * if (periodoArrecadacaoFinal == null
			 * || periodoArrecadacaoFinal.equals("")) {
			 * periodoArrecadacaoFinal = periodoArrecadacaoInicial;
			 * }
			 * String periodoArrecadacaoInicialFormatado = Util
			 * .formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoInicial);
			 * String periodoArrecadacaoFinalFormatado = Util
			 * .formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoFinal);
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new Intervalo(
			 * FiltroArrecadacaoDadosDiarios.ANO_MES_REFERENCIA_ARRECADACAO,
			 * periodoArrecadacaoInicialFormatado,
			 * periodoArrecadacaoFinalFormatado));
			 */
		}

		// Localidade
		if(localidade != null && !localidade.equals("") && !localidade.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			/*
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.LOCALIDADE_ID,
			 * localidade));
			 */
		}else{
			filtrarDadosDiariosArrecadacaoActionForm.setLocalidade("");
			filtrarDadosDiariosArrecadacaoActionForm.setDescricaoLocalidade("");
		}

		// Gerencia Regional
		if(idGerenciaRegional != null && !idGerenciaRegional.equals("")
						&& (!(idGerenciaRegional.equals(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))

		){
			peloMenosUmParametroInformado = true;

			/*
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.GERENCIA_REGIONAL,
			 * idGerenciaRegional));
			 */
		}else{
			filtrarDadosDiariosArrecadacaoActionForm.setIdGerenciaRegional("");
			filtrarDadosDiariosArrecadacaoActionForm.setNomeGerenciaRegional("");
		}

		// Unidade de Neg�cio
		if(unidadeNegocioId != null && !unidadeNegocioId.equals("")
						&& (!(unidadeNegocioId.equals(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))

		){
			peloMenosUmParametroInformado = true;

		}else{
			filtrarDadosDiariosArrecadacaoActionForm.setunidadeNegocioId("");
			filtrarDadosDiariosArrecadacaoActionForm.setNomeUnidadeNegocio("");
		}

		// Arrecadador
		if(idArrecadador != null && !idArrecadador.equals("") && !idArrecadador.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			/*
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.ARRECADADOR_ID,
			 * idArrecadador));
			 */
		}else{
			filtrarDadosDiariosArrecadacaoActionForm.setIdArrecadador("");
			filtrarDadosDiariosArrecadacaoActionForm.setNomeArrecadador("");
		}

		// Elo
		if(idElo != null && !idElo.equals("") && !idElo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			/*
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.LOCALIDADE_ELO,
			 * idElo));
			 */
		}else{
			filtrarDadosDiariosArrecadacaoActionForm.setIdElo("");
			filtrarDadosDiariosArrecadacaoActionForm.setNomeElo("");
		}

		// Imovel Perfil
		int i = 0;
		if(idsImovelPerfil != null){
			while(i < idsImovelPerfil.length){
				if(!idsImovelPerfil[i].equals("")){
					peloMenosUmParametroInformado = true;
					/*
					 * if (i + 1 < idsImovelPerfil.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.IMOVEL_PERFIL_ID,
					 * idsImovelPerfil[i],
					 * ConectorOr.CONECTOR_OR,
					 * (idsImovelPerfil.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.IMOVEL_PERFIL_ID,
					 * idsImovelPerfil[i]));
					 * }
					 */
				}
				i++;
			}
		}

		// Situa��o Liga��o �gua
		i = 0;
		if(idsLigacaoAgua != null){
			while(i < idsLigacaoAgua.length){
				if(!idsLigacaoAgua[i].equals("")){
					peloMenosUmParametroInformado = true;
					/*
					 * if (i + 1 < idsLigacaoAgua.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.LIGACAO_AGUA_SITUACAO_ID,
					 * idsLigacaoAgua[i],
					 * ConectorOr.CONECTOR_OR,(idsLigacaoAgua.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.LIGACAO_AGUA_SITUACAO_ID,
					 * idsLigacaoAgua[i]));
					 * }
					 */
				}
				i++;
			}
		}

		// Situa��o Liga��o Esgoto
		i = 0;
		if(idsLigacaoEsgoto != null){
			while(i < idsLigacaoEsgoto.length){
				if(!idsLigacaoEsgoto[i].equals("")){
					peloMenosUmParametroInformado = true;
					/*
					 * if (i + 1 < idsLigacaoEsgoto.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.LIGACAO_ESGOTO_SITUACAO_ID,
					 * idsLigacaoEsgoto[i],
					 * ConectorOr.CONECTOR_OR,(idsLigacaoEsgoto.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.LIGACAO_ESGOTO_SITUACAO_ID,
					 * idsLigacaoEsgoto[i]));
					 * }
					 */
				}
				i++;
			}
		}

		// Tipo do Documento
		i = 0;
		if(idsDocumentosTipos != null){
			while(i < idsDocumentosTipos.length){
				if(!idsDocumentosTipos[i].equals("")){
					/*
					 * if (i + 1 < idsDocumentosTipos.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.DOCUMENTO_TIPO_ID,
					 * idsDocumentosTipos[i],
					 * ConectorOr.CONECTOR_OR,
					 * (idsDocumentosTipos.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.DOCUMENTO_TIPO_ID,
					 * idsDocumentosTipos[i]));
					 * }
					 */
				}
				i++;
			}
		}

		// Categoria
		i = 0;
		if(idsCategoria != null){
			while(i < idsCategoria.length){
				if(!idsCategoria[i].equals("")){
					/*
					 * if (i + 1 < idsCategoria.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.CATEGORIA_ID,
					 * idsCategoria[i],
					 * ConectorOr.CONECTOR_OR,(idsCategoria.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.CATEGORIA_ID,
					 * idsCategoria[i]));
					 * }
					 */
				}
				i++;
			}
		}

		// Esfera Poder
		i = 0;
		if(idsEsferaPoder != null){
			while(i < idsEsferaPoder.length){
				if(!idsEsferaPoder[i].equals("")){
					/*
					 * if (i + 1 < idsEsferaPoder.length) {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.ESFERA_PODER_ID,
					 * idsEsferaPoder[i],
					 * ConectorOr.CONECTOR_OR,
					 * (idsEsferaPoder.length)));
					 * } else {
					 * filtroArrecadacaoDadosDiarios
					 * .adicionarParametro(new ParametroSimples(
					 * FiltroArrecadacaoDadosDiarios.ESFERA_PODER_ID,
					 * idsEsferaPoder[i]));
					 * }
					 */
				}
				i++;
			}
		}

		/*
		 * filtroArrecadacaoDadosDiarios.setCampoOrderBy(FiltroArrecadacaoDadosDiarios.ANO_MES_REFERENCIA_ARRECADACAO
		 * ,
		 * FiltroArrecadacaoDadosDiarios.DATA_PAGAMENTO,FiltroArrecadacaoDadosDiarios.CATEGORIA_ID);
		 * colecaoArrecadacaoDadosDiarios = fachada.pesquisar(filtroArrecadacaoDadosDiarios,
		 * ArrecadacaoDadosDiarios.class.getName());
		 */
		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// [FS0009] Verifica a exist�ncia de Dados diarios de arrecadacao
		if(colecaoArrecadacaoDadosDiarios == null || colecaoArrecadacaoDadosDiarios.isEmpty()){
			// Nenhum dados diarios de arrecadacao cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}else{
			sessao.setAttribute("colecaoArrecadacaoDadosDiarios", colecaoArrecadacaoDadosDiarios);
		}

		// manda o statusWizard para a sess�o
		sessao.setAttribute("statusWizard", statusWizard);

		sessao.setAttribute("nuPaginaParametro", String.valueOf(nuPaginaParametro));
		sessao.setAttribute("nuPaginaGerencia", String.valueOf(nuPaginaGerencia));
		sessao.setAttribute("nuPaginaArrecadador", String.valueOf(nuPaginaArrecadador));
		sessao.setAttribute("nuPaginaCategoria", String.valueOf(nuPaginaCategoria));
		sessao.setAttribute("nuPaginaPerfil", String.valueOf(nuPaginaPerfil));
		sessao.setAttribute("nuPaginaDocumento", String.valueOf(nuPaginaDocumento));

		sessao.removeAttribute("colecaoLigacaoAgua");
		sessao.removeAttribute("colecaoImoveisPerfil");
		sessao.removeAttribute("colecaoLigacaoEsgoto");
		sessao.removeAttribute("colecaoCategoria");
		sessao.removeAttribute("colecaoEsferaPoder");
		sessao.removeAttribute("colecaoDocumentoTipo");

		sessao.removeAttribute("dadosArrecadacaoUnidadeNegocio");
		sessao.removeAttribute("valordadosArrecadacaoUnidadeNegocio");
		sessao.removeAttribute("dadosArrecadacaoConcessionaria");
		sessao.removeAttribute("valordadosArrecadacaoConcessionaria");
		sessao.removeAttribute("dadosArrecadacaoArrecadador");
		sessao.removeAttribute("valordadosArrecadacaoArrecadador");
		sessao.removeAttribute("dadosArrecadacaoCategoria");
		sessao.removeAttribute("valordadosArrecadacaoCategoria");
		sessao.removeAttribute("dadosArrecadacaoPerfil");
		sessao.removeAttribute("valordadosArrecadacaoPerfil");
		sessao.removeAttribute("dadosArrecadacaoDocumento");
		sessao.removeAttribute("valordadosArrecadacaoDocumento");

		// Devolve o mapeamento de retorno
		return retorno;
	}
}
