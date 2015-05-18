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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action responsável por adicionar na coleção a relação entre o cliente imovel, o cliente e a data
 * de inicio da relação
 * 
 * @author Sávio Luiz
 * @created 16 de Maio de 2004
 */
public class AdicionarAtualizarImovelClienteAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("adicionarAtualizarImovelCliente");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		httpServletRequest.setAttribute("responsavelNaoContemDados", null);

		DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Collection<ClienteImovel> imovelClientesNovos = null;

		Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		if(sessao.getAttribute("imovelClientesNovos") != null){
			imovelClientesNovos = (Collection) sessao.getAttribute("imovelClientesNovos");
		}else{
			imovelClientesNovos = new ArrayList();
		}

		Boolean indicadorDataRelacaoFimInserir = false;
		try{
			if(ParametroCadastro.P_INDICADOR_INFORMAR_DATA_RELACAO_FIM_INSERIR_CLIENTE_IMOVEL.executar().equals(
							ConstantesSistema.SIM.toString())){
				indicadorDataRelacaoFimInserir = true;
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// instância um cliente

		Cliente cliente = new Cliente();

		// teste se o cliente ja foi pesquisado com enter

		if(inserirImovelActionForm.get("idCliente") != null){

			// recupera o id do cliente
			String idCliente = (String) inserirImovelActionForm.get("idCliente");
			// instância o filtro do cliente
			FiltroCliente filtroCliente = new FiltroCliente();

			// adiciona o parametro no filtro
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

			// faz a pesquisa do cliente
			Collection clientesObjs = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			// recupera o cliente da coleção pesquisada
			if(!clientesObjs.isEmpty()){
				cliente = (Cliente) clientesObjs.iterator().next();
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
			}

		}

		// inicializa o tipo do cliente imovel
		ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();

		// recupera id do tipo do cliente imovel
		clienteRelacaoTipo.setId((Integer) inserirImovelActionForm.get("tipoClienteImovel"));
		// recupera a descricao do tipo do cliente imovel
		clienteRelacaoTipo.setDescricao((String) inserirImovelActionForm.get("textoSelecionado"));

		SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");

		Date dataInicioRelacao = null;
		if(inserirImovelActionForm.get("dataInicioClienteImovelRelacao") != null
						&& !((String) inserirImovelActionForm.get("dataInicioClienteImovelRelacao")).equals("")){
			try{
				dataInicioRelacao = dataFormato.parse((String) inserirImovelActionForm.get("dataInicioClienteImovelRelacao"));

			}catch(ParseException ex){
				// dataInicioRelacao = null;
			}
		}

		Date dataCorrente = null;
		Calendar data = Calendar.getInstance();

		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MILLISECOND, 0);
		data.set(Calendar.HOUR, 0);
		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		dataCorrente = data.getTime();

		if(dataInicioRelacao == null){
			dataInicioRelacao = new Date();
		}

		// caso a data de inicio da relação seja anterior que a data atual
		if(dataInicioRelacao.after(dataCorrente)){
			throw new ActionServletException("atencao.data_inicio_relacao_cliente_imovel");
		}

		// inicializa o cliente imovel
		ClienteImovel clienteImovel = new ClienteImovel(dataInicioRelacao, null, null, cliente, clienteRelacaoTipo);

		if(inserirImovelActionForm.get("dataFimClienteImovelRelacao") != null
						&& !((String) inserirImovelActionForm.get("dataFimClienteImovelRelacao")).equals("")){
			try{
				Calendar cDataFimRelacao = Calendar.getInstance();
				Calendar cDataInicioRelacao = Calendar.getInstance();

				SimpleDateFormat dateformato = new SimpleDateFormat("dd/MM/yyyy");
				try{
					cDataFimRelacao.setTime(dateformato.parse((String) inserirImovelActionForm.get("dataFimClienteImovelRelacao")));
				}catch(ParseException e){
					// TODO Auto-generated catch block
					throw new ActionServletException("erro.sistema", e);
				}

				cDataInicioRelacao.setTime(dataInicioRelacao);

				cDataFimRelacao.set(Calendar.HOUR, cDataInicioRelacao.get(Calendar.HOUR));
				cDataFimRelacao.set(Calendar.HOUR_OF_DAY, cDataInicioRelacao.get(Calendar.HOUR_OF_DAY));
				cDataFimRelacao.set(Calendar.MINUTE, cDataInicioRelacao.get(Calendar.MINUTE));
				cDataFimRelacao.set(Calendar.SECOND, cDataInicioRelacao.get(Calendar.SECOND));
				cDataFimRelacao.set(Calendar.MILLISECOND, cDataInicioRelacao.get(Calendar.MILLISECOND));

				if(cDataFimRelacao.before(cDataInicioRelacao)){
					throw new ActionServletException("atencao.data_fim_relacao_maior.cliente_imovel_usuario");
				}

				clienteImovel.setDataPrevistaFimRelacao(dataFormato.parse((String) inserirImovelActionForm.get("dataFimClienteImovelRelacao")));

				if(dataCorrente.after(clienteImovel.getDataPrevistaFimRelacao())){
					clienteImovel.setDataFimRelacao(clienteImovel.getDataPrevistaFimRelacao());
				}
				
			}catch(ParseException ex){
				// dataInicioRelacao = null;
			}
		}

		if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO.intValue()){
			if(inserirImovelActionForm.get("idMotivoFimClienteImovelRelacao") != null
							&& !((String) inserirImovelActionForm.get("idMotivoFimClienteImovelRelacao")).equals("")
							&& !((String) inserirImovelActionForm.get("idMotivoFimClienteImovelRelacao")).equals(String
											.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo = new ClienteImovelFimRelacaoMotivo();
				clienteImovelFimRelacaoMotivo
								.setId(Integer.valueOf((String) inserirImovelActionForm.get("idMotivoFimClienteImovelRelacao")));

				clienteImovel.setClienteImovelFimRelacaoMotivo(clienteImovelFimRelacaoMotivo);
			}
		}

		if(clienteRelacaoTipo.getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO.intValue()
						&& indicadorDataRelacaoFimInserir){
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovel.getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, clienteRelacaoTipo
							.getId().intValue()));

			boolean existeDataFimMaiorQueDataInicio = false;
			Date dataFimRelacaoComparacao = null;
			
			Collection<ClienteImovel> colecaoClientesImoveisRemovidos = (Collection<ClienteImovel>) sessao
							.getAttribute("colecaoClientesImoveisRemovidos");
			if(colecaoClientesImoveisRemovidos != null){
				for(ClienteImovel clienteImovelRemovidos : colecaoClientesImoveisRemovidos){
					if(clienteImovelRemovidos.getId() != null){
						Date dataFimRelacaoAtual = clienteImovelRemovidos.getDataFimRelacao();
						if (clienteImovelRemovidos.getDataFimRelacao() == null && clienteImovelRemovidos.getDataPrevistaFimRelacao() != null) {
							dataFimRelacaoAtual = clienteImovelRemovidos.getDataPrevistaFimRelacao();
						}
						
						if(dataFimRelacaoAtual != null){
							if(dataFimRelacaoComparacao != null){
								if (dataFimRelacaoAtual.after(dataFimRelacaoComparacao)){
									dataFimRelacaoComparacao = dataFimRelacaoAtual;
								}
							}else{
								dataFimRelacaoComparacao = dataFimRelacaoAtual;
							}
						}
					}
				}
			}

			Collection<ClienteImovel> colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
			for(ClienteImovel clienteImovelBanco : colecaoClienteImovel) {
				Boolean bFlagExisteItemRemovido = false;
				if(colecaoClientesImoveisRemovidos != null){
					bFlagExisteItemRemovido = colecaoClientesImoveisRemovidos.contains(clienteImovelBanco);
				}

				if(!bFlagExisteItemRemovido){
					Date dataFimRelacaoAtualBanco = clienteImovelBanco.getDataFimRelacao();
					if(clienteImovelBanco.getDataFimRelacao() == null && clienteImovelBanco.getDataPrevistaFimRelacao() != null){
						dataFimRelacaoAtualBanco = clienteImovelBanco.getDataPrevistaFimRelacao();
					}

					if(dataFimRelacaoAtualBanco != null){
						if(dataFimRelacaoComparacao != null){
							if(dataFimRelacaoAtualBanco.after(dataFimRelacaoComparacao)){
								dataFimRelacaoComparacao = dataFimRelacaoAtualBanco;
							}
						}else{
							dataFimRelacaoComparacao = dataFimRelacaoAtualBanco;
						}
					}else{
						dataFimRelacaoComparacao = null;
					}
				}
			}

			Date dataNovaMaiorFimRelacaoComparacao = dataFimRelacaoComparacao;
			if (dataFimRelacaoComparacao != null) {
				for(ClienteImovel clienteImovelNovos : imovelClientesNovos){
					if(clienteImovelNovos.getClienteRelacaoTipo() != null
									&& clienteImovelNovos.getClienteRelacaoTipo().getId() != null
									&& clienteImovelNovos.getClienteRelacaoTipo().getId()
													.equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO)
									&& clienteImovelNovos.getClienteRelacaoTipo().getId()
													.equals(clienteImovel.getClienteRelacaoTipo().getId())){
						Date dataFimRelacaoAtualNovo = clienteImovelNovos.getDataFimRelacao();
						if(clienteImovelNovos.getDataFimRelacao() == null && clienteImovelNovos.getDataPrevistaFimRelacao() != null){
							dataFimRelacaoAtualNovo = clienteImovelNovos.getDataPrevistaFimRelacao();
						}

						if(dataFimRelacaoAtualNovo != null){
							if(dataFimRelacaoAtualNovo.after(dataNovaMaiorFimRelacaoComparacao)
											&& dataNovaMaiorFimRelacaoComparacao != null){
								dataNovaMaiorFimRelacaoComparacao = dataFimRelacaoAtualNovo;
							}
						}else{
							dataNovaMaiorFimRelacaoComparacao = null;
						}
					}
				}
			}

			
			if(dataNovaMaiorFimRelacaoComparacao == null || clienteImovel.getDataInicioRelacao() == null){
				existeDataFimMaiorQueDataInicio = true;
			}else{
				Calendar cal = Calendar.getInstance();
				cal.setTime(dataNovaMaiorFimRelacaoComparacao);
				cal.add(Calendar.DATE, 1);
				dataNovaMaiorFimRelacaoComparacao = cal.getTime();

				if(clienteImovel.getDataInicioRelacao().equals(dataNovaMaiorFimRelacaoComparacao)){
					existeDataFimMaiorQueDataInicio = false;
				}else{
					existeDataFimMaiorQueDataInicio = true;
				}
			}
			
			if(existeDataFimMaiorQueDataInicio){
				if (dataNovaMaiorFimRelacaoComparacao != null) {
					throw new ActionServletException("atencao.data_inicio_cliente_relacao_cliente_imovel", null, Util.formatarData(new Date(
									dataNovaMaiorFimRelacaoComparacao.getTime())));
				} else {
					throw new ActionServletException("atencao.data_inicio_cliente_relacao_cliente_imovel_ativo", null, inserirImovelActionForm.get("textoSelecionado").toString());
				}
			}
		}

		// //Adicionando Conta envio para a DESO
		// if
		// (getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO))
		// {
		// ImovelContaEnvio imovelContaEnvio = new ImovelContaEnvio();
		// imovelContaEnvio.setId(ConstantesSistema.NUMERO_NAO_INFORMADO);
		// clienteImovel.setImovelContaEnvio(imovelContaEnvio);
		// }
		clienteImovel.setIndicadorAtualizarDebitos(Short.valueOf(inserirImovelActionForm.get("indicadorAtualizarDebitos").toString()));

		// Coloca a data de ultima alteração para identificar o objeto
		clienteImovel.setUltimaAlteracao(new Date());

		if(!imovelClientesNovos.contains(clienteImovel)){
			// verifica se o tipo do cliente é usuário ou é responsável
			if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO.intValue()){

				Boolean validarTipoClienteCadastrado = true;
				if(!indicadorDataRelacaoFimInserir){
					if(sessao.getAttribute("idClienteImovelUsuario") == null || sessao.getAttribute("idClienteImovelUsuario").equals("")){
						validarTipoClienteCadastrado = true;
					}else{
						validarTipoClienteCadastrado = false;
					}
				}


				if(validarTipoClienteCadastrado){
					if(imovel != null && imovel.getImovelPerfil() != null && imovel.getImovelPerfil().getId() != null
									&& imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL)){
						throw new ActionServletException("atencao.cliente_na_tarifa_social", null, "usuário"); // cliente
						// na
						// tarifa
						// social

					}
					// inserirImovelActionForm.set("idClienteImovelUsuario",cliente.getId().toString());
					sessao.setAttribute("idClienteImovelUsuario", cliente.getId().toString());
					httpServletRequest.setAttribute("idClienteImovelUsuario", cliente.getId().toString());
					// httpServletRequest.setAttribute("idClienteImovelUsuario",
					// cliente.getId().toString());
					// adiciona o cliente imovel na coleção de imovelClientesNovos
					imovelClientesNovos.add(clienteImovel);
				}else{
					throw new ActionServletException("atencao.ja_cadastradado.cliente_imovel_usuario");
				}
			}else if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_RESPONSAVEL
							.intValue()){
				if(sessao.getAttribute("idClienteImovelResponsavel") == null
								|| sessao.getAttribute("idClienteImovelResponsavel").equals("")){
					// inserirImovelActionForm.set("idClienteImovelResponsavel",
					// cliente.getId().toString());
					// httpServletRequest.setAttribute("idClienteImovelResponsavel",
					// cliente.getId().toString());
					sessao.setAttribute("idClienteImovelResponsavel", cliente.getId().toString());

					// adiciona o cliente imovel na coleção de imovelClientesNovos
					imovelClientesNovos.add(clienteImovel);
					/*
					 * Exibe um alert na JSP caso esse Cliente não possua dados na tabela
					 * Cliente_Responsavel
					 * Se o Cliente for DESO
					 */
					// Paramentro para diferenciar a companhia que o sistema está rodando
					if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
						// ********** Companhia - DESO ******************************
						FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
						filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.CLIENTE, cliente));
						Collection clientesResponsavel = fachada.pesquisar(filtroClienteResponsavel, ClienteResponsavel.class.getName());
						if(clientesResponsavel.isEmpty()){
							httpServletRequest.setAttribute("responsavelNaoContemDados", "true");
						}else{
							httpServletRequest.setAttribute("responsavelNaoContemDados", null);
						}
					}

				}else{
					throw new ActionServletException("atencao.ja_cadastradado.cliente_imovel_responsavel");
				}
			}else{
				if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
					if(inserirImovelActionForm.get("idClienteImovelProprietario") == null
									|| inserirImovelActionForm.get("idClienteImovelProprietario").equals("")){
						inserirImovelActionForm.set("idClienteImovelProprietario", cliente.getId().toString());

						sessao.setAttribute("idClienteImovelProprietario", cliente.getId().toString());
						// inserirImovelActionForm.set();
						// adiciona o cliente imovel na coleção de
						// imovelClientesNovos
						clienteImovel.setIndicadorNomeConta(Util.obterShort("2"));
						imovelClientesNovos.add(clienteImovel);
					}else{
						throw new ActionServletException("atencao.ja_cadastradado.cliente_imovel_proprietario");
					}
				}else{
					// sever para cliente do tipo proprietario adiciona o cliente imovel na coleção
					// de imovelClientesNovos
					imovelClientesNovos.add(clienteImovel);
				}
			}

			inserirImovelActionForm.set("idCliente", null);
			inserirImovelActionForm.set("nomeCliente", null);
			// atualizarColecaoClienteImovelSessao(httpServletRequest);
			// manda para a sessão a coleção de imovelClienteNovos

		}else{
			throw new ActionServletException("atencao.ja_cadastradado.cliente_imovel");
		}

		return retorno;
	}

	// public static void atualizarColecaoClienteImovelSessao(HttpServletRequest httpServletRequest)
	// {
	// HttpSession sessao = httpServletRequest.getSession();
	// Integer paramatroSistema = ((SistemaParametro)
	// sessao.getAttribute(SistemaParametro.SISTEMA_PARAMETRO)).getParmId();
	// if (paramatroSistema.shortValue() == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()) {
	// ImovelContaEnvio imovelContaEnvio = null;
	// Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");
	//
	// Collection<ClienteImovel> imovelClientesNovos = (Collection)
	// sessao.getAttribute("imovelClientesNovos");
	//
	// if (imovelClientesNovos != null && !imovelClientesNovos.isEmpty()) {
	// for (ClienteImovel imovelCliente : imovelClientesNovos) {
	// String idImovelContaEnvio = httpServletRequest.getParameter("enviaConta" +
	// imovelCliente.getCliente().getId() + ""
	// + imovelCliente.getClienteRelacaoTipo().getId());
	//
	// if (idImovelContaEnvio != null) {
	// imovelContaEnvio = new ImovelContaEnvio();
	// imovelContaEnvio.setId(Integer.parseInt(idImovelContaEnvio));
	//
	// }
	//
	// if (httpServletRequest.getParameter("atualizarEnviaExtrato") != null) {
	// Short indicador = ConstantesSistema.NAO;
	// if (httpServletRequest.getParameter("enviaExtrato" + imovelCliente.getCliente().getId() + ""
	// + imovelCliente.getClienteRelacaoTipo().getId()) != null) {
	// indicador = ConstantesSistema.SIM;
	// }
	//
	// if
	// (imovelCliente.getClienteRelacaoTipo().getId().equals(ConstantesSistema.CLIENTE_IMOVEL_TIPO_RESPONSAVEL)
	// && ((ConstantesSistema.SIM.equals(indicador)))) {
	// imovelCliente.setIndicadorEmissaoExtratoFaturamento(ConstantesSistema.SIM);
	//
	// } else {
	// imovelCliente.setIndicadorEmissaoExtratoFaturamento(ConstantesSistema.NAO);
	// }
	//
	// if (imovel != null) {
	// imovel.setIndicadorEmissaoExtratoFaturamento(imovelCliente.getIndicadorEmissaoExtratoFaturamento());
	// }
	//
	// }
	// }
	//
	// sessao.setAttribute("imovelClientesNovos", imovelClientesNovos);
	// }
	// }
	// }

}
