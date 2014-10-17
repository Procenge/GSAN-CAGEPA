
package gcom.gui.componente;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.OptionTag;
import org.apache.struts.taglib.html.OptionsTag;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.RequestUtils;

public class ListBox
				extends SelectTag
				implements UiComponent {

	private Collection options = new ArrayList();

	public ListBox() {

	}

	public ListBox(HttpServletRequest request, String id) {

		request.setAttribute(id, this);
		setId(id);
	}

	public ListBox(HttpSession session, String id) {

		session.setAttribute(id, this);
		setId(id);
	}

	public String getHtmlBody(PageContext pageContext) throws JspException{

		setPageContext(pageContext);

		// Store this tag itself as a page attribute
		pageContext.setAttribute(Constants.SELECT_KEY, this);

		// Render a tag representing the end of our current form
		StringBuffer results = new StringBuffer();

		results.append(renderSelectStartElement());

		this.calculateMatchValues();

		for(Object option : options){

			if(option instanceof Option){

				Option optionTag = (Option) option;
				optionTag.setPageContext(pageContext);
				String value = optionTag.getValue();
				String label = (String) optionTag.getValue(value);
				optionTag.doInitBody();
				optionTag.doStartTag();
				optionTag.doAfterBody();
				optionTag.setText(label);
				optionTag.doEndTag();
				results.append(optionTag.renderOptionElement());
				optionTag.release();

			}else if(option instanceof Options){

				Options optionsTag = (Options) option;
				optionsTag.setPageContext(pageContext);
				String name = optionsTag.getCollection();
				pageContext.setAttribute(name, optionsTag.getValue(name));
				optionsTag.doStartTag();
				optionsTag.doAfterBody();
				optionsTag.doEndTag();
				results.append(optionsTag.renderOptionsElements());
				optionsTag.release();
				pageContext.removeAttribute(name);
			}
		}

		// Remove the page scope attributes we created
		pageContext.removeAttribute(Constants.SELECT_KEY);

		results.append("</select>");

		return results.toString();
	}

	public OptionTag addOption(String value, String label){

		Option optionTag = new Option();
		optionTag.setValue(value);
		optionTag.setValue(value, label);
		this.options.add(optionTag);
		return optionTag;
	}

	public OptionsTag addOptions(Collection options, String property, String labelProperty){

		String name = "selectOptions";
		Options optionsTag = new Options();
		optionsTag.setCollection(name);
		optionsTag.setValue(name, options);
		optionsTag.setLabelProperty(labelProperty);
		optionsTag.setProperty(property);
		this.options.add(optionsTag);
		return optionsTag;
	}

	@Override
	public void doInitBody() throws JspException{

	}

	@Override
	public int doStartTag() throws JspException{

		return SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException{

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException{

		return SKIP_PAGE;
	}

	/**
	 * Calculate the match values we will actually be using.
	 * 
	 * @throws JspException
	 */
	private void calculateMatchValues() throws JspException{

		if(this.value != null){
			this.match = new String[1];
			this.match[0] = this.value;

		}else{

			Object bean = RequestUtils.lookup(pageContext, name, null);
			if(bean == null){
				JspException e = new JspException(messages.getMessage("getter.bean", name));

				RequestUtils.saveException(pageContext, e);
				throw e;
			}
			try{
				this.match = BeanUtils.getArrayProperty(bean, property);
				if(this.match == null){
					this.match = new String[0];
				}
			}catch(IllegalAccessException e){
				RequestUtils.saveException(pageContext, e);
				throw new JspException(messages.getMessage("getter.access", property, name));

			}catch(InvocationTargetException e){
				Throwable t = e.getTargetException();
				RequestUtils.saveException(pageContext, t);
				throw new JspException(messages.getMessage("getter.result", property, t.toString()));

			}catch(NoSuchMethodException e){
				RequestUtils.saveException(pageContext, e);
				throw new JspException(messages.getMessage("getter.method", property, name));
			}
		}
	}

	class Option
					extends OptionTag {

		private String text;

		public void setText(String text){

			this.text = text;
		}

		public String getText(){

			return text;
		}

		/**
		 * Process the start of this tag.
		 * 
		 * @exception JspException
		 *                if a JSP exception has occurred
		 */
		@Override
		public int doStartTag() throws JspException{

			// Initialize the placeholder for our body content
			this.text = null;

			// Do nothing until doEndTag() is called
			return SKIP_BODY;
		}

		@Override
		public int doAfterBody() throws JspException{

			return SKIP_BODY;
		}

		/**
		 * Process the end of this tag.
		 * 
		 * @exception JspException
		 *                if a JSP exception has occurred
		 */
		@Override
		public int doEndTag() throws JspException{

			return SKIP_PAGE;
		}

		/**
		 * Generate an HTML %lt;option&gt; element.
		 * 
		 * @throws JspException
		 * @since Struts 1.1
		 */
		@Override
		protected String renderOptionElement() throws JspException{

			return super.renderOptionElement();
		}

		/**
		 * Return the text to be displayed to the user for this option (if any).
		 * 
		 * @exception JspException
		 *                if an error occurs
		 */
		@Override
		protected String text() throws JspException{

			String optionText = this.text;

			if((optionText == null) && (this.key != null)){
				optionText = RequestUtils.message(pageContext, bundle, locale, key);
			}

			// no body text and no key to lookup so display the value
			if(optionText == null){
				optionText = this.value;
			}

			return optionText;
		}

		@Override
		public void release(){

			this.text = null;
			super.release();
		}
	}

	class Options
					extends OptionsTag {

		private StringBuffer renderOptionsElements = null;

		public String renderOptionsElements(){

			return renderOptionsElements.toString();
		}

		/**
		 * Process the end of this tag.
		 * 
		 * @exception JspException
		 *                if a JSP exception has occurred
		 */
		@Override
		public int doEndTag() throws JspException{

			// Acquire the select tag we are associated with
			SelectTag selectTag = (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);
			if(selectTag == null){
				throw new JspException(messages.getMessage("optionsTag.select"));
			}

			renderOptionsElements = new StringBuffer();

			// If a collection was specified, use that mode to render options
			if(collection != null){
				Iterator collIterator = getIterator(collection, null);
				while(collIterator.hasNext()){

					Object bean = collIterator.next();
					Object value = null;
					Object label = null;

					try{
						value = PropertyUtils.getProperty(bean, property);
						if(value == null){
							value = "";
						}
					}catch(IllegalAccessException e){
						throw new JspException(messages.getMessage("getter.access", property, collection));
					}catch(InvocationTargetException e){
						Throwable t = e.getTargetException();
						throw new JspException(messages.getMessage("getter.result", property, t.toString()));
					}catch(NoSuchMethodException e){
						throw new JspException(messages.getMessage("getter.method", property, collection));
					}

					try{
						if(labelProperty != null){
							label = PropertyUtils.getProperty(bean, labelProperty);
						}else{
							label = value;
						}

						if(label == null){
							label = "";
						}
					}catch(IllegalAccessException e){
						throw new JspException(messages.getMessage("getter.access", labelProperty, collection));
					}catch(InvocationTargetException e){
						Throwable t = e.getTargetException();
						throw new JspException(messages.getMessage("getter.result", labelProperty, t.toString()));
					}catch(NoSuchMethodException e){
						throw new JspException(messages.getMessage("getter.method", labelProperty, collection));
					}

					String stringValue = value.toString();
					addOption(renderOptionsElements, stringValue, label.toString(), selectTag.isMatched(stringValue));
				}
			}

			// Otherwise, use the separate iterators mode to render options
			else{

				// Construct iterators for the values and labels collections
				Iterator valuesIterator = getIterator(name, property);
				Iterator labelsIterator = null;
				if((labelName == null) && (labelProperty == null)){
					labelsIterator = getIterator(name, property); // Same coll.
				}else{
					labelsIterator = getIterator(labelName, labelProperty);
				}

				// Render the options tags for each element of the values coll.
				while(valuesIterator.hasNext()){
					Object valueObject = valuesIterator.next();
					if(valueObject == null){
						valueObject = "";
					}
					String value = valueObject.toString();
					String label = value;
					if(labelsIterator.hasNext()){
						Object labelObject = labelsIterator.next();
						if(labelObject == null){
							labelObject = "";
						}
						label = labelObject.toString();
					}
					addOption(renderOptionsElements, value, label, selectTag.isMatched(value));
				}
			}

			// Evaluate the remainder of this page
			return SKIP_PAGE;

		}

		@Override
		public void release(){

			renderOptionsElements = null;
			super.release();
		}

	}

}
