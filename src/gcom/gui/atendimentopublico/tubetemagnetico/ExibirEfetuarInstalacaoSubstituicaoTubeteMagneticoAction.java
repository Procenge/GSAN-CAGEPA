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

package gcom.gui.atendimentopublico.tubetemagnetico;

import gcom.atendimentopublico.ordemservico.FiltroServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
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
 * [UC3064] Efetuar Instalação/Substituição de Tubete Magnético
 * 
 * @author Leonardo José De S. C. Angelim
 * @date 14/08/2012
 */
public class ExibirEfetuarInstalacaoSubstituicaoTubeteMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("efetuarInstalacaoSubstituicaoTubeteMagnetico");

		EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm = (EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm) actionForm;

		// Verificar permissão especial
		boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
		boolean temPermissaoPercentualCobrancaExcedente = fachada.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
		boolean temPermissaoQuantidadeParcelasExcedente = fachada.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

		if(temPermissaoMotivoNaoCobranca){
			httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
		}
		// -----------------------------------------------------------

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getVeioEncerrarOS() != null && efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
										.getVeioEncerrarOS().equals("true"))){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getVeioEncerrarOS() != null
							&& !efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setVeioEncerrarOS(String.valueOf(veioEncerrarOS));

		// Variavel responsavél pelo preenchimento do imovel no formulário
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setDataReligacao((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getIdOrdemServico() != null){
			idOrdemServico = efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getIdOrdemServico();
		}

		OrdemServico ordemServico = null;

		String matriculaImovel = null;
		Imovel imovel = null;
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorIdTubeteMagnetico(Integer.valueOf(idOrdemServico));

			if(ordemServico != null){

				BigDecimal valorDebito = new BigDecimal(0);

				// [FS0001] Validar Ordem de Serviço.
				fachada.validarOSInstalacaoSubstituicaoTubeteMagnetico(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setIdOrdemServico(idOrdemServico);
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else if(ordemServico.getRegistroAtendimento() != null){
					if(ordemServico.getRegistroAtendimento().getImovel() != null){
						imovel = ordemServico.getRegistroAtendimento().getImovel();
					}else{
						throw new ActionServletException(
										"atencao.ordem_servico_nao_associada_imovel_instalacao_substituicao_tubete_magnetico", null,
										ordemServico.getRegistroAtendimento().getId().toString());
					}
				}

				if(imovel == null){
					throw new ActionServletException("atencao.ordem_servico.imovel.registro_atendimento.nao.associado");
				}
				matriculaImovel = imovel.getId().toString();
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- Início dados do Imóvel---------------*/

				// Matricula Imóvel
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setMatriculaImovel(imovel.getId().toString());

				// Inscrição Imóvel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setInscricaoImovel(inscricaoImovel);

				// Situação da Ligação de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situação da Ligação de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();

				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				this.pesquisarCliente(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm);

				sessao.setAttribute("imovel", imovel);

				// 5. Dados da Geração do Débito
				ServicoTipo servicoTipo = ordemServico.getServicoTipo();

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setIdTipoDebito(servicoTipo.getDebitoTipo().getId().toString());
					efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setDescricaoTipoDebito(servicoTipo.getDebitoTipo()
									.getDescricao());

					FiltroServicoNaoCobrancaMotivo filtroMotivoNaoCobranca = new FiltroServicoNaoCobrancaMotivo();
					filtroMotivoNaoCobranca.setCampoOrderBy(FiltroServicoNaoCobrancaMotivo.DESCRICAO);
					Collection colecaoNaoCobranca = fachada.pesquisar(filtroMotivoNaoCobranca, ServicoNaoCobrancaMotivo.class.getName());

					if(colecaoNaoCobranca == null || colecaoNaoCobranca.isEmpty()){
						throw new ActionServletException("atencao.tabela_servico_nao_cobranca_sem_dados_para_selecao");
					}

					httpServletRequest.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);

					String calculaValores = httpServletRequest.getParameter("calculaValores");
					Integer qtdeParcelas = null;

					if(calculaValores != null && calculaValores.equals("S")){

						// [UC0186] - Calcular Prestação
						BigDecimal taxaJurosFinanciamento = null;
						qtdeParcelas = Integer.valueOf(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getQuantidadeParcelas());

						if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue()
										&& qtdeParcelas.intValue() != 1){
							taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
						}else{
							taxaJurosFinanciamento = new BigDecimal(0);
						}

						BigDecimal valorPrestacao = null;
						if(taxaJurosFinanciamento != null){

							valorDebito = new BigDecimal(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getValorDebito().replace(
											",", "."));
							valorDebito = valorDebito.multiply(new BigDecimal(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
											.getPercentualCobranca()).divide(new BigDecimal("100")));
							valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
											new BigDecimal("0.00"));

							valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
						}

						if(valorPrestacao == null){
							efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setValorParcelas("0,00");
						}else{
							String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
							efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setValorParcelas(valorPrestacaoComVirgula);
						}

					}else{

						valorDebito = fachada.obterValorDebito(servicoTipo.getId(), imovel.getId(), MedicaoTipo.NENHUM);

						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));

					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setMotivoNaoCobranca(ordemServico
										.getServicoNaoCobrancaMotivo().getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca()
										.toString());
					}

					if(temPermissaoPercentualCobrancaExcedente){

						httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
						httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

					}else{

						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setPercentualCobranca("100");
					}

					if(!temPermissaoQuantidadeParcelasExcedente){

						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setQuantidadeParcelas("1");
					}else{

						httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
					}
					if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setValorParcelas(Util
										.formataBigDecimal(valorDebito, 2, true));
					}
				}

				if(ordemServico.getDataExecucao() != null){
					efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setDataReligacao(Util.formatarData(ordemServico
									.getDataExecucao()));
				}

				// [FS0005] – Validar motivo da não cobrança
				this.validarMotivoNaoCobranca(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm, sistemaParametro,
								temPermissaoMotivoNaoCobranca);

			}else{
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}

		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setIdOrdemServico("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setMatriculaImovel("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setInscricaoImovel("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setClienteUsuario("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setCpfCnpjCliente("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setSituacaoLigacaoAgua("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setSituacaoLigacaoEsgoto("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setDataReligacao("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setIdTipoDebito("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setDescricaoTipoDebito("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setQuantidadeParcelas("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setValorParcelas("");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setPercentualCobranca("-1");
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setMotivoNaoCobranca("-1");

		}

		if(sessao.getAttribute("colecaoPercentualCobranca") == null){

			sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
		}

		return retorno;
	}

	private void pesquisarCliente(
					EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
						efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = this.getFachada().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

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
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setClienteUsuario(cliente.getNome());
			efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * [FS0005] – Validar motivo da não cobrança
	 * 
	 * @param efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm
	 * @param usuarioLogado
	 * @param fachada
	 */

	private void validarMotivoNaoCobranca(
					EfetuarInstalacaoSubstituicaoTubeteMagneticoActionForm efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm,
					SistemaParametro sistemaParametro, boolean temPermissaoMotivoNaoCobranca){

		// [FS0005] – Validar motivo da não cobrança
		// . Caso o motivo da não cobrança seja selecionado e o usuário não possua permissão
		// especial para informar o motivo da não cobrança
		// (não existe ocorrência na tabela USUARIO_PERMISSAO_ESPECIAL com USUR_ID=Id do usuário
		// logado e PMEP_ID=PMEP_ID da tabela PERMISSAO_ESPECIAL com
		// PMEP_DSPERMISSAOESPECIAL=”INFORMAR MOTIVO DA NÃO COBRANÇA”), exibir a mensagem “Motivo da
		// Não Cobrança não deve ser selecionado.” e retornar para o passo correspondente no fluxo
		// principal.
		// -----------------------------------------------------------

		if(efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getMotivoNaoCobranca() != null
						&& !efetuarInstalacaoSubstituicaoTubeteMagneticoActionForm.getMotivoNaoCobranca().equals("")
						&& !temPermissaoMotivoNaoCobranca){
			throw new ActionServletException("atencao.motivo_nao_cobranca_nao_deve_ser_selecionado");
		}

	}
}
