package com.angel.elasticsearch7.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);
	/**
	 * 集群名称
	 */
	@Value("${spring.elasticsearch.cluster-name}")
	private String clusterName;
	/**
	 * 集群节点
	 */
	@Value("${spring.elasticsearch.cluster-nodes}")
	private String[] clusterNodes;
	/**
	 * x-pack 用户名
	 */
	@Value("${spring.elasticsearch.username}")
	private String username;
	/**
	 * x-pack 密码
	 */
	@Value("${spring.elasticsearch.password}")
	private String password;
	
	private int ConnectTimeout = 5000;
	
	private int SocketTimeout = 60000;

	private int MaxRetryTimeoutMillis = 60000;
	
	private int IoThreadCount = 1;
	/**
	 * The time to wait for a ping response from a node. Defaults to 5s.
	 */
	private String clientPingTimeout = "5s";
	/**
	 * How often to sample / ping the nodes listed and connected. Defaults to 5s.
	 */
    private String clientNodesSamplerInterval = "5s";

    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数

	@Bean
	public RestHighLevelClient client(){
        logger.info("start init restHighLevelClient ...");
        HttpHost[] hosts = new HttpHost[clusterNodes.length];
        for(int i=0,j=clusterNodes.length;i<j;i++){
            String host = clusterNodes[i].split(":")[0];
            Integer port = Integer.valueOf(clusterNodes[i].split(":")[1]);
            hosts[i] = new HttpHost(host,port);
        }
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(hosts)
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						httpClientBuilder.setMaxConnTotal(maxConnectNum);
						httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    	return httpClientBuilder.setDefaultCredentialsProvider(setCredentialProvider());
                    }
                })
                // 配置连接时间延时
                .setRequestConfigCallback(requestConfigBuilder -> {
                    requestConfigBuilder.setConnectTimeout(ConnectTimeout);
                    requestConfigBuilder.setSocketTimeout(SocketTimeout);
                    return requestConfigBuilder;
                }));
        return client;
	}

    private CredentialsProvider setCredentialProvider() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        return credentialsProvider;
    }
}