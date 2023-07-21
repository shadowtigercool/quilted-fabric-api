/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2023 The Quilt Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.registry.sync;

import org.quiltmc.qsl.registry.impl.dynamic.DynamicMetaRegistryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.impl.registry.sync.DynamicRegistriesImpl;

// Adds namespaces to tag directories for registries added by mods.
@Mixin(TagManagerLoader.class)
abstract class TagManagerLoaderMixin {
	@Inject(method = "getPath", at = @At("HEAD"), cancellable = true)
	private static void onGetPath(RegistryKey<? extends Registry<?>> registryKey, CallbackInfoReturnable<String> info) {
		Identifier id = registryKey.getValue();

		if (DynamicMetaRegistryImpl.isModdedRegistryId(id) && DynamicRegistriesImpl.DYNAMIC_REGISTRY_KEYS.contains(registryKey)) {
			info.setReturnValue("tags/" + id.getNamespace() + "/" + id.getPath());
		}
	}
}
