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
 * [UC3150] Manter Atividade Econ�mica
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

		// obt�m os par�metros passados
		String codigo = (String) getParametro("codigo");
		String descricao = (String) getParametro("descricao");
		String indicadorUso = (String) getParametro("indicadorUso");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Collection<AtividadeEconomica> colecaoAtividadeEconomica = (Collection<AtividadeEconomica>) getParametro("colecaoAtividadeEconomica");

		// monta a cole��o de beans do relat�rio
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

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		if(indicadorUso.equals(ConstantesSistema.INDICADOR_USO_ATIVO.toString())){

			indicadorUso = "1 - Ativo";
		}else if(indicadorUso.equals(ConstantesSistema.INDICADOR_USO_DESATIVO.toString())){

			indicadorUso = "2 - Inativo";
		}else{

			indicadorUso = "3 - Todos";
		}

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("codigo", codigo);
		parametros.put("descricao", descricao);
		parametros.put("indicadorUso", indicadorUso);
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){

			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE_ECONOMICA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_ATIVIDADE_ECONOMICA, idFuncionalidadeIniciada, null);
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

		// obt�m os par�metros passados
		Collection<AtividadeEconomica> colecaoAtividadeEconomica = (Collection<AtividadeEconomica>) getParametro("colecaoAtividadeEconomica");

		retorno = colecaoAtividadeEconomica.size();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAtividadeEconomica", this);
	}

}
