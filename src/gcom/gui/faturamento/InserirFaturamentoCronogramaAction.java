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

package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Flávio Cordeiro
 */
public class InserirFaturamentoCronogramaAction
				extends GcomAction {

	/**
	 * Este Action cuida da montagem de objetos baseados nos dados fornecidos
	 * pelo usuario faz alguma validações e envia os objetos(FaturamentoGrupoCronogramaMensal,
	 * FaturamentoAtividadeCronogra)
	 * para inserção, onde será feito a validação, parte de negocio, e inserido o objeto.
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		FaturamentoActionForm faturamentoActionForm = (FaturamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio
		// String testeFaturamentoNaoObrigatorio = "0";

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		String idGrupo = faturamentoActionForm.getIdGrupoFaturamento();
		String mesAno = faturamentoActionForm.getMesAno();
		Integer quantidadeCronogramas = 1;

		if(!Util.isVazioOuBranco(faturamentoActionForm.getQuantidadeCronogramas())){

			quantidadeCronogramas = Util.obterInteger(faturamentoActionForm.getQuantidadeCronogramas());
		}

		Collection faturamentoAtividades = (Collection) sessao.getAttribute("faturamentoAtividades");
		Collection faturamentoAtividadeCronogramas = new ArrayList();
		Collection colecaoTestePredecessoraVazia = new ArrayList();
		Iterator iterateFaturamentoAtividades = faturamentoAtividades.iterator();
		FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = null;

		// Instancia objeto FaturamenatoGrupo
		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
		faturamentoGrupo.setId(new Integer(idGrupo));
		faturamentoGrupo = (FaturamentoGrupo) fachada.pesquisar(faturamentoGrupo.getId(), FaturamentoGrupo.class);

		while(iterateFaturamentoAtividades.hasNext()){
			faturamentoAtividadeCronograma = new FaturamentoAtividadeCronograma();
			faturamentoAtividadeCronograma.setFaturamentoAtividade((FaturamentoAtividade) iterateFaturamentoAtividades.next());
			String dataPrevista = (String) httpServletRequest.getParameter("n"
							+ faturamentoAtividadeCronograma.getFaturamentoAtividade().getId().toString());
			SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

			try{
				if(!dataPrevista.trim().equalsIgnoreCase("")){
					faturamentoAtividadeCronograma.setDataPrevista(data.parse(dataPrevista));
					faturamentoAtividadeCronogramas.add(faturamentoAtividadeCronograma);

					// guarda objetos FaturamentoAtividadeCronograma com ou sem data prevista
					colecaoTestePredecessoraVazia.add(faturamentoAtividadeCronograma);
				}else if(faturamentoAtividadeCronograma.getFaturamentoAtividade().getIndicadorObrigatoriedadeAtividade().intValue() == 1
								&& dataPrevista.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.campos_obrigatorios_cronograma_nao_preenchidos");
				}else if(faturamentoAtividadeCronograma.getFaturamentoAtividade().getIndicadorObrigatoriedadeAtividade().intValue() == 1){
					// testeFaturamentoNaoObrigatorio = "1";

					// guarda objetos FaturamentoAtividadeCronograma com ou sem data prevista
					colecaoTestePredecessoraVazia.add(faturamentoAtividadeCronograma);
				}else if(faturamentoAtividadeCronograma.getFaturamentoAtividade().getIndicadorObrigatoriedadeAtividade().intValue() == ConstantesSistema.INDICADOR_USO_DESATIVO
								&& dataPrevista.trim().equalsIgnoreCase("")){
					// guarda objetos FaturamentoAtividadeCronograma com ou sem data prevista
					colecaoTestePredecessoraVazia.add(faturamentoAtividadeCronograma);

					iterateFaturamentoAtividades.remove();
				}

				// se foi marcado o check de comandar a atividade
				// sera atribuido o valor de comando
				// este valor será igual ao da data prevista
				String comandar = (String) httpServletRequest.getParameter("c"
								+ faturamentoAtividadeCronograma.getFaturamentoAtividade().getId().toString());

				if(comandar != null && !comandar.trim().equals("") && faturamentoAtividadeCronograma != null && dataPrevista != null
								&& !dataPrevista.trim().equals("")){
					faturamentoAtividadeCronograma.setComando(data.parse(dataPrevista));
				}
			}catch(ParseException ex){
				throw new ActionServletException("atencao.cobranca.data_prevista_mes_ano_menor_sem_parametros");
			}

		}

		/*
		 * if (faturamentoAtividadeCronogramas.size() < 5
		 * || (faturamentoAtividadeCronogramas.size() == 5 && testeFaturamentoNaoObrigatorio
		 * .trim().equalsIgnoreCase("0"))) {
		 * throw new ActionServletException(
		 * "atencao.campos_obrigatorios_cronograma_nao_preenchidos");
		 * }
		 */

		// Instancia objeto FaturamentoGrupoCronogramaMensal
		FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal = new FaturamentoGrupoCronogramaMensal();

		// Seta o objeto FaturamentoGrupo no objeto
		// FaturamentoGrupoCronogramaMensal
		faturamentoGrupoCronogramaMensal.setFaturamentoGrupo(faturamentoGrupo);

		// Concatena ano mes para insercao
		String mes = mesAno.substring(0, 2);
		String ano = mesAno.substring(3, 7);

		mesAno = ano + "" + mes;
		// Seta o mes ano no objeto FaturamentoGrupoCronogramaMensal
		faturamentoGrupoCronogramaMensal.setAnoMesReferencia(new Integer(mesAno));

		// validar as datas e seguencias do crongrama
		fachada.validarFaturamentoCronogramaAtividadeMaiorQueMesAnoCronograma((new Integer(mesAno)).intValue(),
						colecaoTestePredecessoraVazia);
		fachada.validarFaturamentoCronograma(colecaoTestePredecessoraVazia);

		String referenciasInseridas = fachada.inserirFaturamentoGrupoCronogramaMensal(faturamentoGrupoCronogramaMensal,
						faturamentoAtividadeCronogramas, usuarioLogado,
						quantidadeCronogramas);



		montarPaginaSucesso(httpServletRequest, "Cronograma de Faturamento do grupo " + faturamentoGrupo.getDescricao()
						+ " referente ao(s) mês(es)/ano(s): " + referenciasInseridas + " inserido(s) com sucesso.",
						"Inserir outro Cronograma de Faturamento.", "exibirInserirFaturamentoCronogramaAction.do");

		sessao.removeAttribute("faturamentoAtividades");

		sessao.setAttribute("voltar", "filtrar");

		return retorno;
	}

}
