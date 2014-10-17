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

package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.DocumentoEmissaoForma;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoEmissaoForma;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * Exibe a tela de Filtro da Consulta de Histórico de Manutenção de Ligação de Água
 * 
 * @author Luciano Galvão
 * @created 18/09/2012
 */
public class ExibirFiltrarHistoricoManutencaoLigacaoAguaAction
				extends GcomAction {

	private static final String OBJETO_CONSULTA = "objetoConsulta";

	private static final String SETOR_COMERCIAL_FINAL = "4";

	private static final String SETOR_COMERCIAL_INICIAL = "2";

	private static final String LOCALIDADE_FINAL = "3";

	private static final String LOCALIDADE_INICIAL = "1";

	private static final String COLECAO_PERFIS_IMOVEL = "colecaoPerfisImovel";

	private static final String COLECAO_FORMAS_EMISSAO = "colecaoFormasEmissao";

	private static final String COLECAO_TIPOS_DOCUMENTO = "colecaoTiposDocumento";

	private static final String COLECAO_TIPOS_SERVICO = "colecaoTiposServico";

	private Fachada fachada = Fachada.getInstancia();


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarHistoricoManutencaoLigacaoAguaAction");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Carregar coleções para construir os Selects da tela
		carregarPerfisImovel(sessao);
		carregarFormasEmissao(sessao);
		carregarTiposDocumento(sessao);
		carregarTiposServico(sessao);

		// Captura o Form
		FiltrarHistoricoManutencaoLigacaoAguaActionForm form = (FiltrarHistoricoManutencaoLigacaoAguaActionForm) actionForm;

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter(OBJETO_CONSULTA);

		// Pesquisar Localidade Inicial ou Final
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals(LOCALIDADE_INICIAL) || objetoConsulta.trim().equals(LOCALIDADE_FINAL))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, httpServletRequest, objetoConsulta);
		}

		// Pesquisar Setor Comercial Inicial ou Final
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals(SETOR_COMERCIAL_INICIAL) || objetoConsulta.trim().equals(SETOR_COMERCIAL_FINAL))){

			// Faz a consulta de Setor ComercialInicial
			this.pesquisarSetorComercial(form, httpServletRequest, objetoConsulta);
		}

		if(!Util.isVazioOuBranco(form.getImovel())){
			this.pesquisarImovel(form, httpServletRequest);
		}

		verificarItensEncontrados(form, httpServletRequest);

		return retorno;
	}


	private void pesquisarLocalidade(FiltrarHistoricoManutencaoLigacaoAguaActionForm form, HttpServletRequest request, String objetoConsulta){


		String localidadeInicial = form.getLocalidadeInicial();

		if(!objetoConsulta.trim().equals(LOCALIDADE_INICIAL)){
			localidadeInicial = form.getLocalidadeFinal();
		}

		if(!Util.isVazioOuBranco(localidadeInicial)){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(localidadeInicial)));

			// Recupera Localidade
			Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLocalidade)){

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				if(objetoConsulta.trim().equals(LOCALIDADE_INICIAL)){
					form.setLocalidadeInicial(localidade.getId().toString());
					form.setNomeLocalidadeInicial(localidade.getDescricao());
				}

				form.setLocalidadeFinal(localidade.getId().toString());
				form.setNomeLocalidadeFinal(localidade.getDescricao());

			}else{
				if(objetoConsulta.trim().equals(LOCALIDADE_INICIAL)){
					form.setLocalidadeInicial(null);
					form.setNomeLocalidadeInicial("Localidade Inicial inexistente");

					form.setLocalidadeFinal(null);
					form.setNomeLocalidadeFinal(null);
				}else{
					form.setLocalidadeFinal(null);
					form.setNomeLocalidadeFinal("Localidade Final inexistente");
				}
			}
		}
	}

	private void pesquisarSetorComercial(FiltrarHistoricoManutencaoLigacaoAguaActionForm form, HttpServletRequest request,
					String objetoConsulta){

		String localidadeInicial = form.getLocalidadeInicial();

		String setorComercialInicial = form.getSetorComercialInicial();

		if(!objetoConsulta.trim().equals(SETOR_COMERCIAL_INICIAL)){
			setorComercialInicial = form.getSetorComercialFinal();
		}

		if(!Util.isVazioOuBranco(localidadeInicial) && !Util.isVazioOuBranco(setorComercialInicial)){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial
							.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialInicial));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, localidadeInicial));

			// Recupera Setor Comercial
			Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				if(objetoConsulta.trim().equals(SETOR_COMERCIAL_INICIAL)){
					form.setSetorComercialInicial(setorComercial.getCodigo() + "");
					form.setNomeSetorComercialInicial(setorComercial.getDescricao());
				}

				form.setSetorComercialFinal(setorComercial.getCodigo() + "");
				form.setNomeSetorComercialFinal(setorComercial.getDescricao());

			}else{

				if(objetoConsulta.trim().equals(SETOR_COMERCIAL_INICIAL)){
					form.setSetorComercialFinal(null);
					form.setNomeSetorComercialInicial("Setor Comercial Inicial inexistente");

					form.setSetorComercialFinal(null);
					form.setNomeSetorComercialFinal(null);
				}else{
					form.setSetorComercialFinal(null);
					form.setNomeSetorComercialFinal("Setor Comercial Final inexistente");
				}

			}
		}
	}

	private void pesquisarImovel(FiltrarHistoricoManutencaoLigacaoAguaActionForm form, HttpServletRequest request){

		String idImovel = form.getImovel();

		if(!Util.isVazioOuBranco(idImovel)){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

			// Recupera Imovel
			Collection colecaoImovel = this.getFachada().pesquisar(filtroImovel, Imovel.class.getName());

			if(!Util.isVazioOrNulo(colecaoImovel)){

				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				form.setImovel(imovel.getId() + "");
				form.setInscricaoImovel(imovel.getInscricaoFormatada());

			}else{
				form.setImovel(null);
				form.setInscricaoImovel("Imóvel inexistente");
			}
		}
	}

	private void verificarItensEncontrados(FiltrarHistoricoManutencaoLigacaoAguaActionForm form, HttpServletRequest request){

		if(!Util.isVazioOuBranco(form.getImovel())){
			request.setAttribute("matriculaEncontrada", "true");
		}
		if(!Util.isVazioOuBranco(form.getLocalidadeInicial())){
			request.setAttribute("localidadeInicialEncontrada", "true");
		}
		if(!Util.isVazioOuBranco(form.getLocalidadeFinal())){
			request.setAttribute("localidadeFinalEncontrada", "true");
		}
		if(!Util.isVazioOuBranco(form.getSetorComercialInicial())){
			request.setAttribute("setorComercialInicialEncontrado", "true");
		}
		if(!Util.isVazioOuBranco(form.getSetorComercialFinal())){
			request.setAttribute("setorComercialFinalEncontrado", "true");
		}
	}

	/**
	 * Carrega coleção de Perfis do Imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 18/09/2012
	 * @param sessao
	 */
	private void carregarPerfisImovel(HttpSession sessao){

		Collection colecaoPerfisImovel = (Collection) sessao.getAttribute(COLECAO_PERFIS_IMOVEL);

		if(Util.isVazioOrNulo(colecaoPerfisImovel)){
			// Filtra Localidade
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
			colecaoPerfisImovel = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			if(!Util.isVazioOrNulo(colecaoPerfisImovel)){
				sessao.setAttribute(COLECAO_PERFIS_IMOVEL, colecaoPerfisImovel);
			}else{

				throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Imóvel");
			}
		}
	}

	/**
	 * Carrega coleção de Formas de Emissao
	 * 
	 * @author Luciano Galvao
	 * @date 18/09/2012
	 * @param sessao
	 */
	private void carregarFormasEmissao(HttpSession sessao){

		Collection colecaoFormasEmissao = (Collection) sessao.getAttribute(COLECAO_FORMAS_EMISSAO);

		if(Util.isVazioOrNulo(colecaoFormasEmissao)){
			// Filtra Localidade
			FiltroDocumentoEmissaoForma filtroDocumentoEmissaoForma = new FiltroDocumentoEmissaoForma();
			filtroDocumentoEmissaoForma.setCampoOrderBy(FiltroDocumentoEmissaoForma.EMISSAO_FORMA);
			colecaoFormasEmissao = fachada.pesquisar(filtroDocumentoEmissaoForma, DocumentoEmissaoForma.class.getName());

			if(!Util.isVazioOrNulo(colecaoFormasEmissao)){
				sessao.setAttribute(COLECAO_FORMAS_EMISSAO, colecaoFormasEmissao);
			}else{

				throw new ActionServletException("atencao.naocadastrado", null, "Forma de Emissão");
			}
		}
	}

	/**
	 * Carrega coleção de Tipos de Documento
	 * 
	 * @author Luciano Galvao
	 * @date 18/09/2012
	 * @param sessao
	 */
	private void carregarTiposDocumento(HttpSession sessao){

		Collection colecaoTiposDocumento = (Collection) sessao.getAttribute(COLECAO_TIPOS_DOCUMENTO);

		if(Util.isVazioOrNulo(colecaoTiposDocumento)){
			// Filtra Localidade
			FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_GERAR_HISTORICO_IMOVEL,
							ConstantesSistema.SIM));
			filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);
			colecaoTiposDocumento = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoTiposDocumento)){
				sessao.setAttribute(COLECAO_TIPOS_DOCUMENTO, colecaoTiposDocumento);
			}else{

				throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Documento");
			}
		}
	}

	/**
	 * Carrega coleção de Tipos de Serviço
	 * 
	 * @author Luciano Galvao
	 * @date 18/09/2012
	 * @param sessao
	 */
	private void carregarTiposServico(HttpSession sessao){

		Collection colecaoTiposServico = (Collection) sessao.getAttribute(COLECAO_TIPOS_SERVICO);

		if(Util.isVazioOrNulo(colecaoTiposServico)){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_GERAR_HISTORICO_IMOVEL,
							ConstantesSistema.SIM));
			filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
			colecaoTiposServico = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoTiposServico)){
				sessao.setAttribute(COLECAO_TIPOS_SERVICO, colecaoTiposServico);
			}else{

				throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Serviço");
			}
		}
	}
}