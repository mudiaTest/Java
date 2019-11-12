package my.com.pl.srv.common;

import org.springframework.stereotype.Service;

@Service
public class AssertionSrv {
	static public void Assert(boolean condition, String info) {
		if (!condition) {
			throw new RuntimeException("Assert: " + info);
		}
	}
}
