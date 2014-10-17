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
 * [UC3020]-Gerar Movimento Cob. Bancária para Envio ao Banco.
 * Processamento para gerar movimento de cobrança bancária para o banco
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

		// caso a opção do movimento de cobrança bancária seja diferente de
		// nulo
		if(gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria() != null
						&& !gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria().equals("")){

			// [SB0001] - Gerar Movimento para Cobrança Bancária
			// Se a opção seja Gerar Movimento para Cobrança Bancária

			if(gerarMovimentoCobrancaBancariaBancoActionForm.getOpcaoMovimentoCobrancaBancaria().equals(ConstantesSistema.GERAR_MOVIMENTO)){

				// recupera o Map<Banco, Collection<DebitoAutomaticoMovimento>>
				// da sessao
				Map<Banco, Collection> boletoBancarioMovimentacaoBancosMap = (Map<Banco, Collection>) sessao
								.getAttribute("boletoBancarioMovimentacaoBancosMap");

				String[] idsBancos = gerarMovimentoCobrancaBancariaBancoActionForm.getIdsCodigosBancos();
				// cria um Map<Banco, Collection<DebitoAutomaticoMovimento>>
				// novo para inserir só os que foram escolhidos
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
				// no caso da opção ser Regerar Arquivo TXT do Movimento.

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

		// montando página de sucesso
		montarPaginaSucesso(httpServletRequest, "Movimento Cobrança Bancária Enviado para Processamento", "Voltar",
						"/exibirGerarMovimentoCobrancaBancariaBancoAction.do");

		return retorno;
	}
}
