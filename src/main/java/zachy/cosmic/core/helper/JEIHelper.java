package zachy.cosmic.core.helper;

import net.minecraft.item.ItemStack;
import zachy.cosmic.block.base.BlockBase;

public class JEIHelper {

    public static Class getContainer(String name) {
        name = toClass(name);

        try {
            return Class.forName("zachy.cosmic.container.machine.Container" + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Class getGui(String name) {
        name = toClass(name);

        try {
            return Class.forName("zachy.cosmic.client.gui.machine.Gui" + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String toClass(String name) {
        String clazz = Character.toUpperCase(name.charAt(0)) + name.substring(1);

        if (name.contains("_")) {
            String temp = "";
            String split[] = name.split("_");

            for (int i = 0; i < split.length; i++) {
                split[i] = Character.toUpperCase(split[i].charAt(0)) + split[i].substring(1);

                temp += split[i];
            }

            clazz = temp;
        }

        return clazz;
    }

    public static ItemStack getCatalyst(String name) {
        BlockBase block = (BlockBase) BlockBase.getBlockFromName("cosmic:" + name);

        if (block != null) {
            return new ItemStack(block);
        }

        return ItemStack.EMPTY;
    }
}
