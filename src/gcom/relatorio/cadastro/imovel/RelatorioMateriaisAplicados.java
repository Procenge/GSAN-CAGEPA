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
 * [UC3135] Gerar Relat�rio de Materiais Aplicados
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
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
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

		// cole��o de beans do relat�rio
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

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecao != null && !colecao.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoIterator.hasNext()){

				RelatorioMateriaisAplicadosHelper helper = (RelatorioMateriaisAplicadosHelper) colecaoIterator.next();

				relatorioMateriaisAplicadosBean = new RelatorioMateriaisAplicadosBean(helper);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioMateriaisAplicadosBean);

			}

		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

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

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MATERIAIS_APLICADOS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MATERIAIS_APLICADOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
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