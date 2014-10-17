
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
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubBacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.operacional.ZonaAbastecimento;
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

	private String idSistemaAbastecimento;

	private String idDistritoOperacional;

	private String idZonaAbastecimento;

	private String idSetorAbastecimento;

	private String idSistemaEsgoto;

	private String idSubsistemaEsgoto;

	private String idBacia;

	private String idSubBacia;

	private String idUnidadeOrganizacionalOrigem;

	private String descricaoUnidadeOrganizacionalOrigem;

	private String idUnidadeOrganizacionalDestino;

	private String descricaoUnidadeOrganizacionalDestino;

	private String indicadorUso;

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
	 * @return the idSistemaAbastecimento
	 */
	public String getIdSistemaAbastecimento(){

		return idSistemaAbastecimento;
	}

	/**
	 * @param idSistemaAbastecimento
	 *            the idSistemaAbastecimento to set
	 */
	public void setIdSistemaAbastecimento(String idSistemaAbastecimento){

		this.idSistemaAbastecimento = idSistemaAbastecimento;
	}

	/**
	 * @return the idDistritoOperacional
	 */
	public String getIdDistritoOperacional(){

		return idDistritoOperacional;
	}

	/**
	 * @param idDistritoOperacional
	 *            the idDistritoOperacional to set
	 */
	public void setIdDistritoOperacional(String idDistritoOperacional){

		this.idDistritoOperacional = idDistritoOperacional;
	}

	/**
	 * @return the idZonaAbastecimento
	 */
	public String getIdZonaAbastecimento(){

		return idZonaAbastecimento;
	}

	/**
	 * @param idZonaAbastecimento
	 *            the idZonaAbastecimento to set
	 */
	public void setIdZonaAbastecimento(String idZonaAbastecimento){

		this.idZonaAbastecimento = idZonaAbastecimento;
	}

	/**
	 * @return the idSetorAbastecimento
	 */
	public String getIdSetorAbastecimento(){

		return idSetorAbastecimento;
	}

	/**
	 * @param idSetorAbastecimento
	 *            the idSetorAbastecimento to set
	 */
	public void setIdSetorAbastecimento(String idSetorAbastecimento){

		this.idSetorAbastecimento = idSetorAbastecimento;
	}

	/**
	 * @return the idSistemaEsgoto
	 */
	public String getIdSistemaEsgoto(){

		return idSistemaEsgoto;
	}

	/**
	 * @param idSistemaEsgoto
	 *            the idSistemaEsgoto to set
	 */
	public void setIdSistemaEsgoto(String idSistemaEsgoto){

		this.idSistemaEsgoto = idSistemaEsgoto;
	}

	/**
	 * @return the idSubsistemaEsgoto
	 */
	public String getIdSubsistemaEsgoto(){

		return idSubsistemaEsgoto;
	}

	/**
	 * @param idSubsistemaEsgoto
	 *            the idSubsistemaEsgoto to set
	 */
	public void setIdSubsistemaEsgoto(String idSubsistemaEsgoto){

		this.idSubsistemaEsgoto = idSubsistemaEsgoto;
	}

	/**
	 * @return the idBacia
	 */
	public String getIdBacia(){

		return idBacia;
	}

	/**
	 * @param idBacia
	 *            the idBacia to set
	 */
	public void setIdBacia(String idBacia){

		this.idBacia = idBacia;
	}

	/**
	 * @return the idSubBacia
	 */
	public String getIdSubBacia(){

		return idSubBacia;
	}

	/**
	 * @param idSubBacia
	 *            the idSubBacia to set
	 */
	public void setIdSubBacia(String idSubBacia){

		this.idSubBacia = idSubBacia;
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

		// Sistema de Abastecimento
		String idSistemaAbastecimentoStr = numeroNaoInformadoStr;

		SistemaAbastecimento sistemaAbastecimento = especificacaoTramite.getSistemaAbastecimento();

		if(sistemaAbastecimento != null){
			Integer idSistemaAbastecimento = sistemaAbastecimento.getId();
			idSistemaAbastecimentoStr = Integer.toString(idSistemaAbastecimento);
		}

		this.setIdSistemaAbastecimento(idSistemaAbastecimentoStr);

		// Distrito Operacional
		String idDistritoOperacionalStr = numeroNaoInformadoStr;

		DistritoOperacional distritoOperacional = especificacaoTramite.getDistritoOperacional();

		if(distritoOperacional != null){
			Integer idDistritoOperacional = distritoOperacional.getId();
			idDistritoOperacionalStr = Integer.toString(idDistritoOperacional);
		}

		this.setIdDistritoOperacional(idDistritoOperacionalStr);

		// Zona de Abastecimento
		String idZonaAbastecimentoStr = numeroNaoInformadoStr;

		ZonaAbastecimento zonaAbastecimento = especificacaoTramite.getZonaAbastecimento();

		if(zonaAbastecimento != null){
			Integer idZonaAbastecimento = zonaAbastecimento.getId();
			idZonaAbastecimentoStr = Integer.toString(idZonaAbastecimento);
		}

		this.setIdZonaAbastecimento(idZonaAbastecimentoStr);

		// Setor de Abastecimento
		String idSetorAbastecimentoStr = numeroNaoInformadoStr;

		SetorAbastecimento setorAbastecimento = especificacaoTramite.getSetorAbastecimento();

		if(setorAbastecimento != null){
			Integer idSetorAbastecimento = setorAbastecimento.getId();
			idSetorAbastecimentoStr = Integer.toString(idSetorAbastecimento);
		}

		this.setIdSetorAbastecimento(idSetorAbastecimentoStr);

		// Sistema de Esgoto
		String idSistemaEsgotoStr = numeroNaoInformadoStr;

		SistemaEsgoto sistemaEsgoto = especificacaoTramite.getSistemaEsgoto();

		if(sistemaEsgoto != null){
			Integer idSistemaEsgoto = sistemaEsgoto.getId();
			idSistemaEsgotoStr = Integer.toString(idSistemaEsgoto);
		}

		this.setIdSistemaEsgoto(idSistemaEsgotoStr);

		// Subsistema de Esgoto
		String idSubsistemaEsgotoStr = numeroNaoInformadoStr;

		SubsistemaEsgoto subsistemaEsgoto = especificacaoTramite.getSubsistemaEsgoto();

		if(subsistemaEsgoto != null){
			Integer idSubsistemaEsgoto = subsistemaEsgoto.getId();
			idSubsistemaEsgotoStr = Integer.toString(idSubsistemaEsgoto);
		}

		this.setIdSubsistemaEsgoto(idSubsistemaEsgotoStr);

		// Bacia
		String idBaciaStr = numeroNaoInformadoStr;

		Bacia bacia = especificacaoTramite.getBacia();

		if(bacia != null){
			Integer idBacia = bacia.getId();
			idBaciaStr = Integer.toString(idBacia);
		}

		this.setIdBacia(idBaciaStr);

		// SubBacia
		String idSubBaciaStr = numeroNaoInformadoStr;

		SubBacia subBacia = especificacaoTramite.getSubBacia();

		if(subBacia != null){
			Integer idSubBacia = subBacia.getId();
			idSubBaciaStr = Integer.toString(idSubBacia);
		}

		this.setIdSubBacia(idSubBaciaStr);

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

		// Sistema de Abastecimento
		SistemaAbastecimento sistemaAbastecimento = null;

		String idSistemaAbastecimentoStr = this.getIdSistemaAbastecimento();

		if(idSistemaAbastecimentoStr != null && !idSistemaAbastecimentoStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idSistemaAbastecimentoStr);

			sistemaAbastecimento = new SistemaAbastecimento();
			sistemaAbastecimento.setId(id);
		}

		especificacaoTramite.setSistemaAbastecimento(sistemaAbastecimento);

		// Distrito Operacional
		DistritoOperacional distritoOperacional = null;

		String idDistritoOperacionalStr = this.getIdDistritoOperacional();

		if(idDistritoOperacionalStr != null && !idDistritoOperacionalStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idDistritoOperacionalStr);

			distritoOperacional = new DistritoOperacional();
			distritoOperacional.setId(id);
		}

		especificacaoTramite.setDistritoOperacional(distritoOperacional);

		// Zona de Abastecimento
		ZonaAbastecimento zonaAbastecimento = null;

		String idZonaAbastecimentoStr = this.getIdZonaAbastecimento();

		if(idZonaAbastecimentoStr != null && !idZonaAbastecimentoStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idZonaAbastecimentoStr);

			zonaAbastecimento = new ZonaAbastecimento();
			zonaAbastecimento.setId(id);
		}

		especificacaoTramite.setZonaAbastecimento(zonaAbastecimento);

		// Setor de Abastecimento
		SetorAbastecimento setorAbastecimento = null;

		String idSetorAbastecimentoStr = this.getIdSetorAbastecimento();

		if(idSetorAbastecimentoStr != null && !idSetorAbastecimentoStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idSetorAbastecimentoStr);

			setorAbastecimento = new SetorAbastecimento();
			setorAbastecimento.setId(id);
		}

		especificacaoTramite.setSetorAbastecimento(setorAbastecimento);

		// Sistema de Esgoto
		SistemaEsgoto sistemaEsgoto = null;

		String idSistemaEsgotoStr = this.getIdSistemaEsgoto();

		if(idSistemaEsgotoStr != null && !idSistemaEsgotoStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idSistemaEsgotoStr);

			sistemaEsgoto = new SistemaEsgoto();
			sistemaEsgoto.setId(id);
		}

		especificacaoTramite.setSistemaEsgoto(sistemaEsgoto);

		// Subsistema de Esgoto
		SubsistemaEsgoto subsistemaEsgoto = null;

		String idSubsubsistemaEsgotoStr = this.getIdSubsistemaEsgoto();

		if(idSubsubsistemaEsgotoStr != null && !idSubsubsistemaEsgotoStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idSubsubsistemaEsgotoStr);

			subsistemaEsgoto = new SubsistemaEsgoto();
			subsistemaEsgoto.setId(id);
		}

		especificacaoTramite.setSubsistemaEsgoto(subsistemaEsgoto);

		// Bacia
		Bacia bacia = null;

		String idBaciaStr = this.getIdBacia();

		if(idBaciaStr != null && !idBaciaStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idBaciaStr);

			bacia = new Bacia();
			bacia.setId(id);
		}

		especificacaoTramite.setBacia(bacia);

		// SubBacia
		SubBacia subBacia = null;

		String idSubBaciaStr = this.getIdSubBacia();

		if(idSubBaciaStr != null && !idSubBaciaStr.equals(numeroNaoInformadoStr)){
			Integer id = Integer.parseInt(idSubBaciaStr);

			subBacia = new SubBacia();
			subBacia.setId(id);
		}

		especificacaoTramite.setSubBacia(subBacia);

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
	}

}
