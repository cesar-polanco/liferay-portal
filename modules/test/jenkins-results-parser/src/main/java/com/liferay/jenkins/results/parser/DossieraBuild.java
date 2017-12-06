/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import java.io.File;

package com.liferay.jenkins.results.parser;

/**
 * @author Cesar Polanco
 */

public class DossieraBuild extends AxisBuild {

	@Override
	protected void runBuild() {
		//Database-test-run-test
		// prepare-log4j-ext-xml

		createTestHostPropertiesFile();
		createJDBCProperties();
		createExtProperties();
		self._db.start();
		self._db.rebuild(getSqlFileLocation());

	}



	@Override
	protected void setupBuild() {
		self._appServer.start()
	}


	protected DossieraBuild() {
		_appServer = "tomcat";
		_database = "mysql56";
		_operatingSystem = "centos6";

	}

	protected DossieraBuild() {

	}

/*
	@Override
	protected void teardownBuild() {

	}
*/

	private void createExtProperties() {
		String filename = "portal-impl/test/system-ext.properties";

		StringBuilder props = new StringBuilder("log.sanitizer.enabled=false");

		JenkinsResultsParserUtil.appendToFile(filename, props.toString());
	}

	private void createJDBCProperties() {
		String filename = "portal-impl/test/portal-test-ext.properties";

		StringBuilder props = new StringBuilder("liferay.home=");
		props.append(self.getLiferayHome());
		props.append("\n\n");
		props.append("jdbc.default.driverClassName=");
		props.append(self.getDatabaseDriver());
		props.append("\n");
		props.append("jdbc.default.url=");
		props.append(self.getDatabaseUrl());
		props.append("\n");
		props.append("jdbc.default.username=");
		props.append(self.getDatabaseUsername());
		props.append("\n");
		props.append("jdbc.default.password=");
		props.append(self.getDatabasePassword());
		props.append("\n\n");
		props.append("// HikariCP\n\n");
		props.append("jdbc.default.connectionTimeout=600000\n");
		props.append("jdbc.default.maximumPoolSize=20\n");
		props.append("jdbc.default.minimumIdle=0\n\n");
		props.append("jdbc.counter.maximumPoolSize=5\n");
		props.append("jdbc.counter.minimumIdle=0\n\n");
		props.append("// Tomcat\n\n");
		props.append("jdbc.default.intialSize=0\n");
		props.append("jdbc.default.maxActive=20\n");
		props.append("jdbc.default.maxIdle=0\n");
		props.append("jdbc.default.minIdle=0\n\n");
		props.append("jdbc.counter.intialSize=0\n");
		props.append("jdbc.counter.maxActive=5\n");
		props.append("jdbc.counter.maxIdle=0\n");
		props.append("jdbc.counter.minIdle=0\n\n");
		props.append("counter.jdbc.prefix=jdbc.counter.\n");
		props.append("module.framework.base.dir=" + self.getLiferayHome() + "/osgi");
		props.append("\n\n");
		props.append("sprite.root.dir=/tmp/sprite");
		props.append("\n\n");
		props.append("memory.scheduler.org.quartz.threadPool.threadCount=1");
		props.append("\n\n");
		props.append("persisted.scheduler.org.quartz.threadPool.threadCount=1");

		JenkinsResultsParserUtil.appendToFile(filename, props.toString());
	}

	private void createTestHostPropertiesFile() {
		String hostname = JenkinsResultsParserUtil.getHostName("local");

		String filename = "test." + hostname + ".properties";

		JenkinsResultsParserUtil.appendToFile(filename, self._db.getProperties());

	}

	private void getSqlFileLocation() {

	}

	private void prepareLog4J() {
		Build parentBuild = getParentBuild();

		String appServerHome = parentBuild.getAppServerHomeDirectory();

	}

	private static OperatingSystem getOperatingSystem() {
		Build parentBuild = getParentBuild();
		String ossstring = parentBuild.getOperatingSystem();

		if (osstring.toLowerCase().indexOf("win") >= 0){
			verString = osstring.substring(osstring.indexOf(" ") + 1);
			osstring = osstring.substring(0, osstring.indexOf(" "));
		}

		switch (osstring.toLowerCase()){
			case "linux":
			case "freebsd":
			case "unix":
			case "mpe/ix":
			case "irix":
			case "digital unix":
				return new LinuxOS();
				break;
			case "mac os x":
				return new MacOSX();
				break;
			case "solaris":
			case "sunos":
			case "sun os":
				return new SunOS();
				break;
			case "windows":
				return new WindowsOS(verString);
				break;
			default:
				throw new RuntimeException("Invalid OS");
		}
	}

	private static Database getDatabaseType() {
		Build parentBuild = getParentBuild();
		String dbstring = parentBuild.getDatabase();

		switch (dbstring.toLowerCase()){
			case "db2":
				return new DB2Database();
				break;
			case "hypersonic":
				return new Hypersonic();
				break;
			case "mariadb":
				return new MariaDB();
				break;
			case "mysql":
				return new MySQL(self.getDatabaseVersion);
				break;
			case "oracle":
				return new Oracle();
				break;
			case "postgresql":
				return new Postgresql();
				break;
			case "sqlserver":
				return new SQLServer();
				break;
			case "sybase":
				return new Sybase();
				break;
			default:
				throw new RuntimeException("Invalid Database");
		}
	}

	private static final OperatingSystem _os =
		self.getOperatingSystem();
	private static final Database _db =
		self.getDatabaseType();

}