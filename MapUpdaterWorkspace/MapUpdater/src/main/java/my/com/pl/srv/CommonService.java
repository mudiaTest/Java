package my.com.pl.srv;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	public List<String> filterLines(List<String> lines, String pattern)
	{
		return lines.stream().filter(a->a.contains(pattern)).collect(Collectors.toList());		
	}
}
