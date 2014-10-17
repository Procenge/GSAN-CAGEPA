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

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC0XXX] Relat�rio Gestao de Registro de Atendimento
 * 
 * @author Anderson Italo
 * @date 29/03/2011
 */
public class RelatorioGestaoRegistroAtendimentoSintetico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioGestaoRegistroAtendimentoSintetico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_SINTETICO);
	}

	@Deprecated
	public RelatorioGestaoRegistroAtendimentoSintetico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// obt�m os par�metros passados
		FiltrarRegistroAtendimentoHelper filtroRA = (FiltrarRegistroAtendimentoHelper) getParametro("filtroRA");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		int tipoRelatorio = (Integer) getParametro("tipoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obt�m a cole��o com dados do detalhe do relat�rio
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioGestaoRA(filtroRA, tipoRelatorio);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		// processamento dos dados do detalhe do relat�rio
		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioGestaoRegistroAtendimentoSinteticoBean bean = new RelatorioGestaoRegistroAtendimentoSinteticoBean();

			// Cabe�alho

			// Id da Unidade de Atendimento
			bean.setIdUnidadeAtendimento(dados[0].toString());

			// Descricao da Unidade de Atendimento
			bean.setDescricaoUnidadeAtendimento(dados[1].toString());

			// Sigla da Unidade de Atendimento
			if(dados[20] != null && !dados[20].toString().equals("")) bean.setSiglaUnidadeAtendimento(dados[20].toString());

			// Linha Detalhe

			// Qtd Solicitada por Especifica��o
			bean.setQtdSolicitadoPorUnidade(dados[2].toString());

			// Qtd Atendido TT por Especifica��o
			bean.setQtdAtendidoPorUnidade(dados[3].toString());

			// Qtd Atendido NP por Especifica��o
			bean.setQtdAtendidoNPPorUnidade(dados[4].toString());

			// Qtd Atendido FP por Especifica��o
			bean.setQtdAtendidoFPPorUnidade(dados[5].toString());

			// Qtd Residual no Prazo por Especifica��o
			bean.setQtdResidualNoPrazoPorUnidade(dados[14].toString());

			// Qtd Residual Fora do Prazo por Especifica��o
			bean.setQtdResidualForaPrazoPorUnidade(dados[17].toString());

			// Qtd Solicitada por Especifica��o na Origem
			bean.setQtdSolicitadoPorUnidadeOrigem(dados[6].toString());

			// Qtd Atendido TT por Especifica��o na Origem
			bean.setQtdAtendidoPorUnidadeOrigem(dados[7].toString());

			// Qtd Atendido NP por Especifica��o na Origem
			bean.setQtdAtendidoNPPorUnidadeOrigem(dados[8].toString());

			// Qtd Atendido FP por Especifica��o na Origem
			bean.setQtdAtendidoFPPorUnidadeOrigem(dados[9].toString());

			// Qtd Residual no Prazo por Especifica��o na Origem
			bean.setQtdResidualNoPrazoPorUnidadeOrigem(dados[15].toString());

			// Qtd Residual Fora do Prazo por Especifica��o na Origem
			bean.setQtdResidualForaPrazoPorUnidadeOrigem(dados[18].toString());

			// Qtd Solicitada por Especifica��o Outros
			bean.setQtdSolicitadoPorUnidadeOutros(dados[10].toString());

			// Qtd Atendido TT por Especifica��o Outros
			bean.setQtdAtendidoPorUnidadeOutros(dados[11].toString());

			// Qtd Atendido NP por Especifica��o Outros
			bean.setQtdAtendidoNPPorUnidadeOutros(dados[12].toString());

			// Qtd Atendido FP por Especifica��o Outros
			bean.setQtdAtendidoFPPorUnidadeOutros(dados[13].toString());

			// Qtd Residual no Prazo por Especifica��o Outros
			bean.setQtdResidualNoPrazoPorUnidadeOutros(dados[16].toString());

			// Qtd Residual Fora do Prazo por Especifica��o Outros
			bean.setQtdResidualForaPrazoPorUnidadeOutros(dados[19].toString());

			relatorioBeans.add(bean);
		}

		// preenche os valores dos percentuais
		for(int i = 0; i < relatorioBeans.size(); i++){

			RelatorioGestaoRegistroAtendimentoSinteticoBean bean = (RelatorioGestaoRegistroAtendimentoSinteticoBean) relatorioBeans.get(i);

			if(bean.getQtdSolicitadoPorUnidade() != null){

				// totaliza percentual por unidade
				BigDecimal percentualPorUnidade = null;

				if(!bean.getQtdSolicitadoPorUnidade().equals("0")){

					// AT %
					BigDecimal quantidadeSolicitadaPorUnidade = new BigDecimal(bean.getQtdSolicitadoPorUnidade());
					BigDecimal quantidadeAtendidaPorUnidade = new BigDecimal(bean.getQtdAtendidoPorUnidade());

					quantidadeAtendidaPorUnidade = quantidadeAtendidaPorUnidade.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaPorUnidade.divide(quantidadeSolicitadaPorUnidade, 2, BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoPorUnidade(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// NP %
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaNPPorUnidade = new BigDecimal(bean.getQtdAtendidoNPPorUnidade());

					quantidadeAtendidaNPPorUnidade = quantidadeAtendidaNPPorUnidade.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaNPPorUnidade.divide(quantidadeSolicitadaPorUnidade, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoNPPorUnidade(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// FP %
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaFPPorUnidade = new BigDecimal(bean.getQtdAtendidoFPPorUnidade());

					quantidadeAtendidaFPPorUnidade = quantidadeAtendidaFPPorUnidade.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaFPPorUnidade.divide(quantidadeSolicitadaPorUnidade, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoFPPorUnidade(Util.formataBigDecimal(percentualPorUnidade, 2, false));
				}else{
					bean.setPercAtendidoPorUnidade("0,00");
					bean.setPercAtendidoNPPorUnidade("0,00");
					bean.setPercAtendidoFPPorUnidade("0,00");
				}

				if(!bean.getQtdSolicitadoPorUnidadeOrigem().equals("0")){

					// AT % Origem
					percentualPorUnidade = null;
					BigDecimal quantidadeSolicitadaPorUnidadeOrigem = new BigDecimal(bean.getQtdSolicitadoPorUnidadeOrigem());
					BigDecimal quantidadeAtendidaPorUnidadeOrigem = new BigDecimal(bean.getQtdAtendidoPorUnidadeOrigem());

					quantidadeAtendidaPorUnidadeOrigem = quantidadeAtendidaPorUnidadeOrigem.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaPorUnidadeOrigem.divide(quantidadeSolicitadaPorUnidadeOrigem, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoPorUnidadeOrigem(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// NP % Origem
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaNPPorUnidadeOrigem = new BigDecimal(bean.getQtdAtendidoNPPorUnidadeOrigem());

					quantidadeAtendidaNPPorUnidadeOrigem = quantidadeAtendidaNPPorUnidadeOrigem.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaNPPorUnidadeOrigem.divide(quantidadeSolicitadaPorUnidadeOrigem, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoNPPorUnidadeOrigem(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// FP % Origem
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaFPPorUnidadeOrigem = new BigDecimal(bean.getQtdAtendidoFPPorUnidadeOrigem());

					quantidadeAtendidaFPPorUnidadeOrigem = quantidadeAtendidaFPPorUnidadeOrigem.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaFPPorUnidadeOrigem.divide(quantidadeSolicitadaPorUnidadeOrigem, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoFPPorUnidadeOrigem(Util.formataBigDecimal(percentualPorUnidade, 2, false));
				}else{
					bean.setPercAtendidoPorUnidadeOrigem("0,00");
					bean.setPercAtendidoNPPorUnidadeOrigem("0,00");
					bean.setPercAtendidoFPPorUnidadeOrigem("0,00");
				}

				if(!bean.getQtdSolicitadoPorUnidadeOutros().equals("0")){

					// AT % Outros
					percentualPorUnidade = null;
					BigDecimal quantidadeSolicitadaPorUnidadeOutros = new BigDecimal(bean.getQtdSolicitadoPorUnidadeOutros());
					BigDecimal quantidadeAtendidaPorUnidadeOutros = new BigDecimal(bean.getQtdAtendidoPorUnidadeOutros());

					quantidadeAtendidaPorUnidadeOutros = quantidadeAtendidaPorUnidadeOutros.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaPorUnidadeOutros.divide(quantidadeSolicitadaPorUnidadeOutros, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoPorUnidadeOutros(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// NP % Outros
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaNPPorUnidadeOutros = new BigDecimal(bean.getQtdAtendidoNPPorUnidadeOutros());

					quantidadeAtendidaNPPorUnidadeOutros = quantidadeAtendidaNPPorUnidadeOutros.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaNPPorUnidadeOutros.divide(quantidadeSolicitadaPorUnidadeOutros, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoNPPorUnidadeOutros(Util.formataBigDecimal(percentualPorUnidade, 2, false));

					// FP % Outros
					percentualPorUnidade = null;
					BigDecimal quantidadeAtendidaFPPorUnidadeOutros = new BigDecimal(bean.getQtdAtendidoFPPorUnidadeOutros());

					quantidadeAtendidaFPPorUnidadeOutros = quantidadeAtendidaFPPorUnidadeOutros.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeAtendidaFPPorUnidadeOutros.divide(quantidadeSolicitadaPorUnidadeOutros, 2,
									BigDecimal.ROUND_HALF_UP);
					bean.setPercAtendidoFPPorUnidadeOutros(Util.formataBigDecimal(percentualPorUnidade, 2, false));
				}else{
					bean.setPercAtendidoPorUnidadeOutros("0,00");
					bean.setPercAtendidoNPPorUnidadeOutros("0,00");
					bean.setPercAtendidoFPPorUnidadeOutros("0,00");
				}

			}

			relatorioBeans.set(i, bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// Situa��o
		String situacao = "";
		if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_TODOS){

			situacao = "Todos";
		}else if(filtroRA.getCodigoSituacao() != null && Short.valueOf(filtroRA.getCodigoSituacao()).equals(Short.valueOf("501"))){

			situacao = "Reiterados";
		}else if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_PENDENTE){

			situacao = "Pendentes";
		}else if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO){

			situacao = "Encerrados";
		}else if(filtroRA.getCodigoSituacao() != null){

			situacao = "Sem Local de Ocorr�ncia";
		}

		// Per�odo de Atendimento
		String periodoAtendimento = "";
		if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

			periodoAtendimento = Util.formatarData(filtroRA.getDataAtendimentoInicial()) + " a "
							+ Util.formatarData(filtroRA.getDataAtendimentoFinal());
		}

		// Per�odo de Encerramento
		String periodoEncerramento = "";
		if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

			periodoEncerramento = Util.formatarData(filtroRA.getDataEncerramentoInicial()) + " a "
							+ Util.formatarData(filtroRA.getDataEncerramentoFinal());
		}

		// Unidade Superior ou Chefia
		String unidadeSuperior = "";
		if(filtroRA.getUnidadeSuperior() != null){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, filtroRA
							.getUnidadeSuperior().getId()));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeSuperior += unidadeOrganizacional.getId().toString() + " - " + unidadeOrganizacional.getDescricao();
		}

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("situacao", situacao);
		parametros.put("periodoAtendimento", periodoAtendimento);
		parametros.put("periodoEncerramento", periodoEncerramento);
		parametros.put("unidadeSuperior", unidadeSuperior);

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_SINTETICO, parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub
	}

}