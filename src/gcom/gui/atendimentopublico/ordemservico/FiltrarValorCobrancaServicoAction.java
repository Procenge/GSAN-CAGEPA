/**
 * 
 */
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoCobrancaValor;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 30/10/2006
 */
public class FiltrarValorCobrancaServicoAction
				extends GcomAction {

	/**
	 * [UC0392] Filtrar Valor de Cobrança do Serviço
	 * Este caso de uso cria um filtro que será usado na pesquisa do Valor de
	 * Cobrança de Serviço
	 * 
	 * @author Rômulo Aurélio
	 * @date 30/10/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterValorCobrancaServicoAction");

		FiltrarValorCobrancaServicoActionForm filtrarValorCobrancaServicoActionForm = (FiltrarValorCobrancaServicoActionForm) actionForm;

		FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();

		HttpSession sessao = httpServletRequest.getSession(false);

		boolean peloMenosUmParametroInformado = false;

		String tipoServico = filtrarValorCobrancaServicoActionForm.getTipoServico();

		String perfilImovel = filtrarValorCobrancaServicoActionForm.getPerfilImovel();

		String indicadorMedido = filtrarValorCobrancaServicoActionForm.getIndicadorMedido();

		String capacidadeHidrometro = filtrarValorCobrancaServicoActionForm.getCapacidadeHidrometro();

		String valorServicoInicial = filtrarValorCobrancaServicoActionForm.getValorServicoInicial();

		String valorServicoFinal = filtrarValorCobrancaServicoActionForm.getValorServicoFinal();

		// Verifica se o campo tipoServico foi informado

		if(tipoServico != null && !tipoServico.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.SERVICOTIPO, tipoServico));

		}

		// Verifica se o campo perfilImovel foi informado

		if(perfilImovel != null && !perfilImovel.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			peloMenosUmParametroInformado = true;

			filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.IMOVELPERFIL, perfilImovel));

		}

		// Verifica se o campo indicadorMedido foi informado

		if(indicadorMedido != null && !indicadorMedido.trim().equals("3")){

			peloMenosUmParametroInformado = true;
			filtroServicoCobrancaValor
							.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.INDICADORMEDIDO, indicadorMedido));

		}

		// Verifica se o campo capacidadeHidrometro foi informado

		if(capacidadeHidrometro != null && !capacidadeHidrometro.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.HIDROMETROCAPACIDADE,
							capacidadeHidrometro));

		}

		// Verifica se o campo valorServicoInicial foi informado

		if(valorServicoInicial != null && !valorServicoInicial.trim().equalsIgnoreCase("")){

			String valorSemPontos = valorServicoInicial.replace(".", "");

			valorServicoInicial = valorSemPontos.replace(",", ".");

			peloMenosUmParametroInformado = true;

		}else{

			valorServicoInicial = "00000000000";

		}

		// Verifica se o campo valorServicoFinal foi informado

		if(valorServicoFinal != null && !valorServicoFinal.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			String valorSemPontos = valorServicoFinal.replace(".", "");

			valorServicoFinal = valorSemPontos.replace(",", ".");

		}else{

			valorServicoFinal = "99999999999";
		}

		BigDecimal valorInicial = new BigDecimal(valorServicoInicial);

		BigDecimal valorFinal = new BigDecimal(valorServicoFinal);

		Integer resultado = valorInicial.compareTo(valorFinal);

		if(resultado == 1){
			throw new ActionServletException("atencao.valor_servico_final_menor_valor_servico_inicial");
		}

		filtroServicoCobrancaValor.adicionarParametro(new Intervalo(FiltroServicoCobrancaValor.VALOR, valorInicial, valorFinal));

		filtroServicoCobrancaValor.setCampoOrderBy(FiltroServicoCobrancaValor.SERVICOTIPO);

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");

		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");

		// filtroServicoCobrancaValor
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroServicoCobrancaValor.INDICADORMEDIDO);
		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

		// Verifica se o checkbox Atualizar está marcado e em caso afirmativo
		// manda pelo um request uma variável para o
		// ExibirManterValorCobrancaServicoAction para nele verificar se irá
		// para o
		// atualizar ou para o manter
		if(filtrarValorCobrancaServicoActionForm.getAtualizar() != null
						&& filtrarValorCobrancaServicoActionForm.getAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", filtrarValorCobrancaServicoActionForm.getAtualizar());

		}

		sessao.setAttribute("filtroServicoCobrancaValor", filtroServicoCobrancaValor);

		httpServletRequest.setAttribute("filtroServicoCobrancaValor", filtroServicoCobrancaValor);

		return retorno;

	}
}
