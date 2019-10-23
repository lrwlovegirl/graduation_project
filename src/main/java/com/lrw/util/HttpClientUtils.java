package com.lrw.util;


// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   HttpClientUtils.java


import com.lrw.util.HttpClientResult;
import java.io.*;
import java.util.*;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils
{

    public HttpClientUtils()
    {
    }

    public static HttpClientResult doGet(String url)
        throws Exception
    {
        return doGet(url, null, null);
    }

    public static HttpClientResult doGet(String url, Map params)
        throws Exception
    {
        return doGet(url, null, params);
    }

    public static HttpClientResult doGet(String url, Map headers, Map params)
        throws Exception
    {
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        CloseableHttpResponse httpResponse;
        httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        if(params != null)
        {
            Set entrySet = params.entrySet();
            java.util.Map.Entry entry;
            for(Iterator iterator = entrySet.iterator(); iterator.hasNext(); uriBuilder.setParameter((String)entry.getKey(), entry.getValue().toString()))
                entry = (java.util.Map.Entry)iterator.next();

        }
        System.out.println(uriBuilder.build());
        httpGet = new HttpGet(uriBuilder.build());
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000).setSocketTimeout(6000).build();
        httpGet.setConfig(requestConfig);
        packageHeader(headers, httpGet);
        httpResponse = null;
        HttpClientResult httpclientresult = getHttpClientResult(httpResponse, httpClient, httpGet);
        release(httpResponse, httpClient);
        return httpclientresult;
    }

    public static void packageHeader(Map params, HttpRequestBase httpMethod)
    {
        if(params != null)
        {
            Set entrySet = params.entrySet();
            java.util.Map.Entry entry;
            for(Iterator iterator = entrySet.iterator(); iterator.hasNext(); httpMethod.setHeader((String)entry.getKey(), (String)entry.getValue()))
                entry = (java.util.Map.Entry)iterator.next();

        }
    }

    public static void packageParam(Map params, HttpEntityEnclosingRequestBase httpMethod)
        throws UnsupportedEncodingException
    {
        if(params != null)
        {
            List nvps = new ArrayList();
            Set entrySet = params.entrySet();
            java.util.Map.Entry entry;
            for(Iterator iterator = entrySet.iterator(); iterator.hasNext(); nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue())))
                entry = (java.util.Map.Entry)iterator.next();

            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }
    }

    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod)
        throws Exception
    {
        httpResponse = httpClient.execute(httpMethod);
        if(httpResponse != null && httpResponse.getStatusLine() != null)
        {
            String content = "";
            if(httpResponse.getEntity() != null)
                content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        } else
        {
            return new HttpClientResult(500);
        }
    }

    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient)
        throws IOException
    {
        if(httpResponse != null)
            httpResponse.close();
        if(httpClient != null)
            httpClient.close();
    }

    private static final String ENCODING = "UTF-8";
    private static final int CONNECT_TIMEOUT = 6000;
    private static final int SOCKET_TIMEOUT = 6000;
}
