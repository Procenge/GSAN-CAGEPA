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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

/**
 * classe responsável por criar o ComprovanteInscricaoCampanhaPremiacao
 * 
 * @author Hiroshi Gonçalves
 * @date 23/09/2013
 */
public class RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_COMPROVANTE_INSCRICAO_CAMPANHA_PREMIACAO);
	}

	@Deprecated
	public RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1() {

		super(null, "");
	}

	private Collection<RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean> inicializarBeanRelatorio(CampanhaCadastro campanhaCadastro){

		ArrayList retorno = new ArrayList();
		Fachada fachada = Fachada.getInstancia();


		RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean bean = new RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean();

		bean.setDsTituloCampanha(campanhaCadastro.getCampanha().getDsTituloCampanha());

		bean.setNumeroInscricao(campanhaCadastro.getNumeroInscricao());

		bean.setNomeCliente(campanhaCadastro.getNomeCliente());

		bean.setIdImovel(campanhaCadastro.getImovel().getId().toString());

		// Endereço do Imóvel (<<Inclui>> [UC0085 - Obter Endereço])
		bean.setEnderecoImovel(fachada.pesquisarEndereco(campanhaCadastro.getImovel().getId()));

		bean.setDtHoraInsricao(Util.formatarDataComHora(campanhaCadastro.getTmInscricao()));

		bean.setLocalInscricao(campanhaCadastro.getUnidadeOrganizacional().getDescricao());

		bean.setCdTipoRelacao(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().toString());

		// Pessoa Física
		if(campanhaCadastro.getNumeroCPF() != null){

			bean.setNumeroCPF(Util.formatarCpf(campanhaCadastro.getNumeroCPF()));

			bean.setNumeroRG(campanhaCadastro.getNumeroRG());

			bean.setDtEmissao(Util.formatarData(campanhaCadastro.getDataRGEmissao()));

			bean.setDsOrgaoExpedidor(campanhaCadastro.getOrgaoExpedidorRG().getDescricao());

			bean.setDsUF(campanhaCadastro.getUnidadeFederacao().getSigla());

			bean.setDtNascimento(Util.formatarData(campanhaCadastro.getDataNascimento()));

			bean.setNomeMae(campanhaCadastro.getNomeMae());

		}else{

			bean.setNumeroCNPJ(Util.formatarCnpj(campanhaCadastro.getNumeroCNPJ()));
		}

		if(campanhaCadastro.getDsEmail() != null){
			bean.setDsEmail(campanhaCadastro.getDsEmail());
		}

		retorno.add(bean);

		return retorno;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String indicadorEnvioComprovanteEmail = (String) getParametro("indicadorEnvioComprovanteEmail");
		CampanhaCadastro campanhaCadastro = (CampanhaCadastro) getParametro("campanhaCadastro");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		//
		Collection<RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean> colComprovanteInscBean = this
						.inicializarBeanRelatorio(campanhaCadastro);

		RelatorioDataSource ds = new RelatorioDataSource((List) colComprovanteInscBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_COMPROVANTE_INSCRICAO_CAMPANHA_PREMIACAO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.COMPROVANTE_INSCRICAO_CAMPANHA_PREMIACAO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		if(this.getUsuario() != null){
		// Atualizar dados da emissão do comprovante
		this.atualizarDadosEmissaoComprovante(fachada, campanhaCadastro, this.getUsuario().getId());
		}else{
			this.atualizarDadosEmissaoComprovante(fachada, campanhaCadastro, null);
		}

		// Envia por E-mail ou Exibe na tela
		if(indicadorEnvioComprovanteEmail != null && indicadorEnvioComprovanteEmail.equals(ConstantesSistema.SIM.toString())){

			String emailRemetente = campanhaCadastro.getCampanha().getDsEmailEnvioComprovante();
			String emailDestinatario = campanhaCadastro.getDsEmail();
			String emailTitulo = campanhaCadastro.getCampanha().getDsTituloCampanha();

			// Enviar por E-Mail
			fachada.enviarEmailComprovanteInscricaoCampanhaPremiacao(emailDestinatario, emailRemetente, emailTitulo, retorno);

			ActionServletException e = new ActionServletException("atencao.campanhapremiacao.comprovante_enviado_email", emailDestinatario);
			e.setUrlBotaoVoltar("exibirEfetuarInscricaoCampanhaPremiacaoAction.do?idImovel=" + campanhaCadastro.getImovel().getId());
			throw e;

		}else{

			// Exibir Comprovante
			return retorno;
		}
	}

	private void atualizarDadosEmissaoComprovante(Fachada fachada, CampanhaCadastro campanhaCadastro, Integer idUsuario){
		// Atualizar dados da emissão do comprovante
		campanhaCadastro.setIdEmissaoComprovante(idUsuario);

		if(campanhaCadastro.getDataEmissaoComprovante() == null){
			campanhaCadastro.setDataEmissaoComprovante(new Date());
		}

		campanhaCadastro.setIndicadorComprovanteBloqueado(ConstantesSistema.NAO);

		campanhaCadastro.setUltimaAlteracao(new Date());

		fachada.atualizar(campanhaCadastro);

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("ComprovanteInscricaoCampanhaPremiacao", this);
	}
}