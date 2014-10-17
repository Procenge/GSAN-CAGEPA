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

package gcom.gui.faturamento.conta;

import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdicionarCreditoRealizadoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAdicionarCreditoRealizadoConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		AdicionarCreditoRealizadoContaActionForm adicionarCreditoRealizadoContaActionForm = (AdicionarCreditoRealizadoContaActionForm) actionForm;

		// Parãmetros recebidos para adicionar um débito cobrado
		String creditoTipoID = adicionarCreditoRealizadoContaActionForm.getCreditoTipoID();
		String mesAnoCredito = adicionarCreditoRealizadoContaActionForm.getMesAnoCredito();
		String mesAnoCobranca = adicionarCreditoRealizadoContaActionForm.getMesAnoCobranca();
		String valorCredito = adicionarCreditoRealizadoContaActionForm.getValorCredito();

		if(Util.isVazioOuBrancoOuZero(valorCredito)){
			valorCredito = httpServletRequest.getParameter("valorCreditoHidden");
		}
		String idImovelSelected = adicionarCreditoRealizadoContaActionForm.getImovelID();

		String creditoOrigemID = adicionarCreditoRealizadoContaActionForm.getCreditoOrigemID();

		// Gerando o objeto CreditoRealizado que será inserido na coleção
		CreditoRealizado objCreditoRealizado = new CreditoRealizado();
		objCreditoRealizado.setUltimaAlteracao(new Date());

		// Verificação da matrícula do imóvel selecionado
		if(idImovelSelected == null || idImovelSelected.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.adicionar_debito_imovel_obrigatorio");
		}

		// Verificação do mês e ano de débito
		if(mesAnoCredito != null && !mesAnoCredito.equalsIgnoreCase("")){

			// [FS0006] - Validar ano e mês de referência
			if(Util.validarAnoMes(mesAnoCredito)){
				throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido");
			}
			// [FS0004] - Verifica ano e mês do faturamento
			else{

				// Invertendo para AnoMês e retirando a barra
				mesAnoCredito = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoCredito);

				FiltroImovel filtroImovel = new FiltroImovel();

				// Objetos que serão retornados pelo hibernate
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelSelected));

				Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				if(colecaoImovel == null || colecaoImovel.isEmpty()){
					throw new ActionServletException("atencao.adicionar_credito_ano_mes_debito_invalido");
				}else{
					Imovel objImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
					Integer mesAnoFaturamentoImovel = objImovel.getRota().getFaturamentoGrupo().getAnoMesReferencia();

					if(!compararAnoMesReferencia(mesAnoFaturamentoImovel, new Integer(mesAnoCredito), ">")){
						throw new ActionServletException("atencao.adicionar_credito_ano_mes_debito_invalido");
					}else{
						// Inserir o mesAnoReferencia do crédito no objeto final
						objCreditoRealizado.setAnoMesReferenciaCredito(new Integer(mesAnoCredito));
					}
				}
			}
		}

		// Verificação do mês e ano de cobrança
		if(mesAnoCobranca != null && !mesAnoCobranca.equalsIgnoreCase("")){

			// [FS0002] - Validar ano e mês de referência
			if(Util.validarAnoMes(mesAnoCobranca)){
				throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido");
			}
			// [FS0011] - Verifica ano e mês da cobrança
			else{

				// Invertendo para AnoMês e retirando a barra
				mesAnoCobranca = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoCobranca);

				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				if(sistemaParametro == null){
					throw new ActionServletException("atencao.adicionar_debito_ano_mes_cobranca_invalido");
				}else if(compararAnoMesReferencia(new Integer(mesAnoCobranca), new Integer(sistemaParametro.getAnoMesArrecadacao()), ">")){
					throw new ActionServletException("atencao.adicionar_debito_ano_mes_faturamento_invalido");
				}else{
					// Inserir o mesAnoCobranca do débito no objeto final
					objCreditoRealizado.setAnoMesCobrancaCredito(new Integer(mesAnoCobranca));
				}
			}
		}

		// [FS0012] - Verificar valor do crédito realizado igual a zero
		BigDecimal objValorCredito = Util.formatarMoedaRealparaBigDecimal(valorCredito);
		if(objValorCredito.equals(new BigDecimal("0"))){
			throw new ActionServletException("atencao.adicionar_debito_valor_debito_igual_zero");
		}else{

			// Inserir o valor do crédito no objeto final
			objCreditoRealizado.setValorCredito(objValorCredito);
		}

		// Realizando consulta para obter os dados do tipo do crédito selecionado
		FiltroCreditoTipo filtroCreditoTipo = new FiltroCreditoTipo();

		filtroCreditoTipo.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.ID, creditoTipoID));

		filtroCreditoTipo.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCreditoTipo = fachada.pesquisar(filtroCreditoTipo, CreditoTipo.class.getName());

		if(colecaoCreditoTipo == null || colecaoCreditoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "CREDITO_TIPO");
		}else{
			CreditoTipo objCreditoTipo = (CreditoTipo) Util.retonarObjetoDeColecao(colecaoCreditoTipo);

			// Inserindo o tipo do crédito selecionado
			objCreditoRealizado.setCreditoTipo(objCreditoTipo);
		}

		// Realizando consulta para obter os dados do crédito origem selecionado
		FiltroCreditoOrigem filtroCreditoOrigem = new FiltroCreditoOrigem();

		filtroCreditoOrigem.adicionarParametro(new ParametroSimples(FiltroCreditoOrigem.ID, creditoOrigemID));

		filtroCreditoOrigem.adicionarParametro(new ParametroSimples(FiltroCreditoOrigem.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCreditoOrigem = fachada.pesquisar(filtroCreditoOrigem, CreditoOrigem.class.getName());

		BigDecimal valorTotalDisponivel = this
						.obterValorCreditoDisponivel(objCreditoRealizado.getCreditoTipo().getId(), httpServletRequest);

		if(colecaoCreditoOrigem == null || colecaoCreditoOrigem.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "CREDITO_ORIGEM");
		}else{
			CreditoOrigem objCreditoOrigem = (CreditoOrigem) Util.retonarObjetoDeColecao(colecaoCreditoOrigem);

			if(objCreditoOrigem.getId().equals(CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)
							&& objCreditoOrigem.getIndicadorUsoLivre().equals(ConstantesSistema.NAO)){

				if(valorTotalDisponivel != null){

					// 5.1.1. Caso exista, preencher com o valor total disponível (sugerir) e
					// verificar se o valor preenchido não é maior que o total disponível, pois o
					// usuário pode alterar (Somatório de CRAR_VLCREDITO em CREDITO_A_REALIZAR com
					// CROG_ID = 1 e IMOV_ID = Id do imóvel em questão e PGHI_ID <> NULL) (ATENÇÃO:
					// Precisa fazer algum controle interno, pois o usuário pode incluir vários
					// créditos para tipos diferentes e com a mesma origem);

					if(objCreditoRealizado.getValorCredito().compareTo(valorTotalDisponivel) == 1){

						throw new ActionServletException("atencao.valor_preenchido_maior_valor_credito_disponivel", null,
										Util.formatarMoedaReal(valorTotalDisponivel));

					}else{

						valorTotalDisponivel = valorTotalDisponivel.subtract(objCreditoRealizado.getValorCredito());
						objCreditoRealizado.setCreditoARealizarGeral(this.obterCreditoARealizarGeral(Integer.valueOf(idImovelSelected),
										objCreditoRealizado.getCreditoTipo().getId(), objCreditoOrigem.getId()));

						objCreditoRealizado.setPgtAssociado(this.existePagamentoAssociado(objCreditoRealizado));

					}

				}else{
					
					// 5.1.2. Caso não exista, exibir a mensagem “Não existe crédito de devolução de
					// valores relacionado ao imóvel” e não permitir que seja incluído um crédito.

					throw new ActionServletException("atencao.nao_existe_cred_devolucao_valores_imov");

				}

			}

			// Inserindo o tipo do crédito selecionado
			objCreditoRealizado.setCreditoOrigem(objCreditoOrigem);
		}

		String pagamentoHistorico = httpServletRequest.getParameter("idPagamentoHistorico");
		if(Util.isNaoNuloBrancoZero(pagamentoHistorico)){
			objCreditoRealizado.setPgtAssociado(true);

			if(objCreditoRealizado.getPagamentoHistorico() == null){
				objCreditoRealizado.setPagamentoHistorico(new PagamentoHistorico());
			}

			objCreditoRealizado.getPagamentoHistorico().setId(Integer.valueOf(pagamentoHistorico));
		}

		// Número de prestações
		objCreditoRealizado.setNumeroPrestacao(new Short("1").shortValue());

		// Número da prestação
		objCreditoRealizado.setNumeroPrestacaoCredito(new Short("1").shortValue());

		// Colocando o objeto gerado na coleção que ficará na sessão
		if(sessao.getAttribute("colecaoCreditoRealizado") == null){
			Collection colecaoCreditoRealizado = new Vector();
			colecaoCreditoRealizado.add(objCreditoRealizado);
			sessao.setAttribute("colecaoCreditoRealizado", colecaoCreditoRealizado);

			httpServletRequest.setAttribute("reloadPage", "OK");

		}else{
			Collection colecaoCreditoRealizado = (Collection) sessao.getAttribute("colecaoCreditoRealizado");
			// [FS0014] - Verificar débito já existente
			if(!verificarCreditoJaExistente(colecaoCreditoRealizado, objCreditoRealizado)){
				colecaoCreditoRealizado.add(objCreditoRealizado);

				httpServletRequest.setAttribute("reloadPage", "OK");

			}else{
				throw new ActionServletException("atencao.adicionar_debito_ja_existente");
			}
		}

		// Limpando o formulário para inserir um novo débito
		adicionarCreditoRealizadoContaActionForm.setCreditoTipoID("");
		adicionarCreditoRealizadoContaActionForm.setMesAnoCobranca("");
		adicionarCreditoRealizadoContaActionForm.setMesAnoCredito("");
		adicionarCreditoRealizadoContaActionForm.setValorCredito("");

		/*
		 * Colocado por Raphael Rossiter em 29/03/2007
		 * Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		this.atualizarValorCreditoDisponivel(objCreditoRealizado.getCreditoTipo().getId(), valorTotalDisponivel, httpServletRequest);

		return retorno;

	}

	/**
	 * Compara dois objetos no formato anoMesReferencia de acordo com o sinal logico passado.
	 * 
	 * @param anoMesReferencia1
	 * @param anoMesReferencia2
	 * @param sinal
	 * @return um boleano
	 */
	private boolean compararAnoMesReferencia(Integer anoMesReferencia1, Integer anoMesReferencia2, String sinal){

		boolean retorno = true;

		// Separando os valores de mês e ano para realizar a comparação
		String mesReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(4, 6);
		String anoReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(0, 4);

		String mesReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(4, 6);
		String anoReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(0, 4);

		if(sinal.equalsIgnoreCase("=")){
			if(!Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))){
				retorno = false;
			}else if(!Integer.valueOf(mesReferencia1).equals(Integer.valueOf(mesReferencia2))){
				retorno = false;
			}
		}else if(sinal.equalsIgnoreCase(">")){
			if(Integer.valueOf(anoReferencia1).intValue() < Integer.valueOf(anoReferencia2).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))
							&& Integer.valueOf(mesReferencia1).intValue() <= Integer.valueOf(mesReferencia2).intValue()){
				retorno = false;
			}
		}else{
			if(Integer.valueOf(anoReferencia2).intValue() < Integer.valueOf(anoReferencia1).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia2).equals(Integer.valueOf(anoReferencia1))
							&& Integer.valueOf(mesReferencia2).intValue() <= Integer.valueOf(mesReferencia1).intValue()){
				retorno = false;
			}
		}

		return retorno;
	}

	/**
	 * [FS0014] - Caso o tipo do crédito selecionado já esteja na lista
	 * 
	 * @param colecaoCreditoRealizado
	 * @param creditoRealizadoInsert
	 * @return um boleano
	 */
	private boolean verificarCreditoJaExistente(Collection colecaoCreditoRealizado, CreditoRealizado creditoRealizadoInsert){

		boolean retorno = false;

		Iterator colecaoCreditoRealizadoIt = colecaoCreditoRealizado.iterator();
		CreditoRealizado creditoRealizadoColecao;

		while(colecaoCreditoRealizadoIt.hasNext()){
			creditoRealizadoColecao = (CreditoRealizado) colecaoCreditoRealizadoIt.next();
			if(creditoRealizadoColecao.getCreditoTipo().getId().equals(creditoRealizadoInsert.getCreditoTipo().getId())){
				retorno = true;
				break;
			}
		}

		return retorno;
	}

	private BigDecimal obterValorCreditoDisponivel(Integer idCreditoTipo, HttpServletRequest httpServletRequest){

		Map<Integer, BigDecimal> mapCreditos = (Map<Integer, BigDecimal>) httpServletRequest.getSession().getAttribute(
						ConstantesSistema.CREDITO_TOTAL_DISPONIVEL);

		return mapCreditos.get(idCreditoTipo);

	}

	private void atualizarValorCreditoDisponivel(Integer idCreditoTipo, BigDecimal valorAtualizado, HttpServletRequest httpServletRequest){

		Map<Integer, BigDecimal> mapCreditos = (Map<Integer, BigDecimal>) httpServletRequest.getSession().getAttribute(
						ConstantesSistema.CREDITO_TOTAL_DISPONIVEL);

		mapCreditos.remove(idCreditoTipo);

		mapCreditos.put(idCreditoTipo, valorAtualizado);

		httpServletRequest.getSession().setAttribute(ConstantesSistema.CREDITO_TOTAL_DISPONIVEL, mapCreditos);

	}

	private CreditoARealizarGeral obterCreditoARealizarGeral(Integer idImovel, Integer idCreditoTipo, Integer idCreditoOrigem){

		FiltroCreditoARealizar filtro = new FiltroCreditoARealizar();
		filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.IMOVEL_ID, idImovel));
		filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_ORIGEM, idCreditoOrigem));
		filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.IC_USO_LIVRE_CREDITO_ORIGEM, ConstantesSistema.NAO));
		filtro.adicionarParametro(new ParametroNaoNulo(FiltroCreditoARealizar.PAGAMENTO_HISTORICO));
		filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO, idCreditoTipo));
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.CREDITO_TIPO);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.PAGAMENTO_HISTORICO);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.CREDITO_A_REALIZAR_GERAL);

		CreditoARealizar creditoARealizar = (CreditoARealizar) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtro,
						CreditoARealizar.class.getName()));

		if(creditoARealizar != null){

			return creditoARealizar.getCreditoARealizarGeral();

		}else{

			FiltroCreditoARealizarHistorico filtroHistorico = new FiltroCreditoARealizarHistorico();
			filtroHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.IMOVE_ID, idImovel));
			filtroHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.CREDITO_ORIGEM_ID, idCreditoOrigem));
			filtroHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.IC_USO_LIVRE_CREDITO_ORIGEM,
							ConstantesSistema.NAO));
			filtroHistorico.adicionarParametro(new ParametroNaoNulo(FiltroCreditoARealizarHistorico.PAGAMENTO_HISTORICO));
			filtroHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.ID_CREDITO_TIPO, idCreditoTipo));
			filtroHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizarHistorico.CREDITO_TIPO);
			filtroHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizarHistorico.PAGAMENTO_HISTORICO);
			filtroHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizarHistorico.CREDITO_A_REALIZAR_GERAL);

			CreditoARealizarHistorico creditoARealizarHistorico = (CreditoARealizarHistorico) Util.retonarObjetoDeColecao(Fachada
							.getInstancia().pesquisar(filtroHistorico, CreditoARealizarHistorico.class.getName()));

			if(creditoARealizarHistorico != null){

				return creditoARealizarHistorico.getCreditoARealizarGeral();

			}else{

				return null;

			}

		}

	}

	private Boolean existePagamentoAssociado(CreditoRealizado creditoRealizado){

		Boolean retorno = Boolean.FALSE;

		if(creditoRealizado.getCreditoARealizarGeral() != null){

			FiltroCreditoARealizar filtroCreditoARealizar = new FiltroCreditoARealizar();
			filtroCreditoARealizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID, creditoRealizado
							.getCreditoARealizarGeral().getId()));
			filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.PAGAMENTO_HISTORICO);

			CreditoARealizar creditoARealizar = (CreditoARealizar) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroCreditoARealizar, CreditoARealizar.class.getName()));

			if(creditoARealizar != null){

				retorno = Boolean.TRUE;

			}else{

				FiltroCreditoARealizarHistorico filtroCreditoARealizarHistorico = new FiltroCreditoARealizarHistorico();
				filtroCreditoARealizarHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.ID,
								creditoRealizado.getCreditoARealizarGeral().getId()));
				filtroCreditoARealizarHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizarHistorico.PAGAMENTO_HISTORICO);

				CreditoARealizarHistorico creditoARealizarHistorico = (CreditoARealizarHistorico) Util.retonarObjetoDeColecao(Fachada
								.getInstancia().pesquisar(filtroCreditoARealizarHistorico, CreditoARealizarHistorico.class.getName()));

				if(creditoARealizarHistorico != null){

					retorno = Boolean.TRUE;

				}

			}

		}

		return retorno;

	}

}
