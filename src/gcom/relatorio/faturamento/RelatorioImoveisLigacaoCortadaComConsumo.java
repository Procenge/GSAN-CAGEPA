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
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * [UC]
 * 
 * @author Vivianne Sousa
 * @date 27/01/2007
 */

public class RelatorioImoveisLigacaoCortadaComConsumo
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioImoveisLigacaoCortadaComConsumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_LIGACAO_CORTADA_COM_CONSUMO);
	}

	private Collection<RelatorioImoveisLigacaoCortadaComConsumoBean> inicializarBeanRelatorio(
					Collection colecaoContasEmitidasRelatorioHelper, String mesAnoReferencia, String idGrupoFaturamento){

		Collection<RelatorioImoveisLigacaoCortadaComConsumoBean> retorno = new ArrayList<RelatorioImoveisLigacaoCortadaComConsumoBean>();

		Iterator iter = colecaoContasEmitidasRelatorioHelper.iterator();
		
		String leituraCorte;
		String leituraAnterior;
		String leituraAtual;
		while(iter.hasNext()){

			RelatorioImoveisLigacaoCortadaComConsumoHelper helper = (RelatorioImoveisLigacaoCortadaComConsumoHelper) iter.next();
			
			// Leitura Corte
			if(helper.getLeituraCorteLaguId() != null){
				leituraCorte = helper.getLeituraCorteLaguId();
			} else {
				leituraCorte = helper.getLeituraCorteImovId();
			}
			
			// Leitura Anterior
			if(helper.getLeituraAnteriorLaguId() != null){
				leituraAnterior = helper.getLeituraAnteriorLaguId();
			} else {
				leituraAnterior = helper.getLeituraAnteriorImovId();
			}
			
			// Leitura Atual
			if(helper.getLeituraAtualLaguId() != null){
				leituraAtual = helper.getLeituraAtualLaguId();
			} else {
				leituraAtual = helper.getLeituraAtualImovId();
			}

			RelatorioImoveisLigacaoCortadaComConsumoBean bean = new RelatorioImoveisLigacaoCortadaComConsumoBean(helper.getLocalidade(), helper.getSetorComercial(), helper.getMatriculaImovel(), 
							helper.getNomeCliente(), helper.getQuadra(),
							helper.getLote(), helper.getDtCorte(), leituraCorte, helper.getConsumoAnterior(),
							helper.getRefConsumoAnterior(), leituraAnterior, leituraAtual, helper.getConsumoMes());

			retorno.add(bean);

		}

		return retorno;
	}

	/**
	 * M�todo que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		Integer mesAnoInteger = (Integer) getParametro("mesAnoInteger");
		Integer idGrupoFaturamento = (Integer) getParametro("idGrupoFaturamento");
		String mesAno = (String) getParametro("mesAno");

		// consultarContasEmitidasRelatorio(
		Collection colHelper = fachada.consultarImoveisLigacaoCortadaComConsumo(mesAnoInteger.intValue(),
						idGrupoFaturamento);

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// Par�metros do relat�rio
		Map<String, String> parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("imagemConta", sistemaParametro.getImagemConta());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("P_AM_REF", mesAno);
		parametros.put("P_GP_FATURAMENTO", idGrupoFaturamento.toString());

		Collection dadosRelatorio = colHelper;

		Collection<RelatorioImoveisLigacaoCortadaComConsumoBean> colecaoBean = this.inicializarBeanRelatorio(dadosRelatorio,
						mesAnoInteger.toString(), idGrupoFaturamento.toString());

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_LIGACAO_CORTADA_COM_CONSUMO, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_IMOVEIS_LIGACAO_CORTADA_COM_CONSUMO, idFuncionalidadeIniciada, null);
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

		Integer mesAnoInteger = (Integer) getParametro("mesAnoInteger");
		Integer idGrupoFaturamento = (Integer) getParametro("idGrupoFaturamento");
		Collection idEsferaPoder = (Collection) getParametro("colecaoIdEsferaPoder");

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioImoveisLigacaoCortadaComConsumo", this);
	}

}
