/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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

		// cole��o de beans do relat�rio
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

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
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

		// Descri��o do Feriado
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
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e1);
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_FERIADO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
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