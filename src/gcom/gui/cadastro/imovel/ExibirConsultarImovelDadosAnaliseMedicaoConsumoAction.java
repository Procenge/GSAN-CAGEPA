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
 * GSANPCG
 * Virgínia Melo
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

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0473] Consultar Imovel 3° Aba - Dados de Ligações, consumo e medição
 * 
 * @author Rafael Santos
 * @since 07/09/2006
 *        Correção de Erros
 * @author Virgínia Melo
 * @date 24/12/2008
 * @author eduardo henrique
 * @date 30/12/2008
 *       Alteração no carregamento dos Historicos de Consumos do Imóvel, para atender às situações
 *       dos
 *       Imóveis Estimados.
 * @author eduardo henrique
 * @date 30/01/2009
 *       Correção na atribuição da Leitura Anterior do Imóvel, dados de agua e poco.
 *       adição de campo de Saldo de Credito de Consumo no Historico de Medicoes.
 */
public class ExibirConsultarImovelDadosAnaliseMedicaoConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("consultarImovelAnaliseMedicaoConsumo");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba dados cadastrais
		String idImovelAnaliseMedicaoConsumo = consultarImovelActionForm.getIdImovelAnaliseMedicaoConsumo();

		// recupera a flag de limpar o form
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelParametro = httpServletRequest.getParameter("idImovelPrincipalAba");
		String idImovelPrincipalAba = null;

		String flagTelaAtualizarHidrometro = (String) httpServletRequest.getParameter("flagTelaAtualizarHidrometro");
		if(flagTelaAtualizarHidrometro != null && flagTelaAtualizarHidrometro != ""){
			sessao.setAttribute("flagTelaAtualizarHidrometro", flagTelaAtualizarHidrometro);
		}else{
			sessao.removeAttribute("flagTelaAtualizarHidrometro");
		}

		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}else if(idImovelParametro != null && !idImovelParametro.trim().equals("")){
			idImovelPrincipalAba = idImovelParametro;
		}

		if(limparForm != null && !limparForm.equals("")){

			// limpar os dados
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			sessao.removeAttribute("idImovelPrincipalAba");
			limparForm(sessao, httpServletRequest, consultarImovelActionForm);

		}else if((idImovelAnaliseMedicaoConsumo != null && !idImovelAnaliseMedicaoConsumo.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelAnaliseMedicaoConsumo != null && !idImovelAnaliseMedicaoConsumo.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){

						consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(idImovelAnaliseMedicaoConsumo);

					}else if(!(idImovelAnaliseMedicaoConsumo.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(idImovelPrincipalAba);
						idImovelAnaliseMedicaoConsumo = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelAnaliseMedicaoConsumo = idImovelPrincipalAba;
			}

			// Obtém a instância da Fachada
			Fachada fachada = Fachada.getInstancia();

			Imovel imovel = null;

			// verifica se o objeto imovel é o mesmo ja pesquisado, se for, não realizar as
			// consultas novamente.
			boolean imovelNovoPesquisado = false;

			if(sessao.getAttribute("imovelDadosAnaliseMedicaoConsumo") != null){

				imovel = (Imovel) sessao.getAttribute("imovelDadosAnaliseMedicaoConsumo");
				if(!(imovel.getId().toString().equals(idImovelAnaliseMedicaoConsumo.trim()))){
					imovel = fachada.consultarImovelAnaliseMedicaoConsumo(Integer.valueOf(idImovelAnaliseMedicaoConsumo.trim()));
					imovelNovoPesquisado = true;
				}

			}else{
				imovel = fachada.consultarImovelAnaliseMedicaoConsumo(Integer.valueOf(idImovelAnaliseMedicaoConsumo.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelDadosAnaliseMedicaoConsumo", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anteriormente ou seja a 1ª
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){

					limparForm(sessao, httpServletRequest, consultarImovelActionForm);

					// seta na tela a inscrição do imovel
					consultarImovelActionForm.setMatriculaImovelAnaliseMedicaoConsumo(fachada.pesquisarInscricaoImovel(Integer
									.valueOf(idImovelAnaliseMedicaoConsumo.trim()), true));

					httpServletRequest.setAttribute("idImovelAnaliseMedicaoConsumoNaoEncontrado", null);

					// pesquisa o endereço
					String enderecoAnaliseMedicaoConsumo = fachada.pesquisarEndereco(Integer.valueOf(idImovelAnaliseMedicaoConsumo.trim()));

					// seta o endereço do imovel
					sessao.setAttribute("enderecoAnaliseMedicaoConsumo", enderecoAnaliseMedicaoConsumo);
					consultarImovelActionForm.setEnderecoAnaliseMedicaoConsumo(enderecoAnaliseMedicaoConsumo);

					// seta a situação de agua
					consultarImovelActionForm.setSituacaoAguaAnaliseMedicaoConsumo(imovel.getLigacaoAguaSituacao().getDescricao());

					// seta a situação de esgoto
					consultarImovelActionForm.setSituacaoEsgotoAnaliseMedicaoConsumo(imovel.getLigacaoEsgotoSituacao().getDescricao());

					// Segmento

					String numeroSegmentoStr = "";

					Short numeroSegmento = imovel.getNumeroSegmento();

					if(numeroSegmento != null){
						numeroSegmentoStr = Short.toString(numeroSegmento);
					}

					consultarImovelActionForm.setNumeroSegmento(numeroSegmentoStr);

					// -------Parte que trata do código quando o usuário tecla enter

					// caso seja o id do municipio
					int consumoMedioHidrometro = 0;

					Object[] parmClienteImovel = null;

					Collection parmsclienteImovelLigacaoAgua = fachada.pesquiarImovelExcecoesApresentaDados(Integer
									.valueOf(idImovelAnaliseMedicaoConsumo), true);

					if(!parmsclienteImovelLigacaoAgua.isEmpty()){

						parmClienteImovel = (Object[]) parmsclienteImovelLigacaoAgua.iterator().next();

						// id faturamento grupo
						if(parmClienteImovel[0] != null){

							consultarImovelActionForm.setGrupoFaturamento(((Integer) parmClienteImovel[0]).toString());
							faturamentoGrupo.setId((Integer) parmClienteImovel[0]);

						}else{
							consultarImovelActionForm.setGrupoFaturamento("");
						}

						// ano mes faturamento grupo
						if(parmClienteImovel[1] != null){
							String anoMes = ((Integer) parmClienteImovel[1]).toString();

							String ano = anoMes.substring(0, 4);
							String mes = anoMes.substring(4, 6);
							consultarImovelActionForm.setMesAnoFaturamentoCorrente(mes + "/" + ano);
							faturamentoGrupo.setAnoMesReferencia((Integer) parmClienteImovel[1]);

						}else{
							consultarImovelActionForm.setMesAnoFaturamentoCorrente("");
						}

						// nome empresa
						if(parmClienteImovel[2] != null){
							consultarImovelActionForm.setEmpresaLeitura((String) parmClienteImovel[2]);
						}else{
							consultarImovelActionForm.setEmpresaLeitura("");
						}

						// dia vencimento faturamento grupo
						if(parmClienteImovel[41] != null){
							consultarImovelActionForm.setDiaVencimento(((Short) parmClienteImovel[41]).toString());
						}else{
							consultarImovelActionForm.setDiaVencimento("");
						}

						// rota
						if(parmClienteImovel[42] != null){
							consultarImovelActionForm.setRota(((Short) parmClienteImovel[42]).toString());
						}else{
							consultarImovelActionForm.setRota("");
						}

						// sequencial rota
						if(parmClienteImovel[43] != null){
							consultarImovelActionForm.setSequencialRota(((Integer) parmClienteImovel[43]).toString());
						}else{
							consultarImovelActionForm.setSequencialRota("");
						}

						// data ligação agua
						if(parmClienteImovel[20] != null){
							consultarImovelActionForm.setDataLigacaoAgua(Util.formatarData((Date) parmClienteImovel[20]));
						}else{
							consultarImovelActionForm.setDataLigacaoAgua("");
						}

						// data corte agua
						if(parmClienteImovel[21] != null){
							consultarImovelActionForm.setDataCorteAgua(Util.formatarData((Date) parmClienteImovel[21]));
						}else{
							consultarImovelActionForm.setDataCorteAgua("");
						}

						// data religação agua
						if(parmClienteImovel[22] != null){
							consultarImovelActionForm.setDataReligacaoAgua(Util.formatarData((Date) parmClienteImovel[22]));
						}else{
							consultarImovelActionForm.setDataReligacaoAgua("");
						}

						// data supressão agua
						if(parmClienteImovel[23] != null){
							consultarImovelActionForm.setDataSupressaoAgua(Util.formatarData((Date) parmClienteImovel[23]));
						}else{
							consultarImovelActionForm.setDataSupressaoAgua("");
						}

						// data restabelecimento agua
						if(parmClienteImovel[35] != null){
							consultarImovelActionForm.setDataRestabelecimentoAgua(Util.formatarData((Date) parmClienteImovel[35]));
						}else{
							consultarImovelActionForm.setDataRestabelecimentoAgua("");
						}

						// descrição ligação agua diametro
						if(parmClienteImovel[24] != null){
							consultarImovelActionForm.setDescricaoLigacaoAguaDiametro((String) parmClienteImovel[24]);
						}else{
							consultarImovelActionForm.setDescricaoLigacaoAguaDiametro("");
						}

						// descrição ligação agua material
						if(parmClienteImovel[25] != null){
							consultarImovelActionForm.setDescricaoLigacaoAguaMaterial((String) parmClienteImovel[25]);
						}else{
							consultarImovelActionForm.setDescricaoLigacaoAguaMaterial("");
						}

						// Perfil de ligação
						if(parmClienteImovel[36] != null){
							consultarImovelActionForm.setDescricaoligacaoAguaPerfil((String) parmClienteImovel[36]);
						}else{
							consultarImovelActionForm.setDescricaoligacaoAguaPerfil("");
						}

						// numero consumo mínimo agua
						if(parmClienteImovel[26] != null){
							consultarImovelActionForm.setNumeroConsumominimoAgua(((Integer) parmClienteImovel[26]).toString());
						}else{
							consultarImovelActionForm.setNumeroConsumominimoAgua("");
						}

						// Matrícula Religação
						String matriculaReligacao = "";

						if(parmClienteImovel[45] != null){
							Integer idFuncionarioReligacaoAgua = (Integer) parmClienteImovel[45];
							matriculaReligacao = Integer.toString(idFuncionarioReligacaoAgua);
						}

						consultarImovelActionForm.setMatriculaFuncionarioReligacao(matriculaReligacao);

						// Nome do Funcionario Hint Religação
						String nomeFuncionarioHintReligacao = "";

						if(parmClienteImovel[46] != null){
							nomeFuncionarioHintReligacao = (String) parmClienteImovel[46];
						}

						consultarImovelActionForm.setNomeFuncionarioHintReligacao(nomeFuncionarioHintReligacao);

						// DADOS DO HIDROMETRO DA LIGACAO DE AGUA

						// numero do hidrometro
						if(parmClienteImovel[9] != null){
							consultarImovelActionForm.setNumeroHidrometro(((String) parmClienteImovel[9]).toString());
						}else{
							consultarImovelActionForm.setNumeroHidrometro("");
						}

						// data de instalação de hidrometro
						if(parmClienteImovel[10] != null){
							consultarImovelActionForm.setInstalacaoHidrometro(Util.formatarData((Date) parmClienteImovel[10]));
						}else{
							consultarImovelActionForm.setInstalacaoHidrometro("");
						}

						// descrição hidrometro capacidade
						if(parmClienteImovel[11] != null){
							consultarImovelActionForm.setCapacidadeHidrometro((String) parmClienteImovel[11]);
						}else{
							consultarImovelActionForm.setCapacidadeHidrometro("");
						}

						// descrição hidrometro tipo
						if(parmClienteImovel[12] != null){
							consultarImovelActionForm.setTipoHidrometro((String) parmClienteImovel[12]);
						}else{
							consultarImovelActionForm.setTipoHidrometro("");
						}

						// descrição hidrometro marca
						if(parmClienteImovel[13] != null){
							consultarImovelActionForm.setMarcaHidrometro((String) parmClienteImovel[13]);
						}else{
							consultarImovelActionForm.setMarcaHidrometro("");
						}

						// descrição hidrometro local instalação
						if(parmClienteImovel[14] != null){
							consultarImovelActionForm.setLocalInstalacaoHidrometro((String) parmClienteImovel[14]);
						}else{
							consultarImovelActionForm.setLocalInstalacaoHidrometro("");
						}

						// descrição hidrometro diametro
						if(parmClienteImovel[15] != null){
							consultarImovelActionForm.setDiametroHidrometro((String) parmClienteImovel[15]);
						}else{
							consultarImovelActionForm.setDiametroHidrometro("");
						}

						// descrição hidrometro proteção
						if(parmClienteImovel[16] != null){
							consultarImovelActionForm.setProtecaoHidrometro((String) parmClienteImovel[16]);
						}else{
							consultarImovelActionForm.setProtecaoHidrometro("");
						}

						// indicador cavalete do hidrometro instalação histórico
						if(parmClienteImovel[17] != null){
							Short icCavalete = (Short) parmClienteImovel[17];
							if(icCavalete != null && icCavalete == 1){
								consultarImovelActionForm.setIndicadorCavalete("Sim");
							}else{
								consultarImovelActionForm.setIndicadorCavalete("Não");
							}
						}else{
							consultarImovelActionForm.setIndicadorCavalete("");
						}

						// ano fabricação do hidrometro
						if(parmClienteImovel[18] != null){
							consultarImovelActionForm.setAnoFabricacao(((Short) parmClienteImovel[18]).toString());
						}else{
							consultarImovelActionForm.setAnoFabricacao("");
						}

						// id do hidrometro
						if(parmClienteImovel[44] != null){
							consultarImovelActionForm.setIdHidrometro(((Integer) parmClienteImovel[44]).toString());
						}else{
							consultarImovelActionForm.setIdHidrometro("");
						}

						// número de leitura de instalação do hidrometro
						if(parmClienteImovel[47] != null){
							consultarImovelActionForm.setNumeroLeituraInstalacao(((Integer) parmClienteImovel[47]).toString());
						}else{
							consultarImovelActionForm.setNumeroLeituraInstalacao("");
						}
					}

					Collection parmsclienteImovelNaoLigacaoAgua = fachada.pesquiarImovelExcecoesApresentaDados(Integer
									.valueOf(idImovelAnaliseMedicaoConsumo), false);

					if(!parmsclienteImovelNaoLigacaoAgua.isEmpty()){
						Object[] parmsclienteImovel = (Object[]) parmsclienteImovelNaoLigacaoAgua.iterator().next();

						// DADOS DA LIGACAO DE ESGOTO

						// data ligação esgoto
						if(parmsclienteImovel[27] != null){
							consultarImovelActionForm.setDataLigacaoEsgoto(Util.formatarData((Date) parmsclienteImovel[27]));
						}else{
							consultarImovelActionForm.setDataLigacaoEsgoto("");
						}

						// descrição ligação esgoto diametro
						if(parmsclienteImovel[28] != null){
							consultarImovelActionForm.setDescricaoLigacaoEsgotoDiametro((String) parmsclienteImovel[28]);
						}else{
							consultarImovelActionForm.setDescricaoLigacaoEsgotoDiametro("");
						}

						// descrição ligação esgoto material
						if(parmsclienteImovel[29] != null){
							consultarImovelActionForm.setDescricaoLigacaoEsgotoMaterial((String) parmsclienteImovel[29]);
						}else{
							consultarImovelActionForm.setDescricaoLigacaoEsgotoMaterial("");
						}

						// descrição ligação esgoto perfil
						if(parmsclienteImovel[30] != null){
							consultarImovelActionForm.setDescricaoligacaoEsgotoPerfil((String) parmsclienteImovel[30]);
						}else{
							consultarImovelActionForm.setDescricaoligacaoEsgotoPerfil("");
						}

						// numero consumo mínimo esgoto
						if(parmsclienteImovel[31] != null){
							consultarImovelActionForm.setNumeroConsumominimoEsgoto(((Integer) parmsclienteImovel[31]).toString());
						}else{
							consultarImovelActionForm.setNumeroConsumominimoEsgoto("");
						}

						// percentual ligação esgoto
						if(parmsclienteImovel[32] != null){
							consultarImovelActionForm.setPercentualEsgoto((Util.formatarMoedaReal((BigDecimal) parmsclienteImovel[32])));
						}else{
							consultarImovelActionForm.setPercentualEsgoto("");
						}

						// percentual coleta ligação esgoto
						if(parmsclienteImovel[33] != null){
							consultarImovelActionForm.setPercentualAguaConsumidaColetada((Util
											.formatarMoedaReal((BigDecimal) parmsclienteImovel[33])));
						}else{
							consultarImovelActionForm.setPercentualAguaConsumidaColetada("");
						}

						if(parmsclienteImovel[48] != null){
							consultarImovelActionForm.setDescricaoRamalLocalInstalacaoEsgoto(((String) parmsclienteImovel[48]));
						}else{
							consultarImovelActionForm.setDescricaoRamalLocalInstalacaoEsgoto("");
						}

						// descrição tipo poço
						if(parmsclienteImovel[34] != null){
							consultarImovelActionForm.setDescricaoPocoTipo((String) parmsclienteImovel[34]);
						}else{
							consultarImovelActionForm.setDescricaoPocoTipo("");
						}

						// descrição tipo poço
						if(parmsclienteImovel[37] != null){
							consultarImovelActionForm.setIdLigacaoEsgoto(((Integer) parmsclienteImovel[37]).toString());
						}else{
							consultarImovelActionForm.setIdLigacaoEsgoto("");
						}

						// DADOS DO HIDROMETRO DO POÇO

						// numero do hidrometro
						if(parmsclienteImovel[9] != null){
							consultarImovelActionForm.setNumeroHidrometroPoco(((String) parmsclienteImovel[9]).toString());
						}else{
							consultarImovelActionForm.setNumeroHidrometroPoco("");
						}

						// data de instalação de hidrometro
						if(parmsclienteImovel[10] != null){
							consultarImovelActionForm.setInstalacaoHidrometroPoco(Util.formatarData((Date) parmsclienteImovel[10]));
						}else{
							consultarImovelActionForm.setInstalacaoHidrometroPoco("");
						}

						// descrição hidrometro capacidade
						if(parmsclienteImovel[11] != null){
							consultarImovelActionForm.setCapacidadeHidrometroPoco((String) parmsclienteImovel[11]);
						}else{
							consultarImovelActionForm.setCapacidadeHidrometroPoco("");
						}

						// descrição hidrometro tipo
						if(parmsclienteImovel[12] != null){
							consultarImovelActionForm.setTipoHidrometroPoco((String) parmsclienteImovel[12]);
						}else{
							consultarImovelActionForm.setTipoHidrometroPoco("");
						}

						// descrição hidrometro marca
						if(parmsclienteImovel[13] != null){
							consultarImovelActionForm.setMarcaHidrometroPoco((String) parmsclienteImovel[13]);
						}else{
							consultarImovelActionForm.setMarcaHidrometroPoco("");
						}

						// descrição hidrometro local instalação
						if(parmsclienteImovel[14] != null){
							consultarImovelActionForm.setLocalInstalacaoHidrometroPoco((String) parmsclienteImovel[14]);
						}else{
							consultarImovelActionForm.setLocalInstalacaoHidrometroPoco("");
						}

						// descrição hidrometro diametro
						if(parmsclienteImovel[15] != null){
							consultarImovelActionForm.setDiametroHidrometroPoco((String) parmsclienteImovel[15]);
						}else{
							consultarImovelActionForm.setDiametroHidrometroPoco("");
						}

						// descrição hidrometro proteção
						if(parmsclienteImovel[16] != null){
							consultarImovelActionForm.setProtecaoHidrometroPoco((String) parmsclienteImovel[16]);
						}else{
							consultarImovelActionForm.setProtecaoHidrometroPoco("");
						}

						// indicador cavalete do hidrometro instalação histórico
						if(parmsclienteImovel[17] != null){
							Short icCavalete = (Short) parmsclienteImovel[17];
							if(icCavalete != null && icCavalete == 1){
								consultarImovelActionForm.setIndicadorCavaletePoco("Sim");
							}else{
								consultarImovelActionForm.setIndicadorCavaletePoco("Não");
							}
						}else{
							consultarImovelActionForm.setIndicadorCavaletePoco("");
						}

						// ano fabricação do hidrometro
						if(parmsclienteImovel[18] != null){
							consultarImovelActionForm.setAnoFabricacaoPoco(((Short) parmsclienteImovel[18]).toString());
						}else{
							consultarImovelActionForm.setAnoFabricacaoPoco("");
						}

						// número de leitura de instalação do hidrometro
						if(parmsclienteImovel[47] != null){
							consultarImovelActionForm.setNumeroLeituraInstalacaoPoco(((Integer) parmsclienteImovel[47]).toString());
						}else{
							consultarImovelActionForm.setNumeroLeituraInstalacaoPoco("");
						}

					}

					Object[] parmMedicaoHistorico = null;
					// ---- Carrega objetos ligados a cliente

					Collection parmsMedicaoHistorico = new ArrayList();

					Collection parmsMedicaoHistoricoLigacaoAgua = fachada
									.pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(faturamentoGrupo
													.getAnoMesReferencia(), Integer.valueOf(idImovelAnaliseMedicaoConsumo), true);

					if(parmsMedicaoHistoricoLigacaoAgua == null || parmsMedicaoHistoricoLigacaoAgua.isEmpty()){

						SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

						parmsMedicaoHistoricoLigacaoAgua = fachada.pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(
										sistemaParametro.getAnoMesFaturamento(), Integer.valueOf(idImovelAnaliseMedicaoConsumo), true);

					}

					parmsMedicaoHistorico.addAll(parmsMedicaoHistoricoLigacaoAgua);

					if(!parmsMedicaoHistorico.isEmpty()){

						parmMedicaoHistorico = (Object[]) parmsMedicaoHistorico.iterator().next();

						// DADOS DA MEDICAO DO MES DA LIGACAO DE AGUA

						// descrição tipo medicao
						if(parmMedicaoHistorico[0] != null){
							consultarImovelActionForm.setTipoMedicao((String) parmMedicaoHistorico[0]);
						}else{
							consultarImovelActionForm.setTipoMedicao("");
						}

						MedicaoTipo medicaoTipo = new MedicaoTipo();

						// id tipo medicao
						if(parmMedicaoHistorico[1] != null){
							consultarImovelActionForm.setIdTipoMedicao(((Integer) parmMedicaoHistorico[1]).toString());
							medicaoTipo.setId((Integer) parmMedicaoHistorico[1]);
						}else{
							consultarImovelActionForm.setIdTipoMedicao("");
						}

						// data leitura anterior
						Date dataLeituraAnterior = null;
						if(parmMedicaoHistorico[2] != null){
							dataLeituraAnterior = (Date) parmMedicaoHistorico[2];
						}

						// data leitura atual faturada
						Date dtLeituraAtualFaturada = null;
						if(parmMedicaoHistorico[3] != null){
							dtLeituraAtualFaturada = (Date) parmMedicaoHistorico[3];
						}

						// --- fim variavel
						int diasConsumo = 0;
						if(dataLeituraAnterior != null && dtLeituraAtualFaturada != null){
							diasConsumo = gcom.util.Util.obterQuantidadeDiasEntreDuasDatas(dataLeituraAnterior, dtLeituraAtualFaturada);
						}

						consultarImovelActionForm.setDiasConsumo("" + diasConsumo);

						if(dataLeituraAnterior != null){
							consultarImovelActionForm.setDtLeituraAnterior(Util.formatarData(dataLeituraAnterior));
						}else{
							consultarImovelActionForm.setDtLeituraAnterior("");
						}

						// leitura anterior faturamento
						if(parmMedicaoHistorico[5] != null){
							consultarImovelActionForm.setLeituraAnterior(((Integer) parmMedicaoHistorico[5]).toString());
						}else{
							consultarImovelActionForm.setLeituraAnterior("");
						}

						// data leitura atual informada
						if(parmMedicaoHistorico[6] != null){
							consultarImovelActionForm.setDtLeituraInformada((Util.formatarData((Date) parmMedicaoHistorico[6])));
						}else{
							consultarImovelActionForm.setDtLeituraInformada("");
						}

						// leitura atual informada
						if(parmMedicaoHistorico[7] != null){
							consultarImovelActionForm.setLeituraAtualInformada(((Integer) parmMedicaoHistorico[7]).toString());
						}else{
							consultarImovelActionForm.setLeituraAtualInformada("");
						}

						// data leitura faturada
						if(dtLeituraAtualFaturada != null){
							consultarImovelActionForm.setDtLeituraFaturada(Util.formatarData(dtLeituraAtualFaturada));
						}else{
							consultarImovelActionForm.setDtLeituraFaturada("");
						}

						// descrição leitura situação atual
						if(parmMedicaoHistorico[8] != null){
							consultarImovelActionForm.setSituacaoLeituraAtual(((String) parmMedicaoHistorico[8]).toString());
						}else{
							consultarImovelActionForm.setSituacaoLeituraAtual("");
						}

						// data leitura atual faturamento
						if(parmMedicaoHistorico[9] != null){
							consultarImovelActionForm.setDtLeituraFaturada((Util.formatarData((Date) parmMedicaoHistorico[9])));
						}else{
							consultarImovelActionForm.setDtLeituraFaturada("");
						}

						// leitura atual faturamento
						if(parmMedicaoHistorico[10] != null){
							consultarImovelActionForm.setLeituraAtualFaturada(((Integer) parmMedicaoHistorico[10]).toString());
						}else{
							consultarImovelActionForm.setLeituraAtualFaturada("");
						}

						// id funcionário
						if(parmMedicaoHistorico[11] != null){
							consultarImovelActionForm.setCodigoFuncionario(((Integer) parmMedicaoHistorico[11]).toString());
						}else{
							consultarImovelActionForm.setCodigoFuncionario("");
						}

						// nome do Funcionário do Hint
						if(parmMedicaoHistorico[23] != null){
							consultarImovelActionForm.setNomeFuncionarioHint((String) parmMedicaoHistorico[23]);
						}else{
							consultarImovelActionForm.setNomeFuncionarioHint("");
						}

						// descrição leitura anormalidade informada
						if(parmMedicaoHistorico[12] != null){
							consultarImovelActionForm.setAnormalidadeLeituraInformada((String) parmMedicaoHistorico[12]);
						}else{
							consultarImovelActionForm.setAnormalidadeLeituraInformada("");
						}

						// descrição leitura anormalidade faturamento
						if(parmMedicaoHistorico[13] != null){
							consultarImovelActionForm.setAnormalidadeLeituraFaturada((String) parmMedicaoHistorico[13]);
						}else{
							consultarImovelActionForm.setAnormalidadeLeituraFaturada("");
						}

						// numero consumo mes
						if(parmMedicaoHistorico[14] != null){
							consultarImovelActionForm.setConsumoMedido(((Integer) parmMedicaoHistorico[14]).toString());
						}else{
							consultarImovelActionForm.setConsumoMedido("");
						}

						// DADOS DO CONSUMO DO MES DA LIAGACAO DE AGUA

						int consumoFaturado = 0;
						// numero consumo fatura mes
						if(parmMedicaoHistorico[15] != null){
							consumoFaturado = (Integer) parmMedicaoHistorico[15];
							consultarImovelActionForm.setConsumoFaturado(((Integer) parmMedicaoHistorico[15]).toString());
						}else{
							consultarImovelActionForm.setConsumoFaturado("");
						}

						// numero consumo rateio
						if(parmMedicaoHistorico[16] != null){
							consultarImovelActionForm.setConsumoRateio(((Integer) parmMedicaoHistorico[16]).toString());
						}else{
							consultarImovelActionForm.setConsumoRateio("");
						}
						// descrição abreviada consumo anormalidade
						if(parmMedicaoHistorico[17] != null){
							consultarImovelActionForm.setAnormalidadeConsumo((String) parmMedicaoHistorico[17]);
						}else{
							consultarImovelActionForm.setAnormalidadeConsumo("");
						}
						// descrição abreviada consumo tipo
						if(parmMedicaoHistorico[18] != null){
							consultarImovelActionForm.setConsumoTipo((String) parmMedicaoHistorico[18]);
						}else{
							consultarImovelActionForm.setConsumoTipo("");
						}

						// consumo médio do hidrometro
						if(parmMedicaoHistorico[19] != null){
							consultarImovelActionForm.setConsumoMedioHidrometro(((Integer) parmMedicaoHistorico[19]).toString());
							consumoMedioHidrometro = (Integer) parmMedicaoHistorico[19];
						}else{
							consultarImovelActionForm.setConsumoMedioHidrometro("");
						}
						// consumo medio do imovel
						if(parmMedicaoHistorico[20] != null){
							consultarImovelActionForm.setConsumoMedioImovel(((Integer) parmMedicaoHistorico[20]).toString());
						}else{
							consultarImovelActionForm.setConsumoMedioImovel("");
						}

						// consumo mínimo creditado
						if(parmMedicaoHistorico[21] != null){
							consultarImovelActionForm.setConsumoMinimoCreditadoMes(((Integer) parmMedicaoHistorico[21]).toString());
						}else{
							consultarImovelActionForm.setConsumoMinimoCreditadoMes("");
						}

						if(consumoFaturado != 0 && consumoMedioHidrometro != 0){
							int operacaoSubMult = (consumoFaturado - consumoMedioHidrometro) * 100;
							BigDecimal percentual = new BigDecimal(operacaoSubMult).divide(new BigDecimal(consumoMedioHidrometro), 2,
											BigDecimal.ROUND_HALF_UP);
							String valorPercentual = "" + percentual;
							consultarImovelActionForm.setPercentualVariacao("" + valorPercentual.replace(".", ",") + "%");

						}else{
							consultarImovelActionForm.setPercentualVariacao("");
						}

						// Crédito de Consumos Anteriores
						if(parmMedicaoHistorico.length >= 23 && parmMedicaoHistorico[22] != null){
							consultarImovelActionForm.setConsumoCreditoAnterior(((Integer) parmMedicaoHistorico[22]).toString());
						}else{
							consultarImovelActionForm.setConsumoCreditoAnterior("");
						}
					}

					Collection<MedicaoHistorico> medicoesHistorico = new ArrayList();
					Collection<ImovelMicromedicao> imoveisMicromedicao = new ArrayList();

					Collection medicoesHistoricoLigacaoAgua = fachada.carregarDadosMedicao(Integer.valueOf(idImovelAnaliseMedicaoConsumo),
									true);

					if(medicoesHistoricoLigacaoAgua != null){
						medicoesHistorico.addAll(medicoesHistoricoLigacaoAgua);
					}

					Collection imoveisMicromedicaoCarregamentoLigacaoAgua = fachada.carregarDadosConsumo(Integer
									.valueOf(idImovelAnaliseMedicaoConsumo), LigacaoTipo.LIGACAO_AGUA);
					if(imoveisMicromedicaoCarregamentoLigacaoAgua != null){
						imoveisMicromedicao.addAll(imoveisMicromedicaoCarregamentoLigacaoAgua);
					}

					// Organizar a coleção de Conta
					if(medicoesHistorico != null && !medicoesHistorico.isEmpty()){

						Collections.sort((List) medicoesHistorico, new Comparator() {

							public int compare(Object a, Object b){

								int retorno = 0;
								Integer anoMesReferencia1 = ((MedicaoHistorico) a).getAnoMesReferencia();
								Integer anoMesReferencia2 = ((MedicaoHistorico) b).getAnoMesReferencia();

								if(anoMesReferencia1.compareTo(anoMesReferencia2) == 1){
									retorno = -1;
								}else if(anoMesReferencia1.compareTo(anoMesReferencia2) == -1){
									retorno = 1;
								}

								return retorno;

							}
						});
					}

					// medicao da agua
					sessao.setAttribute("medicoesHistoricos", medicoesHistorico);

					if(imoveisMicromedicao != null && !imoveisMicromedicao.isEmpty()){

						Collections.sort((List) imoveisMicromedicao, new Comparator() {

							public int compare(Object a, Object b){

								int retorno = 0;
								Integer anoMesReferencia1 = ((ImovelMicromedicao) a).getConsumoHistorico().getReferenciaFaturamento();
								Integer anoMesReferencia2 = ((ImovelMicromedicao) b).getConsumoHistorico().getReferenciaFaturamento();

								if(anoMesReferencia1.compareTo(anoMesReferencia2) == 1){
									retorno = -1;
								}else if(anoMesReferencia1.compareTo(anoMesReferencia2) == -1){
									retorno = 1;
								}

								return retorno;

							}
						});
					}

					// consumo da agua
					sessao.setAttribute("imoveisMicromedicao", imoveisMicromedicao);

					// ESGOTO E POÇO
					Collection parmsMedicaoHistoricoLigacaoEsgoto = fachada
									.pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(faturamentoGrupo
													.getAnoMesReferencia(), Integer.valueOf(idImovelAnaliseMedicaoConsumo), false);

					if(!parmsMedicaoHistoricoLigacaoEsgoto.isEmpty()){

						Object[] parmMedicaoHistoricoLigacaoEsgoto = (Object[]) parmsMedicaoHistoricoLigacaoEsgoto.iterator().next();

						// DADOS DA MEDICAO DO MES DO POCO

						// descrição tipo medicao
						if(parmMedicaoHistoricoLigacaoEsgoto[0] != null){
							consultarImovelActionForm.setTipoMedicaoPoco((String) parmMedicaoHistoricoLigacaoEsgoto[0]);
						}else{
							consultarImovelActionForm.setTipoMedicaoPoco("");
						}

						// data leitura anterior
						Date dataLeituraAnterior = null;
						if(parmMedicaoHistoricoLigacaoEsgoto[2] != null){
							dataLeituraAnterior = (Date) parmMedicaoHistoricoLigacaoEsgoto[2];
						}
						// data leitura atual faturada
						Date dtLeituraAtualFaturada = null;
						if(parmMedicaoHistoricoLigacaoEsgoto[3] != null){
							dtLeituraAtualFaturada = (Date) parmMedicaoHistoricoLigacaoEsgoto[3];
						}

						// --- fim variavel
						int diasConsumo = 0;
						if(dataLeituraAnterior != null && dtLeituraAtualFaturada != null){
							diasConsumo = gcom.util.Util.obterQuantidadeDiasEntreDuasDatas(dataLeituraAnterior, dtLeituraAtualFaturada);
						}

						consultarImovelActionForm.setDiasConsumo("" + diasConsumo);

						if(dataLeituraAnterior != null){
							consultarImovelActionForm.setDtLeituraAnteriorPoco(Util.formatarData(dataLeituraAnterior));
						}else{
							consultarImovelActionForm.setDtLeituraAnteriorPoco("");
						}

						// leitura anterior faturamento
						if(parmMedicaoHistoricoLigacaoEsgoto[5] != null){
							consultarImovelActionForm.setLeituraAnteriorPoco(((Integer) parmMedicaoHistoricoLigacaoEsgoto[5]).toString());
						}else{
							consultarImovelActionForm.setLeituraAnteriorPoco("");
						}

						// data leitura atual informada
						if(parmMedicaoHistoricoLigacaoEsgoto[6] != null){
							consultarImovelActionForm.setDtLeituraInformadaPoco((Util
											.formatarData((Date) parmMedicaoHistoricoLigacaoEsgoto[6])));
						}else{
							consultarImovelActionForm.setDtLeituraInformadaPoco("");
						}

						// leitura atual informada
						if(parmMedicaoHistoricoLigacaoEsgoto[7] != null){
							consultarImovelActionForm.setLeituraAtualInformadaPoco(((Integer) parmMedicaoHistoricoLigacaoEsgoto[7])
											.toString());
						}else{
							consultarImovelActionForm.setLeituraAtualInformadaPoco("");
						}

						// data leitura faturada
						if(dtLeituraAtualFaturada != null){
							consultarImovelActionForm.setDtLeituraFaturadaPoco(Util.formatarData(dtLeituraAtualFaturada));
						}else{
							consultarImovelActionForm.setDtLeituraFaturadaPoco("");
						}

						// descrição leitura situação atual
						if(parmMedicaoHistoricoLigacaoEsgoto[8] != null){
							consultarImovelActionForm.setSituacaoLeituraAtualPoco(((String) parmMedicaoHistoricoLigacaoEsgoto[8])
											.toString());
						}else{
							consultarImovelActionForm.setSituacaoLeituraAtualPoco("");
						}

						// data leitura atual faturamento
						if(parmMedicaoHistoricoLigacaoEsgoto[9] != null){
							consultarImovelActionForm.setDtLeituraFaturadaPoco((Util
											.formatarData((Date) parmMedicaoHistoricoLigacaoEsgoto[9])));
						}else{
							consultarImovelActionForm.setDtLeituraFaturadaPoco("");
						}

						// leitura atual faturamento
						if(parmMedicaoHistoricoLigacaoEsgoto[10] != null){
							consultarImovelActionForm.setLeituraAtualFaturadaPoco(((Integer) parmMedicaoHistoricoLigacaoEsgoto[10])
											.toString());
						}else{
							consultarImovelActionForm.setLeituraAtualFaturadaPoco("");
						}

						// id funcionário
						if(parmMedicaoHistoricoLigacaoEsgoto[11] != null){
							consultarImovelActionForm
											.setCodigoFuncionarioPoco(((Integer) parmMedicaoHistoricoLigacaoEsgoto[11]).toString());
						}else{
							consultarImovelActionForm.setCodigoFuncionarioPoco("");
						}

						// nome do funcionario do hint Poço
						if(parmMedicaoHistoricoLigacaoEsgoto[23] != null){
							consultarImovelActionForm.setNomeFuncionarioHintPoco((String) parmMedicaoHistoricoLigacaoEsgoto[23]);
						}else{
							consultarImovelActionForm.setNomeFuncionarioHintPoco("");
						}

						// descrição leitura anormalidade informada
						if(parmMedicaoHistoricoLigacaoEsgoto[12] != null){
							consultarImovelActionForm.setAnormalidadeLeituraInformadaPoco((String) parmMedicaoHistoricoLigacaoEsgoto[12]);
						}else{
							consultarImovelActionForm.setAnormalidadeLeituraInformadaPoco("");
						}

						// descrição leitura anormalidade faturamento
						if(parmMedicaoHistoricoLigacaoEsgoto[13] != null){
							consultarImovelActionForm.setAnormalidadeLeituraFaturadaPoco((String) parmMedicaoHistoricoLigacaoEsgoto[13]);
						}else{
							consultarImovelActionForm.setAnormalidadeLeituraFaturadaPoco("");
						}

						// numero consumo mes
						if(parmMedicaoHistoricoLigacaoEsgoto[14] != null){
							consultarImovelActionForm.setConsumoMedidoEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[14]).toString());
						}else{
							consultarImovelActionForm.setConsumoMedidoEsgoto("");
						}

						// DADOS DO VOLUME DO MES DA LIGACAO DE ESGOTO

						int consumoFaturado = 0;

						// numero consumo fatura mes
						if(parmMedicaoHistoricoLigacaoEsgoto[15] != null){
							consumoFaturado = (Integer) parmMedicaoHistoricoLigacaoEsgoto[15];
							consultarImovelActionForm
											.setConsumoFaturadoEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[15]).toString());
						}else{
							consultarImovelActionForm.setConsumoFaturadoEsgoto("");
						}

						// numero consumo rateio
						if(parmMedicaoHistoricoLigacaoEsgoto[16] != null){
							consultarImovelActionForm.setConsumoRateioEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[16]).toString());
						}else{
							consultarImovelActionForm.setConsumoRateioEsgoto("");
						}

						// descrição abreviada consumo anormalidade
						if(parmMedicaoHistoricoLigacaoEsgoto[17] != null){
							consultarImovelActionForm.setAnormalidadeConsumoEsgoto((String) parmMedicaoHistoricoLigacaoEsgoto[17]);
						}else{
							consultarImovelActionForm.setAnormalidadeConsumoEsgoto("");
						}

						// descrição abreviada consumo tipo
						if(parmMedicaoHistoricoLigacaoEsgoto[18] != null){
							consultarImovelActionForm.setConsumoTipoEsgoto((String) parmMedicaoHistoricoLigacaoEsgoto[18]);
						}else{
							consultarImovelActionForm.setConsumoTipoEsgoto("");
						}

						// consumo médio do hidrometro
						if(parmMedicaoHistoricoLigacaoEsgoto[19] != null){
							consultarImovelActionForm.setConsumoMedioHidrometroPoco(((Integer) parmMedicaoHistoricoLigacaoEsgoto[19])
											.toString());
							consumoMedioHidrometro = (Integer) parmMedicaoHistoricoLigacaoEsgoto[19];
						}else{
							consultarImovelActionForm.setConsumoMedioHidrometroPoco("");
						}

						// consumo medio do imovel
						if(parmMedicaoHistoricoLigacaoEsgoto[20] != null){
							consultarImovelActionForm.setConsumoMedioImovelEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[20])
											.toString());
						}else{
							consultarImovelActionForm.setConsumoMedioImovelEsgoto("");
						}

						// consumo mínimo Creditado de Esgoto
						if(parmMedicaoHistoricoLigacaoEsgoto[21] != null){
							consultarImovelActionForm.setConsumoMinimoCreditadoEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[21])
											.toString());
						}else{
							consultarImovelActionForm.setConsumoMinimoCreditadoEsgoto("");
						}

						if(parmMedicaoHistoricoLigacaoEsgoto.length >= 23 && parmMedicaoHistoricoLigacaoEsgoto[22] != null){
							consultarImovelActionForm.setConsumoCreditoAnteriorEsgoto(((Integer) parmMedicaoHistoricoLigacaoEsgoto[22])
											.toString());
						}else{
							consultarImovelActionForm.setConsumoCreditoAnteriorEsgoto("");
						}

						if(consumoFaturado != 0 && consumoMedioHidrometro != 0){
							int operacaoSubMult = (consumoFaturado - consumoMedioHidrometro) * 100;
							BigDecimal percentual = new BigDecimal(operacaoSubMult).divide(new BigDecimal(consumoMedioHidrometro), 2,
											BigDecimal.ROUND_HALF_UP);
							String valorPercentual = "" + percentual;
							consultarImovelActionForm.setPercentualVariacaoEsgoto("" + valorPercentual.replace(".", ",") + "%");

						}else{
							consultarImovelActionForm.setPercentualVariacaoEsgoto("");
						}
					}

					Collection<ImovelMicromedicao> imoveisMicromedicaoEsgoto = new ArrayList();

					Collection medicoesHistoricoPoco = fachada.carregarDadosMedicao(Integer.valueOf(idImovelAnaliseMedicaoConsumo), false);

					Collection imoveisMicromedicaoCarregamentoLigacaoEsgoto = fachada.carregarDadosConsumo(Integer
									.valueOf(idImovelAnaliseMedicaoConsumo), LigacaoTipo.LIGACAO_ESGOTO);

					if(imoveisMicromedicaoCarregamentoLigacaoEsgoto != null){
						imoveisMicromedicaoEsgoto.addAll(imoveisMicromedicaoCarregamentoLigacaoEsgoto);
					}

					if(medicoesHistoricoPoco != null && !medicoesHistoricoPoco.isEmpty()){

						Collections.sort((List) medicoesHistoricoPoco, new Comparator() {

							public int compare(Object a, Object b){

								int retorno = 0;
								Integer anoMesReferencia1 = ((MedicaoHistorico) a).getAnoMesReferencia();
								Integer anoMesReferencia2 = ((MedicaoHistorico) b).getAnoMesReferencia();

								if(anoMesReferencia1.compareTo(anoMesReferencia2) == 1){
									retorno = -1;
								}else if(anoMesReferencia1.compareTo(anoMesReferencia2) == -1){
									retorno = 1;
								}

								return retorno;

							}
						});
					}

					// coloca a colecao de medicao historico no request
					// medicao
					sessao.setAttribute("medicoesHistoricosPoco", medicoesHistoricoPoco);
					// coloca colecao de consumo historico no request

					if(imoveisMicromedicaoEsgoto != null && !imoveisMicromedicaoEsgoto.isEmpty()){

						Collections.sort((List) imoveisMicromedicaoEsgoto, new Comparator() {

							public int compare(Object a, Object b){

								int retorno = 0;
								Integer anoMesReferencia1 = ((ImovelMicromedicao) a).getConsumoHistorico().getReferenciaFaturamento();
								Integer anoMesReferencia2 = ((ImovelMicromedicao) b).getConsumoHistorico().getReferenciaFaturamento();

								if(anoMesReferencia1.compareTo(anoMesReferencia2) == 1){
									retorno = -1;
								}else if(anoMesReferencia1.compareTo(anoMesReferencia2) == -1){
									retorno = 1;
								}

								return retorno;

							}
						});
					}

					// consumo
					sessao.setAttribute("imoveisMicromedicaoEsgoto", imoveisMicromedicaoEsgoto);

					sessao.setAttribute("consultarImovelActionForm", consultarImovelActionForm);

				}

			}else{

				httpServletRequest.setAttribute("idImovelAnaliseMedicaoConsumoNaoEncontrado", "true");

				consultarImovelActionForm.setMatriculaImovelAnaliseMedicaoConsumo("IMÓVEL INEXISTENTE");

				sessao.removeAttribute("consultarImovelActionForm");
				sessao.removeAttribute("imovelDadosAnaliseMedicaoConsumo");
				sessao.removeAttribute("medicoesHistoricos");
				sessao.removeAttribute("imoveisMicromedicao");
				sessao.removeAttribute("colecaoImovelSubcategoria");
				sessao.removeAttribute("leituraConsumoActionForm");
				sessao.removeAttribute("medicoesHistoricosPoco");
				sessao.removeAttribute("imoveisMicromedicaoEsgoto");

				consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
				consultarImovelActionForm.setSituacaoAguaAnaliseMedicaoConsumo(null);
				consultarImovelActionForm.setSituacaoEsgotoAnaliseMedicaoConsumo(null);
				consultarImovelActionForm.setNumeroSegmento(null);
				consultarImovelActionForm.setTipoApresentacao(null);
				consultarImovelActionForm.setLocalidade(null);
				consultarImovelActionForm.setNomeLocalidade(null);
				consultarImovelActionForm.setSetorComercialID(null);
				consultarImovelActionForm.setSetorComercial(null);
				consultarImovelActionForm.setSetorComercialNome(null);
				consultarImovelActionForm.setQuadraInicial(null);
				consultarImovelActionForm.setQuadraInicialNome(null);
				consultarImovelActionForm.setQuadraInicialID(null);
				consultarImovelActionForm.setQuadraInicialMensagem(null);
				consultarImovelActionForm.setQuadraFinal(null);
				consultarImovelActionForm.setQuadraFinalNome(null);
				consultarImovelActionForm.setQuadraFinalID(null);
				consultarImovelActionForm.setQuadraFinalMensagem(null);
				consultarImovelActionForm.setImovel(null);
				consultarImovelActionForm.setImovelCondominio(null);
				consultarImovelActionForm.setGrupoFaturamento(null);
				consultarImovelActionForm.setEmpresaLeitura(null);
				consultarImovelActionForm.setIndicadorImovelCondominio(null);
				consultarImovelActionForm.setPerfilImovel(null);
				consultarImovelActionForm.setCategoriaImovel(null);
				consultarImovelActionForm.setQuantidadeEconomia(null);
				consultarImovelActionForm.setTipoMedicao(null);
				consultarImovelActionForm.setIdTipoMedicao(null);
				consultarImovelActionForm.setTipoLigacao(null);
				consultarImovelActionForm.setTipoAnormalidade(null);
				consultarImovelActionForm.setAnormalidadeLeituraInformada(null);
				consultarImovelActionForm.setAnormalidadeLeituraFaturada(null);
				consultarImovelActionForm.setAnormalidadeConsumo(null);
				consultarImovelActionForm.setConsumoFaturamdoMinimo(null);
				consultarImovelActionForm.setConsumoMedidoMinimo(null);
				consultarImovelActionForm.setConsumoMedioMinimo(null);
				consultarImovelActionForm.setInscricaoTipo(null);
				consultarImovelActionForm.setMesAnoFaturamentoCorrente(null);
				consultarImovelActionForm.setInscricaoImovel(null);
				consultarImovelActionForm.setEnderecoFormatado(null);
				consultarImovelActionForm.setLigacaoAguaSituacao(null);
				consultarImovelActionForm.setLigacaoEsgotoSituacao(null);
				consultarImovelActionForm.setClienteNome(null);
				consultarImovelActionForm.setClienteCpfCnpj(null);
				consultarImovelActionForm.setNumeroHidrometro(null);
				consultarImovelActionForm.setCapacidadeHidrometro(null);
				consultarImovelActionForm.setTipoHidrometro(null);
				consultarImovelActionForm.setMarcaHidrometro(null);
				consultarImovelActionForm.setDiametroHidrometro(null);
				consultarImovelActionForm.setInstalacaoHidrometro(null);
				consultarImovelActionForm.setLocalInstalacaoHidrometro(null);
				consultarImovelActionForm.setAnoFabricacao(null);
				consultarImovelActionForm.setProtecaoHidrometro(null);
				consultarImovelActionForm.setIndicadorCavalete(null);
				consultarImovelActionForm.setDtLeituraAnterior(null);
				consultarImovelActionForm.setLeituraAnterior(null);
				consultarImovelActionForm.setDtLeituraInformada(null);
				consultarImovelActionForm.setLeituraAtualInformada(null);
				consultarImovelActionForm.setSituacaoLeituraAtual(null);
				consultarImovelActionForm.setCodigoFuncionario(null);
				consultarImovelActionForm.setDtLeituraFaturada(null);
				consultarImovelActionForm.setLeituraAtualFaturada(null);
				consultarImovelActionForm.setConsumoRateio(null);
				consultarImovelActionForm.setConsumoFaturado(null);
				consultarImovelActionForm.setConsumoMedido(null);
				consultarImovelActionForm.setPercentualVariacao(null);
				consultarImovelActionForm.setConsumoMedioHidrometro(null);
				consultarImovelActionForm.setConsumoCreditoAnterior(null);
				consultarImovelActionForm.setDiasConsumo(null);
				consultarImovelActionForm.setConsumoTipo(null);
				consultarImovelActionForm.setIdImovelSubstituirConsumo(null);
				consultarImovelActionForm.setHabilitaLupa(null);
				consultarImovelActionForm.setIdEmpresa(null);
				consultarImovelActionForm.setIdGrupoFaturamento(null);
				consultarImovelActionForm.setLocalidadeFiltro(null);
				consultarImovelActionForm.setNomeLocalidadeFiltro(null);
				consultarImovelActionForm.setSetorComercialFiltro(null);
				consultarImovelActionForm.setQuadraInicialFiltro(null);
				consultarImovelActionForm.setQuadraFinalFiltro(null);
				consultarImovelActionForm.setImovelFiltro(null);
				consultarImovelActionForm.setImovelCondominioFiltro(null);
				consultarImovelActionForm.setImovelMatriculaFiltro(null);
				consultarImovelActionForm.setImovelMatriculaCondominioFiltro(null);
				consultarImovelActionForm.setDataLigacaoAgua(null);
				consultarImovelActionForm.setDataCorteAgua(null);
				consultarImovelActionForm.setDataReligacaoAgua(null);
				consultarImovelActionForm.setDataSupressaoAgua(null);
				consultarImovelActionForm.setDataRestabelecimentoAgua(null);
				consultarImovelActionForm.setDescricaoLigacaoAguaDiametro(null);
				consultarImovelActionForm.setDescricaoLigacaoAguaMaterial(null);
				consultarImovelActionForm.setDescricaoligacaoAguaPerfil(null);
				consultarImovelActionForm.setNumeroConsumominimoAgua(null);
				consultarImovelActionForm.setMatriculaFuncionarioReligacao(null);
				consultarImovelActionForm.setIdLigacaoEsgoto(null);
				consultarImovelActionForm.setConsumoMesEsgoto(null);
				consultarImovelActionForm.setDataLigacaoEsgoto(null);
				consultarImovelActionForm.setDescricaoLigacaoEsgotoDiametro(null);
				consultarImovelActionForm.setDescricaoLigacaoEsgotoMaterial(null);
				consultarImovelActionForm.setDescricaoligacaoEsgotoPerfil(null);
				consultarImovelActionForm.setNumeroConsumominimoEsgoto(null);
				consultarImovelActionForm.setPercentualEsgoto(null);
				consultarImovelActionForm.setPercentualAguaConsumidaColetada(null);
				consultarImovelActionForm.setDescricaoPocoTipo(null);
				consultarImovelActionForm.setIdGrupoFaturamentoFiltro(null);
				consultarImovelActionForm.setIdEmpresaFiltro(null);
				consultarImovelActionForm.setIndicadorImovelCondominioFiltro(null);
				consultarImovelActionForm.setConsumoMedioImovel(null);
				consultarImovelActionForm.setPerfilImovelFiltro(null);
				consultarImovelActionForm.setCategoriaImovelFiltro(null);
				consultarImovelActionForm.setQuantidadeEconomiaFiltro(null);
				consultarImovelActionForm.setTipoMedicaoFiltro(null);
				consultarImovelActionForm.setIdTipoMedicaoFiltro(null);
				consultarImovelActionForm.setTipoLigacaoFiltro(null);
				consultarImovelActionForm.setTipoAnormalidadeFiltro(null);
				consultarImovelActionForm.setAnormalidadeLeituraInformadaFiltro(null);
				consultarImovelActionForm.setAnormalidadeLeituraFaturadaFiltro(null);
				consultarImovelActionForm.setAnormalidadeConsumoFiltro(null);
				consultarImovelActionForm.setConsumoFaturamdoMinimoFiltro(null);
				consultarImovelActionForm.setConsumoMedidoMinimoFiltro(null);
				consultarImovelActionForm.setConsumoMedioMinimoFiltro(null);
				consultarImovelActionForm.setIdLigacaoAguaSituacao(null);
				consultarImovelActionForm.setIdLigacaoAgua(null);
				consultarImovelActionForm.setIdAnormalidade(null);
				consultarImovelActionForm.setDescricaoAnormalidade(null);
				consultarImovelActionForm.setNumeroLeituraInstalacao(null);

				consultarImovelActionForm.setTipoMedicaoPoco(null);
				consultarImovelActionForm.setNumeroHidrometroPoco(null);
				consultarImovelActionForm.setInstalacaoHidrometroPoco(null);
				consultarImovelActionForm.setCapacidadeHidrometroPoco(null);
				consultarImovelActionForm.setTipoHidrometroPoco(null);
				consultarImovelActionForm.setMarcaHidrometroPoco(null);
				consultarImovelActionForm.setLocalInstalacaoHidrometroPoco(null);
				consultarImovelActionForm.setDiametroHidrometroPoco(null);
				consultarImovelActionForm.setProtecaoHidrometroPoco(null);
				consultarImovelActionForm.setIndicadorCavaletePoco(null);
				consultarImovelActionForm.setAnoFabricacaoPoco(null);
				consultarImovelActionForm.setDtLeituraAnteriorPoco(null);
				consultarImovelActionForm.setLeituraAnteriorPoco(null);
				consultarImovelActionForm.setDtLeituraInformadaPoco(null);
				consultarImovelActionForm.setLeituraAtualInformadaPoco(null);
				consultarImovelActionForm.setDtLeituraFaturadaPoco(null);
				consultarImovelActionForm.setLeituraAtualFaturadaPoco(null);
				consultarImovelActionForm.setSituacaoLeituraAtualPoco(null);
				consultarImovelActionForm.setCodigoFuncionarioPoco(null);
				consultarImovelActionForm.setAnormalidadeLeituraInformadaPoco(null);
				consultarImovelActionForm.setAnormalidadeLeituraFaturadaPoco(null);
				consultarImovelActionForm.setConsumoMedioHidrometroPoco(null);
				consultarImovelActionForm.setConsumoMedidoEsgoto(null);
				consultarImovelActionForm.setConsumoFaturadoEsgoto(null);
				consultarImovelActionForm.setConsumoRateioEsgoto(null);
				consultarImovelActionForm.setConsumoMedioImovelEsgoto(null);
				consultarImovelActionForm.setAnormalidadeConsumoEsgoto(null);
				consultarImovelActionForm.setPercentualVariacaoEsgoto(null);
				consultarImovelActionForm.setDiasConsumoEsgoto(null);
				consultarImovelActionForm.setConsumoTipoEsgoto(null);
				consultarImovelActionForm.setNumeroLeituraInstalacaoPoco(null);

				consultarImovelActionForm.setConsumoMinimoCreditadoMes(null);
				consultarImovelActionForm.setConsumoMinimoCreditadoEsgoto(null);
				consultarImovelActionForm.setConsumoCreditoAnteriorEsgoto(null);
			}

		}else{

			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(idImovelAnaliseMedicaoConsumo);

			httpServletRequest.setAttribute("idImovelAnaliseMedicaoConsumoNaoEncontrado", null);

			sessao.removeAttribute("consultarImovelActionForm");
			sessao.removeAttribute("imovelDadosAnaliseMedicaoConsumo");
			sessao.removeAttribute("medicoesHistoricos");
			sessao.removeAttribute("imoveisMicromedicao");
			sessao.removeAttribute("colecaoImovelSubcategoria");
			sessao.removeAttribute("leituraConsumoActionForm");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("medicoesHistoricosPoco");
			sessao.removeAttribute("imoveisMicromedicaoEsgoto");

			consultarImovelActionForm.setMatriculaImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setSituacaoAguaAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setSituacaoEsgotoAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setNumeroSegmento(null);
			consultarImovelActionForm.setTipoApresentacao(null);
			consultarImovelActionForm.setLocalidade(null);
			consultarImovelActionForm.setNomeLocalidade(null);
			consultarImovelActionForm.setSetorComercialID(null);
			consultarImovelActionForm.setSetorComercial(null);
			consultarImovelActionForm.setSetorComercialNome(null);
			consultarImovelActionForm.setQuadraInicial(null);
			consultarImovelActionForm.setQuadraInicialNome(null);
			consultarImovelActionForm.setQuadraInicialID(null);
			consultarImovelActionForm.setQuadraInicialMensagem(null);
			consultarImovelActionForm.setQuadraFinal(null);
			consultarImovelActionForm.setQuadraFinalNome(null);
			consultarImovelActionForm.setQuadraFinalID(null);
			consultarImovelActionForm.setQuadraFinalMensagem(null);
			consultarImovelActionForm.setImovel(null);
			consultarImovelActionForm.setImovelCondominio(null);
			consultarImovelActionForm.setGrupoFaturamento(null);
			consultarImovelActionForm.setEmpresaLeitura(null);
			consultarImovelActionForm.setIndicadorImovelCondominio(null);
			consultarImovelActionForm.setPerfilImovel(null);
			consultarImovelActionForm.setCategoriaImovel(null);
			consultarImovelActionForm.setQuantidadeEconomia(null);
			consultarImovelActionForm.setTipoMedicao(null);
			consultarImovelActionForm.setIdTipoMedicao(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setTipoAnormalidade(null);
			consultarImovelActionForm.setAnormalidadeLeituraInformada(null);
			consultarImovelActionForm.setAnormalidadeLeituraFaturada(null);
			consultarImovelActionForm.setAnormalidadeConsumo(null);
			consultarImovelActionForm.setConsumoFaturamdoMinimo(null);
			consultarImovelActionForm.setConsumoMedidoMinimo(null);
			consultarImovelActionForm.setConsumoMedioMinimo(null);
			consultarImovelActionForm.setInscricaoTipo(null);
			consultarImovelActionForm.setMesAnoFaturamentoCorrente(null);
			consultarImovelActionForm.setInscricaoImovel(null);
			consultarImovelActionForm.setEnderecoFormatado(null);
			consultarImovelActionForm.setLigacaoAguaSituacao(null);
			consultarImovelActionForm.setLigacaoEsgotoSituacao(null);
			consultarImovelActionForm.setClienteNome(null);
			consultarImovelActionForm.setClienteCpfCnpj(null);
			consultarImovelActionForm.setNumeroHidrometro(null);
			consultarImovelActionForm.setCapacidadeHidrometro(null);
			consultarImovelActionForm.setTipoHidrometro(null);
			consultarImovelActionForm.setMarcaHidrometro(null);
			consultarImovelActionForm.setDiametroHidrometro(null);
			consultarImovelActionForm.setInstalacaoHidrometro(null);
			consultarImovelActionForm.setLocalInstalacaoHidrometro(null);
			consultarImovelActionForm.setAnoFabricacao(null);
			consultarImovelActionForm.setProtecaoHidrometro(null);
			consultarImovelActionForm.setIndicadorCavalete(null);
			consultarImovelActionForm.setDtLeituraAnterior(null);
			consultarImovelActionForm.setLeituraAnterior(null);
			consultarImovelActionForm.setDtLeituraInformada(null);
			consultarImovelActionForm.setLeituraAtualInformada(null);
			consultarImovelActionForm.setSituacaoLeituraAtual(null);
			consultarImovelActionForm.setCodigoFuncionario(null);
			consultarImovelActionForm.setDtLeituraFaturada(null);
			consultarImovelActionForm.setLeituraAtualFaturada(null);
			consultarImovelActionForm.setConsumoRateio(null);
			consultarImovelActionForm.setConsumoFaturado(null);
			consultarImovelActionForm.setConsumoMedido(null);
			consultarImovelActionForm.setPercentualVariacao(null);
			consultarImovelActionForm.setConsumoMedioHidrometro(null);
			consultarImovelActionForm.setConsumoCreditoAnterior(null);
			consultarImovelActionForm.setDiasConsumo(null);
			consultarImovelActionForm.setConsumoTipo(null);
			consultarImovelActionForm.setIdImovelSubstituirConsumo(null);
			consultarImovelActionForm.setHabilitaLupa(null);
			consultarImovelActionForm.setIdEmpresa(null);
			consultarImovelActionForm.setIdGrupoFaturamento(null);
			consultarImovelActionForm.setLocalidadeFiltro(null);
			consultarImovelActionForm.setNomeLocalidadeFiltro(null);
			consultarImovelActionForm.setSetorComercialFiltro(null);
			consultarImovelActionForm.setQuadraInicialFiltro(null);
			consultarImovelActionForm.setQuadraFinalFiltro(null);
			consultarImovelActionForm.setImovelFiltro(null);
			consultarImovelActionForm.setImovelCondominioFiltro(null);
			consultarImovelActionForm.setImovelMatriculaFiltro(null);
			consultarImovelActionForm.setImovelMatriculaCondominioFiltro(null);
			consultarImovelActionForm.setDataLigacaoAgua(null);
			consultarImovelActionForm.setDataCorteAgua(null);
			consultarImovelActionForm.setDataReligacaoAgua(null);
			consultarImovelActionForm.setDataSupressaoAgua(null);
			consultarImovelActionForm.setDataRestabelecimentoAgua(null);
			consultarImovelActionForm.setDescricaoLigacaoAguaDiametro(null);
			consultarImovelActionForm.setDescricaoLigacaoAguaMaterial(null);
			consultarImovelActionForm.setDescricaoligacaoAguaPerfil(null);
			consultarImovelActionForm.setNumeroConsumominimoAgua(null);
			consultarImovelActionForm.setMatriculaFuncionarioReligacao(null);
			consultarImovelActionForm.setIdLigacaoEsgoto(null);
			consultarImovelActionForm.setConsumoMesEsgoto(null);
			consultarImovelActionForm.setDataLigacaoEsgoto(null);
			consultarImovelActionForm.setDescricaoLigacaoEsgotoDiametro(null);
			consultarImovelActionForm.setDescricaoLigacaoEsgotoMaterial(null);
			consultarImovelActionForm.setDescricaoligacaoEsgotoPerfil(null);
			consultarImovelActionForm.setNumeroConsumominimoEsgoto(null);
			consultarImovelActionForm.setPercentualEsgoto(null);
			consultarImovelActionForm.setPercentualAguaConsumidaColetada(null);
			consultarImovelActionForm.setDescricaoPocoTipo(null);
			consultarImovelActionForm.setIdGrupoFaturamentoFiltro(null);
			consultarImovelActionForm.setIdEmpresaFiltro(null);
			consultarImovelActionForm.setIndicadorImovelCondominioFiltro(null);
			consultarImovelActionForm.setConsumoMedioImovel(null);
			consultarImovelActionForm.setPerfilImovelFiltro(null);
			consultarImovelActionForm.setCategoriaImovelFiltro(null);
			consultarImovelActionForm.setQuantidadeEconomiaFiltro(null);
			consultarImovelActionForm.setTipoMedicaoFiltro(null);
			consultarImovelActionForm.setIdTipoMedicaoFiltro(null);
			consultarImovelActionForm.setTipoLigacaoFiltro(null);
			consultarImovelActionForm.setTipoAnormalidadeFiltro(null);
			consultarImovelActionForm.setAnormalidadeLeituraInformadaFiltro(null);
			consultarImovelActionForm.setAnormalidadeLeituraFaturadaFiltro(null);
			consultarImovelActionForm.setAnormalidadeConsumoFiltro(null);
			consultarImovelActionForm.setConsumoFaturamdoMinimoFiltro(null);
			consultarImovelActionForm.setConsumoMedidoMinimoFiltro(null);
			consultarImovelActionForm.setConsumoMedioMinimoFiltro(null);
			consultarImovelActionForm.setIdLigacaoAguaSituacao(null);
			consultarImovelActionForm.setIdLigacaoAgua(null);
			consultarImovelActionForm.setIdAnormalidade(null);
			consultarImovelActionForm.setDescricaoAnormalidade(null);
			consultarImovelActionForm.setNumeroLeituraInstalacao(null);

			consultarImovelActionForm.setTipoMedicaoPoco(null);
			consultarImovelActionForm.setNumeroHidrometroPoco(null);
			consultarImovelActionForm.setInstalacaoHidrometroPoco(null);
			consultarImovelActionForm.setCapacidadeHidrometroPoco(null);
			consultarImovelActionForm.setTipoHidrometroPoco(null);
			consultarImovelActionForm.setMarcaHidrometroPoco(null);
			consultarImovelActionForm.setLocalInstalacaoHidrometroPoco(null);
			consultarImovelActionForm.setDiametroHidrometroPoco(null);
			consultarImovelActionForm.setProtecaoHidrometroPoco(null);
			consultarImovelActionForm.setIndicadorCavaletePoco(null);
			consultarImovelActionForm.setAnoFabricacaoPoco(null);
			consultarImovelActionForm.setDtLeituraAnteriorPoco(null);
			consultarImovelActionForm.setLeituraAnteriorPoco(null);
			consultarImovelActionForm.setDtLeituraInformadaPoco(null);
			consultarImovelActionForm.setLeituraAtualInformadaPoco(null);
			consultarImovelActionForm.setDtLeituraFaturadaPoco(null);
			consultarImovelActionForm.setLeituraAtualFaturadaPoco(null);
			consultarImovelActionForm.setSituacaoLeituraAtualPoco(null);
			consultarImovelActionForm.setCodigoFuncionarioPoco(null);
			consultarImovelActionForm.setAnormalidadeLeituraInformadaPoco(null);
			consultarImovelActionForm.setAnormalidadeLeituraFaturadaPoco(null);
			consultarImovelActionForm.setConsumoMedioHidrometroPoco(null);
			consultarImovelActionForm.setConsumoMedidoEsgoto(null);
			consultarImovelActionForm.setConsumoFaturadoEsgoto(null);
			consultarImovelActionForm.setConsumoRateioEsgoto(null);
			consultarImovelActionForm.setConsumoMedioImovelEsgoto(null);
			consultarImovelActionForm.setAnormalidadeConsumoEsgoto(null);
			consultarImovelActionForm.setPercentualVariacaoEsgoto(null);
			consultarImovelActionForm.setDiasConsumoEsgoto(null);
			consultarImovelActionForm.setConsumoTipoEsgoto(null);
			consultarImovelActionForm.setNumeroLeituraInstalacaoPoco(null);

			consultarImovelActionForm.setConsumoMinimoCreditadoMes(null);
			consultarImovelActionForm.setConsumoMinimoCreditadoEsgoto(null);
			consultarImovelActionForm.setConsumoCreditoAnteriorEsgoto(null);
		}

		return retorno;
	}

	/**
	 * Limpar Form consultarImovelActionForm
	 * 
	 * @param sessao
	 * @param httpServletRequest
	 * @param consultarImovelActionForm
	 */
	private void limparForm(HttpSession sessao, HttpServletRequest httpServletRequest, ConsultarImovelActionForm consultarImovelActionForm){

		httpServletRequest.setAttribute("idImovelAnaliseMedicaoConsumoNaoEncontrado", null);

		sessao.removeAttribute("consultarImovelActionForm");

		sessao.removeAttribute("imovelDadosAnaliseMedicaoConsumo");
		sessao.removeAttribute("medicoesHistoricos");
		sessao.removeAttribute("imoveisMicromedicao");
		sessao.removeAttribute("colecaoImovelSubcategoria");
		sessao.removeAttribute("leituraConsumoActionForm");
		sessao.removeAttribute("enderecoAnaliseMedicaoConsumo");
		sessao.removeAttribute("medicoesHistoricosPoco");
		sessao.removeAttribute("imoveisMicromedicaoEsgoto");

		consultarImovelActionForm.setIdImovelDadosComplementares(null);
		consultarImovelActionForm.setIdImovelDadosCadastrais(null);
		consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
		consultarImovelActionForm.setIdImovelDebitos(null);
		consultarImovelActionForm.setIdImovelPagamentos(null);
		consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
		consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
		consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
		consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

		consultarImovelActionForm.setMatriculaImovelAnaliseMedicaoConsumo(null);
		consultarImovelActionForm.setSituacaoAguaAnaliseMedicaoConsumo(null);
		consultarImovelActionForm.setSituacaoEsgotoAnaliseMedicaoConsumo(null);
		consultarImovelActionForm.setNumeroSegmento(null);
		consultarImovelActionForm.setTipoApresentacao(null);
		consultarImovelActionForm.setLocalidade(null);
		consultarImovelActionForm.setNomeLocalidade(null);
		consultarImovelActionForm.setSetorComercialID(null);
		consultarImovelActionForm.setSetorComercial(null);
		consultarImovelActionForm.setSetorComercialNome(null);
		consultarImovelActionForm.setQuadraInicial(null);
		consultarImovelActionForm.setQuadraInicialNome(null);
		consultarImovelActionForm.setQuadraInicialID(null);
		consultarImovelActionForm.setQuadraInicialMensagem(null);
		consultarImovelActionForm.setQuadraFinal(null);
		consultarImovelActionForm.setQuadraFinalNome(null);
		consultarImovelActionForm.setQuadraFinalID(null);
		consultarImovelActionForm.setQuadraFinalMensagem(null);
		consultarImovelActionForm.setImovel(null);
		consultarImovelActionForm.setImovelCondominio(null);
		consultarImovelActionForm.setGrupoFaturamento(null);
		consultarImovelActionForm.setDiaVencimento(null);
		consultarImovelActionForm.setEmpresaLeitura(null);
		consultarImovelActionForm.setRota(null);
		consultarImovelActionForm.setSequencialRota(null);
		consultarImovelActionForm.setIndicadorImovelCondominio(null);
		consultarImovelActionForm.setPerfilImovel(null);
		consultarImovelActionForm.setCategoriaImovel(null);
		consultarImovelActionForm.setQuantidadeEconomia(null);
		consultarImovelActionForm.setTipoMedicao(null);
		consultarImovelActionForm.setIdTipoMedicao(null);
		consultarImovelActionForm.setTipoLigacao(null);
		consultarImovelActionForm.setTipoAnormalidade(null);
		consultarImovelActionForm.setAnormalidadeLeituraInformada(null);
		consultarImovelActionForm.setAnormalidadeLeituraFaturada(null);
		consultarImovelActionForm.setAnormalidadeConsumo(null);
		consultarImovelActionForm.setConsumoFaturamdoMinimo(null);
		consultarImovelActionForm.setConsumoMedidoMinimo(null);
		consultarImovelActionForm.setConsumoMedioMinimo(null);
		consultarImovelActionForm.setConsumoMinimoCreditadoMes(null);
		consultarImovelActionForm.setInscricaoTipo(null);
		consultarImovelActionForm.setMesAnoFaturamentoCorrente(null);
		consultarImovelActionForm.setInscricaoImovel(null);
		consultarImovelActionForm.setEnderecoFormatado(null);
		consultarImovelActionForm.setLigacaoAguaSituacao(null);
		consultarImovelActionForm.setLigacaoEsgotoSituacao(null);
		consultarImovelActionForm.setClienteNome(null);
		consultarImovelActionForm.setClienteCpfCnpj(null);
		consultarImovelActionForm.setIdHidrometro(null);
		consultarImovelActionForm.setNumeroHidrometro(null);
		consultarImovelActionForm.setCapacidadeHidrometro(null);
		consultarImovelActionForm.setTipoHidrometro(null);
		consultarImovelActionForm.setMarcaHidrometro(null);
		consultarImovelActionForm.setDiametroHidrometro(null);
		consultarImovelActionForm.setInstalacaoHidrometro(null);
		consultarImovelActionForm.setLocalInstalacaoHidrometro(null);
		consultarImovelActionForm.setAnoFabricacao(null);
		consultarImovelActionForm.setProtecaoHidrometro(null);
		consultarImovelActionForm.setIndicadorCavalete(null);
		consultarImovelActionForm.setDtLeituraAnterior(null);
		consultarImovelActionForm.setLeituraAnterior(null);
		consultarImovelActionForm.setDtLeituraInformada(null);
		consultarImovelActionForm.setLeituraAtualInformada(null);
		consultarImovelActionForm.setSituacaoLeituraAtual(null);
		consultarImovelActionForm.setCodigoFuncionario(null);
		consultarImovelActionForm.setDtLeituraFaturada(null);
		consultarImovelActionForm.setLeituraAtualFaturada(null);
		consultarImovelActionForm.setConsumoRateio(null);
		consultarImovelActionForm.setConsumoFaturado(null);
		consultarImovelActionForm.setConsumoMedido(null);
		consultarImovelActionForm.setPercentualVariacao(null);
		consultarImovelActionForm.setConsumoMedioHidrometro(null);
		consultarImovelActionForm.setDiasConsumo(null);
		consultarImovelActionForm.setConsumoTipo(null);
		consultarImovelActionForm.setIdImovelSubstituirConsumo(null);
		consultarImovelActionForm.setHabilitaLupa(null);
		consultarImovelActionForm.setIdEmpresa(null);
		consultarImovelActionForm.setIdGrupoFaturamento(null);
		consultarImovelActionForm.setLocalidadeFiltro(null);
		consultarImovelActionForm.setNomeLocalidadeFiltro(null);
		consultarImovelActionForm.setSetorComercialFiltro(null);
		consultarImovelActionForm.setQuadraInicialFiltro(null);
		consultarImovelActionForm.setQuadraFinalFiltro(null);
		consultarImovelActionForm.setImovelFiltro(null);
		consultarImovelActionForm.setImovelCondominioFiltro(null);
		consultarImovelActionForm.setImovelMatriculaFiltro(null);
		consultarImovelActionForm.setImovelMatriculaCondominioFiltro(null);
		consultarImovelActionForm.setDataLigacaoAgua(null);
		consultarImovelActionForm.setDataCorteAgua(null);
		consultarImovelActionForm.setDataReligacaoAgua(null);
		consultarImovelActionForm.setDataSupressaoAgua(null);
		consultarImovelActionForm.setDataRestabelecimentoAgua(null);
		consultarImovelActionForm.setDescricaoLigacaoAguaDiametro(null);
		consultarImovelActionForm.setDescricaoLigacaoAguaMaterial(null);
		consultarImovelActionForm.setDescricaoligacaoAguaPerfil(null);
		consultarImovelActionForm.setNumeroConsumominimoAgua(null);
		consultarImovelActionForm.setMatriculaFuncionarioReligacao(null);
		consultarImovelActionForm.setIdLigacaoEsgoto(null);
		consultarImovelActionForm.setConsumoMesEsgoto(null);
		consultarImovelActionForm.setDataLigacaoEsgoto(null);
		consultarImovelActionForm.setDescricaoLigacaoEsgotoDiametro(null);
		consultarImovelActionForm.setDescricaoLigacaoEsgotoMaterial(null);
		consultarImovelActionForm.setDescricaoligacaoEsgotoPerfil(null);
		consultarImovelActionForm.setNumeroConsumominimoEsgoto(null);
		consultarImovelActionForm.setPercentualEsgoto(null);
		consultarImovelActionForm.setPercentualAguaConsumidaColetada(null);
		consultarImovelActionForm.setDescricaoPocoTipo(null);
		consultarImovelActionForm.setIdGrupoFaturamentoFiltro(null);
		consultarImovelActionForm.setIdEmpresaFiltro(null);
		consultarImovelActionForm.setIndicadorImovelCondominioFiltro(null);
		consultarImovelActionForm.setConsumoMedioImovel(null);
		consultarImovelActionForm.setPerfilImovelFiltro(null);
		consultarImovelActionForm.setCategoriaImovelFiltro(null);
		consultarImovelActionForm.setQuantidadeEconomiaFiltro(null);
		consultarImovelActionForm.setTipoMedicaoFiltro(null);
		consultarImovelActionForm.setIdTipoMedicaoFiltro(null);
		consultarImovelActionForm.setTipoLigacaoFiltro(null);
		consultarImovelActionForm.setTipoAnormalidadeFiltro(null);
		consultarImovelActionForm.setEnderecoAnaliseMedicaoConsumo(null);
		consultarImovelActionForm.setAnormalidadeLeituraInformadaFiltro(null);
		consultarImovelActionForm.setAnormalidadeLeituraFaturadaFiltro(null);
		consultarImovelActionForm.setAnormalidadeConsumoFiltro(null);
		consultarImovelActionForm.setConsumoFaturamdoMinimoFiltro(null);
		consultarImovelActionForm.setConsumoMedidoMinimoFiltro(null);
		consultarImovelActionForm.setConsumoMedioMinimoFiltro(null);
		consultarImovelActionForm.setIdLigacaoAguaSituacao(null);
		consultarImovelActionForm.setIdLigacaoAgua(null);
		consultarImovelActionForm.setIdAnormalidade(null);
		consultarImovelActionForm.setDescricaoAnormalidade(null);
		consultarImovelActionForm.setNumeroLeituraInstalacao(null);

		consultarImovelActionForm.setTipoMedicaoPoco(null);
		consultarImovelActionForm.setNumeroHidrometroPoco(null);
		consultarImovelActionForm.setInstalacaoHidrometroPoco(null);
		consultarImovelActionForm.setCapacidadeHidrometroPoco(null);
		consultarImovelActionForm.setTipoHidrometroPoco(null);
		consultarImovelActionForm.setMarcaHidrometroPoco(null);
		consultarImovelActionForm.setLocalInstalacaoHidrometroPoco(null);
		consultarImovelActionForm.setDiametroHidrometroPoco(null);
		consultarImovelActionForm.setProtecaoHidrometroPoco(null);
		consultarImovelActionForm.setIndicadorCavaletePoco(null);
		consultarImovelActionForm.setAnoFabricacaoPoco(null);
		consultarImovelActionForm.setDtLeituraAnteriorPoco(null);
		consultarImovelActionForm.setLeituraAnteriorPoco(null);
		consultarImovelActionForm.setDtLeituraInformadaPoco(null);
		consultarImovelActionForm.setLeituraAtualInformadaPoco(null);
		consultarImovelActionForm.setDtLeituraFaturadaPoco(null);
		consultarImovelActionForm.setLeituraAtualFaturadaPoco(null);
		consultarImovelActionForm.setSituacaoLeituraAtualPoco(null);
		consultarImovelActionForm.setCodigoFuncionarioPoco(null);
		consultarImovelActionForm.setAnormalidadeLeituraInformadaPoco(null);
		consultarImovelActionForm.setAnormalidadeLeituraFaturadaPoco(null);
		consultarImovelActionForm.setConsumoMedioHidrometroPoco(null);
		consultarImovelActionForm.setConsumoMedidoEsgoto(null);
		consultarImovelActionForm.setConsumoFaturadoEsgoto(null);
		consultarImovelActionForm.setConsumoRateioEsgoto(null);
		consultarImovelActionForm.setConsumoMedioImovelEsgoto(null);
		consultarImovelActionForm.setAnormalidadeConsumoEsgoto(null);
		consultarImovelActionForm.setPercentualVariacaoEsgoto(null);
		consultarImovelActionForm.setDiasConsumoEsgoto(null);
		consultarImovelActionForm.setConsumoTipoEsgoto(null);
		consultarImovelActionForm.setConsumoMinimoCreditadoEsgoto(null);
		consultarImovelActionForm.setConsumoCreditoAnterior(null);
		consultarImovelActionForm.setConsumoCreditoAnteriorEsgoto(null);
		consultarImovelActionForm.setNumeroLeituraInstalacaoPoco(null);

		consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
	}
}
