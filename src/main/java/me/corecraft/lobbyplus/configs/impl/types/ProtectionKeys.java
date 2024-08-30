package me.corecraft.lobbyplus.configs.impl.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import me.corecraft.lobbyplus.configs.beans.SoundProperty;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ProtectionKeys implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        conf.setComment("protection", "Prevents players from wreaking havoc in your lobby!");
    }

    @Comment("Prevents a player from building, if this is set to true.")
    public static final Property<Boolean> event_prevent_block_interact = newProperty("protection.block.prevent-interaction.toggle", true);

    @Comment({
            "A list of sounds: https://minecraft.wiki/w/Sounds.json",
            "",
            "Handles the sound to play, when a player cannot build, volume/pitch control, and Custom Sounds are supported!"
    })
    public static final Property<SoundProperty> protection_sound = newBeanProperty(SoundProperty.class, "protection.block.prevent-interaction.sound", new SoundProperty().populate());

    @Comment("Prevents a player from picking up items, if this is set to true.")
    public static final Property<Boolean> event_prevent_item_pickup = newProperty("protection.item.prevent-pickup", true);

    @Comment("Prevents a player from loosing hunger, if this is set to true.")
    public static final Property<Boolean> event_prevent_hunger_change = newProperty("protection.prevent-hunger-change", true);

    @Comment("Prevents a player from dropping items, if this is set to true.")
    public static final Property<Boolean> event_prevent_item_drop = newProperty("protection.item.prevent-drop", true);

    @Comment("Prevent leaf decay, if this is set to true.")
    public static final Property<Boolean> event_prevent_leaf_decay = newProperty("protection.block.prevent-leaf-decay", true);

    @Comment("Prevents fire from spreading, if this is set to true.")
    public static final Property<Boolean> event_prevent_fire_spread = newProperty("protection.block.prevent-fire-spread", true);

    @Comment("Prevents blocks from burning, if this is set to true.")
    public static final Property<Boolean> event_prevent_block_burn = newProperty("protection.block.prevent-block-burn", true);

    @Comment("Prevents mobs from spawning, if this is set to true.")
    public static final Property<Boolean> event_prevent_mob_spawning = newProperty("protection.block.prevent-mob-spawning", true);

}