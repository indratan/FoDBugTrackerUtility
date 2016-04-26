package com.fortify.fod.connection;

import com.sun.jersey.api.representation.Form;

/**
 * <p>This {@link AbstractFoDConnectionFactory} implementation
 * allows for configuring client credentials used to connect to FoD.</p>
 */
public class FoDConnectionFactoryClientCredentials extends AbstractFoDConnectionFactory {
	private String clientId;
	private String clientSecret;
	
	public FoDConnectionFactoryClientCredentials() {
		setGrantType("client_credentials");
	}
	
	@Override
	public void addCredentials(Form form) {
		form.putSingle("client_id", getClientId());
		form.putSingle("client_secret", getClientSecret());
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
