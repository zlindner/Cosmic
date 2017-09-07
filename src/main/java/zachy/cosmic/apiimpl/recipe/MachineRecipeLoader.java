package zachy.cosmic.apiimpl.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import zachy.cosmic.apiimpl.API;
import zachy.cosmic.core.Lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

public class MachineRecipeLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void load(String machine, int inputs, int outputs) {
        JsonContext context = new JsonContext(Lib.MOD_ID);

        CraftingHelper.findFiles(Loader.instance().activeModContainer(), "assets/" + Lib.MOD_ID + "/machine_recipes/" + machine, root -> {
            //TODO Load the constants into to the context.

            return true;
        }, (root, file) -> {
            String relative = root.relativize(file).toString();

            if (!"json".equals(FilenameUtils.getExtension(file.toString()))) {
                return true;
            }

            String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
            ResourceLocation key = new ResourceLocation(Lib.MOD_ID, name);

            BufferedReader reader = null;

            try {
                reader = Files.newBufferedReader(file);

                API.instance().getMachineRegistry(machine).addRecipe(new MachineRecipeFactory(key, JsonUtils.fromJson(GSON, reader, JsonObject.class), inputs, outputs).create(context));
            } catch (JsonParseException e) {
                FMLLog.log.error("Parsing error while reading JSON recipe {}", key, e);

                return false;
            } catch (IOException e) {
                FMLLog.log.error("Couldn't read JSON recipe {} from {}", key, file, e);

                return false;
            } finally {
                IOUtils.closeQuietly(reader);
            }

            FMLLog.log.info("Loaded " + machine + " recipes");

            return true;
        });
    }
}
