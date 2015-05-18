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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.relatorio.cobranca;

import gcom.cadastro.cliente.Cliente;
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

import java.util.*;

/**
 * Classe respons�vel por criar o relat�rio de Certid�o Negativa
 * 
 * @author Anderson Italo
 * @date 14/09/2014
 */
public class RelatorioCertidaoNegativaModelo4
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioCertidaoNegativaModelo4(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_4);
	}

	@Deprecated
	public RelatorioCertidaoNegativaModelo4() {

		super(null, "");
	}

	public Object executar() throws TarefaException{


		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer matriculaImovel = (Integer) getParametro("matriculaImovel");
		String usuarioEmissor = (String) getParametro("usuarioEmissor");
		String enderecoFormatado = (String) getParametro("enderecoFormatado");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		Cliente clienteUsuarioImovel = fachada.pesquisarClienteUsuarioImovel(matriculaImovel);

		String inscricaoImovel = fachada.pesquisarInscricaoImovel(matriculaImovel, true);

		// cole��o de beans do relat�rio
		List colecaoRelatorioBeans = new ArrayList();

		RelatorioCertidaoNegativaModelo4Bean bean = new RelatorioCertidaoNegativaModelo4Bean();
		bean.setMatriculaImovel(matriculaImovel.toString());
		bean.setInscricaoImovel(inscricaoImovel);
		bean.setNomeCliente(clienteUsuarioImovel.getNome());
		bean.setCodigoCliente(clienteUsuarioImovel.getId().toString());
		bean.setEnderecoImovel(enderecoFormatado);
		colecaoRelatorioBeans.add(bean);

		// Par�metros do relat�rio
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

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(colecaoRelatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CERTIDAO_NEGATIVA_MODELO_4, parametros, ds, tipoFormatoRelatorio);

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCertidaoNegativaModelo4", this);
	}
}