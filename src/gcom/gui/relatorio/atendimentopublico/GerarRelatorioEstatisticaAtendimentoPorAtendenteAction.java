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
 * Ivan S�rgio Virginio da Silva J�nior
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

package gcom.gui.relatorio.atendimentopublico;

import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioEstatisticaAtendimentoPorAtendente;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @created 23/03/2011
 */
public class GerarRelatorioEstatisticaAtendimentoPorAtendenteAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm formulario = (GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		boolean parametroInformado = false;
		FiltrarRegistroAtendimentoHelper filtroRA = new FiltrarRegistroAtendimentoHelper();

		// Situa��o
		if(!Util.isVazioOuBranco(formulario.getSituacao())){
			filtroRA.setCodigoSituacao(Short.valueOf(formulario.getSituacao()));
			parametroInformado = true;
		}

		// Tipo Solicita��o
		Collection<Integer> colecaoSolicitacaoTipoSolicitacao = new ArrayList<Integer>();
		if(formulario.getTipoSolicitacao() != null && formulario.getTipoSolicitacao().length > 0){

			String[] tipoSolicitacao = formulario.getTipoSolicitacao();
			for(int i = 0; i < tipoSolicitacao.length; i++){

				if(!Util.isVazioOuBranco(tipoSolicitacao[i])){

					colecaoSolicitacaoTipoSolicitacao.add(Integer.valueOf(tipoSolicitacao[i]));
					parametroInformado = true;
				}
			}

			filtroRA.setColecaoTipoSolicitacao(colecaoSolicitacaoTipoSolicitacao);
		}

		// Tipo Especifica��o
		Collection<Integer> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		if(colecaoSolicitacaoTipoSolicitacao.size() < 2 && formulario.getEspecificacao() != null
						&& formulario.getEspecificacao().length > 0){

			String[] tipoSolicitacaoEspecificacao = formulario.getEspecificacao();
			for(int i = 0; i < tipoSolicitacaoEspecificacao.length; i++){

				if(!Util.isVazioOuBranco(tipoSolicitacaoEspecificacao[i])){

					colecaoSolicitacaoTipoEspecificacao.add(Integer.valueOf(tipoSolicitacaoEspecificacao[i]));
					parametroInformado = true;
				}
			}

			filtroRA.setColecaoTipoSolicitacaoEspecificacao(colecaoSolicitacaoTipoEspecificacao);
		}

		// Data de Atendimento
		Date dataAtendimentoInicial = null;
		Date dataAtendimentoFinal = null;
		if(Util.isVazioOuBranco(formulario.getPeriodoAtendimentoInicial())){

			if(!Util.isVazioOuBranco(formulario.getPeriodoAtendimentoFinal())){

				dataAtendimentoFinal = Util.converteStringParaDate(formulario.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util.formatarDataFinal(dataAtendimentoFinal);
				dataAtendimentoInicial = Util.converteStringParaDate("01/01/1900");
				dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);
				parametroInformado = true;
			}

		}else{

			dataAtendimentoInicial = Util.converteStringParaDate(formulario.getPeriodoAtendimentoInicial());
			dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);
			dataAtendimentoFinal = null;

			if(Util.isVazioOuBranco(formulario.getPeriodoAtendimentoFinal())){
				dataAtendimentoFinal = new Date();
				dataAtendimentoFinal = Util.formatarDataFinal(dataAtendimentoFinal);
			}else{
				dataAtendimentoFinal = Util.converteStringParaDate(formulario.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtendimentoFinal);
			}

			// Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataAtendimentoInicial, dataAtendimentoFinal);

			if(qtdeDias < 0){

				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}

			parametroInformado = true;
		}

		filtroRA.setDataAtendimentoInicial(dataAtendimentoInicial);
		filtroRA.setDataAtendimentoFinal(dataAtendimentoFinal);

		// Data de Encerramento
		Date dataEncerramentoInicial = null;
		Date dataEncerramentoFinal = null;
		if(Util.isVazioOuBranco(formulario.getPeriodoEncerramentoInicial())){

			if(!Util.isVazioOuBranco(formulario.getPeriodoEncerramentoFinal())){

				dataEncerramentoFinal = Util.converteStringParaDate(formulario.getPeriodoEncerramentoFinal());
				dataAtendimentoFinal = Util.formatarDataFinal(dataEncerramentoFinal);
				dataEncerramentoInicial = Util.converteStringParaDate("01/01/1900");
				dataEncerramentoInicial = Util.formatarDataInicial(dataEncerramentoInicial);
				parametroInformado = true;
			}
		}else{

			dataEncerramentoInicial = Util.converteStringParaDate(formulario.getPeriodoEncerramentoInicial());
			dataEncerramentoInicial = Util.formatarDataInicial(dataEncerramentoInicial);
			dataEncerramentoFinal = null;

			if(Util.isVazioOuBranco(formulario.getPeriodoEncerramentoFinal())){

				dataEncerramentoFinal = new Date();
				dataEncerramentoFinal = Util.formatarDataInicial(dataEncerramentoFinal);
			}else{

				dataEncerramentoFinal = Util.converteStringParaDate(formulario.getPeriodoEncerramentoFinal());
				dataEncerramentoFinal = Util.adaptarDataFinalComparacaoBetween(dataEncerramentoFinal);
			}

			// [FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataEncerramentoInicial, dataEncerramentoFinal);

			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}

			parametroInformado = true;
		}

		filtroRA.setDataEncerramentoInicial(dataEncerramentoInicial);
		filtroRA.setDataEncerramentoFinal(dataEncerramentoFinal);

		// Unidade de Atendimento
		Collection<Integer> colecaoUnidadeAtendimento = new ArrayList();
		if(formulario.getUnidadeAtendimento() != null && formulario.getUnidadeAtendimento().length > 0){

			String[] unidadeAtendimento = formulario.getUnidadeAtendimento();
			for(int i = 0; i < unidadeAtendimento.length; i++){

				if(!unidadeAtendimento[i].equals("")){

					colecaoUnidadeAtendimento.add(Integer.valueOf(unidadeAtendimento[i]));
					parametroInformado = true;
				}
			}

			filtroRA.setColecaoUnidadeAtendimento(colecaoUnidadeAtendimento);
		}

		// Unidade de Atual
		Collection<Integer> colecaoUnidadeAtual = new ArrayList();
		if(formulario.getUnidadeAtual() != null && formulario.getUnidadeAtual().length > 0){

			String[] unidadeAtual = formulario.getUnidadeAtual();
			for(int i = 0; i < unidadeAtual.length; i++){

				if(!unidadeAtual[i].equals("")){

					colecaoUnidadeAtual.add(Integer.valueOf(unidadeAtual[i]));
					parametroInformado = true;
				}
			}

			filtroRA.setColecaoUnidadeAtual(colecaoUnidadeAtual);
		}

		// Unidade Superior
		UnidadeOrganizacional unidadeSuperior = null;
		Collection<Integer> colecaoUnidadesSubordinadas = new ArrayList();
		if(formulario.getUnidadeSuperiorId() != null && !formulario.getUnidadeSuperiorId().equals("")){

			unidadeSuperior = new UnidadeOrganizacional();
			unidadeSuperior.setId(Integer.valueOf(formulario.getUnidadeSuperiorId()));

			// Verificar exist�ncia de unidades subordinadas
			fachada.verificarExistenciaUnidadesSubordinadas(unidadeSuperior);

			colecaoUnidadesSubordinadas.add(unidadeSuperior.getId());
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.setCampoOrderBy(FiltroUnidadeOrganizacional.ID);
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID_UNIDADE_SUPERIOR,
							unidadeSuperior.getId()));

			Collection colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			for(Iterator iterator = colecaoUnidade.iterator(); iterator.hasNext();){
				UnidadeOrganizacional unidadeSubordinada = (UnidadeOrganizacional) iterator.next();

				colecaoUnidadesSubordinadas.add(unidadeSubordinada.getId());
			}

			filtroRA.setUnidadeSuperior(unidadeSuperior);
			filtroRA.setColecaoUnidadesSubordinadas(colecaoUnidadesSubordinadas);
			parametroInformado = true;
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!parametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		RelatorioEstatisticaAtendimentoPorAtendente relatorio = new RelatorioEstatisticaAtendimentoPorAtendente(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorio.addParametro("filtroRA", filtroRA);

		String tipoRelatorio = httpServletRequest.getParameter("escolhaTipoRelatorio");

		if(Util.isVazioOuBranco(tipoRelatorio)){
			throw new ActionServletException("atencao.selecionar.opcao.relatorio");
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}
}
