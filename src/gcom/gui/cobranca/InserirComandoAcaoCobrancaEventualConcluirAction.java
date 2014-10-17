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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.localidade.*;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0243] Inserir Comando de A��o de Conbran�a - Tipo de Comando Cronograma
 * Executado qdo o usu�rio clica em Concluir estando na tela de
 * comandar_acao_cobranca_eventual_inserir_processo2.jsp
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 * @author eduardo henrique
 * @date 30/08/2008
 *       Altera��es no [UC0243] para a v0.04
 * @author Virg�nia Melo
 * @date 08/11/2008
 *       Altera��es no [UC0243] para a v0.06
 * @author Virg�nia Melo
 * @date 05/08/2009
 *       Altera��es no [UC0243]
 *       - Adicionado campo valor limite para emiss�o obrigat�ria
 *       - Retirados campos de refer�ncia da conta
 */
public class InserirComandoAcaoCobrancaEventualConcluirAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm = (InserirComandoAcaoCobrancaEventualCriterioRotaActionForm) actionForm;

		String idLocalidade = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID();
		String codigoSetorComercial = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD();

		String idLocalidadeFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID();
		String codigoSetorComercialFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoCD();

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Collection colecaoClienteArrecadador = (Collection) sessao.getAttribute("colecaoClienteArrecadador");

		if(Util.isVazioOrNulo(colecaoClienteArrecadador)){
			if(Util.isVazioOuBranco(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao())){
				String[] cobrancaAcao = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao();
				for(int i = 0; i < cobrancaAcao.length; i++){
					if(cobrancaAcao[i].equals(CobrancaAcao.COBRANCA_BANCARIA.toString())){
						setarUrlLevantarExcecao(new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Arrecadador"));
					}
				}
			}
		}

		extraiArquivo(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm);

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));
			// pesquisa
			Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades == null || localidades.isEmpty()){
				setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.localidade_inicial_inexistente"));
			}
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
				filtroSetorComercial.limparListaParametros();
				// coloca parametro no filtro
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
								.valueOf(idLocalidade)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
								.valueOf(codigoSetorComercial)));
				// pesquisa
				Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				if(setorComerciais == null || setorComerciais.isEmpty()){
					setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.setor_inicial_inexistente"));
				}
			}
		}

		filtroLocalidade = new FiltroLocalidade();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidadeFinal)));
			// pesquisa
			Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades == null || localidades.isEmpty()){
				setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.localidade_final_inexistente"));
			}
		}

		filtroSetorComercial = new FiltroSetorComercial();
		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
				filtroSetorComercial.limparListaParametros();
				// coloca parametro no filtro
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Integer
								.valueOf(idLocalidadeFinal)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
								.valueOf(codigoSetorComercialFinal)));
				// pesquisa
				Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				if(setorComerciais == null || setorComerciais.isEmpty()){
					setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.setor_final_inexistente"));
				}
			}
		}

		FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
		String arrecadadorID = "";
		// Arrecadador
		if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdClienteCombo() != null
						&& !inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdClienteCombo().equals("-1")){
			// Inclui a obejeto de cliente no filtro de arrecadador
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			// filtra arrecadador pelo cliente
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CLIENTE_ID,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdClienteCombo()));
			// Preenche colecao de arrecadador baseado no cliente escolhido
			Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){
				Iterator iteratorColecaoArrecadador = colecaoArrecadador.iterator();
				while(iteratorColecaoArrecadador.hasNext()){
					Arrecadador arrecadador = (Arrecadador) iteratorColecaoArrecadador.next();
					arrecadadorID = arrecadador.getId().toString();
				}
			}
		}

		// [FS0025 � Verificar exist�ncia da quadra]
		String nuQuadraInicial = null;
		String nuQuadraFinal = null;
		nuQuadraInicial = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getQuadraInicial();
		nuQuadraFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getQuadraFinal();

		if((idLocalidade != null && !idLocalidade.equals("")) && (codigoSetorComercial != null && !codigoSetorComercial.equals(""))
						&& (nuQuadraInicial != null && !nuQuadraInicial.equals("") && nuQuadraFinal != null && !nuQuadraFinal.equals(""))){

			// Se a quadra inicial e final forem a mesma ent�o basta verificar apenas uma.
			String[] arNuQuadra = nuQuadraInicial.equals(nuQuadraFinal) ? new String[] {nuQuadraInicial}
							: new String[] {nuQuadraInicial, nuQuadraFinal};

			for(int i = 0; i < arNuQuadra.length; i++){
				FiltroQuadra filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, arNuQuadra[i]));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, idLocalidade));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, codigoSetorComercial));

				Collection quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(quadras == null || quadras.isEmpty()){
					setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.quadra_inexistente"));
				}
			}
		}


		String codigoRotaInicial = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getRotaInicial();
		String idRotaInicial = null;
		if((idLocalidade != null && !idLocalidade.equals("")) && (codigoSetorComercial != null && !codigoSetorComercial.equals(""))
						&& (codigoRotaInicial != null && !codigoRotaInicial.equals(""))){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRotaInicial));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));

			Collection rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
			if(rotas != null && !rotas.isEmpty()){
				idRotaInicial = ((Rota) rotas.iterator().next()).getId().toString();
			}else{
				setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.rota_inicial_inexistente"));
			}
		}

		String codigoRotaFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getRotaFinal();
		String idRotaFinal = null;

		if((idLocalidadeFinal != null && !idLocalidadeFinal.equals(""))
						&& (codigoSetorComercialFinal != null && !codigoSetorComercialFinal.equals(""))
						&& (codigoRotaFinal != null && !codigoRotaFinal.equals(""))){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.limparListaParametros();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidadeFinal));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercialFinal));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRotaFinal));
			Collection rotas = null;
			rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
			if(rotas != null && !rotas.isEmpty()){
				idRotaFinal = ((Rota) rotas.iterator().next()).getId().toString();
			}else{
				setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.rota_final_inexistente"));
			}

		}

		CobrancaCriterio cobrancaCriterio = null;
		if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicadorCriterioComando().equals("2")
						&& inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio() != null
						&& !inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio().equals("")){

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();

			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
							.valueOf(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio())));

			Collection colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class.getName());

			if(colecaoCobrancaCriterio == null || colecaoCobrancaCriterio.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.criterio_inexistente");
			}

			cobrancaCriterio = (CobrancaCriterio) colecaoCobrancaCriterio.iterator().next();
		}

		// Comando de A��o Atividade Precedente
		CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComandoPrecedente = null;
		if(!Util.isVazioOuBranco(sessao.getAttribute("cobrancaAcaoAtividadeComandoPrecedente"))){

			cobrancaAcaoAtividadeComandoPrecedente = new CobrancaAcaoAtividadeComando();
			String idCobrancaAcaoAtividadeComando = (String) sessao.getAttribute("cobrancaAcaoAtividadeComandoPrecedente");
			cobrancaAcaoAtividadeComandoPrecedente.setId(Integer.valueOf(idCobrancaAcaoAtividadeComando));
		}

		Collection colecaoCobrancaAcaoAtividadeComando = new Vector();
		try{

			byte[] arquivo = new byte[0];

			if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getArquivoImoveis() != null){
				if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getArquivoImoveis().getFileName() != null
								&& !inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getArquivoImoveis().getFileName().equals("")){
					arquivo = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getArquivoImoveis().getFileData();
				}
			}

			colecaoCobrancaAcaoAtividadeComando = fachada.concluirComandoAcaoCobranca(null, null, null, null,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaGrupo(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getGerenciaRegional(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoCD(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdCliente(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getClienteRelacaoTipo(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicadorCriterioComando(), nuQuadraInicial,
							nuQuadraFinal, idRotaInicial, idRotaFinal,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemID(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoID(), null,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getUnidadeNegocio(), this
											.getUsuarioLogado(httpServletRequest), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
											.getTitulo(), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
											.getDescricaoSolicitacao(), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
											.getPrazoExecucao(), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
											.getQuantidadeMaximaDocumentos(), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
											.getIndicadorImoveisDebito(), inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
.getIndicadorGerarBoletimCadastro(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCodigoClienteSuperior(),
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getEmpresa(), null, arquivo, arrecadadorID,
							cobrancaAcaoAtividadeComandoPrecedente, cobrancaCriterio,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicadorGerarRelacaoDocumento(), null,
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getFormatoArquivo());
		}catch(FachadaException e){
			setarUrlLevantarExcecao(new ActionServletException(e.getMessage()));
		}catch(FileNotFoundException e){
			setarUrlLevantarExcecao(new ActionServletException("atencao.nao_arquivo"));
		}catch(IOException e){
			setarUrlLevantarExcecao(new ActionServletException("erro.sistema"));
		}

		// pesquisar cobranca acao
		// CobrancaAcao cobrancaAcao =
		// fachada.consultarCobrancaAcao(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao());

		// pesquisar cobranca atividade
		CobrancaAtividade cobrancaAtividade = fachada.consultarCobrancaAtividade(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
						.getCobrancaAtividade());

		montarPaginaSucesso(httpServletRequest, " " + colecaoCobrancaAcaoAtividadeComando.size()
						+ " A��o(�es) de cobran�a para a atividade " + cobrancaAtividade.getDescricaoCobrancaAtividade()
						+ " comandada(s) com sucesso.", "Inserir outro Comando de A��o de Cobran�a",
						"exibirInserirComandoAcaoCobrancaEventualCriterioRotaAction.do?limparForm=OK&menu=sim");

		return retorno;
	}

	private void extraiArquivo(InserirComandoAcaoCobrancaEventualCriterioRotaActionForm form){

		// cria uma string builder que recupera o txt e joga para o
		// controlador para o processamento

		Collection linhas = new Vector();

		int quantidadeRegistros = 0;

		try{
			if(form.getArquivoImoveis() != null && !form.getArquivoImoveis().getFileName().equals("")){
				// cria um contador para saber quantas linhas ter� o txt

				// abre o arquivo
				InputStreamReader reader = new InputStreamReader(form.getArquivoImoveis().getInputStream());
				BufferedReader buffer = new BufferedReader(reader);

				// StringBuffer linha = new StringBuffer();
				// cria uma variavel do tipo boolean
				boolean eof = false;
				// boolean primeiraLinha = true;
				// enquanto a variavel for false
				while(!eof){

					// pega a linha do arquivo
					String linhaLida = buffer.readLine();

					// se for a ultima linha do arquivo
					if(linhaLida != null){
						String matricula = linhaLida;
						if(!Util.isNumero(matricula, false, 0)){
							setarUrlLevantarExcecao(new ActionServletException("atencao.ha_matricula_nao_numerica_na_linha", null,
											(quantidadeRegistros + 1) + ""));
						}
						if(Util.obterInteger(matricula).equals(Util.obterInteger("0"))){
							setarUrlLevantarExcecao(new ActionServletException("atencao.ha_matricula_zero_na_linha", null,
											(quantidadeRegistros + 1) + ""));
						}
						if(getFachada().pesquisarImovel(Util.obterInteger(matricula)) == null){
							setarUrlLevantarExcecao(new ActionServletException("atencao.ha_matricula_inexistente_na_linha", null,
											(quantidadeRegistros + 1) + ""));
						}
						quantidadeRegistros++;
						linhas.add(linhaLida);
						// if(quantidadeRegistros > 100){
						// setarUrlLevantarExcecao(new
						// ActionServletException("atencao.arquivo_possui_mais_linhas_que_permitido"));
						// }
					}else{
						break;
					}

				}

				// fecha o arquivo
				buffer.close();
				reader.close();
				form.getArquivoImoveis().getInputStream().close();
			}else{
				return;
			}
		}catch(Exception e){
			setarUrlLevantarExcecao(new ActionServletException(e.getMessage(), null, (quantidadeRegistros + 1) + ""));
		}

		if(form.getArquivoImoveis() != null && linhas.size() < 1){
			setarUrlLevantarExcecao(new ActionServletException("atencao.arquivo_sem_dados", null, form.getArquivoImoveis().getFileName()));
		}
	}

	/**
	 * Met�do criado para setar a url de retorno e levantar a exce��o criada.
	 * 
	 * @param url
	 * @param actionServletException
	 */
	private void setarUrlLevantarExcecao(ActionServletException actionServletException){

		actionServletException.setUrlBotaoVoltar("/gsan/exibirInserirComandoAcaoCobrancaEventualCriterioRotaAction.do");
		throw actionServletException;
	}

}