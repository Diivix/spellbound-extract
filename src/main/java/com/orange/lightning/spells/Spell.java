package com.orange.lightning.spells;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.orange.lightning.DdObject;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public abstract class Spell implements DdObject {
    protected abstract String name();

    protected abstract String school();

    protected abstract int level();

    protected abstract ImmutableList<String> classTypes();

    protected abstract String castingTime();

    protected abstract String range();

    protected abstract ImmutableList<String> components();

    protected abstract String materials();

    protected abstract String duration();

    protected abstract String description();

    protected abstract String atHigherLevels();

    @Value.Check
    protected void check() {
        // Check for empty values
        Preconditions.checkState(!name().isEmpty(), "'name' cannot be empty");
        Preconditions.checkState(!school().isEmpty(), "'school' cannot be empty");
        Preconditions.checkState(!classTypes().isEmpty(), "'classTypes' cannot be empty");
        Preconditions.checkState(!castingTime().isEmpty(), "'castingTime' cannot be empty");
        Preconditions.checkState(!range().isEmpty(), "'range' cannot be empty");
        Preconditions.checkState(!components().isEmpty(), "'components' cannot be empty");
        Preconditions.checkState(!duration().isEmpty(), "'duration' cannot be empty");
        Preconditions.checkState(!description().isEmpty(), "'description' cannot be empty");

        // Check for wrong information
        Preconditions.checkState(!description().matches("^Page\\:.*"),
                "'description' has wrong information.");
        Preconditions.checkState(!atHigherLevels().matches("^Page\\:.*"),
                "'atHigherLevels' has wrong information.");

        // Check level
        Preconditions.checkState(level() >= 0 && level() <= 20,
                "Level must be between 0 and 20. Found level at: " + level());

        // Check class Types
        ImmutableList<String> validClassTypes = ImmutableList.of("barbarian","bard","cleric","druid","fighter",
                "monk","paladin","ranger","rogue","sorcerer","warlock","wizard");
        classTypes().forEach(classType -> Preconditions.checkState(validClassTypes.contains(classType.toLowerCase()),
                "ClassType '" + classType + "' is not a valid classType."));

        // Check component
        Preconditions.checkState(components().size() > 0 && components().size() <= 3,
                "There can only be three components. Found: " + components().size());
        ImmutableList<String> validComponents = ImmutableList.of("v", "s", "m");
        components().forEach(component -> Preconditions.checkState(validComponents.contains(component.toLowerCase()),
                "Component " + component + " is not a valid component."));
    }
}
