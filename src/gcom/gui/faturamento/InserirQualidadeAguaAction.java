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
 * @author K�ssia Albuquerque
 * @date 24/07/2007
 * @author eduardo henrique
 * @date 15/07/2008
 *       Adi��o de novos atributos contidos no UC e
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
			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Qualidade da �gua com refer�ncia  " + form.getReferencia() + " inserida com sucesso.",
							"Inserir outra Qualidade da �gua", "exibirInserirQualidadeAguaAction.do?menu=sim", null, null);

		}else{
			// Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Qualidade da �gua com refer�ncia  "
							+ Util.formatarAnoMesParaMesAno(qualidadeAgua.getAnoMesReferencia()) + " inserida com sucesso.",
							"Inserir outra Qualidade da �gua", "exibirInserirQualidadeAguaAction.do?menu=sim",
							"exibirAtualizarQualidadeAguaAction.do?idRegistroAtualizacao=" + qualidadeAgua.getId(),
							"Atualizar Qualidade da �gua inserida");
		}

		return retorno;
	}
}
