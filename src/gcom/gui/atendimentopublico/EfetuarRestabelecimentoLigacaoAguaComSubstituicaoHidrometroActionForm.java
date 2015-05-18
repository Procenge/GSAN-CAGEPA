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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.imovel.Imovel;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroProtecao;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idOrdemServico;

	private String nomeOrdemServico;

	// Imóvel
	private String matriculaImovel;

	private String inscricaoImovel;

	private String clienteUsuario;

	private String cpfCnpjCliente;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	// Dados do Restabelecimento
	private String dataRestabelecimento;

	private String veioEncerrarOS;

	// Dados do Hidrômetro
	private String numeroHidrometro;

	private String dataInstalacao;

	private String localInstalacao;

	private String protecao;

	private String leituraInstalacao;

	private String numeroSelo;

	private String situacaoCavalete;

	private String idTipoMedicao;

	private String tipoMedicao;

	private String dataRetirada;

	private String leituraRetirada;

	private String idSituacaoHidrometro;

	private String situacaoHidrometro;

	private String idLocalArmazenagem;

	private String localArmazenagem;

	private String numeroHidrometroNovo;

	private String indicadorTrocaProtecao;

	private String indicadorTrocaRegistro;

	private String motivoSubstituicao;

	// Dados da Geração do Débito
	private String idTipoDebito;

	private String descricaoTipoDebito;

	private String valorDebito;

	private String motivoNaoCobranca;

	private String percentualCobranca;

	private String quantidadeParcelas;

	private String valorParcelas;

	private String valorServico;

	private String quantidade;

	private String unidadeMedida;

	private String alteracaoValor;

	private String qtdeMaxParcelas;

	private String idFuncionario;

	private String descricaoFuncionario;

	/**
	 * @return the idOrdemServico
	 */
	public String getIdOrdemServico(){

		return idOrdemServico;
	}

	/**
	 * @param idOrdemServico
	 *            the idOrdemServico to set
	 */
	public void setIdOrdemServico(String idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	/**
	 * @return the nomeOrdemServico
	 */
	public String getNomeOrdemServico(){

		return nomeOrdemServico;
	}

	/**
	 * @param nomeOrdemServico
	 *            the nomeOrdemServico to set
	 */
	public void setNomeOrdemServico(String nomeOrdemServico){

		this.nomeOrdemServico = nomeOrdemServico;
	}

	/**
	 * @return the matriculaImovel
	 */
	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	/**
	 * @param matriculaImovel
	 *            the matriculaImovel to set
	 */
	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	/**
	 * @return the clienteUsuario
	 */
	public String getClienteUsuario(){

		return clienteUsuario;
	}

	/**
	 * @param clienteUsuario
	 *            the clienteUsuario to set
	 */
	public void setClienteUsuario(String clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	/**
	 * @return the cpfCnpjCliente
	 */
	public String getCpfCnpjCliente(){

		return cpfCnpjCliente;
	}

	/**
	 * @param cpfCnpjCliente
	 *            the cpfCnpjCliente to set
	 */
	public void setCpfCnpjCliente(String cpfCnpjCliente){

		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	/**
	 * @return the situacaoLigacaoAgua
	 */
	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	/**
	 * @param situacaoLigacaoAgua
	 *            the situacaoLigacaoAgua to set
	 */
	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	/**
	 * @return the situacaoLigacaoEsgoto
	 */
	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	/**
	 * @param situacaoLigacaoEsgoto
	 *            the situacaoLigacaoEsgoto to set
	 */
	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	/**
	 * @return the dataRestabelecimento
	 */
	public String getDataRestabelecimento(){

		return dataRestabelecimento;
	}

	/**
	 * @param dataRestabelecimento
	 *            the dataRestabelecimento to set
	 */
	public void setDataRestabelecimento(String dataRestabelecimento){

		this.dataRestabelecimento = dataRestabelecimento;
	}

	/**
	 * @return the veioEncerrarOS
	 */
	public String getVeioEncerrarOS(){

		return veioEncerrarOS;
	}

	/**
	 * @param veioEncerrarOS
	 *            the veioEncerrarOS to set
	 */
	public void setVeioEncerrarOS(String veioEncerrarOS){

		this.veioEncerrarOS = veioEncerrarOS;
	}

	/**
	 * @return the numeroHidrometro
	 */
	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	/**
	 * @param numeroHidrometro
	 *            the numeroHidrometro to set
	 */
	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	/**
	 * @return the dataInstalacao
	 */
	public String getDataInstalacao(){

		return dataInstalacao;
	}

	/**
	 * @param dataInstalacao
	 *            the dataInstalacao to set
	 */
	public void setDataInstalacao(String dataInstalacao){

		this.dataInstalacao = dataInstalacao;
	}

	/**
	 * @return the localInstalacao
	 */
	public String getLocalInstalacao(){

		return localInstalacao;
	}

	/**
	 * @param localInstalacao
	 *            the localInstalacao to set
	 */
	public void setLocalInstalacao(String localInstalacao){

		this.localInstalacao = localInstalacao;
	}

	/**
	 * @return the protecao
	 */
	public String getProtecao(){

		return protecao;
	}

	/**
	 * @param protecao
	 *            the protecao to set
	 */
	public void setProtecao(String protecao){

		this.protecao = protecao;
	}

	/**
	 * @return the leituraInstalacao
	 */
	public String getLeituraInstalacao(){

		return leituraInstalacao;
	}

	/**
	 * @param leituraInstalacao
	 *            the leituraInstalacao to set
	 */
	public void setLeituraInstalacao(String leituraInstalacao){

		this.leituraInstalacao = leituraInstalacao;
	}

	/**
	 * @return the numeroSelo
	 */
	public String getNumeroSelo(){

		return numeroSelo;
	}

	/**
	 * @param numeroSelo
	 *            the numeroSelo to set
	 */
	public void setNumeroSelo(String numeroSelo){

		this.numeroSelo = numeroSelo;
	}

	/**
	 * @return the situacaoCavalete
	 */
	public String getSituacaoCavalete(){

		return situacaoCavalete;
	}

	/**
	 * @param situacaoCavalete
	 *            the situacaoCavalete to set
	 */
	public void setSituacaoCavalete(String situacaoCavalete){

		this.situacaoCavalete = situacaoCavalete;
	}

	/**
	 * @return the idTipoMedicao
	 */
	public String getIdTipoMedicao(){

		return idTipoMedicao;
	}

	/**
	 * @param idTipoMedicao
	 *            the idTipoMedicao to set
	 */
	public void setIdTipoMedicao(String idTipoMedicao){

		this.idTipoMedicao = idTipoMedicao;
	}

	/**
	 * @return the tipoMedicao
	 */
	public String getTipoMedicao(){

		return tipoMedicao;
	}

	/**
	 * @param tipoMedicao
	 *            the tipoMedicao to set
	 */
	public void setTipoMedicao(String tipoMedicao){

		this.tipoMedicao = tipoMedicao;
	}

	/**
	 * @return the dataRetirada
	 */
	public String getDataRetirada(){

		return dataRetirada;
	}

	/**
	 * @param dataRetirada
	 *            the dataRetirada to set
	 */
	public void setDataRetirada(String dataRetirada){

		this.dataRetirada = dataRetirada;
	}

	/**
	 * @return the leituraRetirada
	 */
	public String getLeituraRetirada(){

		return leituraRetirada;
	}

	/**
	 * @param leituraRetirada
	 *            the leituraRetirada to set
	 */
	public void setLeituraRetirada(String leituraRetirada){

		this.leituraRetirada = leituraRetirada;
	}

	/**
	 * @return the idSituacaoHidrometro
	 */
	public String getIdSituacaoHidrometro(){

		return idSituacaoHidrometro;
	}

	/**
	 * @param idSituacaoHidrometro
	 *            the idSituacaoHidrometro to set
	 */
	public void setIdSituacaoHidrometro(String idSituacaoHidrometro){

		this.idSituacaoHidrometro = idSituacaoHidrometro;
	}

	/**
	 * @return the situacaoHidrometro
	 */
	public String getSituacaoHidrometro(){

		return situacaoHidrometro;
	}

	/**
	 * @param situacaoHidrometro
	 *            the situacaoHidrometro to set
	 */
	public void setSituacaoHidrometro(String situacaoHidrometro){

		this.situacaoHidrometro = situacaoHidrometro;
	}

	/**
	 * @return the idLocalArmazenagem
	 */
	public String getIdLocalArmazenagem(){

		return idLocalArmazenagem;
	}

	/**
	 * @param idLocalArmazenagem
	 *            the idLocalArmazenagem to set
	 */
	public void setIdLocalArmazenagem(String idLocalArmazenagem){

		this.idLocalArmazenagem = idLocalArmazenagem;
	}

	/**
	 * @return the localArmazenagem
	 */
	public String getLocalArmazenagem(){

		return localArmazenagem;
	}

	/**
	 * @param localArmazenagem
	 *            the localArmazenagem to set
	 */
	public void setLocalArmazenagem(String localArmazenagem){

		this.localArmazenagem = localArmazenagem;
	}

	/**
	 * @return the numeroHidrometroNovo
	 */
	public String getNumeroHidrometroNovo(){

		return numeroHidrometroNovo;
	}

	/**
	 * @param numeroHidrometroNovo
	 *            the numeroHidrometroNovo to set
	 */
	public void setNumeroHidrometroNovo(String numeroHidrometroNovo){

		this.numeroHidrometroNovo = numeroHidrometroNovo;
	}

	/**
	 * @return the indicadorTrocaProtecao
	 */
	public String getIndicadorTrocaProtecao(){

		return indicadorTrocaProtecao;
	}

	/**
	 * @param indicadorTrocaProtecao
	 *            the indicadorTrocaProtecao to set
	 */
	public void setIndicadorTrocaProtecao(String indicadorTrocaProtecao){

		this.indicadorTrocaProtecao = indicadorTrocaProtecao;
	}

	/**
	 * @return the indicadorTrocaRegistro
	 */
	public String getIndicadorTrocaRegistro(){

		return indicadorTrocaRegistro;
	}

	/**
	 * @param indicadorTrocaRegistro
	 *            the indicadorTrocaRegistro to set
	 */
	public void setIndicadorTrocaRegistro(String indicadorTrocaRegistro){

		this.indicadorTrocaRegistro = indicadorTrocaRegistro;
	}

	/**
	 * @return the motivoSubstituicao
	 */
	public String getMotivoSubstituicao(){

		return motivoSubstituicao;
	}

	/**
	 * @param motivoSubstituicao
	 *            the motivoSubstituicao to set
	 */
	public void setMotivoSubstituicao(String motivoSubstituicao){

		this.motivoSubstituicao = motivoSubstituicao;
	}

	/**
	 * @return the idTipoDebito
	 */
	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	/**
	 * @param idTipoDebito
	 *            the idTipoDebito to set
	 */
	public void setIdTipoDebito(String idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	/**
	 * @return the descricaoTipoDebito
	 */
	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	/**
	 * @param descricaoTipoDebito
	 *            the descricaoTipoDebito to set
	 */
	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	/**
	 * @return the valorDebito
	 */
	public String getValorDebito(){

		return valorDebito;
	}

	/**
	 * @param valorDebito
	 *            the valorDebito to set
	 */
	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	/**
	 * @return the motivoNaoCobranca
	 */
	public String getMotivoNaoCobranca(){

		return motivoNaoCobranca;
	}

	/**
	 * @param motivoNaoCobranca
	 *            the motivoNaoCobranca to set
	 */
	public void setMotivoNaoCobranca(String motivoNaoCobranca){

		this.motivoNaoCobranca = motivoNaoCobranca;
	}

	/**
	 * @return the percentualCobranca
	 */
	public String getPercentualCobranca(){

		return percentualCobranca;
	}

	/**
	 * @param percentualCobranca
	 *            the percentualCobranca to set
	 */
	public void setPercentualCobranca(String percentualCobranca){

		this.percentualCobranca = percentualCobranca;
	}

	/**
	 * @return the quantidadeParcelas
	 */
	public String getQuantidadeParcelas(){

		return quantidadeParcelas;
	}

	/**
	 * @param quantidadeParcelas
	 *            the quantidadeParcelas to set
	 */
	public void setQuantidadeParcelas(String quantidadeParcelas){

		this.quantidadeParcelas = quantidadeParcelas;
	}

	/**
	 * @return the valorParcelas
	 */
	public String getValorParcelas(){

		return valorParcelas;
	}

	/**
	 * @param valorParcelas
	 *            the valorParcelas to set
	 */
	public void setValorParcelas(String valorParcelas){

		this.valorParcelas = valorParcelas;
	}

	/**
	 * @return the valorServico
	 */
	public String getValorServico(){

		return valorServico;
	}

	/**
	 * @param valorServico
	 *            the valorServico to set
	 */
	public void setValorServico(String valorServico){

		this.valorServico = valorServico;
	}

	/**
	 * @return the quantidade
	 */
	public String getQuantidade(){

		return quantidade;
	}

	/**
	 * @param quantidade
	 *            the quantidade to set
	 */
	public void setQuantidade(String quantidade){

		this.quantidade = quantidade;
	}

	/**
	 * @return the unidadeMedida
	 */
	public String getUnidadeMedida(){

		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida
	 *            the unidadeMedida to set
	 */
	public void setUnidadeMedida(String unidadeMedida){

		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the alteracaoValor
	 */
	public String getAlteracaoValor(){

		return alteracaoValor;
	}

	/**
	 * @param alteracaoValor
	 *            the alteracaoValor to set
	 */
	public void setAlteracaoValor(String alteracaoValor){

		this.alteracaoValor = alteracaoValor;
	}

	/**
	 * @return the qtdeMaxParcelas
	 */
	public String getQtdeMaxParcelas(){

		return qtdeMaxParcelas;
	}

	/**
	 * @param qtdeMaxParcelas
	 *            the qtdeMaxParcelas to set
	 */
	public void setQtdeMaxParcelas(String qtdeMaxParcelas){

		this.qtdeMaxParcelas = qtdeMaxParcelas;
	}

	public HidrometroInstalacaoHistorico setFormValues(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico){

		/*
		 * Campos obrigatórios
		 */

		// data instalação
		hidrometroInstalacaoHistorico.setDataInstalacao(Util.converteStringParaDate(getDataInstalacao()));

		if(idTipoMedicao.equalsIgnoreCase("" + MedicaoTipo.POCO)){

			Imovel imovel = new Imovel();
			imovel.setId(new Integer(matriculaImovel));

			hidrometroInstalacaoHistorico.setImovel(imovel);
			hidrometroInstalacaoHistorico.setLigacaoAgua(null);

		}else if(idTipoMedicao.equalsIgnoreCase("" + MedicaoTipo.LIGACAO_AGUA)){

			LigacaoAgua ligacaoAgua = new LigacaoAgua();
			ligacaoAgua.setId(new Integer(matriculaImovel));

			hidrometroInstalacaoHistorico.setLigacaoAgua(ligacaoAgua);
			hidrometroInstalacaoHistorico.setImovel(null);
		}
		// medição tipo
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		medicaoTipo.setId(Integer.parseInt(getIdTipoMedicao()));
		hidrometroInstalacaoHistorico.setMedicaoTipo(medicaoTipo);

		// hidrômetro local instalação
		HidrometroLocalInstalacao hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		hidrometroLocalInstalacao.setId(Integer.parseInt(getLocalInstalacao()));
		hidrometroInstalacaoHistorico.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);

		// proteção
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
		hidrometroProtecao.setId(Integer.parseInt(getProtecao()));
		hidrometroInstalacaoHistorico.setHidrometroProtecao(hidrometroProtecao);

		// leitura instalação
		if(getLeituraInstalacao() != null && !getLeituraInstalacao().trim().equalsIgnoreCase("")){
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(Integer.parseInt(getLeituraInstalacao()));
		}else{
			hidrometroInstalacaoHistorico.setNumeroLeituraInstalacao(0);
		}

		// cavalete
		hidrometroInstalacaoHistorico.setIndicadorExistenciaCavalete(Short.parseShort(getSituacaoCavalete()));

		/*
		 * Campos opcionais
		 */
		// leitura corte
		hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);

		// leitura supressão
		hidrometroInstalacaoHistorico.setNumeroLeituraSupressao(null);

		// numero selo
		if(getNumeroSelo() != null && !getNumeroSelo().equalsIgnoreCase("")){
			hidrometroInstalacaoHistorico.setNumeroSelo(getNumeroSelo());
		}else{
			hidrometroInstalacaoHistorico.setNumeroSelo(null);
		}

		// numero hidrometro
		hidrometroInstalacaoHistorico.setNumeroHidrometro(getNumeroHidrometroNovo());


		hidrometroInstalacaoHistorico.setDataImplantacaoSistema(new Date());

		// indicador instalação substituição
		hidrometroInstalacaoHistorico.setIndicadorInstalcaoSubstituicao(new Short("1"));

		// data última alteração
		hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());

		if(getIndicadorTrocaProtecao() != null && !getIndicadorTrocaProtecao().equalsIgnoreCase("")){
			hidrometroInstalacaoHistorico.setIndicadorTrocaProtecao(new Short(getIndicadorTrocaProtecao()));
		}
		if(getIndicadorTrocaRegistro() != null && !getIndicadorTrocaRegistro().equalsIgnoreCase("")){
			hidrometroInstalacaoHistorico.setIndicadorTrocaRegistro(new Short(getIndicadorTrocaRegistro()));
		}

		return hidrometroInstalacaoHistorico;
	}

	public void setIdFuncionario(String idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public String getIdFuncionario(){

		return idFuncionario;
	}

	public void setDescricaoFuncionario(String descricaoFuncionario){

		this.descricaoFuncionario = descricaoFuncionario;
	}

	public String getDescricaoFuncionario(){

		return descricaoFuncionario;
	}

}
