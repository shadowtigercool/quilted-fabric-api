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

package net.fabricmc.fabric.test.command;

import java.util.Locale;

import org.quiltmc.qsl.testing.api.game.QuiltGameTest;
import org.quiltmc.qsl.testing.api.game.QuiltTestContext;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.test.GameTest;
import net.minecraft.util.math.BlockPos;

public class EntitySelectorGameTest {
	private void spawn(QuiltTestContext context, float health) {
		MobEntity entity = context.spawnMob(EntityType.CREEPER, BlockPos.ORIGIN);
		entity.setAiDisabled(true);
		entity.setHealth(health);
	}

	@GameTest(templateName = QuiltGameTest.EMPTY_STRUCTURE)
	public void testEntitySelector(QuiltTestContext context) {
		BlockPos absolute = context.getAbsolutePos(BlockPos.ORIGIN);

		spawn(context, 1.0f);
		spawn(context, 5.0f);
		spawn(context, 10.0f);

		String command = String.format(
				Locale.ROOT,
				"/kill @e[x=%d, y=%d, z=%d, distance=..2, %s=5.0]",
				absolute.getX(),
				absolute.getY(),
				absolute.getZ(),
				CommandTest.SELECTOR_ID.toUnderscoreSeparatedString()
		);

		context.expectEntitiesAround(EntityType.CREEPER, BlockPos.ORIGIN, 3, 2.0);
		MinecraftServer server = context.getWorld().getServer();
		int result = server.getCommandManager().executeWithPrefix(server.getCommandSource(), command);
		context.assertTrue(result == 2, "Expected 2 entities killed, got " + result);
		context.expectEntitiesAround(EntityType.CREEPER, BlockPos.ORIGIN, 1, 2.0);
		context.complete();
	}
}
