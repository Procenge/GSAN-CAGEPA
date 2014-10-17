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

package gcom.util;

import gcom.arrecadacao.ArrecadacaoContabilParametros;
import gcom.arrecadacao.ArrecadacaoDadosDiarios;
import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.ArrecadadorContratoTarifa;
import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.ArrecadadorMovimentoItem;
import gcom.arrecadacao.AvisoInteligest;
import gcom.arrecadacao.ContratoDemanda;
import gcom.arrecadacao.ContratoMotivoCancelamento;
import gcom.arrecadacao.DeducaoTipo;
import gcom.arrecadacao.Devolucao;
import gcom.arrecadacao.DevolucaoHistorico;
import gcom.arrecadacao.DevolucaoSituacao;
import gcom.arrecadacao.GuiaDevolucao;
import gcom.arrecadacao.MetasArrecadacao;
import gcom.arrecadacao.RecebimentoTipo;
import gcom.arrecadacao.RegistroCodigo;
import gcom.arrecadacao.ResumoArrecadacao;
import gcom.arrecadacao.aviso.AvisoAcerto;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.AvisoDeducoes;
import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoRetornoCodigo;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoria;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoriaHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoDiametro;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoMaterial;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.EquipeComponentes;
import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacao;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoAgua;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoEsgoto;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoHidrometroCapacidade;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoServicoACobrar;
import gcom.atendimentopublico.ordemservico.Material;
import gcom.atendimentopublico.ordemservico.MaterialUnidade;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoAtividade;
import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
import gcom.atendimentopublico.ordemservico.OrdemServicoUnidade;
import gcom.atendimentopublico.ordemservico.OsAtividadeMaterialExecucao;
import gcom.atendimentopublico.ordemservico.OsAtividadePeriodoExecucao;
import gcom.atendimentopublico.ordemservico.OsExecucaoEquipe;
import gcom.atendimentopublico.ordemservico.OsExecucaoEquipeComponentes;
import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
import gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo;
import gcom.atendimentopublico.ordemservico.ProgramacaoRoteiro;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoAtividade;
import gcom.atendimentopublico.ordemservico.ServicoTipoGrupo;
import gcom.atendimentopublico.ordemservico.ServicoTipoMaterial;
import gcom.atendimentopublico.ordemservico.ServicoTipoOperacao;
import gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade;
import gcom.atendimentopublico.ordemservico.ServicoTipoReferencia;
import gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo;
import gcom.atendimentopublico.ordemservico.SupressaoMotivo;
import gcom.atendimentopublico.registroatendimento.AgenciaReguladoraMotReclamacao;
import gcom.atendimentopublico.registroatendimento.AgenciaReguladoraMotRetorno;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovSitCriterio;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovelSituacao;
import gcom.atendimentopublico.registroatendimento.EspecificacaoTipoValidacao;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.LocalidadeSolicTipoGrupo;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.RaDadosAgenciaReguladora;
import gcom.atendimentopublico.registroatendimento.RaDadosAgenciaReguladoraFone;
import gcom.atendimentopublico.registroatendimento.RaEnderecoDescritivo;
import gcom.atendimentopublico.registroatendimento.RaMotivoReativacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoColetor;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoGrupo;
import gcom.atendimentopublico.registroatendimento.SolicitanteFone;
import gcom.atendimentopublico.registroatendimento.Tramite;
import gcom.batch.FuncionalidadeIniciada;
import gcom.batch.FuncionalidadeSituacao;
import gcom.batch.Processo;
import gcom.batch.ProcessoFuncionalidade;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoSituacao;
import gcom.batch.ProcessoTipo;
import gcom.batch.Relatorio;
import gcom.batch.RelatorioGerado;
import gcom.batch.UnidadeIniciada;
import gcom.batch.UnidadeProcessamento;
import gcom.batch.UnidadeSituacao;
import gcom.cadastro.DbVersaoImplementada;
import gcom.cadastro.EnvioEmail;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelSituacao;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaAcaoCronograma;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.CobrancaAtividadeComandoRota;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaCriterioLinha;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.CobrancaGrupoCronogramaMes;
import gcom.cobranca.DocumentoEmissaoForma;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.ResumoCobrancaAcaoEventual;
import gcom.cobranca.ResumoPendencia;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoDescontoAntiguidade;
import gcom.cobranca.parcelamento.ParcelamentoDescontoInatividade;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.cobranca.parcelamento.ParcelamentoMotivoDesfazer;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacao;
import gcom.cobranca.parcelamento.ParcelamentoQuantidadeReparcelamento;
import gcom.cobranca.parcelamento.ParcelamentoSituacao;
import gcom.cobranca.parcelamento.ParcelamentoTipo;
import gcom.faturamento.ConsumoFaixaCategoria;
import gcom.faturamento.ConsumoFaixaLigacao;
import gcom.faturamento.ContaRevisaoFaixaValor;
import gcom.faturamento.DocumentoNaoEntregue;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoContabilParametros;
import gcom.faturamento.FaturamentoDados;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FaturamentoImediatoAjuste;
import gcom.faturamento.FaturamentoSituacaoHistorico;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FaturamentoTipo;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.HistogramaAguaEconomia;
import gcom.faturamento.HistogramaAguaLigacao;
import gcom.faturamento.HistogramaEsgotoEconomia;
import gcom.faturamento.HistogramaEsgotoLigacao;
import gcom.faturamento.ImpostoTipo;
import gcom.faturamento.ImpostoTipoAliquota;
import gcom.faturamento.LocalEntregaDocumento;
import gcom.faturamento.QualidadeAgua;
import gcom.faturamento.QualidadeAguaPadrao;
import gcom.faturamento.ResumoFaturamentoSimulacao;
import gcom.faturamento.ResumoFaturamentoSituacaoEspecial;
import gcom.faturamento.VencimentoAlternativo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaCategoriaConsumoFaixa;
import gcom.faturamento.conta.ContaCategoriaConsumoFaixaHistorico;
import gcom.faturamento.conta.ContaCategoriaHistorico;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.ContaImpostosDeduzidos;
import gcom.faturamento.conta.ContaImpostosDeduzidosHistorico;
import gcom.faturamento.conta.ContaImpressao;
import gcom.faturamento.conta.ContaMensagem;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.ContaTipo;
import gcom.faturamento.conta.Fatura;
import gcom.faturamento.conta.FaturaItem;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.faturamento.conta.MovimentoSegundaVia;
import gcom.faturamento.conta.Refaturamento;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoARealizarCategoria;
import gcom.faturamento.credito.CreditoARealizarCategoriaHistorico;
import gcom.faturamento.credito.CreditoARealizarCategoriaPK;
import gcom.faturamento.credito.CreditoARealizarGeral;
import gcom.faturamento.credito.CreditoARealizarHistorico;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoRealizadoCategoria;
import gcom.faturamento.credito.CreditoRealizadoCategoriaHistorico;
import gcom.faturamento.credito.CreditoRealizadoHistorico;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarCategoria;
import gcom.faturamento.debito.DebitoACobrarCategoriaHistorico;
import gcom.faturamento.debito.DebitoACobrarCategoriaPK;
import gcom.faturamento.debito.DebitoACobrarGeral;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCobradoCategoria;
import gcom.faturamento.debito.DebitoCobradoCategoriaHistorico;
import gcom.faturamento.debito.DebitoCobradoCategoriaHistoricoPK;
import gcom.faturamento.debito.DebitoCobradoHistorico;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoFaixaValore;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.ContaAReceberContabil;
import gcom.financeiro.ContaContabil;
import gcom.financeiro.DevedoresDuvidososContabilParametro;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.LancamentoResumo;
import gcom.financeiro.LancamentoResumoConta;
import gcom.financeiro.LancamentoResumoContaHistorico;
import gcom.financeiro.LancamentoResumoValorTipo;
import gcom.financeiro.ParametrosDevedoresDuvidosos;
import gcom.financeiro.ParametrosDevedoresDuvidososItem;
import gcom.financeiro.ResumoDevedoresDuvidosos;
import gcom.financeiro.ResumoFaturamento;
import gcom.financeiro.lancamento.LancamentoContabil;
import gcom.financeiro.lancamento.LancamentoContabilItem;
import gcom.financeiro.lancamento.LancamentoItem;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoOrigem;
import gcom.financeiro.lancamento.LancamentoTipo;
import gcom.financeiro.lancamento.LancamentoTipoItem;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.ImovelTestesMedicaoConsumo;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.RateioTipo;
import gcom.micromedicao.ResumoAnormalidadeLeitura;
import gcom.micromedicao.Rota;
import gcom.micromedicao.RoteiroEmpresa;
import gcom.micromedicao.SituacaoTransmissaoLeitura;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoFixoFaixa;
import gcom.micromedicao.consumo.ConsumoFixoUnidade;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.ConsumoHistoricoAnterior;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.consumo.ResumoAnormalidadeConsumo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroMotivoBaixa;
import gcom.micromedicao.hidrometro.HidrometroMotivoMovimentacao;
import gcom.micromedicao.hidrometro.HidrometroMovimentacao;
import gcom.micromedicao.hidrometro.HidrometroMovimentado;
import gcom.micromedicao.hidrometro.HidrometroProtecao;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.hidrometro.HidrometroTipo;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.micromedicao.leitura.LeituraFaixaFalsa;
import gcom.micromedicao.leitura.LeituraFiscalizacao;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistoricoAnterior;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.ProducaoAgua;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SistemaEsgotoTratamentoTipo;
import gcom.operacional.ZonaAbastecimento;
import gcom.operacional.ZonaPressao;
import gcom.operacional.abastecimento.AbastecimentoProgramacao;
import gcom.operacional.abastecimento.ManutencaoProgramacao;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.FuncionalidadeDependencia;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.GrupoAcesso;
import gcom.seguranca.acesso.GrupoFuncionalidadeOperacao;
import gcom.seguranca.acesso.Modulo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.OperacaoOrdemExibicao;
import gcom.seguranca.acesso.OperacaoTabela;
import gcom.seguranca.acesso.OperacaoTipo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAbrangencia;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAlteracao;
import gcom.seguranca.acesso.usuario.UsuarioFavorito;
import gcom.seguranca.acesso.usuario.UsuarioGrupo;
import gcom.seguranca.acesso.usuario.UsuarioGrupoRestricao;
import gcom.seguranca.acesso.usuario.UsuarioPermissaoEspecial;
import gcom.seguranca.acesso.usuario.UsuarioSituacao;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.seguranca.transacao.AlteracaoTipo;
import gcom.seguranca.transacao.SgbdTabela;
import gcom.seguranca.transacao.SgbdTabelaColuna;
import gcom.seguranca.transacao.Tabela;
import gcom.seguranca.transacao.TabelaColuna;
import gcom.seguranca.transacao.TabelaLinhaAlteracao;
import gcom.seguranca.transacao.TabelaLinhaColunaAlteracao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

