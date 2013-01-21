/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.typesystem.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.xtext.common.types.JvmConstructor;
import org.eclipse.xtext.xbase.XConstructorCall;
import org.eclipse.xtext.xbase.typesystem.computation.IConstructorLinkingCandidate;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@NonNullByDefault
public class NullConstructorLinkingCandidate extends AbstractNullLinkingCandidate<IConstructorLinkingCandidate> implements IConstructorLinkingCandidate {

	public NullConstructorLinkingCandidate(XConstructorCall constructorCall) {
		super(constructorCall);
	}
	
	public IConstructorLinkingCandidate getPreferredCandidate(IConstructorLinkingCandidate other) {
		return other;
	}

	public XConstructorCall getConstructorCall() {
		return (XConstructorCall) featureOrConstructorCall;
	}

	public JvmConstructor getConstructor() {
		throw new UnsupportedOperationException();
	}

}
