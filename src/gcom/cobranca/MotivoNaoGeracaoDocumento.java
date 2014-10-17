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

package gcom.cobranca;

import gcom.util.ConstantesSistema.ChaveMotivoNaoGeracaoDocumento;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import br.com.procenge.util.Constantes;

public class MotivoNaoGeracaoDocumento
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ARQUIVO_PROPRIEDADES_MOTIVO_NAO_GERACAO_DOCUMENTO = "motivoNaoGeracaoDocumento.properties";

	public static Integer IMOVEL_EXCLUIDO;

	public static Integer CLIENTE_SEM_CPF_CNPJ;

	public static Integer IMOVEL_SEM_CEP;

	public static Integer IMOVEL_SEM_DOC_PRECED_VALIDO;

	public static Integer IMOVEL_SEM_OS_EXECUTADA;

	public static Integer IMOVEL_COM_NEGOCIACAO_DEB_PENDENTE;

	public static Integer IMOVEL_COM_NEGATIVACAO_VALIDA;

	public static Integer IMOVEL_COM_DOCUMENTO_TIPO_VALIDO;

	public static Integer IMOVEL_NAO_SATISFAZ_RD;

	public static Integer EXCEDEU_QTD_MAX_DOCUMENTO;

	public static Integer SIT_LIGACAO_AGUA;

	public static Integer SIT_LIGACAO_ESGOTO;

	public static Integer IMOVEL_SIT_ESPECIAL_COBRANCA;

	public static Integer IMOVEL_SIT_COBRANCA_NAO_PERMITIDA;

	public static Integer PERFIL_IMOVEL_NAO_PERMITIDO;

	public static Integer IMOVEL_NAO_SATISFAZ_CRITERIO;

	public static Integer IMOVEL_SEM_DEBITO;

	public static Integer IMOVEL_COM_DOCUMENTO_SUCESSOR_VALIDO;

	public static Integer IMOVEL_EM_COBRANCA_BANCARIA;

	public static Integer IMOVEL_COM_ITEM_REMUNERAVEL;

	public static Integer IMOVEL_COM_DEBITO_ANTIGO_PARA_ACAO;

	public static Integer IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES;

	public static Integer IMOVEL_COM_DEBITO_SOMENTE_CONTA_ANTIGA;

	public static Integer IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES_ANTIGA;

	public static Integer CLIENTE_SEM_NOME_CONTA;

	public static Integer CLIENTE_SEM_FONE;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + " " + getDescricao();
	}

	static{
		inicializarPropriedades();
	}

	/**
	 * M�todo respons�vel por inicializar os ids de d�bito tipo a partir do arquivo
	 * debitoTipo.properties
	 */
	private static void inicializarPropriedades(){

		try{
			Properties propriedades = new Properties();
			InputStream stream = Constantes.class.getClassLoader().getResourceAsStream(ARQUIVO_PROPRIEDADES_MOTIVO_NAO_GERACAO_DOCUMENTO);
			propriedades.load(stream);

			IMOVEL_EXCLUIDO = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.IMOVEL_EXCLUIDO.toString()).trim());
			CLIENTE_SEM_CPF_CNPJ = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.CLIENTE_SEM_CPF_CNPJ.toString())
							.trim());
			IMOVEL_SEM_CEP = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.IMOVEL_SEM_CEP.toString()).trim());
			IMOVEL_SEM_DOC_PRECED_VALIDO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_SEM_DOC_PRECED_VALIDO.toString()).trim());
			IMOVEL_SEM_OS_EXECUTADA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_SEM_OS_EXECUTADA.toString()).trim());
			IMOVEL_COM_NEGOCIACAO_DEB_PENDENTE = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_NEGOCIACAO_DEB_PENDENTE.toString()).trim());
			IMOVEL_COM_NEGATIVACAO_VALIDA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_NEGATIVACAO_VALIDA.toString()).trim());
			IMOVEL_COM_DOCUMENTO_TIPO_VALIDO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DOCUMENTO_TIPO_VALIDO.toString()).trim());
			IMOVEL_NAO_SATISFAZ_RD = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_NAO_SATISFAZ_RD.toString()).trim());
			EXCEDEU_QTD_MAX_DOCUMENTO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.EXCEDEU_QTD_MAX_DOCUMENTO.toString()).trim());
			SIT_LIGACAO_AGUA = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.SIT_LIGACAO_AGUA.toString()).trim());
			SIT_LIGACAO_ESGOTO = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.SIT_LIGACAO_ESGOTO.toString())
							.trim());
			IMOVEL_SIT_ESPECIAL_COBRANCA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_SIT_ESPECIAL_COBRANCA.toString()).trim());
			IMOVEL_SIT_COBRANCA_NAO_PERMITIDA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_SIT_COBRANCA_NAO_PERMITIDA.toString()).trim());
			PERFIL_IMOVEL_NAO_PERMITIDO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.PERFIL_IMOVEL_NAO_PERMITIDO.toString()).trim());

			IMOVEL_NAO_SATISFAZ_CRITERIO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_NAO_SATISFAZ_CRITERIO.toString()).trim());

			IMOVEL_SEM_DEBITO = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.IMOVEL_SEM_DEBITO.toString())
							.trim());

			IMOVEL_COM_DOCUMENTO_SUCESSOR_VALIDO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DOCUMENTO_SUCESSOR_VALIDO.toString()).trim());

			IMOVEL_EM_COBRANCA_BANCARIA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_EM_COBRANCA_BANCARIA.toString()).trim());

			IMOVEL_COM_ITEM_REMUNERAVEL = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_ITEM_REMUNERAVEL.toString()).trim());

			IMOVEL_COM_DEBITO_ANTIGO_PARA_ACAO = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DEBITO_ANTIGO_PARA_ACAO.toString()).trim());

			IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES.toString()).trim());

			IMOVEL_COM_DEBITO_SOMENTE_CONTA_ANTIGA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DEBITO_SOMENTE_CONTA_ANTIGA.toString()).trim());

			IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES_ANTIGA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.IMOVEL_COM_DEBITO_SOMENTE_CONTA_MES_ANTIGA.toString()).trim());

			CLIENTE_SEM_NOME_CONTA = Integer.valueOf(propriedades.getProperty(
							ChaveMotivoNaoGeracaoDocumento.CLIENTE_SEM_NOME_CONTA.toString()).trim());

			CLIENTE_SEM_FONE = Integer.valueOf(propriedades.getProperty(ChaveMotivoNaoGeracaoDocumento.CLIENTE_SEM_FONE.toString()).trim());

			stream.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
