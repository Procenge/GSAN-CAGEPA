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

package gcom.gui.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
 * [SB0002] - Imprimir Relação das Contas Pré-faturadas
 * 
 * @author Anderson Italo
 * @date 23/02/2012
 */
public class RelatorioContasPreFaturadas
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioContasPreFaturadas(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTAS_PRE_FATURADAS);
	}

	@Deprecated
	public RelatorioContasPreFaturadas() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		Fachada fachada = Fachada.getInstancia();

		// Obtêm os parâmetros passados.
		Collection<Conta> colecaoConta = (Collection<Conta>) getParametro("colecaoConta");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String periodoReferenciaFaturamento = (String) getParametro("periodoReferenciaFaturamento");
		String periodoVencimento = (String) getParametro("periodoVencimento");
		String matriculaImovel = (String) getParametro("matriculaImovel");
		String grupoFaturamento = (String) getParametro("grupoFaturamento");
		String localidadeInicial = (String) getParametro("localidadeInicial");
		String localidadeFinal = (String) getParametro("localidadeFinal");
		String setorComercialInicial = (String) getParametro("setorComercialInicial");
		String setorComercialFinal = (String) getParametro("setorComercialFinal");
		String rotaInicial = (String) getParametro("rotaInicial");
		String rotaFinal = (String) getParametro("rotaFinal");

		// Monta a coleção de beans do relatório.
		List relatorioBeans = new ArrayList();
		RelatorioContasPreFaturadasBean bean = null;
		BigDecimal valorTotalContas = BigDecimal.ZERO;

		for(Conta conta : colecaoConta){

			bean = new RelatorioContasPreFaturadasBean();

			// Grupo de Faturamento
			if(conta.getRota() != null && conta.getRota().getId() != null){

				bean.setIdGrupoFaturamento(conta.getRota().getFaturamentoGrupo().getId().toString());
				bean.setDescricaoGrupoFaturamento(conta.getRota().getFaturamentoGrupo().getDescricao());

				// Rota
				bean.setRota(conta.getRota().getId().toString());
			}else{

				bean.setIdGrupoFaturamento("0");
				bean.setDescricaoGrupoFaturamento("");
			}

			// Imóvel
			bean.setMatriculaImovel(conta.getImovel().getId().toString());

			// Referência da Conta
			bean.setReferenciaConta(conta.getReferenciaFormatada());

			// Inscrição
			bean.setInscricaoImovel(conta.getImovel().getInscricaoFormatada());

			// Situação de Água
			bean.setSituacaoLigacaoAgua(conta.getLigacaoAguaSituacao().getDescricao());

			// Situação de Esgoto
			bean.setSituacaoLigacaoEsgoto(conta.getLigacaoEsgotoSituacao().getDescricao());

			// Perfil do Imóvel
			if(conta.getImovelPerfil() != null && conta.getImovelPerfil().getDescricao() != null){

				bean.setPerfilImovel(conta.getImovelPerfil().getDescricao());
			}

			// Vencimento da Conta
			bean.setVencimentoConta(Util.formatarData(conta.getDataVencimentoConta()));

			// Tarifa
			bean.setTarifa(conta.getConsumoTarifa().getDescricao());

			// Valor da Conta
			bean.setValorConta(conta.getValorTotal());

			// Situação Atual
			bean.setSituacaoAtual(conta.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada());

			// Totalização
			valorTotalContas = valorTotalContas.add(bean.getValorConta());
			bean.setValorTotalContas(valorTotalContas);

			relatorioBeans.add(bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("periodoReferenciaFaturamento", periodoReferenciaFaturamento);
		parametros.put("periodoVencimento", periodoVencimento);
		parametros.put("matriculaImovel", matriculaImovel);
		parametros.put("grupoFaturamento", grupoFaturamento);
		parametros.put("localidadeInicial", localidadeInicial);
		parametros.put("localidadeFinal", localidadeFinal);
		parametros.put("setorComercialInicial", setorComercialInicial);
		parametros.put("setorComercialFinal", setorComercialFinal);
		parametros.put("rotaInicial", rotaInicial);
		parametros.put("rotaFinal", rotaFinal);

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){

			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_PRE_FATURADAS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_PRE_FATURADAS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = 0;

		Collection<Conta> colecaoConta = (Collection<Conta>) getParametro("colecaoConta");

		if(!Util.isVazioOrNulo(colecaoConta)){

			retorno = colecaoConta.size();
		}

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasPreFaturadas", this);
	}

}
