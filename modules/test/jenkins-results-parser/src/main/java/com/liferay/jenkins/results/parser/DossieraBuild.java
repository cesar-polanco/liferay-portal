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

package com.liferay.jenkins.results.parser;

/**
 * @author Cesar Polanco
 */

public class DossieraBuild extends AxisBuild {

	@Override
	protected void runBuild() {
		//Database-test-run-test
		// prepare-log4j-ext-xml

		createTestHostPropsFile();

		prepareLog4J();
		//

	}

/*	@Override
	protected void setupBuild() {

	}

	@Override
	protected void teardownBuild() {

	}
*/

	private void createTestHostPropsFile() {

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
				return new MySQL();
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

	private static final OperatingSystem os =
		self.getOperatingSystem();
	private static final Database db =
		self.getDatabaseType();

}