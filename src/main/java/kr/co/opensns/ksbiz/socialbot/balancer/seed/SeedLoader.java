package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.lang.reflect.InvocationTargetException;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 20., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedLoader<T extends Loadable>{

	Class<T> cls;
	
	public SeedLoader(){
		
	}
	
	public SeedQueue LoadSeedQueue(String path,String type) {
		SeedQueue q;
		try {
			
			T loader=cls.getConstructor().newInstance(); 
			q = loader.Load(path, type);
			
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			q=null;
		} finally {
			
		}
		return q;
	}
	
}
