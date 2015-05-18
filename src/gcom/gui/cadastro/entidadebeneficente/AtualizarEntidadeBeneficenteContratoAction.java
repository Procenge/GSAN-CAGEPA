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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.EntidadeBeneficenteContrato;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bsh.ParseException;

/**
 * Classe responsável por atualizar uma Empresa.
 * 
 * @author Gicevalter Couto
 * @date: 08/05/2015
 */
public class AtualizarEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ParseException{

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarEntidadeBeneficenteContratoActionForm form = (AtualizarEntidadeBeneficenteContratoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		EntidadeBeneficenteContrato entidadeBeneficenteContrato = (EntidadeBeneficenteContrato) sessao
						.getAttribute("entidadeBeneficenteContratoAtualizar");

		Integer idEntidadeBeneficente = null;
		if(form.getIdEntidadeBeneficente() != null){
			idEntidadeBeneficente = Integer.valueOf(form.getIdEntidadeBeneficente().toString().trim());
		}

		// Verifica se a Entidade Beneficente já está cadastrada
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID, idEntidadeBeneficente));
		EntidadeBeneficente entidadeBeneficente = (EntidadeBeneficente) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroEntidadeBeneficente, EntidadeBeneficente.class.getName()));
		if(entidadeBeneficente == null){
			throw new ActionServletException("atencao.entidade.inexistente");
		}

		SimpleDateFormat fmtData = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicioContrato = null;
		if(form.getDataInicioContrato() != null && !form.getDataInicioContrato().equals("")){
			try{
				dataInicioContrato = fmtData.parse(form.getDataInicioContrato());
			}catch(java.text.ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date dataFimContrato = null;
		if(form.getDataFimContrato() != null && !form.getDataFimContrato().equals("")){
			try{
				dataFimContrato = fmtData.parse(form.getDataFimContrato());
			}catch(java.text.ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ActionServletException(e.getMessage(), e);
			}
		}

		Date dataEncerramento = null;
		if(form.getDataEncerramento() != null && !form.getDataEncerramento().equals("")){
			try{
				dataEncerramento = fmtData.parse(form.getDataEncerramento());
			}catch(java.text.ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ActionServletException(e.getMessage(), e);
			}
		}

		if(dataFimContrato != null && dataInicioContrato != null){
			if(dataInicioContrato.compareTo(dataFimContrato) > 0){
				throw new ActionServletException("atencao.entidade.data.intervalo.fim.invalido");
			}
		}

		if(dataEncerramento != null && dataInicioContrato != null){
			if(dataInicioContrato.compareTo(dataEncerramento) > 0){
				throw new ActionServletException("atencao.entidade.data.intervalo.encerramento.invalido");
			}
		}

		if(dataFimContrato != null && dataEncerramento == null){
			throw new ActionServletException("atencao.entidade.data.encerramento.obrigatorio");
		}

		if(dataFimContrato == null && dataEncerramento != null){
			throw new ActionServletException("atencao.entidade.data.fim.obrigatorio");
		}

		if(dataEncerramento != null && dataFimContrato != null){
			if(dataFimContrato.compareTo(dataEncerramento) > 0){
				throw new ActionServletException("atencao.entidade.data.intervalo.fim_encerramento.invalido");
			}
		}

		BigDecimal percentualRemuneracao = null;
		if(form.getPercentualRemuneracao() != null && !form.getPercentualRemuneracao().equals("")){
			percentualRemuneracao = Util.formatarStringMoedaRealparaBigDecimal(form.getPercentualRemuneracao(), 2);
		}

		BigDecimal valorRemuneracao = null;
		if(form.getValorRemuneracao() != null && !form.getValorRemuneracao().equals("")){
			valorRemuneracao = Util.formatarStringMoedaRealparaBigDecimal(form.getValorRemuneracao(), 2);
		}

		BigDecimal valorMinimoDoacao = null;
		if(form.getValorMinimoDoacao() != null && !form.getValorMinimoDoacao().equals("")){
			valorMinimoDoacao = Util.formatarStringMoedaRealparaBigDecimal(form.getValorMinimoDoacao(), 2);
		}
		
		BigDecimal valorMaximoDoacao = null;
		if(form.getValorMaximoDoacao() != null && !form.getValorMaximoDoacao().equals("")){
			valorMaximoDoacao = Util.formatarStringMoedaRealparaBigDecimal(form.getValorMaximoDoacao(), 2);
		}

		entidadeBeneficenteContrato.setEntidadeBeneficente(entidadeBeneficente);
		entidadeBeneficenteContrato.setNumeroContrato(form.getNumeroContrato().trim());
		entidadeBeneficenteContrato.setDataInicioContrato(dataInicioContrato);
		entidadeBeneficenteContrato.setDataFimContrato(dataFimContrato);
		entidadeBeneficenteContrato.setDataEncerramento(dataEncerramento);
		entidadeBeneficenteContrato.setPercentualRemuneracao(percentualRemuneracao);
		entidadeBeneficenteContrato.setValorRemuneracao(valorRemuneracao);
		entidadeBeneficenteContrato.setValorMinimoDoacao(valorMinimoDoacao);
		entidadeBeneficenteContrato.setValorMaximoDoacao(valorMaximoDoacao);
		entidadeBeneficenteContrato.setUltimaAlteracao(new Date());

		fachada.atualizar(entidadeBeneficenteContrato);

		sessao.removeAttribute("idEntidadeBeneficenteContrato");

		sessao.removeAttribute("entidadeBeneficenteContratoAtualizar");

		montarPaginaSucesso(httpServletRequest, "Contrato de Entidade Beneficente " + entidadeBeneficenteContrato.getNumeroContrato()
						+ " atualizada com sucesso.", "Manter outro Contrato de Entidade Beneficente",
						"exibirFiltrarEntidadeBeneficenteContratoAction.do?menu=sim");

		return retorno;

	}
}