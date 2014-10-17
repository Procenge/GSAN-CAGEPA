
package gcom.tagslib;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.Constants;

/**
 * Classe base para construção de tags customizadas do Gsan.
 * 
 * @author João Santana
 */
public abstract class GcomBaseTag
				extends BaseHandlerTag {

	private static final long serialVersionUID = 1L;

	protected String name = Constants.BEAN_KEY;

	protected String property = null;

	protected String value = null;

	protected String maxlength = null;

	protected boolean redisplay = true;

	public String getContextRoot(){

		return ((HttpServletRequest) pageContext.getRequest()).getContextPath();
	}

	public void release(){

		super.release();
		name = Constants.BEAN_KEY;
		property = null;
		value = null;
		maxlength = null;
		redisplay = true;
	}

	public String getName(){

		return name;
	}

	public void setName(String name){

		this.name = name;
	}

	public String getProperty(){

		return property;
	}

	public void setProperty(String property){

		this.property = property;
	}

	public String getMaxlength(){

		return maxlength;
	}

	public void setMaxlength(String maxlength){

		this.maxlength = maxlength;
	}

	public String getValue(){

		return value;
	}

	public void setValue(String value){

		this.value = value;
	}

	public boolean isRedisplay(){

		return redisplay;
	}

	public void setRedisplay(boolean redisplay){

		this.redisplay = redisplay;
	}
}
