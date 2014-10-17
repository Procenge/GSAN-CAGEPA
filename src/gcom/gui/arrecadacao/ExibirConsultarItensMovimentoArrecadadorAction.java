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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.bean.ArrecadadorMovimentoItemHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio os itens do movimento do arrecadador
 * 
 * @author Raphael Rossiter
 * @date 20/03/2006
 */
public class ExibirConsultarItensMovimentoArrecadadorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarItensMovimentoArrecadador");

		HttpSession sessao = httpServletRequest.getSession(true);

		PesquisarItensMovimentoArrecadadorActionForm pesquisarItensMovimentoArrecadadorActionForm = (PesquisarItensMovimentoArrecadadorActionForm) actionForm;

		String idArrecadadorMovimento = pesquisarItensMovimentoArrecadadorActionForm.getIdArrecadadorMovimento();

		Fachada fachada = Fachada.getInstancia();

		ArrecadadorMovimento arrecadadorMovimento = new ArrecadadorMovimento();
		arrecadadorMovimento.setId(Integer.valueOf(idArrecadadorMovimento));

		Integer idImovel = null;
		String retornoImovel = "";
		if(pesquisarItensMovimentoArrecadadorActionForm.getMatriculaImovel() != null
						&& !pesquisarItensMovimentoArrecadadorActionForm.getMatriculaImovel().equals("")){
			idImovel = Integer.valueOf(pesquisarItensMovimentoArrecadadorActionForm.getMatriculaImovel());

			retornoImovel = fachada.pesquisarInscricaoImovel(idImovel, true);
			if(retornoImovel == null || retornoImovel.equalsIgnoreCase("")){
				throw new ActionServletException("atencao.imovel.inexistente");
			}
		}else{
			sessao.removeAttribute("valorDadosMovimento");
			sessao.removeAttribute("valorDadosPagamento");
		}

		Short indicadorAceitacao = null;
		if(pesquisarItensMovimentoArrecadadorActionForm.getIndicadorAceitacao() != null
						&& !pesquisarItensMovimentoArrecadadorActionForm.getIndicadorAceitacao().equals("")
						&& !pesquisarItensMovimentoArrecadadorActionForm.getIndicadorAceitacao().equals("3")
						&& !pesquisarItensMovimentoArrecadadorActionForm.getIndicadorAceitacao().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			indicadorAceitacao = Short.valueOf(pesquisarItensMovimentoArrecadadorActionForm.getIndicadorAceitacao());
		}

		String descricaoOcorrencia = null;
		if(pesquisarItensMovimentoArrecadadorActionForm.getDescricaoOcorrencia() != null
						&& !pesquisarItensMovimentoArrecadadorActionForm.getDescricaoOcorrencia().equals("")
						&& !pesquisarItensMovimentoArrecadadorActionForm.getDescricaoOcorrencia().equals("3")
						&& !pesquisarItensMovimentoArrecadadorActionForm.getDescricaoOcorrencia().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			descricaoOcorrencia = pesquisarItensMovimentoArrecadadorActionForm.getDescricaoOcorrencia();
		}

		// ------------ Forma de Arrecadacao --------------- K�ssia Albuquerque

		String codigoArrecadacaoForma = null;
		if(pesquisarItensMovimentoArrecadadorActionForm.getFormaArrecadacao() != null
						&& !pesquisarItensMovimentoArrecadadorActionForm.getFormaArrecadacao().equals("")
						&& !pesquisarItensMovimentoArrecadadorActionForm.getFormaArrecadacao().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();

			filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(FiltroArrecadacaoForma.CODIGO,
							pesquisarItensMovimentoArrecadadorActionForm.getFormaArrecadacao()));

			Collection<ArrecadacaoForma> colecaoArrecadacaoForma = fachada.pesquisar(filtroArrecadacaoForma, ArrecadacaoForma.class
							.getName());

			if(colecaoArrecadacaoForma != null && !colecaoArrecadacaoForma.isEmpty()){

				ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) colecaoArrecadacaoForma.iterator().next();
				codigoArrecadacaoForma = arrecadacaoForma.getCodigoArrecadacaoForma();
			}
		}

		Collection colecaoArrecadadorMovimentoItemHelper = null;

		try{

			colecaoArrecadadorMovimentoItemHelper = fachada.consultarItensMovimentoArrecadador(arrecadadorMovimento, idImovel,
							indicadorAceitacao, descricaoOcorrencia);
		}catch(Exception e){

			if(e.getCause() instanceof StringIndexOutOfBoundsException){

				throw new ActionServletException("atencao.arquivo.movimento.conteudo.item.incompleto");
			}
		}

		// Este trecho de c�digo foi comentado pois altera o Tipo de Pagamento do objeto Helper
		// retornado para a Interface. Este atributo Tipo de Pagamento j� � setado no Controlador e
		// n�o precisa ser alterado neste ponto. A pedido de Yara, estou mantendo este c�digo
		// comentado, para poss�vel avalia��o em breve.
		//
		// @author Luciano Galv�o
		// @date 02/10/2012
		// ======================================================================================
		//
		// consulta para listar os pagamentos com id do arrecadador
		// Collection<Pagamento> colecaoPagamentos =
		// fachada.pesquisarPagamentoMovimentoArrecadador(null, null, null, null, null, null,
		// arrecadadorMovimento.getId().toString(), null, null, null, null, null, null, null, null,
		// null, null);
		//
		// // consulta para listar os pagamentosHistorico com id do arrecadador
		// Collection<PagamentoHistorico> colecaoPagamentosHistorico =
		// fachada.pesquisarPagamentoHistoricoMovimentoArrecadador(null, null,
		// null, null, null, null, arrecadadorMovimento.getId().toString(), null, null, null, null,
		// null, null, null, null,
		// null, null);
		//
		// // estrutura para setar o tipo do pagamento correspondente ao arrecadador no helper
		// if(colecaoArrecadadorMovimentoItemHelper != null &&
		// !colecaoArrecadadorMovimentoItemHelper.isEmpty()){
		// ArrecadadorMovimentoItemHelper helper = null;
		// Iterator colecaoArrecadadorMovimentoItemHelperIterator =
		// colecaoArrecadadorMovimentoItemHelper.iterator();
		//
		// while(colecaoArrecadadorMovimentoItemHelperIterator.hasNext()){
		// helper = (ArrecadadorMovimentoItemHelper)
		// colecaoArrecadadorMovimentoItemHelperIterator.next();
		// if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){
		// boolean achouPagamento = false;
		//
		// for(Pagamento pagamento : colecaoPagamentos){
		// if(helper.getId().equals(pagamento.getArrecadadorMovimentoItem().getId())){
		// helper.setTipoPagamento(pagamento.getDocumentoTipo().getDescricaoDocumentoTipo());
		// achouPagamento = true;
		// break;
		// }
		// }
		// if(!achouPagamento){
		// if(colecaoPagamentosHistorico != null && !colecaoPagamentosHistorico.isEmpty()){
		// for(PagamentoHistorico pagamentoHistorico : colecaoPagamentosHistorico){
		// if(helper.getId().equals(pagamentoHistorico.getArrecadadorMovimentoItem().getId())){
		// helper.setTipoPagamento(pagamentoHistorico.getDocumentoTipo().getDescricaoDocumentoTipo());
		// achouPagamento = false;
		// break;
		// }
		// }
		// }
		// }
		// }
		// }
		// }
		// ------------- Agrupando os valores Totais do Movimento e do Pagamento ------- K�ssia
		// Albuquerque

		Boolean indicadorAjuste = false;

		ArrecadadorMovimentoItemHelper helper = null;
		BigDecimal valorDadosMovimento = new BigDecimal("0.00");
		BigDecimal valorDadosPagamento = new BigDecimal("0.00");

		if(colecaoArrecadadorMovimentoItemHelper != null && !colecaoArrecadadorMovimentoItemHelper.isEmpty()){

			Iterator colecaoArrecadadorMovimentoItemHelperIterator = colecaoArrecadadorMovimentoItemHelper.iterator();
			// percorre a colecao de debito a cobrar somando o valor para obter um valor total
			while(colecaoArrecadadorMovimentoItemHelperIterator.hasNext()){

				helper = (ArrecadadorMovimentoItemHelper) colecaoArrecadadorMovimentoItemHelperIterator.next();
				if((indicadorAceitacao == null || indicadorAceitacao != 1) && indicadorAjuste != true){
					if(helper.getNvDescricaoOcorrencia() != null){
						indicadorAjuste = true;
					}
				}
				if(helper.getVlMovimento() != null && !helper.getVlMovimento().equals("")){

					valorDadosMovimento = valorDadosMovimento.add(Util.formatarMoedaRealparaBigDecimal(helper.getVlMovimento()));
				}

				if(helper.getVlPagamento() != null && !helper.getVlPagamento().equals("")){

					valorDadosPagamento = valorDadosPagamento.add(Util.formatarMoedaRealparaBigDecimal(helper.getVlPagamento()));
				}

			}
			sessao.setAttribute("valorDadosMovimento", Util.formatarMoedaReal(valorDadosMovimento));
			sessao.setAttribute("valorDadosPagamento", Util.formatarMoedaReal(valorDadosPagamento));
			sessao.setAttribute("indicadorAjuste", indicadorAjuste);
		}

		httpServletRequest.setAttribute("colecaoArrecadadorMovimentoItemHelper", colecaoArrecadadorMovimentoItemHelper);

		return retorno;
	}

}
