/**
 *
 */
package com.obs.temperature.reader;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.types.State;

import com.obs.temperature.reader.interfaces.TemperatureReader;

/**
 * @author bwoo
 *
 */
public class FuelTankTemperatureReader implements TemperatureReader
{
    private static final int MIN_TEMPERATURE = 0;
    private static final int MAX_TEMPERATURE = 60;

    private static TemperatureReader reader;

    private FuelTankTemperatureReader()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.obs.temperature.reader.interfaces.TemperatureReader#getTemperature()
     */
    @Override
    public State getTemperature()
    {
        int randValue = ThreadLocalRandom.current().nextInt(MIN_TEMPERATURE, MAX_TEMPERATURE + 1);

        return new DecimalType(randValue);
    }

    /**
     * instantiate a singleton.
     *
     * @return
     */
    public static synchronized TemperatureReader getInstance()
    {
        if (null == reader)
        {
            reader = new FuelTankTemperatureReader();
        }

        return reader;
    }

}
