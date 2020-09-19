/*
 * Copyright 2020 Google LLC
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.devtools.ksp.processor

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

class CheckOverrideProcessor : AbstractTestProcessor() {
    val results = mutableListOf<String>()

    override fun toResult(): List<String> {
        return results
    }

    override fun process(resolver: Resolver) {
        val javaList = resolver.getClassDeclarationByName(resolver.getKSNameFromString("JavaList")) as KSClassDeclaration
        val kotlinList = resolver.getClassDeclarationByName(resolver.getKSNameFromString("KotlinList")) as KSClassDeclaration
        val getFunKt = resolver.getSymbolsWithAnnotation("GetAnno").single() as KSFunctionDeclaration
        val getFunJava = javaList.getAllFunctions().single { it.simpleName.asString() == "get" }
        val fooFunJava = javaList.getDeclaredFunctions().single { it.simpleName.asString() == "foo" }
        val fooFunKt = resolver.getSymbolsWithAnnotation("FooAnno").single() as KSFunctionDeclaration
        val foooFunKt = resolver.getSymbolsWithAnnotation("BarAnno").single() as KSFunctionDeclaration
        val equalFunKt = kotlinList.getDeclaredFunctions().single { it.simpleName.asString() == "equals" }
        val equalFunJava = javaList.getAllFunctions().single { it.simpleName.asString() == "equals" }
        val bazPropKt = resolver.getSymbolsWithAnnotation("BazAnno").single() as KSPropertyDeclaration
        val baz2PropKt = resolver.getSymbolsWithAnnotation("Baz2Anno").single() as KSPropertyDeclaration
        val bazzPropKt = resolver.getSymbolsWithAnnotation("BazzAnno").single() as KSPropertyDeclaration
        val bazz2PropKt = resolver.getSymbolsWithAnnotation("Bazz2Anno").single() as KSPropertyDeclaration
        results.add("${getFunKt.qualifiedName?.asString()} overrides ${getFunJava.qualifiedName?.asString()}: ${getFunKt.overrides(getFunJava)}")
        results.add("${fooFunKt.qualifiedName?.asString()} overrides ${fooFunJava.qualifiedName?.asString()}: ${fooFunKt.overrides(fooFunJava)}")
        results.add("${foooFunKt.qualifiedName?.asString()} overrides ${fooFunJava.qualifiedName?.asString()}: ${foooFunKt.overrides(fooFunJava)}")
        results.add("${equalFunKt.qualifiedName?.asString()} overrides ${equalFunJava.qualifiedName?.asString()}: ${equalFunKt.overrides(equalFunJava)}")
        results.add("${bazPropKt.qualifiedName?.asString()} overrides ${baz2PropKt.qualifiedName?.asString()}: ${bazPropKt.overrides(baz2PropKt)}")
        results.add("${bazPropKt.qualifiedName?.asString()} overrides ${bazz2PropKt.qualifiedName?.asString()}: ${bazPropKt.overrides(bazz2PropKt)}")
        results.add("${bazzPropKt.qualifiedName?.asString()} overrides ${bazz2PropKt.qualifiedName?.asString()}: ${bazzPropKt.overrides(bazz2PropKt)}")
        results.add("${bazzPropKt.qualifiedName?.asString()} overrides ${baz2PropKt.qualifiedName?.asString()}: ${bazzPropKt.overrides(baz2PropKt)}")
    }
}