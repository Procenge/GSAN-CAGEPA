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

package gcom.gui.util.tabelaauxiliar.abreviada;

import gcom.arrecadacao.banco.Banco;
import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.cadastro.cliente.EstadoCivil;
import gcom.cadastro.cliente.Nacionalidade;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.cliente.Raca;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Despejo;
import gcom.cadastro.imovel.FonteAbastecimento;
import gcom.cadastro.localidade.Zeis;
import gcom.cobranca.CobrancaGrupo;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.Funcionalidade;
import gcom.gui.util.tabelaauxiliar.DadosTelaTabelaAuxiliar;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.operacional.SistemaAbastecimento;
import gcom.seguranca.acesso.Operacao;
import gcom.util.HibernateUtil;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.util.HashMap;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class DadosTelaTabelaAuxiliarAbreviada
				extends DadosTelaTabelaAuxiliar {

	private TabelaAuxiliarAbreviada tabelaAbreviada;

	private static HashMap telas = new HashMap();

	private static HashMap configuracaoParametrosTelas = new HashMap();

	/**
	 * Este método busca um map de funcionalidades cadastradas e cria uma
	 * instância da funcionalidade para ser usada na Tabela Auxiliar
	 * 
	 * @param nome
	 * @return
	 */
	public static DadosTelaTabelaAuxiliarAbreviada obterDadosTelaTabelaAuxiliar(String nome){

		System.out.println("-----------Adicionando itens");
		// Verifica se a funcionalidade desejada já foi instanciada e já está no
		// cache
		if(!telas.containsKey(nome)){
			String[] configuracaoTela = (String[]) configuracaoParametrosTelas.get(nome);

			try{
				// Cria a instância do objeto DadosTelaTabelaAuxiliar
				DadosTelaTabelaAuxiliarAbreviada dadosTela = new DadosTelaTabelaAuxiliarAbreviada(configuracaoTela[1],
								(TabelaAuxiliarAbreviada) Class.forName(configuracaoTela[0]).newInstance(), configuracaoTela[2], nome);
				// Coloca a instância criada no map que representa o cache com
				// as instância já criadas
				telas.put(nome, dadosTela);

				return dadosTela;
			}catch(ClassNotFoundException ex){
				throw new SistemaException();
			}catch(IllegalAccessException ex){
				throw new SistemaException();
			}catch(InstantiationException ex){
				throw new SistemaException();
			}
		}else{
			// Se o a funcionalidade já estiver no cache, então ela é retornada
			// sem a necessidade de passar pelo método
			return (DadosTelaTabelaAuxiliarAbreviada) telas.get(nome);
		}
	}

	static{

		configuracaoParametrosTelas.put("operacao", new String[] {Operacao.class.getName(), "Operação", Funcionalidade.TELA_OPERACAO});

		configuracaoParametrosTelas
						.put(
										"hidrometroLocalArmazenagemOrigem",
										new String[] {HidrometroLocalArmazenagem.class.getName(), "Local de Armazenagem do Hidrômetro", Funcionalidade.TELA_HIDROMETRO_LOCAL_ARMAZENAGEM_ORIGEM});
		configuracaoParametrosTelas
						.put(
										"hidrometroLocalArmazenagemDestino",
										new String[] {HidrometroLocalArmazenagem.class.getName(), "Local de Armazenagem do Hidrômetro", Funcionalidade.TELA_HIDROMETRO_LOCAL_ARMAZENAGEM_DESTINO});
		configuracaoParametrosTelas
						.put(
										"hidrometroLocalArmazenagem",
										new String[] {HidrometroLocalArmazenagem.class.getName(), "Local de Armazenagem do Hidrômetro", Funcionalidade.TELA_HIDROMETRO_LOCAL_ARMAZENAGEM});
		configuracaoParametrosTelas.put("zeis", new String[] {Zeis.class.getName(), "Zeis", Funcionalidade.TELA_ZEIS});
		configuracaoParametrosTelas.put("faturamentoGrupo",
						new String[] {FaturamentoGrupo.class.getName(), "Faturamento Grupo", Funcionalidade.TELA_FATURAMENTO_GRUPO});
		configuracaoParametrosTelas.put("grupoCobranca",
						new String[] {CobrancaGrupo.class.getName(), "Grupo de Cobrança", Funcionalidade.TELA_COBRANCA_GRUPO});
		configuracaoParametrosTelas
						.put(
										"sistemaAbastecimento",
										new String[] {SistemaAbastecimento.class.getName(), "Sistema de Abastecimento", Funcionalidade.TELA_SISTEMA_ABASTECIMENTO});
		configuracaoParametrosTelas
						.put(
										"fonteAbastecimento",
										new String[] {FonteAbastecimento.class.getName(), "Fonte de Abastecimento", Funcionalidade.TELA_FONTE_ABASTECIMENTO});
		configuracaoParametrosTelas.put("despejo", new String[] {Despejo.class.getName(), "Despejo", Funcionalidade.TELA_DESPEJO});
		configuracaoParametrosTelas.put("orgaoExpedidorRg",
						new String[] {OrgaoExpedidorRg.class.getName(), "Órgao Expedidor do RG", Funcionalidade.TELA_ORGAO_EXPEDIDOR_RG});
		configuracaoParametrosTelas.put("unidadeFederacao",
						new String[] {UnidadeFederacao.class.getName(), "Unidade da Federação", Funcionalidade.TELA_UNIDADE_FEDERACAO});
		configuracaoParametrosTelas
						.put(
										"equipamentosEspeciais",
										new String[] {EquipamentosEspeciais.class.getName(), "Equipamento Especial", Funcionalidade.TELA_EQUIPAMENTOS_ESPECIAIS});

		configuracaoParametrosTelas.put("banco", new String[] {Banco.class.getName(), "Banco", Funcionalidade.TELA_BANCO});

		configuracaoParametrosTelas
						.put(
										"funcionalidade",
										new String[] {gcom.seguranca.acesso.Funcionalidade.class.getName(), "Funcionalidade", Funcionalidade.TELA_FUNCIONALIDADE});

		configuracaoParametrosTelas.put("areaTipo",
						new String[] {gcom.cadastro.localidade.AreaTipo.class.getName(), "Área Tipo", Funcionalidade.TELA_AREA_TIPO});

		configuracaoParametrosTelas
						.put("hidrometroClasseMetrologica", new String[] {gcom.micromedicao.hidrometro.HidrometroClasseMetrologica.class
										.getName(), "Hidrômetro Classe Metrológica", Funcionalidade.TELA_HIDROMETRO_CLASSE_METROLOGICA});

		configuracaoParametrosTelas
						.put("hidrometroLocalInstalacao", new String[] {gcom.micromedicao.hidrometro.HidrometroLocalInstalacao.class
										.getName(), "Hidrômetro Local Instalação", Funcionalidade.TELA_HIDROMETRO_LOCAL_INSTALACAO});

		configuracaoParametrosTelas
						.put(
										"hidrometroProtecao",
										new String[] {gcom.micromedicao.hidrometro.HidrometroProtecao.class.getName(), "Hidrômetro Proteção", Funcionalidade.TELA_HIDROMETRO_PROTECAO});

		configuracaoParametrosTelas
						.put(
										"hidrometroTipo",
										new String[] {gcom.micromedicao.hidrometro.HidrometroTipo.class.getName(), "Hidrômetro Tipo", Funcionalidade.TELA_HIDROMETRO_TIPO});

		configuracaoParametrosTelas.put("infracaoTipo",
						new String[] {gcom.cobranca.InfracaoTipo.class.getName(), "Infração Tipo", Funcionalidade.TELA_INFRACAO_TIPO});

		configuracaoParametrosTelas.put("impostoTipo",
						new String[] {gcom.faturamento.ImpostoTipo.class.getName(), "Imposto Tipo", Funcionalidade.TELA_IMPOSTO_TIPO});

		configuracaoParametrosTelas
						.put(
										"infracaoImovelSituacao",
										new String[] {gcom.cobranca.InfracaoImovelSituacao.class.getName(), "Infração Imóvel Situação", Funcionalidade.TELA_INFRACAO_IMOVEL_SITUACAO});

		configuracaoParametrosTelas
						.put(
										"infracaoLigacaoSituacao",
										new String[] {gcom.cobranca.InfracaoLigacaoSituacao.class.getName(), "Infração Ligação Situação", Funcionalidade.TELA_INFRACAO_LIGACAO_SITUACAO});

		configuracaoParametrosTelas
						.put(
										"lancamentoItem",
										new String[] {gcom.financeiro.lancamento.LancamentoItem.class.getName(), "Lançamento Item", Funcionalidade.TELA_LANCAMENTO_ITEM});

		configuracaoParametrosTelas
						.put(
										"localEntregaDocumento",
										new String[] {gcom.faturamento.LocalEntregaDocumento.class.getName(), "Local Entrega Documento", Funcionalidade.TELA_LOCAL_ENTREGA_DOCUMENTO});

		configuracaoParametrosTelas
						.put(
										"logradouroTipo",
										new String[] {gcom.cadastro.endereco.LogradouroTipo.class.getName(), "Logradouro Tipo", Funcionalidade.TELA_LOGRADOURO_TIPO});

		configuracaoParametrosTelas
						.put(
										"logradouroTitulo",
										new String[] {gcom.cadastro.endereco.LogradouroTitulo.class.getName(), "Logradouro Titulo", Funcionalidade.TELA_LOGRADOURO_TITULO});

		configuracaoParametrosTelas
						.put("materialCavaleteAgua", new String[] {gcom.atendimentopublico.ordemservico.MaterialCavaleteAgua.class
										.getName(), "Material Cavalete Água", Funcionalidade.TELA_MATERIAL_CAVA_AGUA});

		configuracaoParametrosTelas.put("materialRamalAgua", new String[] {gcom.atendimentopublico.ordemservico.MaterialRamalAgua.class
						.getName(), "Material Ramal Água", Funcionalidade.TELA_MATERIAL_RAMAL_AGUA});

		configuracaoParametrosTelas.put("materialRamalEsgoto", new String[] {gcom.atendimentopublico.ordemservico.MaterialRamalEsgoto.class
						.getName(), "Material Ramal Esgoto", Funcionalidade.TELA_MATERIAL_RAMAL_ESGOTO});

		configuracaoParametrosTelas.put("materialRedeAgua", new String[] {gcom.atendimentopublico.ordemservico.MaterialRedeAgua.class
						.getName(), "Material Rede Água", Funcionalidade.TELA_MATERIAL_REDE_AGUA});

		configuracaoParametrosTelas.put("materialRedeEsgoto", new String[] {gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto.class
						.getName(), "Material Rede Esgoto", Funcionalidade.TELA_MATERIAL_REDE_ESGOTO});

		configuracaoParametrosTelas.put("meioSolicitacao", new String[] {gcom.atendimentopublico.registroatendimento.MeioSolicitacao.class
						.getName(), "Meio Solicitação", Funcionalidade.TELA_MEIO_SOLICITACAO});

		configuracaoParametrosTelas.put("motivoInterrupcao", new String[] {gcom.atendimentopublico.ordemservico.MotivoInterrupcao.class
						.getName(), "Motivo Interrupção", Funcionalidade.TELA_MOTIVO_INTERRUPCAO});

		configuracaoParametrosTelas.put("motivoNaoEntregaDocumento", new String[] {gcom.faturamento.conta.MotivoNaoEntregaDocumento.class
						.getName(), "Motivo Não Entrega Documento", Funcionalidade.TELA_MOTIVO_NAO_ENTREGA_DOCUMENTO});

		configuracaoParametrosTelas
						.put(
										"acaoCobrancaEfeito",
										new String[] {gcom.cobranca.AcaoCobrancaEfeito.class.getName(), "Ação Cobrança Efeito", Funcionalidade.TELA_ACAO_COBRANCA_EFEITO});

		configuracaoParametrosTelas
						.put(
										"agenciaReguladoraMotRetorno",
										new String[] {gcom.atendimentopublico.registroatendimento.AgenciaReguladoraMotRetorno.class
														.getName(), "Agência Reguladora Mot Retorno", Funcionalidade.TELA_AGENCIA_REGULADORA_MOT_RETORNO});

		configuracaoParametrosTelas
						.put(
										"agenciaReguladoraMotReclamacao",
										new String[] {gcom.atendimentopublico.registroatendimento.AgenciaReguladoraMotReclamacao.class
														.getName(), "Agência Reguladora Mot Reclamação", Funcionalidade.TELA_AGENCIA_REGULADORA_MOT_RECLAMACAO});

		configuracaoParametrosTelas
						.put(
										"agenteExterno",
										new String[] {gcom.atendimentopublico.ordemservico.AgenteExterno.class.getName(), "Agente Externo", Funcionalidade.TELA_AGENTE_EXTERNO});

		configuracaoParametrosTelas
						.put(
										"atendimentoIncompletoMotivo",
										new String[] {gcom.atendimentopublico.registroatendimento.AtendimentoIncompletoMotivo.class
														.getName(), "Atendimento Incompleto Motivo ", Funcionalidade.TELA_ATENDIMENTO_INCOMPLETO_MOTIVO});

		configuracaoParametrosTelas
						.put("boletoBancarioMotivoCancelamento", new String[] {gcom.cobranca.BoletoBancarioMotivoCancelamento.class
										.getName(), "Boleto Bancário Motivo Cancelamento ", Funcionalidade.TELA_BOLETO_BANCARIO_MOT_CANCEL});

		configuracaoParametrosTelas
						.put(
										"boletoBancarioSituacao",
										new String[] {gcom.cobranca.BoletoBancarioSituacao.class.getName(), "Boleto Bancário Situação ", Funcionalidade.TELA_BOLETO_BANCARIO_SITUACAO});

		configuracaoParametrosTelas
						.put(
										"causaVazamento",
										new String[] {gcom.atendimentopublico.ordemservico.CausaVazamento.class.getName(), "Causa Vazamento", Funcionalidade.TELA_CAUSA_VAZAMENTO});

		configuracaoParametrosTelas
						.put(
										"cobrancaAcaoSituacao",
										new String[] {gcom.cobranca.CobrancaAcaoSituacao.class.getName(), "Cobranca Acao Situacao", Funcionalidade.TELA_COBRANCA_ACAO_SITUACAO});

		configuracaoParametrosTelas.put("cobrancaForma",
						new String[] {gcom.cobranca.CobrancaForma.class.getName(), "Cobranca Forma ", Funcionalidade.TELA_COBRANCA_FORMA});

		configuracaoParametrosTelas
						.put(
										"consumoAnormalidade",
										new String[] {gcom.micromedicao.consumo.ConsumoAnormalidade.class.getName(), "Consumo Anormalidade", Funcionalidade.TELA_CONSUMO_ANORMALIDADE});

		configuracaoParametrosTelas
						.put(
										"consumoFixoUnidade",
										new String[] {gcom.micromedicao.consumo.ConsumoFixoUnidade.class.getName(), "Consumo Fixo Unidade", Funcionalidade.TELA_CONSUMO_FIXO_UNIDADE});

		configuracaoParametrosTelas
						.put(
										"consumoTipo",
										new String[] {gcom.micromedicao.consumo.ConsumoTipo.class.getName(), "Consumo Tipo", Funcionalidade.TELA_CONSUMO_TIPO});

		configuracaoParametrosTelas
						.put(
										"creditoOrigem",
										new String[] {gcom.faturamento.credito.CreditoOrigem.class.getName(), "Credito Origem", Funcionalidade.TELA_CREDITO_ORIGEM});

		configuracaoParametrosTelas
						.put(
										"debitoAutomaticoMovimentoCancelamentoMotivo",
										new String[] {gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimentoCancelamentoMotivo.class
														.getName(), "Debito Automatico Movimento Cancelamento Motivo", Funcionalidade.TELA_DEBITO_AUTOM_MOVTO_CANC_MOTIVO});

		configuracaoParametrosTelas.put("deducaoTipo",
						new String[] {gcom.arrecadacao.DeducaoTipo.class.getName(), " Deducao Tipo", Funcionalidade.TELA_DEDUCAO_TIPO});

		configuracaoParametrosTelas.put("despejo",
						new String[] {gcom.cadastro.imovel.Despejo.class.getName(), "Despejo", Funcionalidade.TELA_DESPEJO});

		configuracaoParametrosTelas
						.put(
										"devolucaoSituacao",
										new String[] {gcom.arrecadacao.DevolucaoSituacao.class.getName(), "Devolucao Situacao", Funcionalidade.TELA_DEVOLUCAO_SITUACAO});

		configuracaoParametrosTelas
						.put("diametroCavaleteAgua", new String[] {gcom.atendimentopublico.ordemservico.DiametroCavaleteAgua.class
										.getName(), "Diametro Cavalete Agua", Funcionalidade.TELA_DIAMETRO_CAVA_AGUA});

		configuracaoParametrosTelas.put("diametroRamalAgua", new String[] {gcom.atendimentopublico.ordemservico.DiametroRamalAgua.class
						.getName(), "Diametro Ramal Agua", Funcionalidade.TELA_DIAMETRO_RAMAL_AGUA});

		configuracaoParametrosTelas.put("diametroRamalEsgoto", new String[] {gcom.atendimentopublico.ordemservico.DiametroRamalEsgoto.class
						.getName(), "Diametro Ramal Esgoto", Funcionalidade.TELA_DIAMETRO_RAMAL_ESGOTO});

		configuracaoParametrosTelas.put("diametroRedeAgua", new String[] {gcom.atendimentopublico.ordemservico.DiametroRedeAgua.class
						.getName(), "Diametro Rede Agua", Funcionalidade.TELA_DIAMETRO_REDE_AGUA});

		configuracaoParametrosTelas.put("diametroRedeEsgoto", new String[] {gcom.atendimentopublico.ordemservico.DiametroRedeEsgoto.class
						.getName(), " Diametro Rede Esgoto ", Funcionalidade.TELA_DIAMETRO_REDE_ESGOTO});

		configuracaoParametrosTelas
						.put(
										"enderecoReferencia",
										new String[] {gcom.cadastro.endereco.EnderecoReferencia.class.getName(), " Endereco Referencia ", Funcionalidade.TELA_ENDERECO_REFERENCIA});
		configuracaoParametrosTelas
						.put(
										"esgotoTratamentoTipo",
										new String[] {gcom.operacional.EsgotoTratamentoTipo.class.getName(), " Esgoto Tratamento Tipo ", Funcionalidade.TELA_ESGOTO_TRATAMENTO_TIPO});
		configuracaoParametrosTelas
						.put(
										"eventoGeracao",
										new String[] {gcom.atendimentopublico.ordemservico.EventoGeracao.class.getName(), " Evento Geracao ", Funcionalidade.TELA_EVENTO_GERACAO});

		configuracaoParametrosTelas.put("formaEncerramento", new String[] {gcom.atendimentopublico.ordemservico.FormaEncerramento.class
						.getName(), " Forma Encerramento ", Funcionalidade.TELA_FORMA_ENCERRAMENTO});

		configuracaoParametrosTelas
						.put(
										"formaGeracao",
										new String[] {gcom.atendimentopublico.ordemservico.FormaGeracao.class.getName(), " Forma Geracao ", Funcionalidade.TELA_FORMA_GERACAO});

		configuracaoParametrosTelas.put("formaProgramacao", new String[] {gcom.atendimentopublico.ordemservico.FormaProgramacao.class
						.getName(), " Forma Programacao ", Funcionalidade.TELA_FORMA_PROGRAMACAO});

		configuracaoParametrosTelas.put("formaSelecaoEquipe", new String[] {gcom.atendimentopublico.ordemservico.FormaSelecaoEquipe.class
						.getName(), " Forma Selecao Equipe ", Funcionalidade.TELA_FORMA_SELECAO_EQUIPE});

		configuracaoParametrosTelas
						.put(
										"formaTramite",
										new String[] {gcom.atendimentopublico.ordemservico.FormaTramite.class.getName(), " Forma Tramite ", Funcionalidade.TELA_FORMA_TRAMITE});

		configuracaoParametrosTelas
						.put(
										"orgaoExpedidorRG",
										new String[] {gcom.cadastro.cliente.OrgaoExpedidorRg.class.getName(), "Órgão Expedidor RG", Funcionalidade.TELA_ORGAO_EXPEDIDOR_RG});

		configuracaoParametrosTelas
						.put("osProgramNaoEncerMotivo", new String[] {gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo.class
										.getName(), "OS Programa Não Encerrado Motivo", Funcionalidade.TELA_OS_PROGRAM_NAO_ENCER_MOTIVO});

		configuracaoParametrosTelas
						.put(
										"pagamentoSituacao",
										new String[] {gcom.arrecadacao.pagamento.PagamentoSituacao.class.getName(), "Pagamento Situação", Funcionalidade.TELA_PAGAMENTO_SITUACAO});

		configuracaoParametrosTelas
						.put(
										"pavimentoCalcada",
										new String[] {gcom.cadastro.imovel.PavimentoCalcada.class.getName(), "Pavimento Calçada", Funcionalidade.TELA_PAVIMENTO_CALCADA});

		configuracaoParametrosTelas
						.put(
										"pavimentoRua",
										new String[] {gcom.cadastro.imovel.PavimentoRua.class.getName(), "Pavimento Rua", Funcionalidade.TELA_PAVIMENTO_RUA});

		configuracaoParametrosTelas
						.put(
										"processoSituacao",
										new String[] {gcom.batch.ProcessoSituacao.class.getName(), "Processo Situação", Funcionalidade.TELA_PROCESSO_SITUACAO});

		configuracaoParametrosTelas
						.put(
										"ramalLocalInstalacao",
										new String[] {gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao.class.getName(), "Ramal Local Instalação", Funcionalidade.TELA_RAMAL_LOCAL_INSTALACAO});

		configuracaoParametrosTelas
						.put("raMotivoReativacao", new String[] {gcom.atendimentopublico.registroatendimento.RaMotivoReativacao.class
										.getName(), "RA Motivo Reativação", Funcionalidade.TELA_RA_MOTIVO_REATIVACAO});

		configuracaoParametrosTelas.put("relatorio",
						new String[] {gcom.batch.Relatorio.class.getName(), "Relatorio", Funcionalidade.TELA_RELATORIO});

		configuracaoParametrosTelas.put("servicoTipoGrupo", new String[] {gcom.atendimentopublico.ordemservico.ServicoTipoGrupo.class
						.getName(), "Servico Tipo Grupo", Funcionalidade.TELA_SERVICO_TIPO_GRUPO});

		configuracaoParametrosTelas
						.put(
										"unidadeProcessamento",
										new String[] {gcom.batch.UnidadeProcessamento.class.getName(), "Unidade Processamento", Funcionalidade.TELA_UNIDADE_PROCESSAMENTO});

		configuracaoParametrosTelas
						.put(
										"unidadeSituacao",
										new String[] {gcom.batch.UnidadeSituacao.class.getName(), "Unidade Situação", Funcionalidade.TELA_UNIDADE_SITUACAO});

		configuracaoParametrosTelas.put("zeis",
						new String[] {gcom.cadastro.localidade.Zeis.class.getName(), "Zeis", Funcionalidade.TELA_ZEIS});

		configuracaoParametrosTelas
						.put(
										"motivoNaoGeracaoDocumento",
										new String[] {gcom.cobranca.MotivoNaoGeracaoDocumento.class.getName(), "Motivo Não Geração Documento", Funcionalidade.TELA_MOTIVO_NAO_GERACAO_DOCUMENTO});

		configuracaoParametrosTelas.put("raca", new String[] {Raca.class.getName(), "Raca", Funcionalidade.TELA_RACA});

		configuracaoParametrosTelas.put("estadoCivil",
						new String[] {EstadoCivil.class.getName(), "Estado Civil", Funcionalidade.TELA_ESTADO_CIVIL});
		configuracaoParametrosTelas.put("nacionalidade",
						new String[] {Nacionalidade.class.getName(), "Nacionalidade", Funcionalidade.TELA_NACIONALIDADE});

	}

	/**
	 * Construtor da classe DadosTelaTabelaAuxiliarAbreviada
	 * 
	 * @param titulo
	 *            Descrição do parâmetro
	 * @param tabela
	 *            Descrição do parâmetro
	 * @param funcionalidadeTabelaAux
	 *            Descrição do parâmetro
	 */
	protected DadosTelaTabelaAuxiliarAbreviada(String titulo, TabelaAuxiliar tabela, String funcionalidadeTabelaAux,
												String nomeParametroFuncionalidade) {

		super(titulo, tabela, funcionalidadeTabelaAux, nomeParametroFuncionalidade);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param dados
	 *            Descrição do parâmetro
	 */
	public static void adicionarDadosTela(DadosTelaTabelaAuxiliar dados){

		DadosTelaTabelaAuxiliar.adicionarDadosTela(dados);
	}

	/**
	 * Retorna o valor de dadosTela
	 * 
	 * @param nome
	 *            Descrição do parâmetro
	 * @return O valor de dadosTela
	 */
	public static DadosTelaTabelaAuxiliar getDadosTela(String nome){

		return DadosTelaTabelaAuxiliar.getDadosTela(nome);
	}

	/**
	 * Método sobrescrito que retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxManter(){

		return super.getFuncionalidadeTabelaAuxManter().replaceAll(Funcionalidade.TABELA_AUXILIAR_MANTER,
						Funcionalidade.TABELA_AUXILIAR_ABREVIADA_MANTER);
	}

	/**
	 * Método sobrescrito que retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxFiltrar(){

		return super.getFuncionalidadeTabelaAuxFiltrar().replaceAll(Funcionalidade.TABELA_AUXILIAR_FILTRAR,
						Funcionalidade.TABELA_AUXILIAR_ABREVIADA_FILTRAR);
	}

	/**
	 * Método sobrescrito que retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxInserir(){

		return super.getFuncionalidadeTabelaAuxInserir().replaceAll(Funcionalidade.TABELA_AUXILIAR_INSERIR,
						Funcionalidade.TABELA_AUXILIAR_ABREVIADA_INSERIR);
	}

	/**
	 * Retorna o valor de tamanhoMaximoCampo
	 * 
	 * @return O valor de tamanhoMaximoCampo
	 */
	public int getTamanhoMaximoDescricaoAbreviada(){

		return HibernateUtil.getColumnSize(this.getTabelaAuxiliar().getClass(), "descricaoAbreviada");
	}

	/**
	 * Retorna o valor de tabelaAuxiliar
	 * 
	 * @return O valor de tabelaAuxiliar
	 */
	public TabelaAuxiliar getTabela(){

		return super.getTabelaAuxiliar();
	}
}