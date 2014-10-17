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

package gcom.relatorio.cadastro.endereco;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
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
 * [UC0XXX] Gerar Relatório Logradouro Geral
 * 
 * @author Anderson Italo
 * @date 25/01/2011
 */
public class RelatorioLogradouroGeral
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioLogradouroGeral(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_LOGRADOURO_GERAL);
	}

	@Deprecated
	public RelatorioLogradouroGeral() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obtêm os parâmetros passados
		Integer idLocalidadeInicial = new Integer((String) getParametro("idLocalidadeInicial"));
		Integer idLocalidadeFinal = new Integer((String) getParametro("idLocalidadeFinal"));
		String nomeLocalidadeInicial = (String) getParametro("nomeLocalidadeInicial");
		String nomeLocalidadeFinal = (String) getParametro("nomeLocalidadeFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		// obtém as localidades do intervalo informado
		Collection colecaoLocalidade = fachada.pesquisarLocalidadePorIdIntervalo(idLocalidadeInicial, idLocalidadeFinal);

		for(Iterator iterator = colecaoLocalidade.iterator(); iterator.hasNext();){

			Object[] localidade = (Object[]) iterator.next();
			// obtém todos os logradouros do município associado a localidade
			Collection colecaoLogradouros = fachada.pesquisarLogradourosPorMunicipio(new Integer(localidade[2].toString()));

			int totalLogradourosPorLocalidade = 0;

			if(colecaoLogradouros != null && !colecaoLogradouros.isEmpty()){

				Iterator iColecaoLogradouros = colecaoLogradouros.iterator();
				while(iColecaoLogradouros.hasNext()){

					Object[] logradouro = (Object[]) iColecaoLogradouros.next();
					RelatorioLogradouroGeralBean bean = new RelatorioLogradouroGeralBean();

					// 4.1. Código da Localidade
					bean.setCodigoLocalidade(localidade[0].toString());

					// 4.2. Nome da Localidade
					bean.setNomeLocalidade(localidade[1].toString());

					// 4.3.1. Código do Logradouro
					// Adicionado Zeros a Esquerda para formatação do código do logradouro
					String formtLocalidade = Util.adicionarZerosEsqueda(1, logradouro[3].toString());
					bean.setCodigoLogradouro(formtLocalidade);

					// Descrição do Logradouro – Composta pelos seguintes campos:
					// 4.3.2. Tipo do Logradouro
					String descricaoLogradouro = logradouro[1].toString().trim();

					// 4.3.3. Titulo do Logradouro – se existir
					if(logradouro[2] != null && !logradouro[2].toString().equals("")){
						descricaoLogradouro += "  " + logradouro[2].toString().trim();
					}

					// 4.3.4. Nome do Logradouro
					if(logradouro[0] != null && !logradouro[0].toString().equals("")){
						descricaoLogradouro += "  " + logradouro[0].toString().trim();
					}

					bean.setDescricaoLogradouro(descricaoLogradouro);
					totalLogradourosPorLocalidade++;

					if(!iColecaoLogradouros.hasNext()){
						bean.setTotalLogradouroPorLocalidade(Integer.valueOf(totalLogradourosPorLocalidade));
					}

					relatorioBeans.add(bean);
				}
			}else{
				RelatorioLogradouroGeralBean bean = new RelatorioLogradouroGeralBean();

				// 4.1. Código da Localidade
				bean.setCodigoLocalidade(localidade[0].toString());

				// 4.2. Nome da Localidade
				bean.setNomeLocalidade(localidade[1].toString());
				bean.setTotalLogradouroPorLocalidade(0);
				relatorioBeans.add(bean);
			}
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("nomeLocalidadeInicial", nomeLocalidadeInicial);
		parametros.put("codigoLocalidadeInicial", idLocalidadeInicial.toString());
		parametros.put("nomeLocalidadeFinal", nomeLocalidadeFinal);
		parametros.put("codigoLocalidadeFinal", idLocalidadeFinal.toString());
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_LOGRADOURO_GERAL, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_LOGRADOURO_GERAL, idFuncionalidadeIniciada, null);
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

		Integer retorno = 0;

		if(new Integer((String) getParametro("totalRegistrosRelatorio")) != null){
			retorno = new Integer((String) getParametro("totalRegistrosRelatorio"));
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioLogradouroGeral", this);
	}

}