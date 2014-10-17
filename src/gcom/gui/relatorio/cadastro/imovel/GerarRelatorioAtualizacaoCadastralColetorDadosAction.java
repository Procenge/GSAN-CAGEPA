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

package gcom.gui.relatorio.cadastro.imovel;

import gcom.gui.ActionServletException;
import gcom.gui.cadastro.imovel.FiltrarAtualizacaoCadastralColetorDadosActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.RelatorioAtualizacaoCadastralColetorDados;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3113] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 10/10/2013
 */
public class GerarRelatorioAtualizacaoCadastralColetorDadosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descrição do método>>
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
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Valida os parâmetro passados como consulta
		ActionForward retorno = null;

		FiltrarAtualizacaoCadastralColetorDadosActionForm filtrarAtualizacaoCadastralColetorDadosActionForm = (FiltrarAtualizacaoCadastralColetorDadosActionForm) actionForm;
		// Recupera os parâmetros do form
		Integer referenciaInicial = new Integer(Util.formatarMesAnoComBarraParaAnoMes(filtrarAtualizacaoCadastralColetorDadosActionForm
						.getReferenciaInicial()));
		Integer referenciaFinal = new Integer(Util.formatarMesAnoComBarraParaAnoMes(filtrarAtualizacaoCadastralColetorDadosActionForm
						.getReferenciaFinal()));
		Integer matricula = null;
		Integer localidade = null;
		Integer setorComercial = null;
		Integer rota = null;
		if(!filtrarAtualizacaoCadastralColetorDadosActionForm.getMatriculaImovel().equals("")){
			matricula = new Integer(filtrarAtualizacaoCadastralColetorDadosActionForm.getMatriculaImovel());
		}else{
			if(!filtrarAtualizacaoCadastralColetorDadosActionForm.getLocalidade().equals("")){
				localidade = new Integer(filtrarAtualizacaoCadastralColetorDadosActionForm.getLocalidade());
				if(!filtrarAtualizacaoCadastralColetorDadosActionForm.getSetorComercial().equals("")){
					setorComercial = new Integer(filtrarAtualizacaoCadastralColetorDadosActionForm.getSetorComercial());
				}
				if(!filtrarAtualizacaoCadastralColetorDadosActionForm.getRota().equals("")){
					rota = new Integer(filtrarAtualizacaoCadastralColetorDadosActionForm.getRota());
				}
			}else{
				throw new ActionServletException("atencao.naoinformado", "Matricula ou Localidade");
			}
		}

		// Fim da parte que vai mandar os parametros para o relatório

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioAtualizacaoCadastralColetorDados relatorioAtualizacaoCadastralColetorDados = new RelatorioAtualizacaoCadastralColetorDados(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioAtualizacaoCadastralColetorDados.addParametro("referenciaInicial", referenciaInicial);
		relatorioAtualizacaoCadastralColetorDados.addParametro("referenciaFinal", referenciaFinal);
		relatorioAtualizacaoCadastralColetorDados.addParametro("matricula", matricula);
		relatorioAtualizacaoCadastralColetorDados.addParametro("localidade", localidade);
		relatorioAtualizacaoCadastralColetorDados.addParametro("setorComercial", setorComercial);
		relatorioAtualizacaoCadastralColetorDados.addParametro("rota", rota);
			if(tipoRelatorio == null){
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

		relatorioAtualizacaoCadastralColetorDados.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorioAtualizacaoCadastralColetorDados, tipoRelatorio, httpServletRequest,
						httpServletResponse,
							actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}
