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

import gcom.atendimentopublico.ligacaoagua.*;
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
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
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
 * Action respons�vel pela pre-exibi��o da pagina de inserir bairro
 * 
 * @author Leandro Cavalcanti
 * @created 20 de Junho de 2006
 */
public class ExibirEfetuarLigacaoAguaAction
				extends GcomAction {

	/**
	 * Description of the Method
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

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("efetuarLigacaoAgua");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		EfetuarLigacaoAguaActionForm ligacaoAguaActionForm = (EfetuarLigacaoAguaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
		boolean temPermissaoPercentualCobrancaExcedente = fachada.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
		boolean temPermissaoQuantidadeParcelasExcedente = fachada.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

		if(temPermissaoMotivoNaoCobranca){

			httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
		}

		// Pesquisar Funcionario ENTER
		if((ligacaoAguaActionForm.getIdFuncionario() != null && !ligacaoAguaActionForm.getIdFuncionario().equals(""))
						&& (ligacaoAguaActionForm.getDescricaoFuncionario() == null || ligacaoAguaActionForm.getDescricaoFuncionario()
										.equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, ligacaoAguaActionForm.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				ligacaoAguaActionForm.setIdFuncionario("");
				ligacaoAguaActionForm.setDescricaoFuncionario("FUNCION�RIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				ligacaoAguaActionForm.setIdFuncionario(funcionario.getId().toString());
				ligacaoAguaActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcion�rio POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		this.consultaSelectObrigatorio(sessao);

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(ligacaoAguaActionForm.getVeioEncerrarOS() == null || ligacaoAguaActionForm.getVeioEncerrarOS().equals("")){
				veioEncerrarOS = Boolean.FALSE;
			}else{
				if(ligacaoAguaActionForm.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}
		}

		ligacaoAguaActionForm.setVeioEncerrarOS(String.valueOf(veioEncerrarOS));

		// Variavel responsav�l pelo preenchimento do imovel no formul�rio
		String idOrdemServico = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			ligacaoAguaActionForm.setDataLigacao((String) httpServletRequest.getAttribute("dataEncerramento"));

			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(ligacaoAguaActionForm.getIdOrdemServico() != null){
			idOrdemServico = ligacaoAguaActionForm.getIdOrdemServico();
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}

		// [FS0001] Validar Ordem de Servi�o.
		if(idOrdemServico != null && !("").equals(idOrdemServico.trim())){

			OrdemServico ordemServico = fachada.recuperaOSPorId(Integer.valueOf(idOrdemServico));

			if(ordemServico != null){

				BigDecimal valorDebito = new BigDecimal(0);

				fachada.validarLigacaoAguaExibir(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				ligacaoAguaActionForm.setIdOrdemServico(idOrdemServico);
				ligacaoAguaActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				Imovel imovel = ordemServico.getImovel();
				ServicoTipo servicoTipo = ordemServico.getServicoTipo();

				if(servicoTipo != null && servicoTipo.getDebitoTipo() != null){

					ligacaoAguaActionForm.setIdTipoDebito(servicoTipo.getDebitoTipo().getId().toString());
					ligacaoAguaActionForm.setDescricaoTipoDebito(servicoTipo.getDebitoTipo().getDescricao());

					// [FS0013] - Altera��o de Valor
					this.permitirAlteracaoValor(servicoTipo, ligacaoAguaActionForm);

					String calculaValores = httpServletRequest.getParameter("calculaValores");

					SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
					Integer qtdeParcelas = null;

					if(calculaValores != null && calculaValores.equals("S")){

						// [UC0186] - Calcular Presta��o
						BigDecimal taxaJurosFinanciamento = null;
						qtdeParcelas = Integer.valueOf(ligacaoAguaActionForm.getQuantidadeParcelas());

						if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue()
										&& qtdeParcelas.intValue() != 1){
							taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
						}else{
							taxaJurosFinanciamento = new BigDecimal(0);
						}

						BigDecimal valorPrestacao = null;
						if(taxaJurosFinanciamento != null){

							valorDebito = new BigDecimal(ligacaoAguaActionForm.getValorDebito().replace(",", "."));

							String percentualCobranca = ligacaoAguaActionForm.getPercentualCobranca();

							valorDebito = valorDebito.multiply(new BigDecimal(percentualCobranca).divide(new BigDecimal("100")));

							valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
											new BigDecimal("0.00"));

							valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
						}

						if(valorPrestacao == null){
							ligacaoAguaActionForm.setValorParcelas("0,00");
						}else{
							String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
							ligacaoAguaActionForm.setValorParcelas(valorPrestacaoComVirgula);
						}

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{
							ligacaoAguaActionForm.setPercentualCobranca("100");
						}

					}else{

						valorDebito = fachada.obterValorDebito(servicoTipo.getId(), imovel.getId(), Short.valueOf(String
										.valueOf(LigacaoTipo.LIGACAO_AGUA)));

						ligacaoAguaActionForm.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));

						if(temPermissaoPercentualCobrancaExcedente){

							httpServletRequest
											.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);

							httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

						}else{

							ligacaoAguaActionForm.setPercentualCobranca("100");
						}

						if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
							ligacaoAguaActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
						}
					}

					if(!temPermissaoQuantidadeParcelasExcedente){

						ligacaoAguaActionForm.setQuantidadeParcelas("1");
					}else{

						httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
					}

					if(ordemServico.getServicoNaoCobrancaMotivo() != null){
						ligacaoAguaActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getId().toString());
					}

					if(ordemServico.getPercentualCobranca() != null){
						ligacaoAguaActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca().toString());
					}
				}

				if(ordemServico.getDataExecucao() != null){
					ligacaoAguaActionForm.setDataLigacao(Util.formatarData(ordemServico.getDataExecucao()));
				}

				sessao.setAttribute("imovel", imovel);

				String matriculaImovel = imovel.getId().toString();
				ligacaoAguaActionForm.setMatriculaImovel("" + matriculaImovel);

				/*-------------- In�cio dados do Im�vel---------------*/

				sessao.setAttribute("imovel", ordemServico.getImovel());

				if(imovel != null){

					// Matricula Im�vel
					ligacaoAguaActionForm.setMatriculaImovel(imovel.getId().toString());

					// Inscri��o Im�vel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					ligacaoAguaActionForm.setInscricaoImovel(inscricaoImovel);

					// Situa��o da Liga��o de Agua
					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					ligacaoAguaActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

					// Situa��o da Liga��o de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					ligacaoAguaActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

					this.pesquisarCliente(ligacaoAguaActionForm, Integer.valueOf(matriculaImovel));
				}

				this.consultaSelectObrigatorio(sessao);
			}else{
				ligacaoAguaActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
				ligacaoAguaActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}
		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			ligacaoAguaActionForm.setIdOrdemServico("");
			ligacaoAguaActionForm.setMatriculaImovel("");
			ligacaoAguaActionForm.setInscricaoImovel("");
			ligacaoAguaActionForm.setClienteUsuario("");
			ligacaoAguaActionForm.setCpfCnpjCliente("");
			ligacaoAguaActionForm.setSituacaoLigacaoAgua("");
			ligacaoAguaActionForm.setSituacaoLigacaoEsgoto("");
			ligacaoAguaActionForm.setDataLigacao("");
			ligacaoAguaActionForm.setDiametroLigacao("");
			ligacaoAguaActionForm.setMaterialLigacao("");
			ligacaoAguaActionForm.setPerfilLigacao("");
			ligacaoAguaActionForm.setConsumoMinimo("");
			ligacaoAguaActionForm.setIdTipoDebito("");
			ligacaoAguaActionForm.setDescricaoTipoDebito("");
			ligacaoAguaActionForm.setQuantidadeParcelas("");
			ligacaoAguaActionForm.setValorParcelas("");
			ligacaoAguaActionForm.setPercentualCobranca("-1");
			ligacaoAguaActionForm.setMotivoNaoCobranca("-1");
			ligacaoAguaActionForm.setAceitaLacre("2");

			/*
			 * Colocado por Raphael Rossiter em 18/04/2007
			 * [FS0013 - Altera��o de Valor]
			 */
			ligacaoAguaActionForm.setAlteracaoValor("");
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
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarLigacaoAguaActionForm form){

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
	private void pesquisarCliente(EfetuarLigacaoAguaActionForm ligacaoAguaActionForm, Integer matriculaImovel){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matriculaImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}else{
			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			ligacaoAguaActionForm.setClienteUsuario(cliente.getNome());
			ligacaoAguaActionForm.setCpfCnpjCliente(documento);
		}
	}

	/**
	 * Monta os select�s obrigatorios
	 * 
	 * @author Rafael Pinto
	 * @date 22/08/2006
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

			if(colecaoDiametroLigacao == null || colecaoDiametroLigacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Diametro da Liga��o");
			}else{
				sessao.setAttribute("colecaoDiametroLigacao", colecaoDiametroLigacao);

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

			if(colecaoRamalLocalInstalacao == null || colecaoRamalLocalInstalacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Local de Instala��o do Ramal");
			}else{
				sessao.setAttribute("colecaoRamalLocalInstalacao", colecaoRamalLocalInstalacao);
			}
		}

	}

}
