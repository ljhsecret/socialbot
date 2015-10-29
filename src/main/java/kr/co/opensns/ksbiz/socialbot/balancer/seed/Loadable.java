package kr.co.opensns.ksbiz.socialbot.balancer.seed;

/**
 * repository별 Seed 데이터 로드를 처리하기 위한 추상클래스
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

public abstract class Loadable {
	abstract SeedQueue Load(String path,String type);
}
