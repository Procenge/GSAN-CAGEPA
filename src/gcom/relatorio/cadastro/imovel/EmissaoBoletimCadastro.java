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

package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Este caso de uso permite a emiss�o de boletins de cadastro
 * [UC0582] Emitir Boletim de Cadastro pelo
 * [UC0164] - Filtrar Im�vel por Outros Crit�rios
 * 
 * @author Anderson Italo
 * @date 20/04/2011
 */
public class EmissaoBoletimCadastro
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public EmissaoBoletimCadastro(Usuario usuario) {

		super(usuario, ConstantesRelatorios.BOLETIM_CADASTRO);
	}

	@Deprecated
	public EmissaoBoletimCadastro() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		FiltrarRelatorioBoletimCadastroHelper filtro = (FiltrarRelatorioBoletimCadastroHelper) getParametro("filtrarRelatorioBoletimCadastroHelper");
		byte[] retorno = null;
		Fachada fachada = Fachada.getInstancia();
		Collection<RelatorioBoletimCadastralModelo2Bean> colecaoRelatorioBeans = null;
		try{

			if(ConstantesSistema.BOLETIM_CADASTRAL_MODELO_1.equals(ParametroCadastro.P_LAYOUT_BOLETIM_CADASTRAL.executar())){
				retorno = fachada.emitirBoletimCadastro(filtro);
			}else{

				// Par�metros do relat�rio
				Map<String, String> parametros = new HashMap();

				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				parametros.put("imagem", sistemaParametro.getImagemRelatorio());
				parametros.put("imagemConta", sistemaParametro.getImagemConta());
				parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
				parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
				parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
				parametros.put("P_INSC_EST",
								(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));

				colecaoRelatorioBeans = fachada.consultarDadosBoletimCadastralModelo2(filtro);

				int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

				if(colecaoRelatorioBeans == null || colecaoRelatorioBeans.isEmpty()){
					// N�o existem dados para a exibi��o do relat�rio.
					throw new RelatorioVazioException("atencao.relatorio.vazio");
				}

				RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoRelatorioBeans);

				retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_BOLETIM_CADASTRO_MODELO_2, parametros, ds,
								tipoFormatoRelatorio);

			}

			persistirRelatorioConcluido(retorno, Relatorio.BOLETIM_CADASTRO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}catch(NegocioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		FiltrarRelatorioBoletimCadastroHelper filtro = (FiltrarRelatorioBoletimCadastroHelper) getParametro("filtrarRelatorioBoletimCadastroHelper");

		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioBoletimCadastro(filtro);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("EmissaoBoletimCadastro", this);
	}
}