package com.senthink.www.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jason on 2016/11/25.
 */
@ConfigurationProperties(prefix = "senthink" ,ignoreUnknownFields = false )
public class ColumbiaProperties {


    public Logging getLogging() {
        return logging;
    }

    private final Logging logging = new Logging();

    public static class Logging {

        private final Logstash logstash = new Logstash();

        public Logstash getLogstash() {
            return logstash;
        }

        public static class Logstash {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 5000;

            private int queueSize = 512;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public int getQueueSize() {
                return queueSize;
            }

            public void setQueueSize(int queueSize) {
                this.queueSize = queueSize;
            }
        }

        private final Smtp smtp = new Smtp();

        public Smtp getSmtp(){return smtp;}

        public static class Smtp{

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            private boolean enabled = false;

            private String host = "smtp.senthink.com";

            private int port = 25;

            private String username = "lingcy@senthink.com";

            private String password = "senthink1101";

            private boolean ssl = false;

            private boolean asynchronousSending = false;

            private String to = "lingcy@lierda.com";

            private String from = "lingcy@senthink.com";

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public boolean getSsl() {
                return ssl;
            }

            public void setSsl(boolean ssl) {
                this.ssl = ssl;
            }

            public boolean getAsynchronousSending() {
                return asynchronousSending;
            }

            public void setAsynchronousSending(boolean asynchronousSending) {
                this.asynchronousSending = asynchronousSending;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }
        }
    }
    
    
    private final Async async = new Async();

    public Async getAsync() {
        return async;
    }

    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }
}
