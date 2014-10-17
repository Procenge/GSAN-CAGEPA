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
 * @date 25/03/2011
 */
public class RelatorioGestaoRegistroAtendimentoAnalitico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioGestaoRegistroAtendimentoAnalitico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_ANALITICO);
	}

	@Deprecated
	public RelatorioGestaoRegistroAtendimentoAnalitico() {

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
		Integer qtdAtendidoPorUnidade = 0;
		Integer qtdAtendidoPorUnidadeOrigem = 0;
		Integer qtdAtendidoPorUnidadeOutros = 0;
		Integer qtdSolicitadoPorUnidade = 0;
		Integer qtdSolicitadoPorUnidadeOrigem = 0;
		Integer qtdSolicitadoPorUnidadeOutros = 0;
		Integer qtdAtendidoNPPorUnidade = 0;
		Integer qtdAtendidoNPPorUnidadeOrigem = 0;
		Integer qtdAtendidoNPPorUnidadeOutros = 0;
		Integer qtdAtendidoFPPorUnidade = 0;
		Integer qtdAtendidoFPPorUnidadeOrigem = 0;
		Integer qtdAtendidoFPPorUnidadeOutros = 0;
		Integer qtdResidualNoPrazoPorUnidade = 0;
		Integer qtdResidualNoPrazoPorUnidadeOrigem = 0;
		Integer qtdResidualNoPrazoPorUnidadeOutros = 0;
		Integer qtdResidualForaPrazoPorUnidade = 0;
		Integer qtdResidualForaPrazoPorUnidadeOrigem = 0;
		Integer qtdResidualForaPrazoPorUnidadeOutros = 0;

		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioGestaoRegistroAtendimentoAnaliticoBean bean = new RelatorioGestaoRegistroAtendimentoAnaliticoBean();

			// Cabe�alho

			// Id da Unidade de Atendimento
			bean.setIdUnidadeAtendimento(dados[4].toString());

			// Descricao da Unidade de Atendimento
			bean.setDescricaoUnidadeAtendimento(dados[5].toString());

			// Sigla da Unidade de Atendimento
			if(dados[31] != null && !dados[31].toString().equals("")) bean.setSiglaUnidadeAtendimento(dados[31].toString());

			// Id do Tipo da Solicita��o
			bean.setIdTipoSolicitacao(dados[2].toString());

			// Descri��o do Tipo da Solicita��o
			bean.setDescricaoTipoSolicitacao(dados[3].toString());

			// Linha Detalhe

			// Id da Especifica��o
			bean.setIdEspecificacao(dados[0].toString());

			// Descri��o da Especifica��o
			bean.setDescricaoEspecificacao(dados[1].toString());

			// Qtd Solicitada por Especifica��o
			bean.setQtdSolicitadoPorEspecificacao(dados[6].toString());
			qtdSolicitadoPorUnidade += Integer.valueOf(dados[6].toString());

			// Qtd Atendido TT por Especifica��o
			bean.setQtdAtendidoPorEspecificacao(dados[7].toString());
			qtdAtendidoPorUnidade += Integer.valueOf(dados[7].toString());

			// Qtd Atendido NP por Especifica��o
			bean.setQtdAtendidoNPPorEspecificacao(dados[8].toString());
			qtdAtendidoNPPorUnidade += Integer.valueOf(dados[8].toString());

			// Qtd Atendido FP por Especifica��o
			bean.setQtdAtendidoFPPorEspecificacao(dados[9].toString());
			qtdAtendidoFPPorUnidade += Integer.valueOf(dados[9].toString());

			// Qtd Residual no Prazo por Especifica��o
			bean.setQtdResidualNoPrazoPorEspecificacao(dados[18].toString());
			qtdResidualNoPrazoPorUnidade += Integer.valueOf(dados[18].toString());

			// Qtd Residual Fora do Prazo por Especifica��o
			bean.setQtdResidualForaPrazoPorEspecificacao(dados[21].toString());
			qtdResidualForaPrazoPorUnidade += Integer.valueOf(dados[21].toString());

			// Prazo Padr�o de Exec. em dias por Especifica��o
			if(dados[24] != null && !dados[24].toString().equals("")) bean.setPrazoPadraoExecPorEspecificacao(Util.adicionarZerosEsqueda(3,
							dados[24].toString()));
			else bean.setPrazoPadraoExecPorEspecificacao("-");

			// Prazo M�dio de Exec. em dias por Especifica��o
			if(dados[25] != null && !dados[25].toString().equals("") && !dados[25].toString().equals("0.0")) bean
							.setPrazoMedioExecPorEspecificacao(Util.adicionarZerosEsqueda(3, String
											.valueOf(new Double(dados[25].toString()).intValue())));
			else bean.setPrazoMedioExecPorEspecificacao("-");

			// Atraso M�dio de Exec. em dias por Especifica��o
			if(dados[28] != null && !dados[28].toString().equals("") && !dados[28].toString().equals("0.0")) bean
							.setAtrasoMedioExecPorEspecificacao(Util.adicionarZerosEsqueda(3, String.valueOf(new Double(dados[28]
											.toString()).intValue())));
			else bean.setAtrasoMedioExecPorEspecificacao("-");

			// Qtd Solicitada por Especifica��o na Origem
			bean.setQtdSolicitadoPorEspecificacaoOrigem(dados[10].toString());
			qtdSolicitadoPorUnidadeOrigem += Integer.valueOf(dados[10].toString());

			// Qtd Atendido TT por Especifica��o na Origem
			bean.setQtdAtendidoPorEspecificacaoOrigem(dados[11].toString());
			qtdAtendidoPorUnidadeOrigem += Integer.valueOf(dados[11].toString());

			// Qtd Atendido NP por Especifica��o na Origem
			bean.setQtdAtendidoNPPorEspecificacaoOrigem(dados[12].toString());
			qtdAtendidoNPPorUnidadeOrigem += Integer.valueOf(dados[12].toString());

			// Qtd Atendido FP por Especifica��o na Origem
			bean.setQtdAtendidoFPPorEspecificacaoOrigem(dados[13].toString());
			qtdAtendidoFPPorUnidadeOrigem += Integer.valueOf(dados[13].toString());

			// Qtd Residual no Prazo por Especifica��o na Origem
			bean.setQtdResidualNoPrazoPorEspecificacaoOrigem(dados[19].toString());
			qtdResidualNoPrazoPorUnidadeOrigem += Integer.valueOf(dados[19].toString());

			// Qtd Residual Fora do Prazo por Especifica��o na Origem
			bean.setQtdResidualForaPrazoPorEspecificacaoOrigem(dados[22].toString());
			qtdResidualForaPrazoPorUnidadeOrigem += Integer.valueOf(dados[22].toString());

			// Prazo M�dio de Exec. em dias por Especifica��o na Origem
			if(dados[26] != null && !dados[26].toString().equals("") && !dados[26].toString().equals("0.0")) bean
							.setPrazoMedioExecPorEspecificacaoOrigem(Util.adicionarZerosEsqueda(3, String.valueOf(new Double(dados[26]
											.toString()).intValue())));
			else bean.setPrazoMedioExecPorEspecificacaoOrigem("-");

			// Atraso M�dio de Exec. em dias por Especifica��o na Origem
			if(dados[29] != null && !dados[29].toString().equals("") && !dados[29].toString().equals("0.0")) bean
							.setAtrasoMedioExecPorEspecificacaoOrigem(Util.adicionarZerosEsqueda(3, String.valueOf(new Double(dados[29]
											.toString()).intValue())));
			else bean.setAtrasoMedioExecPorEspecificacaoOrigem("-");

			// Qtd Solicitada por Especifica��o Outros
			bean.setQtdSolicitadoPorEspecificacaoOutros(dados[14].toString());
			qtdSolicitadoPorUnidadeOutros += Integer.valueOf(dados[14].toString());

			// Qtd Atendido TT por Especifica��o Outros
			bean.setQtdAtendidoPorEspecificacaoOutros(dados[15].toString());
			qtdAtendidoPorUnidadeOutros += Integer.valueOf(dados[15].toString());

			// Qtd Atendido NP por Especifica��o Outros
			bean.setQtdAtendidoNPPorEspecificacaoOutros(dados[16].toString());
			qtdAtendidoNPPorUnidadeOutros += Integer.valueOf(dados[16].toString());

			// Qtd Atendido FP por Especifica��o Outros
			bean.setQtdAtendidoFPPorEspecificacaoOutros(dados[17].toString());
			qtdAtendidoFPPorUnidadeOutros += Integer.valueOf(dados[17].toString());

			// Qtd Residual no Prazo por Especifica��o Outros
			bean.setQtdResidualNoPrazoPorEspecificacaoOutros(dados[20].toString());
			qtdResidualNoPrazoPorUnidadeOutros += Integer.valueOf(dados[20].toString());

			// Qtd Residual Fora do Prazo por Especifica��o Outros
			bean.setQtdResidualForaPrazoPorEspecificacaoOutros(dados[23].toString());
			qtdResidualForaPrazoPorUnidadeOutros += Integer.valueOf(dados[23].toString());

			// Prazo M�dio de Exec. em dias por Especifica��o Outros
			if(dados[27] != null && !dados[27].toString().equals("") && !dados[27].toString().equals("0.0")) bean
							.setPrazoMedioExecPorEspecificacaoOutros(Util.adicionarZerosEsqueda(3, String.valueOf(new Double(dados[27]
											.toString()).intValue())));
			else bean.setPrazoMedioExecPorEspecificacaoOutros("-");

			// Atraso M�dio de Exec. em dias por Especifica��o Outros
			if(dados[30] != null && !dados[30].toString().equals("") && !dados[30].toString().equals("0.0")) bean
							.setAtrasoMedioExecPorEspecificacaoOutros(Util.adicionarZerosEsqueda(3, String.valueOf(new Double(dados[30]
											.toString()).intValue())));
			else bean.setAtrasoMedioExecPorEspecificacaoOutros("-");

			// verifica quebras por unidade e totaliza
			if(i == (colecaoDadosRelatorio.size() - 1)){

				bean.setQtdSolicitadoPorUnidade(qtdSolicitadoPorUnidade.toString());
				bean.setQtdSolicitadoPorUnidadeOrigem(qtdSolicitadoPorUnidadeOrigem.toString());
				bean.setQtdSolicitadoPorUnidadeOutros(qtdSolicitadoPorUnidadeOutros.toString());
				bean.setQtdAtendidoPorUnidade(qtdAtendidoPorUnidade.toString());
				bean.setQtdAtendidoPorUnidadeOrigem(qtdAtendidoPorUnidadeOrigem.toString());
				bean.setQtdAtendidoPorUnidadeOutros(qtdAtendidoPorUnidadeOutros.toString());
				bean.setQtdAtendidoNPPorUnidade(qtdAtendidoNPPorUnidade.toString());
				bean.setQtdAtendidoNPPorUnidadeOrigem(qtdAtendidoNPPorUnidadeOrigem.toString());
				bean.setQtdAtendidoNPPorUnidadeOutros(qtdAtendidoNPPorUnidadeOutros.toString());
				bean.setQtdAtendidoFPPorUnidade(qtdAtendidoFPPorUnidade.toString());
				bean.setQtdAtendidoFPPorUnidadeOrigem(qtdAtendidoFPPorUnidadeOrigem.toString());
				bean.setQtdAtendidoFPPorUnidadeOutros(qtdAtendidoFPPorUnidadeOutros.toString());
				bean.setQtdResidualNoPrazoPorUnidade(qtdResidualNoPrazoPorUnidade.toString());
				bean.setQtdResidualNoPrazoPorUnidadeOrigem(qtdResidualNoPrazoPorUnidadeOrigem.toString());
				bean.setQtdResidualNoPrazoPorUnidadeOutros(qtdResidualNoPrazoPorUnidadeOutros.toString());
				bean.setQtdResidualForaPrazoPorUnidade(qtdResidualForaPrazoPorUnidade.toString());
				bean.setQtdResidualForaPrazoPorUnidadeOrigem(qtdResidualForaPrazoPorUnidadeOrigem.toString());
				bean.setQtdResidualForaPrazoPorUnidadeOutros(qtdResidualForaPrazoPorUnidadeOutros.toString());

			}else{

				Object[] dadosAux = (Object[]) colecaoDadosRelatorio.get(i + 1);

				if(!dados[4].toString().equals(dadosAux[4].toString())){

					bean.setQtdSolicitadoPorUnidade(qtdSolicitadoPorUnidade.toString());
					bean.setQtdSolicitadoPorUnidadeOrigem(qtdSolicitadoPorUnidadeOrigem.toString());
					bean.setQtdSolicitadoPorUnidadeOutros(qtdSolicitadoPorUnidadeOutros.toString());
					bean.setQtdAtendidoPorUnidade(qtdAtendidoPorUnidade.toString());
					bean.setQtdAtendidoPorUnidadeOrigem(qtdAtendidoPorUnidadeOrigem.toString());
					bean.setQtdAtendidoPorUnidadeOutros(qtdAtendidoPorUnidadeOutros.toString());
					bean.setQtdAtendidoNPPorUnidade(qtdAtendidoNPPorUnidade.toString());
					bean.setQtdAtendidoNPPorUnidadeOrigem(qtdAtendidoNPPorUnidadeOrigem.toString());
					bean.setQtdAtendidoNPPorUnidadeOutros(qtdAtendidoNPPorUnidadeOutros.toString());
					bean.setQtdAtendidoFPPorUnidade(qtdAtendidoFPPorUnidade.toString());
					bean.setQtdAtendidoFPPorUnidadeOrigem(qtdAtendidoFPPorUnidadeOrigem.toString());
					bean.setQtdAtendidoFPPorUnidadeOutros(qtdAtendidoFPPorUnidadeOutros.toString());
					bean.setQtdResidualNoPrazoPorUnidade(qtdResidualNoPrazoPorUnidade.toString());
					bean.setQtdResidualNoPrazoPorUnidadeOrigem(qtdResidualNoPrazoPorUnidadeOrigem.toString());
					bean.setQtdResidualNoPrazoPorUnidadeOutros(qtdResidualNoPrazoPorUnidadeOutros.toString());
					bean.setQtdResidualForaPrazoPorUnidade(qtdResidualForaPrazoPorUnidade.toString());
					bean.setQtdResidualForaPrazoPorUnidadeOrigem(qtdResidualForaPrazoPorUnidadeOrigem.toString());
					bean.setQtdResidualForaPrazoPorUnidadeOutros(qtdResidualForaPrazoPorUnidadeOutros.toString());

					qtdSolicitadoPorUnidade = 0;
					qtdSolicitadoPorUnidadeOrigem = 0;
					qtdSolicitadoPorUnidadeOutros = 0;
					qtdAtendidoPorUnidade = 0;
					qtdAtendidoPorUnidadeOrigem = 0;
					qtdAtendidoPorUnidadeOutros = 0;
					qtdAtendidoNPPorUnidade = 0;
					qtdAtendidoNPPorUnidadeOrigem = 0;
					qtdAtendidoNPPorUnidadeOutros = 0;
					qtdAtendidoFPPorUnidade = 0;
					qtdAtendidoFPPorUnidadeOrigem = 0;
					qtdAtendidoFPPorUnidadeOutros = 0;
					qtdResidualNoPrazoPorUnidade = 0;
					qtdResidualNoPrazoPorUnidadeOrigem = 0;
					qtdResidualNoPrazoPorUnidadeOutros = 0;
					qtdResidualForaPrazoPorUnidade = 0;
					qtdResidualForaPrazoPorUnidadeOrigem = 0;
					qtdResidualForaPrazoPorUnidadeOutros = 0;
				}
			}

			relatorioBeans.add(bean);
		}

		// preenche os valores dos percentuais
		for(int i = 0; i < relatorioBeans.size(); i++){

			RelatorioGestaoRegistroAtendimentoAnaliticoBean bean = (RelatorioGestaoRegistroAtendimentoAnaliticoBean) relatorioBeans.get(i);

			// totaliza percentual por especifica��o
			BigDecimal percentualPorEspecificacao = null;

			if(!bean.getQtdSolicitadoPorEspecificacao().equals("0")){

				// AT %
				BigDecimal quantidadeSolicitadaPorEspecificacao = new BigDecimal(bean.getQtdSolicitadoPorEspecificacao());
				BigDecimal quantidadeAtendidaPorEspecificacao = new BigDecimal(bean.getQtdAtendidoPorEspecificacao());
				quantidadeAtendidaPorEspecificacao = quantidadeAtendidaPorEspecificacao.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaPorEspecificacao.divide(quantidadeSolicitadaPorEspecificacao, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoPorEspecificacao(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// NP %
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaNPPorEspecificacao = new BigDecimal(bean.getQtdAtendidoNPPorEspecificacao());

				quantidadeAtendidaNPPorEspecificacao = quantidadeAtendidaNPPorEspecificacao.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaNPPorEspecificacao.divide(quantidadeSolicitadaPorEspecificacao, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoNPPorEspecificacao(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// FP %
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaFPPorEspecificacao = new BigDecimal(bean.getQtdAtendidoFPPorEspecificacao());

				quantidadeAtendidaFPPorEspecificacao = quantidadeAtendidaFPPorEspecificacao.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaFPPorEspecificacao.divide(quantidadeSolicitadaPorEspecificacao, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoFPPorEspecificacao(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));
			}else{
				bean.setPercAtendidoPorEspecificacao("0,00");
				bean.setPercAtendidoNPPorEspecificacao("0,00");
				bean.setPercAtendidoFPPorEspecificacao("0,00");
			}

			if(!bean.getQtdSolicitadoPorEspecificacaoOrigem().equals("0")){

				// AT % Origem
				BigDecimal quantidadeSolicitadaPorEspecificacaoOrigem = new BigDecimal(bean.getQtdSolicitadoPorEspecificacaoOrigem());
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaPorEspecificacaoOrigem = new BigDecimal(bean.getQtdAtendidoPorEspecificacaoOrigem());

				quantidadeAtendidaPorEspecificacaoOrigem = quantidadeAtendidaPorEspecificacaoOrigem.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaPorEspecificacaoOrigem.divide(quantidadeSolicitadaPorEspecificacaoOrigem, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoPorEspecificacaoOrigem(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// NP % Origem
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaNPPorEspecificacaoOrigem = new BigDecimal(bean.getQtdAtendidoNPPorEspecificacaoOrigem());

				quantidadeAtendidaNPPorEspecificacaoOrigem = quantidadeAtendidaNPPorEspecificacaoOrigem.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaNPPorEspecificacaoOrigem.divide(quantidadeSolicitadaPorEspecificacaoOrigem,
								2, BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoNPPorEspecificacaoOrigem(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// FP % Origem
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaFPPorEspecificacaoOrigem = new BigDecimal(bean.getQtdAtendidoFPPorEspecificacaoOrigem());

				quantidadeAtendidaFPPorEspecificacaoOrigem = quantidadeAtendidaFPPorEspecificacaoOrigem.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaFPPorEspecificacaoOrigem.divide(quantidadeSolicitadaPorEspecificacaoOrigem,
								2, BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoFPPorEspecificacaoOrigem(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));
			}else{
				bean.setPercAtendidoPorEspecificacaoOrigem("0,00");
				bean.setPercAtendidoNPPorEspecificacaoOrigem("0,00");
				bean.setPercAtendidoFPPorEspecificacaoOrigem("0,00");
			}

			if(!bean.getQtdSolicitadoPorEspecificacaoOutros().equals("0")){

				// AT % Outros
				BigDecimal quantidadeSolicitadaPorEspecificacaoOutros = new BigDecimal(bean.getQtdSolicitadoPorEspecificacaoOutros());
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaPorEspecificacaoOutros = new BigDecimal(bean.getQtdAtendidoPorEspecificacaoOutros());

				quantidadeAtendidaPorEspecificacaoOutros = quantidadeAtendidaPorEspecificacaoOutros.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaPorEspecificacaoOutros.divide(quantidadeSolicitadaPorEspecificacaoOutros, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoPorEspecificacaoOutros(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// NP % Outros
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaNPPorEspecificacaoOutros = new BigDecimal(bean.getQtdAtendidoNPPorEspecificacaoOutros());

				quantidadeAtendidaNPPorEspecificacaoOutros = quantidadeAtendidaNPPorEspecificacaoOutros.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaNPPorEspecificacaoOutros.divide(quantidadeSolicitadaPorEspecificacaoOutros,
								2, BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoNPPorEspecificacaoOutros(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

				// FP % Outros
				percentualPorEspecificacao = null;
				BigDecimal quantidadeAtendidaFPPorEspecificacaoOutros = new BigDecimal(bean.getQtdAtendidoFPPorEspecificacaoOutros());

				quantidadeAtendidaFPPorEspecificacaoOutros = quantidadeAtendidaFPPorEspecificacaoOutros.multiply(new BigDecimal("100"));
				percentualPorEspecificacao = quantidadeAtendidaFPPorEspecificacaoOutros.divide(quantidadeSolicitadaPorEspecificacaoOutros,
								2, BigDecimal.ROUND_HALF_UP);
				bean.setPercAtendidoFPPorEspecificacaoOutros(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));
			}else{
				bean.setPercAtendidoPorEspecificacaoOutros("0,00");
				bean.setPercAtendidoNPPorEspecificacaoOutros("0,00");
				bean.setPercAtendidoFPPorEspecificacaoOutros("0,00");
			}

			// caso seja a linha da totaliza��o por unidade
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
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_ANALITICO, parametros, ds, tipoFormatoRelatorio);

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