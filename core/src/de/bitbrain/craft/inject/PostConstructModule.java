/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bitbrain.craft.inject;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public enum PostConstructModule implements Module, TypeListener {
	 
    INSTANCE;
 
    /**
     * {@inheritDoc}
     *
     * @see com.google.inject.Module#configure(com.google.inject.Binder)
     */
    @Override
    public void configure(final Binder binder) {
        binder.bindListener(Matchers.any(), this);
    }
 
    /**
     * Ruft nach der Injection die Postconstruct Methode(n) auf, wenn sie existieren.
     *
     * <p>
     * {@inheritDoc}
     *
     * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
     */
    @Override
    public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
        encounter.register(new InjectionListener<I>() {
 
            @Override
            public void afterInjection(final I injectee) {
                // alle postconstruct Methoden (nie null) ausführen.
                for (final Method postConstructMethod : injectee.getClass().getMethods()) {
                    try {
                    	if (postConstructMethod.getAnnotation(PostConstruct.class) != null) {
                    		postConstructMethod.invoke(injectee);
                    	}
                    } catch (final Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    } 
}