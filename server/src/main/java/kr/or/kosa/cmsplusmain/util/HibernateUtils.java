package kr.or.kosa.cmsplusmain.util;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class HibernateUtils {

	/**
	 * 상속관계 엔티티 구현체를 구한다.
	 * */
	public static <T> T getImplements(Object proxy, Class<T> clazz) {
		if (!(proxy instanceof HibernateProxy) && clazz.isInstance(proxy)) {
			return (T) proxy;
		}
		LazyInitializer lazyInitializer = HibernateProxy.extractLazyInitializer(proxy);
		if (lazyInitializer == null) {
			return null;
		}

		Object object = lazyInitializer.getImplementation();
		if (!clazz.isInstance(object)) {
			throw new IllegalStateException("타입이 일치하지 않습니다,");
		}

		return (T) object;
	}
}
