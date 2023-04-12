package cn.evolvefield.sdk.fastws.client.config;

import cn.evolvefield.sdk.fastws.client.core.process.BaseActuator;
import lombok.Data;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:40
 * Description:
 */
@Data
public class WSConfig {
    /**
     * 封装 WebSocket 支持的协议类型
     * @author cnlimiter
     * @version 1.0.0
     */
    public final static class Agreement {
        public static final String WS = "ws";
        public static final String WSS = "wss";
    }

    /**
     * 封装 WebSocket 支持的版本号
     * @author cnlimiter
     * @version 1.0.0
     */
    public final static class Version {
        public static final String V00 = "V00";
        public static final String V07 = "V07";
        public static final String V08 = "V08";
        public static final String V13 = "V13";
        public static final String UNKNOWN = "UNKNOWN";
    }

    /**
     * 封装 WebSocket 日志版本
     * @author cnlimiter
     * @version 1.0.0
     */
    public final static class LogLevel {
        public static final String NONE = "NONE";
        public static final String TRACE = "TRACE";
        public static final String DEBUG = "DEBUG";
        public static final String INFO = "INFO";
        public static final String WARN = "WARN";
        public static final String ERROR = "ERROR";
    }

    /**
     * 封装 Cookies 的对象
     * @author cnlimiter
     * @version 1.0.0
     */
    @Getter
    public static class Cookie {
        private String name;
        private String value;

        public Cookie(String name, String value) {
            this.name = name;
            this.value = value;
        }


        public String content() {
            return name + "=" + value;
        }
    }

    // 端口
    private int port;
    // 主机地址
    private String host;
    // 协议类型
    private String agreement;
    // 请求路径
    private String path;
    // 请求的参数
    private String param;
    // URI 地址
    private URI uri;
    // 处理器
    private BaseActuator actuator;
    // 版本号
    private String version = Version.V13;
    // Netty 日志是否开启
    private boolean nettyLoggerBool = false;
    // Netty 日志的等级
    private String nettyLogLevel = LogLevel.NONE;

    // 请求的 Cookie
    private List<Cookie> cookies = new ArrayList<>();
    // 请求的头部
    private Map<String, Object> httpHeaders = new HashMap<>();

    // 是否自定义证书
    private String sslPath;

    public WSConfig() {
        try {
            this.setUri(new URI("ws://127.0.0.1:8080"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(String url) {
        try {
            this.setUri(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri) {
        this.setUri(uri);
    }

    public WSConfig(URI uri, Cookie... cookies) {
        this.setUri(uri);
        this.addCookies(cookies);
    }

    public WSConfig(String url, Cookie... cookies) {
        try {
            this.setUri(new URI(url));
            this.addCookies(cookies);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, Map<String, Object> httpHeaders) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
    }

    public WSConfig(String url, Map<String, Object> httpHeaders) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, BaseActuator actuator) {
        this.setUri(uri);
        this.setActuator(actuator);
    }

    public WSConfig(String url, BaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, Map<String, Object> httpHeaders, List<Cookie> cookies) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.addCookies(cookies);
    }

    public WSConfig(String url, Map<String, Object> httpHeaders, List<Cookie> cookies) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.addCookies(cookies);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, Map<String, Object> httpHeaders, BaseActuator actuator) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.setActuator(actuator);
    }

    public WSConfig(String url, Map<String, Object> httpHeaders, BaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, List<Cookie> cookies, BaseActuator actuator) {
        this.setUri(uri);
        this.addCookies(cookies);
        this.setActuator(actuator);
    }

    public WSConfig(String url, List<Cookie> cookies, BaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addCookies(cookies);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public WSConfig(URI uri, Map<String, Object> httpHeaders, List<Cookie> cookies, BaseActuator actuator) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.addCookies(cookies);
        this.setActuator(actuator);
    }

    public WSConfig(String url, Map<String, Object> httpHeaders, List<Cookie> cookies, BaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.addCookies(cookies);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    private void setPort(int port) {
        this.port = port;
        if (port < 0) {
            try {
                if (Agreement.WS.equals(this.getAgreement().toLowerCase())) this.port = 80;
                else if (Agreement.WSS.equals(this.getAgreement().toLowerCase())) this.port = 443;
                else throw new RuntimeException(this.getClass().toString() + " port parameter exception.");
                this.uri = new URI(this.uri.getScheme()
                        + "://" + this.uri.getHost()
                        + ":" + this.getPort()
                        + this.uri.getRawPath()
                        + (this.uri.getQuery() == null ? "" : "?" + this.uri.getQuery()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    private void setHost(String host) {
        if (host == null) throw new RuntimeException(this.getClass().toString() + " host parameter exception.");
        this.host = host;
    }


    private void setAgreement(String agreement) {
        if (agreement == null) throw new RuntimeException(this.getClass().toString() + " agreement parameter exception.");
        this.agreement = agreement;
    }

    private void setParam(String param) {
        this.param = param == null ? "" : param;
    }

    public void setUri(URI uri) {
        try {
            this.uri = uri;
            this.uri = new URI(this.uri.getScheme().toLowerCase()
                    + "://" + this.uri.getHost().toLowerCase()
                    + (this.uri.getPort() < 0 ? "" : ":" + this.uri.getPort())
                    + this.uri.getRawPath().toLowerCase()
                    + (this.uri.getQuery() == null ? "" : "?" + this.uri.getQuery().toLowerCase()));
            this.setAgreement(this.uri.getScheme());
            this.setHost(this.uri.getHost());
            this.setPort(this.uri.getPort());
            this.setParam(this.uri.getQuery());
            this.setPath(this.uri.getRawPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



    public void addCookies(Cookie... cookies) {
        this.cookies.addAll(Arrays.asList(cookies));
    }

    public void addCookies(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
    }

    public void clearCookies() {
        this.cookies.clear();
    }

    public void addHttpHeaders(Map<String, Object> httpHeaders) {
        if (httpHeaders != null) {
            for (String key : httpHeaders.keySet()) {
                this.httpHeaders.put(key, httpHeaders.get(key));
            }
        }
    }

    public void delHttpHeaders(String headerName) {
        this.httpHeaders.remove(headerName);
    }

    public void clearHttpHeaders() {
        this.httpHeaders.clear();
    }



    @Override
    public String toString() {
        return "{" + "\"port\":" +
                port +
                ",\"host\":\"" +
                host + '\"' +
                ",\"agreement\":\"" +
                agreement + '\"' +
                ",\"path\":\"" +
                path + '\"' +
                ",\"param\":\"" +
                param + '\"' +
                ",\"uri\":" +
                uri +
                ",\"actuator\":" +
                actuator +
                ",\"version\":\"" +
                version + '\"' +
                ",\"nettyLoggerBool\":" +
                nettyLoggerBool +
                ",\"nettyLogLevel\":\"" +
                nettyLogLevel + '\"' +
                ",\"cookies\":" +
                cookies +
                ",\"httpHeaders\":" +
                httpHeaders +
                ",\"sslPath\":\"" +
                sslPath + '\"' +
                '}';
    }
}
