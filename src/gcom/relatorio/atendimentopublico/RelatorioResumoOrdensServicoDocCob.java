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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.PesquisarOrdemServicoHelper;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class RelatorioResumoOrdensServicoDocCob
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoOrdensServicoDocCob(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB);
	}

	@Deprecated
	public RelatorioResumoOrdensServicoDocCob() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obtêm os parâmetros passados
		PesquisarOrdemServicoHelper filtro = (PesquisarOrdemServicoHelper) getParametro("filtroRelatorioResumo");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		filtro.setNumeroPaginasPesquisa(-1);

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		Collection<OrdemServico> colecaoOrdemServico = Fachada.getInstancia().pesquisarOrdemServico(filtro);

		Collection<RelatorioResumoOrdensServicoPendentesHelper> colecaoDadosRelatorio = null;

		// variáveis utilizadas no loop
		Categoria categoria = new Categoria();
		Imovel imovel = new Imovel();
		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = new HidrometroRelatorioOSHelper();
		Integer totalRegistros = 0;

		UnidadeOrganizacional unidadeAtual;
		for(Iterator iterator = colecaoOrdemServico.iterator(); iterator.hasNext();){

			OrdemServico dados = (OrdemServico) iterator.next();

			RelatorioResumoOrdensServicoDocCobBean bean = new RelatorioResumoOrdensServicoDocCobBean();

			bean.setDataEmissao(dados.getDataEmissao());
			bean.setDataGeracao(dados.getDataGeracao());
			bean.setOrdemServico(dados.getId().toString());
			bean.setTipoServico(dados.getServicoTipo().getId().toString() + " - " + dados.getServicoTipo().getDescricao());
			if(dados.getCobrancaDocumento() != null){
				bean.setNumeroDocumentoCobranca(dados.getCobrancaDocumento().getId().toString());
			}
			if(dados.getRegistroAtendimento() != null && dados.getRegistroAtendimento().getImovel() != null
							&& dados.getRegistroAtendimento().getImovel().getId() != null){
				bean.setImovel(dados.getRegistroAtendimento().getImovel().getId().toString());
			}else if(dados.getImovel() != null){
				bean.setImovel(dados.getImovel().getId().toString());
			}else if(dados.getCobrancaDocumento() != null && dados.getCobrancaDocumento().getImovel() != null){
				bean.setImovel(dados.getCobrancaDocumento().getImovel().getId().toString());
			}
			if(dados.getSituacaoDocumentoDebito() != null){
				bean.setSituacaoDocumentoCobranca(dados.getSituacaoDocumentoDebito());
			}

			unidadeAtual = fachada.obterUnidadeAtualOrdemServico(dados.getId());
			bean.setUnidadeAtual(unidadeAtual.getId().toString());
			// Cabeçalho
			totalRegistros++;

			if(!iterator.hasNext()){

				// última página finaliza totalizando tudo
				bean.setTotalRegistros(totalRegistros);
			}

			relatorioBeans.add(bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoOrdensServicoPendentes", this);
	}

}