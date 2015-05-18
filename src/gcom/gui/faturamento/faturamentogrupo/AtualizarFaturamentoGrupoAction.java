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

package gcom.gui.faturamento.faturamentogrupo;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
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
 * Classe responsável por atualizar.
 * 
 * @author Hiroshi Goncalves
 * @date: 28/02/2014
 */
public class AtualizarFaturamentoGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		DynaActionForm form = (DynaActionForm) actionForm;

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) sessao.getAttribute("faturamentoGrupoAtualizar");

		faturamentoGrupo = this.preencherObjeto(faturamentoGrupo, form);

		fachada.atualizar(faturamentoGrupo);

		montarPaginaSucesso(httpServletRequest, "Grupo de Faturamento " + form.get("descricao") + " atualizado com sucesso.",
						"Realizar outra Manutenção de Grupo de Faturamento", "exibirFiltrarFaturamentoGrupoAction.do?menu=sim");

		sessao.removeAttribute("idFaturamentoGrupo");
		sessao.removeAttribute("faturamentoGrupoAtualizar");

		// devolve o mapeamento de retorno
		return retorno;

	}

	private FaturamentoGrupo preencherObjeto(FaturamentoGrupo objeto, DynaActionForm form){

		objeto.setDescricao((String) form.get("descricao"));
		objeto.setDescricaoAbreviada((String) form.get("descricaoAbreviada"));
		objeto.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(((String) form.get("anoMesReferencia"))));
		objeto.setDiaVencimento(Short.parseShort((String) form.get("diaVencimento")));
		objeto.setIndicadorVencimentoMesFatura(Short.parseShort((String) form.get("indicadorVencimentoMesFatura")));
		objeto.setIndicadorUso(Short.parseShort((String) form.get("indicadorUso")));
		objeto.setUltimaAlteracao(new Date());

		if(!Util.isVazioOuBranco(form.get("diaVencimentoDebitoAutomatico"))){

			objeto.setDiaVencimentoDebitoAutomatico(Short.parseShort((String) form.get("diaVencimentoDebitoAutomatico")));
		}else{

			objeto.setDiaVencimentoDebitoAutomatico(null);

		}

		if(!Util.isVazioOuBranco(form.get("diaVencimentoEntregaAlternativa"))){

			objeto.setDiaVencimentoEntregaAlternativa(Short.parseShort((String) form.get("diaVencimentoEntregaAlternativa")));

		}else{

			objeto.setDiaVencimentoEntregaAlternativa(null);

		}
		return objeto;
	}
}