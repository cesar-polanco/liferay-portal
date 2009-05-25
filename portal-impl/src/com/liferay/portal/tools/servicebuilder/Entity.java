/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.TextFormatter;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="Entity.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Entity {

	public static final String DEFAULT_DATA_SOURCE = "liferayDataSource";

	public static final String DEFAULT_SESSION_FACTORY =
		"liferaySessionFactory";

	public static final String DEFAULT_TX_MANAGER = "liferayTransactionManager";

	public static EntityColumn getColumn(
		String name, List<EntityColumn> columnList) {

		int pos = columnList.indexOf(new EntityColumn(name));

		if (pos != -1) {
			return columnList.get(pos);
		}
		else {
			throw new RuntimeException("Column " + name + " not found");
		}
	}

	public static boolean hasColumn(
		String name, List<EntityColumn> columnList) {

		int pos = columnList.indexOf(new EntityColumn(name));

		if (pos != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	public Entity(String name) {
		this(
			null, null, null, name, null, null, false, false, true, null, null,
			null, null, null, true, null, null, null, null, null, null, null,
			null);
	}

	public Entity(
		String packagePath, String portletName, String portletShortName,
		String name, String table, String alias, boolean uuid,
		boolean localService, boolean remoteService, String persistenceClass,
		String finderClass, String dataSource, String sessionFactory,
		String txManager, boolean cacheEnabled, List<EntityColumn> pkList,
		List<EntityColumn> regularColList, List<EntityColumn> collectionList,
		List<EntityColumn> columnList, EntityOrder order,
		List<EntityFinder> finderList, List<Entity> referenceList,
		List<String> txRequiredList) {

		_packagePath = packagePath;
		_portletName = portletName;
		_portletShortName = portletShortName;
		_name = name;
		_table = table;
		_alias = alias;
		_uuid = uuid;
		_localService = localService;
		_remoteService = remoteService;
		_persistenceClass = persistenceClass;
		_finderClass = finderClass;
		_dataSource = GetterUtil.getString(dataSource, DEFAULT_DATA_SOURCE);
		_sessionFactory = GetterUtil.getString(
			sessionFactory, DEFAULT_SESSION_FACTORY);
		_txManager = GetterUtil.getString(txManager, DEFAULT_TX_MANAGER);
		_cacheEnabled = cacheEnabled;
		_pkList = pkList;
		_regularColList = regularColList;
		_collectionList = collectionList;
		_columnList = columnList;
		_order = order;
		_finderList = finderList;
		_referenceList = referenceList;
		_txRequiredList = txRequiredList;
	}

	public boolean equals(Object obj) {
		Entity entity = (Entity)obj;

		String name = entity.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getAlias() {
		return _alias;
	}

	public List<EntityFinder> getCollectionFinderList() {
		List<EntityFinder> finderList = ListUtil.copy(_finderList);

		Iterator<EntityFinder> itr = finderList.iterator();

		while (itr.hasNext()) {
			EntityFinder finder = itr.next();

			if (!finder.isCollection()) {
				itr.remove();
			}
		}

		return finderList;
	}

	public List<EntityColumn> getCollectionList() {
		return _collectionList;
	}

	public EntityColumn getColumn(String name) {
		return getColumn(name, _columnList);
	}

	public EntityColumn getColumnByMappingTable(String mappingTable) {
		for (int i = 0; i < _columnList.size(); i++) {
			EntityColumn col = _columnList.get(i);

			if (col.getMappingTable() != null &&
				col.getMappingTable().equals(mappingTable)) {

				return col;
			}
		}

		return null;
	}

	public List<EntityColumn> getColumnList() {
		return _columnList;
	}

	public String getDataSource() {
		return _dataSource;
	}

	public String getFinderClass() {
		return _finderClass;
	}

	public List<EntityFinder> getFinderList() {
		return _finderList;
	}

	public String getName() {
		return _name;
	}

	public String getNames() {
		return TextFormatter.formatPlural(new String(_name));
	}

	public EntityOrder getOrder() {
		return _order;
	}

	public String getPackagePath() {
		return _packagePath;
	}

	public String getPersistenceClass() {
		return _persistenceClass;
	}

	public String getPKClassName() {
		if (hasCompoundPK()) {
			return _name + "PK";
		}
		else {
			EntityColumn col = _pkList.get(0);

			return col.getType();
		}
	}

	public List<EntityColumn> getPKList() {
		return _pkList;
	}

	public String getPKVarName() {
		if (hasCompoundPK()) {
			return getVarName() + "PK";
		}
		else {
			EntityColumn col = _pkList.get(0);

			return col.getName();
		}
	}

	public String getPortletName() {
		return _portletName;
	}

	public String getPortletShortName() {
		return _portletShortName;
	}

	public List<Entity> getReferenceList() {
		return _referenceList;
	}

	public List<EntityColumn> getRegularColList() {
		return _regularColList;
	}

	public String getSessionFactory() {
		return _sessionFactory;
	}

	public String getShortName() {
		if (_name.startsWith(_portletShortName)) {
			return _name.substring(_portletShortName.length());
		}
		else {
			return _name;
		}
	}

	public String getSpringPropertyName() {
		return TextFormatter.format(_name, TextFormatter.L);
	}

	public String getTable() {
		return _table;
	}

	public String getTXManager() {
		return _txManager;
	}

	public List<String> getTxRequiredList() {
		return _txRequiredList;
	}

	public List<EntityFinder> getUniqueFinderList() {
		List<EntityFinder> finderList = ListUtil.copy(_finderList);

		Iterator<EntityFinder> itr = finderList.iterator();

		while (itr.hasNext()) {
			EntityFinder finder = itr.next();

			if (finder.isCollection()) {
				itr.remove();
			}
		}

		return finderList;
	}

	public String getVarName() {
		return TextFormatter.format(_name, TextFormatter.I);
	}

	public String getVarNames() {
		return TextFormatter.formatPlural(new String(getVarName()));
	}

	public boolean hasColumn(String name) {
		return hasColumn(name, _columnList);
	}

	public boolean hasColumns() {
		if ((_columnList == null) || (_columnList.size() == 0)) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean hasCompoundPK() {
		if (_pkList.size() > 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasFinderClass() {
		if (Validator.isNull(_finderClass)) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean hasLocalService() {
		return _localService;
	}

	public boolean hasPrimitivePK() {
		if (hasCompoundPK()) {
			return false;
		}
		else {
			EntityColumn col = _pkList.get(0);

			if (col.isPrimitiveType()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean hasRemoteService() {
		return _remoteService;
	}

	public boolean hasUuid() {
		return _uuid;
	}

	public boolean isCacheEnabled() {
		return _cacheEnabled;
	}

	public boolean isDefaultDataSource() {
		if (_dataSource.equals(DEFAULT_DATA_SOURCE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDefaultSessionFactory() {
		if (_sessionFactory.equals(DEFAULT_SESSION_FACTORY)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDefaultTXManager() {
		if (_txManager.equals(DEFAULT_TX_MANAGER)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isHierarchicalTree() {
		if (!hasPrimitivePK()) {
			return false;
		}

		EntityColumn col = _pkList.get(0);

		if ((_columnList.indexOf(
				new EntityColumn("parent" + col.getMethodName())) != -1) &&
			(_columnList.indexOf(
				new EntityColumn("left" + col.getMethodName())) != -1) &&
			(_columnList.indexOf(
				new EntityColumn("right" + col.getMethodName())) != -1)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isOrdered() {
		if (_order != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPortalReference() {
		return _portalReference;
	}

	public void setPortalReference(boolean portalReference) {
		_portalReference = portalReference;
	}

	private String _alias;
	private boolean _cacheEnabled;
	private List<EntityColumn> _collectionList;
	private List<EntityColumn> _columnList;
	private String _dataSource;
	private String _finderClass;
	private List<EntityFinder> _finderList;
	private boolean _localService;
	private String _name;
	private EntityOrder _order;
	private String _packagePath;
	private String _persistenceClass;
	private List<EntityColumn> _pkList;
	private boolean _portalReference;
	private String _portletName;
	private String _portletShortName;
	private List<Entity> _referenceList;
	private List<EntityColumn> _regularColList;
	private boolean _remoteService;
	private String _sessionFactory;
	private String _table;
	private String _txManager;
	private List<String> _txRequiredList;
	private boolean _uuid;

}