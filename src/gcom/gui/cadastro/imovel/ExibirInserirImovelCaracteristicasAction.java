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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.*;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ExecutorParametrosCadastro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

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
public class ExibirInserirImovelCaracteristicasAction
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
	 * @author eduardo henrique
	 * @date 16/04/2009
	 *       Alteração no carregamento das opções de Situação de Ligação de Água (Virtual)
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirImovelCaracteristicas");

		// Obtendo uma instância da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm inserirImovelClienteActionForm = (DynaValidatorForm) sessao.getAttribute("InserirImovelActionForm");

		// Cria filtros
		FiltroAreaConstruidaFaixa filtroAreaConstruida = new FiltroAreaConstruidaFaixa();
		FiltroReservatorioVolumeFaixa filtroReservatorioVolumeFaixa = new FiltroReservatorioVolumeFaixa();
		FiltroPadraoConstrucao filtroPadraoConstrucao = new FiltroPadraoConstrucao();
		FiltroEsgotamento filtroEsgotamento = new FiltroEsgotamento();
		FiltroPiscinaVolumeFaixa filtroPiscinaVolumeFaixa = new FiltroPiscinaVolumeFaixa();
		FiltroPavimentoCalcada filtroPavimentoCalcada = new FiltroPavimentoCalcada();
		FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua();
		FiltroFonteAbastecimento filtroFonteAbastecimento = new FiltroFonteAbastecimento();
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
		FiltroPocoTipo filtroPocoTipo = new FiltroPocoTipo();
		FiltroDespejo filtroDespejo = new FiltroDespejo();
		FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
		FiltroSubBacia filtroSubBacia = new FiltroSubBacia();

		// Cria Coleçao
		Collection areaContruidaFaixas = null;
		Collection reservatorioVolumeFaixas = null;
		Collection padroesConstrucao = null;
		Collection esgotamentos = null;
		Collection piscinaVolumeFaixas = null;
		Collection pavimetoCalcadas = null;
		Collection pavimentoRuas = null;
		Collection fonteAbastecimentos = null;
		Collection ligacaoEsgotoSituacaos = null;
		Collection ligacaoAguaSituacaos = null;
		Collection pocoTipos = null;
		Collection imovelPerfis = null;
		Collection despejos = null;
		Collection<SetorAbastecimento> colecaoSetorAbastecimento = null;
		Collection<SubBacia> colecaoSubBacia = null;

		// Obtém a instancia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Faz as pesuisas e testa se as colecoes estao vazias antes de jogalas
		// na sessao
		filtroAreaConstruida.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		areaContruidaFaixas = fachada.pesquisar(filtroAreaConstruida, AreaConstruidaFaixa.class.getName());
		if(areaContruidaFaixas == null || areaContruidaFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Area Construida Faixa");
		}
		filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		reservatorioVolumeFaixas = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());
		if(reservatorioVolumeFaixas == null || reservatorioVolumeFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Reservatorio Volume Faixa");
		}

		filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		padroesConstrucao = fachada.pesquisar(filtroPadraoConstrucao, PadraoConstrucao.class.getName());
		if(padroesConstrucao == null || padroesConstrucao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Padrão de Contrução");
		}

		filtroEsgotamento.adicionarParametro(new ParametroSimples(FiltroEsgotamento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		esgotamentos = fachada.pesquisar(filtroEsgotamento, Esgotamento.class.getName());
		if(esgotamentos == null || esgotamentos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Esgotamentos");
		}

		filtroPiscinaVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroPiscinaVolumeFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		piscinaVolumeFaixas = fachada.pesquisar(filtroPiscinaVolumeFaixa, PiscinaVolumeFaixa.class.getName());
		if(piscinaVolumeFaixas == null || piscinaVolumeFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Piscina Volume Faixa");
		}

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(!getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
			// ********** Companhia - ADA ******************************
			// jardim
			if(inserirImovelClienteActionForm.get("jardim") != null && inserirImovelClienteActionForm.get("jardim").equals("")){
				inserirImovelClienteActionForm.set("jardim", "2");
			}
			// ********** Companhia - ADA ******************************
		}

		// filtro pavimento calçada
		filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPavimentoCalcada.setCampoOrderBy(FiltroPavimentoCalcada.DESCRICAO);
		pavimetoCalcadas = fachada.pesquisar(filtroPavimentoCalcada, PavimentoCalcada.class.getName());
		if(pavimetoCalcadas == null || pavimetoCalcadas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Pavimento Calçada");
		}
		// filtro pavimento rua
		filtroPavimentoRua
						.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPavimentoRua.setCampoOrderBy(FiltroPavimentoRua.DESCRICAO);
		pavimentoRuas = fachada.pesquisar(filtroPavimentoRua, PavimentoRua.class.getName());
		if(pavimentoRuas == null || pavimentoRuas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Pavimento Rua");
		}

		// filtro fonte abastecimento
		filtroFonteAbastecimento.adicionarParametro(new ParametroSimples(FiltroFonteAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroFonteAbastecimento.setCampoOrderBy(FiltroFonteAbastecimento.DESCRICAO);
		fonteAbastecimentos = fachada.pesquisar(filtroFonteAbastecimento, FonteAbastecimento.class.getName());
		if(fonteAbastecimentos == null || fonteAbastecimentos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Fonte Abastecimento");
		}
		// TESTE PARA SABER SE A QUADRA TEM OU NÃO REDE DE AGUA
		Quadra quadraSessao = (Quadra) sessao.getAttribute("quadraCaracteristicas");

		String idLocalidade = (String) inserirImovelClienteActionForm.get("idLocalidade");
		String idSetorComercial = (String) inserirImovelClienteActionForm.get("idSetorComercial");

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, quadraSessao.getId()));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, Integer.valueOf(idLocalidade)));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, Integer.valueOf(idSetorComercial)));
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ZONA_ABASTECIMENTO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.BACIA);

		Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		Quadra quadra = (Quadra) colecaoQuadra.iterator().next();

		if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.SEM_REDE)){

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.POTENCIAL,
							FiltroParametro.CONECTOR_OR));

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.VIRTUAL));
		}
		if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.COM_REDE)){

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.FACTIVEL,
							FiltroParametro.CONECTOR_OR));

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.VIRTUAL));
		}
		if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.REDE_PARCIAL)){
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.POTENCIAL,
							FiltroParametro.CONECTOR_OR));
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.FACTIVEL,
							FiltroParametro.CONECTOR_OR));

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, LigacaoAguaSituacao.VIRTUAL));
		}
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
		ligacaoAguaSituacaos = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		if(ligacaoAguaSituacaos == null || ligacaoAguaSituacaos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado");
		}
		// Verifica parâmento de abastecimento obrigatorio
		// 2.25.1.Caso o parâmetro P_CRITICAR_ESGOTO_QUADRA tenha valor 1.
		try{
			if(ParametroCadastro.P_FONTE_DE_ABASTECIMENTO_IMOVEL_OBRIGATORIO.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorFonteAbastecimentoObrigatorio", true);
			}else{
				sessao.removeAttribute("indicadorFonteAbastecimentoObrigatorio");
			}
			// paramentro de fonte de abastecimento
			String[] idAtual = ((String) ParametroCadastro.P_SITUACAO_ESGOTO_NAO_PERMITE_ALTERAR_TIPO_DE_DESPEJO
							.executar(ExecutorParametrosCadastro.getInstancia())).split(",");
			sessao.setAttribute("situacaoEsgotoNaoPermiteAlterarTipoDespejo", idAtual);
			//
			if(ParametroAtendimentoPublico.P_CRITICAR_ESGOTO_QUADRA.executar().equals(ConstantesSistema.SIM.toString())){
				// TESTE PARA SABER SE A QUADRA TEM OU NÃO REDE DE ESGOTO
				if(quadra.getIndicadorRedeEsgoto() != null && quadra.getIndicadorRedeEsgoto().equals(Quadra.SEM_REDE)){
					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
									LigacaoEsgotoSituacao.POTENCIAL));
				}
				if(quadra.getIndicadorRedeEsgoto() != null && quadra.getIndicadorRedeEsgoto().equals(Quadra.COM_REDE)){
					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
									LigacaoEsgotoSituacao.FACTIVEL));
				}
				if(quadra.getIndicadorRedeEsgoto() != null && quadra.getIndicadorRedeEsgoto().equals(Quadra.REDE_PARCIAL)){
					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
									LigacaoEsgotoSituacao.POTENCIAL, FiltroParametro.CONECTOR_OR));
					filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
									LigacaoEsgotoSituacao.FACTIVEL));
				}
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		ligacaoEsgotoSituacaos = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

		if(ligacaoEsgotoSituacaos == null || ligacaoEsgotoSituacaos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ligacao Esgoto Situação");
		}

		filtroPocoTipo.adicionarParametro(new ParametroSimples(FiltroPocoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPocoTipo.setCampoOrderBy(FiltroPocoTipo.DESCRICAO);
		pocoTipos = fachada.pesquisar(filtroPocoTipo, PocoTipo.class.getName());
		if(pocoTipos == null || pocoTipos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Poço Tipo");
		}

		String indicadorCadastroPrevioTarifaSocial = null;
		try{
			indicadorCadastroPrevioTarifaSocial = ParametroCadastro.P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL.executar();
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente" + " - P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL.");
		}

		filtroImovelPerfil
		.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		
		if (Util.isNaoNuloBrancoZero(indicadorCadastroPrevioTarifaSocial) && indicadorCadastroPrevioTarifaSocial.equals(ConstantesSistema.SIM.toString())){
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_GERACAO_AUTOMATICA, ImovelPerfil.NAO));
			filtroImovelPerfil.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelPerfil.DESCRICAO, "TARIFA SOCIAL"));
			filtroImovelPerfil.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelPerfil.ID, ImovelPerfil.FACTIVEL_EM_ESPERA));
			filtroImovelPerfil.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelPerfil.ID, ImovelPerfil.FACTIVEL_FATURADO));
		}
				
		filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
		imovelPerfis = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());
		if(imovelPerfis == null || imovelPerfis.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Imovel Perfil");
		}

		filtroDespejo.adicionarParametro(new ParametroSimples(FiltroDespejo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroDespejo.setCampoOrderBy(FiltroDespejo.DESCRICAO);
		despejos = fachada.pesquisar(filtroDespejo, Despejo.class.getName());
		if(despejos == null || despejos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Despejo");
		}

		// Setor de Abastecimento
		ZonaAbastecimento zonaAbastecimento = quadra.getZonaAbastecimento();

		if(zonaAbastecimento != null){
			Integer idZonaAbastecimento = zonaAbastecimento.getId();

			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO_ID,
							idZonaAbastecimento));
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSetorAbastecimento.setCampoOrderBy(FiltroSetorAbastecimento.DESCRICAO);

			colecaoSetorAbastecimento = fachada.pesquisar(filtroSetorAbastecimento, SetorAbastecimento.class.getName());
		}

		// Sub-bacia
		Bacia bacia = quadra.getBacia();

		if(bacia != null){
			Integer idBacia = bacia.getId();

			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.BACIA_ID, idBacia));
			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSubBacia.setCampoOrderBy(FiltroSubBacia.DESCRICAO);

			colecaoSubBacia = fachada.pesquisar(filtroSubBacia, SubBacia.class.getName());
		}

		httpServletRequest.setAttribute("areaContruidaFaixas", areaContruidaFaixas);
		httpServletRequest.setAttribute("reservatorioVolumeFaixas", reservatorioVolumeFaixas);
		httpServletRequest.setAttribute("padroesConstrucao", padroesConstrucao);
		httpServletRequest.setAttribute("esgotamentos", esgotamentos);
		httpServletRequest.setAttribute("piscinaVolumeFaixas", piscinaVolumeFaixas);
		httpServletRequest.setAttribute("pavimetoCalcadas", pavimetoCalcadas);
		httpServletRequest.setAttribute("pavimentoRuas", pavimentoRuas);
		httpServletRequest.setAttribute("fonteAbastecimentos", fonteAbastecimentos);
		httpServletRequest.setAttribute("ligacaoAguaSituacaos", ligacaoAguaSituacaos);
		httpServletRequest.setAttribute("ligacaoEsgotoSituacaos", ligacaoEsgotoSituacaos);
		httpServletRequest.setAttribute("perfilImoveis", imovelPerfis);
		httpServletRequest.setAttribute("pocoTipos", pocoTipos);
		httpServletRequest.setAttribute("tipoDespejos", despejos);
		httpServletRequest.setAttribute("colecaoSetorAbastecimento", colecaoSetorAbastecimento);
		httpServletRequest.setAttribute("colecaoSubBacia", colecaoSubBacia);


		return retorno;
	}
}
