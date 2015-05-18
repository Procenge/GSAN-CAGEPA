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
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * [UC0083] Gerar Dados para Leitura
 */
public class RelatorioGerarDadosParaleitura
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioGerarDadosParaleitura(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GERAR_DADOS_PARA_LEITURA);
	}

	public RelatorioGerarDadosParaleitura() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		StringBuilder arquivoTipoE = null;
		StringBuilder arquivoTipoF = null;
		StringBuilder arquivoTipoP = null;
		StringBuilder arquivoTipoT = null;
		String nomeArquivoTipoE = null;
		String nomeArquivoTipoF = null;
		String nomeArquivoTipoP = null;
		String nomeArquivoTipoT = null;

		String parametroModeloArquivoLeiturasMicroletor = null;

		try{

			parametroModeloArquivoLeiturasMicroletor = ParametroMicromedicao.P_MODELO_ARQUIVO_LEITURA.executar();
		}catch(ControladorException e){

			throw new TarefaException("atencao.sistemaparametro_inexistente", null, "P_MODELO_ARQUIVO_LEITURA");
		}

		// 1 - Padr�o GSAN
		if(parametroModeloArquivoLeiturasMicroletor.equals(ConstantesSistema.UM.toString())){

			RelatorioGerarDadosParaLeituraBean relatorioGerarDadosParaLeituraBean = null;

			// cole��o de beans do relat�rio
			List relatorioBeans = new ArrayList();

			List<GerarDadosParaLeituraHelper> colecaoGerarDadosParaLeituraHelper = (List<GerarDadosParaLeituraHelper>) getParametro("colecaoGerarDadosParaLeituraHelper");
			String indicadorExibirTotalizacoes = (String) getParametro("indicadorExibirTotalizacoes");

			String descricaoArquivoGerado = "";

			// Par�metros do relat�rio
			Map parametros = new HashMap();
			parametros.put("indicadorExibirTotalizacoes", indicadorExibirTotalizacoes);

			if(colecaoGerarDadosParaLeituraHelper != null && !colecaoGerarDadosParaLeituraHelper.isEmpty()){

				// Ordena a cole��o por localidade, setor e rota
				List sortFields = new ArrayList();
				sortFields.add(new BeanComparator("codigoLocalidade"));
				sortFields.add(new BeanComparator("codigoSetor"));
				sortFields.add(new BeanComparator("codigoRota"));

				ComparatorChain multiSort = new ComparatorChain(sortFields);
				Collections.sort(colecaoGerarDadosParaLeituraHelper, multiSort);

				Iterator itera = colecaoGerarDadosParaLeituraHelper.iterator();

				boolean primeiraVez = true;

				while(itera.hasNext()){
					GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper = (GerarDadosParaLeituraHelper) itera.next();

					// caso seja a primeira vez, ent�o recupera os parametros do
					// inicio do relat�rio
					if(primeiraVez){
						String anoMesReferncia = gerarDadosParaLeituraHelper.getAnoMesReferncia();
						String grupo = gerarDadosParaLeituraHelper.getGrupo();

						parametros.put("codigoRota", gerarDadosParaLeituraHelper.getCodigoRota());
						parametros.put("descricaoLocalidade", gerarDadosParaLeituraHelper.getDescricaoLocalidade());
						parametros.put("anoMesReferencia", anoMesReferncia);
						parametros.put("grupo", grupo);
						parametros.put("codigoSetor", gerarDadosParaLeituraHelper.getCodigoSetor());
						parametros.put("dataPrevistaFaturamento", gerarDadosParaLeituraHelper.getDataPrevistaFaturamento());

						if(Util.isVazioOuBranco(descricaoArquivoGerado)){
							if(grupo != null){
								descricaoArquivoGerado = "GRUPO " + grupo + " - ";
							}

							if(anoMesReferncia != null){
								descricaoArquivoGerado = descricaoArquivoGerado + anoMesReferncia;
							}
						}

						primeiraVez = false;
					}

					relatorioGerarDadosParaLeituraBean = new RelatorioGerarDadosParaLeituraBean(gerarDadosParaLeituraHelper);

					relatorioBeans.add(relatorioGerarDadosParaLeituraBean);

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

			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_DADOS_PARA_LEITURA, parametros, ds, tipoFormatoRelatorio);

			try{

				persistirRelatorioConcluido(retorno, Relatorio.GERAR_DADOS_PARA_LEITURA, idFuncionalidadeIniciada, descricaoArquivoGerado);
			}catch(ControladorException e){

				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}

		}else{

			// SAAE
			arquivoTipoE = (StringBuilder) getParametro("arquivoTipoE");
			nomeArquivoTipoE = (String) getParametro("nomeArquivoTipoE");

			arquivoTipoF = (StringBuilder) getParametro("arquivoTipoF");
			nomeArquivoTipoF = (String) getParametro("nomeArquivoTipoF");

			arquivoTipoP = (StringBuilder) getParametro("arquivoTipoP");
			nomeArquivoTipoP = (String) getParametro("nomeArquivoTipoP");

			arquivoTipoT = (StringBuilder) getParametro("arquivoTipoT");
			nomeArquivoTipoT = (String) getParametro("nomeArquivoTipoT");

			try{

				retorno = this.getBytesFromFileZip(arquivoTipoE, nomeArquivoTipoE, arquivoTipoF, nomeArquivoTipoF, arquivoTipoP,
								nomeArquivoTipoP, arquivoTipoT, nomeArquivoTipoT);

				persistirRelatorioConcluido(retorno, Relatorio.GERAR_DADOS_PARA_LEITURA, idFuncionalidadeIniciada,
								nomeArquivoTipoE.substring(0, nomeArquivoTipoE.length() - 1));
			}catch(ControladorException e){

				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}catch(IOException e){

				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}catch(Exception e){

				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuilder arquivoTipoE, String nomeArquivoTipoE, StringBuilder arquivoTipoF,
					String nomeArquivoTipoF, StringBuilder arquivoTipoP, String nomeArquivoTipoP, StringBuilder arquivoTipoT,
					String nomeArquivoTipoT) throws IOException, Exception{

		File compactado = new File("FaturamentoConvencionalMicroColetor.zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivo1 = new File(nomeArquivoTipoE);

		BufferedWriter outDados = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo1.getAbsolutePath())));
		outDados.write(arquivoTipoE.toString());
		outDados.flush();
		outDados.close();

		File arquivo2 = new File(nomeArquivoTipoF);

		BufferedWriter outDados2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo2.getAbsolutePath())));
		outDados2.write(arquivoTipoF.toString());
		outDados2.flush();
		outDados2.close();

		File arquivo3 = new File(nomeArquivoTipoP);

		BufferedWriter outDados3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo3.getAbsolutePath())));
		outDados3.write(arquivoTipoP.toString());
		outDados3.flush();
		outDados3.close();

		File arquivo4 = new File(nomeArquivoTipoT);

		BufferedWriter outDados4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo4.getAbsolutePath())));
		outDados4.write(arquivoTipoT.toString());
		outDados4.flush();
		outDados4.close();

		ZipUtil.adicionarArquivos(zos, arquivo1, arquivo2, arquivo3, arquivo4);

		zos.close();
		arquivo1.delete();
		arquivo2.delete();
		arquivo3.delete();
		arquivo4.delete();

		byte[] retorno = this.getBytesFromFile(compactado);

		compactado.delete();

		return retorno;
	}

	private byte[] getBytesFromFile(File file) throws IOException{

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if(length > Integer.MAX_VALUE){
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0){
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if(offset < bytes.length){
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioGerarDadosParaleitura", this);

	}

}