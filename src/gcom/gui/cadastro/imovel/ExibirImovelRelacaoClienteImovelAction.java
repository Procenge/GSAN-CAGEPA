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
import gcom.cadastro.imovel.*;
import gcom.cadastro.imovel.bean.ImovelSubcategoriaHelper;
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
 * Action ConsultarRelacaoClienteImovelAction
 * 
 * @author thiago toscano
 * @date 10/03/2006
 */
public class ExibirImovelRelacaoClienteImovelAction
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
		HttpSession sessao = request.getSession(false);

		ConsultarRelacaoClienteImovelActionForm form = (ConsultarRelacaoClienteImovelActionForm) actionForm;

		if(form.getIdImovel() == null || "".equals(form.getIdImovel())){
			throw new ActionServletException("erro.parametro.nao.informado", null, "idImovel");
		}

		// pesquisando o imovel a ser apresentado
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getIdImovel()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TIPO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TITULO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.BAIRRO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_UF);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.MUNICIPIO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_FEDERACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
		// consulta do imovel
		Collection coll = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());

		if(coll != null && !coll.isEmpty()){
			Imovel imovel = (Imovel) coll.iterator().next();
			sessao.setAttribute("imovel", imovel);
			// para o imovel encontrado pesquisa todos os clientes do imovel
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.setConsultaSemLimites(true);
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, form.getIdImovel()));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteImovelFimRelacaoMotivo");
			if(form.getIdClienteRelacaoTipo() != null && !"".equals(form.getIdClienteRelacaoTipo())){
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, form
								.getIdClienteRelacaoTipo()));
			}
			if(form.getIdClienteImovelFimRelacaoMotivo() != null && !"".equals(form.getIdClienteImovelFimRelacaoMotivo())){
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.FIM_RELACAO_MOTIVO, form
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
				diFim.set(Calendar.HOUR, 0);
				diFim.set(Calendar.MINUTE, 0);
				diFim.set(Calendar.SECOND, 0);

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
				diFim.set(Calendar.HOUR, 0);
				diFim.set(Calendar.MINUTE, 0);
				diFim.set(Calendar.SECOND, 0);

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
.setCampoOrderByDesc(new String[] {FiltroClienteImovel.DATA_FIM_RELACAO});

			// consulta de cliente imovel
			coll = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
			sessao.setAttribute("collClienteImovel", coll);

			// Coleção Consumo por Faixa de Área e Categoria
			FiltroImovelConsumoFaixaAreaCategoria filroConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria(
							FiltroImovelConsumoFaixaAreaCategoria.CONSUMO_FAIXA_AREA_CATEGORIA_ID);
			filroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID, form
							.getIdImovel()));
			filroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filroConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA);

			coll = Fachada.getInstancia().pesquisar(filroConsumoFaixaAreaCategoria, ImovelConsumoFaixaAreaCategoria.class.getName());
			sessao.setAttribute("collImovelConsumoFaixaAreaCategoria", coll);

			// para o imovel consultar todos os imovel sub categoria
			FiltroImovelSubCategoria filtroImovelSubCategoria = new FiltroImovelSubCategoria();
			filtroImovelSubCategoria.setConsultaSemLimites(true);
			filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA);
			filtroImovelSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelSubCategoria.SUBCATEGORIA_CATEGORIA);
			filtroImovelSubCategoria.adicionarParametro(new ParametroSimples(FiltroImovelSubCategoria.IMOVEL_ID, form.getIdImovel()));
			// consulta das subcategorias do imovel
			coll = Fachada.getInstancia().pesquisar(filtroImovelSubCategoria, ImovelSubcategoria.class.getName());

			// para todos os sub categorias do imovel cria o ImovelSubCategoriaHelper
			Collection collImovelSubCategoriaHelper = new Vector();
			if(coll != null && !coll.isEmpty()){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) it.next();

					// consulta os ClienteImovelEconomia que pertenca ao imovelSubCategoria corrente
					// ( id do imovel e id da subcategoria)
					// e que tenha o cliente tipo usuario e que o relacionamento do cliente nao foi
					// finalizado
					// carrega tb o imovelEconomia
					// e a area construida do imovelEconomia
					FiltroClienteImovelEconomia filtroClienteImovelEconomia = new FiltroClienteImovelEconomia();
					filtroClienteImovelEconomia.setConsultaSemLimites(true);
					filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.IMOVEL_ID, form
									.getIdImovel()));
					filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.SUBCATEGORIA_ID,
									imovelSubcategoria.getComp_id().getSubcategoria().getId()));
					filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.CLIENTE_RELACAO_TIPO,
									ClienteRelacaoTipo.USUARIO));
					filtroClienteImovelEconomia.adicionarParametro(new ParametroNulo(FiltroClienteImovelEconomia.FIM_RELACAO_MOTIVO));
					filtroClienteImovelEconomia.adicionarParametro(new ParametroNulo(FiltroClienteImovelEconomia.DATA_FIM_RELACAO));
					filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIA);
					filtroClienteImovelEconomia
									.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_AREA_CONSTRUIDA_FAIXA);
					// filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_AREA_CONSTRUIDA_FAIXA);
					Collection collClienteImovelEconomia = Fachada.getInstancia().pesquisar(filtroClienteImovelEconomia,
									ClienteImovelEconomia.class.getName());

					ImovelSubcategoriaHelper imovelSubcategoriaHelper = new ImovelSubcategoriaHelper();
					imovelSubcategoriaHelper.setCategoria(imovelSubcategoria.getComp_id().getSubcategoria().getCategoria().getDescricao());
					imovelSubcategoriaHelper.setSubcategoria(imovelSubcategoria.getComp_id().getSubcategoria().getDescricao());
					imovelSubcategoriaHelper.setQuantidadeEconomias(imovelSubcategoria.getQuantidadeEconomias());
					imovelSubcategoriaHelper.setColecaoImovelEconomia(collClienteImovelEconomia);
					collImovelSubCategoriaHelper.add(imovelSubcategoriaHelper);
				}
			}

			sessao.setAttribute("collImovelSubCategoriaHelper", collImovelSubCategoriaHelper);

		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}