package org.modelcatalogue.discourse.sso

import groovyx.net.http.URIBuilder
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SingleSignOn {

    private final String singleSignOnSecret
    private final String discourseServerUrl

    SingleSignOn(String singleSignOnSecret, String discourseServerUrl) {
        this.singleSignOnSecret = singleSignOnSecret
        this.discourseServerUrl = discourseServerUrl
    }
    String verifyParameters(String payload, String sig) {
        verifyParameters(singleSignOnSecret, payload, sig)
    }

    URL getRedirectURL(String nonce, User user){
        def params = prepareResponseParameters(singleSignOnSecret, nonce, user)
        URIBuilder uriBuilder = new URIBuilder(discourseServerUrl)
        uriBuilder.path = '/session/sso_login'
        uriBuilder.addQueryParam('sso', params.sso)
        uriBuilder.addQueryParam('sig', params.sig)

        uriBuilder as URL
    }

    /**
     * Verifies the incoming request and returns the nonce
     * @param key secret key
     * @param payload payload from the request
     * @param sig signature from the request
     * @return nonce from the request
     * @throws IllegalArgumentException if payload or signature is null or if the checksum fails
     */
    static String verifyParameters(String key, String payload, String sig) {
        if (payload == null || sig == null) {
            throw new IllegalArgumentException("error parameter")
        }
        if (!checksum(key, payload).equals(sig)) {
            throw new IllegalArgumentException("checksum failed")
        }
        String urlDecode = URLDecoder.decode(payload, "UTF-8");
        return new String(Base64.decodeBase64(urlDecode));
    }

    static prepareResponseParameters(String key, String nonce, User user) {
        String urlEncode = nonce + "&name=" + URLEncoder.encode(user.name, "UTF-8") + "&username=" + URLEncoder.encode(user.username, "UTF-8") + "&email=" + URLEncoder.encode(user.email, "UTF-8")+ "&external_id=" + URLEncoder.encode(user.externalId + "", "UTF-8");
        String urlBase64 = new String(Base64.encodeBase64(urlEncode.getBytes("UTF-8")));

        [sso: urlBase64, sig: checksum(key, urlBase64)]
    }

    static String checksum(String macKey, String macData) {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = macKey.getBytes("UTF-8");
        byte[] dataBytes = macData.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(secretKey);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexBytes = new Hex().encode(doFinal);
        return new String(hexBytes);
    }

}
