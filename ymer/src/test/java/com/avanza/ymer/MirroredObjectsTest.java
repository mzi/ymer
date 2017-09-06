/*
 * Copyright 2015 Avanza Bank AB
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
package com.avanza.ymer;

import com.gigaspaces.annotation.pojo.SpaceRouting;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 
 * @author Elias Lindholm (elilin)
 *
 */
public class MirroredObjectsTest {
	
	@Test(expected = NonMirroredTypeException.class)
	public void getMirroredDocumentThrowsIllegalArgumentExceptionForNonMirroredType() throws Exception {
		MirroredObjects mirroredObjects = new MirroredObjects();
		mirroredObjects.getMirroredObject(FakeMirroredType.class);
	}
	
	@Test
	public void returnsMirroredDocumentForGivenType() throws Exception {
		DocumentPatch[] patches = {};
		MirroredObject<FakeMirroredType> mirroredObject = MirroredObjectDefinition.create(FakeMirroredType.class).documentPatches(patches).buildMirroredDocument(MirroredObjectDefinitionsOverride.noOverride());
		MirroredObjects mirroredObjects = new MirroredObjects(mirroredObject);
		assertSame(mirroredObject, mirroredObjects.getMirroredObject(FakeMirroredType.class));
	}
	
	@Test
	public void returnsSetOfMirroredTypes() throws Exception {
		DocumentPatch[] patches = {};
		MirroredObject<FakeMirroredType> mirroredObject = MirroredObjectDefinition.create(FakeMirroredType.class).documentPatches(patches).buildMirroredDocument(MirroredObjectDefinitionsOverride.noOverride());
		MirroredObjects mirroredObjects = new MirroredObjects(mirroredObject);
		
		Set<Class<?>> expected = new HashSet<>();
		expected.add(FakeMirroredType.class);
		
		assertEquals(expected, mirroredObjects.getMirroredTypes());
	}
	
	@Test
	public void returnsSetOfMirroredTypeNames() throws Exception {
		DocumentPatch[] patches = {};
		MirroredObject<FakeMirroredType> mirroredObject = MirroredObjectDefinition.create(FakeMirroredType.class).documentPatches(patches).buildMirroredDocument(MirroredObjectDefinitionsOverride.noOverride());
		MirroredObjects mirroredObjects = new MirroredObjects(mirroredObject);
		
		Set<String> expected = new HashSet<String>();
		expected.add(FakeMirroredType.class.getName());
		
		assertEquals(expected, mirroredObjects.getMirroredTypeNames());
	}
	
	@Test
	public void returnsAllMirroredDocuments() throws Exception {
		DocumentPatch[] patches = {};
		MirroredObject<FakeMirroredType> mirroredObject = MirroredObjectDefinition.create(FakeMirroredType.class).documentPatches(patches).buildMirroredDocument(MirroredObjectDefinitionsOverride.noOverride());
		MirroredObjects mirroredObjects = new MirroredObjects(mirroredObject);
		
		Collection<MirroredObject<?>> allMirroredDocs = mirroredObjects.getMirroredObjects();
		assertEquals(1, allMirroredDocs.size());
		assertSame(mirroredObject, allMirroredDocs.iterator().next());
	}
	
	@Test
	public void mirroredTypes() throws Exception {
		DocumentPatch[] patches = {};
		MirroredObject<FakeMirroredType> mirroredObject = MirroredObjectDefinition.create(FakeMirroredType.class).documentPatches(patches).buildMirroredDocument(MirroredObjectDefinitionsOverride.noOverride());
		MirroredObjects mirroredObjects = new MirroredObjects(mirroredObject);
		assertTrue(mirroredObjects.isMirroredType(FakeMirroredType.class));
		class NonMirroredType {
			
		}
		assertTrue(mirroredObjects.isMirroredType(FakeMirroredType.class));
		assertFalse(mirroredObjects.isMirroredType(NonMirroredType.class));
	}
	
	static class FakeMirroredType {
		@SpaceRouting
		public Integer getRoutingKey() {
			return null; // Never used
		}
	}

}
