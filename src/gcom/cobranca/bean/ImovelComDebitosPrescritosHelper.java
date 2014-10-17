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

package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * [UC3141] Filtrar Imóveis com Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 02/04/2014
 */
public class ImovelComDebitosPrescritosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;


	private Integer idImovel;

	private Integer idLocalidade;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;
	
	private Short lote;
	
	private Short sublote;

	private Integer idLigacaoAguaSituacao;
	
	private Integer idLigacaoEsgotoSituacao;
	
	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String nomeLocalidade;

	private String descricaoImovelPerfil;
	
	private BigDecimal valorContasMarcadas;
	
	private Integer quantidadeContasMarcadas;
	
	private BigDecimal valorContasDesmarcadas;
	
	private Integer quantidadeContasDesmarcadas;
	
	private BigDecimal valorGuiasPrestacaoMarcadas;
	
	private Integer quantidadeGuiasPrestacaoMarcadas;
	
	private BigDecimal valorGuiasPrestacaoDesmarcadas;
	
	private Integer quantidadeGuiasPrestacaoDesmarcadas;

	public ImovelComDebitosPrescritosHelper() {

	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getLote(){

		return lote;
	}

	public void setLote(Short lote){

		this.lote = lote;
	}

	public Short getSublote(){

		return sublote;
	}

	public void setSublote(Short sublote){

		this.sublote = sublote;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public BigDecimal getValorContasMarcadas(){

		return valorContasMarcadas;
	}

	public void setValorContasMarcadas(BigDecimal valorContasMarcadas){

		this.valorContasMarcadas = valorContasMarcadas;
	}

	public Integer getQuantidadeContasMarcadas(){

		return quantidadeContasMarcadas;
	}

	public void setQuantidadeContasMarcadas(Integer quantidadeContasMarcadas){

		this.quantidadeContasMarcadas = quantidadeContasMarcadas;
	}

	public BigDecimal getValorContasDesmarcadas(){

		return valorContasDesmarcadas;
	}

	public void setValorContasDesmarcadas(BigDecimal valorContasDesmarcadas){

		this.valorContasDesmarcadas = valorContasDesmarcadas;
	}

	public Integer getQuantidadeContasDesmarcadas(){

		return quantidadeContasDesmarcadas;
	}

	public void setQuantidadeContasDesmarcadas(Integer quantidadeContasDesmarcadas){

		this.quantidadeContasDesmarcadas = quantidadeContasDesmarcadas;
	}
	
	public BigDecimal getValorGuiasPrestacaoMarcadas(){

		return valorGuiasPrestacaoMarcadas;
	}

	public void setValorGuiasPrestacaoMarcadas(BigDecimal valorGuiasPrestacaoMarcadas){

		this.valorGuiasPrestacaoMarcadas = valorGuiasPrestacaoMarcadas;
	}

	public Integer getQuantidadeGuiasPrestacaoMarcadas(){

		return quantidadeGuiasPrestacaoMarcadas;
	}

	public void setQuantidadeGuiasPrestacaoMarcadas(Integer quantidadeGuiasPrestacaoMarcadas){

		this.quantidadeGuiasPrestacaoMarcadas = quantidadeGuiasPrestacaoMarcadas;
	}

	public BigDecimal getValorGuiasPrestacaoDesmarcadas(){

		return valorGuiasPrestacaoDesmarcadas;
	}

	public void setValorGuiasPrestacaoDesmarcadas(BigDecimal valorGuiasPrestacaoDesmarcadas){

		this.valorGuiasPrestacaoDesmarcadas = valorGuiasPrestacaoDesmarcadas;
	}

	public Integer getQuantidadeGuiasPrestacaoDesmarcadas(){

		return quantidadeGuiasPrestacaoDesmarcadas;
	}

	public void setQuantidadeGuiasPrestacaoDesmarcadas(Integer quantidadeGuiasPrestacaoDesmarcadas){

		this.quantidadeGuiasPrestacaoDesmarcadas = quantidadeGuiasPrestacaoDesmarcadas;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}
	
	
}