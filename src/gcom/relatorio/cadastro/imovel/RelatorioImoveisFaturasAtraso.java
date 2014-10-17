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
 * 
 * GSANPCG
 * Vitor Hora
 */

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de imoveis com faturas em atraso
 * 
 * @author Bruno Barros
 * @created 10/12/2007
 */
public class RelatorioImoveisFaturasAtraso
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioImoveisFaturasAtraso(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_ATRASO);
	}

	@Deprecated
	public RelatorioImoveisFaturasAtraso() {

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

		// Totalizadores do Relatório
		int qtdTotalUsuarios = 0;
		int qtdTotalFaturasEmAtraso = 0;
		BigDecimal valorTotalFaturasEmAtraso = BigDecimal.ZERO;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltrarRelatorioImoveisFaturasAtrasoHelper filtro = (FiltrarRelatorioImoveisFaturasAtrasoHelper) getParametro("filtrarRelatorioImoveisFaturasAtrasoHelper");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioImoveisFaturasAtrasoBean relatorioImoveisFaturasAtrasoBean = null;

		Collection<RelatorioImoveisFaturasAtrasoHelper> colecao = fachada.pesquisarRelatorioImoveisFaturasAtraso(filtro);

		// se a coleção de parâmetros da analise não for vazia
		if(colecao != null && !colecao.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			Collection usuario = new ArrayList();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoIterator.hasNext()){

				RelatorioImoveisFaturasAtrasoHelper helper = (RelatorioImoveisFaturasAtrasoHelper) colecaoIterator.next();

				// Totalizador da quantidade de faturas em atraso
				if(!usuario.contains(helper.getNomeCliente())){
					usuario.add(helper.getNomeCliente());
					qtdTotalUsuarios = qtdTotalUsuarios + 1;
				}

				// Totalizador da quantidade de faturas em atraso
				qtdTotalFaturasEmAtraso = qtdTotalFaturasEmAtraso + helper.getQuantidadeFaturasAtraso().intValue();

				// Totalizador do valor das faturas em atraso
				valorTotalFaturasEmAtraso = valorTotalFaturasEmAtraso.add(helper.getValorFaturasAtraso());

				// Instância do relatório
				relatorioImoveisFaturasAtrasoBean = new RelatorioImoveisFaturasAtrasoBean(helper);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioImoveisFaturasAtrasoBean);
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

		parametros.put("qtdTotalUsuarios", Util.agruparNumeroEmMilhares(qtdTotalUsuarios));

		parametros.put("qtdTotalFaturasEmAtraso", Util.agruparNumeroEmMilhares(qtdTotalFaturasEmAtraso));

		parametros.put("valorTotalFaturasEmAtraso", Util.formatarMoedaReal(valorTotalFaturasEmAtraso));

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_ATRASO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.IMOVEIS_FATURAS_ATRASO, idFuncionalidadeIniciada, null);
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

		retorno = Fachada.getInstancia().pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(
						(FiltrarRelatorioImoveisFaturasAtrasoHelper) getParametro("filtrarRelatorioImoveisFaturasAtrasoHelper"));

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioImoveisFaturasAtraso", this);

	}

}