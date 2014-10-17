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

package gcom.gui.atendimentopublico;

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
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela exibição da pagina Efetuar Restabelecimento Ligacao Agua Com Substituicao
 * Hidrometro
 * 
 * @author isilva
 * @created 22 de Dezembro de 2010
 */
public class ExibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("colecaoPercentualCobranca") == null){
			sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
		}

		// -----------------------------------------------------------
		// Verificar permissão especial
		boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
		// -----------------------------------------------------------

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometro");

		EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm = (EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm) actionForm;

		// Pesquisar Funcionario ENTER
		if((efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario() != null && !efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
						.getIdFuncionario().equals(""))
						&& (efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getDescricaoFuncionario() == null || efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
										.getDescricaoFuncionario().equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID,
							efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdFuncionario("");
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDescricaoFuncionario("FUNCIONÁRIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdFuncionario(funcionario.getId().toString());
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcionário POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		if(httpServletRequest.getParameter("desfazer") != null){
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoCavalete(null);
		}

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS() != null && efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
										.getVeioEncerrarOS().equals("true"))){

			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS() != null
							&& !efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

		this.consultaSelectObrigatorio(sessao);

		// Variavel responsavél pelo preenchimento do imovel no formulário
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataRestabelecimento((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataInstalacao((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataRetirada((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdOrdemServico() != null){
			idOrdemServico = efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdOrdemServico();
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}
		OrdemServico ordemServico = null;
		String matriculaImovel = null;

		// [FS0001] Validar Ordem de Serviço.
		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){

				fachada.validarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroExibir(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdOrdemServico(idOrdemServico);
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setNomeOrdemServico(ordemServico.getServicoTipo()
								.getDescricao());

				ServicoTipo servicoTipo = ordemServico.getServicoTipo();
				boolean temPermissaoPercentualCobrancaExcedente = fachada
								.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
				boolean temPermissaoQuantidadeParcelasExcedente = fachada
								.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

				BigDecimal valorDebito = new BigDecimal(0.00);

				/*-------------- Início dados do Imóvel---------------*/
				Imovel imovel = null;

				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else if(ordemServico.getRegistroAtendimento() != null){
					if(ordemServico.getRegistroAtendimento().getImovel() != null){
						imovel = ordemServico.getRegistroAtendimento().getImovel();
					}else{
						throw new ActionServletException("atencao.ordem.servico.nao.vinculada.imovel");
					}
				}else{
					throw new ActionServletException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
				}

				if(imovel == null){
					throw new ActionServletException("atencao.ordem.servico.nao.vinculada.imovel");
				}

				sessao.setAttribute("imovel", imovel);

				/*-------------- Fim dados do Imóvel---------------*/

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					if(sessao.getAttribute("colecaoPercentualCobranca") == null){

						sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
					}

					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdTipoDebito(servicoTipo.getDebitoTipo()
									.getId().toString());

					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDescricaoTipoDebito(servicoTipo
									.getDebitoTipo().getDescricao());

					// [FS0013] - Alteração de Valor
					this.permitirAlteracaoValor(ordemServico.getServicoTipo(),
									efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm);

					// Colocado por Raphael Rossiter em 04/05/2007 (Analista:
					// Rosana Carvalho)
					if(!Util.verificarNaoVazio(httpServletRequest.getParameter("objetoConsulta"))){
						// Colocado por Raphael Rossiter em 04/05/2007 (Analista: Rosana Carvalho)
						valorDebito = this.calcularValores(httpServletRequest, ordemServico,
										efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm);

						efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setQtdeMaxParcelas(fachada
										.obterQuantidadeParcelasMaxima().toString());

						// -----------------------------------------------------------
						// Verificar permissão especial

						if(temPermissaoMotivoNaoCobranca){
							httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
						}
						// -----------------------------------------------------------

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{

							efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setPercentualCobranca("100");
						}

						if(!temPermissaoQuantidadeParcelasExcedente){

							efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setQuantidadeParcelas("1");
						}else{

							httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente",
											temPermissaoQuantidadeParcelasExcedente);
						}

						if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
							efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setValorParcelas(Util.formataBigDecimal(
											valorDebito, 2, true));
						}
					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setMotivoNaoCobranca(ordemServico
										.getServicoNaoCobrancaMotivo().getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setPercentualCobranca(ordemServico
										.getPercentualCobranca().toString());
					}
				}

				if(ordemServico.getDataExecucao() != null){
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataRestabelecimento(Util
									.formatarData(ordemServico.getDataExecucao()));
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataInstalacao(Util.formatarData(ordemServico
									.getDataExecucao()));
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataRetirada(Util.formatarData(ordemServico
									.getDataExecucao()));
				}

				matriculaImovel = imovel.getId().toString();
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- Início dados do Imóvel---------------*/
				if(imovel != null){

					// Matricula Imóvel
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setMatriculaImovel(imovel.getId().toString());

					// Inscrição Imóvel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setInscricaoImovel(inscricaoImovel);

					// Situação da Ligação de Agua
					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

					// Situação da Ligação de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

					this.pesquisarCliente(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm, new Integer(
									matriculaImovel));
				}
				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
				}

				if(temPermissaoPercentualCobrancaExcedente){

					httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
					httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

				}else{

					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}

				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
									.setValorParcelas(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
													.getValorDebito());
				}
			}else{
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}
		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdOrdemServico("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setMatriculaImovel("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setInscricaoImovel("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setClienteUsuario("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setCpfCnpjCliente("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoLigacaoAgua("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoLigacaoEsgoto("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDataRestabelecimento("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdTipoDebito("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setDescricaoTipoDebito("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setQuantidadeParcelas("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setValorParcelas("");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setPercentualCobranca("-1");
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setMotivoNaoCobranca("-1");

		}

		String numeroHidrometroAtual = "";
		String tipoMedicao = "";
		Integer leituraRetirada = null;
		if(ordemServico != null){

			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			if(imovel == null){
				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else if(ordemServico.getRegistroAtendimento() != null){
					if(ordemServico.getRegistroAtendimento().getImovel() != null){
						imovel = ordemServico.getRegistroAtendimento().getImovel();
					}else{
						throw new ActionServletException("atencao.ordem.servico.nao.vinculada.imovel");
					}
				}else{
					throw new ActionServletException("atencao.ordem.servico.nao.vinculada.registro.etendimento");
				}

				if(imovel == null){
					throw new ActionServletException("atencao.ordem.servico.nao.vinculada.imovel");
				}
			}

			Short indicadorLigacaoAgua = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getMedicaoTipo().getId().shortValue();
			HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = null;

			if(indicadorLigacaoAgua.shortValue() == MedicaoTipo.LIGACAO_AGUA.intValue()){

				numeroHidrometroAtual = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumero();

				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdTipoMedicao(MedicaoTipo.LIGACAO_AGUA.toString());
				tipoMedicao = MedicaoTipo.DESC_LIGACAO_AGUA;

				FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID, imovel.getLigacaoAgua()
								.getId()));
				filtroMedicaoHistorico.setCampoOrderBy("anoMesReferencia DESC");

				Collection<MedicaoHistorico> colecaoMedicaoHistorico = fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class
								.getName());
				if(!Util.isVazioOrNulo(colecaoMedicaoHistorico)){

					MedicaoHistorico medicaoHistorico = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistorico);
					leituraRetirada = Integer.valueOf(medicaoHistorico.getLeituraAtualFaturamento());

				}

				hidrometroSubstituicaoHistorico = carregarHidrometroInstalacaoHistorico(imovel.getLigacaoAgua()
								.getHidrometroInstalacaoHistorico().getId());

				// Armazena na sessão os dados do Histórico da Instalacao do Hidrômetro
				// (HidrometroInstalacaoHistorico) p/ tipo de medição igual a Ligação de Água
				sessao.setAttribute("restabelecimentoAguaSubstituicaoHidrometroHistorico", hidrometroSubstituicaoHistorico);

			}else if(indicadorLigacaoAgua.shortValue() == MedicaoTipo.POCO.intValue()){

				numeroHidrometroAtual = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro().getNumero();

				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdTipoMedicao(MedicaoTipo.POCO.toString());
				tipoMedicao = MedicaoTipo.DESC_POCO;

				FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID, imovel.getId()));

				Collection<MedicaoHistorico> colecaoMedicaoHistorico = fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class
								.getName());
				if(!Util.isVazioOrNulo(colecaoMedicaoHistorico)){

					MedicaoHistorico medicaoHistorico = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistorico);
					leituraRetirada = Integer.valueOf(medicaoHistorico.getLeituraAtualFaturamento());

				}

				hidrometroSubstituicaoHistorico = carregarHidrometroInstalacaoHistorico(imovel.getHidrometroInstalacaoHistorico().getId());

				// Armazena na sessão os dados do Histórico da Instalacao do Hidrômetro
				// (HidrometroInstalacaoHistorico) p/ tipo de medição igual a
				// Poço
				sessao.setAttribute("restabelecimentoAguaSubstituicaoHidrometroHistorico", hidrometroSubstituicaoHistorico);

			}

			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setNumeroHidrometro(numeroHidrometroAtual);
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setTipoMedicao(tipoMedicao);
			if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada() == null
							|| efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getLeituraRetirada().equalsIgnoreCase(
											"")){
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
								.setLeituraRetirada(leituraRetirada != null ? leituraRetirada.toString() : "");
			}

			// Situação do Hidrômetro
			Hidrometro hidrometroAtual = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroAtual);
			if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdSituacaoHidrometro() == null
							|| efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdSituacaoHidrometro()
											.equalsIgnoreCase("")
							|| efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIdSituacaoHidrometro()
											.equalsIgnoreCase("-1")){
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdSituacaoHidrometro(hidrometroAtual
								.getHidrometroSituacao().getId().toString());
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoHidrometro(hidrometroAtual
								.getHidrometroSituacao().getDescricao());
			}

			// Local de Armazenagem
			if(hidrometroAtual.getHidrometroLocalArmazenagem() != null){

				FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();
				filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID,
								hidrometroAtual.getHidrometroLocalArmazenagem().getId()));

				Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(filtroHidrometroLocalArmazenagem,
								HidrometroLocalArmazenagem.class.getName());
				if(!Util.isVazioOrNulo(colecaoHidrometroLocalArmazenagem)){

					HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
									.retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIdLocalArmazenagem(hidrometroLocalArmazenagem
									.getId().toString());
					efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setLocalArmazenagem(hidrometroLocalArmazenagem
									.getDescricao());
				}
			}
		}

		// Pesquisa o hidrômetro novo
		String numeroHidrometroNovo = (String) httpServletRequest.getParameter("numeroHidrometroNovo");

		if(Util.verificarNaoVazio(numeroHidrometroNovo)){

			Hidrometro hidrometroNovo = fachada.pesquisarHidrometroPeloNumero(numeroHidrometroNovo);

			if(hidrometroNovo == null){
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setNumeroHidrometroNovo("");
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			if(hidrometroNovo.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ActionServletException(
								"atencao.situacao_ligacao_agua_substituicao_invalida_restabelecimento_situacao_hidromentro", null,
								hidrometroNovo.getHidrometroSituacao().getDescricao());
			}else{
				efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setNumeroHidrometroNovo(hidrometroNovo.getNumero());
			}

			if(ordemServico != null && ordemServico.getRegistroAtendimento() != null
							&& ordemServico.getRegistroAtendimento().getImovel() != null){

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade.hidrometroLocalArmazenagem");
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ordemServico.getRegistroAtendimento().getImovel()
								.getId()));

				Collection colecaoImoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				Imovel imovelComLocalidade = (Imovel) Util.retonarObjetoDeColecao(colecaoImoveis);

				if(imovelComLocalidade != null
								&& imovelComLocalidade.getLocalidade().getHidrometroLocalArmazenagem() != null
								&& hidrometroNovo.getHidrometroLocalArmazenagem() != null
								&& !hidrometroNovo.getHidrometroLocalArmazenagem().getId().equals(
												imovelComLocalidade.getLocalidade().getHidrometroLocalArmazenagem().getId())){
					throw new ActionServletException(
									"atencao.hidrometro_local_armazenagem_imovel_diferente_hidrometro_local_armazenagem_hidrometro");
				}

			}

		}
		// Troca de Protecao - default NÃO
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaProtecao() == null){
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIndicadorTrocaProtecao(ConstantesSistema.NAO
							.toString());
		}

		// Troca de Registro - default NÃO
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getIndicadorTrocaRegistro() == null){
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setIndicadorTrocaRegistro(ConstantesSistema.NAO
							.toString());
		}

		// Situação do Cavalete - default COM (SIM)
		if(efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.getSituacaoCavalete() == null){
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setSituacaoCavalete(ConstantesSistema.SIM.toString());
		}

		// Parâmetro que indica obrigatoriedade do campo Funcionário
		String indicadorObrigatoriedadeFuncionario = null;
		try{
			indicadorObrigatoriedadeFuncionario = ParametroCadastro.P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("indicadorObrigatoriedadeFuncionario", indicadorObrigatoriedadeFuncionario);

		return retorno;
	}

	/**
	 * Carrega o objeto HidrometroInstalacaoHistorico a partir do id
	 * 
	 * @author Luciano Galvao
	 * @date 08/03/2013
	 */
	private HidrometroInstalacaoHistorico carregarHidrometroInstalacaoHistorico(Integer hidrometroInstalacaoHistoricoId){

		FiltroHidrometroInstalacaoHistorico filtroHidrometro = new FiltroHidrometroInstalacaoHistorico();
		filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.ID, hidrometroInstalacaoHistoricoId));

		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO_SITUACAO);

		HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico = (HidrometroInstalacaoHistorico) Util.retonarObjetoDeColecao(Fachada
						.getInstancia().pesquisar(filtroHidrometro, HidrometroInstalacaoHistorico.class.getName()));
		return hidrometroSubstituicaoHistorico;
	}

	/*
	 * [FS0013 - Alteração de Valor]
	 * autor: Raphael Rossiter
	 * data: 19/04/2007
	 */
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm form){

		if(servicoTipo.getIndicadorPermiteAlterarValor() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			form.setAlteracaoValor("OK");
		}else{
			form.setAlteracaoValor("");
		}

	}

	/*
	 * Calcular valor da prestação com juros
	 * return: Retorna o valor total do débito
	 * autor: Isilva
	 * data: 28/12/2010
	 */
	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

		BigDecimal valorDebito = new BigDecimal(0);
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		Integer qtdeParcelas = null;

		if(calculaValores != null && calculaValores.equals("S")){

			// [UC] - Calcular Prestação
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

				// valorDebito = new BigDecimal(form.getValorDebito().replace(",","."));
				valorDebito = Util.formatarMoedaRealparaBigDecimal(form.getValorDebito());

				String percentualCobranca = form.getPercentualCobranca();

				if(percentualCobranca == null || percentualCobranca.equalsIgnoreCase("")
								|| percentualCobranca.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.informe_campo", null, "Percentual de Cobrança");
				}

				valorDebito = valorDebito.multiply(Util.converterEmPercentagem(percentualCobranca, 1));

				valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
								new BigDecimal("0.00"));

				valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			if(valorPrestacao != null){
				String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
				form.setValorParcelas(valorPrestacaoComVirgula);
			}else{
				form.setValorParcelas("0,00");
			}

		}else{

			HttpSession sessao = httpServletRequest.getSession(false);
			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			valorDebito = Fachada.getInstancia().obterValorDebito(ordemServico.getServicoTipo().getId(), imovel.getId(), new Short("3"));

			if(valorDebito != null){
				form.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
			}else{
				form.setValorDebito("0");
			}
		}

		return valorDebito;
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Corrêa
	 * @date 27/11/2006
	 */
	private void pesquisarCliente(
					EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm,
					Integer matriculaImovel){

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
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setClienteUsuario(cliente.getNome());
			efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Monta os select´s obrigatorios
	 * 
	 * @author Rafael Corrêa
	 * @date 27/11/2006
	 */
	private void consultaSelectObrigatorio(HttpSession sessao){

		// Filtro para o campo Tipo Debito
		Collection colecaoNaoCobranca = (Collection) sessao.getAttribute("colecaoNaoCobranca");
		if(colecaoNaoCobranca == null){
			FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();

			filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

			colecaoNaoCobranca = Fachada.getInstancia().pesquisar(filtroServicoNaoCobrancaMotivo, ServicoNaoCobrancaMotivo.class.getName());

			if(colecaoNaoCobranca != null && !colecaoNaoCobranca.isEmpty()){
				sessao.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Motivo da Não Cobrança");
			}
		}

		FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();
		filtroHidrometroSituacao.adicionarParametro(new ParametroSimples(FiltroHidrometroSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<HidrometroSituacao> colecaoHidrometroSituacao = Fachada.getInstancia().pesquisar(filtroHidrometroSituacao,
						HidrometroSituacao.class.getName());
		sessao.setAttribute("colecaoHidrometroSituacao", colecaoHidrometroSituacao);

		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();
		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<HidrometroLocalArmazenagem> colecaoHidrometroLocalArmazenagem = Fachada.getInstancia().pesquisar(
						filtroHidrometroLocalArmazenagem, HidrometroLocalArmazenagem.class.getName());

		if(colecaoHidrometroLocalArmazenagem == null || colecaoHidrometroLocalArmazenagem.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Hidrômetro local de instalação");
		}

		sessao.setAttribute("colecaoHidrometroLocalArmazenagem", colecaoHidrometroLocalArmazenagem);

		// Pesquisando local de instalação
		FiltroHidrometroLocalInstalacao filtroHidrometroLocalInstalacao = new FiltroHidrometroLocalInstalacao();
		filtroHidrometroLocalInstalacao.setCampoOrderBy(FiltroHidrometroLocalInstalacao.DESCRICAO);
		filtroHidrometroLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalInstalacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoHidrometroLocalInstalacao = Fachada.getInstancia().pesquisar(filtroHidrometroLocalInstalacao,
						HidrometroLocalInstalacao.class.getName());

		if(colecaoHidrometroLocalInstalacao == null || colecaoHidrometroLocalInstalacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Hidrômetro local de instalação");
		}

		sessao.setAttribute("colecaoHidrometroLocalInstalacao", colecaoHidrometroLocalInstalacao);

		// Pesquisando proteção
		FiltroHidrometroProtecao filtroHidrometroProtecao = new FiltroHidrometroProtecao();
		filtroHidrometroProtecao.setCampoOrderBy(FiltroHidrometroProtecao.DESCRICAO);
		filtroHidrometroProtecao.adicionarParametro(new ParametroSimples(FiltroHidrometroProtecao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoHidrometroProtecao = Fachada.getInstancia().pesquisar(filtroHidrometroProtecao,
						HidrometroProtecao.class.getName());

		if(colecaoHidrometroProtecao == null || colecaoHidrometroProtecao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Hidrômetro proteção");
		}

		sessao.setAttribute("colecaoHidrometroProtecao", colecaoHidrometroProtecao);

		// Pesquisando Motivo da Substituição
		FiltroHidrometroMotivoMovimentacao filtroHidrometroMotivoMovimentacao = new FiltroHidrometroMotivoMovimentacao();
		filtroHidrometroMotivoMovimentacao.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoMovimentacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroHidrometroMotivoMovimentacao.setCampoOrderBy(FiltroHidrometroMotivoMovimentacao.DESCRICAO);
		Collection colecaoHidrometroMotivoMovimentacao = Fachada.getInstancia().pesquisar(filtroHidrometroMotivoMovimentacao,
						HidrometroMotivoMovimentacao.class.getName());

		if(Util.isVazioOrNulo(colecaoHidrometroMotivoMovimentacao)){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Motivo da Substituição");
		}

		sessao.setAttribute("colecaoHidrometroMotivoMovimentacao", colecaoHidrometroMotivoMovimentacao);

	}

}
