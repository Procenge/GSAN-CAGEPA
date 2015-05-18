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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.OSFiltroHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.RaMotivoReativacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.atendimentopublico.registroatendimento.bean.RAFiltroHelper;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarRegistroAtendimentoAction
				extends GcomAction {

	/**
	 *@author eduardo henrique
	 *@date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno;
		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();
		// Sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm = (FiltrarRegistroAtendimentoActionForm) actionForm;

		if(filtrarRegistroAtendimentoActionForm.getSituacao() != null && filtrarRegistroAtendimentoActionForm.getSituacao().equals("500")){

			// Seta o mapeamento de retorno
			retorno = actionMapping.findForward("manterRegistroAtendimentoReativado");
		}else{

			// Seta o mapeamento de retorno
			retorno = actionMapping.findForward("manterRegistroAtendimento");
		}

		if(filtrarRegistroAtendimentoActionForm.getIndicadorOrdemServicoGerada() != null
						&& !filtrarRegistroAtendimentoActionForm.getIndicadorOrdemServicoGerada().equals(
										String.valueOf(ConstantesSistema.SIM))){
			filtrarRegistroAtendimentoActionForm.setIndicadorGeradaPelaUnidadeAtual("3");

		}

		if(filtrarRegistroAtendimentoActionForm.getIndicadorGeradaPelaUnidadeAtual() != null
						&& !filtrarRegistroAtendimentoActionForm.getIndicadorGeradaPelaUnidadeAtual().equals(
										String.valueOf(ConstantesSistema.TODOS))
						&& (filtrarRegistroAtendimentoActionForm.getUnidadeAtualId() == null || Util
										.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getUnidadeAtualId()))){

			throw new ActionServletException("atencao.fitrar_ra_informar_unidade_atual");

		}

		boolean parametroInformado = false;

		RegistroAtendimento ra = new RegistroAtendimento();
		FiltrarRegistroAtendimentoHelper filtroRA = new FiltrarRegistroAtendimentoHelper();

		// Numero RA
		if(filtrarRegistroAtendimentoActionForm.getNumeroRA() != null && !filtrarRegistroAtendimentoActionForm.getNumeroRA().equals("")){
			ra.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getNumeroRA().trim()));
			parametroInformado = true;

			String[] idRegistrosRAGerarOrdemServico = new String[1];
			idRegistrosRAGerarOrdemServico[0] = filtrarRegistroAtendimentoActionForm.getNumeroRA().trim().toString();
			sessao.setAttribute("idRegistrosRAGerarOrdemServico", idRegistrosRAGerarOrdemServico);

		}else if(httpServletRequest.getParameter("idRA") != null && !httpServletRequest.getParameter("idRA").equals("")){
			String idRA = httpServletRequest.getParameter("idRA");

			ra.setId(Integer.valueOf(idRA));
			parametroInformado = true;

			String[] idRegistrosRAGerarOrdemServico = new String[1];
			idRegistrosRAGerarOrdemServico[0] = idRA;
			sessao.setAttribute("idRegistrosRAGerarOrdemServico", idRegistrosRAGerarOrdemServico);
		}
		// Número Manual
		if(filtrarRegistroAtendimentoActionForm.getNumeroRAManual() != null
						&& !filtrarRegistroAtendimentoActionForm.getNumeroRAManual().equals("")){

			/*
			 * String[] arrayNumeroRAManual =
			 * filtrarRegistroAtendimentoActionForm.getNumeroRAManual().split("-");
			 * Integer numeracao = new Integer(arrayNumeroRAManual[0]);
			 * Integer digitoModulo11 = new Integer(arrayNumeroRAManual[1]);
			 */

			// Caso o dígito verificador do número informado não bata com o dígito calculado com o
			// módulo 11
			/*
			 * if
			 * (!digitoModulo11.equals(Util.obterDigitoVerificadorModulo11(Long.parseLong(numeracao
			 * .toString())))){
			 * throw new ActionServletException("atencao.numeracao_ra_manual_digito_invalido");
			 * }
			 */

			if(filtrarRegistroAtendimentoActionForm.getNumeroRAManual().contains("-")){

				ra.setManual(Util.obterNumeracaoRAManual(filtrarRegistroAtendimentoActionForm.getNumeroRAManual()));
			}else{

				ra.setManual(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getNumeroRAManual().trim()));
			}

			parametroInformado = true;
		}
		// Imovel
		if(filtrarRegistroAtendimentoActionForm.getMatriculaImovel() != null
						&& !filtrarRegistroAtendimentoActionForm.getMatriculaImovel().equals("")){
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getMatriculaImovel()));
			ra.setImovel(imovel);
			parametroInformado = true;
		}

		// Situação
		if(filtrarRegistroAtendimentoActionForm.getSituacao() != null && !filtrarRegistroAtendimentoActionForm.getSituacao().equals("")){
			ra.setCodigoSituacao(Short.valueOf(filtrarRegistroAtendimentoActionForm.getSituacao()));
			if(!filtrarRegistroAtendimentoActionForm.getSituacao().trim().equals("0")){
				parametroInformado = true;
			}

			// RAs Reiterados ("situação" 501)
			if(filtrarRegistroAtendimentoActionForm.getSituacao().equals("501")){
				sessao.setAttribute("isRAReiterado", "OK");
			}else{
				sessao.removeAttribute("isRAReiterado");
			}
		}

		// Indicador de Ordem de Serviço Gerada
		String IndicadorOrdemServicoGerada = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorOrdemServicoGerada())){
			IndicadorOrdemServicoGerada = filtrarRegistroAtendimentoActionForm.getIndicadorOrdemServicoGerada();
			parametroInformado = true;
		}

		// Indicador Gerada pela Unidade Atual
		String IndicadorGeradaPelaUnidadeAtual = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorGeradaPelaUnidadeAtual())){
			IndicadorGeradaPelaUnidadeAtual = filtrarRegistroAtendimentoActionForm.getIndicadorGeradaPelaUnidadeAtual();
			parametroInformado = true;
		}

		// Indicador Processo Adm/Jud
		String indicadorProcessoAdmJud = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorProcessoAdmJud())){
			indicadorProcessoAdmJud = filtrarRegistroAtendimentoActionForm.getIndicadorProcessoAdmJud();
			parametroInformado = true;
		}

		// Indicador Ra Vencidas
		String indicadorRaVencidas = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorRaVencidas())){
			indicadorRaVencidas = filtrarRegistroAtendimentoActionForm.getIndicadorRaVencidas();
			parametroInformado = true;
		}

		// Indicador Ra com Pagamento()
		String indicadorRaPagamento = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorRaPagamento())){
			indicadorRaPagamento = filtrarRegistroAtendimentoActionForm.getIndicadorRaPagamento();
			parametroInformado = true;
		}

		// Indicador Ra Devolucao
		String indicadorRaDevolucao = "";

		if(!Util.isVazioOuBranco(filtrarRegistroAtendimentoActionForm.getIndicadorRaDevolucao())){
			indicadorRaDevolucao = filtrarRegistroAtendimentoActionForm.getIndicadorRaDevolucao();
			parametroInformado = true;
		}

		// Tipo Solicitação
		Collection<Integer> colecaoSolicitacaoTipoSolicitacao = new ArrayList();
		if(filtrarRegistroAtendimentoActionForm.getTipoSolicitacao() != null
						&& filtrarRegistroAtendimentoActionForm.getTipoSolicitacao().length > 0){
			String[] tipoSolicitacao = filtrarRegistroAtendimentoActionForm.getTipoSolicitacao();
			for(int i = 0; i < tipoSolicitacao.length; i++){
				if(Integer.parseInt(tipoSolicitacao[i]) != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecaoSolicitacaoTipoSolicitacao.add(Integer.valueOf(tipoSolicitacao[i]));
					// passar a coleção de especificação por parâmetro
					parametroInformado = true;
				}
			}
		}

		// Tipo Especificação
		Collection<Integer> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		if(colecaoSolicitacaoTipoSolicitacao.size() < 2 && filtrarRegistroAtendimentoActionForm.getEspecificacao() != null
						&& filtrarRegistroAtendimentoActionForm.getEspecificacao().length > 0){
			String[] tipoSolicitacaoEspecificacao = filtrarRegistroAtendimentoActionForm.getEspecificacao();
			for(int i = 0; i < tipoSolicitacaoEspecificacao.length; i++){
				if(Integer.parseInt(tipoSolicitacaoEspecificacao[i]) != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecaoSolicitacaoTipoEspecificacao.add(Integer.valueOf(tipoSolicitacaoEspecificacao[i]));
					// passar a coleção de especificação por parâmetro
					parametroInformado = true;
				}
			}
		}

		// Data de Previsao de Atendimento
		Date dataPrevisaoAtendimentoInicial = null;
		Date dataPrevisaoAtendimentoFinal = null;
		if(filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoInicial() == null
						|| filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoInicial().equals("")){

			if(filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoFinal() != null
							&& !filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoFinal().equals("")){

				dataPrevisaoAtendimentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm
								.getPeriodoPrevistoAtendimentoFinal());
				dataPrevisaoAtendimentoFinal = Util.formatarDataFinal(dataPrevisaoAtendimentoFinal);

				dataPrevisaoAtendimentoInicial = Util.converteStringParaDate("01/01/1900");
				dataPrevisaoAtendimentoInicial = Util.formatarDataInicial(dataPrevisaoAtendimentoInicial);

				// passar as datas de atendimento por parâmetro
				parametroInformado = true;
			}
		}else{
			dataPrevisaoAtendimentoInicial = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm
							.getPeriodoPrevistoAtendimentoInicial());
			dataPrevisaoAtendimentoInicial = Util.formatarDataInicial(dataPrevisaoAtendimentoInicial);

			dataPrevisaoAtendimentoFinal = null;
			if(filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoFinal() == null
							|| filtrarRegistroAtendimentoActionForm.getPeriodoPrevistoAtendimentoFinal().equals("")){
				dataPrevisaoAtendimentoFinal = new Date();
				dataPrevisaoAtendimentoFinal = Util.formatarDataFinal(dataPrevisaoAtendimentoFinal);
			}else{
				dataPrevisaoAtendimentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm
								.getPeriodoPrevistoAtendimentoFinal());
				dataPrevisaoAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataPrevisaoAtendimentoFinal);
			}
			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataPrevisaoAtendimentoInicial, dataPrevisaoAtendimentoFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de atendimento por parâmetro
			parametroInformado = true;

		}

		// Data de Atendimento
		Date dataAtendimentoInicial = null;
		Date dataAtendimentoFinal = null;
		if(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial() == null
						|| filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial().equals("")){

			if(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal() != null
							&& !filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal().equals("")){

				dataAtendimentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util.formatarDataFinal(dataAtendimentoFinal);

				dataAtendimentoInicial = Util.converteStringParaDate("01/01/1900");
				dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);

				// passar as datas de atendimento por parâmetro
				parametroInformado = true;
			}
		}else{
			dataAtendimentoInicial = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial());
			dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);

			dataAtendimentoFinal = null;
			if(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal() == null
							|| filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal().equals("")){
				dataAtendimentoFinal = new Date();
				dataAtendimentoFinal = Util.formatarDataFinal(dataAtendimentoFinal);
			}else{
				dataAtendimentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtendimentoFinal);
			}
			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataAtendimentoInicial, dataAtendimentoFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de atendimento por parâmetro
			parametroInformado = true;
		}

		// Data de Encerramento
		Date dataEncerramentoInicial = null;
		Date dataEncerramentoFinal = null;
		if(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial() == null
						|| filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial().equals("")){
			if(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal() != null
							&& !filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal().equals("")){

				dataEncerramentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal());
				dataEncerramentoFinal = Util.formatarDataFinal(dataEncerramentoFinal);

				dataEncerramentoInicial = Util.converteStringParaDate("01/01/1900");
				dataEncerramentoInicial = Util.formatarDataInicial(dataEncerramentoInicial);

				// passar as datas de atendimento por parâmetro
				parametroInformado = true;
			}
		}else{
			dataEncerramentoInicial = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial());
			dataEncerramentoInicial = Util.formatarDataInicial(dataEncerramentoInicial);

			dataEncerramentoFinal = null;
			if(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal() == null
							|| filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal().equals("")){
				dataEncerramentoFinal = new Date();
				dataEncerramentoFinal = Util.formatarDataInicial(dataEncerramentoFinal);
			}else{
				dataEncerramentoFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal());
				dataEncerramentoFinal = Util.adaptarDataFinalComparacaoBetween(dataEncerramentoFinal);
			}
			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataEncerramentoInicial, dataEncerramentoFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de encerramento por parâmetro
			parametroInformado = true;
		}

		// Data de Trâmite
		Date dataTramiteInicial = null;
		Date dataTramiteFinal = null;
		if(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteInicial() == null
						|| filtrarRegistroAtendimentoActionForm.getPeriodoTramiteInicial().equals("")){
			if(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal() != null
							&& !filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal().equals("")){

				dataTramiteFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal());
				dataTramiteFinal = Util.formatarDataFinal(dataTramiteFinal);

				dataTramiteInicial = Util.converteStringParaDate("01/01/1900");
				dataTramiteInicial = Util.formatarDataInicial(dataTramiteInicial);

				// passar as datas de trâmite por parâmetro
				parametroInformado = true;
			}
		}else{
			dataTramiteInicial = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteInicial());
			dataTramiteInicial = Util.formatarDataInicial(dataTramiteInicial);

			dataTramiteFinal = null;
			if(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal() == null
							|| filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal().equals("")){
				dataTramiteFinal = new Date();
				dataTramiteFinal = Util.formatarDataInicial(dataTramiteFinal);
			}else{
				dataTramiteFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoActionForm.getPeriodoTramiteFinal());
				dataTramiteFinal = Util.adaptarDataFinalComparacaoBetween(dataTramiteFinal);
			}
			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataTramiteInicial, dataTramiteFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de trâmite por parâmetro
			parametroInformado = true;
		}

		// Unidade de Atendimento
		UnidadeOrganizacional unidadeAtendimento = null;
		if(filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId().equals("")){
			unidadeAtendimento = new UnidadeOrganizacional();
			unidadeAtendimento.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId()));
			// passar coleção de unidades por parâmetro
			parametroInformado = true;
		}

		// Registro Atendimento Unidade
		RegistroAtendimentoUnidade registroAtendimentoUnidade = null;
		Usuario usuario = null;
		if(filtrarRegistroAtendimentoActionForm.getLoginUsuario() != null
						&& !filtrarRegistroAtendimentoActionForm.getLoginUsuario().equals("")){

			usuario = new Usuario();
			usuario.setLogin(filtrarRegistroAtendimentoActionForm.getLoginUsuario());

			registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
			registroAtendimentoUnidade.setUsuario(usuario);

			// passar coleção de registro atendimento unidades(usuário) por parâmetro
			parametroInformado = true;
		}

		// Unidade de Atual
		UnidadeOrganizacional unidadeAtual = null;
		if(filtrarRegistroAtendimentoActionForm.getUnidadeAtualId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeAtualId().equals("")){
			unidadeAtual = new UnidadeOrganizacional();
			unidadeAtual.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getUnidadeAtualId()));
			// passar coleção de unidades por parâmetro
			parametroInformado = true;
		}
		// Unidade de Atual
		UnidadeOrganizacional unidadeSuperior = null;
		if(filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId().equals("")){
			unidadeSuperior = new UnidadeOrganizacional();
			unidadeSuperior.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId()));
			// passar coleção de unidades por parâmetro
			parametroInformado = true;
		}
		// Município
		String municipioId = null;
		if(filtrarRegistroAtendimentoActionForm.getMunicipioId() != null
						&& !filtrarRegistroAtendimentoActionForm.getMunicipioId().equals("")){
			municipioId = filtrarRegistroAtendimentoActionForm.getMunicipioId();
			parametroInformado = true;
		}
		// Bairro
		String bairroId = null;
		String bairroCodigo = null;
		if(filtrarRegistroAtendimentoActionForm.getBairroCodigo() != null
						&& !filtrarRegistroAtendimentoActionForm.getBairroCodigo().equals("")){

			// [FS009] Verificar informação do município
			if(filtrarRegistroAtendimentoActionForm.getMunicipioId() == null
							|| filtrarRegistroAtendimentoActionForm.getMunicipioId().equals("")){

				throw new ActionServletException("atencao.filtrar_informar_municipio");
			}

			bairroCodigo = filtrarRegistroAtendimentoActionForm.getBairroCodigo();

			if(filtrarRegistroAtendimentoActionForm.getBairroId() != null && !filtrarRegistroAtendimentoActionForm.getBairroId().equals("")){

				bairroId = filtrarRegistroAtendimentoActionForm.getBairroId();
			}

			parametroInformado = true;
		}
		// Bairro Área
		if(filtrarRegistroAtendimentoActionForm.getAreaBairroId() != null
						&& Integer.valueOf(filtrarRegistroAtendimentoActionForm.getAreaBairroId()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			BairroArea bairroArea = new BairroArea();
			bairroArea.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getAreaBairroId()));
			ra.setBairroArea(bairroArea);
			parametroInformado = true;
		}
		// Logradouro
		String logradouroId = null;
		if(filtrarRegistroAtendimentoActionForm.getLogradouroId() != null
						&& !filtrarRegistroAtendimentoActionForm.getLogradouroId().equals("")){
			logradouroId = filtrarRegistroAtendimentoActionForm.getLogradouroId();
			parametroInformado = true;
		}

		// Nome Solicitante
		String solicitante = null;
		if(filtrarRegistroAtendimentoActionForm.getSolicitante() != null
						&& !filtrarRegistroAtendimentoActionForm.getSolicitante().equals("")){

			solicitante = filtrarRegistroAtendimentoActionForm.getSolicitante();
			parametroInformado = true;
		}

		// Nome Solicitante
		String codigoCliente = null;
		if(filtrarRegistroAtendimentoActionForm.getCodigoCliente() != null
						&& !filtrarRegistroAtendimentoActionForm.getCodigoCliente().equals("")){

			codigoCliente = filtrarRegistroAtendimentoActionForm.getCodigoCliente();
			parametroInformado = true;
		}

		// Cpf Cliente
		String cpf = null;
		if(filtrarRegistroAtendimentoActionForm.getCpf() != null && !filtrarRegistroAtendimentoActionForm.getCpf().equals("")){

			cpf = filtrarRegistroAtendimentoActionForm.getCpf();
			parametroInformado = true;
		}

		// Cnpj Cliente
		String cnpj = null;
		if(filtrarRegistroAtendimentoActionForm.getCnpj() != null && !filtrarRegistroAtendimentoActionForm.getCnpj().equals("")){

			cnpj = filtrarRegistroAtendimentoActionForm.getCnpj();
			parametroInformado = true;
		}

		// Motivo da Reativacao
		if(filtrarRegistroAtendimentoActionForm.getMotivoReativacao() != null
						&& !filtrarRegistroAtendimentoActionForm.getMotivoReativacao().equals("")){
			RaMotivoReativacao raMotivoReativacao = new RaMotivoReativacao();
			raMotivoReativacao.setId(Integer.valueOf(filtrarRegistroAtendimentoActionForm.getMotivoReativacao()));
			ra.setRaMotivoReativacao(raMotivoReativacao);

		}

		// Matricula Atendente
		String matriculaAtendenteId = null;
		if(filtrarRegistroAtendimentoActionForm.getMatriculaAtendente() != null
						&& !filtrarRegistroAtendimentoActionForm.getMatriculaAtendente().equals("")){

			matriculaAtendenteId = filtrarRegistroAtendimentoActionForm.getMatriculaAtendente();
			parametroInformado = true;
		}

		// Filtra Registro Atendimento
		if(parametroInformado){
			Collection<RegistroAtendimento> colecaoRegistroAtendimento = new ArrayList();

			filtroRA.setRegistroAtendimento(ra);
			filtroRA.setUnidadeAtendimento(unidadeAtendimento);
			filtroRA.setUnidadeAtual(unidadeAtual);
			filtroRA.setUnidadeSuperior(unidadeSuperior);
			filtroRA.setDataAtendimentoInicial(dataAtendimentoInicial);
			filtroRA.setDataAtendimentoFinal(dataAtendimentoFinal);
			filtroRA.setDataEncerramentoInicial(dataEncerramentoInicial);
			filtroRA.setDataEncerramentoFinal(dataEncerramentoFinal);
			filtroRA.setDataTramiteInicial(dataTramiteInicial);
			filtroRA.setDataTramiteFinal(dataTramiteFinal);
			filtroRA.setColecaoTipoSolicitacaoEspecificacao(colecaoSolicitacaoTipoEspecificacao);
			filtroRA.setColecaoTipoSolicitacao(colecaoSolicitacaoTipoSolicitacao);
			filtroRA.setMunicipioId(municipioId);
			filtroRA.setBairroId(bairroId);
			filtroRA.setBairroCodigo(bairroCodigo);
			filtroRA.setLogradouroId(logradouroId);
			filtroRA.setSolicitante(solicitante);
			filtroRA.setCpf(cpf);
			filtroRA.setCnpj(cnpj);
			filtroRA.setCodigoCliente(codigoCliente);
			filtroRA.setMatriculaAtendenteId(matriculaAtendenteId);
			filtroRA.setIndicadorOrdemServicoGerada(IndicadorOrdemServicoGerada);
			filtroRA.setIndicadorGeradaPelaUnidadeAtual(IndicadorGeradaPelaUnidadeAtual);
			filtroRA.setIndicadorProcessoAdmJud(indicadorProcessoAdmJud);
			filtroRA.setIndicadorRaVencidas(indicadorRaVencidas);
			filtroRA.setIndicadorRaPagamento(indicadorRaPagamento);
			filtroRA.setIndicadorRaDevolucao(indicadorRaDevolucao);

			if(filtrarRegistroAtendimentoActionForm.getTipoPesquisa() != null){

				if(filtrarRegistroAtendimentoActionForm.getTipoPesquisa().equals("0")){

					filtroRA.setIniciado(true);
				}else if(filtrarRegistroAtendimentoActionForm.getTipoPesquisa().equals("1")){

					filtroRA.setContem(true);
				}
			}

			filtroRA.setDataPrevisaoAtendimentoInicial(dataPrevisaoAtendimentoInicial);
			filtroRA.setDataPrevisaoAtendimentoFinal(dataPrevisaoAtendimentoFinal);

			filtroRA.setRegistroAtendimentoUnidade(registroAtendimentoUnidade);
			filtroRA.setNumeroPagina(ConstantesSistema.NUMERO_NAO_INFORMADO);

			int totalRegistros = ConstantesSistema.NUMERO_NAO_INFORMADO;

			Integer tamanho = null;
			if(httpServletRequest.getParameter("page.offset") == null){
				Collection colecaoRAHelperCompleta = fachada.filtrarRegistroAtendimento(filtroRA);
				tamanho = colecaoRAHelperCompleta.size();

				sessao.setAttribute("colecaoCompleta", colecaoRAHelperCompleta);

			}else{
				tamanho = (Integer) sessao.getAttribute("totalRegistros");
			}

			if(tamanho == null || tamanho == 0){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");

			}else if(tamanho.intValue() == 1){

				retorno = actionMapping.findForward("consultarRegistroAtendimento");

				colecaoRegistroAtendimento = fachada.filtrarRegistroAtendimento(filtroRA);
				Collection colecaoRAHelper = loadColecaoRAHelper(colecaoRegistroAtendimento);

				RAFiltroHelper raFiltroHelper = (RAFiltroHelper) Util.retonarObjetoDeColecao(colecaoRAHelper);
				sessao.setAttribute("numeroOS", raFiltroHelper.getRegistroAtendimento().getId());

				sessao.removeAttribute("colecaoCompleta");
				sessao.removeAttribute("colecaoRAHelper");

			}else{

				totalRegistros = tamanho.intValue();

				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				int numeroPaginasPesquisa = ((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")).intValue();

				filtroRA.setNumeroPagina(numeroPaginasPesquisa);

				colecaoRegistroAtendimento = fachada.filtrarRegistroAtendimento(filtroRA);
				Collection colecaoRAHelper = loadColecaoRAHelper(colecaoRegistroAtendimento);

				sessao.removeAttribute("numeroOS");
				sessao.setAttribute("colecaoRAHelper", colecaoRAHelper);

			}
		}else{

			throw new ActionServletException("atencao.filtrar_informar_um_filtro", "exibirFiltrarRegistroAtendimentoAction.do?menu=sim", "");
		}

		return retorno;
	}

	/**
	 * Carrega coleção de registro atendimento, situação abreviada e unidade atual no
	 * objeto facilitador
	 * 
	 * @author Leonardo Regis
	 * @date 10/08/2006
	 * @param colecaoRegistroAtendimento
	 * @return
	 */
	private Collection loadColecaoRAHelper(Collection<RegistroAtendimento> colecaoRegistroAtendimento){

		Fachada fachada = Fachada.getInstancia();
		Collection colecaoRAHelper = new ArrayList();
		UnidadeOrganizacional unidadeAtual = null;
		ObterDescricaoSituacaoRAHelper situacao = null;
		RAFiltroHelper helper = null;
		String servicoRA = null;

		for(Iterator iter = colecaoRegistroAtendimento.iterator(); iter.hasNext();){
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) iter.next();

			situacao = fachada.obterDescricaoSituacaoRA(registroAtendimento.getId());
			unidadeAtual = fachada.obterUnidadeAtualRA(registroAtendimento.getId());

			helper = new RAFiltroHelper();
			helper.setRegistroAtendimento(registroAtendimento);
			helper.setUnidadeAtual(unidadeAtual);
			helper.setSituacao(situacao.getDescricaoAbreviadaSituacao());

			// Verifica se a RA possui ordem de serviço gerada
			// para exibir registros com cores diferentes em manter registro de atendimento
			Integer possuiOrdemServicoGerada = fachada.verificarOrdemServicoParaRA(registroAtendimento.getId());

			if(!Util.isVazioOuBranco(possuiOrdemServicoGerada)){
				helper.setIndicadorOrdemServicoGerada(ConstantesSistema.SIM.toString());
				// Obtem a colecao de OS vinculada com o RA
				Collection<OrdemServico> colecaoOrdemServico = fachada.obterOSRA(registroAtendimento.getId());
				if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
					// Metodo loadColecaoOSHelper - carrega os atributos a serem utilizados no RA
					helper.setSituacaoOS(loadColecaoOSHelperSituacao(colecaoOrdemServico));
					helper.setServicoOS(loadColecaoOSHelperServico(colecaoOrdemServico));
				}

			}else{
				helper.setIndicadorOrdemServicoGerada(ConstantesSistema.NAO.toString());
			}

			// Endereço da Ocorrencia [UC0422 – Obter Endereço da Ocorrência do RA]
			String enderecoOcorrencia = fachada.obterEnderecoOcorrenciaRA(registroAtendimento.getId());

			helper.setEnderecoOcorrencia(enderecoOcorrencia);
			// Cria um OSFiltroHelper para cada OS associada ao RA

			helper.setQuantidadeGuiasPendentes(registroAtendimento.getQuantidadeGuiasPendentes());

			colecaoRAHelper.add(helper);

		}
		return colecaoRAHelper;
	}

	private String loadColecaoOSHelperSituacao(Collection<OrdemServico> colecaoOrdemServico){

		Fachada fachada = Fachada.getInstancia();

		ObterDescricaoSituacaoOSHelper situacao = null;
		OSFiltroHelper helper = null;
		String situacaoOS = "";

		for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

			OrdemServico ordemServico = (OrdemServico) iter.next();

			situacao = fachada.obterDescricaoSituacaoOS(ordemServico.getId());

			situacaoOS = situacaoOS + situacao.getDescricaoAbreviadaSituacao() + " \r\n";

		}

		return situacaoOS;
	}

	private String loadColecaoOSHelperServico(Collection<OrdemServico> colecaoOrdemServico){

		Fachada fachada = Fachada.getInstancia();

		OSFiltroHelper helper = null;
		String servicoOS = "";

		for(Iterator iter = colecaoOrdemServico.iterator(); iter.hasNext();){

			OrdemServico ordemServico = (OrdemServico) iter.next();

			servicoOS = servicoOS + ordemServico.getServicoTipo().getId().toString() + " \r\n";

		}

		return servicoOS;
	}

}