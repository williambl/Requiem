package ladysnake.dissolution.common.entity.ai;

import ladysnake.dissolution.api.v1.possession.Possessable;
import net.minecraft.entity.ai.goal.Goal;

public class InertGoal extends Goal {
    private final Possessable owner;

    public InertGoal(Possessable owner) {
        super();
        this.owner = owner;
        this.setControlBits(0xFF);
    }

    @Override
    public boolean canStart() {
        return this.owner.isBeingPossessed();
    }

    @Override
    public boolean shouldContinue() {
        return this.owner.isBeingPossessed();
    }

    @Override
    public boolean canStop() {
        return false;
    }
}
