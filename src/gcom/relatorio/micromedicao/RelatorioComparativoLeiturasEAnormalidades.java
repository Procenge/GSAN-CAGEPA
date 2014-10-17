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

package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.micromedicao.bean.AnormalidadeLeituraHelper;
import gcom.micromedicao.bean.ComparativoLeiturasEAnormalidadesRelatorioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório do comparativo de leituras e anormalidades
 * 
 * @author Rafael Corrêa
 * @created 12/04/2007
 */
public class RelatorioComparativoLeiturasEAnormalidades
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComparativoLeiturasEAnormalidades(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_COMPARATIVOS_LEITURAS_E_ANORMALIDADES);
	}

	@Deprecated
	public RelatorioComparativoLeiturasEAnormalidades() {

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

		Integer idGrupoFaturamento = (Integer) getParametro("idGrupoFaturamento");
		Integer idEmpresa = (Integer) getParametro("idEmpresa");
		Integer anoMes = (Integer) getParametro("anoMes");
		// Date dataUltimaAlteracao = (Date) getParametro("dataUltimaAlteracao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioComparativoLeiturasEAnormalidadesBean relatorioBean = null;

		Collection colecaoDadosRelatorioComparativoLeiturasEAnormalidades = fachada
						.pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(idGrupoFaturamento, idEmpresa, anoMes);

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoDadosRelatorioComparativoLeiturasEAnormalidades != null
						&& !colecaoDadosRelatorioComparativoLeiturasEAnormalidades.isEmpty()){

			Integer totalRegistrosRecebidos = 0;
			Integer totalRegistrosComLeitura = 0;
			Integer totalRegistrosComAnormalidade = 0;
			Integer totalRegistrosComLeituraEAnormalidade = 0;

			String empresa = "";
			String grupoFaturamento = "";

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoDadosRelatorioComparativoLeiturasEAnormalidadesIterator = colecaoDadosRelatorioComparativoLeiturasEAnormalidades
							.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoDadosRelatorioComparativoLeiturasEAnormalidadesIterator.hasNext()){

				ComparativoLeiturasEAnormalidadesRelatorioHelper comparativoLeiturasEAnormalidadesRelatorioHelper = (ComparativoLeiturasEAnormalidadesRelatorioHelper) colecaoDadosRelatorioComparativoLeiturasEAnormalidadesIterator
								.next();

				totalRegistrosRecebidos = totalRegistrosRecebidos
								+ comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosRecebidos();
				totalRegistrosComLeitura = totalRegistrosComLeitura
								+ comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComLeitura();
				totalRegistrosComAnormalidade = totalRegistrosComAnormalidade
								+ comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComAnormalidade();
				totalRegistrosComLeituraEAnormalidade = totalRegistrosComLeituraEAnormalidade
								+ comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComLeituraEAnormalidade();

				if(comparativoLeiturasEAnormalidadesRelatorioHelper.getIdEmpresa() != null){
					empresa = comparativoLeiturasEAnormalidadesRelatorioHelper.getIdEmpresa() + " - "
									+ comparativoLeiturasEAnormalidadesRelatorioHelper.getNomeEmpresa();
				}

				grupoFaturamento = comparativoLeiturasEAnormalidadesRelatorioHelper.getIdGrupoFaturamento().toString();

				relatorioBean = new RelatorioComparativoLeiturasEAnormalidadesBean(
				// Grupo de Faturamento
								grupoFaturamento,

								// Empresa
								empresa,

								// Id do Setor Comercial
								comparativoLeiturasEAnormalidadesRelatorioHelper.getIdSetorComercial().toString(),

								// Id da Localidade
								comparativoLeiturasEAnormalidadesRelatorioHelper.getIdLocalidade().toString(),

								// Código do Setor Comercial
								comparativoLeiturasEAnormalidadesRelatorioHelper.getCodigoSetorComercial().toString(),

								// Registros Recebidos
								comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosRecebidos().toString(),

								// Registros c/ Leitura
								comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComLeitura().toString(),

								// Registros c/ Anormalidade
								comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComAnormalidade().toString(),

								// Registros c/ Leitura e Anormalidade
								comparativoLeiturasEAnormalidadesRelatorioHelper.getRegistrosComLeituraEAnormalidade().toString(),

								// Anormalidade de Leitura
								null,

								// Quantidade de Anormalidades de Leitura
								null);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}

			relatorioBean = new RelatorioComparativoLeiturasEAnormalidadesBean(
			// Grupo de Faturamento
							grupoFaturamento,

							// Empresa
							empresa,

							// Id do Setor Comercial
							"",

							// Id da Localidade
							"T O T A L",

							// Código do Setor Comercial
							"",

							// Registros Recebidos
							totalRegistrosRecebidos.toString(),

							// Registros c/ Leitura
							totalRegistrosComLeitura.toString(),

							// Registros c/ Anormalidade
							totalRegistrosComAnormalidade.toString(),

							// Registros c/ Leitura e Anormalidade
							totalRegistrosComLeituraEAnormalidade.toString(),

							// Anormalidade de Leitura
							null,

							// Quantidade de Anormalidades de Leitura
							null);

			// adiciona o bean a coleção
			relatorioBeans.add(relatorioBean);

		}

		Collection colecaoAnormalidadesLeiturasRelatorio = fachada.pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(
						idGrupoFaturamento, idEmpresa, anoMes);

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoAnormalidadesLeiturasRelatorio != null && !colecaoAnormalidadesLeiturasRelatorio.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoAnormalidadesLeiturasRelatorioIterator = colecaoAnormalidadesLeiturasRelatorio.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoAnormalidadesLeiturasRelatorioIterator.hasNext()){

				AnormalidadeLeituraHelper anormalidadeLeituraHelper = (AnormalidadeLeituraHelper) colecaoAnormalidadesLeiturasRelatorioIterator
								.next();

				String anormalidadeLeitura = "";
				if(anormalidadeLeituraHelper.getIdAnormalidadeLeitura() != null){
					anormalidadeLeitura = anormalidadeLeituraHelper.getIdAnormalidadeLeitura() + " - "
									+ anormalidadeLeituraHelper.getDescricaoAnormalidadeLeitura();
				}

				String empresa = "";

				if(anormalidadeLeituraHelper.getIdEmpresa() != null){
					empresa = anormalidadeLeituraHelper.getIdEmpresa() + " - " + anormalidadeLeituraHelper.getNomeEmpresa();
				}

				String grupoFaturamento = anormalidadeLeituraHelper.getIdGrupoFaturamento().toString();

				relatorioBean = new RelatorioComparativoLeiturasEAnormalidadesBean(

				// Grupo de Faturamento
								grupoFaturamento,

								// Empresa
								empresa,

								// Id do Setor Comercial
								null,

								// Id da Localidade
								null,

								// Código do Setor Comercial
								null,

								// Registros Recebidos
								null,

								// Registros c/ Leitura
								null,

								// Registros c/ Anormalidade
								null,

								// Registros c/ Leitura e Anormalidade
								null,

								// Anormalidade de Leitura
								anormalidadeLeitura,

								// Quantidade de Anormalidades de Leitura
								anormalidadeLeituraHelper.getQuantidade().toString());

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}else{
			// Caso a pesquisa não retorne nenhum resultado comunica ao usuário;
			throw new ActionServletException("atencao.relatorio.vazio");
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(anoMes));

		parametros.put("tipoFormatoRelatorio", "R0638");

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_COMPARATIVOS_LEITURAS_E_ANORMALIDADES, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.COMPARATIVO_LEITURAS_E_ANORMALIDADES, idFuncionalidadeIniciada, null);
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

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioComparativoLeiturasEAnormalidades", this);

	}

}