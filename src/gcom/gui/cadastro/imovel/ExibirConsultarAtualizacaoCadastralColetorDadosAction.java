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

package gcom.gui.cadastro.imovel;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3113] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 08/10/2013
 */

public class ExibirConsultarAtualizacaoCadastralColetorDadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirConsultarAtualizacaoCadastralColetorDados");

		Fachada fachada = Fachada.getInstancia();
		ConsultarAtualizacaoCadastralColetorDadosActionForm form = (ConsultarAtualizacaoCadastralColetorDadosActionForm) actionForm;
		Integer id;
		if(httpServletRequest.getParameter("idRegistro") != null){
			id = Integer.parseInt(httpServletRequest.getParameter("idRegistro"));
		}else{
			id = Integer.parseInt(httpServletRequest.getAttribute("idRegistro").toString());
		}
		Collection colecao = fachada.pesquisarConsultaAtualizacaoCadastralColetorDados(id);
		if(colecao != null && !colecao.isEmpty()){
			Object[] resultado = (Object[]) Util.retonarObjetoDeColecao(colecao);
			form.setInscricao(String.format("%03d", ((BigDecimal) resultado[0]).intValue()) + "."
							+ String.format("%03d", ((BigDecimal) resultado[1]).intValue()) + "."
							+ String.format("%03d", ((BigDecimal) resultado[2]).intValue()) + "."
							+ String.format("%04d", ((BigDecimal) resultado[3]).intValue()) + "."
							+ String.format("%03d", ((BigDecimal) resultado[4]).intValue()));
			form.setNomeCliente(resultado[5] != null ? (String) resultado[5] : "");
			String cpfOuCnpj = "";
			if(resultado[6] != null){
				cpfOuCnpj = (String) resultado[6];
				if(cpfOuCnpj.length() == 11){
					cpfOuCnpj = Util.formatarCpf(cpfOuCnpj);
				}else{
					cpfOuCnpj = Util.formatarCnpj(cpfOuCnpj);
				}
			}
			form.setCpfCnpj(cpfOuCnpj);
			form.setDsLigacaoAguaSituacao(resultado[7] != null ? (String) resultado[7] : "");
			form.setDsLigacaoEsgotoSituacao(resultado[8] != null ? (String) resultado[8] : "");
			String endereco = fachada.pesquisarEndereco(((BigDecimal) resultado[27]).intValue());
			form.setEndereco(endereco != null ? endereco : "");
			form.setFuncionario(resultado[10] != null && resultado[10] != null ? ((BigDecimal) resultado[9]).intValue() + " - "
							+ ((String) resultado[10])
							: "");
			form.setDataLeitura(resultado[11] != null ? Util.formatarDataComHora((Date) resultado[11]) : "");
			form.setAnoMesMovimento(resultado[12] != null ? Util.formatarAnoMesParaMesAno(((BigDecimal) resultado[12]).intValue()) : "");
			form.setNumeroImovel(resultado[13] != null ? ((BigDecimal) resultado[13]).toString() : "");
			form.setNumeroImovelAlteracao(resultado[14] != null ? ((BigDecimal) resultado[14]).toString() : "");
			form.setDsComplementoEndereco(resultado[15] != null ? (String) resultado[15] : "");
			form.setDsComplementoEnderecoAlteracao(resultado[16] != null ? (String) resultado[16] : "");
			form.setNumeroHidrometro(resultado[17] != null ? ((Character) resultado[17]).toString() : "");
			form.setNumeroHidrometroAlteracao(resultado[18] != null ? ((BigDecimal) resultado[18]).toString() : "");
			form.setQntEconomiaResidencial(resultado[19] != null ? ((BigDecimal) resultado[19]).toString() : "");
			form.setQntEconomiaResidencialAlteracao(resultado[20] != null ? ((BigDecimal) resultado[20]).toString() : "");
			form.setQntEconomiaComercial(resultado[21] != null ? ((BigDecimal) resultado[21]).toString() : "");
			form.setQntEconomiaComercialAlteracao(resultado[22] != null ? ((BigDecimal) resultado[22]).toString() : "");
			form.setQntEconomiaIndustrial(resultado[23] != null ? ((BigDecimal) resultado[23]).toString() : "");
			form.setQntEconomiaIndustrialAlteracao(resultado[24] != null ? ((BigDecimal) resultado[24]).toString() : "");
			form.setQntEconomiaPublica(resultado[25] != null ? ((BigDecimal) resultado[25]).toString() : "");
			form.setQntEconomiaPublicaAlteracao(resultado[26] != null ? ((BigDecimal) resultado[26]).toString() : "");
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		return retorno;
	}
}
