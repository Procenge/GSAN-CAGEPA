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

package gcom.batch;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.financeiro.ControladorFinanceiroLocal;
import gcom.financeiro.ControladorFinanceiroLocalHome;
import gcom.financeiro.ParametrosDevedoresDuvidosos;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.tarefa.TarefaBatch;
import gcom.util.*;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

import javax.ejb.CreateException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FuncionalidadeIniciada
				implements Serializable {

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date dataHoraInicio;

	/** nullable persistent field */
	private Date dataHoraTermino;

	/** persistent field */
	private gcom.batch.FuncionalidadeSituacao funcionalidadeSituacao;

	/** persistent field */
	private gcom.batch.ProcessoIniciado processoIniciado;

	/** persistent field */
	private gcom.batch.ProcessoFuncionalidade processoFuncionalidade;

	/** persistent field */
	private Set unidadesIniciadas;

	/** persistent field */
	private Set relatoriosGerados;

	private byte[] tarefaBatchBlob;

	private byte[] textoLogExecucaoBlob;

	/** full constructor */
	public FuncionalidadeIniciada(Date dataHoraInicio, Date dataHoraTermino, gcom.batch.FuncionalidadeSituacao funcionalidadeSituacao,
									gcom.batch.ProcessoIniciado processoIniciado, gcom.batch.ProcessoFuncionalidade processoFuncionalidade,
									Set unidadesIniciadas, Set relatoriosGerados) {

		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraTermino = dataHoraTermino;
		this.funcionalidadeSituacao = funcionalidadeSituacao;
		this.processoIniciado = processoIniciado;
		this.processoFuncionalidade = processoFuncionalidade;
		this.unidadesIniciadas = unidadesIniciadas;
		this.relatoriosGerados = relatoriosGerados;
	}

	/** default constructor */
	public FuncionalidadeIniciada() {

	}

	/** minimal constructor */
	public FuncionalidadeIniciada(gcom.batch.FuncionalidadeSituacao funcionalidadeSituacao, gcom.batch.ProcessoIniciado processoIniciado,
									gcom.batch.ProcessoFuncionalidade processoFuncionalidade, Set unidadesIniciadas, Set relatoriosGerados) {

		this.funcionalidadeSituacao = funcionalidadeSituacao;
		this.processoIniciado = processoIniciado;
		this.processoFuncionalidade = processoFuncionalidade;
		this.unidadesIniciadas = unidadesIniciadas;
		this.relatoriosGerados = relatoriosGerados;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataHoraInicio(){

		return this.dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio){

		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraTermino(){

		return this.dataHoraTermino;
	}

	public void setDataHoraTermino(Date dataHoraTermino){

		this.dataHoraTermino = dataHoraTermino;
	}

	public gcom.batch.FuncionalidadeSituacao getFuncionalidadeSituacao(){

		return this.funcionalidadeSituacao;
	}

	public void setFuncionalidadeSituacao(gcom.batch.FuncionalidadeSituacao funcionalidadeSituacao){

		this.funcionalidadeSituacao = funcionalidadeSituacao;
	}

	public gcom.batch.ProcessoIniciado getProcessoIniciado(){

		return this.processoIniciado;
	}

	public void setProcessoIniciado(gcom.batch.ProcessoIniciado processoIniciado){

		this.processoIniciado = processoIniciado;
	}

	public gcom.batch.ProcessoFuncionalidade getProcessoFuncionalidade(){

		return this.processoFuncionalidade;
	}

	public void setProcessoFuncionalidade(gcom.batch.ProcessoFuncionalidade processoFuncionalidade){

		this.processoFuncionalidade = processoFuncionalidade;
	}

	public Set getUnidadesIniciadas(){

		return this.unidadesIniciadas;
	}

	public void setUnidadesIniciadas(Set unidadesIniciadas){

		this.unidadesIniciadas = unidadesIniciadas;
	}

	public Set getRelatoriosGerados(){

		return this.relatoriosGerados;
	}

	public void setRelatoriosGerados(Set relatoriosGerados){

		this.relatoriosGerados = relatoriosGerados;
	}

	public byte[] getTextoLogExecucao(){

		return textoLogExecucaoBlob;
	}

	public void setTextoLogExecucao(byte[] textoLogExecucao){

		textoLogExecucaoBlob = textoLogExecucao;
	}

	public void setTextoLogExecucao(Blob textoLogExecucao){

		textoLogExecucaoBlob = IoUtil.toByteArray(textoLogExecucao);
	}

	public byte[] getTextoLogExecucaoBlob(){

		return textoLogExecucaoBlob;
	}

	public void setTextoLogExecucaoBlob(byte[] textoLogExecucaoBlob){

		this.textoLogExecucaoBlob = textoLogExecucaoBlob;
	}

	public void setTextoLogExecucaoBlob(Blob textoLogExecucaoBlob){

		this.textoLogExecucaoBlob = IoUtil.toByteArray(textoLogExecucaoBlob);
	}

	public void adicionarLinhaTextoLogExecucao(String textoLogExecucao) throws IOException, ClassNotFoundException{

		StringBuffer log = null;
		if(!Util.isVazioOuBranco(this.getTextoLogExecucao())){
			log = new StringBuffer((String) IoUtil.transformarBytesParaObjeto(this.getTextoLogExecucao()));
			log.append("\n");
		}else{
			log = new StringBuffer();
		}
		log.append(textoLogExecucao);
		this.setTextoLogExecucao(IoUtil.transformarObjetoParaBytes(log.toString()));
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return the tarefaBatch
	 */
	public byte[] getTarefaBatch(){

		return tarefaBatchBlob;
	}

	/**
	 * @return the tarefaBatchBlob
	 */
	public byte[] getTarefaBatchBlob(){

		return tarefaBatchBlob;
	}

	public void setTarefaBatch(byte[] tarefaBatch){

		tarefaBatchBlob = tarefaBatch;
	}

	public void setTarefaBatch(Blob tarefaBatchBlob){

		this.tarefaBatchBlob = IoUtil.toByteArray(tarefaBatchBlob);
	}

	/**
	 * @param setTarefaBatchBlob
	 *            the TarefaBatchBlob to set
	 *            Apenas para o Tratamento de Blobs do Hibernate, não invocar.
	 */
	public void setTarefaBatchBlob(byte[] tarefaBatchBlob){

		this.tarefaBatchBlob = tarefaBatchBlob;
	}

	public void setTarefaBatchBlob(Blob tarefaBatchBlob){

		this.tarefaBatchBlob = IoUtil.toByteArray(tarefaBatchBlob);
	}

	public String getDiferencaInicioTermino(){

		String retorno = "";
		if(this.dataHoraInicio != null && this.dataHoraTermino != null){

			if(this.dataHoraTermino.getTime() < this.dataHoraInicio.getTime()){
				this.dataHoraTermino = new Date();
			}

			long diferencaEmMilisegundos = this.dataHoraTermino.getTime() - this.dataHoraInicio.getTime();

			long diferencaEmHoras = diferencaEmMilisegundos / (60 * 60 * 1000);

			long diferencaEmMinutos = (diferencaEmMilisegundos / (60 * 1000)) % 60;

			long diferencaEmSegundos = (diferencaEmMilisegundos / (1000)) % 60;

			if(diferencaEmHoras < 10){
				retorno = "0" + diferencaEmHoras;
			}else{
				retorno = "" + diferencaEmHoras;
			}

			if(diferencaEmMinutos < 10){
				retorno = retorno + ":0" + diferencaEmMinutos;
			}else{
				retorno = retorno + ":" + diferencaEmMinutos;
			}

			if(diferencaEmSegundos < 10){
				retorno = retorno + ":0" + diferencaEmSegundos;
			}else{
				retorno = retorno + ":" + diferencaEmSegundos;
			}

		}
		return retorno;
	}

	public void finalizador(){

		try{
			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

			TarefaBatch tarefaBatch = (TarefaBatch) IoUtil.transformarBytesParaObjeto(getTarefaBatch());

			if(tarefaBatch != null){

				switch(this.processoFuncionalidade.getFuncionalidade().getId()){

					case Funcionalidade.GERAR_DADOS_PARA_LEITURA:

						Integer anoMesFaturamentoGrupoLeitura = (Integer) tarefaBatch.getParametro("anoMesFaturamentoGrupo");

						Integer idFaturamentoGrupo = (Integer) tarefaBatch.getParametro("idFaturamentoGrupo");

						// atualiza a data e a hora da realização da atividade com a
						// data e
						// a hora correntes

						getControladorFaturamento().atualizarDataHoraRealizacaoAtividade(FaturamentoAtividade.GERAR_ARQUIVO_LEITURA,
										anoMesFaturamentoGrupoLeitura, idFaturamentoGrupo);

						break;

					case Funcionalidade.FATURAR_GRUPO_FATURAMENTO:

						FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) tarefaBatch.getParametro("faturamentoGrupo");
						Integer anoMesFaturamentoGrupo = (Integer) tarefaBatch.getParametro("anoMesFaturamentoGrupo");
						Integer atividade = (Integer) tarefaBatch.getParametro("atividade");

						/*
						 * getControladorFaturamento()
						 * .emitirExtratoConsumoImovelCondominio(
						 * colecaoRotasParaExecucao,
						 * "" + anoMesFaturamentoGrupo,
						 * "" + faturamentoGrupo.getId());
						 */

						/*
						 * Caso a atividade que esteja sendo executada, corresponda
						 * a faturar grupo, atualiza o ano/mês de referência do
						 * faturamento para o mês seguinte.
						 */
						if(atividade == FaturamentoAtividade.FATURAR_GRUPO.intValue()){
							getControladorFaturamento().atualizarAnoMesReferenciaFaturamentoGrupo(faturamentoGrupo, anoMesFaturamentoGrupo,
											atividade);
						}

						break;

					case Funcionalidade.GERAR_FATURAMENTO_IMEDIATO:

						FaturamentoGrupo faturamentoGrupoFaturamentoImediato = (FaturamentoGrupo) tarefaBatch
										.getParametro("faturamentoGrupo");
						Integer anoMesFaturamentoGrupoFaturamentoImediato = (Integer) tarefaBatch.getParametro("anoMesFaturamentoGrupo");
						Integer atividadeFaturamentoImediato = (Integer) tarefaBatch.getParametro("atividade");

						System.out.println("**********************ENTROU PARA ATUALIZAR DATAHORA REALIZAÇÃO DA ATIVIDADE:  "
										+ faturamentoGrupoFaturamentoImediato.getId().toString() + "********************");
						/*
						 * Caso a atividade que esteja sendo executada, corresponda
						 * a faturar grupo, atualiza o ano/mês de referência do
						 * faturamento para o mês seguinte.
						 */
						if(atividadeFaturamentoImediato == FaturamentoAtividade.GERAR_FATURAMENTO_IMEDIATO.intValue()){
							getControladorFaturamento().atualizarDataHoraRealizacaoAtividade(atividadeFaturamentoImediato,
											anoMesFaturamentoGrupoFaturamentoImediato, faturamentoGrupoFaturamentoImediato.getId());
						}

						break;

					case Funcionalidade.CONSISTIR_LEITURAS_E_CALCULAR_CONSUMOS:

						FaturamentoGrupo faturamentoGrupoConsistirLeituras = (FaturamentoGrupo) tarefaBatch
										.getParametro("faturamentoGrupo");
						SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

						System.out.println("**********************ENTROU PARA ATUALIZAR DATAHORA REALIZAÇÃO DA ATIVIDADE:  "
										+ faturamentoGrupoConsistirLeituras.getId().toString() + "********************");

						getControladorFaturamento().atualizarDataHoraRealizacaoAtividade(
										FaturamentoAtividade.CONSISTIR_LEITURAS_E_CALCULAR_CONSUMOS,
										sistemaParametro.getAnoMesFaturamento(), faturamentoGrupoConsistirLeituras.getId());

						break;

					case Funcionalidade.GERAR_HISTORICO_PARA_ENCERRAR_FATURAMENTO_MES:

						/**
						 * Item 12 do batch de encerrar faturamento do mês.
						 */
						// recupera o ano/mês de referência do faturamento
						int anoMesFaturamentoSistemaParametro = sistemaParametros.getAnoMesFaturamento();

						getControladorFaturamento().atualizarAnoMesFaturamento(anoMesFaturamentoSistemaParametro);

						break;

					case Funcionalidade.GERAR_HISTORICO_PARA_ENCERRAR_ARRECADACAO_MES:

						/**
						 * Item 11 do batch de encerrar arrecadação do mês.
						 */
						// recupera o ano/mês de referência da arrecadação
						int anoMesArrecadacaoSistemaParametro = sistemaParametros.getAnoMesArrecadacao();

						getControladorArrecadacao().atualizarAnoMesArrecadacao(anoMesArrecadacaoSistemaParametro);

						break;

					case Funcionalidade.GERAR_RESUMO_DEVEDORES_DUVIDOSOS:

						Integer anoMesReferenciaContabil = (Integer) tarefaBatch.getParametro("anoMesReferenciaContabil");
						ParametrosDevedoresDuvidosos parametrosDevedoresDuvidosos = getControladorFinanceiro()
										.pesquisarParametrosDevedoresDuvidosos(anoMesReferenciaContabil);
						parametrosDevedoresDuvidosos.setDataProcessamento(new Date());
						this.getControladorUtil().atualizar(parametrosDevedoresDuvidosos);

						break;

					case Funcionalidade.GERAR_DADOS_DIARIOS_ARRECADACAO:

						sistemaParametros.setDataHoraDadosDiariosArrecadacao(new Date());

						System.out.println("**********************ENTROU PARA ATUALIZAR DATAHORA DA GERAÇÃO DOS DADOS********************");

						getControladorUtil().atualizar(sistemaParametros);

						break;

					case Funcionalidade.GERAR_ATIVIDADE_ACAO_COBRANCA:

						CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = (CobrancaAcaoAtividadeCronograma) tarefaBatch
										.getParametro("comandoAtividadeAcaoCobranca");
						CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) tarefaBatch
										.getParametro("comandoAtividadeAcaoComando");
						CobrancaAcao acaoCobranca = (CobrancaAcao) tarefaBatch.getParametro("acaoCobranca");

						Date dataCorrente = new Date();

						if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){

							cobrancaAcaoAtividadeCronograma.setRealizacao(dataCorrente);

						}else if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){

							// caso a quantidade de dias de realização seja diferente de nulo
							if(cobrancaAcaoAtividadeComando.getQuantidadeDiasRealizacao() != null){
								// caso a atividade seja diferente de nulo
								if(cobrancaAcaoAtividadeComando.getCobrancaAtividade() != null){
									// caso a atividade seja EMITIR, então a data de encerramento
									// prevista recebe soma a data de realização com a quantidade de
									// dias de realização
									if(cobrancaAcaoAtividadeComando.getCobrancaAtividade().getId().equals(CobrancaAtividade.EMITIR)){
										Date dataEncerramentoPrevista = Util.adicionarNumeroDiasDeUmaData(dataCorrente,
														cobrancaAcaoAtividadeComando.getQuantidadeDiasRealizacao());
										cobrancaAcaoAtividadeComando.setDataEncerramentoPrevista(dataEncerramentoPrevista);
									}
								}
							}

							if(acaoCobranca.getNumeroDiasValidade() != null){
								Date prazoAcao = Util.adicionarNumeroDiasDeUmaData(dataCorrente, acaoCobranca.getNumeroDiasValidade());
								cobrancaAcaoAtividadeComando.setDataLimiteAcao(prazoAcao);
							}

							if(cobrancaAcaoAtividadeComando.getCobrancaAtividade().getId().equals(CobrancaAtividade.SIMULAR)){
								cobrancaAcaoAtividadeComando.setDataEncerramentoPrevista(dataCorrente);
								cobrancaAcaoAtividadeComando.setDataEncerramentoRealizada(dataCorrente);
							}

							cobrancaAcaoAtividadeComando.setRealizacao(dataCorrente);
						}

						this.getControladorCobranca().atualizarTotalizadoresCobrancaAcaoAtividade(cobrancaAcaoAtividadeComando,
										cobrancaAcaoAtividadeCronograma);

						break;

				}
			}

			// Se ocorrer algum erro com o finalizador a funcionalidadeIniciada
			// é marcada como CONCLUIDA_COM_ERRO
		}catch(ControladorException ex){
			ex.printStackTrace();
			this.funcionalidadeSituacao.setId(FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
		}catch(IOException ex){
			ex.printStackTrace();
			this.funcionalidadeSituacao.setId(FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
			this.funcionalidadeSituacao.setId(FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
		}catch(NullPointerException ex){
			ex.printStackTrace();
			this.funcionalidadeSituacao.setId(FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
		}

	}

	/**
	 * Retorna o valor de controladorArrecadacao
	 * 
	 * @return O valor de controladorCliente
	 */
	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorFinanceiro
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorFinanceiroLocal getControladorFinanceiro(){

		ControladorFinanceiroLocalHome localHome = null;
		ControladorFinanceiroLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFinanceiroLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FINANCEIRO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);

			// guarda a referencia de um objeto capaz de fazer chamadas à objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof FuncionalidadeIniciada)) return false;
		FuncionalidadeIniciada castOther = (FuncionalidadeIniciada) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}
}
