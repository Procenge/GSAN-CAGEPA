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

package gcom.gui.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipoEspecial;
import gcom.cadastro.cliente.FiltroClienteTipoEspecial;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Pedro Alexandre
 * @date 21/05/2007
 */
public class ExibirGerarRelatorioClientesEspeciaisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioClientesEspeciais");

		GerarRelatorioClientesEspeciaisActionForm gerarRelatorioClientesEspeciaisActionForm = (GerarRelatorioClientesEspeciaisActionForm) actionForm;

		if(httpServletRequest.getParameter("menu") != null){

			// Carrega as coleções que serão mostradas na página
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);
			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			FiltroImovelPerfil filtroPerfilImovel = new FiltroImovelPerfil(FiltroImovelPerfil.DESCRICAO);
			Collection colecaoPerfilImovel = fachada.pesquisar(filtroPerfilImovel, ImovelPerfil.class.getName());

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			FiltroCategoria filtroCategoria = new FiltroCategoria(FiltroCategoria.DESCRICAO);
			Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

			FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria(FiltroSubCategoria.DESCRICAO);
			Collection colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

			FiltroLigacaoAguaSituacao filtroSituacaoLigacaoAgua = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);
			Collection colecaoSituacaoLigacaoAgua = fachada.pesquisar(filtroSituacaoLigacaoAgua, LigacaoAguaSituacao.class.getName());

			FiltroLigacaoEsgotoSituacao filtroSituacaoLigacaoEsgoto = new FiltroLigacaoEsgotoSituacao(FiltroLigacaoEsgotoSituacao.DESCRICAO);
			Collection colecaoSituacaoLigacaoEsgoto = fachada.pesquisar(filtroSituacaoLigacaoEsgoto, LigacaoEsgotoSituacao.class.getName());

			FiltroHidrometroCapacidade filtroCapacidadeHidrometro = new FiltroHidrometroCapacidade(FiltroHidrometroCapacidade.NUMERO_ORDEM);
			Collection colecaoCapacidadeHidrometro = fachada.pesquisar(filtroCapacidadeHidrometro, HidrometroCapacidade.class.getName());

			FiltroConsumoTarifa filtroTarifaConsumo = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
			Collection colecaoTarifaConsumo = fachada.pesquisar(filtroTarifaConsumo, ConsumoTarifa.class.getName());

			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade(FiltroConsumoAnormalidade.DESCRICAO);

			Collection colecaoConsumosAnormalidades = fachada.pesquisar(filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

			FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial(FiltroClienteTipoEspecial.DESCRICAO);
			Collection colecaoClienteTipoEspecial = fachada.pesquisar(filtroClienteTipoEspecial, ClienteTipoEspecial.class.getName());


			sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
			sessao.setAttribute("colecaoPerfilImovel", colecaoPerfilImovel);
			sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
			sessao.setAttribute("colecaoCategoria", colecaoCategoria);
			sessao.setAttribute("colecaoSubCategoria", colecaoSubCategoria);
			sessao.setAttribute("colecaoSituacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
			sessao.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
			sessao.setAttribute("colecaoCapacidadeHidrometro", colecaoCapacidadeHidrometro);
			sessao.setAttribute("colecaoTarifaConsumo", colecaoTarifaConsumo);
			sessao.setAttribute("colecaoConsumosAnormalidades", colecaoConsumosAnormalidades);
			sessao.setAttribute("colecaoClienteTipoEspecial", colecaoClienteTipoEspecial);

			gerarRelatorioClientesEspeciaisActionForm.setConsumoAnormalidade("");
			gerarRelatorioClientesEspeciaisActionForm.setLeituraAnormalidade("");

		}

		// Verifica se o usuário efetuou alguma pesquisa
		String idLocalidadeInicialForm = gerarRelatorioClientesEspeciaisActionForm.getIdLocalidadeInicial();

		if(idLocalidadeInicialForm != null && !idLocalidadeInicialForm.equals("")){

			Integer idLocalidadeInicial = new Integer(idLocalidadeInicialForm);

			Localidade localidadeOrigem = fachada.pesquisarLocalidadeDigitada(new Integer(idLocalidadeInicial));

			if(localidadeOrigem != null){
				gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeInicial(localidadeOrigem.getDescricao());
			}else{
				gerarRelatorioClientesEspeciaisActionForm.setIdLocalidadeInicial("");
				gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeInicial("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("localidadeInicialInexistente", true);
			}

		}else{
			gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeInicial("");
		}

		String idLocalidadeFinalForm = gerarRelatorioClientesEspeciaisActionForm.getIdLocalidadeFinal();

		if(idLocalidadeFinalForm != null && !idLocalidadeFinalForm.equals("")){

			Integer idLocalidadeFinal = new Integer(idLocalidadeFinalForm);

			Localidade localidadeDestino = fachada.pesquisarLocalidadeDigitada(new Integer(idLocalidadeFinal));

			if(localidadeDestino != null){
				gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeFinal(localidadeDestino.getDescricao());
			}else{
				gerarRelatorioClientesEspeciaisActionForm.setIdLocalidadeFinal("");
				gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeFinal("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("localidadeFinalInexistente", true);
			}

		}else{
			gerarRelatorioClientesEspeciaisActionForm.setNomeLocalidadeFinal("");
		}

		String idClienteResponsavel = gerarRelatorioClientesEspeciaisActionForm.getIdClienteResponsavel();

		if(idClienteResponsavel != null && !idClienteResponsavel.equals("")){

			Cliente clienteResponsavel = fachada.pesquisarClienteDigitado(new Integer(idClienteResponsavel));

			if(clienteResponsavel != null){
				gerarRelatorioClientesEspeciaisActionForm.setNomeClienteResponsavel(clienteResponsavel.getNome());
			}else{
				gerarRelatorioClientesEspeciaisActionForm.setIdClienteResponsavel("");
				gerarRelatorioClientesEspeciaisActionForm.setNomeClienteResponsavel("CLIENTE INEXISTENTE");
				httpServletRequest.setAttribute("clienteResponsavelInexistente", true);
			}

		}else{
			gerarRelatorioClientesEspeciaisActionForm.setNomeClienteResponsavel("");
		}

		String idLeituraAnormalidade = gerarRelatorioClientesEspeciaisActionForm.getIdLeituraAnormalidade();

		if(idLeituraAnormalidade != null && !idLeituraAnormalidade.equals("")){

			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, idLeituraAnormalidade));

			Collection colecaoLeiturasAnormalidades = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

			if(colecaoLeiturasAnormalidades != null && !colecaoLeiturasAnormalidades.isEmpty()){
				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(colecaoLeiturasAnormalidades);

				gerarRelatorioClientesEspeciaisActionForm.setDescricaoLeituraAnormalidade(leituraAnormalidade.getDescricao());
			}else{
				gerarRelatorioClientesEspeciaisActionForm.setIdLeituraAnormalidade("");
				gerarRelatorioClientesEspeciaisActionForm.setDescricaoLeituraAnormalidade("ANORM. DE LEITURA INEXISTENTE");
				httpServletRequest.setAttribute("anormalidadeLeituraInexistente", true);
			}

		}else{
			gerarRelatorioClientesEspeciaisActionForm.setDescricaoLeituraAnormalidade("");
		}

		return retorno;
	}
}