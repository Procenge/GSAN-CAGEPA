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

package gcom.gui.faturamento.faturamentosituacaotipo;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;

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
public class InserirFaturamentoSituacaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		FaturamentoSituacaoTipo faturamentoSituacaoTipo = this.preencherObjeto(form);
		
		Integer idFaturamentoSituacaoTipo = (Integer) fachada.inserir(faturamentoSituacaoTipo);
		
		montarPaginaSucesso(httpServletRequest,
						"Faturamento Situacao Tipo " + faturamentoSituacaoTipo.getDescricao() +
		 " inserida com sucesso.",
		 "Inserir outro Faturamento Situacao Tipo",
		 "exibirInserirFaturamentoSituacaoTipoAction.do?menu=sim",
		 "exibirAtualizarFaturamentoSituacaoTipoAction.do?idFaturamentoSituacaoTipo=" +
		idFaturamentoSituacaoTipo, "Atualizar Faturamento Situacao Tipo inserido");

		return retorno;

	}

	private FaturamentoSituacaoTipo preencherObjeto(DynaActionForm form){

		FaturamentoSituacaoTipo objetoRetorno = new FaturamentoSituacaoTipo();
		objetoRetorno.setDescricao((String) form.get("descricao"));
		objetoRetorno.setIndicadorParalisacaoFaturamento(Short.parseShort((String) form.get("indicadorParalisacaoFaturamento")));
		objetoRetorno.setIndicadorFaturamentoParalisacaoEsgoto(Short.parseShort((String) form.get("indicadorFaturamentoParalisacaoEsgoto")));
		objetoRetorno.setIndicadorParalisacaoLeitura(Short.parseShort((String) form.get("indicadorParalisacaoLeitura")));
		objetoRetorno.setIndicadorValidoAgua(Short.parseShort((String) form.get("indicadorValidoAgua")));
		objetoRetorno.setIndicadorValidoEsgoto(Short.parseShort((String) form.get("indicadorValidoEsgoto")));
		objetoRetorno.setIndicadorPercentualEsgoto(Short.parseShort((String) form.get("indicadorPercentualEsgoto")));
		objetoRetorno.setLeituraAnormalidadeLeituraComLeitura(new LeituraAnormalidadeLeitura(Integer.parseInt((String) form
						.get("leituraAnormalidadeLeituraComLeitura"))));
		objetoRetorno.setLeituraAnormalidadeLeituraSemLeitura(new LeituraAnormalidadeLeitura(Integer.parseInt((String) form
						.get("leituraAnormalidadeLeituraSemLeitura"))));
		objetoRetorno.setLeituraAnormalidadeConsumoComLeitura(new LeituraAnormalidadeConsumo(Integer.parseInt((String) form
						.get("leituraAnormalidadeConsumoComLeitura"))));
		objetoRetorno.setLeituraAnormalidadeConsumoSemLeitura(new LeituraAnormalidadeConsumo(Integer.parseInt((String) form
						.get("leituraAnormalidadeConsumoSemLeitura"))));
		objetoRetorno.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		objetoRetorno.setUltimaAlteracao(new Date());

		return objetoRetorno;
	}
}