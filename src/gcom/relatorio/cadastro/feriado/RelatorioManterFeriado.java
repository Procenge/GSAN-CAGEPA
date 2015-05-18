/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.relatorio.cadastro.feriado;

import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.sistemaparametro.bean.FeriadoHelper;
import gcom.fachada.Fachada;
import gcom.gui.cadastro.sistemaparametro.FiltrarFeriadoActionForm;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import java.util.*;

/**
 * [UC0387] Manter Feriado
 * 
 * @author Anderson Italo
 * @date 15/04/2011
 */
public class RelatorioManterFeriado
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioManterFeriado(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_FERIADO_MANTER);
	}

	@Deprecated
	public RelatorioManterFeriado() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		FiltrarFeriadoActionForm filtro = (FiltrarFeriadoActionForm) getParametro("parametrosfiltroferiado");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Collection<FeriadoHelper> colecaoFeriados = (Collection) getParametro("colecaoFeriado");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterFeriadoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		for(FeriadoHelper feriado : colecaoFeriados){

			String tipo = "";
			if(feriado.getTipoFeriado().intValue() == FeriadoHelper.TIPO_NACIONAL) tipo = "Nacional";
			else if(feriado.getTipoFeriado().intValue() == FeriadoHelper.TIPO_ESTADUAL) tipo = "Estadual";
			else tipo = "Municipal";

			relatorioBean = new RelatorioManterFeriadoBean(feriado.getId().toString(), feriado.getDescricao(), feriado.getData(), tipo,
							feriado.getDescricaoMunicipio());

			relatorioBeans.add(relatorioBean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// Tipo
		if(Util.obterInteger(filtro.getIndicadorTipoFeriado()).intValue() == FeriadoHelper.TIPO_NACIONAL) parametros.put("tipoFeriado",
						"Nacional");
		else if(Util.obterInteger(filtro.getIndicadorTipoFeriado()).intValue() == FeriadoHelper.TIPO_NACIONAL) parametros.put(
						"tipoFeriado", "Municipal");
		else parametros.put("tipoFeriado", "Todos");

		// Municipio
		if(!Util.isVazioOuBranco(filtro.getIdMunicipio())){

			FiltroTabelaAuxiliarAbreviada filtroTabelaAuxiliarAbreviada = new FiltroTabelaAuxiliarAbreviada();
			filtroTabelaAuxiliarAbreviada.adicionarParametro(new ParametroSimples(FiltroTabelaAuxiliarAbreviada.ID, Util
							.obterInteger(filtro.getIdMunicipio())));

			Collection colecaoMunicipio = fachada.pesquisar(filtroTabelaAuxiliarAbreviada, Municipio.class.getName());

			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
			parametros.put("municipio", municipio.getDescricaoParaRegistroTransacao());
		}

		// Data do Feriado
		if(!Util.isVazioOuBranco(filtro.getDataFeriadoInicio()) && !Util.isVazioOuBranco(filtro.getDataFeriadoFim())){

			parametros.put("data", filtro.getDataFeriadoInicio() + " a " + filtro.getDataFeriadoFim());
		}

		// Descrição do Feriado
		if(!Util.isVazioOuBranco(filtro.getDescricaoFeriado())){

			parametros.put("descricao", filtro.getDescricaoFeriado());
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		try{
			parametros.put("P_NOME_EMPRESA_RELATORIO", ParametroGeral.P_NOME_EMPRESA_RELATORIO.executar());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e1);
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_FERIADO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}
}