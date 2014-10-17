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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0426] Este caso de uso permite reativar um RA
 * 
 * @author Ana Maria
 * @created 17/08/2006
 */
public class ExibirReativarRegistroAtendimentoAction
				extends GcomAction {

	/**
	 * Exibe a Tela para Reativar o RA
	 * 
	 * @author Ana Maria
	 * @date 17/06/2006
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("reativarRegistroAtendimento");
		ReativarRegistroAtendimentoActionForm form = (ReativarRegistroAtendimentoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Reseta Tramita��o
		if(form.getResetarReativar().equalsIgnoreCase("true")){
			form.resetarReativar();
		}

		Integer idRegistroAtendimento = Integer.valueOf(httpServletRequest.getParameter("numeroRA"));

		if(form.getValidaUnidadeAtendimento().equalsIgnoreCase("false") && form.getValidaUnidadeDestino().equalsIgnoreCase("false")){

			if(form.getEspecificacaoId() == null || form.getEspecificacaoId().equals("")){

				// [FS0001] Verificar possibilidade de reativa��o do registro atendimento
				fachada.validaPossibilidadeReativacaoRA(idRegistroAtendimento, usuario.getId());

				ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = fachada
								.obterDadosRegistroAtendimento(idRegistroAtendimento);

				// [FS0002] Verificar exist�ncia de registro de atendimento para o im�vel com a
				// mesma especifica��o
				// [SB0004] Verificar situa��o do im�vel e especifica��o
				if(registroAtendimentoHelper.getRegistroAtendimento().getImovel() != null){
					Integer idSolicitacaoTipoEspecificacao = validaRegistroAtendimento(fachada, registroAtendimentoHelper);
					fachada.verificarSituacaoImovelEspecificacao(registroAtendimentoHelper.getRegistroAtendimento().getImovel(),
									idSolicitacaoTipoEspecificacao);
				}

				// Dados Gerais do Registro de Atendimento
				setDadosRA(form, registroAtendimentoHelper, httpServletRequest);
				setDadosSolicitante(form, registroAtendimentoHelper);
				setDadosEnderecoOcorrencia(form, registroAtendimentoHelper);
				setUnidades(form, registroAtendimentoHelper);

				// Dados da reativa��o
				// Informa a Unidade Atendimento e o Meio de solicita��o
				setUnidadeAtendimentoMeioSolicitacao(form, fachada, usuario);
				// Informa o tipo de Atendimento(on-line)
				form.setTipoAtendimento("1");

				// Carrega Meio de Solicita��o
				carregarMeioSolicitacao(httpServletRequest, fachada, sessao);

				/**
				 * Os [SB0001], [SB0002], [SB0003] Definir Unidade Destino, foram alterados de
				 * acordo com o DI (Adequar Funcionalidade para tratar
				 * nova parametriza��o do tr�mite)
				 */
				sugerirUnidadeDestino(httpServletRequest, form);

				// Carrega Motivo da Reativa��o
				carregaMotivoReativacao(httpServletRequest, fachada, sessao);

				// Identificar tipo de gera��o da ordem de servi�o (AUTOM�TICA, OPCIONAL ou N�O
				// GERAR)
				/*
				 * if (fachada.gerarOrdemServicoAutomatica(Util
				 * .converterStringParaInteger(form.getEspecificacaoId()))) {
				 * sessao.setAttribute("gerarOSAutomativa", "OK");
				 * FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new
				 * FiltroSolicitacaoTipoEspecificacao();
				 * filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade(
				 * "servicoTipo");
				 * filtroSolicitacaoTipoEspecificacao
				 * .adicionarParametro(new ParametroSimples(
				 * FiltroSolicitacaoTipoEspecificacao.ID,
				 * form.getEspecificacaoId()));
				 * filtroSolicitacaoTipoEspecificacao
				 * .adicionarParametro(new ParametroNaoNulo(
				 * FiltroSolicitacaoTipoEspecificacao.SERVICO_TIPO_ID));
				 * filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
				 * FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
				 * ConstantesSistema.INDICADOR_USO_ATIVO));
				 * Collection colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(
				 * filtroSolicitacaoTipoEspecificacao,
				 * SolicitacaoTipoEspecificacao.class.getName());
				 * Integer idServicoTipo = ((SolicitacaoTipoEspecificacao)
				 * colecaoSolicitacaoTipoEspecificacao.iterator().next()).getServicoTipo().getId();
				 * sessao.setAttribute("servicoTipo", idServicoTipo);
				 * } else {
				 * sessao.removeAttribute("gerarOSAutomativa");
				 * sessao.removeAttribute("servicoTipo");
				 * }
				 */
			}

			String definirDataPrevista = httpServletRequest.getParameter("definirDataPrevista");
			String tempoEsperaFinalDesabilitado = httpServletRequest.getParameter("tempoEsperaFinalDesabilitado");

			// [FS003] Verifica data atendimento - Caso a data do atendimento esteja habilitada para
			// preenchimento
			if(definirDataPrevista != null && !definirDataPrevista.equalsIgnoreCase("")){

				String horaAtendimentoReativado = form.getHoraAtendimentoReativado();

				if(Util.isVazioOuBranco(form.getHoraAtendimentoReativado())){

					horaAtendimentoReativado = "00:00";
				}

				Date dataAtendimento = Util.converteStringParaDateHora(form.getDataAtendimentoReativado() + " " + horaAtendimentoReativado
								+ ":00");

				DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper dataPrevistaUnidadeDestino = fachada
								.definirDataPrevistaUnidadeDestinoEspecificacao(dataAtendimento, Integer.valueOf(form.getEspecificacaoId()));

				if(dataPrevistaUnidadeDestino.getDataPrevista() != null){
					if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
						form.setDataPrevistaReativado(Util.formatarData(dataPrevistaUnidadeDestino.getDataPrevista()));
					}else{
						form.setDataPrevistaReativado(Util.formatarData(dataPrevistaUnidadeDestino.getDataPrevista()) + " "
										+ Util.formatarHoraSemSegundos(horaAtendimentoReativado));
					}
				}
				// [FS0006] Verificar tempo de espera final para atendimento
			}else if(tempoEsperaFinalDesabilitado != null && !tempoEsperaFinalDesabilitado.equalsIgnoreCase("")){
				/*
				 * Caso o Tempo de Espera Final esteja desabilitado e o Tempo de
				 * Espera Inicial para Atendimento esteja preenchido, atribuir o
				 * valor correspondente � hora corrente e n�o permitir altera��o
				 */
				this.atribuirHoraCorrenteTempoEsperaFinal(form);
				httpServletRequest.setAttribute("nomeCampo", "unidade");
			}else{
				// [SB0005 � Habilita/Desabilita Dados do Momento do Atendimento]
				habilitacaoDadosMomentoAtendimento(form, httpServletRequest);

				if(form.getTipoAtendimento().equals("1")){
					Date dataAtendimento = Util.converteStringParaDateHora(form.getDataAtendimentoReativado() + " "
									+ form.getHoraAtendimentoReativado() + ":00");
					/*
					 * [SB0001] � Define Data Prevista - (exibir a data prevista calculada no SB0001
					 * e n�o
					 * permitir altera��o).
					 */
					Date dataPrevista = fachada.definirDataPrevistaRA(dataAtendimento, Integer.valueOf(form.getEspecificacaoId()));
					if(dataPrevista != null){
						if(this.getParametroCompanhia(httpServletRequest).toString().equals(
										SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
							form.setDataPrevistaReativado(Util.formatarData(dataPrevista));
						}else{
							form.setDataPrevistaReativado(Util.formatarData(dataPrevista) + " "
											+ Util.formatarHoraSemSegundos(dataPrevista));
						}
					}
					httpServletRequest.setAttribute("nomeCampo", "tempoEsperaInicial");
				}
			}

		}else{
			// Unidade de Atendimento
			if(form.getIdUnidadeAtendimento() != null && !form.getIdUnidadeAtendimento().equals("")){
				getUnidadeAtendimentoEnter(form, fachada, httpServletRequest);
			}
			// Unidade Destino
			if(form.getIdUnidadeDestino() != null && !form.getIdUnidadeDestino().equals("")){
				getUnidadeDestinoEnter(form, fachada, httpServletRequest);
			}
			httpServletRequest.setAttribute("nomeCampo", "tempoEsperaInicial");
		}

		return retorno;
	}

	/**
	 * Recupera Unidade de Atendimento
	 * 
	 * @param form
	 */

	private void getUnidadeDestinoEnter(ReativarRegistroAtendimentoActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		if(form.getValidaUnidadeDestino().equalsIgnoreCase("true")){
			// Filtro para descobrir a unidade destino
			FiltroUnidadeOrganizacional filtroUnidadeDestino = new FiltroUnidadeOrganizacional();

			filtroUnidadeDestino.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form.getIdUnidadeDestino()));

			Collection colecaoUnidadesDestino = fachada.pesquisar(filtroUnidadeDestino, UnidadeOrganizacional.class.getName());

			if(colecaoUnidadesDestino != null && !colecaoUnidadesDestino.isEmpty()){
				UnidadeOrganizacional unidadeDestinoIterator = (UnidadeOrganizacional) colecaoUnidadesDestino.iterator().next();
				// [FS0013] - Verificar possibilidade de encaminhamento para a unidade destino
				fachada.verificaPossibilidadeEncaminhamentoUnidadeDestino(unidadeDestinoIterator);
				form.setIdUnidadeDestino(unidadeDestinoIterator.getId().toString());
				form.setUnidadeDestino(unidadeDestinoIterator.getDescricao());
				httpServletRequest.setAttribute("unidadeDestinoEncontrado", "true");
			}else{
				// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
				httpServletRequest.setAttribute("unidadeDestinoEncontrado", "exception");
				form.setIdUnidadeDestino("");
				form.setUnidadeDestino("Unidade Destino inexistente");
			}
		}
		form.setValidaUnidadeDestino("false");
	}

	/**
	 * Recupera Unidade Destino
	 * 
	 * @param form
	 */

	private void getUnidadeAtendimentoEnter(ReativarRegistroAtendimentoActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(form.getValidaUnidadeAtendimento().equalsIgnoreCase("true")){
			// Filtro para descobrir a unidade atendimento
			FiltroUnidadeOrganizacional filtroUnidadeAtendimento = new FiltroUnidadeOrganizacional();

			filtroUnidadeAtendimento
							.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form.getIdUnidadeAtendimento()));

			Collection colecaoUnidadesAtendimento = fachada.pesquisar(filtroUnidadeAtendimento, UnidadeOrganizacional.class.getName());

			if(colecaoUnidadesAtendimento != null && !colecaoUnidadesAtendimento.isEmpty()){
				UnidadeOrganizacional unidadeAtendimentoIterator = (UnidadeOrganizacional) colecaoUnidadesAtendimento.iterator().next();
				fachada.verificarAutorizacaoUnidadeAberturaRA(unidadeAtendimentoIterator.getId(), false);
				form.setIdUnidadeAtendimento(unidadeAtendimentoIterator.getId().toString());
				form.setUnidadeAtendimento(unidadeAtendimentoIterator.getDescricao());
				httpServletRequest.setAttribute("unidadeAtendimentoEncontrado", "true");
			}else{
				// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
				httpServletRequest.setAttribute("unidadeAtendimentoEncontrado", "exception");
				form.setIdUnidadeAtendimento("");
				form.setUnidadeAtendimento("Unidade Atendimento inexistente");
			}
		}
		form.setValidaUnidadeAtendimento("false");
	}

	/**
	 * Unidade de Atendimento (exibir a tela com a unidade associada ao
	 * usu�rio que estiver efetuando a reativa��o.
	 * Meio de Solicita��o (exibir na tela com o meio de solicita��o
	 * associado � unidade de atendimento)
	 * [FS0009] Verificar autoriza��o da unidade de atendimento para abertura de
	 * registro de atendimento
	 */
	private void setUnidadeAtendimentoMeioSolicitacao(ReativarRegistroAtendimentoActionForm form, Fachada fachada, Usuario usuario){

		UnidadeOrganizacional unidadeOrganizacionalUsuario = fachada.obterUnidadeOrganizacionalAberturaRAAtivoUsuario(usuario.getLogin());

		if(unidadeOrganizacionalUsuario != null){

			form.setIdUnidadeAtendimento(unidadeOrganizacionalUsuario.getId().toString());
			form.setUnidadeAtendimento(unidadeOrganizacionalUsuario.getDescricao());

			if(unidadeOrganizacionalUsuario.getMeioSolicitacao() != null){

				form.setMeioSolicitacao(unidadeOrganizacionalUsuario.getMeioSolicitacao().getId().toString());
			}
		}
	}

	/**
	 * Motivo da Reativa��o - Carregando a cole��o que ir� ficar dispon�vel
	 * para escolha do usu�rio
	 * [FS0003] � Verificar exist�ncia de dados
	 */
	private void carregaMotivoReativacao(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao){

		FiltroRAMotivoReativacao filtroRAMotivoReativacao = new FiltroRAMotivoReativacao();
		filtroRAMotivoReativacao.setCampoOrderBy(FiltroRAMotivoReativacao.DESCRICAO);
		filtroRAMotivoReativacao.adicionarParametro(new ParametroSimples(FiltroRAMotivoReativacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoMotivoReativacao = fachada.pesquisar(filtroRAMotivoReativacao, RaMotivoReativacao.class.getName());

		if(colecaoMotivoReativacao == null || colecaoMotivoReativacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Motide da Reativa��o");
		}

		sessao.setAttribute("colecaoMotivoReativacao", colecaoMotivoReativacao);
	}

	/**
	 * Meio de Solicita��o - Carregando a cole��o que ir� ficar dispon�vel
	 * para escolha do usu�rio
	 * [FS0003] � Verificar exist�ncia de dados
	 */
	private void carregarMeioSolicitacao(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao){

		Collection colecaoMeioSolicitacao = (Collection) httpServletRequest.getAttribute("colecaoMeioSolicitacao");

		if(colecaoMeioSolicitacao == null){

			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao(FiltroMeioSolicitacao.DESCRICAO);

			filtroMeioSolicitacao.setConsultaSemLimites(true);

			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoMeioSolicitacao = fachada.pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());

			if(colecaoMeioSolicitacao == null || colecaoMeioSolicitacao.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "MEIO_SOLICITACAO");
			}else{
				sessao.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
			}
		}
	}

	/**
	 * Os [SB0001], [SB0002], [SB0003] Definir Unidade Destino, foram alterados de acordo com o DI
	 * (Adequar Funcionalidade para tratar nova
	 * parametriza��o do tr�mite)
	 * 
	 * @author isilva
	 * @param httpServletRequest
	 * @param form
	 */
	private void sugerirUnidadeDestino(HttpServletRequest httpServletRequest, ReativarRegistroAtendimentoActionForm form){

		SistemaParametro sistemaParametro = (SistemaParametro) getSessao(httpServletRequest).getAttribute(
						SistemaParametro.SISTEMA_PARAMETRO);

		/*
		 * Caso o sistema deva sugerir a unidade destino para o primeiro encaminhamento
		 * do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na tabela SISTEMA_PARAMETROS)
		 */
		if(sistemaParametro != null && sistemaParametro.getIndicadorSugestaoTramite() != null
						&& sistemaParametro.getIndicadorSugestaoTramite().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
			obterUnidadeOrganizacionalDestinoParaRA(form);
		}
	}

	/**
	 * Obtem a unidade destino de tramita��o, dependendo da unidade original, especifica��o e de
	 * outros campos informados no filtro
	 * 
	 * @author isilva
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	private void obterUnidadeOrganizacionalDestinoParaRA(ReativarRegistroAtendimentoActionForm form){

		EspecificacaoTramite especificacaoTramiteAuxiliar = new EspecificacaoTramite();

		// Adiciona SolicitacaoTipoEspecificacao ao filtro
		if(!Util.isVazioOuBranco(form.getEspecificacaoId())){
			Integer idEspecificacao = Integer.valueOf(form.getEspecificacaoId());
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setId(idEspecificacao);
			especificacaoTramiteAuxiliar.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		}

		// Adiciona UnidadeOrganizacional Origem ao filtro
		if(!Util.isVazioOuBranco(form.getUnidadeAtualId())){
			Integer idUnidadeOrganizacionalOrigem = Integer.valueOf(form.getUnidadeAtualId());
			UnidadeOrganizacional unidadeOrganizacionalOrigem = new UnidadeOrganizacional();
			unidadeOrganizacionalOrigem.setId(idUnidadeOrganizacionalOrigem);
			especificacaoTramiteAuxiliar.setUnidadeOrganizacionalOrigem(unidadeOrganizacionalOrigem);
		}

		if(!Util.isVazioOuBranco(form.getBairroId()) && !Util.isVazioOuBranco(form.getMunicipioId())){
			// Adiciona Bairro e Municipio ao filtro
			// S� considerar se informar os dois campos

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, form.getBairroId()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, form.getMunicipioId()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// obter o id do Bairro
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao((ArrayList<Bairro>) Fachada.getInstancia().pesquisar(filtroBairro,
							Bairro.class.getName()));

			if(bairro == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}else{
				Municipio municipio = new Municipio();
				municipio.setId(Integer.valueOf(form.getMunicipioId()));
				bairro.setMunicipio(municipio);
				especificacaoTramiteAuxiliar.setBairro(bairro);
			}
		}

		// Adiciona Localidade ao filtro
		if(Util.isVazioOuBranco(form.getLocalidadeId()) || Util.isVazioOuBranco(form.getSetorComercialCodigo())){
			if(!Util.isVazioOuBranco(form.getLocalidadeId())){
				// Adiciona Localidade ao filtro

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getLocalidadeId()));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao((ArrayList<Localidade>) Fachada.getInstancia().pesquisar(
								filtroLocalidade, Localidade.class.getName()));

				if(localidade == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
				}else{
					especificacaoTramiteAuxiliar.setLocalidade(localidade);
				}
			}
		}else{

			// Adiciona Localidade e Setor Comercial ao filtro
			// S� considerar se informar os dois campos

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getLocalidadeId()));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao((ArrayList<Localidade>) Fachada.getInstancia().pesquisar(
							filtroLocalidade, Localidade.class.getName()));

			if(localidade == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}else{
				especificacaoTramiteAuxiliar.setLocalidade(localidade);
			}

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer.valueOf(form
							.getSetorComercialCodigo())));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer.valueOf(form
							.getLocalidadeId())));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroSetorComercial, SetorComercial.class.getName()));

			if(setorComercial == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial");
			}else{
				especificacaoTramiteAuxiliar.setSetorComercial(setorComercial);
			}

		}

		// S� pesquisa os ativos
		especificacaoTramiteAuxiliar.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = Fachada.getInstancia().obterUnidadeDestinoPorEspecificacao(
						especificacaoTramiteAuxiliar);

		if(!Util.isVazioOrNulo(colecaoUnidadeOrganizacional)){
			String pSugerirUnidadeComMaisDeUmaEspecTram = null;

			try{
				pSugerirUnidadeComMaisDeUmaEspecTram = (String) ParametroAtendimentoPublico.P_SUGERIR_UNIDADE_ATENDIMENTO_COM_MAIS_DE_UMA_ESPECIFICACAO_TRAMITE
								.executar();
			}catch(ControladorException e){
				throw new ActionServletException("erro.sistema");
			}

			if(colecaoUnidadeOrganizacional.size() > 1 && !Util.isVazioOuBranco(pSugerirUnidadeComMaisDeUmaEspecTram)
							&& pSugerirUnidadeComMaisDeUmaEspecTram.equals(ConstantesSistema.NAO.toString())){
				form.setIdUnidadeDestino("");
				form.setUnidadeDestino("");
			}else{
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
								.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

				form.setIdUnidadeDestino(String.valueOf(unidadeOrganizacional.getId()));
				form.setUnidadeDestino(String.valueOf(unidadeOrganizacional.getDescricao()));
			}
		}
	}

	/**
	 * [FS0002] Verificar exist�ncia de registro de atendimento para o im�vel com a mesma
	 * especifica��o
	 * 
	 * @param fachada
	 * @param registroAtendimentoHelper
	 */
	private Integer validaRegistroAtendimento(Fachada fachada, ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		Integer idImovel = registroAtendimentoHelper.getRegistroAtendimento().getImovel().getId();
		Integer idSolicitacaoTipoEspecificacao = registroAtendimentoHelper.getRegistroAtendimento().getSolicitacaoTipoEspecificacao()
						.getId();
		fachada.verificarExistenciaRAImovelMesmaEspecificacao(idImovel, idSolicitacaoTipoEspecificacao);
		return idSolicitacaoTipoEspecificacao;
	}

	/**
	 * Carrega Unidades (Atendimento e Atual)
	 * 
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setUnidades(ReativarRegistroAtendimentoActionForm form, ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		UnidadeOrganizacional unidadeAtendimento = registroAtendimentoHelper.getUnidadeAtendimento();
		if(unidadeAtendimento != null){
			form.setUnidadeAtendimentoId(unidadeAtendimento.getId().toString());
			form.setUnidadeAtendimentoDescricao(unidadeAtendimento.getDescricao());
		}
		UnidadeOrganizacional unidadeAtual = registroAtendimentoHelper.getUnidadeAtual();
		if(unidadeAtual != null){
			form.setUnidadeAtualId(unidadeAtual.getId().toString());
			form.setUnidadeAtualDescricao(unidadeAtual.getDescricao());
		}
	}

	/**
	 * Carrega Dados do RA
	 * 
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosRA(ReativarRegistroAtendimentoActionForm form, ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper,
					HttpServletRequest httpServletRequest){

		RegistroAtendimento registroAtendimento = registroAtendimentoHelper.getRegistroAtendimento();
		// Dados Gerais do Registro de Atendimento
		form.setNumeroRA(registroAtendimento.getId().toString());
		form.setSituacaoRA(registroAtendimentoHelper.getDescricaoSituacaoRA());
		if(registroAtendimentoHelper.getRAAssociado() != null){
			form.setNumeroRaAssociado(registroAtendimentoHelper.getRAAssociado().getId().toString());
			form.setSituacaoRaAssociado(registroAtendimentoHelper.getDescricaoSituacaoRAAssociado());
		}
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = registroAtendimento.getSolicitacaoTipoEspecificacao();
		if(solicitacaoTipoEspecificacao != null){
			if(solicitacaoTipoEspecificacao.getSolicitacaoTipo() != null){
				form.setTipoSolicitacaoId(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getId().toString());
				form.setTipoSolicitacaoDescricao(solicitacaoTipoEspecificacao.getSolicitacaoTipo().getDescricao());
			}
			form.setEspecificacaoId(solicitacaoTipoEspecificacao.getId().toString());
			form.setEspecificacaoDescricao(solicitacaoTipoEspecificacao.getDescricao());
		}
		if(registroAtendimento.getMeioSolicitacao() != null){
			form.setMeioSolicitacaoId(registroAtendimento.getMeioSolicitacao().getId().toString());
			// reativarRegistroAtendimentoActionForm.setMeioSolicitacao(registroAtendimento.getMeioSolicitacao().getId()+"");
			form.setMeioSolicitacaoDescricao(registroAtendimento.getMeioSolicitacao().getDescricao());
		}

		if(registroAtendimento.getSenhaAtendimento() != null && !registroAtendimento.getSenhaAtendimento().equals("")){
			form.setSenhaAtendimento(registroAtendimento.getSenhaAtendimento().toString());
		}

		// Imovel
		Imovel imovel = registroAtendimento.getImovel();
		if(imovel != null){
			form.setMatriculaImovel(imovel.getId().toString());
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
		}
		Date dataAtendimento = registroAtendimento.getRegistroAtendimento();
		form.setDataAtendimento(Util.formatarData(dataAtendimento));
		form.setHoraAtendimento(Util.formatarHoraSemData(dataAtendimento));
		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
			form.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()));
		}else{
			form.setDataPrevista(Util.formatarData(registroAtendimento.getDataPrevistaAtual()) + " "
							+ Util.formatarHoraSemSegundos(registroAtendimento.getDataPrevistaAtual()));
		}
		// Encerramento
		setDadosEncerramento(form, registroAtendimento);

		// Dados necess�rio p/ inserir o novo RA
		if(registroAtendimento.getLogradouroBairro() != null){
			form.setLogradouroBairro(registroAtendimento.getLogradouroBairro().getId());
		}
		if(registroAtendimento.getLogradouroCep() != null){
			form.setLogradouroCep(registroAtendimento.getLogradouroCep().getId());
		}
		form.setComplementoEndereco(registroAtendimento.getComplementoEndereco());
		if(registroAtendimento.getLocalOcorrencia() != null){
			form.setLocalOcorrencia(registroAtendimento.getLocalOcorrencia().getId());
		}
		if(registroAtendimento.getPavimentoRua() != null){
			form.setPavimentoRua(registroAtendimento.getPavimentoRua().getId());
		}
		if(registroAtendimento.getPavimentoCalcada() != null){
			form.setPavimentoCalcada(registroAtendimento.getPavimentoCalcada().getId());
		}
		form.setDescricaoLocalOcorrencia(registroAtendimento.getDescricaoLocalOcorrencia());
	}

	/**
	 * Carrega Dados do RA
	 * 
	 * @param form
	 * @param registroAtendimento
	 */
	private void setDadosEncerramento(ReativarRegistroAtendimentoActionForm form, RegistroAtendimento registroAtendimento){

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = registroAtendimento.getAtendimentoMotivoEncerramento();
		if(atendimentoMotivoEncerramento != null){
			form.setIdMotivoEncerramento(atendimentoMotivoEncerramento.getId().toString());
			form.setMotivoEncerramento(atendimentoMotivoEncerramento.getDescricao());
			Date dataEncerramento = registroAtendimento.getDataEncerramento();
			form.setDataEncerramento(Util.formatarData(dataEncerramento));
		}
	}

	/**
	 * Carrega Dados do Solicitante
	 * 
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosSolicitante(ReativarRegistroAtendimentoActionForm form,
					ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		// Dados do Solicitante
		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = registroAtendimentoHelper.getSolicitante();
		if(registroAtendimentoSolicitante != null){
			form.setIdRaSolicitante(registroAtendimentoSolicitante.getID());
			Cliente cliente = registroAtendimentoSolicitante.getCliente();
			UnidadeOrganizacional unidadeSolicitante = registroAtendimentoSolicitante.getUnidadeOrganizacional();
			// Caso o principal solicitante do registro de atendimento seja um cliente
			// obter os dados do cliente
			if(cliente != null){
				form.setIdClienteSolicitante(cliente.getId().toString());
				form.setClienteSolicitante(cliente.getNome());
				// Caso o principal solicitante do registro de atendimento seja uma unidade
				// obter os dados da unidade
			}else if(unidadeSolicitante != null){
				form.setIdUnidadeSolicitante(unidadeSolicitante.getId().toString());
				form.setUnidadeSolicitante(unidadeSolicitante.getDescricao());
				// Caso o principal solicitante do registro de atendimento n�o seja um cliente, nem
				// uma unidade
				// obter os dados do solicitante
			}else{
				form.setNomeSolicitante(registroAtendimentoSolicitante.getSolicitante());
			}
		}
	}

	/**
	 * Carrega Dados do Endere�o de Ocorr�ncia
	 * 
	 * @param form
	 * @param registroAtendimentoHelper
	 */
	private void setDadosEnderecoOcorrencia(ReativarRegistroAtendimentoActionForm form,
					ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper){

		String enderecoOcorrencia = registroAtendimentoHelper.getEnderecoOcorrencia();
		form.setEnderecoOcorrencia(enderecoOcorrencia);
		form.setPontoReferencia(registroAtendimentoHelper.getRegistroAtendimento().getPontoReferencia());

		// Caso o registro atendimento esteja associado a uma �rea de bairro,
		// obter os dados da �rea do bairro
		BairroArea bairroArea = registroAtendimentoHelper.getRegistroAtendimento().getBairroArea();
		if(bairroArea != null){
			form.setBairroId(bairroArea.getBairro().getId().toString());
			form.setBairroDescricao(bairroArea.getBairro().getNome());
			form.setAreaBairroId(bairroArea.getId().toString());
			form.setAreaBairroDescricao(bairroArea.getNome());

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, bairroArea.getBairro().getId()));
			filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroBairro, Bairro.class.getName()));

			if(bairro == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}else if(bairro.getMunicipio() == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Munic�pio");
			}else{
				form.setMunicipioId(String.valueOf(bairro.getMunicipio().getId()));
				form.setMunicipioDescricao(bairro.getMunicipio().getNome());
			}

		}
		Localidade localidade = registroAtendimentoHelper.getRegistroAtendimento().getLocalidade();
		if(localidade != null){
			form.setLocalidadeId(localidade.getId().toString());
			form.setLocalidadeDescricao(localidade.getDescricao());
		}
		SetorComercial setorComercial = registroAtendimentoHelper.getRegistroAtendimento().getSetorComercial();
		if(setorComercial != null){
			form.setSetorComercialId(setorComercial.getId().toString());
			form.setSetorComercialCodigo(String.valueOf(setorComercial.getCodigo()));
		}
		Quadra quadra = registroAtendimentoHelper.getRegistroAtendimento().getQuadra();
		if(quadra != null){
			form.setQuadraId(quadra.getId().toString());
			form.setQuadraNumero(String.valueOf(quadra.getNumeroQuadra()));
		}
		DivisaoEsgoto divisaoEsgoto = registroAtendimentoHelper.getRegistroAtendimento().getDivisaoEsgoto();
		if(divisaoEsgoto != null){
			form.setDivisaoEsgotoId(divisaoEsgoto.getId().toString());
			form.setDivisaoEsgotoDescricao(divisaoEsgoto.getDescricao());
		}
	}

	/**
	 * Habilitar ou desabilitar os campos Tempo de Espera para Atendimento, Data
	 * do Atendimento e Hora do Atendimento
	 * [SB0005] Habilita/Desabilita Dados do Momento da Reativa��o
	 * 
	 * @param reativarRegistroAtendimentoActionForm
	 * @return void
	 */
	private void habilitacaoDadosMomentoAtendimento(ReativarRegistroAtendimentoActionForm form, HttpServletRequest httpServletRequest){

		// On-line
		if(form.getTipoAtendimento().equalsIgnoreCase("1")){
			Date dataCorrente = new Date();

			form.setDataAtendimentoReativado(Util.formatarData(dataCorrente));
			form.setHoraAtendimentoReativado(Util.formatarHoraSemSegundos(dataCorrente));
			form.setTempoEsperaInicial("");
			form.setTempoEsperaFinal("");

			httpServletRequest.setAttribute("nomeCampo", "tempoEsperaInicial");
		}
		// Manual
		else{
			form.setDataAtendimentoReativado("");
			form.setHoraAtendimentoReativado("");
			form.setTempoEsperaInicial("");
			form.setTempoEsperaFinal("");
			form.setDataPrevistaReativado("");

			httpServletRequest.setAttribute("nomeCampo", "dataAtendimentoReativado");
		}
	}

	/**
	 * Atribui o valor correspondente � hora corrente
	 * 
	 * @param InserirRegistroAtendimentoActionForm
	 * @return void
	 */
	private void atribuirHoraCorrenteTempoEsperaFinal(ReativarRegistroAtendimentoActionForm form){

		Date dataCorrente = new Date();

		form.setTempoEsperaFinal(Util.formatarHoraSemSegundos(dataCorrente));
	}
}
