package com.RestApplication.project;

import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import objects.Player;

import java.util.Base64;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "players";
	
	/**
	 * Decodes authorization header into string
	 * 
	 * @param authorizationHeader
	 * @return Decoded authorization header
	 */
	private String decodeAuthorizationHeader(List<String> authorizationHeader) {
		String authorizationToken = authorizationHeader.get(0);
		authorizationToken = authorizationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
		byte[] decodedBytes = Base64.getDecoder().decode(authorizationToken);
		String decodedString = new String(decodedBytes);
		
		return decodedString;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authorizationHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
			if(authorizationHeader != null && authorizationHeader.size() > 0) {
				String decodedString = decodeAuthorizationHeader(authorizationHeader);
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if("user".equals(username) && "password".equals(password)){
					return;
				}
			}
	
			Response unauthorizedResponse = Response
												.status(Response.Status.UNAUTHORIZED)
												.entity(new Player())
												.build();
			requestContext.abortWith(unauthorizedResponse);
		}
	}
}
