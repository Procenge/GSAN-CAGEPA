/**
 * 
 */
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

package gcom.gui.cadastro.sistemaparametro;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 09/01/2007
 */
public class ExibirInformarParametrosSistemaMicromedicaoCobrancaAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirInformarParametrosSistemaMicromedicaoCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		Collection colecaoHidrometroCapacidade = null;

		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

		filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.ID);

		// filtroHidrometroCapacidade
		// .adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());

		httpServletRequest.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);

		// Verifica se ja entrou nesse action, caso nao coloca no form os dados
		// do objeto sistemaParametro

		Integer cont;

		if(sessao.getAttribute("MicromedicaoCobranca") == null){
			cont = 1;
			sessao.setAttribute("MicromedicaoCobranca", cont);

			if(sistemaParametro.getHidrometroCapacidade() != null){

				form.setCodigoMenorCapacidade("" + sistemaParametro.getHidrometroCapacidade().getId());

			}

			if(sistemaParametro.getIndicadorFaixaFalsa() != null){

				form.setIndicadorGeracaoFaixaFalsa("" + sistemaParametro.getIndicadorFaixaFalsa());
			}

			if(sistemaParametro.getIndicadorUsoFaixaFalsa() != null){
				form.setIndicadorPercentualGeracaoFaixaFalsa("" + sistemaParametro.getIndicadorUsoFaixaFalsa());
			}

			if(sistemaParametro.getPercentualFaixaFalsa() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualFaixaFalsa());

				form.setPercentualGeracaoFaixaFalsa("" + valorAux);
			}

			if(sistemaParametro.getIndicadorPercentualFiscalizacaoLeitura() != null){
				form.setIndicadorPercentualGeracaoFiscalizacaoLeitura("" + sistemaParametro.getIndicadorPercentualFiscalizacaoLeitura());
			}

			if(sistemaParametro.getIndicadorUsoFiscalizadorLeitura() != null){
				form.setIndicadorGeracaoFiscalizacaoLeitura("" + sistemaParametro.getIndicadorUsoFiscalizadorLeitura());
			}

			if(sistemaParametro.getPercentualFiscalizacaoLeitura() != null){

				String valorAux = Util.formatarMoedaReal(sistemaParametro.getPercentualFiscalizacaoLeitura());

				form.setPercentualGeracaoFiscalizacaoLeitura("" + valorAux);
			}

			if(sistemaParametro.getIncrementoMaximoConsumoRateio() != null){

				form.setIncrementoMaximoConsumo("" + sistemaParametro.getIncrementoMaximoConsumoRateio());
			}

			if(sistemaParametro.getDecrementoMaximoConsumoRateio() != null){
				form.setDecrementoMaximoConsumo("" + sistemaParametro.getDecrementoMaximoConsumoRateio());
			}

			if(sistemaParametro.getPercentualToleranciaRateio() != null){

				String valorAux = Util.formataBigDecimal(sistemaParametro.getPercentualToleranciaRateio(), 1, false);

				form.setPercentualToleranciaRateioConsumo("" + valorAux);
			}

			if(sistemaParametro.getNumeroDiasVencimentoCobranca() != null){

				form.setDiasVencimentoCobranca("" + sistemaParametro.getNumeroDiasVencimentoCobranca());

			}

			if(sistemaParametro.getIndicadorLayoutArquivoLeituraPadrao() != null){
				form.setIndicadorLayoutArquivoLeituraPadrao(sistemaParametro.getIndicadorLayoutArquivoLeituraPadrao().toString());
			}

			if(sistemaParametro.getCodigoLimiteAceitavelAnormalidades() != null){
				form.setCodigoLimiteAceitavelAnormalidades(sistemaParametro.getCodigoLimiteAceitavelAnormalidades().toString());
			}

			if(sistemaParametro.getPercentualAnormalidadeAceitavel() != null){
				String valorAux = Util.formataBigDecimal(sistemaParametro.getPercentualAnormalidadeAceitavel(), 2, false);

				form.setPercentualAnormalidadeAceitavel("" + valorAux);
			}
		}
		return retorno;
	}
}
