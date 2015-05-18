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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.bean.IntervaloReferenciaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author
 * @created
 */

public class ExibirConverterContasEmGuiaPorResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirConverterContasEmGuiaPorResponsavelAction");

		ConverterContasEmGuiaPorResponsavelActionForm form = (ConverterContasEmGuiaPorResponsavelActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String idDigitadoEnterCliente = (String) form.getCodigoCliente();
		String idDigitadoEnterImovel = (String) form.getCodigoImovel();
		Integer idCliente = null;
		
		Integer parametroTipoSolicitacao = null;
		try{
			parametroTipoSolicitacao = Integer.valueOf(ParametroAtendimentoPublico.P_TIPO_SOLICITACAO_RA_CANCELAMENTO_CONTAS_ORGAO_PUBLICO
							.executar());

			if(parametroTipoSolicitacao == null){
				throw new ControladorException("atencao.param_nao_cadastrado", null,
								ParametroAtendimentoPublico.P_TIPO_SOLICITACAO_RA_CANCELAMENTO_CONTAS_ORGAO_PUBLICO.getCodigo());
			}

		}catch(NumberFormatException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String removerIntervaloReferenciasContas = "N";
		if(httpServletRequest.getParameter("removeIntervaloReferencias") != null){
			removerIntervaloReferenciasContas = httpServletRequest.getParameter("removeIntervaloReferencias").toString();
		}
		
		String validarCalcularValorTotalGuia = "N";
		if(httpServletRequest.getParameter("calcularValorTotalGuia") != null){
			validarCalcularValorTotalGuia = httpServletRequest.getParameter("calcularValorTotalGuia").toString();
		}

		// verifica se o codigo do cliente foi digitado
		if(!Util.isVazioOuBrancoOuZero(idDigitadoEnterCliente)){
			this.pesquisarCliente(idDigitadoEnterCliente, fachada, form, httpServletRequest);
		}

		if(!Util.isVazioOuBranco(form.getCodigoCliente())){
			idCliente = Util.converterStringParaInteger(form.getCodigoCliente());
		}

		String alterarCliente = "N";
		if(!Util.isVazioOuBrancoOuZero(idDigitadoEnterImovel)){
			this.pesquisarImovel(idDigitadoEnterImovel, fachada, form, httpServletRequest);

			if(httpServletRequest.getParameter("alterarCliente") != null
							&& httpServletRequest.getParameter("alterarCliente").toString().equals("S")){
				alterarCliente = "S";
			}
		}

		BigDecimal valorTotalListaContas = BigDecimal.ZERO;
		BigDecimal valorJuros = BigDecimal.ZERO;

		// Adiciona
		if(httpServletRequest.getParameter("adicionaIntervaloReferencias") != null){
			if(idCliente == null){
				throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Cliente Responsável");
			}

			this.carregarValorContaPorIntervaloReferencia(fachada, form);
			
			Collection colocaoContas = form.getColecaoIntervaloReferenciaHelper();
			Iterator it = colocaoContas.iterator();
			while(it.hasNext()){
				IntervaloReferenciaHelper itHelper = (IntervaloReferenciaHelper) it.next();

				if(!Util.isVazioOuBranco(itHelper.getValorTotalContas())){
					valorTotalListaContas = valorTotalListaContas.add(Util.formatarMoedaRealparaBigDecimal(itHelper.getValorTotalContas()));
				}
			}
			
			form.setValorTotalListaContas(Util.formatarMoedaReal(valorTotalListaContas, 2));

			form.setReferenciaInicial("");
			form.setReferenciaFinal("");
		}

		// remove
		if(!removerIntervaloReferenciasContas.equals("")){
			form.setIdIntervaloReferenciaHelper(removerIntervaloReferenciasContas);

			form.removeIntervaloReferencia();

			form.setReferenciaInicial("");
			form.setReferenciaFinal("");

			form.setValorTotalGuiaPagamento("");
		}

		if(alterarCliente.equals("S")){
			form.getColecaoIntervaloReferenciaHelper().clear();
			form.setValorTotalGuiaPagamento("");
		}

		sessao.setAttribute("colecaoIntervaloReferenciaHelper", form.getColecaoIntervaloReferenciaHelper());

		// Calcular
		if(validarCalcularValorTotalGuia.equals("S")){
			if(!Util.isVazioOuBranco(form.getValorTotalListaContas())){
				valorTotalListaContas = Util.formatarMoedaRealparaBigDecimal(form.getValorTotalListaContas());
			}
			if(!Util.isVazioOuBranco(form.getJurosParcelamento())){
				valorJuros = Util.formatarMoedaRealparaBigDecimal(form.getJurosParcelamento());
			}
			
			BigDecimal calcularValorTotalGuia = valorTotalListaContas.add(valorJuros);

			form.setValorTotalGuiaPagamento(Util.formatarMoedaReal(calcularValorTotalGuia, 2));
		}

		return retorno;
	}

	private void pesquisarCliente(String idDigitadoEnterCliente, Fachada fachada, ConverterContasEmGuiaPorResponsavelActionForm form,
					HttpServletRequest httpServletRequest){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idDigitadoEnterCliente));
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection collClienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		if(collClienteEncontrado != null && !collClienteEncontrado.isEmpty()){

			Cliente clienteEncontrado = (Cliente) Util.retonarObjetoDeColecao(collClienteEncontrado);
			form.setCodigoCliente(clienteEncontrado.getId().toString());
			form.setNomeCliente(clienteEncontrado.getNome());

			}else{
				httpServletRequest.setAttribute("corCliente", "exception");
				form.setNomeCliente(ConstantesSistema.CODIGO_CLIENTE_INEXISTENTE);
				form.setCodigoCliente("");

			}
	}

	private void pesquisarImovel(String idDigitadoEnterImovel, Fachada fachada, ConverterContasEmGuiaPorResponsavelActionForm form,
					HttpServletRequest httpServletRequest){

		// Pesquisa o imovel na base
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idDigitadoEnterImovel));

		Collection collImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(collImovel);

		if(imovel != null){

			String imovelEncontrado = fachada.pesquisarInscricaoImovel(new Integer(idDigitadoEnterImovel), true);

			// O imovel foi encontrado
			form.setCodigoImovel(idDigitadoEnterImovel);
			form.setInscricaoImovel(imovelEncontrado);

		}else{
			httpServletRequest.setAttribute("corImovel", "exception");
			form.setInscricaoImovel(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
			form.setCodigoImovel("");

		}
	}

	public void carregarValorContaPorIntervaloReferencia(Fachada fachada, ConverterContasEmGuiaPorResponsavelActionForm form){

		IntervaloReferenciaHelper intervaloReferenciaHelper = new IntervaloReferenciaHelper();

		Collection<IntervaloReferenciaHelper> colecaoReferencias = new ArrayList();

		Integer id = form.getColecaoIntervaloReferenciaHelper().size() + 1;

		intervaloReferenciaHelper.setReferenciaInicial(form.getReferenciaInicial());
		intervaloReferenciaHelper.setReferenciaFinal(form.getReferenciaFinal());
		intervaloReferenciaHelper.setId(id);

		colecaoReferencias.add(intervaloReferenciaHelper);


		BigDecimal valorTotalContas = fachada.obterValorTotalContasParaCancelamento(new Integer(form.getCodigoCliente()),
						colecaoReferencias);

		form.setValorTotalContas(Util.formatarMoedaReal(valorTotalContas, 2));

		form.addIntervaloReferencia();
	

	}

}
