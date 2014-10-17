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

package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioAuditoriaLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * @author Felipe Rosacruz
 * @date 04/10/2013
 */
public class GerarRelatorioAuditoriaLeituraAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("ExibirGerarRelatorioAuditoriaLeitura");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		
		DynaActionForm form = (DynaActionForm) actionForm;

		Integer idFaturamentoGrupo = null;

		idFaturamentoGrupo = Integer.parseInt((String) form.get("P_GP_FATURAMENTO"));

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples("id", idFaturamentoGrupo));
		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);

		FiltroRota filtroRotaSelecao = new FiltroRota();
		filtroRotaSelecao.adicionarParametro(new ParametroSimples("faturamentoGrupo.id", idFaturamentoGrupo));

		Integer idLocalidade = null;
		if(form.get("localidadeID") != null && !form.get("localidadeID").equals("")){
		
			idLocalidade = Integer.parseInt((String) form.get("localidadeID"));
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples("id", idLocalidade));
			Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(Util.isVazioOrNulo(colecaoLocalidade)){
				throw new ActionServletException("pesquisa.localidade.inexistente");
			}

			Localidade localidade = (Localidade) Util
.retonarObjetoDeColecao(colecaoLocalidade);
		
			boolean indicadorVinculoLocalidadeFaturamentoGrupo = fachada.consultarVinculoLocalidadeFaturamentoGrupo(idFaturamentoGrupo,
							idLocalidade);
			
			if(!indicadorVinculoLocalidadeFaturamentoGrupo){
				form.set("descricaoSetorComercial", "");
				form.set("setorComercialCD", "");
				form.set("setorComercialID", "");
				form.set("cdRota", "");
				form.set("descricaoRota", "");
				throw new ActionServletException("atencao.localidade.nao.vinculada.faturamento.grupo",
								"exibirGerarRelatorioAuditoriaLeituraAction.do", new Exception(),
 localidade.getDescricao(),
									faturamentoGrupo.getDescricao());
			}

			filtroRotaSelecao.adicionarParametro(new ParametroSimples("setorComercial.localidade.id", idLocalidade));
		}

		Integer cdSetorComercial = null;
		if(form.get("setorComercialCD") != null && !form.get("setorComercialCD").equals("")){

			cdSetorComercial = Integer.parseInt((String) form.get("setorComercialCD"));
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, cdSetorComercial));
			Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
			if(Util.isVazioOrNulo(colecaoSetorComercial)){
				throw new ActionServletException("atencao.setor_comercial.inexistente");
			}

			boolean indicadorVinculoSetorComercialFaturamentoGrupo = fachada.consultarVinculoSetorComercialFaturamentoGrupo(
							idFaturamentoGrupo,
 cdSetorComercial);

			if(!indicadorVinculoSetorComercialFaturamentoGrupo){

				form.set("cdRota", "");
				form.set("descricaoRota", "");
				throw new ActionServletException("atencao.setor.comercial.nao.vinculado.faturamento.grupo",
								"exibirGerarRelatorioAuditoriaLeituraAction.do", new Exception(), cdSetorComercial.toString(),
								faturamentoGrupo.getDescricao());
			}

			filtroRotaSelecao.adicionarParametro(new ParametroSimples("setorComercial.codigo", cdSetorComercial));
		}
		Integer cdRota = null;
		if(form.get("cdRota") != null && !form.get("cdRota").equals("")){

			cdRota = Integer.parseInt((String) form.get("cdRota"));
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, cdRota));
			Collection<Rota> colecaoRotas = fachada.pesquisar(filtroRota, Rota.class.getName());
			if(Util.isVazioOrNulo(colecaoRotas)){
				throw new ActionServletException("atencao.pesquisa.rota_inexistente");
			}

			Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRotas);
			if(rota.getIndicadorGerarFiscalizacao().equals(ConstantesSistema.NAO)){
				throw new ActionServletException("atencao.rota.nao.disponivel.auditoria", rota.getCodigo().toString());
			}

			boolean indicadorVinculoSetorComercialFaturamentoGrupo = fachada.consultarVinculoRotaFaturamentoGrupo(idFaturamentoGrupo,
							cdRota);

			if(!indicadorVinculoSetorComercialFaturamentoGrupo){

				throw new ActionServletException("atencao.rota.nao.vinculada.faturamento.grupo",
								"exibirGerarRelatorioAuditoriaLeituraAction.do", new Exception(), rota.getCodigo().toString(),
								faturamentoGrupo.getDescricao());
			}

			filtroRotaSelecao.adicionarParametro(new ParametroSimples("id", rota.getId()));
		}
		
		
		filtroRotaSelecao.adicionarParametro(new ParametroSimples("indicadorGerarFiscalizacao", ConstantesSistema.SIM));

		Collection<Rota> colecaoRotas = fachada.pesquisar(filtroRotaSelecao, Rota.class.getName());

		if(Util.isVazioOrNulo(colecaoRotas)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		Collection<Integer> colecaoIdsRotas = new ArrayList<Integer>();
		for(Rota rota : colecaoRotas){
			colecaoIdsRotas.add(rota.getId());
		}
		
		Integer tipoRelatorio = Integer.parseInt(ConstantesSistema.PDF);
		
		RelatorioAuditoriaLeitura relatorioAuditoriaLeitura = new RelatorioAuditoriaLeitura(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioAuditoriaLeitura.addParametro("colecaoIdsRotas", colecaoIdsRotas);
		relatorioAuditoriaLeitura.addParametro("idFaturamentoGrupo", idFaturamentoGrupo);
		relatorioAuditoriaLeitura.addParametro("idLocalidade", idLocalidade);
		relatorioAuditoriaLeitura.addParametro("cdSetorComercial", cdSetorComercial);
		relatorioAuditoriaLeitura.addParametro("tipoFormatoRelatorio", tipoRelatorio);
		relatorioAuditoriaLeitura.addParametro("cdRota", cdRota);
		
		try{
			retorno = processarExibicaoRelatorio(relatorioAuditoriaLeitura, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
