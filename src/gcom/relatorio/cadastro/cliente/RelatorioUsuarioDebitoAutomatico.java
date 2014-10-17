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

package gcom.relatorio.cadastro.cliente;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
 * [UC0XXX] Relatório Usuários em Débito Automático
 * 
 * @author Anderson Italo
 * @date 24/02/2011
 */
public class RelatorioUsuarioDebitoAutomatico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioUsuarioDebitoAutomatico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_USUARIO_DEBITO_AUTOMATICO);
	}

	@Deprecated
	public RelatorioUsuarioDebitoAutomatico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obtêm os parâmetros passados
		Integer idBancoInicial = new Integer((Integer) getParametro("idBancoInicial"));
		Integer idBancoFinal = new Integer((Integer) getParametro("idBancoFinal"));
		String nomeBancoInicial = (String) getParametro("nomeBancoInicial");
		String nomeBancoFinal = (String) getParametro("nomeBancoFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();
		// obtém as localidades do intervalo informado
		Collection colecaoDadosRelatorio = fachada.pesquisaRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		for(Iterator iterator = colecaoDadosRelatorio.iterator(); iterator.hasNext();){

			Object[] dados = (Object[]) iterator.next();

			RelatorioUsuarioDebitoAutomaticoBean bean = new RelatorioUsuarioDebitoAutomaticoBean();

			// Cabeçalho

			// 4.1. Código do Banco
			bean.setCodigoBanco(dados[0].toString());

			// 4.2. Nome do Banco
			bean.setNomeBanco(dados[1].toString());

			// Linha Detalhe

			// 4.3. Agência
			bean.setAgencia(dados[2].toString());

			// 4.4. Conta Corrente
			bean.setContaCorrente(dados[3].toString());

			// 4.5. Matrícula Imóvel
			bean.setMatriculaImovel(dados[4].toString());

			// 4.6. Nome Cliente
			bean.setCliente(dados[5].toString());

			// 4.7. Data Opção
			if(dados[6] != null && !dados[6].toString().equals("")){
				bean.setDataOpcao(Util.formatarData(dados[6].toString().replace("-", "")));
			}

			relatorioBeans.add(bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("nomeBancoInicial", nomeBancoInicial);
		parametros.put("codigoBancoInicial", idBancoInicial.toString());
		parametros.put("nomeBancoFinal", nomeBancoFinal);
		parametros.put("codigoBancoFinal", idBancoFinal.toString());

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_USUARIO_DEBITO_AUTOMATICO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_USUARIO_DEBITO_AUTOMATICO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		// obtêm os parâmetros passados
		Integer idBancoInicial = (Integer) getParametro("idBancoInicial");
		Integer idBancoFinal = (Integer) getParametro("idBancoFinal");

		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(idBancoInicial, idBancoFinal).intValue();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioUsuarioDebitoAutomatico", this);
	}

}