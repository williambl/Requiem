package ladysnake.dissolution.common.compat;

import ladylib.compat.EnhancedBusSubscriber;
import ladylib.compat.StateEventReceiver;
import ladysnake.dissolution.common.entity.EntityPlayerShell;
import ladysnake.dissolution.common.entity.PossessableEntityFactory;
import ladysnake.dissolution.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;

import java.util.ArrayList;
import java.util.List;

@EnhancedBusSubscriber(Thaumcraft.MODID)
public class ThaumcraftCompat implements StateEventReceiver {

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        assignAspects();
    }

    private static void assignAspects() {
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.HUMAN_FLESH_RAW), new AspectList()
                .add(Aspect.MAN, 5)
                .add(Aspect.LIFE, 5)
                .add(Aspect.EARTH, 5)
        );
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.HUMAN_HEART), new AspectList()
                .add(Aspect.LIFE, 20)
                .add(Aspect.MAN, 5)
                .add(Aspect.EARTH, 5)
                .add(Aspect.MOTION, 10)
        );
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.HUMAN_BRAIN), new AspectList(new ItemStack(ItemsTC.brain))
                .add(Aspect.MAN, 10)
                .remove(Aspect.UNDEAD)
        );
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.SANGUINE_POTION), new AspectList()
                .add(Aspect.SOUL, 10)
                .add(Aspect.ALCHEMY, 5)
                .add(Aspect.MAN, 2)
                .add(Aspect.WATER, 5)
                .add(Aspect.LIFE, 3)
        );
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.EAU_DE_MORT), new AspectList()
                .add(Aspect.SOUL, 10)
                .add(Aspect.ALCHEMY, 5)
                .add(Aspect.MAN, 2)
                .add(Aspect.WATER, 5)
                .add(Aspect.DEATH, 3)
        );
        // Add aspects to every procedurally generated entity based on the original
        PossessableEntityFactory.getAllGeneratedPossessables().forEach(e -> {
            AspectList tags;
            String entityName = EntityRegistry.getEntry(e.getKey()).getName();
            ThaumcraftApi.EntityTagsNBT[] nbt = new ThaumcraftApi.EntityTagsNBT[0];
            List<ThaumcraftApi.EntityTags> entityTags = new ArrayList<>();
            for (ThaumcraftApi.EntityTags et : CommonInternals.scanEntities) {
                if (!et.entityName.equals(entityName)) continue;
                if (et.nbts == null || et.nbts.length == 0) {
                    tags = et.aspects;
                } else {
                    tags = et.aspects;
                    nbt = et.nbts;
                }
                entityTags.add(new ThaumcraftApi.EntityTags(entityName, tags, nbt));
            }
            CommonInternals.scanEntities.addAll(entityTags);
        });
        ThaumcraftApi.registerEntityTag(
                EntityRegistry.getEntry(EntityPlayerShell.class).getName(),
                new AspectList().add(Aspect.MAN, 4).add(Aspect.EARTH, 8).add(Aspect.ENTROPY, 4)
        );
    }
}
