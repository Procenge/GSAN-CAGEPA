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

package gcom.relatorio;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.Processo;
import gcom.gui.ActionServletException;
import gcom.relatorio.cobranca.RelatorioDadosImoveisComContaEmAtraso;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;

import javax.ejb.CreateException;

/**
 * Classe responsável por analisar cada relatorio
 * 
 * @author Thiago Toscano
 * @date 25/05/2006
 */
public class GerenciadorExecucaoTarefaRelatorio {

	/**
	 * Método responsável por verificar se a tarefaRelatorio será exibida online
	 * ou será armazenada em batch
	 * 
	 * @author Thiago Toscano
	 * @date 25/05/2006
	 * @param tarefaRelatorio
	 * @param tipoTarefa
	 */
	public static RelatorioProcessado analisarExecucao(TarefaRelatorio tarefaRelatorio, int tipoTarefa){

		RelatorioProcessado retorno = null;
		// pegando a quantidade de registro dessa tarefa
		int quantidadeRegistroGerado = tarefaRelatorio.calcularTotalRegistrosRelatorio();

		String nomeClasseRelatorio = tarefaRelatorio.getClass().getSimpleName();

		int quantidadeMaximaOnLineRelatorio = ConstantesExecucaoRelatorios.get(nomeClasseRelatorio);

		// se a quantidade a ser processada for maior que a permitida
		if(quantidadeMaximaOnLineRelatorio == ConstantesExecucaoRelatorios.QUANTIDADE_NAO_INFORMADA
						|| quantidadeRegistroGerado > quantidadeMaximaOnLineRelatorio){

			try{
				getControladorBatch().iniciarProcessoRelatorio(tarefaRelatorio);
			}catch(ControladorException e){
				throw new SistemaException(e, "Erro Batch Relatório");
			}

		}else{

			// caso contrario executa e monta o relatorio processado
			// Fazer depois -- toda tarefa deverá passar pelo agendador, mesmo se for imediata
			byte[] dados = (byte[]) tarefaRelatorio.executar();
			retorno = new RelatorioProcessado(dados, tipoTarefa);
		}

		return retorno;

	}

	/**
	 * Método responsável por verificar se a tarefaRelatorio será exibida online ou será armazenada
	 * em batch
	 * Retornando null, significa q foi pra batch, senão será aberto na tela.
	 * 
	 * @author isilva
	 * @date 24/09/2010
	 * @param tarefaRelatorio
	 * @param tipoTarefa
	 */
	public static RelatorioProcessado analisarExecucaoRelatorioOrdemServico(TarefaRelatorio tarefaRelatorio, int tipoTarefa){

		RelatorioProcessado retorno = null;
		// pegando a quantidade de registro dessa tarefa

		int quantidadeRegistroGerado = tarefaRelatorio.calcularTotalRegistrosRelatorio();

		String nomeClasseRelatorio = tarefaRelatorio.getClass().getSimpleName();

		int quantidadeMaximaOnLineRelatorio = ConstantesExecucaoRelatorios.get(nomeClasseRelatorio);

		// se a quantidade a ser processada for maior que a permitida
		if(quantidadeMaximaOnLineRelatorio == ConstantesExecucaoRelatorios.QUANTIDADE_NAO_INFORMADA
						|| quantidadeRegistroGerado > quantidadeMaximaOnLineRelatorio){

			// throw new ActionServletException("atencao.execucao.relatorio.batch");

			try{
				getControladorBatch().iniciarProcessoRelatorio(tarefaRelatorio);
			}catch(ControladorException e){
				throw new SistemaException(e, "Erro Batch Relatório");
			}

		}else{

			// caso contrario executa e monta o relatorio processado
			// Fazer depois -- toda tarefa deverá passar pelo agendador, mesmo se for imediata
			byte[] dados = (byte[]) tarefaRelatorio.executar();
			retorno = new RelatorioProcessado(dados, tipoTarefa);
		}

		return retorno;

	}

	public static RelatorioProcessado analisarExecucaoRelatorioOrdemSeletiva(TarefaRelatorio tarefaRelatorio, int tipoTarefa){

		RelatorioProcessado retorno = null;

		// Pegando a quantidade de registro dessa tarefa
		int quantidadeRegistroGerado = tarefaRelatorio.calcularTotalRegistrosRelatorio();

		if(quantidadeRegistroGerado != 0){
			// Esse relatório sempre deve ser executado em batch, pois a tela
			// não deve ficar esperando o processo terminar

			try{
				getControladorBatch().iniciarProcessoRelatorio(tarefaRelatorio);
			}catch(ControladorException e){
				throw new SistemaException(e, "Erro Batch Relatório");
			}
		}else{
			throw new ActionServletException("atencao.nenhum.imovel.encontrado");
		}

		return retorno;
	}

