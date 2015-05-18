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
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author S�vio Luiz
 * @version 1.0
 */

public class RelatorioRegistrarLeiturasAnormalidades
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioRegistrarLeiturasAnormalidades(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES);
	}

	@Deprecated
	public RelatorioRegistrarLeiturasAnormalidades() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		Collection<MedicaoHistorico> colecaoMedicaoHistoricoRelatorio = (Collection<MedicaoHistorico>) getParametro("colecaoMedicaoHistoricoRelatorio");
		String grupoFaturamento = (String) getParametro("faturamentoGrupo");
		String localidade = (String) getParametro("localidade");
		String empresa = (String) getParametro("empresa");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String nomeArquivo = (String) getParametro("nomeArquivo");

		Fachada fachada = Fachada.getInstancia();

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		// Lista q sera passada como parametro para o subrelatorio
		Map<LeituraAnormalidade, Integer> mapaRelatorioBeansSubReport = new HashMap<LeituraAnormalidade, Integer>();

		RelatorioRegistrarLeiturasAnormalidadesBean relatorioBean = null;

		// se a cole��o de par�metros da analise n�o for vazia
		if(!Util.isVazioOrNulo(colecaoMedicaoHistoricoRelatorio)){
			Localidade localidadeAtual = colecaoMedicaoHistoricoRelatorio.iterator().next().getLocalidade();
			Rota rotaAtual = colecaoMedicaoHistoricoRelatorio.iterator().next().getRota();
			Integer quantidadeMovimentosLeitura = 0;
			Integer quantidadeMovimentosAnormalidade = 0;
			Integer quantidadeMovimentosLeituraAnormalidade = 0;
			Integer quantidadeMovimentos = 0;
			for(MedicaoHistorico medicaoHistorico : colecaoMedicaoHistoricoRelatorio){
				if(!localidadeAtual.getId().equals(medicaoHistorico.getLocalidade().getId())
								|| !rotaAtual.getId().equals(medicaoHistorico.getRota().getId())){
					relatorioBean = new RelatorioRegistrarLeiturasAnormalidadesBean(localidadeAtual.getId(), rotaAtual.getId(),
									quantidadeMovimentosLeitura, quantidadeMovimentosAnormalidade, quantidadeMovimentosLeituraAnormalidade,
									quantidadeMovimentos);
					relatorioBeans.add(relatorioBean);
					localidadeAtual = medicaoHistorico.getLocalidade();
					rotaAtual = medicaoHistorico.getRota();
					quantidadeMovimentosLeitura = 0;
					quantidadeMovimentosAnormalidade = 0;
					quantidadeMovimentosLeituraAnormalidade = 0;
					quantidadeMovimentos = 0;
				}

				if(medicaoHistorico.getLeituraAtualInformada() != null && medicaoHistorico.getLeituraAnormalidadeInformada() == null){
					quantidadeMovimentosLeitura++;
				}else if((medicaoHistorico.getLeituraAtualInformada() == null || medicaoHistorico.getLeituraAtualInformada().equals(
								Integer.valueOf(0)))
								&& medicaoHistorico.getLeituraAnormalidadeInformada() != null){
					incrementarAnormalidades(mapaRelatorioBeansSubReport, medicaoHistorico);
					quantidadeMovimentosAnormalidade++;
				}else if(medicaoHistorico.getLeituraAtualInformada() != null && medicaoHistorico.getLeituraAnormalidadeInformada() != null){
					quantidadeMovimentosLeituraAnormalidade++;
					incrementarAnormalidades(mapaRelatorioBeansSubReport, medicaoHistorico);
				}
				quantidadeMovimentos++;
			}
			relatorioBean = new RelatorioRegistrarLeiturasAnormalidadesBean(
							localidadeAtual.getId(),
							rotaAtual.getId(),
							quantidadeMovimentosLeitura,
							quantidadeMovimentosAnormalidade,
							quantidadeMovimentosLeituraAnormalidade,
							(quantidadeMovimentos - (quantidadeMovimentosLeitura + quantidadeMovimentosAnormalidade + quantidadeMovimentosLeituraAnormalidade)));
			relatorioBeans.add(relatorioBean);

		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("grupoFaturamento", "" + grupoFaturamento);

		parametros.put("empresa", empresa);

		parametros.put("localidade", localidade);

		parametros.put("nomeArquivo", nomeArquivo);

		RelatorioRegistrarLeiturasAnormalidadesSubReportBean relatorioSubReportBean = null;
		List<RelatorioRegistrarLeiturasAnormalidadesSubReportBean> relatorioSubReportList = new ArrayList<RelatorioRegistrarLeiturasAnormalidadesSubReportBean>();
		for(LeituraAnormalidade leituraAnormalidade : mapaRelatorioBeansSubReport.keySet()){
			relatorioSubReportBean = new RelatorioRegistrarLeiturasAnormalidadesSubReportBean(leituraAnormalidade.getId(),
							leituraAnormalidade.getDescricao(), mapaRelatorioBeansSubReport.get(leituraAnormalidade));
			relatorioSubReportList.add(relatorioSubReportBean);
		}

		parametros.put("relatorioBeansSubReport", new RelatorioDataSource(relatorioSubReportList));

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES, parametros, ds, tipoFormatoRelatorio);

		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES, idFuncionalidadeIniciada,
							nomeArquivo);
		}catch(Exception e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	private void incrementarAnormalidades(Map<LeituraAnormalidade, Integer> relatorioBeansSubReport, MedicaoHistorico medicaoHistorico){

		if(relatorioBeansSubReport.containsKey(medicaoHistorico.getLeituraAnormalidadeInformada().getId())){
			relatorioBeansSubReport.put(medicaoHistorico.getLeituraAnormalidadeInformada(), relatorioBeansSubReport.get(medicaoHistorico
							.getLeituraAnormalidadeInformada()) + 1);
		}else{
			relatorioBeansSubReport.put(medicaoHistorico.getLeituraAnormalidadeInformada(), Integer.valueOf(1));
		}
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioRegistrarLeiturasAnormalidades", this);
	}

}