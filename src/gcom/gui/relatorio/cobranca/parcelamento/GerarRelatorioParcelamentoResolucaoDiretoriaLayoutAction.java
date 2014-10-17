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
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [UC0252] Consultar Parcelamentos de D�bitos
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
	 * @author Cinthya
	 * @date 19/10/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
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

		// IN�CIO :: Relat�rio de Cobran�a Administrativa
		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = (ParcelamentoTermoTestemunhas) sessao
						.getAttribute("parcelamentoTermoTestemunhas");
		sessao.removeAttribute("parcelamentoTermoTestemunhas");

		// Caso a vari�vel parcelamentoTermoTestemunhas diferente de NULL significa que � um
		// Parcelamento de Cobran�a Administrativa e j� tem os dados das testemunhas para emiss�o do
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
						// 13.2.2. Caso seja parcelamento de cobran�a administrativa

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
		// FIM :: Relat�rio de Cobran�a Administrativa

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
