package zachy.cosmic.apiimpl.recipe.blast_furnace;

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
import zachy.cosmic.common.core.Lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

public class BlastFurnaceRecipeLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void load() {
        JsonContext context = new JsonContext(Lib.MOD_ID);

        CraftingHelper.findFiles(Loader.instance().activeModContainer(), "assets/" + Lib.MOD_ID + "/machine_recipes/blast_furnace", root -> {
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

                API.instance().getBlastFurnaceRegistry().addRecipe(new BlastFurnaceRecipeFactory(key, JsonUtils.fromJson(GSON, reader, JsonObject.class)).create(context));
            } catch (JsonParseException e) {
                FMLLog.log.error("Parsing error while reading JSON blast furnace recipe {}", key, e);

                return false;
            } catch (IOException e) {
                FMLLog.log.error("Couldn't read JSON blast furnace recipe {} from {}", key, file, e);

                return false;
            } finally {
                IOUtils.closeQuietly(reader);
            }

            return true;
        });
    }
}
