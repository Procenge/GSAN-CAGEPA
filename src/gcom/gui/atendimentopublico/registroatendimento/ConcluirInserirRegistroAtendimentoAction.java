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

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosIdentificacaoLocalOcorrenciaHelper;
import gcom.atendimentopublico.registroatendimento.bean.RegistroAtendimentoFaltaAguaGeneralizadaHelper;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade validar as informações das três abas do processo de inserção de um
 * registro de atendimento e chamar o método que irá
 * concluir a mesma
 * 
 * @author Raphael Rossiter
 * @date 25/07/2006
 */
public class ConcluirInserirRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirRegistroAtendimentoActionForm form = (InserirRegistroAtendimentoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// se veio das outras abas
		validarLocalidadeMuniciopio(form, fachada, httpServletRequest);

		boolean incompleto = (httpServletRequest.getParameter("incompleta") != null && !httpServletRequest.getParameter("incompleta")
						.equals("")) ? true : false;

		if(incompleto){
			if(form.getMotivoAtendimentoIncompleto() == null || form.getMotivoAtendimentoIncompleto().equalsIgnoreCase("-1")){
				throw new ActionServletException("atencao.campo.informado", null, "Motivo Atendimento Incompleto");
			}
			this.concluirInserirRegistroAtendimentoIncompleto(form, httpServletRequest, httpServletResponse);

			// Montar a página de sucesso
			this.montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código "
							+ form.getSequenceRA().toString() + " inserido com sucesso.", "Inserir outro RA - Registro de Atendimento",
							"exibirInserirRegistroAtendimentoAction.do?menu=sim", "exibirConsultarRegistroAtendimentoAction.do?numeroRA="
											+ form.getSequenceRA().toString(), "", "",
							"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
											+ form.getSequenceRA().toString());
		}else{

			/*
			 * Validação Aba 01
			 */
			try{
				fachada.validarInserirRegistroAtendimentoDadosGerais(form.getDataAtendimento(), form.getHora(),
								form.getTempoEsperaInicial(), form.getTempoEsperaFinal(), form.getUnidade(),
								form.getNumeroAtendimentoManual(), form.getEspecificacao(), form.getIdRaReiteracao());
			}catch(FachadaException e){
				throw new ActionServletException(
								e.getMessage(),
								"inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&pagina=1",
								e, e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
			}

			if(form.getIdRaReiteracao() != null && !form.getIdRaReiteracao().equalsIgnoreCase("")){
				RegistroAtendimento ra = fachada.pesquisarRegistroAtendimento(Integer.valueOf(form.getIdRaReiteracao()));
				if(ra == null){
					throw new ActionServletException("atencao.registro_atendimento.inexistente");
				}else if(ra.getCodigoSituacao() == RegistroAtendimento.SITUACAO_ENCERRADO){
					throw new ActionServletException("atencao.registro_atendimento.encerrado");
				}else{
					MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
					meioSolicitacao.setId(Integer.valueOf(form.getMeioSolicitacao()));
					ra.setMeioSolicitacao(meioSolicitacao);

					fachada.reiterarRegistroAtendimento(ra, usuario, null);
				}
			}

			/*
			 * Validação Aba 02
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
			String idUnidade = form.getUnidade();
			String descricaoUnidade = form.getDescricaoUnidadeDestino();
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
			String idCliente = form.getIdCliente();
			String idUnidadeDestino = form.getIdUnidadeDestino();

			String clienteTipo = "-1";
			if(!Util.isVazioOuBranco(form.getClienteTipo()) && !form.getClienteTipo().equals("-1")){
				clienteTipo = form.getClienteTipo();
			}

			String especificacao = "";
			if(!Util.isVazioOuBranco(form.getEspecificacao())){
				especificacao = form.getEspecificacao();
			}

			String numeroCpf = "";
			if(!Util.isVazioOuBranco(form.getNumeroCpf())){
				numeroCpf = form.getNumeroCpf();
			}

			String numeroRG = "";
			if(!Util.isVazioOuBranco(form.getNumeroRG())){
				numeroRG = form.getNumeroRG();
			}

			String orgaoExpedidorRg = "-1";
			if(!Util.isVazioOuBranco(form.getOrgaoExpedidorRg())){
				orgaoExpedidorRg = form.getOrgaoExpedidorRg();
			}

			String unidadeFederacaoRG = "-1";
			if(!Util.isVazioOuBranco(form.getUnidadeFederacaoRG())){
				unidadeFederacaoRG = form.getUnidadeFederacaoRG();
			}

			String numeroCnpj = "";
			if(!Util.isVazioOuBranco(form.getNumeroCnpj())){
				numeroCnpj = form.getNumeroCnpj();
			}

			String indicadorProcessoAdmJud = form.getIndicadorProcessoAdmJud();
			String numeroProcessoAgencia = form.getNumeroProcessoAgencia();

			Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

			try{

				fachada.validarCamposObrigatoriosRA_2ABA(idImovel, pontoReferencia, idMunicipio, descricaoMunicipio, cdBairro,
								descricaoBairro, idAreaBairro, idlocalidade, descricaoLocalidade, cdSetorComercial,
								descricaoSetorComercial, numeroQuadra, idDivisaoEsgoto, idUnidade, descricaoUnidade, idLocalOcorrencia,
								idPavimentoRua, idPavimentoCalcada, descricaoLocalOcorrencia, imovelObrigatorio, pavimentoRuaObrigatorio,
								pavimentoCalcadaObrigatorio, solicitacaoTipoRelativoFaltaAgua, solicitacaoTipoRelativoAreaEsgoto,
								desabilitarMunicipioBairro, indRuaLocalOcorrencia, indCalcadaLocalOcorrencia,
								Integer.valueOf(idEspecificacao), null, colecaoEnderecos, idCliente, idUnidadeDestino);

			}catch(FachadaException e){
				throw new ActionServletException(
								e.getMessage(),
								"inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pagina=2",
								e, e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
			}

			// -----------------------------------------------------------------------

			// valida os campos de enter(caso tenha mudado algum valor validar)
			validarCamposEnter(form, fachada, httpServletRequest, actionMapping, sessao, usuario);

			/*
			 * ======================================================================================
			 * ================
			 * 
			 * ======================================================================================
			 * ================
			 */

			/*
			 * Validação Aba 03
			 * ======================================================================
			 * ================================
			 */
			String nomeSolicitante = null;
			if(form.getNomeSolicitante() != null && !form.getNomeSolicitante().equalsIgnoreCase("")){
				nomeSolicitante = form.getNomeSolicitante();
			}

			Collection colecaoEnderecoSolicitante = null;
			if(sessao.getAttribute("colecaoEnderecosAbaSolicitante") != null){
				colecaoEnderecoSolicitante = (Collection) sessao.getAttribute("colecaoEnderecosAbaSolicitante");
			}

			Collection colecaoFone = null;
			if(sessao.getAttribute("colecaoFonesAbaSolicitante") != null){
				colecaoFone = (Collection) sessao.getAttribute("colecaoFonesAbaSolicitante");
			}

			Short indicadorClienteEspecificacao = null;
			if(form.getIndicadorClienteEspecificacao() != null && !form.getIndicadorClienteEspecificacao().equalsIgnoreCase("")){
				indicadorClienteEspecificacao = Short.valueOf(form.getIndicadorClienteEspecificacao());
			}

			try{

				// [FS0030] Verificar preenchimento dos dados de identificação do solicitante
				fachada.verificaDadosSolicitante(Util.converterStringParaInteger(form.getIdCliente()),
								Util.converterStringParaInteger(form.getIdUnidadeSolicitante()),
								Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()), nomeSolicitante,
								colecaoEnderecoSolicitante, colecaoFone, indicadorClienteEspecificacao,
								Util.converterStringParaInteger(form.getIdImovel()), null,
								Util.converterStringParaInteger(form.getEspecificacao()));

			}catch(FachadaException e){
				throw new ActionServletException(
								e.getMessage(),
								"inserirRegistroAtendimentoWizardAction.do?destino=3&action=inserirRegistroAtendimentoDadosLocalOcorrenciaAction",
								e, e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
			}

			Integer idRaReiteracao = null;

			// Ids de Serviço Tipo
			Collection<Integer> colecaoIdServicoTipo = null;

			if(sessao.getAttribute("servicoTipo") != null){
				colecaoIdServicoTipo = (ArrayList<Integer>) sessao.getAttribute("servicoTipo");
			}else{
				Integer idEspecificacaoInt = Integer.valueOf(idEspecificacao);

				if(fachada.gerarOrdemServicoAutomatica(idEspecificacaoInt)){
					colecaoIdServicoTipo = fachada.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(idEspecificacaoInt);
				}
			}

			Collection colecaoEnderecoLocalOcorrencia = null;

			if(sessao.getAttribute("colecaoEnderecos") != null){
				colecaoEnderecoLocalOcorrencia = (Collection) sessao.getAttribute("colecaoEnderecos");
			}

			/*
			 * Raphael Rossiter em 29/05/2007 Caso o usuário tente inserir o mesmo RA por mais de
			 * uma vez
			 */
			String idRAJAGeradoSessao = (String) sessao.getAttribute("idRegistroAtendimento");
			Integer idRAJAGerado = null;

			if(idRAJAGeradoSessao != null){
				idRAJAGerado = Integer.valueOf(idRAJAGeradoSessao);
			}

			String parametroGerarContratoPrestacaoServicoRA = null;

			try{

				// Atenção!!!
				// Essa parâmetro indica o valor do id da solicitação tipo que quando for diferente
				// de -1 , exibirá um link para a emissão de contrato.
				parametroGerarContratoPrestacaoServicoRA = ParametroAtendimentoPublico.P_GERAR_CONTRATO_PRESTACAO_SERVICO_INSERIR_RA
								.executar();
			}catch(ControladorException e){
				e.printStackTrace();
			}

			// [SB0025] Verifica Registro de Atendimento de Falta de água Generalizada
			String efetivarInclusao = httpServletRequest.getParameter("efetivarInclusao");
			
			// Verifica se irá Gerar Guia de Pagamento ou Ordem de Servico
			Boolean flagGerarGuiaPagamento = false;
			// Verificar se o Tipo de Servico é de Pagamento Antecipado
			// sendo o mesmo irá criar uma Guia de Pagamento paa que após o pagamento desta Guia
			// seja criada a ordem de servico
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Util
							.retonarObjetoDeColecao(colecaoIdServicoTipo)));

			ServicoTipo servicoTipoAtual = (ServicoTipo) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroServicoTipo,
							ServicoTipo.class.getName()));

			if(servicoTipoAtual != null && servicoTipoAtual.getIndicadorPagamentoAntecipado() == 1){
				flagGerarGuiaPagamento = true;
			}

			if(efetivarInclusao == null || efetivarInclusao.equalsIgnoreCase("")){

				RegistroAtendimentoFaltaAguaGeneralizadaHelper faltaAguaGeneralizada = fachada
								.verificarRegistroAtendimentoFaltaAguaGeneralizafa(
												Util.converterStringParaInteger(form.getEspecificacao()), Util
																.converterStringParaInteger(form.getIdBairroArea()));

				if(faltaAguaGeneralizada != null){

					httpServletRequest.setAttribute("atencao", "Existem Registros de Atendimento de "
									+ faltaAguaGeneralizada.getSolicitacaoTipoEspecificacao().getDescricao()
									+ " em aberto para a área do bairro " + faltaAguaGeneralizada.getBairroArea().getNome());

					// URL concluir
					httpServletRequest
									.setAttribute("concluir",
													"/gsan/inserirRegistroAtendimentoWizardAction.do?concluir=true&action=inserirRegistroAtendimentoDadosSolicitanteAction&efetivarInclusao=OK");

					// Tipo chamada (Popup ou tela convencional)
					httpServletRequest.setAttribute("tipoChamada", "popup");

					// Label botão utilitário
					httpServletRequest.setAttribute("labelBotao", "Encerrar RA");

					// URL botão utilitário
					httpServletRequest.setAttribute("urlBotao",
									"pesquisarRegistrosAtendimentoPendentesFaltaAguaEncerrarAction.do?idEspecificacao="
													+ form.getEspecificacao() + "&idBairroArea="
													+ faltaAguaGeneralizada.getBairroArea().getId());

					retorno = actionMapping.findForward("telaOpcaoConsultar");
				}else{

					BigDecimal coordenadaNorte = null;
					BigDecimal coordenadaLeste = null;

					if((form.getCoordenadaNorte() != null && !("").equals(form.getCoordenadaNorte()) && Util
									.validarValorNaoNumericoComoBigDecimal(form.getCoordenadaNorte()))
									|| (form.getCoordenadaLeste() != null && !("").equals(form.getCoordenadaLeste()) && Util
													.validarValorNaoNumericoComoBigDecimal(form.getCoordenadaLeste()))){
						throw new ActionServletException("atencao.valor_coordenada_invalido");
					}

					if(form.getCoordenadaNorte() != null && !("").equals(form.getCoordenadaNorte())){
						coordenadaNorte = new BigDecimal(form.getCoordenadaNorte());
					}
					if(form.getCoordenadaLeste() != null && !("").equals(form.getCoordenadaLeste())){
						coordenadaLeste = new BigDecimal(form.getCoordenadaLeste());
					}

					if(form.getIdRaReiteracao() != null && !("").equals(form.getIdRaReiteracao())){
						idRaReiteracao = Integer.valueOf(form.getIdRaReiteracao());
					}
					if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
						form.setDataPrevista(form.getDataPrevista() + " 00:00:00");
					}
					// Verifica os dados do solicitante com relação a rg, cpf e cnpj
					if(!fachada.isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(Integer.valueOf(form.getMeioSolicitacao()))){

						try{

							fachada.verificarDadosSolicitanteRgCpfCnpj(clienteTipo, especificacao, numeroCpf, numeroRG, orgaoExpedidorRg,
											unidadeFederacaoRG, numeroCnpj);
							
						}catch(FachadaException e){
							throw new ActionServletException(
											e.getMessage(),
											"inserirRegistroAtendimentoWizardAction.do?destino=3&action=inserirRegistroAtendimentoDadosLocalOcorrenciaAction",
											e, e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));			
						}						
					}

					Collection<Conta> colecaoContas = null;
					String identificadores = null;
					ContaMotivoRevisao contaMotivoRevisao = null;

					Map<String, Object> objetosContasParaRevisao = validarColocarContaRevisao(httpServletRequest, sessao, form, fachada,
									colecaoContas, identificadores, contaMotivoRevisao);

					colecaoContas = (Collection<Conta>) objetosContasParaRevisao.get("colecaoContas");
					identificadores = (String) objetosContasParaRevisao.get("identificadores");
					contaMotivoRevisao = (ContaMotivoRevisao) objetosContasParaRevisao.get("contaMotivoRevisao");

					Short quantidadePrestacoesGuiaPagamento = null;
					if(form.getQuantidadePrestacoesGuiaPagamento() != null && !form.getQuantidadePrestacoesGuiaPagamento().equals("")){
						quantidadePrestacoesGuiaPagamento = new Short(form.getQuantidadePrestacoesGuiaPagamento());
					}

					// [SB0028] Inclui Registro de Atendimento
					Integer[] idsGerados = fachada.inserirRegistroAtendimento(Short.parseShort(form.getTipo()), form.getDataAtendimento(),
									form.getHora(),
									form.getTempoEsperaInicial(), form.getTempoEsperaFinal(),
									Util.converterStringParaInteger(form.getMeioSolicitacao()),
									Util.converterStringParaInteger(form.getSenhaAtendimento()),
									Util.converterStringParaInteger(especificacao), form.getDataPrevista(), form.getObservacao(),
									Util.converterStringParaInteger(form.getIdImovel()), form.getDescricaoLocalOcorrencia(),
									Util.converterStringParaInteger(form.getTipoSolicitacao()), colecaoEnderecoLocalOcorrencia,
									form.getPontoReferencia(), Util.converterStringParaInteger(form.getIdBairroArea()),
									Util.converterStringParaInteger(form.getIdLocalidade()),
									Util.converterStringParaInteger(form.getIdSetorComercial()),
									Util.converterStringParaInteger(form.getIdQuadra()),
									Util.converterStringParaInteger(form.getIdDivisaoEsgoto()),
									Util.converterStringParaInteger(form.getIdLocalOcorrencia()),
									Util.converterStringParaInteger(form.getIdPavimentoRua()),
									Util.converterStringParaInteger(form.getIdPavimentoCalcada()),
									Util.converterStringParaInteger(form.getUnidade()), usuario.getId(),
									Util.converterStringParaInteger(form.getIdCliente()), form.getPontoReferenciaSolicitante(),
									form.getNomeSolicitante(), false, Util.converterStringParaInteger(form.getIdUnidadeSolicitante()),
									Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()), colecaoFone,
									colecaoEnderecoSolicitante, Util.converterStringParaInteger(form.getIdUnidadeDestino()),
									form.getParecerUnidadeDestino(), colecaoIdServicoTipo, form.getNumeroAtendimentoManual(), idRAJAGerado,
									coordenadaNorte, coordenadaLeste, Integer.valueOf(form.getSequenceRA()), idRaReiteracao, clienteTipo,
									numeroCpf, numeroRG, orgaoExpedidorRg, unidadeFederacaoRG, numeroCnpj, colecaoContas, identificadores,
									contaMotivoRevisao, indicadorProcessoAdmJud, numeroProcessoAgencia, quantidadePrestacoesGuiaPagamento);

					// sessao.setAttribute("idRegistroAtendimento", idsGerados[0].toString());

					// Montando a página de sucesso
					if(!fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))
									&& fachada.gerarOrdemServicoOpcional(Util.converterStringParaInteger(form.getEspecificacao()))){

						if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){

						montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + idsGerados[0]
										+ " inserido com sucesso.", "Inserir outro RA - Registro de Atendimento",
										"exibirInserirRegistroAtendimentoAction.do?menu=sim",
										"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
										"Atualizar RA - Registro de Atendimento inserido", "Gerar OS",
										"exibirGerarOrdemServicoAction.do?forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
														+ idsGerados[0], "Imprimir RA",
										"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "gerarRelatorioContratoPrestacaoServicoAction.do",
											"Emitir Contrato de Prestação de Serviço");

							sessao.setAttribute("numeroImovel", form.getIdImovel());

						}else{
							montarPaginaSucessoUmRelatorio(httpServletRequest, "Registro de Atendimento de código " + idsGerados[0]
											+ " inserido com sucesso.", "Inserir outro Registro de Atendimento",
											"exibirInserirRegistroAtendimentoAction.do?menu=sim",
											"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
											"Atualizar Registro de Atendimento inserido", "Gerar OS",
											"exibirGerarOrdemServicoAction.do?forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
															+ idsGerados[0], "Imprimir RA",
											"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
														+ idsGerados[0]);

						}

					}else{

						if(fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))){
							String msgInseridaComSucesso = "";
							String msgImprimirComSucesso = "";
							String msgTipoInserido = "";

							if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){
								if(flagGerarGuiaPagamento){
									msgTipoInserido = "Guia de Pagamento";
									msgInseridaComSucesso = " e Guia de Pagamento de código " + idsGerados[1]
													+ ". Após o pagamento da guia no vencimento, será gerado o Ordem de serviço";
								}else{
									msgTipoInserido = "OS";
									msgInseridaComSucesso = " e Ordem de Serviço de código " + idsGerados[1];
								}

								if(flagGerarGuiaPagamento){
									msgImprimirComSucesso = "exibirAtualizarGuiaPagamentoAction.do?manter=sim&idRegistroAtualizacao="
													+ idsGerados[1];
								}else{
									msgImprimirComSucesso = "gerarRelatorioOrdemServicoAction.do?idsOS=" + idsGerados[1];

								}

								montarPaginaSucessoDoisRelatorios(httpServletRequest, "RA - Registro de Atendimento de código "
												+ idsGerados[0] + msgInseridaComSucesso + " inseridos com sucesso.",
											"Inserir outro RA - Registro de Atendimento",
											"exibirInserirRegistroAtendimentoAction.do?menu=sim",
											"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
											"Atualizar RA - Registro de Atendimento inserido", "Imprimir RA",
											"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
																+ idsGerados[0], "Imprimir " + msgTipoInserido, msgImprimirComSucesso,
												"gerarRelatorioContratoPrestacaoServicoAction.do",
												"Emitir Contrato de Prestação de Serviço");
							}else{
								if(flagGerarGuiaPagamento){
									msgTipoInserido = "Guia de Pagamento";
									msgInseridaComSucesso = " e Guia de Pagamento de código " + idsGerados[1]
													+ ". Após o pagamento da guia no vencimento, será gerado o Ordem de serviço";
								}else{
									msgTipoInserido = "OS";
									msgInseridaComSucesso = " e Ordem de Serviço de código " + idsGerados[1];
								}

								if(flagGerarGuiaPagamento){
									msgImprimirComSucesso = "exibirAtualizarGuiaPagamentoAction.do?manter=sim&idRegistroAtualizacao="
													+ idsGerados[1];
								}else{
									msgImprimirComSucesso = "gerarRelatorioOrdemServicoAction.do?idsOS=" + idsGerados[1];

								}

								montarPaginaSucessoDoisRelatorios(httpServletRequest, "RA - Registro de Atendimento de código "
												+ idsGerados[0] + msgInseridaComSucesso
												+ " inseridos com sucesso.", "Inserir outro RA - Registro de Atendimento",
												"exibirInserirRegistroAtendimentoAction.do?menu=sim",
												"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
												"Atualizar RA - Registro de Atendimento inserido", "Imprimir RA",
												"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
																+ idsGerados[0], "Imprimir " + msgTipoInserido, msgImprimirComSucesso);
							}

						}else{
							// montando form para opção de exclusao de RA. Este form sera usado caso
							// o usuario clique na opçao "encerrar RA"

							if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){

							montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + idsGerados[0]
											+ " inserido com sucesso.", "Inserir outro RA - Registro de Atendimento",
											"exibirInserirRegistroAtendimentoAction.do?menu=sim",
											"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
											"Atualizar RA - Registro de Atendimento inserido", "Imprimir RA",
											"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "Encerrar RA Imediata",
											"encerrarRegistroAtendimentoAction.do?idRegistroAtendimento=" + idsGerados[0]
															+ "&veioDeInserir=true&dataAtendimento=" + form.getDataAtendimento()
																+ "&horaAtendimento=" + form.getHora(),
												"gerarRelatorioContratoPrestacaoServicoAction.do",
												"Emitir Contrato de Prestação de Serviço");

								sessao.setAttribute("numeroImovel", form.getIdImovel());

							}else{
								montarPaginaSucessoUmRelatorio(httpServletRequest, "Registro de Atendimento de código " + idsGerados[0]
											+ " inserido com sucesso.", "Inserir outro Registro de Atendimento",
											"exibirInserirRegistroAtendimentoAction.do?menu=sim",
											"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
											"Atualizar Registro de Atendimento inserido", "Imprimir RA",
											"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "Encerrar RA Imediata",
											"encerrarRegistroAtendimentoAction.do?idRegistroAtendimento=" + idsGerados[0]
															+ "&veioDeInserir=true&dataAtendimento=" + form.getDataAtendimento()
															+ "&horaAtendimento=" + form.getHora());
						}
						}

					}
				}
				
			}else{

				BigDecimal coordenadaNorte = null;
				BigDecimal coordenadaLeste = null;

				if(Util.validarValorNaoNumericoComoBigDecimal(form.getCoordenadaNorte())
								|| Util.validarValorNaoNumericoComoBigDecimal(form.getCoordenadaLeste())){
					throw new ActionServletException("atencao.valor_coordenada_invalido");
				}else{
					coordenadaNorte = new BigDecimal(form.getCoordenadaNorte());
					coordenadaLeste = new BigDecimal(form.getCoordenadaLeste());
				}
				// Verifica os dados do solicitante com relação a rg, cpf e cnpj
				try{

					fachada.verificarDadosSolicitanteRgCpfCnpj(clienteTipo, especificacao, numeroCpf, numeroRG, orgaoExpedidorRg,
									unidadeFederacaoRG, numeroCnpj);

				}catch(FachadaException e){
					throw new ActionServletException(
									e.getMessage(),
									"inserirRegistroAtendimentoWizardAction.do?destino=3&action=inserirRegistroAtendimentoDadosLocalOcorrenciaAction",
									e, e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
				}

				Collection<Conta> colecaoContas = null;
				String identificadores = null;
				ContaMotivoRevisao contaMotivoRevisao = null;

				Map<String, Object> objetosContasParaRevisao = validarColocarContaRevisao(httpServletRequest, sessao, form, fachada,
								colecaoContas, identificadores, contaMotivoRevisao);

				colecaoContas = (Collection<Conta>) objetosContasParaRevisao.get("colecaoContas");
				identificadores = (String) objetosContasParaRevisao.get("identificadores");
				contaMotivoRevisao = (ContaMotivoRevisao) objetosContasParaRevisao.get("contaMotivoRevisao");

				Short quantidadePrestacoesGuiaPagamento = null;
				if(form.getQuantidadePrestacoesGuiaPagamento() != null && !form.getQuantidadePrestacoesGuiaPagamento().equals("")){
					quantidadePrestacoesGuiaPagamento = new Short(form.getQuantidadePrestacoesGuiaPagamento());
				}

				// [SB0028] Inclui Registro de Atendimento
				Integer[] idsGerados = fachada.inserirRegistroAtendimento(Short.parseShort(form.getTipo()), form.getDataAtendimento(),
								form.getHora(),
								form.getTempoEsperaInicial(), form.getTempoEsperaFinal(),
								Util.converterStringParaInteger(form.getMeioSolicitacao()),
								Util.converterStringParaInteger(form.getSenhaAtendimento()),
								Util.converterStringParaInteger(form.getEspecificacao()), form.getDataPrevista(), form.getObservacao(),
								Util.converterStringParaInteger(form.getIdImovel()), form.getDescricaoLocalOcorrencia(),
								Util.converterStringParaInteger(form.getTipoSolicitacao()), colecaoEnderecoLocalOcorrencia,
								form.getPontoReferencia(), Util.converterStringParaInteger(form.getIdBairroArea()),
								Util.converterStringParaInteger(form.getIdLocalidade()),
								Util.converterStringParaInteger(form.getIdSetorComercial()),
								Util.converterStringParaInteger(form.getIdQuadra()),
								Util.converterStringParaInteger(form.getIdDivisaoEsgoto()),
								Util.converterStringParaInteger(form.getIdLocalOcorrencia()),
								Util.converterStringParaInteger(form.getIdPavimentoRua()),
								Util.converterStringParaInteger(form.getIdPavimentoCalcada()),
								Util.converterStringParaInteger(form.getUnidade()), usuario.getId(),
								Util.converterStringParaInteger(form.getIdCliente()), form.getPontoReferenciaSolicitante(),
								form.getNomeSolicitante(), false, Util.converterStringParaInteger(form.getIdUnidadeSolicitante()),
								Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()), colecaoFone,
								colecaoEnderecoSolicitante, Util.converterStringParaInteger(form.getIdUnidadeDestino()),
								form.getParecerUnidadeDestino(), colecaoIdServicoTipo, form.getNumeroAtendimentoManual(), idRAJAGerado,
								coordenadaNorte, coordenadaLeste, Integer.valueOf(form.getSequenceRA()), idRaReiteracao,
								form.getClienteTipo(), form.getNumeroCpf(), form.getNumeroRG(), form.getOrgaoExpedidorRg(),
								form.getUnidadeFederacaoRG(), form.getNumeroCnpj(), colecaoContas, identificadores, contaMotivoRevisao,
								indicadorProcessoAdmJud, numeroProcessoAgencia, quantidadePrestacoesGuiaPagamento);

				// sessao.setAttribute("idRegistroAtendimento", idsGerados[0].toString());

				// Montando a pagina de sucesso
				if(!fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))
								&& fachada.gerarOrdemServicoOpcional(Util.converterStringParaInteger(form.getEspecificacao()))){

					if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){

						montarPaginaSucessoUmRelatorio(httpServletRequest, "Registro de Atendimento de código " + idsGerados[0]
										+ " inserido com sucesso.", "Inserir outro Registro de Atendimento",
										"exibirInserirRegistroAtendimentoAction.do?menu=sim",
										"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
										"Atualizar Registro de Atendimento inserido", "Gerar OS",
										"exibirGerarOrdemServicoAction.do?forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
														+ idsGerados[0], "Imprimir RA",
										"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
														+ idsGerados[0], "gerarRelatorioContratoPrestacaoServicoAction.do",
										"Emitir Contrato de Prestação de Serviço");

						sessao.setAttribute("numeroImovel", form.getIdImovel());

					}else{
					montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + idsGerados[0]
									+ " inserido com sucesso.", "Inserir outro RA - Registro de Atendimento",
									"exibirInserirRegistroAtendimentoAction.do?menu=sim",
									"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
									"Atualizar RA - Registro de Atendimento inserido", "Gerar OS",
									"exibirGerarOrdemServicoAction.do?forward=exibirGerarOrdemServico&veioRA=OK&idRegistroAtendimento="
													+ idsGerados[0], "Imprimir RA",
									"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento=" + idsGerados[0]);
					}
				}else{

					if(fachada.gerarOrdemServicoAutomatica(Util.converterStringParaInteger(form.getEspecificacao()))){
						String msgInseridaComSucesso = "";
						String msgImprimirComSucesso = "";
						String msgTipoInserido = "";

						if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){
							if(flagGerarGuiaPagamento){
								msgTipoInserido = "Guia de Pagamento";
								msgInseridaComSucesso = " e Guia de Pagamento de código " + idsGerados[1]
												+ ". Após o pagamento da guia no vencimento, será gerado o Ordem de serviço";
							}else{
								msgTipoInserido = "OS";
								msgInseridaComSucesso = " e Ordem de Serviço de código " + idsGerados[1];
							}

							if(flagGerarGuiaPagamento){
								msgImprimirComSucesso = "exibirAtualizarGuiaPagamentoAction.do?manter=sim&idRegistroAtualizacao="
												+ idsGerados[1];
							}else{
								msgImprimirComSucesso = "gerarRelatorioOrdemServicoAction.do?idsOS=" + idsGerados[1];

							}

						montarPaginaSucessoDoisRelatorios(httpServletRequest, "RA - Registro de Atendimento de código " + idsGerados[0]
											+ msgInseridaComSucesso + " inseridos com sucesso.",
										"Inserir outro RA - Registro de Atendimento", "exibirInserirRegistroAtendimentoAction.do?menu=sim",
										"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
										"Atualizar RA - Registro de Atendimento inserido", "Imprimir RA",
										"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "Imprimir " + msgTipoInserido, msgImprimirComSucesso,
											"Emitir Contrato de Prestação de Serviço",
											"gerarRelatorioContratoPrestacaoServicoAction.do");

							sessao.setAttribute("numeroImovel", form.getIdImovel());

						}else{
							if(flagGerarGuiaPagamento){
								msgTipoInserido = "Guia de Pagamento";
								msgInseridaComSucesso = " e Guia de Pagamento de código " + idsGerados[1]
												+ ". Após o pagamento da guia no vencimento, será gerado o Ordem de serviço";
							}else{
								msgTipoInserido = "OS";
								msgInseridaComSucesso = " e Ordem de Serviço de código " + idsGerados[1];
							}

							if(flagGerarGuiaPagamento){
								msgImprimirComSucesso = "exibirAtualizarGuiaPagamentoAction.do?manter=sim&idRegistroAtualizacao="
												+ idsGerados[1];
							}else{
								msgImprimirComSucesso = "gerarRelatorioOrdemServicoAction.do?idsOS=" + idsGerados[1];

							}

						montarPaginaSucessoDoisRelatorios(httpServletRequest, "Registro de Atendimento de código " + idsGerados[0]
											+ msgInseridaComSucesso + " inseridos com sucesso.",
										"Inserir outro Registro de Atendimento", "exibirInserirRegistroAtendimentoAction.do?menu=sim",
										"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0],
										"Atualizar Registro de Atendimento inserido", "Imprimir RA",
										"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "Imprimir " + msgTipoInserido, msgImprimirComSucesso);
						}
					}else{

						if(form.getTipoSolicitacao().equals(parametroGerarContratoPrestacaoServicoRA)){

							montarPaginaSucessoUmRelatorio(httpServletRequest, "Registro de Atendimento de código " + idsGerados[0]
											+ " inserido com sucesso.", "Inserir outro Registro de Atendimento",
											"exibirInserirRegistroAtendimentoAction.do?menu=sim",
											"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0], "Imprimir RA",
											"Atualizar Registro de Atendimento inserido",
											"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
															+ idsGerados[0], "gerarRelatorioContratoPrestacaoServicoAction.do",
											"Emitir Contrato de Prestação de Serviço");

							sessao.setAttribute("numeroImovel", form.getIdImovel());

						}else{
						montarPaginaSucessoUmRelatorio(httpServletRequest, "RA - Registro de Atendimento de código " + idsGerados[0]
										+ " inserido com sucesso.", "Inserir outro RA - Registro de Atendimento",
										"exibirInserirRegistroAtendimentoAction.do?menu=sim",
										"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + idsGerados[0], "Imprimir RA",
										"Atualizar RA - Registro de Atendimento inserido",
										"gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do?idRegistroAtendimento="
														+ idsGerados[0]);
					}
				}

			}
		}
		}

		return retorno;
	}
	
	/**
	 * @author isilva
	 * @param inserirRegistroAtendimentoActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	@SuppressWarnings("unchecked")
	private void validarLocalidadeMuniciopio(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
			if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdLocalidade())
							&& !Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdMunicipio())){

				Integer idMunicipio = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdMunicipio());
				Integer idLocalidade = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdLocalidade());

				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Municipio> colecaoMunicipio = (ArrayList<Municipio>) fachada.pesquisar(filtroMunicipio, Municipio.class
								.getName());

				if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Município");
				}

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection<Localidade> colecaoLocalidade = (ArrayList<Localidade>) fachada.pesquisar(filtroLocalidade, Localidade.class
								.getName());

				if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
				}

				if(!fachada.existeVinculoLocalidadeMunicipio(Integer.valueOf(idLocalidade), Integer.valueOf(idMunicipio))){
					throw new ActionServletException("atencao.localidade.nao.esta.municipio.informado");
				}
			}
		}
	}

	private void validarCamposEnter(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest, ActionMapping actionMapping, HttpSession sessao, Usuario usuario){

		/*
		 * [SB0004] Obtém e Habilita/Desabilita Dados da Identificação do Local da Ocorrência e
		 * Dados do Solicitante [FS0019] Verificar endereço do
		 * imóvel [FS0020] - Verificar existência de registro de atendimento para o imóvel com a
		 * mesma especificação
		 * [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
		 * religação ou restabelecimento
		 * [SB0020] Verifica Situação do
		 * Imóvel e Especificação
		 */

		// [SB0002] Habilita/Desabilita Município, Bairro, área do Bairro e Divisão de Esgoto
		ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto = fachada.habilitarGeograficoDivisaoEsgoto(Integer
						.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()));

		String idImovel = inserirRegistroAtendimentoActionForm.getIdImovel();
		String inscricaoImovel = inserirRegistroAtendimentoActionForm.getInscricaoImovel();

		// caso seja a pesquisa do enter do imóvel ou o indicador de
		// validação de matrícula do imóvel seja 1
		if(idImovel != null && !idImovel.equalsIgnoreCase("") && (inscricaoImovel == null || inscricaoImovel.equals(""))){
			ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
							.obterDadosIdentificacaoLocalOcorrencia(Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdImovel()),
											Integer.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()),
											Integer.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()), true, usuario);

			if(dadosIdentificacaoLocalOcorrencia.getImovel() != null){

				// [FS0020] - Verificar existência de registro de atendimento para o imóvel com a
				// mesma especificação
				fachada.verificarExistenciaRAImovelMesmaEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel().getId(), Integer
								.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()));

				// [SB0020] Verifica Situação do Imóvel e Especificação
				fachada.verificarSituacaoImovelEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel(), Integer
								.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()));

				inserirRegistroAtendimentoActionForm.setIdImovel(dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());

				inserirRegistroAtendimentoActionForm.setInscricaoImovel(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getInscricaoFormatada());

				if(!dadosIdentificacaoLocalOcorrencia.isInformarEndereco()){
					Collection colecaoEnderecos = new ArrayList();
					colecaoEnderecos.add(dadosIdentificacaoLocalOcorrencia.getImovel());
					sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
				}else if(dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo() != null){
					inserirRegistroAtendimentoActionForm.setDescricaoLocalOcorrencia(dadosIdentificacaoLocalOcorrencia
									.getEnderecoDescritivo());
					sessao.removeAttribute("colecaoEnderecos");
				}else{
					sessao.removeAttribute("colecaoEnderecos");
				}

				this.carregarMunicipioBairroParaImovel(habilitaGeograficoDivisaoEsgoto, dadosIdentificacaoLocalOcorrencia,
								inserirRegistroAtendimentoActionForm, sessao, fachada);
				inserirRegistroAtendimentoActionForm.setIdLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel().getLocalidade().getId()
								.toString());
				inserirRegistroAtendimentoActionForm.setDescricaoLocalidade(dadosIdentificacaoLocalOcorrencia.getImovel().getLocalidade()
								.getDescricao());
				inserirRegistroAtendimentoActionForm.setIdSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel().getSetorComercial()
								.getId().toString());
				inserirRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getSetorComercial().getCodigo()));
				inserirRegistroAtendimentoActionForm.setDescricaoSetorComercial(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getSetorComercial().getDescricao());
				inserirRegistroAtendimentoActionForm.setIdQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel().getQuadra()
								.getId()));
				inserirRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(dadosIdentificacaoLocalOcorrencia.getImovel().getQuadra()
								.getNumeroQuadra()));
			}
		}else if(idImovel != null && !idImovel.equalsIgnoreCase("")){

			// [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
			// religação ou restabelecimento
			fachada.verificarExistenciaServicoReligacaoRestabelecimento(Integer.valueOf(idImovel),
							Integer.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()));

		}

		String idMunicipio = inserirRegistroAtendimentoActionForm.getIdMunicipio();
		String descricaoMunicipio = inserirRegistroAtendimentoActionForm.getDescricaoMunicipio();

		if(idMunicipio != null && !idMunicipio.equalsIgnoreCase("") && (descricaoMunicipio == null || descricaoMunicipio.equals(""))){
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, inserirRegistroAtendimentoActionForm
							.getIdMunicipio()));
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
			if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Município");
			}else{
				Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
				inserirRegistroAtendimentoActionForm.setIdMunicipio(municipio.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(municipio.getNome());
				httpServletRequest.setAttribute("nomeCampo", "cdBairro");
			}
		}
		String codigoBairro = inserirRegistroAtendimentoActionForm.getCdBairro();
		String descricaoBairro = inserirRegistroAtendimentoActionForm.getDescricaoBairro();
		if(codigoBairro != null && !codigoBairro.equalsIgnoreCase("")){
			if((descricaoBairro == null || descricaoBairro.equals(""))){
				FiltroBairro filtroBairro = new FiltroBairro();
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, inserirRegistroAtendimentoActionForm
								.getCdBairro()));
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, inserirRegistroAtendimentoActionForm
								.getIdMunicipio()));
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
				if(colecaoBairro == null || colecaoBairro.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
				}else{
					Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);
					inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getCodigo()));
					inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getId()));
					inserirRegistroAtendimentoActionForm.setDescricaoBairro(bairro.getNome());
					this.pesquisarBairroArea(Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdBairro()), fachada, sessao);
				}
			}
		}
		String idLocalidade = inserirRegistroAtendimentoActionForm.getIdLocalidade();
		String descricaoLocalidade = inserirRegistroAtendimentoActionForm.getDescricaoBairro();

		if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("") && (descricaoLocalidade == null || descricaoLocalidade.equals(""))){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, inserirRegistroAtendimentoActionForm
							.getIdLocalidade()));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");

			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				inserirRegistroAtendimentoActionForm.setIdLocalidade(localidade.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoLocalidade(localidade.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

			}
		}

		String cdSetorComercial = inserirRegistroAtendimentoActionForm.getCdSetorComercial();
		String descricaoSetorComercial = inserirRegistroAtendimentoActionForm.getDescricaoSetorComercial();

		if(cdSetorComercial != null && !cdSetorComercial.equalsIgnoreCase("")
						&& (descricaoSetorComercial == null || descricaoSetorComercial.equals(""))){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
							inserirRegistroAtendimentoActionForm.getIdLocalidade()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							inserirRegistroAtendimentoActionForm.getCdSetorComercial()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}else{
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
				inserirRegistroAtendimentoActionForm.setIdSetorComercial(setorComercial.getId().toString());
				inserirRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(setorComercial.getCodigo()));
				inserirRegistroAtendimentoActionForm.setDescricaoSetorComercial(setorComercial.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "nnQuadra");
			}
		}

		String nnQuadra = inserirRegistroAtendimentoActionForm.getNnQuadra();

		if(nnQuadra != null && !nnQuadra.equalsIgnoreCase("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, inserirRegistroAtendimentoActionForm
							.getIdSetorComercial()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, inserirRegistroAtendimentoActionForm
							.getNnQuadra()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Quadra");

			}else{
				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				inserirRegistroAtendimentoActionForm.setIdQuadra(quadra.getId().toString());
				inserirRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(quadra.getNumeroQuadra()));

				// [SB0006] Obtém Divisão de Esgoto
				DivisaoEsgoto divisaoEsgoto = fachada.obterDivisaoEsgoto(quadra.getId(), habilitaGeograficoDivisaoEsgoto
								.isSolicitacaoTipoRelativoAreaEsgoto());

				if(divisaoEsgoto != null){
					inserirRegistroAtendimentoActionForm.setIdDivisaoEsgoto(divisaoEsgoto.getId().toString());

					/*
					 * [FS0013] Verificar compatibilidade entre divisão de esgoto e
					 * localidade/setor/quadra [SB0007] Define Unidade Destino da Divisão
					 * de Esgoto
					 */
					this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada, inserirRegistroAtendimentoActionForm,
									habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());
				}
			}
		}
	}

	public void verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(Fachada fachada,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, boolean solicitacaoTipoRelativoAreaEsgoto){

		fachada.verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdLocalidade()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdSetorComercial()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdQuadra()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdDivisaoEsgoto()));

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
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, HttpSession sessao, Fachada fachada){

		if(habilitaGeograficoDivisaoEsgoto != null && habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoFaltaAgua()
						&& obterDadosIdentificacaoLocalOcorrenciaHelper.getEnderecoDescritivo() == null){

			inserirRegistroAtendimentoActionForm.setIdMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getId().toString());

			inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getNome());

			inserirRegistroAtendimentoActionForm.setIdBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro()
							.getBairro().getId().toString());

			inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getCodigo()));

			inserirRegistroAtendimentoActionForm.setDescricaoBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getNome());

			this.pesquisarBairroArea(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro().getBairro().getId(),
							fachada, sessao);

			sessao.setAttribute("desabilitarMunicipioBairro", "OK");

		}else{

			inserirRegistroAtendimentoActionForm.setIdMunicipio("");

			inserirRegistroAtendimentoActionForm.setDescricaoMunicipio("");

			inserirRegistroAtendimentoActionForm.setIdBairro("");

			inserirRegistroAtendimentoActionForm.setCdBairro("");

			inserirRegistroAtendimentoActionForm.setDescricaoBairro("");

			sessao.removeAttribute("colecaoBairroArea");
		}
	}

	public void concluirInserirRegistroAtendimentoIncompleto(InserirRegistroAtendimentoActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		// protocolo
		AtendimentoIncompleto atendimentoIncompleto = new AtendimentoIncompleto();
		Integer codigoRA = Integer.valueOf(actionForm.getSequenceRA());
		atendimentoIncompleto.setId(codigoRA);

		// fone e ddd
		Collection colecaoFones = (Collection) sessao.getAttribute("colecaoFonesAbaSolicitante");

		if(colecaoFones != null && !colecaoFones.isEmpty()){

			Iterator fonePrincipal = colecaoFones.iterator();
			ClienteFone fone = null;

			while(fonePrincipal.hasNext()){

				fone = (ClienteFone) fonePrincipal.next();
				Short ddd = Short.valueOf(fone.getDdd().trim());
				Integer telefone = Integer.parseInt(fone.getTelefone().trim());

				if(fone.getDdd() != null && !fone.getDdd().equals("")){
					atendimentoIncompleto.setDdd(ddd);
				}else{
					atendimentoIncompleto.setDdd(null);
				}

				if(fone.getTelefone() != null && !fone.getTelefone().equals("")){
					atendimentoIncompleto.setNumeroTelefone(telefone);
				}else{
					atendimentoIncompleto.setNumeroTelefone(null);
				}
				break;
			}
		}

		// contato
		atendimentoIncompleto.setNomeContato(actionForm.getNomeContato());

		// observaçao
		atendimentoIncompleto.setDescricaoObservacao(actionForm.getObservacao());

		// retorno chamada
		atendimentoIncompleto.setIndicadorRetornoChamada(Short.valueOf("2"));

		// motivo atendimento incompleto
		if(actionForm.getMotivoAtendimentoIncompleto() != null && !actionForm.getMotivoAtendimentoIncompleto().equals("")){
			AtendimentoIncompletoMotivo atendimentoIncompletoMotivo = new AtendimentoIncompletoMotivo();
			atendimentoIncompletoMotivo.setId(Integer.valueOf(actionForm.getMotivoAtendimentoIncompleto()));
			atendimentoIncompleto.setAtendimentoIncompletoMotivo(atendimentoIncompletoMotivo);
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Motivo Atendimento Incompleto");
		}
		// especificaçao
		if(actionForm.getEspecificacao() != null && !actionForm.getEspecificacao().equals("")){
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setId(Integer.valueOf(actionForm.getEspecificacao()));
			atendimentoIncompleto.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		}

		// cliente
		atendimentoIncompleto.setCliente(null);

		// imovel
		if(actionForm.getIdImovel() != null && !actionForm.getIdImovel().equals("")){
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(actionForm.getIdImovel()));
			atendimentoIncompleto.setImovel(imovel);
		}else{
			atendimentoIncompleto.setImovel(null);
		}

		// RA
		atendimentoIncompleto.setRegistroAtendimento(null);

		// Timestamp Chamada
		atendimentoIncompleto.setDuracaoChamada(new Date());

		// Unidade
		if(actionForm.getUnidade() != null && !actionForm.getUnidade().equals("")){
			UnidadeOrganizacional unidade = new UnidadeOrganizacional();
			unidade.setId(Integer.valueOf(actionForm.getUnidade()));
			atendimentoIncompleto.setAtendimentoUnidadeOrganizacional(unidade);
		}

		// Usuario
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		atendimentoIncompleto.setAtendimentoUsuario(usuario);

		// Unidade Retorno
		atendimentoIncompleto.setRetornoChamadaUnidadeOrganizacional(null);

		// Usuario Retorno
		atendimentoIncompleto.setRetornoChamadaUsuario(null);

		// Timestamp alteracao
		atendimentoIncompleto.setUltimaAlteracao(new Date());

		// RA a reiterar
		fachada.inserir(atendimentoIncompleto);
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
					InserirRegistroAtendimentoActionForm form, Fachada fachada, Collection<Conta> colecaoContas, String identificadores,
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
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

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