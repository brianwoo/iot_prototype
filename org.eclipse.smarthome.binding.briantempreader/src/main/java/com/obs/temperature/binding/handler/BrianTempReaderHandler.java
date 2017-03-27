/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.obs.temperature.binding.handler;

import static com.obs.temperature.binding.BrianTempReaderBindingConstants.CHANNEL_TEMPERATURE;

import java.math.BigDecimal;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obs.temperature.factory.TemperatureReaderFactory;
import com.obs.temperature.reader.interfaces.TemperatureReader;

/**
 * The {@link BrianTempReaderHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author BrianWoo - Initial contribution
 */
public class BrianTempReaderHandler extends BaseThingHandler
{
    private static final String REFRESH_PARAM = "refresh";
    private static final BigDecimal DEFAULT_REFRESH_INTERVAL = new BigDecimal(60);

    private Logger logger = LoggerFactory.getLogger(BrianTempReaderHandler.class);

    private BigDecimal refreshInterval;

    ScheduledFuture<?> refreshJob;

    public BrianTempReaderHandler(Thing thing)
    {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command)
    {
        if (command instanceof RefreshType)
        {
            TemperatureReaderFactory factory = new TemperatureReaderFactory();
            TemperatureReader reader = factory.getTemperatureReader(TemperatureReaderFactory.Type.FUEL_TANK);

            if (reader != null)
            {
                updateState(channelUID, reader.getTemperature());
            }
        }
        else
        {
            logger.debug("Command {} is not supported for channel: {}", command, channelUID.getId());
        }
    }

    @Override
    public void initialize()
    {
        // Read the refresh config set by the user.
        try
        {
            Configuration config = getThing().getConfiguration();
            refreshInterval = (BigDecimal) config.get(REFRESH_PARAM);

            if (null == refreshInterval)
            {
                refreshInterval = DEFAULT_REFRESH_INTERVAL;
            }
        }
        catch (Exception e) // unable to read the config
        {
            refreshInterval = DEFAULT_REFRESH_INTERVAL;
        }

        // Initialize the automatic refresh job.
        try
        {
            startAutomaticRefresh();
            updateStatus(ThingStatus.ONLINE);
        }
        catch (Exception e)
        {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_INITIALIZING_ERROR,
                    "Unable to initialize. Reason: " + e.getMessage());
            logger.error("Unable to initialize", e);
        }

    }

    @Override
    public void dispose()
    {
        refreshJob.cancel(true);
    }

    private void startAutomaticRefresh()
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    TemperatureReaderFactory factory = new TemperatureReaderFactory();
                    TemperatureReader reader = factory.getTemperatureReader(TemperatureReaderFactory.Type.FUEL_TANK);

                    if (reader != null)
                    {
                        updateState(new ChannelUID(getThing().getUID(), CHANNEL_TEMPERATURE), reader.getTemperature());
                    }
                }
                catch (Exception e)
                {
                    logger.debug("Exception occurred during execution: {}", e.getMessage(), e);
                }
            }
        };

        refreshJob = scheduler.scheduleAtFixedRate(runnable, 0, refreshInterval.intValue(), TimeUnit.SECONDS);
    }
}
