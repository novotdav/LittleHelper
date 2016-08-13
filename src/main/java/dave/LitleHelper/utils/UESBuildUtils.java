package dave.LitleHelper.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.exception.ValidationException;
import dave.LitleHelper.exception.ValidationException.ValErr;

public class UESBuildUtils {

	private static Settings settings = Settings.getInstance();

	public static Map<String, List<String>> getVersions(UESBuildType type) {
		URL web;
		Map<String, List<String>> resultMap = new TreeMap<>();

		try {
			web = new URL(type.getUrl());
			URLConnection connection = web.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(5000);

			String data = IOUtils.toString(connection.getInputStream(), Charset.defaultCharset());
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
			throw new LittleException(Err.WRONG_URL, e);
		} catch (SocketTimeoutException e) {
			throw new LittleException(Err.CONN_TIME_OUT, e);
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

	public static File getWorkspaceLocation(WorkspaceType type) {
		String workspacePath = null;

		switch (type) {
			case LEGACY:
				workspacePath = settings.getValue(PropertyValues.WRK_LEGACY_PATH);
				break;
			case CMD:
				workspacePath = settings.getValue(PropertyValues.WRK_CMD_PATH);
				break;
			case OTHER:
				workspacePath = settings.getValue(PropertyValues.WRK_OTHER_PATH);
				break;
		}

		if (workspacePath == null || workspacePath.equals("")) {
			throw new ValidationException(ValErr.WRONG_PATH);
		}

		return new File(workspacePath);
	}

	public static URL getBuildUrl(String type, String mainVersion, String subVersion) {
		UESBuildType buildType = UESBuildType.valueOf(type);

		StringBuilder sb = new StringBuilder(buildType.getUrl()).append(mainVersion).append("/");

		if (UESBuildType.M.equals(buildType)) {
			sb.append("ues/");
		}

		sb.append(subVersion);

		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			throw new LittleException(Err.WRONG_URL, e);
		}
	}

}
