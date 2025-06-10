package com.smartbalaram.planning.util;

public class TestConstant {
    public static final String LOG_MONGO_SAVE = "✔ Plan saved to MongoDB: ID = {}";
    public static final String LOG_KAFKA_SENT = "📤 Kafka message sent to 'plan-created' topic for user age {}";
    public static final String LOG_REQUEST_RECEIVED = "📥 Received plan request for target ₹{} in {} years";
    public static final String LOG_RESPONSE_READY = "✅ Response prepared for user age {} with estimated CAGR {}%";

    public static final String RISK_LOW = "low";
    public static final String RISK_MODERATE = "moderate";
    public static final String RISK_HIGH = "high";

    public static final String PROJECTION_FORMAT = "You will reach ₹%.0f in %d years";
}