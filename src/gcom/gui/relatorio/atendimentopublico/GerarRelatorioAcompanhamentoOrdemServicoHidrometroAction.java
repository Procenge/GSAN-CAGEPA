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

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioRelacaoOrdensServicoEncerradasPendentes;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoEncerradasPendentes;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ivan Sérgio
 * @created 11/12/2007
 */
public class GerarRelatorioAcompanhamentoOrdemServicoHidrometroAction
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

		// cria a variável de retorno
		ActionForward retorno = null;

		GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm form = (GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm) actionForm;

		// Recupera os valores do form para serem passados como parâmetros para
		// o RelatorioAcompanhamentoOrdemServicoHidrometro
		String tipoOrdem = form.getTipoOrdem();
		String situacaoOrdemServico = form.getSituacaoOrdemServico();
		String firma = form.getFirma();
		String nomeFirma = form.getNomeFirma();
		String idLocalidade = form.getLocalidade();
		String nomeLocalidade = form.getNomeLocalidade();
		String dataEncerramentoInicial = form.getDataEncerramentoInicial();
		String dataEncerramentoFinal = form.getDataEncerramentoFinal();
		String tipoRelatorioAcompanhamento = form.gettipoRelatorioAcompanhamento();

		// Valida a Localidade Informada
		if(idLocalidade != null && !idLocalidade.equals("")){
			Localidade localidade = validarLocalidade(idLocalidade);

			if(nomeLocalidade == null || nomeLocalidade.equals("")){
				nomeLocalidade = localidade.getDescricao();
			}
		}

		String tipoGeracaoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		// Verifica o Tipo do Relatorio Escolhido pelo usuario
		// 1 - Analitico (RelatorioRelacaoOrdensServicoEncerradasPendentes)
		// 2 - Sintetico (RelatorioResumoOrdensServicoEncerradasPendentes)
		if(Util.converterStringParaInteger(tipoRelatorioAcompanhamento).equals(
						GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm.TIPO_RELATORIO_ANALITICO)){

			RelatorioRelacaoOrdensServicoEncerradasPendentes relatorio = new RelatorioRelacaoOrdensServicoEncerradasPendentes(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("tipoOrdem", tipoOrdem);
			relatorio.addParametro("situacaoOrdemServico", situacaoOrdemServico);
			relatorio.addParametro("firma", firma);
			relatorio.addParametro("nomeFirma", nomeFirma);
			relatorio.addParametro("idLocalidade", idLocalidade);
			relatorio.addParametro("nomeLocalidade", nomeLocalidade);
			relatorio.addParametro("dataEncerramentoInicial", dataEncerramentoInicial);
			relatorio.addParametro("dataEncerramentoFinal", dataEncerramentoFinal);
			relatorio.addParametro("tipoRelatorioAcompanhamento", tipoRelatorioAcompanhamento);

			if(tipoGeracaoRelatorio == null){
				tipoGeracaoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoGeracaoRelatorio));

			retorno = processarExibicaoRelatorio(relatorio, tipoGeracaoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		}else if(Util.converterStringParaInteger(tipoRelatorioAcompanhamento).equals(
						GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm.TIPO_RELATORIO_SINTETICO)){

			RelatorioResumoOrdensServicoEncerradasPendentes relatorio = new RelatorioResumoOrdensServicoEncerradasPendentes(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("tipoOrdem", tipoOrdem);
			relatorio.addParametro("situacaoOrdemServico", situacaoOrdemServico);
			relatorio.addParametro("firma", firma);
			relatorio.addParametro("nomeFirma", nomeFirma);
			relatorio.addParametro("idLocalidade", idLocalidade);
			relatorio.addParametro("nomeLocalidade", nomeLocalidade);
			relatorio.addParametro("dataEncerramentoInicial", dataEncerramentoInicial);
			relatorio.addParametro("dataEncerramentoFinal", dataEncerramentoFinal);
			relatorio.addParametro("tipoRelatorioAcompanhamento", tipoRelatorioAcompanhamento);

			if(tipoGeracaoRelatorio == null){
				tipoGeracaoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoGeracaoRelatorio));

			retorno = processarExibicaoRelatorio(relatorio, tipoGeracaoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
		}

		return retorno;
	}

	private Localidade validarLocalidade(String localidade){

		Localidade retorno = null;

		Fachada fachada = Fachada.getInstancia();

		FiltroLocalidade filtroLocalidade = null;
		Collection colecaoLocalidade = null;

		// Verifica se a Localidade existe
		if(localidade != null && !localidade.equals("")){
			filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				Iterator iColecaoLocalidade = colecaoLocalidade.iterator();
				retorno = (Localidade) iColecaoLocalidade.next();
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}
		}

		return retorno;
	}
}
