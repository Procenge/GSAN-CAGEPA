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
 * classe respons�vel por criar o relat�rio de histograma de liga��o de esgoto por economia
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

					// Obtem a diferen�a da faixa atual
					String[] faixas = listaDTO.get(corrente).getFaixaDescricao().split(" a ");
					faixaInicio = Integer.valueOf(faixas[0]);
					faixaFim = Integer.valueOf(faixas[1]);

					if(faixaInicio == 0){
						difConsumoFaixa = faixaFim;
					}else{
						difConsumoFaixa = ((faixaFim - faixaInicio) + 1);
					}

					// Verifica se tem registro anterior para calcular a diferen�a de consumo
					// anterior
					String[] faixasAnterior = listaDTO.get(corrente - 1).getFaixaDescricao().split(" a ");
					faixaInicio = Integer.valueOf(faixasAnterior[0]);
					faixaFim = Integer.valueOf(faixasAnterior[1]);

					// Obt�m a diferen�a de consumo da faixa anterior
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

				// Verifica se a cole��o est� no final
				if(i < colecao.size()){
					opcaoTotalizacaoCorrente = listaDTO.get(i + 1).getDescricaoOpcaoTotalizacao();
					percentualCorrente = listaDTO.get(i + 1).getPercentualEsgoto();
					categoriaCorrente = listaDTO.get(i + 1).getDescricaoCategoria();
				}
			}
		}
		
		// cole��o de beans do relat�rio

		List beans = new ArrayList();
		
		for(HistogramaEsgotoEconomiaDTO histogramaDTO : listaDTO){
			beans.add(new RelatorioHistogramaEsgotoEconomiaBean(histogramaDTO));
		}

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtrarEmitirHistogramaEsgotoEconomiaHelper.getMesAnoFaturamento()));
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(beans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.HISTOGRAMA_ESGOTO_POR_ECONOMIA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
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