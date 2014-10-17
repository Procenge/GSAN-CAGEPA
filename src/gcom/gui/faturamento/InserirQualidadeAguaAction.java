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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.QualidadeAgua;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0596] Inserir Qualidade de Agua
 * 
 * @author Kássia Albuquerque
 * @date 24/07/2007
 * @author eduardo henrique
 * @date 15/07/2008
 *       Adição de novos atributos contidos no UC e
 */

public class InserirQualidadeAguaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirQualidadeAguaActionForm form = (InserirQualidadeAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		QualidadeAgua qualidadeAgua = new QualidadeAgua();

		Collection colecaoQualidadeAgua = (Collection) sessao.getAttribute("colecaoQualidadeAgua");

		if(colecaoQualidadeAgua == null || colecaoQualidadeAgua.isEmpty()){

			// Localidade
			Integer idLocalidade = Util.converterStringParaInteger(form.getIdLocalidade().trim());
			if(idLocalidade == null){
				throw new ActionServletException("atencao.campo.invalido", null, "Localidade");
			}
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(Util.isVazioOrNulo(colecaoLocalidades)){
				throw new ActionServletException("atencao.localidade.inexistente");
			}

			Localidade localidade = ((Localidade) colecaoLocalidades.iterator().next());

			// Setor Comercial
			SetorComercial setorComercial = null;
			if(form.getIdSetorComercial() != null && !form.getIdSetorComercial().toString().trim().equalsIgnoreCase("")){

				Integer idSetorComercial = Util.converterStringParaInteger(form.getIdSetorComercial().trim());
				if(idSetorComercial == null){
					throw new ActionServletException("atencao.campo.invalido", null, "Setor Comercial");
				}

				if(localidade != null){

					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									idSetorComercial));

					Collection colecaoSetorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					if(Util.isVazioOrNulo(colecaoSetorComerciais)){
						throw new ActionServletException("atencao.setor_comercial.inexistente");
					}
					setorComercial = (SetorComercial) colecaoSetorComerciais.iterator().next();
					qualidadeAgua.setSetorComercial(setorComercial);
				}
			}

			// Seta todos os campos que foram preenchidos no formulario
			qualidadeAgua = form.setDadosQualidadeAgua(localidade, setorComercial);

		}

		// Atualiza na base de dados da Qualidade de Agua
		fachada.inserirQualidadeAgua(qualidadeAgua, colecaoQualidadeAgua, usuarioLogado);

		if(colecaoQualidadeAgua != null && !colecaoQualidadeAgua.isEmpty()){
			// Monta a página de sucesso
			montarPaginaSucesso(httpServletRequest, "Qualidade da água com referência  " + form.getReferencia() + " inserida com sucesso.",
							"Inserir outra Qualidade da água", "exibirInserirQualidadeAguaAction.do?menu=sim", null, null);

		}else{
			// Monta a página de sucesso
			montarPaginaSucesso(httpServletRequest, "Qualidade da água com referência  "
							+ Util.formatarAnoMesParaMesAno(qualidadeAgua.getAnoMesReferencia()) + " inserida com sucesso.",
							"Inserir outra Qualidade da água", "exibirInserirQualidadeAguaAction.do?menu=sim",
							"exibirAtualizarQualidadeAguaAction.do?idRegistroAtualizacao=" + qualidadeAgua.getId(),
							"Atualizar Qualidade da água inserida");
		}

		return retorno;
	}
}
