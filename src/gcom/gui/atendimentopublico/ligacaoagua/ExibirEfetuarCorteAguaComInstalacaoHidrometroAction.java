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

package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.*;
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
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.*;
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
 * Action respons�vel pela pre-exibi��o da pagina de efetuar corte de �gua
 * com instala��o de hidrometro
 * 
 * @author isilva
 * @created 15/12/2010
 */
public class ExibirEfetuarCorteAguaComInstalacaoHidrometroAction
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
		ActionForward retorno = actionMapping.findForward("efetuarCorteAguaComInstalacaoHidrometro");

		EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm = (EfetuarCorteAguaComInstalacaoHidrometroActionForm) actionForm;

		// Seta Cole��es
		getMotivoCorteCollection(sessao);
		getTipoCorteCollection(sessao);
		getMotivoNaoCobrancaCollection(sessao);

		if(httpServletRequest.getParameter("desfazer") != null){
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setSituacaoCavalete(null);
		}

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null
						|| (efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS() != null && efetuarCorteAguaComInstalacaoHidrometroActionForm
										.getVeioEncerrarOS().equals("true"))){

			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS() != null
							&& !efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().equals("")){
				if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		efetuarCorteAguaComInstalacaoHidrometroActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

		this.consultaSelectObrigatorio(sessao);

		// Variavel responsav�l pelo preenchimento do imovel no formul�rio
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");

			efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataCorte((String) httpServletRequest.getAttribute("dataEncerramento"));

			efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataInstalacao((String) httpServletRequest
							.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else{
			if(efetuarCorteAguaComInstalacaoHidrometroActionForm.getIdOrdemServico() != null){
				idOrdemServico = efetuarCorteAguaComInstalacaoHidrometroActionForm.getIdOrdemServico();
			}
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}
		OrdemServico ordemServico = null;
		String matriculaImovel = null;

		// [FS0001] Validar Ordem de Servi�o.
		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			ordemServico = fachada.recuperaOSPorId(Integer.valueOf(idOrdemServico));

			if(ordemServico != null){

				fachada.validarCorteAguaComInstalacaoHidrometroExibir(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				efetuarCorteAguaComInstalacaoHidrometroActionForm.setIdOrdemServico(idOrdemServico);
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

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

				// Preencher dados do Corte da Liga��o
				this.pesquisarDadosCorteLigacao(sessao, efetuarCorteAguaComInstalacaoHidrometroActionForm, ordemServico);

				ServicoTipo servicoTipo = ordemServico.getServicoTipo();

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					if(sessao.getAttribute("colecaoPercentualCobranca") == null){

						sessao.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());
					}

					efetuarCorteAguaComInstalacaoHidrometroActionForm.setIdTipoDebito(servicoTipo.getDebitoTipo().getId().toString());
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setDescricaoTipoDebito(servicoTipo.getDebitoTipo().getDescricao());

					// [FS0013] - Altera��o de Valor
					this.permitirAlteracaoValor(ordemServico.getServicoTipo(), efetuarCorteAguaComInstalacaoHidrometroActionForm);

					// Colocado por Raphael Rossiter em 04/05/2007 (Analista:
					// Rosana Carvalho)
					if(!Util.verificarNaoVazio(httpServletRequest.getParameter("objetoConsulta"))){
						// Colocado por Raphael Rossiter em 04/05/2007 (Analista: Rosana Carvalho)
						BigDecimal valorDebito = this.calcularValores(httpServletRequest, ordemServico,
										efetuarCorteAguaComInstalacaoHidrometroActionForm);

						efetuarCorteAguaComInstalacaoHidrometroActionForm.setQtdeMaxParcelas(fachada.obterQuantidadeParcelasMaxima()
										.toString());

						// -----------------------------------------------------------
						// Verificar permiss�o especial

						if(temPermissaoMotivoNaoCobranca){
							httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
						}
						// -----------------------------------------------------------

						boolean temPermissaoPercentualCobrancaExcedente = fachada
										.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
						boolean temPermissaoQuantidadeParcelasExcedente = fachada
										.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{

							efetuarCorteAguaComInstalacaoHidrometroActionForm.setPercentualCobranca("100");
						}

						if(!temPermissaoQuantidadeParcelasExcedente){

							efetuarCorteAguaComInstalacaoHidrometroActionForm.setQuantidadeParcelas("1");
						}else{

							httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente",
											temPermissaoQuantidadeParcelasExcedente);
						}
						if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
							efetuarCorteAguaComInstalacaoHidrometroActionForm
											.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
						}
					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						efetuarCorteAguaComInstalacaoHidrometroActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo()
										.getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						efetuarCorteAguaComInstalacaoHidrometroActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca()
										.toString());
					}
				}

				if(ordemServico.getDataExecucao() != null){
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataCorte(Util.formatarData(ordemServico.getDataExecucao()));
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataInstalacao(Util.formatarData(ordemServico.getDataExecucao()));
				}

				matriculaImovel = imovel.getId().toString();
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- In�cio dados do Im�vel---------------*/

				// sessao.setAttribute("imovel", ordemServico.getRegistroAtendimento().getImovel());

				// Matricula Im�vel
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setMatriculaImovel(imovel.getId().toString());

				// Inscri��o Im�vel
				String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setInscricaoImovel(inscricaoImovel);

				// Situa��o da Liga��o de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situa��o da Liga��o de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();

				// if (imovel != null
				// && imovel.getLigacaoEsgoto() != null
				// &&
				// ConstantesSistema.NAO.equals(imovel.getLigacaoEsgoto().getIndicadorFormaLigacao()))
				// {
				// situacaoLigacaoEsgoto += " (FORCA DE LEI) ";
				// }
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				this.pesquisarCliente(efetuarCorteAguaComInstalacaoHidrometroActionForm, Integer.valueOf(matriculaImovel));

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setMotivoNaoCobranca("-1");
				}else{
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setPercentualCobranca("100");
					efetuarCorteAguaComInstalacaoHidrometroActionForm.setQuantidadeParcelas("1");
				}
				//
				// if (ordemServico.getRegistroAtendimento() != null &&
				// ordemServico.getRegistroAtendimento().getQuantidadeParcelas() != null) {
				// efetuarCorteAguaComInstalacaoHidrometroActionForm.setQuantidadeParcelas(ordemServico.getRegistroAtendimento()
				// .getQuantidadeParcelas().toString());
				// }
				//
				// if (ordemServico.getServicoTipo().getUnidadeServico() != null) {
				// efetuarCorteAguaComInstalacaoHidrometroActionForm.setUnidadeMedida(ordemServico.getServicoTipo().getUnidadeServico()
				// .getDescricao());
				// } else {
				// efetuarCorteAguaComInstalacaoHidrometroActionForm.setUnidadeMedida(null);
				// }

			}else{
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}
		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			efetuarCorteAguaComInstalacaoHidrometroActionForm.setIdOrdemServico("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setMatriculaImovel("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setInscricaoImovel("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setClienteUsuario("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setCpfCnpjCliente("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setSituacaoLigacaoAgua("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setSituacaoLigacaoEsgoto("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataCorte("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setDataInstalacao("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setIdTipoDebito("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setDescricaoTipoDebito("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setQuantidadeParcelas("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setValorParcelas("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setPercentualCobranca("-1");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setMotivoNaoCobranca("-1");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setValorServico("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setUnidadeMedida("");
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setQuantidade("");

		}

		// String numeroHidrometro =
		// efetuarCorteAguaComInstalacaoHidrometroActionForm.getNumeroHidrometro();
		String numeroHidrometro = (String) httpServletRequest.getParameter("numeroHidrometro");

		// Verificar se o n�mero do hidr�metro n�o est� cadastrado

		if(numeroHidrometro != null && !numeroHidrometro.trim().equals("")){

			// Pesquisa o hidr�metro
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(numeroHidrometro);

			if(hidrometro == null){
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setNumeroHidrometro("");
				throw new ActionServletException("atencao.hidrometro_inexistente");
			}

			if(hidrometro.getHidrometroSituacao().getId().intValue() != HidrometroSituacao.DISPONIVEL){
				throw new ActionServletException("atencao.situacao_hidrometro_nao_permite_corte_com_instalao_hidrometro", null, hidrometro
								.getHidrometroSituacao().getDescricao());
			}else{
				efetuarCorteAguaComInstalacaoHidrometroActionForm.setNumeroHidrometro(hidrometro.getNumero());
			}

		}

		return retorno;
	}

	/*
	 * [FS0013 - Altera��o de Valor]
	 * autor: Raphael Rossiter data: 19/04/2007
	 */
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarCorteAguaComInstalacaoHidrometroActionForm form){

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
					EfetuarCorteAguaComInstalacaoHidrometroActionForm form){

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

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 27/11/2006
	 */
	private void pesquisarCliente(EfetuarCorteAguaComInstalacaoHidrometroActionForm efetuarCorteAguaComInstalacaoHidrometroActionForm,
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
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setClienteUsuario(cliente.getNome());
			efetuarCorteAguaComInstalacaoHidrometroActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Monta os select�s obrigatorios
	 * 
	 * @author Rafael Corr�a
	 * @date 27/11/2006
	 */
	private void consultaSelectObrigatorio(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		// Filtro para o campo Diametro Liga��o �gua
		Collection colecaoDiametroLigacao = (Collection) sessao.getAttribute("colecaoDiametroLigacaoAgua");

		if(colecaoDiametroLigacao == null){

			FiltroDiametroLigacao filtroDiametroLigacao = new FiltroDiametroLigacao();

			filtroDiametroLigacao.adicionarParametro(new ParametroSimples(FiltroDiametroLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroDiametroLigacao.setCampoOrderBy(FiltroDiametroLigacao.DESCRICAO);

			colecaoDiametroLigacao = fachada.pesquisar(filtroDiametroLigacao, LigacaoAguaDiametro.class.getName());

			if(colecaoDiametroLigacao != null && !colecaoDiametroLigacao.isEmpty()){
				sessao.setAttribute("colecaoDiametroLigacao", colecaoDiametroLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Diametro da Liga��o");
			}
		}

		// Filtro para o campo Material da Liga��o
		Collection colecaoMaterialLigacao = (Collection) sessao.getAttribute("colecaoMaterialLigacao");

		if(colecaoMaterialLigacao == null){

			FiltroMaterialLigacao filtroMaterialLigacao = new FiltroMaterialLigacao();
			filtroMaterialLigacao.adicionarParametro(new ParametroSimples(FiltroMaterialLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroMaterialLigacao.setCampoOrderBy(FiltroMaterialLigacao.DESCRICAO);

			colecaoMaterialLigacao = fachada.pesquisar(filtroMaterialLigacao, LigacaoAguaMaterial.class.getName());

			if(colecaoMaterialLigacao != null && !colecaoMaterialLigacao.isEmpty()){
				sessao.setAttribute("colecaoMaterialLigacao", colecaoMaterialLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Material da Liga��o");
			}
		}

		// Filtro para o campo Perfil da Liga��o
		Collection colecaoPerfilLigacao = (Collection) sessao.getAttribute("colecaoPerfilLigacao");

		if(colecaoPerfilLigacao == null){

			FiltroPerfilLigacao filtroPerfilLigacao = new FiltroPerfilLigacao();
			filtroPerfilLigacao.adicionarParametro(new ParametroSimples(FiltroPerfilLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroPerfilLigacao.setCampoOrderBy(FiltroPerfilLigacao.DESCRICAO);
			colecaoPerfilLigacao = fachada.pesquisar(filtroPerfilLigacao, LigacaoAguaPerfil.class.getName());

			if(colecaoPerfilLigacao != null && !colecaoPerfilLigacao.isEmpty()){
				sessao.setAttribute("colecaoPerfilLigacao", colecaoPerfilLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Material da Liga��o");
			}
		}

		// Filtro para o campo Tpo Debito
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

		// Filtro para o campo Ramal local instalacao
		Collection colecaoRamalLocalInstalacao = (Collection) sessao.getAttribute("colecaoRamalLocalInstalacao");

		if(colecaoRamalLocalInstalacao == null){

			FiltroRamalLocalInstalacao filtroRamalLocalInstalacao = new FiltroRamalLocalInstalacao();
			filtroRamalLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroRamalLocalInstalacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroRamalLocalInstalacao.setCampoOrderBy(FiltroPerfilLigacao.DESCRICAO);

			colecaoRamalLocalInstalacao = fachada.pesquisar(filtroRamalLocalInstalacao, RamalLocalInstalacao.class.getName());

			if(colecaoRamalLocalInstalacao != null && !colecaoRamalLocalInstalacao.isEmpty()){
				sessao.setAttribute("colecaoRamalLocalInstalacao", colecaoRamalLocalInstalacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Local de Instala��o do Ramal");
			}
		}

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

		sessao.setAttribute("colecaoHidrometroLocalInstalacao", colecaoHidrometroLocalInstalacao);

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
		sessao.setAttribute("colecaoHidrometroProtecao", colecaoHidrometroProtecao);
	}

	/**
	 * Carrega cole��o de motivo do corte.
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param sessao
	 */
	private void getMotivoCorteCollection(HttpSession sessao){

		// Filtro para o campo Motivo do Corte
		FiltroMotivoCorte filtroMotivoCorteLigacaoAgua = new FiltroMotivoCorte();
		filtroMotivoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroMotivoCorteLigacaoAgua.setCampoOrderBy(FiltroMotivoCorte.DESCRICAO);

		Collection colecaoMotivoCorteLigacaoAgua = this.getFachada().pesquisar(filtroMotivoCorteLigacaoAgua, MotivoCorte.class.getName());
		if(colecaoMotivoCorteLigacaoAgua != null && !colecaoMotivoCorteLigacaoAgua.isEmpty()){
			sessao.setAttribute("colecaoMotivoCorteLigacaoAgua", colecaoMotivoCorteLigacaoAgua);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo do Corte");
		}
	}

	/**
	 * Carrega cole��o de tipo do corte.
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param sessao
	 */
	private void getTipoCorteCollection(HttpSession sessao){

		// Filtro para o campo Motivo do Corte
		FiltroCorteTipo filtroTipoCorteLigacaoAgua = new FiltroCorteTipo();
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_CORTE_ADMINISTRATIVO,
						ConstantesSistema.NAO));
		filtroTipoCorteLigacaoAgua.setCampoOrderBy(FiltroCorteTipo.DESCRICAO);

		Collection colecaoTipoCorteLigacaoAgua = this.getFachada().pesquisar(filtroTipoCorteLigacaoAgua, CorteTipo.class.getName());

		if(colecaoTipoCorteLigacaoAgua != null && !colecaoTipoCorteLigacaoAgua.isEmpty()){
			sessao.setAttribute("colecaoTipoCorteLigacaoAgua", colecaoTipoCorteLigacaoAgua);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo do Corte");
		}
	}

	/**
	 * Carrega cole��o de motivo do corte.
	 * 
	 * @author isilva
	 * @date 16/12/2010
	 * @param sessao
	 */
	private void getMotivoNaoCobrancaCollection(HttpSession sessao){

		// Filtra Motivo da N�o Cobran�a
		FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();
		filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroServicoNaoCobrancaMotivo.DESCRICAO);

		Collection colecaoServicoNaoCobrancaMotivo = this.getFachada().pesquisar(filtroServicoNaoCobrancaMotivo,
						ServicoNaoCobrancaMotivo.class.getName());
		if(colecaoServicoNaoCobrancaMotivo != null && !colecaoServicoNaoCobrancaMotivo.isEmpty()){
			sessao.setAttribute("colecaoNaoCobranca", colecaoServicoNaoCobrancaMotivo);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo N�o Cobran�a");
		}
	}

	/**
	 * Preencher dados do corte da liga��o
	 * 
	 * @author isilva
	 * @date 17/12/2010
	 * @param sessao
	 * @param form
	 * @param os
	 */
	private void pesquisarDadosCorteLigacao(HttpSession sessao, EfetuarCorteAguaComInstalacaoHidrometroActionForm form,
					OrdemServico ordemServico){

		// Data Execu��o
		if(ordemServico.getDataExecucao() != null && !ordemServico.getDataExecucao().toString().equalsIgnoreCase("")){
			form.setDataCorte(Util.formatarData(ordemServico.getDataExecucao()));
		}

		// Comentado por Raphael Rossiter em 28/02/2007
		// Motivo do Corte
		/*
		 * if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getMotivoCorte() !=
		 * null){
		 * 
		 * form.setMotivoCorte(""+ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().
		 * getMotivoCorte().getId());
		 * }
		 * // Tipo do Corte
		 * if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getCorteTipo() !=
		 * null) {
		 * form.setTipoCorte(""+ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().
		 * getCorteTipo().getId());
		 * }
		 * // Leitura do Corte
		 * HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico =
		 * ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().
		 * getHidrometroInstalacaoHistorico();
		 * if(hidrometroInstalacaoHistorico != null &&
		 * hidrometroInstalacaoHistorico.getNumeroLeituraCorte() != null){
		 * form.setNumLeituraCorte(""+hidrometroInstalacaoHistorico.getNumeroLeituraCorte());
		 * }
		 * // N�mero do Selo do Corte
		 * 
		 * if(ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua().getNumeroSeloCorte()
		 * != null){
		 * 
		 * form.setNumSeloCorte(""+ordemServico.getRegistroAtendimento().getImovel().getLigacaoAgua()
		 * .getNumeroSeloCorte());
		 * }
		 */

		if(ordemServico.getImovel().getLigacaoAgua() != null){
			if(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte() != null){
				form.setMotivoCorte("" + ordemServico.getImovel().getLigacaoAgua().getMotivoCorte().getId());
			}
		}
		// Tipo do Corte
		if(ordemServico.getImovel().getLigacaoAgua() != null){
			if(ordemServico.getImovel().getLigacaoAgua().getCorteTipo() != null){
				form.setTipoCorte("" + ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getId());
			}
		}
		// Leitura do Corte
		if(ordemServico.getImovel().getLigacaoAgua() != null){
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = ordemServico.getImovel().getLigacaoAgua()
							.getHidrometroInstalacaoHistorico();

			if(hidrometroInstalacaoHistorico != null && hidrometroInstalacaoHistorico.getNumeroLeituraCorte() != null){
				form.setNumLeituraCorte("" + hidrometroInstalacaoHistorico.getNumeroLeituraCorte());
			}
		}
		// N�mero do Selo do Corte
		if(ordemServico.getImovel().getLigacaoAgua() != null){
			if(ordemServico.getImovel().getLigacaoAgua().getNumeroSeloCorte() != null){
				form.setNumSeloCorte("" + ordemServico.getImovel().getLigacaoAgua().getNumeroSeloCorte());
			}
		}
	}
}