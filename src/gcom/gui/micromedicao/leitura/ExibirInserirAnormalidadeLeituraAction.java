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
 * @author Thiago Ten�rio
 * @date 07/02/2007
 * @author eduardo henrique [UC0190]
 * @date 16/06/2008
 *       Inclus�o dos atributos: Mensagem de Leitura , Mensagem de manuten��o, Mensagem de preven��o
 *       de acidentes
 *       Incid�ncia da anormalidade para a gera��o da ordem de servi�o,
 *       Emiss�o autom�tica de documento, Indicador de que a ocorr�ncia aceita leitura,
 *       Indicador de que a ocorr�ncia deve ser listada nos relat�rios de cr�tica/fiscaliza��o de
 *       leitura
 *       Indicador de reten��o de conta , Indicador de concess�o de cr�dito de consumo
 *       Indicador de isen��o de cobran�a de �gua, Indicador de isen��o de cobran�a de esgoto
 */
public class ExibirInserirAnormalidadeLeituraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirAnormalidadeLeitura");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
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

			// Retorna Tipo Servi�o
			colecaoPesquisa = fachada.pesquisar(filtroTipoServico, ServicoTipo.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Nenhum registro na tabela localidade_porte foi encontrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tipo de Servico");
			}else{
				sessao.setAttribute("colecaoTipoServico", colecaoPesquisa);
			}

		}

		// cole��o anormalidade consumo

		FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
		filtroLeituraAnormalidadeConsumo.setCampoOrderBy(FiltroLeituraAnormalidadeConsumo.ID);

		Collection colecaoLeituraAnormalidadeConsumo = fachada.pesquisar(filtroLeituraAnormalidadeConsumo, LeituraAnormalidadeConsumo.class
						.getName());
		sessao.setAttribute("colecaoLeituraAnormalidadeConsumo", colecaoLeituraAnormalidadeConsumo);

		// cole��o anormalidade leitura

		FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
		filtroLeituraAnormalidadeLeitura.setCampoOrderBy(FiltroLeituraAnormalidadeLeitura.ID);

		Collection colecaoLeituraAnormalidadeLeitura = fachada.pesquisar(filtroLeituraAnormalidadeLeitura, LeituraAnormalidadeLeitura.class
						.getName());
		sessao.setAttribute("colecaoLeituraAnormalidadeLeitura", colecaoLeituraAnormalidadeLeitura);

		// Cole��o de Tipos de Documento
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.ID);

		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());
		sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);

		// devolve o mapeamento de retorno
		return retorno;
	}

}