	/**
	 * Retorna o identificador do processo associado com o relatório
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/09/2006
	 * @param nomeRelatorio
	 * @return
	 */
	public static int obterProcessoRelatorio(String nomeRelatorio){

		int retornoCodigoProcesso = 0;

		if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_BAIRRO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_BAIRRO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CLIENTE_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_CLIENTE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SETOR_COMERCIAL_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_SETOR_COMERCIAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_LOCALIDADE_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_LOCALIDADE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_LOGRADOURO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_LOGRADOURO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_QUADRA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_QUADRA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEL_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_IMOVEL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_TARIFA_SOCIAL_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_TARIFA_SOCIAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SUBCATEGORIA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_SUBCATEGORIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ROTA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_ROTA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CATEGORIA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_CATEGORIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CRONOGRAMA_FATURAMENTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_CRONOGRAMA_FATURAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CRONOGRAMA_COBRANCA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_CRONOGRAMA_COBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CRITERIO_COBRANCA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_CRITERIO_COBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PERFIL_PARCELAMENTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_PERFIL_PARCELAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MOVIMENTO_ARRECADADOR_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_MOVIMENTO_ARRECADADOR;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MENSAGEM_CONTA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_MENSAGEM_CONTA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_BANCARIO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_AVISO_BANCARIO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GUIA_DEVOLUCAO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_GUIA_DEVOLUCAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEL_OUTROS_CRITERIOS_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_IMOVEL_OUTROS_CRITERIOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DADOS_ECONOMIA_IMOVEL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DADOS_TARIFA_SOCIAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DADOS_TARIFA_SOCIAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_HIDROMETRO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_HIDROMETRO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESOLUCAO_DIRETORIA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_RESOLUCAO_DIRETORIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ARRECADACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_ARRECADACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERAR_RELACAO_DEBITOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_GERAR_RELACAO_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DEVOLUCAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DEVOLUCAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PAGAMENTO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PAGAMENTO_TOTALIZADO_POR_DATA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_PAGAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_2_VIA_CONTA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SEGUNDA_VIA_CONTA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PARCELAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_PARCELAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EXTRATO_DEBITO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_EXTRATO_DEBITO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_EMITIR)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_EMITIR_GUIA_PAGAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GUIA_DEVOLUCAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_GUIA_DEVOLUCAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ROTEIRO_PROGRAMACAO_LAYOUT_PAISAGEM)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ROTEIRO_PROGRAMACAO_LAYOUT_RETRATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ROTEIRO_PROGRAMACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_MANUT_RAMAL_ESGOTO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_VERIFICACAO_LIMPEZA)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DESOBSTRUCAO_RAMAL_ESGOTO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_INSTALACAO_HIDROMETRO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SUBSTITUICAO_HIDROMETRO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_VERIFICACAO_LIGACAO_AGUA)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_CAERD)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FISCALIZACAO_ORDEM_CORTE)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FISCALIZACAO_ORDEM_SUPRESSAO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_COBRANCA)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_PADRAO_COM_OCORRENCIA)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_ESTRUTURA)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_BASE_COM_SUBRELATORIOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ORDEM_SERVICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_OPERACAO_CONSULTAR)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONSULTAR_OPERACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_NUMERACAO_RA_MANUAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_NUMERACAO_RA_MANUAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_EXECUCAO_OS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ACOMPANHAMENTO_EXECUCAO_OS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PRODUTIVIDADE_EQUIPE)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_PRODUTIVIDADE_EQUIPE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_OS_NAO_EXECUTADA_EQUIPE)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_OS_NAO_EXECUTADA_EQUIPE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MOVIMENTO_ARRECADADOR)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MOVIMENTO_ARRECADADOR;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MAPA_CONTROLE_CONTA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MAPA_CONTROLE_CONTA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_COMPARATIVOS_LEITURAS_E_ANORMALIDADES)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_COMPARATIVO_LEITURAS_E_ANORMALIDADE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_2_VIA_CONTA_TIPO_2)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SEGUNDA_VIA_CONTA_TIPO_2;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CLIENTES_ESPECIAIS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CLIENTES_ESPECIAIS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEL_ENDERECO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEL_ENDERECO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_ECONOMIA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HISTOGRAMA_AGUA_ECONOMIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_LIGACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HISTOGRAMA_AGUA_LIGACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CADASTRO_CONSUMIDORES_INSCRICAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CADASTRO_CONSUMIDORES_INSCRICAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTA_TIPO_2)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTA_MODELO_2)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTA_MODELO_3)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTA_TIPO_2;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ORIGINAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ORDEM_CORTE_ORIGINAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FATURA_CLIENTE_RESPONSAVEL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_FATURA_CLIENTE_RESPONSAVEL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.BOLETIM_CADASTRO)){

			retornoCodigoProcesso = Processo.GERAR_BOLETIM_CADASTRO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_VOLUMES_FATURADOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_VOLUMES_FATURADOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_VOLUMES_FATURADOS_RESUMIDO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_VOLUMES_FATURADOS_RESUMIDO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_EM_REVISAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTAS_EM_REVISAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_EM_REVISAO_RESUMIDO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTAS_EM_REVISAO_RESUMIDO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERAR_CURVA_ABC_DEBITOS)){

			retornoCodigoProcesso = Processo.GERAR_CURVA_ABC_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANORMALIDADE_CONSUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ANORMALIDADE_CONSUMO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HISTOGRAMA_ESGOTO_ECONOMIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_LIGACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HISTOGRAMA_ESGOTO_LIGACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORCAMENTO_SINP)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ORCAMENTO_SINP;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_SITUACAO_LIGACAO_AGUA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_SITUACAO_LIGACAO_AGUA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_ATRASO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_FATURAS_ATRASO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_CONSUMO_MEDIO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_CONSUMO_MEDIO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA)){

			retornoCodigoProcesso = Processo.GERAR_EMITIR_ORDEM_SERVICO_SELETIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO)){

			retornoCodigoProcesso = Processo.GERAR_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_ATIVOS_NAO_MEDIDOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_ATIVOS_NAO_MEDIDOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_FISCALIZACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ORDEM_FISCALIZACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_TIPO_CONSUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_TIPO_CONSUMO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_FATURAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_1)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_2)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_3)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CERTIDAO_NEGATIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_DEBITO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_DEBITO_MODELO_2) || nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_DEBITO_MODELO3)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_AVISO_DEBITO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_CORTE)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_AVISO_CORTE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_TELECOBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_TELECOBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_JURIDICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_JURIDICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_NEGATIVACAO_SPC_SERASA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SPC_SERASA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FECHAMENTO_COBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_FECHAMENTO_COBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EFICIENCIA_COBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_EFICIENCIA_COBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO)){

			retornoCodigoProcesso = Processo.GERAR_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEL_POR_ACAO_COBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEL_POR_ACAO_COBRANCA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RELIGACAO_POR_IMOVEL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RELIGACOES_POR_IMOVEL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_ACAO)){

			retornoCodigoProcesso = Processo.GERAR_ACOMPANHAMENTO_ACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_NEGATIVACOES_EXCLUIDAS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_NEGATIVACOES_EXCLUIDAS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_CLIENTES_NEGATIVADOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ACOMPANHAMENTO_CLIENTES_NEGATIVADOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EXCLUSAO_NEGATIVACAO_SPC_SERASA)){

			retornoCodigoProcesso = Processo.GERAR_REL_EXC_NEGATIVACAO_SPC_SERASA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANALISE_CONSUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ANALISE_CONSUMO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANALISE_CONSUMO_EXCEL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ANALISE_CONSUMO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_LOGRADOURO_GERAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_LOGRADOURO_GERAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SUBSISTEMA_ESGOTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_SUBSISTEMA_ESGOTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SISTEMA_ESGOTAMENTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_SISTEMA_ESGOTAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SUBBACIA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_SUBBACIA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ZONA_ABASTECIMENTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_ZONA_ABASTECIMENTO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_BACIA_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_BACIA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GRANDES_CONSUMIDORES)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_GRANDES_CONSUMIDORES;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_USUARIO_DEBITO_AUTOMATICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_USUARIO_DEBITO_AUTOMATICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CARTA_OPCAO_PARCELAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_REL_CARTA_OPCAO_PARCELAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_OS_ENCERRADAS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_OS_PENDENTES;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MACROMEDIDORES_MICROMEDIDOS_ASSOCIADOS_ULTIMOS_CONSUMOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MICROMEDICAO_CONSUMOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MICROMEDIDORES)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MICROMEDICAO_ASSOCIADOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERAR_COMPROVANTES_LEITURA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_COMPROVANTES_LEITURA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERAR_DADOS_LEITURA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DADOS_LEITURA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_SINTETICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_SINTETICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANORMALIDADES_LEITURAS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ANORMALIDADES_LEITURAS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DADOS_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DADOS_FATURAMENTO_IMEDIATO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_TABELAS_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_TABELAS_FATURAMENTO_IMEDIATO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_FATURAMENTO_IMEDIATO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERACAO_CARTAS_COBRANCA_BANCARIA)){

			retornoCodigoProcesso = Processo.GERAR_CARTAS_COBRANCA_BANCARIA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_ACAO_COBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_LISTA_DE_IMOVEIS_ACAO_COBRANCA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_BOLETOS_BANCARIOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_BOLETOS_BANCARIOS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_CORTE_ARQUIVO_TXT)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_4)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_5)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_AVISO_CORTE_ARQUIVO_TXT;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_TXT) //
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_4) //
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_5)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ORDEM_CORTE_ARQUIVO_TXT;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_GUIA_PAGAMENTO_MANTER;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_POR_QUADRA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_POR_QUADRA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_LEITURA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_LEITURA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_CONSUMO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MOVIMENTO_ARRECADADOR)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_PRE_FATURADAS)){

			retornoCodigoProcesso = Processo.GERAR_RELACAO_DAS_CONTAS_PRE_FATURADAS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_GERACAO_ORDEM_SERVICO_TXT)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_GERACAO_ORDEM_SERVICO_TXT;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_1)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_2)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_3)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_4)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_5)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_6)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_7)){

			retornoCodigoProcesso = Processo.GERAR_EMITIR_ORDEM_SERVICO_SELETIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MUNICIPIO_MANTER)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MUNICIPIO_MANTER;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DADOS_TABELAS_FATURAMENTO_IMEDIATO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DADOS_TABELAS_FATURAMENTO_IMEDIATO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO;

		}else if(ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_ECONOMIA.equals(nomeRelatorio)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RESUMO_LIGACOES_ECONOMIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_FATURAMENTO_CONVENCIONAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_FATURAMENTO_CONVENCIONAL;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CASAL)){

			retornoCodigoProcesso = Processo.RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CASAL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_FATURAMENTO)){

			retornoCodigoProcesso = Processo.RELATORIO_ACOMPANHAMENTO_FATURAMENTO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ESTATISTICO_RA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_PROCESSAR_ARQUIVO_DADOS_LEITURA_E_ANORMALIDADE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DEBITO_POR_RESPONSAVEL_ANALITICO)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_DEBITO_POR_RESPONSAVEL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO)){

			retornoCodigoProcesso = Processo.RELATORIO_LIQUIDOS_RECEBIVEIS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL)
						|| nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SETOR_ABASTECIMENTO_MANTER)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SETOR_ABASTECIMENTO_MANTER;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_EXCLUIDOS)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_EXCLUIDOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO_CATEGORIAS)){
			retornoCodigoProcesso = Processo.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL)){
			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO)){
			retornoCodigoProcesso = Processo.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_ESGOTO_ECONOMIA_TXT)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HISTOGRAMA_AGUA_ESGOTO_ECONOMIA_TXT;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MODELO_2)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA)){

			retornoCodigoProcesso = Processo.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA_MODELO_2)){

			retornoCodigoProcesso = Processo.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MANTER_CEP)){

			retornoCodigoProcesso = Processo.RELATORIO_MANTER_CEP;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_HIDROMETRO_MOVIMENTACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_HIDROMETRO_MOVIMENTACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ARQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR)){
			retornoCodigoProcesso = Processo.GERAR_AQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_IMOVEIS_INSCRICAO_ALTERADA)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_IMOVEIS_INSCRICAO_ALTERADA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_RETIRADAS_REVISAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTAS_RETIRADAS_REVISAO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_BLOQUEADAS_ANALISE)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTAS_BLOQUEADAS_ANALISE;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_AUDITORIA_LEITURA)){

			retornoCodigoProcesso = Processo.RELATORIO_AUDITORIA_LEITURA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_COMANDO_OS_SELETIVA)){

			retornoCodigoProcesso = Processo.RELATORIO_COMANDO_OS_SELETIVA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA)){

			retornoCodigoProcesso = Processo.RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES)){

			retornoCodigoProcesso = Processo.RELATORIO_ACOMP_MOV_ARRECADADORES;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MAIORES_CONSUMIDORES)){

			retornoCodigoProcesso = Processo.RELATORIO_MAIORES_CONSUMIDORES;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MAIORES_DEVEDORES)){

			retornoCodigoProcesso = Processo.RELATORIO_MAIORES_DEVEDORES;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_QUADRO_HIDROMETROS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_SITUACAO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_QUADRO_HIDROMETROS_SITUACAO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ORDENS_SERVICO_ENCERRADAS_DENTRO_FORA_PRAZO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_OS_ENCERRADA_DENTRO_FORA_PRAZO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MANTER_USUARIO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MANTER_USUARIO;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MATERIAIS_APLICADOS)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_MATERIAIS_APLICADOS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_REGISTRO_ATENDIMENTO_COM_PROCESSO_ADM_JUD)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_RA_COM_PROCESSO_ADM_JUD;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DEBITOS_PRESCRITOS)){
			retornoCodigoProcesso = Processo.RELATORIO_DEBITOS_PRESCRITOS;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS)){

			retornoCodigoProcesso = Processo.RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTRATO_DEMANDA_CONSUMO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_CONTRATO_DEMANDA_CONSUMO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO)){

			retornoCodigoProcesso = Processo.RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANALITICO_FATURAMENTO)){

			retornoCodigoProcesso = Processo.GERAR_RELATORIO_ANALITICO_FATURAMENTO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES)){

			retornoCodigoProcesso = Processo.RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE_ECONOMICA)){

			retornoCodigoProcesso = Processo.RELATORIO_MANTER_ATIVIDADE_ECONOMICA;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_RECALCULADAS)){

			retornoCodigoProcesso = Processo.RELATORIO_CONTAS_RECALCULADAS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANALITICO_CONTAS)){

			retornoCodigoProcesso = Processo.RELATORIO_ANALITICO_CONTAS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB)){

			retornoCodigoProcesso = Processo.RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA)){
			retornoCodigoProcesso = Processo.RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA;
		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_MANTER_ORDEM_SERVICO)){
			retornoCodigoProcesso = Processo.RELATORIO_MANTER_ORDEM_SERVICO;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS)){

			retornoCodigoProcesso = Processo.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS;

		}else if(nomeRelatorio.equals(ConstantesRelatorios.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE)){

			retornoCodigoProcesso = Processo.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE;
		}

		return retornoCodigoProcesso;

	}

	private static ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Método que iniciar o processamento de um relatório a partir da tela de inserir processo
	 * eventual
	 * 
	 * @author Anderson italo
	 * @date 13/03/2014
	 */
	public static boolean verificarGeracaoRelatorioProcessoEventual(int idProcesso){

		boolean retorno = false;
		if(idProcesso == Processo.RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO){

			retorno = true;
			RelatorioDadosImoveisComContaEmAtraso relatorioDadosImoveisComContaEmAtraso = new RelatorioDadosImoveisComContaEmAtraso(
							Usuario.USUARIO_BATCH);
			relatorioDadosImoveisComContaEmAtraso.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

			try{

				getControladorBatch().iniciarProcessoRelatorio(relatorioDadosImoveisComContaEmAtraso);
			}catch(ControladorException e){
				throw new SistemaException(e, "Erro Batch Relatório");
			}
		}

		return retorno;
	}

	public static RelatorioProcessado analisarExecucaoRelatorioResumoOrdemServico(TarefaRelatorio tarefaRelatorio, int tipoTarefa){

		RelatorioProcessado retorno = null;
		// pegando a quantidade de registro dessa tarefa

		String nomeClasseRelatorio = tarefaRelatorio.getClass().getSimpleName();

		// caso contrario executa e monta o relatorio processado
		// Fazer depois -- toda tarefa deverá passar pelo agendador, mesmo se for imediata
		byte[] dados = (byte[]) tarefaRelatorio.executar();
		retorno = new RelatorioProcessado(dados, tipoTarefa);

		return retorno;

	}

}
