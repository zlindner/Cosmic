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
import zachy.cosmic.api.recipe.IMachineRecipe;
import zachy.cosmic.core.util.StackUtils;

import java.util.ArrayList;
import java.util.List;

public class MachineRecipeFactory {

    private final ResourceLocation name;
    private JsonObject json;

    private int inputs;
    private int outputs;

    public MachineRecipeFactory(ResourceLocation name, JsonObject json, int inputs, int outputs) {
        this.name = name;
        this.json = json;

        this.inputs = inputs;
        this.outputs = outputs;
    }

    public IMachineRecipe create(JsonContext context) {
        JsonArray jsonInputs = JsonUtils.getJsonArray(json, "inputs");

        if (jsonInputs.size() != inputs) {
            throw new JsonSyntaxException("Expected " + inputs + " inputs, got " + jsonInputs.size() + " input(s)");
        }

        NonNullList<ItemStack>inputs = NonNullList.withSize(jsonInputs.size(), ItemStack.EMPTY);

        int i = 0;

        for (JsonElement element : jsonInputs) {
            if (!element.isJsonNull()) {
                inputs.set(i, CraftingHelper.getItemStack(element.getAsJsonObject(), context));
            }

            i++;
        }

        JsonArray jsonOutputs = JsonUtils.getJsonArray(json, "outputs");

        if (jsonOutputs.size() != outputs) {
            throw new JsonSyntaxException("Expected " + outputs + " outputs, got " + jsonOutputs.size() + " output(s)");
        }

        NonNullList<ItemStack> outputs = NonNullList.withSize(jsonOutputs.size(), ItemStack.EMPTY);

        i = 0;

        for (JsonElement element : jsonOutputs) {
            if (!element.isJsonNull()) {
                outputs.set(i, CraftingHelper.getItemStack(element.getAsJsonObject(), context));
            }

            i++;
        }

        final int duration = JsonUtils.getInt(json, "duration");
        final int energy = JsonUtils.getInt(json, "energy");

        FluidStack fluid = null;
        int fluidAmount = 0;

        if (json.has("fluid")) {
            fluid = FluidRegistry.getFluidStack(JsonUtils.getString(json, "fluid"), 0);
            fluidAmount = JsonUtils.getInt(json, "fluid_amount");
        }

        final FluidStack fluidFinal = fluid;
        final int fluidAmountFinal = fluidAmount;

        int heat = 0;

        if (json.has("heat")) {
            heat = JsonUtils.getInt(json, "heat");
        }

        final int heatFinal = heat;

        return new IMachineRecipe() {

            @Override
            public ResourceLocation getName() {
                return name;
            }

            @Override
            public ItemStack getInput(int index) {
                return inputs.get(index);
            }

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

            @Override
            public FluidStack getFluid() {
                return fluidFinal;
            }

            @Override
            public int getFluidAmount() {
                return fluidAmountFinal;
            }

            @Override
            public int getHeat() {
                return heatFinal;
            }
        };
    }
}
