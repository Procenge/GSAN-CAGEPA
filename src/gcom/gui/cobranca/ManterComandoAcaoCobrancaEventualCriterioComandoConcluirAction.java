/**
 * 
 */
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

package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAtividade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
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
 * [UC0244] Manter Comando de Ação de Conbrança
 * 
 * @author Rafael Santos
 * @since 24/04/2006
 * @author Virgínia Melo
 * @date 14/11/2008
 *       Alterações para a v0.06
 * @author Virgínia Melo
 * @date 06/08/2009
 *       Incluindo campo valorLimiteEmissao
 *       Não utilizar mais campos de referencia da conta.
 */
public class ManterComandoAcaoCobrancaEventualCriterioComandoConcluirAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm = null;
		if(sessao.getAttribute("manterComandoAcaoCobrancaDetalhesActionForm") != null){
			manterComandoAcaoCobrancaDetalhesActionForm = (ManterComandoAcaoCobrancaDetalhesActionForm) sessao
							.getAttribute("manterComandoAcaoCobrancaDetalhesActionForm");
		}

		extraiArquivo(manterComandoAcaoCobrancaDetalhesActionForm);

		CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = null;

		if(sessao.getAttribute("cobrancaAcaoAtividadeComando") != null){
			cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) sessao.getAttribute("cobrancaAcaoAtividadeComando");
		}

		String idComando = httpServletRequest.getParameter("idComando");

		String idLocalidade = manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeOrigemID();
		String codigoSetorComercial = manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialOrigemCD();

		String idLocalidadeFinal = manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeDestinoID();
		String codigoSetorComercialFinal = manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialDestinoCD();

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
		if(manterComandoAcaoCobrancaDetalhesActionForm.getIdClienteCombo() != null
						&& !manterComandoAcaoCobrancaDetalhesActionForm.getIdClienteCombo().equals("-1")){
			// Inclui a obejeto de cliente no filtro de arrecadador
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			// filtra arrecadador pelo cliente
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CLIENTE_ID,
							manterComandoAcaoCobrancaDetalhesActionForm.getIdClienteCombo()));
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

		String rotaInicial = manterComandoAcaoCobrancaDetalhesActionForm.getRotaInicial();

		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
				if(rotaInicial != null && !rotaInicial.toString().trim().equalsIgnoreCase("")){
					FiltroRota filtroRota = new FiltroRota();
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaInicial));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));

					Collection rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
					String idRotaInicial = null;
					if(rotas != null && !rotas.isEmpty()){
						idRotaInicial = ((Rota) rotas.iterator().next()).getId().toString();
					}else{
						setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.rota_inicial_inexistente"));
					}
				}
			}
		}

		String rotaFinal = manterComandoAcaoCobrancaDetalhesActionForm.getRotaFinal();

		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){
			if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
				if(rotaFinal != null && !rotaFinal.toString().trim().equalsIgnoreCase("")){

					FiltroRota filtroRota = new FiltroRota();
					filtroRota.limparListaParametros();
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));
					filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaFinal));
					Collection rotas = null;
					rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
					String idRotaFinal = null;
					if(rotas != null && !rotas.isEmpty()){
						idRotaFinal = ((Rota) rotas.iterator().next()).getId().toString();
					}else{
						setarUrlLevantarExcecao(new ActionServletException("atencao.pesquisa.rota_final_inexistente"));
					}
				}
			}
		}

		try{
			fachada.concluirManterComandoAcaoCobranca(null, null, null, null,
							manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaAcao(), manterComandoAcaoCobrancaDetalhesActionForm
											.getCobrancaAtividade(), manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaGrupo(),
							manterComandoAcaoCobrancaDetalhesActionForm.getGerenciaRegional(), manterComandoAcaoCobrancaDetalhesActionForm
											.getLocalidadeOrigemID(), manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeDestinoID(),
							manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialOrigemCD(),
							manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialDestinoCD(),
							manterComandoAcaoCobrancaDetalhesActionForm.getIdCliente(), manterComandoAcaoCobrancaDetalhesActionForm
											.getClienteRelacaoTipo(), manterComandoAcaoCobrancaDetalhesActionForm
											.getIndicadorCriterioComando(), null/* quadraInicial */, null/* quadraFinal */, rotaInicial,
							rotaFinal, manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialOrigemID(),
							manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialDestinoID(), cobrancaAcaoAtividadeComando.getId()
											.toString(), cobrancaAcaoAtividadeComando.getRealizacao(), cobrancaAcaoAtividadeComando
											.getComando(), cobrancaAcaoAtividadeComando.getUltimaAlteracao(), cobrancaAcaoAtividadeComando
											.getUsuario(), cobrancaAcaoAtividadeComando.getEmpresa(), cobrancaAcaoAtividadeComando
											.getQuantidadeDocumentos(), cobrancaAcaoAtividadeComando.getValorDocumentos(),
							cobrancaAcaoAtividadeComando.getQuantidadeItensCobrados(), idComando,
							manterComandoAcaoCobrancaDetalhesActionForm.getUnidadeNegocio(), manterComandoAcaoCobrancaDetalhesActionForm
											.getTitulo(), manterComandoAcaoCobrancaDetalhesActionForm.getDescricaoSolicitacao(),
							manterComandoAcaoCobrancaDetalhesActionForm.getPrazoExecucao(), manterComandoAcaoCobrancaDetalhesActionForm
											.getQuantidadeMaximaDocumentos(), manterComandoAcaoCobrancaDetalhesActionForm
											.getIndicadorImoveisDebito(), manterComandoAcaoCobrancaDetalhesActionForm
											.getIndicadorGerarBoletimCadastro(), manterComandoAcaoCobrancaDetalhesActionForm
											.getCodigoClienteSuperior(), null, manterComandoAcaoCobrancaDetalhesActionForm
											.getArquivoImoveis().getFileName().equals("") ? new byte[0]
											: manterComandoAcaoCobrancaDetalhesActionForm.getArquivoImoveis().getFileData(), arrecadadorID,
							null, null, manterComandoAcaoCobrancaDetalhesActionForm.getIndicadorGerarRelacaoDocumento(), null);
		}catch(FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * fachada.concluirManterComandoAcaoCobranca(
		 * manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaAcao(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getCobrancaAtividade(),
		 * manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaGrupo(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getGerenciaRegional(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getLocalidadeOrigemID(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getLocalidadeDestinoID(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getSetorComercialOrigemCD(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getSetorComercialDestinoCD(),
		 * manterComandoAcaoCobrancaDetalhesActionForm.getIndicador(),
		 * rotaInicial,
		 * rotaFinal,
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getSetorComercialOrigemID(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getSetorComercialDestinoID(),
		 * cobrancaAcaoAtividadeComando.getId().toString(),
		 * cobrancaAcaoAtividadeComando.getRealizacao(),
		 * cobrancaAcaoAtividadeComando.getComando(),
		 * cobrancaAcaoAtividadeComando.getUltimaAlteracao(),
		 * cobrancaAcaoAtividadeComando.getUsuario(),
		 * cobrancaAcaoAtividadeComando.getEmpresa(),
		 * idComando,
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getUnidadeNegocio(),
		 * manterComandoAcaoCobrancaDetalhesActionForm.getTitulo(),
		 * manterComandoAcaoCobrancaDetalhesActionForm
		 * .getDescricaoSolicitacao(),
		 * null,
		 * null,
		 * null);
		 */

		// pesquisar cobranca acao
		CobrancaAcao cobrancaAcao = fachada.consultarCobrancaAcao(manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaAcao());

		// pesquisar cobranca atividade
		CobrancaAtividade cobrancaAtividade = fachada.consultarCobrancaAtividade(manterComandoAcaoCobrancaDetalhesActionForm
						.getCobrancaAtividade());

		montarPaginaSucesso(httpServletRequest, "A Ação " + cobrancaAcao.getDescricaoCobrancaAcao() + " para a atividade "
						+ cobrancaAtividade.getDescricaoCobrancaAtividade() + " comandada com sucesso",
						"Manter outro Comando de Ação de Cobrança", "exibirManterComandoAcaoCobrancaAction.do?menu=sim");

		return retorno;
	}

	private void extraiArquivo(ManterComandoAcaoCobrancaDetalhesActionForm form){

		// cria uma string builder que recupera o txt e joga para o
		// controlador para o processamento

		Collection linhas = new Vector();

		int quantidadeRegistros = 0;

		try{
			if(form.getArquivoImoveis() != null && !form.getArquivoImoveis().getFileName().equals("")){
				// cria um contador para saber quantas linhas terá o txt

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
						if(quantidadeRegistros > 100){
							setarUrlLevantarExcecao(new ActionServletException("atencao.arquivo_possui_mais_linhas_que_permitido"));
						}
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
			throw new ActionServletException(e.getMessage(), null, (quantidadeRegistros + 1) + "");
		}

		if(form.getArquivoImoveis() != null && linhas.size() < 1){
			throw new ActionServletException("atencao.arquivo_sem_dados", null, form.getArquivoImoveis().getFileName());
		}
	}

	/**
	 * Metódo criado para setar a url de retorno e levantar a exceção criada.
	 * 
	 * @param url
	 * @param actionServletException
	 */
	private void setarUrlLevantarExcecao(ActionServletException actionServletException){

		actionServletException.setUrlBotaoVoltar("/gsan/exibirManterComandoAcaoCobrancaEventualCriterioComandoAction.do");
		throw actionServletException;
	}

}
