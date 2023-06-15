package vn.sunbuy.storyapi.exception;

public class StoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public StoryNotFoundException(String id) {
        super("Story id not found : " + id);
    }

}
