package com.lrw.util;

import java.io.IOException;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageClient1 storageClient = null;
	private StorageServer storageServer = null;

	public FastDFSClient(String conf) {
		try {
			if (conf.contains("classpath:")) {
				conf=conf.replaceAll("classpath:", this.getClass().getResource("/").getPath());
			}
			ClientGlobal.init(conf);
			trackerClient = new TrackerClient();
			trackerServer=trackerClient.getConnection();
			storageClient=new StorageClient1(trackerServer,storageServer);
		} catch (IOException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String uploadFile(String fileName,String extName,NameValuePair[] metas) {
		try {
			return storageClient.upload_file1(fileName, extName, metas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String uploadFile(String fileName,String extName) {
		return this.uploadFile(fileName, extName, null);
	}
	public String uploadFile(byte[] content,String extName,NameValuePair[] metas) {
		try {
			return storageClient.upload_file1(content, extName, metas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String uploadFile(byte[] content,String extName) {
		return this.uploadFile(content, extName, null);
	}

}
