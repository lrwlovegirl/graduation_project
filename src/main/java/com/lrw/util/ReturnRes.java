package com.lrw.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReturnRes implements Serializable {
  private String msg;
  private String url;
  private boolean isSuccess;
  private Object data;
}
