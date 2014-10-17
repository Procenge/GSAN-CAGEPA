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

package gcom.gui.relatorio.cobranca.parcelamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoTermoTestemunhas;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioParcelamentoException;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioResolucaoDiretoria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author Cinthya
	 * @date 19/10/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		String idParcelamento = (String) sessao.getAttribute("idParcelamento");
		boolean abrirTelaTestemunhas = false;

		String indicadorParcelamentoCobrancaBancaria = httpServletRequest.getParameter("indicadorParcelamentoCobrancaBancaria");
		String consultaParcDebitos = httpServletRequest.getParameter("consultaParcDebitos");

		String[] idsParcelamento = null;

		if(idParcelamento != null){
			idsParcelamento = idParcelamento.split("\\$");
		}

		Collection<Integer> colecaoIdsParcelamento = new ArrayList<Integer>();

		for(String idParc : idsParcelamento){
			colecaoIdsParcelamento.add(Integer.valueOf(idParc));
		}

		// INÍCIO :: Relatório de Cobrança Administrativa
		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = (ParcelamentoTermoTestemunhas) sessao
						.getAttribute("parcelamentoTermoTestemunhas");
		sessao.removeAttribute("parcelamentoTermoTestemunhas");

		// Caso a variável parcelamentoTermoTestemunhas diferente de NULL significa que é um
		// Parcelamento de Cobrança Administrativa e já tem os dados das testemunhas para emissão do
		// Termo
		if(colecaoIdsParcelamento.size() == 1 && parcelamentoTermoTestemunhas == null){

			FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria.resolucaoDiretoriaLayout");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
			filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, colecaoIdsParcelamento.iterator().next()));

			List<Parcelamento> listaParcelamento = (List<Parcelamento>) fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());

			if(!Util.isVazioOrNulo(listaParcelamento)){

				int caracteristicaParcelamento = fachada.obterCaracteristicaParcelamento(listaParcelamento.iterator().next());

				switch(caracteristicaParcelamento){
					case 2:
						// 13.2.2. Caso seja parcelamento de cobrança administrativa

						try{
							ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = fachada
											.obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa();

							// Caso o termo do parcelamento exija a assinatura de testemunhas
							if(resolucaoDiretoriaLayout != null
											&& resolucaoDiretoriaLayout.getIndicadorSolicitaTestemunhas() != null
											&& resolucaoDiretoriaLayout.getIndicadorSolicitaTestemunhas().intValue() == ConstantesSistema.SIM
															.intValue()){

								// Solicitar dados das testemunhas do parcelamento
								abrirTelaTestemunhas = true;
								retorno = actionMapping.findForward("exibirParcelamentoTermoTestemunhasAction");
							}

						}catch(GeradorRelatorioParcelamentoException e){
							throw new ActionServletException("erro.sistema", e);
						}

						break;

					default:
						break;
				}
			}
		}
		// FIM :: Relatório de Cobrança Administrativa

		if(!abrirTelaTestemunhas){
			String nomeRelatorio = null;

			if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){
				nomeRelatorio = ConstantesRelatorios.RELATORIO_PARCELAMENTO_CAER;
			}else{
				nomeRelatorio = ConstantesRelatorios.RELATORIO_PARCELAMENTO;
			}

			TarefaRelatorio tarefaRelatorio = new GeradorRelatorioResolucaoDiretoria(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"), nomeRelatorio);
			tarefaRelatorio.addParametro("colecaoIdsParcelamento", colecaoIdsParcelamento);
			tarefaRelatorio.addParametro("colecaoContaValores", sessao.getAttribute("colecaoContaValores"));
			tarefaRelatorio.addParametro("indicadorParcelamentoCobrancaBancaria", indicadorParcelamentoCobrancaBancaria);
			tarefaRelatorio.addParametro("consultarParcelamentoDebitos", consultaParcDebitos != null ? Boolean.TRUE : Boolean.FALSE);
			tarefaRelatorio.addParametro("parcelamentoTermoTestemunhas", parcelamentoTermoTestemunhas);

			retorno = processarExibicaoRelatorio(tarefaRelatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest, httpServletResponse,
							actionMapping);
		}
		return retorno;
	}
}
