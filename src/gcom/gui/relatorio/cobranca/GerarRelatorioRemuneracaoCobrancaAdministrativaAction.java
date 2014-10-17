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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.imovel.FiltroImovelCobrancaAdministrivaItem;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.FiltroLocalidadeSetorComercialHelper;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativa;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativaHelper;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativaModelo2;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3070] Relatório Estatístico de Registro Atendimento
 * 
 * @author Luciano Galvao
 * @date 31/08/2012
 * @author Felipe Rosacruz
 * @date 22/12/2013
 *       Alteração para Customização do módulo de cobrança para DESO
 */
public class GerarRelatorioRemuneracaoCobrancaAdministrativaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	private static final String COLECAO_LOCALIDADES_SETORES = "colecaoLocalidadesSetores";


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){
		ActionForward retorno = null;

		if(httpServletRequest.getParameter("modeloRelatorio") != null){
			String modeloRelatorio = ((GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm) actionForm).getModeloRelatorio();
			if(modeloRelatorio.equals("1")){
				gerarRelatorioRemuneracaoCobrancaAdministrativaActionModelo1(actionMapping, actionForm, httpServletRequest,
								httpServletResponse);
			}else if(modeloRelatorio.equals("2")){
				gerarRelatorioRemuneracaoCobrancaAdministrativaActionModelo2(actionMapping, actionForm, httpServletRequest,
								httpServletResponse);
			}
		}

		return retorno;
	}

	/**
	 * @author Felipe Rosacruz
	 * @date 22/12/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	private ActionForward gerarRelatorioRemuneracaoCobrancaAdministrativaActionModelo1(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form = (GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm) actionForm;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Date periodoPagamentoInicial = null;
		Date periodoPagamentoFinal = null;

		// Captura a data inicial do período de pagamento
		if(!Util.isVazioOuBranco(form.getPeriodoPagamentoInicial())){
			try{
				periodoPagamentoInicial = Util.converteStringParaDate(form.getPeriodoPagamentoInicial());

			}catch(IllegalArgumentException e){
				throw new ActionServletException("atencao.data.inicial.invalida");
			}
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", "Data Inicial");
		}

		// Captura a data final do período de pagamento
		if(!Util.isVazioOuBranco(form.getPeriodoPagamentoFinal())){
			try{
				periodoPagamentoFinal = Util.converteStringParaDate(form.getPeriodoPagamentoFinal());

			}catch(IllegalArgumentException e){
				throw new ActionServletException("atencao.data.final.invalida");
			}
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", "Data Final");
		}

		String situacaoRemuneracao = form.getSituacaoRemuneracao();

		if(situacaoRemuneracao == null
						|| (!situacaoRemuneracao.equals("1") && !situacaoRemuneracao.equals("2") && !situacaoRemuneracao.equals("3"))){
			throw new ActionServletException("atencao.informe_campo", null, "Situação da Remuneração");
		}

		String indicadorConfirmaPagamento = form.getIndicadorConfirmaPagamento();

		if(indicadorConfirmaPagamento == null || (!indicadorConfirmaPagamento.equals("1") && !indicadorConfirmaPagamento.equals("2"))){
			throw new ActionServletException("atencao.informe_campo", null, "Confirma pagamento da remuneração dos itens emitidos?");
		}

		RelatorioRemuneracaoCobrancaAdministrativaHelper helper = new RelatorioRemuneracaoCobrancaAdministrativaHelper(periodoPagamentoInicial,
						periodoPagamentoFinal);

		// Captura as localidades e setores informados pelo usuário para geração do Relatório
		capturarLocalidadesSetores(sessao, helper);

		helper.setSituacaoRemuneracao(situacaoRemuneracao);
		helper.setIndicadorConfirmaPagamento(indicadorConfirmaPagamento);

		Fachada.getInstancia().validarCamposObrigatoriosHelperRemuneracaoCobrancaAdministrativa(helper);

		RelatorioRemuneracaoCobrancaAdministrativa relatorio = new RelatorioRemuneracaoCobrancaAdministrativa(
						(Usuario) sessao.getAttribute("usuarioLogado"));

		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativa.FILTRO_INFORMADO, helper);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativa.FORMATO_RELATORIO, TarefaRelatorio.TIPO_PDF);

		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

	/**
	 * @author Felipe Rosacruz
	 * @date 22/12/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	private ActionForward gerarRelatorioRemuneracaoCobrancaAdministrativaActionModelo2(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm form = (GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		String tipoRelatorio;
		String referenciaArrecadacaoInicial;
		String referenciaArrecadacaoFinal;
		String situacaoRemuneracao;
		String indicadorpagamento;

		RelatorioRemuneracaoCobrancaAdministrativaModelo2 relatorio = new RelatorioRemuneracaoCobrancaAdministrativaModelo2(
						(Usuario) sessao.getAttribute("usuarioLogado"));

		tipoRelatorio = form.getTipoRelatorio();
		referenciaArrecadacaoInicial = form.getReferenciaInicial();
		referenciaArrecadacaoFinal = form.getReferenciaFinal();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		int anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao();

		if(Util.formatarMesAnoComBarraParaAnoMes(referenciaArrecadacaoInicial) > anoMesArrecadacao - 1
						|| Util.formatarMesAnoComBarraParaAnoMes(referenciaArrecadacaoFinal) > anoMesArrecadacao - 1){
			throw new ActionServletException("atencao.arrecadacao_referencia_invalido");
		}

		Collection<Integer> idsEmpresasCobrancaAdministrativa = new ArrayList<Integer>();
		if(form.getIdEmpresaCobrancaAdministrativa() != null){
		for(Integer idEmpresa : form.getIdEmpresaCobrancaAdministrativa()){
			if(idEmpresa != -1){
				idsEmpresasCobrancaAdministrativa.add(idEmpresa);
			}
		}}

		situacaoRemuneracao = form.getSituacaoRemuneracao();
		indicadorpagamento = form.getIndicadorConfirmaPagamento();

		FiltroImovelCobrancaAdministrivaItem filtroImovelCobrancaAdministrivaItem = new FiltroImovelCobrancaAdministrivaItem();
		filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroNaoNulo(
						FiltroImovelCobrancaAdministrivaItem.COBRANCA_ADMINISTRATIVA_REMUNERACAO_MENSAL));

		FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

		FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
		Collection<CobrancaAcaoAtividadeComando> colecaoCobrancaAcaoAtividadeComandos = fachada.pesquisar(
						filtroCobrancaAcaoAtividadeComando, CobrancaAcaoAtividadeComando.class.getName());
		Collection<Integer> colecaoIdsCobrancaAcaoAtividadeComando = new ArrayList<Integer>();
		for(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando : colecaoCobrancaAcaoAtividadeComandos){
			if(colecaoIdsCobrancaAcaoAtividadeComando.size() == 1000){
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimplesColecao(
								FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, colecaoIdsCobrancaAcaoAtividadeComando,
								ConectorOr.CONECTOR_OR));
				colecaoIdsCobrancaAcaoAtividadeComando.clear();
			}
			colecaoIdsCobrancaAcaoAtividadeComando.add(cobrancaAcaoAtividadeComando.getId());
		}

		filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimplesColecao(
						FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, colecaoIdsCobrancaAcaoAtividadeComando));
		
		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = fachada.pesquisar(filtroImovelCobrancaSituacao,
						ImovelCobrancaSituacao.class.getName());
		
		Collection<Integer> colecaoIdsImovelCobrancaSituacao = new ArrayList<Integer>();
		
		for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){
			if(colecaoIdsImovelCobrancaSituacao.size() == 1000){
				filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroSimplesColecao(
								FiltroImovelCobrancaAdministrivaItem.IMOVEL_COBRANCA_SITUACAO_ID, colecaoIdsImovelCobrancaSituacao,
								ConectorOr.CONECTOR_OR));
				colecaoIdsImovelCobrancaSituacao.clear();
			}
			colecaoIdsImovelCobrancaSituacao.add(imovelCobrancaSituacao.getId());
		}
		
		filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroSimplesColecao(
						FiltroImovelCobrancaAdministrivaItem.IMOVEL_COBRANCA_SITUACAO_ID, colecaoIdsImovelCobrancaSituacao));

		// filtroImovelCobrancaAdministrivaItem.setCampoOrderBy("imovelCobrancaSituacao.cobrancaAcaoAtividadeComando.empresa.id",
		// "anoMesReferenciaArrecadacao", "imovelCobrancaSituacao.cobrancaAcaoAtividadeComando.id",
		// "dataPagamentoDocumento");

		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.FILTRO_ITENS_COBRANCA_ADMINISTRATIVA_DADOS_COMANDOS,
						filtroImovelCobrancaAdministrivaItem);

		// pesquisar para parâmetros informardos na tela
		filtroImovelCobrancaAdministrivaItem = new FiltroImovelCobrancaAdministrivaItem();
		filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroNaoNulo(
						FiltroImovelCobrancaAdministrivaItem.COBRANCA_ADMINISTRATIVA_REMUNERACAO_MENSAL));
		filtroImovelCobrancaAdministrivaItem.adicionarParametro(new MaiorQue("anoMesReferenciaArrecadacao", Util
						.formatarMesAnoComBarraParaAnoMes(referenciaArrecadacaoInicial)));
		filtroImovelCobrancaAdministrivaItem.adicionarParametro(new MenorQue("anoMesReferenciaArrecadacao", Util
						.formatarMesAnoComBarraParaAnoMes(referenciaArrecadacaoFinal)));
		if(!situacaoRemuneracao.equals("3")){
			filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroSimples("indicadorRemuneracaoPaga", situacaoRemuneracao));
		}

		if(Util.isNaoNuloBrancoZero(idsEmpresasCobrancaAdministrativa)){
			filtroCobrancaAcaoAtividadeComando.limparListaParametros();
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimplesColecao(
							FiltroCobrancaAcaoAtividadeComando.ID_EMPRESA,
							idsEmpresasCobrancaAdministrativa));
			colecaoCobrancaAcaoAtividadeComandos = fachada.pesquisar(filtroCobrancaAcaoAtividadeComando,
							CobrancaAcaoAtividadeComando.class.getName());

			for(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando : colecaoCobrancaAcaoAtividadeComandos){
				if(colecaoIdsCobrancaAcaoAtividadeComando.size() == 1000){
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimplesColecao(
									FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID,
									colecaoIdsCobrancaAcaoAtividadeComando, ConectorOr.CONECTOR_OR));
					colecaoIdsCobrancaAcaoAtividadeComando.clear();
				}
				colecaoIdsCobrancaAcaoAtividadeComando.add(cobrancaAcaoAtividadeComando.getId());
			}

			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimplesColecao(
							FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, colecaoIdsCobrancaAcaoAtividadeComando));

			colecaoImovelCobrancaSituacao = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class.getName());

			colecaoIdsImovelCobrancaSituacao = new ArrayList<Integer>();

			for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){
				if(colecaoIdsImovelCobrancaSituacao.size() == 1000){
					filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroSimplesColecao(
									FiltroImovelCobrancaAdministrivaItem.IMOVEL_COBRANCA_SITUACAO_ID, colecaoIdsImovelCobrancaSituacao,
									ConectorOr.CONECTOR_OR));
					colecaoIdsImovelCobrancaSituacao.clear();
				}
				colecaoIdsImovelCobrancaSituacao.add(imovelCobrancaSituacao.getId());
			}

			filtroImovelCobrancaAdministrivaItem.adicionarParametro(new ParametroSimplesColecao(
							FiltroImovelCobrancaAdministrivaItem.IMOVEL_COBRANCA_SITUACAO_ID, colecaoIdsImovelCobrancaSituacao));

		}

		// Collection<ImovelCobrancaAdministrivaItem>
		// colecaoImovelCobrancaAdministrivaItemParametrosInformados = fachada.pesquisar(
		// filtroImovelCobrancaAdministrivaItem, ImovelCobrancaAdministrivaItem.class.getName());
		// Comparator<ImovelCobrancaAdministrivaItem> comparator = new
		// Comparator<ImovelCobrancaAdministrivaItem>() {
		//
		// public int compare(ImovelCobrancaAdministrivaItem o1, ImovelCobrancaAdministrivaItem o2){
		//
		// // TODO Auto-generated method stub
		// return 0;
		// }
		// };



		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_TIPO_RELATORIO, tipoRelatorio);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_REFERENCIA_INICIAL,
						referenciaArrecadacaoInicial);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_REFERENCIA_FINAL,
						referenciaArrecadacaoFinal);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_SITUACAO_REMUNERACAO,
						situacaoRemuneracao);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_INDICADOR_PAGAMENTO,
						indicadorpagamento);

		Collection<Empresa> colecaoEmpresas = new ArrayList<Empresa>();
		if(Util.isNaoNuloBrancoZero(idsEmpresasCobrancaAdministrativa)){
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa(FiltroEmpresa.DESCRICAO);
			filtroEmpresa.adicionarParametro(new ParametroSimplesColecao(FiltroEmpresa.ID, idsEmpresasCobrancaAdministrativa));
			colecaoEmpresas = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		}

		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.PARAMETRO_INFORMADO_EMPRESAS,
 colecaoEmpresas);
		relatorio.addParametro(RelatorioRemuneracaoCobrancaAdministrativaModelo2.FILTRO_ITENS_COBRANCA_ADMINISTRATIVA,
						filtroImovelCobrancaAdministrivaItem);

		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

	/**
	 * Retorna uma coleção de tipos de solicitação a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param localidadesId
	 * @return
	 */
	private Collection<Localidade> getLocalidades(String[] localidadesId){

		// Localidade
		Collection<Localidade> localidades = null;

		for(int i = 0; i < localidadesId.length; i++){

			if(!Util.isVazioOuBranco(localidadesId[i]) && !localidadesId[i].equals("0") && !localidadesId[i].equals("-1")){

				try{

					FiltroLocalidade filtro = new FiltroLocalidade();
					filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.parseInt(localidadesId[i])));

					Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro,
									Localidade.class.getName()));

					if(localidade == null){
						throw new ActionServletException("pesquisa.localidade.inexistente");
					}

					if(localidades == null){
						localidades = new ArrayList();
					}

					localidades.add(localidade);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return localidades;
	}

	/**
	 * Retorna uma coleção de tipos de solicitação a partir de um conjunto de Ids
	 * 
	 * @author Luciano Galvao
	 * @param tiposSolicitacaoId
	 * @return
	 */
	private Collection<SetorComercial> getSetoresComerciais(String[] setoresComerciaisId){

		// Tipo Solicitação
		Collection<SetorComercial> setoresComerciais = null;

		for(int i = 0; i < setoresComerciaisId.length; i++){

			if(!Util.isVazioOuBranco(setoresComerciaisId[i]) && !setoresComerciaisId[i].equals("0") && !setoresComerciaisId[i].equals("-1")){

				try{

					FiltroSetorComercial filtro = new FiltroSetorComercial();
					filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, Integer.parseInt(setoresComerciaisId[i])));

					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro,
									SetorComercial.class.getName()));

					if(setorComercial == null){
						throw new ActionServletException("atencao.setor_comercial.inexistente");
					}

					if(setoresComerciais == null){
						setoresComerciais = new ArrayList();
					}

					setoresComerciais.add(setorComercial);

				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.numero.formato.invalido");
				}
			}
		}

		return setoresComerciais;
	}

	/**
	 * Recupera as localidades e setores selecionados pelo usuário e preenche no objeto Helper que
	 * será repassado ao Relatório
	 * 
	 * @author Luciano Galvao
	 * @created 11/09/2012
	 * @param sessao
	 * @param helper
	 */
	private void capturarLocalidadesSetores(HttpSession sessao, RelatorioRemuneracaoCobrancaAdministrativaHelper helper){

		Collection<Localidade> localidadesComSetorComercial = new ArrayList<Localidade>();
		Collection<Localidade> localidadesSemSetorComercial = new ArrayList<Localidade>();
		Collection<SetorComercial> setoresComerciais = new ArrayList<SetorComercial>();

		Collection<FiltroLocalidadeSetorComercialHelper> colecaoLocalidadesSetores = (Collection<FiltroLocalidadeSetorComercialHelper>) sessao
						.getAttribute(COLECAO_LOCALIDADES_SETORES);

		if(!Util.isVazioOrNulo(colecaoLocalidadesSetores)){

			for(FiltroLocalidadeSetorComercialHelper localidadeSetorComercialHelper : colecaoLocalidadesSetores){
				if(localidadeSetorComercialHelper.getLocalidade() != null){
					if(localidadeSetorComercialHelper.getSetorComercial() == null){
						localidadesSemSetorComercial.add(localidadeSetorComercialHelper.getLocalidade());
					}else{
						localidadesComSetorComercial.add(localidadeSetorComercialHelper.getLocalidade());
						setoresComerciais.add(localidadeSetorComercialHelper.getSetorComercial());
					}
				}
			}
		}

		helper.setLocalidadesSemSetorComercial(localidadesSemSetorComercial);
		helper.setLocalidadesComSetorComercial(localidadesComSetorComercial);
		helper.setSetoresComerciais(setoresComerciais);

	}

}
