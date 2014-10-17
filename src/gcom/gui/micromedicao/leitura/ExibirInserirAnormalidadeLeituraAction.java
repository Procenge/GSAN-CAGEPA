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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.micromedicao.leitura;

import gcom.atendimentopublico.ordemservico.FiltroTipoServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeLeitura;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Este cado de uso Permite Inserir uma Anormalidade de Leitura
 * 
 * @author Thiago Tenório
 * @date 07/02/2007
 * @author eduardo henrique [UC0190]
 * @date 16/06/2008
 *       Inclusão dos atributos: Mensagem de Leitura , Mensagem de manutenção, Mensagem de prevenção
 *       de acidentes
 *       Incidência da anormalidade para a geração da ordem de serviço,
 *       Emissão automática de documento, Indicador de que a ocorrência aceita leitura,
 *       Indicador de que a ocorrência deve ser listada nos relatórios de crítica/fiscalização de
 *       leitura
 *       Indicador de retenção de conta , Indicador de concessão de crédito de consumo
 *       Indicador de isenção de cobrança de água, Indicador de isenção de cobrança de esgoto
 */
public class ExibirInserirAnormalidadeLeituraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirAnormalidadeLeitura");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirAnormalidadeLeituraActionForm inserirAnormalidadeLeituraActionForm = (InserirAnormalidadeLeituraActionForm) actionForm;

		if((httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S"))){
			// -------------- bt DESFAZER ---------------

			// Limpando o formulario
			inserirAnormalidadeLeituraActionForm.setDescricao("");
			inserirAnormalidadeLeituraActionForm.setAbreviatura("");
			inserirAnormalidadeLeituraActionForm.setIndicadorRelativoHidrometro("");
			inserirAnormalidadeLeituraActionForm.setIndicadorImovelSemHidrometro("");
			inserirAnormalidadeLeituraActionForm.setUsoRestritoSistema("");
			inserirAnormalidadeLeituraActionForm.setPerdaTarifaSocial("");
			inserirAnormalidadeLeituraActionForm.setOsAutomatico("");
			inserirAnormalidadeLeituraActionForm.setTipoServico("");
			inserirAnormalidadeLeituraActionForm.setConsumoLeituraNaoInformado("");
			inserirAnormalidadeLeituraActionForm.setConsumoLeituraInformado("");
			inserirAnormalidadeLeituraActionForm.setLeituraLeituraInformado("");
			inserirAnormalidadeLeituraActionForm.setLeituraLeituraNaoturaInformado("");

			inserirAnormalidadeLeituraActionForm.setTipoDocumento("");
			inserirAnormalidadeLeituraActionForm.setNumeroIncidenciasGeracaoOS("");
			inserirAnormalidadeLeituraActionForm.setAceiteLeitura("");
			inserirAnormalidadeLeituraActionForm.setImpressaoRelatorioCriticaFiscalizacao("");
			inserirAnormalidadeLeituraActionForm.setIndicadorRetencaoConta("");
			inserirAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaAgua("");
			inserirAnormalidadeLeituraActionForm.setIndicadorIsencaoCobrancaEsgoto("");
			inserirAnormalidadeLeituraActionForm.setIndicadorConcessaoCreditoConsumo("");
			inserirAnormalidadeLeituraActionForm.setMensagemImpressaoConta("");
			inserirAnormalidadeLeituraActionForm.setSugestaoAgenteManutencao("");
			inserirAnormalidadeLeituraActionForm.setSugestaoAgentePrevencao("");
		}

		String limparForm = (String) httpServletRequest.getParameter("limparCampos");

		if(inserirAnormalidadeLeituraActionForm.getTipoServico() == null
						|| inserirAnormalidadeLeituraActionForm.getTipoServico().equals("")){
			Collection colecaoPesquisa = null;

			FiltroTipoServico filtroTipoServico = new FiltroTipoServico();

			filtroTipoServico.setCampoOrderBy(FiltroTipoServico.DESCRICAO);

			filtroTipoServico.adicionarParametro(new ParametroSimples(FiltroTipoServico.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Tipo Serviço
			colecaoPesquisa = fachada.pesquisar(filtroTipoServico, ServicoTipo.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela localidade_porte foi encontrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tipo de Servico");
			}else{
				sessao.setAttribute("colecaoTipoServico", colecaoPesquisa);
			}

		}

		// coleção anormalidade consumo

		FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
		filtroLeituraAnormalidadeConsumo.setCampoOrderBy(FiltroLeituraAnormalidadeConsumo.ID);

		Collection colecaoLeituraAnormalidadeConsumo = fachada.pesquisar(filtroLeituraAnormalidadeConsumo, LeituraAnormalidadeConsumo.class
						.getName());
		sessao.setAttribute("colecaoLeituraAnormalidadeConsumo", colecaoLeituraAnormalidadeConsumo);

		// coleção anormalidade leitura

		FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
		filtroLeituraAnormalidadeLeitura.setCampoOrderBy(FiltroLeituraAnormalidadeLeitura.ID);

		Collection colecaoLeituraAnormalidadeLeitura = fachada.pesquisar(filtroLeituraAnormalidadeLeitura, LeituraAnormalidadeLeitura.class
						.getName());
		sessao.setAttribute("colecaoLeituraAnormalidadeLeitura", colecaoLeituraAnormalidadeLeitura);

		// Coleção de Tipos de Documento
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.ID);

		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());
		sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);

		// devolve o mapeamento de retorno
		return retorno;
	}

}
