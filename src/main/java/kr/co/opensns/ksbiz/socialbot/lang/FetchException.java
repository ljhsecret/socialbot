package kr.co.opensns.ksbiz.socialbot.lang;

/**
 * Exception for Fetch
 * @author jaeho
 *
 */
public class FetchException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FetchException(String msg) {
		super(msg);
	}
	
	public FetchException() {
		super("FetchException");
	}
}
