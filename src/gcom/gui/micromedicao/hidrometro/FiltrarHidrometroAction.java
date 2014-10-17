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

package gcom.gui.micromedicao.hidrometro;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 5 de Setembro de 2005
 */
public class FiltrarHidrometroAction
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

		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		// HttpSession sessao = httpServletRequest.getSession(false);
		HttpSession sessao = httpServletRequest.getSession(false);
		String tela = (String) sessao.getAttribute("tela");
		if(tela != null && !tela.equals("")){
			if(tela.equals("movimentarHidrometro")){
				retorno = actionMapping.findForward("movimentarHidrometro");
			}
		}else{
			retorno = actionMapping.findForward("retornarFiltroHidrometro");
		}

		// Fachada fachada = Fachada.getInstancia();

		// Recupera os par�metros do form
		String numeroHidrometro = hidrometroActionForm.getNumeroHidrometro();
		String dataAquisicao = hidrometroActionForm.getDataAquisicao();
		String anoFabricacao = hidrometroActionForm.getAnoFabricacao();
		String indicadorMacromedidor = hidrometroActionForm.getIndicadorMacromedidor();
		String idHidrometroClasseMetrologica = hidrometroActionForm.getIdHidrometroClasseMetrologica();
		String idHidrometroMarca = hidrometroActionForm.getIdHidrometroMarca();
		String idHidrometroDiametro = hidrometroActionForm.getIdHidrometroDiametro();
		String idHidrometroCapacidade = hidrometroActionForm.getIdHidrometroCapacidade();
		String idHidrometroTipo = hidrometroActionForm.getIdHidrometroTipo();
		String idHidrometroTipoTurbina = hidrometroActionForm.getIdHidrometroTipoTurbina();
		String idHidrometroSituacao = hidrometroActionForm.getIdHidrometroSituacao();
		String idLocalArmazenagem = hidrometroActionForm.getIdLocalArmazenagem();
		String fixo = hidrometroActionForm.getFixo();
		String faixaInicial = hidrometroActionForm.getFaixaInicial();
		String faixaFinal = hidrometroActionForm.getFaixaFinal();
		String codigoFormatoNumeracao = hidrometroActionForm.getCodigoFormatoNumeracao();

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro(FiltroHidrometro.NUMERO_HIDROMETRO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_SITUACAO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM);

		boolean peloMenosUmParametroInformado = false;
		// SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		// Caso o fixo, a faixa inicial e faixa final seja diferente de null
		// ent�o ignora os outros parametros e faz a pesquisa do filtro por
		// esses 3 par�metros
		if(fixo != null && !fixo.equalsIgnoreCase("")){
			if(faixaInicial != null && !faixaInicial.equalsIgnoreCase("")){
				sessao.setAttribute("faixaInicial", faixaInicial);
			}
			if(faixaFinal != null && !faixaFinal.equalsIgnoreCase("")){
				sessao.setAttribute("faixaFinal", faixaFinal);
			}
			sessao.setAttribute("fixo", fixo);
			sessao.setAttribute("codigoFormatoNumeracao", codigoFormatoNumeracao);
			peloMenosUmParametroInformado = true;
		}else{
			// Insere os par�metros informados no filtro
			if(numeroHidrometro != null && !numeroHidrometro.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ComparacaoTexto(FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));
			}

			Date dataAquisicaoDate = Util.converteStringParaDate(dataAquisicao);
			Calendar dataAtual = new GregorianCalendar();
			if(dataAquisicao != null && !dataAquisicao.trim().equalsIgnoreCase("")){

				// caso a data de aquisi��o seja menor que a data atual
				if(dataAquisicaoDate.after(new Date())){
					throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
				}

				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.DATA_AQUISICAO, dataAquisicaoDate));
			}

			if(anoFabricacao != null && !anoFabricacao.trim().equalsIgnoreCase("")){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.ANO_FABRICACAO, anoFabricacao));

				int anoAtual = dataAtual.get(Calendar.YEAR);
				Integer anoFabricacaoInteger = new Integer(anoFabricacao);
				// caso o ano de fabrica��o seja maior que o atual
				if(anoFabricacaoInteger > anoAtual){
					throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");
				}
				if(dataAquisicaoDate != null){
					Integer anoDataAquisicao = Util.getAno(dataAquisicaoDate);
					// caso a data de aquisi��o seja menor que o ano fabrica��o
					if(anoDataAquisicao < anoFabricacaoInteger){
						throw new ActionServletException("atencao.ano.fabricacao.menor.ano.aquisicao");

					}
				}
			}

			if(indicadorMacromedidor != null && !indicadorMacromedidor.trim().equalsIgnoreCase("")
							&& !indicadorMacromedidor.trim().equalsIgnoreCase("-1")){
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.INDICADOR_MACROMEDIDOR, indicadorMacromedidor));
			}

			if(idHidrometroClasseMetrologica != null
							&& Integer.parseInt(idHidrometroClasseMetrologica) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_CLASSE_METROLOGICA_ID,
								idHidrometroClasseMetrologica));
			}

			if(idHidrometroMarca != null && Integer.parseInt(idHidrometroMarca) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_MARCA_ID, idHidrometroMarca));
			}

			if(idHidrometroDiametro != null && Integer.parseInt(idHidrometroDiametro) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_DIAMETRO_ID, idHidrometroDiametro));
			}

			if(idHidrometroCapacidade != null && Integer.parseInt(idHidrometroCapacidade) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro
								.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_CAPACIDADE_ID, idHidrometroCapacidade));
			}
			if(idHidrometroTipo != null && Integer.parseInt(idHidrometroTipo) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_TIPO_ID, idHidrometroTipo));
			}

			if(idHidrometroSituacao != null && Integer.parseInt(idHidrometroSituacao) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_SITUACAO_ID, idHidrometroSituacao));
			}

			if(idLocalArmazenagem != null && !idLocalArmazenagem.equals("")){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM_ID,
								idLocalArmazenagem));
			}

			if(!Util.isVazioOuBranco(idHidrometroTipoTurbina)
							&& Integer.parseInt(idHidrometroTipoTurbina) > ConstantesSistema.NUMERO_NAO_INFORMADO){
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.HIDROMETRO_TIPO_TURBINA_ID,
								idHidrometroTipoTurbina));
			}

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if(!peloMenosUmParametroInformado){
				/*
				 * if(sessao.getAttribute("parametroInformado") != "sim")
				 * {
				 */
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
				// }
			}

			if(retorno.getName().equalsIgnoreCase("movimentarHidrometro")){
				filtroHidrometro.setConsultaSemLimites(true);
			}

			// Manda o filtro pela sess�o para o ExibirManterHidrometroAction
			sessao.setAttribute("filtroHidrometro", filtroHidrometro);

			sessao.setAttribute("voltarFiltrar", "1");

			sessao.removeAttribute("fixo");
			sessao.removeAttribute("faixaFinal");
			sessao.removeAttribute("faixaInicial");
			sessao.removeAttribute("codigoFormatoNumeracao");
		}
		sessao.setAttribute("filtrar_manter", "filtrar_manter");

		return retorno;
	}
}
