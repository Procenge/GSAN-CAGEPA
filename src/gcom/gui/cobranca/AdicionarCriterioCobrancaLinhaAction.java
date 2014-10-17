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

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.CobrancaCriterioLinha;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Processamento para adicionar a linha do criterio da cobran�a
 * 
 * @author S�vio Luiz
 * @date 02/05/2006
 *       Retirada dos campos 'Situa��o de Liga��o de �gua' e 'Campo Situa��o de Liga��o de Esgoto'.
 * @author Virg�nia Melo
 * @date 29/07/2009
 */
public class AdicionarCriterioCobrancaLinhaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("adicionarCriterioCobrancaLinha");

		CriterioCobrancaActionForm criterioCobrancaActionForm = (CriterioCobrancaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		// recupera o par imovel perfil que tem o id e a descricao do imovel perfil separado por ;
		String[] parImovelPerfil = criterioCobrancaActionForm.getParImovelPerfil();
		// String[] arrayImovelPerfil = parImovelPerfil.split(";");

		Collection colecaoCobrancaCriterioLinha = null;

		if(sessao.getAttribute("colecaoCobrancaCriterioLinha") != null && !sessao.getAttribute("colecaoCobrancaCriterioLinha").equals("")){
			colecaoCobrancaCriterioLinha = (Collection) sessao.getAttribute("colecaoCobrancaCriterioLinha");
		}else{
			colecaoCobrancaCriterioLinha = new ArrayList();
		}

		if(parImovelPerfil != null && !parImovelPerfil.equals("")){

			for(int i = 0; i < parImovelPerfil.length; i++){
				// cria o imovel perfil para ser inserido na cobranca criterio linha
				String[] arrayImovelPerfil = parImovelPerfil[i].split(";");
				ImovelPerfil imovelPerfil = new ImovelPerfil();
				imovelPerfil.setId(Integer.valueOf(arrayImovelPerfil[0]));
				imovelPerfil.setDescricao(arrayImovelPerfil[1]);

				// recupera o par categoria que tem o id e a descricao da categoria separado por ;
				String[] parCategoria = criterioCobrancaActionForm.getParCategoria();

				if(parCategoria != null && !parCategoria.equals("")){

					for(int j = 0; j < parCategoria.length; j++){
						// cria a categoria para ser inserido na cobranca criterio linha
						String[] arrayCategoria = parCategoria[j].split(";");
						Categoria categoria = new Categoria();
						categoria.setId(Integer.valueOf(arrayCategoria[0]));
						categoria.setDescricao(arrayCategoria[1]);

						// faz um iterator para verificar se existe na cole��o o imovel perfil
						// e a categoria que foram escolhidos
						Iterator iteratorCobrancaCriterioLinha = colecaoCobrancaCriterioLinha.iterator();
						while(iteratorCobrancaCriterioLinha.hasNext()){
							CobrancaCriterioLinha cobrancaCriterioLinha = (CobrancaCriterioLinha) iteratorCobrancaCriterioLinha.next();
							if(cobrancaCriterioLinha.getImovelPerfil().getId().equals(imovelPerfil.getId())
											&& cobrancaCriterioLinha.getCategoria().getId().equals(categoria.getId())){
								throw new ActionServletException("atencao.imovel.perfil.categoria.informados");
							}
						}

						// cria a cobranca criterio linha para ser exibido na
						// tela de inserir verifica se o valor maximo � menor
						// que o m�nimo
						BigDecimal valorDebitoMinimo = null;
						if(criterioCobrancaActionForm.getValorDebitoMinimo() != null
										&& !criterioCobrancaActionForm.getValorDebitoMinimo().equals("")){
							valorDebitoMinimo = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getValorDebitoMinimo());
						}
						BigDecimal valorDebitoMaximo = null;
						if(criterioCobrancaActionForm.getValorDebitoMaximo() != null
										&& !criterioCobrancaActionForm.getValorDebitoMaximo().equals("")){
							valorDebitoMaximo = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getValorDebitoMaximo());
						}
						if(valorDebitoMinimo != null && valorDebitoMaximo != null){
							if(valorDebitoMinimo.compareTo(valorDebitoMaximo) == 1){
								throw new ActionServletException("atencao.valor.maximo.debito.menor.valor.minimo.debito");
							}
						}
						CobrancaCriterioLinha cobrancaCriterioLinha = new CobrancaCriterioLinha();
						cobrancaCriterioLinha.setImovelPerfil(imovelPerfil);
						cobrancaCriterioLinha.setCategoria(categoria);
						cobrancaCriterioLinha.setValorMinimoDebito(valorDebitoMinimo);
						cobrancaCriterioLinha.setValorMaximoDebito(valorDebitoMaximo);

						Short qtdContasMinima = null;
						if(criterioCobrancaActionForm.getQtdContasMinima() != null
										&& !criterioCobrancaActionForm.getQtdContasMinima().equals("")){
							qtdContasMinima = Short.valueOf(criterioCobrancaActionForm.getQtdContasMinima());
						}
						Integer qtdContasMaxima = null;
						if(criterioCobrancaActionForm.getQtdContasMaxima() != null
										&& !criterioCobrancaActionForm.getQtdContasMaxima().equals("")){
							qtdContasMaxima = Integer.valueOf(criterioCobrancaActionForm.getQtdContasMaxima());
						}
						if(qtdContasMinima != null && qtdContasMaxima != null){
							if(qtdContasMinima > qtdContasMaxima){
								throw new ActionServletException("atencao.quantidade.maxima.contas.menor.quantidade.minima.contas");
							}
						}
						cobrancaCriterioLinha.setQuantidadeMinimaContas(qtdContasMinima);
						cobrancaCriterioLinha.setQuantidadeMaximaContas(qtdContasMaxima);

						if(criterioCobrancaActionForm.getVlMinimoDebitoCliente() != null
										&& !criterioCobrancaActionForm.getVlMinimoDebitoCliente().equals("")){
							cobrancaCriterioLinha.setValorMinimoDebitoDebitoAutomatico(Util
											.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getVlMinimoDebitoCliente()));
						}
						if(criterioCobrancaActionForm.getQtdMinContasCliente() != null
										&& !criterioCobrancaActionForm.getQtdMinContasCliente().equals("")){
							cobrancaCriterioLinha.setQuantidadeMinimaContasDebitoAutomatico(Short.valueOf(criterioCobrancaActionForm
											.getQtdMinContasCliente()));
						}
						if(criterioCobrancaActionForm.getVlMinimoContasMes() != null
										&& !criterioCobrancaActionForm.getVlMinimoContasMes().equals("")){
							cobrancaCriterioLinha.setValorMinimoContaMes(Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm
											.getVlMinimoContasMes()));
						}
						if(criterioCobrancaActionForm.getQuantidadeMinimaParcelasAtraso() != null
										&& !criterioCobrancaActionForm.getQuantidadeMinimaParcelasAtraso().equals("")){
							cobrancaCriterioLinha.setQuantidadeMinimaContasParcelamento(Short.valueOf(criterioCobrancaActionForm
											.getQuantidadeMinimaParcelasAtraso()));

						}else{
							cobrancaCriterioLinha.setQuantidadeMinimaContasParcelamento(Short.valueOf("0"));
						}

						// Novos campos adicionados - Campo Situa��o Medi��o
						String situacaoMedicao = criterioCobrancaActionForm.getSituacaoMedicao();
						if(situacaoMedicao != null && !situacaoMedicao.equals("") && !situacaoMedicao.equals("-1")){
							cobrancaCriterioLinha.setSituacaoMedicao(Short.valueOf(situacaoMedicao));
						}else{
							cobrancaCriterioLinha.setSituacaoMedicao(null);
						}

						// Campo Situa��o de Liga��o de �gua
						/*
						 * String situacaoLigacaoAguaID =
						 * criterioCobrancaActionForm.getStLigacaoAguaID();
						 * if (situacaoLigacaoAguaID != null && !situacaoLigacaoAguaID.equals("")) {
						 * LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
						 * ligacaoAguaSituacao.setId(Integer.parseInt(situacaoLigacaoAguaID));
						 * cobrancaCriterioLinha.setLigacaoAguaSituacao(ligacaoAguaSituacao);
						 * }
						 */

						// Campo Situa��o de Liga��o de Esgoto
						/*
						 * String situacaoLigacaoEsgotoID =
						 * criterioCobrancaActionForm.getStLigacaoEsgotoID();
						 * if (situacaoLigacaoEsgotoID != null &&
						 * !situacaoLigacaoEsgotoID.equals("")) {
						 * LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new
						 * LigacaoEsgotoSituacao();
						 * ligacaoEsgotoSituacao.setId(Integer.parseInt(situacaoLigacaoEsgotoID));
						 * cobrancaCriterioLinha.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
						 * }
						 */

						// Campo N�mero de Dias para data de vencimento (obrigat�rio)
						String diasParaVencimento = criterioCobrancaActionForm.getDiasParaVencimento();

						if(diasParaVencimento != null && !diasParaVencimento.equals("")){

							if(Util.validarStringNumerica(diasParaVencimento)){
								cobrancaCriterioLinha.setDiasParaVencimento(Integer.parseInt(diasParaVencimento));
							}else{
								throw new ActionServletException("atencao.numero_dias_vencimento_invalido");
							}

						}else{
							throw new ActionServletException("atencao.required", null, "N� de Dias para Vencimento");
						}

						// Campo Data de Liga��o Limite
						Date dataLimite = null;
						String strDataLimite = criterioCobrancaActionForm.getDataLimite();
						if(strDataLimite != null && !strDataLimite.equals("")){

							// [FS0008]
							if(Util.validarDiaMesAno(strDataLimite)){
								throw new ActionServletException("atencao.data.inicio.invalida");
							}
							dataLimite = Util.converteStringParaDate(strDataLimite);
							cobrancaCriterioLinha.setDataLimite(dataLimite);
						}

						String referenciaDebitoInicial = criterioCobrancaActionForm.getReferenciaDebitoInicial();
						String referenciaDebitoFinal = criterioCobrancaActionForm.getReferenciaDebitoFinal();

						// [FS0009] � Validar referenciaDebitoInicial
						if(referenciaDebitoInicial != null && !referenciaDebitoInicial.equals("")){

							if(!Util.validarMesAno(referenciaDebitoInicial)){
								throw new ActionServletException("atencao.periodoReferencia.invalido");
							}

							// Invertendo o formato para yyyyMM (sem a barra)
							referenciaDebitoInicial = Util.formatarMesAnoParaAnoMesSemBarra(referenciaDebitoInicial);
							cobrancaCriterioLinha.setReferenciaDebitoInicial(Integer.parseInt(referenciaDebitoInicial));

						}

						// [FS0009] � Validar referenciaDebitoFinal
						if(referenciaDebitoFinal != null && !referenciaDebitoFinal.equals("")){

							if(!Util.validarMesAno(referenciaDebitoFinal) || !Util.validarMesAno(referenciaDebitoFinal)){
								throw new ActionServletException("atencao.periodoReferencia.invalido");
							}

							referenciaDebitoFinal = Util.formatarMesAnoParaAnoMesSemBarra(referenciaDebitoFinal);
							// [FS0010] � Verificar refer�ncia final menor que refer�ncia inicial
							if(referenciaDebitoInicial != null && !referenciaDebitoInicial.equals("")){
								boolean referenciaFinalMenorInicial = Util.compararAnoMesReferencia(Integer.valueOf(referenciaDebitoFinal),
												Integer.valueOf(referenciaDebitoInicial), "<");

								if(referenciaFinalMenorInicial){
									throw new ActionServletException("atencao.periodo_ref_final.anterior.periodo_ref_inicial");
								}
								cobrancaCriterioLinha.setReferenciaDebitoFinal(Integer.parseInt(referenciaDebitoFinal));
							}

						}

						// quantidade dias vencimento
						Integer qtdDiasVencimentoInicial = null;
						if(criterioCobrancaActionForm.getQtdDiasVencMinima() != null
										&& !criterioCobrancaActionForm.getQtdDiasVencMinima().equals("")){
							qtdDiasVencimentoInicial = Integer.valueOf(criterioCobrancaActionForm.getQtdDiasVencMinima());
						}
						Integer qtdDiasVencimentoFinal = null;
						if(criterioCobrancaActionForm.getQtdDiasVencMaxima() != null
										&& !criterioCobrancaActionForm.getQtdDiasVencMaxima().equals("")){
							qtdDiasVencimentoFinal = Integer.valueOf(criterioCobrancaActionForm.getQtdDiasVencMaxima());
						}
						if(qtdDiasVencimentoInicial != null && qtdDiasVencimentoFinal != null){
							if(qtdDiasVencimentoInicial > qtdDiasVencimentoFinal){
								throw new ActionServletException(
												"atencao.quantidade.maxima.dias.vencimento.menor.quantidade.minima.dias.vencimento");
							}
						}
						cobrancaCriterioLinha.setQuantidadeDiasVencimentoInicial(qtdDiasVencimentoInicial);
						cobrancaCriterioLinha.setQuantidadeDiasVencimentoFinal(qtdDiasVencimentoFinal);

						cobrancaCriterioLinha.setUltimaAlteracao(new Date());
						colecaoCobrancaCriterioLinha.add(cobrancaCriterioLinha);
					}
				}else{
					throw new ActionServletException("atencao.informe_campo", null, "Categoria");
				}
			}
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Perfil do Im�vel");
		}

		sessao.setAttribute("colecaoCobrancaCriterioLinha", colecaoCobrancaCriterioLinha);
		httpServletRequest.setAttribute("fechaPopup", "true");

		return retorno;
	}
}
