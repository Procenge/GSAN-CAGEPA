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
 * Ivan S�rgio Virginio da Silva J�nior
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.medicao.FiltroMedicaoTipo;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// ============ 3 aba caracteristicas ==========================

		ActionForward retorno = actionMapping.findForward("filtrarImovelEmissaoOrdensSeletivasCaracteristicas");

		// obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		// Im�vel Condom�nio
		String imovelCondominioStr = imovelEmissaoOrdensSeletivas.getImovelCondominio();

		if(Util.isVazioOuBranco(imovelCondominioStr)){
			imovelEmissaoOrdensSeletivas.setImovelCondominio(ConstantesSistema.NAO.toString());
		}

		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(!Util.isVazioOuBranco(objetoConsulta) && imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao() != null){
			String exibirIntervaloDataCorte = "N";
			if(imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao().length == 1){
				if("1".equals(objetoConsulta)){
					// Combo Situa��o da Liga��o de �gua

					String[] ligacaoAguaSituacao = imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao();

					if(!Util.isVazioOuBranco(ligacaoAguaSituacao)){
						Integer idLigacaoAguaSituacao = Integer.parseInt(ligacaoAguaSituacao[0]);

						if(LigacaoAguaSituacao.CORTADO.equals(idLigacaoAguaSituacao)){
							exibirIntervaloDataCorte = "S";
						}
					}

				}
			}
			sessao.setAttribute("exibirIntervaloDataCorte", exibirIntervaloDataCorte);
		}

		Fachada fachada = Fachada.getInstancia();

		Collection<ImovelPerfil> collectionImovelPerfil = null;
		Collection<Categoria> collectionImovelCategoria = null;
		Collection<Subcategoria> collectionImovelSubcategoria = null;
		Collection<AreaConstruidaFaixa> collectionIntervaloAreaConstruidaPredefinida = null;
		Collection<MedicaoTipo> collectionTipoMedicao = null;
		Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = null;
		Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = null;

		Integer categoria = 0;

		if(imovelEmissaoOrdensSeletivas.getCategoria() != null && imovelEmissaoOrdensSeletivas.getCategoria().length == 1){
			categoria = Integer.parseInt(imovelEmissaoOrdensSeletivas.getCategoria()[0]);
		}
		// Lista o Perfil do Imovel
		if(imovelEmissaoOrdensSeletivas.getPerfilImovel() == null){
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
			collectionImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			if(collectionImovelPerfil == null || collectionImovelPerfil.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Im�vel");
			}

			sessao.setAttribute("collectionImovelPerfil", collectionImovelPerfil);
		}

		// Lista de Categoria
		if(imovelEmissaoOrdensSeletivas.getCategoria() == null){
			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.SIM));
			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			collectionImovelCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
			if(collectionImovelCategoria == null || collectionImovelCategoria.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Categoria");
			}

			sessao.setAttribute("collectionImovelCategoria", collectionImovelCategoria);
		}

		if(imovelEmissaoOrdensSeletivas.getCategoria() != null && imovelEmissaoOrdensSeletivas.getCategoria().length == 1
						&& !imovelEmissaoOrdensSeletivas.getCategoria()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			// Lista de SubCategorias
			FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();

			filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, categoria));

			// if(categoria != 0
			// && !imovelEmissaoOrdensSeletivas.getCategoria().trim().equalsIgnoreCase(
			// Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			//
			// filtroSubcategoria.adicionarParametro(new
			// ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, categoria));
			// }
			filtroSubcategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

			collectionImovelSubcategoria = fachada.pesquisar(filtroSubcategoria, Subcategoria.class.getName());
			if(collectionImovelSubcategoria == null || collectionImovelSubcategoria.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Subcategoria");
			}

			sessao.setAttribute("collectionImovelSubcategoria", collectionImovelSubcategoria);
		}else{
			sessao.setAttribute("collectionImovelSubcategoria", null);
		}
		
		// Lista de LigacaoAguaSituacao
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Caso o subgrupo do tipo de servi�o selecionado corresponda � substitui��o ou retirada ou
		// remanejamento de hidr�metro ser�o exibidas as situa��es de liga��o de �gua permitida
		try{

			if(fachada.comparaServicoTipoSubgrupoSubstituicaoHidrometro(imovelEmissaoOrdensSeletivas.getServicoTipo())){
				String[] idsLigacaoAguaSituacaoPermitidos = ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO.executar()
								.split(",");

				for(int i = 0; i < idsLigacaoAguaSituacaoPermitidos.length; i++){
					filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
									idsLigacaoAguaSituacaoPermitidos[i], ConectorOr.CONECTOR_OR, idsLigacaoAguaSituacaoPermitidos.length));

					
				}
			}
		}catch(ControladorException e1){

			e1.printStackTrace();

		}

		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

		colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);

		if(Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Liga��o de �gua");
		}

		// Lista de LigacaoEsgotoSituacao
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

		if(Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Liga��o de Esgoto");
		}

		sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);

		// Lista As Faixas das Areas Construidas
		if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida() == null){
			FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();

			filtroAreaConstruidaFaixa.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroAreaConstruidaFaixa.setCampoOrderBy(FiltroAreaConstruidaFaixa.CODIGO);

			collectionIntervaloAreaConstruidaPredefinida = fachada
							.pesquisar(filtroAreaConstruidaFaixa, AreaConstruidaFaixa.class.getName());

			if(collectionIntervaloAreaConstruidaPredefinida == null || collectionIntervaloAreaConstruidaPredefinida.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Faixa de Area Construida");
			}

			sessao.setAttribute("collectionIntervaloAreaConstruidaPredefinida", collectionIntervaloAreaConstruidaPredefinida);
		}

		// Tipo Medicao
		if(imovelEmissaoOrdensSeletivas.getTipoMedicao() == null){
			FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();

			filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroMedicaoTipo.setCampoOrderBy(FiltroMedicaoTipo.DESCRICAO);
			collectionTipoMedicao = fachada.pesquisar(filtroMedicaoTipo, MedicaoTipo.class.getName());

			if(collectionTipoMedicao == null || collectionTipoMedicao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Medi��o");
			}

			sessao.setAttribute("collectionTipoMedicao", collectionTipoMedicao);
			imovelEmissaoOrdensSeletivas.setTipoMedicao(Integer.toString(MedicaoTipo.LIGACAO_AGUA));
		}

		// Usado para fazer o controle de navegacao por conta da Aba Local
		sessao.setAttribute("abaAtual", "CARACTERISTICAS");

		if(imovelEmissaoOrdensSeletivas.getServicoTipo() != null){
			Boolean mesmoSubgrupo = false;
			try{
				mesmoSubgrupo = fachada.comparaServicoTipoSubgrupoSubstituicaoHidrometro(imovelEmissaoOrdensSeletivas.getServicoTipo());
			}catch(ControladorException e){
			}
			if(mesmoSubgrupo){
				httpServletRequest.setAttribute("servicoTipo", "SUBSTITUI��O");
			}

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer.valueOf(imovelEmissaoOrdensSeletivas
							.getServicoTipo())));

			ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroServicoTipo,
							ServicoTipo.class.getName()));

			httpServletRequest.setAttribute("tipoOrdem", servicoTipo.getCodigoConstante());
		}
		return retorno;
	}
}