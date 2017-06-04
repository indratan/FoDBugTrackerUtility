package com.fortify.processrunner.util.rest;

import org.apache.commons.lang.StringUtils;

import com.fortify.processrunner.context.Context;
import com.fortify.processrunner.context.ContextPropertyDefinition;
import com.fortify.processrunner.context.ContextPropertyDefinitions;
import com.fortify.util.rest.ProxyConfiguration;

public final class ContextAwareProxyConfigurationFactory {
	private static final String PRP_SFX_URL = "ProxyUrl";
	private static final String PRP_SFX_USER_NAME = "ProxyUserName";
	private static final String PRP_SFX_PASSWORD = "ProxyPassword";
	
	private ContextAwareProxyConfigurationFactory() {}
	
	public static final void addContextPropertyDefinitions(ContextPropertyDefinitions contextPropertyDefinitions, Context context, String name) {
		contextPropertyDefinitions.add(new ContextPropertyDefinition(name+PRP_SFX_URL, name+" Proxy URL", context, null, false));
		contextPropertyDefinitions.add(new ContextPropertyDefinition(name+PRP_SFX_USER_NAME, name+" Proxy User Name", context, null, false));
		contextPropertyDefinitions.add(new ContextPropertyDefinition(name+PRP_SFX_PASSWORD, name+" Proxy Password", context, "Read from console if proxy user name is set", false));
	}
	
	public static final ProxyConfiguration getProxyConfiguration(Context context, String name) {
		ProxyConfiguration proxy = null;
		String proxyUrl = (String)context.get(name+PRP_SFX_URL);
		if ( StringUtils.isNotBlank(proxyUrl) ) {
			proxy = new ProxyConfiguration();
			proxy.setUriString(proxyUrl);
			String userName = (String)context.get(name+PRP_SFX_USER_NAME);
			if ( StringUtils.isNotBlank(userName) ) {
				proxy.setUserName(userName);
				String password = (String)context.get(name+PRP_SFX_PASSWORD);
				if ( StringUtils.isBlank(password) ) {
					password = new String(System.console().readPassword(name+" Proxy Password: "));
				}
				proxy.setPassword(password);
			}
		}
		return proxy;
	}
}
