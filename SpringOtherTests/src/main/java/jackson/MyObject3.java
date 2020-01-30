package jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyObject3 {
	public String txt;
	public MyObject31 o31 = new MyObject31();
	public List<MyObject32> l32 = new ArrayList<>();
	public Map<Long, MyObject32> m32 = new HashMap<>();
}
