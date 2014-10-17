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

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
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
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Classe responsável por criar o relatório de Certidão Negativa
 * 
 * @author Virgínia Melo
 * @date 06/02/2009
 */
public class RelatorioCertidaoNegativaModelo1
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioCertidaoNegativaModelo1(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_1);
	}

	@Deprecated
	public RelatorioCertidaoNegativaModelo1() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioCertidaoNegativaBean relatorioBean = null;

		// Obter dados do imovel
		String assuntoDescricao = "Pagamento de contas de água/coleta de esgoto";
		String nomeCliente = (String) getParametro("nomeCliente");
		String enderecoCliente = (String) getParametro("enderecoCliente");
		String periodoInicial = (String) getParametro("periodoInicial");
		String periodoFinal = (String) getParametro("periodoFinal");
		String inscricao = (String) getParametro("inscricaoImovel");
		String matricula = (String) getParametro("matriculaImovel");
		String hidrometro = (String) getParametro("numeroHidrometro");
		String nomeUsuario = (String) getParametro("nomeUsuario");
		String emissorUsuario = (String) getParametro("emissorUsuario").toString();
		String empresa = (String) getParametro("empresa");

		// Montar o Helper
		relatorioBean = new RelatorioCertidaoNegativaBean(assuntoDescricao, nomeCliente, enderecoCliente, periodoInicial, periodoFinal,
						inscricao, Util.adicionarZerosEsquedaNumero(9, matricula), hidrometro, nomeUsuario, emissorUsuario, empresa);

		// adiciona o bean a coleção
		relatorioBeans.add(relatorioBean);

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeUsuario", nomeUsuario);

		parametros.put("emissorUsuario", emissorUsuario);

		// Empresa
		if(sistemaParametro.getNomeEmpresa() != null){
			parametros.put("empresa", sistemaParametro.getNomeEmpresa());

		}else{
			parametros.put("empresa", "");
		}
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
		}catch(NegocioException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		// Endereco Empresa
		String enderecoEmpresa = "";

		boolean virgula = false;

		// Logradouro
		Logradouro logradouro = sistemaParametro.getLogradouro();
		if(logradouro != null){

			if(logradouro.getId() != null && !logradouro.getId().equals("")){

				FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
				filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, logradouro.getId()));
				Collection logradouros = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

				if(logradouros != null && !logradouros.isEmpty()){

					Iterator logradouroIterator = logradouros.iterator();
					logradouro = (Logradouro) logradouroIterator.next();

					if(logradouro.getNome() != null && !logradouro.getNome().equals("")){
						enderecoEmpresa = logradouro.getNome();
						virgula = true;
					}
				}
			}
		}

		// Adiciona o número
		if(sistemaParametro.getNumeroImovel() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
			virgula = true;
		}

		// Adiciona o complemento
		if(sistemaParametro.getComplementoEndereco() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
			virgula = true;
		}

		// Adiciona o bairro
		if(sistemaParametro.getBairro() != null){

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, sistemaParametro.getBairro().getId()));
			Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

			if(colecaoBairro != null && !colecaoBairro.isEmpty()){
				for(Object object : colecaoBairro){
					enderecoEmpresa += (virgula == true ? ", " : "") + ((Bairro) object).getNome();
					virgula = true;
				}
			}
		}

		// Adiciona o cep
		if(sistemaParametro.getCep() != null){

			Integer idCep = sistemaParametro.getCep().getCepId();
			// Busca o cep
			FiltroCep filtroCep = new FiltroCep();
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, idCep));
			Collection colecaoCep = fachada.pesquisar(filtroCep, Cep.class.getName());

			if(colecaoCep != null && !colecaoCep.isEmpty()){
				for(Object object : colecaoCep){
					enderecoEmpresa += (virgula == true ? ", Cep: " : "") + ((Cep) object).getCepFormatado();
				}
			}
		}

		try{

			String parametroSiteEmpresa = ParametroCadastro.P_SITE_EMPRESA.executar();
			parametros.put("siteEmpresa", parametroSiteEmpresa);
		}catch(ControladorException e){

			e.printStackTrace();
		}

		parametros.put("enderecoEmpresa", enderecoEmpresa);
		parametros.put("email", sistemaParametro.getDsEmailResponsavel());
		parametros.put("telefone", sistemaParametro.getNumeroTelefone());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_1, parametros, ds, tipoFormatoRelatorio);

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCertidaoNegativaModelo1", this);
	}
}