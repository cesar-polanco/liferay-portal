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

package com.liferay.portlet.shopping.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.shopping.model.ShoppingItem;

/**
 * <a href="ItemMinQuantityComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ItemMinQuantityComparator extends OrderByComparator {

	public static String ORDER_BY_ASC =
		"categoryId ASC, minQuantity ASC, name ASC";

	public static String ORDER_BY_DESC =
		"categoryId DESC, minQuantity DESC, name DESC";

	public static String[] ORDER_BY_FIELDS = {
		"categoryId", "minQuantity", "name"
	};

	public ItemMinQuantityComparator() {
		this(false);
	}

	public ItemMinQuantityComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		ShoppingItem item1 = (ShoppingItem)obj1;
		ShoppingItem item2 = (ShoppingItem)obj2;

		Long categoryId1 = new Long(item1.getCategoryId());
		Long categoryId2 = new Long(item2.getCategoryId());

		int value = categoryId1.compareTo(categoryId2);

		if (value == 0) {
			if (item1.getMinQuantity() < item2.getMinQuantity()) {
				value = -1;
			}
			else if (item1.getMinQuantity() > item2.getMinQuantity()) {
				value = 1;
			}
		}

		if (value == 0) {
			value = item1.getName().toLowerCase().compareTo(
				item2.getName().toLowerCase());
		}

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_asc) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _asc;
	}

	private boolean _asc;

}