-- Example data for BASE_STATION_ENTITY
INSERT INTO BASE_STATION_ENTITY (ID, DETECTION_RADIUS_IN_METERS, NAME, X, Y)
VALUES ('c2e5dfb4-8b8e-4e4f-9b8d-9e9a8b7a6d5c', 100, 'Base Station 1', 10, 20);

INSERT INTO BASE_STATION_ENTITY (ID, DETECTION_RADIUS_IN_METERS, NAME, X, Y)
VALUES ('d2e8c5b6-7a7b-4c4d-8e8f-9a9b8c7d6e5f', 100, 'Base Station 2', 40, 40);

INSERT INTO BASE_STATION_ENTITY (ID, DETECTION_RADIUS_IN_METERS, NAME, X, Y)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 100, 'Base Station 3', 90, 60);


-- Create the mobile station entry
INSERT INTO MOBILE_STATION_ENTITY (ID, LAST_KNOWNX, LAST_KNOWNY)
VALUES ('12345678-1234-5678-1234-567812345678', NULL, NULL);

-- Create the report entries
INSERT INTO REPORT_ENTRY_ENTITY (ID, DISTANCE, TIMESTAMP, BASE_STATION_ID, MOBILE_STATION_ID)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 10.0, '2023-08-05 20:21:40.880', 'c2e5dfb4-8b8e-4e4f-9b8d-9e9a8b7a6d5c',
        '12345678-1234-5678-1234-567812345678');

INSERT INTO REPORT_ENTRY_ENTITY (ID, DISTANCE, TIMESTAMP, BASE_STATION_ID, MOBILE_STATION_ID)
VALUES ('6fa459ea-ee8a-3ca4-894e-db77e160355e', 15.0, '2023-08-05 20:21:40.880', 'd2e8c5b6-7a7b-4c4d-8e8f-9a9b8c7d6e5f',
        '12345678-1234-5678-1234-567812345678');

INSERT INTO REPORT_ENTRY_ENTITY (ID, DISTANCE, TIMESTAMP, BASE_STATION_ID, MOBILE_STATION_ID)
VALUES ('69e6f2b4-5b70-4a5a-8d6b-8c8b5b4a6e7d', 24.0, '2023-08-05 20:21:40.880', 'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d',
        '12345678-1234-5678-1234-567812345678');