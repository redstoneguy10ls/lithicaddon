package com.redstoneguy10ls.lithicaddon.util.advancements;


import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;



public class GenericTrigger extends SimpleCriterionTrigger<GenericTrigger.TriggerInstance>
{
    private final ResourceLocation id;

    public GenericTrigger(ResourceLocation id){this.id=id;}

    public void trigger (ServerPlayer player){this.trigger(player, instance -> true);}

    @Override
    protected GenericTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context)
    {
        return new GenericTrigger.TriggerInstance(predicate);
    }

    public ResourceLocation getId() {
        return id;
    }
    public class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(ContextAwarePredicate predicate){super(id,predicate);}
    }
}
