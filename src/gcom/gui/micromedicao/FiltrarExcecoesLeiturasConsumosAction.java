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

package gcom.gui.micromedicao;

import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

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
public class FiltrarExcecoesLeiturasConsumosAction
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

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(true);

		String nomeCaminhoMapping = (String) sessao.getAttribute("nomeCaminhoMapping");

		ActionForward retorno = actionMapping.findForward(nomeCaminhoMapping);

		LeituraConsumoActionForm leituraConsumoActionForm = (LeituraConsumoActionForm) actionForm;

		leituraConsumoActionForm.setCaminhoReload("/gsan/exibirManterAnaliseExcecoesConsumosAction.do");

		// obtem instancia da fachada
		Fachada fachada = Fachada.getInstancia();

		// sessao.removeAttribute("leituraConsumoActionForm");

		// ----------- Parte 1 - Pesquisar por Imovel subCategoria - 1.1 > 1.10
		String localidade = (String) leituraConsumoActionForm.getLocalidadeFiltro();
		String setorComercial = (String) leituraConsumoActionForm.getSetorComercialFiltro();
		String nmQuadraInicial = (String) leituraConsumoActionForm.getQuadraInicialFiltro();
		String nmQuadraFinal = (String) leituraConsumoActionForm.getQuadraFinalFiltro();
		String nmRotaInicial = (String) leituraConsumoActionForm.getRotaInicialFiltro();
		String nmRotaFinal = (String) leituraConsumoActionForm.getRotaFinalFiltro();
		String matricula = (String) leituraConsumoActionForm.getImovelFiltro();
		String matriculaCondominio = (String) leituraConsumoActionForm.getImovelCondominioFiltro();
		String grupoFaturamento = (String) leituraConsumoActionForm.getIdGrupoFaturamentoFiltro();
		String idEmpresa = (String) leituraConsumoActionForm.getIdEmpresaFiltro();
		String indicadorImovelCondominio = null;
		String indicadorRateio = null;

		if(leituraConsumoActionForm.getIndicadorImovelCondominioFiltro() != null
						&& (leituraConsumoActionForm.getIndicadorImovelCondominioFiltro().equalsIgnoreCase("S") || (leituraConsumoActionForm
										.getImovelCondominioFiltro() != null && !leituraConsumoActionForm.getImovelCondominioFiltro()
										.equals("")))){

			indicadorImovelCondominio = "1";
		}else if(leituraConsumoActionForm.getIndicadorImovelCondominioFiltro() != null
						&& leituraConsumoActionForm.getIndicadorImovelCondominioFiltro().equalsIgnoreCase("N")){
			indicadorImovelCondominio = "2";
		}else{
			indicadorImovelCondominio = "3";
		}
		sessao.setAttribute("indicadorImovelCondominio", indicadorImovelCondominio);

		// Indicador de Rateio
		if(leituraConsumoActionForm.getIndicadorRateio() != null && leituraConsumoActionForm.getIndicadorRateio().equalsIgnoreCase("S")){
			indicadorRateio = "1";
		}else if(leituraConsumoActionForm.getIndicadorRateio() != null
						&& leituraConsumoActionForm.getIndicadorRateio().equalsIgnoreCase("N")){
			indicadorRateio = "2";
		}else{
			indicadorRateio = "3";
		}

		sessao.setAttribute("indicadorRateio", indicadorRateio);

		String perfilImovel = (String) leituraConsumoActionForm.getPerfilImovelFiltro();
		String categoria = (String) leituraConsumoActionForm.getCategoriaImovelFiltro();
		String qtdEconomias = (String) leituraConsumoActionForm.getQuantidadeEconomiaFiltro();
		// ----------- Parte 2 - Pesquisar Por Consumo Historico e Medicao
		// Historico
		String tipoMedicao = (String) leituraConsumoActionForm.getTipoMedicaoFiltro();
		String tipoLigacao = (String) leituraConsumoActionForm.getTipoLigacaoFiltro();
		String tipoAnormalidade = (String) leituraConsumoActionForm.getTipoAnormalidadeFiltro();
		String anormalidadeLeituraInformada = (String) leituraConsumoActionForm.getAnormalidadeLeituraInformadaFiltro();
		String anormalidadeLeituraFaturada = (String) leituraConsumoActionForm.getAnormalidadeLeituraFaturadaFiltro();
		String anormalidadeConsumo = (String) leituraConsumoActionForm.getAnormalidadeConsumoFiltro();
		String consumoFaturadoMinimo = (String) leituraConsumoActionForm.getConsumoFaturamdoMinimoFiltro();
		String consumoMedidoMinimo = (String) leituraConsumoActionForm.getConsumoMedidoMinimoFiltro();
		String consumoMedioMinimo = (String) leituraConsumoActionForm.getConsumoMedioMinimoFiltro();
		String tipoApresentacao = (String) leituraConsumoActionForm.getTipoApresentacao();

		String indicadorDebitoAutomatico = (String) leituraConsumoActionForm.getIndicadorDebitoAutomatico();

		String leituraSituacaoAtual = leituraConsumoActionForm.getLeituraSituacaoAtualFiltro();

		sessao.setAttribute("tipoApresentacao", tipoApresentacao);

		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql = new FiltroMedicaoHistoricoSql();

		// --------------Verifica existencia
		// ---Imovel e Imovel Condominio
		// =====================================================================

		if(matricula != null && !matricula.trim().equalsIgnoreCase("")){

			String inscricao = fachada.pesquisarInscricaoImovel(Integer.valueOf(matricula), true);
			if(inscricao == null){
				boolean testeExclusao = fachada.confirmarImovelExcluido(Integer.valueOf(matricula));
				if(testeExclusao){
					throw new ActionServletException("atencao.imovel.excluido");
				}
				throw new ActionServletException("atencao.naocadastrado", null, "im�vel");
			}
		}
		// ======================================================================
		if(matriculaCondominio != null && !matriculaCondominio.trim().equalsIgnoreCase("")){
			String condominio = fachada.pesquisarInscricaoImovel(Integer.valueOf(matriculaCondominio), true);

			if(condominio == null || condominio.trim().equals("")){
				throw new ActionServletException("atencao.naocadastrado", null, "im�vel condominio");
			}
		}

		// ---- Localidade
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		if(localidade != null && !localidade.trim().equalsIgnoreCase("")){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidade));
		}
		Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		if(localidades.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "localidade");
		}
		// ---- Setor Comercial
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercial));
		}
		Collection<SetorComercial> setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
		if(setorComerciais.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "setor comercial");
		}
		// Se a empresa for DESO, a pesquisa � pela Rota.
		if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			FiltroRota filtroRotaInicial = new FiltroRota();
			if(nmRotaInicial != null && !nmRotaInicial.trim().equalsIgnoreCase("")){
				Integer rotaInicial = Integer.valueOf(nmRotaInicial);
				filtroRotaInicial.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaInicial));
				if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
					// Adiciona o id do setor comercial que est� no formul�rio
					// para compor a pesquisa.
					filtroRotaInicial.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, setorComercial));
				}

				filtroRotaInicial.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Rota> rotaIniciais = fachada.pesquisar(filtroRotaInicial, Rota.class.getName());
				if(rotaIniciais.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Rota Inicial");
				}
			}

			// ---- Rota Final
			FiltroRota filtroRotaFinal = new FiltroRota();
			if(nmRotaFinal != null && !nmRotaFinal.trim().equalsIgnoreCase("")){
				Integer rotaFinal = Integer.valueOf(nmRotaFinal);

				filtroRotaFinal.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaFinal));

				if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
					// Adiciona o id do setor comercial que est� no formul�rio
					// para compor a pesquisa.
					filtroRotaFinal.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, setorComercial));
				}

				filtroRotaFinal.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Rota> rotaFinais = fachada.pesquisar(filtroRotaFinal, Rota.class.getName());
				if(rotaFinais.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Rota Final");
				}
			}
			// Se for ADA a pesquisa � pela Quadra
		}else if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_ADA.shortValue()){
			FiltroQuadra filtroQuadraInicial = new FiltroQuadra();
			if(nmQuadraInicial != null && !nmQuadraInicial.trim().equalsIgnoreCase("")){
				Integer quadraIni = Integer.valueOf(nmQuadraInicial);
				filtroQuadraInicial.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraIni));
				if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
					// Adiciona o id do setor comercial que est� no formul�rio
					// para
					// compor a pesquisa.
					filtroQuadraInicial.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, setorComercial));
				}

				filtroQuadraInicial.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Quadra> quadraIniciais = fachada.pesquisar(filtroQuadraInicial, Quadra.class.getName());
				if(quadraIniciais.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Quadra Inicial");
				}
			}

			// ---- Quadra Final
			FiltroQuadra filtroQuadraFinal = new FiltroQuadra();
			if(nmQuadraFinal != null && !nmQuadraFinal.trim().equalsIgnoreCase("")){
				Integer quadraFin = Integer.valueOf(nmQuadraInicial);

				filtroQuadraFinal.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraFin));

				if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
					// Adiciona o id do setor comercial que est� no formul�rio
					// para
					// compor a pesquisa.
					filtroQuadraFinal.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, setorComercial));
				}

				filtroQuadraFinal
								.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Quadra> quadraFinais = fachada.pesquisar(filtroQuadraFinal, Quadra.class.getName());
				if(quadraFinais.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Quadra Final");
				}
			}
		}

		// ----- Fim verifica Existencia

		boolean peloMenosUmParametro = false;
		// -------- Parte 1

		if(matricula != null && !matricula.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.IMOVEL_ID, Integer
							.valueOf(matricula)));
		}

		if(matriculaCondominio != null && !matriculaCondominio.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.IMOVEL_CONDOMINIO_ID, Integer
							.valueOf(matriculaCondominio)));
		}

		if(grupoFaturamento != null && !grupoFaturamento.trim().equalsIgnoreCase("" + +ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !grupoFaturamento.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.GRUPO_FATURAMENTO, Integer
							.valueOf(grupoFaturamento)));

			FiltroGrupo filtroGrupo = new FiltroGrupo();
			filtroGrupo.adicionarParametro(new ParametroSimples(FiltroGrupo.ID, grupoFaturamento));
			Collection colecaoGrupo = fachada.pesquisar(filtroGrupo, FaturamentoGrupo.class.getName());
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) colecaoGrupo.iterator().next();
			sessao.setAttribute("faturamentoGrupo", faturamentoGrupo);
		}

		if(idEmpresa != null && !idEmpresa.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.IMOVEL_EMPRESA, idEmpresa));
		}

		if(localidade != null && !localidade.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.LOCALIDADE_IMOVEL, Integer
							.valueOf(localidade)));
		}

		if(setorComercial != null && !setorComercial.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.SETOR_COMERCIAL_IMOVEL_CODIGO,
							Integer.valueOf(setorComercial)));
		}

		if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			if(nmRotaInicial != null && !nmRotaInicial.trim().equalsIgnoreCase("")){
				peloMenosUmParametro = true;

				if(nmRotaFinal == null || nmRotaFinal.trim().equalsIgnoreCase("")){
					nmRotaFinal = nmRotaInicial;
				}
				filtroMedicaoHistoricoSql.adicionarParametro(new Intervalo(FiltroMedicaoHistoricoSql.ROTA_IMOVEL_NUMERO, Integer
								.valueOf(nmRotaInicial), Integer.valueOf(nmRotaFinal)));
			}
		}else if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_ADA.shortValue()){
			if(nmQuadraInicial != null && !nmQuadraInicial.trim().equalsIgnoreCase("")){
				peloMenosUmParametro = true;

				if(nmQuadraFinal == null || nmQuadraFinal.trim().equalsIgnoreCase("")){
					nmQuadraFinal = nmQuadraInicial;
				}
				filtroMedicaoHistoricoSql.adicionarParametro(new Intervalo(FiltroMedicaoHistoricoSql.QUADRA_IMOVEL_NUMERO, Integer
								.valueOf(nmQuadraInicial), Integer.valueOf(nmQuadraFinal)));
			}
		}

		if(indicadorImovelCondominio != null && !indicadorImovelCondominio.trim().equalsIgnoreCase("")){
			if(indicadorImovelCondominio.trim().equalsIgnoreCase("1")){
				peloMenosUmParametro = true;

				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.INDICADOR_IMOVEL_CONDOMINIO,
								Short.valueOf(indicadorImovelCondominio)));
			}else if(indicadorImovelCondominio.trim().equalsIgnoreCase("2")){
				peloMenosUmParametro = true;

				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.INDICADOR_IMOVEL_CONDOMINIO,
								Short.valueOf(indicadorImovelCondominio)));
			}
		}

		if(indicadorDebitoAutomatico != null && !indicadorDebitoAutomatico.trim().equals("")){
			if(indicadorDebitoAutomatico.trim().equals("S")){
				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.INDICADOR_DEBITO_AUTOMATICO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else if(indicadorDebitoAutomatico.trim().equals("N")){
				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.INDICADOR_DEBITO_AUTOMATICO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		if(perfilImovel != null && !perfilImovel.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.PERFIL_IMOVEL, Integer
							.valueOf(perfilImovel)));
		}

		if(categoria != null && !categoria.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.IMOVEL_CATEGORIA, Integer
							.valueOf(categoria)));
		}

		// ---- fim parte 1

		// ---- parte 2

		// ----Por Medicao Historico

		if(tipoMedicao != null && !tipoMedicao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.MH_MEDICAO_TIPO_ID, tipoMedicao));
		}

		if(tipoAnormalidade != null && !tipoAnormalidade.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			if(tipoAnormalidade.trim().equalsIgnoreCase(ConstantesSistema.ANORMALIDADE_LEITURA_INFORMADA)){
				peloMenosUmParametro = true;
				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroNaoNulo(FiltroMedicaoHistoricoSql.MH_ANORMALIDADE_INFORMADA));
			}else if(tipoAnormalidade.trim().equalsIgnoreCase(ConstantesSistema.ANORMALIDADE_LEITURA_FATURADA)){
				peloMenosUmParametro = true;
				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroNaoNulo(FiltroMedicaoHistoricoSql.MH_ANORMALIDADE_FATURADA));
			}else if(tipoAnormalidade.trim().equalsIgnoreCase(ConstantesSistema.ANORMALIDADE_CONSUMO)){
				peloMenosUmParametro = true;
				filtroMedicaoHistoricoSql.adicionarParametro(new ParametroNaoNulo(FiltroMedicaoHistoricoSql.MH_ANORMALIDADE_CONSUMO));
			}
		}

		if(anormalidadeLeituraInformada != null
						&& !anormalidadeLeituraInformada.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.MH_ANORMALIDADE_INFORMADA, Integer
							.valueOf(anormalidadeLeituraInformada)));
		}

		if(anormalidadeLeituraFaturada != null
						&& !anormalidadeLeituraFaturada.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.MH_ANORMALIDADE_FATURADA, Integer
							.valueOf(anormalidadeLeituraFaturada)));
		}

		// ----- Por Consumo Historico
		if(tipoLigacao != null && !tipoLigacao.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.CONSUMO_HISTORICO_LIGACAO_TIPO,
							Integer.valueOf(tipoLigacao)));
		}

		if(anormalidadeConsumo != null && !anormalidadeConsumo.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(
							FiltroMedicaoHistoricoSql.CONSUMO_HISTORICO_ANORMALIDADE_CONSUMO, Integer.valueOf(anormalidadeConsumo)));
		}

		if(consumoFaturadoMinimo != null && !consumoFaturadoMinimo.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new MaiorQue(FiltroMedicaoHistoricoSql.CONSUMO_HISTORICO_FATURADO_MES,
							consumoFaturadoMinimo));
		}

		if(consumoMedidoMinimo != null && !consumoMedidoMinimo.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql
							.adicionarParametro(new MaiorQue(FiltroMedicaoHistoricoSql.MH_CONSUMO_MEDIDO_MES, consumoMedidoMinimo));
		}

		if(qtdEconomias != null && !qtdEconomias.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.MH_IMOVEL_QTD_ECONOMIAS,
							qtdEconomias));
		}

		if(consumoMedioMinimo != null && !consumoMedioMinimo.trim().equalsIgnoreCase("")){
			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new MaiorQue(FiltroMedicaoHistoricoSql.CONSUMO_HISTORICO_CONSUMO_MEDIO,
							consumoMedioMinimo));
		}

		if(leituraSituacaoAtual != null && !leituraSituacaoAtual.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			peloMenosUmParametro = true;

			filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.MH_LEITURA_SITUACAO_ATUAL, Integer
							.valueOf(leituraSituacaoAtual)));
		}

		filtroMedicaoHistoricoSql.adicionarParametro(new ParametroSimples(FiltroMedicaoHistoricoSql.IMOVEL_INDICADOR_EXCLUSAO,
						ConstantesSistema.INDICADOR_IMOVEL_ATIVO));

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametro){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}

		sessao.setAttribute("leituraConsumoActionForm", leituraConsumoActionForm);
		sessao.setAttribute("filtroMedicaoHistoricoSql", filtroMedicaoHistoricoSql);

		return retorno;
	}

}
