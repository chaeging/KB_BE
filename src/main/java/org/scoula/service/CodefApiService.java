package org.scoula.service;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.ChungyakAccountDTO;
import org.scoula.mapper.AccountMapper;
import org.scoula.util.RsaEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class CodefApiService {

    @Autowired
    private RsaEncryptionUtil rsaEncryptor;

    @Autowired
    private AccountMapper accountMapper;

    private static final String CLIENT_ID = "1a7ca0c1-62f9-4276-a287-573c79c6ac0d";
    private static final String CLIENT_SECRET = "353eb61f-4017-4480-af85-784b0bc5be06";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnsO8QhkAzifN1nQgvfVyyLv8Hptsr/dmCK7MS2091rriNTzl1Q0fYHBHuBClfh3DNVYICgx3saDiYJ06EoAg4wDcZ1QMID/ehdOr6A5AvCqzt8SSWDkd6pkW0nW58BQxgO0/blvP9nooUzYcmyxDohDeHBmCtdMxbzhzMSsQEkPG8IKqFH//fTTgtp6SLmEPkqKC+rayMCgxoCl+GI+DVSr/Bj/iHyFv/t/bEqZXwbg/PVeFagjthyy43uVQ5F2IYwgp8z3N7W4yhTJxuT5h9+eiMo0PzG0M64aPBA4nY0tyZ48FRjXIfinYtJiTNW0M0nsASaVYcqhUTF4OLKXJXwIDAQAB";
    //private static final String CLIENT_ID = "b46daa0e-a37e-410f-a24c-ce6d6a731f3e";
    //private static final String CLIENT_SECRET = "293b6562-a80c-4360-9184-a7229233084b";
    //private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApm7RFqlurGGqgoze4OJyZawg03Jj9kk/mhhiBYPP7w7g3oPUxQ4/Xdu4o0PE/N39nqULxTf89DCRAoCtWApnEc45MoIOKgGreZjHoDgPNxbQw335Ivb3Fuw9F/B3sdG8P+36Qf/aTLBWdVBAsDspWV5V68e8uA0Hi+gg+7lhVIkF1JGfLi0r/9Jm0EN/Rj/8ik+zi+UejpHI9zdyKRI0E9MxhoI6e7HM3YJ93rMGqAVDim+gzVgZAUbsUnW4cGGXX5QiFAChqq/07E3WJDjU1hmyC5z7RE6zrZKFVcOS0Jx1sMjy146eS3k2xUrlBsNekCcx0uy6S/oY1XJWh1s3sQIDAQAB";


public List<ChungyakAccountDTO> autoConnectAndFetchChungyakAccounts(
        String id, String password, String organization, String bankName, int userIdx) throws Exception {

    String accessToken = getAccessToken();
    String encryptedPassword = rsaEncryptor.encryptPassword(PUBLIC_KEY, password);
    String connectedId = createConnectedId(accessToken, id, encryptedPassword, organization);

    if (connectedId == null) {
        return Collections.emptyList();
    }

    String accountListJson = requestAccountList(accessToken, connectedId, organization);

    List<ChungyakAccountDTO> accounts = filterChungyakAccounts(accountListJson, bankName);
    saveChungyakAccounts(accounts, userIdx, false);

    return accounts;
}

    private void saveChungyakAccounts(List<ChungyakAccountDTO> accounts, int userIdx, boolean isPayment) {
        for (ChungyakAccountDTO dto : accounts) {
            accountMapper.insertChungyakAccount(dto, userIdx, isPayment);
        }
    }

    public List<ChungyakAccountDTO> getChungyakAccountsByUserIdx(int userIdx) {
        return accountMapper.findAccountsByUserIdx(userIdx);
    }



    private String getAccessToken() throws Exception {
        String basicAuth = Base64.getEncoder()
                .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        String url = "https://oauth.codef.io/oauth/token";
        String body = "grant_type=client_credentials";

        String response = postFormRequest(url, basicAuth, body);

        log.info("AccessToken 응답: {}", response);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    private String createConnectedId(String accessToken, String id, String encryptedPassword, String organization) throws Exception {
        String url = "https://development.codef.io/v1/account/create";

        String bodyJson = "{"
                + "\"accountList\": [{"
                + "\"countryCode\": \"KR\","
                + "\"businessType\": \"BK\","
                + "\"clientType\": \"P\","
                + "\"organization\": \"" + organization + "\","
                + "\"loginType\": \"1\","
                + "\"id\": \"" + id + "\","
                + "\"password\": \"" + encryptedPassword + "\""
                + "}]"
                + "}";

        String response = postJsonRequest(url, accessToken, bodyJson);

        log.info("ConnectedId 응답: {}", response);

        // JSON 파싱 후 connectedId 추출
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        if (jsonObject.has("data")) {
            JsonObject dataObject = jsonObject.getAsJsonObject("data");
            if (dataObject.has("connectedId")) {
                return dataObject.get("connectedId").getAsString();
            }
        }

        return null;
    }


    private String requestAccountList(String accessToken, String connectedId, String organization) throws Exception {
        String url = "https://development.codef.io/v1/kr/bank/p/account/account-list";

        String bodyJson = "{"
                + "\"organization\": \"" + organization + "\","
                + "\"connectedId\": \"" + connectedId + "\""
                + "}";

        return postJsonRequest(url, accessToken, bodyJson);
    }

    private String postFormRequest(String urlStr, String basicAuth, String body) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + basicAuth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        return readResponse(conn);
    }

    private String postJsonRequest(String urlStr, String accessToken, String bodyJson) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(bodyJson.getBytes(StandardCharsets.UTF_8));
        }

        return readResponse(conn);
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        String rawResponse = response.toString();
        String decodedResponse = URLDecoder.decode(rawResponse, StandardCharsets.UTF_8.name());

        log.info("디코딩된 응답: {}", decodedResponse);

        return decodedResponse;
    }

    private List<ChungyakAccountDTO> filterChungyakAccounts(String json, String bankName) {
        List<ChungyakAccountDTO> resultList = new ArrayList<>();

        Gson gson = new Gson();
        JsonObject root = gson.fromJson(json, JsonObject.class);

        if (root.has("data")) {
            JsonObject data = root.getAsJsonObject("data");

            if (data.has("resDepositTrust")) {
                JsonArray accounts = data.getAsJsonArray("resDepositTrust");

                for (JsonElement elem : accounts) {
                    JsonObject account = elem.getAsJsonObject();
                    String accountName = account.has("resAccountName") ? account.get("resAccountName").getAsString() : "";

                    if (accountName.contains("청약")) {
                        String display = account.has("resAccountDisplay") ? account.get("resAccountDisplay").getAsString() : "";
                        String balance = account.has("resAccountBalance") ? account.get("resAccountBalance").getAsString() : "";
                        String startDate = account.has("resAccountStartDate") ? account.get("resAccountStartDate").getAsString() : "";
                        String resAccount = account.has("resAccount") ? account.get("resAccount").getAsString() : "";
                        String resAccountName = account.has("resAccountName") ? account.get("resAccountName").getAsString() : "";

                        ChungyakAccountDTO dto = new ChungyakAccountDTO(display, balance, startDate, resAccount, resAccountName,bankName);
                        resultList.add(dto);
                    }
                }
            }
        }

        return resultList;
    }


}
