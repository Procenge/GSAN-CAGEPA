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

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
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
 * Classe responsável por criar o relatório de Certidão Negativa
 * 
 * @author Felipe Rosacruz
 * @date 21/02/2014
 */
public class RelatorioCertidaoNegativaModelo3
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioCertidaoNegativaModelo3(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_3);
	}

	@Deprecated
	public RelatorioCertidaoNegativaModelo3() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		byte[] retorno = null;
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioCertidaoNegativaBean relatorioBean = new RelatorioCertidaoNegativaBean();

		// Obter dados do imovel
		String nomeCliente = (String) getParametro("nomeCliente");
		String enderecoCliente = (String) getParametro("enderecoCliente");
		String inscricao = (String) getParametro("inscricaoImovel");
		String matricula = (String) getParametro("matriculaImovel");
		String nomeUsuario = (String) getParametro("nomeUsuario");
		String emissorUsuario = (String) getParametro("emissorUsuario").toString();
		String cidade = (String) getParametro("cidade");

		// Montagem do bean
		relatorioBean.setNomeCliente(nomeCliente);
		relatorioBean.setEnderecoCliente(enderecoCliente);
		relatorioBean.setInscricaoImovel(inscricao);
		relatorioBean.setMatriculaImovel(Util.adicionarZerosEsquedaNumero(9, matricula));
		relatorioBean.setCidade(cidade);

		relatorioBeans.add(relatorioBean);

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeUsuario", nomeUsuario);

		parametros.put("emissorUsuario", emissorUsuario);

		parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());

		parametros.put("municipioEmpresa", sistemaParametro.getCep().getMunicipio());

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		String msg = "Declaramos que no arquivo de contas a receber da CAGEPA, NÃO CONSTA nesta data, débito referente ao consumo de "
						+ "água e/ou serventia de esgoto, serviços, multas, etc, tem validade por 30 (trinta) dias e exclui contas ainda não vencidas,"
						+ " novas faturas, parcelamentos, financiamentos e serviços ou multas a cobrar.";
		parametros.put("msg", msg);

		String dataPorExtenso = "";
		Calendar dataCalendar = new GregorianCalendar();

		Integer ano = dataCalendar.get(Calendar.YEAR);
		Integer mes = dataCalendar.get(Calendar.MONTH) + 1;
		Integer dia = dataCalendar.get(Calendar.DAY_OF_MONTH);

		String diaStr = Integer.toString(dia);
		diaStr = Util.adicionarZerosEsquedaNumero(2, diaStr);

		dataPorExtenso = sistemaParametro.getCep().getMunicipio() + ", " + diaStr + " DE " + Util.retornaDescricaoMes(mes).toUpperCase()
						+ " DE " + ano + ".";
		parametros.put("dataPorExtenso", dataPorExtenso);

		// Cria uma instancia do DataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_3, parametros, ds, tipoFormatoRelatorio);

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCertidaoNegativaModelo3", this);
	}
}