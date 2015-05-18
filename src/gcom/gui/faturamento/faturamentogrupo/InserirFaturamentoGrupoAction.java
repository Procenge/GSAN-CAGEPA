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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.faturamento.faturamentogrupo;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Action
 * 
 * @author Hiroshi Gon�alves
 */
public class InserirFaturamentoGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		FaturamentoGrupo faturamentoGrupo = this.preencherObjeto(form);
		
		try{
			fachada.inserir(faturamentoGrupo);
		}catch(Exception e){
			throw new ActionServletException("atencao.faturamento_grupo.existente", faturamentoGrupo.getId().toString());
		}
		
		montarPaginaSucesso(httpServletRequest,
 "Grupo de Faturamento " + faturamentoGrupo.getDescricao() + " inserido com sucesso.",
						"Inserir outro Grupo de Faturamento",
		 "exibirInserirFaturamentoGrupoAction.do?menu=sim",
		 "exibirAtualizarFaturamentoGrupoAction.do?idFaturamentoGrupo=" +
						 faturamentoGrupo.getId().toString(),
						"Atualizar Grupo de Faturamento");

		return retorno;

	}

	private FaturamentoGrupo preencherObjeto(DynaActionForm form){

		FaturamentoGrupo objetoRetorno = new FaturamentoGrupo();
		objetoRetorno.setId(Integer.parseInt((String) form.get("idFaturamentoGrupo")));
		objetoRetorno.setDescricao((String) form.get("descricao"));
		objetoRetorno.setDescricaoAbreviada((String) form.get("descricaoAbreviada"));
		objetoRetorno.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(((String) form.get("anoMesReferencia"))));
		objetoRetorno.setDiaVencimento(Short.parseShort((String) form.get("diaVencimento")));
		objetoRetorno.setIndicadorVencimentoMesFatura(Short.parseShort((String) form.get("indicadorVencimentoMesFatura")));
		objetoRetorno.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		objetoRetorno.setUltimaAlteracao(new Date());
		if(!Util.isVazioOuBranco(form.get("diaVencimentoDebitoAutomatico"))){

			objetoRetorno.setDiaVencimentoDebitoAutomatico(Short.parseShort((String) form.get("diaVencimentoDebitoAutomatico")));
		}else{

			objetoRetorno.setDiaVencimentoDebitoAutomatico(null);

		}

		if(!Util.isVazioOuBranco(form.get("diaVencimentoEntregaAlternativa"))){

			objetoRetorno.setDiaVencimentoEntregaAlternativa(Short.parseShort((String) form.get("diaVencimentoEntregaAlternativa")));

		}else{

			objetoRetorno.setDiaVencimentoEntregaAlternativa(null);

		}

		return objetoRetorno;
	}
}