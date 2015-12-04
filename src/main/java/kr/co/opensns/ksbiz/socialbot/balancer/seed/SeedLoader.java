package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.lang.reflect.InvocationTargetException;

/**
 * 클래스 설명
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 20., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedLoader<L extends Loadable> {

	Class<L> cls;

	public SeedLoader(Class<L> cls) {
		this.cls = cls;
		
		// ParameterizedType genericSuperClass =
		// (ParameterizedType)getClass().getGenericSuperclass();
		// Type type = genericSuperClass.getActualTypeArguments()[0];
		//
		// if(type instanceof ParameterizedType){
		// this.cls = (Class)((ParameterizedType)type).getRawType();
		// } else {
		// this.cls = (Class)type;
		// }
	}

	public SeedQueue LoadSeedQueue(String path, String type)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		SeedQueue q = null;
		L loader = cls.getConstructor().newInstance();
		q = loader.Load(path, type);
		cls.getName();
		return q;
	}

}
