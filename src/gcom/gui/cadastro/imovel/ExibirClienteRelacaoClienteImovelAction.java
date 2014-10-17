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
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.bean.ConsultarClienteRelacaoClienteImovelHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action ExibirClienteRelacaoClienteImovelActionForm
 * 
 * @author thiago toscano
 * @date 10/03/2006
 */
public class ExibirClienteRelacaoClienteImovelAction
				extends GcomAction {

	/**
	 * Método responsavel por responder a requisicao
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
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibir");

		ConsultarRelacaoClienteImovelActionForm form = (ConsultarRelacaoClienteImovelActionForm) actionForm;

		HttpSession sessao = request.getSession(false);
		sessao.removeAttribute("imovel");
		sessao.removeAttribute("collClienteImovel");
		sessao.removeAttribute("collImovelSubCategoriaHelper");
		sessao.removeAttribute("cliente");
		sessao.removeAttribute("collClienteImovelEconomia");

		if(form.getIdCliente() == null || "".equals(form.getIdCliente())){
			throw new ActionServletException("erro.parametro.nao.informado", null, "idCliente");
		}

		// monta a consulta do cliente
		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(form.getIdCliente())));

		/*
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.LOGRADOURO);
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.LOGRADOUR_TIPO);
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.LOGRADOUR_TITULO);
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.BAIRRO_CODIGO);
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.MUNICIPIO_ID);
		 * filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CEP);
		 */
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_ENDERECOS);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RAMO_ATIVIDADE);

		// consulta o cliente /
		// Collection coll =
		// Fachada.getInstancia().pesquisar(filtroCliente,Cliente.class.getName());
		Collection coll = Fachada.getInstancia().pesquisarClienteDadosClienteEnderecoRelatorio(filtroCliente);

		if(coll != null && !coll.isEmpty()){
			Cliente cliente = (Cliente) coll.iterator().next();
			sessao.setAttribute("cliente", cliente);

			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, cliente.getId()));
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("cliente");

			Collection colecaoClienteEnderecos = Fachada.getInstancia().pesquisar(filtroClienteEndereco, ClienteEndereco.class.getName());

			sessao.setAttribute("colecaoClienteEnderecos", colecaoClienteEnderecos);

			ClienteEndereco clienteEndereco = new ClienteEndereco();

			if(colecaoClienteEnderecos != null && !colecaoClienteEnderecos.isEmpty()){
				Iterator colecaoClienteEnderecosIterator = colecaoClienteEnderecos.iterator();
				while(colecaoClienteEnderecosIterator.hasNext()){
					clienteEndereco = (ClienteEndereco) colecaoClienteEnderecosIterator.next();
					if(clienteEndereco.getIndicadorEnderecoCorrespondencia().equals(ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){
						request.setAttribute("enderecoCorrespondencia", clienteEndereco);
						break;
					}
				}
			}

			sessao.setAttribute("clienteEndereco", clienteEndereco);

			// cliente imoveis
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.setConsultaSemLimites(true);
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, new Integer(form.getIdCliente())));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteImovelFimRelacaoMotivo");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOCALIDADE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.SETOR_COMERCIAL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.QUADRA);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.LOGRADOURO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.BAIRRO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.MUNICIPIO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CEP);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel." + FiltroImovel.UNIDADE_FEDERACAO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel." + FiltroImovel.ENDERECO_REFERENCIA);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel." + FiltroImovel.LOGRADOURO_TIPO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel." + FiltroImovel.LOGRADOURO_TITULO);

			if(form.getIdClienteRelacaoTipo() != null && !"".equals(form.getIdClienteRelacaoTipo())){
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID, new Integer(form
								.getIdClienteRelacaoTipo())));
			}
			if(form.getIdClienteImovelFimRelacaoMotivo() != null && !"".equals(form.getIdClienteImovelFimRelacaoMotivo())){
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO, new Integer(form
								.getIdClienteImovelFimRelacaoMotivo())));
			}
			if(form.getPeriodoInicialDataInicioRelacao() != null && !"".equals(form.getPeriodoInicialDataInicioRelacao())){
				// filtroClienteImovel.adicionarParametro(new
				// ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO,
				// form.getIdClienteImovelFimRelacaoMotivo()));

				Date periodoInicialDataInicioRelacao = Util.converteStringParaDate(form.getPeriodoInicialDataInicioRelacao());

				Date periodoFinalDataInicioRelacao = null;

				if(form.getPeriodoFinalDataInicioRelacao() == null || form.getPeriodoFinalDataInicioRelacao().equals("")){
					periodoFinalDataInicioRelacao = periodoInicialDataInicioRelacao;
				}else{
					periodoFinalDataInicioRelacao = Util.converteStringParaDate(form.getPeriodoFinalDataInicioRelacao());
				}
				Calendar diInicio = Calendar.getInstance();
				diInicio.setTime(periodoInicialDataInicioRelacao);
				diInicio.set(Calendar.HOUR, 0);
				diInicio.set(Calendar.MINUTE, 0);
				diInicio.set(Calendar.SECOND, 0);

				Calendar diFim = Calendar.getInstance();
				diFim.setTime(periodoFinalDataInicioRelacao);
				diFim.set(Calendar.HOUR, 23);
				diFim.set(Calendar.MINUTE, 59);
				diFim.set(Calendar.SECOND, 59);

				filtroClienteImovel.adicionarParametro(new Intervalo(FiltroClienteImovel.DATA_INICIO_RELACAO, diInicio.getTime(), diFim
								.getTime()));
			}
			if(form.getPeriodoInicialDataFimRelacao() != null && !"".equals(form.getPeriodoInicialDataFimRelacao())){
				// filtroClienteImovel.adicionarParametro(new
				// ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO,
				// form.getIdClienteImovelFimRelacaoMotivo()));

				Date periodoInicialDataFimRelacao = Util.converteStringParaDate(form.getPeriodoInicialDataFimRelacao());

				Date periodoFinalDataFimRelacao = null;

				if(form.getPeriodoFinalDataFimRelacao() == null || form.getPeriodoFinalDataFimRelacao().equals("")){
					periodoFinalDataFimRelacao = periodoInicialDataFimRelacao;
				}else{
					periodoFinalDataFimRelacao = Util.converteStringParaDate(form.getPeriodoFinalDataFimRelacao());
				}
				Calendar diInicio = Calendar.getInstance();
				diInicio.setTime(periodoInicialDataFimRelacao);
				diInicio.set(Calendar.HOUR, 0);
				diInicio.set(Calendar.MINUTE, 0);
				diInicio.set(Calendar.SECOND, 0);

				Calendar diFim = Calendar.getInstance();
				diFim.setTime(periodoFinalDataFimRelacao);
				diFim.set(Calendar.HOUR, 23);
				diFim.set(Calendar.MINUTE, 59);
				diFim.set(Calendar.SECOND, 59);

				filtroClienteImovel.adicionarParametro(new Intervalo(FiltroClienteImovel.DATA_FIM_RELACAO, diInicio.getTime(), diFim
								.getTime()));
			}

			if(form.getSituacaoRelacao() != null && form.getSituacaoRelacao().equalsIgnoreCase("1")){
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			}else if(form.getSituacaoRelacao() != null && form.getSituacaoRelacao().equalsIgnoreCase("2")){
				filtroClienteImovel.adicionarParametro(new ParametroNaoNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			}else{
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO, FiltroParametro.CONECTOR_OR,
								2));
				filtroClienteImovel.adicionarParametro(new ParametroNaoNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			}

			// ordenando colecao OC1119689
			filtroClienteImovel
							.setCampoOrderBy(new String[] {FiltroClienteImovel.DATA_INICIO_RELACAO, FiltroClienteImovel.DATA_FIM_RELACAO, FiltroClienteImovel.IMOVEL_ID, FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID});

			// consulta de cliente imovel
			coll = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			Collection colecaoImovel = new ArrayList();
			if(coll != null && !coll.isEmpty()){
				Collection colecaoHelper = new ArrayList();
				Iterator iteClienteImovel = coll.iterator();
				while(iteClienteImovel.hasNext()){

					ClienteImovel clienteImovel = (ClienteImovel) iteClienteImovel.next();
					ConsultarClienteRelacaoClienteImovelHelper consultarClienteRelacaoClienteImovelHelper = new ConsultarClienteRelacaoClienteImovelHelper();
					consultarClienteRelacaoClienteImovelHelper.setClienteImovel(clienteImovel);

					if(clienteImovel.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.USUARIO)){
						consultarClienteRelacaoClienteImovelHelper.setNomeClienteUsuario(clienteImovel.getCliente().getNome());
					}else{
						String nomeClienteUsuario = Fachada.getInstancia().pesquisarNomeClientePorImovel(clienteImovel.getImovel().getId());
						consultarClienteRelacaoClienteImovelHelper.setNomeClienteUsuario(nomeClienteUsuario);
					}

					String enderecoImovel = Fachada.getInstancia().pesquisarEndereco(clienteImovel.getImovel().getId());
					consultarClienteRelacaoClienteImovelHelper.setEnderecoImovel(enderecoImovel);

					colecaoHelper.add(consultarClienteRelacaoClienteImovelHelper);

					if(clienteImovel.getImovel().getId() != null && !colecaoImovel.contains(clienteImovel.getImovel().getId())){
						colecaoImovel.add(clienteImovel.getImovel().getId());
					}

				}

				sessao.setAttribute("collClienteImovel", colecaoHelper);
				sessao.setAttribute("colecaoImovel", colecaoImovel);
			}


			// consulta os ClienteImovelEconomia que pertenca ao
			// imovelSubCategoria corrente ( id do imovel e id da subcategoria)
			FiltroClienteImovelEconomia filtroClienteImovelEconomia = new FiltroClienteImovelEconomia();
			filtroClienteImovelEconomia.setConsultaSemLimites(true);
			filtroClienteImovelEconomia
							.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.CLIENTE_ID, form.getIdCliente()));

			if(form.getIdClienteRelacaoTipo() != null && !"".equals(form.getIdClienteRelacaoTipo())){
				filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.CLIENTE_RELACAO_TIPO, form
								.getIdClienteRelacaoTipo()));
			}
			if(form.getIdClienteImovelFimRelacaoMotivo() != null && !"".equals(form.getIdClienteImovelFimRelacaoMotivo())){
				filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.FIM_RELACAO_MOTIVO, form
								.getIdClienteImovelFimRelacaoMotivo()));
			}
			if(form.getPeriodoInicialDataInicioRelacao() != null && !"".equals(form.getPeriodoInicialDataInicioRelacao())){
				// filtroClienteImovel.adicionarParametro(new
				// ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO,
				// form.getIdClienteImovelFimRelacaoMotivo()));

				Date periodoInicialDataInicioRelacao = Util.converteStringParaDate(form.getPeriodoInicialDataInicioRelacao());

				Date periodoFinalDataInicioRelacao = null;

				if(form.getPeriodoFinalDataInicioRelacao() == null || form.getPeriodoFinalDataInicioRelacao().equals("")){
					periodoFinalDataInicioRelacao = periodoInicialDataInicioRelacao;
				}else{
					periodoFinalDataInicioRelacao = Util.converteStringParaDate(form.getPeriodoFinalDataInicioRelacao());
				}
				Calendar diInicio = Calendar.getInstance();
				diInicio.setTime(periodoInicialDataInicioRelacao);
				diInicio.set(Calendar.HOUR, 0);
				diInicio.set(Calendar.MINUTE, 0);
				diInicio.set(Calendar.SECOND, 0);

				Calendar diFim = Calendar.getInstance();
				diFim.setTime(periodoFinalDataInicioRelacao);
				diFim.set(Calendar.HOUR, 23);
				diFim.set(Calendar.MINUTE, 59);
				diFim.set(Calendar.SECOND, 59);

				filtroClienteImovelEconomia.adicionarParametro(new Intervalo(FiltroClienteImovelEconomia.DATA_INICIO_RELACAO, diInicio
								.getTime(), diFim.getTime()));
			}
			if(form.getPeriodoInicialDataFimRelacao() != null && !"".equals(form.getPeriodoInicialDataFimRelacao())){
				// filtroClienteImovel.adicionarParametro(new
				// ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO,
				// form.getIdClienteImovelFimRelacaoMotivo()));

				Date periodoInicialDataFimRelacao = Util.converteStringParaDate(form.getPeriodoInicialDataFimRelacao());

				Date periodoFinalDataFimRelacao = null;

				if(form.getPeriodoFinalDataFimRelacao() == null || form.getPeriodoFinalDataFimRelacao().equals("")){
					periodoFinalDataFimRelacao = periodoInicialDataFimRelacao;
				}else{
					periodoFinalDataFimRelacao = Util.converteStringParaDate(form.getPeriodoFinalDataFimRelacao());
				}
				Calendar diInicio = Calendar.getInstance();
				diInicio.setTime(periodoInicialDataFimRelacao);
				diInicio.set(Calendar.HOUR, 0);
				diInicio.set(Calendar.MINUTE, 0);
				diInicio.set(Calendar.SECOND, 0);

				Calendar diFim = Calendar.getInstance();
				diFim.setTime(periodoFinalDataFimRelacao);
				diFim.set(Calendar.HOUR, 23);
				diFim.set(Calendar.MINUTE, 59);
				diFim.set(Calendar.SECOND, 59);

				filtroClienteImovelEconomia.adicionarParametro(new Intervalo(FiltroClienteImovelEconomia.DATA_FIM_RELACAO, diInicio
								.getTime(), diFim.getTime()));
			}

			if(form.getSituacaoRelacao() != null && form.getSituacaoRelacao().equalsIgnoreCase("1")){
				filtroClienteImovelEconomia.adicionarParametro(new ParametroNulo(FiltroClienteImovelEconomia.DATA_FIM_RELACAO));
			}else if(form.getSituacaoRelacao() != null && form.getSituacaoRelacao().equalsIgnoreCase("2")){
				filtroClienteImovelEconomia.adicionarParametro(new ParametroNaoNulo(FiltroClienteImovelEconomia.DATA_FIM_RELACAO));
			}

			filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIA);
			filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.CLIENTE);
			filtroClienteImovelEconomia
							.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_AREA_CONSTRUIDA_FAIXA);
			filtroClienteImovelEconomia
							.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_IMOVEL_SUB_CATEGORIA);
			filtroClienteImovelEconomia
							.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_IMOVEL_CATEGORIA);
			filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL);
			filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("clienteImovelFimRelacaoMotivo");
			filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
			Collection collClienteImovelEconomia = Fachada.getInstancia().pesquisar(filtroClienteImovelEconomia,
							ClienteImovelEconomia.class.getName());
			sessao.setAttribute("collClienteImovelEconomia", collClienteImovelEconomia);

		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}