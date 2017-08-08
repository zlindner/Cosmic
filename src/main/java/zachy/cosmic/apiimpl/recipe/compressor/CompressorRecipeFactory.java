package zachy.cosmic.apiimpl.recipe.compressor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import zachy.cosmic.api.recipe.compressor.ICompressorRecipe;
import zachy.cosmic.common.core.util.StackUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

//TODO fix recipes... compact?
public class CompressorRecipeFactory {

    private final ResourceLocation name;
    private JsonObject json;

    public CompressorRecipeFactory(ResourceLocation name, JsonObject json) {
        this.name = name;
        this.json = json;
    }

    public ICompressorRecipe create(JsonContext context) {
        JsonArray inputsArray = JsonUtils.getJsonArray(json, "inputs");

        if (inputsArray.size() != 2) {
            throw new JsonSyntaxException("Expected 2 inputs, got " + inputsArray.size() + " input(s)");
        }

        List<NonNullList<ItemStack>> inputs = new ArrayList<>(2);

        for (JsonElement element : inputsArray) {
            if (element.isJsonNull()) {
                inputs.add(StackUtils.emptyNonNullList());
            } else {
                inputs.add(NonNullList.from(ItemStack.EMPTY, CraftingHelper.getItemStack(element.getAsJsonObject(), context)));
            }
        }

        JsonArray outputsArray = JsonUtils.getJsonArray(json, "outputs");

        if (outputsArray.size() != 2) {
            throw new JsonSyntaxException("Expected 2 outputs, got " + outputsArray.size() + " output(s)");
        }

        NonNullList<ItemStack> outputs = NonNullList.withSize(2, ItemStack.EMPTY);

        int i = 0;

        for (JsonElement element : outputsArray) {
            if (!element.isJsonNull()) {
                outputs.set(i, CraftingHelper.getItemStack(element.getAsJsonObject(), context));
            }

            i++;
        }

        final int duration = JsonUtils.getInt(json, "duration");
        final int energy = JsonUtils.getInt(json, "energy");

        return new ICompressorRecipe() {
            @Override
            public ResourceLocation getName() {
                return name;
            }

            @Nonnull
            @Override
            public NonNullList<ItemStack> getInput(int index) {
                return inputs.get(index);
            }

            @Nonnull
            @Override
            public ItemStack getOutput(int index) {
                return outputs.get(index);
            }

            @Override
            public int getDuration() {
                return duration;
            }

            @Override
            public int getEnergy() {
                return energy;
            }
        };
    }
}
