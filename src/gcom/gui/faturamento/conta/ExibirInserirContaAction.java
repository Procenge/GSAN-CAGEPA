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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.conta.FiltroMotivoInclusaoConta;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		/*
		 * Colocado por Raphael Rossiter em 29/03/2007
		 * Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a
		 * empresa
		 */
		// SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		// httpServletRequest.setAttribute("empresaNome",
		// sistemaParametro.getNomeAbreviadoEmpresa().trim());

		// Instância do formulário que está sendo utilizado
		InserirContaActionForm inserirContaActionForm = (InserirContaActionForm) actionForm;
		String limparForm = httpServletRequest.getParameter("limparForm");

		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "INSERIRCONTA");

		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();

		Collection colecaoCategoria = new ArrayList();

		// Removendo coleções da sessão
		if(limparForm != null && !limparForm.equalsIgnoreCase("")){
			sessao.removeAttribute("colecaoCategoria");
			sessao.removeAttribute("colecaoDebitoCobrado");
			sessao.removeAttribute("colecaoAdicionarCategoria");
			sessao.removeAttribute("colecaoAdicionarDebitoTipo");
		}

		Collection colecaoMotivoInclusaoConta, colecaoSituacaoLigacaoAgua, colecaoSituacaoLigacaoEsgoto;

		// Carregar a data corrente do sistema
		// ====================================
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		// Data Corrente + quantidade máxima de dias para pagamento de conta
		// Pesquisa o parâmetro contendo a quantidade maxíma de dias para o vencimento de uma conta
		try{
			String nuMaximoDiasVencimentoConta = ParametroFaturamento.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA.executar();
			dataCorrente.add(Calendar.DATE, Integer.parseInt(nuMaximoDiasVencimentoConta));
			httpServletRequest.setAttribute("dataMaximaVencimento", formatoData.format(dataCorrente.getTime()));
			httpServletRequest.setAttribute("parametroQuantMaxDiasVenc", nuMaximoDiasVencimentoConta);
		}catch(ControladorException e){
			throw new ActionServletException("erro.sistema");
		}

		/*
		 * Costante que informa o ano limite para o campo anoMesReferencia da conta
		 */
		httpServletRequest.setAttribute("anoLimite", ConstantesSistema.ANO_LIMITE);

		/*
		 * Motivo da inclusão (Carregar coleção) e remover as coleções que ainda estão na sessão
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoMotivoInclusaoConta") == null){

			FiltroMotivoInclusaoConta filtroMotivoInclusaoConta = new FiltroMotivoInclusaoConta(
							FiltroMotivoInclusaoConta.MOTIVO_INCLUSAO_CONTA);

			filtroMotivoInclusaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoInclusaoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoMotivoInclusaoConta = fachada.pesquisar(filtroMotivoInclusaoConta, ContaMotivoInclusao.class.getName());

			if(colecaoMotivoInclusaoConta == null || colecaoMotivoInclusaoConta.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_INCLUSAO_CONTA");
			}else{
				sessao.setAttribute("colecaoMotivoInclusaoConta", colecaoMotivoInclusaoConta);
			}
		}

		/*
		 * Situação Ligação Água (Carregar coleção)
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoSituacaoLigacaoAgua") == null){

			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoSituacaoLigacaoAgua = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

			if(colecaoSituacaoLigacaoAgua == null || colecaoSituacaoLigacaoAgua.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LIGACAO_AGUA_SITUACAO");
			}else{
				sessao.setAttribute("colecaoSituacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
			}
		}

		/*
		 * Situação Ligação Esgoto (Carregar coleção)
		 * ======================================================================
		 */
		if(sessao.getAttribute("colecaoSituacaoLigacaoEsgoto") == null){

			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(FiltroLigacaoEsgotoSituacao.DESCRICAO);

			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoSituacaoLigacaoEsgoto = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

			if(colecaoSituacaoLigacaoEsgoto == null || colecaoSituacaoLigacaoEsgoto.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LIGACAO_ESGOTO_SITUACAO");
			}else{
				sessao.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
			}
		}

		// Verifica se existe a coleção de ligação de esgoto perfil
		if(sessao.getAttribute("colecaoLigacaoEsgotoPerfil") == null){

			FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
			filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

			// Verificar existência de dados
			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoPerfil)){

				sessao.setAttribute("colecaoLigacaoEsgotoPerfil", colecaoLigacaoEsgotoPerfil);
			}else{

				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Ligação Esgoto Perfil");
			}
		}

		/*
		 * Pesquisar o imóvel a partir da matrícula do imóvel
		 * ======================================================================
		 */
		String idImovel = inserirContaActionForm.getIdImovel();
		String reloadPage = httpServletRequest.getParameter("reloadPage");

		if(idImovel != null && !idImovel.equalsIgnoreCase("") && (reloadPage == null || reloadPage.equalsIgnoreCase(""))){

			FiltroImovel filtroImovel = new FiltroImovel();

			// Objetos que serão retornados pelo hobernate
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			/*
			 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial.codigo");
			 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra.numeroQuadra");
			 * 
			 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao.descricao")
			 * ;
			 * 
			 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.descricao"
			 * );
			 * filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto.percentual");
			 */
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaTemporaria");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaTemporaria.consumoTarifaVigencias");

			// Realizando a pesquisa do imóvel a partir da matrícula recebida
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			/*
			 * Apenas imóveis que não foram excluídos poderão inserir conta
			 * (IMOV_ICEXCLUSAO = 1)
			 */
			filtroImovel
							.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO,
											Imovel.IMOVEL_EXCLUIDO));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			// [FS0001] - Verificar existência da matrícula do imóvel
			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				/*
				 * throw new ActionServletException(
				 * "atencao.imovel.inexistente");
				 */

				httpServletRequest.setAttribute("corInscricao", "exception");
				inserirContaActionForm.setIdImovel("");
				inserirContaActionForm.setInscricaoImovel("Matrícula Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}else{

				Imovel objetoImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				/*
				 * Pesquisar o cliente usuário do imóvel selecionado
				 * ======================================================================
				 */
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

				// Objetos que serão retornados pelo hibernate.
				// filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.nome");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
								ClienteRelacaoTipo.USUARIO));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));

				Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				// Verifica existência do cliente usuátio
				if(colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "cliente do tipo usuário foi");
				}

				ClienteImovel objetoClienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

				// Carregando as informações do imóvel no formulário de exibição.
				inserirContaActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());
				inserirContaActionForm.setNomeClienteUsuario(objetoClienteImovel.getCliente().getNome());
				inserirContaActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());
				inserirContaActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());
				inserirContaActionForm.setSituacaoAguaConta(String.valueOf(objetoImovel.getLigacaoAguaSituacao().getId().intValue()));
				inserirContaActionForm.setSituacaoEsgotoConta(String.valueOf(objetoImovel.getLigacaoEsgotoSituacao().getId().intValue()));
				if(objetoImovel.getLigacaoEsgoto() != null){
					if(objetoImovel.getLigacaoEsgoto().getPercentual() != null){
						inserirContaActionForm.setPercentualEsgoto(Util.formatarMoedaReal(objetoImovel.getLigacaoEsgoto().getPercentual()));
					}
				}

				// Caso a situação da Ligação de Esgoto seja faturavel, habilita campos perfil,
				// consumo e percentual dos dados da ligação de esgoto
				inserirContaActionForm.setIndicadorEsgotoFaturavel(objetoImovel.getLigacaoEsgotoSituacao()
								.getIndicadorFaturamentoSituacao().toString());

				inserirContaActionForm.setIndicadorAguaFaturavel(objetoImovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao()
								.toString());

				// Inicializa o formulário
				inserirContaActionForm.setValorAgua("");
				inserirContaActionForm.setValorEsgoto("");
				inserirContaActionForm.setValorDebito("");
				inserirContaActionForm.setValorTotal("");
				inserirContaActionForm.setConsumoAgua("");
				inserirContaActionForm.setConsumoEsgoto("");

				FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

				sessao.setAttribute("colecaoTarifa", getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName()));

				// [FS0023] - Obter Tarifa vigente para o Imóvel
				Integer valorComparacaoDataValidadeTarifaTemporaria = null;
				Integer tarifaTemporaria = 2;

				if(objetoImovel.getDataValidadeTarifaTemporaria() != null){
					valorComparacaoDataValidadeTarifaTemporaria = Util.compararData(objetoImovel.getDataValidadeTarifaTemporaria(),
									new Date());
				}

				sessao.setAttribute("valorComparacaoDataValidadeTarifaTemporaria", valorComparacaoDataValidadeTarifaTemporaria);

				if(objetoImovel.getDataValidadeTarifaTemporaria() != null && objetoImovel.getConsumoTarifaTemporaria().getId() != null
								&& valorComparacaoDataValidadeTarifaTemporaria != -1){
					inserirContaActionForm.setTarifaID(objetoImovel.getConsumoTarifaTemporaria().getId().toString());
					tarifaTemporaria = 1;
				}else{
					inserirContaActionForm.setTarifaID(objetoImovel.getConsumoTarifa().getId().toString());
				}

				sessao.setAttribute("tarifaTemporaria", tarifaTemporaria);

				// [UC0108] - Quantidade de economias por categoria
				colecaoCategoria = fachada.obterQuantidadeEconomiasCategoria(objetoImovel);

				sessao.setAttribute("colecaoCategoria", colecaoCategoria);

				// Remove a coleção de debitos que foi selecionada com a matricula do imóvel
				// anterior
				// sessao.removeAttribute("colecaoDebitoCobrado");

				// Colocando o faco pra o campo de ano mês referencia
				httpServletRequest.setAttribute("nomeCampo", "mesAnoConta");
			}
		}
		if(sessao.getAttribute("colecaoCategoria") != null){
			Collection colecao = (Collection) sessao.getAttribute("colecaoCategoria");
			Iterator iteratorColecaoCategoria = colecao.iterator();

			Categoria categoria = null;
			String quantidadeEconomia = null;
			int valor = 0;
			while(iteratorColecaoCategoria.hasNext()){
				categoria = (Categoria) iteratorColecaoCategoria.next();
				// teste para ver se existe na página alguma categoria
				valor++;
				if(requestMap.get("categoria" + categoria.getId()) != null){

					quantidadeEconomia = (requestMap.get("categoria" + categoria.getId()))[0];

					if(quantidadeEconomia == null || quantidadeEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Quantidade de Economias");
					}

					categoria.setQuantidadeEconomiasCategoria(new Integer(quantidadeEconomia));
				}
			}
			if(valor == 0){
				sessao.setAttribute("existeColecao", "nao");
			}else{
				sessao.removeAttribute("existeColecao");
			}
		}
		if(sessao.getAttribute("colecaoSubcategoria") != null){
			Collection colecao = (Collection) sessao.getAttribute("colecaoSubcategoria");
			Iterator iteratorColecaoSubcategoria = colecao.iterator();

			Subcategoria subcategoria = null;
			String quantidadeEconomia = null;
			int valor = 0;
			while(iteratorColecaoSubcategoria.hasNext()){
				subcategoria = (Subcategoria) iteratorColecaoSubcategoria.next();
				// teste para ver se existe na página alguma categoria
				valor++;
				if(requestMap.get("subcategoria" + subcategoria.getId()) != null){

					quantidadeEconomia = (requestMap.get("subcategoria" + subcategoria.getId()))[0];

					if(quantidadeEconomia == null || quantidadeEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Quantidade de Economias");
					}

					subcategoria.setQuantidadeEconomias(new Integer(quantidadeEconomia));
				}
			}
			if(valor == 0){
				sessao.setAttribute("existeColecao", "nao");
			}else{
				sessao.removeAttribute("existeColecao");
			}
		}

		String pAcumularConsumoEsgotoPoco = null;
		try{

			pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso a empresa não acumule o volume do poço com o volume da ligação de água para cálculo
		// do valor de esgoto
		if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())){

			if(!Util.isVazioOuBranco(inserirContaActionForm.getIdImovel()) && Util.isInteger(inserirContaActionForm.getIdImovel())){

				LigacaoEsgoto ligacaoEsgoto = (LigacaoEsgoto) fachada.pesquisar(Util.obterInteger(inserirContaActionForm.getIdImovel()),
								LigacaoEsgoto.class);

				// Caso o imóvel possua volume fixo para poço
				if(ligacaoEsgoto != null && ligacaoEsgoto.getNumeroConsumoFixoPoco() != null){

					inserirContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.SIM.toString());
				}else{

					inserirContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.NAO.toString());
					inserirContaActionForm.setConsumoFixoPoco(null);
				}
			}else{

				inserirContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.NAO.toString());
				inserirContaActionForm.setConsumoFixoPoco(null);
			}
		}else{

			inserirContaActionForm.setHabilitarConsumoFixoPoco(ConstantesSistema.NAO.toString());
			inserirContaActionForm.setConsumoFixoPoco(null);
		}

		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			Collection colecaoDebito = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
			Iterator iteratorColecaoDebito = colecaoDebito.iterator();

			DebitoCobrado debitoCobrado = null;
			String valor = null;
			BigDecimal valor2 = new BigDecimal("0.00");

			while(iteratorColecaoDebito.hasNext()){
				debitoCobrado = (DebitoCobrado) iteratorColecaoDebito.next();
				// valor minimo
				if(requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)) != null){

					valor = (requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)))[0];

					if(valor == null || valor.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Débito Cobrado");
					}else{
						valor2 = Util.formatarMoedaRealparaBigDecimal(valor);
					}

					debitoCobrado.setValorPrestacao(valor2);
				}
			}
		}

		if(requestMap.get("percentualEsgoto") != null){
			String percentualEsgoto = (String) requestMap.get("percentualEsgoto")[0];
			inserirContaActionForm.setPercentualEsgoto(percentualEsgoto);
			httpServletRequest.setAttribute("percentualEsgoto", percentualEsgoto);
		}

		// Verifica se a situação da ligação de esgoto é faturável, caso não seja limpa e desabilita
		// campos relativos aos dados da ligação de esgoto.
		if(inserirContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| inserirContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			inserirContaActionForm.setLigacaoEsgotoPerfilId(null);
			inserirContaActionForm.setConsumoEsgoto(null);
			inserirContaActionForm.setPercentualEsgoto(null);
		}

		// Limpando os campos após remoção ou inserção de categorias ou débitos
		if(reloadPage != null && !reloadPage.equalsIgnoreCase("")){

			inserirContaActionForm.setValorAgua("");
			inserirContaActionForm.setValorEsgoto("");
			inserirContaActionForm.setValorDebito("");
			inserirContaActionForm.setValorTotal("");
		}

		return retorno;
	}
}
