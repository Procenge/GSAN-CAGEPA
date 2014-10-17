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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.cobranca.FiltroLocalidadeSetorComercialHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtrar Registro Atendimento - Exibir
 * 
 * @author Leonardo Regis - 02/08/2006
 * @author eduardo henrique
 * @date 10/03/2009 - Inclus�o de filtros de Datas de Previs�o Inicial e Final de Atendimento
 */
public class ExibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction
				extends GcomAction {

	private static final String COLECAO_SETORES_COMERCIAIS = "colecaoSetoresComerciais";

	private static final String COLECAO_LOCALIDADES = "colecaoLocalidades";

	private static final String PARAM_ID_SETOR_REMOCAO = "idSetorRemocao";

	private static final String PARAM_ID_LOCALIDADE_REMOCAO = "idLocalidadeRemocao";

	private static final String PARAM_OPERACAO = "operacao";

	private static final String OPERACAO_ADICIONAR = "Adicionar";

	private static final String OPERACAO_REMOVER = "Remover";

	private static final String COLECAO_LOCALIDADES_SETORES = "colecaoLocalidadesSetores";

	private Fachada fachada = Fachada.getInstancia();

	private static final String METODO_GERAR_RELATORIO_MODELO_1 = "exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo1";

	private static final String METODO_GERAR_RELATORIO_MODELO_2 = "exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo2";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		String pNomeMetodoGerarRelatorio = null;
		ActionForward actionForward = null;

		try{
			pNomeMetodoGerarRelatorio = ((String) ParametroCobranca.P_RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA.executar());

		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA");
		}

		if(pNomeMetodoGerarRelatorio.equals(METODO_GERAR_RELATORIO_MODELO_1)){
			actionForward = exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo1(actionMapping, actionForm, httpServletRequest,
							httpServletResponse);
		}else if(pNomeMetodoGerarRelatorio.equals(METODO_GERAR_RELATORIO_MODELO_2)){
			actionForward = exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo2(actionMapping, actionForm, httpServletRequest,
							httpServletResponse);
		}else{
			throw new ActionServletException("erro.implementacao.metodo.nao.econtrado");
		}

		return actionForward;
	}

	public ActionForward exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo1(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo1");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form = (GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm) actionForm;

		// Tratamento das opera��es da tela
		String operacao = httpServletRequest.getParameter(PARAM_OPERACAO);

		// Opera��o de adicionar Localidade e Setor Comercial
		if(!Util.isVazioOuBranco(operacao)){

			if(operacao.equals(OPERACAO_ADICIONAR)){
				adicionarLocalidadeSetor(sessao, form);

			}else if(operacao.equals(OPERACAO_REMOVER)){
				removerLocalidadeSetor(sessao, httpServletRequest);
			}

		}else{

			// Localidades
			carregarLocalidades(sessao);

			// Setores Comerciais
			carregarSetoresComerciais(sessao, form);
		}

		return retorno;

	}

	public ActionForward exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo2(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioRemuneracaoCobrancaAdministrativaModelo2");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form = (GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm) actionForm;
		
		if(httpServletRequest.getParameter("menu") != null){
			form.setTipoRelatorio("S");

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
			form.setReferenciaInicial(Util.formatarMesAnoReferencia(sistemaParametro.getAnoMesArrecadacao() - 1));
			form.setReferenciaFinal(Util.formatarMesAnoReferencia(sistemaParametro.getAnoMesArrecadacao() - 1));

			form.setSituacaoRemuneracao("2");

			form.setIndicadorConfirmaPagamento(ConstantesSistema.NAO_CONFIRMADA);

		}

		Collection<Empresa> colecaoEmpresasCobrancaAdministrativa = fachada.consultarEmpresaCobrancaAdministrativa();
		Collections.sort((List<Empresa>) colecaoEmpresasCobrancaAdministrativa);

		if(Util.isVazioOrNulo(colecaoEmpresasCobrancaAdministrativa)){
			// Fazer a verifica��o aqui
		}

		sessao.setAttribute("colecaoEmpresasCobrancaAdministrativa", colecaoEmpresasCobrancaAdministrativa);

		return retorno;

	}

	private void removerLocalidadeSetor(HttpSession sessao, HttpServletRequest httpServletRequest){

		String idLocalidadeRemocao = httpServletRequest.getParameter(PARAM_ID_LOCALIDADE_REMOCAO);
		String idSetorRemocao = httpServletRequest.getParameter(PARAM_ID_SETOR_REMOCAO);

		Localidade localidadeParaRemocao = null;
		SetorComercial setorParaRemocao = null;

		if(!Util.isVazioOuBranco(idLocalidadeRemocao)){
			localidadeParaRemocao = getLocalidade(idLocalidadeRemocao);
		}
		if(!Util.isVazioOuBranco(idSetorRemocao)){
			if(Integer.valueOf(idSetorRemocao) != ConstantesSistema.NUMERO_NAO_INFORMADO){
				setorParaRemocao = getSetorComercial(idSetorRemocao);
			}
		}
		if(localidadeParaRemocao != null){
			FiltroLocalidadeSetorComercialHelper filtroLocalidadeSetor = new FiltroLocalidadeSetorComercialHelper(localidadeParaRemocao);
			filtroLocalidadeSetor.setSetorComercial(setorParaRemocao);

			Collection<FiltroLocalidadeSetorComercialHelper> colecaoLocalidadesSetores = (Collection<FiltroLocalidadeSetorComercialHelper>) sessao
							.getAttribute(COLECAO_LOCALIDADES_SETORES);

			if(!Util.isVazioOrNulo(colecaoLocalidadesSetores)){
				colecaoLocalidadesSetores.remove(filtroLocalidadeSetor);

				if(colecaoLocalidadesSetores.isEmpty()){
					sessao.removeAttribute(COLECAO_LOCALIDADES_SETORES);
				}else{
					sessao.setAttribute(COLECAO_LOCALIDADES_SETORES, colecaoLocalidadesSetores);
				}
			}
		}

	}

	private Localidade getLocalidade(String idLocalidade){

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.parseInt(idLocalidade)));

		return (Localidade) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro, Localidade.class.getName()));
	}

	private SetorComercial getSetorComercial(String idSetorComercial){

		FiltroSetorComercial filtro = new FiltroSetorComercial();
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, Integer.parseInt(idSetorComercial)));

		return (SetorComercial) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro, SetorComercial.class.getName()));
	}

	private void adicionarLocalidadeSetor(HttpSession sessao, GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form){

		Collection<Localidade> localidades = null;

		if(!Util.isVazioOrNulo(form.getLocalidades())){
			localidades = getLocalidades(form.getLocalidades());

			if(!Util.isVazioOrNulo(localidades)){

				if(localidades.size() == 1){
					Localidade localidade = localidades.iterator().next();

					Collection<SetorComercial> setores = null;
					if(!Util.isVazioOrNulo(form.getSetoresComerciais())){
						setores = getSetoresComerciais(form.getSetoresComerciais());
					}

					adicionarFiltroLocalidadeSetor(sessao, localidade, setores);
				}else{
					for(Localidade localidade : localidades){
						adicionarFiltroLocalidadeSetor(sessao, localidade, null);
					}
				}
			}
		}
	}

	private void adicionarFiltroLocalidadeSetor(HttpSession sessao, Localidade localidade, Collection<SetorComercial> setores){

		FiltroLocalidadeSetorComercialHelper novoItemFiltro;

		Collection<FiltroLocalidadeSetorComercialHelper> colecaoLocalidadesSetores = (Collection<FiltroLocalidadeSetorComercialHelper>) sessao
						.getAttribute(COLECAO_LOCALIDADES_SETORES);

		if(localidade != null){

			if(colecaoLocalidadesSetores == null){
				colecaoLocalidadesSetores = new ArrayList<FiltroLocalidadeSetorComercialHelper>();
			}

			if(!Util.isVazioOrNulo(setores)){
				for(SetorComercial setorComercial : setores){
					novoItemFiltro = new FiltroLocalidadeSetorComercialHelper(localidade);
					novoItemFiltro.setSetorComercial(setorComercial);

					if(!colecaoLocalidadesSetores.contains(novoItemFiltro)){
						colecaoLocalidadesSetores.add(novoItemFiltro);
					}
				}
			}else{
				novoItemFiltro = new FiltroLocalidadeSetorComercialHelper(localidade);

				if(!colecaoLocalidadesSetores.contains(novoItemFiltro)){
					colecaoLocalidadesSetores.add(novoItemFiltro);
				}
			}

			sessao.setAttribute(COLECAO_LOCALIDADES_SETORES, colecaoLocalidadesSetores);
		}
	}

	/**
	 * Carrega cole��o de solicita��o tipo
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param sessao
	 */
	private void carregarLocalidades(HttpSession sessao){

		Collection colecaoLocalidades = (Collection) sessao.getAttribute(COLECAO_LOCALIDADES);

		if(Util.isVazioOrNulo(colecaoLocalidades)){
			// Filtra Localidade
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
			colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidades)){
				sessao.setAttribute(COLECAO_LOCALIDADES, colecaoLocalidades);
			}else{

				throw new ActionServletException("atencao.naocadastrado", null, "Localidade");
			}
		}
	}

	/**
	 * Carrega cole��o de setores comerciais
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param sessao
	 */
	private void carregarSetoresComerciais(HttpSession sessao, GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form){

		String[] localidades = (String[]) form.getLocalidades();

		Collection<SetorComercial> setoresComerciais = new ArrayList<SetorComercial>();

		if(!Util.isVazioOrNulo(localidades)){

			form.setSelectedLocalidadesSize(localidades.length + "");

			if((localidades.length == 1) && (Integer.valueOf(localidades[0]) != ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Filtra Setores Comerciais de uma localidade
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidades[0]));
				filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL);

				Collection colecaoSetoresComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(!Util.isVazioOrNulo(colecaoSetoresComerciais)){
					setoresComerciais.addAll(colecaoSetoresComerciais);
				}
			}
		}else{
			form.setSelectedLocalidadesSize("0");
		}

		form.setSetoresComerciais(null);
		sessao.setAttribute(COLECAO_SETORES_COMERCIAIS, setoresComerciais);
	}

	/**
	 * Retorna uma cole��o de tipos de solicita��o a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param localidadesId
	 * @return
	 */
	private Collection<Localidade> getLocalidades(String[] localidadesId){

		// Localidade
		Collection<Localidade> localidades = null;

		for(int i = 0; i < localidadesId.length; i++){

			if(!Util.isVazioOuBranco(localidadesId[i]) && !localidadesId[i].equals("0") && !localidadesId[i].equals("-1")){

				try{
					Localidade localidade = getLocalidade(localidadesId[i]);

					if(localidade == null){
						throw new ActionServletException("pesquisa.localidade.inexistente");
					}

					if(localidades == null){
						localidades = new ArrayList();
					}

					localidades.add(localidade);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return localidades;
	}

	/**
	 * Retorna uma cole��o de tipos de solicita��o a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param tiposSolicitacaoId
	 * @return
	 */
	private Collection<SetorComercial> getSetoresComerciais(String[] setoresComerciaisId){

		// Tipo Solicita��o
		Collection<SetorComercial> setoresComerciais = null;

		for(int i = 0; i < setoresComerciaisId.length; i++){

			if(!Util.isVazioOuBranco(setoresComerciaisId[i]) && !setoresComerciaisId[i].equals("0") && !setoresComerciaisId[i].equals("-1")){

				try{

					FiltroSetorComercial filtro = new FiltroSetorComercial();
					filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, Integer.parseInt(setoresComerciaisId[i])));

					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro,
									SetorComercial.class.getName()));

					if(setorComercial == null){
						throw new ActionServletException("atencao.setor_comercial.inexistente");
					}

					if(setoresComerciais == null){
						setoresComerciais = new ArrayList();
					}

					setoresComerciais.add(setorComercial);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return setoresComerciais;
	}

}