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
 * [UC1015] Relat�rio Resumo de Ordens de Servi�o Pendentes
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

		// obt�m os par�metros passados
		PesquisarOrdemServicoHelper filtro = (PesquisarOrdemServicoHelper) getParametro("filtroRelatorioResumo");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		filtro.setNumeroPaginasPesquisa(-1);

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		Collection<OrdemServico> colecaoOrdemServico = Fachada.getInstancia().pesquisarOrdemServico(filtro);

		Collection<RelatorioResumoOrdensServicoPendentesHelper> colecaoDadosRelatorio = null;

		// vari�veis utilizadas no loop
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
			// Cabe�alho
			totalRegistros++;

			if(!iterator.hasNext()){

				// �ltima p�gina finaliza totalizando tudo
				bean.setTotalRegistros(totalRegistros);
			}

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB, parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
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