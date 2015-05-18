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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.bean.ImovelEmissaoOrdensSeletivaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.ImovelEmissaoOrdensSeletivasActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletiva;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletivaSugestao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarEmissaoOrdensSeletivasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @author Ivan Sérgio
	 * @created 06/11/2007
	 *          <<Descrição do método>>
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Recupera a Sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper = imovelEmissaoOrdensSeletivas
						.obterImovelEmissaoOrdensSeletivaHelper();

		Usuario usuario = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		// Verifica se o usuario selecionou simulação
		if(!imovelEmissaoOrdensSeletivas.getSimulacao().equals("1")){
			RelatorioEmitirOrdemServicoSeletiva relatorioEmitirOrdemServicoSeletiva = new RelatorioEmitirOrdemServicoSeletiva(usuario);

			// Fim da parte que vai mandar os parametros para o relatório

			int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

			try{
				// PDF = 1 e TXT = 2
				String tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();

				if(tipoArquivo.equals(ConstantesSistema.TXT)){
					tipoRelatorio = TarefaRelatorio.TIPO_ZIP;
				}
			}catch(ControladorException ex){
				throw new ActionServletException(ex.getMessage(), ex);
			}

			Object[] retornoRelatorio = fachada.gerarRelatorioOrdemSeletiva(imovelEmissaoOrdensSeletivaHelper, usuario);
			relatorioEmitirOrdemServicoSeletiva = (RelatorioEmitirOrdemServicoSeletiva) retornoRelatorio[0];

			try{
				retorno = this.processarExibicaoRelatorioOrdemSeletiva(relatorioEmitirOrdemServicoSeletiva, tipoRelatorio,
								httpServletRequest, httpServletResponse, actionMapping);

			}catch(TarefaException e){
				throw new ActionServletException(e.getMessage(), "exibirFiltrarImovelEmissaoOrdensSeletivas.do?menu=sim", e, "");
			}catch(Exception e2){
				throw new ActionServletException(
								e2.getMessage(),
								"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
								e2);
			}
		}else{
			RelatorioEmitirOrdemServicoSeletivaSugestao relatorioEmitirOrdemServicoSeletivaSugestao = new RelatorioEmitirOrdemServicoSeletivaSugestao(
							usuario);

			Object[] retornoRelatorio = fachada.gerarRelatorioOrdemSeletiva(imovelEmissaoOrdensSeletivaHelper, usuario);
			relatorioEmitirOrdemServicoSeletivaSugestao = (RelatorioEmitirOrdemServicoSeletivaSugestao) retornoRelatorio[1];

			// Fim da parte que vai mandar os parametros para o relatório
			String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

			tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_PDF);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

			if(!relatorioEmitirOrdemServicoSeletivaSugestao.existeDados()){
				throw new ActionServletException("atencao.nenhum.imovel.encontrado");
			}

			try{

				retorno = this.processarExibicaoRelatorio(relatorioEmitirOrdemServicoSeletivaSugestao, tipoRelatorio, httpServletRequest,
								httpServletResponse, actionMapping);

			}catch(TarefaException e){
				throw new ActionServletException(
								e.getMessage(),
								"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
								e);
			}catch(Exception e2){
				throw new ActionServletException(e2.getMessage(), e2);
			}

		}

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}