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
import gcom.faturamento.bean.FaturaClienteResponsavelHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar as faturas do cliente responsavel
 * 
 * @author Rafael Pinto, Saulo Lima
 * @created 16/08/2007, 22/09/2008
 */
public class RelatorioFaturaClienteResponsavel
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioFaturaClienteResponsavel(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_FATURA_CLIENTE_RESPONSAVEL);
	}

	@Deprecated
	public RelatorioFaturaClienteResponsavel() {

		super(null, "");
	}

	/**
	 * <<Descrição do método>>
	 * 
	 * @param Parameter
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception exceção
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Collection<Integer> idsFaturas = (Collection<Integer>) getParametro("idsFaturas");

		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		RelatorioFaturaClienteResponsavelBean relatorioFaturaClienteResponsavelBean = null;

		Collection<FaturaClienteResponsavelHelper> colecaoFaturaClienteResponsavelHelper = new ArrayList<FaturaClienteResponsavelHelper>();

		try{
			colecaoFaturaClienteResponsavelHelper = fachada.gerarColecaoFaturaClienteResponsavelHelper(idsFaturas);
		}catch(ControladorException ex){
			ex.printStackTrace();
			throw new TarefaException("atencao.relatorio.erro.", ex);
		}

		Iterator<FaturaClienteResponsavelHelper> iterator = colecaoFaturaClienteResponsavelHelper.iterator();

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		while(iterator.hasNext()){

			FaturaClienteResponsavelHelper faturaClienteResponsavelHelper = iterator.next();
			relatorioFaturaClienteResponsavelBean = new RelatorioFaturaClienteResponsavelBean(faturaClienteResponsavelHelper);
			relatorioBeans.add(relatorioFaturaClienteResponsavelBean);

		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_FATURA_CLIENTE_RESPONSAVEL, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.FATURA_CLIENTE_RESPONSAVEL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioFaturaClienteResponsavel", this);

	}

}