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

package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.RelatorioMaioresDevedoresHelper;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [] Gerar Relat�rio Maiores Devedores
 * 
 * @author Victon Malcolm
 * @since 13/11/2013
 */
public class RelatorioMaioresDevedores
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioMaioresDevedores(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_MAIORES_DEVEDORES);
	}

	@Deprecated
	public RelatorioMaioresDevedores() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer localidade = Integer.parseInt((String) getParametro("localidade"));
		Integer registros = (Integer) getParametro("registros");
		Integer tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioMaioresDevedoresBean relatorioBean = null;
		Collection colecaoRelatorioMaioresDevedoresHelper = fachada.pesquisarDadosRelatorioMaioresDevedores(localidade,
						registros);
		if(colecaoRelatorioMaioresDevedoresHelper != null && !colecaoRelatorioMaioresDevedoresHelper.isEmpty()){
			Iterator colecaoRelatorioMaioresDevedoresHelperIterator = colecaoRelatorioMaioresDevedoresHelper
							.iterator();
			String cpfCnpj = "";//
			String descricaoLocalidade = "";//
			String setor = "";//
			String endereco = "";//
			String tipo = "";//
			String matricula = "";//
			String nome = "";//
			Integer quantidade = new Integer("0");//
			BigDecimal valorDebito = new BigDecimal("0");
			// la�o para criar a cole��o de par�metros da analise
			while(colecaoRelatorioMaioresDevedoresHelperIterator.hasNext()){
				RelatorioMaioresDevedoresHelper relatorioMaioresDevedoresHelper = (RelatorioMaioresDevedoresHelper) colecaoRelatorioMaioresDevedoresHelperIterator
								.next();
				if(relatorioMaioresDevedoresHelper.getMatricula() != null){
					matricula = relatorioMaioresDevedoresHelper.getMatricula().toString();
				}
				if(relatorioMaioresDevedoresHelper.getLocalidade() != null){
					descricaoLocalidade = relatorioMaioresDevedoresHelper.getLocalidade();
				}
				if(relatorioMaioresDevedoresHelper.getEndereco() != null){
					endereco = relatorioMaioresDevedoresHelper.getEndereco();
				}
				if(relatorioMaioresDevedoresHelper.getCpfCnpj() != null){
					cpfCnpj = relatorioMaioresDevedoresHelper.getCpfCnpj();
				}
				if(relatorioMaioresDevedoresHelper.getSetor() != null){
					setor = relatorioMaioresDevedoresHelper.getSetor().toString();
				}
				if(relatorioMaioresDevedoresHelper.getTipo() != null){
					tipo = relatorioMaioresDevedoresHelper.getTipo();
				}
				if(relatorioMaioresDevedoresHelper.getNome() != null){
					nome = relatorioMaioresDevedoresHelper.getNome();
				}
				if(relatorioMaioresDevedoresHelper.getQuantidade() != null){
					quantidade = relatorioMaioresDevedoresHelper.getQuantidade().intValue();
				}
				if(relatorioMaioresDevedoresHelper.getValorDebito() != null){
					valorDebito = relatorioMaioresDevedoresHelper.getValorDebito();
				}
				relatorioBean = new RelatorioMaioresDevedoresBean(descricaoLocalidade, setor, matricula, nome, cpfCnpj, tipo, endereco,
								quantidade, valorDebito);
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________
		// Par�metros do relat�rio
		Map parametros = new HashMap();
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MAIORES_DEVEDORES, parametros, ds,
						tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MAIORES_DEVEDORES, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------tava
		// retorna o relat�rio gerado

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer localidade = Integer.parseInt((String) getParametro("localidade"));
		Integer registros = (Integer) getParametro("registros");
		Collection colecaoRelatorioMaioresDevedoresHelper = Fachada.getInstancia().pesquisarDadosRelatorioMaioresDevedores(
						localidade, registros);

		return colecaoRelatorioMaioresDevedoresHelper.size();
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioMaioresDevedores", this);
	}
}