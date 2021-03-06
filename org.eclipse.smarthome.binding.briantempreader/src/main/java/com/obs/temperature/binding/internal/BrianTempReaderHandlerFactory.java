/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.obs.temperature.binding.internal;

import static com.obs.temperature.binding.BrianTempReaderBindingConstants.*;

import java.util.Collections;
import java.util.Set;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

import com.obs.temperature.binding.handler.BrianTempReaderHandler;

/**
 * The {@link BrianTempReaderHandlerFactory} is responsible for creating things and thing 
 * handlers.
 * 
 * @author BrianWoo - Initial contribution
 */
public class BrianTempReaderHandlerFactory extends BaseThingHandlerFactory {
    
    private final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_TEMP_READER);
    
    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {

        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(THING_TYPE_TEMP_READER)) {
            return new BrianTempReaderHandler(thing);
        }

        return null;
    }
}

