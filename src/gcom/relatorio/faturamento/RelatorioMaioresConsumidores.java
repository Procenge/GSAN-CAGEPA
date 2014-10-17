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

package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.RelatorioMaioresConsumidoresHelper;
import gcom.gui.ActionServletException;
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
import java.util.*;

/**
 * [] Gerar Relatório Maiores Consumidores
 * 
 * @author Victon Malcolm
 * @since 13/11/2013
 */
public class RelatorioMaioresConsumidores
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioMaioresConsumidores(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MAIORES_CONSUMIDORES);
	}

	@Deprecated
	public RelatorioMaioresConsumidores() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer localidade = Integer.parseInt((String) getParametro("localidade"));
		Integer anoMes = (Integer) getParametro("anoMes");
		Integer registros = Integer.parseInt((String) getParametro("registros"));
		Integer tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioMaioresConsumidoresBean relatorioBean = null;
		Collection colecaoRelatorioMaioresConsumidoresHelper = fachada.pesquisarDadosRelatorioMaioresConsumidores(anoMes, localidade,
						registros);
		if(colecaoRelatorioMaioresConsumidoresHelper != null && !colecaoRelatorioMaioresConsumidoresHelper.isEmpty()){
			Iterator colecaoRelatorioMaioresConsumidoresHelperIterator = colecaoRelatorioMaioresConsumidoresHelper
							.iterator();
			String referencia = "";//
			String descricaoLocalidade = "";//
			String setor = "";//
			String quadra = "";//
			String tipo = "";//
			String matricula = "";//
			String nome = "";//
			Integer consumo = new Integer("0");//
			BigDecimal valorConta = new BigDecimal("0");
			// laço para criar a coleção de parâmetros da analise
			while(colecaoRelatorioMaioresConsumidoresHelperIterator.hasNext()){
				RelatorioMaioresConsumidoresHelper relatorioMaioresConsumidoresHelper = (RelatorioMaioresConsumidoresHelper) colecaoRelatorioMaioresConsumidoresHelperIterator
								.next();
				if(relatorioMaioresConsumidoresHelper.getReferencia() != null){
					referencia = Util.formatarAnoMesParaMesAno(relatorioMaioresConsumidoresHelper.getReferencia()
									.intValue());
				}
				if(relatorioMaioresConsumidoresHelper.getMatricula() != null){
					matricula = relatorioMaioresConsumidoresHelper.getMatricula().toString();
				}
				if(relatorioMaioresConsumidoresHelper.getLocalidade() != null){
					descricaoLocalidade = localidade + " - " + relatorioMaioresConsumidoresHelper.getLocalidade();
				}
				if(relatorioMaioresConsumidoresHelper.getSetor() != null){
					setor = relatorioMaioresConsumidoresHelper.getSetor().toString();
				}
				if(relatorioMaioresConsumidoresHelper.getQuadra() != null){
					quadra = relatorioMaioresConsumidoresHelper.getQuadra().toString();
				}
				if(relatorioMaioresConsumidoresHelper.getTipo() != null){
					tipo = relatorioMaioresConsumidoresHelper.getTipo();
				}
				if(relatorioMaioresConsumidoresHelper.getNome() != null){
					nome = relatorioMaioresConsumidoresHelper.getNome();
				}
				if(relatorioMaioresConsumidoresHelper.getConsumo() != null){
					consumo = relatorioMaioresConsumidoresHelper.getConsumo().intValue();
				}
				if(relatorioMaioresConsumidoresHelper.getValorConta() != null){
					valorConta = relatorioMaioresConsumidoresHelper.getValorConta();
				}
				relatorioBean = new RelatorioMaioresConsumidoresBean(referencia, descricaoLocalidade, setor, quadra, tipo, matricula, nome,
								consumo,
								valorConta);
				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________
		// Parâmetros do relatório
		Map parametros = new HashMap();
		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(anoMes));
		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MAIORES_CONSUMIDORES, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MAIORES_CONSUMIDORES, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------tava
		// retorna o relatório gerado

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer localidade = Integer.parseInt((String) getParametro("localidade"));
		Integer anoMes = (Integer) getParametro("anoMes");
		Integer registros = Integer.parseInt((String) getParametro("registros"));
		Collection colecaoRelatorioMaioresConsumidoresHelper = Fachada.getInstancia().pesquisarDadosRelatorioMaioresConsumidores(anoMes,
						localidade, registros);

		return colecaoRelatorioMaioresConsumidoresHelper.size();
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioMaioresConsumidores", this);
	}
}