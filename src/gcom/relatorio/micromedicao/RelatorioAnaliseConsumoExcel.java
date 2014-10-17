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
import gcom.micromedicao.bean.RelatorioAnaliseConsumoDetailHelper;
import gcom.micromedicao.bean.RelatorioAnaliseConsumoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.*;
import java.util.*;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Vivianne Sousa
 * @date 06/11/2007
 */

public class RelatorioAnaliseConsumoExcel
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAnaliseConsumoExcel(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ANALISE_CONSUMO_EXCEL);
	}

	@Deprecated
	public RelatorioAnaliseConsumoExcel() {

		super(null, "");
	}

	private Collection<RelatorioAnaliseConsumoBean> inicializarBeanRelatorio(Collection colecaoAnaliseConsumoHelper,
					String mesAnoFaturamento){

		Fachada fachada = Fachada.getInstancia();

		Collection<RelatorioAnaliseConsumoBean> retorno = new ArrayList<RelatorioAnaliseConsumoBean>();

		Iterator iter = colecaoAnaliseConsumoHelper.iterator();
		while(iter.hasNext()){

			RelatorioAnaliseConsumoHelper relatorioAnaliseConsumoHelper = (RelatorioAnaliseConsumoHelper) iter.next();

			try{

				Collection colecaoObjeto = fachada.pesquisarLeiturasImovel(relatorioAnaliseConsumoHelper.getMatriculaImovel(), Util
								.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamento));

				if(!colecaoObjeto.isEmpty()){

					Object[] objeto = (Object[]) colecaoObjeto.iterator().next();

					String leitura = "";
					// leitura anterior faturada
					if(objeto[1] != null){
						leitura = objeto[1].toString();
					}
					// leitura atual informada
					if(objeto[0] != null){
						leitura = leitura + "/" + objeto[0].toString();
					}
					// hidrometro
					String numeroHidrometro = "";
					if(objeto[2] != null){
						numeroHidrometro = objeto[2].toString();
					}
					// ligacao agua
					String ligacaoAgua = "";
					if(objeto[3] != null){
						ligacaoAgua = objeto[3].toString();
					}
					// sit ligacao agua
					String situacaoLigacaoAgua = "";
					if(objeto[4] != null){
						situacaoLigacaoAgua = objeto[4].toString();
					}
					// leiturista
					String leiturista = "";
					if(objeto[5] != null){
						leiturista = objeto[5].toString();
					}

					// anormalidade Leitura
					String anormalidadeLeitura = null;
					if(objeto[6] != null){
						anormalidadeLeitura = objeto[6].toString();
					}

					// anormalidade Consumo
					String anormalidadeConsumo = null;
					if(objeto[7] != null){
						anormalidadeConsumo = objeto[7].toString();
					}

					String anormalidade = "";
					if(anormalidadeLeitura != null && anormalidadeConsumo != null){
						anormalidade = anormalidadeLeitura + " / " + anormalidadeConsumo;
					}else{
						if(anormalidadeLeitura != null){
							anormalidade = anormalidadeLeitura;
						}else if(anormalidadeConsumo != null){
							anormalidade = anormalidadeConsumo;
						}
					}

					// situacao LigacaoEsgoto
					String situacaoLigacaoEsgoto = "";
					if(objeto[8] != null){
						situacaoLigacaoEsgoto = objeto[8].toString();
					}

					relatorioAnaliseConsumoHelper.setLeituraAtual(leitura);
					relatorioAnaliseConsumoHelper.setHidrometro(numeroHidrometro);
					relatorioAnaliseConsumoHelper.setLigacaoAgua(ligacaoAgua);
					relatorioAnaliseConsumoHelper.setSituacaoLigacaoAgua(situacaoLigacaoAgua);
					relatorioAnaliseConsumoHelper.setLeiturista(leiturista);
					// relatorioAnaliseConsumoHelper.setAnormalidadeConsumo(anormalidadeConsumo);
					// relatorioAnaliseConsumoHelper.setAnormalidadeLeitura(anormalidadeLeitura);
					relatorioAnaliseConsumoHelper.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);
					relatorioAnaliseConsumoHelper.setAnormalidade(anormalidade);

					// O relatório exibe informações de consumo e medição dos últimos 5 meses,
					// a partir do anomes do grupo de faturamente vigente.
					Integer quantidadeMeses = 3;
					String anoMesFaturamento = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamento);
					Integer anoMesFaturamentoMinimo = Util.subtraiAteSeisMesesAnoMesReferencia(Util
									.converterStringParaInteger(anoMesFaturamento), quantidadeMeses);
					Integer idImovel = Util.converterStringParaInteger(relatorioAnaliseConsumoHelper.getMatriculaImovel());

					Collection colecaoObjetoDetail = fachada.pesquisarConsumoPorQuantidadeMeses(idImovel, anoMesFaturamentoMinimo, Util
									.converterStringParaInteger(anoMesFaturamento));
					Collection<RelatorioAnaliseConsumoDetailBean> colecaoDetail = new ArrayList<RelatorioAnaliseConsumoDetailBean>();
					if(!colecaoObjetoDetail.isEmpty()){

						Iterator itt = colecaoObjetoDetail.iterator();
						while(itt.hasNext()){

							Object[] objetoDetail = (Object[]) itt.next();

							RelatorioAnaliseConsumoDetailHelper relatorioAnaliseConsumoDetailHelper = new RelatorioAnaliseConsumoDetailHelper();

							String anoMes = "";
							if(objetoDetail[0] != null){
								Integer valorAnoMes = (Integer) objetoDetail[0];
								anoMes = Util.formatarAnoMesSemBarraParaMesAnoComBarra(valorAnoMes);
							}
							String numeroLeituraAtualFaturamento = "";
							if(objetoDetail[1] != null){
								numeroLeituraAtualFaturamento = objetoDetail[1].toString();
							}
							String numeroConsumoMedidoMes = "";
							if(objetoDetail[2] != null){
								numeroConsumoMedidoMes = objetoDetail[2].toString();
							}
							String consumoMedioImovel = "";
							if(objetoDetail[3] != null){
								consumoMedioImovel = objetoDetail[3].toString();
							}
							String numeroConsumoFaturadoMes = "";
							if(objetoDetail[4] != null){
								numeroConsumoFaturadoMes = objetoDetail[4].toString();
							}
							String leituraAnormalidadeFatu = "";
							if(objetoDetail[5] != null){
								leituraAnormalidadeFatu = objetoDetail[5].toString();
							}
							relatorioAnaliseConsumoDetailHelper.setAnoMes(anoMes);
							relatorioAnaliseConsumoDetailHelper.setNumeroLeituraAtualFaturamento(numeroLeituraAtualFaturamento);
							relatorioAnaliseConsumoDetailHelper.setNumeroConsumoMedidoMes(numeroConsumoMedidoMes);
							relatorioAnaliseConsumoDetailHelper.setConsumoMedioImovel(consumoMedioImovel);
							relatorioAnaliseConsumoDetailHelper.setNumeroConsumoFaturadoMes(numeroConsumoFaturadoMes);
							relatorioAnaliseConsumoDetailHelper.setLeituraAnormalidadeFatu(leituraAnormalidadeFatu);

							RelatorioAnaliseConsumoDetailBean relatorioAnaliseConsumoDetailBean = new RelatorioAnaliseConsumoDetailBean(
											relatorioAnaliseConsumoDetailHelper);
							colecaoDetail.add(relatorioAnaliseConsumoDetailBean);

						}

						RelatorioAnaliseConsumoBean relatorioAnaliseConsumoBean = new RelatorioAnaliseConsumoBean(
										relatorioAnaliseConsumoHelper, colecaoDetail);
						retorno.add(relatorioAnaliseConsumoBean);
					}

				}

			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return retorno;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		// Collection colecaoEmitirContaHelper = (Collection)
		// getParametro("colecaoEmitirContaHelper");
		String idsImovel = (String) getParametro("idsImovel");
		String mesAnoFaturamento = (String) getParametro("mesAnoFaturamento");
		String mesAnoArrecadacao = (String) getParametro("mesAnoArrecadacao");
		String grupo = (String) getParametro("grupo");

		if(idsImovel == null || idsImovel.equals("")){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}
		Collection<RelatorioAnaliseConsumoHelper> colecaoAnaliseConsumoHelper = fachada.pesquisarDadosImovel(idsImovel);

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Collection<RelatorioAnaliseConsumoBean> colecaoBean = this.inicializarBeanRelatorio(colecaoAnaliseConsumoHelper, mesAnoFaturamento);

		// valor de retorno
		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		// Parâmetros do relatório
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("colecaoRetorno", colecaoBean);

		parametros.put("mesAnoFaturamento", mesAnoFaturamento);
		parametros.put("mesAnoArrecadacao", mesAnoArrecadacao);
		parametros.put("grupo", grupo);
		parametros.put("nomeEmpresa", sistemaParametro.getNomeEmpresa());

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		try{

			File template = (File) getParametro("template");

			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, parametros);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioAnaliseConsumoTemplate.xls");

			FileInputStream inputStream = new FileInputStream("relatorioAnaliseConsumoTemplate.xls");
			byte[] temp = new byte[1024];
			int numBytesRead = 0;

			while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
				retorno.write(temp, 0, numBytesRead);
			}

			inputStream.close();

		}catch(FileNotFoundException e){
			throw new TarefaException("atencao.planilha_template_nao_encontrado", e);
		}catch(IOException e1){
			throw new TarefaException("", e1);
		}

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.ANALISE_CONSUMO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno.toByteArray();

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		String idsImovel = (String) getParametro("idsImovel");
		Integer qtde = 0;
		if(idsImovel != null && !idsImovel.equals("")){
			qtde = idsImovel.split(",").length;
		}

		return qtde;
	}

	public Integer retornarTotalRegistrosRelatorio(){
		
		Fachada fachada = Fachada.getInstancia();

		String idsImovel = (String) getParametro("idsImovel");
		String mesAnoFaturamento = (String) getParametro("mesAnoFaturamento");
		Integer qtde = 0;

		Collection<RelatorioAnaliseConsumoHelper> colecaoAnaliseConsumoHelper = fachada.pesquisarDadosImovel(idsImovel);

		Iterator iter = colecaoAnaliseConsumoHelper.iterator();
		while(iter.hasNext()){

			RelatorioAnaliseConsumoHelper relatorioAnaliseConsumoHelper = (RelatorioAnaliseConsumoHelper) iter.next();

			try{

				qtde = qtde
								+ fachada.pesquisarLeiturasImovelCount(relatorioAnaliseConsumoHelper.getMatriculaImovel(),
								Util
								.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamento));
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return qtde;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAnaliseConsumo", this);
	}

	public void novaPlanilhaTipoHSSF(final HSSFWorkbook hSSFWorkbook, final String destFileName) throws IOException{

		Workbook workbook = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		workbook.write(fileOut);
		fileOut.close();
	}

}
