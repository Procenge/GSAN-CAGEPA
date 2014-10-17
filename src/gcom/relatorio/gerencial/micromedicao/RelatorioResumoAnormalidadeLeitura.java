/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.relatorio.gerencial.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.Processo;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.micromedicao.ResumoAnormalidadeConsultaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por criar o relatório de resumo de anormalidade por totalizador.
 */
public class RelatorioResumoAnormalidadeLeitura
				extends TarefaRelatorio {

	private static final String STRING_TODOS = "TODOS";

	private static final long serialVersionUID = 1L;

	private InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta;

	private SistemaParametro parametro;

	public RelatorioResumoAnormalidadeLeitura() {

		super(null, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_LEITURA, Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_LEITURA);
	}

	public RelatorioResumoAnormalidadeLeitura(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_LEITURA, Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_LEITURA);
	}

	protected RelatorioResumoAnormalidadeLeitura(Usuario usr, String relatorio, int processoId) {

		super(usr, relatorio, processoId);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	public Object executar() throws TarefaException{

		dadosParametrosConsulta = (InformarDadosGeracaoRelatorioConsultaHelper) getParametro("informarDadosGeracaoRelatorioConsultaHelper");
		Collection<ResumoAnormalidadeConsultaHelper> colecaoResumosAnormalidadeLeitura = (Collection) getParametro("colecaoResumosAnormalidadeLeitura");

		int opcaoTotalizacao = dadosParametrosConsulta.getOpcaoTotalizacao().intValue();
		Integer tipoRelatorio = dadosParametrosConsulta.getTipoRelatorio() == null ? TarefaRelatorio.TIPO_PDF : dadosParametrosConsulta
						.getTipoRelatorio();

		parametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoResumosAnormalidadeLeitura != null && !colecaoResumosAnormalidadeLeitura.isEmpty()){

			int codQuebra = 0;
			int codQuebraAnterior = 0;
			int codTipoMedicao = 0;
			int codTipoMedicaoAnterior = 0;
			boolean primeiraEntrada = true;
			int totalQuantidade = 0;
			int totalQuantidadeGeral = 0;
			int totalQuantidadeSemAnormalidadeGeral = 0;
			BigDecimal totalPercentual = new BigDecimal("0.00");
			totalPercentual = totalPercentual.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			ResumoAnormalidadeConsultaHelper itemAnterior = null;
			RelatorioResumoAnormalidadeLeituraBean totalComAnormalidade = null;
			RelatorioResumoAnormalidadeLeituraBean totalSemAnormalidade = null;
			RelatorioResumoAnormalidadeLeituraBean bean = null;

			for(ResumoAnormalidadeConsultaHelper itens : colecaoResumosAnormalidadeLeitura){
				String tipoLigacao = itens.getTipoLigacao();
				String codigoAnormalidade = itens.getCodigoLeituraAnormalidadeFaturada();
				String descricaoAnormalidade = itens.getDescricaoLeituraAnormalidadeFaturada();
				String qtdeLigacoes = itens.getQuantidadeMedicao();
				String percentual = arredondarPercentual(itens.getPercentual());

				// Preenche o Bean com os dados que vem do Helper
				bean = new RelatorioResumoAnormalidadeLeituraBean(null, null, tipoLigacao, codigoAnormalidade, descricaoAnormalidade,
								qtdeLigacoes, percentual);
				preencherCamposQuebra(opcaoTotalizacao, bean, itens.getParametrosQuebra());
				// Fim

				// Início Método Refatorar...

				codQuebra = bean.getIdCampoQuebra();
				codTipoMedicao = Integer.valueOf(itens.getIdMedicaoTipo());
				if(primeiraEntrada){
					codQuebraAnterior = bean.getIdCampoQuebra();
					codTipoMedicaoAnterior = Integer.valueOf(itens.getIdMedicaoTipo());
					itemAnterior = itens;
					primeiraEntrada = false;
				}
				totalQuantidadeGeral += Integer.parseInt(bean.getQtdeLigacoes());
				if(codQuebra == codQuebraAnterior && codTipoMedicao == codTipoMedicaoAnterior){
					totalQuantidade += Integer.parseInt(bean.getQtdeLigacoes());
					totalPercentual = totalPercentual.add(new BigDecimal(itens.getPercentual()));
					// Adiciona item na lista para retornar para a lista na ordem e com os totais.
					relatorioBeans.add(bean);
				}else{
					// Adiciona item na lista antes de add o total.

					totalComAnormalidade = preencherValoresTotalComAnormalidade(opcaoTotalizacao, totalQuantidade, totalPercentual,
									itemAnterior);
					totalSemAnormalidade = preencherValoresTotalSemAnormalidade(opcaoTotalizacao, dadosParametrosConsulta, itemAnterior,
									bean);
					totalQuantidadeSemAnormalidadeGeral += Integer.parseInt(totalSemAnormalidade.getQtdeLigacoes());

					// Adiciona o Total e depois o item da próxima quebra
					relatorioBeans.add(totalComAnormalidade);
					relatorioBeans.add(totalSemAnormalidade);
					relatorioBeans.add(bean);

					totalQuantidade = Integer.parseInt(bean.getQtdeLigacoes());
					totalPercentual = new BigDecimal(bean.getPercentual());
				}
				// Somatório Total

				codQuebraAnterior = bean.getIdCampoQuebra();
				codTipoMedicaoAnterior = Integer.valueOf(itens.getIdMedicaoTipo());
				itemAnterior = itens;
				// FIM Método refatorar

			}

			// Adicionar o último que sobrou
			totalComAnormalidade = preencherValoresTotalComAnormalidade(opcaoTotalizacao, totalQuantidade, totalPercentual, itemAnterior);
			totalSemAnormalidade = preencherValoresTotalSemAnormalidade(opcaoTotalizacao, dadosParametrosConsulta, itemAnterior, bean);
			relatorioBeans.add(totalComAnormalidade);
			relatorioBeans.add(totalSemAnormalidade);

			RelatorioResumoAnormalidadeLeituraBean totalGeral = new RelatorioResumoAnormalidadeLeituraBean();
			totalGeral.setCodigoAnormalidade("");
			totalGeral.setDescricaoAnormalidade("TOTAL GERAL ");
			int soma = (totalQuantidadeGeral + totalQuantidadeSemAnormalidadeGeral + Integer
							.valueOf(totalSemAnormalidade.getQtdeLigacoes()));
			totalGeral.setQtdeLigacoes(String.valueOf(soma));
			totalGeral.setPercentual(" ");
			totalGeral.setTipoLigacao(itemAnterior.getTipoLigacao());
			preencherCamposQuebra(opcaoTotalizacao, totalGeral, itemAnterior.getParametrosQuebra());
			relatorioBeans.add(totalGeral);
		}

		Map parametros = montarMapParametros();

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_LEITURA, parametros, ds, tipoRelatorio);

		// Grava o relatório no sistema
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		try{
			this.persistirRelatorioConcluido(retorno, getIdRelatorio(), idFuncionalidadeIniciada, "Relatório de anormalidade Leitura.");

		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;
	}

	private static String arredondarPercentual(String percentual){

		return ((new BigDecimal(percentual)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
	}

	/**
	 * Id do relatório unidade.
	 * 
	 * @return
	 */
	protected int getIdRelatorio(){

		return Relatorio.RELATORIO_RESUMO_LEITURA_ANORMALIDADE;
	}

	private RelatorioResumoAnormalidadeLeituraBean preencherValoresTotalSemAnormalidade(int opcaoTotalizacao,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta, ResumoAnormalidadeConsultaHelper itemAnterior,
					RelatorioResumoAnormalidadeLeituraBean bean){

		Integer qtdeTotalSemAnormalidade = Fachada.getInstancia().consultarTotalResumoSemAnormalidade(opcaoTotalizacao,
						dadosParametrosConsulta, itemAnterior, bean);

		RelatorioResumoAnormalidadeLeituraBean totalSemAnormalidade = new RelatorioResumoAnormalidadeLeituraBean();
		totalSemAnormalidade.setCodigoAnormalidade("");
		totalSemAnormalidade.setDescricaoAnormalidade("TOTAL S/ ANORMALIDADE: ");
		totalSemAnormalidade.setQtdeLigacoes(String.valueOf(qtdeTotalSemAnormalidade));
		totalSemAnormalidade.setPercentual(" ");
		totalSemAnormalidade.setTipoLigacao(itemAnterior.getTipoLigacao());
		preencherCamposQuebra(opcaoTotalizacao, totalSemAnormalidade, itemAnterior.getParametrosQuebra());

		return totalSemAnormalidade;
	}

	private RelatorioResumoAnormalidadeLeituraBean preencherValoresTotalComAnormalidade(int opcaoTotalizacao, int totalQuantidade,
					BigDecimal totalPercentual, ResumoAnormalidadeConsultaHelper itemAnterior){

		RelatorioResumoAnormalidadeLeituraBean totalComAnormalidade = new RelatorioResumoAnormalidadeLeituraBean();
		totalComAnormalidade.setCodigoAnormalidade("");
		totalComAnormalidade.setDescricaoAnormalidade("TOTAL C/ ANORMALIDADE: ");
		totalComAnormalidade.setQtdeLigacoes(String.valueOf(totalQuantidade));
		totalComAnormalidade.setPercentual(arredondarPercentual(Util.converterObjetoParaString(totalPercentual)));
		totalComAnormalidade.setTipoLigacao(itemAnterior.getTipoLigacao());
		preencherCamposQuebra(opcaoTotalizacao, totalComAnormalidade, itemAnterior.getParametrosQuebra());
		return totalComAnormalidade;
	}

	/**
	 * Método que preenche o bean com a opção de totalização.
	 * 
	 * @param opcaoTotalizacao
	 *            opção de totalização
	 * @param bean
	 *            bean que vai ser preenchido os campos de cod e descrição da quebra
	 * @param parametrosQuebra
	 *            parâmetro que obtenha valores das quebras.
	 */
	public static void preencherCamposQuebra(int opcaoTotalizacao, RelatorioResumoAnormalidadeLeituraBean bean, Object[] parametrosQuebra){

		FaturamentoGrupo faturamentoGrupo;
		int id = 0;
		String descricao = "";
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
				SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
				descricao = sistemaParametro.getNomeEstado();
				id = 0;
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				id = (Integer) parametrosQuebra[4];
				descricao = (String) parametrosQuebra[5];
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				id = (Integer) parametrosQuebra[6];
				descricao = (String) parametrosQuebra[7];
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				id = (Integer) parametrosQuebra[8];
				descricao = (String) parametrosQuebra[9];
				break;
			case ConstantesSistema.CODIGO_QUADRA:
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				id = (Integer) parametrosQuebra[10];
				descricao = (String) parametrosQuebra[11];
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				id = (Integer) parametrosQuebra[12];
				descricao = (String) parametrosQuebra[13];
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				id = (Integer) parametrosQuebra[14];
				descricao = (String) parametrosQuebra[15];
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				faturamentoGrupo = Fachada.getInstancia().pesquisarFaturamentoGrupoPorID((Integer) parametrosQuebra[16]);
				id = faturamentoGrupo.getId();
				descricao = faturamentoGrupo.getDescricao();
				break;
		}

		bean.setIdCampoQuebra(id);
		bean.setDescricaoCampoQuebra(descricao);

	}

	private Map montarMapParametros(){

		Map<String, String> parametros = new HashMap();

		parametros.put("imagem", parametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", parametro.getNomeEstado());
		String mesAnoReferencia = dadosParametrosConsulta.getAnoMesReferencia().toString();
		parametros.put("anoMesReferencia", mesAnoReferencia.substring(4) + "/" + mesAnoReferencia.substring(0, 4));
		parametros.put("labelQuebra", definirLabelQuebra());
		parametros.put("tipoAnormalidade", (String) getParametro("tipoAnormalidade"));

		StringBuilder filtroInformado = new StringBuilder();
		filtroInformado.append("Perfil do Imóvel: ");
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoImovelPerfil())){
			boolean primeiro = true;
			for(Object perfil : dadosParametrosConsulta.getColecaoImovelPerfil()){
				if(!primeiro){
					filtroInformado.append(", ");
				}
				filtroInformado.append(((ImovelPerfil) perfil).getDescricao());
				primeiro = false;
			}
		}else{
			filtroInformado.append(STRING_TODOS);
		}
		filtroInformado.append("\n");
		filtroInformado.append("Ligação de Água: ");
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoLigacaoAguaSituacao())){
			boolean primeiro = true;
			for(Object perfil : dadosParametrosConsulta.getColecaoLigacaoAguaSituacao()){
				if(!primeiro){
					filtroInformado.append(", ");
				}
				filtroInformado.append(((LigacaoAguaSituacao) perfil).getDescricao());
				primeiro = false;
			}
		}else{
			filtroInformado.append(STRING_TODOS);
		}
		filtroInformado.append("\n");
		filtroInformado.append("Ligação de Esgoto: ");
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoLigacaoEsgotoSituacao())){
			boolean primeiro = true;
			for(Object perfil : dadosParametrosConsulta.getColecaoLigacaoEsgotoSituacao()){
				if(!primeiro){
					filtroInformado.append(", ");
				}
				filtroInformado.append(((LigacaoEsgotoSituacao) perfil).getDescricao());
				primeiro = false;
			}
		}else{
			filtroInformado.append(STRING_TODOS);
		}
		filtroInformado.append("\n");
		filtroInformado.append("Categoria: ");
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoCategoria())){
			boolean primeiro = true;
			for(Object perfil : dadosParametrosConsulta.getColecaoCategoria()){
				if(!primeiro){
					filtroInformado.append(", ");
				}
				filtroInformado.append(((Categoria) perfil).getDescricao());
				primeiro = false;
			}
		}else{
			filtroInformado.append(STRING_TODOS);
		}
		filtroInformado.append("\n");

		filtroInformado.append("Esfera de Poder: ");
		if(!Util.isVazioOrNulo(dadosParametrosConsulta.getColecaoEsferaPoder())){
			boolean primeiro = true;
			for(Object perfil : dadosParametrosConsulta.getColecaoEsferaPoder()){
				if(!primeiro){
					filtroInformado.append(", ");
				}
				filtroInformado.append(((EsferaPoder) perfil).getDescricao());
				primeiro = false;
			}
		}else{
			filtroInformado.append(STRING_TODOS);
		}

		parametros.put("filtroInformado", filtroInformado.toString());
		return parametros;
	}

	private String definirLabelQuebra(){

		Integer opcaoTotalizacao = dadosParametrosConsulta.getOpcaoTotalizacao();

		String titulo;
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
				titulo = "ESTADO: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", ELO PÓLO: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", GERÊNCIA REGIONAL: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", GRUPO COBRANÇA: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", GRUPO FATURAMENTO: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", LOCALIDADE: ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				titulo = "ESTADO: " + parametro.getNomeEstado() + ", UNIDADE NEGÓCIO: ";
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				titulo = "GRUPO DE FATURAMENTO: ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				titulo = "GERÊNCIA REGIONAL: ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				titulo = "GERÊNCIA REGIONAL:  " + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ", ELO PÓLO: ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				titulo = "GERÊNCIA REGIONAL:  " + dadosParametrosConsulta.getGerenciaRegional().getNomeComId() + ", LOCALIDADE: ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
				titulo = "ELO PÓLO: ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				titulo = "ELO PÓLO:  " + dadosParametrosConsulta.getEloPolo().getId() + " - "
								+ dadosParametrosConsulta.getEloPolo().getDescricao() + ", LOCALIDADE: ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				titulo = "ELO PÓLO:  " + dadosParametrosConsulta.getEloPolo().getId() + " - "
								+ dadosParametrosConsulta.getEloPolo().getDescricao() + ", SETOR COMERCIAL: ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
				titulo = "LOCALIDADE: ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				titulo = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ", QUADRA: ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				titulo = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + ", SETOR COMERCIAL: ";
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				titulo = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao() + //
								", SETOR: ";
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				titulo = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao()
								+ //
								" , SETOR:  " + dadosParametrosConsulta.getSetorComercial().getId() + " - "
								+ dadosParametrosConsulta.getSetorComercial().getDescricao() + ", QUADRA: ";
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				titulo = "LOCALIDADE: " + dadosParametrosConsulta.getLocalidade().getId() + " - "
								+ dadosParametrosConsulta.getLocalidade().getDescricao()
								+ //
								", SETOR: " + dadosParametrosConsulta.getSetorComercial().getId() + " - "
								+ dadosParametrosConsulta.getSetorComercial().getDescricao() + //
								", QUADRA: ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				titulo = "UNIDADE NEGÓCIO: ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				titulo = "UNIDADE NEGÓCIO: " + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ", ELO PÓLO: ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				titulo = "UNIDADE NEGÓCIO: " + dadosParametrosConsulta.getUnidadeNegocio().getId() + " - "
								+ dadosParametrosConsulta.getUnidadeNegocio().getNome() + ", LOCALIDADE: ";
				break;

			default:
				titulo = "ERRO!!!";
				break;
		}

		return titulo;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Collection anormalidades = (Collection) getParametro("colecaoResumosAnormalidadeLeitura");
		return anormalidades == null ? 0 : anormalidades.size();
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoAnormalidadeLeitura", this);
	}
}