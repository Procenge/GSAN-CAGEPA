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

package gcom.gui.faturamento;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.DadosPrestacaoGuiaHelper;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ListaDadosPrestacaoGuiaHelper;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ExibirInserirGuiaPagamentoAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 24/07/2008
	 *       Alterações contidas no [UC0187]
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Alteração para pegar o cliente usuário do imovel que tenha dataFimRelacao igual a nulo
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirGuiaPagamento");

		InserirGuiaPagamentoActionForm inserirGuiaPagamentoActionForm = (InserirGuiaPagamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String numeroContratoParcelOrgaoPublico = null;

		// Verifica se o usuário tem permissão especial para inserir Guia de Pagamento sem RA(38)
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		boolean inserirGuiaPagamentoSemRa = Fachada.getInstancia().verificarPermissaoEspecial(
						PermissaoEspecial.INSERIR_GUIA_DE_PAGAMENTO_SEM_RA, usuarioLogado);
		httpServletRequest.setAttribute("inserirGuiaPagamentoSemRa", inserirGuiaPagamentoSemRa);

		// Carregar a data corrente do sistema
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		// Data Corrente + 60 dias
		dataCorrente.add(Calendar.DATE, 60);
		httpServletRequest.setAttribute("dataAtual60", formatoData.format(dataCorrente.getTime()));

		try{
			// Seta na sessão o indicador que será utilizado na jsp para decidir se o campo
			// "Emitir a observação do RA na guia?" será exibido.
			String paramExibirObservacaoRA = (String) ParametroFaturamento.P_INDICADOR_EMISSAO_OBSERVACAO_RA_GUIA_PAGAMENTO.executar();
			sessao.setAttribute("paramExibirObservacaoRA", paramExibirObservacaoRA);

		}catch(ControladorException e){
			e.printStackTrace();
		}

		// Carrega a página com a opção <NÃO> marcada para o campo <Emitir a observação do RA na
		// guia?>
		// if(inserirGuiaPagamentoActionForm.getIndicadorEmissaoObservacaoRA() == null
		// || inserirGuiaPagamentoActionForm.getIndicadorEmissaoObservacaoRA().equals("")){
		//
		// inserirGuiaPagamentoActionForm.setIndicadorEmissaoObservacaoRA(ConstantesSistema.NAO.toString());
		// }

		if(!Util.isVazioOuBranco(sessao.getAttribute("exibirNuContratoParcOrgaoPublico"))){
			sessao.removeAttribute("exibirNuContratoParcOrgaoPublico");
		}

		// Código do Cliente
		String codigoDigitadoClienteEnter = inserirGuiaPagamentoActionForm.getCodigoCliente();

		// Matrícula do Imóvel
		String codigoDigitadoImovelEnter = inserirGuiaPagamentoActionForm.getIdImovel();

		// Verifica se o código do imóvel foi digitado
		if(codigoDigitadoImovelEnter != null && !codigoDigitadoImovelEnter.trim().equals("")
						&& Integer.parseInt(codigoDigitadoImovelEnter) > 0){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoDigitadoImovelEnter));

			Collection imovelEncontrado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(imovelEncontrado != null && !imovelEncontrado.isEmpty()){

				// O imovel foi encontrado
				Imovel imovel = (Imovel) imovelEncontrado.iterator().next();

				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

				filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
				filtroImovelCobrancaSituacao
								.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));

				Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
								.getName());

				if(imovel.getIndicadorExclusao() == null ? false : imovel.getIndicadorExclusao().equals(Imovel.IMOVEL_EXCLUIDO)){
					throw new ActionServletException("atencao.imovel.excluido");
				}

				// Verifica se o imóvel tem débito em cobrança administrativa
				if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

					ImovelCobrancaSituacao imovelCobrancaSituacao = ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada)
									.get(0));

					if(imovelCobrancaSituacao.getCobrancaSituacao() != null){

						if(imovelCobrancaSituacao.getCobrancaSituacao().getId().equals(CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
										&& imovelCobrancaSituacao.getDataRetiradaCobranca() == null){

							// Código comentado para a customização da cobrança administrativa CASAL
							// throw new
							// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
						}
					}
				}

				inserirGuiaPagamentoActionForm.setIdImovel("" + ((Imovel) ((List) imovelEncontrado).get(0)).getId());
				inserirGuiaPagamentoActionForm.setInscricaoImovel(((Imovel) ((List) imovelEncontrado).get(0)).getInscricaoFormatada());
				inserirGuiaPagamentoActionForm.setSituacaoAgua(((Imovel) ((List) imovelEncontrado).get(0)).getLigacaoAguaSituacao()
								.getDescricao());

				inserirGuiaPagamentoActionForm.setSituacaoEsgoto(((Imovel) ((List) imovelEncontrado).get(0)).getLigacaoEsgotoSituacao()
								.getDescricao());

				inserirGuiaPagamentoActionForm.setLocalidade("" + ((Imovel) ((List) imovelEncontrado).get(0)).getLocalidade().getId());

				httpServletRequest.setAttribute("nomeCampo", "registroAtendimento");

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoDigitadoImovelEnter));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.USUARIO));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

				Collection clientesImovelEncontrado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				ClienteImovel clienteImovel = null;
				if(clientesImovelEncontrado != null && !clientesImovelEncontrado.isEmpty()){

					// O cliente imovel foi encontrado
					clienteImovel = (ClienteImovel) clientesImovelEncontrado.iterator().next();

					inserirGuiaPagamentoActionForm.setNomeClienteUsuario(clienteImovel.getCliente().getNome());
					inserirGuiaPagamentoActionForm.setCodigoCliente(clienteImovel.getCliente().getId().toString());
				}

				// inserirGuiaPagamentoActionForm.setCodigoCliente(null);
				//				
				// // Preenche o cliente , o responsável do imóvel. Caso não exista responsável,
				// exibe o Cliente usuário
				// if (codigoClienteResponsavel != null){ // seta o código do cliente para 'fazer
				// uso' da mesma consulta de tela
				// codigoDigitadoClienteEnter = codigoClienteResponsavel.toString();
				// inserirGuiaPagamentoActionForm.setCodigoCliente(codigoClienteResponsavel.toString());
				// }

			}else{
				inserirGuiaPagamentoActionForm.setIdImovel("");
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente.guia");
			}
		}

		// Verifica se o do cliente código foi digitado
		inserirGuiaPagamentoActionForm.setCpf(null);
		inserirGuiaPagamentoActionForm.setNomeCliente(null);
		inserirGuiaPagamentoActionForm.setProfissao(null);
		inserirGuiaPagamentoActionForm.setRamoAtividade(null);
		if(codigoDigitadoClienteEnter != null && !codigoDigitadoClienteEnter.trim().equals("")
						&& Integer.parseInt(codigoDigitadoClienteEnter) > 0){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("profissao");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("ramoAtividade");

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoDigitadoClienteEnter));

			Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(clienteEncontrado != null && !clienteEncontrado.isEmpty()){

				// O Cliente foi encontrado
				Cliente cliente = ((Cliente) ((List) clienteEncontrado).get(0));

				if(cliente.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
					throw new ActionServletException("atencao.cliente.inativo", null, "" + cliente.getId());
				}

				inserirGuiaPagamentoActionForm.setNomeCliente(cliente.getNome());
				httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");

				if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){

					if(cliente.getCpfFormatado() == null || cliente.getCpfFormatado().equalsIgnoreCase("null")){

						inserirGuiaPagamentoActionForm.setCpf("");
					}else{
						inserirGuiaPagamentoActionForm.setCpf(cliente.getCpfFormatado());
					}

					inserirGuiaPagamentoActionForm
									.setProfissao(cliente.getProfissao() == null ? "" : cliente.getProfissao().getDescricao());

				}else{

					if(cliente.getCnpjFormatado() == null || cliente.getCnpjFormatado().equalsIgnoreCase("null")){

						inserirGuiaPagamentoActionForm.setCpf("");

					}else{
						inserirGuiaPagamentoActionForm.setCpf(cliente.getCnpjFormatado());
					}

					inserirGuiaPagamentoActionForm.setRamoAtividade(cliente.getRamoAtividade() == null ? "" : cliente.getRamoAtividade()
									.getDescricao());
				}

			}else{

				inserirGuiaPagamentoActionForm.setCodigoCliente("");
				throw new ActionServletException("atencao.pesquisa.cliente.inexistente.guia");

			}

			httpServletRequest.setAttribute("nomeCampo", "registroAtendimento");
		}

		
		Collection colecaoGuiaPrestacoes = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");

		boolean removerCampoNuContratoParcOrgaoPublico = true;

		if(colecaoGuiaPrestacoes != null){
			Iterator iterator = colecaoGuiaPrestacoes.iterator();

			while(iterator.hasNext()){
				GuiaPagamentoPrestacaoHelper guiaPrestacao = (GuiaPagamentoPrestacaoHelper) iterator.next();

				if(!Util.isVazioOuBranco(guiaPrestacao.getId())){
					// Exibir o campo "No. Contrato Parcel. Órgão Público"
					try{

						FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
						filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, guiaPrestacao.getId()));

						Collection<DebitoTipo> colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

						if(!Util.isVazioOrNulo(colecaoDebitoTipo)){
							DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
							
							if(!ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar().equals(
											ConstantesSistema.NUMERO_NAO_INFORMADO + "")
											&& ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar().equals(
															debitoTipo.getId().toString())){
								sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.SIM);

								removerCampoNuContratoParcOrgaoPublico = false;
							}
						}
					}catch(ControladorException e){
						throw new ActionServletException(e.getMessage(), e);
					}
				}
			}

			if(!removerCampoNuContratoParcOrgaoPublico){
				sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.SIM);
			}else{
				sessao.removeAttribute("exibirNuContratoParcOrgaoPublico");
			}

		}
		


		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String idOrdemServico = inserirGuiaPagamentoActionForm.getOrdemServico();
		String idRegistroAtendimento = inserirGuiaPagamentoActionForm.getRegistroAtendimento();

		// Pesquisar Ordem Servico
		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){
			// Faz a consulta de Ordem Servico
			this.pesquisarOrdemServico(inserirGuiaPagamentoActionForm, httpServletRequest);
		}

		// Pesquisar Registro Atendimento
		if(idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")){
			// Faz a consulta de Registro Atendimento
			this.pesquisarRegistroAtendimento(inserirGuiaPagamentoActionForm, httpServletRequest);
		}

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, inserirGuiaPagamentoActionForm);

		// código para checar ou naum o Atualizar
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			inserirGuiaPagamentoActionForm.setQtdeDiasVencimento("30");
			inserirGuiaPagamentoActionForm.setNumeroTotalPrestacao("1");
		}

		// [SB0002] – Criar Lista dos Dados das Prestações da Guia
		// Cria a lista de dados das prestações da guia
		if(!Util.isVazioOrNulo(colecaoGuiaPrestacoes)){
			Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacoesGuia = null;

			Short indicador = (Short) sessao.getAttribute("indicadorDadosAlterados");
			if(!Util.isVazioOuBranco(indicador) && indicador.equals(ConstantesSistema.SIM)){
				colecaoListaDadosPrestacoesGuia = (Collection<ListaDadosPrestacaoGuiaHelper>) sessao
								.getAttribute("colecaoListaDadosPrestacoesGuia");
			}else{
				colecaoListaDadosPrestacoesGuia = this.criarListaDadosPrestacoesGuia(
								inserirGuiaPagamentoActionForm, colecaoGuiaPrestacoes);
			}

			if(!Util.isVazioOrNulo(colecaoListaDadosPrestacoesGuia)){
				Collection<DadosPrestacaoGuiaHelper> colecaoDadosPrestacaoGuiaHelper = new ArrayList<DadosPrestacaoGuiaHelper>();
				DadosPrestacaoGuiaHelper dadosPrestacaoGuiaHelper = null;
				BigDecimal valorTipoDebitoNaPrestacao = BigDecimal.ZERO;

				// A partir dos dados da lista dos dados das prestações da guia:
				for(ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuiaHelper : colecaoListaDadosPrestacoesGuia){
					Map<Integer, BigDecimal> mapalistaDadosPrestacoesGuia = listaDadosPrestacaoGuiaHelper
									.getMapValorDebitoNaPrestacaoPorTipoDebito();

					Integer[] arrayNumeroPrestacao = new Integer[mapalistaDadosPrestacoesGuia.size()];
					Date[] arrayDataVencimentoPrestacao = new Date[mapalistaDadosPrestacoesGuia.size()];
					String[] arrayidDebitoTipo = new String[mapalistaDadosPrestacoesGuia.size()];
					String[] arrayDescricaoDebitoTipo = new String[mapalistaDadosPrestacoesGuia.size()];
					String[] arrayValorTipoDebitoNaPrestacao = new String[mapalistaDadosPrestacoesGuia.size()];

					int i = 0;

					for(Integer chaveDebitoTipo : mapalistaDadosPrestacoesGuia.keySet()){
						FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
						filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, chaveDebitoTipo));
						Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
						valorTipoDebitoNaPrestacao = mapalistaDadosPrestacoesGuia.get(chaveDebitoTipo);

						if(!Util.isVazioOrNulo(colecaoDebitoTipo)){
							DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
							arrayNumeroPrestacao[i] = listaDadosPrestacaoGuiaHelper.getPrestacao();
							arrayDataVencimentoPrestacao[i] = listaDadosPrestacaoGuiaHelper.getDataVencimentoPrestacao();
							arrayidDebitoTipo[i] = debitoTipo.getId().toString();
							arrayDescricaoDebitoTipo[i] = debitoTipo.getDescricao();
							arrayValorTipoDebitoNaPrestacao[i] = valorTipoDebitoNaPrestacao.toString();
						}

						i++;
					}

					dadosPrestacaoGuiaHelper = new DadosPrestacaoGuiaHelper();
					dadosPrestacaoGuiaHelper.setNumeroPrestacao(listaDadosPrestacaoGuiaHelper.getPrestacao());
					dadosPrestacaoGuiaHelper.setDataVencimentoPrestacao(listaDadosPrestacaoGuiaHelper.getDataVencimentoPrestacao());
					dadosPrestacaoGuiaHelper.setNumeroPrestacaoArray(arrayNumeroPrestacao);
					dadosPrestacaoGuiaHelper.setDataVencimentoPrestacaoArray(arrayDataVencimentoPrestacao);
					dadosPrestacaoGuiaHelper.setIdDebitoTipo(arrayidDebitoTipo);
					dadosPrestacaoGuiaHelper.setDescricaoDebitoTipo(arrayDescricaoDebitoTipo);
					dadosPrestacaoGuiaHelper.setValorDebitoNaPrestacao(arrayValorTipoDebitoNaPrestacao);

					colecaoDadosPrestacaoGuiaHelper.add(dadosPrestacaoGuiaHelper);

				}

				// Coloca a coleção os dados gerados para exibir na tela de alteração
				sessao.setAttribute("colecaoDadosPrestacaoGuiaHelper", colecaoDadosPrestacaoGuiaHelper);

				// Coloca a coleção das listas de dados gerados
				sessao.setAttribute("colecaoListaDadosPrestacoesGuia", colecaoListaDadosPrestacoesGuia);
			}
		}

		return retorno;
	}

	/**
	 * Pesquisa Registro Atendimento
	 * 
	 * @author Rafael Pinto
	 * @date 02/11/2006
	 * @author eduardo henrique
	 * @date 25/07/2008
	 *       Adição do [FS0008] do [UC0187]
	 */
	private void pesquisarRegistroAtendimento(InserirGuiaPagamentoActionForm form, HttpServletRequest httpServletRequest){

		// Pesquisa de acordo com os parâmetros informados no filtro
		ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimentoHelper = Fachada.getInstancia().obterDadosRegistroAtendimento(
						new Integer(form.getRegistroAtendimento()));

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(obterDadosRegistroAtendimentoHelper != null && obterDadosRegistroAtendimentoHelper.getRegistroAtendimento() != null){

			// verifica se existe Guia de Pagamento para o RA encontrado

			// Obtém o objeto da coleção pesquisada
			RegistroAtendimento registroAtendimento = obterDadosRegistroAtendimentoHelper.getRegistroAtendimento();

			Integer idImovel = null;
			if(form.getIdImovel() != null && !form.getIdImovel().trim().equals("")){
				idImovel = new Integer(form.getIdImovel());
			}

			Integer idCliente = null;
			if(form.getCodigoCliente() != null && !form.getCodigoCliente().trim().equals("")){
				idCliente = new Integer(form.getCodigoCliente());
			}

			Fachada.getInstancia().validarExibirInserirGuiaPagamento(registroAtendimento, null, idImovel, idCliente);

			form.setRegistroAtendimento(registroAtendimento.getId().toString());
			form.setNomeRegistroAtendimento(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());

		}else{
			httpServletRequest.setAttribute("nomeCampo", "registroAtendimento");
			form.setRegistroAtendimento("");
			form.setNomeRegistroAtendimento("RA - Registro de Atendimento inexistente");
		}
	}

	/**
	 * Pesquisa Ordem Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 02/11/2006
	 */
	private void pesquisarOrdemServico(InserirGuiaPagamentoActionForm form, HttpServletRequest httpServletRequest){

		// Pesquisa de acordo com os parâmetros informados no filtro
		OrdemServico ordemServico = Fachada.getInstancia().recuperaOSPorId(new Integer(form.getOrdemServico()));

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(ordemServico != null){

			Integer idImovel = null;
			if(form.getIdImovel() != null && !form.getIdImovel().trim().equals("")){
				idImovel = new Integer(form.getIdImovel());
			}

			Integer idCliente = null;
			if(form.getCodigoCliente() != null && !form.getCodigoCliente().trim().equals("")){
				idCliente = new Integer(form.getCodigoCliente());
			}

			Fachada.getInstancia().validarExibirInserirGuiaPagamento(null, ordemServico, idImovel, idCliente);

			form.setOrdemServico("" + ordemServico.getId());
			form.setDescricaoOrdemServico(ordemServico.getServicoTipo().getDescricao());

			RegistroAtendimento registroAtendimento = ordemServico.getRegistroAtendimento();

			form.setRegistroAtendimento("" + registroAtendimento.getId());
			form.setNomeRegistroAtendimento(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());

			if(ordemServico.getServicoTipo().getDebitoTipo() != null){

				form.setHabilitaTipoDebito("false");
				form.setIdTipoDebito("" + ordemServico.getServicoTipo().getDebitoTipo().getId());
				form.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
			}

		}else{
			httpServletRequest.setAttribute("nomeCampo", "ordemServico");
			form.setOrdemServico("");
			form.setDescricaoOrdemServico("Ordem de serviço inexistente");

		}
	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Rafael Pinto
	 * @date 02/11/2006
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, InserirGuiaPagamentoActionForm form){

		// Registro Atendimento
		if(form.getRegistroAtendimento() != null && !form.getRegistroAtendimento().equals("") && form.getNomeRegistroAtendimento() != null
						&& !form.getNomeRegistroAtendimento().equals("")){

			httpServletRequest.setAttribute("numeroRAEncontrada", "true");
		}

		// Documento Cobrança
		if(form.getOrdemServico() != null && !form.getOrdemServico().equals("") && form.getDescricaoOrdemServico() != null
						&& !form.getDescricaoOrdemServico().equals("")){

			httpServletRequest.setAttribute("ordemServicoEncontrada", "true");
		}
	}

	/**
	 * [SB0002] – Criar Lista dos Dados das Prestações da Guia
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 20/05/2013
	 * @param colecaoGuiaPrestacaoHelper
	 * @param debitoTipo
	 */
	public Collection<ListaDadosPrestacaoGuiaHelper> criarListaDadosPrestacoesGuia(
					InserirGuiaPagamentoActionForm inserirGuiaPagamentoActionForm,
					Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPrestacaoHelper){

		Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacaoGuiaHelper = new ArrayList<ListaDadosPrestacaoGuiaHelper>();
		ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuiaHelper = null;
		Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito = null;

		Date dataVencimentoInicial = null;
		Date dataVencimentoAnterior = null;

		int qtdDiasVencimento = 0;
		int numeroTotalPrestacoes = 0;

		BigDecimal valorTipoDebitoNaPrestacao = BigDecimal.ZERO;
		// 1. O sistema cria a lista dos dados das prestações da guia
		// de acordo com as seguintes regras:

		// 1.1. Atribui o valor 1 ao Número da Prestação.
		int numeroPrestacao = 1;

		// 1.2. Enquanto o Número da Prestação for menor ou igual ao “Número de Prestações”,
		numeroTotalPrestacoes = Integer.parseInt(inserirGuiaPagamentoActionForm.getNumeroTotalPrestacao());

		while(numeroPrestacao <= numeroTotalPrestacoes){
			// o sistema atribui os dados das prestações à lista dos dados das prestações da guia:
			// ************************************************
			listaDadosPrestacaoGuiaHelper = new ListaDadosPrestacaoGuiaHelper();

			// 1.2.1. Prestação (Número da Prestação);
			listaDadosPrestacaoGuiaHelper.setPrestacao(numeroPrestacao);

			// 1.2.2. Data de Vencimento da Prestação
			// para a primeira prestação, será a Data de Vencimento informada;
			if(numeroPrestacao == 1){
				if(!Util.isVazioOuBranco(inserirGuiaPagamentoActionForm.getDataVencimento())){
					// Data de vencimento da primeira prestação
					dataVencimentoInicial = Util.converterStringParaData(inserirGuiaPagamentoActionForm.getDataVencimento());

					// Recebe a data de vencimento da primeira prestação
					// para ser incrementada nas demais prestações
					dataVencimentoAnterior = dataVencimentoInicial;

					listaDadosPrestacaoGuiaHelper.setDataVencimentoPrestacao(dataVencimentoInicial);

				}
			}else{
				if(!Util.isVazioOuBranco(dataVencimentoAnterior)
								&& !Util.isVazioOuBranco(inserirGuiaPagamentoActionForm.getQtdeDiasVencimento())){
					// para as seguintes, será a data de vencimento da prestação
					// anterior mais a Quantidade de Dias entre os Vencimentos);
					qtdDiasVencimento = Integer.parseInt(inserirGuiaPagamentoActionForm.getQtdeDiasVencimento());

					dataVencimentoAnterior = Util.adicionarNumeroDiasDeUmaData(dataVencimentoAnterior, qtdDiasVencimento);

					listaDadosPrestacaoGuiaHelper.setDataVencimentoPrestacao(dataVencimentoAnterior);
				}
			}

			// ***** 1.2.3. Para cada tipo de débito informado,
			// atribui os dados dos tipos de débito da prestação: *******
			if(!Util.isVazioOrNulo(colecaoGuiaPrestacaoHelper)){
				for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecaoGuiaPrestacaoHelper){
					// 1.2.3.1. Tipo de Débito (Tipo de Débito);
					int idTipoDebito = guiaPagamentoPrestacaoHelper.getId().intValue();

					valorTipoDebitoNaPrestacao = new BigDecimal(0.00);

					// 1.2.3.2. Valor do Débito na Prestação
					// (Valor do Débito dividido pelo Número de Prestações
					// caso o resultado não seja inteiro, truncar o valor obtido para duas
					// casas decimais e adicionar o saldo na última prestação).
					valorTipoDebitoNaPrestacao = Util.calcularValorPrestacao(guiaPagamentoPrestacaoHelper.getValorTipoDebito(),
									numeroTotalPrestacoes, numeroPrestacao);

					// Realiza o acúmulo do valor por Tipo de Débito para a Prestação
					mapValorDebitoNaPrestacaoPorTipoDebito = listaDadosPrestacaoGuiaHelper.getMapValorDebitoNaPrestacaoPorTipoDebito();

					if(!mapValorDebitoNaPrestacaoPorTipoDebito.containsKey(idTipoDebito)){
						mapValorDebitoNaPrestacaoPorTipoDebito.put(idTipoDebito, valorTipoDebitoNaPrestacao);
					}else{
						mapValorDebitoNaPrestacaoPorTipoDebito.put(idTipoDebito, valorTipoDebitoNaPrestacao);
					}
				}
			}

			// 1.2.4. Número da Prestação = Número da Prestação mais 1 (um).
			numeroPrestacao++;

			// Adiciona a lista a coleção
			colecaoListaDadosPrestacaoGuiaHelper.add(listaDadosPrestacaoGuiaHelper);
		}

		return colecaoListaDadosPrestacaoGuiaHelper;
	}

}
