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
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroImpostoTipo;
import gcom.faturamento.ImpostoTipo;
import gcom.financeiro.ContaContabil;
import gcom.financeiro.FiltroContaContabil;
import gcom.financeiro.lancamento.FiltroLancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroModulo;
import gcom.seguranca.acesso.Modulo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.ParametroContabil;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição da tela de consultar lancamentos contabeis sinteticos
 * 
 * @author Genival Barbosa
 * @created 07 de Julho de 2011
 */
public class ExibirConsultarLancamentoSinteticoAction
				extends GcomAction
				implements ExecutorParametro {

	private static final String OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL = "3";

	private static final String OBJETO_CONSULTA_CONTA_AUXILIAR_FINAL = "4";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ConsultarLancamentoSinteticoActionForm consultarLancamentoSinteticoActionForm = (ConsultarLancamentoSinteticoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("exibirConsultarLancamentoSintetico");

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade Inicial / Final
		if(Util.isNaoNuloBrancoZero(objetoConsulta)
						&& (objetoConsulta.equals(ConstantesSistema.SIM.toString()) || objetoConsulta.equals(ConstantesSistema.NAO
										.toString()))){
			this.pesquisarLocalidade(consultarLancamentoSinteticoActionForm, objetoConsulta, httpServletRequest);
		}

		// Pesquisar Conta Auxiliar Inicial / Final
		if(Util.isNaoNuloBrancoZero(objetoConsulta)
						&& (objetoConsulta.equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL) || objetoConsulta
										.equals(OBJETO_CONSULTA_CONTA_AUXILIAR_FINAL))){
			this.pesquisarContaAuxiliar(consultarLancamentoSinteticoActionForm, objetoConsulta, httpServletRequest);
		}

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
					httpServletRequest.setAttribute("colecaoComplementoItemContabil",
									fachada.pesquisar(filtroLancamentoItemContabil, LancamentoItemContabil.class.getName()));

				}else if(eventoComercial.getIndicadorComplemento().trim().equals("T")){

					FiltroImpostoTipo filtroImpostoTipo = new FiltroImpostoTipo(FiltroImpostoTipo.DESCRICAO);
					httpServletRequest.setAttribute("colecaoComplementoImposto",
									fachada.pesquisar(filtroImpostoTipo, ImpostoTipo.class.getName()));
				}
			}
		}

		// try{
		// carregarComboUnidadeContabilAgrupamento(httpServletRequest);
		// }catch(ControladorException e){
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// carregar combo categoria
		FiltroCategoria filtroCategoria = new FiltroCategoria(FiltroCategoria.DESCRICAO);
		httpServletRequest.setAttribute("colecaoCategoria", fachada.pesquisar(filtroCategoria, Categoria.class.getName()));

		return retorno;

	}

	private void carregarComboUnidadeContabilAgrupamento(HttpServletRequest httpServletRequest) throws ControladorException{

		Fachada fachada = Fachada.getInstancia();

		String parametroUnidadeContabilAgrupamento = ParametroContabil.UNIDADE_CONTABIL_AGRUPAMENTO.executar(this).toString();
		httpServletRequest.setAttribute("unidadeContabilAgrupamento", parametroUnidadeContabilAgrupamento);

		if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.LOCALIDADE)){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil",
							fachada.pesquisar(filtroLocalidade, Localidade.class.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.SETOR_COMERCIAL)){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial(FiltroSetorComercial.DESCRICAO);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil",
							fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.GERENCIA_REGIONAL)){

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil",
							fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName()));

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.UNIDADE_NEGOCIO)){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);
			httpServletRequest.setAttribute("colecaoUnidadeAgrupamentoContabil",
							fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName()));
		}
	}

	private void pesquisarLocalidade(ConsultarLancamentoSinteticoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

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
				sessao.setAttribute("localidadeInicialEncontrada", "true");
			}

			form.setLocalidadeFinal(localidade.getId().toString());
			form.setNomeLocalidadeFinal(localidade.getDescricao());
			sessao.setAttribute("localidadeFinalEncontrada", "true");

		}else{
			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(ConstantesSistema.SIM.toString())){
				form.setLocalidadeInicial(null);
				form.setNomeLocalidadeInicial("Localidade Inicial inexistente");

				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal(null);
				sessao.removeAttribute("localidadeInicialEncontrada");
				sessao.removeAttribute("localidadeFinalEncontrada");
			}else{
				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal("Localidade Final inexistente");
				sessao.removeAttribute("localidadeFinalEncontrada");
			}
		}
	}

	private void pesquisarContaAuxiliar(ConsultarLancamentoSinteticoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
		Integer idArrecadador = null;

		if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
			idArrecadador = Integer.valueOf(form.getContaAuxiliarInicial());
		}else{
			idArrecadador = Integer.valueOf(form.getContaAuxiliarFinal());
		}
		filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.ID, idArrecadador));
		filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);
		Collection colecaoArrecadador = this.getFachada().pesquisar(filtroArrecadador, Arrecadador.class.getName());

		if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){

			Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);

			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
				form.setContaAuxiliarInicial(arrecadador.getId().toString());
				form.setNomeContaAuxiliarInicial(arrecadador.getCodigoComNomeCliente());
				sessao.setAttribute("contaAuxiliarInicialEncontrada", "true");
			}

			form.setContaAuxiliarFinal(arrecadador.getId().toString());
			form.setNomeContaAuxiliarFinal(arrecadador.getCodigoComNomeCliente());
			sessao.setAttribute("contaAuxiliarFinalEncontrada", "true");

		}else{
			if(Util.isNaoNuloBrancoZero(objetoConsulta) && objetoConsulta.trim().equals(OBJETO_CONSULTA_CONTA_AUXILIAR_INICIAL)){
				form.setContaAuxiliarInicial(null);
				form.setNomeContaAuxiliarInicial("Conta Auxliar Inicial inexistente");

				form.setContaAuxiliarFinal(null);
				form.setNomeContaAuxiliarFinal(null);
				sessao.removeAttribute("contaAuxiliarInicialEncontrada");
				sessao.removeAttribute("contaAuxiliarFinalEncontrada");
			}else{
				form.setContaAuxiliarFinal(null);
				form.setNomeContaAuxiliarFinal("Conta Auxliar Final inexistente");
				sessao.removeAttribute("contaAuxiliarFinalEncontrada");
			}
		}
	}
}