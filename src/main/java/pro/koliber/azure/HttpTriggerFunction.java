package pro.koliber.azure;

import java.sql.*;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import pro.koliber.azure.ambient.AmbientMetric;

/**
 * Azure Function with HTTP Trigger. Selects ambient metrics from a database that were collected on a given date.
 */
public class HttpTriggerFunction {

    @FunctionName("HttpTriggerFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        String query = request.getQueryParameters().get("date");
        String date = request.getBody().orElse(query);

        if (date == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a date on the query string")
                    .build();

        } else if (!isValidDate(date)){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a valid date on the query string - valid format is yyyy-mm-dd")
                    .build();

        } else {

            List<AmbientMetric> ambientMetrics = getAmbientMetrics(date, context);

            return request.createResponseBuilder(HttpStatus.OK)
                    .body(ambientMetrics)
                    .build();
        }
    }

    private List<AmbientMetric> getAmbientMetrics(String date, ExecutionContext context){

        List<AmbientMetric> ambientMetrics = new ArrayList<>();

        String url = System.getenv("sql-db-jdbc-connection-string");

        String queryString = "SELECT id, device_id, metric_datetime, temperature, pressure, humidity " +
                "FROM device_metrics WHERE CONVERT(DATE, metric_datetime) = ?";

        try (Connection con = DriverManager.getConnection(url)){

            PreparedStatement statement = con.prepareStatement(queryString);
            statement.setString(1, date);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                AmbientMetric ambientMetric = new AmbientMetric();

                ambientMetric.setId(rs.getLong("id"));
                ambientMetric.setDeviceId(rs.getString("device_id"));
                ambientMetric.setMetricDatetime(rs.getString("metric_datetime"));
                ambientMetric.setTemperature(rs.getString("temperature"));
                ambientMetric.setPressure(rs.getString("pressure"));
                ambientMetric.setHumidity(rs.getString("humidity"));

                context.getLogger().info("Fetched ambient metric with id " + ambientMetric.getId());

                ambientMetrics.add(ambientMetric);
            }

        } catch (SQLException e) {
            context.getLogger().warning(e.getMessage());
        }

        return ambientMetrics;
    }

    private boolean isValidDate(String date){

        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
