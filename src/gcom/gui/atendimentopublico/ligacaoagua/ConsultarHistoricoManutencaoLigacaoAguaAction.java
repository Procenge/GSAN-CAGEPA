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

package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Consultar Histórico de Manutenção de Ligação de Água
 * 
 * @author Luciano Galvão
 * @date 18/09/2012
 */
public class ConsultarHistoricoManutencaoLigacaoAguaAction
				extends GcomAction {

	private Fachada fachada = Fachada.getInstancia();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarHistoricoManutencaoLigacaoAgua");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Captura o Form
		FiltrarHistoricoManutencaoLigacaoAguaActionForm form = (FiltrarHistoricoManutencaoLigacaoAguaActionForm) actionForm;

		// Construindo o Helper para realização da consulta
		ConsultarHistoricoManutencaoLigacaoHelper helper = new ConsultarHistoricoManutencaoLigacaoHelper();

		if(!Util.isVazioOuBranco(form.getImovel())){
			helper.setImovelId(obterInteger(form.getImovel(), "Imóvel"));
		}
		if(!Util.isVazioOuBranco(form.getLocalidadeInicial())){
			helper.setLocalidadeIdInicial(obterInteger(form.getLocalidadeInicial(), "Localidade Inicial"));
		}
		if(!Util.isVazioOuBranco(form.getLocalidadeFinal())){
			helper.setLocalidadeIdFinal(obterInteger(form.getLocalidadeFinal(), "Localidade Final"));
		}
		if(!Util.isVazioOuBranco(form.getSetorComercialInicial())){
			helper.setSetorComercialIdInicial(obterInteger(form.getSetorComercialInicial(), "Setor Comercial Inicial"));
		}
		if(!Util.isVazioOuBranco(form.getSetorComercialFinal())){
			helper.setSetorComercialIdFinal(obterInteger(form.getSetorComercialFinal(), "Setor Comercial Final"));
		}

		Collection<Integer> perfisImovel = getItensSelecionados(form.getPerfisImovel(), "Perfil do Imóvel");
		if(!Util.isVazioOrNulo(perfisImovel)){
			helper.setPerfisImovel(perfisImovel);
		}
		Collection<Integer> formasEmissao = getItensSelecionados(form.getFormasEmissao(), "Forma de Emissão");
		if(!Util.isVazioOrNulo(formasEmissao)){
			helper.setFormasEmissao(formasEmissao);
		}
		Collection<Integer> tiposDocumento = getItensSelecionados(form.getTiposDocumento(), "Tipo de Documento");
		if(!Util.isVazioOrNulo(tiposDocumento)){
			helper.setTiposDocumento(tiposDocumento);
		}
		Collection<Integer> tiposServico = getItensSelecionados(form.getTiposServico(), "Tipo de Serviço");
		if(!Util.isVazioOrNulo(tiposServico)){
			helper.setTiposServico(tiposServico);
		}
		if(!Util.isVazioOuBranco(form.getValorDebitoInicial())){
			try{
				helper.setValorDebitoInicial(Util.formatarStringParaBigDecimal(form.getValorDebitoInicial(), 2, false));
			}catch(NumberFormatException e){
				throw new ActionServletException("Valor de Débito Inicial não é um número válido");
			}
		}
		if(!Util.isVazioOuBranco(form.getValorDebitoFinal())){
			try{
				helper.setValorDebitoFinal(Util.formatarStringParaBigDecimal(form.getValorDebitoFinal(), 2, false));
			}catch(NumberFormatException e){
				throw new ActionServletException("Valor de Débito Inicial não é um número válido");
			}
		}
		if(!Util.isVazioOuBranco(form.getPeriodoEmissaoInicial())){
			helper.setPeriodoEmissaoInicial(Util.formatarDataInicial(Util.converteStringParaDate(form.getPeriodoEmissaoInicial())));
		}
		if(!Util.isVazioOuBranco(form.getPeriodoEmissaoFinal())){
			helper.setPeriodoEmissaoFinal(Util.formatarDataFinal(Util.converteStringParaDate(form.getPeriodoEmissaoFinal())));
		}


		// Aciona o controle de paginação para que sejam pesquisados apenas
		// os registros que aparecem na página
		Integer totalRegistros = fachada.consultarTotalRegistrosHistoricoManutencaoLigacao(helper);

		if(totalRegistros == null || totalRegistros == 0){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// Consulta o histórico da manutenção da ligação de água
		Collection<HistoricoManutencaoLigacaoHelper> colecaoHistoricoManutencaoLigacao = fachada.consultarHistoricoManutencaoLigacao(
						helper, (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		sessao.setAttribute("colecaoHistoricoManutencaoLigacao", colecaoHistoricoManutencaoLigacao);

		return retorno;
	}

	private Integer obterInteger(String valor, String entidade){

		try{
			return Integer.parseInt(valor);
		}catch(NumberFormatException e){
			throw new ActionServletException(entidade + " não é um número válido");
		}
	}

	private Collection<Integer> getItensSelecionados(String[] colecao, String entidade){

		Collection<Integer> resultado = new ArrayList<Integer>();

		if(!Util.isVazioOrNulo(colecao)){
			for(int i = 0; i < colecao.length; i++){
				if(colecao[i] != null && !colecao[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					resultado.add(obterInteger(colecao[i], entidade));
				}
			}
		}

		return resultado;

	}

}