package pro.koliber.azure;

import java.time.*;
import java.time.format.DateTimeFormatter;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import pro.koliber.azure.ambient.AmbientMetric;

/**
 * Azure Function with Timer trigger. Generates ambient metrics every minute and sends them to a queue.
 */
public class TimerTriggerFunction {

    @FunctionName("TimerTriggerFunction")
    @QueueOutput(name="message", queueName = "cs-arch-srvless-hmk-queue-001", connection = "csarchsrvlesshmkstorage_STORAGE")
    public AmbientMetric run(
        @TimerTrigger(name = "timerInfo", schedule = "0 */5 * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        AmbientMetric ambientMetric = new AmbientMetric();

        ambientMetric.setDeviceId("MyDeviceID123455677");
        ambientMetric.setMetricDatetime(formatDateTime);
        ambientMetric.setTemperature("25.60");
        ambientMetric.setPressure("1011.54");
        ambientMetric.setHumidity("0.48");

        context.getLogger().info("Message to be added: " + ambientMetric.toString());

        return ambientMetric;
    }
}
