package ladysnake.dissolution.common.init;

import ladysnake.dissolution.common.Dissolution;
import ladysnake.dissolution.common.Reference;
import ladysnake.dissolution.common.blocks.BlockCrystallizer;
import ladysnake.dissolution.common.blocks.BlockDriedLava;
import ladysnake.dissolution.common.blocks.BlockEctoplasm;
import ladysnake.dissolution.common.blocks.BlockMercuriusWaystone;
import ladysnake.dissolution.common.blocks.BlockMercuryCandle;
import ladysnake.dissolution.common.blocks.BlockResuscitator;
import ladysnake.dissolution.common.blocks.BlockSepulture;
import ladysnake.dissolution.common.blocks.BlockSoulAnchor;
import ladysnake.dissolution.common.blocks.BlockSoulExtractor;
import ladysnake.dissolution.common.blocks.BlockSulfurCandle;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	public static Block ECTOPLASMA;
	public static BlockEctoplasm ECTOPLASM;
    public static BlockCrystallizer CRYSTALLIZER;
    public static BlockDriedLava DRIED_LAVA;
    public static BlockMercuriusWaystone MERCURIUS_WAYSTONE;
    public static BlockSoulAnchor SOUL_ANCHOR;
    public static BlockMercuryCandle MERCURY_CANDLE;
    public static BlockSulfurCandle SULFUR_CANDLE;
    public static BlockSoulExtractor SOUL_EXTRACTOR;
    public static BlockSepulture SEPULTURE;
    public static BlockResuscitator RESUSCITATOR;
    
    private IForgeRegistry<Block> reg;

    public static void init() {
    	CRYSTALLIZER = new BlockCrystallizer();
    	//DRIED_LAVA = new BlockDriedLava();
    	ECTOPLASM = new BlockEctoplasm();
    	ECTOPLASMA = new Block(Material.CLOTH);
    	ECTOPLASMA.setUnlocalizedName(Reference.Blocks.ECTOPLASMA.getUnlocalizedName());
    	ECTOPLASMA.setRegistryName(Reference.Blocks.ECTOPLASMA.getRegistryName());
    	ECTOPLASMA.setHardness(0.5f);
    	MERCURIUS_WAYSTONE = new BlockMercuriusWaystone();
    	SOUL_ANCHOR = new BlockSoulAnchor();
    	MERCURY_CANDLE = new BlockMercuryCandle();
    	SULFUR_CANDLE = new BlockSulfurCandle();
    	SOUL_EXTRACTOR = new BlockSoulExtractor();
    	SEPULTURE = new BlockSepulture();
    	//RESUSCITATOR = new BlockResuscitator();
    }
    
    @SubscribeEvent
    public void onRegister(RegistryEvent.Register<Block> event) {
    	reg = event.getRegistry();
    	this.register();
    }
    
    public void register() {
    	registerBlock(CRYSTALLIZER);
    	//registerBlock(DRIED_LAVA);
    	registerBlock(ECTOPLASMA);
    	registerBlock(ECTOPLASM);
    	registerBlock(MERCURIUS_WAYSTONE).setMaxStackSize(1);
    	registerBlock(SOUL_ANCHOR);
    	registerBlock(MERCURY_CANDLE);
    	registerBlock(SULFUR_CANDLE);
    	reg.register(SEPULTURE);
    	registerBlock(SOUL_EXTRACTOR);
    	//registerBlock(RESUSCITATOR);
    }
    
    private Item registerBlock(Block block) {
    	reg.register(block);
    	ItemBlock item = new ItemBlock(block);
    	item.setRegistryName(block.getRegistryName());
    	ModItems.blocks.add(item);
    	block.setCreativeTab(Dissolution.CREATIVE_TAB);
    	return item;
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerRenders(ModelRegistryEvent event) {
    	registerRender(CRYSTALLIZER);
    	//registerRender(DRIED_LAVA);
    	registerRender(SOUL_EXTRACTOR);
    	registerRender(MERCURIUS_WAYSTONE);
    	registerRender(SEPULTURE);
    	registerRender(ECTOPLASM);
    	registerRender(ECTOPLASMA);
    	registerRender(SOUL_ANCHOR);

    	//registerRender(RESUSCITATOR);

    	registerRender(MERCURY_CANDLE);
    	registerRender(SULFUR_CANDLE);
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerRender(Block block) {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().toString().substring(5)));
    }
}