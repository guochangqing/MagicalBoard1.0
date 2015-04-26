package six.magicalboard.core.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IUtil {

	static final AtomicInteger rc = new AtomicInteger(0);
	
	public static int getIdentifier(){
		return rc.incrementAndGet();
	}
}
