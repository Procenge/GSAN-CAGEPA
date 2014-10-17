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

package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action utilizado
 * [UC0631] Processar Requisi��es do Dipositivo M�vel.
 * 
 * @author Thiago Nascimento
 * @since 15/08/2007
 */
public class ProcessarRequisicaoDipositivoMovelAction
				extends GcomAction {

	/**
	 * Constantes de Classe.
	 */
	private static final int RESPOSTA_ERRO = 1;

	private static final int ARQUIVO_TEXTO_PARA_ENVIO = 2;

	private static final int ARQUIVO_TEXTO_ENVIADO = 3;

	private static final int ARQUIVO_TEXTO_FINALIZADO = 4;

	private static final int PACOTE_BAIXAR_ARQUIVO = 0;

	private static final int ATUALIZAR_MOVIMENTO = 1;

	private static final int FINALIZAR_LEITURA = 2;

	/**
	 * M�todo Execute do Action
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse response){

		try{
			InputStream in = request.getInputStream();
			DataInputStream din = new DataInputStream(in);

			int pacote = din.readByte();
			switch(pacote){
				case PACOTE_BAIXAR_ARQUIVO:
					// Baixar Arquivo
					this.baixarArquivo(din, response);
					break;
				case ATUALIZAR_MOVIMENTO:
					// Atualizar Movimento
					this.atualizarMovimentacao(din);
					break;
				case FINALIZAR_LEITURA:
					// Finalizar Movimento
					this.finalizarMovimentacao(din);
					break;
			}
			din.close();

		}catch(IOException e){
			System.out.println("Erro: " + e.getMessage());
		}
		return null;

	}

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * M�todo que baixa o arquivo de entrada do servidor.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param data
	 * @param response
	 * @throws IOException
	 */
	public void baixarArquivo(DataInputStream data, HttpServletResponse response) throws IOException{

		Fachada fachada = Fachada.getInstancia();
		long imei = data.readLong();
		byte[] arq = fachada.baixarArquivoTextoParaLeitura(imei);
		if(arq != null){
			response.setContentLength(arq.length);
			OutputStream out = response.getOutputStream();
			out.write(arq);
			out.flush();
			out.close();
			fachada.atualizarArquivoTextoEnviado(imei, ARQUIVO_TEXTO_PARA_ENVIO, ARQUIVO_TEXTO_ENVIADO);
		}else{
			response.setContentLength(RESPOSTA_ERRO);
			OutputStream out = response.getOutputStream();
			out.write(RESPOSTA_ERRO);
			out.flush();
			out.close();
		}
	}

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * M�todo que atualiza as movimenta��es dos leituristas.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @throws IOException
	 */
	public Long atualizarMovimentacao(DataInputStream data) throws IOException{

		Fachada fachada = Fachada.getInstancia();
		Collection<DadosMovimentacao> dados = new ArrayList<DadosMovimentacao>();
		Long imei = new Long(data.readLong());
		int imovel = data.read();
		while(imovel != -1){
			byte[] b = new byte[4];
			b[0] = (byte) imovel;
			b[1] = (byte) data.read();
			b[2] = (byte) data.read();
			b[3] = (byte) data.read();
			dados.add(new DadosMovimentacao(new Integer(this.convertArrayByteToInt(b)), new Integer(data.readInt()), new Integer(data
							.readByte()), new Date(data.readLong()), imei, new Byte(data.readByte())));
			imovel = data.read();
		}
		fachada.atualiarRoteiro(dados);
		return imei;
	}

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * M�todo que finaliza as movimenta��es.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @throws IOException
	 */
	public void finalizarMovimentacao(DataInputStream data) throws IOException{

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizarArquivoTextoEnviado(this.atualizarMovimentacao(data), ARQUIVO_TEXTO_ENVIADO, ARQUIVO_TEXTO_FINALIZADO);
	}

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * M�todo auxilar para converter um array de byte em um inteiro.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param b
	 * @return int
	 */
	private int convertArrayByteToInt(byte[] b){

		int i = 0;
		i += unsignedByteToInt(b[0]) << 24;
		i += unsignedByteToInt(b[1]) << 16;
		i += unsignedByteToInt(b[2]) << 8;
		i += unsignedByteToInt(b[3]) << 0;
		return i;

	}

	/**
	 * [UC0631] Processar Requisi��es do Dispositivo M�vel.
	 * M�todo auxiliar para converter byte em int.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param b
	 * @return
	 */
	private int unsignedByteToInt(byte b){

		return (int) b & 0xFF;
	}

}