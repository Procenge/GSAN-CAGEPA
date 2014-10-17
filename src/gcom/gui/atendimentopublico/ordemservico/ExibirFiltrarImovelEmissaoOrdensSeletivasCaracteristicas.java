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
 * Ivan Sérgio Virginio da Silva Júnior
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

		// obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		// Imóvel Condomínio
		String imovelCondominioStr = imovelEmissaoOrdensSeletivas.getImovelCondominio();

		if(Util.isVazioOuBranco(imovelCondominioStr)){
			imovelEmissaoOrdensSeletivas.setImovelCondominio(ConstantesSistema.NAO.toString());
		}

		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(!Util.isVazioOuBranco(objetoConsulta) && imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao() != null){
			String exibirIntervaloDataCorte = "N";
			if(imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao().length == 1){
				if("1".equals(objetoConsulta)){
					// Combo Situação da Ligação de Água

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
				throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Imóvel");
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

		// Caso o subgrupo do tipo de serviço selecionado corresponda à substituição ou retirada ou
		// remanejamento de hidrômetro serão exibidas as situações de ligação de água permitida
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
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Ligação de Água");
		}

		// Lista de LigacaoEsgotoSituacao
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

		if(Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Ligação de Esgoto");
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
				throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Medição");
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
				httpServletRequest.setAttribute("servicoTipo", "SUBSTITUIÇÃO");
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