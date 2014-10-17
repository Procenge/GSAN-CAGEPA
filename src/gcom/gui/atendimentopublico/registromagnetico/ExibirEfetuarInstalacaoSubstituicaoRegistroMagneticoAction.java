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

package gcom.gui.atendimentopublico.registromagnetico;

import gcom.atendimentopublico.ordemservico.FiltroServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.cliente.*;
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
 * [UC3063] Efetuar Instala��o/Substitui��o de Registro Magn�tico
 * 
 * @author Leonardo Jos� De S. C. Angelim
 * @date 14/08/2012
 */
public class ExibirEfetuarInstalacaoSubstituicaoRegistroMagneticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("colecaoPercentualCobranca") == null){

			sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
		}

		// -----------------------------------------------------------
		// Verificar permiss�o especial
		boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
		// -----------------------------------------------------------

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("efetuarInstalacaoSubstituicaoRegistroMagnetico");

		EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm = (EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm) actionForm;

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getVeioEncerrarOS() != null && efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm
										.getVeioEncerrarOS().equals("true"))){

			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getVeioEncerrarOS() != null
							&& !efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

		// Variavel responsav�l pelo preenchimento do imovel no formul�rio
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setDataReligacao((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getIdOrdemServico() != null){
			idOrdemServico = efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getIdOrdemServico();
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}
		OrdemServico ordemServico = null;
		String matriculaImovel = null;

		// [FS0001] Validar Ordem de Servi�o.
		if(!Util.isVazioOuBranco(idOrdemServico)){
			ordemServico = fachada.recuperaOSPorIdRegistroMagnetico(Integer.valueOf(idOrdemServico));

			if(ordemServico != null){
				fachada.validarInstalacaoSubstituicaoRegistroMagneticoExibir(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setIdOrdemServico(idOrdemServico);
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				Imovel imovel = null;

				if(ordemServico.getImovel() != null){
					imovel = ordemServico.getImovel();
				}else if(ordemServico.getRegistroAtendimento() != null){
					if(ordemServico.getRegistroAtendimento().getImovel() != null){
						imovel = ordemServico.getRegistroAtendimento().getImovel();
					}else{
						throw new ActionServletException(
										"atencao.ordem_servico_nao_associada_imovel_instalacao_substituicao_registro_magnetico", null,
										ordemServico.getRegistroAtendimento().getId().toString());
					}
				}

				sessao.setAttribute("imovel", imovel);

				ServicoTipo servicoTipo = ordemServico.getServicoTipo();
				boolean temPermissaoPercentualCobrancaExcedente = fachada
								.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
				boolean temPermissaoQuantidadeParcelasExcedente = fachada
								.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					if(sessao.getAttribute("colecaoPercentualCobranca") == null){

						sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
					}

					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm
									.setIdTipoDebito(servicoTipo.getDebitoTipo().getId().toString());
					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setDescricaoTipoDebito(servicoTipo.getDebitoTipo()
									.getDescricao());

					if(!Util.verificarNaoVazio(httpServletRequest.getParameter("objetoConsulta"))){

						BigDecimal valorDebito = this.calcularValores(httpServletRequest, ordemServico,
										efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm);

						efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setQtdeMaxParcelas(fachada.obterQuantidadeParcelasMaxima()
										.toString());

						FiltroServicoNaoCobrancaMotivo filtroMotivoNaoCobranca = new FiltroServicoNaoCobrancaMotivo(
										FiltroServicoNaoCobrancaMotivo.DESCRICAO);
						Collection colecaoNaoCobranca = fachada
										.pesquisar(filtroMotivoNaoCobranca, ServicoNaoCobrancaMotivo.class.getName());

						if(Util.isVazioOrNulo(colecaoNaoCobranca)){
							throw new ActionServletException("atencao.naocadastrado", null, "Motivo da N�o Cobran�a");
						}

						// Verificar permiss�o especial
						if(temPermissaoMotivoNaoCobranca){
							httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
							httpServletRequest.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
							efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

						}

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{

							efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setPercentualCobranca("100");
						}

						if(!temPermissaoQuantidadeParcelasExcedente){

							efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setQuantidadeParcelas("1");
						}else{

							httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente",
											temPermissaoQuantidadeParcelasExcedente);
						}

						if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
							efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito,
											2, true));
						}
					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMotivoNaoCobranca(ordemServico
										.getServicoNaoCobrancaMotivo().getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca()
										.toString());
					}
				}

				if(ordemServico.getDataExecucao() != null){
					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setDataReligacao(Util.formatarData(ordemServico
									.getDataExecucao()));
				}

				matriculaImovel = imovel.getId().toString();
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- In�cio dados do Im�vel---------------*/

				// Matricula Im�vel
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMatriculaImovel(imovel.getId().toString());

				// Inscri��o Im�vel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setInscricaoImovel(inscricaoImovel);

				// Situa��o da Liga��o de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situa��o da Liga��o de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				this.pesquisarCliente(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm);

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

				}

				if(temPermissaoPercentualCobrancaExcedente){

					httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
					httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

				}else{

					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}
				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm
									.setValorParcelas(efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getValorDebito());
				}

			}else{
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
				efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}
		}else{
			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setIdOrdemServico("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMatriculaImovel("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setInscricaoImovel("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setClienteUsuario("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setCpfCnpjCliente("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setSituacaoLigacaoAgua("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setSituacaoLigacaoEsgoto("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setDataReligacao("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setIdTipoDebito("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setDescricaoTipoDebito("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setQuantidadeParcelas("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setValorParcelas("");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setPercentualCobranca("-1");
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setMotivoNaoCobranca("-1");

			sessao.removeAttribute("ordemServico");
		}

		return retorno;
	}

	private BigDecimal calcularValores(HttpServletRequest httpServletRequest, OrdemServico ordemServico,
					EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm form){

		String calculaValores = httpServletRequest.getParameter("calculaValores");

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

				// valorDebito = new BigDecimal(form.getValorDebito().replace(",","."));
				valorDebito = Util.formatarMoedaRealparaBigDecimal(form.getValorDebito());

				form.setValorServico(Util.formataBigDecimal(valorDebito, 2, true));

				String percentualCobranca = form.getPercentualCobranca();

				if(percentualCobranca == null || percentualCobranca.equalsIgnoreCase("")
								|| percentualCobranca.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.informe_campo", null, "Percentual de Cobran�a");
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

	private void pesquisarCliente(
					EfetuarInstalacaoSubstituicaoRegistroMagneticoActionForm efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
						efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.getMatriculaImovel()));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_CLIENTE_TIPO);

		Collection<ClienteImovel> colecaoClienteImovel = this.getFachada().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(!Util.isVazioOrNulo(colecaoClienteImovel)){
			ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			Cliente cliente = clienteImovel.getCliente();

			ClienteTipo clienteTipo = cliente.getClienteTipo();
			Short indicadorPessoaFisicaJuridica = clienteTipo.getIndicadorPessoaFisicaJuridica();

			String documento = "";

			if(indicadorPessoaFisicaJuridica.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}

			// Cliente Nome/CPF-CNPJ
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setClienteUsuario(cliente.getNome());
			efetuarInstalacaoSubstituicaoRegistroMagneticoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}
}
