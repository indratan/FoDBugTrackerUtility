package com.fortify.processrunner.processor;

import java.util.HashMap;
import java.util.Map;

import com.fortify.processrunner.context.Context;
import com.fortify.util.spring.SpringExpressionUtil;

/**
 * This {@link IProcessor} implementation allows for building a 
 * String {@link Map} based on the configured expressions.
 */
public class ProcessorBuildStringMap extends AbstractProcessor {
	private String rootExpression;
	private String appenderExpression;
	private Map<String,String> rootExpressionTemplates;
	private Map<String,String> appenderExpressionTemplates;
	
	@Override
	protected boolean process(Context context) {
		Map<String,String> map = new HashMap<String,String>(rootExpressionTemplates.size());
		if ( getRootExpressionTemplates() != null ) {
			Object rootObject = SpringExpressionUtil.evaluateExpression(context, rootExpression, Object.class);
			for ( Map.Entry<String, String> rootExpressionTemplate : getRootExpressionTemplates().entrySet() ) {
				map.put(rootExpressionTemplate.getKey(), SpringExpressionUtil.evaluateTemplateExpression(rootObject, rootExpressionTemplate.getValue(), String.class));
			}
		}
		if ( getAppenderExpressionTemplates() != null ) {
			Iterable<?> appenderValues = SpringExpressionUtil.evaluateExpression(context, appenderExpression, Iterable.class);
			for ( Object appenderValue : appenderValues ) {
				for ( Map.Entry<String, String> appenderExpressionTemplate : getAppenderExpressionTemplates().entrySet() ) {
					String key = appenderExpressionTemplate.getKey();
					String value = map.get(key);
					if ( value == null ) { value = ""; }
					value += ""+SpringExpressionUtil.evaluateTemplateExpression(appenderValue, appenderExpressionTemplate.getValue(), Object.class);
					map.put(key, value);
				}
			}
		}
		
		context.as(IContextStringMap.class).setStringMap(map);
		
		return true;
	}

	public String getRootExpression() {
		return rootExpression;
	}

	public void setRootExpression(String rootExpression) {
		this.rootExpression = rootExpression;
	}

	public String getAppenderExpression() {
		return appenderExpression;
	}

	public void setAppenderExpression(String appenderExpression) {
		this.appenderExpression = appenderExpression;
	}

	public Map<String, String> getRootExpressionTemplates() {
		return rootExpressionTemplates;
	}

	public void setRootExpressionTemplates(
			Map<String, String> rootExpressionTemplates) {
		this.rootExpressionTemplates = rootExpressionTemplates;
	}

	public Map<String, String> getAppenderExpressionTemplates() {
		return appenderExpressionTemplates;
	}

	public void setAppenderExpressionTemplates(
			Map<String, String> appenderExpressionTemplates) {
		this.appenderExpressionTemplates = appenderExpressionTemplates;
	}
	
	public static interface IContextStringMap {
		public void setStringMap(Map<String, String> map);
		public Map<String, String> getStringMap();
	}
}
