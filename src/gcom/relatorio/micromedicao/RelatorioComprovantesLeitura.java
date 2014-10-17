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
 * classe respons�vel por criar as contas apartir do txt
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
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		RelatorioGerarDadosParaLeituraBean relatorioGerarDadosParaLeituraBean = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Collection<GerarDadosParaLeituraHelper> relatorioBeansOrdenado = new ArrayList<GerarDadosParaLeituraHelper>();

		Collection colecaoGerarDadosParaLeituraHelper = (ArrayList) getParametro("colecaoGerarDadosParaLeituraHelper");

		int totalPaginas = colecaoGerarDadosParaLeituraHelper.size();

		// dividir o relatorio
		relatorioBeansOrdenado = this.dividirColecaoOrdenando(colecaoGerarDadosParaLeituraHelper);

		GerarDadosParaLeituraHelper primeiroHelper = null;
		GerarDadosParaLeituraHelper segundoHelper = null;

		// Par�metros do relat�rio
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

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("tipoFormatoRelatorio", "R0083");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_COMPROVANTES_LEITURA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_GERAR_COMPROVANTES_LEITURA, idFuncionalidadeIniciada, null);
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