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

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
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
 * classe responsável por criar o relatório de histograma de ligação de esgoto por economia
 * 
 * @author Rafael Pinto
 * @created 07/11/2007
 */
public class RelatorioHistogramaEsgotoEconomia
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioHistogramaEsgotoEconomia(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA);
	}

	@Deprecated
	public RelatorioHistogramaEsgotoEconomia() {

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

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtrarEmitirHistogramaEsgotoEconomiaHelper = (FiltrarEmitirHistogramaEsgotoEconomiaHelper) getParametro("filtrarEmitirHistogramaEsgotoEconomiaHelper");

		Fachada fachada = Fachada.getInstancia();

		filtrarEmitirHistogramaEsgotoEconomiaHelper.setIdFuncionalidadeIniciada(idFuncionalidadeIniciada);
		Collection<HistogramaEsgotoEconomiaDTO> colecao = fachada
						.pesquisarHistogramaEsgotoEconomiaDTO(filtrarEmitirHistogramaEsgotoEconomiaHelper);

		if(Util.isVazioOrNulo(colecao)) throw new ActionServletException("atencao.pesquisa.nenhumresultado");

		int inicioIdx = 0;
		int fimIdx = 0;
		boolean isPrimeiro = true;

		List<HistogramaEsgotoEconomiaDTO> listaDTO = new ArrayList<HistogramaEsgotoEconomiaDTO>(colecao);

		String opcaoTotalizacaoCorrente = listaDTO.get(0).getDescricaoOpcaoTotalizacao();
		Short percentualCorrente = listaDTO.get(0).getPercentualEsgoto();
		String categoriaCorrente = listaDTO.get(0).getDescricaoCategoria();

		for(int i = 0; i < colecao.size(); i++){
			if(opcaoTotalizacaoCorrente.equals(listaDTO.get(i).getDescricaoOpcaoTotalizacao())
							&& percentualCorrente.equals(listaDTO.get(i).getPercentualEsgoto())
							&& categoriaCorrente.equals(listaDTO.get(i).getDescricaoCategoria())){
				// DENTRO DO GRUPO
				if (isPrimeiro){
					isPrimeiro = false;
					inicioIdx = i;
				} else {
					fimIdx = i;
				}
			} else{
				// ACONTECEU QUEBRA

				// Coloca o indicador para primeira linha do grupo
				isPrimeiro = true;

				int faixaInicio = 0;
				int faixaFim = 0;
				int difConsumoFaixa = 0;
				int difConsumoFaixaAnterior = 0;
				long qtdEconomiasAcumuladaMedido = 0;
				long qtdEconomiasAcumuladaNaoMedido = 0;
				Long volumeASerReduzidoMedido = 0L;
				Long volumeASerReduzidoNaoMedido = 0L;
				Long volumeFaturadoMedido = 0L;
				Long volumeFaturadoNaoMedido = 0L;

				// Acumula a quantidade de economias das faixas seguintes a faixa atual
				for(int corrente = inicioIdx + 1; corrente < fimIdx; corrente++){
					// Acumula a quantidade de economias das faixas seguintes a faixa atual
					qtdEconomiasAcumuladaMedido = somarEconomiasMedido(listaDTO.subList(corrente + 1, fimIdx + 1));
					qtdEconomiasAcumuladaNaoMedido = somarEconomiasNaoMedido(listaDTO.subList(corrente + 1, fimIdx + 1));

					// Obtem a diferença da faixa atual
					String[] faixas = listaDTO.get(corrente).getFaixaDescricao().split(" a ");
					faixaInicio = Integer.valueOf(faixas[0]);
					faixaFim = Integer.valueOf(faixas[1]);

					if(faixaInicio == 0){
						difConsumoFaixa = faixaFim;
					}else{
						difConsumoFaixa = ((faixaFim - faixaInicio) + 1);
					}

					// Verifica se tem registro anterior para calcular a diferença de consumo
					// anterior
					String[] faixasAnterior = listaDTO.get(corrente - 1).getFaixaDescricao().split(" a ");
					faixaInicio = Integer.valueOf(faixasAnterior[0]);
					faixaFim = Integer.valueOf(faixasAnterior[1]);

					// Obtém a diferença de consumo da faixa anterior
					if(faixaInicio == 0){
						difConsumoFaixaAnterior = faixaFim;
					}else{
						difConsumoFaixaAnterior = ((faixaFim - faixaInicio) + 1);
					}

					// Calcula o volume a ser reduzido
					volumeASerReduzidoMedido = listaDTO.get(corrente).getTotalConsumoMedido();
					volumeASerReduzidoMedido = volumeASerReduzidoMedido
									- (listaDTO.get(corrente).getTotalEconomiasMedido() * difConsumoFaixaAnterior);

					volumeASerReduzidoNaoMedido = listaDTO.get(corrente).getTotalConsumoNaoMedido();
					volumeASerReduzidoNaoMedido = volumeASerReduzidoNaoMedido
									- (listaDTO.get(corrente).getTotalEconomiasNaoMedido() * difConsumoFaixaAnterior);

					// Calcula o volume faturado
					volumeFaturadoMedido = (qtdEconomiasAcumuladaMedido * difConsumoFaixa) + volumeASerReduzidoMedido;
					volumeFaturadoNaoMedido = (qtdEconomiasAcumuladaNaoMedido * difConsumoFaixa) + volumeASerReduzidoNaoMedido;

					// Atualiza o registro com o valor faturado
					listaDTO.get(corrente).setTotalVolumeFaturadoMedido(volumeFaturadoMedido);
					listaDTO.get(corrente).setTotalVolumeFaturadoNaoMedido(volumeFaturadoNaoMedido);

				}

				i--;

				// Verifica se a coleção está no final
				if(i < colecao.size()){
					opcaoTotalizacaoCorrente = listaDTO.get(i + 1).getDescricaoOpcaoTotalizacao();
					percentualCorrente = listaDTO.get(i + 1).getPercentualEsgoto();
					categoriaCorrente = listaDTO.get(i + 1).getDescricaoCategoria();
				}
			}
		}
		
		// coleção de beans do relatório

		List beans = new ArrayList();
		
		for(HistogramaEsgotoEconomiaDTO histogramaDTO : listaDTO){
			beans.add(new RelatorioHistogramaEsgotoEconomiaBean(histogramaDTO));
		}

		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtrarEmitirHistogramaEsgotoEconomiaHelper.getMesAnoFaturamento()));
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(beans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.HISTOGRAMA_ESGOTO_POR_ECONOMIA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	private long somarEconomiasMedido(List<HistogramaEsgotoEconomiaDTO> subList){

		long qtdEconomias = 0;

		for(HistogramaEsgotoEconomiaDTO dto : subList){
			qtdEconomias += dto.getTotalEconomiasMedido();
		}
		
		return qtdEconomias;

	}
	
	private long somarEconomiasNaoMedido(List<HistogramaEsgotoEconomiaDTO> subList){

		long qtdEconomias = 0;

		for(HistogramaEsgotoEconomiaDTO dto : subList){
			qtdEconomias += dto.getTotalEconomiasNaoMedido();
		}

		return qtdEconomias;

	}
	
	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioHistogramaEsgotoEconomia", this);

	}

}