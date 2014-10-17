/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltrarImovelAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		String redirecionar = (String) sessao.getAttribute("redirecionar");

		if(redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && "ManterVinculoImoveisRateioConsumo".equals(redirecionar)){
			retorno = actionMapping.findForward("retornarVinculosImoveisRateioConsumo");
		}else if(redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && "ManterDadosTarifaSocial".equals(redirecionar)){
			retorno = actionMapping.findForward("retornarManterDadosTarifaSocial");
		}else{
			retorno = actionMapping.findForward("retornarFiltroImovel");
		}
		FiltrarImovelActionForm filtrarImovelActionForm = (FiltrarImovelActionForm) actionForm;

		String idLocalidade = filtrarImovelActionForm.getIdLocalidadeFiltro();
		String idSetorComercial = filtrarImovelActionForm.getIdSetorComercialFiltro();
		String idQuadra = filtrarImovelActionForm.getIdQuadraFiltro();
		String codigoHidrometro = filtrarImovelActionForm.getCodigoHidrometroFiltro();
		String idHidrometroHistInst = null;
		String lote = filtrarImovelActionForm.getLoteFiltro();
		String subLote = filtrarImovelActionForm.getSubLoteFiltro();
		String codigoCliente = filtrarImovelActionForm.getIdClienteFiltro();
		String idMunicipio = filtrarImovelActionForm.getIdMunicipioFiltro();
		String cep = filtrarImovelActionForm.getCepFiltro();
		String idBairro = filtrarImovelActionForm.getIdBairroFiltro();
		String idLogradouro = filtrarImovelActionForm.getIdLogradouroFiltro();
		// String indicadorUso = (String) filtrarImovelActionForm.get("indicadorUso");
		String matricula = filtrarImovelActionForm.getMatriculaFiltro();

		String atualizar = httpServletRequest.getParameter("atualizarFiltro");

		if(atualizar != null && atualizar.equals("1")){
			httpServletRequest.setAttribute("atualizar", atualizar);
		}else{
			filtrarImovelActionForm.setAtualizarFiltro("");
		}

		boolean peloMenosUmParametroInformado = false;

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");

		if(redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && "ManterVinculoImoveisRateioConsumo".equals(redirecionar)){
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
							ClienteRelacaoTipo.USUARIO));
		}

		// matricula
		if(matricula != null && !matricula.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matricula));
		}
		// localidade
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			// filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.LOCALIDADE_ID, new Integer(idLocalidade)));
		}
		// setor comercial
		if(idSetorComercial != null && !idSetorComercial.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.SETOR_COMERCIAL_CODIGO, new Integer(
							idSetorComercial)));
		}
		// quadra
		if(idQuadra != null && !idQuadra.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota");
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.QUADRA_NUMERO, new Integer(idQuadra)));
		}

		// hidrometro
		if(codigoHidrometro != null && !codigoHidrometro.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;
			// Pesquisa o hidrometro pelo codigo
			Hidrometro hidrometro = fachada.pesquisarHidrometroPeloNumero(codigoHidrometro);

			FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
			filtroHidrometroInstalacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("hidrometro");

			if(hidrometro != null){
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroHidrometroInstalacaoHistorico.HIDROMETRO_ID, hidrometro.getId()));
				// Pesquisa o historico pelo id do hidrometro
				Collection colecaoHidrometroInsHist = fachada.pesquisar(filtroHidrometroInstalacaoHistorico,
								HidrometroInstalacaoHistorico.class.getName());

				if(!colecaoHidrometroInsHist.isEmpty()){
					filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.hidrometroInstalacaoHistorico");

					// Varre o historico em busa do hidrometro que est� instalado, ou seja, com a
					// data de retirada nula.
					for(Iterator iterator = colecaoHidrometroInsHist.iterator(); iterator.hasNext();){
						Object historico = iterator.next();

						filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.HIDROMETRO_INSTALACAO_HISTORICO_ID,
										((HidrometroInstalacaoHistorico) historico).getId(), FiltroParametro.CONECTOR_OR));

						filtroClienteImovel.adicionarParametro(new ParametroSimples(
										FiltroClienteImovel.HIDROMETRO_INSTALACAO_HISTORICO_DATA_RETIRADA, null));

						// Se a data de retirada do historico do hidrometro estiver nulla, seta o id
						// do historico do hidrometro
						// para ser recuperado na pesquisar de imovel clinete.
						if(((HidrometroInstalacaoHistorico) historico).getDataRetirada() == null){
							idHidrometroHistInst = "" + ((HidrometroInstalacaoHistorico) historico).getId();
						}
					}
				}
			}

			if(idHidrometroHistInst == null){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Im�vel");
			}
		}

		// lote
		if(lote != null && !lote.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.LOTE, new Short(lote)));
		}
		// lote
		if(subLote != null && !subLote.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.SUBLOTE, new Short(subLote)));
		}
		// cliente
		if(codigoCliente != null && !codigoCliente.trim().equalsIgnoreCase("")){

			// filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, new Integer(codigoCliente)));
		}
		// municipio
		if(idMunicipio != null && !idMunicipio.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial.municipio");
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.MUNICIPIO_SETOR_COMERICAL_CODIGO, new Integer(
							idMunicipio)));
		}
		// cep
		if(cep != null && !cep.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			// *filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CEP_CODIGO, cep));
		}
		// bairro
		if(idBairro != null && !idBairro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.BAIRRO_CODIGO, new Integer(idBairro)));
		}
		// logradouro
		if(idLogradouro != null && !idLogradouro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro");
			filtroClienteImovel.adicionarParametro(new ComparacaoTexto(FiltroClienteImovel.LOGRADOURO_ID, idLogradouro));
		}

		filtroClienteImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO,
						Imovel.IMOVEL_EXCLUIDO, FiltroParametro.CONECTOR_OR, 2));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO));

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}
		httpServletRequest.setAttribute("filtroImovelPreenchido", filtroClienteImovel);
		// sessao.setAttribute("pesquisaQuadra", "1");

		sessao.setAttribute("filtroImovelPreenchido", filtroClienteImovel);

		sessao.setAttribute("idLocalidade", idLocalidade);
		sessao.setAttribute("idSetorComercial", idSetorComercial);
		sessao.setAttribute("idQuadra", idQuadra);
		sessao.setAttribute("lote", lote);
		sessao.setAttribute("subLote", subLote);
		sessao.setAttribute("codigoCliente", codigoCliente);
		sessao.setAttribute("idMunicipio", idMunicipio);
		sessao.setAttribute("cep", cep);
		sessao.setAttribute("idBairro", idBairro);
		sessao.setAttribute("idLogradouro", idLogradouro);

		sessao.setAttribute("idImovel", matricula);

		sessao.setAttribute("codigoHidrometro", codigoHidrometro);
		sessao.setAttribute("idHidrometroHistInst", idHidrometroHistInst);

		sessao.removeAttribute("primeiraVez");
		// sessao.removeAttribute("redirecionar");
		return retorno;
	}

}
