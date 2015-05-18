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

package gcom.gui.atendimentopublico.hidrometro;

import gcom.atendimentopublico.ordemservico.FiltroServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Ana Maria
 * @date 30/06/2006
 */
public class ExibirEfetuarInstalacaoHidrometroAction
				extends GcomAction {

	/**
	 * Este caso de uso permite efetuar instala��o de hidr�metro, sendo chamado pela funcionalidade
	 * que encerra
	 * a execu��o da ordem de servi�o ou chamada diretamente do Menu.
	 * [UC0362] Efetuar Instala��o de Hidr�metro
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("efetuarInstalacaoHidrometro");
		EfetuarInstalacaoHidrometroActionForm efetuarInstalacaoHidrometroActionForm = (EfetuarInstalacaoHidrometroActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Pesquisar Funcionario ENTER
		if((efetuarInstalacaoHidrometroActionForm.getIdFuncionario() != null && !efetuarInstalacaoHidrometroActionForm.getIdFuncionario()
						.equals(""))
						&& (efetuarInstalacaoHidrometroActionForm.getDescricaoFuncionario() == null || efetuarInstalacaoHidrometroActionForm
										.getDescricaoFuncionario().equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, efetuarInstalacaoHidrometroActionForm
							.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				efetuarInstalacaoHidrometroActionForm.setIdFuncionario("");
				efetuarInstalacaoHidrometroActionForm.setDescricaoFuncionario("FUNCION�RIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				efetuarInstalacaoHidrometroActionForm.setIdFuncionario(funcionario.getId().toString());
				efetuarInstalacaoHidrometroActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcion�rio POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarInstalacaoHidrometroActionForm.getVeioEncerrarOS() != null && efetuarInstalacaoHidrometroActionForm
										.getVeioEncerrarOS().equalsIgnoreCase("true"))){

			veioEncerrarOS = Boolean.TRUE;
		}else{
			veioEncerrarOS = Boolean.FALSE;
		}

		if(httpServletRequest.getParameter("submitAutomatico2") != null
						&& httpServletRequest.getParameter("submitAutomatico2").equals("ok")){
			httpServletRequest.setAttribute("submitAutomatico2", "ok");
		}

		// Colocado por Vivianne Sousa em 05/12/2007
		// ------------------------------------------------------------
		if(efetuarInstalacaoHidrometroActionForm.getIndicadorTrocaProtecao() == null){
			efetuarInstalacaoHidrometroActionForm.setIndicadorTrocaProtecao(ConstantesSistema.NAO.toString());
		}

		if(efetuarInstalacaoHidrometroActionForm.getIndicadorTrocaRegistro() == null){
			efetuarInstalacaoHidrometroActionForm.setIndicadorTrocaRegistro(ConstantesSistema.NAO.toString());
		}
		// ------------------------------------------------------------

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		this.pesquisarSelectObrigatorio(httpServletRequest);

		String idOrdemServico = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			efetuarInstalacaoHidrometroActionForm.setDataInstalacao((String) httpServletRequest.getAttribute("dataEncerramento"));
			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else{

			if(efetuarInstalacaoHidrometroActionForm.getIdOrdemServico() != null){
				idOrdemServico = efetuarInstalacaoHidrometroActionForm.getIdOrdemServico();
			}
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}

		OrdemServico ordemServico = null;
		String matriculaImovel = null;

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){

				httpServletRequest.setAttribute("ordemServicoEncontrado", "true");

				fachada.validarExibirInstalacaoHidrometro(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarInstalacaoHidrometroActionForm.setIdOrdemServico("" + ordemServico.getId());

				efetuarInstalacaoHidrometroActionForm.setVeioEncerrarOS("" + veioEncerrarOS);
				efetuarInstalacaoHidrometroActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				Imovel imovel = null;

				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else{
					imovel = ordemServico.getRegistroAtendimento().getImovel();
				}

				// Matricula Im�vel
				matriculaImovel = imovel.getId().toString();
				efetuarInstalacaoHidrometroActionForm.setMatriculaImovel(matriculaImovel);

				// Inscri��o Im�vel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				efetuarInstalacaoHidrometroActionForm.setInscricaoImovel(inscricaoImovel);

				// Situa��o da Liga��o de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				efetuarInstalacaoHidrometroActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situa��o da Liga��o de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
				efetuarInstalacaoHidrometroActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				// Cliente Usu�rio
				this.pesquisarCliente(efetuarInstalacaoHidrometroActionForm);

				// Tipo medi��o - Liga��o �gua
				if(ordemServico.getRegistroAtendimento() == null
								|| ordemServico.getRegistroAtendimento().getSolicitacaoTipoEspecificacao().getIndicadorLigacaoAgua()
												.equals(MedicaoTipo.LIGACAO_AGUA.shortValue())){
					efetuarInstalacaoHidrometroActionForm.setTipoMedicao(MedicaoTipo.LIGACAO_AGUA.toString());

					// Tipo medi��o- Po�o
				}else{
					efetuarInstalacaoHidrometroActionForm.setTipoMedicao(MedicaoTipo.POCO.toString());
				}

				// Data recibida da execu��o da OS
				Date dataInstalacao = ordemServico.getDataExecucao();
				if(dataInstalacao != null && !dataInstalacao.equals("")){
					efetuarInstalacaoHidrometroActionForm.setDataInstalacao(Util.formatarData(dataInstalacao));
				}

				// Tipo D�bito
				if(ordemServico.getServicoTipo().getDebitoTipo() != null){
					efetuarInstalacaoHidrometroActionForm.setIdTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getId() + "");
					efetuarInstalacaoHidrometroActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo()
									.getDescricao()
									+ "");
				}else{
					efetuarInstalacaoHidrometroActionForm.setIdTipoDebito("");
					efetuarInstalacaoHidrometroActionForm.setDescricaoTipoDebito("");
				}

				// [FS0013] - Altera��o de Valor
				this.permitirAlteracaoValor(ordemServico.getServicoTipo(), efetuarInstalacaoHidrometroActionForm);

				// Colocado por Raphael Rossiter em 04/05/2007 (Analista: Rosana Carvalho)
				BigDecimal valorDebito = this.calcularValores(httpServletRequest, ordemServico, efetuarInstalacaoHidrometroActionForm);

				efetuarInstalacaoHidrometroActionForm.setQtdeMaxParcelas(fachada.obterQuantidadeParcelasMaxima().toString());

				// -----------------------------------------------------------
				// Verificar permiss�o especial
				boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
				// -----------------------------------------------------------

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
				}

				boolean temPermissaoPercentualCobrancaExcedente = fachada
								.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
				boolean temPermissaoQuantidadeParcelasExcedente = fachada
								.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

				if(temPermissaoPercentualCobrancaExcedente){

					httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
					httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

				}else{

					efetuarInstalacaoHidrometroActionForm.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					efetuarInstalacaoHidrometroActionForm.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}
				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					efetuarInstalacaoHidrometroActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
				}
			}else{
				httpServletRequest.setAttribute("ordemServicoEncontrado", "exception");
				efetuarInstalacaoHidrometroActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
				efetuarInstalacaoHidrometroActionForm.setIdOrdemServico("");
			}

		}else{
			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarInstalacaoHidrometroActionForm.setIdOrdemServico("");
			efetuarInstalacaoHidrometroActionForm.setMatriculaImovel("");
			efetuarInstalacaoHidrometroActionForm.setInscricaoImovel("");
			efetuarInstalacaoHidrometroActionForm.setClienteUsuario("");
			efetuarInstalacaoHidrometroActionForm.setCpfCnpjCliente("");
			efetuarInstalacaoHidrometroActionForm.setSituacaoLigacaoAgua("");
			efetuarInstalacaoHidrometroActionForm.setSituacaoLigacaoEsgoto("");
			efetuarInstalacaoHidrometroActionForm.setNomeOrdemServico("");
			efetuarInstalacaoHidrometroActionForm.setIdTipoDebito("");
			efetuarInstalacaoHidrometroActionForm.setDescricaoTipoDebito("");
		}

		String numeroHidrometro = efetuarInstalacaoHidrometroActionForm.getNumeroHidrometro();

		// Verificar se o n�mero do hidr�metro n�o est� cadastrado
		if(numeroHidrometro != null && !numeroHidrometro.trim().equals("")){

			// Filtro para descobrir id do Hidrometro
			FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

			filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.NUMERO_HIDROMETRO, numeroHidrometro));

			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");

			Collection colecaoHidrometro = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

			if(colecaoHidrometro == null || colecaoHidrometro.isEmpty()){
				efetuarInstalacaoHidrometroActionForm.setNumeroHidrometro("");
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}else{
				Hidrometro hidro = (Hidrometro) Util.retonarObjetoDeColecao(colecaoHidrometro);
				efetuarInstalacaoHidrometroActionForm.setNumeroHidrometro(hidro.getNumero());

				if(ordemServico != null){

					// Colocado por Raphael Rossiter em 04/05/2007 (Analista: Rosana Carvalho)
					BigDecimal valorDebito = this.calcularValores(httpServletRequest, ordemServico, efetuarInstalacaoHidrometroActionForm);

					if(valorDebito != null){
						efetuarInstalacaoHidrometroActionForm.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
					}else{
						efetuarInstalacaoHidrometroActionForm.setValorDebito("0");
					}
				}

			}
		}
		// Seta cole��o de motivo de n�o cobran�a
		getMotivoNaoCobrancaCollection(sessao);

		// Par�metro que indica obrigatoriedade do campo Funcion�rio
		String indicadorObrigatoriedadeFuncionario = null;
		try{
			indicadorObrigatoriedadeFuncionario = ParametroCadastro.P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("indicadorObrigatoriedadeFuncionario", indicadorObrigatoriedadeFuncionario);

		return retorno;
	}

	/*
	 * [FS0013 - Altera��o de Valor]
	 * autor: Raphael Rossiter
	 * data: 19/04/2007
	 */
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarInstalacaoHidrometroActionForm form){

		if(servicoTipo.getIndicadorPermiteAlterarValor() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			form.setAlteracaoValor("OK");
		}else{
			form.setAlteracaoValor("");
		}

	}

	/*
	 * Calcular valor da presta��o com juros
	 * return: Retorna o valor total do d�bito
	 * autor: Raphael Rossiter
	 * data: 04/05/2007
	 */
	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarInstalacaoHidrometroActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

		BigDecimal valorDebitoCalc = new BigDecimal(0);
		BigDecimal valorDebito = new BigDecimal(0);
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		Integer qtdeParcelas = null;

		if(calculaValores != null && calculaValores.equals("S")){

			// [UC0186] - Calcular Presta��o
			BigDecimal taxaJurosFinanciamento = null;
			qtdeParcelas = new Integer(form.getQuantidadeParcelas());

			if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue() && qtdeParcelas.intValue() > 1){

				taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
			}else{
				taxaJurosFinanciamento = new BigDecimal(0);
				qtdeParcelas = 1;
			}

			BigDecimal valorPrestacao = null;
			if(taxaJurosFinanciamento != null){

				valorDebitoCalc = new BigDecimal(form.getValorDebito().replace(",", "."));
				valorDebitoCalc = valorDebitoCalc.multiply(new BigDecimal(form.getPercentualCobranca()).divide(new BigDecimal("100")));
				valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebitoCalc,
								new BigDecimal("0.00"));

				valorDebito = Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(),
								ordemServico.getRegistroAtendimento().getImovel().getId(), new Short("3"));

				if(valorDebito != null){
					form.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
				}else{
					form.setValorDebito("0");
				}

				valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			if(valorPrestacao != null){
				String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
				form.setValorParcelas(valorPrestacaoComVirgula);
			}else{
				form.setValorParcelas("0,00");
			}

		}else if(ordemServico.getRegistroAtendimento() != null){

			valorDebito = Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(),
							ordemServico.getRegistroAtendimento().getImovel().getId(), new Short("3"));

			if(valorDebito != null){
				form.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
			}else{
				form.setValorDebito("0");
			}
		}

		return valorDebito;
	}

	/**
	 * Pesquisa o local de instala��o
	 * Pesquisa hidrometro prote��o
	 */
	private void pesquisarSelectObrigatorio(HttpServletRequest httpServletRequest){

		// Pesquisando local de instala��o
		FiltroHidrometroLocalInstalacao filtroHidrometroLocalInstalacao = new FiltroHidrometroLocalInstalacao();
		filtroHidrometroLocalInstalacao.setCampoOrderBy(FiltroHidrometroLocalInstalacao.DESCRICAO);
		filtroHidrometroLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalInstalacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoHidrometroLocalInstalacao = Fachada.getInstancia().pesquisar(filtroHidrometroLocalInstalacao,
						HidrometroLocalInstalacao.class.getName());

		if(colecaoHidrometroLocalInstalacao == null || colecaoHidrometroLocalInstalacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Hidr�metro local de instala��o");
		}

		httpServletRequest.setAttribute("colecaoHidrometroLocalInstalacao", colecaoHidrometroLocalInstalacao);

		// Pesquisando prote��o
		FiltroHidrometroProtecao filtroHidrometroProtecao = new FiltroHidrometroProtecao();
		filtroHidrometroProtecao.setCampoOrderBy(FiltroHidrometroProtecao.DESCRICAO);
		filtroHidrometroProtecao.adicionarParametro(new ParametroSimples(FiltroHidrometroProtecao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoHidrometroProtecao = Fachada.getInstancia().pesquisar(filtroHidrometroProtecao,
						HidrometroProtecao.class.getName());

		if(colecaoHidrometroProtecao == null || colecaoHidrometroProtecao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Hidr�metro prote��o");
		}

		httpServletRequest.setAttribute("colecaoHidrometroProtecao", colecaoHidrometroProtecao);
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 01/09/2006
	 */
	private void pesquisarCliente(EfetuarInstalacaoHidrometroActionForm efetuarInstalacaoHidrometroActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, efetuarInstalacaoHidrometroActionForm
						.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			efetuarInstalacaoHidrometroActionForm.setClienteUsuario(cliente.getNome());
			efetuarInstalacaoHidrometroActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Carrega cole��o de motivo da n�o cobran�a.
	 * 
	 * @author Leonardo Regis
	 * @date 16/09/2006
	 * @param sessao
	 */
	private void getMotivoNaoCobrancaCollection(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();
		// Filtra Motivo da N�o Cobran�a
		FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();
		filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroServicoNaoCobrancaMotivo.DESCRICAO);

		Collection colecaoServicoNaoCobrancaMotivo = fachada.pesquisar(filtroServicoNaoCobrancaMotivo, ServicoNaoCobrancaMotivo.class
						.getName());
		if(colecaoServicoNaoCobrancaMotivo != null && !colecaoServicoNaoCobrancaMotivo.isEmpty()){
			sessao.setAttribute("colecaoNaoCobranca", colecaoServicoNaoCobrancaMotivo);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo N�o Cobran�a");
		}
	}
}