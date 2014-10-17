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

package gcom.relatorio.operacional;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.SetorAbastecimento;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UCXXXX] Manter Setor Abastecimento
 * 
 * @author Péricles Tavares
 * @date 09/02/2011
 */
public class RelatorioManterSetorAbastecimento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioManterSetorAbastecimento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_SETOR_ABASTECIMENTO_MANTER);
	}

	@Deprecated
	public RelatorioManterSetorAbastecimento() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltroSetorAbastecimento filtroSetorAbastecimento = (FiltroSetorAbastecimento) getParametro("filtroSetorAbastecimento");
		SetorAbastecimento setorAbastecimentoParametros = (SetorAbastecimento) getParametro("setorAbastecimentoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterSetorAbastecimentoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL_LOCALIDADE);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO);
		Collection colecaoSetorAbastecimento = fachada.pesquisar(filtroSetorAbastecimento, SetorAbastecimento.class.getName());

		if(colecaoSetorAbastecimento != null && !colecaoSetorAbastecimento.isEmpty()){

			Iterator SetorAbastecimentoIterator = colecaoSetorAbastecimento.iterator();

			while(SetorAbastecimentoIterator.hasNext()){

				SetorAbastecimento setorAbastecimento = (SetorAbastecimento) SetorAbastecimentoIterator.next();

				String codigo = "";
				if(setorAbastecimento.getCodigoSetorAbastecimento() != null){
					codigo = setorAbastecimento.getCodigoSetorAbastecimento().toString();
				}

				String descricaoAbreviada = "";
				if(setorAbastecimento.getDescricaoAbreviada() != null && !setorAbastecimento.getDescricaoAbreviada().equals("")){
					descricaoAbreviada = setorAbastecimento.getDescricaoAbreviada();
				}
				String unidade = "";
				if(setorAbastecimento.getDistritoOperacional() != null){
					unidade = setorAbastecimento.getDistritoOperacional().getDescricao();
				}

				String localidade = "";
				if(setorAbastecimento.getDistritoOperacional() != null
								&& setorAbastecimento.getDistritoOperacional().getLocalidade() != null){
					localidade = setorAbastecimento.getDistritoOperacional().getLocalidade().getDescricaoComId();
				}

				String zonaAbastecimento = "";
				if(setorAbastecimento.getZonaAbastecimento() != null){
					zonaAbastecimento = setorAbastecimento.getZonaAbastecimento().getDescricaoComCodigo();
				}

				relatorioBean = new RelatorioManterSetorAbastecimentoBean(
				// Código
								codigo,
								// Descrição
								setorAbastecimento.getDescricao(),
								// Descrição Abreviada
								descricaoAbreviada,
								// Unidade Operacional
								unidade,
								// Localidade
								localidade,
								// Zona Abastecimento
								zonaAbastecimento);

				relatorioBeans.add(relatorioBean);
			}
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(setorAbastecimentoParametros.getCodigoSetorAbastecimento() != null
						&& setorAbastecimentoParametros.getCodigoSetorAbastecimento().intValue() > 0){

			parametros.put("codigo", setorAbastecimentoParametros.getCodigoSetorAbastecimento().toString());
		}

		if(setorAbastecimentoParametros.getDescricao() != null && !setorAbastecimentoParametros.getDescricao().equals("")){

			parametros.put("descricao", setorAbastecimentoParametros.getDescricao());
		}

		if(setorAbastecimentoParametros.getDescricaoAbreviada() != null && !setorAbastecimentoParametros.getDescricaoAbreviada().equals("")){

			parametros.put("descricaoAbreviada", setorAbastecimentoParametros.getDescricaoAbreviada());
		}

		if(setorAbastecimentoParametros.getDistritoOperacional() != null){

			parametros.put("unidadeOperacional", setorAbastecimentoParametros.getDistritoOperacional().getDescricao());
		}

		if(setorAbastecimentoParametros.getSistemaAbastecimento() != null){

			parametros.put("sistemaAbastecimento", setorAbastecimentoParametros.getSistemaAbastecimento().getDescricaoComId());
		}
		if(setorAbastecimentoParametros.getZonaAbastecimento() != null){

			parametros.put("zonaAbastecimento", setorAbastecimentoParametros.getZonaAbastecimento().getDescricaoComCodigo());
		}

		if(setorAbastecimentoParametros.getIndicadorUso() > 0){

			if(setorAbastecimentoParametros.getIndicadorUso() == 1){
				parametros.put("indicadorUso", "Ativo");
			}else{
				parametros.put("indicadorUso", "Inativo");
			}
		}else{
			parametros.put("indicadorUso", "Todos");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_SETOR_ABASTECIMENTO_MANTER, parametros, ds, tipoFormatoRelatorio);
		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_SETOR_ABASTECIMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterSetorAbastecimento", this);
	}

}