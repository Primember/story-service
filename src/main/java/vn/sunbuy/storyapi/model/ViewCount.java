package vn.sunbuy.storyapi.model;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ViewCount implements Comparator<ViewCount> {
	
	private Long total;
	private String storyId;
	@Override
	public int compare(ViewCount o1, ViewCount o2) {
		return o1.getTotal().compareTo(o2.getTotal());
	}

}
