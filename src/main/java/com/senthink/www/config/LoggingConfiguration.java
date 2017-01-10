package com.senthink.www.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.boolex.JaninoEventEvaluator;
import ch.qos.logback.classic.html.HTMLLayout;
import ch.qos.logback.classic.net.SMTPAppender;
import ch.qos.logback.core.filter.EvaluatorFilter;
import ch.qos.logback.core.spi.FilterReply;
import net.logstash.logback.appender.LogstashSocketAppender;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * Created by lingcy on 2016/11/15.
 */
@Configuration
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.profiles.active}")
    private String environment;

    @Inject
    private ColumbiaProperties columbiaProperties;

    @PostConstruct
    private void init() {
        if (!"dev".equals(environment) && columbiaProperties.getLogging().getLogstash().isEnabled()) {
            addLogstashAppender();
        }
        if ("pro".equals(environment) && columbiaProperties.getLogging().getSmtp().isEnabled()) {
            addSmtpAppender();
        }
    }

    public void addSmtpAppender()  {
        log.info("Initializing smtp logging");
        SMTPAppender  smtpAppender=new SMTPAppender();
        smtpAppender.setName("SMTP");
        smtpAppender.setContext(context);


        smtpAppender.setSmtpHost(columbiaProperties.getLogging().getSmtp().getHost());
        smtpAppender.setSmtpPort(columbiaProperties.getLogging().getSmtp().getPort());
        smtpAppender.setAsynchronousSending(columbiaProperties.getLogging().getSmtp().getAsynchronousSending());
        smtpAppender.setUsername(columbiaProperties.getLogging().getSmtp().getUsername());
        smtpAppender.setPassword(columbiaProperties.getLogging().getSmtp().getPassword());
        smtpAppender.setFrom(columbiaProperties.getLogging().getSmtp().getFrom());
        smtpAppender.addTo(columbiaProperties.getLogging().getSmtp().getTo());
        smtpAppender.setSSL(columbiaProperties.getLogging().getSmtp().getSsl());
        String subject = appName+"-"+environment;
        smtpAppender.setSubject(subject);

        HTMLLayout htmlayout= new HTMLLayout();
        htmlayout.setPattern("%date%level%thread%logger{0}%line%mdc%message");
        htmlayout.setContext(context);
        htmlayout.start();
        smtpAppender.setLayout(htmlayout);

        EvaluatorFilter evaluatorFilter = new EvaluatorFilter();
        JaninoEventEvaluator janinoEventEvaluator= new JaninoEventEvaluator();
        janinoEventEvaluator.setExpression("level >= ERROR && message.contains(\"email\")");
        janinoEventEvaluator.start();
        evaluatorFilter.setEvaluator(janinoEventEvaluator);
        evaluatorFilter.setOnMatch(FilterReply.ACCEPT);
        evaluatorFilter.setOnMismatch(FilterReply.DENY);
        evaluatorFilter.start();

        smtpAppender.addFilter(evaluatorFilter);
        smtpAppender.start();

        context.getLogger("ROOT").addAppender(smtpAppender);
    }

    public void addLogstashAppender() {
        log.info("Initializing Logstash logging");
        LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
        logstashAppender.setName("LOGSTASH");
        logstashAppender.setContext(context);
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\",\"environment\":\"" + environment + "\"}";

        // Set the Logstash appender config from senthink properties
        logstashAppender.setSyslogHost(columbiaProperties.getLogging().getLogstash().getHost());
        logstashAppender.setPort(columbiaProperties.getLogging().getLogstash().getPort());
        logstashAppender.setCustomFields(customFields);

        // Limit the maximum length of the forwarded stacktrace so that it won't exceed the 8KB UDP limit of logstash
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setMaxLength(7500);
        throwableConverter.setRootCauseFirst(true);
        logstashAppender.setThrowableConverter(throwableConverter);

        logstashAppender.start();

        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(columbiaProperties.getLogging().getLogstash().getQueueSize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();

        context.getLogger("ROOT").addAppender(asyncLogstashAppender);
    }


}
