package ladysnake.dissolution.mixin.server.network;

import com.mojang.authlib.GameProfile;
import ladysnake.dissolution.api.possession.Possessable;
import ladysnake.dissolution.api.possession.Possessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ladysnake.dissolution.common.network.DissolutionNetworking.*;
import static ladysnake.dissolution.mixin.server.PlayerTagKeys.*;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world_1, GameProfile gameProfile_1) {
        super(world_1, gameProfile_1);
    }

    @Inject(method = "onStartedTracking", at = @At("HEAD"))
    public void onStartedTracking(Entity tracked, CallbackInfo info) {
        if (tracked instanceof PlayerEntity) {
            // Synchronize soul players with other players
            sendTo((ServerPlayerEntity)(Object)this, createCorporealityPacket((PlayerEntity) tracked));
        } else if (tracked instanceof Possessable) {
            // Synchronize possessed entities with their possessor
            if (((Possessable)tracked).getPossessingEntityUuid().filter(this.getUuid()::equals).isPresent()) {
                sendTo((ServerPlayerEntity)(Object)this, createPossessionPacket(getUuid(), tracked.getEntityId()));
            }
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    public void writePossessedMobToTag(CompoundTag tag, CallbackInfo info) {
        Entity possessedEntity = (Entity) ((Possessor)this).getPossessedEntity();
        if (possessedEntity != null) {
            Entity possessedEntityVehicle = possessedEntity.getTopmostRiddenEntity();
            CompoundTag possessedRoot = new CompoundTag();
            CompoundTag serializedPossessed = new CompoundTag();
            possessedEntityVehicle.saveToTag(serializedPossessed);
            possessedRoot.put(POSSESSED_ENTITY_TAG, serializedPossessed);
            possessedRoot.putUuid(POSSESSED_UUID_TAG, possessedEntity.getUuid());
            tag.put(POSSESSED_ROOT_TAG, possessedRoot);
        }
    }
}