/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.compiler;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;
import static org.eclipse.xtext.util.Strings.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.xtext.common.types.JvmArrayType;
import org.eclipse.xtext.common.types.JvmPrimitiveType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmVoid;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class ImportManager {

	private Map<String,String> imports = newHashMap();

	private boolean organizeImports;

	private String seedSimpleName;
	
	public ImportManager(boolean organizeImports) {
		this(organizeImports, null);
	}
	
	public ImportManager(boolean organizeImports, String seedSimpleName) {
		this.organizeImports = organizeImports;
		this.seedSimpleName = seedSimpleName;
	}
	
	public CharSequence serialize(JvmType type) {
		StringBuilder sb = new StringBuilder();
		appendType(type, sb);
		return sb;
	}
	
	private Pattern JAVA_LANG_PACK = Pattern.compile("java\\.lang\\.[\\w]+");

	public void appendType(final JvmType type, StringBuilder builder) {
		if (type instanceof JvmPrimitiveType || type instanceof JvmVoid || type instanceof JvmTypeParameter) {
			builder.append(type.getQualifiedName('.'));
		} else if (type instanceof JvmArrayType) {
			appendType(((JvmArrayType) type).getComponentType(), builder);
			builder.append("[]");
		} else {
			final String qn = type.getQualifiedName('.');
			final String simpleName = type.getSimpleName();
			if (JAVA_LANG_PACK.matcher(qn).matches() || equal(qn,simpleName)) {
				builder.append(simpleName);
			} else if (!organizeImports || simpleName.equals(seedSimpleName)) {
				builder.append(qn);
			} else {
				if (imports.containsKey(simpleName)) {
					if (qn.equals(imports.get(simpleName))) {
						builder.append(simpleName);
					} else {
						builder.append(qn);
					}
				} else {
					imports.put(simpleName, qn);
					builder.append(simpleName);
				}
			}
		}
	}

	public List<String> getImports() {
		ArrayList<String> result = newArrayList(imports.values());
		Collections.sort(result);
		return result;
	}
}
