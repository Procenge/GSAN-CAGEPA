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

package gcom.relatorio.cadastro.imovel;

import gcom.atendimentopublico.ordemservico.*;
import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * [UC3135] Gerar Relatório de Materiais Aplicados
 * 
 * @author Felipe Rosacruz
 * @date 31/01/2014
 */
public class RelatorioMateriaisAplicados
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public static String P_ID_LOCALIDADE = "P_ID_LOCALIDADE";

	public static String P_CD_SETOR = "P_CD_SETOR";

	public static String P_DATA_INICIAL = "P_DATA_INICIAL";

	public static String P_DATA_FINAL = "P_DATA_FINAL";

	public static String P_TIPO_SERVICO = "P_TIPO_SERVICO";

	public static String P_MATERIAL = "P_MATERIAL";

	public static String P_EQUIPE = "P_EQUIPE";

	public RelatorioMateriaisAplicados(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MATERIAIS_APLICADOS);
	}

	@Deprecated
	public RelatorioMateriaisAplicados() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Integer idLocalidade = (Integer) getParametro(P_ID_LOCALIDADE);
		Integer cdSetorComercial = (Integer) getParametro(P_CD_SETOR);
		Date dataExecucaoInicial = (Date) getParametro(P_DATA_INICIAL);
		Date dataExecucaoFinal = (Date) getParametro(P_DATA_FINAL);
		Collection<Integer> colecaoIdServicoTipo = (Collection<Integer>) getParametro(P_TIPO_SERVICO);
		Collection<Integer> colecaoIdMaterial = (Collection<Integer>) getParametro(P_MATERIAL);
		Collection<Integer> colecaoIdEquipe = (Collection<Integer>) getParametro(P_EQUIPE);

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioMateriaisAplicadosBean relatorioMateriaisAplicadosBean = null;

		Collection<RelatorioMateriaisAplicadosHelper> colecao = fachada.obterDadosRelatorioMateriaisAplicados(idLocalidade,
						cdSetorComercial, dataExecucaoInicial, dataExecucaoFinal, colecaoIdServicoTipo, colecaoIdMaterial, colecaoIdEquipe);

		ComparatorChain sortMateriaisAplicados = new ComparatorChain();
		sortMateriaisAplicados.addComparator(new BeanComparator("idLocalidade"));
		sortMateriaisAplicados.addComparator(new BeanComparator("cdSetorComercial"));
		sortMateriaisAplicados.addComparator(new BeanComparator("idServicoTipo"));
		sortMateriaisAplicados.addComparator(new BeanComparator("tmExecucao"));
		sortMateriaisAplicados.addComparator(new BeanComparator("idOrdemServico"));
		sortMateriaisAplicados.addComparator(new BeanComparator("qtMaterial"));

		Collections.sort((List<RelatorioMateriaisAplicadosHelper>) colecao, sortMateriaisAplicados);

		// se a coleção de parâmetros da analise não for vazia
		if(colecao != null && !colecao.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoIterator.hasNext()){

				RelatorioMateriaisAplicadosHelper helper = (RelatorioMateriaisAplicadosHelper) colecaoIterator.next();

				relatorioMateriaisAplicadosBean = new RelatorioMateriaisAplicadosBean(helper);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioMateriaisAplicadosBean);

			}

		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		if(idLocalidade != null){
			Localidade localidade = fachada.pesquisarLocalidadeDigitada(idLocalidade);
			parametros.put("P_LOCALIDADE", localidade.getDescricaoComId());
		}
		if(cdSetorComercial != null){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, cdSetorComercial));
			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSetorComercial,
							SetorComercial.class.getName()));
			parametros.put("P_SETOR_COMERCIAL", setorComercial.getDescricaoComCodigo());
		}
		if(dataExecucaoInicial != null){
			parametros.put("P_DATA_EXEC_INICIAL", Util.formatarData(dataExecucaoInicial));
		}
		if(dataExecucaoFinal != null){
			parametros.put("P_DATA_EXEC_FINAL", Util.formatarData(dataExecucaoFinal));
		}
		if(colecaoIdServicoTipo != null){
			String PARAM_TIPO_SERVICO = "";
			Iterator<Integer> iterator = colecaoIdServicoTipo.iterator();
			while(iterator.hasNext()){
				Integer id = iterator.next();
				FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, id));
				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroServicoTipo,
								ServicoTipo.class.getName()));
				PARAM_TIPO_SERVICO += servicoTipo.getId() + "-" + servicoTipo.getDescricao();
				if(iterator.hasNext()){
					PARAM_TIPO_SERVICO += ", ";
				}
			}
			parametros.put("P_TIPO_SERVICO", PARAM_TIPO_SERVICO);
		}
		if(colecaoIdEquipe != null){
			String PARAM_EQUIPE = "";
			Iterator<Integer> iterator = colecaoIdEquipe.iterator();
			while(iterator.hasNext()){
				Integer id = iterator.next();
				FiltroEquipe filtroEquipe = new FiltroEquipe();
				filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, id));
				Equipe equipe = (Equipe) Util
								.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroEquipe, Equipe.class.getName()));
				PARAM_EQUIPE += equipe.getId() + "-" + equipe.getNome();
				if(iterator.hasNext()){
					PARAM_EQUIPE += ", ";
				}
			}
			parametros.put("P_EQUIPE", PARAM_EQUIPE);
		}
		if(colecaoIdMaterial != null){
			String PARAM_MATERIAL = "";
			Iterator<Integer> iterator = colecaoIdMaterial.iterator();
			while(iterator.hasNext()){
				Integer id = iterator.next();
				FiltroMaterial filtroMaterial = new FiltroMaterial();
				filtroMaterial.adicionarParametro(new ParametroSimples(filtroMaterial.ID, id));
				Material material = (Material) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroMaterial,
								Material.class.getName()));
				PARAM_MATERIAL += material.getId() + "-" + material.getDescricao();
				if(iterator.hasNext()){
					PARAM_MATERIAL += ", ";
				}
			}
			parametros.put("P_MATERIAL", PARAM_MATERIAL);
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MATERIAIS_APLICADOS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MATERIAIS_APLICADOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;
		
		Integer idLocalidade = (Integer) getParametro(P_ID_LOCALIDADE);
		Integer cdSetorComercial = (Integer) getParametro(P_CD_SETOR);
		Date dataExecucaoInicial = (Date) getParametro(P_DATA_INICIAL);
		Date dataExecucaoFinal = (Date) getParametro(P_DATA_FINAL);
		Collection<Integer> colecaoIdServicoTipo = (Collection<Integer>) getParametro(P_TIPO_SERVICO);
		Collection<Integer> colecaoIdMaterial = (Collection<Integer>) getParametro(P_MATERIAL);
		Collection<Integer> colecaoIdEquipe = (Collection<Integer>) getParametro(P_EQUIPE);

		retorno = Fachada
						.getInstancia()
						.obterDadosRelatorioMateriaisAplicados(idLocalidade, cdSetorComercial, dataExecucaoInicial, dataExecucaoFinal,
										colecaoIdServicoTipo, colecaoIdMaterial, colecaoIdEquipe).size();
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioMateriaisAplicados", this);

	}

}