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

package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.cobranca.parcelamento.Parcelamento;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RelatorioDebitoPorResponsavelAnalitico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioDebitoPorResponsavelAnalitico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_DEBITO_POR_RESPONSAVEL_ANALITICO);
	}

	@Deprecated
	public RelatorioDebitoPorResponsavelAnalitico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// Obtêm os parâmetros passados
		FiltroRelatorioDebitoPorResponsavelHelper filtro = (FiltroRelatorioDebitoPorResponsavelHelper) getParametro("filtroRelatorioDebitoPorResponsavelHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		String enderecoResponsavel = "";
		String enderecoImovel = "";
		Imovel imovel = new Imovel();
		Localidade localidade = new Localidade();
		SetorComercial setorComercial = new SetorComercial();
		Quadra quadra = new Quadra();
		CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidade = null;
		BigDecimal valorMultasCobradas = null;
		String dataVencimentoString = null;
		Date dataVencimentoDate = null;
		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();
		Integer totalConsumidores = 0;
		BigDecimal valorJuros = BigDecimal.ZERO;
		BigDecimal valorMulta = BigDecimal.ZERO;
		BigDecimal valorCorrecao = BigDecimal.ZERO;

		// Obtém os dados do relatório
		Collection colecaoDadosRelatorio = fachada.pesquisarRelatorioDebitoPorReponsavel(filtro);

		for(Iterator iterator = colecaoDadosRelatorio.iterator(); iterator.hasNext();){

			Object[] dados = (Object[]) iterator.next();

			RelatorioDebitoPorResponsavelBean bean = new RelatorioDebitoPorResponsavelBean();

			// Código do Responsável
			bean.setIdResponsavel(dados[1].toString());

			// Nome do Responsável
			bean.setNomeResponsavel(dados[2].toString());

			enderecoResponsavel = fachada.pesquisarEnderecoClienteAbreviado(Util.obterInteger(bean.getIdResponsavel()));

			// Endereço do Responsável
			bean.setEnderecoResponsavel(enderecoResponsavel);

			// Tipo do Responsável
			bean.setTipoResponsavel(dados[19].toString() + " - " + dados[20].toString());

			// Matrícula do Imóvel
			bean.setMatricula(Util.obterInteger(dados[3].toString()));

			// Inscrição
			localidade.setId(Util.obterInteger(dados[4].toString()));
			imovel.setLocalidade(localidade);
			setorComercial.setCodigo(Util.obterShort(dados[6].toString()));
			imovel.setSetorComercial(setorComercial);
			quadra.setNumeroQuadra(Util.obterInteger(dados[7].toString()));
			imovel.setQuadra(quadra);
			imovel.setLote(Util.obterShort(dados[8].toString()));
			imovel.setSubLote(Util.obterShort(dados[9].toString()));
			bean.setInscricao(imovel.getInscricaoFormatada());

			// Situação da Ligação de Àgua
			bean.setIdLigacaoAguaSituacao(dados[11].toString());
			bean.setDescricaoLigacaoAguaSituacao(dados[21].toString());
			bean.setEsferaPoder(dados[22].toString());

			// Situação da Ligação de Esgoto
			bean.setIdLigacaoEsgotoSituacao(dados[12].toString());

			// Cliente Usuário
			bean.setClienteUsuario(dados[10].toString());

			try{

				enderecoImovel = fachada.pesquisarEnderecoFormatado(bean.getMatricula());
			}catch(ControladorException e){

				e.printStackTrace();
			}

			// Endereço do Imóvel
			bean.setEnderecoImovel(enderecoImovel);

			// Referência do Débito
			bean.setReferenciaDebito(Util.formatarAnoMesParaMesAno(dados[15].toString()));

			// Data de Vencimento
			bean.setDataVencimento(Util.formatarData(dados[16].toString().replace("-", "")));

			// Consumo de Água
			if(dados[17] != null && !dados[17].toString().equals("")){

				bean.setConsumoAgua(Util.obterInteger(dados[17].toString()));
			}else{

				bean.setConsumoAgua(0);
			}

			// Consumo de Esgoto
			if(dados[18] != null && !dados[18].toString().equals("")){

				bean.setConsumoEsgoto(Util.obterInteger(dados[18].toString()));
			}else{

				bean.setConsumoEsgoto(0);
			}

			// Valor Nominal
			bean.setValorNominalConsumidor(new BigDecimal(dados[14].toString()));

			if(bean.getValorNominalConsumidor() == null){

				bean.setValorNominalConsumidor(BigDecimal.ZERO);
			}

			if(filtro.getIndicadorValorCorrigido() != null && filtro.getIndicadorValorCorrigido().equals(ConstantesSistema.SIM)){

				calcularAcrescimoPorImpontualidade = new CalcularAcrescimoPorImpontualidadeHelper();
				valorMultasCobradas = fachada.pesquisarValorMultasCobradas(Util.obterInteger(dados[0].toString()));
				dataVencimentoString = dados[16].toString().replace("-", "");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

				try{

					dataVencimentoDate = sdf1.parse(dataVencimentoString);
				}catch(ParseException e){

					e.printStackTrace();
				}

				dataVencimentoString = Util.formatarData(dados[16].toString().replace("-", ""));

				// [UC0216] Calcular Acrescimo por Impontualidade
				calcularAcrescimoPorImpontualidade = fachada
								.calcularAcrescimoPorImpontualidade(Util.obterInteger(dados[15].toString()), dataVencimentoDate, null,
												bean.getValorNominalConsumidor(), valorMultasCobradas,
												Util.obterShort(dados[13].toString()), sistemaParametros.getAnoMesArrecadacao().toString(),
												Util.obterInteger(dados[0].toString()), ConstantesSistema.SIM, ConstantesSistema.SIM,
												ConstantesSistema.SIM);

				if(calcularAcrescimoPorImpontualidade != null){

					// Valor de Correção
					if(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria() != null){

						valorCorrecao = calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria().setScale(
										Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						bean.setValorCorrecaoConsumidor(valorCorrecao);
					}else{

						bean.setValorCorrecaoConsumidor(BigDecimal.ZERO);
					}

					// Valor de Juros
					if(calcularAcrescimoPorImpontualidade.getValorJurosMora() != null){

						valorJuros = calcularAcrescimoPorImpontualidade.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO);
						bean.setValorJurosConsumidor(valorJuros);
					}else{

						bean.setValorJurosConsumidor(BigDecimal.ZERO);
					}

					// Valor de Multa
					if(calcularAcrescimoPorImpontualidade.getValorMulta() != null){

						valorMulta = calcularAcrescimoPorImpontualidade.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO);
						bean.setValorMultaConsumidor(valorMulta);
					}else{

						bean.setValorMultaConsumidor(BigDecimal.ZERO);
					}
				}else{

					bean.setValorCorrecaoConsumidor(BigDecimal.ZERO);
					bean.setValorJurosConsumidor(BigDecimal.ZERO);
					bean.setValorMultaConsumidor(BigDecimal.ZERO);
				}
			}else{

				bean.setValorCorrecaoConsumidor(BigDecimal.ZERO);
				bean.setValorJurosConsumidor(BigDecimal.ZERO);
				bean.setValorMultaConsumidor(BigDecimal.ZERO);
			}

			// Valor Total Consumidor
			bean.setValorTotalConsumidor(bean.getValorNominalConsumidor().add(bean.getValorCorrecaoConsumidor())
							.add(bean.getValorJurosConsumidor()).add(bean.getValorMultaConsumidor()));

			if(bean.getValorTotalConsumidor() == null){

				bean.setValorTotalConsumidor(BigDecimal.ZERO);
			}

			relatorioBeans.add(bean);
		}

		BigDecimal totalValorNominal = BigDecimal.ZERO;
		BigDecimal totalValorJuros = BigDecimal.ZERO;
		BigDecimal totalValorMulta = BigDecimal.ZERO;
		BigDecimal totalValorCorrecao = BigDecimal.ZERO;
		BigDecimal totalValorGeral = BigDecimal.ZERO;
		Collection<Integer> colecaoMatriculas = new ArrayList<Integer>();
		Map<Integer, Collection<Integer>> mapSituacaoConsumidores = new HashMap<Integer, Collection<Integer>>();
		Map<Integer, BigDecimal> mapSituacaoValorNominal = new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> mapSituacaoValorJuros = new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> mapSituacaoValorMulta = new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> mapSituacaoValorCorrecao = new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> mapSituacaoValorTotal = new HashMap<Integer, BigDecimal>();
		Collection<RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean> relatorioTotalizacaoBeans = new ArrayList<RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean>();
		RelatorioDebitoPorResponsavelBean beanAnterior = new RelatorioDebitoPorResponsavelBean();

		// Totaliza por situação da ligação de água para cada responsável
		for(Iterator iterator = relatorioBeans.iterator(); iterator.hasNext();){

			RelatorioDebitoPorResponsavelBean bean = (RelatorioDebitoPorResponsavelBean) iterator.next();

			if(beanAnterior.getIdResponsavel() != null && !beanAnterior.getIdResponsavel().equals(bean.getIdResponsavel())){

				relatorioTotalizacaoBeans.clear();
				colecaoMatriculas.clear();
				mapSituacaoValorNominal.clear();
				mapSituacaoValorJuros.clear();
				mapSituacaoValorMulta.clear();
				mapSituacaoValorCorrecao.clear();
				mapSituacaoValorTotal.clear();
				mapSituacaoConsumidores.clear();

			}

			Integer idLigacaoAguaSituacao = Util.obterInteger(bean.getIdLigacaoAguaSituacao());

			if(mapSituacaoConsumidores.get(idLigacaoAguaSituacao) == null){

				RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean beanAux = new RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean();

				beanAux.setIdLigacaoAguaSituacao(idLigacaoAguaSituacao);
				beanAux.setDescricaoSituacao(bean.getDescricaoLigacaoAguaSituacao());
				beanAux.setQuantidadeConsumidores("1");
				beanAux.setValorNominalConsumidor(bean.getValorNominalConsumidor());
				beanAux.setValorCorrecaoConsumidor(bean.getValorCorrecaoConsumidor());
				beanAux.setValorMultaConsumidor(bean.getValorMultaConsumidor());
				beanAux.setValorJurosConsumidor(bean.getValorJurosConsumidor());
				beanAux.setValorTotalConsumidor(bean.getValorTotalConsumidor());

				colecaoMatriculas = new ArrayList<Integer>();
				colecaoMatriculas.add(bean.getMatricula());
				mapSituacaoConsumidores.put(idLigacaoAguaSituacao, colecaoMatriculas);
				mapSituacaoValorNominal.put(idLigacaoAguaSituacao, bean.getValorNominalConsumidor());
				mapSituacaoValorCorrecao.put(idLigacaoAguaSituacao, bean.getValorCorrecaoConsumidor());
				mapSituacaoValorJuros.put(idLigacaoAguaSituacao, bean.getValorJurosConsumidor());
				mapSituacaoValorMulta.put(idLigacaoAguaSituacao, bean.getValorMultaConsumidor());
				mapSituacaoValorTotal.put(idLigacaoAguaSituacao, bean.getValorTotalConsumidor());

				relatorioTotalizacaoBeans.add(beanAux);
			}else{

				colecaoMatriculas = mapSituacaoConsumidores.get(idLigacaoAguaSituacao);

				if(!colecaoMatriculas.contains(bean.getMatricula())){

					colecaoMatriculas.add(bean.getMatricula());
					mapSituacaoConsumidores.put(idLigacaoAguaSituacao, colecaoMatriculas);
				}

				totalValorNominal = mapSituacaoValorNominal.get(idLigacaoAguaSituacao);
				totalValorNominal = totalValorNominal.add(bean.getValorNominalConsumidor());
				mapSituacaoValorNominal.put(idLigacaoAguaSituacao, totalValorNominal);

				totalValorCorrecao = mapSituacaoValorCorrecao.get(idLigacaoAguaSituacao);
				totalValorCorrecao = totalValorCorrecao.add(bean.getValorCorrecaoConsumidor());
				mapSituacaoValorCorrecao.put(idLigacaoAguaSituacao, totalValorCorrecao);

				totalValorJuros = mapSituacaoValorJuros.get(idLigacaoAguaSituacao);
				totalValorJuros = totalValorJuros.add(bean.getValorJurosConsumidor());
				mapSituacaoValorJuros.put(idLigacaoAguaSituacao, totalValorJuros);

				totalValorMulta = mapSituacaoValorMulta.get(idLigacaoAguaSituacao);
				totalValorMulta = totalValorMulta.add(bean.getValorMultaConsumidor());
				mapSituacaoValorMulta.put(idLigacaoAguaSituacao, totalValorMulta);

				totalValorGeral = mapSituacaoValorTotal.get(idLigacaoAguaSituacao);
				totalValorGeral = totalValorGeral.add(bean.getValorTotalConsumidor());
				mapSituacaoValorTotal.put(idLigacaoAguaSituacao, totalValorGeral);
			}

			for(RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean beanSubRelatorio : relatorioTotalizacaoBeans){

				if(mapSituacaoConsumidores.get(beanSubRelatorio.getIdLigacaoAguaSituacao()) != null){
					beanSubRelatorio.setQuantidadeConsumidores(String.valueOf(mapSituacaoConsumidores.get(
									beanSubRelatorio.getIdLigacaoAguaSituacao()).size()));
				}

				beanSubRelatorio.setValorNominalConsumidor(mapSituacaoValorNominal.get(beanSubRelatorio.getIdLigacaoAguaSituacao()));
				beanSubRelatorio.setValorCorrecaoConsumidor(mapSituacaoValorCorrecao.get(beanSubRelatorio.getIdLigacaoAguaSituacao()));
				beanSubRelatorio.setValorJurosConsumidor(mapSituacaoValorJuros.get(beanSubRelatorio.getIdLigacaoAguaSituacao()));
				beanSubRelatorio.setValorMultaConsumidor(mapSituacaoValorMulta.get(beanSubRelatorio.getIdLigacaoAguaSituacao()));
				beanSubRelatorio.setValorTotalConsumidor(mapSituacaoValorTotal.get(beanSubRelatorio.getIdLigacaoAguaSituacao()));

			}

			bean.setarBeansTotalizadores(relatorioTotalizacaoBeans);

			beanAnterior = bean;
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(filtro.getIndicadorResponsabilidade() != null){

			if(filtro.getIndicadorResponsabilidade().equals("D")){

				parametros.put("responsabilidade", "RESPONSABILIDADE DIRETA");
			}else if(filtro.getIndicadorResponsabilidade().equals("I")){

				parametros.put("responsabilidade", "RESPONSABILIDADE INDIRETA");
			}else{

				parametros.put("responsabilidade", "AMBAS RESPONSABILIDADES");
			}
		}

		String parametrosFiltro = "PARÂMETROS: (   ";

		if(filtro.getIdClienteInicial() != null){

			parametrosFiltro += "RSP: " + filtro.getIdClienteInicial().toString() + " - " + filtro.getIdClienteFinal().toString() + "  ";
		}

		if(filtro.getReferenciaDebitoInicial() != null){

			parametrosFiltro += "REF: " + Util.formatarAnoMesParaMesAno(filtro.getReferenciaDebitoInicial()) + " - "
							+ Util.formatarAnoMesParaMesAno(filtro.getReferenciaDebitoFinal()) + "  ";
		}

		if(filtro.getIdTipoClienteInicial() != null){

			parametrosFiltro += "TIPO: " + filtro.getIdTipoClienteInicial().toString() + " - " + filtro.getIdTipoClienteFinal().toString()
							+ "  ";
		}

		parametrosFiltro += "TIP. REL: ANALÍTICO  ";

		if(filtro.getIndicadorValorCorrigido() != null){

			if(filtro.getIndicadorValorCorrigido().equals(ConstantesSistema.SIM)){

				parametrosFiltro += "CORRIGIR: SIM  ";
			}else{

				parametrosFiltro += "CORRIGIR: NÃO  ";
			}
		}

		if(filtro.getIndicadorContasEmRevisao() != null){

			if(filtro.getIndicadorContasEmRevisao().equals(ConstantesSistema.SIM)){

				parametrosFiltro += "CTA. REVISÃO: SIM  ";
			}else{

				parametrosFiltro += "CTA. REVISÃO: NÃO  ";
			}
		}

		if(filtro.getIdMotivoRevisao() != null && Util.verificarIdNaoVazio(filtro.getIdMotivoRevisao().toString())){

			parametrosFiltro += "MOT REV.: " + filtro.getIdMotivoRevisao() + "  ";
		}

		parametrosFiltro += ")";

		parametros.put("parametrosFiltro", parametrosFiltro);

		if(tipoFormatoRelatorio == TIPO_PDF){

			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_DEBITO_POR_RESPONSAVEL_ANALITICO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_DEBITO_POR_RESPONSAVEL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		FiltroRelatorioDebitoPorResponsavelHelper filtro = (FiltroRelatorioDebitoPorResponsavelHelper) getParametro("filtroRelatorioDebitoPorResponsavelHelper");
		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(filtro);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioDebitoPorResponsavelAnalitico", this);
	}

}