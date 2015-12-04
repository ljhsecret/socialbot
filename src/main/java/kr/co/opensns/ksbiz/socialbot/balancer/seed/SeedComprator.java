package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Comparator;

/**
 * SeedQueue의 우선순위 비교를 위한 SeedEntity간 Comparator
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 28., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 28., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedComprator implements Comparator<SeedEntity> {

	@Override
	public int compare(SeedEntity o1, SeedEntity o2) {
		double o1Pri = o1.getPriority();
		double o2Pri = o2.getPriority();

		if(o1Pri==o2Pri) return 0;
		else if(o1Pri>o2Pri) return 1;
		else return -1;
		
	}
}
