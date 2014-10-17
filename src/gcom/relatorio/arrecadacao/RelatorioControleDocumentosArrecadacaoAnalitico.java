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

package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.ArrecadacaoDadosDiarios;
import gcom.arrecadacao.ArrecadacaoForma;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Descrição da classe
 * 
 * @author Genival Barbosa
 * @date 26/07/2011
 */
public class RelatorioControleDocumentosArrecadacaoAnalitico
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RelatorioControleDocumentosArrecadacaoAnalitico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO);
	}

	@Deprecated
	public RelatorioControleDocumentosArrecadacaoAnalitico() {

		super(null, "");
	}

	private Collection inicializarBeanRelatorio(Collection<ArrecadacaoDadosDiarios> dadosRelatorio){

		Map<String, RelatorioControleDocumentosArrecadacaoAnaliticoBean> retorno = new HashMap<String, RelatorioControleDocumentosArrecadacaoAnaliticoBean>();

		Iterator iteratordadosRelatorio = dadosRelatorio.iterator();

		while(iteratordadosRelatorio.hasNext()){
			Object[] dadoConsulta = (Object[]) iteratordadosRelatorio.next();

			RelatorioControleDocumentosArrecadacaoAnaliticoBean dado;
			String dia = String.valueOf(Util.getDiaMes((Date) dadoConsulta[2]));

			if(retorno.containsKey(dia + "-" + dadoConsulta[3].toString())){
				dado = retorno.get(dia + "-" + dadoConsulta[3].toString());
			}else{
				dado = new RelatorioControleDocumentosArrecadacaoAnaliticoBean();
			}

			if(dadoConsulta[0] != null){
				dado.setIdArrecadacaoForma(dadoConsulta[0].toString());
			}

			if(dadoConsulta[1] != null){
				dado.setDescricaoArrecadacaoForma(dadoConsulta[1].toString());
			}

			if(dadoConsulta[2] != null){
				dado.setDia(dia);
			}

			if(dado.getIdArrecadacaoForma().equals(ArrecadacaoForma.DEBITO_AUTOMATICO.toString())){
				if(dadoConsulta[5] != null){
					if(dado.getQuantidadeDebitoAutomatico() != null){
						dado
										.setQuantidadeDebitoAutomatico(dado.getQuantidadeDebitoAutomatico()
														+ (new Integer(dadoConsulta[5].toString())));
					}else{
						dado.setQuantidadeDebitoAutomatico(new Integer(dadoConsulta[5].toString()));
					}
				}
				if(dadoConsulta[6] != null){
					if(dado.getValorDebitosAutomatico() != null){
						dado.setValorDebitosAutomatico(dado.getValorDebitosAutomatico().add(new BigDecimal(dadoConsulta[6].toString())));
					}else{
						dado.setValorDebitosAutomatico(new BigDecimal(dadoConsulta[6].toString()));
					}
				}
			}else if(dado.getIdArrecadacaoForma().equals(ArrecadacaoForma.ARRECADACAO_ELETRONICA.toString())){
				if(dadoConsulta[5] != null){
					if(dado.getQuantidadeCartao() != null){
						dado.setQuantidadeCartao(dado.getQuantidadeCartao() + (new Integer(dadoConsulta[5].toString())));
					}else{
						dado.setQuantidadeCartao(new Integer(dadoConsulta[5].toString()));
					}
				}
				if(dadoConsulta[6] != null){
					if(dado.getValorCartao() != null){
						dado.setValorCartao(dado.getValorCartao().add(new BigDecimal(dadoConsulta[6].toString())));
					}else{
						dado.setValorCartao(new BigDecimal(dadoConsulta[6].toString()));
					}
				}
			}else{
				if(dadoConsulta[5] != null){
					if(dado.getQuantidadeOutros() != null){
						dado.setQuantidadeOutros(dado.getQuantidadeOutros() + (new Integer(dadoConsulta[5].toString())));
					}else{
						dado.setQuantidadeOutros(new Integer(dadoConsulta[5].toString()));
					}
				}
				if(dadoConsulta[6] != null){
					if(dado.getValorOutros() != null){
						dado.setValorOutros(dado.getValorOutros().add(new BigDecimal(dadoConsulta[6].toString())));
					}else{
						dado.setValorOutros(new BigDecimal(dadoConsulta[6].toString()));
					}
				}
			}

			if(dadoConsulta[3] != null){
				dado.setIdArrecadador(dadoConsulta[3].toString());
			}
			if(dadoConsulta[4] != null){
				dado.setDescricaoArrecadador(dadoConsulta[4].toString());
			}
			if(dadoConsulta[7] != null){
				dado.setDescricaoArrecadador(dadoConsulta[7].toString() + " - " + dado.getDescricaoArrecadador());
			}

			retorno.put(dia + "-" + dadoConsulta[3].toString(), dado);
		}

		return retorno.values();
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

		Collection dadosRelatorio = (Collection) getParametro("colecaoArrecadacaoDadosDiarios");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String mesAnoArrecadacao = (String) getParametro("mesAnoArrecadacao");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("mesAnoArrecadacao", mesAnoArrecadacao);

		parametros.put("tipoFormatoRelatorio", "O0610");

		Collection<RelatorioControleDocumentosArrecadacaoAnaliticoBean> colecaoBean = new ArrayList<RelatorioControleDocumentosArrecadacaoAnaliticoBean>(
						this.inicializarBeanRelatorio(dadosRelatorio));

		totalizarDia(colecaoBean);

		List<RelatorioControleDocumentosArrecadacaoAnaliticoBean> listaOrdenada = new ArrayList<RelatorioControleDocumentosArrecadacaoAnaliticoBean>(
						colecaoBean);

		Collections.sort(listaOrdenada, new Comparator<RelatorioControleDocumentosArrecadacaoAnaliticoBean>() {

			public int compare(RelatorioControleDocumentosArrecadacaoAnaliticoBean o1,
							RelatorioControleDocumentosArrecadacaoAnaliticoBean o2){

				if(o1.getIdArrecadador().compareTo(o2.getIdArrecadador()) == 0){
					Integer dia1 = Integer.parseInt(o1.getDia());
					Integer dia2 = Integer.parseInt(o2.getDia());
					return dia1 - dia2;
				}else{
					return o1.getIdArrecadador().compareTo(o2.getIdArrecadador());
				}
			};
		});

		colecaoBean = listaOrdenada;

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO, idFuncionalidadeIniciada,
							null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

	}

	private void totalizarDia(Collection<RelatorioControleDocumentosArrecadacaoAnaliticoBean> colecaoBean){

		for(RelatorioControleDocumentosArrecadacaoAnaliticoBean relatorio : colecaoBean){

			Integer totalQuantidade = 0;
			if(relatorio.getQuantidadeCartao() != null){
				totalQuantidade = totalQuantidade + relatorio.getQuantidadeCartao();
			}else{
				relatorio.setQuantidadeCartao(0);
			}
			if(relatorio.getQuantidadeDebitoAutomatico() != null){
				totalQuantidade = totalQuantidade + relatorio.getQuantidadeDebitoAutomatico();
			}else{
				relatorio.setQuantidadeDebitoAutomatico(0);
			}
			if(relatorio.getQuantidadeOutros() != null){
				totalQuantidade = totalQuantidade + relatorio.getQuantidadeOutros();
			}else{
				relatorio.setQuantidadeOutros(0);
			}
			relatorio.setTotalParcialQuantidade(totalQuantidade);

			BigDecimal totalValor = BigDecimal.ZERO;
			if(relatorio.getValorCartao() != null){
				totalValor = totalValor.add(relatorio.getValorCartao());
			}else{
				relatorio.setValorCartao(BigDecimal.ZERO);
			}
			if(relatorio.getValorDebitosAutomatico() != null){
				totalValor = totalValor.add(relatorio.getValorDebitosAutomatico());
			}else{
				relatorio.setValorDebitosAutomatico(BigDecimal.ZERO);
			}
			if(relatorio.getValorOutros() != null){
				totalValor = totalValor.add(relatorio.getValorOutros());
			}else{
				relatorio.setValorOutros(BigDecimal.ZERO);
			}
			relatorio.setTotalParcialValor(totalValor);
		}
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		retorno = ((Collection) getParametro("colecaoArrecadacaoDadosDiarios")).size();

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioControleDocumentosArrecadacaoAnalitico", this);
	}
}