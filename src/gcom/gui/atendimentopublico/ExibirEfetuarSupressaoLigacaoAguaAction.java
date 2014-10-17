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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroSupressaoTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 14/07/2006
 */
public class ExibirEfetuarSupressaoLigacaoAguaAction
				extends GcomAction {

	/**
	 * [UC0360] Efetuar Supressao de �gua
	 * Este caso de uso permite efetuar supress�o da liga��o de �gua, sendo
	 * chamada pela funcionalidade que encerra a execu��o da ordem de servi�o ou
	 * chamada diretamente do menu.
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
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("efetuarSupressaoLigacaoAgua");

		EfetuarSupressaoLigacaoAguaActionForm efetuarSupressaoLigacaoAguaActionForm = (EfetuarSupressaoLigacaoAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarSupressaoLigacaoAguaActionForm.getVeioEncerrarOS() != null
							&& !efetuarSupressaoLigacaoAguaActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarSupressaoLigacaoAguaActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		boolean pesquisouFuncionario = false;

		// Pesquisar Funcionario ENTER
		if((efetuarSupressaoLigacaoAguaActionForm.getIdFuncionario() != null && !efetuarSupressaoLigacaoAguaActionForm.getIdFuncionario()
						.equals(""))
						&& (efetuarSupressaoLigacaoAguaActionForm.getDescricaoFuncionario() == null || efetuarSupressaoLigacaoAguaActionForm
										.getDescricaoFuncionario().equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, efetuarSupressaoLigacaoAguaActionForm
							.getIdFuncionario()));

			Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				efetuarSupressaoLigacaoAguaActionForm.setIdFuncionario("");
				efetuarSupressaoLigacaoAguaActionForm.setDescricaoFuncionario("FUNCION�RIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				efetuarSupressaoLigacaoAguaActionForm.setIdFuncionario(funcionario.getId().toString());
				efetuarSupressaoLigacaoAguaActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
			pesquisouFuncionario = true;
		}

		// Pesquisar Funcion�rio POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		String idOrdemServico = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			efetuarSupressaoLigacaoAguaActionForm.setDataSupressao((String) httpServletRequest.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(efetuarSupressaoLigacaoAguaActionForm.getIdOrdemServico() != null){
			idOrdemServico = efetuarSupressaoLigacaoAguaActionForm.getIdOrdemServico();
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}

		this.pesquisarSelectObrigatorio(httpServletRequest, efetuarSupressaoLigacaoAguaActionForm);

		/*
		 * // Verifica se o id da Ordem de servico vem da sessao. if
		 * (sessao.getAttribute("idOrdemServico") != null) { idOrdemServico =
		 * (String) sessao.getAttribute("idOrdemServico"); } else {
		 * idOrdemServico = efetuarSupressaoLigacaoAguaActionForm
		 * .getIdOrdemServico(); }
		 */

		OrdemServico ordemServico = null;

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){

				fachada.validarExibirSupressaoLigacaoAgua(ordemServico, veioEncerrarOS);

				efetuarSupressaoLigacaoAguaActionForm.setIdOrdemServico(idOrdemServico);
				efetuarSupressaoLigacaoAguaActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

				efetuarSupressaoLigacaoAguaActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				sessao.setAttribute("ordemServico", ordemServico);

				// Comentado por Raphael Rossiter em 28/02/2007
				// Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
				Imovel imovel = ordemServico.getImovel();
				BigDecimal valorDebito = new BigDecimal(0);

				if(ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getDebitoTipo() != null){
					efetuarSupressaoLigacaoAguaActionForm.setIdTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getId().toString());
					efetuarSupressaoLigacaoAguaActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo()
									.getDescricao());

					// [FS0013] - Altera��o de Valor
					this.permitirAlteracaoValor(ordemServico.getServicoTipo(), efetuarSupressaoLigacaoAguaActionForm);

					String calculaValores = httpServletRequest.getParameter("calculaValores");

					SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
					Integer qtdeParcelas = null;

					if(calculaValores != null && calculaValores.equals("S")){

						// [UC0186] - Calcular Presta��o
						BigDecimal taxaJurosFinanciamento = null;
						qtdeParcelas = new Integer(efetuarSupressaoLigacaoAguaActionForm.getQuantidadeParcelas());

						if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue()
										&& qtdeParcelas.intValue() != 1){

							taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
						}else{
							taxaJurosFinanciamento = new BigDecimal(0);
						}

						BigDecimal valorPrestacao = null;
						if(taxaJurosFinanciamento != null){

							valorDebito = new BigDecimal(efetuarSupressaoLigacaoAguaActionForm.getValorDebito().replace(",", "."));
							valorDebito = valorDebito
											.multiply(new BigDecimal(efetuarSupressaoLigacaoAguaActionForm.getPercentualCobranca())
															.divide(new BigDecimal("100")));
							valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
											new BigDecimal("0.00"));
							valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
						}

						if(valorPrestacao != null){
							String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
							efetuarSupressaoLigacaoAguaActionForm.setValorParcelas(valorPrestacaoComVirgula);
						}else{
							efetuarSupressaoLigacaoAguaActionForm.setValorParcelas("0,00");
						}

					}else{

						// Valor do D�bitou
						short tipoMedicao = 3;

						valorDebito = Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(),
										ordemServico.getImovel().getId(), tipoMedicao);

						efetuarSupressaoLigacaoAguaActionForm.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));

					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarSupressaoLigacaoAguaActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getId()
										.toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarSupressaoLigacaoAguaActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca().toString());
					}
				}

				String matriculaImovel = imovel.getId().toString();
				efetuarSupressaoLigacaoAguaActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- In�cio dados do Im�vel---------------*/

				// Comentado por Raphael Rossiter em 28/02/2007
				// sessao.setAttribute("imovel", ordemServico.getRegistroAtendimento().getImovel());
				sessao.setAttribute("imovel", ordemServico.getImovel());

				if(imovel != null){

					// Matricula Im�vel
					efetuarSupressaoLigacaoAguaActionForm.setMatriculaImovel(imovel.getId().toString());

					// Inscri��o Im�vel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					efetuarSupressaoLigacaoAguaActionForm.setInscricaoImovel(inscricaoImovel);

					// Situa��o da Liga��o de Agua

					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					efetuarSupressaoLigacaoAguaActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

					// Situa��o da Liga��o de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					efetuarSupressaoLigacaoAguaActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

					this.pesquisarCliente(efetuarSupressaoLigacaoAguaActionForm, new Integer(matriculaImovel));
				}

				// Data Execu��o
				String dataExecucaoOdServico = Util.formatarData(ordemServico.getDataExecucao());
				if(dataExecucaoOdServico != null && !dataExecucaoOdServico.equals("")){
					efetuarSupressaoLigacaoAguaActionForm.setDataSupressao(dataExecucaoOdServico);
				}

				LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();

				// Supressao Motivo
				if(ligacaoAgua.getSupressaoMotivo() != null){

					String supressaoMotivo = ligacaoAgua.getSupressaoMotivo().getId().toString();
					efetuarSupressaoLigacaoAguaActionForm.setMotivoSupressao(supressaoMotivo);
				}

				// Supressao Tipo

				if(httpServletRequest.getParameter("combo") == null){
					if(ligacaoAgua.getSupressaoTipo() != null){

						String supressaoTipo = ligacaoAgua.getSupressaoTipo().getId().toString();
						efetuarSupressaoLigacaoAguaActionForm.setTipoSupressao(supressaoTipo);

						if((ligacaoAgua.getSupressaoTipo().getIndicadorTotal() != null && ligacaoAgua.getSupressaoTipo()
										.getIndicadorTotal().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue())
										&& (ligacaoAgua.getSupressaoTipo().getIndicadorParcial() == null || ligacaoAgua.getSupressaoTipo()
														.getIndicadorParcial().shortValue() == ConstantesSistema.INDICADOR_USO_DESATIVO
														.shortValue())){
							efetuarSupressaoLigacaoAguaActionForm.setIndicadorTipoSupressao(ConstantesSistema.INDICADOR_USO_ATIVO
											.toString());
						}else if((ligacaoAgua.getSupressaoTipo().getIndicadorTotal() == null || ligacaoAgua.getSupressaoTipo()
										.getIndicadorTotal().shortValue() == ConstantesSistema.INDICADOR_USO_DESATIVO.shortValue())
										&& (ligacaoAgua.getSupressaoTipo().getIndicadorParcial() != null && ligacaoAgua.getSupressaoTipo()
														.getIndicadorParcial().shortValue() == ConstantesSistema.INDICADOR_USO_ATIVO
														.shortValue())){
							efetuarSupressaoLigacaoAguaActionForm.setIndicadorTipoSupressao(ConstantesSistema.INDICADOR_USO_DESATIVO
											.toString());
						}

					}
				}
				// Selo Supress�o
				if(ligacaoAgua.getNumeroSeloSupressao() != null){

					String NumeroSeloSupressao = ligacaoAgua.getNumeroSeloSupressao().toString();
					efetuarSupressaoLigacaoAguaActionForm.setNumeroSeloSupressao(NumeroSeloSupressao);

				}else{
					efetuarSupressaoLigacaoAguaActionForm.setNumeroSeloSupressao("");
				}

				if(Util.isVazioOuBranco(efetuarSupressaoLigacaoAguaActionForm.getIndicadorRetirardaHidrometro())){
					efetuarSupressaoLigacaoAguaActionForm.setIndicadorRetirardaHidrometro(ConstantesSistema.INDICADOR_USO_ATIVO.toString());
				}

				if(ligacaoAgua.getHidrometroInstalacaoHistorico() != null){

					FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();

					filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.ID,
									ligacaoAgua.getHidrometroInstalacaoHistorico().getId()));

					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("hidrometroProtecao");

					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("hidrometro");
					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("rateioTipo");
					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("hidrometroLocalInstalacao");
					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua");
					filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("medicaoTipo");

					Collection colecaoHidrometroInstalacaoHistorico = fachada.pesquisar(filtroHidrometroInstalacaoHistorico,
									HidrometroInstalacaoHistorico.class.getName());

					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = (HidrometroInstalacaoHistorico) colecaoHidrometroInstalacaoHistorico
									.iterator().next();

					if(hidrometroInstalacaoHistorico != null && hidrometroInstalacaoHistorico.getNumeroLeituraSupressao() != null){
						efetuarSupressaoLigacaoAguaActionForm.setNumeroLeituraSupressao(""
										+ hidrometroInstalacaoHistorico.getNumeroLeituraSupressao());
					}else{
						efetuarSupressaoLigacaoAguaActionForm.setNumeroLeituraSupressao("");
					}

				}else{
					efetuarSupressaoLigacaoAguaActionForm.setNumeroLeituraSupressao("");
				}

				// Filtro para o campo Tipo Debito
				Collection colecaoNaoCobranca = (Collection) sessao.getAttribute("colecaoNaoCobranca");
				if(colecaoNaoCobranca == null){
					FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();

					filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

					colecaoNaoCobranca = fachada.pesquisar(filtroServicoNaoCobrancaMotivo, ServicoNaoCobrancaMotivo.class.getName());

					if(colecaoNaoCobranca != null && !colecaoNaoCobranca.isEmpty()){
						sessao.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
					}else{
						throw new ActionServletException("atencao.naocadastrado", null, "Motivo da N�o Cobran�a");
					}
				}
				// Dados da Gera��o de D�bito
				// this.pesquisarDadosGeracaoDebito(efetuarSupressaoLigacaoAguaActionForm,
				// ordemServico);
				FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo().getId()));

				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("creditoTipo");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoPerfilTipo");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoSubgrupo");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoPrioridade");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoAtividades");
				filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoMateriais");

				Collection colecaoServicoTipo = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

				ServicoTipo servicoTipo = (ServicoTipo) colecaoServicoTipo.iterator().next();

				// Filtro para carregar o Cliente
				if(servicoTipo.getDebitoTipo() != null){

					FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

					filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, servicoTipo.getDebitoTipo().getId()
									.toString()));

					Collection colecaoDebitoTipo = Fachada.getInstancia().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

					DebitoTipo debitoTipo = (DebitoTipo) colecaoDebitoTipo.iterator().next();

					if(debitoTipo.getId() != null && !debitoTipo.getId().equals("")){

						// Codigo/descricao
						efetuarSupressaoLigacaoAguaActionForm.setIdTipoDebito(debitoTipo.getId().toString());
						efetuarSupressaoLigacaoAguaActionForm.setDescricaoTipoDebito(debitoTipo.getDescricao());
					}else{
						// Codigo/descricao
						efetuarSupressaoLigacaoAguaActionForm.setIdTipoDebito("");
						efetuarSupressaoLigacaoAguaActionForm.setDescricaoTipoDebito("");

					}
				}

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

					efetuarSupressaoLigacaoAguaActionForm.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					efetuarSupressaoLigacaoAguaActionForm.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}

				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					efetuarSupressaoLigacaoAguaActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
				}

				String listaServicoSupressaoSemRetirada = null;
				try{
					listaServicoSupressaoSemRetirada = ParametroAtendimentoPublico.P_LISTA_SERVICOS_SUPRESSAO_SEM_RETIRADA_HIDROMETRO
									.executar();
				}catch(ControladorException e){
					throw new ActionServletException("atencao.sistemaparametro_inexistente", null,
									"P_LISTA_SERVICOS_SUPRESSAO_SEM_RETIRADA_HIDROMETRO");
				}

				boolean supressaoSemRetirada = false;
				if(!listaServicoSupressaoSemRetirada.equals(ConstantesSistema.INVALIDO_ID.toString())){

					String[] idsServicos = listaServicoSupressaoSemRetirada.split(",");

					for(int x = 0; x < idsServicos.length; x++){

						if(ordemServico.getServicoTipo().getId().toString().equals(idsServicos[x])){
							supressaoSemRetirada = true;
							break;
						}
					}
				}

				if(supressaoSemRetirada){
					httpServletRequest.setAttribute("supressaoSemRetirada", "OK");
				}else{
					httpServletRequest.removeAttribute("supressaoSemRetirada");
				}

			}else{

				httpServletRequest.setAttribute("OrdemServicoInexistente", true);
				efetuarSupressaoLigacaoAguaActionForm.setIdOrdemServico("");
				efetuarSupressaoLigacaoAguaActionForm.setNomeOrdemServico("ORDEM DE SERVI�O INEXISTENTE");

			}
		}

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
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarSupressaoLigacaoAguaActionForm form){

		if(servicoTipo.getIndicadorPermiteAlterarValor() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			form.setAlteracaoValor("OK");
		}else{
			form.setAlteracaoValor("");
		}

	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 22/08/2006
	 */
	private void pesquisarCliente(EfetuarSupressaoLigacaoAguaActionForm efetuarSupressaoLigacaoAguaActionForm, Integer matriculaImovel){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matriculaImovel));

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
			efetuarSupressaoLigacaoAguaActionForm.setClienteUsuario(cliente.getNome());
			efetuarSupressaoLigacaoAguaActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	// Dados da Gera��o de D�bito
	/*
	 * private void pesquisarDadosGeracaoDebito(
	 * EfetuarSupressaoLigacaoAguaActionForm efetuarSupressaoLigacaoAguaActionForm,
	 * OrdemServico ordemServico) {
	 * }
	 */

	/**
	 * Pesquisa o local de instala��o Pesquisa hidrometro prote��o
	 */
	private void pesquisarSelectObrigatorio(HttpServletRequest httpServletRequest,
					EfetuarSupressaoLigacaoAguaActionForm efetuarSupressaoLigacaoAguaActionForm){

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// ************ Tipo Supressao***************

		// Vericacao dequal o tipo de supressao informada
		// pelo usuario para
		// habilitar no combo box as opcoes correspondentes
		// Parte relativa ao campo Tipo da Supressao Parcial

		FiltroSupressaoTipo filtroSupressaoTipo = new FiltroSupressaoTipo();

		if(efetuarSupressaoLigacaoAguaActionForm.getIndicadorTipoSupressao() != null){

			if(efetuarSupressaoLigacaoAguaActionForm.getIndicadorTipoSupressao().equals("1")){

				filtroSupressaoTipo.adicionarParametro(new ParametroSimples(FiltroSupressaoTipo.INDICADOR_TOTAL,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection<SupressaoTipo> colecaoSupressaoTipo = fachada.pesquisar(filtroSupressaoTipo, SupressaoTipo.class.getName());

				if(colecaoSupressaoTipo == null || colecaoSupressaoTipo.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Supress�o Tipo ");
				}

				efetuarSupressaoLigacaoAguaActionForm.setIndicadorTipoSupressao("1");
				httpServletRequest.setAttribute("colecaoSupressaoTipo", colecaoSupressaoTipo);

			}else{
				if(efetuarSupressaoLigacaoAguaActionForm.getIndicadorTipoSupressao().equals("2")){

					filtroSupressaoTipo.adicionarParametro(new ParametroSimples(FiltroSupressaoTipo.INDICADOR_PARCIAL,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection<SupressaoTipo> colecaoSupressaoTipo = fachada.pesquisar(filtroSupressaoTipo, SupressaoTipo.class.getName());

					if(colecaoSupressaoTipo == null || colecaoSupressaoTipo.isEmpty()){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Supress�o Tipo ");
					}
					efetuarSupressaoLigacaoAguaActionForm.setIndicadorTipoSupressao("2");
					httpServletRequest.setAttribute("colecaoSupressaoTipo", colecaoSupressaoTipo);
				}
			}
		}else{

			efetuarSupressaoLigacaoAguaActionForm.setIndicadorTipoSupressao("1");

			filtroSupressaoTipo.adicionarParametro(new ParametroSimples(FiltroSupressaoTipo.INDICADOR_TOTAL,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<SupressaoTipo> colecaoSupressaoTipo = fachada.pesquisar(filtroSupressaoTipo, SupressaoTipo.class.getName());

			if(colecaoSupressaoTipo == null || colecaoSupressaoTipo.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tabela Supress�o Tipo ");
			}
			httpServletRequest.setAttribute("colecaoSupressaoTipo", colecaoSupressaoTipo);

		}
		// Supressao Motivo
		FiltroSupressaoMotivo filtroSupressaoMotivo = new FiltroSupressaoMotivo();

		Collection<SupressaoMotivo> colecaoSupressaoMotivo = fachada.pesquisar(filtroSupressaoMotivo, SupressaoMotivo.class.getName());

		httpServletRequest.setAttribute("colecaoMotivoSupressao", colecaoSupressaoMotivo);

		// Filtro para o campo Tpo Debito
		Collection colecaoNaoCobranca = (Collection) sessao.getAttribute("colecaoNaoCobranca");
		if(colecaoNaoCobranca == null){
			FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();

			filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

			colecaoNaoCobranca = Fachada.getInstancia().pesquisar(filtroServicoNaoCobrancaMotivo, ServicoNaoCobrancaMotivo.class.getName());

			if(colecaoNaoCobranca != null && !colecaoNaoCobranca.isEmpty()){
				sessao.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Motivo da N�o Cobran�a");
			}
		}
	}

}
