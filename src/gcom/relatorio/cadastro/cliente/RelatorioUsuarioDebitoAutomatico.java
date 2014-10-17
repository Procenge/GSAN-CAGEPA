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

package gcom.relatorio.cadastro.cliente;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * [UC0XXX] Relat�rio Usu�rios em D�bito Autom�tico
 * 
 * @author Anderson Italo
 * @date 24/02/2011
 */
public class RelatorioUsuarioDebitoAutomatico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioUsuarioDebitoAutomatico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_USUARIO_DEBITO_AUTOMATICO);
	}

	@Deprecated
	public RelatorioUsuarioDebitoAutomatico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obt�m os par�metros passados
		Integer idBancoInicial = new Integer((Integer) getParametro("idBancoInicial"));
		Integer idBancoFinal = new Integer((Integer) getParametro("idBancoFinal"));
		String nomeBancoInicial = (String) getParametro("nomeBancoInicial");
		String nomeBancoFinal = (String) getParametro("nomeBancoFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		// obt�m as localidades do intervalo informado
		Collection colecaoDadosRelatorio = fachada.pesquisaRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		for(Iterator iterator = colecaoDadosRelatorio.iterator(); iterator.hasNext();){

			Object[] dados = (Object[]) iterator.next();

			RelatorioUsuarioDebitoAutomaticoBean bean = new RelatorioUsuarioDebitoAutomaticoBean();

			// Cabe�alho

			// 4.1. C�digo do Banco
			bean.setCodigoBanco(dados[0].toString());

			// 4.2. Nome do Banco
			bean.setNomeBanco(dados[1].toString());

			// Linha Detalhe

			// 4.3. Ag�ncia
			bean.setAgencia(dados[2].toString());

			// 4.4. Conta Corrente
			bean.setContaCorrente(dados[3].toString());

			// 4.5. Matr�cula Im�vel
			bean.setMatriculaImovel(dados[4].toString());

			// 4.6. Nome Cliente
			bean.setCliente(dados[5].toString());

			// 4.7. Data Op��o
			if(dados[6] != null && !dados[6].toString().equals("")){
				bean.setDataOpcao(Util.formatarData(dados[6].toString().replace("-", "")));
			}

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("nomeBancoInicial", nomeBancoInicial);
		parametros.put("codigoBancoInicial", idBancoInicial.toString());
		parametros.put("nomeBancoFinal", nomeBancoFinal);
		parametros.put("codigoBancoFinal", idBancoFinal.toString());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_USUARIO_DEBITO_AUTOMATICO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_USUARIO_DEBITO_AUTOMATICO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		// obt�m os par�metros passados
		Integer idBancoInicial = (Integer) getParametro("idBancoInicial");
		Integer idBancoFinal = (Integer) getParametro("idBancoFinal");

		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal).intValue();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioUsuarioDebitoAutomatico", this);
	}

}