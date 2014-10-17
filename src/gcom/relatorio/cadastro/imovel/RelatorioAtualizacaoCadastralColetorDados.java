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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC3113] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 08/10/2013
 */
public class RelatorioAtualizacaoCadastralColetorDados
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAtualizacaoCadastralColetorDados(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS);
	}

	@Deprecated
	public RelatorioAtualizacaoCadastralColetorDados() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		Integer referenciaInicial = (Integer) getParametro("referenciaInicial");
		Integer referenciaFinal = (Integer) getParametro("referenciaFinal");
		Integer matricula = (Integer) getParametro("matricula");
		Integer localidade = (Integer) getParametro("localidade");
		Integer setorComercial = (Integer) getParametro("setorComercial");
		Integer rota = (Integer) getParametro("rota");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();
		
		RelatorioAtualizacaoCadastralColetorDadosBean relatorioBean = null;
		Collection colecaoAtualizacaoCadastralColetorDados = fachada.pesquisarAtualizacaoCadastralColetorDados(
						referenciaInicial, referenciaFinal, matricula, localidade, setorComercial, rota, true);
		if(colecaoAtualizacaoCadastralColetorDados != null && !colecaoAtualizacaoCadastralColetorDados.isEmpty()){
		 Iterator colecaoAtualizacaoCadastralColetorDadosIterator = colecaoAtualizacaoCadastralColetorDados.iterator();
			String referencia = "";
			String matriculaImovel = "";
			String inscricaoImovel = "";
			String leiturista = "";
			String dataHora = "";
			String numeroImovel = "";
			String complemento = "";
			String numeroHidrometro = "";
			String qntEconomiaResidencial = "";
			String qntEconomiaComercial = "";
			String qntEconomiaIndustrial = "";
			String qntEconomiaPublica = "";
			String numeroImovelAlteracao = "";
			String complementoAlteracao = "";
			String numeroHidrometroAlteracao = "";
			String qntEconomiaResidencialAlteracao = "";
			String qntEconomiaComercialAlteracao = "";
			String qntEconomiaIndustrialAlteracao = "";
			String qntEconomiaPublicaAlteracao = "";
			// la�o para criar a cole��o de par�metros da analise
			while(colecaoAtualizacaoCadastralColetorDadosIterator.hasNext()){
				Object[] atualizacaoCadastralColetorDados = (Object[]) colecaoAtualizacaoCadastralColetorDadosIterator.next();
				if(atualizacaoCadastralColetorDados[3] != null){
					referencia = Util.formatarAnoMesParaMesAno(((BigDecimal) atualizacaoCadastralColetorDados[3]).intValue());
				}
				if(atualizacaoCadastralColetorDados[1] != null){
					matriculaImovel = atualizacaoCadastralColetorDados[1].toString();
				}
				if(atualizacaoCadastralColetorDados[2] != null){
					inscricaoImovel = atualizacaoCadastralColetorDados[2].toString();
				}
				if(atualizacaoCadastralColetorDados[4] != null){
					leiturista = atualizacaoCadastralColetorDados[4].toString();
				}
				if(atualizacaoCadastralColetorDados[5] != null){
					dataHora = Util.formatarDataComHora((Date) atualizacaoCadastralColetorDados[5]);
				}
				if(atualizacaoCadastralColetorDados[6] != null){
					numeroImovel = atualizacaoCadastralColetorDados[6].toString();
				}
				if(atualizacaoCadastralColetorDados[7] != null){
					numeroImovelAlteracao = atualizacaoCadastralColetorDados[7].toString();
				}
				if(atualizacaoCadastralColetorDados[8] != null){
					complemento = atualizacaoCadastralColetorDados[8].toString();
				}
				if(atualizacaoCadastralColetorDados[9] != null){
					complementoAlteracao = atualizacaoCadastralColetorDados[9].toString();
				}
				if(atualizacaoCadastralColetorDados[10] != null){
					numeroHidrometro = atualizacaoCadastralColetorDados[10].toString();
				}
				if(atualizacaoCadastralColetorDados[11] != null){
					numeroHidrometroAlteracao = atualizacaoCadastralColetorDados[11].toString();
				}
				if(atualizacaoCadastralColetorDados[12] != null){
					qntEconomiaResidencial = atualizacaoCadastralColetorDados[12].toString();
				}
				if(atualizacaoCadastralColetorDados[13] != null){
					qntEconomiaResidencialAlteracao = atualizacaoCadastralColetorDados[13].toString();
				}
				if(atualizacaoCadastralColetorDados[14] != null){
					qntEconomiaComercial = atualizacaoCadastralColetorDados[14].toString();
				}
				if(atualizacaoCadastralColetorDados[15] != null){
					qntEconomiaComercialAlteracao = atualizacaoCadastralColetorDados[15].toString();
				}
				if(atualizacaoCadastralColetorDados[16] != null){
					qntEconomiaIndustrial = atualizacaoCadastralColetorDados[16].toString();
				}
				if(atualizacaoCadastralColetorDados[17] != null){
					qntEconomiaIndustrialAlteracao = atualizacaoCadastralColetorDados[17].toString();
				}
				if(atualizacaoCadastralColetorDados[18] != null){
					qntEconomiaPublica = atualizacaoCadastralColetorDados[18].toString();
				}
				if(atualizacaoCadastralColetorDados[19] != null){
					qntEconomiaPublicaAlteracao = atualizacaoCadastralColetorDados[19].toString();
				}

				relatorioBean = new RelatorioAtualizacaoCadastralColetorDadosBean(referencia, matriculaImovel, inscricaoImovel, leiturista,
								dataHora, numeroImovel, complemento, numeroHidrometro, qntEconomiaResidencial, qntEconomiaComercial,
								qntEconomiaIndustrial, qntEconomiaPublica, numeroImovelAlteracao, complementoAlteracao,
								numeroHidrometroAlteracao, qntEconomiaResidencialAlteracao, qntEconomiaComercialAlteracao,
								qntEconomiaIndustrialAlteracao, qntEconomiaPublicaAlteracao);
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
			}
		 // __________________________________________________________________
		 // Par�metros do relat�rio
		 Map parametros = new HashMap();
		 // adiciona os par�metros do relat�rio
		 // adiciona o laudo da an�lise
		 SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		 parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		 parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("referenciaInicial", Util.formatarAnoMesParaMesAno(referenciaInicial));
		parametros.put("referenciaFinal", Util.formatarAnoMesParaMesAno(referenciaFinal));
		parametros.put("matricula", matricula != null ? matricula.toString() : "");
		parametros.put("localidade", localidade != null ? localidade.toString() : "");
		parametros.put("setorComercial", setorComercial != null ? setorComercial.toString() : "");
		parametros.put("rota", rota != null ? rota.toString() : "");
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// -----------------------------------
		// retorna o relat�rio gerado

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer referenciaInicial = (Integer) getParametro("referenciaInicial");
		Integer referenciaFinal = (Integer) getParametro("referenciaFinal");
		Integer matricula = (Integer) getParametro("matricula");
		Integer localidade = (Integer) getParametro("localidade");
		Integer setorComercial = (Integer) getParametro("setorComercial");
		Integer rota = (Integer) getParametro("rota");
		Collection colecaoAtualizacaoCadastralColetorDados = Fachada.getInstancia()
						.pesquisarAtualizacaoCadastralColetorDados(referenciaInicial, referenciaFinal, matricula, localidade,
										setorComercial, rota, true);
		return colecaoAtualizacaoCadastralColetorDados.size();
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioFaturamentoConsumoDiretoIndiretoEstadual", this);
	}
}