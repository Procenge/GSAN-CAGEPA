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

package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarResolucaoDiretoriaAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("manterResolucaoDiretoria");

		FiltrarResolucaoDiretoriaActionForm filtrarResolucaoDiretoriaActionForm = (FiltrarResolucaoDiretoriaActionForm) actionForm;
		ResolucaoDiretoriaGrupoHelper resolucaoDiretoriaGrupoHelper = new ResolucaoDiretoriaGrupoHelper();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa todo o formul�rio para evitar "sujeiras" na tela
		String numero = "";
		String assunto = "";
		String dataInicio = "";
		String dataFim = "";
		String resolucaoDiretoriaLayout = "";
		String grupo = "";
		String indicadorUsoRDParcImovel = "";
		String indicadorUsoRDUsuarios = "";
		String indicadorUsoRDDebitoCobrar = "";
		String indicadorEmissaoAssuntoConta;
		String indicadorTrataMediaAtualizacaoMonetaria;
		String indicadorCobrarDescontosArrasto;
		String indicadorArrasto;
		String idLocalidade = "";
		String mesAnoReferenciaDebitoInicio = "";
		String mesAnoReferenciaDebitoFim = "";
		String tipoPesquisa = "";

		// Verifica se voltou do ManterResolucaoDiretoria e para isso, verifica
		// se a pagina��o � nula
		if(httpServletRequest.getParameter("page.offset") == null){

			// Variaveis
			// Recupera os par�metros do form, setando-os na sess�o para poder
			// recuper�-los e coloc�-los novamente na p�gina caso o usu�rio
			// deseje voltar para o filtro
			numero = filtrarResolucaoDiretoriaActionForm.getNumero();
			sessao.setAttribute("numero", numero);
			assunto = filtrarResolucaoDiretoriaActionForm.getAssunto();
			sessao.setAttribute("assunto", assunto);
			dataInicio = filtrarResolucaoDiretoriaActionForm.getDataInicio();
			sessao.setAttribute("dataInicio", dataInicio);
			dataFim = filtrarResolucaoDiretoriaActionForm.getDataFim();
			sessao.setAttribute("dataFim", dataFim);
			resolucaoDiretoriaLayout = filtrarResolucaoDiretoriaActionForm.getResolucaoDiretoriaLayout();
			sessao.setAttribute("resolucaoDiretoriaLayout", resolucaoDiretoriaLayout);
			grupo = filtrarResolucaoDiretoriaActionForm.getGrupo();
			sessao.setAttribute("grupo", grupo);
			indicadorUsoRDParcImovel = filtrarResolucaoDiretoriaActionForm.getIndicadorUsoRDParcImovel();
			sessao.setAttribute("indicadorUsoRDParcImovel", indicadorUsoRDParcImovel);
			indicadorUsoRDUsuarios = filtrarResolucaoDiretoriaActionForm.getIndicadorUsoRDUsuarios();
			sessao.setAttribute("indicadorUsoRDUsuarios", indicadorUsoRDUsuarios);
			indicadorUsoRDDebitoCobrar = filtrarResolucaoDiretoriaActionForm.getIndicadorUsoRDDebitoCobrar();
			sessao.setAttribute("indicadorUsoRDDebitoCobrar", indicadorUsoRDDebitoCobrar);
			indicadorEmissaoAssuntoConta = filtrarResolucaoDiretoriaActionForm.getIndicadorEmissaoAssuntoConta();
			sessao.setAttribute("indicadorEmissaoAssuntoConta", indicadorEmissaoAssuntoConta);
			indicadorTrataMediaAtualizacaoMonetaria = filtrarResolucaoDiretoriaActionForm.getIndicadorTrataMediaAtualizacaoMonetaria();
			sessao.setAttribute("indicadorTrataMediaAtualizacaoMonetaria", indicadorTrataMediaAtualizacaoMonetaria);
			indicadorCobrarDescontosArrasto = filtrarResolucaoDiretoriaActionForm.getIndicadorCobrarDescontosArrasto();
			sessao.setAttribute("indicadorCobrarDescontosArrasto", indicadorCobrarDescontosArrasto);
			indicadorArrasto = filtrarResolucaoDiretoriaActionForm.getIndicadorArrasto();
			sessao.setAttribute("indicadorArrasto", indicadorArrasto);
			idLocalidade = filtrarResolucaoDiretoriaActionForm.getIdLocalidade();
			sessao.setAttribute("idLocalidade", idLocalidade);
			mesAnoReferenciaDebitoInicio = filtrarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio();
			sessao.setAttribute("anoMesReferenciaDebitoInicio", mesAnoReferenciaDebitoInicio);
			mesAnoReferenciaDebitoFim = filtrarResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim();
			sessao.setAttribute("anoMesReferenciaDebitoFim", mesAnoReferenciaDebitoFim);
			tipoPesquisa = filtrarResolucaoDiretoriaActionForm.getTipoPesquisa();
			sessao.setAttribute("tipoPesquisa", tipoPesquisa);
		}else{
			// Recupera as vari�veis digitadas anteriormente para coloc�-los
			// novamente no filtro, pois devido ao esquema de
			// pagina��o, cada vez que o usu�rio seleciona a visualiza��o das
			// anteriores ou pr�ximas resolu��es de diretoria � efetuado um novo
			// filtro
			numero = (String) sessao.getAttribute("numero");
			assunto = (String) sessao.getAttribute("assunto");
			dataInicio = (String) sessao.getAttribute("dataInicio");
			dataFim = (String) sessao.getAttribute("dataFim");
			resolucaoDiretoriaLayout = (String) sessao.getAttribute("resolucaoDiretoriaLayout");
			grupo = (String) sessao.getAttribute("grupo");
			indicadorUsoRDParcImovel = (String) sessao.getAttribute("indicadorUsoRDParcImovel");
			indicadorUsoRDUsuarios = (String) sessao.getAttribute("indicadorUsoRDUsuarios");
			indicadorUsoRDDebitoCobrar = (String) sessao.getAttribute("indicadorUsoRDDebitoCobrar");
			indicadorEmissaoAssuntoConta = (String) sessao.getAttribute("indicadorEmissaoAssuntoConta");
			indicadorTrataMediaAtualizacaoMonetaria = (String) sessao.getAttribute("indicadorTrataMediaAtualizacaoMonetaria");
			indicadorCobrarDescontosArrasto = (String) sessao.getAttribute("indicadorCobrarDescontosArrasto");
			indicadorArrasto = (String) sessao.getAttribute("indicadorArrasto");
			idLocalidade = (String) sessao.getAttribute("idLocalidade");
			mesAnoReferenciaDebitoInicio = (String) sessao.getAttribute("anoMesReferenciaDebitoInicio");
			mesAnoReferenciaDebitoFim = (String) sessao.getAttribute("anoMesReferenciaDebitoFim");
			tipoPesquisa = (String) sessao.getAttribute("tipoPesquisa");
		}

		// Cria o filtro
		FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
		filtroResolucaoDiretoria.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoriaGrupos");
		filtroResolucaoDiretoria
						.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_PAGTO_A_VISTA);
		filtroResolucaoDiretoria
						.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_CONDICOES_VALOR_ENTRADA);

		// Ordena a pesquisa por um par�metro pr�-definido
		filtroResolucaoDiretoria.setCampoOrderBy("numeroResolucaoDiretoria");

		if(dataInicio != null && !dataInicio.trim().equals("") && dataFim != null && !dataFim.trim().equals("")){
			if((Util.converteStringParaDate(dataInicio)).compareTo(Util.converteStringParaDate(dataFim)) >= 0){
				throw new ActionServletException("atencao.termino_vigencia.anterior.inicio_vigencia");
			}
		}

		boolean peloMenosUmParametroInformado = false;

		// Neste ponto o filtro � criado com os par�metros informados na p�gina
		// de filtrar resolu��o de diretoria para ser executada a pesquisa no
		// ExibirManterResolucaoDiretoriaAction

		// Caso o usu�rio informe o N�mero da RD, desconsiderar os demais par�metros do filtro
		if(numero != null && !numero.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.NUMERO, numero));
			resolucaoDiretoriaGrupoHelper.setNumero(numero);
		}else{
			if(assunto != null && !assunto.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;
				resolucaoDiretoriaGrupoHelper.setAssunto(assunto);

				if(tipoPesquisa != null && !tipoPesquisa.trim().equalsIgnoreCase("")){
					peloMenosUmParametroInformado = true;
					resolucaoDiretoriaGrupoHelper.setTipoPesquisa(tipoPesquisa);
					filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.TIPO_PESQUISA, tipoPesquisa));
					if(tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
						filtroResolucaoDiretoria
										.adicionarParametro(new ComparacaoTextoCompleto(FiltroResolucaoDiretoria.DESCRICAO, assunto));
					}else{
						filtroResolucaoDiretoria.adicionarParametro(new ComparacaoTexto(FiltroResolucaoDiretoria.DESCRICAO, assunto));
					}
				}
			}

			if(dataInicio != null && !dataInicio.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;

				Date dataInicioFormatada = Util.converteStringParaDate(dataInicio);

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.DATA_VIGENCIA_INICIO,
								dataInicioFormatada));
				resolucaoDiretoriaGrupoHelper.setDataInicio(dataInicioFormatada);
			}

			if(dataFim != null && !dataFim.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;

				Date dataFimFormatada = Util.converteStringParaDate(dataFim);

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.DATA_VIGENCIA_FIM,
								dataFimFormatada));
				resolucaoDiretoriaGrupoHelper.setDataFim(dataFimFormatada);
			}

			if(resolucaoDiretoriaLayout != null && !resolucaoDiretoriaLayout.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;

				ResolucaoDiretoriaLayout resolucao = new ResolucaoDiretoriaLayout();
				resolucao.setId(Integer.valueOf(resolucaoDiretoriaLayout));

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_LAYOUT,
								resolucao));
				resolucaoDiretoriaGrupoHelper.setResolucaoDiretoriaLayout(resolucao);
			}

			if(grupo != null && !grupo.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_GRUPO_ID,
								grupo));
				resolucaoDiretoriaGrupoHelper.setGrupo(Integer.valueOf(grupo));
			}

			if(!Util.isVazioOuBranco(indicadorUsoRDParcImovel)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_PARCELAMENTO_UNICO,
								Short.valueOf(indicadorUsoRDParcImovel)));
				resolucaoDiretoriaGrupoHelper.setIndicadorUsoRDParcImovel(indicadorUsoRDParcImovel);
			}

			if(!Util.isVazioOuBranco(indicadorUsoRDUsuarios)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_UTILIZACAO_LIVRE, Short
								.valueOf(indicadorUsoRDUsuarios)));
				resolucaoDiretoriaGrupoHelper.setIndicadorUsoRDUsuarios(indicadorUsoRDUsuarios);
			}

			if(!Util.isVazioOuBranco(indicadorUsoRDDebitoCobrar)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_DESCONTO_SANCOES, Short
								.valueOf(indicadorUsoRDDebitoCobrar)));
				resolucaoDiretoriaGrupoHelper.setIndicadorUsoRDDebitoCobrar(indicadorUsoRDDebitoCobrar);
			}

			if(!Util.isVazioOuBranco(indicadorEmissaoAssuntoConta)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_EMISSAO_ASSUNTO_CONTA,
								Short.valueOf(indicadorEmissaoAssuntoConta)));
				resolucaoDiretoriaGrupoHelper.setIndicadorEmissaoAssuntoConta(indicadorEmissaoAssuntoConta);
			}

			if(!Util.isVazioOuBranco(indicadorTrataMediaAtualizacaoMonetaria)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(
								FiltroResolucaoDiretoria.INDICADOR_TRATA_MEDIA_ATUALIZACAO_MONETARIA, Short
												.valueOf(indicadorTrataMediaAtualizacaoMonetaria)));
				resolucaoDiretoriaGrupoHelper.setIndicadorTrataMediaAtualizacaoMonetaria(indicadorTrataMediaAtualizacaoMonetaria);
			}

			if(!Util.isVazioOuBranco(indicadorCobrarDescontosArrasto)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria
								.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_COBRAR_DESCONTOS_ARRASTO, Short
												.valueOf(indicadorCobrarDescontosArrasto)));
				resolucaoDiretoriaGrupoHelper.setIndicadorCobrarDescontosArrasto(indicadorCobrarDescontosArrasto);
			}

			if(!Util.isVazioOuBranco(indicadorArrasto)){
				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.INDICADOR_ARRASTO, Short
								.valueOf(indicadorArrasto)));
				resolucaoDiretoriaGrupoHelper.setIndicadorArrasto(indicadorArrasto);
			}

			if(!Util.isVazioOuBranco(idLocalidade)){
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));

				Collection localidades = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

				if(Util.isVazioOrNulo(localidades)){
					throw new ActionServletException("pesquisa.localidade.inexistente");
				}

				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(
								FiltroResolucaoDiretoria.PARCELAMENTO_SITUACAO_ESPECIAL_LOCALIDADE_ID, Integer.valueOf(idLocalidade)));
				resolucaoDiretoriaGrupoHelper.setLocalidade(Integer.valueOf(idLocalidade));
			}

			if(!Util.isVazioOuBranco(mesAnoReferenciaDebitoInicio)){

				if(!Util.validarMesAno(mesAnoReferenciaDebitoInicio)){
					throw new ActionServletException("atencao.ano_mes_referencia.invalida");
				}

				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria
								.adicionarParametro(new ParametroSimples(
												FiltroResolucaoDiretoria.PARCELAMENTO_SITUACAO_ESPECIAL_ANOMES_DEBITO_INICIO,
												mesAnoReferenciaDebitoInicio));
				resolucaoDiretoriaGrupoHelper.setAnoMesReferenciaDebitoInicio(Util
								.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaDebitoInicio));
			}

			if(!Util.isVazioOuBranco(mesAnoReferenciaDebitoFim)){

				if(!Util.validarMesAno(mesAnoReferenciaDebitoFim)){
					throw new ActionServletException("atencao.ano_mes_referencia.invalida");
				}else if(!Util.isVazioOuBranco(mesAnoReferenciaDebitoInicio)){
					try{
						Fachada.getInstancia().validarAnoMesInicialFinalPeriodo(mesAnoReferenciaDebitoInicio, mesAnoReferenciaDebitoFim,
										"Per�odo de Refer�ncia do D�bito Inicial", "Per�odo de Refer�ncia do D�bito Final",
										"atencao.referencia.final.menor.referencia.inicial");
					}catch(ControladorException e){
						// abafada pois j� � tratada na fachada
					}
				}

				peloMenosUmParametroInformado = true;

				filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(
								FiltroResolucaoDiretoria.PARCELAMENTO_SITUACAO_ESPECIAL_ANOMES_DEBITO_FIM, mesAnoReferenciaDebitoFim));
				resolucaoDiretoriaGrupoHelper
								.setAnoMesReferenciaDebitoFim(Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaDebitoFim));
			}
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Verifica se o checkbox Atualizar est� marcado e em caso afirmativo
		// manda pelo um request uma vari�vel para o
		// ExibirManterResolucaoDiretoriaAction e nele verificar se ir� para o
		// atualizar ou para o manter
		if(filtrarResolucaoDiretoriaActionForm.getAtualizar() != null
						&& filtrarResolucaoDiretoriaActionForm.getAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", filtrarResolucaoDiretoriaActionForm.getAtualizar());
		}

		// Manda o filtro pela sessao para o
		// ExibirManterResolucaoDiretoriaAction
		sessao.setAttribute("filtroResolucaoDiretoria", filtroResolucaoDiretoria);
		sessao.setAttribute("resolucaoDiretoriaGrupoHelper", resolucaoDiretoriaGrupoHelper);

		return retorno;
	}
}