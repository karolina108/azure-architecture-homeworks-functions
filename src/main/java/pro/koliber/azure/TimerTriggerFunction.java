package pro.koliber.azure;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import pro.koliber.azure.ambient.AmbientMetric;

/**
 * Azure Functions with Timer trigger.
 */
public class TimerTriggerFunction {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimerTriggerFunction")
    @QueueOutput(name="message", queueName = "cs-arch-srvless-hmk-queue-001", connection = "csarchsrvlesshmkstorage_STORAGE")
    public AmbientMetric run(
        @TimerTrigger(name = "timerInfo", schedule = "0 */1 * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());

        AmbientMetric ambientMetric = new AmbientMetric();

        ambientMetric.setDeviceId("MyDeviceID123455677");
        ambientMetric.setMetricDatetime(LocalDateTime.now().toString());
        ambientMetric.setTemperature("25.60");
        ambientMetric.setPressure("1011.54");
        ambientMetric.setHumidity("0.48");

        context.getLogger().info("Message to be added: " + ambientMetric.toString());

        return ambientMetric;
    }
}
