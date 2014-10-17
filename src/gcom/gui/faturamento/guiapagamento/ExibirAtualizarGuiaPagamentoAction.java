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

package gcom.gui.faturamento.guiapagamento;

import gcom.arrecadacao.pagamento.*;
import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0188] Manter Guia de Pagamento
 * [SB0001 – Exibir Prestações da Guia de Pagamento]
 * 
 * @author Anderson Italo, Hugo Lima
 * @date 01/11/2011, 02/01/2011
 */
public class ExibirAtualizarGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("guiaPagamentoAtualizar");
		AtualizarGuiaPagamentoActionForm formulario = (AtualizarGuiaPagamentoActionForm) actionForm;
		formulario.reset();
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		String idGuiaPagamento = null;

		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){

			idGuiaPagamento = httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{

			idGuiaPagamento = ((Integer) sessao.getAttribute("idRegistroAtualizacao")).toString();
		}

		// Exibir o campo "No. Contrato Parcel. Órgão Público"
		try{
			if(!ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar()
							.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.SIM);
			}else{
				sessao.removeAttribute("exibirNuContratoParcOrgaoPublico");
			}

		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		if(!Util.isVazioOuBranco(idGuiaPagamento) && Util.obterInteger(idGuiaPagamento).intValue() > 0){

			// Pesquisa as prestações da guia de pagamento
			Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = fachada.pesquisarGuiasPagamentoPrestacaoFiltrar(Integer
							.valueOf(idGuiaPagamento));

			// [FS0007] – Verificar existência das prestações da guia de pagamento
			if(Util.isVazioOrNulo(colecaoGuiasPrestacoes)){
				throw new ActionServletException("atencao.guia_sem_prestacoes");
			}

			// monta os hints com as descricoes dos tipos de debitos
			this.montarHintGuiaPagamentoPrestacaoHelper(colecaoGuiasPrestacoes);

			FiltroGuiaPagamentoGeral filtroGuiaPagamentoGeral = new FiltroGuiaPagamentoGeral();
			filtroGuiaPagamentoGeral.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoGeral.ID, idGuiaPagamento));
			filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO);
			filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_HISTORICO);
			filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.PRESTACOES_GUIA_PAGAMENTO);

			Collection<GuiaPagamentoGeral> colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamentoGeral, GuiaPagamentoGeral.class
							.getName());

			GuiaPagamentoGeral guiaPagamentoGeral = null;
			if(!Util.isVazioOrNulo(colecaoGuiaPagamento)){

				guiaPagamentoGeral = (GuiaPagamentoGeral) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
			}

			Imovel imovelGuia = null;
			Cliente clienteGuia = null;
			if(guiaPagamentoGeral.getGuiaPagamento() != null){

				if(guiaPagamentoGeral.getGuiaPagamento().getImovel() != null){

					imovelGuia = guiaPagamentoGeral.getGuiaPagamento().getImovel();
				}

				if(guiaPagamentoGeral.getGuiaPagamento().getCliente() != null){

					clienteGuia = guiaPagamentoGeral.getGuiaPagamento().getCliente();
				}
			}else{

				if(guiaPagamentoGeral.getGuiaPagamentoHistorico().getImovel() != null){

					imovelGuia = guiaPagamentoGeral.getGuiaPagamentoHistorico().getImovel();
				}

				if(guiaPagamentoGeral.getGuiaPagamentoHistorico().getCliente() != null){

					clienteGuia = guiaPagamentoGeral.getGuiaPagamentoHistorico().getCliente();
				}
			}

			// Caso a guia esteja associada a um imóvel
			if(imovelGuia != null){

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelGuia.getId()));

				Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				imovelGuia = (Imovel) colecaoImovel.iterator().next();

				formulario.setIdImovel(imovelGuia.getId().toString());
				formulario.setInscricaoImovel(imovelGuia.getInscricaoFormatada());
				formulario.setSituacaoAgua(imovelGuia.getLigacaoAguaSituacao().getDescricao());
				formulario.setSituacaoEsgoto(imovelGuia.getLigacaoEsgotoSituacao().getDescricao());

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovelGuia.getId()));
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);

				Collection clientesImovelEncontrado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
				ClienteImovel clienteImovel = null;

				if(clientesImovelEncontrado != null && !clientesImovelEncontrado.isEmpty()){

					Iterator clienteImovelEncontradoIterator = clientesImovelEncontrado.iterator();

					while(clienteImovelEncontradoIterator.hasNext()){

						clienteImovel = (ClienteImovel) clienteImovelEncontradoIterator.next();

						if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.USUARIO.intValue()
										&& clienteImovel.getDataFimRelacao() == null){
							break;
						}
					}

					formulario.setNomeClienteUsuario(clienteImovel.getCliente().getNome());
				}

				// Pesquisa o relacionamento com um cliente Responsável
				Collection clientesGuiaPagamento = null;

				if(guiaPagamentoGeral.getGuiaPagamento() != null){

					FiltroClienteGuiaPagamento filtroClienteGuiaPagamento = new FiltroClienteGuiaPagamento();
					filtroClienteGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteGuiaPagamento.CLIENTE);
					filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.GUIA_PAGAMENTO_ID,
									idGuiaPagamento));
					filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.CLIENTE_RELACAO_TIPO_ID,
									ClienteRelacaoTipo.RESPONSAVEL));

					clientesGuiaPagamento = fachada.pesquisar(filtroClienteGuiaPagamento, ClienteGuiaPagamento.class.getName());
				}else if(guiaPagamentoGeral.getGuiaPagamentoHistorico() != null){

					// Caso a guia esteja histórico
					FiltroClienteGuiaPagamentoHistorico filtroClienteGuiaPagamentoHistorico = new FiltroClienteGuiaPagamentoHistorico();
					filtroClienteGuiaPagamentoHistorico
									.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteGuiaPagamentoHistorico.CLIENTE);
					filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
									FiltroClienteGuiaPagamentoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
					filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
									FiltroClienteGuiaPagamentoHistorico.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.RESPONSAVEL));

					clientesGuiaPagamento = fachada.pesquisar(filtroClienteGuiaPagamentoHistorico, ClienteGuiaPagamentoHistorico.class
									.getName());
				}

				if(!Util.isVazioOrNulo(clientesGuiaPagamento)){

					Object clienteGuiaPagamento = Util.retonarObjetoDeColecao(clientesGuiaPagamento);

					if(clienteGuiaPagamento instanceof ClienteGuiaPagamento){

						formulario.setNomeClienteResponsavel(((ClienteGuiaPagamento) clienteGuiaPagamento).getCliente().getNome());
					}else if(clienteGuiaPagamento instanceof ClienteGuiaPagamentoHistorico){

						formulario.setNomeClienteResponsavel(((ClienteGuiaPagamentoHistorico) clienteGuiaPagamento).getCliente().getNome());
					}
				}

			}

			if(clienteGuia != null){

				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

				Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				clienteGuia = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

				formulario.setCodigoCliente(clienteGuia.getId().toString());

				// verifica se cliente é pessoa física ou jurídica
				if(clienteGuia.getClienteTipo().getIndicadorPessoaFisicaJuridica().shortValue() == ClienteTipo.INDICADOR_PESSOA_FISICA){

					formulario.setCpf(clienteGuia.getCpfFormatado());

					FiltroCliente filtroClienteCompleto = new FiltroCliente();
					filtroClienteCompleto.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
					filtroClienteCompleto.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteCompleto, Cliente.class
									.getName()));

					// Profissão
					String descricaoProfissao = "";

					Profissao profissao = cliente.getProfissao();

					if(profissao != null){

						descricaoProfissao = profissao.getDescricao();
					}

					formulario.setProfissao(descricaoProfissao);
				}else{

					formulario.setCpf(clienteGuia.getCnpjFormatado());

					FiltroCliente filtroClienteCompleto = new FiltroCliente();
					filtroClienteCompleto.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RAMO_ATIVIDADE);
					filtroClienteCompleto.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteCompleto, Cliente.class
									.getName()));

					// Ramo de Atividade
					String descricaoRamoAtividade = "";

					RamoAtividade ramoAtividade = cliente.getRamoAtividade();

					if(ramoAtividade != null){

						descricaoRamoAtividade = ramoAtividade.getDescricao();
					}

					formulario.setRamoAtividade(descricaoRamoAtividade);
				}

				formulario.setNomeCliente(clienteGuia.getNome());

			}

			if(!Util.isVazioOrNulo(colecaoGuiasPrestacoes)){

				sessao.setAttribute("colecaoGuiasPrestacoes", colecaoGuiasPrestacoes);
			}

			if(sessao.getAttribute("colecaoGuiaPagamento") != null){

				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarGuiaPagamentoAction.do");
			}else{

				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarGuiaPagamentoAction.do");
			}

		}

		return retorno;
	}

	/**
	 * [UC0188] Manter Guia de Pagamento
	 * [SB0001 – Exibir Prestações da Guia de Pagamento]
	 * 
	 * @author Hugo Lima
	 * @date 02/01/2011
	 */
	private void montarHintGuiaPagamentoPrestacaoHelper(Collection<GuiaPagamentoPrestacaoHelper> colecao){

		String hint = "";
		Collection colecaoGuiaPagamentoPrestacao = null;
		String idDebitoCreditoSituacao = "0";

		for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecao){

			// INICIO - Rotina que preenche os hints de Tipo de débito
			// verifica se a prestação está no histórico
			if(Util.isVazioOuBranco(guiaPagamentoPrestacaoHelper.getIdOcorrenciaHistorico())
							|| !guiaPagamentoPrestacaoHelper.getIdOcorrenciaHistorico().equals(ConstantesSistema.SIM)){

				FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
				filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
								guiaPagamentoPrestacaoHelper.getIdGuiaPagamento()));
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
								guiaPagamentoPrestacaoHelper.getNumeroPrestacao()));

				colecaoGuiaPagamentoPrestacao = Fachada.getInstancia().pesquisar(filtroGuiaPagamentoPrestacao,
								GuiaPagamentoPrestacao.class.getName());
			}else{

				FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
				filtroGuiaPagamentoPrestacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacaoHistorico.DEBITO_TIPO);
				filtroGuiaPagamentoPrestacaoHistorico
								.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID,
												guiaPagamentoPrestacaoHelper.getIdGuiaPagamento()));
				filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroGuiaPagamentoPrestacaoHistorico.NUMERO_PRESTACAO, guiaPagamentoPrestacaoHelper.getNumeroPrestacao()));

				colecaoGuiaPagamentoPrestacao = Fachada.getInstancia().pesquisar(filtroGuiaPagamentoPrestacaoHistorico,
								GuiaPagamentoPrestacaoHistorico.class.getName());
			}

			if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacao)){

				for(Object object : colecaoGuiaPagamentoPrestacao){

					if(object instanceof GuiaPagamentoPrestacao){

						hint = hint + ((GuiaPagamentoPrestacao) object).getDebitoTipo().getId() + " - "
										+ ((GuiaPagamentoPrestacao) object).getDebitoTipo().getDescricao() + "\n";

						idDebitoCreditoSituacao = ((GuiaPagamentoPrestacao) object).getDebitoCreditoSituacao().getId().toString();
					}else if(object instanceof GuiaPagamentoPrestacaoHistorico){

						hint = hint + ((GuiaPagamentoPrestacaoHistorico) object).getDebitoTipo().getId() + " - "
										+ ((GuiaPagamentoPrestacaoHistorico) object).getDebitoTipo().getDescricao() + "\n";
						idDebitoCreditoSituacao = ((GuiaPagamentoPrestacaoHistorico) object).getDebitoCreditoSituacao().getId().toString();
					}

				}

			}

			guiaPagamentoPrestacaoHelper.setDebitoTipoHint(hint);
			guiaPagamentoPrestacaoHelper.setIdDebitoCreditoSituacao(idDebitoCreditoSituacao);

			hint = "";
			// FIM - Rotina que preenche os hints de Tipo de débito

			// INICIO - Rotina que preenche os hints de Indicador de Pagamento
			if(guiaPagamentoPrestacaoHelper.getIndicadorPagamento().intValue() == ConstantesSistema.SIM.intValue()){

				FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
				filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
								guiaPagamentoPrestacaoHelper.getIdGuiaPagamento()));
				filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.NUMERO_PRESTACAO,
								guiaPagamentoPrestacaoHelper.getNumeroPrestacao()));

				Collection colecaoPagamentoHistorico = Fachada.getInstancia().pesquisar(filtroPagamentoHistorico,
								PagamentoHistorico.class.getName());

				if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){

					guiaPagamentoPrestacaoHelper.setIndicadorPagamentoHint(Util.formatarData(((PagamentoHistorico) Util
									.retonarObjetoDeColecao(colecaoPagamentoHistorico)).getDataPagamento())
									+ " - "
									+ Util.formatarMoedaReal(((PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistorico))
													.getValorPagamento()));
				}
			}
			// FIM - Rotina que preenche os hints de Indicador de Pagamento
		}
	}
}