/**
 *
 */
package com.obs.temperature.factory;

import com.obs.temperature.reader.FuelTankTemperatureReader;
import com.obs.temperature.reader.interfaces.TemperatureReader;

/**
 * @author bwoo
 *
 */
public class TemperatureReaderFactory
{
    public enum Type
    {
        FUEL_TANK
    }

    public TemperatureReader getTemperatureReader(Type type)
    {
        if (Type.FUEL_TANK == type)
        {
            return FuelTankTemperatureReader.getInstance();
        }
        else
        {
            throw new IllegalArgumentException("You did not implement this type of temperature reader: " + type);
        }
    }
}
