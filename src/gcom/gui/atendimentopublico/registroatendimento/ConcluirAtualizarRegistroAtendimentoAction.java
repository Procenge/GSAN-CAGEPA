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

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosIdentificacaoLocalOcorrenciaHelper;
import gcom.cadastro.geografico.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade validar as informações das três abas do
 * processo de atualização de um registro de atendimento e chamar o método que
 * irá concluir a mesma
 * 
 * @author Sávio Luiz
 * @date 10/08/2006
 */
public class ConcluirAtualizarRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarRegistroAtendimentoActionForm form = (AtualizarRegistroAtendimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		// recupara o id da especificação para verificar se será gerado a ordem de serviço ou não
		// dependendo da mudança da especificação
		Integer idEspecificacaoBase = (Integer) sessao.getAttribute("idEspecificacaoBase");

		/*
		 * Validação Aba 01
		 * 
		 * ==========================================================================================
		 * ============
		 */
		fachada.validarInserirRegistroAtendimentoDadosGerais(form.getDataAtendimento(), form.getHora(), form.getTempoEsperaInicial(), form
						.getTempoEsperaFinal(), form.getUnidade(), null, form.getEspecificacao(), null);

		/*
		 * ==========================================================================================
		 * ============
		 * 
		 * ==========================================================================================
		 * ============
		 */

		/*
		 * Validação Aba 02
		 * 
		 * ==========================================================================================
		 * ============
		 */

		// [FS0040] - Validar Preenchimento dos campos
		String idImovel = form.getIdImovel();
		String pontoReferencia = form.getPontoReferencia();
		String idMunicipio = form.getIdMunicipio();
		String descricaoMunicipio = form.getDescricaoMunicipio();
		String cdBairro = form.getCdBairro();
		String descricaoBairro = form.getDescricaoBairro();
		String idAreaBairro = form.getIdBairroArea();
		String idlocalidade = form.getIdLocalidade();
		String descricaoLocalidade = form.getDescricaoLocalidade();
		String cdSetorComercial = form.getCdSetorComercial();
		String descricaoSetorComercial = form.getDescricaoSetorComercial();
		String numeroQuadra = form.getNnQuadra();
		String idDivisaoEsgoto = form.getIdDivisaoEsgoto();
		String idUnidade = form.getIdUnidadeAtual();
		String descricaoUnidade = form.getDescricaoUnidadeAtual();
		String idLocalOcorrencia = form.getIdLocalOcorrencia();
		String idPavimentoRua = form.getIdPavimentoRua();
		String idPavimentoCalcada = form.getIdPavimentoCalcada();
		String descricaoLocalOcorrencia = form.getDescricaoLocalOcorrencia();
		String imovelObrigatorio = form.getImovelObrigatorio();
		String pavimentoRuaObrigatorio = form.getPavimentoRuaObrigatorio();
		String pavimentoCalcadaObrigatorio = form.getPavimentoCalcadaObrigatorio();
		String solicitacaoTipoRelativoFaltaAgua = (String) sessao.getAttribute("solicitacaoTipoRelativoFaltaAgua");
		String solicitacaoTipoRelativoAreaEsgoto = (String) sessao.getAttribute("solicitacaoTipoRelativoAreaEsgoto");

		String desabilitarMunicipioBairro = (String) sessao.getAttribute("desabilitarMunicipioBairro");
		String indRuaLocalOcorrencia = form.getIndRuaLocalOcorrencia();
		String indCalcadaLocalOcorrencia = form.getIndCalcadaLocalOcorrencia();

		String idEspecificacao = form.getEspecificacao();
		String numeroRA = form.getNumeroRA();
		String idUnidadeDestino = form.getIdUnidadeAtual();

		Integer senhaAtendimento = null;
		if(form.getSenhaAtendimento() != null){
			String senhaAtendimentoString = form.getSenhaAtendimento().trim();
			if(senhaAtendimentoString != null && !senhaAtendimentoString.equals("")){
				senhaAtendimento = Util.converterStringParaInteger(senhaAtendimentoString);
			}
		}

		String indicadorProcessoAdmJud = form.getIndicadorProcessoAdmJud();
		String numeroProcessoAgencia = form.getNumeroProcessoAgencia();

		Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

		fachada.validarCamposObrigatoriosRA_2ABA(idImovel, pontoReferencia, idMunicipio, descricaoMunicipio, cdBairro, descricaoBairro,
						idAreaBairro, idlocalidade, descricaoLocalidade, cdSetorComercial, descricaoSetorComercial, numeroQuadra,
						idDivisaoEsgoto, idUnidade, descricaoUnidade, idLocalOcorrencia, idPavimentoRua, idPavimentoCalcada,
						descricaoLocalOcorrencia, imovelObrigatorio, pavimentoRuaObrigatorio, pavimentoCalcadaObrigatorio,
						solicitacaoTipoRelativoFaltaAgua, solicitacaoTipoRelativoAreaEsgoto, desabilitarMunicipioBairro,
						indRuaLocalOcorrencia, indCalcadaLocalOcorrencia, Integer.valueOf(idEspecificacao), Integer.valueOf(numeroRA),
						colecaoEnderecos, null, idUnidadeDestino);

		// -----------------------------------------------------------------------

		// valida os campos de enter(caso tenha mudado algum valor validar)
		validarCamposEnter(form, fachada, httpServletRequest, actionMapping, sessao, usuario);

		/*
		 * ==========================================================================================
		 * ============
		 * 
		 * ==========================================================================================
		 * ============
		 */

		/*
		 * Validação Aba 03
		 * 
		 * ==========================================================================================
		 * ============
		 */
		Collection colecaoRASolicitantes = null;
		AdicionarSolicitanteRegistroAtendimentoActionForm solicitanteForm = null;
		// Recupera o form do solicitante
		if(sessao.getAttribute("AdicionarSolicitanteRegistroAtendimentoActionForm") != null){
			solicitanteForm = (AdicionarSolicitanteRegistroAtendimentoActionForm) sessao
							.getAttribute("AdicionarSolicitanteRegistroAtendimentoActionForm");
		}

		// recupera a coleção de RA solicitante
		Collection colecaoRASolicitante = (Collection) sessao.getAttribute("colecaoRASolicitante");

		Collection colecaoRASolicitanteRemovida = (Collection) sessao.getAttribute("colecaoRASolicitanteRemovidas");

		String idSolicitantePrincipal = form.getIdSolicitantePrincipal();

		String clienteTipo = "-1";
		String especificacao = "";
		String numeroCpf = "";
		String orgaoExpedidorRg = "-1";
		String unidadeFederacaoRG = "-1";
		String numeroRG = "";
		String numeroCnpj = "";
		String idCliente = "-1";

		// [FS0044]
		if(solicitanteForm == null){
			FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
			filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroRegistroAtendimentoSolicitante.adicionarCaminhoParaCarregamentoEntidade("cliente");

			filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
							FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, form.getNumeroRA()));

			colecaoRASolicitantes = fachada.pesquisar(filtroRegistroAtendimentoSolicitante, RegistroAtendimentoSolicitante.class.getName());

			if(colecaoRASolicitantes != null && !colecaoRASolicitantes.isEmpty()){

				Iterator iteRASolicitante = colecaoRASolicitantes.iterator();

				while(iteRASolicitante.hasNext()){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteRASolicitante
									.next();
					if(registroAtendimentoSolicitante.getIndicadorSolicitantePrincipal() == ConstantesSistema.SIM){
						if(registroAtendimentoSolicitante.getClienteTipo() != null){
							clienteTipo = registroAtendimentoSolicitante.getClienteTipo().toString();
						}
						if(registroAtendimentoSolicitante.getCliente() != null){
							idCliente = registroAtendimentoSolicitante.getCliente().getId().toString();
						}
						if(registroAtendimentoSolicitante.getNumeroCpf() != null){
							numeroCpf = registroAtendimentoSolicitante.getNumeroCpf();
						}
						if(registroAtendimentoSolicitante.getNumeroCnpj() != null){
							numeroCnpj = registroAtendimentoSolicitante.getNumeroCnpj();
						}

					}
				}
			}
		}else{
			clienteTipo = solicitanteForm.getClienteTipo();
			idCliente = solicitanteForm.getIdCliente();
			numeroCpf = solicitanteForm.getNumeroCpf();
			numeroCnpj = solicitanteForm.getNumeroCnpj();
			numeroRG = solicitanteForm.getNumeroRG();
			orgaoExpedidorRg = solicitanteForm.getOrgaoExpedidorRg();
			unidadeFederacaoRG = solicitanteForm.getUnidadeFederacaoRG();
		}

		if(!Util.isVazioOuBranco(form.getClienteTipo()) && !form.getClienteTipo().equals("-1")){
			clienteTipo = form.getClienteTipo();
		}

		if(!Util.isVazioOuBranco(form.getEspecificacao())){
			especificacao = form.getEspecificacao();
		}

		if(!Util.isVazioOuBranco(form.getNumeroCpf())){
			numeroCpf = form.getNumeroCpf();
		}

		if(!Util.isVazioOuBranco(form.getNumeroRG())){
			numeroRG = form.getNumeroRG();
		}

		if(!Util.isVazioOuBranco(form.getOrgaoExpedidorRg())){
			orgaoExpedidorRg = form.getOrgaoExpedidorRg();
		}

		if(!Util.isVazioOuBranco(form.getUnidadeFederacaoRG())){
			unidadeFederacaoRG = form.getUnidadeFederacaoRG();
		}

		if(!Util.isVazioOuBranco(form.getNumeroCnpj())){
			numeroCnpj = form.getNumeroCnpj();
		}

		// Verifica os dados do solicitante com relação a rg, cpf e cnpj
		if(!fachada.isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(Integer.valueOf(form.getMeioSolicitacao()))){
			fachada.verificarDadosSolicitanteRgCpfCnpj(clienteTipo, idEspecificacao, numeroCpf, numeroRG, orgaoExpedidorRg,
							unidadeFederacaoRG, numeroCnpj, idCliente);
		}

		if(!Util.isVazioOrNulo(colecaoRASolicitante)){
			Iterator iteratorRASolicitante = colecaoRASolicitante.iterator();
			while(iteratorRASolicitante.hasNext()){
				RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteratorRASolicitante
								.next();

				Integer especificacaoID = null;
				Integer imovelID = null;
				Integer clienteID = null;

				if(!Util.isVazioOuBranco(idEspecificacao)){
					especificacaoID = Integer.valueOf(idEspecificacao);
				}

				if(!Util.isVazioOuBranco(idImovel)){
					imovelID = Integer.valueOf(idImovel);
				}

				if(!Util.isVazioOuBranco(registroAtendimentoSolicitante.getCliente())
								&& !Util.isVazioOuBranco(registroAtendimentoSolicitante.getCliente().getId())){
					clienteID = registroAtendimentoSolicitante.getCliente().getId();
				}

				fachada.verificarDebitosImovelCliente(especificacaoID, imovelID, clienteID);
			}
		}

		if(idSolicitantePrincipal != null && !idSolicitantePrincipal.equals("")){
			// responsável pera troca do solicitante principal caso tenha sido trocado então sai da
			// coleção
			boolean trocaPrincipal = false;

			if(colecaoRASolicitante != null && !colecaoRASolicitante.isEmpty()){
				Iterator iteratorRASolicitante = colecaoRASolicitante.iterator();
				while(iteratorRASolicitante.hasNext()){
					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) iteratorRASolicitante
									.next();
					// caso a colecao só tenha um solicitante então o solicitante será o principal
					if(colecaoRASolicitante.size() == 1){
						registroAtendimentoSolicitante.setIndicadorSolicitantePrincipal(Short.valueOf("1"));
					}else{
						// senão se o id socilitante seja igual ao o id do solicitante que foi
						// escolhido como principal
						if(registroAtendimentoSolicitante.getUltimaAlteracao().getTime() == Long.parseLong(idSolicitantePrincipal)){
							// se for diferente de 1, ou seja se o solicitante não era o principal
							if(registroAtendimentoSolicitante.getIndicadorSolicitantePrincipal() != 1){
								// seta o valor 1 ao indicador principal do solicitante
								registroAtendimentoSolicitante.setIndicadorSolicitantePrincipal(Short.valueOf("1"));
								// verifica se o indicador principal do
								// solicitante que era 1 anteriormente já
								// foi mudado para 2(nesse caso o boolean
								// trocaPrincipal está com o valor true).
								if(trocaPrincipal){
									break;
								}else{
									trocaPrincipal = true;
								}
								// caso o solicitante principal não tenha mudado então sai do loop
							}else{
								break;
							}

						}else{
							// parte que muda o indicador principal do solicitante(que não é mais
							// principal) para 2
							if(registroAtendimentoSolicitante.getIndicadorSolicitantePrincipal() == 1){
								registroAtendimentoSolicitante.setIndicadorSolicitantePrincipal(Short.valueOf("2"));
								if(trocaPrincipal){
									break;
								}else{
									trocaPrincipal = true;
								}
							}
						}
					}
				}
			}else{
				// [FS0021] - Verificar registro atendimento sem solicitante
				throw new ActionServletException("atencao.informar_registro_atendimento_solicitante");
			}
		}

		/*
		 * ==========================================================================================
		 * ============
		 * 
		 * ==========================================================================================
		 * ============
		 */

		// Comentado por Raphael Rossiter
		/*
		 * OrdemServico os = null;
		 * if (sessao.getAttribute("ordemServico") != null) {
		 * os = (OrdemServico) sessao.getAttribute("ordemServico");
		 * }
		 */

		Collection colecaoEnderecoLocalOcorrencia = null;

		if(sessao.getAttribute("colecaoEnderecos") != null){
			colecaoEnderecoLocalOcorrencia = (Collection) sessao.getAttribute("colecaoEnderecos");
		}

		Date ultimaAlteracao = (Date) sessao.getAttribute("ultimaAlteracao");

		// Ids de Serviço Tipo
		Collection<Integer> colecaoIdServicoTipo = null;

		if(sessao.getAttribute("servicoTipo") != null){
			colecaoIdServicoTipo = (ArrayList<Integer>) sessao.getAttribute("servicoTipo");
		}

		if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			form.setDataPrevista(form.getDataPrevista() + " 00:00:00");
		}

		Collection<Conta> colecaoContas = null;
		String identificadores = null;
		ContaMotivoRevisao contaMotivoRevisao = null;

		Map<String, Object> objetosContasParaRevisao = validarColocarContaRevisao(httpServletRequest, sessao, form, fachada, colecaoContas,
						identificadores, contaMotivoRevisao);

		colecaoContas = (Collection<Conta>) objetosContasParaRevisao.get("colecaoContas");
		identificadores = (String) objetosContasParaRevisao.get("identificadores");
		contaMotivoRevisao = (ContaMotivoRevisao) objetosContasParaRevisao.get("contaMotivoRevisao");

		// [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
		// religação ou restabelecimento
		// fachada.verificarExistenciaServicoReligacaoRestabelecimento(Integer.valueOf(idImovel),
		// Integer.valueOf(form.getTipoSolicitacao()));

		// [SB0028] Inclui Registro de Atendimento

		Integer[] idsGerados = fachada.atualizarRegistroAtendimento(Integer.valueOf(form.getNumeroRA()), Short.parseShort(form.getTipo()),
						form.getDataAtendimento(), form.getHora(), form.getTempoEsperaInicial(), form.getTempoEsperaFinal(), Util
										.converterStringParaInteger(form.getMeioSolicitacao()), senhaAtendimento, Util
										.converterStringParaInteger(form.getEspecificacao()), form.getDataPrevista(), form.getObservacao(),
						Util.converterStringParaInteger(form.getIdImovel()), form.getDescricaoLocalOcorrencia(), Util
										.converterStringParaInteger(form.getTipoSolicitacao()), colecaoEnderecoLocalOcorrencia, form
										.getPontoReferencia(), Util.converterStringParaInteger(form.getIdBairroArea()), Util
										.converterStringParaInteger(form.getIdLocalidade()), Util.converterStringParaInteger(form
										.getIdSetorComercial()), Util.converterStringParaInteger(form.getIdQuadra()), Util
										.converterStringParaInteger(form.getIdDivisaoEsgoto()), Util.converterStringParaInteger(form
										.getIdLocalOcorrencia()), Util.converterStringParaInteger(form.getIdPavimentoRua()), Util
										.converterStringParaInteger(form.getIdPavimentoCalcada()), Util.converterStringParaInteger(form
										.getUnidade()), usuario, Util.converterStringParaInteger(form.getIndMatricula()), ultimaAlteracao,
						colecaoRASolicitante, colecaoRASolicitanteRemovida, colecaoIdServicoTipo, (Integer) sessao
.getAttribute("idEspecificacaoBase"),
						colecaoContas, identificadores, contaMotivoRevisao, indicadorProcessoAdmJud, numeroProcessoAgencia);

		// Colocado por Raphael Rossiter em 01/03/2007
		// Montando a pagina de sucesso
		if(!fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))
						&& fachada.gerarOrdemServicoOpcional(Util.converterStringParaInteger(form.getEspecificacao()))
						&& !idEspecificacaoBase.equals(Integer.valueOf(form.getEspecificacao()))){

			montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + form.getNumeroRA()
							+ " atualizado com sucesso.", "Atualizar outro RA - Registro de Atendimento",
							"exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
							"exibirGerarOrdemServicoAction.do?menu=sim&forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
											+ form.getNumeroRA(), "Gerar OS", "Imprimir RA",
							"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento=" + idsGerados[0], null,
							null);
		}else{

			if(fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))
							&& !idEspecificacaoBase.equals(Integer.valueOf(form.getEspecificacao()))){

				montarPaginaSucessoDoisRelatorios(httpServletRequest, "RA - Registro de Atendimento de código " + form.getNumeroRA()
								+ " atualizado com sucesso.", "Atualizar outro RA - Registro de Atendimento",
								"exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
								"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + form.getNumeroRA(), null, "Imprimir RA",
								"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento=" + idsGerados[0],
								"Imprimir OS", "gerarRelatorioOrdemServicoAction.do?idsOS=" + idsGerados[1]);

			}else{

				montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + form.getNumeroRA()
								+ " atualizado com sucesso.", "Atualizar outro RA - Registro de Atendimento",
								"exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
								"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento=" + idsGerados[0],
								"Imprimir RA", "Voltar", "filtrarRegistroAtendimentoAction.do?idRA=" + numeroRA);
			}

		}

		// Comentado por Raphael Rossiter em 01/03/2007
		// Montando a página de sucesso
		/*
		 * if
		 * ((!fachada.gerarOrdemServicoOpcional(Util.converterStringParaInteger(form.getEspecificacao
		 * ())))
		 * &&
		 * fachada.gerarOrdemServicoOpcional(Util.converterStringParaInteger(form.getEspecificacao
		 * ()))
		 * && !(idEspecificacaoBase.equals(Integer.valueOf(form
		 * .getEspecificacao())))) {
		 * montarPaginaSucesso(httpServletRequest,
		 * "RA - Registro de Atendimento de código " + form.getNumeroRA()
		 * + " atualizado com sucesso.",
		 * "Atualizar outro RA - Registro de Atendimento",
		 * "exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
		 * "exibirGerarOrdemServicoAction.do?menu=sim&forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
		 * + form.getNumeroRA(),
		 * "Gerar OS",
		 * "Voltar", "exibirConsultarRegistroAtendimentoAction.do?numeroRA="
		 * + form.getNumeroRA());
		 * } else {
		 * montarPaginaSucesso(httpServletRequest,
		 * "RA - Registro de Atendimento de código " + form.getNumeroRA()
		 * + " atualizado com sucesso.",
		 * "Atualizar outro RA - Registro de Atendimento",
		 * "exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
		 * "exibirConsultarRegistroAtendimentoAction.do?numeroRA="
		 * + form.getNumeroRA(),
		 * "Voltar");
		 * }
		 */

		// remove as coleções da sessão
		sessao.removeAttribute("AtualizarRegistroAtendimentoActionForm");
		sessao.removeAttribute("colecaoMeioSolicitacao");
		sessao.removeAttribute("colecaoSolicitacaoTipo");
		sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");
		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("idEspecificacaoBase");
		sessao.removeAttribute("ultimaAlteracao");
		sessao.removeAttribute("ordemServico");
		sessao.removeAttribute("colecaoDivisaoEsgoto");
		sessao.removeAttribute("colecaoLocalOcorrencia");
		sessao.removeAttribute("colecaoPavimentoRua");
		sessao.removeAttribute("colecaoPavimentoCalcada");
		sessao.removeAttribute("solicitacaoTipoRelativoFaltaAgua");
		sessao.removeAttribute("solicitacaoTipoRelativoAreaEsgoto");
		sessao.removeAttribute("colecaoBairroArea");
		sessao.removeAttribute("habilitarAlteracaoEndereco");
		sessao.removeAttribute("desabilitarMunicipioBairro");
		sessao.removeAttribute("desabilitarDescricaoLocalOcorrencia");
		sessao.removeAttribute("colecaoRASolicitanteRemovidas");
		sessao.removeAttribute("colecaoRASolicitante");
		sessao.removeAttribute("osAutomatica");

		return retorno;
	}

	private void validarCamposEnter(AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest, ActionMapping actionMapping, HttpSession sessao, Usuario usuarioLogado){

		/*
		 * [SB0004] Obté½m e Habilita/Desabilita Dados da Identificação do Local da Ocorrência e
		 * Dados do Solicitante
		 * [FS0019] erificar endereço do imóvel [FS0020] - Verificar existência de registro de
		 * atendimento para o imóvel com a mesma especificação
		 * [FS0053] - Verificar existência de serviço em andamento para solicitações do
		 * tiporeligação ou restabelecimento
		 * [SB0020] Verifica Situação do Imóvel e Especificação
		 */

		// [SB0002] Habilita/Desabilita Município, Bairro, área do Bairro e Divisão de Esgoto
		ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto = fachada.habilitarGeograficoDivisaoEsgoto(Integer
						.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()));

		String idImovel = atualizarRegistroAtendimentoActionForm.getIdImovel();
		String inscricaoImovel = atualizarRegistroAtendimentoActionForm.getInscricaoImovel();
		// caso seja a pesquisa do enter do imóvel ou o indicador de validação de matrícula do
		// imóvel seja 1
		if(idImovel != null && !idImovel.equalsIgnoreCase("") && (inscricaoImovel == null || inscricaoImovel.equals(""))){

			ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
							.obterDadosIdentificacaoLocalOcorrenciaAtualizar(
											Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdImovel()),
											Integer.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()),
											Integer.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()),
											Integer.valueOf(atualizarRegistroAtendimentoActionForm.getNumeroRA()), true, usuarioLogado);

			if(dadosIdentificacaoLocalOcorrencia.getImovel() != null){

				// [FS0020] - Verificar existência de RA - Registro de Atendimento para o imóvel com
				// a
				// mesma especificação
				fachada.verificarExistenciaRAImovelMesmaEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel().getId(), Integer
								.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()));

				// [SB0020] Verifica Situação do imóvel e Especificação
				fachada.verificarSituacaoImovelEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel(), Integer
								.valueOf(atualizarRegistroAtendimentoActionForm.getEspecificacao()));

				atualizarRegistroAtendimentoActionForm.setIdImovel(dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());
				atualizarRegistroAtendimentoActionForm.setInscricaoImovel(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getInscricaoFormatada());
				if(!dadosIdentificacaoLocalOcorrencia.isInformarEndereco()){
					Collection colecaoEnderecos = new ArrayList();
					colecaoEnderecos.add(dadosIdentificacaoLocalOcorrencia.getImovel());
					sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
				}else if(dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo() != null){
					atualizarRegistroAtendimentoActionForm.setDescricaoLocalOcorrencia(dadosIdentificacaoLocalOcorrencia
									.getEnderecoDescritivo());
					sessao.removeAttribute("colecaoEnderecos");
				}else{
					sessao.removeAttribute("colecaoEnderecos");
				}

				this.carregarMunicipioBairroParaImovel(habilitaGeograficoDivisaoEsgoto, dadosIdentificacaoLocalOcorrencia,
								atualizarRegistroAtendimentoActionForm, sessao, fachada);
				atualizarRegistroAtendimentoActionForm.setIdLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel().getLocalidade()
								.getId().toString());
				atualizarRegistroAtendimentoActionForm.setDescricaoLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel().getLocalidade()
								.getDescricao());
				atualizarRegistroAtendimentoActionForm.setIdSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getSetorComercial().getId().toString());
				atualizarRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getSetorComercial().getCodigo()));
				atualizarRegistroAtendimentoActionForm.setDescricaoSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getSetorComercial().getDescricao());
				atualizarRegistroAtendimentoActionForm.setIdQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel().getQuadra()
								.getId()));
				atualizarRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel().getQuadra()
								.getNumeroQuadra()));
			}
		}else if(idImovel != null && !idImovel.equalsIgnoreCase("")){

			// [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
			// religação ou restabelecimento
			fachada.verificarExistenciaServicoReligacaoRestabelecimento(Integer.valueOf(idImovel),
							Integer.valueOf(atualizarRegistroAtendimentoActionForm.getTipoSolicitacao()));

		}



		String idMunicipio = atualizarRegistroAtendimentoActionForm.getIdMunicipio();
		String descricaoMunicipio = atualizarRegistroAtendimentoActionForm.getDescricaoMunicipio();

		if(idMunicipio != null && !idMunicipio.equalsIgnoreCase("") && (descricaoMunicipio == null || descricaoMunicipio.equals(""))){
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, atualizarRegistroAtendimentoActionForm
							.getIdMunicipio()));
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
			if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Município");
			}else{
				Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

				atualizarRegistroAtendimentoActionForm.setIdMunicipio(municipio.getId().toString());
				atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio(municipio.getNome());

				httpServletRequest.setAttribute("nomeCampo", "cdBairro");
			}
		}

		String codigoBairro = atualizarRegistroAtendimentoActionForm.getCdBairro();
		String descricaoBairro = atualizarRegistroAtendimentoActionForm.getDescricaoBairro();

		if(codigoBairro != null && !codigoBairro.equalsIgnoreCase("")){

			if((descricaoBairro == null || descricaoBairro.equals(""))){
				FiltroBairro filtroBairro = new FiltroBairro();
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, atualizarRegistroAtendimentoActionForm
								.getCdBairro()));
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, atualizarRegistroAtendimentoActionForm
								.getIdMunicipio()));
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

				if(colecaoBairro == null || colecaoBairro.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
				}else{
					Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

					atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getCodigo()));
					atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getId()));
					atualizarRegistroAtendimentoActionForm.setDescricaoBairro(bairro.getNome());
					this.pesquisarBairroArea(Integer.valueOf(atualizarRegistroAtendimentoActionForm.getIdBairro()), fachada, sessao);

				}
			}

		}

		String idLocalidade = atualizarRegistroAtendimentoActionForm.getIdLocalidade();
		String descricaoLocalidade = atualizarRegistroAtendimentoActionForm.getDescricaoBairro();

		if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("") && (descricaoLocalidade == null || descricaoLocalidade.equals(""))){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, atualizarRegistroAtendimentoActionForm
							.getIdLocalidade()));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				atualizarRegistroAtendimentoActionForm.setIdLocalidade(localidade.getId().toString());
				atualizarRegistroAtendimentoActionForm.setDescricaoLocalidade(localidade.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

			}
		}

		String cdSetorComercial = atualizarRegistroAtendimentoActionForm.getCdSetorComercial();
		String descricaoSetorComercial = atualizarRegistroAtendimentoActionForm.getDescricaoSetorComercial();

		if(cdSetorComercial != null && !cdSetorComercial.equalsIgnoreCase("")
						&& (descricaoSetorComercial == null || descricaoSetorComercial.equals(""))){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
							atualizarRegistroAtendimentoActionForm.getIdLocalidade()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							atualizarRegistroAtendimentoActionForm.getCdSetorComercial()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");

			}else{
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				atualizarRegistroAtendimentoActionForm.setIdSetorComercial(setorComercial.getId().toString());
				atualizarRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(setorComercial.getCodigo()));
				atualizarRegistroAtendimentoActionForm.setDescricaoSetorComercial(setorComercial.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "nnQuadra");

			}
		}

		String nnQuadra = atualizarRegistroAtendimentoActionForm.getNnQuadra();

		if(nnQuadra != null && !nnQuadra.equalsIgnoreCase("")){
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, atualizarRegistroAtendimentoActionForm
							.getIdSetorComercial()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, atualizarRegistroAtendimentoActionForm
							.getNnQuadra()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());
			if(colecaoQuadra == null || colecaoQuadra.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Quadra");
			}else{
				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				atualizarRegistroAtendimentoActionForm.setIdQuadra(quadra.getId().toString());
				atualizarRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(quadra.getNumeroQuadra()));

				// [SB0006] Obtém Divisão de Esgoto
				DivisaoEsgoto divisaoEsgoto = fachada.obterDivisaoEsgoto(quadra.getId(), habilitaGeograficoDivisaoEsgoto
								.isSolicitacaoTipoRelativoAreaEsgoto());

				if(divisaoEsgoto != null){
					atualizarRegistroAtendimentoActionForm.setIdDivisaoEsgoto(divisaoEsgoto.getId().toString());

					/*
					 * [FS0013] Verificar compatibilidade entre divisão de
					 * esgoto e localidade/setor/quadra [SB0007] Define
					 * Unidade Destino da Divisão de Esgoto
					 */
					this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada, atualizarRegistroAtendimentoActionForm,
									habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

				}

			}
		}
	}

	public void verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(Fachada fachada,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm, boolean solicitacaoTipoRelativoAreaEsgoto){

		fachada.verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(Util
						.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdLocalidade()), Util
						.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdSetorComercial()), Util
						.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdQuadra()), Util
						.converterStringParaInteger(atualizarRegistroAtendimentoActionForm.getIdDivisaoEsgoto()));

	}

	public void pesquisarBairroArea(Integer idBairro, Fachada fachada, HttpSession sessao){

		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID_BAIRRO, idBairro));
		Collection colecaoBairroArea = fachada.pesquisar(filtroBairroArea, BairroArea.class.getName());
		if(colecaoBairroArea == null || colecaoBairroArea.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "BAIRRO_AREA");
		}else{
			sessao.setAttribute("colecaoBairroArea", colecaoBairroArea);
		}
	}

	public void carregarMunicipioBairroParaImovel(ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto,
					ObterDadosIdentificacaoLocalOcorrenciaHelper obterDadosIdentificacaoLocalOcorrenciaHelper,
					AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm, HttpSession sessao, Fachada fachada){

		if(habilitaGeograficoDivisaoEsgoto != null && habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoFaltaAgua()
						&& obterDadosIdentificacaoLocalOcorrenciaHelper.getEnderecoDescritivo() == null){

			atualizarRegistroAtendimentoActionForm.setIdMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getId().toString());
			atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getNome());
			atualizarRegistroAtendimentoActionForm.setIdBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getId().toString());
			atualizarRegistroAtendimentoActionForm.setCdBairro(String.valueOf(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getCodigo()));
			atualizarRegistroAtendimentoActionForm.setDescricaoBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getNome());
			this.pesquisarBairroArea(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro().getBairro().getId(),
							fachada, sessao);

			sessao.setAttribute("desabilitarMunicipioBairro", "OK");

		}else{
			atualizarRegistroAtendimentoActionForm.setIdMunicipio("");
			atualizarRegistroAtendimentoActionForm.setDescricaoMunicipio("");
			atualizarRegistroAtendimentoActionForm.setIdBairro("");
			atualizarRegistroAtendimentoActionForm.setCdBairro("");
			atualizarRegistroAtendimentoActionForm.setDescricaoBairro("");
			sessao.removeAttribute("colecaoBairroArea");
		}
	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param sessao
	 * @param form
	 * @param fachada
	 * @param colecaoContas
	 * @param identificadores
	 * @param contaMotivoRevisao
	 */
	private Map<String, Object> validarColocarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao,
					AtualizarRegistroAtendimentoActionForm form, Fachada fachada, Collection<Conta> colecaoContas, String identificadores,
					ContaMotivoRevisao contaMotivoRevisao){

		Map<String, Object> objetosContasParaRevisao = new TreeMap<String, Object>();

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			// [SB0033] – Verifica se coloca Contas em Revisão.
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
							.valueOf(form.getEspecificacao())));
			filtroSolicitacaoTipoEspecificacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);
			filtroSolicitacaoTipoEspecificacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));

			if(solicitacaoTipoEspecificacao == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Especificação");
			}

			// 1. Caso a especificação informada para o RA tenha indicativo que é para colocar
			// contas em revisão (STEP_ICCOLOCACONTASEMREVISAO da tabela
			// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1))
			if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())
							&& solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao().intValue() == ConstantesSistema.SIM.intValue()){

				verificaEspecificacaoIdImovel(httpServletRequest, sessao, form);

				// 1.1. Caso o imóvel informado tenha conta(s) [FS0047 – Verificar existência de
				// alguma conta].
				SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) sessao
								.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);

				if(selecionarContaRevisaoActionForm != null){

					colecaoContas = selecionarContaRevisaoActionForm.getColecaoContas();
					identificadores = selecionarContaRevisaoActionForm.getContaSelectAuxID();
					contaMotivoRevisao = selecionarContaRevisaoActionForm.getContaMotivoRevisao();

					if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao())
									&& solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao().intValue() == ConstantesSistema.NAO
													.intValue()){

						if(colecaoContas == null || colecaoContas.isEmpty() || Util.isVazioOuBranco(identificadores)){
							throw new ActionServletException("atencao.informe_campo", null, "Contas");
						}

						if(contaMotivoRevisao == null){
							throw new ActionServletException("atencao.informe_campo", null, "Motivo da Revisão");
						}

					}

				}
			}

		}

		objetosContasParaRevisao.put("colecaoContas", colecaoContas);
		objetosContasParaRevisao.put("identificadores", identificadores);
		objetosContasParaRevisao.put("contaMotivoRevisao", contaMotivoRevisao);

		return objetosContasParaRevisao;
	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param sessao
	 * @param inserirRegistroAtendimentoActionForm
	 */
	private void verificaEspecificacaoIdImovel(HttpServletRequest httpServletRequest, HttpSession sessao,
					AtualizarRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) sessao
							.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);

			if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)){
				if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdImovel())){
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())){
						if(!inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getCodigoImovel())){
							throw new ActionServletException("atencao.validacao.colocar.conta.revisao");
						}
					}
				}

				if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getEspecificacao())){
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getEspecificacao())){
						if(!inserirRegistroAtendimentoActionForm.getEspecificacao().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getEspecificacao())){
							throw new ActionServletException("atencao.validacao.colocar.conta.revisao");
						}
					}
				}

			}

		}
	}
}
