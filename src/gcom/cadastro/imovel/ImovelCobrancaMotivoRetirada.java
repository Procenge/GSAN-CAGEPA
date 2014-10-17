
package gcom.cadastro.imovel;

import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hugo Lima
 */
public class ImovelCobrancaMotivoRetirada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private String descricaoAbreviada;

	private Short indicadorSistema;

	private String codigoConstante;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private CobrancaDebitoSituacao cobrancaDebitoSituacao;

	public static Integer NEGOCIADO;

	public static Integer COMANDADO;

	public static Integer CANCELADO;

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Anderson Italo
	 * @date 24/07/2012
	 */
	public static void inicializarConstantes(){

		NEGOCIADO = ImovelCobrancaMotivoRetiradaEnum.NEGOCIADO.getId();

		COMANDADO = ImovelCobrancaMotivoRetiradaEnum.COMANDADO.getId();

		COMANDADO = ImovelCobrancaMotivoRetiradaEnum.CANCELADO.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Anderson Italo
	 * @date 24/07/2012
	 */
	public static enum ImovelCobrancaMotivoRetiradaEnum {

		NEGOCIADO, COMANDADO, CANCELADO;

		private Integer id = -1;

		private ImovelCobrancaMotivoRetiradaEnum() {

			try{
				ImovelCobrancaMotivoRetirada imovelCobrancaMotivoRetirada = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								ImovelCobrancaMotivoRetirada.class);

				if(imovelCobrancaMotivoRetirada != null){

					id = imovelCobrancaMotivoRetirada.getId();
				}
			}catch(ErroRepositorioException e){

				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

	public ImovelCobrancaMotivoRetirada() {

	}

	public ImovelCobrancaMotivoRetirada(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Short getIndicadorSistema(){

		return indicadorSistema;
	}

	public void setIndicadorSistema(Short indicadorSistema){

		this.indicadorSistema = indicadorSistema;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public CobrancaDebitoSituacao getCobrancaDebitoSituacao(){

		return cobrancaDebitoSituacao;
	}

	public void setCobrancaDebitoSituacao(CobrancaDebitoSituacao cobrancaDebitoSituacao){

		this.cobrancaDebitoSituacao = cobrancaDebitoSituacao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
