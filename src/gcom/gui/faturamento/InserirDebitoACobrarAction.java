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

package gcom.gui.faturamento;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaForma;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.financeiro.FiltroFinanciamentoTipo;
import gcom.financeiro.FinanciamentoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir D�bito A Cobraqr do Imovel
 * 
 * @author Rafael Santos
 * @since 21/12/2005 [UC0183] Inserir D�bito a Cobrar
 */
public class InserirDebitoACobrarAction
				extends GcomAction
				implements Parametrizacao {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		InserirDebitoACobrarActionForm inserirDebitoACobrarActionForm = (InserirDebitoACobrarActionForm) actionForm;

		Imovel imovel = null;

		if(sessao.getAttribute("imovelPesquisado") != null){

			imovel = (Imovel) sessao.getAttribute("imovelPesquisado");

		}else{

			throw new ActionServletException("atencao.imovel.inexistente");

		}

		Integer numeroPrestacoes = new Integer(inserirDebitoACobrarActionForm.getNumeroPrestacoes().replace(',', '.'));
		/*
		 * String valorTotalServicoAParcelar = inserirDebitoACobrarActionForm
		 * .getValorTotalServicoAParcelar();
		 */
		BigDecimal valorTotalServico = Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getValorTotalServico());
		BigDecimal percentualAbatimento = null;

		if(inserirDebitoACobrarActionForm.getPercentualAbatimento() != null
						&& !inserirDebitoACobrarActionForm.getPercentualAbatimento().equals("")){

			percentualAbatimento = Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getPercentualAbatimento());
		}else{
			percentualAbatimento = new BigDecimal("0.00");
		}

		String anoMesString = inserirDebitoACobrarActionForm.getAnoMesCobrancaDebito();
		Integer anoMesCobrancaDebito = null;
		if(anoMesString != null && !anoMesString.equals("")){
			anoMesCobrancaDebito = Util.formatarMesAnoComBarraParaAnoMes(anoMesString);

			// [UC0183][FS0020] Valida Refer�ncia inicial da Cobran�a
			String anoMesPermitido;
			try{

				anoMesPermitido = (String) ParametroFaturamento.P_LIMITE_MAXIMO_MESES_INICIO_COBRANCA_DEBITO_A_COBRAR.executar(this);

			}catch(ControladorException e){

				throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
								new String[e.getParametroMensagem().size()]));

			}

			if(anoMesPermitido != null && !anoMesPermitido.equals("0")){

				if(imovel != null){

					FaturamentoGrupo faturamentoGrupo = getFachada().pesquisarGrupoImovel(imovel.getId());

					if(faturamentoGrupo != null){

						Integer mesAnoPermitido = Util.formatarMesAnoComBarraParaAnoMes(Util.somaMesMesAnoComBarra(Util
										.formatarAnoMesParaMesAno(faturamentoGrupo.getAnoMesReferencia()), Util
										.obterInteger(anoMesPermitido)));

						if(anoMesCobrancaDebito > mesAnoPermitido){

							throw new ActionServletException("atencao.referencia_inicial_cobranca_superior", null, Util
											.formatarAnoMesParaMesAno(mesAnoPermitido));

						}

					}

				}

			}

		}

		BigDecimal valorEntrada = null;
		if(inserirDebitoACobrarActionForm.getValorEntrada() != null && !inserirDebitoACobrarActionForm.getValorEntrada().equals("")){

			valorEntrada = Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getValorEntrada());
		}

		String idDebitoTipo = inserirDebitoACobrarActionForm.getIdTipoDebito();

		// [FS0008] Verificar exist�ncia de d�bito a cobrar para o registro de
		// atendimento
		if((idDebitoTipo != null && !idDebitoTipo.equals(""))
						&& (inserirDebitoACobrarActionForm.getRegistroAtendimento() != null && !inserirDebitoACobrarActionForm
										.getRegistroAtendimento().equals(""))){

			FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID_REGISTRO_ATENDIMENTO,
							inserirDebitoACobrarActionForm.getRegistroAtendimento()));

			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID, idDebitoTipo));

			filtroDebitoACobrar.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
							DebitoCreditoSituacao.CANCELADA));

			Collection colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());

			if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
				throw new ActionServletException("atencao.debito_a_cobrar.ja.cadastrado");
			}
		}

		// validar debito tipo
		if((inserirDebitoACobrarActionForm.getOrdemServico() != null && !inserirDebitoACobrarActionForm.getOrdemServico().equals(""))
						&& (idDebitoTipo != null && !idDebitoTipo.equals(""))){

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, inserirDebitoACobrarActionForm
							.getOrdemServico()));

			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DEBITO_TIPO);

			Collection colecaoOrdemServico = fachada.pesquisar(filtroOrdemServico, OrdemServico.class.getName());
			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

				OrdemServico ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
				if(ordemServico.getServicoTipo().getDebitoTipo() != null){
					idDebitoTipo = ordemServico.getServicoTipo().getDebitoTipo().getId().toString();
				}
			}
		}

		DebitoACobrar debitoACobrar = new DebitoACobrar();
		debitoACobrar.setGeracaoDebito(new Date());

		debitoACobrar.setValorDebito(Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getValorTotalServicoAParcelar()));
		debitoACobrar.setNumeroPrestacaoDebito(new Short(inserirDebitoACobrarActionForm.getNumeroPrestacoes()).shortValue());

		debitoACobrar.setAnoMesCobrancaDebito(anoMesCobrancaDebito);
		debitoACobrar.setAnoMesReferenciaDebito(anoMesCobrancaDebito);
		debitoACobrar.setNumeroPrestacaoCobradas(new Short("0"));
		debitoACobrar.setCodigoSetorComercial(imovel.getSetorComercial().getCodigo());
		debitoACobrar.setNumeroQuadra(new Integer(imovel.getQuadra().getNumeroQuadra()));
		debitoACobrar.setNumeroLote(imovel.getLote());
		debitoACobrar.setNumeroSubLote(imovel.getSubLote());
		BigDecimal valorTaxa = new BigDecimal("0.00");

		if(inserirDebitoACobrarActionForm.getTaxaJurosFinanciamento() != null
						&& !inserirDebitoACobrarActionForm.getTaxaJurosFinanciamento().equalsIgnoreCase("")){

			// [UC0183][FS0019] Valida taxa de juros de financiamento
			if(!getFachada().verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_TAXA_DE_JUROS_DE_FINANCIAMENTO,
							getUsuarioLogado(httpServletRequest))){

				SistemaParametro sistemaParametro = getFachada().pesquisarParametrosDoSistema();

				BigDecimal taxaJurosFinanciamento = BigDecimal.ZERO;
				taxaJurosFinanciamento = Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getTaxaJurosFinanciamento());

				if(taxaJurosFinanciamento.compareTo(sistemaParametro.getPercentualTaxaJurosFinanciamento()) > 0){
					throw new ActionServletException("atencao.taxa_juros_financiamento_informada_nao_pode_superior", null, sistemaParametro
									.getPercentualTaxaJurosFinanciamento().toString());
				}
			}
			valorTaxa = Util.formatarMoedaRealparaBigDecimal(inserirDebitoACobrarActionForm.getTaxaJurosFinanciamento());
		}
		debitoACobrar.setPercentualTaxaJurosFinanciamento(valorTaxa);

		// registro de atendimento
		RegistroAtendimento registroAtendimento = null;
		if(inserirDebitoACobrarActionForm.getRegistroAtendimento() != null
						&& !inserirDebitoACobrarActionForm.getRegistroAtendimento().trim().equals("")){
			registroAtendimento = new RegistroAtendimento();
			registroAtendimento.setId(new Integer(inserirDebitoACobrarActionForm.getRegistroAtendimento()));
		}

		debitoACobrar.setRegistroAtendimento(registroAtendimento);

		// ordem de servico
		if(inserirDebitoACobrarActionForm.getOrdemServico() != null && !inserirDebitoACobrarActionForm.getOrdemServico().equals("")){

			OrdemServico ordemServico = new OrdemServico();
			ordemServico.setId(new Integer(inserirDebitoACobrarActionForm.getOrdemServico()));

			debitoACobrar.setOrdemServico(ordemServico);
		}else{
			debitoACobrar.setOrdemServico(null);
		}

		debitoACobrar.setImovel(imovel);
		debitoACobrar.setQuadra(imovel.getQuadra());
		debitoACobrar.setLocalidade(imovel.getLocalidade());

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		/*
		 * filtroDebitoTipo
		 * .adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
		 */
		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");

		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idDebitoTipo));
		/*
		 * filtroDebitoTipo
		 * .adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.LANCAMENTO_ITEM_CONTABIL_ID);
		 */
		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");

		Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
		DebitoTipo debitoTipo = (DebitoTipo) colecaoDebitoTipo.iterator().next();
		debitoTipo.setId(new Integer(idDebitoTipo));

		// [FS0006] - Verificar tipo de financaimento do tipo de d�bito
		if(debitoTipo.getFinanciamentoTipo().getId().intValue() != FinanciamentoTipo.SERVICO_NORMAL.intValue()
						|| debitoTipo.getIndicadorGeracaoAutomatica().shortValue() == 1){

			throw new ActionServletException("atencao.tipo_financiamento.tipo_debito.nao.permite.debito_a_cobrar", debitoTipo
							.getFinanciamentoTipo().getDescricao(), debitoTipo.getDescricao());
		}

		// obter o financiamento tipo do tipo de debito
		FiltroFinanciamentoTipo filtroFinanciamentoTipo = new FiltroFinanciamentoTipo();
		filtroFinanciamentoTipo.adicionarParametro(new ParametroSimples(FiltroFinanciamentoTipo.ID, debitoTipo.getFinanciamentoTipo()
						.getId()));

		Collection colecaoFinaciamentoTipo = fachada.pesquisar(filtroFinanciamentoTipo, FinanciamentoTipo.class.getName());

		FinanciamentoTipo financiamentoTipo = null;
		if(colecaoFinaciamentoTipo != null && !colecaoFinaciamentoTipo.isEmpty()){
			financiamentoTipo = (FinanciamentoTipo) colecaoFinaciamentoTipo.iterator().next();
		}

		debitoACobrar.setFinanciamentoTipo(financiamentoTipo);
		debitoACobrar.setDebitoTipo(debitoTipo);
		debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());

		CobrancaForma cobrancaForma = new CobrancaForma();
		cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);
		debitoACobrar.setCobrancaForma(cobrancaForma);
		// ALTERA��O NILA
		if(!GenericValidator.isBlankOrNull(inserirDebitoACobrarActionForm.getNumerosDiasSuspensao())){
			debitoACobrar.setNumeroDiasSuspensao(Util.obterInteger(inserirDebitoACobrarActionForm.getNumerosDiasSuspensao()));
		}
		debitoACobrar.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_DEBITO_A_COBRAR_INSERIR, imovel.getId(), imovel
						.getId(), new UsuarioAcaoUsuarioHelper(getUsuarioLogado(httpServletRequest),
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		registradorOperacao.registrarOperacao(debitoACobrar);
		// ------------ REGISTRAR TRANSA��O ----------------

		/**
		 * alterado por pedro alexandre dia 21/11/2006 Recupera o usu�rio logado
		 * para passar no met�do de inserir d�bito a cobrar para verificar se o
		 * usu�rio tem abrang�ncia para inserir um d�bito a cobrar para o im�vel
		 * informado.
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		fachada.inserirDebitoACobrar(numeroPrestacoes, debitoACobrar, valorTotalServico, imovel, percentualAbatimento, valorEntrada,
						usuarioLogado, false, null, null, null);
		// fachada.inserirDebitoACobrar(numeroPrestacoes,
		// debitoACobrar,valorTotalServico, imovel, percentualAbatimento,
		// valorEntrada);

		if(sessao.getAttribute("imovelPesquisado") != null){
			sessao.removeAttribute("imovelPesquisado");
		}

		/*
		 * montarPaginaSucesso(httpServletRequest, "D�bito a cobrar " +
		 * debitoTipo.getDescricao() +" para o im�vel " + imovel.getId() +"
		 * inclu�da com sucesso", "Inserir outro D�bito a Cobrar",
		 * "exibirInserirDebitoACobrarAction.do?menu=sim");
		 */

		// 6.7 caso o valor da enrada tenha sido informadao
		if(inserirDebitoACobrarActionForm.getValorEntrada() != null && !inserirDebitoACobrarActionForm.getValorEntrada().equals("")){

			StringBuffer parametros = new StringBuffer("?menu=sim");

			parametros.append("&idImovel=" + imovel.getId());

			if(debitoACobrar.getOrdemServico() != null){
				parametros.append("&ordemServico=" + debitoACobrar.getOrdemServico().getId());
			}else if(debitoACobrar.getRegistroAtendimento() != null){
				parametros.append("&registroAtendimento=" + registroAtendimento.getId());
			}

			parametros.append("&idTipoDebito=" + idDebitoTipo);
			parametros.append("&dataVencimento=" + Util.formatarData(new Date()));
			parametros.append("&valorDebito=" + inserirDebitoACobrarActionForm.getValorEntrada());

			montarPaginaSucesso(httpServletRequest, "D�bito a cobrar " + debitoTipo.getDescricao() + " para o im�vel " + imovel.getId()
							+ " inclu�do com sucesso", "Inserir outro D�bito a Cobrar", "exibirInserirDebitoACobrarAction.do?menu=sim",
							"exibirManterDebitoACobrarAction.do?idRegistroInseridoManter=" + imovel.getId(),
							"Cancelar D�bito(s) a Cobrar do im�vel " + imovel.getId(), "Inserir Guia de Pagamento",
							"exibirInserirGuiaPagamentoAction.do" + parametros.toString());

		}else{

			montarPaginaSucesso(httpServletRequest, "D�bito a cobrar " + debitoTipo.getDescricao() + " para o im�vel " + imovel.getId()
							+ " inclu�do com sucesso", "Inserir outro D�bito a Cobrar", "exibirInserirDebitoACobrarAction.do?menu=sim",
							"exibirManterDebitoACobrarAction.do?idRegistroInseridoManter=" + imovel.getId(),
							"Cancelar D�bito(s) a Cobrar do im�vel " + imovel.getId());

		}

		return retorno;
	}

	public ExecutorParametro getExecutorParametro(){

		// TODO Auto-generated method stub
		return ExecutorParametrosFaturamento.getInstancia();
	}
}
