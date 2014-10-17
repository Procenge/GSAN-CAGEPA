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

package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * [UC0XXX] Gerar Relat�rio Resumo de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
 * Obter dados para gerar Relat�rio Resumo de Ordem de Servi�o Encerradas Dentro e Fora do Prazo
 * 
 * @author Victon Santos
 * @date 23/05/2013
 */
public class RelatorioOrdemServicoEncerradaDentroForaPrazo
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioOrdemServicoEncerradaDentroForaPrazo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ORDENS_SERVICO_ENCERRADAS_DENTRO_FORA_PRAZO);
	}

	@Deprecated
	public RelatorioOrdemServicoEncerradaDentroForaPrazo() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// obt�m os par�metros passados
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String origemServico = (String) getParametro("origemServico");//
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");//
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");//
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");//
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");//
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");//
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");//
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");//
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");//
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");//--
		String idLocalidade = (String) getParametro("idLocalidade");//
		String tipoOrdenacao = (String) getParametro("tipoOrdenacao");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obt�m a cole��o com dados do detalhe do relat�rio
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico, idsServicosTipos,
						idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
						periodoEncerramentoInicial, periodoEncerramentoFinal, idLocalidade);
		// pesquisaRelatorioResumoOsEncerradas


		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioOrdemServicoEncerradasDentroForaPrazoBean bean = new RelatorioOrdemServicoEncerradasDentroForaPrazoBean();
			if(dados[0] != null)
			bean.setNumeroOS(dados[0].toString());
			if(dados[1] != null)
			bean.setNumeroRA(dados[1].toString());
			if(dados[2] != null)
			bean.setMatricula(dados[2].toString());
			if(dados[2] != null)
			bean.setEndereco(Fachada.getInstancia().obterEnderecoAbreviadoImovel(Integer.parseInt(dados[2].toString())));
			if(dados[3] != null)
 bean.setSolicitacao(dados[3].toString());
			if(dados[4] != null)
			bean.setTipoEspecificacao(dados[4].toString());
			if(dados[5] != null)
				bean.setDtCriacao(Util.formatarData((Date) dados[5]));
			if(dados[6] != null)
				bean.setDtEncerramento(Util.formatarData((Date) dados[6]));
			if(dados[7] != null) bean.setUnEncerramento(dados[7].toString());
			if(dados[8] != null) bean.setUnAtual(dados[8].toString());
			if(dados[9] != null && dados[11] != null){
				bean.setPrazoRA(Util.formatarData((Date) dados[9]));
				int atrazo = Util.calcularDiferencaEntreDatas((Date) dados[9], (Date) dados[11], ConstantesSistema.DIFERENCA_HORAS)
								.intValue();
				if(atrazo > 0) bean.setDiasAtraso(atrazo);
				else bean.setDiasAtraso(0);

			}
			if(dados[12] != null){
				bean.setQntReiteracao(dados[12].toString());
			}else{
				bean.setQntReiteracao("0");
			}

			if(dados[10] != null){
				bean.setDtCriacaoRA(Util.formatarData((Date) dados[10]));
			}

			if(dados[11] != null){
				bean.setDtEncerramentoRA(Util.formatarData((Date) dados[11]));
			}

			relatorioBeans.add(bean);
		}
		if(!tipoOrdenacao.equals("1")){
			Collections.sort(relatorioBeans);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// Per�odo de Encerramento
		String periodoEncerramento = "";
		if(periodoEncerramentoInicial != null && periodoEncerramentoFinal != null){

			periodoEncerramento = Util.formatarData(periodoEncerramentoInicial) + " a "
							+ Util.formatarData(periodoEncerramentoFinal);
		}
		// Per�odo de Atendimento
		String periodoAtendimento = "";
		if(periodoAtendimentoInicial != null && periodoAtendimentoFinal != null){

			periodoAtendimento = Util.formatarData(periodoAtendimentoInicial) + " a "
							+ Util.formatarData(periodoAtendimentoFinal);
		}

		// Unidade Atual
		String unidadeAtual = "";
		if(Util.validarStringNumerica(idUnidadeAtual)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeAtual)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeAtual = unidadeOrganizacional.getId().toString() + " - " + unidadeOrganizacional.getDescricao();
		}

		// Unidade atendimento
		String unidadeAtendimento = "";
		if(Util.validarStringNumerica(idUnidadeAtendimento)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeAtendimento)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeAtendimento = unidadeOrganizacional.getId().toString() + " - "
							+ unidadeOrganizacional.getDescricao();
		}

		// Unidade Encerramento
		String unidadeEncerramento = "";
		if(Util.validarStringNumerica(idUnidadeEncerramento)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeEncerramento)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeEncerramento = unidadeOrganizacional.getId().toString() + " - "
							+ unidadeOrganizacional.getDescricao();
		}

		// Localidade
		String localidade = "";
		if(Util.validarStringNumerica(idLocalidade)){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			Localidade local = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			localidade = local.getId().toString() + " - " + local.getDescricao();
		}

		String origem = "";
		if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
			parametros.put("tipoUnidadeAgrupamento", "Atendimento");
			origem = "Solicitados";
		}else{
			parametros.put("tipoUnidadeAgrupamento", "Encerramento");
			origem = "Seletivos";
		}

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("localidade", localidade);
		if(idsServicosTipos != null && !idsServicosTipos.equals("")
						&& (idsServicosTipos.length != 1 || !idsServicosTipos[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			parametros.put("tipoOS", Arrays.toString(idsServicosTipos));
		}else{
			parametros.put("tipoOS", null);
		}
		parametros.put("periodoEncerramento", periodoEncerramento);
		parametros.put("periodoAtendimento", periodoAtendimento);
		parametros.put("unidadeAtual", unidadeAtual);
		parametros.put("unidadeAtendimento", unidadeAtendimento);
		parametros.put("unidadeEncerramento", unidadeEncerramento);
		parametros.put("origem", origem);
		if(tipoOrdenacao.equals("1")){
			parametros.put("ordenacao", "N�mero R.A.");
		}else{
			parametros.put("ordenacao", "Endere�o");
		}

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDENS_SERVICO_ENCERRADAS_DENTRO_FORA_PRAZO, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OS_ENCERRADA_DENTRO_FORA_PRAZO, idFuncionalidadeIniciada, null);
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

		Fachada fachada = Fachada.getInstancia();

		int retorno = 0;

		String origemServico = (String) getParametro("origemServico");
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");
		String idLocalidade = (String) getParametro("idLocalidade");

		retorno = fachada.pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico, idsServicosTipos,
						idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
						periodoEncerramentoInicial, periodoEncerramentoFinal, idLocalidade);
		// pesquisaTotalRegistrosRelatorioResumoOsEncerradas

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("Relatorio Resumo de OS Encerradas", this);
	}

}