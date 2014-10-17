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
 * [UC3114] Gerar Relatório Faturamento e Consumo Direto e Indireto Estadual
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

			// laço para criar a coleção de parâmetros da analise
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

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
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

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------tava

		// retorna o relatório gerado
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