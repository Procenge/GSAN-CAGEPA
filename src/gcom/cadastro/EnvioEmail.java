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

package gcom.cadastro;

import gcom.seguranca.acesso.Funcionalidade;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EnvioEmail
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private String emailRemetente;

	/** persistent field */
	private String emailReceptor;

	/** persistent field */
	private String tituloMensagem;

	/** persistent field */
	private String corpoMensagem;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Funcionalidade funcionalidade;

	// Constantes das funcionalidades Batch que tem envio de e-mail
	public final static Integer GERAR_MOVIMENTO_AUTOMATICO_BANCO = 1;

	public final static Integer GERAR_MOVIMENTO_AUTOMATICO_BANCO_COM_ERRO = 2;

	public final static Integer REGERAR_MOVIMENTO_AUTOMATICO_BANCO = 6;

	public final static Integer REGERAR_MOVIMENTO_AUTOMATICO_BANCO_COM_ERRO = 7;

	public final static Integer REGISTRAR_MOVIMENTO_ARRECADADORES = 3;

	public final static Integer REGISTRAR_MOVIMENTO_ARRECADADORES_COM_ERRO = 4;

	public final static Integer REGISTRAR_MOVIMENTO_ARRECADADORES_PDF = 5;

	public final static Integer GERAR_DADOS_PARA_LEITURA_MICRO_COLETOR = 8;

	public final static Integer REGISTRAR_LEITURAS_ANORMALIDADES = 9;

	public final static Integer REGISTRAR_LEITURAS_ANORMALIDADES_COM_ERRO = 10;

	public final static Integer GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA = 11;

	public final static Integer GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA_FALHA = 12;

	public final static Integer GERAR_RESUMO_ANORMALIDADE_CONSUMO = 13;

	public final static Integer GERAR_RESUMO_ANORMALIDADE_CONSUMO_FALHA = 14;

	public final static Integer GERAR_RESUMO_ANORMALIDADE_LEITURA = 15;

	public final static Integer GERAR_RESUMO_ANORMALIDADE_LEITURA_FALHA = 16;

	public final static Integer PESQUISAR_IDS_TODAS_CONTAS = 17;

	public final static Integer PESQUISAR_IDS_TODAS_CONTAS_FALHA = 18;

	public final static Integer CLASSIFICAR_PAGAMENTOS_DEVOLUCOES = 19;

	public final static Integer CLASSIFICAR_PAGAMENTOS_DEVOLUCOES_FALHA = 20;

	public final static Integer GERAR_FATURAMENTO_CLIENTE_RESPONSAVEL = 21;

	public final static Integer GERAR_FATURAMENTO_CLIENTE_RESPONSAVEL_FALHA = 22;

	public final static Integer ENCERRAR_ARRECADACAO_MES = 23;

	public final static Integer ENCERRAR_ARRECADACAO_MES_FALHA = 24;

	public final static Integer GERAR_MOVIMENTO_CONTABEIS_ARRECADACAO = 25;

	public final static Integer GERAR_MOVIMENTO_CONTABEIS_ARRECADACAO_FALHA = 26;

	public final static Integer DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA = 27;

	public final static Integer DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA_FALHA = 28;

	public final static Integer GERAR_RESUMO_DEVEDORES_DUVIDOSOS = 29;

	public final static Integer GERAR_RESUMO_DEVEDORES_DUVIDOSOS_FALHA = 30;

	public final static Integer INSERIR_USUARIO = 31;

	public final static Integer EMITIR_EXTRATO_CONSUMO_IMOVEL_CONDOMINIO = 32;

	public final static Integer ENTRE_EM_CONTATO = 33;

	public final static Integer GERAR_ARQUIVO_TEXTO_FATURAMENTO = 37;

	public final static Integer GERAR_RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES = 35;

	public final static Integer GERAR_RELATORIO_MAPA_CONTROLE_CONTA = 36;

	public final static Integer GERAR_DADOS_PARA_LEITURA_RELATORIO_LEITURA_CONVENCIONAL = 38;

	public final static Integer REGISTRAR_FATURAMENTO_IMEDIATO = 39;

	public final static Integer GERAR_ARQUIVO_ERRO_INSTALACAO_HIDROMETROS_LOTE = 40;

	public final static Integer SPC_SERASA = 41;

	public final static Integer SPC_SERASA_MOV_RETORNO = 42;

	public final static Integer ARQUIVO_RETORNO_BAIXA_ORDEM_SERVICO = 43;

	public final static Integer UNIFICACAO_CADASTRO_CLIENTES_REDUNDANTES = 45;

	public final static Integer PROCESSAR_ARQUIVO_LEITURA_ANORMALIDADES = 46;

	public final static Integer ARQUIVO_DADOS_FATURAMENTO_IMEDIATO = 47;

	public final static Integer ARQUIVO_TABELAS_FATURAMENTO_IMEDIATO = 48;

	public final static Integer GERAR_RESUMO_FATURAMENTO_IMEDIATO = 49;

	public final static Integer GERAR_CARTA_COBRANCA_BANCARIA = 50;

	public final static Integer IDENTIFICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO = 51;

	public final static Integer GERAR_MOVIMENTO_COBRANCA_BANCARIA = 52;

	public final static Integer REGERAR_MOVIMENTO_COBRANCA_BANCARIA = 53;

	public final static Integer NEGOCIACAO_DE_DEBITO = 54;

	public final static Integer ARQUIVO_DADOS_TABELAS_FATURAMENTO_IMEDIATO = 55;

	public final static Integer AJUSTE_FATURAMENTO_SERVICOS_1 = 56;

	public final static Integer AJUSTE_FATURAMENTO_SERVICOS_2 = 57;

	public final static Integer AJUSTE_FATURAMENTO_TARIFAS_3 = 58;

	public final static Integer NOTIFICACAO_CONTRATO_DEMANDA_CONSUMO_A_VENCER = 59;

	public final static Integer SIMULAR_FATURAMENTO_FALHA = 60;

	/** full constructor */
	public EnvioEmail(Integer id, String emailRemetente, String emailReceptor, String tituloMensagem, String corpoMensagem,
						Date ultimaAlteracao, Funcionalidade funcionalidade) {

		this.id = id;
		this.emailRemetente = emailRemetente;
		this.emailReceptor = emailReceptor;
		this.tituloMensagem = tituloMensagem;
		this.corpoMensagem = corpoMensagem;
		this.ultimaAlteracao = ultimaAlteracao;
		this.funcionalidade = funcionalidade;
	}

	public EnvioEmail() {

	}

	public String getCorpoMensagem(){

		return corpoMensagem;
	}

	public void setCorpoMensagem(String corpoMensagem){

		this.corpoMensagem = corpoMensagem;
	}

	public String getEmailReceptor(){

		return emailReceptor;
	}

	public void setEmailReceptor(String emailReceptor){

		this.emailReceptor = emailReceptor;
	}

	public String getEmailRemetente(){

		return emailRemetente;
	}

	public void setEmailRemetente(String emailRemetente){

		this.emailRemetente = emailRemetente;
	}

	public Funcionalidade getFuncionalidade(){

		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade){

		this.funcionalidade = funcionalidade;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getTituloMensagem(){

		return tituloMensagem;
	}

	public void setTituloMensagem(String tituloMensagem){

		this.tituloMensagem = tituloMensagem;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
