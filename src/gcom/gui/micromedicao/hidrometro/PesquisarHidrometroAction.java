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

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.bean.FiltroHidrometroHelper;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarHidrometroAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obt�m o action form
		PesquisarHidrometroActionForm pesquisarHidrometroActionForm = (PesquisarHidrometroActionForm) actionForm;

		// Cole��o que armazena o resultado da pesquisa
		Collection<Hidrometro> colecaoHidrometros = null;

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping.findForward("listaHidrometro");

		// cria sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega a instancia da fachada
		Fachada fachada = Fachada.getInstancia();

		FiltroHidrometroHelper filtroHidrometroHelper = new FiltroHidrometroHelper();

		// Recupera os par�metros do form
		String numeroHidrometro = pesquisarHidrometroActionForm.getNumeroHidrometro();
		String dataAquisicao = pesquisarHidrometroActionForm.getDataAquisicao();
		String anoFabricacao = pesquisarHidrometroActionForm.getAnoFabricacao();
		String indicadorMacromedidor = pesquisarHidrometroActionForm.getFinalidade();
		String idHidrometroClasseMetrologica = pesquisarHidrometroActionForm.getIdHidrometroClasseMetrologica();
		String idHidrometroMarca = pesquisarHidrometroActionForm.getIdHidrometroMarca();
		String idHidrometroDiametro = pesquisarHidrometroActionForm.getIdHidrometroDiametro();
		String idHidrometroCapacidade = pesquisarHidrometroActionForm.getIdHidrometroCapacidade();
		String idHidrometroTipo = pesquisarHidrometroActionForm.getIdHidrometroTipo();
		String idLocalArmazenagem = pesquisarHidrometroActionForm.getIdLocalArmazenamento();
		String idHidrometroSituacao = pesquisarHidrometroActionForm.getIdHidrometroSituacao();
		String fixo = pesquisarHidrometroActionForm.getFixo();
		String faixaInicial = pesquisarHidrometroActionForm.getFaixaInicial();
		String faixaFinal = pesquisarHidrometroActionForm.getFaixaFinal();
		String dataInstalacao = pesquisarHidrometroActionForm.getDataInstalacao();

		boolean peloMenosUmParametroInformado = false;

		// Caso o fixo, a faixa inicial e faixa final seja diferente de null
		// ent�o ignora os outros parametros e faz a pesquisa do filtro por
		// esses 3 par�metros
		if((fixo != null && !fixo.trim().equalsIgnoreCase(""))
						&& (faixaInicial != null && !faixaInicial.trim().equalsIgnoreCase("") && (faixaFinal != null && !faixaFinal.trim()
										.equalsIgnoreCase("")))){

			// Verifica se a faixa inicial e final s�o iguais a zero
			if(faixaInicial.equals("") || faixaInicial == null){
				throw new ActionServletException("atencao.faixa.inicial.deve.ser.informada");
			}else{
				if(faixaInicial.equals("0")){
					throw new ActionServletException("atencao.faixa.inicial.deve.ser.maior.zero");
				}
			}
			if(faixaFinal.equals("") || faixaFinal == null){
				throw new ActionServletException("atencao.faixa.final.deve.ser.informada");
			}else{
				if(faixaFinal.equals("0")){
					throw new ActionServletException("atencao.faixa.final.deve.ser.maior.zero");
				}
			}

			if(faixaInicial != null && faixaFinal != null){
				Integer faixaIni = new Integer(faixaInicial);
				Integer faixaFim = new Integer(faixaFinal);
				if(faixaIni > faixaFim){
					throw new ActionServletException("atencao.faixa.final.deve.ser.maior.faixao.inicial");
				}
			}

			colecaoHidrometros = fachada.pesquisarNumeroHidrometroFaixa(fixo, faixaInicial, faixaFinal);
		}else{
			// Insere os par�metros informados no filtro
			if(numeroHidrometro != null && !numeroHidrometro.trim().equalsIgnoreCase("")){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setNumero(numeroHidrometro);
			}

			if(dataAquisicao != null && !dataAquisicao.trim().equalsIgnoreCase("")){

				// Caso a data de aquisi��o seja maior que a data atual
				SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
				Date dataAquisicaoFormatada = null;
				try{
					dataAquisicaoFormatada = formatoData.parse(dataAquisicao);
				}catch(ParseException ex){
					// Erro no hibernate
					reportarErros(httpServletRequest, "erro.sistema", ex);
					// Atribui o mapeamento de retorno para a tela de erro
					retorno = actionMapping.findForward("telaErro");
				}

				// caso a data de aquisi��o seja menor que a data atual
				if(dataAquisicaoFormatada.after(new Date())){
					throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
				}

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setDataAquisicao(Util.converteStringParaDate(dataAquisicao, true));
			}

			if(anoFabricacao != null && !anoFabricacao.trim().equalsIgnoreCase("")){
				// Verifica se o ano de fabrica��o � maior que o ano da data de aquisi��o
				// Integer anoFabricacaoForm = new Integer(anoFabricacao);

				Short anoFabricacaoForm = new Short(anoFabricacao);

				if(dataAquisicao != null && !dataAquisicao.trim().equalsIgnoreCase("") && dataAquisicao.length() == 10){

					Integer anoDataAquisicao = new Integer(dataAquisicao.substring(6));
					if(anoFabricacaoForm > anoDataAquisicao){
						throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.fabricacao");
					}
				}

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setAnoFabricacao(Util.obterShort(anoFabricacao));
			}

			if(indicadorMacromedidor != null && !indicadorMacromedidor.trim().equalsIgnoreCase("")
							&& !indicadorMacromedidor.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIndicadorMacromedidor(Util.obterShort(indicadorMacromedidor));
			}

			if(idHidrometroClasseMetrologica != null
							&& Integer.parseInt(idHidrometroClasseMetrologica) > ConstantesSistema.NUMERO_NAO_INFORMADO){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroClasseMetrologica(Util.obterInteger(idHidrometroClasseMetrologica));
			}

			if(idHidrometroMarca != null && Integer.parseInt(idHidrometroMarca) > ConstantesSistema.NUMERO_NAO_INFORMADO){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroMarca(Util.obterInteger(idHidrometroMarca));
			}

			if(idHidrometroDiametro != null && Integer.parseInt(idHidrometroDiametro) > ConstantesSistema.NUMERO_NAO_INFORMADO){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroDiametro(Util.obterInteger(idHidrometroDiametro));
			}

			if(idHidrometroCapacidade != null && Integer.parseInt(idHidrometroCapacidade) > ConstantesSistema.NUMERO_NAO_INFORMADO){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroCapacidade(Util.obterInteger(idHidrometroCapacidade));
			}
			if(idHidrometroTipo != null && Integer.parseInt(idHidrometroTipo) > ConstantesSistema.NUMERO_NAO_INFORMADO){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroTipo(Util.obterInteger(idHidrometroTipo));
			}

			if(idLocalArmazenagem != null && !idLocalArmazenagem.equals("")){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroLocalArmazenagem(Util.obterInteger(idLocalArmazenagem));
			}

			if(!Util.isVazioOuBranco(idHidrometroSituacao)){

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setIdHidrometroSituacao(Util.obterInteger(idHidrometroSituacao));
			}

			if(!Util.isVazioOuBranco(dataInstalacao)
							&& (filtroHidrometroHelper.getIdHidrometroSituacao() == null || filtroHidrometroHelper
											.getIdHidrometroSituacao().equals(HidrometroSituacao.INSTALADO))){

				Date dataInstalacaoDate = Util.converteStringParaDate(dataInstalacao, true);

				// Caso a data de instal��o seja maior que a data corrente
				if(dataInstalacaoDate.after(new Date())){

					throw new ActionServletException("atencao.data.instalacao.nao.superior.data.corrente");
				}

				peloMenosUmParametroInformado = true;
				filtroHidrometroHelper.setDataInstalacao(dataInstalacaoDate);
				filtroHidrometroHelper.setConsultarHistoricoInstalacao(true);
			}else{

				filtroHidrometroHelper.setDataInstalacao(null);
			}

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if(!peloMenosUmParametroInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Integer totalRegistros = fachada.pesquisarHidrometroFiltroTotalRegistros(filtroHidrometroHelper);

			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

			colecaoHidrometros = fachada.pesquisarHidrometroFiltro(filtroHidrometroHelper,
							(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		}

		// Verificar se a pesquisa de hidrometros retornou vazia
		if(colecaoHidrometros == null || colecaoHidrometros.isEmpty()){

			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Hidr�metro");
		}

		sessao.setAttribute("colecaoHidrometros", colecaoHidrometros);
		// Manda a cole��o dos hidr�metros pesquisados para o request
		httpServletRequest.getSession(false).setAttribute("colecaoHidrometros", colecaoHidrometros);

		return retorno;
	}
}
