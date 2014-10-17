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

package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Ana Maria
 * @date 14/02/2007
 */
public class RelatorioResumoImovelMicromedicao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoImovelMicromedicao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_IMOVEL_MICROMEDICAO);
	}

	@Deprecated
	public RelatorioResumoImovelMicromedicao() {

		super(null, "");
	}

	private Collection<RelatorioResumoImovelMicromedicaoBean> inicializarBeanRelatorio(Collection dadosRelatorio){

		Collection<RelatorioResumoImovelMicromedicaoBean> retorno = new ArrayList();

		// Hist�rico Medi��o e Consumo
		Iterator iterator = dadosRelatorio.iterator();

		while(iterator.hasNext()){

			ImovelMicromedicao imovelMicromedicao = (ImovelMicromedicao) iterator.next();

			String mesAnoMedicao = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getMesAno());

			String dataLeituraInformada = "";
			if(imovelMicromedicao.getMedicaoHistorico().getDataLeituraAtualInformada() != null){
				dataLeituraInformada = Util.formatarData(imovelMicromedicao.getMedicaoHistorico().getDataLeituraAtualInformada());
			}

			String leituraInformada = "";
			if(imovelMicromedicao.getMedicaoHistorico().getLeituraAtualInformada() != null){
				leituraInformada = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getLeituraAtualInformada());
			}

			String dataLeituraFaturada = "";
			if(imovelMicromedicao.getMedicaoHistorico().getDataLeituraAtualFaturamento() != null){
				dataLeituraFaturada = Util.formatarData(imovelMicromedicao.getMedicaoHistorico().getDataLeituraAtualFaturamento());
			}

			String leituraFaturada = "";
			if(imovelMicromedicao.getMedicaoHistorico().getLeituraAtualFaturamento() != null){
				leituraFaturada = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getLeituraAtualFaturamento());
			}

			String consumo = "";
			if(imovelMicromedicao.getConsumoHistorico().getNumeroConsumoFaturadoMes() != null){
				consumo = String.valueOf(imovelMicromedicao.getConsumoHistorico().getNumeroConsumoFaturadoMes());
			}

			String anormalidadeConsumo = "";
			if(imovelMicromedicao.getConsumoHistorico().getConsumoAnormalidade().getDescricaoAbreviada() != null){
				anormalidadeConsumo = String.valueOf(imovelMicromedicao.getConsumoHistorico().getConsumoAnormalidade()
								.getDescricaoAbreviada());
			}

			String anormalidadeLeitura = "";
			if(imovelMicromedicao.getMedicaoHistorico().getLeituraAnormalidadeFaturamento().getId() != null){
				anormalidadeLeitura = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getLeituraAnormalidadeFaturamento().getId());
			}

			String sitLeituraAtual = "";
			if(imovelMicromedicao.getMedicaoHistorico().getLeituraSituacaoAtual().getDescricao() != null){
				sitLeituraAtual = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getLeituraSituacaoAtual().getDescricao());
			}

			String consumoMedido = "";
			if(imovelMicromedicao.getMedicaoHistorico().getNumeroConsumoMes() != null){
				consumoMedido = String.valueOf(imovelMicromedicao.getMedicaoHistorico().getNumeroConsumoMes());
			}

			RelatorioResumoImovelMicromedicaoBean bean = new RelatorioResumoImovelMicromedicaoBean(mesAnoMedicao, dataLeituraInformada,
							leituraInformada, dataLeituraFaturada, leituraFaturada, consumo, anormalidadeConsumo, anormalidadeLeitura,
							sitLeituraAtual, consumoMedido);

			retorno.add(bean);

		}

		return retorno;
	}

	/**
	 * M�todo que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Collection colecaoImovelMicromedicao = (Collection) getParametro("colecaoImovelMicromedicao");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		String matriculaFormatada = ((String) getParametro("matricula")).substring(0, ((String) getParametro("matricula")).length() - 1)
						+ "." + ((String) getParametro("matricula")).substring(((String) getParametro("matricula")).length() - 1);

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("matricula", matriculaFormatada);
		parametros.put("inscricao", (String) getParametro("inscricao"));
		parametros.put("sitLigacaoAgua", (String) getParametro("sitLigacaoAgua"));
		parametros.put("sitLigacaoEsgoto", (String) getParametro("sitLigacaoEsgoto"));
		parametros.put("endereco", (String) getParametro("endereco"));
		parametros.put("clienteUsuario", (String) getParametro("clienteUsuario"));

		Collection<RelatorioResumoImovelMicromedicaoBean> colecaoBean = this.inicializarBeanRelatorio(colecaoImovelMicromedicao);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_IMOVEL_MICROMEDICAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RESUMO_IMOVEL_MEDICAO, idFuncionalidadeIniciada, null);
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

		// retorno = ((Collection) getParametro("idsGuiaDevolucao")).size();
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoImovelMicromedicao", this);
	}
}