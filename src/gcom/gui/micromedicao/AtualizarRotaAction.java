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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action form de manter rota
 * 
 * @author Vivianne Sousa
 * @created03/04/2006
 * @author eduardo henrique
 * @date 26/08/2008
 *       Alteração para obrigatoriedade do Leiturista/Agente Comercial no cadastro da Rota
 */
public class AtualizarRotaAction
				extends GcomAction {

	/**
	 * @author Vivianne Sousa
	 * @date 03/04/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		InserirRotaActionForm inserirRotaActionForm = (InserirRotaActionForm) actionForm;
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		/*
		 * RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		 * Operacao.OPERACAO_ROTA_ATUALIZAR,
		 * new UsuarioAcaoUsuarioHelper(getUsuarioLogado(httpServletRequest),
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		 * Operacao operacao = new Operacao();
		 * operacao.setId(Operacao.OPERACAO_ROTA_ATUALIZAR);
		 * OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		 * operacaoEfetuada.setOperacao(operacao);
		 */
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		String idRota = (String) sessao.getAttribute("idRegistroAtualizacao");
		// String descFaturamentoGrupo = null;
		// obrigatórios
		String codigoRota = inserirRotaActionForm.getCodigoRota();
		String idLocalidade = inserirRotaActionForm.getIdLocalidade();
		String codigoSetorComercial = inserirRotaActionForm.getCodigoSetorComercial();
		String idCobrancaGrupo = inserirRotaActionForm.getCobrancaGrupo();
		String idFaturamentoGrupo = inserirRotaActionForm.getFaturamentoGrupo();
		String idLeituraTipo = inserirRotaActionForm.getLeituraTipo();
		String idEmpresaLeituristica = inserirRotaActionForm.getEmpresaLeituristica();
		String idCobrancaCriterio = inserirRotaActionForm.getCobrancaCriterio();
		String indicadorFiscalizarCortado = inserirRotaActionForm.getIndicadorFiscalizarCortado();
		String indicadorFiscalizarSuprimido = inserirRotaActionForm.getIndicadorFiscalizarSuprimido();
		String indicadorGerarFalsaFaixa = inserirRotaActionForm.getIndicadorGerarFalsaFaixa();
		String indicadorGerarFiscalizacao = inserirRotaActionForm.getIndicadorGerarFiscalizacao();
		String indicadorAjusteConsumo = inserirRotaActionForm.getIndicadorAjusteConsumo();
		String indicadorUso = inserirRotaActionForm.getIndicadorUso();
		String idLeiturista = inserirRotaActionForm.getCodigoLeiturista();

		// n obrigatórios
		// Date dataAjusteLeitura = null;
		// if (inserirRotaActionForm.getDataAjusteLeitura() != null ){
		// dataAjusteLeitura =
		// Util.converteStringParaDate(inserirRotaActionForm.getDataAjusteLeitura());
		// }
		String percentualGeracaoFaixaFalsa = null;
		if(inserirRotaActionForm.getPercentualGeracaoFaixaFalsa() != null){
			percentualGeracaoFaixaFalsa = inserirRotaActionForm.getPercentualGeracaoFaixaFalsa().toString().replace(",", ".");
		}

		String percentualGeracaoFiscalizacao = null;
		if(inserirRotaActionForm.getPercentualGeracaoFiscalizacao() != null){
			percentualGeracaoFiscalizacao = inserirRotaActionForm.getPercentualGeracaoFiscalizacao().toString().replace(",", ".");
		}

		// Critério de Cobrança
		Collection collectionRotaAcaoCriterio = null;

		if(sessao.getAttribute("collectionRotaAcaoCriterio") != null){
			collectionRotaAcaoCriterio = (Collection) sessao.getAttribute("collectionRotaAcaoCriterio");
		}

		validacaoRota(idLocalidade, codigoSetorComercial, codigoRota, idCobrancaGrupo, idFaturamentoGrupo, idLeituraTipo,
						idEmpresaLeituristica, indicadorFiscalizarCortado, indicadorFiscalizarSuprimido, indicadorGerarFalsaFaixa,
						indicadorGerarFiscalizacao, percentualGeracaoFaixaFalsa, percentualGeracaoFiscalizacao, indicadorUso, idLeiturista,
						collectionRotaAcaoCriterio, indicadorAjusteConsumo, fachada, httpServletRequest);

		Rota rotaNova = new Rota();

		rotaNova.setId(Integer.valueOf(idRota));
		// codigo
		rotaNova.setCodigo(Short.valueOf(codigoRota));

		// rotaNova.setDataAjusteLeitura(dataAjusteLeitura);

		rotaNova.setIndicadorFiscalizarCortado(Integer.valueOf(indicadorFiscalizarCortado).shortValue());

		rotaNova.setIndicadorFiscalizarSuprimido(Integer.valueOf(indicadorFiscalizarSuprimido).shortValue());

		rotaNova.setIndicadorGerarFalsaFaixa(Integer.valueOf(indicadorGerarFalsaFaixa).shortValue());

		rotaNova.setIndicadorAjusteConsumo(Integer.valueOf(indicadorAjusteConsumo).shortValue());

		if(percentualGeracaoFaixaFalsa != null && !percentualGeracaoFaixaFalsa.equals("")){
			rotaNova.setPercentualGeracaoFaixaFalsa(new BigDecimal(percentualGeracaoFaixaFalsa));
		}

		rotaNova.setIndicadorGerarFiscalizacao(Integer.valueOf(indicadorGerarFiscalizacao).shortValue());

		if(percentualGeracaoFiscalizacao != null && !percentualGeracaoFiscalizacao.equals("")){
			rotaNova.setPercentualGeracaoFiscalizacao(new BigDecimal(percentualGeracaoFiscalizacao));
		}

		SetorComercial setorComercial = null;
		if(codigoSetorComercial != null && !codigoSetorComercial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			// Filtro para descobrir id do setor comercial
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial
							.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer.valueOf(idLocalidade)));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
							.valueOf(codigoSetorComercial)));

			Collection<SetorComercial> colectionSetorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			Integer idSetorComercial = colectionSetorComerciais.iterator().next().getId();

			setorComercial = new SetorComercial();
			setorComercial.setId(Integer.valueOf(idSetorComercial));

			rotaNova.setSetorComercial(setorComercial);

		}

		CobrancaGrupo cobrancaGrupo = null;
		if(idCobrancaGrupo != null && !idCobrancaGrupo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			cobrancaGrupo = new CobrancaGrupo();
			cobrancaGrupo.setId(Integer.valueOf(idCobrancaGrupo));
			rotaNova.setCobrancaGrupo(cobrancaGrupo);
		}

		FaturamentoGrupo faturamentoGrupo = null;
		if(idFaturamentoGrupo != null && !idFaturamentoGrupo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			faturamentoGrupo = new FaturamentoGrupo();
			faturamentoGrupo.setId(Integer.valueOf(idFaturamentoGrupo));
			rotaNova.setFaturamentoGrupo(faturamentoGrupo);

			/*
			 * FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			 * filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(
			 * FiltroFaturamentoGrupo.ID, new Integer(
			 * idFaturamentoGrupo)));
			 * Collection<FaturamentoGrupo> colectionFaturamentoGrupos = fachada.pesquisar(
			 * filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
			 * descFaturamentoGrupo = ((FaturamentoGrupo)
			 * Util.retonarObjetoDeColecao(colectionFaturamentoGrupos)).getDescricao();
			 */
		}

		LeituraTipo leituraTipo = null;
		if(idLeituraTipo != null && !idLeituraTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			leituraTipo = new LeituraTipo();
			leituraTipo.setId(Integer.valueOf(idLeituraTipo));
			rotaNova.setLeituraTipo(leituraTipo);
		}

		Empresa empresaLeituristica = null;
		if(idEmpresaLeituristica != null && !idEmpresaLeituristica.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			empresaLeituristica = new Empresa();
			empresaLeituristica.setId(Integer.valueOf(idEmpresaLeituristica));
			rotaNova.setEmpresa(empresaLeituristica);
		}

		// adicionado a pedido de Eduardo 22/02/2007
		if(idEmpresaLeituristica != null && !idEmpresaLeituristica.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			rotaNova.setEmpresaCobranca(empresaLeituristica);
		}else{
			empresaLeituristica.setId(Integer.valueOf(1));
			rotaNova.setEmpresaCobranca(empresaLeituristica);
		}

		Leiturista leiturista = null;
		if(idLeiturista != null && !idLeiturista.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			leiturista = new Leiturista();
			leiturista.setId(Integer.valueOf(idLeiturista));

			rotaNova.setLeiturista(leiturista);
		}

		CobrancaCriterio cobrancaCriterio = null;
		if(idCobrancaCriterio != null && !idCobrancaCriterio.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			cobrancaCriterio = new CobrancaCriterio();
			cobrancaCriterio.setId(Integer.valueOf(idCobrancaCriterio));
			// rotaNova.setCobrancaCriterio(cobrancaCriterio);
		}

		rotaNova.setIndicadorUso(Integer.valueOf(indicadorUso).shortValue());
		rotaNova.setUltimaAlteracao(Util.converteStringParaDateHora(inserirRotaActionForm.getUltimaAlteracao()));

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		/*
		 * rotaNova.setOperacaoEfetuada(operacaoEfetuada);
		 * rotaNova.adicionarUsuario(getUsuarioLogado(httpServletRequest),
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		 * registradorOperacao.registrarOperacao(rotaNova);
		 */
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		fachada.atualizarRota(rotaNova, idLocalidade, collectionRotaAcaoCriterio, usuarioLogado, true);

		sessao.removeAttribute("collectionRotaAcaoCriterio");
		sessao.removeAttribute("idRegistroAtualizacao");

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, "Rota de código " + inserirRotaActionForm.getCodigoRota() + " da localidade "
							+ idLocalidade + " do setor " + codigoSetorComercial + " atualizada com sucesso.",
							"Realizar outra Manutenção de Rota", "exibirFiltrarRotaAction.do?desfazer=S");
		}

		return retorno;
	}

	private void validacaoRota(String idLocalidade, String codigoSetorComercial, String codigoRota, String idCobrancaGrupo,
					String idFaturamentoGrupo, String idLeituraTipo, String idEmpresaLeituristica, String indicadorFiscalizarCortado,
					String indicadorFiscalizarSuprimido, String indicadorGerarFalsaFaixa, String indicadorGerarFiscalizacao,
					String percentualGeracaoFaixaFalsa, String percentualGeracaoFiscalizacao, String indicadorUso, String codigoLeiturista,
					Collection collectionRotaAcaoCriterioNovos, String indicadorAjusteConsumo, Fachada fachada,
					HttpServletRequest httpServletRequest){

		// Localidade é obrigatório.
		if((idLocalidade == null) || (idLocalidade.equals(""))){
			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			throw new ActionServletException("atencao.localidade_nao_informada");
		}else if(Util.validarValorNaoNumerico(idLocalidade)){
			// Localidade não numérico.
			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			throw new ActionServletException("atencao.nao.numerico", null, "Localidade");
		}

		// Setor Comercial é obrigatório.
		if((codigoSetorComercial == null) || (codigoSetorComercial.equals(""))){
			httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
			throw new ActionServletException("atencao.codigo_setor_comercial_nao_informado");
		}else if(Util.validarValorNaoNumerico(codigoSetorComercial)){
			// Setor Comercial não numérico.
			httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
			throw new ActionServletException("atencao.nao.numerico", null, "Setor Comercial");
		}else{
			verificaExistenciaCodSetorComercial(idLocalidade, codigoSetorComercial, fachada, httpServletRequest);
		}

		// O código da rota é obrigatório.
		if((codigoRota == null) || (codigoRota.equals(""))){
			httpServletRequest.setAttribute("nomeCampo", "codigoRota");
			throw new ActionServletException("atencao.rota_codigo_nao_informado");
		}

		// O grupo de faturamento é obrigatório.
		if((idFaturamentoGrupo == null) || (idFaturamentoGrupo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "faturamentoGrupo");
			throw new ActionServletException("atencao.faturamento_grupo_nao_informado");
		}

		// O grupo de cobrança é obrigatório.
		if((idCobrancaGrupo == null) || (idCobrancaGrupo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "cobrancaGrupo");
			throw new ActionServletException("atencao.cobranca_grupo_nao_informado");
		}

		// O tipo de leitura é obrigatório.
		if((idLeituraTipo == null) || (idLeituraTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "leituraTipo");
			throw new ActionServletException("atencao.leitura_tipo_nao_informado");
		}

		// A empresa leituristica é obrigatória.
		if((idEmpresaLeituristica == null) || (idEmpresaLeituristica.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "empresaLeituristica");
			throw new ActionServletException("atencao.empresa_leituristica_nao_informado");
		}

		if((indicadorAjusteConsumo == null) || (indicadorAjusteConsumo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "indicadorAjusteConsumo");
			throw new ActionServletException("atencao.ajuste_consumo_nao_informado");
		}

		// O identificador de ajusta consumo é obrigatório.
		/*
		 * if ((indicadorAjusteConsumo == null)
		 * || (indicadorAjusteConsumo.equals(""
		 * + ConstantesSistema.NUMERO_NAO_INFORMADO))) {
		 * httpServletRequest.setAttribute("nomeCampo","indicadorAjusteConsumo");
		 * throw new ActionServletException(
		 * "atencao.ajusta_consumo_nao_informado");
		 * }
		 */

		// O identificador de fiscalizar cortado é obrigatório.
		if((indicadorFiscalizarCortado == null) || (indicadorFiscalizarCortado.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "indicadorFiscalizarCortado");
			throw new ActionServletException("atencao.fiscaliza_cortados_nao_informado");
		}

		// O identificador de fiscalizar suprido é obrigatório.
		if((indicadorFiscalizarSuprimido == null) || (indicadorFiscalizarSuprimido.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "indicadorFiscalizarSuprimido");
			throw new ActionServletException("atencao.fiscaliza_suprimidos_nao_informado");
		}

		// Sistema Parametro vai ser utilizado na validação de
		// Percentual de Faixa Falsa e
		// Percentual de Fiscalização de Leitura
		FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

		Collection<SistemaParametro> collectionSistemaParametro = fachada.pesquisar(filtroSistemaParametro, SistemaParametro.class
						.getName());
		SistemaParametro sistemaParametro = (SistemaParametro) collectionSistemaParametro.iterator().next();

		// O identificador de gerar faixa falsa é obrigatório.
		if((indicadorGerarFalsaFaixa == null) || (indicadorGerarFalsaFaixa.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "indicadorGerarFalsaFaixa");
			throw new ActionServletException("atencao.gera_faixa_nao_informado");
		}else if((percentualGeracaoFaixaFalsa == null || percentualGeracaoFaixaFalsa.equalsIgnoreCase(""))
						&& indicadorGerarFalsaFaixa.equals("" + ConstantesSistema.SIM)
						&& sistemaParametro.getIndicadorUsoFaixaFalsa().equals(SistemaParametro.INDICADOR_USO_FAIXA_FALSA_ROTA)){
			// Percentual de Faixa Falsa é obrigatório
			// caso o indicador de geração de fiscalização de leitura seja SIM e
			// o indicador de uso do percentual para geração de fiscalização de leitura
			// na tabela SISTEMA_PARAMETRO (PARM_ICUSOPERCENTUALFISCALIZACAOLEITURA)
			// corresponda ao valor 2=USA PERCENTUAL DA ROTA

			httpServletRequest.setAttribute("nomeCampo", "percentualGeracaoFaixaFalsa");
			throw new ActionServletException("atencao.percentual_faixa_falsa_nao_informado");
		}

		// O identificador de gerar fiscalização é obrigatório.
		if((indicadorGerarFiscalizacao == null) || (indicadorGerarFiscalizacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "indicadorGerarFiscalizacao");
			throw new ActionServletException("atencao.gera_fiscalizacao_leitura_nao_informado");
		}else if((percentualGeracaoFiscalizacao == null || percentualGeracaoFiscalizacao.equalsIgnoreCase(""))
						&& indicadorGerarFiscalizacao.equals("" + ConstantesSistema.SIM)
						&& sistemaParametro.getIndicadorPercentualFiscalizacaoLeitura().equals(
										SistemaParametro.INDICADOR_PERCENTUAL_FISCALIZACAO_LEITURA_ROTA)){
			// Percentual de Fiscalização de Leitura é obrigatório
			// caso o indicador de geração de faixa falsa seja SIM e
			// o indicador de uso do percentual para geração da faixa falsa
			// na tabela SISTEMA_PARAMETRO (PARM_ICUSOPERCENTUALFAIXAFALSA)
			// corresponda ao valor 2=USA PERCENTUAL DA ROTA
			httpServletRequest.setAttribute("nomeCampo", "percentualGeracaoFiscalizacao");
			throw new ActionServletException("atencao.percentual_fiscalizacao_leitura_nao_informado");
		}

		// // [FS0010] Verificar inexistência de alguma ação de cobrança
		// if(collectionRotaAcaoCriterioNovos == null){
		// // É necessário informar o critério de cobrança da rota para todas as ações de cobrança
		// throw new ActionServletException("atencao.criterio_cobranca_rota.informar");
		//
		// }else{
		// FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		// filtroCobrancaAcao.adicionarParametro(new
		// ParametroSimples(FiltroCobrancaCriterio.INDICADOR_USO, ConstantesSistema.SIM));
		// Collection<CobrancaGrupo> collectionCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao,
		// CobrancaAcao.class.getName());
		//
		// if(collectionRotaAcaoCriterioNovos.size() != collectionCobrancaAcao.size()){
		// // É necessário informar o critério de cobrança da rota para todas as ações de
		// // cobrança
		// throw new ActionServletException("atencao.criterio_cobranca_rota.informar");
		// }
		// }

	}

	private void verificaExistenciaCodSetorComercial(String idDigitadoEnterLocalidade, String codigoDigitadoEnterSetorComercial,
					Fachada fachada, HttpServletRequest httpServletRequest){

		// Verifica se o código do Setor Comercial foi digitado
		if((codigoDigitadoEnterSetorComercial != null && !codigoDigitadoEnterSetorComercial.toString().trim().equalsIgnoreCase(""))
						&& (idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.toString().trim().equalsIgnoreCase(""))){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			if(idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.toString().trim().equalsIgnoreCase("")){

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
								.valueOf(idDigitadoEnterLocalidade)));
			}

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
							.valueOf(codigoDigitadoEnterSetorComercial)));

			Collection<SetorComercial> setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(setorComerciais == null || setorComerciais.isEmpty()){
				// o setor comercial não foi encontrado
				// Setor Comercial não existe para esta localidade
				httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
				throw new ActionServletException("atencao.setor_comercial_nao_existe");

			}

		}

	}

}
