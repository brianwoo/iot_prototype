/**
 *
 */
package com.obs.temperature.reader.interfaces;

import org.eclipse.smarthome.core.types.State;

/**
 * @author bwoo
 *
 */
public interface TemperatureReader
{
    public State getTemperature();
}
