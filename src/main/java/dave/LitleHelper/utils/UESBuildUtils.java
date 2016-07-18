package dave.LitleHelper.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class UESBuildUtils {

	public static Map<String, List<String>> getVersions(UESBuildType type) {
		URL web;
		Map<String, List<String>> resultMap = new TreeMap<>();

		try {
			web = new URL(type.getUrl());

			String data = IOUtils.toString(web.openStream(), Charset.defaultCharset());
			Pattern pattern = Pattern.compile(">(\\d*?)-(\\d*?)\\/<");
			Matcher matcher = pattern.matcher(data);

			List<String> versions = new ArrayList<>();
			while (matcher.find()) {
				versions.add(matcher.group(1) + "-" + matcher.group(2));
			}

			for (String version : versions) {
				String url = type.getUrl();
				if (UESBuildType.R.equals(type)) {
					url += version + "/";
				} else if (UESBuildType.M.equals(type)) {
					url += version + "/ues/";
				}

				resultMap.put(version, getUESBuilds(url));
			}

			return resultMap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static List<String> getUESBuilds(String url) throws IOException {
		URL web = new URL(url);

		String data = IOUtils.toString(web.openStream(), Charset.defaultCharset());

		Pattern pattern = Pattern.compile("zip\">(.*?)<");
		Matcher matcher = pattern.matcher(data);

		List<String> resultList = new ArrayList<>();
		while (matcher.find()) {
			resultList.add(matcher.group(1));
		}

		return resultList.stream().filter(item -> !(item.contains("pbe") || item.contains("sdk")))
				.collect(Collectors.toList());
	}

	public enum UESBuildType {
		R("http://192.168.4.212/UES_BUILDS/R/"),
		M("http://192.168.4.212/UES_BUILDS/M/");

		private String url;

		private UESBuildType(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}

}
