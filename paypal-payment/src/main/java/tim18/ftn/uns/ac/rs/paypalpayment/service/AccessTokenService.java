package tim18.ftn.uns.ac.rs.paypalpayment.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tim18.ftn.uns.ac.rs.paypalpayment.model.AccessToken;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.AccessTokenRepository;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.MerchantRepository;

@Service
public class AccessTokenService {

    private final MerchantRepository merchantRepository;

    private final AccessTokenRepository accessTokenRepository;

    private RestTemplate restTemplate;

    @Autowired
    public AccessTokenService(MerchantRepository merchantRepository, AccessTokenRepository accessTokenRepository) {
        this.restTemplate = new RestTemplate();
        this.merchantRepository = merchantRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Transactional
    public String getAccessToken(Integer appId) throws UnsupportedEncodingException {
//        Optional<AccessToken> token = accessTokenRepository.findById(appId);
//        if(token.isPresent()){
//            return token.get().getToken();
//        }

        Merchant merchant = merchantRepository.getOne(appId);
        // TODO: Decrypt merchant id and secret
        String merchantId = merchant.getClientId();
        String merchantPassword = merchant.getClientSecret();

        String paypalAPI = "https://api.sandbox.paypal.com/v1/oauth2/token?"
			+ URLEncoder.encode("grant_type", "UTF-8")
			+ "=" + URLEncoder.encode("client_credentials", "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(merchantId, merchantPassword);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);

        Gson gson = new Gson();
        AccessToken accessToken = new AccessToken();
        accessToken.setAppId(appId);
        accessToken.setToken(gson.fromJson(jsonResponse, JsonObject.class).get("access_token").getAsString());
        accessToken.setExpiresAt(
        	Long.parseLong(gson.fromJson(jsonResponse, JsonObject.class).get("expires_in").getAsString())
        );
        System.out.println("Temporarily saving access tokens for paypal");
        accessTokenRepository.save(accessToken);

        return accessToken.getToken();
    }
}