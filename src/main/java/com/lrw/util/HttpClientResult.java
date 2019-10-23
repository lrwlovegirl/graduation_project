package com.lrw.util;


// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   HttpClientResult.java



public class HttpClientResult
{

    public HttpClientResult()
    {
    }

    public HttpClientResult(int code)
    {
        this.code = code;
    }

    public HttpClientResult(String content)
    {
        this.content = content;
    }

    public HttpClientResult(int code, String content)
    {
        this.code = code;
        this.content = content;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String toString()
    {
        return (new StringBuilder()).append("HttpClientResult [code=").append(code).append(", content=").append(content).append("]").toString();
    }

    private int code;
    private String content;
}
