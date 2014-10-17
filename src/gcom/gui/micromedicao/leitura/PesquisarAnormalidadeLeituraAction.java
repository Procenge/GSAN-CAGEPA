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

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarAnormalidadeLeituraAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @author eduardo henrique
	 * @date 17/06/2008
	 *       Inclus�o dos atributos: Mensagem de Leitura , Mensagem de manuten��o, Mensagem de
	 *       preven��o de acidentes
	 *       Incid�ncia da anormalidade para a gera��o da ordem de servi�o,
	 *       Emiss�o autom�tica de documento, Indicador de que a ocorr�ncia aceita leitura,
	 *       Indicador de que a ocorr�ncia deve ser listada nos relat�rios de cr�tica/fiscaliza��o
	 *       de leitura
	 *       Indicador de reten��o de conta , Indicador de concess�o de cr�dito de consumo
	 *       Indicador de isen��o de cobran�a de �gua, Indicador de isen��o de cobran�a de esgoto
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("listaLeituraAnormalidades");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarLeituraAnormalidadeActionForm pesquisarLeituraAnormalidadeActionForm = (PesquisarLeituraAnormalidadeActionForm) actionForm;

		// Recupera os parametros

		String descricao = pesquisarLeituraAnormalidadeActionForm.getDescricao();
		String anormalidadeHidrometro = pesquisarLeituraAnormalidadeActionForm.getAnormalidadeRelativaHidrometro();
		String anormalidadeAceitaSemHidrometro = pesquisarLeituraAnormalidadeActionForm.getAnormalidadeSemHidrometro();
		String anormalidadeUsoSistema = pesquisarLeituraAnormalidadeActionForm.getAnormalidadeRestritoSistema();
		String anormalidadePerdaTarifaSocial = pesquisarLeituraAnormalidadeActionForm.getAnormalidadePerdaTarifaSocial();
		String anormalidadeOrdemServicoAutomatica = pesquisarLeituraAnormalidadeActionForm.getAnormalidadeOrdemServicoAutomatica();
		Integer tipoServico = new Integer(pesquisarLeituraAnormalidadeActionForm.getTipoServico());
		Integer consumoLeituraInformada = new Integer(pesquisarLeituraAnormalidadeActionForm.getConsumoCobradoLeituraInformada());
		Integer consumoLeituraNaoInformada = new Integer(pesquisarLeituraAnormalidadeActionForm.getConsumoCobradoLeituraNaoInformada());
		Integer leituraFaturamentoLeituraInformada = new Integer(pesquisarLeituraAnormalidadeActionForm
						.getLeituraFaturamentoLeituraInformada());
		Integer leituraFaturamentoLeituraNaoInformada = new Integer(pesquisarLeituraAnormalidadeActionForm
						.getLeituraFaturamentoLeituraNaoInformada());

		String tipoPesquisa = pesquisarLeituraAnormalidadeActionForm.getTipoPesquisa();

		FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade(FiltroLeituraAnormalidade.ID);

		// Insere os par�metros informados no filtro

		if(descricao != null && !descricao.equals("")){
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroLeituraAnormalidade.adicionarParametro(new ComparacaoTextoCompleto(FiltroSistemaEsgoto.DESCRICAO, descricao));
			}else{

				filtroLeituraAnormalidade.adicionarParametro(new ComparacaoTexto(FiltroSistemaEsgoto.DESCRICAO, descricao));
			}
		}

		if(anormalidadeHidrometro != null && !anormalidadeHidrometro.equals("")
						&& !(new Short(anormalidadeHidrometro)).equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_RELATIVO_HIDROMETRO,
							anormalidadeHidrometro));
		}

		if(anormalidadeAceitaSemHidrometro != null && !anormalidadeAceitaSemHidrometro.equals("")
						&& !(new Short(anormalidadeAceitaSemHidrometro)).equals(ConstantesSistema.TODOS)){

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_IMOVEL_SEM_HIDROMETRO,
							anormalidadeAceitaSemHidrometro));
		}

		if(anormalidadeUsoSistema != null && !anormalidadeUsoSistema.equals("")
						&& !(new Short(anormalidadeUsoSistema)).equals(ConstantesSistema.TODOS)){

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_USO_SISTEMA,
							anormalidadeUsoSistema));
		}

		if(anormalidadePerdaTarifaSocial != null && !anormalidadePerdaTarifaSocial.equals("")
						&& !(new Short(anormalidadePerdaTarifaSocial)).equals(ConstantesSistema.TODOS)){

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_PERDA_TARIFA_SOCIAL,
							anormalidadePerdaTarifaSocial));
		}

		if(anormalidadeOrdemServicoAutomatica != null && !anormalidadeOrdemServicoAutomatica.equals("")
						&& !(new Short(anormalidadeOrdemServicoAutomatica)).equals(ConstantesSistema.TODOS)){

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_EMISSAO_ORDEM_SERVICO,
							anormalidadeOrdemServicoAutomatica));
		}

		if(tipoServico != null && tipoServico.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_TIPO_SERVICO, tipoServico));
		}

		if(consumoLeituraInformada != null && consumoLeituraInformada.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_CONSUMO_A_COBRAR_COM_LEITURA,
							consumoLeituraInformada));
		}

		if(consumoLeituraNaoInformada != null && consumoLeituraNaoInformada.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_CONSUMO_A_COBRAR_SEM_LEITURA,
							consumoLeituraNaoInformada));
		}

		if(leituraFaturamentoLeituraInformada != null
						&& leituraFaturamentoLeituraInformada.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_LEITURA_A_FATURAR_COM_LEITURA,
							leituraFaturamentoLeituraInformada));
		}

		if(leituraFaturamentoLeituraNaoInformada != null
						&& leituraFaturamentoLeituraNaoInformada.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_LEITURA_A_FATURAR_SEM_LEITURA,
							leituraFaturamentoLeituraNaoInformada));
		}

		// Adicionado Eduardo Henrique 17/06/2008
		if(pesquisarLeituraAnormalidadeActionForm.getTipoDocumento() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getTipoDocumento().trim().equalsIgnoreCase(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID_TIPO_DOCUMENTO,
							pesquisarLeituraAnormalidadeActionForm.getTipoDocumento()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getNumeroIncidenciasGeracaoOS() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getNumeroIncidenciasGeracaoOS().equalsIgnoreCase("")){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.NUMERO_INCIDENCIAS_GERACAO_OS,
							pesquisarLeituraAnormalidadeActionForm.getNumeroIncidenciasGeracaoOS()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getAceiteLeitura() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getAceiteLeitura().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getAceiteLeitura())).equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_ACEITE_LEITURA,
							pesquisarLeituraAnormalidadeActionForm.getAceiteLeitura()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getImpressaoRelatorioCriticaFiscalizacao() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getImpressaoRelatorioCriticaFiscalizacao().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getImpressaoRelatorioCriticaFiscalizacao()))
										.equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(
							FiltroLeituraAnormalidade.INDICADOR_IMPRESSAO_RELATORIOS_CRITICA_FISCALIZACAO,
							pesquisarLeituraAnormalidadeActionForm.getImpressaoRelatorioCriticaFiscalizacao()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getIndicadorRetencaoConta() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getIndicadorRetencaoConta().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getIndicadorRetencaoConta())).equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_RETENCAO_CONTA,
							pesquisarLeituraAnormalidadeActionForm.getIndicadorRetencaoConta()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaAgua() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaAgua().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaAgua()))
										.equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_ISENCAO_COBRANCA_AGUA,
							pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaAgua()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaEsgoto() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaEsgoto().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaEsgoto()))
										.equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_ISENCAO_COBRANCA_ESGOTO,
							pesquisarLeituraAnormalidadeActionForm.getIndicadorIsencaoCobrancaEsgoto()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getIndicadorConcessaoCreditoConsumo() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getIndicadorConcessaoCreditoConsumo().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getIndicadorConcessaoCreditoConsumo()))
										.equals(ConstantesSistema.TODOS)){
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(
							FiltroLeituraAnormalidade.INDICADOR_CONCESSAO_CREDITO_CONSUMO, pesquisarLeituraAnormalidadeActionForm
											.getIndicadorConcessaoCreditoConsumo()));
		}

		if(pesquisarLeituraAnormalidadeActionForm.getMensagemImpressaoConta() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getMensagemImpressaoConta().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getMensagemImpressaoConta())).equals(ConstantesSistema.TODOS)){

			if(pesquisarLeituraAnormalidadeActionForm.getMensagemImpressaoConta().equalsIgnoreCase("1")){
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNaoNulo(FiltroLeituraAnormalidade.MENSAGEM_IMPRESSAO_CONTA));
			}else{
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNulo(FiltroLeituraAnormalidade.MENSAGEM_IMPRESSAO_CONTA));
			}
		}

		if(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgenteManutencao() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getSugestaoAgenteManutencao().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgenteManutencao()))
										.equals(ConstantesSistema.TODOS)){

			if(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgenteManutencao().equalsIgnoreCase("1")){
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNaoNulo(FiltroLeituraAnormalidade.MENSAGEM_SUGESTAO_MANUTENCAO));
			}else{
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNulo(FiltroLeituraAnormalidade.MENSAGEM_SUGESTAO_MANUTENCAO));
			}
		}

		if(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgentePrevencao() != null
						&& !pesquisarLeituraAnormalidadeActionForm.getSugestaoAgentePrevencao().equalsIgnoreCase("")
						&& !(new Short(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgentePrevencao()))
										.equals(ConstantesSistema.TODOS)){

			if(pesquisarLeituraAnormalidadeActionForm.getSugestaoAgentePrevencao().equalsIgnoreCase("1")){
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNaoNulo(
								FiltroLeituraAnormalidade.MENSAGEM_SUGESTAO_PREVENCAO_ACIDENTES));
			}else{
				filtroLeituraAnormalidade.adicionarParametro(new ParametroNulo(
								FiltroLeituraAnormalidade.MENSAGEM_SUGESTAO_PREVENCAO_ACIDENTES));
			}
		}

		Collection leituraAnormalidades = null;

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// adiciona as depend�ncias para serem mostradas na p�gina
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoSemleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoComleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraSemleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraComleitura");

		// Faz a busca das empresas
		leituraAnormalidades = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

		if(leituraAnormalidades == null || leituraAnormalidades.isEmpty()){

			// Nenhum municipio cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Anormalidade de Leitura");
		}else if(leituraAnormalidades.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA){
			// Muitos registros encontrados
			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		}else{
			if(leituraAnormalidades.size() == ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA){
				httpServletRequest.setAttribute("limitePesquisa", "");
			}

			// Coloca a cole��o na sess�o
			sessao.setAttribute("leituraAnormalidades", leituraAnormalidades);

		}

		return retorno;
	}

}
