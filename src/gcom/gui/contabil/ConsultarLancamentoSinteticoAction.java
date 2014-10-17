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

package gcom.gui.contabil;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.localidade.*;
import gcom.contabil.EventoComercial;
import gcom.contabil.FiltroEventoComercial;
import gcom.contabil.UnidadeContabilAgrupamento;
import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroImpostoTipo;
import gcom.faturamento.ImpostoTipo;
import gcom.financeiro.ContaContabil;
import gcom.financeiro.FiltroContaContabil;
import gcom.financeiro.lancamento.FiltroLancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroModulo;
import gcom.seguranca.acesso.Modulo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.ParametroContabil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição da tela de consultar conta
 * 
 * @author Genival Barbosa
 * @created 07 de Julho de 2011
 */
public class ConsultarLancamentoSinteticoAction
				extends GcomAction
				implements ExecutorParametro {

	private static final String OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL = "3";

	private static final String OBJETO_CONSULTA_CONTA_AUXILIAR_FINAL = "4";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ConsultarLancamentoSinteticoActionForm form = (ConsultarLancamentoSinteticoActionForm) actionForm;

		ActionForward retorno = actionMapping.findForward("consultarLancamentoSintetico");

		Fachada fachada = Fachada.getInstancia();

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade Inicial / Final
		if(Util.isNaoNuloBrancoZero(objetoConsulta)
						&& (objetoConsulta.equals(ConstantesSistema.SIM.toString()) || objetoConsulta.equals(ConstantesSistema.NAO
										.toString()))){
			this.pesquisarLocalidade(form, objetoConsulta, httpServletRequest);
		}

		// Pesquisar Conta Auxiliar Inicial / Final
		if(Util.isNaoNuloBrancoZero(objetoConsulta)
						&& (objetoConsulta.equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL) || objetoConsulta
										.equals(OBJETO_CONSULTA_CONTA_AUXILIAR_FINAL))){
			this.pesquisarContaAuxiliar(form, objetoConsulta, httpServletRequest);
		}

		try{
			carregarCombos(httpServletRequest, form, fachada);
			// carregarComboUnidadeContabilAgrupamento(httpServletRequest);

			Map<String, Object> filtro;

			filtro = montarFiltroPesquisa(form);

			Collection<LancamentoContabilSinteticoConsultaHelper> colecaoLcs = fachada.consultarLancamentoContabilSintetico(filtro);

			httpServletRequest.setAttribute("lancamentos", colecaoLcs);

		}catch(ParseException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			// }catch(ControladorException e){
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return retorno;

	}

	private Map<String, Object> montarFiltroPesquisa(ConsultarLancamentoSinteticoActionForm form) throws ParseException{

		Map<String, Object> mapaParametros = new HashMap<String, Object>();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date dataCorrente = new Date();
		Date dataCotabilInicial = Util.converteStringParaDate(form.getDtInicio(), false);
		Date dataCotabilFinal = Util.converteStringParaDate(form.getDtFim(), false);

		if(dataCotabilInicial.after(dataCorrente)){
			throw new ActionServletException("atencao.data_contabil_inicial_posterior_data_corrente", null, Util.formatarData(dataCorrente));
		}

		if(dataCotabilFinal.after(dataCorrente)){
			throw new ActionServletException("atencao.data_contabil_final_posterior_data_corrente", null, Util.formatarData(dataCorrente));
		}

		if(!Util.isVazioOuBranco(form.getDtInicio())){
			mapaParametros.put("dataInicio", new Date(format.parse(form.getDtInicio()).getTime()));
		}
		if(!Util.isVazioOuBranco(form.getDtFim())){
			mapaParametros.put("dataFim", new Date(format.parse(form.getDtFim()).getTime()));
		}

		// if(!Util.isVazioOuBranco(form.getIdUnidadeAgrupamento())){
		// mapaParametros.put("idUnidadeContabilAgrupamento", new
		// Integer(form.getIdUnidadeAgrupamento()));
		// }
		if(Util.isNaoNuloBrancoZero(form.getLocalidadeInicial())){
			mapaParametros.put("localidadeInicial", new Integer(form.getLocalidadeInicial()));
		}

		if(Util.isNaoNuloBrancoZero(form.getLocalidadeFinal())){
			mapaParametros.put("localidadeFinal", new Integer(form.getLocalidadeFinal()));
		}

		if(Util.isNaoNuloBrancoZero(form.getContaContabilInicial())){
			mapaParametros.put("contaContabilInicial", new Integer(form.getContaContabilInicial()));
		}

		if(Util.isNaoNuloBrancoZero(form.getContaContabilFinal())){
			mapaParametros.put("contaContabilFinal", new Integer(form.getContaContabilFinal()));
		}

		if(Util.isNaoNuloBrancoZero(form.getContaAuxiliarInicial())){
			mapaParametros.put("contaAuxiliarInicial", new Short(form.getContaAuxiliarInicial()));
		}

		if(Util.isNaoNuloBrancoZero(form.getContaAuxiliarFinal())){
			mapaParametros.put("contaAuxiliarFinal", new Short(form.getContaAuxiliarFinal()));
		}

		if(Util.isNaoNuloBrancoZero(form.getRegionalInicial())){
			mapaParametros.put("regionalInicial", new Integer(form.getRegionalInicial()));
		}

		if(Util.isNaoNuloBrancoZero(form.getRegionalInicial())){
			mapaParametros.put("regionalFinal", new Integer(form.getRegionalFinal()));
		}

		if(!Util.isVazioOuBranco(form.getIdEventoComercial())){
			mapaParametros.put("idEventoComercial", new Integer(form.getIdEventoComercial()));
		}
		if(!Util.isVazioOuBranco(form.getIdCategoria())){
			mapaParametros.put("idCategoria", new Integer(form.getIdCategoria()));
		}
		if(!Util.isVazioOuBranco(form.getIdComplementoLancamentoItemContabil())){
			mapaParametros.put("idLancamentoItemContabil", new Integer(form.getIdComplementoLancamentoItemContabil()));
		}
		if(!Util.isVazioOuBranco(form.getIdComplementoImposto())){
			mapaParametros.put("idImpostoTipo", new Integer(form.getIdComplementoImposto()));
		}
		if(!Util.isVazioOuBranco(form.getIdModulo())){
			mapaParametros.put("idModulo", new Integer(form.getIdModulo()));
		}

		return mapaParametros;
	}

	private void carregarCombos(HttpServletRequest httpServletRequest,
					ConsultarLancamentoSinteticoActionForm consultarLancamentoSinteticoActionForm, Fachada fachada){

		// carregar combo Conta Contábil
		FiltroContaContabil filtroContaContabil = new FiltroContaContabil();
		filtroContaContabil.setCampoOrderBy(FiltroContaContabil.ID);
		httpServletRequest.setAttribute("colecaoContaContabil", fachada.pesquisar(filtroContaContabil, ContaContabil.class.getName()));

		// carregar combo Conta Contábil
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		httpServletRequest.setAttribute("colecaoRegional", fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName()));

		// carregar combo modulo
		FiltroModulo filtroModulo = new FiltroModulo();
		filtroModulo.setCampoOrderBy(FiltroModulo.DESCRICAO_MODULO);
		httpServletRequest.setAttribute("colecaoModulo", fachada.pesquisar(filtroModulo, Modulo.class.getName()));

		// carregar combo evento comercial
		FiltroEventoComercial filtroEventoComercial = new FiltroEventoComercial();
		filtroEventoComercial.setCampoOrderBy(FiltroEventoComercial.DESCRICAO);
		String idModulo = consultarLancamentoSinteticoActionForm.getIdModulo();
		if(idModulo != null && !idModulo.trim().equals("")){
			filtroEventoComercial.adicionarParametro(new ParametroSimples(FiltroEventoComercial.MODULO_ID, new Integer(idModulo)));
		}
		Collection<EventoComercial> listaEventoComercial = fachada.pesquisar(filtroEventoComercial, EventoComercial.class.getName());
		httpServletRequest.setAttribute("colecaoEventoComercial", listaEventoComercial);

		// carregar combo item contabil
		String idEventoComercial = consultarLancamentoSinteticoActionForm.getIdEventoComercial();
		if(idEventoComercial != null && !idEventoComercial.trim().equals("") && listaEventoComercial != null
						&& listaEventoComercial.size() > 0){
			EventoComercial eventoComercial = fachada.obterEventoComercial(new Integer(idEventoComercial));
			if(eventoComercial.getIndicadorComplemento() != null){
				if(eventoComercial.getIndicadorComplemento().trim().equals("I")){

					FiltroLancamentoItemContabil filtroLancamentoItemContabil = new FiltroLancamentoItemContabil(
									FiltroLancamentoItemContabil.DESCRICAO);
					httpServletRequest.setAttribute("colecaoComplementoItemContabil", fachada.pesquisar(filtroLancamentoItemContabil,
									LancamentoItemContabil.class.getName()));

				}else if(eventoComercial.getIndicadorComplemento().trim().equals("T")){

					FiltroImpostoTipo filtroImpostoTipo = new FiltroImpostoTipo(FiltroImpostoTipo.DESCRICAO);
					httpServletRequest.setAttribute("colecaoComplementoImposto", fachada.pesquisar(filtroImpostoTipo, ImpostoTipo.class
									.getName()));
				}
			}
		}

		// carregar combo categoria
		FiltroCategoria filtroCategoria = new FiltroCategoria(FiltroCategoria.DESCRICAO);
		httpServletRequest.setAttribute("colecaoCategoria", fachada.pesquisar(filtroCategoria, Categoria.class.getName()));
	}

	private void carregarComboUnidadeContabilAgrupamento(HttpServletRequest httpServletRequest) throws ControladorException{

		Fachada fachada = Fachada.getInstancia();

		String parametroUnidadeContabilAgrupamento = ParametroContabil.UNIDADE_CONTABIL_AGRUPAMENTO.executar(this).toString();
		httpServletRequest.setAttribute("unidadeContabilAgrupamento", parametroUnidadeContabilAgrupamento);

		if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.LOCALIDADE)){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil", fachada.pesquisar(filtroLocalidade, Localidade.class
							.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.SETOR_COMERCIAL)){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial(FiltroSetorComercial.DESCRICAO);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil", fachada.pesquisar(filtroSetorComercial,
							SetorComercial.class.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.GERENCIA_REGIONAL)){

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil", fachada.pesquisar(filtroGerenciaRegional,
							GerenciaRegional.class.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.UNIDADE_NEGOCIO)){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil", fachada.pesquisar(filtroUnidadeNegocio,
							UnidadeNegocio.class.getName()));
		}
	}

	private void pesquisarLocalidade(ConsultarLancamentoSinteticoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		Integer idLocalidade = null;

		if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(ConstantesSistema.SIM.toString())){
			idLocalidade = Integer.valueOf(form.getLocalidadeInicial());
		}else{
			idLocalidade = Integer.valueOf(form.getLocalidadeFinal());
		}
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(ConstantesSistema.SIM.toString())){
				form.setLocalidadeInicial(localidade.getId().toString());
				form.setNomeLocalidadeInicial(localidade.getDescricao());
				httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			}

			form.setLocalidadeFinal(localidade.getId().toString());
			form.setNomeLocalidadeFinal(localidade.getDescricao());
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

		}else{
			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(ConstantesSistema.SIM.toString())){
				form.setLocalidadeInicial(null);
				form.setNomeLocalidadeInicial("Localidade Inicial inexistente");

				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal(null);
			}else{
				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal("Localidade Final inexistente");
			}
		}
	}

	private void pesquisarContaAuxiliar(ConsultarLancamentoSinteticoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
		Integer idArrecadador = null;

		if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
			idArrecadador = Integer.valueOf(form.getContaAuxiliarInicial());
		}else{
			idArrecadador = Integer.valueOf(form.getContaAuxiliarFinal());
		}
		filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.ID, idArrecadador));
		Collection colecaoArrecadador = this.getFachada().pesquisar(filtroArrecadador, Arrecadador.class.getName());

		if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){

			Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);

			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
				form.setContaAuxiliarInicial(arrecadador.getId().toString());
				form.setNomeContaAuxiliarInicial(arrecadador.getCodigoComNomeCliente());
				httpServletRequest.setAttribute("contaAuxiliarInicialEncontrada", "true");
			}

			form.setContaAuxiliarFinal(arrecadador.getId().toString());
			form.setNomeContaAuxiliarFinal(arrecadador.getCodigoComNomeCliente());
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

		}else{
			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
				form.setContaAuxiliarInicial(null);
				form.setNomeContaAuxiliarInicial("Localidade Inicial inexistente");

				form.setContaAuxiliarFinal(null);
				form.setNomeContaAuxiliarFinal(null);
			}else{
				form.setContaAuxiliarFinal(null);
				form.setNomeContaAuxiliarFinal("Localidade Final inexistente");
			}
		}
	}
}
