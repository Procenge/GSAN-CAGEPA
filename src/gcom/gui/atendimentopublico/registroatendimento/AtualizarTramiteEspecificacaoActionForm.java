
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Atualizar Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class AtualizarTramiteEspecificacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String idSolicitacaoTipo;

	private String descricaoSolicitacaoTipo;

	private String idSolicitacaoTipoEspecificacao;

	private String descricaoSolicitacaoTipoEspecificacao;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String codigoSetorComercial;

	private String descricaoSetorComercial;

	private String idMunicipio;

	private String nomeMunicipio;

	private String codigoBairro;

	private String nomeBairro;



	private String idUnidadeOrganizacionalOrigem;

	private String descricaoUnidadeOrganizacionalOrigem;

	private String idUnidadeOrganizacionalDestino;

	private String descricaoUnidadeOrganizacionalDestino;

	private String indicadorUso;

	private String indicadorPrimeiroTramite;

	/**
	 * @return the id
	 */
	public String getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id){

		this.id = id;
	}

	/**
	 * @return the idSolicitacaoTipo
	 */
	public String getIdSolicitacaoTipo(){

		return idSolicitacaoTipo;
	}

	/**
	 * @param idSolicitacaoTipo
	 *            the idSolicitacaoTipo to set
	 */
	public void setIdSolicitacaoTipo(String idSolicitacaoTipo){

		this.idSolicitacaoTipo = idSolicitacaoTipo;
	}

	/**
	 * @return the idSolicitacaoTipoEspecificacao
	 */
	public String getIdSolicitacaoTipoEspecificacao(){

		return idSolicitacaoTipoEspecificacao;
	}

	/**
	 * @param idSolicitacaoTipoEspecificacao
	 *            the idSolicitacaoTipoEspecificacao to set
	 */
	public void setIdSolicitacaoTipoEspecificacao(String idSolicitacaoTipoEspecificacao){

		this.idSolicitacaoTipoEspecificacao = idSolicitacaoTipoEspecificacao;
	}

	/**
	 * @return the idLocalidade
	 */
	public String getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return the descricaoLocalidade
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade
	 *            the descricaoLocalidade to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return the codigoSetorComercial
	 */
	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	/**
	 * @param codigoSetorComercial
	 *            the codigoSetorComercial to set
	 */
	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	/**
	 * @return the descricaoSetorComercial
	 */
	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	/**
	 * @param descricaoSetorComercial
	 *            the descricaoSetorComercial to set
	 */
	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	/**
	 * @return the idMunicipio
	 */
	public String getIdMunicipio(){

		return idMunicipio;
	}

	/**
	 * @param idMunicipio
	 *            the idMunicipio to set
	 */
	public void setIdMunicipio(String idMunicipio){

		this.idMunicipio = idMunicipio;
	}

	/**
	 * @return the nomeMunicipio
	 */
	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	/**
	 * @param nomeMunicipio
	 *            the nomeMunicipio to set
	 */
	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	/**
	 * @return the codigoBairro
	 */
	public String getCodigoBairro(){

		return codigoBairro;
	}

	/**
	 * @param codigoBairro
	 *            the codigoBairro to set
	 */
	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	/**
	 * @return the nomeBairro
	 */
	public String getNomeBairro(){

		return nomeBairro;
	}

	/**
	 * @param nomeBairro
	 *            the nomeBairro to set
	 */
	public void setNomeBairro(String nomeBairro){

		this.nomeBairro = nomeBairro;
	}



	/**
	 * @return the idUnidadeOrganizacionalOrigem
	 */
	public String getIdUnidadeOrganizacionalOrigem(){

		return idUnidadeOrganizacionalOrigem;
	}

	/**
	 * @param idUnidadeOrganizacionalOrigem
	 *            the idUnidadeOrganizacionalOrigem to set
	 */
	public void setIdUnidadeOrganizacionalOrigem(String idUnidadeOrganizacionalOrigem){

		this.idUnidadeOrganizacionalOrigem = idUnidadeOrganizacionalOrigem;
	}

	/**
	 * @return the descricaoUnidadeOrganizacionalOrigem
	 */
	public String getDescricaoUnidadeOrganizacionalOrigem(){

		return descricaoUnidadeOrganizacionalOrigem;
	}

	/**
	 * @param descricaoUnidadeOrganizacionalOrigem
	 *            the descricaoUnidadeOrganizacionalOrigem to set
	 */
	public void setDescricaoUnidadeOrganizacionalOrigem(String descricaoUnidadeOrganizacionalOrigem){

		this.descricaoUnidadeOrganizacionalOrigem = descricaoUnidadeOrganizacionalOrigem;
	}

	/**
	 * @return the idUnidadeOrganizacionalDestino
	 */
	public String getIdUnidadeOrganizacionalDestino(){

		return idUnidadeOrganizacionalDestino;
	}

	/**
	 * @param idUnidadeOrganizacionalDestino
	 *            the idUnidadeOrganizacionalDestino to set
	 */
	public void setIdUnidadeOrganizacionalDestino(String idUnidadeOrganizacionalDestino){

		this.idUnidadeOrganizacionalDestino = idUnidadeOrganizacionalDestino;
	}

	/**
	 * @return the descricaoUnidadeOrganizacionalDestino
	 */
	public String getDescricaoUnidadeOrganizacionalDestino(){

		return descricaoUnidadeOrganizacionalDestino;
	}

	/**
	 * @param descricaoUnidadeOrganizacionalDestino
	 *            the descricaoUnidadeOrganizacionalDestino to set
	 */
	public void setDescricaoUnidadeOrganizacionalDestino(String descricaoUnidadeOrganizacionalDestino){

		this.descricaoUnidadeOrganizacionalDestino = descricaoUnidadeOrganizacionalDestino;
	}

	/**
	 * @return the indicadorUso
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the descricaoSolicitacaoTipo
	 */
	public String getDescricaoSolicitacaoTipo(){

		return descricaoSolicitacaoTipo;
	}

	/**
	 * @param descricaoSolicitacaoTipo
	 *            the descricaoSolicitacaoTipo to set
	 */
	public void setDescricaoSolicitacaoTipo(String descricaoSolicitacaoTipo){

		this.descricaoSolicitacaoTipo = descricaoSolicitacaoTipo;
	}

	/**
	 * @return the descricaoSolicitacaoTipoEspecificacao
	 */
	public String getDescricaoSolicitacaoTipoEspecificacao(){

		return descricaoSolicitacaoTipoEspecificacao;
	}

	/**
	 * @param descricaoSolicitacaoTipoEspecificacao
	 *            the descricaoSolicitacaoTipoEspecificacao to set
	 */
	public void setDescricaoSolicitacaoTipoEspecificacao(String descricaoSolicitacaoTipoEspecificacao){

		this.descricaoSolicitacaoTipoEspecificacao = descricaoSolicitacaoTipoEspecificacao;
	}

	/**
	 * Popular form com os valores da entidade
	 */
	public void setFormValues(EspecificacaoTramite especificacaoTramite){

		String numeroNaoInformadoStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);

		// Id
		String idStr = "";

		Integer id = especificacaoTramite.getId();

		if(id != null){
			idStr = Integer.toString(id);
		}

		this.setId(idStr);

		// Solicitação Tipo & Solicitação Tipo Especificação
		String idSolicitacaoTipoStr = "";
		String descricaoSolicitacaoTipoStr = "";

		String idSolicitacaoTipoEspecificacaoStr = "";
		String descricaoSolicitacaoTipoEspecificacaoStr = "";

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = especificacaoTramite.getSolicitacaoTipoEspecificacao();

		if(solicitacaoTipoEspecificacao != null){
			Integer idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();
			idSolicitacaoTipoEspecificacaoStr = Integer.toString(idSolicitacaoTipoEspecificacao);

			descricaoSolicitacaoTipoEspecificacaoStr = solicitacaoTipoEspecificacao.getDescricao();

			SolicitacaoTipo solicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo();

			if(solicitacaoTipo != null){
				Integer idSolicitacaoTipo = solicitacaoTipo.getId();
				idSolicitacaoTipoStr = Integer.toString(idSolicitacaoTipo);

				descricaoSolicitacaoTipoStr = solicitacaoTipo.getDescricao();
			}
		}

		this.setIdSolicitacaoTipo(idSolicitacaoTipoStr);
		this.setDescricaoSolicitacaoTipo(descricaoSolicitacaoTipoStr);
		this.setIdSolicitacaoTipoEspecificacao(idSolicitacaoTipoEspecificacaoStr);
		this.setDescricaoSolicitacaoTipoEspecificacao(descricaoSolicitacaoTipoEspecificacaoStr);

		// Localidade
		String idLocalidadeStr = "";
		String descricaoLocalidade = "";

		Localidade localidade = especificacaoTramite.getLocalidade();

		if(localidade != null){
			Integer idLocalidade = localidade.getId();
			idLocalidadeStr = Integer.toString(idLocalidade);

			descricaoLocalidade = localidade.getDescricao();
		}

		this.setIdLocalidade(idLocalidadeStr);
		this.setDescricaoLocalidade(descricaoLocalidade);

		// Setor Comercial
		String codigoSetorComercialStr = "";
		String descricaoSetorComercial = "";

		SetorComercial setorComercial = especificacaoTramite.getSetorComercial();

		if(setorComercial != null){
			int codigoSetorComercial = setorComercial.getCodigo();
			codigoSetorComercialStr = Integer.toString(codigoSetorComercial);

			descricaoSetorComercial = setorComercial.getDescricao();
		}

		this.setCodigoSetorComercial(codigoSetorComercialStr);
		this.setDescricaoSetorComercial(descricaoSetorComercial);

		// Municipio & Bairro
		String idMunicipioStr = "";
		String nomeMunicipio = "";

		String codigoBairroStr = "";
		String nomeBairro = "";

		Bairro bairro = especificacaoTramite.getBairro();

		if(bairro != null){
			int codigoBairro = bairro.getCodigo();
			codigoBairroStr = Integer.toString(codigoBairro);

			nomeBairro = bairro.getNome();

			Municipio municipio = bairro.getMunicipio();

			if(municipio != null){
				Integer idMunicipio = municipio.getId();
				idMunicipioStr = Integer.toString(idMunicipio);

				nomeMunicipio = municipio.getNome();
			}
		}

		this.setIdMunicipio(idMunicipioStr);
		this.setNomeMunicipio(nomeMunicipio);
		this.setCodigoBairro(codigoBairroStr);
		this.setNomeBairro(nomeBairro);


		// Unidade Organizacional de Origem
		String idUnidadeOrganizacionalOrigemStr = "";
		String descricaoUnidadeOrganizacionalOrigem = "";

		UnidadeOrganizacional unidadeOrganizacionalOrigem = especificacaoTramite.getUnidadeOrganizacionalOrigem();

		if(unidadeOrganizacionalOrigem != null){
			Integer idUnidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem.getId();
			idUnidadeOrganizacionalOrigemStr = Integer.toString(idUnidadeOrganizacionalOrigem);

			descricaoUnidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem.getDescricao();
		}

		this.setIdUnidadeOrganizacionalOrigem(idUnidadeOrganizacionalOrigemStr);
		this.setDescricaoUnidadeOrganizacionalOrigem(descricaoUnidadeOrganizacionalOrigem);

		// Unidade Organizacional de Destino
		String idUnidadeOrganizacionalDestinoStr = "";
		String descricaoUnidadeOrganizacionalDestino = "";

		UnidadeOrganizacional unidadeOrganizacionalDestino = especificacaoTramite.getUnidadeOrganizacionalDestino();

		if(unidadeOrganizacionalDestino != null){
			Integer idUnidadeOrganizacionalDestino = unidadeOrganizacionalDestino.getId();
			idUnidadeOrganizacionalDestinoStr = Integer.toString(idUnidadeOrganizacionalDestino);

			descricaoUnidadeOrganizacionalDestino = unidadeOrganizacionalDestino.getDescricao();
		}

		this.setIdUnidadeOrganizacionalDestino(idUnidadeOrganizacionalDestinoStr);
		this.setDescricaoUnidadeOrganizacionalDestino(descricaoUnidadeOrganizacionalDestino);

		// Indicador de uso
		String indicadorUsoStr = numeroNaoInformadoStr;

		Short indicadorUso = especificacaoTramite.getIndicadorUso();

		if(indicadorUso != null){
			indicadorUsoStr = Short.toString(indicadorUso);
		}

		this.setIndicadorUso(indicadorUsoStr);

		// Indicador de primeiro tramite
		String indicadorPrimeiroTramiteStr = ConstantesSistema.NAO.toString();

		Short indicadorPrimeiroTramite = especificacaoTramite.getIndicadorUso();

		if(indicadorPrimeiroTramite != null){
			indicadorPrimeiroTramiteStr = Short.toString(indicadorPrimeiroTramite);
		}

		this.setIndicadorPrimeiroTramite(indicadorPrimeiroTramiteStr);

	}

	/**
	 * Popular entidade com os valores do form
	 */
	public void setEspecificacaoTramiteValues(EspecificacaoTramite especificacaoTramite){

		Fachada fachada = Fachada.getInstancia();

		String numeroNaoInformadoStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);

		// Localidade
		Localidade localidade = null;

		String idLocalidadeStr = this.getIdLocalidade();

		if(idLocalidadeStr != null && !idLocalidadeStr.equals("")){
			Integer id = Integer.parseInt(idLocalidadeStr);

			localidade = new Localidade();
			localidade.setId(id);
		}

		especificacaoTramite.setLocalidade(localidade);

		// Setor Comercial
		SetorComercial setorComercial = null;

		String codigoSetorComercialStr = this.getCodigoSetorComercial();

		if(codigoSetorComercialStr != null && !codigoSetorComercialStr.equals("") && idLocalidadeStr != null && !idLocalidadeStr.equals("")){
			FiltroSetorComercial filtro = new FiltroSetorComercial();
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidadeStr));
			filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercialStr));

			Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtro, SetorComercial.class.getName());
			setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
		}

		especificacaoTramite.setSetorComercial(setorComercial);

		// Bairro
		Bairro bairro = null;

		String codigoBairroStr = this.getCodigoBairro();
		String idMunicipioStr = this.getIdMunicipio();

		if(codigoBairroStr != null && !codigoBairroStr.equals("") && idMunicipioStr != null && !idMunicipioStr.equals("")){
			FiltroBairro filtro = new FiltroBairro();
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipioStr));
			filtro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, codigoBairroStr));

			Collection<Bairro> colecaoBairro = fachada.pesquisar(filtro, Bairro.class.getName());
			bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);
		}

		especificacaoTramite.setBairro(bairro);



		// Unidade Organizacional de Origem
		UnidadeOrganizacional unidadeOrganizacionalOrigem = null;

		String idUnidadeOrganizacionalOrigemStr = this.getIdUnidadeOrganizacionalOrigem();

		if(idUnidadeOrganizacionalOrigemStr != null && !idUnidadeOrganizacionalOrigemStr.equals("")){
			Integer id = Integer.parseInt(idUnidadeOrganizacionalOrigemStr);

			unidadeOrganizacionalOrigem = new UnidadeOrganizacional();
			unidadeOrganizacionalOrigem.setId(id);
		}

		especificacaoTramite.setUnidadeOrganizacionalOrigem(unidadeOrganizacionalOrigem);

		// Unidade Organizacional de Destino
		UnidadeOrganizacional unidadeOrganizacionalDestino = null;

		String idUnidadeOrganizacionalDestinoStr = this.getIdUnidadeOrganizacionalDestino();

		if(idUnidadeOrganizacionalDestinoStr != null && !idUnidadeOrganizacionalDestinoStr.equals("")){
			Integer id = Integer.parseInt(idUnidadeOrganizacionalDestinoStr);

			unidadeOrganizacionalDestino = new UnidadeOrganizacional();
			unidadeOrganizacionalDestino.setId(id);
		}

		especificacaoTramite.setUnidadeOrganizacionalDestino(unidadeOrganizacionalDestino);

		// Indicador de uso
		Short indicadorUso = null;

		String indicadorUsoStr = this.getIndicadorUso();

		if(indicadorUsoStr != null && !indicadorUsoStr.equals(numeroNaoInformadoStr)){
			indicadorUso = Short.parseShort(indicadorUsoStr);
		}

		especificacaoTramite.setIndicadorUso(indicadorUso);
		
		
		// Indicador de primeiro tramite
		Short indicadorPrimeiroTramite = null;

		String indicadorPrimeiroTramiteStr = this.getIndicadorPrimeiroTramite();

		if(indicadorPrimeiroTramiteStr != null && !indicadorPrimeiroTramiteStr.equals(numeroNaoInformadoStr)){
			indicadorPrimeiroTramite = Short.parseShort(indicadorPrimeiroTramiteStr);
		}

		especificacaoTramite.setIndicadorPrimeiroTramite(indicadorPrimeiroTramite);

	}

	public String getIndicadorPrimeiroTramite(){

		return indicadorPrimeiroTramite;
	}

	public void setIndicadorPrimeiroTramite(String indicadorPrimeiroTramite){

		this.indicadorPrimeiroTramite = indicadorPrimeiroTramite;
	}

}
