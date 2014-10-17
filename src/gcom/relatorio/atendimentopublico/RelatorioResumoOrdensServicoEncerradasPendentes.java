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
 * Ivan S�rgio Virginio da Silva J�nior
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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.bean.RelacaoOrdensServicoEncerradasPendentesHelper;
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
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de regitro atendimento manter de
 * �gua
 * 
 * @author Ivan S�rgio
 * @created 12/12/2007
 */
public class RelatorioResumoOrdensServicoEncerradasPendentes
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoOrdensServicoEncerradasPendentes(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS_PENDENTES);
	}

	@Deprecated
	public RelatorioResumoOrdensServicoEncerradasPendentes() {

		super(null, "");
	}

	public static final Integer TIPO_RELATORIO_ANALITICO = 1;

	public static final Integer TIPO_RELATORIO_SINTETICO = 2;

	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String tipoOrdem = (String) getParametro("tipoOrdem");
		String situacaoOrdemServico = (String) getParametro("situacaoOrdemServico");
		String firma = (String) getParametro("firma");
		String nomeFirma = (String) getParametro("nomeFirma");
		String idLocalidade = (String) getParametro("idLocalidade");
		String nomeLocalidade = (String) getParametro("nomeLocalidade");
		String dataEncerramentoInicial = (String) getParametro("dataEncerramentoInicial");
		String dataEncerramentoFinal = (String) getParametro("dataEncerramentoFinal");
		String tipoRelatorio = (String) getParametro("tipoRelatorioAcompanhamento");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioResumoOrdensServicoEncerradasPendentesBean relatorioBean = null;

		Collection colecaoDadosHelper = fachada.pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(firma, tipoOrdem,
						situacaoOrdemServico, idLocalidade, dataEncerramentoInicial, dataEncerramentoFinal, tipoRelatorio);
		Long quantidadeMotivos = new Long("0");
		if(colecaoDadosHelper != null && !colecaoDadosHelper.isEmpty()){
			Iterator iColecaoDados = colecaoDadosHelper.iterator();

			if(situacaoOrdemServico.equalsIgnoreCase("ENCERRADAS")){

				while(iColecaoDados.hasNext()){
					// dados = (Object[]) iColecaoDados.next();
					RelacaoOrdensServicoEncerradasPendentesHelper helper = (RelacaoOrdensServicoEncerradasPendentesHelper) iColecaoDados
									.next();

					relatorioBean = new RelatorioResumoOrdensServicoEncerradasPendentesBean();

					// Localidade
					relatorioBean.setIdLocalidade(idLocalidade);
					// Situacao selecionada
					relatorioBean.setSituacao(situacaoOrdemServico);
					// Tipo do Servico
					relatorioBean.setTipoServico(tipoOrdem);
					// Periodo encerramento
					if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
									&& !dataEncerramentoFinal.equals("")){
						relatorioBean.setPeriodoEncerramento(dataEncerramentoInicial + " � " + dataEncerramentoFinal);
					}else{
						relatorioBean.setPeriodoEncerramento("");
					}
					// Nome Localidade
					relatorioBean.setNomeLocalidade(nomeLocalidade);
					// Firma
					relatorioBean.setFirma(firma);
					// Nome Firma
					relatorioBean.setNomeFirma(nomeFirma);

					// Motivo Encerramento
					if(helper.getMotivoEncerramento() != null){
						relatorioBean.setMotivoEncerramento(helper.getMotivoEncerramento());
						// Total de Ordens
						relatorioBean.setTotalOrdensServico("" + helper.getQuantidadeOS());
						quantidadeMotivos = quantidadeMotivos + helper.getQuantidadeOS();

					}else{
						relatorioBean.setMotivoEncerramento("");
						relatorioBean.setTotalOrdensServico("");
					}

					// adiciona o bean a cole��o
					relatorioBeans.add(relatorioBean);
				}

			}else if(situacaoOrdemServico.equalsIgnoreCase("PENDENTES")){
				relatorioBean = new RelatorioResumoOrdensServicoEncerradasPendentesBean();

				// Localidade
				relatorioBean.setIdLocalidade(idLocalidade);
				// Situacao selecionada
				relatorioBean.setSituacao(situacaoOrdemServico);
				// Tipo do Servico
				relatorioBean.setTipoServico(tipoOrdem);
				// Periodo encerramento
				if(dataEncerramentoInicial != null && !dataEncerramentoInicial.equals("") && dataEncerramentoFinal != null
								&& !dataEncerramentoFinal.equals("")){
					relatorioBean.setPeriodoEncerramento(dataEncerramentoInicial + " � " + dataEncerramentoFinal);
				}else{
					relatorioBean.setPeriodoEncerramento("");
				}
				// Nome Localidade
				relatorioBean.setNomeLocalidade(nomeLocalidade);
				// Firma
				relatorioBean.setFirma(firma);
				// Nome Firma
				relatorioBean.setNomeFirma(nomeFirma);
				// Total de Ordens
				relatorioBean.setTotalOrdensServico("");

				quantidadeMotivos = Long.valueOf(colecaoDadosHelper.size());
				// Motivo Encerramento
				relatorioBean.setMotivoEncerramento("");

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

			// Par�metros do relat�rio
			Map parametros = new HashMap();

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			parametros.put("imagem", sistemaParametro.getImagemRelatorio());

			parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

			parametros.put("quantidadeMotivos", quantidadeMotivos + "");

			// cria uma inst�ncia do dataSource do relat�rio
			RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS_PENDENTES, parametros, ds,
							tipoFormatoRelatorio);

			// Grava o relat�rio no sistema
			try{
				persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_OS_ENCERRADAS_PENDENTES, idFuncionalidadeIniciada, null);
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
			}
			// ------------------------------------
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoOrdensServicoEncerradasPendentes", this);
	}
}