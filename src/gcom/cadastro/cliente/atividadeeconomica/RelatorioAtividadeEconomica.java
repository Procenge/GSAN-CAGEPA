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

package gcom.cadastro.cliente.atividadeeconomica;

import gcom.batch.Relatorio;
import gcom.cadastro.cliente.AtividadeEconomica;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * [UC3150] Manter Atividade Econômica
 * 
 * @author Anderson Italo
 * @date 30/06/2014
 */
public class RelatorioAtividadeEconomica
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAtividadeEconomica(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE_ECONOMICA);
	}

	@Deprecated
	public RelatorioAtividadeEconomica() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obtêm os parâmetros passados
		String codigo = (String) getParametro("codigo");
		String descricao = (String) getParametro("descricao");
		String indicadorUso = (String) getParametro("indicadorUso");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Collection<AtividadeEconomica> colecaoAtividadeEconomica = (Collection<AtividadeEconomica>) getParametro("colecaoAtividadeEconomica");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		for(Iterator iterator = colecaoAtividadeEconomica.iterator(); iterator.hasNext();){

			AtividadeEconomica atividadeEconomica = (AtividadeEconomica) iterator.next();
			RelatorioAtividadeEconomicaBean bean = new RelatorioAtividadeEconomicaBean();

			bean.setCodigo(atividadeEconomica.getCodigo());
			bean.setDescricao(atividadeEconomica.getDescricao());

			if(atividadeEconomica.getLigacaoEsgotoPerfil() != null){

				bean.setPerfilLigacaoEsgoto(atividadeEconomica.getLigacaoEsgotoPerfil().getDescricao());
			}

			if(atividadeEconomica.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_ATIVO)){

				bean.setIndicadorUso("1 - Ativo");
			}else{

				bean.setIndicadorUso("2 - Inativo");
			}

			relatorioBeans.add(bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		if(indicadorUso.equals(ConstantesSistema.INDICADOR_USO_ATIVO.toString())){

			indicadorUso = "1 - Ativo";
		}else if(indicadorUso.equals(ConstantesSistema.INDICADOR_USO_DESATIVO.toString())){

			indicadorUso = "2 - Inativo";
		}else{

			indicadorUso = "3 - Todos";
		}

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("codigo", codigo);
		parametros.put("descricao", descricao);
		parametros.put("indicadorUso", indicadorUso);
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){

			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE_ECONOMICA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_ATIVIDADE_ECONOMICA, idFuncionalidadeIniciada, null);
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

		// obtêm os parâmetros passados
		Collection<AtividadeEconomica> colecaoAtividadeEconomica = (Collection<AtividadeEconomica>) getParametro("colecaoAtividadeEconomica");

		retorno = colecaoAtividadeEconomica.size();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAtividadeEconomica", this);
	}

}
