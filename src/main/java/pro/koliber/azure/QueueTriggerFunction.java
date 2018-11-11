package pro.koliber.azure;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggerFunction {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTriggerFunction")
    public void run(
        @QueueTrigger(name = "message", queueName = "cs-arch-srvless-hmk-queue-001", connection = "csarchsrvlesshmkstorage_STORAGE") String message,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Queue trigger function processed a message: " + message);
    }
}
