/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 The Quilt Project
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

package net.fabricmc.fabric.api.rendering.data.v1;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockRenderView;

/**
 * Interface for {@link BlockEntity}s which provide dynamic model state data.
 *
 * <p>Dynamic model state data is separate from BlockState, and will be
 * cached during render chunk building on the main thread (safely) and accessible
 * during chunk rendering on non-main threads.
 *
 * <p>To access the dynamic data, cast the {@link BlockRenderView} to {@link RenderAttachedBlockView},
 * and then call {@link #getRenderAttachmentData()} with the correct position.
 *
 * <p>Due to chunk meshing happening on non-main threads, please ensure that all accesses to the passed model data are
 * thread-safe. This can be achieved by, for example, passing a pre-generated
 * immutable object, or ensuring all gets performed on the passed object are atomic
 * and well-checked for unusual states.
 */
@FunctionalInterface
public interface RenderAttachmentBlockEntity {
	/**
	 * @return The model state data provided by this block entity. Can be null.
	 */
	@Nullable
	Object getRenderAttachmentData();
}
