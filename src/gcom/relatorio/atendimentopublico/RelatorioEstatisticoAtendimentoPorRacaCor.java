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

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.FiltroRaca;
import gcom.cadastro.cliente.Raca;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * @author Hiroshi Gon�alves
 * @created 17/02/2014
 */
public class RelatorioEstatisticoAtendimentoPorRacaCor
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEstatisticoAtendimentoPorRacaCor(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ESTATISTICO_ATENDIMENTO_POR_RACA_COR);
	}

	@Deprecated
	public RelatorioEstatisticoAtendimentoPorRacaCor() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obt�m os par�metros passados
		GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form = (GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm) getParametro("gerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		// Collection relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// CONSULTA DADOS DO RELATORIO
		List<RelatorioEstatisticoAtendimentoPorRacaCorBean> relatorioBeans = fachada
						.pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(form);

		if(relatorioBeans == null || relatorioBeans.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		String stringFiltroUnidadeAtendimento = "";
		String stringFiltroTipoSolicitacao = "";
		String stringFiltroEspecificacao = "";
		String stringFiltroRacaCor = "";
		String stringFiltroRegional = "";
		String stringFiltroLocalidade = "";
		Long qtdTotalAtendimento = 0L;

		for(RelatorioEstatisticoAtendimentoPorRacaCorBean objeto : relatorioBeans){
			qtdTotalAtendimento += objeto.getQtd();
		}

		// Recupera descricao Unidade de Atendimento
		if(form.getDsUnidadeAtendimento() != null && !form.getDsUnidadeAtendimento().equals("")){
			stringFiltroUnidadeAtendimento = form.getDsUnidadeAtendimento();
		}else if(form.getUnidadeAtendimento() != null && !form.getUnidadeAtendimento().equals("")){
			UnidadeOrganizacional unidadeAtendimento = (UnidadeOrganizacional) fachada.pesquisar(
							Integer.parseInt(form.getUnidadeAtendimento()), UnidadeOrganizacional.class);

			if(unidadeAtendimento != null){
				stringFiltroUnidadeAtendimento = unidadeAtendimento.getDescricao();
			}
		}

		// Recupera descri��o Solicita��o Tipo
		if(form.getTipoSolicitacao() != null && (form.getTipoSolicitacao().length > 0)){
			FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo(FiltroSolicitacaoTipo.DESCRICAO);

			Collection<SolicitacaoTipo> colRetorno = fachada.pesquisar(Arrays.asList(form.getTipoSolicitacao()), filtroSolicitacaoTipo,
							SolicitacaoTipo.class.getName());
			if(!colRetorno.isEmpty()){
				for(SolicitacaoTipo objeto : colRetorno){
					stringFiltroTipoSolicitacao += ", " + objeto.getDescricao();
				}

				// Retira a v�rgula do come�o
				stringFiltroTipoSolicitacao = stringFiltroTipoSolicitacao.substring(2);
			}
		}

		// Recupera Especificacao
		if(form.getEspecificacao() != null && (form.getEspecificacao().length > 0)){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao(
							FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

			Collection<SolicitacaoTipoEspecificacao> colRetorno = fachada.pesquisar(Arrays.asList(form.getEspecificacao()),
							filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());
			if(!colRetorno.isEmpty()){
				for(SolicitacaoTipoEspecificacao objeto : colRetorno){
					stringFiltroEspecificacao += ", " + objeto.getDescricao();
				}

				// Retira a v�rgula do come�o
				stringFiltroEspecificacao = stringFiltroEspecificacao.substring(2);
			}
		}

		// Recupera descri��o Ra�a/Cor
		if(form.getRacaCorSelecionadas() != null && (form.getRacaCorSelecionadas().length > 0)){
			FiltroRaca filtroRaca = new FiltroRaca(FiltroRaca.DESCRICAO);

			Collection<Raca> colRetorno = fachada.pesquisar(Arrays.asList(form.getRacaCorSelecionadas()), filtroRaca, Raca.class.getName());
			if(!colRetorno.isEmpty()){
				for(Raca objeto : colRetorno){
					stringFiltroRacaCor += ", " + objeto.getDescricao();
				}

				// Retira a v�rgula do come�o
				stringFiltroRacaCor = stringFiltroRacaCor.substring(2);
			}
		}

		// Recupera a descricao da Gerencia Regional
		if(!Util.isVazioOuBranco(form.getGerenciaRegional()) && !form.getGerenciaRegional().equals(ConstantesSistema.VALOR_NAO_INFORMADO)){

			GerenciaRegional gerenciaRegional = (GerenciaRegional) fachada.pesquisar(Integer.parseInt(form.getGerenciaRegional()),
							GerenciaRegional.class);

			if(gerenciaRegional != null){
				stringFiltroRegional = gerenciaRegional.getNome();
			}
		}
		// Recupera descricao Localidade
		if(!Util.isVazioOuBranco(form.getDsLocalidade())){
			stringFiltroLocalidade = form.getDsLocalidade();

		}else if(!Util.isVazioOuBranco(form.getLocalidade())){
			Localidade localidade = (Localidade) fachada.pesquisar(Integer.parseInt(form.getLocalidade()), Localidade.class);

			if(localidade != null){
				stringFiltroLocalidade = localidade.getDescricao();
			}
		}

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("FILTRO_PERIODO_ATENDIMENTO_INICIAL", form.getPeriodoAtendimentoInicial());
		parametros.put("FILTRO_PERIODO_ATENDIMENTO_FINAL", form.getPeriodoAtendimentoFinal());
		parametros.put("FILTR0_UNIDADE_ATENDIMENTO", stringFiltroUnidadeAtendimento);
		parametros.put("FILTRO_TIPO_SOLICITACAO", stringFiltroTipoSolicitacao);
		parametros.put("FILTRO_ESPECIFICACAO", stringFiltroEspecificacao);
		parametros.put("FILTRO_RACA_COR", stringFiltroRacaCor);
		parametros.put("FILTRO_REGIONAL", stringFiltroRegional);
		parametros.put("FILTRO_LOCALIDADE", stringFiltroLocalidade);
		parametros.put("QTD_TOTAL_ATENDIMENTO", qtdTotalAtendimento);

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ESTATISTICO_ATENDIMENTO_POR_RACA_COR, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ESTATISTICO_ATENDIMENTO_POR_RACA_COR, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int calcularTotalRegistrosRelatorio(){

		// obt�m os par�metros passados
		GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form = (GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm) getParametro("gerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm");
		Collection resultado = null; // Fachada.getInstancia().pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(form);

		if(resultado != null){
			return resultado.size();
		}else{
			return 0;
		}
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEstatisticoAtendimentoPorRacaCor", this);
	}
}