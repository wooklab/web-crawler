package dev.wooklab.crawl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.stream.Stream;

public class HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    enum CharSetEnum {
        EUC_KR("EUC-KR"), UTF_8("UTF-8"), MS949("MS949"), KS_C("KS_C_5601-1987");

        private String charset;

        CharSetEnum(String charset) {
            this.charset = charset;
        }

        public String getCharSet() {
            return charset;
        }

        public static Stream<CharSetEnum> stream() {
            return Stream.of(CharSetEnum.values());
        }

    }

    private HttpHandler() {
        throw new AssertionError();
    }


    /**
     * Method to retrieves HTML source code using HTTP URL Connection.<br/>
     *
     * @param seedUrl 연결할 URL
     * @return HTML Content
     */
    public static String getHttpHTML(String seedUrl) {
        URL url;
        HttpURLConnection con = null;

        BufferedReader  br = null;
        String          readLine;
        String          contentCharset;
        StringBuilder   sb = new StringBuilder();

        try {
            url = new URL(seedUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            /* When response code is not 200, return to response code */
            String responseCode = String.valueOf(con.getResponseCode());
            logger.debug("Response CODE : {}", responseCode);
            if (responseCode.equals("200") == false)
                return null;

            /* To get the encoding value of the document */
            contentCharset = con.getContentType();
            logger.debug("load charset : {}", contentCharset);

            br = getBufferedReaderNewInstance(con, contentCharset);

            while ((readLine = br.readLine()) != null) {
//                sb.append(readLine + "\r\n");   // add a newline string
                sb.append(readLine);
            }
            br.close();

        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File Not Found Error in getHttpHTML method", fileNotFoundException);
        } catch (IOException iOException) {
            logger.error("IO Error Error in getHttpHTML method", iOException);
        } finally {
            destory(con, br);
        }

        return sb.toString();
    }

    private static BufferedReader getBufferedReaderNewInstance(HttpURLConnection conn, String contentCharset) throws IOException {
        CharSetEnum correctCharset = CharSetEnum.stream()
                .filter(charset -> contentCharset.toUpperCase().contains(charset.getCharSet()))
                .findAny().orElse(CharSetEnum.EUC_KR);
        logger.debug("Set Charset: {}", correctCharset.getCharSet());
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), correctCharset.getCharSet()));

    }

    public static String getHttpsRefererJSON(String baseUrl, String referer) {
        StringBuilder sb = new StringBuilder();
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(baseUrl);

            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; " +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC1; .NET CLR 2.0.50727; " +
                    ".NET CLR 3.5.30729; InfoPath.2; .NET CLR 3.0.30729)");
            conn.setRequestProperty("Referer", referer);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setHostnameVerifier((s, sslSession) -> false);

            // SSL setting
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            } }, null);
            conn.setSSLSocketFactory(context.getSocketFactory());

            /* When response code is not 200, return to response code */
            String responseCode = String.valueOf(conn.getResponseCode());

            logger.debug("Response CODE : {}", responseCode);
            if (responseCode.equals("200") == false)
                return null;

            /* To get the encoding value of the document */
            String contentCharset = conn.getContentType();
            logger.debug("load charset : {}", contentCharset);


            /* 설정된 인코딩으로 InputStream */
            br = getBufferedReaderNewInstance(conn, contentCharset);


            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
//                sb.append("\r\n");
            }


        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File Error", fileNotFoundException);
        } catch (IOException iOException) {
            logger.error("IO Error", iOException);
        } catch (KeyManagementException keyManagementException) {
            logger.error("KeyManagement Error", keyManagementException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("NoSuchAlgorithm Error", noSuchAlgorithmException);
        } finally {
            destory(conn, br);
        }
        return sb.toString();
    }

    private static void destory(HttpURLConnection conn, BufferedReader br) {
        if (conn != null)
            conn.disconnect();
        try {
            if (br != null)
                br.close();
        } catch (IOException e) {
            logger.error("IO Error in destory method", e);
        }
    }
}
