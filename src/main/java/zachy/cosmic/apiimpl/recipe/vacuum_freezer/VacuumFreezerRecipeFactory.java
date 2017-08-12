package zachy.cosmic.apiimpl.recipe.vacuum_freezer;

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
import zachy.cosmic.api.recipe.vacuum_freezer.IVacuumFreezerRecipe;
import zachy.cosmic.common.core.util.StackUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class VacuumFreezerRecipeFactory {

    private final ResourceLocation name;
    private JsonObject json;

    public VacuumFreezerRecipeFactory(ResourceLocation name, JsonObject json) {
        this.name = name;
        this.json = json;
    }

    public IVacuumFreezerRecipe create(JsonContext context) {
        JsonArray inputArray = JsonUtils.getJsonArray(json, "input");

        if (inputArray.size() != 1) {
            throw new JsonSyntaxException("Expected 1 input, got " + inputArray.size() + " input(s)");
        }

        List<NonNullList<ItemStack>> input = new ArrayList<>(1);

        for (JsonElement element : inputArray) {
            if (element.isJsonNull()) {
                input.add(StackUtils.emptyNonNullList());
            } else {
                input.add(NonNullList.from(ItemStack.EMPTY, CraftingHelper.getItemStack(element.getAsJsonObject(), context)));
            }
        }

        JsonArray outputArray = JsonUtils.getJsonArray(json, "output");

        if (outputArray.size() != 1) {
            throw new JsonSyntaxException("Expected 1 output, got " + outputArray.size() + " output(s)");
        }

        ItemStack output = ItemStack.EMPTY;

        for (JsonElement element : outputArray) {
            if (!element.isJsonNull()) {
                output = new ItemStack(CraftingHelper.getItemStack(element.getAsJsonObject(), context).getItem());
            }
        }

        ItemStack finalOutput = output;

        final int duration = JsonUtils.getInt(json, "duration");
        final int energy = JsonUtils.getInt(json, "energy");

        return new IVacuumFreezerRecipe() {
            @Override
            public ResourceLocation getName() {
                return name;
            }

            @Nonnull
            @Override
            public NonNullList<ItemStack> getInput() {
                return input.get(0);
            }

            @Nonnull
            @Override
            public ItemStack getOutput() {
                return finalOutput;
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