/**
 * Classe responsável pela instanciação do Hibernate e serviços específicos da
 * tecnologia
 * 
 * @author rodrigo
 */
public class HibernateUtilParaTestes {

	private static SessionFactory sessionFactory;

	private static Configuration configuration;

	private static boolean sessionFactoryInicializada = false;

	public static void inicializarSessionFactory(){

		try{

			// -------------------Configuração do servidor Gerencial------------------//
			configuration = new Configuration();

			configuration.setProperties(lerArquivoPropriedades("_hibernate.properties"));

			configuration.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
			configuration.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@10.50.254.65:1521:GSANCON");
			configuration.setProperty("hibernate.connection.username", "GSAN_OPER");
			configuration.setProperty("hibernate.connection.password", "oper#t14");

			configuration.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
			configuration.setProperty("hibernate.connection.pool_size", "1");
			configuration.setProperty("hibernate.current_session_context_class", "thread");
			configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
			configuration.setProperty("hibernate.show_sql", "true");
			configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle9Dialect");

			configuration

							// CLASSES DO PACOTE gcom.atendimentopublico //
							// ********************************************//
							// gcom.atendimentopublico.ligacaoagua
							.addClass(CorteTipo.class)
							.addClass(EmissaoOrdemCobrancaTipo.class)
							.addClass(LigacaoAgua.class)
							.addClass(LigacaoAguaDiametro.class)
							.addClass(LigacaoAguaMaterial.class)
							.addClass(LigacaoAguaPerfil.class)
							.addClass(LigacaoAguaSituacao.class)
							.addClass(SupressaoTipo.class)
							// gcom.atendimentopublico.ligacaoesgoto
							.addClass(LigacaoEsgoto.class)
							.addClass(LigacaoEsgotoDiametro.class)
							.addClass(LigacaoEsgotoMaterial.class)
							.addClass(LigacaoEsgotoPerfil.class)
							.addClass(LigacaoEsgotoSituacao.class)
							// gcom.atendimentopublico.registroatendimento
							.addClass(RegistroAtendimento.class)
							.addClass(RegistroAtendimentoColetor.class)
							.addClass(AgenciaReguladoraMotReclamacao.class)
							.addClass(AgenciaReguladoraMotRetorno.class)
							.addClass(AtendimentoMotivoEncerramento.class)
							.addClass(AtendimentoRelacaoTipo.class)
							.addClass(LocalOcorrencia.class)
							.addClass(MeioSolicitacao.class)
							.addClass(RaDadosAgenciaReguladora.class)
							.addClass(RaDadosAgenciaReguladoraFone.class)
							.addClass(RaEnderecoDescritivo.class)
							.addClass(RaMotivoReativacao.class)
							.addClass(RegistroAtendimentoSolicitante.class)
							.addClass(RegistroAtendimentoUnidade.class)
							.addClass(SolicitacaoTipo.class)
							.addClass(SolicitacaoTipoEspecificacao.class)
							.addClass(SolicitacaoTipoGrupo.class)
							.addClass(SolicitanteFone.class)
							.addClass(Tramite.class)
							.addClass(EspecificacaoImovelSituacao.class)
							.addClass(EspecificacaoImovSitCriterio.class)
							.addClass(EspecificacaoTipoValidacao.class)
							// gcom.atendimentopublico.ordemservico
							.addClass(OrdemServico.class)
							.addClass(ServicoTipo.class)
							.addClass(SupressaoMotivo.class)
							.addClass(Atividade.class)
							.addClass(Equipe.class)
							.addClass(EquipamentosEspeciais.class)
							.addClass(EquipeComponentes.class)
							.addClass(EspecificacaoServicoTipo.class)
							.addClass(FiscalizacaoColetiva.class)
							.addClass(Material.class)
							.addClass(MaterialUnidade.class)
							.addClass(OrdemServicoAtividade.class)
							.addClass(OrdemServicoProgramacao.class)
							.addClass(OrdemServicoUnidade.class)
							.addClass(OsAtividadeMaterialExecucao.class)
							.addClass(OsAtividadePeriodoExecucao.class)
							.addClass(OsExecucaoEquipe.class)
							.addClass(OsExecucaoEquipeComponentes.class)
							.addClass(OsProgramNaoEncerMotivo.class)
							.addClass(OsReferidaRetornoTipo.class)
							.addClass(ProgramacaoRoteiro.class)
							.addClass(ServicoCobrancaValor.class)
							.addClass(ServicoNaoCobrancaMotivo.class)
							.addClass(ServicoPerfilTipo.class)
							.addClass(ServicoTipoAtividade.class)
							.addClass(ServicoTipoGrupo.class)
							.addClass(ServicoTipoMaterial.class)
							.addClass(ServicoTipoOperacao.class)
							.addClass(ServicoTipoPrioridade.class)
							.addClass(ServicoTipoReferencia.class)
							.addClass(ServicoTipoSubgrupo.class)
							.addClass(LocalidadeSolicTipoGrupo.class)
							.addClass(FiscalizacaoSituacao.class)
							.addClass(FiscalizacaoSituacaoAgua.class)
							.addClass(FiscalizacaoSituacaoEsgoto.class)
							.addClass(FiscalizacaoSituacaoHidrometroCapacidade.class)
							.addClass(FiscalizacaoSituacaoServicoACobrar.class)

							// *************************************//
							// CLASSES DO PACOTE gcom.faturamento //
							// *************************************//
							.addClass(QualidadeAgua.class)
							.addClass(ImpostoTipo.class)
							.addClass(ImpostoTipoAliquota.class)
							.addClass(FaturamentoGrupo.class)
							.addClass(FaturamentoSituacaoTipo.class)
							.addClass(FaturamentoAtividade.class)
							.addClass(FaturamentoAtividadeCronograma.class)
							.addClass(FaturamentoGrupoCronogramaMensal.class)
							.addClass(FaturamentoImediatoAjuste.class)
							.addClass(FaturamentoSituacaoMotivo.class)
							.addClass(FaturamentoSituacaoHistorico.class)
							.addClass(FaturamentoTipo.class)
							.addClass(FaturamentoAtivCronRota.class)
							.addClass(FaturamentoDados.class)
							.addClass(ResumoFaturamentoSimulacao.class)
							.addClass(VencimentoAlternativo.class)
							.addClass(ResumoFaturamentoSituacaoEspecial.class)
							.addClass(FaturamentoContabilParametros.class)
							.addClass(GuiaPagamentoGeral.class)
							.addClass(DocumentoNaoEntregue.class)
							.addClass(HistogramaAguaEconomia.class)
							.addClass(HistogramaAguaLigacao.class)
							.addClass(HistogramaEsgotoEconomia.class)
							.addClass(HistogramaEsgotoLigacao.class)
							.addClass(QualidadeAguaPadrao.class)

							// gcom.faturamento.conta ContaMensagem
							.addClass(ContaCategoriaConsumoFaixa.class)
							.addClass(Conta.class)
							.addClass(ContaCategoria.class)
							.addClass(MotivoNaoEntregaDocumento.class)
							.addClass(Refaturamento.class)
							.addClass(Fatura.class)
							.addClass(FaturaItem.class)
							.addClass(ContaHistorico.class)
							.addClass(ContaImpostosDeduzidos.class)
							.addClass(ContaMotivoCancelamento.class)
							.addClass(ContaMotivoInclusao.class)
							.addClass(ContaMotivoRetificacao.class)
							.addClass(ContaMotivoRevisao.class)
							.addClass(ContaGeral.class)
							.addClass(ContaImpressao.class)
							.addClass(ContaCategoriaConsumoFaixaHistorico.class)
							.addClass(ContaCategoriaHistorico.class)
							.addClass(ContaImpostosDeduzidosHistorico.class)
							.addClass(ContaTipo.class)
							// gcom.faturamento.debito
							.addClass(DebitoCobrado.class)
							.addClass(DebitoTipo.class)
							.addClass(DebitoACobrar.class)
							.addClass(DebitoACobrarCategoria.class)
							.addClass(DebitoCobradoHistorico.class)
							.addClass(DebitoCobradoCategoria.class)
							.addClass(DebitoACobrarHistorico.class)
							.addClass(DebitoCreditoSituacao.class)
							.addClass(ContaMensagem.class)
							.addClass(DebitoACobrarGeral.class)

							// gcom.faturamento.credito
							.addClass(CreditoRealizado.class)
							.addClass(CreditoARealizar.class)
							.addClass(CreditoARealizarCategoria.class)
							.addClass(CreditoRealizadoHistorico.class)
							.addClass(CreditoRealizadoCategoria.class)
							.addClass(CreditoTipo.class)
							.addClass(CreditoARealizarHistorico.class)
							.addClass(CreditoOrigem.class)
							.addClass(CreditoARealizarGeral.class)

							// gcom.faturamento.consumotarifa
							.addClass(ConsumoTarifa.class)
							.addClass(ConsumoTarifaVigencia.class)
							.addClass(ConsumoTarifaCategoria.class)
							.addClass(ConsumoTarifaFaixa.class)
							.addClass(LocalEntregaDocumento.class)

							// gcom.faturamento.debito
							.addClass(DebitoFaixaValore.class)

							// *************************************//
							// CLASSES DO PACOTE gcom.micromedicao //
							// *************************************//
							.addClass(Rota.class)
							.addClass(RateioTipo.class)
							.addClass(ImovelTestesMedicaoConsumo.class)
							// gcom.micromedicao.hidrometro
							.addClass(HidrometroCapacidade.class)
							.addClass(Hidrometro.class)
							.addClass(HidrometroMotivoBaixa.class)
							.addClass(HidrometroClasseMetrologica.class)
							.addClass(HidrometroMarca.class)
							.addClass(HidrometroMovimentacao.class)
							.addClass(HidrometroMotivoMovimentacao.class)
							.addClass(HidrometroLocalArmazenagem.class)
							.addClass(HidrometroSituacao.class)
							.addClass(HidrometroDiametro.class)
							.addClass(HidrometroInstalacaoHistorico.class)
							.addClass(HidrometroLocalInstalacao.class)
							.addClass(HidrometroTipo.class)
							.addClass(HidrometroProtecao.class)
							.addClass(HidrometroMovimentado.class)
							.addClass(Leiturista.class)
							.addClass(ArquivoTextoRoteiroEmpresa.class)
							.addClass(RoteiroEmpresa.class)
							.addClass(MovimentoRoteiroEmpresa.class)

							// gcom.micromedicao.leitura
							.addClass(LeituraTipo.class)
							.addClass(LeituraSituacao.class)
							.addClass(LeituraFaixaFalsa.class)
							.addClass(LeituraAnormalidadeLeitura.class)
							.addClass(LeituraAnormalidade.class)
							.addClass(LeituraFiscalizacao.class)
							.addClass(LeituraAnormalidadeConsumo.class)
							// gcom.micromedicao.medicao //
							.addClass(MedicaoHistorico.class)
							.addClass(MedicaoTipo.class)
							// gcom.micromedicao.consumo //
							.addClass(ConsumoHistorico.class)
							.addClass(ConsumoTipo.class)
							.addClass(ConsumoAnormalidade.class)
							.addClass(LigacaoTipo.class)
							.addClass(ResumoAnormalidadeConsumo.class)
							.addClass(ResumoAnormalidadeLeitura.class)
							.addClass(ConsumoHistoricoAnterior.class)
							.addClass(MedicaoHistoricoAnterior.class)
							.addClass(SituacaoTransmissaoLeitura.class)
							.addClass(ConsumoFixoUnidade.class)
							.addClass(ConsumoFixoFaixa.class)

							// ************************************//
							// CLASSES DO PACOTE gcom.financeiro //
							// ************************************//
							.addClass(LancamentoContabil.class)
							.addClass(LancamentoResumo.class)
							.addClass(LancamentoResumoValorTipo.class)
							.addClass(LancamentoResumoConta.class)
							.addClass(LancamentoResumoContaHistorico.class)
							.addClass(FinanciamentoTipo.class)
							.addClass(LancamentoContabilItem.class)
							.addClass(ContaContabil.class)
							.addClass(LancamentoOrigem.class)
							.addClass(ResumoFaturamento.class)
							.addClass(LancamentoItem.class)
							.addClass(LancamentoItemContabil.class)
							.addClass(LancamentoTipoItem.class)
							.addClass(LancamentoTipo.class)
							.addClass(DevedoresDuvidososContabilParametro.class)
							.addClass(ContaAReceberContabil.class)
							// ************************************//
							// CLASSES DO PACOTE gcom.arrecadacao //
							// ************************************//
							// gcom.arrecadacao.banco

							.addClass(ResumoArrecadacao.class)
							.addClass(Banco.class)
							.addClass(Agencia.class)
							// gcom.arrecadacao.pagamento
							.addClass(Pagamento.class)
							.addClass(PagamentoSituacao.class)
							.addClass(GuiaPagamento.class)
							.addClass(GuiaPagamentoHistorico.class)
							.addClass(GuiaPagamentoCategoriaHistorico.class)
							// gcom.arrecadacao.debito
							.addClass(DebitoAutomatico.class)
							.addClass(DebitoAutomaticoRetornoCodigo.class)
							.addClass(DebitoAutomaticoMovimento.class)
							.addClass(GuiaPagamentoCategoria.class)
							.addClass(MetasArrecadacao.class)

							.addClass(DevolucaoHistorico.class)
							.addClass(GuiaPagamentoPrestacao.class)
							.addClass(GuiaPagamentoPrestacaoHistorico.class)
							// *************************************//
							// CLASSES DO PACOTE gcom.operacional //
							// *************************************//
							.addClass(Bacia.class)
							.addClass(DistritoOperacional.class)
							.addClass(DivisaoEsgoto.class)
							.addClass(SistemaAbastecimento.class)
							.addClass(SistemaEsgoto.class)
							.addClass(SistemaEsgotoTratamentoTipo.class)
							.addClass(AbastecimentoProgramacao.class)
							.addClass(ManutencaoProgramacao.class)
							.addClass(SetorAbastecimento.class)
							.addClass(ZonaAbastecimento.class)
							.addClass(ZonaPressao.class)
							.addClass(ProducaoAgua.class)
							// ************************************//
							// CLASSES DO PACOTE gcom.seguranca //
							// ************************************//
							// gcom.seguranca.acesso
							.addClass(AlteracaoTipo.class)
							.addClass(UsuarioTipo.class)
							.addClass(TabelaLinhaAlteracao.class)
							.addClass(TabelaLinhaColunaAlteracao.class)
							.addClass(TabelaColuna.class)
							.addClass(Tabela.class)
							.addClass(UsuarioAcao.class)
							.addClass(UsuarioFavorito.class)
							.addClass(GrupoAcesso.class)
							// gcom.seguranca.transacao
							.addClass(SgbdTabela.class).addClass(SgbdTabelaColuna.class).addClass(UsuarioSituacao.class).addClass(
											UsuarioPermissaoEspecial.class).addClass(UsuarioAlteracao.class).addClass(
											UsuarioGrupoRestricao.class).addClass(UsuarioGrupo.class).addClass(UsuarioAbrangencia.class)
							.addClass(Usuario.class).addClass(ResolucaoDiretoria.class).addClass(CreditoRealizadoCategoriaHistorico.class)
							.addClass(CreditoARealizarCategoriaHistorico.class).addClass(DebitoCobradoCategoriaHistorico.class).addClass(
											DebitoACobrarCategoriaHistorico.class).addClass(PermissaoEspecial.class).addClass(
											AvisoDeducoes.class).addClass(AvisoBancario.class).addClass(AvisoAcerto.class).addClass(
											ArrecadadorMovimentoItem.class).addClass(ArrecadadorMovimento.class).addClass(
											ArrecadadorContratoTarifa.class).addClass(ParcelamentoTipo.class).addClass(
											ParcelamentoSituacao.class).addClass(ParcelamentoQuantidadeReparcelamento.class).addClass(
											ParcelamentoQuantidadePrestacao.class).addClass(ParcelamentoPerfil.class).addClass(
											ParcelamentoItem.class).addClass(ParcelamentoDescontoInatividade.class).addClass(
											ParcelamentoDescontoAntiguidade.class).addClass(Parcelamento.class).addClass(
											DocumentoTipo.class).addClass(DocumentoEmissaoForma.class).addClass(DevolucaoSituacao.class)
							.addClass(Devolucao.class).addClass(DeducaoTipo.class).addClass(GuiaDevolucao.class).addClass(
											GrupoFuncionalidadeOperacao.class).addClass(Grupo.class).addClass(
											FuncionalidadeDependencia.class).addClass(Funcionalidade.class).addClass(
											ParcelamentoMotivoDesfazer.class).addClass(PagamentoHistorico.class).addClass(
											OperacaoEfetuada.class).addClass(Operacao.class).addClass(OperacaoTipo.class).addClass(
											OperacaoTabela.class).addClass(RegistroCodigo.class).addClass(ArrecadadorContrato.class)
							.addClass(Arrecadador.class).addClass(ArrecadacaoForma.class).addClass(CobrancaAcao.class).addClass(
											RotaAcaoCriterio.class).addClass(CobrancaAcaoAtividadeComando.class).addClass(
											CobrancaCriterioLinha.class).addClass(CobrancaCriterio.class).addClass(
											CobrancaAtividadeComandoRota.class).addClass(CobrancaAtividade.class).addClass(
											CobrancaAcaoCronograma.class).addClass(CobrancaAcaoAtividadeCronograma.class).addClass(
											Modulo.class).addClass(ContratoDemanda.class).addClass(ContratoMotivoCancelamento.class)
							.addClass(CobrancaGrupoCronogramaMes.class).addClass(CobrancaDocumentoItem.class).addClass(
											CobrancaDocumento.class).addClass(ImovelSituacaoTipo.class).addClass(ImovelSituacao.class)
							.addClass(ContaBancaria.class).addClass(ArrecadacaoDadosDiarios.class).addClass(ResumoPendencia.class)
							.addClass(RecebimentoTipo.class).addClass(ArrecadacaoContabilParametros.class).addClass(MotivoCorte.class)
							.addClass(UnidadeProcessamento.class).addClass(ProcessoIniciado.class).addClass(ProcessoSituacao.class)
							.addClass(ProcessoFuncionalidade.class).addClass(FuncionalidadeIniciada.class).addClass(
											FuncionalidadeSituacao.class).addClass(Processo.class).addClass(ProcessoTipo.class).addClass(
											UnidadeIniciada.class).addClass(RelatorioGerado.class).addClass(Relatorio.class).addClass(
											UnidadeSituacao.class).addClass(RamalLocalInstalacao.class).addClass(
											ParametrosDevedoresDuvidosos.class).addClass(ParametrosDevedoresDuvidososItem.class).addClass(
											ResumoDevedoresDuvidosos.class).addClass(DbVersaoImplementada.class).addClass(EnvioEmail.class)
							.addClass(ResumoCobrancaAcaoEventual.class).addClass(ConsumoFaixaLigacao.class).addClass(
											ConsumoFaixaCategoria.class).addClass(ContaRevisaoFaixaValor.class).addClass(
											OperacaoOrdemExibicao.class)

							// gestaoLeiturista
							.addClass(MovimentoSegundaVia.class)

							// Migração ADA -- MUDAR PARA CARGA DINÂMICA
							.addClass(AvisoInteligest.class);

			sessionFactory = configuration.buildSessionFactory();
			sessionFactoryInicializada = true;
		}catch(Throwable ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a SessionFactory");
		}
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static Session getSession(){

		if(!sessionFactoryInicializada){
			inicializarSessionFactory();
		}
		Session retorno = null;

		try{
			retorno = sessionFactory.openSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static StatelessSession getStatelessSession(){

		StatelessSession retorno = null;

		try{
			retorno = sessionFactory.openStatelessSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static StatelessSession getStatelessSessionGerencial(){

		StatelessSession retorno = null;

		try{
			retorno = sessionFactory.openStatelessSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static Session getSessionGerencial(){

		Session retorno = null;

		try{
			retorno = sessionFactory.openSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session Gerencial");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	/*
	 * public static Session getSession(Interceptador interceptador) { Session
	 * retorno = null;
	 * try { //retorno = sessionFactory.openSession(interceptador); retorno =
	 * sessionFactory.openSession(interceptador); } catch (HibernateException
	 * ex) { throw new SistemaException("Hibernate - Erro ao criar a Session"); }
	 * return retorno; }
	 */

	/**
	 * Fecha a session
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 */
	public static void closeSession(Session session){

		if(session != null){
			try{

				// session.clear();
				session.close();
			}catch(HibernateException ex){
				throw new SistemaException("Hibernate - Erro ao fechar a Session");
			}

		}
	}

	/**
	 * Fecha a session
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 */
	public static void closeSession(StatelessSession session){

		if(session != null){
			try{
				session.close();
			}catch(HibernateException ex){
				throw new SistemaException("Hibernate - Erro ao fechar a Session");
			}

		}
	}

	/**
	 * Método que obtém o tamanho da propriedade da classe
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @param propertyName
	 *            Nome da propriedade da classe
	 * @return O valor de columnSize
	 */
	public static int getColumnSize(Class mappedClass, String propertyName){

		Configuration cfg = HibernateUtilParaTestes.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());
		Column col = null;
		Property hibProp = null;

		try{
			hibProp = pClass.getProperty(propertyName);

			Iterator it = hibProp.getColumnIterator();

			while(it.hasNext()){
				col = (Column) hibProp.getColumnIterator().next();
				break;
			}

		}catch(MappingException ex){
			throw new SistemaException("Hibernate - Erro no mapeamento");
		}

		return col.getLength();
	}

	/**
	 * Método que obtém o nome da coluna no banco da propriedade passada Caso
	 * nao tenha, retorna null
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @param propertyName
	 *            Nome da propriedade da classe
	 * @return nome da coluna
	 */
	public static String getNameColumn(Class mappedClass, String propertyName){

		String retorno = null;
		Configuration cfg = HibernateUtilParaTestes.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());
		Column col = null;
		Property hibProp = null;

		try{
			hibProp = pClass.getProperty(propertyName);

			Iterator it = hibProp.getColumnIterator();

			while(it.hasNext()){
				col = (Column) hibProp.getColumnIterator().next();
				break;
			}

			// retorno = col.getComment();
			// if (retorno == null || "".equals(retorno)) {
			if(col == null){
				retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + propertyName);
			}else{
				retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + col.getName());
			}
			if(retorno == null && col != null){

				retorno = col.getName();
			}

			if(col == null){
				retorno = null;
			}
			// }

		}catch(MappingException ex){
			try{

				hibProp = pClass.getIdentifierProperty();
				if(hibProp.getName().equalsIgnoreCase(propertyName)){

					Iterator it = hibProp.getColumnIterator();

					while(it.hasNext()){
						col = (Column) hibProp.getColumnIterator().next();
						break;
					}

					// retorno = col.getComment();
					// if (retorno == null || "".equals(retorno)) {
					// retorno = col.getName();
					// }

					retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + col.getName());
					if(retorno == null){
						retorno = col.getName();
					}
				}

			}catch(MappingException eex){
				eex.printStackTrace();
			}
		}

		return retorno;
	}

	/**
	 * Método que obtém o nome da tabela da classe passada
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @return O String nome da tablea
	 */
	public static String getNameTable(Class mappedClass){

		Configuration cfg = HibernateUtilParaTestes.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());

		String retorno = pClass.getTable().getComment();
		if(retorno == null || "".equals(retorno)){
			retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName());
			if(retorno == null){
				retorno = pClass.getTable().getName();
			}

		}

		return retorno;
	}

	/**
	 * Retorna a que classe está mapeada a tabela passada
	 * 
	 * @param tableName
	 *            caminho da tabela
	 * @return caminho da classe
	 */
	public static String getClassName(String tableName){

		Configuration cfg = HibernateUtilParaTestes.getConfig();
		if(cfg != null){
			Iterator iter = cfg.getClassMappings();
			while(iter.hasNext()){
				PersistentClass classe = (PersistentClass) iter.next();
				if(classe.getTable().getName().equals(tableName)){
					return classe.getClassName();
				}
			}
		}
		return null;
	}

	/**
	 * Retorna o valor de config
	 * 
	 * @return O valor de config
	 */
	public static Configuration getConfig(){

		return configuration;
	}

	/**
	 * The main program for the HibernateUtil class
	 * 
	 * @param args
	 *            The command line arguments
	 * @throws ErroRepositorioException
	 */
	public static void main(String[] args) throws ErroRepositorioException{

		// BasicConfigurator.configure();
		inicializarSessionFactory();
		// transferirDebitoACobrarHistoricoParaDebitoACobrar(507561);
		// transferirCreditoARealizarHistoricoParaCreditoARealizar(713);
	}

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove débitos a cobrar referentes
	 * ao parcelamento historico
	 * 
	 * @author Vitor
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public static void transferirDebitoACobrarHistoricoParaDebitoACobrar(Integer idDebitoACobrarHistorico) throws ErroRepositorioException{

		Session session = getSession();
		Transaction tx = null;

		try{
			tx = session.beginTransaction();
			String consulta = "select dac " + "from DebitoACobrarHistorico dac " + "where dac.id = :idDebitoACobrarHistorico";

			DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) session.createQuery(consulta).setInteger(
							"idDebitoACobrarHistorico", idDebitoACobrarHistorico.intValue()).uniqueResult();

			consulta = "select dac " + "from DebitoACobrarGeral dac " + "where dac.id = :idDebitoACobrarHistorico";

			Date ultimaAlt = new Date();
			DebitoACobrar debitoACobrar = new DebitoACobrar();
			try{
				PropertyUtils.copyProperties(debitoACobrar, debitoACobrarHistorico);
			}catch(IllegalAccessException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(InvocationTargetException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DebitoCreditoSituacao dcsAnteriorHistorico = debitoACobrarHistorico.getDebitoCreditoSituacaoAnterior();
			if(dcsAnteriorHistorico == null){
				DebitoCreditoSituacao debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
				debitoCreditoSituacaoAtual.setId(DebitoCreditoSituacao.NORMAL);
				debitoACobrar.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
			}else{
				debitoACobrar.setDebitoCreditoSituacaoAtual(dcsAnteriorHistorico);
			}

			debitoACobrar.setDebitoCreditoSituacaoAnterior(null);
			debitoACobrar.setUltimaAlteracao(ultimaAlt);
			debitoACobrar.setGeracaoDebito(debitoACobrarHistorico.getDebitoGeradoRealizar());

			DebitoACobrarGeral debitoACobrarGeral = (DebitoACobrarGeral) session.createQuery(consulta).setInteger(
							"idDebitoACobrarHistorico", idDebitoACobrarHistorico.intValue()).uniqueResult();
			debitoACobrarGeral.setIndicadorHistorico(ConstantesSistema.NAO);
			debitoACobrarGeral.setUltimaAlteracao(ultimaAlt);
			debitoACobrar.setDebitoACobrarGeral(debitoACobrarGeral);

			DebitoACobrarCategoria debitoACobrarCategoria = new DebitoACobrarCategoria();
			DebitoACobrarCategoriaHistorico debitoACobrarCategoriaHistorico = debitoACobrarHistorico.getDebitoACobrarCategoriasHistorico()
							.iterator().next();

			debitoACobrarCategoria.setCategoria(debitoACobrarCategoriaHistorico.getCategoria());
			debitoACobrarCategoria.setDebitoACobrar(debitoACobrar);
			debitoACobrarCategoria.setUltimaAlteracao(ultimaAlt);
			debitoACobrarCategoria.setValorCategoria(debitoACobrarCategoriaHistorico.getValorCategoria());
			debitoACobrarCategoria.setQuantidadeEconomia(debitoACobrarCategoriaHistorico.getQuantidadeEconomia());

			DebitoACobrarCategoriaPK pk = new DebitoACobrarCategoriaPK();
			pk.setCategoria(debitoACobrarCategoriaHistorico.getCategoria());
			pk.setDebitoACobrar(debitoACobrar);
			debitoACobrarCategoria.setComp_id(pk);

			Set<DebitoACobrarCategoria> debitoACobrarCategorias = new HashSet<DebitoACobrarCategoria>();
			debitoACobrarCategorias.add(debitoACobrarCategoria);
			debitoACobrar.setDebitoACobrarCategorias(debitoACobrarCategorias);

			session.delete(debitoACobrarCategoriaHistorico);
			session.delete(debitoACobrarHistorico);

			session.save(debitoACobrar);
			session.save(debitoACobrarCategoria);

			tx.commit(); // Flush happens automatically
			System.out.println("huuhuu");
		}catch(HibernateException e){
			e.printStackTrace();
			tx.rollback();
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}
	}

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove débitos a cobrar referentes
	 * ao parcelamento historico
	 * 
	 * @author Vitor
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public static void transferirCreditoARealizarHistoricoParaCreditoARealizar(Integer idCreditoARealizarHistorico)
					throws ErroRepositorioException{

		Session session = getSession();
		Transaction tx = null;

		try{
			tx = session.beginTransaction();
			String consulta = "select dac from CreditoARealizarHistorico dac where dac.id = :idCreditoARealizarHistorico";

			CreditoARealizarHistorico creditoARealizarHistorico = (CreditoARealizarHistorico) session.createQuery(consulta).setInteger(
							"idCreditoARealizarHistorico", idCreditoARealizarHistorico.intValue()).uniqueResult();

			consulta = "select dac from CreditoARealizarGeral dac where dac.id = :idCreditoARealizarHistorico";

			Date ultimaAlt = new Date();
			CreditoARealizar creditoARealizar = new CreditoARealizar();
			try{
				PropertyUtils.copyProperties(creditoARealizar, creditoARealizarHistorico);
			}catch(IllegalAccessException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(InvocationTargetException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DebitoCreditoSituacao dcsAnteriorHistorico = creditoARealizarHistorico.getDebitoCreditoSituacaoAnterior();
			if(dcsAnteriorHistorico == null){
				DebitoCreditoSituacao debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
				debitoCreditoSituacaoAtual.setId(DebitoCreditoSituacao.NORMAL);
				creditoARealizar.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
			}else{
				creditoARealizar.setDebitoCreditoSituacaoAtual(dcsAnteriorHistorico);
			}

			creditoARealizar.setDebitoCreditoSituacaoAnterior(null);
			creditoARealizar.setUltimaAlteracao(ultimaAlt);
			creditoARealizar.setGeracaoCredito(creditoARealizarHistorico.getGeracaoCreditoARealizar());

			CreditoARealizarGeral creditoARealizarGeral = (CreditoARealizarGeral) session.createQuery(consulta).setInteger(
							"idCreditoARealizarHistorico", idCreditoARealizarHistorico.intValue()).uniqueResult();
			creditoARealizarGeral.setIndicadorHistorico(ConstantesSistema.NAO);
			creditoARealizarGeral.setUltimaAlteracao(ultimaAlt);
			creditoARealizar.setCreditoARealizarGeral(creditoARealizarGeral);

			CreditoARealizarCategoria creditoARealizarCategoria = new CreditoARealizarCategoria();
			CreditoARealizarCategoriaHistorico creditoARealizarCategoriaHistorico = creditoARealizarHistorico
							.getCreditoARealizarCategoriasHistorico().iterator().next();

			creditoARealizarCategoria.setCategoria(creditoARealizarCategoriaHistorico.getCategoria());
			creditoARealizarCategoria.setCreditoARealizar(creditoARealizar);
			creditoARealizarCategoria.setUltimaAlteracao(ultimaAlt);
			creditoARealizarCategoria.setValorCategoria(creditoARealizarCategoriaHistorico.getValorCategoria());
			creditoARealizarCategoria.setQuantidadeEconomia(creditoARealizarCategoriaHistorico.getQuantidadeEconomia());

			CreditoARealizarCategoriaPK pk = new CreditoARealizarCategoriaPK();
			pk.setCategoria(creditoARealizarCategoriaHistorico.getCategoria());
			pk.setCreditoARealizar(creditoARealizar);
			creditoARealizarCategoria.setComp_id(pk);

			Set<CreditoARealizarCategoria> creditoARealizarCategorias = new HashSet<CreditoARealizarCategoria>();
			creditoARealizarCategorias.add(creditoARealizarCategoria);
			creditoARealizar.setCreditoARealizarCategoria(creditoARealizarCategorias);

			session.delete(creditoARealizarCategoriaHistorico);
			session.delete(creditoARealizarHistorico);

			session.save(creditoARealizar);
			session.save(creditoARealizarCategoria);

			tx.commit(); // Flush happens automatically
			System.out.println("huuhuu");
		}catch(HibernateException e){
			e.printStackTrace();
			tx.rollback();
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}
	}

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Pesquisa os créditos a realizar por categoria.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param creditoARealizar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public static Collection pesquisarCreditoARealizarCategoria(CreditoARealizar creditoARealizar) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = getSession();
		String consulta = null;

		try{
			consulta = "select cacg " + "from " + "CreditoARealizarCategoria cacg "
							+ "where cacg.comp_id.creditoARealizar.id = :idCreditoARealizar";

			retorno = session.createQuery(consulta).setInteger("idCreditoARealizar", creditoARealizar.getId()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0155] - Encerrar Faturamento do Mês Item 05 - pesquisar créditos
	 * realizados de contas canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @param idConta
	 *            Código da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public static Collection<CreditoRealizadoHistorico> pesquisarCreditosRealizadosCanceladosPorMesAnoReferenciaContabilHistorico2(
					int anoMesReferenciaContabil, Integer idConta) throws ErroRepositorioException{

		Collection retornoSQL = null;
		Collection<CreditoRealizadoHistorico> retorno = new ArrayList<CreditoRealizadoHistorico>();
		// cria uma sessão com o hibernate
		Session session = getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select crh.crhi_id, crh.cnta_id, crh.crhi_tmcreditorealizado, crh.crhi_cdsetorcomercial, crh.crhi_nnquadra, "
							+ "crh.crhi_nnlote, crh.crhi_nnsublote, crh.crhi_amreferenciacredito, crh.crhi_amcobrancacredito, crh.crhi_vlcredito, "
							+ "crh.crhi_nnprestacao, crh.crhi_nnprestacaocredito, crh.crhi_tmultimaalteracao, crh.qdra_id, crh.loca_id, "
							+ "crh.crti_id, crh.lict_id, crh.crog_id  " + "from credito_realizado_historico crh, conta_historico cnta "
							+ "where crh.cnta_id = cnta.cnta_id and " + "crh.cnta_id = " + idConta
							+ " and cnta.cnhi_amreferenciacontabil <= " + anoMesReferenciaContabil + " and (cnta.dcst_idatual = "
							+ DebitoCreditoSituacao.CANCELADA + " or cnta.dcst_idatual = "
							+ DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO + " or cnta.dcst_idatual = "
							+ DebitoCreditoSituacao.PARCELADA + " or cnta.dcst_idatual = " + DebitoCreditoSituacao.ARRASTADA + ")";

			// executa o hql
			retornoSQL = session.createSQLQuery(consulta).addScalar("crhi_id", Hibernate.INTEGER).addScalar("cnta_id", Hibernate.INTEGER)
							.addScalar("crhi_tmcreditorealizado", Hibernate.TIMESTAMP)
							.addScalar("crhi_cdsetorcomercial", Hibernate.INTEGER).addScalar("crhi_nnquadra", Hibernate.INTEGER).addScalar(
											"crhi_nnlote", Hibernate.SHORT).addScalar("crhi_nnsublote", Hibernate.SHORT).addScalar(
											"crhi_amreferenciacredito", Hibernate.INTEGER).addScalar("crhi_amcobrancacredito",
											Hibernate.INTEGER).addScalar("crhi_vlcredito", Hibernate.BIG_DECIMAL).addScalar(
											"crhi_nnprestacao", Hibernate.SHORT).addScalar("crhi_nnprestacaocredito", Hibernate.SHORT)
							.addScalar("crhi_tmultimaalteracao", Hibernate.TIMESTAMP).addScalar("qdra_id", Hibernate.INTEGER).addScalar(
											"loca_id", Hibernate.INTEGER).addScalar("crti_id", Hibernate.INTEGER).addScalar("lict_id",
											Hibernate.INTEGER).addScalar("qdra_id", Hibernate.INTEGER).addScalar("crog_id",
											Hibernate.INTEGER).list();

			for(Iterator iterator = retornoSQL.iterator(); iterator.hasNext();){
				Object[] objArray = (Object[]) iterator.next();
				CreditoRealizadoHistorico creditoRealizadoTemp = new CreditoRealizadoHistorico();
				creditoRealizadoTemp.setId((Integer) objArray[0]);
				ContaHistorico contaHistorico = new ContaHistorico();
				contaHistorico.setId((Integer) objArray[1]);
				creditoRealizadoTemp.setContaHistorico(contaHistorico);
				creditoRealizadoTemp.setDataHoraCreditoRealizado((Date) objArray[2]);
				/*
				 * creditoRealizadoHistoricoTemp
				 * .setGeracaoHistoricoCredito(new Date());
				 */
				creditoRealizadoTemp.setCodigoSetorComercial((Integer) objArray[3]);
				creditoRealizadoTemp.setNumeroQuadra((Integer) objArray[4]);
				creditoRealizadoTemp.setNumeroLote((Short) objArray[5]);
				creditoRealizadoTemp.setNumeroSubLote((Short) objArray[6]);
				creditoRealizadoTemp.setAnoMesReferenciaCredito((Integer) objArray[7]);
				creditoRealizadoTemp.setAnoMesCobrancaCredito((Integer) objArray[8]);
				creditoRealizadoTemp.setValorCredito((BigDecimal) objArray[9]);
				creditoRealizadoTemp.setNumeroPrestacao((Short) objArray[10]);
				creditoRealizadoTemp.setNumeroPrestacaoCredito((Short) objArray[11]);
				creditoRealizadoTemp.setUltimaAlteracao((Date) objArray[12]);

				Quadra q = new Quadra();
				q.setId((Integer) objArray[13]);
				creditoRealizadoTemp.setQuadra(q);
				Localidade loc = new Localidade();
				loc.setId((Integer) objArray[14]);
				creditoRealizadoTemp.setLocalidade(loc);
				CreditoTipo ct = new CreditoTipo();
				ct.setId((Integer) objArray[15]);
				creditoRealizadoTemp.setCreditoTipo(ct);
				LancamentoItemContabil lic = new LancamentoItemContabil();
				lic.setId((Integer) objArray[16]);
				creditoRealizadoTemp.setLancamentoItemContabil(lic);
				CreditoOrigem co = new CreditoOrigem();
				co.setId((Integer) objArray[17]);
				creditoRealizadoTemp.setCreditoOrigem(co);
				retorno.add(creditoRealizadoTemp);
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0155] - Encerrar Faturamento do Mês Item 05 - pesquisar créditos
	 * realizados de contas canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @param idConta
	 *            Código da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public static Collection<CreditoRealizadoHistorico> pesquisarCreditosRealizadosCanceladosPorMesAnoReferenciaContabilHistorico(
					int anoMesReferenciaContabil, Integer idConta) throws ErroRepositorioException{

		Collection<CreditoRealizadoHistorico> retorno = null;

		// cria uma sessão com o hibernate
		Session session = getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select crrz " + "from CreditoRealizadoHistorico crrz " + "inner join fetch crrz.contaHistorico cnta "
							+ "where cnta.anoMesReferenciaContabil <= :anoMesReferenciaContabil " + "and cnta.id = :idConta "
							+ "and (cnta.debitoCreditoSituacaoAtual = " + DebitoCreditoSituacao.CANCELADA + " or "
							+ "cnta.debitoCreditoSituacaoAtual = " + DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO + " or "
							+ "cnta.debitoCreditoSituacaoAtual = " + DebitoCreditoSituacao.PARCELADA + " or "
							+ "cnta.debitoCreditoSituacaoAtual = " + DebitoCreditoSituacao.ARRASTADA + ")";

			// executa o hql
			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("idConta",
							idConta).list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}

		return retorno;
	}

	private static void testPesquisarDebitoCobradoCategoriaHistorico() throws ErroRepositorioException{

		Integer idDebitoCobrado = new Integer(384324);
		Collection<DebitoCobradoCategoriaHistorico> colDebitoCobradoCategoriaHistorico = pesquisarDebitoCobradoCategoriaHistorico(idDebitoCobrado);
		for(DebitoCobradoCategoriaHistorico debitoCobradoCategoriaHistorico : colDebitoCobradoCategoriaHistorico){
			System.out.println(debitoCobradoCategoriaHistorico.getCategoria().getId());
			System.out.println(debitoCobradoCategoriaHistorico.getDebitoCobradoHistorico().getId());
			System.out.println(debitoCobradoCategoriaHistorico.getComp_id().getCategoria().getId());
			System.out.println(debitoCobradoCategoriaHistorico.getComp_id().getDebitoCobradoHistorico().getId());
		}
	}

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Pesquisa os débitos cobrados por categoria
	 * 
	 * @author Vitor Hora
	 * @date 21/08/2008
	 * @param idDebitoCobrado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public static Collection pesquisarDebitoCobradoCategoriaHistorico(Integer idDebitoCobrado) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = getSession();
		String consulta;

		try{
			consulta = "select DBHI_ID, CATG_ID, DCCH_QTECONOMIA, DCCH_VLCATEGORIA, DCCH_TMULTIMAALTERACAO from debito_cobrado_categoria_hist where dbhi_id = "
							+ idDebitoCobrado;

			retorno = session.createSQLQuery(consulta).addScalar("DBHI_ID", Hibernate.INTEGER).addScalar("CATG_ID", Hibernate.INTEGER)
							.addScalar("DCCH_QTECONOMIA", Hibernate.INTEGER).addScalar("DCCH_VLCATEGORIA", Hibernate.BIG_DECIMAL)
							.addScalar("DCCH_TMULTIMAALTERACAO", Hibernate.TIMESTAMP).list();
			DebitoCobradoCategoriaHistorico dcch = new DebitoCobradoCategoriaHistorico();
			Object[] retornoArray = (Object[]) retorno.toArray()[0];

			DebitoCobradoHistorico dch = new DebitoCobradoHistorico();
			dch.setId((Integer) retornoArray[0]);
			dcch.setDebitoCobradoHistorico(dch);

			Categoria cat = new Categoria();
			cat.setId((Integer) retornoArray[1]);
			dcch.setCategoria(cat);

			DebitoCobradoCategoriaHistoricoPK comp_id = new DebitoCobradoCategoriaHistoricoPK(dch, cat);
			dcch.setComp_id(comp_id);

			dcch.setQuantidadeEconomia((Integer) retornoArray[2]);
			dcch.setValorCategoria((BigDecimal) retornoArray[3]);
			dcch.setUltimaAlteracao((Date) retornoArray[4]);

			retorno = new ArrayList<DebitoCobradoCategoriaHistorico>();
			retorno.add(dcch);
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			closeSession(session);
		}

		return retorno;
	}

	public static Session getCurrentSession(){

		Session retorno = null;

		try{
			retorno = sessionFactory.getCurrentSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Carrega o arquivo de properties do sistema
	 */
	public static synchronized Properties lerArquivoPropriedades(String nomeArquivo){

		Properties prop = null;
		InputStream input = null;
		try{
			input = Util.class.getClassLoader().getResourceAsStream(nomeArquivo);
			prop = new Properties();
			prop.load(input);
		}catch(IOException ex){
			throw new SistemaException("erro.servidor");
		}finally{
			if(input != null){
				try{
					input.close();
				}catch(IOException exception){
					throw new SistemaException("erro.servidor");
				}
			}
		}
		return prop;
	}

}