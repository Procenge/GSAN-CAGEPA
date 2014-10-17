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
 * [UC0243] Inserir Comando de Ação de Cobrança - Tipo de Comando Cronograma
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 * @author Virgínia Melo
 * @date 08/11/2008
 *       Alterações no [UC0243] para a v0.06
 */
public class InserirComandoAcaoCobrancaEventualCriterioComandoConcluirAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		InserirComandoAcaoCobrancaEventualCriterioComandoActionForm form = null;

		if(sessao.getAttribute("inserirComandoAcaoCobrancaEventualCriterioComandoActionForm") != null){
			form = (InserirComandoAcaoCobrancaEventualCriterioComandoActionForm) sessao
							.getAttribute("inserirComandoAcaoCobrancaEventualCriterioComandoActionForm");
		}

		Collection colecaoClienteArrecadador = (Collection) sessao.getAttribute("colecaoClienteArrecadador");

		if(Util.isVazioOrNulo(colecaoClienteArrecadador)){
			if(Util.isVazioOuBranco(form.getCobrancaAcao())){
				String[] cobrancaAcao = form.getCobrancaAcao();
				for(int i = 0; i < cobrancaAcao.length; i++){
					if(cobrancaAcao[i].equals(CobrancaAcao.COBRANCA_BANCARIA.toString())){
						throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Arrecadador");
					}
				}
			}
		}

		extraiArquivo(form);

		String idComando = httpServletRequest.getParameter("idComando");

		String idLocalidade = form.getLocalidadeOrigemID();
		String codigoSetorComercial = form.getSetorComercialOrigemCD();

		String idLocalidadeFinal = form.getLocalidadeDestinoID();
		String codigoSetorComercialFinal = form.getSetorComercialDestinoCD();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));
			// pesquisa
			Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades == null || localidades.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.localidade_inicial_inexistente");
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
				Collection<SetorComercial> setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				if(setorComerciais == null || setorComerciais.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.setor_inicial_inexistente");
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
			Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades == null || localidades.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.localidade_final_inexistente");
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
				Collection<SetorComercial> setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
				if(setorComerciais == null || setorComerciais.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.setor_final_inexistente");
				}
			}
		}

		FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
		String arrecadadorID = "";
		// Arrecadador
		if(form.getIdClienteCombo() != null && !form.getIdClienteCombo().equals("-1")){
			// Inclui a obejeto de cliente no filtro de arrecadador
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			// filtra arrecadador pelo cliente
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CLIENTE_ID, form.getIdClienteCombo()));
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

		String codigoRotaInicial = form.getRotaInicial();
		String idRotaInicial = null;
		if((idLocalidade != null && !idLocalidade.equals("")) && (codigoSetorComercial != null && !codigoSetorComercial.equals(""))
						&& (codigoRotaInicial != null && !codigoRotaInicial.equals(""))){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRotaInicial));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));

			Collection<Rota> rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
			if(rotas != null && !rotas.isEmpty()){
				idRotaInicial = (rotas.iterator().next()).getId().toString();
			}else{
				throw new ActionServletException("atencao.pesquisa.rota_inicial_inexistente");
			}
		}

		String codigoRotaFinal = form.getRotaFinal();
		String idRotaFinal = null;

		if((idLocalidadeFinal != null && !idLocalidadeFinal.equals(""))
						&& (codigoSetorComercialFinal != null && !codigoSetorComercialFinal.equals(""))
						&& (codigoRotaFinal != null && !codigoRotaFinal.equals(""))){
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.limparListaParametros();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidadeFinal));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercialFinal));
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRotaFinal));
			Collection<Rota> rotas = null;
			rotas = fachada.pesquisar(filtroRota, Rota.class.getName());
			if(rotas != null && !rotas.isEmpty()){
				idRotaFinal = (rotas.iterator().next()).getId().toString();
			}else{
				throw new ActionServletException("atencao.pesquisa.rota_final_inexistente");
			}
		}

		Collection colecaoCobrancaAcaoAtividadeComando = new Vector();
		try{

			byte[] arquivo = new byte[0];

			if(form.getArquivoImoveis() != null){
				if(form.getArquivoImoveis().getFileName() != null && !form.getArquivoImoveis().getFileName().equals("")){
					arquivo = form.getArquivoImoveis().getFileData();
				}
			}
			colecaoCobrancaAcaoAtividadeComando = fachada.concluirComandoAcaoCobranca(null, null, null, null, form.getCobrancaAcao(), form
							.getCobrancaAtividade(), form.getCobrancaGrupo(), form.getGerenciaRegional(), form.getLocalidadeOrigemID(),
							form.getLocalidadeDestinoID(), form.getSetorComercialOrigemCD(), form.getSetorComercialDestinoCD(), form
.getIdCliente(), form.getClienteRelacaoTipo(), form.getIndicador(), null/* quadraInicial */, null/* quadraFinal */, idRotaInicial, idRotaFinal,
							form.getSetorComercialOrigemID(), form.getSetorComercialDestinoID(), idComando, form.getUnidadeNegocio(), this
											.getUsuarioLogado(httpServletRequest), form.getTitulo(), form.getDescricaoSolicitacao(), form
											.getPrazoExecucao(), form.getQuantidadeMaximaDocumentos(), form.getIndicadorImoveisDebito(),
							form.getIndicadorGerarBoletimCadastro(), form.getCodigoClienteSuperior(), form.getEmpresa(), form
											.getValorLimiteEmissao(), arquivo, arrecadadorID, null, null, form
											.getIndicadorGerarRelacaoDocumento(), null, null);
		}catch(FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// pesquisar cobranca atividade
		CobrancaAtividade cobrancaAtividade = fachada.consultarCobrancaAtividade(form.getCobrancaAtividade());

		montarPaginaSucesso(httpServletRequest, colecaoCobrancaAcaoAtividadeComando.size() + " Ação(ões) de cobrança para a atividade "
						+ cobrancaAtividade.getDescricaoCobrancaAtividade() + " comandada(s) com sucesso.",
						"Inserir outro Comando de Ação de Cobrança", "exibirInserirComandoAcaoCobrancaAction.do?limparForm=OK&menu=sim");

		return retorno;
	}

	private void extraiArquivo(InserirComandoAcaoCobrancaEventualCriterioComandoActionForm form){

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
							throw new ActionServletException("atencao.ha_matricula_nao_numerica_na_linha", null, (quantidadeRegistros + 1)
											+ "");
						}
						if(Util.obterInteger(matricula).equals(Util.obterInteger("0"))){
							throw new ActionServletException("atencao.ha_matricula_zero_na_linha", null, (quantidadeRegistros + 1) + "");
						}

						// thiagosantos (considerando o comentário ao lado foi modificada para o
						// verificarExistencia):
						// consulta caríssima, passou +-30min para um arquivo com 50mil registros,
						// deve ser trocada por um consulta que retorna apenas um boolean dizendo se
						// existe ou não o id fornecido, e não a materialização completa de cada
						// objeto imóvel.
						// Troquei a busca pelo objeto completo pela pergunta da quantidade de
						// registros com a matrícula
						if(this.getFachada().verificarExistenciaImovel(Util.obterInteger(matricula)) < 1){
							throw new ActionServletException("atencao.ha_matricula_inexistente_na_linha", null, (quantidadeRegistros + 1)
											+ "");
						}
						quantidadeRegistros++;
						linhas.add(linhaLida);
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

}
