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

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
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
public class InserirImovelLocalidadeAction
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

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// instanciando o ActionForm de InserirImovelLocalidadeActionForm
		DynaValidatorForm inserirImovelLocalidadeActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

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
		SetorComercial setorComercial = null;
		Quadra quadraNova = null;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// atribui os valores q vem pelo request as variaveis
		idLocalidade = (String) inserirImovelLocalidadeActionForm.get("idLocalidade");
		codigoSetorComercial = (String) inserirImovelLocalidadeActionForm.get("idSetorComercial");
		numeroQuadra = (String) inserirImovelLocalidadeActionForm.get("idQuadra");
		lote = (String) inserirImovelLocalidadeActionForm.get("lote");
		subLote = (String) inserirImovelLocalidadeActionForm.get("subLote");
		testadaLote = (String) inserirImovelLocalidadeActionForm.get("testadaLote");
		sequencialRota = (String) inserirImovelLocalidadeActionForm.get("sequencialRota");
		idRota = (String) inserirImovelLocalidadeActionForm.get("idRota");
		nnSegmento = (String) inserirImovelLocalidadeActionForm.get("nnSegmento");
		idDistritoOperacional = (String) inserirImovelLocalidadeActionForm.get("idDistritoOperacional");

		sessao.setAttribute("idRota", idRota);
		sessao.setAttribute("nnSegmento", nnSegmento);

		// Cria Filtros
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Seta os objetos
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADEPORTE);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ID_ELO);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.GERENCIAREGIONAL);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ENDERECOREFERENCIA);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADECLASSE);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROCEP);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROBAIRRO);
			filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.UNIDADENEGOCIO);

			Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(!localidades.isEmpty()){
				// coloca os bojetos na sessão
				sessao.setAttribute("localidade", localidades.iterator().next());
			}else{

				throw new ActionServletException("pesquisa.localidade.inexistente");
			}

			if(codigoSetorComercial != null && !codigoSetorComercial.trim().equalsIgnoreCase("")){
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
								codigoSetorComercial)));
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(idLocalidade)));

				Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(!setorComerciais.isEmpty()){
					sessao.setAttribute("setorComerciais", setorComerciais);
					setorComercial = (SetorComercial) setorComerciais.iterator().next();
					sessao.setAttribute("setorComercial", setorComercial);
				}else{
					throw new ActionServletException("atencao.setor_comercial.inexistente");
				}

			}

			if(numeroQuadra != null && !numeroQuadra.trim().equalsIgnoreCase("")){

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(numeroQuadra.trim())));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(setorComercial
								.getCodigo())));

				Collection quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(!quadras.isEmpty()){
					quadraNova = (Quadra) quadras.iterator().next();
					sessao.setAttribute("quadra", quadraNova);
					sessao.setAttribute("quadraCaracteristicas", quadraNova);
				}else{
					throw new ActionServletException("atencao.quadra.inexistente");
				}

			}

		}

		if(!Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(codigoSetorComercial) && !Util.isVazioOuBranco(numeroQuadra)
						&& !Util.isVazioOuBranco(lote) && !Util.isVazioOuBranco(subLote)){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, new Integer(idLocalidade)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, new Integer(codigoSetorComercial)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, new Integer(numeroQuadra)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOTE, new Integer(lote)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.SUBLOTE, new Integer(subLote)));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, new Integer("2")));

			Collection imoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(!Util.isVazioOrNulo(imoveis)){
				Imovel imovel = (Imovel) imoveis.iterator().next();
				throw new ActionServletException("atencao.imovel_ja_cadastrado", null, imovel.getId().toString());
			}
		}

		// Rota
		if(!Util.isVazioOuBranco(idRota)){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL_LOCALIDADE);
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, idRota));

			Collection<Rota> rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(!Util.isVazioOrNulo(rotas)){
				Rota rota = (Rota) Util.retonarObjetoDeColecao(rotas);
				sessao.setAttribute("rota", rota);
			}else{
				throw new ActionServletException("atencao.rota_inexistente");
			}
		}

		if(Util.isNaoNuloBrancoZero(idLocalidade) && Util.isNaoNuloBrancoZero(codigoSetorComercial)
						&& Util.isNaoNuloBrancoZero(numeroQuadra) && Util.isNaoNuloBrancoZero(idRota)){
			fachada.verificarAlteracaoRota(Integer.valueOf(idLocalidade), Integer.valueOf(codigoSetorComercial),
							Integer.valueOf(numeroQuadra), Integer.valueOf(idRota));
		}

		// Capturando Distrito Operacional
		if(!Util.isVazioOuBranco(idDistritoOperacional) && (!idDistritoOperacional.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, idDistritoOperacional));

			Collection<DistritoOperacional> distritos = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());

			if(!Util.isVazioOrNulo(distritos)){
				DistritoOperacional distritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(distritos);
				sessao.setAttribute("distritoOperacional", distritoOperacional);
			}else{
				throw new ActionServletException("atencao.distrito_operacional_setor_abastecimento_nao_informado");
			}
		}else if((quadraNova != null)
						&& ((quadraNova.getIndicadorRedeAgua().equals(Quadra.COM_REDE)) || ((quadraNova.getIndicadorRedeAgua()
										.equals(Quadra.REDE_PARCIAL))))){

			throw new ActionServletException("atencao.distrito_operacional_obrigatorio");
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

		idLocalidade = Util.adicionarZerosEsquedaNumero(3, new Integer(idLocalidade.trim()).toString());
		codigoSetorComercial = Util.adicionarZerosEsquedaNumero(3, new Integer(codigoSetorComercial.trim()).toString());
		numeroQuadra = Util.adicionarZerosEsquedaNumero(3, new Integer(numeroQuadra.trim()).toString());
		lote = Util.adicionarZerosEsquedaNumero(4, new Integer(lote.trim()).toString());
		subLote = Util.adicionarZerosEsquedaNumero(3, new Integer(subLote.trim()).toString());

		// atribui os valores q vem pelo request as variaveis
		inserirImovelLocalidadeActionForm.set("idLocalidade", idLocalidade);
		inserirImovelLocalidadeActionForm.set("idDistritoOperacional", idDistritoOperacional);
		inserirImovelLocalidadeActionForm.set("idSetorComercial", codigoSetorComercial);
		inserirImovelLocalidadeActionForm.set("idQuadra", numeroQuadra);
		inserirImovelLocalidadeActionForm.set("lote", lote);
		inserirImovelLocalidadeActionForm.set("subLote", subLote);

		httpServletRequest.setAttribute("destino", "inserirImovelEndereco");

		return retorno;
	}
}
