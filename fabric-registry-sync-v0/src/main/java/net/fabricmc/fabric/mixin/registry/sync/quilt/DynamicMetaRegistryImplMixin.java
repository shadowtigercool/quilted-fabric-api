package net.fabricmc.fabric.mixin.registry.sync.quilt;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.impl.registry.sync.DynamicRegistriesImpl;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import org.quiltmc.qsl.registry.impl.dynamic.DynamicMetaRegistryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DynamicMetaRegistryImpl.class)
public class DynamicMetaRegistryImplMixin {
	@Inject(method = "register", at = @At("HEAD"), remap = false)
	private static <E> void quilted_fabric_api$registerInjector(RegistryKey<? extends Registry<E>> ref, Codec<E> entryCodec, CallbackInfo ci){
		DynamicRegistriesImpl.DYNAMIC_REGISTRY_KEYS.add(ref);
	}
}
