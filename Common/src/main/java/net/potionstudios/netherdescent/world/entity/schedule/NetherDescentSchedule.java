package net.potionstudios.netherdescent.world.entity.schedule;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentSchedule {
    public static final Supplier<Schedule> NERTLING = register("nertling", new ScheduleBuilder(new Schedule())
            .changeActivityAt(10, Activity.IDLE)
            .changeActivityAt(2000, Activity.WORK)
            .changeActivityAt(5000, Activity.PLAY)
            .changeActivityAt(7000, Activity.WORK)
            .changeActivityAt(9000, Activity.MEET)
            .changeActivityAt(10000, Activity.PLAY)
            .changeActivityAt(12000, Activity.REST));

    private static Supplier<Schedule> register(String key, ScheduleBuilder scheduleBuilder) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.SCHEDULE, key, scheduleBuilder::build);
    }

    public static void schedules() {
        NetherDescent.LOGGER.info("Registering Nether Descent Schedules");
    }
}
