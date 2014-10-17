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

package gcom.relatorio.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao.LigacaoAguaSituacaoEnum;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao.LigacaoEsgotoSituacaoEnum;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.FaturamentoConsumoDiretoIndiretoEstadualRelatorioHelper;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * [UC3114] Gerar Relat�rio Faturamento e Consumo Direto e Indireto Estadual
 * 
 * @author Victon Malcolm
 * @since 26/09/2013
 */
public class RelatorioFaturamentoConsumoDiretoIndiretoEstadual
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioFaturamentoConsumoDiretoIndiretoEstadual(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL);
	}

	@Deprecated
	public RelatorioFaturamentoConsumoDiretoIndiretoEstadual() {

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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer opcaoRelatorio = Integer.parseInt((String) getParametro("opcaoRelatorio"));
		Integer anoMes = (Integer) getParametro("anoMes");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioFaturamentoConsumoDiretoIndiretoEstadualBean relatorioBean = null;

		Collection colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper = fachada
						.pesquisarDadosRelatorioFaturamentoConsumoDiretoIndiretoEstadual(anoMes,
						opcaoRelatorio);
		if(colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper != null
						&& !colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper.isEmpty()){

			Iterator colecaoFaturamentoConsumoDiretoIndiretoEstadualHelperIterator = colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper
							.iterator();

			String referencia = "";
			String responsavel = "";
			String descricaoLocalidade = "";
			String tipoResponsavel = "";
			String usuario = "";
			String categoria = "";
			String endereco = "";
			String situacaoAgua = "";
			String situacaoEsgoto = "";
			String matricula = "";
			String economia = "";

			Integer valorLeituraAnterior = new Integer("0");
			Integer valorLeituraAtual = new Integer("0");
			Integer consumoMedido = new Integer("0");
			Integer consumoFaturado = new Integer("0");
			Integer consumoMedio = new Integer("0");

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoFaturamentoConsumoDiretoIndiretoEstadualHelperIterator.hasNext()){

				FaturamentoConsumoDiretoIndiretoEstadualRelatorioHelper faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper = (FaturamentoConsumoDiretoIndiretoEstadualRelatorioHelper) colecaoFaturamentoConsumoDiretoIndiretoEstadualHelperIterator
								.next();
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getReferencia() != null){
					referencia = Util.formatarAnoMesParaMesAno(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getReferencia()
									.intValue());
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoResponsavel() != null){
					tipoResponsavel = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoResponsavel();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCodigoResponsavel() != null){
					responsavel = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCodigoResponsavel() + " - "
									+ faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getResponsavel();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCodigoUsuario() != null){
					usuario = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCodigoUsuario() + " - "
									+ faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getUsuario();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getMatricula() != null){
					matricula = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getMatricula().toString();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getLocalidade() != null){
					descricaoLocalidade = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getLocalidade() + " - "
									+ faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getNomeLocalidade();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getEndereco() != null){
					endereco = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getEndereco();
				}

				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoAgua() != null){
					for(LigacaoAguaSituacaoEnum valor : LigacaoAguaSituacaoEnum.values()){
						if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoAgua().intValue() == valor.getId()){
							situacaoAgua = valor.name();
						}
					}

				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoEsgoto() != null){
					for(LigacaoEsgotoSituacaoEnum valor : LigacaoEsgotoSituacaoEnum.values()){
						if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getTipoEsgoto().intValue() == valor.getId()){
							situacaoEsgoto = valor.name();
						}
					}
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCategoria() != null){
					if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getCategoria().equals('P')) categoria = "PUBLICO";
				}

				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getEconomia() != null){
					economia = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getEconomia().toString();
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getValorLeituraAnterior() != null){
					valorLeituraAnterior = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getValorLeituraAnterior().intValue();
				}else{
					valorLeituraAnterior = 0;
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getValorLeituraAtual() != null){
					valorLeituraAtual = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getValorLeituraAtual().intValue();
				}else{
					valorLeituraAtual = 0;
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoMicromedido() != null){
					consumoMedido = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoMicromedido().intValue();
				}else{
					consumoMedido = new Integer("0");
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoFaturado() != null){
					consumoFaturado = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoFaturado().intValue();
				}else{
					consumoFaturado = new Integer("0");
				}
				if(faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoMedio() != null){
					consumoMedio = faturamentoConsumoDiretoIndiretoEstadualRelatorioHelper.getConsumoMedio().intValue();
				}else{
					consumoMedio = new Integer("0");
				}


				relatorioBean = new RelatorioFaturamentoConsumoDiretoIndiretoEstadualBean(
referencia, responsavel, tipoResponsavel,
								descricaoLocalidade, matricula, usuario, situacaoAgua, situacaoEsgoto, categoria, economia, endereco,
								valorLeituraAnterior, valorLeituraAtual, consumoMedido, consumoFaturado, consumoMedio);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);

			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		if(opcaoRelatorio == 26){
			parametros.put("tipoResponsavel", "DIRETA");
		}else{
			parametros.put("tipoResponsavel", "INDIRETA");
		}
		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(anoMes));

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------tava

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer anoMes = (Integer) getParametro("anoMes");
		Integer opcaoRelatorio = Integer.parseInt((String) getParametro("opcaoRelatorio"));
		Collection colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper = Fachada.getInstancia()
						.pesquisarDadosRelatorioFaturamentoConsumoDiretoIndiretoEstadual(anoMes, opcaoRelatorio);

		return colecaoFaturamentoConsumoDiretoIndiretoEstadualHelper.size();
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioFaturamentoConsumoDiretoIndiretoEstadual", this);
	}
}