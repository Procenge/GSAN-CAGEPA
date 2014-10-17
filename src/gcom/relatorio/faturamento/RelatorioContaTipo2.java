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
import gcom.faturamento.bean.EmitirContaTipo2Helper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.conta.RelatorioContaTipo2Bean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * classe responsável por criar as contas
 * 
 * @author Rafael Pinto, Saulo Lima
 * @created 27/06/2007, 31/10/2008
 */
public class RelatorioContaTipo2
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public final static Integer QUANTIDADE_ITENS_POR_PAGINA = Integer.valueOf(7);

	public RelatorioContaTipo2(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTA_TIPO_2);
	}

	@Deprecated
	public RelatorioContaTipo2() {

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

		System.out.println("*************************************");
		System.out.println("ENTROU NO EXECUTAR RELATORIO DE CONTA");
		System.out.println("*************************************");

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// valor de retorno
		byte[] retorno = null;

		Collection<EmitirContaTipo2Helper> colecaoContaTipo2Helper = (Collection<EmitirContaTipo2Helper>) getParametro("colecaoHelperContas");

		// coleção de beans do relatório
		List<RelatorioContaTipo2Bean> relatorioBeans = new ArrayList<RelatorioContaTipo2Bean>();

		if(colecaoContaTipo2Helper != null && !colecaoContaTipo2Helper.isEmpty()){
			Iterator<EmitirContaTipo2Helper> contaHelperIterator = colecaoContaTipo2Helper.iterator();
			while(contaHelperIterator.hasNext()){

				RelatorioContaTipo2Bean relatorioContaTipo2Bean = null;

				EmitirContaTipo2Helper contaHelper1 = contaHelperIterator.next();
				EmitirContaTipo2Helper contaHelper2 = null;
				if(contaHelperIterator.hasNext()){
					contaHelper2 = contaHelperIterator.next();
				}

				if(contaHelper2 == null){
					contaHelper2 = new EmitirContaTipo2Helper();
					contaHelper2.setInscricao(null);
				}

				relatorioContaTipo2Bean = new RelatorioContaTipo2Bean(contaHelper1, contaHelper2);

				relatorioBeans.add(relatorioContaTipo2Bean);

			}

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String descricaoArquivo = null;
		if(getParametro("descricaoArquivo") != null){
			descricaoArquivo = (String) getParametro("descricaoArquivo");
		}

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTA_TIPO_2, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTA, idFuncionalidadeIniciada, descricaoArquivo);
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

		AgendadorTarefas.agendarTarefa("RelatorioContaTipo2", this);

	}

}