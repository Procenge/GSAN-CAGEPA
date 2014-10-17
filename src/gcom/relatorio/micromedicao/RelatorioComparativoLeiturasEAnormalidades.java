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
 * classe respons�vel por criar o relat�rio do comparativo de leituras e anormalidades
 * 
 * @author Rafael Corr�a
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

		Integer idGrupoFaturamento = (Integer) getParametro("idGrupoFaturamento");
		Integer idEmpresa = (Integer) getParametro("idEmpresa");
		Integer anoMes = (Integer) getParametro("anoMes");
		// Date dataUltimaAlteracao = (Date) getParametro("dataUltimaAlteracao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioComparativoLeiturasEAnormalidadesBean relatorioBean = null;

		Collection colecaoDadosRelatorioComparativoLeiturasEAnormalidades = fachada
						.pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(idGrupoFaturamento, idEmpresa, anoMes);

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoDadosRelatorioComparativoLeiturasEAnormalidades != null
						&& !colecaoDadosRelatorioComparativoLeiturasEAnormalidades.isEmpty()){

			Integer totalRegistrosRecebidos = 0;
			Integer totalRegistrosComLeitura = 0;
			Integer totalRegistrosComAnormalidade = 0;
			Integer totalRegistrosComLeituraEAnormalidade = 0;

			String empresa = "";
			String grupoFaturamento = "";

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoDadosRelatorioComparativoLeiturasEAnormalidadesIterator = colecaoDadosRelatorioComparativoLeiturasEAnormalidades
							.iterator();

			// la�o para criar a cole��o de par�metros da analise
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

								// C�digo do Setor Comercial
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

				// adiciona o bean a cole��o
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

							// C�digo do Setor Comercial
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

			// adiciona o bean a cole��o
			relatorioBeans.add(relatorioBean);

		}

		Collection colecaoAnormalidadesLeiturasRelatorio = fachada.pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(
						idGrupoFaturamento, idEmpresa, anoMes);

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoAnormalidadesLeiturasRelatorio != null && !colecaoAnormalidadesLeiturasRelatorio.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoAnormalidadesLeiturasRelatorioIterator = colecaoAnormalidadesLeiturasRelatorio.iterator();

			// la�o para criar a cole��o de par�metros da analise
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

								// C�digo do Setor Comercial
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

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}else{
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao usu�rio;
			throw new ActionServletException("atencao.relatorio.vazio");
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(anoMes));

		parametros.put("tipoFormatoRelatorio", "R0638");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_COMPARATIVOS_LEITURAS_E_ANORMALIDADES, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.COMPARATIVO_LEITURAS_E_ANORMALIDADES, idFuncionalidadeIniciada, null);
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

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioComparativoLeiturasEAnormalidades", this);

	}

}