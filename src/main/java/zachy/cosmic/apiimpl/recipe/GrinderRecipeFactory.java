package zachy.cosmic.apiimpl.recipe;

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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zachy.cosmic.api.recipe.IGrinderRecipe;

import javax.annotation.Nonnull;

public class GrinderRecipeFactory {

    private final ResourceLocation name;
    private JsonObject json;

    public GrinderRecipeFactory(ResourceLocation name, JsonObject json) {
        this.name = name;
        this.json = json;
    }

    public IGrinderRecipe create(JsonContext context) {
        JsonArray inputArray = JsonUtils.getJsonArray(json, "input");

        if (inputArray.size() != 1) {
            throw new JsonSyntaxException("Expected 1 input, got " + inputArray.size() + " input(s)");
        }

        NonNullList<ItemStack> _input = NonNullList.create();

        for (JsonElement element : inputArray) {
            _input = NonNullList.from(ItemStack.EMPTY, CraftingHelper.getIngredient(element, context).getMatchingStacks());
        }

        NonNullList<ItemStack> input = _input;

        FluidStack fluid = FluidRegistry.getFluidStack(JsonUtils.getString(json, "fluid"), 0);

        JsonArray outputsArray = JsonUtils.getJsonArray(json, "outputs");

        if (outputsArray.size() != 4) {
            throw new JsonSyntaxException("Expected 4 outputs, got " + outputsArray.size() + " output(s)");
        }

        NonNullList<ItemStack> outputs = NonNullList.withSize(4, ItemStack.EMPTY);

        int i = 0;

        for (JsonElement element : outputsArray) {
            if (!element.isJsonNull()) {
                outputs.set(i, CraftingHelper.getItemStack(element.getAsJsonObject(), context));
            }

            i++;
        }

        final int fluidAmount = JsonUtils.getInt(json, "fluid_amount");
        final int duration = JsonUtils.getInt(json, "duration");
        final int energy = JsonUtils.getInt(json, "energy");

        return new IGrinderRecipe() {
            @Override
            public ResourceLocation getName() {
                return name;
            }

            @Nonnull
            @Override
            public NonNullList<ItemStack> getInput() {
                return input;
            }

            @Override
            public FluidStack getFluid() {
                return fluid;
            }

            @Override
            public int getFluidAmount() {
                return fluidAmount;
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
