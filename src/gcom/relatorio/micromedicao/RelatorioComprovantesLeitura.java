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
import gcom.micromedicao.bean.GerarDadosParaLeituraHelper;
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
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar as contas apartir do txt
 * 
 * @author *****
 * @created 28/09/2007
 */
public class RelatorioComprovantesLeitura
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComprovantesLeitura(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GERAR_COMPROVANTES_LEITURA);
	}

	public RelatorioComprovantesLeitura() {

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
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		RelatorioGerarDadosParaLeituraBean relatorioGerarDadosParaLeituraBean = null;

		// coleção de beans do relatório

		List relatorioBeans = new ArrayList();

		Collection<GerarDadosParaLeituraHelper> relatorioBeansOrdenado = new ArrayList<GerarDadosParaLeituraHelper>();

		Collection colecaoGerarDadosParaLeituraHelper = (ArrayList) getParametro("colecaoGerarDadosParaLeituraHelper");

		int totalPaginas = colecaoGerarDadosParaLeituraHelper.size();

		// dividir o relatorio
		relatorioBeansOrdenado = this.dividirColecaoOrdenando(colecaoGerarDadosParaLeituraHelper);

		GerarDadosParaLeituraHelper primeiroHelper = null;
		GerarDadosParaLeituraHelper segundoHelper = null;

		// Parâmetros do relatório
		Map parametros = new HashMap();

		if(!Util.isVazioOrNulo(relatorioBeansOrdenado)){
			int pagina = 1;
			int pagina1 = 1;
			int pagina2 = 0;

			if((totalPaginas % 2) == 0){
				pagina2 = (totalPaginas / 2) + 1;
			}else{
				pagina2 = (totalPaginas / 2) + 2;
			}

			for(GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper : relatorioBeansOrdenado){

				if((primeiroHelper == null) && (segundoHelper == null)){
					primeiroHelper = gerarDadosParaLeituraHelper;
					pagina1++;
				}else{
					segundoHelper = gerarDadosParaLeituraHelper;
					pagina2++;
				}

				if((primeiroHelper != null) && (segundoHelper != null)){

					parametros.put("codigoRota", primeiroHelper.getCodigoRota());
					parametros.put("descricaoLocalidade", primeiroHelper.getDescricaoLocalidade());
					parametros.put("anoMesReferencia", primeiroHelper.getAnoMesReferncia());
					parametros.put("grupo", primeiroHelper.getGrupo());
					parametros.put("codigoSetor", primeiroHelper.getCodigoSetor());
					parametros.put("dataPrevistaFaturamento", primeiroHelper.getDataPrevistaFaturamento());

					parametros.put("codigoRota1", segundoHelper.getCodigoRota());
					parametros.put("descricaoLocalidade1", segundoHelper.getDescricaoLocalidade());
					parametros.put("anoMesReferencia1", segundoHelper.getAnoMesReferncia());
					parametros.put("grupo1", segundoHelper.getGrupo());
					parametros.put("codigoSetor1", segundoHelper.getCodigoSetor());
					parametros.put("dataPrevistaFaturamento1", segundoHelper.getDataPrevistaFaturamento());

					primeiroHelper.setNumeroPagina(Util.adicionarZerosEsquedaNumero(3, pagina + ""));
					segundoHelper.setNumeroPagina(Util.adicionarZerosEsquedaNumero(3, pagina + ""));
					pagina++;

					relatorioGerarDadosParaLeituraBean = new RelatorioGerarDadosParaLeituraBean(primeiroHelper, segundoHelper);

					relatorioBeans.add(relatorioGerarDadosParaLeituraBean);

					primeiroHelper = null;
					segundoHelper = null;
				}
			}
			if(primeiroHelper != null && segundoHelper == null){

				parametros.put("codigoRota", primeiroHelper.getCodigoRota());
				parametros.put("descricaoLocalidade", primeiroHelper.getDescricaoLocalidade());
				parametros.put("anoMesReferencia", primeiroHelper.getAnoMesReferncia());
				parametros.put("grupo", primeiroHelper.getGrupo());
				parametros.put("codigoSetor", primeiroHelper.getCodigoSetor());
				parametros.put("dataPrevistaFaturamento", primeiroHelper.getDataPrevistaFaturamento());

				primeiroHelper.setNumeroPagina(Util.adicionarZerosEsquedaNumero(3, pagina + ""));

				relatorioBeans.add(new RelatorioGerarDadosParaLeituraBean(primeiroHelper, segundoHelper));
			}

		}

		Fachada fachada = Fachada.getInstancia();

		// __________________________________________________________________

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("tipoFormatoRelatorio", "R0083");

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_COMPROVANTES_LEITURA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_GERAR_COMPROVANTES_LEITURA, idFuncionalidadeIniciada, null);
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

		AgendadorTarefas.agendarTarefa("RelatorioComprovantesLeitura", this);

	}

	public Collection dividirColecaoOrdenando(Collection colecao){

		List retorno = new ArrayList();
		List colecaoTmp = (List) colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add(colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add(colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}

}