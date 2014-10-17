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

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ordemservico.SupressaoMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class LigacaoAgua
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date dataImplantacao;

	/** nullable persistent field */
	private Date dataLigacao;

	/** nullable persistent field */
	private Date dataSupressao;

	/** nullable persistent field */
	private Date dataCorte;

	/** nullable persistent field */
	private Date dataReligacao;

	/** nullable persistent field */
	private Integer numeroSeloCorte;

	/** nullable persistent field */
	private Integer numeroSeloSupressao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Date dataRestabelecimentoAgua;

	/** nullable persistent field */
	private Integer numeroConsumoMinimoAgua;

	/** nullable persistent field */
	private Short laguIcemissaocortesupressao;

	/** nullable persistent field */
	private Imovel imovel;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.SupressaoTipo supressaoTipo;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo emissaoOrdemCobrancaTipo;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil ligacaoAguaPerfil;

	/** persistent field */
	private HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro ligacaoAguaDiametro;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.CorteTipo corteTipo;

	/** persistent field */
	private gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial ligacaoAguaMaterial;

	/** nullable persistent field */
	private gcom.atendimentopublico.ligacaoagua.MotivoCorte motivoCorte;

	/** nullable persistent field */
	private SupressaoMotivo supressaoMotivo;

	/** nullable persistent field */
	private Date dataCorteAdministrativo;

	private RamalLocalInstalacao ramalLocalInstalacao;

	/** nullable persistent field */
	private String numeroLacre;

	private Set medicaoHistoricos;

	private Integer numeroCorte;

	private Integer numeroCorteAdministrativo;

	private Integer numeroSupressaoAgua;

	private Funcionario funcionarioCorte;

	private Funcionario funcionarioLigacaoAgua;

	private Funcionario funcionarioSupressaoAgua;

	private Funcionario funcionarioReligacaoAgua;

	private Funcionario funcionarioRestabelecimento;

	private Integer numeroSupressaoParcial = new Integer(0);

	private Integer numeroSupressaoTotal = new Integer(0);

	private Integer numeroSupressaoFaltaPagamento = new Integer(0);

	private Integer numeroSupressaoInfracao = new Integer(0);

	private Integer numeroSupressaoPedido = new Integer(0);

	private Integer numeroReligacao = new Integer(0);

	private Integer numeroRestabelecimento = new Integer(0);

	private Integer numeroRestabelecimentoParcial = new Integer(0);

	private Integer numeroRestabelecimentoTotal = new Integer(0);

	private Integer numeroCorteFaltaPagamento = new Integer(0);

	private Integer numeroCorteInfracao = new Integer(0);

	private Integer numeroCortePedido = new Integer(0);

	private CorteRegistroTipo corteRegistroTipo;

	public Integer getNumeroCorteFaltaPagamento(){

		return numeroCorteFaltaPagamento;
	}

	public void setNumeroCorteFaltaPagamento(Integer numeroCorteFaltaPagamento){

		this.numeroCorteFaltaPagamento = numeroCorteFaltaPagamento;
	}

	public Integer getNumeroCorteInfracao(){

		return numeroCorteInfracao;
	}

	public void setNumeroCorteInfracao(Integer numeroCorteInfracao){

		this.numeroCorteInfracao = numeroCorteInfracao;
	}

	public Integer getNumeroCortePedido(){

		return numeroCortePedido;
	}

	public void setNumeroCortePedido(Integer numeroCortePedido){

		this.numeroCortePedido = numeroCortePedido;
	}

	public Integer getNumeroRestabelecimento(){

		return numeroRestabelecimento;
	}

	public void setNumeroRestabelecimento(Integer numeroRestabelecimento){

		this.numeroRestabelecimento = numeroRestabelecimento;
	}

	public Integer getNumeroRestabelecimentoParcial(){

		return numeroRestabelecimentoParcial;
	}

	public void setNumeroRestabelecimentoParcial(Integer numeroRestabelecimentoParcial){

		this.numeroRestabelecimentoParcial = numeroRestabelecimentoParcial;
	}

	public Integer getNumeroRestabelecimentoTotal(){

		return numeroRestabelecimentoTotal;
	}

	public void setNumeroRestabelecimentoTotal(Integer numeroRestabelecimentoTotal){

		this.numeroRestabelecimentoTotal = numeroRestabelecimentoTotal;
	}

	public Integer getNumeroSupressaoParcial(){

		return numeroSupressaoParcial;
	}

	public void setNumeroSupressaoParcial(Integer numeroSupressaoParcial){

		this.numeroSupressaoParcial = numeroSupressaoParcial;
	}

	public Integer getNumeroSupressaoTotal(){

		return numeroSupressaoTotal;
	}

	public void setNumeroSupressaoTotal(Integer numeroSupressaoTotal){

		this.numeroSupressaoTotal = numeroSupressaoTotal;
	}

	public Integer getNumeroSupressaoFaltaPagamento(){

		return numeroSupressaoFaltaPagamento;
	}

	public void setNumeroSupressaoFaltaPagamento(Integer numeroSupressaoFaltaPagamento){

		this.numeroSupressaoFaltaPagamento = numeroSupressaoFaltaPagamento;
	}

	public Integer getNumeroSupressaoInfracao(){

		return numeroSupressaoInfracao;
	}

	public void setNumeroSupressaoInfracao(Integer numeroSupressaoInfracao){

		this.numeroSupressaoInfracao = numeroSupressaoInfracao;
	}

	public Integer getNumeroSupressaoPedido(){

		return numeroSupressaoPedido;
	}

	public void setNumeroSupressaoPedido(Integer numeroSupressaoPedido){

		this.numeroSupressaoPedido = numeroSupressaoPedido;
	}

	/**
	 * @return Retorna o campo numeroLacre.
	 */
	public String getNumeroLacre(){

		return numeroLacre;
	}

	/**
	 * @param numeroLacre
	 *            O numeroLacre a ser setado.
	 */
	public void setNumeroLacre(String numeroLacre){

		this.numeroLacre = numeroLacre;
	}

	/** full constructor */
	public LigacaoAgua(Date dataImplantacao, Date dataLigacao, Date dataSupressao, Date dataCorte, Date dataReligacao,
						Integer numeroSeloCorte, Integer numeroSeloSupressao, Date ultimaAlteracao, Date dataRestabelecimentoAgua,
						Date dataCorteAdministrativo, Integer numeroConsumoMinimoAgua, Short laguIcemissaocortesupressao, Imovel imovel,
						gcom.atendimentopublico.ligacaoagua.SupressaoTipo supressaoTipo,
						gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo emissaoOrdemCobrancaTipo,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil ligacaoAguaPerfil,
						HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro ligacaoAguaDiametro,
						gcom.atendimentopublico.ligacaoagua.CorteTipo corteTipo,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial ligacaoAguaMaterial,
						gcom.atendimentopublico.ligacaoagua.MotivoCorte motivoCorte, SupressaoMotivo supressaoMotivo) {

		this.dataImplantacao = dataImplantacao;
		this.dataLigacao = dataLigacao;
		this.dataSupressao = dataSupressao;
		this.dataCorte = dataCorte;
		this.dataReligacao = dataReligacao;
		this.numeroSeloCorte = numeroSeloCorte;
		this.numeroSeloSupressao = numeroSeloSupressao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.dataRestabelecimentoAgua = dataRestabelecimentoAgua;
		this.dataCorteAdministrativo = dataCorteAdministrativo;
		this.numeroConsumoMinimoAgua = numeroConsumoMinimoAgua;
		this.laguIcemissaocortesupressao = laguIcemissaocortesupressao;
		this.imovel = imovel;
		this.supressaoTipo = supressaoTipo;
		this.emissaoOrdemCobrancaTipo = emissaoOrdemCobrancaTipo;
		this.ligacaoAguaPerfil = ligacaoAguaPerfil;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.ligacaoAguaDiametro = ligacaoAguaDiametro;
		this.corteTipo = corteTipo;
		this.ligacaoAguaMaterial = ligacaoAguaMaterial;
		this.motivoCorte = motivoCorte;
		this.supressaoMotivo = supressaoMotivo;
	}

	/** default constructor */
	public LigacaoAgua() {

	}

	/** minimal constructor */
	public LigacaoAgua(gcom.atendimentopublico.ligacaoagua.SupressaoTipo supressaoTipo,
						gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo emissaoOrdemCobrancaTipo,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil ligacaoAguaPerfil,
						HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro ligacaoAguaDiametro,
						gcom.atendimentopublico.ligacaoagua.CorteTipo corteTipo,
						gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial ligacaoAguaMaterial) {

		this.supressaoTipo = supressaoTipo;
		this.emissaoOrdemCobrancaTipo = emissaoOrdemCobrancaTipo;
		this.ligacaoAguaPerfil = ligacaoAguaPerfil;
		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
		this.ligacaoAguaDiametro = ligacaoAguaDiametro;
		this.corteTipo = corteTipo;
		this.ligacaoAguaMaterial = ligacaoAguaMaterial;
	}

	public Date getDataCorteAdministrativo(){

		return dataCorteAdministrativo;
	}

	public void setDataCorteAdministrativo(Date dataCorteAdministrativo){

		this.dataCorteAdministrativo = dataCorteAdministrativo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataImplantacao(){

		return this.dataImplantacao;
	}

	public void setDataImplantacao(Date dataImplantacao){

		this.dataImplantacao = dataImplantacao;
	}

	public Date getDataLigacao(){

		return this.dataLigacao;
	}

	public void setDataLigacao(Date dataLigacao){

		this.dataLigacao = dataLigacao;
	}

	public Date getDataSupressao(){

		return this.dataSupressao;
	}

	public void setDataSupressao(Date dataSupressao){

		this.dataSupressao = dataSupressao;
	}

	public Date getDataCorte(){

		return this.dataCorte;
	}

	public void setDataCorte(Date dataCorte){

		this.dataCorte = dataCorte;
	}

	public Date getDataReligacao(){

		return this.dataReligacao;
	}

	public void setDataReligacao(Date dataReligacao){

		this.dataReligacao = dataReligacao;
	}

	public Integer getNumeroSeloCorte(){

		return this.numeroSeloCorte;
	}

	public void setNumeroSeloCorte(Integer numeroSeloCorte){

		this.numeroSeloCorte = numeroSeloCorte;
	}

	public Integer getNumeroSeloSupressao(){

		return this.numeroSeloSupressao;
	}

	public void setNumeroSeloSupressao(Integer numeroSeloSupressao){

		this.numeroSeloSupressao = numeroSeloSupressao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getNumeroConsumoMinimoAgua(){

		return this.numeroConsumoMinimoAgua;
	}

	public void setNumeroConsumoMinimoAgua(Integer numeroConsumoMinimoAgua){

		this.numeroConsumoMinimoAgua = numeroConsumoMinimoAgua;
	}

	public Short getLaguIcemissaocortesupressao(){

		return this.laguIcemissaocortesupressao;
	}

	public void setLaguIcemissaocortesupressao(Short laguIcemissaocortesupressao){

		this.laguIcemissaocortesupressao = laguIcemissaocortesupressao;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public gcom.atendimentopublico.ligacaoagua.SupressaoTipo getSupressaoTipo(){

		return this.supressaoTipo;
	}

	public void setSupressaoTipo(gcom.atendimentopublico.ligacaoagua.SupressaoTipo supressaoTipo){

		this.supressaoTipo = supressaoTipo;
	}

	public gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo getEmissaoOrdemCobrancaTipo(){

		return this.emissaoOrdemCobrancaTipo;
	}

	public void setEmissaoOrdemCobrancaTipo(gcom.atendimentopublico.ligacaoagua.EmissaoOrdemCobrancaTipo emissaoOrdemCobrancaTipo){

		this.emissaoOrdemCobrancaTipo = emissaoOrdemCobrancaTipo;
	}

	public gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil getLigacaoAguaPerfil(){

		return this.ligacaoAguaPerfil;
	}

	public void setLigacaoAguaPerfil(gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil ligacaoAguaPerfil){

		this.ligacaoAguaPerfil = ligacaoAguaPerfil;
	}

	public HidrometroInstalacaoHistorico getHidrometroInstalacaoHistorico(){

		return this.hidrometroInstalacaoHistorico;
	}

	public void setHidrometroInstalacaoHistorico(HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico){

		this.hidrometroInstalacaoHistorico = hidrometroInstalacaoHistorico;
	}

	public gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro getLigacaoAguaDiametro(){

		return this.ligacaoAguaDiametro;
	}

	public void setLigacaoAguaDiametro(gcom.atendimentopublico.ligacaoagua.LigacaoAguaDiametro ligacaoAguaDiametro){

		this.ligacaoAguaDiametro = ligacaoAguaDiametro;
	}

	public gcom.atendimentopublico.ligacaoagua.CorteTipo getCorteTipo(){

		return this.corteTipo;
	}

	public void setCorteTipo(gcom.atendimentopublico.ligacaoagua.CorteTipo corteTipo){

		this.corteTipo = corteTipo;
	}

	public gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial getLigacaoAguaMaterial(){

		return this.ligacaoAguaMaterial;
	}

	public void setLigacaoAguaMaterial(gcom.atendimentopublico.ligacaoagua.LigacaoAguaMaterial ligacaoAguaMaterial){

		this.ligacaoAguaMaterial = ligacaoAguaMaterial;
	}

	public gcom.atendimentopublico.ligacaoagua.MotivoCorte getMotivoCorte(){

		return this.motivoCorte;
	}

	public void setMotivoCorte(gcom.atendimentopublico.ligacaoagua.MotivoCorte motivoCorte){

		this.motivoCorte = motivoCorte;
	}

	public SupressaoMotivo getSupressaoMotivo(){

		return this.supressaoMotivo;
	}

	public void setSupressaoMotivo(SupressaoMotivo supressaoMotivo){

		this.supressaoMotivo = supressaoMotivo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();

		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("supressaoTipo");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("emissaoOrdemCobrancaTipo");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaPerfil");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaDiametro");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("corteTipo");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaMaterial");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("motivoCorte");
		filtroLigacaoAgua.adicionarCaminhoParaCarregamentoEntidade("supressaoMotivo");

		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, this.getId()));
		return filtroLigacaoAgua;
	}

	/**
	 * @return Retorna o campo dataRestabelecimentoAgua.
	 */
	public Date getDataRestabelecimentoAgua(){

		return dataRestabelecimentoAgua;
	}

	/**
	 * @param dataRestabelecimentoAgua
	 *            O dataRestabelecimentoAgua a ser setado.
	 */
	public void setDataRestabelecimentoAgua(Date dataRestabelecimentoAgua){

		this.dataRestabelecimentoAgua = dataRestabelecimentoAgua;
	}

	public RamalLocalInstalacao getRamalLocalInstalacao(){

		return ramalLocalInstalacao;
	}

	public void setRamalLocalInstalacao(RamalLocalInstalacao ramalLocalInstalacao){

		this.ramalLocalInstalacao = ramalLocalInstalacao;
	}

	public Set getMedicaoHistoricos(){

		return medicaoHistoricos;
	}

	public void setMedicaoHistoricos(Set medicaoHistorico){

		this.medicaoHistoricos = medicaoHistorico;
	}

	@Override
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] labels = {"imovel.id", "id"};
		return labels;
	}

	@Override
	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"imovel", "ligacao"};
		return labels;
	}

	// ------- Fim Registra transação ---------------

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	/**
	 * @return the numeroCorte
	 */
	public Integer getNumeroCorte(){

		return numeroCorte;
	}

	/**
	 * @param numeroCorte
	 *            the numeroCorte to set
	 */
	public void setNumeroCorte(Integer numeroCorte){

		this.numeroCorte = numeroCorte;
	}

	/**
	 * @return the numeroCorteAdministrativo
	 */
	public Integer getNumeroCorteAdministrativo(){

		return numeroCorteAdministrativo;
	}

	/**
	 * @param numeroCorteAdministrativo
	 *            the numeroCorteAdministrativo to set
	 */
	public void setNumeroCorteAdministrativo(Integer numeroCorteAdministrativo){

		this.numeroCorteAdministrativo = numeroCorteAdministrativo;
	}

	/**
	 * @return the numeroSupressaoAgua
	 */
	public Integer getNumeroSupressaoAgua(){

		return numeroSupressaoAgua;
	}

	/**
	 * @param numeroSupressaoAgua
	 *            the numeroSupressaoAgua to set
	 */
	public void setNumeroSupressaoAgua(Integer numeroSupressaoAgua){

		this.numeroSupressaoAgua = numeroSupressaoAgua;
	}

	/**
	 * @return the funcionarioCorte
	 */
	public Funcionario getFuncionarioCorte(){

		return funcionarioCorte;
	}

	/**
	 * @param funcionarioCorte
	 *            the funcionarioCorte to set
	 */
	public void setFuncionarioCorte(Funcionario funcionarioCorte){

		this.funcionarioCorte = funcionarioCorte;
	}

	/**
	 * @return the funcionarioLigacaoAgua
	 */
	public Funcionario getFuncionarioLigacaoAgua(){

		return funcionarioLigacaoAgua;
	}

	/**
	 * @param funcionarioLigacaoAgua
	 *            the funcionarioLigacaoAgua to set
	 */
	public void setFuncionarioLigacaoAgua(Funcionario funcionarioLigacaoAgua){

		this.funcionarioLigacaoAgua = funcionarioLigacaoAgua;
	}

	/**
	 * @return the funcionarioSupressaoAgua
	 */
	public Funcionario getFuncionarioSupressaoAgua(){

		return funcionarioSupressaoAgua;
	}

	/**
	 * @param funcionarioSupressaoAgua
	 *            the funcionarioSupressaoAgua to set
	 */
	public void setFuncionarioSupressaoAgua(Funcionario funcionarioSupressaoAgua){

		this.funcionarioSupressaoAgua = funcionarioSupressaoAgua;
	}

	/**
	 * @return the funcionarioReligacaoAgua
	 */
	public Funcionario getFuncionarioReligacaoAgua(){

		return funcionarioReligacaoAgua;
	}

	/**
	 * @param funcionarioReligacaoAgua
	 *            the funcionarioReligacaoAgua to set
	 */
	public void setFuncionarioReligacaoAgua(Funcionario funcionarioReligacaoAgua){

		this.funcionarioReligacaoAgua = funcionarioReligacaoAgua;
	}

	/**
	 * @return the funcionarioRestabelecimento
	 */
	public Funcionario getFuncionarioRestabelecimento(){

		return funcionarioRestabelecimento;
	}

	/**
	 * @param funcionarioRestabelecimento
	 *            the funcionarioRestabelecimento to set
	 */
	public void setFuncionarioRestabelecimento(Funcionario funcionarioRestabelecimento){

		this.funcionarioRestabelecimento = funcionarioRestabelecimento;
	}

	public void setNumeroReligacao(Integer numeroReligacao){

		this.numeroReligacao = numeroReligacao;
	}

	public Integer getNumeroReligacao(){

		return numeroReligacao;
	}

	public CorteRegistroTipo getCorteRegistroTipo(){

		return corteRegistroTipo;
	}

	public void setCorteRegistroTipo(CorteRegistroTipo corteRegistroTipo){

		this.corteRegistroTipo = corteRegistroTipo;
	}

}
