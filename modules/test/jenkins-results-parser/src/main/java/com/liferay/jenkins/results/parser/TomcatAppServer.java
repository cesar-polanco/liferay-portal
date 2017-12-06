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

public class TomcatAppServer implements AppServer {

  @Override
  public void configure(String liferayVersion) {

    switch (liferayVersion) {

      case "6.0.6":
        appServerTomcatVersion = "6.0.29";
        break;

      case "6.0.12":
        appServerTomcatVersion = "6.0.32";
        break;

      case "6.1.20":
        appServerTomcatVersion = "7.0.27";
        break;

      case "6.1.2":
      case "6.1.30":
        appServerTomcatVersion = "7.0.40";
        break;

      case "6.2.0":
      case "6.2.1":
      case "6.2.2":
      case "6.2.3":
      case "6.2.10.1":
      case "6.2.10.2":
      case "6.2.10.3":
      case "6.2.10.4":
      case "6.2.10.5":
      case "6.2.10.6":
      case "6.2.10.7":
      case "6.2.10.8":
      case "6.2.10.9":
      case "6.2.10.10":
      case "6.2.10.11":
      case "6.2.10.12":
      case "6.2.10.13":
        appServerTomcatVersion = "7.0.42";
        break;

      case "6.2.4":
      case "6.2.5":
      case "6.2.10.14":
      case "6.2.10.15":
        appServerTomcatVersion = "7.0.62";
        break;

      default:
        System.out.println("Invalid Portal version.");
    }

    appServerTomcatDirectory = "./bundles/tomcat-" + appServerTomcatVersion;
    appServerTomcatBinDirectory = appServerTomcatDirectory + "/bin";
    appServerTomcatLibGlobalDirectory = appServerTomcatDirectory + "/lib/ext";
    appServerTomcatZipName = "apache-tomcat-" + appServerTomcatVersion + ".zip";
    appServerTomcatZipURL = "http://archive.apache.org/dist/tomcat/tomcat-8/v" +
      appServerTomcatVersion + "/bin/" + appServerTomcatZipName;

  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  public TomcatAppServer() {}

  private String appServerTomcatVersion;
  private String appServerTomcatDirectory;
  private String appServerTomcatBinDirectory;
  private String appServerTomcatLibGlobalDirectory;
  private String appServerTomcatZipName;
  private String appServerTomcatZipURL;

}
