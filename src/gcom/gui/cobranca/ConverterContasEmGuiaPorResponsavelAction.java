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
 */

package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;

/**
 * InformarEntregaDevolucaoDocumentoCobrancaActionForm
 * [UC3044] Informar Entrega/Devolução de Documentos de Cobrança
 * 
 * @author Carlos Chrystian
 * @created 29 de Fevereiro de 2012
 */

public class ConverterContasEmGuiaPorResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ConverterContasEmGuiaPorResponsavelActionForm form = (ConverterContasEmGuiaPorResponsavelActionForm) actionForm;
		
		Integer idClienteResponsavel = null;
		if(!Util.isVazioOuBranco(form.getCodigoCliente())){
			idClienteResponsavel = Util.converterStringParaInteger(form.getCodigoCliente());
		}else{		
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Cliente");
		}
		
		Integer idImovel = null;
		if(!Util.isVazioOuBranco(form.getCodigoImovel())){
			idImovel = Util.converterStringParaInteger(form.getCodigoImovel());
		}else{		
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Imóvel");
		}

		Collection colecaoReferencias = null;
		if(!Util.isVazioOrNulo(form.getColecaoIntervaloReferenciaHelper())){
			colecaoReferencias = form.getColecaoIntervaloReferenciaHelper();
		}else{
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Intervalo de Referência de Conta");
		}
		
		BigDecimal valorJuros = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(form.getJurosParcelamento())){
			valorJuros = Util.formatarMoedaRealparaBigDecimal(form.getJurosParcelamento());
		}
		
		String valorTotalGuiaPagamento = form.getValorTotalGuiaPagamento();
		if(Util.isVazioOuBranco(valorTotalGuiaPagamento)){
			throw new ActionServletException("atencao.cobranca_converter_conta_guia_pagamento", null, "Intervalo de Referência de Conta");
		}		

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		fachada.executarConversaoContasParaGuiaDePagamento(idClienteResponsavel, idImovel, colecaoReferencias, valorJuros);

		// [FS0006 - Verificar sucesso da transação]:
		montarPaginaSucesso(httpServletRequest, " A Guia de Pagamento será gerada para a matrícula : " + idImovel, " ",
						"exibirConverterContasEmGuiaPorResponsavelAction.do?limparForm=OK");

		return retorno;
	}
}
