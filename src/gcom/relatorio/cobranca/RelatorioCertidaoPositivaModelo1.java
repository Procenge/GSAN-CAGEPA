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
 * 
 * GSANPCG
 * Virgínia Melo
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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
import java.util.*;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * [UC3154] Gerar Certidão Positiva
 * 
 * @author Anderson Italo
 * @date 07/08/2014
 */
public class RelatorioCertidaoPositivaModelo1
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioCertidaoPositivaModelo1(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CERTIDAO_POSITIVA_MODELO_1);
	}

	@Deprecated
	public RelatorioCertidaoPositivaModelo1() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer matriculaImovel = (Integer) getParametro("matriculaImovel");
		String anoMesReferenciaInicial = (String) getParametro("anoMesReferenciaInicial");
		String anoMesReferenciaFinal = (String) getParametro("anoMesReferenciaFinal");
		Date dataVencimentoInicial = (Date) getParametro("dataVencimentoInicial");
		Date dataVencimentoFinal = (Date) getParametro("dataVencimentoFinal");
		String usuarioEmissor = (String) getParametro("usuarioEmissor");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List colecaoRelatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);

		// Obtendo os débitos do imovel
		ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoImovel.intValue(),
						matriculaImovel.toString(), null, null, anoMesReferenciaInicial, anoMesReferenciaFinal,
						dataVencimentoInicial,
						dataVencimentoFinal,
						indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(),
						indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, new Date(),
 ConstantesSistema.SIM, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
						ConstantesSistema.SIM, 2, null);

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

		ContaValoresHelper dadosConta = null;

		BigDecimal valorPrincipal = BigDecimal.ZERO;
		BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
		BigDecimal valorMulta = BigDecimal.ZERO;
		BigDecimal valorJuros = BigDecimal.ZERO;
		BigDecimal valorDivida = BigDecimal.ZERO;
		HashMap<String, Collection<String>> mapAnoReferenciaExercicioContas = new HashMap<String, Collection<String>>();
		HashMap<String, Collection<String>> mapAnoReferenciaExercicioGuias = new HashMap<String, Collection<String>>();
		Collection<String> colecaoMesesExercicio = null;
		String anoDebito = "";
		String mesDebito;

		Cliente clienteUsuarioImovel = fachada.pesquisarClienteUsuarioImovel(matriculaImovel);

		if(!Util.isVazioOrNulo(colecaoContaValores)){

			Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();

			while(colecaoContaValoresIterator.hasNext()){

				dadosConta = (ContaValoresHelper) colecaoContaValoresIterator.next();

				// Só considera contas vencidas e que estejam associadas ao usuário atual do imóvel
				ClienteConta clienteConta = fachada.pesquisarClienteContaPorTipoRelacao(dadosConta.getConta().getId(),
								ClienteRelacaoTipo.USUARIO);

				if(dadosConta.getConta().getDataVencimentoConta() != null
								&& Util.compararData(dadosConta.getConta().getDataVencimentoConta(), new Date()) == -1
								&& clienteConta.getCliente().getId().equals(clienteUsuarioImovel.getId())){

					valorPrincipal = valorPrincipal.add(dadosConta.getConta().getValorTotal());
					valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(dadosConta.getValorAtualizacaoMonetaria());
					valorJuros = valorJuros.add(dadosConta.getValorJurosMora());
					valorMulta = valorMulta.add(dadosConta.getValorMulta());
					anoDebito = String.valueOf(dadosConta.getConta().getReferencia()).substring(0, 4);
					mesDebito = String.valueOf(dadosConta.getConta().getReferencia()).substring(4);
					
					if(mapAnoReferenciaExercicioContas.containsKey(anoDebito)){
						
						colecaoMesesExercicio = mapAnoReferenciaExercicioContas.get(anoDebito);
						colecaoMesesExercicio.add(mesDebito);
						mapAnoReferenciaExercicioContas.put(anoDebito, colecaoMesesExercicio);
						
					}else{
						
						colecaoMesesExercicio = new ArrayList<String>();
						colecaoMesesExercicio.add(mesDebito);
						mapAnoReferenciaExercicioContas.put(anoDebito, colecaoMesesExercicio);
					}
				}
			}
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();

		BigDecimal valorGuiaPagamento = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){

			Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores.iterator();

			// Percorre a colecao de Prestações da Guia de Pagamento somando o valor para obter o
			// total em aberto
			while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){

				GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) colecaoGuiaPagamentoValoresHelperIterator
								.next();
				valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
								Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

				if(dadosGuiaPagamentoValoresHelper.getAnoMesReferenciaFaturamento() != null){

					anoDebito = String.valueOf(dadosGuiaPagamentoValoresHelper.getAnoMesReferenciaFaturamento()).substring(0, 4);
					mesDebito = String.valueOf(dadosGuiaPagamentoValoresHelper.getAnoMesReferenciaFaturamento()).substring(4);
				}else{

					anoDebito = String.valueOf(Util.getAnoMesComoInteger(new Date())).substring(0, 4);
					mesDebito = String.valueOf(Util.getAnoMesComoInteger(new Date())).substring(4);
				}

				if(mapAnoReferenciaExercicioGuias.containsKey(anoDebito)){

					colecaoMesesExercicio = mapAnoReferenciaExercicioGuias.get(anoDebito);
					colecaoMesesExercicio.add(mesDebito);
					mapAnoReferenciaExercicioGuias.put(anoDebito, colecaoMesesExercicio);

				}else{

					colecaoMesesExercicio = new ArrayList<String>();
					colecaoMesesExercicio.add(mesDebito);
					mapAnoReferenciaExercicioGuias.put(anoDebito, colecaoMesesExercicio);
				}
			}

			valorPrincipal = valorPrincipal.add(valorGuiaPagamento);
		}

		valorDivida = valorPrincipal.add(valorMulta.add(valorJuros).add(valorAtualizacaoMonetaria));

		String enderecoImovel = fachada.obterEnderecoAbreviadoImovel(matriculaImovel);
		Categoria categoria = fachada.obterPrincipalCategoriaImovel(matriculaImovel);
		String inscricaoImovel = fachada.pesquisarInscricaoImovel(matriculaImovel, true);

		// Detalhe do Relatório
		RelatorioCertidaoPositivaModelo1Bean relatorioBean = null;

		// Contas
		Iterator iteratorAnosExercicioConta = mapAnoReferenciaExercicioContas.keySet().iterator();
		String linhaAnoMesesExercicio = "";

		while(iteratorAnosExercicioConta.hasNext()){

			relatorioBean = new RelatorioCertidaoPositivaModelo1Bean();

			relatorioBean.setMatriculaImovel(matriculaImovel.toString());
			relatorioBean.setInscricaoImovel(inscricaoImovel);
			relatorioBean.setNomeCliente(clienteUsuarioImovel.getNome());
			relatorioBean.setEnderecoImovel(enderecoImovel);
			relatorioBean.setCategoria(categoria.getDescricao());
			relatorioBean.setReferenciaAtual(Util.formatarAnoMesParaMesAno(Util.getAnoMesComoInteger(new Date())));
			relatorioBean.setValorPrincipal(Util.formatarMoedaReal(valorPrincipal, 2));
			relatorioBean.setValorAtualizacao(Util.formatarMoedaReal(valorAtualizacaoMonetaria, 2));
			relatorioBean.setValorJuros(Util.formatarMoedaReal(valorJuros, 2));
			relatorioBean.setValorMulta(Util.formatarMoedaReal(valorMulta, 2));
			relatorioBean.setValorDivida(Util.formatarMoedaReal(valorDivida, 2));

			anoDebito = (String) iteratorAnosExercicioConta.next();
			linhaAnoMesesExercicio = anoDebito + " Meses: ";
			colecaoMesesExercicio = mapAnoReferenciaExercicioContas.get(anoDebito);

			for(String mesExercicioDebito : colecaoMesesExercicio){

				linhaAnoMesesExercicio += mesExercicioDebito + ",";
			}

			linhaAnoMesesExercicio = linhaAnoMesesExercicio.substring(0, linhaAnoMesesExercicio.length() - 1);

			relatorioBean.setIndicadorConta("1");
			relatorioBean.setIndicadorContaOrdenacao(1);
			relatorioBean.setAnoMesesExercicioContasOuGuias(linhaAnoMesesExercicio);
			relatorioBean.setAnoOrdenacao(Util.obterInteger(anoDebito));

			colecaoRelatorioBeans.add(relatorioBean);
		}

		// Guias
		Iterator iteratorAnosExercicioGuias = mapAnoReferenciaExercicioGuias.keySet().iterator();
		linhaAnoMesesExercicio = "";

		while(iteratorAnosExercicioGuias.hasNext()){
			
			relatorioBean = new RelatorioCertidaoPositivaModelo1Bean();

			relatorioBean.setMatriculaImovel(matriculaImovel.toString());
			relatorioBean.setInscricaoImovel(inscricaoImovel);
			relatorioBean.setNomeCliente(clienteUsuarioImovel.getNome());
			relatorioBean.setEnderecoImovel(enderecoImovel);
			relatorioBean.setCategoria(categoria.getDescricao());
			relatorioBean.setReferenciaAtual(Util.formatarAnoMesParaMesAno(Util.getAnoMesComoInteger(new Date())));
			relatorioBean.setValorPrincipal(Util.formatarMoedaReal(valorPrincipal, 2));
			relatorioBean.setValorAtualizacao(Util.formatarMoedaReal(valorAtualizacaoMonetaria, 2));
			relatorioBean.setValorJuros(Util.formatarMoedaReal(valorJuros, 2));
			relatorioBean.setValorMulta(Util.formatarMoedaReal(valorMulta, 2));
			relatorioBean.setValorDivida(Util.formatarMoedaReal(valorDivida, 2));

			anoDebito = (String) iteratorAnosExercicioGuias.next();
			linhaAnoMesesExercicio = anoDebito + " Meses: ";
			colecaoMesesExercicio = mapAnoReferenciaExercicioGuias.get(anoDebito);

			for(String mesExercicioDebito : colecaoMesesExercicio){

				linhaAnoMesesExercicio += mesExercicioDebito + ",";
			}

			linhaAnoMesesExercicio = linhaAnoMesesExercicio.substring(0, linhaAnoMesesExercicio.length() - 1);

			relatorioBean.setAnoMesesExercicioContasOuGuias(linhaAnoMesesExercicio);
			relatorioBean.setIndicadorConta("2");
			relatorioBean.setIndicadorContaOrdenacao(2);
			relatorioBean.setAnoOrdenacao(Util.obterInteger(anoDebito));

			colecaoRelatorioBeans.add(relatorioBean);
		}
		
		if(colecaoRelatorioBeans.isEmpty()){
			throw new ActionServletException("atencao.nao_ha_dados_geracao_relatorio");
		}

		List sortFields = new ArrayList();
		sortFields.add(new BeanComparator("indicadorContaOrdenacao"));
		sortFields.add(new BeanComparator("anoOrdenacao"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort(colecaoRelatorioBeans, multiSort);

		Iterator iteratorBeansRelatorio = colecaoRelatorioBeans.iterator();
		boolean primeiraLinhaConta = true;
		boolean primeiraLinhaGuia = true;

		while(iteratorBeansRelatorio.hasNext()){

			RelatorioCertidaoPositivaModelo1Bean beanHelper = (RelatorioCertidaoPositivaModelo1Bean) iteratorBeansRelatorio.next();

			if(beanHelper.getIndicadorContaOrdenacao().intValue() == 1 && primeiraLinhaConta){

				beanHelper.setExercicio("Exercício:");
				primeiraLinhaConta = false;
			}

			if(beanHelper.getIndicadorContaOrdenacao().intValue() == 2 && primeiraLinhaGuia){

				beanHelper.setExercicio("Exercício:");
				primeiraLinhaGuia = false;
			}
		}


		// Parâmetros do relatório
		Map parametros = new HashMap();
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		try{

			parametros.put("enderecoEmpresa", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){

			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		parametros.put("telefone", sistemaParametro.getNumeroTelefone());
		parametros.put("CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("CEP", sistemaParametro.getCep().getCepFormatado());
		parametros.put("textoRodape",
						"Sorocaba, " + Util.completarStringZeroEsquerda("" + Util.getDiaMes(new Date()), 2) + " de "
										+ Util.retornaDescricaoMes(Util.getMes(new Date())) + " de " + Util.getAno(new Date()));
		parametros.put("usuarioEmissor", usuarioEmissor);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(colecaoRelatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CERTIDAO_POSITIVA_MODELO_1, parametros, ds, tipoFormatoRelatorio);

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCertidaoPositivaModelo1", this);
	}
}