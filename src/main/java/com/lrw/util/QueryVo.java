package com.lrw.util;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于接收前端发送的页码和查询数据的条数
 * 还有模糊查询的关键字
 */
@Data
public class QueryVo  implements Serializable{
    private int page;
    private int limit;
    private String keyword;
    private int type;
    private String publisher;
}
