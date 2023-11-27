package com.example.omnitricswearable.data.sensor.implementations

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import com.example.omnitricswearable.data.sensor.AndroidSensor

class HeartRateSensor(
    context: Context
) : AndroidSensor(
    context = context,
//    sensorFeature = PackageManager.FEATURE_SENSOR_HEART_RATE,
//    sensorType = Sensor.TYPE_HEART_RATE
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)


