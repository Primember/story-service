package vn.sunbuy.storyapi.model;

import java.io.Serializable;

public class FilterVO implements Serializable{
	private String id = "";
	private String status = "";
	private String userName = "";
	private String type = "";
	private int page = 1;
	private int size = 10;
	private String categoryId = "";
	private float rate = 0;
	private String fromDate = "";
	private String toDate = "";
	private String userId = "";
	public FilterVO() {
		
	}
	@Override
	public String toString() {
		return "FilterVO [id=" + id + ", status=" + status + ", userName=" + userName + ", type=" + type + ", page="
				+ page + ", size=" + size + ", categoryId=" + categoryId + ", rate=" + rate + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", userId=" + userId + "]";
	}


}
