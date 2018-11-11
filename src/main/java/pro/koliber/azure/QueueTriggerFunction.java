package pro.koliber.azure;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import pro.koliber.azure.ambient.AmbientMetric;

import java.sql.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggerFunction {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTriggerFunction")
    public void run(
        @QueueTrigger(name = "message", queueName = "cs-arch-srvless-hmk-queue-001", connection = "csarchsrvlesshmkstorage_STORAGE") AmbientMetric message,
        final ExecutionContext context
    ) {

        context.getLogger().info("Java Queue trigger function processed a message: " + message.toString());

        insertAmbientMetric(message, context);
    }

    private void insertAmbientMetric(AmbientMetric message, ExecutionContext context){

        String url = System.getenv("sql-db-jdbc-connection-string");

        String insertStatement = "INSERT INTO device_metrics " +
                "(device_id, metric_datetime, temperature, pressure, humidity) " +
                "VALUES (?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url)){

            PreparedStatement statement = con.prepareStatement(insertStatement);

            statement.setString(1, message.getDeviceId());
            statement.setString(2, message.getMetricDatetime());
            statement.setString(3, message.getTemperature());
            statement.setString(4, message.getPressure());
            statement.setString(5, message.getHumidity());

            int insertedRecord = statement.executeUpdate();

            context.getLogger().info("Inserted " + insertedRecord + " records");

        } catch (SQLException e) {

            context.getLogger().info(e.getMessage());
        }
    }
}
