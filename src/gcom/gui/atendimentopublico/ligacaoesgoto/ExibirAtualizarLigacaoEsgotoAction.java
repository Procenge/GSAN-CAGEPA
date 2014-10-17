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

package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.ligacaoagua.FiltroRamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pr�-exibi��o da p�gina de atualizar liga��o de esgoto
 * 
 * @author Leonardo Regis
 * @created 18 de Julho de 2006
 */
public class ExibirAtualizarLigacaoEsgotoAction
				extends GcomAction {

	/**
	 * Exibi��o de atualiza��o de liga��o de esgoto.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Session
		HttpSession sessao = httpServletRequest.getSession(false);
		// Seta Retorno (Forward = Exibi��o da Tela de Atualiza��o)
		ActionForward retorno = actionMapping.findForward("atualizarLigacaoEsgoto");
		// Form
		AtualizarLigacaoEsgotoActionForm ligacaoEsgotoActionForm = (AtualizarLigacaoEsgotoActionForm) actionForm;
		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisar Funcionario ENTER
		if((ligacaoEsgotoActionForm.getIdFuncionario() != null && !ligacaoEsgotoActionForm.getIdFuncionario().equals(""))
						&& (ligacaoEsgotoActionForm.getDescricaoFuncionario() == null || ligacaoEsgotoActionForm.getDescricaoFuncionario()
										.equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, ligacaoEsgotoActionForm.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				ligacaoEsgotoActionForm.setIdFuncionario("");
				ligacaoEsgotoActionForm.setDescricaoFuncionario("FUNCION�RIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				ligacaoEsgotoActionForm.setIdFuncionario(funcionario.getId().toString());
				ligacaoEsgotoActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcion�rio POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			veioEncerrarOS = Boolean.FALSE;
		}

		// Seta cole��o de di�metro de liga��o
		getDiametroLigacaoCollection(sessao, fachada);
		// Seta cole��o de material de liga��o
		getLigacaoMaterialCollection(sessao, fachada);
		// Seta cole��o de perfil de liga��o
		getPerfilLigacaoCollection(sessao, fachada);
		// Seta cole��o de ramal local instala��o
		getColecaoRamalLocalInstalacao(sessao, fachada);

		// Testa se � redirecionamento
		if(!isRedirectioned(ligacaoEsgotoActionForm)){
			// Testa matr�cula
			if(ligacaoEsgotoActionForm.getIdOrdemServico() != null && !ligacaoEsgotoActionForm.getIdOrdemServico().equals("")){

				// Recupera Im�vel
				OrdemServico ordemServico = fachada.recuperaOSPorId(new Integer(ligacaoEsgotoActionForm.getIdOrdemServico()));

				if(ordemServico != null){

					// Faz todas as valida��es da liga��o de esgoto do im�vel.
					fachada.validarLigacaoEsgotoImovelExibir(ordemServico, veioEncerrarOS);

					ligacaoEsgotoActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

					Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();

					ligacaoEsgotoActionForm.setIdOrdemServico("" + ordemServico.getId());
					ligacaoEsgotoActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

					// Inscri��o do Im�vel
					String inscricaoImovel = imovel.getInscricaoFormatada();
					ligacaoEsgotoActionForm.setMatriculaImovel("" + imovel.getId());
					ligacaoEsgotoActionForm.setInscricaoImovel(inscricaoImovel);

					// Situa��o da Liga��o de Agua
					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					ligacaoEsgotoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

					// Situa��o da Liga��o de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					ligacaoEsgotoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

					// Seta no form informa��es do cliente
					getCliente(ligacaoEsgotoActionForm, fachada);

					/*-------------------- Liga��o de Esgoto -------------------------------*/
					// Seta no form informa��es da liga��o de esgoto
					setLigacaoEsgotoForm(ligacaoEsgotoActionForm, imovel, fachada);
					/*-------------------- Fim Dados da Liga��o ----------------------------*/
					sessao.setAttribute("osEncontrada", "true");
				}else{
					sessao.removeAttribute("osEncontrada");
					ligacaoEsgotoActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
					ligacaoEsgotoActionForm.setIdOrdemServico("");
				}
			}else{
				httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");
				ligacaoEsgotoActionForm.setDataConcorrencia(new Date());
			}
		}else{
			// Tela redirecionada (Motivo: Altera��o do Perfil de Liga��o)
			// Setando Redirect para estado inicial
			ligacaoEsgotoActionForm.setRedirect("false");
			// Fazendo reload do Percentual de Esgoto
			setPercentualEsgoto(ligacaoEsgotoActionForm, fachada);
		}

		String pPermitirInformarPercentualColeta = null;

		try{

			pPermitirInformarPercentualColeta = (String) ParametroAtendimentoPublico.P_PERMITIR_INFORMAR_PCOLETA_LIGACAO_ESGOTO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		if(pPermitirInformarPercentualColeta.equals(ConstantesSistema.SIM.toString())){

			sessao.setAttribute("pPermitirInformarPercentualColeta", true);
		}else{

			ligacaoEsgotoActionForm.setPercentualColeta("100,00");
		}

		return retorno;
	}

	private void getColecaoRamalLocalInstalacao(HttpSession sessao, Fachada fachada){

		// Filtro para o campo Ramal Local de Instalacao
		Collection colecaoLocalInstalacaoRamal = (Collection) sessao.getAttribute("colecaoLocalInstalacaoRamal");

		if(colecaoLocalInstalacaoRamal == null){

			FiltroRamalLocalInstalacao filtroRamalLocalInstalacao = new FiltroRamalLocalInstalacao();
			filtroRamalLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroRamalLocalInstalacao.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.DESCRICAO);

			colecaoLocalInstalacaoRamal = fachada.pesquisar(filtroRamalLocalInstalacao, RamalLocalInstalacao.class.getName());

			if(colecaoLocalInstalacaoRamal != null && !colecaoLocalInstalacaoRamal.isEmpty()){
				sessao.setAttribute("colecaoLocalInstalacaoRamal", colecaoLocalInstalacaoRamal);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Ramal Local de Instala��o");
			}
		}
	}

	/**
	 * Recupera Cliente do Im�vel
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param ligacaoEsgotoActionForm
	 * @param fachada
	 */
	private void getCliente(AtualizarLigacaoEsgotoActionForm ligacaoEsgotoActionForm, Fachada fachada){

		// Filtra ClienteImovel
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, ligacaoEsgotoActionForm
						.getMatriculaImovel()));
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
		// Recupera Cliente
		Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){
			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();
			Cliente cliente = clienteImovel.getCliente();
			// Seta informa��es do cliente
			ligacaoEsgotoActionForm.setClienteUsuario(cliente.getNome());
			// CPF & CNPJ
			String cpfCnpj = "";
			if(cliente.getCpfFormatado() != null && !cliente.getCpfFormatado().equals("")){
				cpfCnpj = cliente.getCpfFormatado();
			}else{
				cpfCnpj = cliente.getCnpjFormatado();
			}
			ligacaoEsgotoActionForm.setCpfCnpjCliente(cpfCnpj);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Seta as informa��es da liga��o de esgoto no form bean
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param ligacaoEsgotoActionForm
	 * @param imovel
	 */
	private void setLigacaoEsgotoForm(AtualizarLigacaoEsgotoActionForm ligacaoEsgotoActionForm, Imovel imovel, Fachada fachada){

		LigacaoEsgoto ligacaoEsgoto = imovel.getLigacaoEsgoto();

		if(ligacaoEsgoto != null){

			// Data de Ligacao
			String dataLigacao = Util.formatarData(ligacaoEsgoto.getDataLigacao());
			ligacaoEsgotoActionForm.setDataLigacao(dataLigacao);

			if(ligacaoEsgoto.getLigacaoEsgotoDiametro() != null && ligacaoEsgoto.getLigacaoEsgotoDiametro().getId() != null){
				ligacaoEsgotoActionForm.setDiametroLigacao(ligacaoEsgoto.getLigacaoEsgotoDiametro().getId().toString());
			}
			if(ligacaoEsgoto.getLigacaoEsgotoMaterial() != null && ligacaoEsgoto.getLigacaoEsgotoMaterial().getId() != null){
				ligacaoEsgotoActionForm.setMaterialLigacao(ligacaoEsgoto.getLigacaoEsgotoMaterial().getId().toString());
			}
			if(ligacaoEsgoto.getLigacaoEsgotoPerfil() != null && ligacaoEsgoto.getLigacaoEsgotoPerfil().getId() != null){
				ligacaoEsgotoActionForm.setPerfilLigacao(ligacaoEsgoto.getLigacaoEsgotoPerfil().getId().toString());
			}

			if(ligacaoEsgoto.getPercentualAguaConsumidaColetada() != null){
				ligacaoEsgotoActionForm
								.setPercentualColeta(ligacaoEsgoto.getPercentualAguaConsumidaColetada().toString().replace(".", ","));
			}

			if(ligacaoEsgoto.getIndicadorCaixaGordura() != null){
				ligacaoEsgotoActionForm.setIndicadorCaixaGordura(ligacaoEsgoto.getIndicadorCaixaGordura().toString());
			}

			setPercentualEsgoto(ligacaoEsgotoActionForm, fachada);
		}
	}

	/**
	 * Seta Valor de Percentual de Esgoto de Acordo com o Perfil de Liga��o.
	 * 
	 * @author Leonardo Regis
	 * @date 22/07/2006
	 * @param ligacaoEsgotoActionForm
	 * @param fachada
	 */
	private void setPercentualEsgoto(AtualizarLigacaoEsgotoActionForm ligacaoEsgotoActionForm, Fachada fachada){

		FiltroLigacaoPerfilEsgoto filtroLigacaoEsgotoPerfil = new FiltroLigacaoPerfilEsgoto();
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoPerfilEsgoto.ID, ligacaoEsgotoActionForm
						.getPerfilLigacao()));
		// filtroLigacaoEsgotoPerfil
		// .adicionarCaminhoParaCarregamentoEntidade("percentualEsgotoConsumidaColetada");
		// Recupera Perfil da Liga��o de Esgoto
		Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());
		ligacaoEsgotoActionForm.setPercentualEsgoto(null);
		if(colecaoLigacaoEsgotoPerfil != null && !colecaoLigacaoEsgotoPerfil.isEmpty()){
			LigacaoEsgotoPerfil ligacaoEsgotoPerfil = (LigacaoEsgotoPerfil) colecaoLigacaoEsgotoPerfil.iterator().next();
			if(ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada() != null){
				ligacaoEsgotoActionForm.setPercentualEsgoto(ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada().toString().replace(
								".", ","));
			}
		}
	}

	/**
	 * Carrega cole��o de di�metro da liga��o.
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getDiametroLigacaoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Di�metro da Liga��o
		FiltroDiametroLigacaoEsgoto filtroDiametroLigacaoEsgoto = new FiltroDiametroLigacaoEsgoto();
		filtroDiametroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroDiametroLigacaoEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroDiametroLigacaoEsgoto.setCampoOrderBy(FiltroDiametroLigacaoEsgoto.DESCRICAO);

		Collection colecaoDiametroLigacaoEsgoto = fachada.pesquisar(filtroDiametroLigacaoEsgoto, LigacaoEsgotoDiametro.class.getName());
		if(colecaoDiametroLigacaoEsgoto != null && !colecaoDiametroLigacaoEsgoto.isEmpty()){
			sessao.setAttribute("colecaoDiametroLigacaoEsgoto", colecaoDiametroLigacaoEsgoto);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Diametro da Liga��o");
		}
	}

	/**
	 * Carrega cole��o de material da liga��o.
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getLigacaoMaterialCollection(HttpSession sessao, Fachada fachada){

		// Filtra Material da Liga��o
		FiltroLigacaoMaterialEsgoto filtroMaterialLigacaoEsgoto = new FiltroLigacaoMaterialEsgoto();
		filtroMaterialLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoMaterialEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroMaterialLigacaoEsgoto.setCampoOrderBy(FiltroLigacaoMaterialEsgoto.DESCRICAO);

		Collection colecaoMaterialLigacaoEsgoto = fachada.pesquisar(filtroMaterialLigacaoEsgoto, LigacaoEsgotoMaterial.class.getName());

		if(colecaoMaterialLigacaoEsgoto != null && !colecaoMaterialLigacaoEsgoto.isEmpty()){
			sessao.setAttribute("colecaoMaterialLigacaoEsgoto", colecaoMaterialLigacaoEsgoto);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Material da Liga��o");
		}
	}

	/**
	 * Carrega cole��o de perfil da liga��o.
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getPerfilLigacaoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Perfil da Liga��o
		FiltroLigacaoPerfilEsgoto filtroPerfilLigacaoEsgoto = new FiltroLigacaoPerfilEsgoto();
		filtroPerfilLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoPerfilEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPerfilLigacaoEsgoto.setCampoOrderBy(FiltroLigacaoPerfilEsgoto.DESCRICAO);

		Collection colecaoPerfilLigacaoEsgoto = fachada.pesquisar(filtroPerfilLigacaoEsgoto, LigacaoEsgotoPerfil.class.getName());
		if(colecaoPerfilLigacaoEsgoto != null && !colecaoPerfilLigacaoEsgoto.isEmpty()){
			sessao.setAttribute("colecaoPerfilLigacaoEsgoto", colecaoPerfilLigacaoEsgoto);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Perfil de Liga��o");
		}
	}

	/**
	 * Valida Redirecionamento (Motivo: Altera��o do Perfil da Liga��o)
	 * 
	 * @author Leonardo Regis
	 * @date 22/07/2006
	 * @param form
	 * @return boolean
	 */
	private boolean isRedirectioned(AtualizarLigacaoEsgotoActionForm form){

		boolean toReturn = false;
		if(form.getRedirect().equalsIgnoreCase("true")){
			toReturn = true;
		}
		return toReturn;
	}

}