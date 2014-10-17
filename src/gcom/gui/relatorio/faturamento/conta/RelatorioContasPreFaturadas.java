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
 * [UC3035] Concluir Faturamento Contas Pr�-Faturadas
 * [SB0002] - Imprimir Rela��o das Contas Pr�-faturadas
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

		// Obt�m os par�metros passados.
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

		// Monta a cole��o de beans do relat�rio.
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

			// Im�vel
			bean.setMatriculaImovel(conta.getImovel().getId().toString());

			// Refer�ncia da Conta
			bean.setReferenciaConta(conta.getReferenciaFormatada());

			// Inscri��o
			bean.setInscricaoImovel(conta.getImovel().getInscricaoFormatada());

			// Situa��o de �gua
			bean.setSituacaoLigacaoAgua(conta.getLigacaoAguaSituacao().getDescricao());

			// Situa��o de Esgoto
			bean.setSituacaoLigacaoEsgoto(conta.getLigacaoEsgotoSituacao().getDescricao());

			// Perfil do Im�vel
			if(conta.getImovelPerfil() != null && conta.getImovelPerfil().getDescricao() != null){

				bean.setPerfilImovel(conta.getImovelPerfil().getDescricao());
			}

			// Vencimento da Conta
			bean.setVencimentoConta(Util.formatarData(conta.getDataVencimentoConta()));

			// Tarifa
			bean.setTarifa(conta.getConsumoTarifa().getDescricao());

			// Valor da Conta
			bean.setValorConta(conta.getValorTotal());

			// Situa��o Atual
			bean.setSituacaoAtual(conta.getDebitoCreditoSituacaoAtual().getDescricaoAbreviada());

			// Totaliza��o
			valorTotalContas = valorTotalContas.add(bean.getValorConta());
			bean.setValorTotalContas(valorTotalContas);

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
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

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_PRE_FATURADAS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_PRE_FATURADAS, idFuncionalidadeIniciada, null);
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
