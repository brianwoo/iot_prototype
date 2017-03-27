/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.obs.temperature.binding;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link BrianTempReaderBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author BrianWoo - Initial contribution
 */
public class BrianTempReaderBindingConstants
{

    public static final String BINDING_ID = "briantempreader";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_TEMP_READER = new ThingTypeUID(BINDING_ID, "temperature_reader");

    // List of all Channel ids
    public final static String CHANNEL_TEMPERATURE = "temperature";

}
