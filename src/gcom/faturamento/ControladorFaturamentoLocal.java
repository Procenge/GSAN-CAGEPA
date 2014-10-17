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

package gcom.faturamento;

import gcom.arrecadacao.ContratoDemanda;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public interface ControladorFaturamentoLocal
				extends javax.ejb.EJBLocalObject, IControladorFaturamento {

	/**
	 * Permite inserir um Contrato de Demanda
	 * [UC0521] Inserir Contrato Demanda
	 * 
	 * @author Eduardo Bianchi
	 * @date 07/03/2007
	 */
	public Integer inserirContratoDemanda(ContratoDemanda contratoDemanda, Usuario usuarioLogado) throws ControladorException;

	public Integer inserirContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao, Usuario usuarioLogado) throws ControladorException;

	public Collection<ContaMotivoRevisao> pesquisarContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao, String tipoPesquisa)
					throws ControladorException;

	public void atualizarContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao) throws ControladorException;

	public Integer inserirContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao, Usuario usuarioLogado)
					throws ControladorException;

	public Collection<ContaMotivoRetificacao> pesquisarContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao,
					String tipoPesquisa) throws ControladorException;

	public void atualizarContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao) throws ControladorException;

	/**
	 * Gera o objeto débito a cobrar historico a partir do debito a cobrar.
	 * 
	 * @param debitoACobrar
	 * @return
	 */
	public DebitoACobrarHistorico gerarDebitoACobrarHistoricoDoDebitoACobrar(DebitoACobrar debitoACobrar);

	/**
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterEnderecoEntregaCliente(Imovel imovel) throws ControladorException;

	/**
	 * @param faturamentoGrupoCronogramaMensal
	 * @param dataRealizada
	 * @param anoMesReferencia
	 * @param grupoFaturamento
	 * @throws ControladorException
	 */
	public void atualizarFaturamentoAtividadeCronograma(FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal)
					throws ControladorException;

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 15/08/2011
	 * @param idTipoEspecificacaoSolicitacao
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeRAEncerradaAnoCorrentePorTipoSolicitacaoEspecificacao(Integer idTipoEspecificacaoSolicitacao,
					Integer idImovel) throws ControladorException;

	public DebitoAutomatico obterObjetoDebitoAutomatico(Integer idImovel) throws ControladorException;

	void verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(Collection idsConta, Usuario usuarioLogado,
					boolean validarPermissaoEspecialNoventa, boolean criticarContasEmRevisaoMotivosNaoParametrizados, String enderecoURL)
					throws ControladorException;

	/**
	 * @autor Cinthya Cavalcanti
	 * @date 13/09/2011
	 * @param consumoFaturadoAgua
	 * @param consumoMinimoLigacao
	 * @param consumoFaturadoEsgoto
	 * @param qtdEconnomia
	 * @return Map<String, String>
	 */
	Map<String, String> ajusteConsumoMultiploQuantidadeEconomia(Integer consumoFaturadoAgua, int consumoMinimoLigacao,
					Integer consumoFaturadoEsgoto, Integer qtdEconnomia) throws ControladorException;

	/**
	 * [UC3037] Filtrar Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 24/02/2012
	 *          Exibir Contas Pré-Faturadas.
	 */
	public Integer obterMaiorAnoMesReferenciaAnteriorMedicaoHistorico(Integer idImovel, Integer anoMesConta) throws ControladorException;

	/**
	 * [UC3037] Filtrar Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 24/02/2012
	 *          Exibir Contas Pré-Faturadas.
	 */
	public Integer obterMaiorAnoMesReferenciaAnteriorConsumoHistorico(Integer idImovel, Integer anoMesConta) throws ControladorException;

	/**
	 * @param imovel
	 * @param faturamentoGrupo
	 * @param anoMesReferencia
	 * @param sistemaParametro
	 * @param colecaoCategorias
	 * @param antecipado
	 * @param colecaoOcorrenciaGeracaoPreFaturamento
	 * @throws ControladorException
	 */
	public void gerarContaPreFaturadaImovel(Imovel imovel, FaturamentoGrupo faturamentoGrupo, Integer anoMesReferencia,
					SistemaParametro sistemaParametro, Collection colecaoCategorias, boolean antecipado,
					Collection<Imovel> colecaoOcorrenciaGeracaoPreFaturamento) throws ControladorException;

	/**
	 * Remover Mensagem da Conta
	 * 
	 * @author Hebert Falcão
	 * @created 14/03/2014
	 */
	public void removerMensagemConta(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * OC1213341 - Verificar se existe valor do Debito Tipo para a Localidade informada se positivo
	 * retorna o mesmo.
	 * 
	 * @author Ado Rocha
	 * @date 23/04/2014
	 **/
	public BigDecimal verificarDebitoTipoValorLocalidade(Integer idImovel, Integer idDebitoTipo) throws ControladorException;

}