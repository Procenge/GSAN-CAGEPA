/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.gui.operacional.cadastro.FiltrarRelacaoImoveisComMudancaDaQuadraActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.imovel.RelatorioRelacaoImoveisComMudancaDaQuadra;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * [UC0074] Alterar Inscrição de Imóvel
 * 
 * @author Carlos Chrystian
 * @date 02/02/2012
 */
public class GerarRelatorioRelacaoImoveisComMudancaDaQuadraAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm form = (DynaValidatorForm) sessao.getAttribute("AlterarImovelInscricaoActionForm");

		FiltrarRelacaoImoveisComMudancaDaQuadraActionForm filtroForm = new FiltrarRelacaoImoveisComMudancaDaQuadraActionForm();

		// Incluir os parâmetros passados pelo form no filtro
		filtroForm.setLocalidadeDestinoID((String) form.get("localidadeDestinoID"));
		filtroForm.setLocalidadeOrigemID((String) form.get("localidadeOrigemID"));
		filtroForm.setSetorComercialDestinoCD((String) form.get("setorComercialDestinoCD"));
		filtroForm.setSetorComercialOrigemCD((String) form.get("setorComercialOrigemCD"));
		filtroForm.setSetorComercialDestinoID((String) form.get("setorComercialDestinoID"));
		filtroForm.setSetorComercialOrigemID((String) form.get("setorComercialOrigemID"));
		filtroForm.setQuadraDestinoNM((String) form.get("quadraDestinoNM"));
		filtroForm.setQuadraOrigemNM((String) form.get("quadraOrigemNM"));
		filtroForm.setLoteDestino((String) form.get("loteDestino"));
		filtroForm.setLoteOrigem((String) form.get("loteOrigem"));
		filtroForm.setTotalImovelMesmaRota((String) form.get("totalImovelMesmaRota"));
		filtroForm.setTotalImovelRotaDiferente((String) form.get("totalImovelRotaDiferente"));

		// Recupera o valor do indicador para ser repassado como parâmetro para o relatório
		String indicadorAlteracaoRota = (String) form.get("indicadorAlteracaoRota");

		// seta os parametros que serão mostrados no relatório
		Imovel imovelParametros = new Imovel();
		imovelParametros.setLocalidade(new Localidade());
		imovelParametros.setSetorComercial(new SetorComercial());
		imovelParametros.setQuadra(new Quadra());

		if(!Util.isVazioOuBranco(filtroForm.getLocalidadeDestinoID())){
			imovelParametros.getLocalidade().setId(Integer.valueOf(filtroForm.getLocalidadeDestinoID()));
		}else{
			if(!Util.isVazioOuBranco(filtroForm.getLocalidadeOrigemID())){
				imovelParametros.getLocalidade().setId(Integer.valueOf(filtroForm.getLocalidadeOrigemID()));
			}
		}

		if(!Util.isVazioOuBranco(filtroForm.getSetorComercialDestinoCD())){
			imovelParametros.getSetorComercial().setCodigo(Integer.valueOf(filtroForm.getSetorComercialDestinoCD()).intValue());
		}else{
			if(!Util.isVazioOuBranco(filtroForm.getSetorComercialOrigemCD())){
				imovelParametros.getSetorComercial().setCodigo(Integer.valueOf(filtroForm.getSetorComercialOrigemCD()).intValue());
			}
		}

		if(!Util.isVazioOuBranco(filtroForm.getSetorComercialDestinoID())){
			imovelParametros.getSetorComercial().setId(Integer.valueOf(filtroForm.getSetorComercialDestinoID()).intValue());
		}else{
			if(!Util.isVazioOuBranco(filtroForm.getSetorComercialOrigemID())){
				imovelParametros.getSetorComercial().setId(Integer.valueOf(filtroForm.getSetorComercialOrigemID()).intValue());
			}
		}

		if(!Util.isVazioOuBranco(filtroForm.getQuadraDestinoNM())){
			imovelParametros.getQuadra().setNumeroQuadra(Integer.valueOf(filtroForm.getQuadraDestinoNM()).intValue());
		}else{
			if(!Util.isVazioOuBranco(filtroForm.getQuadraOrigemNM())){
				imovelParametros.getQuadra().setNumeroQuadra(Integer.valueOf(filtroForm.getQuadraOrigemNM()).intValue());
			}
		}

		if(!Util.isVazioOuBranco(filtroForm.getLoteDestino())){
			imovelParametros.setLote(Short.valueOf(filtroForm.getLoteDestino()).shortValue());
		}else{
			if(!Util.isVazioOuBranco(filtroForm.getLoteOrigem())){
				imovelParametros.setLote(Short.valueOf(filtroForm.getLoteOrigem()).shortValue());
			}
		}

		// cria uma instância da classe do relatório
		RelatorioRelacaoImoveisComMudancaDaQuadra relatorioRelacaoImoveisComMudancaDaQuadra = new RelatorioRelacaoImoveisComMudancaDaQuadra(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		// Repassa os parâmetros para o relatório
		relatorioRelacaoImoveisComMudancaDaQuadra.addParametro("imovelParametros", imovelParametros);

		relatorioRelacaoImoveisComMudancaDaQuadra.addParametro("indicadorAlteracaoRota", indicadorAlteracaoRota);

		if(!Util.isVazioOuBranco(filtroForm.getTotalImovelMesmaRota())){
			relatorioRelacaoImoveisComMudancaDaQuadra.addParametro("totalImovelMesmaRotaAntesAlteracao", filtroForm
							.getTotalImovelMesmaRota());
		}

		if(!Util.isVazioOuBranco(filtroForm.getTotalImovelRotaDiferente())){
			relatorioRelacaoImoveisComMudancaDaQuadra.addParametro("totalImovelRotaDiferenteAntesAlteracao", filtroForm
							.getTotalImovelRotaDiferente());
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioRelacaoImoveisComMudancaDaQuadra.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		// Processa o relatório
		try{
			retorno = processarExibicaoRelatorio(relatorioRelacaoImoveisComMudancaDaQuadra, tipoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// retorna para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// retorna para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}
}