/* Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.inconsequential.mapping;

import org.grails.inconsequential.core.EntityCreationException;
import org.grails.inconsequential.mapping.lifecycle.Initializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract implementation to be subclasses on a per datastore basis
 *
 * @author Graeme Rocher
 * @since 1.0
 */
public abstract class AbstractPersistentEntity<T> implements PersistentEntity, Initializable{
    protected Class javaClass;
    protected List<PersistentProperty> persistentProperties;
    protected Map<String, PersistentProperty> propertiesByName = new HashMap<String,PersistentProperty>();
    protected MappingContext context;
    protected PersistentProperty identity;
    protected List<String> persistentPropertyNames;

    public AbstractPersistentEntity(Class javaClass, MappingContext context) {
        if(javaClass == null) throw new IllegalArgumentException("The argument [javaClass] cannot be null");
        this.javaClass = javaClass;
        this.context = context;
    }

    public void initialize() {
        this.identity = context.getMappingSyntaxStrategy().getIdentity(javaClass, context);
        this.persistentProperties = context.getMappingSyntaxStrategy().getPersistentProperties(javaClass, context);
        persistentPropertyNames = new ArrayList<String>();
        for (PersistentProperty persistentProperty : persistentProperties) {
            persistentPropertyNames.add(persistentProperty.getName());
        }
        for (PersistentProperty persistentProperty : persistentProperties) {
            propertiesByName.put(persistentProperty.getName(), persistentProperty);
        }
    }

    public List<String> getPersistentPropertyNames() {
        return this.persistentPropertyNames;
    }

    public ClassMapping<T> getMapping() {
        return new AbstractClassMapping<T>(this, context) {
            public T getMappedForm() {
                return null;
            }
        };
    }

    public Object newInstance() {
        try {
            return getJavaClass().newInstance();
        } catch (InstantiationException e) {
            throw new EntityCreationException("Unable to create entity of type ["+getJavaClass()+"]: " + e.getMessage(),e);
        } catch (IllegalAccessException e) {
            throw new EntityCreationException("Unable to create entity of type ["+getJavaClass()+"]: " + e.getMessage(),e);
        }
    }

    public String getName() {
        return javaClass.getName();
    }

    public PersistentProperty getIdentity() {
        return this.identity;
    }

    public Class getJavaClass() {
        return this.javaClass;
    }

    public boolean isInstance(Object obj) {
        return getJavaClass().isInstance(obj);
    }

    public List<PersistentProperty> getPersistentProperties() {
        return persistentProperties;
    }

    public PersistentProperty getPropertyByName(String name) {
        return propertiesByName.get(name);
    }

    @Override
    public int hashCode() {
        return javaClass.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof PersistentEntity)) return false;
        if(this == o) return true;

        PersistentEntity other = (PersistentEntity) o;
        return javaClass.equals(other.getJavaClass());
    }
}