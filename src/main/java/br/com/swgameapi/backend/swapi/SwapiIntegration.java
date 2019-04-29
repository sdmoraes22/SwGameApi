package br.com.swgameapi.backend.swapi;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.URLEncoder;

public class SwapiIntegration {
	private String url;

	public SwapiIntegration(String url) {
		this.url = url;
	}

	public String getResponse() {
		String result = "";

		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		method.setRequestHeader("User-Agent", "swapi-Java-SCOTT-SWAPI-JAVA");

		// Provide custom retry handler is necessary
		method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			InputStream responseBody = method.getResponseBodyAsStream();
			StringBuffer strBuffer = new StringBuffer("");

			StringBuilder sb = new StringBuilder(strBuffer);
			try (BufferedReader rdr = new BufferedReader(new InputStreamReader(responseBody))) {
				for (int c; (c = rdr.read()) != -1;) {
					sb.append((char) c);
				}
			}

			// Deal with the response.
			result = sb.toString();

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		return result;
	}

	public String urlEncode(String text) {
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return text;
	}
}