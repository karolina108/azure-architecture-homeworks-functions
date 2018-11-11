-- noinspection SqlNoDataSourceInspectionForFile

/* Create table */
CREATE TABLE device_metrics (
    id int NOT NULL IDENTITY(1, 1) PRIMARY KEY,
    device_id NVARCHAR(35),
    metric_datetime DATETIME,
    temperature NUMERIC(6, 2),
    pressure NUMERIC (6, 2),
    humidity NUMERIC (6, 2)
);

/* Check */
SELECT * FROM device_metrics;