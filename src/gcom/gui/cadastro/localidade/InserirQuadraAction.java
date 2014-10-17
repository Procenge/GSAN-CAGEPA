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
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.dadocensitario.FiltroIbgeSetorCensitario;
import gcom.cadastro.dadocensitario.IbgeSetorCensitario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.localidade.AreaTipo;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.QuadraPerfil;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.Zeis;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirQuadraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		InserirQuadraActionForm inserirQuadraActionForm = (InserirQuadraActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_QUADRA_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_QUADRA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		String localidadeID = inserirQuadraActionForm.getLocalidadeID();
		String setorComercialCD = inserirQuadraActionForm.getSetorComercialCD();
		String quadraNM = inserirQuadraActionForm.getQuadraNM();
		String bairroID = inserirQuadraActionForm.getBairroID();
		String perfilQuadraID = inserirQuadraActionForm.getPerfilQuadra();
		String areaTipoID = inserirQuadraActionForm.getAreaTipoID();
		String indicadorRedeAgua = inserirQuadraActionForm.getIndicadorRedeAguaAux();
		String indicadorRedeEsgoto = inserirQuadraActionForm.getIndicadorRedeEsgotoAux();
		// String sistemaEsgotoID = inserirQuadraActionForm.getSistemaEsgotoID();
		String baciaID = inserirQuadraActionForm.getBaciaID();
		String distritoOperacionalID = inserirQuadraActionForm.getDistritoOperacionalID();
		String zonaAbastecimentoID = inserirQuadraActionForm.getZonaAbastecimentoID();
		String setorCensitarioID = inserirQuadraActionForm.getSetorCensitarioID();
		String zeisID = inserirQuadraActionForm.getZeisID();
		String rotaCD = inserirQuadraActionForm.getRotaID();

		Quadra quadraInserir = new Quadra();
		Collection colecaoPesquisa = null;
		Localidade objetoLocalidade = null;

		// localidade é obrigatório.
		if(localidadeID == null || localidadeID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}else{
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.localidade_inexistente");
			}else{
				objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA.shortValue())
								&& (objetoLocalidade.getLocalidade() == null || objetoLocalidade.getLocalidade().getId().intValue() == 0)){
					throw new ActionServletException("atencao.localidade_sem_elo");
				}
			}
		}

		// ========================================================================

		// setor comercial é obrigatória.
		SetorComercial objetoSetorComercial = null;
		if(setorComercialCD == null || setorComercialCD.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Setor Comercial");
		}else{
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercial.LOCALIDADE);

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.processo.setorComercialNaoCadastrada");
			}else{
				objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraInserir.setSetorComercial(objetoSetorComercial);
			}
		}

		// ========================================================================

		// O número da quadra é obrigatório.
		if(quadraNM == null || quadraNM.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.required", null, "Quadra");
		}else{
			quadraInserir.setNumeroQuadra(Integer.parseInt(quadraNM));
		}

		// ========================================================================

		// O bairro é obrigatório
		if(bairroID != null && !bairroID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, objetoSetorComercial.getMunicipio().getId()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, bairroID));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			colecaoPesquisa = fachada.pesquisar(filtroBairro, Bairro.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.bairro_inexistente");
			}else{
				Bairro bairro = new Bairro();
				bairro.setId(Integer.valueOf(bairroID));
				quadraInserir.setBairro(bairro);
			}

		}else{
			throw new ActionServletException("atencao.required", null, "Bairro");
		}

		// ========================================================================

		// Perfil da Quadra
		if(perfilQuadraID == null || perfilQuadraID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Perfil da Quadra");
		}else{
			QuadraPerfil objetoQuadraPerfil = new QuadraPerfil();
			objetoQuadraPerfil.setId(Integer.valueOf(perfilQuadraID));
			quadraInserir.setQuadraPerfil(objetoQuadraPerfil);
		}
		// ========================================================================

		// Area Tipo /Localização
		if(areaTipoID != null && !areaTipoID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			AreaTipo objetoAreaTipo = new AreaTipo();
			objetoAreaTipo.setId(Integer.valueOf(areaTipoID));
			quadraInserir.setAreaTipo(objetoAreaTipo);
		}
		// =========================================================================
		// IndicadorRedeAgua
		if(indicadorRedeAgua == null || indicadorRedeAgua.equals("")){
			throw new ActionServletException("atencao.required", null, "Rede de Água");
		}else{
			quadraInserir.setIndicadorRedeAgua(Short.valueOf(indicadorRedeAgua));
		}

		// ========================================================================

		// IndicadorRedeEsgoto
		if(indicadorRedeEsgoto == null || indicadorRedeEsgoto.equals("")){
			throw new ActionServletException("atencao.required", null, "Rede de Esgoto");
		}else{
			quadraInserir.setIndicadorRedeEsgoto(Short.valueOf(indicadorRedeEsgoto));
		}
		// ========================================================================

		if(distritoOperacionalID != null && !distritoOperacionalID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, distritoOperacionalID));

			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Distrito Operacional
			colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.distrito_operacional_inexistente");
			}else{
				DistritoOperacional objetoDistritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraInserir.setDistritoOperacional(objetoDistritoOperacional);
			}
		}else{
			quadraInserir.setDistritoOperacional(null);
		}

		if(zonaAbastecimentoID != null && !zonaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, zonaAbastecimentoID));

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Distrito Operacional
			colecaoPesquisa = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.zona_abastecimento_inexistente");
			}else{
				ZonaAbastecimento objetoZonaAbastecimento = (ZonaAbastecimento) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraInserir.setZonaAbastecimento(objetoZonaAbastecimento);
			}
		}else{
			quadraInserir.setZonaAbastecimento(null);
		}

		// ======================================================================
		if(!Util.isVazioOuBranco(baciaID) && !baciaID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			Bacia objetoBacia = new Bacia();
			objetoBacia.setId(Integer.valueOf(baciaID));
			quadraInserir.setBacia(objetoBacia);
		}

		// ========================================================================

		// Setor Censitário (Opcional)
		if(!Util.isVazioOuBranco(setorCensitarioID)
						&& !setorCensitarioID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			FiltroIbgeSetorCensitario filtroIbgeSetorCensitario = new FiltroIbgeSetorCensitario();
			filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.ID, setorCensitarioID));
			filtroIbgeSetorCensitario.adicionarParametro(new ParametroSimples(FiltroIbgeSetorCensitario.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Setor censitario
			colecaoPesquisa = fachada.pesquisar(filtroIbgeSetorCensitario, IbgeSetorCensitario.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Censitário");
			}else{
				IbgeSetorCensitario objetoIbgeSetorCensitario = new IbgeSetorCensitario();
				objetoIbgeSetorCensitario.setId(Integer.valueOf(setorCensitarioID));
				quadraInserir.setIbgeSetorCensitario(objetoIbgeSetorCensitario);
			}
		}

		// ========================================================================

		// Zeis (Opcional)
		if(Integer.parseInt(zeisID) != ConstantesSistema.NUMERO_NAO_INFORMADO){
			Zeis objetoZeis = new Zeis();
			objetoZeis.setId(Integer.valueOf(zeisID));
			quadraInserir.setZeis(objetoZeis);
		}

		// ========================================================================

		/*
		 * Rota é obrigatório (o setor comercial da quadra tem que ser o mesmo setor) comercial da
		 * rota
		 */
		if(Util.isVazioOuBranco(rotaCD)){

			throw new ActionServletException("atencao.required", null, "Rota");

		}

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);

		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, localidadeID));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, setorComercialCD));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaCD));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna rota
		colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			throw new ActionServletException("atencao.quadra_rota_mesmo_setor_comercial");
		}else{
			Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);

			if(objetoRota.getSetorComercial().getId().intValue() != quadraInserir.getSetorComercial().getId().intValue()){
				throw new ActionServletException("atencao.quadra_rota_mesmo_setor_comercial");
			}else{
				quadraInserir.setRota(objetoRota);
			}
		}

		// ========================================================================

		// Indicador de Uso
		quadraInserir.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		// ========================================================================

		// Verifica se ja existe uma quadra cadastrada com o mesmo numero
		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, quadraInserir.getSetorComercial().getId()));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Integer.valueOf(quadraInserir.getNumeroQuadra())));

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		quadraInserir.setOperacaoEfetuada(operacaoEfetuada);
		quadraInserir.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(quadraInserir);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// Retorna quadra
		colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.quadra_ja_cadastrada", "" + quadraInserir.getNumeroQuadra(),
							setorComercialCD + "-" + objetoSetorComercial.getDescricao(), localidadeID + "-"
											+ objetoLocalidade.getDescricao());
		}

		// ========================================================================

		// Ultima alteração
		quadraInserir.setUltimaAlteracao(new Date());

		Integer idQuadra = null;

		/**
		 * alterado por pedro alexandre dia 16/11/2006
		 * Recupera o usuário logado para passar no metódo de inserir
		 * para verificar se o usuário tem abrangência para inserir a quadra informada
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		idQuadra = fachada.inserirQuadra(quadraInserir, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Quadra de número " + quadraInserir.getNumeroQuadra() + " do setor comercial "
						+ setorComercialCD + "-" + objetoSetorComercial.getDescricao() + " da localidade " + localidadeID + "-"
						+ objetoLocalidade.getDescricao() + " inserida com sucesso.", "Inserir outra Quadra",
						"exibirInserirQuadraAction.do", "exibirAtualizarQuadraAction.do?idRegistroInseridoAtualizar=" + idQuadra,
						"Atualizar Quadra Inserida");

		sessao.removeAttribute("InserirQuadraActionForm");
		sessao.removeAttribute("colecaoPerfilQuadra");
		sessao.removeAttribute("colecaoSistemaEsgoto");
		sessao.removeAttribute("colecaoZeis");
		sessao.removeAttribute("colecaoBacia");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
