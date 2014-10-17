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
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
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
import gcom.util.parametrizacao.ParametroGeral;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Gerar e Emitir Extrato de Débito por Cliente
 * 
 * @author Ana Maria
 * @date 09/04/2007
 */

public class RelatorioExtratoDebitoCliente
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7034984685957706140L;

	/**
	 * Quantidade máxima de linhas do detail da primeira página do relátorio
	 */
	public final static int NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA = 62;

	/**
	 * Quantidade máxima de linhas do detail a partir da segunda página do relátorio
	 */
	public final static int NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS = 104;

	public RelatorioExtratoDebitoCliente(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EXTRATO_DEBITO_CLIENTE);
	}

	@Deprecated
	public RelatorioExtratoDebitoCliente() {

		super(null, "");
	}

	private Collection<RelatorioExtratoDebitoClienteBean> inicializarBeanRelatorioTotalLinhaFinal(
					Collection<ContaValoresHelper> colecaoContas){

		Collection<RelatorioExtratoDebitoClienteBean> retorno = new ArrayList();
		/*
		 * Selecionar os itens do documento de cobrança
		 * correspondentes a conta e ordenar por ano/mês de
		 * referência da conta
		 */
		if(colecaoContas != null && !colecaoContas.isEmpty()){

			Collection<RelatorioExtratoDebitoClienteDetailBean> colecaoDetail = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
			Collection<RelatorioExtratoDebitoClienteDetailBean> colecaoDetailTemp = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
			Collection<RelatorioExtratoDebitoClienteDetailBean> sobraColecao = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
			Iterator colecaoContasIterator = colecaoContas.iterator();

			int totalPaginasRelatorio = (colecaoContas.size() / NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS) + 2;
			String indicadorPrimeiraPagina = "2";

			if(colecaoContas.size() < NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA){
				indicadorPrimeiraPagina = "1";
			}

			while(colecaoContasIterator.hasNext()){

				String mesAno = "";
				String matricula = "";
				String vencimentoFatura = "";
				String valorFatura = "";

				ContaValoresHelper contaHelper = (ContaValoresHelper) colecaoContasIterator.next();

				// Mês/Ano de referência da conta
				mesAno = Util.completaString(Util.formatarAnoMesParaMesAno(contaHelper.getConta().getReferencia()), 9);

				// Matricula
				matricula = Util.retornaMatriculaImovelFormatadaParametrizada(contaHelper.getConta().getImovel().getId());

				// Data de vencimento da conta
				vencimentoFatura = Util.formatarData(contaHelper.getConta().getDataVencimentoConta());

				// Valor do item
				valorFatura = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(contaHelper.getConta().getValorTotal()), 16);

				RelatorioExtratoDebitoClienteDetailBean bean = new RelatorioExtratoDebitoClienteDetailBean(mesAno, matricula,
								vencimentoFatura, valorFatura);

				colecaoDetail.add(bean);

				if(colecaoDetail.size() == NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS || !colecaoContasIterator.hasNext()){

					if(!colecaoContasIterator.hasNext()){
						if(colecaoDetail.size() > NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA){
							indicadorPrimeiraPagina = "" + totalPaginasRelatorio;
							int contador = 0;
							for(RelatorioExtratoDebitoClienteDetailBean item : colecaoDetail){
								contador++;
								if(contador > NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS){
									sobraColecao.add(item);
								}else{
									colecaoDetailTemp.add(item);
								}
							}
						}else{
							indicadorPrimeiraPagina = "1";
						}
					}else{
						indicadorPrimeiraPagina = "" + totalPaginasRelatorio;
					}

					if(!colecaoDetailTemp.isEmpty()){
						colecaoDetail = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
						colecaoDetail.addAll(colecaoDetailTemp);
					}

					RelatorioExtratoDebitoClienteBean relatorioExtratoDebitoClienteBean = new RelatorioExtratoDebitoClienteBean(
									indicadorPrimeiraPagina, colecaoDetail);
					retorno.add(relatorioExtratoDebitoClienteBean);
					colecaoDetail = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
					totalPaginasRelatorio--;
				}
			}

			RelatorioExtratoDebitoClienteBean relatorioExtratoDebitoClienteBean = new RelatorioExtratoDebitoClienteBean("1", sobraColecao);
			retorno.add(relatorioExtratoDebitoClienteBean);
		}

		return retorno;
	}

	private Collection<RelatorioExtratoDebitoClienteBean> inicializarBeanRelatorio(Collection<ContaValoresHelper> colecaoContas){

		Collection<RelatorioExtratoDebitoClienteBean> retorno = new ArrayList();
		/*
		 * Selecionar os itens do documento de cobrança
		 * correspondentes a conta e ordenar por ano/mês de
		 * referência da conta
		 */
		if(colecaoContas != null && !colecaoContas.isEmpty()){

			Collection<RelatorioExtratoDebitoClienteDetailBean> colecaoDetail = new ArrayList<RelatorioExtratoDebitoClienteDetailBean>();
			Iterator colecaoContasIterator = colecaoContas.iterator();

			int totalLinhasRelatorio = 0;
			int totalPaginasRelatorio = 1;
			String indicadorPrimeiraPagina = "2";

			while(colecaoContasIterator.hasNext()){

				String mesAno = "";
				String matricula = "";
				String vencimentoFatura = "";
				String valorFatura = "";

				ContaValoresHelper contaHelper = (ContaValoresHelper) colecaoContasIterator.next();

				// Mês/Ano de referência da conta
				mesAno = Util.completaString(Util.formatarAnoMesParaMesAno(contaHelper.getConta().getReferencia()), 9);

				// Matricula
				matricula = Util.retornaMatriculaImovelFormatadaParametrizada(contaHelper.getConta().getImovel().getId());

				// Data de vencimento da conta
				vencimentoFatura = Util.formatarData(contaHelper.getConta().getDataVencimentoConta());

				// Valor do item
				valorFatura = Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(contaHelper.getConta().getValorTotal()), 16);

				RelatorioExtratoDebitoClienteDetailBean bean = new RelatorioExtratoDebitoClienteDetailBean(mesAno, matricula,
								vencimentoFatura, valorFatura);

				colecaoDetail.add(bean);

				totalLinhasRelatorio = totalLinhasRelatorio + 1;

				if((totalLinhasRelatorio == NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA)
								|| (totalLinhasRelatorio - NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA)
												% NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS == 0){

					RelatorioExtratoDebitoClienteBean relatorioExtratoDebitoClienteBean = new RelatorioExtratoDebitoClienteBean(
									indicadorPrimeiraPagina, colecaoDetail);
					retorno.add(relatorioExtratoDebitoClienteBean);
					colecaoDetail.clear();

				}

				if((totalLinhasRelatorio - NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA) % NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS == 0){
					totalPaginasRelatorio = totalPaginasRelatorio + 1;
					indicadorPrimeiraPagina = "" + (totalPaginasRelatorio);
				}
			}

			if(totalLinhasRelatorio != NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA
							&& ((totalLinhasRelatorio - NUMERO_MAX_REGISTROS_DETAIL_PRIMEIRA_PAGINA)
											% NUMERO_MAX_REGISTROS_DETAIL_OUTRAS_PAGINAS != 0)){

				RelatorioExtratoDebitoClienteBean relatorioExtratoDebitoClienteBean = new RelatorioExtratoDebitoClienteBean(
								indicadorPrimeiraPagina, colecaoDetail);
				retorno.add(relatorioExtratoDebitoClienteBean);

			}

		}

		return retorno;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		String nomeCliente = (String) getParametro("nomeCliente");
		String codigoClienteResponsavel = (String) getParametro("codigoClienteResponsavel");
		String enderecoCliente = (String) getParametro("enderecoCliente");
		String seqDocCobranca = (String) getParametro("seqDocCobranca");
		String tipoResponsavel = (String) getParametro("tipoResponsavel");
		String dataEmissao = (String) getParametro("dataEmissao");
		if(dataEmissao == null || dataEmissao.equals("")){
			Date data = new Date();
			String dataCorrenteFormatada = Util.formatarData(data);
			dataEmissao = dataCorrenteFormatada;
		}
		String dataValidade = (String) getParametro("dataValidade");
		String valorContas = (String) getParametro("valorContas");
		String debitosACobrar = (String) getParametro("debitosACobrar");
		String acrescimoImpontualidade = (String) getParametro("acrescimoImpontualidade");
		String valorTotalContas = (String) getParametro("valorTotalContas");

		String representacaoNumericaCodBarra = (String) getParametro("representacaoNumericaCodBarra");
		String representacaoNumericaCodBarraSemDigito = (String) getParametro("representacaoNumericaCodBarraSemDigito");

		Collection<ContaValoresHelper> colecaoContas = (Collection) getParametro("colecaoContas");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		Collection<RelatorioExtratoDebitoClienteBean> colecaoBean = null;

		if(true){ // Foi solicitado na OC1173832 para ser exibino na ultima página! Caso outra
					// empresa solicite na primeira página usar o outro método e criar um parametro
					// de sistema.
			colecaoBean = this.inicializarBeanRelatorioTotalLinhaFinal(colecaoContas);
		}else{
			colecaoBean = this.inicializarBeanRelatorio(colecaoContas);
		}

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		// Linha 2
		parametros.put("nomeCliente", nomeCliente);
		parametros.put("codigoClienteResponsavel", codigoClienteResponsavel);

		// Linha 3
		parametros.put("enderecoCliente", enderecoCliente);
		parametros.put("seqDocCobranca", seqDocCobranca);

		// Linha 4
		parametros.put("tipoResponsavel", tipoResponsavel);
		parametros.put("quantidadeFaturas", "" + colecaoContas.size());

		// Linha 11
		parametros.put("dataEmissao", dataEmissao);
		parametros.put("valorContas", valorContas);
		parametros.put("debitosACobrar", debitosACobrar);
		parametros.put("acrescimoImpontualidade", acrescimoImpontualidade);
		parametros.put("valorTotalContas", valorTotalContas);

		// Linha 13
		parametros.put("representacaoNumericaCodBarra", representacaoNumericaCodBarra);
		parametros.put("representacaoNumericaCodBarraSemDigito", representacaoNumericaCodBarraSemDigito);

		parametros.put("dataValidade", dataValidade);

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagemConta", sistemaParametro.getImagemConta());

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", sistemaParametro.getNumeroTelefone());
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_EXTRATO_DEBITO_CLIENTE, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.EXTRATO_DEBITO_CLIENTE, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioExtratoDebitoCliente", this);
	}
}
