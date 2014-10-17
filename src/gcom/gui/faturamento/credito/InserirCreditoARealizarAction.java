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

package gcom.gui.faturamento.credito;

import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0194] Cr�dito a Realizar Permite inserir um cr�dito a realizar
 * 
 * @author Roberta Costa
 * @since 11/01/2006
 */
public class InserirCreditoARealizarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Pega uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		// Inst�ncia do formul�rio que est� sendo utilizado
		InserirCreditoARealizarActionForm inserirCreditoARealizarActionForm = (InserirCreditoARealizarActionForm) actionForm;

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CREDITO_A_REALIZAR_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CREDITO_A_REALIZAR_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		// Campos do Formul�rio
		Integer tipoCredito = new Integer(inserirCreditoARealizarActionForm.getTipoCredito());
		Integer origemCredito = new Integer(inserirCreditoARealizarActionForm.getOrigemCredito());
		String valorCreditoAntes = inserirCreditoARealizarActionForm.getValorCredito().toString().replace(".", "");
		String valorCreditoHidden = httpServletRequest.getParameter("valorCreditoHidden");
		if(Util.isVazioOuBranco(valorCreditoAntes)){
			valorCreditoAntes = valorCreditoHidden;
		}

		String valorCredito = valorCreditoAntes.replace(',', '.');
		String registroAtendimentoForm = inserirCreditoARealizarActionForm.getRegistroAtendimento();
		String matriculaImovel = inserirCreditoARealizarActionForm.getMatriculaImovel();
		String referencia = inserirCreditoARealizarActionForm.getReferencia();

		String qtdPrestacoes = inserirCreditoARealizarActionForm.getNumeroPrestacoes().trim();
		Short numeroPrestacoes = 1;
		if(qtdPrestacoes != null && !qtdPrestacoes.equals("") && !qtdPrestacoes.equals("0")){
			numeroPrestacoes = new Short(inserirCreditoARealizarActionForm.getNumeroPrestacoes());
		}

		// Criando o objeto creditoARealizar
		CreditoARealizar creditoARealizar = new CreditoARealizar();

		// Criando o objeto Cr�dito Tipo
		CreditoTipo creditoTipo = new CreditoTipo();
		creditoTipo.setId(tipoCredito);

		creditoARealizar.setCreditoTipo(creditoTipo);

		// Criando o objeto Cr�dito Origem
		CreditoOrigem creditoOrigem = new CreditoOrigem();
		creditoOrigem.setId(origemCredito);

		creditoARealizar.setCreditoOrigem(creditoOrigem);

		// Criando o objeto Registro Atendimento
		RegistroAtendimento registroAtendimento = new RegistroAtendimento();

		if(registroAtendimentoForm != null && !registroAtendimentoForm.equals("")){
			registroAtendimento.setId(new Integer(registroAtendimentoForm));

			creditoARealizar.setRegistroAtendimento(registroAtendimento);
		}else{
			// inseri o valor 1 temporariamente
			RegistroAtendimento registroAtendimentoConstante = new RegistroAtendimento();
			registroAtendimentoConstante.setId(RegistroAtendimento.CONSTANTE);
			creditoARealizar.setRegistroAtendimento(registroAtendimentoConstante);
		}

		if(inserirCreditoARealizarActionForm.getOrdemServico() != null && !inserirCreditoARealizarActionForm.getOrdemServico().equals("")){
			Integer ordemServicoForm = new Integer(inserirCreditoARealizarActionForm.getOrdemServico());

			// Criando o objeto Ordem de Servi�o
			OrdemServico ordemServico = new OrdemServico();
			ordemServico.setId(ordemServicoForm);

			creditoARealizar.setOrdemServico(ordemServico);
		}else{
			creditoARealizar.setOrdemServico(null);
		}

		// [FS0006] Verificar exist�ncia de credito a realizar para o registro de atendimento
		if((inserirCreditoARealizarActionForm.getTipoCredito() != null && !inserirCreditoARealizarActionForm.getTipoCredito().equals(""))
						&& (inserirCreditoARealizarActionForm.getRegistroAtendimento() != null && !inserirCreditoARealizarActionForm
										.getRegistroAtendimento().equals(""))){
			FiltroCreditoARealizar filtroCreditoARelizar = new FiltroCreditoARealizar();
			filtroCreditoARelizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_REGISTRO_ATENDIMENTO,
							inserirCreditoARealizarActionForm.getRegistroAtendimento()));
			filtroCreditoARelizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO,
							inserirCreditoARealizarActionForm.getTipoCredito()));
			filtroCreditoARelizar.adicionarParametro(new ParametroSimplesDiferenteDe(
							FiltroCreditoARealizar.DEBITO_CREDITO_SITUACAO_ATUAL_ID, DebitoCreditoSituacao.CANCELADA));

			Collection colecaoCreditoARealizar = fachada.pesquisar(filtroCreditoARelizar, CreditoARealizar.class.getName());

			if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
				throw new ActionServletException("atencao.existe.credito_a_realizar.para.registro_atendimento");
			}
		}

		// Pega o Im�vel da sess�o
		Imovel imovel = null;
		if(sessao.getAttribute("imovelPesquisado") != null){
			imovel = (Imovel) sessao.getAttribute("imovelPesquisado");
			sessao.removeAttribute("imovelPesquisado");
		}else{
			if(matriculaImovel != null && !matriculaImovel.trim().equals("")){
				// Pesquisa o imovel na base
				FiltroImovel filtroImovel = new FiltroImovel();

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, matriculaImovel));

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

				/*
				 * filtroImovel
				 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao.id");
				 * filtroImovel
				 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.id");
				 */

				Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
				// Inxistente
				if(imovelPesquisado != null && imovelPesquisado.isEmpty()){
					throw new ActionServletException("atencao.imovel.inexistente");
				}

				// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
				// Excluido
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
					imovel = imovelPesquisado.iterator().next();
					if(imovel.getIndicadorExclusao() == Imovel.IMOVEL_EXCLUIDO){
						throw new ActionServletException("atencao.pesquisa.imovel.excluido");
					}
				}

				// [FS0002 - Verificar usu�rio com d�bito em cobran�a administrativa
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
					imovel = imovelPesquisado.iterator().next();
					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel
									.getId()));

					Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao,
									ImovelCobrancaSituacao.class.getName());

					// Verifica se o im�vel tem d�bito em cobran�a administrativa
					if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

						if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao() != null){

							if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao().getId()
											.equals(CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
											&& ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0))
															.getDataRetiradaCobranca() == null){

								// C�digo comentado para a customiza��o da cobran�a administrativa
								// CASAL
								// throw new
								// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
							}
						}
					}
				}

				// [FS0003 - Verificar situa��o liga��o de �giua e esgoto]
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
					imovel = imovelPesquisado.iterator().next();
					if((imovel.getLigacaoAguaSituacao() != null)
									&& ((imovel.getLigacaoAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL) || (imovel
													.getLigacaoEsgotoSituacao().getId() == LigacaoAguaSituacao.FACTIVEL))
									&& (imovel.getLigacaoEsgotoSituacao().getId() != LigacaoEsgotoSituacao.LIGADO)){
						throw new ActionServletException("atencao.pesquisa.imovel.inativo");
					}
				}

				// Obtem o cliente imovel do imovel pesquisado
				if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

					imovel = imovelPesquisado.iterator().next();
				}
			}
		}

		if(referencia != null && !referencia.equals("")){
			Integer referenciaCredito = Util.formatarMesAnoComBarraParaAnoMes(referencia);

			creditoARealizar.setAnoMesReferenciaCredito(referenciaCredito);
		}

		creditoARealizar.setNumeroPrestacaoCredito(numeroPrestacoes);

		// Setando as informa��es do Im�vel
		creditoARealizar.setImovel(imovel);
		creditoARealizar.setCodigoSetorComercial(imovel.getSetorComercial().getCodigo());
		creditoARealizar.setNumeroQuadra(new Integer(imovel.getQuadra().getNumeroQuadra()));
		creditoARealizar.setNumeroLote(imovel.getLote());
		creditoARealizar.setNumeroSubLote(imovel.getSubLote());
		creditoARealizar.setQuadra(imovel.getQuadra());
		creditoARealizar.setLocalidade(imovel.getLocalidade());
		creditoARealizar.setValorCredito(new BigDecimal(valorCredito));
		PagamentoHistorico pagamentoHistorico = new PagamentoHistorico();

		String idPagamentoHistorico = httpServletRequest.getParameter("idPagamentoHistorico");
		if(Util.isNaoNuloBrancoZero(idPagamentoHistorico)){
			pagamentoHistorico.setId(Integer.valueOf(idPagamentoHistorico));
			creditoARealizar.setPagamentoHistorico(pagamentoHistorico);
		}

		// ------------ REGISTRAR TRANSA��O ----------------
		creditoARealizar.setOperacaoEfetuada(operacaoEfetuada);
		creditoARealizar.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(creditoARealizar);
		// ------------ REGISTRAR TRANSA��O ----------------

		fachada.inserirCreditoARealizar(imovel, creditoARealizar, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Cr�dito a Realizar do Im�vel " + matriculaImovel + " inserido com sucesso.",
						"Inserir outro Cr�dito a Realizar", "exibirInserirCreditoARealizarAction.do?menu=sim",
						"exibirManterCreditoARealizarAction.do?codigoImovel=" + matriculaImovel,
						"Cancelar Cr�dito(s) a Realizar do Im�vel " + matriculaImovel);

		return retorno;
	}
}