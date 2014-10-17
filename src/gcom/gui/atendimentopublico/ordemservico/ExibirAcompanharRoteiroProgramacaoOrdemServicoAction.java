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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.acquagis.atendimento.OrdemServicoDetalhesJSONHelper;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.FiltrarOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.OSProgramacaoHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterCargaTrabalhoEquipeHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesConfig;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAcompanharRoteiroProgramacaoOrdemServicoAction
				extends GcomAction {

	private static Logger log = Logger.getLogger(ExibirAcompanharRoteiroProgramacaoOrdemServicoAction.class);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Calendar tempoInicio = Calendar.getInstance();

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("acompanharOrdemServico");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm = (AcompanharRoteiroProgramacaoOrdemServicoActionForm) actionForm;

		// Data do Roteiro
		Date dataRoteiro = null;
		if(httpServletRequest.getParameter("dataRoteiro") == null || httpServletRequest.getParameter("dataRoteiro").equals("")){
			dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);
		}else{
			dataRoteiro = Util.converteStringParaDate(httpServletRequest.getParameter("dataRoteiro"), false);
		}

		String paramAcquaGis = null;
		try{
			paramAcquaGis = ParametroAtendimentoPublico.P_ENDERECO_WEBSERVICE_ACQUAGIS.executar();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		// Precisa pegar a unidade do usuario do login que esta na sessao
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();

		acompanharActionForm.setDataRoteiro(Util.formatarData(dataRoteiro));
		acompanharActionForm.setUnidadeLotacao("" + idUnidadeLotacao);

		String tipoAcao = httpServletRequest.getParameter("tipoAcao");

		// [SB0005] - Incluir Ordem de Serviço na Programação
		if(tipoAcao != null && tipoAcao.equals("I")){

			this.incluirOrdemServicoProgramacao(acompanharActionForm, usuario);

			// [SB0006] - Aloca Equipes para a Ordem de Serviço
		}else if(tipoAcao != null && tipoAcao.equals("A")){

			this.alocaEquipesParaOrdemServico(acompanharActionForm, usuario);

			// [SB0007] - Remaneja Ordem de Serviço
		}else if(tipoAcao != null && tipoAcao.equals("R")){

			this.remanejaOrdemServico(acompanharActionForm, usuario);

			// [SB0008] - Informa Situação da Ordem de Serviço
		}else if(tipoAcao != null && tipoAcao.equals("S")){

			this.informaSituacaoOrdemServico(acompanharActionForm, usuario, sessao);

			// [SB0013] - Reordena Sequencial na Programacao - Nova Ordem
		}else if(tipoAcao != null && tipoAcao.equals("P")){

			this.reordenaSequencialNaProgramacaoNovaOrdem(acompanharActionForm, sessao);

		}else if(tipoAcao != null && tipoAcao.equals("G")){

			this.enviarOrdemServicoParaAcquaGis(httpServletRequest, acompanharActionForm, usuario, paramAcquaGis);
		}

		String msg = httpServletRequest.getParameter("mensagemRetorno");
		if(msg != null && !msg.equals("")){
			acompanharActionForm.setMensagemRetorno(msg);
		}

		// Monta o mapOsProgramacaoHelper com as ordens de serviço de programação
		try{

			if(dataRoteiro != null){
				if(idUnidadeLotacao != null){
					this.montaOrdemServicoProgramacao(sessao, dataRoteiro, idUnidadeLotacao);
				}else{
					throw new ActionServletException("atencao.usuario_sem_unidade");
				}
				// [SB0010]-Prepara Barra de Carga de Trabalho
				this.preparaBarraCargaTrabalho(sessao, dataRoteiro);
			}else{
				throw new ActionServletException("atencao.data_roteiro_nao_encontrada");
			}

		}catch(ActionServletException ase){
			retorno = actionMapping.findForward("naExisteOSComProgramacaoParaEstaData");
		}

		String exibirBotaoImportarGis = null;
		if(Util.isNaoNuloBrancoZero(paramAcquaGis) && !"-1".equals(paramAcquaGis)){
			exibirBotaoImportarGis = "Ok";
		}

		httpServletRequest.setAttribute("exibirBotaoImportarGis", exibirBotaoImportarGis);

		System.out.println("Exibir Acompanhar Roteiro = " + Util.calcularDiferencaTempo(tempoInicio));

		return retorno;
	}

	private void enviarOrdemServicoParaAcquaGis(HttpServletRequest httpServletRequest,
					AcompanharRoteiroProgramacaoOrdemServicoActionForm form, Usuario usuario, String parametroUrlAquaGis){

		String[] oss = httpServletRequest.getParameterValues("osSelecionada");

		OrdemServicoDetalhesJSONHelper detalheJson = null;
		OrdemServico os = null;
		if(oss != null){

			for(String chaves : oss){
				String[] tokens = chaves.split("___");
				String idOs = tokens[0];
				String idOsProgramacao = tokens[2];

				os = Fachada.getInstancia().recuperaOSPorId(Integer.valueOf(idOs));

				FiltrarOrdemServicoHelper filtro = new FiltrarOrdemServicoHelper();
				filtro.setNumeroOS(Integer.valueOf(idOs));
				filtro.setPesquisarMaxTramite(false);
				detalheJson = Fachada.getInstancia().pesquisarOrdemServicoDetalhesWebService(filtro);

				FiltroOrdemServicoProgramacao filtroOSP = new FiltroOrdemServicoProgramacao();
				filtroOSP.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.ID, Integer.valueOf(idOsProgramacao)));
				Collection colecaoOSP = Fachada.getInstancia().pesquisar(filtroOSP, OrdemServicoProgramacao.class.getName());
				OrdemServicoProgramacao osp = (OrdemServicoProgramacao) Util.retonarObjetoDeColecao(colecaoOSP);

				if(osp.getIndicadorEnvioAcquaGis() == ConstantesSistema.SIM.shortValue()){
					throw new ActionServletException("atencao.os.ja.enviada.para.acquaGIS");
				}

				if(detalheJson != null && montarRegistroEnviarDadosParaAcquaGISComSucesso(os, detalheJson, osp, parametroUrlAquaGis)){
					osp.setIndicadorEnvioAcquaGis(new Short("1"));
					Fachada.getInstancia().atualizar(osp);
				}
			}
		}
	}

	private boolean montarRegistroEnviarDadosParaAcquaGISComSucesso(OrdemServico ordemServico, OrdemServicoDetalhesJSONHelper detalheJson,
					OrdemServicoProgramacao osp, String parametroUrlAquaGis){

		Integer numeroOS = detalheJson.getNumeroOs();
		Integer situacaoOS = detalheJson.getCodigoSituacaoOS();
		String matricula = detalheJson.getMatricula();
		int idMotivoEncerramento = 0;
		Integer codServico = detalheJson.getCodigoServicoOS();
		Integer idEquipe = osp.getEquipe().getId();
		String bairro = detalheJson.getBairro();
		String municipio = detalheJson.getNomeMunicipio();
		String descricaoCas = null;
		String prioridadeExecucao = ordemServico.getServicoTipoPrioridadeAtual().getDescricao();
		String servicoTipo = detalheJson.getDescricaoServicoTipo();
		String unidadeNegocio = detalheJson.getUnidadeNegocio();
		String localidade = detalheJson.getNomeLocalidade();
		String logradouro = detalheJson.getLogradouro();
		String nomeCliente = detalheJson.getNomeCliente();
		String observacao = ordemServico.getObservacao();
		String parecer = detalheJson.getParecer();
		String pontoReferencia = detalheJson.getPontoReferencia();
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		String estado = sistemaParametro.getNomeEstado();
		String telefoneContato = detalheJson.getTelefoneContato();
		String numeroLogradouro = detalheJson.getNumeroLogradouro();
		String cep = null;
		if(ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getLogradouroCep() != null
						&& ordemServico.getRegistroAtendimento().getLogradouroCep().getCep() != null
						&& ordemServico.getRegistroAtendimento().getLogradouroCep().getCep().getCepFormatado() != null){
			cep = ordemServico.getRegistroAtendimento().getLogradouroCep().getCep().getCepFormatado();
		}

		String latitudeStr = detalheJson.getCoordenadaNorte();
		String longitudeStr = detalheJson.getCoordenadaLeste();

		boolean enviadoComSucesso = true;
		try{

			StringBuilder body = new StringBuilder();
			body.append(obterValorNoPadraoDeEnvio(numeroOS, "NumeroOS", true));
			body.append(obterValorNoPadraoDeEnvio(idEquipe, "IdEquipe", true));
			body.append(obterValorNoPadraoDeEnvio(bairro, "Bairro", true));
			body.append(obterValorNoPadraoDeEnvio(latitudeStr, "Latitude", true));
			body.append(obterValorNoPadraoDeEnvio(longitudeStr, "Longitude", true));
			body.append(obterValorNoPadraoDeEnvio(descricaoCas, "DescricaoCas", false));
			body.append(obterValorNoPadraoDeEnvio(prioridadeExecucao, "PrioridadeExecucao", true));
			body.append(obterValorNoPadraoDeEnvio(servicoTipo, "ServicoTipo", true));
			body.append(obterValorNoPadraoDeEnvio(situacaoOS, "SituacaoOS", true));
			body.append(obterValorNoPadraoDeEnvio(unidadeNegocio, "UnidadeNegocio", false));
			body.append(obterValorNoPadraoDeEnvio(localidade, "Localidade", true));
			body.append(obterValorNoPadraoDeEnvio(logradouro, "Logradouro", true));
			body.append(obterValorNoPadraoDeEnvio(matricula, "Matricula", false));
			body.append(obterValorNoPadraoDeEnvio(idMotivoEncerramento, "IdMotivoEncerramento", false));
			body.append(obterValorNoPadraoDeEnvio(municipio, "Municipio", false));
			body.append(obterValorNoPadraoDeEnvio(nomeCliente, "NomeCliente", false));
			body.append(obterValorNoPadraoDeEnvio(observacao, "Observacao", false));
			body.append(obterValorNoPadraoDeEnvio(parecer, "Parecer", false));
			body.append(obterValorNoPadraoDeEnvio(pontoReferencia, "PontoReferencia", false));
			body.append(obterValorNoPadraoDeEnvio(estado, "Estado", false));
			body.append(obterValorNoPadraoDeEnvio(telefoneContato, "TelefoneContato", false));
			body.append(obterValorNoPadraoDeEnvio(numeroLogradouro, "NumeroLogradouro", false));
			body.append(obterValorNoPadraoDeEnvio(cep, "Cep", false));
			body.append(obterValorNoPadraoDeEnvio(codServico, "CodServico", true)); // Ultimo!

			String urlWebService = ConstantesConfig.getUrlWebserviceAcquaGisTramitarOS();
			System.out.println("URL: " + urlWebService);
			System.out.println("BODY: " + body.toString());
			HttpURLConnection con = null;
			URL rq = new URL(urlWebService);


			con = (HttpURLConnection) rq.openConnection();
			con.setRequestMethod("POST"); // type: POST, PUT, DELETE, GET
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setConnectTimeout(600000); // 60 secs
			con.setReadTimeout(600000); // 60 secs
			con.setRequestProperty("Accept-Encoding", "UTF-8");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setDoInput(true);
			con.setDoOutput(true);

			log.info("BODY: " + body.toString());

			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(body.toString());
			out.flush();
			out.close();

			con.connect();

			int httpCode = con.getResponseCode();
			if(!(httpCode >= HttpURLConnection.HTTP_OK && httpCode < HttpURLConnection.HTTP_MULT_CHOICE)){
				// Pode ser Removido após os testes! caso ocorra erro não altera o indicador
				throw new RuntimeException("Failed : HTTP error code : " + httpCode);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = null;
			StringBuilder sb = new StringBuilder();
			while((temp = in.readLine()) != null){
				sb.append(temp).append(" \n ");
			}
			log.info(sb.toString());

			in.close();

		}catch(MalformedURLException e){
			enviadoComSucesso = false;
			log.error("Erro na comunicação com AcquaGIS.", e);

		}catch(IOException e){

			enviadoComSucesso = false;
			log.error("Erro na comunicação com AcquaGIS.", e);
		}

		return enviadoComSucesso;
	}

	private String obterValorNoPadraoDeEnvio(Object param, String nome, boolean obrigatorio){
		String atributoPost = "";
		String valor = null;
		if(Util.isVazioOuBrancoOuZero(param) && obrigatorio){
			log.error("Atributo " + nome + " obrigatório para envio ao AcquaGIS. Atribuindo VALOR 0");
			valor = "0";
			if("Latitude".equals(nome) || "Longitude".equals(nome)){
				valor = "1";
			}

		}else if(!Util.isVazioOuBranco(param)){
			valor = String.valueOf(param);
			valor = valor.trim();
			valor = valor.replaceAll("-", "%2D");
			valor = valor.replaceAll(" ", "+");
		} 

		if("null".equals(valor)){
			valor = "0";
			if("Latitude".equals(nome) || "Longitude".equals(nome)){
				valor = "1";
			}
		}

		String addComercial = "&";
		if("CodServico".equals(nome)){
			addComercial = "";
		}

		if(Util.isNaoNuloBrancoZero(valor) || obrigatorio){
			atributoPost = nome + "=" + valor + addComercial;
		}
		log.info(nome + " = [" + atributoPost + "]");

		return atributoPost;
	}

	/**
	 * [SB0005] - Incluir Ordem de Serviço na Programação
	 * 
	 * @author Rafael Pinto
	 * @date 28/08/2006
	 * @param AcompanharRoteiroProgramacaoOrdemServicoActionForm
	 *            ,usuario
	 */
	private void incluirOrdemServicoProgramacao(AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm, Usuario usuario){

		Fachada fachada = Fachada.getInstancia();

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);

		// Colocado por Raphael Rossiter em 12/03/2007
		Integer idUnidadeLotacao = Integer.valueOf(acompanharActionForm.getUnidadeLotacao());

		OrdemServico os = Fachada.getInstancia().recuperaOSPorId(Integer.valueOf(acompanharActionForm.getIdOrdemServico()));

		// [FS0008] - Verificar possibilidade da inclusão da ordem de serviço
		fachada.validarInclusaoOsNaProgramacao(os, idUnidadeLotacao);

		Equipe equipe = new Equipe();
		equipe.setId(Integer.valueOf(acompanharActionForm.getIdEquipe()));

		fachada.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, equipe.getId());

		// [SB0012] - Reordena Sequencial de Programação - Inclusão de Ordem de Serviço
		/*
		 * Identificar a maior sequência das Programações das Ordens de Seviço naquela data, para
		 * aquela Equipe
		 */
		Integer maiorSequencialBase = fachada.maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(equipe.getId(), dataRoteiro);
		Integer maiorSequencial = maiorSequencialBase;

		if(maiorSequencial == null){
			maiorSequencial = 1;
		}else{
			maiorSequencial++;
		}

		// Short maiorSequencial = Short.valueOf(acompanharActionForm.getSequencialProgramacao());
		Short sequencialInformado = Short.valueOf(acompanharActionForm.getSequencialProgramacaoPrevisto());

		if((sequencialInformado.intValue() - 1) > maiorSequencialBase){
			throw new ActionServletException("atencao.sequencial_programacao_maior_limite", null, "" + maiorSequencialBase);
		}
		// else {
		// //fachada.reordenaSequencialProgramacaoInclusaoOrdemServico(dataRoteiro,sequencial);
		// //fachada.reordenaSequencialProgramacaoInclusaoOrdemServico(dataRoteiro, equipe,
		// sequencialInformado);
		// }

		// [SB0011] - Incluir Programação
		OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();

		ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, usuario.getUnidadeOrganizacional().getId());

		OrdemServico ordemServico = new OrdemServico();
		ordemServico.setId(Integer.valueOf(acompanharActionForm.getIdOrdemServico()));

		ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);
		ordemServicoProgramacao.setOrdemServico(ordemServico);

		ordemServicoProgramacao.setEquipe(equipe);
		ordemServicoProgramacao.setUsuarioProgramacao(usuario);
		ordemServicoProgramacao.setUsuarioFechamento(null);
		ordemServicoProgramacao.setOsProgramNaoEncerMotivo(null);
		ordemServicoProgramacao.setNnSequencialProgramacao(sequencialInformado);
		ordemServicoProgramacao.setIndicadorAtivo(ConstantesSistema.SIM);
		ordemServicoProgramacao.setIndicadorEquipePrincipal(ConstantesSistema.SIM);
		ordemServicoProgramacao.setUltimaAlteracao(new Date());
		ordemServicoProgramacao.setSituacaoFechamento(OrdemServicoProgramacao.SITUACAO_VAZIO);

		fachada.incluirOrdemServicoProgramacaoComSequencialInformado(ordemServicoProgramacao, usuario, false);
		// Ordem as OrdemServicoProgramacao, da equipe, pela data
		fachada.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, equipe.getId());

		// fachada.incluirOrdemServicoProgramacao(ordemServicoProgramacao, usuario);
	}

	/**
	 * [SB0006] - Aloca Equipes para a Ordem de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 28/08/2006
	 * @param AcompanharRoteiroProgramacaoOrdemServicoActionForm
	 *            ,usuario
	 */
	private void alocaEquipesParaOrdemServico(AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm, Usuario usuario){

		Fachada fachada = Fachada.getInstancia();

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);

		Integer equipePrincipal = Integer.valueOf(acompanharActionForm.getIdEquipePrincipal());
		String[] equipeSelecionada = acompanharActionForm.getEquipeSelecionada();
		String[] equipeSelecionadaBanco = acompanharActionForm.getEquipeSelecionadaAtual();

		Collection equipeSelecionadaParaAlocar = Util.separarCamposString(",", equipeSelecionada[0]);

		for(int i = 0; i < equipeSelecionadaBanco.length; i++){

			String idEquipeBanco = equipeSelecionadaBanco[i];

			// Se nao tem mais a osProgramacao na lista de equipes para alocar,entao deve-se remover
			// essa programação
			if(!equipeSelecionadaParaAlocar.contains(idEquipeBanco)){

				fachada.alocaEquipeParaOs(Integer.valueOf(acompanharActionForm.getIdOrdemServico()), dataRoteiro, Integer
								.valueOf(idEquipeBanco));

			}else{
				equipeSelecionadaParaAlocar.remove(idEquipeBanco);
			}
		}

		// Se sobrou alguma equipe, então de inserir a osProgramacao
		if(equipeSelecionadaParaAlocar != null && !equipeSelecionadaParaAlocar.isEmpty()){
			Iterator itera = equipeSelecionadaParaAlocar.iterator();
			while(itera.hasNext()){

				String idEquipe = (String) itera.next();
				int id = Integer.valueOf(idEquipe).intValue();

				// [SB0011] - Incluir Programação
				OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();

				ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, usuario.getUnidadeOrganizacional()
								.getId());

				OrdemServico ordemServico = new OrdemServico();
				ordemServico.setId(Integer.valueOf(acompanharActionForm.getIdOrdemServico()));

				ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);
				ordemServicoProgramacao.setOrdemServico(ordemServico);

				Equipe equipe = new Equipe();
				equipe.setId(Integer.valueOf(idEquipe));

				ordemServicoProgramacao.setEquipe(equipe);
				ordemServicoProgramacao.setUsuarioProgramacao(usuario);
				ordemServicoProgramacao.setUsuarioFechamento(null);
				ordemServicoProgramacao.setOsProgramNaoEncerMotivo(null);
				ordemServicoProgramacao.setNnSequencialProgramacao(Short.valueOf(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)));
				ordemServicoProgramacao.setIndicadorAtivo(ConstantesSistema.SIM);

				if(equipePrincipal.intValue() == id){
					ordemServicoProgramacao.setIndicadorEquipePrincipal(ConstantesSistema.SIM);
				}else{
					ordemServicoProgramacao.setIndicadorEquipePrincipal(ConstantesSistema.NAO);
				}

				ordemServicoProgramacao.setUltimaAlteracao(new Date());
				ordemServicoProgramacao.setSituacaoFechamento(OrdemServicoProgramacao.SITUACAO_VAZIO);

				fachada.incluirOrdemServicoProgramacao(ordemServicoProgramacao, usuario);
			}

		}
	}

	/**
	 * [SB0007] - Remaneja Ordem de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 28/08/2006
	 * @param AcompanharRoteiroProgramacaoOrdemServicoActionForm
	 *            ,usuario
	 */
	private void remanejaOrdemServico(AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm, Usuario usuario){

		Fachada fachada = Fachada.getInstancia();

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);

		Integer equipeRemanejada = Integer.valueOf(acompanharActionForm.getIdEquipePrincipal());
		Integer equipeAtual = Integer.valueOf(acompanharActionForm.getIdEquipeAtual());
		Integer idOrdemServico = Integer.valueOf(acompanharActionForm.getIdOrdemServico());

		// Usa o mesmo metodo de alocar equipe
		fachada.alocaEquipeParaOs(idOrdemServico, dataRoteiro, equipeAtual);

		// [SB0011] - Incluir Programação
		OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();

		ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, usuario.getUnidadeOrganizacional().getId());

		OrdemServico ordemServico = new OrdemServico();
		ordemServico.setId(Integer.valueOf(acompanharActionForm.getIdOrdemServico()));

		ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);
		ordemServicoProgramacao.setOrdemServico(ordemServico);

		Equipe equipe = new Equipe();
		equipe.setId(Integer.valueOf(equipeRemanejada));

		ordemServicoProgramacao.setEquipe(equipe);
		ordemServicoProgramacao.setUsuarioProgramacao(usuario);
		ordemServicoProgramacao.setUsuarioFechamento(null);
		ordemServicoProgramacao.setOsProgramNaoEncerMotivo(null);
		ordemServicoProgramacao.setNnSequencialProgramacao((Short.valueOf(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))));
		ordemServicoProgramacao.setIndicadorAtivo(ConstantesSistema.SIM);
		ordemServicoProgramacao.setIndicadorEquipePrincipal(ConstantesSistema.SIM);

		ordemServicoProgramacao.setUltimaAlteracao(new Date());
		ordemServicoProgramacao.setSituacaoFechamento(OrdemServicoProgramacao.SITUACAO_VAZIO);

		fachada.incluirOrdemServicoProgramacao(ordemServicoProgramacao, usuario);

		// Ordem as OrdemServicoProgramacao, da equipe, pela data
		fachada.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, equipeAtual);

	}

	/**
	 * [SB0008] - Informa Situação da Ordem de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 28/08/2006
	 * @param AcompanharRoteiroProgramacaoOrdemServicoActionForm
	 *            ,usuario
	 */
	private void informaSituacaoOrdemServico(AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm, Usuario usuario,
					HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);

		Short novaSituacaoOs = Short.valueOf(acompanharActionForm.getSituacaoOrdemServico());

		Integer motivoNaoEncerramentoOs = null;
		if(acompanharActionForm.getMotivoNaoEncerramento() != null
						&& !acompanharActionForm.getMotivoNaoEncerramento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			motivoNaoEncerramentoOs = Integer.valueOf(acompanharActionForm.getMotivoNaoEncerramento());
		}

		Integer idOrdemServico = Integer.valueOf(acompanharActionForm.getIdOrdemServico());

		OrdemServico ordemServico = fachada.recuperaOSPorId(idOrdemServico);

		if(novaSituacaoOs.shortValue() == OrdemServico.SITUACAO_PENDENTE.shortValue()
						|| novaSituacaoOs.shortValue() == OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO.shortValue()){

			ordemServico.setSituacao(novaSituacaoOs);

			if(novaSituacaoOs.shortValue() == OrdemServico.SITUACAO_PENDENTE.shortValue()){
				ordemServico.setIndicadorProgramada(OrdemServico.NAO_PROGRAMADA);
			}

			fachada.atualizarOrdemServico(ordemServico, usuario);

			fachada.atualizarOrdemServicoProgramacaoSituacaoOs(idOrdemServico, dataRoteiro, novaSituacaoOs, motivoNaoEncerramentoOs);

		}

		String chaveEquipe = acompanharActionForm.getChaveEquipe();

		HashMap mapEquipe = (HashMap) sessao.getAttribute("mapEquipe");

		Equipe equipe = (Equipe) mapEquipe.get(chaveEquipe);

		fachada.reordenarSequencialProgramacao(dataRoteiro, equipe.getId());
	}

	/**
	 * [SB0013] - Reordena Sequencial na Programacao - Nova Ordem
	 * 
	 * @author Rafael Pinto
	 * @date 28/08/2006
	 * @param AcompanharRoteiroProgramacaoOrdemServicoActionForm
	 *            ,sessao
	 */
	private void reordenaSequencialNaProgramacaoNovaOrdem(AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm,
					HttpSession sessao){

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro(), false);

		Short sequencialAtual = Short.valueOf(acompanharActionForm.getSequencialProgramacao());
		int sequencialInformado = Integer.valueOf(acompanharActionForm.getSequencialProgramacaoPrevisto());
		String chaveEquipe = acompanharActionForm.getChaveEquipe();

		HashMap mapOsProgramacaoHelper = (HashMap) sessao.getAttribute("mapOsProgramacaoHelper");

		Collection colecaoHelper = (ArrayList) mapOsProgramacaoHelper.get(chaveEquipe);
		int valor = this.retornaUltimoSequencial(colecaoHelper);

		if(sequencialInformado > valor){
			throw new ActionServletException("atencao.sequencial_programacao_maior_limite", null, "" + valor);
		}
		HashMap mapEquipe = (HashMap) sessao.getAttribute("mapEquipe");

		Equipe equipe = (Equipe) mapEquipe.get(chaveEquipe);

		Fachada.getInstancia().reordenaSequencialProgramacaoNovaOrdem(dataRoteiro, Short.valueOf(String.valueOf(sequencialInformado)),
						sequencialAtual, equipe.getId(), true);
	}

	/**
	 * Monta um HashMap(nomeEquipe,Colecao de OSProgramacaoHelper) a partir
	 * da colecao de Ordem de Servico Programcao associado a data de roteiro
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 * @param sessao
	 *            ,data do roteiro
	 */
	private void montaOrdemServicoProgramacao(HttpSession sessao, Date dataRoteiro, Integer idUnidadeLotacao){

		Collection<OrdemServicoProgramacao> colecaoOrdemServicoProgramacao = Fachada.getInstancia()
						.recuperaOSProgramacaoPorDataRoteiroUnidade(dataRoteiro, idUnidadeLotacao);

		HashMap mapOsProgramacaoHelper = new HashMap();
		HashMap mapEquipe = new HashMap();

		String idAcompanhamentoRoteiro = null;
		try{
			idAcompanhamentoRoteiro = ParametroAtendimentoPublico.P_ACOMPANHAMENTO_PROGRAMACAO_OS_ENCERRADA.executar();
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		if(colecaoOrdemServicoProgramacao != null && !colecaoOrdemServicoProgramacao.isEmpty()){

			Iterator itera = colecaoOrdemServicoProgramacao.iterator();


			while(itera.hasNext()){

				OrdemServicoProgramacao ordemServicoProgramacao = (OrdemServicoProgramacao) itera.next();
				OrdemServico ordemServico = ordemServicoProgramacao.getOrdemServico();

				Equipe equipe = ordemServicoProgramacao.getEquipe();
				String chave = equipe.getNome();

				OSProgramacaoHelper helper = new OSProgramacaoHelper();

				int qtdDiasCliente = ConstantesSistema.NUMERO_NAO_INFORMADO;
				int qtdDiasAgencia = ConstantesSistema.NUMERO_NAO_INFORMADO;

				if(ordemServico.getRegistroAtendimento() != null){
					Date dataPrevistaAtual = ordemServico.getRegistroAtendimento().getDataPrevistaAtual();

					if(dataPrevistaAtual != null){
						qtdDiasCliente = Util.obterQuantidadeDiasEntreDuasDatas(dataPrevistaAtual, new Date());
					}

					Date dataAgenciaReguladoraPrevisaoAtual = Fachada.getInstancia().obterDataAgenciaReguladoraPrevisaoAtual(
									ordemServico.getRegistroAtendimento().getId());

					if(dataAgenciaReguladoraPrevisaoAtual != null){

						qtdDiasAgencia = Util.obterQuantidadeDiasEntreDuasDatas(dataAgenciaReguladoraPrevisaoAtual, new Date());
					}

					int logradouro = ConstantesSistema.NUMERO_NAO_INFORMADO;
					if(ordemServico.getRegistroAtendimento().getLogradouroBairro() != null){
						logradouro = ordemServico.getRegistroAtendimento().getLogradouroBairro().getId().intValue();
					}

					Collection colecaoAlertaLogradouro = pesquisaEquipePeloLogradouro(sessao, logradouro, chave);
					if(colecaoAlertaLogradouro != null && !colecaoAlertaLogradouro.isEmpty()){
						helper.setTemAlerta(true);
						helper.setColecaoAlertaEquipeDeLogradouro(colecaoAlertaLogradouro);
					}
				}

				helper.setPodeRemover(false);

				ServicoPerfilTipo servicoPerfilTipo = ordemServico.getServicoTipo().getServicoPerfilTipo();
				ServicoPerfilTipo servicoPerfilTipoEquipe = equipe.getServicoPerfilTipo();

				if(servicoPerfilTipo != null && servicoPerfilTipoEquipe != null
								&& servicoPerfilTipo.getId().intValue() != servicoPerfilTipoEquipe.getId().intValue()){

					helper.setTemAlerta(true);
					helper.setAlertaEquipeDeServicoPerfilTipo(chave);

				}else if((servicoPerfilTipo == null && equipe.getServicoPerfilTipo() != null)
								|| (servicoPerfilTipo != null && equipe.getServicoPerfilTipo() == null)){

					helper.setTemAlerta(true);
					helper.setAlertaEquipeDeServicoPerfilTipo(chave);
				}

				if(qtdDiasCliente > 0){
					helper.setDiasAtrasoCliente(qtdDiasCliente);
				}

				if(qtdDiasAgencia > 0){
					helper.setDiasAtrasoAgencia(qtdDiasAgencia);
				}

				ObterDescricaoSituacaoOSHelper obter = Fachada.getInstancia().obterDescricaoSituacaoOS(ordemServico.getId());

				helper.setSituacao(obter.getDescricaoAbreviadaSituacao());
				String endereco = Fachada.getInstancia().obterEnderecoAbreviadoOS(ordemServico.getId());
				helper.setEndereco(endereco);
				helper.setOrdemServicoProgramacao(ordemServicoProgramacao);

				Short situacao = this.getFachada().verificaSituacaoOS(ordemServico.getId());

				if(!mapOsProgramacaoHelper.containsKey(chave)){

					Collection colecaoHelper = new ArrayList();

					if(situacao.equals(OrdemServico.SITUACAO_ENCERRADO)){

						if(idAcompanhamentoRoteiro.equalsIgnoreCase(ConstantesSistema.SIM.toString())){
							colecaoHelper.add(helper);
						}
					}else{

						colecaoHelper.add(helper);
					}

					if(!colecaoHelper.isEmpty()){

						mapOsProgramacaoHelper.put(chave, colecaoHelper);

					}
				}else{

					Collection colecaoHelper = (ArrayList) mapOsProgramacaoHelper.get(chave);

					if(situacao.equals(OrdemServico.SITUACAO_ENCERRADO)){

						if(idAcompanhamentoRoteiro.equalsIgnoreCase(ConstantesSistema.SIM.toString())){
							colecaoHelper.add(helper);
						}
					}else{
						colecaoHelper.add(helper);
					}

					if(!colecaoHelper.isEmpty()){

						mapOsProgramacaoHelper.put(chave, colecaoHelper);

					}
				}

				mapEquipe.put(chave, equipe);
			}
		}else{
			throw new ActionServletException("atencao.ordem_servico_programacao_inexistente", null, Util.formatarData(dataRoteiro));
		}

		sessao.setAttribute("mapOsProgramacaoHelper", mapOsProgramacaoHelper);
		sessao.setAttribute("mapEquipe", mapEquipe);
	}

	/**
	 * [SB00010] - Prepara Barra da Carga de Trabalho
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 * @param sessao
	 *            ,data do roteiro
	 */
	private void preparaBarraCargaTrabalho(HttpSession sessao, Date dataRoteiro){

		HashMap mapEquipeIdsOsProgramadas = new HashMap();

		HashMap mapOsProgramacaoHelper = (HashMap) sessao.getAttribute("mapOsProgramacaoHelper");

	if(mapOsProgramacaoHelper!=null){
			
		Collection colecaoHelper = mapOsProgramacaoHelper.values();
		
		Iterator itera = colecaoHelper.iterator();

		while(itera.hasNext()){

			Collection colecaoOSProgramacaoHelper = (Collection) itera.next();

			Iterator iteraOsProgramacao = colecaoOSProgramacaoHelper.iterator();

			while(iteraOsProgramacao.hasNext()){

				OSProgramacaoHelper osProgramacaoHelper = (OSProgramacaoHelper) iteraOsProgramacao.next();

				OrdemServicoProgramacao osProgramacao = osProgramacaoHelper.getOrdemServicoProgramacao();
				String chaveNome = osProgramacao.getEquipe().getNome();

				if(!mapEquipeIdsOsProgramadas.containsKey(chaveNome)){

					Set colecaoIds = new HashSet();
					colecaoIds.add(osProgramacao.getOrdemServico().getId());

					mapEquipeIdsOsProgramadas.put(chaveNome, colecaoIds);
				}else{

					Set colecaoIds = (HashSet) mapEquipeIdsOsProgramadas.get(chaveNome);
					colecaoIds.add(osProgramacao.getOrdemServico().getId());

					mapEquipeIdsOsProgramadas.put(chaveNome, colecaoIds);
				}

			}
		}

		itera = mapEquipeIdsOsProgramadas.keySet().iterator();

		HashMap mapEquipe = (HashMap) sessao.getAttribute("mapEquipe");

		while(itera.hasNext()){

			String key = (String) itera.next();

			Collection colecaoIdsOSProgramadas = (HashSet) mapEquipeIdsOsProgramadas.get(key);

			Equipe equipe = (Equipe) mapEquipe.get(key);

			ObterCargaTrabalhoEquipeHelper obterCargaTrabalhoEquipeHelper = Fachada.getInstancia().obterCargaTrabalhoEquipe(equipe.getId(),
							colecaoIdsOSProgramadas, null, dataRoteiro);

			BigDecimal percentualPrevista = obterCargaTrabalhoEquipeHelper.getPercentualCargaTrabalhoPrevista();
			BigDecimal percentualRealizada = obterCargaTrabalhoEquipeHelper.getPercentualCargaRealizada();

			String chaveSessao = key.replace("-", "");
			chaveSessao = chaveSessao.replace(" ", "");

			String keyPercentualPrevista = "percentualTrabalhoPrevista" + chaveSessao;
			sessao.setAttribute(keyPercentualPrevista, percentualPrevista);

			String keyPercentualRealizada = "percentualTrabalhoRealizada" + chaveSessao;
			sessao.setAttribute(keyPercentualRealizada, percentualRealizada);

			Integer qtidadeOs = 0;
			if(colecaoIdsOSProgramadas != null){
				qtidadeOs = colecaoIdsOSProgramadas.size();
			}
			sessao.setAttribute("qtidadeOS" + chaveSessao, qtidadeOs);
			if(obterCargaTrabalhoEquipeHelper == null){
				sessao.setAttribute("cargaTrabalhoPrevistaHoras" + chaveSessao, Util.obterHoraMinutos(null));
			}else{
				sessao.setAttribute("cargaTrabalhoPrevistaHoras" + chaveSessao, Util.obterHoraMinutos(obterCargaTrabalhoEquipeHelper
								.getCargaTrabalhoPrevistaHoras()));
			}
		}
	}
	}

	/**
	 * Pesquisar a Programacao Roteiro (como a tabela eh pequena usa o filtro)
	 * 
	 * @author Rafael Pinto
	 * @date 21/08/2006
	 * @param data
	 *            do roteiro,idUnidade
	 */
	private ProgramacaoRoteiro consultarProgramacaoRoteiro(Date dataRoterio, Integer unidade){

		FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.DATA_ROTEIRO, dataRoterio));

		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID, unidade));

		Collection colecaoProgramacaoRoteiro = Fachada.getInstancia().pesquisar(filtroProgramacaoRoteiro,
						ProgramacaoRoteiro.class.getName());

		ProgramacaoRoteiro programacaoRoteiro = (ProgramacaoRoteiro) Util.retonarObjetoDeColecao(colecaoProgramacaoRoteiro);

		return programacaoRoteiro;

	}

	/**
	 * Retorna a colecao de chave da equipe que tenha o mesmo logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 * @param sessao
	 *            ,id do Logradouro e chave da equipe
	 * @return colecao de chaves da equipe
	 */
	private Collection pesquisaEquipePeloLogradouro(HttpSession sessao, int idLogradouro, String chave){

		Set retorno = new HashSet();

		HashMap mapOsProgramacaoHelper = (HashMap) sessao.getAttribute("mapOsProgramacaoHelper");

		if(mapOsProgramacaoHelper != null && !mapOsProgramacaoHelper.isEmpty()){

			Collection colecao = (Collection) mapOsProgramacaoHelper.get(chave);

			if(colecao != null && !colecao.isEmpty()){
				Iterator iter = colecao.iterator();
				while(iter.hasNext()){
					OSProgramacaoHelper helper = (OSProgramacaoHelper) iter.next();

					OrdemServicoProgramacao ordemServicoProgramacao = helper.getOrdemServicoProgramacao();
					OrdemServico ordemServico = ordemServicoProgramacao.getOrdemServico();
					int idLogra = ConstantesSistema.NUMERO_NAO_INFORMADO;

					if(ordemServico.getRegistroAtendimento() != null){

						if(ordemServico.getRegistroAtendimento().getLogradouroBairro() != null){
							idLogra = ordemServico.getRegistroAtendimento().getLogradouroBairro().getId().intValue();
						}

						String key = ordemServicoProgramacao.getEquipe().getNome();

						if(idLogra == idLogradouro && !key.equals(chave)){
							retorno.add(key);
						}
					}
				}
			}
		}

		return retorno;
	}

	/**
	 * Retorna o ultimo sequencial das os´s programadas
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 * @param colecao
	 *            de OsProgramacaoHelper
	 * @return ultimoSequencial
	 */
	private int retornaUltimoSequencial(Collection colecaoOsProgramacaoHelper){

		Short valorSequencial = 0;
		Iterator iter = colecaoOsProgramacaoHelper.iterator();
		while(iter.hasNext()){
			OSProgramacaoHelper helper = (OSProgramacaoHelper) iter.next();

			if(valorSequencial < helper.getOrdemServicoProgramacao().getNnSequencialProgramacao()){
				valorSequencial = helper.getOrdemServicoProgramacao().getNnSequencialProgramacao();
			}
		}

		return valorSequencial;
	}

}