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

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosIdentificacaoLocalOcorrenciaHelper;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.integracao.acquagis.DadosAcquaGis;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usuário a tela que receberá os
 * parâmetros para realização da inserção de um R.A (Aba nº 02 - Dados do local
 * de ocorrência)
 * 
 * @author Raphael Rossiter
 * @date 25/07/2006
 */
public class ExibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirRegistroAtendimentoDadosLocalOcorrencia");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", "N");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm = (InserirRegistroAtendimentoActionForm) actionForm;

		controlarFormularioSelecionarContaRevisao(httpServletRequest, sessao, inserirRegistroAtendimentoActionForm);

		/*
		 * Carregamento inicial da tela responsável pelo redebimento das
		 * informações referentes ao local da ocorrência (ABA Nº 02)
		 * ==========================================================================================
		 * ==================
		 */

		try{
			String pIndicadorTramiteRestritoUnidades = ParametroAtendimentoPublico.P_INDICADOR_TRAMITE_RESTRITO_UNIDADES_RESPONSAVEIS
							.executar();

			if(pIndicadorTramiteRestritoUnidades != null && pIndicadorTramiteRestritoUnidades.equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("desabilitarUnidadeDestino", pIndicadorTramiteRestritoUnidades);
			}else{
				sessao.removeAttribute("desabilitarUnidadeDestino");
			}

		}catch(ControladorException e1){

			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_INDICADOR_TRAMITE_RESTRITO_UNIDADES_RESPONSAVEIS");
		}

		/*
		 * Divisão de Esgoto - Carregando a coleção que irá ficar disponível
		 * para escolha do usuário
		 * [FS0003] – Verificar existência de dados
		 */
		Collection colecaoDivisaoEsgoto = (Collection) sessao.getAttribute("colecaoDivisaoEsgoto");

		if(colecaoDivisaoEsgoto == null){

			FiltroDivisaoEsgoto filtroDivisaoEsgoto = new FiltroDivisaoEsgoto(FiltroDivisaoEsgoto.DESCRICAO);
			filtroDivisaoEsgoto.adicionarParametro(new ParametroSimples(FiltroDivisaoEsgoto.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroDivisaoEsgoto.setConsultaSemLimites(true);
			colecaoDivisaoEsgoto = fachada.pesquisar(filtroDivisaoEsgoto, DivisaoEsgoto.class.getName());

			if(colecaoDivisaoEsgoto == null || colecaoDivisaoEsgoto.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "DIVISAO_ESGOTO");
			}else{
				sessao.setAttribute("colecaoDivisaoEsgoto", colecaoDivisaoEsgoto);
			}
		}

		/*
		 * Local de Ocorrência - Carregando a coleção que irá ficar disponível para escolha do
		 * usuário
		 * [FS0003] – Verificar existência de dados
		 */
		Collection colecaoLocalOcorrencia = (Collection) sessao.getAttribute("colecaoLocalOcorrencia");

		if(colecaoLocalOcorrencia == null){

			FiltroLocalOcorrencia filtroLocalOcorrencia = new FiltroLocalOcorrencia(FiltroLocalOcorrencia.DESCRICAO);
			filtroLocalOcorrencia.adicionarParametro(new ParametroSimples(FiltroLocalOcorrencia.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalOcorrencia.setConsultaSemLimites(true);
			colecaoLocalOcorrencia = fachada.pesquisar(filtroLocalOcorrencia, LocalOcorrencia.class.getName());

			if(colecaoLocalOcorrencia == null || colecaoLocalOcorrencia.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "LOCAL_OCORRENCIA");
			}else{
				sessao.setAttribute("colecaoLocalOcorrencia", colecaoLocalOcorrencia);
			}
		}

		/*
		 * Pavimento Rua - Carregando a coleção que irá ficar disponível para escolha do usuário
		 * [FS0003] – Verificar existência de dados
		 */
		Collection colecaoPavimentoRua = (Collection) sessao.getAttribute("colecaoPavimentoRua");

		if(colecaoPavimentoRua == null){

			FiltroPavimentoRua filtroPavimentoRua = new FiltroPavimentoRua(FiltroPavimentoRua.DESCRICAO);
			filtroPavimentoRua.adicionarParametro(new ParametroSimples(FiltroPavimentoRua.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroPavimentoRua.setConsultaSemLimites(true);
			colecaoPavimentoRua = fachada.pesquisar(filtroPavimentoRua, PavimentoRua.class.getName());

			if(colecaoPavimentoRua == null || colecaoPavimentoRua.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "PAVIMENTO_RUA");
			}else{
				sessao.setAttribute("colecaoPavimentoRua", colecaoPavimentoRua);
			}
		}

		/*
		 * Pavimento Calçada - Carregando a coleção que irá ficar disponível para escolha do usuário
		 * [FS0003] – Verificar existência de dados
		 */
		Collection colecaoPavimentoCalcada = (Collection) sessao.getAttribute("colecaoPavimentoCalcada");

		if(colecaoPavimentoCalcada == null){

			FiltroPavimentoCalcada filtroPavimentoCalcada = new FiltroPavimentoCalcada(FiltroPavimentoCalcada.DESCRICAO);
			filtroPavimentoCalcada.adicionarParametro(new ParametroSimples(FiltroPavimentoCalcada.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroPavimentoCalcada.setConsultaSemLimites(true);
			colecaoPavimentoCalcada = fachada.pesquisar(filtroPavimentoCalcada, PavimentoCalcada.class.getName());

			if(colecaoPavimentoCalcada == null || colecaoPavimentoCalcada.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "PAVIMENTO_CALCADA");
			}else{
				sessao.setAttribute("colecaoPavimentoCalcada", colecaoPavimentoCalcada);
			}
		}

		// [SB0002] – Habilita/Desabilita Município, Bairro, Área do Bairro e Divisão de Esgoto
		ObterDadosIdentificacaoLocalOcorrenciaHelper habilitaGeograficoDivisaoEsgoto = fachada.habilitarGeograficoDivisaoEsgoto(Integer
						.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()));
		String pesquisarImovel = httpServletRequest.getParameter("pesquisarImovel");

		if(habilitaGeograficoDivisaoEsgoto != null){
			if(habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoFaltaAgua()){
				sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "SIM");

				// Verificar carregamento do Município e Bairro de acordo com o tipo de solicitação
				if(inserirRegistroAtendimentoActionForm.getIdImovel() != null
								&& !inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase("")
								&& (pesquisarImovel == null || pesquisarImovel.equalsIgnoreCase(""))){

					ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
									.obterDadosIdentificacaoLocalOcorrencia(
													Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdImovel()),
													Integer.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()),
													Integer.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()), false,
													usuarioLogado);

					this.carregarMunicipioBairroParaImovel(habilitaGeograficoDivisaoEsgoto, dadosIdentificacaoLocalOcorrencia,
									inserirRegistroAtendimentoActionForm, sessao, fachada);
				}

			}else{
				sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "NAO");
			}

			if(habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto()){
				sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "SIM");
			}else{
				sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "NAO");
			}
		}else{
			sessao.setAttribute("solicitacaoTipoRelativoFaltaAgua", "SIM");
			sessao.setAttribute("solicitacaoTipoRelativoAreaEsgoto", "SIM");
		}

		/*
		 * Fim do carregamento inicial
		 * 
		 * ==========================================================================================
		 * ==================
		 * 
		 * ==========================================================================================
		 * ==================
		 */

		/*
		 * Pesquisas realizadas a partir do ENTER
		 * 
		 * ==========================================================================================
		 * =================
		 */

		/*
		 * Dados da identificação do local de ocorrência
		 * [FS0019] – Verificar endereço do imóvel
		 */

		if(pesquisarImovel != null && !pesquisarImovel.equalsIgnoreCase("")){

			/*
			 * [SB0004] – Obtém e Habilita/Desabilita Dados da Identificação do
			 * Local da Ocorrência e Dados do Solicitante
			 * [FS0019] – Verificar endereço do imóvel
			 * [FS0020] - Verificar existência de registro de atendimento para o imóvel com a mesma
			 * especificação
			 * [FS0053] - Verificar existência de serviço em andamento para solicitações do tipo
			 * religação ou restabelecimento
			 * [SB0020] – Verifica Situação do Imóvel e Especificação
			 * [SB0021] – Verifica Existência de Registro de Atendimento Pendente para o Imóvel
			 * [SB0032 – Verifica se o imóvel informado tem débito]
			 */
			ObterDadosIdentificacaoLocalOcorrenciaHelper dadosIdentificacaoLocalOcorrencia = fachada
							.obterDadosIdentificacaoLocalOcorrencia(Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdImovel()),
											Integer.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()),
											Integer.valueOf(inserirRegistroAtendimentoActionForm.getTipoSolicitacao()), false,
											usuarioLogado);

			if(dadosIdentificacaoLocalOcorrencia.getImovel() != null){

				boolean msgAlert = false;

				// [SB0021] – Verifica Existência de Registro de Atendimento Pendente para o Imóvel
				boolean raPendente = fachada.verificaExistenciaRAPendenteImovel(dadosIdentificacaoLocalOcorrencia.getImovel().getId());

				if(raPendente){
					httpServletRequest.setAttribute("msgAlert", "Atenção! Existe RA - Registro de Atendimento pendente para o imóvel "
									+ dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());
					msgAlert = true;
				}

				configurarColocarContaRevisao(httpServletRequest, sessao, inserirRegistroAtendimentoActionForm);

				// [SB0020] – Verifica Situação do Imóvel e Especificação
				fachada.verificarSituacaoImovelEspecificacao(dadosIdentificacaoLocalOcorrencia.getImovel(), Integer
								.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()));
				
				
				//GIS
				if(dadosIdentificacaoLocalOcorrencia.getImovel().getCoordenadaX() != null){
					inserirRegistroAtendimentoActionForm.setCoordenadaNorte(dadosIdentificacaoLocalOcorrencia.getImovel().getCoordenadaX().toString());
				}
			
				if(dadosIdentificacaoLocalOcorrencia.getImovel().getCoordenadaY()!= null){
					inserirRegistroAtendimentoActionForm.setCoordenadaLeste(dadosIdentificacaoLocalOcorrencia.getImovel().getCoordenadaY().toString());
				}
				
				
				

				// [SB0032] – Verifica se o imóvel informado tem débito
				Integer idEspecificacao = null;
				Integer idImovel = null;

				if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getEspecificacao())){
					idEspecificacao = Integer.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao());
				}

				if(dadosIdentificacaoLocalOcorrencia.getImovel() != null && dadosIdentificacaoLocalOcorrencia.getImovel().getId() != null){
					idImovel = dadosIdentificacaoLocalOcorrencia.getImovel().getId();
				}

				fachada.verificarDebitosImovelCliente(idEspecificacao, idImovel, null);

				inserirRegistroAtendimentoActionForm.setIdImovel(dadosIdentificacaoLocalOcorrencia.getImovel().getId().toString());
				inserirRegistroAtendimentoActionForm.setInscricaoImovel(dadosIdentificacaoLocalOcorrencia.getImovel()
								.getInscricaoFormatada());

				if(!dadosIdentificacaoLocalOcorrencia.isInformarEndereco()){
					Collection colecaoEnderecos = new ArrayList();
					colecaoEnderecos.add(dadosIdentificacaoLocalOcorrencia.getImovel());
					sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
					sessao.setAttribute("enderecoPertenceImovel", "OK");
				}else if(dadosIdentificacaoLocalOcorrencia.getEnderecoDescritivo() != null){
					inserirRegistroAtendimentoActionForm.setDescricaoLocalOcorrencia(dadosIdentificacaoLocalOcorrencia
									.getEnderecoDescritivo());

					if(msgAlert){
						httpServletRequest.setAttribute("msgAlert2",
														"O RA - Registro de Atendimento ficará bloqueado até que seja informado o logradouro para o imóvel");
					}else{
						httpServletRequest.setAttribute("msgAlert",
														"O RA - Registro de Atendimento ficará bloqueado até que seja informado o logradouro para o imóvel");
					}

					sessao.removeAttribute("colecaoEnderecos");
					sessao.removeAttribute("enderecoPertenceImovel");
				}else{
					sessao.removeAttribute("colecaoEnderecos");
					sessao.removeAttribute("enderecoPertenceImovel");
				}

				if(dadosIdentificacaoLocalOcorrencia.isHabilitarAlteracaoEndereco()){
					sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
				}else{
					sessao.setAttribute("habilitarAlteracaoEndereco", "NAO");
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

				// [SB0006] – Obtém Divisão de Esgoto
				DivisaoEsgoto divisaoEsgoto = fachada.obterDivisaoEsgoto(dadosIdentificacaoLocalOcorrencia.getImovel().getQuadra().getId(),
								habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

				if(divisaoEsgoto != null){
					inserirRegistroAtendimentoActionForm.setIdDivisaoEsgoto(divisaoEsgoto.getId().toString());
					sessao.setAttribute("desabilitarDivisaoEsgoto", "OK");
				}else{
					sessao.removeAttribute("desabilitarDivisaoEsgoto");
				}

				if(dadosIdentificacaoLocalOcorrencia.getImovel().getPavimentoRua() != null){
					inserirRegistroAtendimentoActionForm.setIdPavimentoRua(dadosIdentificacaoLocalOcorrencia.getImovel().getPavimentoRua()
									.getId().toString());
					sessao.setAttribute("desabilitarPavimentoRua", "OK");
				}else{
					sessao.removeAttribute("desabilitarPavimentoRua");
				}

				if(dadosIdentificacaoLocalOcorrencia.getImovel().getPavimentoCalcada() != null){
					inserirRegistroAtendimentoActionForm.setIdPavimentoCalcada(dadosIdentificacaoLocalOcorrencia.getImovel()
									.getPavimentoCalcada().getId().toString());
					sessao.setAttribute("desabilitarPavimentoCalcada", "OK");
				}else{
					sessao.removeAttribute("desabilitarPavimentoCalcada");
				}
				sessao.setAttribute("desabilitarLcalidadeSetorQuadra", "OK");
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");

			}else{
				inserirRegistroAtendimentoActionForm.setIdImovel("");
				inserirRegistroAtendimentoActionForm.setInscricaoImovel("Imóvel Inexistente");
				sessao.removeAttribute("colecaoEnderecos");
				sessao.removeAttribute("enderecoPertenceImovel");
				sessao.removeAttribute("desabilitarDivisaoEsgoto");
				sessao.removeAttribute("desabilitarPavimentoRua");
				sessao.removeAttribute("desabilitarPavimentoCalcada");
				sessao.removeAttribute("desabilitarLcalidadeSetorQuadra");
				httpServletRequest.setAttribute("corImovel", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}
		}else{
			configurarColocarContaRevisao(httpServletRequest, sessao, inserirRegistroAtendimentoActionForm);
		}

		String pesquisarMunicipio = httpServletRequest.getParameter("pesquisarMunicipio");

		if(pesquisarMunicipio != null && !pesquisarMunicipio.equalsIgnoreCase("")){
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, inserirRegistroAtendimentoActionForm
							.getIdMunicipio()));
			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

			if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
				inserirRegistroAtendimentoActionForm.setIdMunicipio("");
				inserirRegistroAtendimentoActionForm.setDescricaoMunicipio("Município Inexistente");
				httpServletRequest.setAttribute("corMunicipio", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idMunicipio");

			}else{
				Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
				inserirRegistroAtendimentoActionForm.setIdMunicipio(municipio.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(municipio.getNome());
				httpServletRequest.setAttribute("nomeCampo", "cdBairro");
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		String pesquisarBairro = httpServletRequest.getParameter("pesquisarBairro");
		String descricaoBairro = httpServletRequest.getParameter("descricaoBairro");

		if(pesquisarBairro != null && !pesquisarBairro.equalsIgnoreCase("")){
			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, inserirRegistroAtendimentoActionForm.getCdBairro()));
			filtroBairro.adicionarParametro(new ComparacaoTexto(FiltroBairro.NOME, descricaoBairro));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, inserirRegistroAtendimentoActionForm
							.getIdMunicipio()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

			if(colecaoBairro == null || colecaoBairro.isEmpty()){
				inserirRegistroAtendimentoActionForm.setCdBairro("");
				inserirRegistroAtendimentoActionForm.setDescricaoBairro("Bairro Inexistente");
				httpServletRequest.setAttribute("corBairro", "exception");
				httpServletRequest.setAttribute("nomeCampo", "cdBairro");

			}else{
				Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);
				inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(bairro.getCodigo()));
				inserirRegistroAtendimentoActionForm.setDescricaoBairro(bairro.getNome());
				this.pesquisarBairroArea(bairro.getId(), fachada, sessao);
				httpServletRequest.setAttribute("nomeCampo", "idBairroArea");
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		String pesquisarBairroArea = httpServletRequest.getParameter("pesquisarBairroArea");

		if(pesquisarBairroArea != null && !pesquisarBairroArea.equalsIgnoreCase("")){
			this.pesquisarBairroArea(Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdBairro()), fachada, sessao);
			httpServletRequest.setAttribute("nomeCampo", "idBairroArea");
			httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
		}

		String pesquisarLocalidade = httpServletRequest.getParameter("pesquisarLocalidade");

		if(pesquisarLocalidade != null && !pesquisarLocalidade.equalsIgnoreCase("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, inserirRegistroAtendimentoActionForm
							.getIdLocalidade()));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

				inserirRegistroAtendimentoActionForm.setIdLocalidade("");
				inserirRegistroAtendimentoActionForm.setDescricaoLocalidade("Localidade Inexistente");

				httpServletRequest.setAttribute("corLocalidade", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idLocalidade");

			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

				inserirRegistroAtendimentoActionForm.setIdLocalidade(localidade.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoLocalidade(localidade.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		String pesquisarSetorComercial = httpServletRequest.getParameter("pesquisarSetorComercial");

		if(pesquisarSetorComercial != null && !pesquisarSetorComercial.equalsIgnoreCase("")){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
							inserirRegistroAtendimentoActionForm.getIdLocalidade()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							inserirRegistroAtendimentoActionForm.getCdSetorComercial()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){
				inserirRegistroAtendimentoActionForm.setIdSetorComercial("");
				inserirRegistroAtendimentoActionForm.setCdSetorComercial("");
				inserirRegistroAtendimentoActionForm.setDescricaoSetorComercial("Setor Comercial Inexistente");
				httpServletRequest.setAttribute("corSetorComercial", "exception");
				httpServletRequest.setAttribute("nomeCampo", "cdSetorComercial");

			}else{
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
				inserirRegistroAtendimentoActionForm.setIdSetorComercial(setorComercial.getId().toString());
				inserirRegistroAtendimentoActionForm.setCdSetorComercial(String.valueOf(setorComercial.getCodigo()));
				inserirRegistroAtendimentoActionForm.setDescricaoSetorComercial(setorComercial.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "nnQuadra");
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		String pesquisarQuadra = httpServletRequest.getParameter("pesquisarQuadra");

		if(pesquisarQuadra != null && !pesquisarQuadra.equalsIgnoreCase("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, inserirRegistroAtendimentoActionForm
							.getIdSetorComercial()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, inserirRegistroAtendimentoActionForm
							.getNnQuadra()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

				inserirRegistroAtendimentoActionForm.setIdQuadra("");
				inserirRegistroAtendimentoActionForm.setNnQuadra("");

				httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");
				httpServletRequest.setAttribute("nomeCampo", "nnQuadra");

			}else{
				Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

				inserirRegistroAtendimentoActionForm.setIdQuadra(quadra.getId().toString());
				inserirRegistroAtendimentoActionForm.setNnQuadra(String.valueOf(quadra.getNumeroQuadra()));

				// [SB0006] – Obtém Divisão de Esgoto
				DivisaoEsgoto divisaoEsgoto = fachada.obterDivisaoEsgoto(quadra.getId(), habilitaGeograficoDivisaoEsgoto
								.isSolicitacaoTipoRelativoAreaEsgoto());

				if(divisaoEsgoto != null){
					inserirRegistroAtendimentoActionForm.setIdDivisaoEsgoto(divisaoEsgoto.getId().toString());

					/*
					 * [FS0013] – Verificar compatibilidade entre divisão de
					 * esgoto e localidade/setor/quadra [SB0007] – Define
					 * Unidade Destino da Divisão de Esgoto
					 */
					this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada, inserirRegistroAtendimentoActionForm,
									habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());

					httpServletRequest.setAttribute("nomeCampo", "idUnidadeDestino");
				}else{
					httpServletRequest.setAttribute("nomeCampo", "idDivisaoEsgoto");
				}
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		String pesquisarUnidadeDestino = httpServletRequest.getParameter("pesquisarUnidadeDestino");

		if(pesquisarUnidadeDestino != null && !pesquisarUnidadeDestino.equalsIgnoreCase("")){
			FiltroUnidadeOrganizacional filtroUnidadeDestino = new FiltroUnidadeOrganizacional();
			filtroUnidadeDestino.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
							inserirRegistroAtendimentoActionForm.getIdUnidadeDestino()));
			Collection colecaoUnidadeDestino = fachada.pesquisar(filtroUnidadeDestino, UnidadeOrganizacional.class.getName());
			if(colecaoUnidadeDestino == null || colecaoUnidadeDestino.isEmpty()){
				inserirRegistroAtendimentoActionForm.setIdUnidadeDestino("");
				inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino("Unidade Destino Inexistente");
				httpServletRequest.setAttribute("corUnidadeDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idUnidadeDestino");
			}else{
				UnidadeOrganizacional unidadeDestino = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeDestino);

				// [FS0021] - Verificar possibilidade de encaminhamento para a
				// unidade destino
				fachada.verificaPossibilidadeEncaminhamentoUnidadeDestino(unidadeDestino);
				inserirRegistroAtendimentoActionForm.setIdUnidadeDestino(unidadeDestino.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino(unidadeDestino.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "parecerUnidadeDestino");
				httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
			}
		}

		/*
		 * Fim das pesquisas realizadas pelo ENTER
		 * 
		 * ==========================================================================================
		 * =================
		 * 
		 * ==========================================================================================
		 * =================
		 */

		/*
		 * [FS0013] – Verificar compatibilidade entre divisão de esgoto e
		 * localidade/setor/quadra [SB0007] – Define Unidade Destino da Divisão
		 * de Esgoto
		 */
		String verificarCompatibilidade = httpServletRequest.getParameter("verificarCompatibilidade");

		if(verificarCompatibilidade != null && !verificarCompatibilidade.equalsIgnoreCase("")){

			this.verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(fachada, inserirRegistroAtendimentoActionForm,
							habilitaGeograficoDivisaoEsgoto.isSolicitacaoTipoRelativoAreaEsgoto());
			httpServletRequest.setAttribute("nomeCampo", "idUnidadeDestino");
			httpServletRequest.setAttribute("desabilitarDescricaoLocalOcorrencia", "OK");
		}

		/*
		 * Removendo ColecaoBairroArea
		 */
		String removerColecaoBairroArea = httpServletRequest.getParameter("removerColecaoBairroArea");

		if(removerColecaoBairroArea != null && !removerColecaoBairroArea.equalsIgnoreCase("")){
			sessao.removeAttribute("colecaoBairroArea");
			httpServletRequest.setAttribute("nomeCampo", httpServletRequest.getParameter("campoFoco"));
		}

		/*
		 * Removendo endereço
		 */
		String removerEndereco = httpServletRequest.getParameter("removerEndereco");

		if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){
			if(sessao.getAttribute("colecaoEnderecos") != null){
				Collection enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");
				if(!enderecos.isEmpty()){
					enderecos.remove(enderecos.iterator().next());
				}
			}
		}

		String paramAcquaGis = null;
		try{
			paramAcquaGis = ParametroAtendimentoPublico.P_ENDERECO_WEBSERVICE_ACQUAGIS.executar();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		String exibirBotaoImportarGis = null;
		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_ADA.toString())){
			exibirBotaoImportarGis = "Ok";
		}else if(Util.isNaoNuloBrancoZero(paramAcquaGis) && !"-1".equals(paramAcquaGis)){
			exibirBotaoImportarGis = "Ok";
		}else{
			exibirBotaoImportarGis = null;
		}

		httpServletRequest.setAttribute("exibirBotaoImportarGis", exibirBotaoImportarGis);

		// Recuperar Dados AcquaGis
		String recuperarDadosAcquaGis = httpServletRequest.getParameter("recuperarDadosAcquaGis");
		DadosAcquaGis dadosAcquaGis = new DadosAcquaGis();

		if(recuperarDadosAcquaGis != null && !recuperarDadosAcquaGis.equals("")){

			ServletContext context = sessao.getServletContext();
			dadosAcquaGis = (DadosAcquaGis) context.getAttribute(usuarioLogado.getLogin());
			boolean encontrouImovel = false;

			if(dadosAcquaGis != null){
				inserirRegistroAtendimentoActionForm.setCoordenadaNorte(dadosAcquaGis.getNumeroCoordenadaNorte());
				inserirRegistroAtendimentoActionForm.setCoordenadaLeste(dadosAcquaGis.getNumeroCoordenadaLeste());
				inserirRegistroAtendimentoActionForm.setPontoReferencia(dadosAcquaGis.getDescricaoPontoReferencia());

				// Pesquisar o Imovel
				if(dadosAcquaGis.getIdImovel() != null && !dadosAcquaGis.getIdImovel().equals("")
								&& !dadosAcquaGis.getIdImovel().equals("0")){

					Imovel imovel = null;
					FiltroImovel filtroImovel = new FiltroImovel();

					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, dadosAcquaGis.getIdImovel()));
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TIPO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TITULO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_FEDERACAO);

					Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
					if(colecaoImovel != null && !colecaoImovel.isEmpty()){
						imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
					}

					if(imovel != null){

						if(imovel.getLogradouroBairro() != null && imovel.getLogradouroBairro().getBairro() != null){

							if(imovel.getLogradouroBairro().getBairro().getMunicipio() != null){
								inserirRegistroAtendimentoActionForm.setIdMunicipio(String.valueOf(imovel.getLogradouroBairro().getBairro()
												.getMunicipio().getId()));
								inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(imovel.getLogradouroBairro().getBairro()
												.getMunicipio().getNome());
							}
							inserirRegistroAtendimentoActionForm.setIdBairro(imovel.getLogradouroBairro().getBairro().getId().toString());
							inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(imovel.getLogradouroBairro().getBairro()
											.getCodigo()));
							inserirRegistroAtendimentoActionForm.setDescricaoBairro(imovel.getLogradouroBairro().getBairro().getNome());
						}
						inserirRegistroAtendimentoActionForm.setIdImovel(imovel.getId().toString());
						inserirRegistroAtendimentoActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());

						Collection colecaoEnderecos = new ArrayList();
						colecaoEnderecos.add(imovel);
						sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
						encontrouImovel = true;
					}else{
						inserirRegistroAtendimentoActionForm.setIdImovel("");
						inserirRegistroAtendimentoActionForm.setInscricaoImovel("Imóvel Inexistente");
					}
				}

				// Pesquisar o LogradouroBairro
				if(dadosAcquaGis.getIdLogradouroBairro() != null && !dadosAcquaGis.getIdLogradouroBairro().equals("")
								&& !dadosAcquaGis.getIdLogradouroBairro().equals("0") && !encontrouImovel){

					LogradouroBairro logBairro = fachada.pesquisarLogradouroBairro(Integer.valueOf(dadosAcquaGis.getIdLogradouroBairro()));

					if(logBairro != null){

						inserirRegistroAtendimentoActionForm.setIdMunicipio(String.valueOf(logBairro.getBairro().getMunicipio().getId()));
						inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(logBairro.getBairro().getMunicipio().getNome());
						inserirRegistroAtendimentoActionForm.setIdBairro(logBairro.getBairro().getId().toString());
						inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(logBairro.getBairro().getCodigo()));
						inserirRegistroAtendimentoActionForm.setDescricaoBairro(logBairro.getBairro().getNome());

						// Montando coleção que exibirá o endereço.
						Imovel imovel = new Imovel();
						LogradouroCep logCep = new LogradouroCep();
						logCep.setLogradouro(logBairro.getLogradouro());

						imovel.setLogradouroCep(logCep);
						imovel.setLogradouroBairro(logBairro);
						imovel.setComplementoEndereco(dadosAcquaGis.getDescricaoComplemento());
						imovel.setNumeroImovel(dadosAcquaGis.getNumeroImovel());

						Collection colecaoEnderecos = new ArrayList();
						colecaoEnderecos.add(imovel);
						sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
					}

				}

			}
			context.removeAttribute(usuarioLogado.getLogin());
		}

		/*
		 * Adicionar endereço
		 */
		String adicionarEndereco = httpServletRequest.getParameter("tipoPesquisaEndereco");

		if(adicionarEndereco != null && !adicionarEndereco.trim().equalsIgnoreCase("")){
			retorno = actionMapping.findForward("informarEndereco");
		}

		/*
		 * Limpar Imóvel
		 */
		String limparImovel = httpServletRequest.getParameter("limparImovel");

		if(limparImovel != null && !limparImovel.trim().equalsIgnoreCase("")){
			this.limparImovel(inserirRegistroAtendimentoActionForm, sessao);
			httpServletRequest.setAttribute("nomeCampo", "idImovel");
		}

		// se veio das outras abas
		validarLocalidadeMuniciopio(inserirRegistroAtendimentoActionForm, fachada, httpServletRequest);

		String limparContaRevisao = httpServletRequest.getParameter("limparContaRevisao");

		if(!Util.isVazioOuBranco(limparContaRevisao) && limparContaRevisao.equalsIgnoreCase("OK")){
			sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
			httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao", "N");
		}

		if((!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getSugerirUnidadeDestinoRA()) && "S"
						.equalsIgnoreCase(inserirRegistroAtendimentoActionForm.getSugerirUnidadeDestinoRA()))
						|| (!Util.isVazioOuBranco(httpServletRequest.getParameter("sugerirUnidadeDestino")) && "S"
										.equalsIgnoreCase(httpServletRequest.getParameter("sugerirUnidadeDestino")))
						|| (!Util.isVazioOuBranco(pesquisarImovel) && "OK".equalsIgnoreCase(pesquisarImovel))){
			inserirRegistroAtendimentoActionForm.setSugerirUnidadeDestinoRA("N");

			try{
				sugerirUnidadeDestino(httpServletRequest, inserirRegistroAtendimentoActionForm);
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retorno;

	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param inserirRegistroAtendimentoActionForm
	 * @throws ControladorException
	 */
	private void sugerirUnidadeDestino(HttpServletRequest httpServletRequest,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm) throws ControladorException{

		SistemaParametro sistemaParametro = (SistemaParametro) getSessao(httpServletRequest).getAttribute(
						SistemaParametro.SISTEMA_PARAMETRO);

		/*
		 * Caso o sistema deva sugerir a unidade destino para o primeiro encaminhamento
		 * do Registro de Atendimento (PARM_ICSUGESTAOTRAMITE=1 na tabela SISTEMA_PARAMETROS)
		 */

		if(sistemaParametro != null && sistemaParametro.getIndicadorSugestaoTramite() != null
						&& sistemaParametro.getIndicadorSugestaoTramite().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
			obterUnidadeOrganizacionalDestinoParaRA(inserirRegistroAtendimentoActionForm);
		}
	}

	/**
	 * Obtem a unidade destino de tramitação, dependendo da unidade original, especificação e de
	 * outros campos informados no filtro
	 * 
	 * @author isilva
	 * @param inserirRegistroAtendimentoActionForm
	 */
	@SuppressWarnings("unchecked")
	private void obterUnidadeOrganizacionalDestinoParaRA(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		EspecificacaoTramite especificacaoTramiteAuxiliar = new EspecificacaoTramite();

		// Adiciona SolicitacaoTipoEspecificacao ao filtro
		if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getEspecificacao())){
			Integer idEspecificacao = Integer.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao());
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setId(idEspecificacao);
			especificacaoTramiteAuxiliar.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		}

		// Adiciona UnidadeOrganizacional Origem ao filtro
		if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getUnidade())){
			Integer idUnidadeOrganizacionalOrigem = Integer.valueOf(inserirRegistroAtendimentoActionForm.getUnidade());
			UnidadeOrganizacional unidadeOrganizacionalOrigem = new UnidadeOrganizacional();
			unidadeOrganizacionalOrigem.setId(idUnidadeOrganizacionalOrigem);
			especificacaoTramiteAuxiliar.setUnidadeOrganizacionalOrigem(unidadeOrganizacionalOrigem);
		}

		if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getCdBairro())
						&& !Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdMunicipio())){
			// Adiciona Bairro e Municipio ao filtro
			// Só considerar se informar os dois campos

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, inserirRegistroAtendimentoActionForm.getCdBairro()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, inserirRegistroAtendimentoActionForm
							.getIdMunicipio()));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// obter o id do Bairro
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao((ArrayList<Bairro>) Fachada.getInstancia().pesquisar(filtroBairro,
							Bairro.class.getName()));

			if(bairro == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bairro");
			}else{
				Municipio municipio = new Municipio();
				municipio.setId(Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdMunicipio()));
				bairro.setMunicipio(municipio);
				especificacaoTramiteAuxiliar.setBairro(bairro);
			}
		}

		// Adiciona Localidade ao filtro
		if(Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdLocalidade())
						|| Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getCdSetorComercial())){
			if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdLocalidade())){
				// Adiciona Localidade ao filtro

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, inserirRegistroAtendimentoActionForm
								.getIdLocalidade()));
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
			// Só considerar se informar os dois campos

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, inserirRegistroAtendimentoActionForm
							.getIdLocalidade()));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao((ArrayList<Localidade>) Fachada.getInstancia().pesquisar(
							filtroLocalidade, Localidade.class.getName()));

			if(localidade == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}else{
				especificacaoTramiteAuxiliar.setLocalidade(localidade);
			}

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
							.valueOf(inserirRegistroAtendimentoActionForm.getCdSetorComercial())));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
							.valueOf(inserirRegistroAtendimentoActionForm.getIdLocalidade())));
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

		// Só pesquisa os ativos
		especificacaoTramiteAuxiliar.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = Fachada.getInstancia().obterUnidadeDestinoPorEspecificacao(
						especificacaoTramiteAuxiliar, true);
		
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
				inserirRegistroAtendimentoActionForm.setIdUnidadeDestino("");
				inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino("");
			}else{
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util
								.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

				inserirRegistroAtendimentoActionForm.setIdUnidadeDestino(String.valueOf(unidadeOrganizacional.getId()));
				inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino(String.valueOf(unidadeOrganizacional.getDescricao()));
			}
		}else{
			try{

				if(ParametroAtendimentoPublico.P_EXIBIR_UNIDADE_ATENDIMENTO.executar().equals(ConstantesSistema.NAO.toString())){

					inserirRegistroAtendimentoActionForm.setIdUnidadeDestino("");
					inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino("");
				}

			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param inserirRegistroAtendimentoActionForm
	 */
	private void configurarColocarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdImovel())){

				// SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm =
				// (SelecionarContaRevisaoActionForm)
				// sessao.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				//
				// if (!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)) {
				// if (!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())) {
				// if
				// (!inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(selecionarContaRevisaoActionForm.getCodigoImovel()))
				// {
				// sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				// }
				// }
				// } else {
				// sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
				// }

				// [SB0033] – Verifica se coloca Contas em Revisão.
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
								.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao())));
				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);
				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util
								.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroSolicitacaoTipoEspecificacao,
												SolicitacaoTipoEspecificacao.class.getName()));

				if(solicitacaoTipoEspecificacao == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Especificação");
				}

				// httpServletRequest.setAttribute("especificacao",
				// String.valueOf(solicitacaoTipoEspecificacao.getId()));

				// 1. Caso a especificação informada para o RA tenha indicativo que é para colocar
				// contas em revisão (STEP_ICCOLOCACONTASEMREVISAO da tabela
				// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1))

				if(Util.isVazioOuBranco(sessao.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO))){

					if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())
									&& solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao().intValue() == ConstantesSistema.SIM
													.intValue()){

						// 1.1. Caso o imóvel informado tenha conta(s) [FS0047 – Verificar
						// existência de
						// alguma conta].

						Collection<Conta> contas = pesquisarContasImovel(sessao, inserirRegistroAtendimentoActionForm.getIdImovel());

						if(contas != null && !contas.isEmpty()){

							// 1.1.1. Caso a especificação informada para o RA tenha indicativo que
							// é
							// para
							// exibir mensagem de alerta (STEP_ICMENSAGEMALERTA da tabela
							// SOLICITACAO_TIPO_ESPECIFICACAO com valor igual a SIM (1)
							if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao())
											&& solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao().intValue() == ConstantesSistema.SIM
															.intValue()){

								// 1.1.1.1. O sistema exibe a mensagem “Atenção, existem faturas
								// pendentes para o imóvel <<IMOV_ID da tabela
								// REGISTRO_ATENDIMENTO>>.
								// Deseja colocar as contas em revisão?”
								httpServletRequest.setAttribute(
												"msgEnviarContaRevisao",
												"Atenção, existem faturas pendentes para o imóvel "
																+ inserirRegistroAtendimentoActionForm.getIdImovel()
																+ ". Deseja colocar as contas em revisão?");

								httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao",
												SelecionarContaRevisaoActionForm.COM_MENSAGEM);
								// 1.1.1.1.1. Caso Sim, [SB0034 – Listar Contas]

							}else{
								// 1.1.2. Caso Não, [SB0034 – Listar Contas]
								httpServletRequest.setAttribute("abrirPopUpEnviarContaRevisao",
												SelecionarContaRevisaoActionForm.SEM_MENSAGEM);
							}
						}
						}

				}
			}
		}
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param codigoImovel
	 */
	private Collection<Conta> pesquisarContasImovel(HttpSession sessao, String codigoImovel){

		// Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Imovel imovel = Fachada.getInstancia().pesquisarImovelRegistroAtendimento(Integer.valueOf(codigoImovel));
		Collection<Conta> colecaoContaImovelRetornoConsulta = Fachada.getInstancia().obterContasImovelManter(imovel);
		Collection<Conta> colecaoContaImovel = new ArrayList<Conta>();

		if(colecaoContaImovelRetornoConsulta != null && !colecaoContaImovelRetornoConsulta.isEmpty()){
			for(Conta conta : colecaoContaImovelRetornoConsulta){
				if(conta.getDataRevisao() == null){
					colecaoContaImovel.add(conta);
				}
			}
		}

		// // ------------ CONTROLE DE ABRANGENCIA ----------------
		// Abrangencia abrangencia = new Abrangencia(usuarioLogado, imovel);
		//
		// if (!fachada.verificarAcessoAbrangencia(abrangencia)) {
		// throw new ActionServletException("atencao.acesso.negado.abrangencia");
		// }
		// // ------------ FIM CONTROLE DE ABRANGENCIA ------------

		// Coloca na sessão a coleção com as contas do imóvel selecionado
		return colecaoContaImovel;
	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param sessao
	 * @param inserirRegistroAtendimentoActionForm
	 */
	private void controlarFormularioSelecionarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) sessao
							.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);

			boolean removeFormulario = false;

			if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)){
				if(Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdImovel())){
					removeFormulario = true;
				}else{
					if(Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())){
						removeFormulario = true;
					}else{
						if(!inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getCodigoImovel())){
							removeFormulario = true;
						}
					}
				}

				if(Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getEspecificacao())){
					removeFormulario = true;
				}else{
					if(Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getEspecificacao())){
						removeFormulario = true;
					}else{
						if(!inserirRegistroAtendimentoActionForm.getEspecificacao().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getEspecificacao())){
							removeFormulario = true;
						}
					}
				}
			}

			if(removeFormulario){
				sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
			}

		}
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

	/*
	 * Métodos auxiliares
	 * 
	 * ==============================================================================================
	 * ===============
	 */
	public void verificarCompatibilidadeDefinirUnidadeDestinoDivisaoEsgoto(Fachada fachada,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, boolean solicitacaoTipoRelativoAreaEsgoto){

		fachada.verificarCompatibilidadeDivisaoEsgotoLocalidadeSetorQuadra(Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdLocalidade()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdSetorComercial()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdQuadra()), Util
						.converterStringParaInteger(inserirRegistroAtendimentoActionForm.getIdDivisaoEsgoto()));

		// UnidadeOrganizacional unidadeDestino =
		// fachada.definirUnidadeDestinoDivisaoEsgoto(Integer.valueOf(inserirRegistroAtendimentoActionForm
		// .getEspecificacao()),
		// Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdDivisaoEsgoto()),
		// solicitacaoTipoRelativoAreaEsgoto);
		//
		// if (unidadeDestino != null) {
		// inserirRegistroAtendimentoActionForm.setIdUnidadeDestino(unidadeDestino.getId().toString());
		// inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino(unidadeDestino.getDescricao());
		// }
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
						&& Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getEnderecoDescritivo())){

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
			inserirRegistroAtendimentoActionForm.setIdMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getId().toString());
			}

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
			inserirRegistroAtendimentoActionForm.setDescricaoMunicipio(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getMunicipio().getNome());
			}

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
				inserirRegistroAtendimentoActionForm.setIdBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
								.getLogradouroBairro().getBairro().getId().toString());
			}

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
			inserirRegistroAtendimentoActionForm.setCdBairro(String.valueOf(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getCodigo()));
			}

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
			inserirRegistroAtendimentoActionForm.setDescricaoBairro(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel()
							.getLogradouroBairro().getBairro().getNome());
			}

			if(!Util.isVazioOuBranco(obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro())){
				this.pesquisarBairroArea(
								obterDadosIdentificacaoLocalOcorrenciaHelper.getImovel().getLogradouroBairro().getBairro().getId(),
							fachada, sessao);
			}

			sessao.setAttribute("desabilitarMunicipioBairro", "OK");

		}else{

			inserirRegistroAtendimentoActionForm.setIdMunicipio("");
			inserirRegistroAtendimentoActionForm.setDescricaoMunicipio("");
			inserirRegistroAtendimentoActionForm.setIdBairro("");
			inserirRegistroAtendimentoActionForm.setCdBairro("");
			inserirRegistroAtendimentoActionForm.setDescricaoBairro("");
			sessao.removeAttribute("colecaoBairroArea");
			sessao.removeAttribute("desabilitarMunicipioBairro");
		}
	}

	private void limparImovel(InserirRegistroAtendimentoActionForm form, HttpSession sessao){

		form.setIdImovel("");
		form.setInscricaoImovel("");
		form.setDescricaoLocalOcorrencia("");
		form.setIdMunicipio("");
		form.setDescricaoMunicipio("");
		form.setIdBairro("");
		form.setCdBairro("");
		form.setDescricaoBairro("");
		form.setIdLocalidade("");
		form.setDescricaoLocalidade("");
		form.setIdSetorComercial("");
		form.setCdSetorComercial("");
		form.setDescricaoSetorComercial("");
		form.setIdQuadra("");
		form.setNnQuadra("");
		form.setIdUnidadeDestino("");
		form.setDescricaoUnidadeDestino("");
		form.setIdPavimentoRua(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
		form.setIdPavimentoCalcada(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
		form.setIdDivisaoEsgoto(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));

		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("enderecoPertenceImovel");
		sessao.removeAttribute("habilitarAlteracaoEndereco");
		sessao.removeAttribute("colecaoBairroArea");
		sessao.removeAttribute("desabilitarMunicipioBairro");
		sessao.removeAttribute("desabilitarDivisaoEsgoto");
		sessao.removeAttribute("desabilitarPavimentoRua");
		sessao.removeAttribute("desabilitarPavimentoCalcada");
		sessao.removeAttribute("desabilitarLcalidadeSetorQuadra");
	}
	// =================================================================================================================

}
