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

package gcom.gui.relatorio.cobranca.spcserasa;

import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.*;
import gcom.cobranca.*;
import gcom.cobranca.bean.DadosConsultaNegativacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.spcserasa.InformarDadosConsultaNegativacaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.spcserasa.RelatorioNegativacoesExcluidas;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.FiltroNegativador;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição do relatório de Acompanhamanto de Clientes Negativados
 * 
 * @author Yara Taciane
 * @created 18 de março de 2008
 */
public class GerarRelatorioNegativacoesExcluidasAction
				extends ExibidorProcessamentoTarefaRelatorio {

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
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		InformarDadosConsultaNegativacaoActionForm form = (InformarDadosConsultaNegativacaoActionForm) actionForm;
		DadosConsultaNegativacaoHelper parametros = new DadosConsultaNegativacaoHelper();

		String idNegativador = null;
		if(!Util.isVazioOuBranco(form.getIdNegativador())){
			idNegativador = (String) form.getIdNegativador();
			// idNegativador = (String) sessao.getAttribute("idNegativador");

		}
		sessao.setAttribute("idNegativador", idNegativador);

		Date periodoEnvioNegativacaoInicio = null;
		if(!Util.isVazioOuBranco(form.getPeriodoEnvioNegativacaoInicio())){
			Date periodoInicio = Util.converterStringParaDate(form.getPeriodoEnvioNegativacaoInicio(),
							"atencao.data_inicial_periodo_negativacao.invalida");
			periodoEnvioNegativacaoInicio = Util.getSQLDate(periodoInicio);
		}
		// if (periodoEnvioNegativacaoInicio == null){
		// periodoEnvioNegativacaoInicio =
		// (Date)sessao.getAttribute("periodoEnvioNegativacaoInicio");
		// }
		sessao.setAttribute("periodoEnvioNegativacaoInicio", periodoEnvioNegativacaoInicio);

		Date periodoEnvioNegativacaoFim = null;

		if(!Util.isVazioOuBranco(form.getPeriodoEnvioNegativacaoFim())){
			Date periodoFim = Util.converterStringParaDate(form.getPeriodoEnvioNegativacaoFim(),
							"atencao.data_final_periodo_negativacao.invalida");
			periodoEnvioNegativacaoFim = Util.getSQLDate(periodoFim);
		}

		// if (periodoEnvioNegativacaoFim == null){
		// periodoEnvioNegativacaoFim = (Date)sessao.getAttribute("periodoEnvioNegativacaoFim");
		// }
		sessao.setAttribute("periodoEnvioNegativacaoFim", periodoEnvioNegativacaoFim);

		Date periodoExclusaoNegativacaoInicio = null;

		if(!Util.isVazioOuBranco(form.getPeriodoExclusaoNegativacaoInicio())){
			Date periodoInicio = Util.converterStringParaDate(form.getPeriodoExclusaoNegativacaoInicio(),
							"atencao.data_inicial_periodo_exclusao.invalida");
			periodoExclusaoNegativacaoInicio = Util.getSQLDate(periodoInicio);
		}

		// if (periodoExclusaoNegativacaoInicio == null){
		// periodoExclusaoNegativacaoInicio =
		// (Date)sessao.getAttribute("periodoExclusaoNegativacaoInicio");
		// }
		sessao.setAttribute("periodoExclusaoNegativacaoInicio", periodoExclusaoNegativacaoInicio);

		Date periodoExclusaoNegativacaoFim = null;
		if(!Util.isVazioOuBranco(form.getPeriodoExclusaoNegativacaoFim())){
			Date periodoFim = Util.converterStringParaDate(form.getPeriodoExclusaoNegativacaoFim(),
							"atencao.data_final_periodo_exclusao.invalida");
			periodoExclusaoNegativacaoFim = Util.getSQLDate(periodoFim);
		}
		// if (periodoExclusaoNegativacaoFim == null){
		// periodoExclusaoNegativacaoFim =
		// (Date)sessao.getAttribute("periodoExclusaoNegativacaoFim");
		// }
		sessao.setAttribute("periodoExclusaoNegativacaoFim", periodoExclusaoNegativacaoFim);

		String idNegativadorExclusaoMotivo = null;
		if(!Util.isVazioOuBranco(form.getIdNegativadorExclusaoMotivo())){
			idNegativadorExclusaoMotivo = (String) form.getIdNegativadorExclusaoMotivo();
			// idNegativadorExclusaoMotivo =
			// (String)sessao.getAttribute("idNegativadorExclusaoMotivo");
		}
		sessao.setAttribute("idNegativadorExclusaoMotivo", idNegativadorExclusaoMotivo);

		String tituloComando = null;
		if(form.getTituloComando() != null && !form.getTituloComando().equals("")){
			tituloComando = form.getTituloComando();
		}
		// if (tituloComando == null || tituloComando.equalsIgnoreCase("") ){
		// tituloComando = (String)sessao.getAttribute("tituloComando");
		// }
		sessao.setAttribute("tituloComando", tituloComando);

		Integer idEloPolo = null;
		if(!Util.isVazioOuBranco(form.getIdEloPolo()) && !form.getIdEloPolo().equals("-1")){
			idEloPolo = new Integer(form.getIdEloPolo());
		}
		// if (idEloPolo == null){
		// idEloPolo = (Integer)sessao.getAttribute("idEloPolo");
		// }
		sessao.setAttribute("idEloPolo", idEloPolo);

		Integer idLocalidade = null;

		if(!Util.isVazioOuBranco(form.getIdLocalidade()) && !form.getIdLocalidade().equals("-1")){
			idLocalidade = new Integer(form.getIdLocalidade());
		}

		// if (idLocalidade == null){
		// idLocalidade = (Integer)sessao.getAttribute("idLocalidade");
		// }

		sessao.setAttribute("idLocalidade", idLocalidade);

		Integer idSetorComercial = null;

		if(!Util.isVazioOuBranco(form.getIdSetorComercial()) && !form.getIdSetorComercial().equals("-1")){
			idSetorComercial = new Integer(form.getIdSetorComercial());
		}

		// if (idSetorComercial == null){
		// idSetorComercial = (Integer)sessao.getAttribute("idSetorComercial");
		// }

		sessao.setAttribute("idSetorComercial", idSetorComercial);

		Integer idQuadra = null;

		if(!Util.isVazioOuBranco(form.getIdQuadra()) && !form.getIdQuadra().equals("-1")){
			idQuadra = new Integer(form.getIdQuadra());
		}

		// if (idQuadra == null){
		// idQuadra = (Integer)sessao.getAttribute("idQuadra");
		// }

		sessao.setAttribute("idQuadra", idQuadra);

		// --------------------------------------------------------------------------------------------------------
		// Grupo Cobrança
		// --------------------------------------------------------------------------------------------------------
		String[] arrayCobrancaGrupo = null;

		if(((String[]) form.getArrayCobrancaGrupo()) == null
						|| (((String[]) form.getArrayCobrancaGrupo()).length == 1 && ((String[]) form.getArrayCobrancaGrupo())[0]
										.equalsIgnoreCase(""))){
			// arrayCobrancaGrupo = (String[])sessao.getAttribute("arrayCobrancaGrupo");
			arrayCobrancaGrupo = null;
		}else{

			String[] arrayCobrancaGrupoForm = form.getArrayCobrancaGrupo();

			Collection<String> arrayCobrancaGrupoAux = new ArrayList<String>();

			if(arrayCobrancaGrupoForm != null && !(arrayCobrancaGrupoForm.length <= 0)){
				for(String idCG : arrayCobrancaGrupoForm){
					if(!(idCG.trim().equalsIgnoreCase("")) || !(idCG.trim().length() < 1)){
						arrayCobrancaGrupoAux.add(idCG);
					}
				}

				if(arrayCobrancaGrupoAux != null && !arrayCobrancaGrupoAux.isEmpty()){
					arrayCobrancaGrupo = (String[]) arrayCobrancaGrupoAux.toArray(new String[arrayCobrancaGrupoAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayCobrancaGrupo", arrayCobrancaGrupo);

		CobrancaGrupo cobrancaGrupoColecao = new CobrancaGrupo();
		cobrancaGrupoColecao.setId(-1);

		Collection colecaoCobrancaGrupo = new ArrayList();
		int i = 0;

		if(arrayCobrancaGrupo != null && arrayCobrancaGrupo.length > 0){
			cobrancaGrupoColecao.setDescricao("OPÇÕES SELECIONADAS");
			colecaoCobrancaGrupo.add(cobrancaGrupoColecao);
			FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();

			for(i = 0; i < arrayCobrancaGrupo.length; i++){
				if(!arrayCobrancaGrupo[i].equals("") && !arrayCobrancaGrupo[i].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(i + 1 < arrayCobrancaGrupo.length){
						filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, arrayCobrancaGrupo[i],
										ConectorOr.CONECTOR_OR, arrayCobrancaGrupo.length));

					}else{
						filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, arrayCobrancaGrupo[i]));
					}
				}
			}

			filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);

			Collection colecaoCobrancaGrupoPesquisa = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

			if(colecaoCobrancaGrupoPesquisa != null && !colecaoCobrancaGrupoPesquisa.isEmpty()){
				colecaoCobrancaGrupo.addAll(colecaoCobrancaGrupoPesquisa);
			}
		}else{
			cobrancaGrupoColecao.setDescricao("TODOS");
			colecaoCobrancaGrupo.add(cobrancaGrupoColecao);
		}

		// --------------------------------------------------------------------------------------------------------
		// Gerência Regional
		// --------------------------------------------------------------------------------------------------------
		String[] arrayGerenciaRegional = null;
		if(((String[]) form.getArrayGerenciaRegional()) == null
						|| (((String[]) form.getArrayGerenciaRegional()).length == 1 && ((String[]) form.getArrayGerenciaRegional())[0]
										.equalsIgnoreCase(""))){
			// arrayGerenciaRegional = (String[])sessao.getAttribute("arrayGerenciaRegional");
			arrayGerenciaRegional = null;
		}else{
			String[] arrayGerenciaRegionalForm = form.getArrayGerenciaRegional();

			Collection<String> arrayGerenciaRegionalAux = new ArrayList<String>();

			if(arrayGerenciaRegionalForm != null && !(arrayGerenciaRegionalForm.length <= 0)){
				for(String idGR : arrayGerenciaRegionalForm){
					if(!(idGR.trim().equalsIgnoreCase("")) || !(idGR.trim().length() < 1)){
						arrayGerenciaRegionalAux.add(idGR);
					}
				}

				if(arrayGerenciaRegionalAux != null && !arrayGerenciaRegionalAux.isEmpty()){
					arrayGerenciaRegional = (String[]) arrayGerenciaRegionalAux.toArray(new String[arrayGerenciaRegionalAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayGerenciaRegional", arrayGerenciaRegional);

		GerenciaRegional gerenciaRegionalColecao = new GerenciaRegional();
		gerenciaRegionalColecao.setId(-1);

		Collection colecaoGerenciaRegional = new ArrayList();
		int j = 0;

		if(arrayGerenciaRegional != null && arrayGerenciaRegional.length > 0){
			gerenciaRegionalColecao.setNome("OPÇÕES SELECIONADAS");
			colecaoGerenciaRegional.add(gerenciaRegionalColecao);
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			for(j = 0; j < arrayGerenciaRegional.length; j++){
				if(!arrayGerenciaRegional[j].equals("") && !arrayGerenciaRegional[j].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(j + 1 < arrayGerenciaRegional.length){
						filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, arrayGerenciaRegional[j],
										ConectorOr.CONECTOR_OR, arrayGerenciaRegional.length));

					}else{
						filtroGerenciaRegional
										.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, arrayGerenciaRegional[j]));
					}
				}
			}

			filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

			Collection colecaoGerenciaRegionalPesquisa = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoGerenciaRegionalPesquisa != null && !colecaoGerenciaRegionalPesquisa.isEmpty()){

				colecaoGerenciaRegional.addAll(colecaoGerenciaRegionalPesquisa);
			}
		}else{
			gerenciaRegionalColecao.setNome("TODOS");
			colecaoGerenciaRegional.add(gerenciaRegionalColecao);
		}
		// --------------------------------------------------------------------------------------------------------
		// Unidade de Negócio
		// --------------------------------------------------------------------------------------------------------
		String[] arrayUnidadeNegocio = null;
		if(((String[]) form.getArrayUnidadeNegocio()) == null
						|| (((String[]) form.getArrayUnidadeNegocio()).length == 1 && ((String[]) form.getArrayUnidadeNegocio())[0]
										.equalsIgnoreCase(""))){
			// arrayUnidadeNegocio = (String[])sessao.getAttribute("arrayUnidadeNegocio");
			arrayUnidadeNegocio = null;
		}else{
			String[] arrayUnidadeNegocioForm = form.getArrayUnidadeNegocio();

			Collection<String> arrayUnidadeNegocioAux = new ArrayList<String>();

			if(arrayUnidadeNegocioForm != null && !(arrayUnidadeNegocioForm.length <= 0)){
				for(String idUN : arrayUnidadeNegocioForm){
					if(!(idUN.trim().equalsIgnoreCase("")) || !(idUN.trim().length() < 1)){
						arrayUnidadeNegocioAux.add(idUN);
					}
				}

				if(arrayUnidadeNegocioAux != null && !arrayUnidadeNegocioAux.isEmpty()){
					arrayUnidadeNegocio = (String[]) arrayUnidadeNegocioAux.toArray(new String[arrayUnidadeNegocioAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayUnidadeNegocio", arrayUnidadeNegocio);

		UnidadeNegocio unidadeNegocioColecao = new UnidadeNegocio();
		unidadeNegocioColecao.setId(-1);

		Collection colecaoUnidadeNegocio = new ArrayList();
		int l = 0;

		if(arrayUnidadeNegocio != null && arrayUnidadeNegocio.length > 0){
			unidadeNegocioColecao.setNome("OPÇÕES SELECIONADAS");
			colecaoUnidadeNegocio.add(unidadeNegocioColecao);
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			for(l = 0; l < arrayUnidadeNegocio.length; l++){
				if(!arrayUnidadeNegocio[l].equals("") && !arrayUnidadeNegocio[l].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(l + 1 < arrayUnidadeNegocio.length){
						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayUnidadeNegocio[l],
										ConectorOr.CONECTOR_OR, arrayUnidadeNegocio.length));

					}else{
						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayUnidadeNegocio[l]));
					}
				}
			}

			filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

			Collection colecaoUnidadeNegocioPesquisa = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocioPesquisa != null && !colecaoUnidadeNegocioPesquisa.isEmpty()){
				colecaoUnidadeNegocio.addAll(colecaoUnidadeNegocioPesquisa);
			}
		}else{
			unidadeNegocioColecao.setNome("TODOS");
			colecaoUnidadeNegocio.add(unidadeNegocioColecao);
		}

		// --------------------------------------------------------------------------------------------------------
		// Perfil Imóvel
		// --------------------------------------------------------------------------------------------------------
		String[] arrayImovelPerfil = null;
		if(((String[]) form.getArrayImovelPerfil()) == null
						|| (((String[]) form.getArrayImovelPerfil()).length == 1 && ((String[]) form.getArrayImovelPerfil())[0]
										.equalsIgnoreCase(""))){
			// arrayImovelPerfil = (String[])sessao.getAttribute("arrayImovelPerfil");
			arrayImovelPerfil = null;
		}else{
			String[] arrayImovelPerfilForm = form.getArrayImovelPerfil();

			Collection<String> arrayImovelPerfilAux = new ArrayList<String>();

			if(arrayImovelPerfilForm != null && !(arrayImovelPerfilForm.length <= 0)){
				for(String idIP : arrayImovelPerfilForm){
					if(!(idIP.trim().equalsIgnoreCase("")) || !(idIP.trim().length() < 1)){
						arrayImovelPerfilAux.add(idIP);
					}
				}

				if(arrayImovelPerfilAux != null && !arrayImovelPerfilAux.isEmpty()){
					arrayImovelPerfil = (String[]) arrayImovelPerfilAux.toArray(new String[arrayImovelPerfilAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayImovelPerfil", arrayImovelPerfil);

		ImovelPerfil imovelPerfilColecao = new ImovelPerfil();
		imovelPerfilColecao.setId(-1);

		Collection colecaoImovelPerfil = new ArrayList();
		int m = 0;

		if(arrayImovelPerfil != null && arrayImovelPerfil.length > 0){
			imovelPerfilColecao.setDescricao("OPÇÕES SELECIONADAS");
			colecaoImovelPerfil.add(imovelPerfilColecao);
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

			for(m = 0; m < arrayImovelPerfil.length; m++){
				if(!arrayImovelPerfil[m].equals("") && !arrayImovelPerfil[m].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(m + 1 < arrayImovelPerfil.length){
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, arrayImovelPerfil[m],
										ConectorOr.CONECTOR_OR, arrayImovelPerfil.length));

					}else{
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, arrayImovelPerfil[m]));
					}
				}
			}

			filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);

			Collection colecaoImovelPerfilPesquisa = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			if(colecaoImovelPerfilPesquisa != null && !colecaoImovelPerfilPesquisa.isEmpty()){
				colecaoImovelPerfil.addAll(colecaoImovelPerfilPesquisa);
			}
		}else{
			imovelPerfilColecao.setDescricao("TODOS");
			colecaoImovelPerfil.add(imovelPerfilColecao);
		}

		// --------------------------------------------------------------------------------------------------------
		// Categoria
		// --------------------------------------------------------------------------------------------------------
		String[] arrayCategoria = null;
		if(((String[]) form.getArrayCategoria()) == null
						|| (((String[]) form.getArrayCategoria()).length == 1 && ((String[]) form.getArrayCategoria())[0]
										.equalsIgnoreCase(""))){
			// arrayCategoria = (String[])sessao.getAttribute("arrayCategoria");
			arrayCategoria = null;
		}else{
			String[] arrayCategoriaForm = form.getArrayCategoria();

			Collection<String> arrayCategoriaAux = new ArrayList<String>();

			if(arrayCategoriaForm != null && !(arrayCategoriaForm.length <= 0)){
				for(String idC : arrayCategoriaForm){
					if(!(idC.trim().equalsIgnoreCase("")) || !(idC.trim().length() < 1)){
						arrayCategoriaAux.add(idC);
					}
				}

				if(arrayCategoriaAux != null && !arrayCategoriaAux.isEmpty()){
					arrayCategoria = (String[]) arrayCategoriaAux.toArray(new String[arrayCategoriaAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayCategoria", arrayCategoria);

		Categoria categoriaColecao = new Categoria();
		categoriaColecao.setId(-1);

		Collection colecaoCategoria = new ArrayList();
		int n = 0;

		if(arrayCategoria != null && arrayCategoria.length > 0){
			categoriaColecao.setDescricao("OPÇÕES SELECIONADAS");
			colecaoCategoria.add(categoriaColecao);
			FiltroCategoria filtroCategoria = new FiltroCategoria();

			for(n = 0; n < arrayCategoria.length; n++){
				if(!arrayCategoria[n].equals("") && !arrayCategoria[n].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(n + 1 < arrayCategoria.length){
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, arrayCategoria[n],
										ConectorOr.CONECTOR_OR, arrayCategoria.length));

					}else{
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, arrayCategoria[n]));
					}
				}
			}

			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			Collection colecaoCategoriaPesquisa = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

			if(colecaoCategoriaPesquisa != null && !colecaoCategoriaPesquisa.isEmpty()){
				colecaoCategoria.addAll(colecaoCategoriaPesquisa);
			}
		}else{
			categoriaColecao.setDescricao("TODOS");
			colecaoCategoria.add(categoriaColecao);
		}

		// --------------------------------------------------------------------------------------------------------
		// TipoCliente
		// --------------------------------------------------------------------------------------------------------
		String[] arrayTipoCliente = null;
		if(((String[]) form.getArrayTipoCliente()) == null
						|| (((String[]) form.getArrayTipoCliente()).length == 1 && ((String[]) form.getArrayTipoCliente())[0]
										.equalsIgnoreCase(""))){
			// arrayTipoCliente = (String[])sessao.getAttribute("arrayTipoCliente");
			arrayTipoCliente = null;
		}else{
			String[] arrayTipoClienteForm = form.getArrayTipoCliente();

			Collection<String> arrayTipoClienteAux = new ArrayList<String>();

			if(arrayTipoClienteForm != null && !(arrayTipoClienteForm.length <= 0)){
				for(String idTC : arrayTipoClienteForm){
					if(!(idTC.trim().equalsIgnoreCase("")) || !(idTC.trim().length() < 1)){
						arrayTipoClienteAux.add(idTC);
					}
				}

				if(arrayTipoClienteAux != null && !arrayTipoClienteAux.isEmpty()){
					arrayTipoCliente = (String[]) arrayTipoClienteAux.toArray(new String[arrayTipoClienteAux.size()]);
				}
			}
		}
		sessao.setAttribute("arrayTipoCliente", arrayTipoCliente);

		ClienteTipo tipoClienteColecao = new ClienteTipo();
		tipoClienteColecao.setId(-1);

		Collection colecaoTipoCliente = new ArrayList();
		int o = 0;

		if(arrayTipoCliente != null && arrayTipoCliente.length > 0){
			tipoClienteColecao.setDescricao("OPÇÕES SELECIONADAS");
			colecaoTipoCliente.add(tipoClienteColecao);
			FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();

			for(o = 0; o < arrayTipoCliente.length; o++){
				if(!arrayTipoCliente[o].equals("") && !arrayTipoCliente[o].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(o + 1 < arrayTipoCliente.length){
						filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, arrayTipoCliente[o],
										ConectorOr.CONECTOR_OR, arrayTipoCliente.length));

					}else{
						filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, arrayTipoCliente[o]));
					}
				}
			}

			filtroClienteTipo.setCampoOrderBy(FiltroClienteTipo.DESCRICAO);

			Collection colecaoTipoClientePesquisa = fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());

			if(colecaoTipoClientePesquisa != null && !colecaoTipoClientePesquisa.isEmpty()){
				colecaoTipoCliente.addAll(colecaoTipoClientePesquisa);
			}
		}else{
			tipoClienteColecao.setDescricao("TODOS");
			colecaoTipoCliente.add(tipoClienteColecao);
		}

		// --------------------------------------------------------------------------------------------------------
		// Esfera Poder
		// --------------------------------------------------------------------------------------------------------
		String[] arrayEsferaPoder = null;
		if(((String[]) form.getArrayEsferaPoder()) == null
						|| (((String[]) form.getArrayEsferaPoder()).length == 1 && ((String[]) form.getArrayEsferaPoder())[0]
										.equalsIgnoreCase(""))){
			// arrayEsferaPoder = (String[])sessao.getAttribute("arrayEsferaPoder");
			arrayEsferaPoder = null;
		}else{
			String[] arrayEsferaPoderForm = form.getArrayEsferaPoder();

			Collection<String> arrayEsferaPoderAux = new ArrayList<String>();

			if(arrayEsferaPoderForm != null && !(arrayEsferaPoderForm.length <= 0)){
				for(String idEP : arrayEsferaPoderForm){
					if(!(idEP.trim().equalsIgnoreCase("")) || !(idEP.trim().length() < 1)){
						arrayEsferaPoderAux.add(idEP);
					}
				}

				if(arrayEsferaPoderAux != null && !arrayEsferaPoderAux.isEmpty()){
					arrayEsferaPoder = (String[]) arrayEsferaPoderAux.toArray(new String[arrayEsferaPoderAux.size()]);
				}
			}
		}

		sessao.setAttribute("arrayEsferaPoder", arrayEsferaPoder);

		EsferaPoder esferaPoderColecao = new EsferaPoder();
		esferaPoderColecao.setId(-1);

		Collection colecaoEsferaPoder = new ArrayList();
		int p = 0;

		if(arrayEsferaPoder != null && arrayEsferaPoder.length > 0){
			esferaPoderColecao.setDescricao("OPÇÕES SELECIONADAS");
			colecaoEsferaPoder.add(esferaPoderColecao);
			FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();

			for(p = 0; p < arrayEsferaPoder.length; p++){
				if(!arrayEsferaPoder[p].equals("") && !arrayEsferaPoder[p].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					if(p + 1 < arrayEsferaPoder.length){
						filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.ID, arrayEsferaPoder[p],
										ConectorOr.CONECTOR_OR, arrayEsferaPoder.length));
					}else{
						filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.ID, arrayEsferaPoder[p]));
					}
				}
			}

			filtroEsferaPoder.setCampoOrderBy(FiltroEsferaPoder.DESCRICAO);
			Collection colecaoEsferaPoderPesquisa = fachada.pesquisar(filtroEsferaPoder, EsferaPoder.class.getName());

			if(colecaoEsferaPoderPesquisa != null && !colecaoEsferaPoderPesquisa.isEmpty()){
				colecaoEsferaPoder.addAll(colecaoEsferaPoderPesquisa);
			}
		}else{
			esferaPoderColecao.setDescricao("TODOS");
			colecaoEsferaPoder.add(esferaPoderColecao);
		}

		// cria uma instância da classe do relatório
		RelatorioNegativacoesExcluidas relatorio = new RelatorioNegativacoesExcluidas(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		// seta os parametros que serão mostrados no relatório
		parametros = validarGeracaoRelatorio(idNegativador, periodoEnvioNegativacaoInicio, periodoEnvioNegativacaoFim,
						periodoExclusaoNegativacaoInicio, periodoExclusaoNegativacaoFim, idNegativadorExclusaoMotivo, tituloComando,
						idEloPolo, idLocalidade, idSetorComercial, idQuadra, colecaoCobrancaGrupo, colecaoGerenciaRegional,
						colecaoUnidadeNegocio, colecaoImovelPerfil, colecaoCategoria, colecaoTipoCliente, colecaoEsferaPoder);
		// Fim da parte que vai mandar os parametros para o relatório

		relatorio.addParametro("parametros", parametros);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		try{
			retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			reportarErros(httpServletRequest, "erro.relatorio.vazio");
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

	private DadosConsultaNegativacaoHelper validarGeracaoRelatorio(String idNegativador, Date periodoEnvioNegativacaoInicio,
					Date periodoEnvioNegativacaoFim, Date periodoExclusaoNegativacaoInicio, Date periodoExclusaoNegativacaoFim,
					String idNegativadorExclusaoMotivo, String tituloComando, Integer idEloPolo, Integer idLocalidade,
					Integer idSetorComercial, Integer idQuadra, Collection collCobrancaGrupo, Collection collGerenciaRegional,
					Collection collUnidadeNegocio, Collection collImovelPerfil, Collection collCategoria, Collection collTipoCliente,
					Collection collEsferaPoder){

		Fachada fachada = Fachada.getInstancia();

		// Inicio da parte que vai mandar os parametros para o relatório
		DadosConsultaNegativacaoHelper helper = new DadosConsultaNegativacaoHelper();

		if(idNegativador != null && !idNegativador.equals("")){
			FiltroNegativador filtroNegativador = new FiltroNegativador();
			filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.ID, idNegativador));
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			Collection collNegativador = fachada.pesquisar(filtroNegativador, Negativador.class.getName());

			if(Util.isVazioOrNulo(collNegativador)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Negativador");
			}
		}

		if(idNegativadorExclusaoMotivo != null && !idNegativadorExclusaoMotivo.equals("") && !idNegativadorExclusaoMotivo.equals("-1")){
			FiltroNegativadorExclusaoMotivo filtroNegativadorExclusaoMotivo = new FiltroNegativadorExclusaoMotivo();
			filtroNegativadorExclusaoMotivo.adicionarParametro(new ParametroSimples(FiltroNegativadorExclusaoMotivo.ID,
							idNegativadorExclusaoMotivo));
			Collection collNegativadorExclusaoMotivo = fachada.pesquisar(filtroNegativadorExclusaoMotivo,
							NegativadorExclusaoMotivo.class.getName());

			if(Util.isVazioOrNulo(collNegativadorExclusaoMotivo)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Negativador Exclusão Motivo");
			}
		}

		if(idEloPolo != null && !idEloPolo.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, idEloPolo));
			Collection collLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(Util.isVazioOrNulo(collLocalidade)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "EloPolo");
			}
		}

		if(idLocalidade != null && !idLocalidade.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
			Collection collLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(Util.isVazioOrNulo(collLocalidade)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}
		}

		if(idSetorComercial != null && !idSetorComercial.equals("")){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercial));
			Collection collSetorComercial = Fachada.getInstancia().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(Util.isVazioOrNulo(collSetorComercial)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}
		}

		if(idQuadra != null && !idQuadra.equals("")){
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, idLocalidade));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, idSetorComercial));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, idQuadra));
			Collection collQuadra = Fachada.getInstancia().pesquisar(filtroQuadra, Quadra.class.getName());

			if(Util.isVazioOrNulo(collQuadra)){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Quadra");
			}
		}

		helper.setIdNegativador(new Integer(idNegativador));
		helper.setPeriodoEnvioNegativacaoInicio(periodoEnvioNegativacaoInicio);
		helper.setPeriodoEnvioNegativacaoFim(periodoEnvioNegativacaoFim);
		helper.setPeriodoExclusaoNegativacaoInicio(periodoExclusaoNegativacaoInicio);
		helper.setPeriodoExclusaoNegativacaoFim(periodoExclusaoNegativacaoFim);
		helper.setIdNegativadorExclusaoMotivo(new Integer(idNegativadorExclusaoMotivo));
		helper.setTituloComando(tituloComando);
		helper.setIdEloPolo(idEloPolo);
		helper.setIdLocalidade(idLocalidade);
		helper.setIdSetorComercial(idSetorComercial);
		helper.setIdQuadra(idQuadra);
		helper.setColecaoCobrancaGrupo(collCobrancaGrupo);
		helper.setColecaoGerenciaRegional(collGerenciaRegional);
		helper.setColecaoUnidadeNegocio(collUnidadeNegocio);
		helper.setColecaoImovelPerfil(collImovelPerfil);
		helper.setColecaoCategoria(collCategoria);
		helper.setColecaoClienteTipo(collTipoCliente);
		helper.setColecaoEsferaPoder(collEsferaPoder);

		int qtdeResultados = fachada.pesquisarRelatorioNegativacoesExcluidasCount(helper);

		if(qtdeResultados == 0){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}else{
			return helper;
		}
	}
}
