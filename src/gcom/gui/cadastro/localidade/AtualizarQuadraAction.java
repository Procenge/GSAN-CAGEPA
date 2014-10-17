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
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.operacional.*;
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

public class AtualizarQuadraAction
				extends GcomAction {

	/**
	 * Permite a alteração de Quadras
	 * Inclusão do campo bairro
	 * 
	 * @author Virginia Melo
	 * @date 28/07/2008
	 *       Inclusão do campo grupo de faturamento
	 * @author Virgínia Melo
	 * @date 28/07/2009
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarQuadraActionForm atualizarQuadraActionForm = (AtualizarQuadraActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_QUADRA_ATUALIZAR, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_QUADRA_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		String quadraID = atualizarQuadraActionForm.getQuadraID();
		String localidadeID = atualizarQuadraActionForm.getLocalidadeID();
		String setorComercialCD = atualizarQuadraActionForm.getSetorComercialCD();
		String quadraNM = atualizarQuadraActionForm.getQuadraNM();
		String bairroID = atualizarQuadraActionForm.getBairroID();
		String perfilQuadraID = atualizarQuadraActionForm.getPerfilQuadra();
		String areaTipoID = atualizarQuadraActionForm.getAreaTipoID();
		String indicadorRedeAgua = atualizarQuadraActionForm.getIndicadorRedeAguaAux();
		String indicadorRedeEsgoto = atualizarQuadraActionForm.getIndicadorRedeEsgotoAux();
		// String sistemaEsgotoID = atualizarQuadraActionForm.getSistemaEsgotoID();
		String baciaID = atualizarQuadraActionForm.getBaciaID();
		String distritoOperacionalID = atualizarQuadraActionForm.getDistritoOperacionalID();
		String zonaAbastecimentoID = atualizarQuadraActionForm.getZonaAbastecimentoID();
		String setorCensitarioID = atualizarQuadraActionForm.getSetorCensitarioID();
		String zeisID = atualizarQuadraActionForm.getZeisID();
		String rotaCD = atualizarQuadraActionForm.getRotaID();
		String indicadorUso = atualizarQuadraActionForm.getIndicadorUso();

		Quadra quadraAlterar = (Quadra) sessao.getAttribute("quadraManter");

		if(quadraAlterar == null && sessao.getAttribute("quadraRelatorioImoveis") != null){

			quadraAlterar = (Quadra) sessao.getAttribute("quadraRelatorioImoveis");
		}

		Collection colecaoPesquisa = null;

		quadraAlterar.setId(Integer.valueOf(quadraID));

		// Setor Comercial
		SetorComercial objetoSetorComercial = null;
		if(Util.isVazioOuBranco(setorComercialCD)){

			throw new ActionServletException("atencao.required", null, "Setor Comercial");
		}else{

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSetorComercial.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorComercial.LOCALIDADE);

			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(Util.isVazioOrNulo(colecaoPesquisa)){

				throw new ActionServletException("atencao.processo.setorComercialNaoCadastrada");
			}else{

				objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraAlterar.setSetorComercial(objetoSetorComercial);
			}
		}

		// ========================================================================

		// Quadra
		if(Util.isVazioOuBranco(quadraNM)){

			throw new ActionServletException("atencao.required", null, "Quadra");
		}else{

			quadraAlterar.setNumeroQuadra(Integer.parseInt(quadraNM));
		}

		// ========================================================================

		// Perfil da Quadra
		if(perfilQuadraID == null || perfilQuadraID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			throw new ActionServletException("atencao.required", null, "Perfil da Quadra");
		}else{

			QuadraPerfil objetoQuadraPerfil = new QuadraPerfil();
			objetoQuadraPerfil.setId(Integer.valueOf(perfilQuadraID));
			quadraAlterar.setQuadraPerfil(objetoQuadraPerfil);
		}
		// ========================================================================

		// Bairro
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
				quadraAlterar.setBairro(bairro);
			}

		}

		// ========================================================================

		// Area Tipo /Localização
		if(areaTipoID != null && !areaTipoID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			AreaTipo objetoAreaTipo = new AreaTipo();
			objetoAreaTipo.setId(Integer.valueOf(areaTipoID));
			quadraAlterar.setAreaTipo(objetoAreaTipo);

		}else{

			quadraAlterar.setAreaTipo(null);
		}

		// ========================================================================

		// IndicadorRedeAgua
		if(indicadorRedeAgua == null || indicadorRedeAgua.equals("")){

			throw new ActionServletException("atencao.required", null, "Rede de Água");
		}else{

			quadraAlterar.setIndicadorRedeAgua(Short.valueOf(indicadorRedeAgua));
		}

		// ========================================================================

		// IndicadorRedeEsgoto
		if(indicadorRedeEsgoto == null || indicadorRedeEsgoto.equals("")){

			throw new ActionServletException("atencao.required", null, "Rede de Esgoto");
		}else{

			quadraAlterar.setIndicadorRedeEsgoto(Short.valueOf(indicadorRedeEsgoto));
		}
		// ========================================================================

		DistritoOperacional objetoDistritoOperacional = null;

		if(distritoOperacionalID == null || distritoOperacionalID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			quadraAlterar.setDistritoOperacional(null);
		}else{

			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, distritoOperacionalID));
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Distrito Operacional
			colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if((colecaoPesquisa == null || colecaoPesquisa.isEmpty()) && !indicadorRedeAgua.equals(ConstantesSistema.SIM + "")){

				throw new ActionServletException("atencao.pesquisa.distrito_operacional_inexistente");
			}else{

				objetoDistritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraAlterar.setDistritoOperacional(objetoDistritoOperacional);
			}
		}

		if(zonaAbastecimentoID != null && !zonaAbastecimentoID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, zonaAbastecimentoID));

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Distrito Operacional
			colecaoPesquisa = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

			if((colecaoPesquisa == null || colecaoPesquisa.isEmpty()) && !indicadorRedeAgua.equals(ConstantesSistema.SIM + "")){

				throw new ActionServletException("atencao.pesquisa.zona_abastecimento_inexistente");
			}else{

				ZonaAbastecimento objetoZonaAbastecimento = (ZonaAbastecimento) Util.retonarObjetoDeColecao(colecaoPesquisa);
				quadraAlterar.setZonaAbastecimento(objetoZonaAbastecimento);
			}
		}else{

			quadraAlterar.setZonaAbastecimento(null);
		}

		// ======================================================================

		Bacia objetoBacia = null;

		if(!Util.isVazioOuBranco(baciaID) && !baciaID.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& !indicadorRedeEsgoto.equals(ConstantesSistema.SIM + "")){

			objetoBacia = new Bacia();
			objetoBacia.setId(Integer.valueOf(baciaID));
			quadraAlterar.setBacia(objetoBacia);
		}else{

			quadraAlterar.setBacia(objetoBacia);
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
				quadraAlterar.setIbgeSetorCensitario(objetoIbgeSetorCensitario);
			}
		}

		// ========================================================================

		// Zeis (Opcional)
		if(Integer.parseInt(zeisID) != ConstantesSistema.NUMERO_NAO_INFORMADO){

			Zeis objetoZeis = new Zeis();
			objetoZeis.setId(Integer.valueOf(zeisID));
			quadraAlterar.setZeis(objetoZeis);
		}

		// ========================================================================

		/*
		 * Rota é obrigatório (o setor comercial da quadra tem que ser o mesmo setor comercial da
		 * rota)
		 */
		if(rotaCD == null || rotaCD.equalsIgnoreCase("")){

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

		if(Util.isVazioOrNulo(colecaoPesquisa)){

			throw new ActionServletException("atencao.pesquisa.rota_inexistente");
		}else{

			Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
			if(objetoRota.getSetorComercial().getId().intValue() != quadraAlterar.getSetorComercial().getId().intValue()){

				throw new ActionServletException("atencao.quadra_rota_mesmo_setor_comercial");
			}else{

				quadraAlterar.setRota(objetoRota);
			}
		}

		// ========================================================================

		// Indicador de Uso
		quadraAlterar.setIndicadorUso(Short.valueOf(indicadorUso));

		// ========================================================================

		// Ultima alteração
		quadraAlterar.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		quadraAlterar.setOperacaoEfetuada(operacaoEfetuada);
		quadraAlterar.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(quadraAlterar);
		// ------------ REGISTRAR TRANSAÇÃO ----------------
		
		String proximaAcao = "";

		// [SB0003] - Verificar Mudança de Rota da Quadra
		if(!quadraAlterar.getRota().getId().toString().equals(atualizarQuadraActionForm.getIdRotaAnterior())){

			sessao.setAttribute("rotaAlterada", "sim");
			proximaAcao = "processarAtualizarQuadra";

		}

		// [SB0005] – Verificar Mudança do Distrito Operacional da Quadra
		if((quadraAlterar.getDistritoOperacional() != null)
						&& (!Util.isVazioOuBranco(atualizarQuadraActionForm.getDistritoOperacionalIDAnterior()))
						&& (!quadraAlterar.getDistritoOperacional().getId().toString()
										.equals(atualizarQuadraActionForm
										.getDistritoOperacionalIDAnterior()))){

			sessao.setAttribute("distritoOperacionalAlterado", "sim");
			proximaAcao = "processarAtualizarQuadra";
        }

		if(!Util.isVazioOuBranco(proximaAcao)){
			sessao.setAttribute("quadraRelatorioImoveis", quadraAlterar);
			return actionMapping.findForward("processarAtualizarQuadra");
			
		}else{

			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

			fachada.atualizarQuadra(quadraAlterar, usuarioLogado);

			sessao.removeAttribute("quadraManter");
			sessao.removeAttribute("colecaoPerfilQuadra");
			sessao.removeAttribute("colecaoSistemaEsgoto");
			sessao.removeAttribute("colecaoZeis");
			sessao.removeAttribute("colecaoBacia");
		}

		montarPaginaSucesso(httpServletRequest, "Quadra de número  " + quadraAlterar.getNumeroQuadra() + " do setor comercial "
						+ quadraAlterar.getSetorComercial().getCodigo() + "-" + quadraAlterar.getSetorComercial().getDescricao()
						+ " da localidade " + quadraAlterar.getSetorComercial().getLocalidade().getId() + "-"
						+ quadraAlterar.getSetorComercial().getLocalidade().getDescricao() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Quadra", "exibirFiltrarQuadraAction.do?desfazer=S");
		return retorno;
	}
}
