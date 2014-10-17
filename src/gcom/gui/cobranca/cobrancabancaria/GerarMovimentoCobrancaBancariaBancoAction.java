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

package gcom.gui.cobranca.cobrancabancaria;

import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.FiltroArrecadadorMovimento;
import gcom.arrecadacao.banco.Banco;
import gcom.cobranca.BoletoBancarioMovimentacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3020]-Gerar Movimento Cob. Banc�ria para Envio ao Banco.
 * Processamento para gerar movimento de cobran�a banc�ria para o banco
 * 
 * @author Yara Souza
 * @date 16/10/2011
 */
public class GerarMovimentoCobrancaBancariaBancoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		GerarMovimentoCobrancaBancariaBancoActionForm gerarMovimentoCobrancaBancariaBancoActionForm = (GerarMovimentoCobrancaBancariaBancoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoMovimentos = null;

		// caso a op��o do movimento de cobran�a banc�ria seja diferente de
		// nulo
		if(gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria() != null
						&& !gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria().equals("")){

			// [SB0001] - Gerar Movimento para Cobran�a Banc�ria
			// Se a op��o seja Gerar Movimento para Cobran�a Banc�ria

			if(gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria().equals(ConstantesSistema.GERAR_MOVIMENTO)){

				// recupera o Map<Banco, Collection<DebitoAutomaticoMovimento>>
				// da sessao
				Map<Banco, Collection> boletoBancarioMovimentacaoBancosMap = (Map<Banco, Collection>) sessao
								.getAttribute("boletoBancarioMovimentacaoBancosMap");

				String[] idsBancos = gerarMovimentoCobrancaBancariaBancoActionForm.getIdsCodigosBancos();
				// cria um Map<Banco, Collection<DebitoAutomaticoMovimento>>
				// novo para inserir s� os que foram escolhidos
				if(boletoBancarioMovimentacaoBancosMap != null && boletoBancarioMovimentacaoBancosMap.size() != idsBancos.length){
					Map<Banco, Collection> boletoBancarioMovimentacaoBancosMapNovo = new HashMap();

					for(int i = 0; i < idsBancos.length; i++){
						Integer idBanco = new Integer(idsBancos[i]);
						Iterator boletoBancarioMovimentacaoBancosIterator = boletoBancarioMovimentacaoBancosMap.keySet().iterator();
						while(boletoBancarioMovimentacaoBancosIterator.hasNext()){
							Banco banco = (Banco) boletoBancarioMovimentacaoBancosIterator.next();

							if(banco.getId().equals(idBanco)){
								Collection<BoletoBancarioMovimentacao> boletoBancarioMovimentacao = boletoBancarioMovimentacaoBancosMap
												.get(banco);
								boletoBancarioMovimentacaoBancosMapNovo.put(banco, boletoBancarioMovimentacao);
							}

						}
					}
					// manda os selecionados
					fachada.gerarMovimentoCobrancaBancaria(boletoBancarioMovimentacaoBancosMapNovo, (Usuario) (httpServletRequest
									.getSession(false)).getAttribute("usuarioLogado"));

				}else{
					// caso todos forem selecionados
					fachada.gerarMovimentoCobrancaBancaria(boletoBancarioMovimentacaoBancosMap, (Usuario) (httpServletRequest
									.getSession(false)).getAttribute("usuarioLogado"));
				}
			}else{
				// no caso da op��o ser Regerar Arquivo TXT do Movimento.

				String codigoRemessa = gerarMovimentoCobrancaBancariaBancoActionForm.getCodigoRemessaMovimento();
				String identificacaoServicoMovimento = gerarMovimentoCobrancaBancariaBancoActionForm.getIdentificacaoServicoMovimento();
				if(codigoRemessa.equals("1") && identificacaoServicoMovimento.equals(ConstantesSistema.COBRANCA_BANCARIA)){
					Integer idArrecadadorMovimento = new Integer(gerarMovimentoCobrancaBancariaBancoActionForm.getIdArrecadadorMovimento());
					FiltroArrecadadorMovimento filtroArrecadadorMovimento = new FiltroArrecadadorMovimento();
					filtroArrecadadorMovimento.adicionarParametro(new ParametroSimples(FiltroArrecadadorMovimento.ID,
									idArrecadadorMovimento));
					Collection colecaoArrecadadorMovimento = fachada.pesquisar(filtroArrecadadorMovimento, ArrecadadorMovimento.class
									.getName());
					if(colecaoArrecadadorMovimento != null && !colecaoArrecadadorMovimento.isEmpty()){
						ArrecadadorMovimento arrecadadorMovimento = (ArrecadadorMovimento) Util
										.retonarObjetoDeColecao(colecaoArrecadadorMovimento);
						String enviarBanco = gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoEnvioParaBanco();
						fachada.regerarArquivoTxtMovimentoCobrancaBancaria(arrecadadorMovimento, enviarBanco, (Usuario) (httpServletRequest
										.getSession(false)).getAttribute("usuarioLogado"));
					}
				}else{
					throw new ActionServletException("atencao.movimento.cobranca.bancaria.nao.envio");
				}

			}

		}
		gerarMovimentoCobrancaBancariaBancoActionForm.setDataAtual(new Date());
		sessao.removeAttribute("colecaoMovimentos");

		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Movimento Cobran�a Banc�ria Enviado para Processamento", "Voltar",
						"/exibirGerarMovimentoCobrancaBancariaBancoAction.do");

		return retorno;
	}
}
