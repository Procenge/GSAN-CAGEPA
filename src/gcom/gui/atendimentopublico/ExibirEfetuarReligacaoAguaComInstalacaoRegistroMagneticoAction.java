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
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * Descrição da classe
 * 
 * @author Leonardo José De S. C. Angelim
 * @date 14/08/2012
 */
public class ExibirEfetuarReligacaoAguaComInstalacaoRegistroMagneticoAction
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
		boolean temPermissaoPercentualCobrancaExcedente = fachada.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
		boolean temPermissaoQuantidadeParcelasExcedente = fachada.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);
		// -----------------------------------------------------------

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("efetuarReligacaoAguaComInstalacaoRegistroMagnetico");

		EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm = (EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm) actionForm;

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getVeioEncerrarOS() != null && efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm
										.getVeioEncerrarOS().equals("true"))){

			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getVeioEncerrarOS() != null
							&& !efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

		// Variavel responsavél pelo preenchimento do imovel no formulário
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setDataReligacao((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdOrdemServico() != null){
			idOrdemServico = efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getIdOrdemServico();
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

			ordemServico = fachada.recuperaOSPorIdRegistroMagnetico(Integer.valueOf(idOrdemServico));

			if(ordemServico != null){

				// fachada.validarReligacaoAguaComInstalacaoRegistroMagneticoExibir(ordemServico,
				// veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setIdOrdemServico(idOrdemServico);
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setNomeOrdemServico(ordemServico.getServicoTipo()
								.getDescricao());

				Imovel imovel = null;

				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else if(ordemServico.getRegistroAtendimento() != null){
					if(ordemServico.getRegistroAtendimento().getImovel() != null){
						imovel = ordemServico.getRegistroAtendimento().getImovel();
					}else{
						throw new ActionServletException("atencao.ordem_servico_nao_associada_imovel_registro_magnetico", null,
										ordemServico.getRegistroAtendimento().getId().toString());
					}
				}

				if(imovel == null){
					throw new ActionServletException("atencao.ordem_servico.imovel.registro_atendimento.nao.associado");
				}

				sessao.setAttribute("imovel", imovel);

				ServicoTipo servicoTipo = ordemServico.getServicoTipo();

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					if(sessao.getAttribute("colecaoPercentualCobranca") == null){

						sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
					}

					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setIdTipoDebito(servicoTipo.getDebitoTipo().getId()
									.toString());
					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setDescricaoTipoDebito(servicoTipo.getDebitoTipo()
									.getDescricao());

					if(!Util.verificarNaoVazio(httpServletRequest.getParameter("objetoConsulta"))){

						BigDecimal valorDebito = this.calcularValores(httpServletRequest, ordemServico,
										efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm);

						efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setQtdeMaxParcelas(fachada
										.obterQuantidadeParcelasMaxima().toString());

						FiltroServicoNaoCobrancaMotivo filtroMotivoNaoCobranca = new FiltroServicoNaoCobrancaMotivo();
						Collection colecaoNaoCobranca = fachada
										.pesquisar(filtroMotivoNaoCobranca, ServicoNaoCobrancaMotivo.class.getName());

						if(colecaoNaoCobranca == null || colecaoNaoCobranca.isEmpty()){
							throw new ActionServletException("atencao.tabela_servico_nao_cobranca_sem_dados_para_selecao");
						}

						// Verificar permissão especial
						if(temPermissaoMotivoNaoCobranca){
							httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
							httpServletRequest.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
							efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

						}else{
							efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setPercentualCobranca("100");
						}

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{

							efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setPercentualCobranca("100");
						}

						if(!temPermissaoQuantidadeParcelasExcedente){

							efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setQuantidadeParcelas("1");
						}else{

							httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente",
											temPermissaoQuantidadeParcelasExcedente);
						}
						if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
							efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setValorParcelas(Util.formataBigDecimal(
											valorDebito, 2, true));
						}
					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMotivoNaoCobranca(ordemServico
										.getServicoNaoCobrancaMotivo().getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setPercentualCobranca(ordemServico
										.getPercentualCobranca().toString());
					}
				}

				if(ordemServico.getDataExecucao() != null){
					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setDataReligacao(Util.formatarData(ordemServico
									.getDataExecucao()));
				}

				matriculaImovel = imovel.getId().toString();
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- Início dados do Imóvel---------------*/

				// Matricula Imóvel
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMatriculaImovel(imovel.getId().toString());

				// Inscrição Imóvel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setInscricaoImovel(inscricaoImovel);

				// Situação da Ligação de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situação da Ligação de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();

				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				this.pesquisarCliente(efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm);

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

				}else{
					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setPercentualCobranca("100");
					efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setQuantidadeParcelas("1");
				}

			}else{
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}
		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setIdOrdemServico("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMatriculaImovel("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setInscricaoImovel("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setClienteUsuario("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setCpfCnpjCliente("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setSituacaoLigacaoAgua("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setSituacaoLigacaoEsgoto("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setDataReligacao("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setIdTipoDebito("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setDescricaoTipoDebito("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setQuantidadeParcelas("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setValorParcelas("");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setPercentualCobranca("-1");
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

		}

		return retorno;
	}

	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

		BigDecimal valorDebito = new BigDecimal(0);
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		Integer qtdeParcelas = null;

		if(calculaValores != null && calculaValores.equals("S")){

			// [UC0186] - Calcular Prestação
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

				form.setValorServico(Util.formataBigDecimal(valorDebito, 2, true));

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

			// }else if(ordemServico.getRegistroAtendimento() != null){
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

	private void pesquisarCliente(
					EfetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
						efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.getMatriculaImovel()));

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
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setClienteUsuario(cliente.getNome());
			efetuarReligacaoAguaComInstalacaoRegistroMagneticoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

}
