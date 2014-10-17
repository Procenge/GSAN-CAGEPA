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
import gcom.util.filtro.*;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ExecutorParametrosCadastro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Exibir os dados da Caracteristica do imovel
 * 
 * @author Flávio, Rafael Santos
 * @since 07/02/2006
 */
public class ExibirAtualizarImovelCaracteristicasAction
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

		ActionForward retorno = actionMapping.findForward("atualizarImovelCaracteristicas");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm atualizarImovelCaracteristicasActionForm = (DynaValidatorForm) actionForm;

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

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Faz as pesuisas e testa se as colecoes estao vazias antes de jogalas
		// na sessao

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			// CARREGA OS DADOS DA AREA CONSTRUIDA
			filtroAreaConstruida.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			areaContruidaFaixas = fachada.pesquisar(filtroAreaConstruida, AreaConstruidaFaixa.class.getName());
			if(areaContruidaFaixas == null || areaContruidaFaixas.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Area Construida Faixa");
			}
			// ********** Companhia - ADA ******************************
		}
		// CARREGA OS DADOS DO RESERVATORIO
		filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		reservatorioVolumeFaixas = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());
		if(reservatorioVolumeFaixas == null || reservatorioVolumeFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Reservatorio Volume Faixa");
		}

		// CARREGA OS DADOS DO Padrao de Contrucao
		filtroPadraoConstrucao.setCampoOrderBy(FiltroPadraoConstrucao.DESCRICAO);
		filtroPadraoConstrucao.adicionarParametro(new ParametroSimples(FiltroPadraoConstrucao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		padroesConstrucao = fachada.pesquisar(filtroPadraoConstrucao, PadraoConstrucao.class.getName());
		if(padroesConstrucao == null || padroesConstrucao.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Padrões Construção");
		}
		httpServletRequest.setAttribute("padroesConstrucao", padroesConstrucao);

		// CARREGA OS DADOS DO Esgotamento
		filtroEsgotamento.setCampoOrderBy(FiltroEsgotamento.DESCRICAO);
		filtroEsgotamento.adicionarParametro(new ParametroSimples(FiltroEsgotamento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		esgotamentos = fachada.pesquisar(filtroEsgotamento, Esgotamento.class.getName());
		if(esgotamentos == null || esgotamentos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Esgotamentos");
		}
		httpServletRequest.setAttribute("esgotamentos", esgotamentos);

		// CARREGA OS DADOS DE PISCINA
		filtroPiscinaVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroPiscinaVolumeFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		piscinaVolumeFaixas = fachada.pesquisar(filtroPiscinaVolumeFaixa, PiscinaVolumeFaixa.class.getName());
		if(piscinaVolumeFaixas == null || piscinaVolumeFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Piscina Volume Faixa");
		}
		// CARREGA OS DADOS DE PAVIMENTO CALÇADA
		filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPavimentoCalcada.setCampoOrderBy(FiltroPavimentoCalcada.DESCRICAO);
		// filtro pavimento calcadas
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

		// CARREGAR OS DADOS DE FONTE DE ABASTECIMENTO
		filtroFonteAbastecimento.adicionarParametro(new ParametroSimples(FiltroFonteAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroFonteAbastecimento.setCampoOrderBy(FiltroFonteAbastecimento.DESCRICAO);

		fonteAbastecimentos = fachada.pesquisar(filtroFonteAbastecimento, FonteAbastecimento.class.getName());
		if(fonteAbastecimentos == null || fonteAbastecimentos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Fonte Abastecimento");
		}

		// CARREGAR OS DADOS DE POÇO TIPO
		filtroPocoTipo.setCampoOrderBy(FiltroPocoTipo.DESCRICAO);
		filtroPocoTipo.adicionarParametro(new ParametroSimples(FiltroPocoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		pocoTipos = fachada.pesquisar(filtroPocoTipo, PocoTipo.class.getName());
		if(pocoTipos == null || pocoTipos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Poço Tipo");
		}

		Imovel imovel = null;
		if(sessao.getAttribute("imovelAtualizacao") != null){
			imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");
		}

		// CARRGAR OS DADOS DE IMOVEL PERFIL
		String indicadorCadastroPrevioTarifaSocial = null;
		try{
			indicadorCadastroPrevioTarifaSocial = ParametroCadastro.P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL.executar();
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema.inexistente" + " - P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL.");
		}

		if(imovel != null && (imovel.getImovelPerfil().getId().intValue() == ImovelPerfil.GRANDE_NO_MES.intValue())){
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, ImovelPerfil.GRANDE_NO_MES,
							ConectorOr.CONECTOR_OR));
		}
		filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);

		if(Util.isNaoNuloBrancoZero(indicadorCadastroPrevioTarifaSocial)
						&& indicadorCadastroPrevioTarifaSocial.equals(ConstantesSistema.SIM.toString())){
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_GERACAO_AUTOMATICA, ImovelPerfil.NAO));
			filtroImovelPerfil.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelPerfil.DESCRICAO, "TARIFA SOCIAL"));
		}

		// if(!(imovel.getImovelPerfil() != null &&
		// imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL))){
		// filtroImovelPerfil.adicionarParametro(new
		// ParametroSimples(FiltroImovelPerfil.INDICADOR_GERACAO_AUTOMATICA, ImovelPerfil.NAO));
		// }
		filtroImovelPerfil
						.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		imovelPerfis = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

		if(imovelPerfis == null || imovelPerfis.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Imovel Perfil");
		}
		// CARREGAR OS DADOS DE DESPEJO
		filtroDespejo.setCampoOrderBy(FiltroDespejo.DESCRICAO);
		filtroDespejo.adicionarParametro(new ParametroSimples(FiltroDespejo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		despejos = fachada.pesquisar(filtroDespejo, Despejo.class.getName());
		if(despejos == null || despejos.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Despejo");
		}

		// IMOVEL QUE ESTA SENDO ATUALIZADO
		// Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");
		Quadra quadra = imovel.getQuadra();
		String idQuadra = (String) atualizarImovelCaracteristicasActionForm.get("idQuadra");
		String idSetorComercial = (String) atualizarImovelCaracteristicasActionForm.get("idSetorComercial");
		String idLocalidade = (String) atualizarImovelCaracteristicasActionForm.get("idLocalidade");

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(idSetorComercial)));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(idQuadra)));
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ZONA_ABASTECIMENTO);
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.BACIA);

		// OBTEM A QUADRA QUE ESTA NO BANCO, POIS OS DADOS DELA PODE MUDAR NO MOMENTO DA MANUTENÇÃO
		// DO IMOVEL
		Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		quadra = (Quadra) colecaoQuadra.iterator().next();

		// [FS008] - VERIFICAR SITUAÇÃO DA LIGAÇÃO AGUA - FACTIVEL e POTENCIAL
		// verifica a situação primeiro do imovel para depois caso não satisfaça a condição faz em
		// relação a quadra
		if(imovel.getLigacaoAguaSituacao() != null
						&& (imovel.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.FACTIVEL.intValue() & imovel
										.getLigacaoAguaSituacao().getId().intValue() != LigacaoAguaSituacao.POTENCIAL.intValue())){
			// carrega apenas a situação de agua do imovel
			Collection situacaoAguaImovel = new ArrayList();
			situacaoAguaImovel.add(imovel.getLigacaoAguaSituacao());
			ligacaoAguaSituacaos = situacaoAguaImovel;

			httpServletRequest.setAttribute("semSituacaoAgua", "true");
		}else{
			httpServletRequest.setAttribute("semSituacaoAgua", "false");

			// CARREGAR OS DADOS DE LIGAÇÃO SITUAÇÃO AGUA
			// TESTE PARA SABER SE A QUADRA TEM OU NÃO REDE DE AGUA
			if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.SEM_REDE)){
				filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
								LigacaoAguaSituacao.POTENCIAL));
			}
			if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.COM_REDE)){
				filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
								LigacaoAguaSituacao.FACTIVEL));
			}
			if(quadra.getIndicadorRedeAgua() != null && quadra.getIndicadorRedeAgua().equals(Quadra.REDE_PARCIAL)){
				filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
								LigacaoAguaSituacao.POTENCIAL, FiltroParametro.CONECTOR_OR));
				filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
								LigacaoAguaSituacao.FACTIVEL));
			}
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
			ligacaoAguaSituacaos = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

			if(ligacaoAguaSituacaos == null || ligacaoAguaSituacaos.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Ligação Água Situação");
			}

		}

		// [FS009] - VERIFICAR SITUAÇÃO DA LIGAÇÃO ESGOTO - FACTIVEL e POTENCIA
		// verifica a situação primeiro do imovel para depois caso não satisfaça a condição faz em
		// relação a quadra
		if(imovel.getLigacaoEsgotoSituacao() != null
						&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.FACTIVEL.intValue() &

						imovel.getLigacaoEsgotoSituacao().getId().intValue() != LigacaoEsgotoSituacao.POTENCIAL.intValue())){
			// carrega apenas a situação do esgoto do imovel
			Collection situacaoEsgotoImovel = new ArrayList();
			situacaoEsgotoImovel.add(imovel.getLigacaoEsgotoSituacao());
			ligacaoEsgotoSituacaos = situacaoEsgotoImovel;

			httpServletRequest.setAttribute("semSituacaoEsgoto", "true");
		}else{
			httpServletRequest.setAttribute("semSituacaoEsgoto", "false");

			// 2.25.1.Caso o parâmetro P_CRITICAR_ESGOTO_QUADRA tenha valor 1.
			try{
				if(ParametroAtendimentoPublico.P_CRITICAR_ESGOTO_QUADRA.executar().equals(ConstantesSistema.SIM.toString())){
					// CARREGAR OS DADOS DE LIGAÇÃO SITUAÇÃO ESGOTO
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
				throw new ActionServletException("atencao.naocadastrado", null, "Ligação Esgoto Situação");
			}
		}

		// LOCAL QUE SETA OS DADOS NO REQUEST
		httpServletRequest.setAttribute("piscinaVolumeFaixas", piscinaVolumeFaixas);
		String piscina = (String) atualizarImovelCaracteristicasActionForm.get("piscina");
		if(piscina != null && !piscina.equalsIgnoreCase("")){
			// Seta o valor da faixa de piscina
			filtroPiscinaVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroPiscinaVolumeFaixa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroPiscinaVolumeFaixa.adicionarParametro(new MaiorQue(FiltroPiscinaVolumeFaixa.VOLUME_MAIOR_FAIXA, new BigDecimal(piscina
							.replace(',', '.'))));
			filtroPiscinaVolumeFaixa.adicionarParametro(new MenorQue(FiltroPiscinaVolumeFaixa.VOLUME_MENOR_FAIXA, new BigDecimal(piscina
							.replace(',', '.'))));

			Collection piscinasFaixas = fachada.pesquisar(filtroPiscinaVolumeFaixa, PiscinaVolumeFaixa.class.getName());
			PiscinaVolumeFaixa piscinaVolumeFaixa = null;
			if(piscinasFaixas != null && !piscinasFaixas.isEmpty()){

				Iterator piscinasFaixasIterator = piscinasFaixas.iterator();

				piscinaVolumeFaixa = (PiscinaVolumeFaixa) piscinasFaixasIterator.next();
				atualizarImovelCaracteristicasActionForm.set("faixaPiscina", piscinaVolumeFaixa.getId().toString());
			}
		}

		// Paramentro para diferenciar a companhia que o sistema está rodando
		if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_ADA)){
			// ********** Companhia - ADA ******************************
			httpServletRequest.setAttribute("areaContruidaFaixas", areaContruidaFaixas);

			String areaConstruida = (String) atualizarImovelCaracteristicasActionForm.get("areaConstruida");
			if(areaConstruida != null && !areaConstruida.equalsIgnoreCase("")){
				// seta o valor da faixa da area construida
				filtroAreaConstruida.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroAreaConstruida.adicionarParametro(new MaiorQue(FiltroAreaConstruidaFaixa.MAIOR_FAIXA, new BigDecimal(areaConstruida
								.replace(',', '.'))));
				filtroAreaConstruida.adicionarParametro(new MenorQue(FiltroAreaConstruidaFaixa.MENOR_FAIXA, new BigDecimal(areaConstruida
								.replace(',', '.'))));

				Collection areasConstruidasFaixas = fachada.pesquisar(filtroAreaConstruida, AreaConstruidaFaixa.class.getName());
				AreaConstruidaFaixa areaConstruidaFaixa = null;
				if(areasConstruidasFaixas != null && !areasConstruidasFaixas.isEmpty()){

					Iterator areaContruidaFaixaIterator = areasConstruidasFaixas.iterator();

					areaConstruidaFaixa = (AreaConstruidaFaixa) areaContruidaFaixaIterator.next();
					atualizarImovelCaracteristicasActionForm.set("faixaAreaConstruida", areaConstruidaFaixa.getId().toString());
				}
			}
			// ********** Companhia - ADA ******************************
		}

		httpServletRequest.setAttribute("reservatorioVolumeFaixas", reservatorioVolumeFaixas);
		String reservatorioInferior = (String) atualizarImovelCaracteristicasActionForm.get("reservatorioInferior");
		if(reservatorioInferior != null && !reservatorioInferior.equalsIgnoreCase("")){
			// Seta o valor da faixa do reservatorio inferior
			filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MaiorQue(FiltroReservatorioVolumeFaixa.VOLUME_MAIOR_FAIXA, new BigDecimal(
							reservatorioInferior.replace(',', '.'))));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MenorQue(FiltroReservatorioVolumeFaixa.VOLUME_MENOR_FAIXA, new BigDecimal(
							reservatorioInferior.replace(',', '.'))));

			Collection inferioresFaixas = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());
			ReservatorioVolumeFaixa reservatorioVolumeFaixa = null;
			if(inferioresFaixas != null && !inferioresFaixas.isEmpty()){

				Iterator inferioresFaixasIterator = inferioresFaixas.iterator();

				reservatorioVolumeFaixa = (ReservatorioVolumeFaixa) inferioresFaixasIterator.next();
				atualizarImovelCaracteristicasActionForm.set("faixaReservatorioInferior", reservatorioVolumeFaixa.getId().toString());
			}
		}

		String reservatorioSuperior = (String) atualizarImovelCaracteristicasActionForm.get("reservatorioSuperior");
		if(reservatorioSuperior != null && !reservatorioSuperior.equalsIgnoreCase("")){
			// Seta o valor da faixa do reservatorio inferior
			filtroReservatorioVolumeFaixa.adicionarParametro(new ParametroSimples(FiltroReservatorioVolumeFaixa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MaiorQue(FiltroReservatorioVolumeFaixa.VOLUME_MAIOR_FAIXA, new BigDecimal(
							reservatorioSuperior.replace(',', '.'))));
			filtroReservatorioVolumeFaixa.adicionarParametro(new MenorQue(FiltroReservatorioVolumeFaixa.VOLUME_MENOR_FAIXA, new BigDecimal(
							reservatorioSuperior.replace(',', '.'))));
			ReservatorioVolumeFaixa reservatorioVolumeFaixa = null;
			Collection superiosFaixas = fachada.pesquisar(filtroReservatorioVolumeFaixa, ReservatorioVolumeFaixa.class.getName());
			if(superiosFaixas != null && !superiosFaixas.isEmpty()){

				Iterator superiosFaixasIterator = superiosFaixas.iterator();
				reservatorioVolumeFaixa = (ReservatorioVolumeFaixa) superiosFaixasIterator.next();
				atualizarImovelCaracteristicasActionForm.set("faixaResevatorioSuperior", reservatorioVolumeFaixa.getId().toString());
			}
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

		if(imovel != null && imovel.getFotoFachada() != null && imovel.getFotoFachada().length > 0){
			httpServletRequest.setAttribute("fotoFachada", imovel.getFotoFachada());

		}else{
			httpServletRequest.setAttribute("fotoFachada", null);
		}

		if(imovel != null){
			httpServletRequest.setAttribute("idImovel", imovel.getId());
		}

		if(imovel.getImovelPerfil() != null && imovel.getImovelPerfil().getId().equals(ImovelPerfil.FACTIVEL_EM_ESPERA)
						|| imovel.getImovelPerfil().getId().equals(ImovelPerfil.FACTIVEL_FATURADO)){
			httpServletRequest.setAttribute("factivelEmEspera", "1");
		}else{
			httpServletRequest.setAttribute("factivelEmEspera", "0");
		}
		try{
			// paramentro de fonte de abastecimento
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
		}catch(ControladorException e){
			e.printStackTrace();
		}

		try{

			String alterarEsgoto = Integer.valueOf(ParametroAtendimentoPublico.P_PERMITE_ALTERAR_SITUACAO_LIGACAO_ESGOTO.executar())
							.toString();

			sessao.setAttribute("alterarEsgoto", alterarEsgoto);

		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}

}
