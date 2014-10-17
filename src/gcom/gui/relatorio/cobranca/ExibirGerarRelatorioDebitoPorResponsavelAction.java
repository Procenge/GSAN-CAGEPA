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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroMotivoRevisaoConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe na qual irá exibir a tela para gerar o relatório de Débito por Responsável.
 * 
 * @author Ricardo Rodrigues.
 * @date 12/04/2012
 */
public class ExibirGerarRelatorioDebitoPorResponsavelAction
				extends GcomAction {

	private static final String TIPO_RELATORIO_ANALITICO = "A";

	private static final String RESPONSABILIDADE_DIRETA = "D";

	private static final Integer CLIENTE_INICIAL = Integer.valueOf(1);

	private static final Integer CLIENTE_FINAL = Integer.valueOf(2);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioDebitoPorResponsavel");

		GerarRelatorioDebitoPorResponsavelActionForm form = (GerarRelatorioDebitoPorResponsavelActionForm) actionForm;

		// CarregarEsferaPoder
		this.carregarEsferaPoder(httpServletRequest);

		// Pesquisa todos os tipos dos clientes
		this.pesquisarTiposClientes(httpServletRequest);

		// Pesquisa todos os motivos de conta em revisão
		this.pesquisarMotivoContasRevisao(httpServletRequest);

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsultaStr = httpServletRequest.getParameter("objetoConsulta");

		Integer objetoConsulta = null;
		if(objetoConsultaStr != null){
			objetoConsulta = Integer.valueOf(objetoConsultaStr);
		}

		if(objetoConsulta != null){
			if((objetoConsulta.equals(CLIENTE_INICIAL)) || (objetoConsulta.equals(CLIENTE_FINAL))){
				pesquisarCliente(form, objetoConsulta);
			}
		}

		// Carregar valores padrões para os Radios da tela
		this.carregarRadios(form);

		// Habilita ou Desabilita o combo de Motivo de Revisão
		String contasEmRevisao = form.getIndicadorContasEmRevisao();
		String disabledMotivoRevisao = "false";

		if(!Util.isVazioOuBranco(contasEmRevisao)){
			if(contasEmRevisao.equals(ConstantesSistema.NAO.toString())){
				disabledMotivoRevisao = "true";
			}
		}
		httpServletRequest.setAttribute("disabledMotivoRevisao", disabledMotivoRevisao);

		// Habilita ou Desabilita o combo de Cliente Inicial
		String clienteInicial = form.getIdClienteInicial();
		String disabledClienteInicial = "false";

		if(!Util.isVazioOuBranco(clienteInicial)){
			disabledClienteInicial = "true";
		}
		httpServletRequest.setAttribute("disabledClienteInicial", disabledClienteInicial);

		// Habilita ou Desabilita o combo de Cliente Final
		String clienteFinal = form.getIdClienteFinal();
		String disabledClienteFinal = "false";

		if(!Util.isVazioOuBranco(clienteFinal)){
			disabledClienteFinal = "true";
		}
		httpServletRequest.setAttribute("disabledClienteFinal", disabledClienteFinal);
		return retorno;
	}

	private void carregarEsferaPoder(HttpServletRequest request){

		Fachada fachada = Fachada.getInstancia();

		FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder(FiltroEsferaPoder.DESCRICAO);

		Collection colecaoEsferaPoder = fachada.pesquisar(filtroEsferaPoder, EsferaPoder.class.getName());

		if(colecaoEsferaPoder == null || colecaoEsferaPoder.isEmpty()){
			// Nenhum registro na tabela esfera_poder foi encontrado
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Esfera poder");
		}else{
			request.setAttribute("colecaoEsferaPoder", colecaoEsferaPoder);
		}

	}

	private void carregarRadios(GerarRelatorioDebitoPorResponsavelActionForm form){

		String tipoRelatorio = form.getIndicadorTipoRelatorio();
		if(Util.isVazioOuBranco(tipoRelatorio)){

			form.setIndicadorTipoRelatorio(TIPO_RELATORIO_ANALITICO);
		}

		String responsabilidade = form.getIndicadorResponsabilidade();
		if(Util.isVazioOuBranco(responsabilidade)){

			form.setIndicadorResponsabilidade(RESPONSABILIDADE_DIRETA);
		}

		String contasEmRevisao = form.getIndicadorContasEmRevisao();
		if(Util.isVazioOuBranco(contasEmRevisao)){

			form.setIndicadorContasEmRevisao(ConstantesSistema.NAO.toString());
		}

		String valorCorrigido = form.getIndicadorValorCorrigido();
		if(Util.isVazioOuBranco(valorCorrigido)){

			try{
				String pValorPadraoIndicadorValorCorrigido = ParametroCobranca.P_VALOR_PADRAO_INDICADOR_VALOR_CORRIGIDO_RELATORIO_DEBITO_RESPONSAVEL
								.executar();
				form.setIndicadorValorCorrigido(pValorPadraoIndicadorValorCorrigido);
			}catch(ControladorException e){
				throw new ActionServletException("atencao.sistemaparametro_inexistente",
								"P_VALOR_PADRAO_INDICADOR_VALOR_CORRIGIDO_RELATORIO_DEBITO_RESPONSAVEL");
			}
		}
	}

	/**
	 * Método que pesquisa todos os motivos de contas em revisão.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 12/04/2012
	 * @param request
	 */
	private void pesquisarMotivoContasRevisao(HttpServletRequest request){

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// Criando o Filtro de Motivo contas em revisão
		FiltroMotivoRevisaoConta filtroMotivoRevisaoConta = new FiltroMotivoRevisaoConta(
						FiltroMotivoRevisaoConta.DESCRICAO_MOTIVO_REVISAO_CONTA);

		filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoMotivoRevisaoConta = fachada.pesquisar(filtroMotivoRevisaoConta, ContaMotivoRevisao.class.getName());

		if(colecaoMotivoRevisaoConta == null || colecaoMotivoRevisaoConta.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_REVISAO_CONTA");
		}

		// Disponibiliza a coleção para a tela
		request.setAttribute("colecaoMotivoRevisaoConta", colecaoMotivoRevisaoConta);
	}

	/**
	 * Método que pesquisa todos os tipos de clientes.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 12/04/2012
	 * @param request
	 */
	private void pesquisarTiposClientes(HttpServletRequest request){

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa os Tipos de Clientes para a página
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteTipo.setCampoOrderBy(FiltroClienteTipo.DESCRICAO);

		// Seta a coleção para View.
		request.setAttribute("colecaoTiposClientes", fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName()));

	}

	/**
	 * Método que pesquisa o cliente e replica o valor do inicial para o final também.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 12/04/2012
	 * @param form
	 * @param objetoConsulta
	 */
	private void pesquisarCliente(GerarRelatorioDebitoPorResponsavelActionForm form, Integer objetoConsulta){

		Integer clienteId = null;
		String clienteIdStr = null;
		Cliente cliente = null;
		boolean inicial = false;

		// Recupera o id do cliente de acordo com a variável objetoConsulta, que indica qual
		// campo do formulário foi informado para pesquisa
		if(objetoConsulta.equals(CLIENTE_INICIAL)){

			clienteIdStr = form.getIdClienteInicial();
			inicial = true;
		}else if(objetoConsulta.equals(CLIENTE_FINAL)){

			clienteIdStr = form.getIdClienteFinal();
		}

		if(!Util.isVazioOuBranco(clienteIdStr)){
			clienteId = Integer.valueOf(clienteIdStr);

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteId));

			// Recupera Cliente para preencher o formulário
			Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){

				cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
			}

			// Se encontrou a cliente, preenche as propriedades do formulário
			if(cliente != null){

				if(inicial){

					form.setIdClienteInicial(cliente.getId().toString());
					form.setNomeClienteInicial(cliente.getNome());
					form.setIdClienteFinal(cliente.getId().toString());
					form.setNomeClienteFinal(cliente.getNome());
				}else{

					form.setIdClienteFinal(cliente.getId().toString());
					form.setNomeClienteFinal(cliente.getNome());
				}
			}else{
				if(inicial){

					form.setIdClienteInicial(null);
					form.setNomeClienteInicial("Cliente inexistente");
				}else{

					form.setIdClienteFinal(null);
					form.setNomeClienteFinal("Cliente inexistente");
				}
			}
		}
	}

}
