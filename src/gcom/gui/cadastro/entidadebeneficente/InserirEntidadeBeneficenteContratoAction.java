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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.EntidadeBeneficenteContrato;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.cadastro.imovel.FiltroEntidadeBeneficenteContrato;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por Inserir Contrato de Entidade Beneficente.
 * 
 * @author Gicevalter Couto
 */
public class InserirEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirEntidadeBeneficenteContratoActionForm form = (InserirEntidadeBeneficenteContratoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Integer idEntidadeBeneficente = null;
		if(form.getIdEntidadeBeneficente() != null){
			idEntidadeBeneficente = Integer.valueOf(form.getIdEntidadeBeneficente().toString().trim());
		}

		// Verifica se a Entidade Beneficente j� est� cadastrada
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID, idEntidadeBeneficente));
		filtroEntidadeBeneficente
						.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO, ConstantesSistema.ATIVO));
		EntidadeBeneficente entidadeBeneficente = (EntidadeBeneficente) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroEntidadeBeneficente, EntidadeBeneficente.class.getName()));
		if(entidadeBeneficente == null){
			throw new ActionServletException("atencao.entidade.inexistente");
		}

		SimpleDateFormat fmtData = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicioContrato = null;
		if (form.getDataInicioContrato() != null && !form.getDataInicioContrato().equals("") ) {
			try{
				dataInicioContrato = fmtData.parse(form.getDataInicioContrato());
			}catch(ParseException e){
				e.printStackTrace();
				throw new ActionServletException(e.getMessage(), e);
			}
		}
		
		Date dataFimContrato = null;
		if (form.getDataFimContrato() != null && !form.getDataFimContrato().equals("") ) {
			try{
				dataFimContrato = fmtData.parse(form.getDataFimContrato());
			}catch(ParseException e){
				e.printStackTrace();
				throw new ActionServletException(e.getMessage(), e);
			}
		}
		
		Date dataEncerramento = null;
		if (form.getDataEncerramento() != null && !form.getDataEncerramento().equals("") ) {
			try{
				dataEncerramento = fmtData.parse(form.getDataEncerramento());
			}catch(ParseException e){
				e.printStackTrace();
				throw new ActionServletException(e.getMessage(), e);
			}
		}	
		
		if (dataFimContrato != null && dataInicioContrato != null) {
			if (dataInicioContrato.compareTo(dataFimContrato) > 0) {
				throw new ActionServletException("atencao.entidade.data.intervalo.fim.invalido");
			}
		}
		
		if (dataEncerramento != null && dataInicioContrato != null) {
			if (dataInicioContrato.compareTo(dataEncerramento) > 0) {
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

		// Verifica se a Entidade Beneficente j� est� cadastrada
		FiltroEntidadeBeneficenteContrato filtroEntidadeBeneficenteContrato = new FiltroEntidadeBeneficenteContrato();
		filtroEntidadeBeneficenteContrato.adicionarParametro(new ParametroSimples(
						FiltroEntidadeBeneficenteContrato.ID_CLIENTE_ENTIDADE_BENEFICIENTE, entidadeBeneficente.getCliente().getId()));

		filtroEntidadeBeneficenteContrato.setCampoOrderByDesc(FiltroEntidadeBeneficenteContrato.DATA_FIM_CONTRATO);

		Collection<EntidadeBeneficenteContrato> colecaoEntidadeBeneficenteContrato = fachada.pesquisar(filtroEntidadeBeneficenteContrato,
						EntidadeBeneficenteContrato.class.getName());
		if(colecaoEntidadeBeneficenteContrato != null && !colecaoEntidadeBeneficenteContrato.isEmpty()){
			for(EntidadeBeneficenteContrato entidadeBeneficenteContrato : colecaoEntidadeBeneficenteContrato){

				if(entidadeBeneficenteContrato.getDataFimContrato() == null){
					throw new ActionServletException("atencao.entidade.sem_data_fim");
				}else{
					if(entidadeBeneficenteContrato.getDataFimContrato().after(dataInicioContrato)){
						throw new ActionServletException("atencao.entidade.data_inicio_menor_data_fim");
					}
				}
			}
		}

		BigDecimal percentualRemuneracao = null;
		if (form.getPercentualRemuneracao() != null && !form.getPercentualRemuneracao().equals("")) {
			percentualRemuneracao = Util.formatarStringMoedaRealparaBigDecimal(form.getPercentualRemuneracao(), 2);
		}
		
		BigDecimal valorRemuneracao = null;
		if (form.getValorRemuneracao() != null && !form.getValorRemuneracao().equals("")) {
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

		EntidadeBeneficenteContrato entidadeBeneficenteContrato = new EntidadeBeneficenteContrato();
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

		Integer idEntidadeBeneficenteContrato = (Integer) fachada.inserir(entidadeBeneficenteContrato);

		montarPaginaSucesso(httpServletRequest, "Contrato da Entidade Beneficente " + form.getNumeroContrato().trim()
						+ " inserido com sucesso.",
						"Inserir outro Contrato de Entidade Beneficente", "exibirInserirEntidadeBeneficenteContratoAction.do?menu=sim",
						"exibirAtualizarEntidadeBeneficenteContratoAction.do?idEntidadeBeneficenteContrato="
										+ idEntidadeBeneficenteContrato,
						"Atualizar Contrato de Entidade Beneficente inserida");

		return retorno;

	}
}