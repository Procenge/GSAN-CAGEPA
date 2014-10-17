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

import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class AtualizarImovelLocalidadeAction
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

		ActionForward retorno = null;

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("ExibirAbaLocalidade") != null && sessao.getAttribute("ExibirAbaLocalidade").equals("N")){
			retorno = actionMapping.findForward("atualizarImovelLocalidadeEndereco");
		}else{
			retorno = actionMapping.findForward("gerenciadorProcesso");
		}

		DynaValidatorForm inserirImovelLocalidadeActionForm = (DynaValidatorForm) actionForm;

		// Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		// Cria variaveis
		String idLocalidade = null;
		String codigoSetorComercial = null;
		String numeroQuadra = null;
		String lote = null;
		String subLote = null;
		String idRota = null;
		String nnSegmento = null;
		String testadaLote = null;
		String sequencialRota = null;
		String idDistritoOperacional = null;
		Quadra quadraNova = null;

		// atribui os valores q vem pelo request as variaveis
		idLocalidade = (String) inserirImovelLocalidadeActionForm.get("idLocalidade");
		codigoSetorComercial = (String) inserirImovelLocalidadeActionForm.get("idSetorComercial");
		numeroQuadra = (String) inserirImovelLocalidadeActionForm.get("idQuadra");
		lote = (String) inserirImovelLocalidadeActionForm.get("lote");
		subLote = (String) inserirImovelLocalidadeActionForm.get("subLote");
		// cdRota = (String) inserirImovelLocalidadeActionForm.get("cdRota");
		idRota = (String) inserirImovelLocalidadeActionForm.get("idRota");
		nnSegmento = (String) inserirImovelLocalidadeActionForm.get("nnSegmento");
		testadaLote = (String) inserirImovelLocalidadeActionForm.get("testadaLote");
		sequencialRota = (String) inserirImovelLocalidadeActionForm.get("sequencialRota");
		idDistritoOperacional = (String) inserirImovelLocalidadeActionForm.get("idDistritoOperacional");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria Filtros
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Seta os objetos
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADEPORTE);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ID_ELO);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.GERENCIAREGIONAL);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ENDERECOREFERENCIA);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADECLASSE);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROCEP);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROBAIRRO);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.UNIDADENEGOCIO);

			Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(localidades.isEmpty()){
				throw new ActionServletException("atencao.localidade.inexistente");
			}
			sessao.setAttribute("localidade", localidades.iterator().next());

			if(codigoSetorComercial != null && !codigoSetorComercial.trim().equalsIgnoreCase("")){
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
								.valueOf(codigoSetorComercial)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
								.valueOf(idLocalidade)));

				Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(setorComerciais.isEmpty()){
					throw new ActionServletException("atencao.setor_comercial.inexistente");
				}
				sessao.setAttribute("setorComercial", setorComerciais.iterator().next());
				sessao.setAttribute("setorComerciais", setorComerciais);
			}

			if(numeroQuadra != null && !numeroQuadra.trim().equalsIgnoreCase("")){
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Integer.valueOf(numeroQuadra)));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, Integer
								.valueOf(codigoSetorComercial)));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, Integer.valueOf(idLocalidade)));

				Collection quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(quadras.isEmpty()){
					throw new ActionServletException("atencao.quadra.inexistente");
				}

				quadraNova = (Quadra) quadras.iterator().next();
				sessao.setAttribute("quadra", quadraNova);
				sessao.setAttribute("quadraCaracteristicas", quadraNova);
			}
		}


		// Rota
		if(!Util.isVazioOuBranco(idRota)){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.FATURAMENTO_GRUPO);
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, idRota));

			Collection<Rota> rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(!Util.isVazioOrNulo(rotas)){
				Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
				sessao.setAttribute("rota", rota);
			}else{
				throw new ActionServletException("atencao.rota_inexistente");
			}
		}

		
		// Capturando Distrito Operacional
		if(!Util.isVazioOuBranco(idDistritoOperacional)
						&& (!Integer.valueOf(idDistritoOperacional).equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, idDistritoOperacional));

			Collection<DistritoOperacional> distritos = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if(!Util.isVazioOrNulo(distritos)){
				DistritoOperacional distritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(distritos);
				sessao.setAttribute("distritoOperacional", distritoOperacional);
			}else{
				throw new ActionServletException("atencao.pesquisa.distrito_operacional_inexistente");
			}
		}else if((quadraNova != null)
							&& ((quadraNova.getIndicadorRedeAgua().equals(Quadra.COM_REDE)) || ((quadraNova.getIndicadorRedeAgua()
											.equals(Quadra.REDE_PARCIAL))))){

			throw new ActionServletException("atencao.pesquisa.distrito_operacional_obrigatorio");
		}

		if(nnSegmento != null && !nnSegmento.trim().equalsIgnoreCase("")){
			String nnSegmentoAux = Util.adicionarZerosEsquedaNumero(2, new Integer(nnSegmento.trim()).toString());
			inserirImovelLocalidadeActionForm.set("nnSegmento", nnSegmentoAux);
		}

		if(testadaLote != null && !testadaLote.trim().equalsIgnoreCase("")){
			String testadaLoteAux = Util.adicionarZerosEsquedaNumero(2, new Integer(testadaLote.trim()).toString());
			inserirImovelLocalidadeActionForm.set("testadaLote", testadaLoteAux);
		}

		if(sequencialRota != null && !sequencialRota.trim().equalsIgnoreCase("")){
			String sequencialRotaAux = Util.adicionarZerosEsquedaNumero(4, new Integer(sequencialRota.trim()).toString());
			inserirImovelLocalidadeActionForm.set("sequencialRota", sequencialRotaAux);
		}

		if(Util.isNaoNuloBrancoZero(idLocalidade) && Util.isNaoNuloBrancoZero(codigoSetorComercial)
						&& Util.isNaoNuloBrancoZero(numeroQuadra) && Util.isNaoNuloBrancoZero(idRota)){
			fachada.verificarAlteracaoRota(Integer.valueOf(idLocalidade), Integer.valueOf(codigoSetorComercial),
							Integer.valueOf(numeroQuadra), Integer.valueOf(idRota));
		}

		idLocalidade = Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(idLocalidade).toString());
		codigoSetorComercial = Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(codigoSetorComercial).toString());
		lote = Util.adicionarZerosEsquedaNumero(4, Integer.valueOf(lote).toString());
		subLote = Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(subLote).toString());

		// atribui os valores q vem pelo request as variaveis
		inserirImovelLocalidadeActionForm.set("idLocalidade", idLocalidade);
		inserirImovelLocalidadeActionForm.set("idDistritoOperacional", idDistritoOperacional);
		inserirImovelLocalidadeActionForm.set("idSetorComercial", codigoSetorComercial);
		inserirImovelLocalidadeActionForm.set("idQuadra", numeroQuadra);
		inserirImovelLocalidadeActionForm.set("lote", lote);
		inserirImovelLocalidadeActionForm.set("subLote", subLote);

		return retorno;
	}
}
