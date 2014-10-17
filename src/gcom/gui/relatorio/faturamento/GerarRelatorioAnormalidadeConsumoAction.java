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

package gcom.gui.relatorio.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.faturamento.GerarRelatorioAnormalidadeConsumoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioAnormalidadeConsumo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição do relatório de contas em revisão
 * 
 * @author Rafael Corrêa
 * @created 20/09/2007
 */
public class GerarRelatorioAnormalidadeConsumoAction
				extends ExibidorProcessamentoTarefaRelatorio {

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

		// cria a variável de retorno
		ActionForward retorno = null;

		GerarRelatorioAnormalidadeConsumoActionForm gerarRelatorioAnormalidadeConsumoActionForm = (GerarRelatorioAnormalidadeConsumoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Valida os parâmetro passados como consulta
		boolean peloMenosUmParametroInformado = false;

		// Grupo
		Integer idGrupo = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getGrupo() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getGrupo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idGrupo = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getGrupo());
		}

		// Rota
		Short cdRota = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getRota() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getRota().trim().equals("")){
			peloMenosUmParametroInformado = true;
			cdRota = new Short(gerarRelatorioAnormalidadeConsumoActionForm.getRota());
		}

		// Gerência Regional
		Integer idGerenciaRegional = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getRegional() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idGerenciaRegional = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getRegional());
		}

		// Unidade de Negócio
		Integer idUnidadeNegocio = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getUnidadeNegocio() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getUnidadeNegocio().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			idUnidadeNegocio = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getUnidadeNegocio());
		}

		// Elo
		Localidade elo = null;

		String idElo = gerarRelatorioAnormalidadeConsumoActionForm.getIdElo();

		if(idElo != null && !idElo.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idElo));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, idElo));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				elo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
			}
		}

		// Localidade Inicial
		Localidade localidadeInicial = null;

		String idLocalidadeInicial = gerarRelatorioAnormalidadeConsumoActionForm.getIdLocalidadeInicial();

		if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				localidadeInicial = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
			}
		}

		// Localidade Final
		Localidade localidadeFinal = null;

		String idLocalidadeFinal = gerarRelatorioAnormalidadeConsumoActionForm.getIdLocalidadeFinal();

		if(idLocalidadeFinal != null && !idLocalidadeFinal.equals("")){
			peloMenosUmParametroInformado = true;

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeFinal));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				localidadeFinal = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
			}
		}

		// Anormalidade de Consumo
		Integer idAnormalidadeConsumo = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getIdConsumoAnormalidade() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getIdConsumoAnormalidade().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			idAnormalidadeConsumo = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getIdConsumoAnormalidade());
		}

		// Anormalidade de Consumo
		Integer idAnormalidadeLeitura = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getIdLeituraAnormalidade() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getIdLeituraAnormalidade().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			idAnormalidadeLeitura = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getIdLeituraAnormalidade());
		}

		// Perfil do Imóvel
		Integer idImovelPerfil = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getIdImovelPerfil() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getIdImovelPerfil().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;

			idImovelPerfil = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getIdImovelPerfil());
		}

		// Anormalidade de Leitura
		// LeituraAnormalidade leituraAnormalidade = null;
		//
		// String idAnormalidadeLeitura = gerarRelatorioAnormalidadeConsumoActionForm
		// .getIdLeituraAnormalidade();
		//
		// if (idAnormalidadeLeitura != null && !idAnormalidadeLeitura.equals("")) {
		// peloMenosUmParametroInformado = true;
		//
		// FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
		//
		// filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(
		// FiltroLeituraAnormalidade.ID, idAnormalidadeLeitura));
		//
		// Collection colecaoAnormalidadesLeitura = fachada.pesquisar(
		// filtroLeituraAnormalidade, LeituraAnormalidade.class
		// .getName());
		//
		// if (colecaoAnormalidadesLeitura != null
		// && !colecaoAnormalidadesLeitura.isEmpty()) {
		// leituraAnormalidade = (LeituraAnormalidade) Util
		// .retonarObjetoDeColecao(colecaoAnormalidadesLeitura);
		// } else {
		// throw new ActionServletException(
		// "atencao.pesquisa_inexistente", null,
		// "Anormalidade de Leitura");
		// }
		// }

		// Referência
		Integer referencia = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getReferencia() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getReferencia().equals("")){
			peloMenosUmParametroInformado = true;

			referencia = Util.formatarMesAnoComBarraParaAnoMes(gerarRelatorioAnormalidadeConsumoActionForm.getReferencia());

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			if(referencia > sistemaParametro.getAnoMesFaturamento()){
				throw new ActionServletException("atencao.ano_mes_referencia_anterior_que_ano_mes_faturamento_corrente", null, Util
								.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento()));
			}

		}

		Integer mediaConsumoInicial = null;
		Integer mediaConsumoFinal = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getIntervaloMediaConsumoInicial() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getIntervaloMediaConsumoInicial().trim().equals("")){
			peloMenosUmParametroInformado = true;

			mediaConsumoInicial = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getIntervaloMediaConsumoInicial());

			mediaConsumoFinal = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getIntervaloMediaConsumoFinal());

			if(mediaConsumoInicial > mediaConsumoFinal){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

		}

		Integer numeroOcorrenciasConsecutivas = null;

		if(gerarRelatorioAnormalidadeConsumoActionForm.getNumOcorrenciasConsecutivas() != null
						&& !gerarRelatorioAnormalidadeConsumoActionForm.getNumOcorrenciasConsecutivas().trim().equals("")){
			numeroOcorrenciasConsecutivas = new Integer(gerarRelatorioAnormalidadeConsumoActionForm.getNumOcorrenciasConsecutivas());

			if(numeroOcorrenciasConsecutivas > 12){
				throw new ActionServletException("atencao.quantidade_ocorrencia_maior");
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// seta os parametros que serão mostrados no relatório

		String indicadorOcorrenciasIguais = gerarRelatorioAnormalidadeConsumoActionForm.getIndicadorOcorrenciasIguais();

		Integer totalRegistros = null;
		try{
			totalRegistros = fachada.pesquisarDadosRelatorioAnormalidadeConsumoCount(idGrupo, cdRota, idGerenciaRegional, idUnidadeNegocio,
							Util.converterStringParaInteger(idElo), Util.converterStringParaInteger(idLocalidadeInicial),
							Util.converterStringParaInteger(idLocalidadeFinal), referencia,
 idImovelPerfil, numeroOcorrenciasConsecutivas,
							indicadorOcorrenciasIguais, mediaConsumoInicial, mediaConsumoFinal, idAnormalidadeConsumo,
							idAnormalidadeLeitura);


		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(totalRegistros == null || totalRegistros.intValue() == 0){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// Fim da parte que vai mandar os parametros para o relatório
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		RelatorioAnormalidadeConsumo relatorioAnormalidadeConsumo = new RelatorioAnormalidadeConsumo((Usuario) (httpServletRequest
						.getSession(false)).getAttribute("usuarioLogado"));

		relatorioAnormalidadeConsumo.addParametro("idGrupo", idGrupo);

		relatorioAnormalidadeConsumo.addParametro("cdRota", cdRota);

		relatorioAnormalidadeConsumo.addParametro("idGerenciaRegional", idGerenciaRegional);

		relatorioAnormalidadeConsumo.addParametro("idUnidadeNegocio", idUnidadeNegocio);

		if(elo != null){
			relatorioAnormalidadeConsumo.addParametro("idElo", elo.getId());
		}

		if(localidadeInicial != null){
			relatorioAnormalidadeConsumo.addParametro("idLocalidadeInicial", localidadeInicial.getId());
		}

		if(localidadeFinal != null){
			relatorioAnormalidadeConsumo.addParametro("idLocalidadeFinal", localidadeFinal.getId());
		}

		// if (leituraAnormalidade != null) {
		// relatorioAnormalidadeConsumo.addParametro("idAnormalidadeLeitura",
		// leituraAnormalidade.getId());
		// }

		relatorioAnormalidadeConsumo.addParametro("idAnormalidadeLeitura", idAnormalidadeLeitura);

		relatorioAnormalidadeConsumo.addParametro("idAnormalidadeConsumo", idAnormalidadeConsumo);

		relatorioAnormalidadeConsumo.addParametro("numeroOcorrencias", numeroOcorrenciasConsecutivas);

		relatorioAnormalidadeConsumo.addParametro("ocorrenciasIguais", gerarRelatorioAnormalidadeConsumoActionForm
						.getIndicadorOcorrenciasIguais());

		relatorioAnormalidadeConsumo.addParametro("idImovelPerfil", idImovelPerfil);
		relatorioAnormalidadeConsumo.addParametro("referencia", referencia);

		relatorioAnormalidadeConsumo.addParametro("mediaConsumoInicial", mediaConsumoInicial);
		relatorioAnormalidadeConsumo.addParametro("mediaConsumoFinal", mediaConsumoFinal);

		relatorioAnormalidadeConsumo.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorioAnormalidadeConsumo, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}
