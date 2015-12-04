package kr.co.opensns.ksbiz.socialbot.balancer.exception;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 28., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 28., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class BalancerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BalancerException(String msg){
		super(msg);
	}
}
