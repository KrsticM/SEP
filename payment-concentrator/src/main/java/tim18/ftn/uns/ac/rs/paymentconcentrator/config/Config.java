package tim18.ftn.uns.ac.rs.paymentconcentrator.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@Configuration
public class Config {

	@Value("${server.ssl.trust-store}")
	private Resource keyStore;
	@Value("${server.ssl.trust-store-password}")
	private String keyStorePassword;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException {
		System.out.println("In fuction");
		System.out.println("Key: " + keyStorePassword);
		System.out.println("URL: " + keyStore.getURL());
		return builder
	                .requestFactory(() -> validateSSL())
	                .build();
	}
	
	private HttpComponentsClientHttpRequestFactory validateSSL(){
        SSLContext sslContext = null;
        try{
            sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(ResourceUtils.getFile(keyStore.getURL()), keyStorePassword.toCharArray())
                    .build();
        }catch (Exception e){

        }
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,new LocalHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return requestFactory;
    }

    private class LocalHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return "localhost".equalsIgnoreCase(s) || "127.0.0.1".equals(s);
        }
    }
    
    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("password");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGenerator(new MySaltGenerator());
        config.setStringOutputType("hexadecimal");
        encryptor.setConfig(config);
        return encryptor;
    }

}
