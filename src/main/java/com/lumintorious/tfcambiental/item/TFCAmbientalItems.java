package com.lumintorious.tfcambiental.item;

import com.lumintorious.tfcambiental.TFCAmbiental;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TFCAmbientalItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TFCAmbiental.MOD_ID);

    public static final RegistryObject<Item> LEATHER_APRON = ITEMS.register(
        "leather_apron",
        () -> new ClothesItem(LeatherApronMaterial.MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );

    public static final RegistryObject<Item> STRAW_HAT = ITEMS.register(
        "straw_hat",
        () -> new ClothesItem(StrawClothesMaterial.MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );

    public static final RegistryObject<Item> WOOL_HAT = ITEMS.register(
        "wool_hat",
        () -> new ClothesItem(WoolClothesMaterial.MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> WOOL_SWEATER = ITEMS.register(
            "wool_sweater",
            () -> new ClothesItem(WoolClothesMaterial.MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> WOOL_PANTS = ITEMS.register(
            "wool_pants",
            () -> new ClothesItem(WoolClothesMaterial.MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> WOOL_BOOTS = ITEMS.register(
            "wool_boots",
            () -> new ClothesItem(WoolClothesMaterial.MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );

    public static final RegistryObject<Item> SILK_COWL = ITEMS.register(
            "silk_cowl",
            () -> new ClothesItem(SilkClothesMaterial.MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> SILK_SHIRT = ITEMS.register(
            "silk_shirt",
            () -> new ClothesItem(SilkClothesMaterial.MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> SILK_PANTS = ITEMS.register(
            "silk_pants",
            () -> new ClothesItem(SilkClothesMaterial.MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> SILK_SHOES = ITEMS.register(
            "silk_shoes",
            () -> new ClothesItem(SilkClothesMaterial.MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );

    public static final RegistryObject<Item> BURLAP_COWL = ITEMS.register(
            "burlap_cowl",
            () -> new ClothesItem(BurlapClothesMaterial.MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> BURLAP_SHIRT = ITEMS.register(
            "burlap_shirt",
            () -> new ClothesItem(BurlapClothesMaterial.MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> BURLAP_PANTS = ITEMS.register(
            "burlap_pants",
            () -> new ClothesItem(BurlapClothesMaterial.MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> BURLAP_SHOES = ITEMS.register(
            "burlap_shoes",
            () -> new ClothesItem(BurlapClothesMaterial.MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );

    public static final RegistryObject<Item> LEATHER_HAT = ITEMS.register(
            "insulated_leather_hat",
            () -> new ClothesItem(InsulatedLeatherClothesMaterial.MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> LEATHER_TUNIC = ITEMS.register(
            "insulated_leather_tunic",
            () -> new ClothesItem(InsulatedLeatherClothesMaterial.MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> LEATHER_PANTS = ITEMS.register(
            "insulated_leather_pants",
            () -> new ClothesItem(InsulatedLeatherClothesMaterial.MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
    public static final RegistryObject<Item> LEATHER_BOOTS = ITEMS.register(
            "insulated_leather_boots",
            () -> new ClothesItem(InsulatedLeatherClothesMaterial.MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1))
    );
}
