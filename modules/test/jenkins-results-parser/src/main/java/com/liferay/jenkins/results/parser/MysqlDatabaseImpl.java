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
public class MysqlDatabaseImpl implements Database {

	MysqlDatabaseImpl(String version) {
		self._version = version;
	}

	MysqlDatabaseImpl() {
		self._version = "5.6";
	}

	@Override
	public String databaseDump() {
		return "stub";
	}

	@Override
	public String getProperties() {
		StringBuilder sb = "\n";
		sb.append("database.type=" + self._type);
		sb.append("\n");
		sb.append("database." + self._type + ".version=" + self._version);

		return sb.toString();
	}

	@Override
	public void rebuild(String sqlFileLocation) {
		System.out.println("REBUILD Stub.");
	}

	@Override
	public void start() {
		System.out.println("Start Stub");
	}

	@Override
	public void stop() {
		System.out.println("Stop Stub");
	}

	private static final String _type = "mysql";
	private final String _version;
}